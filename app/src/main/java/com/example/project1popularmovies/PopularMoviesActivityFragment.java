package com.example.project1popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;

import java.util.ArrayList;

public class PopularMoviesActivityFragment extends Fragment implements IMoviesListener {

    private ImageAdapter mImageGridAdapter;

    public PopularMoviesActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_popular_movies, container, false);

        Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner_sort_order);
        ArrayAdapter<CharSequence> spinnerSortAdapter = createSpinnerSortAdapter();
        spinner.setAdapter(spinnerSortAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String sortOrder = parent.getItemAtPosition(position).toString();
                updateWithSortOrder(sortOrder);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mImageGridAdapter = new ImageAdapter(getActivity());
        ((GridView) rootView.findViewById(R.id.popular_grid))
                .setAdapter(mImageGridAdapter);

        return rootView;
    }

    ArrayAdapter<CharSequence> createSpinnerSortAdapter() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(
                        getActivity(),
                        R.array.popular_movies_sort_options,
                        android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return adapter;
    }

    private void updateWithSortOrder(String sortOrder) {
        String sortOrderArg = null;

        if (sortOrder.equals(getString(R.string.sort_popularity_label)))
            sortOrderArg = getString(R.string.sort_popularity);
        else if (sortOrder.equals(getString(R.string.sort_score_label)))
            sortOrderArg = getString(R.string.sort_score);
        else
            return;

        mImageGridAdapter.clear();
        mImageGridAdapter.notifyDataSetChanged();

        String apiKey = getString(R.string.api_key);
        new FetchPopularMoviesTask(this).execute(apiKey, sortOrderArg);
    }

    @Override
    public void receiveMovies(Movie[] movies) {
        mImageGridAdapter.AddImageUrls(getMovieUrls(movies));
        mImageGridAdapter.notifyDataSetChanged();
    }

    private ArrayList<String> getMovieUrls(Movie[] movies) {
        if (movies == null)
            return new ArrayList<>();

        ArrayList<String> urls = new ArrayList<>(movies.length);

        for (int i = 0; i < movies.length; i++) {
            urls.add(movies[i].getPosterUrl().toString());
        }

        return urls;
    }
}
