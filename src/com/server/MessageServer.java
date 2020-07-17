package com.server;

import net.sf.json.JSONObject;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageServer implements Runnable{
    private DatagramPacket packet;
    private static DatagramSocket datagramSocket;

    public MessageServer(DatagramPacket packet) {
        this.packet = packet;
    }

    @Override
    public void run() {
        try{
            String jsonStr = new String(packet.getData());
            JSONObject json = JSONObject.fromObject(jsonStr);
            System.out.println(json);

            if(json.getString("type").equals("reg")){//注册
                String uid = json.getString("fromUID");
                System.out.println(uid);
                OnlineUserList.getOulInstance().updateOnlineUDP(uid,packet.getAddress().getHostAddress(),packet.getPort());
                System.out.println("reg message reached");
            } else if(json.getString("type").equals("msg") || json.getString("type").equals("qr")){
                String fromUID = json.getString("fromUID");
                String toUID = json.getString("toUID");
                System.out.println(json);
                OnlineUserList.getOulInstance().updateOnlineUDP(fromUID,packet.getAddress().getHostAddress(),packet.getPort());

                User toUser = OnlineUserList.getOulInstance().getOnlineUserInfo(toUID);
                if(toUser!=null) {
                    DatagramPacket datagramPacket = new DatagramPacket(packet.getData(), packet.getLength(),
                            InetAddress.getByName(toUser.getUdpip()), toUser.getUdpport());

                    datagramSocket.send(datagramPacket);
                }
            }
        } catch (UnknownHostException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void openServer() throws SocketException {
        datagramSocket = new DatagramSocket(9001);
        ExecutorService execute = Executors.newFixedThreadPool(1000);
        while (true){
            try{
                byte[] bytes = new byte[1024*24];
                DatagramPacket datagramPacket = new DatagramPacket(bytes,bytes.length);
                datagramSocket.receive(datagramPacket);
                execute.execute(new MessageServer(datagramPacket));
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
