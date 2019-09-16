
package mini;
import java.util.Comparator;

public class BinarySearchTree<E> 
{
    protected BinaryTreeNode<E> root = null; //to get root
    private Comparator<E> comparator;
    public BinarySearchTree()
    {
        this(null);
    }
    public BinarySearchTree(Comparator<E> c)
    {
        comparator = c;
    }
    public boolean contains(E data)
    {
        return nodeContaining(data) != null;
    }
    public void add(E data)
    {
        if (root == null)
        {
            root = new LinkedBinaryTreeNode<E>(data);
        }
        BinaryTreeNode<E> n = root;
        while (true)
        {
            int compRes = compare(data, n.getData());
            if (compRes == 0)
            {
                n.setData(data);
                return;
            }
            else if (compRes < 0)
            {
                if (n.getLeft() == null)
                {
                    n.setLeft(new LinkedBinaryTreeNode<E>(data));
                    return;
                }
                n = n.getLeft();
            }
            else
            { // comparisonResult > 0
                if (n.getRight() == null)
                {
                    n.setRight(new LinkedBinaryTreeNode<E>(data));
                    return;
                }
                n = n.getRight();
            }
        }
    }

    public void remove(E data)
    {
        BinaryTreeNode<E> node = nodeContaining(data);
        if (node == null)
        {
            // No such object, do nothing.
            return;
        }
        else if (node.getLeft() != null && node.getRight() != null)
        {
            
            BinaryTreeNode<E> predecessor = predecessor(node);
            node.setData(predecessor.getData());
            node = predecessor;
        }
        // At this point node has zero or one child
        BinaryTreeNode<E> pullUp =(node.getLeft() == null) ? node.getRight() : node.getLeft();
        if (node == root)
        {
            setRoot(pullUp);
        }
        else if (node.getParent().getLeft() == node)
        {
            node.getParent().setLeft(pullUp);
        }else
        {
            node.getParent().setRight(pullUp);
        }
    }

    // Best to put the comparison code in a single place so that we don't have
    // to check for comparators and cast all over the place.

    protected int compare(E x, E y)
    {
        if (comparator == null)
        {
            return ((Comparable<E>)x).compareTo(y);
        }
        else
        {
            return comparator.compare(x, y);
        }
    }
    protected BinaryTreeNode getRoot()
    {
        return root;
    }

    protected void setRoot(BinaryTreeNode<E> node)
    {
        if (node != null)
        {
            node.removeFromParent();
        }
        root = node;
    }

    /**
     * Rotates left around the given node.
     */
    protected void rotateLeft(BinaryTreeNode<E> n) {
        if (n.getRight() == null) {
            return;
        }
        BinaryTreeNode<E> oldRight = n.getRight();
        n.setRight(oldRight.getLeft());
        if (n.getParent() == null) {
            root = oldRight;
        } else if (n.getParent().getLeft() == n) {
            n.getParent().setLeft(oldRight);
        } else {
            n.getParent().setRight(oldRight);
        }
        oldRight.setLeft(n);
    }

    /**
     * Rotates right around the given node.
     */
    protected void rotateRight(BinaryTreeNode<E> n) {
        if (n.getLeft() == null) {
            return;
        }
        BinaryTreeNode<E> oldLeft = n.getLeft();
        n.setLeft(oldLeft.getRight());
        if (n.getParent() == null) {
            root = oldLeft;
        } else if (n.getParent().getLeft() == n) {
            n.getParent().setLeft(oldLeft);
        } else {
            n.getParent().setRight(oldLeft);
        }
        oldLeft.setRight(n);
    }

    /**
     * Returns the rightmost node in the left subtree.
     */
    protected BinaryTreeNode<E> predecessor(BinaryTreeNode<E> node) {
        BinaryTreeNode<E> n = node.getLeft();
        if (n != null) {
            while (n.getRight() != null) {
                n = n.getRight();
            }
        }
        return n;
    }

    /**
     * A special helper method that returns the node containing
     * an object that compares equal to the given object.  This
     * is used in both contains and remove.
     */
    protected BinaryTreeNode<E> nodeContaining(E data) {
        for (BinaryTreeNode<E> n = root; n != null;) {
            int comparisonResult = compare(data, n.getData());
            if (comparisonResult == 0) {
                return n;
            } else if (comparisonResult < 0) {
                n = n.getLeft();
            } else {
                n = n.getRight();
            }
        }
        return null;
    }
}