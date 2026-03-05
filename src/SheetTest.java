import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SheetTest {

    @Test
    public void testNumbersAndText() {
        Ex2Sheet sheet = new Ex2Sheet(5,5);

        sheet.set(0,0,"5");
        sheet.set(1,0,"hello");

        assertEquals("5", sheet.value(0,0));
        assertEquals("hello", sheet.value(1,0));
    }


    @Test
    public void testSimpleFormula() {

        Ex2Sheet sheet = new Ex2Sheet(5,5);

        sheet.set(0,0,"5");
        sheet.set(0,1,"=A0+3");

        sheet.eval();

        assertEquals("8.0", sheet.value(0,1));
    }


    @Test
    public void testFormulaWithMultipleReferences() {

        Ex2Sheet sheet = new Ex2Sheet(5,5);

        sheet.set(0,0,"5");
        sheet.set(0,1,"7");
        sheet.set(0,2,"=A0+A1");

        sheet.eval();

        assertEquals("12.0", sheet.value(0,2));
    }


    @Test
    public void testDependencyChain() {

        Ex2Sheet sheet = new Ex2Sheet(5,5);

        sheet.set(0,0,"5");
        sheet.set(0,1,"=A0+2");
        sheet.set(0,2,"=A1+3");

        sheet.eval();

        assertEquals("10.0", sheet.value(0,2));
    }


    @Test
    public void testParenthesesFormula() {

        Ex2Sheet sheet = new Ex2Sheet(5,5);

        sheet.set(0,0,"5");
        sheet.set(0,1,"=(A0+3)*2");

        sheet.eval();

        assertEquals("16.0", sheet.value(0,1));
    }


    @Test
    public void testOutOfBoundsReference() {

        Ex2Sheet sheet = new Ex2Sheet(5,5);

        sheet.set(0,0,"=Z1+3");

        sheet.eval();

        assertEquals("Err_Form", sheet.value(0,0));
    }


    @Test
    public void testSelfReferenceCycle() {

        Ex2Sheet sheet = new Ex2Sheet(5,5);

        sheet.set(0,0,"=A0+1");

        sheet.eval();

        assertEquals("Err_Form", sheet.value(0,0));
    }


    @Test
    public void testTwoCellCycle() {

        Ex2Sheet sheet = new Ex2Sheet(5,5);

        sheet.set(0,0,"=A1+1");
        sheet.set(0,1,"=A0+1");

        sheet.eval();

        int[][] depth = sheet.depth();

        assertEquals(-1, depth[0][0]);
        assertEquals(-1, depth[0][1]);
    }


    @Test
    public void testComplexCycle() {

        Ex2Sheet sheet = new Ex2Sheet(5,5);

        sheet.set(0,0,"=A1+1");
        sheet.set(0,1,"=A2+1");
        sheet.set(0,2,"=A0+1");

        sheet.eval();

        int[][] depth = sheet.depth();

        assertEquals(-1, depth[0][0]);
        assertEquals(-1, depth[0][1]);
        assertEquals(-1, depth[0][2]);
    }


    @Test
    public void testInvalidFormula() {

        Ex2Sheet sheet = new Ex2Sheet(5,5);

        sheet.set(0,0,"=1++2");

        sheet.eval();

        assertEquals("Err_Form", sheet.value(0,0));
    }


    @Test
    public void testLargeRowReference() {

        Ex2Sheet sheet = new Ex2Sheet(5,20);

        sheet.set(0,10,"5");
        sheet.set(0,11,"=A10+2");

        sheet.eval();

        assertEquals("7.0", sheet.value(0,11));
    }


    @Test
    public void testSaveLoad() throws Exception {

        Ex2Sheet sheet = new Ex2Sheet(5,5);

        sheet.set(0,0,"5");
        sheet.set(0,1,"=A0+3");

        sheet.save("test_sheet.txt");

        Ex2Sheet sheet2 = new Ex2Sheet(5,5);
        sheet2.load("test_sheet.txt");

        assertEquals("5", sheet2.value(0,0));
        assertEquals("8.0", sheet2.value(0,1));
    }

}