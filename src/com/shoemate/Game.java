package com.shoemate;

import com.shoemate.players.Computer;
import com.shoemate.players.Person;
import com.shoemate.players.Player;

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
        int numPeople = getInt("Human players: ", (a) -> a>=0, "Human players must be non-negative.");
        int numComputers =  getInt("Computer players: ", (a) -> a>=0, "Computer players must be non-negative.");

        Player[] temp = new Player[numPeople + numComputers];
        int idx = 0;
        for (; idx < numPeople; idx++) {
            temp[idx] = new Person();
        }
        for (; idx < numComputers; idx++) {
            temp[idx] = new Computer();
        }
        players = temp;
    }

    public void start() {
        iteration++;

        while (!gameboard.isFull()) {
            System.out.println(gameboard);

            Player currentPlayer = players[iteration % players.length];
            Player[][] view = gameboard.getView();

            // Repeatedly ask for a play until a valid play is found
            while (!gameboard.takeTurn(currentPlayer, currentPlayer.play(view), allowDiagonal));
        }
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
