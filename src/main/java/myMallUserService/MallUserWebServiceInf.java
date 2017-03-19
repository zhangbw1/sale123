package myMallUserService;

import java.util.List;

import javax.jws.WebService;

import myEntity.T_MALL_ADDRESS;
import myEntity.T_MALL_USER;

@WebService
public interface MallUserWebServiceInf {

	public T_MALL_USER login(T_MALL_USER user);

	public List<T_MALL_ADDRESS> get_address_by_user(T_MALL_USER user);

	public int add_address_by_user(T_MALL_ADDRESS address);

	public int add_addresses_by_user(List<T_MALL_ADDRESS> list);

	public int add_user(T_MALL_USER user);

}
