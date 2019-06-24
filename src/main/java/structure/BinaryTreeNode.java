package structure;

import lombok.Data;

import java.util.List;

/**
 * 节点实例
 * Created by xujia on 2019/5/27
 */
@Data
public class BinaryTreeNode {

    /**
     * 节点数据
     */
    private String value;
    /**
     * 左子节点
     */
    private BinaryTreeNode leftChild;
    /**
     * 右子节点
     */
    private BinaryTreeNode rightChild;

    public BinaryTreeNode(String value, BinaryTreeNode leftChild, BinaryTreeNode rightChild) {
        this.value = value;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    // 证明泛型在编译时就已将类型进行擦除，其本质是编译器帮我们进行了强制类型转换
//    public void t(List<Integer> t) {
//        System.out.println("1");
//    }
//
//    public void t(List<String> t) {
//        System.out.println("1");
//    }
}
