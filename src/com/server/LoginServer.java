package com.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.client.Config;
import com.database.UserLogin;
import com.exception.*;
import net.sf.json.JSONObject;
import net.sf.json.JSONArray;

import java.sql.*;
/**
 * 登录
 * @author Bryant Ma
 */
public class LoginServer implements Runnable{
    private Socket socket = null;
    /**
     *  main login service for server
     */
    @Override
    public void run() {
        String uid = null;
        InputStream in = null;
        OutputStream out = null;
        try {
            /**
             * waiting for client message
             */
            in = socket.getInputStream();
            out = socket.getOutputStream();
            byte[] bytes = new byte[1024];
            int len = in.read(bytes);
            /**
             * resolve
             */
            String json_str = new String(bytes, 0, len);
            JSONObject json = JSONObject.fromObject(json_str);
            System.out.println(json);
            String username = json.getString("username");
            String password = json.getString("password");
            /**
             * login logic
             */
            boolean isPhone = false;
            if (username.trim().length() == 11 && username.indexOf("@") <= -1){
                try{
                    isPhone = true;
                } catch (NumberFormatException nfe){
                    out.write("{\"state\":4,\"msg\":\"Unknown error!\"}".getBytes());
                    out.flush();
                    return;
                }
            }
            else {
                isPhone = false;
            }
            try {
                if (isPhone){
                    uid = new UserLogin().loginFromphoneNumber(username,password);
                    OnlineUserList.getOulInstance().regOnline(uid, null, username,socket);
                }
                else {
                    uid = new UserLogin().loginFromEmail(username, password);
                    OnlineUserList.getOulInstance().regOnline(uid, null, username, socket);
                }
                out.write("{\"state\":0,\"msg\":\"Successfully logged in!\"}".getBytes());
                out.flush();
                /**
                 * constantly receiving msg from client
                 */
                while (true){
                    bytes = new byte[2048];
                    len = in.read(bytes);
                    String cmd = new String(bytes, 0, len);
                    System.out.println(cmd);
                    if (cmd.equals("U0001")){ //update friend list
                        ArrayList<com.database.User> userArrayList = new UserLogin().getFriendsList(uid);
                        if(!userArrayList.isEmpty()) {
                            out.write(JSONArray.fromObject(userArrayList).toString().getBytes());
                            out.flush();
                        }else{
                            out.write("empty".getBytes());
                            out.flush();
                        }
                    }
                    else if (cmd.equals("U0002")){ //update online list
                        out.write(1);
                        out.flush();
                        len = in.read(bytes);
                        String str = new String(bytes, 0, len);
                        //System.out.println(str);//打印好友列表
                        String[] ids = str.split(",");
                        StringBuffer sb = new StringBuffer();
                        for (String string : ids){
                            if (OnlineUserList.getOulInstance().isOnline(string)){
                                sb.append(string);
                                sb.append(",");
                            }
                        }
                        if (sb.length() == 0){// no friend is online
                            out.write("No friend is online currently".getBytes());
                            out.flush();
                        } else{
                            System.out.println(sb.toString());
                            out.write(sb.toString().getBytes());
							out.flush();
                        }
                    }
                    else if (cmd.equals("U0003")){
                        com.database.User userinfo = new UserLogin().getUserInfo(uid);
                        System.out.println(userinfo);
                        out.write(JSONObject.fromObject(userinfo).toString().getBytes());
                        out.flush();
                    }
                    else if (cmd.equals("EXIT")){
                        OnlineUserList.getOulInstance().logout(uid);
                        return;
                    }
                }
            }
            catch (UserNotFoundException e) {
                out.write("{\"state\":2,\"msg\":\"User name does not exist!\"}".getBytes());
                out.flush();
                return;
            } catch (PasswordErrorException e) {
                out.write("{\"state\":1,\"msg\":\"Password does not match!\"}".getBytes());
                out.flush();
                return;
            } catch (StateException e) {
                out.write("{\"state\":3,\"msg\":\"Account is locked!\"}".getBytes());
                out.flush();
                return;
            } catch (SQLException e) {
                out.write("{\"state\":4,\"msg\":\"Unknown error!\"}".getBytes());
                out.flush();
                return;
            }
        } catch (Exception e){
        } finally {
            try {
                OnlineUserList.getOulInstance().logout(uid);
                in.close();
                out.close();
                socket.close();
            } catch (Exception ee){
                ee.printStackTrace();
            }
        }
    }
    public LoginServer(Socket socket){
        this.socket = socket;
    }

    /**
     * start server
     * @throws Exception
     */
    public static void openServer() throws Exception{
        /**thread pool*/
        ExecutorService es = Executors.newFixedThreadPool(1000);
        /**tcp port 4001 for login*/
        ServerSocket server = new ServerSocket(9000);
        /**infinite service*/
        while (true){
            Socket nSocket = server.accept();
            nSocket.setSoTimeout(10000);
            es.execute(new LoginServer(nSocket));
        }
    }

    /**
     * main method
     * @param args
     */
    public static void main(String[] args){
        try {
            openServer();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
