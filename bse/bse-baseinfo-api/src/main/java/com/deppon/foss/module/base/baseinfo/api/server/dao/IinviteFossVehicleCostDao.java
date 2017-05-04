package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.InviteFossVehicleCostEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.InviteFossVehicleCostQueryDto;

public interface IinviteFossVehicleCostDao {

	/**
	 * @author 332219
	 * 新增零担外请车单票费用信息
	 */
	public int insertInviteFossVehicleCost(InviteFossVehicleCostEntity inviteFossVehicleCostEntity);
	
	/**
	 * @author 332219
	 * 修改零担外请车单票费用信息
	 */
	public void updateInviteFossVehicleCost(InviteFossVehicleCostEntity inviteFossVehicleCostEntity);
	
	/**
	 * @author 332219
	 * 查询外请车单票费用总条数
	 */
	public long queryTotalByVehicleCost(InviteFossVehicleCostQueryDto queryDto);
	
	/**
	 * @author 332219
	 * 根据事业部和大区查询是否有数据
	 */
	public String queryTotalByBusinessAndRegional(InviteFossVehicleCostEntity entity);

	/**
	 * @author 332219
	 * 分页查询外请车单票费用
	 */
	public List<InviteFossVehicleCostEntity> queryVehicleDrivingByVehicleCost(InviteFossVehicleCostQueryDto queryDto,int start, int limit);

	/**
	 * @author 332219
	 * 根据大区查询部门费用信息
	 */
	public InviteFossVehicleCostEntity queryVehicleDrivingByRegionalCode(String regionalCode);

}