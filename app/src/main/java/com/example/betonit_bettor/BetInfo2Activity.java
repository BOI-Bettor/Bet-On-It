package com.example.betonit_bettor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.DecimalFormat;

public class BetInfo2Activity extends AppCompatActivity {

    String challenger;
    String betId;
    String TAG = "Bet Info";
    Bet bet = new Bet();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet_info2);

        final TextView username = findViewById(R.id.username);
        final TextView desc = findViewById(R.id.betDesc);
        final TextView btnChallenger = findViewById(R.id.btnChallenger);
        final TextView btnChallengee = findViewById(R.id.btnChallengee);
        final TextView betName = findViewById(R.id.betName);
        final TextView betAmount = findViewById(R.id.betAmount);
        final TextView betStart = findViewById(R.id.betStart);
        final TextView betEnd = findViewById(R.id.betEnd);
        final TextView tvWinner = findViewById(R.id.tvWinner);

        final DecimalFormat df = new DecimalFormat("$#.00");

        // Check if bet was received.

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            betId = (String) intent.getSerializableExtra("betObjectId");
            challenger = (String) intent.getSerializableExtra("challengerUser");
            username.setText(challenger);

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Bet");
            query.getInBackground(betId, new GetCallback<ParseObject>() {
                public void done(ParseObject object, ParseException e) {
                    if (e == null) {
//                        username.setText(object.getParseUser("bet_Challenger"));
                        betName.setText(object.getString("bet_Name"));
                        betAmount.setText((df.format(object.getDouble("bet_Amount"))));
//                        betStart.setText(String.valueOf(object.getDate("bet_Start")));
//                        betEnd.setText(String.valueOf(object.getDate("bet_End")));
                        desc.setText(object.getString("bet_Desc"));

                        // If the challenge is rejected or complete, hide the buttons to vote.

                        if (object.getString("bet_Status").equals("REJECTED") ||
                                object.getString("bet_Status").equals("COMPLETE")) {
                            tvWinner.setVisibility(View.INVISIBLE);
                            btnChallengee.setVisibility(View.INVISIBLE);
                            btnChallenger.setVisibility(View.INVISIBLE);
                        }

                    } else {
                        Log.i(TAG, "EMPTY?");
                    }
                }
            });
        }

        // Challenger chooses a winner.

        btnChallenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseQuery<Bet> bet = ParseQuery.getQuery("Bet");
                bet.getInBackground(betId, new GetCallback<Bet>() {
                    public void done(Bet object, ParseException e) {
                        if (e == null) {
                            if (object.getBetChallenger().getObjectId().equals(ParseUser.getCurrentUser().getObjectId())) {
                                object.put("bet_vote_1", ParseUser.getCurrentUser());
                                object.saveInBackground();
                            }
                            else {
                                object.put("bet_vote_2", ParseUser.getCurrentUser());
                                object.saveInBackground();
                            }
                            alertDisplayer("Vote Sent", "Your decision has been sent.");
                        } else {
                            Log.e(TAG, "Please, kill me.");
                        }
                    }
                });
            }
        });

        btnChallengee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseQuery<Bet> bet = ParseQuery.getQuery("Bet");
                bet.getInBackground(betId, new GetCallback<Bet>() {
                    public void done(Bet object, ParseException e) {
                        if (e == null) {
                            if (object.getBetChallenger().getObjectId().equals(ParseUser.getCurrentUser().getObjectId())) {
                                object.put("bet_vote_1", object.getBetChallengee());
                                object.saveInBackground();
                            }

                            else {
                                object.put("bet_vote_2", object.getBetChallengee());
                                object.saveInBackground();
                            }
                            alertDisplayer("Vote Sent", "Your decision has been sent.");
                        }
                    }
                });
            }
        });

    }

    private void alertDisplayer(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }
}