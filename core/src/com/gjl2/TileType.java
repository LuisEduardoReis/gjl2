package com.gjl2;

public class TileType {
    public boolean solid;

    TileType(boolean solid) {
        this.solid = solid;
    }

    public static TileType WALL = new TileType(true);
    public static TileType ROOM = new TileType(false);
}
