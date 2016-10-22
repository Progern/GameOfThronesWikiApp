package progernapplications.gameofthroneswikiapp;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class HousesRecViewAdapter extends RecyclerView.Adapter<HousesRecViewAdapter.housesViewHolder> {

    private Context context;
    private ArrayList<House> housesList;
    private int lastPosition = -1; // For animation

    public HousesRecViewAdapter(Context context, ArrayList<House> housesList) {
        this.context = context;
        this.housesList = housesList;
    }

    @Override
    public housesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.from(context).inflate(R.layout.house_row_rv, parent, false);

        housesViewHolder mHousesViewHolder = new housesViewHolder(view);

        return mHousesViewHolder;
    }

    @Override
    public void onBindViewHolder(housesViewHolder holder, int position) {
        House currentHouse = housesList.get(position);
        holder.name.setText(currentHouse.getName());
        holder.region.setText(currentHouse.getRegion());
        holder.weapons.setText(currentHouse.getWeapons());
        holder.seat.setText(currentHouse.getSeat());
        holder.words.setText(currentHouse.getWords());

        setAnimation(holder.container, position);

    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.fade_in_rv);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        if (housesList != null) {
            return housesList.size();
        } else return 0;
    }

    public static class housesViewHolder extends RecyclerView.ViewHolder {
        public TextView name, region, words, seat, weapons;

        public LinearLayout container;


        public housesViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.house_name_rv);
            region = (TextView) itemView.findViewById(R.id.house_region_rv);
            words = (TextView) itemView.findViewById(R.id.house_words_rv);
            seat = (TextView) itemView.findViewById(R.id.house_seats_rv);
            weapons = (TextView) itemView.findViewById(R.id.house_weapons_rv);

            container = (LinearLayout) itemView.findViewById(R.id.houses_container);
        }
    }
}
