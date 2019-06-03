package com.client;

import net.sf.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetService implements Runnable{
    private boolean run = false;
    private Socket socket = null;
    private InputStream input = null;
    private OutputStream output = null;
    private Thread thread = null;

    //单例模式
    private NetService() {
    }

    private static NetService netService = new NetService();

    public static NetService getNetService(){
        return netService;
    }

    /**
     *
     * @return 返回尝试登录后服务器返回的信息
     * @throws UnknownHostException
     * 服务器IP错误
     * @throws IOException
     * 服务器通讯错误
     *
     */
    public JSONObject login() throws UnknownHostException, IOException{
        socket = new Socket(Config.IP,Config.LOGIN_PORT);
        input = socket.getInputStream();
        output = socket.getOutputStream();
        String json = "{\"username\":\"}"+Config.username+"\",\"password\":\""+Config.password;
        //用户名密码发送给服务器
        output.write(json.getBytes());
        output.flush();

        byte[] bytes = new byte[1024];
        int len = input.read(bytes);

        json = new String(bytes,0,len);
        JSONObject jsonObject = JSONObject.fromObject(json);

        if(jsonObject.getInt("state") == 0){//登录成功
            if(thread != null){
                if (thread.getState() == Thread.State.RUNNABLE){
                    run = false;
                    try{
                        thread.join();
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
            //获取好友列表
            output.write("U0001".getBytes());
            output.flush();
            bytes = new byte[2014*32];
            len = input.read(bytes);
            String frendList = new String(bytes,0,len);

            Config.resolveFriendInformation(frendList);

            //获取个人信息
            output.write("U0003".getBytes());
            output.flush();
            len = input.read(bytes);
            String userInfo = new String(bytes,0,len);

            Config.user_json_data = userInfo;

            Config.datagramSocket = new DatagramSocket();
            new UpdateService(Config.datagramSocket);
            new MessageService(Config.datagramSocket);

            thread = new Thread(this);
            run = true;
            thread.start();

        }

        return jsonObject;

    }

    @Override
    public void run() {
        try{
            byte[] bytes = new byte[1024*32];
            int len  =0;
            //实时更新各种数据
            while (run){
                output.write("U0002".getBytes());//更新好友的在线状态
                output.flush();
                input.read();
                output.write(Config.friendlist_json_data.getBytes());
                output.flush();
                len = input.read(bytes);
                String online = new String(bytes,0,len);
                if(!Config.friends_online.equals(online)){
                    Config.friends_online = online;
                    /**
                     * 更新UI
                     */
                }

                try{
                    Thread.sleep(2000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
        } catch (IOException e){
            run = false;
            e.printStackTrace();
        }
    }
}
