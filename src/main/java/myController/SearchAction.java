package myController;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import myEntity.MODEL_T_MALL_SKU_ATTR_VALUE;
import myEntity.OBJECT_T_MALL_SKU;
import myService.SearchService;

@Controller
public class SearchAction {
	@Autowired
	SearchService searchService;

	
	@RequestMapping(value = "/search_sku_attr_by_flbh2/{flbh2}/{flmch2}")
	public String search_sku_attr_by_flbh2(@PathVariable int flbh2, @PathVariable String flmch2, ModelMap map) {
		Map<Object, Object> hashMap = searchService.get_sku_attr_by_flbh2(flbh2);
		map.put("list_attr", hashMap.get("list_attr"));
		map.put("list_sku", hashMap.get("list_sku"));
		return "mall_search_class";
	}
	
	/**
	 * 按条件查找
	 * 模型驱动
	 * @param flbh2
	 * @param flmch2
	 * @param order_by
	 * @param list_attr_value
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/get_sku_by_flbh2_attr_value", method = RequestMethod.POST)
	public String get_sku_by_flbh2_attr_value(int flbh2,String flmch2,String order_by,
			MODEL_T_MALL_SKU_ATTR_VALUE list_attr_value,ModelMap map) {
		
//		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		List<OBJECT_T_MALL_SKU> list_object_sku = searchService.get_sku_by_flbh2_attr_value(flbh2, order_by,list_attr_value.getList_attr_value());
		map.put("list_object_sku", list_object_sku);
		return "mall_search_class_list_sku";
	}

	@RequestMapping(value="/get_object_sku_by_shp_id_sku_id/{id}/{shp_id}" )
	public String get_object_sku_by_shp_id_sku_id(@PathVariable("id")int sku_id,@PathVariable("shp_id")int shp_id,
			ModelMap map){
		
		List<OBJECT_T_MALL_SKU> sku = searchService.get_object_sku_by_shp_id_sku_id(sku_id,shp_id);
		List<OBJECT_T_MALL_SKU> list_sku = searchService.get_object_sku_by_shp_id_sku_id(0, shp_id);
		map.put("sku", sku.get(0));
		map.put("list_sku", list_sku);
		
		return "mall_sku_detail";
	}

}
