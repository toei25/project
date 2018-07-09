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

public class SpellingABCActivity extends AppCompatActivity implements View.OnClickListener {

    SurfaceView camera_detect;
    ImageButton btn_back;
    ImageButton btn_home;
    ImageButton btn_random_abc;
    ImageView gen_img;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    private int gen_num;
    private static int time_check = 0;
    private static int time_stack = 0;
    private static boolean set_stack = false;
    final int RequestCameraPermissionID = 1001;
    public static String receive_value;
    public static String fail_value;
    private String question_code;
    private String question_code2;
    public CountDownTimer cd;
    public CountDownTimer game_run;

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
    protected void onPause() {
        super.onPause();
        cd.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cd.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spelling_abc);
        gen_img = (ImageView)findViewById(R.id.picture_show);
        camera_detect = (SurfaceView)findViewById(R.id.camera_scan);
        btn_random_abc = (ImageButton)findViewById(R.id.btn_random);
        btn_back = (ImageButton)findViewById(R.id.btn_back);
        btn_home = (ImageButton)findViewById(R.id.btn_home);
        btn_random_abc.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        gen_img.setOnClickListener(this);
        btn_home.setOnClickListener(this);
        barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();
        cameraSource = new CameraSource.Builder(this, barcodeDetector).setRequestedPreviewSize(320, 240).build();
        receive_value = "000000";

        camera_detect.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(SpellingABCActivity.this, new String[]{Manifest.permission.CAMERA},RequestCameraPermissionID);
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

