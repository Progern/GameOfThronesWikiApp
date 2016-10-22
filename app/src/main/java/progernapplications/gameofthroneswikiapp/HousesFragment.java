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


public class HousesFragment extends Fragment implements View.OnClickListener {

    private static String searchQuery = "http://anapioficeandfire.com/api/houses?name=";
    private static AsyncHttpClient mClient;
    private ArrayList<House> housesList;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private HousesRecViewAdapter mHousesAdapter;
    private EditText searchField;
    private Snackbar errorSnackbar;
    private ImageView baratheonSign;
    private StringBuffer weaponsBuf, seatsBuf;

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
        View myView = inflater.inflate(R.layout.fragment_houses, container, false);
        setupUI(myView);

        housesList = new ArrayList<>();
        mClient = new AsyncHttpClient();


        mRecyclerView = (RecyclerView) myView.findViewById(R.id.houses_rv);
        mLinearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        searchField = (EditText) myView.findViewById(R.id.houses_search_query);
        mHousesAdapter = new HousesRecViewAdapter(getActivity().getApplicationContext(), housesList);
        mRecyclerView.setAdapter(mHousesAdapter);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        baratheonSign = (ImageView) myView.findViewById(R.id.baratheon_sign);
        errorSnackbar = Snackbar.make(myView, "Search query is empty", Snackbar.LENGTH_LONG);
        baratheonSign.setOnClickListener(this);


        searchField.setOnClickListener(this);

        return myView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.baratheon_sign:
                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
                baratheonSign.startAnimation(animation);

                if (!(searchField.getText().toString().isEmpty())) {
                    getHouse(searchQuery + searchField.getText().toString());
                } else errorSnackbar.show();
                break;


        }
    }

    public void getHouse(String urlForParse) {
        weaponsBuf = new StringBuffer();
        seatsBuf = new StringBuffer();

        mClient.get(urlForParse, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    JSONObject house = response.getJSONObject(0);
                    if (listReduplication(housesList, house.getString("name"))) {

                        JSONArray seats = house.getJSONArray("seats");
                        for (int i = 0; i < seats.length(); i++) {
                            seatsBuf.append(seats.getString(i));
                        }

                        JSONArray weapons = house.getJSONArray("ancestralWeapons");
                        for (int i = 0; i < weapons.length(); i++) {
                            weaponsBuf.append(weapons.getString(i));
                        }

                        housesList.add(new House(house.getString("name"), house.getString("region"), "\"" + house.getString("words") + "\"", seatsBuf.toString(), weaponsBuf.toString()));
                        mHousesAdapter.notifyDataSetChanged();
                    } else
                        Toast.makeText(getActivity(), "Already added.", Toast.LENGTH_SHORT).show();


                } catch (JSONException e) {
                    Toast.makeText(getActivity(), "Error occurred. Try again please", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getActivity(), "Error occurred. Try again please", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // To check if we already added sought book to our list
    public boolean listReduplication(ArrayList<House> housesList, String name) {
        for (int i = 0; i < housesList.size(); i++) {
            if (housesList.get(i).getName().equals(name)) return false;
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
        outState.putSerializable("data", housesList);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            housesList = (ArrayList<House>) savedInstanceState.getSerializable("data");
            mClient = new AsyncHttpClient();
            mLinearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            mHousesAdapter = new HousesRecViewAdapter(getActivity().getApplicationContext(), housesList);
            mRecyclerView.setAdapter(mHousesAdapter);
            mRecyclerView.setLayoutManager(mLinearLayoutManager);


        }
    }
}
