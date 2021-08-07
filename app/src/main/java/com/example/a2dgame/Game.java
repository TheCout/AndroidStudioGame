package com.example.a2dgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

/***
 *  Game manages all objects in the game and is responsible for updating all states and render all objects to the screen
 */
public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private GameLoop gameLoop;
    private Context context;

    public Game(Context context) {
        super(context);

        // Get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        this.context = context;
        gameLoop = new GameLoop(this, surfaceHolder);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    public void drawUPS(Canvas canvas) {
        String avarageUPS = Double.toString(gameLoop.getAvarageUPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("UPS: " + avarageUPS, 100, 100, paint);
    }

    public void drawFPS(Canvas canvas) {
        String avarageFPS = Double.toString(gameLoop.getAvarageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("FPS: " + avarageFPS, 100, 180, paint);
    }

    public void update() {
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawUPS(canvas);
        drawFPS(canvas);
    }
}
