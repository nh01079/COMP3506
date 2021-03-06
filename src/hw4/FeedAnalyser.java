package hw4;

import java.util.*;

/**
 * Class that implements the social media feed searches
 */
public class FeedAnalyser {
    private final Map<String,List<FeedItem>> userMap = new HashMap<>(); // store FeedItem based on username
    private final List<FeedItem> voteList = new ArrayList<>();  // sort FeedItems by vote counts
    private final List<FeedItem> idList = new ArrayList<>();    // sort FeedItems by id

    private static final Comparator<FeedItem> dateComp = new Comparator<FeedItem>() {  // FeedItem comparator for dates
        @Override
        public int compare(FeedItem o1, FeedItem o2) {
            return o1.getDate().compareTo(o2.getDate());
        }
    };
    private static final Comparator<FeedItem> voteComp = new Comparator<FeedItem>() {   // FeedItem comparator for upvotes
        @Override
        public int compare(FeedItem o1, FeedItem o2) {
            return o2.getUpvotes()-o1.getUpvotes();
        }
    };

    private static final Comparator<FeedItem> idComp = new Comparator<FeedItem>() {
        @Override
        public int compare(FeedItem o1, FeedItem o2) {
            if(o1.getId()==o2.getId())return 0;
            return (o1.getId()-o2.getId())>0? 1:-1;
        }
    };

    /**
     * Loads social media feed data from a file
     *
     * @param filename the file to load from
     *
     * time complexity: O(nlogn)
     * space complexity: O(n)
     */
    public FeedAnalyser(String filename) {
        Iterator<FeedItem> iter = new Util.FileIterator(filename);
        while (iter.hasNext()) {
            FeedItem item = iter.next();
            if(!userMap.containsKey(item.getUsername())) userMap.put(item.getUsername(),new ArrayList<>());
            userMap.get(item.getUsername()).add(item);
            int index = Collections.binarySearch(voteList,item,voteComp);   //find the appropriate index to perform insertion
            if(index<0) index = -(index+1);
            voteList.add(index,item);
            idList.add(item);
        }
        // voteList.sort(voteComp);
    }

    /**
     * Return all feed items posted by the given username between startDate and endDate (inclusive)
     * If startDate is null, items from the beginning of the history should be included
     * If endDate is null, items until the end of the history should be included
     * The resulting list should be ordered by the date of each FeedItem
     * If no items that meet the criteria can be found, the empty list should be returned
     *
     * @param username the user to search the posts of
     * @param startDate the date to start searching from
     * @param endDate the date to stop searching at
     * @return a list of FeedItems made by username between startDate and endDate
     *
     * @require username != null
     * @ensure result != null
     *
     * Time complexity: O(logn)
     * Space complexity: O(1)
     */
    public List<FeedItem> getPostsBetweenDates(String username, Date startDate, Date endDate) {
        List<FeedItem> list = userMap.get(username);
        int start, end;
        if(startDate!=null) {
            if(endDate!=null && startDate.compareTo(endDate)>0) return new ArrayList<>(); // assure that startDate>endDate
            start = Collections.binarySearch(list, new FeedItem(-1, "", startDate, -1, ""), dateComp);
            if (start < 0) start = -(start + 1); // if not found
        }else start = 0;
        if(endDate!=null) {
            end = Collections.binarySearch(list, new FeedItem(-1, "", endDate, -1, ""), dateComp);
            if (end < 0) end = -(end + 1);
        }else end = list.size();
        return list.subList(Math.min(list.size(),start), Math.min(end, list.size()));
    }

    /**
     * Return the first feed item posted by the given username at or after searchDate
     * That is, the feed item closest to searchDate that is greater than or equal to searchDate
     * If no items that meet the criteria can be found, null should be returned
     *
     * @param username the user to search the posts of
     * @param searchDate the date to start searching from
     * @return the first FeedItem made by username at or after searchDate
     *
     * @require username != null && searchDate != null
     *
     * Time complexity: O(logn)
     * Space complexity: O(1)
     */
    public FeedItem getPostAfterDate(String username, Date searchDate) {
        List<FeedItem> list = userMap.get(username);
        int index = Collections.binarySearch(list,new FeedItem(-1,"",searchDate,-1,""), dateComp);
        if(index<0) index = -(index+1); // if no exact match is found
        return index==list.size() ? null: list.get(index); // return null if no items that meet the criteria can be found
    }

