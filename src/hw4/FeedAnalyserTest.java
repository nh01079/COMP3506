package hw4;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class FeedAnalyserTest {

    private FeedAnalyser sampleAnalyser;
    private static FeedItem[] sampleFeed;

    static {
        sampleFeed = new FeedItem[11];
        Iterator<FeedItem> iter = new Util.FileIterator("src/hw4/feed-sample.csv");
        while (iter.hasNext()) {
            FeedItem next = iter.next();
            sampleFeed[(int)next.getId()] = next;
        }
    }

    @Before
    public void setup() {
        sampleAnalyser = new FeedAnalyser("src/hw4/feed-sample.csv");
    }

    @Test(timeout=1000)
    public void testGetPostsBetweenDates() {
        assertEquals(Collections.singletonList(sampleFeed[6]),
                sampleAnalyser.getPostsBetweenDates("tom",
                        Util.parseDate("03/01/2019 12:00:00"),
                        Util.parseDate("03/01/2019 14:00:00")));

        assertEquals(Arrays.asList(sampleFeed[5], sampleFeed[7]),
                sampleAnalyser.getPostsBetweenDates("emily",
                        Util.parseDate("03/01/2019 12:00:00"),
                        Util.parseDate("03/01/2019 14:00:00")));

    }
    @Test
    public void testGetBetweenEdge(){
        // startDate edge case
        assertEquals(Collections.singletonList(sampleFeed[4]),
                sampleAnalyser.getPostsBetweenDates("tom",
                        Util.parseDate("03/01/2018 12:00:00"),
                        Util.parseDate("03/01/2019 12:00:00")));
        // null startDate
        assertEquals(Collections.singletonList(sampleFeed[4]),
                sampleAnalyser.getPostsBetweenDates("tom",null,
                        Util.parseDate("03/01/2019 12:00:00")));

        // endDate edge case
        assertEquals(Collections.singletonList(sampleFeed[6]),
                sampleAnalyser.getPostsBetweenDates("tom",
                        Util.parseDate("03/01/2019 12:00:00"),
                        Util.parseDate("03/01/2040 12:00:00")));
        // null endDate
        assertEquals(Collections.singletonList(sampleFeed[6]),
                sampleAnalyser.getPostsBetweenDates("tom",
                        Util.parseDate("03/01/2019 12:00:00"), null));
        // null startDate and endDate
        assertEquals(Arrays.asList(sampleFeed[4],sampleFeed[6]),
                sampleAnalyser.getPostsBetweenDates("tom",
                        null,null));

        // no match
        assertEquals(new ArrayList<>(),
                sampleAnalyser.getPostsBetweenDates("tom",
                        Util.parseDate("03/01/2038 12:00:00"),
                        Util.parseDate("03/01/2039 12:00:00")));

        // startDate > endDate
        assertEquals(new ArrayList<>(),
                sampleAnalyser.getPostsBetweenDates("tom",
                Util.parseDate("03/01/2019 12:00:00"),
                Util.parseDate("03/01/2009 12:00:00")));

    }

    @Test(timeout=1000)
    public void testGetPostAfterDate() {
        assertEquals(sampleFeed[1],
                sampleAnalyser.getPostAfterDate("james.gvr",
                        Util.parseDate("01/01/2019 07:00:00")));

        assertEquals(sampleFeed[8],
                sampleAnalyser.getPostAfterDate("hob",
                        Util.parseDate("01/01/2019 07:00:00")));
    }

    @Test(timeout = 1000)
    public void testGetPostAfterDateEdge(){
        // null return
        assertNull(sampleAnalyser.getPostAfterDate("james.gvr",
                Util.parseDate("01/01/2029 07:00:00")));
    }

    @Test(timeout=1000)
    public void testGetHighestUpvote() {
        assertEquals(sampleFeed[8], sampleAnalyser.getHighestUpvote());
        assertEquals(sampleFeed[9], sampleAnalyser.getHighestUpvote());

        //test empty exception
        for(int i =0; i<8; i++)
            sampleAnalyser.getHighestUpvote();
        try {
            sampleAnalyser.getHighestUpvote();
            fail();
        }catch (NoSuchElementException e){
            // pass
        }
    }

    @Test (timeout=1000)
    public void testGetPostsWithText() {
        assertEquals(Collections.singletonList(sampleFeed[2]),
                sampleAnalyser.getPostsWithText("jiaozi"));

        assertEquals(Arrays.asList(sampleFeed[4], sampleFeed[5], sampleFeed[9]),
                sampleAnalyser.getPostsWithText("no"));
    }

    @Test (timeout=1000)
    public void testGetPostsWithTextEdge(){
        assertEquals(Arrays.asList(sampleFeed[3],sampleFeed[4],sampleFeed[5],sampleFeed[8],sampleFeed[10]),
                sampleAnalyser.getPostsWithText("I"));
        // empty list return
        assertTrue(sampleAnalyser.getPostsWithText("<>?").isEmpty());
        // pattern length exceeds text length
        assertTrue(sampleAnalyser.getPostsWithText("asdfasdgsfijaglkjjasdg;loksajglksajgklsajsgfdsl").isEmpty());
    }
}
