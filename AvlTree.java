import java.util.ArrayList;
import java.util.Iterator;

public class AvlTree implements Iterable <Integer> {

    /* a private Node which represent the root of the tree*/
    private Node rootNode;

    /* a private Node which represent the root of the tree*/
    private int currentItems;

    /**
     * The default constructor.
     */
    public AvlTree() {
        currentItems = 0;
        rootNode = null;
    }

    /**
     * @return : the number of nodes in the tree.
     */
    public int size() {
        return currentItems;
    }

    public int contains(int searchVal){
        Node node = findNodeByValue(searchVal, rootNode);
        if(node == null){
            return -1;
        }
        return (rootNode.getHeight() - node.getHeight());
    }

    /*finds and returns the node holding the given value. if no such node exists, returns null*/
    private Node findNodeByValue(int searchVal, Node root){
        if(root == null){
            return null;
        }
        int value = root.getValue();
        if(value == searchVal){
            return root;
        }
        else if(value > searchVal){
            return findNodeByValue(searchVal, root.getRightNode());
        }
        return findNodeByValue(searchVal, root.getLeftNode());//when code gets here, the value is the left
    }

    /**
     * @return : an iterator for the Avl Tree. The returned iterator iterates over the tree nodes in an
     * ascending order, and does NOT implement the remove() method.
     */
    @Override
    public Iterator<Integer> iterator() {
        ArrayList <Integer> treeList = new ArrayList<>();
        insertTreeIntoList(rootNode, treeList);
        return treeList.iterator();
    }

    /* this private help method insert a given avl tree which it's root is node, and insert it to a given
    arrayList in ascending order*/
    private void insertTreeIntoList(Node node, ArrayList<Integer> list) {
        if (node == null) {
            return;
        }
        insertTreeIntoList(node.getLeftNode(), list);
        list.add(node.getValue());
        insertTreeIntoList(node.getRightNode(), list);
    }


}
