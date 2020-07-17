package com.server;

import com.database.UserLogin;
import com.exception.AddFriendsException;
import com.exception.InputInvalidexception;
import com.exception.UserNameUsedException;
import com.exception.UserNotFoundException;
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

public class FriendsServer implements Runnable {
    public Socket socket;

    public FriendsServer(Socket socket) {
        this.socket = socket;
    }


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
            System.out.println("Friends: "+str);
            JSONObject jsonObject = JSONObject.fromObject(str);
            String type = jsonObject.getString("type");

            //搜索请求
            if(type.equals("search")){
                String email = jsonObject.getString("email");
                String result = new String();
                try {
                    result = new UserLogin().searchUser(email);
                } catch (SQLException e) {
                    output.write("{\"state\":2,\"msg\":\"未知错误\"}".getBytes());
                    output.flush();
                } catch (UserNotFoundException e) {
                    output.write("{\"state\":1,\"msg\":\"未找到该用户!\"}".getBytes());
                    output.flush();;
                } catch (ClassNotFoundException e) {
                    output.write("{\"state\":3,\"msg\":\"未知错误\"}".getBytes());
                    output.flush();
                }
                output.write(result.getBytes());
                output.flush();
            } else if(type.equals("add")){
                String fromUID = jsonObject.getString("fromUID");
                String toUID = jsonObject.getString("toUID");
                try {
                    new UserLogin().addFriends(fromUID,toUID);
                } catch (SQLException e) {
                    output.write("{\"state\":2,\"msg\":\"未知错误\"}".getBytes());
                    output.flush();
                } catch (AddFriendsException e) {
                    output.write("{\"state\":1,\"msg\":\"已添加过此好友!\"}".getBytes());
                    output.flush();;
                } catch (ClassNotFoundException e) {
                    output.write("{\"state\":3,\"msg\":\"未知错误\"}".getBytes());
                    output.flush();
                }
                output.write("{\"state\":0,\"msg\":\"添加成功\"}".getBytes());
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
        ServerSocket server = new ServerSocket(9003);
        while (true) {
            Socket socket = server.accept();
            service.execute(new FriendsServer(socket));
        }
    }
}
