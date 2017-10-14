package com.shoemate;

import com.shoemate.players.AI_Greedy;
import com.shoemate.players.AI_Random;
import com.shoemate.players.Person;
import com.shoemate.players.Player;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.Function;

public class Game {

    private Scanner scan = new Scanner(System.in);

    private static int iteration = 0;
    private Player[] players;
    private Board gameboard;
    private boolean allowDiagonal = false;

    public Game() {
        // Initialize boards
        int width = getInt("Board width: ", (a) -> a>0, "Width must be positive.");
        int height = getInt("Board height: ", (a) -> a>0, "Height must be positive.");

        gameboard = new Board(width, height);

        // Initialize players
        Player[] temp = new Player[8];

        boolean addPlayers = true;
        int numPlayers = 0;
        System.out.println("Select up to eight players.");
        while ((addPlayers || numPlayers < 2) && numPlayers < 8) {
            System.out.println("1. Add human            (Player " + Integer.toString(numPlayers + 1) + ")");
            System.out.println("2. Add greedy computer  (AI " + Integer.toString(numPlayers + 1) + ")");
            System.out.println("3. Add random computer  (AI " + Integer.toString(numPlayers + 1) + ")");
            System.out.println("4. Start game!");
            int selection = getInt("Selection: ", (a) -> a>=0 && a <=4, "Please enter a menu option: {1, 2, 3, 4}");

            if (selection == 1) {
                temp[numPlayers] = new Person();
            } else if (selection == 2) {
                temp[numPlayers] = new AI_Greedy();
            } else if (selection == 3) {
                temp[numPlayers] = new AI_Random();
            } else if (selection == 4) {
                addPlayers = false;
            }
            numPlayers++;
        }
        System.out.println(temp[0]);
        players = Arrays.copyOfRange(temp, 0, numPlayers - 1).clone();

        System.out.println(players[0]);
        gameboard.playerInit(players);
    }

    public void start() {
        Player currentPlayer = players[0];

        do {
            System.out.println(gameboard);
            System.out.println("Score: " + gameboard.getScore(players));
            System.out.println(currentPlayer.toString() + " turn:");
            Player[][] view = gameboard.getView();

            // Repeatedly ask for a play until a valid play is found
            while (!gameboard.takeTurn(currentPlayer, currentPlayer.play(view, allowDiagonal), allowDiagonal));

            // Increment player
            iteration++;
            currentPlayer = players[iteration % players.length];
            System.out.println();

        } while (!gameboard.isFull(currentPlayer));

        System.out.println("---- Game Over ----");
        System.out.println(gameboard);
        System.out.println("No moves found for " + currentPlayer.toString());
        System.out.println("Final score: " + gameboard.getScore(players));
    }

    private int getInt(String prompt, Function<Integer, Boolean> check, String promptFail) {
        while (true) {
            try {
                System.out.print(prompt);
                int candidate = scan.nextInt();

                if (check.apply(candidate)) {
                    return candidate;
                } else {
                    System.out.println(promptFail);
                }
            } catch (InputMismatchException e) {
                System.out.println("Input must be a whole number. Try again.");
                scan.next();
            }
        }
    }
}
