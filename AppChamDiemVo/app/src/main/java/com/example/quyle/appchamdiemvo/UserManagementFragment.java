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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

import Model.HocVien;
import Model.User;
import View.Adapter.HocVienAdapter;
import View.Adapter.UserAdapter;

/**
 * Created by vtnhan on 12/8/2017.
 */

public class UserManagementFragment extends Fragment {
    List<User> lsUser = new ArrayList<>();
    RecyclerView revUser;
    DatabaseReference dbUser;
    UserAdapter adater;
    MaterialEditText txtInputTenUser, txtInputEmail, txtInputPass, txtInputPhoneNumber;
    Button btnSaveUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.quan_ly_user_fragment, container, false);
        revUser = viewRoot.findViewById(R.id.recyclerQLUser);
        txtInputEmail = viewRoot.findViewById(R.id.txtInputEmailUser);
        txtInputPass = viewRoot.findViewById(R.id.txtInputPassUser);
        txtInputTenUser = viewRoot.findViewById(R.id.txtInPutTenUser);
        txtInputPhoneNumber = viewRoot.findViewById(R.id.txtInputPhoneUser);
        btnSaveUser = viewRoot.findViewById(R.id.btnQLLuuUser);
        dbUser = FirebaseDatabase.getInstance().getReference("Users");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        revUser.setLayoutManager(layoutManager);
        dbUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lsUser.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    User user = postSnapshot.getValue(User.class);
                    lsUser.add(user);
                }
                adater = new UserAdapter(lsUser, new UserAdapter.OnLongClickUserListener() {
                    @Override
                    public void onItemLongClick(User user) {
                        showUpdateDialog(user.Phone,user.Name,user.Email,user.Pass);
                    }
                }, getContext());
                revUser.setAdapter(adater);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        btnSaveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });
        return viewRoot;
    }

    private void addUser() {
        //getting the values to save
        String name = txtInputTenUser.getText().toString().trim();
        String Email = txtInputEmail.getText().toString().trim();
        String PhoneNumber = txtInputPhoneNumber.getText().toString().trim();

        String Pass = txtInputPass.getText().toString().trim();


        //checking if the value is provided
        if (!TextUtils.isEmpty(name)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            //creating an Artist Object
            User user = new User(Email,name,Pass);
            user.Phone = PhoneNumber;
            //Saving the Artist
            dbUser.child(PhoneNumber).setValue(user);
            //setting edittext to blank again
            //displaying a success toast
            Toast.makeText(getContext(), "Đã thêm User", Toast.LENGTH_LONG).show();
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(getContext(), "Xin hãy nhập User", Toast.LENGTH_LONG).show();
        }
    }

    private void showUpdateDialog(final String idUser, final String name, String email, String pass) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_user_dialog, null);
        dialogBuilder.setView(dialogView);
//        final TextView textViewTenDotThiCu = (TextView) dialogView.findViewById(R.id.textView5);
        final MaterialEditText txtTen = dialogView.findViewById(R.id.txtQLTenUser);
        final MaterialEditText txtPass = dialogView.findViewById(R.id.txtQLPass);
        final MaterialEditText txtEmail = dialogView.findViewById(R.id.txtQLEmail);
        final Button btnUpdate = (Button) dialogView.findViewById(R.id.btnCapNhatUser);
        final Button btnDelete = (Button) dialogView.findViewById(R.id.btnXoatUser);
        dialogBuilder.setTitle("Cập Nhật Giám Khảo" + name);
        final AlertDialog b = dialogBuilder.create();
        b.show();
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtTen.getText().toString().trim();
                String pass = txtPass.getText().toString().trim();
                String email = txtEmail.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    txtTen.setError("Bạn không được để trống tên giám khảo");
                    return;
                } else if (TextUtils.isEmpty(pass)) {
                    txtTen.setError("Bạn không được để trống mật khẩu ");
                    return;
                }else if (TextUtils.isEmpty(email)) {
                    txtTen.setError("Bạn không được để trống tên email");
                    return;
                }
                updateUser(idUser, name, email, pass);
                b.dismiss();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser(idUser);
                b.dismiss();
            }
        });
    }

    private boolean updateUser(String phone, String name, String email, String pass) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("HocVien").child(phone);

        //updating artist
        User user = new User(email,name,pass);
        dR.setValue(user);
        Toast.makeText(getContext(), "Giám đã được cập nhật", Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean deleteUser(String phone) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("HocVien").child(phone);

        //removing artist
        dR.removeValue();

        Toast.makeText(getContext(), "Học Viên đã được xóa", Toast.LENGTH_LONG).show();

        return true;
    }
}
