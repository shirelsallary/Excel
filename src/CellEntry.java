
// This class represents a spreadsheet cell index such as "A0", "B3", "C12".
// It implements the Index2D interface and converts the string representation
// into numeric coordinates (x,y).

public class CellEntry implements Index2D{
    // The original string representation of the cell index
    private String index;

    // Constructor that receives the cell index as a string
    public CellEntry(String index) {
        this.index = index;
    }

    // Returns the original string representation of the index
    @Override
    public String toString() {
        return index;
    }

    // Checks if the index string is valid according to the rules:
// First character must be a letter A-Z
// Remaining characters must represent a number between 0 and 99
    @Override
    public boolean isValid() {

        // Index cannot be null and must contain at least two characters
        if (index == null || index.length() < 2) {
            return false;
        }

        // Extract the first character (column letter)
        char letter = Character.toUpperCase(index.charAt(0));

        // Check that the first character is between A and Z
        if (letter < 'A' || letter > 'Z') {
            return false;
        }

        try {
            // Parse the numeric part (row index)
            int y = Integer.parseInt(index.substring(1));
            // Row must be between 0 and 99
            return y >= 0 && y <= 99;
        } catch (Exception e) {
            // If parsing fails the index is invalid
            return false;
        }
    }

    // Returns the column index (x coordinate)
// Example: A0 → 0, B3 → 1, C12 → 2
    @Override
    public int getX() {

        // If the index is invalid return error value
        if (!isValid()) {
            return Ex2Utils.ERR;
        }

        // Convert column letter to numeric index
        char letter = Character.toUpperCase(index.charAt(0));

        return letter - 'A';
    }

    // Returns the row index (y coordinate)
// Example: A0 → 0, B3 → 3, C12 → 12
    @Override
    public int getY() {

        // If the index is invalid return error value
        if (!isValid()) {
            return Ex2Utils.ERR;
        }

        // Parse the numeric part of the index
        return Integer.parseInt(index.substring(1));
    }
}
