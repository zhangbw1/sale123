package myController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.cxf.wsdl.http.UrlEncoded;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;

import myEntity.T_MALL_SHOPPINGCAR;
import myEntity.T_MALL_USER;
import myMallUserService.MallUserWebServiceInf;
import myService.LoginService;
import myService.ShoppingService;
import myUtil.MallUserWebServiceUtil;
import myUtil.Mall_Tools;

@Controller
public class LoginAction {

	@Autowired
	LoginService loginService;
	
	@Autowired
	ShoppingService shoppingService;
	
	//WebService 
	MallUserWebServiceInf webService = MallUserWebServiceUtil.getService("http://localhost:18080/mall_webservice_cxf_user/MallUserWebServiceInf?wsdl",
			MallUserWebServiceInf.class);
//	MallUserWebServiceInf webService = MallUserWebServiceUtil.getService("http://192.168.10.137:28080/sale/MallUserWebServiceInf?wsdl",
//			MallUserWebServiceInf.class);
	/***
	 * 跳入首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpSession session) {
		Cookie[] cookies = request.getCookies();
		String cookie_user = "";
		if (null != cookies) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("mall_user")) {
					cookie_user = cookies[i].getValue();
				}
			}
		}

		// 当用户选择自动登录时，访问首页获得浏览器的用户信息
		if (!cookie_user.equals("")) {
			// cookie_user
			Gson gson = new Gson();
			try {
				String decode = URLDecoder.decode(cookie_user, "utf-8");
				T_MALL_USER user = gson.fromJson(decode, T_MALL_USER.class);
				// 验证用户是否正确
				session.setAttribute("user", user);
			} catch (UnsupportedEncodingException e) {
		
				e.printStackTrace();
			}
		}
		return "index";
	}

	/***
	 * 跳入登录首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/to_login", method = RequestMethod.GET)
	public String to_login(HttpServletRequest request, HttpSession session,
			ModelMap map) {

	/*	Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				String name = cookies[i].getName();
				if (name.equals("mall_user")) {
					String value = cookies[i].getValue();
					Gson gson = new Gson();
					try {
						value = URLDecoder.decode(value, "utf-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}

					T_MALL_USER fromJson = gson.fromJson(value,
							T_MALL_USER.class);
					T_MALL_USER login = loginService.login(fromJson);// 调用认证服务
					// 如果用户正确
					if (null != login) {
						session.setAttribute("user", login);

						return "index";
					}

				} else {
					// 如果用户错误
					map.put("err", "用户名或者密码错误");
					return "mall_login";
				}

			}
		}*/

		return "mall_login";

	}

	/***
	 * 普通登录
	 * 
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(T_MALL_USER user, String auto_login,
			HttpSession session, HttpServletRequest request,HttpServletResponse response, ModelMap map) {

		// 根据用户的名和密码查询数据库
		T_MALL_USER login = webService.login(user);// 调用认证服务

		// 如果用户正确
		if (null != login) {
			session.setAttribute("user", login);
			// 判断是否自动登录
			if ("1".equals(auto_login)) {
				// 创建cookie，将cookie发送浏览器
				Cookie cookie;
				try {
					cookie = new Cookie("mall_user", URLEncoder.encode(
							new Gson().toJson(login), "utf-8"));
					cookie.setMaxAge(60 * 60);
					response.addCookie(cookie);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			//同步cookie,session,数据库
			car_list_cookie_db_session(session, response, request);
			return "index";
		} else {
			// 如果用户错误
			map.put("err", "用户名或者密码错误");
			return "mall_login";
		}
	}

	
	private void car_list_cookie_db_session(HttpSession session, HttpServletResponse response,
			HttpServletRequest request) {
		
		//获得用户信息
		T_MALL_USER user = (T_MALL_USER) session.getAttribute("user");
		Cookie[] cookies = request.getCookies();
		
		//获得cookie中的carlist
		List<T_MALL_SHOPPINGCAR> cookie_carList = Mall_Tools.get_cookie_list(cookies);
		//从数据库中获得购物项
		List<T_MALL_SHOPPINGCAR> db_carList = shoppingService.get_car_list_by_user(user.getId());
		
		if(null == cookie_carList || cookie_carList.size() == 0){
			session.setAttribute("session_car_list", db_carList);
		}else{
			if(null == db_carList || db_carList.size() ==0){
				for (int i = 0; i < cookie_carList.size(); i++) {
					cookie_carList.get(i).setId(user.getId());
					shoppingService.add_car(cookie_carList.get(i));
				}
				session.setAttribute("session_car_list", cookie_carList);
			}else{
				
				for (int i = 0; i < cookie_carList.size(); i++) {
					
					 boolean b = Mall_Tools.if_new_car(db_carList, cookie_carList.get(i));
					 if(b == true){
						 cookie_carList.get(i).setId(user.getId());
						 shoppingService.add_car(cookie_carList.get(i));
					 }else{
						 for (int j = 0; j < db_carList.size(); j++) {
							 if(db_carList.get(j).getSku_id() == cookie_carList.get(i).getSku_id()) {
								 cookie_carList.get(i).setId(db_carList.get(j).getId());
								 cookie_carList.get(i).setTjshl(db_carList.get(j).getTjshl() + cookie_carList.get(i).getTjshl());
								 shoppingService.change_car(cookie_carList.get(i));
							 }
						}
					 }
				}
				//同步session
				session.setAttribute("session_car_list", shoppingService.get_car_list_by_user(user.getId()));
			}
			
		}
		response.addCookie(new Cookie("cookie_car_list", ""));
	}

	/***
	 * 登出
	 * 
	 * @return
	 */
	@RequestMapping(value = "/log_out", method = RequestMethod.GET)
	public String log_out(HttpSession session,HttpServletRequest request) {

		/*Cookie[] cookies = request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			if(cookies[i].getName() == "mall_user"){
				cookies[i].
			}
		}*/
		session.removeAttribute("user");
   
		return "index";

	}

}
