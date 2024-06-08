package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class GameApp extends JFrame {
    public PlayArea playArea;
    private Menu menu;
    private Clip backgroundMusic;
    private boolean musicOn = false;

    public GameApp() {
        // Set up the frame
        setTitle("Game App");
        // make game app full screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // Use null layout for absolute positioning


        // Center the frame on the screen
        setLocationRelativeTo(null);
        setResizable(false);

        // Create the play area
        playArea = new PlayArea(this);
        playArea.setBounds(0, 0, getWidth(), getHeight());
        add(playArea);

        // Create the menu as a separate window
        menu = new Menu(this);

        // Add a key listener to toggle the menu visibility
        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ESCAPE) {
                    showMenu("Resume", "Music on/off", "Quit game");
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
            showMenu("Play", "Music on/off", "Quit game");
        });

        toggleMusic();
    }

    public float getScale() {
        return Toolkit.getDefaultToolkit().getScreenResolution() / 32f;
    }

    public void showMenu(String... options) {
        menu.setButtons(options);
        menu.setVisible(true);
        playArea.setPaused(true);
        menu.toFront();
        menu.requestFocus();
    }

    public void hideMenu() {
        menu.setVisible(false);
        playArea.setPaused(false);
    }

    public void toggleMusic() {
        musicOn = !musicOn;
        if (musicOn) {
            startMusic();
        } else {
            stopMusic();
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
        menu.dispose(); // Dispose the menu window
    }

    public static void main(String[] args) {
        new GameApp();
    }
}
