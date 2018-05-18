package oop.ex4.data_structures;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * this class implements a wrapper for a tree iterator
 */
public class TreeIteratorWrapper implements Iterator <Integer>  {

    private ArrayList<Integer> treeList = new ArrayList<>();
    private Iterator <Integer> iterator;

    /**
     * this constructor receives the root node of a tree and insert the tree roted in this node to an
     * array list
     * @param treeRoot : a root of a given tree.
     */
    public TreeIteratorWrapper(Node treeRoot) {
        insertTreeIntoList(treeRoot);
        iterator = treeList.iterator();
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasNext(){
        return iterator.hasNext();
    }

    /**
     * {@inheritDoc}
     */
    public Integer next(){
        return iterator.next();
    }


    /* this private help method insert a given avl tree which it's root is node, and insert it to a given
    arrayList in ascending order*/
    private void insertTreeIntoList(Node node) {
        if (node == null) {
            return;
        }
        insertTreeIntoList(node.getLeftNode());
        treeList.add(node.getValue());
        insertTreeIntoList(node.getRightNode());
    }

    /**
     * {@inheritDoc}
     * this method is not supported by AVL tree iterator
     */
    public void remove() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("remove");
    }
}
