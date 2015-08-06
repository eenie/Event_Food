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


public class Login_Activity extends Activity {


    Button btn_login, btn_reg, btn_login_back;
    EditText edit_name, edit_pwd;
    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_layout);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login_back = (Button) findViewById(R.id.btn_login_back);
        btn_reg = (Button) findViewById(R.id.btn_login_reg);




        btn_login_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Activity.this,
                        Reg_Activity.class);

                startActivity(intent);
                finish();
            }
        });


        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_pwd = (EditText) findViewById(R.id.edit_pwd);

        handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                // TODO 自动生成的方法存根

                if (msg.what == 1) {

                    Util.Login(edit_name.getText().toString(), edit_pwd.getText().toString());
                    finish();

                } else if (msg.what == 2) {
                    Toast.makeText(getApplicationContext(), "登录失败！请检查账号密码", Toast.LENGTH_SHORT).show();
                }

                super.handleMessage(msg);
            }

            @Override
            public void dispatchMessage(Message msg) {
                // TODO 自动生成的方法存根
                super.dispatchMessage(msg);
            }

            @Override
            public String getMessageName(Message message) {
                // TODO 自动生成的方法存根
                return super.getMessageName(message);
            }

            @Override
            public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
                // TODO 自动生成的方法存根
                return super.sendMessageAtTime(msg, uptimeMillis);
            }

            @Override
            public String toString() {
                // TODO 自动生成的方法存根
                return super.toString();
            }

        };


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new Thread() {
                    @Override
                    public void run() {

                        Getsrcoce getsrcoce = new Getsrcoce();
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        NameValuePair paramUser = new BasicNameValuePair("phone", edit_name.getText().toString());
                        NameValuePair paramPassword = new BasicNameValuePair("password", edit_pwd.getText().toString());
                        params.add(paramUser);
                        params.add(paramPassword);
                        ParseData parseData = new ParseData();
                        String res = parseData.parseReg(getsrcoce.sroceByPost(BaseApplicationInterface.mLoginUrl, params));
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
