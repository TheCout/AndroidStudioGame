package com.example.a2dgame.object;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.a2dgame.R;
import com.example.a2dgame.object.Circle;
import com.example.a2dgame.object.GameLoop;
import com.example.a2dgame.object.Player;

public class Bullet extends Circle {
    private static final double SPEED_PIXELS_PER_SECOND = 800.0;
    private static final double SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;

    public Bullet(Context context, Player player, double size) {
        super(context, ContextCompat.getColor(context, R.color.bullet), player.getPositionX(), player.getPositionY(), size);

        velocityX = player.getDirectionX() * SPEED;
        velocityY = player.getDirectionY() * SPEED;
    }

    @Override
    public void update() {
        positionX += velocityX;
        positionY += velocityY;
    }
}
