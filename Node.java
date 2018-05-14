public class Node {

    private Node rightNode;
    private Node leftNode;
    private int value;

    /*the height of the node - a node w/o any children will has a default height of 0*/
    private int height;
    /*the balance factor of the node. calculated by - (BF = height of left node - height of right node)*/
    private int balanceFactor;

    /**
     * Default constructor - creates a node with no left or right nodes.
     */
    public Node(){

    }

    public Node(int value){
        this.value = value;
    }


    /**
     * @return the value that the node holds
     */
    public int getValue() {
        return value;
    }

    /**
     * @return the  height of the node - calculated by max{height of leftNode, height of right Node}
     * where the height of a null Node is calculated as
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return the current balance factor of the Node
     */
    public int getBalanceFactor() {
        return balanceFactor;
    }

    /**
     * @return the left node of this node
     */
    public Node getLeftNode() {
        return leftNode;
    }

    /**
     * @return the right node of this node
     */
    public Node getRightNode() {
        return rightNode;
    }

    /**
     * sets the balance factor to a new value
     * @param balanceFactor - the new balance factor
     */
    public void setBalanceFactor(int balanceFactor) {
        this.balanceFactor = balanceFactor;
    }

    /**
     * sets the height to a new value
     * @param height - the new height of the node
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * sets the value of the node
     * @param value - the new value of the node
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * sets the left node of the node
     * @param leftNode - the new left node of the node
     */
    public void setLeftNode(Node leftNode) {
        this.leftNode = leftNode;
    }

    /**
     * sets the right node of the node
     * @param rightNode - the new right node of the node
     */
    public void setRightNode(Node rightNode) {
        this.rightNode = rightNode;
    }
}
