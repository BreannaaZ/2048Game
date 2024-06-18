/**
 * ...
 *
 *  @authors Bobby Caron, Matteo Ciavaglia, Charles Greco, Breanna Zinky
 *  @date 11/2/22
 *  @version 1.0
 */
//TODO: Add JavaDoc comments to necessary methods
//TODO: Add try and catch blocks to deal with the thrown exceptions
/************************************************************/
/* Tile Class */
public class Tile {
/************************************************************/
/* Variables */
private double value;
    /************************************************************/
    /* Constructors */
    public Tile() // Default constructor
    {
    this.value = 2;
    }

    public Tile(double value)
    {
        // Check that value is a power of 2
        if (power2(value))
        {
            this.value = value;
        }
        else throw new IllegalArgumentException();  // Throw exception if value is not a power of 2
    }
/************************************************************/
    /* Getters and Setters */
    public void setValue(double value)
    {
        // Check that value is a power of 2
        if (power2(value))
        {
            this.value = value;
        }
        else throw new IllegalArgumentException();  // Throw exception if value is not a power of 2
    }

    public double getValue()
    {
        return value;
    }
/************************************************************/
    /* Power2 Method */
public boolean power2(double value)
    {
        // This method will be recursive. It will recursively divide the value by 2 to determine if
        // It is a valid power of 2.
        if (value < 2) // Base case
        {
            return false;
        }
        if (value == 2.0)
        {
            return true;
        }
        return power2(value / 2.0);
    }
/************************************************************/
    /* ToString Method */
    public String toString(double value)
    {
        // Check that value is a power of 2
        if (power2(value))
        {
            String valueString = "" + value;
            return valueString;
        } else throw new IllegalArgumentException(); // Throw exception if value is not a power of 2
    }
} // End of Tile class
