package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Menu extends JFrame {
    private GameApp gameApp;
    private JPanel buttonPanel;

    public Menu(GameApp gameApp) {
        this.gameApp = gameApp;
        setTitle("Menu");
        setSize(400, 300);
        setLayout(new BorderLayout());
        setUndecorated(true); // Remove window decorations
        setLocationRelativeTo(null); // Center the menu window
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JLabel menuLabel = new JLabel("Menu", SwingConstants.CENTER);
        menuLabel.setForeground(Color.WHITE);
        menuLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(menuLabel, BorderLayout.NORTH);

        buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 10));
        add(buttonPanel, BorderLayout.CENTER);

        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ESCAPE) {
                    gameApp.hideMenu();
                }
            }
        });
    }

    public void setButtons(String... options) {
        buttonPanel.removeAll();
        for (String option : options) {
            JButton button = new JButton(option);
            button.addActionListener(this::handleButtonAction);
            buttonPanel.add(button);
        }
        buttonPanel.add(new JLabel("Number of balls: " + gameApp.playArea.ballCount()), BorderLayout.SOUTH);
        revalidate();
        repaint();
    }

    private void handleButtonAction(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Play":
                gameApp.hideMenu();
                gameApp.playArea.start();
                break;
            case "Resume":
                gameApp.hideMenu();
                break;
            case "Play Again":
                gameApp.hideMenu();
                gameApp.playArea.start();
                break;
            case "Music on/off":
                gameApp.toggleMusic();
                break;
            case "Quit game":
                System.exit(0);
                break;
            default:
                break;
        }
    }
}
