package ch.zli.clapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class StartActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        new Handler().postDelayed(() -> {
            Intent homeIntent = new Intent(StartActivity.this, UpcomingActivity.class);
            startActivity(homeIntent);
            finish();
        }, SPLASH_TIME_OUT);
    }
}