/**  
 * Project Name:tfr-common  
 * File Name:ServerConfigDao.java  
 * Package Name:com.deppon.foss.module.transfer.common.server.dao.impl  
 * Date:2015年6月10日下午9:31:09  
 *  
 */
package com.deppon.foss.module.transfer.common.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.common.api.server.dao.IServerConfigDao;
import com.deppon.foss.module.transfer.common.api.shared.domain.ServerConfigEntity;
import com.deppon.foss.util.define.FossConstants;

/**  
 * ClassName: ServerConfigDao <br/>  
 * Function: 后台配置-数据访问层. <br/>  
 * date: 2015年6月10日 下午9:31:09 <br/>  
 *  
 * @author shiwei-045923 shiwei@outlook.com  
 * @version   
 * @since JDK 1.6  
 */
public class ServerConfigDao extends iBatis3DaoImpl implements IServerConfigDao {
	
	private static final String NAMESPACE = "foss.tfr.serverconfig.";

	/**  
	 * @see com.deppon.foss.module.transfer.common.api.server.dao.IServerConfigDao#queryServerConfig(java.lang.String, java.lang.String)  
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ServerConfigEntity queryServerConfig(String module, String code) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("module",module);
		map.put("code",code);
		List<ServerConfigEntity> list = this.getSqlSession().selectList(NAMESPACE + "queryServerConfig", map);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	/**  
	 * @see com.deppon.foss.module.transfer.common.api.server.dao.IServerConfigDao#updateServerConfig(java.lang.String, java.lang.String, java.lang.String)  
	 */
	@Override
	public int updateServerConfig(String module, String code, String newValue) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("module",module);
		map.put("code",code);
		map.put("newValue", newValue);
		this.getSqlSession().update(NAMESPACE + "updateServerConfig", map);
		if(newValue!=null){
			//gis服务开关的开启时间 更新日志的最后一条记录的修改时间
			if("Y".equals(newValue)){
				this.getSqlSession().update(NAMESPACE + "updateServerConfigLogLast", map);
			}
		}
		
		return FossConstants.SUCCESS;
	}

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
	@Override
	public int insertServerConfig(String module, String code, String description) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("module",module);
		map.put("code",code);
		map.put("description", description);
		try {
			this.getSqlSession().insert(NAMESPACE + "insertServerConfig", map);
		} catch (Exception e) {
			return FossConstants.FAILURE;
		}
		return FossConstants.SUCCESS;
	}

}
