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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
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

public class FOFragment extends Fragment {

    public static final String TAG = "Find Opponent Fragment";
    ListView listView;
    private String fullname;
    CustomAdapter customAdapter;
    List<UsersModel> usersList = new ArrayList<>();


    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        getActivity().setTitle("Find Opponents");

        View rootView = inflater.inflate(R.layout.fragment_findopponent, container, false);
        listView = (ListView) rootView.findViewById(R.id.listview);

        queryUsers(usersList);
        customAdapter = new CustomAdapter(usersList, getActivity());
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


    // ADD USERS TO LIST

    public void queryUsers(final List<UsersModel> usersList) {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> users, ParseException e) {

                if (e == null) {
                    Log.i(TAG, "Users stashed in background.");
                    for (ParseUser user : users) {
                        fullname = user.getString("user_First") + " " + user.getString("user_Last");
                        UsersModel usersModel = new UsersModel(user.getUsername(), fullname, R.drawable.ic_user);
                        usersList.add(usersModel);
                    }
                } else {
                    Log.e(TAG, "Something went wrong.");
                }
            }
        });
    }

    // FUCKING ADAPTER

    public class CustomAdapter extends BaseAdapter implements Filterable {

        private List<UsersModel> usersModelsL;
        private List<UsersModel> filteredList;
        private Context context;

        public CustomAdapter(List<UsersModel> usersModelsL, Context context) {
            this.usersModelsL = usersModelsL;
            this.filteredList = usersModelsL;
            this.context = context;
        }

        @Override
        public int getCount() {
            return filteredList.size();
        }

        @Override
        public Object getItem(int position) {
            return filteredList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.item_result, null);

            TextView usernames = view.findViewById(R.id.name);
            TextView nameRN = view.findViewById(R.id.nameRN);
            ImageView imageView = view.findViewById(R.id.images);

            usernames.setText(filteredList.get(position).getUserName());
            nameRN.setText(filteredList.get(position).getFullName());
            imageView.setImageResource(filteredList.get(position).getImg());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "item clicked");
                    startActivity(new Intent(getActivity(), UserViewActivity.class).
                            putExtra("users", filteredList.get(position)));

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
                        filterResults.count = usersModelsL.size();
                        filterResults.values = usersModelsL;
                    } else {
                        String searchString = constraint.toString();
                        List<UsersModel> resultData = new ArrayList<>();

                        for (UsersModel usersModel : usersModelsL) {
                            if (usersModel.getUserName().contains(searchString) || usersModel.getFullName().contains(searchString)) {
                                resultData.add(usersModel);
                            }

                            filterResults.count = resultData.size();
                            filterResults.values = resultData;
                        }
                    }

                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults results) {
                    filteredList = (List<UsersModel>) results.values;
                    notifyDataSetChanged();
                }
            };
            return filter;
        }
    }



}


