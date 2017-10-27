package com.example.quyle.appchamdiemvo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Model.CapDai;
import Model.HocVien;
import View.Adapter.CapDaiAdapter;
import View.Adapter.HocVienAdapter;

/**
 * Created by vtnhan on 10/28/2017.
 */

public class HocVienActivity extends AppCompatActivity{
    List<HocVien> lsHV = new ArrayList<>();
    ListView lvHV;
    DatabaseReference dbHV;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hocvien_activity);
        lvHV = (ListView) findViewById(R.id.lvHV);
        dbHV = FirebaseDatabase.getInstance().getReference("HocVien");
        dbHV.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lsHV.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    HocVien hv = postSnapshot.getValue(HocVien.class);
                    //adding artist to the list
                    lsHV.add(hv);
                }
                HocVienAdapter adater = new HocVienAdapter(HocVienActivity.this,lsHV);
                lvHV.setAdapter(adater);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
