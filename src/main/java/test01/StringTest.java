package test01;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import entity.User;
import groovy.lang.GroovyShell;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.ErrorCollector;
import org.codehaus.groovy.control.MultipleCompilationErrorsException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import javax.tools.*;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

/**
 * Created by xujia on 2019/11/1
 */
public class StringTest {

    @Test
    public void tett() {
        String s = "import org.apache     ";
        System.out.println(s);
        if (s.endsWith(";")) {
            s = s.substring(0, s.length() - 1);
        }
        s = s.trim();
        System.out.println(s);
    }

    @Test
    public void test() {
        // groovy语法检查
        GroovyShell shell = new GroovyShell();
        try {
            shell.parse("import uyun.hornet.common.exception.ExceptionConst;\n" +
                    "import uyun.hornet.common.exception.HornetException;\n" +
                    "\n" +
                    "def count = 5\n" +
                    "def res = 0\n" +
                    "(1..< count).each {res += count}\n" +
                    "def hh()");
        } catch (CompilationFailedException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void javaCompileTest() {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

        Iterable<? extends JavaFileObject> iterable = fileManager.getJavaFileObjectsFromStrings(Arrays.asList("public class HelloBuggyWorld {\n" +
                "    String test \n" +
                "\n" +
                "    public static void main (String [] args) {\n" +
                "        System.out.println('Hello World!');  \n" +
                "    }\n" +
                "}"));
        DiagnosticCollector<JavaFileObject> diagnosticCollector = new DiagnosticCollector<>();
        compiler.getTask(null, fileManager, diagnosticCollector, null, null, iterable).call();
        List<String> messages = new ArrayList<>();
        Formatter formatter = new Formatter();
        for (Diagnostic diagnostic : diagnosticCollector.getDiagnostics()) {
            messages.add(diagnostic.getKind() + ":\t Line [" + diagnostic.getLineNumber() + "] \t Position [" + diagnostic.getPosition() + "]\t" + diagnostic.getMessage(Locale.ROOT) + "\n");
        }
        System.out.println(messages);

    }

    @Test
    public void tt() {
        User user1 = new User("123", "xj");
        User user2 = new User("123", "xj2");
        List<User> list = Lists.newArrayList(user1, user2);
        String[] arr = list.stream().map(User::getId).distinct().toArray(String[]::new);
        System.out.println(arr.length);
        List<String> strings = Lists.newArrayList("1", "2", "3", "1", "2");
        strings.stream().distinct().findFirst();
        System.out.println();
    }

    @Test
    public void tea() {
        Set<String> set1 = Sets.newHashSet("1");
        Set<String> set2 = Sets.newHashSet("1", "2");
        Sets.SetView<String> setView = Sets.difference(set1, set2);
        System.out.println(setView);
    }

    @Test
    public void test11() throws Exception {
        User user = new User("123", "xujia");
        ObjectMapper mapper = new ObjectMapper();
        StringWriter stringWriter = new StringWriter();
        mapper.writeValue(stringWriter, user);
        System.out.println(stringWriter.toString());
        System.out.println("123");
    }

    /**
     * url正则表达式
     */
    @Test
    public void test12() {
        AtomicInteger atomicInteger = new AtomicInteger(5);
        atomicInteger.decrementAndGet();
        System.out.println(atomicInteger.get());

        String str = "https://www.com.uu";
        if (Pattern.matches("^(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]$", str)) {
            System.out.println("匹配成功");
        } else {
            System.out.println("匹配失败");
        }
    }

    @Test
    public void test13() {
        List<String> str = Lists.newArrayList("1", "2");
        str.removeIf(s -> s.equals("1"));
        System.out.println(str);
    }

    @Test
    public void test14() {
        int i = 2147483647; // 2^31-1
        short s = 12356;
        long l = 1233333333333333323L;
        long h = 9223372036854775807L;
        double d = 1.123444343434343434334434343434123332123121312312131; // 16位
        System.out.println(d);
        System.out.println(l);
        System.out.println(Long.MAX_VALUE);
    }

    @Test
    public void test15() throws Exception{
        // byte[] buffer = new byte[12];
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        User user = new User("1", "test", false, Lists.newArrayList("2"), map);
        ByteArrayOutputStream bos = new ByteArrayOutputStream(12);
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(user);
        System.out.println("byte length : " + bos.toByteArray().length);
    }

    @Test
    public void test16() throws Exception{
        String s = "!@#$%^&*()+";
        String name = "!%40%23%24%25%5E%26*()%2B";
        System.out.println(URLDecoder.decode(URLDecoder.decode(name.replaceAll("%", "%25"), "UTF-8"), "UTF-8"));
        //System.out.println(URLDecoder.decode(s.replaceAll("\\+", "%2B"), "UTF-8"));
        System.out.println(URLEncoder.encode(s, "UTF-8"));
    }

    @Test
    public void test17() throws Exception{
        String s = "!@#$%^&*()+";
        System.out.println(URLDecoder.decode(URLDecoder.decode(s.replaceAll("%", "%25"), "UTF-8"), "UTF-8"));
    }

    @Test
    public void test18() {
        List<String> list = Lists.newArrayList("1", "2");
        System.out.println(list);
        list.clear();
        System.out.println(list.size());
        list.add("3");
        System.out.println(list);
    }
}
