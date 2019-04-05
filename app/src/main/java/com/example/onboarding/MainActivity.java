package com.example.onboarding;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ViewPager mSlideViewPager;
    private LinearLayout mDottLayout;
    private Button nextButton;
    private Button previousButton;

    private SliderAdapter mSliderAdapter;

    private TextView[] mDots;

    private int mCurrentPage;

    private static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nextButton = findViewById(R.id.btn_next);
        previousButton = findViewById(R.id.btn_back);

        mSlideViewPager = findViewById(R.id.viewpager);


        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean("my_first_time", true)) {
            //the app is being launched for first time, do something
            Log.d("Comments", "First time");

            // first time task
            Toast.makeText(this,"App is used for the first time",Toast.LENGTH_LONG).show();
            initiateOnBoardingScreen();
            // record the fact that the app has been started at least once
            settings.edit().putBoolean("my_first_time", false).apply();
        }else {
            startLoginScreen();
            Toast.makeText(this,"App is already used",Toast.LENGTH_LONG).show();
        }

    }

    private void initiateOnBoardingScreen(){
        mDottLayout =  findViewById(R.id.dotsLayout);

        mSliderAdapter = new SliderAdapter(this);
        mSlideViewPager.setAdapter(mSliderAdapter);
        mSlideViewPager.addOnPageChangeListener(viewListener);

        addDotsndicator(0);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentPage < 3){
                    mSlideViewPager.setCurrentItem(mCurrentPage+1);
                    Log.d(TAG, "onClick: next , page number = "+mCurrentPage);
                }
                if(mCurrentPage == 2){
                    Log.d(TAG, "onClick: next, trying to start login");
                    startLoginScreen();
                }
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlideViewPager.setCurrentItem(mCurrentPage-1);
            }
        });
    }

    private void startLoginScreen(){
        Log.d(TAG, "startLoginScreen: Starts");
        Intent loginIntent = new Intent(this,LoginActivity.class);
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
    }

    public void addDotsndicator(int position){
        mDots = new TextView[3];
        mDottLayout.removeAllViews();

        for(int i=0;i<mDots.length;i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));

            mDottLayout.addView(mDots[i]);
        }

        if (mDots.length>0){
            mDots[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsndicator(i);
            mCurrentPage = i;
            if(i == 0){
                nextButton.setEnabled(true);
                previousButton.setEnabled(false);
                previousButton.setVisibility(View.INVISIBLE);
            }else if(i == mDots.length-1){
                nextButton.setEnabled(true);
                previousButton.setEnabled(true);
                previousButton.setVisibility(View.VISIBLE);

                nextButton.setText("Finish");
            }else {
                nextButton.setEnabled(true);
                previousButton.setEnabled(true);
                previousButton.setVisibility(View.VISIBLE);
                nextButton.setText("Next");
            }

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
}
