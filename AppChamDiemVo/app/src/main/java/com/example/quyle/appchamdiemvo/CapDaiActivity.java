package com.example.quyle.appchamdiemvo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Model.CapDai;
import View.Adapter.CapDaiAdapter;

/**
 * Created by vtnhan on 10/27/2017.
 */

public class CapDaiActivity extends AppCompatActivity{
    List<CapDai> lsCapDai = new ArrayList<>();
    ListView lvCapdai;
    DatabaseReference dbCapDai;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_cap_dai);
        lvCapdai = (ListView) findViewById(R.id.lvCapDai);
        dbCapDai = FirebaseDatabase.getInstance().getReference("CapDai");
        dbCapDai.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lsCapDai.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    CapDai capdai = postSnapshot.getValue(CapDai.class);
                    //adding artist to the list
                    lsCapDai.add(capdai);
                }
                CapDaiAdapter adater = new CapDaiAdapter(CapDaiActivity.this,lsCapDai);
                lvCapdai.setAdapter(adater);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        lvCapdai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(CapDaiActivity.this,HocVienActivity.class);
                startActivity(intent);
            }
        });
    }
}
