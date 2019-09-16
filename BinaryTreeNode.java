package mini;
 public interface BinaryTreeNode<E>
 {

    E getData(); //to read data
    void setData(E data);   //to modify data to string
    BinaryTreeNode<E> getParent(); //returns parent
    BinaryTreeNode<E> getLeft();   //returns lef child
    void setLeft(BinaryTreeNode<E> child);
    BinaryTreeNode<E> getRight();   //returns right child
    void setRight(BinaryTreeNode<E> child);
    void removeFromParent();
    void traversePreorder(Visitor visitor);
    void traversePostorder(Visitor visitor);
    void traverseInorder(Visitor visitor);
    public interface Visitor
    {
        <E> void visit(BinaryTreeNode<E> node);
    }
}