package com.shoemate.players;
import com.shoemate.Logic;
import java.util.Random;

public class AI_Random extends Player {
    private Random rand = new Random();

    public int[] play(Player[][] view, boolean allowDiagonal) {
        // Collect all valid tiles in the candidates list
        int numCandidates = 0;
        int[][] candidates = new int[view.length * view[0].length][2];

        for (int i=0; i < view.length; i++) {
            for (int j=0; j < view[0].length; j++) {
                if (Logic.isValid(view, this, new int[] {i, j}, allowDiagonal, false)) {
                    candidates[numCandidates++] = new int[] {i, j};
                }
            }
        }

        // Choose and return one random acceptable tile
        return candidates[rand.nextInt(numCandidates)];
    }
}
