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

public class BooksRecViewAdapter extends RecyclerView.Adapter<BooksRecViewAdapter.booksViewHolder>
{
    private Context context;
    private ArrayList<Book> booksList;
    private int lastPosition = -1;



    public BooksRecViewAdapter(Context context, ArrayList<Book> booksList)
    {
        this.context = context;
        this.booksList = booksList;
    }

    @Override
    public booksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.from(context).inflate(R.layout.book_row_cv, parent, false);

        booksViewHolder mBooksViewHolder = new booksViewHolder(view);

        return mBooksViewHolder;
    }

    @Override
    public void onBindViewHolder(booksViewHolder holder, int position) {
        Book currentBook = booksList.get(position);
            holder.bookName.setText(currentBook.getName());
            holder.author.setText(currentBook.getAuthorsList());
            holder.publisher.setText(currentBook.getPublisher());
            holder.pages.setText(currentBook.getPages());
            holder.countryPublisher.setText(currentBook.getCountryOfRelease());

        setAnimation(holder.container, position);
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.fade_in_rv);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        if(booksList != null)
        {
            return booksList.size();
        }
        else return 0;
    }

    public static class booksViewHolder extends RecyclerView.ViewHolder
    {

        public TextView bookName, author, publisher, pages, countryPublisher;
        public LinearLayout container;

        public booksViewHolder(View itemView) {
            super(itemView);
            bookName = (TextView)itemView.findViewById(R.id.nameOfBook_rv);
            author = (TextView)itemView.findViewById(R.id.author_books_rv);
            publisher = (TextView)itemView.findViewById(R.id.publisher_rv);
            pages = (TextView)itemView.findViewById(R.id.pages_quantity_rv);
            countryPublisher = (TextView)itemView.findViewById(R.id.country_books_rv);

            container = (LinearLayout)itemView.findViewById(R.id.book_container);
        }
    }


}
