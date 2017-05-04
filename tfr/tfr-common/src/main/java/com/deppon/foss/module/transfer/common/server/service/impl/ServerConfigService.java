/**  
 * Project Name:tfr-common  
 * File Name:ServerConfigService.java  
 * Package Name:com.deppon.foss.module.transfer.common.server.service.impl  
 * Date:2015年6月10日下午10:09:38  
 *  
 */
package com.deppon.foss.module.transfer.common.server.service.impl;

import com.deppon.foss.module.transfer.common.api.server.dao.IServerConfigDao;
import com.deppon.foss.module.transfer.common.api.server.service.IServerConfigService;
import com.deppon.foss.module.transfer.common.api.shared.domain.ServerConfigEntity;

/**  
 * ClassName: ServerConfigService <br/>  
 * Function: 后台配置--service层. <br/>  
 * date: 2015年6月10日 下午10:09:38 <br/>  
 *  
 * @author shiwei-045923 shiwei@outlook.com  
 * @version   
 * @since JDK 1.6  
 */
public class ServerConfigService implements IServerConfigService {
	
	private IServerConfigDao serverConfigDao;

	/**  
	 * serverConfigDao.  
	 *  
	 * @param   serverConfigDao    the serverConfigDao to set  
	 * @since   JDK 1.6  
	 */
	public void setServerConfigDao(IServerConfigDao serverConfigDao) {
		this.serverConfigDao = serverConfigDao;
	}

	/**  
	 * @see com.deppon.foss.module.transfer.common.api.server.service.IServerConfigService#queryServerConfig(java.lang.String, java.lang.String)  
	 */
	@Override
	public ServerConfigEntity queryServerConfig(String module, String code) {
		return serverConfigDao.queryServerConfig(module, code);
	}

	/**  
	 * @see com.deppon.foss.module.transfer.common.api.server.service.IServerConfigService#updateServerConfig(java.lang.String, java.lang.String, java.lang.String)  
	 */
	@Override
	public int updateServerConfig(String module, String code, String newValue) {
		return serverConfigDao.updateServerConfig(module, code, newValue);
	}

	
	/**
	* @description insertServerConfig
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.common.api.server.service.IServerConfigService#insertServerConfig(java.lang.String, java.lang.String, java.lang.String)
	* @author 14022-foss-songjie
	* @update 2015年7月19日 上午9:57:17
	* @version V1.0
	*/
	@Override
	public int insertServerConfig(String module, String code, String description) {
		return serverConfigDao.insertServerConfig(module, code, description);
	}

}
