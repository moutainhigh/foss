package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressVehiclesDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressVehiclesEntity;
import com.deppon.foss.util.define.FossConstants;

public class ExpressVehiclesDao extends SqlSessionDaoSupport implements IExpressVehiclesDao{
	
    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".expressVehicles";

	@Override
    public int addExpressVehicles(ExpressVehiclesEntity expressVehiclesEntity) {
		expressVehiclesEntity.setCreateDate(new Date());
		expressVehiclesEntity.setModifyUser(expressVehiclesEntity.getCreateUser());
		expressVehiclesEntity.setModifyDate(expressVehiclesEntity.getCreateDate());
		return getSqlSession().insert(NAMESPACE + ".addExpressVehicles", expressVehiclesEntity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressVehiclesEntity> queryInfos(
			ExpressVehiclesEntity expressVehiclesEntity, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		expressVehiclesEntity.setActive(FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE + ".queryAllInfos",
				expressVehiclesEntity, rowBounds);
	}

	@Override
	public Long queryRecordCount(ExpressVehiclesEntity expressVehiclesEntity) {
		expressVehiclesEntity.setActive(FossConstants.ACTIVE);
		return (Long) this.getSqlSession().selectOne(NAMESPACE + ".queryCount",
				expressVehiclesEntity);
	}

	@Override
	public ExpressVehiclesEntity queryExpressVehiclesById(String id) {
		return  (ExpressVehiclesEntity) this.getSqlSession().selectOne(NAMESPACE + ".queryExpressVehiclesById",
				id);
	}

	@Override
	public int updateExpressVehicles(ExpressVehiclesEntity expressVehiclesEntity) {
		return getSqlSession().update(NAMESPACE + ".updateExpressVehicles", expressVehiclesEntity);
	}
	@Override
	public Long queryCountByExpressVehicles(ExpressVehiclesEntity expressVehiclesEntity) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + ".queryCountByExpressVehicles",
				expressVehiclesEntity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressVehiclesEntity> getExpressVehiclesEntitysByEmpcode(
			String empCode) {
		return this.getSqlSession().selectList(NAMESPACE + ".queryExpressVehiclesEntitysByEmpcode",
				empCode);
	}

	/**
	   * 统计按条件查询返回的记录数
	   * 
	   * @author  WangPeng
	   * @Date    2013-8-23 下午1:31:17
	   * @param   enity
	   * @return  Long
	   * 
	   *
	   */
	  public Long recordCountByQueryParams(ExpressVehiclesEntity enity) {
		  enity.setActive(FossConstants.ACTIVE);
		return (Long) this.getSqlSession().selectOne(NAMESPACE + ".recordCountByQueryParams", enity);
	}
	  
	  /**
	   * 作废车辆信息
	   * 
	   * @author  WangPeng
	   * @Date    2013-8-23 下午4:52:19
	   * @param   entity
	   * @param   ids 车辆信息id集合
	   * @return  int
	   * 
	   *
	   */
	  public int  deleteExpressVehicles(ExpressVehiclesEntity entity,List<String> ids) {
		    Map<String,Object> map = new HashMap<String,Object>();
			map.put("obj", entity);
			map.put("ids", ids);
			map.put("active1",FossConstants.ACTIVE );
		return this.getSqlSession().update(NAMESPACE + ".deleteExpressVehicles", map);
	}
	  
	  /**
	   * 根据条件查询快递车辆信息
	   * 
	   * @author  FOSS-ShenWeiHua-132599
	   * @Date    2014-9-24 下午1:31:17
	   * @param   enity
	   * @return  entity
	   *
	   */  
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressVehiclesEntity> queryExpressVehiclesByEntity(
			ExpressVehiclesEntity entity) {
		
		 return this.getSqlSession().selectList(NAMESPACE + ".queryExpressVehiclesByEntity",
				entity);
	}
}