package com.shoemate.players;

import com.shoemate.Logic;

import java.util.Arrays;
import java.util.Collections;

public class AI_Greedy extends Player {
    private Logic logic = new Logic();

    public int[] play(Player[][] view, boolean allowDiagonal) {
        int numCandidates = 0;
        int[][] full_candidates = new int[view.length * view[0].length][2];

        for (int i=0; i < view.length; i++) {
            for (int j=0; j < view[0].length; j++) {
                if (logic.isValid(view, this, new int[] {i, j}, allowDiagonal, false)) {
                    full_candidates[numCandidates++] = new int[] {i, j};
                }
            }
        }
        int[][] candidates = Arrays.copyOfRange(full_candidates, 0, numCandidates);
        Collections.shuffle(Arrays.asList(candidates));

        int[] choice = candidates[0];
        int fitness = 0;

        for (int i=0; i<numCandidates; i++) {
            Player [][] copy = new Player[view.length][view[0].length];
            for(int j = 0; j < view.length; j++) copy[j] = view[j].clone();

            Player[][] peek = logic.setTile(copy, this, candidates[i], allowDiagonal);

            // Replace choice if candidate has better fitness
            int candidate_fitness = getFitness(peek);
            if (candidate_fitness > fitness) {
                fitness = candidate_fitness;
                choice = candidates[i];
            }
        }

        return choice;
    }

    private int getFitness(Player[][] view) {
        int fitness = 0;
        for (int i = 0; i < view.length; i++) {
            for (int j=0; j < view[0].length; j++) {
                // Increment scoreboard value
                if (view[i][j] == this) fitness++;
            }
        }
        return fitness;
    }
}
