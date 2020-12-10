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
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class RatedDetailActivity extends AppCompatActivity {
    public static final String TAG = "RatedDetailActivity";
    public String no;
    TextView tvCaseId;
    TextView tvRatedCaseWinner;
    EditText etRatedCaseRating;
    TextView tvChallengerEvidence;
    TextView tvChallengeeEvidence;
    TextView tvRatedCaseWinnerDesc;
    Button btnSubmitRating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rated_detail);
        tvCaseId = findViewById(R.id.tvRatedCaseId);
        tvRatedCaseWinner = findViewById(R.id.tvRatedCaseWinner);
        tvRatedCaseWinnerDesc = findViewById(R.id.tvRatedCaseWinnerDesc);
        etRatedCaseRating = findViewById(R.id.etRatedCaseRating);
        tvChallengerEvidence = findViewById(R.id.tvChallengerEvidence);
        tvChallengeeEvidence = findViewById(R.id.tvChallengeeEvidence);
        btnSubmitRating = findViewById(R.id.btnSubmitRating);

        final String caseId = getIntent().getStringExtra("tvCaseId");
        String user1_case = getIntent().getStringExtra("tvChallengerEvidence");
        String user2_case = getIntent().getStringExtra("tvChallengeeEvidence");
        String winnerDesc = getIntent().getStringExtra("tvRatedCaseWinnerDesc");

        tvCaseId.setText(caseId);
        tvChallengerEvidence.setText(user1_case);
        tvChallengeeEvidence.setText(user2_case);
        tvRatedCaseWinnerDesc.setText(winnerDesc);

        // Search for Cases with the given status
        ParseQuery<Case> query = ParseQuery.getQuery(Case.class);
        query.whereEqualTo(Case.KEY_CASE_STATUS, "RESOLVED");
        query.whereEqualTo(Case.KEY_CASE_ISRATED, false);
        query.findInBackground(new FindCallback<Case>() {
            public void done(List<Case> cases, ParseException e) {
                if (e != null) {
                    // Access the array of results here
                    Log.e(TAG, "Error: " + e.getMessage(), e);
                }
                for (Case case1 : cases) {
                    no = case1.getObjectId();
                    Log.i(TAG, "MyCase: " + case1.getKeyCaseBetId().getObjectId().toString());
                    Log.i(TAG, "Arbitrator: " + case1.getKeyCaseBetId().getObjectId().toString());
                }

            }
        });

        btnSubmitRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int rating;

                String numberText = etRatedCaseRating.getText().toString();
                if(!"".equals(numberText)) {
                    // if the numbers are between 1 and 5 (incl.)
                    rating = Integer.parseInt(numberText);

                    if (rating <= 5 && rating >= 1)
                    {
                        ParseQuery<Case> query = ParseQuery.getQuery(Case.class);

                        query.getInBackground(no, new GetCallback<Case>() {
                            public void done(Case event, ParseException e) {
                                if (e == null) {
                                    Toast.makeText(getApplicationContext(), "Case Has been Accepted", Toast.LENGTH_LONG).show();
                                }
                                event.put("case_Status", "RESOLVED");
                                event.put("case_Rating", rating);
                                event.put("case_isRated", true);
                                event.put("case_Rater", ParseUser.getCurrentUser());
                                event.saveInBackground();
                                Intent goToFactsList = new Intent(RatedDetailActivity.this, MainActivity.class);
                                startActivity(goToFactsList);
                            }
                        });
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Please make a valid choice", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}