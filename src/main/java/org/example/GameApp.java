package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class GameApp extends JFrame {
    private PlayArea playArea;
    private Menu menu;
    private Clip backgroundMusic;

    public GameApp() {
        // Set up the frame
        setTitle("Game App");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // Use null layout for absolute positioning

        // Create the play area
        playArea = new PlayArea();
        playArea.setBounds(0, 0, 800, 600);
        add(playArea);

        // Create the menu (initially hidden)
        menu = new Menu();
        menu.setBounds(200, 150, 400, 300);
        menu.setVisible(false);
        add(menu);

        // Add a key listener to toggle the menu visibility
        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ESCAPE) {
                    toggleMenu();
                }
            }
        });

        // Initialize the background music
        try {
            File musicFile = new File("music.wav");
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicFile);
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioInput);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }

        // Add mouse motion listener to play area
        playArea.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // Update cursor position on dragging
                playArea.updatePlayerPosition(e.getX(), e.getY());
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                // Update cursor position on movement
                playArea.updatePlayerPosition(e.getX(), e.getY());
            }
        });

        SwingUtilities.invokeLater(() -> {
            setVisible(true);
            startMusic();
            playArea.start();
        });
    }

    private void toggleMenu() {
        menu.setVisible(!menu.isVisible());
        playArea.setPaused(menu.isVisible());
        if (menu.isVisible()) {
            requestFocus();
        }
    }

    private void startMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    private void stopMusic() {
        if (backgroundMusic != null && backgroundMusic.isRunning()) {
            backgroundMusic.stop();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        stopMusic(); // Stop music when the application is closed
    }

    public static void main(String[] args) {
        new GameApp();
    }
}
