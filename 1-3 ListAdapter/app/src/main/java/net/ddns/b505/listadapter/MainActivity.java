package net.ddns.b505.listadapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView tvLLlight, tvLLac;
    private LinearLayout LLHlight, LLHac;
    private ArrayList<HashMap<String, String>> ArrayLight = new ArrayList<HashMap<String,String>>();
    private ArrayList<HashMap<String, String>> ArrayAc = new ArrayList<HashMap<String,String>>();
    private ListView lv_light, lv_ac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.ToolBar);
        setSupportActionBar(toolbar);
        setTitle(null); //取消原本的標題
        TextView title = findViewById(R.id.toolbarTitle);
        title.setText("行控中心大廳");
        lv_light = findViewById(R.id.lv_light);
        lv_ac = findViewById(R.id.lv_ac);
        getHemsAppliance();
        setLinearLayoutaction();
    }

    private void setLinearLayoutaction(){
        LinearLayout LLlight = findViewById(R.id.layout_light);
        LinearLayout LLac = findViewById(R.id.layout_ac);
        LLHlight = findViewById(R.id.layout_hide_light);
        LLHac = findViewById(R.id.layout_hide_ac);
        tvLLlight = findViewById(R.id.layout_light_tv);
        tvLLac = findViewById(R.id.layout_ac_tv);
        LLlight.setOnClickListener(llListener);
        LLac.setOnClickListener(llListener);
    }
    private Button.OnClickListener llListener = new Button.OnClickListener() {
        boolean light, ac;
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.layout_light:
                    light = !light;
                    LLHlight.setVisibility(light ? View.VISIBLE : View.GONE);
                    tvLLlight.setText(light ? "-" : "+");
                    break;
                case R.id.layout_ac:
                    ac = !ac;
                    LLHac.setVisibility(ac ? View.VISIBLE : View.GONE);
                    tvLLac.setText(ac ? "-" : "+");
                    break;
            }
        }
    };

    public void getHemsAppliance() {
        try {

            String response = "{\"status\":true,\"message\":\"\",\"data\":{\"list\":[{\"id\":\"1\",\"field\":\"1f_lobby\",\"name\":\"ac_1\",\"auto_control_mode\":\"auto_comfort_1\",\"switch\":\"0\",\"setting\":\"25\",\"power\":null,\"average_power\":\"2.600\",\"controller_name\":\"AirConditioner\",\"controller_address\":\"2\",\"dr_switch\":null,\"dr_priority\":\"1\",\"dr_recover\":\"0\",\"sheeding_willing\":\"9\",\"web_display\":\"空調1\",\"update_time\":\"2020-07-03 10:17:05\"},{\"id\":\"2\",\"field\":\"1f_lobby\",\"name\":\"ac_2\",\"auto_control_mode\":\"auto_comfort_1\",\"switch\":\"0\",\"setting\":\"25\",\"power\":null,\"average_power\":null,\"controller_name\":\"AirConditioner\",\"controller_address\":\"1\",\"dr_switch\":null,\"dr_priority\":\"0\",\"dr_recover\":\"0\",\"sheeding_willing\":\"9\",\"web_display\":\"空調2\",\"update_time\":\"2020-07-03 10:17:05\"},{\"id\":\"3\",\"field\":\"1f_lobby\",\"name\":\"ac_3\",\"auto_control_mode\":\"auto_comfort_2\",\"switch\":\"0\",\"setting\":\"25\",\"power\":null,\"average_power\":null,\"controller_name\":\"AirConditioner\",\"controller_address\":\"3\",\"dr_switch\":null,\"dr_priority\":\"0\",\"dr_recover\":\"0\",\"sheeding_willing\":\"9\",\"web_display\":\"空調3\",\"update_time\":\"2020-07-03 10:17:05\"},{\"id\":\"6\",\"field\":\"1f_lobby\",\"name\":\"light_1\",\"auto_control_mode\":null,\"switch\":\"0\",\"setting\":null,\"power\":null,\"average_power\":\"0.100\",\"controller_name\":\"LightSwitch\",\"controller_address\":\"67\",\"dr_switch\":null,\"dr_priority\":\"2\",\"dr_recover\":\"0\",\"sheeding_willing\":\"7\",\"web_display\":\"大廳入口電燈\",\"update_time\":\"2020-07-17 05:20:11\"},{\"id\":\"7\",\"field\":\"1f_lobby\",\"name\":\"light_2\",\"auto_control_mode\":null,\"switch\":\"0\",\"setting\":null,\"power\":null,\"average_power\":null,\"controller_name\":\"LightSwitch\",\"controller_address\":\"68\",\"dr_switch\":null,\"dr_priority\":\"0\",\"dr_recover\":\"0\",\"sheeding_willing\":\"7\",\"web_display\":\"警衛區電燈1\",\"update_time\":\"2020-07-16 19:55:05\"},{\"id\":\"8\",\"field\":\"1f_lobby\",\"name\":\"light_3\",\"auto_control_mode\":null,\"switch\":\"0\",\"setting\":null,\"power\":null,\"average_power\":null,\"controller_name\":\"LightSwitch\",\"controller_address\":\"69\",\"dr_switch\":null,\"dr_priority\":\"0\",\"dr_recover\":\"0\",\"sheeding_willing\":\"7\",\"web_display\":\"警衛區電燈2\",\"update_time\":\"2020-07-17 09:52:55\"},{\"id\":\"9\",\"field\":\"1f_lobby\",\"name\":\"light_4\",\"auto_control_mode\":null,\"switch\":\"0\",\"setting\":null,\"power\":null,\"average_power\":null,\"controller_name\":\"LightSwitch\",\"controller_address\":\"70\",\"dr_switch\":null,\"dr_priority\":\"0\",\"dr_recover\":\"0\",\"sheeding_willing\":\"7\",\"web_display\":\"廁所門口電燈\",\"update_time\":\"2020-07-17 15:35:48\"},{\"id\":\"10\",\"field\":\"1f_lobby\",\"name\":\"light_5\",\"auto_control_mode\":null,\"switch\":\"0\",\"setting\":null,\"power\":null,\"average_power\":null,\"controller_name\":\"LightSwitch\",\"controller_address\":\"71\",\"dr_switch\":null,\"dr_priority\":\"0\",\"dr_recover\":\"0\",\"sheeding_willing\":\"7\",\"web_display\":\"展示區電燈1\",\"update_time\":\"2020-07-17 14:51:56\"},{\"id\":\"11\",\"field\":\"1f_lobby\",\"name\":\"light_6\",\"auto_control_mode\":null,\"switch\":\"0\",\"setting\":null,\"power\":null,\"average_power\":null,\"controller_name\":\"LightSwitch\",\"controller_address\":\"72\",\"dr_switch\":null,\"dr_priority\":\"0\",\"dr_recover\":\"0\",\"sheeding_willing\":\"7\",\"web_display\":\"展示區電燈2\",\"update_time\":\"2020-07-17 14:52:07\"},{\"id\":\"27\",\"field\":\"1f_lobby\",\"name\":\"plug_002\",\"auto_control_mode\":null,\"switch\":null,\"setting\":null,\"power\":null,\"average_power\":null,\"controller_name\":null,\"controller_address\":null,\"dr_switch\":null,\"dr_priority\":null,\"dr_recover\":null,\"sheeding_willing\":null,\"web_display\":\"大廳飲水機\",\"update_time\":\"2019-10-28 17:08:32\"}]},\"sql\":\"SELECT * FROM hems_appliances WHERE field = :field \"}";
            Log.d("response", response);
            JSONObject object = new JSONObject(response);
            JSONArray list = object.getJSONObject("data").getJSONArray("list");
            Log.d("test", list.getJSONObject(0).getString("web_display"));

            for(int i = 0; i < list.length(); i++){
                HashMap<String, String> itemLight = new HashMap<String, String>();
                HashMap<String, String> itemAc = new HashMap<String, String>();
                HashMap<String, String> itemFan = new HashMap<String, String>();
                switch (list.getJSONObject(i).getString("name").split("_")[0]){
                    case "light":
                        itemLight.put("tv_ctrl_name", list.getJSONObject(i).getString("web_display"));
                        itemLight.put("tv_ctrl_status", list.getJSONObject(i).getString("setting"));
                        itemLight.put("img_ctrl_status", list.getJSONObject(i).getString("switch"));
                        itemLight.put("type", "light");
                        itemLight.put("field", list.getJSONObject(i).getString("field"));
                        itemLight.put("name", list.getJSONObject(i).getString("name"));
                        itemLight.put("address", list.getJSONObject(i).getString("controller_address"));
                        ArrayLight.add(itemLight);
                        break;
                    case "ac":
                        itemAc.put("tv_ctrl_name", list.getJSONObject(i).getString("web_display"));
                        itemAc.put("tv_ctrl_status", list.getJSONObject(i).getString("setting"));
                        itemAc.put("img_ctrl_status", list.getJSONObject(i).getString("switch"));
                        itemAc.put("type", "ac");
                        itemAc.put("field", list.getJSONObject(i).getString("field"));
                        itemAc.put("name", list.getJSONObject(i).getString("name"));
                        itemAc.put("address", list.getJSONObject(i).getString("controller_address"));
                        ArrayAc.add(itemAc);
                        break;
                }
            }
            CtrlViewAdapter light = new CtrlViewAdapter(MainActivity.this, ArrayLight);
            lv_light.setAdapter(light);
            CtrlViewAdapter ac = new CtrlViewAdapter(MainActivity.this, ArrayAc);
            lv_ac.setAdapter(ac);
            Helper.getListViewSize(lv_light);
            Helper.getListViewSize(lv_ac);
        } catch (JSONException jsone) {
            Log.e("json exception", jsone.toString());
        }
    }
}

