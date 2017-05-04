/**   
* @Title: PricingBampInterceptor.java 
* @Package com.deppon.foss.module.pickup.pricing.server.interceptor 
* @author 045925-YANGBIN
* @date 2014年11月15日 下午3:58:10 
* @version V1.0   
*/
package com.deppon.foss.module.pickup.pricing.server.interceptor;

import org.apache.commons.lang.StringUtils;

import com.deppon.ar.bamp.client.interceptor.struts2.RequestErrorInterceptor;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;

/** 
 * @ClassName: PricingBampInterceptor 
 * @author 045925-YANGBIN
 * @date 2014年11月15日 下午3:58:10 
 *  
 */
public class PricingBampInterceptor extends RequestErrorInterceptor {

	/** 
	*/ 
	private static final long serialVersionUID = 7142537875550448272L;

	/** 
	 * @Title: getEmpCode 
	 * @author 045925-YANGBIN
	 * @description 返回登陆人工号
	 * @date 2014年11月15日 下午3:58:10 
	 * @param @return    设定文件 
	 * @throws 
	 */
	@Override
	public String getEmpCode() {
		CurrentInfo ci = FossUserContext.getCurrentInfo();
		if(null != ci
				&& StringUtils.isNotBlank(ci.getEmpCode())){
			return ci.getEmpCode();
		}else{
			return "-1";
		}
	}

}
