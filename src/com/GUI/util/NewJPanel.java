package com.GUI.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class NewJPanel extends JPanel {
    String url = "./src/com/GUI/res/background.jpg";
    public NewJPanel(String url){
        super();
        this.url = url;
    }
    public NewJPanel(){
        super();
    }
    @Override
    public void paintComponent(Graphics gs) {
        Graphics2D g = (Graphics2D) gs;
        super.paintComponent(g);

        InputStream in;
        try {
            in = new FileInputStream(url);
            // 画背景图片
            // Image image =
            // Toolkit.getDefaultToolkit().getImage(getClass().getResource("sk.png"));
            BufferedImage image = ImageIO.read(in);
            g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(),
                    this);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
