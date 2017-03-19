package myUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.Cookie;

import org.apache.cxf.wsdl.http.UrlEncoded;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import myEntity.T_MALL_SHOPPINGCAR;

public class Mall_Tools {

	//public static final String ws_url = "http://localhost:18080/mall_webservice_cxf_user/MallUserWebServiceInf?wsdl";
	
	public static List<T_MALL_SHOPPINGCAR> get_cookie_list(Cookie[] cookies) {
		
		String cookie_carList = "";
		if(null != cookies && cookies.length >0){
			for (int i = 0; i < cookies.length; i++) {
				if(cookies[i].getName().equals("cookie_car_list")){
					cookie_carList = cookies[i].getValue();
				}
			}
		}
		Gson gson = new Gson();
		TypeToken<List<T_MALL_SHOPPINGCAR>> new_typeToken = new TypeToken<List<T_MALL_SHOPPINGCAR>>(){};
		try {
			cookie_carList = URLDecoder.decode(cookie_carList,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		List<T_MALL_SHOPPINGCAR> carList = gson.fromJson(cookie_carList, new_typeToken.getType());
		return carList;
	}

	public  static <T> String object_to_gson(T t) {
		
		Gson gson = new Gson();
		String json = gson.toJson(t);
		try {
			json = URLEncoder.encode(json,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return json ;
	}

	public static boolean if_new_car(List<T_MALL_SHOPPINGCAR> carList, T_MALL_SHOPPINGCAR car) {
		boolean b = true;
		for (int i = 0; i < carList.size(); i++) {
			if (car.getSku_id() == carList.get(i).getSku_id()) {
				b = false;
			}
		}
		return b;
	}
}
