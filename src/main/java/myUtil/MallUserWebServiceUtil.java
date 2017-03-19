package myUtil;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import myMallUserService.MallUserWebServiceInf;

public class MallUserWebServiceUtil {

	public static <T> T  getService(String url, Class<T> t) {
		
		JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
		jaxWsProxyFactoryBean.setAddress(url);
		T create = jaxWsProxyFactoryBean.create(t);
		
		return create;
	}

}
