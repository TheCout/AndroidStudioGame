package com.example.a2dgame;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

/**
 * MainActivity is the entry point to our application
 */
public class MainActivity extends Activity {

    private Game game;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set content view to game, so that objects in the Game class can be rendered to the screen
        game = new Game(this);
        setContentView(game);
    }

    protected void onStart() {
        Log.d("MainActivity.java", "onStart()");
        super.onStart();
    }
    protected void onResume() {
        Log.d("MainActivity.java", "onResume()");
        super.onResume();
    }
    protected void onPause() {
        Log.d("MainActivity.java", "onPause()");
        game.pause();
        super.onPause();
    }
    protected void onStop() {
        Log.d("MainActivity.java", "onStop()");
        super.onStop();
    }
    protected void onDestroy() {
        Log.d("MainActivity.java", "onDestroy()");
        super.onDestroy();
    }

    public void onBackPressed() {
        // Comment out super to prevent any back press action
        // super.onBackPressed();
    }
}