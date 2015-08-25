package example.com.project1popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FetchPopularMoviesTask extends AsyncTask<String, Void, Movie[]> {

    private static final String LOG_TAG = FetchPopularMoviesTask.class.getSimpleName();

    private final IMoviesListener mListener;

    public FetchPopularMoviesTask(IMoviesListener listener) {
        mListener = listener;
    }

    @Override
    protected void onPostExecute(Movie[] movies) {
        super.onPostExecute(movies);

        mListener.receiveMovies(movies);
    }

    @Override
    protected Movie[] doInBackground(String... params) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String popularMoviesJson = null;

        try {
            String apiKey = params[0];
            String sortBy = params[1];

            Uri.Builder uriBuilder = new Uri.Builder();
            uriBuilder.scheme("http")
                    .authority("api.themoviedb.org")
                    .appendPath("3")
                    .appendPath("discover")
                    .appendPath("movie")
                    .appendQueryParameter("sort_by", sortBy)
                    .appendQueryParameter("vote_count.gte", "10")
                    .appendQueryParameter("api_key", apiKey);

            URL url = new URL(uriBuilder.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream == null) {
                return null;
            }

            StringBuilder buffer = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            if (buffer.length() == 0) {
                return null;
            }

            popularMoviesJson = buffer.toString();

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        try {
            return parseMovies(popularMoviesJson);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Unable to parse movies.");
            return null;
        } catch (ParseException e) {
            Log.e(LOG_TAG, "Unable to parse a movie date.");
            return null;
        }
    }

    private Movie[] parseMovies(String jsonData)
            throws JSONException, ParseException {

        JSONObject popularMoviesObj = new JSONObject(jsonData);
        JSONArray popularMoviesArr = popularMoviesObj.getJSONArray("results");

        Movie[] movies = new Movie[popularMoviesArr.length()];
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        for (int i = 0; i < popularMoviesArr.length(); i++) {
            JSONObject movie = popularMoviesArr.getJSONObject(i);

            String releaseDateString = movie.getString("release_date");
            Date releaseDate = releaseDateString != null ? dateFormatter.parse(releaseDateString) : null;

            movies[i] = new Movie(
                    movie.getString("original_title"),
                    movie.getString("overview"),
                    releaseDate,
                    (int) (movie.getDouble("vote_average") * 10),
                    movie.getString("poster_path"));
        }

        return movies;
    }
}
