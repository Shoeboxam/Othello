package com.shoemate;

import com.shoemate.players.Player;
import com.shoemate.players.Unowned;

public class Board {
    private Player[][] boardData;
    private static Unowned unowned = new Unowned();

    public Board(int width, int height) {
        Player[][] temp = new Player[width][height];
        for (int i = 0; i < width; i++) {
            for (int j=0; j < height; j++) {
                temp[i][j] = unowned;
            }
        }
        boardData = temp;
    }

    public boolean isFull() {
        return false;
    }

    protected boolean takeTurn(Player player, int[] play, boolean allowDiagonal) {
        if (isValid(play)) {
            boardData[play[0]][play[1]] = player;
            return true;
        } else {
            return false;
        }
    }

    public boolean isValid(int[] play) {
        return true;
    }

    public Player[][] getView() {
        Player[][] view = new Player[boardData.length][];
        for (int i = 0; i < boardData.length; i++) view[i] = boardData[i].clone();
        return view;
    }

    public String toString() {
        StringBuilder whole = new StringBuilder();
        for (int i = 0; i < boardData[0].length; i++) {

            StringBuilder row = new StringBuilder();
            for (int j=0; j < boardData.length; j++) {
                row.append(boardData[i][j]);
            }
            whole.append(row);
            whole.append('\n');
        }
        return whole.toString();
    }
}
