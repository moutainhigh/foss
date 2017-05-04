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
package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesBillingGroupDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesBillingGroupEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 营业部和集中开单组关系Dao
 * @author foss-zhujunyong
 * @date Apr 26, 2013 3:59:13 PM
 * @version 1.0
 */
public class SalesBillingGroupDao extends SqlSessionDaoSupport implements ISalesBillingGroupDao {

    /**
     * 日志类
     */
    @SuppressWarnings("unused")
    private static final Logger log = Logger.getLogger(SalesBillingGroupDao.class);

    /**
     * 
     * mybatis 命名空间
     */
    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".salesBillingGroup.";
    
    /** 
     * <p>添加单条营业部和集中开单组关系</p> 
     * @author foss-zhujunyong
     * @date Apr 26, 2013 3:59:13 PM
     * @param entity
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesBillingGroupDao#addSalesBillingGroup(com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesBillingGroupEntity)
     */
    @Override
    public SalesBillingGroupEntity addSalesBillingGroup(SalesBillingGroupEntity entity) {
	if (entity == null) {
	    return null;
	}

	Date now = new Date();
	entity.setId(UUIDUtils.getUUID());
	entity.setVirtualCode(entity.getId());
	entity.setCreateDate(now);
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	entity.setModifyUser(entity.getCreateUser());
	entity.setVersion(now.getTime());
	entity.setActive(FossConstants.ACTIVE);
	int result = getSqlSession().insert(NAMESPACE + "addSalesBillingGroup", entity);
	return result > 0 ? entity : null;
    }

    @Override
    public SalesBillingGroupEntity deleteSalesBillingGroupByVirtualCode(SalesBillingGroupEntity entity) {
	if (entity == null) {
	    return entity;
	}
	
	Date now = new Date();
	Map<String, Object> map = new HashMap<String, Object> ();
	map.put("inactive", FossConstants.INACTIVE);
	map.put("modifyDate", now);
	map.put("version", now.getTime());
	map.put("modifyUser", entity.getModifyUser());
	map.put("active", FossConstants.ACTIVE);
	map.put("virtualCode", entity.getVirtualCode());

	int result = getSqlSession().update(NAMESPACE + "deleteSalesBillingGroupByVirtualCode", map);
	return result > 0 ? entity : null;
    }

    @Override
    public int deleteSalesBillingGroupBySalesCode(SalesBillingGroupEntity entity) {
	if (entity == null) {
	    return FossConstants.FAILURE;
	}
	
	Date now = new Date();
	Map<String, Object> map = new HashMap<String, Object> ();
	map.put("inactive", FossConstants.INACTIVE);
	map.put("modifyDate", now);
	map.put("version", now.getTime());
	map.put("modifyUser", entity.getModifyUser());
	map.put("active", FossConstants.ACTIVE);
	map.put("salesDeptCode", entity.getSalesDeptCode());

	return getSqlSession().update(NAMESPACE + "deleteSalesBillingGroupBySalesCode", map);
    }

    @Override
    public int deleteSalesBillingGroupBySalesBillingGroupCode(SalesBillingGroupEntity entity) {
	if (entity == null) {
	    return FossConstants.FAILURE;
	}
	
	Date now = new Date();
	Map<String, Object> map = new HashMap<String, Object> ();
	map.put("inactive", FossConstants.INACTIVE);
	map.put("modifyDate", now);
	map.put("version", now.getTime());
	map.put("modifyUser", entity.getModifyUser());
	map.put("active", FossConstants.ACTIVE);
	map.put("salesDeptCode", entity.getSalesDeptCode());
	map.put("billingGroupCode", entity.getBillingGroupCode());

	return getSqlSession().update(NAMESPACE + "deleteSalesBillingGroupBySalesBillingGroupCode", map);
    }
    
    
    
    @Override
    public int deleteSalesBillingGroupByBillingGroupCode(SalesBillingGroupEntity entity) {
	if (entity == null) {
	    return FossConstants.FAILURE;
	}
	
	Date now = new Date();
	Map<String, Object> map = new HashMap<String, Object> ();
	map.put("inactive", FossConstants.INACTIVE);
	map.put("modifyDate", now);
	map.put("version", now.getTime());
	map.put("modifyUser", entity.getModifyUser());
	map.put("active", FossConstants.ACTIVE);
	map.put("billingGroupCode", entity.getBillingGroupCode());

	return getSqlSession().update(NAMESPACE + "deleteSalesBillingGroupByBillingGroupCode", map);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SalesBillingGroupEntity> querySalesListByBillingGroupCode(String billingGroupCode) {
	if (StringUtils.isBlank(billingGroupCode)) {
	    return new ArrayList<SalesBillingGroupEntity> ();
	}
	SalesBillingGroupEntity entity = new SalesBillingGroupEntity();
	entity.setBillingGroupCode(billingGroupCode);
	entity.setActive(FossConstants.ACTIVE);
	return getSqlSession().selectList(NAMESPACE + "querySalesListByBillingGroupCode", entity);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<SalesBillingGroupEntity> queryBillingGroupListBySalesCode(String salesCode) {
	if (StringUtils.isBlank(salesCode)) {
	    return new ArrayList<SalesBillingGroupEntity>();
	}
	SalesBillingGroupEntity entity = new SalesBillingGroupEntity();
	entity.setSalesDeptCode(salesCode);
	entity.setActive(FossConstants.ACTIVE);
	return getSqlSession().selectList(NAMESPACE + "queryBillingGroupListBySalesCode", entity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SalesBillingGroupEntity> querySalesBillingGroupListForDownload(SalesBillingGroupEntity entity) {
	if (entity == null) {
	    entity = new SalesBillingGroupEntity();
	}
	return getSqlSession().selectList(NAMESPACE + "querySalesBillingGroupListForDownload", entity);
    }

	@Override
	public SalesBillingGroupEntity querySalesListByBillingGroupCodeAndSalesCode(
			String billingGroupCode,String salesCode) {
		if (StringUtils.isBlank(salesCode)) {
		    return null;
		}
		if (StringUtils.isBlank(billingGroupCode)) {
		    return null;
		}
		SalesBillingGroupEntity entity = new SalesBillingGroupEntity();
		entity.setSalesDeptCode(salesCode);
		entity.setBillingGroupCode(billingGroupCode);
		entity.setActive(FossConstants.ACTIVE);
		return (SalesBillingGroupEntity) getSqlSession().selectOne(NAMESPACE + "queryBillingGroupListBySalesCodeAndGroupCode", entity);
	}
    
}
