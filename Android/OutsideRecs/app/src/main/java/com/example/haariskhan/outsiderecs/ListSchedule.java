package com.example.haariskhan.outsiderecs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.HashMap;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.PlaylistTrack;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ListSchedule extends AppCompatActivity {

    SpotifyApi api;
    SpotifyService service;
    ImageView img;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_schedule);
        Intent intent = getIntent();
        String token = intent.getStringExtra("Token");

        img = (ImageView) findViewById(R.id.niceMeme);
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
                for (Artist a : artistPager.items) {
                    System.out.println(a.name);
                }
            }

            @Override
            public void failure(SpotifyError error) {
                System.out.println("Failure: " + error.getMessage());
            }
        });
    }
}
