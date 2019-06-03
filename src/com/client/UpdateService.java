package com.client;

import net.sf.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class UpdateService extends Thread {
    private DatagramSocket client = null;

    public UpdateService(DatagramSocket client) {
        this.client = client;
        this.start();
    }

    //每10s向服务器请求一次
    @Override
    public void run() {
        String uid = JSONObject.fromObject(Config.user_json_data).getString("uid");
        String jsonStr = "{\"type\":\"reg\",\"fromUID\":\"" + uid + "\"}";
        byte[] bytes = jsonStr.getBytes();

        while (true){
            try {
                DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length, InetAddress.getByName(Config.IP),6003);
                client.send(datagramPacket);
                Thread.sleep(9999);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

}
