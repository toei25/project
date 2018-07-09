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
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class QuizMelodyActivity extends AppCompatActivity implements View.OnClickListener {

    SurfaceView camera_stack;
    ImageButton btn_back, btn_gen_melody;
    ImageButton btn_rerun;
    ImageButton btn_home;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    ImageView left_note, right_note;
    final int RequestCameraPermissionID = 1001;
    public static String receive_value;
    public CountDownTimer cd;

    public CountDownTimer run_melody;
    public CountDownTimer run_game;
    Intent intent;
    protected int gen_img_num, gen_img_old = 8;
    protected static String repeat_value = "000000";
    private static int score = 0;
    private static int stack = 0;
    private static int ans_stack = 0;
    protected boolean question_run = false;
    protected boolean melody_run = false;
    protected int[] num_melody = new int[2];
    protected static int[] question;
    protected static int[] ans_melody;
    protected int[] img_melody = new int[]{R.drawable.violet_nt,R.drawable.indigo_nt,R.drawable.blue_nt,R.drawable.green_nt,R.drawable.yellow_nt,R.drawable.orange_nt,R.drawable.red_nt};
    protected ImageView[] disp_img;

    MediaPlayer mp;

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                            return;
                        }
                        cameraSource.start(camera_stack.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
        }
    }



    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_melody);
        score = 0;
        left_note = (ImageView)findViewById(R.id.left_nt);
        right_note = (ImageView)findViewById(R.id.right_nt);
        disp_img = new ImageView[]{left_note,right_note};
        for (int i = 0;i < 2;i++){
            do {
                gen_img_num = (int)(Math.random() * 7);
            }while (gen_img_num == gen_img_old);
            disp_img[i].setImageDrawable(getDrawable(img_melody[gen_img_num]));
            num_melody[i] = gen_img_num;
            gen_img_old = gen_img_num;
        }
        camera_stack = (SurfaceView)findViewById(R.id.camera_stack);
        btn_back = (ImageButton)findViewById(R.id.btn_back);
        btn_rerun = (ImageButton)findViewById(R.id.rerun_nt);
        btn_home = (ImageButton)findViewById(R.id.btn_home);
        btn_gen_melody = (ImageButton)findViewById(R.id.btn_random_melody);
        btn_gen_melody.setOnClickListener(this);
        btn_rerun.setOnClickListener(this);
        btn_home.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        receive_value = "000000";
        barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();
        cameraSource = new CameraSource.Builder(this, barcodeDetector).setRequestedPreviewSize(480, 320).build();

        camera_stack.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(QuizMelodyActivity.this, new String[]{Manifest.permission.CAMERA},RequestCameraPermissionID);
                        return;
                    }
                    cameraSource.start(camera_stack.getHolder());
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
            intent = new Intent(this, MusicActivity.class);
            startActivity(intent);
            finish();
        } else if (v == btn_gen_melody){
            stack = 0;
            if (question_run){
                run_game.cancel();
            } else if (melody_run){
                run_melody.cancel();
            }
            randomNote();
        } else if (v == btn_rerun){
            stack = 0;
            if (melody_run){
                run_melody.cancel();
            }
            stepPlay();
        } else if (v == btn_home){
            intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private void randomNote() {

        question = new int[score + 1];
        for (int i = 0; i < (score + 1);i++){
            int set = (int)(Math.random() * 2);
            question[i] = num_melody[set];
        }

        stepPlay();

    }

    private void stepPlay() {
        melody_run = true;
        run_melody = new CountDownTimer(((score + 1) * 500) + 10, 500) {
            @Override
            public void onTick(long millisUntilFinished) {

                playSong();


            }

            @Override
            public void onFinish() {
                stack = 0;
                melody_run = false;
                gameStart();
                disp_img[0].setImageDrawable(getDrawable(R.drawable.blank_nt));
                disp_img[1].setImageDrawable(getDrawable(R.drawable.blank_nt));
            }
        }.start();
    }

    private void gameStart() {

        receive_value = "000000";
        ans_melody = new int[score + 1];
        question_run = true;
        run_game = new CountDownTimer(99990000, 2000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (!receive_value.equalsIgnoreCase("000000") && !repeat_value.equalsIgnoreCase(receive_value)){
                    pushMelody();
                    if ((ans_stack) == (score + 1)){
                        checkAns();
                    }
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();

    }

    private void checkAns() {
        for (int i = 0;i < ans_stack ;i++){
            if (question[i] == ans_melody[i]){
                if (i == (score)){
                    mp = MediaPlayer.create(getApplicationContext(), R.raw.exc);
                    mp.start();
                    ans_stack = 0;
                    receive_value = "000000";
                    score++;
                    run_game.cancel();
                    break;
                }
            } else {
                receive_value = "000000";
                mp = MediaPlayer.create(getApplicationContext(), R.raw.try_again);
                mp.start();
                ans_stack = 0;
                break;
            }

        }


    }

    private void pushMelody() {
        switch (receive_value){
            case "030001" : mp = MediaPlayer.create(getApplicationContext(),R.raw.c_nt);
                ans_melody[ans_stack] = 0;
                break;
            case "030002" : mp = MediaPlayer.create(getApplicationContext(),R.raw.d_nt);
                ans_melody[ans_stack] = 1;
                break;
            case "030003" : mp = MediaPlayer.create(getApplicationContext(),R.raw.e_nt);
                ans_melody[ans_stack] = 2;
                break;
            case "030004" : mp = MediaPlayer.create(getApplicationContext(),R.raw.f_nt);
                ans_melody[ans_stack] = 3;
                break;
            case "030005" : mp = MediaPlayer.create(getApplicationContext(),R.raw.g_nt);
                ans_melody[ans_stack] = 4;
                break;
            case "030006" : mp = MediaPlayer.create(getApplicationContext(),R.raw.a_nt);
                ans_melody[ans_stack] = 5;
                break;
            case "030007" : mp = MediaPlayer.create(getApplicationContext(),R.raw.b_nt);
                ans_melody[ans_stack] = 6;
                break;
            case "130001" : mp = MediaPlayer.create(getApplicationContext(),R.raw.c_nt);
                ans_melody[ans_stack] = 0;
                break;
            case "130002" : mp = MediaPlayer.create(getApplicationContext(),R.raw.d_nt);
                ans_melody[ans_stack] = 1;
                break;
            case "130003" : mp = MediaPlayer.create(getApplicationContext(),R.raw.e_nt);
                ans_melody[ans_stack] = 2;
                break;
            case "130004" : mp = MediaPlayer.create(getApplicationContext(),R.raw.f_nt);
                ans_melody[ans_stack] = 3;
                break;
            case "130005" : mp = MediaPlayer.create(getApplicationContext(),R.raw.g_nt);
                ans_melody[ans_stack] = 4;
                break;
            case "130006" : mp = MediaPlayer.create(getApplicationContext(),R.raw.a_nt);
                ans_melody[ans_stack] = 5;
                break;
            case "130007" : mp = MediaPlayer.create(getApplicationContext(),R.raw.b_nt);
                ans_melody[ans_stack] = 6;
                break;
        }
        repeat_value = receive_value;
        receive_value = "000000";
        mp.start();
        ans_stack++;

    }

    private void playSong() {

        if (question[stack] == num_melody[0]){
            disp_img[0].setImageDrawable(getDrawable(img_melody[num_melody[0]]));
            disp_img[1].setImageDrawable(getDrawable(R.drawable.blank_nt));
        } else if(question[stack] == num_melody[1]) {
            disp_img[0].setImageDrawable(getDrawable(R.drawable.blank_nt));
            disp_img[1].setImageDrawable(getDrawable(img_melody[num_melody[1]]));
        }

        switch (question[stack]){
            case 0: mp = MediaPlayer.create(getApplicationContext(),R.raw.c_nt);
                break;
            case 1: mp = MediaPlayer.create(getApplicationContext(),R.raw.d_nt);
                break;
            case 2: mp = MediaPlayer.create(getApplicationContext(),R.raw.e_nt);
                break;
            case 3: mp = MediaPlayer.create(getApplicationContext(),R.raw.f_nt);
                break;
            case 4: mp = MediaPlayer.create(getApplicationContext(),R.raw.g_nt);
                break;
            case 5: mp = MediaPlayer.create(getApplicationContext(),R.raw.a_nt);
                break;
            case 6: mp = MediaPlayer.create(getApplicationContext(),R.raw.b_nt);
                break;
        }


        mp.start();
        stack++;
    }


}
