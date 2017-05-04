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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/AirAgencyDeptService.java
 * 
 * FILE NAME        	: AirAgencyDeptService.java
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
import com.deppon.foss.module.base.baseinfo.api.server.dao.IAirAgencyDeptDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleAgencyDeptDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAirAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyCompanyService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISendOutBranchInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.AirAgencyBranchException;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * 空运代理网点接口实现：提供对空运代理网点的增删改查基本操作
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-15 上午11:39:35 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-15 上午11:39:35
 * @since
 * @version
 */
public class AirAgencyDeptService implements IAirAgencyDeptService {
    
    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AirAgencyDeptService.class);
    
    /**
     * 偏线代理网点DAO接口.
     */
    @Inject
    private IVehicleAgencyDeptDao vehicleAgencyDeptDao;
    
    /**
     * 同步代理网点信息service接口.
     */
    @Inject
    private ISendOutBranchInfoService sendOutBranchInfoService;
    
    /**
     * 空运代理网点.
     */
    @Inject
    private IAirAgencyDeptDao airAgencyDeptDao;
    
    /**
     * 偏线代理公司service接口.
     */
    @Inject
    private IVehicleAgencyCompanyService vehicleAgencyCompanyService;
    
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
     * 设置 空运代理网点.
     *
     * @param airAgencyDeptDao the new 空运代理网点
     */
    public void setAirAgencyDeptDao(IAirAgencyDeptDao airAgencyDeptDao) {
        this.airAgencyDeptDao = airAgencyDeptDao;
    }
    
    /**
     * 设置 组织信息 Service接口.
     *
     * @param orgAdministrativeInfoService the new 组织信息 Service接口
     */
    public void setOrgAdministrativeInfoService(
    	IOrgAdministrativeInfoService orgAdministrativeInfoService) {
        this.orgAdministrativeInfoService = orgAdministrativeInfoService;
    }
    
    /**
     * 设置 行政区域接口.
     *
     * @param administrativeRegionsService the new 行政区域接口
     */
    public void setAdministrativeRegionsService(
    	IAdministrativeRegionsService administrativeRegionsService) {
        this.administrativeRegionsService = administrativeRegionsService;
    }
    
    /**
     * 设置 偏线代理公司service接口.
     *
     * @param vehicleAgencyCompanyService the new 偏线代理公司service接口
     */
    public void setVehicleAgencyCompanyService(
    	IVehicleAgencyCompanyService vehicleAgencyCompanyService) {
        this.vehicleAgencyCompanyService = vehicleAgencyCompanyService;
    }
    
    /**
     * 设置 同步代理网点service接口.
     *
     * @param sendOutBranchInfoService the new 同步代理网点service接口
     */
    
    public void setSendOutBranchInfoService(
			ISendOutBranchInfoService sendOutBranchInfoService) {
		this.sendOutBranchInfoService = sendOutBranchInfoService;
	}
    
    
	/**
     * 设置 偏线代理网点DAO接口.
     *
     * @param vehicleAgencyDeptDao the new 偏线代理网点DAO接口
     */
    public void setVehicleAgencyDeptDao(
	    IVehicleAgencyDeptDao vehicleAgencyDeptDao) {
	this.vehicleAgencyDeptDao = vehicleAgencyDeptDao;
    }
    
    /**
     * 新增空运代理网点.
     *
     * @param entity 空运/偏线代理网点实体
     * @return 1：成功；-1：失败
     * @throws AirAgencyBranchException 
     * @author 094463-foss-xieyantao
     * @date 2012-10-15 上午11:42:44
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAirAgencyDeptService#addAirAgencyDept(com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity)
     */
    //@Transactional
    @Override
    public int addAirAgencyDept(OuterBranchEntity entity) throws AirAgencyBranchException{
	//创建日期
	Date date = new Date();
	//防御参数验证
	if(null == entity){
	    
	    return FossConstants.FAILURE;
	}else if(StringUtil.isBlank(entity.getAirWaybillName())){//网点名称不为空验证
	    throw new AirAgencyBranchException(AirAgencyBranchException.AIRWAYBILLNAME_NULL_ERROR_CODE);
	}
	//记录ID有工具类生成ＵＵＩＤ
	entity.setId(UUIDUtils.getUUID());
	//设置创建日期
	entity.setCreateDate(date);
	//设置版本号
	entity.setVersionNo(date.getTime());
	//设置修改日期
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	//第一次记录新增时，虚拟编码为记录的id
	entity.setVirtualCode(entity.getId());
	//设置新增记录默认生效
	entity.setActive(FossConstants.ACTIVE);
	//设置网点类型为：空运(KY)
	entity.setBranchtype(DictionaryValueConstants.OUTERBRANCH_TYPE_KY);
	
    //同步新增代理网点到WDGH及OMS
    syncOuterBranchInfoToWdgh(entity, NumberConstants.ONE);
    
	return airAgencyDeptDao.addAirAgencyDept(entity);
    }
    
	/**
     *<p>同步给网点规划</p>
     *@author 269231 -qirongsheng
     *@date 2016-3-30 上午10:21:32
     *@param outerBranchEntity
     *@param type
     */
    private void syncOuterBranchInfoToWdgh(OuterBranchEntity outerBranchEntity,
			Integer type) {	
    	
    	if(null !=outerBranchEntity){
        	//同步接口
    		sendOutBranchInfoService.syncOutBranchInfoToWDGH(outerBranchEntity, type.toString());
    	}
	}
    
    /**
     * 根据code作废空运代理网点.
     *
     * @param codeStr 空运代理网点虚拟编码拼接字符串
     * @param modifyUser 
     * @return 1：成功；-1：失败
     * @throws AirAgencyBranchException 
     * @author 094463-foss-xieyantao
     * @date 2012-10-15 上午11:43:05
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAirAgencyDeptService#deleteAirAgencyDeptByCode(java.lang.String[])
     */
    //@Transactional
    @Override
    public int deleteAirAgencyDeptByCode(String codeStr,String modifyUser) throws AirAgencyBranchException{
    	
	//代理网点虚拟编码验证
	if(StringUtil.isBlank(codeStr)){
		throw new AirAgencyBranchException("空运代理网点虚拟编码不允许为空！");
	}else {//拆分字符串
	    String[] codes = StringUtil.split(codeStr, SymbolConstants.EN_COMMA);
	    LOGGER.debug("codeStr: "+ codeStr);
	    List<OuterBranchEntity> outlist = vehicleAgencyDeptDao.queryOuterBranchsByVirtualCode(codes);
	    int issuc=airAgencyDeptDao.deleteAirAgencyDeptByCode(codes,modifyUser);    
	    Date date = new Date();
	    for(OuterBranchEntity entity:outlist){
	    	
			entity.setModifyUser(modifyUser);
			entity.setVersionNo(date.getTime());
			entity.setModifyDate(date);
			entity.setActive(FossConstants.INACTIVE);
			
			//同步作废代理网点到WDGH及OMS
			syncOuterBranchInfoToWdgh(entity,NumberConstants.THREE);
		}
		return issuc; 
	}
    }
    
    /**
     * 修改空运代理网点
     * 对于有版本控制的编辑，先逻辑删除该记录，再新增一条记录.
     *
     * @param entity 空运/偏线代理网点实体
     * @return 1：成功；-1：失败
     * @throws AirAgencyBranchException 
     * @author 094463-foss-xieyantao
     * @date 2012-10-15 上午11:43:15
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAirAgencyDeptService#udpateAirAgencyDept(com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity)
     */
    @Transactional
    @Override
    public int updateAirAgencyDept(OuterBranchEntity entity) throws AirAgencyBranchException{
	//参数验证
	if(null == entity){
	    return FossConstants.FAILURE;
	}else if(StringUtil.isBlank(entity.getVirtualCode())){//虚拟编码不允许为空验证
	    throw new AirAgencyBranchException("代理网点虚拟编码不允许为空！");
	}
	//正单开单名称.验证
	if(StringUtil.isBlank(entity.getAirWaybillName())){
	    
	    throw new AirAgencyBranchException(AirAgencyBranchException.AIRWAYBILLNAME_NULL_ERROR_CODE);
	}
	
	//定义一个虚拟编码code
	String[] codes = {entity.getVirtualCode()};
	    
	//作废历史记录
	int flag = airAgencyDeptDao.deleteAirAgencyDeptByCode(codes, entity.getModifyUser());
	LOGGER.debug("flag: "+ flag);
	
	if(flag > 0){//作废成功，新增一条记录
	    //设置状态：有效
	    entity.setActive(FossConstants.ACTIVE);
	    //设置ID
	    entity.setId(UUIDUtils.getUUID());
	    //设置创建日期
	    entity.setCreateDate(new Date());
	    //设置版本号
	    entity.setVersionNo(entity.getCreateDate().getTime());
	    //设置修改日期
	    entity.setModifyDate(new Date(NumberConstants.ENDTIME));

	    //同步更新代理网点到WDGH及OMS
	    syncOuterBranchInfoToWdgh(entity, NumberConstants.TWO);
	    
	    return airAgencyDeptDao.addAirAgencyDept(entity);
	}
	
	return FossConstants.FAILURE;
    }
    
    /**
     * 根据传入对象查询符合条件所有空运代理网点信息.
     *
     * @param entity 空运/偏线代理网点实体
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     * @throws AirAgencyBranchException 
     * @author 094463-foss-xieyantao
     * @date 2012-10-15 上午11:43:25
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAirAgencyDeptService#queryAirAgencyDepts(com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity, int, int)
     */
    @Transactional
    @Override
    public List<OuterBranchEntity> queryAirAgencyDepts(
	    OuterBranchEntity entity, int limit, int start) throws AirAgencyBranchException{
	//设置状态：有效
	entity.setActive(FossConstants.ACTIVE);
	//设置 网点类型：空运代理：KY 偏线代理：PX.
	entity.setBranchtype(DictionaryValueConstants.OUTERBRANCH_TYPE_KY);
	//返回批量填充行政区域名称、管理部门名称、代理公司名称 
	return convertOuterBranchList(airAgencyDeptDao.queryAirAgencyDepts(entity, limit, start));
    }
    
    /**
     * 统计总记录数.
     *
     * @param entity 空运/偏线代理网点实体
     * @return 
     * @throws AirAgencyBranchException 
     * @author 094463-foss-xieyantao
     * @date 2012-10-15 上午11:43:44
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAirAgencyDeptService#getCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity)
     */
    @Transactional
    @Override
    public Long queryRecordCount(OuterBranchEntity entity) throws AirAgencyBranchException{
	//设置状态：有效
	entity.setActive(FossConstants.ACTIVE);
	//设置 网点类型：空运代理：KY 偏线代理：PX.
	entity.setBranchtype(DictionaryValueConstants.OUTERBRANCH_TYPE_KY);
	
	return airAgencyDeptDao.queryRecordCount(entity);
    }
    
    /**
     * <p>
     * 填充行政区域名称、管理部门名称、代理公司名称
     * </p>.
     *
     * @param entity 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-11-12 上午9:12:38
     * @see
     */
    private OuterBranchEntity convertOuterBranch(OuterBranchEntity entity) {
	if (null == entity) {
	    return null;
	} else {
	    // 通过部门编码获得该部门的实体
	    OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
		    .queryOrgAdministrativeInfoByCode(entity.getManagement());
	    //根据代理公司虚拟编码查询代理公司信息
	    BusinessPartnerEntity busiPartner = vehicleAgencyCompanyService.queryEntityByVirtualCode(entity.getAgentCompany());
	    if(null != org){
		//设置管理部门名称
		entity.setManagementName(org.getName());
	    }
	    if(null != busiPartner){
		//设置代理公司名称
		entity.setAgentCompanyName(busiPartner.getAgentCompanyName());
	    }
	    // 设置省份名称
	    entity.setProvName(administrativeRegionsService
		    .queryAdministrativeRegionsNameByCode(entity.getProvCode()));
	    // 设置城市名称
	    entity.setCityName(administrativeRegionsService
		    .queryAdministrativeRegionsNameByCode(entity.getCityCode()));
	    // 设置区县名称
	    entity.setCountyName(administrativeRegionsService
		    .queryAdministrativeRegionsNameByCode(entity
			    .getCountyCode()));

	    return entity;
	}

    }

    /**
     * <p>
     * 批量填充行政区域名称、管理部门名称、代理公司名称
     * </p>.
     *
     * @param list 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-11-12 上午9:13:00
     * @see
     */
    private List<OuterBranchEntity> convertOuterBranchList(
	    List<OuterBranchEntity> list) {
	//定义存放代理网点的集合
	List<OuterBranchEntity> entities = new ArrayList<OuterBranchEntity>();
	//集合验证
	if (CollectionUtils.isNotEmpty(list)) {
	    //循环赋值
	    for (OuterBranchEntity entity : list) {
		//填充行政区域名称、管理部门名称、代理公司名称 
		entity = convertOuterBranch(entity);
		//集合添加
		entities.add(entity);
	    }
	    //返回封装好的集合
	    return entities;
	} else {
	    return null;
	}
    }
    
    /**
     *<p>根据代理公司虚拟编码查询所属代理网点</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-12-29 下午2:42:43
     * @param comVirtualCode 代理公司虚拟编码
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAirAgencyDeptService#queryAirAgencyDeptsByComVirtualCode(java.lang.String)
     */
    @Override
    public List<OuterBranchEntity> queryAirAgencyDeptsByComVirtualCode(
	    String comVirtualCode) {
	//参数验证
	if(StringUtil.isBlank(comVirtualCode)){
	    throw new AirAgencyBranchException("代理公司虚拟编码不允许为空！");
	}else {
	    //返回批量填充批量填充行政区域名称、管理部门名称、代理公司名称 
	    return convertOuterBranchList(airAgencyDeptDao.queryAirAgencyDeptsByComVirtualCode(comVirtualCode));
	}
    }
    
    /**
     * 根据传入对象查询符合条件所有空运代理网点信息(代理网点名称为精确查询).
     *
     * @param entity 空运/偏线代理网点实体
     * @return 符合条件的实体列表
     * @throws AirAgencyBranchException 
     * @author 313353-foss-qiupeng
     * @date 2016-05-19 上午11:43:25
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAirAgencyDeptService#queryAirAgencyDepts(com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity, int, int)
     */
    @Transactional
    @Override
    public List<OuterBranchEntity> queryAgencyBranchByAgentDeptName(
	    OuterBranchEntity entity, int limit, int start) throws AirAgencyBranchException{
	//设置状态：有效
	entity.setActive(FossConstants.ACTIVE);
	//设置 网点类型：空运代理：KY 偏线代理：PX.
	entity.setBranchtype(DictionaryValueConstants.OUTERBRANCH_TYPE_KY);
	//返回批量填充行政区域名称、管理部门名称、代理公司名称 
	return convertOuterBranchList(airAgencyDeptDao.queryAgencyBranchByAgentDeptName(entity));
    }

}
