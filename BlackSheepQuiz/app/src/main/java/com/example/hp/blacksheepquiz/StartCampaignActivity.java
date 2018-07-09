package com.example.hp.blacksheepquiz;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class StartCampaignActivity extends AppCompatActivity implements View.OnClickListener {

    protected TextView countdownC, showScoreC, condition;
    protected int scoreNumC, checkClear, levelGame, randomAnsC, randomChoiceC, randomChoiceHardC, randomFakeC, numCircleC, numPicC, numGroupC;
    protected ImageButton[] choiceC;
    protected GroupChoiceGame groupChoiceGame = new GroupChoiceGame();
    protected final CountdownTime timer = new CountdownTime(100000, 1000);
    String conditionGame;
    int[][][] circle;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_campaign);
        countdownC = (TextView)findViewById(R.id.countdownTimerC);
        showScoreC = (TextView)findViewById(R.id.scoreC);
        condition = (TextView)findViewById(R.id.conditionC);
        intent = new Intent(this, ResultStageActivity.class);
        choiceC = new ImageButton[] {(ImageButton) findViewById(R.id.choice1c), (ImageButton) findViewById(R.id.choice2c), (ImageButton) findViewById(R.id.choice3c), (ImageButton) findViewById(R.id.choice4c)};
        circle = groupChoiceGame.circle;
        Bundle bundle = getIntent().getBundleExtra("cpBundle");
        levelGame = bundle.getInt("level");
        for (int i = 0; i < 4; i++) {
            choiceC[i].setOnClickListener(this);
        }
        scoreNumC = 0;
        numCircleC = groupChoiceGame.numCircle;
        numGroupC = groupChoiceGame.numGroupAll;
        numPicC = groupChoiceGame.numPicAll;
        conditionGame = groupChoiceGame.con[levelGame - 1];
        condition.setText(conditionGame);
        timer.start();
        checkStage();


    }

    public void checkStage(){
        switch (levelGame) {
            case 1 : gameLv1();
                break;
            case 2 : gameLv2();
                break;
            case 3 : gameLv3();
                break;
            case 4 : gameLv4();
                break;
            case 5 : gameLv5();
                break;
            case 6 : gameLv6();
                break;
            case 7 : gameLv7();
                break;
            case 8 : gameLv8();
                break;
            case 9 : gameLv9();
                break;
            case 10 : gameLv10();
                break;
            case 11 : gameLv11();
                break;
            case 12 : gameLv12();
                break;
            case 13 : gameLv13();
                break;
            case 14 : gameLv14();
                break;
            case 15 : gameLv15();
                break;
            case 16 : gameLv16();
                break;
            case 17 : gameLv17();
                break;
            case 18 : gameLv18();
                break;
            case 19 : gameLv19();
                break;
            case 20 : gameLv20();
                break;

        }
    }

    @Override
    public void onClick(View v) {
        if (v == choiceC[randomAnsC]){
            scoreNumC++;
            showScoreC.setText(String.valueOf(scoreNumC));
            checkStage();
        } else {

            switch (levelGame) {
                case 1 : showScoreC.setText(String.valueOf(scoreNumC));
                    break;
                case 2 : checkNegative(1);
                    break;
                case 3 : checkNegative(1);
                    break;
                case 4 : checkNegative(2);
                    break;
                case 5 : checkNegative(2);
                    break;
                case 6 : reScoreRun();
                    break;
                case 7 : reScoreRun();
                    break;
                case 8 : reScoreRun();
                    break;
                case 9 : deathMath();
                    break;
                case 10 : deathMath();
                    break;
                case 11 : checkNegative(1);
                    break;
                case 12 : checkNegative(1);
                    break;
                case 13 : checkNegative(2);
                    break;
                case 14 : checkNegative(3);
                    break;
                case  15 : reScoreRun();
                    break;
                case 16 : reScoreRun();
                    break;
                case 17 : deathMath();
                    break;
                case 18 : deathMath();
                    break;
                case 19 : deathMath();
                    break;
                case 20 : checkNegative(3);
            }


        }
    }

    public void deathMath(){
        Bundle bundle = new Bundle();
        intent = new Intent(this, ResultStageActivity.class);
        checkClear = 0;
        bundle.putInt("clear", checkClear);
        bundle.putInt("levelUp", levelGame);
        intent.putExtra("chBundle", bundle);
        timer.cancel();
        startActivity(intent);
        finish();

    }

    public  void reScoreRun(){
        scoreNumC = 0;
        showScoreC.setText(String.valueOf(scoreNumC));
    }

    public void checkNegative(int x) {
        if (scoreNumC == 0 || scoreNumC <= x) {
            scoreNumC = 0;
            showScoreC.setText(String.valueOf(scoreNumC));
        } else {
            scoreNumC-=x;
            showScoreC.setText(String.valueOf(scoreNumC));
        }
    }

    public class CountdownTime extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public CountdownTime(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long millis = millisUntilFinished;
            String hms = String.format("%02d", TimeUnit.MILLISECONDS.toSeconds(millis));
            countdownC.setText(hms);
        }

        @Override
        public void onFinish() {
            countdownC.setText("Time out!");
            Bundle bundle = new Bundle();
            checkClear = 0;
            bundle.putInt("clear", checkClear);
            bundle.putInt("levelUp", levelGame);
            intent.putExtra("chBundle", bundle);
            startActivity(intent);
            finish();
        }
    }

    public  void gameLv1(){
        randomAnsC = randomNum(4);
        randomChoiceC = randomNum(numCircleC);
        do {
            randomFakeC = randomNum(numCircleC);
        } while (randomFakeC == randomChoiceC);

        for (int i = 0; i < 4; i++) {
            if (i == randomAnsC) {

                choiceC[i].setImageResource(circle[randomChoiceC][randomNum(numGroupC)][randomNum(numPicC)]);
            } else {

                choiceC[i].setImageResource(circle[randomFakeC][randomNum(numGroupC)][randomNum(numPicC)]);
            }
        }

        if (scoreNumC == 10) {
            checkClear = 1;
            Bundle bundle = new Bundle();
            bundle.putInt("clear", checkClear);
            bundle.putInt("levelUp", levelGame);
            intent.putExtra("chBundle", bundle);
            timer.cancel();
            startActivity(intent);
            finish();
        }


    }

    public  void gameLv2(){
        randomAnsC = randomNum(4);
        randomChoiceC = randomNum(numCircleC);
        do {
            randomFakeC = randomNum(numCircleC);
        } while (randomFakeC == randomChoiceC);

        for (int i = 0; i < 4; i++) {
            if (i == randomAnsC) {

                choiceC[i].setImageResource(circle[randomChoiceC][randomNum(numGroupC)][randomNum(numPicC)]);
            } else {

                choiceC[i].setImageResource(circle[randomFakeC][randomNum(numGroupC)][randomNum(numPicC)]);
            }
        }

        if (scoreNumC == 15) {
            checkClear = 1;
            Bundle bundle = new Bundle();
            bundle.putInt("clear", checkClear);
            bundle.putInt("levelUp", levelGame);
            intent.putExtra("chBundle", bundle);
            timer.cancel();
            startActivity(intent);
            finish();
        }


    }

    public  void gameLv3(){
        randomAnsC = randomNum(4);
        randomChoiceC = randomNum(numCircleC);
        do {
            randomFakeC = randomNum(numCircleC);
        } while (randomFakeC == randomChoiceC);

        for (int i = 0; i < 4; i++) {
            if (i == randomAnsC) {

                choiceC[i].setImageResource(circle[randomChoiceC][randomNum(numGroupC)][randomNum(numPicC)]);
            } else {

                choiceC[i].setImageResource(circle[randomFakeC][randomNum(numGroupC)][randomNum(numPicC)]);
            }
        }

        if (scoreNumC == 20) {
            checkClear = 1;
            Bundle bundle = new Bundle();
            bundle.putInt("clear", checkClear);
            bundle.putInt("levelUp", levelGame);
            intent.putExtra("chBundle", bundle);
            timer.cancel();
            startActivity(intent);
            finish();
        }


    }


    public  void gameLv4(){
        randomAnsC = randomNum(4);
        randomChoiceC = randomNum(numCircleC);
        do {
            randomFakeC = randomNum(numCircleC);
        } while (randomFakeC == randomChoiceC);

        for (int i = 0; i < 4; i++) {
            if (i == randomAnsC) {

                choiceC[i].setImageResource(circle[randomChoiceC][randomNum(numGroupC)][randomNum(numPicC)]);
            } else {

                choiceC[i].setImageResource(circle[randomFakeC][randomNum(numGroupC)][randomNum(numPicC)]);
            }
        }

        if (scoreNumC == 25) {
            checkClear = 1;
            Bundle bundle = new Bundle();
            bundle.putInt("clear", checkClear);
            bundle.putInt("levelUp", levelGame);
            intent.putExtra("chBundle", bundle);
            timer.cancel();
            startActivity(intent);
            finish();
        }


    }

    public  void gameLv5(){
        randomAnsC = randomNum(4);
        randomChoiceC = randomNum(numCircleC);
        do {
            randomFakeC = randomNum(numCircleC);
        } while (randomFakeC == randomChoiceC);

        for (int i = 0; i < 4; i++) {
            if (i == randomAnsC) {

                choiceC[i].setImageResource(circle[randomChoiceC][randomNum(numGroupC)][randomNum(numPicC)]);
            } else {

                choiceC[i].setImageResource(circle[randomFakeC][randomNum(numGroupC)][randomNum(numPicC)]);
            }
        }

        if (scoreNumC == 30) {
            checkClear = 1;
            Bundle bundle = new Bundle();
            bundle.putInt("clear", checkClear);
            bundle.putInt("levelUp", levelGame);
            intent.putExtra("chBundle", bundle);
            timer.cancel();
            startActivity(intent);
            finish();
        }


    }

    public  void gameLv6(){
        randomAnsC = randomNum(4);
        randomChoiceC = randomNum(numCircleC);
        do {
            randomFakeC = randomNum(numCircleC);
        } while (randomFakeC == randomChoiceC);

        for (int i = 0; i < 4; i++) {
            if (i == randomAnsC) {

                choiceC[i].setImageResource(circle[randomChoiceC][randomNum(numGroupC)][randomNum(numPicC)]);
            } else {

                choiceC[i].setImageResource(circle[randomFakeC][randomNum(numGroupC)][randomNum(numPicC)]);
            }
        }

        if (scoreNumC == 10) {
            checkClear = 1;
            Bundle bundle = new Bundle();
            bundle.putInt("clear", checkClear);
            bundle.putInt("levelUp", levelGame);
            intent.putExtra("chBundle", bundle);
            timer.cancel();
            startActivity(intent);
            finish();
        }


    }

    public  void gameLv7(){
        randomAnsC = randomNum(4);
        randomChoiceC = randomNum(numCircleC);
        do {
            randomFakeC = randomNum(numCircleC);
        } while (randomFakeC == randomChoiceC);

        for (int i = 0; i < 4; i++) {
            if (i == randomAnsC) {

                choiceC[i].setImageResource(circle[randomChoiceC][randomNum(numGroupC)][randomNum(numPicC)]);
            } else {

                choiceC[i].setImageResource(circle[randomFakeC][randomNum(numGroupC)][randomNum(numPicC)]);
            }
        }

        if (scoreNumC == 15) {
            checkClear = 1;
            Bundle bundle = new Bundle();
            bundle.putInt("clear", checkClear);
            bundle.putInt("levelUp", levelGame);
            intent.putExtra("chBundle", bundle);
            timer.cancel();
            startActivity(intent);
            finish();
        }


    }

    public  void gameLv8(){
        randomAnsC = randomNum(4);
        randomChoiceC = randomNum(numCircleC);
        do {
            randomFakeC = randomNum(numCircleC);
        } while (randomFakeC == randomChoiceC);

        for (int i = 0; i < 4; i++) {
            if (i == randomAnsC) {

                choiceC[i].setImageResource(circle[randomChoiceC][randomNum(numGroupC)][randomNum(numPicC)]);
            } else {

                choiceC[i].setImageResource(circle[randomFakeC][randomNum(numGroupC)][randomNum(numPicC)]);
            }
        }

        if (scoreNumC == 20) {
            checkClear = 1;
            Bundle bundle = new Bundle();
            bundle.putInt("clear", checkClear);
            bundle.putInt("levelUp", levelGame);
            intent.putExtra("chBundle", bundle);
            timer.cancel();
            startActivity(intent);
            finish();
        }


    }

    public  void gameLv9(){
        randomAnsC = randomNum(4);
        randomChoiceC = randomNum(numCircleC);
        do {
            randomFakeC = randomNum(numCircleC);
        } while (randomFakeC == randomChoiceC);

        for (int i = 0; i < 4; i++) {
            if (i == randomAnsC) {

                choiceC[i].setImageResource(circle[randomChoiceC][randomNum(numGroupC)][randomNum(numPicC)]);
            } else {

                choiceC[i].setImageResource(circle[randomFakeC][randomNum(numGroupC)][randomNum(numPicC)]);
            }
        }

        if (scoreNumC == 15) {
            checkClear = 1;
            Bundle bundle = new Bundle();
            bundle.putInt("clear", checkClear);
            bundle.putInt("levelUp", levelGame);
            intent.putExtra("chBundle", bundle);
            timer.cancel();
            startActivity(intent);
            finish();
        }


    }

    public  void gameLv10(){
        randomAnsC = randomNum(4);
        randomChoiceC = randomNum(numCircleC);
        do {
            randomFakeC = randomNum(numCircleC);
        } while (randomFakeC == randomChoiceC);

        for (int i = 0; i < 4; i++) {
            if (i == randomAnsC) {

                choiceC[i].setImageResource(circle[randomChoiceC][randomNum(numGroupC)][randomNum(numPicC)]);
            } else {

                choiceC[i].setImageResource(circle[randomFakeC][randomNum(numGroupC)][randomNum(numPicC)]);
            }
        }

        if (scoreNumC == 20) {
            checkClear = 1;
            Bundle bundle = new Bundle();
            bundle.putInt("clear", checkClear);
            bundle.putInt("levelUp", levelGame);
            intent.putExtra("chBundle", bundle);
            timer.cancel();
            startActivity(intent);
            finish();
        }


    }

    public  void gameLv11(){
        randomAnsC = randomNum(4);
        randomChoiceC = randomNum(numCircleC);
        randomChoiceHardC = randomNum(numGroupC);
        do {
            randomFakeC = randomNum(numGroupC);
        } while (randomFakeC == randomChoiceHardC);

        for (int i = 0; i < 4; i++) {
            if (i == randomAnsC) {
                choiceC[i].setImageResource(circle[randomChoiceC][randomChoiceHardC][randomNum(numPicC)]);
            } else {
                choiceC[i].setImageResource(circle[randomChoiceC][randomFakeC][randomNum(numPicC)]);
            }
        }

        if (scoreNumC == 15) {
            checkClear = 1;
            Bundle bundle = new Bundle();
            bundle.putInt("clear", checkClear);
            bundle.putInt("levelUp", levelGame);
            intent.putExtra("chBundle", bundle);
            timer.cancel();
            startActivity(intent);
            finish();
        }


    }

    public  void gameLv12(){
        randomAnsC = randomNum(4);
        randomChoiceC = randomNum(numCircleC);
        randomChoiceHardC = randomNum(numGroupC);
        do {
            randomFakeC = randomNum(numGroupC);
        } while (randomFakeC == randomChoiceHardC);

        for (int i = 0; i < 4; i++) {
            if (i == randomAnsC) {
                choiceC[i].setImageResource(circle[randomChoiceC][randomChoiceHardC][randomNum(numPicC)]);
            } else {
                choiceC[i].setImageResource(circle[randomChoiceC][randomFakeC][randomNum(numPicC)]);
            }
        }

        if (scoreNumC == 20) {
            checkClear = 1;
            Bundle bundle = new Bundle();
            bundle.putInt("clear", checkClear);
            bundle.putInt("levelUp", levelGame);
            intent.putExtra("chBundle", bundle);
            timer.cancel();
            startActivity(intent);
            finish();
        }


    }

    public  void gameLv13(){
        randomAnsC = randomNum(4);
        randomChoiceC = randomNum(numCircleC);
        randomChoiceHardC = randomNum(numGroupC);
        do {
            randomFakeC = randomNum(numGroupC);
        } while (randomFakeC == randomChoiceHardC);

        for (int i = 0; i < 4; i++) {
            if (i == randomAnsC) {
                choiceC[i].setImageResource(circle[randomChoiceC][randomChoiceHardC][randomNum(numPicC)]);
            } else {
                choiceC[i].setImageResource(circle[randomChoiceC][randomFakeC][randomNum(numPicC)]);
            }
        }

        if (scoreNumC == 25) {
            checkClear = 1;
            Bundle bundle = new Bundle();
            bundle.putInt("clear", checkClear);
            bundle.putInt("levelUp", levelGame);
            intent.putExtra("chBundle", bundle);
            timer.cancel();
            startActivity(intent);
            finish();
        }


    }

    public  void gameLv14(){
        randomAnsC = randomNum(4);
        randomChoiceC = randomNum(numCircleC);
        randomChoiceHardC = randomNum(numGroupC);
        do {
            randomFakeC = randomNum(numGroupC);
        } while (randomFakeC == randomChoiceHardC);

        for (int i = 0; i < 4; i++) {
            if (i == randomAnsC) {
                choiceC[i].setImageResource(circle[randomChoiceC][randomChoiceHardC][randomNum(numPicC)]);
            } else {
                choiceC[i].setImageResource(circle[randomChoiceC][randomFakeC][randomNum(numPicC)]);
            }
        }

        if (scoreNumC == 30) {
            checkClear = 1;
            Bundle bundle = new Bundle();
            bundle.putInt("clear", checkClear);
            bundle.putInt("levelUp", levelGame);
            intent.putExtra("chBundle", bundle);
            timer.cancel();
            startActivity(intent);
            finish();
        }


    }

    public  void gameLv15(){
        randomAnsC = randomNum(4);
        randomChoiceC = randomNum(numCircleC);
        randomChoiceHardC = randomNum(numGroupC);
        do {
            randomFakeC = randomNum(numGroupC);
        } while (randomFakeC == randomChoiceHardC);

        for (int i = 0; i < 4; i++) {
            if (i == randomAnsC) {
                choiceC[i].setImageResource(circle[randomChoiceC][randomChoiceHardC][randomNum(numPicC)]);
            } else {
                choiceC[i].setImageResource(circle[randomChoiceC][randomFakeC][randomNum(numPicC)]);
            }
        }

        if (scoreNumC == 15) {
            checkClear = 1;
            Bundle bundle = new Bundle();
            bundle.putInt("clear", checkClear);
            bundle.putInt("levelUp", levelGame);
            intent.putExtra("chBundle", bundle);
            timer.cancel();
            startActivity(intent);
            finish();
        }


    }

    public  void gameLv16(){
        randomAnsC = randomNum(4);
        randomChoiceC = randomNum(numCircleC);
        randomChoiceHardC = randomNum(numGroupC);
        do {
            randomFakeC = randomNum(numGroupC);
        } while (randomFakeC == randomChoiceHardC);

        for (int i = 0; i < 4; i++) {
            if (i == randomAnsC) {
                choiceC[i].setImageResource(circle[randomChoiceC][randomChoiceHardC][randomNum(numPicC)]);
            } else {
                choiceC[i].setImageResource(circle[randomChoiceC][randomFakeC][randomNum(numPicC)]);
            }
        }

        if (scoreNumC == 25) {
            checkClear = 1;
            Bundle bundle = new Bundle();
            bundle.putInt("clear", checkClear);
            bundle.putInt("levelUp", levelGame);
            intent.putExtra("chBundle", bundle);
            timer.cancel();
            startActivity(intent);
            finish();
        }


    }

    public  void gameLv17(){
        randomAnsC = randomNum(4);
        randomChoiceC = randomNum(numCircleC);
        randomChoiceHardC = randomNum(numGroupC);
        do {
            randomFakeC = randomNum(numGroupC);
        } while (randomFakeC == randomChoiceHardC);

        for (int i = 0; i < 4; i++) {
            if (i == randomAnsC) {
                choiceC[i].setImageResource(circle[randomChoiceC][randomChoiceHardC][randomNum(numPicC)]);
            } else {
                choiceC[i].setImageResource(circle[randomChoiceC][randomFakeC][randomNum(numPicC)]);
            }
        }

        if (scoreNumC == 15) {
            checkClear = 1;
            Bundle bundle = new Bundle();
            bundle.putInt("clear", checkClear);
            bundle.putInt("levelUp", levelGame);
            intent.putExtra("chBundle", bundle);
            timer.cancel();
            startActivity(intent);
            finish();
        }


    }

    public  void gameLv18(){
        randomAnsC = randomNum(4);
        randomChoiceC = randomNum(numCircleC);
        randomChoiceHardC = randomNum(numGroupC);
        do {
            randomFakeC = randomNum(numGroupC);
        } while (randomFakeC == randomChoiceHardC);

        for (int i = 0; i < 4; i++) {
            if (i == randomAnsC) {
                choiceC[i].setImageResource(circle[randomChoiceC][randomChoiceHardC][randomNum(numPicC)]);
            } else {
                choiceC[i].setImageResource(circle[randomChoiceC][randomFakeC][randomNum(numPicC)]);
            }
        }

        if (scoreNumC == 20) {
            checkClear = 1;
            Bundle bundle = new Bundle();
            bundle.putInt("clear", checkClear);
            bundle.putInt("levelUp", levelGame);
            intent.putExtra("chBundle", bundle);
            timer.cancel();
            startActivity(intent);
            finish();
        }


    }

    public  void gameLv19(){
        randomAnsC = randomNum(4);
        randomChoiceC = randomNum(numCircleC);
        randomChoiceHardC = randomNum(numGroupC);
        do {
            randomFakeC = randomNum(numGroupC);
        } while (randomFakeC == randomChoiceHardC);

        for (int i = 0; i < 4; i++) {
            if (i == randomAnsC) {
                choiceC[i].setImageResource(circle[randomChoiceC][randomChoiceHardC][randomNum(numPicC)]);
            } else {
                choiceC[i].setImageResource(circle[randomChoiceC][randomFakeC][randomNum(numPicC)]);
            }
        }

        if (scoreNumC == 25) {
            checkClear = 1;
            Bundle bundle = new Bundle();
            bundle.putInt("clear", checkClear);
            bundle.putInt("levelUp", levelGame);
            intent.putExtra("chBundle", bundle);
            timer.cancel();
            startActivity(intent);
            finish();
        }


    }

    public  void gameLv20(){
        randomAnsC = randomNum(4);
        randomChoiceC = randomNum(numCircleC);
        randomChoiceHardC = randomNum(numGroupC);
        do {
            randomFakeC = randomNum(numGroupC);
        } while (randomFakeC == randomChoiceHardC);

        for (int i = 0; i < 4; i++) {
            if (i == randomAnsC) {
                choiceC[i].setImageResource(circle[randomChoiceC][randomChoiceHardC][randomNum(numPicC)]);
            } else {
                choiceC[i].setImageResource(circle[randomChoiceC][randomFakeC][randomNum(numPicC)]);
            }
        }

        if (scoreNumC == 40) {
            checkClear = 1;
            Bundle bundle = new Bundle();
            bundle.putInt("clear", checkClear);
            bundle.putInt("levelUp", levelGame);
            intent.putExtra("chBundle", bundle);
            timer.cancel();
            startActivity(intent);
            finish();

        }


    }

    private int randomNum(int value) {
        return (int) (Math.random() * value);
    }
}
