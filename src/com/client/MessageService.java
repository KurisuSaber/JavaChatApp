package com.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class MessageService extends Thread{
    private DatagramSocket client = null;
    @Override
    public void run() {
        while(true){//持续监听消息 收到就放到消息池中
            try{
                byte[] bytes = new byte[1024 * 64];
                DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);
                client.receive(datagramPacket);
                MessagePool.getMessagePool().addMessage(
                        new String(datagramPacket.getData(),0,datagramPacket.getData().length)
                );
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public MessageService(DatagramSocket client) {
        this.client = client;
        this.start();
    }
}
