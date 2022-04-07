package ru.ruslan.currencyconvertor.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import ru.ruslan.currencyconvertor.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        setTitle("");
        TextView appName = findViewById(R.id.appName);
        LottieAnimationView lottieAnimationView = findViewById(R.id.lottie);

        //определение ориентации
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            appName.animate().translationY(-1600).setDuration(3000).setStartDelay(0);
            lottieAnimationView.animate().translationX(2000).setDuration(4000).setStartDelay(3000);
        }
        else{
            appName.animate().translationY(-700).setDuration(2000).setStartDelay(0);
            lottieAnimationView.animate().translationX(2000).setDuration(3000).setStartDelay(2500);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), ListCurenciesActivity.class);
                startActivity(intent);
            }
        }, 5000);
    }

    @Override
    protected void onPause() {
        super.onPause();

        finishAffinity();
    }
}