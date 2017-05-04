/**   
* @Title: TempBampInterceptor.java 
* @Package com.deppon.foss.module.transfer.scheduling.server.interceptor 
* @author shiwei-045923-shiwei@outlook.com
* @date 2014年7月25日 下午7:41:55 
* @version V1.0   
*/
package com.deppon.foss.module.transfer.scheduling.server.interceptor;

import com.deppon.ar.bamp.client.interceptor.struts2.RequestErrorInterceptor;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;

/** 
 * @ClassName: TempBampInterceptor 
 * @author shiwei-045923-shiwei@outlook.com
 * @date 2014年7月25日 下午7:41:55 
 *  
 */
public class TempBampInterceptor extends RequestErrorInterceptor {

	/** 
	*/ 
	private static final long serialVersionUID = 62097006780980135L;

	/** 
	 * @Title: getEmpCode 
	 * @Description: 返回登陆人工号
	 * @author shiwei-045923-shiwei@outlook.com
	 * @date 2014年7月25日 下午7:41:55 
	 * @param @return    设定文件 
	 * @throws 
	 */
	@Override
	public String getEmpCode() {
		CurrentInfo ci = FossUserContext.getCurrentInfo();
		return ci.getEmpCode();
	}

}
