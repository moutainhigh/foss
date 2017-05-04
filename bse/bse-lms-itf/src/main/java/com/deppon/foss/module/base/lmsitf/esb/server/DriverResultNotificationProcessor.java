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
 * PROJECT NAME	: bse-lms-itf
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/lmsitf/esb/server/DriverResultNotificationProcessor.java
 * 
 * FILE NAME        	: DriverResultNotificationProcessor.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.lmsitf.esb.server;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.inteface.domain.vehicle.DriverInfo;
import com.deppon.esb.inteface.domain.vehicle.DriverSyncProcessDetail;
import com.deppon.esb.inteface.domain.vehicle.DriverSyncRequest;
import com.deppon.esb.inteface.domain.vehicle.DriverSyncResponse;
import com.deppon.esb.pojo.transformer.jaxb.DriverSyncRequestTrans;
import com.deppon.esb.pojo.transformer.jaxb.DriverSyncResponseTrans;
import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOwnDriverService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnDriverEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.OwnDriverException;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.lmsitf.esb.util.DataRuleMessageConstant;

/**
 * 用来从LMS同步公司“司机信息”到FOSS数据库：无SUC
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:100847-foss-GaoPeng,date:2012-11-23 上午11:12:40
 * </p>
 * 
 * @author 100847-foss-GaoPeng
 * @date 2012-11-23 上午11:12:40
 * @since
 * @version
 */
public class DriverResultNotificationProcessor implements IProcess {

    private static final Logger LOGGER = LoggerFactory
	    .getLogger(DriverResultNotificationProcessor.class);

    // 同步"司机信息"接口结果操作Service
    private IOwnDriverService ownDriverService;

    // 同步"部门信息"接口结果操作Service
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;

    /**
     * 业务互斥锁服务.
     */
    private IBusinessLockService businessLockService;

    /**
     * @param businessLockService
     *            the businessLockService to set
     */
    public void setBusinessLockService(IBusinessLockService businessLockService) {
	this.businessLockService = businessLockService;
    }

