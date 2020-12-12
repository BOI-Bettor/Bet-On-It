package com.example.betonit_bettor;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CWFragment extends Fragment {

    public static final String TAG = "Create Wager Fragment";
    String challengee, type, startDate, endDate, desc, betName;
    double amount;
    Date dateStart, dateEnd;
    Boolean userExist;
    Bet newWager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Create Wager");
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        View rootView = inflater.inflate(R.layout.fragment_createwager, container, false);

        // Set EditTexts.

        final EditText etBetName = rootView.findViewById(R.id.etBetName);
        final EditText etUsername = rootView.findViewById(R.id.etUsername);
        final EditText etAmount = rootView.findViewById(R.id.etAmount);
        final EditText etType = rootView.findViewById(R.id.etType);
        final EditText etStartDate = rootView.findViewById(R.id.etStartDate);
        final EditText etRescDate = rootView.findViewById(R.id.etRescDate);
        final EditText etDesc = rootView.findViewById(R.id.etDesc);
        final Button btnConfirm = rootView.findViewById(R.id.btnConfirm);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Validate that nothing is empty.

                boolean validationError = false;

                StringBuilder validationErrorMessage = new StringBuilder("Please, insert ");
                if (etBetName.getText().toString().isEmpty()) {
                    validationError = true;
                    validationErrorMessage.append("a bet name");
                }
                if (etUsername.getText().toString().isEmpty()) {
                    if (validationError) {
                        validationErrorMessage.append(" and ");
                    }
                    validationError = true;
                    validationErrorMessage.append("an opponent");
                }
                if (etAmount.getText().toString().isEmpty()) {
                    if (validationError) {
                        validationErrorMessage.append(" and ");
                    }
                    validationError = true;
                    validationErrorMessage.append("a bet amount");
                }

                if (etStartDate.getText().toString().isEmpty()) {
                    if (validationError) {
                        validationErrorMessage.append(" and ");
                    }
                    validationError = true;
                    validationErrorMessage.append("a start date");
                }

                if (etRescDate.getText().toString().isEmpty()) {
                    if (validationError) {
                        validationErrorMessage.append(" and ");
                    }
                    validationError = true;
                    validationErrorMessage.append("an end date");
                }

                else {
                    if ((etDesc.getText().toString().isEmpty())) {
                        if (validationError) {
                            validationErrorMessage.append(" and ");
                        }
                        validationError = true;
                        validationErrorMessage.append("a description");
                    }
                }
                validationErrorMessage.append(".");

                if (validationError) {
                    Toast.makeText(getActivity(), validationErrorMessage.toString(), Toast.LENGTH_LONG).show();
                    return;
                }

                // Grab info from EditTexts.

                betName = etBetName.getText().toString();
                challengee = etUsername.getText().toString();
                amount = Double.parseDouble(etAmount.getText().toString());
                type = etType.getText().toString();
                try {
                    DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                    dateStart = formatter.parse(etStartDate.getText().toString());
//            user.put("user_DOB", dateObject);

                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
                try {
                    DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                    dateEnd = formatter.parse(etRescDate.getText().toString());
//            user.put("user_DOB", dateObject);

                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
                desc = etDesc.getText().toString();

                // Set up progress dialog.

                final ProgressDialog dlg = new ProgressDialog(getActivity());
                dlg.setTitle("Please, wait a moment.");
                dlg.setMessage("Sending your bet...");
                dlg.show();

                // Create new bet if user exists.

                    ParseQuery<ParseUser> query = ParseUser.getQuery();
                    query.whereEqualTo("username", challengee);
                    query.findInBackground(new FindCallback<ParseUser>() {
                        public void done(List<ParseUser> users, ParseException e) {
                            if (users == null || users.isEmpty()) {
                                dlg.dismiss();
                                alertDisplayer("User Not Found", "The user you are challenging does not exist.");
                            }

                            if (e == null && users != null) {
                                for (ParseUser user : users) {

                                    ParseACL betACL = new ParseACL();
                                    betACL.setReadAccess(user, true);
                                    betACL.setWriteAccess(user, true);
                                    betACL.setReadAccess(ParseUser.getCurrentUser(), true);
                                    betACL.setWriteAccess(ParseUser.getCurrentUser(), true);

                                    Bet bet = new Bet();
                                    bet.setACL(betACL);
                                    bet.setBetName(betName);
                                    bet.setBetChallengee(user);
                                    bet.setBetChallenger(ParseUser.getCurrentUser());
                                    bet.setBetAmount(amount);
                                    bet.setBetType(type);
                                    bet.setBetStart(dateStart);
                                    bet.setBetEnd(dateEnd);
                                    bet.setBetDescription(desc);
                                    bet.setBetStatus("PENDING");
                                    bet.saveInBackground();

                                    dlg.dismiss();
                                    alertDisplayer("Bet Received", "Your challenge has been sent!");
                                }
                            } else {
                                Log.i(TAG, "help me god");
                            }
                        }
                    });
            }
        });


        return rootView;
    }

    private void alertDisplayer(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
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
