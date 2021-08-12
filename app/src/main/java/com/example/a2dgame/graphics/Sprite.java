package com.example.a2dgame.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

import com.example.a2dgame.Utils;

public class Sprite {
    private final SpriteSheet spriteSheet;
    private final Rect rect;

    public Sprite(SpriteSheet spriteSheet, Rect rect) {
        this.spriteSheet = spriteSheet;
        this.rect = rect;
    }

    public void draw(Canvas canvas, int positionX, int positionY, int width, int height, float angle) {
        canvas.save();
        canvas.rotate(angle, positionX, positionY);

        canvas.drawBitmap(
                spriteSheet.getBitmap(),
                rect,
                new Rect(positionX - width / 2, positionY - height / 2, positionX + width / 2, positionY + height / 2),
                null
        );
        canvas.restore();
    }

    public int getWidth() {
        return rect.width();
    }

    public int getHeight() {
        return rect.height();
    }
}
