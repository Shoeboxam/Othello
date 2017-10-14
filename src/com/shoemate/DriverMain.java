/* Analysis: Create Othello with support for human and AI players.
   Design:   Collect user preferences for board size, players, and game mode (diagonal)
             Iterate through player list until a play cannot be made
                Continue to poll a player until a valid play is made
                Apply othello game rules to the game board when a valid play is made
 */

package com.shoemate;

public class DriverMain {
    public static void main(String[] args) {
        
        // Initialize game
        Game myGame = new Game();

        // Play the game!
        myGame.start();
    }
}
