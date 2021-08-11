package com.example.a2dgame;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.a2dgame.GUI.GameOver;
import com.example.a2dgame.GUI.Joystick;
import com.example.a2dgame.GUI.Performance;
import com.example.a2dgame.object.Bullet;
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
    private GameOver gameOver;
    private Performance performance;
    private GameDisplay gameDisplay;

    public Game(Context context) {
        super(context);

        // Get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        // Initialize game loop
        gameLoop = new GameLoop(this, surfaceHolder);

        // Initialize game panels
        performance = new Performance(context, gameLoop);
        gameOver = new GameOver(context);
        joystick = new Joystick(265, 850, 120, 65);

        // Initialize game objects
        player = new Player(context, joystick, 500, 500, 40);

        // Initialize game display and center it around the player
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        gameDisplay = new GameDisplay(displayMetrics.widthPixels, displayMetrics.heightPixels, player);

        // Play some doom music
        Audio.play(context, R.raw.doom);
        // To stop audio: Audio.stop();
        setFocusable(true);
    }

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

    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        Log.d("Game.java", "surfaceCreated()");
        if (gameLoop.getState().equals(Thread.State.TERMINATED))
        {
            gameLoop = new GameLoop(this, holder);
        }
        gameLoop.startLoop();
    }
    public void surfaceChanged(@NonNull SurfaceHolder holder, int i, int i1, int i2) {
        Log.d("Game.java", "surfaceChanged()");
    }
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        Log.d("Game.java", "surfaceDestroyed()");
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        performance.drawUPS(canvas);
        performance.drawFPS(canvas);

        // Draw game objects
        player.draw(canvas, gameDisplay);

        for (Enemy enemy : enemies) {
            enemy.draw(canvas, gameDisplay);
        }

        for (Bullet bullet : bullets) {
            bullet.draw(canvas, gameDisplay);
        }

        // Draw game panels
        joystick.draw(canvas);
        performance.draw(canvas);

        // Draw game over if player health is less than 0
        if (!playerIsAlive) {
            gameOver.draw(canvas);
        }
    }

    public void update() {

        // If the game is over, stop updating the game
        if (!playerIsAlive) { return; }

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

                int playerHealth = player.getHealthPoints();
                if (playerHealth - 20 > 0) {
                    player.setHealthPoints(playerHealth - 20);
                } else {
                    // --- Game over ---
                    playerIsAlive = false;
                }
                continue;
            }

            Iterator<Bullet> iteratorBullet = bullets.iterator();
            while (iteratorBullet.hasNext()) {
                Circle bullet = iteratorBullet.next();
                
                if (Circle.isColliding(bullet, enemy)) {
                    // Remove enemy and bullet if both collides with each other
                    iteratorEnemy.remove();
                    iteratorBullet.remove();
                    break;
                }
            }
        }
        gameDisplay.update();
    }

    public void pause() {
        gameLoop.stopLoop();
    }
}
