package com.shoemate;

import com.shoemate.players.Player;
import com.shoemate.players.Unowned;

public class Board {
    private Player[][] boardData;
    private static Unowned unowned = new Unowned();
    private Logic logic = new Logic();

    public Board(int width, int height) {
        Player[][] temp = new Player[width][height];
        for (int i = 0; i < width; i++) {
            for (int j=0; j < height; j++) {
                temp[i][j] = unowned;
            }
        }
        boardData = temp;
    }

    public void playerInit(Player[] players) {
        int[] origin = new int[] { (boardData.length - 1) / 2, (boardData[0].length - 1) / 2};
        int startingTokens = players.length * 2;
        int[][] offsets = logic.spiralOffsets(startingTokens);

        for (int i=0; i < startingTokens; i++) {
//            System.out.println(Integer.toString(origin[0] + offsets[i][0])+  ", " + Integer.toString(origin[1] + offsets[i][1]));
            boardData[origin[0] + offsets[i][0]][origin[1] + offsets[i][1]] = players[i % players.length];
        }
    }

    public boolean isFull() {
        return false;
    }

    protected boolean takeTurn(Player player, int[] tile, boolean allowDiagonal) {
        if (logic.isValid(boardData, player, tile, allowDiagonal)) {
            System.out.println(Integer.toString(tile[0])+  ", " + Integer.toString(tile[1]));
            boardData = logic.setTile(boardData, player, tile, allowDiagonal);
            return true;
        } else {
            return false;
        }
    }

    public Player[][] getView() {
        Player[][] view = new Player[boardData.length][];
        for (int i = 0; i < boardData.length; i++) view[i] = boardData[i].clone();
        return view;
    }

    public String toString() {
        StringBuilder whole = new StringBuilder();

        whole.append(" ");
        for (int j=0; j < boardData.length; j++) whole.append(j + 1);
        whole.append("\n");

        for (int i = 0; i < boardData[0].length; i++) {

            StringBuilder row = new StringBuilder();
            row.append(i + 1);
            for (int j=0; j < boardData.length; j++) {
                row.append(boardData[j][i]);
            }
            whole.append(row);
            whole.append('\n');
        }
        return whole.toString();
    }
}
