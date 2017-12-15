package com.example.quyle.appchamdiemvo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

import Model.DotThi;
import Model.HocVien;
import View.Adapter.HocVienAdapter;

/**
 * Created by vtnhan on 12/9/2017.
 */

public class QuanLyHV extends Fragment {
    List<HocVien> lsHV = new ArrayList<>();
    RecyclerView lvHV;
    DatabaseReference dbHV;
    HocVienAdapter adater;
    MaterialEditText txtInputTen, txtInputNgaySinh, txtInputCapDai, txtInputDonVi, txtInputURLImage;
    Button btnSaveHV;

    public QuanLyHV() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.quanly_hv_fragment, container, false);
        lvHV = viewRoot.findViewById(R.id.recyclerQLHocVien);
        txtInputCapDai = viewRoot.findViewById(R.id.txtQLInputCapDai);
        txtInputDonVi = viewRoot.findViewById(R.id.txtQLInputDonVi);
        txtInputNgaySinh = viewRoot.findViewById(R.id.txtQLInputNgaySinh);
        txtInputTen = viewRoot.findViewById(R.id.txtQLInPutTen);
        btnSaveHV = viewRoot.findViewById(R.id.btnQLLuuHV);
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
                adater = new HocVienAdapter(lsHV, getContext(), new HocVienAdapter.OnLongClickHVListener() {
                    @Override
                    public void onItemLongClick(HocVien hv) {
                        showUpdateDialog(hv.id,hv._Ten,hv._NgaySinh,hv._DaiHienTai,hv._DonVi);
                    }
                });
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
            txtInputCapDai.clearValidators();
            //displaying a success toast
            Toast.makeText(getContext(), "Đã thêm Học Viên", Toast.LENGTH_LONG).show();
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(getContext(), "Xin hãy nhập Học Viên", Toast.LENGTH_LONG).show();
        }
    }

    private void showUpdateDialog(final String idHV, final String tenHocVien, String date, String capDai, String donVi) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.updatehvdialog, null);
        dialogBuilder.setView(dialogView);
//        final TextView textViewTenDotThiCu = (TextView) dialogView.findViewById(R.id.textView5);
        final MaterialEditText txtTen = dialogView.findViewById(R.id.txtQLTen);
        final MaterialEditText txtNgaySinh = dialogView.findViewById(R.id.txtQLNgaySinh);
        final MaterialEditText txtCapDai = dialogView.findViewById(R.id.txtQLCapDai);
        final MaterialEditText txtDonVi = dialogView.findViewById(R.id.txtQLDonVi);
        final Button btnUpdate = (Button) dialogView.findViewById(R.id.btnCapNhatHV);
        final Button btnDelete = (Button) dialogView.findViewById(R.id.btnXoatHV);
        dialogBuilder.setTitle("Cập Nhật Học Viên" + tenHocVien);
        final AlertDialog b = dialogBuilder.create();
        b.show();
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtTen.getText().toString().trim();
                String date = txtNgaySinh.getText().toString().trim();
                String capDai = txtCapDai.getText().toString().trim();
                String donVi = txtDonVi.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    txtTen.setError("Bạn không được để trống tên học viên");
                    return;
                } else if (TextUtils.isEmpty(date)) {
                    txtTen.setError("Bạn không được để trống ngày sinh");
                    return;
                }else if (TextUtils.isEmpty(donVi)) {
                    txtTen.setError("Bạn không được để trống tên đơn vị");
                    return;
                }else if (TextUtils.isEmpty(capDai)) {
                    txtTen.setError("Bạn không được để trống tên cấp đai");
                    return;
                }
                updateHV(idHV, name, donVi, capDai, date);
                b.dismiss();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteHV(idHV);
                b.dismiss();
            }
        });
    }

    private boolean updateHV(String id, String name, String donVi, String capDai, String ngaySinh) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("HocVien").child(id);

        //updating artist
        HocVien hv = new HocVien(id,name, ngaySinh, donVi, capDai);
        dR.setValue(hv);
        Toast.makeText(getContext(), "Học viên đã được cập nhật", Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean deleteHV(String id) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("HocVien").child(id);

        //removing artist
        dR.removeValue();

        Toast.makeText(getContext(), "Học Viên đã được xóa", Toast.LENGTH_LONG).show();

        return true;
    }
}
