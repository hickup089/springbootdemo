package com.example.springtest.test01;

import org.apache.commons.codec.digest.DigestUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class test02 {
    static Connection conn = null;

    public static void initConn() throws ClassNotFoundException, SQLException {

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


    public static void insert(int insertNum) {
        // 开时时间
        Long begin = System.currentTimeMillis();
        System.out.println("开始插入数据...");
        // sql前缀
        String prefix = "INSERT INTO test (username, `password`, email) VALUES ";

        try {
            // 保存sql后缀
            StringBuffer suffix = new StringBuffer();
            // 设置事务为非自动提交
            conn.setAutoCommit(false);
            PreparedStatement pst = conn.prepareStatement(" ");
            for (int i = 1; i <= insertNum; i++) {
                // 构建sql后缀
                suffix.append("('" +  randomStr(8)  + "','"+ DigestUtils.md5Hex("123456")+"', '" + i * Math.random() + "@163.com'),");
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
        insert(5000000);

    }
}
