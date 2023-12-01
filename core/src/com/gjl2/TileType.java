package com.gjl2;

import java.util.HashMap;
import java.util.Map;

public class TileType {

    public static Map<Integer, TileType> TILETYPES_BY_ID = new HashMap<>();
    public static Map<String, TileType> TILETYPES = new HashMap<>();

    static {
        for (int i = 0; i < 255; i++) {
            createTileType(i, new TileType(true));
        }
        getTileTypeByPosition(1,0).setSolid(false);
        getTileTypeByPosition(2,0).setSolid(false).setLadder(true);
        getTileTypeByPosition(0,1).setSolid(false);
    }

    public static TileType createTileType(int id, TileType type) {
        TILETYPES_BY_ID.put(id, type);
        type.id = id;
        return type;
    }
    public static TileType getTileType(String name) {
        if (TILETYPES.containsKey(name)) {
            return TILETYPES.get(name);
        } else {
            return TILETYPES.get("test");
        }
    }
    public static TileType getTileTypeById(int id) {
        if (TILETYPES_BY_ID.containsKey(id)) {
            return TILETYPES_BY_ID.get(id);
        } else {
            return TILETYPES_BY_ID.get(255);
        }
    }
    public static TileType getTileTypeByPosition(int x, int y) {
        if (TILETYPES_BY_ID.containsKey(y * 16 + x)) {
            return TILETYPES_BY_ID.get(y * 16 + x);
        } else {
            return TILETYPES_BY_ID.get(255);
        }
    }

    public boolean solid;
    public boolean ladder;
    public int id;
    public String name;

    TileType(boolean solid) {
        this.solid = solid;
        this.ladder = false;
    }

    private TileType setLadder(boolean ladder) {
        this.ladder = ladder;
        return this;
    }
    private TileType setSolid(boolean solid) {
        this.solid = solid;
        return this;
    }
}
