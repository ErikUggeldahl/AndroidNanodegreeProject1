package com.example.project1popularmovies;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

// Made with help from
// https://github.com/square/picasso/blob/master/picasso-sample/src/main/java/com/example/picasso/SampleGridViewAdapter.java

public class ImageAdapter extends BaseAdapter {
    private final Context mContext;
    private List<String> mImageUrls = new ArrayList<>();

    public ImageAdapter(Context context) {
        mContext = context;
    }

    public void AddImageUrls(List<String> urls) {
        mImageUrls.addAll(urls);
    }

    public void clear()
    {
        mImageUrls.clear();
    }

    @Override
    public int getCount() {
        return mImageUrls.size();
    }

    @Override
    public String getItem(int position) {
        return mImageUrls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = (ImageView) convertView;
        if (imageView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(500, 750));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        String url = getItem(position);

        Picasso.with(mContext)
                .load(url)
                .fit()
                .into(imageView);

        return imageView;
    }
}
