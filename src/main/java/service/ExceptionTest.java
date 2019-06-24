package service;

import entity.Dog;
import entity.User;
import org.junit.Test;

import java.io.*;

/**
 * Created by xujia on 2019/6/3
 */
public class ExceptionTest {

    class Annoyance extends Exception {}
    class Sneeze extends Annoyance {}

    @Test
    public void test01() {
        try {
            try {
                throw new Sneeze();
            }
            catch ( Annoyance a ) {
                System.out.println("Caught Annoyance");
                throw a;
            }
        }
        catch ( Sneeze s ) {
            System.out.println("Caught Sneeze");
            return ;
        }
        finally {
            System.out.println("Hello World!");
        }
    }

    @Test
    public void test02() {
        User user = new User("123", "234");
        Dog dog = new Dog("泰迪");
        user.setDog(dog);
//        User user1 = user;
//        System.out.println(user == user1);
//        System.out.println(user.equals(user1));

//        User user2 = (User) user.clone();
//        System.out.println(user == user2);
//        System.out.println(user.equals(user2));
        // 下面演示深拷贝，采用流的方式
        // 将对象写入流中
        User user2 = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(user);
            // 从流中读出对象
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            user2 = (User) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(user.getId() + user.getName() + user.getDog().getName());
        System.out.println(user2.getId() + user2.getName() + user2.getDog().getName());
        dog.setName("拉布拉多");
        System.out.println(user.getId() + user.getName() + user.getDog().getName());
        System.out.println(user2.getId() + user2.getName() + user2.getDog().getName());



    }
}
