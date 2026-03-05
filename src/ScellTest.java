import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ScellTest {

    @Test
    public void testIsNumber() {

        Scell cell = new Scell();

        assertTrue(cell.isNumber("1"));
        assertTrue(cell.isNumber("-1"));
        assertTrue(cell.isNumber("1.9"));
        assertTrue(cell.isNumber("-1.9"));

        assertFalse(cell.isNumber("@@"));
        assertFalse(cell.isNumber("-5-"));
        assertFalse(cell.isNumber("3...."));
        assertFalse(cell.isNumber("0.-"));
    }

    @Test
    public void testComputeForm() {

        Scell cell = new Scell();

        assertEquals(1.0, cell.computeForm("=1"));
        assertEquals(1.125, cell.computeForm("=(1+2)*3/(4+4)"));
        assertEquals(9.0, cell.computeForm("=(1+2)+(2*(1+2))"));
        assertEquals(13.0, cell.computeForm("=((1+(2+2)*(1+2)))"));
        assertEquals(11.0, cell.computeForm("=(2+3)*(4-2)+1"));
        assertEquals(7.0, cell.computeForm("=1+2*3"));
        assertEquals(9.0, cell.computeForm("=(1+2)*3"));
        assertEquals(2.0, cell.computeForm("=8/4"));
        assertEquals(3.5, cell.computeForm("=7/2"));
        assertEquals(14.0, cell.computeForm("=2*(3+4)"));
        assertEquals(13.0, cell.computeForm("=((1+(2+2)*(1+2)))"));
        assertEquals(6.0, cell.computeForm("=1+2+3"));
        assertEquals(5.0, cell.computeForm("=10-3-2"));
        assertEquals(11.0, cell.computeForm("=(2+3)*(4-2)+1"));

    }

    @Test
    public void testIsForm() {

        Scell cell = new Scell();

        assertTrue(cell.isForm("=A0"));
        assertTrue(cell.isForm("=(1+2)*3/(A4+4)"));
        assertTrue(cell.isForm("=1.125"));

        assertFalse(cell.isForm("=)("));
        assertFalse(cell.isForm("=1+2)*3/(4+4)"));
        assertFalse(cell.isForm("1.125"));
        assertFalse(cell.isForm("=(*1+2)"));
        assertFalse(cell.isForm("=#@$"));
        assertFalse(cell.isForm("=#"));
        assertFalse(cell.isForm("="));
        assertFalse(cell.isForm("=(1+9)#)"));
        assertFalse(cell.isForm("=1++2"));     // two operators in a row
        assertFalse(cell.isForm("=+3*2"));     // expression starts with operator
        assertFalse(cell.isForm("=3*2-"));     // expression ends with operator
        assertFalse(cell.isForm("=(*3+2)"));   // operator immediately after '('
        assertFalse(cell.isForm("=(3+2*)"));   // operator before ')'
    }
}
