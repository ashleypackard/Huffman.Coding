/**
 * 
 * @author Mike Kucharski & Ashley Packard
 * 
 */

package huffman;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

public class HuffmanCode {
	
	private boolean filesExist, wasEncoded;
	private Scanner in;
	
	// Creates an array list of buckets to hold all our buckets and then we can order them acorrding to frequency.
	// which then we are able to create a tree
	private ArrayList<Bucket> frequencyList;
	private Tree<Bucket> huffmanTree;
	private String plain_text, binary_text, decoded_text;
	
	// hold the letter as the key and the huffman code gathered from traversing the tree as a string
	private HashMap<Character, String> codeHolder;
	
	public HuffmanCode(String p_text, String b_text, String d_text){
		plain_text = p_text;
		binary_text = b_text;
		decoded_text = d_text;
		filesExist = true;
		wasEncoded = false;
		
		File plainText = new File(plain_text);
		File binaryText = new File(binary_text);
		File decodedText = new File(decoded_text);
		
		// if the files don't exist then terminate the program
		if(!plainText.exists() || !binaryText.exists() || !decodedText.exists()){
			filesExist = false;
			System.out.println("FILES NOT FOUND!!");
			System.exit(-1);
		}
		
		frequencyList = new ArrayList<Bucket>();
		huffmanTree = new Tree<Bucket>(); 
		codeHolder = new HashMap<Character, String>();
		
		// create frequency list, construct the tree from this, and then produce all the codes to the letters.
		populateFrequencyList();
		constructTree();
		populateCodeHolder(huffmanTree, "");
	}
	
