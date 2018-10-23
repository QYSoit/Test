package com.example.lenovo.myapplication12;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;


public class UDPClient implements Runnable {
    private final static int udpPort = 8080;
    private final static String hostIp = "112.74.168.224";
    private static DatagramSocket socket = null;
    private static DatagramPacket packetSend, packetRcv;
    private boolean udpLife = true; //udp生命线程
    private byte[] msgRcv = new byte[1024]; //接收消息

    UDPClient() {
        super();
    }

    //返回udp生命线程因子是否存活
    private boolean isUdpLife() {
        return udpLife;

    }

    //更改UDP生命线程因子
    public void setUdpLife(boolean b) {
        udpLife = b;
    }

    //send
    public String s(String msgSend) {
        InetAddress hostAddress = null;

        try {
            hostAddress = InetAddress.getByName(hostIp);
        } catch (UnknownHostException e) {
           // Log.i("udpClient", "未找到服务器");
          //  e.printStackTrace();
        }

        DatagramPacket packetSend = new DatagramPacket(msgSend.getBytes(), msgSend.getBytes().length, hostAddress, udpPort);

        try {
            socket.send(packetSend);
        } catch (IOException e) {
            //e.printStackTrace();
            //Log.i("udpClient", "发送失败");
        }
        return msgSend;
    }

    //a发送消息
    public void send(String msgSend) {
        InetAddress hostAddress = null;

        try {
            hostAddress = InetAddress.getByName(hostIp);
        } catch (UnknownHostException e) {
          //  Log.i("udpClient", "未找到服务器");
        //    e.printStackTrace();
        }

        DatagramPacket packetSend = new DatagramPacket(msgSend.getBytes(), msgSend.getBytes().length, hostAddress, udpPort);

        try {
            socket.send(packetSend);
        } catch (IOException e) {
         //   e.printStackTrace();
          //  Log.i("udpClient", "发送失败");
        }

    }

    //b发消息
    public void b(String msgSend) {
        InetAddress hostAddress = null;

        try {
            hostAddress = InetAddress.getByName(hostIp);
        } catch (UnknownHostException e) {
          //  Log.i("udpClient", "未找到服务器");
          //  e.printStackTrace();
        }

        DatagramPacket  packetSend = new DatagramPacket(msgSend.getBytes(), msgSend.getBytes().length, hostAddress, udpPort);

        try {
            socket.send(packetSend);
        } catch (IOException e) {
           // e.printStackTrace();
           // Log.i("udpClient", "发送失败");
        }
        //   socket.close();
    }

    //c发消息
    public void c(String msgSend) {
        InetAddress hostAddress = null;

        try {
            hostAddress = InetAddress.getByName(hostIp);
        } catch (UnknownHostException e) {
          // Log.i("udpClient", "未找到服务器");
          //  e.printStackTrace();
        }

        DatagramPacket  packetSend = new DatagramPacket(msgSend.getBytes(), msgSend.getBytes().length, hostAddress, udpPort);

        try {
            socket.send(packetSend);
        } catch (IOException e) {
           // e.printStackTrace();
           // Log.i("udpClient", "发送失败");
        }
        //   socket.close();
    }

    //d发送消息
    public void d(String msgSend) {
        InetAddress hostAddress = null;

        try {
            hostAddress = InetAddress.getByName(hostIp);
        } catch (UnknownHostException e) {
          //  Log.i("udpClient", "未找到服务器");
          //  e.printStackTrace();
        }

        DatagramPacket  packetSend = new DatagramPacket(msgSend.getBytes(), msgSend.getBytes().length, hostAddress, udpPort);

        try {
            socket.send(packetSend);
        } catch (IOException e) {
           // e.printStackTrace();
           // Log.i("udpClient", "发送失败");
        }
    }

