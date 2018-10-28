package com.example.android.newsapp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {


    public MovieAdapter(Context context, List<Movie> movies) {
        super(context, 0, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.movie_list_item, parent, false);
        }

        Movie currentMovie = getItem(position);

        ImageView movieView = (ImageView) listItemView.findViewById(R.id.image);
        movieView.setImageResource(currentMovie.getImageResourceId());

        TextView titleView = (TextView) listItemView.findViewById(R.id.title);
        titleView.setText(currentMovie.getTitle());

        TextView sectionView = (TextView) listItemView.findViewById(R.id.section);
        sectionView.setText(currentMovie.getSection());

        TextView authorView = (TextView) listItemView.findViewById(R.id.author);
        authorView.setText(currentMovie.getAuthor());

        TextView newDateView = listItemView.findViewById(R.id.date);
        newDateView.setText(currentMovie.getPublicationDate());

        return listItemView;
    }

}

