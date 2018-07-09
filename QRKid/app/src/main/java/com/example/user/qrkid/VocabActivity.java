package com.example.user.qrkid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class VocabActivity extends AppCompatActivity implements View.OnClickListener {

    protected ImageButton btn_back;
    protected ImageButton btn_spell;
    protected ImageButton btn_dic;
    protected ImageButton btn_home;
    protected Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocab);

        btn_back = (ImageButton)findViewById(R.id.btn_back);
        btn_dic = (ImageButton)findViewById(R.id.btn_dic);
        btn_spell = (ImageButton)findViewById(R.id.btn_spell);
        btn_home = (ImageButton)findViewById(R.id.btn_home);

        btn_spell.setOnClickListener(this);
        btn_dic.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        btn_home.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if(v == btn_back){
            intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
            finish();
        } else if (v == btn_dic){
            intent = new Intent(this, DictionaryPlayActivity.class);
            startActivity(intent);
            finish();
        } else if (v == btn_spell){
            intent = new Intent(this, SpellingABCActivity.class);
            startActivity(intent);
            finish();
        } else if (v == btn_home){
            intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
