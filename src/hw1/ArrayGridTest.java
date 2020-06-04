package hw1;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Welcome to the dominion of all the evil things (A.K.A. the marking tests)
 */
public class ArrayGridTest {
    private static final int WIDTH = 10;
    private static final int HEIGHT = 15;
    private Grid<String> grid;

    /**
     * Populates a new grid to be used in testing
     * This was originally used as a @Before,
     *     but that got removed because this isn't critical for all the tests
     */
    private void setup() {
        grid = new ArrayGrid<>(WIDTH, HEIGHT);

        grid.add(5, 5, "Override me");
        grid.add(5, 5, "Hello");
        grid.add(0, 0, "World");
        grid.add(0, 14, "UQ");
        grid.add(9, 0, "COMP3506");
        grid.add(9, 14, "COMP7505");
    }

    /**
     * Assert that the grid is correctly populated
     * Actually doing this properly, unlike the sample tests which don't check null cells
     */
    private void assertItems() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (i == 5 && j == 5) {
                    assertEquals("Hello", grid.get(i, j));
                } else if (i == 0 && j == 0) {
                    assertEquals("World", grid.get(i, j));
                } else if (i == 0 && j == 14) {
                    assertEquals("UQ", grid.get(i, j));
                } else if (i == 9 && j == 0) {
                    assertEquals("COMP3506", grid.get(i, j));
                } else if (i == 9 && j == 14) {
                    assertEquals("COMP7505", grid.get(i, j));
                } else {
                    assertNull(grid.get(i, j));
                }
            }
        }
    }

    /**
     * Assert that the grid passed in is empty
     */
    private void assertEmpty(Grid<?> grid, int width, int height) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                assertNull(grid.get(i, j));
            }
        }
    }

    /**
     * Basic test for the get method (almost identical to the sample tests)
     */
    @Test(timeout=1000)
    public void testGet() {
        setup();
        assertItems();
    }

    /**
     * Basic test for the remove method (almost identical to the sample tests)
     */
    @Test(timeout=1000)
    public void testRemove() {
        setup();

        assertTrue(grid.remove(5, 5));
        assertTrue(grid.remove(0, 0));
        assertTrue(grid.remove(0, 14));
        assertTrue(grid.remove(9, 0));
        assertTrue(grid.remove(9, 14));

        assertFalse(grid.remove(5, 5));
        assertEmpty(grid, WIDTH, HEIGHT);
    }

    /**
     * Basic test for the clear method
     */
    @Test(timeout=1000)
    public void testClear() {
        setup();
        grid.clear();
        assertEmpty(grid, WIDTH, HEIGHT);
    }

    /**
     * Test that the add/get/remove/clear operations work in any order on any grid
     */
    @Test(timeout=1000)
    public void testCombo() {
        int width = 1;
        int height = 1;
        Grid<Integer> grid2 = new ArrayGrid<>(width, height);
        assertEmpty(grid2, width, height);
        grid2.add(0, 0, 0);
        assertEquals(0, (int)grid2.get(0, 0));
        grid2.add(0, 0, 1);
        assertEquals(1, (int)grid2.get(0, 0));
        grid2.clear();
        assertEmpty(grid2, width, height);
        grid2.add(0, 0, 2);
        assertEquals(2, (int)grid2.get(0, 0));
        assertTrue(grid2.remove(0, 0));
        assertFalse(grid2.remove(0, 0));
        assertEmpty(grid2, width, height);
        grid2.add(0, 0, 3);
        assertEquals(3, (int)grid2.get(0, 0));
    }

    /**
     * Tests that adding/getting/removing elements outside the grid's bounds throws an error
     */
    @Test(timeout=1000)
    public void testBounds() {
        grid = new ArrayGrid<>(WIDTH, HEIGHT);

        try {
            grid.add(WIDTH, 0, "Bad");
            fail();
        } catch (IllegalArgumentException ex) {}

        try {
            grid.add(0, HEIGHT, "Bad");
            fail();
        } catch (IllegalArgumentException ex) {}

        try {
            grid.add(-1, 0, "Bad");
            fail();
        } catch (IllegalArgumentException ex) {}

        try {
            grid.add(0, -1, "Bad");
            fail();
        } catch (IllegalArgumentException ex) {}

        try {
            grid.get(WIDTH, 0);
            fail();
        } catch (IndexOutOfBoundsException ex) {}

        try {
            grid.get(0, HEIGHT);
            fail();
        } catch (IndexOutOfBoundsException ex) {}

        try {
            grid.get(-1, 0);
            fail();
        } catch (IndexOutOfBoundsException ex) {}

        try {
            grid.get(0, -1);
            fail();
        } catch (IndexOutOfBoundsException ex) {}

        try {
            grid.remove(WIDTH, 0);
            fail();
        } catch (IndexOutOfBoundsException ex) {}

        try {
            grid.remove(0, HEIGHT);
            fail();
        } catch (IndexOutOfBoundsException ex) {}

        try {
            grid.remove(-1, 0);
            fail();
        } catch (IndexOutOfBoundsException ex) {}

        try {
            grid.remove(0, -1);
            fail();
        } catch (IndexOutOfBoundsException ex) {}
    }

    /**
     * Test that it is not possible to construct a grid with invalid dimensions
     */
    @Test(timeout=1000)
    public void testDimensions() {
        try {
            grid = new ArrayGrid<>(0, 1);
            fail();
        } catch (IllegalArgumentException ex) {}

        try {
            grid = new ArrayGrid<>(1, 0);
            fail();
        } catch (IllegalArgumentException ex) {}
    }

    /**
     * Basic test for the resize method
     */
    @Test(timeout=1000)
    public void testResizeLarger() {
        setup();
        grid.resize(WIDTH + 10, HEIGHT + 10);
        assertItems();

        assertNull(grid.get(19, 24));
    }

    /**
     * Basic test for the resize method (shrinking)
     */
    @Test(timeout=1000)
    public void testResizeSmaller() {
        setup();
        grid.remove(9, 14);
        grid.remove(0, 14);
        grid.remove(9, 0);
        grid.resize(WIDTH - 1, HEIGHT - 1);

        try {
            grid.get(9, 14);
            fail();
        } catch (IndexOutOfBoundsException ex) {}
    }

    /**
     * Test that an exception is thrown when elements are lost during a resize
     */
    @Test(timeout=1000)
    public void testResizeElementsLost() {
        setup();

        try {
            grid.resize(WIDTH - 1, HEIGHT);
            fail();
        } catch (IllegalArgumentException ex) {}

        try {
            grid.resize(WIDTH, HEIGHT - 1);
            fail();
        } catch (IllegalArgumentException ex) {}

        assertItems();

        grid.resize(WIDTH + 5, HEIGHT + 5);
        grid.add(14, 19, "Item");

        try {
            grid.resize(WIDTH, HEIGHT);
            fail();
        } catch (IllegalArgumentException ex) {}
    }

    /**
     * Test that an exception is thrown if resizing to invalid dimensions
     */
    @Test(timeout=1000)
    public void testInvalidResize() {
        grid = new ArrayGrid<>(WIDTH, HEIGHT);

        try {
            grid.resize(0, HEIGHT);
            fail();
        } catch (IllegalArgumentException ex) {}

        try {
            grid.resize(WIDTH, 0);
            fail();
        } catch (IllegalArgumentException ex) {}
    }
}
