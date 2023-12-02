package com.gjl2;

public interface Interactable {
    default void interact(Player player) {};

    default void interactHold(Player player, float delta) {};

    String getHoverMessage();
}
