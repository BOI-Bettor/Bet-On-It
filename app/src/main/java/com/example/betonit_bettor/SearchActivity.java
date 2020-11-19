package com.example.betonit_bettor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    ListView listView;
    String userSearch;
    public static final String TAG = "SearchActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        listView = findViewById(R.id.listview);
        userSearch = "01LakersFan";
        queryUsers(userSearch);
//        queryUsers2(userSearch);
    }

    private void queryUsers(String userSearch) {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", userSearch);
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> users, ParseException e) {
                if (e == null) {
                    Log.i(TAG, "User: " + users);
                } else {
                    Log.e(TAG, "Something went wrong.");
                }
            }
        });
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

                Log.e("Main", " data search" + newText);
//                customAdapter.getFilter().filter(newText);
                return true;
            }
        });

        return true;

    }

//    private void queryUsers2(String userSearch) {
//        ParseQuery<User> query = ParseQuery.getQuery(User.class);
//        query.whereEqualTo("username", userSearch);
//        query.findInBackground(new FindCallback<User>() {
//            public void done(List<User> users, ParseException e) {
//                if (e != null) {
//                    Log.e(TAG, "Something went wrong.", e);
//                }
//                for (User user : users)
//                    Log.i(TAG, "Users: " + user.getUser());
//            }
//        });
//    }
}




