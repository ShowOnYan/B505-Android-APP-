package net.ddns.b505.chartsample.listviewitems;


import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

import net.ddns.b505.chartsample.*;
/**
 * Custom implementation of the MarkerView.
 *
 * @author Philipp Jahoda
 */
//顯示數值
public class MyMarkerView extends MarkerView {

    private TextView tvContent;
    private String y;

    public MyMarkerView(Context context, int layoutResource, String yUnit) {
        super(context, layoutResource);
        this.y = yUnit;
        // 對應到R.layout.custom_marker_view_layout.xml
        tvContent = findViewById(R.id.tvContent);
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        if (e instanceof CandleEntry) {

            CandleEntry ce = (CandleEntry) e;

            tvContent.setText("" + Utils.formatNumber(ce.getHigh(), 2, true) + " " + y);
            //tvContent.setText("" + Utils.formatNumber(ce.getHigh(), 3, true) + " " + y);
            //Log.d("XXX",Utils.formatNumber(ce.getHigh(), 3, true).toString());
        } else {

            tvContent.setText("" + String.format("%.2f",e.getY()) + " " + y);
            //tvContent.setText("" + Utils.formatNumber(e.getY(), 3, true) + " " + y);
            //Log.d("YYY",Utils.formatNumber(e.getY(), 3, true).toString());
        }

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
