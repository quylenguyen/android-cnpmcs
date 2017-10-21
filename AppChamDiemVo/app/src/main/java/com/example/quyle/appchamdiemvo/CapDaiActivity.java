package com.example.quyle.appchamdiemvo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CapDaiActivity extends AppCompatActivity {
    public static final String DAI_ID = "daiid";
    public static final String TEN_DAI = "tendai";

    EditText editTextName;
    Spinner spinnerGenre;
    Button buttonAddArtist;
    ListView listViewCapDais;

    //a list to store all the artist from firebase database
    List<CapDai> danhSachCapDais;

    //our database reference object
    DatabaseReference databaseArtists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_dai);

        //getting the reference of artists node
        databaseArtists = FirebaseDatabase.getInstance().getReference("capdais");

        //getting views
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextName.setVisibility(View.INVISIBLE);
        spinnerGenre = (Spinner) findViewById(R.id.spinnerDiemSo);
        spinnerGenre.setVisibility(View.INVISIBLE);
        listViewCapDais = (ListView) findViewById(R.id.listViewCapDais);


        buttonAddArtist = (Button) findViewById(R.id.buttonAddArtist);
        buttonAddArtist.setVisibility(View.INVISIBLE);
        //list to store artists
        danhSachCapDais = new ArrayList<>();


        //adding an onclicklistener to button
        buttonAddArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calling the method addArtist()
                //the method is defined below
                //this method is actually performing the write operation
                addArtist();
            }
        });

        //attaching listener to listview
        listViewCapDais.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //getting the selected artist
                CapDai capdai = danhSachCapDais.get(i);

                //creating an intent
                Intent intent = new Intent(getApplicationContext(), HinhThucThiActivity.class);

                //putting artist name and id to intent
                intent.putExtra(DAI_ID, capdai.getDaiId());
                intent.putExtra(TEN_DAI, capdai.getTenDai());

                //starting the activity with intent
                startActivity(intent);
            }
        });

//        listViewArtists.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Artist artist = artists.get(i);
//                showUpdateDeleteDialog(artist.getArtistId(), artist.getArtistName());
//                return true;
//            }
//        });


    }

//    private void showUpdateDeleteDialog(final String artistId, String artistName) {
//
//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
//        LayoutInflater inflater = getLayoutInflater();
//        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
//        dialogBuilder.setView(dialogView);
//
//        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
//        final Spinner spinnerGenre = (Spinner) dialogView.findViewById(R.id.spinnerGenres);
//        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateArtist);
//        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteArtist);
//
//        dialogBuilder.setTitle(artistName);
//        final AlertDialog b = dialogBuilder.create();
//        b.show();
//
//
//        buttonUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String name = editTextName.getText().toString().trim();
//                String genre = spinnerGenre.getSelectedItem().toString();
//                if (!TextUtils.isEmpty(name)) {
//                    updateArtist(artistId, name, genre);
//                    b.dismiss();
//                }
//            }
//        });
//
//
//        buttonDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                deleteArtist(artistId);
//                b.dismiss();
//            }
//        });
//    }

//    private boolean updateArtist(String id, String name, String genre) {
//        //getting the specified artist reference
//        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("artists").child(id);
//
//        //updating artist
//        Artist artist = new Artist(id, name, genre);
//        dR.setValue(artist);
//        Toast.makeText(getApplicationContext(), "Artist Updated", Toast.LENGTH_LONG).show();
//        return true;
//    }
//
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
        databaseArtists.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
                danhSachCapDais.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    CapDai capdai = postSnapshot.getValue(CapDai.class);
                    //adding artist to the list
                    danhSachCapDais.add(capdai);
                }

                //creating adapter
                DanhSachCapDai capdaiAdapter = new DanhSachCapDai(CapDaiActivity.this, danhSachCapDais);
                //attaching adapter to the listview
                listViewCapDais.setAdapter(capdaiAdapter);
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
    private void addArtist() {
        //getting the values to save
        String name = editTextName.getText().toString().trim();
//        String genre = spinnerGenre.getSelectedItem().toString();

        //checking if the value is provided
        if (!TextUtils.isEmpty(name)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            String id = databaseArtists.push().getKey();

            //creating an Artist Object
            CapDai artist = new CapDai(id, name);

            //Saving the Artist
            databaseArtists.child(id).setValue(artist);

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

