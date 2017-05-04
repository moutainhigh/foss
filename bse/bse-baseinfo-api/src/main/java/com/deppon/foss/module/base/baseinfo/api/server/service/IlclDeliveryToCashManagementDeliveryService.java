package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.LclDeliveryToCashManagementDeliveryEntity;

public interface IlclDeliveryToCashManagementDeliveryService {
	/**
	 * 
	 * <p>新增</p> 
	 * @author 273311 
	 * @date 2016-8-23 下午3:47:48
	 * @param entity
	 * @return
	 * @see
	 */
	LclDeliveryToCashManagementDeliveryEntity addLclDeliveryToCashManagementDelivery(
			LclDeliveryToCashManagementDeliveryEntity entity);

	/**
	 * 
	 * <p>删除</p> 
	 * @author 273311 
	 * @date 2016-8-23 下午3:48:28
	 * @param entity
	 * @return
	 * @see
	 */
	LclDeliveryToCashManagementDeliveryEntity deletelclDeliveryToCashManagementDelivery(
			LclDeliveryToCashManagementDeliveryEntity entity);

	/**
	 * 
	 * <p>更新</p> 
	 * @author 273311 
	 * @date 2016-8-23 下午3:48:56
	 * @param entity
	 * @return
	 * @see
	 */
	LclDeliveryToCashManagementDeliveryEntity updatelclDeliveryToCashManagementDelivery(
			LclDeliveryToCashManagementDeliveryEntity entity);

	/**
	 * 
	 * <p>批量删除</p> 
	 * @author 273311 
	 * @date 2016-8-23 下午3:49:23
	 * @param ids
	 * @return
	 * @see
	 */
	LclDeliveryToCashManagementDeliveryEntity deletelclDeliveryToCashManagementDeliveryMore(
			String[] ids);

	/**
	 * 
	 * <p>查询  分页</p> 
	 * @author 273311 
	 * @date 2016-8-23 下午3:49:57
	 * @param entity
	 * @param start
	 * @param limit
	 * @return
	 * @see
	 */
	List<LclDeliveryToCashManagementDeliveryEntity> queryLclDeliveryToCashManagementDeliveryExactByEntity(
			LclDeliveryToCashManagementDeliveryEntity entity, int start,
			int limit);

	/**
	 * 
	 * <p>查询条数 分页</p> 
	 * @author 273311 
	 * @date 2016-8-23 下午3:50:31
	 * @param entity
	 * @return
	 * @see
	 */
	long queryLclDeliveryToCashManagementDeliveryExactByEntityCount(
			LclDeliveryToCashManagementDeliveryEntity entity);

	/**
	 * 
	 * <p>通过部门编码精确查询</p> 
	 * @author 273311 
	 * @date 2016-8-23 下午3:51:09
	 * @param code
	 * @return
	 * @see
	 */
	List<LclDeliveryToCashManagementDeliveryEntity> queryLclDeliveryToCashManagementDeliveryByOrgCode(
			String code);

	/**
	 * 
	 * <p>通过部门编码和Id精确查询</p> 
	 * @author 273311 
	 * @date 2016-8-23 下午3:51:41
	 * @param code
	 * @param id
	 * @return
	 * @see
	 */
	LclDeliveryToCashManagementDeliveryEntity queryLclDeliveryToCashManagementDeliveryByIdCode(
			String code, String id);
}
