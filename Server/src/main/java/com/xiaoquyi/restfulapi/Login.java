package com.xiaoquyi.restfulapi;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;


import javax.naming.NamingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.xiaoquyi.utilities.*;

@Path("/login")
public class Login extends AbstractAPI {

	//TODO: Should using post here for security
	/**This interface accept the http POST request to login. API address:\a /login
	* 
	* @param userName The account user name want to logged in
	* @param passwd The password of the logging account
	* @return One instance of \ref jsonelements.Status which indicate the status of this post action, 
	* successful or not, contain the error code and text message
	*/
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String login(@QueryParam("username") String userName,
			@QueryParam("password") String passwd) throws NamingException, IOException  {
		allowCORS(); 
		String sqlGetPasswd = String.format(SQLStatements.S_GET_PASSWD_BY_NAME,userName);



		String accessToken = "-1";
		int uid = -1;
		try {
			String passInDB = null;
			Connection conn = DBconnector.getConnection();
			ResultSet rs = DBconnector.DBQuery(conn, sqlGetPasswd);
			if (rs.next()) {
				passInDB = rs.getString("user_pass");
				uid = rs.getInt("user_id");
			}
			rs.close();
			Logger.debug(String.format("sql [%s] executed and get the result: %s",sqlGetPasswd, passInDB));
			Logger.debug(String.format("Password from user is %s", passwd));
			if (passInDB!=null && passInDB.equals(passwd)) {
				getHttpSession().setAttribute("username", userName);
				accessToken = generateAccessToken(userName + Miscellaneous.getCurrentTimestamp());
				createOrUpdateSessionRecord(conn, uid, accessToken);
			}
			conn.close();
		}
		catch (SQLException se) {
			Logger.warning(se.getMessage());
		}
		Logger.debug(String.format("generated access token is : %s", accessToken));

		injectCookies("uid", ((Integer)uid).toString());
		injectCookies("accesstoken", accessToken);

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

	private int createOrUpdateSessionRecord(Connection conn, int uid, String accessToken) throws NamingException, IOException {


		String getAccessToken = String.format(SQLStatements.S_GET_ACCESSTOKEN_BY_UID,uid);
		try {
			//				Connection conn = DBconnector.getConnection();
			ResultSet rs = DBconnector.DBQuery(conn,getAccessToken);
			if (!rs.next()){
				// creat a session record when the user firstly log in
				String sqlCreateSession = String.format(SQLStatements.I_SESSION_RECORD,uid,accessToken);
				DBconnector.DBUpdate(conn,sqlCreateSession);
			}
			else {
				// update the already accesstoken and last updated time stamp
				String updateAccessToken = String.format(SQLStatements.U_SESSION_RECORD,accessToken,uid);
				DBconnector.DBUpdate(conn,updateAccessToken);
			}
			rs.close();
		}
		catch (SQLException se) {
			Logger.warning(se.getMessage());
			return -1;
		}

		return 0;

	}
}

