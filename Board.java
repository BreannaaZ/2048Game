/**
 * ...
 *
 *  @authors Bobby Caron, Matteo Ciavaglia, Charles Greco, Breanna Zinky
 *  @date 11/2/22
 *  @version 1.0
 */

//TODO: Add try and catch block to deal with thrown exceptions
//TODO: Add JavaDoc comments

public class Board {
    /************************************************************/
    /* Variables */
    //Note: Still need to make the LinkedList class so we can actually use it here
    //private LinkedList<LinkedList<Tile>> b;
    //Code given in project outline: LinkedList<LinkedList<Tile>> b = new LinkedList<LinkedList<Tile>>();
    private LinkedList<LinkedList<Tile>> board = new LinkedList<LinkedList<Tile>>();
    private int boardSize;
    /************************************************************/
    /* Constructors */
    public Board()
    {
        // Default constructor sets the board to a 4x4 size
        this.boardSize = 4;
        //creates the 4x4 board of null tiles
        for(int row = 0;row < 4; row++){
            board.add(row,new LinkedList<Tile>());
            for(int col = 0; col < 4; col++){
                board.get(row).add(col,null);
            }
        }

    }
    // This constructor takes a boardSize and setts the board to it
    public Board(int boardSize)
    {
        if (boardSize <= 10 && boardSize >= 4) // Validate boardSize
        {
            this.boardSize = boardSize;
        } else throw new IllegalArgumentException();

        //creates the board of null tiles
        for(int row = 0;row < boardSize; row++){
            board.add(row,new LinkedList<Tile>());
            for(int col = 0; col < boardSize; col++){
                board.get(row).add(col,null);
            }
        }

    }
    /************************************************************/
    /* HasEmpty Method */
    public boolean hasEmpty()
    {
        // Each cell on our board is either null or occupied by a Tile.
        // Write a function public boolean hasEmpty() that checks if
        // there are any empty cells (i.e., nulls) left on the board.
        for(int row = 0;row < boardSize; row++){
            for(int col = 0; col < boardSize; col++){
                if(board.get(row).get(col) == null){//checks every row and col to see if tile is empty
                    return true;
                }
            }
        }
        return false;
    }
    /************************************************************/
    /* getTile Method */
    public Tile getTile(int row, int col)
    {
        //This function returns the Tile from the provided spot.
        // Throw a new IllegalArgumentException() if the provided
        // row or col aren't within the boundaries of the board.
        if(row > boardSize || col > boardSize){
            //throw exception if the row or col are out of boundaries
            throw new IllegalArgumentException();
        }
        //return the tile at the given row and col
        return board.get(row).get(col);

    }
    /************************************************************/
    /* setTile Method */
    public void setTile(int row, int col, Tile t)
    {
        // set the Tile passed in to the position indicated by the parameters.
        // Again, throw a new IllegalArgumentException() if the values are
        // invalid for rows or columns.
        if(row > boardSize || col > boardSize){
            //throw exception if the row or col are out of boundaries
            throw new IllegalArgumentException();
        }
        //sets col in given row to t
        board.get(row).set(col,t);
    }
    /************************************************************/
    /* getValue Method */
    public int getValue(int row, int col)
    {
        // get the value at a particular position. If there is no Tile
        // at that position return -1. As always, check the values for
        // row and col and throw a new IllegalArgumentException() if necessary.
        if(row > boardSize || col > boardSize){
            //throw exception if the row or col are out of boundaries
            throw new IllegalArgumentException();
        }
        //store the tile at given row col as t
        Tile t = board.get(row).get(col);
        if(t == null){
            return -1;
        }else{
            //return the value of the tile
            return (int)t.getValue();
        }

    }
    //getters and setters for boardSize
    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public LinkedList printBoard() {
        return this.board;
    }
} // End of Board class

