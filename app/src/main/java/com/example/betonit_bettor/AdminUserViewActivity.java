package com.example.betonit_bettor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class AdminUserViewActivity extends AppCompatActivity {

    public static final String TAG = "AdminUserView";
    ImageView imageView;
    TextView textView;
    TextView userStatus;
    UsersModel usersModel;
    Button btnPause;
    Button btnDeact;
    Button btnBan;
    Button btnAct;
    ParseUser currBOIuser = new ParseUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_view);

        imageView = findViewById(R.id.imgProfile);
        textView = findViewById(R.id.tvUsername);
        userStatus = findViewById(R.id.tvStatus);
        btnPause = findViewById(R.id.btnPause);
        btnDeact = findViewById(R.id.btnDeactivate);
        btnBan = findViewById(R.id.btnBan);
        btnAct = findViewById(R.id.btnActivate);

        Intent intent = getIntent();
        if(intent.getExtras() != null){
            usersModel = (UsersModel) intent.getSerializableExtra("users");
            imageView.setImageResource(usersModel.getImg());
            textView.setText(usersModel.getUserName());
            userStatus.setText(usersModel.getStatus());
        }

        findUsers();

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "pause clicked");
                usersModel.setStatus("Paused");
                userStatus.setText("STATUS: " + usersModel.getStatus());
                currBOIuser.put("user_Status", "Paused");
                currBOIuser.saveInBackground();
            }
        });

        btnDeact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "deact clicked");
                usersModel.setStatus("Inactive");
                userStatus.setText("STATUS: " + usersModel.getStatus());
                currBOIuser.put("user_Status", "Inactive");
                currBOIuser.saveInBackground();
            }
        });

        btnBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "ban clicked");
                usersModel.setStatus("Banned");
                userStatus.setText("STATUS: " + usersModel.getStatus());
                currBOIuser.put("user_Status", "Banned");
                currBOIuser.saveInBackground();
            }
        });

        btnAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "act clicked");
                usersModel.setStatus("Active");
                userStatus.setText("STATUS: " + usersModel.getStatus());
                currBOIuser.put("user_Status", "Active");
                currBOIuser.saveInBackground();
            }
        });
    }

    public void findUsers() {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", textView.toString());
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> users, ParseException e) {
                if (e == null) {
                    // The query was successful, returns the users that matches
                    // the criterias.
                    for(ParseUser user : users) {
                        if (users.size() == 1)
                        {
                            System.out.println(user.getUsername());
                            System.out.println(user.get("user_Status"));
                            currBOIuser = user;
                        }
                    }
                } else {
                    // Something went wrong.
                }
            }
        });
    }
}