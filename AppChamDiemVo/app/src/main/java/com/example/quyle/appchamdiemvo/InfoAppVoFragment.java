package com.example.quyle.appchamdiemvo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.List;

import Model.InfoVoviNam;
import View.Adapter.InfoVovinamAdapter;
import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;

public class InfoAppVoFragment extends Fragment {
    FeatureCoverFlow featureCoverFlow;
    List<InfoVoviNam> lsCategory = new ArrayList<>();
    private TextSwitcher mTitle;
    InfoVovinamAdapter mAdapter;
    public InfoAppVoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewroot = inflater.inflate(R.layout.info_fragment, container, false);
        initData();
        mTitle = (TextSwitcher) viewroot.findViewById(R.id.txtSwitcher);
        mTitle.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                TextView title = (TextView) inflater.inflate(R.layout.layout_tittle,null);
                return  title;
            }
        });
        Animation animation_in = AnimationUtils.loadAnimation(getContext(),R.anim.slideintop);
        Animation animation_out = AnimationUtils.loadAnimation(getContext(),R.anim.slide_out_bottom);
        mTitle.setAnimation(animation_in);
        mTitle.setAnimation(animation_out);

        mAdapter = new InfoVovinamAdapter(lsCategory,getContext());
        featureCoverFlow = (FeatureCoverFlow) viewroot.findViewById(R.id.coverFlow);
        featureCoverFlow.setAdapter(mAdapter);
        featureCoverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(),lsCategory.get(i).categoryInfo,Toast.LENGTH_SHORT).show();
            }
        });

        featureCoverFlow.setOnScrollPositionListener(new FeatureCoverFlow.OnScrollPositionListener() {
            @Override
            public void onScrolledToPosition(int position) {
                mTitle.setText(lsCategory.get(position).categoryInfo);
            }

            @Override
            public void onScrolling() {
            }
        });
        return viewroot;
    }

    private void initData() {
        lsCategory.add(new InfoVoviNam("Hệ Thống Các Cấp Đai","https://upload.wikimedia.org/wikipedia/commons/thumb/7/7e/Vovinam_vophuc_1.png/320px-Vovinam_vophuc_1.png"));
        lsCategory.add(new InfoVoviNam("Lịch Sử Hình Thành","http://t2.rbxcdn.com/57c77273f74a308f546513e65d97aca4"));
        lsCategory.add(new InfoVoviNam("Lý Thuyết Võ Đạo","https://i.ytimg.com/vi/1qZgFAYCMKw/maxresdefault.jpg"));
        lsCategory.add(new InfoVoviNam("Video Về VoviNam","https://i.ytimg.com/vi/I_XwLNIGNyw/maxresdefault.jpg"));
    }
}
