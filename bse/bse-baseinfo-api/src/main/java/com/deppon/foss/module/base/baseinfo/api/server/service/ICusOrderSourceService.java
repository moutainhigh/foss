package com.deppon.foss.module.base.baseinfo.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusOrderSourceEntity;
/**
 * CRM行业、客户等级、订单来源信息Service接口
 * @author dujunhui-187862
 * @date 2014-9-25 下午3:03:25
 * @since
 * @version
 */
public interface ICusOrderSourceService extends IService{
	/**
	 * 新增CRM行业、客户等级、订单来源信息
	 * @author dujunhui-187862
	 * @date 2014-9-25 下午3:04:27
	 * @param entity 客户行业信息实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int addCusOrderSource(CusOrderSourceEntity entity);

	/**
	 * 修改CRM行业、客户等级、订单来源信息
	 * @author dujunhui-187862
	 * @date 2014-9-25 下午3:06:18
	 * @param entity 客户行业信息实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int updateCusOrderSource(CusOrderSourceEntity entity);
	
	/**
	 * 作废CRM行业、客户等级、订单来源信息
	 * @author dujunhui-187862
	 * @date 2014-11-14 上 午9:31:53
	 * @param entity 客户行业信息实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int deleteCusOrderSource(CusOrderSourceEntity entity);

	/**
	 * <p>根据主编码cusOrderSourceCode查询信息实体</p>
	 * @author dujunhui-187862
	 * @date 2014-9-25 下午3:11:37
	 * @param cusOrderSourceCode
	 * @return
	 * @see
	 */
	CusOrderSourceEntity queryCusOrderSourceByCode(String importPattern, String cusOrderSourceCode);
	
}
