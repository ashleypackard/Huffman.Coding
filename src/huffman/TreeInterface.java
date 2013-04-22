/**
 * 
 * @author Mike Kucharski & Ashley Packard
 * 
 */

package huffman;

public interface TreeInterface <T> {
	
	// Templated Tree interface with the necessary functions for dealing with trees
	
	// Our Tree is made up of nodes which are made up of buckets
	public Node<T> getRootNode();
	public T getRootData();
	public void setRootData(T rootData);
	public void setRootNode(Node<T> rootNode);
	public int getHeight();
	public int getNumberOfNodes();
	public boolean isEmpty();
	public void clear();
}
