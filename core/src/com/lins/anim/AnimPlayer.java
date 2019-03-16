package com.lins.anim;

import com.badlogic.gdx.utils.Timer;

public class AnimPlayer {
    private class Task extends Timer.Task {
        final float deltaDelaySec;
        /** A time in milliseconds */
        long prevExecuteTime;

        Task(float deltaDelaySec) {
            super();
            this.deltaDelaySec = deltaDelaySec;
        }

        public void run() {
            final long deltaTime = getExecuteTimeMillis() - prevExecuteTime;   // in milliseconds
            anim.act(deltaTime);
            prevExecuteTime = getExecuteTimeMillis();
            if (anim.progress < 1)
                timer.scheduleTask(this, deltaDelaySec);
            else
                if (onAnimFinish != null) onAnimFinish.run();
        }
    }

    private Anim anim;
    private final Timer timer = new Timer();
    private final Task task;
    public Runnable onAnimFinish;

    public AnimPlayer() {
        this(.03f);
    }

    public AnimPlayer(float deltaDelaySec) {
        this(deltaDelaySec, null);
    }

    public AnimPlayer(Anim anim) {
        this(.03f, anim);
    }

    public AnimPlayer(float deltaDelaySec, Anim anim) {
        this.anim = anim;
        task = new Task(deltaDelaySec);
    }

    public void start() {
        if (anim == null) return;
        task.prevExecuteTime = System.nanoTime() / 1000000;
        timer.postTask(task);
    }

    public void startAnim(Anim anim) {
        this.anim = anim;
        start();
    }
}
