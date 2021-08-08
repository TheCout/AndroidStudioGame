package com.example.a2dgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class Circle extends GameObject {
    protected double radius;
    protected Paint paint;

    public Circle(Context context, int color, double positionX, double positionY, double radius) {
        super(positionX, positionY);
        this.radius = radius;

        // How circle should be painted
        paint = new Paint();
        paint.setColor(color);
    }

    public double getRadius() {
        return radius;
    }

    public static boolean isColliding(Circle obj1, Circle obj2) {
        double distance = getDistanceBetweenObjects(obj1, obj2);
        double distanceToCollision = obj1.getRadius() + obj2.getRadius();
        return distance < distanceToCollision;
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle((float) positionX, (float) positionY, (float) radius, paint);
    }
}
