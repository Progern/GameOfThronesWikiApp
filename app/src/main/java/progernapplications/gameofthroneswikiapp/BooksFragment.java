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


public class BooksFragment extends Fragment implements View.OnClickListener {
    private static String searchQuery = "http://www.anapioficeandfire.com/api/books?name=";
    private static AsyncHttpClient mClient;
    private ArrayList<Book> booksList;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private BooksRecViewAdapter mBooksAdapter;
    private EditText searchField;
    private Snackbar errorSnackbar;
    private ImageView starkSign;

    private StringBuffer authorsBuf;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_books, container, false);
        setupUI(myView);

        booksList = new ArrayList<>();
        mClient = new AsyncHttpClient();

        mRecyclerView = (RecyclerView) myView.findViewById(R.id.booksRecView);
        mLinearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

        searchField = (EditText) myView.findViewById(R.id.searchFieldBooks);

        mBooksAdapter = new BooksRecViewAdapter(getActivity().getApplicationContext(), booksList);
        mRecyclerView.setAdapter(mBooksAdapter);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        starkSign = (ImageView) myView.findViewById(R.id.buttonImageBooks);

        errorSnackbar = Snackbar.make(myView, R.string.empty_search_query, Snackbar.LENGTH_LONG);
        starkSign.setOnClickListener(this);

        return myView;

    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.buttonImageBooks:
                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
                starkSign.startAnimation(animation);
                if (!(searchField.getText().toString().isEmpty()))
                    getBook(searchQuery + searchField.getText().toString());
                else errorSnackbar.show();

                break;
        }
    }

    public void getBook(String urlForParse) {
        authorsBuf = new StringBuffer();

        mClient.get(urlForParse, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    JSONObject book = response.getJSONObject(0);
                    if(listReduplication(booksList, book.getString("name"))) {
                        JSONArray authors = book.getJSONArray("authors");
                        if (authors.length() > 1) {
                            for (int i = 0; i < authors.length(); i++) {
                                authorsBuf.append(authors.getString(i) + ", ");
                            }
                        } else authorsBuf.append(authors.getString(0));
                        booksList.add(new Book(book.getString("name"), book.getString("numberOfPages"), book.getString("publisher")
                                , book.getString("country"), authorsBuf.toString()));
                        mBooksAdapter.notifyDataSetChanged();
                    }
                    else Toast.makeText(getActivity(), "Already added.", Toast.LENGTH_SHORT).show();
                } catch (JSONException ex) {
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
    public boolean listReduplication(ArrayList<Book> booksList, String name)
    {
        for(int i = 0; i < booksList.size(); i++)
        {
            if(booksList.get(i).getName().equals(name)) return false;
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

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
}