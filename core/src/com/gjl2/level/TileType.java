package com.gjl2.level;

import java.util.HashMap;
import java.util.Map;

public class TileType {

    public static Map<Integer, TileType> TILETYPES_BY_ID = new HashMap<>();
    public static Map<String, TileType> TILETYPES = new HashMap<>();
    public static TileType EMPTY_TILE;

    static {
        for (int i = 0; i < 255; i++) {
            createTileType(i, new TileType(true));
        }
        EMPTY_TILE = getTileTypeBySheetPosition(0,0).setSolid(false).setName("empty");
        getTileTypeBySheetPosition(1,0).setSolid(false).setName("room");
        getTileTypeBySheetPosition(2,0).setSolid(false).setLadder(true).setName("ladder");
        getTileTypeBySheetPosition(0,1).setSolid(false).setName("door_frame");
    }

    public static void createTileType(int id, TileType type) {
        TILETYPES_BY_ID.put(id, type);
        type.id = id;
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
    public static TileType getTileTypeBySheetPosition(int x, int y) {
        if (TILETYPES_BY_ID.containsKey(y * 16 + x)) {
            return TILETYPES_BY_ID.get(y * 16 + x);
        } else {
            return TILETYPES_BY_ID.get(255);
        }
    }

    public boolean solid;
    public boolean ladder;
    private String name;
    public int id;

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
    private TileType setName(String name) {
        this.name = name;
        TILETYPES.put(name, this);
        return this;
    }
}
