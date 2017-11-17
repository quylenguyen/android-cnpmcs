package com.example.quyle.appchamdiemvo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import View.Adapter.HomePagerAdapter;

public class ManagementActivity extends AppCompatActivity {

    //firebase auth object
    private HomePagerAdapter adapter;
    private FirebaseAuth firebaseAuth;
    private ViewPager viewPager;
    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.vp);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the user is not logged in
        //that means current user will return null
        if (firebaseAuth.getCurrentUser() == null) {
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, MainActivity.class));
        }

        //getting current user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //initializing views

//        buttonDangXuat = (Button) findViewById(R.id.buttonDangXuat);
////        buttonProfile = (Button) findViewById(R.id.buttonProfile);
//        buttonChamDiem = (Button) findViewById(R.id.buttonChamDiem);


        //adding listener to button
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new HomePagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new RootFragment(), "ONE");
        adapter.addFrag(new InfoAppVoFragment(), "TWO");
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("Chấm Điểm");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.school, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Kiến Thức");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.info, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

    }

//    @Override
//    public void onClick(View view) {
//        //if logout is pressed
//        if(view == buttonDangXuat){
//            //logging out the user
//            firebaseAuth.signOut();
//            //closing activity
//            finish();
//            //starting login activity
//            startActivity(new Intent(this, MainActivity.class));
//        }
//        if(view == buttonChamDiem)
//        {
//            startActivity(new Intent(getApplicationContext(), DotThiActivity.class));
//        }
//    }
}
