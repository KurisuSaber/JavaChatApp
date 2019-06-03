package com.GUI.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicPanelUI;

import com.client.Config;
import net.sf.json.JSONObject;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import com.GUI.util.ImageScale;
import com.GUI.util.NewJPanel;

/**
 * @author yogurt
 * The Login Dialog, designed for login or registering initially
 * Based on JDialog
 * 用了外部美化包，在lib目录下的beautyeye_inf.jar需要配置
 */
@SuppressWarnings("serial")


public class LoginDialog extends JDialog {

    private JFrame jframe = null;
    private final NewJPanel contentPanel = new NewJPanel();
    private JTextField usernameField;//用户名
    private JTextField passwordField;//密码
    private JTextField emailField;//邮箱
    private JTextField verifacationField;//验证码
    private JTextField signupPasswordField;//密码
    private JTextField confirmPasswordField;//确认密码
    private JPasswordField textFiled;

    private static final int DIALOG_WIDTH=414;
    private static final int DIALOG_HEIGHT=340;
    private static final int DIALOG_HEIGHT_EXTEND=573;


    /**
     * 检验邮箱输入的合法性
     * @param email
     * @return
     */
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

    /**
     * Launch the application.
     */
    public static void main(String[] args) {

        try {
            LoginDialog dialog = new LoginDialog();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public LoginDialog() {

        jframe = new JFrame("Stupid QQ");
        ImageIcon image = new ImageIcon("./src/com/GUI/res/chatting.png");
        jframe.setIconImage(image.getImage());
        setAlwaysOnTop(true);
        setResizable(false);
        setBounds(400, 100, DIALOG_WIDTH,DIALOG_HEIGHT-55);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setUI(new BasicPanelUI());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);


        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //设置居中
        setLocation(com.GUI.util.WindowXY.getXY(LoginDialog.this.getSize()));

        JButton btnNewButton = new JButton("注 册");
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if(LoginDialog.this.getHeight()==DIALOG_HEIGHT_EXTEND){
                    LoginDialog.this.setSize(DIALOG_WIDTH,DIALOG_HEIGHT-55);
                }
                else{
                    LoginDialog.this.setSize(DIALOG_WIDTH,DIALOG_HEIGHT_EXTEND);
                }
            }
        });
        btnNewButton.setBounds(104, 224, 93, 23);
        btnNewButton.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
        contentPanel.add(btnNewButton);

        JButton loginButton = new JButton("登 录");
        loginButton.setBounds(217, 224, 93, 23);
        loginButton.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
        loginButton.setBackground(Color.white);
        contentPanel.add(loginButton);
        usernameField = new JTextField();
        usernameField.setBounds(153, 147, 150, 25);
        contentPanel.add(usernameField);
        usernameField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setBounds(153, 182, 150, 25);
        contentPanel.add(passwordField);
        passwordField.setColumns(10);



        JLabel emailLabel = new JLabel("邮 箱");
        emailLabel.setBounds(105, 151, 54, 15);
        contentPanel.add(emailLabel);

        JLabel passwordLabel = new JLabel("密 码");
        passwordLabel.setBounds(105, 194, 54, 15);
        contentPanel.add(passwordLabel);

        JLabel imgLabel = new JLabel("New label");
        imgLabel.setBounds(0, 0, 400, 136);
        ImageIcon icon=new ImageIcon("./src/com/GUI/res/background.jpg");
        icon=ImageScale.getImage(icon, imgLabel.getWidth(), imgLabel.getHeight());
        imgLabel.setIcon((icon));
        contentPanel.add(imgLabel);

        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(null, "Stupid QQ", TitledBorder.ABOVE_TOP, TitledBorder.TOP, null, null));
        panel.setBounds(39, 280, 336, 221);
        contentPanel.add(panel);
        panel.setLayout(null);


        JLabel emailLabel2 = new JLabel("邮 箱");
        emailLabel2.setBounds(41, 29, 55, 18);
        panel.add(emailLabel2);

        JLabel verifyLabel = new JLabel("验证码");
        verifyLabel.setBounds(41, 85, 55, 18);
        panel.add(verifyLabel);

        JLabel passwordLabel2 = new JLabel("密 码");
        passwordLabel2.setBounds(41, 115, 55, 18);
        panel.add(passwordLabel2);

        JLabel label = new JLabel("确认密码");
        label.setBounds(41, 145, 55, 18);
        panel.add(label);

        emailField = new JTextField();
        emailField.setBounds(123, 22, 150, 25);
        panel.add(emailField);
        emailField.setColumns(10);

        verifacationField = new JTextField();
        verifacationField.setBounds(123, 80, 150, 25);
        panel.add(verifacationField);
        verifacationField.setColumns(10);

        signupPasswordField = new JPasswordField();
        signupPasswordField.setBounds(123, 113, 150, 25);
        panel.add(signupPasswordField);
        signupPasswordField.setColumns(10);



        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(123, 145, 150, 25);
        panel.add(confirmPasswordField);
        confirmPasswordField.setColumns(10);

        JButton btnNewButton_2 = new JButton("发送验证码");
        btnNewButton_2.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
        btnNewButton_2.setBackground(Color.white);
        btnNewButton_2.setBounds(123, 52, 83, 23);
        panel.add(btnNewButton_2);

        JButton btnNewButton_3 = new JButton("取 消");
        btnNewButton_3.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
        btnNewButton_3.setBackground(Color.white);
        btnNewButton_3.setBounds(51, 182, 83, 27);
        panel.add(btnNewButton_3);

        btnNewButton_3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if(LoginDialog.this.getHeight()==DIALOG_HEIGHT_EXTEND){
                    LoginDialog.this.setSize(DIALOG_WIDTH,DIALOG_HEIGHT-55);
                }
                else{
                    LoginDialog.this.setSize(DIALOG_WIDTH,DIALOG_HEIGHT_EXTEND);
                }
            }
        });



        JButton btnNewButton_4 = new JButton("确 认");
        btnNewButton_4.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
        btnNewButton_4.setBackground(Color.white);
        btnNewButton_4.setBounds(190, 182, 83, 27);
        panel.add(btnNewButton_4);

        btnNewButton_4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (emailField.getText().trim().equals("") || !checkEmail(emailField.getText().trim())) {
                    JOptionPane.showMessageDialog(LoginDialog.this, "邮箱格式不合法!");
                    return;
                }
                if (signupPasswordField.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(LoginDialog.this, "密码不能为空!");
                    return;
                }
                if (confirmPasswordField.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(LoginDialog.this, "确认密码不能为空!");
                    return;
                }
                if (verifacationField.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(LoginDialog.this, "验证码不能为空!");
                    return;
                }
                if (!signupPasswordField.getText().trim().equals(confirmPasswordField.getText())) {
                    JOptionPane.showMessageDialog(LoginDialog.this, "两次密码不相等!");
                    return;
                }
                try {
                    Socket socket = new Socket(Config.IP, Config.SIGNUP_PORT);
                    System.out.println(socket.getRemoteSocketAddress());
                    InputStream input = socket.getInputStream();
                    OutputStream output = socket.getOutputStream();

                    String email = emailField.getText();
                    String password = passwordField.getText();


                    output.write(("{\"type\":\"signup\",\"username\":\"" + email + "\",\"password\":\""
                            + password + "\"}").getBytes());
                    output.flush();

                    byte[] bytes = new byte[1024];
                    int len = input.read(bytes);
                    System.out.println(len);
                    String str = new String(bytes, 0, len);
                    JSONObject json = JSONObject.fromObject(str);
                    System.out.println(json);
                    if (json.getInt("state") == 0) {
                        JOptionPane.showMessageDialog(LoginDialog.this, "注册成功!");
                    } else if (json.getInt("state") == 1) {
                        JOptionPane.showMessageDialog(LoginDialog.this, "邮箱已被注册!");
                    } else if (json.getInt("state") == 3) {
                        System.out.println("未知错误，请稍后注册");
                    }

                    input.close();
                    output.close();
                    socket.close();
                } catch (Exception e_){
                    e_.printStackTrace();
                }
            }
        });



    }
}
