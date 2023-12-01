package com.gjl2;

public class Timer {
    public float timer=0;
    float cooldown;

    public Timer(float cooldown) {
        this.cooldown = cooldown;
    }

    public void update(float delta){
        timer = Util.stepTo(timer, 0, delta);
    }

    public void reset(){
        timer = cooldown;
    }
}
