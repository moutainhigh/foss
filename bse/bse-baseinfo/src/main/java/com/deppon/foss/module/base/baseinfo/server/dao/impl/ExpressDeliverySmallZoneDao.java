package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressDeliverySmallZoneDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliverySmallZoneEntity;
import com.deppon.foss.util.define.FossConstants;

public class ExpressDeliverySmallZoneDao extends SqlSessionDaoSupport implements
		IExpressDeliverySmallZoneDao {

	private static final String NAMESPACE = "foss.bse.bse-baseinfo.expressDeliverySmallZone.";

	/**
     * 新增快递收派小区 ------
     * @author 130134
     * @date 2014-5-4  
     * @param entity
     * @return 1：成功；-1：失败
     * @see
     */
    @Override
    public int addExpressDeliverySmallZone(ExpressDeliverySmallZoneEntity entity) {
	
	entity.setCreateDate(new Date());
	
	return this.getSqlSession().insert(NAMESPACE + "insert", entity);
    }
    
    /**
     * 根据code作废快递收派小区信息 ---------
     * @author 130134
     * @date 2014-5-4  
     * @param codes  
     * @return 1：成功；-1：失败
     * @see
     */
    @Override
    public int deleteExpressDeliverySmallZoneByCode(String[] codes,String modifyUser) {

	Map<String, Object> map = new HashMap<String, Object>();
	map.put("codes", codes);
	map.put("modifyUser", modifyUser);
	map.put("modifyDate", new Date());
	map.put("active", FossConstants.INACTIVE);
	map.put("active0", FossConstants.ACTIVE);
	
	return this.getSqlSession().update(NAMESPACE + "deleteByCode", map);
    }
    
    /**
     * 修改快递收派小区信息 
     * @author 130134
     * @date 2014-5-4  
     * @param entity 快递收派小区实体
     * @return 1：成功；-1：失败
     * @see
     */
    @Override
    public int updateExpressDeliverySmallZone(ExpressDeliverySmallZoneEntity entity) {

	return this.getSqlSession().update(NAMESPACE + "update", entity);
    }
    
    /**
     * 根据传入对象查询符合条件所有快递收派小区信息 -----
     * @author 130134
     * @date 2014-5-4  
     * @param entity 快递收派小区实体
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     * @see
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ExpressDeliverySmallZoneEntity> queryExpressDeliverySmallZones(
	    ExpressDeliverySmallZoneEntity entity, int limit, int start) {
	
	RowBounds rowBounds = new RowBounds(start, limit);
	
	return this.getSqlSession().selectList(NAMESPACE + "getAllInfos", entity,rowBounds);
    }

    /**根据小区的名称和编码查询小区信息
     * @author 130134
     * @date 2014-5-4  
     * @param entity 快递收派小区实体
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ExpressDeliverySmallZoneEntity> querySmallZonesByNameOrCode(
	    ExpressDeliverySmallZoneEntity entity) {
	return this.getSqlSession().selectList(NAMESPACE + "querySmallZonesByNameOrCode",
		entity);
    }
    
    /**
     * 统计总记录数 
     * @author 130134
     * @date 2014-5-4  
     * @param entity 快递收派小区实体
     * @return
     * @see
     */
    @Override
    public Long queryRecordCount(ExpressDeliverySmallZoneEntity entity) {
	
	return  (Long)this.getSqlSession().selectOne(NAMESPACE + "getCount", entity);
    }
    
    /**
     * 根据传入的管理部门Code、快递收派大区的区域类型（接货区、送货区）查询符合条件
     * 的快递收派小区 
     * @author 130134
     * @date 2014-5-4  
     * @param deptCode 管理部门Code
     * @param type 区域类型
     * @return 快递收派小区列表
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ExpressDeliverySmallZoneEntity> querySmallZonesByDeptCode(String deptCode,
	    String areaTyp,String bigZoneVirtualCode) {
	
	Map<String, String> map = new HashMap<String, String>();
	map.put("deptCode", deptCode);
	map.put("regionType",areaTyp);
	map.put("bigZoneVirtualCode", bigZoneVirtualCode);
	map.put("active", FossConstants.ACTIVE);
	
	return this.getSqlSession().selectList(NAMESPACE + "querySmallZonesByDeptCode", map);
    }
    
    /**
     * 根据区域编码查询快递收派小区详细信息 
     * @author 130134
     * @date 2014-5-4  
     * @param regionCode 区域编码
     * @return 
     */
    @Override
    public ExpressDeliverySmallZoneEntity querySmallZoneByCode(String regionCode) {
	
	Map<String, String> map = new HashMap<String, String>();
	map.put("regionCode", regionCode);
	map.put("active", FossConstants.ACTIVE);
	
	return (ExpressDeliverySmallZoneEntity)this.getSqlSession().selectOne(NAMESPACE + "querySmallZoneByCode", map);
    }
    
    /**
     * <p>根据虚拟编码查询快递收派小区详细信息 </p> 
     * @author 130134
     * @date 2014-5-4  
     * @param virtualCode 虚拟编码
     * @return 
     */
    @SuppressWarnings("unchecked")
	@Override
    public ExpressDeliverySmallZoneEntity querySmallZoneByVirtualCode(String virtualCode) {
//	Map<String, String> map = new HashMap<String, String>();
//	map.put("virtualCode", virtualCode);
//	map.put("active", FossConstants.ACTIVE);
    	ExpressDeliverySmallZoneEntity map=new ExpressDeliverySmallZoneEntity();
    	map.setActive("Y");
    	map.setVirtualCode(NAMESPACE);
	List<ExpressDeliverySmallZoneEntity> list=this.getSqlSession().selectList(NAMESPACE + "querySmallByVirtualCode", map);
	return list.get(0);
    }
    
    /**
     * <p>验证小区名称是否唯一</p> --------
     * @author 130134
     * @date 2014-5-4  
     * @param regionName 快递收派小区名称
     * @return 
     */
    @Override
    public ExpressDeliverySmallZoneEntity querySmallZoneByName(String regionName) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("regionName", regionName);
	map.put("active", FossConstants.ACTIVE);
	
	return (ExpressDeliverySmallZoneEntity)this.getSqlSession().selectOne(NAMESPACE + "querySmallZoneByName", map);
    }
    
    /**
     * <p>根据大区的区域编码模糊查询快递收派小区</p> 
     * @author 130134
     * @date 2014-5-4  
     * @param bigZoneRegionCode 大区的区域编码
     * @return 
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ExpressDeliverySmallZoneEntity> querySmallZonesByBigZoneRegionCode(
	    String bigZoneRegionCode) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("bigZoneRegionCode", bigZoneRegionCode);
	map.put("active", FossConstants.ACTIVE);
	
	return this.getSqlSession().selectList(NAMESPACE + "querySmallZonesByBigZoneRegionCode", map);
    }
    
    /**
     * <p>根据小区虚拟编码修改小区的区域编码、所属大区</p> -----
     * @author 130134
     * @date 2014-5-4  
     * @param entity
     * @return 
     */
    @Override
    public int updateSmallZoneByVirtualCode(ExpressDeliverySmallZoneEntity entity) {
	
	return this.getSqlSession().update(NAMESPACE + "updateSmallZoneByVirtualCode", entity);
    }
    
    /**
     * <p>
     * 根据所属大区编码修改小区编码、所属大区编码为空------
     * </p>
     * @author 130134
     * @date 2014-5-4  
     * @return 
     */
    @Override
    public int updateSmallZoneByBigCode(ExpressDeliverySmallZoneEntity entity) {
	
	return this.getSqlSession().update(NAMESPACE + "updateSmallZoneByBigCode", entity);
    }
    
    /**
     * <p>
     * 检查小区编码是否存在-------
     * </p>
     * @author 130134
     * @date 2014-5-4  
     * @return 
     */
	@Override
	public String queryRegionCodeByManagement(ExpressDeliverySmallZoneEntity entity) {
		 
		return  (String) this.getSqlSession().selectOne(NAMESPACE+"queryRegionCodeByManagement", entity);
	}

	/**
	 * 根据虚拟编码查看小区信息----
	 * @author 130134
	 * @date 2014-5-4  
	 */
	@Override
	public ExpressDeliverySmallZoneEntity queryExpressDeliverySmallZoneByVirtualCode(String virtualCode) {
		 
		Map<String, String> map = new HashMap<String, String>();
		map.put("virtualCode", virtualCode);
		map.put("active", FossConstants.ACTIVE);
		
		return (ExpressDeliverySmallZoneEntity) this.getSqlSession().selectOne(NAMESPACE+"ByVirtualCode", map);
	}
	/**
	 * 根据管理部门编码查看小区信息---------
	 * @author 130134
	 * @date 2014-5-4  
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressDeliverySmallZoneEntity> queryExpressDeliverySmallZoneByCode(String[] codes) {
		 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codes", codes);
		map.put("active", FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE+"queryExpressDeliverySmallZoneByCode", map);
	}

	/**
	 * 根据GIS的ID查看小区信息-----
	 * @author 130134
	 * @date 2014-5-4  
	 */
	@Override
	public ExpressDeliverySmallZoneEntity querySmallZoneByGisId(ExpressDeliverySmallZoneEntity entity) {
		 entity.setActive(FossConstants.ACTIVE);
		 return (ExpressDeliverySmallZoneEntity) this.getSqlSession().selectOne(NAMESPACE+"querySmallZoneByGisId", entity);
	}

	/**
	 * 根据部门编码和用户编码查询数据权限-----
	 * @author 130134
	 * @date 2014-5-4  
	 */
	@Override
	public Long queryDataPermissions(String empCode, String deptCode) {
		Map<String,String> map=new HashMap<String,String>(); 
		map.put("empCode", empCode);
		map.put("deptCode", deptCode);
		
		 return (Long) this.getSqlSession().selectOne(NAMESPACE+"queryDataPermissions", map);
	}
	

}