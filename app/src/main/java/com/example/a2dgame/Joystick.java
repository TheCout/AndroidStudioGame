package com.example.a2dgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Joystick {

    private int outerCircleCenterPostitionX, outerCircleCenterPostitionY;
    private int innerCircleCenterPostitionX, innerCircleCenterPostitionY;
    private double outerCircleRadius, innerCircleRadius;
    private Paint outerCirclePaint, innerCirclePaint;

    private boolean isPressed = false;
    private double joystickCenterToTouchDistance;

    private double actuatorX, actuatorY;

    public Joystick(int centerPositionX, int centerPositionY, double outerCircleRadius, double innerCircleRadius) {

        // Outer and inner circle make up the joystick
        outerCircleCenterPostitionX = centerPositionX;
        outerCircleCenterPostitionY = centerPositionY;
        innerCircleCenterPostitionX = centerPositionX;
        innerCircleCenterPostitionY = centerPositionY;

        // Radius of circle
        this.outerCircleRadius = outerCircleRadius;
        this.innerCircleRadius = innerCircleRadius;

        // Paint of circles
        outerCirclePaint = new Paint();
        outerCirclePaint.setColor(Color.GRAY);
        outerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        innerCirclePaint = new Paint();
        innerCirclePaint.setColor(Color.BLUE);
        innerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle(outerCircleCenterPostitionX, outerCircleCenterPostitionY, (float) outerCircleRadius, outerCirclePaint);
        canvas.drawCircle(innerCircleCenterPostitionX, innerCircleCenterPostitionY, (float) innerCircleRadius, innerCirclePaint);
    }

    public void update() {
        updateInnerCirclePosition();
    }

    private void updateInnerCirclePosition() {
        // Since our actuator pos is normalized between -1 and 1 we multiply it by the outer circle radius
        // and then we add it with the outer circle position, obviously
        innerCircleCenterPostitionX = (int) (outerCircleCenterPostitionX + actuatorX*outerCircleRadius);
        innerCircleCenterPostitionY = (int) (outerCircleCenterPostitionY + actuatorY*outerCircleRadius);
    }

    public boolean isPressed(double positionX, double positionY) {
        joystickCenterToTouchDistance = Math.sqrt(
                Math.pow(outerCircleCenterPostitionX - positionX, 2) +
                        Math.pow(outerCircleCenterPostitionY - positionY, 2)
        );
        return joystickCenterToTouchDistance < outerCircleRadius;
    }

    public void setIsPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }

    public boolean getIsPressed() {
        return this.isPressed;
    }

    public void setActuatorPos(double positionX, double positionY) {
        double deltaX = positionX - outerCircleCenterPostitionX;
        double deltaY = positionY - outerCircleCenterPostitionY;
        double deltaDistance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

        if (deltaDistance < outerCircleRadius) {
            actuatorX = deltaX / outerCircleRadius;
            actuatorY = deltaY / outerCircleRadius;
        }
        else {
            actuatorX = deltaX / deltaDistance;
            actuatorY = deltaY / deltaDistance;
        }
    }

    public void resetActuatorPos() {
        this.actuatorX = 0.0;
        this.actuatorY = 0.0;
    }

    public double getActuatorX() {
        return actuatorX;
    }

    public double getActuatorY() {
        return actuatorY;
    }
}
