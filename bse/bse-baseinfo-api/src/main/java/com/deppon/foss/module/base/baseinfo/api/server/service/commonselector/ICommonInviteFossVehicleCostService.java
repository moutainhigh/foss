package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.InviteCommonExpressageEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.InviteFossVehicleCostEntity;
/**
 * 事业部大区信息service接口
 * @author 332219
 *
 */
public interface ICommonInviteFossVehicleCostService extends IService {
    
    /**
     * 总条数事业部信息service接口
     * @author 332219
     *
     */
    long queryCommonBusinessCount(InviteFossVehicleCostEntity entity);
    
    /**
     * 分页查询事业部信息service接口
     * @author 332219
     *
     */
    List<InviteFossVehicleCostEntity> queryCommonInviteFossVehicleCostByBusiness(InviteFossVehicleCostEntity entity,int start, int limit);

    /**
     * 总条数大区信息service接口
     * @author 332219
     *
     */
    long queryCommonRegionalCount(InviteFossVehicleCostEntity entity);
    
    /**
     * 分页查询大区信息service接口
     * @author 332219
     *
     */
    List<InviteFossVehicleCostEntity> queryCommonInviteFossVehicleCostByRegional(InviteFossVehicleCostEntity entity,int start, int limit);
    /**
     * 总条数快递点部和分部信息service接口
     * @author 332219
     *
     */
    long queryCommonExpressageCount(InviteCommonExpressageEntity entity);
    
    /**
     * 分页查询快递点部和分部信息service接口
     * @author 332219
     *
     */
    List<InviteCommonExpressageEntity> queryCommonInviteFossVehicleCostByExpressage(InviteCommonExpressageEntity entity,int start, int limit);

}