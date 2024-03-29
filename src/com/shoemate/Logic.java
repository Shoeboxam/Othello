package com.shoemate;

import com.shoemate.players.Player;
import com.shoemate.players.Unowned;

public class Logic {

    private static final int[][] CARDINAL_OFFSET = new int[][] {
            new int[] {0, -1},
            new int[] {1, 0},
            new int[] {0, 1},
            new int[] {-1, 0}};

    private static final int[][] DIAGONAL_OFFSET = new int[][] {
            new int[] {-1, -1},
            new int[] {1, -1},
            new int[] {-1, 1},
            new int[] {1, 1}};

    // Check if game rules are satisfied if a player attempts to act on a tile
    public static boolean isValid(Player[][] boardData, Player player, int[] play, boolean allowDiagonal, boolean debug) {
        //The debug argument prints the reason why a tile is not selected.

        if (!(boardData[play[0]][play[1]] instanceof Unowned)) {
            if (debug) {
                System.out.println("(" + Integer.toString(play[0] + 1) + "," + Integer.toString(play[1] + 1) + ") is already owned.");
            }
            return false;
        }

        boolean hasNeighbor = false;

        // ---- Cardinal Neighbors ----
        for (int[] offset : CARDINAL_OFFSET) {
            int[] neighbor = new int[] {play[0] + offset[0], play[1] + offset[1]};
            if (exists(boardData, neighbor) && boardData[neighbor[0]][neighbor[1]] == player){
                if (debug) {
                    System.out.println("Tiles may not be placed next to your own tile.");
                }
                return false;
            }
            if (exists(boardData, neighbor) && !(boardData[neighbor[0]][neighbor[1]] instanceof Unowned)) hasNeighbor = true;
        }

        // ---- Diagonal Neighbors ----
        if (allowDiagonal) {
            for (int[] offset : DIAGONAL_OFFSET) {
                int[] neighbor = new int[] {play[0] + offset[0], play[1] + offset[1]};
                if (exists(boardData, neighbor) && !(boardData[neighbor[0]][neighbor[1]] instanceof Unowned)) hasNeighbor = true;
            }
        }

        if (!hasNeighbor && debug) {
            System.out.println("Tiles must be placed next to an opponents tile.");
        }
        return hasNeighbor;
    }

    public static boolean setTile(Player[][] boardData, Player player, int[] tile, boolean allowDiagonal) {
        // Invalid requests are ignored
        if (!isValid(boardData, player, tile, allowDiagonal, true)) return false;

        // Assign tile
        boardData[tile[0]][tile[1]] = player;

        // Assign all neighboring tiles to satisfy game rules
        for (int[] offset : CARDINAL_OFFSET) {
            if (search(boardData, player, tile, offset)) {
                replace(boardData, player, tile, offset);
            }
        }

        if (allowDiagonal) {
            for (int[] offset : DIAGONAL_OFFSET) {
                int[] neighbor = new int[] {tile[0] + offset[0], tile[1] + offset[1]};
                if (search(boardData, player, neighbor, offset)) {
                    replace(boardData, player, tile, offset);
                }
            }
        }
        return true;
    }

    // RECURSIVE OPERATIONS
    // search and replace acts on offsets until a matching element is found.
    // For this game, the offsets passed always follow rows, columns or diagonals.
    private static boolean search(Player[][] boardData, Player player, int[] tile, int[] offset) {
        int[] neighbor = new int[] {tile[0] + offset[0], tile[1] + offset[1]};

        if (!exists(boardData, neighbor)) return false;
        if (boardData[neighbor[0]][neighbor[1]] instanceof Unowned) return false;
        if (boardData[neighbor[0]][neighbor[1]] == player) return true;

        return search(boardData, player, neighbor, offset);
    }

    private static void replace(Player[][] boardData, Player player, int[] tile, int[] offset) {
        int[] neighbor = new int[] {tile[0] + offset[0], tile[1] + offset[1]};
        if (boardData[neighbor[0]][neighbor[1]] == player) return;

        boardData[neighbor[0]][neighbor[1]] = player;
        replace(boardData, player, neighbor, offset);
    }

    // Sanity check to avoid indexing outside of the game board
    private static boolean exists(Player[][] boardData, int[] tile) {
        if (tile[0] < 0 || tile[0] >= boardData.length) return false;
        if (tile[1] < 0 || tile[1] >= boardData[1].length) return false;

        return true;
    }

    // Helper function to determine initial positioning of player tokens
    // Allows game init to handle 3+ players gracefully
    static int[][] spiralOffsets(int length) {
        int[][] offsets = new int[length][2];

        int elem = 0;
        int layer = 0;
        int[] cursor = new int[] {0, 0};
        while (true) {
            int shell_length = layer * 2 + 1;

            for (int card=0; card < 4; card++) {
                for (int j=0; j < shell_length; j++) {
                    offsets[elem++] = cursor.clone();

                    if (j < shell_length - 1) {
                        cursor[0] += CARDINAL_OFFSET[card][0];
                        cursor[1] += CARDINAL_OFFSET[card][1];
                    }
                    if (elem == length) return offsets;
                }
                if (card != 3) {
                    cursor[0] += CARDINAL_OFFSET[(card + 1) % 4][0];
                    cursor[1] += CARDINAL_OFFSET[(card + 1) % 4][1];
                }
            }
            cursor[0] += CARDINAL_OFFSET[3][0];
            cursor[1] += CARDINAL_OFFSET[3][1];
            layer++;
        }
    }
}
