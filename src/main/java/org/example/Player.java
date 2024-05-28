package org.example;

import java.awt.*;
import java.util.List;

public class Player {
    private Vector position;
    private double radius;
    private Color color = Color.RED;

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval((int) (position.x - radius), (int) (position.y - radius), (int) (2 * radius), (int) (2 * radius));
    }

    public Player(double x, double y, double radius) {
        this.position = new Vector(x, y);
        this.radius = radius;
    }

    public Vector getPosition() {
        return position;
    }

    public double getRadius() {
        return radius;
    }

    public void setPosition(int x, int y) {
        this.position = new Vector(x, y);
    }

    public void checkBallCollision(List<Ball> balls) {
        for (Ball ball : balls) {
            if (isColliding(ball)) {
                System.out.println("Player collided with ball");
            }
        }
    }

    public boolean isColliding(Ball ball) {
        Vector distanceVec = new Vector(ball.getPosition().x - this.position.x, ball.getPosition().y - this.position.y);
        double distance = distanceVec.magnitude();
        return distance < ball.getRadius() + this.radius;
    }
}
