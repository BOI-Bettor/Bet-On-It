package com.example.betonit_bettor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class UserViewActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
    UsersModel usersModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);

        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);

        Intent intent = getIntent();
        if(intent.getExtras() != null){
            usersModel = (UsersModel) intent.getSerializableExtra("users");
            imageView.setImageResource(usersModel.getImg());
            textView.setText(usersModel.getUserName());
        }

    }
}