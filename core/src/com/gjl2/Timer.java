package com.gjl2;

import java.util.LinkedList;
import java.util.List;

public class Timer {
    public float timer=0;
    float cooldown;
    List<Runnable> callBacks = new LinkedList<>();

    public Timer(float cooldown) {
        this.cooldown = cooldown;
    }

    public void update(float delta){
        timer = Util.stepTo(timer, 0, delta);
        if(timer==0){
            for (Runnable f : callBacks
                 ) {
                f.run();
            }
        }
    }

    public void addCallback(Runnable f) {
        callBacks.add(f);
    }

    public void reset(){
        timer = cooldown;
    }
}
