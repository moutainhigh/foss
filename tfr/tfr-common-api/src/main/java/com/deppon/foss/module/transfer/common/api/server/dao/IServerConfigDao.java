/**  
 * Project Name:tfr-common-api  
 * File Name:IServerConfigDao.java  
 * Package Name:com.deppon.foss.module.transfer.common.api.server.dao  
 * Date:2015年6月10日下午9:23:32  
 *  
 */
package com.deppon.foss.module.transfer.common.api.server.dao;

import com.deppon.foss.module.transfer.common.api.shared.domain.ServerConfigEntity;

/**  
 * ClassName: IServerConfigDao <br/>  
 * Function: 后台配置-数据访问层. <br/>  
 * date: 2015年6月10日 下午9:23:32 <br/>  
 *  
 * @author shiwei-045923 shiwei@outlook.com  
 * @version   
 * @since JDK 1.6  
 */
public interface IServerConfigDao {
	
	/**
	 * queryServerConfig:根据模块和编码来查找配置项的值. <br/>  
	 *  
	 *  Date:2015年6月10日下午9:26:15  
	 * @author shiwei-045923 shiwei@outlook.com  
	 * @param module
	 * @param code
	 * @return  
	 * @since JDK 1.6
	 */
	ServerConfigEntity queryServerConfig(String module,String code);
	
	/**
	 * 
	 * updateServerConfig:根据模块和编码来更新配置项的值. <br/>  
	 *  
	 *  Date:2015年6月10日下午9:27:52  
	 * @author shiwei-045923 shiwei@outlook.com  
	 * @param module
	 * @param code
	 * @param newValue
	 * @return  
	 * @since JDK 1.6
	 */
	int updateServerConfig(String module,String code,String newValue);
	
	
	/**
	* @description 根据模块和编码来更新配置项的值
	* @param module
	* @param code
	* @param description
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年7月19日 上午9:48:47
	*/
	int insertServerConfig(String module,String code,String description);

}
