package com.gjl2;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface PointableEntity {

    TextureRegion getIcon();

    default boolean isActive() { return true; }
}
