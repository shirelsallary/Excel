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
    public Cell_Inerface get(int x, int y) {
        return table[x][y];
    }

    @Override
    public Cell_Inerface get(String cords) {
        Cell_Inerface ans = null;
        // Add your code here

        /////////////////////
        return ans;
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
        Cell_Inerface c = new Scell(s);
        table[x][y] = c;
        // Add your code here

        /////////////////////
    }
    @Override
    public void eval() {
        int[][] dd = depth();
        // Add your code here

        // ///////////////////
    }

    @Override
    public boolean isIn(int xx, int yy) {

        // First check that the coordinates are not negative
        boolean ans = xx >= 0 && yy >= 0;

        // Now check that the coordinates are inside the spreadsheet size
        // xx must be smaller than width and yy must be smaller than height
        ans = ans && xx < width() && yy < height();

        // If all conditions are satisfied the cell is inside the table
        return ans;
    }

    @Override
    public int[][] depth() {
        int[][] ans = new int[width()][height()];
        // Add your code here

        // ///////////////////
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
        String ans = null;
        if(get(x,y)!=null) {ans = get(x,y).toString();}
        // Add your code here

        /////////////////////
        return ans;
    }
}