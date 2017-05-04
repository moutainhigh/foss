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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/VehicleAgencyDeptAction.java
 * 
 * FILE NAME        	: VehicleAgencyDeptAction.java
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

/**
 * 1.5.3 界面描述-偏线代理网
 * 
 * 点查询界面 该界面分为三个部分：
 * 
 * 查询条件区域、
 * 
 * 查询结果列表、
 * 
 * 功能按钮区； 1.
 * 
 * 
 * 查询条件区域：查询条件包括偏
 * 
 * 线代理网点编码、偏线代理网点
 * 
 * 、代理公司、省份、增值业务、
 * 
 * 管理部门； 1.1
 * 
 * 所属偏线代理公司：共用选择框
 * 
 * ，内容读取偏线代理公司基础资
 * 
 * 料； 1.2 城市：共用选择框，读
 * 
 * 取行政区域基础资料中的城市信息
 * 
 * ； 1.3
 * 增值服务：下拉框，包括全部、
 * 
 * 返回签单、货到付款、代收货款
 * 
 * ； 1.4 承运业务：下拉框，包括
 * 
 * 全部、出发、到达、中转； 1.5
 * 
 * 管理部门：新增偏线
 * 
 * 代理网点时的管
 * 
 * 理部门；共用选择框，
 * 
 * 读取行政组织； 2.
 * 
 * 
 * 查询结果列表：数据元素参见
 * 
 * 【偏线代理网点查询结果列表】，
 * 
 * “偏线代理网点编码”
 * 
 * 字段添加超链接，
 * 
 * 点击超链可打开偏线代理网点详情界面；
 * 
 *  3.
 * 功能按钮区：按钮包括新增、修改、
 * 
 * 作废、查询、重置；  
 * 
 * 新增：此按钮位于系统悬浮工具条中，
 * 
 * 点击可打开偏线代理网
 * 
 * 点新增界面(图2)；
 *  
 * 修改：图标按钮，位于【偏线代理网点查
 * 
 * 询结果列表】最前面的操作列，
 * 
 * 点击可打开偏线代理网点修改界面(图2)； 
 * 
 * 
 * 作废：【偏线代理网点查询结果列表】
 * 
 * 顶部和下部各有一个
 * 
 * 
 * “作废”按钮，点击可作废选中的偏线
 * 
 * 代理网点信息；另外存在“作废”的图标
 * 
 * 按钮，位于【偏线代理网点查询结果列表
 * 
 * 】最前面的操作列，点击可作废该行偏线
 * 
 * 代理网点；  查询：点击此按钮查询符
 * 
 * 合条件的偏线代理网点；  重置：重
 * 
 * 新初始化【偏线代理网点查询条件】；
 * 4. 提供的相关用例链接或提示信息：作
 * 
 * 废偏线代理网点成功后，提示操作成功，
 * 
 * 同时刷新【偏线代理网点查询结果列表】
 * 
 * ，停留在查询页面，可继续执行本用例；
 * 
 * 
 * 作废失败后，提示失败原因。 1.5.5 
 * 
 * 界面描述-偏线代理网点新增、修改
 * 
 * 界面 该界面分为两个部分：偏线
 * 
 * 代理网点信息输入区域，功能按
 * 
 * 钮区； 1.
 * 
 * 偏线代理网点信息输入区域
 * 
 * 
 * ：字段包括偏线代理网点编码、偏
 * 
 * 线代理网点名称、代理公司、省份
 * 
 * 、城市、区/县、偏线代理网点地
 * 
 * 址、联系电话、备注、标准服务
 * 
 * 、增值服务
 * 
 * 、到达限制、自提区域描述、派
 * 
 * 送区域描述； 1.1 偏线代理网
 * 
 * 点编码：可作为偏线代理网点的唯一标
 * 
 * 识，不可重复，参见业务规则SR-1
 * 
 * ； 1.2
 * 偏线代理网点名称：不可重复，参见
 * 
 * 业务规则SR-1； 1.3 管理部门：共
 * 
 * 用选择框，读取行政组织，参见业
 * 
 * 务规则SR-2； 1.4
 * 
 * 
 * 代理公司：该偏线代理网点所属的代
 * 
 * 理公司，共用选择框； 1.5 省份
 * 
 * 、城市、区/县：共用选择框，读
 * 
 * 取行政区域基础资料，参见业务
 * 
 * 规则SR-2； 1.6
 * 标准服务：包括自提、送货上
 * 
 * 门复选框，参见业务规则SR-3； 
 * 
 * 1.7 增值服务：包括返回签单、
 * 
 * 代收货款、货到付款复选框，参见业务规则SR-3；
 * 1.8 承运业务：包括出发、到达
 * 
 * 、中转复选框，参见业务规则SR
 * 
 * -3； 2. 功能按钮区：按钮包括
 * 
 * 保存、取消、重置； 
 * 
 * 保存：点击此按钮保存偏线代理
 * 
 * 网点信息；  取消：退出当前
 * 
 * 
 * 界面；  重置：点击此按钮重
 * 
 * 新初始化输入区域各控件； 3.
 * 
 * 提供的相关用例链接或提示信息
 * 
 * ：保存成功后，提示操作成功，
 * 
 * 停留在当前界面，界面中各控件
 * 
 * 不可输入；保存失败后，提
 * 
 * 示失败原因。
 * 
 * SR-1 “偏线代理网点编码”不可
 * 
 * 重复；“偏线代理网点名称”不可
 * 
 * 重复； SR-2 新增或修改偏线代
 * 
 * 理网点时： 1、
 * 
 * 新增、修改时，偏线代理维护员
 * 
 * 登录后，界面初始化时“管理部门
 * 
 * ”默认为当前用户所属部门，不
 * 
 * 
 * 可更改；系统管理员可修改“管
 * 
 * 理部门”； 2、
 * 
 * 必须先输入“省份”才可输入“城
 * 
 * 
 * 市”，并且输入“省份”后，“城市
 * 
 * ”自动过滤为该省份下辖城市；
 * 
 *  3、
 * 必须先输入“城市”才可输入“区/县
 * 
 * ”，并且输入“城市”后，“区/县”
 * 
 * 自动过滤为该城市下辖区县； S
 * 
 * R-3 新增或修改偏线代理网点时
 * 
 * ： 1、
 * 只有勾选“标准服务”中的“自提”
 * 
 * 后，“自提区域描述”方可输入
 * 
 * ； 2、 只有勾选“标准服务”中
 * 
 * 的“送货上门”后，“派送区域描述
 * 
 * ”方可输入；
 * 
 * 只有勾选“承运业务”中的“到达
 * 
 * ”后，方可勾选“标准服务”和“增值
 * 
 * 服务”； SR-4
 * 
 * 只有新增偏线代理网点时的管
 * 
 * 理部门或者系统管理员才有权限
 * 
 * 修改、作废偏线代理网点；
 * 
 */
