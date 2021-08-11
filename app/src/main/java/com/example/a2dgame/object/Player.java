package com.example.a2dgame.object;

import android.content.Context;
import android.graphics.Canvas;

import androidx.core.content.ContextCompat;

import com.example.a2dgame.GUI.HealthBar;
import com.example.a2dgame.GUI.Joystick;
import com.example.a2dgame.R;
import com.example.a2dgame.Utils;

public class Player extends Circle {
    private static final double SPEED_PIXELS_PER_SECOND = 400.0;
    private static final double SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    public static final int MAX_HEALTH_POINTS = 100;
    private int healthPoints;

    private Joystick joystick;
    private HealthBar healthBar;

    public Player(Context context, Joystick joystick, double positionX, double positionY, double size) {
        super(context, ContextCompat.getColor(context, R.color.player), positionX, positionY, size);
        this.healthPoints = MAX_HEALTH_POINTS;
        this.joystick = joystick;
        this.healthBar = new HealthBar(context,this);
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        if (healthPoints < 0) this.healthPoints = 0;
        this.healthPoints = healthPoints;
    }

    public void update() {
        // Update player pos and velocity based an actuator of joystick
        velocityX = joystick.getActuatorX() * SPEED;
        velocityY = joystick.getActuatorY() * SPEED;

        // Update position
        positionX += velocityX;
        positionY += velocityY;

        // Update
        if (velocityX != 0 || velocityY != 0) {
            // Normalize velocity to get direction (unit vector of velocity)
            double distance = Utils.getDistanceBetweenPoints(0, 0, velocityX, velocityY);
            directionX = velocityX / distance;
            directionY = velocityY / distance;
        }
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        healthBar.draw(canvas);
    }
}






