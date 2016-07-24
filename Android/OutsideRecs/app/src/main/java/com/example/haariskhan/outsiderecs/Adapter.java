package com.example.haariskhan.outsiderecs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import kaaes.spotify.webapi.android.models.Image;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by haariskhan on 7/24/16.
 */
public class Adapter extends BaseAdapter {

    private final Context context;
    private String[] names;
    private Image[] images;

    public Adapter(Context context, String[] names, Image[] images) {
        this.context = context;
        this.names = names;
        this.images = images;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi = LayoutInflater.from(context);
            v = vi.inflate(R.layout.row, null);
        }

        String name = getName(position);
        Image img = getImage(position);

        if (name != null && img != null) {
            TextView textView = (TextView) v.findViewById(R.id.name);
            ImageView imageView = (ImageView) v.findViewById(R.id.img);

            if (textView != null && imageView != null) {
                textView.setText(name);
                Picasso.with(context).load(img.url).into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        System.out.println("Success!");
                    }

                    @Override
                    public void onError() {
                        System.out.println("Failure");
                    }
                });
            }
        }

        return v;
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    public String getName(int i) {
        return names[i];
    }

    public Image getImage(int i) {
        return images[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}
