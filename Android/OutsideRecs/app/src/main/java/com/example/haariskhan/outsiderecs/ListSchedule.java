package com.example.haariskhan.outsiderecs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.PlaylistTrack;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ListSchedule extends AppCompatActivity {

    SpotifyApi api;
    SpotifyService service;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_schedule);
        Intent intent = getIntent();
        String token = intent.getStringExtra("Token");

        listView = (ListView) findViewById(R.id.artistList);

        api = new SpotifyApi();
        api.setAccessToken(token);

        service = api.getService();

        analyzeArtists();

    }

    public void analyzeArtists() {
        service.getTopArtists(new SpotifyCallback<Pager<Artist>>() {
            @Override
            public void success(Pager<Artist> artistPager, Response response) {
                ArrayList<String> names = new ArrayList<String>();
                ArrayList<Image> images = new ArrayList<Image>();

                for (Artist a : artistPager.items) {
                    if (a.images.size() != 0) {
                        images.add(a.images.get(0));
                        names.add(a.name);
                    }
                }

                String[] nameString = names.toArray(new String[names.size()]);
                Image[] imageArray = images.toArray(new Image[images.size()]);

                Adapter adapter = new Adapter(getApplicationContext(), nameString, imageArray);
                listView.setAdapter(adapter);
            }

            @Override
            public void failure(SpotifyError error) {
                System.out.println("Failure: " + error.getMessage());
            }
        });
    }
}
