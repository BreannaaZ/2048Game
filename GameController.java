import java.util.Random;

/**
 * GameController acts as the controller part of the 2048 game. It contains methods
 * to control the board, such as making moves and checking for a win/loss.
 *
 *  @authors Bobby Caron, Matteo Ciavaglia, Charles Greco, Breanna Zinky
 *  @date 11/2/22
 *  @version 1.0
 */
public class GameController {
    /*************************************************************************/
    /* Variables */
    private Board board;
    private GameStatus gameStatus = GameStatus.IN_PROGRESS;
    private int winValue;
    private Random rand;

    /*************************************************************************/
    /* Constructors */
    /**
     * This no-arg constructor creates a default board.
     */
    public GameController(){
        board = new Board();
        winValue = 2048; // Default win value
        rand = new Random(); // Rand will be used to randomly choose 2 or 4 for the starting tile value
        newTile(); // Will place a tile randomly for the start of the board
    }

    /**
     * This parameterized constructor creates a board based on the given boardsize and win value.
     *
     * @param boardSize     An integer between 4 and 10 for the size of the square board (# of rows/columns)
     * @param winValue      The number to reach in order to win the game - some power of 2.
     */
    public GameController(int boardSize, int winValue){
            board = new Board(boardSize);
            this.winValue = winValue; // Isn't necessary to check here if winValue is a power of 2
                                     // because it is already checked at every input point
            rand = new Random();
            newTile();
    }
    /*************************************************************************/
    /* Getters and Setters */

    /**
     * This returns the private board object.
     *
     * @return Board    A Board object that holds the values in the tiles of the board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * This sets the value for the private board object from the passed
     * in parameter.
     *
     * @param board A Board object that holds the values in the tiles of the board
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * This returns the value in the private GameStatus enum.
     * It also checks for a loss and win to update the game status
     * if necessary before returning it.
     *
     * @return GameStatus   An enum for the game status (lost, won, or in-progress)
     */
    public GameStatus getGameStatus() {
        checkLoss();
        checkWin();
        return gameStatus;
    }

    /**
     * This sets the value for the private GameStatus enum
     * from the passed in parameter.
     *
     * @param gameStatus    An enum for the game status (lost, won, or in-progress)
     */
    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    /*************************************************************************/
    /* New Tile Method */
    /**
     * This will create a random tile on the board with a value of either
     * 2 or 4.
     */
    public void newTile(){
        boolean notFound = true; // Used to check if an empty tile is found or not
        int row; // For choosing a random row on the board
        int col; // For choosing a random column on the board
        Tile startTile;
        // Uses rand to randomly choose either 2 or 4 for the starting tile
        int startValue = rand.nextInt(2);
        if(startValue == 1){
            startTile = new Tile(2);
        }else{
            startTile = new Tile(4);
        }
        // Searches through random tiles to find an empty one
        while(notFound){
            row = rand.nextInt(board.getBoardSize()); // Chooses a random row for the new tile
            col = rand.nextInt(board.getBoardSize()); // Chooses a random column for the new tile
            if(board.getValue(row,col) == -1){ // Checks to see if tile is null
                board.setTile(row,col,startTile); // Sets the tile if it is empty
                notFound = false;
            }
        }

    }

    /*************************************************************************/
    /* Reset Method */
    /**
     * This resets the board to set each tile to null.
     */
    public void reset(){
        // Goes through every one of the tiles
        for(int row = 0;row < board.getBoardSize(); row++){
            for(int col = 0; col < board.getBoardSize(); col++){
                // Set the tile to null
                board.setTile(row,col,null);
            }
        }
        newTile(); // Creates a new starting tile after emptying the board
        gameStatus = GameStatus.IN_PROGRESS; // Resets the game status
    }

    /*************************************************************************/
    /* Check Win Method */
    // Will cycle through each tile to check if the win value was achieved
    private void checkWin(){
        // Goes through every one of the tiles
        for(int row = 0;row < board.getBoardSize(); row++){
            for(int col = 0; col < board.getBoardSize(); col++){
                if(board.getValue(row,col) == winValue){
                    gameStatus = GameStatus.WON;
                    // Leave the function because a win was already found
                    return;
                }
            }
        }
    }

