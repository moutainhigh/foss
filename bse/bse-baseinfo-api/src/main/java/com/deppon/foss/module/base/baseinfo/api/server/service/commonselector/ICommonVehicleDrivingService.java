package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleDrivingEntity;
/**
 * 长途车队信息service接口
 * @author 323136
 *
 */
public interface ICommonVehicleDrivingService extends IService {
    
    /**
     * 总条数长途车队信息service接口
     * @author 323136
     *
     */
    long queryCommonVehicleDrivingRecordByCondition(VehicleDrivingEntity entity);
    
    /**
     * 分页查询长途车队信息service接口
     * @author 323136
     *
     */
    List<VehicleDrivingEntity> queryCommonVehicleDrivingByCondition(VehicleDrivingEntity entity,int start, int limit);

}