    /**
     * Return the feed item with the highest upvote
     * Subsequent calls should return the next highest item
     *     i.e. the nth call to this method should return the item with the nth highest upvote
     * Posts with equal upvote counts can be returned in any order
     *
     * @return the feed item with the nth highest upvote value,
     *      where n is the number of calls to this method
     * @throws NoSuchElementException if all items in the feed have already been returned
     *      by this method
     *
     * time complexity: O(1)
     * space complexity: O(1)
     */
    public FeedItem getHighestUpvote() throws NoSuchElementException {
        if(voteList.isEmpty()) throw new NoSuchElementException("All items in the feed have already been returned");
        return voteList.remove(0);
    }


    /**
     * Return all feed items containing the specific pattern in the content field
     * Case should not be ignored, eg. the pattern "hi" should not be matched in the text "Hi there"
     * The resulting list should be ordered by FeedItem ID
     * If the pattern cannot be matched in any content fields the empty list should be returned
     *
     * @param pattern the substring pattern to search for
     * @return all feed items containing the pattern string
     *
     * @require pattern != null && pattern.length() > 0
     * @ensure result != null
     *
     * time complexity(KMP): O( n*(pattern.length()+content.length()))
     * time complexity(RK): O( n*(pattern.length()-content.length()))
     * space complexity: O(n)
     */
    public List<FeedItem> getPostsWithText(String pattern) {
        List<FeedItem> out = new ArrayList<>();
        for(FeedItem item: idList){
            //if(item.getContent().contains(pattern)) out.add(item);    // baseline
            //if(kmpContains(item.getContent(),pattern)) out.add(item); // kmp algorithm
            if(rkContains(item.getContent(),pattern)) out.add(item);    // rk algorithm
        }
        return out;
    }

    /**
     * KMP algo identifies recurring characters within the pattern to speed up searches
     * @param text
     * @param pattern
     * @return true if text contains pattern
     * time complexity O(m+n)
     * space complexity O(m)
     */
    private static boolean kmpContains(String text, String pattern){
        if(pattern.length()>text.length()) return false;
        int[] kmp = new int[pattern.length()];
        for(int i = 0, j=0; i<pattern.length();i++){
            // looking for repeating characters within the pattern
            // O(pattern.length)
            kmp[i] = 0; // default val
            if(i!=j && pattern.charAt(i)==pattern.charAt(j)) {
                kmp[i] = kmp[i-1] + 1;
                j++;
            }else j=0;
        }
        for(int i = 0, j=0 ; i<text.length(); i++){
            // O(text.length)
            if(text.charAt(i)==pattern.charAt(j)){
                j++; // move the pointer of pattern
                if(j==pattern.length())return true;
            }else{
                if(j!=0) j = kmp[j];
            }
        }
        return false;
    }

    /**
     * Rabin-karp algo generates a hashvalue for the pattern and perform a rolling hash match on the text
     * @param text
     * @param pattern
     * @return true if text contains the pattern
     * worst time complexity O(nm)
     * expected time complexity O(n-m+1)
     * space O(m)
     */
    private static boolean rkContains(String text, String pattern){
        if(pattern.length()>text.length()) return false;
        long pHash = 0;
        long tHash = 0;
        int len = pattern.length();
        for(int i = 0; i<pattern.length(); i++){
            pHash = simpleHash(pattern.charAt(i), (char)0,pHash,len);
            tHash = simpleHash(text.charAt(i), (char)0, tHash,len);
        }
        for(int index = len; index<text.length()+1; index++){
            if(pHash==tHash){   // check if it is a match
                int i;
                for(i=0; i<len; i++)
                    if(pattern.charAt(i)!=text.charAt(index-len+i)) break;
                if(i==len)return true;  // continue to search for next matching hash values
            }
            if(index<text.length()) tHash = simpleHash(text.charAt(index),text.charAt(index-len),tHash,len);
        }
        return false;
    }

    /**
     * Rolling hash function to support searching operations of Rabin-Karp algorithm
     * @param in character to be added to the hash value
     * @param out character to be removed from the hash value
     * @param hash current hash value
     * @param len length of the pattern to determine the order of
     * @return modified hash value
     * time complexity: O(1)
     */
    private static long simpleHash(char in, char out, long hash, int len){
        final int PRIME = 39; // 17 as prime num
        if(out!=(char)0) hash -= (int)(out) * Math.pow(PRIME,len-1);
        hash *= PRIME;
        hash += in;
        return hash;
    }


    public static void main(String[] args) {
        String pattern = "abcd";
        String text = "abcdabcd";
        System.out.println(kmpContains(text,pattern));
        kmpContains(pattern,pattern);
        System.out.println(rkContains(text,pattern));
    }
}