        cd = new CountDownTimer(9999000, 2000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {

            }
        }.start();

    }

    @Override
    public void onClick(View v) {
        if (v == btn_back){
            intent = new Intent(this, VocabActivity.class);
            startActivity(intent);
            finish();
        } else if (v == btn_random_abc){

            playRandom();

        } else if (v == gen_img){

            playQuest();

        } else if (v == btn_home){
            intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void playRandom() {
        fail_value = "000000";
        receive_value = "000000";
        time_check = 0;
        gen_num = (int)(Math.random() * 26);
        playQuest();

        game_run = new CountDownTimer(9999000, 500) {
            @Override
            public void onTick(long millisUntilFinished) {

                if (receive_value.equalsIgnoreCase("000000") || question_code.equalsIgnoreCase("000000")|| receive_value.equalsIgnoreCase(fail_value)){
                    time_check++;
                }else {
                    checkAnswer();
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();

    }

    private void playQuest() {
        switch (gen_num){
            case 0 : gen_img.setImageDrawable(getDrawable(R.drawable.a_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.a_ch);
                mp.start();
                question_code = "050001";
                question_code2 = "150001";
                break;
            case 1 : gen_img.setImageDrawable(getDrawable(R.drawable.b_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.b_ch);
                mp.start();
                question_code = "050002";
                question_code2 = "150002";
                break;
            case 2 : gen_img.setImageDrawable(getDrawable(R.drawable.c_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.c_ch);
                mp.start();
                question_code = "050003";
                question_code2 = "150003";
                break;
            case 3 : gen_img.setImageDrawable(getDrawable(R.drawable.d_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.d_ch);
                mp.start();
                question_code = "050004";
                question_code2 = "150004";
                break;
            case 4 : gen_img.setImageDrawable(getDrawable(R.drawable.e_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.e_ch);
                mp.start();
                question_code = "050005";
                question_code2 = "150005";
                break;
            case 5 : gen_img.setImageDrawable(getDrawable(R.drawable.f_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.f_ch);
                mp.start();
                question_code = "050006";
                question_code2 = "150006";
                break;
            case 6 : gen_img.setImageDrawable(getDrawable(R.drawable.g_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.g_ch);
                mp.start();
                question_code = "050007";
                question_code2 = "150007";
                break;
            case 7 : gen_img.setImageDrawable(getDrawable(R.drawable.h_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.h_ch);
                mp.start();
                question_code = "050008";
                question_code2 = "150008";
                break;
            case 8 : gen_img.setImageDrawable(getDrawable(R.drawable.i_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.i_ch);
                mp.start();
                question_code = "050009";
                question_code2 = "150009";
                break;
            case 9 : gen_img.setImageDrawable(getDrawable(R.drawable.j_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.j_ch);
                mp.start();
                question_code = "050010";
                question_code2 = "150010";
                break;
            case 10 : gen_img.setImageDrawable(getDrawable(R.drawable.k_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.k_ch);
                mp.start();
                question_code = "050011";
                question_code2 = "150011";
                break;
            case 11 : gen_img.setImageDrawable(getDrawable(R.drawable.l_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.l_ch);
                mp.start();
                question_code = "050012";
                question_code2 = "150012";
                break;
            case 12 : gen_img.setImageDrawable(getDrawable(R.drawable.m_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.m_ch);
                mp.start();
                question_code = "050013";
                question_code2 = "150013";
                break;
            case 13 : gen_img.setImageDrawable(getDrawable(R.drawable.n_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.n_ch);
                mp.start();
                question_code = "050014";
                question_code2 = "150014";
                break;
            case 14 : gen_img.setImageDrawable(getDrawable(R.drawable.o_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.o_ch);
                mp.start();
                question_code = "050015";
                question_code2 = "150015";
                break;
            case 15 : gen_img.setImageDrawable(getDrawable(R.drawable.p_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.b_ch);
                mp.start();
                question_code = "050016";
                question_code2 = "150016";
                break;
            case 16 : gen_img.setImageDrawable(getDrawable(R.drawable.q_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.q_ch);
                mp.start();
                question_code = "050017";
                question_code2 = "150017";
                break;
            case 17 : gen_img.setImageDrawable(getDrawable(R.drawable.r_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.r_ch);
                mp.start();
                question_code = "050018";
                question_code2 = "150018";
                break;
            case 18 : gen_img.setImageDrawable(getDrawable(R.drawable.s_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.s_ch);
                mp.start();
                question_code = "050019";
                question_code2 = "150019";
                break;
            case 19 : gen_img.setImageDrawable(getDrawable(R.drawable.t_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.t_ch);
                mp.start();
                question_code = "050020";
                question_code2 = "150020";
                break;
            case 20 : gen_img.setImageDrawable(getDrawable(R.drawable.u_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.u_ch);
                mp.start();
                question_code = "050021";
                question_code2 = "150021";
                break;
            case 21 : gen_img.setImageDrawable(getDrawable(R.drawable.v_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.v_ch);
                mp.start();
                question_code = "050022";
                question_code2 = "150022";
                break;
            case 22 : gen_img.setImageDrawable(getDrawable(R.drawable.w_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.w_ch);
                mp.start();
                question_code = "050023";
                question_code2 = "150023";
                break;
            case 23 : gen_img.setImageDrawable(getDrawable(R.drawable.x_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.x_ch);
                mp.start();
                question_code = "050024";
                question_code2 = "150024";
                break;
            case 24 : gen_img.setImageDrawable(getDrawable(R.drawable.y_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.y_ch);
                mp.start();
                question_code = "050025";
                question_code2 = "150025";
                break;
            case 25 : gen_img.setImageDrawable(getDrawable(R.drawable.z_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.z_ch);
                mp.start();
                question_code = "050026";
                question_code2 = "150026";
                break;
            default: gen_img.setImageDrawable(getDrawable(R.mipmap.ic_launcher));

        }
    }

    private void checkAnswer() {

        if (receive_value.equalsIgnoreCase(question_code) || receive_value.equalsIgnoreCase(question_code2)){
            receive_value = "000000";
            question_code = "000000";
            question_code2 = "000000";
            mp = MediaPlayer.create(getApplicationContext(), R.raw.exc);
            mp.start();
            game_run.cancel();

        } else{
            fail_value = receive_value;
            receive_value = "000000";
            mp = MediaPlayer.create(getApplicationContext(), R.raw.try_again);
            mp.start();

        }

    }
}
