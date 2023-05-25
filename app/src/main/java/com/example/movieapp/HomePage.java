package com.example.movieapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

public class HomePage extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    private Toolbar toolbar;

    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        View view = findViewById(R.id.app_bar_home_page);

        toolbar = view.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);


        drawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,R.id.nav_theatre,R.id.nav_profile,R.id.nav_theatre,R.id.nav_tv, R.id.nav_setting,R.id.nav_logout)
                .setOpenableLayout(drawerLayout)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home_page);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(navController == null) return false;

                if(item.getItemId() == R.id.nav_logout){
                    signOutUser();
                    return false;
                }

                int id = item.getItemId();
                if(id == R.id.nav_home || id == R.id.nav_theatre || id == R.id.nav_profile || id == R.id.nav_add_movie || id == R.id.nav_movie || id == R.id.nav_tv || id == R.id.nav_setting){
                    navController.navigate(id);
                    navView.setCheckedItem(id);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                return true;
            }
        });

    }

    private void contactUs(){
        //rest of the code for contact us
        showToast("Contact us");
    }

    private void showAboutUs(){
        //rest of the code for about us
        showToast("About us");
    }

    private void showPolicy(){
        //rest of the code for policy
        showToast("Policy clicked");
    }

    private void signOutUser(){
        FirebaseAuth.getInstance().signOut();
        showToast("Log out successfully");
    }



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home_page);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void showToast(String message){
        Toast.makeText(HomePage.this, message, Toast.LENGTH_SHORT).show();
    }
}