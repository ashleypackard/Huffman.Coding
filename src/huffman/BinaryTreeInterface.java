/**
 * 
 * @author Mike Kucharski & Ashley Packard
 * 
 */

package huffman;

public interface BinaryTreeInterface<T> extends TreeInterface<T>
{
	// Extension of tree interface that sets the tree
    public void setTree(Node<T> data); 
    public void setTree(Node<T> data, Tree<T> leftTree, Tree<T> rightTree); 
}
