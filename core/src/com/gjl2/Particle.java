package com.gjl2;

public class Particle extends Entity {

    float ttl = 1;

    Particle() {
    }

    @Override
    void update(float delta) {
        ttl = Util.stepTo(ttl, 0, delta);
        if (ttl == 0) {
            remove = true;
        }
    }
}
