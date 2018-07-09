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
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class PlayMelodyActivity extends AppCompatActivity implements View.OnClickListener {

    SurfaceView camera_detect;
    ImageButton btn_back;
    ImageButton btn_home;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;
    public static String receive_value;
    public static String repeat_value;
    public CountDownTimer cd;
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
        setContentView(R.layout.activity_play_melody);
        camera_detect = (SurfaceView)findViewById(R.id.camera_scan);
        btn_back = (ImageButton)findViewById(R.id.btn_back);
        btn_home = (ImageButton)findViewById(R.id.btn_home);
        btn_home.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        receive_value = "000000";
        repeat_value = "000000";
        barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();
        cameraSource = new CameraSource.Builder(this, barcodeDetector).setRequestedPreviewSize(480, 320).build();

        camera_detect.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(PlayMelodyActivity.this, new String[]{Manifest.permission.CAMERA},RequestCameraPermissionID);
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

        cd = new CountDownTimer(999000, 2000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (!receive_value.equalsIgnoreCase("000000") && !repeat_value.equalsIgnoreCase(receive_value)){
                    setMelo();
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();

    }

    private void setMelo() {
        switch (receive_value) {
            case "030001" : mp = MediaPlayer.create(getApplicationContext(), R.raw.c_nt);
                break;
            case "030002" : mp = MediaPlayer.create(getApplicationContext(),R.raw.d_nt);
                break;
            case "030003" : mp = MediaPlayer.create(getApplicationContext(),R.raw.e_nt);
                break;
            case "030004" : mp = MediaPlayer.create(getApplicationContext(),R.raw.f_nt);
                break;
            case "030005" : mp = MediaPlayer.create(getApplicationContext(),R.raw.g_nt);
                break;
            case "030006" : mp = MediaPlayer.create(getApplicationContext(),R.raw.a_nt);
                break;
            case "030007" : mp = MediaPlayer.create(getApplicationContext(),R.raw.b_nt);
                break;
            case "130001" : mp = MediaPlayer.create(getApplicationContext(),R.raw.c_nt);
                break;
            case "130002" : mp = MediaPlayer.create(getApplicationContext(),R.raw.d_nt);
                break;
            case "130003" : mp = MediaPlayer.create(getApplicationContext(),R.raw.e_nt);
                break;
            case "130004" : mp = MediaPlayer.create(getApplicationContext(),R.raw.f_nt);
                break;
            case "130005" : mp = MediaPlayer.create(getApplicationContext(),R.raw.g_nt);
                break;
            case "130006" : mp = MediaPlayer.create(getApplicationContext(),R.raw.a_nt);
                break;
            case "130007" : mp = MediaPlayer.create(getApplicationContext(),R.raw.b_nt);
                break;
            default:          mp.release();
                break;
        }

        repeat_value = receive_value;
        receive_value = "000000";
        mp.start();

    }

    @Override
    public void onClick(View v) {

        if (v == btn_back){
            intent = new Intent(this, MusicActivity.class);
            startActivity(intent);
            finish();
        }  else if (v == btn_home){
            intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
