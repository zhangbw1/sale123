package myEntity;

import java.util.Date;
import java.util.List;

public class OBJECT_T_MALL_SKU {

	private int sku_id;
	private int shp_id;
	private int kc;
	private double jg;
	private Date chjshj;
	private String sku_mch;
	private String shp_tp_url;
	private String shp_msh;
	private T_MALL_PRODUCT product;
	private T_MALL_TRADE_MARK tm;
	private List<T_MALL_SKU_ATTR_VALUE> list_attr_value;
	private List<T_MALL_PRODUCT_IMAGE> list_img;
	private List<OBJECT_ATTR_VALUE_NAME> list_o_a_v_n;

	public List<T_MALL_PRODUCT_IMAGE> getList_img() {
		return list_img;
	}

	public void setList_img(List<T_MALL_PRODUCT_IMAGE> list_img) {
		this.list_img = list_img;
	}

	public String getShp_msh() {
		return shp_msh;
	}

	public void setShp_msh(String shp_msh) {
		this.shp_msh = shp_msh;
	}

	public List<OBJECT_ATTR_VALUE_NAME> getList_o_a_v_n() {
		return list_o_a_v_n;
	}

	public void setList_o_a_v_n(List<OBJECT_ATTR_VALUE_NAME> list_o_a_v_n) {
		this.list_o_a_v_n = list_o_a_v_n;
	}

	public int getSku_id() {
		return sku_id;
	}

	public void setSku_id(int sku_id) {
		this.sku_id = sku_id;
	}

	public int getShp_id() {
		return shp_id;
	}

	public void setShp_id(int shp_id) {
		this.shp_id = shp_id;
	}

	public int getKc() {
		return kc;
	}

	public void setKc(int kc) {
		this.kc = kc;
	}

	public double getJg() {
		return jg;
	}

	public void setJg(double jg) {
		this.jg = jg;
	}

	public Date getChjshj() {
		return chjshj;
	}

	public void setChjshj(Date chjshj) {
		this.chjshj = chjshj;
	}

	public String getSku_mch() {
		return sku_mch;
	}

	public void setSku_mch(String sku_mch) {
		this.sku_mch = sku_mch;
	}

	public String getShp_tp_url() {
		return shp_tp_url;
	}

	public void setShp_tp_url(String shp_tp_url) {
		this.shp_tp_url = shp_tp_url;
	}

	public List<T_MALL_SKU_ATTR_VALUE> getList_attr_value() {
		return list_attr_value;
	}

	public void setList_attr_value(List<T_MALL_SKU_ATTR_VALUE> list_attr_value) {
		this.list_attr_value = list_attr_value;
	}

	public T_MALL_PRODUCT getProduct() {
		return product;
	}

	public void setProduct(T_MALL_PRODUCT product) {
		this.product = product;
	}

	public T_MALL_TRADE_MARK getTm() {
		return tm;
	}

	public void setTm(T_MALL_TRADE_MARK tm) {
		this.tm = tm;
	}

}
