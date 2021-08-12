package com.example.a2dgame.world;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.a2dgame.GameDisplay;
import com.example.a2dgame.graphics.Sprite;
import com.example.a2dgame.graphics.SpriteSheet;

public class World {
    protected static final int WORLD_WIDTH = 10;
    protected static final int WORLD_HEIGHT = 10;
    private final WorldGenerator worldGenerator;

    protected Sprite[] worldMap = new Sprite[WORLD_WIDTH * WORLD_HEIGHT];

    public World(SpriteSheet spriteSheet) {
        final Sprite grassSprite = new Sprite(spriteSheet, new Rect(32, 0, 64, 32));
        final Sprite dirtSprite = new Sprite(spriteSheet, new Rect(64, 0, 96, 32));

        Sprite[] sprites = new Sprite[2];
        sprites[0] = grassSprite;
        sprites[1] = dirtSprite;
        worldGenerator = new WorldGenerator(sprites);
        worldGenerator.generateWorld(worldMap, WORLD_WIDTH, WORLD_HEIGHT);
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        for (int x = 0; x < WORLD_WIDTH; x++) {
            for (int y = 0; y < WORLD_HEIGHT; y++) {
                int positionX = x * 100;
                int positionY = y * 100;
                worldMap[y * WORLD_WIDTH + x].draw(canvas,
                        (int) gameDisplay.gameToDisplayCoordinatesX(positionX),
                        (int) gameDisplay.gameToDisplayCoordinatesY(positionY),
                        100, 100, 0.1f);
            }
        }
    }
}



