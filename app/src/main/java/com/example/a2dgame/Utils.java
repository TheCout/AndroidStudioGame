package com.example.a2dgame;

import com.example.a2dgame.object.GameObject;

public class Utils {
    public static double getDistanceBetweenObjects(GameObject obj1, GameObject obj2) {
        return Math.sqrt(Math.pow(obj2.getPositionX() - obj1.getPositionX(), 2) + Math.pow(obj2.getPositionY() - obj1.getPositionY(), 2));
    }

    public static double getDistanceBetweenPoints(double p0x, double p0y, double p1x, double p1y) {
        return Math.sqrt(Math.pow(p1x - p0x, 2) + Math.pow(p1y - p0y, 2));
    }
}
