package com.example.betonit_bettor;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BetInfoActivity extends AppCompatActivity {
    public static final String TAG = "BetInfoActivity";
    private TextView tvBetName, tvBetName_field, tvChallenger, tvChallenger_field, tvAmount, tvAmount_field, tvType, tvType_field,
                     tvStart, tvStart_field, tvRescind, tvRescind_field, tvDesc, tvDesc_field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.betinfo_screen);

        tvBetName = findViewById(R.id.tvBetName);
        tvBetName_field = findViewById(R.id.tvBetName_field);
        tvChallenger = findViewById(R.id.tvChallenger_field);
        tvChallenger_field = findViewById(R.id.tvChallenger_field);
        tvAmount = findViewById(R.id.tvAmount);
        tvAmount_field = findViewById(R.id.tvAmount_field);
        tvType = findViewById(R.id.tvType);
        tvType_field = findViewById(R.id.tvType_field);
        tvStart = findViewById(R.id.tvStart);
        tvStart_field = findViewById(R.id.tvStart_field);
        tvRescind = findViewById(R.id.tvRescind);
        tvRescind_field = findViewById(R.id.tvRescind_field);
        tvDesc = findViewById(R.id.tvDesc);
        tvDesc_field = findViewById(R.id.tvDesc_field);



    }
