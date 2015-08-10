package com.neusoft.sheng.event_food;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.neusoft.sheng.event_food_db.DBTool;
import com.neusoft.sheng.event_food_db.Shop_db;
import com.neusoft.sheng.event_food_util.BaseApplicationInterface;
import com.neusoft.sheng.event_food_util.Food_info;

import java.io.InputStream;


public class Food_info_Activity extends Activity {

    Food_info food_info;
    Intent intent;
    ImageView imageView;
    TextView text_price, text_valuesation, text_mater;
    Button btn_call,btn_collect,btn_back;

    AssetManager assetManager;
    String phone;

    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_info);
        intent = getIntent();
        food_info = (Food_info) intent.getSerializableExtra("food");
        init();
        //imageView.setImageResource(food_info.getFood_pic());
        text_price.setText("价格￥"+food_info.getFood_price()+"/份");
        text_valuesation.setText("月售："+food_info.getFood_sell()+"份");
        text_mater.setText(food_info.getFood_mater());



        assetManager=this.getAssets();
        try {
            InputStream in=assetManager.open("ic_shop_" + food_info.getFood_pic());
            Bitmap bmp= BitmapFactory.decodeStream(in);
            imageView.setImageBitmap(bmp);
            in.close();
        } catch (Exception e) {

        }


        View.OnClickListener btn_listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (view.getId())
                {
                    case R.id.btn_back:
                        finish();
                        break;
                    case R.id.btn_collect:
                        Toast.makeText(getApplicationContext(), "收藏", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.btn_call:


                        DBTool dbTool=new DBTool(BaseApplicationInterface.databasePath);

                        Shop_db shop_db=new Shop_db(dbTool);

                        phone=shop_db.getShop_phoneByShop_id(food_info.getShop_id());


                        AlertDialog.Builder aler = new AlertDialog.Builder(
                                view.getContext());

                        DialogInterface.OnClickListener dia_listener = new DialogInterface.OnClickListener()
                        {

                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {




                                switch (which)
                                {
                                    case -1:


                                        Intent phoneIntent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phone));
                                        startActivity(phoneIntent);

                                        break;
                                    case -2:

                                        break;

                                }
                            }
                        };

                        aler.setIcon(R.drawable.ic_launcher);
                        aler.setTitle("确认结果");
                        aler.setMessage("拨打：" +phone+"？");
                        aler.setPositiveButton("拨打", dia_listener);
                        aler.setNegativeButton("取消", dia_listener);
                        aler.show();
                        break;
                }
            }
        };


        btn_call.setOnClickListener(btn_listener);
        btn_back.setOnClickListener(btn_listener);
        btn_collect.setOnClickListener(btn_listener);



    }

    private void init() {
        imageView = (ImageView) findViewById(R.id.imageView);
        btn_call = (Button) findViewById(R.id.btn_call);
        text_price=(TextView)findViewById(R.id.text_prince);
        text_valuesation = (TextView) findViewById(R.id.text_valuesation);
        text_mater=(TextView)findViewById(R.id.text_mater);
        btn_back=(Button)findViewById(R.id.btn_back);
        btn_collect=(Button)findViewById(R.id.btn_collect);
    }


    @Override
    public void finish() {
        super.finish();

         view=findViewById(R.id.food_info_linearlayout);
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeScaleUpAnimation(view, //The View that the new activity is animating from
                        (int)view.getWidth()/2, (int)view.getHeight()/2, //拉伸开始的坐标
                        0, 0);//拉伸开始的区域大小，这里用（0，0）表示从无到全屏

        startNewAcitivity(options);


    }


    private void startNewAcitivity(ActivityOptionsCompat options) {

        Intent intent = new Intent();

intent.setClass(getApplicationContext(),MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ActivityCompat.startActivity(Food_info_Activity.this, intent, options.toBundle());
    }



    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        System.out.println("onNewIntent");
    }
}
