package com.server;

public class start{
    public static void main(String[] args){
        new Thread() {
            public void run() {
                try {
                    SignUpServer.openServer();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
        }.start();
    }
}