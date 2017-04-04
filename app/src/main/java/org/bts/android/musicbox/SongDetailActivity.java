package org.bts.android.musicbox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class SongDetailActivity extends AppCompatActivity {

    private static final String TAG = SongDetailActivity.class.getSimpleName();
    int listIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_detail);

        Intent mIntent = getIntent();
        Log.i(SongDetailActivity.TAG, mIntent.toString());
        listIndex = mIntent.getIntExtra("Index", -1);

        final TextView songTitle = (TextView) findViewById(R.id.tv_song_title);
        final TextView songCountry = (TextView) findViewById(R.id.tv_song_country);
        final TextView songDescription = (TextView) findViewById(R.id.tv_song_description);

        String [] countries = {
                "Brazil",
                "USA",
                "India",
                "Iceland",
                "South Korea",
                "Indonesia",
                "Brazil"
        };

        String [] descriptions = {
                "Samba is a Brazilian musical genre and dance style, with its roots in Africa via the West African slave trade religious particularly of Angola and African traditions.",
                "Country music is a genre of American popular originated Southern States in the 1920s music that in the United",
                "The music of India includes multiple varieties of folk music, pop, and Indian classical music. India's classical music tradition, including Hindustani music and Carnatic, has a history spanning millennia and developed over several eras",
                "The music of Iceland includes vibrant folk and pop traditions. Well-known artists from Iceland include medieval music group Voces Thules, alternative rock band The Sugarcubes, singers Björk and Emiliana Torrini, post- rock band Sigur Rós and indie folk/indie pop band Of Monsters and Men",
                "The Music of South Korea has evolved over the course of the decades since the end of the Korean War, and has its roots in the music of the Korean people, who have inhabited the Korean peninsula for over a millennium. Contemporary South Korean music can be divided into three different categories: Traditional Korean folk music, popular music, or K- pop, and Western- influenced non-popular music",
                "The music of Indonesia demonstrates its cultural diversity, the local musical creativity, as well as subsequent foreign musical influences that shaped contemporary music scenes of Indonesia. Nearly thousands Indonesian having its own cultural and artistic history and character Nearly of islands",
                "Samba is a Brazilian musical genre and dance style, with its roots in Africa via the West African slave trade religious particularly of Angola"
        };

        songTitle.setText(String.valueOf(PlayerActivity.getListItem().get(listIndex)));
        songCountry.setText("Country of Origin: " + countries[listIndex]);
        songDescription.setText("Description: " +descriptions[listIndex]);
    }


}
