package com.ftocs.myapp.uiActivities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ftocs.myapp.R;
import com.ftocs.myapp.fragment.HomeFragment;
import com.ftocs.myapp.utils.Constants;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TabLayout.OnTabSelectedListener {

    private TabLayout tabLayout;
    public static TextView tv_appBar_title;

    private ColorStateList colors;
    private Boolean exit = false;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public static Toolbar toolbar;
    public static ActionBarDrawerToggle toggle;
    public static NavigationView navigationView;
    public static DrawerLayout drawer;
    private String prefUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.gradient_end_color));
        navigationView.setItemIconTintList(null);
        NavigationMenuView navMenuView = (NavigationMenuView) navigationView.getChildAt(0);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(HomeActivity.this,
                LinearLayoutManager.VERTICAL);
        navMenuView.addItemDecoration(dividerItemDecoration);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.nave_decorator));
        //init custom views
        init();
        //adding tabs
        if (getIntent().getStringExtra(Constants.SKIP)!=null)
        {
            Constants.skip = "skip";
        }

        if (Constants.skip != null) {
            addTabsN(tabLayout);
        } else {
            addTabs(tabLayout);
        }

        if (Constants.skip != null )
        {

            navigationView = (NavigationView) findViewById(R.id.nav_view);
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.sign_out).setVisible(false);
            nav_Menu.findItem(R.id.sign_in).setVisible(true);
        }


        if (Constants.skip == null)
        {

            navigationView = (NavigationView) findViewById(R.id.nav_view);
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.sign_out).setVisible(true);
            nav_Menu.findItem(R.id.sign_in).setVisible(false);
        }


        this.setTitle("Home");

        tabLayout.setOnTabSelectedListener(this);

        changeStateColorTab(tabLayout, colors);
        tabLayout.getTabAt(0).select();

        sharedPreferences = getSharedPreferences(Constants.MY_PREF_LOGIN, Context.MODE_PRIVATE);
        prefUserId = sharedPreferences.getString(Constants.PREF_USER_user_Type, Constants.NULL);



    }

    private void addTabs(TabLayout tabLayout) {

        tabLayout.addTab(tabLayout.newTab().setText("Home").setIcon(R.drawable.ic_menu_gallery));
        tabLayout.addTab(tabLayout.newTab().setText("Kinno Booking").setIcon(R.drawable.ic_dund));

    }

    private void addTabsN(TabLayout tabLayout) {

        tabLayout.addTab(tabLayout.newTab().setText("Home").setIcon(R.drawable.ic_menu_gallery));
        tabLayout.addTab(tabLayout.newTab().setText("Kinno Booking").setIcon(R.drawable.ic_dund));
    }

    private void init() {

        tabLayout = findViewById(R.id.tabLayout);
        tv_appBar_title = findViewById(R.id.tv_appBar_title);


    }

    private void changeStateColorTab(TabLayout tabLayout, ColorStateList colors) {

        if (Build.VERSION.SDK_INT >= 23) {
            colors = getResources().getColorStateList(R.color.tab_icon, getTheme());
        } else {
            colors = getResources().getColorStateList(R.color.tab_icon);
        }

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            Drawable icon = tab.getIcon();

            if (icon != null) {
                icon = DrawableCompat.wrap(icon);
                DrawableCompat.setTintList(icon, colors);
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.messages) {

            if (Constants.skip != null ) {
                Toast.makeText(this, "Sign in First", Toast.LENGTH_SHORT).show();
            } else {

                Intent intent = new Intent(HomeActivity.this, BookingActivity.class);
                startActivity(intent);
            }

            // Handle the camera action
        } else if (id == R.id.history) {

            if (Constants.skip != null) {
                Toast.makeText(this, "Sign in First", Toast.LENGTH_SHORT).show();
            } else {

                Intent intent = new Intent(HomeActivity.this, OrderHistoryActivity.class);
                startActivity(intent);
            }

            // Handle the camera action
        }  else if (id == R.id.payment_info) {


            Intent intent = new Intent(HomeActivity.this, PaymentInfoActivity.class);
            startActivity(intent);


            // Handle the camera action
        } else if (id == R.id.edit_profile) {

            if (Constants.skip != null && !Constants.skip.isEmpty()) {
                Toast.makeText(this, "Sign in First", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(HomeActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }


        } else if (id == R.id.about_us) {

            Intent intent = new Intent(HomeActivity.this, AboutUsActivity.class);
            startActivity(intent);


        } else if (id == R.id.contact_us) {

            Intent intent = new Intent(HomeActivity.this, ContactusActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.sign_in) {

            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);

        }else if (id == R.id.share_app) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                    "chickdrive");
            String shareMessage = "Visit this app on play store  https://play.google.com/store/apps/details?id=com.ftocs.myapp";
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                    shareMessage);
            startActivity(Intent.createChooser(sharingIntent, "Share using"));

        } else if (id == R.id.rate_app) {
            Intent xx = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.ftocs.myapp"));
            startActivity(xx);

        } else if (id == R.id.sign_out) {
            if (Constants.skip != null && !Constants.skip.isEmpty()) {
                Toast.makeText(this, "Sign in First", Toast.LENGTH_SHORT).show();
            } else {

                logOutDialog("LOGOUT", "ARE YOU SURE YOU WANT TO LOGOUT");
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Fragment fragment;
        if (Constants.skip==null ) {
            switch (tab.getPosition()) {

                case 0:

                    tv_appBar_title.setText("HOME");
                    fragment = new HomeFragment();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.simpleFrameLayout, fragment).commit();

                    break;
                case 1:
                    Intent intent = new Intent(HomeActivity.this,BookingActivity.class);
                    startActivity(intent);
                    finish();
            }
        }

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

        Fragment fragment;
        if (Constants.skip==null) {
            switch (tab.getPosition()) {

                case 0:

                    tv_appBar_title.setText("Home");
                    fragment = new HomeFragment();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.simpleFrameLayout, fragment).commit();

                    break;
            }
        }

    }

    public void logOutDialog(String title, String message) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle(title);
        builder.setMessage(message);

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // positive button logic
                        sharedPreferences = getSharedPreferences(Constants.MY_PREF_LOGIN, Context.MODE_PRIVATE);
                        editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        editor.commit();
                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish(); //
                    }
                });

        String negativeText = getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // negative button logic
                    }
                });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();


    }
}
