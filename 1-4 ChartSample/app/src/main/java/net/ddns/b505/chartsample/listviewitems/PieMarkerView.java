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

import net.ddns.b505.chartsample.R;

import java.util.ArrayList;

public class PieMarkerView extends MarkerView {

    private TextView tvContent, tvLabel;
    private String y;
    private ArrayList<String> labels;

    public PieMarkerView(Context context, int layoutResource, String yUnit, ArrayList<String> labels) {
        super(context, layoutResource);
        this.y = yUnit;
        this.labels = labels;

        //tvLabel = findViewById(R.id.tvLabel);
        tvContent = findViewById(R.id.tvContent);
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        if (e instanceof CandleEntry) {

            CandleEntry ce = (CandleEntry) e;

            tvContent.setText("" + Utils.formatNumber(ce.getHigh(), 2, true) + " " + y);
            //Log.d("XXX",Utils.formatNumber(ce.getHigh(), 2, true).toString());
        } else {

            //tvLabel.setText(labels.get((int)highlight.getX()));
            //tvContent.setText(Utils.formatNumber(e.getY(), 2, true) + " " + y);
            tvContent.setText(labels.get((int)highlight.getX()) + "：" +
                    String.format("%.2f",e.getY()) + " " + y);
        }

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
