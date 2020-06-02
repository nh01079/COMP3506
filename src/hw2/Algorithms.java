package hw2;

import java.util.Arrays;
import java.util.Objects;
import java.util.Queue;

/**
 * Write your solution to this assignment in this class
 */
public class Algorithms {

    /**
     * Write your implementation of the sortQueue algorithm here
     *
     * @param queue the queue to sort
     */
    public static <T extends Comparable<T>> void sortQueue(Queue<T> queue) {
        // O(n^2)
        if(queue==null||queue.size()==1)return;               // only one element in queue
        T min;
        for(int i= queue.size(); i>0; i--){
            min = queue.poll();
            for(int j = 0; j<i-1; j++){
                if(min.compareTo(queue.peek())>=0){
                    queue.add(min);
                    min = queue.poll();                 // keep the minimum element
                } else
                    queue.add(queue.poll());
            }
            for(int j = queue.size()-i;j>-1;j--)       //reorder the queue
                queue.add(queue.poll());
            queue.add(min);
        }
    }

    private static <T extends Comparable<T>> boolean sortElements(T[] item){
        boolean flag = false;
        if(item[1].compareTo(item[0])<0){
            T temp = item[0];
            item[0]= item[1];
            item[1]=temp;
            flag=true;
        }
        if(item[2].compareTo(item[1])<0){
            T temp = item[1];
            item[1]= item[2];
            item[2]=temp;
            flag=true;
        }
        return flag;
    }

    /**
     * Write your implementation of the findMissingNumber algorithm here
     *
     * @param numbers the arithmetic sequence
     * @return the missing number in the sequence
     */
    public static int findMissingNumber(int[] numbers) {
        int diff = numbers[1]-numbers[0];
        return recurrDiff(numbers,diff);
    }

    private static int recurrDiff(int[] numbers, int diff){
        if(numbers.length==1)return 0;
        if(numbers.length==2){
            if(numbers[0]+diff==numbers[1])return 0;
            return numbers[0]+diff;
        }
        int mid = numbers.length/2;
        if(numbers[mid-1]+diff!=numbers[mid])return numbers[mid-1]+diff;
        return recurrDiff(Arrays.copyOfRange(numbers,0,mid),diff)+
                recurrDiff(Arrays.copyOfRange(numbers,mid,numbers.length),diff);
    }
}
