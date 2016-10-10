package progernapplications.gameofthroneswikiapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Олег-PC on 09.10.2016.
 */
public class CharactersRecViewAdapter extends RecyclerView.Adapter<CharactersRecViewAdapter.charactersViewHolder>
{

    private Context context;
    private ArrayList<Character> characterList;


    public CharactersRecViewAdapter(Context context, ArrayList<Character> characterList)
    {
        this.context = context;
        this.characterList = characterList;
    }

    @Override
    public charactersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.from(context).inflate(R.layout.character_row_rv, parent, false);

        charactersViewHolder mCharactersViewHolder = new charactersViewHolder(view);

        return mCharactersViewHolder;
    }

    @Override
    public void onBindViewHolder(charactersViewHolder holder, int position) {
        Character currentCharacter = characterList.get(position);
            holder.name.setText(currentCharacter.getName() + ", ");
            holder.title.setText(currentCharacter.getTitle());
            holder.culture.setText(currentCharacter.getCulture());
            holder.born.setText(currentCharacter.getBornDate());
            holder.died.setText(currentCharacter.getDeathName());

            holder.father.setText(currentCharacter.getFather());
            holder.mother.setText(currentCharacter.getMother());
            holder.spouse.setText(currentCharacter.getSpouse());

            holder.povBooks.setText(currentCharacter.getListOfBooks());
            holder.tvSeries.setText(currentCharacter.getTvSeries());
    }

    @Override
    public int getItemCount() {
        if(characterList != null)
        {
            return characterList.size();
        }
        else return 0;
    }

    public static class charactersViewHolder extends RecyclerView.ViewHolder
    {

        //Main information
        public TextView name, title, culture, born, died;

        // Family information

        public TextView father, mother, spouse;

        // Secondary information

        public TextView povBooks, tvSeries;



        public charactersViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.name_rv);
            title = (TextView)itemView.findViewById(R.id.title_rv);
            culture = (TextView)itemView.findViewById(R.id.culture_rv);
            born = (TextView)itemView.findViewById(R.id.born_rv);
            died = (TextView)itemView.findViewById(R.id.died_rv);

            father = (TextView)itemView.findViewById(R.id.father_rv);
            mother = (TextView)itemView.findViewById(R.id.mother_rv);
            spouse = (TextView)itemView.findViewById(R.id.spouce_rv);

            povBooks = (TextView)itemView.findViewById(R.id.books_rv);
            tvSeries = (TextView)itemView.findViewById(R.id.tv_series_rv);


        }
    }
}