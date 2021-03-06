package net.ddns.b505.bottomnavigationview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ViewPager viewPager;
    MenuItem prevMenuItem;
    private Toolbar toolbar;
    private TextView title;
    String user;
    private int[] TollBarTitle = {R.string.title_home, R.string.title_power,R.string.title_control,R.string.title_user,R.string.title_dr};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Log.d("User","使用者: "+user);
        toolbar = (Toolbar) findViewById(R.id.ToolBar);
        setSupportActionBar(toolbar);
        setTitle(null); //取消原本的標題
        title = findViewById(R.id.toolbarTitle);
        title.setText(TollBarTitle[0]);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        //toolbar.getMenu().clear();
                        switch (item.getItemId()) {
                            //點擊首頁
                            case R.id.navigation_home:
                                viewPager.setCurrentItem(0);
                                title.setText(TollBarTitle[0]);
                                break;
                            //點擊PV、EV、ESS
                            case R.id.navigation_power:
                                viewPager.setCurrentItem(1);
                                title.setText(TollBarTitle[1]);
                                break;
                            //點擊電器控制
                            case R.id.navigation_control:
                                viewPager.setCurrentItem(2);
                                title.setText(TollBarTitle[2]);
                                break;
                            //點擊用電資訊
                            case R.id.navigation_user:
                                viewPager.setCurrentItem(3);
                                title.setText(TollBarTitle[3]);
                                break;
                            //點擊需量反應
                            case R.id.navigation_dr:
                                viewPager.setCurrentItem(4);
                                title.setText(TollBarTitle[4]);
                                break;
                        }
                        return false;
                    }
                });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);
                toolbar.getMenu().clear();
                switch (position) {
                    case 0:
                        toolbar.inflateMenu(R.menu.menu_home);
                        title.setText(TollBarTitle[0]);
                        break;
                    case 1:
                        toolbar.inflateMenu(R.menu.menu_home);
                        title.setText(TollBarTitle[1]);
                        break;
                    case 2:
                        toolbar.inflateMenu(R.menu.menu_home);
                        title.setText(TollBarTitle[2]);
                        break;
                    case 3:
                        toolbar.inflateMenu(R.menu.menu_user);
                        title.setText(TollBarTitle[3]);
                        break;
                    case 4:
                        toolbar.inflateMenu(R.menu.menu_user);
                        title.setText(TollBarTitle[4]);
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        // 如果想禁止滑動，可以把下面的代碼取消註解
     /* viewPager.setOnTouchListener(new View.OnTouchListener() {
           @Override
          public boolean onTouch(View v, MotionEvent event) {
               return true;
          }
        });*/

        //設定fragment分頁頁數
        setViewPager();
    }
    //設置Activity初始的Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }
    //toolbar按鈕事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int item_id = item.getItemId();
        Intent reit = new Intent();
        FragmentTransaction _FragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment _Fragment = getSupportFragmentManager().findFragmentByTag("");
        if (_Fragment != null) {
            _FragmentTransaction.remove(_Fragment);
        }
        _FragmentTransaction.addToBackStack(null);
        DialogFragment newFragment;
        switch (item_id){
            case R.id.ItemLogout: //登出
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("確認視窗")
                        .setMessage("確定要登出嗎?")
                        .setPositiveButton("確定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        //File file = new File("/data/data/net.ddns.b505.xlbems/shared_prefs","auto.xml");
                                        //file.delete();
                                        //跳轉至登入頁
                                        /*
                                        *
                                        editor.putBoolean("auto_check",false).commit();
                                        Intent reit2 = new Intent();
                                        reit2.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                        reit2.setClass(MainActivity.this, LoginActivity.class);
                                        startActivity(reit2);
                                        finish();
                                        * */
                                    }
                                })
                        .setNegativeButton("取消",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        // TODO Auto-generated method stub
                                    }
                                }).show();
                break;
            /*
            case R.id.ItemUserDB: //用戶歷史事件
                FragmentTransaction SelectFragmentTransaction = getSupportFragmentManager().beginTransaction();
                Fragment Select = getSupportFragmentManager().findFragmentByTag("Select");
                if (Select != null) {
                    SelectFragmentTransaction.remove(Select);
                }
                SelectFragmentTransaction.addToBackStack(null);
                DialogFragment newFragment2 = new SelectFragment();
                newFragment2.show(SelectFragmentTransaction, "dr");
                break;
            case R.id.EnergyUsed: //用戶用電資訊
                reit1.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                reit1.setClass(MainActivity.this, UserActivity.class);
                startActivity(reit1);
                break;

            default: return false;
            */
        }
        return true;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) { // 攔截返回鍵
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("確認視窗")
                    .setMessage("確定要結束應用程式嗎?")
                    .setPositiveButton("確定",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                    finish();
                                }
                            })
                    .setNegativeButton("取消",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // TODO Auto-generated method stub
                                }
                            }).show();
        }
        return true;
    }

    private void setViewPager(){
        IndexFragment Home = new IndexFragment();
        PowerFragment Power = new PowerFragment();
        ControlFragment Control = new ControlFragment();
        UserFragment User = new UserFragment();
        DRFragment Reaction = new DRFragment();
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(Home);
        fragmentList.add(Power);
        fragmentList.add(Control);
        fragmentList.add(User);
        fragmentList.add(Reaction);
        ViewPagerFragmentAdapter myFragmentAdapter = new ViewPagerFragmentAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(myFragmentAdapter);
    }
}
