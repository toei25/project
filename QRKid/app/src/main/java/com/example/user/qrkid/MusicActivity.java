package com.example.user.qrkid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MusicActivity extends AppCompatActivity implements View.OnClickListener {

    protected ImageButton btn_back;
    protected ImageButton btn_quiz;
    protected ImageButton btn_play;
    protected ImageButton btn_home;
    protected Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        btn_back = (ImageButton)findViewById(R.id.btn_back);
        btn_home = (ImageButton)findViewById(R.id.btn_home);
        btn_play = (ImageButton)findViewById(R.id.btn_play);
        btn_quiz = (ImageButton)findViewById(R.id.btn_quiz);

        btn_play.setOnClickListener(this);
        btn_quiz.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        btn_home.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btn_back){
            intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
            finish();
        } else if (v == btn_play){
            intent = new Intent(this, PlayMelodyActivity.class);
            startActivity(intent);
            finish();
        } else if (v == btn_quiz){
            intent = new Intent(this, QuizMelodyActivity.class);
            startActivity(intent);
            finish();
        } else if (v == btn_home){
            intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
