package com.example.betonit_bettor;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;
import android.graphics.Color;



public class MessageViewActivity extends AppCompatActivity {

    ParseUser user = ParseUser.getCurrentUser();
    boolean isAdmin;
    String msgId, adminUser, userName, desc;
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
        final ProgressDialog dlg = new ProgressDialog(this);
        dlg.setTitle("Please, wait a moment.");
        dlg.setMessage("Sending your message...");

        isAdmin = user.getBoolean("isAdmin");

        // If admin, allow to edit.
        if (isAdmin) {
            btnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    userName = etUser.getText().toString();
                    desc = etDesc.getText().toString();
                    dlg.show();

                    ParseQuery<ParseUser> query = ParseUser.getQuery();
                    query.whereEqualTo("username", userName);
                    query.findInBackground(new FindCallback<ParseUser>() {
                        public void done(List<ParseUser> users, ParseException e) {
                            if (users == null || users.isEmpty()) {
                                dlg.dismiss();
                                alertDisplayer("User Not Found", "The user you are challenging does not exist.");
                            }

                            if (e == null && users != null) {
                                for (ParseUser user : users) {

                                    Message message = new Message();
                                    message.setKeyMessageBody(desc);
                                    message.setKeyMessageSender(ParseUser.getCurrentUser());
                                    message.setKeyMessageStatus("Sent");
                                    message.setKeyMessageReceiver(user);

                                    ParseACL messageACL = new ParseACL();
                                    messageACL.setReadAccess(user, true);
                                    messageACL.setWriteAccess(user, true);
                                    messageACL.setWriteAccess(ParseUser.getCurrentUser(), true);
                                    messageACL.setReadAccess(ParseUser.getCurrentUser(), true);
                                    message.setACL(messageACL);
                                    message.saveInBackground();
                                    dlg.dismiss();
                                    alertDisplayer("Message Sent", "Your message has been sent!");
                                }
                            } else {
                                Log.i(TAG, "help me god");
                            }
                        }
                    });
                }
            });
        }

        // If not, display this screen instead.
        if (!isAdmin) {
            Intent intent = getIntent();
            if (intent.getExtras() != null) {

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
                            etDesc.setTextColor(Color.BLACK);
                            etUser.setText(adminUser);
                            etUser.setTextColor(Color.BLACK);
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