/**  
 * Project Name:tfr-common-api  
 * File Name:IServerConfigService.java  
 * Package Name:com.deppon.foss.module.transfer.common.api.server.service  
 * Date:2015年6月10日下午9:24:43  
 *  
 */
package com.deppon.foss.module.transfer.common.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.common.api.shared.domain.ServerConfigEntity;

/**  
 * ClassName: IServerConfigService <br/>  
 * Function: 后台配置service层. <br/>  
 * date: 2015年6月10日 下午9:24:43 <br/>  
 *  
 * @author shiwei-045923 shiwei@outlook.com  
 * @version   
 * @since JDK 1.6  
 */
public interface IServerConfigService extends IService {
	
	/**
	 * 
	 * queryServerConfig:根据模块和编码来查找配置项的值. <br/>  
	 *  
	 *  Date:2015年6月10日下午9:28:39  
	 * @author shiwei-045923 shiwei@outlook.com  
	 * @param module
	 * @param code
	 * @return  
	 * @since JDK 1.6
	 */
	ServerConfigEntity queryServerConfig(String module,String code);
	
	/**
	 * updateServerConfig:根据模块和编码来更新配置项的值. <br/>  
	 *  
	 *  Date:2015年6月10日下午9:28:59  
	 * @author shiwei-045923 shiwei@outlook.com  
	 * @param module
	 * @param code
	 * @param newValue
	 * @return  
	 * @since JDK 1.6
	 */
	int updateServerConfig(String module,String code,String newValue);
	
	
	
	/**
	* @description 根据模块和编码来更新配置项的值. 
	* @param module
	* @param code
	* @param description
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年7月19日 上午9:55:03
	*/
	public int insertServerConfig(String module, String code, String description);

}