    @Override
    public Object process(Object req) throws ESBBusinessException {
	LOGGER.info(" ***************************** 同步”司机信息“开始 ***************************** ");

	DriverSyncRequest driverSyncRequest = (DriverSyncRequest) req;
	DriverSyncResponse driverSyncResponse = new DriverSyncResponse();

	LOGGER.info(new DriverSyncRequestTrans().fromMessage(driverSyncRequest));

	if (null != driverSyncRequest) {
	    int successCount = 0, failCount = 0;
	    List<DriverSyncProcessDetail> detailList = driverSyncResponse
		    .getDetail();
	    for (DriverInfo driverInfo : driverSyncRequest.getDriverList()) {
		// FOSS"司机信息"信息初始化
		OwnDriverEntity ownDriver = null;
		try {
		    ownDriver = this
			    .converterLMSDataObjectToFOSSObjectData(driverInfo);
		    if (NumberConstants.NUMERAL_S_ONE
			    .equalsIgnoreCase(driverInfo.getOperateType())) {
			if (null != ownDriver) {
			    throw new OwnDriverException(
				    DataRuleMessageConstant.DATA_RULE_REASON_OBJECTISNOTNULL_ERROR);
			}
		    }
		    if (null == ownDriver) {
			ownDriver = new OwnDriverEntity();
		    }

		    /*
		     * else{ if(null == ownDriver){ throw new
		     * OwnDriverException(
		     * DataRuleMessageConstant.DATA_RULE_REASON_OBJECTISNULL_ERROR
		     * ); }else{} }
		     */
		    // 数据转换与封装
		    ownDriver.setEmpCode(driverInfo.getDriverNo());
		    ownDriver.setEmpName(driverInfo.getDriverName());
		    // 转换“部门标杆编码”到“部门编码”
		    if (StringUtils.isNotBlank(driverInfo.getDeptNumber())) {
			OrgAdministrativeInfoEntity orgAdministrativeInfo = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByUnifiedCode(driverInfo
					.getDeptNumber());
			if (null != orgAdministrativeInfo) {
			    ownDriver.setOrgId(orgAdministrativeInfo.getCode());
			} else {
			    throw new OwnDriverException(
				    DataRuleMessageConstant.DATA_RULE_REASON_DEPARTMENT_ERROR);
			}
		    }
		    ownDriver.setEmpPhone(driverInfo.getTelephone());
		    /*
		     * 数据类型转换： 1、驾照类型
		     */
		    this.converterLMSDataObjectToFOSSObjectData(ownDriver,
			    driverInfo);
		    // 业务锁
		    MutexElement mutex = new MutexElement(
			    driverInfo.getDriverNo(), "DRIVER_NO",
			    MutexElementType.DRIVER_NO);
		    LOGGER.info("开始加锁：" + mutex.getBusinessNo());
		    boolean result = businessLockService.lock(mutex,
			    NumberConstants.ZERO);
		    if (result) {
			LOGGER.info("成功加锁：" + mutex.getBusinessNo());
			if (NumberConstants.NUMERAL_S_ONE
				.equalsIgnoreCase(driverInfo.getOperateType())) {
			    LOGGER.info("新增司机信息开始。。。。。。。。。。。。。。。。。。。。。。。。。。");
			    // 根据同步的动作执行对应的"新增"
			    ownDriverService.addOwnDriver(ownDriver,
				    ComnConst.EXTERNAL_SYSTEM_LMS_USER_ACCOUNT,
				    false);
			    LOGGER.info("新增司机信息结束。。。。。。。。。。。。。。。。。。。。。。。。。。");

			} else if (NumberConstants.NUMERAL_S_THREE
				.equalsIgnoreCase(driverInfo.getOperateType())) {
			    LOGGER.info("作废司机信息开始。。。。。。。。。。。。。。。。。。。。。。。。。。");
			    // 根据同步的动作执行对应的"删除"
			    ownDriverService.deleteOwnDriver(ownDriver,
				    ComnConst.EXTERNAL_SYSTEM_LMS_USER_ACCOUNT);

			    LOGGER.info("作废司机信息结束。。。。。。。。。。。。。。。。。。。。。。。。。。");
			} else if (NumberConstants.NUMERAL_S_TWO
				.equalsIgnoreCase(driverInfo.getOperateType())) {
			    LOGGER.info("修改司机信息开始。。。。。。。。。。。。。。。。。。。。。。。。。。");
			    // 根据同步的动作执行对应的"修改"
			    ownDriverService.updateOwnDriver(ownDriver,
				    ComnConst.EXTERNAL_SYSTEM_LMS_USER_ACCOUNT,
				    true);

			    LOGGER.info("修改司机信息结束。。。。。。。。。。。。。。。。。。。。。。。。。。");
			} else {
			    throw new OwnDriverException(
				    DataRuleMessageConstant.DATA_RULE_OPERATE_ERROR);
			}
			LOGGER.info("开始解锁：" + mutex.getBusinessNo());
			// 解业务锁
			businessLockService.unlock(mutex);
			LOGGER.info("完成解锁：" + mutex.getBusinessNo());
		    } else {
			detailList.add(this.recordSynchronizedRequestDataError(
				driverInfo, false, "出现了高并发操作！"));
			LOGGER.info("失败加锁：" + mutex.getBusinessNo());
			failCount++;
			continue;
		    }
		    successCount++;
		    detailList.add(this.recordSynchronizedRequestDataError(
			    driverInfo, true, null));
		} catch (Exception e) {
		    failCount++;
		    detailList.add(this.recordSynchronizedRequestDataError(
			    driverInfo, false, e.getMessage()));
		    LOGGER.error(e.getMessage(), e);
		    continue;
		}
	    }
	    driverSyncResponse.setSuccessCount(successCount);
	    driverSyncResponse.setFailCount(failCount);

	    LOGGER.info(new DriverSyncResponseTrans()
		    .fromMessage(driverSyncResponse));
	}

	LOGGER.info(" ***************************** End to record data ***************************** ");
	return driverSyncResponse;
    }

