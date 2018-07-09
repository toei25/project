package com.example.hp.blacksheepquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.dd.morphingbutton.MorphingButton;

import java.util.List;

public class ResultScoreActivity extends AppCompatActivity implements View.OnClickListener {

    TextView showResult, easyRec, hardRec;
    MorphingButton again, reMenu;
    int mScore, eScore, hScore;
    private DBHelper dh;
    private StringBuilder sb;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_score);
        showResult = (TextView)findViewById(R.id.result);
        easyRec = (TextView)findViewById(R.id.easyResult);
        hardRec = (TextView)findViewById(R.id.hardResult);
        again = (MorphingButton)findViewById(R.id.playAgain);
        reMenu = (MorphingButton)findViewById(R.id.comeMenu);
        again.setOnClickListener(this);
        reMenu.setOnClickListener(this);
        Bundle bundle = getIntent().getBundleExtra("rBundle");
        mScore = bundle.getInt("score");
        showResult.setText(String.valueOf(mScore));
        dh = new DBHelper(this);
        if (bundle.getInt("modeClear") == 1) {
            if (mScore > eScore ) {
                dh.update(21,mScore);
            }
        } else {
            if (mScore > hScore) {
                dh.update(22,mScore);
            }
        }

        dh.selectById(21);
        retrieveAllRows();
        eScore = Integer.parseInt(sb.toString());
        dh.selectById(22);
        retrieveAllRows();
        hScore = Integer.parseInt(sb.toString());

        easyRec.setText(String.valueOf(eScore));
        hardRec.setText(String.valueOf(hScore));
    }



    private void retrieveAllRows() {
        List<Integer> idList = dh.getIdList();
        List<Integer> stageList = dh.getStageList();
        Log.v("MainActivity", idList.toString());
        Log.v("MainActivity", stageList.toString());
        sb = new StringBuilder();
        for (Integer name : stageList) {
            sb.append(name);
        }
    }

    @Override
    public void onClick(View v) {

        if (v == again) {
            intent = new Intent(this, SelectTimeattackActivity.class);
            startActivity(intent);
            finish();
        }

        if (v == reMenu) {
            intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
