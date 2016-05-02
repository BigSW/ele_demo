package com.chinamobile.util;


public interface Util {
	String DEVICEFLAG = "MOBILEPHONEAPP";
	//String SERVER_REQLOGIN_URL = "http://ip:port/cmbzassist/login";
	String SERVER_REQLOGIN_URL = "http://134.41.20.34:8080/MarketHelper/servlet/LoginServlet";
	String SERVER_REQDETECT_URL = "http://134.41.20.34:8080/MarketHelper/servlet/QueryServlet";
	String SERVER_SENDRESULT_URL = "http://134.41.20.34:8080/MarketHelper/servlet/RecommendServlet";
	String SERVER_REQRESETPSW_URL = "http://ip:port/cmbzassist/login";
	
	
	public String LOGIN_SUCESS = "1001";//成功
	public String LOGIN_FILED = "1002";   //登录失败
	public String NOT_EXIT = "1003";  // 用户不存在
	public String PSW_ERROR = "1004";//密码错误
	public String NO_PERMISSIOIN = "1008";   //该手机没有权限登录
	//public String ERRORPHONE = "10004";//请使用登录账户手机号所在的手机进行登录，或联系521市业支进行申请
	//public String NOMOBILEDEVICE = "10005";//"你好，请用手机”一线营销助手app“客户端正确访问";
}
