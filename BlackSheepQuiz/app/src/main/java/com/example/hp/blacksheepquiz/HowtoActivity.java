package com.example.hp.blacksheepquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.dd.morphingbutton.MorphingButton;

public class HowtoActivity extends AppCompatActivity implements View.OnClickListener {

    MorphingButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_howto);
        back = (MorphingButton)findViewById(R.id.backMenu);
        back.setOnClickListener(this);

    }//

    @Override
    public void onClick(View v) {
        if (v == back) {
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
