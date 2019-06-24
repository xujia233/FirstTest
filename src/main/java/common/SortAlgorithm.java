package common;

import org.junit.Test;

import java.util.Arrays;

/**
 * 本例演示八大排序算法
 * 各示例结果均为升序
 * Created by xujia on 2019/4/25
 */
public class SortAlgorithm {

    /**
     * 演示数组
     */
    private int[] array = new int[]{10, 1, 4, 22, 56, 5, 15, 4};

    /**
     * 冒泡排序，其主要思想就是通过循环比较每一对相邻的元素，若不符合排序条件则交换
     */
    @Test
    public void bubbleSort() {
        int temp,i,j;
        int length = array.length;
        // 外层控制循环次数
        for (i = 0 ; i < length - 1; i++) {
            // length-1-i 能够排除较大值，减少比较次数
            for (j = 0;j < length - 1 - i;j++) {
                // 如果后一位比前一位小，则交换元素位置，通过重重循环，实现较大数往后沉，较小数往前冒
                if (array[j+1] < array[j]) {
                    temp = array[j+1];
                    array[j+1] = array[j];
                    array[j] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(array));
    }

    /**
     * 选择排序
     * 基本思路：在未排序的数组中选择最小的一个数与第一个数进行交换，然后在剩下的元素中继续找出最小的元素放到第二个位置，依次循环得到排序结果
     */
    @Test
    public void selectSort() {
        int temp,i,j;
        int length = array.length;
        for (i = 0; i < length;i++) {
            // 临时下标，记录最小元素的小标
            int k = i;
            for (j = i;j< length;j++) {
                // 循环找出最小的元素，并将数组下标赋值给临时下标
                if (array[j] < array[k])
                    k = j;
            }
            if (k != i) {
                // 当下标不一致时交换
                temp = array[k];
                array[k] = array[i];
                array[i] = temp;
            }
        }
        System.out.println(Arrays.toString(array));
    }

    /**
     * 插入排序，基本思想就是从第二个数开始，分别将待插入元素与已排好序数组的最后一个元素进行比较，若不符合规则，则继续与前一个元素比较，直到小于或等于新元素
     */
    @Test
    public void insertSort() {
        int i,j,length = array.length;
        // 从第二个元素开始循环
        for (i = 1; i < length; i++) {
            // 存放新元素
            int temp = array[i];
            // 如果新元素小于前一元素（已排好序），则做交换操作，即先将前一元素赋值到当前下标，继续重复比较新元素与前前元素，直到找到已排序元素的末尾小于或等于新元素
            for (j = i; j > 0 && temp < array[j-1]; j--) {
                array[j] = array[j-1];
            }
            // 当不相等时说明新元素不是排在末尾，需与特定下标处的元素进行交换
            if (temp != array[j])
                array[j] = temp;
        }
        System.out.println(Arrays.toString(array));
    }

    /**
     * 希尔排序，直接插入排序的优化，其基本思想与插入排序一毛一样，唯一不同的是增加了增量的概念，即从原始数组中以该增量挑选出新的数组进行排序，重复该步骤
     * 保证，循环一次过后改变该增量再次循环，直到增量小于1，而直接插入排序的增量为1
     */
    @Test
    public void shellSort() {
        int i,j,length = array.length,increment = length/2;
        // 一直循环直到增量小于1
        while (increment > 0) {
            // 原始数组需全部循环一遍
            for (i = increment; i < length; i++) {
                // 接下来与插入排序思想类似，这里就不写注释了，只是多了个增量概念而已
                int temp = array[i];
                for (j = i;j >= increment && temp < array[j-increment];j-=increment)
                    array[j] = array[j-increment];
                if (temp != array[j])
                    array[j] = temp;
            }
            increment = increment/2;
        }
        System.out.println(Arrays.toString(array));
    }

    /**
     * 堆排序，直接选择排序的优化，其基本思想同样是通过循环从待排序数组中挑选出最大或最小的元素，只是采用了建堆的方式，提高了性能
     * 主要包括三步 1、建堆 2、交换堆顶元素与最后一个元素 3、剔除最后一个元素然后重复1 2步
     */
    @Test
    public void heapSort() {
        int i, length = array.length;
        // 循环建堆与交换
        for (i = 0; i < length; i++) {
            // 建造堆结构
            buildHeap(length-1-i);
            // 交换，把第一个最大或最小的元素与最后一个元素交换
            swap(0, length-1-i);
        }
        System.out.println(Arrays.toString(array));
    }

    /**
     * 堆排序中的建堆方法，算法中很多=符号其实都很重要，要理解他们的意思，请勿漏写
     * @param lastIndex 最后一个元素
     */
    private void buildHeap(int lastIndex) {
        // 从最后一个元素的父节点开始往前循环比较
        for (int i = (lastIndex-1)/2; i >= 0; i--) {
            // 记录当前父节点下标用于下面比较
            int temp = i;
            // 说明存在左子节点
            if ((2*i+1) <= lastIndex) {
                // 当前左子节点下标
                int biggerIndex = 2*i + 1;
                // 如果左子节点小于最后一个元素，说明该父节点存在右子节点
                if (biggerIndex < lastIndex) {
                    if (array[biggerIndex] < array[biggerIndex+1])
                        // 总是记录较大元素的下标
                        biggerIndex++;
                }
                if (array[temp] < array[biggerIndex])
                    // 确保大元素始终处于父节点中，为之后的循环比较作铺垫
                    swap(temp, biggerIndex);
            }
        }
    }

    /**
     * 堆排序中用于交换元素所用
     * @param i
     * @param j
     */
    private void swap(int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * 快速排序
     * @param i 第一个元素的下标
     * @param j 最后一个元素的下标
     */
    private void quickSort(int i, int j) {
        int t,low = i,high = j;
        if (i >= j)
            return;
        // 第一个元素作为基准数，临时记录下，以便之后交换所用
        int temp = array[i];

        // 只要low与high不重合则一直循环下去，直到找到需与基准点交换的元素处
        while (low < high) {

            // high下标先开始寻找较小数，遇到满足要求的元素并且两个游标未重合时则一直往前遍历
            while (low < high && array[high] >= temp)
                high--;
            // 然后low下标开始往后寻找较大数，遇到满足要求的元素并且两个游标未重合时则一直往后遍历
            while (low < high && array[low] <= temp)
                low++;

            // 确保游标不重合才交换
            if (low < high) {
                t = array[high];
                array[high] = array[low];
                array[low] = t;
            }
        }
        // 执行到这里说明已经找到两个游标重合点，此时low=high，将该处元素与基准数交换
        array[i] = array[high];
        array[high] = temp;
        // 然后继续递归分治，首先是基准数左边数组分治
        quickSort(i, low - 1);
        // 基准数右边数组分治
        quickSort(low + 1, j);
    }

    @Test
    public void testQuickSort() {
        quickSort(0, array.length - 1);
        System.out.println(Arrays.toString(array));
    }

    /**
     * 归并排序，与快排一样采用分治法思想
     * @param low
     * @param high
     */
    private void mergeSort(int low, int high) {
        // 只要不是单个元素则一直递归排序、合并
        if (low < high) {
            // 双路归并，取中间下标
            int mid = (low + high) / 2;
            // 前半段子序列递归
            mergeSort(low, mid);
            // 后半段子序列递归
            mergeSort(mid + 1, high);
            // 合并子序列，做真正的排序操作
            merge(low, mid, high);
        }
    }

    /**
     * 归并排序中的合并方法，用于将两组子序列合并
     * @param left 第一组子序列起始点下标
     * @param mid 第一组子序列末尾下标 mid+1为第二组子序列起始点下标
     * @param right 第二组子序列末尾下标
     */
    private void merge(int left, int mid, int right) {
        // p1为第一组子序列起始下标 p2为第二组子序列起始下标 k是临时数组存放指针
        int p1 = left, p2 = mid + 1, k = left;
        // 临时数组，用于存储合并后的数组
        int[] tempArray = new int[array.length];
        // 只有两个子序列均未超出各自长度 则循环判断
        while (p1 <= mid && p2 <= right) {
            if (array[p1] <= array[p2]) {
                tempArray[k++] = array[p1++];
            } else {
                tempArray[k++] = array[p2++];
            }
        }
        // 若第一组子序列中还有剩余元素，则说明是较大数剩余，一并加到新数组后面
        while (p1 <= mid) {
            tempArray[k++] = array[p1++];
        }
        // 同理，若第二组子序列中还有剩余元素，则说明是较大数剩余，一并加到新数组后面
        while (p2 <= right) {
            tempArray[k++] = array[p2++];
        }
        // 将新数组中的元素赋值到原数组，完成合并操作
        for (int i = left; i <= right; i++) {
            array[i] = tempArray[i];
        }
    }

    @Test
    public void testMergeSort() {
        mergeSort(0, array.length - 1);
        System.out.println(Arrays.toString(array));
    }

}
