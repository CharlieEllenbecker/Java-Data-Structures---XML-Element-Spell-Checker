package edu.uwm.cs351;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import edu.uwm.cs351.util.Element;
import edu.uwm.cs351.util.SortUtils;
import edu.uwm.cs351.util.XMLParseException;
import edu.uwm.cs351.util.XMLReader;

public class SpellCheck {

	private final String[] dictionary;
	
	public SpellCheck() throws IOException {
		ArrayList<String> temp = new ArrayList<String>();
		InputStream is = new FileInputStream(new File("lib","dictionary.txt"));
		Reader r = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(r);
		String s;
		while ((s = br.readLine()) != null) {
			temp.add(s);
		}
		br.close();
		dictionary = temp.toArray(new String[temp.size()]);
	}
	
	// anonymous class to return the comparator for string
	// used in order to create an instance of SortUtils
	private static Comparator<String> strComparator() {
		return new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				return s1.compareTo(s2);
			}
		};
	}
	
	/**
	 * Check the words in the given element.
	 * Return a list of all words that do not occur in the dictionary.
	 * @param e HTML element (ignore script and style tags!)
	 * @return non-null list of all words not occurring in the dictionary.
	 */
	public List<String> check(Element e) {
		
		// instance of SortUtils
		SortUtils<String> sorter = new SortUtils<String>(strComparator());
		
		// call recursive helper method
		ArrayList<String> arrList = gatherText(e, new ArrayList<String>());
		
		// convert ArrayList<String> to String[]
		String[] arr = new String[arrList.size()];
		
		// utilize SortUtils methods
		arrList.toArray(arr);
		sorter.mergeSort(arr);
		sorter.uniq(arr);
		
		// 'l' for the length of final array
		int l = sorter.difference(0, arr.length, 0, dictionary.length, arr, dictionary, arr);
		
		// convert String[] to List<String>
		return Arrays.asList(arr).subList(0, l);
	}
	
	/**
	 * Collect all of the words in the element e and parse out all of the punctuation 
	 * and the special characters to be put into an ArrayList<String>
	 * @param e
	 * @param arr (empty ArrayList<String> )
	 * @return Arraylist<String>
	 */
	private ArrayList<String> gatherText(Element e, ArrayList<String> arr) {
		
		// no style or script elements
		if(!e.getName().equals("style") && !e.getName().equals("script")) {
			
			// every element and text in 'e'
			for(Object o : e.contentList()) {
				
				// recursion if element
				if(o instanceof Element) 
					gatherText((Element)o, arr);
				
				// parse text and add to arr
				else {
					String s = ((String)o).toString();
					s = s.replaceAll("[.?\",!:;()]", "");
					s = s.replace("\n", " ");
					String[] sArr = s.split(" ");
					
					// add each valid string from sArr
					for(String sIn : sArr) {
						if(!sIn.equals(""))
							arr.add(sIn);
					}					
				}
			}
		}
		return arr;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		SpellCheck sc = new SpellCheck();
		for (String url : args) {
			System.out.println("Checking spelling of HTML in " + url);
			try {
				Reader r= new InputStreamReader(new BufferedInputStream(new URL(url).openStream()));
				final XMLReader t = new XMLReader(r);
				// t.addCDATA("script");
				Object next = t.readElement();
				if (next instanceof Element) { // could be null
					Element contents = (Element)next;
					if (!contents.getName().equalsIgnoreCase("html")) {
						throw new XMLParseException("element must be HTML not " + contents.getName());
					}
					System.out.println("Mispelled:");
					for (String s : sc.check(contents)) {
						System.out.println("  " + s);
					}
				} else {
					throw new XMLParseException("contents must be HTML, not " + next);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
