import java.io.IOException;


public class Ex2Sheet implements Sheet {
    private Cell_Inerface[][] table;


    // Constructor: creates a spreadsheet of size x*y and initializes all cells as empty
    public Ex2Sheet(int x, int y) {

        table = new Scell[x][y]; // create the 2D table of cells

        for(int i = 0; i < x; i = i + 1) { // iterate over columns
            for(int j = 0; j < y; j = j + 1) { // iterate over rows

                table[i][j] = new Scell(Ex2Utils.EMPTY_CELL); // initialize each cell as empty
            }
        }

        eval(); // compute initial values of the sheet
    }
    public Ex2Sheet() {
        this(Ex2Utils.WIDTH, Ex2Utils.HEIGHT);
    }

    @Override
    public String value(int x, int y) {
        String ans = Ex2Utils.EMPTY_CELL;
        // Add your code here

        Cell_Inerface c = get(x,y);
        if(c!=null) {ans = c.toString();}

        /////////////////////
        return ans;
    }

    @Override
    public Cell_Inerface get(String cords) {

        CellEntry entry = new CellEntry(cords); // parse the spreadsheet coordinate (e.g., "B3")

        if(!entry.isValid()) { // invalid coordinate format
            return null;
        }

        int x = entry.getX(); // column index
        int y = entry.getY(); // row index

        if(!isIn(x,y)) { // check bounds of the sheet
            return null;
        }

        return table[x][y]; // return the requested cell
    }

    @Override
    public int width() {
        return table.length;
    }
    @Override
    public int height() {
        return table[0].length;
    }
    @Override
    public void set(int x, int y, String s) {

        if(!isIn(x,y)) { // ignore if coordinates are outside the sheet
            return;
        }
        Cell_Inerface c = new Scell(s); // create a new cell with the given content
        table[x][y] = c; // place the cell in the table
    }
    @Override
    public void eval() {
        int[][] dd = depth();
        // Add your code here

        // ///////////////////
    }

    @Override
    public boolean isIn(int xx, int yy) {
        boolean ans = xx >= 0 && yy >= 0; // coordinates must be non-negative
        ans = ans && xx < width() && yy < height(); // must be inside sheet bounds
        return ans;
    }

    @Override
    public void load(String fileName) throws IOException {
        // Add your code here

        /////////////////////
    }

    @Override
    public void save(String fileName) throws IOException {
        // Add your code here

        /////////////////////
    }
    @Override
    public String eval(int x, int y) {

        if(!isIn(x,y)) { // coordinates outside sheet
            return Ex2Utils.EMPTY_CELL;
        }

        Cell_Inerface cell = table[x][y]; // get the cell
        String text = cell.getData(); // original cell content

        if(text == null || text.isEmpty()) { // empty cell
            return Ex2Utils.EMPTY_CELL;
        }

        Scell sc = (Scell) cell; // cast so we can use Scell methods

        if(sc.isNumber(text) || sc.isText(text)) { // plain number or text
            return text;
        }

        if(sc.isForm(text)) { // formula case

            try {

                String formula = text.substring(1); // remove '='

                formula = resolveCellReferences(formula); // replace A0,B1 etc with values

                if(formula.equals("Err_Form")) { // reference error
                    return "Err_Form";
                }

                return String.valueOf(sc.computeForm(formula)); // compute numeric expression

            }
            catch(Exception e) {
                return "Err_Form";
            }
        }

        return "Err_Form";
    }

    // Replaces cell references (like A0, B3) with their computed values
    private String resolveCellReferences(String formula) {

        StringBuilder resolved = new StringBuilder(); // build the new formula
        int i = 0; // pointer that scans the formula

        while (i < formula.length()) {

            char c = formula.charAt(i);

            // if we see a letter → start of a cell reference
            if (Character.isLetter(c)) {

                int col = Character.toUpperCase(c) - 'A'; // convert A,B,C → 0,1,2

                int numberStart = i + 1; // start reading the row number

                // read all digits of the row (for cases like A12)
                while (numberStart < formula.length() &&
                        Character.isDigit(formula.charAt(numberStart))) {
                    numberStart++;
                }

                int row = Integer.parseInt(formula.substring(i + 1, numberStart)); // parse row index

                // check if the reference is inside the sheet
                if (!isIn(col, row)) {
                    return "Err_Form";
                }

                // compute the referenced cell value
                String cellValue = eval(col, row);

                // if referenced cell is invalid → propagate error
                if (cellValue.equals("Err_Form")) {
                    return "Err_Form";
                }

                resolved.append(cellValue); // insert the value instead of A0/B1 etc.

                i = numberStart; // jump forward after the reference

            } else {

                resolved.append(c); // copy normal characters (+ - * / etc.)
                i++;
            }
        }

        return resolved.toString(); // return the new numeric formula
    }


}