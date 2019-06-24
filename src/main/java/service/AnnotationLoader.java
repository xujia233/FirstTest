package service;

import api.ScoreService;
import common.ScoreAno;
import entity.PageResult;
import entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import scan.ScanTest01;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 扫描某个包路径下的所有class
 * Created by xujia on 2019/3/1
 */
@Slf4j
public class AnnotationLoader {

    private static final String packageName = "scan";

    // 存放指定注解下的值与指定实现类的map
    private static Map<String, Class> map = new HashMap<>();

    /**
     * 存放指定类下的方法集
     */
    private static Map<QueryType, Method> methodMap = new HashMap<>();
    /**
     * 需要扫面的类数组
     */
    private static Class[] classes = new Class[] {ScanTest01.class};

    @Test
    public void test01 () {
        // 上文所说的在进程启动时调用可用@PostConstruct注解实现
        // 扫描指定class下所有被指定注解所标识的方法
        for (Class clz : classes) {
            Method[] methods = clz.getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(ScoreAno.class)) {
                    Annotation[] annotations = method.getAnnotations();
                    for (Annotation annotation : annotations) {
                        if (!(annotation instanceof ScoreAno))
                            continue;
                        ScoreAno scoreAno = (ScoreAno) annotation;
                        QueryType queryType = new QueryType(scoreAno.filterType(), scoreAno.queryType());
                        methodMap.put(queryType, method);
                    }
                }
            }
        }

        // 这里演示在service层通过反射调用
        Method method = getMethod("test", ScoreAno.QueryType.PAGE);
        try {
            PageResult<User> result = (PageResult<User>) method.invoke("123", "小明");

        } catch (IllegalAccessException | InvocationTargetException e) {

        }

    }

    public Method getMethod(String fielterType, ScoreAno.QueryType queryType) {
        return methodMap.get(new QueryType(fielterType, queryType));
    }


    /**
     * 内部类
     */
    private class QueryType {

        private String fielterType;
        private ScoreAno.QueryType queryType;

        public QueryType(String fielterType, ScoreAno.QueryType queryType) {
            this.fielterType = fielterType;
            this.queryType = queryType;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof QueryType)) return false;
            QueryType queryType1 = (QueryType) o;
            return Objects.equals(fielterType, queryType1.fielterType) &&
                    queryType == queryType1.queryType;
        }

        @Override
        public int hashCode() {
            return (fielterType + "_" + queryType.toString()).hashCode();
        }
    }

    public static void main(String[] agrs) {
        List<Class> classes = getAllClassByInterface(ScoreService.class, packageName, false);
        classes.forEach(cls -> {
            // 判断该类是否含有指定注解
            if (cls.isAnnotationPresent(ScoreAno.class)) {
                // 获取所有注解
                Annotation[] annotations = cls.getAnnotations();
                for (Annotation annotation : annotations) {
                    if (!annotation.equals(ScoreAno.class))
                        continue;
                    ScoreAno scoreAno = (ScoreAno) annotation;
                    // 存入特定map
                    map.put(scoreAno.filterType(), cls);
                }
            }
        });
        System.out.println(classes);
    }

    /**
     * @param intface 需要继承的接口类
     * @param packageName 包名
     * @param scanSubPackage 是否扫描子包
     */
    public static List<Class> getAllClassByInterface(Class intface, String packageName, boolean scanSubPackage) {
        List<Class> returnClassList = new ArrayList<>();
        if(!intface.isInterface()){
            throw new IllegalArgumentException("class must interface.");
        }

        List<Class<?>> allClass = getClasses(packageName, scanSubPackage);
        for (Class allClas : allClass) {
            if (intface.isAssignableFrom(allClas)) {
                if (!intface.equals(allClas)) {
                    returnClassList.add(allClas);
                }
            }
        }
        return returnClassList;
    }

    public static List<Class<?>> getClasses(String packageName, boolean scanSubPackage) {
        //第一个class类的集合
        List<Class<?>> classes = new ArrayList<>();
        //获取包的名字 并进行替换
        String packageDirName = packageName.replace('.', '/');
        //定义一个枚举的集合 并进行循环来处理这个目录下的things
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
        } catch (IOException e) {
            throw new IllegalArgumentException("packageName is illegal.", e);
        }
        //循环迭代下去
        while (dirs.hasMoreElements()) {
            //获取下一个元素
            URL url = dirs.nextElement();
            //得到协议的名称
            String protocol = url.getProtocol();
            //如果是以文件的形式保存在服务器上
            if ("file".equals(protocol)) {
                //获取包的物理路径
                String filePath = null;
                try {
                    filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                //以文件的方式扫描整个包下的文件 并添加到集合中
                findAndAddClassesInPackageByFile(packageName, filePath, scanSubPackage, classes);
            } else if ("jar".equals(protocol)) {
                //如果是jar包文件
                //定义一个JarFile
                JarFile jar = null;
                try {
                    //获取jar
                    jar = ((JarURLConnection) url.openConnection()).getJarFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //从此jar包 得到一个枚举类
                Enumeration<JarEntry> entries = jar.entries();
                //同样的进行循环迭代
                while (entries.hasMoreElements()) {
                    //获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                    JarEntry entry = entries.nextElement();
                    String name = entry.getName();
                    //如果是以/开头的
                    if (name.charAt(0) == '/') {
                        //获取后面的字符串
                        name = name.substring(1);
                    }
                    //如果前半部分和定义的包名相同
                    if (name.startsWith(packageDirName)) {
                        int idx = name.lastIndexOf('/');
                        //如果以"/"结尾 是一个包
                        if (idx != -1) {
                            //获取包名 把"/"替换成"."
                            packageName = name.substring(0, idx).replace('/', '.');
                        }
                        //如果可以迭代下去 并且是一个包
                        if ((idx != -1) || scanSubPackage) {
                            //如果是一个.class文件 而且不是目录
                            if (name.endsWith(".class") && !entry.isDirectory()) {
                                //去掉后面的".class" 获取真正的类名
                                String className = name.substring(packageName.length() + 1, name.length() - 6);
                                try {
                                    //添加到classes
                                    classes.add(Class.forName(packageName + '.' + className));
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }
        return classes;
    }

    private static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive, List<Class<?>> classes) {
        //获取此包的目录 建立一个File
        File dir = new File(packagePath);
        //如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        //如果存在 就获取包下的所有文件 包括目录
        File[] dirfiles = dir.listFiles(new FileFilter() {
            //自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
            public boolean accept(File file) {
                return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
            }
        });
        //循环所有文件
        for (File file : dirfiles) {
            //如果是目录 则继续扫描
            if (file.isDirectory()) {
                try {
                    findAndAddClassesInPackageByFile(packageName + "." + file.getName(),
                            file.getCanonicalPath(),
                            recursive,
                            classes);
                } catch (IOException e) {
                    log.warn("get folder error");
                }
            }
            else {
                //如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    //添加到集合中去
                    classes.add(Class.forName(packageName + '.' + className));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}


