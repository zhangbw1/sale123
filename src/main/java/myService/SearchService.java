package myService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myEntity.OBJECT_T_MALL_SKU;
import myEntity.T_MALL_ATTR;
import myEntity.T_MALL_SKU;
import myEntity.T_MALL_SKU_ATTR_VALUE;
import myMapper.Search_Mapper;

@Service
public class SearchService {

	@Autowired
	Search_Mapper search_Mapper;

	public List<OBJECT_T_MALL_SKU> get_sku_by_flbh2_attr_value(int flbh2, String order_by, List<T_MALL_SKU_ATTR_VALUE> list_attr_value) {

		StringBuilder sb = new StringBuilder();

		if (null != list_attr_value) {
			sb.append(" and b.id in ( select sku_0.sku_id from ");

			for (int i = 0; i < list_attr_value.size(); i++) {
				sb.append("(select sku_id from t_mall_sku_attr_value where shxm_id ="
						+ list_attr_value.get(i).getShxm_id() + " and shxzh_id=" + list_attr_value.get(i).getShxzh_id()
						+ ") sku_" + i);
				if (i != list_attr_value.size() - 1) {
					sb.append(" ,");
				}
			}

		
			
			if (list_attr_value.size() > 1) {
				sb.append(" where ");
				for (int i = 0; i < list_attr_value.size(); i++) {
					if (i != (list_attr_value.size() - 1)) {
						sb.append("sku_" + i + ".sku_id=sku_" + (i + 1) + ".sku_id");
					}
					if (i < (list_attr_value.size() - 2)) {// 计算错误
						sb.append(" and ");
					}
				}
			}

			sb.append(") ");
		}

		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
		hashMap.put("flbh2", flbh2);
		hashMap.put("order_by", order_by);
		if (null == list_attr_value || list_attr_value.size() == 0) {
			hashMap.put("sql", "");
		} else {
			hashMap.put("sql", sb.toString());
		}

		List<OBJECT_T_MALL_SKU> list_object_sku = search_Mapper.select_sku_by_flbh2_attr_value(hashMap);
		return list_object_sku;
	}

	public Map<Object, Object> get_sku_attr_by_flbh2(int flbh2) {
		List<T_MALL_SKU> list_sku = search_Mapper.select_sku_by_flbh2(flbh2);
		List<T_MALL_ATTR> list_attr = search_Mapper.select_attr_value_by_flbh2(flbh2);

		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();

		hashMap.put("list_sku", list_sku);
		hashMap.put("list_attr", list_attr);

		return hashMap;
	}

	public List<OBJECT_T_MALL_SKU> get_object_sku_by_shp_id_sku_id(int sku_id,
			int shp_id) {
		
		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
		hashMap.put("sku_id", sku_id);
		hashMap.put("shp_id", shp_id);
		List<OBJECT_T_MALL_SKU> list_object_sku = search_Mapper.select_object_sku_by_shp_id_sku_id(hashMap);
		return list_object_sku;
	}

}
