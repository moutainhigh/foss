package com.deppon.foss.module.base.baseinfo.api.server.dao;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusOrderSourceEntity;
/**
 * CRM行业、客户等级、订单来源信息Dao接口
 * @author dujunhui-187862
 * @date 2014-9-25 下午4:11:33
 * @since
 * @version
 */
public interface ICusOrderSourceDao {
	
	/**
	 * 新增CRM行业、客户等级、订单来源信息
	 * @author dujunhui-187862
	 * @date 2014-9-25 下午4:11:59
	 * @param entity 客户行业信息实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int addCusOrderSource(CusOrderSourceEntity entity);

	/**
	 * 修改CRM行业、客户等级、订单来源信息
	 * @author dujunhui-187862
	 * @date 2014-9-25 下午4:13:14
	 * @param entity 客户行业信息实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int updateCusOrderSource(CusOrderSourceEntity entity);
	
	/**
	 * 作废CRM行业、客户等级、订单来源信息
	 * @author dujunhui-187862
	 * @date 2014-11-14 上午9:35:24
	 * @param entity 客户行业信息实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	public int deleteCusOrderSource(CusOrderSourceEntity entity);

	/**
	 * <p>根据主编码查询信息实体</p>
	 * @author dujunhui-187862
	 * @date 2014-9-25 下午4:17:02
	 * @param cusOrderSourceCode
	 * @return
	 * @see
	 */
	CusOrderSourceEntity queryCusOrderSourceByCode(String importPattern, String cusOrderSourceCode);
}
