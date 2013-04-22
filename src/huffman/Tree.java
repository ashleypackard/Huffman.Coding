/**
 * 
 * @author Mike Kucharski & Ashley Packard
 * 
 */

package huffman;

public class Tree<T> implements BinaryTreeInterface<T>
{
	// Our tree is comprised of nodes
	private Node<T> root;
	  
	public Tree() {
		root = null;
	}

	// Create root node without children
	public Tree(Node<T> rootData) {
		this(rootData, null, null);
	}

	// Create root and also initialize children
	public Tree(Node<T> rootData, Tree<T> leftTree, Tree<T> rightTree) 
	{
		setTree(rootData, leftTree, rightTree);
	}
	
	@Override
	// set the tree from the tree constructors, this creates a leaf
	public void setTree(Node<T> data) {
		setTree(data, null, null);
	}

	@Override
	// set the tree from the tree constructors
	public void setTree(Node<T> data, Tree<T> leftTree, Tree<T> rightTree) {
		root = data;
		root.setLeftChild(leftTree);
		root.setRightChild(rightTree);
	}

	// accessor methods
	@Override
	public Node<T> getRootNode() {
		return root;
	}
	
	@Override
	public T getRootData() {
		if (root != null)
			return root.getData();
		else
			return null;
	}
	
	//mutator methods
	@Override
	public void setRootData(T rootData) {
		root.setData(rootData);
	}
	
	@Override
	public void setRootNode(Node<T> rootNode) {
		root = rootNode;
	}
	
	public Tree<T> getLeftTree(){
		// tests to make sure there is a root, otherwise it'll throw an exception
		if(root == null) 
			return null;
		return root.getLeftChild();
	}
	
	public Tree<T> getRightTree(){
		// tests to make sure there is a root, otherwise it'll throw an exception
		if(root == null) 
			return null;
		return root.getRightChild();
	}
	
	@Override
	public int getNumberOfNodes() {
		// recursive! counts all the nodes starting from the top 
		// and working its way through the tree on each side
		int leftNumber = 0; 
	    int rightNumber = 0;
	    if( this.isEmpty() ) return 0; 
	    if (root.getLeftChild() != null) leftNumber = root.getLeftChild().getNumberOfNodes(); 
	    if (root.getRightChild()!=null) rightNumber = root.getRightChild().getNumberOfNodes(); 
	    return 1 + leftNumber + rightNumber; 
	}
	
	@Override
	// this is the getHieght function people call
	public int getHeight() { return getHeight(this);}
	
	// this is the getHeight function that does all the work
	private int getHeight(Tree<T> tree) {
		int height = 0;
		if (tree != null && !isEmpty()) {
			// get the height of the side with the most levels
			height = 1 + Math.max(getHeight(tree.getLeftTree()),
					getHeight(tree.getRightTree()));
		}
		return height;
	}


	@Override
	public boolean isEmpty() {
		return root == null;
	}

	@Override
	// delete the tree!
	public void clear() {
		root.setLeftChild(null);
		root.setRightChild(null);
		root = null;
	}

	// the functions that gets called by the user
	public void inorderTraverse() {
		inorderTraverse(this);
	}

	// the function that does the work!
	private void inorderTraverse(Tree<T> tree) {
		if (tree != null) {
			// start on the left, go to root, the go to right
			inorderTraverse(tree.getLeftTree());
			System.out.print(tree.getRootData() + " "); // “visit”
			inorderTraverse(tree.getRightTree());
		}
	}
	
	
}
