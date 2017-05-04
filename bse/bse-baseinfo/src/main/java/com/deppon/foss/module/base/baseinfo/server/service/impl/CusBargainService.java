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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/CusBargainService.java
 * 
 * FILE NAME        	: CusBargainService.java
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

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ICusBargainDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICusBargainService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.CusBargainException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CusBargainVo;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 客户合同信息Service接口实现
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-11-20 下午6:56:46
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-11-20 下午6:56:46
 * @since
 * @version
 */
public class CusBargainService implements ICusBargainService {

    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(CusBargainService.class);
   private ICustomerDao customerDao;
    public ICustomerDao getCustomerDao() {
	return customerDao;
}

public void setCustomerDao(ICustomerDao customerDao) {
	this.customerDao = customerDao;
}

	/**
     * 客户合同信息DAO接口.
     */
    private ICusBargainDao cusBargainDao;
    
    /**
     * 组织信息 Service接口.
     */
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;
    
    /**
     * 设置 客户合同信息DAO接口.
     * 
     * @param cusBargainDao
     *            the new 客户合同信息DAO接口
     */
    public void setCusBargainDao(ICusBargainDao cusBargainDao) {
	this.cusBargainDao = cusBargainDao;
    }

    /**
     * 设置 组织信息 Service接口.
     * 
     * @param orgAdministrativeInfoService
     *            the orgAdministrativeInfoService to set
     */
    public void setOrgAdministrativeInfoService(
	    IOrgAdministrativeInfoService orgAdministrativeInfoService) {
	this.orgAdministrativeInfoService = orgAdministrativeInfoService;
    }

    /**
     * 新增客户合同信息.
     * 
     * @param entity
     *            客户合同信息实体
     * @return 1：成功；-1：失败
     * @throws CusBargainException
     * @author 094463-foss-xieyantao
     * @date 2012-11-20 上午9:33:05
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ICusBargainService#addCusBargain(com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity)
     */
    @Transactional
    @Override
    public int addCusBargain(CusBargainEntity entity)
	    throws CusBargainException {

	if (null == entity) {

	    return FossConstants.FAILURE;
	}
	
	if(null == entity.getCrmId()){
	    throw new CusBargainException("客户合同信息CRM_ID不允许为空！");
	}
	
	//先验证在数据库是否存在
	boolean isFlag = cusBargainDao.queryCusBargainByCrmId(entity.getCrmId(),null);
	
	if(isFlag){//存在就修改
	    cusBargainDao.updateCusBargain(entity);
	}else {
	    entity.setId(UUIDUtils.getUUID());
	    // 第一次记录新增时，虚拟编码为记录的id
	    entity.setVirtualCode(entity.getId());

	    cusBargainDao.addCusBargain(entity);
	}
	
	return FossConstants.SUCCESS;
    }

    /**
     * 根据code作废客户合同信息.
     * 
     * @param crmId
     * @param modifyUser
     * @return 1：成功；-1：失败
     * @throws CusBargainException
     * @author dp-xieyantao
     * @date 2012-11-20 上午9:33:05
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ICusBargainService#deleteCusBargainByCode(java.lang.String,
     *      java.lang.String)
     */
    @Transactional
    @Override
    public int deleteCusBargainByCode(BigDecimal crmId, String modifyUser)
	    throws CusBargainException {

	if (null == crmId) {
	    throw new CusBargainException("客户合同ＩＤ不允许为空！");
	} else {
	    LOGGER.debug("crmId: " + crmId);
	    return cusBargainDao.deleteCusBargainByCode(crmId, modifyUser);
	}
    }

    /**
     * 修改客户合同信息 对于有版本控制的编辑，先逻辑删除该记录，再新增一条记录.
     * 
     * @param entity
     *            客户合同信息实体
     * @return 1：成功；-1：失败
     * @throws CusBargainException
     * @author dp-xieyantao
     * @date 2012-11-20 上午9:33:05
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ICusBargainService#updateCusBargain(com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity)
     */
    @Transactional
    @Override
    public int updateCusBargain(CusBargainEntity entity)
	    throws CusBargainException {
	if (null == entity) {
	    return FossConstants.FAILURE;
	}
	if (null == entity.getCrmId()) {
	    throw new CusBargainException("客户合同ＩＤ不允许为空！");
	} else {
	    /*// 作废记录
	    int flag = cusBargainDao.deleteCusBargainByCode(entity.getCrmId(),
		    entity.getModifyUser());

	    if (flag > 0) {// 作废成功
		entity.setActive(FossConstants.ACTIVE);
		entity.setId(UUIDUtils.getUUID());

		return cusBargainDao.addCusBargain(entity);
	    }*/
	    
	    return cusBargainDao.updateCusBargain(entity);
//	    return FossConstants.FAILURE;
	}
    }

