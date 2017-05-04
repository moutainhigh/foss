/*******************************************************************************
 * Copyright 2013 BSE TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/NonfixedCustomerService.java
 * 
 * FILE NAME        	: NonfixedCustomerService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
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
package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.INonfixedCustomerDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.INonfixedCustomerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.NonfixedCustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerPaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.NonfixedCustomerException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 散客信息Service接口实现
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-11-20 下午6:44:02
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-11-20 下午6:44:02
 * @since
 * @version
 */
public class NonfixedCustomerService implements INonfixedCustomerService {

    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(NonfixedCustomerService.class);

    /**
     * 散客信息DAO接口.
     */
    private INonfixedCustomerDao nonfixedCustomerDao;

    /**
     * 组织信息 Service接口.
     */
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;
    
    /**
     * 固定客户Dao接口 为了少走一个方法
     */
    private ICustomerDao customerDao;
    
    /**
     * @param orgAdministrativeInfoService
     *            the orgAdministrativeInfoService to set
     */
    public void setOrgAdministrativeInfoService(
	    IOrgAdministrativeInfoService orgAdministrativeInfoService) {
	this.orgAdministrativeInfoService = orgAdministrativeInfoService;
    }

    /**
     * 设置 散客信息DAO接口.
     * 
     * @param nonfixedCustomerDao
     *            the new 散客信息DAO接口
     */
    public void setNonfixedCustomerDao(INonfixedCustomerDao nonfixedCustomerDao) {
	this.nonfixedCustomerDao = nonfixedCustomerDao;
    }

