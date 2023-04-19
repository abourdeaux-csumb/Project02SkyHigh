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
import com.example.project02skyhigh.databinding.ActivityLoginBinding;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    /*
Intent factory to switch between activities
 */
    EditText mUsername;
    EditText mPassword;

    Button mLoginButton;

    TextView mInvalidCredentials;

    UserDAO mUserDAO;

    List<User> mUsers;

    ActivityLoginBinding mLoginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = mLoginBinding.getRoot();
        setContentView(view);
        wireupDisplay();
        UserRepository.initialize(this);
    }

    private void wireupDisplay() {

        mUsername = mLoginBinding.loginUsernameEditText;
        mPassword = mLoginBinding.loginPasswordEditText;
        mLoginButton = mLoginBinding.loginLoginButton;
        mInvalidCredentials = mLoginBinding.loginInvalidCredentials;
        setButtonOnClickListeners();
    }

    private void setButtonOnClickListeners() {
        /*
        Attempt to login with the provided credentials
         */
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int userId = UserRepository.validateCredentials(mUsername.getText().toString(), mPassword.getText().toString());
                if (userId != -1) {
                    SharedPreferences sharedPref = getSharedPreferences("Logins", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor =  sharedPref.edit();
                    editor.putInt("Login", userId);
                    editor.commit();
                    Intent intent = LandingPage.getIntent(getApplicationContext());
                    startActivity(intent);
                }
                else {
                    mInvalidCredentials.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

}
