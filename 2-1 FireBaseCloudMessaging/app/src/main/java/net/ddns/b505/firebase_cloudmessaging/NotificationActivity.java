package net.ddns.b505.firebase_cloudmessaging;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class NotificationActivity extends AppCompatActivity {
    private RequestQueue requestQueue;
    private String url = "http://163.18.57.43/notice-sample.php";
    private StringRequest postRequest;
    private String[] strList = {"請選擇", "全部", "地下二樓", "地下一樓", "一樓", "二樓", "三樓", "四樓", "五樓", "六樓"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        requestQueue = Volley.newRequestQueue(this);
        setTitle("APP推播");
        final TextView notice = findViewById(R.id.ed_title);
        final TextView content = findViewById(R.id.ed_content);
        Button save = findViewById(R.id.btn_save);
        Button close = findViewById(R.id.btn_close);
        final Spinner object = findViewById(R.id.sp_object);
        ArrayAdapter<String> listFloor;
        listFloor = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                strList);
        object.setAdapter(listFloor);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String role = "";
                int count = object.getSelectedItemPosition();
                if(strList[count].equals("請選擇"))
                    Toast.makeText(NotificationActivity.this, "請選擇推播對象", Toast.LENGTH_SHORT).show();
                else {
                    switch (strList[count]) {
                        case "地下二樓" :
                            role = "/topics/B2";
                            break;
                        case "地下一樓" :
                            role = "/topics/B1";
                            break;
                        case "一樓" :
                            role = "/topics/1F";
                            break;
                        case "二樓" :
                            role = "/topics/2F";
                            break;
                        case "三樓" :
                            role = "/topics/3F";
                            break;
                        case "四樓" :
                            role = "/topics/4F";
                            break;
                        case "五樓" :
                            role = "/topics/5F";
                            break;
                        case "六樓" :
                            role = "/topics/6F";
                            break;
                    }
                    sendNotification(role, notice.getText().toString(), content.getText().toString());
                }
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationActivity.this.finish();
            }
        });
    }

    public void sendNotification(final String role,final String title,final String body) {
        //以下透過API以JSON格式傳輸資料
        postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        //Log.d("sendNotification", response);
                        Toast.makeText(NotificationActivity.this, response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        //Log.d("sendNotification.Error", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("id", "");
                params.put("role", role);
                params.put("title", title);
                params.put("body", body);

                //Log.d("sendNotification", role);
                return params;
            }
        };
        postRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 5;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
    }
}
