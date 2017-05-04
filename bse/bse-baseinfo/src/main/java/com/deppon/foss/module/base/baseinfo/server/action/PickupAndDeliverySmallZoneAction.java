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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/PickupAndDeliverySmallZoneAction.java
 * 
 * FILE NAME        	: PickupAndDeliverySmallZoneAction.java
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
package com.deppon.foss.module.base.baseinfo.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.base.util.define.ColumnConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPickupAndDeliverySmallZoneService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.PAndDeliveryZoneRegionVo;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * 接送货小区action
 * 
 * @author 073586-FOSS-LIXUEXING
 * @date 2012-11-27 14:10:10
 * @since
 * @version 0.01
 */
public class PickupAndDeliverySmallZoneAction extends AbstractAction {

    /**
     * 下面是声明的属性
     */
    private static final long serialVersionUID = -802246567875971335L;

    // 接送货小区service接口
    private IPickupAndDeliverySmallZoneService pickupAndDeliverySmallZoneService;
    /**
     * 部门 复杂查询 service.
     */
    private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
    
    /**
     * 系统配置参数 Service接口.
     */
    private IConfigurationParamsService configurationParamsService;
    /**
     * 业务锁Service
     */
    private IBusinessLockService businessLockService;
    /**
     * 
     * 组织信息orgAdministrativeInfoService
     */
    @Inject
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;
    
    public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	// 接送货小区 使用VO
    private PAndDeliveryZoneRegionVo objectVo = new PAndDeliveryZoneRegionVo();

    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(AirAgencyDeptAction.class);
    
    /**
     * @param orgAdministrativeInfoComplexService the orgAdministrativeInfoComplexService to set
     */
    public void setOrgAdministrativeInfoComplexService(
    	IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
        this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
    }
    
    /**
     * @param configurationParamsService the configurationParamsService to set
     */
    public void setConfigurationParamsService(
    	IConfigurationParamsService configurationParamsService) {
        this.configurationParamsService = configurationParamsService;
    }
    
    public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	/**
     * 导出Excel 文件名.
     */
    private String downloadFileName;

    /**
     * 导出Excel 文件流
     */
    private InputStream inputStream;

    /**
     * <p>
     * 查询接送货小区
     * </p>
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-11 10:10:10
     * @return String
     */
    @JSON
    public String queryPickupAndDeliverySmallZoneByEntity() {
    try{
	//获取当前部门编码
	List<String> deptCodes = FossUserContext
		.getCurrentUserManagerDeptCodes();
	// 部门编码集合
	List<String> deptCodeList = new ArrayList<String>();
	SmallZoneEntity entityCondition = objectVo.getSmallZoneEntity();
	
	ConfigurationParamsEntity entity = configurationParamsService.queryConfigurationParamsByOrgCode(
		DictionaryConstants.SYSTEM_CONFIG_PARM__BAS,
		ConfigurationParamsConstants.BAS_REGIONAL_VEHICLE_SYSTEM_ADMINSTRATOR, FossUserContext.getCurrentDeptCode());
	if (entity != null) {
	    deptCodeList = getDeptCodes(entityCondition, deptCodes, deptCodeList,
				entity);
	}else {
	    // 查找指定多个部门的上级第一个顶级车队，然后找该顶级车队下属的所有有"车队调度组"属性的部门编码集合
	    deptCodeList = orgAdministrativeInfoComplexService.queryDispatchTeamDeptCodeListFromTopFleetByCodeList(deptCodes); 
	    if (StringUtils.isNotBlank(entityCondition.getManagement())) {

		if (exists(deptCodeList,
			entityCondition.getManagement())) {
		    deptCodeList = new ArrayList<String>();
		    deptCodeList.add(entityCondition.getManagement());
		} else {
		    deptCodeList = new ArrayList<String>();
		    deptCodeList.add("depponNull");
		}

	    } else {
		deptCodeList.add(FossUserContext.getCurrentDeptCode());
	    }
	}
	
	entityCondition.setManagementCodeList(deptCodeList);
	// 返回的结果显示在表格中：
	objectVo.setSmallZoneEntityList(pickupAndDeliverySmallZoneService
		.queryPickupAndDeliverySmallZones(entityCondition, limit, start));
	totalCount = pickupAndDeliverySmallZoneService
		.queryRecordCount(entityCondition);
	return returnSuccess();
	}
	catch (BusinessException e) {
		   LOGGER.debug(e.getMessage(), e);
		   return returnError(e);
	}
    }

