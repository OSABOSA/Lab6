package org.example;

import javax.swing.*;
import java.awt.*;

public class Wall extends JPanel {
    private int x;
    private int y;
    private int width;
    private int height;

    private boolean isInbound;

    public Wall() {
        this(0, 0, 0, 0, false);
    }

    public Wall(int x, int y, int width, int height, boolean isInbound) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isInbound = isInbound;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isInbound() {
        return isInbound;
    }

    public void setInbound(boolean isInbound) {
        this.isInbound = isInbound;
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics g) {
        g.setColor(isInbound ? new java.awt.Color(0, 0, 0) : new java.awt.Color(255, 255, 255));
        g.fillRect(x, y, width, height);
    }
}
