package com.deppon.foss.module.base.baseinfo.api.server.dao;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OneticketornotEntity;
/**
 * CRM是否一票多件信息Dao接口实现
 * @author 273311
 * @date 2015-12-16
 * @since
 * @version
 */
public interface IOneticketornotDao {
	/**
	 * 新增是否一票多件信息
	 * @author 273311
	 * @date 2015-12-16 
	 * @param entity 信息实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
		int addOneticketornot(OneticketornotEntity entity);
		/**
		 * 作废是否一票多件信息
		 * @author 273311
		 * @date 2015-12-16 
		 * @param entity 信息实体
		 * @return 1：成功；-1：失败
		 * @see
		 */
		int deleteOneticketornot(OneticketornotEntity entity);
		
		/**
		 * <p>根据客户编码查询信息实体</p>
		 * @author 273311
		 * @date 2015-12-16
		 * @param 
		 * @return
		 * @see
		 */
		OneticketornotEntity queryOneticketornotBycode(String code);
		/**
		 * <p>验证是否存在</p>
		 * @author 273311
		 * @date 2015-12-16
		 * @param 
		 * @return
		 * @see
		 */
		boolean queryOneticketornotBycode1(String code);
}
