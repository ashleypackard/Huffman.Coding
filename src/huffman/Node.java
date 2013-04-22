/**
 * 
 * @author Mike Kucharski & Ashley Packard
 * 
 */

package huffman;

public class Node<T> implements BinaryNodeInterface<T>
{
	// our nodes hold buckets for the data and have left and 
	// right childs are comprised of trees instead of nodes
	private T data;
	private Tree<T> left;
	private Tree<T> right;
	
	public Node(){
		this(null);
	}
	
	public Node(T data){
		this(data, null, null);
	}
	
	public Node(T inData, Tree<T> inLeft, Tree<T> inRight )
	{
		data = inData;
		left = inLeft;
		right = inRight;
	}
	
	@Override
	public Tree<T> getRightChild() {
		return right;
	}

	@Override
	public Tree<T> getLeftChild() {
		return left;
	}

	@Override
	public boolean hasLeftChild() {
		return left != null;
	}

	@Override
	public boolean hasRightChild() {
		return right != null;
	}

	@Override
	public boolean isLeaf() {
		return (left == null) && (right == null);
	}

	@Override
	public T getData() {
		return data;
	}

	@Override
	public void setData(T newData) {
		data = newData;
	}

	@Override
	public void setLeftChild(Tree<T> leftChild) {
		left = leftChild;
	}

	@Override
	public void setRightChild(Tree<T> rightChild) {
		right = rightChild;
		
	}

}
