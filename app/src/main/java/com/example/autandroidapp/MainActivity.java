package com.example.autandroidapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SoundEffectConstants;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

//this class includes the activities of all the buttons on the homepage,
//once the buttons are pressed, the activity will be executed.
public class MainActivity extends AppCompatActivity {
    private MediaPlayer main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(main == null) //Media players are space intensive so we only want one, create if not already created.
        {
            main = MediaPlayer.create(this,R.raw.blop_mark_diangelo);
        }
    }

    //this method includes the activity of the chatbot button on the homepage,
    //once the chatbot button is pressed, the activity will be executed.
    public void chatbotActivity(View view) {
        messagePop();
        Intent intent = new Intent(this, ChatbotActivity.class);
        startActivity(intent);
    }

    //this method includes the activity of the courses button on the homepage,
    //once the courses button is pressed, the activity will be executed.
    public void coursesActivity(View view) {
        messagePop();
        Intent intent = new Intent(this, courses_button.class);
        startActivity(intent);
    }

    //this method includes the activity of the help button on the homepage,
    //once the help button is pressed, the activity will be executed.
    public void helpActivity(View view) {
        messagePop();
        Intent intent = new Intent(this, help_button.class);
        startActivity(intent);
    }

    //this method includes the activity of the services button on the homepage,
    //once the services button is pressed, the activity will be executed.
    public void servicesActivity(View view) {
        messagePop();
        Intent intent = new Intent(this, services_button.class);
        startActivity(intent);
    }

    //this method includes the activity of the map button on the homepage,
    //once the map button is pressed, the activity will be executed.
    public void MapActivity(View view) {
        messagePop();
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    //this method includes the activity of the calender button on the homepage,
    //once the calender button is pressed, the activity will be executed.
    public void CalendarActivity(View view) {
        messagePop();
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }

    //to play the sound
    public void messagePop()
    {
        getMain().start();
    }

    //Getter for the  media player
    public MediaPlayer getMain() {
        return main;
    }
}
