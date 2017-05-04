/**
 *  initial comments.
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-load
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/server/action/DeliverLoadGapReportAction.java
 *  
 *  FILE NAME          :DeliverLoadGapReportAction.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrSerialNumberRuleEnum;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.dao.IHandOverBillDao;
import com.deppon.foss.module.transfer.load.api.server.dao.ILoadTaskQueryDao;
import com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao;
import com.deppon.foss.module.transfer.load.api.server.service.ILoadService;
import com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService;
import com.deppon.foss.module.transfer.load.api.server.service.IVehicleSealService;
import com.deppon.foss.module.transfer.load.api.shared.define.TaskTruckConstant;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillSerialNoDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadSerialNoEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.VehicleSealInfoDto;
import com.deppon.foss.module.transfer.scheduling.api.define.TransportPathConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;
import com.deppon.foss.util.define.FossConstants;

/** 
 * @className: LoadService
 * @author: ShiWei shiwei@outlook.com
 * @description: 装车通用service
 * @date: 2013-4-2 下午4:15:11
 * 
 */
public class LoadService implements ILoadService {

	private static final Logger LOGGER = LogManager.getLogger(LoadService.class);
	
	/**
	 *  综合管理 组织信息 Service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	/**
	 * 用于获取驻地部门所属的外场code
	 */
	private ISaleDepartmentService saleDepartmentService;
	
	/**
	 * 封签service
	 */
	private IVehicleSealService vehicleSealService;
	
	/**
	 * 任务车辆service
	 */
	private ITruckTaskService truckTaskService;
	
	/**
	 * tfr common，生成交接单编号
	 */
	private ITfrCommonService tfrCommonService;
	
	/**
	 * 走货路径service
	 */
	private ICalculateTransportPathService calculateTransportPathService;
	
	/**
	 * 交接单dao
	 */
	private IHandOverBillDao handOverBillDao;
	
	/**
	 * 任务查询dao
	 */
	public ILoadTaskQueryDao loadTaskQueryDao;
	
	/**
	 * 用于漂移待办
	 */
	private IWaybillRfcService waybillRfcService;
	
	private IMotorcadeService motorcadeService;
	
