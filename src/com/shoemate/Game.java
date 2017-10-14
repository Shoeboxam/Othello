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
        // Handle user preferences
        int width = getInt("Board width: ", (a) -> a>0, "Width must be positive.");
        int height = getInt("Board height: ", (a) -> a>0, "Height must be positive.");

        allowDiagonal = getInt("Game Mode\n 1. Standard\n 2. Diagonal\nSelection: ",
                (a) -> a==1 || a==2, "Please enter a menu option: {1, 2}") == 2;

        gameboard = new Board(width, height);

        // Initialize players
        Player[] temp = new Player[8];

        boolean addPlayers = true;
        int numPlayers = 0;
        System.out.println("Select between two and eight players.");
        while ((addPlayers || numPlayers < 2) && numPlayers < 8) {
            System.out.println(" 1. Add human            (Player " + Integer.toString(numPlayers + 1) + ")");
            System.out.println(" 2. Add greedy computer  (AI " + Integer.toString(numPlayers + 1) + ")");
            System.out.println(" 3. Add random computer  (AI " + Integer.toString(numPlayers + 1) + ")");

            int selection;
            if (numPlayers < 2) {
                selection = getInt("Selection: ", (a) -> a>=0 && a <=3, "Please enter a menu option: {1, 2, 3}");
            } else {
                System.out.println(" 4. Start game!");
                selection = getInt("Selection: ", (a) -> a>=0 && a <=4, "Please enter a menu option: {1, 2, 3, 4}");
            }

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

        players = Arrays.copyOfRange(temp, 0, numPlayers - 1).clone();

        // Place starting elements in center of board
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

            // End when board is full
        } while (!gameboard.isFull(currentPlayer, allowDiagonal));

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

                // constraints are handled via a passed lambda function
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
