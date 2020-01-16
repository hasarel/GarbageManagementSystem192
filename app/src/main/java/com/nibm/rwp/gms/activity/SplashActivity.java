package com.nibm.rwp.gms.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.nibm.rwp.gms.R;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView mTvHomePage;
    private ImageView mIvLogo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_layout);

        initView();
        animationLogo();
    }

    public void initView(){
        mTvHomePage = findViewById(R.id.home_page_text);
        mIvLogo = findViewById(R.id.home_page_logo);
    }

    public void animationLogo(){
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.home_page);
        mTvHomePage.startAnimation(animation);
        mIvLogo.startAnimation(animation);
        final Intent intent = new Intent(this,LoginActivity.class);
        Thread thread = new Thread(){
            public void run(){
                try {
                    sleep(3000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    startActivity(intent);
                    finish();
                }
            }
        };
        thread.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

        }
    }
}
