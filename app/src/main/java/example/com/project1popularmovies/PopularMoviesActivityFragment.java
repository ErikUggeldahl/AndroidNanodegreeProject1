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

public class PopularMoviesActivityFragment extends Fragment implements IMoviesListener {

    private ImageAdapter adapter;

    public PopularMoviesActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_popular_movies, container, false);

        ((Spinner) rootView.findViewById(R.id.spinner_sort_order))
                .setAdapter(createSpinnerSortAdapter());

        adapter = new ImageAdapter(getActivity());
        ((GridView) rootView.findViewById(R.id.popular_grid))
                .setAdapter(adapter);

        String apiKey = getString(R.string.api_key);
        new FetchPopularMoviesTask(this).execute(apiKey);

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

    @Override
    public void receiveMovies(Movie[] movies) {
        adapter.AddImageUrls(getMovieUrls(movies));
        adapter.notifyDataSetChanged();
    }

    private ArrayList<String> getMovieUrls(Movie[] movies) {
        ArrayList<String> urls = new ArrayList<>(movies.length);

        for (int i = 0; i < movies.length; i++) {
            urls.add(movies[i].getPosterUrl().toString());
        }

        return urls;
    }
}
