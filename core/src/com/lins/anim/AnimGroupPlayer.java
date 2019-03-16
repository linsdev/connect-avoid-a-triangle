package com.lins.anim;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;

public class AnimGroupPlayer {
    private class Task extends Timer.Task {
        final float deltaDelaySec;
        /** A time in milliseconds */
        long prevExecuteTime;

        Task(float deltaDelaySec) {
            super();
            this.deltaDelaySec = deltaDelaySec;
        }

        public void run() {
            float remains = 0;
            final long deltaTime = getExecuteTimeMillis() - prevExecuteTime;   // in milliseconds
            for (Anim a : anims) {
                a.act(deltaTime);
                remains += 1 - a.progress;
            }
            prevExecuteTime = getExecuteTimeMillis();
            if (remains > 0)
                timer.scheduleTask(this, deltaDelaySec);
            else
                if (onAnimFinish != null) onAnimFinish.run();
        }
    }

    private final Array<Anim> anims;
    private final Timer timer = new Timer();
    private final Task task;
    public Runnable onAnimFinish;

    public AnimGroupPlayer(int objectsNumber) {
        this(.03f, objectsNumber);
    }

    public AnimGroupPlayer(float deltaDelaySec, int objectsNumber) {
        anims = new Array<Anim>(objectsNumber);
        task = new Task(deltaDelaySec);
    }

    public void add(Anim anim) {
        anims.add(anim);
    }

    public void start() {
        task.prevExecuteTime = System.nanoTime() / 1000000;
        timer.postTask(task);
    }
}