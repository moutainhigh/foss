/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesBillingGroupEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.MapDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SalesBillingGroupDto;

/**
 * 营业部集中开单组关系 Service接口
 * @author foss-zhujunyong
 * @date Apr 26, 2013 3:54:57 PM
 * @version 1.0
 */
public interface ISalesBillingGroupService extends IService {
    
    /**
     * 
     * <p>新增,更新或作废营业部和集中开单组关系</p> 
     * 通过dto中BillingGroupList的值来确定,如果值为null表示作废
     * @author foss-zhujunyong
     * @date Apr 26, 2013 4:42:04 PM
     * @param dto
     * @return
     * @see
     */
    void mergeSalesBillingGroupDto(SalesBillingGroupDto dto);

    /**
     * 
     * <p>根据营业部部门编码作废营业部和集中开单组关系</p> 
     * @author foss-zhujunyong
     * @date Apr 26, 2013 4:47:03 PM
     * @param salesCode
     * @param modifyUser
     * @return
     * @see
     */
    int deleteSalesBillingGroupDtoBySalesCode(String salesCode, String modifyUser);

    /**
     * 
     * <p>根据集中开单组部门编码作废营业部和集中开单组关系</p> 
     * @author foss-zhujunyong
     * @date Apr 28, 2013 4:21:33 PM
     * @param billingGroupCode
     * @param modifyUser
     * @return
     * @see
     */
    int deleteSalesBillingGroupDtoByBillingGroupCode(String billingGroupCode, String modifyUser);    

    /**
     * 
     * <p>根据集中开单组部门编码查询相关的营业部列表</p> 
     * @author foss-zhujunyong
     * @date Apr 26, 2013 4:58:06 PM
     * @param billingGroupCode 集中开单组部门编码
     * @return
     * @see
     */
    List<MapDto> querySalesListByBillingGroupCode(String billingGroupCode);

    /**
     * 
     * <p>根据营业部部门编码查询相关的集中开单组列表</p> 
     * @author foss-zhujunyong
     * @date Apr 26, 2013 5:14:01 PM
     * @param salesCode 营业部部门编码
     * @return
     * @see
     */
    List<MapDto> queryBillingGroupListBySalesCode(String salesCode);
    
    /**
     * 
     * 根据营业部部门编码查询相关的集中开单组列表
     * @author foss-zhangxiaohui
     * @date May 16, 2013 11:19:01 AM
     * @param salesCode 营业部部门编码
     * @return
     * @see
     */
    List<SalesBillingGroupEntity> queryBillingGroupListBySaleDepCode(String salesCode);
    /**
     * 
     *<p>>根据集中开单组部门编码查询相关的营业部列表</p>
     *@author 130566-zengJunfan
     *@date   2013-7-24下午2:52:33
     * @param salesCode
     * @return
     */
    List<SalesBillingGroupEntity> queryBillingGroupListByBillingGroupCode(String billingGroupCode);
    /**
     *<p>批量插入集中开单组的关系</p>
     *@author 130566-zengJunfan
     *@date   2013-7-25下午6:03:46
     * @param entities
     * @return
     */
    List<SalesBillingGroupEntity> addSalesBillingGroupEntityMore(List<SalesBillingGroupEntity> entities);
    /**
     * 
     *<p>批量废除营业部集中开单组的关系</p>
     *@author 130566-zengJunfan
     *@date   2013-7-25下午6:18:47
     * @param entities
     * @return
     */
    List<SalesBillingGroupEntity> deleteSalesBillingGroupEntities(List<SalesBillingGroupEntity> entities);
    /**
     * 
     *<p>根据BillingGroupCode来作废</p>
     *@author 130566-zengJunfan
     *@date   2013-7-26上午9:31:06
     * @param billingGroupEntity
     * @return
     */
    SalesBillingGroupEntity deleteSalesBillingGroupByBillingGroupCode(SalesBillingGroupEntity billingGroupEntity);
    /**
     * 
     *<p>根据salesCode来作废</p>
     *@author 130566-zengJunfan
     *@date   2013-7-26上午9:32:08
     * @param billingGroupEntity
     * @return
     */
    SalesBillingGroupEntity deleteSalesBillingGroupBySalesCode(SalesBillingGroupEntity billingGroupEntity);
}
