package com.example.a2dgame;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class Audio {
    private static MediaPlayer mp;
    private static SoundPool sp;
    private static boolean shouldStop = false;

    public static void play(Context context, int resid) {
        if (mp == null) {
            mp = MediaPlayer.create(context, resid);
            sp = new SoundPool.Builder().setMaxStreams(4).build();
            if (!mp.isPlaying() && !shouldStop)
            {
                mp.start();
                mp.setLooping(true);
            }
        }
        mp.start();
    }

    public static void stop() {
        mp.stop();
        shouldStop = true;
    }
}
