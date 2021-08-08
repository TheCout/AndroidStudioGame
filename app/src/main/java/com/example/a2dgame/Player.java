package com.example.a2dgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

public class Player {
    private static final double SPEED_PIXELS_PER_SECOND = 400.0;
    private static final double SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;

    private double positionX, positionY, size;
    private Paint paint;
    private double velocityX, velocityY;

    public Player(Context context, double positionX, double positionY, double size) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.size = size;

        paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.player);
        paint.setColor(color);
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle((float) positionX, (float) positionY, (float) size, paint);
    }

    public void update(Joystick joystick) {
        velocityX = joystick.getActuatorX() * SPEED;
        velocityY = joystick.getActuatorY() * SPEED;
        positionX += velocityX;
        positionY += velocityY;
    }

    public void setPosition(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }
}
