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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/lmsitf/esb/server/TrailerTypeResultNotificationProcessor.java
 * 
 * FILE NAME        	: TrailerTypeResultNotificationProcessor.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.lmsitf.esb.server;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.inteface.domain.vehicle.TrailerTypeInfo;
import com.deppon.esb.inteface.domain.vehicle.TrailerTypeSyncProcessDetail;
import com.deppon.esb.inteface.domain.vehicle.TrailerTypeSyncRequest;
import com.deppon.esb.inteface.domain.vehicle.TrailerTypeSyncResponse;
import com.deppon.esb.pojo.transformer.jaxb.TrailerTypeSyncRequestTrans;
import com.deppon.esb.pojo.transformer.jaxb.TrailerTypeSyncResponseTrans;
import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.VehicleBrandException;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.base.dict.api.shared.exception.DataDictionaryValueException;
import com.deppon.foss.module.base.lmsitf.esb.util.DataRuleMessageConstant;
/**
 * 用来从LMS同步公司“挂车类型”到FOSS数据库数据字典：无SUC
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-22 下午4:14:14</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-22 下午4:14:14
 * @since
 * @version
 */
public class TrailerTypeResultNotificationProcessor implements IProcess {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrailerTypeResultNotificationProcessor.class);
    
    //同步"挂车类型"接口结果操作Service
    private IDataDictionaryValueService dataDictionaryValueService;
    
    /**
     * 业务互斥锁服务.
     */
    private IBusinessLockService businessLockService;
    
    /**
     * @param businessLockService the businessLockService to set
     */
    public void setBusinessLockService(IBusinessLockService businessLockService) {
        this.businessLockService = businessLockService;
    }

    
    @Override
    public Object process(Object req) throws ESBBusinessException {
	LOGGER.info(" ***************************** 同步挂车类型开始 ***************************** ");
	
	TrailerTypeSyncRequest trailerTypeSyncRequest = (TrailerTypeSyncRequest) req;
	TrailerTypeSyncResponse trailerTypeSyncResponse = new TrailerTypeSyncResponse();
	
	LOGGER.info(new TrailerTypeSyncRequestTrans().fromMessage(trailerTypeSyncRequest));
	
	if (null != trailerTypeSyncRequest) {
	    int successCount = 0, failCount  = 0, index = 0;
	    List<TrailerTypeSyncProcessDetail> detailList = trailerTypeSyncResponse.getDetail();
	    for (TrailerTypeInfo trailerTypeInfo : trailerTypeSyncRequest.getTrailerTypeList()) {
		//FOSS"挂车类型"信息初始化
		DataDictionaryValueEntity dataDictionaryValue = null;
		try {
		    dataDictionaryValue = this.converterLMSDataObjectToFOSSObjectData(trailerTypeInfo);
		    if(NumberConstants.NUMERAL_S_ONE.equalsIgnoreCase(trailerTypeInfo.getOperateType())){
			if(null == dataDictionaryValue){
			    dataDictionaryValue = new DataDictionaryValueEntity();
			}else{
			    throw new VehicleBrandException(DataRuleMessageConstant.DATA_RULE_REASON_OBJECTISNOTNULL_ERROR);
			}
		    }else{
			if(null == dataDictionaryValue){
			    throw new VehicleBrandException(DataRuleMessageConstant.DATA_RULE_REASON_OBJECTISNULL_ERROR);
			}
		    }
		    //数据转换与封装
		    dataDictionaryValue.setTermsCode(DictionaryConstants.LMS_TRAILER_TYPE_TERMSCODE);
		    if(StringUtils.isNotBlank(trailerTypeInfo.getTrailerTypeNo())){
			//清除空格
			dataDictionaryValue.setValueCode(trailerTypeInfo.getTrailerTypeNo().trim());
		    }else {
			dataDictionaryValue.setValueCode(trailerTypeInfo.getTrailerTypeNo());
		    }
		    
		    dataDictionaryValue.setValueName(trailerTypeInfo.getCarType());
		    dataDictionaryValue.setValueSeq(String.valueOf(Long.valueOf(System.currentTimeMillis()).shortValue() + ++index));
		    // 业务锁
			MutexElement mutex = new MutexElement(trailerTypeInfo.getTrailerTypeNo(), "TRAILER_TYPENO",
				    MutexElementType.TRAILER_TYPENO);
			LOGGER.info("开始加锁：" + mutex.getBusinessNo());
			boolean result = businessLockService.lock(mutex, NumberConstants.ZERO);
			if (result) {
			    LOGGER.info("成功加锁：" + mutex.getBusinessNo());
		    if (NumberConstants.NUMERAL_S_ONE.equalsIgnoreCase(trailerTypeInfo.getOperateType())) {
			LOGGER.info("新增挂车类型开始。。。。。。。。。。。。。。。。。。。。。。。。");
			//根据同步的动作执行对应的"新增"
			dataDictionaryValue.setCreateUser(ComnConst.EXTERNAL_SYSTEM_LMS_USER_ACCOUNT);
			dataDictionaryValueService.addDataDictionaryValue(dataDictionaryValue);
			LOGGER.info("新增挂车类型结束。。。。。。。。。。。。。。。。。。。。。。。。");
			
		    }else if (NumberConstants.NUMERAL_S_THREE.equalsIgnoreCase(trailerTypeInfo.getOperateType())) {
			LOGGER.info("作废挂车类型开始。。。。。。。。。。。。。。。。。。。。。。。。");
			//根据同步的动作执行对应的"删除"
			dataDictionaryValueService
				.deleteDataDictionaryValue(dataDictionaryValue);
			LOGGER.info("作废挂车类型结束。。。。。。。。。。。。。。。。。。。。。。。。");
		    }else if (NumberConstants.NUMERAL_S_TWO.equalsIgnoreCase(trailerTypeInfo.getOperateType())) {
			
			LOGGER.info("修改挂车类型开始。。。。。。。。。。。。。。。。。。。。。。。。");
			//根据同步的动作执行对应的"修改"
			dataDictionaryValue.setModifyUser(ComnConst.EXTERNAL_SYSTEM_LMS_USER_ACCOUNT);
			dataDictionaryValueService
				.updateDataDictionaryValue(dataDictionaryValue);
			
			LOGGER.info("修改挂车类型结束。。。。。。。。。。。。。。。。。。。。。。。。");
		    }else{
			throw new DataDictionaryValueException(DataRuleMessageConstant.DATA_RULE_OPERATE_ERROR);
		    }
		    LOGGER.info("开始解锁：" + mutex.getBusinessNo());
			// 解业务锁
			businessLockService.unlock(mutex);
			LOGGER.info("完成解锁：" + mutex.getBusinessNo());
			} else {
				detailList.add(this.recordSynchronizedRequestDataError(
						trailerTypeInfo, false, "出现了高并发操作！"));
			    LOGGER.info("失败加锁：" + mutex.getBusinessNo());
			    failCount++;
			    continue;
			}
		    successCount ++;
		    detailList.add(this.recordSynchronizedRequestDataError(trailerTypeInfo, true, null));
		} catch (Exception e) {
		    failCount ++;
		    detailList.add(this.recordSynchronizedRequestDataError(trailerTypeInfo, false, e.getMessage()));
		    LOGGER.error(e.getMessage(), e);
		    continue; 
		}
	    }
	    trailerTypeSyncResponse.setSuccessCount(successCount);
	    trailerTypeSyncResponse.setFailCount(failCount);
	    
	    LOGGER.info(new TrailerTypeSyncResponseTrans().fromMessage(trailerTypeSyncResponse));
	}
	
	LOGGER.info(" ***************************** 同步挂车类型结束***************************** ");
	return trailerTypeSyncResponse;
    }

    @Override
    public Object errorHandler(Object req) throws ESBBusinessException {
	LOGGER.error("ESB处理错误");
	return null;
    }
    
    /**
     * <p>验证当前的LMS"挂车类型"信息是否已经存在FOSS中</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-21 下午6:50:00
     * @param tractor LMS"挂车类型"信息参数实体
     * @return FOSS"挂车类型"信息实体
     * @see
     */
    private DataDictionaryValueEntity converterLMSDataObjectToFOSSObjectData(TrailerTypeInfo trailerTypeInfo){
	if (null == trailerTypeInfo || StringUtils.isBlank(trailerTypeInfo.getTrailerTypeNo().toString())) {
	    return null;
	}
	
	String termsCode = DictionaryConstants.LMS_TRAILER_TYPE_TERMSCODE;
	String valueCode = trailerTypeInfo.getTrailerTypeNo().toString();
	DataDictionaryValueEntity dataDictionaryValue = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCodeNoCache(termsCode, valueCode);
	return dataDictionaryValue;
    }
    
    /**
     * <p>对同步的LMS对象的做成功和失败的记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-21 下午3:42:13
     * @param tractor 请求的"挂车类型"序列化对象
     * @param result 0表示失败，1表示成功
     * @param reason 失败的原因
     * @return
     * @see
     */
    private TrailerTypeSyncProcessDetail recordSynchronizedRequestDataError(TrailerTypeInfo trailerTypeInfo, boolean result, String reason){
	TrailerTypeSyncProcessDetail trailerTypeSyncProcessDetail = new TrailerTypeSyncProcessDetail();
	trailerTypeSyncProcessDetail.setTrailerTypeNo(trailerTypeInfo.getTrailerTypeNo());
	trailerTypeSyncProcessDetail.setOperateType(trailerTypeInfo.getOperateType());
	
	//判断操作是否失败，失败需要强制增加原因
	if (!result) {
	    trailerTypeSyncProcessDetail.setReason(reason);
	}
	trailerTypeSyncProcessDetail.setResult(result);
	return trailerTypeSyncProcessDetail;
    }
    
    /**
     * @param dataDictionaryValueService the dataDictionaryValueService to set
     */
    public void setDataDictionaryValueService(
    	IDataDictionaryValueService dataDictionaryValueService) {
        this.dataDictionaryValueService = dataDictionaryValueService;
    }
}
