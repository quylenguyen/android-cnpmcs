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

import Model.DotThi;

/**
 * Created by quyle on 12/7/2017.
 */

public class DotThiList extends ArrayAdapter<DotThi> {
    private Activity context;
    List<DotThi> dotThiList;

    public DotThiList(Activity context, List<DotThi> dotThiList) {
        super(context, R.layout.list_layout, dotThiList);
        this.context = context;
        this.dotThiList = dotThiList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        DotThi dotthi = dotThiList.get(position);
        textViewName.setText(dotthi.getTenDotThi());

        return listViewItem;
    }
}