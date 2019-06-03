package com.client;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.net.DatagramSocket;

public class Config {
    public static final String IP = "123.207.148.155";
    public static final int LOGIN_PORT = 6001;
    public static final int SIGNUP_PORT = 6000;
    public static String username;
    public static String password;

    // 好友信息列表 JSON
    public static String friends_json_data = "";

    // 好友信息列表
    public static String friendlist_json_data = "";

    public static String user_json_data = "";

    // 好友在线
    public static String friends_online = "";

    // UDP发送和接收
    public static DatagramSocket datagramSocket = null;

    public static void resolveFriendInformation(String data){
        Config.friends_json_data = data;
        JSONArray json = JSONArray.fromObject(data);
        StringBuffer stringBuffer = new StringBuffer();
        for(int i=0;i<json.size();i++){
            JSONObject jsonObject = (JSONObject) json.get(i);
            stringBuffer.append(jsonObject.getString("uid"));
            stringBuffer.append(",");
        }
        friendlist_json_data = stringBuffer.toString();
    }

    /**
     * UI
     */
}