	public IWaybillManagerService waybillManagerService;
		
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}


	/** 
	 * 组织接口，用于判断到达部门是外场还是营业部
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	
	 public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
     * 
     * @date Mar 12, 2013 2:17:18 PM
     * @param motorcadeService
     * @see
     */
    public void setMotorcadeService(IMotorcadeService motorcadeService) {
        this.motorcadeService = motorcadeService;
    }
	
	public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
		this.waybillRfcService = waybillRfcService;
	}

	public void setHandOverBillDao(IHandOverBillDao handOverBillDao) {
		this.handOverBillDao = handOverBillDao;
	}

	public void setLoadTaskQueryDao(ILoadTaskQueryDao loadTaskQueryDao) {
		this.loadTaskQueryDao = loadTaskQueryDao;
	}

	public void setCalculateTransportPathService(
			ICalculateTransportPathService calculateTransportPathService) {
		this.calculateTransportPathService = calculateTransportPathService;
	}

	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}

	public void setTruckTaskService(ITruckTaskService truckTaskService) {
		this.truckTaskService = truckTaskService;
	}

	public void setVehicleSealService(IVehicleSealService vehicleSealService) {
		this.vehicleSealService = vehicleSealService;
	}

	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
	 * 根据传入的部门code，获取该部门所属的营业部（派送部）、总调、外场
	 * 装、卸车部门转换均使用该方法
	 * @author 045923-foss-shiwei
	 * @date 2013-4-2 下午4:17:21
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ILoadService#querySuperiorOrgByOrgCode(java.lang.String)
	 */
	@Override
	public OrgAdministrativeInfoEntity querySuperiorOrgByOrgCode(String orgCode) {
		
		//设置查询参数
		List<String> bizTypesList = new ArrayList<String>();
		//外场类型
		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		//空运总调类型
		bizTypesList.add(BizTypeConstants.ORG_AIR_DISPATCH);
		//营业部（派送部）
		bizTypesList.add(BizTypeConstants.ORG_SALES_DEPARTMENT);
		//查询上级部门
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.
				queryOrgAdministrativeInfoIncludeSelfByCode(orgCode, bizTypesList);
		if(orgAdministrativeInfoEntity != null){
			//返回部门
			return orgAdministrativeInfoEntity;
		}else{
			//获取上级部门失败
			LOGGER.error("################查询组织（code：" + orgCode + "）所属的上级部门失败(包括营业部、派送部、外场、总调)！##########");
			return null;
		}
	}
	
	/**
	 * * 同组织查询所属外场、空运总调、营业部（传入车队返回该顶级车队服务外场）
	 * @author 105869
	 * @date 2015年1月20日 16:37:24
	 * @Prama code
	 * @return OrgAdministrativeInfoEntity
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ILoadService#querySuperOrgByOrgCode(java.lang.String)
	 */
	@Override
	public OrgAdministrativeInfoEntity querySuperOrgByOrgCode(String orgCode){
		//设置查询参数
		List<String> bizTypesList = new ArrayList<String>();
		//外场类型
		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		//空运总调类型
		bizTypesList.add(BizTypeConstants.ORG_AIR_DISPATCH);
		//营业部（派送部）
		bizTypesList.add(BizTypeConstants.ORG_SALES_DEPARTMENT);
		//查询上级部门
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.
				queryOrgAdministrativeInfoIncludeSelfByCode(orgCode, bizTypesList);
		if(orgAdministrativeInfoEntity != null){
			//返回部门
			return orgAdministrativeInfoEntity;
		}else{
			OrgAdministrativeInfoEntity fleet = orgAdministrativeInfoComplexService.getTopFleetByCode(orgCode);
			if(fleet!=null){
				MotorcadeEntity motorcadeEntity= motorcadeService.queryMotorcadeByCodeClean(fleet.getCode());
				//若查询出的上级车队是顶级车队
				if(motorcadeEntity !=null&&FossConstants.YES.equals(motorcadeEntity.getIsTopFleet())
						&& StringUtils.isNotEmpty(motorcadeEntity.getTransferCenter())){
					
					OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(motorcadeEntity.getTransferCenter());
					if(orgEntity!=null&&StringUtils.equals(orgEntity.getTransferCenter(), FossConstants.YES)){
						return orgEntity;
					}else{
						//获取上级部门失败
						LOGGER.error("################查询组织（code：" + orgCode + "）所属的上级部门失败(包括营业部、派送部、外场、总调)！##########");
						return null;
					}
				}else{
					//获取上级部门失败
					LOGGER.error("################查询组织（code：" + orgCode + "）所属的上级部门失败(包括营业部、派送部、外场、总调)！##########");
					return null;
				}
				
			}else{
				//获取上级部门失败
				LOGGER.error("################查询组织（code：" + orgCode + "）所属的上级部门失败(包括营业部、派送部、外场、总调)！##########");
				return null;	
			}
			
		}
	}
	
	/**
	 * * 同组织查询所属外场（传入车队返回该顶级车队服务外场）
	 * @author 189284
	 * @date 2015年10月12日 16:37:24
	 * @Prama code
	 * @return OrgAdministrativeInfoEntity
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ILoadService#querySuperOrgByOrgCode(java.lang.String)
	 */
	@Override
	public OrgAdministrativeInfoEntity queryTCOrgByOrgCode(String orgCode){
		//设置查询参数
		List<String> bizTypesList = new ArrayList<String>();
		//外场类型
		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		//营业部（派送部）
		bizTypesList.add(BizTypeConstants.ORG_SALES_DEPARTMENT);
		//查询上级部门
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.
				queryOrgAdministrativeInfoIncludeSelfByCode(orgCode, bizTypesList);
		if(orgAdministrativeInfoEntity != null && StringUtils.isNotBlank(orgAdministrativeInfoEntity.getCode())){
			//如果为营业部
			if(StringUtils.equals(orgAdministrativeInfoEntity.getSalesDepartment(), FossConstants.YES)){
				//如果营业部为驻地部门
				SaleDepartmentEntity saleOrgEntity = saleDepartmentService.querySaleDepartmentByCode(orgAdministrativeInfoEntity.getCode());
				if(saleOrgEntity == null || StringUtils.isBlank(saleOrgEntity.getCode())){
					LOGGER.info("################部门（code：" + orgAdministrativeInfoEntity.getCode() + "）为营业部，但获取营业部信息接口返回为空或接口返回的营业部code为空！");
					throw new TfrBusinessException("部门（code：" + orgAdministrativeInfoEntity.getCode() + "）为营业部，但获取营业部信息接口返回为空或接口返回的营业部code为空！");
				}
				if(StringUtils.equals(saleOrgEntity.getStation(), FossConstants.YES)){
					return //先查出组织信息：
					orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(saleOrgEntity.getTransferCenter());
				}
			}
			//返回部门
			return orgAdministrativeInfoEntity;
		}else{
			OrgAdministrativeInfoEntity fleet = orgAdministrativeInfoComplexService.getTopFleetByCode(orgCode);
			if(fleet!=null){
				MotorcadeEntity motorcadeEntity= motorcadeService.queryMotorcadeByCodeClean(fleet.getCode());
				//若查询出的上级车队是顶级车队
				if(motorcadeEntity !=null&&FossConstants.YES.equals(motorcadeEntity.getIsTopFleet())
						&& StringUtils.isNotEmpty(motorcadeEntity.getTransferCenter())){
					
					OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(motorcadeEntity.getTransferCenter());
					if(orgEntity!=null&&StringUtils.equals(orgEntity.getTransferCenter(), FossConstants.YES)){
						return orgEntity;
					}else{
						//获取上级部门失败
						LOGGER.error("################查询组织（code：" + orgCode + "）所属的上级部门失败(包括营业部、派送部、外场、总调)！##########");
						return null;
					}
				}else{
					//获取上级部门失败
					LOGGER.error("################查询组织（code：" + orgCode + "）所属的上级部门失败(包括营业部、派送部、外场、总调)！##########");
					return null;
				}
				
			}else{
				//获取上级部门失败
				LOGGER.error("################查询组织（code：" + orgCode + "）所属的上级部门失败(包括营业部、派送部、外场、总调)！##########");
				return null;	
			}
			
		}
	}
	
	/**
	 * 根据传入的部门code，获取该部门所属的营业部(派送部)，总调、外场的code，如果获取到的部门为驻地部门，则返回驻地部门所属外场的code
	 * @author 045923-foss-shiwei
	 * @date 2013-4-16 上午10:15:07
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ILoadService#querySuperiorOrgCodeIncludeTransferCenterByOrgCode(java.lang.String)
	 */
	@Override
	public String querySuperiorOrgCodeIncludeTransferCenterByOrgCode(
			String orgCode) {
		//设置查询参数
		List<String> bizTypesList = new ArrayList<String>();
		//外场类型
		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		//营业部（派送部）
		bizTypesList.add(BizTypeConstants.ORG_SALES_DEPARTMENT);
		//查询上级部门
		OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoComplexService.
				queryOrgAdministrativeInfoIncludeSelfByCode(orgCode, bizTypesList);
		if(orgEntity != null && StringUtils.isNotBlank(orgEntity.getCode())){
			//如果为营业部
			if(StringUtils.equals(orgEntity.getSalesDepartment(), FossConstants.YES)){
				//如果营业部为驻地部门
				SaleDepartmentEntity saleOrgEntity = saleDepartmentService.querySaleDepartmentByCode(orgEntity.getCode());
				if(saleOrgEntity == null || StringUtils.isBlank(saleOrgEntity.getCode())){
					LOGGER.info("################部门（code：" + orgEntity.getCode() + "）为营业部，但获取营业部信息接口返回为空或接口返回的营业部code为空！");
					throw new TfrBusinessException("部门（code：" + orgEntity.getCode() + "）为营业部，但获取营业部信息接口返回为空或接口返回的营业部code为空！");
				}
				if(StringUtils.equals(saleOrgEntity.getStation(), FossConstants.YES)){
					return saleOrgEntity.getTransferCenter();
				}
			}
			//返回部门
			return orgEntity.getCode();
		}else{
			//获取上级部门失败
			LOGGER.error("################查询组织（code：" + orgCode + "）所属的上级部门失败(包括营业部、派送部、外场)！##########");
			return null;
		}
	}

	/**
	 * 校验车牌号是否可用，主要是检验是否解封签
	 * @author 045923-foss-shiwei
	 * @date 2013-4-17 上午10:40:17
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ILoadService#validateVehicleNoCanBeUsed(java.lang.String)
	 */
	@Override
	public void validateVehicleNoCanBeUsed(String vehicleNo) {
		//如果车辆已封签，则不能保存交接单
		VehicleSealInfoDto seal = vehicleSealService.queryVehicleSealInfo(vehicleNo);
		if(seal != null){
			StringBuilder sealDestOrg = new StringBuilder();
			if(CollectionUtils.isNotEmpty(seal.getTruckTaskDetails())){
				int i = 0;
				for(TruckTaskDetailEntity truckTaskDetail : seal.getTruckTaskDetails()){
					i += 1;
					//因为sql中没有去除状态为cancled的车辆任务明细，所以在service中去除
					if(!StringUtils.equals(truckTaskDetail.getState(), TaskTruckConstant.TASK_TRUCK_STATE_CANCLED)){
						sealDestOrg.append(truckTaskDetail.getDestOrgName());
						if(i == seal.getTruckTaskDetails().size()){
							sealDestOrg.append("。");
						}else{
							sealDestOrg.append("、");
						}
					}else{
						if(i == seal.getTruckTaskDetails().size()){
							sealDestOrg.append("。");
						}
					}
				}
			}
			throw new TfrBusinessException("车辆封签尚未校验，可校验该车辆封签的部门为："+sealDestOrg.toString()); 
		}
	}

	/**
	 * 新增交接单时校验车牌号，做如下校验：对于指定的车牌号，存在有出发部门不是本部门、且车辆未出发的记录，则本部门不能使用该车牌号。
	 * @author 045923-foss-shiwei
	 * @date 2013-5-15 上午10:05:12
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ILoadService#queryUndepartRecByVehicleNo(com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskDetailEntity)
	 */
	@Override
	public void queryUndepartRecByVehicleNo(String origOrgCode,String vehicleNo,String state) {
		//构造查询条件对象
		TruckTaskDetailEntity queryCon = new TruckTaskDetailEntity();
		queryCon.setOrigOrgCode(origOrgCode);
		queryCon.setVehicleNo(vehicleNo);
		queryCon.setState(state);
		
		//查询未出发的记录
		List<String> recList = truckTaskService.queryUndepartRecByVehicleNo(queryCon);
		
		//如果记录不为空，则抛异常
		if(CollectionUtils.isNotEmpty(recList)){
			StringBuilder orgList = new StringBuilder();
			int i = 0;
			for(String orgName : recList){
				i += 1;
				orgList.append(orgName);
				if(i != recList.size()){
					orgList.append("、");
				}
			}
			throw new TfrBusinessException("车辆已经被“"+orgList.toString() + "”使用，请联系该部门或更换车辆！"); 
		}
	}

	/**
	 * 生成交接单编号
	 * @author 045923-foss-shiwei
	 * @date 2013-5-23 下午5:41:15
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ILoadService#generateHandOverBillNo()
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public String generateHandOverBillNo() {
		String handOverBillNo = tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.JJD);
		return handOverBillNo;
	}
	
	/**
	 * 调用走货路径
	 * @author 045923-foss-shiwei
	 * @date 2013-5-30 上午10:56:17
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ILoadService#callTransportPathService(com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillEntity, java.util.List, java.util.List, java.util.Map)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void callTransportPathService(HandOverBillEntity nowHandOverBillEntity,
			List<HandOverBillDetailEntity> allWaybillList,
			List<HandOverBillSerialNoDetailEntity> deletedSerialNoList,
			Map<String,Map<String,HandOverBillSerialNoDetailEntity>> allSerialNoMap){
		//收集运单下的流水号列表
		List<String> serialNoList = null;
		//全部运单调用走货路径loadVehicle方法
		for(HandOverBillDetailEntity waybillEntity : allWaybillList){
			String waybillNo = waybillEntity.getWaybillNo();
			serialNoList = new ArrayList<String>();
			//获取流水号Map
			Map<String,HandOverBillSerialNoDetailEntity> serialNoMap = allSerialNoMap.get(waybillNo);
			Set entrySet = serialNoMap.entrySet();
			Iterator it = entrySet.iterator();
			while(it.hasNext()){
				Map.Entry entry = (Map.Entry)it.next();
				HandOverBillSerialNoDetailEntity serialNo = (HandOverBillSerialNoDetailEntity)entry.getValue();
				serialNoList.add(serialNo.getSerialNo());
			}
			//是否合车
			int isJoinCar = StringUtils.equals(waybillEntity.getIsJoinCar(), FossConstants.YES) ? 1:0;
			calculateTransportPathService.loadVehicle(waybillNo, 
																			  serialNoList, 
																			  TransportPathConstants.TRANSPORTPATH_STATUS_HANDOVER, 
																			  waybillEntity.getPieces().intValue(), 
																			  isJoinCar, 
																			  nowHandOverBillEntity.getDepartDeptCode(), 
																			  nowHandOverBillEntity.getArriveDeptCode(), 
																			  nowHandOverBillEntity.getVehicleNo());
		}
		//删除的流水号调用走货路径rollBack方法
		for(HandOverBillSerialNoDetailEntity serialNo : deletedSerialNoList){
			calculateTransportPathService.rollBack(serialNo.getWaybillNo(), serialNo.getSerialNo(), nowHandOverBillEntity.getVehicleNo(),TransportPathConstants.TRANSPORTPATH_STATUS_HANDOVER);
		}
	}

	/**
	 * 漂移交接单内运单的待办事项
	 * @author 045923-foss-shiwei
	 * @date 2013-6-5 下午11:28:15
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ILoadService#driftToDoAfterHandOvered(com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillDto, java.util.List)
	 */
	@Override
	@Transactional
	public int driftToDoAfterHandOvered(QueryHandOverBillDto baseDto,List<HandOverBillSerialNoDetailEntity> serialNoList){
		//当前部门
		String cOrgCode = baseDto.getDepartDeptCode();
		//下一部门
		String nOrgCode = baseDto.getArriveDeptCode();
		//交接单号
		String handOverBillNo = baseDto.getHandOverBillNo();
		//循环调用待办接口
		for(HandOverBillSerialNoDetailEntity serialNo : serialNoList){
			try{
				this.waybillRfcService.queryTodoWhenLoad(serialNo.getWaybillNo(), serialNo.getSerialNo(), cOrgCode, nOrgCode);
			}catch(Exception e){
				LOGGER.error("调用pkp待办漂移接口出现异常，运单号：" + serialNo.getWaybillNo() + "，流水号：" + serialNo.getSerialNo() + "，异常信息：" + e.getMessage());
				continue;
			}
		}
		//更新交接单的“是否待办漂移”为Y
		handOverBillDao.updateHandOverBillDriftedToDo(handOverBillNo);
		return FossConstants.SUCCESS;
	}

	/**
	 * 
	 * 提供方法给清仓，查询给定时间前的装车扫描状态
	 * @author alfred
	 * @date 2013-9-6 上午9:04:39
	 * @param waybillNo
	 * @param serialNo
	 * @param loadStartTime
	 * @param origOrgCode
	 * @return 
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ILoadService#queryLoadScanState(java.lang.String, java.lang.String, java.util.Date, java.lang.String)
	 */
	@Override
	public List<LoadSerialNoEntity> queryLoadScanState(String waybillNo,
			String serialNo, Date loadStartTime, String origOrgCode) {
		Map<String,Object> condition = new HashMap<String,Object>();
		//运单号
		condition.put("wayBillNo",waybillNo);
		//流水号
		condition.put("serialNo", serialNo);
		//开始时间
		condition.put("loadStartTime",loadStartTime);
		//操作部门
		condition.put("origOrgCode", origOrgCode);
	
		List<LoadSerialNoEntity> loadSerialNoList = loadTaskQueryDao.queryLoadScanState(condition);
		return loadSerialNoList;
	}
	/**
	 * @author nly
	 * @date 2015年5月16日 下午5:49:39
	 * @function 查询落地配装车信息
	 * @param waybillNo
	 * @param serialNo
	 * @param deptCode
	 * @return
	 */
	@Override
	public List<LoadSerialNoEntity> queryLdpLoadScanInfo(String waybillNo,String serialNo, String deptCode) {
		Map<String,String> condition = new HashMap<String,String>();
		//运单号
		condition.put("wayBillNo",waybillNo);
		//流水号
		condition.put("serialNo", serialNo);
		//操作部门
		condition.put("origOrgCode", deptCode);
	
		List<LoadSerialNoEntity> loadSerialNoList = loadTaskQueryDao.queryLdpLoadScanInfo(condition);
		return loadSerialNoList;		
	}
	

	/**
	 * 依赖注入DAO
	 */
	private IPDALoadDao pdaLoadDao;
	public void setPdaLoadDao(IPDALoadDao pdaLoadDao) {
		this.pdaLoadDao = pdaLoadDao;
}
	/**
	 * 判断运单是在派送中的不能做法更改
	 * @param waybillNo
	 * @return 是否是可以更改
	 * @author 268084
	 */
	@Override
	public boolean ifCouldBeChangedeWaybillNo(String waybillNo) {
		try {
			// 此处判断货物是不是还在库存中(派送拉回、单票入库、清仓入库等方式)
			if (pdaLoadDao.judgeIfIsDeliverLoad(waybillNo) == 0) {// 判断某运单是不是在派送装车任务中
				return true;
			} else {
				List<String> sList = pdaLoadDao.queryWaybillInStock(waybillNo);// 根据运单号查询这个运单号有没有在库存中
				if (sList == null || sList.isEmpty()) {
					return false;
				} else {
					Integer qtyInStock = pdaLoadDao
							.waybillNoQtyInStock(waybillNo);// 一票多件时判断库存中有没有
					Integer loadedQty = pdaLoadDao.loadWaybillQty(waybillNo);// 判断已装车的有没有
					WaybillEntity wayBill = waybillManagerService.queryWaybillBasicByNo(waybillNo);
					//判定货物若是全部在库存，则可以发更改
					if(qtyInStock.equals(wayBill.getGoodsQtyTotal())){
						return true;
					}
					if (qtyInStock != 0 && loadedQty != 0) {
						long lastInStockTime = pdaLoadDao.lastInstockTime(
								waybillNo).getTime();// 运单的最后入库时间
						long loadTime = pdaLoadDao.loadTime(waybillNo)
								.getTime();// 运单的装车时间
						if (lastInStockTime > loadTime) {
							return true;
						} else {
							return false;
						}
					} else if (qtyInStock != 0 && loadedQty == 0) {
						return true;
					} else if (qtyInStock == 0 && loadedQty != 0) {
						return false;
					}	
					
				return true;
				}
			}
		} catch (Exception e) {
			LOGGER.error("查询派送派送中的货物时出错!"+e);
			throw new TfrBusinessException("查询派送派送中的货物时出错！");
		}
	}

}
