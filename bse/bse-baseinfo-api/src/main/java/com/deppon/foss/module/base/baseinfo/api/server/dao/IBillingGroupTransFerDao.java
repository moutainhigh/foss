package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.BillingGroupTransFerEntity;

/**
 * 
 * 外场集中开单组关系 DAO接口
 * @author foss-lifanghong
 * @date 2013-06-02
 * @version 1.0
 */
public interface IBillingGroupTransFerDao {

    
    /**
     * 
     * <p>根据集中开单组部门编码查找外场列表</p> 
     * @author foss-lifanghong
     * @date2013-06-02
     * @param billingGroupCode
     * @return
     * @see
     */
    BillingGroupTransFerEntity queryBillingGroupTransferCenterCode(String billingGroupCode);
    
    /**
     * 
     * <p>新增集中开单组和外场对应关系</p> 
     * @author foss-zhujunyong
     * @date Jun 8, 2013 6:06:15 PM
     * @param entity
     * @return
     * @see
     */
    BillingGroupTransFerEntity addBillingGroupTransfer(BillingGroupTransFerEntity entity);
 
    /**
     * 
     * <p>作废集中开单组和外场对应关系</p> 
     * @author foss-zhujunyong
     * @date Jun 8, 2013 6:40:11 PM
     * @param entity
     * @see
     */
    BillingGroupTransFerEntity deleteBillingGroupTransferByBillingGroupCode(BillingGroupTransFerEntity entity);    
    
    /**
     * 
     * <p>更新集中开单组和外场对应关系</p> 
     * @author foss-zhujunyong
     * @date Jun 9, 2013 10:33:34 AM
     * @param parm
     * @return
     * @see
     */
    BillingGroupTransFerEntity updateBillingGroupTransfer(BillingGroupTransFerEntity parm);
    
    /**
     * 
     * <p>查询符合条件的集中开单组和外场关系表供下载</p> 
     * @author foss-zhujunyong
     * @date Jun 3, 2013 1:53:03 PM
     * @param entity
     * @return
     * @see
     */
    List<BillingGroupTransFerEntity> querybillingGroupTransferGroupListForDownload(BillingGroupTransFerEntity entity);
    /**
     * 
     * <p>根据集中开单组部门编码和时间查找外场</p> 
     * @author foss-lifanghong
     * @date2013-07-31
     * @param billingGroupCode
     * @return
     * @see
     */
	BillingGroupTransFerEntity queryBillingGroupTransferCenterCode(
			String billingGroupCode, Date createTime);   

}
