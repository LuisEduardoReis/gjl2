package com.gjl2;

import com.badlogic.gdx.graphics.Color;

public class AlienChunkParticle extends Particle {

    public AlienChunkParticle() {
        super();
        color = new Color(0x297520ff);
        size = 0.2f;
        hasGravity = true;
        collidesWithLevel = true;

        vx = Util.randomRange(-2, 2);
        vy = Util.randomRange(2, 5);
    }
}
