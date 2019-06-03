package com.server;

import com.database.UserLogin;
import com.exception.InputInvalidexception;
import com.exception.UserNameUsedException;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SignUpServer implements Runnable{
    public Socket socket;

    public SignUpServer(Socket socket) {
        this.socket = socket;
    }

    //验证码
    private static HashMap<String, String> checkCode = new HashMap<String, String>();

    @Override
    public void run(){
        InputStream input = null;
        OutputStream output = null;
        try {
            input = socket.getInputStream();
            output = socket.getOutputStream();

            //等待客户端的请求
            byte[] bytes = new byte[1024];
            int len = input.read(bytes);
            String str = new String(bytes,0,len);
            JSONObject jsonObject = JSONObject.fromObject(str);
            String type = jsonObject.getString("type");

            //注册请求
            if(type.equals("signup")){
                String username = jsonObject.getString("username");
                String password = jsonObject.getString("password");

                try {
                    new UserLogin().signin(username,password);
                } catch (InputInvalidexception inputInvalidexception) {
                    output.write("{\"state\":3,\"msg\":\"未知错误\"}".getBytes());
                    output.flush();
                } catch (SQLException e) {
                    output.write("{\"state\":3,\"msg\":\"未知错误\"}".getBytes());
                    output.flush();
                } catch (UserNameUsedException e) {
                    output.write("{\"state\":1,\"msg\":\"该邮箱或手机号已注册过!\"}".getBytes());
                    output.flush();;
                } catch (ClassNotFoundException e) {
                    output.write("{\"state\":3,\"msg\":\"未知错误\"}".getBytes());
                    output.flush();
                }
                output.write("{\"state\":0,\"msg\":\"注册成功!\"}".getBytes());
                output.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                input.close();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void openServer() throws IOException {
        ExecutorService service = Executors.newFixedThreadPool(1000);
        ServerSocket server = new ServerSocket(4002);
        while (true) {
            Socket socket = server.accept();
            service.execute(new SignUpServer(socket));
        }
    }
}
