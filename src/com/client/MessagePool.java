package com.client;

import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

//本类的目的是把客户端接收到的所有服务器传来的数据存储起来
public class MessagePool {

    //使用单例模式
    private MessagePool() {
    }

    private static MessagePool messagePool = new MessagePool();

    public static MessagePool getMessagePool(){
        return messagePool;
    }

    private static HashMap<String, ArrayList<Message>> messages = new HashMap();

    /**
     *
     * @param json 与服务器交互的json信息
     */
    public void addMessage(String json){
        JSONObject jsonObject = JSONObject.fromObject(json);
        String fromUID = jsonObject.getString("fromUID");
        String toUID = jsonObject.getString("toUID");
        String message = jsonObject.getString("message");
        String type = jsonObject.getString("type");
        String code = jsonObject.getString("code");

        Message messageObject = new Message();
        messageObject.setCode(code);
        messageObject.setCode(message);
        messageObject.setFromUID(fromUID);
        messageObject.setToUID(toUID);
        messageObject.setType(type);

        ArrayList<Message> arrayList = messages.get(fromUID);
        if(arrayList == null){
            arrayList = new ArrayList<Message>();
        }
        arrayList.add(messageObject);
        messages.put(fromUID,arrayList);

        /**
         * 加载聊天框 等GUI完成后做
         */
    }


}
