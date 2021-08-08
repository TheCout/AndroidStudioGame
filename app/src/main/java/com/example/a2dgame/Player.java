package com.example.a2dgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

public class Player extends Circle {
    private static final double SPEED_PIXELS_PER_SECOND = 400.0;
    private static final double SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    private final Joystick joystick;

    public Player(Context context, Joystick joystick, double positionX, double positionY, double size) {
        super(context, ContextCompat.getColor(context, R.color.player), positionX, positionY, size);
        this.joystick = joystick;
    }

    public void update() {
        // Update player pos and velocity based an actuator of joystick
        velocityX = joystick.getActuatorX() * SPEED;
        velocityY = joystick.getActuatorY() * SPEED;

        positionX += velocityX;
        positionY += velocityY;
    }
}
