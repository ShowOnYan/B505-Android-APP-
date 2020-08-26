package net.ddns.b505.chartsample.listviewitems;

import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;


public class Chart implements OnChartGestureListener {
    private Context context;
    // 圓餅圖的類別顏色，如果超過8個類別再自行增加
    private int[] Piecolors = {Color.rgb(255,255,100),Color.rgb(100,255,255),
            Color.rgb(255,100,255),Color.rgb(200,200,200),Color.rgb(150,200,150),
            Color.rgb(200,255,150),Color.rgb(150,255,200),Color.rgb(150,200,255)};

    public Chart(Context context) {
        super();
        this.context = context;
    }
    // 單筆資料折線圖
    public LineData generateDataLine(String label_name, ArrayList<Entry> dataset, int colors) {
        ArrayList<ILineDataSet> sets = new ArrayList<ILineDataSet>();

        LineDataSet d1 = new LineDataSet(dataset, label_name);
        d1.setLineWidth(2.5f);
        d1.setCircleRadius(4.5f);
        d1.setColors(colors);
        d1.setCircleColors(colors);
        d1.setHighLightColor(Color.rgb(244, 117, 117));
        d1.setDrawValues(false);
        sets.add(d1);

        LineData cd = new LineData(sets);
        return cd;
    }
    // 多筆資料折線圖
    public LineData generateSixDataLine(ArrayList<String> label_name, ArrayList<ArrayList> dataset, ArrayList<Integer> colors) {
        ArrayList<ILineDataSet> sets = new ArrayList<ILineDataSet>();
        for(int i = 0; i < dataset.size(); i++){
            LineDataSet d1 = new LineDataSet(dataset.get(i), label_name.get(i));
            d1.setLineWidth(2f);
            d1.setCircleRadius(0f);
            d1.setColors(colors.get(i));
            d1.setCircleColors(colors.get(i));
            d1.setHighLightColor(colors.get(i));
            d1.setDrawValues(false);
            sets.add(d1);
        }

        LineData cd = new LineData(sets);
        return cd;
    }

    // 單筆資料柱狀圖
    public BarData generateDataBar(String label_name, ArrayList<BarEntry> dataset, int colors) {
        ArrayList<IBarDataSet> sets = new ArrayList<IBarDataSet>();
        List<Integer> positive_negative_colors = new ArrayList<>();
        for (int i = 0; i < dataset.size(); i++) {

            // 資料大於小於零時，呈現不同柱狀顏色的方式
            if (dataset.get(i).getY() >= 0)
                positive_negative_colors.add(0xFF0099FF);
            else
                positive_negative_colors.add(0xFFFF0000);
        }

        BarDataSet d = new BarDataSet(dataset, label_name);
        // d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        d.setColors(positive_negative_colors);
        d.setValueTextColor(colors);
        d.setHighLightAlpha(Color.rgb(244, 117, 117));
        d.setDrawValues(false); // 是否在點上加數值
        d.setHighLightAlpha(255);
        sets.add(d);

        BarData cd = new BarData(sets);
        cd.setBarWidth(0.9f);
        return cd;
    }
    // 多筆資料柱狀圖
    public BarData generateSixDataBar(ArrayList<String> label_name, ArrayList<ArrayList> dataset, ArrayList<Integer> colors) {
        ArrayList<IBarDataSet> sets = new ArrayList<IBarDataSet>();
        for(int i = 0; i < dataset.size(); i++){
            BarDataSet d1 = new BarDataSet(dataset.get(i), label_name.get(i));
            d1.setColors(colors.get(i));
            d1.setDrawValues(false);
            d1.setHighLightAlpha(255);

            sets.add(d1);
        }

        BarData cd = new BarData(sets);
        cd.setBarWidth(0.9f);
        return cd;
    }

    // 圓餅圖
    public PieData generateDataPie(ArrayList<String> label_name, ArrayList<Double> dataset) {

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        for (int i = 0; i < label_name.size(); i++) {
            entries.add(new PieEntry(Float.parseFloat(String.valueOf(dataset.get(i))), label_name.get(i)));
        }

        PieDataSet d = new PieDataSet(entries, "");

        // space between slices
        d.setSliceSpace(2f);
        // 這邊先定義7種色，要是PieChart超過7類別的話，上方顏色定義再自行加入顏色
        d.setColors(Piecolors);

        PieData cd = new PieData(d);
        return cd;
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        //Log.d("Chart", "onChartGestureStart");
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        //Log.d("Chart", "onChartGestureEnd");
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        //Log.d("Chart", "onChartLongPressed");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        //Log.d("Chart", "onChartDoubleTapped");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        //Log.d("Chart", "onChartSingleTapped");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        //Log.d("Chart", "onChartFling");
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        //Log.d("Chart", "onChartScale");
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        //Log.d("Chart", "onChartTranslate");
    }
}
