package com.example.betonit_bettor;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.betonit_bettor.Case;
import com.example.betonit_bettor.Bet;
import com.example.betonit_bettor.R;
import com.example.betonit_bettor.RatedCaseAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class RatedCasesFragment extends Fragment {

    public static final String TAG = "RatedCasesFragment";
    private RecyclerView rvRatedCases;
    protected RatedCaseAdapter adapter;
    protected List<Case> allPosts;


    public RatedCasesFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
            getActivity().setTitle("Rate A Case");
        return inflater.inflate(R.layout.fragment_rated_cases, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvRatedCases = view.findViewById(R.id.rvRatedCases);
        allPosts = new ArrayList<>();
        adapter = new RatedCaseAdapter(getContext(), allPosts);

        rvRatedCases.setAdapter(adapter);
        rvRatedCases.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.notifyDataSetChanged();
        queryRatedCases();
    }

    private void queryRatedCases()
    {
        // Define the class we would like to query
        final ParseQuery<Case> query = ParseQuery.getQuery(Case.class);
        // Define our query conditions
        query.whereEqualTo(Case.KEY_CASE_STATUS, "RESOLVED"); // Resolved case
        query.whereNotEqualTo(Case.KEY_CASE_ARBITRATOR, ParseUser.getCurrentUser()); // not me
        query.whereEqualTo(Case.KEY_CASE_ISRATED, false); // not rated
        query.whereNotEqualTo(Case.KEY_CASE_RATER, ParseUser.getCurrentUser()); // no rater

        // Similar to previous query, find the bet that matches the Case's BetId

        // Execute the find asynchronously
        query.findInBackground(new FindCallback<Case>() {
            public void done(List<Case> cases, ParseException e) {
                if (e == null) {
                    // Access the array of results here
                    for (int i = 0; i < cases.size(); i++)
                    {
                        Log.i(TAG, "RatedCase: " + cases.get(i).getKeyCaseBetId().getObjectId().toString());
                        Log.i(TAG, "Arbitrator: " + cases.get(i).getKeyCaseArbitrator().getObjectId().toString());
                    }
//                    Toast.makeText(TodoItemsActivity.this, firstItemId, Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "Error: " + e.getMessage(), e);
                }
                allPosts.addAll(cases);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
