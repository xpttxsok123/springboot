/*
package com.xp.ld;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

*/
/**
 * @ClassName TestJSch
 * @Description TODO
 * @Author
 * @Date 2019/3/25 21:55
 **//*

public class TestJSch {

    //本地端口
    static int lport = 33306;

    //远程MySQL服务器
    static String rhost = "localhost";


    //远程MySQL服务端口
    static int rport = 33306;

    public static void go() {

        //SSH连接用户名
        String user = "ubuntu";

        //SSH连接密码
        String password = "Njc3sfbIDTXc";

        //SSH服务器
        String host = "40.73.98.56";

        //SSH访问端口
        int port = 22;

        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            System.out.println(session.getServerVersion());//这里打印SSH服务器版本信息
            int assinged_port = session.setPortForwardingL(lport, rhost, rport);
            System.out.println("localhost:" + assinged_port + " -> " + rhost + ":" + rport);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sql() {
        Connection conn = null;
        ResultSet rs = null;
        Statement st = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:33306/dkey_am", "root", "qazwsxedc321");
            st = conn.createStatement();
            String sql = "SELECT COUNT(1) FROM am_access_access_session";
            rs = st.executeQuery(sql);
            while (rs.next())
                System.out.println(rs.getString(1));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //rs.close();st.close();conn.close();
        }
    }

    public static void main(String[] args) {
       go();
       sql();
    }

}
*/
