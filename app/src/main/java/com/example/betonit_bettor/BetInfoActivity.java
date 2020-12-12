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
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class BetInfoActivity extends AppCompatActivity {

    String challenger;
    String betId;
    String TAG = "Bet Info";
    Bet bet = new Bet();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet_info);

        TextView username = findViewById(R.id.username);
        final TextView desc = findViewById(R.id.betDesc);
        final TextView accept = findViewById(R.id.buttonAccept);
        final TextView reject = findViewById(R.id.buttonReject);
        final TextView betName = findViewById(R.id.betName);
        final TextView betAmount = findViewById(R.id.betAmount);
        final TextView betStart = findViewById(R.id.betStart);
        final TextView betEnd = findViewById(R.id.betEnd);

        final DecimalFormat df = new DecimalFormat("$#.00");

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            challenger = (String) intent.getSerializableExtra("challengerUser");
            username.setText(challenger);
            betId = (String) intent.getSerializableExtra("betObjectId");

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Bet");
            query.getInBackground(betId, new GetCallback<ParseObject>() {
                public void done(ParseObject object, ParseException e) {
                    if (e == null) {
                        betName.setText(object.getString("bet_Name"));
                        betAmount.setText((df.format(object.getDouble("bet_Amount"))));
                        betStart.setText(String.valueOf(object.getDate("bet_Start")));
//                        betEnd.setText(String.valueOf(object.getDate("bet_End")));
                        desc.setText(object.getString("bet_Desc"));
                    } else {
                        Log.i(TAG, "EMPTY?");
                    }
                }
            });
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