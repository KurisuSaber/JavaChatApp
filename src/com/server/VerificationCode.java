package com.server;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import org.apache.commons.mail.HtmlEmail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class VerificationCode {
    public static boolean send(String phoneNumber, String code) {

        try {
            TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", "23434458",
                    "beedcab9cf9af63d1b7a502b140d4372");
            AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
            req.setExtend("123456");
            req.setSmsType("normal");
            req.setSmsFreeSignName("凯哥学堂");
            req.setSmsParamString("{\"code\":\"" + code + "\"}");
            req.setRecNum(phoneNumber);
            req.setSmsTemplateCode("SMS_13060260");
            AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
            if (!(rsp.getBody().indexOf("\"success\":true") >= 0)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }

    }

    public static void sendEmail(String emailaddress, String code) {

        // 收件人电子邮箱
        String to = emailaddress;

        // 发件人电子邮箱
        String from = "shufenyangxiaoyuan@163.com";

        // 指定发送邮件的主机为 localhost
        String host = "localhost";

        // 获取系统属性
        Properties properties = System.getProperties();

        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", host);

        // 获取默认的 Session 对象。
        Session session = Session.getDefaultInstance(properties);

        try{
            // 创建默认的 MimeMessage 对象。
            MimeMessage message = new MimeMessage(session);

            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));

            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));

            // Set Subject: 头字段
            message.setSubject("This is the Subject Line!");

            // 发送 HTML 消息, 可以插入html标签
            message.setContent("<h1>"+code+"</h1>",
                    "text/html" );

            // 发送消息
            Transport.send(message);
            System.out.println("Sent message successfully....");
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }

    public static void main(String[] args) {
        sendEmail("724133160@qq.com","123456");
    }
}
