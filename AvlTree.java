public class AvlTree implements Iterable<Integer>{

    private Node rootNode;

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
}
