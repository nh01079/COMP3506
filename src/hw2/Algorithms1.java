package hw2;

import java.util.Arrays;
import java.util.Queue;

public class Algorithms1 {
    /**
     * Write your implementation of the sortQueue algorithm here
     *
     * @param queue the queue to sort
     */
    public static <T extends Comparable<T>> void sortQueue(Queue<T> queue) {
        if(queue==null||queue.size()==1)return;
        T[] item = (T[]) new Comparable[3];
        int i=0;
        while(queue.peek()!=null&&i<3){
            item[i]=queue.poll();
            i++;
        }
        if(i==2){
            if(item[1].compareTo(item[0])>0){
                queue.add(item[0]);
                queue.add(item[1]);
            }
            else{
                queue.add(item[1]);
                queue.add(item[0]);
            }
            return;
        }
        sortElements(item);
        if(queue.isEmpty()){for(T get:item){ queue.add(get);}}
        for(int count=0;count<queue.size()+1;count++) {
            T temp = item[0];
            item[0]=queue.poll();
            if(sortElements(item))count=0;
            if(temp.compareTo(item[0])<0)queue.add(temp);
            else {
                queue.add(item[0]);
                item[0]=temp;
            }
        }
        for(T get:item){ queue.add(get);}
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

    static int recurrDiff(int[] numbers, int diff){
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
