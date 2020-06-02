package hw2;

import hw2.Algorithms;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.Assert.assertEquals;

public class AlgorithmsTest {

    @Test
    public void testSortQueueExample1() {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(10);
        queue.add(9);
        queue.add(8);
        queue.add(7);
        queue.add(6);
        queue.add(5);
        queue.add(4);
        queue.add(3);
        queue.add(2);
        Algorithms.sortQueue(queue);
        assertEquals(2, (int)queue.remove());
        assertEquals(3, (int)queue.remove());
        assertEquals(4, (int)queue.remove());
        assertEquals(5, (int)queue.remove());
        assertEquals(6, (int)queue.remove());
        assertEquals(7, (int)queue.remove());
        assertEquals(8,(int)queue.remove());
        assertEquals(9,(int)queue.remove());
        assertEquals(1, queue.size());
    }

    @Test(timeout=1000)
    public void testSortQueueExample2() {
        Queue<String> queue = new LinkedList<>();
        queue.add("a");
        queue.add("z");
        queue.add("x");
        queue.add("b");
        queue.add("a");
        Algorithms.sortQueue(queue);
        assertEquals("a", queue.remove());
        assertEquals("a", queue.remove());
        assertEquals("b", queue.remove());
        assertEquals("x", queue.remove());
        assertEquals("z", queue.remove());
    }

    @Test(timeout=1000)
    public void testFindMissingNumberExample1() {
        int[] arr = {2, 4, 6, 8, 10, 12, 14, 18, 20};
        assertEquals(16, Algorithms.findMissingNumber(arr));
    }

    @Test(timeout=1000)
    public void testFindMissingNumberExample2() {
        int[] arr = {4, 1, -5};
        assertEquals(-2, Algorithms.findMissingNumber(arr));
    }
}
