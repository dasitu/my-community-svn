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
	                'a', 'b', 'c', 'd', 'e', 'f' };// �������ֽ�ת����16���Ʊ�ʾ���ַ�  
	        try {  
	            java.security.MessageDigest md = java.security.MessageDigest  
	                    .getInstance("MD5");  
	            md.update(userName.getBytes());
	            byte tmp[] = md.digest();// MD5 �ļ�������һ�� 128 λ�ĳ�������  
	            // ���ֽڱ�ʾ���� 16 ���ֽ�  
	            char str[] = new char[16 * 2];// ÿ���ֽ��� 16 ���Ʊ�ʾ�Ļ���ʹ�������ַ��� ���Ա�ʾ�� 16  
	            // ������Ҫ 32 ���ַ�  
	            int k = 0;// ��ʾת������ж�Ӧ���ַ�λ��  
	            for (int i = 0; i < 16; i++) {// �ӵ�һ���ֽڿ�ʼ���� MD5 ��ÿһ���ֽ�// ת���� 16  
	                // �����ַ���ת��  
	                byte byte0 = tmp[i];// ȡ�� i ���ֽ�  
	                str[k++] = hexDigits[byte0 >>> 4 & 0xf];// ȡ�ֽ��и� 4 λ������ת��,// >>>  
	                // Ϊ�߼����ƣ�������λһ������  
	                str[k++] = hexDigits[byte0 & 0xf];// ȡ�ֽ��е� 4 λ������ת��  
	  
	            }  
	            accessToken = new String(str);// ����Ľ��ת��Ϊ�ַ���  
	  
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