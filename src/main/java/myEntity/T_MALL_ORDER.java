package myEntity;

import java.io.Serializable;
import java.util.Date;

public class T_MALL_ORDER implements Serializable{
	private int id;
	private String shp_mch;
	private int sku_shl;
	private String shhr;
	private double zje;
	private int jdh;
	private int yh_id;
	private int shp_id;
	private Date chjshj;
	private int sku_id;
	private Date yjsdshj;
	private String shp_tp;
	private int gwch_id;
	private int dzh_id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getShp_mch() {
		return shp_mch;
	}

	public void setShp_mch(String shp_mch) {
		this.shp_mch = shp_mch;
	}

	public int getSku_shl() {
		return sku_shl;
	}

	public void setSku_shl(int sku_shl) {
		this.sku_shl = sku_shl;
	}

	public String getShhr() {
		return shhr;
	}

	public void setShhr(String shhr) {
		this.shhr = shhr;
	}

	public double getZje() {
		return zje;
	}

	public void setZje(double zje) {
		this.zje = zje;
	}

	public int getJdh() {
		return jdh;
	}

	public void setJdh(int jdh) {
		this.jdh = jdh;
	}

	public int getYh_id() {
		return yh_id;
	}

	public void setYh_id(int yh_id) {
		this.yh_id = yh_id;
	}

	public int getShp_id() {
		return shp_id;
	}

	public void setShp_id(int shp_id) {
		this.shp_id = shp_id;
	}

	public Date getChjshj() {
		return chjshj;
	}

	public void setChjshj(Date chjshj) {
		this.chjshj = chjshj;
	}

	public int getSku_id() {
		return sku_id;
	}

	public void setSku_id(int sku_id) {
		this.sku_id = sku_id;
	}

	public Date getYjsdshj() {
		return yjsdshj;
	}

	public void setYjsdshj(Date yjsdshj) {
		this.yjsdshj = yjsdshj;
	}

	public String getShp_tp() {
		return shp_tp;
	}

	public void setShp_tp(String shp_tp) {
		this.shp_tp = shp_tp;
	}

	public int getGwch_id() {
		return gwch_id;
	}

	public void setGwch_id(int gwch_id) {
		this.gwch_id = gwch_id;
	}

	public int getDzh_id() {
		return dzh_id;
	}

	public void setDzh_id(int dzh_id) {
		this.dzh_id = dzh_id;
	}

}
