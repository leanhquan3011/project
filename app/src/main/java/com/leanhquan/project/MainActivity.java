package com.leanhquan.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    private Camera camera = null;
    private CameraPreview cameraPreview;
    private FrameLayout preview;

    private Animation animation;
    private Button btn;
    private RelativeLayout parent;
    private Handler handler = new Handler();


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preview = findViewById(R.id.camera_preview);
        animation = AnimationUtils.loadAnimation(this,R.anim.annim_slide_down);

        if (checkCameraHardware(this)) {
            camera = Camera.open();
            cameraPreview = new CameraPreview(this, camera);
            preview.addView(cameraPreview);
        }

        parent = findViewById(R.id.parent);
        parent.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int id = event.getActionIndex();
                if (id == 0){
                    btn.setVisibility(View.VISIBLE);
                    btn.startAnimation(animation);
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            btn.setVisibility(View.GONE);
                        }
                    };
                    handler.postDelayed(runnable, 5000);
                    return true;
                }
                return false;
            }
        });

        btn = findViewById(R.id.fbbtn);
        btn.setVisibility(View.GONE);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Test.class);
                startActivity(intent);
            }
        });

    }

    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)){
            return true;
        } else {
            return false;
        }
    }

}
