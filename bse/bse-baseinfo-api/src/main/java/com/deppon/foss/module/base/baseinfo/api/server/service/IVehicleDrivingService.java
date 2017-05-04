package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleDrivingEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleDrivingQueryDto;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
/**
 * 长途车队信息service接口
 * @author 323136
 *
 */
public interface IVehicleDrivingService extends IService {
	/**
	 * 新增长途车队信息service接口
	 * @author 323136
	 *
	 */
	void addVehicleDriving(VehicleDrivingEntity entity,CurrentInfo currentInfo);
	
	
    /**
     * 作废长途车队信息service接口
     * @author 323136
     *
     */
	void deleteVehicleDriving(VehicleDrivingQueryDto queryDto,CurrentInfo currentInfo);
	
    
    /**
     * 总条数长途车队信息service接口
     * @author 323136
     *
     */
    long queryTotalByCondition(VehicleDrivingQueryDto queryDto);
	
    

    /**
     * 分页查询长途车队信息service接口
     * @author 323136
     *
     */
    List<VehicleDrivingEntity> queryVehicleDrivingByCondition(VehicleDrivingQueryDto queryDto,int start, int limit);
    
    

    /**
     * 供中转调用
     * 根据上级车队查
     * @param longHaulFleetCode
     * @return
     */
    List<VehicleDrivingEntity> queryVehicleDrivingByLHF(String longHaulFleetCode);
    /**
     * 供中转调用
     * 根据下辖部门查询
     * @param departmentCode
     * @return
     */
    List<VehicleDrivingEntity> queryVehicleDrivingByDEP(String departmentCode);
}