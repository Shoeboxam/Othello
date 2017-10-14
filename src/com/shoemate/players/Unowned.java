package com.shoemate.players;

// The 'Unowned' player starts off owning all places.
public class Unowned extends Player {

    public int[] play(Player[][] board, boolean allowDiagonal) {
        throw new RuntimeException("The unowned player should never get a chance to play!");
    }
}
