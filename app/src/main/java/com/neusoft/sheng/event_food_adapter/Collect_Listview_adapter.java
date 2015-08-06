package com.neusoft.sheng.event_food_adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.neusoft.sheng.event_food.Collect_Activity;
import com.neusoft.sheng.event_food.Food_info_Activity;
import com.neusoft.sheng.event_food.R;
import com.neusoft.sheng.event_food_db.DBTool;
import com.neusoft.sheng.event_food_db.Food_db;
import com.neusoft.sheng.event_food_util.BaseApplicationInterface;
import com.neusoft.sheng.event_food_util.Collect;
import com.neusoft.sheng.event_food_util.Food_info;

import java.io.InputStream;
import java.util.ArrayList;

public class Collect_Listview_adapter extends BaseAdapter {


    private LayoutInflater inflater;
    private Context context;
    ArrayList<String> food_id;
    private ListView listView;


    Holder holder;

    AssetManager assetManager;


    public Collect_Listview_adapter(Context context, ArrayList<String> food_id) {
        this.context = context;
        this.food_id = food_id;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return food_id.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        listView=(ListView)inflater.inflate(R.layout.collect_list, null).findViewById(R.id.collect_list);



        view = inflater.inflate(R.layout.collect_list_item, null);

        holder = new Holder();




        DBTool dbTool = new DBTool(BaseApplicationInterface.databasePath);
        dbTool.openDatabase();
        Food_db food_db = new Food_db(dbTool);
        Food_info food_info = new Food_info();
        food_info = food_db.getFoodByfood_id(food_id.get(i));


        //System.out.println(food_db.getFoodByfood_id(food_id.get(i)).getFood_name());


        holder.image_collect_food_pic = (ImageView) view.findViewById(R.id.image_collect_food_pic);
        holder.text_collect_food_name = (TextView) view.findViewById(R.id.text_collect_food_name);
        holder.text_collect_food_sell = (TextView) view.findViewById(R.id.text_collect_food_sell);
        holder.text_collect_food_price = (TextView) view.findViewById(R.id.text_collect_food_price);
        holder.btn_item_collect_btn = (Button) view.findViewById(R.id.btn_item_collect_btn);


        holder.btn_item_collect_btn.setBackgroundResource(R.drawable.star);
        holder.text_collect_food_name.setText(food_info.getFood_name());

        holder.text_collect_food_sell.setText("Sell " + food_info.getFood_sell());
        holder.text_collect_food_price.setText("Price " + food_info.getFood_price());


        assetManager = context.getAssets();
        try {
            InputStream in = assetManager.open("ic_shop_" + food_info.getFood_pic());
            Bitmap bmp = BitmapFactory.decodeStream(in);
            holder.image_collect_food_pic.setImageBitmap(bmp);
            in.close();
        } catch (Exception e) {

        }


        view.setOnClickListener(new list_listener(food_info));
        holder.btn_item_collect_btn.setOnClickListener(new list_listener(food_info));

        return view;
    }


    class list_listener implements View.OnClickListener {

        Food_info food_info;

        public list_listener(Food_info food_info) {
            this.food_info = food_info;
        }

        @Override
        public void onClick(View view) {

            if (view.getId() != R.id.btn_item_collect_btn) {
                Intent intent = new Intent();
                intent.putExtra("food", food_info);
                intent.setClass(context, Food_info_Activity.class);
                context.startActivity(intent);


            } else {



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


                                Collect collect = new Collect(food_info.getFood_id(), context);
                                collect.delCollect();
                                Collect_Activity.upList(context);

                                break;
                            case -2:

                                break;

                        }
                    }
                };

                aler.setIcon(R.drawable.ic_launcher);
                aler.setTitle("确认结果");
                aler.setMessage("确认删除该收藏吗？");
                aler.setPositiveButton("确定", dia_listener);
                aler.setNegativeButton("取消", dia_listener);
                aler.show();










            }

        }
    }


    class Holder {
        TextView text_collect_food_name, text_collect_food_sell, text_collect_food_price;
        ImageView image_collect_food_pic;
        Button btn_item_collect_btn;
    }

}
