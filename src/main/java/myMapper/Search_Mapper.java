package myMapper;

import java.util.HashMap;
import java.util.List;

import myEntity.OBJECT_T_MALL_SKU;
import myEntity.T_MALL_ADDRESS;
import myEntity.T_MALL_ATTR;
import myEntity.T_MALL_SKU;
import myEntity.T_MALL_USER;

public interface Search_Mapper {

	List<T_MALL_SKU> select_sku_by_flbh2(int flbh2);

	List<T_MALL_ATTR> select_attr_value_by_flbh2(int flbh2);

	List<OBJECT_T_MALL_SKU> select_sku_by_flbh2_attr_value(HashMap<Object, Object> hashMap);

	List<OBJECT_T_MALL_SKU> select_object_sku_by_shp_id_sku_id(
			HashMap<Object, Object> hashMap);

}
