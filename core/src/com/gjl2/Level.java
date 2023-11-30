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

    Level() {
        this.entities = new LinkedList<>();

        this.width = 16;
        this.height = 16;
        this.tiles = new Tile[this.width * this.height];

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
            entity.update(delta);
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
        return this.tiles[y * this.width + x];
    }
}
