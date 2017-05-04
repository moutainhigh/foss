package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonInviteFossVehicleCostDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonInviteFossVehicleCostService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.InviteCommonExpressageEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.InviteFossVehicleCostEntity;


public class CommonInviteFossVehicleCostService implements ICommonInviteFossVehicleCostService  {
	/**
	 * 日志打印对象申明
	 */

	
	private ICommonInviteFossVehicleCostDao commonInviteFossVehicleCostDao;

	public void setCommonInviteFossVehicleCostDao(
			ICommonInviteFossVehicleCostDao commonInviteFossVehicleCostDao) {
		this.commonInviteFossVehicleCostDao = commonInviteFossVehicleCostDao;
	}

	/**
	 * 选择器查询出的总条数
	 */
	@Override
	public long queryCommonBusinessCount(InviteFossVehicleCostEntity entity) {
		
		return commonInviteFossVehicleCostDao.queryCommonBusinessCount(entity);
	}

	/**
	 * 选择器查询出维护的所有事业部信息
	 */
	@Override
	public List<InviteFossVehicleCostEntity> queryCommonInviteFossVehicleCostByBusiness(InviteFossVehicleCostEntity entity,int start, int limit){
		
		return commonInviteFossVehicleCostDao.queryCommonInviteFossVehicleCostByBusiness(entity, start, limit);
	}
	
	/**
	 * 选择器查询出的总条数
	 */
	@Override
	public long queryCommonRegionalCount(InviteFossVehicleCostEntity entity){
		
		return commonInviteFossVehicleCostDao.queryCommonRegionalCount(entity);
	}

	/**
	 * 选择器查询出维护的所有大区信息
	 */
	@Override
	public List<InviteFossVehicleCostEntity> queryCommonInviteFossVehicleCostByRegional(InviteFossVehicleCostEntity entity,int start, int limit){
		
		return commonInviteFossVehicleCostDao.queryCommonInviteFossVehicleCostByRegional(entity, start, limit);
	}
	
	/**
	 * 选择器查询出的总条数
	 */
	@Override
	public long queryCommonExpressageCount(InviteCommonExpressageEntity entity) {
		
		return commonInviteFossVehicleCostDao.queryCommonExpressageCount(entity);
	}

	/**
	 * 选择器查询出维护的所有快递点部和分部信息
	 */
	@Override
	public List<InviteCommonExpressageEntity> queryCommonInviteFossVehicleCostByExpressage(InviteCommonExpressageEntity entity,int start, int limit){
		
		return commonInviteFossVehicleCostDao.queryCommonInviteFossVehicleCostByExpressage(entity, start, limit);
	}
}
