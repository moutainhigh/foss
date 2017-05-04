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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/lmsitf/esb/server/TrailerResultNotificationProcessor.java
 * 
 * FILE NAME        	: TrailerResultNotificationProcessor.java
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
import com.deppon.esb.inteface.domain.vehicle.TrailerInfo;
import com.deppon.esb.inteface.domain.vehicle.TrailerSyncProcessDetail;
import com.deppon.esb.inteface.domain.vehicle.TrailerSyncRequest;
import com.deppon.esb.inteface.domain.vehicle.TrailerSyncResponse;
import com.deppon.esb.pojo.transformer.jaxb.TrailerSyncRequestTrans;
import com.deppon.esb.pojo.transformer.jaxb.TrailerSyncResponseTrans;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleTypeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.OwnTrailerException;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.lmsitf.esb.service.ISynchronousOwnVehicleService;
import com.deppon.foss.module.base.lmsitf.esb.util.DataRuleMessageConstant;
import com.deppon.foss.util.NumberUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 用来从LMS同步公司“挂车信息”到FOSS数据库：无SUC
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-21 下午5:27:22</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-21 下午5:27:22
 * @since
 * @version
 */
public class TrailerResultNotificationProcessor implements IProcess {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrailerResultNotificationProcessor.class);
    
    //同步"公司车辆（厢式车、挂车、拖头）"接口结果操作Service
    private ISynchronousOwnVehicleService synchronousOwnVehicleService;
    
    //同步"部门信息"接口结果操作Service
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;
    
    //"车型车长数据"Service
    private ILeasedVehicleTypeService leasedVehicleTypeService;
    
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
	
	LOGGER.info(" ***************************** 同步”挂车信息”开始 ***************************** ");
	
	TrailerSyncRequest trailerSyncRequest = (TrailerSyncRequest) req;
	TrailerSyncResponse trailerSyncResponse = new TrailerSyncResponse();
	
	LOGGER.info(new TrailerSyncRequestTrans().fromMessage(trailerSyncRequest));
	
	if (null != trailerSyncRequest) {
	    int successCount = 0, failCount  = 0;
	    List<TrailerSyncProcessDetail> detailList = trailerSyncResponse.getDetail();
	    for (TrailerInfo trailer : trailerSyncRequest.getTrailerList()) {
		//FOSS"挂车"信息初始化
		OwnTruckEntity ownTruck = null;
		try {
		    ownTruck = this.converterLMSDataObjectToFOSSObjectData(trailer);
		    if(NumberConstants.NUMERAL_S_ONE.equalsIgnoreCase(trailer.getOperateType())){
			if(null != ownTruck){
			    throw new OwnTrailerException(DataRuleMessageConstant.DATA_RULE_REASON_OBJECTISNOTNULL_ERROR);
			}
		    }
		    if(null == ownTruck){
			ownTruck = new OwnTruckEntity();
		    }
		    //数据转换封装和数据库操作
		    if(StringUtils.isNotBlank(trailer.getCarNo())){
			ownTruck.setVehicleNo(trailer.getCarNo().trim());
		    }else {
			ownTruck.setVehicleNo(trailer.getCarNo());
		    }
		    ownTruck.setVersionNo(trailer.getVersion());
		    ownTruck.setBrand(trailer.getCarBrand());
		    
		    //转行“部门标杆编码”到“部门编码”
		    if (StringUtils.isNotBlank(trailer.getDeptNumber())) {
			OrgAdministrativeInfoEntity orgAdministrativeInfo = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByUnifiedCode(trailer.getDeptNumber());
			if (null != orgAdministrativeInfo) {
			    ownTruck.setOrgId(orgAdministrativeInfo.getCode());
			}
		    }
		    //"车型车长处理"
		    if(null != leasedVehicleTypeService.queryLeasedVehicleTypeByVehicleLengthCode(trailer.getLengthNo())){
			ownTruck.setVehcleLengthCode(trailer.getLengthNo());
		    }
		    
		    ownTruck.setVehicleType(DictionaryValueConstants.VEHICLE_TYPE_TRAILER);
		    ownTruck.setTrailerType(trailer.getTrailerType());
		    ownTruck.setContainerCode(trailer.getContainerNumber());
		    ownTruck.setSelfVolume(NumberUtils.createBigDecimal(String.valueOf(trailer.getVolumn())));
		    ownTruck.setConsumeFuel(NumberUtils.createBigDecimal(String.valueOf(trailer.getOilCost())));
		    ownTruck.setSelfWeight(NumberUtils.createBigDecimal(String.valueOf(trailer.getWeight())));
		    ownTruck.setVehicleDeadLoad(NumberUtils.createBigDecimal(String.valueOf(trailer.getRealWeight())));
		    ownTruck.setDeadLoad(NumberUtils.createBigDecimal(String.valueOf(trailer.getRatedWeight())));
		    ownTruck.setTankVolume(NumberUtils.createBigDecimal(String.valueOf(trailer.getOilTankVolume())));
		    ownTruck.setVehicleLength(NumberUtils.createBigDecimal(String.valueOf(trailer.getLength())));
		    ownTruck.setVehicleWidth(NumberUtils.createBigDecimal(String.valueOf(trailer.getWidth())));
		    ownTruck.setVehicleHeight(NumberUtils.createBigDecimal(String.valueOf(trailer.getHight())));
		    /*
		     * 数据类型转换：
		     * 1、是否气囊柜
		     * 2、车辆状态
		     * 3、车辆使用类型
		     */
		    this.converterLMSDataObjectToFOSSObjectData(ownTruck, trailer);
		    /*
		     * 根据系统集成组设计检测是否选填：
		     * 1、停车计划开始日期
		     * 2、停车计划结束日期
		     * 3、停车原因代码
		     */
		    String ruleReason = this.validationSynchronizedRequestDataRule(ownTruck, trailer);
		    if(StringUtils.isNotBlank(ruleReason)){
			throw new OwnTrailerException(ruleReason);
		    }
		    // 业务锁
			MutexElement mutex = new MutexElement(trailer.getCarNo(), "TRAILER_VERSIONNO",
				    MutexElementType.TRAILER_VERSIONNO);
			LOGGER.info("开始加锁：" + mutex.getBusinessNo());
			boolean result = businessLockService.lock(mutex, NumberConstants.ZERO);
			if (result) {
			    LOGGER.info("成功加锁：" + mutex.getBusinessNo());
		    if (NumberConstants.NUMERAL_S_ONE.equalsIgnoreCase(trailer.getOperateType())) {
			LOGGER.info("新增挂车信息开始。。。。。。。。。。。。。。。。。。。");
			//根据同步的动作执行对应的"新增"
			synchronousOwnVehicleService.addOwnVehicle(ownTruck);
			
			LOGGER.info("新增挂车信息结束。。。。。。。。。。。。。。。。。。。");
			
		    }else if (NumberConstants.NUMERAL_S_THREE.equalsIgnoreCase(trailer.getOperateType())) {
			
			LOGGER.info("作废挂车信息开始。。。。。。。。。。。。。。。。。。。");
			//根据同步的动作执行对应的"删除"
			synchronousOwnVehicleService.deleteOwnVehicle(ownTruck);
			
			LOGGER.info("作废挂车信息结束。。。。。。。。。。。。。。。。。。。");
		    }else if (NumberConstants.NUMERAL_S_TWO.equalsIgnoreCase(trailer.getOperateType())) {
			LOGGER.info("修改挂车信息开始。。。。。。。。。。。。。。。。。。。");
			//根据同步的动作执行对应的"修改"
			synchronousOwnVehicleService.updateOwnVehicle(ownTruck);
			
			LOGGER.info("修改挂车信息结束。。。。。。。。。。。。。。。。。。。");
		    }else{
			throw new OwnTrailerException(DataRuleMessageConstant.DATA_RULE_OPERATE_ERROR);
		    }
		    LOGGER.info("开始解锁：" + mutex.getBusinessNo());
			// 解业务锁
			businessLockService.unlock(mutex);
			LOGGER.info("完成解锁：" + mutex.getBusinessNo());
			} else {
				detailList.add(this.recordSynchronizedRequestDataError(
						trailer, false, "出现了高并发操作！"));
			    LOGGER.info("失败加锁：" + mutex.getBusinessNo());
			    failCount++;
			    continue;
			}
		    successCount ++;
		    detailList.add(this.recordSynchronizedRequestDataError(trailer, true, null));
		} catch (Exception e) {
		    failCount ++;
		    detailList.add(this.recordSynchronizedRequestDataError(trailer, false, e.getMessage()));
		    LOGGER.error(e.getMessage(), e);
		    continue; 
		}
	    }
	    trailerSyncResponse.setSuccessCount(successCount);
	    trailerSyncResponse.setFailCount(failCount);
	    
	    LOGGER.info(new TrailerSyncResponseTrans().fromMessage(trailerSyncResponse));
	}

	LOGGER.info(" ***************************** 同步”挂车信息”结束 ***************************** ");
	return trailerSyncResponse;
    }

    @Override
    public Object errorHandler(Object req) throws ESBBusinessException {
	LOGGER.error("ESB处理错误");
	return null;
    }
    
    /**
     * <p>验证当前的LMS"挂车"信息是否已经存在FOSS中</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-22 上午11:12:34
     * @param trailer LMS"挂车"信息参数实体
     * @return FOSS"挂车"信息实体
     * @see
     */
    private OwnTruckEntity converterLMSDataObjectToFOSSObjectData(TrailerInfo trailer){
	if (null == trailer || StringUtils.isBlank(trailer.getCarNo())) {
	    return null;
	}
	OwnTruckEntity ownTruck = new OwnTruckEntity();
	ownTruck.setVehicleNo(trailer.getCarNo());
//	ownTruck.setVehicleType(DictionaryValueConstants.VEHICLE_TYPE_TRAILER);
	return synchronousOwnVehicleService.queryOwnVehicleBySelective(ownTruck);
    }
    
    /**
     * <p>转换从LMS同步到FOSS中不匹配的数据</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-22 上午11:12:37
     * @param ownTruck FOSS中待存储的"挂车"信息对象
     * @param trailer 请求的"挂车"序列化对象
     * @see
     */
    private void converterLMSDataObjectToFOSSObjectData(OwnTruckEntity ownTruck, TrailerInfo trailer){
	//是否气囊柜
	if (NumberConstants.NUMERAL_ZORE == trailer.getIsBalloonContainer()) {
	    ownTruck.setIsBallon(FossConstants.INACTIVE);
	}
	if (NumberConstants.NUMERAL_ONE == trailer.getIsBalloonContainer()) {
	    ownTruck.setIsBallon(FossConstants.ACTIVE);
	}
	
	//"车辆状态"数据类型转换
	if (NumberConstants.NUMERAL_S_ZORE.equalsIgnoreCase(trailer.getCarState())) {
	    ownTruck.setStatus(FossConstants.INACTIVE);
	}
	if (NumberConstants.NUMERAL_S_ONE.equalsIgnoreCase(trailer.getCarState())) {
	    ownTruck.setStatus(FossConstants.ACTIVE);
	}
	
	//"车辆使用类型"数据类型转换
	if (NumberConstants.NUMERAL_S_ZERE.equalsIgnoreCase(trailer.getCarUsedType())) {
	    ownTruck.setUsedType(DictionaryValueConstants.BES_VEHICLE_USED_TYPE_SERVICE);
	}else if (NumberConstants.NUMERAL_S_ONE.equalsIgnoreCase(trailer.getCarUsedType())) {
	    ownTruck.setUsedType(DictionaryValueConstants.BES_VEHICLE_USED_TYPE_PKP);
	}else if(NumberConstants.NUMERAL_S_TWO.equalsIgnoreCase(trailer.getCarUsedType())){
	    ownTruck.setUsedType(DictionaryValueConstants.BES_VEHICLE_USED_TYPE_BUS);
	}else if(NumberConstants.NUMERAL_S_THREE.equalsIgnoreCase(trailer.getCarUsedType())){
	    ownTruck.setUsedType(DictionaryValueConstants.BES_VEHICLE_USED_TYPE_COACH);
	}
    }
    
    /**
     * 
     * <p>验证从LMS同步的"挂车"信息中数据规则</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-22 上午11:12:41
     * @param ownTruck FOSS中待存储的"挂车"信息对象
     * @param trailer 请求的"挂车"序列化对象
     * @return
     * @see
     */
    private String validationSynchronizedRequestDataRule(OwnTruckEntity ownTruck, TrailerInfo trailer){
	StringBuffer ruleReason = new StringBuffer("");
	/*
	 * 根据系统集成组设计的规则要求：
	 * 停车计划开始日期、停车计划结束日期、停车原因代码,若“车辆状态”为不可用，则字段必填
	 */
	if(NumberConstants.NUMERAL_S_ZORE.equalsIgnoreCase(trailer.getCarState())){
	    if (null == trailer.getPlanBeginTime()){
		ruleReason.append(DataRuleMessageConstant.DATA_RULE_REASON_PLANBEGINTIME_ERROR);
	    } else if (null == trailer.getPlanEndTime()) {
		ruleReason.append(DataRuleMessageConstant.DATA_RULE_REASON_PLANENDTIME_ERROR);
	    } else if (null == trailer.getReason()) {
		ruleReason.append(DataRuleMessageConstant.DATA_RULE_REASON_PLANREASONCODE_ERROR);
	    } else {
		ownTruck.setBeginDate(trailer.getPlanBeginTime());
		ownTruck.setEndDate(trailer.getPlanEndTime());
		ownTruck.setUnavilableCode(trailer.getReason());
	    }
	}else{
	    ownTruck.setUnavilableCode(null);
	    ownTruck.setBeginDate(null);
	    ownTruck.setEndDate(null);
	}
	return ruleReason.toString();
    }
    
    /**
     * <p>对同步的LMS对象的做成功和失败的记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-22 上午10:00:10
     * @param tractor 请求的"挂车"序列化对象
     * @param result false表示失败，true表示成功
     * @param reason 失败的原因
     * @return
     * @see
     */
    private TrailerSyncProcessDetail recordSynchronizedRequestDataError(TrailerInfo trailer, boolean result, String reason){
	TrailerSyncProcessDetail trailerSyncProcessDetail = new TrailerSyncProcessDetail();
	trailerSyncProcessDetail.setCarNumber(trailer.getCarNo());
	trailerSyncProcessDetail.setOperateType(trailer.getOperateType());
	trailerSyncProcessDetail.setVersion(trailer.getVersion());
	//判断操作是否失败，失败需要强制增加原因
	if (!result) {
	    trailerSyncProcessDetail.setReason(reason);
	}
	trailerSyncProcessDetail.setResult(result);
	return trailerSyncProcessDetail;
    }

    /**
     * @param synchronousOwnVehicleService the synchronousOwnVehicleService to set
     */
    public void setSynchronousOwnVehicleService(
    	ISynchronousOwnVehicleService synchronousOwnVehicleService) {
        this.synchronousOwnVehicleService = synchronousOwnVehicleService;
    }

    /**
     * @param orgAdministrativeInfoService the orgAdministrativeInfoService to set
     */
    public void setOrgAdministrativeInfoService(
    	IOrgAdministrativeInfoService orgAdministrativeInfoService) {
        this.orgAdministrativeInfoService = orgAdministrativeInfoService;
    }

    /**
     * @param leasedVehicleTypeService the leasedVehicleTypeService to set
     */
    public void setLeasedVehicleTypeService(
    	ILeasedVehicleTypeService leasedVehicleTypeService) {
        this.leasedVehicleTypeService = leasedVehicleTypeService;
    }
}
