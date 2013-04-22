/**
 * 
 * @author Mike Kucharski & Ashley Packard
 * 
 */

package huffman;

public class Driver 
{

	public static void main(String[] args) 
	{
		// Create the huffman code using these text files
		HuffmanCode hf = new HuffmanCode("plainText.txt","binaryText.txt", "decodedText.txt");
		System.out.println(hf.getFrequencyList());
		
		hf.getHuffmanTree().inorderTraverse();
		System.out.println();
		
		// if files exist, encode and then decode
		if(hf.encode())
			System.out.println("Your file has been encoded!");
		else
			System.out.println("Your file could not be encoded :(");
		
		if(hf.decode())
			System.out.println("Your file has been decoded!");
		else
			System.out.println("Your file could not be decoded :(");
	}

}
