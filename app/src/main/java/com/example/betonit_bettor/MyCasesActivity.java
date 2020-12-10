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
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class MyCasesActivity extends AppCompatActivity {
    public static final String TAG = "MyCasesActivity";
    public String no;
    public String yes1;
    public String yes2;
    public String user;
    TextView tvUsername;
    EditText editTextTextPersonName;
    TextView status2;
    TextView description1;
    TextView description2;
    Button chlnge;
    Button chlngr;
    Button prevclass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted);

        tvUsername = findViewById(R.id.tvUsername);
        status2 = findViewById(R.id.status2);
        editTextTextPersonName = findViewById(R.id.editTextTextPersonName);
        description1 = findViewById(R.id.description1);
        description2 = findViewById(R.id.description2);
        chlnge = findViewById(R.id.chlnge);
        chlngr = findViewById(R.id.chlngr);
        prevclass = findViewById(R.id.rtnbtn2);

        final String stat = getIntent().getStringExtra("status");
        String desc1 = getIntent().getStringExtra("description1");
        String desc2 = getIntent().getStringExtra("description2");
        status2.setText(stat);
        description1.setText(desc1);
        description2.setText(desc2);

        ParseQuery<Case> query = ParseQuery.getQuery(Case.class);
        query.whereEqualTo(Case.KEY_CASE_STATUS, stat);
        query.whereEqualTo(Case.KEY_CASE_CHALLENGER_EVIDENCE, desc1);
        query.whereEqualTo(Case.KEY_CASE_CHALLENGEE_EVIDENCE, desc2);
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

        chlngr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<Case> query = ParseQuery.getQuery(Case.class);
                final String windes = editTextTextPersonName.getText().toString();
                query.getInBackground(no, new GetCallback<Case>() {
                    public void done(Case event, ParseException e) {
                        if (e == null) {

                            Toast.makeText(getApplicationContext(), "Case Has been Resolved", Toast.LENGTH_LONG).show();
                        }
                        //Select challenger as winner
                        event.put("case_Status", "RESOLVED");
                        event.put("case_Winner_Desc", windes);
                        event.setKeyCaseBetWinner(ParseUser.getCurrentUser());
                        //event.put("case_Arbitrator", "BBkTRVg4nb");
                        event.saveInBackground();
                        Intent goToFactsList = new Intent(MyCasesActivity.this, BettorActivity.class);
                        startActivity(goToFactsList);
                    }
                });
            }
        });

        chlnge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<Case> query = ParseQuery.getQuery(Case.class);
                final String windes = editTextTextPersonName.getText().toString();
                query.getInBackground(no, new GetCallback<Case>() {
                    public void done(Case event, ParseException e) {
                        if (e == null) {

                            Toast.makeText(getApplicationContext(), "Case Has been Resolved", Toast.LENGTH_LONG).show();
                        }
                        //Select challenger as winner
                        event.put("case_Status", "RESOLVED");
                        event.put("case_Winner_Desc", windes);
                        event.setKeyCaseBetWinner(ParseUser.getCurrentUser() );
                        //event.put("case_Arbitrator", "BBkTRVg4nb");
                        event.saveInBackground();
                        Intent goToFactsList = new Intent(MyCasesActivity.this, BettorActivity.class);
                        startActivity(goToFactsList);
                    }
                });
            }
        });

        prevclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToFactsList = new Intent(MyCasesActivity.this, BettorActivity.class);
                startActivity(goToFactsList);
            }
        });
    }
}