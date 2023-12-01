package com.gjl2;

public class Tile {

    int x,y;
    TileType type;
    Door door;

    Tile(TileType type) {
        this.type = type;
        this.door = null;
    }
}
