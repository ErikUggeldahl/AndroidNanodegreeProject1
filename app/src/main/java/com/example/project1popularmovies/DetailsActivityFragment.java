package com.example.project1popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsActivityFragment extends Fragment {

    public DetailsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        Movie movie = getActivity().getIntent().getParcelableExtra(getString(R.string.intent_extra_movie));

        getActivity().setTitle(movie.getTitle());

        String formattedDate = new SimpleDateFormat("MMM dd, yyyy").format(movie.getReleaseDate());
        ((TextView) rootView.findViewById(R.id.detail_release_date)).setText(formattedDate);

        ((TextView) rootView.findViewById(R.id.detail_overview)).setText(movie.getOverview());

        ((RatingBar) rootView.findViewById(R.id.detail_rating)).setRating(movie.getScore() / 20.0f);

        ImageView poster = (ImageView) rootView.findViewById(R.id.detail_poster);
        Picasso.with(getActivity())
                .load(movie.getPosterUrl().toString())
                .fit()
                .into(poster);

        return rootView;
    }
}
