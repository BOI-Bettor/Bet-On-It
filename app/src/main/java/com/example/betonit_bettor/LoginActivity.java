package com.example.betonit_bettor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";
    private EditText etUsername;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(ParseUser.getCurrentUser() != null)
        {
            checkAdminUser();
        }

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView btnRegister = findViewById(R.id.tvSignUp);

        // Login User to Bet On It
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                // Validating the log in data
                boolean validationError = false;

                StringBuilder validationErrorMessage = new StringBuilder("Please, insert ");
                if (username.isEmpty()) {
                    validationError = true;
                    validationErrorMessage.append("an username");
                }
                if (password.isEmpty()) {
                    if (validationError) {
                        validationErrorMessage.append(" and ");
                    }
                    validationError = true;
                    validationErrorMessage.append("a password");
                }
                validationErrorMessage.append(".");

                if (validationError) {
                    Toast.makeText(LoginActivity.this, validationErrorMessage.toString(), Toast.LENGTH_LONG).show();
                    return;
                }

                // If the fields are valid, login
                loginUser(username, password);
            }
        });

        // USER REGISTRATION

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(myIntent);
            }
        });

    }

    // LOGIN SECTION

        private void loginUser (String username, String password)
        {
            ParseUser.logInInBackground(username, password, new LogInCallback() {
                @Override
                public void done(ParseUser parseUser, ParseException e) {
                    if (e != null) {
                        Log.e(TAG, "Issue with login", e);
                        Toast.makeText(LoginActivity.this, "Invalid Username/Password. Please try again.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // Check to see if the user if an admin or standard bettor
                    checkAdminUser();
                }
            });
        }

     // CHECK IF USER IS ADMIN

    private void checkAdminUser() {
        final ParseUser parseUser = ParseUser.getCurrentUser();
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("isAdmin", true);
        query.whereNotEqualTo("user_Status", "BANNED");
        query.whereEqualTo("username", parseUser.getUsername());
        query.findInBackground(new FindCallback<ParseUser>() {
           @Override
           public void done(List<ParseUser> users, ParseException e) {
               if(e == null)
               {
                   if(users.isEmpty())
                   {
                       Log.d(TAG, "There is no admin user with the username " + parseUser.getUsername());
                       goBettorActivity();
                   }
                   else
                   {
                       Log.d(TAG, "Admin user: " + parseUser.getUsername() + " : FOUND");
                       goAdminActivity();
                   }
               }
           }
       });
    }

    // DIRECT USER TO ACTIVITIES

    private void goBettorActivity() {
        Intent i = new Intent(this, BettorActivity.class);
        startActivity(i);
        finish();
    }

    private void goAdminActivity() {
        Intent i = new Intent(this, AdminActivity.class);
        startActivity(i);
        finish();
    }
}
