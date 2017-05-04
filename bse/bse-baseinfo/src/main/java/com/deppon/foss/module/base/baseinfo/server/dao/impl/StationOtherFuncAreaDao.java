package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IStationOtherFuncAreaDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.StationOtherFuncAreaEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class StationOtherFuncAreaDao extends SqlSessionDaoSupport implements IStationOtherFuncAreaDao {

	private final static String NAMESPACE="foss.bse.bse-baseinfo.stationOtherFuncArea.";
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StationOtherFuncAreaEntity> queryAll(
			StationOtherFuncAreaEntity stationOtherFuncAreaEntity) {
		 
		return this.getSqlSession().selectList(NAMESPACE+"queryAll", stationOtherFuncAreaEntity);
	}

	@Override
	public StationOtherFuncAreaEntity queryByEntityParam(
			StationOtherFuncAreaEntity stationOtherFuncAreaEntity) {
		 
		return (StationOtherFuncAreaEntity) this.getSqlSession().selectOne(NAMESPACE+"queryAll", stationOtherFuncAreaEntity);
	}

	@Override
	public Integer updateStationOtherFuncArea(
			StationOtherFuncAreaEntity stationOtherFuncAreaEntity) {
	 
		return this.getSqlSession().update(NAMESPACE+"updateStationOtherFuncArea", stationOtherFuncAreaEntity);
	}

	/**
	 * 
	 */
	@Override
	public Integer addStationOtherFuncArea(
			StationOtherFuncAreaEntity stationOtherFuncAreaEntity) {
		stationOtherFuncAreaEntity.setId(UUIDUtils.getUUID());
		stationOtherFuncAreaEntity.setCreateDate(new Date());
		stationOtherFuncAreaEntity.setActive(FossConstants.ACTIVE);
		stationOtherFuncAreaEntity.setModifyDate(new Date(NumberConstants.ENDTIME));
		stationOtherFuncAreaEntity.setCreateUser(FossUserContext.getCurrentInfo().getEmpCode());
		stationOtherFuncAreaEntity.setModifyUser(null);
 		return this.getSqlSession().insert(NAMESPACE+"addStationOtherFuncArea", stationOtherFuncAreaEntity);
	}

	/**
	 * 作废
	 */
	@Override
	public Integer deleteStationOtherFuncArea(
			String[] codes) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codes", codes);
		map.put("modifyUser", FossUserContext.getCurrentInfo().getEmpCode());
		map.put("modifyDate", new Date());
		map.put("active", FossConstants.INACTIVE);
		map.put("active0", FossConstants.ACTIVE);
 		return this.getSqlSession().update(NAMESPACE+"deleteByCode", map);
	}
	
	/**
	 * 更具ID作废
	 */
	@Override 
	public Integer deleteStationOtherFuncAreaById(StationOtherFuncAreaEntity stationOtherFuncAreaEntity){
		stationOtherFuncAreaEntity.setActive(FossConstants.INACTIVE);
		stationOtherFuncAreaEntity.setModifyDate(new Date());
		stationOtherFuncAreaEntity.setModifyUser(FossUserContext.getCurrentInfo().getEmpCode());
 		return this.getSqlSession().update(NAMESPACE+"deleteById", stationOtherFuncAreaEntity);
	}

	/**
	 * 
	 */
	@Override
	public Long countAll(StationOtherFuncAreaEntity stationOtherFuncAreaEntity) {
		 
		return (Long) this.getSqlSession().selectOne(NAMESPACE+"countAll", stationOtherFuncAreaEntity);
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StationOtherFuncAreaEntity> queryAllByParam(
			StationOtherFuncAreaEntity stationOtherFuncAreaEntity,int offset,int limit) {
		RowBounds rowBounds=new RowBounds(offset, limit);
		
		return this.getSqlSession().selectList(NAMESPACE+"queryAllByParam", stationOtherFuncAreaEntity, rowBounds);
	}
	

}
