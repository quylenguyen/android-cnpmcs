package View.Adapter;

import android.app.Activity;
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

/**
 * Created by vtnhan on 12/8/2017.
 */

public class CapDaiAdapterList extends ArrayAdapter<CapDai> {
    private Activity context;
    List<CapDai> capDaiList;

    public CapDaiAdapterList(Activity context, List<CapDai> capDaiList) {
        super(context, R.layout.list_layout, capDaiList);
        this.context = context;
        this.capDaiList = capDaiList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);


        CapDai capDai = capDaiList.get(position);
        textViewName.setText(capDai._TenCapDai);

        return listViewItem;
    }
}
