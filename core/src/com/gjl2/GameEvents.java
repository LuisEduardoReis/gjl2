package com.gjl2;
import java.util.function.Function;

public class GameEvents {
    Level level;

    float timeToEvent;
    public GameEvents(Level level) {
        this.level = level;
        timeToEvent = Util.randomRange(10, 30);
    }

    public void update(float delta){
        timeToEvent = Util.stepTo(timeToEvent, 0, 1*delta);
        if (timeToEvent == 0) {
            rollEvent();
            timeToEvent =  Util.randomRange(10, 30);
        }
    }

    public static boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }

    private void rollEvent(){
        int roll;
        roll = (int)Util.randomRange(1, 100);
        if (isBetween(roll, 0, 90) ){
            //Oxygen leak
            this.level.O2Level = this.level.O2Level - 20;
        }
    }
}
