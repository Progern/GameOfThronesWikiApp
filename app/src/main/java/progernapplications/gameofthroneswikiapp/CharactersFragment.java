package progernapplications.gameofthroneswikiapp;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class CharactersFragment extends Fragment implements View.OnClickListener {
    private ArrayList<Character> charactersList;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private CharactersRecViewAdapter mAdapter;
    private Button searchButton;
    private EditText searchField;
    private static String searchQuery = "http://www.anapioficeandfire.com/api/characters?name=";
    private static AsyncHttpClient mClient;
    StringBuffer titlesBuffer, tvseriesBuffer;
    private Snackbar mSnackbar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_characters, container, false);
        setupUI(myView);
        charactersList = new ArrayList<>();
        mClient = new AsyncHttpClient();

        mRecyclerView = (RecyclerView) myView.findViewById(R.id.charsRecView);
        mLinearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        searchButton = (Button) myView.findViewById(R.id.charSearchButton);
        searchField = (EditText) myView.findViewById(R.id.charSearchField);

        mAdapter = new CharactersRecViewAdapter(getActivity().getApplicationContext(), charactersList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        searchButton.setOnClickListener(this);
        mSnackbar = Snackbar.make(myView, "Fill search query first.", Snackbar.LENGTH_LONG);

        return myView;
    }

    public void addNewCharacter(Character character) {
        charactersList.add(character);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {

        titlesBuffer = new StringBuffer();
        tvseriesBuffer = new StringBuffer();


        switch (view.getId()) {
            case R.id.charSearchButton:

                if (!(searchField.getText().toString().equals(""))) {

                    mClient.get(searchQuery + searchField.getText().toString().replaceAll(" ", "%20"), new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                            try {
                                // Main JSON-Object with Character info
                                JSONObject currentCharacter = response.getJSONObject(0);

                                // Check have we got something or not
                                if (currentCharacter == null) mSnackbar.show();

                                else {
                                    // JSON-Array with all titles, we need just few..
                                    JSONArray titles = currentCharacter.getJSONArray("titles");
                                    for (int i = 0; i < titles.length(); i++) {
                                        titlesBuffer.append(titles.getString(i) + " ");
                                    }

                                    JSONArray tvSeriesArr = currentCharacter.getJSONArray("tvSeries");

                                    for (int i = 0; i < tvSeriesArr.length(); i++) {
                                        tvseriesBuffer.append(tvSeriesArr.getString(i) + ", ");
                                    }


                                    // ------------------------------------------------------------------------
                                    charactersList.add(new Character(currentCharacter.getString("name"), titlesBuffer.toString()
                                            , currentCharacter.getString("culture"), currentCharacter.getString("born"), currentCharacter.getString("died")
                                            , currentCharacter.getString("father"), currentCharacter.getString("mother"), currentCharacter.getString("spouse"), tvseriesBuffer.toString()));
                                    mAdapter.notifyDataSetChanged();
                                    // ------------------------------------------------------------------------
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                    });
                } else mSnackbar.show();


        }
    }

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText) || !(view instanceof Button) || (view instanceof RecyclerView)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    hideSoftKeyboard(getActivity());
                    return false;
                }


            });
        }

    }
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

}


