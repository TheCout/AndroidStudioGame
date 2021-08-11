package com.example.a2dgame.object;

import android.graphics.Canvas;

import com.example.a2dgame.GameDisplay;

/**
 * GameObject is an abstract class which is the foundation of all world objects in the game
 */
public abstract class GameObject {
    protected double positionX, positionY;
    protected double velocityX, velocityY;
    protected double directionX, directionY;

    public GameObject(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public double getPositionX() { return positionX; }
    public double getPositionY() {
        return positionY;
    }
    public double getDirectionX() { return directionX; }
    public double getDirectionY() { return directionY; }

    public abstract void draw(Canvas canvas, GameDisplay gameDisplay);
    public abstract void update();
}
