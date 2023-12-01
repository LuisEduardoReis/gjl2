package com.gjl2;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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

    public static float stepTo(float a, float b, float x) {
        if (Math.abs(b - a) < x) return b;
        else return a + x * Math.signum(b - a);
    }

    static GlyphLayout layout = new GlyphLayout();
    public static void drawTextCentered(SpriteBatch batch, BitmapFont font, String text, float x, float y) {
        layout.setText(font, text);
        font.draw(batch, text, x - layout.width/2, y + layout.height/2);
    }
}
