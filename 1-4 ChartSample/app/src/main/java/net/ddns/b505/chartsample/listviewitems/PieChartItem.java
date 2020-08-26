package net.ddns.b505.chartsample.listviewitems;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import net.ddns.b505.chartsample.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PieChartItem extends ChartItem {

    private SpannableString mCenterText;
    private String title;
    private Context context;
    private ArrayList<String> labels;

    public PieChartItem(ChartData<?> cd, Context c, String title, ArrayList<String> labels) {
        super(cd);
        this.title = title;
        this.context = c;
        this.labels = labels;
        //Log.d("Pie", String.valueOf(cd));
        // 設置PieChart中間的字
        // mCenterText = generateCenterText();
    }

    @Override
    public int getItemType() {
        return TYPE_PIECHART;
    }

    @Override
    public View getView(int position, View convertView, Context c) {

        ViewHolder holder = null;

        if (convertView == null) {

            holder = new ViewHolder();

            convertView = LayoutInflater.from(c).inflate(
                    R.layout.list_item_piechart, null);
            holder.chart = convertView.findViewById(R.id.chart);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // apply styling
        holder.chart.getDescription().setEnabled(false);
        holder.chart.setHoleRadius(52f);
        holder.chart.setTransparentCircleRadius(57f);
        holder.chart.setCenterText(title);
        holder.chart.setCenterTextSize(17f);
        holder.chart.setUsePercentValues(true);
        holder.chart.setEntryLabelColor(Color.BLACK);
        holder.chart.setExtraOffsets(5, 10, 50, 10);
        holder.chart.setNoDataText("尚無資料");
        holder.chart.setDrawEntryLabels(false);

        mChartData.setValueFormatter(new DecimalRemover(new DecimalFormat("###,###,###.##")));
        mChartData.setValueTextSize(11f);
        mChartData.setValueTextColor(Color.BLACK);
        // set data
        holder.chart.setData((PieData) mChartData);

        Legend l = holder.chart.getLegend();
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        l.setTextColor(Color.BLACK);

        // do not forget to refresh the chart
        // holder.chart.invalidate();
        holder.chart.animateY(900);


        PieMarkerView mv = new PieMarkerView(context, R.layout.custom_marker_view_layout, "%", labels);
        mv.setChartView(holder.chart);
        holder.chart.setMarker(mv);

        return convertView;
    }

    private SpannableString generateCenterText() {
        SpannableString s = new SpannableString("MPAndroidChart\ncreated by\nPhilipp Jahoda");
        s.setSpan(new RelativeSizeSpan(1.6f), 0, 14, 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.VORDIPLOM_COLORS[0]), 0, 14, 0);
        s.setSpan(new RelativeSizeSpan(.9f), 14, 25, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, 25, 0);
        s.setSpan(new RelativeSizeSpan(1.4f), 25, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), 25, s.length(), 0);
        return s;
    }

    private static class ViewHolder {
        PieChart chart;
    }
    public class DecimalRemover extends PercentFormatter {

        protected DecimalFormat mFormat;

        public DecimalRemover(DecimalFormat format) {
            this.mFormat = format;
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            // 百分比小於5 %時，不顯示數值
            if(value < 5) return "";
            return mFormat.format(value) + " %";
        }
    }
}
