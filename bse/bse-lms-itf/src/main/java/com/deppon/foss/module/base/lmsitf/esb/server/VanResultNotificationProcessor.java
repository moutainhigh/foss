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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/lmsitf/esb/server/VanResultNotificationProcessor.java
 * 
 * FILE NAME        	: VanResultNotificationProcessor.java
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
import com.deppon.esb.inteface.domain.vehicle.VanInfo;
import com.deppon.esb.inteface.domain.vehicle.VanSyncProcessDetail;
import com.deppon.esb.inteface.domain.vehicle.VanSyncRequest;
import com.deppon.esb.inteface.domain.vehicle.VanSyncResponse;
import com.deppon.esb.pojo.transformer.jaxb.VanSyncRequestTrans;
import com.deppon.esb.pojo.transformer.jaxb.VanSyncResponseTrans;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleTypeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.OwnVanException;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.lmsitf.esb.service.ISynchronousOwnVehicleService;
import com.deppon.foss.module.base.lmsitf.esb.util.DataRuleMessageConstant;
import com.deppon.foss.util.NumberUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 用来从LMS同步公司“厢式车信息”到FOSS数据库：无SUC
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-21 下午5:27:18</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-21 下午5:27:18
 * @since
 * @version
 */
public class VanResultNotificationProcessor implements IProcess {

    private static final Logger LOGGER = LoggerFactory.getLogger(VanResultNotificationProcessor.class);
    
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
	
	LOGGER.info(" ***************************** 同步厢式车开始 ***************************** ");
	
	VanSyncRequest vanSyncRequest = (VanSyncRequest) req;
	VanSyncResponse vanSyncResponse = new VanSyncResponse();
	
	LOGGER.info(new VanSyncRequestTrans().fromMessage(vanSyncRequest));
	
