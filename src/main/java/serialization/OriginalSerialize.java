package serialization;

import java.io.*;
import java.util.HashMap;

/**
 * Created by xujia on 2020/1/2
 */
public class OriginalSerialize {

    public static void main(String[] args) throws Exception {

        long start = System.currentTimeMillis();
        setSerializzeObject();
        System.out.println("java原生的序列化时间 ： "+ (System.currentTimeMillis()-start)+"ms");

        start = System.currentTimeMillis();
        getSerializeObject();
        System.out.println("java原生的发序列化时间 ： "+(System.currentTimeMillis()-start)+"ms");
    }

    public static void setSerializzeObject() throws Exception {

        FileOutputStream fileOutputStream = new FileOutputStream("OriginalSerialize.txt");

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        for (int i = 0; i < 1000; i++) {
            HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();

            stringIntegerHashMap.put("Original01", i);
            stringIntegerHashMap.put("Original02", i);

            objectOutputStream.writeObject(new Simple("dada", i+10, stringIntegerHashMap));

        }
        objectOutputStream.flush();
        objectOutputStream.close();
    }
    public static void getSerializeObject() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("OriginalSerialize.txt");

        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Simple simple = null;
        try {
            while ((simple = (Simple)objectInputStream.readObject()) != null){
            }
        }catch (EOFException e){

        }

        objectInputStream.close();
    }

}
