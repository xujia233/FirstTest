package serialization;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoPool;
import com.google.common.collect.Lists;
import entity.User;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 序列化测试
 * Created by xujia on 2020/1/2
 */
public class SerializationTest {

    private static final ThreadLocal<Kryo> kryoThreadLocal = new ThreadLocal<Kryo>() {
        protected Kryo initialValue() {
            Kryo kryo = new Kryo();
            // 这里可以增加一系列配置信息
            return kryo;
        }
    };

    @Test
    public void test0 () throws Exception {
        // 需实现Serializable接口
        User user = new User("123", "jdks");
        // 将对象写入到文件中
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("user.txt"));
        objectOutputStream.writeObject(user);
        objectOutputStream.close();
        // 将对象从文件中读出来
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("user.txt"));
        User newUser = (User) inputStream.readObject();
        inputStream.close();
        assert "jdks".equals(newUser.getName());
    }

    /**
     * Kryo的快速入门，与jdk序列化差距并不是很大
     * @throws Exception
     */
    @Test
    public void test1() throws Exception {
        Kryo kryo = new Kryo();
        User user = new User("123", "kryo");
        Output output = new Output(new FileOutputStream("userKryo.txt"));
        kryo.writeObject(output, user);
        output.close();

        Input input = new Input(new FileInputStream("userKryo.txt"));
        User newUser = kryo.readObject(input, User.class);
        input.close();
        assert "kryo".equals(newUser.getName());
    }

    /**
     * 测试类注册
     * @throws Exception
     */
    @Test
    public void test2() throws Exception {
        Kryo kryo = new Kryo();
        // 如果开启类注册功能，在遇到任何未注册的类时将抛出异常，阻止应用程序使用类名字符串来序列化
        //kryo.setRegistrationRequired(true);
        // 注册时会默认从10开始递增，默认使用0-9注册所有基本类型、基本类包装器、string和void，所以如果在手动指定id时要小心id覆盖的情况
        kryo.register(User.class);

        Object user = new User("123", "kryoR");
        Output output = new Output(new FileOutputStream("userKryo1.txt"));
        kryo.writeObject(output, user);
        output.close();

        Input input = new Input(new FileInputStream("userKryo1.txt"));
        User newUser = kryo.readObject(input, User.class);
        input.close();
        assert "kryoR".equals(newUser.getName());
    }

    @Test
    public void test3() throws Exception {
        Kryo kryo = new Kryo();
        User user = new User("123", "kryoR");
        Output output = new Output(new FileOutputStream("userKryo2.txt"));
        // 将对象的字节码信息也存入到序列化结果中，以便在反序列化时自行读取字节码信息
        kryo.writeClassAndObject(output, user);
        output.close();

        Input input = new Input(new FileInputStream("userKryo2.txt"));
        Object newUser = kryo.readClassAndObject(input);
        input.close();
        assert newUser instanceof User;
    }

    @Test
    public void test4() throws Exception {
        Kryo kryo = new Kryo();
        List<User> list = Lists.newArrayList(new User("123", "kryoR"));
        Output output = new Output(new FileOutputStream("userKryo3.txt"));
        kryo.writeObject(output, list);
        output.close();

        Input input = new Input(new FileInputStream("userKryo3.txt"));
        // 使用Kryo在反序列化自定义对象的list时无需像有些json工具一样透传泛型参数，因为Kryo在序列化结果里记录了泛型参数的实际类型的信息，反序列化时会根据这些信息来实例化对象
        List newList = kryo.readObject(input, List.class);
        input.close();
        assert newList.get(0) instanceof User;
    }

    @Test
    public void test5() throws Exception{
        long time = System.currentTimeMillis();
        Kryo kryo = new Kryo();
        Output output = new Output(new FileOutputStream("kryoPerformance.txt"));
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        for (int i = 0;i < 10000; i++) {
            kryo.writeObject(output, new User(String.valueOf(i), "test", false, Lists.newArrayList(String.valueOf(i)), map));
        }
        output.close();
        System.out.println("Kryo序列化消耗的时间" + (System.currentTimeMillis() - time));
    }

    @Test
    public void test6() throws Exception{
        long time = System.currentTimeMillis();
        Kryo kryo = new Kryo();
        Input input = new Input(new FileInputStream("kryoPerformance.txt"));
        User user = null;
        try {
            while (null != (user = kryo.readObject(input, User.class))) {

            }
        } catch (KryoException e) {

        }
        input.close();
        System.out.println("Kryo反序列化消耗的时间" + (System.currentTimeMillis() - time));
    }

    @Test
    public void test7() throws Exception{
        long time = System.currentTimeMillis();
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("JDKPerformance.txt"));
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        for (int i = 0;i < 10000; i++) {
            oos.writeObject(new User(String.valueOf(i), "test", false, Lists.newArrayList(String.valueOf(i)), map));
        }
        oos.close();
        System.out.println("JDK序列化消耗的时间" + (System.currentTimeMillis() - time));
    }

    @Test
    public void test8() throws Exception{
        long time = System.currentTimeMillis();
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("JDKPerformance.txt"));
        User user = null;
        try {
            while (null != (user = (User) ois.readObject())) {

            }
        } catch (EOFException e) {

        }
        System.out.println("JDK反序列化消耗的时间" + (System.currentTimeMillis() - time));
    }

    public byte[] jsonSerialize(Object data) throws IOException{
        SerializeWriter out = new SerializeWriter();
        JSONSerializer serializer = new JSONSerializer(out);
        // 注意补充对枚举类型的特殊处理
        serializer.config(SerializerFeature.WriteEnumUsingToString, true);
        // 额外补充类名可以在反序列化时获得更丰富的信息
        serializer.config(SerializerFeature.WriteClassName, true);
        serializer.write(data);
        return out.toBytes("UTF-8");
    }

    public <T> T jsonDeserialize(byte[] data, Class<T> tClass) throws IOException {
        return JSON.parseObject(new String(data), tClass);
    }

    public byte[] hessianSerialize(Object data) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Hessian2Output out = new Hessian2Output(bos);
        out.writeObject(data);
        out.flush();
        return bos.toByteArray();
    }

    public <T> T hessianDeserialize(byte[] bytes, Class<T> clz) throws IOException {
        Hessian2Input input = new Hessian2Input(new ByteArrayInputStream(bytes));
        return (T) input.readObject(clz);
    }

    @Test
    public void fileTest() {
        List<String> list = new ArrayList(new HashMap<>().values());
        System.out.println("1");
    }

    public KryoPool createPool() {
        return new KryoPool.Builder(() -> {
            Kryo kryo = new Kryo();
            // 此处也可以进行一系列配置，可通过实现KryoFactory接口来满足动态注册，抽象该类
            return kryo;
        }).softReferences().build();
    }
}
