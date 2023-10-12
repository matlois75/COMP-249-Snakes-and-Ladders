// -------------------------------------------------------
// Assignment 1
// Written by: Mathys Loiselle, 40242303
// For COMP 249 Section PP – Winter 2023
// --------------------------------------------------------

import java.util.Scanner;

/**
 * -------------------------------------------------------<p>
 * Mathys Loiselle, 40242303<p>
 * COMP 249 Section PP, Winter 2023<p>
 * Assignment #1<p>
 * Due Date: February 3, 2023<p>
 * --------------------------------------------------------<p>
 *  Using different methods, this program begins by explaining the rules of the program and the game to
 *  the users, and continues by deciding the first player. Then, the program jumps right into the game with the first
 *  round, prompting each player to roll the dice, and displaying the players' positions on the game board which is
 *  printed after each player rolls the dice. This game also implements the rules of snakes and ladders, hence the name 
 *  of the game, meaning if a player lands on one of these special squares their position will change in correspondence 
 *  with their square's consequence. Finally, once a player reaches the 100th square, the game will end and the winner 
 *  will see their player number be congratulated on the console.<p>
 *  
 *  Note that a multiple player extension is easily accessible by altering the condition statement in the constructor and
 *  adding more player icons to be displayed on the board, as well as doing some minor tweaks on the first player decision 
 *  logic. Other than those three things, the program is essentially fully functional for more than 2 players.
 * @author Mathys Loiselle, 40242303
 *
 */
public class LadderAndSnake {
	private int[] players; // Creating an array of integers, which will serve as the order for who the first player is, as well as how many players there will be in the game.
	private int[] playerPosition; // Creating an array of integers, which will serve as the position of each player on the board.
	private int[][] board; // Creating a 2-D array of integers which will be used to display the board to the screen.
	
	/**
	 * LadderAndSnake constructor which will set the number of players, as well as create the 2-D board array.
	 * @param players The amount of players playing, specified by the user.
	 */
	public LadderAndSnake(int players) {
		
		// Creating the game board.
		board = new int[10][10];
		
		// Setting the number of players based off of the user's input. The program will display a statement corresponding with each respective value the user inputs.
		if (players > 2) {
			System.out.println("Initialization was attempted for " + players + " member of players; however this is only expected for an extended version of the game. Value will be set to 2.");
			this.players = new int[2];
			this.playerPosition = new int[2];
			for (int i = 0; i < playerPosition.length; i++) {
				playerPosition[i] = 0;
			}
		}
		else if (players < 2) {
			System.out.println("Error: Cannot execute the game with less than 2 players! Will exit.");
			System.exit(0);
		}
		else {
			System.out.println("Starting a new game for two players.");
			this.players = new int[players];
			this.playerPosition = new int[players];
			for (int i = 0; i < playerPosition.length; i++) {
				playerPosition[i] = 0;
			}
		}
		
		// Displays a message for the user to understand how to interact with the program, as well as the first set of instructions/rules of the game.
		System.out.println("\nRules of the program:\n"
						 + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
						 + "~~ Enter any input to roll the dice ~~\n"
						 + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n"
						 + "Each player must take turns rolling the dice to decide who will be the first player.\n"
						 + "The player with the highest number will be awarded first player.\n");
	}
	
	/**
	 * This method will return a random number from 1 to 6 when called.
	 * @return A random number from 1 to 6.
	 */
	public int flipDice() {
		return (1 + (int)(Math.random()*6));
	}
	