	// read in the text file and count the ocurrences of each letter and place in a bucket thats stored in an arraylist
	private void populateFrequencyList() {
		String word;
		
		try {
			// make sure file exists
			in = new Scanner(new File(plain_text));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		// while there is text in the document
		while(in.hasNext()){
			// get the next word, get rid of any special characters and trimmed, and turn all uppercase
			word = in.next();
			word = word.replace("\\", "");
			word = word.replaceAll("[!@#$%^&*()_+-={}|\\:;\",.<>?/]", "");
			word = word.trim().toUpperCase();

			if (word.length() == 0)
				continue;
			
			// for each letter in the word
			for(int i = 0; i < word.length(); i++)
			{
				boolean inList = false;
				char tempChar =	word.charAt(i);
				
				// cycle through arraylist to see if the letter already exists in a bucket
				for(int listCycle = 0; listCycle < frequencyList.size(); listCycle++)
				{
					// if there is a bucket with this letter then increase the frequency by one
					if(frequencyList.get(listCycle).getLetter() == tempChar)
					{
						frequencyList.get(listCycle).incrementFrequency();
						inList = true;
						break;
					}
				}
				
				// if there isn't a bucket with the letter add a new bucket
				if(!inList)
				{
					frequencyList.add(new Bucket(tempChar, 1));
				} // end if
			} // end for
		} // end while
		in.close();
		
		// sort the array list in increasing order
		selectionSort();
	}

	// construct the tree of nodes that are contained in the sorted array list
	private void constructTree() 
	{
		if(frequencyList.isEmpty()){
			return;
			// there are two cases if the array list is populated, first
			// if there is just one then create just a rootnode of the tree
		}else if(frequencyList.size() == 1){
			huffmanTree = new Tree<Bucket>(new Node<Bucket>(frequencyList.get(0)));
		}else{
			// else if there is more then one then create the bottom two nodes and the suseequent parent node
			// then loop through the rest of the list to create the rest of the tree, if there is only two items in
			// the list then the loop won't run
			
			// the left side is always the smallest
			Bucket left = frequencyList.get(0);
			Bucket right = frequencyList.get(1);
			
			// the parent holds just the combined frequency totals of its children
			Bucket root = new Bucket(left.getFrequency()+right.getFrequency());
			
			// create a tree of buckets for th eleft and right
			Tree<Bucket> leftTree = new Tree<Bucket>(new Node<Bucket>(left));
			Tree<Bucket> rightTree = new Tree<Bucket>(new Node<Bucket>(right));
			Node<Bucket> rootNode = new Node<Bucket>(root);
			
			huffmanTree = new Tree<Bucket>(rootNode, leftTree, rightTree);
			
			// cycle through the rest of the indexes
			for(int i = 2; i < frequencyList.size(); i++){
				Tree<Bucket> tempTree;
				
				// store the next bucket in this bucket
				Bucket nextBucket = frequencyList.get(i);
				
				// create a tree  that holds the new bucket stored in a node
				Tree<Bucket> nextTree = new Tree<Bucket>(new Node<Bucket>(nextBucket));
				
				//create new root node by adding frequencies
				int huffFreq = huffmanTree.getRootData().getFrequency();
				int nextBucketFreq = nextBucket.getFrequency();
				Node<Bucket> newRootNode = new Node<Bucket>(new Bucket(nextBucketFreq + huffFreq));
				
				// determine where to place old huffmanTree and nextTree
				if(nextBucketFreq > huffFreq){
					// if the new tree is greater then the original tree store it the right
					tempTree = new Tree<Bucket>(newRootNode, huffmanTree, nextTree);
				}else{
					// else store it the left
					tempTree = new Tree<Bucket>(newRootNode, nextTree, huffmanTree);
				}
				// reassign the tree
				huffmanTree = tempTree;
			}
		}
	}

	// populate the hashmap by traversing through the tree and getting the code
	private void populateCodeHolder(Tree<Bucket> tree, String code) 
	{
		// all letters are stored as leaves
		if (tree.getRootNode().isLeaf()) 
		{
			// once you find the leaf store its letter and code in the map
			codeHolder.put(tree.getRootData().getLetter(), code);
			return;
		}
		
		// for every left tree we traverse add a 0 to the code
		populateCodeHolder(tree.getLeftTree(), code+"0");
		// for every right tree we traverse add a 1 to the code
		populateCodeHolder(tree.getRightTree(), code+"1");
	}
	
	// encode the text document using the codes stored in hash map
	public boolean encode()
	{
		if(!filesExist)
			return false;
		
		try{
			File tf = new File(plain_text);
			
			in = new Scanner(tf);
			BufferedWriter out = new BufferedWriter(new FileWriter(binary_text));

			// read in the text file again and for every letter we come across look 
			// it up in the hash map and write into the output file
			while(in.hasNext())
			{
				String word = in.next();
				word = word.replace("\\", "");
				word = word.replaceAll("[!@#$%^&*()_+-={}|\\:;\",.<>?/]", "");
				word = word.trim().toUpperCase();
	
				if (word.length() == 0)
					continue;
	
				for (int i = 0; i < word.length(); i++) {
					out.write(getHuffmanCode(word.charAt(i)) + " ");
				}
			}
			out.close();

		}catch(FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		wasEncoded = true;
		return wasEncoded;
	}

	// decode the text document by using th einverse of the hashmap
	public boolean decode()
	{
		if(!filesExist || !wasEncoded)
			return false;
		
		try{
			File bf = new File(binary_text);
			
			in = new Scanner(bf);
			BufferedWriter out = new BufferedWriter(new FileWriter(decoded_text));

			// for every code we come across in the text file find its 
			// corresponding letter and write to output file
			while(in.hasNext())
			{
				String code = in.next();
				code = code.trim();
	
				if (code.length() == 0)
					continue;
	
				out.write(getCharFromCode(code));

			}
			out.close();

		}catch(FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	// return the code string by looking it up with the key char
	private String getHuffmanCode(char inChar) {
		return codeHolder.get(inChar);
	}
	
	// use a selectionsort to sort the data from lowest to highest
	private void selectionSort() 
	 {	
		// cycle through the arraylist and get the smallest index and swap with current index
		for (int index = 0; index < frequencyList.size() - 1; index++) {
			int indexOfNextSmallest = getIndexSmall(index, frequencyList.size() - 1);
			swap(index, indexOfNextSmallest);
		}
	}

	// find the smallest index after the current index
	private int getIndexSmall(int first, int last) {
		Bucket min = frequencyList.get(first);
		int indexOfMin = first;
		for (int index = first + 1; index <= last; index++) {
			if (frequencyList.get(index).getFrequency() < min.getFrequency()) 
			{
				min = frequencyList.get(index);
				indexOfMin = index;
			}
		}
		return indexOfMin;
	}

	private void swap(int i, int j) 
	{
		Bucket temp = frequencyList.get(i);
		frequencyList.set(i, frequencyList.get(j));
		frequencyList.set(j, temp);
	}
	
	// fancy for loop that cycles through the hash map and looks at the 
	// characters and find the matching code that we supplied. returns the character
	private char getCharFromCode(String code)
	{
		for (Entry<Character, String> entry : codeHolder.entrySet()) {
            if (entry.getValue().equals(code)) {
                return entry.getKey();
            }
        }
		
		// else if not found returns null
		return '\0';
		
	}
	
	public ArrayList<Bucket> getFrequencyList()
	{
		return frequencyList;
	}
	
	public Tree<Bucket> getHuffmanTree()
	{
		return huffmanTree;
	}
}
