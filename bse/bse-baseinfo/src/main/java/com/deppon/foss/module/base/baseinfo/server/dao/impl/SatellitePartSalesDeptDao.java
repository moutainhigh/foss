package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ISatellitePartSalesDeptDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SatellitePartSalesDeptEntity;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


public class SatellitePartSalesDeptDao extends SqlSessionDaoSupport implements
		ISatellitePartSalesDeptDao {
	//命名空间
	private static final String NAMESPACE =ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".satellitePartSalesDept.";
	
	/**
	 *<P>添加卫星点对应实体<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-3-25下午4:06:56
	 * @param entity
	 * @return
	 */
	@Override
	public SatellitePartSalesDeptEntity addSatellitePartSales(
			SatellitePartSalesDeptEntity entity) {
		entity.setId(UUIDUtils.getUUID());
		Date now =new Date();
		entity.setCreateDate(now);
		entity.setModifyDate(new Date(NumberConstants.ENDTIME));
		entity.setActive(FossConstants.ACTIVE);
		entity.setVersionNo(now.getTime());
		entity.setModifyUser(entity.getCreateUser());
		int result =this.getSqlSession().insert(NAMESPACE+"addSatellitePartSales", entity);
		return result ==NumberConstants.ZERO?null:entity;
	}
	/**
	 *<P>根据id删除<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-3-25下午4:30:32
	 * @param entity
	 * @return
	 */
	@Override
	public int deleteSatellitePartSalesById(
			SatellitePartSalesDeptEntity entity) {
		Map<String,Object> map =new HashMap<String,Object>();
		Date now =new Date();
		entity.setActive(FossConstants.INACTIVE);
		entity.setModifyDate(now);
		entity.setVersionNo(now.getTime());
		map.put("entity", entity);
		map.put("conditionActive", FossConstants.ACTIVE);
		return this.getSqlSession().delete(NAMESPACE+"deleteSatellitePartSalesById", map);
	}
	/** 
	 *<P>根据条件分页查询<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-3-25下午4:48:37
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SatellitePartSalesDeptEntity> querySatellitePartSalesList(
			SatellitePartSalesDeptEntity entity,int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		entity.setActive(FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE+"querySatellitePartSalesList",entity,rowBounds);
	}
	/**
	 *<P>根据营业部编码查询<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-3-26上午11:11:29
	 * @param salesDeptCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SatellitePartSalesDeptEntity> querySatellitePartSalesListbySalesCode(
			String salesDeptCode) {
		if(StringUtils.isBlank(salesDeptCode)){
			return null;
		}
		SatellitePartSalesDeptEntity entity =new SatellitePartSalesDeptEntity();
		entity.setSalesDeptCode(salesDeptCode);
		entity.setActive(FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE+"querySatellitePartSalesListbySalesCode", entity);
	}
	/**
	 *<P>根据卫星点编码查询<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-3-26上午11:18:41
	 * @param satelliteDeptCode
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	@Override
	public SatellitePartSalesDeptEntity querySatellitePartSalesbySatelliteCode(
			String satelliteDeptCode) {
		if(StringUtils.isBlank(satelliteDeptCode)){
			return null;
		}
		SatellitePartSalesDeptEntity entity =new SatellitePartSalesDeptEntity();
		entity.setSatelliteDeptCode(satelliteDeptCode);
		entity.setActive(FossConstants.ACTIVE);
		List<SatellitePartSalesDeptEntity> resultList =getSqlSession().selectList(NAMESPACE+"querySatellitePartSalesbySatelliteCode",entity);
		if(CollectionUtils.isEmpty(resultList)){
			return null;
		}
		return resultList.get(0);
	}
	/**
	 *<P>查询总数<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-3-26上午11:22:06
	 * @param entity
	 * @return
	 */
	@Override
	public long querySatellitePartSalesCount(SatellitePartSalesDeptEntity entity) {
		entity.setActive(FossConstants.ACTIVE);
		return (Long) getSqlSession().selectOne(NAMESPACE+"querySatellitePartSalesCount",entity);
	}
	/**
	 *<P>根据卫星点部编码、营业部编码动态作废实体<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-4-1下午2:24:33
	 * @param deleteEntity
	 * @return
	 */
	@Override
	public SatellitePartSalesDeptEntity deleteSatellitePartSales(
			SatellitePartSalesDeptEntity deleteEntity) {
		Map<String,Object> map =new HashMap<String,Object>();
		Date now =new Date();
		deleteEntity.setActive(FossConstants.INACTIVE);
		deleteEntity.setModifyDate(now);
		deleteEntity.setVersionNo(now.getTime());
		map.put("entity", deleteEntity);
		map.put("conditionActive", FossConstants.ACTIVE);
		int result =this.getSqlSession().delete(NAMESPACE+"deleteSatellitePartSales", map);
		return result==NumberConstants.ZERO?null:deleteEntity;
	}

}
