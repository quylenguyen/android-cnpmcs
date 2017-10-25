package com.example.quyle.appchamdiemvo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
<<<<<<< HEAD
import android.widget.AdapterView;
=======
>>>>>>> a4d743ab48679a75e092979ffacdb8a49ccf3528
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

<<<<<<< HEAD
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
=======
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
>>>>>>> a4d743ab48679a75e092979ffacdb8a49ccf3528

import java.util.ArrayList;
import java.util.List;

public class HinhThucThiActivity extends AppCompatActivity {
<<<<<<< HEAD
    public static final String HINH_THUC_THI_ID = "httid";
    public static final String TEN_HINH_THUC_THI = "tenhttt";
=======
>>>>>>> a4d743ab48679a75e092979ffacdb8a49ccf3528

    Button buttonAddTrack;
    EditText editTextTrackName;
    SeekBar seekBarRating;
    TextView textViewRating, textViewArtist;
    ListView listViewTracks;

    DatabaseReference databaseTracks;

    List<HinhThucThi> hinhthucthis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hinh_thuc_thi);

        Intent intent = getIntent();

        /*
        * this line is important
        * this time we are not getting the reference of a direct node
        * but inside the node track we are creating a new child with the artist id
        * and inside that node we will store all the tracks with unique ids
        * */
        databaseTracks = FirebaseDatabase.getInstance().getReference("hinhthucthis").child(intent.getStringExtra(CapDaiActivity.DAI_ID));

        buttonAddTrack = (Button) findViewById(R.id.buttonAddTrack);
        editTextTrackName = (EditText) findViewById(R.id.editTextName);
        seekBarRating = (SeekBar) findViewById(R.id.seekBarRating);
        textViewRating = (TextView) findViewById(R.id.textViewRating);
        textViewArtist = (TextView) findViewById(R.id.textViewArtist);
        listViewTracks = (ListView) findViewById(R.id.listViewTracks);
<<<<<<< HEAD
        buttonAddTrack.setVisibility(View.INVISIBLE);
        editTextTrackName.setVisibility(View.INVISIBLE);
        textViewRating.setVisibility(View.INVISIBLE);
        seekBarRating.setVisibility(View.INVISIBLE);
=======

>>>>>>> a4d743ab48679a75e092979ffacdb8a49ccf3528
        hinhthucthis = new ArrayList<>();

        textViewArtist.setText(intent.getStringExtra(CapDaiActivity.TEN_DAI));

//        seekBarRating.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                textViewRating.setText(String.valueOf(i));
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });

        buttonAddTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTrack();
            }
        });
<<<<<<< HEAD
        //attaching listener to listview
        listViewTracks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //getting the selected artist
                HinhThucThi hinhthucthi = hinhthucthis.get(i);

                //creating an intent
                Intent intent = new Intent(getApplicationContext(), ThiSinhActivity.class);

                //putting artist name and id to intent
                intent.putExtra(HINH_THUC_THI_ID, hinhthucthi.getId());
                intent.putExtra(TEN_HINH_THUC_THI, hinhthucthi.getTenHinhThucThi());

                //starting the activity with intent
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseTracks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                hinhthucthis.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    HinhThucThi hinhthucthi = postSnapshot.getValue(HinhThucThi.class);
                    hinhthucthis.add(hinhthucthi);
                }
                DanhSachHinhThucThi trackListAdapter = new DanhSachHinhThucThi(HinhThucThiActivity.this, hinhthucthis);
                listViewTracks.setAdapter(trackListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
=======
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        databaseTracks.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                tracks.clear();
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    Track track = postSnapshot.getValue(Track.class);
//                    tracks.add(track);
//                }
//                TrackList trackListAdapter = new TrackList(ArtistActivity.this, tracks);
//                listViewTracks.setAdapter(trackListAdapter);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
>>>>>>> a4d743ab48679a75e092979ffacdb8a49ccf3528

    private void saveTrack() {
        String trackName = editTextTrackName.getText().toString().trim();
        int rating = seekBarRating.getProgress();
        if (!TextUtils.isEmpty(trackName)) {
            String id  = databaseTracks.push().getKey();
            HinhThucThi hinhthucthi = new HinhThucThi(id, trackName);
            databaseTracks.child(id).setValue(hinhthucthi);
            Toast.makeText(this, "Track saved", Toast.LENGTH_LONG).show();
            editTextTrackName.setText("");
        } else {
            Toast.makeText(this, "Please enter track name", Toast.LENGTH_LONG).show();
        }
    }
}

