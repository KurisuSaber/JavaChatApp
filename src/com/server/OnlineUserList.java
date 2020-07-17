package com.server;


import java.net.Socket;
import java.util.HashMap;
import java.util.Set;
//import java.util.LinkedList;

/**
 * List of online user
 * @author Bryant Ma
 */
public class OnlineUserList {
    private OnlineUserList(){}
    private static OnlineUserList oulInstance = new OnlineUserList();
    /**
     * first param is uid
     */
    private HashMap<String, User> userHashMap = new HashMap<String, User>();
    /**
     *
     * @return oulInstance
     */
    public static OnlineUserList getOulInstance(){
        return oulInstance;
    }

    /**
     * reg online user to oul
     * @param uid id
     * @param socket socket
     */
    public void regOnline(String uid, String email, String phone, Socket socket){
        User user = userHashMap.get(uid);
        if (user != null){
            try {
                try{
                    user.getSocket().getOutputStream().write(4);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                user.getSocket().close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        user = new User();
        user.setEmail(email);
        user.setPhone(phone);
        user.setUid(uid);
        user.setSocket(socket);
        /**
         * logged in
         */
        userHashMap.put(uid, user);
    }

    /**
     * update client UDP info
     * @param uid id
     * @param ip udp ip
     * @param port udp port
     * @throws NullPointerException
     */
    public void updateOnlineUDP(String uid, String ip, int port) throws NullPointerException{
        User user = userHashMap.get(uid);
        user.setUdpip(ip);
        user.setUdpport(port);
    }

    /**
     * if user is online
     * @param uid id
     * @return state
     */
    public boolean isOnline(String uid){
        return userHashMap.containsKey(uid);
    }

    /**
     * get specific user info
     * @param uid id
     * @return User
     */
    public User getOnlineUserInfo(String uid){
        return userHashMap.get(uid);
    }

    /**
     * log out
     * @param uid id
     */
    public void logout(String uid){
        userHashMap.remove(uid);
    }

    /**
     * get all online users
     * @return hash map clone
     */
    public Set<String> getOnlineUserList(){
        return userHashMap.keySet();
    }
}
