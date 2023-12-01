package com.gjl2;

import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.util.LinkedList;
import java.util.List;

import static com.gjl2.TileType.*;

public class Level {


    Player player;
    List<Entity> entities;

    public int width, height;
    Tile[] tiles;
    Tile[] overlayTiles;
    Tile boundaryTile;

    Level() {
        this.entities = new LinkedList<>();

        TiledMap map = new TmxMapLoader(new InternalFileHandleResolver()).load("level.tmx");
        this.width = (int) map.getProperties().get("width");
        this.height = (int) map.getProperties().get("height");

        this.tiles = new Tile[this.width * this.height];
        this.overlayTiles = new Tile[this.width * this.height];
        for (int i = 0; i < this.width * this.height; i++) {
            this.tiles[i] = new Tile(getTileType("empty"));
            this.overlayTiles[i] = new Tile(null);
        }
        this.boundaryTile = new Tile(getTileType("empty"));

        TiledMapTileLayer mapTiles = (TiledMapTileLayer) map.getLayers().get("tiles");
        TiledMapTileLayer overlayTiles = (TiledMapTileLayer) map.getLayers().get("tiles_overlay");
        MapObjects mapObjects = map.getLayers().get("objects").getObjects();

        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                TiledMapTileLayer.Cell cell = mapTiles.getCell(x, y);
                getTile(x,y).x = x;
                getTile(x,y).y = y;
                getTile(x, y).type = getTileTypeById(cell.getTile().getId() - 1);

                TiledMapTileLayer.Cell cellOverlay = overlayTiles.getCell(x, y);
                getTileOverlay(x, y).x = x;
                getTileOverlay(x, y).y = y;
                if (cellOverlay != null) getTileOverlay(x, y).type = getTileTypeById(cellOverlay.getTile().getId() - 1);
            }
        }

        this.player = new Player();
        addEntity(this.player, 0,0);

        if (mapObjects != null) setupMapObjects(mapObjects);
    }

    private void setupMapObjects(MapObjects mapObjects) {
        for (MapObject mapObject : mapObjects) {
            String type = (String) mapObject.getProperties().get("type");

            if ("spawn".equals(type)) {
                this.player.x = (Float) mapObject.getProperties().get("x") / Main.TILE_SIZE;
                this.player.y = (Float) mapObject.getProperties().get("y") / Main.TILE_SIZE;
            } else
            if ("door".equals(type)) {
                Door door = new Door();
                addEntity(door, (Float) mapObject.getProperties().get("x") / Main.TILE_SIZE, (Float) mapObject.getProperties().get("y") / Main.TILE_SIZE);
                getTile(door.x, door.y).door = door;
            }
        }
    }

    private void addEntity(Entity entity, float x, float y) {
        this.entities.add(entity);
        entity.x = x;
        entity.y = y;
        entity.level = this;
    }

    public void update(float delta) {
        for (Entity entity : this.entities) {
            entity.preupdate(delta);
        }
        for (Entity entity : this.entities) {
            entity.update(delta);
        }

        handleEntityCollisions();
        handleLevelCollisions();
    }

    private void handleEntityCollisions() {
        for (Entity e : entities) {
            for (Entity o : entities) {
                if (e == o) continue;
                float dist = Util.pointDistance(e.x, e.y, o.x, o.y);
                if (dist < (e.radius + o.radius)) {
                    e.collide(o);
                }
            }
        }
    }

    private void handleLevelCollisions() {
        for (Entity entity : this.entities) {
            if (!entity.collidesWithLevel) continue;

            int xc = (int) Math.floor(entity.x);
            int yc = (int) Math.floor(entity.y);
            float xr = entity.x - xc;
            float yr = entity.y - yc;

            if (entity.isTileSolid(getTile(xc, yc))) {
                entity.x = entity.px;
                entity.y = entity.py;
                entity.vy = 0;
                entity.vx = 0;
            }

            if (entity.isTileSolid(getTile(xc - 1, yc)) && xr < entity.radius) {
                xr = entity.radius;
                entity.vx = 0;
                entity.x = xc + xr;
            }
            if (entity.isTileSolid(getTile(xc + 1, yc)) && xr > 1 - entity.radius) {
                xr = 1 - entity.radius;
                entity.vx = 0;
                entity.x = xc + xr;
            }

            if (entity.isTileSolid(getTile(xc, yc - 1)) && yr < entity.radius) {
                yr = entity.radius;
                entity.vy = 0;
                entity.y = yc + yr;
            }
            if (entity.isTileSolid(getTile(xc, yc + 1)) && yr > 1 - entity.radius) {
                yr = 1 - entity.radius;
                entity.vy = 0;
                entity.y = yc + yr;
            }
        }
    }

    public void renderSprites(SpriteBatch spriteBatch) {
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                Tile tile = this.getTile(x, y);
                spriteBatch.draw(Assets.getTileTextureById(tile.type.id), x, y, 1, 1);

                Tile overlayTile = this.getTileOverlay(x, y);
                if (overlayTile.type != null) spriteBatch.draw(Assets.getTileTextureById(overlayTile.type.id), x, y, 1, 1);
            }
        }

        for (Entity entity : this.entities) {
            entity.renderSprites(spriteBatch);
        }
    }

    public void renderShapes(ShapeRenderer shapeRenderer) {
        /*for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                Tile tile = this.getTile(x,y);
                shapeRenderer.setColor(tile.type.solid ? Color.BLACK : Color.GRAY);
                shapeRenderer.rect(x, y, 1,1);
            }
        }*/

        for (Entity entity : this.entities) {
            entity.renderShapes(shapeRenderer);
        }
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || x >= this.width || y < 0 || y >= this.height) return this.boundaryTile;
        return this.tiles[y * this.width + x];
    }

    public Tile getTile(float x, float y) {
        return getTile((int) Math.floor(x), (int) Math.floor(y));
    }

    public Tile getTileOverlay(int x, int y) {
        if (x < 0 || x >= this.width || y < 0 || y >= this.height) return this.boundaryTile;
        return this.overlayTiles[y * this.width + x];
    }
}