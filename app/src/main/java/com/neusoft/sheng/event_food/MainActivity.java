package com.neusoft.sheng.event_food;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.neusoft.sheng.event_food_adapter.Food_Listview_adapter;
import com.neusoft.sheng.event_food_adapter.Shop_Listview_adapter;
import com.neusoft.sheng.event_food_db.DBTool;
import com.neusoft.sheng.event_food_db.Food_db;
import com.neusoft.sheng.event_food_db.Shop_db;
import com.neusoft.sheng.event_food_util.BaseApplicationInterface;
import com.neusoft.sheng.event_food_util.Food_info;
import com.neusoft.sheng.event_food_util.Shop_info;
import com.neusoft.sheng.event_food_util.Util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;


public class MainActivity extends ActionBarActivity {

    public CoordinatorLayout rootLayout;
    private DrawerLayout mDrawerLayout;
    private ListView shop_list;
    private GridView food_list;
    private Toolbar toolbar;
    private TextView text_acti_title;
    private Food_db food_db;
    private Shop_db shop_db;
    ArrayList<Shop_info> Shop_infos=new ArrayList<Shop_info>();
    ArrayList<Food_info>food_infos=new ArrayList<Food_info>();

    WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;

    ActionBarDrawerToggle mDrawerToggle;


    final static String DB_NAME = "eatit.db";
    final static String TABLE_NAME = "shop_info";
    ApplicationInfo appinfo;
    String packgeName;
    String dbPath;

    DBTool dbTool;


    public CoordinatorLayout getRootLayout() {
        return rootLayout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY, "yXxVzMdUk8wyRSOsqiTGNGAC");



        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        rootLayout = (CoordinatorLayout) findViewById(R.id.rootLayout);
        food_list=(GridView)findViewById(R.id.food_list);
        text_acti_title=(TextView)findViewById(R.id.text_acti_title);

        shop_list = (ListView) findViewById(R.id.shop_list);
        FloatingActionButton fabBtn = (FloatingActionButton) findViewById(R.id.fabBtn);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name,
                R.string.hello_world);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);




        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) findViewById(R.id.main_swipe);
        mWaveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Do work to refresh the list here.
                new Task().execute();
            }
        });


        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println(view);

            }
        });
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//
//
//
//
//
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//
//                System.out.println(item);
//
//                switch (item.getItemId()) {
//                    case R.id.ab_search:
//                        Toast.makeText(MainActivity.this, "ab_search", Toast.LENGTH_SHORT).show();
//                        break;
//                    default:
//
//                        break;
//                }
//                return true;
//            }
//        });

        initdatabase();
        BaseApplicationInterface.databasePath=dbPath;
        dbTool = new DBTool(dbPath);

        food_db=new Food_db(dbTool);
        shop_db=new Shop_db(dbTool);


        shop_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                shop_list.setItemChecked(position, true);


                food_infos = food_db.getFood(Shop_infos.get(position).getShop_id());
                Food_Listview_adapter food_listview_adapter = new Food_Listview_adapter(MainActivity.this, food_infos);


                food_list.setAdapter(food_listview_adapter);


                text_acti_title.setText(((TextView) view.findViewById(R.id.text_shop_name)).getText());
                Snackbar.make(rootLayout, ((TextView) view.findViewById(R.id.text_shop_name)).getText(), Snackbar.LENGTH_SHORT)
                        .setAction("清除", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                            }
                        })
                        .show();


                mDrawerLayout.closeDrawer(Gravity.LEFT);
            }
        });









        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user_name=Util.isLogin();

                if (user_name!=null)

                {
                    Intent intent = new Intent(MainActivity.this,
                            Collect_Activity.class);
                    intent.putExtra("user_name",user_name);
                    startActivity(intent);
                }

                else
                {
                    Snackbar.make(rootLayout, "要使用收藏夹，请先登录！", Snackbar.LENGTH_LONG)
                            .setAction("登录", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent intent = new Intent(MainActivity.this,
                                            Login_Activity.class);
                                    startActivity(intent);
                                }
                            })
                            .show();

                }




            }
        });


        Shop_infos=shop_db.getShop();
        food_infos=food_db.getFood();
       Shop_Listview_adapter shop_listview_adapter=new  Shop_Listview_adapter(MainActivity.this,Shop_infos);

        Food_Listview_adapter food_listview_adapter=new Food_Listview_adapter(MainActivity.this,food_infos);

        shop_list.setAdapter(shop_listview_adapter);
        food_list.setAdapter(food_listview_adapter);

    }

    private void initdatabase() {
        packgeName = getPackageName();
        System.out.println(packgeName);
        try {
            appinfo = getPackageManager().getApplicationInfo(packgeName,
                    PackageManager.GET_META_DATA);


            String dbDir = appinfo.dataDir + File.separator + "database";

            dbPath = dbDir + File.separator + DB_NAME;
            System.out.println("--dbpath=" + dbPath);
            File dbdir2 = new File(dbDir);
            if (!dbdir2.exists()) {
                dbdir2.mkdir();
            }

            InputStream ins = getAssets().open(DB_NAME);
            File dbFile = new File(dbPath);
            if (!dbFile.exists())

            {
                dbFile.createNewFile();
                FileOutputStream fos = new FileOutputStream(dbFile);
                byte[] buffer = new byte[1024];
                int count = 0;
                while ((count = ins.read(buffer)) != -1) {

                    fos.write(buffer, 0, count);

                }

                fos.close();
                ins.close();


            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         switch (item.getItemId()) {
         case R.id.head:
            String user_name= Util.isLogin();
             if (user_name!=null)
             {

                 Intent intent = new Intent(MainActivity.this,
                         Persion_center_Activity.class);
                 intent.putExtra("user_name",user_name);
                 startActivity(intent);
             }

             else {

                 Intent intent = new Intent(MainActivity.this,
                         Login_Activity.class);

                 startActivity(intent);
             }



         break;

         }
        return super.onOptionsItemSelected(item);
    }







    private class Task extends AsyncTask<Void, Void, String[]> {


        @Override
        protected String[] doInBackground(Void... params) {

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return new String[0];
        }

        @Override protected void onPostExecute(String[] result) {
            // Call setRefreshing(false) when the list has been refreshed.
            mWaveSwipeRefreshLayout.setRefreshing(false);
            super.onPostExecute(result);
        }
    }

}


