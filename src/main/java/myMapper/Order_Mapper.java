package myMapper;

import java.util.ArrayList;
import java.util.List;

import myEntity.OBJECT_T_MALL_ORDER;
import myEntity.T_MALL_FLOW;

public interface Order_Mapper {

//	int insert_orders(List<OBJECT_T_MALL_ORDER> list);

	int insert_flows(ArrayList<T_MALL_FLOW> list);
	
	public int insert_order(OBJECT_T_MALL_ORDER object_T_MALL_ORDER);

	void insert_flows(List<T_MALL_FLOW> list_flow);

	int update_orders_by_dd_id(List<OBJECT_T_MALL_ORDER> list);

	void insert_flows_pay(List<T_MALL_FLOW> list);

	void update_kc_by_sku_id(OBJECT_T_MALL_ORDER order);

}
