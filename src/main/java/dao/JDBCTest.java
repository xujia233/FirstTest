package dao;

import java.sql.*;

/**
 * Statement和preparedStatement的区别：
 * 1、前者适用于静态sql语句，后者适用于动态sql语句
 * 2、前者每执行一次sql语句，数据库都需要编译一次，即调用一次就会生成一次执行计划，而后者是预编译的，通过绑定不同的变量可以只生成一次执行计划
 * 3、虽然后者的效率比前者好很多，但是若是只有一次性操作的话，用前者会比较好，因为后者的对象开销是很大的
 * 4、后者的安全性更好，当需要在sql语句上绑定变量时，前者只能通过拼接的方式绑在sql语句的后面，该方式会将字符串的""也有可能拼进去导致意想不到的侵入性，而后者是通过set的方式绑定去除了字符串的隐患
 * Created by xujia on 2019/12/17
 */
public class JDBCTest {

    // jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=utf-8&useSSL=true
    private static final String url = "jdbc:mysql://localhost:3306/demo";
    private static final String userName = "root";
    private static final String password = "DBuser123!";

    public static void main(String[] args) throws Exception{
        // 1、加载mysql驱动程序
        Class.forName("com.mysql.jdbc.Driver");
        // 2、获取数据库连接
        Connection connection = DriverManager.getConnection(url, userName, password);
        // 3、创建Statement或PreparedStatement对象
//        Statement statement = connection.createStatement();
//        ResultSet rs = statement.executeQuery("select * from user");

        // 预编译测试
        PreparedStatement ps = connection.prepareStatement("select * from user where id = ?");
        ps.setString(1, "1");
        ResultSet rs = ps.executeQuery();
        // 循环打印日志
        while (rs.next()) {
            System.out.println(rs.getString("id") + "," + rs.getString("username"));
        }
    }
}
