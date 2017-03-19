package myController;

import java.lang.Character.UnicodeScript;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import myEntity.T_MALL_SHOPPINGCAR;
import myEntity.T_MALL_USER;
import myService.ShoppingService;
import myUtil.Mall_Tools;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;

@Controller
public class ShoppingCarAction {

	@Autowired
	ShoppingService shoppingService;

	/***
	 * 判断用户是否登录 登陆的话判断是否已经存在购物项 没登录的话判断是否已经存在购物项
	 * 
	 * @param car
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/add_car", method = RequestMethod.POST)
	public String add_car(T_MALL_SHOPPINGCAR car, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {

		/*T_MALL_USER user = (T_MALL_USER) session.getAttribute("user");

		// 用户未登录
		if (null == user || user.getId() == 0) {
			Cookie[] cookies = request.getCookies();
			// 从cookies中取出购物车集合
			List<T_MALL_SHOPPINGCAR> carList = Mall_Tools.get_cookie_list(cookies);
			// 判断购物车里是不是已经存在购物项
			if (null == carList || carList.size() == 0) {
				carList = new ArrayList<T_MALL_SHOPPINGCAR>();
				carList.add(car);
				// 工具方法转换成cookie格式，每次操作cookie都要response.addCookie(cookie);
				// 因为获取的cookie都只是副本
				
				 String cookie_mall_car = Mall_Tools.object_to_gson(carList);
				 Cookie cookie = new Cookie("cookie_mall_car",cookie_mall_car); 
				 cookie.setMaxAge(60*60);
				 response.addCookie(cookie);
				 
			} else {
				// 判断是否已经存在该购物项
				boolean b = Mall_Tools.if_new_car(carList, car);
				if (b == true) {
					// 是新的购物数据，更新cookie中的购物车数据
					carList.add(car);
				} else {
					for (int i = 0; i < carList.size(); i++) {
						if (carList.get(i).getSku_id() == car.getSku_id()) {
							carList.get(i).setTjshl(carList.get(i).getTjshl() + 1);
						}
					}
				}
				
				 * boolean is_new_car = true; //cookie中已经存在购物项 for (int i = 0; i
				 * < carList.size(); i++) { if(car.getId() ==
				 * carList.get(i).getId() ){ is_new_car =false; } }
				 * //判断是否已经存在购物项，没有直接添加，有的话原有数量上加1 if(is_new_car == true){
				 * carList.add(car); }else{ for (int i = 0; i < carList.size();
				 * i++) { if(car.getId() == carList.get(i).getId() ){
				 * carList.get(i).setTjshl(carList.get(i).getTjshl()+1); } } }
				 
				
				 String cookie_mall_car = Mall_Tools.object_to_gson(carList);
				 Cookie cookie = new Cookie("cookie_mall_car",cookie_mall_car); 
				 cookie.setMaxAge(60*60*24);
				 response.addCookie(cookie);
			}
		}*/
		T_MALL_USER user = (T_MALL_USER) session.getAttribute("user");

		// 用户未登录
		if (null == user || user.getId() == 0) {
			Cookie[] cookies = request.getCookies();

			// 从cookie中取出，取出购物车数据
			List<T_MALL_SHOPPINGCAR> carList = Mall_Tools.get_cookie_list(cookies);
			// 如果cookie里没有购物数据，直接新增
			if (null == carList || carList.size() == 0) {
				carList = new ArrayList<T_MALL_SHOPPINGCAR>();
				carList.add(car);
				String cookie_mall_car = Mall_Tools.object_to_gson(carList);
				Cookie cookie = new Cookie("cookie_car_list", cookie_mall_car);
				cookie.setMaxAge(60 * 60 * 24 * 30);
				response.addCookie(cookie);
			} else {
				// 如果cookie中有购物数据
				// 判断是新的购物数据还是原有的购物数据
				boolean b = Mall_Tools.if_new_car(carList, car);
				if (b == true) {
					// 是新的购物数据，更新cookie中的购物车数据
					carList.add(car);
				} else {
					for (int i = 0; i < carList.size(); i++) {
						if (carList.get(i).getSku_id() == car.getSku_id()) {
							carList.get(i).setTjshl(carList.get(i).getTjshl() + 1);
						}
					}
				}
				// 更新cookie
				String cookie_mall_car = Mall_Tools.object_to_gson(carList);
				Cookie cookie = new Cookie("cookie_car_list", cookie_mall_car);
				// 从客户端浏览器取出的cookie，修改后要重新放入客户端，并且重新设置过期时间
				cookie.setMaxAge(60 * 60 * 24 * 30);
				response.addCookie(cookie);
			}
			// 用户已经登录
		}
		// 用户登录后
		else {
			List<T_MALL_SHOPPINGCAR> carList = (List<T_MALL_SHOPPINGCAR>) session.getAttribute("session_car_list");
			if (null == carList || carList.size() == 0) {

				car.setYh_id(user.getId());
				// 插入数据库
				int i = shoppingService.add_car(car);
				//
				carList = new ArrayList<T_MALL_SHOPPINGCAR>();
				carList.add(car);
			} else {
				// 判断是否已经存在该购物项
				Boolean is_new_car = Mall_Tools.if_new_car(carList, car);
				if (is_new_car == true) {

					car.setYh_id(user.getId());
					int i = shoppingService.add_car(car);
					carList.add(car);
				} else {
					for (int j = 0; j < carList.size(); j++) {
						if (carList.get(j).getSku_id() == car.getSku_id()) {
							carList.get(j).setTjshl(carList.get(j).getTjshl() + 1);
							car.setId(carList.get(j).getId());
							car.setTjshl(carList.get(j).getTjshl());
						}
					}
					int i = shoppingService.change_car(car);
				}
			}
			// 同步session
			session.setAttribute("session_car_list", carList);
		}

