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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/AirAgencyCompanyService.java
 * 
 * FILE NAME        	: AirAgencyCompanyService.java
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IAirAgencyCompanyDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAirAgencyCompanyService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAirAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISendAgentCompanyInfoToWDGHService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.AirAgencyCompanyException;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * 空运代理公司service接口实现：提供对空运代理公司的增删改查基本操作
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-10-15 下午1:43:30
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-10-15 下午1:43:30
 * @since
 * @version
 */
public class AirAgencyCompanyService implements IAirAgencyCompanyService {

    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(AirAgencyCompanyService.class);

    /**
     * 空运代理公司DAO接口.
     */
    @Inject
    private IAirAgencyCompanyDao airAgencyCompanyDao;

    /**
     * 组织信息 Service接口.
     */
    @Inject
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;

    /**
     * 行政区域接口.
     */
    @Inject
    private IAdministrativeRegionsService administrativeRegionsService;

    /**
     * 空运代理网点接口.
     */
    @Inject
    private IAirAgencyDeptService airAgencyDeptService;
    
	/**
     * 同步代理公司给wdgh 系统接口service
     */
    @Inject
    private ISendAgentCompanyInfoToWDGHService sendAgentCompanyInfoToWDGHService;
    
    /**
     * 设置 空运代理公司DAO接口.
     * 
     * @param airAgencyCompanyDao
     *            the new 空运代理公司DAO接口
     */
    public void setAirAgencyCompanyDao(IAirAgencyCompanyDao airAgencyCompanyDao) {
	this.airAgencyCompanyDao = airAgencyCompanyDao;
    }

    /**
     * 设置 组织信息 Service接口.
     * 
     * @param orgAdministrativeInfoService
     *            the new 组织信息 Service接口
     */
    public void setOrgAdministrativeInfoService(
	    IOrgAdministrativeInfoService orgAdministrativeInfoService) {
	this.orgAdministrativeInfoService = orgAdministrativeInfoService;
    }

    /**
     * 设置 空运代理网点接口.
     * 
     * @param airAgencyDeptService
     *            the airAgencyDeptService to set
     */
    public void setAirAgencyDeptService(
	    IAirAgencyDeptService airAgencyDeptService) {
	this.airAgencyDeptService = airAgencyDeptService;
    }

    /**
     * 设置 行政区域接口.
     * 
     * @param administrativeRegionsService
     *            the new 行政区域接口
     */
    public void setAdministrativeRegionsService(
	    IAdministrativeRegionsService administrativeRegionsService) {
	this.administrativeRegionsService = administrativeRegionsService;
    }
    
