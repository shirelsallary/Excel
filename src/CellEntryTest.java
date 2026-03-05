import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CellEntryTest {

    @Test
    public void testValidIndices() {

        CellEntry c1 = new CellEntry("A0");
        assertTrue(c1.isValid());

        CellEntry c2 = new CellEntry("B3");
        assertTrue(c2.isValid());

        CellEntry c3 = new CellEntry("C12");
        assertTrue(c3.isValid());
    }

    @Test
    public void testInvalidIndices() {

        CellEntry c1 = new CellEntry("1A");
        assertFalse(c1.isValid());

        CellEntry c2 = new CellEntry("A");
        assertFalse(c2.isValid());

        CellEntry c3 = new CellEntry("A100");
        assertFalse(c3.isValid());

        CellEntry c4 = new CellEntry("@3");
        assertFalse(c4.isValid());
    }

    @Test
    public void testCoordinates() {

        CellEntry c = new CellEntry("C12");

        assertEquals(2, c.getX());
        assertEquals(12, c.getY());
    }
}