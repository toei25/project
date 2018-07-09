package com.example.hp.blacksheepquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.dd.morphingbutton.MorphingButton;

import java.util.ArrayList;

public class ResultStageActivity extends AppCompatActivity implements View.OnClickListener {

    MorphingButton reGame, selectStage, comeBackMenu;
    ImageView resultClear;
    Intent intent;
    int clear, level;
    private DBHelper dh;
    ArrayList<Integer> stageClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_stage);
        reGame = (MorphingButton)findViewById(R.id.playRetry);
        selectStage = (MorphingButton)findViewById(R.id.comeStage);
        comeBackMenu = (MorphingButton)findViewById(R.id.nextC);
        resultClear = (ImageView)findViewById(R.id.clearStage);
        stageSetClear();
        dh = new DBHelper(this);
        Bundle bundle = getIntent().getBundleExtra("chBundle");
        clear = bundle.getInt("clear");
        level = bundle.getInt("levelUp");
        reGame.setText("Retry");
        selectStage.setText("Select Stage");
        if (clear == 1 && level < 20) {
            dh.update(level + 1,stageClear.get(level));
            comeBackMenu.setText("Next Stage");
            resultClear.setImageResource(R.drawable.text_menu_stageclear);
        } else if (clear == 1 && level == 20) {
            comeBackMenu.setText("Back to Menu");
            resultClear.setImageResource(R.drawable.text_menu_stageclear);
        } else {
            comeBackMenu.setText("Back to Menu");
            resultClear.setImageResource(R.drawable.text_menu_stagefailed);
        }
        selectStage.setOnClickListener(this);
        comeBackMenu.setOnClickListener(this);
        reGame.setOnClickListener(this);

    }

    private void stageSetClear() {
        stageClear = new ArrayList<Integer>();
        stageClear.add(R.drawable.lv1);
        stageClear.add(R.drawable.lv2);
        stageClear.add(R.drawable.lv3);
        stageClear.add(R.drawable.lv4);
        stageClear.add(R.drawable.lv5);
        stageClear.add(R.drawable.lv6);
        stageClear.add(R.drawable.lv7);
        stageClear.add(R.drawable.lv8);
        stageClear.add(R.drawable.lv9);
        stageClear.add(R.drawable.lv10);
        stageClear.add(R.drawable.lv11);
        stageClear.add(R.drawable.lv12);
        stageClear.add(R.drawable.lv13);
        stageClear.add(R.drawable.lv14);
        stageClear.add(R.drawable.lv15);
        stageClear.add(R.drawable.lv16);
        stageClear.add(R.drawable.lv17);
        stageClear.add(R.drawable.lv18);
        stageClear.add(R.drawable.lv19);
        stageClear.add(R.drawable.lv20);
    }

    @Override
    public void onClick(View v) {
        if (v == reGame) {
            intent = new Intent(this, StartCampaignActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("level", level);
            intent.putExtra("cpBundle", bundle);
            startActivity(intent);
            finish();
        }

        if (v == selectStage) {
            intent = new Intent(this, SelectCampaignActivity.class);
            startActivity(intent);
            finish();
        }

        if (v == comeBackMenu) {
            if (comeBackMenu.getText().toString().equalsIgnoreCase("Back to Menu")) {
                intent = new Intent(this, MenuActivity.class);
                startActivity(intent);
            } else {
                intent = new Intent(this, StartCampaignActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("level", level + 1);
                intent.putExtra("cpBundle", bundle);
                startActivity(intent);
            }
        }
    }
}
