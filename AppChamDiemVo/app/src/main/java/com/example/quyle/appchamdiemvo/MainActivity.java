package com.example.quyle.appchamdiemvo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import Model.Common;
import Model.User;
import cn.pedant.SweetAlert.SweetAlertDialog;
import dmax.dialog.SpotsDialog;
import info.hoang8f.widget.FButton;


public class MainActivity extends AppCompatActivity {


    //defining views
    private FButton buttonSignIn;
    private MaterialEditText txtPassword;
    private MaterialEditText txtPhoneNumber;
    Boolean isAdmin;
    SpotsDialog dialog;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialog = new SpotsDialog(this);
        //init view
        txtPassword = findViewById(R.id.txtPass);
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber);
        buttonSignIn = findViewById(R.id.buttonSign_in);
        //init firebase
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users");


        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SweetAlertDialog pDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading");
                pDialog.setCancelable(true);
                pDialog.show();
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(txtPhoneNumber.getText().toString()).exists()) {
                            pDialog.dismiss();
                            User user = dataSnapshot.child(txtPhoneNumber.getText().toString()).getValue(User.class);
                            user.Phone = txtPhoneNumber.getText().toString();
                            if (user.Pass.equals(txtPassword.getText().toString())) {
                                Common.currentUser = user;
                                if (user.Email.equals("admin@gmail.com")) {
                                    isAdmin = true;
                                    Intent i = new Intent(MainActivity.this, A.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putBoolean("isAdmin", isAdmin);
                                    i.putExtras(bundle);
                                    startActivity(i);
                                    finish();
                                } else {
                                    isAdmin = false;
                                    Intent i = new Intent(getApplicationContext(), A.class);
                                    i.putExtra("isAdmin", isAdmin);
                                    startActivity(i);
                                    finish();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Sign in Failed", Toast.LENGTH_LONG).show();
                            }

                        }
                        else
                            Toast.makeText(getApplicationContext(), "Tài khoản không tồn tại", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

    }

}
