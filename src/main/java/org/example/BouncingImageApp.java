package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BouncingImageApp {
    public static void main(String[] args) {
        // Get the screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        // Load the image
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("C:\\Users\\0oski\\IdeaProjects\\Lab6\\res\\ball.jpg"));
            System.out.println(image.getWidth());
            System.out.println(image.getHeight());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Create the main frame
        JFrame frame = new JFrame("Bouncing Image App");
        frame.setSize(screenWidth, screenHeight);
        frame.setUndecorated(true);  // Remove the title bar and borders
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);  // Use null layout for absolute positioning

        // Create a list to store the bouncing image panels
        ArrayList<BouncingImagePanel> panels = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            BouncingImagePanel panel = new BouncingImagePanel(image, screenWidth, screenHeight, panels);
            panel.setBounds(0, 0, screenWidth, screenHeight);
            panel.setOpaque(false);
            frame.add(panel);
            panels.add(panel);
        }

        // Make the frame visible
        frame.setVisible(true);
    }
}

class BouncingImagePanel extends JPanel {
    private BufferedImage image;
    private int x, y;
    private int xVelocity, yVelocity;
    private int panelWidth, panelHeight;

    private ArrayList<BouncingImagePanel> otherPanels;

    public BouncingImagePanel(BufferedImage image, int panelWidth, int panelHeight,  ArrayList<BouncingImagePanel> otherPanels) {
        this.image = image;
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        // start the image at random position
        this.x = (int) (Math.random() * (panelWidth - image.getWidth()));
        this.y = (int) (Math.random() * (panelHeight - image.getHeight()));
        this.xVelocity = 5;
        this.yVelocity = 5;
        this.otherPanels = otherPanels;

        Timer timer = new Timer(30, e -> {
            moveImage();
            checkCollisions();
            repaint();
        });
        timer.start();
    }

    private void moveImage() {
        x += xVelocity;
        y += yVelocity;

        if (x < 0 || x + image.getWidth() > panelWidth) {
            xVelocity = -xVelocity;
        }
        if (y < 0 || y + image.getHeight() > panelHeight) {
            yVelocity = -yVelocity;
        }
    }

//    private void checkCollisions() {
//        for (BouncingImagePanel otherPanel : otherPanels) {
//            if (otherPanel != this) {
//                Rectangle thisBounds = new Rectangle(x, y, image.getWidth(), image.getHeight());
//                Rectangle otherBounds = new Rectangle(otherPanel.x, otherPanel.y, otherPanel.image.getWidth(), otherPanel.image.getHeight());
//                if (thisBounds.intersects(otherBounds)) {
//                    // Collision detected, update velocities
//                    int tempXVelocity = xVelocity;
//                    int tempYVelocity = yVelocity;
//                    xVelocity = otherPanel.xVelocity;
//                    yVelocity = otherPanel.yVelocity;
//                    otherPanel.xVelocity = tempXVelocity;
//                    otherPanel.yVelocity = tempYVelocity;
//                }
//            }
//        }
//    }

    private void checkCollisions() {
        for (BouncingImagePanel otherPanel : otherPanels) {
            if (otherPanel != this) {
                // Calculate the centers of the circles
                double thisCenterX = x + image.getWidth() / 2.0;
                double thisCenterY = y + image.getHeight() / 2.0;
                double otherCenterX = otherPanel.x + otherPanel.image.getWidth() / 2.0;
                double otherCenterY = otherPanel.y + otherPanel.image.getHeight() / 2.0;

                // Calculate the distance between the centers of the circles
                double distanceX = thisCenterX - otherCenterX;
                double distanceY = thisCenterY - otherCenterY;
                double distanceSquared = distanceX * distanceX + distanceY * distanceY;
                double radiusSquared = (image.getWidth() / 2.0 + otherPanel.image.getWidth() / 2.0) * (image.getWidth() / 2.0 + otherPanel.image.getWidth() / 2.0);

                // Check if a collision occurs
                if (distanceSquared <= radiusSquared) {
                    // Calculate the collision normal
                    double normalX = distanceX / Math.sqrt(distanceSquared);
                    double normalY = distanceY / Math.sqrt(distanceSquared);

                    // Calculate the relative velocity
                    double relativeVelocityX = otherPanel.xVelocity - xVelocity;
                    double relativeVelocityY = otherPanel.yVelocity - yVelocity;

                    // Calculate the dot product of the relative velocity and the collision normal
                    double dotProduct = relativeVelocityX * normalX + relativeVelocityY * normalY;

                    // Calculate the impulse along the collision normal
                    double impulseX = normalX * dotProduct;
                    double impulseY = normalY * dotProduct;

                    // Update the velocities of the balls
                    xVelocity += impulseX;
                    yVelocity += impulseY;
                    otherPanel.xVelocity -= impulseX;
                    otherPanel.yVelocity -= impulseY;
                }
            }
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, x, y, this);
    }
}
