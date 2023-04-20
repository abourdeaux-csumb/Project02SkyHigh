package com.example.project02skyhigh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project02skyhigh.DB.UserDAO;
import com.example.project02skyhigh.DB.UserRepository;
import com.example.project02skyhigh.databinding.ActivitySignUpBinding;

import java.util.List;

public class SignUpActivity extends AppCompatActivity {
    /**
     * Title: SignUpActivity.java
     * Abstract: Activity used to create a new accounts
     * Author: Aaron Bourdeaux
     * Date: 2023/04/14
     */
    EditText mUsername;
    EditText mPassword;
    EditText mRepeatPassword;
    Button mSignUpButton;
    TextView mInvalidCredentials;
    UserDAO mUserDAO;
    List<User> mUsers;
    ActivitySignUpBinding mSignUpBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSignUpBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = mSignUpBinding.getRoot();
        setContentView(view);
        wireupDisplay();
    }

    /**
     * Enable control of various elements in the layout
     */
    private void wireupDisplay() {

        mUsername = mSignUpBinding.signUpUsernameEditText;
        mPassword = mSignUpBinding.signUpPasswordEditText;
        mRepeatPassword = mSignUpBinding.signUpRepeatPasswordEditText;
        mInvalidCredentials = mSignUpBinding.signUpInvalidCredentials;
        mSignUpButton = mSignUpBinding.signUpButton;
        setButtonOnClickListeners();
    }

    /**
     * Dictates logic regarding click events
     */
    private void setButtonOnClickListeners() {
        /*
        Attempt to create an account and sign in
         */
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mPassword.getText().toString().equals(mRepeatPassword.getText().toString())) {
                    setValidationError("Password does not match");
                }
                else {
                    int newUserId = UserRepository.createAccount(mUsername.getText().toString(), mPassword.getText().toString(), false);
                    if (newUserId == -1) {
                        setValidationError("User with username '" + mUsername.getText().toString() + "' already exists");
                    }
                    else {
                        SharedPreferences sharedPref = getSharedPreferences("Logins", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor =  sharedPref.edit();
                        editor.putInt("Login", newUserId);
                        editor.commit();
                        Intent intent = LandingPage.getIntent(getApplicationContext());
                        startActivity(intent);
                    }
                }
            }
        });
    }

    /*
    Sets the validation error text and makes the text visible
     */
    private void setValidationError(String text) {
        mInvalidCredentials.setText(text);
        mInvalidCredentials.setVisibility(View.VISIBLE);
    }

    /*
    Intent factory to switch between activities
    */
    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, SignUpActivity.class);
        return intent;
    }
}