    @SuppressLint("Assert")
    @Override
    public void run() {
        try {
            socket = new DatagramSocket();
            socket.setSoTimeout(3000);//设置超时为3s
        } catch (SocketException e) {
            Log.i("udpClient","建立接收数据报失败");
            e.printStackTrace();
        }
        DatagramPacket packetRcv = new DatagramPacket(msgRcv,msgRcv.length);
        while (isUdpLife()) {
            try {
                Log.i("udpClient", "UDP监听");
                socket.receive(packetRcv);
                String RcvMsg = new String(packetRcv.getData(), packetRcv.getOffset(), packetRcv.getLength());
                //将收到的消息发给主界面
                Intent RcvIntent = new Intent();
                RcvIntent.setAction("udpRcvMsg");
                RcvIntent.putExtra("udpRcvMsg", RcvMsg);
                MainActivity.context.sendBroadcast(RcvIntent);
                Log.i("Rcv", RcvMsg);
                int length = packetRcv.getLength();
                int j;
                int len = 0;
                String key = "///";
              //  int i = RcvMsg.indexOf(key);
                for ( int i = RcvMsg.indexOf(key); i < length; i = i + 3, len++) {
                    j = i;
                    if (length - i != 0) {
                        i = RcvMsg.indexOf(key, i);
                        if (i - j > 0 && len <= 4) {
                            String Ru = RcvMsg.substring(j, i - j);
                            if (Ru.contains("//ena") && len == 1) {
                                String a = Ru.substring(0, Ru.length() - 5);
                                //btn_a.setText = Color.MediumAquamarine;
                                Handler handler = null;
                                assert false;
                                Message msg = handler.obtainMessage();
                                msg.what=11;
                               msg.obj = a;
                                //sendMessage()方法，无论是在主线程当中发送，还是在Worker Thread当中发送都是可以的
                                handler.sendMessage(msg);
                            } else if (Ru.contains("//dis") && len == 1) {
                                String aa = Ru.substring(0, Ru.length() - 5);
                                Handler handler = null;
                                assert false;
                                Message msg = handler.obtainMessage();
                                msg.what=12;
                                msg.obj = aa;
                                handler.sendMessage(msg);
                            }
                            if (Ru.contains("//ena") && len == 2) {
                                //  b.setText = Ru.Remove(Ru.Length - 5, 5);
                                String b = Ru.substring(0, Ru.length() - 5);
                                Handler handler = null;
                                assert false;
                                Message msg = handler.obtainMessage();
                                //handler.sendMessage(msg);
                                msg.what=13;
                                msg.obj = b;
                                //sendMessage()方法，无论是在主线程当中发送，还是在Worker Thread当中发送都是可以的
                                handler.sendMessage(msg);

                            } else if (Ru.contains("//dis") && len == 2) {
                                String bb = Ru.substring(0, Ru.length() - 5);
                                Handler handler = null;
                                assert false;
                                Message msg = handler.obtainMessage();
                                msg.what=14;
                                msg.obj = bb;
                                handler.sendMessage(msg);
                            }
                            if (Ru.contains("//ena") && len == 3) {
                                String c = Ru.substring(0, Ru.length() - 5);
                                Handler handler = null;
                                assert false;
                                Message msg = handler.obtainMessage();
                                msg.what=15;
                                msg.obj = c;
                                handler.sendMessage(msg);

                            } else if (Ru.contains("//dis") && len == 3) {

                                String cc = Ru.substring(0, Ru.length() - 5);
                                Handler handler = null;
                                assert false;
                                Message msg = handler.obtainMessage();
                                msg.what=16;
                                msg.obj = cc;
                                handler.sendMessage(msg);
                            }
                            if (Ru.contains("//ena") && len == 4) {
                                String d = Ru.substring(0, Ru.length() - 5);
                                Handler handler = null;
                                assert false;
                                Message msg = handler.obtainMessage();
                                msg.what=17;
                                msg.obj = d;
                                handler.sendMessage(msg);

                            } else if (Ru.contains("//dis") && len == 4) {
                                String dd = Ru.substring(0, Ru.length() - 5);
                                Handler handler = null;
                                assert false;
                                Message msg = handler.obtainMessage();
                                msg.what=18;
                                msg.obj = dd;
                                handler.sendMessage(msg);
                            }
                        }
                    }
                }
                Log.i("Rcv", RcvMsg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Log.i("udpClient", "UDP监听关闭");
        socket.close();
    }
}
