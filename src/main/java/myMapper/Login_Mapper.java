package myMapper;

import java.util.List;

import myEntity.T_MALL_ADDRESS;
import myEntity.T_MALL_SHOPPINGCAR;
import myEntity.T_MALL_USER;

public interface Login_Mapper {

	public T_MALL_USER select_user(T_MALL_USER user);

	public List<T_MALL_SHOPPINGCAR> get_car_list_by_user(int id);

}
