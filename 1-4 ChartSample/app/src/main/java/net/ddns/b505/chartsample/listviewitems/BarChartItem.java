package net.ddns.b505.chartsample.listviewitems;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import net.ddns.b505.chartsample.*;

import java.util.ArrayList;

public class BarChartItem extends ChartItem {

    private Typeface mTf;
    private String title, x, y;
    private Context context;
    private ArrayList<String> label;
    float groupSpace = 0.08f;
    float barSpace = 0.06f; // x4 DataSet
    float barWidth = 0.4f; // x4 DataSet
    int groupCount = 1;
    private boolean multiple;
    private ArrayList<LegendEntry> legendEntries = new ArrayList<LegendEntry>();
    protected String[] values = new String[]{
            "1"
    };

    public BarChartItem(ChartData<?> cd, Context c, String title, ArrayList<String> label, String yUnit, String xUnit, boolean multiple) {
        super(cd);
        for (int i=0; i < cd.getDataSetLabels().length; i++){
            LegendEntry legendEntry = new LegendEntry();
            legendEntry.label = cd.getDataSetLabels()[i];
            legendEntry.formColor = cd.getColors()[i];
            this.legendEntries.add(legendEntry);
        }
        this.groupCount = cd.getDataSetLabels().length;
        this.title = title;
        this.label = label;
        this.context = c;
        this.x = xUnit;
        this.y = yUnit;
        this.multiple = multiple;

        // mTf = Typeface.createFromAsset(c.getAssets(), "OpenSans-Regular.ttf");
    }

    @Override
    public int getItemType() {
        return TYPE_BARCHART;
    }

    @Override
    public View getView(int position, View convertView, Context c) {

        ViewHolder holder = null;

        if (convertView == null) {

            holder = new ViewHolder();

            convertView = LayoutInflater.from(c).inflate(
                    R.layout.list_item_barchart, null);
            holder.chart = convertView.findViewById(R.id.chart);
            holder.tv_title = convertView.findViewById(R.id.tv_title);
            holder.y_unit = convertView.findViewById(R.id.tv_y_unit);
            holder.x_unit = convertView.findViewById(R.id.tv_x_unit);
            holder.y_unit.setText(y);
            holder.x_unit.setText(x);
            RotateAnimation ranim = (RotateAnimation) AnimationUtils.loadAnimation(context, R.anim.rotate_anim);
            ranim.setFillAfter(true); //For the button to remain at the same place after the rotation
            holder.y_unit.setAnimation(ranim);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_title.setText(title);
        // apply styling
        holder.chart.getDescription().setEnabled(false);
        holder.chart.getLegend().setCustom(legendEntries);
        holder.chart.setDrawGridBackground(false);
        holder.chart.setDrawBarShadow(false);

        XAxis xAxis = holder.chart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        //xAxis.setTypeface(mTf);
        xAxis.setEnabled(true);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(true);
        xAxis.setLabelCount(2);
        xAxis.setLabelCount(5, false);
        // 自定義x軸顯示
        MyXFormatter formatter = new MyXFormatter(values);
        // xAxis.setValueFormatter(formatter);

        // 要是遇到這行報錯，代表輸入資料(x,y)有問題，記得先用Log印出資料檢查看看
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if( value%1 == 0)
                    return label.get((int)value);
                else
                    return " ";
            }
        });

        YAxis leftAxis = holder.chart.getAxisLeft();
        //leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(5, false);
        leftAxis.setDrawAxisLine(true);
        leftAxis.setSpaceTop(20f);
        //y軸最小值
        // leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = holder.chart.getAxisRight();
        rightAxis.setDrawAxisLine(false);
        rightAxis.setDrawLabels(false);
        // y軸最小值
        // rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        //mChartData.setValueTypeface(mTf);


        MyMarkerView mv = new MyMarkerView(context, R.layout.custom_marker_view_layout, y);
        mv.setChartView(holder.chart);
        holder.chart.setMarker(mv);
        // set data
        holder.chart.setData((BarData) mChartData);
        ((BarData) mChartData).setBarWidth(barWidth);
        holder.chart.setFitBars(true);
        // 決定多筆柱狀圖是否重疊，要重疊就把下面這兩行註解掉
        if(multiple)
            // 決定柱狀的起始位置，如果格式有跑掉，就從這裡慢慢去修改
            holder.chart.groupBars(1.5f,groupSpace,barSpace);
        // do not forget to refresh the chart
        // holder.chart.invalidate();
        holder.chart.animateY(700);

        return convertView;
    }

    private static class ViewHolder {
        BarChart chart;
        TextView tv_title, y_unit, x_unit;
    }
}
