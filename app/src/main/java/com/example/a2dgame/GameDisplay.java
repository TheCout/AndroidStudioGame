package com.example.a2dgame;

import com.example.a2dgame.object.GameObject;

public class GameDisplay {
    private double gameToDisplayCoordinateOffsetX;
    private double gameToDisplayCoordinateOffsetY;
    private double displayCenterX;
    private double displayCenterY;
    private double gameCenterX;
    private double gameCenterY;
    private GameObject centerObject;

    public GameDisplay(int widthPixels, int heightPixels, GameObject centerObject) {
        this.centerObject = centerObject;

        displayCenterX = widthPixels / 2.0;
        displayCenterY = heightPixels / 2.0;
    }

    public void update() {
        gameCenterX = centerObject.getPositionX();
        gameCenterY = centerObject.getPositionY();

        gameToDisplayCoordinateOffsetX = displayCenterX - gameCenterX;
        gameToDisplayCoordinateOffsetY = displayCenterY - gameCenterY;
    }

    public double gameToDisplayCoordinatesX(double positionX) {
        return positionX + gameToDisplayCoordinateOffsetX;
    }

    public double gameToDisplayCoordinatesY(double positionY) {
        return positionY + gameToDisplayCoordinateOffsetY;
    }
}
