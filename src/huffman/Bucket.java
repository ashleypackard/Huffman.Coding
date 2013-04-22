/**
 * 
 * @author Mike Kucharski & Ashley Packard
 * 
 */

package huffman;

public class Bucket 
{
	/* Our bucket class holds the letter and the frequency of its occurrence
	 Usually its held in nodes but because of the way it was implemented we had
	 to have an object hold all the data
	 */
	
	private char letter;
	private int frequency;

	public Bucket() {
		// '\0' is the null character, this default constructor set its to null
		this('\0', 0);
	}

	public Bucket(int inFreq) {
		// this creates the nodes that hold just the frequency
		this('\0', inFreq);
	}

	public Bucket(char inLet, int inFreq) {
		// this takes in both data types 
		letter = inLet;
		frequency = inFreq;
	}

	public char getLetter() {
		return letter;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setLetter(char ch) {
		letter = ch;
	}

	public void setFrequency(int fr) {
		frequency = fr;
	}

	public void incrementFrequency() {
		frequency++;
	}

	public String toString() {
		return "Char: '" + letter + "' Freq: " + frequency;
	}

}
