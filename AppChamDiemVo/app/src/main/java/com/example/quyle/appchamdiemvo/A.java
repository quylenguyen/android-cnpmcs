package com.example.quyle.appchamdiemvo;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import Model.Common;
import View.Adapter.HomePagerAdapter;

public class A extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ViewPager viewPager;
    private FirebaseAuth firebaseAuth;
    private TabLayout tabLayout;
    private HomePagerAdapter adapter;
    TextView txtName,txtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        txtName = headerView.findViewById(R.id.txtName);
        txtEmail = headerView.findViewById(R.id.txtEmail);
        txtName.setText(Common.currentUser.Name);
        txtEmail.setText(Common.currentUser.Email);


        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.vp);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.a, menu);
        return true;
    }

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

    @SuppressLint("WrongConstant")
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.Logout) {
            firebaseAuth.signOut();
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        Intent i = getIntent();
        if(i.getBooleanExtra("isAdmin",false)) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            Fragment fragment = new Fragment();
            switch (id) {
                case R.id.QLHV:
                    fragment = new QuanLyHV();
                    break;
                case R.id.QLDT:
                    fragment = new QuanLyDotThiFragmnet();
                    break;
                case R.id.QLCD:
                    fragment = new QuanLyCapDai();
                    break;
                case R.id.QLUser:
                    fragment = new UserManagementFragment();
                    break;
            }
            transaction.replace(R.id.admin_root_frame, fragment);
            transaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new HomePagerAdapter(getSupportFragmentManager());
        Intent i = getIntent();
        if(i.getBooleanExtra("isAdmin",false)) {
            adapter.addFrag(new AdminRootFrame(), "THREE");
        }
        adapter.addFrag(new RootFragment(), "ONE");
        adapter.addFrag(new InfoAppVoFragment(), "TWO");

        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons() {

        Intent i = getIntent();
        if(i.getBooleanExtra("isAdmin",false)) {
            TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
            tabThree.setText("Admin");
            tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.person, 0, 0);
            tabLayout.getTabAt(0).setCustomView(tabThree);


            TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
            tabTwo.setText("Kiến Thức");
            tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.info, 0, 0);
            tabLayout.getTabAt(1).setCustomView(tabTwo);

            TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
            tabOne.setText("Chấm Điểm");
            tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.school, 0, 0);
            tabLayout.getTabAt(2).setCustomView(tabOne);
        }else {

            TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
            tabOne.setText("Chấm Điểm");
            tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.school, 0, 0);
            tabLayout.getTabAt(0).setCustomView(tabOne);

            TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
            tabTwo.setText("Kiến Thức");
            tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.info, 0, 0);
            tabLayout.getTabAt(1).setCustomView(tabTwo);
        }
    }
    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