    @Override
    public Object errorHandler(Object req) throws ESBBusinessException {
	LOGGER.error("ESB处理错误");
	return null;
    }

    /**
     * <p>
     * 验证当前的LMS"司机信息"信息是否已经存在FOSS中
     * </p>
     * 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-21 下午6:50:00
     * @param tractor
     *            LMS"司机信息"信息参数实体
     * @return FOSS"司机信息"信息实体
     * @see
     */
    private OwnDriverEntity converterLMSDataObjectToFOSSObjectData(
	    DriverInfo driverInfo) {
	if (null == driverInfo || StringUtils.isBlank(driverInfo.getDriverNo())) {
	    return null;
	}
	OwnDriverEntity ownDriver = new OwnDriverEntity();
	ownDriver.setEmpCode(driverInfo.getDriverNo());
	List<OwnDriverEntity> ownDriverList = ownDriverService
		.queryOwnDriverListBySelectiveCondition(ownDriver, 0, 1);
	if (CollectionUtils.isEmpty(ownDriverList)) {
	    return null;
	}
	return ownDriverList.get(0);
    }

    /**
     * <p>
     * 转换从LMS同步到FOSS中不匹配的数据
     * </p>
     * 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-21 下午3:42:02
     * @param ownTruck
     *            FOSS中待存储的"司机信息"信息对象
     * @param tractor
     *            请求的"司机信息"序列化对象
     * @see
     */
    private void converterLMSDataObjectToFOSSObjectData(
	    OwnDriverEntity ownDriver, DriverInfo driverInfo) {
	// "单双桥"数据类型转换
	Integer licenseType = Integer.valueOf(driverInfo.getLicenseType());
	switch (licenseType.intValue()) {
	case NumberConstants.NUMBER_1:
	    ownDriver.setLicenseType("A1");
	    break;
	case NumberConstants.NUMBER_2:
	    ownDriver.setLicenseType("A2");
	    break;
	case NumberConstants.NUMBER_3:
	    ownDriver.setLicenseType("A3");
	    break;
	case NumberConstants.NUMBER_4:
	    ownDriver.setLicenseType("B1");
	    break;
	case NumberConstants.NUMBER_5:
	    ownDriver.setLicenseType("B2");
	    break;
	default:
	    break;
	}
    }

    /**
     * <p>
     * 对同步的LMS对象的做成功和失败的记录
     * </p>
     * 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-21 下午3:42:13
     * @param tractor
     *            请求的"司机信息"序列化对象
     * @param result
     *            0表示失败，1表示成功
     * @param reason
     *            失败的原因
     * @return
     * @see
     */
    private DriverSyncProcessDetail recordSynchronizedRequestDataError(
	    DriverInfo driverInfo, boolean result, String reason) {
	DriverSyncProcessDetail driverSyncProcessDetail = new DriverSyncProcessDetail();
	driverSyncProcessDetail.setDriverNo(driverInfo.getDriverNo());
	driverSyncProcessDetail.setOperateType(driverInfo.getOperateType());
	// 判断操作是否失败，失败需要强制增加原因
	if (!result) {
	    driverSyncProcessDetail.setReason(reason);
	}
	driverSyncProcessDetail.setResult(result);
	return driverSyncProcessDetail;
    }

    /**
     * @param ownDriverService
     *            the ownDriverService to set
     */
    public void setOwnDriverService(IOwnDriverService ownDriverService) {
	this.ownDriverService = ownDriverService;
    }

    /**
     * @param orgAdministrativeInfoService
     *            the orgAdministrativeInfoService to set
     */
    public void setOrgAdministrativeInfoService(
	    IOrgAdministrativeInfoService orgAdministrativeInfoService) {
	this.orgAdministrativeInfoService = orgAdministrativeInfoService;
    }
}
