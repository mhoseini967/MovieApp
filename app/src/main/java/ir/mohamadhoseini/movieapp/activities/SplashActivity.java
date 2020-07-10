package ir.mohamadhoseini.movieapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import ir.mohamadhoseini.movieapp.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.splashScreenTheme);
        super.onCreate(savedInstanceState);
        startActivity(new Intent(SplashActivity.this,MainActivity.class));

    }
}