		return "mall_add_car_success";
	}

	/**
	 * 点击购物车进入购物列表
	 * 
	 * @param session
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/goto_car")
	public String goto_car(HttpSession session, HttpServletRequest request, ModelMap map) {

		T_MALL_USER user = (T_MALL_USER) session.getAttribute("user");
		List<T_MALL_SHOPPINGCAR> carList = null;
		if (null == user || user.getId() == 0) {
			Cookie[] cookies = request.getCookies();
			carList = Mall_Tools.get_cookie_list(cookies);
		} else {
			carList = (List<T_MALL_SHOPPINGCAR>) session.getAttribute("session_car_list");
		}
		double sum = 0.0;
		for (int i = 0; i < carList.size(); i++) {
			if (carList.get(i).getShfxz() == 1) {
				sum = sum + carList.get(i).getHj();
			}
		}
		map.put("sum", sum);
		map.put("carList", carList);
		return "mall_shoppingCar";
	}

	@RequestMapping(value = "/miniCar")
	public String miniCar(HttpSession session, HttpServletRequest request, ModelMap map) {

		
		T_MALL_USER user = (T_MALL_USER) session.getAttribute("user");
		List<T_MALL_SHOPPINGCAR> carList = null; // (List<T_MALL_SHOPPINGCAR>)
													// session.getAttribute("session_car_list");
		if (user == null || user.getId() == 0) {
			Cookie[] cookies = request.getCookies();
			carList = Mall_Tools.get_cookie_list(cookies);
		} else {
			carList = (List<T_MALL_SHOPPINGCAR>) session.getAttribute("session_car_list");
		}
		map.put("carList", carList);
		return "mall_miniCar_content";
	}

	@RequestMapping(value = "/change_shoppingCar")
	public String change_shoppingCar(T_MALL_SHOPPINGCAR car, HttpSession session,HttpServletRequest request,HttpServletResponse response, ModelMap map) {

		T_MALL_USER user = (T_MALL_USER) session.getAttribute("user");
		List<T_MALL_SHOPPINGCAR> carList = null;
		if (user == null || user.getId() == 0) {
			// 用户没有登录，根据提交参数更新cookie的购物数据
			Cookie[] cookies = request.getCookies();
			carList = Mall_Tools.get_cookie_list(cookies);
			for (int i = 0; i < carList.size(); i++) {
				if (carList.get(i).getSku_id() == car.getSku_id()) {
					carList.get(i).setShfxz(car.getShfxz());
					carList.get(i).setTjshl(car.getTjshl());
//					carList.get(i).getShfxz() 
						carList.get(i).setHj(carList.get(i).getTjshl() * carList.get(i).getSku_jg());
					
				}
			}
			String cookie_mall_car = Mall_Tools.object_to_gson(carList);
			Cookie cookie = new Cookie("cookie_car_list", cookie_mall_car);
			cookie.setMaxAge(60 * 60 * 24 * 30);
			response.addCookie(cookie);
			map.put("carList", carList);
		} else {
			// 更改session和数据库中数据
			carList = (List<T_MALL_SHOPPINGCAR>) session.getAttribute("session_car_list");
			for (int i = 0; i < carList.size(); i++) {
				if (carList.get(i).getSku_id() == car.getSku_id()) {
					carList.get(i).setShfxz(car.getShfxz());
					carList.get(i).setTjshl(car.getTjshl());
//					if(carList.get(i).getShfxz() == 1){
						carList.get(i).setHj(carList.get(i).getTjshl() * carList.get(i).getSku_jg());
//					}
					car = carList.get(i);
				}
			}
			shoppingService.change_car(car);
			
			// session.setAttribute("session_car_list", carList);
			map.put("carList", carList);
		}
		double sum = 0.0;
		for (int i = 0; i < carList.size(); i++) {
			if(carList.get(i).getShfxz() == 1){
				sum = sum + carList.get(i).getHj();
			}
		}
		//System.out.println(sum+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		map.put("sum", sum);
		return "mall_shoppingCar_content";
//		return "redirect:/mall_shoppingCar_content.jsp";
	}

}
