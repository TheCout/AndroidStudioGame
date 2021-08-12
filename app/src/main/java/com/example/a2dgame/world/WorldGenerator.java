package com.example.a2dgame.world;

import com.example.a2dgame.Utils;
import com.example.a2dgame.graphics.Sprite;

import java.util.Random;

public class WorldGenerator {
    private Sprite[] sprites;

    public WorldGenerator(Sprite[] sprites) {
        this.sprites = sprites;
    }

    public void generateWorld(Sprite[] worldMap, int worldWidth, int worldHeight) {
        for (int x = 0; x < worldWidth; x++) {
            for (int y = 0; y < worldHeight; y++) {
                boolean placeDirt = Utils.tryAChance(20); // There are 20% of chance to place dirt
                int choosenOne = placeDirt ? 1 : 0;
                worldMap[y * worldWidth + x] = sprites[choosenOne];
            }
        }
    }
}
