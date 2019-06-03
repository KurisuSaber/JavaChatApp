package com.database;
import com.exception.*;
import com.mysql.cj.xdevapi.SqlDataResult;

import java.sql.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserLogin {
    /**
     *
     * @param id 登录方式
     * @param password 登录密码
     * @param sql sql语句
     * @return
     * @throws UserNotFoundException
     * 用户未查找到
     * @throws PasswordErrorException
     * 密码输入错误
     * @throws SQLException
     * 数据库连接错误
     * @throws StateException
     * 用户状态错误 即已登录不能重复登录
     * @throws ClassNotFoundException
     * 数据库Driver加载失败
     */
    public String login(String id,String password,String sql)
            throws UserNotFoundException, PasswordErrorException, SQLException, StateException, ClassNotFoundException {
        Connection connection = null;
        try{
            connection = DBManager.getConnecttion();
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1,id);
            ResultSet resultSet = pst.executeQuery();
            if(resultSet.next()) {
                if(resultSet.getString("state").equals("offline")){
                    if(resultSet.getString("password").equals(password)){
                        PreparedStatement updatepst = connection.prepareStatement("update userinfo set state = 'online' where uid = "+resultSet.getString(1));
                        updatepst.execute();
                        return resultSet.getString(1);
                    }else{
                        throw new PasswordErrorException();
                    }
                }else{
                    throw new StateException();
                }
            }else{
                throw new UserNotFoundException();
            }
        } catch(SQLException e){
            throw e;
        } catch (ClassNotFoundException e) {
            throw e;
        } finally {
            connection.close();
        }
    }

    /**
     *
     * @param email 邮箱的方式登录
     * @param password 密码
     * @return
     * @throws UserNotFoundException
     * 用户未查找到
     * @throws PasswordErrorException
     * 密码输入错误
     * @throws SQLException
     * 数据库连接错误
     * @throws StateException
     * 用户状态错误 即已登录不能重复登录
     * @throws ClassNotFoundException
     * 数据库Driver加载失败
     */
    public String loginFromEmail(String email,String password) throws UserNotFoundException, PasswordErrorException, StateException, SQLException, ClassNotFoundException {
        return login(email,password,"select * from userinfo where email = ?");
    }

    /**
     *
     * @param phoneNumber 电话的方式登录
     * @param password 密码
     * @return
     * @throws UserNotFoundException
     * 用户未查找到
     * @throws PasswordErrorException
     * 密码输入错误
     * @throws SQLException
     * 数据库连接错误
     * @throws StateException
     * 用户状态错误 即已登录不能重复登录
     * @throws ClassNotFoundException
     * 数据库Driver加载失败
     */
    public String loginFromphoneNumber(String phoneNumber,String password) throws UserNotFoundException, PasswordErrorException, StateException, SQLException, ClassNotFoundException {
        return login(phoneNumber,password,"select * from userinfo where phonenumber = ?");
    }

    /**
     *
     * @param uid 用户的id
     * @return  返回好友列表
     * @throws SQLException
     * 数据库链接错误
     * @throws ClassNotFoundException
     * 数据库Driver加载失败
     */
    public ArrayList<User> getFriendsList(String uid) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        ArrayList<User> friends = new ArrayList<User>();
        try{
            connection = DBManager.getConnecttion();
            PreparedStatement pst = connection.prepareStatement("select * " +
                    "from friends f inner join userinfo u on u.uid=f.fid and f.uid=? ");
            pst.setString(1, uid);
            ResultSet resultSet = pst.executeQuery();
            while(resultSet.next()){
                User user = new User();
                user.setUid(resultSet.getInt("uid"));
                user.setNickname(resultSet.getString("nickname"));
                user.setImg(resultSet.getString("img"));
                user.setWhatsup(resultSet.getString("whatsup"));
                friends.add(user);
            }
            return friends;
        }catch (SQLException e){
            throw e;
        }finally {
            connection.close();
        }
    }

    /**
     *
     * @param uid 用户的id
     * @return 返回指定id的用户的信息
     * @throws SQLException
     * 数据库连接错误
     * @throws ClassNotFoundException
     * 数据库Driver加载失败
     */
    public User getUserInfo(String uid) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        User user = new User();
        try{
            connection = DBManager.getConnecttion();
            PreparedStatement pst = connection.prepareStatement("select * from userinfo where uid=?");
            pst.setString(1,uid);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                user.setUid(resultSet.getInt("uid"));
                user.setPhonenumber(resultSet.getString("phonenumber"));
                user.setEmail(resultSet.getString("email"));
                user.setNickname(resultSet.getString("nickname"));
                user.setWhatsup(resultSet.getString("whatsup"));
                user.setImg(resultSet.getString("img"));
                user.setRemarks(resultSet.getString("remarks"));
                user.setBirthday(resultSet.getDate("birthday").toString());
                user.setSex(resultSet.getString("sex"));
            }
            return user;
        }catch (SQLException e){
            throw e;
        }finally {
            connection.close();
        }
    }

    public static boolean checkEmail(String email){
        String mailRegex,mailName,mailDomain;
        mailName="^[0-9a-z]+\\w*";
        mailDomain="([0-9a-z]+\\.)+[0-9a-z]+$";
        mailRegex=mailName+"@"+mailDomain;
        Pattern pattern= Pattern.compile(mailRegex);
        Matcher matcher=pattern.matcher(email);
        if(matcher.matches()){
            return true;
        }else {
            return false;
        }
    }

    public static boolean checkphone(String phonenumber){
        String phoneRegex="^[0-9]*$";
        Pattern pattern= Pattern.compile(phoneRegex);
        Matcher matcher=pattern.matcher(phonenumber);
        if(matcher.matches() && phonenumber.length()==11){
            return true;
        }else {
            return false;
        }
    }

    public void signin(String username,String password) throws SQLException, ClassNotFoundException, UserNameUsedException, InputInvalidexception {
        Connection connection = null;
        try{
            connection = DBManager.getConnecttion();
            PreparedStatement pst = connection.prepareStatement("select * from userinfo where phonenumber = ? or email = ?");
            pst.setString(1,username);
            pst.setString(2,username);
            ResultSet resultSet = pst.executeQuery();
            if(resultSet.next()){
                throw new UserNameUsedException();
            }
            if(checkEmail(username)){
                pst = connection.prepareStatement("insert into userinfo (email,password,createtime) values (?,?,now())");
            } else if(username.length() == 11){
                pst = connection.prepareStatement("insert into userinfo (phonenumber,password,createtime) values (?,?,now())");
            }   else {
                throw new InputInvalidexception();
            }
            pst.setString(1,username);
            pst.setString(2,password);
            if(pst.executeUpdate() <= 0){
                throw new SQLException();
            }
        } catch (SQLException e) {
           throw e;
        }
    }


    public static void main(String[] args){
        System.out.println(UserLogin.checkEmail("thy001@buaa.edu.cn"));
        try {
            new UserLogin().loginFromphoneNumber("15200000002","admin");
            System.out.println("成功");
        } catch (UserNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (PasswordErrorException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (StateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
