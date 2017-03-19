package myService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myEntity.OBJECT_T_MALL_ORDER;
import myEntity.T_MALL_FLOW;
import myMapper.Order_Mapper;
import myMapper.Shopping_Mapper;

@Service
public class OrderService {

	@Autowired
	Order_Mapper order_Mapper;

	@Autowired
	Shopping_Mapper shopping_Mapper;

	public int save(List<OBJECT_T_MALL_ORDER> list_object_order) {

		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
		hashMap.put("list_object_order", list_object_order);
		// 购物车id
		List<Integer> list_car_id = new ArrayList<Integer>();

		// 批量保存订单信息，返回保存的订单的id
		int insert_orders = 0;
		for (int i = 0; i < list_object_order.size(); i++) {
			order_Mapper.insert_order(list_object_order.get(i));
			insert_orders++;
		}

		// 批量保存物流信息，需要订单id
		List<T_MALL_FLOW> list_flow = new ArrayList<T_MALL_FLOW>();
		for (int i = 0; i < list_object_order.size(); i++) {
			T_MALL_FLOW t_MALL_FLOW = new T_MALL_FLOW();
			t_MALL_FLOW.setDd_id(list_object_order.get(i).getId());
			t_MALL_FLOW.setMdd(list_object_order.get(i).getDzh_mch());
			t_MALL_FLOW.setMqdd("还在仓库");
			t_MALL_FLOW.setPsfsh(list_object_order.get(i).getPsfsh());
			t_MALL_FLOW.setPsmsh("");
			t_MALL_FLOW.setPsshj(new Date());
			t_MALL_FLOW.setYh_id(list_object_order.get(i).getYh_id());
			t_MALL_FLOW.setYwy("");
			list_flow.add(t_MALL_FLOW);
			list_car_id.add(list_object_order.get(i).getGwch_id());
		}
		// order_Mapper.insert_flows(list_flow);
		//
		// // 根据购物车id，删除购物车数据
		// shopping_Mapper.delete_shoppingCar_by_car_id(list_car_id);
		//
		return insert_orders;
	}

	public int pay_orders(List<OBJECT_T_MALL_ORDER> list_object_order) {

		// 更新订单表信息
		int order_i = order_Mapper.update_orders_by_dd_id(list_object_order);

		// 插入物流信息
		List<T_MALL_FLOW> list_flow = new ArrayList<T_MALL_FLOW>();
		for (int i = 0; i < list_object_order.size(); i++) {
			T_MALL_FLOW t_MALL_FLOW = new T_MALL_FLOW();
			t_MALL_FLOW.setDd_id(list_object_order.get(i).getId());
			t_MALL_FLOW.setMdd(list_object_order.get(i).getDzh_mch());
			t_MALL_FLOW.setMqdd("准备仓库");
			t_MALL_FLOW.setPsfsh(list_object_order.get(i).getPsfsh());
			t_MALL_FLOW.setPsmsh("已经出库");
			t_MALL_FLOW.setPsshj(new Date());
			t_MALL_FLOW.setYh_id(list_object_order.get(i).getYh_id());
			t_MALL_FLOW.setYwy("硅谷快递员老张");
			t_MALL_FLOW.setLxfsh("110110110");
			list_flow.add(t_MALL_FLOW);
		}
		order_Mapper.insert_flows_pay(list_flow);

		// 更新sku表库存
		for (int i = 0; i < list_object_order.size(); i++) {
			order_Mapper.update_kc_by_sku_id(list_object_order.get(i));
		}
		return order_i;

	}

}
