package com.example.springtest.test01;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tomcat.util.security.MD5Encoder;

import java.sql.*;
import java.util.Date;

import static org.apache.tomcat.util.net.openssl.ciphers.MessageDigest.MD5;

public class InsertMySQL {

    static final String USER = "root";
    static final String PASS = "1020422790";
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/test?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";


    public static void insert() throws SQLException {
        // 开时时间
        Long begin = new Date().getTime();
        // sql前缀
        String prefix = "INSERT INTO test (username, `password`, email) VALUES";
        Connection conn = null;

        // 注册 JDBC 驱动
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        try {
            // 保存sql后缀
            StringBuffer suffix = new StringBuffer();
            // 设置事务为非自动提交
            conn.setAutoCommit(false);
            // Statement st = conn.createStatement();
            // 比起st，pst会更好些
            PreparedStatement pst =null;
                    String username=null;
            String password=null;
            String email=null;
            // 外层循环，总提交事务次数
            for (int i = 1; i <= 1; i++) {
                // 第次提交步长
                for (int j = 1; j <= 1; j++) {
                    // 构建sql后缀
                    suffix.append("(" + j * i + "user, "+ DigestUtils.md2Hex("12345") +", " + i * j);
                }
                // 构建完整sql
                String sql = prefix + suffix.substring(0, suffix.length() - 1);
                // 添加执行sql
                 pst = conn.prepareStatement(sql);
                pst.addBatch(sql);
                // 执行操作
                try {
                    pst.executeBatch();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                // 提交事务
                conn.commit();
                // 清空上一次添加的数据
                suffix = new StringBuffer();
            }
            // 头等连接
            pst.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 结束时间
        Long end = new Date().getTime();
        // 耗时
        System.out.println("cast : " + (end - begin) / 1000 + " ms");
    }

public static void initConn(){
    Connection conn = null;
    String url = "jdbc:mysql://localhost:3306/test?"
            + "user=root&password=1020422790&useUnicode=true&characterEncoding=UTF8&useSSL=false&serverTimezone=UTC";

        try {
        // 动态加载mysql驱动
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("成功加载MySQL驱动程序");
        conn = DriverManager.getConnection(url);
    } catch (Exception e) {
        e.printStackTrace();
    }
}


    public static String randomStr(int size) {
        //定义一个空字符串
        String result = "";
        for (int i = 0; i < size; ++i) {
            //生成一个97~122之间的int类型整数
            int intVal = (int) (Math.random() * 26 + 97);
            //强制转换（char）intVal 将对应的数值转换为对应的字符，并将字符进行拼接
            result = result + (char) intVal;
        }
        //输出字符串
        return result;
    }

    static Connection conn = null;
    public static void insert(int insertNum) throws SQLException {

        // 开时时间
        Long begin = System.currentTimeMillis();
        System.out.println("开始插入数据...");
        // sql前缀
        String prefix = "INSERT INTO test (username, password, email) VALUES ";
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        try {
            // 保存sql后缀
            StringBuffer suffix = new StringBuffer();
            // 设置事务为非自动提交
            conn.setAutoCommit(false);
            PreparedStatement pst = conn.prepareStatement("");
            for (int i = 1; i <= insertNum; i++) {
                // 构建sql后缀
                suffix.append("(" +  randomStr(8)  + "', "+DigestUtils.md5Hex("123456")+", " + i * Math.random() + "@163.com),");
            }
            // 构建完整sql
            String sql = prefix + suffix.substring(0, suffix.length() - 1);
            // 添加执行sql
            pst.addBatch(sql);
            // 执行操作
            pst.executeBatch();
            // 提交事务
            conn.commit();

            // 关闭连接
            pst.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 结束时间
        Long end = System.currentTimeMillis();
        System.out.println("插入"+insertNum+"条数据数据完成！");
        System.out.println("耗时 : " + (end - begin) / 1000 + " 秒");
    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        initConn();
        insert(1);

    }
}
