package com.gjl2;

public class Tile {

    int x,y;
    TileType type;
    Door door;

    OxygenRoom oxygenRoom;

    Tile(TileType type) {
        this.type = type;
        this.door = null;
    }
}
