/**
 * 
 */
package com.fsgame.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author sunweidong
 *
 *1、连接数据库工具类
 *
 */
public class DBUtil {

	/**
	 * @param args
	 */
	private static Connection conn;
	
	private static String url = "jdbc:sqlserver://localhost:1433;DatabaseName=fsgame;";
	
	public static void main(String[] args) {
		 
	        
	        try {
	            // 连接数据库
	            conn = DriverManager.getConnection(url, "sa", "19971124swD");
//	            if (conn != null) {
//	                conn.close();
//	                conn = null;
//	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            System.out.println("数据库连接失败");
	        }
    }

}
