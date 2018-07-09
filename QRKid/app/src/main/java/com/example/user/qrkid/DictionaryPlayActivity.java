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

public class DictionaryPlayActivity extends AppCompatActivity implements View.OnClickListener {

    SurfaceView camera_decode;
    ImageButton btn_back;
    ImageButton btn_random_vocab;
    ImageButton btn_home;
    ImageView gen_img;
    TextView txt_display;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    private int gen_num;
    private static int stack_vocab = 0;
    private static int time_stack = 0;
    private static boolean set_stack = false;
    final int RequestCameraPermissionID = 1001;
    public static String receive_value;
    public static String repeat_value;
    private String question_vocab;

    private String disp_vocab;
    private String space_vocab;
    private String hint_vocab;
    private String answer_vocab = "";
    private String answer_decode;
    private String character_three = "   _ _ _   ";
    private String character_four = "  _ _ _ _  ";
    private String character_five = "_ _ _ _ _";
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
                        cameraSource.start(camera_decode.getHolder());
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
        setContentView(R.layout.activity_dictionary_play);
        txt_display = (TextView)findViewById(R.id.txt_disp);
        gen_img = (ImageView)findViewById(R.id.img_question);
        camera_decode = (SurfaceView)findViewById(R.id.camera_detect);
        btn_random_vocab = (ImageButton)findViewById(R.id.btn_gen_vocab);
        btn_back = (ImageButton)findViewById(R.id.btn_back);
        btn_home = (ImageButton)findViewById(R.id.btn_home);
        btn_random_vocab.setOnClickListener(this);
        btn_home.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();
        cameraSource = new CameraSource.Builder(this, barcodeDetector).setRequestedPreviewSize(480, 320).build();
        receive_value = "000000";

