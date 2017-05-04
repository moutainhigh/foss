package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.LclDeliveryToCashManagementDeliveryEntity;

public interface IlclDeliveryToCashManagementDeliveryDao {
	/**
	 * 
	 * <p>新增</p> 
	 * @author 273311 
	 * @date 2016-8-23 下午4:00:20
	 * @param entity
	 * @return
	 * @see
	 */
	LclDeliveryToCashManagementDeliveryEntity addLclDeliveryToCashManagementDeliveryEntity(
			LclDeliveryToCashManagementDeliveryEntity entity);
    /**
     * 
     * <p>删除</p> 
     * @author 273311 
     * @date 2016-8-23 下午4:00:37
     * @param entity
     * @return
     * @see
     */
	LclDeliveryToCashManagementDeliveryEntity deletelclDeliveryToCashManagementDelivery(
			LclDeliveryToCashManagementDeliveryEntity entity);
    /**
     * 
     * <p>批量删除</p> 
     * @author 273311 
     * @date 2016-8-23 下午4:01:04
     * @param ids
     * @return
     * @see
     */
	LclDeliveryToCashManagementDeliveryEntity deletelclDeliveryToCashManagementDeliveryMore(
			String[] ids);

	/**
	 * 
	 * <p>更新</p> 
	 * @author 273311 
	 * @date 2016-8-23 下午4:01:28
	 * @param entity
	 * @return
	 * @see
	 */
	LclDeliveryToCashManagementDeliveryEntity updatelclDeliveryToCashManagementDelivery(
			LclDeliveryToCashManagementDeliveryEntity entity);
    /**
     * 
     * <p>查询，分页</p> 
     * @author 273311 
     * @date 2016-8-23 下午4:01:50
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
	 * <p查询 条数</p> 
	 * @author 273311 
	 * @date 2016-8-23 下午4:02:17
	 * @param entity
	 * @return
	 * @see
	 */
	long queryLclDeliveryToCashManagementDeliveryExactByEntityCount(
			LclDeliveryToCashManagementDeliveryEntity entity);

	/**
	 * 
	 * <p> 通过部门编码查询</p> 
	 * @author 273311 
	 * @date 2016-8-23 下午4:02:38
	 * @param code
	 * @return
	 * @see
	 */
	List<LclDeliveryToCashManagementDeliveryEntity> queryLclDeliveryToCashManagementDeliveryByOrgCode(
			String code);

	/**
	 * 
	 * <p>通过部门编码和ID查询</p> 
	 * @author 273311 
	 * @date 2016-8-23 下午4:02:58
	 * @param code
	 * @param id
	 * @return
	 * @see
	 */
	LclDeliveryToCashManagementDeliveryEntity queryLclDeliveryToCashManagementDeliveryByIdCode(
			String code, String id);
}
