
package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PlayArea extends Wall {
    private boolean paused;
    private List<Ball> balls;
    private List<Wall> walls;
    private Player player;
    private GameApp gameApp;
    private Timer ballAdditionTimer;

    public PlayArea(GameApp gameApp) {
        this.gameApp = gameApp;
        paused = false;
        balls = new ArrayList<>(); // Initialize the list of balls
        walls = new ArrayList<>();
        walls.add(new Wall());
        player = new Player(0, 0, 10);
        setInbound(true);

        // Add a new ball every 30 seconds
        ballAdditionTimer = new Timer(30000, e -> {
            addBall(new Ball(findEmptySpot(10), 10, Color.BLUE));
        });
        ballAdditionTimer.start();
    }

    public void updatePlayerPosition(int x, int y) {
        player.setPosition(x, y);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        walls.set(0, new Wall(x, y, width, height, true));
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
        repaint();
    }

    public void addBall(Ball ball) {
        balls.add(ball); // Add a ball to the list
        System.out.println("Ball added at: " + ball.getPosition());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the balls when not paused
        if (!paused) {
            for (Wall wall : walls) {
                wall.draw(g);
            }
            for (Ball ball : balls) {
                ball.draw(g);
            }
            player.draw(g);
        } else {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.WHITE);
            g.drawString("Paused", getWidth() / 2 - 30, getHeight() / 2);
        }
    }

    Timer gameTimer = new Timer(10, e -> {
        if (!paused) {
            for (Ball ball : balls) {
                ball.checkBoxCollision(walls);
                ball.checkBallCollision(balls);
                ball.move();
            }
            if (player.checkBallCollision(balls)) {
                System.out.println("Player collided with ball");
                // Show menu and stop the game
                gameApp.showMenu("Play Again", "Music on/off");
            }

            repaint();
        }
    });

    public void addWall(Wall wall) {
        walls.add(wall);
    }

    public void start() {
        balls.clear();
        for (int i = 0; i < 5; i++) {
            addBall(new Ball(findEmptySpot(10), 10, Color.BLUE));
        }
        gameTimer.start();
    }

    public Vector findEmptySpot(double radius) {
        Vector spot = new Vector(0, 0);
        boolean found = false;
        while (!found) {
            spot.x = Math.random() * (getWidth() - 2 * radius) + radius;
            spot.y = Math.random() * (getHeight() - 2 * radius) + radius;
            Vector spotCopy = new Vector(spot);
            found = true;
            for (Wall wall : walls) {
                if (wall.isInbound()) {
                    continue;
                }
                if (spotCopy.x - radius >= wall.getX() && spotCopy.x + radius <= wall.getX() + wall.getWidth() &&
                        spotCopy.y - radius >= wall.getY() && spotCopy.y + radius <= wall.getY() + wall.getHeight()) {
                    found = false;
                    break;
                }
            }
            for (Ball ball : balls) {
                if (spotCopy.subtract(ball.getPosition()).magnitude() < ball.getRadius() + radius) {
                    found = false;
                    break;
                }
            }
        }
        return spot;
    }
}