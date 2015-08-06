package com.neusoft.sheng.event_food_adapter;


import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.neusoft.sheng.event_food.R;
import com.neusoft.sheng.event_food_util.BaseApplicationInterface;
import com.neusoft.sheng.event_food_util.Shop_info;

import java.io.InputStream;
import java.util.ArrayList;

public class Shop_Listview_adapter extends BaseAdapter {


    private LayoutInflater inflater;
    private Context context;

    ArrayList<Shop_info> shop_infos;

AssetManager assetManager;



    public Shop_Listview_adapter(Context context,ArrayList<Shop_info> shop_infos)
    {
        this.context=context;
        this.shop_infos=shop_infos;
        this.inflater = LayoutInflater.from(context);
    }



    @Override
    public int getCount() {
        return shop_infos.size();
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




        view = inflater.inflate(R.layout.shop_list_item, null);
        Holder holder=new Holder();
        holder.iamge_shop=(ImageView)view.findViewById(R.id.shop_head);
        holder.text_shop_name=(TextView)view.findViewById(R.id.text_shop_name);
        holder.text_shop_value=(TextView)view.findViewById(R.id.text_shop_value);
        holder.text_shop_ad=(TextView)view.findViewById(R.id.text_shop_ad);

            holder.text_shop_name.setText(shop_infos.get(i).getShop_name());

            holder.text_shop_value.setText(shop_infos.get(i).getShop_valueation());
            holder.text_shop_ad.setText(shop_infos.get(i).getShop_ad());


            assetManager=context.getAssets();
            try {
                InputStream in=assetManager.open("ic_shop_"+shop_infos.get(i).getShop_head());
                Bitmap bmp=BitmapFactory.decodeStream(in);
                holder.iamge_shop.setImageBitmap(bmp);
                in.close();
            } catch (Exception e) {
                // TODO: handle exception
            }







        return view;
    }


    class Holder {
        TextView text_shop_name, text_shop_value, text_shop_ad;
        ImageView iamge_shop;
    }

}
