package myService;

import myEntity.T_MALL_SHOPPINGCAR;
import myMapper.Shopping_Mapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingService {
    
	@Autowired
	Shopping_Mapper shopping_Mapper;
	
	public int add_car(T_MALL_SHOPPINGCAR car) {
	
		int count_insert_car = shopping_Mapper.insert_car(car);
		return count_insert_car;
	}

	public int change_car(T_MALL_SHOPPINGCAR car) {
		int count_updata_car = shopping_Mapper.update_car(car);
		return count_updata_car;
	}

	public List<T_MALL_SHOPPINGCAR> get_car_list_by_user(int id) {
		
		List<T_MALL_SHOPPINGCAR> carList = shopping_Mapper.select_car_list_by_user(id);
		return carList;
	}

	

}
