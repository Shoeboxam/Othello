package com.shoemate.players;

public abstract class Player {
    static private int numPlayers = 0;
    static private String[] colorOptions = new String[] {"_void", "Black", "White", "Orange", "Green", "Yellow", "Purple", "Grey", "Taupe"};
    private String color;

    public Player() {
        color = colorOptions[numPlayers++];
    }

    public String getColor() {
        return this.color;
    }

    public abstract int[] play(Player[][] board);
    public String toString() {
        return Character.toString(color.charAt(0));
    }
}