        camera_decode.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(DictionaryPlayActivity.this, new String[]{Manifest.permission.CAMERA},RequestCameraPermissionID);
                        return;
                    }
                    cameraSource.start(camera_decode.getHolder());
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
        } else if (v == btn_random_vocab){

//            if (set_stack){
//                game_run.cancel();
//                cd.cancel();
//                set_stack = false;
//            }

            playDictionary();

        } else if (v == btn_home){
            intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private void playDictionary() {
        set_stack = true;
        hint_vocab = "";
        disp_vocab = "";
        answer_vocab = "";
        repeat_value = "000000";
        receive_value = "000000";
        stack_vocab = 0;
        gen_num = (int)(Math.random() * 26);
        switch (gen_num){
            case 0 : gen_img.setImageDrawable(getDrawable(R.drawable.ant_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.ant);
                mp.start();
                question_vocab = "ANT";
                break;
            case 1 : gen_img.setImageDrawable(getDrawable(R.drawable.bat_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.bat);
                mp.start();
                question_vocab = "BAT";
                break;
            case 2 : gen_img.setImageDrawable(getDrawable(R.drawable.bee_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.bee);
                mp.start();
                question_vocab = "BEE";
                break;
            case 3 : gen_img.setImageDrawable(getDrawable(R.drawable.cat_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.cat);
                mp.start();
                question_vocab = "CAT";
                break;
            case 4 : gen_img.setImageDrawable(getDrawable(R.drawable.cow_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.cow);
                mp.start();
                question_vocab = "COW";
                break;
            case 5 : gen_img.setImageDrawable(getDrawable(R.drawable.dog_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.dog);
                mp.start();
                question_vocab = "DOG";
                break;
            case 6 : gen_img.setImageDrawable(getDrawable(R.drawable.fox_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.fox);
                mp.start();
                question_vocab = "FOX";
                break;
            case 7 : gen_img.setImageDrawable(getDrawable(R.drawable.one_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.one_n);
                mp.start();
                question_vocab = "ONE";
                break;
            case 8 : gen_img.setImageDrawable(getDrawable(R.drawable.owl_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.owl);
                mp.start();
                question_vocab = "OWL";
                break;
            case 9 : gen_img.setImageDrawable(getDrawable(R.drawable.six_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.six_n);
                mp.start();
                question_vocab = "SIX";
                break;
            case 10 : gen_img.setImageDrawable(getDrawable(R.drawable.ten_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.ten_n);
                mp.start();
                question_vocab = "TEN";
                break;
            case 11 : gen_img.setImageDrawable(getDrawable(R.drawable.two_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.two_n);
                mp.start();
                question_vocab = "TWO";
                break;
            case 12 : gen_img.setImageDrawable(getDrawable(R.drawable.bear_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.bear);
                mp.start();
                question_vocab = "BEAR";
                break;
            case 13 : gen_img.setImageDrawable(getDrawable(R.drawable.bird_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.bird);
                mp.start();
                question_vocab = "BIRD";
                break;
            case 14 : gen_img.setImageDrawable(getDrawable(R.drawable.fish_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.fish);
                mp.start();
                question_vocab = "FISH";
                break;
            case 15 : gen_img.setImageDrawable(getDrawable(R.drawable.five_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.five_n);
                mp.start();
                question_vocab = "FIVE";
                break;
            case 16 : gen_img.setImageDrawable(getDrawable(R.drawable.four_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.four_n);
                mp.start();
                question_vocab = "FOUR";
                break;
            case 17 : gen_img.setImageDrawable(getDrawable(R.drawable.goat_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.goat);
                mp.start();
                question_vocab = "GOAT";
                break;
            case 18 : gen_img.setImageDrawable(getDrawable(R.drawable.lion_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.lion);
                mp.start();
                question_vocab = "LION";
                break;
            case 19 : gen_img.setImageDrawable(getDrawable(R.drawable.nine_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.nine_n);
                mp.start();
                question_vocab = "NINE";
                break;
            case 20 : gen_img.setImageDrawable(getDrawable(R.drawable.eight_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.eight_n);
                mp.start();
                question_vocab = "EIGHT";
                break;
            case 21 : gen_img.setImageDrawable(getDrawable(R.drawable.seven_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.seven_n);
                mp.start();
                question_vocab = "SEVEN";
                break;
            case 22 : gen_img.setImageDrawable(getDrawable(R.drawable.sheep_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.sheep);
                mp.start();
                question_vocab = "SHEEP";
                break;
            case 23 : gen_img.setImageDrawable(getDrawable(R.drawable.three_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.three_n);
                mp.start();
                question_vocab = "THREE";
                break;
            case 24 : gen_img.setImageDrawable(getDrawable(R.drawable.tiger_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.tiger);
                mp.start();
                question_vocab = "TIGER";
                break;
            case 25 : gen_img.setImageDrawable(getDrawable(R.drawable.zebra_pic));
                mp = MediaPlayer.create(getApplicationContext(), R.raw.zebra);
                mp.start();
                question_vocab = "ZEBRA";
                break;
            default: gen_img.setImageDrawable(getDrawable(R.mipmap.ic_launcher));

        }

        if (gen_num < 12) {
            txt_display.setText(character_three);
            hint_vocab = character_three;
            gameThree();
        } else if (gen_num >= 12 && gen_num < 20){
            txt_display.setText(character_four);
            hint_vocab = character_four;
            gameFour();
        } else {
            txt_display.setText(character_five);
            hint_vocab = character_five;
            gameFive();
        }

    }

    private void gameThree() {
        game_run = new CountDownTimer(99999000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (receive_value.equalsIgnoreCase("000000") || question_vocab.equalsIgnoreCase("XXX") || repeat_value.equalsIgnoreCase(receive_value)){
                    time_stack++;
                } else {
                    decodeAns(receive_value);
                    pushAnsThree();
                    if (stack_vocab == 3){
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
        if (question_vocab.equalsIgnoreCase(answer_vocab)){
            mp = MediaPlayer.create(getApplicationContext(), R.raw.exc);
            mp.start();
            game_run.cancel();
            set_stack = false;
            question_vocab = "XXX";
        } else {
            mp = MediaPlayer.create(getApplicationContext(), R.raw.try_again);
            mp.start();
            stack_vocab = 0;
            answer_vocab = "";
        }
    }

    private void decodeAns(String receive_value) {
        repeat_value = receive_value;
        switch (receive_value) {
            case "050001": mp = MediaPlayer.create(getApplicationContext(), R.raw.a_ch);
                mp.start();
                answer_decode = "A";
                break;
            case "050002": mp = MediaPlayer.create(getApplicationContext(), R.raw.b_ch);
                mp.start();
                answer_decode = "B";
                break;
            case "050003": mp = MediaPlayer.create(getApplicationContext(), R.raw.c_ch);
                mp.start();
                answer_decode = "C";
                break;
            case "050004": mp = MediaPlayer.create(getApplicationContext(), R.raw.d_ch);
                mp.start();
                answer_decode = "D";
                break;
            case "050005": mp = MediaPlayer.create(getApplicationContext(), R.raw.e_ch);
                mp.start();
                answer_decode = "E";
                break;
            case "050006": mp = MediaPlayer.create(getApplicationContext(), R.raw.f_ch);
                mp.start();
                answer_decode = "F";
                break;
            case "050007": mp = MediaPlayer.create(getApplicationContext(), R.raw.g_ch);
                mp.start();
                answer_decode = "G";
                break;
            case "050008": mp = MediaPlayer.create(getApplicationContext(), R.raw.h_ch);
                mp.start();
                answer_decode = "H";
                break;
            case "050009": mp = MediaPlayer.create(getApplicationContext(), R.raw.i_ch);
                mp.start();
                answer_decode = "I";
                break;
            case "050010": mp = MediaPlayer.create(getApplicationContext(), R.raw.j_ch);
                mp.start();
                answer_decode = "J";
                break;
            case "050011": mp = MediaPlayer.create(getApplicationContext(), R.raw.k_ch);
                mp.start();
                answer_decode = "K";
                break;
            case "050012": mp = MediaPlayer.create(getApplicationContext(), R.raw.l_ch);
                mp.start();
                answer_decode = "L";
                break;
            case "050013": mp = MediaPlayer.create(getApplicationContext(), R.raw.m_ch);
                mp.start();
                answer_decode = "M";
                break;
            case "050014": mp = MediaPlayer.create(getApplicationContext(), R.raw.n_ch);
                mp.start();
                answer_decode = "N";
                break;
            case "050015": mp = MediaPlayer.create(getApplicationContext(), R.raw.o_ch);
                mp.start();
                answer_decode = "O";
                break;
            case "050016": mp = MediaPlayer.create(getApplicationContext(), R.raw.p_ch);
                mp.start();
                answer_decode = "P";
                break;
            case "050017": mp = MediaPlayer.create(getApplicationContext(), R.raw.q_ch);
                mp.start();
                answer_decode = "Q";
                break;
            case "050018": mp = MediaPlayer.create(getApplicationContext(), R.raw.r_ch);
                mp.start();
                answer_decode = "R";
                break;
            case "050019": mp = MediaPlayer.create(getApplicationContext(), R.raw.s_ch);
                mp.start();
                answer_decode = "S";
                break;
            case "050020": mp = MediaPlayer.create(getApplicationContext(), R.raw.t_ch);
                mp.start();
                answer_decode = "T";
                break;
            case "050021": mp = MediaPlayer.create(getApplicationContext(), R.raw.u_ch);
                mp.start();
                answer_decode = "U";
                break;
            case "050022": mp = MediaPlayer.create(getApplicationContext(), R.raw.v_ch);
                mp.start();
                answer_decode = "V";
                break;
            case "050023": mp = MediaPlayer.create(getApplicationContext(), R.raw.w_ch);
                mp.start();
                answer_decode = "W";
                break;
            case "050024": mp = MediaPlayer.create(getApplicationContext(), R.raw.x_ch);
                mp.start();
                answer_decode = "X";
                break;
            case "050025": mp = MediaPlayer.create(getApplicationContext(), R.raw.y_ch);
                mp.start();
                answer_decode = "Y";
                break;
            case "050026": mp = MediaPlayer.create(getApplicationContext(), R.raw.z_ch);
                mp.start();
                answer_decode = "Z";
                break;
            case "150001": mp = MediaPlayer.create(getApplicationContext(), R.raw.a_ch);
                mp.start();
                answer_decode = "A";
                break;
            case "150002": mp = MediaPlayer.create(getApplicationContext(), R.raw.b_ch);
                mp.start();
                answer_decode = "B";
                break;
            case "150003": mp = MediaPlayer.create(getApplicationContext(), R.raw.c_ch);
                mp.start();
                answer_decode = "C";
                break;
            case "150004": mp = MediaPlayer.create(getApplicationContext(), R.raw.d_ch);
                mp.start();
                answer_decode = "D";
                break;
            case "150005": mp = MediaPlayer.create(getApplicationContext(), R.raw.e_ch);
                mp.start();
                answer_decode = "E";
                break;
            case "150006": mp = MediaPlayer.create(getApplicationContext(), R.raw.f_ch);
                mp.start();
                answer_decode = "F";
                break;
            case "150007": mp = MediaPlayer.create(getApplicationContext(), R.raw.g_ch);
                mp.start();
                answer_decode = "G";
                break;
            case "150008": mp = MediaPlayer.create(getApplicationContext(), R.raw.h_ch);
                mp.start();
                answer_decode = "H";
                break;
            case "150009": mp = MediaPlayer.create(getApplicationContext(), R.raw.i_ch);
                mp.start();
                answer_decode = "I";
                break;
            case "150010": mp = MediaPlayer.create(getApplicationContext(), R.raw.j_ch);
                mp.start();
                answer_decode = "J";
                break;
            case "150011": mp = MediaPlayer.create(getApplicationContext(), R.raw.k_ch);
                mp.start();
                answer_decode = "K";
                break;
            case "150012": mp = MediaPlayer.create(getApplicationContext(), R.raw.l_ch);
                mp.start();
                answer_decode = "L";
                break;
            case "150013": mp = MediaPlayer.create(getApplicationContext(), R.raw.m_ch);
                mp.start();
                answer_decode = "M";
                break;
            case "150014": mp = MediaPlayer.create(getApplicationContext(), R.raw.n_ch);
                mp.start();
                answer_decode = "N";
                break;
            case "150015": mp = MediaPlayer.create(getApplicationContext(), R.raw.o_ch);
                mp.start();
                answer_decode = "O";
                break;
            case "150016": mp = MediaPlayer.create(getApplicationContext(), R.raw.p_ch);
                mp.start();
                answer_decode = "P";
                break;
            case "150017": mp = MediaPlayer.create(getApplicationContext(), R.raw.q_ch);
                mp.start();
                answer_decode = "Q";
                break;
            case "150018": mp = MediaPlayer.create(getApplicationContext(), R.raw.r_ch);
                mp.start();
                answer_decode = "R";
                break;
            case "150019": mp = MediaPlayer.create(getApplicationContext(), R.raw.s_ch);
                mp.start();
                answer_decode = "S";
                break;
            case "150020": mp = MediaPlayer.create(getApplicationContext(), R.raw.t_ch);
                mp.start();
                answer_decode = "T";
                break;
            case "150021": mp = MediaPlayer.create(getApplicationContext(), R.raw.u_ch);
                mp.start();
                answer_decode = "U";
                break;
            case "150022": mp = MediaPlayer.create(getApplicationContext(), R.raw.v_ch);
                mp.start();
                answer_decode = "V";
                break;
            case "150023": mp = MediaPlayer.create(getApplicationContext(), R.raw.w_ch);
                mp.start();
                answer_decode = "W";
                break;
            case "150024": mp = MediaPlayer.create(getApplicationContext(), R.raw.x_ch);
                mp.start();
                answer_decode = "X";
                break;
            case "150025": mp = MediaPlayer.create(getApplicationContext(), R.raw.y_ch);
                mp.start();
                answer_decode = "Y";
                break;
            case "150026": mp = MediaPlayer.create(getApplicationContext(), R.raw.z_ch);
                mp.start();
                answer_decode = "Z";
                break;

        }

    }

    private void pushAnsThree() {
        stack_vocab++;
        if (stack_vocab == 1) {
            disp_vocab = answer_decode + " _ _";
            space_vocab = answer_decode + " ";
            txt_display.setText(disp_vocab);
        } else if (stack_vocab == 2){
            disp_vocab = space_vocab + answer_decode + " _";
            txt_display.setText(disp_vocab);
            space_vocab = space_vocab + answer_decode + " ";
        } else if (stack_vocab == 3){
            disp_vocab = space_vocab + answer_decode;
            txt_display.setText(disp_vocab);
        }
        answer_vocab = answer_vocab + answer_decode;

    }

    private void gameFour() {
        game_run = new CountDownTimer(99999000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (receive_value.equalsIgnoreCase("000000") || question_vocab.equalsIgnoreCase("XXX") || repeat_value.equalsIgnoreCase(receive_value)){
                    time_stack++;
                } else {
                    decodeAns(receive_value);
                    pushAnsFour();
                    if (stack_vocab == 4){
                        checkAns();
                    }
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    private void pushAnsFour() {
        stack_vocab++;
        if (stack_vocab == 1) {
            disp_vocab = answer_decode + " _ _ _";
            space_vocab = answer_decode + " ";
            txt_display.setText(disp_vocab);
        } else if (stack_vocab == 2){
            disp_vocab = space_vocab + answer_decode + " _ _";
            txt_display.setText(disp_vocab);
            space_vocab = space_vocab + answer_decode + " ";
        } else if (stack_vocab == 3){
            disp_vocab = space_vocab + answer_decode  + " _";
            txt_display.setText(disp_vocab);
            space_vocab = space_vocab + answer_decode + " ";
        } else if (stack_vocab == 4){
            disp_vocab = space_vocab + answer_decode;
            txt_display.setText(disp_vocab);
        }
        answer_vocab = answer_vocab + answer_decode;

    }

    private void gameFive() {
        game_run = new CountDownTimer(99999000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (receive_value.equalsIgnoreCase("000000") || question_vocab.equalsIgnoreCase("XXX") || repeat_value.equalsIgnoreCase(receive_value)){
                    time_stack++;
                } else {
                    decodeAns(receive_value);
                    pushAnsFive();
                    if (stack_vocab == 5){
                        checkAns();
                    }
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    private void pushAnsFive() {
        stack_vocab++;
        if (stack_vocab == 1) {
            disp_vocab = answer_decode + " _ _ _ _";
            space_vocab = answer_decode + " ";
            txt_display.setText(disp_vocab);
        } else if (stack_vocab == 2){
            disp_vocab = space_vocab + answer_decode + " _ _ _";
            txt_display.setText(disp_vocab);
            space_vocab = space_vocab + answer_decode + " ";
        } else if (stack_vocab == 3){
            disp_vocab = space_vocab + answer_decode  + " _ _";
            txt_display.setText(disp_vocab);
            space_vocab = space_vocab + answer_decode + " ";
        } else if (stack_vocab == 4){
            disp_vocab = space_vocab + answer_decode  + " _";
            txt_display.setText(disp_vocab);
            space_vocab = space_vocab + answer_decode + " ";
        } else if (stack_vocab == 5){
            disp_vocab = space_vocab + answer_decode;
            txt_display.setText(disp_vocab);
        }
        answer_vocab = answer_vocab + answer_decode;
    }
}
