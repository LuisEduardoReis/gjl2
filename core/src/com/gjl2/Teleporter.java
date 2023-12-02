package com.gjl2;

public class Teleporter extends Entity implements Interactable {
    public int ID;
    float x;
    float y;
    Teleporter linkedTeleporter;
    Teleporter(int id, float x, float y) {
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
