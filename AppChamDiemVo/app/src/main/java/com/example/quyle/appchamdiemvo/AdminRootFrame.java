package com.example.quyle.appchamdiemvo;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by vtnhan on 12/9/2017.
 */

public class AdminRootFrame extends Fragment {
    public AdminRootFrame() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.admin_root_fragment,container,false);

        FragmentTransaction transaction = getFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.admin_root_frame, new QuanLyDotThiFragmnet());

        transaction.commit();
        return  viewRoot;
    }
}
