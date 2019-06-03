package com.GUI.view;

import com.GUI.util.LoginRoundButton;
import com.GUI.util.NewButton;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;


import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * @author yogurt
 * @The chat frame for chat personally
 * Based on JFrame
 */
public class PersonalCharFrame extends JFrame {
    JPanel targetInformation = new JPanel();
    JTextPane showPersonalMessagePane = new JTextPane();
    JTextPane sendPersonalMessagePane = new JTextPane();
    JTextArea targetIDText = new JTextArea();
    JLabel idLabelPersonal = new JLabel();//聊天对象的用户名
    JLabel headProfileLabel = new JLabel();//聊天对象的头像
    LoginRoundButton button2;
    LoginRoundButton button;
    JLabel sentensLabel = new JLabel();
    StyledDocument doc = null;
    JComboBox fontName = null, fontSize = null, fontStyle = null, fontColor = null, fontBackColor;
    JTextField addText = null;
    JScrollPane showPane = null, sendPane = null;
    String addStr = " ";
    private FileDialog openDialog, sendFialog;

    public static void main(String[] args) {

        PersonalCharFrame personalCharFrame = new PersonalCharFrame();
        personalCharFrame.startPersonalChat();
    }

    /**
     * The initial method.
     */
    public void startPersonalChat() {
        this.setTitle("私聊窗口");
        this.setSize(620, 500);
        this.setDefaultCloseOperation(2);
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        ImageIcon image = new ImageIcon("./src/com/GUI/res/chatting.png");
        this.setIconImage(image.getImage());


        this.setLayout(new BorderLayout());

        JLabel topLabel = new JLabel();
        ImageIcon icon = new ImageIcon("./src/com/GUI/res/blue-mountain-silhouettes_free_stock_photos_picjumbo_HNCK7375-1080x720.jpg");
        topLabel.setIcon(icon);
        topLabel.setBounds(0, 0, 150, 90);

        this.add(topLabel, BorderLayout.NORTH);

        JButton headButton = new JButton();
        ImageIcon icon1 = new ImageIcon("./src/com/GUI/res/headProfile.jpg");
        headButton.setIcon(icon1);
        headButton.setBounds(10, 10, icon1.getIconWidth(), icon1.getIconHeight() - 5);
        topLabel.add(headButton);

        JLabel nameLabel = new JLabel("Cherish");
        nameLabel.setFont(new Font("楷体_GB2312", 10, 14));
        nameLabel.setForeground(Color.black);
        nameLabel.setLocation(90, 20);
        nameLabel.setSize(80, 20);
        topLabel.add(nameLabel);

        JLabel sentenceLabel = new JLabel("蜜桃星人");
        topLabel.add(sentenceLabel);
        sentenceLabel.setLocation(90, 50);
        sentenceLabel.setSize(190, 20);


        JLabel midLabel = new JLabel();


        midLabel.setLayout(new BorderLayout());

        showPane = new JScrollPane(showPersonalMessagePane);
        showPersonalMessagePane.setEditable(false);
        showPane.setBounds(10, 100, 580, 200);

        sendPane = new JScrollPane(sendPersonalMessagePane);
        sendPane.setBounds(10, 330, 580, 80);
        midLabel.add(sendPane, BorderLayout.SOUTH);
        this.add(midLabel, BorderLayout.CENTER);

        sendPane.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    addStr = sendPersonalMessagePane.getText();

                    sendPersonalMessagePane.setText("");
                    showPersonalMessagePane.setText("" + addStr);

                }
            }

        });

        button = new LoginRoundButton("发送");
        button.setBounds(500, 420, 100, 30);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                addStr = sendPersonalMessagePane.getText();
                String dataStr = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, Locale.CHINESE).format(new Date());
                SimpleAttributeSet attrset = new SimpleAttributeSet();
                StyleConstants.setFontSize(attrset, 24);
                StyleConstants.setFontFamily(attrset, "楷体");
                StyleConstants.setBold(attrset, true);
                Document doc = sendPersonalMessagePane.getDocument();
                try {
                    doc.insertString(doc.getLength(), "追加文本", attrset);
                } catch (BadLocationException be) {
                    be.printStackTrace();
                }
                //showPersonalMessagePane.setFont(new Font("楷体", Font.BOLD, 13));
                showPersonalMessagePane.setText(showPersonalMessagePane.getText() + dataStr + "\n" + "我：" + addStr + "\n" + "\n");
                sendPersonalMessagePane.setText("");

            }
        });

        button2 = new LoginRoundButton("关闭");
        button2.setBounds(400, 420, 100, 30);
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        String labels[]={"宋体","楷体","黑色","蓝色"};
        JComboBox comboBox = new JComboBox(labels);
        comboBox.setEditable(false);
        comboBox.setBounds(new Rectangle(90, 25, 55, 20));
        comboBox.setLocation(10,305);
        ItemListener itemListener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(ItemEvent.SELECTED == e.getStateChange()){
                    String selectedItem = e.getItem().toString();
                    showPersonalMessagePane.setFont(new Font(selectedItem,Font.BOLD,13));
                }
            }
        };
        this.add(comboBox,BorderLayout.SOUTH);

        /*JButton emptyButton = new NewButton("C:\\Users\\yogurt\\.ssh\\QQ\\GUI module\\src\\res\\A_16px_1168287_easyicon.net.png", "C:\\Users\\yogurt\\.ssh\\QQ\\GUI module\\src\\res\\A_16px_1168287_easyicon.net.png", "C:\\Users\\yogurt\\.ssh\\QQ\\GUI module\\src\\res\\A_16px_1168287_easyicon.net.png");
        emptyButton.setBounds(10, 305, 20, 20);
        this.add(emptyButton, BorderLayout.SOUTH);
        emptyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            comboBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });
            }
        });*/


        NewButton fileButton = new NewButton("./src/com/GUI/res/A_32.png", "./src/com/GUI/res/A_32.png", "./src/com/GUI/res/A_32.png");
        fileButton.setBounds(70, 305, 20, 20);
        this.add(fileButton, BorderLayout.SOUTH);
        fileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openDialog = new FileDialog(new PersonalCharFrame(), "打开", FileDialog.LOAD);
                openDialog.setVisible(true);
                String dirPath = openDialog.getDirectory();
                String fileName = openDialog.getFile();

                if (dirPath == null || fileName == null) {
                    return;
                }

                File file = new File(dirPath, fileName);
                JTextArea textArea = new JTextArea();
                try {
                    BufferedReader bufr = new BufferedReader(new FileReader(file));
                    String line = null;
                    while ((line = bufr.readLine()) != null) {
                        textArea.append(line + "\r\n");
                    }
                    bufr.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        });

        NewButton wordButton = new NewButton("C:\\Users\\yogurt\\.ssh\\QQ\\GUI module\\src\\res\\bg.jpg", "C:\\Users\\yogurt\\.ssh\\QQ\\GUI module\\src\\res\\bg.jpg", "C:\\Users\\yogurt\\.ssh\\QQ\\GUI module\\src\\res\\bg.jpg");
        wordButton.setBounds(60, 305, 20, 20);
        this.add(wordButton, BorderLayout.SOUTH);


        this.add(idLabelPersonal);
        this.add(showPane);
        this.add(sendPane);
        this.add(button);
        this.add(button2);
        this.add(fileButton);
        this.add(wordButton);
        this.setVisible(true);
    }


}
