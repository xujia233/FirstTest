package impl;

import csdn.MyLinkedList;
import org.junit.Test;
import proxy.People;

import java.util.LinkedList;
import java.util.TreeSet;

/**
 * Created by xujia on 2019/4/15
 */
public class Boy implements People {

    @Override
    public void say() {
        System.out.println("i'm a boy");
    }

    @Test
    public void test() {
        LinkedList<Integer> linkedList = new LinkedList<>();
        linkedList.add(1);
        linkedList.add(2);
        linkedList.add(3);
        linkedList.add(null);
        linkedList.remove((Integer) 1);

        System.out.println(linkedList.get(0));
    }

    @Test
    public void test01() {
        MyLinkedList<Integer> linkedList = new MyLinkedList<>();
        linkedList.add(1);
//        linkedList.add(2);
//        linkedList.add(3);
//        linkedList.add(null);
        System.out.println(linkedList.get(0));
//        System.out.println(linkedList.get(1));
//        System.out.println(linkedList.get(2));
//        System.out.println(linkedList.get(3));
        linkedList.remove(0);
        System.out.println(linkedList.get(0));
    }

    @Test
    public void test02() {
        TreeSet<Integer> set = new TreeSet<Integer>();
        TreeSet<Integer> subSet = new TreeSet<Integer>();
        for(int i=606;i<613;i++){
            if(i%2==0){
                set.add(i);
            }
        }
        subSet = (TreeSet)set.subSet(608,true,611,true);
        set.add(629);
        System.out.println(set+" "+subSet);
    }
}
