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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/VehicleAgencyCompanyAction.java
 * 
 * FILE NAME        	: VehicleAgencyCompanyAction.java
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

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.ColumnConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyCompanyService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.VehicleAgencyCompanyException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.AgencyCompanyOrDeptVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;

/**
 * 偏线代理公司action.
 * 
 * @author 073586-FOSS-LIXUEXING
 * @date 2012-11-27 下午14:10:10
 * @since
 * @version 0.01
 */
public class VehicleAgencyCompanyAction extends AbstractAction {

    /**
     * 下面是声明的属性.
     */
    private static final long serialVersionUID = -3372339694836134644L;

    /**
     * 偏线代理公司service接口.
     */
    private IVehicleAgencyCompanyService vehicleAgencyCompanyService;

    /**
     * 偏线代理公司 action使用VO.
     */
    private AgencyCompanyOrDeptVo objectVo = new AgencyCompanyOrDeptVo();

    /**
     * 导出Excel 文件名.
     */
    private String downloadFileName;

    /**
     * 导出Excel 文件流.
     */
    private InputStream inputStream;

    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(VehicleAgencyCompanyAction.class);

    /**
     * 获取 导出Excel 文件名.
     * 
     * @return the downloadFileName
     */
    public String getDownloadFileName() {
	return downloadFileName;
    }

    /**
     * 设置 导出Excel 文件名.
     * 
     * @param downloadFileName
     *            the downloadFileName to set
     */
    public void setDownloadFileName(String downloadFileName) {
	this.downloadFileName = downloadFileName;
    }

    /**
     * 获取 导出Excel 文件流.
     * 
     * @return the inputStream
     */
    public InputStream getInputStream() {
	return inputStream;
    }

    /**
     * 设置 导出Excel 文件流.
     * 
     * @param inputStream
     *            the inputStream to set
     */
    public void setInputStream(InputStream inputStream) {
	this.inputStream = inputStream;
    }

    /**
     * <p>
     * 查询偏线代理公司
     * </p>
     * .
     * 
     * @return String
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-11-27 下午14:10:10
     */
    public String queryVehicleAgencyCompany() {
	BusinessPartnerEntity entityCondition = objectVo
		.getBusinessPartnerEntity();
	// 返回的结果显示在表格中：
	objectVo.setBusinessPartnerEntityList(vehicleAgencyCompanyService
		.queryVehicleAgencyCompanys(entityCondition, limit, start));
	totalCount = vehicleAgencyCompanyService
		.queryRecordCount(entityCondition);
	return returnSuccess();
    }

    /**
     * <p>
     * 偏线代理公司 是否重复
     * </p>
     * .
     * 
     * @return String
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-05 11:30:10
     */
    public String vehicleAgencyCompanyIsExist() {
	try {
	    BusinessPartnerEntity entityCondition = objectVo
		    .getBusinessPartnerEntity();
	    if (entityCondition != null
		    && StringUtils.isNotBlank(entityCondition
			    .getAgentCompanyCode())) {
		// 根据偏线代理公司编码查询代理公司信息 (验证该代理公司是否存在)
		objectVo.setBusinessPartnerEntity(vehicleAgencyCompanyService
			.queryEntityByCode(entityCondition
				.getAgentCompanyCode()));
	    } else if (entityCondition != null
		    && StringUtils.isNotBlank(entityCondition
			    .getAgentCompanyName())) {
		// 根据偏线代理公司名称查询代理公司信息 (验证该代理公司是否存在)
		objectVo.setBusinessPartnerEntity(vehicleAgencyCompanyService
			.queryEntityByName(entityCondition
				.getAgentCompanyName()));
	    } else if (entityCondition != null
		    && StringUtils.isNotBlank(entityCondition.getSimplename())) {
		// 根据偏线代理公司简称查询代理公司信息 (验证该代理公司是否存在)
		objectVo.setBusinessPartnerEntity(vehicleAgencyCompanyService
			.queryEntityBySimplename(entityCondition
				.getSimplename()));
	    }
	    return returnSuccess();
	} catch (VehicleAgencyCompanyException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * 根据code作废偏线代理公司.
     * 
     * @return String
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-11-27 下午19:10:10
     * @see
     */
    public String deleteVehicleAgencyCompanyByCode() {
	try {
	    // 1：成功；-1：失败
	    int returnInt = vehicleAgencyCompanyService
		    .deleteVehicleAgencyCompanyByCode(objectVo.getCodeStr(),
			    FossUserContext.getCurrentInfo().getEmpCode());
	    objectVo.setReturnInt(returnInt);
	    return returnSuccess();
	} catch (VehicleAgencyCompanyException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * 修改偏线代理公司.
     * 
     * @return String
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-11-28 13:50:10
     * @see
     */
    public String updateVehicleAgencyCompany() {
	try {
	    // 1：成功；-1：失败
	    int returnInt = vehicleAgencyCompanyService
		    .updateVehicleAgencyCompany(objectVo
			    .getBusinessPartnerEntity());
	    objectVo.setReturnInt(returnInt);
	    return returnSuccess();
	} catch (VehicleAgencyCompanyException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * 新增偏线代理公司.
     * 
     * @return String
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-11-28 13:50:10
     * @see
     */
    public String addVehicleAgencyCompany() {
	try {
	    // 1：成功；-1：失败
	    int returnInt = vehicleAgencyCompanyService
		    .addVehicleAgencyCompany(objectVo
			    .getBusinessPartnerEntity());
	    objectVo.setReturnInt(returnInt);
	    return returnSuccess();
	} catch (VehicleAgencyCompanyException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }
    
    /**
     * <p>导出偏线代理公司数据至EXCEl</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-7-10 下午4:41:16
     * @return
     * @see
     */
    public String exportVehicleAgencyComs() {
	try {
	    // 导出文件名称
	    downloadFileName = URLEncoder.encode(
		    ColumnConstants.EXPROT_VEHICLE_AGENCYCOM_NAME, "UTF-8");
	    // 获取查询参数
	    BusinessPartnerEntity entity = objectVo.getBusinessPartnerEntity();
	    if (null == entity) {
		entity = new BusinessPartnerEntity();
	    }
	    // 导出偏线代理公司
	    ExportResource exportResource = vehicleAgencyCompanyService
		    .exportVehicleAgencyComtList(entity);
	    ExportSetting exportSetting = new ExportSetting();
	    // 设置名称
	    exportSetting
		    .setSheetName(ColumnConstants.EXPROT_VEHICLE_AGENCYCOM_NAME);

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

    /*
     * =================================================================
     * 下面是get,set方法：
     */
    /**
     * 设置 偏线代理公司service接口.
     * 
     * @param vehicleAgencyCompanyService
     *            the new 偏线代理公司service接口
     */
    public void setVehicleAgencyCompanyService(
	    IVehicleAgencyCompanyService vehicleAgencyCompanyService) {
	this.vehicleAgencyCompanyService = vehicleAgencyCompanyService;
    }

    /**
     * 获取 偏线代理公司 action使用VO.
     * 
     * @return the 偏线代理公司 action使用VO
     */
    public AgencyCompanyOrDeptVo getObjectVo() {
	return objectVo;
    }

    /**
     * 设置 偏线代理公司 action使用VO.
     * 
     * @param objectVo
     *            the new 偏线代理公司 action使用VO
     */
    public void setObjectVo(AgencyCompanyOrDeptVo objectVo) {
	this.objectVo = objectVo;
    }
}