    /**
     * <p>
     * 导出接送货小区EXCEl
     * </p>
     * .
     * 
     * @return
     * @author 094463-foss-xieyantao
     * @date 2012-12-24 下午2:00:06
     * @see
     */
    public String exportSmallZoneList() {
	try {
	    // 导出文件名称
	    downloadFileName = URLEncoder.encode(
		    ColumnConstants.SMALL_ZONE_NAME, "UTF-8");
	    // 获取查询参数
	    SmallZoneEntity entityCondition = objectVo.getSmallZoneEntity();
	    if (null == entityCondition) {
	    	entityCondition = new SmallZoneEntity();
	    }
	    /**
	     * 130566-zjf 设置数据权限
	     */
	    //获取当前部门编码
		List<String> deptCodes = FossUserContext
			.getCurrentUserManagerDeptCodes();
		// 部门编码集合
		List<String> deptCodeList = new ArrayList<String>();
		ConfigurationParamsEntity entity = configurationParamsService.queryConfigurationParamsByOrgCode(
				DictionaryConstants.SYSTEM_CONFIG_PARM__BAS,
				ConfigurationParamsConstants.BAS_REGIONAL_VEHICLE_SYSTEM_ADMINSTRATOR, FossUserContext.getCurrentDeptCode());
			if (entity != null) {
			    deptCodeList = getDeptCodes(entityCondition, deptCodes,
						deptCodeList, entity);
			}else {
			    // 查找指定多个部门的上级第一个顶级车队，然后找该顶级车队下属的所有有"车队调度组"属性的部门编码集合
			    deptCodeList = orgAdministrativeInfoComplexService.queryDispatchTeamDeptCodeListFromTopFleetByCodeList(deptCodes); 
			    if (StringUtils.isNotBlank(entityCondition.getManagement())) {

				if (exists(deptCodeList,
					entityCondition.getManagement())) {
				    deptCodeList = new ArrayList<String>();
				    deptCodeList.add(entityCondition.getManagement());
				} else {
				    deptCodeList = new ArrayList<String>();
				    deptCodeList.add("depponNull");
				}

			    } else {
				deptCodeList.add(FossUserContext.getCurrentDeptCode());
			    }
			}
		entityCondition.setManagementCodeList(deptCodeList);
	    // 获取导出数据对象
	    ExportResource exportResource = pickupAndDeliverySmallZoneService
		    .exportSmallZoneList(entityCondition);

	    ExportSetting exportSetting = new ExportSetting();
	    // 设置名称
	    exportSetting.setSheetName(ColumnConstants.SMALL_ZONE_NAME);

	    ExporterExecutor objExporterExecutor = new ExporterExecutor();
	    // 导出成文件
	    inputStream = objExporterExecutor.exportSync(exportResource,
		    exportSetting);	

	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	} catch (UnsupportedEncodingException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError("UnsupportedEncodingException", e);
	}
    }

	private List<String> getDeptCodes(SmallZoneEntity entityCondition,
			List<String> deptCodes, List<String> deptCodeList,
			ConfigurationParamsEntity entity) {
		String parmValue = entity.getConfValue();
		if(StringUtils.isNotBlank(parmValue)){
		if(!StringUtils.equals(FossConstants.YES, parmValue)){
		    if (StringUtils.isNotBlank(entityCondition.getManagement())) {

			if (exists(deptCodeList,
				entityCondition.getManagement())) {
			    deptCodeList.add(entityCondition.getManagement());
			} else {
			    deptCodeList.add("depponNull");
			}

		    } else {
			deptCodeList.add(FossUserContext.getCurrentDeptCode());
		    }
		}else {
		    // 查找指定多个部门的上级第一个顶级车队，然后找该顶级车队下属的所有有"车队调度组"属性的部门编码集合
		    deptCodeList = orgAdministrativeInfoComplexService.queryDispatchTeamDeptCodeListFromTopFleetByCodeList(deptCodes); 
		    if (StringUtils.isNotBlank(entityCondition.getManagement())) {
			 deptCodeList = new ArrayList<String>();
			 deptCodeList.add(entityCondition.getManagement());

		    } else {
			deptCodeList = new ArrayList<String>();
		    }
		}
		}
		return deptCodeList;
	}

