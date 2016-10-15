package progernapplications.gameofthroneswikiapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class BooksFragment extends Fragment implements View.OnClickListener{
    private ArrayList<Book> booksList;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private BooksRecViewAdapter mBooksAdapter;

    private EditText searchField;
    private static String searchQuery = "http://www.anapioficeandfire.com/api/books?name=";
    private static AsyncHttpClient mClient;
    private Snackbar errorSnackbar;
    private ImageView starkSign;

    private StringBuffer authorsBuf;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_books, container, false);
        // TODO Setup keyboard hiding

        booksList = new ArrayList<>();
        mClient = new AsyncHttpClient();

        mRecyclerView = (RecyclerView)myView.findViewById(R.id.booksRecView);
        mLinearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

        searchField = (EditText)myView.findViewById(R.id.searchFieldBooks);

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


        switch(view.getId())
        {
            case R.id.buttonImageBooks:
                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
                starkSign.startAnimation(animation);
                getBook(searchQuery + searchField.getText().toString());
                break;
        }
    }

    public void getBook(String urlForParse)
    {


        if( !(urlForParse.isEmpty()))
        {
            authorsBuf = new StringBuffer();

            mClient.get(urlForParse, new JsonHttpResponseHandler()
            {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    try {
                        JSONObject book = response.getJSONObject(0);
                        JSONArray authors = book.getJSONArray("authors");
                        if(authors.length() > 1) {
                            for (int i = 0; i < authors.length(); i++) {
                                authorsBuf.append(authors.getString(i) + ", ");
                            }
                        }
                        else authorsBuf.append(authors.getString(0));
                        booksList.add(new Book(book.getString("name"), book.getString("numberOfPages"), book.getString("publisher")
                        , book.getString("country"),authorsBuf.toString()));
                        mBooksAdapter.notifyDataSetChanged();
                    }catch (JSONException ex)
                    {
                        // TODO something magical, you know :P
                    }

                }
            });
        }
    }
}
