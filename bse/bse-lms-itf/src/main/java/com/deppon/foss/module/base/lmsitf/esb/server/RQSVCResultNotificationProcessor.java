/*******************************************************************************
 * Copyright 2014 BSE TEAM
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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/lmsitf/esb/server/RQSVCResultNotificationProcessor.java
 * 
 * FILE NAME        	: RQSVCResultNotificationProcessor.java
 * 
 * AUTHOR			: FOSS综合开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2014  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.lmsitf.esb.server;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.esb.inteface.domain.vehicle.RQSVCInfo;
import com.deppon.esb.inteface.domain.vehicle.RQSVCSyncProcessDetail;
import com.deppon.esb.inteface.domain.vehicle.RQSVCSyncRequest;
import com.deppon.esb.inteface.domain.vehicle.RQSVCSyncResponse;
import com.deppon.esb.pojo.transformer.jaxb.RQSVCSyncRequestTrans;
import com.deppon.esb.pojo.transformer.jaxb.RQSVCSyncResponseTrans;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleTypeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.OwnRQSVCException;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.lmsitf.esb.service.ISynchronousOwnVehicleService;
import com.deppon.foss.module.base.lmsitf.esb.util.DataRuleMessageConstant;
import com.deppon.foss.util.NumberUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 用来从LMS同步公司“骨架车信息”到FOSS数据库：无SUC
 * @author 187862-dujunhui
 * @date 2014-06-10 下午2:57:55
 * @since
 * @version
 */
public class RQSVCResultNotificationProcessor implements IProcess {

    private static final Logger LOGGER = LoggerFactory.getLogger(RQSVCResultNotificationProcessor.class);
    
    //同步"公司车辆（厢式车、挂车、拖头、骨架车）"接口结果操作Service
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
	
	LOGGER.info(" ***************************** 同步骨架车开始 ***************************** ");
	
	RQSVCSyncRequest rSyncRequest = (RQSVCSyncRequest) req;
	RQSVCSyncResponse rSyncResponse = new RQSVCSyncResponse();
	
	LOGGER.info(new RQSVCSyncRequestTrans().fromMessage(rSyncRequest));
	
