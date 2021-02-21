/*
 * Charlie Ellenbecker
 * 351 HW #14
 * For this assignment I collaborated a little bit with Bryce Tome.
 * He helped by explaining the mergeSortMove() method. From there
 * I received some help with the SpellCheck from Natasha during her 
 * office hours.
 */
package edu.uwm.cs351.util;

import java.util.Comparator;



/**
 * An class with utility code for sorting and using sorted arrays.
 * @param K type of keys
 */
public class SortUtils<T>{
	private Comparator<T> _comparator;
	
	public SortUtils(Comparator<T> c) {
		_comparator = c;
	}
	
	/** merge sort the elements of the array
	 * @param array must not be null
	 */
	public void mergeSort(T[] array) {
		mergeSortKeep(0,array.length,array,array.clone());
	}
	
	/** Merge sort "in" array between lo and hi and put results in "out".
	 * The "in" array will be used for scratch in the same range.
	 * @param lo >= 0
	 * @param hi >= lo
	 * @param in must not be null
	 * @param out must not be null or same as in
	 */
	public void mergeSortMove(int lo, int hi, T[] in, T[] out) {
		
		if(lo == hi) return;	// base case
		
		int mid = (lo + hi)/2;
		
		mergeSortKeep(lo, mid, in, out);	// keep contents in in[]
		mergeSortKeep(mid, hi, in, out);
		
		merge(lo, mid, hi, in, out);		// merge from in[] into out[]
	}

	/** merge sort "in" array between lo and hi in place using temp array for scratch
	 * @param lo >= 0
	 * @param hi >= lo
	 * @param data must not be null, length >= hi
	 * @param temp must not be null or same as data, length >= hi
	 */
	public void mergeSortKeep(int lo, int hi, T[] data, T[] temp) {
		
		if(lo == hi || hi-lo == 1) return;	// base cases
		
		int mid = (lo + hi)/2;
		
		mergeSortMove(lo, mid, data, temp);	// move to tmp[]
		mergeSortMove(mid, hi, data, temp);
		
		merge(lo, mid, hi, temp, data);		// merge back to data[]
	}
	
	/** merge sorted lists in [lo,mid) and [mid,hi) in "in" into [lo,hi) in "out".
	 * @param lo >= 0
	 * @param mid >= lo
	 * @param hi >= mid
	 * @param in must not be null, length >= hi
	 * @param out must not be null or same as in, length >= hi
	 */
	public void merge(int lo, int mid, int hi, T[] in, T[] out) {
		
		int i = lo;
		int j = mid;
		int k = lo;
		
		while(i < mid && j < hi) {
			
			// copy index of 'i' to out
			if(_comparator.compare(in[i], in[j]) <= 0)
				out[k++] = in[i++];
			
			// copy index of 'j' to out
			else
				out[k++] = in[j++];
		}
		
		// copy over any other remaining elements
		while(j < hi)
				out[k++] = in[j++];
		
		while(i < mid)
				out[k++] = in[i++];
	}
	
	/** Write elements from sorted array in range [lo1,hi1)
	 * into out [lo1,...] as long as they
	 * don't occur in sorted array rem in range [lo2,hi2).  
	 * The result (out) will also be sorted.  
	 * @param lo1 >= 0
	 * @param hi1 >= lo1
	 * @param lo2 >= 0
	 * @param hi2 >= lo2
	 * @param in must not be null, length >= hi1
	 * @param rem must not be null, length >= hi2
	 * @param out must not be null, length >= hi1.
	 * The array out may be the same as the in, but not the same as rem.
	 * @return the index after the last element added into out.
	 */
	public int difference(int lo1, int hi1, int lo2, int hi2, T[] in, T[] rem, T[] out) {

		int i = lo1;
		int j = lo2;
		int k = lo1;
		
		while(i < hi1 && j < hi2 && k < out.length) {
			
			// unique, so copy over
			if(_comparator.compare(in[i], rem[j]) < 0)
				out[k++] = in[i++];
			
			// iterate 'j'
			else if(_comparator.compare(in[i], rem[j]) > 0)
				j++;
			
			// equal, so iterate 'i'
			else
				i++;
		}
		
		if(j == hi2) {
			
			// copy over remaining
			while(i < hi1) {
				out[k++] = in[i++];
			}
		}
		return k;
	}
	
	/**
	 * Remove duplicate elements (ones with 0 comparison)
	 * from a sorted array.  If the array is not sorted,
	 * it won't necessarily work correctly.
	 * @param array must not be null
	 * @return number of unique elements.
	 */
	public int uniq(T[] array) {
		return uniq(0,array.length,array,array);
	}
	
	/**
	 * Remove duplicate elements (one with 0 comparison)
	 * from a sorted array range [lo,hi), writing the unique elements to
	 * the second array [lo,...).
	 * @param lo >= 0
	 * @param hi >= lo
	 * @param in must not be null
	 * @param out must not be null.  This array may be the same as in without problems.
	 * @return index after unique elements
	 */
	public int uniq(int lo, int hi, T[] in, T[] out) {
		
		int i = lo;
		int j = i+1;
		int k = lo;
		
		while(i < hi) {
			
			if(j < hi) {
				
				// equal, so iterate 'j'
				if(_comparator.compare(in[i], in[j]) == 0)
					j++;
				
				// copy over and iterate both 'i' and 'j'
				else {
					out[k++] = in[i];
					i = j;
					j++;
				}
			}
			
			// copy over
			else {
				out[k++] = in[i];
				break;
			}
		}
		return k;
	}
}