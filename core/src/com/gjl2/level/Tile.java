package com.gjl2.level;

import com.gjl2.entities.interactables.Door;

public class Tile {

    public int x,y;
    public TileType type;
    public Door door;

    public Tile(TileType type) {
        this.type = type;
        this.door = null;
    }
}
