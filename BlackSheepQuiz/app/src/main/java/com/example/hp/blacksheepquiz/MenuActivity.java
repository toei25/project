package com.example.hp.blacksheepquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton campaign, timeAttack, howTo, exit;
    Intent intent;
    private DBHelper dh;
    ArrayList<Integer> stageBlock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        campaign = (ImageButton) findViewById(R.id.btnCampaign);
        timeAttack = (ImageButton) findViewById(R.id.btnTimeAttack);
        howTo = (ImageButton) findViewById(R.id.btnHowTo);
        exit = (ImageButton) findViewById(R.id.btnExit);

        campaign.setOnClickListener(this);
        timeAttack.setOnClickListener(this);
        howTo.setOnClickListener(this);
        exit.setOnClickListener(this);
        stageSetting();


    }

    private void stageSetting() {
        dh = new DBHelper(this);
        stageBlock = new ArrayList<Integer>();
        stageBlock.add(R.drawable.lv1);
        stageBlock.add(R.drawable.lv2b);
        stageBlock.add(R.drawable.lv3b);
        stageBlock.add(R.drawable.lv4b);
        stageBlock.add(R.drawable.lv5b);
        stageBlock.add(R.drawable.lv6b);
        stageBlock.add(R.drawable.lv7b);
        stageBlock.add(R.drawable.lv8b);
        stageBlock.add(R.drawable.lv9b);
        stageBlock.add(R.drawable.lv10b);
        stageBlock.add(R.drawable.lv11b);
        stageBlock.add(R.drawable.lv12b);
        stageBlock.add(R.drawable.lv13b);
        stageBlock.add(R.drawable.lv14b);
        stageBlock.add(R.drawable.lv15b);
        stageBlock.add(R.drawable.lv16b);
        stageBlock.add(R.drawable.lv17b);
        stageBlock.add(R.drawable.lv18b);
        stageBlock.add(R.drawable.lv19b);
        stageBlock.add(R.drawable.lv20b);
        for (int i = 0; i < 20; i++) {
            dh.insert(i + 1, stageBlock.get(i));
        }

        dh.insert(21, 0);
        dh.insert(22, 0);
    }

    @Override
    public void onClick(View v) {
        if (v == campaign) {


            intent = new Intent(this, SelectCampaignActivity.class);
            startActivity(intent);

        }

        if (v == timeAttack) {

            intent = new Intent(this, SelectTimeattackActivity.class);
            startActivity(intent);

        }

        if (v == howTo) {

            intent = new Intent(this, HowtoActivity.class);
            startActivity(intent);

        }

        if (v == exit) {

            finish();


        }

    }
}
