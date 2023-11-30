package com.gjl2;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.LinkedList;
import java.util.List;

public class Level {


    Player player;
    List<Entity> entities;

    public int width, height;
    Tile[] tiles;
    Tile boundaryTile;

    Level() {
        this.entities = new LinkedList<>();

        this.width = 16;
        this.height = 16;
        this.tiles = new Tile[this.width * this.height];
        this.boundaryTile = new Tile(TileType.WALL);

        for (int i = 0; i < this.width * this.height; i++) {
            this.tiles[i] = new Tile(Math.random() > 0.5 ? TileType.WALL : TileType.ROOM);
        }

        this.player = new Player();
        addEntity(this.player, 0,0);
    }

    private void addEntity(Entity entity, int x, int y) {
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

            if (getTile(xc, yc - 1).type.solid && yr < entity.radius) {
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
    }

    public void renderShapes(ShapeRenderer shapeRenderer) {
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.height; x++) {
                Tile tile = this.getTile(x,y);
                shapeRenderer.setColor(tile.type.solid ? Color.BLACK : Color.GRAY);
                shapeRenderer.rect(x, y, 1,1);
            }
        }

        for (Entity entity : this.entities) {
            entity.renderShapes(shapeRenderer);
        }
    }

    private Tile getTile(int x, int y) {
        if (x < 0 || x >= this.width || y < 0 || y >= this.height) return this.boundaryTile;
        return this.tiles[y * this.width + x];
    }
}