package com.deppon.foss.module.base.baseinfo.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.foss.base.util.define.ColumnConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.AirAgencyCompanyException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.VehicleAgencyBranchException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.AgencyCompanyOrDeptVo;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.define.FossConstants;

/**
 * 偏线代理网点action.
 *
 * @author 073586-FOSS-LIXUEXING
 * @date 2012-11-28 19:10:10
 * @since
 * @version 0.01
 */
public class VehicleAgencyDeptAction extends AbstractAction {

    /**
     * 下面是声明的属性.
     */
    private static final long serialVersionUID = -3372339694836134644L;

    /**
     * 偏线代理网点service接口.
     */
    private IVehicleAgencyDeptService vehicleAgencyDeptService;
    
    /**
     * 导出Excel 文件名.
     */
    private String downloadFileName;
    /**
     * 导出Excel 文件流.
     */
    private InputStream inputStream;

    /**
     * 偏线代理网点 action使用VO.
     */
    private AgencyCompanyOrDeptVo objectVo = new AgencyCompanyOrDeptVo();

    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(VehicleAgencyDeptAction.class);
    /**
	 * 业务锁SERVICE
	 */
	private IBusinessLockService businessLockService;
	
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}
    
    /**
     * 获取 导出Excel 文件流.
     *
     * @return  the inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }
    
    /**
     * 设置 导出Excel 文件流.
     *
     * @param inputStream the inputStream to set
     */
    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * 获取 导出Excel 文件名.
     *
     * @return  the downloadFileName
     */
    public String getDownloadFileName() {
        return downloadFileName;
    }

    /**
     * 设置 导出Excel 文件名.
     *
     * @param downloadFileName the downloadFileName to set
     */
    public void setDownloadFileName(String downloadFileName) {
        this.downloadFileName = downloadFileName;
    }

    /**
     * <p>
     * 查询偏线代理网点
     * </p>.
     *
     * @return String
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-11-27 下午14:10:10
     */
    public String queryVehicleAgencyDept() {
	objectVo = dealCarrierBusiness(dealValueAddedServices(objectVo));
	OuterBranchEntity entityCondition = objectVo.getOuterBranchEntity();
	// 返回的结果显示在表格中：
	objectVo.setOuterBranchEntityList(vehicleAgencyDeptService
		.queryVehicleAgencyDepts(entityCondition, limit, start));
	totalCount = vehicleAgencyDeptService.queryRecordCount(entityCondition);
	return returnSuccess();
    }

    /**
     * <p>
     * 偏线代理网点 是否重复
     * </p>.
     *
     * @return String
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-05 11:30:10
     */
    public String vehicleAgencyDeptIsExist() {
	try {
	    objectVo.setOuterBranchEntityList(vehicleAgencyDeptService
		    .queryVehicleAgencyDepts(objectVo.getOuterBranchEntity(),
			    1, 0));
	    return returnSuccess();
	} catch (VehicleAgencyBranchException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * 根据代理公司虚拟编码查询该公司的所有代理网点.
     *
     * @return String
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-11-30 13:50:10
     * @see
     */
    public String queryOuterBranchsByComCode() {
	String comVirtualCode = objectVo.getBusinessPartnerEntity()
		.getVirtualCode();
	// 返回的结果显示在表格中：
	objectVo.setOuterBranchEntityList(vehicleAgencyDeptService
		.queryOuterBranchsByComCode(comVirtualCode));
	return returnSuccess();
    }

    /**
     * 根据code作废偏线代理网点.
     *
     * @return String
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-11-27 下午19:10:10
     * @see
     */
    public String deleteVehicleAgencyDeptByCode() {
	try {
	    // 1：成功；-1：失败
	    int returnInt = vehicleAgencyDeptService
		    .deleteVehicleAgencyDeptByCode(objectVo.getCodeStr(),
			    FossUserContext.getCurrentInfo().getEmpCode());
	    objectVo.setReturnInt(returnInt);
	    return returnSuccess();
	} catch (AirAgencyCompanyException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * 修改偏线代理网点.
     *
     * @return String
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-11-28 13:50:10
     * @see
     */
    public String updateVehicleAgencyDept() {
	try {
	    // 1：成功；-1：失败
	    int returnInt = vehicleAgencyDeptService
		    .updateVehicleAgencyDept(objectVo.getOuterBranchEntity());
	    objectVo.setReturnInt(returnInt);
	    return returnSuccess();
	} catch (AirAgencyCompanyException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * 新增偏线代理网点.
     *
     * @return String
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-11-28 13:50:10
     * @see
     */
    public String addVehicleAgencyDept() {
	try {
		// 业务锁
		MutexElement mutex = new MutexElement(objectVo
				.getOuterBranchEntity().getAgentDeptCode(),
				"AGENT_DEPT_CODE", MutexElementType.AGENT_DEPT_CODE);
		LOGGER.info("开始加锁：" + mutex.getBusinessNo());
		boolean result = businessLockService.lock(mutex, NumberConstants.ZERO);
		if (result) {
			LOGGER.info("成功加锁：" + mutex.getBusinessNo());
		    // 1：成功；-1：失败
		    int returnInt = vehicleAgencyDeptService
			    .addVehicleAgencyDept(objectVo.getOuterBranchEntity());
		    objectVo.setReturnInt(returnInt);
		    LOGGER.info("开始解锁：" + mutex.getBusinessNo());
			// 解业务锁
			businessLockService.unlock(mutex);
			LOGGER.info("完成解锁：" + mutex.getBusinessNo());
		}else{
			LOGGER.info("失败加锁：" + mutex.getBusinessNo());
		}
	    return returnSuccess();
	} catch (AirAgencyCompanyException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * 处理增值服务，前台传数据字典转换到后台查询.
     *
     * @param agencyCompanyOrDeptVo 
     * @return 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-11-29 11:30:42
     */
    private AgencyCompanyOrDeptVo dealValueAddedServices(
	    AgencyCompanyOrDeptVo agencyCompanyOrDeptVo) {
	OuterBranchEntity entityCondition = agencyCompanyOrDeptVo
		.getOuterBranchEntity();
	if (DictionaryValueConstants.PAY_COLLECTION
		.equals(agencyCompanyOrDeptVo.getValueAddedServices())||DictionaryValueConstants.CASH_DELIVERY
		.equals(agencyCompanyOrDeptVo.getValueAddedServices())||DictionaryValueConstants.BACK_SIGN
		.equals(agencyCompanyOrDeptVo.getValueAddedServices())) {
	    entityCondition.setReturnBill(FossConstants.YES);
	} /*else if (DictionaryValueConstants.CASH_DELIVERY
		.equals(agencyCompanyOrDeptVo.getValueAddedServices())) {
	    entityCondition.setReturnBill(FossConstants.YES);
	} else if (DictionaryValueConstants.BACK_SIGN
		.equals(agencyCompanyOrDeptVo.getValueAddedServices())) {
	    entityCondition.setReturnBill(FossConstants.YES);
	}*/
	agencyCompanyOrDeptVo.setOuterBranchEntity(entityCondition);
	return agencyCompanyOrDeptVo;
    }

    /**
     * 处理承运业务，前台传数据字典转换到后台查询.
     *
     * @param agencyCompanyOrDeptVo 
     * @return 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-11-29 11:30:42
     */
    private AgencyCompanyOrDeptVo dealCarrierBusiness(
	    AgencyCompanyOrDeptVo agencyCompanyOrDeptVo) {
	OuterBranchEntity entityCondition = agencyCompanyOrDeptVo
		.getOuterBranchEntity();
	if (DictionaryValueConstants.DEPARTURE.equals(agencyCompanyOrDeptVo
		.getCarrierBusiness())) {
	    entityCondition.setLeave(FossConstants.ACTIVE);
	} else if (DictionaryValueConstants.ARRIVE.equals(agencyCompanyOrDeptVo
		.getCarrierBusiness())) {
	    entityCondition.setArrive(FossConstants.ACTIVE);
	} else if (DictionaryValueConstants.TRANSFER
		.equals(agencyCompanyOrDeptVo.getCarrierBusiness())) {
	    entityCondition.setTransfer(FossConstants.ACTIVE);
	}
	agencyCompanyOrDeptVo.setOuterBranchEntity(entityCondition);
	return agencyCompanyOrDeptVo;
    }
    
   /**
    * <p>导出偏线代理网点数据至EXCEl</p> 
    * @author 094463-foss-xieyantao
    * @date 2013-7-5 下午4:44:23
    * @return
    * @see
    */
    public String exportVehicleAgencyDepts(){
	try {
	    //导出文件名称
	    downloadFileName = URLEncoder.encode(ColumnConstants.EXPROT_VEHICLE_AGENCYDEPT_NAME, "UTF-8");
	    //获取查询参数
	    OuterBranchEntity entity = objectVo.getOuterBranchEntity();
	    if(null == entity){
		entity = new OuterBranchEntity();
	    }
	    //导出偏线代理网点
	    ExportResource exportResource =vehicleAgencyDeptService.exportVehicleAgencyDeptList(entity);
	    ExportSetting exportSetting = new ExportSetting();
	    //设置名称
	    exportSetting.setSheetName(ColumnConstants.EXPROT_VEHICLE_AGENCYDEPT_NAME);
	    
	    ExporterExecutor objExporterExecutor = new ExporterExecutor();
	    // 导出成文件
	    inputStream =  objExporterExecutor.exportSync(exportResource, exportSetting);
	    
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
     * 设置 偏线代理网点service接口.
     *
     * @param vehicleAgencyDeptService the new 偏线代理网点service接口
     */
    public void setVehicleAgencyDeptService(
	    IVehicleAgencyDeptService vehicleAgencyDeptService) {
	this.vehicleAgencyDeptService = vehicleAgencyDeptService;
    }

    /**
     * 获取 偏线代理网点 action使用VO.
     *
     * @return the 偏线代理网点 action使用VO
     */
    public AgencyCompanyOrDeptVo getObjectVo() {
	return objectVo;
    }

    /**
     * 设置 偏线代理网点 action使用VO.
     *
     * @param objectVo the new 偏线代理网点 action使用VO
     */
    public void setObjectVo(AgencyCompanyOrDeptVo objectVo) {
	this.objectVo = objectVo;
    }
}
