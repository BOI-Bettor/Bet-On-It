package com.example.betonit_bettor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class MessageViewActivity extends AppCompatActivity {

    ParseUser user = ParseUser.getCurrentUser();
    boolean isAdmin;
    String msgId, adminUser;
    String TAG = "Message View Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_info);

        final EditText etUser = findViewById(R.id.etUsername);
        final EditText etDesc = findViewById(R.id.etBody);
        TextView tvMsg = findViewById(R.id.tvMsg);
        final CardView btnSend = findViewById(R.id.cardView);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        isAdmin = user.getBoolean("isAdmin");

        Intent intent = getIntent();
        if (intent.getExtras() != null) {

            // If admin, allow to edit.

            if (isAdmin) {
                btnSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etUser.setText("bRO WHAT THE FUCK WHY ARENT YOU REGISTERING MY CLICKS???");
                        Log.i(TAG, "Message sent.");
                    }
                });
            }

            // If not, display this screen instead.

            if (!isAdmin) {
                tvMsg.setText("RECEIVED MESSAGE");
                etUser.setEnabled(false);
                etDesc.setEnabled(false);
                btnSend.setVisibility(View.INVISIBLE);

                msgId = (String) intent.getSerializableExtra("messageObjId");
                adminUser = (String) intent.getSerializableExtra("adminUser");

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Message");
                query.getInBackground(msgId, new GetCallback<ParseObject>() {
                    public void done(ParseObject object, ParseException e) {
                        if (e == null) {
                            etDesc.setText(object.getString("message_Body"));
                            etUser.setText(adminUser);
                            object.put("message_Status", "Read");
                            object.saveInBackground();
                        } else {
                            Log.i(TAG, "EMPTY?");
                        }
                    }
                });
            }
        }
    }
}