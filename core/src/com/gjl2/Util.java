package com.gjl2;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL32;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Affine2;

public class Util {

    public static Affine2 affine2 = new Affine2();

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

    public static void drawTextAlignRight(SpriteBatch batch, BitmapFont font, String text, float x, float y) {
        layout.setText(font, text);
        font.draw(batch, text, x - layout.width, y);
    }

    public static void enableBlending() {
        Gdx.gl.glEnable(GL32.GL_BLEND);
        Gdx.gl.glBlendFunc(GL32.GL_SRC_ALPHA, GL32.GL_ONE_MINUS_SRC_ALPHA);
    }

    public static float randomRange(float a, float b){
        return (float) (a + Math.random() * (b - a));
    }

    public static boolean isBetween(float x, float lower, float upper) {
        return lower <= x && x <= upper;
    }

    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }

    public static float mapValue(float v, float a1, float b1, float a2, float b2) {
        return a2 + (b2 - a2) * ((v - a1) / (b1 - a1));
    };
}
