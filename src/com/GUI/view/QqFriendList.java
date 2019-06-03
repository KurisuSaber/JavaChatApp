package com.GUI.view;


import com.GUI.util.LoginRoundButton;
import com.GUI.util.NewJPanel;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;




import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.net.MalformedURLException;

import javax.swing.*;

/**
 * @author yogurt
 * @The penal to show FriendList
 * Based on JFrame
 */
public class QqFriendList {


        public static void main(String[] args) {
            QqView view = new QqView();
            view.startQqFriendList();
            view.actionPerformed(null);

           /* EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencyAppleLike;
                        org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });*/
        }
    }


/**
 * @author yogurt
 */
    class QqView extends JFrame implements ActionListener {

        JButton btn1,btn2,btn3,btn4,btn5,btn6,btnCha,btntom1,btntom2,btntom3,btntom4;
        JPanel jpBotm,jtabP1,jtabP2,jtabP3,jpMidN,jpBotmC;
        JLabel jpTop,jlbTop,jpTopl;
        NewJPanel jpMid;
        JTextField jqianM,jchaZhao;
        JTabbedPane jtabP;
        JComboBox jcb;
        File file1 = new File("music//可米小子-青春纪念册.wma");
        File file2 = new File("music//gggg.wav");
        AudioClip clip;

        public void startQqFriendList(){
            ImageIcon image = new ImageIcon(QqFriendList.class.getResource("/res/chatting_29.923566878981px_1227628_easyicon.net.png"));
            this.setIconImage(image.getImage());
        }
        public QqView() {

            super("A Stupid QQ");

            //窗口头部设置
            this.setLayout(new BorderLayout());
           // ImageIcon bounds = new ImageIcon(QqFriendList.class.getResource("src/com/GUI/res/42.png"));

            jpTop = new JLabel();
            jpTop.setIcon(new ImageIcon("./src/com/GUI/res/background.jpg"));
            jpTop.setBounds(0, 0, 300, 60);
            this.add(jpTop,BorderLayout.NORTH);


            //增加头像
            btn1 = new JButton();

            ImageIcon icon = new ImageIcon("./src/com/GUI/res/headProfile.jpg");
            btn1.setIcon(icon);
            btn1.setBounds(15, 20, icon.getIconWidth(), icon.getIconHeight());
            jpTop.add(btn1);

            //增加昵称面板
            jpTopl = new JLabel("Cherish");
            jpTopl.setFont(new Font("楷体_GB2312", 10, 14));
            jpTopl.setForeground(Color.black);
            jpTopl.setLocation(152, 20);
            jpTopl.setSize(80, 20);
            jpTop.add(jpTopl);

            //增加状态栏
            jcb = new JComboBox();
            jcb.addItem("在线");
            jcb.addItem("离开");
            jcb.addItem("忙碌");
            jcb.addItem("下线");
            jcb.setLocation(30, 10);
            jcb.setBounds(new Rectangle(90, 25, 55, 20));
            jpTop.add(jcb);

            //增加签名
            jqianM = new JTextField("蜜桃星人");
            jpTop.add(jqianM);
            jqianM.setLocation(90, 50);
            jqianM.setSize(190,20);

            //增加空间邮箱等按钮
            btn2 = new JButton(new ImageIcon("/res/headProfile.png"));
            jpTop.add(btn2);
            btn2.setLocation(90, 80);
            btn2.setSize(25, 16);

            btn3 = new JButton(new ImageIcon("imags//weibo.jpg"));
            jpTop.add(btn3);
            btn3.setLocation(115, 80);
            btn3.setSize(25, 16);

            btn4 = new JButton(new ImageIcon("imags//youxiang.jpg"));
            jpTop.add(btn4);
            btn4.setLocation(140, 80);
            btn4.setSize(25, 16);

            btn5 = new JButton(new ImageIcon("imags//278.png"));
            jpTop.add(btn5);
            btn5.setLocation(165, 80);
            btn5.setSize(25, 16);

            btn6 = new JButton(new ImageIcon("imags//227.png"));
            jpTop.add(btn6);
            btn6.setLocation(190, 80);
            btn6.setSize(25, 16);

            //好友查询窗口
            jchaZhao = new JTextField();
            jchaZhao.setSize(120, 30);
            jchaZhao.setLocation(0, 0);
            btnCha = new LoginRoundButton("查找");
            btnCha.setSize(23, 24);
            //设置窗口菜单
            jtabP = new JTabbedPane();
            jtabP1 = new JPanel();
            jtabP2 = new JPanel();
            jtabP3 = new JPanel();

            jtabP1.setName("好友列表");
            jtabP2.setName("Q群列表");
            jtabP3.setName("最近联系");

            //将中间面板添加到frame的中部
            jpMid = new NewJPanel();
            this.add(jpMid,BorderLayout.CENTER);

            //中部面板重新布局成borderlayout
            jpMid.setLayout(new BorderLayout());
            jpMid.setSize(300, 300);
            //定义中部面板的北部面板用于添加查找框和按钮 依次往菜单中添加组件
            jpMidN = new JPanel();
            jpMid.add(jpMidN, BorderLayout.NORTH);

            //中部面板的北部面板再布局成borlayout
            jpMidN.setLayout(new BorderLayout());
            //将组件添加进去
            jpMidN.add(jchaZhao,BorderLayout.CENTER);
            jpMidN.add(btnCha,BorderLayout.EAST);

            //中部面板将菜单增加到中部布局的中部
            jpMid.add(jtabP,BorderLayout.CENTER);
            jtabP.add(jtabP1);
            jtabP.add(jtabP2);
            jtabP.add(jtabP3);


            //底部设计
            jpBotm = new JPanel();
            jpBotm.setSize(300, 100);
            this.add(jpBotm, BorderLayout.SOUTH);
            jpBotm.setLayout(new BorderLayout());

            //系统设置
            btntom1 = new LoginRoundButton("系统设置");
            btntom1.setSize(24, 25);
            jpBotm.add(btntom1,BorderLayout.WEST);


            //添加娱乐项目
            btntom4 = new LoginRoundButton("退出账号");
            btntom4.setSize(24, 20);
            jpBotm.add(btntom4,BorderLayout.EAST);


            //设置窗口图标
            Toolkit kit = Toolkit.getDefaultToolkit();
            Image img = kit.getImage("./src/com/GUI/res/chatting.png");
            this.setIconImage(img);

            //主窗口设置
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setSize(300, 700);
            this.setLocation(400, 20);
            this.setVisible(true);
            this.setResizable(false);
            //this.validate();
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            //个人资料
            btn1.addMouseListener(new MouseListener() {

                @Override
                public void mouseReleased(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mousePressed(MouseEvent e) {
                    JOptionPane.showMessageDialog(btn1, "昵称：Cherish，个性签名：蜜桃星人");

                }

                @Override
                public void mouseExited(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    // TODO Auto-generated method stub

                }
            });

            //查找按钮的事件
            btnCha.addMouseListener(new MouseListener() {

                @Override
                public void mouseReleased(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mousePressed(MouseEvent e) {
                    try {
                        clip = Applet.newAudioClip(file2.toURL());
                    } catch (MalformedURLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    clip.play();

                    JOptionPane.showMessageDialog(btnCha, "您查找的好友不在您的列表中！");

                }

                @Override
                public void mouseExited(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    // TODO Auto-generated method stub

                }
            });

            //音乐播放事件
            btntom2.addMouseListener(new MouseListener() {

                @Override
                public void mouseReleased(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mousePressed(MouseEvent e) {

                    try {
                        clip = Applet.newAudioClip(file1.toURL());
                    } catch (MalformedURLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    clip.play();

                }

                @Override
                public void mouseExited(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    JOptionPane.showInputDialog("请输入想听的音乐");

                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    // TODO Auto-generated method stub

                }
            });

            btntom4.addMouseListener(new MouseListener() {

                @Override
                public void mouseReleased(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mousePressed(MouseEvent e) {
                    JOptionPane.showInputDialog(btntom4, "输入对方qq号");

                }

                @Override
                public void mouseExited(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    // TODO Auto-generated method stub

                }
            });

        }

    }