	if (null != rSyncRequest) {
	    int successCount = 0, failCount  = 0;
	    List<RQSVCSyncProcessDetail> detailList = rSyncResponse.getDetail();
	    for (RQSVCInfo rInfo : rSyncRequest.getRQSVCList()) {
		//FOSS"骨架车"信息初始化
		OwnTruckEntity ownTruck = null;
		try {
		    ownTruck = this.converterLMSDataObjectToFOSSObjectData(rInfo);
		    if(NumberConstants.NUMERAL_S_ONE.equalsIgnoreCase(rInfo.getOperateType())){
			if(null != ownTruck){
			    throw new OwnRQSVCException(DataRuleMessageConstant.DATA_RULE_REASON_OBJECTISNOTNULL_ERROR);
			}
		    }
		    if(null == ownTruck){
			ownTruck = new OwnTruckEntity();
		    }
		    //数据转换封装和数据库操作
		    if(StringUtils.isNotBlank(rInfo.getCarNo())){
			//清除空格
			ownTruck.setVehicleNo(rInfo.getCarNo().trim());
		    }else {
			ownTruck.setVehicleNo(rInfo.getCarNo());
		    }
		    
		    ownTruck.setVersionNo(rInfo.getVersion());
		    ownTruck.setBrand(rInfo.getCarBrand());
		    
		    //转行“部门标杆编码”到“部门编码”
		    if (StringUtils.isNotBlank(rInfo.getDeptNumber())) {
			OrgAdministrativeInfoEntity orgAdministrativeInfo = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByUnifiedCode(rInfo.getDeptNumber());
			if (null != orgAdministrativeInfo) {
			    ownTruck.setOrgId(orgAdministrativeInfo.getCode()); 
			}
		    }
		    //"车型车长处理"
		    if(null != leasedVehicleTypeService.queryLeasedVehicleTypeByVehicleLengthCode(rInfo.getLengthNo())){
			ownTruck.setVehcleLengthCode(rInfo.getLengthNo());
		    }
		    
		    ownTruck.setVehicleType(DictionaryValueConstants.VEHICLE_TYPE_RQSVC);
		    ownTruck.setSelfVolume(NumberUtils.createBigDecimal(String.valueOf(rInfo.getVolume())));
		    ownTruck.setConsumeFuel(NumberUtils.createBigDecimal(String.valueOf(rInfo.getOilCost())));
		    ownTruck.setSelfWeight(NumberUtils.createBigDecimal(String.valueOf(rInfo.getWeight())));
		    ownTruck.setVehicleDeadLoad(NumberUtils.createBigDecimal(String.valueOf(rInfo.getRealWeight())));
		    ownTruck.setDeadLoad(NumberUtils.createBigDecimal(String.valueOf(rInfo.getRatedWeight())));
		    ownTruck.setVehicleLength(NumberUtils.createBigDecimal(String.valueOf(rInfo.getLenghth())));
		    ownTruck.setVehicleWidth(NumberUtils.createBigDecimal(String.valueOf(rInfo.getWidth())));
		    ownTruck.setVehicleHeight(NumberUtils.createBigDecimal(String.valueOf(rInfo.getHeight())));
		    /*
		     * 数据类型转换：
		     * 1、是否气囊柜
		     * 2、车辆状态
		     * 3、车辆使用类型
		     */
		    this.converterLMSDataObjectToFOSSObjectData(ownTruck, rInfo);
		    /*
		     * 根据系统集成组设计检测是否选填：
		     * 1、停车计划开始日期
		     * 2、停车计划结束日期
		     * 3、停车原因代码
		     */
		    String ruleReason = this.validationSynchronizedRequestDataRule(ownTruck, rInfo);
		    if(StringUtils.isNotBlank(ruleReason)){
			throw new OwnRQSVCException(ruleReason);
		    }
		    // 业务锁
			MutexElement mutex = new MutexElement(rInfo.getVersion(), "RQSVCINFO_VERSIONNO",
				    MutexElementType.RQSVCINFO_VERSIONNO);
			LOGGER.info("开始加锁：" + mutex.getBusinessNo());
			boolean result = businessLockService.lock(mutex, NumberConstants.ZERO);
			if (result) {
			    LOGGER.info("成功加锁：" + mutex.getBusinessNo());
						if (NumberConstants.NUMERAL_S_ONE
								.equalsIgnoreCase(rInfo.getOperateType())) {
							LOGGER.info("新增骨架车开始。。。。。。。。。。。。。。。。。。");
							// 根据同步的动作执行对应的"新增"
							synchronousOwnVehicleService
									.addOwnVehicle(ownTruck);
							LOGGER.info("新增骨架车结束。。。。。。。。。。。。。。。。。。");

						} else if (NumberConstants.NUMERAL_S_THREE
								.equalsIgnoreCase(rInfo.getOperateType())) {
							LOGGER.info("作废骨架车开始。。。。。。。。。。。。。。。。。。");
							// 根据同步的动作执行对应的"删除"
							synchronousOwnVehicleService
									.deleteOwnVehicle(ownTruck);

							LOGGER.info("作废骨架车结束。。。。。。。。。。。。。。。。。。");
						} else if (NumberConstants.NUMERAL_S_TWO
								.equalsIgnoreCase(rInfo.getOperateType())) {
							LOGGER.info("修改骨架车开始。。。。。。。。。。。。。。。。。。");
							// 根据同步的动作执行对应的"修改"
							synchronousOwnVehicleService
									.updateOwnVehicle(ownTruck);
							LOGGER.info("修改骨架车结束。。。。。。。。。。。。。。。。。。");
						} else {
							throw new OwnRQSVCException(
									DataRuleMessageConstant.DATA_RULE_OPERATE_ERROR);
						}
						LOGGER.info("开始解锁：" + mutex.getBusinessNo());
						// 解业务锁
						businessLockService.unlock(mutex);
						LOGGER.info("完成解锁：" + mutex.getBusinessNo());
					} else {
						detailList.add(this.recordSynchronizedRequestDataError(
								rInfo, false, "出现了高并发操作！"));
						LOGGER.info("失败加锁：" + mutex.getBusinessNo());
						failCount++;
						continue;
					}
					successCount++;
					detailList.add(this.recordSynchronizedRequestDataError(
							rInfo, true, null));
				} catch (Exception e) {
					failCount++;
					detailList.add(this.recordSynchronizedRequestDataError(
							rInfo, false, e.getMessage()));
					LOGGER.error(e.getMessage(), e);
					continue;
				}
			}
	    rSyncResponse.setSuccessCount(successCount);
	    rSyncResponse.setFailCount(failCount);
	    
	    LOGGER.info(new RQSVCSyncResponseTrans().fromMessage(rSyncResponse));
	}
	
