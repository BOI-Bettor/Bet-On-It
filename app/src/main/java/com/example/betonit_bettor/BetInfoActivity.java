package com.example.betonit_bettor;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class BetInfoActivity extends AppCompatActivity {

    String challenger;
    String betId;
    String TAG = "Bet Info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet_info);

        TextView username = findViewById(R.id.username);
        TextView desc = findViewById(R.id.desc);
        final TextView accept = findViewById(R.id.buttonAccept);
        final TextView reject = findViewById(R.id.buttonReject);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            challenger = (String) intent.getSerializableExtra("challengerUser");
            username.setText(challenger);
            betId = (String) intent.getSerializableExtra("betObjectId");
            Log.i(TAG, betId);
        }



        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseQuery<Bet> bet = ParseQuery.getQuery("Bet");
                bet.getInBackground(betId, new GetCallback<Bet>() {
                    public void done(Bet object, ParseException e) {
                        if (e == null) {
                            object.setBetStatus("ACCEPTED");
                            object.saveInBackground();
                        } else {
                            Log.e(TAG, "Please, kill me.");
                        }
                    }
                });
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseQuery<Bet> bet = ParseQuery.getQuery("Bet");
                bet.getInBackground(betId, new GetCallback<Bet>() {
                    public void done(Bet object, ParseException e) {
                        if (e == null) {
                            object.setBetStatus("REJECTED");
                                object.saveInBackground();
                        } else {
                            Log.e(TAG, "Please, kill me.");
                        }
                    }
                });
            }
        });

    }
}