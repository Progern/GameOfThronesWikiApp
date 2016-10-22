package progernapplications.gameofthroneswikiapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class CharactersFragment extends Fragment implements View.OnClickListener {
    private static String searchQuery = "http://www.anapioficeandfire.com/api/characters?name=";
    private static AsyncHttpClient mClient;
    //
    StringBuffer tvseriesBuffer;
    String title;
    String deathDate;
    private ArrayList<Character> charactersList;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private CharactersRecViewAdapter mAdapter;
    private EditText searchField;
    private Snackbar mSnackbar;
    private ImageView targSign;

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_characters, container, false);
        setupUI(myView);
        charactersList = new ArrayList<>();
        mClient = new AsyncHttpClient();


        mRecyclerView = (RecyclerView) myView.findViewById(R.id.charsRecView);
        mLinearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

        searchField = (EditText) myView.findViewById(R.id.charSearchField);

        mAdapter = new CharactersRecViewAdapter(getActivity().getApplicationContext(), charactersList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        targSign = (ImageView) myView.findViewById(R.id.buttonImage);
        targSign.setOnClickListener(this);


        mSnackbar = Snackbar.make(myView, R.string.empty_search_query, Snackbar.LENGTH_LONG);

        return myView;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.buttonImage:
                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
                targSign.startAnimation(animation);
                if (!(searchField.getText().toString().isEmpty()))
                    getCharacter(searchQuery + searchField.getText().toString());
                else mSnackbar.show();
                break;

        }
    }

    public void getCharacter(String urlForParse) {

        tvseriesBuffer = new StringBuffer();
        if (!(urlForParse.isEmpty())) {
            mClient.get(urlForParse, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    try {
                        JSONObject character = response.getJSONObject(0);
                        if (listReduplication(charactersList, character.getString("name"))) {
                            title = character.getJSONArray("titles").getString(0);

                            // Check if character has died. If not - then fill the Text field with appropriate text
                            deathDate = checkInfoPresence(character.getString("died"));


                            charactersList.add(new Character(character.getString("name"), title, character.getString("culture")
                                    , character.getString("born"), deathDate, tvseriesBuffer.toString()));
                            mAdapter.notifyDataSetChanged();
                        } else Toast.makeText(getActivity(), "Already added.", Toast.LENGTH_SHORT).show();
                    } catch (JSONException ex) {
                        // TODO something :D
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Toast.makeText(getActivity(), "Error occurred. Try again please", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    // To avoid blank spaces in UI
    public String checkInfoPresence(String s) {
        if (s.isEmpty()) return "No information";
        else return s;
    }

    // To check if we already added sought character to our list
    public boolean listReduplication(ArrayList<Character> charactersList, String name)
    {
        for(int i = 0; i < charactersList.size(); i++)
        {
            if(charactersList.get(i).getName().equals(name)) return false;
        }
        return true;
    }

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText) || !(view instanceof Button) || !(view instanceof RecyclerView)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    hideSoftKeyboard(getActivity());
                    return false;
                }


            });
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("data", charactersList);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            charactersList = (ArrayList<Character>) savedInstanceState.getSerializable("data");
            mClient = new AsyncHttpClient();

            mLinearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            mAdapter = new CharactersRecViewAdapter(getActivity().getApplicationContext(), charactersList);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(mLinearLayoutManager);
        }
    }
}