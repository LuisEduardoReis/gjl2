package com.gjl2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.util.LinkedList;
import java.util.List;

import static com.gjl2.Util.isBetween;

public class GameEvents {
    Level level;

    List<Runnable> events = new LinkedList<>();
    List<Timer> timers = new LinkedList<>();

    float timeToNextAsteroid = -1;

    float timeToEvent;
    public GameEvents(Level level) {
        this.level = level;
        timeToEvent = 10f;

        events.add(this::asteroidEvent);
        events.add(this::eventAlienInvasion);
        events.add(this::shipLost);
        events.add(this::engineOverload);
    }

    public void update(float delta){
        timeToEvent = Util.stepTo(timeToEvent, 0, 1*delta);
        if (timeToEvent == 0) {
            rollEvent();
            timeToEvent =  Util.randomRange(15, 30);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) this.rollEvent();
        for (Timer t : timers) {
            t.update(delta);
        }

        if (timeToNextAsteroid > 0) {
            timeToNextAsteroid = Util.stepTo(timeToNextAsteroid, 0, delta);
            if (timeToNextAsteroid == 0) {
                eventAsteroidHit();
                timeToNextAsteroid = -1;
            }
        }

    }

    private void rollEvent(){
        int roll = (int) Math.floor(Util.randomRange(0, events.size()));
        events.get(roll).run();
    }

    private void asteroidEvent() {
        this.level.gameScreen.hud.addWarning("Incoming Asteroid!");
        this.timeToNextAsteroid = 5;
    }

    private void eventAsteroidHit() {
        if (level.shipState.shieldState >= 100) {
            level.shipState.shieldState = 0;
            this.level.gameScreen.hud.addWarning("Asteroid deflected!");
        } else {
            level.shipState.shieldState = 0;
            this.level.gameScreen.hud.addWarning("Asteroid hit!");

            for (int i = 0; i < 100; i++) {
                float x = (float) (Math.floor(Util.randomRange(0, this.level.width)) + 0.5f);
                float y = (float) (Math.floor(Util.randomRange(0, this.level.height)) + 0.5f);

                Tile tile = this.level.getTile(x, y);
                if (tile.type != TileType.getTileType("room")) continue;

                Tile tileBelow = this.level.getTile(x, y - 1);
                if (!tileBelow.type.solid) continue;

                Tile overlayTile = this.level.getTileOverlay(x, y);
                if (overlayTile.type != null) continue;

                this.level.addEntity(new AsteroidHit(), x, y);
                this.level.shipState.hullStatus = Math.max(0, this.level.shipState.hullStatus - 10);
                break;
            }
        }
    }

    private void eventAlienInvasion(){
        for (int i = 0; i < 100; i++) {
            float x = (float) (Math.floor(Util.randomRange(0, this.level.width)) + 0.5f);
            float y = (float) (Math.floor(Util.randomRange(0, this.level.height)) + 0.5f);

            Tile tile = this.level.getTile(x, y);
            if (tile.type != TileType.getTileType("room")) continue;

            Tile tileBelow = this.level.getTile(x, y - 1);
            if (!tileBelow.type.solid) continue;

            Tile overlayTile = this.level.getTileOverlay(x, y);
            if (overlayTile.type != null) continue;

            this.level.gameScreen.hud.addWarning("Aliens invaded!");
            for (int j = 0; j < 4; j++) {
                this.level.addEntity(new Alien(), x + j*0.01f,y);
            }

            break;
        }
    }

    private void shipLost() {
        this.level.gameScreen.hud.addWarning("Navigation failed!");
        level.shipState.lost = true;
    }

    private void engineOverload() {
        this.level.gameScreen.hud.addWarning("Engine overloaded!");
        level.shipState.engineOverloaded = true;
    }
}
