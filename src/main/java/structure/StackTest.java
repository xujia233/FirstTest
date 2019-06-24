package structure;

import org.junit.Test;

import java.util.Stack;

/**
 * Created by xujia on 2019/5/27
 */
public class StackTest {

    @Test
    public void test1() {
        Stack stack = new Stack();
        for (int i = 0;i<6;i++) {
            stack.push(i);
        }
        System.out.print(stack.lastElement());

        while (!stack.isEmpty())
            System.out.println(stack.pop());

    }

    /**
     * 初始化一颗二叉树
     * @return
     */
    private BinaryTreeNode initBinaryTreeNode() {
        BinaryTreeNode Fnode = new BinaryTreeNode("F", null, null);
        BinaryTreeNode Cnode = new BinaryTreeNode("C", Fnode, null);
        BinaryTreeNode Enode = new BinaryTreeNode("E", null, null);
        BinaryTreeNode Dnode = new BinaryTreeNode("D", null, null);
        BinaryTreeNode Bnode = new BinaryTreeNode("B", Dnode, Enode);
        BinaryTreeNode root = new BinaryTreeNode("A", Bnode, Cnode);
        return root;
    }

    @Test
    public void test() {
        BinaryTreeNode root = initBinaryTreeNode();
        System.out.println("非递归后序遍历（采用两个栈的方式）:");
        postOrder(root);
        System.out.println();

        System.out.println("非递归后序遍历（采用一个栈的方式）：");
        postOrderWithOneStack(root);
        System.out.println();

        System.out.println("递归后序遍历:");
        postOrderWithRecursion(root);
        System.out.println();

        System.out.println("非递归先序遍历:");
        preOrder(root);
        System.out.println();

        System.out.println("递归先序遍历：");
        priOrderWithRecursion(root);
        System.out.println();

        System.out.println("非递归中序遍历:");
        inOrder(root);
        System.out.println();

        System.out.println("递归中序遍历：");
        inOrderWithRecursion(root);
    }

    /**
     * 非递归后序遍历二叉树，逆向思维
     * 如果使用两个栈，可以在最后获取到一个完整数据的栈，你可以对该栈做操作，当然也可以使用一个栈来进行遍历，此时则做不到如上业务需求
     * 本方法采取的是利用两个栈变量来遍历，逻辑更清晰
     * 利用栈的先进后出特性，整体思想是先将所有数据按后序遍历的反序即根右左的顺序入栈，最后对栈执行出栈操作，具体操作为：
     * 1、先对根节点执行入栈操作，此时存在栈1，栈2
     * 2、然后不断搜索右子节点，并执行入栈操作，直到右子节点为空
     * 3、此时对栈1执行出栈操作开始向上遍历，并取出左子节点继续循环入栈，直到栈1为空并且当前节点为空
     * @param root 根节点
     */
    private void postOrder(BinaryTreeNode root) {
        if (null != root) {
            Stack<BinaryTreeNode> stackTemp = new Stack<>();
            Stack<BinaryTreeNode> stack = new Stack<>();
            while (null != root || !stackTemp.isEmpty()) {
                while (null != root) {
                    // 不断搜索右子节点并分别往两个栈中推元素
                    stackTemp.push(root);
                    stack.push(root);
                    root = root.getRightChild();
                }
                if (!stackTemp.isEmpty()) {
                    // 当右子节点搜索到尽头时，从临时栈中取出顶端节点开始向上反遍历
                    root = stackTemp.pop();
                    root = root.getLeftChild();
                }
            }
            while (!stack.isEmpty())
                System.out.print(stack.pop().getValue() + "\t");
        }
    }

    /**
     * 非递归后序遍历，采用一个栈的方式，正向思维
     * @param root
     */
    public void postOrderWithOneStack(BinaryTreeNode root){
        Stack<BinaryTreeNode> stack = new Stack<>();
        while (true){
            if (root != null){
                stack.push(root);
                root = root.getLeftChild();
            } else {
                if (stack.isEmpty())
                    return;
                if (null == stack.peek().getRightChild()) {
                    root = stack.pop();
                    System.out.print(root.getValue() + "\t");
                    while (root == stack.peek().getRightChild()) {
                        root = stack.pop();
                        System.out.print(root.getValue() + "\t");
                        if (stack.isEmpty()) {
                            break;
                        }
                    }
                }
                if (!stack.isEmpty())
                    root = stack.peek().getRightChild();
                else
                    root = null;
            }
        }
    }

    /**
     * 递归后序遍历
     * @param root
     */
    private void postOrderWithRecursion(BinaryTreeNode root) {
        if (null != root) {
            postOrderWithRecursion(root.getLeftChild());
            postOrderWithRecursion(root.getRightChild());
            System.out.print(root.getValue()+ "\t");
        }
    }

    /**
     * 非递归先序遍历，虽然是采用栈的方式，但是并未完全应用栈的思想，采取循环过程中输出的方式来遍历
     * @param root
     */
    private void preOrder(BinaryTreeNode root) {
        Stack<BinaryTreeNode> stack = new Stack<>();
        while (null != root || !stack.isEmpty()) {
            while (null != root) {
                System.out.print(root.getValue() + "\t");
                stack.push(root);
                root = root.getLeftChild();
            }
            if (!stack.isEmpty()) {
                root = stack.pop();
                root = root.getRightChild();
            }
        }
    }

    /**
     * 递归先序遍历
     * @param root
     */
    private void priOrderWithRecursion(BinaryTreeNode root) {
        if (null != root) {
            System.out.print(root.getValue() + "\t");
            priOrderWithRecursion(root.getLeftChild());
            priOrderWithRecursion(root.getRightChild());
        }
    }

    /**
     * 非递归中序遍历
     * @param root
     */
    private void inOrder(BinaryTreeNode root) {
        Stack<BinaryTreeNode> stack = new Stack<>();
        while (null != root || !stack.isEmpty()) {
            while (null != root) {
                stack.push(root);
                root = root.getLeftChild();
            }
            if (!stack.isEmpty()) {
                root = stack.pop();
                System.out.print(root.getValue() + "\t");
                root = root.getRightChild();
            }
        }
    }

    /**
     * 递归中序遍历
     * @param root
     */
    private void inOrderWithRecursion(BinaryTreeNode root) {
        if (null != root) {
            inOrderWithRecursion(root.getLeftChild());
            System.out.print(root.getValue() + "\t");
            inOrderWithRecursion(root.getRightChild());
        }
    }

}
