/**   
* @Title: TempBampInterceptor.java 
* @Package com.deppon.foss.module.transfer.platform.server.interceptor 
* @author shiwei-045923-shiwei@outlook.com
* @date 2014年7月26日 下午3:58:10 
* @version V1.0   
*/
package com.deppon.foss.module.transfer.platform.server.interceptor;

import com.deppon.ar.bamp.client.interceptor.struts2.RequestErrorInterceptor;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;

/** 
 * @ClassName: TempBampInterceptor 
 * @author shiwei-045923-shiwei@outlook.com
 * @date 2014年7月26日 下午3:58:10 
 *  
 */
public class TempBampInterceptor extends RequestErrorInterceptor {

	/** 
	*/ 
	private static final long serialVersionUID = 7142537875550448272L;

	/** 
	 * @Title: getEmpCode 
	 * @author shiwei-045923-shiwei@outlook.com
	 * @description 返回登陆人工号
	 * @date 2014年7月26日 下午3:58:10 
	 * @param @return    设定文件 
	 * @throws 
	 */
	@Override
	public String getEmpCode() {
		CurrentInfo ci = FossUserContext.getCurrentInfo();
		return ci.getEmpCode();
	}

}
