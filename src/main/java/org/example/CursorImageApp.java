package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class CursorImageApp {
    public static void main(String[] args) {
        // Get the screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        // Load the PNG image
        BufferedImage cursorImage = null;
        Cursor player = null;
        try {
            //read the image
            cursorImage = ImageIO.read(new File("C:\\Users\\0oski\\IdeaProjects\\Lab6\\res\\ball.jpg"));
            System.out.println(cursorImage.getWidth());
            System.out.println(cursorImage.getHeight());
            player = Toolkit.getDefaultToolkit().createCustomCursor(
                    cursorImage, new Point(15, 15), "blank cursor");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }


        // Create the main frame
        JFrame frame = new JFrame("Cursor Image App");

        if (player != null) {
            frame.setCursor(player);
        }

        frame.setSize(screenSize);
        frame.setUndecorated(true);  // Remove the title bar and borders
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);  // Use null layout for absolute positioning

        // Create a custom panel to draw the image at the cursor position
        CustomPanel customPanel = new CustomPanel(cursorImage);
        customPanel.setBounds(0, 0, screenWidth, screenHeight);
        frame.add(customPanel);

        // Add a MouseMotionListener to the frame's content pane to track mouse movements
        frame.getContentPane().addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // Update cursor position on dragging
                customPanel.updateCursorPosition(e.getX(), e.getY());
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                // Update cursor position on movement
                customPanel.updateCursorPosition(e.getX(), e.getY());
            }
        });

        // Make the frame visible
        frame.setVisible(true);
    }
}

class CustomPanel extends JPanel {
    private BufferedImage image;
    private int x = -1;
    private int y = -1;

    public CustomPanel(BufferedImage image) {
        this.image = image;
    }

    public void updateCursorPosition(int x, int y) {
        this.x = x;
        this.y = y;
        repaint();  // Request a repaint to update the image position
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (x != -1 && y != -1) {  // Draw the image only if the cursor has moved
            g.drawImage(image, x, y, this);
        }
    }
}
