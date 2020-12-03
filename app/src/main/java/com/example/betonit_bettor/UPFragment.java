package com.example.betonit_bettor;

import android.app.Activity;
import android.app.MediaRouteButton;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.parceler.Parcels;

public class UPFragment extends Fragment {

    public static final String TAG = "User Fragment";


    final ParseUser parseUser = ParseUser.getCurrentUser();
    String username, password, firstName, lastName, email, phone, address,
            city, state, country, faveBet, fullName;
    boolean btnClicked = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_userprofile, container, false);
        getActivity().setTitle("User Profile");

        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Point to EditTexts.
        final EditText pFName = rootView.findViewById(R.id.profFName);
        final EditText pLName = rootView.findViewById(R.id.profLName);
        final EditText pEmail = rootView.findViewById(R.id.profEmail);
        final EditText pPhone = rootView.findViewById(R.id.profPhone);
        final EditText pAddr = rootView.findViewById(R.id.profAddr);
        final EditText pCity = rootView.findViewById(R.id.profCity);
        final EditText pState = rootView.findViewById(R.id.profState);
        final EditText pCountry = rootView.findViewById(R.id.profCountry);
        final EditText pBet = rootView.findViewById(R.id.profBets);

        editableSwitchFalse(pFName, pLName, pEmail,  pPhone,
                pAddr,  pCity,  pState,  pCountry,  pBet);

        // Get key fields from the user
        username = parseUser.getUsername();
        password = parseUser.getString("password");
        firstName = parseUser.getString("user_First");
        lastName = parseUser.getString("user_Last");
        fullName = firstName + " " + lastName;
        email = parseUser.getEmail();
        phone = parseUser.getString("user_Phone");
        address = parseUser.getString("user_Addr");
        city = parseUser.getString("user_City");
        state = parseUser.getString("user_State");
        country = parseUser.getString("user_Country");
        faveBet = parseUser.getString("user_Fave_Bet");

        // Display username and real name.
        TextView profUName = rootView.findViewById(R.id.prof_name);
        final TextView profRN = rootView.findViewById(R.id.prof_RN);

        final Button editBtn = rootView.findViewById(R.id.btnSave);
        editBtn.setText(R.string.editprof);

        profUName.setText(username);
        profRN.setText(fullName);

        // Set all variables to current user data.
        pFName.setText(firstName);
        pLName.setText(lastName);
        pEmail.setText(email);
        pPhone.setText(phone);
        pAddr.setText(address);
        pCity.setText(city);
        pState.setText(state);
        pCountry.setText(country);
        pBet.setText(faveBet);

        // Clicking Edit Button.
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!btnClicked) {

                    Log.i(TAG, "Clicked to change info.");
                    editableSwitchTrue(pFName, pLName, pEmail,  pPhone,
                            pAddr,  pCity,  pState,  pCountry,  pBet);

                    editBtn.setText("Save Changes");
                    btnClicked = true;

                } else {

                    String userID = parseUser.getObjectId();
                    ParseQuery<ParseUser> query = ParseUser.getQuery();
                    query.getInBackground(userID, new GetCallback<ParseUser>() {
                        @Override
                        public void done(ParseUser parseUser, ParseException e) {
                            if (e == null) {

                                Log.i(TAG, "Editing info...");

                                firstName = pFName.getText().toString();
                                lastName = pLName.getText().toString();
                                email = pEmail.getText().toString();
                                phone = pPhone.getText().toString();
                                address = pAddr.getText().toString();
                                city = pCity.getText().toString();
                                state = pState.getText().toString();
                                country = pCountry.getText().toString();
                                faveBet = pBet.getText().toString();

                                parseUser.put("user_First", firstName);
                                parseUser.put("user_Last", lastName);
                                parseUser.put("email", email);
                                parseUser.put("user_Phone", phone);
                                parseUser.put("user_Addr", address);
                                parseUser.put("user_City", city);
                                parseUser.put("user_State", state);
                                parseUser.put("user_Country", country);
                                parseUser.put("user_Fave_Bet", faveBet);

                                // SAVING THIS INFO TO THE DATABASE
                                parseUser.saveInBackground();
                                editableSwitchFalse(pFName, pLName, pEmail,  pPhone,
                                        pAddr,  pCity,  pState,  pCountry,  pBet);

                            }

                        }
                    });

                    editBtn.setText("Edit Profile");
                    fullName = firstName + " " + lastName;
                    profRN.setText(fullName);
                    btnClicked = false;
                    Log.i(TAG, "Changes saved.");
                }
            }
        });

        return rootView;
    }

    public void editableSwitchFalse(EditText pFName, EditText pLName, EditText pEmail, EditText pPhone,
                                    EditText pAddr, EditText pCity, EditText pState, EditText pCountry, EditText pBet) {

        pFName.setText(firstName);
        pLName.setText(lastName);
        pEmail.setText(email);
        pPhone.setText(phone);
        pAddr.setText(address);
        pCity.setText(city);
        pState.setText(state);
        pCountry.setText(country);
        pBet.setText(faveBet);

        pFName.setEnabled(false);
        pLName.setEnabled(false);
        pEmail.setEnabled(false);
        pPhone.setEnabled(false);
        pAddr.setEnabled(false);
        pCity.setEnabled(false);
        pState.setEnabled(false);
        pCountry.setEnabled(false);
        pBet.setEnabled(false);
    }

    public void editableSwitchTrue(EditText pFName, EditText pLName, EditText pEmail, EditText pPhone,
                                   EditText pAddr, EditText pCity, EditText pState, EditText pCountry, EditText pBet) {
        pFName.setEnabled(true);
        pLName.setEnabled(true);
        pEmail.setEnabled(true);
        pPhone.setEnabled(true);
        pAddr.setEnabled(true);
        pCity.setEnabled(true);
        pState.setEnabled(true);
        pCountry.setEnabled(true);
        pBet.setEnabled(true);
    }

}
