package serialization;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;

/**
 * Created by xujia on 2020/1/2
 */
public class KyroSerialize {
    public static void main(String[] args) throws FileNotFoundException {
        long statr = System.currentTimeMillis();
        setSerializeObject();
        System.out.println("kryo序列化的时间 ： "+(System.currentTimeMillis()-statr)+"ms");
        statr = System.currentTimeMillis();
        getSerializeObject();
        System.out.println("kryo反序列化的时间 ： "+(System.currentTimeMillis()-statr)+"ms");

    }
    public static void setSerializeObject() throws FileNotFoundException {
        Kryo kryo = new Kryo();
        Output output = new Output(new FileOutputStream("KyroSerialize.txt"));

        for (int i = 0; i < 1000; i++) {
            HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();

            stringIntegerHashMap.put("Kyro01", i+1);
            stringIntegerHashMap.put("Kyro02", i+1);

            kryo.writeObject(output, new Simple("ddd", 10+i, stringIntegerHashMap));
        }
        output.flush();
        output.close();
    }

    public static void getSerializeObject() throws FileNotFoundException {
        Kryo kryo = new Kryo();

        Input input = new Input(new FileInputStream("KyroSerialize.txt"));

        Simple simple = null;
        try {
            while ((simple=kryo.readObject(input,Simple.class)) != null){
            }
        }catch (KryoException e){

        }

        input.close();
    }
}
