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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/lmsitf/esb/service/impl/SynchronousOwnVehicleService.java
 * 
 * FILE NAME        	: SynchronousOwnVehicleService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.lmsitf.esb.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOwnVehicleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleUnavilableService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleUnavilableEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.OwnVehicleException;
import com.deppon.foss.module.base.lmsitf.esb.service.ISynchronousOwnVehicleService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IVehicleActualSituationManageService;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.VehicleActualSituationEntity;
import com.deppon.foss.module.transfer.departure.api.server.service.ILMSSynchronousService;
import com.deppon.foss.util.define.FossConstants;
/**
 * 用来同步LMS“公司车辆（厢式车、挂车、拖头）”到FOSS系统的抽取Service接口实现类：无SUC
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-12-29 下午5:15:13</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-12-29 下午5:15:13
 * @since
 * @version
 */
public class SynchronousOwnVehicleService implements
	ISynchronousOwnVehicleService {

    //"中转"同步LMS的数据到其本地
    private ILMSSynchronousService lmsSynchronousService;
    
    //"接送货"同步公司车辆数据到其本地
    private IVehicleActualSituationManageService vehicleActualSituationManageService;
    
    //"公司车辆（厢式车、挂车、拖头）"Service
    private IOwnVehicleService ownVehicleService;
    
    //同步"停车计划"service
    private IVehicleUnavilableService vehicleUnavilableService;
    
    /**
     * <p>从LMS同步一个“公司车辆和停车计划信息”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-16 下午2:33:15
     * @param ownTruck “公司车辆”实体
     * @param vehicleUnavilable 停车计划
     * @return 1：成功；-1：失败
     * @throws OwnVehicleException 
     * @see com.deppon.foss.module.base.lmsitf.esb.service.ISynchronousOwnVehicleService#synchronousOwnVehiclePlanByLMS(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity, java.lang.String)
     */
    @Override
    @Transactional
    public int synchronousOwnVehiclePlanByLMS(OwnTruckEntity ownTruck, VehicleUnavilableEntity vehicleUnavilable) throws OwnVehicleException {
	int start = NumberConstants.NUMERAL_ZORE, limit = NumberConstants.NUMERAL_ONE;
	List<VehicleUnavilableEntity> vUnaviableCars = null;
	VehicleUnavilableEntity pUnaviableCar = new VehicleUnavilableEntity();
	pUnaviableCar.setVehicleNo(vehicleUnavilable.getVehicleNo());
	pUnaviableCar.setLmsCode(vehicleUnavilable.getLmsCode());
	vUnaviableCars = vehicleUnavilableService.queryVehicleUnavilableListBySelectiveCondition(pUnaviableCar, start, limit);
	//同步“停车计划”原因历史
	if(CollectionUtils.isEmpty(vUnaviableCars)){
	    vehicleUnavilableService.addVehicleUnavilable(vehicleUnavilable, ComnConst.EXTERNAL_SYSTEM_LMS_USER_ACCOUNT, false);
	} else {
	    //根据ID删除
	    vehicleUnavilableService.deleteVehicleUnavilable(vUnaviableCars.get(0).getId(), ComnConst.EXTERNAL_SYSTEM_LMS_USER_ACCOUNT);
	    vehicleUnavilableService.updateVehicleUnavilable(vehicleUnavilable, ComnConst.EXTERNAL_SYSTEM_LMS_USER_ACCOUNT, false);
	}
	//同步“公共车辆”停车计划信息
	ownVehicleService.synchronousOwnVehiclePlanByLMS(ownTruck, ComnConst.EXTERNAL_SYSTEM_LMS_USER_ACCOUNT);
	//同步给“中转”LMS停车计划数据
	lmsSynchronousService.lmsSynchronous(ownTruck.getVehicleNo(), ownTruck.getBeginDate(), ownTruck.getEndDate(), ownTruck.getUnavilableCode());
	return FossConstants.SUCCESS;
    }

    /**
     * <p>新增一个“公司车辆（厢式车、挂车、拖头）”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-19 下午2:32:37
     * @param ownTruck “公司车辆（厢式车、挂车、拖头）”实体
     * @return 1：成功；-1：失败
     * @throws OwnVehicleException 
     * @see com.deppon.foss.module.base.lmsitf.esb.service.ISynchronousOwnVehicleService#addOwnVehicle(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity)
     */
    @Override
    @Transactional
    public int addOwnVehicle(OwnTruckEntity ownTruck) throws OwnVehicleException {
	ownVehicleService.addOwnVehicle(ownTruck, ComnConst.EXTERNAL_SYSTEM_LMS_USER_ACCOUNT, false, ownTruck.getVehicleType());
	//同步给“接送货”公司车辆数据
	VehicleActualSituationEntity situationEntity = new VehicleActualSituationEntity();
	situationEntity.setVehicleNo(ownTruck.getVehicleNo());
	situationEntity.setRemainingWeight(ownTruck.getDeadLoad());
	situationEntity.setRemainingVolume(ownTruck.getSelfVolume());
	situationEntity.setCreateDate(ownTruck.getCreateDate());
	vehicleActualSituationManageService.addVehicleSituation(situationEntity);
	return FossConstants.SUCCESS;
    }

    /**
     * <p>根据“公司车辆（厢式车、挂车、拖头）”记录唯一标识作废（逻辑删除）一条“公司车辆（厢式车、挂车、拖头）”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-19 下午2:32:40
     * @param ownTruck “公司车辆（厢式车、挂车、拖头）”实体
     * @return 1：成功；-1：失败
     * @throws OwnVehicleException 
     * @see com.deppon.foss.module.base.lmsitf.esb.service.ISynchronousOwnVehicleService#deleteOwnVehicle(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity)
     */
    @Override
    @Transactional
    public int deleteOwnVehicle(OwnTruckEntity ownTruck) throws OwnVehicleException {
	ownVehicleService.deleteOwnVehicle(ownTruck, ComnConst.EXTERNAL_SYSTEM_LMS_USER_ACCOUNT, ownTruck.getVehicleType());
	return FossConstants.SUCCESS;
    }

    /**
     * <p>修改一个“公司车辆（厢式车、挂车、拖头）”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-19 下午2:32:43
     * @param ownTruck “公司车辆（厢式车、挂车、拖头）”实体
     * @return 1：成功；-1：失败
     * @throws OwnVehicleException 
     * @see com.deppon.foss.module.base.lmsitf.esb.service.ISynchronousOwnVehicleService#updateOwnVehicle(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity)
     */
    @Override
    @Transactional
    public int updateOwnVehicle(OwnTruckEntity ownTruck) throws OwnVehicleException {
	ownVehicleService.updateOwnVehicle(ownTruck, ComnConst.EXTERNAL_SYSTEM_LMS_USER_ACCOUNT, ownTruck.getVehicleType());
	return FossConstants.SUCCESS;
    }
    
    /**
     * <p>根据条件有选择的检索出符合条件的“公司车辆（厢式车、挂车、拖头）”单个实体（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-22 上午9:28:20
     * @param ownTruck 以“公司车辆（厢式车、挂车、拖头）”实体承载的条件参数实体
     * @return 符合条件的“公司车辆（厢式车、挂车、拖头）”实体
     * @throws OwnVehicleException 
     * @see com.deppon.foss.module.base.lmsitf.esb.service.ISynchronousOwnVehicleService#queryOwnVehicleBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity)
     */
    @Override
    @Transactional(readOnly = true)
    public OwnTruckEntity queryOwnVehicleBySelective(OwnTruckEntity ownTruck)
            throws OwnVehicleException {
	if(null == ownTruck){
	    ownTruck = new OwnTruckEntity();
	}
        return ownVehicleService.queryOwnVehicleBySelective(ownTruck, ownTruck.getVehicleType());
    }
    
    /**
     * @param lmsSynchronousService the lmsSynchronousService to set
     */
    public void setLmsSynchronousService(
    	ILMSSynchronousService lmsSynchronousService) {
        this.lmsSynchronousService = lmsSynchronousService;
    }
    
    /**
     * @param vehicleActualSituationManageService the vehicleActualSituationManageService to set
     */
    public void setVehicleActualSituationManageService(
    	IVehicleActualSituationManageService vehicleActualSituationManageService) {
        this.vehicleActualSituationManageService = vehicleActualSituationManageService;
    }

    /**
     * @param ownVehicleService the ownVehicleService to set
     */
    public void setOwnVehicleService(IOwnVehicleService ownVehicleService) {
        this.ownVehicleService = ownVehicleService;
    }

    /**
     * @param vehicleUnavilableService the vehicleUnavilableService to set
     */
    public void setVehicleUnavilableService(
    	IVehicleUnavilableService vehicleUnavilableService) {
        this.vehicleUnavilableService = vehicleUnavilableService;
    }
}
