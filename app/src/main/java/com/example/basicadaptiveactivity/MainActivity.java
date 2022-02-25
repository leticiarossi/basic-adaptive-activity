package com.example.basicadaptiveactivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigationrail.NavigationRailView;
import com.google.android.material.snackbar.Snackbar;

/** An adaptive Activity that changes the main navigation component according to screen size. */
public class MainActivity extends AppCompatActivity {

    private static final int MEDIUM_SCREEN_WIDTH_SIZE = 600;
    private static final int LARGE_SCREEN_WIDTH_SIZE = 1240;

    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView modalNavDrawer = findViewById(R.id.modal_nav_drawer);
        FloatingActionButton fab = findViewById(R.id.fab);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        NavigationRailView navRail = findViewById(R.id.nav_rail);
        NavigationView navDrawer = findViewById(R.id.nav_drawer);
        ExtendedFloatingActionButton navFab = findViewById(R.id.nav_fab);
        Configuration configuration = getResources().getConfiguration();

        // Update the visibility of the main navigation view components according to screen size.
        int screenWidth = configuration.screenWidthDp;
        updateNavigationViewLayout(
                screenWidth,
                drawerLayout,
                modalNavDrawer,
                fab,
                bottomNav,
                navRail,
                navDrawer,
                navFab);

        // Set up listeners.
        fab.setOnClickListener(view ->
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show());
        navFab.setOnClickListener(view ->
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show());
        modalNavDrawer.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        modalNavDrawer.setCheckedItem(item);
                        drawerLayout.closeDrawer(modalNavDrawer);
                        return true;
                    }
                });

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new MainFragment())
                .commit();
    }

    /**
     * Updates the visibility of the main navigation view components according to screen size.
     *
     * <p>The small screen layout should have a bottom navigation and optionally a fab. The medium
     * layout should have a navigation rail with a fab, and the large layout should have a
     * navigation drawer with an extended fab.
     */
    private void updateNavigationViewLayout(
            int screenWidth,
            @NonNull DrawerLayout drawerLayout,
            @NonNull NavigationView modalNavDrawer,
            @NonNull FloatingActionButton fab,
            @NonNull BottomNavigationView bottomNav,
            @NonNull NavigationRailView navRail,
            @NonNull NavigationView navDrawer,
            @NonNull ExtendedFloatingActionButton navFab) {

        // Set navigation menu button to show a modal navigation drawer in medium screens.
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        setNavRailButtonOnClickListener(
                drawerLayout, navRail.getHeaderView().findViewById(R.id.nav_button), modalNavDrawer);
        setModalDrawerButtonOnClickListener(
                drawerLayout,
                modalNavDrawer.getHeaderView(0).findViewById(R.id.nav_button),
                modalNavDrawer);

        if (screenWidth < MEDIUM_SCREEN_WIDTH_SIZE) {
            // Small screen
            fab.setVisibility(View.VISIBLE);
            bottomNav.setVisibility(View.VISIBLE);
            navRail.setVisibility(View.GONE);
            navDrawer.setVisibility(View.GONE);
        } else if (screenWidth < LARGE_SCREEN_WIDTH_SIZE) {
            // Medium screen
            fab.setVisibility(View.GONE);
            bottomNav.setVisibility(View.GONE);
            navRail.setVisibility(View.VISIBLE);
            navDrawer.setVisibility(View.GONE);
            navFab.shrink();
        } else {
            // Large screen
            fab.setVisibility(View.GONE);
            bottomNav.setVisibility(View.GONE);
            navRail.setVisibility(View.GONE);
            navDrawer.setVisibility(View.VISIBLE);
            navFab.extend();
        }
    }

    /* Sets navigation rail's header button to open the modal navigation drawer. */
    private void setNavRailButtonOnClickListener(
            @NonNull DrawerLayout drawerLayout,
            @NonNull View navButton,
            @NonNull NavigationView modalDrawer) {
        navButton.setOnClickListener(v -> drawerLayout.openDrawer(modalDrawer));
    }

    /* Sets modal navigation drawer's header button to close the drawer. */
    private void setModalDrawerButtonOnClickListener(
            @NonNull DrawerLayout drawerLayout,
            @NonNull View button,
            @NonNull NavigationView modalDrawer) {
        button.setOnClickListener(v -> drawerLayout.closeDrawer(modalDrawer));
    }
}