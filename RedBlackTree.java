package mini;
import java.awt.Color;
import java.util.Comparator;

public class RedBlackTree extends BinarySearchTree
{

    public RedBlackTree()
    {
        this(null);
    }
    public RedBlackTree(Comparator c)
    {
        super(c);
    }
    class Node extends LinkedBinaryTreeNode
    {
        Color color = Color.black;

        public Node(Object data)
        {
            super(data);
        }
    }
    public void add(Object data)
    {
        if (root == null)
        {
            root = new Node(data);
        }
        BinaryTreeNode n = root;
        while (true)
        {
            int comparisonResult = compare(data, n.getData());
            if (comparisonResult == 0)
            {
                n.setData(data);
                return;
            }
            else if (comparisonResult < 0)
            {
                if (n.getLeft() == null)
                {
                    n.setLeft(new Node(data));
                    adjustAfterInsertion((Node) n.getLeft());
                    break;
                }
                n = n.getLeft();
            }
            else
            {
                if (n.getRight() == null)
                {
                    n.setRight(new Node(data));
                    adjustAfterInsertion((Node) n.getRight());
                    break;
                }
                n = n.getRight();
            }
        }
    }
    private void adjustAfterInsertion(Node n)
    {
        // Step 1: color the node red
        setColor(n, Color.red);
        // Step 2: Correct double red problems, if they exist
        if (n != null && n != root && isRed(parentOf(n)))
        {
            if (isRed(siblingOf(parentOf(n))))           //recoloring if uncle is red
            {
                setColor(parentOf(n), Color.black);
                setColor(siblingOf(parentOf(n)), Color.black);
                setColor(grandparentOf(n), Color.red);
                adjustAfterInsertion(grandparentOf(n));
            }
            //RESTRUCTURE WHEN UNCLE IS BLACK(L-L,L-R)
            else if (parentOf(n) == leftOf(grandparentOf(n)))
            {
                if (n == rightOf(parentOf(n)))
                {
                    rotateLeft(n = parentOf(n));
                }
                setColor(parentOf(n), Color.black);
                setColor(grandparentOf(n), Color.red);
                rotateRight(grandparentOf(n));
            }
            //RESTRUCTURE WHEN UNCLE IS BLACK(R-R,R-L)
            else if (parentOf(n) == rightOf(grandparentOf(n)))
            {
                if (n == leftOf(parentOf(n)))
                {
                    rotateRight(n = parentOf(n));
                }
                setColor(parentOf(n), Color.black);
                setColor(grandparentOf(n), Color.red);
                rotateLeft(grandparentOf(n));
            }
        }
        setColor((Node) root, Color.black);
    }
    private Color colorOf(Node n)
    {
        return n == null ? Color.black : n.color;
    }
    private boolean isRed(Node n)
    {
        return n != null && colorOf(n) == Color.red;
    }
    private boolean isBlack(Node n)
    {
        return n == null || colorOf(n) == Color.black;
    }
    private void setColor(Node n, Color c)
    {
        if (n != null)
            n.color = c;
    }
    private Node parentOf(Node n)
    {
        return n == null ? null : (Node) n.getParent();
    }
    private Node grandparentOf(Node n)
    {
        return (n == null || n.getParent() == null) ? null : (Node) n
                .getParent().getParent();
    }
    private Node siblingOf(Node n)
    {
        return (n == null || n.getParent() == null) ? null : (n == n.getParent().getLeft()) ? (Node) n.getParent().getRight(): (Node) n.getParent().getLeft();
    }
    private Node leftOf(Node n) 
    {
        return n == null ? null : (Node) n.getLeft();
    }
    private Node rightOf(Node n)
    {
        return n == null ? null : (Node) n.getRight();
    }
}