    /**
     * <p>
     * 根据crmId,最后一次修改时间查询客户合同是否存在
     * </p>
     * .
     * 
     * @param crmId
     * @param lastupdatetime
     * @return
     * @author 094463-foss-xieyantao
     * @date 2012-11-26 上午10:29:35
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ICusBargainService#queryCusBargainByCrmId(java.math.BigDecimal,
     *      java.util.Date)
     */
    @Override
    public boolean queryCusBargainByCrmId(BigDecimal crmId, Date lastupdatetime) {

	return cusBargainDao.queryCusBargainByCrmId(crmId, lastupdatetime);
    }

    /**
     * <p>
     * 根据客户编码查询客户合同中月结客户的最大欠款天数
     * </p>
     * .
     * 
     * @param custCode
     *            客户编码
     * @return CusBargainEntity(int debtDays:月结客户的最大欠款天数)
     * @author 094463-foss-xieyantao
     * @date 2013-1-17 下午2:03:26
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ICusBargainService#queryCusBargainByCustCode(java.lang.String)
     */
    @Override
    public CusBargainEntity queryCusBargainByCustCode(String custCode) {
	if (StringUtil.isBlank(custCode)) {
	    return null;
	} else {
	    Date date = new Date();
	   
	   CusBargainEntity entity =	cusBargainDao.queryCusBargainByCustCode(custCode, date,FossConstants.ACTIVE);
	   if(entity!=null){
	   BigDecimal crmId = entity.getCrmId();
	   long count = customerDao.queryIscpackBybargainCrmId(crmId);
	   if(count>0){
		   entity.setIsAccuratePackage("Y");
	   }
	   }
	   return entity;
	}
    }
    
    /**
     * <p>根据客户编码、开单时间查询客户合同信息</p>.
     *
     * @param custCode 客户编码
     * @param billDate 开单时间
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-3-11 上午10:18:58
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ICusBargainService#queryCusBargainInfos(java.lang.String, java.util.Date)
     */
    @Override
    public CusBargainEntity queryCusBargainInfos(String custCode, Date billDate) {
	if(StringUtil.isBlank(custCode)){
	    return null;
	}else if(null == billDate){
	    Date date = new Date();
	    return cusBargainDao.queryCusBargainByCustCode(custCode, date,null);
	}else {
	    return cusBargainDao.queryCusBargainByCustCode(custCode, billDate, null);
	}
    }

    /**
     * <p>
     * 根据合同编码和部门编码查询合同信息
     * </p>
     * .
     * 
     * @param bargainCode
     *            合同编号
     * @param deptCode
     *            部门编码
     * @return
     * @author 094463-foss-xieyantao
     * @date 2013-2-23 上午10:35:43
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ICusBargainService#queryCusBargainByParams(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public CusBargainEntity queryCusBargainByParams(String bargainCode,
	    String deptCode) {
	if (StringUtil.isBlank(bargainCode)) {
	    return null;
	}
	if (StringUtil.isNotBlank(deptCode)) {
	    // 通过部门编码获得该部门的标杆编码
	    OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
		    .queryOrgAdministrativeInfoByCode(deptCode);
	    if (null == org) {
		return null;
	    } else {
		String unifiedCode = org.getUnifiedCode();
		return cusBargainDao.queryCusBargainByParams(bargainCode,
			unifiedCode);
	    }
	}else {
	    return cusBargainDao.queryCusBargainByParams(bargainCode, deptCode);
	}

    }
    
    /**
     * 根据Vo获取客户信息
     * @author WangQianJin
     * @date 2013-7-30 下午8:33:30
     */
    public CusBargainEntity queryCusBargainByVo(CusBargainVo vo){
    	return cusBargainDao.queryCusBargainByVo(vo);
    }
    
    /**
     * 根据Vo获取客户信息用于更改单
     * @author WangQianJin
     * @date 2013-8-31 下午16:33:30
     */
    public CusBargainEntity queryCusBargainByVoForRfc(CusBargainVo vo){
    	return cusBargainDao.queryCusBargainByVoForRfc(vo);
    }

	@Override
	public CusBargainEntity queryCusBargainByVoExp(CusBargainVo vo) {
		return cusBargainDao.queryCusBargainByVoExp(vo);
	}

	@Override
	public CusBargainEntity queryCusBargainByVoForRfcExp(CusBargainVo vo) {
		return cusBargainDao.queryCusBargainByVoForRfcExp(vo);
	}
}
