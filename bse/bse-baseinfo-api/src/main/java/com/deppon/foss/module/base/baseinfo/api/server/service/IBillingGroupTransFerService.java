package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.Date;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BillingGroupTransFerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.MapDto;
/**
 * 外场集中开单组关系 Service接口
 * @author foss-lifanghong
 * @date 2013-06-02
 * @version 1.0
 */
public interface IBillingGroupTransFerService extends IService{
    
    /**
     * 
     * <p>根据集中开单组部门编码查询相关的外场列表</p> 
     * @author foss-lifanghong
     * @date 2013-06-02
     * @param billingGroupCode 集中开单组部门编码
     * @return
     * @see
     */
    BillingGroupTransFerEntity queryTransFerListByBillingGroupCode(String billingGroupCode);

   
    /**
     * 
     * <p>通过集中开单组组织编码查询对应的外场组织编码和名称</p> 
     * @author foss-zhujunyong
     * @date Jun 8, 2013 2:56:43 PM
     * @param billingGroupCode
     * @return
     * @see
     */
    MapDto queryTransferCenterByBillingGroupCode(String billingGroupCode);    
 
    /**
     * 
     * <p>合并集中开单组和外场关系</p>
     * 如果作废，只填营业部编码,外场编码不要填
     * 如果新增或更新，填营业部编码，名称和外场编码名称
     *  
     *  
     * @author foss-zhujunyong
     * @date Jun 9, 2013 10:36:55 AM
     * @param entity
     * @see
     */
    void mergeBillingGroupTransfer(BillingGroupTransFerEntity entity);

    /**
     * 
     * <p>通过集中开单组组织编码查询对应的外场组织编码和名称</p> 
     * @author foss-lifanghong
     * @date 2013-07-31, 2013 2:56:43 PM
     * @param billingGroupCode
     * @return
     * @see
     */
    BillingGroupTransFerEntity queryTransferCenterByBillingGroupCode(String billingGroupCode,Date createTime);    
    
}
