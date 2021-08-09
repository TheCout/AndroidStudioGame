package com.example.a2dgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.a2dgame.object.Circle;
import com.example.a2dgame.object.Enemy;
import com.example.a2dgame.object.GameLoop;
import com.example.a2dgame.object.Player;

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
    private List<Bullet> bullets = new ArrayList<Bullet>();
    private int joystickPointerId = 0;
    private int numberOfBulletsToCast = 0;

    private boolean playerIsAlive = true;

    public Game(Context context) {
        super(context);

        // Get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        // Initialize game loop
        gameLoop = new GameLoop(this, surfaceHolder);

        // Initialize game objects
        joystick = new Joystick(265, 850, 120, 65);
        player = new Player(getContext(), joystick, 500, 500, 40);

        // Play some doom music
        Audio.play(context, R.raw.doom);
        // To stop audio: Audio.stop();
        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // Handle touch event actions
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if (joystick.getIsPressed()) {
                    // Joystick was clicked but not pressed, shoot
                    numberOfBulletsToCast++;
                } else if (joystick.isPressed((double) event.getX(), (double) event.getY())) {
                    joystickPointerId = event.getPointerId(event.getActionIndex());
                    joystick.setIsPressed(true);
                } else {
                    numberOfBulletsToCast++;
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if (joystick.getIsPressed()) {
                    joystick.setActuatorPos((double) event.getX(), (double) event.getY());
                }
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if (joystickPointerId == event.getPointerId(event.getActionIndex())) {
                    // Joystick was released
                    joystick.setIsPressed(false);
                    joystick.resetActuatorPos();
                }
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

        // Spawn bullets if the queue isn't empty
        while (numberOfBulletsToCast > 0) {
            bullets.add(new Bullet(getContext(), player, 20));
            numberOfBulletsToCast--;
        }

        // Update each enemy logic
        for (Enemy enemy : enemies) {
            enemy.update();
        }

        // Update each bullet logic
        for (Bullet bullet : bullets) {
            bullet.update();
        }

        // Check collision with each enemy and the player
        Iterator<Enemy> iteratorEnemy = enemies.iterator();
        while(iteratorEnemy.hasNext()) {
            Circle enemy = iteratorEnemy.next();
            if (Circle.isColliding(enemy, player)) {
                // Remove enemy if it collides with the player
                iteratorEnemy.remove();

                float playerHealth = player.getHealthPoints();
                if (playerHealth > 0) {
                    player.setHealthPoints(playerHealth - 10);
                } else {
                    // --- Game over ---
                    playerIsAlive = false;
                }
                continue;
            }

            Iterator<Bullet> iteratorBullet = bullets.iterator();
            while (iteratorBullet.hasNext()) {
                Circle bullet = iteratorBullet.next();

                // Removes bullet if it is outside the screen
                if (bullet.getPositionX() < 0 || bullet.getDirectionY() < 0) {
                    iteratorBullet.remove();
                }
                if (Circle.isColliding(bullet, enemy)) {
                    // Remove enemy and bullet if both collides with each other
                    iteratorEnemy.remove();
                    iteratorBullet.remove();
                    break;
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        for (Enemy enemy : enemies) {
            enemy.draw(canvas);
        }

        for (Bullet bullet : bullets) {
            bullet.draw(canvas);
        }

        joystick.draw(canvas);
        player.draw(canvas);

        drawUPS(canvas);
        drawFPS(canvas);
        
        // Draw game over if player health is less than 0
        if (!playerIsAlive) {
            
        }
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int i, int i1, int i2) {
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        //gameLoop.stopLoop();
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
