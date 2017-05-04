package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressDeliveryBigZoneDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliveryBigZoneEntity;
import com.deppon.foss.util.define.FossConstants;

public class ExpressDeliveryBigZoneDao extends SqlSessionDaoSupport implements
		IExpressDeliveryBigZoneDao {

	 private static final String NAMESPACE = "foss.bse.bse-baseinfo.expressDeliveryBigZone.";

	 /**
		 * 新增快递收派大区
		 * @param entity
		 * @return
		 * @param @param entity
		 * @param @return
		 * @author 130134
		 * @date 2014-4-16 下午2:48:44
		 */
	    @Override
	    public int addExpressDeliveryBigZone(ExpressDeliveryBigZoneEntity entity) {

		return this.getSqlSession().insert(NAMESPACE + "insert", entity);
	    }

	    /**
	     * 根据code作废快递收派大区信息--------
	     * @param codes
	     * @param modifyUser
	     * @return
	     * @param @param codes
	     * @param @param modifyUser
	     * @param @return
	     * @author 130134
	     * @date 2014-4-16 下午2:48:50
	     */
	    @Override
	    public int deleteExpressDeliveryBigZoneByCode(String[] codes,
		    String modifyUser) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codes", codes);
		map.put("modifyUser", modifyUser);
		map.put("modifyDate", new Date());
		map.put("active", FossConstants.INACTIVE);
		map.put("active0", FossConstants.ACTIVE);

		return this.getSqlSession().update(NAMESPACE + "deleteByCode", map);
	    }

	    /**
	     * 修改快递收派大区信息
	     * @param entity
	     * @return
	     * @param @param entity
	     * @param @return
	     * @author 130134
	     * @date 2014-4-16 下午2:48:56
	     */
	    @Override
	    public int updateExpressDeliveryBigZone(ExpressDeliveryBigZoneEntity entity) {

		return this.getSqlSession().update(NAMESPACE + "update", entity);
	    }

	    /**
		 * 根据传入对象查询符合条件所有快递收派大区信息
		 * @param entity
		 * @param limit
		 * @param start
		 * @return
		 * @param @param entity
		 * @param @param limit
		 * @param @param start
		 * @param @return
		 * @author 130134
		 * @date 2014-4-16 下午2:49:03
		 */
	    @SuppressWarnings("unchecked")
	    @Override
	    public List<ExpressDeliveryBigZoneEntity> queryExpressDeliveryBigZones(
		    ExpressDeliveryBigZoneEntity entity, int limit, int start) {

		RowBounds rowBounds = new RowBounds(start, limit);

		return this.getSqlSession().selectList(NAMESPACE + "queryBigZonesByCondition",
			entity, rowBounds);
	    }


	    /**
	     * 根据条件查询接送货大区信息
	     * @param entity
	     * @return
	     * @param @param entity
	     * @param @return
	     * @author 130134
	     * @date 2014-4-16 下午2:49:47
	     */
	    @SuppressWarnings("unchecked")
	    @Override
	    public List<ExpressDeliveryBigZoneEntity> queryBigZonesByNameOrCode(ExpressDeliveryBigZoneEntity entity) {
		return this.getSqlSession().selectList(NAMESPACE + "queryBigZonesByNameOrCode",
			entity);
	    }

	    /**
	     * 统计总记录数
	     * @param entity
	     * @return
	     * @param @param entity
	     * @param @return
	     * @author 130134
	     * @date 2014-4-16 下午2:49:14
	     */
	    @Override
	    public Long queryRecordCount(ExpressDeliveryBigZoneEntity entity) {

		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryCount",
			entity);
	    }

	    /**
	     * 根据区域编码查询快递收派大区详细信息
	     * @param regionCode
	     * @return
	     * @param @param regionCode
	     * @param @return
	     * @author 130134
	     * @date 2014-4-16 下午2:49:21
	     */
	    @Override
	    public ExpressDeliveryBigZoneEntity queryBigzoneByCode(String regionCode) {

		Map<String, String> map = new HashMap<String, String>();
		map.put("regionCode", regionCode);
		map.put("active", FossConstants.ACTIVE);
		return (ExpressDeliveryBigZoneEntity) this.getSqlSession().selectOne(
			NAMESPACE + "queryBigzoneByCode", map);
	    }

	    /**
	     * 根据大区虚拟编码查询快递收派大区详细信息
	     * @param virtualCode
	     * @return
	     * @param @param virtualCode
	     * @param @return
	     * @author 130134
	     * @date 2014-4-16 下午2:49:27
	     */
	    @Override
	    public ExpressDeliveryBigZoneEntity queryBigzoneByVirtualCode(String virtualCode) {
		//参数为空判断
		if(StringUtils.isBlank(virtualCode)){
		    return null;
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("virtualCode", virtualCode);
		map.put("active", FossConstants.ACTIVE);
		
		return (ExpressDeliveryBigZoneEntity)this.getSqlSession().selectOne(NAMESPACE + "queryBigzoneByVirtualCode",map);
	    }
	     

	    /**
	     * 根据大区的管理部门编码查询快递收派大区信息
	     * @param manageMent
	     * @return
	     * @param @param manageMent
	     * @param @return
	     * @author 130134
	     * @date 2014-4-16 下午2:50:00
	     */
		@Override
		public ExpressDeliveryBigZoneEntity queryBigzoneByManageMent(String manageMent) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("management",manageMent);
			map.put("active", FossConstants.ACTIVE);
			
			return (ExpressDeliveryBigZoneEntity) this.getSqlSession().selectOne(NAMESPACE + "queryBigzoneByManageMent", map);
		}

		/**
	     * 根据快递收派大区的名称查新大区信息
	     * @param bigName
	     * @return
	     * @param @param bigName
	     * @param @return
	     * @author 130134
	     * @date 2014-4-16 下午2:50:29
	     */
		@Override
		public List<ExpressDeliveryBigZoneEntity> queryBigzoneByName(String regionName) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("regionName",regionName);
			map.put("active", FossConstants.ACTIVE);
			
			return  this.getSqlSession().selectList(NAMESPACE + "queryBigzoneByName", map);
		}

}