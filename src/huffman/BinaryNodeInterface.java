/**
 * 
 * @author Mike Kucharski & Ashley Packard
 * 
 */

package huffman;

public interface BinaryNodeInterface <T>
{
	// Necessary functions for the node class that has a right and left child
	// nodes are made up of buckets
	public T getData();
	public void setData(T newData);
	
	public Tree<T> getLeftChild();
	public Tree<T> getRightChild();
	
	public void setLeftChild(Tree<T> leftChild);
	public void setRightChild(Tree<T> rightChild);// Sets right child node.

	public boolean hasLeftChild(); 	
	public boolean hasRightChild(); 	

	public boolean isLeaf (); 

}
