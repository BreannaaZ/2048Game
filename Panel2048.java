import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Panel2048 is the GUI panel that houses the model part of the 2048 game.
 * It also contains a main method to obtain user input and start and run the game.
 *
 *  @authors Bobby Caron, Matteo Ciavaglia, Charles Greco, Breanna Zinky
 *  @date 11/2/22
 *  @version 1.0
 */
public class Panel2048 extends JPanel implements ActionListener, KeyListener {
    /*************************************************************************/
    /* Variables */
    private int winSize; // Obtained from user input - some number (power of 2) that once achieved results in a win
    private int boardSize; // Obtained from user input - the size of the square board (# of rows/columns)
    private JLabel gamesPlayed; // Displays the counter for number of games played
    private JFrame frame;
    private JLabel[][] lordBoard; // Array of JLabels which shows the board of the 2048 game; displays its tiles/numbers
    // Buttons to control the game
    private JButton up;
    private JButton down;
    private JButton left;
    private JButton right;
    private JLabel wins; // Displays the counter for number of wins
    private JMenu file; // Holds the file menu with the quit and reset options
    private JMenuItem quit;
    private JMenuItem reset;
    private JMenuBar menus;
    private int games; // Counts the number of games played
    private int winCounter; // Counts the number of wins
    private JPanel tilePanel; // Panel to hold the board of the game (the lordBoard JLabel array object)
    private GameController game; // Object to control the game logic

    /**
     * Creates the GUI panel for the 2048 game.
     *
     * @param boardSize     The size of the square board (# of rows/columns)
     * @param winSize       Some number (power of 2) that once achieved results in a win
     */
    public Panel2048(int boardSize, int winSize) {
        // Set the board size and win size
        this.boardSize = boardSize;
        this.winSize = winSize;
        // Create the main frame
        frame = new JFrame("2048 GAME");
        // Create the main game controller object that will control the game logic
        game = new GameController(boardSize, winSize);
        // Create a side panel to hold the counters
        JPanel counterPanel = new JPanel();
        counterPanel.setLayout(new GridLayout(2,1));
        // Counters for number of wins and number of games played
        wins = new JLabel("Wins = " + winCounter);
        // Make so the background of the wins text field matches the frame's color
        wins.setBackground(new java.awt.Color(0, 0, 0, 0));
        gamesPlayed = new JLabel(("Games Played = " + games));
        // Center the counter JLabels
        wins.setHorizontalAlignment(JTextField.CENTER);
        gamesPlayed.setHorizontalAlignment(JTextField.CENTER);
        // Add counters to the side panel
        counterPanel.add(wins);
        counterPanel.add(gamesPlayed);
        // Create the file menu holding the quit and reset options
        file = new JMenu("File");
        quit = new JMenuItem("Quit!");
        reset = new JMenuItem("Reset!");
        file.add(quit);
        file.add(reset);
        menus = new JMenuBar();
        menus.add(file);
        // Create a separate panel to hold the buttons
        JPanel separatePanel = new JPanel();
        separatePanel.setLayout(new BorderLayout(5, 5));
        // Create the buttons to control the game
        up = new JButton("Up");
        up.setFocusable(false); // This ensures that the buttons don't take the focus of the container, which causes keylistener to not work after clicking a button
        up.setBackground(Color.white); // Set color of button
        down = new JButton("Down");
        down.setFocusable(false);
        down.setBackground(Color.white);
        left = new JButton("Left");
        left.setFocusable(false);
        left.setBackground(Color.white);
        right = new JButton("Right");
        right.setFocusable(false);
        right.setBackground(Color.white);
        // Action listeners for each button
        up.addActionListener(this);
        down.addActionListener(this);
        left.addActionListener(this);
        right.addActionListener(this);
        quit.addActionListener(this);
        reset.addActionListener(this);
        // Add buttons to the side separatePanel
        separatePanel.add(up, BorderLayout.NORTH);
        separatePanel.add(down, BorderLayout.SOUTH);
        separatePanel.add(left, BorderLayout.WEST);
        separatePanel.add(right, BorderLayout.EAST);
        // Create a new panel to hold the board tiles
        tilePanel = new JPanel();
        // Set the tilePanel to a grid layout, so it holds the square board of tiles
        tilePanel.setLayout(new GridLayout(boardSize, boardSize, 2, 2));
        // Instantiate lordBoard object and set the size
        lordBoard = new JLabel[boardSize][boardSize];
        // Add the counters and panels to the main panel
        this.add(counterPanel);
        this.add(separatePanel);
        // Call displayBoard to refresh board
        displayBoard();
        // KeyListener to allow use of arrow keys to control the board
        this.addKeyListener(this);
        this.setFocusable(true);
        this.addKeyListener(this);
        // Finish up the main frame
        frame.setJMenuBar(menus);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setVisible(true);
    }
    /*************************************************************************/
    /* displayBoard Method */

