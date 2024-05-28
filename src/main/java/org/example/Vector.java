package org.example;

public class Vector {
    public double x, y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector(Vector v) {
        this.x = v.x;
        this.y = v.y;
    }

    public Vector add(Vector v) {
        this.x += v.x;
        this.y += v.y;
        return this;
    }

    public Vector subtract(Vector v) {
        this.x -= v.x;
        this.y -= v.y;
        return this;
    }

    public Vector multiply(double scalar) {
        this.x *= scalar;
        this.y *= scalar;
        return this;
    }

    public Vector divide(double scalar) {
        this.x /= scalar;
        this.y /= scalar;
        return this;
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public Vector normalize() {
        double mag = magnitude();
        if (mag != 0) {
            divide(mag);
        }
        return this;
    }

    public double dot(Vector v) {
        return this.x * v.x + this.y * v.y;
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }
}