	if (null != vanSyncRequest) {
	    int successCount = 0, failCount  = 0;
	    List<VanSyncProcessDetail> detailList = vanSyncResponse.getDetail();
	    for (VanInfo vanInfo : vanSyncRequest.getVanList()) {
		//FOSS"厢式车"信息初始化
		OwnTruckEntity ownTruck = null;
		try {
		    ownTruck = this.converterLMSDataObjectToFOSSObjectData(vanInfo);
		    if(NumberConstants.NUMERAL_S_ONE.equalsIgnoreCase(vanInfo.getOperateType())){
			if(null != ownTruck){
			    throw new OwnVanException(DataRuleMessageConstant.DATA_RULE_REASON_OBJECTISNOTNULL_ERROR);
			}
		    }
		    if(null == ownTruck){
			ownTruck = new OwnTruckEntity();
		    }
		    //数据转换封装和数据库操作
		    if(StringUtils.isNotBlank(vanInfo.getCarNo())){
			//清除空格
			ownTruck.setVehicleNo(vanInfo.getCarNo().trim());
		    }else {
			ownTruck.setVehicleNo(vanInfo.getCarNo());
		    }
		    
		    ownTruck.setVersionNo(vanInfo.getVersion());
		    ownTruck.setBrand(vanInfo.getCarBrand());
		    
		    //转行“部门标杆编码”到“部门编码”
		    if (StringUtils.isNotBlank(vanInfo.getDeptNumber())) {
			OrgAdministrativeInfoEntity orgAdministrativeInfo = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByUnifiedCode(vanInfo.getDeptNumber());
			if (null != orgAdministrativeInfo) {
			    ownTruck.setOrgId(orgAdministrativeInfo.getCode()); 
			}
		    }
		    //"车型车长处理"
		    if(null != leasedVehicleTypeService.queryLeasedVehicleTypeByVehicleLengthCode(vanInfo.getLengthNo())){
			ownTruck.setVehcleLengthCode(vanInfo.getLengthNo());
		    }
		    
		    ownTruck.setVehicleType(DictionaryValueConstants.VEHICLE_TYPE_VAN);
		    ownTruck.setSelfVolume(NumberUtils.createBigDecimal(String.valueOf(vanInfo.getVolume())));
		    ownTruck.setConsumeFuel(NumberUtils.createBigDecimal(String.valueOf(vanInfo.getOilCost())));
		    ownTruck.setSelfWeight(NumberUtils.createBigDecimal(String.valueOf(vanInfo.getWeight())));
		    ownTruck.setVehicleDeadLoad(NumberUtils.createBigDecimal(String.valueOf(vanInfo.getRealWeight())));
		    ownTruck.setDeadLoad(NumberUtils.createBigDecimal(String.valueOf(vanInfo.getRatedWeight())));
		    ownTruck.setVehicleLength(NumberUtils.createBigDecimal(String.valueOf(vanInfo.getLenghth())));
		    ownTruck.setVehicleWidth(NumberUtils.createBigDecimal(String.valueOf(vanInfo.getWidth())));
		    ownTruck.setVehicleHeight(NumberUtils.createBigDecimal(String.valueOf(vanInfo.getHeight())));
		    /*
		     * 数据类型转换：
		     * 1、是否气囊柜
		     * 2、车辆状态
		     * 3、车辆使用类型
		     */
		    this.converterLMSDataObjectToFOSSObjectData(ownTruck, vanInfo);
		    /*
		     * 根据系统集成组设计检测是否选填：
		     * 1、停车计划开始日期
		     * 2、停车计划结束日期
		     * 3、停车原因代码
		     */
		    String ruleReason = this.validationSynchronizedRequestDataRule(ownTruck, vanInfo);
		    if(StringUtils.isNotBlank(ruleReason)){
			throw new OwnVanException(ruleReason);
		    }
		    // 业务锁
			MutexElement mutex = new MutexElement(vanInfo.getVersion(), "VANINFO_VERSIONNO",
				    MutexElementType.VANINFO_VERSIONNO);
			LOGGER.info("开始加锁：" + mutex.getBusinessNo());
			boolean result = businessLockService.lock(mutex, NumberConstants.ZERO);
			if (result) {
			    LOGGER.info("成功加锁：" + mutex.getBusinessNo());
		    if (NumberConstants.NUMERAL_S_ONE.equalsIgnoreCase(vanInfo.getOperateType())) {
			LOGGER.info("新增厢式车开始。。。。。。。。。。。。。。。。。。");
			//根据同步的动作执行对应的"新增"
			synchronousOwnVehicleService.addOwnVehicle(ownTruck);
			LOGGER.info("新增厢式车结束。。。。。。。。。。。。。。。。。。");
			
		    }else if (NumberConstants.NUMERAL_S_THREE.equalsIgnoreCase(vanInfo.getOperateType())) {
			LOGGER.info("作废厢式车开始。。。。。。。。。。。。。。。。。。");
			//根据同步的动作执行对应的"删除"
			synchronousOwnVehicleService.deleteOwnVehicle(ownTruck);
			
			LOGGER.info("作废厢式车结束。。。。。。。。。。。。。。。。。。");
		    }else if (NumberConstants.NUMERAL_S_TWO.equalsIgnoreCase(vanInfo.getOperateType())) {
			LOGGER.info("修改厢式车开始。。。。。。。。。。。。。。。。。。");
			//根据同步的动作执行对应的"修改"
			synchronousOwnVehicleService.updateOwnVehicle(ownTruck);
			LOGGER.info("修改厢式车结束。。。。。。。。。。。。。。。。。。");
		    }else{
			throw new OwnVanException(DataRuleMessageConstant.DATA_RULE_OPERATE_ERROR);
		    }
		    LOGGER.info("开始解锁：" + mutex.getBusinessNo());
			// 解业务锁
			businessLockService.unlock(mutex);
			LOGGER.info("完成解锁：" + mutex.getBusinessNo());
			} else {
				detailList.add(this.recordSynchronizedRequestDataError(
						vanInfo, false, "出现了高并发操作！"));
			    LOGGER.info("失败加锁：" + mutex.getBusinessNo());
			    failCount++;
			    continue;
			}
		    successCount ++;
		    detailList.add(this.recordSynchronizedRequestDataError(vanInfo, true, null));
		} catch (Exception e) {
		    failCount ++;
		    detailList.add(this.recordSynchronizedRequestDataError(vanInfo, false, e.getMessage()));
		    LOGGER.error(e.getMessage(), e);
		    continue; 
		}
	    }
	    vanSyncResponse.setSuccessCount(successCount);
	    vanSyncResponse.setFailCount(failCount);
	    
	    LOGGER.info(new VanSyncResponseTrans().fromMessage(vanSyncResponse));
	}
	
