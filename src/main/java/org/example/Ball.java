    package org.example;

    import javax.swing.*;
    import java.awt.*;
    import java.util.List;

    public class Ball {
        private Vector position;
        private Vector velocity;
        private double radius;
        private Color color;

        public Ball(double x, double y, double radius, Color color) {
            this.position = new Vector(x, y);
            this.velocity = new Vector(5, 5); // Initial velocity
            this.radius = radius;
            this.color = color;
        }

        public Ball(Vector position, double radius, Color color) {
            this.position = position;
            this.radius = radius;
            this.color = color;
            this.velocity = new Vector(5, 5); // Initial velocity
        }

        public void draw(Graphics g) {
            g.setColor(color);
            g.fillOval((int) (position.x - radius), (int) (position.y - radius), (int) (2 * radius), (int) (2 * radius));
        }

        public void move() {
            position.add(velocity);
        }

        public void checkBoxCollision(List<Wall> walls) {
            for (Wall wall : walls) {
                if (isColliding(wall)) {
                    resolveWallPenetration(wall);
                }
            }
        }

        public void resolveWallPenetration(Wall wall) {
            int x = wall.getX();
            int y = wall.getY();
            int width = wall.getWidth();
            int height = wall.getHeight();

            if (wall.isInbound()) {
                // If the wall is inbound, we handle the collisions inside the wall's boundaries
                if (position.x - radius < x) {
                    position.x = x + radius;
                    velocity.x = -velocity.x;
                }
                if (position.x + radius > x + width) {
                    position.x = x + width - radius;
                    velocity.x = -velocity.x;
                }
                if (position.y - radius < y) {
                    position.y = y + radius;
                    velocity.y = -velocity.y;
                }
                if (position.y + radius > y + height) {
                    position.y = y + height - radius;
                    velocity.y = -velocity.y;
                }
            } else {
                if (position.x + radius >= x && position.x - radius <= x + width && position.y + radius >= y && position.y - radius <= y + height) {
                    // check whick side of the wall the ball is colliding with
                    double dx = Math.min(Math.abs(position.x - x), Math.abs(position.x - x - width));
                    double dy = Math.min(Math.abs(position.y - y), Math.abs(position.y - y - height));

                    if (dx < dy) {
                        // horizontal collision
                        if (position.x < x + width / 2) {
                            position.x = x - radius;
                        } else {
                            position.x = x + width + radius;
                        }
                        velocity.x = -velocity.x;
                    } else {
                        // vertical collision
                        if (position.y < y + height / 2) {
                            position.y = y - radius;
                        } else {
                            position.y = y + height + radius;
                        }
                        velocity.y = -velocity.y;
                    }
                }

            }
        }


        public void checkBallCollision(List<Ball> balls) {
            for (Ball ball : balls) {
                if (ball != this && isColliding(ball)) {
                    resolvePenetration(ball);
                    velocityChange(ball);
                }
            }
        }

        public boolean isColliding(Ball ball) {
            Vector distanceVec = new Vector(ball.position.x - this.position.x, ball.position.y - this.position.y);
            double distance = distanceVec.magnitude();
            return distance < ball.radius + this.radius;
        }

        public boolean isColliding(Wall wall) {
            return position.x - radius <= 0 || position.x + radius >= wall.getWidth() ||
                    position.y - radius <= 0 || position.y + radius >= wall.getHeight();
        }

        public void resolvePenetration(Ball ball) {
            Vector distanceVec = new Vector(ball.position.x - this.position.x, ball.position.y - this.position.y);
            double distance = distanceVec.magnitude();
            double overlap = radius + ball.radius - distance;
            distanceVec.normalize();
            distanceVec.multiply(overlap / 2);
            position.subtract(distanceVec);
            ball.position.add(distanceVec);
        }

        public void velocityChange(Ball ball) {
            Vector normal = new Vector(ball.position.x - this.position.x, ball.position.y - this.position.y);
            double distance = normal.magnitude();
            normal.normalize();

            Vector relativeVelocity = new Vector(ball.velocity.x - this.velocity.x, ball.velocity.y - this.velocity.y);
            double dot = relativeVelocity.dot(normal);

            double impulse = 2 * dot / (1 / this.radius + 1 / ball.radius);

            Vector impulseVec = new Vector(normal.multiply(impulse));

            this.velocity.add(new Vector(impulseVec.x / this.radius, impulseVec.y / this.radius));
            ball.velocity.subtract(new Vector(impulseVec.x / ball.radius, impulseVec.y / ball.radius));
        }


        public Vector getPosition() {
            return position;
        }

        public double getRadius() {
            return radius;
        }
    }
