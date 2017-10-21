package com.example.quyle.appchamdiemvo;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ThiSinhActivity extends AppCompatActivity {
//    public static final String DAI_ID = "daiid";
//    public static final String TEN_DAI = "tendai";

    EditText editTextName;
    Spinner spinnerGenre;
    Button buttonAddThiSinh;
    ListView listViewThiSinhs;

    //a list to store all the artist from firebase database
    List<ThiSinh> danhSachThiSinhs;

    //our database reference object
    DatabaseReference databaseThiSinhs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thi_sinh);

        //getting the reference of artists node
        databaseThiSinhs = FirebaseDatabase.getInstance().getReference("thisinhs");

        //getting views
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextName.setVisibility(View.INVISIBLE);
        spinnerGenre = (Spinner) findViewById(R.id.spinnerDiemSo);
        spinnerGenre.setVisibility(View.INVISIBLE);
        listViewThiSinhs = (ListView) findViewById(R.id.listViewThiSinhs);

        buttonAddThiSinh = (Button) findViewById(R.id.buttonAddThiSinh);
        buttonAddThiSinh.setVisibility(View.INVISIBLE);
        //list to store artists
        danhSachThiSinhs = new ArrayList<>();


        //adding an onclicklistener to button
        buttonAddThiSinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calling the method addArtist()
                //the method is defined below
                //this method is actually performing the write operation
                addThiSinh();
            }
        });

        //attaching listener to listview
//        listViewThiSinhs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                //getting the selected artist
//                 ThiSinh thisinh = danhSachThiSinhs.get(i);
//
//                //creating an intent
//                Intent intent = new Intent(getApplicationContext(), HinhThucThiActivity.class);
//
//                //putting artist name and id to intent
//                intent.putExtra(DAI_ID, thisinh.getDaiId());
//                intent.putExtra(TEN_DAI, capdai.getTenDai());
//
//                //starting the activity with intent
//                startActivity(intent);
//            }
//        });

        listViewThiSinhs.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                ThiSinh thisinh = danhSachThiSinhs.get(i);
                showUpdateDeleteDialog(thisinh.getId(), thisinh.getHoTen());
                return false;
            }
        });


    }

    private void showUpdateDeleteDialog(final String IdThiSinh, String name) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.cham_diem, null);
        dialogBuilder.setView(dialogView);

        final TextView textViewName = (TextView) dialogView.findViewById(R.id.textViewName);
        final TextView textViewNoiDungThi = (TextView) dialogView.findViewById(R.id.textViewNoiDungThi);
        final Spinner spinnerDiemSo = (Spinner) dialogView.findViewById(R.id.spinnerDiemSo);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateThiSinh);
//        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteThiSinh);
        textViewName.setText("ABC");

        textViewNoiDungThi.setText("BCD");
        dialogBuilder.setTitle(name);
        final AlertDialog b = dialogBuilder.create();
        b.show();


//        buttonUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String name = textViewName.getText().toString().trim();
//                String diemso = spinnerDiemSo.getSelectedItem().toString();
//                String noidungthi = textViewNoiDungThi.getText().toString().trim();
//                if (!TextUtils.isEmpty(name)) {
//                    updateArtist(IdThiSinh, name, diemso, noidungthi);
//                    b.dismiss();
//                }
//            }
//        });


//        buttonDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                deleteArtist(artistId);
//                b.dismiss();
//            }
//        });
    }

//    private boolean updateArtist(String id, String name, String diemso, String noidung) {
//        //getting the specified artist reference
//        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("thisinhs").child(id);
//
//        //updating artist
//        ThiSinh thisinh = new ThiSinh(id, name, diemso, noidung);
//        dR.setValue(thisinh);
//        Toast.makeText(getApplicationContext(), "Artist Updated", Toast.LENGTH_LONG).show();
//        return true;
//    }

//    private boolean deleteArtist(String id) {
//        //getting the specified artist reference
//        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("artists").child(id);
//
//        //removing artist
//        dR.removeValue();
//
//        //getting the tracks reference for the specified artist
//        DatabaseReference drTracks = FirebaseDatabase.getInstance().getReference("tracks").child(id);
//
//        //removing all tracks
//        drTracks.removeValue();
//        Toast.makeText(getApplicationContext(), "Artist Deleted", Toast.LENGTH_LONG).show();
//
//        return true;
//    }

    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        databaseThiSinhs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
                danhSachThiSinhs.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    ThiSinh thisinh = postSnapshot.getValue(ThiSinh.class);
                    //adding artist to the list
                    danhSachThiSinhs.add(thisinh);
                }

                //creating adapter
                DanhSachThiSinh thisinhAdapter = new DanhSachThiSinh(ThiSinhActivity.this, danhSachThiSinhs);
                //attaching adapter to the listview
                listViewThiSinhs.setAdapter(thisinhAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    /*
    * This method is saving a new artist to the
    * Firebase Realtime Database
    * */
    private void addThiSinh() {
        //getting the values to save
        String name = editTextName.getText().toString().trim();
//        String genre = spinnerGenre.getSelectedItem().toString();

        //checking if the value is provided
        if (!TextUtils.isEmpty(name)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            String id = databaseThiSinhs.push().getKey();
            String noidung = "Chưa có nội dung";
            String diem = "0";
            //creating an Artist Object
            ThiSinh thisinh = new ThiSinh(id, name, noidung, diem);

            //Saving the Artist
            databaseThiSinhs.child(id).setValue(thisinh);

            //setting edittext to blank again
            editTextName.setText("");

            //displaying a success toast
            Toast.makeText(this, "Artist added", Toast.LENGTH_LONG).show();
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
        }
    }
}
