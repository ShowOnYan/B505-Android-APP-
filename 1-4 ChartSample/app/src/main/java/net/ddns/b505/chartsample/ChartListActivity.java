package net.ddns.b505.chartsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;

import net.ddns.b505.chartsample.listviewitems.*;

import java.util.ArrayList;

public class ChartListActivity extends AppCompatActivity {

    private ListView lv, lv2, lv3, lv4;
    private ArrayList<String> dataset_id = new ArrayList<>();
    private ArrayList<ChartItem> list = new ArrayList<>(), list2 = new ArrayList<>()
            , list3 = new ArrayList<>(), list4 = new ArrayList<>();
    private ArrayList<ArrayList> totalList_line = new ArrayList<>(), totalList_bar = new ArrayList<>();
    private ArrayList<Integer> colors = new ArrayList<>();
    ArrayList<String> labels = new ArrayList<>();
    ArrayList<Double> powerData = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_list);
        //
        lv = findViewById(R.id.listView);
        lv2 = findViewById(R.id.listView2);
        lv3 = findViewById(R.id.listView3);
        lv4 = findViewById(R.id.listView4);
        Chart chart = new Chart(this);

        // 定義LineData第一筆資料
        ArrayList<Entry> dataset1 = new ArrayList<>();
        dataset1.add(new Entry(2, 1));
        dataset1.add(new Entry(3, 3));
        dataset1.add(new Entry(4, 3));

        // 定義LineData第二筆資料
        ArrayList<Entry> dataset2 = new ArrayList<>();
        dataset2.add(new Entry(3, 0));
        dataset2.add(new Entry(4, 2));
        dataset2.add(new Entry(5, 4));
        dataset2.add(new Entry(6, 5));
        dataset2.add(new Entry(7, 6));
        dataset2.add(new Entry(8, 7));
        dataset2.add(new Entry(9, 8));

        // 定義BareData第一筆資料
        ArrayList<BarEntry> dataset3 = new ArrayList<>();
        dataset3.add(new BarEntry(2, 2));
        dataset3.add(new BarEntry(3, 1));
        dataset3.add(new BarEntry(4, 3));

        // 定義BarData第二筆資料
        ArrayList<BarEntry> dataset4 = new ArrayList<>();
        dataset4.add(new BarEntry(2, 2));
        dataset4.add(new BarEntry(3, 1));
        dataset4.add(new BarEntry(4, 2));
        dataset4.add(new BarEntry(5, 4));
        dataset4.add(new BarEntry(6, 5));
        dataset4.add(new BarEntry(7, 6));
        dataset4.add(new BarEntry(8, 7));
        dataset4.add(new BarEntry(9, 8));

        // 定義BarData第二筆資料
        ArrayList<BarEntry> dataset5 = new ArrayList<>();
        dataset5.add(new BarEntry(2, 2));
        dataset5.add(new BarEntry(3, 1));
        dataset5.add(new BarEntry(4, 2));
        dataset5.add(new BarEntry(5, 4));
        dataset5.add(new BarEntry(6, 5));
        dataset5.add(new BarEntry(7, 6));
        dataset5.add(new BarEntry(8, 7));
        dataset5.add(new BarEntry(9, 8));

        // 定義LineData線條與BarData圓柱的顏色
        colors.add(0xFFFF0000);
        colors.add(0xFF0099FF);
        colors.add(0xFF00FFFF);

        // 定義LineData與BarData的x軸單位
        String xUnit = "月";

        // 定義LineData與BarData的y軸單位
        String yUnit = "kW";

        // 定義LineData與BarData的資料名稱
        dataset_id.add("歷史資料 (kWh)");
        dataset_id.add("預測值 (kWh)");
        dataset_id.add("第三筆 (kWh)");

        //將多筆資料再儲存至一陣列清單中，給多筆資料折線圖表使用
        totalList_line.add(dataset1);
        totalList_line.add(dataset2);

        //將多筆資料再儲存至一陣列清單中，給多筆資料柱狀圖表使用
        totalList_bar.add(dataset3);
        totalList_bar.add(dataset4);
        totalList_bar.add(dataset5);

        // 定義圓餅圖的類別
        labels.add("空調");
        labels.add("燈具");
        labels.add("插座");

        //輸入圓餅圖類別對應的數值(%)
        powerData.add(30.0);
        powerData.add(4.0);
        powerData.add(66.0);

        // 定義單筆資料時，LineData線條與BarData圓柱的顏色
        int color = 0xFFFF0000;

        // 建立多筆資料折線圖，並加入至圖表清單
        list.add(new LineChartItem(chart.generateSixDataLine(dataset_id , totalList_line, colors), ChartListActivity.this, "實功 (kW)", getLabels(), yUnit, xUnit));
        // 建立單筆資料折線圖，並加入至圖表清單
        list.add(new LineChartItem(chart.generateDataLine(dataset_id.get(0), dataset2, color), ChartListActivity.this, "實功 (kW)", getLabels(), yUnit, xUnit));
        // 建立單筆資料柱狀圖，並加入至圖表清單 p.s. 注意 單筆資料時，最後的參數『multple』填1
        list2.add(new BarChartItem(chart.generateDataBar(dataset_id.get(0), dataset4, color), ChartListActivity.this, "發電量 (kWh)", getLabels(), yUnit, xUnit, 1));
        // 建立多筆資料柱狀圖，並加入至圖表清單 p.s. 注意 多筆資料時，最後的參數『multple』填上資料筆數
        list3.add(new BarChartItem(chart.generateSixDataBar(dataset_id, totalList_bar, colors), ChartListActivity.this, "發電量 (kWh)", getLabels(), yUnit, xUnit, totalList_bar.size()));
        // 建立圓餅圖，並加入至圖表清單
        list4.add(new PieChartItem(chart.generateDataPie(labels , powerData), ChartListActivity.this, "用電數據", labels));


        ChartDataAdapter LineData = new ChartDataAdapter(ChartListActivity.this, list);
        lv.setAdapter(LineData);
        Helper.getListViewSize(lv);

        ChartDataAdapter BarData = new ChartDataAdapter(ChartListActivity.this, list2);
        lv2.setAdapter(BarData);
        Helper.getListViewSize(lv2);

        ChartDataAdapter BarData_multiple_2 = new ChartDataAdapter(ChartListActivity.this, list3);
        lv3.setAdapter(BarData_multiple_2);
        Helper.getListViewSize(lv3);

        ChartDataAdapter PieData = new ChartDataAdapter(ChartListActivity.this, list4);
        lv4.setAdapter(PieData);
        Helper.getListViewSize(lv4);
    }

    // 定義x軸資料
    private ArrayList<String> getLabels(){
        ArrayList<String> chartLabels = new ArrayList<>();
        // 預設10比資料，自由調整
        for(int i=0;i< 10;i++){
            chartLabels.add(String.valueOf(i));
        }
        return chartLabels;
    }
}