package com.example.quyle.appchamdiemvo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import Model.HocVien;
import View.Adapter.HocVienAdapter;

public class HocVienFragment extends Fragment {
    List<HocVien> lsHV = new ArrayList<>();
    RecyclerView lvHV;
    DatabaseReference dbHV;
    HocVienAdapter adater;
    EditText txtInputTen, txtInputNgaySinh, txtInputCapDai, txtInputDonVi, txtInputURLImage;
    Button btnSaveHV;

    public HocVienFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.hocvien_fragment, container, false);
        lvHV = viewRoot.findViewById(R.id.lvHV);
        txtInputCapDai = viewRoot.findViewById(R.id.txtInputCapDai);
        txtInputDonVi = viewRoot.findViewById(R.id.txtInputDonVi);
        txtInputNgaySinh = viewRoot.findViewById(R.id.txtInputNgaySinh);
        txtInputTen = viewRoot.findViewById(R.id.txtInPutTen);
        txtInputURLImage = viewRoot.findViewById(R.id.txtURLImage);
        btnSaveHV = viewRoot.findViewById(R.id.btnAddHocVien);
        dbHV = FirebaseDatabase.getInstance().getReference("HocVien");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        lvHV.setLayoutManager(layoutManager);
        dbHV.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lsHV.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    HocVien hv = postSnapshot.getValue(HocVien.class);
                    lsHV.add(hv);
                }
                adater = new HocVienAdapter(lsHV, new HocVienAdapter.OnClickHVListener() {
                    @Override
                    public void onItemClick(HocVien hv) {
                        if (hv.ktCham == 0) {
                            Bundle bundle = new Bundle();
                            bundle.putString("ID", hv.id);
                            bundle.putString("Ten", hv._Ten);
                            bundle.putString("NgaySinh", hv._NgaySinh);
                            bundle.putString("CapDai", hv._DaiHienTai);
                            bundle.putString("DonVi", hv._DonVi);
                            bundle.putInt("DiemND1", hv._DiemNoiDung1);
                            bundle.putInt("DiemND2", hv._DiemNoiDung2);
                            bundle.putInt("DiemND3", hv._DiemNoiDung3);
                            bundle.putInt("DiemND4", hv._DiemNoiDung4);
                            bundle.putInt("DiemND5", hv._DiemNoiDung5);
                            FragmentTransaction trans = getFragmentManager()
                                    .beginTransaction();
                            TinhDiemFragment tinhDiemFragment = new TinhDiemFragment();
                            tinhDiemFragment.setArguments(bundle);
                            trans.replace(R.id.root_frame, tinhDiemFragment);
                            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                            trans.addToBackStack(null);
                            trans.commit();
                            hv.ktCham = 1;
                            DatabaseReference dR = FirebaseDatabase.getInstance().getReference("HocVien").child(hv.id);
                            dR.setValue(hv);

                        }
                        else if(hv.ktCham == 1) {
                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                            LayoutInflater inflater = getLayoutInflater();
                            final View dialogView = inflater.inflate(R.layout.dialog, null);
                            dialogBuilder.setView(dialogView);
                            final TextView txtDiemThi =dialogView.findViewById(R.id.txtTongDiem);
                            txtDiemThi.setText(hv._Ten + " : " +hv._TongDiem);
                            dialogBuilder.setTitle("Điểm học viên");
                            final AlertDialog b = dialogBuilder.create();
                            b.show();

                        }

                    }
                }, getContext());
                lvHV.setAdapter(adater);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        btnSaveHV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addHocVien();
            }
        });
        return viewRoot;
    }

    private void addHocVien() {
        //getting the values to save
        String name = txtInputTen.getText().toString().trim();
        String ngaySinh = txtInputNgaySinh.getText().toString().trim();
        String capDaiHienTai = txtInputCapDai.getText().toString().trim();
        String URLImage = txtInputURLImage.getText().toString().trim();
        String donVi = txtInputDonVi.getText().toString().trim();


        //checking if the value is provided
        if (!TextUtils.isEmpty(name)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            String id = dbHV.push().getKey();
            //creating an Artist Object
            HocVien hv = new HocVien(id, name, ngaySinh, donVi, capDaiHienTai, 0, 0, 0, 0, 0, 0);
            //Saving the Artist
            dbHV.child(id).setValue(hv);
            //setting edittext to blank again
            txtInputDonVi.setText("");
            txtInputNgaySinh.setText("");
            txtInputCapDai.setText("");
            txtInputURLImage.setText("");
            txtInputTen.setText("");
            //displaying a success toast
            Toast.makeText(getContext(), "Đã thêm Học Viên", Toast.LENGTH_LONG).show();

        } else {
            //if the value is not given displaying a toast
            Toast.makeText(getContext(), "Xin hãy nhập Học Viên", Toast.LENGTH_LONG).show();
        }
    }
}
