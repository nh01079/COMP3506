package hw1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayGridTest {
    static final int WIDTH = 10;
    static final int HEIGHT = 15;
    Grid<String> grid;

    /**
     * Populates a new grid to be used in testing
     */
    @Before
    public void setup() {
        grid = new ArrayGrid<>(WIDTH, HEIGHT);

        grid.add(5, 5, "Override me");
        grid.add(5, 5, "Hello");
        grid.add(0, 0, "World");
        grid.add(0, 14, "UQ");
        grid.add(9, 0, "COMP3506");
        grid.add(9, 14, "COMP7505");
        try {
            grid.get(10,15);
            fail();
        }catch (IndexOutOfBoundsException e){

        }
    }

    /**
     * Assert that the grid is correctly populated
     */
    void assertItems() {
        Assert.assertEquals("Hello", grid.get(5, 5));
        Assert.assertEquals("World", grid.get(0, 0));
        Assert.assertEquals("UQ", grid.get(0, 14));
        Assert.assertEquals("COMP3506", grid.get(9, 0));
        Assert.assertEquals("COMP7505", grid.get(9, 14));

    }

    /**
     * Assert that the grid is empty
     */
    void assertEmpty() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                assertNull(grid.get(i, j));
            }
        }
    }

    @Test(timeout=1000)
    public void testGet() {
        assertItems();
    }

    @Test(timeout=1000)
    public void testRemove() {
        assertTrue(grid.remove(5, 5));
        assertTrue(grid.remove(0, 0));
        assertTrue(grid.remove(0, 14));
        assertTrue(grid.remove(9, 0));
        assertTrue(grid.remove(9, 14));

        assertFalse(grid.remove(5, 5));
        assertEmpty();
    }

    @Test(timeout = 1000)
    public void testResizeLarger() {
        grid.resize(WIDTH + 10, HEIGHT + 10);
        assertItems();

        assertNull(grid.get(19, 24));
    }

    @Test
    public void testResizeSmaller(){
        grid.clear();
        grid.add(6,9,"new");
        grid.add(0,0,"World");
        grid.resize(WIDTH-3,HEIGHT-5);
        Assert.assertEquals("World", grid.get(0, 0));
        Assert.assertEquals("new", grid.get(6, 9));
        try {
            Assert.assertEquals("COMP3506", grid.get(9, 0));
            fail();
        }
        catch (IndexOutOfBoundsException e){

        }
    }
}
