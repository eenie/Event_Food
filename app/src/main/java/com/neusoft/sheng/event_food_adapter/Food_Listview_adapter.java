package com.neusoft.sheng.event_food_adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.neusoft.sheng.event_food.Food_info_Activity;
import com.neusoft.sheng.event_food.R;
import com.neusoft.sheng.event_food_util.Collect;
import com.neusoft.sheng.event_food_util.Food_info;

import java.io.InputStream;
import java.util.ArrayList;

public class Food_Listview_adapter extends BaseAdapter {


    private LayoutInflater inflater;
    private Context context;
    ArrayList<Food_info> food_infos;
    private GridView food_list;

    Holder holder;

    AssetManager assetManager;


    public Food_Listview_adapter(Context context, ArrayList<Food_info> food_infos) {
        this.context = context;
        this.food_infos = food_infos;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return food_infos.size();
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


        view = inflater.inflate(R.layout.food_list_item, null);

        food_list = (GridView) view.findViewById(R.id.food_list);

        holder = new Holder();
        holder.text_food_pic = (ImageView) view.findViewById(R.id.text_food_pic);
        holder.text_food_name = (TextView) view.findViewById(R.id.text_food_name);
        holder.text_food_sell = (TextView) view.findViewById(R.id.text_food_sell);
        holder.text_food_price = (TextView) view.findViewById(R.id.text_food_price);
        holder.btn_item_collect = (Button) view.findViewById(R.id.btn_item_collect);


        holder.btn_item_collect.setBackgroundResource(R.drawable.star);
        holder.text_food_name.setText(food_infos.get(i).getFood_name());

        holder.text_food_sell.setText("月售：" + food_infos.get(i).getFood_sell());
        holder.text_food_price.setText("价格： " + food_infos.get(i).getFood_price() + "/份");


        assetManager = context.getAssets();
        try {
            InputStream in = assetManager.open("ic_shop_" + food_infos.get(i).getFood_pic());
            Bitmap bmp = BitmapFactory.decodeStream(in);
            holder.text_food_pic.setImageBitmap(bmp);
            in.close();
        } catch (Exception e) {

        }


        view.setOnClickListener(new list_listener(i));
        holder.btn_item_collect.setOnClickListener(new list_listener(i));

        return view;
    }


    class list_listener implements View.OnClickListener {

        int i;

        public list_listener(int i) {
            this.i = i;
        }

        @Override
        public void onClick(View view) {


            if (view.getId() != R.id.btn_item_collect) {


                ActivityOptionsCompat options =
                        ActivityOptionsCompat.makeScaleUpAnimation(view, //The View that the new activity is animating from
                                (int)view.getWidth()/2, (int)view.getHeight()/2, //拉伸开始的坐标
                                0, 0);//拉伸开始的区域大小，这里用（0，0）表示从无到全屏

                startNewAcitivity(options,i);



            } else {
                Collect collect = new Collect(food_infos.get(i).getFood_id(), context);
                collect.addCollect();
            }


        }
    }


    class Holder {
        TextView text_food_name, text_food_sell, text_food_price;
        ImageView text_food_pic;
        Button btn_item_collect;
    }



    private void startNewAcitivity(ActivityOptionsCompat options,int i) {

        Intent intent = new Intent(context, Food_info_Activity.class);
        intent.putExtra("food", food_infos.get(i));


        ActivityCompat.startActivity((Activity) context, intent, options.toBundle());
    }


}
