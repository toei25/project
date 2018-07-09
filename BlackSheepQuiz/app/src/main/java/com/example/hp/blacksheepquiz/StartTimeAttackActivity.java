package com.example.hp.blacksheepquiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class StartTimeAttackActivity extends AppCompatActivity implements View.OnClickListener {


    protected TextView countdown, showScore;
    protected int modeGame, scoreNum, randomAns, randomChoice, randomChoiceHard, randomFake, numCircle, numPic, numGroup;
    protected ImageButton[] choice;
    protected GroupChoiceGame groupChoiceGame = new GroupChoiceGame();
    protected final CountdownTime timer = new CountdownTime(100000, 1000);
    int[][][] circle;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_time_attack);
        countdown = (TextView) findViewById(R.id.countdownTimer);
        showScore = (TextView) findViewById(R.id.score);
        Bundle bundle = getIntent().getBundleExtra("cBundle");
        modeGame = bundle.getInt("mode");
        choice = new ImageButton[]{(ImageButton) findViewById(R.id.choice1), (ImageButton) findViewById(R.id.choice2), (ImageButton) findViewById(R.id.choice3), (ImageButton) findViewById(R.id.choice4)};
        circle = groupChoiceGame.circle;
        numCircle = groupChoiceGame.numCircle;
        numGroup = groupChoiceGame.numGroupAll;
        numPic = groupChoiceGame.numPicAll;
        scoreNum = 0;
        for (int i = 0; i < 4; i++) {
            choice[i].setOnClickListener(this);
        }
        timer.start();
        checkMode();
        intent = new Intent(this, ResultScoreActivity.class);

    }

    private void checkMode() {
        if (modeGame == 1) {
            gameStartEasy();
        } else {
            gameStartHard();
        }

    }

    private int randomNum(int value) {
        return (int) (Math.random() * value);
    }

    private void gameStartEasy() {
        randomAns = randomNum(4);
        randomChoice = randomNum(numCircle);
        do {
            randomFake = randomNum(numCircle);
        } while (randomFake == randomChoice);

        for (int i = 0; i < 4; i++) {
            if (i == randomAns) {

                choice[i].setImageResource(circle[randomChoice][randomNum(numGroup)][randomNum(numPic)]);
            } else {

                choice[i].setImageResource(circle[randomFake][randomNum(numGroup)][randomNum(numPic)]);
            }
        }
    }


    private void gameStartHard() {
        randomAns = randomNum(4);
        randomChoice = randomNum(numCircle);
        randomChoiceHard = randomNum(numGroup);
        do {
            randomFake = randomNum(numGroup);
        } while (randomFake == randomChoiceHard);

        for (int i = 0; i < 4; i++) {
            if (i == randomAns) {
                choice[i].setImageResource(circle[randomChoice][randomChoiceHard][randomNum(numPic)]);
            } else {
                choice[i].setImageResource(circle[randomChoice][randomFake][randomNum(numPic)]);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == choice[randomAns]){
            scoreNum++;
            showScore.setText(String.valueOf(scoreNum));
            checkMode();
        } else {
            if (scoreNum <= 0 || scoreNum == 1) {
                scoreNum = 0;
                showScore.setText(String.valueOf(scoreNum));
            } else {
                if (modeGame == 1) {
                    scoreNum--;
                    showScore.setText(String.valueOf(scoreNum));
                } else {
                    scoreNum -= 2;
                    showScore.setText(String.valueOf(scoreNum));
                }
            }

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
            countdown.setText(hms);
        }

        @Override
        public void onFinish() {
            countdown.setText("Time out!");
            Bundle bundle = new Bundle();
            bundle.putInt("score", scoreNum);
            bundle.putInt("modeClear", modeGame);
            intent.putExtra("rBundle", bundle);
            startActivity(intent);
            finish();

        }
    }
}
