package View.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.quyle.appchamdiemvo.R;

import java.util.List;

import Model.CapDai;
import Model.DotThi;

/**
 * Created by vtnhan on 10/28/2017.
 */

public class CapDaiAdapter extends ArrayAdapter<CapDai> {
    private Activity context;
    List<CapDai> lsCapDai;
    public CapDaiAdapter(Activity context, List<CapDai> lsCapDai) {
        super(context, R.layout.row_cap_dai, lsCapDai);
        this.context = context;
        this.lsCapDai = lsCapDai;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.row_cap_dai, null, true);

        TextView txtNameCapDai =  listViewItem.findViewById(R.id.txtNameCapDai);


        CapDai capDai = lsCapDai.get(position);
        txtNameCapDai.setText(capDai._TenCapDai);
        return listViewItem;
    }
}