    /**
     * 作废接送货小区
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-11 10:10:10
     * @return String
     * @see
     */
    public String deletePickupAndDeliverySmallZone() {
	try {
	    objectVo.setReturnInt(pickupAndDeliverySmallZoneService
		    .deletePickupAndDeliverySmallZoneByCode(
			    objectVo.getCodeStr(), "1"));
	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * 修改接送货小区
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-11 10:10:10
     * @return String
     * @see
     */
    public String updatePickupAndDeliverySmallZone() {
	try {
		if(StringUtils.isNotBlank(objectVo.getSmallZoneEntity().getRegionName())){
			MutexElement mutex = new MutexElement(String.valueOf(objectVo.getSmallZoneEntity().getRegionName()), "PICKUP_SMALLZONE_NAME",MutexElementType.PICKUP_SMALLZONE_NAME);
			LOGGER.info("开始加锁：" + mutex.getBusinessNo());
			boolean result = businessLockService.lock(mutex,
					NumberConstants.ZERO);
			if (result) {
				LOGGER.info("成功加锁：" + mutex.getBusinessNo());
			} else {
				LOGGER.info("失败加锁：" + mutex.getBusinessNo());
			}
			 objectVo.setReturnInt(pickupAndDeliverySmallZoneService
					    .updatePickupAndDeliverySmallZone(objectVo
						    .getSmallZoneEntity()));
			LOGGER.info("开始解锁：" + mutex.getBusinessNo());
			// 解业务锁
			businessLockService.unlock(mutex);
			LOGGER.info("完成解锁：" + mutex.getBusinessNo());
		}
	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * 新增接送货小区
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-11 10:10:10
     * @return String
     * @see
     */
    public String addPickupAndDeliverySmallZone() {
	try {
		
	    objectVo.setReturnInt(pickupAndDeliverySmallZoneService
		    .addPickupAndDeliverySmallZone(objectVo
			    .getSmallZoneEntity()));
	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * <p>
     * 接送货小区 是否重复
     * </p>
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-11 10:10:10
     * @return String
     */
    public String pickupAndDeliverySmallZoneIsExist() {
	// TODO 接送货小区 是否重复
	return returnSuccess();
    }
    
    /**
     * <p>判断某个元素在集合中是否存在</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-6-28 上午9:38:08
     * @param list
     * @param element
     * @return
     * @see
     */
    private boolean exists(List<String> list,String element){
	if(CollectionUtils.isNotEmpty(list) && null != element){
	    for (String elment2 : list) {
                if (StringUtils.equals(elment2,element)) {
                    return true;
                }
            }
	}
	return false;
    }

    /**
     * 小区名称重复验证
     * @return
     */
    @JSON
    public String queryPickSmallRegionByRegionName(){
    	SmallZoneEntity entity =
    			pickupAndDeliverySmallZoneService.querySmallZoneByName(objectVo.getSmallZoneEntity().getRegionName());
    	if(null!=entity){
    		 return returnError("该小区名称已存在！");
    	}
    	objectVo.setSmallZoneEntity(entity);
    	return returnSuccess();
    }
    
    /**
     * 根据管理部门（顶级车队）获取所属外场，再获取所属外场对应派送部坐标 
     * @author 187862-dujunhui
     * @date 2015-5-21 下午3:41:02
     * @return
     */
    @JSON
    public String findDeliverySaleDepartment(){
    	if(StringUtils.isNotEmpty(objectVo.getSmallZoneEntity().getManagement())){
    		//根据管理部门（顶级车队）获取所属外场
    		List<String> bizTypes = new ArrayList<String>();
    		bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);//外场类型
    		OrgAdministrativeInfoEntity  outfieldEntity=orgAdministrativeInfoComplexService.
    				queryOrgAdministrativeInfoByCode(objectVo.getSmallZoneEntity().getManagement(),bizTypes);
    		//根据外场组织CODE，查询该外场的驻地派送部门对象（包含CODE）
    		if(outfieldEntity!=null){
    			List<SaleDepartmentEntity> saleList=orgAdministrativeInfoComplexService.queryStationDeliverOrgByOutfieldCode(outfieldEntity.getCode());
    			if(CollectionUtils.isNotEmpty(saleList)){
    				//根据驻地派送部CODE查询组织表中部门服务区坐标编号
    				OrgAdministrativeInfoEntity orgEntity=orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(saleList.get(0).getCode());
    				if(orgEntity!=null){//取部门服务坐标
    					objectVo.setDeliverySaleCoordinate(orgEntity.getDepCoordinate());
    				}
    			}
    		}
    	}
		return returnSuccess();
    }
    /*
     * =================================================================
     * 下面是get,set方法：
     */
    public PAndDeliveryZoneRegionVo getObjectVo() {
	return objectVo;
    }

    public void setObjectVo(PAndDeliveryZoneRegionVo objectVo) {
	this.objectVo = objectVo;
    }

    public void setPickupAndDeliverySmallZoneService(
	    IPickupAndDeliverySmallZoneService pickupAndDeliverySmallZoneService) {
	this.pickupAndDeliverySmallZoneService = pickupAndDeliverySmallZoneService;
    }

    /**
     * @return the downloadFileName
     */
    public String getDownloadFileName() {
	return downloadFileName;
    }

    /**
     * @param downloadFileName
     *            the downloadFileName to set
     */
    public void setDownloadFileName(String downloadFileName) {
	this.downloadFileName = downloadFileName;
    }

    /**
     * @return the inputStream
     */
    public InputStream getInputStream() {
	return inputStream;
    }

    /**
     * @param inputStream
     *            the inputStream to set
     */
    public void setInputStream(InputStream inputStream) {
	this.inputStream = inputStream;
    }

}
