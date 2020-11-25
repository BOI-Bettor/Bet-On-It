package com.example.betonit_bettor;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ChFragment extends Fragment {

    String Username;
    public static final String TAG = "Challenge Fragment";

    ListView listView;
    CustomAdapter customAdapter;
    List<Bet> betsList = new ArrayList<>();
    List<String> challengers = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_challenges, container, false);

        queryChallenges(betsList, challengers);

        listView = rootView.findViewById(R.id.listview);
        customAdapter = new CustomAdapter(challengers, this.getContext());
        listView.setAdapter(customAdapter);

        return rootView;

    }

    public class CustomAdapter extends BaseAdapter {

        private List<String> arrayList;
        private Context context;

        public CustomAdapter(List<String> arrayList, Context context) {
            this.arrayList = arrayList;
            this.context = context;
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }
        @Override
        public Object getItem(int position) {
            return position;
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
             convertView = getLayoutInflater().inflate(R.layout.item_challenge, null);

            TextView userName = convertView.findViewById(R.id.tvUsername);
            userName.setText(arrayList.get(position));
            Log.i(TAG, arrayList.get(0));

            return convertView;
        }
    }

    private void queryChallenges(final List<Bet> betsList, final List<String> challengers) {

        ParseQuery<Bet> receivedBets = ParseQuery.getQuery("Bet");
        receivedBets.whereContains("bet_User_Challengee", ParseUser.getCurrentUser().getObjectId());
        receivedBets.findInBackground(new FindCallback<Bet>() {
            public void done(List<Bet> bets, ParseException e) {
                if (e == null) {
                    for (Bet bet : bets) {

                        // General bet info.
                        bet.getBetDescription();
                        bet.setBetChallengee(ParseUser.getCurrentUser());
                        bet.setBetChallenger(bet.getBetChallenger());
                        bet.getBetName();
                        bet.getBetStatus();
                        bet.getBetAmount();
                        betsList.add(bet);

                        // THIS IS AWFUL. But, anyway, queries the challenger.
                        ParseQuery<ParseUser> query = ParseUser.getQuery();
                        query.whereEqualTo("objectId", bet.getBetChallenger().getObjectId());
                        query.findInBackground(new FindCallback<ParseUser>() {
                            public void done(List<ParseUser> objects, ParseException e) {
                                if (e == null) {
                                    for (ParseUser object : objects) {
                                        challengers.add(object.getUsername());
                                    }
                                } else {
                                    Log.e(TAG, "Please, kill me.");
                                }
                            }
                        });
                    }
                } else {
                    Log.e(TAG, "what the FUCK");
                }
            }
        });

    }

}

//                        Log.i(TAG, "Bet Name: " + bet.getString("bet_Name"));
//                        Log.i(TAG, bet.getParseUser());
//                    Log.i(TAG, ParseUser.getCurrentUser().getUsername() + " received " + bets.size() + " challenges.");
