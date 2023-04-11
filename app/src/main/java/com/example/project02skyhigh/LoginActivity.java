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
import androidx.room.Room;

import com.example.project02skyhigh.DB.AppDatabase;
import com.example.project02skyhigh.DB.UserDAO;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    /*
Intent factory to switch between activities
 */
    EditText mUsername;
    EditText mPassword;

    Button mLogin;

    TextView mInvalidCredentials;

    UserDAO mUserDAO;

    List<User> mUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUsername = findViewById(R.id.loginUsernameEditText);
        mPassword = findViewById(R.id.loginPasswordEditText);
        mLogin = findViewById(R.id.loginLoginButton);
        mInvalidCredentials = findViewById(R.id.loginInvalidCredentials);
        mUserDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.dbName)
                .allowMainThreadQueries()
                .build()
                .getUserDAO();
        populateUsers();
        mUsers = mUserDAO.getUsers();

        /*
        Navigate to login activity upon clicking the login button
         */
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int userId = validateCredentials(mUsername.getText().toString(), mPassword.getText().toString());
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

    private void populateUsers() {
        List<User> users = mUserDAO.getUsers();
        if (mUserDAO.getUsers().isEmpty()) {
            User testUser = new User();
            testUser.setUsername("testuser1");
            testUser.setPassword("testuser1");
            testUser.setIsAdmin(false);

            User adminUser = new User();
            adminUser.setUsername("admin2");
            adminUser.setPassword("admin2");
            adminUser.setIsAdmin(true);

            mUserDAO.insert(testUser, adminUser);
        }
    }

    private int validateCredentials(String username, String password) {
        for (User user : mUsers) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user.getUserId();
            }
        }
        return -1;
    }

}
