package com.neusoft.sheng.event_food;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.neusoft.sheng.event_food_util.BaseApplicationInterface;
import com.neusoft.sheng.event_food_util.Util;
import com.neusoft.sheng.net.Getsrcoce;
import com.neusoft.sheng.net.ParseData;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;


public class Reg_Activity extends Activity {


    Button btn_reg_back,btn_reg_reg;
    EditText edit_reg_name,edit_reg_pwd;
    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.register_layout);
        btn_reg_back=(Button)findViewById(R.id.btn_reg_back);
        btn_reg_reg=(Button)findViewById(R.id.btn_reg_reg);
        edit_reg_name=(EditText)findViewById(R.id.edit_reg_name);
        edit_reg_pwd=(EditText)findViewById(R.id.edit_reg_pwd);

        btn_reg_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {


                if (msg.what == 1) {


                    finish();

                } else if (msg.what == 2) {
                    Toast.makeText(getApplicationContext(), "注册失败！", Toast.LENGTH_SHORT).show();
                }

                super.handleMessage(msg);
            }

            @Override
            public void dispatchMessage(Message msg) {

                super.dispatchMessage(msg);
            }

            @Override
            public String getMessageName(Message message) {

                return super.getMessageName(message);
            }

            @Override
            public boolean sendMessageAtTime(Message msg, long uptimeMillis) {

                return super.sendMessageAtTime(msg, uptimeMillis);
            }

            @Override
            public String toString() {

                return super.toString();
            }

        };




        btn_reg_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new Thread() {
                    @Override
                    public void run() {

                        Getsrcoce getsrcoce = new Getsrcoce();
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        NameValuePair paramUser = new BasicNameValuePair("phone", edit_reg_name.getText().toString());
                        NameValuePair paramPassword = new BasicNameValuePair("password", edit_reg_pwd.getText().toString());
                        params.add(paramUser);
                        params.add(paramPassword);
                        ParseData parseData = new ParseData();
                        String res = parseData.parseReg(getsrcoce.sroceByPost(BaseApplicationInterface.mRegisterUrl, params));
                        System.out.println(res);
                        if (res.equals("success")) {
                            handler.sendEmptyMessage(1);

                        } else {
                            handler.sendEmptyMessage(2);
                        }


                    }
                }.start();
            }
        });





    }




}
