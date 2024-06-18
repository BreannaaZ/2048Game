import java.util.Scanner;

/**
 * Text2048 is the command line interface that houses the model part of the 2048 game.
 *
 *  @authors Bobby Caron, Matteo Ciavaglia, Charles Greco, Breanna Zinky
 *  @date 11/2/22
 *  @version 1.0
 */
public class Text2048 {
    /*************************************************************************/
    /* Variables */
    private GameController game = new GameController(); // Instance of GameController to control the game logic/perform actions on the board
    private Scanner scanner = new Scanner(System.in); // For getting user input

    /*************************************************************************/
    /* printBoard Method */

    /**
     * This prints the current board (from the values in the linked list) into the command line interface.
     */
    public void printBoard(){
        // Cycle through the rows and columns
        for (int row = 0; row < game.getBoard().getBoardSize(); row++) {
            for (int col = 0; col < game.getBoard().getBoardSize(); col++) {
                if(game.getBoard().getValue(row,col) == -1) { // If the tile is empty
                    System.out.print("0 / ");
                }else{
                    System.out.print(game.getBoard().getValue(row,col) + " / "); // If the tile has a value
                }
            }
            System.out.println("\n");
        }
    }

    /*************************************************************************/
    /* Run Method */

    /**
     * This allows the user to play the 2048 game. It takes user input for the board size and win value.
     * It then allows the user to play certain moves to update the board, repeating until a win or loss is detected.
     */
    public void run(){
        Boolean repeat = true;
        int boardSize = 0;
        int winValue = 4;
        while(repeat) {
            try {
                System.out.println("Enter the size of board. The size must be in between sizes 4-10.");
                boardSize = scanner.nextInt(); // This may throw an exception

                if (boardSize > 10 || boardSize < 4) {
                    throw new IllegalArgumentException("Must be a whole integer in between 4 and 10.");
                } else {
                    repeat = false; // If it makes it here, the input is valid
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("Number is out of bounds");
                scanner.nextLine(); // Clear the scanner's buffer
            }catch (Exception ex){
                System.out.println("Must be an integer");
                scanner.nextLine(); // Clear the scanner's buffer
            }
        }
        repeat = true; // Reset repeat to true
        while (repeat) {
            try {
                System.out.println("What should the winning number be?");
                winValue = scanner.nextInt(); // This may throw an exception
                // Checks if the number is a power of 2
                Tile t = new Tile(winValue);
                repeat = false; // If it makes it here, the input is valid
            } catch (IllegalArgumentException ex) {
                System.out.println("Must be an positive even number that is a power of 24");
                scanner.nextLine(); // Clear the scanner's buffer
            } catch (Exception x){
                System.out.println("Must be an integer");
                scanner.nextLine(); // Clear the scanner's buffer
                }
        }

        game = new GameController(boardSize,winValue); // Instance of GameController passing it the obtained user input

        // Letting the user input moves and running those moves
        while (game.getGameStatus() == GameStatus.IN_PROGRESS) {
            printBoard(); // Update/display the current board
            System.out.println("Move in which direction? (WASD): ");
            String direction = scanner.nextLine();
            switch (direction) {
                case "W":
                    game.recurseUp(1);
                    break;
                case "w":
                    game.recurseUp(1);
                    break;
                case "S":
                    game.recurseDown(boardSize -2);
                    break;
                case "s":
                    game.recurseDown(boardSize -2);
                    break;
                case "A":
                    game.recurseLeft(1);
                    break;
                case "a":
                    game.recurseLeft(1);
                    break;
                case "D":
                    game.recurseRight(boardSize - 2);
                    break;
                case "d":
                    game.recurseRight(boardSize - 2);
                    break;
                case "Q":
                    System.out.println("Thank you for playing!\n\n");
                    System.exit(0);
                    break;
                case "q":
                    System.out.println("Thank you for playing!\n\n");
                    System.exit(0);
                    break;
                default:
                    System.out.println("That is not a valid command.");
                    continue;
            }
            if (game.getBoard().hasEmpty()) {
                game.newTile();
            }
        }
        printBoard(); // Update/display the current board
        // Repeat running through moves until a win or loss is detected
        if(game.getGameStatus() == GameStatus.WON){
            System.out.println("CONGRATULATIONS!\n\n");
        } else {
            System.out.println("Better luck next time.\n\n");
        }
    }
    /*************************************************************************/
    /* Main Method */
    /**
     * This main method creates an instance of the Text2048 object and runs it.
     *
     * @param args      Arguments for main method
     */
    public static void main(String[] args){
        Text2048 game = new Text2048();
        game.run();
    }
}
