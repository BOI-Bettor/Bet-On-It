package com.example.betonit_bettor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    public static final String TAG = "SearchActivity";
    private String fullname;

    ListView listView;
    CustomAdapter customAdapter;

    List<UsersModel> usersList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        listView = findViewById(R.id.listview);

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> users, ParseException e) {

                if (e == null) {
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

        customAdapter = new CustomAdapter(usersList, this);
        listView.setAdapter(customAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_view);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i(TAG, " data search" + newText);
                customAdapter.getFilter().filter(newText);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.search_view) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

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
            convertView = getLayoutInflater().inflate(R.layout.item_result, null);

            TextView usernames = convertView.findViewById(R.id.name);
            TextView nameRN = convertView.findViewById(R.id.nameRN);
            ImageView imageView = convertView.findViewById(R.id.images);

            usernames.setText(filteredList.get(position).getUserName());
            nameRN.setText(filteredList.get(position).getFullName());
            imageView.setImageResource(filteredList.get(position).getImg());

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "item clicked");
                    startActivity(new Intent(SearchActivity.this, UserViewActivity.class).
                            putExtra("users", filteredList.get(position)));

                }
            });

            return convertView;
        }

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


