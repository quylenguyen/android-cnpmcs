package com.example.quyle.appchamdiemvo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminPanelActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private Button btnDX;
    private Button btnQLDT;
    private Button btnQLCapDai;
    private Button btnHv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

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

        btnDX = (Button) findViewById(R.id.btnDX);
        btnQLDT = (Button) findViewById(R.id.btnQLDT);

        btnQLCapDai = (Button) findViewById(R.id.btnQLCapDai);
        btnHv = findViewById(R.id.btnHV);
        btnHv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnQLCapDai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminPanelActivity.this,QuanLyCapDai.class));
            }
        });
        btnDX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == btnDX){
                    //logging out the user
                    firebaseAuth.signOut();
                    //closing activity
                    finish();
                    //starting login activity
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            }
        });
        btnQLDT.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(v == btnQLDT){
                    //closing activity
                    finish();
                    //starting QLDT activity
                    startActivity(new Intent(getApplicationContext(), QuanLyDotThiFragmnet.class));
                }
            }
        });

    }
}
