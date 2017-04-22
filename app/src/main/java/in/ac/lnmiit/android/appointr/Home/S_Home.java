package in.ac.lnmiit.android.appointr.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import in.ac.lnmiit.android.appointr.Adapters.CategoryAdapter;
import in.ac.lnmiit.android.appointr.ApiCall.SessionManagement;
import in.ac.lnmiit.android.appointr.Appoint;
import in.ac.lnmiit.android.appointr.LoginAc.P_Reset;
import in.ac.lnmiit.android.appointr.LoginAc.login_home;
import in.ac.lnmiit.android.appointr.R;

public class S_Home extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {


    private Toolbar toolbar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_home);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager2);


        CategoryAdapter adapter = new CategoryAdapter(this, getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Find the tab layout that shows the tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs2);


        tabLayout.setupWithViewPager(viewPager);


        toolbar = (Toolbar) findViewById(R.id.student_toolbar);
        setSupportActionBar(toolbar);
        ImageView appoint = (ImageView) findViewById(R.id.create);
        appoint.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(S_Home.this, Appoint.class);
                startActivity(intent);
                finish();
                // Do something in response to button click
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_student);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_student);
        navigationView.setNavigationItemSelectedListener(this);

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_student);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_change_password) {
            Intent intent = new Intent(S_Home.this, P_Reset.class);
            intent.putExtra("activity","student");
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_logout) {
            SessionManagement session = new SessionManagement(getApplicationContext());
            session.logoutUser();
            Toast.makeText(getApplicationContext(), "User Successfully Logged Out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(S_Home.this, login_home.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_feedback) {

        } else if (id == R.id.nav_aboutus) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_faculty);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}