	/**
	 * This method will not return anything but will serve as the main logic for how the game operates.
	 * This method will display the rules of the game to the user, and will then begin the process of displaying the current round, prompting the players to roll the dice, and displaying their position on the game board printed to the console.
	 * @param scan Uses the 'scan' Scanner variable from the driver class to be able to re-use this Scanner instead of making a new one.
	 */
	public void play(Scanner scan) {
		firstPlayerRoll(scan); // Calls a method to decide who the first player of the game will be. Gives the 'scan' Scanner as a variable from the driver class so that this variable can be used across different methods(Creating many different Scanner variables would make things less organized).
		
		int round = 0; // int variable that will keep track of how many rounds the players go through as the game advances.
		int dice = 0; // int variable that will serve as a temporary holder for the random number generated by a player. This will serve to tell the user what number they got from rolling the dice.
		int original = 0; // int variable that will serve as a temporary holder for the player's position. This number will be compared to the player's position after potentially having landed on a snake or ladder square to see if they have moved. 
		int overflow = 0; // int variable that will serve to retain the value of the overflow number of squares a player has gone past the 100th square by.
		
		// Displays a message to explain the rules of the game and the different types of squares, as well as their locations.
		System.out.println("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n\n"
						 + "Rules of the game:\n"
						 + "@ One at a time, each player will roll the dice and advance by the amount the dice displays, starting from square 0 (Outside the board).\n"
						 + "@ If a player lands on a square occupied by another player, the player already on that square is kicked and returned to square 0 (Outside the board).\n"
						 + "@ If a player rolls a number that puts them further than the 100th square, that player will go to the 100th square and then go back however many squares the overflow number is determined to be.\n"
						 + "@ If a player reaches the bottom of a ladder, the player automatically gets moved up to the top of the ladder. The following line explains which squares are ladder squares:\n"
						 + "(1 -> 38), (4 -> 14), (9 -> 31), (21 -> 42), (28 -> 84), (36 -> 44), (51 -> 67), (71 -> 91), (80 -> 100)\n"
						 + "@ If a player reaches the head of a snake, the player automatically gets moved down to the bottom of the snake. The following line explains which squares are snake squares:\n"
						 + "(16 -> 6), (48 -> 30), (64 -> 60), (79 -> 19), (93 -> 68), (95 -> 24), (97 -> 76), (98 -> 78)\n");
		
		// while loop that will run continuously for each round until a player has reached the 100th square.
		while (true) {
			
			// Updates the number of rounds this game has taken, and displays it accordingly.
			round++; 
			System.out.println("\nRound " + round + ":\n"
							 + "================");
			
			// for loop that goes through the entire list of players to prompt them to roll the dice, which will alter their position as a result.
			for (int i = 0; i < players.length; i++) {
				System.out.print("Player " + players[i] + ", please roll the dice: ");
				scan.next();
				dice = flipDice();
				playerPosition[i] += dice;
				System.out.println("Player " + players[i] + " rolled a " + dice + "!"); // Lets the user know what number they got from the dice.

				// Checks if the player has gone past the 100th square.
				if (playerPosition[i] > 100) {
					overflow = playerPosition[i]-100;
					playerPosition[i] = 100 - overflow;
					System.out.println("Player " + players[i] + " has gone past the 100th square by " + overflow + " too many places. Player will be moved to square " + playerPosition[i] + ".");
				}
				
				original = playerPosition[i];
				// Ladder squares will move a player up to a certain square.
				switch (playerPosition[i]) {
				case 1:
					playerPosition[i] = 38;
					break;
				case 4:
					playerPosition[i] = 14;
					break;
				case 9:
					playerPosition[i] = 31;
					break;
				case 21:
					playerPosition[i] = 42;
					break;
				case 28:
					playerPosition[i] = 84;
					break;
				case 36:
					playerPosition[i] = 44;
					break;
				case 51:
					playerPosition[i] = 67;
					break;
				case 71:
					playerPosition[i] = 91;
					break;
				case 80:
					playerPosition[i] = 100;
					break;
				}
				
				// Snake squares will move a player down to a certain square.
				switch (playerPosition[i]) {
				case 16:
					playerPosition[i] = 6;
					break;
				case 48:
					playerPosition[i] = 30;
					break;
				case 64:
					playerPosition[i] = 60;
					break;
				case 79:
					playerPosition[i] = 19;
					break;
				case 93:
					playerPosition[i] = 68;
					break;
				case 95:
					playerPosition[i] = 24;
					break;
				case 97:
					playerPosition[i] = 76;
					break;
				case 98:
					playerPosition[i] = 78;
					break;
				}
				
				// Displays a message letting the players know that one of them has reached either a snake or a ladder square.
				if (original < playerPosition[i])
					System.out.println("Player " + players[i] + " has landed on a ladder square! They have been moved from " + original + " up to square " + playerPosition[i] + ".");
				else if (original > playerPosition[i])
					System.out.println("Player " + players[i] + " has landed on a snake square! They have been moved from " + original + " down to square " + playerPosition[i] + ".");
				
				
				// for loop that checks if the new square of a player is already occupied by another player. If the square is already occupied by a player, that player will be kicked off the board and returned to square 0.
				for (int j = 0; j < players.length; j++) {
					
					// Makes sure the players are not being compared to themselves.
					if ((i == j) && ((players.length-1)-j > 0))
						j++;
					else if ((i == j) && ((players.length-1)-j == 0))
						break;
					
					// Will return the player already on the square to square 0 if another player lands on their square. Will display all positions for those who have moved.
					if (playerPosition[i] == playerPosition[j]) {
						playerPosition[j] = 0;
						System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
										 + "Player " + players[i] + " is now at square " + playerPosition[i] 
										 + "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
										 + "Player " + players[j] + " is now at square " + playerPosition[j]);
					}
					else {
						System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
								 + "Player " + players[i] + " is now at square " + playerPosition[i] + "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
					}
				}
				
				gameBoard(playerPosition[0], playerPosition[1]); // Calling the gameBoard() method will print the game board with the players' positions shown on it, using the two parameters given.
				
				// Celebration message displayed if a player has landed on the 100th square and won.
				if (playerPosition[i] == 100) {
					System.out.println("~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~\n"
							+ "*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*\n"
							 + "\t\tCongratulations player " + players[i] + " for winning the game!!!\n"
							 + "*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*\n"
							 + "~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~");
					scan.close(); // Closes the scanPlay scanner.
					System.exit(0); // Terminates the program.
				}
			}
		}
	}
	
	/**
	 * This method will decide who the first player will be depending on who rolled the higher dice number.
	 * Each player must roll the dice one by one and compare with each other to see who rolled the highest number. The player who rolled the highest number will be awarded first player.
	 * @param scan Uses the 'scan' Scanner variable from the play() method (originally created in the driver class) to be able to re-use this Scanner instead of making a new one.
	 */
	public void firstPlayerRoll(Scanner scan) {
		int attempts = 0; // This int variable will keep track of how many attempts it took for the players to decide the first player.
		int highest = 0; // This int variable will keep track of the highest number attained by the players.
		int firstPlayer = 0; // This int variable will be used to assign a first player.
		boolean duplicate = true; // This boolean variable will be used to check if any players have duplicate dice values.
		
		// while loop will run until every player has a unique dice value.
		while (duplicate == true) {
			duplicate = false; // Initially the players are assumed not to have duplicate values (This will change later on if needed)
			highest = 0; // Resetting the highest value of highest to be 0 so that the highest value of a previous round doesn't affect the values of the current round.
			
			// Updating the number of attempts the players are currently on and printing it to the console.
			attempts += 1; 
			System.out.println("Attempt number " + attempts + ":");
			System.out.println("-------------------");

			// This for loop will prompt the user to roll the dice by entering any input into the console. Following their input, a random number will be generated from 1-6 and a message will be displayed to show the number they rolled.
			for (int i = 0; i < players.length; i++) {
				System.out.print("Player " + (i+1) + ", please roll the dice: ");
				scan.next();
				players[i] = flipDice(); // Using the 'players' array in this method to temporarily keep track of which player rolled the higher number and got to be first player. This array will later be reset to an array of ascending numbers and re-ordered to have the first player be in the first position.
				System.out.println("Player " + (i+1) + " rolled a " + players[i] + "\n");
			}
			System.out.println("----------------------------------");
			
			// This for loop will update the 'highest' variable to hold the true highest value present in the array of players (which currently holds the dice values of each player).
			for (int i = 0; i < players.length; i++) {
				if (players[i] > highest)
					highest = players[i];
			}
			
			// This for loop will check if any two players rolled the same dice number.
			for (int i = 0; i < players.length; i++) {
				for (int j = i+1; j < players.length; j++) {
					if (players[i] == players[j])
						duplicate = true;
				}
			}
			
			// If two players rolled the same number, the loop will restart and a new attempt will begin. Else, a message will display to say who the first player has been determined to be. The array of players will also be reset and re-ordered to have the first player be in front.
			if (duplicate == true)
				continue;
			else {
				for (int i = 0; i < players.length; i++) {
					if (players[i] == highest) {
						System.out.println("Player " + (i+1) + " rolled the higher number");
						firstPlayer = i+1;
						
						// Making the list of players be 1, 2, 3... and so on, for ranking.
						for (int j = 0; j < players.length; j++) {
							players[j] = (j+1);
						}
						
						// Swapping the old first player in the list of player with the new first player.
						int temp = players[0];
						players[0] = players[i];
						players[i] = temp;
						break; // Breaking out of the for loop so that the program doesn't have to uselessly check every other player in the list.
					}
				}
				System.out.println("-------------------------------------------");
			}
		}
		
		// Displays a message to say how many it took and who ended up getting first player.
		if (attempts > 1)
			System.out.println("After " + attempts + " attempts, the first player has been established to be player " + firstPlayer + " and will be seen on the board as XXX while the second player will be seen as OOO");
		else
			System.out.println("After " + attempts + " attempt, the first player has been established to be player " + firstPlayer + " and will be seen on the board as XXX while the second player will be seen as OOO");
	}
	
	/**
	 * This method will print the game board and the players' positions on it.
	 * @param player1 This holds the numerical value of the first player's position on the board.
	 * @param player2 This holds the numerical value of the second player's position on the board.
	 */
	public void gameBoard(int player1, int player2) {
		boolean even = false; // Will keep track of the row to see if it is even or odd.
		System.out.println("\t\t\t______________");
		System.out.println("\t\t\t| GAME BOARD |");
		System.out.print("-------------------------------------------------------------\n");
		
		// This for loop will iterate through the entire 10x10 board and print either a horizontal line (-), a vertical line (|), a number, or a player's icon.
		// Every other row, the numbers will start increasing instead of decreasing to give the same effect of a real snake and ladder game (The numbers will snake up instead of writing the numbers left to right like resetting a typewriter).
		for (int i = 100, t = 0; i > 0; t++) {
			System.out.print("|");
			for (int j = 0; j < 10; j++) {
				board[t][j] = i;
				if (even == false)
					i--;
				else
					i++;
				
				// If the first player's position matches with a number on the board, that number will be replaced with an "XXX", while the second player's will be replaced with an "OOO". Otherwise, the numbers will print as usual.
				if (player1 == board[t][j])
					System.out.print(String.format(" %-3s |", "XXX"));
				else if (player2 == board[t][j])
					System.out.print(String.format(" %-3s |", "OOO"));
				else
					System.out.print(String.format(" %-3s |", board[t][j]));
			}
			System.out.println();
			
			// Since the outputs that are printed to the console are by default left to right for each line, I have to manually change the value of the first number being printed in correspondence with the context of the board.
			// It can be seen on a real snake and ladder game that from the far right number to the next far left number, there is a 10 digit difference but since I am already updating the 'i' variable, I must adjust this by either subtracting 9 or 11 depending on if the row is even or odd.
			if (even == false)
				i = i-9;
			else
				i = i-11;
			
			even = !even; // Changing the boolean value for 'even' to its opposite value.
			
			if (i > 0)
				System.out.print("|-----|-----|-----|-----|-----|-----|-----|-----|-----|-----|\n");
			else
				System.out.print("-------------------------------------------------------------\n"); // Will print this if it is the final line.
		}
	}
}
