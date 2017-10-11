package com.shoemate;

import com.shoemate.Players.Player;

public class Board {
    private Player[][] internal;

    public Board(int width, int height) {
        private Player[][] = new Player[width][height]();

    }

    public boolean isFull() {
        return false;
    }

    public boolean takeTurn(Player player, int xVal, int yVal) {
        if (isValid(xVal, yVal)) {
            internal[xVal][yVal] = player;
            return true;
        } else {
            return false;
        }
    }

    public boolean isValid(int xVal, int Yval) {
        return true;
    }
}
