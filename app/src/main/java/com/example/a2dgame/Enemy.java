package com.example.a2dgame;

import android.content.Context;

import androidx.core.content.ContextCompat;

public class Enemy extends Circle {
    private static final double SPEED_PIXELS_PER_SECOND = 300.0;
    private static final double SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    private static final double SPAWNS_PER_MINUTE = 20;
    private static final double UPDATES_PER_SECOND = SPAWNS_PER_MINUTE / 60;
    private static final double UPDATES_PER_SPAWN = GameLoop.MAX_UPS / UPDATES_PER_SECOND;
    private static double updatesUntilNextSpawn = UPDATES_PER_SPAWN;
    private Player player;

    public Enemy(Context context, Player player, double size) {
        super(context, ContextCompat.getColor(context, R.color.enemy),
                Math.random() * 1000, Math.random() * 1000, size);
        this.player = player;
    }

    public static boolean readyToSpawn() {
        if (updatesUntilNextSpawn <= 0) {
            updatesUntilNextSpawn += UPDATES_PER_SPAWN;
            return true;
        }
        updatesUntilNextSpawn--;
        return false;
    }

    @Override
    public void update() {
        // =============================================================================================================================================================
        //      Update velocity of the enemy so that the velocity is in the direction of the player
        // =============================================================================================================================================================
        // Calculate vector from enemy to plater (in x and y)
        double distanceToPlayerX = player.getPositionX() - positionX;
        double distanceToPlayerY = player.getPositionY() - positionY;

        // Calculate (absolute) distance between enemy (this) and player
        double distanceToPlayer = GameObject.getDistanceBetweenObjects(this, player);

        // Calculate direction from enemy to player
        double directionX = distanceToPlayerX/distanceToPlayer;
        double directionY = distanceToPlayerY/distanceToPlayer;

        // Set velocity in the direction to the player
        if (distanceToPlayer > 0) {
            velocityX = directionX * SPEED;
            velocityY = directionY * SPEED;
        } else {
            velocityX = 0;
            velocityY = 0;
        }

        // Update the position of the enemy
        positionX += velocityX;
        positionY += velocityY;
    }
}
