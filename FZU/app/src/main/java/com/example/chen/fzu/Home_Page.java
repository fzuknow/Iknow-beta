package com.example.chen.fzu;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.chen.My.Myself;

import java.util.ArrayList;
import java.util.List;

public class Home_Page extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__page);


        //初始化页面
        initView();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("福大知道");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//        TextView nicheng=(TextView)findViewById(R.id.name);
//        TextView lv=(TextView)findViewById(R.id.InputLv);
//        nicheng.setText(Login.userName);
//        lv.setText(Login.userLv);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.fzu_map) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://ditu.amap.com/place/B024F05T6I "));
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_myself) {
            Intent intent = new Intent(Home_Page.this, Myself.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.nav_wealthvalue) {
            Intent intent=new Intent(Home_Page.this,Wealth.class);
            startActivity(intent);

        } else if (id == R.id.nav_recyclr_bin) {
            Intent intent = new Intent(Home_Page.this, Recycle_bin.class);
            startActivity(intent);
        } else if (id == R.id.nav_setting) {

            Intent intent = new Intent(Home_Page.this, Setting.class);
            startActivity(intent);

        } else if (id == R.id.nav_login_out) {
            Intent intent=new Intent(Home_Page.this,Login.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private RadioGroup radioGroup;
    private RadioButton button_1;
    private RadioButton button_2;
    private RadioButton button_3;
    //private RadioButton button_4;
    private FirstPage fragment_1;
    private Question_Answer fragment_2;
    private Message fragment_3;
    //  private Fragment_4 fragment_4;
    private List<Fragment> list;
    private FrameLayout frameLayout;

    Drawable drawable;

    //初始化页面
    private void initView() {
        frameLayout = (FrameLayout) findViewById(R.id.framelayout);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        //找到四个按钮
        button_1 = (RadioButton) findViewById(R.id.button_1);
        button_2 = (RadioButton) findViewById(R.id.button_2);
        button_3 = (RadioButton) findViewById(R.id.button_3);
        // button_4 = (RadioButton) findViewById(R.id.button_4);

        Drawable drawable_news = getResources().getDrawable(R.drawable.homepage);
        //当这个图片被绘制时，给他绑定一个矩形 ltrb规定这个矩形
        drawable_news.setBounds(0, 0, 45, 45);
        //设置图片在文字的哪个方向
        button_1.setCompoundDrawables(null, drawable_news, null, null);

        Drawable wrapDrawable= DrawableCompat.wrap(drawable_news);
        DrawableCompat.setTintList(wrapDrawable, ColorStateList.valueOf(Color.GREEN));
        //定义底部标签图片大小和位置
        Drawable drawable_live = getResources().getDrawable(R.drawable.que);
        //当这个图片被绘制时，给他绑定一个矩形 ltrb规定这个矩形
        drawable_live.setBounds(0, 0, 45, 45);
        //设置图片在文字的哪个方向
        button_2.setCompoundDrawables(null, drawable_live, null, null);

        //定义底部标签图片大小和位置
        Drawable drawable_tuijian = getResources().getDrawable(R.drawable.mess);
        //当这个图片被绘制时，给他绑定一个矩形 ltrb规定这个矩形
        drawable_tuijian.setBounds(0, 0, 45, 45);
        //设置图片在文字的哪个方向
        button_3.setCompoundDrawables(null, drawable_tuijian, null, null);



        //创建Fragment对象及集合
        fragment_1 = new FirstPage();
        fragment_2 = new Question_Answer();
        fragment_3 = new Message();
        // fragment_4 = new Fragment_4();

        //将Fragment对象添加到list中
        list = new ArrayList<>();
        list.add(fragment_1);
        list.add(fragment_2);
        list.add(fragment_3);
        // list.add(fragment_4);

        //设置RadioGroup开始时设置的按钮，设置第一个按钮为默认值
        radioGroup.check(R.id.button_1);
      //  TextView tt=(TextView)findViewById(R.id.Article);

        //设置按钮点击监听
        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);
        button_3.setOnClickListener(this);
        //button_4.setOnClickListener(this);

        //初始时向容器中添加第一个Fragment对象
        addFragment(fragment_1);
    }

    @Override
    public void finish() {
        ViewGroup viewGroup = (ViewGroup) getWindow().getDecorView();
        viewGroup.removeAllViews();
        super.finish();
    }
//    private String result;
//    ProgressDialog dialog;
//    private Handler mHanlder = new Handler() {
//        @Override
//        public void handleMessage(android.os.Message message) {
//            result = (String) message.obj;
//            JSONObject resultJson = JSONObject.parseObject(result);
//            String finalResult = resultJson.getString("result");
//            System.out.println("结果是：" + finalResult);
//            if (finalResult.equals("success")) {
//                //转到文章详情界面
//                Intent intent=new Intent(Home_Page.this, ArticleDetail.class);
//                startActivity(intent);
//
//            } else{
//                Toast.makeText(Home_Page.this,"出现了一些小问题，请稍后重试！",Toast.LENGTH_LONG).show();
//            }
//
//        }
//    };



    //点击事件处理
    @Override
    public void onClick(View v) {
        //我们根据参数的id区别不同按钮
        //不同按钮对应着不同的Fragment对象页面
        if(v.getId()==R.id.button_1){
            System.out.println("yyyy");
           // button_1.setBackgroundTintList(drawable,ColorStateList.valueOf(Color.GREEN));
            //button_1.setBackground("@drawable/praise");
            addFragment(fragment_1);
        }else if(v.getId()==R.id.button_2){
            System.out.println(",,,,");

            addFragment(fragment_2);
        }else if(v.getId()==R.id.button_3){
            System.out.println("ffff");
            addFragment(fragment_3);
        }
        //else if(v.getId()==R.id.Article) {
//            new Thread() {
//                public void run() {
//                    String response = HttpUtil.doPostRequest(NetUtil.PATH_USER_LOGIN, "");
//                    android.os.Message message = android.os.Message.obtain();
//                    message.obj = response;
////                        System.out.println("返回"+response);
//                    mHanlder.sendMessage(message);
//                }
//            }.start();

     //   }

    }

    //向Activity中添加Fragment的方法
    public void addFragment(Fragment fragment) {

        //获得Fragment管理器
        FragmentManager fragmentManager = getSupportFragmentManager();
        //使用管理器开启事务
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //使用事务替换Fragment容器中Fragment对象
        fragmentTransaction.replace(R.id.framelayout,fragment);
        //提交事务，否则事务不生效
        fragmentTransaction.commit();
    }





}
