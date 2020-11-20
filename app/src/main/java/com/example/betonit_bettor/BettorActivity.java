package com.example.betonit_bettor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.parse.ParseUser;

public class BettorActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = "BettorActivity";
    private DrawerLayout drawer;
    final ParseUser parseUser = ParseUser.getCurrentUser();
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bettor);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView userName = findViewById(R.id.userTV);
        TextView userRN = findViewById(R.id.userRNTV);

//        username = parseUser.getUsername();
//        userName.setText(username);
////        userRN.setText();

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new CWFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_wager);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_wager:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new CWFragment()).commit();
                break;

            case R.id.nav_challenge:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ChFragment()).commit();
                break;

            case R.id.nav_users:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new FOFragment()).commit();
                Intent i = new Intent(this, SearchActivity.class);
                startActivity(i);
                break;

            case R.id.nav_hist:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new BHFragment()).commit();
                break;

            case R.id.nav_acceptcase:
                Toast.makeText(this, "Not our use case.", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_showcases:
                Toast.makeText(this, "Also not our use case.", Toast.LENGTH_SHORT).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}