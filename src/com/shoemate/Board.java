package com.shoemate;

import com.shoemate.players.Player;
import com.shoemate.players.Unowned;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class Board {
    private Player[][] boardData;
    private static Unowned unowned = new Unowned();

    Board(int width, int height) {
        // initialize with all tiles unowned
        Player[][] temp = new Player[width][height];
        for (int i = 0; i < width; i++) {
            for (int j=0; j < height; j++) {
                temp[i][j] = unowned;
            }
        }
        boardData = temp;
    }

    // place starting pieces at center of board
    boolean playerInit(Player[] players) {

        // If there is an attempt to index outside of the board, raise an alert
        try {
            // Starting at the origin, add offsets for each initial tile
            int[] origin = new int[] { (boardData.length - 1) / 2, (boardData[0].length - 1) / 2};
            int startingTokens = players.length * 2;
            int[][] offsets = Logic.spiralOffsets(startingTokens);

            for (int i=0; i < startingTokens; i++) {
                boardData[origin[0] + offsets[i][0]][origin[1] + offsets[i][1]] = players[i % players.length];
            }
            return true;

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("A " + Integer.toString(boardData.length) + "x" + Integer.toString(boardData[0].length) +
                    " game board is too small for " + Integer.toString(players.length) + " players.");

            return false;
        }
    }

    // Check if player can place tiles
    boolean isFull(Player player, boolean allowDiagonal) {
        for (int i=0; i < boardData.length; i++) {
            for (int j=0; j < boardData[0].length; j++) {
                if (Logic.isValid(boardData, player, new int[] {i, j}, allowDiagonal, false)) {
                    return false;
                }
            }
        }
        return true;
    }

    boolean takeTurn(Player player, int[] tile, boolean allowDiagonal) {
        // If game rules are not violated, then place the tile
        if (Logic.setTile(boardData, player, tile, allowDiagonal)) {
            System.out.println("Success: " + player.toString() + " move at (" + Integer.toString(tile[0])+  ", " + Integer.toString(tile[1]) + ")");
            return true;
        }
        return false;
    }

    // Players don't actually have access to edit the gameboard; they may only submit their play
    // Nevertheless, players need to be able to see the board. This gives a readable copy of the game state
    Player[][] getView() {
        Player[][] view = new Player[boardData.length][];
        for (int i = 0; i < boardData.length; i++) view[i] = boardData[i].clone();
        return view;
    }

    // Labeled view of board, with one character per tile
    public String toString() {
        StringBuilder whole = new StringBuilder();

        whole.append(" ");
        for (int j=0; j < boardData.length; j++) whole.append((j + 1) % 10);

        for (int i = 0; i < boardData[0].length; i++) {

            StringBuilder row = new StringBuilder();
            row.append((i + 1) % 10);
            for (int j=0; j < boardData.length; j++) {
                row.append(boardData[j][i].getKey());
            }
            whole.append('\n');
            whole.append(row);
        }
        return whole.toString();
    }

    String getScore(Player[] players) {
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
