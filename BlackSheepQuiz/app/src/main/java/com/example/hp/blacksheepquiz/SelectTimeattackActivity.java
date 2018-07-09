package com.example.hp.blacksheepquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class SelectTimeattackActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton easyMode, hardMode;
    Intent intent;
    int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_timeattack);

        easyMode = (ImageButton)findViewById(R.id.easyLevelTime);
        hardMode = (ImageButton)findViewById(R.id.hardLevelTime);

        easyMode.setOnClickListener(this);
        hardMode.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v == easyMode) {
            mode = 1;
            intent = new Intent(this,StartTimeAttackActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("mode", mode);
            intent.putExtra("cBundle", bundle);
            startActivity(intent);
            finish();
        }

        if (v == hardMode) {
            mode = 2;
            intent = new Intent(this,StartTimeAttackActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("mode", mode);
            intent.putExtra("cBundle", bundle);
            startActivity(intent);
            finish();
        }

    }
}
