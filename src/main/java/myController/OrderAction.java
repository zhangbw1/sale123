package myController;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.sun.istack.logging.Logger;

import myEntity.MODEL_T_MALL_SHOPPINGCAR;
import myEntity.OBJECT_T_MALL_ORDER;
import myEntity.T_MALL_ADDRESS;
import myEntity.T_MALL_ORDER;
import myEntity.T_MALL_USER;
import myMallUserService.MallUserWebServiceInf;
import myService.OrderService;
import myUtil.MallUserWebServiceUtil;

@SessionAttributes("list_object_order")
@Controller
public class OrderAction {

	/*
	 * @Autowired MallUserWebServiceInf mallUserWebServiceInf;
	 */

	MallUserWebServiceInf mallUserWebServiceInf = MallUserWebServiceUtil.getService(
			"http://localhost:18080/mall_webservice_cxf_user/MallUserWebServiceInf?wsdl", MallUserWebServiceInf.class);
	@Autowired
	OrderService orderService;

	@RequestMapping(value = "/goto_orders", method = RequestMethod.POST) // @RequestParam
	public String goto_orders(MODEL_T_MALL_SHOPPINGCAR list_car, double sum, ModelMap map, HttpSession session) {

		T_MALL_USER user = (T_MALL_USER) session.getAttribute("user");
		List<OBJECT_T_MALL_ORDER> list_object_order = new ArrayList<OBJECT_T_MALL_ORDER>();
		if (user != null && user.getId() != 0) {
			List<T_MALL_ADDRESS> list_address = mallUserWebServiceInf.get_address_by_user(user);
			for (int i = 0; i < list_car.getList_car().size(); i++) {

				OBJECT_T_MALL_ORDER object_order = new OBJECT_T_MALL_ORDER();
				if (list_car.getList_car().get(i).getShfxz() == 1) {

					object_order.setGwch_id(list_car.getList_car().get(i).getId());
					// object_order.setDzh_id(dzh_id);
					// object_order.setDzh_mch(dzh_mch);
					// object_order.setId(id);
					object_order.setJdh(1);
					object_order.setList_address(list_address);
					object_order.setPsfsh("京东速递");
					object_order.setShhr(user.getYh_nch());
					object_order.setShp_id(list_car.getList_car().get(i).getShp_id());
					object_order.setShp_mch(list_car.getList_car().get(i).getSku_mch());
					object_order.setShp_tp(list_car.getList_car().get(i).getShp_tp());
					object_order.setSku_id(list_car.getList_car().get(i).getSku_id());
					object_order.setSku_shl(list_car.getList_car().get(i).getTjshl());
					object_order.setYh_id(list_car.getList_car().get(i).getYh_id());
					// object_order.setYjsdshj(yjsdshj);
					object_order.setZje(list_car.getList_car().get(i).getHj());
					list_object_order.add(object_order);
				}
			}
		} else {
			String err = "登陆后才能操作";
			return "mall_buy_login";
			// TODO
		}
		map.put("sum", sum);
		map.put("list_object_order", list_object_order);
		return "mall_order_list";
	}

	@RequestMapping(value = "save_orders", method = RequestMethod.POST) // int
																		// dzh_id,
	public String save_orders(@ModelAttribute("list_object_order") List<OBJECT_T_MALL_ORDER> list_object_order,
			int dzh_id, double sum, ModelMap map) {

		/* Logger logger = Logger.getLogger(T_MALL_ORDER.class); */
		// int dzh_id1 = Integer.parseInt(dzh_id);
		// System.out.println(dzh_id+"++++++++++++++++++++++");
		// int dzh_id = list_object_order.get(0).getDzh_id();

		for (int i = 0; i < list_object_order.size(); i++) {
			list_object_order.get(i).setDzh_id(dzh_id);

			String dzh_mch = "";
			List<T_MALL_ADDRESS> list_address = list_object_order.get(i).getList_address();
			for (int j = 0; j < list_address.size(); j++) {
				if (list_address.get(j).getId() == dzh_id) {
					dzh_mch = list_address.get(j).getYh_dz();
				}
			}
			list_object_order.get(i).setDzh_mch(dzh_mch);

		}
		// 保存订单
		orderService.save(list_object_order);

		map.put("list_object_order", list_object_order);

		return "redirect:/goto_pay_orders/" + sum + ".do";

	}

	@RequestMapping(value = "goto_pay_orders/{sum}", method = RequestMethod.GET)
	public String goto_pay_orders(@PathVariable double sum,
			@ModelAttribute("list_object_order") List<OBJECT_T_MALL_ORDER> list_object_order, ModelMap map) {
		map.put("sum", sum);
		map.put("list_object_order", list_object_order);
		return "mall_order_pay";
	}

	@RequestMapping(value = "/pay_orders", method = RequestMethod.POST)
	public String pay_orders(HttpSession session,@ModelAttribute("list_object_order") List<OBJECT_T_MALL_ORDER> list_object_order
			,Double sum) {

		// 调用支付接口
		// 调用支付订单的业务
		orderService.pay_orders(list_object_order);
		// 删除session中的订单
		session.removeAttribute("list_object_order");
		
		return "redirect:/pay_success.do";
	}

	@RequestMapping(value = "pay_success", method = RequestMethod.GET)
	public String pay_success() {
		return "pay_success";
	}

	@RequestMapping("goto_index")
	public String goto_index(){
		
		return "redirect:/index.do";
	}
}
