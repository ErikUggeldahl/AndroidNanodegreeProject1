package example.com.project1popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class PopularMoviesActivityFragment extends Fragment {

    public PopularMoviesActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_popular_movies, container, false);

        ((Spinner)rootView.findViewById(R.id.spinner_sort_order))
                .setAdapter(CreateSpinnerSortAdapter());

        ArrayList<String> urls = new ArrayList<>();
        urls.add("http://i.imgur.com/DvpvklR.png");

        ImageAdapter adapter = new ImageAdapter(getActivity());
        adapter.AddImageUrls(urls);

        ((GridView) rootView.findViewById(R.id.popular_grid))
                .setAdapter(adapter);

        return rootView;
    }

    ArrayAdapter<CharSequence> CreateSpinnerSortAdapter() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(
                        getActivity(),
                        R.array.popular_movies_sort_options,
                        android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return adapter;
    }
}
