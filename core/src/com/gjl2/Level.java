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
        for (int i = 0; i < this.width * this.height; i++) {
            this.tiles[i] = new Tile(getTileType("wall"));
        }
        this.boundaryTile = new Tile(getTileType("wall"));

        //Init overlay matrix to nil
        this.overlayTiles = new Tile[this.width * this.height];
        for (int i = 0; i < this.width * this.height; i++) {
            this.overlayTiles[i] = new Tile(null);
        }

        //Load positions of items in the overlay
        TiledMapTileLayer overlayTiles = (TiledMapTileLayer) map.getLayers().get("tiles_overlay");


        TiledMapTileLayer mapTiles = (TiledMapTileLayer) map.getLayers().get("tiles");
        MapObjects mapObjects = map.getLayers().get("objects").getObjects();

        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                TiledMapTileLayer.Cell cell = mapTiles.getCell(x, y);
                getTile(x, y).type = getTileTypeById(cell.getTile().getId() - 1);

                TiledMapTileLayer.Cell cellOverlay = overlayTiles.getCell(x, y);
                if (cellOverlay != null) getTileOverlay(x, y).type = getTileTypeById(cellOverlay.getTile().getId() - 1);
            }
        }

        MapObject spawn = mapObjects.get("spawn");
        this.player = new Player();
        addEntity(this.player, (Float) spawn.getProperties().get("x") / 16, (Float) spawn.getProperties().get("y") / 16);
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
        handleCollisions();
    }

    private void handleCollisions() {
        for (Entity entity : this.entities) {
            if (!entity.collidesWithLevel) continue;

            int xc = (int) Math.floor(entity.x);
            int yc = (int) Math.floor(entity.y);
            float xr = entity.x - xc;
            float yr = entity.y - yc;

            if (getTile(xc, yc).type.solid) {
                entity.x = entity.px;
                entity.y = entity.py;
            }

            if (getTile(xc - 1, yc).type.solid && xr < entity.radius) {
                xr = entity.radius;
                entity.vx = 0;
                entity.x = xc + xr;
            }
            if (getTile(xc + 1, yc).type.solid && xr > 1 - entity.radius) {
                xr = 1 - entity.radius;
                entity.vx = 0;
                entity.x = xc + xr;
            }

            Tile tileBelow = getTile(xc, yc - 1);
            if (entity.isTileSolid(tileBelow.type, xc, yc - 1) && yr < entity.radius) {
                yr = entity.radius;
                entity.vy = 0;
                entity.y = yc + yr;
            }
            if (getTile(xc, yc + 1).type.solid && yr > 1 - entity.radius) {
                yr = 1 - entity.radius;
                entity.vy = 0;
                entity.y = yc + yr;
            }
        }
    }

    public void renderSprites(SpriteBatch spriteBatch) {
        for (Entity entity : this.entities) {
            entity.renderSprites(spriteBatch);
        }

        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                Tile tile = this.getTile(x, y);
                spriteBatch.draw(Assets.getTileTextureById(tile.type.id), x, y, 1, 1);

                Tile overlayTile = this.getTileOverlay(x, y);
                if (overlayTile.type != null) spriteBatch.draw(Assets.getTileTextureById(overlayTile.type.id), x, y, 1, 1);
            }
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