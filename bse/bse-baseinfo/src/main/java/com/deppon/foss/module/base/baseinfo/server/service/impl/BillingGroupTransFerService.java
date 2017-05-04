package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IBillingGroupTransFerDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBillingGroupTransFerService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BillingGroupTransFerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.MapDto;
import com.google.inject.Inject;
/**
 * 营业部和集中开单组关系Service类
 * 一个集中开单组只能对应一个外场
 * 但一个外场可以对应多个集中开单组
 * 
 * @author foss-lifanghong
 * @date 2013-06-02
 * @version 1.0
 */
public class BillingGroupTransFerService implements IBillingGroupTransFerService {
	 /**
     * 日志类
     */
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(BillingGroupTransFerService.class);

    @Inject
    private IBillingGroupTransFerDao billingGroupTransFerDao;   

    public void setBillingGroupTransFerDao(IBillingGroupTransFerDao billingGroupTransFerDao) {
	this.billingGroupTransFerDao = billingGroupTransFerDao;
    }

    /**
     * 
     * <p>根据集中开单组部门编码查找外场列表</p> 
     * @author foss-lifanghong
     * @date2013-06-02
     * @param billingGroupCode
     * @return
     * @see
     */
    @Override
    public BillingGroupTransFerEntity queryTransFerListByBillingGroupCode(String billingGroupCode) {
	BillingGroupTransFerEntity entity = new BillingGroupTransFerEntity();
	if (StringUtils.isBlank(billingGroupCode)) {
	    return entity;
	}
	entity = billingGroupTransFerDao.queryBillingGroupTransferCenterCode(billingGroupCode);
	return entity;
    }
    
    /**
     * 
     * <p>通过集中开单组组织编码和时间查询对应的外场组织编码和名称</p> 
     * @author foss-lifanghong
     * @date 2013-07-31, 2013 2:56:43 PM
     * @param billingGroupCode
     * @return
     * @see
     */
    @Override
    public BillingGroupTransFerEntity queryTransferCenterByBillingGroupCode(String billingGroupCode,Date createTime) {
	// 检查参数
	if (StringUtils.isBlank(billingGroupCode)) {
	    return null;
	}
	// 查出集中开单组对应的唯一实体
	BillingGroupTransFerEntity entity = billingGroupTransFerDao.queryBillingGroupTransferCenterCode(billingGroupCode,createTime);
	if (entity == null) {
	    return null;
	}
	return entity;
    }
    
    /**
     * 
     * <p>通过集中开单组组织编码查询对应的外场组织编码和名称</p> 
     * @author foss-zhujunyong
     * @date Jun 8, 2013 2:56:43 PM
     * @param billingGroupCode
     * @return
     * @see
     */
    @Override
    public MapDto queryTransferCenterByBillingGroupCode(String billingGroupCode) {
	// 检查参数
	if (StringUtils.isBlank(billingGroupCode)) {
	    return null;
	}
	// 查出集中开单组对应的唯一实体
	BillingGroupTransFerEntity entity = billingGroupTransFerDao.queryBillingGroupTransferCenterCode(billingGroupCode);
	if (entity == null) {
	    return null;
	}
	MapDto result = new MapDto();
	result.setCode(entity.getTransFerCode());
	result.setName(entity.getTransFerName());
	return result;
    }
    
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
    @Override
    public void mergeBillingGroupTransfer(BillingGroupTransFerEntity entity){
	// 检查参数
	if (entity == null || StringUtils.isBlank(entity.getBillingGroupCode())) {
	    return;
	}
	// 如果该营业部没有匹配任何外场，说明要作废关联关系
	if (StringUtils.isBlank(entity.getTransFerCode())) {
	    billingGroupTransFerDao.deleteBillingGroupTransferByBillingGroupCode(entity);
	    return;
	}
	
	// 如果数据库中没有已关联的信息，说明要新增关联关系
	BillingGroupTransFerEntity db = billingGroupTransFerDao.queryBillingGroupTransferCenterCode(entity.getBillingGroupCode());
	if (db == null) {
	    billingGroupTransFerDao.addBillingGroupTransfer(entity);
	    return;
	}

	// 只要有一个属性不一致，就需要更新
	if (!StringUtils.equals(entity.getBillingGroupName(), db.getBillingGroupName()) || !StringUtils.equals(entity.getTransFerCode(), db.getTransFerCode())
		|| !StringUtils.equals(entity.getTransFerName(), db.getTransFerName())) {
	    billingGroupTransFerDao.updateBillingGroupTransfer(entity);
	}
	// 如果所有属性都一致，不用做任何动作
    }
    
    

}
