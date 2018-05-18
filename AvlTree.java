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
     * A constructor that builds a new AVL tree containing all unique values in the input array.
     * @param data: the values to add to tree.
     */
    public AvlTree(int [] data) {
        this();
        for (int curValue : data) {
            add(curValue);
        }
    }

    /**
     * A copy-constructor that builds the tree from existing tree
     * @param avlTree - tree to be copied
     */
    public AvlTree(AvlTree avlTree) {
        for (int value : avlTree) {
            add(value);
        }
    }


    /**
     * A method that calculates the maximum number of nodes in an AVL tree of height h,
     * @param h- height of the tree (a non-negative number).
     * @return - maximum number of nodes of height h.
     */
    public static int findMaxNodes(int h) {
        return ((int)Math.pow(2, h) - 1);
    }


    /**
     * A method that calculates the minimum numbers of nodes in an AVL tree of height h,
     * @param h - height of the tree (a non-negative number).
     * @return - minimum number of nodes of height h.
     */
    public static int findMinNodes(int h) {
        int heightMinusOne = 1;
        int heightMinusTwo = 2;
        if (h == 0) {
            return heightMinusOne;
        }
        int returnValue = 2;
        for (int i = 2; i <= h; i++) {
            returnValue = heightMinusOne + heightMinusTwo + 1;
            heightMinusOne = heightMinusTwo;
            heightMinusTwo = returnValue;
        }
        return returnValue;
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
            return findNodeByValue(searchVal, root.getLeftNode());
        }
        return findNodeByValue(searchVal, root.getRightNode());//when code gets here, the value is the left
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

    /*
    this private method receives a value to add and root, and insert it to the tree. if there is a violate
     in the tree structure, the method fix it.
     */
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
        root = checkAndRotate(root);
        return root;
    }

    /*
    this function receives a root of a local tree and preforms rotates as needed.
     */
    private Node checkAndRotate(Node root) {
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

    /*this private method receives root and update its height and balance factor*/
    private void updateHeightAndBalanceFactor(Node root) {
        int leftHeight = (root.getLeftNode() == null) ? -1 : root.getLeftNode().getHeight();
        int rightHeight = (root.getRightNode() == null) ? -1 : root.getRightNode().getHeight();
        root.setHeight(Math.max(leftHeight, rightHeight) + 1);
        root.setBalanceFactor(leftHeight - rightHeight);
    }

    /*this method handles a LR rotate*/
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

    /*this method handles a LL rotate*/
    private Node rotateLL(Node root){
        Node leftNode = root.getLeftNode();
        root.setLeftNode(leftNode.getRightNode());
        leftNode.setRightNode(root);
        updateHeightAndBalanceFactor(root);
        updateHeightAndBalanceFactor(leftNode);
        return leftNode;
    }

    /*this method handles a RL rotate*/
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

    /*this method handles a RR rotate*/
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
            this.rootNode = deleteAndFix(toDelete, rootNode);
            this.currentItems--;
            return true;
        }
        return false;
    }

    /*
    this method delete the given value from the tree rooted in root. it also fix any violation that
    occurred by the deletion
     */
    private Node deleteAndFix(int toDelete, Node root) {
        int rootValue = root.getValue();//this is not a null pointer - we know that the value is in the tree
        if (rootValue == toDelete) {
            if (isALeaf(root)) {
                return null;
            }
            if (root.getLeftNode() == null) {
                return root.getRightNode();
            }
            if (root.getRightNode() == null) {
                return root.getLeftNode();
            }
            int successorValue = getSuccessorValue(root.getRightNode());
            root.setValue(successorValue);
            root.setRightNode(deleteAndFix(successorValue, root.getRightNode()));
        }
        if(rootValue > toDelete) {
            root.setLeftNode(deleteAndFix(toDelete, root.getLeftNode()));
        }
        if(rootValue < toDelete){
            root.setRightNode(deleteAndFix(toDelete, root.getRightNode()));
        }
        updateHeightAndBalanceFactor(root);

        //check for rotates
        root = checkAndRotate(root);
        return root;
    }

    /* this help method checks if the given node is a leaf */
    private boolean isALeaf (Node node) {
        return ((node != null) && (node.getRightNode() == null) && (node.getLeftNode() == null));
    }

    private int getSuccessorValue(Node root) {
        while (root.getLeftNode() != null) {
            root = root.getLeftNode();
        }
        return root.getValue();
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
