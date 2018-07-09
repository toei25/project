package com.example.user.qrkid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    protected ImageButton music_btn;
    protected ImageButton vocab_btn;
    protected ImageButton math_btn;
    protected Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        music_btn = (ImageButton)findViewById(R.id.btn_music);
        vocab_btn = (ImageButton)findViewById(R.id.btn_vocab);
        math_btn = (ImageButton)findViewById(R.id.btn_math);
        music_btn.setOnClickListener(this);
        vocab_btn.setOnClickListener(this);
        math_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == music_btn){
            intent = new Intent(this,MusicActivity.class);
            startActivity(intent);
        } else if (v == vocab_btn){
            intent = new Intent(this,VocabActivity.class);
            startActivity(intent);
        } else if (v == math_btn){
            intent = new Intent(this,MathActivity.class);
            startActivity(intent);
        }
    }
}
