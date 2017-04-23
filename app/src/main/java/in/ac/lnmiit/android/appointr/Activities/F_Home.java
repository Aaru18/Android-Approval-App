package in.ac.lnmiit.android.appointr.Activities;

import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.BlockingQueue;

import in.ac.lnmiit.android.appointr.Adapters.CategoryAdapter;
import in.ac.lnmiit.android.appointr.DatabaseConnections.DatabaseCalls;
import in.ac.lnmiit.android.appointr.DatabaseConnections.DatabaseHelper;
import in.ac.lnmiit.android.appointr.Functions.SessionManagement;
import in.ac.lnmiit.android.appointr.R;
import in.ac.lnmiit.android.appointr.models.Request;
import in.ac.lnmiit.android.appointr.models.RequestReq;

public class F_Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    private static final String TAG = Appoint.class.getSimpleName();
    SessionManagement session ;
    private Toolbar toolbar;
    List<Request> requests;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faculty_home);
        session = new SessionManagement(getApplicationContext());
        DatabaseHelper help = new DatabaseHelper(getApplicationContext());


        toolbar = (Toolbar) findViewById(R.id.faculty_toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_faculty);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_faculty);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        ImageView image =(ImageView) header.findViewById(R.id.img_nav);
        TextView name =   (TextView) header.findViewById(R.id.name_header);
        TextView email = (TextView) header.findViewById(R.id.email_header);

        image.setImageResource(R.drawable.ic_team);
        name.setText(help.getFaculty(session.getID()).getFaculty_name());
        email.setText(help.getFaculty(session.getID()).getDepartments());
        help.closeDB();
        new PrefetchData().execute();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_faculty);
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
            Intent intent = new Intent(F_Home.this, P_Reset.class);
            intent.putExtra("activity","faculty");
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_logout) {
            session.logoutUser();
            new DatabaseHelper(getApplicationContext()).deleteDB(getApplicationContext());
            Toast.makeText(getApplicationContext(), "User Successfully Logged Out", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(F_Home.this, login_home.class);
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

    private class PrefetchData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected Void doInBackground(Void... arg0) {

            BlockingQueue<RequestReq> requestss =  (new DatabaseCalls(getApplicationContext())).showRequests();
            try {
                RequestReq requestReq = requestss.take();
                requests = requestReq.getRequests();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);


            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            findViewById(R.id.tabs1).setVisibility(View.VISIBLE);
            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs1);
            ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager1);
            CategoryAdapter adapter = new CategoryAdapter(getApplicationContext(), getSupportFragmentManager());
            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);

        }

    }
    public List<Request> getRequestss(){
        return requests;

    }

}