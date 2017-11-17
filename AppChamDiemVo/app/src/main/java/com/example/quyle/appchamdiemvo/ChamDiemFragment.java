package com.example.quyle.appchamdiemvo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import View.Adapter.DotThiAdapter;


public class ChamDiemFragment extends Fragment {
    RecyclerView listViewDotThi;
    Button buttonAddTenDotThi;
    EditText editTextTenDotThi;
    //a list to store all the artist from firebase database
    List<DotThi> danhSachDotThi;
    DotThiAdapter dotthiAdapter;
    ListView getListViewDotThi;

    //our database reference object
    DatabaseReference databaseDotThi;
    public ChamDiemFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewroot = inflater.inflate(R.layout.dot_thi_fragment, container, false);
        databaseDotThi = FirebaseDatabase.getInstance().getReference("dotthis");

        //getting views

        buttonAddTenDotThi = (Button) viewroot.findViewById(R.id.buttonAddTenDotThi);
        editTextTenDotThi = (EditText) viewroot.findViewById(R.id.editTextTenDotThi);
        listViewDotThi =  viewroot.findViewById(R.id.listViewDotThi);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        listViewDotThi.setLayoutManager(layoutManager);
//        listViewDotThi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(getContext(),CapDaiFragment.class);
//                startActivity(intent);
//            }
//        });

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
        return viewroot;
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


    @Override
    public void onStart() {
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
                dotthiAdapter = new DotThiAdapter(danhSachDotThi, new DotThiAdapter.OnNotificationListener() {
                    @Override
                    public void onIconClick(DotThi item) {
                        NotificationCompat.Builder mBuilder =
                                new NotificationCompat.Builder(getContext())
                                        .setSmallIcon(R.drawable.fight)
                                        .setContentTitle("Thông báo")
                                        .setContentText("Chấm điểm võ");
// Creates an explicit intent for an Activity in your app
                        Intent resultIntent = new Intent(getContext(), ResultActivity.class);

                        PendingIntent resultPendingIntent =
                                PendingIntent.getActivity(
                                        getContext(),
                                        0,
                                        resultIntent,
                                        PendingIntent.FLAG_UPDATE_CURRENT
                                );

                        mBuilder.setContentIntent(resultPendingIntent);
                        NotificationManager mNotificationManager =
                                (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
                        mNotificationManager.notify(113, mBuilder.build());


                        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        mBuilder.setSound(uri);

                    }
                }, new DotThiAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(DotThi item) {
                        FragmentTransaction trans = getFragmentManager()
                                .beginTransaction();
                        trans.replace(R.id.root_frame, new CapDaiFragment());
                        trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        trans.addToBackStack(null);
                        trans.commit();
                    }
                });
                listViewDotThi.setAdapter(dotthiAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
