package com.example.quyle.appchamdiemvo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Model.HocVien;


public class TinhDiemFragment extends Fragment {
    public TinhDiemFragment() {
    }
    DatabaseReference dbHV;
    public static String id;
    TextView txtTen;
    EditText txtND1,txtND2,txtND3,txtND4,txtND5;
    Button btnSave;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.tinhdiem_fragment,container,false);
        dbHV = FirebaseDatabase.getInstance().getReference("HocVien");
        txtTen = viewRoot.findViewById(R.id.txtNameOfHV);
        txtND1 = viewRoot.findViewById(R.id.txtDiemND1);
        txtND2 = viewRoot.findViewById(R.id.txtDiemND2);
        txtND3 = viewRoot.findViewById(R.id.txtDiemND3);
        txtND4 = viewRoot.findViewById(R.id.txtDiemND4);
        txtND5 = viewRoot.findViewById(R.id.txtDiemND5);
        btnSave = viewRoot.findViewById(R.id.btnSaveDiem);
        Bundle bundle = getArguments();
        id = bundle.get("ID").toString();
        final String name = bundle.get("Ten").toString();
        final String ngaySinh = bundle.get("NgaySinh").toString();
        final String capDai = bundle.get("CapDai").toString();
        final String donVi = bundle.get("DonVi").toString();
        txtTen.setText(name);
        txtND1.setText(bundle.get("DiemND1").toString());
        txtND2.setText(bundle.get("DiemND2").toString());
        txtND3.setText(bundle.get("DiemND3").toString());
        txtND4.setText(bundle.get("DiemND4").toString());
        txtND5.setText(bundle.get("DiemND5").toString());


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n1 = Integer.parseInt(txtND1.getText().toString());
                int n2 = Integer.parseInt(txtND2.getText().toString());
                int n3 = Integer.parseInt(txtND3.getText().toString());
                int n4 = Integer.parseInt(txtND4.getText().toString());
                int n5 = Integer.parseInt(txtND5.getText().toString());
                HocVien hv = new HocVien(id,name,ngaySinh,capDai,donVi,n1,n2,n3,n4,n5,(n1+n2+n3+n4+n5));
                dbHV.child(id).setValue(hv);
                Toast.makeText(getContext(),"Lưu Bài Thi Thành Công",Toast.LENGTH_LONG).show();
                FragmentTransaction trans = getFragmentManager()
                        .beginTransaction();
                HocVienFragment hocvienFragment = new HocVienFragment();
                trans.replace(R.id.root_frame, hocvienFragment);
                trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                trans.addToBackStack(null);
                trans.detach(hocvienFragment).attach(hocvienFragment);
                trans.detach(hocvienFragment).attach(hocvienFragment);

                trans.commit();

            }
        });
        return viewRoot;

    }



}
