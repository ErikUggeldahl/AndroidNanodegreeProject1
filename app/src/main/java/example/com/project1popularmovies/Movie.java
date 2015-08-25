package example.com.project1popularmovies;

import android.net.Uri;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

public class Movie {
    final String mTitle;
    final String mOverview;

    final Date mReleaseDate;
    final int mScore;

    final URL mPosterUrl;

    public Movie(String title, String overview, Date releaseDate, int score, String posterUrl) {
        mTitle = title;
        mOverview = overview;
        mReleaseDate = releaseDate;
        mScore = score;
        mPosterUrl = createPosterUri(posterUrl);
    }

    public String getTitle() { return mTitle; }

    public String getOverview() {
        return mOverview;
    }

    public Date getReleaseDate() {
        return mReleaseDate;
    }

    public int getScore() {
        return mScore;
    }

    public URL getPosterUrl() {
        return mPosterUrl;
    }

    private URL createPosterUri(String finalFragment) {
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme("http")
                .authority("image.tmdb.org")
                .appendPath("t")
                .appendPath("p")
                .appendPath("w185")
                .appendPath(finalFragment.substring(1));

        try {
            return new URL(uriBuilder.toString());
        } catch (MalformedURLException e) {
            Log.e("Movie", "Unable to parse poster URL.");
            return null;
        }
    }
}
