package com.example.lenovo.myapplication12;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Handler;
//import android.os.Looper;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static android.graphics.Color.*;


public class MainActivity extends AppCompatActivity {
    TextView txt_Recv,txt_Send;
    Button   btn_CleanRecv,btn_Send,btn_UdpConn,btn_UdpClose,btn_a,btn_b,btn_c,btn_d;
    EditText edit_Send;
    private UDPClient client = null;
    @SuppressLint("StaticFieldLeak")
    public static Context context;
    private final MyHandler myHandler = new MyHandler();
    private StringBuffer udpRcvStrBuf=new StringBuffer(),udpSendStrBuf=new StringBuffer();



    MyBtnClick myBtnClick = new MyBtnClick();
    /**
     为避免handler造成的内存泄漏
     1、使用静态的handler，对外部类不保持对象的引用
     2、但Handler需要与Activity通信，所以需要增加一个对Activity的弱引用
     */
    @SuppressLint("HandlerLeak")
    private  class MyHandler extends Handler{
        MyHandler(){
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    udpRcvStrBuf.append(msg.obj.toString());
                    txt_Recv.setText(udpRcvStrBuf.toString());
                    break;
                case 2:
                    udpSendStrBuf.append(msg.obj.toString());
                    txt_Send.setText(udpSendStrBuf.toString());
                    break;
                case 3:
                    txt_Recv.setText(udpRcvStrBuf.toString());
                    break;
                    case 11:
                       String a = (String) msg.obj;
                        btn_a.setText(a);
                        btn_a.setBackgroundColor(GREEN);
                        break;
                case 12:
                    String aa = (String) msg.obj;
                    String aa_Text=aa+"//dis///";
                    btn_a.setText(aa);
                    btn_a.setBackgroundColor(RED);
                        break;
                case 13:
                    String b = (String) msg.obj;
                    btn_b.setText(b);
                        btn_b.setBackgroundColor(GREEN);
                    break;
                case 14:
                    String bb = (String) msg.obj;
                    btn_b.setText(bb);
                        btn_b.setBackgroundColor(RED);
                    break;
                case 15:
                    String c = (String) msg.obj;
                    btn_c.setText(c);
                        btn_c.setBackgroundColor(GREEN);
                    break;
                case 16:
                    String cc = (String) msg.obj;
                    btn_c.setText(cc);
                        btn_c.setBackgroundColor(RED);
                    break;
                case 17:
                    String d = (String) msg.obj;
                    btn_d.setText(d);
                        btn_d.setBackgroundColor(GREEN);
                    break;
                case 18:
                    String dd = (String) msg.obj;
                    btn_d.setText(dd);
                        btn_d.setBackgroundColor(RED);
                    break;



            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        bindWidget();   //控件绑定
        listening();    //监听事件
        bindReceiver(broadcastReceiver);//注册broadcastReceiver接收器
        iniWidget();    //初始化控件状态
        // new CustomThread().start();//新建并启动CustomThread实例

    }

    private void bindWidget(){
        txt_Recv = findViewById(R.id.txt_Recv);
        // txt_Send = findViewById(R.id.txt_Send);
        btn_CleanRecv = findViewById(R.id.btn_CleanRecv);
        btn_Send = findViewById(R.id.btn_Send);
        btn_UdpConn = findViewById(R.id.btn_udpConn);
        btn_UdpClose = findViewById(R.id.btn_udpClose);
        edit_Send = findViewById(R.id.edit_Send);
        btn_a=findViewById(R.id.btn_a);
        btn_b=findViewById(R.id.btn_b);
        btn_c=findViewById(R.id.btn_c);
        btn_d=findViewById(R.id.btn_d);

    }

    private void listening(){
        btn_Send.setOnClickListener(myBtnClick);
        btn_UdpConn.setOnClickListener(myBtnClick);
        btn_UdpClose.setOnClickListener(myBtnClick);
        btn_CleanRecv.setOnClickListener(myBtnClick);
        btn_a.setOnClickListener(myBtnClick);
        btn_b.setOnClickListener(myBtnClick);
        btn_c.setOnClickListener(myBtnClick);
        btn_d.setOnClickListener(myBtnClick);
    }

    private void bindReceiver(BroadcastReceiver broadcastReceiver){
        IntentFilter udpRcvIntentFilter = new IntentFilter("udpRcvMsg");
        registerReceiver(broadcastReceiver,udpRcvIntentFilter);
    }

    private void iniWidget(){
        btn_Send.setEnabled(false);
    }

    class MyBtnClick implements Button.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_CleanRecv:
                    udpRcvStrBuf.delete(0,udpRcvStrBuf.length());
                    final Message message = new Message();
                    message.what = 3;
                    myHandler.sendMessage(message);
                    break;
                case R.id.btn_udpConn:
                    //建立线程池
                    ExecutorService exec = Executors.newCachedThreadPool();
                    client = new UDPClient();
                    exec.execute(client);
                    btn_UdpClose.setEnabled(true);
                    btn_UdpConn.setEnabled(false);
                    btn_Send.setEnabled(true);
                    break;
                case R.id.btn_udpClose:
                    client.setUdpLife(false);
                    btn_UdpConn.setEnabled(true);
                    btn_UdpClose.setEnabled(false);
                    btn_Send.setEnabled(false);
                    break;
                case R.id.btn_Send:
                    Thread thread0 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //Message message = new Message();
                           // message.what = 0;
                            client.s("//opn///");

                            //myHandler.sendMessage(message);
                 /*           if (!edit_Send.getText().toString().equals(""))
                            {
                                client.s(edit_Send.getText().toString());
                                s.obj = edit_Send.getText().toString();
                                myHandler.sendMessage(s);
                            }*/

                        }
                    });
                    thread0.start();
                    break;

                case R.id.btn_a:
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Message message = new Message();
                           String a = (String) btn_a.getText();
                                   String aa = a + "//ena///";
                                   client.send(aa);
                                   myHandler.sendMessage(message);
                        }
                    });
                    thread.start();
                    break;
                case R.id.btn_b:

                    Thread thread2 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Message message = new Message();
                            String b= (String) btn_b.getText();
                            client.b(b);
                            myHandler.sendMessage(message);


                        }
                    });
                    thread2.start();
                    break;
                case R.id.btn_c:

                    Thread thread3 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Message message = new Message();
                           // message.what = 3;
                            String c= (String) btn_c.getText();
                            client.c(c);
                            myHandler.sendMessage(message);


                        }
                    });
                    thread3.start();
                    break;
                case R.id.btn_d:
                    Thread thread4 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Message message = new Message();
                           // message.what = 4;
                            String d= (String) btn_d.getText();
                            client.d(d);
                            myHandler.sendMessage(message);


                        }
                    });
                    thread4.start();
                    break;}}}
               /* case R.id.btn_a:
                    String a= (String) btn_a.getText();
                    String key=":";
                    int k=a.indexOf(key);
                    final String aip=a.substring(0,k);
                    final String aport=a.substring(k);
                    Thread CustomThread= new Thread(new Runnable() {
                        public void run() {
                            Message message = new Message();
                            message.what = 3;
                            message.obj=aport;
                            message.obj = aip;//传String等
                            myHandler.sendMessage(message);
                        }
                    });
                    CustomThread.start();  //记得启动
                     break;
*/


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.hasExtra("udpRcvMsg"))  {
                Message message = new Message();
                message.obj = intent.getStringExtra("udpRcvMsg");
                message.what = 1;
                Log.i("主界面Broadcast","收到"+message.obj.toString());
                myHandler.sendMessage(message);
            }
        }
    };


}
