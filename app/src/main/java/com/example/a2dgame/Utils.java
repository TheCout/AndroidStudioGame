package com.example.a2dgame;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.example.a2dgame.object.GameObject;

import java.util.Random;

public class Utils {
    public static double getDistanceBetweenObjects(GameObject obj1, GameObject obj2) {
        return Math.sqrt(Math.pow(obj2.getPositionX() - obj1.getPositionX(), 2) + Math.pow(obj2.getPositionY() - obj1.getPositionY(), 2));
    }

    public static double getDistanceBetweenPoints(double p0x, double p0y, double p1x, double p1y) {
        return Math.sqrt(Math.pow(p1x - p0x, 2) + Math.pow(p1y - p0y, 2));
    }

    public static double getDiagonalSize(double side0, double side1) {
        return Math.sqrt(Math.pow(side0, 2) + Math.pow(side1, 2));
    }

    public static double getAngleFromDirection(double directionX, double directionY) {
        double angle = Math.atan2(directionX, directionY);   //radians
        double degrees = 180 * angle/Math.PI;  //degrees
        return degrees;
    }

    public static boolean tryAChance(int chancePercent) {
        final int choosenOne = new Random().nextInt((100 - 0) + 100) + 0;
        return choosenOne < chancePercent;
    }
}
