package com.example.betonit_bettor;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class BHFragment extends Fragment {

    String Username;
    public static final String TAG = "History Fragment";

    CustomAdapter customAdapter;
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;
    ListView listView;

    List<Bet> betsList = new ArrayList<>();
    List<String> challengers = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        getActivity().setTitle("Bet List");

        View rootView = inflater.inflate(R.layout.fragment_challenges, container, false);
        listView = rootView.findViewById(android.R.id.list);

        queryChallenges(betsList, challengers);
        customAdapter = new CustomAdapter(betsList, challengers, getActivity());

        listView.setAdapter(customAdapter);
        return rootView;
    }

    // LEAVE THIS ALONE I WAS SO DESPERATE. THIS MAKES SEARCHVIEW TOOLBAR WORK.

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.item_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search_view);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.i(TAG, " data search" + newText);
                    customAdapter.getFilter().filter(newText);
                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);

                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_view:
                // Not implemented here
                return false;
            default:
                break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }

    // a

    public class CustomAdapter extends BaseAdapter implements Filterable {

        private List<Bet> betsList;
        private List<String> challengerList;
        private List<String> filteredChallenges;
        private Context context;

        public CustomAdapter(List<Bet>betsList, List<String> challengerList, Context context) {
            this.challengerList = challengerList;
            this.filteredChallenges = challengerList;
            this.context = context;
            this.betsList = betsList;
        }

        @Override
        public int getCount() {
            return filteredChallenges.size();
        }

        @Override
        public Object getItem(int position) {
            return filteredChallenges.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.item_challenge2, null);

            TextView username = view.findViewById(R.id.tvUsername);
            TextView betName = view.findViewById(R.id.tvChallenge);
            Button btnInfo = view.findViewById(R.id.btnInfo);

            username.setText(filteredChallenges.get(position));
            betName.setText(betsList.get(position).getBetName());

            // View betting information.

            btnInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                  startActivity(new Intent(getActivity(), BetInfoActivity.class));
                    Intent i = new Intent(getActivity(), BetInfo2Activity.class);
                    i.putExtra("betObjectId", betsList.get(position).getObjectId());
                    i.putExtra("challengerUser", filteredChallenges.get(position));
                    startActivity(i);

//                    startActivity(new Intent(getActivity(), BetInfoActivity.class)
//                            .putExtra("bets", filteredChallenges.get(position)));
                }
            });

            return view;
        }

        // FILTER THAT ALLOWS YOU TO SEARCH FOR PEOPLE BASED ON USERNAME OR REAL NAME.

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();

                    if (constraint == null || constraint.length() == 0) {
                        filterResults.count = challengerList.size();
                        filterResults.values = challengerList;
                    } else {
                        String searchString = constraint.toString();
                        List<String> resultData = new ArrayList<>();

                        for (String challenger : challengerList) {
                            if (challenger.contains(searchString)) {
                                resultData.add(challenger);
                            }

                            filterResults.count = resultData.size();
                            filterResults.values = resultData;
                        }
                    }

                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults results) {
                    filteredChallenges = (List<String>) results.values;
                    notifyDataSetChanged();
                }
            };
            return filter;
        }
    }

    private void queryChallenges(final List<Bet> betsList, final List<String> challengers) {

        ParseQuery<Bet> sentBets = ParseQuery.getQuery("Bet");
        sentBets.whereContains("bet_Challenger", ParseUser.getCurrentUser().getObjectId());
        sentBets.findInBackground(new FindCallback<Bet>() {
            public void done(List<Bet> bets, ParseException e) {
                if (e == null) {
                    for (Bet bet : bets) {

                        if (!bet.getBetStatus().equals("PENDING")) {
                            // General bet info.
                            bet.getBetDescription();
                            bet.getBetChallenger();
                            bet.getBetChallengee();
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
                                    }
                                }
                            });
                        }
                    }
                } else {
                    Log.e(TAG, "what the FUCK");
                }
            }
        });

        ParseQuery<Bet> receivedBets = ParseQuery.getQuery("Bet");
        receivedBets.whereContains("bet_Challengee", ParseUser.getCurrentUser().getObjectId());
        receivedBets.findInBackground(new FindCallback<Bet>() {
            public void done(List<Bet> bets, ParseException e) {
                if (e == null) {
                    for (Bet bet : bets) {

                        if (!bet.getBetStatus().equals("PENDING")) {
                            // General bet info.
                            bet.getBetDescription();
                            bet.getBetChallenger();
                            bet.getBetChallengee();
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
                                    }
                                }
                            });
                        }
                    }
                } else {
                    Log.e(TAG, "what the FUCK");
                }
            }
        });

    }

}