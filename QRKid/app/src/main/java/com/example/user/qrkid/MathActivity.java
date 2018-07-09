package com.example.user.qrkid;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class MathActivity extends AppCompatActivity implements View.OnClickListener {
    SurfaceView camera_detect;
    ImageButton btn_back,btn_random_equation,btn_home;
    TextView first_num, second_num, op_function, answer_num;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;
    public int num_st, num_nd, num_ans, op_syn, num_q, stack_num;
    public static int stack_ans = 0;
    public static int stack_repeat = 0;
    public String ans_str = "";
    public String ans_ch;
    protected String[] code_lib = new String[]{"040010","040001","040002","040003","040004","040005","040006","040007","040008","040009","140010","140001","140002","140003","140004","140005","140006","140007","140008","140009"};
    private static int stack_equa = 0;
    private boolean play_equa_run = false;
    private boolean other_code = false;
    CountDownTimer answer_run, equa_run, wait_med;
    public static String receive_value;
    public static String repeat_value;
    Intent intent;
    MediaPlayer mp;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                            return;
                        }
                        cameraSource.start(camera_detect.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math);
        camera_detect = (SurfaceView)findViewById(R.id.camera_scan);
        btn_back = (ImageButton)findViewById(R.id.btn_back);
        btn_random_equation = (ImageButton)findViewById(R.id.btn_gen_question);
        btn_home = (ImageButton)findViewById(R.id.btn_home);
        first_num = (TextView)findViewById(R.id.num_one);
        second_num = (TextView)findViewById(R.id.num_two);
        op_function = (TextView)findViewById(R.id.op_syntax);
        answer_num = (TextView)findViewById(R.id.ans_num);

        barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();
        cameraSource = new CameraSource.Builder(this, barcodeDetector).setRequestedPreviewSize(320, 240).build();
        receive_value = "000000";
        btn_back.setOnClickListener(this);
        btn_random_equation.setOnClickListener(this);
        btn_home.setOnClickListener(this);

        camera_detect.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(MathActivity.this, new String[]{Manifest.permission.CAMERA},RequestCameraPermissionID);
                        return;
                    }
                    cameraSource.start(camera_detect.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

                cameraSource.stop();

            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrcodes = detections.getDetectedItems();

                if (qrcodes.size() != 0){
                    receive_value = qrcodes.valueAt(0).displayValue;
                }
            }
        });

    }

    @Override
    public void onClick(View v) {

        if (v == btn_back){
            intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
            finish();
        } else if (v == btn_home){
            intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
            finish();
        } else if (v == btn_random_equation){
            if (play_equa_run){
                stack_equa = 0;
                equa_run.cancel();
            }
            randomEquation();
        }

    }

    private void randomEquation() {
        stack_ans = 0;
        do {
            num_st = (int)(Math.random() * 10);
            num_nd = (int)(Math.random() * 10);
            op_syn = (int)(Math.random() * 2);
            if (op_syn == 1){
                num_q = num_st + num_nd;
            } else {
                num_q = num_st - num_nd;
            }
        } while (num_q < 0);

        first_num.setText(String.valueOf(num_st));
        second_num.setText(String.valueOf(num_nd));
        if (op_syn == 1){
            op_function.setText(" + ");
            op_function.setTextSize(33);
        } else {
            op_function.setText(" - ");
            op_function.setTextSize(45);
        }

        if (num_q >= 10){
            stack_num = 2;
        } else {
            stack_num = 1;
        }

        answer_num.setText("__");

        stepRunEquation();


    }

    private void stepRunEquation() {

        play_equa_run = true;

        equa_run = new CountDownTimer(3900, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                playNum();
            }

            @Override
            public void onFinish() {

                stack_equa = 0;
                play_equa_run = false;
                answerTime();

            }
        }.start();


    }

    private void playNum() {
        switch (stack_equa){
            case 0 : decodeEqua(num_st);
                break;
            case 1 : decodeOp(op_syn);
                break;
            case 2 : decodeEqua(num_nd);
        }
        stack_equa++;
    }

    private void decodeOp(int op_set) {
        switch (op_set){
            case 0: mp = MediaPlayer.create(getApplicationContext(), R.raw.minus_n);
                break;
            case 1: mp = MediaPlayer.create(getApplicationContext(), R.raw.plus_n);
                break;
        }
        mp.start();
    }

    private void decodeEqua(int num_set) {
        switch (num_set){
            case 0: mp = MediaPlayer.create(getApplicationContext(), R.raw.zero_n);
                break;
            case 1: mp = MediaPlayer.create(getApplicationContext(), R.raw.one_n);
                break;
            case 2: mp = MediaPlayer.create(getApplicationContext(), R.raw.two_n);
                break;
            case 3: mp = MediaPlayer.create(getApplicationContext(), R.raw.three_n);
                break;
            case 4: mp = MediaPlayer.create(getApplicationContext(), R.raw.four_n);
                break;
            case 5: mp = MediaPlayer.create(getApplicationContext(), R.raw.five_n);
                break;
            case 6: mp = MediaPlayer.create(getApplicationContext(), R.raw.six_n);
                break;
            case 7: mp = MediaPlayer.create(getApplicationContext(), R.raw.seven_n);
                break;
            case 8: mp = MediaPlayer.create(getApplicationContext(), R.raw.eight_n);
                break;
            case 9: mp = MediaPlayer.create(getApplicationContext(), R.raw.nine_n);
                break;
        }
        mp.start();

    }

    private void answerTime() {
        repeat_value = "000000";
        receive_value = "000000";

        answer_run = new CountDownTimer(999999000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (!receive_value.equalsIgnoreCase("000000") && !receive_value.equalsIgnoreCase(repeat_value)){

                    for (int i = 0;i <= 19; i++){
                        if (receive_value.equalsIgnoreCase(code_lib[i])){
                            other_code = true;
                        }
                    }

                    if (other_code){
                        pushAnswer();
                        if (stack_num == stack_ans){
                            checkAns();
                        }
                        other_code = false;
                    } else {
                        diffCode();
                    }



                }

                if (stack_num == 2){
                    stack_repeat++;
                    if (stack_repeat == 6){
                        repeat_value = "000000";
                        receive_value = "000000";
                        stack_repeat = 0;
                    }
                }

            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    private void checkAns() {
        num_ans = Integer.parseInt(ans_str);
        decodeAns();
        if (num_ans == num_q){
            mp = MediaPlayer.create(getApplicationContext(), R.raw.exc);
            mp.start();
            receive_value = "000000";
            repeat_value = "000000";
            ans_str = "";
            stack_ans = 0;
            stack_num = 0;
            answer_run.cancel();
            wait_med = new CountDownTimer(5000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {

                    randomEquation();

                }
            }.start();
        } else {
            mp = MediaPlayer.create(getApplicationContext(), R.raw.try_again);
            mp.start();
            receive_value = "000000";
            stack_ans = 0;
            ans_str = "";
        }
    }

    private void decodeAns() {
        num_ans = Integer.parseInt(ans_str);
        switch (num_ans){
            case 0: mp = MediaPlayer.create(getApplicationContext(), R.raw.zero_n);
                break;
            case 1: mp = MediaPlayer.create(getApplicationContext(), R.raw.one_n);
                break;
            case 2: mp = MediaPlayer.create(getApplicationContext(), R.raw.two_n);
                break;
            case 3: mp = MediaPlayer.create(getApplicationContext(), R.raw.three_n);
                break;
            case 4: mp = MediaPlayer.create(getApplicationContext(), R.raw.four_n);
                break;
            case 5: mp = MediaPlayer.create(getApplicationContext(), R.raw.five_n);
                break;
            case 6: mp = MediaPlayer.create(getApplicationContext(), R.raw.six_n);
                break;
            case 7: mp = MediaPlayer.create(getApplicationContext(), R.raw.seven_n);
                break;
            case 8: mp = MediaPlayer.create(getApplicationContext(), R.raw.eight_n);
                break;
            case 9: mp = MediaPlayer.create(getApplicationContext(), R.raw.nine_n);
                break;
            case 10: mp = MediaPlayer.create(getApplicationContext(), R.raw.ten_n);
                break;
            case 11: mp = MediaPlayer.create(getApplicationContext(), R.raw.eleven_n);
                break;
            case 12: mp = MediaPlayer.create(getApplicationContext(), R.raw.twelve_n);
                break;
            case 13: mp = MediaPlayer.create(getApplicationContext(), R.raw.thirteen_n);
                break;
            case 14: mp = MediaPlayer.create(getApplicationContext(), R.raw.fourteen_n);
                break;
            case 15: mp = MediaPlayer.create(getApplicationContext(), R.raw.fifteen_n);
                break;
            case 16: mp = MediaPlayer.create(getApplicationContext(), R.raw.six_n);
                break;
            case 17: mp = MediaPlayer.create(getApplicationContext(), R.raw.seventeen_n);
                break;
            case 18: mp = MediaPlayer.create(getApplicationContext(), R.raw.eighteen_n);
                break;
        }
        mp.start();
    }

    private void pushAnswer() {
        switch (receive_value){
            case "040010": ans_ch = "0";
                break;
            case "040001": ans_ch = "1";
                break;
            case "040002": ans_ch = "2";
                break;
            case "040003": ans_ch = "3";
                break;
            case "040004": ans_ch = "4";
                break;
            case "040005": ans_ch = "5";
                break;
            case "040006": ans_ch = "6";
                break;
            case "040007": ans_ch = "7";
                break;
            case "040008": ans_ch = "8";
                break;
            case "040009": ans_ch = "9";
                break;
            case "140010": ans_ch = "0";
                break;
            case "140001": ans_ch = "1";
                break;
            case "140002": ans_ch = "2";
                break;
            case "140003": ans_ch = "3";
                break;
            case "140004": ans_ch = "4";
                break;
            case "140005": ans_ch = "5";
                break;
            case "140006": ans_ch = "6";
                break;
            case "140007": ans_ch = "7";
                break;
            case "140008": ans_ch = "8";
                break;
            case "140009": ans_ch = "9";
                break;
        }
        ans_str = ans_str + ans_ch;
        answer_num.setText(ans_str);
        decodeAns();
        repeat_value = receive_value;
        receive_value = "000000";
        stack_ans++;
    }

    private void diffCode() {
        repeat_value = receive_value;
        receive_value = "000000";
        mp = MediaPlayer.create(getApplicationContext(), R.raw.icr_ch);
        mp.start();
    }
}
