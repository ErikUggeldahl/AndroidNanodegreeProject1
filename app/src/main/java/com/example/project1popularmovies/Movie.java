package com.example.project1popularmovies;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

public class Movie implements Parcelable {
    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            final String LOG_TAG = "createFromParcel";

            try {
                String name = in.readString();
                String overview = in.readString();
                Date release = new Date(in.readLong());
                int rating = in.readInt();
                URL url = new URL(in.readString());

                return new Movie(
                        name,
                        overview,
                        release,
                        rating,
                        url
                );
            } catch (MalformedURLException e) {
                Log.e(LOG_TAG, "Failed to parse url.");
                return null;
            }
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

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

    public Movie(String title, String overview, Date releaseDate, int score, URL posterUrl) {
        mTitle = title;
        mOverview = overview;
        mReleaseDate = releaseDate;
        mScore = score;
        mPosterUrl = posterUrl;
    }

    public String getTitle() {
        return mTitle;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mOverview);
        dest.writeLong(mReleaseDate.getTime());
        dest.writeInt(mScore);
        dest.writeString(mPosterUrl.toString());
    }

}
