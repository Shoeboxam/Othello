package com.shoemate.Players;

import com.shoemate.Board;

public abstract class Player {
    private Board board;
    private static int numPlayers = 0;
    private int ID;

    public Player(Board board) {
        // Player needs access to board in order to play
        this.board = board;
        this.ID = numPlayers++;
    }

    public abstract boolean play();
    public abstract String toString();
}
