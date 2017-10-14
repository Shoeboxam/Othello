package com.shoemate.players;
import com.shoemate.Logic;
import java.util.Random;

public class AI_Random extends Player {
    private Logic logic = new Logic();
    private Random rand = new Random();

    public int[] play(Player[][] view, boolean allowDiagonal) {
        int numCandidates = 0;
        int[][] candidates = new int[view.length * view[0].length][2];

        for (int i=0; i < view.length; i++) {
            for (int j=0; j < view[0].length; j++) {
                if (logic.isValid(view, this, new int[] {i, j}, allowDiagonal, false)) {
                    candidates[numCandidates++] = new int[] {i, j};
                }
            }
        }
        return candidates[rand.nextInt(numCandidates)];
    }
}
