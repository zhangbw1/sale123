package myEntity;

import java.util.Date;

public class T_MALL_SKU {

	private int id;
	private int shp_id;
	private int kc;
	private double jg;
	private Date chjshj;
	private String sku_mch;

	
	
	T_MALL_PRODUCT product;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public T_MALL_PRODUCT getProduct() {
		return product;
	}

	public void setProduct(T_MALL_PRODUCT product) {
		this.product = product;
	}

}
