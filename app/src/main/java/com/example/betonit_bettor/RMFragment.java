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

public class RMFragment extends Fragment {

    public static final String TAG = "Message Fragment";
    ListView listView;
    CustomAdapter customAdapter;
    List<Message> inbox = new ArrayList<>();
    List<String> admins = new ArrayList<>();

    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        getActivity().setTitle("Inbox");

        View rootView = inflater.inflate(R.layout.fragment_message, container, false);
        listView = rootView.findViewById(R.id.listview);

        queryMessages(inbox);
        customAdapter = new CustomAdapter(inbox, admins, getActivity());
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

    // FUCK OFF MESSAGE QUERY

    private void queryMessages(final List<Message> inbox) {
        ParseQuery<Message> receivedMessages = ParseQuery.getQuery("Message");
        receivedMessages.whereContains("message_Receiver", ParseUser.getCurrentUser().getObjectId());
        receivedMessages.findInBackground(new FindCallback<Message>() {
            public void done(List<Message> messages, ParseException e) {
                if (e == null) {
                    for (Message message : messages) {
                        message.getKeyMessageBody();
                        message.getKeyMessageStatus();
                        message.getKeyMessageSender();
                        message.getKeyMessageReceiver();

                        // i fucking hate it here, get admin name
                        ParseQuery<ParseUser> query = ParseUser.getQuery();
                        query.whereEqualTo("objectId", message.getKeyMessageSender().getObjectId());
                        query.findInBackground(new FindCallback<ParseUser>() {
                            public void done(List<ParseUser> objects, ParseException e) {
                                if (e == null) {
                                    for (ParseUser object : objects) {
                                        admins.add(object.getUsername());
                                    }
                                } else {
                                    Log.e(TAG, "Please, kill me.");
                                }
                            }
                        });
                        inbox.add(message);
                    }
                } else {
                    Log.e(TAG, "what the FUCK");
                }
            }
        });

    }

    // FUCKING ADAPTER

    public class CustomAdapter extends BaseAdapter implements Filterable {

        private List<Message> inboxList;
        private List<String> adminList;
        private List<Message> filteredList;
        private Context context;

        public CustomAdapter(List<Message> inboxList, List<String> adminList, Context context) {
            this.inboxList = inboxList;
            this.adminList = adminList;
            this.filteredList = inboxList;
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
            TextView idfk = view.findViewById(R.id.nameRN);
            usernames.setText(adminList.get(position));
            idfk.setText("Message from admin.");

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, filteredList.get(position).getObjectId());
                    Intent i = new Intent(getActivity(), MessageViewActivity.class);
                    i.putExtra("messageObjId", filteredList.get(position).getObjectId());
                    i.putExtra("adminUser", adminList.get(position));
                    startActivity(i);
//
//                    startActivity(new Intent(getActivity(), MessageViewActivity.class).
//                            putExtra("messageObjId", filteredList.get(position).getObjectId()));

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
                        filterResults.count = inboxList.size();
                        filterResults.values = inboxList;
                    } else {
                        String searchString = constraint.toString();
                        List<Message> resultData = new ArrayList<>();

                        for (Message message : inboxList) {
                            if (message.getKeyMessageSender().getUsername().contains(searchString)) {
                                resultData.add(message);
                            }

                            filterResults.count = resultData.size();
                            filterResults.values = resultData;
                        }
                    }

                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults results) {
                    filteredList = (List<Message>) results.values;
                    notifyDataSetChanged();
                }
            };
            return filter;
        }
    }

}


