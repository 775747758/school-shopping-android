package com.school.shopping.net;
//服务器接口类
public class URLProtocol {

	//public static final String URL="http://schoolshoppingdwj.sinaapp.com";
	//public static final String URL="http://192.168.1.112:8080";
	
	//public static final String URL="172.22.4.158:8080";
	public static final String URL="http://172.22.4.158:8080";
	public static final String ROOT="";
	public static final int CMD_MOVE=101;
	public static final int CMD_REGISTER=1;
	public static final int CMD_UPLOAD_USER_PORTRAIT=102;
	public static final String GET_FRIENDS=URL+"/schoolShopping/friends/get_friends.do";
	public static final String GET_GOOD=URL+"/schoolShopping/goods/get_good.do";
	public static final String GET_GOODS_BY_TYPE=URL+"/schoolShopping/goods/get_goods_by_type.do";
	public static final String GET_HOME_GOODS=URL+"/schoolShopping/goods/get_goods_home.do";
	public static final String CHECK_LOGIN_STATE=URL+"/schoolShopping/user/checkLoginState.do";
	public static final String GET_TOKEN=URL+"/schoolShopping/user/gettoken.do";
	public static final String DELETE_GOOD=URL+"/schoolShopping/goods/delete_good.do";
	public static final String UPDATE_GOOD=URL+"/schoolShopping/goods/update_good.do";
	public static final String GET_All_GOODS=URL+"/schoolShopping/goods/get_goods.do";
	public static final String GET_MY_GOODS=URL+"/schoolShopping/goods/get_mygoods.do";
	public static final String ADD_A_GOOD=URL+"/schoolShopping/goods/add_good.do";
	public static final String GET_USER_VO=URL+"/schoolShopping/user/get_uservo.do";
	public static final String LOAD_USER_INFO=URL+"/schoolShopping/user/user_info.do";
	public static final String ALTER_USER=URL+"/schoolShopping/user/alter_user.do";
	public static final String LOGIN=URL+"/schoolShopping/user/login.do";
	public static final String DELETE_FFRIEND=URL+"/schoolShopping/friends/delete_friend.do";
	public static final String DELETE_FFRIENDS=URL+"/schoolShopping/friends/delete_friends.do";
	public static final String ADD_FRIEND=URL+"/schoolShopping/friends/add_friend.do";
	public static final String CHECK_USER_EXIST=URL+"/schoolShopping/user/isUserExist.do";
	public static final String REGISTER=URL+"/schoolShopping/user/regster.do";
	public static final String ALTER_USER_PORTRAIT=URL+"/schoolShopping/user/alter_user_portrait.do";
	public static final String DOWNLOAD_USER_PORTRAIT=URL+"/schoolShopping/user/download_portrait.do";
	public static final String UPLOAD_DOOD_PIC=URL+"/schoolShopping/goods/upload_goodpic.do";
	public static final String DOWNLOAD_DOOD_PIC=URL+"/schoolShopping/goods/download_goodpic.do";
	public static final String GET_UNIVERSITY="http://api.map.baidu.com/place/v2/search?ak=AVGQq9FQarO3yC1H0nUZq7Bt&output=json&radius=20000";
	public static final String MSG_SERVER_ERROR="服务器出现问题，我们会尽快解决！";
	public static final int STATUS_FAILURE=103;
	public static final int STATUS_SUCCESS=104;
	public static final int STATUS_NET_WRONG=105;
}
