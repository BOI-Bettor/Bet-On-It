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
import com.example.betonit_bettor.PendingCasesAdapter;
import com.example.betonit_bettor.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class PendingCasesFragment extends Fragment {

    public static final String TAG = "PendingCasesFragment";
    private RecyclerView rvPendingCases;
    protected PendingCasesAdapter adapter;
    protected List<Case> allPosts;

    public PendingCasesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Choose Cases");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pending_cases, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvPendingCases = view.findViewById(R.id.rvPendingCases);
        allPosts = new ArrayList<>();
        adapter = new PendingCasesAdapter(getContext(), allPosts);

        rvPendingCases.setAdapter(adapter);
        rvPendingCases.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.notifyDataSetChanged();
        queryMyCases();

    }

    private void queryMyCases()
    {
        // Define the class we would like to query
        ParseQuery<Case> query = ParseQuery.getQuery(Case.class);
        // Define our query conditions
        query.whereEqualTo(Case.KEY_CASE_STATUS, "PENDING");

        query.findInBackground(new FindCallback<Case>() {
            public void done(List<Case> cases, ParseException e) {
                if (e != null) {
                    // Access the array of results here
                    Log.e(TAG, "Error: " + e.getMessage(), e);
                }
                for (Case case1 : cases) {

                    Log.i(TAG, "MyCase: " + case1.getKeyCaseBetId().getObjectId().toString());
                    Log.i(TAG, "Arbitrator: " + case1.getKeyCaseBetId().getObjectId().toString());
                }

                allPosts.addAll(cases);
                adapter.notifyDataSetChanged();
            }
        });
    }
}