    /*************************************************************************/
    /* Check Loss Method */
    // Will cycle through each tile to check if the game is a loss
    // (If there are no empty tiles or tiles with similar neighbors,
    // and the win value was not reached)
    private void checkLoss(){
        // Goes through every one of the tiles
        for(int row = 0;row < board.getBoardSize(); row++){
            for(int col = 0; col < board.getBoardSize(); col++){
                // Check to see if there is an empty tile
                // If there is then we can return because the game is not over
                if(board.getValue(row,col) == -1){
                    return;
                }
                // If there is a similar neighbor than the game is still in progress, so we can return
                if(findSimilarNeighbors(row,col) == true){
                    return;
                }
            }
        }
        // If there are no similar neighbors and no empty tiles then the game is lost
        gameStatus = GameStatus.LOST;
    }

    /*************************************************************************/
    /* Find Similar Neighbors Method */
    // Checks if any of the tiles have a surrounding tile of the same value
    // (for purpose of checking for a loss)
    private boolean findSimilarNeighbors(int row,int col){
        int currentValue = board.getValue(row,col);
        // Checks the tile below
        if ((row - 1) >= 0) { // Make sure it won't check out of bounds tiles by passing an index of -1
            if (board.getValue(row - 1, col) == currentValue) {
                return true;
            }
        }
        // Checks tile above
        if ((row + 1) < board.getBoardSize()) { // Make sure it won't check out of bounds tiles by passing an index of 4
            if (board.getValue(row + 1, col) == currentValue) {
                return true;
            }
        }
        // Checks tile to the right
        if ((col + 1) < board.getBoardSize()) { // Make sure it won't check out of bounds tiles by passing an index of 4
            if (board.getValue(row, col + 1) == currentValue) {
                return true;
            }
        }
        // Checks tile to the left
        if ((col - 1) >= 0) { // Make sure it won't check out of bounds tiles by passing an index of -1
            if (board.getValue(row, col - 1) == currentValue) {
                return true;
            }
        }
        // If everything up, down, left, and right are not the same then there's no similar neighbors
        return false;
    }

    /*************************************************************************/
    /* Recursing Methods */
    /**
     * This allows for making a left move on the board by
     * collapsing the row and combining similar tiles.
     *
     * @param row   A row on the square board (some integer between 4 and 10)
     */
    public void recurseLeft(int row){
        // Base case for when we reach the end of the row
        if(row == board.getBoardSize()){
            return;
        }
        // Goes through every column
        for(int col = 0;col < board.getBoardSize(); ++col) {
            // If the previous tile is null then move
            // the current tile and set current to null
            if (board.getValue(col, row - 1) == -1) {
                // Only want to move the tile if the current tile isn't null
                if(board.getValue(col,row) != -1) {
                    board.setTile(col, row - 1, board.getTile(col, row));
                    board.setTile(col, row, null);
                    // Recurse the previous col in case there are any new moves
                    // As long as it's not in the first col
                    if (row > 1) {
                        recurseLeft(row - 1);
                    }
                }
            }
            // If the previous is the same, combine the two values and remove the current tile
            if ((board.getValue(col, row) == board.getValue(col, row - 1)) && board.getValue(col,row) != -1) {
                board.getTile(col,row - 1).setValue(board.getValue(col,row) * 2);
                // Set the current tile to null
                board.setTile(col, row, null);
                // Recurse the previous col in case there are any new moves
                // As long as it's not in the first col
                if(row > 1) {
                    recurseLeft(row - 1);
                }
            }
        }
        // Move onto the next col if there were no changes in the current one
        recurseLeft(row + 1);
    }

