package com.GUI.util;

import java.awt.*;

public class WindowXY {
    public static Point getXY(int w,int h){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int height = toolkit.getScreenSize().height;
        int width = toolkit.getScreenSize().width;

        int x = (width-w)/2;
        int y = (height-h)/2;

        Point p = new Point(x,y);

        return p;
    }

    public static Point getXY(Dimension d){
        return getXY(d.width,d.height);
    }


}
