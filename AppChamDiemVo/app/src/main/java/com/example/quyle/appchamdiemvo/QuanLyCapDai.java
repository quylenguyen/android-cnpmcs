package com.example.quyle.appchamdiemvo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

import Model.CapDai;
import View.Adapter.CapDaiAdapterList;

/**
 * Created by vtnhan on 12/8/2017.
 */

public class QuanLyCapDai extends Fragment {
    ListView lvQuanLyCapDai;
    Button btnAddCapDai;
    MaterialEditText edtCapDai;

    List<CapDai> lsCapDai;


    ListView getListViewDotThi;


    //our database reference object
    DatabaseReference dbQLCapDai;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.quanly_capdai_fragment,container,false);

        //getting the reference of artists node
        dbQLCapDai = FirebaseDatabase.getInstance().getReference("CapDai");

        //getting views

        btnAddCapDai = (Button) rootView.findViewById(R.id.btnAddQLCapDai);
        edtCapDai = (MaterialEditText) rootView.findViewById(R.id.edtTenCapDai);

//        editTextTenDotThi.setVisibility(View.INVISIBLE);
//        buttonAddTenDotThi.setVisibility(View.INVISIBLE);
        lvQuanLyCapDai = (ListView) rootView.findViewById(R.id.lvQLCapDai);

        //list to store artists
        lsCapDai = new ArrayList<>();


        //adding an onclicklistener to button
        btnAddCapDai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calling the method addArtist()
                //the method is defined below
                //this method is actually performing the write operation
                addCapDai();
            }
        });
        lvQuanLyCapDai.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                CapDai capDai = lsCapDai.get(position);

                showUpdateDialog(capDai._TenCapDai,capDai.id);

                return false;
            }
        });
        dbQLCapDai.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
                lsCapDai.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    CapDai capDai = postSnapshot.getValue(CapDai.class);
                    //adding artist to the list
                    lsCapDai.add(capDai);
                }

                //creating adapter
                CapDaiAdapterList capDaiAdapterList = new CapDaiAdapterList(getActivity(), lsCapDai);
                //attaching adapter to the listview
                lvQuanLyCapDai.setAdapter(capDaiAdapterList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return rootView;
    }
    private void showUpdateDialog(final String tenCapDai,final String idCapDai) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);
//        final TextView textViewTenDotThiCu = (TextView) dialogView.findViewById(R.id.textView5);
        final EditText editTextTenDotThiUpdate = (EditText) dialogView.findViewById(R.id.editTextTenDotThi2);
        final Button btnUpdate = (Button) dialogView.findViewById(R.id.btnUpdate);
        final Button btnDelete = (Button) dialogView.findViewById(R.id.btnDelete);
        dialogBuilder.setTitle("Cập Nhật Đợt Thi " + tenCapDai);
        final AlertDialog b = dialogBuilder.create();
        b.show();
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextTenDotThiUpdate.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    editTextTenDotThiUpdate.setError("Bạn không được để trống tên đợt thi");
                    return;
                }
                updateCapDai(name,idCapDai);
                b.dismiss();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCapDai(idCapDai);
                b.dismiss();
            }
        });

    }

    private void addCapDai() {
        //getting the values to save
        String name = edtCapDai.getText().toString().trim();


        //checking if the value is provided
        if (!TextUtils.isEmpty(name)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            String id = dbQLCapDai.push().getKey();
            //creating an Artist Object
            CapDai capDai = new CapDai(id, name);
            //Saving the Artist
            dbQLCapDai.child(id).setValue(capDai);
            //setting edittext to blank again
            //displaying a success toast
            Toast.makeText(getContext(), "Đã thêm cấp đai", Toast.LENGTH_LONG).show();
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(getContext(), "Xin hãy nhập cấp đai", Toast.LENGTH_LONG).show();
        }
    }
    private boolean updateCapDai( String name, String id) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("CapDai").child(id);

        //updating artist
        CapDai capDai = new CapDai(id,name);
        dR.setValue(capDai);
        Toast.makeText(getContext(), "Cấp đai đã được cập nhật", Toast.LENGTH_LONG).show();
        return true;
    }
    private boolean deleteCapDai(String id) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("CapDai").child(id);

        //removing artist
        dR.removeValue();

        Toast.makeText(getContext(), "Đợt thi đã được xóa", Toast.LENGTH_LONG).show();

        return true;
    }
}

