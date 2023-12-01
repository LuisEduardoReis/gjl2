package com.gjl2;

import java.util.LinkedList;
import java.util.List;

public class Timer {
    private final float delay;
    public float timer = 0;
    List<Runnable> callBacks = new LinkedList<>();

    public Timer(float delay) {
        this.delay = delay;
        reset();
    }

    public void update(float delta){
        if (timer > 0) {
            timer = Util.stepTo(timer, 0, delta);
            if(timer == 0){
                for (Runnable f : callBacks) {
                    f.run();
                }
            }
        }
    }

    public void addCallback(Runnable f) {
        callBacks.add(f);
    }

    public void reset(){
        timer = delay;
    }
}
