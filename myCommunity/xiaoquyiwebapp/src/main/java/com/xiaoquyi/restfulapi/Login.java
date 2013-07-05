package com.xiaoquyi.restfulapi;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;


import javax.naming.NamingException;
import javax.ws.rs.*;

import com.xiaoquyi.utilities.*;

@Path("/login")
public class Login extends AbstractAPI {

	@GET
	public String login(@QueryParam("user_name") String userName,
						@QueryParam("password") String passwd) throws NamingException, IOException  {

		String sqlGetPasswd = String.format(SQLStatements.S_GET_PASSWD_BY_NAME,userName);
		Object re = DBconnector.executeSqlStatement(sqlGetPasswd);


		try {
			if ((Integer)re == -1)
				Logger.warningWritting("SQL execute exception,please check the sql statement and DB real data!");
		}
		catch(ClassCastException cce) {
			Logger.warningWritting(cce.getMessage());
		}

		ResultSet rs = (ResultSet)re;
		
		String accessToken = "-1";
		try {
			String PassInDB = null;
			if (rs.next())
				PassInDB = rs.getString("user_pass");
			rs.close();
			Logger.debugWritting(String.format("sql [%s] executed and get the result: %s",sqlGetPasswd, PassInDB));
			Logger.debugWritting(String.format("Password from user is %s", passwd));
			if (PassInDB.equals(passwd)) {
				accessToken = generateAccessToken(userName + Miscellaneous.getCurrentTimestamp());
				createSessionRecord(userName,accessToken);
			}
		}
		catch (SQLException se) {
			Logger.warningWritting(se.getMessage());
		}
		Logger.debugWritting(String.format("generated access token is : %s", accessToken));
		return accessToken;
	}
	
	private String generateAccessToken(String userName) {
	//TODO:this method can be extract to a utility method of MD5 encrypto 
	        String accessToken = null;  
	        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',  
	                'a', 'b', 'c', 'd', 'e', 'f' };// 用来将字节转换成16进制表示的字符  
	        try {  
	            java.security.MessageDigest md = java.security.MessageDigest  
	                    .getInstance("MD5");  
	            md.update(userName.getBytes());
	            byte tmp[] = md.digest();// MD5 的计算结果是一个 128 位的长整数，  
	            // 用字节表示就是 16 个字节  
	            char str[] = new char[16 * 2];// 每个字节用 16 进制表示的话，使用两个字符， 所以表示成 16  
	            // 进制需要 32 个字符  
	            int k = 0;// 表示转换结果中对应的字符位置  
	            for (int i = 0; i < 16; i++) {// 从第一个字节开始，对 MD5 的每一个字节// 转换成 16  
	                // 进制字符的转换  
	                byte byte0 = tmp[i];// 取第 i 个字节  
	                str[k++] = hexDigits[byte0 >>> 4 & 0xf];// 取字节中高 4 位的数字转换,// >>>  
	                // 为逻辑右移，将符号位一起右移  
	                str[k++] = hexDigits[byte0 & 0xf];// 取字节中低 4 位的数字转换  
	  
	            }  
	            accessToken = new String(str);// 换后的结果转换为字符串  
	  
	        } catch (NoSuchAlgorithmException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }  
	        return accessToken;  
	    }  
		
	private int createSessionRecord(String userName, String accessToken) throws NamingException, IOException {
		
		String sqlGetUid = String.format(SQLStatements.S_GET_USER_ID_BY_NAME,userName);
		Object re = DBconnector.executeSqlStatement(sqlGetUid);


		try {
			if ((Integer)re == -1)
				Logger.warningWritting("SQL execute exception,please check the sql statement and DB real data!");
		}
		catch(ClassCastException cce) {
			Logger.warningWritting(cce.getMessage());
		}

		ResultSet rs = (ResultSet)re;
		
		try {
			int uid = 0;
			if (rs.next())
				uid = rs.getInt("user_id");
			rs.close();
			Logger.debugWritting(String.format("sql [%s] executed and get the result: %s",sqlGetUid, uid));
			
			String sqlCreateSession = String.format(SQLStatements.I_SESSION_RECORD,uid,accessToken,Miscellaneous.getCurrentTimestamp());
			DBconnector.executeSqlStatement(sqlCreateSession);
			
		}
		catch (SQLException se) {
			Logger.warningWritting(se.getMessage());
			return -1;
		}
		return 0;
		

	}
	
	}