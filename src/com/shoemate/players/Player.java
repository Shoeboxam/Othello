package com.shoemate.players;

public abstract class Player implements Comparable {
    static private int numPlayers = 0;
    static private String[] colorOptions = new String[] {"_unowned", "Black", "White", "Orange", "Green", "Yellow", "Purple", "Grey", "Taupe"};
    private String color;

    public Player() {
        color = colorOptions[numPlayers++];
    }

    public String getKey() {
        return Character.toString(color.charAt(0));
    }

    public abstract int[] play(Player[][] board, boolean allowDiagonal);
    public String toString() {
        return this.color;
    }

    public boolean equals(Object other) {
        return other instanceof Player && getKey().equals(other.toString());
    }

    public int compareTo(Object other) {
        return equals(other) ? 1 : 0;
    }
}
