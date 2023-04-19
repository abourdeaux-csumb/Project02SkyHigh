package com.example.project02skyhigh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project02skyhigh.DB.UserRepository;
import com.example.project02skyhigh.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    Button mSkyHigh_login_button;
    Button mSkyHigh_createAccount_button;
    ActivityMainBinding mMainBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UserRepository.initialize(this);
        redirectIfLoggedIn();
        super.onCreate(savedInstanceState);
        mMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mMainBinding.getRoot();
        setContentView(view);
        wireupDisplay();
    }

    private void wireupDisplay() {
        mSkyHigh_login_button = mMainBinding.mainLoginButton;
        mSkyHigh_createAccount_button = mMainBinding.mainCreateAccountButton;
        setButtonOnClickListeners();
    }

    private void setButtonOnClickListeners() {
        /*
        Navigate to login activity upon clicking the login button
         */
        mSkyHigh_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = LoginActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });

        /*
        Navigate to sign up activity upon clicking the sign up button
         */
        mSkyHigh_createAccount_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = SignUpActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    private void redirectIfLoggedIn() {
        SharedPreferences sharedPref = getSharedPreferences("Logins", Context.MODE_PRIVATE);
        if (sharedPref.getInt("Login", -1) != -1) {
            Intent intent = LandingPage.getIntent(getApplicationContext());
            startActivity(intent);
        }
    }

    /*
    Intent factory to switch between activities
    */
    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }
}