    /**
     * This updates the JLabel array on the GUI panel to have it hold and display the current values in the board.
     */
    public void displayBoard(){
        tilePanel.removeAll(); // Reset the tile panel
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2); // Create a border for the tiles of the board
        // Cycle through the rows and columns of the board to update the JLabel array with the values from the linked list
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if(game.getBoard().getValue(row,col)==-1){ // If the tile is empty
                    lordBoard[row][col] = new JLabel(" 0 ", SwingConstants.CENTER);
                }
                else{
                    lordBoard[row][col] = new JLabel(String.valueOf(game.getBoard().getValue(row,col)), SwingConstants.CENTER);
                }
                // Set the size of the JLabel and make it square
                lordBoard[row][col].setPreferredSize(new Dimension(100, 100));
                lordBoard[row][col].setFont(new Font("Serif", Font.PLAIN, 50));
                lordBoard[row][col].setBorder(border); // Set the border for the tiles of the board
                tilePanel.add(lordBoard[row][col]);
            }
        }
        this.add(tilePanel); // Re-add the tile panel
        revalidate();
        repaint();
    }
    /*************************************************************************/
    /* Main Method to Run GUI */

    /**
     * This gains user input to set the board size and win value of the game. It also creates an instance
     * of the panel to run it.
     *
     * @param args  Arguments for main method
     */
    public static void main(String[] args) {
        int inputBoardSize = 0;
        int inputWinValue = 4;
        boolean retry = true;
        /*************************************************************************/
        /* User Input for BoardSize and Win Amt */
        // User Input Section - Get Board Size
        // Retry boolean is used in a while loop to repeatedly ask the user for input if
        // invalid amounts are entered.
        while (retry) {
            try {
                // Prompt user for input for board size
                String inputString = JOptionPane.showInputDialog(null, "Enter in the size of the board: ");
                // Exit the system if the cancel button was pressed by checking if the input is null
                // (if it is null, then the cancel button was pressed - otherwise it will contain an empty string)
                if (inputString == null) {
                    System.exit(JFrame.DO_NOTHING_ON_CLOSE);
                }
                // Get the boardSize from parsing the input string
                inputBoardSize = Integer.parseInt(inputString); // If invalid amount is entered, an exception is caused here
                // Validate that the input is between 2 and 15.
                if (inputBoardSize < 4 || inputBoardSize > 10) {
                    throw new NumberFormatException();
                }
                retry = false; // If it makes it here, the correct input was entered.
            }
            // Catch and display error message for invalid input
            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a real number in the range of 4 through 10.", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
        /*************************************************************************/
        /* User Input for win number */
        retry = true; // reset retry boolean to true
        while (retry) {
            try {
                // Prompt user for input for board size
                String inputString = JOptionPane.showInputDialog(null, "Enter in the winning number: ");
                // Exit the system if the cancel button was pressed by checking if the input is null
                // (if it is null, then the cancel button was pressed - otherwise it will contain an empty string)
                if (inputString == null) {
                    System.exit(JFrame.DO_NOTHING_ON_CLOSE);
                }
                // Get the boardSize from parsing the input string
                inputWinValue = Integer.parseInt(inputString); // If invalid amount is entered, an exception is caused here
                Tile t = new Tile(inputWinValue); // Check that the number is a power of two - will throw an exception if not
                retry = false; // If it makes it here, the correct input was entered.
            }
            // Catch and display error message for invalid input
            catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Please enter a positive number that is a power of two.", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
        /*************************************************************************/
        /* Create Panel2048 Instance */
        new Panel2048(inputBoardSize, inputWinValue);
    }
    /*************************************************************************/
    /* Action Listeners */

    /**
     * Processes events based on the button pressed
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == quit) {
            System.exit(0);
        }
        if (e.getSource() == up) {
            game.recurseUp(1);
            runner(); // Calling this method will refresh/update the board in the display and check for a win/loss.
        }
        if (e.getSource() == down) {
            game.recurseDown(boardSize - 2);
            runner();
        }
        if (e.getSource() == left) {
            game.recurseLeft(1);
            runner();
        }
        if (e.getSource() == right) {
            game.recurseRight(boardSize - 2);
            runner();
        }
        if (e.getSource() == reset) {
            game.reset();
            displayBoard();
        }
    }

    /**
     * Refreshes the board and checks for a win/loss, resetting the board and adding to relevant counters if necessary.
     */
    public void runner(){
        displayBoard();
        if (game.getBoard().hasEmpty() && game.getGameStatus() == GameStatus.IN_PROGRESS) {
            game.newTile();
            displayBoard();
        }
        else if (game.getGameStatus() == GameStatus.WON) {
            JOptionPane.showMessageDialog(null,"CONGRATS, YOU WON!");
            super.update(this.getGraphics());
            game.reset();
            displayBoard();
            winCounter++;
            games++;
            gamesPlayed.setText("Games Played = " + games);
            wins.setText("Wins = " + winCounter);
        }
        else if (game.getGameStatus() == GameStatus.LOST) {
            JOptionPane.showMessageDialog(null,"MAYBE NEXT TIME! YOU LOST!");
            super.update(this.getGraphics());
            game.reset();
            games++;
            gamesPlayed.setText("Games Played = " + games);
            displayBoard();
        }
    }

    /**
     * Empty/unused method from implementing KeyListener
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Empty/unused method from implementing KeyListener
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
    }

    /**
     * Overridden method from implementing KeyListener, allows the use
     * of the arrow keys to control the board.
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == 38){
            game.recurseUp(1);
            runner();
        }
        if(key == 37){
            game.recurseLeft(1);
            runner();
        }
        if(key == 40){
            game.recurseDown(boardSize - 2);
            runner();
        }
        if(key == 39){
            game.recurseRight(boardSize - 2);
            runner();
        }
    }
}

