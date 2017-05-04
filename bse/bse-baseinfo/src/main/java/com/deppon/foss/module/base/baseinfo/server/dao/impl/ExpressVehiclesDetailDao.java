package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressVehiclesDetailDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressVehiclesDetailEntity;
import com.deppon.foss.util.define.FossConstants;

public class ExpressVehiclesDetailDao  extends SqlSessionDaoSupport implements IExpressVehiclesDetailDao {
	
    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".expressVehiclesDetail";

	@Override
	public int addExpressVehiclesDetail(ExpressVehiclesDetailEntity expressVehiclesDetailEntity) {

		return getSqlSession().insert(NAMESPACE + ".insertExpressVehiclesDetail", expressVehiclesDetailEntity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressVehiclesDetailEntity> queryExpressVehiclesByEmpCode(
			String empCode) {
		return (List<ExpressVehiclesDetailEntity>)getSqlSession().selectList(NAMESPACE + ".queryExpressVehiclesByEmpCode", empCode);
	}

	@Override
	public int deleteExpressVehiclesByEmpCode(String empCode) {
		return getSqlSession().delete(NAMESPACE + ".deleteExpressVehiclesByEmpCode", empCode);
	}

	/**
	 * 根据车辆id去作废行政区域
	 * 
	 * @author WangPeng
	 * @Date   2013-8-23 下午4:56:46
	 * @param  expressVehiclesDetailEntity
	 * @param  ids 车辆表id
	 * @return int
	 *
	 */
	@Override
	public int updateExpressVehiclesDetailBySelective(
			ExpressVehiclesDetailEntity expressVehiclesDetailEntity,List<String> ids) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("obj", expressVehiclesDetailEntity);
		map.put("ids", ids);
		map.put("active1",FossConstants.ACTIVE );
		return getSqlSession().update(NAMESPACE + ".updateExpressVehiclesDetailBySelective", map);
	}
}
