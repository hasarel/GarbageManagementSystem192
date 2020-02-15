package com.nibm.rwp.gms.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nibm.rwp.gms.R;
import com.nibm.rwp.gms.common.BaseActivity;

public class FeedBackActivity extends BaseActivity implements View.OnClickListener {

    //constant
    private static final String TAG = FeedBackActivity.class.getSimpleName();

    //Ui components
    private RatingBar mRatingBar;
    private TextView mTvRateCount, mTvRateMessage;

    private double mRatedValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        setToolbar(getResources().getString(R.string.acitivty_feedback), FeedBackActivity.this);

        initView();
        giveUserFeedback();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void initView() {
        mRatingBar = (RatingBar) findViewById(R.id.ratingBar);
        mTvRateCount = (TextView) findViewById(R.id.tvRateCount);
        mTvRateMessage = (TextView) findViewById(R.id.tvRateMessage);
    }

    private void giveUserFeedback() {
        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                mRatedValue = ratingBar.getRating();
                mTvRateCount.setText("Your Rating : "
                        + mRatedValue + " /5");

                if(mRatedValue <1){
                    mTvRateMessage.setText("Very Bad");
                }else if(mRatedValue <2){
                    mTvRateMessage.setText("Not Bad");
                }else if(mRatedValue <3){
                    mTvRateMessage.setText("Good");
                }else if(mRatedValue <4){
                    mTvRateMessage.setText("Nice");
                }else if(mRatedValue <5){
                    mTvRateMessage.setText("Very Good");
                }else if(mRatedValue ==5){
                    mTvRateMessage.setText("awesome!!!");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_feedback_btn_giveFeedback:

        }
    }
}
