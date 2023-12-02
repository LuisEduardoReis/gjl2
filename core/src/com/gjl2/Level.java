package com.gjl2;

import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static com.gjl2.TileType.*;

public class Level {

    public static final int NUM_STARS = 2000;
    public static final int STARS_BOUNDARY = 6;
    GameScreen gameScreen;
    Player player;
    List<Entity> entities;
    List<Entity> newEntities;
    List<Vector3> stars = new ArrayList<>(NUM_STARS);

    public int width, height;
    Tile[] tiles;
    Tile[] overlayTiles;
    Tile boundaryTile;

    GameEvents gameEvents;

    ShipState shipState;

    public boolean gameOver;

    Level(GameScreen gameScreen) {
        this.gameScreen = gameScreen;

        this.gameEvents = new GameEvents(this);
        this.shipState = new ShipState(this);
        this.entities = new LinkedList<>();
        this.newEntities = new LinkedList<>();

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
                if (cell != null) {
                    getTile(x, y).type = getTileTypeById(cell.getTile().getId() - 1);
                } else {
                    getTile(x,y).type = getTileType("empty");
                }

                TiledMapTileLayer.Cell cellOverlay = overlayTiles.getCell(x, y);
                getTileOverlay(x, y).x = x;
                getTileOverlay(x, y).y = y;
                if (cellOverlay != null) getTileOverlay(x, y).type = getTileTypeById(cellOverlay.getTile().getId() - 1);
            }
        }

        this.player = new Player();
        addEntity(this.player, 0,0);

        if (mapObjects != null) setupMapObjects(mapObjects);

        for (int i = 0; i < NUM_STARS; i++) {
            this.stars.add(new Vector3(
                Util.randomRange(-STARS_BOUNDARY, width + STARS_BOUNDARY),
                Util.randomRange(-STARS_BOUNDARY, height + STARS_BOUNDARY),
                Util.randomRange(0.25f, 1)
            ));
        }
    }

    private void setupMapObjects(MapObjects mapObjects) {
        for (MapObject mapObject : mapObjects) {
            String type = (String) mapObject.getProperties().get("type");

            float ox = (Float) mapObject.getProperties().get("x") / Main.TILE_SIZE;
            float oy = (Float) mapObject.getProperties().get("y") / Main.TILE_SIZE;

            if ("spawn".equals(type)) {
                this.player.x = ox;
                this.player.y = oy;
            } else
            if ("door".equals(type)) {
                Door door = new Door();
                addEntity(door, ox, oy);
                getTile(door.x, door.y).door = door;
            } else
            if ("oxygen-room".equals(type)) {
                addEntity(new OxygenRoom(), ox, oy);
            } else
            if ("shields-room".equals(type)) {
                addEntity(new ShieldRoom(), ox, oy);
            } else
            if ("navigation-room".equals(type)) {
                addEntity(new NavigationRoom(), ox, oy);
            } else
            if ("engine-room".equals(type)) {
                addEntity(new EngineRoom(), ox, oy);
            }
        }
    }

    public void addEntity(Entity entity, float x, float y) {
        this.newEntities.add(entity);
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

        this.gameEvents.update(delta);
        this.shipState.update(delta);

        this.entities.addAll(newEntities);
        this.newEntities.clear();

        handleEntityCollisions(delta);
        handleLevelCollisions();

        if (this.entities.stream().anyMatch(e -> e.remove)) {
            this.entities = this.entities.stream().filter(e -> !e.remove).collect(Collectors.toList());
        }

        if (this.shipState.hullStatus == 0 || this.shipState.oxygenLevel == 0 || this.player.health == 0) {
            if (!Main.DEBUG) gameOver = true;
        }

        for (Vector3 star : this.stars) {
            star.x += star.z * delta * 0.25f;
            if (star.x > this.width + STARS_BOUNDARY) star.x = -STARS_BOUNDARY;
        }
    }

    private void handleEntityCollisions(float delta) {
        for (Entity e : entities) {
            if (!e.collidesWithOthers) continue;
            for (Entity o : entities) {
                if (!o.collidesWithOthers) continue;
                if (e == o) continue;
                float dist = Util.pointDistance(e.x, e.y, o.x, o.y);
                if (dist < (e.radius + o.radius)) {
                    e.collide(o, delta);
                    if (e.pushesOthers && o.pushesOthers) {
                        float dx = (e.x - o.x) / dist;
                        float dy = (e.y - o.y) / dist;
                        o.x -= dx * (e.radius + o.radius - dist) * 0.5f;
                        o.y -= dy * (e.radius + o.radius - dist) * 0.5f;
                    }
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
                entity.handleLevelCollision(0, 0);
            }

            if (entity.isTileSolid(getTile(xc - 1, yc)) && xr < entity.radius) {
                xr = entity.radius;
                entity.vx = 0;
                entity.x = xc + xr;

                entity.handleLevelCollision(-1, 0);
            }
            if (entity.isTileSolid(getTile(xc + 1, yc)) && xr > 1 - entity.radius) {
                xr = 1 - entity.radius;
                entity.vx = 0;
                entity.x = xc + xr;

                entity.handleLevelCollision(+1, 0);
            }

            if (entity.isTileSolid(getTile(xc, yc - 1)) && yr < entity.radius) {
                yr = entity.radius;
                entity.vy = 0;
                entity.y = yc + yr;

                entity.handleLevelCollision(0, +1);
            }
            if (entity.isTileSolid(getTile(xc, yc + 1)) && yr > 1 - entity.radius) {
                yr = 1 - entity.radius;
                entity.vy = 0;
                entity.y = yc + yr;

                entity.handleLevelCollision(0, 1);
            }
        }
    }

    public void renderSprites(SpriteBatch spriteBatch) {
        for (Vector3 star : stars) {
            spriteBatch.draw(Assets.starSprite, star.x, star.y, 0.25f * star.z, 0.25f * star.z);
        }

        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                Tile tile = this.getTile(x, y);
                if (tile.type != EMPTY_TILE) spriteBatch.draw(Assets.getTileTextureById(tile.type.id), x, y, 1, 1);

                Tile overlayTile = this.getTileOverlay(x, y);
                if (overlayTile.type != null) spriteBatch.draw(Assets.getTileTextureById(overlayTile.type.id), x, y, 1, 1);
            }
        }

        entities.sort((a,b) -> Float.compare(a.z, b.z));
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

    public void renderDebug(ShapeRenderer shapeRenderer) {
        for (Entity entity : this.entities) {
            entity.renderDebug(shapeRenderer);
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

    public Tile getTileOverlay(float x, float y) {
        return getTileOverlay((int) Math.floor(x), (int) Math.floor(y));
    }
}
