package com.example.quyle.appchamdiemvo;

/**
 * Created by quyle on 12/7/2017.
 */


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

import java.util.ArrayList;
import java.util.List;

import Model.DotThi;
import View.Adapter.DotThiList;

public class QuanLyDotThiFragmnet extends Fragment {



    ListView listViewDotThi;
    Button buttonAddTenDotThi;
    EditText editTextTenDotThi;
//    TextView textViewTenDotThiCu;
//    EditText editTextTenDotThiUpdate;
//    Button btnUpdate;
    //a list to store all the artist from firebase database
    List<DotThi> danhSachDotThi;


    ListView getListViewDotThi;




    //our database reference object
    DatabaseReference databaseDotThi;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.activity_quan_ly_dot_thi,container,false);

        //getting the reference of artists node
        databaseDotThi = FirebaseDatabase.getInstance().getReference("dotthis");

        //getting views

        buttonAddTenDotThi = (Button) viewRoot.findViewById(R.id.buttonAddTenDotThi1);
        editTextTenDotThi = (EditText) viewRoot.findViewById(R.id.editTextTenDotThi1);

//        editTextTenDotThi.setVisibility(View.INVISIBLE);
//        buttonAddTenDotThi.setVisibility(View.INVISIBLE);
        listViewDotThi = (ListView) viewRoot.findViewById(R.id.listViewDotThi1);

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
        listViewDotThi.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DotThi dotthi = danhSachDotThi.get(position);

                showUpdateDialog(dotthi.getIdDotThi(), dotthi.getTenDotThi());

                return false;
            }
        });
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
                DotThiList dotthiAdapter = new DotThiList(getActivity(), danhSachDotThi);
                //attaching adapter to the listview
                listViewDotThi.setAdapter(dotthiAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return  viewRoot;
    }
    private void showUpdateDialog(final String idDotThi, final String tenDotThi) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);
//        final TextView textViewTenDotThiCu = (TextView) dialogView.findViewById(R.id.textView5);
        final EditText editTextTenDotThiUpdate = (EditText) dialogView.findViewById(R.id.editTextTenDotThi2);
        final Button btnUpdate = (Button) dialogView.findViewById(R.id.btnUpdate);
        final Button btnDelete = (Button) dialogView.findViewById(R.id.btnDelete);
        dialogBuilder.setTitle("Cập Nhật Đợt Thi " + tenDotThi);
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
                updateDotThi(idDotThi, name);
                b.dismiss();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDotThi(idDotThi);
                b.dismiss();
            }
        });





//        buttonDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                deleteArtist(artistId);
//                b.dismiss();
//            }
//        });
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
            Toast.makeText(getContext(), "Đã thêm đợt thi", Toast.LENGTH_LONG).show();
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(getContext(), "Xin hãy nhập đợt thi", Toast.LENGTH_LONG).show();
        }
    }
    private boolean updateDotThi(String id, String name) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("dotthis").child(id);

        //updating artist
        DotThi dotthi = new DotThi(id, name);
        dR.setValue(dotthi);
        Toast.makeText(getContext(), "Đợt thi đã được cập nhật", Toast.LENGTH_LONG).show();
        return true;
    }
    private boolean deleteDotThi(String id) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("dotthis").child(id);

        //removing artist
        dR.removeValue();

        Toast.makeText(getContext(), "Đợt thi đã được xóa", Toast.LENGTH_LONG).show();

        return true;
    }

}
