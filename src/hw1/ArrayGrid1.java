package hw1;

import java.util.Arrays;

/**
 * A 2D grid implemented as an array.
 * Each (x,y) coordinate can hold a single item of type <T>.
 *
 * @param <T> The type of element held in the data structure
 *
 *     construtor: O(m)
 *     add:O(n)
 *     resize:O(m*n)
 */
public class ArrayGrid1<T> implements Grid<T> {

	/**
	 * Constructs a new ArrayGrid.ArrayGrid object with a given width and height.
	 *
	 * @param width The width of the grid
	 * @param height The height of the grid
     * @throws IllegalArgumentException If the width or height is less than or equal to zero
	 */
	private T array[][];
	private int width;

	public ArrayGrid1(int width, int height) throws IllegalArgumentException {
	    // TODO: implement the constructor
		if(width<=0||height<=0)throw new IllegalArgumentException();
		this.width=width;
		array = (T[][]) new Object[height][];
	}

	// TODO: implement all of hw1.Grid's methods here
	public void add(int x, int y, T element) throws IllegalArgumentException{
		if(x<0||x>=width||y<0||y>=array.length)throw new IllegalArgumentException();
		if(array[y]==null) array[y] = (T[]) new Object[width];
		array[y][x]=element;
	}

	public T get(int x, int y) throws IndexOutOfBoundsException{
		if(x<0||x>=width||y<0||y>=array.length)throw new IndexOutOfBoundsException();
		if(array[y]==null)return null;
		return array[y][x];
	}

	public boolean remove(int x, int y) throws IndexOutOfBoundsException{
		if (get(x,y)==null) return false;
		array[y][x]=null;
		return true;
	}

	public void clear(){
		array= (T[][]) new Object[array.length][];
	}

	public void resize(int newWidth, int newHeight) throws IllegalArgumentException{
		// time complexity: O(n + m + n*m)
		if(newHeight<=0||newWidth<=0)throw new IllegalArgumentException();
		if(newHeight<array.length) {
			for (int i = newHeight; i < array.length; i++) {
				if(array[i]==null)continue;
				for (T item : array[i]) {
					if (item != null) throw new IllegalArgumentException();
				}
			}
		}
		if(newWidth<array[0].length) {
			for (int i = 0; i < newHeight; i++) {
				if(array[i]==null)continue;
				for (int j = newWidth; j < array[0].length; j++) {
					if (array[i][j] != null) throw new IllegalArgumentException();
				}
			}
		}
		T[][] temp = (T[][]) new Object[newHeight][];
		for(int i=0; i<Math.min(newHeight,array.length);i++){
			if(array[i]!=null) temp[i]=Arrays.copyOf(array[i],newWidth);
		}
		array=temp;
		width=newWidth;
	}

}