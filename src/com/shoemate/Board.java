package com.shoemate;

import com.shoemate.players.Player;
import com.shoemate.players.Unowned;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
            boardData[origin[0] + offsets[i][0]][origin[1] + offsets[i][1]] = players[i % players.length];
        }
    }

    public boolean isFull(Player player) {
        for (int i = 0; i < boardData.length; i++) {
            for (int j=0; j < boardData[0].length; j++) {
                if (logic.isValid(boardData, player, new int[] {i, j}, false, false)) {
                    return false;
                }
            }
        }
        return true;
    }

    protected boolean takeTurn(Player player, int[] tile, boolean allowDiagonal) {
        if (logic.isValid(boardData, player, tile, allowDiagonal, true)) {
            boardData = logic.setTile(boardData, player, tile, allowDiagonal);

            System.out.println("Success: " + player.toString() + " move at (" + Integer.toString(tile[0])+  ", " + Integer.toString(tile[1]) + ")");
            return true;
        }
        return false;
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

        for (int i = 0; i < boardData[0].length; i++) {

            StringBuilder row = new StringBuilder();
            row.append(i + 1);
            for (int j=0; j < boardData.length; j++) {
                row.append(boardData[j][i].getKey());
            }
            whole.append('\n');
            whole.append(row);
        }
        return whole.toString();
    }

    public String getScore(Player[] players) {
        // Scoreboard initialization
        HashMap<Player, Integer> scoreboard = new HashMap<>();
        for (Player player : players) {
            scoreboard.put(player, 0);
        }

        // Populate scoreboard
        for (int i = 0; i < boardData.length; i++) {
            for (int j=0; j < boardData[0].length; j++) {
                // Increment scoreboard value
                if (boardData[i][j] != unowned) {
                    scoreboard.put(boardData[i][j], scoreboard.get(boardData[i][j]) + 1);
                }
            }
        }
        StringBuilder scores = new StringBuilder();
        scores.append("Score: ");

        Iterator<Map.Entry<Player, Integer>> entries = scoreboard.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<Player, Integer> entry = entries.next();
            scores.append(entry.getKey().toString() + ": " + entry.getValue().toString());
            if (entries.hasNext()) {
                scores.append(", ");
            }
        }

        return scores.toString();
    }
}
