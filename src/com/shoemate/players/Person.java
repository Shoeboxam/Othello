package com.shoemate.players;
import java.util.*;
import java.util.function.Function;

public class Person extends Player {

    private Scanner scan = new Scanner(System.in);

    public int[] play(Player[][] boardView) {
        System.out.println("Color: " + getColor());
        return new int[] {
            getInt("X: ", (a) -> a > 0 && a <= boardView.length, "Selection must be within board width. Try again.") - 1,
            getInt("Y: ", (a) -> a > 0 && a <= boardView[0].length, "Selection must be within board height. Try again.") - 1
        };
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
