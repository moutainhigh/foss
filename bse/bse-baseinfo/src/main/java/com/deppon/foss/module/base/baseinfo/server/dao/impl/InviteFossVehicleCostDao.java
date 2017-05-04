package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IinviteFossVehicleCostDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.InviteFossVehicleCostEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.InviteFossVehicleCostQueryDto;

public class InviteFossVehicleCostDao extends SqlSessionDaoSupport implements IinviteFossVehicleCostDao{

	private static final String NAMESPACE = "foss.bse.bse-baseinfo.invitefossvehiclecostentity.";
	/**
	 * @author 332219
	 * 新增零担外请车单票费用信息
	 */
	@Override
	public int insertInviteFossVehicleCost(InviteFossVehicleCostEntity entity) {
		if(entity == null){
			throw new BusinessException("新增零担外请车单票费用参数异常");
		}
		return getSqlSession().insert(NAMESPACE+"insertInviteFossVehicleCost", entity);
	}
	
	/**
	 * @author 332219
	 * 修改零担外请车单票费用
	 */
	@Override
	public void updateInviteFossVehicleCost(InviteFossVehicleCostEntity entity) {
		getSqlSession().update(NAMESPACE+"updateInviteFossVehicleCost", entity);
	}

	/**
	 * @author 332219
	 * 根据事业部和大区查询零担外请车单票费用
	 */
	@Override
	public String queryTotalByBusinessAndRegional(InviteFossVehicleCostEntity entity) {
		return  (String) this.getSqlSession().selectOne(NAMESPACE+"queryTotalByBusinessAndRegional", entity);
	}
	
	/**
	 * @author 332219
	 * 查询总条数
	 */
	@Override
	public long queryTotalByVehicleCost(InviteFossVehicleCostQueryDto queryDto) {
		
		return (Long) getSqlSession().selectOne(NAMESPACE+"queryTotalByVehicleCost", queryDto);
	}
	
	/**
	 * @author 332219
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InviteFossVehicleCostEntity> queryVehicleDrivingByVehicleCost(InviteFossVehicleCostQueryDto queryDto,int start, int limit) {
		
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(NAMESPACE+"queryVehicleDrivingByVehicleCost",queryDto,rowBounds);
	}
	
	/**
	 * @author 332219
	 * 根据大区查询部门费用信息
	 */
	@Override
	public InviteFossVehicleCostEntity queryVehicleDrivingByRegionalCode(String regionalCode) {
		return (InviteFossVehicleCostEntity) getSqlSession().selectOne(NAMESPACE+"queryVehicleDrivingByRegionalCode", regionalCode);
	}

}