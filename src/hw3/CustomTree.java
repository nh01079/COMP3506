package hw3;

import java.util.Stack;

/**
 * A binary tree, where each node contains at most two children
 * Each root node contains a value and references to its left and right children (if they exist)
 * the customTree class is modified to perform arithmetic computations for single digits(0-9) non negative numbers
 * i.e. (1+2) * 3
 *      *
 *     /  \
 *    +     3
 *   / \
 *  1   2
 */
public class CustomTree extends BinaryTree<String> {
    // TODO: build two different constructors char root and int root
    final static String OPERATORS = "+-*/";
    // variable
    Boolean isOp = false;

    /**
     * Constructs a new binary tree with a single node
     *
     * @param root the element to store at this tree's root
     */
    public CustomTree(String root){
        super(root);
        if(OPERATORS.contains(root)) isOp = true;
    }


    /**
     *  check if node is a valid leaf, i.e the node is has two null children and is not an operator
     * @return true if the condition is satisfied
     */
    @Override
    public boolean isLeaf(){
        return (!isOp && getLeft()==null && getRight()==null);
    }

    /**
     * perform the arithmetic operation based on the provided tree
     * @param root
     * @return result
     */
    public static int compute(CustomTree root){
        if(!checkSyntax(root)) throw new IllegalArgumentException("Invalid syntax");
        return recur(root);
    }

    /**
     * O(n)
     * @param node
     * @return type int (intermediary) result
     */
    protected static int recur(CustomTree node){
        if(node.isLeaf()) return Integer.parseInt(node.getRoot());
        if(node.isOp){
            switch (node.getRoot()){
                case("+"): {
                    return recur((CustomTree) node.getLeft()) + recur((CustomTree) node.getRight());
                }
                case("-"): {
                    return recur((CustomTree) node.getLeft()) - recur((CustomTree) node.getRight());
                }
                case("*"): {
                    return recur((CustomTree) node.getLeft()) * recur((CustomTree) node.getRight());
                }
                case("/"): {
                    return recur((CustomTree) node.getLeft()) / recur((CustomTree) node.getRight());
                }
            }
        }
        return Integer.MAX_VALUE;
    }

    /**
     * static method to check if the syntax is valid
     * O(n)
     * @param node
     * @return true if no violations were found
     */
    public static boolean checkSyntax(CustomTree node){
        if(node.isLeaf())return true;
        if(!node.isOp) return false; // node cannot be a number and not a leaf node
        CustomTree left = (CustomTree) node.getLeft();
        CustomTree right = (CustomTree) node.getRight();
        if(left==null||right==null)return false;
        return (left.isOp ? checkSyntax(left):true) && (right.isOp ? checkSyntax(right):true);
    }

    /**
     * static method to convert string expression to custom tree
     * 1+2*3
     *      *
     *     / \
     *    +   3
     *   / \
     *  1   2
     * @param expression
     */
    public static CustomTree parser(String expression){
        CustomTree current = null;
        if(expression.length()==0)return null;
        int j = 0;
        for(int i=1; i<expression.length(); i++){
            if(OPERATORS.indexOf(expression.charAt(i))==-1)continue; // pass
            CustomTree node = current;
            current = new CustomTree(expression.substring(i,i+1));
            if(j==0){
                current.setLeft(new CustomTree(expression.substring(j,i)));
            }else{
                node.setRight(new CustomTree(expression.substring(j,i)));
                current.setLeft(node);
            }
            j = i+1;
        }
        if(current!=null)current.setRight(new CustomTree(expression.substring(j)));
        else current = new CustomTree(expression.substring(j));
        return current;
    }

    public static CustomTree parser1(String expression){
        CustomTree current = null;
        for(int i = 1; i<expression.length();i+=2){
            CustomTree temp = current;
            current = new CustomTree(expression.substring(i,i+1));
            if(temp==null) current.setLeft(new CustomTree(expression.substring(i-1,i)));
            else current.setLeft(temp);
            current.setRight(new CustomTree(expression.substring(i+1,i+2)));
        }
        /*
        String OPEN = "{[(";
        String CLOSE = "}])";
        Stack<String> stack = new Stack<>();
        int j = 0;
        for(int i=0; i<expression.length();i++){
            if(OPERATORS.indexOf(expression.charAt(i))!=-1){
                CustomTree node = new CustomTree(expression.substring(j,i));
                CustomTree node1 = new CustomTree(expression.substring(i,i+1));
                node1.setLeft(node);

            }
        }

         */
        return current;
    }

}
