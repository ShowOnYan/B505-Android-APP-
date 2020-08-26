package net.ddns.b505.chartsample.listviewitems;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.List;
import java.util.Objects;

/**
 * Created by chenzhiheng on 2018/3/8.
 */

public class ChartDataAdapter extends ArrayAdapter<ChartItem> {

    public ChartDataAdapter(Context context, List<ChartItem> objects) {
        super(context, 0, objects);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return Objects.requireNonNull(getItem(position)).getView(position, convertView, getContext());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int getItemViewType(int position) {
        // return the views type
        return Objects.requireNonNull(getItem(position)).getItemType();
    }

}