	LOGGER.info(" ***************************** 同步骨架车结束***************************** ");
	return rSyncResponse;
    }

    @Override
    public Object errorHandler(Object req) throws ESBBusinessException {
    	LOGGER.error("ESB处理错误");
	return null;
    }

    /**
     * <p>验证当前的LMS"骨架车"信息是否已经存在FOSS中</p> 
     * @author 187862-dujunhui
     * @date 2014-06-10 下午3:31:22
     * @param tractor LMS"骨架车"信息参数实体
     * @return FOSS"骨架车"信息实体
     * @see
     */
    private OwnTruckEntity converterLMSDataObjectToFOSSObjectData(RQSVCInfo rInfo){
	if (null == rInfo || StringUtils.isBlank(rInfo.getCarNo())) {
	    return null;
	}
	OwnTruckEntity ownTruck = new OwnTruckEntity();
	ownTruck.setVehicleNo(rInfo.getCarNo());
	ownTruck.setVehicleType(DictionaryValueConstants.VEHICLE_TYPE_RQSVC);
	return synchronousOwnVehicleService.queryOwnVehicleBySelective(ownTruck);
    }
    
    /**
     * <p>转换从LMS同步到FOSS中不匹配的数据</p> 
     * @author 187862-dujunhui
     * @date 2014-06-10 下午3:33:45
     * @param ownTruck FOSS中待存储的"骨架车"信息对象
     * @param tractor 请求的"骨架车"序列化对象
     * @see
     */
    private void converterLMSDataObjectToFOSSObjectData(OwnTruckEntity ownTruck, RQSVCInfo rInfo){
	//"是否有尾板"数据类型转换
	if (NumberConstants.NUMERAL_ZORE == rInfo.getIsTailPlate()) {
	    ownTruck.setTailBoard(FossConstants.INACTIVE);
	}
	if(NumberConstants.NUMERAL_ONE == rInfo.getIsTailPlate()){
	    ownTruck.setTailBoard(FossConstants.ACTIVE);
	}
	
	//"GPS"数据类型转换
	if (NumberConstants.NUMERAL_ZORE == rInfo.getIsGPS()) {
	    ownTruck.setGps(FossConstants.INACTIVE);
	}
	if (NumberConstants.NUMERAL_ONE == rInfo.getIsGPS()) {
	    ownTruck.setGps(FossConstants.ACTIVE);
	}
	
	//"车辆状态"数据类型转换
	if (NumberConstants.NUMERAL_S_ZORE.equalsIgnoreCase(rInfo.getCarState())) {
	    ownTruck.setStatus(FossConstants.INACTIVE);
	}
	if (NumberConstants.NUMERAL_S_ONE.equalsIgnoreCase(rInfo.getCarState())) {
	    ownTruck.setStatus(FossConstants.ACTIVE);
	}
	
	//"车辆使用类型"数据类型转换
	if (NumberConstants.NUMERAL_S_ONE.equalsIgnoreCase(rInfo.getCarUsedType())) {
	    ownTruck.setUsedType(DictionaryValueConstants.BES_VEHICLE_USED_TYPE_PKP);
	}else if(NumberConstants.NUMERAL_S_TWO.equalsIgnoreCase(rInfo.getCarUsedType())){
	    ownTruck.setUsedType(DictionaryValueConstants.BES_VEHICLE_USED_TYPE_BUS);
	}else if(NumberConstants.NUMERAL_S_THREE.equalsIgnoreCase(rInfo.getCarUsedType())){
	    ownTruck.setUsedType(DictionaryValueConstants.BES_VEHICLE_USED_TYPE_SERVICE);
	}else if(NumberConstants.NUMERAL_S_FOUR.equalsIgnoreCase(rInfo.getCarUsedType())){
	    ownTruck.setUsedType(DictionaryValueConstants.BES_VEHICLE_USED_TYPE_COACH);
	}
    }
    
    /**
     * <p>验证从LMS同步的"骨架车"信息中数据规则</p> 
     * @author 187862-dujunhui
     * @date 2014-06-10 下午3:42:10
     * @param ownTruck FOSS中待存储的"骨架车"信息对象
     * @param tractor 请求的"骨架车"序列化对象
     * @return 错误原因描述
     * @see
     */
    private String validationSynchronizedRequestDataRule(OwnTruckEntity ownTruck, RQSVCInfo rInfo){
	StringBuffer ruleReason = new StringBuffer("");
	/*
	 * 根据系统集成组设计的规则要求：
	 * 停车计划开始日期、停车计划结束日期、停车原因代码,若“车辆状态”为不可用，则字段必填
	 */
	if(NumberConstants.NUMERAL_S_ZORE.equalsIgnoreCase(rInfo.getCarState())){
	    if (null == rInfo.getPlanBeginTime()){
		ruleReason.append(DataRuleMessageConstant.DATA_RULE_REASON_PLANBEGINTIME_ERROR);
	    } else if (null == rInfo.getPlanEndTime()) {
		ruleReason.append(DataRuleMessageConstant.DATA_RULE_REASON_PLANENDTIME_ERROR);
	    } else if (null == rInfo.getReason()) {
		ruleReason.append(DataRuleMessageConstant.DATA_RULE_REASON_PLANREASONCODE_ERROR);
	    } else {
		ownTruck.setUnavilableCode(rInfo.getReason());
		ownTruck.setBeginDate(rInfo.getPlanBeginTime());
		ownTruck.setEndDate(rInfo.getPlanEndTime());
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
     * @author 187862-dujunhui
     * @date 2014-06-10 下午3:45:16
     * @param tractor 请求的"骨架车"序列化对象
     * @param result 0表示失败，1表示成功
     * @param reason 失败的原因
     * @return
     * @see
     */
    private RQSVCSyncProcessDetail recordSynchronizedRequestDataError(RQSVCInfo rInfo, boolean result, String reason){
	RQSVCSyncProcessDetail rSyncProcessDetail = new RQSVCSyncProcessDetail();
	rSyncProcessDetail.setCarNo(rInfo.getCarNo());
	rSyncProcessDetail.setOperateType(rInfo.getOperateType());
	rSyncProcessDetail.setVersion(rInfo.getVersion());
	
	//判断操作是否失败，失败需要强制增加原因
	if (!result) {
	    rSyncProcessDetail.setReason(reason);
	}
	rSyncProcessDetail.setResult(result);
	return rSyncProcessDetail;
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