	LOGGER.info(" ***************************** 同步厢式车开始 ***************************** ");
	return vanSyncResponse;
    }

    @Override
    public Object errorHandler(Object req) throws ESBBusinessException {
	LOGGER.error("ESB处理错误");
	return null;
    }

    /**
     * <p>验证当前的LMS"厢式车"信息是否已经存在FOSS中</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-21 下午6:50:00
     * @param tractor LMS"厢式车"信息参数实体
     * @return FOSS"厢式车"信息实体
     * @see
     */
    private OwnTruckEntity converterLMSDataObjectToFOSSObjectData(VanInfo vanInfo){
	if (null == vanInfo || StringUtils.isBlank(vanInfo.getCarNo())) {
	    return null;
	}
	OwnTruckEntity ownTruck = new OwnTruckEntity();
	ownTruck.setVehicleNo(vanInfo.getCarNo());
//	ownTruck.setVehicleType(DictionaryValueConstants.VEHICLE_TYPE_VAN);
	return synchronousOwnVehicleService.queryOwnVehicleBySelective(ownTruck);
    }
    
    /**
     * <p>转换从LMS同步到FOSS中不匹配的数据</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-21 下午3:42:02
     * @param ownTruck FOSS中待存储的"厢式车"信息对象
     * @param tractor 请求的"厢式车"序列化对象
     * @see
     */
    private void converterLMSDataObjectToFOSSObjectData(OwnTruckEntity ownTruck, VanInfo vanInfo){
	//"是否有尾板"数据类型转换
	if (NumberConstants.NUMERAL_ZORE == vanInfo.getIsTailPlate()) {
	    ownTruck.setTailBoard(FossConstants.INACTIVE);
	}
	if(NumberConstants.NUMERAL_ONE == vanInfo.getIsTailPlate()){
	    ownTruck.setTailBoard(FossConstants.ACTIVE);
	}
	
	//"GPS"数据类型转换
	if (NumberConstants.NUMERAL_ZORE == vanInfo.getIsGPS()) {
	    ownTruck.setGps(FossConstants.INACTIVE);
	}
	if (NumberConstants.NUMERAL_ONE == vanInfo.getIsGPS()) {
	    ownTruck.setGps(FossConstants.ACTIVE);
	}
	
	//"车辆状态"数据类型转换
	if (NumberConstants.NUMERAL_S_ZORE.equalsIgnoreCase(vanInfo.getCarState())) {
	    ownTruck.setStatus(FossConstants.INACTIVE);
	}
	if (NumberConstants.NUMERAL_S_ONE.equalsIgnoreCase(vanInfo.getCarState())) {
	    ownTruck.setStatus(FossConstants.ACTIVE);
	}
	
	//"车辆使用类型"数据类型转换
	if (NumberConstants.NUMERAL_S_ZERE.equalsIgnoreCase(vanInfo.getCarUsedType())) {
	    ownTruck.setUsedType(DictionaryValueConstants.BES_VEHICLE_USED_TYPE_SERVICE);
	}else if (NumberConstants.NUMERAL_S_ONE.equalsIgnoreCase(vanInfo.getCarUsedType())) {
	    ownTruck.setUsedType(DictionaryValueConstants.BES_VEHICLE_USED_TYPE_PKP);
	}else if(NumberConstants.NUMERAL_S_TWO.equalsIgnoreCase(vanInfo.getCarUsedType())){
	    ownTruck.setUsedType(DictionaryValueConstants.BES_VEHICLE_USED_TYPE_BUS);
	}else if(NumberConstants.NUMERAL_S_THREE.equalsIgnoreCase(vanInfo.getCarUsedType())){
	    ownTruck.setUsedType(DictionaryValueConstants.BES_VEHICLE_USED_TYPE_COACH);
	}
    }
    
    /**
     * <p>验证从LMS同步的"厢式车"信息中数据规则</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-21 下午3:42:10
     * @param ownTruck FOSS中待存储的"厢式车"信息对象
     * @param tractor 请求的"厢式车"序列化对象
     * @return 错误原因描述
     * @see
     */
    private String validationSynchronizedRequestDataRule(OwnTruckEntity ownTruck, VanInfo vanInfo){
	StringBuffer ruleReason = new StringBuffer("");
	/*
	 * 根据系统集成组设计的规则要求：
	 * 停车计划开始日期、停车计划结束日期、停车原因代码,若“车辆状态”为不可用，则字段必填
	 */
	if(NumberConstants.NUMERAL_S_ZORE.equalsIgnoreCase(vanInfo.getCarState())){
	    if (null == vanInfo.getPlanBeginTime()){
		ruleReason.append(DataRuleMessageConstant.DATA_RULE_REASON_PLANBEGINTIME_ERROR);
	    } else if (null == vanInfo.getPlanEndTime()) {
		ruleReason.append(DataRuleMessageConstant.DATA_RULE_REASON_PLANENDTIME_ERROR);
	    } else if (null == vanInfo.getReason()) {
		ruleReason.append(DataRuleMessageConstant.DATA_RULE_REASON_PLANREASONCODE_ERROR);
	    } else {
		ownTruck.setUnavilableCode(vanInfo.getReason());
		ownTruck.setBeginDate(vanInfo.getPlanBeginTime());
		ownTruck.setEndDate(vanInfo.getPlanEndTime());
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
     * @date 2012-11-21 下午3:42:13
     * @param tractor 请求的"厢式车"序列化对象
     * @param result 0表示失败，1表示成功
     * @param reason 失败的原因
     * @return
     * @see
     */
    private VanSyncProcessDetail recordSynchronizedRequestDataError(VanInfo vanInfo, boolean result, String reason){
	VanSyncProcessDetail vanSyncProcessDetail = new VanSyncProcessDetail();
	vanSyncProcessDetail.setCarNo(vanInfo.getCarNo());
	vanSyncProcessDetail.setOperateType(vanInfo.getOperateType());
	vanSyncProcessDetail.setVersion(vanInfo.getVersion());
	
	//判断操作是否失败，失败需要强制增加原因
	if (!result) {
	    vanSyncProcessDetail.setReason(reason);
	}
	vanSyncProcessDetail.setResult(result);
	return vanSyncProcessDetail;
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
