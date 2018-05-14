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

    /**
     * Check whether the tree contains the given input value.
     * @param searchVal - value to search for
     * @return - if val is found in the tree, return the depth of the node (0 for the root) with the
     * given value if it was found in the tree, -1 otherwise.
     */
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
     * Add a new node with the given key to the tree.
     * @param newValue - the value of the new node to add.
     * @return - true if the value to add is not already in the tree and it was successfully
     * added, false otherwise.
     */
    public boolean add(int newValue){
        if(contains(newValue) == -1){
            this.rootNode = addAndFix(newValue, rootNode);
            this.currentItems++;
            return true;
        }
        return false;
    }

    private Node addAndFix(int newValue, Node root){
        if(root == null){
            return new Node(newValue);
        }
        int value = root.getValue();
        if(value > newValue) {
            root.setLeftNode(addAndFix(newValue, root.getLeftNode()));
        }
        if(value < newValue){
            root.setRightNode(addAndFix(newValue, root.getRightNode()));
        }

        //updating height and balance factor for each node.
        updateHeightAndBalanceFactor(root);

        //checking if we need to rotate
        int currentBalanceFactor = root.getBalanceFactor();
        if(currentBalanceFactor == 2 && root.getLeftNode() != null){
            if(root.getLeftNode().getBalanceFactor() == -1){
                root = rotateLR(root);
            }
            else{
                root = rotateLL(root);
            }
        }
        else if (currentBalanceFactor == -2 && root.getLeftNode() != null){
            if(root.getRightNode().getBalanceFactor() == 1){
                root = rotateRL(root);
            }
            else{
                root = rotateRR(root);
            }
        }
        return root;
    }

    private void updateHeightAndBalanceFactor(Node root) {
        int leftHeight = (root.getLeftNode() == null) ? -1 : root.getLeftNode().getHeight();
        int rightHeight = (root.getRightNode() == null) ? -1 : root.getRightNode().getHeight();
        root.setHeight(Math.max(leftHeight, rightHeight) + 1);
        root.setBalanceFactor(leftHeight - rightHeight);
    }

    private Node rotateLR(Node root){
        Node leftNode = root.getLeftNode();
        Node grandSonRight = leftNode.getRightNode();
        leftNode.setRightNode(grandSonRight.getLeftNode());
        grandSonRight.setLeftNode(leftNode);
        root.setLeftNode(grandSonRight);
        updateHeightAndBalanceFactor(leftNode);
        updateHeightAndBalanceFactor(grandSonRight);
        return rotateLL(root);
    }
    private Node rotateLL(Node root){
        Node leftNode = root.getLeftNode();
        root.setLeftNode(leftNode.getRightNode());
        leftNode.setRightNode(root);
        updateHeightAndBalanceFactor(root);
        updateHeightAndBalanceFactor(leftNode);
        return leftNode;
    }

    private Node rotateRL(Node root){
        Node rightNode = root.getRightNode();
        Node grandSonLeft = rightNode.getLeftNode();
        rightNode.setLeftNode(grandSonLeft.getRightNode());
        grandSonLeft.setRightNode(rightNode);
        root.setRightNode(grandSonLeft);
        updateHeightAndBalanceFactor(rightNode);
        updateHeightAndBalanceFactor(grandSonLeft);
        return rotateRR(root);
    }
    private Node rotateRR(Node root){
        Node rightNode = root.getRightNode();
        root.setRightNode(rightNode.getLeftNode());
        rightNode.setLeftNode(root);
        updateHeightAndBalanceFactor(root);
        updateHeightAndBalanceFactor(rightNode);
        return rightNode;
    }

    /**
     *Removes the node with the given value from the tree, if it exists.
     * @param toDelete - the value to remove from the tree.
     * @return - true if the given value was found and deleted, false otherwise.
     */
    public boolean delete(int toDelete){
        if(contains(toDelete) != -1){
            this.rootNode = addAndFix(toDelete, rootNode);
            this.currentItems--;
            return true;
        }
        return false;
    }

    private Node deleteAndFix(int toDelete, Node root){
        



        return null;
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
