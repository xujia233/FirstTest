package csdn;

import lombok.Data;
import org.junit.Test;
import structure.BinaryTreeNode;

import java.util.*;

/**
 * 简单算法题练习
 * Created by xujia on 2019/6/6
 */
public class SubjectTest {

    /**
     * 题目：已知sqrt(2)的结果为1.414，要求不使用数学库，求出sqrt(2)精确到小数点后十位
     * 解：利用二分法思想从1.4~1.5区间不断寻找，直到前后差值小于等于0.00000000001时退出循环
     */
    @Test
    public void test() {
        // 数学库求解结果
        System.out.println(Math.sqrt(2));
        double low = 1.4, high = 1.5, mid = (low + high) / 2;
        while ((high - low) > 0.00000000001) {
            // 当前后差值小于等于0.00000000001时退出循环
            if (mid * mid > 2) {
                high = mid;
            } else {
                low = mid;
            }
            mid = (low + high) / 2;
        }
        System.out.println(mid);
    }

    /**
     * 题目：给定一个二叉搜索树，找到树中第K小的节点
     * 解：利用遍历及二叉搜索树的特性：左子树的值 < 根节点的值 < 右子树的值，重点在遍历节点时返回该节点的左子树数量，若等于K-1，说明根节点
     * 即所要搜寻的值，若小于K-1，说明搜寻值必在右子树，从右子树寻找即可。
     */
    @Test
    public void test01() {
        BinaryTreeNode binaryTreeNode = initBinaryTreeNode();
        System.out.println(treeSearch(binaryTreeNode, 4).getVal());
    }

    /**
     * 搜索的具体实现方法
     * @param root 传入节点
     * @param k 指定值
     * @return
     */
    public ResultType treeSearch(BinaryTreeNode root, int k) {
        if (null == root)
            return new ResultType(false, 0);
        // 遍历左子树
        ResultType left = treeSearch(root.getLeftChild(), k);
        // 如果左子树找到，直接返回
        if (left.isFound())
            return new ResultType(true, left.getVal());
        // 如果左子树的节点数等于k-1，说明根节点就是要找的值，直接返回
        if (left.getVal() == k - 1)
            return new ResultType(true, Integer.valueOf(root.getValue()));
        // 否则搜索右子树
        ResultType right = treeSearch(root.getRightChild(), k - 1 - left.getVal());
        if (right.isFound())
            return new ResultType(true, right.getVal());
        // 没找到时返回总节点数
        return new ResultType(false, left.getVal() + right.getVal() + 1);
    }

    /**
     * 初始化一颗二叉搜索树
     * @return
     */
    private BinaryTreeNode initBinaryTreeNode() {
        BinaryTreeNode Fnode = new BinaryTreeNode("1", null, null);
        BinaryTreeNode Cnode = new BinaryTreeNode("2", Fnode, null);
        BinaryTreeNode Enode = new BinaryTreeNode("4", null, null);
        BinaryTreeNode Dnode = new BinaryTreeNode("3", Cnode, Enode);
        BinaryTreeNode Bnode = new BinaryTreeNode("6", null, null);
        BinaryTreeNode root = new BinaryTreeNode("5", Dnode, Bnode);
        return root;
    }

    /**
     * 遍历二叉搜索树时的返回结果，主要比较count参数与给定K值的比较
     */
    @Data
    class ResultType {
        /**
         * 标识是否找到指定值
         */
        private boolean found;
        /**
         * 当found为false时为数量，为true时代表节点的值
         */
        private int val;

        public ResultType(boolean found, int val) {
            this.found = found;
            this.val = val;
        }
    }

    /**
     * 题目：单链表反转，给定一个单链表 1 2 3 4 反转成 4 3 2 1
     * 思路：递归的时候保存前一节点，然后实现反转
     */
    @Test
    public void test03() {
        Node head = initNode();
        System.out.println("旧链表输出：");
        printNode(head);

        Node newHead = reverseNode(head);
        System.out.println("新链表输出：");
        printNode(newHead);
    }

    /**
     * 输出单链表
     * @param node
     */
    private void printNode(Node node) {
        while (null != node) {
            System.out.print(node.value + "\t");
            node = node.next;
        }
        System.out.println();
    }

    /**
     * 反转的具体实现方法
     * @param node
     * @return
     */
    private Node reverseNode(Node node) {
        if (null == node || null == node.next)
            return node;
        // 保存下一节点引用 2 3 4
        Node temp = node.next;
        // 原先的尾节点作为新链表的头结点返回 4，主要还是利用引用地址的特性
        Node head = reverseNode(node.next);
        // 下一节点的引用指向当前节点，完成反转
        temp.next = node;
        node.next = null;
        return head;
    }

    /**
     * 初始化单链表
     */
    private Node initNode() {
        Node node1 = new Node(4, null);
        Node node2 = new Node(3, node1);
        Node node3 = new Node(2, node2);
        Node node4 = new Node(1, node3);
        return node4;
    }

    /**
     * 节点实例
     */
    class Node {
        private int value;
        private Node next;
        public Node(int value, Node node) {
            this.value = value;
            next = node;
        }
        public Node() {}
    }

    @Test
    public void test04() {
        int[] arr = new int[]{1, 3, 3, 4, 5};
        int[] newArr = compluteTwoSum(arr, 6);
        System.out.println(Arrays.toString(newArr));
    }

