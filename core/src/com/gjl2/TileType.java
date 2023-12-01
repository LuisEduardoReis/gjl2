package com.gjl2;

import java.util.HashMap;
import java.util.Map;

public class TileType {

    public static Map<Integer, TileType> TILETYPES_BY_ID = new HashMap<>();
    public static Map<String, TileType> TILETYPES = new HashMap<>();

    static {
        createTileType(255, "test", new TileType(false));
        createTileType(0, "empty", new TileType(true));
        createTileType(1, "room", new TileType(false));
        createTileType(2, "ladder", new TileType(false)).setLadder(true);
        createTileType(16, "door", new TileType(false));
        createTileType(17, "wall", new TileType(true));
        createTileType(3, "shield_overlay", new TileType(false));
        createTileType(4, "o2_overlay", new TileType(false));
    }

    public static TileType createTileType(int id, String name, TileType type) {
        TILETYPES_BY_ID.put(id, type);
        TILETYPES.put(name, type);
        type.id = id;
        type.name = name;
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
            return TILETYPES.get("test");
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

    public TileType setLadder(boolean ladder) {
        this.ladder = ladder;
        return this;
    }
}
