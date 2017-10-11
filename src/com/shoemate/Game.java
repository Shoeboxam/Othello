package com.shoemate;

import com.shoemate.Players.Computer;
import com.shoemate.Players.Person;
import com.shoemate.Players.Player;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {

    private static int iteration = 0;
    private Player[] players;
    private Board gameboard;
    private boolean allowDiagonal;

    public Game() {

        // ~~~~~ Game Settings ~~~~~
        boolean accepted = false;

        int width = getInt("Board width: ");
        int height = getInt("Board height: ");

        int numPeople = 0;
        int numComputers = 0;

        // ~~~~~ Variable Initialization ~~~~~

        // Initialize players
        Player[] temp = new Player[numPeople + numComputers];
        int idx = 0;
        for (; idx < numPeople; idx++) {
            temp[idx] = new Person();
        }
        for (; idx < numComputers; idx++) {
            temp[idx] = new Computer();
        }
        players = temp;

        // Initialize board
        gameboard = new Board(width, height);
    }

    public void gameLoop() {
        iteration++;
        while (!gameboard.isFull()) {
            players[iteration % players.length].play();
        }
    }

    private int getInt(String prompt, Callable<T> condition) {
        Scanner scan = new Scanner(System.in);
        boolean accepted = false;

        while (!accepted) {
            try {
                System.out.print(prompt);
                scan.nextInt();
                accepted = true;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a numeric.");
                scan.next();
            }
        }
    }
}
