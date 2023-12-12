package com.gjl2.entities.interactables;

import com.gjl2.entities.Player;

public interface Interactable {
    default void interact(Player player) {};

    default void interactHold(Player player, float delta) {};

    String getHoverMessage();
}
