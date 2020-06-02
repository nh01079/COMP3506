package hw1;

import java.util.Arrays;

/**
 * A 2D grid implemented as an array.
 * Each (x,y) coordinate can hold a single item of type <T>.
 *
 * @param <T> The type of element held in the data structure
 *
 * 	ArrayGrid.ArrayGrid:O(m*n);
 * 	resize:O(m*n);
 *
 */
public class ArrayGrid<T> implements Grid<T> {

	/**
	 * Constructs a new ArrayGrid.ArrayGrid object with a given width and height.
	 *
	 * @param width The width of the grid
	 * @param height The height of the grid
     * @throws IllegalArgumentException If the width or height is less than or equal to zero
	 */
	private T array[][];

	public ArrayGrid(int width, int height) throws IllegalArgumentException {
	    // TODO: implement the constructor
		if(width<=0||height<=0)throw new IllegalArgumentException();
		array = (T[][]) new Object[height][width];
	}

	// TODO: implement all of hw1.Grid's methods here
	public void add(int x, int y, T element) throws IllegalArgumentException{
		if(x<0||x>=array[0].length||y<0||y>=array.length)throw new IllegalArgumentException();
		array[y][x]=element;
	}

	public T get(int x, int y) throws IndexOutOfBoundsException{
		if(x<0||x>=array[0].length||y<0||y>=array.length)throw new IndexOutOfBoundsException();
		return array[y][x];
	}

	public boolean remove(int x, int y) throws IndexOutOfBoundsException{
		if (get(x,y)==null) return false;
		array[y][x]=null;
		return true;
	}

	public void clear(){
		array= (T[][]) new Object[array.length][array[0].length];
	}

	public void resize(int newWidth, int newHeight) throws IllegalArgumentException{
		if(newHeight<array.length) {
			for (int i = newHeight; i < array.length; i++) {
				for (T item : array[i]) {
					if (item != null) throw new IllegalArgumentException();
				}
			}
		}
		if(newWidth<array[0].length) {
			for (int i = 0; i < newHeight; i++) {
				for (int j = newWidth; j < array[0].length; j++) {
					if (array[i][j] != null) throw new IllegalArgumentException();
				}
			}
		}
		T[][] temp = (T[][]) new Object[newHeight][newWidth];
		for(int i=0; i<Math.min(newHeight,array.length);i++){
			temp[i]=Arrays.copyOf(array[i],newWidth);
		}
		array=temp;
	}

}