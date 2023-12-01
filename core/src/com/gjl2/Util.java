package com.gjl2;

public class Util {

    public static float pointDistanceSquare(float x1, float y1, float x2, float y2) {
        float x = x2 - x1;
        float y = y2 - y1;
        return (x * x) + (y * y);
    }

    public static float pointDistance(float x1, float y1, float x2, float y2) {
        float x = x2 - x1;
        float y = y2 - y1;
        return (float) Math.sqrt((x * x) + (y * y));
    }
}