	/**
	 * @param customerDao the customerDao to set
	 */
	public void setCustomerDao(ICustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	/**
     * 新增散客信息.
     * 
     * @param entity
     *            散客信息实体
     * @return 1：成功；-1：失败
     * @author 094463-foss-xieyantao
     * @date 2012-11-20 下午6:20:51
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.INonfixedCustomerService#addNonfixedCustomer(com.deppon.foss.module.base.baseinfo.api.shared.domain.NonfixedCustomerEntity)
     */
    @Transactional
    @Override
    public int addNonfixedCustomer(NonfixedCustomerEntity entity) {

	if (null == entity) {

	    return FossConstants.FAILURE;
	}
	entity.setId(UUIDUtils.getUUID());
//	entity.setCreateDate(new Date());
	// 新增时修改时间与创建时间相同
//	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	// entity.setActive(FossConstants.ACTIVE);

	return nonfixedCustomerDao.addNonfixedCustomer(entity);
    }

    /**
     * 根据code作废散客信息.
     * 
     * @param code
     * @param modifyUser
     * @return 1：成功；-1：失败
     * @author dp-xieyantao
     * @date 2012-11-20 下午6:20:51
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.INonfixedCustomerService#deleteNonfixedCustomerByCode(java.lang.String,
     *      java.lang.String)
     */
    @Transactional
    @Override
    public int deleteNonfixedCustomerByCode(String code, String modifyUser) {

	if (StringUtil.isBlank(code)) {
	    return FossConstants.FAILURE;
	}
	LOGGER.debug("code:" + code);
	return nonfixedCustomerDao.deleteNonfixedCustomerByCode(code,
		modifyUser);
    }

    /**
     * 修改散客信息.
     * 通过版本控制，修改时先作废数据库存在的记录，再新增一条记录，如果记录在数据库不存在，说明以前数据没有同步过来
     * 则新增一条记录
     * @param entity
     *            散客信息实体
     * @return 1：成功；-1：失败
     * @author dp-xieyantao
     * @date 2012-11-20 下午6:20:51
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.INonfixedCustomerService#updateNonfixedCustomer(com.deppon.foss.module.base.baseinfo.api.shared.domain.NonfixedCustomerEntity)
     */
    @Transactional
    @Override
    public int updateNonfixedCustomer(NonfixedCustomerEntity entity) {

	if (null == entity) {
	    return FossConstants.FAILURE;
	}
	if (null == entity.getCrmId()) {
	    LOGGER.info("散客的CRM_ID不允许为空啊。。。。。。。。。。。。。。。。");
	    return FossConstants.FAILURE;
	}
	// 根据CRMID验证该记录在数据库是否存在
	NonfixedCustomerEntity customer = nonfixedCustomerDao
		.queryEntityByCrmId(entity.getCrmId());

	if (null != customer) {// 记录存在
	    // 先作废该记录
//	    int flag = nonfixedCustomerDao.deleteNonfixedCustomerByCode(String.valueOf(customer.getCrmId()),customer.getModifyUser());
	    /*if (flag > 0) {// 作废成功
		entity.setId(UUIDUtils.getUUID());
		entity.setCreateDate(new Date());
		// 新增时修改时间与创建时间相同
		entity.setModifyDate(new Date(NumberConstants.ENDTIME));
		return nonfixedCustomerDao.addNonfixedCustomer(entity);
	    }*/
	    //修改散客信息
	    return nonfixedCustomerDao.updateNonfixedCustomer(entity);
	} else {//如果记录不存在，则添加该记录
	    entity.setId(UUIDUtils.getUUID());
//	    entity.setCreateDate(new Date());
	    // 新增时修改时间与创建时间相同
//	    entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	    return nonfixedCustomerDao.addNonfixedCustomer(entity);
	}
    }

    /**
     * <p>
     * 根据传入查询条件对象dto，查询符合条件的临欠散客信息集合list
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-1-31 上午10:28:20
     * @param condition
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.INonfixedCustomerService#queryCustomerByCondition(com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto)
     */
    @Override
    public List<CustomerQueryConditionDto> queryCustomerByCondition(
	    CustomerQueryConditionDto condition) {
	// 判空操作
	if (null != condition) {
	    // 判断部门编码不为空时
	    if (StringUtil.isNotBlank(condition.getDeptCode())) {
		// 通过部门编码获得该部门的标杆编码
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
			.queryOrgAdministrativeInfoByCode(condition
				.getDeptCode());
		// 组织不为空时将标杆编码传入进行查询
		if (null != org) {
		    String unifiedCode = org.getUnifiedCode();
		    condition.setDeptCode(unifiedCode);
		}
		// 部门编码无对应的组织编码
		else {
		    return null;
		}
	    }
	    
	    return customerDao.queryNonfixedCustomerByCondition(condition);
	} else {
	    return null;
	}
    }
    /**
     * <p>
     * 根据传入查询条件对象dto，查询符合条件的临欠散客信息集合list(可以查询有效无效信息)
     * 使用该方法客户编码不能为空，否则可能为造成全表扫描
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-1-31 上午10:28:20
     * @param condition
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.INonfixedCustomerService#queryCustomerByCondition(com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto)
     */
    @Override
    public List<NonfixedCustomerEntity> queryCustomerByConditionAll(
	    CustomerQueryConditionDto condition) {
	// 判空操作
	if (null != condition) {
	    //判断客户编码是否为空
		if(StringUtil.isEmpty(condition.getCustCode())){
			  throw new NonfixedCustomerException("散客编码不允许为空！");
		}
	    return nonfixedCustomerDao.queryCustomerByConditionAll(condition);
	} else {
	    return null;
	}
    }
    
    
    /**
     * <p>
     * 根据传入查询条件对象dto，查询符合条件的临欠散客信息集合list
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-1-31 上午10:28:20
     * @param condition
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.INonfixedCustomerService#queryCustomerByCondition(com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto)
     */
    @Override
    public List<CustomerQueryConditionDto> queryCustomerByConditionByPage(
			CustomerPaginationDto condition) {
	// 判空操作
	if (null != condition) {
	    // 判断部门编码不为空时
	    if (StringUtil.isNotBlank(condition.getDeptCode())) {
		// 通过部门编码获得该部门的标杆编码
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
			.queryOrgAdministrativeInfoByCode(condition
				.getDeptCode());
		// 组织不为空时将标杆编码传入进行查询
		if (null != org) {
		    String unifiedCode = org.getUnifiedCode();
		    condition.setDeptCode(unifiedCode);
		}
		// 部门编码无对应的组织编码
		else {
		    return null;
		}
	    }
	    
	    return nonfixedCustomerDao.queryCustomerByConditionByPage(condition);
	} else {
	    return null;
	}
    }
    

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.INonfixedCustomerService#countCustomerByCondition(com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerPaginationDto)
	 */
	@Override
	public int countCustomerByCondition(CustomerPaginationDto condition) {
		// 判空操作
		if (null != condition) {
		    // 判断部门编码不为空时
		    if (StringUtil.isNotBlank(condition.getDeptCode())) {
			// 通过部门编码获得该部门的标杆编码
			OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCode(condition
					.getDeptCode());
			// 组织不为空时将标杆编码传入进行查询
			if (null != org) {
			    String unifiedCode = org.getUnifiedCode();
			    condition.setDeptCode(unifiedCode);
			}
			// 部门编码无对应的组织编码
			else {
			    return 0;
			}
		    }
		    
		    return nonfixedCustomerDao.countCustomerByCondition(condition);
		} else {
		    return 0;
		}
	}
	
    /**
     * <p>
     * 通过传入一个客户编码查询出财务未作废散客信息
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-7-9 上午11:51:03
     * @param custCode
     *            散客编码
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.INonfixedCustomerService#queryNoDeletedCustInfoByCode(java.lang.String)
     */
    @Override
    public NonfixedCustomerEntity queryNoDeletedCustInfoByCode(String custCode) {
	if(StringUtils.isBlank(custCode)){
	    return null;
	}else {
	    List<NonfixedCustomerEntity> list = nonfixedCustomerDao.queryNoDeletedCustInfoByCode(custCode);
	    
	    if(CollectionUtils.isNotEmpty(list)){
		return list.get(0);
	    }else {
		return null;
	    }
	    
	}
    }
    
    /**
	 * 根据查询条件获取客户信息是否存在
	 * @创建时间 2014-5-27 下午6:57:11   
	 * @创建人： WangQianJin
	 */
    @Override
	public int queryCustomerExistByCondition(CustomerQueryConditionDto condition){
		return nonfixedCustomerDao.queryCustomerExistByCondition(condition);
	}

}
