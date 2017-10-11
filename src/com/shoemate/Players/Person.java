package com.shoemate.Players;
import com.shoemate.Board;

import java.util.*;


public class Person extends Player {

    public Person(Board board) {
        super(board);
    }

    public boolean play() {
        int xVal = 0;
        int yVal = 0;
        Scanner scan = new Scanner(System.in);

        do {
            boolean accepted = false;
            while (!accepted) {
                try {
                    xVal = scan.nextInt();
                    accepted = true;
                } catch (InputMismatchException e) {
                    scan.next();
                    System.out.println("Please enter a numeric.");
                }
            }

            accepted = false;

            while (!accepted) {
                try {
                    yVal = scan.nextInt();
                    accepted = true;
                } catch (InputMismatchException e) {
                    scan.next();
                    System.out.println("Please enter a numeric.");
                }
            }
        } while (this.board.takeTurn(this, xVal, yVal));


    }

    public String toString() {
        return "TEST";
    }
}
