package com.neusoft.sheng.event_food;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.neusoft.sheng.event_food_util.Util;

import net.youmi.android.AdManager;
import net.youmi.android.offers.EarnPointsOrderList;
import net.youmi.android.offers.OffersManager;
import net.youmi.android.offers.PointsChangeNotify;
import net.youmi.android.offers.PointsEarnNotify;
import net.youmi.android.offers.PointsManager;
import net.youmi.android.onlineconfig.OnlineConfigCallBack;


public class Persion_center_Activity extends Activity implements View.OnClickListener,PointsChangeNotify,
        PointsEarnNotify {


    Button btn_persion_back, btn_mycollect, btn_exit, btn_about;
    TextView text_user_name;
    String user_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.persion_center_layout);





        //初始化广告，参数：appId, appSecret
        AdManager.getInstance(this).init("f31f9ddc229ab350", "c5b10e7257275dd8");
        OffersManager.getInstance(this).onAppLaunch();


        //开启用户统计
        AdManager.getInstance(this).setUserDataCollect(true);










        btn_persion_back = (Button) findViewById(R.id.btn_persion_back);
        btn_mycollect = (Button) findViewById(R.id.btn_mycollect);
        btn_exit = (Button) findViewById(R.id.btn_exit);
        btn_about = (Button) findViewById(R.id.btn_about);
        text_user_name = (TextView) findViewById(R.id.text_user_name);


        user_name = getIntent().getStringExtra("user_name");

        text_user_name.setText(user_name);

        btn_persion_back.setOnClickListener(this);
        btn_exit.setOnClickListener(this);
        btn_mycollect.setOnClickListener(this);
        btn_about.setOnClickListener(this);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        // （可选）注销积分监听
        // 如果在onCreate调用了PointsManager.getInstance(this).registerNotify(this)进行积分余额监听器注册，那这里必须得注销
        PointsManager.getInstance(this).unRegisterNotify(this);

        // （可选）注销积分订单赚取监听
        // 如果在onCreate调用了PointsManager.getInstance(this).registerPointsEarnNotify(this)进行积分订单赚取监听器注册，那这里必须得注销
        PointsManager.getInstance(this).unRegisterPointsEarnNotify(this);

        // 回收积分广告占用的资源
        OffersManager.getInstance(this).onAppExit();
    }

    /**
     * 积分余额发生变动时，就会回调本方法（本回调方法执行在UI线程中）
     */
    @Override
    public void onPointBalanceChange(int i) {

    }


    /**
     * 积分订单赚取时会回调本方法（本回调方法执行在UI线程中）
     */
    @Override
    public void onPointEarn(Context context, EarnPointsOrderList earnPointsOrderList) {

    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.btn_persion_back:
                //finish();


                Toast.makeText(this, "获取在线参数中...", Toast.LENGTH_LONG).show();

                // 注意：这里获取的在线参数的key为 ：isOpen，为演示的key ， 开发者需要将key替换为开发者在自己有米后台上面设置的key
                AdManager.getInstance(this).asyncGetOnlineConfig("test",
                        new OnlineConfigCallBack() {

                            /**
                             * 获取在线参数成功就会回调本方法（本回调方法执行在UI线程中）
                             */
                            @Override
                            public void onGetOnlineConfigSuccessful(String key, String value) {
                                // 获取在线参数成功
                                Toast.makeText(getApplicationContext(),
                                        String.format("在线参数获取结果：\nkey=%s, value=%s", key, value), Toast.LENGTH_LONG).show();

                                // // 开发者在这里可以判断一下获取到的value值，然后设置一个boolean值并将其保存在文件中，每次调用广告之前从文件中获取boolean值并判断一下是否可以展示广告
                                // if (key.equals("isOpen")) {
                                // if (value.equals("1")) {
                                // // 如果满足开发者自己的定义：如示例中如果key=isOpen value=1 则定义为开启广告
                                // // 则将flag（boolean值）设置为true，然后每次调用广告代码之前都判断一下flag，如果flag为true则执行展示广告的代码
                                // flag = true;
                                // // 写入文件 ...
                                // }
                                // }

                            }

                            /**
                             * 获取在线参数失败就会回调本方法（本回调方法执行在UI线程中）
                             */
                            @Override
                            public void onGetOnlineConfigFailed(String key) {
                                // 获取在线参数失败，可能原因有：键值未设置或为空、网络异常、服务器异常
                                Toast.makeText(getApplicationContext(),
                                        String.format("在线参数获取结果：\n获取在线key=%s失败!\n具体失败原因请查看Log，Log标签：YoumiSdk", key),
                                        Toast.LENGTH_LONG).show();
                            }
                        });






                break;
            case R.id.btn_exit:
                AlertDialog.Builder aler = new AlertDialog.Builder(
                        view.getContext());
                DialogInterface.OnClickListener dia_listener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case -1:
                                Util.exitLogin(user_name);
                                finish();
                                break;
                            case -2:

                                break;

                        }
                    }
                };

                aler.setIcon(R.drawable.ic_launcher);
                aler.setTitle("确认结果");
                aler.setMessage("确定要退出登录？");
                aler.setPositiveButton("确定", dia_listener);
                aler.setNegativeButton("取消", dia_listener);
                aler.show();
                break;

            case R.id.btn_mycollect:
                Intent intent = new Intent(Persion_center_Activity.this,
                        Collect_Activity.class);
                intent.putExtra("user_name", user_name);
                startActivity(intent);
                finish();
                break;



            case R.id.btn_about:

                OffersManager.getInstance(this).showOffersWall();
                break;


        }

    }
}
