package progernapplications.gameofthroneswikiapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;


public class CharactersFragment extends Fragment {
    private ArrayList<Character> charactersList;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLinearLayoutManager;
    CharactersRecViewAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_characters, container, false);
        charactersList = new ArrayList<>();

        mRecyclerView = (RecyclerView)myView.findViewById(R.id.charsRecView);
        mLinearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

        charactersList.add(new Character("John Snow", "the King of the North", "Northman", "880 AD", "Not yet.", "Rhaegar Targarien", "Lyanna Stark", "None", "All books", "All series"));
        charactersList.add(new Character("Daenerys Targarien", "Khaleesi", "Southman", "865 AD", "Not yet", "?" , "?", "Khal Drogo (Dead)", "All books", "All series"));

        mAdapter = new CharactersRecViewAdapter(getActivity().getApplicationContext(), charactersList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        return myView;
    }
}
