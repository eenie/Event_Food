package com.neusoft.sheng.event_food;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;

import com.neusoft.sheng.event_food_adapter.Collect_Listview_adapter;
import com.neusoft.sheng.event_food_util.Collect;

import java.util.ArrayList;


public class Collect_Activity extends Activity {

    private static ListView collect_list;
    private static ArrayList<String> food_id;
    static String user_name;
    Intent intent;

    private Button btn_collect_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.collect_list);



        collect_list = (ListView) findViewById(R.id.collect_list);
        btn_collect_back=(Button)findViewById(R.id.btn_collect_back);
        btn_collect_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                intent= getIntent();
//                intent.setClass(getApplicationContext(),MainActivity.class);
//                startActivity(intent);
//                finish();

                finish();
            }
        });





         user_name = getIntent().getStringExtra("user_name");
         upList(Collect_Activity.this);


    }


    public static void upList(Context context)
    {

        Collect collect = new Collect();
        food_id = collect.getCollect_food_idByuserid(user_name);
        Collect_Listview_adapter collect_listview_adapter = new Collect_Listview_adapter(context, food_id);
        collect_list.setAdapter(collect_listview_adapter);

    }


}