    /**
     * 设置 同步代理公司接口.
     * 
     * @param sendAgentCompanyInfoToWDGHService
     *            the new 同步代理公司接口
     */
    public void setSendAgentCompanyInfoToWDGHService(
			ISendAgentCompanyInfoToWDGHService sendAgentCompanyInfoToWDGHService) {
		this.sendAgentCompanyInfoToWDGHService = sendAgentCompanyInfoToWDGHService;
	}
    /**
     * 新增空运代理公司.
     * 
     * @param entity
     *            偏线/空运代理公司实体
     * @return 1：成功；-1：失败
     * @throws AirAgencyCompanyException
     * @author 094463-foss-xieyantao
     * @date 2012-10-15 下午1:45:22
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAirAgencyCompanyService#addAirAgencyCompany(com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity)
     */
    //@Transactional
    @Override
    public int addAirAgencyCompany(BusinessPartnerEntity entity)
	    throws AirAgencyCompanyException {
	// 创建日期
	Date date = new Date();
	// 防御参数验证
	if (null == entity) {
	    return FossConstants.FAILURE;
	}
	// 设置代理公司类别为：空运
	entity.setAgentCompanySort(DictionaryValueConstants.OUTERBRANCH_TYPE_KY);
	// 设置新增记录默认生效
	entity.setActive(FossConstants.ACTIVE);
	// 记录ID有工具类生成ＵＵＩＤ
	entity.setId(UUIDUtils.getUUID());
	// 新增时虚拟编码与第一次新增记录的ID相同
	entity.setVirtualCode(entity.getId());
	// 设置创建日期
	entity.setCreateDate(date);
	// 设置版本号
	entity.setVersionNo(date.getTime());
	LOGGER.debug("versionNo: " + date.getTime());
	// 新增时修改时间与创建时间相同
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));

	// 验证代理公司名称是否重复
	BusinessPartnerEntity businessPartner = queryEntityByName(entity
		.getAgentCompanyName());
	if (null != businessPartner) {
	    throw new AirAgencyCompanyException("代理公司名称不允许重复！");
	}
	// 验证代理公司简称是否重复
	businessPartner = queryEntityBySimplename(entity.getSimplename());
	if (null != businessPartner) {
	    throw new AirAgencyCompanyException("代理公司简称不允许重复！");
	}
	// 验证代理公司编码是否以KY开头
	if (StringUtil.isNotEmpty(entity.getAgentCompanyCode())) {
	    // 代理公司编码验证
	    if (entity.getAgentCompanyCode().length() < NumberConstants.NUMERAL_TWO) {
		throw new AirAgencyCompanyException("空运代理公司代理编码长度必须大于2！");
	    }
	    // 验证代理公司编码是否以KY开头(现在已无此限制,注释掉)
//	    if (!StringUtil.equals(
//		    DictionaryValueConstants.OUTERBRANCH_TYPE_KY,
//		    entity.getAgentCompanyCode().substring(
//			    NumberConstants.NUMERAL_ZORE,
//			    NumberConstants.NUMERAL_TWO))) {
//		throw new AirAgencyCompanyException("空运代理公司代理编码必须以KY开头！");
//	    }
	}
	// 验证代理公司编码是否重复
	businessPartner = queryEntityByCode(entity.getAgentCompanyCode());
	// 代理公司编码不允许为空验证
	if (null != businessPartner) {
	    throw new AirAgencyCompanyException("代理公司编码不允许重复！");
	}
	int result = airAgencyCompanyDao.addAirAgencyCompany(entity);
	
	//同步增加代理公司到WDGH
	sendAgentCompanyInfoToWDGHService.syncAgentCompanyInfo(entity, (NumberConstants.ONE).toString());
	
	return result;
    }

    /**
     * 根据code作废空运代理公司.
     * 
     * @param codeStr
     *            虚拟code拼接的字符串
     * @param modifyUser
     * @return 1：成功；-1：失败
     * @throws AirAgencyCompanyException
     * @author 094463-foss-xieyantao
     * @date 2012-10-15 下午1:45:45
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAirAgencyCompanyService#deleteAirAgencyCompanyByCode(java.lang.String[])
     */
    //@Transactional
    @Override
    public int deleteAirAgencyCompanyByCode(String codeStr, String modifyUser)
	    throws AirAgencyCompanyException {
	// 防御参数验证
	if (StringUtil.isBlank(codeStr)) {
	    throw new AirAgencyCompanyException("空运代理公司虚拟编码不允许为空！");
	} else {// 拆分字符串，返回数组
	    String[] codes = StringUtil
		    .split(codeStr, SymbolConstants.EN_COMMA);
	    LOGGER.debug("codes: " + codeStr);
	    for (int i = 0; i < codes.length; i++) {
		// 根据代理公司虚拟编码查询所属代理网点
		List<OuterBranchEntity> entityList = airAgencyDeptService
			.queryAirAgencyDeptsByComVirtualCode(codes[i]);
		// 查询结果集合验证
		if (CollectionUtils.isNotEmpty(entityList)) {
		    throw new AirAgencyCompanyException("存在代理网点代理公司不允许作废！");
		} else {
		    // 执行删除操作，同步删除代理公司到WDGH
		    airAgencyCompanyDao.deleteAgencyCompanyByVirtualCode(
			    codes[i], modifyUser);
		}
	    }

	    return FossConstants.SUCCESS;
	}
    }

    /**
     * 修改空运代理公司 对于有版本控制的编辑，先逻辑删除该记录，再新增一条记录.
     * 
     * @param entity
     *            偏线/空运代理公司实体
     * @return 1：成功；-1：失败
     * @throws AirAgencyCompanyException
     * @author 094463-foss-xieyantao
     * @date 2012-10-15 下午1:45:56
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAirAgencyCompanyService#updateAirAgencyCompany(com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity)
     */
    @Transactional
    @Override
    public int updateAirAgencyCompany(BusinessPartnerEntity entity)
	    throws AirAgencyCompanyException {
	// 防御参数验证
	if (null == entity) {
	    return FossConstants.FAILURE;
	} else if (StringUtil.isBlank(entity.getVirtualCode())) {// 虚拟编码验证
	    throw new AirAgencyCompanyException("空运代理公司虚拟编码不允许为空！");
	}

	// 定义一个虚拟编码code
	String[] codes = { entity.getVirtualCode() };

	// 作废历史记录
	int flag = airAgencyCompanyDao.deleteAirAgencyCompanyByCode(codes,
		entity.getModifyUser());
	LOGGER.debug("flag：" + flag);
	if (flag > 0) {// 作废成功，新增一条记录
	    // 设置状态：有效
	    entity.setActive(FossConstants.ACTIVE);
	    // 设置ID
	    entity.setId(UUIDUtils.getUUID());
	    // 设置创建日期
	    entity.setCreateDate(new Date());
	    // 设置版本号
	    entity.setVersionNo(entity.getCreateDate().getTime());
	    // 设置修改日期
	    entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	    // 设置代理公司类别为：空运
	    entity.setAgentCompanySort(DictionaryValueConstants.OUTERBRANCH_TYPE_KY);

	    // 验证代理公司名称是否重复
	    BusinessPartnerEntity businessPartner = queryEntityByName(entity
		    .getAgentCompanyName());
	    // 代理公司名称验证
	    if (null != businessPartner) {
		throw new AirAgencyCompanyException("代理公司名称不允许重复！");
	    }
	    // 验证代理公司简称是否重复
	    businessPartner = queryEntityBySimplename(entity.getSimplename());
	    // 代理公司简称验证
	    if (null != businessPartner) {
		throw new AirAgencyCompanyException("代理公司简称不允许重复！");
	    }

	    // 验证代理公司编码是否以KY开头
	    if (StringUtil.isNotEmpty(entity.getAgentCompanyCode())) {
		// 空运代理公司代理编码长度验证
		if (entity.getAgentCompanyCode().length() < NumberConstants.NUMERAL_TWO) {
		    throw new AirAgencyCompanyException("空运代理公司代理编码长度必须大于2！");
		}
		// 代理编码开头验证(现在已无此限制,注释掉)
//		if (!StringUtil.equals(
//			DictionaryValueConstants.OUTERBRANCH_TYPE_KY,
//			entity.getAgentCompanyCode().substring(
//				NumberConstants.NUMERAL_ZORE,
//				NumberConstants.NUMERAL_TWO))) {
//		    throw new AirAgencyCompanyException("空运代理公司代理编码必须以KY开头！");
//		}
	    }

	    // 验证代理公司编码是否重复
	    businessPartner = queryEntityByCode(entity.getAgentCompanyCode());
	    // 代理公司编码验证
	    if (null != businessPartner) {
		throw new AirAgencyCompanyException("代理公司编码不允许重复！");
	    }
	    int result = airAgencyCompanyDao.addAirAgencyCompany(entity);    
	    
	    //同步更新代理公司到WDGH
	    sendAgentCompanyInfoToWDGHService.syncAgentCompanyInfo(entity, (NumberConstants.TWO).toString());
	   
	    // 返回操作结果
	    return result;
	}

	return FossConstants.FAILURE;
    }

    /**
     * 根据传入对象查询符合条件所有空运代理公司信息.
     * 
     * @param entity
     *            偏线/空运代理公司实体
     * @param limit
     *            每页最大显示记录数
     * @param start
     *            开始记录数
     * @return 符合条件的实体列表
     * @throws AirAgencyCompanyException
     * @author 094463-foss-xieyantao
     * @date 2012-10-15 下午1:46:03
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAirAgencyCompanyService#queryAirAgencyCompanys(com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity,
     *      int, int)
     */
    @Transactional
    @Override
    public List<BusinessPartnerEntity> queryAirAgencyCompanys(
	    BusinessPartnerEntity entity, int limit, int start)
	    throws AirAgencyCompanyException {
	// 设置状态：有效
	entity.setActive(FossConstants.ACTIVE);
	// 设置 代理公司类别：空运
	entity.setAgentCompanySort(DictionaryValueConstants.OUTERBRANCH_TYPE_KY);
	// 返回批量填充行政区域名称、管理部门名称 的结果集合
	return convertOuterBranchList(airAgencyCompanyDao
		.queryAirAgencyCompanys(entity, limit, start));
    }

    /**
     * 统计总记录数.
     * 
     * @param entity
     *            偏线/空运代理公司实体
     * @return 符合条件的总记录数
     * @throws AirAgencyCompanyException
     * @author 094463-foss-xieyantao
     * @date 2012-10-15 下午1:46:17
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAirAgencyCompanyService#getCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity)
     */
    @Transactional
    @Override
    public Long queryRecordCount(BusinessPartnerEntity entity)
	    throws AirAgencyCompanyException {
	// 设置状态：有效
	entity.setActive(FossConstants.ACTIVE);
	// 设置 代理公司类别:空运
	entity.setAgentCompanySort(DictionaryValueConstants.OUTERBRANCH_TYPE_KY);
	// 返回总记录数
	return airAgencyCompanyDao.queryRecordCount(entity);
    }

    /**
     * 根据空运代理公司名称查询代理公司信息 (验证该代理公司是否存在).
     * 
     * @param agentCompanyName
     *            空运代理公司名称
     * @return null:不存在 BusinessPartnerEntity:存在
     * @throws AirAgencyCompanyException
     * @author 094463-foss-xieyantao
     * @date 2012-10-23 上午11:05:59
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAirAgencyCompanyService#queryEntityByName(java.lang.String)
     */
    @Transactional
    @Override
    public BusinessPartnerEntity queryEntityByName(String agentCompanyName)
	    throws AirAgencyCompanyException {
	// 防御参数验证
	if (StringUtil.isBlank(agentCompanyName)) {

	    throw new AirAgencyCompanyException("空运代理公司名称不允许为空！");
	}
	LOGGER.debug("agentCompanyName：" + agentCompanyName);
	// 返回填充行政区域名称、管理部门名称 的结果
	return convertBusinessPartner(airAgencyCompanyDao
		.queryEntityByName(agentCompanyName));
    }

    /**
     * 根据空运代理公司简称查询代理公司信息 (验证该代理公司是否存在).
     * 
     * @param simplename
     *            代理公司简称
     * @return null:不存在 BusinessPartnerEntity:存在
     * @throws AirAgencyCompanyException
     * @author 094463-foss-xieyantao
     * @date 2012-10-23 上午11:10:56
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAirAgencyCompanyService#queryEntityBySimplename(java.lang.String)
     */
    @Transactional
    @Override
    public BusinessPartnerEntity queryEntityBySimplename(String simplename)
	    throws AirAgencyCompanyException {
	// 防御参数验证
	if (StringUtil.isBlank(simplename)) {

	    throw new AirAgencyCompanyException("空运代理公司简称不允许为空！");
	}

	// 返回填充行政区域名称、管理部门名称 的结果
	return convertBusinessPartner(airAgencyCompanyDao
		.queryEntityBySimplename(simplename));
    }

    /**
     * 根据空运代理公司编码查询代理公司信息 (验证该代理公司是否存在).
     * 
     * @param agentCompanyCode
     *            代理公司编码
     * @return null:不存在 BusinessPartnerEntity:存在
     * @throws AirAgencyCompanyException
     * @author 094463-foss-xieyantao
     * @date 2012-10-23 上午11:13:16
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAirAgencyCompanyService#queryEntityByCode(java.lang.String)
     */
    @Transactional
    @Override
    public BusinessPartnerEntity queryEntityByCode(String agentCompanyCode)
	    throws AirAgencyCompanyException {
	// 防御参数验证
	if (StringUtil.isBlank(agentCompanyCode)) {

	    throw new AirAgencyCompanyException("空运代理公司编码不允许为空！");
	}
	// 返回封装好的实体
	return convertBusinessPartner(airAgencyCompanyDao
		.queryEntityByCode(agentCompanyCode));
    }

    /**
     * <p>
     * 填充行政区域名称、管理部门名称
     * </p>
     * .
     * 
     * @param entity
     * @return
     * @author 094463-foss-xieyantao
     * @date 2012-11-12 上午9:12:38
     * @see
     */
    private BusinessPartnerEntity convertBusinessPartner(
	    BusinessPartnerEntity entity) {
	// 参数验证
	if (null == entity) {
	    return null;
	} else {
	    // 通过部门编码获得该部门的实体
	    OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
		    .queryOrgAdministrativeInfoByCode(entity.getManagement());
	    if (null != org) {
		// 设置管理公司名称
		entity.setManagementName(org.getName());
	    }
	    // 设置省份名称
	    entity.setProvName(administrativeRegionsService
		    .queryAdministrativeRegionsNameByCode(entity.getProvCode()));
	    // 设置城市名称
	    entity.setCityName(administrativeRegionsService
		    .queryAdministrativeRegionsNameByCode(entity.getCityCode()));

	    return entity;
	}

    }

    /**
     * <p>
     * 批量填充行政区域名称、管理部门名称
     * </p>
     * .
     * 
     * @param list
     * @return
     * @author 094463-foss-xieyantao
     * @date 2012-11-12 上午9:13:00
     * @see
     */
    private List<BusinessPartnerEntity> convertOuterBranchList(
	    List<BusinessPartnerEntity> list) {
	// 定义存放代理公司实体的集合
	List<BusinessPartnerEntity> entities = new ArrayList<BusinessPartnerEntity>();
	// 集合验证
	if (CollectionUtils.isNotEmpty(list)) {
	    // 循环封装行政区域名称、管理部门名称
	    for (BusinessPartnerEntity entity : list) {
		// 填充行政区域名称、管理部门名称
		entity = convertBusinessPartner(entity);
		// 封装好的实体存放到集合中
		entities.add(entity);
	    }
	    return entities;
	} else {
	    return null;
	}
    }

    /**
     * <p>
     * 通过代理网点的编码查询所属的代理公司信息
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-3-15 下午5:05:44
     * @param agencyDeptCode
     *            代理网点编码
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAirAgencyCompanyService#queryBusinessPartnerByAgencyDeptCode(java.lang.String)
     */
    @Override
    public BusinessPartnerEntity queryBusinessPartnerByAgencyDeptCode(
	    String agencyDeptCode) {
	if(StringUtil.isBlank(agencyDeptCode)){
	    throw new AirAgencyCompanyException("代理网点编码不允许为空！");
	}else {
	    return airAgencyCompanyDao.queryBusinessPartnerByAgencyDeptCode(agencyDeptCode);
	}
    }

}