    /**
     * LeetCode 给定一个int数组，找出数组中两数相加等于目标值的下标
     * 例如：[1, 3, 5, 7] target:4  则返回[0, 1]
     * 整体思路：采用空间换时间，使用map的哈希索引优化查询时间
     * @param arr
     * @param target
     * @return
     */
    private int[] compluteTwoSum(int[] arr, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            if (map.containsKey(target - arr[i])) {
                // 如果存在则直接返回
                return new int[]{map.get(target - arr[i]), i};
            }
            map.put(arr[i], i);
        }
        return null;
    }

    /**
     * LeetCode 将两个有序链表合并为一个新的有序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
     * 例如：输入：1->2->4, 1->3->4
     *      输出：1->1->2->3->4->4
     */
    @Test
    public void test05() {
        Node node1 = initNode(new int[]{4,2,1});
        printNode(node1);
        Node node2 = initNode(new int[]{4,3,1});
        printNode(node2);
        Node newNode = mergeNode(node1, node2);
        printNode(newNode);

        System.out.println();
        Node newNode2 = mergeNode2(node1, node2);
        printNode(newNode2);
//
//        Node newNode3 = mergeNode3(node1, node2);
//        printNode(newNode3);
    }

    /**
     * 合并两个有序链表的具体方法 1、呆萌版
     * @param node1
     * @param node2
     * @return
     */
    private Node mergeNode(Node node1, Node node2) {
        List<Integer> list = new ArrayList<>();
        while (null != node1 || null != node2) {
            if (null != node1) {
                list.add(node1.value);
                node1 = node1.next;
            }
            if (null != node2) {
                list.add(node2.value);
                node2 = node2.next;
            }
        }
        // 降序
        Collections.sort(list, Collections.reverseOrder());
        Node node = null;
        for (int i : list) {
            node = init(i, node);
        }
        return node;
    }

    /**
     * 循环法：在第一次遍历时就设值
     * @param node1
     * @param node2
     * @return
     */
    private Node mergeNode2(Node node1, Node node2) {
        // 维持新链表的头部引用，以便最后返回
        Node node = new Node();
        Node temp = node;
        // 循环终止时必有一方已为null，由于都是有序链表，直接把非空的一方全部设到临时链表后面即可
        while (null != node1 && null != node2) {
            if (node1.value < node2.value) {
                temp.next = node1;
                node1 = node1.next;
            } else {
                temp.next = node2;
                node2 = node2.next;
            }
            temp = temp.next;
        }
        temp.next = node1 != null ? node1 : node2;
        return node.next;
    }

    /**
     * 递归法，递归判断两个链表当前引用的值
     * @param node1
     * @param node2
     * @return
     */
    private Node mergeNode3(Node node1, Node node2) {
        // 如果node1链表为空，直接返回非空链表即可
        if (null == node1)
            return node2;
        if (null == node2)
            return node1;
        if (node1.value < node2.value) {
            node1.next = mergeNode3(node1.next, node2);
            return node1;
        } else {
            node2.next = mergeNode3(node1, node2.next);
            return node2;
        }
    }

    private Node init(int i, Node node) {
        return new Node(i, node);
    }

    /**
     * 供上面调用
     * @return
     */
    private Node initNode(int ...arr) {
        Node node1 = new Node(arr[0], null);
        Node node2 = new Node(arr[1], node1);
        Node node3 = new Node(arr[2], node2);
        Node node4 = new Node(arr[3], node3);
        Node node5 = new Node(arr[4], node4);
        return node5;
    }

    /**
     * 寻找链表倒数第k个节点，假设k不超过链表长度
     * 例如 1->2->3，k为2，则输出2
     * @param node
     * @param k
     * @return
     */
    public int searchNodeK(Node node, int k) {
        // 分析：假设k为2，第一个指针先走1步，走到2，第二个指针开始走，当第一个指针走到3时，第二个指针正好走到2
        Node p1 = node, p2 = node;
        int i = 0;
        while (null != p1) {
            p1 = p1.next;
            i++;
            if (i > k-1 && null != p1) {
                p2 = p2.next;
            }
        }
        // 如果k大于链表长度，则抛出空指针异常
        if (i < k)
            throw new NullPointerException("Beyond the length of the linked list");
        return p2.value;
    }

    @Test
    public void test06() {
        Node node1 = initNode(new int[]{7,7,7,7,7});
        printNode(node1);
        //printNode(deleteDuplicate(node1));
        printNode(deleteNodeVal(node1,7));
    }

    private Node deleteDuplicate(Node node) {
        Node temp = node;
        // 因为是有序链表，因此可直接判断当前节点与下一节点的值
        while (null != temp && null != temp.next) {
            if (temp.value == temp.next.value) {
                temp.next = temp.next.next;
            } else {
                // 该行代码必须在当前节点与下一节点不相等时才调用，如若提取出来则会导致形如1->1->1的链表无法删除
                temp = temp.next;
            }
        }
        return node;
    }

    /**
     * 删除链表中等于给定值的节点 如1->3->7->4->5 给定3则原链表变为1->7->4->5
     * 递归思想的两个核心条件：1、存在简单情况能终止递归即有出口条件    2、能不断靠近该简单情况，缩小问题规模
     * 因此个人认为设计递归时最难的一点就是抽象出这些问题的相同逻辑片段，当然边界条件也都需要好好考虑
     * 针对该题， 每一个节点其实只需要判断该节点的值是否与给定值相等，是则返回当前节点，否则返回下一节点。
     * @param head
     * @return
     */
    private Node deleteNodeVal(Node head,int val) {
        if (head==null)
            return null;
        head.next = deleteNodeVal(head.next,val);
        return head.value == val ? head.next : head;
    }

    private int fbnq(int n){
        if( n== 2 || n==1)
            return 1;
        else
            return fbnq(n-1) + fbnq(n-2);
    }
}
