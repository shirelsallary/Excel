
public interface Index2D {
    /**
     *
     * @return the cell index representation in form of a spreadsheet (e.g., "B3").
     */
    public String toString();

    /**
     * checks of the string representation of this index is valid "XY" as X is a letter "A-Z" (or "a-z"), and Y is an integer [0-99].
     * @return true iff this is a valid 2D index.
     */
    public boolean isValid();

    /**
     *
     * @return the x value (integer) of this index
     */
    public int getX();
    /**
     * @return the y value (integer) of this index
     */
    public int getY();
}