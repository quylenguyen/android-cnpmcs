package com.example.quyle.appchamdiemvo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class CapDaiFragment extends Fragment{
    List<CapDai> lsCapDai = new ArrayList<>();
    ListView lvCapdai;
    DatabaseReference dbCapDai;
    TextView txtCapDai;
    Button btnAddCapDai;
    DatabaseReference databaseCapDai;


    public CapDaiFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewroot = inflater.inflate(R.layout.fragment_cap_dai, container, false);
        lvCapdai = (ListView) viewroot.findViewById(R.id.lvCapDai);
        txtCapDai = viewroot.findViewById(R.id.txtCapDai);
        btnAddCapDai = viewroot.findViewById(R.id.btnAddCapDai);
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
                CapDaiAdapter adater = new CapDaiAdapter(getActivity(),lsCapDai);
                lvCapdai.setAdapter(adater);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        lvCapdai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FragmentTransaction trans = getFragmentManager()
                        .beginTransaction();
                trans.replace(R.id.root_frame, new HocVienFragment());
                trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                trans.addToBackStack(null);
                trans.commit();
            }
        });
        btnAddCapDai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCapDai();
            }
        });
        return viewroot;

    }
    private void addCapDai() {
        //getting the values to save
        String name = txtCapDai.getText().toString().trim();


        //checking if the value is provided
        if (!TextUtils.isEmpty(name)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            String id = dbCapDai.push().getKey();
            //creating an Artist Object
            CapDai capDai = new CapDai(name);
            //Saving the Artist
            dbCapDai.child(id).setValue(capDai);
            //setting edittext to blank again
            txtCapDai.setText("");
            //displaying a success toast
            Toast.makeText(getContext(), "Đã thêm đợt thi", Toast.LENGTH_LONG).show();
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(getContext(), "Xin hãy nhập đợt thi", Toast.LENGTH_LONG).show();
        }
    }
}
