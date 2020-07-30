package net.ddns.b505.listadapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CtrlViewAdapter extends BaseAdapter {
    private Context context;
    private Activity mActivity;
    private ArrayList<HashMap<String, String>> list;
    private int currentItem = -1; //用於紀錄點擊的 Item 的 position。是控制 item 展開的核心
    private int[] status;
    private BroadcastReceiver mBroadcast =  new BroadcastReceiver() {
        @Override
        public void onReceive(Context mContext, Intent mIntent) {

        }
    };
    public CtrlViewAdapter(Activity context,
                           ArrayList<HashMap<String, String>> list) {
        super();
        this.context = context;
        this.list = list;
        this.mActivity= context;
        this.status = new int[list.size()];
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final CtrlViewAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_ctrl_view, parent, false);
            holder = new CtrlViewAdapter.ViewHolder();
            holder.layout_ac = convertView
                    .findViewById(R.id.layout_ac);
            holder.layout_light = convertView
                    .findViewById(R.id.layout_light);
            holder.tv_ctrl_name = convertView
                    .findViewById(R.id.tv_ctrl_name);
            holder.tv_ctrl_status = convertView
                    .findViewById(R.id.tv_ctrl_status);
            holder.img_ctrl_status = convertView
                    .findViewById(R.id.img_ctrl_status);
            holder.sw_switch = convertView
                    .findViewById(R.id.sw_switch);
            holder.btn_ac_air = convertView
                    .findViewById(R.id.btn_ac_air);
            holder.btn_ac_dehumidification = convertView
                    .findViewById(R.id.btn_ac_dehumidification);
            holder.btn_ac_fan = convertView
                    .findViewById(R.id.btn_ac_fan);
            holder.sb_ac = convertView
                    .findViewById(R.id.sb_ac);
            holder.sb_light = convertView
                    .findViewById(R.id.sb_light);

            convertView.setTag(holder);
        } else {
            holder = (CtrlViewAdapter.ViewHolder) convertView.getTag();
        }
        // 注意：我們在此給響應點擊事件的區域（樣例裡是 showArea 的線性布局）加入Tag。為了紀錄點擊的 position。我們正好用 position 設置 Tag

        switch (Objects.requireNonNull(list.get(position).get("type"))) {
            case "light":
                holder.layout_light.setVisibility(View.VISIBLE);
                switch (Objects.requireNonNull(list.get(position).get("img_ctrl_status"))) {
                    case "1":
                        holder.sw_switch.setChecked(true);
                        holder.sb_light.setEnabled(true);
                        if(Objects.equals(list.get(position).get("field"), "2f_meeting_room")) {
                            holder.sb_light.setProgress(Integer.parseInt(Objects.requireNonNull(list.get(position).get("tv_ctrl_status"))));
                            holder.tv_ctrl_status.setText("亮度："+list.get(position).get("tv_ctrl_status")+" %");
                        }else
                            holder.sb_light.setVisibility(View.GONE);
                        holder.img_ctrl_status.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.bulb_on));
                    break;
                    case "0":
                        holder.sw_switch.setChecked(false);
                        holder.sb_light.setEnabled(false);
                        if(Objects.equals(list.get(position).get("field"), "2f_meeting_room")) {
                            holder.sb_light.setProgress(Integer.parseInt(Objects.requireNonNull(list.get(position).get("tv_ctrl_status"))));
                            holder.tv_ctrl_status.setText("亮度："+list.get(position).get("tv_ctrl_status")+" %");
                        }else
                            holder.sb_light.setVisibility(View.GONE);
                        holder.img_ctrl_status.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.bulb_off));
                    break;
                }
                break;
            case "ac":
                holder.layout_ac.setVisibility(View.VISIBLE);
                switch (Objects.requireNonNull(list.get(position).get("img_ctrl_status"))) {
                    case "1":
                        holder.sb_ac.setEnabled(true);
                        holder.btn_ac_fan.setEnabled(true);
                        holder.btn_ac_dehumidification.setEnabled(true);
                        holder.btn_ac_air.setEnabled(true);
                        holder.sw_switch.setChecked(true);
                        holder.img_ctrl_status.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ac_on));
                        switch (Objects.requireNonNull(list.get(position).get("tv_ctrl_status"))) {
                            case "0":
                                holder.btn_ac_fan.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.togBlue));
                                holder.btn_ac_fan.setTextColor(ContextCompat.getColor(mActivity, R.color.White));
                                holder.btn_ac_air.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.White));
                                holder.btn_ac_air.setTextColor(ContextCompat.getColor(mActivity, R.color.togBlue));
                                holder.btn_ac_dehumidification.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.White));
                                holder.btn_ac_dehumidification.setTextColor(ContextCompat.getColor(mActivity, R.color.togBlue));
                                holder.tv_ctrl_status.setText("模式：送風");
                                break;
                            case "1":
                                holder.btn_ac_dehumidification.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.togBlue));
                                holder.btn_ac_dehumidification.setTextColor(ContextCompat.getColor(mActivity, R.color.White));
                                holder.btn_ac_air.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.White));
                                holder.btn_ac_air.setTextColor(ContextCompat.getColor(mActivity, R.color.togBlue));
                                holder.btn_ac_fan.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.White));
                                holder.btn_ac_fan.setTextColor(ContextCompat.getColor(mActivity, R.color.togBlue));
                                holder.tv_ctrl_status.setText("模式：除濕");
                                break;
                            default:
                                holder.btn_ac_air.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.togBlue));
                                holder.btn_ac_air.setTextColor(ContextCompat.getColor(mActivity, R.color.White));
                                holder.btn_ac_dehumidification.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.White));
                                holder.btn_ac_dehumidification.setTextColor(ContextCompat.getColor(mActivity, R.color.togBlue));
                                holder.btn_ac_fan.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.White));
                                holder.btn_ac_fan.setTextColor(ContextCompat.getColor(mActivity, R.color.togBlue));
                                holder.sb_ac.setProgress(Integer.parseInt(Objects.requireNonNull(list.get(position).get("tv_ctrl_status")))-18);
                                holder.tv_ctrl_status.setText("溫度："+list.get(position).get("tv_ctrl_status")+" ℃");
                                break;
                        }
                        break;
                    case "0":
                        holder.sb_ac.setEnabled(false);
                        holder.btn_ac_fan.setEnabled(false);
                        holder.btn_ac_dehumidification.setEnabled(false);
                        holder.btn_ac_air.setEnabled(false);
                        holder.sw_switch.setChecked(false);
                        holder.img_ctrl_status.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ac_off));
                        holder.tv_ctrl_status.setText("");
                        break;
                }
                break;
        }
        holder.tv_ctrl_name.setText(list.get(position).get("tv_ctrl_name"));

        holder.sw_switch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String action_name = "", auto_control = "";
                try {
                    if(holder.sw_switch.isChecked()) {
                        switch (Objects.requireNonNull(list.get(position).get("type"))) {
                            case "sign_light":
                                action_name = "DOController";
                                break;
                            case "light":
                                holder.sb_light.setEnabled(true);
                                action_name = "LightSwitch";
                                auto_control = "auto_dimming";
                                if(Objects.equals(list.get(position).get("field"), "2f_meeting_room")) {
                                    action_name = "DimmerControl";
                                    holder.layout_light.setVisibility(View.VISIBLE);
                                    holder.sb_light.setProgress(Integer.parseInt(Objects.requireNonNull(list.get(position).get("tv_ctrl_status"))));
                                    holder.tv_ctrl_status.setText("亮度："+list.get(position).get("tv_ctrl_status")+" %");
                                }
                                break;
                            case "ac":
                                holder.sb_ac.setEnabled(true);
                                holder.btn_ac_fan.setEnabled(true);
                                holder.btn_ac_dehumidification.setEnabled(true);
                                holder.btn_ac_air.setEnabled(true);
                                action_name = "AirConditioner";
                                auto_control = "auto_comfort";
                                holder.layout_ac.setVisibility(View.VISIBLE);
                                break;
                        }
                    }else {
                        switch (Objects.requireNonNull(list.get(position).get("type"))) {
                            case "sign_light":
                                action_name = "DOController";
                                holder.tv_ctrl_status.setText("");
                                break;
                            case "light":
                                holder.sb_light.setEnabled(false);
                                action_name = "LightSwitch";
                                auto_control = "auto_dimming";
                                if(Objects.equals(list.get(position).get("field"), "2f_meeting_room"))
                                    action_name = "DimmerControl";
                                holder.tv_ctrl_status.setText("");
                                break;
                            case "ac":
                                holder.sb_ac.setEnabled(false);
                                holder.btn_ac_fan.setEnabled(false);
                                holder.btn_ac_dehumidification.setEnabled(false);
                                holder.btn_ac_air.setEnabled(false);
                                action_name = "AirConditioner";
                                auto_control = "";
                                holder.tv_ctrl_status.setText("");
                                break;
                        }
                    }
                } catch (Exception e) {
                    Log.e("switchException", e.toString());
                }
            }
        });
        holder.btn_ac_air.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                holder.btn_ac_air.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.togBlue));
                holder.btn_ac_air.setTextColor(ContextCompat.getColor(mActivity, R.color.White));
                holder.btn_ac_dehumidification.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.White));
                holder.btn_ac_dehumidification.setTextColor(ContextCompat.getColor(mActivity, R.color.togBlue));
                holder.btn_ac_fan.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.White));
                holder.btn_ac_fan.setTextColor(ContextCompat.getColor(mActivity, R.color.togBlue));
            }
        });
        holder.btn_ac_dehumidification.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                holder.btn_ac_dehumidification.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.togBlue));
                holder.btn_ac_dehumidification.setTextColor(ContextCompat.getColor(mActivity, R.color.White));
                holder.btn_ac_air.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.White));
                holder.btn_ac_air.setTextColor(ContextCompat.getColor(mActivity, R.color.togBlue));
                holder.btn_ac_fan.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.White));
                holder.btn_ac_fan.setTextColor(ContextCompat.getColor(mActivity, R.color.togBlue));
            }
        });
        holder.btn_ac_fan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                holder.btn_ac_fan.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.togBlue));
                holder.btn_ac_fan.setTextColor(ContextCompat.getColor(mActivity, R.color.White));
                holder.btn_ac_air.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.White));
                holder.btn_ac_air.setTextColor(ContextCompat.getColor(mActivity, R.color.togBlue));
                holder.btn_ac_dehumidification.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.White));
                holder.btn_ac_dehumidification.setTextColor(ContextCompat.getColor(mActivity, R.color.togBlue));
            }
        });
        holder.sb_ac.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            String value = "0";
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // 元件停止調整時要做的動作寫在這
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                value = String.valueOf(progress+18);
                holder.tv_ctrl_status.setText("溫度："+value+" ℃");
            }
        });
        holder.sb_light.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            String value = "0";
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // 元件停止調整時要做的動作寫在這
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                value = String.valueOf(progress+2);
                holder.tv_ctrl_status.setText("亮度："+value+" %");
            }
        });

        return convertView;
    }

    private static class ViewHolder {
        private LinearLayout layout_ac, layout_light;
        private TextView
                tv_ctrl_name, tv_ctrl_status;
        private ImageView
                img_ctrl_status;
        private Switch
                sw_switch;
        private Button
                btn_ac_air, btn_ac_dehumidification, btn_ac_fan;
        private SeekBar
                sb_ac, sb_light;
    }

}