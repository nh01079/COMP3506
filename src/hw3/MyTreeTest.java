package hw3;

import org.junit.Test;

import static org.junit.Assert.*;

public class MyTreeTest {
    @Test(timeout=1000)
    public void testIsBST() {
        BinaryTree<Integer> tree = new BinaryTree<>(2);
        BinaryTree<Integer> left = new BinaryTree<>(1);
        BinaryTree<Integer> right = new BinaryTree<>(3);
        BinaryTree<Integer> leftLeft = new BinaryTree<>(0);

        tree.setLeft(left);
        tree.setRight(right);
        left.setLeft(leftLeft);
        leftLeft.setLeft(new BinaryTree<>(null));
        leftLeft.setRight(new BinaryTree<>(null));
        left.setRight(new BinaryTree<>(null));
        right.setLeft(new BinaryTree<>(null));
        right.setRight(new BinaryTree<>(null));

        assertTrue(BinaryTree.isBST(tree));
    }

    @Test
    public void testvalidSimpleTree(){
        CustomTree tree = new CustomTree("+");
        CustomTree left = new CustomTree("1");
        CustomTree right = new CustomTree("3");
        tree.setLeft(left);
        tree.setRight(right);
        assertTrue(CustomTree.checkSyntax(tree));
    }

    @Test
    public void testInvalidSimpleTree(){
        CustomTree tree = new CustomTree("0");
        CustomTree left = new CustomTree("1");
        CustomTree right = new CustomTree("3");
        tree.setLeft(left);
        tree.setRight(right);
        assertFalse(CustomTree.checkSyntax(tree));
    }

    @Test
    public void testSimpleCompute(){
        CustomTree tree = new CustomTree("+");
        CustomTree left = new CustomTree("1");
        CustomTree right = new CustomTree("3");
        tree.setLeft(left);
        tree.setRight(right);
        assertEquals(4,CustomTree.compute(tree));
    }

    @Test
    public void testValidComplexTree(){
        CustomTree tree = new CustomTree("*");
        CustomTree left = new CustomTree("-");
        CustomTree leftLeft = new CustomTree("4");
        CustomTree leftRight = new CustomTree("-5");
        CustomTree right = new CustomTree("3");
        tree.setLeft(left);
        left.setLeft(leftLeft);
        left.setRight(leftRight);
        tree.setRight(right);
        assertTrue(CustomTree.checkSyntax(tree));
        assertEquals(27, CustomTree.compute(tree));
    }
    @Test
    public void testInvalidComplexTree(){
        CustomTree tree = new CustomTree("+");
        CustomTree left = new CustomTree("-");
        //CustomTree leftLeft = new CustomTree(null);
        CustomTree leftRight = new CustomTree("5");
        CustomTree right = new CustomTree("3");
        tree.setLeft(left);
        //left.setLeft(leftLeft);
        left.setRight(leftRight);
        tree.setRight(right);
        assertFalse(CustomTree.checkSyntax(tree));
        try{
            CustomTree.compute(tree);
            fail();
        }catch (IllegalArgumentException e){
            ;
        }
    }
    @Test
    public void singleDigitParse() {
        String expression = "1+2*3/9";
        CustomTree node = CustomTree.parser(expression);
        assertEquals(1, CustomTree.compute(node));
    }
    @Test
    public void simpleParse() {
        String expression = "11+2*3-9/10";
        CustomTree node = CustomTree.parser(expression);
        assertEquals(3, CustomTree.compute(node));
    }
    @Test
    public void edgeParse(){
        String expression = "";
        CustomTree node = CustomTree.parser(expression);
        assertNull(node);
        expression = "10";
        node = CustomTree.parser(expression);
        assertEquals(10,CustomTree.compute(node));
        expression = "10+";
        node = CustomTree.parser(expression);
        try{
           CustomTree.compute(node);
           fail();
        }catch(IllegalArgumentException e){
            ;
        }
    }
}
