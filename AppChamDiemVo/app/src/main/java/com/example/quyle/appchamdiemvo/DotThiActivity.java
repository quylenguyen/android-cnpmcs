package com.example.quyle.appchamdiemvo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Model.DotThi;
import Model.DotThiList;

public class DotThiActivity extends AppCompatActivity {



    ListView listViewDotThi;
    Button buttonAddTenDotThi;
    EditText editTextTenDotThi;
    //a list to store all the artist from firebase database
    List<DotThi> danhSachDotThi;


    ListView getListViewDotThi;




    //our database reference object
    DatabaseReference databaseDotThi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dot_thi);

        //getting the reference of artists node
        databaseDotThi = FirebaseDatabase.getInstance().getReference("dotthis");

        //getting views

        buttonAddTenDotThi = (Button) findViewById(R.id.buttonAddTenDotThi);
        editTextTenDotThi = (EditText) findViewById(R.id.editTextTenDotThi);
        editTextTenDotThi.setVisibility(View.INVISIBLE);
        buttonAddTenDotThi.setVisibility(View.INVISIBLE);
        listViewDotThi = (ListView) findViewById(R.id.listViewDotThi);

        //list to store artists
        danhSachDotThi = new ArrayList<>();


        //adding an onclicklistener to button
        buttonAddTenDotThi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calling the method addArtist()
                //the method is defined below
                //this method is actually performing the write operation
                addDotThi();
            }
        });
    }
    private void addDotThi() {
        //getting the values to save
        String name = editTextTenDotThi.getText().toString().trim();


        //checking if the value is provided
        if (!TextUtils.isEmpty(name)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            String id = databaseDotThi.push().getKey();

            //creating an Artist Object
            DotThi dotthi = new DotThi(id, name);

            //Saving the Artist
            databaseDotThi.child(id).setValue(dotthi);

            //setting edittext to blank again
            editTextTenDotThi.setText("");

            //displaying a success toast
            Toast.makeText(this, "Đã thêm đợt thi", Toast.LENGTH_LONG).show();
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Xin hãy nhập đợt thi", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        databaseDotThi.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
                danhSachDotThi.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    DotThi dotthi = postSnapshot.getValue(DotThi.class);
                    //adding artist to the list
                    danhSachDotThi.add(dotthi);
                }

                //creating adapter
                DotThiList dotthiAdapter = new DotThiList(DotThiActivity.this, danhSachDotThi);
                //attaching adapter to the listview
                listViewDotThi.setAdapter(dotthiAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}


