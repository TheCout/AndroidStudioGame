package com.example.a2dgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/***
 *  Game manages all objects in the game and is responsible for updating all states and render all objects to the screen
 */
public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final Player player;
    private final Joystick joystick;

    private GameLoop gameLoop;
    private List<Enemy> enemies = new ArrayList<Enemy>();

    public Game(Context context) {
        super(context);

        // Get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameLoop = new GameLoop(this, surfaceHolder);

        // Initialize game objects
        joystick = new Joystick(265, 850, 120, 65);
        player = new Player(getContext(), joystick, 500, 500, 40);
        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // Handle touch event actions
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (joystick.isPressed((double) event.getX(), (double) event.getY())) {
                    joystick.setIsPressed(true);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if (joystick.getIsPressed()) {
                    joystick.setActuatorPos((double) event.getX(), (double) event.getY());
                }
                return true;
            case MotionEvent.ACTION_UP:
                joystick.setIsPressed(false);
                joystick.resetActuatorPos();
                return true;
        }
        return super.onTouchEvent(event);
    }

    public void update() {
        // Update game state
        joystick.update();
        player.update();

        // Spawn enemies
        if (Enemy.readyToSpawn()) {
            enemies.add(new Enemy(getContext(), player, (double) 40));
        }

        // Update each enemy logic
        for (Enemy enemy : enemies) {
            enemy.update();
        }

        // Check collision with each enemy and the player
        Iterator<Enemy> iteratorEnemy = enemies.iterator();
        while(iteratorEnemy.hasNext()) {
            if (Circle.isColliding(iteratorEnemy.next(), player)) {
                // Remove enemy if it collides with the player
                iteratorEnemy.remove();
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawUPS(canvas);
        drawFPS(canvas);

        joystick.draw(canvas);
        player.draw(canvas);
        for (Enemy enemy : enemies) {
            enemy.draw(canvas);
        }
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
        int color = ContextCompat.getColor(getContext(), R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("UPS: " + avarageUPS, 100, 100, paint);
    }

    public void drawFPS(Canvas canvas) {
        String avarageFPS = Double.toString(gameLoop.getAvarageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("FPS: " + avarageFPS, 100, 180, paint);
    }
}
