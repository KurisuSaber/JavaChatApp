package com.client;

import net.sf.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.json.*;

public class TestSignIn {
    public static void main(String[] args) {
        /*
        try {
            Socket socket = new Socket(Config.IP, Config.SIGNUP_PORT);
            System.out.println(socket.getRemoteSocketAddress());
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();

            String test = "{\"type\":\"reg\",\"username\":\"" + "test1" + "\",\"password\":\""
                    + "Admin" + "\"}";
            JSONObject jb = JSONObject.fromObject(test);
            System.out.println(jb);

            output.write(("{\"type\":\"signup\",\"username\":\"" + "15200000005" + "\",\"password\":\""
                    + "Admin" + "\"}").getBytes());
            output.flush();

            byte[] bytes = new byte[1024];
            int len = input.read(bytes);
            System.out.println(len);
            String str = new String(bytes, 0, len);
            JSONObject json = JSONObject.fromObject(str);
            System.out.println(json);
            if (json.getInt("state") == 0) {

            } else if (json.getInt("state") == 1) {
                System.out.println("用户名已存在!");
            } else if (json.getInt("state") == 2) {
                System.out.println("验证码错误，请重新获得!");
            } else if (json.getInt("state") == 3) {
                System.out.println("未知错误!");
            }

            input.close();
            output.close();
            socket.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        */
    }
}
