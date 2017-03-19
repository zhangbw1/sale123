package myMapper;

import java.util.ArrayList;
import java.util.List;

import myEntity.T_MALL_SHOPPINGCAR;

public interface Shopping_Mapper {

	int insert_car(T_MALL_SHOPPINGCAR car);

	int update_car(T_MALL_SHOPPINGCAR car);

	List<T_MALL_SHOPPINGCAR> select_car_list_by_user(int id);

	int  delete_shoppingCar_by_car_id(ArrayList<Integer> list_car_id);

	void delete_shoppingCar_by_car_id(List<Integer> list_car_id);

}
