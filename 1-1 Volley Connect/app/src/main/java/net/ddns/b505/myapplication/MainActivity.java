package net.ddns.b505.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private RequestQueue requestQueue;
    private StringRequest postRequest;
    private String url = "http://163.18.57.43/demo/demo_sql.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1、創建請求隊列
        requestQueue = Volley.newRequestQueue(this);
        final EditText ed_user = findViewById(R.id.ed_user);
        final EditText ed_pass = findViewById(R.id.ed_pass);
        Button btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*"" => 空字串
                * equals => 是否與 括號 裡的物件 相等
                * edittext.gettext() => 取得 EditText 資料
                * .toString() => 轉型 成String
                * trim() => 資料內容去除 空格後，剩下的資料
                * */
                if("".equals(ed_user.getText().toString().trim()) || "".equals(ed_pass.getText().toString().trim()))
                    Toast.makeText(getApplicationContext(), "實功輸出不能為空！", Toast.LENGTH_SHORT).show();
                else
                /**2、實例化StringRequest請求
                 * 第一個參數選擇Request.Method.GET即get請求方式
                 * 第二個參數的url地址根據文檔所給
                 * 第三個參數Response.Listener 請求成功的回調
                 * 第四個參數Response.ErrorListener 請求失敗的回調
                 */
                    postRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    //String response即為服務器返回的數據
                                    try {
                                        //JSONArray data = new JSONArray(response);
                                        //Log.d("response-getJSONObject", data.getJSONObject(0).toString());
                                        Log.d("response", response);
                                        //浮動視窗顯示回傳值
                                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                                    } catch (Exception e) {
                                        Log.e("Exception",e.toString());
                                    }
                                }
                            }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Log.e("volleyError", volleyError.toString());
                        }
                    })
                    {
                        //POST輸入格式
                        //params.put(這裡放Key, 這裡放Value);
                        @Override
                        protected Map<String, String> getParams()
                        {
                            Map<String, String>  params = new HashMap<String, String>();
                            params.put("action", "update");
                            params.put("username", ed_user.getText().toString());
                            params.put("password", ed_pass.getText().toString());
                            return params;
                        }
                    };
                //3、將請求添加進請求隊列
                requestQueue.add(postRequest);

            }
        });
    }

}
