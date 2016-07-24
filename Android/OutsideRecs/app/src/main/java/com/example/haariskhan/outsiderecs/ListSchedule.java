package com.example.haariskhan.outsiderecs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Artists;
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
    static int choice;

    static HashSet<String> perfArtists = new HashSet<>();
    static HashSet<Artist> toSee = new HashSet<>();
    static HashSet<Artist> seenArtists = new HashSet<>();
    static ArrayDeque<Artist> fringe = new ArrayDeque<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_schedule);
        Intent intent = getIntent();

        boolean fri = intent.getBooleanExtra("Friday", false);
        boolean sat = intent.getBooleanExtra("Saturday", false);
        boolean sun = intent.getBooleanExtra("Sunday", false);

        if (fri && sat && sun) { // 1st option
            choice = 1;
        } else if (fri && sat) { // 2nd option
            choice = 2;
        } else if (fri && sun) { // 3rd option
            choice = 3;
        } else if (sat && sun) { // 4th option
            choice = 4;
        } else if (fri) { // 5th option
            choice = 5;
        } else if (sat) { // 6th option
            choice = 6;
        } else { // 7th option
            /* To Do: Make sure at least one option is chosen before user advances */
            choice = 7;
        }

        String token = intent.getStringExtra("Token");

        listView = (ListView) findViewById(R.id.artistList);

        api = new SpotifyApi();
        api.setAccessToken(token);

        service = api.getService();

        analyzeArtists(choice);

    }

    public void analyzeArtists(int choice) {

        service.getTopArtists(new SpotifyCallback<Pager<Artist>>() {
            @Override
            public void success(Pager<Artist> artistPager, Response response) {

                ArrayList<String> favNames = new ArrayList<String>();
                ArrayList<Image> images = new ArrayList<Image>();
                ArrayList<Artist> bfsArtists = new ArrayList<Artist>();

                for (Artist a : artistPager.items) {
                    if (a.images.size() != 0) {
                        images.add(a.images.get(0));
                        favNames.add(a.name);
                    }
                }

                for (Artist a : artistPager.items) {
                    bfsArtists.add(a);
                }

                breadthFirstSearch(bfsArtists);

                String[] nameString = favNames.toArray(new String[favNames.size()]);
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

    public void breadthFirstSearch(ArrayList<Artist> names) {
        if (choice == 1) {
            perfArtists.add("LCD SOUNDSYSTEM");
            perfArtists.add("J. COLE");
            perfArtists.add("DURAN DURAN");
            perfArtists.add("BEACH HOUSE");
            perfArtists.add("GRIMES");
            perfArtists.add("MIIKE SNOW");
            perfArtists.add("NATHANIEL RATELIFF & THE NIGHT SWEATS");
            perfArtists.add("THOMAS JACK");
            perfArtists.add("FOALS");
            perfArtists.add("THE CLAYPOOL LENNON DELIRIUM");
            perfArtists.add("ST. LUCIA");
            perfArtists.add("POLIÇA");
            perfArtists.add("HIATUS KAIYOTE");
            perfArtists.add("RA RA RIOT");
            perfArtists.add("TOKIMONSTA");
            perfArtists.add("WET");
            perfArtists.add("JIDENNA");
            perfArtists.add("LÅPSLEY");
            perfArtists.add("MARIAN HILL");
            perfArtists.add("CAVEMAN");
            perfArtists.add("VULFPECK");
            perfArtists.add("MOON TAXI");
            perfArtists.add("LANY");
            perfArtists.add("WHITNEY");
            perfArtists.add("REDLIGHT");
            perfArtists.add("PILLOWTALK");
            perfArtists.add("FDVM");
            perfArtists.add("219 BOYS");
            perfArtists.add("MAKE IT FUNKY DJS");
            perfArtists.add("RADIOHEAD");
            perfArtists.add("ZEDD");
            perfArtists.add("AIR");
            perfArtists.add("SUFJAN STEVENS");
            perfArtists.add("HALSEY");
            perfArtists.add("BIG GRAMS (BIG BOI + PHANTOGRAM)");
            perfArtists.add("THE LAST SHADOW PUPPETS");
            perfArtists.add("LORD HURON");
            perfArtists.add("JAUZ");
            perfArtists.add("VINCE STAPLES");
            perfArtists.add("YEARS & YEARS");
            perfArtists.add("IBEYI");
            perfArtists.add("PEACHES");
            perfArtists.add("ANDERSON .PAAK & THE FREE NATIONALS");
            perfArtists.add("THE WOMBATS");
            perfArtists.add("THE KNOCKS");
            perfArtists.add("ROGUE WAVE");
            perfArtists.add("CON BRIO");
            perfArtists.add("KEVIN MORBY");
            perfArtists.add("FANTASTIC NEGRITO");
            perfArtists.add("LEWIS DEL MAR");
            perfArtists.add("JULIEN BAKER");
            perfArtists.add("DECLAN MCKENNA");
            perfArtists.add("METHYL ETHEL");
            perfArtists.add("VICTOR CALDERONE");
            perfArtists.add("PURPLE DISCO MACHINE");
            perfArtists.add("TRANSLUCENT");
            perfArtists.add("W.HAZE");
            perfArtists.add("MOTION POTION");
            perfArtists.add("LIONEL RICHIE");
            perfArtists.add("LANA DEL REY");
            perfArtists.add("MAJOR LAZER");
            perfArtists.add("RYAN ADAMS AND THE SHINING");
            perfArtists.add("CHANCE THE RAPPER");
            perfArtists.add("MIGUEL");
            perfArtists.add("JASON ISBELL");
            perfArtists.add("THIRD EYE BLIND");
            perfArtists.add("KEHLANI");
            perfArtists.add("GRIZ");
            perfArtists.add("BRANDI CARLILE");
            perfArtists.add("SNAKEHIPS");
            perfArtists.add("LETTUCE");
            perfArtists.add("OH WONDER");
            perfArtists.add("KAMASI WASHINGTON");
            perfArtists.add("JACK GARRATT");
            perfArtists.add("RÜFÜS DU SOL");
            perfArtists.add("DIIV");
            perfArtists.add("NATALIA LAFOURCADE");
            perfArtists.add("THE OH HELLOS");
            perfArtists.add("FRANCES");
            perfArtists.add("HÆLOS");
            perfArtists.add("CLOVES");
            perfArtists.add("HERON OBLIVION");
            perfArtists.add("GINA TURNER");
            perfArtists.add("LEE K");
            perfArtists.add("GRENSTA");
            perfArtists.add("DECKARD");
            perfArtists.add("DEREK HENA");

        } else if (choice == 2) {
            perfArtists.add("LCD SOUNDSYSTEM");
            perfArtists.add("J. COLE");
            perfArtists.add("DURAN DURAN");
            perfArtists.add("BEACH HOUSE");
            perfArtists.add("GRIMES");
            perfArtists.add("MIIKE SNOW");
            perfArtists.add("NATHANIEL RATELIFF & THE NIGHT SWEATS");
            perfArtists.add("THOMAS JACK");
            perfArtists.add("FOALS");
            perfArtists.add("THE CLAYPOOL LENNON DELIRIUM");
            perfArtists.add("ST. LUCIA");
            perfArtists.add("POLIÇA");
            perfArtists.add("HIATUS KAIYOTE");
            perfArtists.add("RA RA RIOT");
            perfArtists.add("TOKIMONSTA");
            perfArtists.add("WET");
            perfArtists.add("JIDENNA");
            perfArtists.add("LÅPSLEY");
            perfArtists.add("MARIAN HILL");
            perfArtists.add("CAVEMAN");
            perfArtists.add("VULFPECK");
            perfArtists.add("MOON TAXI");
            perfArtists.add("LANY");
            perfArtists.add("WHITNEY");
            perfArtists.add("REDLIGHT");
            perfArtists.add("PILLOWTALK");
            perfArtists.add("FDVM");
            perfArtists.add("219 BOYS");
            perfArtists.add("MAKE IT FUNKY DJS");
            perfArtists.add("RADIOHEAD");
            perfArtists.add("ZEDD");
            perfArtists.add("AIR");
            perfArtists.add("SUFJAN STEVENS");
            perfArtists.add("HALSEY");
            perfArtists.add("BIG GRAMS (BIG BOI + PHANTOGRAM)");
            perfArtists.add("THE LAST SHADOW PUPPETS");
            perfArtists.add("LORD HURON");
            perfArtists.add("JAUZ");
            perfArtists.add("VINCE STAPLES");
            perfArtists.add("YEARS & YEARS");
            perfArtists.add("IBEYI");
            perfArtists.add("PEACHES");
            perfArtists.add("ANDERSON .PAAK & THE FREE NATIONALS");
            perfArtists.add("THE WOMBATS");
            perfArtists.add("THE KNOCKS");
            perfArtists.add("ROGUE WAVE");
            perfArtists.add("CON BRIO");
            perfArtists.add("KEVIN MORBY");
            perfArtists.add("FANTASTIC NEGRITO");
            perfArtists.add("LEWIS DEL MAR");
            perfArtists.add("JULIEN BAKER");
            perfArtists.add("DECLAN MCKENNA");
            perfArtists.add("METHYL ETHEL");
            perfArtists.add("VICTOR CALDERONE");
            perfArtists.add("PURPLE DISCO MACHINE");
            perfArtists.add("TRANSLUCENT");
            perfArtists.add("W.HAZE");
            perfArtists.add("MOTION POTION");
        } else if (choice == 3) {
            perfArtists.add("LCD SOUNDSYSTEM");
            perfArtists.add("J. COLE");
            perfArtists.add("DURAN DURAN");
            perfArtists.add("BEACH HOUSE");
            perfArtists.add("GRIMES");
            perfArtists.add("MIIKE SNOW");
            perfArtists.add("NATHANIEL RATELIFF & THE NIGHT SWEATS");
            perfArtists.add("THOMAS JACK");
            perfArtists.add("FOALS");
            perfArtists.add("THE CLAYPOOL LENNON DELIRIUM");
            perfArtists.add("ST. LUCIA");
            perfArtists.add("POLIÇA");
            perfArtists.add("HIATUS KAIYOTE");
            perfArtists.add("RA RA RIOT");
            perfArtists.add("TOKIMONSTA");
            perfArtists.add("WET");
            perfArtists.add("JIDENNA");
            perfArtists.add("LÅPSLEY");
            perfArtists.add("MARIAN HILL");
            perfArtists.add("CAVEMAN");
            perfArtists.add("VULFPECK");
            perfArtists.add("MOON TAXI");
            perfArtists.add("LANY");
            perfArtists.add("WHITNEY");
            perfArtists.add("REDLIGHT");
            perfArtists.add("PILLOWTALK");
            perfArtists.add("FDVM");
            perfArtists.add("219 BOYS");
            perfArtists.add("MAKE IT FUNKY DJS");
            perfArtists.add("LIONEL RICHIE");
            perfArtists.add("LANA DEL REY");
            perfArtists.add("MAJOR LAZER");
            perfArtists.add("RYAN ADAMS AND THE SHINING");
            perfArtists.add("CHANCE THE RAPPER");
            perfArtists.add("MIGUEL");
            perfArtists.add("JASON ISBELL");
            perfArtists.add("THIRD EYE BLIND");
            perfArtists.add("KEHLANI");
            perfArtists.add("GRIZ");
            perfArtists.add("BRANDI CARLILE");
            perfArtists.add("SNAKEHIPS");
            perfArtists.add("LETTUCE");
            perfArtists.add("OH WONDER");
            perfArtists.add("KAMASI WASHINGTON");
            perfArtists.add("JACK GARRATT");
            perfArtists.add("RÜFÜS DU SOL");
            perfArtists.add("DIIV");
            perfArtists.add("NATALIA LAFOURCADE");
            perfArtists.add("THE OH HELLOS");
            perfArtists.add("FRANCES");
            perfArtists.add("HÆLOS");
            perfArtists.add("CLOVES");
            perfArtists.add("HERON OBLIVION");
            perfArtists.add("GINA TURNER");
            perfArtists.add("LEE K");
            perfArtists.add("GRENSTA");
            perfArtists.add("DECKARD");
            perfArtists.add("DEREK HENA");
        } else if (choice == 4) {
            perfArtists.add("RADIOHEAD");
            perfArtists.add("ZEDD");
            perfArtists.add("AIR");
            perfArtists.add("SUFJAN STEVENS");
            perfArtists.add("HALSEY");
            perfArtists.add("BIG GRAMS (BIG BOI + PHANTOGRAM)");
            perfArtists.add("THE LAST SHADOW PUPPETS");
            perfArtists.add("LORD HURON");
            perfArtists.add("JAUZ");
            perfArtists.add("VINCE STAPLES");
            perfArtists.add("YEARS & YEARS");
            perfArtists.add("IBEYI");
            perfArtists.add("PEACHES");
            perfArtists.add("ANDERSON .PAAK & THE FREE NATIONALS");
            perfArtists.add("THE WOMBATS");
            perfArtists.add("THE KNOCKS");
            perfArtists.add("ROGUE WAVE");
            perfArtists.add("CON BRIO");
            perfArtists.add("KEVIN MORBY");
            perfArtists.add("FANTASTIC NEGRITO");
            perfArtists.add("LEWIS DEL MAR");
            perfArtists.add("JULIEN BAKER");
            perfArtists.add("DECLAN MCKENNA");
            perfArtists.add("METHYL ETHEL");
            perfArtists.add("VICTOR CALDERONE");
            perfArtists.add("PURPLE DISCO MACHINE");
            perfArtists.add("TRANSLUCENT");
            perfArtists.add("W.HAZE");
            perfArtists.add("MOTION POTION");
            perfArtists.add("LIONEL RICHIE");
            perfArtists.add("LANA DEL REY");
            perfArtists.add("MAJOR LAZER");
            perfArtists.add("RYAN ADAMS AND THE SHINING");
            perfArtists.add("CHANCE THE RAPPER");
            perfArtists.add("MIGUEL");
            perfArtists.add("JASON ISBELL");
            perfArtists.add("THIRD EYE BLIND");
            perfArtists.add("KEHLANI");
            perfArtists.add("GRIZ");
            perfArtists.add("BRANDI CARLILE");
            perfArtists.add("SNAKEHIPS");
            perfArtists.add("LETTUCE");
            perfArtists.add("OH WONDER");
            perfArtists.add("KAMASI WASHINGTON");
            perfArtists.add("JACK GARRATT");
            perfArtists.add("RÜFÜS DU SOL");
            perfArtists.add("DIIV");
            perfArtists.add("NATALIA LAFOURCADE");
            perfArtists.add("THE OH HELLOS");
            perfArtists.add("FRANCES");
            perfArtists.add("HÆLOS");
            perfArtists.add("CLOVES");
            perfArtists.add("HERON OBLIVION");
            perfArtists.add("GINA TURNER");
            perfArtists.add("LEE K");
            perfArtists.add("GRENSTA");
            perfArtists.add("DECKARD");
            perfArtists.add("DEREK HENA");
        } else if (choice == 5) {
            perfArtists.add("LCD SOUNDSYSTEM");
            perfArtists.add("J. COLE");
            perfArtists.add("DURAN DURAN");
            perfArtists.add("BEACH HOUSE");
            perfArtists.add("GRIMES");
            perfArtists.add("MIIKE SNOW");
            perfArtists.add("NATHANIEL RATELIFF & THE NIGHT SWEATS");
            perfArtists.add("THOMAS JACK");
            perfArtists.add("FOALS");
            perfArtists.add("THE CLAYPOOL LENNON DELIRIUM");
            perfArtists.add("ST. LUCIA");
            perfArtists.add("POLIÇA");
            perfArtists.add("HIATUS KAIYOTE");
            perfArtists.add("RA RA RIOT");
            perfArtists.add("TOKIMONSTA");
            perfArtists.add("WET");
            perfArtists.add("JIDENNA");
            perfArtists.add("LÅPSLEY");
            perfArtists.add("MARIAN HILL");
            perfArtists.add("CAVEMAN");
            perfArtists.add("VULFPECK");
            perfArtists.add("MOON TAXI");
            perfArtists.add("LANY");
            perfArtists.add("WHITNEY");
            perfArtists.add("REDLIGHT");
            perfArtists.add("PILLOWTALK");
            perfArtists.add("FDVM");
            perfArtists.add("219 BOYS");
            perfArtists.add("MAKE IT FUNKY DJS");
        } else if (choice == 6) {
            perfArtists.add("RADIOHEAD");
            perfArtists.add("ZEDD");
            perfArtists.add("AIR");
            perfArtists.add("SUFJAN STEVENS");
            perfArtists.add("HALSEY");
            perfArtists.add("BIG GRAMS (BIG BOI + PHANTOGRAM)");
            perfArtists.add("THE LAST SHADOW PUPPETS");
            perfArtists.add("LORD HURON");
            perfArtists.add("JAUZ");
            perfArtists.add("VINCE STAPLES");
            perfArtists.add("YEARS & YEARS");
            perfArtists.add("IBEYI");
            perfArtists.add("PEACHES");
            perfArtists.add("ANDERSON .PAAK & THE FREE NATIONALS");
            perfArtists.add("THE WOMBATS");
            perfArtists.add("THE KNOCKS");
            perfArtists.add("ROGUE WAVE");
            perfArtists.add("CON BRIO");
            perfArtists.add("KEVIN MORBY");
            perfArtists.add("FANTASTIC NEGRITO");
            perfArtists.add("LEWIS DEL MAR");
            perfArtists.add("JULIEN BAKER");
            perfArtists.add("DECLAN MCKENNA");
            perfArtists.add("METHYL ETHEL");
            perfArtists.add("VICTOR CALDERONE");
            perfArtists.add("PURPLE DISCO MACHINE");
            perfArtists.add("TRANSLUCENT");
            perfArtists.add("W.HAZE");
            perfArtists.add("MOTION POTION");
        } else {
            perfArtists.add("LIONEL RICHIE");
            perfArtists.add("LANA DEL REY");
            perfArtists.add("MAJOR LAZER");
            perfArtists.add("RYAN ADAMS AND THE SHINING");
            perfArtists.add("CHANCE THE RAPPER");
            perfArtists.add("MIGUEL");
            perfArtists.add("JASON ISBELL");
            perfArtists.add("THIRD EYE BLIND");
            perfArtists.add("KEHLANI");
            perfArtists.add("GRIZ");
            perfArtists.add("BRANDI CARLILE");
            perfArtists.add("SNAKEHIPS");
            perfArtists.add("LETTUCE");
            perfArtists.add("OH WONDER");
            perfArtists.add("KAMASI WASHINGTON");
            perfArtists.add("JACK GARRATT");
            perfArtists.add("RÜFÜS DU SOL");
            perfArtists.add("DIIV");
            perfArtists.add("NATALIA LAFOURCADE");
            perfArtists.add("THE OH HELLOS");
            perfArtists.add("FRANCES");
            perfArtists.add("HÆLOS");
            perfArtists.add("CLOVES");
            perfArtists.add("HERON OBLIVION");
            perfArtists.add("GINA TURNER");
            perfArtists.add("LEE K");
            perfArtists.add("GRENSTA");
            perfArtists.add("DECKARD");
            perfArtists.add("DEREK HENA");
        }

        int upperBound = names.size();

        for (int i = 0; i < upperBound; i++) {
            fringe.add(names.remove(0));
        }

        while (!fringe.isEmpty()) {
            Artist a = fringe.remove();
            seenArtists.add(a);

            service.getRelatedArtists(a.id, new Callback<Artists>() {
                @Override
                public void success(Artists artists, Response response) {
                    List<Artist> list = artists.artists;
                    for (Artist artist : list) {
                        if (perfArtists.contains(artist.name)) {
                            toSee.add(artist);
                        }

                        if (!seenArtists.contains(artist)) {
                            fringe.add(artist);
                            seenArtists.add(artist);
                        }
                    }

                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }

        System.out.println("Size: " + toSee.size());

        for (Artist b : toSee) {
            System.out.println("Artist: " + b.name);
        }
    }
}
