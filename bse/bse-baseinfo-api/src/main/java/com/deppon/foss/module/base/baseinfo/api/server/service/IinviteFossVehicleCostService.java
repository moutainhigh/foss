package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.InviteFossVehicleCostEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.InviteFossVehicleCostQueryDto;
/**
 * 零担外请车单票费用service接口
 * @author 332219
 *
 */
public interface IinviteFossVehicleCostService extends IService {
	/**
	 * 
	 * 导入零担外请车单票费用信息
	 * @author 332219
	 * @date 2016-03-22 下午6:18:58
	 * @param InviteFossVehicleCostEntity
	 * @see
	 */
	void importInviteFossVehicleCost(List<InviteFossVehicleCostEntity> inviteFossVehicleCostEntity);
	
	
    /**
     * 修改零担外请车单票service接口
     * @author 323136
     *
     */
	void updateInviteFossVehicleCost(InviteFossVehicleCostEntity entity);
	
    
    /**
     * 事业部信息service接口
     * @author 332219
     *
     */
    long queryTotalByVehicleCost(InviteFossVehicleCostQueryDto queryDto);
	
    

    /**
     * 分页查询事业部信息service接口
     * @author 332219
     *
     */
    List<InviteFossVehicleCostEntity> queryVehicleDrivingByVehicleCost(InviteFossVehicleCostQueryDto queryDto,int start, int limit);
    
    /**
     * 根据大区查询部门费用信息service接口
     * @author 332219
     *
     */
    InviteFossVehicleCostEntity queryVehicleDrivingByRegionalCode(String regionalCode);
    
}