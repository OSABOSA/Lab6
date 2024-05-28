package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JPanel {
    public Menu() {
        setLayout(new BorderLayout());
        setBackground(new Color(0, 0, 0, 200)); // Semi-transparent background

        JLabel menuLabel = new JLabel("Menu", SwingConstants.CENTER);
        menuLabel.setForeground(Color.WHITE);
        menuLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(menuLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));

        JButton resumeButton = new JButton("Resume");
        resumeButton.addActionListener(e -> setVisible(false));

        JButton settingsButton = new JButton("Settings");
        settingsButton.addActionListener(e -> JOptionPane.showMessageDialog(Menu.this, "Settings clicked"));

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(resumeButton);
        buttonPanel.add(settingsButton);
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);
    }
}
