package com.wzq.jdbc;

import java.sql.*;

public class TestJDBC {
    public static void main(String[] args) {

        Connection connection=null;
        ResultSet rs = null;
        PreparedStatement ps=null;

        try {
            //1、注册驱动
            //Class.forName是把这个类加载到JVM中，加载的时候，就会执行其中的静态初始化块，完成驱动的初始化的相关工作。
//            Class.forName("com.mysql.jdbc.Driver");
            Driver driver=new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(driver);
            //2、获取连接
            connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/bjpowernode?serverTimezone=UTC"
                            ,"root","13054806895");
            //3、获取数据库操作对象
            String sql="select * from users where userId=?";
            /*程序到此处，会将带有占位符sql语句，给DBMS编译*/
            ps = connection.prepareStatement(sql);
            ps.setInt(1,5);
            //4、执行SQL语句
            rs=ps.executeQuery();
            //5、处理查询结果集（通常用再查询操作中）
            while (rs.next()){
                System.out.println(rs.getInt("userId"));  //获取指定列
                System.out.println(rs.getString("userName"));  //获取指定列
                System.out.println(rs.getString("password"));  //获取指定列
                System.out.println(rs.getString("sex"));  //获取指定列
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {  //放到finally代码块中的代码是必须执行的，一般用于释放资源
            //6、释放资源（关闭顺序问题、分开try问题）
            /*
              打开时：Connection -> PreparedStatement -> ResultSet
              关闭时：ResultSet-> PreparedStatement -> Connection
             */
            if (rs !=null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }

    }
}