    /**
     * This allows for making a right move on the board by
     * collapsing the row and combining similar tiles.
     *
     * @param row   A row on the square board (some integer between 4 and 10)
     */
    public void recurseRight(int row){
        if(row < 0){
            return;
        }
        //goes through every column
        //starting at the second to last col
        for(int col = board.getBoardSize() - 1;col >= 0; --col) {
            //if the next tile is null then move
            // the current tile and set current to null
            if (board.getValue(col, row + 1) == -1) {
                //only want to move the tile if the current tile isn't null
                if(board.getValue(col,row) != -1) {
                    board.setTile(col, row + 1, board.getTile(col, row));
                    board.setTile(col, row, null);
                    //recurse the previous col incase there is any new moves
                    //as long as the col is not the last one
                    if(row < board.getBoardSize() - 2) {
                        recurseRight(row + 1);
                    }
                }
            }
            //if the previous is the same, combine the two values and remove the current tile
            if (board.getValue(col, row) == board.getValue(col, row + 1) && board.getValue(col,row) != -1) {
                board.getTile(col,row + 1).setValue(board.getValue(col,row ) * 2);
                //set the current tile to null
                board.setTile(col, row, null);
                //recurse the previous col incase there is any new moves
                //as long as the col is not the last one
                if(row < board.getBoardSize() - 2) {
                    recurseRight(row + 1);
                }
            }
        }
        //move onto the next col if there were no changes in the current one
        recurseRight(row - 1);
    }

    /**
     * This allows for making an upwards move on the board by
     * collapsing the column and combining similar tiles.
     *
     * @param col   A column on the square board (some integer between 4 and 10)
     */
    public void recurseUp(int col){
        //base case for when we reach the end of the col
        if(col == board.getBoardSize()){
            return;
        }
        //goes through every row
        for(int row = 0;row < board.getBoardSize(); ++row) {
            //if the previous tile is null then move
            // the current tile and set current to null
            if (board.getValue(col - 1, row) == -1) {
                //only want to move the tile if the current tile isn't null
                if(board.getValue(col,row) != -1) {
                    board.setTile(col - 1, row, board.getTile(col, row));
                    board.setTile(col, row, null);
                    //recurse the previous col incase there is any new moves
                    //as long as it's not in the first col
                    if (col > 1) {
                        recurseUp(col - 1);
                    }
                }
            }
            //if the previous is the same, combine the two values and remove the current tile
            if ((board.getValue(col, row) == board.getValue(col - 1, row)) && board.getValue(col,row) != -1) {
                board.getTile(col - 1,row).setValue(board.getValue(col,row) * 2);
                //set the current tile to null
                board.setTile(col, row, null);
                //recurse the previous row incase there is any new moves
                //as long as it's not in the first row
                if(col > 1) {
                    recurseUp(col - 1);
                }
            }


        }
        //move onto the next col if there were no changes in the current one
        recurseUp(col + 1);
    }

    /**
     * This allows for making a downwards move on the board by
     * collapsing the column and combining similar tiles.
     *
     * @param col   A column on the square board (some integer between 4 and 10)
     */
    public void recurseDown(int col){
        //base case for when we reach the end of the col
        if(col < 0){
            return;
        }
        //goes through every row
        for(int row = board.getBoardSize() - 1;row >= 0; --row) {
            //if the previous tile is null then move
            // the current tile and set current to null
            if (board.getValue(col + 1, row) == -1) {
                //only want to move the tile if the current tile isn't null
                if(board.getValue(col,row) != -1) {
                    board.setTile(col + 1, row, board.getTile(col, row));
                    board.setTile(col, row, null);
                    //recurse the previous col incase there is any new moves
                    //as long as it's not in the first col
                    if (col < board.getBoardSize() - 2) {
                        recurseDown(col + 1);
                    }
                }
            }
            //if the previous is the same, combine the two values and remove the current tile
            if ((board.getValue(col, row) == board.getValue(col + 1, row)) && board.getValue(col,row) != -1) {
                board.getTile(col + 1,row).setValue(board.getValue(col,row) * 2);
                //set the current tile to null
                board.setTile(col, row, null);
                //recurse the previous row incase there is any new moves
                //as long as it's not in the first row
                if(col < board.getBoardSize() - 2) {
                    recurseDown(col + 1);
                }
            }
        }
        //move onto the next col if there were no changes in the current one
        recurseDown(col - 1);
    }
}
