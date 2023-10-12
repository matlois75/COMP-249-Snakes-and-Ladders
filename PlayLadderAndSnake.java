
import java.util.Scanner;

/**
 * -------------------------------------------------------<p>
 * Mathys Loiselle, 40242303<p>
 * COMP 249 Section PP, Winter 2023<p>
 * Assignment #1<p>
 * Due Date: February 3, 2023<p>
 * -------------------------------------------------------<p>
 * The purpose of this program is to provide users a working snake and ladder game which they can interact with and
 * play other people on. For this driver class, not much had to be implemented as most of the logic and game design
 * was done on the LadderAndSnake class. This class first outputs a welcome message to the console, and
 * prompts the user for the amount of players they wish to play with. It then used that input to pass it as a parameter
 * when creating a new LadderAndSnake object and running the appropriate methods.
 * @author Mathys Loiselle, 40242303
 *
 */
public class PlayLadderAndSnake {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in); // Creating a new Scanner called 'scan'.
		
		// Outputting welcome message.
		System.out.println("++++++++++++++++++++++++++++++++++++++++\n"
						 + "Welcome to the game of Ladder and Snake!\n"
						 + "++++++++++++++++++++++++++++++++++++++++\n");
		
		// Prompting the user for an integer. This will be used to determine how many players the game will be set for.
		System.out.print("Please enter number of players: ");
		int players = scan.nextInt();
		
		LadderAndSnake game = new LadderAndSnake(players); // Creating a new LadderAndSnake object called 'game' and passing the amount of players as a parameter to the constructor.
		game.play(scan); // Calling the play() method of the 'game' object which will have the 'scan' Scanner as a parameter to be used as a Scanner within itself.
		scan.close(); // Closing the 'scan' Scanner.
	}
}
