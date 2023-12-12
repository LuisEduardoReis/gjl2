package com.gjl2.entities.interactables;

import com.gjl2.Assets;
import com.gjl2.entities.Entity;
import com.gjl2.entities.Player;

public class Teleporter extends Entity implements Interactable {
    public int ID;
    public float x;
    public float y;
    public Teleporter linkedTeleporter;
    public Teleporter(int id, float x, float y) {
        this.ID = id;
        this.x = x;
        this.y = y;
    }
    public void linkTeleporter(Teleporter t){
        linkedTeleporter = t;
    }
    @Override
    public void interact(Player player) {
        level.gameScreen.playSound(Assets.teleportSound);
        player.teleport(linkedTeleporter.x, linkedTeleporter.y);
    }

    @Override
    public String getHoverMessage() {
        return "Press space to teleport";
    }
}
