/**
 *  initial comments.
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
 *  PROJECT NAME  : tfr-unload
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/server/service/impl/UnloadTaskService.java
 *  
 *  FILE NAME          :UnloadTaskService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-unload
 * PACKAGE NAME: com.deppon.foss.module.transfer.unload.server.service.impl
 * FILE    NAME: UnloadTaskService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.unload.server.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.deppon.foss.module.pickup.pricing.dubbo.api.server.service.IProductService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressBranchSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILoadAndUnloadEfficiencyTonService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILoadAndUnloadSquadService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPlatformService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPorterService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressBranchSalesDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadEfficiencyTonEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadSquadEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PorterEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;

import com.deppon.foss.module.pickup.waybill.api.server.service.ICompensateSpreadService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.transfer.arrival.api.server.dao.IArrivalDao;
import com.deppon.foss.module.transfer.arrival.api.shared.dto.WaybillPlanArriveTimeDto;
import com.deppon.foss.module.transfer.common.api.server.service.IBillNumService;
import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToWkService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrJobTodoService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrNotifyService;
import com.deppon.foss.module.transfer.common.api.server.service.IWkBillAddTfrNotifyService;
import com.deppon.foss.module.transfer.common.api.shared.define.BusinessGoalContants;
import com.deppon.foss.module.transfer.common.api.shared.define.BusinessSceneConstants;
import com.deppon.foss.module.transfer.common.api.shared.define.NotifyWkConstants;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.FossToWKResponseEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobTodoEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrNotifyEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.WkHandOverBillEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.common.server.utils.JSONUtils;
import com.deppon.foss.module.transfer.departure.api.handle.DepartureHandle;
import com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao;
import com.deppon.foss.module.transfer.departure.api.shared.define.DepartureConstant;
import com.deppon.foss.module.transfer.departure.api.shared.domain.TruckActionDetailEntity;
import com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao;
import com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao;
import com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService;
import com.deppon.foss.module.transfer.load.api.server.service.ILoadService;
import com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService;
import com.deppon.foss.module.transfer.load.api.server.service.IVehicleSealService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.define.SealConstant;
import com.deppon.foss.module.transfer.load.api.shared.define.TaskTruckConstant;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillSerialNoDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.SealEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.TruckTaskHandOverDto;
import com.deppon.foss.module.transfer.pda.api.shared.exception.TransferPDAExceptionCode;
import com.deppon.foss.module.transfer.scheduling.api.define.PlatformDispatchConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IPlatformDispatchService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PlatformDistributeEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.FeedbackDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao;
import com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao;
import com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadTaskDao;
import com.deppon.foss.module.transfer.unload.api.server.service.IPDAUnloadService;
import com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskService;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadBillDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadSerialNoDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadTaskEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadWaybillDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ArriveBillDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ConfirmUnloadTaskBillsDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ConfirmUnloadTaskDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.DeleteFromUnloadTaskDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ExpressCancelUnloadTaskDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ExpressConfirmUnloadTaskDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ExpressLoaderParticipateDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ExpressQueryUnloadWaybillDetailDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ExpressUnloadTaskAddnewDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ExpressUnloadTaskDetailDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ExpressUnloadWaybillDetailDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ExpressUpdateUnloadTaskDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.FossToWKResponseDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.HandOverAndUnloadDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.PlanUnloadBillDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.QueryArrivedBillInfoByNoDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.QueryArrivedBillNoDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadBillDetailDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadTaskAddnewDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadTaskModifyDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadWaybillDetailDto;
import com.deppon.foss.module.transfer.unload.api.tools.UnloadCommonUtils;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

import net.sf.json.JSONObject;

import javax.annotation.Resource;


/**
 * 卸车任务service类
 * @author 045923-foss-shiwei
 * @date 2012-11-26 上午10:30:18
 */
public class UnloadTaskService implements IUnloadTaskService{
	private IBillNumService billNumService;
	
	private IConfigurationParamsService configurationParamsService;
	
	public void setBillNumService(IBillNumService billNumService) {
		this.billNumService = billNumService;
	}
	
	private ITfrNotifyService tfrNotifyService;
	
	private IFOSSToWkService fossToWkService;
	
	public void setFossToWkService(IFOSSToWkService fossToWkService) {
		this.fossToWkService = fossToWkService;
	}

	public void setTfrNotifyService(ITfrNotifyService tfrNotifyService) {
		this.tfrNotifyService = tfrNotifyService;
	}
	
	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	private IWkBillAddTfrNotifyService wkBillAddTfrNotifyService;
	
	/**
	 * @param wkBillAddTfrNotifyService the wkBillAddTfrNotifyService to set
	 */
	public void setWkBillAddTfrNotifyService(IWkBillAddTfrNotifyService wkBillAddTfrNotifyService) {
		this.wkBillAddTfrNotifyService = wkBillAddTfrNotifyService;
	}

	/**
	 * 待办job service接口
	 */
	private ITfrJobTodoService tfrJobTodoService;
	
	public void setTfrJobTodoService(ITfrJobTodoService tfrJobTodoService) {
		this.tfrJobTodoService = tfrJobTodoService;
	}
	/**
	 *  综合管理 组织信息 Service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 本模块dao
	 */
	private IUnloadTaskDao unloadTaskDao;
	
	/**
	 * 记录日志
	 */
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	/** 
	 * The pda load dao. 
	 * 
	 * 
	 */
	private IPDALoadDao pdaLoadDao;
	/**
	 * Sets the pda load dao.
	 *
	 * @param pdaLoadDao the new pda load dao
	 */
	public void setPdaLoadDao(IPDALoadDao pdaLoadDao) {
		this.pdaLoadDao = pdaLoadDao;
	}
	/**
	 * 用于获取上级组织
	 */
	private ILoadService loadService;
	
	/**
	 * 交接单、配载单service，用于获取其下运单号、流水号
	 */
	private IHandOverBillService handOverBillService;
	
	/**
	 * 交接单、配载单service，用于获取其下运单号、流水号
	 */
	private IVehicleAssembleBillService vehicleAssembleBillService;
	
	/**
	 * 运单service，根据运单号获取运单货物品名，包装，运输性质等
	 */
	private IWaybillManagerService waybillManagerService;
	
	//库存service，卸车确认时，货物入库
	//private IStockService stockService;
	
	/**
	 * 走货路径接口，判断多货货物是夹带多货还是异地夹带多货
	 */
	private ICalculateTransportPathService calculateTransportPathService;
	
	/**
	 * 综合装卸效率服务，用于计算任务结束时间
	 */
	private ILoadAndUnloadEfficiencyTonService loadAndUnloadEfficiencyTonService;
	
	/**
	 * 调度月台服务，用于占用月台和放空月台
	 */
	private IPlatformDispatchService platformDispatchService;
	
	/**
	 * 综合月台服务，获取月台虚拟code
	 */
	private IPlatformService platformService;
	
	/**
	 * 出发模块dao
	 */
	private IDepartureDao departureDao;
	
	/**
	 * 任务车辆dao
	 */
	private ITruckTaskDao truckTaskDao;
	
	/**
	 * pda卸车任务dao
	 */
	private IPDAUnloadTaskDao pdaUnloadTaskDao;
	
	/**
	 * 分配卸车任务dao
	 */
	private IAssignUnloadTaskDao assignUnloadTaskDao;
	
	/**
	 * 产品类型服务
	 */
	@Resource
	private IProductService productService4Dubbo;
	
	/**
	 * 用于获取理货员所在装卸车队
	 */
	private IPorterService porterService;
	
	private IExpressBranchSalesDeptService expressBranchSalesDeptService;
	
	
	private IVehicleSealService vehicleSealService;
	
	/** 
	 * 
	 * The sale department service.
	 * 
	 */
	private ISaleDepartmentService saleDepartmentService;
	/**
	 * Sets the sale department service.
	 *
	 * @param saleDepartmentService the new sale department service
	 */
	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}
	
	/**
	 * Sets the org administrative info service.
	 *
	 * @param orgAdministrativeInfoService the new org administrative info service
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	/**
	 *  The arrival dao.
	 *
	 */
	private IArrivalDao arrivalDao;
	/**
	 * Sets the arrival dao.
	 *
	 * @param arrivalDao the new arrival dao
	 */
	public void setArrivalDao(IArrivalDao arrivalDao) {
		this.arrivalDao = arrivalDao;
	}
	/**
	 * 晚到补差价接口
	 */
	
	private ICompensateSpreadService compensateSpreadService;
	
	public void setCompensateSpreadService(
			ICompensateSpreadService compensateSpreadService) {
		this.compensateSpreadService = compensateSpreadService;
	}
	
	/**
	 * @param vehicleSealService the vehicleSealService to set
	 */
	public void setVehicleSealService(IVehicleSealService vehicleSealService) {
		this.vehicleSealService = vehicleSealService;
	}

	public final void setExpressBranchSalesDeptService(
			IExpressBranchSalesDeptService expressBranchSalesDeptService) {
		this.expressBranchSalesDeptService = expressBranchSalesDeptService;
	}
	/**
	 * 用于获取理货员所在装卸车队
	 */
	private ILoadAndUnloadSquadService loadAndUnloadSquadService;
	private IPDAUnloadService pdaUnloadService;
	
	private IWaybillRfcService waybillRfcService;
	/**
	 * Sets the waybill rfc service.
	 * 
	 *
	 * @param waybillRfcService the new waybill rfc service
	 */
	public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
		this.waybillRfcService = waybillRfcService;
	}
	
	public void setPdaUnloadService(IPDAUnloadService pdaUnloadService) {
		this.pdaUnloadService = pdaUnloadService;
	}
	/*
	 * 定义特殊字符，用于确认卸车任务时，组合map的key
	 */
	private static final String TAG = "@";
	
	public void setLoadService(ILoadService loadService) {
		this.loadService = loadService;
	}
	
//	public void setProductService(IProductService productService) {
//		this.productService = productService;
//	}

	public void setLoadAndUnloadSquadService(
			ILoadAndUnloadSquadService loadAndUnloadSquadService) {
		this.loadAndUnloadSquadService = loadAndUnloadSquadService;
	}

	public void setPorterService(IPorterService porterService) {
		this.porterService = porterService;
	}
	
	public void setPdaUnloadTaskDao(IPDAUnloadTaskDao pdaUnloadTaskDao) {
		this.pdaUnloadTaskDao = pdaUnloadTaskDao;
	}

	public ITruckTaskDao getTruckTaskDao() {
		return truckTaskDao;
	}

	public void setTruckTaskDao(ITruckTaskDao truckTaskDao) {
		this.truckTaskDao = truckTaskDao;
	}

	public void setDepartureDao(IDepartureDao departureDao) {
		this.departureDao = departureDao;
	}

	public void setPlatformService(IPlatformService platformService) {
		this.platformService = platformService;
	}

	public void setPlatformDispatchService(
			IPlatformDispatchService platformDispatchService) {
		this.platformDispatchService = platformDispatchService;
	}

	public void setLoadAndUnloadEfficiencyTonService(
			ILoadAndUnloadEfficiencyTonService loadAndUnloadEfficiencyTonService) {
		this.loadAndUnloadEfficiencyTonService = loadAndUnloadEfficiencyTonService;
	}

	public void setCalculateTransportPathService(
			ICalculateTransportPathService calculateTransportPathService) {
		this.calculateTransportPathService = calculateTransportPathService;
	}

	/*public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}*/

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public void setVehicleAssembleBillService(
			IVehicleAssembleBillService vehicleAssembleBillService) {
		this.vehicleAssembleBillService = vehicleAssembleBillService;
	}

	public void setAssignUnloadTaskDao(IAssignUnloadTaskDao assignUnloadTaskDao) {
		this.assignUnloadTaskDao = assignUnloadTaskDao;
	}

	public void setUnloadTaskDao(IUnloadTaskDao unloadTaskDao) {
		this.unloadTaskDao = unloadTaskDao;
	}
	
	public void setHandOverBillService(IHandOverBillService handOverBillService) {
		this.handOverBillService = handOverBillService;
	}
	/** 
	 * 接送货接口:根据运单号查询货物交接、卸车情况
	 * @author dp-duyi
	 * @date 2012-11-26 上午10:41:24
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskService#queryHandOverAndUnloadByWayBillNo(java.lang.String)
	 */
	@Override
	public List<HandOverAndUnloadDto> queryHandOverAndUnloadByWayBillNo(
			String wayBillNo) {
		List<HandOverAndUnloadDto> resultDtos = unloadTaskDao.queryHandOverAndUnloadByWayBillNo(wayBillNo);
		for(HandOverAndUnloadDto tempDto : resultDtos){
			List<HandOverBillSerialNoDetailEntity> serialDtos = handOverBillService.getHandOverBillSerialNoDetailsByWayBillNo(tempDto.getWayBillNo(),tempDto.getHandOverNo());
			if(serialDtos != null && serialDtos.size() != 0){
				List<String> serialNos = new ArrayList<String>();
				for(HandOverBillSerialNoDetailEntity serialDto : serialDtos){
					serialNos.add(serialDto.getSerialNo());
				}
				tempDto.setSerialNos(serialNos);
			}
		}
		return resultDtos;
	}
	
	/**
	 * 新增卸车任务时，“快速添加”时，根据车牌号获取本部门待卸的所有单据编号及单据类型list
	 * @author 045923-foss-shiwei
	 * @date 2012-12-12 下午4:20:23
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskService#queryArrivedBillNoList(java.lang.String)
	 */
	@Override
	public List<QueryArrivedBillNoDto> queryArrivedBillNoList(String vehicleNo) {
		List<QueryArrivedBillNoDto> billList =new ArrayList<QueryArrivedBillNoDto>();
		//获取当前部门code
		String orgCode = this.querySuperiorOrgCode();
		List<QueryArrivedBillNoDto> billLists = unloadTaskDao.queryArrivedBillNoList(orgCode, vehicleNo);
		
		OrgAdministrativeInfoEntity loginOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(FossUserContext.getCurrentDeptCode());
		
		if("Y".equals(loginOrg.getSalesDepartment())){
			for(int i=0;i<billLists.size();i++){
				if(UnloadCommonUtils.isExpressHandOver(billLists.get(i).getBillNo())){
					billLists.remove(i);
					--i;
				}
			}
		}
		
		if(CollectionUtils.isNotEmpty(billLists)) {
			billList.addAll(billLists);
		}
		//判定营业部是否有对应快递分部，若有查询出该分部的到达车辆信息
		ExpressBranchSalesDeptEntity  branchSalesDeptEntity = new ExpressBranchSalesDeptEntity();
		branchSalesDeptEntity.setSalesDeptCode(orgCode);
		ExpressBranchSalesDeptEntity branchSalesDept= expressBranchSalesDeptService.
				queryByExpressBranchSalesDeptCode(branchSalesDeptEntity);
		if(branchSalesDept !=null){
			List<QueryArrivedBillNoDto> list = unloadTaskDao.queryArrivedBillNoList(branchSalesDept.getExpressBranchCode(), vehicleNo);
			if(CollectionUtils.isNotEmpty(list)){
				billList.addAll(list);
			} 
		}
		/**
		 * @desc 判断月台号是否已经分配，如果未分配，则取拿预分配的月台号
		 * @author 105795
		 * @date 2015-05-07
		 * */
		if(!org.springframework.util.CollectionUtils.isEmpty(billList)&&billList.size()>0){
			
			if(StringUtil.isEmpty(billList.get(0).getPlatformCode())){
				//去预分配中区那月台号
				String platformNo =vehicleSealService.queryPrePlatformCodeByTruckTaskId(orgCode, billList.get(0).getTruckTaskId());
				if(StringUtil.isNotEmpty(platformNo)){
					billList.get(0).setPlatformCode(platformNo);
				}
				
				
			}
			
		}		
		//返回查询结果
		return billList;
	}
	
	/**
	 * 根据交接单号获取待卸的交接单信息list
	 * @author 045923-foss-shiwei
	 * @date 2012-12-12 下午6:36:23
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskService#queryArrivedHandOverBillInfoByNo(java.util.List)
	 */
	@Override
	public List<PlanUnloadBillDto> queryArrivedHandOverBillInfoByNo(
			List<String> handOverBillNoList) {
		List<PlanUnloadBillDto> list = new ArrayList<PlanUnloadBillDto>();
		//获取当前登录部门code
		String orgCode = this.querySuperiorOrgCode();
		//构造查询条件
		QueryArrivedBillInfoByNoDto nosDto = new QueryArrivedBillInfoByNoDto();
		//交接单编号list
		nosDto.setNosList(handOverBillNoList);
		//部门code
		nosDto.setOrgCode(orgCode);
		List<PlanUnloadBillDto> list2 = unloadTaskDao.queryArrivedHandOverBillInfoByNo(nosDto);
		if(CollectionUtils.isNotEmpty(list2)){
			list.addAll(list2);
		}
		//判定营业部是否有对应快递分部，若有查询出该分部的到达车辆信息
		ExpressBranchSalesDeptEntity  branchSalesDeptEntity = new ExpressBranchSalesDeptEntity();
		branchSalesDeptEntity.setSalesDeptCode(orgCode);
		ExpressBranchSalesDeptEntity branchSalesDept= expressBranchSalesDeptService.
				queryByExpressBranchSalesDeptCode(branchSalesDeptEntity);
		if(branchSalesDept !=null){
			nosDto.setOrgCode(branchSalesDept.getExpressBranchCode());
			List<PlanUnloadBillDto> list1 =unloadTaskDao.queryArrivedHandOverBillInfoByNo(nosDto);
			if(CollectionUtils.isNotEmpty(list1)){
				list.addAll(list1);
			}
		}
		
		//返回查询结果
		return list;
	}
	
	
	/**
	 * @description 根据交接单号获取待卸的快递交接单信息list
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月27日 下午11:00:54
	 */
	@Override
	public List<PlanUnloadBillDto> queryArrivedExpressHandOverBillInfoByNo(List<String> expressHandOverBillNoList) {
	
		List<PlanUnloadBillDto> list = new ArrayList<PlanUnloadBillDto>();
		//获取当前登录部门code
		String orgCode = this.querySuperiorOrgCode();
		//构造查询条件
		QueryArrivedBillInfoByNoDto nosDto = new QueryArrivedBillInfoByNoDto();
		//交接单编号list
		nosDto.setNosList(expressHandOverBillNoList);
		//部门code
		nosDto.setOrgCode(orgCode);
		List<PlanUnloadBillDto> list2 = unloadTaskDao.queryArrivedExpressHandOverBillInfoByNo(nosDto);
		
		OrgAdministrativeInfoEntity loginOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(FossUserContext.getCurrentDeptCode());
		
		if("Y".equals(loginOrg.getSalesDepartment())){
					list2=null;
		}
		
		if(CollectionUtils.isNotEmpty(list2)){
			list.addAll(list2);
		}
		
		//返回查询结果
		return list;
	}	
	
	
	/**
	 * 根据配载车次号获取待卸的配载单信息list
	 * @author 045923-foss-shiwei
	 * @date 2012-12-12 下午6:36:37
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskService#queryArrivedVehicleAssembleBillInfoByNo(java.util.List)
	 */
	@Override
	public List<PlanUnloadBillDto> queryArrivedVehicleAssembleBillInfoByNo(
			List<String> vehicleAssembleBillNoList) {
		//获取当前登录部门code
		String orgCode = this.querySuperiorOrgCode();
		//构造查询条件
		QueryArrivedBillInfoByNoDto nosDto = new QueryArrivedBillInfoByNoDto();
		//配载单编号list
		nosDto.setNosList(vehicleAssembleBillNoList);
		//部门code
		nosDto.setOrgCode(orgCode);
		//返回查询结果
		return unloadTaskDao.queryArrivedVehicleAssembleBillInfoByNo(nosDto);
	}

	/**
	 * 新增卸车任务基本信息
	 * @author 045923-foss-shiwei
	 * @date 2012-12-13 下午2:15:04
	 */
	@Override
	public int addUnloadTaskBasicInfo(UnloadTaskEntity baseEntity) {
		//新增卸车任务
		unloadTaskDao.addUnloadTaskBasicInfo(baseEntity);
		//返回处理成功
		return FossConstants.SUCCESS;
	}

	/**
	 * 新增卸车任务单据信息
	 * @author 045923-foss-shiwei
	 * @date 2012-12-13 下午2:17:02
	 */
	@Override
	public int addUnloadTaskBillDetailList(List<UnloadBillDetailEntity> billDetailList,String unloadType) {
		List<ArriveBillDto> dtoList = new ArrayList<ArriveBillDto>();
		List<UnloadBillDetailDto> updateDtoList = new ArrayList<UnloadBillDetailDto>();
		//调用分配卸车任务服务，来判断待保存的单据状态是否为“未分配"
		for(UnloadBillDetailEntity bill : billDetailList){
			ArriveBillDto dto = new ArriveBillDto();
			UnloadBillDetailDto updateDto = new UnloadBillDetailDto();
			dto.setBillNo(bill.getBillNo());
			updateDto.setBillNo(bill.getBillNo());
			if(StringUtils.equals(unloadType, UnloadConstants.UNLOAD_TASK_TYPE_SHORT_DISTANCE)){
				updateDto.setBillType(UnloadConstants.BILL_TYPE_HANDOVER);
			}else if(StringUtils.equals(unloadType, UnloadConstants.UNLOAD_TASK_TYPE_LONG_DISTANCE)){
				updateDto.setBillType(UnloadConstants.BILL_TYPE_VEHICLEASSEMBLE);
			}else if(StringUtils.equals(unloadType, UnloadConstants.UNLOAD_TASK_TYPE_BUSINESS)){
				//272681 商务专递的单据类型
				updateDto.setBillType(UnloadConstants.BILL_TYPE_AIR_HANDOVERTYPE);
			}else{
				//此处可以为空
			}
			updateDtoList.add(updateDto);
			dtoList.add(dto);
			// 272681
			List<ArriveBillDto> queriedList = new ArrayList<ArriveBillDto>();
			if(StringUtils.equals(unloadType, UnloadConstants.UNLOAD_TASK_TYPE_BUSINESS)){
			//272681新增卸车任务前查询商务专递单据状态
			    queriedList = assignUnloadTaskDao.queryAirBusinessBillState(dtoList);
			}else{
				queriedList = assignUnloadTaskDao.queryTransferBillState(dtoList);
			}
			if(queriedList == null || queriedList.size() == 0){
				throw new TfrBusinessException("不存在单据编号为：" + bill.getBillNo() + "的任务车辆单据明细！");
			}
			//如果已分配，则抛异常提示
			if(!StringUtils.equals(queriedList.get(0).getAssignState(), TaskTruckConstant.BILL_ASSIGN_STATE_UNASSIGN)){
				throw new TfrBusinessException("单据：" + bill.getBillNo() + "不是待分配状态！");
			}
		}
		if(StringUtils.equals(unloadType, UnloadConstants.UNLOAD_TASK_TYPE_BUSINESS)){
			//更新商务专递的单据状态272681
			this.updateArriveBusinessAirBillState(updateDtoList, TaskTruckConstant.BILL_ASSIGN_STATE_ASSIGNED,UnloadConstants.UNLOAD_TASK_STATE_UNLOADING);
		}else{
		//更改车辆任务单据状态
		this.updateArriveBillState(updateDtoList, TaskTruckConstant.BILL_ASSIGN_STATE_UNLOADING);
		}
		//插入卸车任务单据明细表
		if(billDetailList.size()>0){
			unloadTaskDao.addUnloadTaskBillDetailList(billDetailList);
		}
		
		return FossConstants.SUCCESS;
	}

	/**
	 * 新增卸车任务运单明细list
	 * @author 045923-foss-shiwei
	 * @date 2012-12-13 下午2:18:27
	 */
	@Override
	public int addUnloadTaskWaybillDetailList(List<UnloadWaybillDetailEntity> waybillDetailList) {
		//新增卸车任务运单明细
		if(CollectionUtils.isNotEmpty(waybillDetailList)){
			unloadTaskDao.addUnloadTaskWaybillDetailList(waybillDetailList);
		}
		//返回处理成功
		return FossConstants.SUCCESS;
	}

	/**
	 * 新增卸车任务流水号明细list
	 * @author 045923-foss-shiwei
	 * @date 2012-12-13 下午2:19:21
	 */
	@Override
	public int addUnloadTaskSerialNoDetailList(List<UnloadSerialNoDetailEntity> serialNoDetailList) {
		//新增卸车任务流水号明细
		if(CollectionUtils.isNotEmpty(serialNoDetailList)){
			unloadTaskDao.addUnloadTaskSerialNoDetailList(serialNoDetailList);
		}
		//返回处理成功
		return FossConstants.SUCCESS;
	}

	/**
	 * 新增理货员参与卸车情况
	 * @author 045923-foss-shiwei
	 * @date 2012-12-13 下午2:21:30
	 */
	@Override
	public int addLoaderParticipationList(List<LoaderParticipationEntity> loaderList) {
		//新增理货员
		if(CollectionUtils.isNotEmpty(loaderList)){
			unloadTaskDao.addLoaderParticipationList(loaderList);
		}
		//返回处理成功
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 计算卸车任务预计完成时间
	 * @author 045923-foss-shiwei
	 * @date 2012-12-28 上午8:58:43
	 */
	@Override
	public Date calculatePlanFinishTime(String orgCode,double weightTotal,double volumeTotal,Date unloadStartTime){
		LoadAndUnloadEfficiencyTonEntity efficiencyEntity = loadAndUnloadEfficiencyTonService.queryLoadAndUnloadEfficiencyTonByOrgCode(orgCode);
		//如果获取不到装卸效率
		if(efficiencyEntity == null){
			//throw new TfrBusinessException("本部门没有装卸车标准基础资料，无法计算月台放空时间！");
			return null;
		}
		if(StringUtils.isNotBlank(efficiencyEntity.getId())){
			//体积卸车时间，分钟/方
			double unloadVolumeMin = volumeTotal*efficiencyEntity.getUnloadVolumeStd().doubleValue();
			//重量卸车时间，分钟/吨
			double unloadWeightMin = (weightTotal/UnloadConstants.KILO_MUTIPLY)*efficiencyEntity.getUnloadWeightStd().doubleValue();
			//取叫大值作为卸车时间
			double planUnloadTime = unloadVolumeMin > unloadWeightMin ? unloadVolumeMin : unloadWeightMin;
			/*
			 * 计算月台预计放空时间
			 * 此处+1000，是防止重量、体积过小导致的占用时间等于放空时间
			 */
			long planEndTime;
			if(planUnloadTime > 0){
				 planEndTime = unloadStartTime.getTime() + new Double(planUnloadTime*UnloadConstants.KILO_MUTIPLY*UnloadConstants.SIXTY_MUTIPLY).longValue();
			}else{
				 planEndTime = unloadStartTime.getTime() + UnloadConstants.KILO_MUTIPLY;
			}
			//返回放空时间
			return new Date(planEndTime);
		}
		return null;
	}
	
	/**
	 * 计算月台放空时间，手动建立卸车任务时
	 * @author 045923-foss-shiwei
	 * @date 2013-6-3 下午8:55:51
	 */
	private Date calculatePlanFinishTimeForNoPDA(String orgCode,double weightTotal,double volumeTotal,Date unloadStartTime){
		LoadAndUnloadEfficiencyTonEntity efficiencyEntity = loadAndUnloadEfficiencyTonService.queryLoadAndUnloadEfficiencyTonByOrgCode(orgCode);
		//如果获取不到装卸效率
		if(efficiencyEntity == null){
			throw new TfrBusinessException("本部门没有装卸车标准基础资料，无法计算月台放空时间，请联系系统管理员！");
		}
		if(StringUtils.isNotBlank(efficiencyEntity.getId())){
			//体积卸车时间，方/分钟
			double unloadVolumeMin = volumeTotal*efficiencyEntity.getUnloadVolumeStd().doubleValue();
			//重量卸车时间，吨/分钟
			double unloadWeightMin = (weightTotal/UnloadConstants.KILO_MUTIPLY)*efficiencyEntity.getUnloadWeightStd().doubleValue();
			//取叫大值作为卸车时间
			double planUnloadTime = unloadVolumeMin > unloadWeightMin ? unloadVolumeMin : unloadWeightMin;
			/*
			 * 计算月台预计放空时间
			 * 此处+1000，是防止重量、体积过小导致的占用时间等于放空时间
			 */
			long planEndTime = unloadStartTime.getTime() + new Double(planUnloadTime*UnloadConstants.KILO_MUTIPLY*UnloadConstants.SIXTY_MUTIPLY).longValue() + UnloadConstants.KILO_MUTIPLY;
			//返回放空时间
			return new Date(planEndTime);
		}
		return null;
	}

	/**
	 * 新增卸车、确认卸车时，更新到达单据状态
	 * @author 042795-foss-duyi
	 * @date 2013-1-12 上午8:27:43
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskService#updateArriveBillState(java.util.List, java.lang.String)
	 */
	@Transactional
	@Override
	public int updateArriveBillState(List<UnloadBillDetailDto> bills,String state){
		
		List<ArriveBillDto> arriveTransferBills = new ArrayList<ArriveBillDto>();
		List<ArriveBillDto> arrivePickUpBills = new ArrayList<ArriveBillDto>();
		List<ArriveBillDto> arriveAirBills = new ArrayList<ArriveBillDto>();
		ArriveBillDto arriveBill;
		String billNo;
		for(UnloadBillDetailDto unloadBill : bills){
			//到达单据
			arriveBill = new ArriveBillDto();
			arriveBill.setBillNo(unloadBill.getBillNo());
			arriveBill.setAssignState(state);
			if(UnloadConstants.BILL_TYPE_PICKUP.equals(unloadBill.getBillType())){
				arrivePickUpBills.add(arriveBill);
			}else if(UnloadConstants.BILL_TYPE_AIRBILL.equals(unloadBill.getBillType())){
				arriveAirBills.add(arriveBill);
			}else{
				arriveTransferBills.add(arriveBill);
			}
		}
		//修改到达单据状态
		if(CollectionUtils.isNotEmpty(arriveTransferBills)){
			assignUnloadTaskDao.updateArriveTransferBillState(arriveTransferBills);
			if(TaskTruckConstant.BILL_ASSIGN_STATE_UNLOADED.equals(state)){
				// 修改任务车辆状态
				billNo = arriveTransferBills.get(0).getBillNo();
				TruckTaskHandOverDto truckTask = truckTaskDao.queryTruckTaskIdByHandOverBill(billNo);
				if(truckTask != null){
					int unfinishUnloadBillQty = pdaUnloadTaskDao.queryUnfinishUnloadedValideBillQty(truckTask.getTruckTaskDettailId());
					if(unfinishUnloadBillQty == 0){
						TruckTaskDetailEntity truckTaskDetail = new TruckTaskDetailEntity();
						truckTaskDetail.setId(truckTask.getTruckTaskDettailId());
						truckTaskDetail.setState(TaskTruckConstant.TASK_TRUCK_STATE_UNLOADED);
						pdaUnloadTaskDao.updateTruckTaskDetailState(truckTaskDetail);
						int unfinishUnloadTruckTaskDetail = pdaUnloadTaskDao.queryUnfinishUnloadedTruckTaskDetailQty(truckTask.getTruckTaskId());
						if(unfinishUnloadTruckTaskDetail == 0){
							TruckTaskEntity truckTaskEntity = new TruckTaskEntity();
							truckTaskEntity.setId(truckTask.getTruckTaskId());
							truckTaskEntity.setState(TaskTruckConstant.TASK_TRUCK_STATE_UNLOADED);
							pdaUnloadTaskDao.updateTruckTaskState(truckTaskEntity);
						}
						//调用ECS系统接口开关
						//营业部卸车不同步数据到WK---332219
						if(!StringUtils.equals(truckTask.getHandOverType(), LoadConstants.HANDOVER_TYPE_SALES_DEPARTMENT)){
							if (configurationParamsService.queryTfrSwitch4Ecs()) {
								/**新增同步给快递系统 车辆任务信息的消息通知任务  以车辆明细更新为通知单位  283250*/
								LOGGER.error("调用插入临时表，通过JOB推送数据给悟空");
								//根据车辆任务明细ID查询出悟空交接单
								List<WkHandOverBillEntity> wkHandoverbillList = wkBillAddTfrNotifyService.queryWkHandOverBillByTruckTaskDetailId(truckTask.getTruckTaskDettailId());
								//已交接单为单位插入到临时表
								for (WkHandOverBillEntity entity : wkHandoverbillList) {
									String paramJson = entity.getHandoverBillNo()+"," + entity.getOperationOrgCode();
									//String currentUserInfo = DepartureHandle.getCurrentUserCode() + "," + DepartureHandle.getCurrentUserName() + "," + DepartureHandle.getCurrentOrgCode();
								tfrNotifyService.addTfrNotifyEntity(new TfrNotifyEntity(
										UUIDUtils.getUUID(),NotifyWkConstants.NOTIFY_TYPE_UNLOAD_UPDATE,
										truckTask.getTruckTaskDettailId(), BusinessSceneConstants.WK_HANDORVERBILL_UNLOAD_UPDATE, null,paramJson));
								}
							}
						}
						
					}
				}
			}
		}
		if(CollectionUtils.isNotEmpty(arrivePickUpBills)){
			assignUnloadTaskDao.updateArrivePickUpBillState(arrivePickUpBills);
		}
		if(CollectionUtils.isNotEmpty(arriveAirBills)){
			//assignUnloadTaskDao.updateArriveAirBillState(arriveAirBills);
			/**
			 * 修改正单交接单卸车状态为 卸车中 （商务专递）263072 linyuzhu  2015-9-19 10:13:59
			 */
			if(TaskTruckConstant.BILL_ASSIGN_STATE_UNLOADING.equals(state)){
				assignUnloadTaskDao.updateAirHandOverBillState(arriveAirBills);
			}
		}
		
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 新增卸车任务
	 * @author 045923-foss-shiwei
	 * @throws Exception 
	 * @date 2012-12-13 下午2:44:11
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskService#addUnloadTask(com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadTaskAddnewDto)
	 */
	@Override
	@Transactional
	public String addUnloadTask(UnloadTaskAddnewDto addnewDto) throws Exception {
		//获取基本信息
		UnloadTaskEntity baseEntity = outPutBaseEntity(addnewDto);
		//获取单据信息列表
		List<UnloadBillDetailEntity> billDetailList = outPutBillDetailEntityList(addnewDto.getBillList(),baseEntity);
		//获取卸车员参与情况列表
		List<LoaderParticipationEntity> loaderList = outPutLoaderParticipationList(baseEntity,addnewDto.getLoaderList(),0);
		//若传入有月台，则需占用月台
		if(!StringUtils.isBlank(baseEntity.getPlatformNo())){
			//计算单中的总体积、总重量
			double totalWeight = 0;
			double totalVolume = 0;
			for(UnloadBillDetailEntity bill : billDetailList){
				//累加总体积
				totalVolume = totalVolume + bill.getVolume().doubleValue();
				//累加总重量
				totalWeight = totalWeight + bill.getWeight().doubleValue();
			}
			//计算计划完成卸车时间
			Date endTime = this.calculatePlanFinishTimeForNoPDA(baseEntity.getUnloadOrgCode(), totalWeight/UnloadConstants.KILO_MUTIPLY, totalVolume, baseEntity.getUnloadStartTime());
			baseEntity.setPlanCompleteTime(endTime);
			//获取月台虚拟code
			PlatformEntity  platform = platformService.queryPlatformByCode(baseEntity.getUnloadOrgCode(), baseEntity.getPlatformNo());
			String platformVirtualCode = null;
			if(platform == null){
				throw new TfrBusinessException("月台不是当前部门所有！");
			}else{
				platformVirtualCode = platform.getVirtualCode();
			}
			/*
			 * 占用月台
			 */
			PlatformDistributeEntity distributeEntity = new PlatformDistributeEntity();
			//月台虚拟code
			distributeEntity.setPlatformNo(platformVirtualCode);
			//车牌号
			distributeEntity.setVehicleNo(baseEntity.getVehicleNo());
			//卸车任务编号
			distributeEntity.setLoadTaskNo(baseEntity.getUnloadTaskNo());
			//开始卸车时间
			distributeEntity.setUseStartTime(baseEntity.getUnloadStartTime());
			//结束卸车时间
			distributeEntity.setUseEndTime(endTime);
			//卸车部门
			distributeEntity.setTransferCenterNo(baseEntity.getUnloadOrgCode());
			//占用类型：实际使用
			distributeEntity.setType(PlatformDispatchConstants.PLATFORMDISPATCH_TYPE_ACTUALUSE);
			//卸车占用
			distributeEntity.setScheduleSource(PlatformDispatchConstants.PLATFORMDISPATCH_SCHEDULESOURCE_UNLOAD);
			//调用月台服务，占用月台
			platformDispatchService.updatePlatformStatusForUsing(distributeEntity);
		}
		//派送发短信代码回滚
//		addJobTodo(baseEntity.getId(),FossUserContext.getCurrentInfo().getEmpCode(),FossUserContext.getCurrentDeptCode());
		//新增卸车任务基本信息
		this.addUnloadTaskBasicInfo(baseEntity);
//		
//		//PC卸车完成时，往接送货的临时表中插值，确定发短信通知
//		AutoTaskDTO dto = new AutoTaskDTO();
//		dto.setId(UUIDUtils.getUUID());
//		dto.setTaskDetailId(baseEntity.getId());
//		dto.setTaskDetailType(UnloadConstants.UNLOAD_FOR_PKP);
//		dto.setStockOrgCode("N/A");
//		sharedDao.insertTempForPKP(dto);
	
		//，，
		
		
		
		
		//新增单据明细
		this.addUnloadTaskBillDetailList(billDetailList,baseEntity.getUnloadType());
		//新增卸车员明细
		this.addLoaderParticipationList(loaderList);
		//营业部交接卸车，改交接单状态  by360903 2016年12月13日 10:30:36
		updateHandOverBillStatus(addnewDto);
		
		boolean tfrSwitch4Ecs = configurationParamsService.queryTfrSwitch4Ecs();
		LOGGER.error("同步新增卸车任务到悟空系统开关" + tfrSwitch4Ecs);
		if (tfrSwitch4Ecs) {

			OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
					.queryOrgAdministrativeInfoByCode(baseEntity.getUnloadOrgCode());
			// 营业部
			if (!StringUtils.equals(FossConstants.YES, org.getSalesDepartment())) {
				addExpressUnloadTask(addnewDto, baseEntity, billDetailList, loaderList);
			}
		}
		
		//返回卸车任务编号
		return baseEntity.getUnloadTaskNo();
	}
	
	
	/**
	 * Foss同步新建卸车任务到悟空系统
	 * @author 328768-foss-gaojianfu
	 * @throws Exception 
	 * @date 2016年4月27日 下午5:35:45
	 */
	@Override
	public void addExpressUnloadTask(UnloadTaskAddnewDto addnewDto,UnloadTaskEntity baseEntity,List<UnloadBillDetailEntity> billDetailList,List<LoaderParticipationEntity> loaderList) throws  Exception{
		//新增快递卸载任务DTO
		ExpressUnloadTaskAddnewDto expressUnloadTaskAddnewDto=new ExpressUnloadTaskAddnewDto();
		
				
		//从卸载任务VO中获取数据封装到快递卸载任务DTO
		expressUnloadTaskAddnewDto.setUnloadTaskNo(baseEntity.getUnloadTaskNo());
		
		//设置 车牌号
		expressUnloadTaskAddnewDto.setVehicleNo(addnewDto.getVehicleNo());
		
		//设置 卸车类型
		expressUnloadTaskAddnewDto.setUnloadType(baseEntity.getUnloadType());
		//设置 当前部门编号
		expressUnloadTaskAddnewDto.setCreateOrgCode(FossUserContext.getCurrentDeptCode());
		
		//设置月台号
		expressUnloadTaskAddnewDto.setPlatformNo(addnewDto.getPlatformCode());
		//设置  建立任务部门编号
		expressUnloadTaskAddnewDto.setUnloadOrgCode(FossUserContext.getCurrentDeptCode());
		//设置  创建人工号
		expressUnloadTaskAddnewDto.setCreateNo(FossUserContext.getCurrentInfo().getEmpCode());
		//设置卸车方式
		expressUnloadTaskAddnewDto.setUnloadWay(baseEntity.getUnloadWay());
		//设置 创建时间
		expressUnloadTaskAddnewDto.setCreateTime(new Date());
		List<ExpressUnloadTaskDetailDto> list=new ArrayList<ExpressUnloadTaskDetailDto>();
		UnloadBillDetailEntity unloadBillDetailEntity=null;
		ExpressUnloadTaskDetailDto expressUnloadTaskDetailDto=null;
		
				
		//遍历待卸单据列表中的数据
		for(Iterator<UnloadBillDetailEntity> its=billDetailList.iterator();its.hasNext();){
			
			  unloadBillDetailEntity=its.next();
			  //如果是快递单据就添加到快递单集合中
			  if(UnloadCommonUtils.isExpressHandOver(unloadBillDetailEntity.getBillNo())){
				
				expressUnloadTaskDetailDto=new ExpressUnloadTaskDetailDto();
				//设置 卸车任务编号
				expressUnloadTaskDetailDto.setUnloadTaskNo(baseEntity.getUnloadTaskNo());
				//设置 交接单编号
				expressUnloadTaskDetailDto.setHandoverBillNo(unloadBillDetailEntity.getBillNo());
				//设置 件数
				expressUnloadTaskDetailDto.setGoodsQty(unloadBillDetailEntity.getPieces().longValue());
				//设置 体积
				expressUnloadTaskDetailDto.setVolume(unloadBillDetailEntity.getVolume());
				//设置 重量
				expressUnloadTaskDetailDto.setWeight(unloadBillDetailEntity.getWeight());
				
				list.add(expressUnloadTaskDetailDto);
			}
		}
		
		//设置创建 卸车任务明细list
		expressUnloadTaskAddnewDto.setCreateUnloadTaskDetailList(list);
		
		List<ExpressLoaderParticipateDto> loaderParticipateList=new ArrayList<ExpressLoaderParticipateDto>();
		ExpressLoaderParticipateDto expressLoaderParticipateDto=null;
		LoaderParticipationEntity loaderParticipationEntity=null;
		
		
		//遍历理货员明细列表中的数据
		for(Iterator<LoaderParticipationEntity> its=loaderList.iterator();its.hasNext();){
			expressLoaderParticipateDto=new ExpressLoaderParticipateDto();
			loaderParticipationEntity=its.next();
			//设置 卸车任务编号
			expressLoaderParticipateDto.setTaskNo(baseEntity.getUnloadTaskNo());
			//设置 理货员姓名
			expressLoaderParticipateDto.setLoaderName(loaderParticipationEntity.getLoaderName());
			//设置 理货员工号
			expressLoaderParticipateDto.setLoaderCode(loaderParticipationEntity.getLoaderCode());
			//设置 装卸车队名称  此字段非必须
			expressLoaderParticipateDto.setLoadOrgName(loaderParticipationEntity.getLoadOrgName());
			//设置 加入时间
			expressLoaderParticipateDto.setJoinTime(loaderParticipationEntity.getJoinTime());
			//设置 是否是创建人 
			if(StringUtils.equals(loaderParticipationEntity.getLoaderName(), FossUserContext.getCurrentInfo().getEmpName())){
				expressLoaderParticipateDto.setIscreator(FossConstants.YES);
			}else{
				expressLoaderParticipateDto.setIscreator(FossConstants.NO);
			}
			
			loaderParticipateList.add(expressLoaderParticipateDto);
		}
		//设置创建 卸车人员参与表 list
		expressUnloadTaskAddnewDto.setCreateLoaderParticipateList(loaderParticipateList);
		
		// 获取objectMapper
		ObjectMapper objectMapper = JSONUtils.obtainObjectMapper();
		// 设置时间转换格式
		SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT);
		// 设置到objectMapper
		objectMapper.setDateFormat(dateFormat);
			
		
		String requestJsonStr=objectMapper.writeValueAsString(expressUnloadTaskAddnewDto);
		LOGGER.info("新建卸车任务同步到悟空系统参数"+requestJsonStr);
		//调用同步数据接口
		FossToWKResponseEntity fossToWKResponseEntity=fossToWkService.syncNewExpressUnloadTaskToWk(requestJsonStr);
		if(fossToWKResponseEntity==null){
			LOGGER.error("Foss同步新建卸车任务到悟空系统失败!");
			throw new TfrBusinessException("Foss同步新建卸车任务到悟空系统失败!");
		}
		if("1".equals(fossToWKResponseEntity.getStatus())){
			LOGGER.info("Foss同步新建卸车任务到悟空系统成功!");
		}else{
			LOGGER.error("Foss同步新建卸车任务到悟空系统失败，错误信息："+fossToWKResponseEntity.getExMsg());
			throw new TfrBusinessException("Foss同步新建卸车任务到悟空系统，错误信息："+fossToWKResponseEntity.getExMsg());
		}
		
	}
	
	/**
	 * @description FOSS同步确认卸车任务到WK
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @throws Exception 
	 * @update 2016年5月10日 下午2:21:33
	 */
	@Transactional
	public void confirmExpressUnloadTask(ConfirmUnloadTaskDto confirmUnloadTaskDto) throws Exception{
		
		//快递同步卸载任务DTO
		ExpressConfirmUnloadTaskDto expressConfirmUnloadTaskDto=new ExpressConfirmUnloadTaskDto();
		
		//获取卸车任务id
		String unloadTaskId = confirmUnloadTaskDto.getUnloadTaskId();
		//构造卸车任务实体
		UnloadTaskEntity baseEntity = unloadTaskDao.queryUnloadTaskBaseInfoById(unloadTaskId);
		
		//设置 卸车任务编号
		expressConfirmUnloadTaskDto.setUnloadTaskNo(baseEntity.getUnloadTaskNo());
		//设置 卸车开始时间
		expressConfirmUnloadTaskDto.setUnloadStartTime(baseEntity.getUnloadStartTime());
		//设置 卸车结束时间
		expressConfirmUnloadTaskDto.setUnloadEndTime(new Date());
		//设置 车牌号
		expressConfirmUnloadTaskDto.setVehicleNo(baseEntity.getVehicleNo());
		//设置 工号
		expressConfirmUnloadTaskDto.setUpdateNo(FossUserContext.getCurrentInfo().getEmpCode());
		//设置 当前操作部门编号
		expressConfirmUnloadTaskDto.setUpdateOrgCode(FossUserContext.getCurrentDeptCode());
		//少货快递卸车运单/包/笼单据明细集合
		List<ExpressUnloadWaybillDetailDto> lackList=new ArrayList<ExpressUnloadWaybillDetailDto>();
	
		//少货快递交接单明集合
		List<ExpressUnloadWaybillDetailDto> lackHandOverBillList=new ArrayList<ExpressUnloadWaybillDetailDto>();
		
		//快递卸车单据明细DTO
		ExpressUnloadWaybillDetailDto unloadWaybillDetailDto=null;
		
		if(StringUtils.equals(baseEntity.getUnloadType(),UnloadConstants.UNLOAD_TASK_TYPE_LONG_DISTANCE)){
			List<ConfirmUnloadTaskBillsDto> lackVehicleAssembleBillList=confirmUnloadTaskDto.getLackVehicleAssembleBillList();
			
			for(ConfirmUnloadTaskBillsDto confirmUnloadTaskBillsDto:lackVehicleAssembleBillList){
				//长途时如果是快递货添加到少货交接单list中
				if(UnloadCommonUtils.isExpressHandOver(confirmUnloadTaskBillsDto.getVehicleAssembleNo())){
					unloadWaybillDetailDto=new ExpressUnloadWaybillDetailDto();
					//设置  卸车任务编号 
					unloadWaybillDetailDto.setUnloadTaskNo(confirmUnloadTaskDto.getUnloadTaskNo());
					//设置 交接单编号 
					unloadWaybillDetailDto.setHandoverBillNo(confirmUnloadTaskBillsDto.getVehicleAssembleNo());
					lackHandOverBillList.add(unloadWaybillDetailDto);
				}
				
			}
			
			for(ConfirmUnloadTaskBillsDto confirmUnloadTaskBillsDto:confirmUnloadTaskDto.getLackHandOverBillList()){
				
				if(StringUtils.isNotEmpty(confirmUnloadTaskBillsDto.getCargoNo())
						&&StringUtils.isNotEmpty(confirmUnloadTaskBillsDto.getCargoType())){
					unloadWaybillDetailDto=new ExpressUnloadWaybillDetailDto();
					
					//设置  卸车任务编号 
					unloadWaybillDetailDto.setUnloadTaskNo(confirmUnloadTaskDto.getUnloadTaskNo());
					//设置 交接单编号 
					unloadWaybillDetailDto.setHandoverBillNo(confirmUnloadTaskBillsDto.getVehicleAssembleNo());
					//设置  件号
					unloadWaybillDetailDto.setCargoNo(confirmUnloadTaskBillsDto.getCargoNo());
					//设置 件类型
					unloadWaybillDetailDto.setCargoType(confirmUnloadTaskBillsDto.getCargoType());
					
					lackList.add(unloadWaybillDetailDto);
				}
				
			}
		}else{
			//确认卸车任务时，提交来的单据，包括配载单、交接单、运单、流水号Dto
			ConfirmUnloadTaskBillsDto confirmUnloadTaskBillsDto=null;
			
			//遍历少货运单list
			for(Iterator<ConfirmUnloadTaskBillsDto> its=confirmUnloadTaskDto.getLackWaybillList().iterator();its.hasNext();){
				
				confirmUnloadTaskBillsDto=its.next();
				//去除少货运单list中的非快递货
				if(StringUtils.isNotEmpty(confirmUnloadTaskBillsDto.getCargoNo())
						&&StringUtils.isNotEmpty(confirmUnloadTaskBillsDto.getCargoType())){
					unloadWaybillDetailDto=new ExpressUnloadWaybillDetailDto();
					
					//设置  卸车任务编号 
					unloadWaybillDetailDto.setUnloadTaskNo(confirmUnloadTaskDto.getUnloadTaskNo());
					//设置 交接单编号 
					unloadWaybillDetailDto.setHandoverBillNo(confirmUnloadTaskBillsDto.getHandOverBillNo());
					//设置  件号
					unloadWaybillDetailDto.setCargoNo(confirmUnloadTaskBillsDto.getCargoNo());
					//设置 件类型
					unloadWaybillDetailDto.setCargoType(confirmUnloadTaskBillsDto.getCargoType());
					
					lackList.add(unloadWaybillDetailDto);
				}
			}
			
			
			//遍历少货交接单list
			for(ConfirmUnloadTaskBillsDto confirmUnloadTaskBill:confirmUnloadTaskDto.getLackHandOverBillList()){
				//去除非快递交接单
				if(UnloadCommonUtils.isExpressHandOver(confirmUnloadTaskBill.getHandOverBillNo())){
					unloadWaybillDetailDto=new ExpressUnloadWaybillDetailDto();
					//设置  卸车任务编号 
					unloadWaybillDetailDto.setUnloadTaskNo(confirmUnloadTaskDto.getUnloadTaskNo());
					//设置 交接单编号 
					unloadWaybillDetailDto.setHandoverBillNo(confirmUnloadTaskBill.getBillNo());
					lackHandOverBillList.add(unloadWaybillDetailDto);
				}
			}
		}
		
		//设置 少货运单集合
		expressConfirmUnloadTaskDto.setLackWayBillList(lackList);
		
		//设置 少货交接单集合
		expressConfirmUnloadTaskDto.setLackHandOverBillList(lackHandOverBillList);
		
		//多货快递卸车单据明细集合
		List<ExpressUnloadWaybillDetailDto> moreList=new ArrayList<ExpressUnloadWaybillDetailDto>();
		//快递卸车单据明细DTO
		ExpressUnloadWaybillDetailDto moreUnloadWaybillDetailDto=null;
		//确认卸车任务时，提交来的单据，包括配载单、交接单、运单、流水号Dto
		ConfirmUnloadTaskBillsDto moreConfirmUnloadTaskBillsDto=null;
		//遍历
		for(Iterator<ConfirmUnloadTaskBillsDto> its=confirmUnloadTaskDto.getMoreSerialNoList().iterator();its.hasNext();){
			
			moreConfirmUnloadTaskBillsDto=its.next();
			//去除多货卸车运单中的零担货
			if(StringUtils.isNotEmpty(moreConfirmUnloadTaskBillsDto.getCargoNo())
					&&StringUtils.isNotEmpty(moreConfirmUnloadTaskBillsDto.getCargoType())){
				moreUnloadWaybillDetailDto=new ExpressUnloadWaybillDetailDto();
				//设置 卸车任务编号
				moreUnloadWaybillDetailDto.setUnloadTaskNo(confirmUnloadTaskDto.getUnloadTaskNo());
				//设置 单据号
				moreUnloadWaybillDetailDto.setHandoverBillNo(moreConfirmUnloadTaskBillsDto.getHandOverBillNo());
				//设置  件号
				moreUnloadWaybillDetailDto.setCargoNo(moreConfirmUnloadTaskBillsDto.getCargoNo());
				//设置  件类型
				moreUnloadWaybillDetailDto.setCargoType(moreConfirmUnloadTaskBillsDto.getCargoType());
				
				moreList.add(moreUnloadWaybillDetailDto);
			}
		}
		//设置 多货集合
		expressConfirmUnloadTaskDto.setMoreWayBillList(moreList);
		
		// 获取objectMapper
		ObjectMapper objectMapper = JSONUtils.obtainObjectMapper();
		// 设置时间转换格式
		SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT);
		// 设置到objectMapper
		objectMapper.setDateFormat(dateFormat);
			
		String requestJsonStr=objectMapper.writeValueAsString(expressConfirmUnloadTaskDto);			
		LOGGER.info("FOSS同步确认卸车任务到WK参数"+requestJsonStr);
		
		//调用同步数据接口
		FossToWKResponseEntity fossToWKResponseEntity=fossToWkService.syncConfirmExpressUnloadTaskToWk(requestJsonStr);
		if(fossToWKResponseEntity==null){
			LOGGER.error("Foss同步确认卸车任务到悟空系统失败!");
			throw new TfrBusinessException("Foss同步确认卸车任务到悟空系统失败!");
		}
		if("1".equals(fossToWKResponseEntity.getStatus())){
			LOGGER.info("Foss同步确认卸车任务到悟空系统成功!");
		}else{
			LOGGER.error("Foss同步确认卸车任务到悟空系统失败，错误信息："+fossToWKResponseEntity.getExMsg());
			throw new TfrBusinessException("Foss同步确认卸车任务到悟空系统，错误信息："+fossToWKResponseEntity.getExMsg());
		}
	}
	
	/**
	 * 私有方法，构造卸车任务基本信息实体
	 * @author 045923-foss-shiwei
	 * @date 2012-12-13 下午2:49:29
	 */
	private UnloadTaskEntity outPutBaseEntity(UnloadTaskAddnewDto addnewDto){
		//取出一条单据信息
		UnloadBillDetailEntity billEntity = addnewDto.getBillList().get(0);
		//创建基本信息
		UnloadTaskEntity baseEntity = new UnloadTaskEntity();
		//获取上级部门
		OrgAdministrativeInfoEntity orgEntity = loadService.querySuperiorOrgByOrgCode(FossUserContext.getCurrentInfo().getCurrentDeptCode());
		if(orgEntity == null){
			throw new TfrBusinessException("获取本部门的上级组织失败(包括营业部、派送部、外场、总调)！");
		}
		//获取当前部门编码、名称
		String orgCode = orgEntity.getCode();
		//当前部门名称
		String orgName = orgEntity.getName();
		//获取车牌号
		String vehicleNo = addnewDto.getVehicleNo();
		//ID
		baseEntity.setId(UUIDUtils.getUUID());
		
		/*
		 * 生成卸车任务编号
		 */
		//String taskNo = tfrCommonService.generateSerialNumberRequireNew(TfrSerialNumberRuleEnum.XC, orgCode);
		String taskNo = billNumService.generateUnLoadTaskNo(orgCode);
		baseEntity.setUnloadTaskNo(taskNo);
		//车牌号
		baseEntity.setVehicleNo(vehicleNo);
		//月台号
		baseEntity.setPlatformNo(addnewDto.getPlatformCode());
		//月台ID
		baseEntity.setPlatformId(addnewDto.getPlatformId());
		//卸车开始时间
		baseEntity.setUnloadStartTime(new Date());
		//卸车结束时间，新增时，此处为空
		//任务状态
		baseEntity.setTaskState(UnloadConstants.UNLOAD_TASK_STATE_UNLOADING);
		//272681商务专递卸车类型
		if(StringUtils.equals(billEntity.getBillType(), TaskTruckConstant.BILL_TYPE_PACKAGE_HANDOVER)){
			baseEntity.setUnloadType(UnloadConstants.UNLOAD_TASK_TYPE_BUSINESS);
		}
		/*
		 * 卸车类型之短途（包括零担和快递的短途）
		 */
		
		String billNo=billEntity.getBillNo();
		if(StringUtils.equals(billEntity.getBillType(), TaskTruckConstant.BILL_TYPE_HANDOVER)
				&&(!UnloadCommonUtils.isExpressAir(billNo))
				&&(!UnloadCommonUtils.isExpressLong(billNo))){
			  baseEntity.setUnloadType(UnloadConstants.UNLOAD_TASK_TYPE_SHORT_DISTANCE);
		}
		
			
		//快递卸车类型之航空
		if(UnloadCommonUtils.isExpressAir(billNo)){
			baseEntity.setUnloadType(UnloadConstants.UNLOAD_TASK_TYPE_PACKAGE);
		}
		
		/*
		 * 卸车类型之长途
		 */
		if(StringUtils.equals(billEntity.getBillType(), TaskTruckConstant.BILL_TYPE_VEHICLEASSEMBLE)
				||UnloadCommonUtils.isExpressLong(billNo)){
			baseEntity.setUnloadType(UnloadConstants.UNLOAD_TASK_TYPE_LONG_DISTANCE);
		}
		//卸车方式
		baseEntity.setUnloadWay(UnloadConstants.UNLOAD_TASK_WAY_NO_PDA);
		//建立任务部门编码
		baseEntity.setUnloadOrgCode(orgCode);
		//建立任务部门名称
		baseEntity.setUnloadOrgName(orgName);
		//是否卸车异常
		baseEntity.setBeException(FossConstants.NO);
		//异常备注，新增时，此处为空
		return baseEntity;
	}
	
	/**
	 * 私有方法，构造待插入的单据信息列表
	 * @author 045923-foss-shiwei
	 * @date 2012-12-13 下午4:29:51
	 */
	private List<UnloadBillDetailEntity> outPutBillDetailEntityList(List<UnloadBillDetailEntity> billList,UnloadTaskEntity baseEntity){
		List<UnloadBillDetailEntity> newBillList = new ArrayList<UnloadBillDetailEntity>();
		for(UnloadBillDetailEntity bill : billList){
			//ID
			bill.setId(UUIDUtils.getUUID());
			//卸车任务ID
			bill.setUnloadTaskId(baseEntity.getId());
			//配合BI，新增创建时间
			bill.setCreateDate(new Date());
			//配合BI，新增修改时间时间
			bill.setModifyDate(new Date());
			newBillList.add(bill);
		}
		return newBillList;
	}
	
	/**
	 * 私有方法，构造待插入的卸车员参与情况列表(第三个参数，0表示新增时调用，1表示修改时调用)
	 * @author 045923-foss-shiwei
	 * @date 2012-12-13 下午6:41:24
	 */
	private List<LoaderParticipationEntity> outPutLoaderParticipationList(UnloadTaskEntity baseEntity,List<LoaderParticipationEntity> loaderList,int addnewOrModify){
		List<LoaderParticipationEntity> loadManList = new ArrayList<LoaderParticipationEntity>();
		for(LoaderParticipationEntity loader : loaderList){
			LoaderParticipationEntity loaderEntity = new LoaderParticipationEntity();
			//ID
			loaderEntity.setId(UUIDUtils.getUUID());
			//理货员姓名
			loaderEntity.setLoaderName(loader.getLoaderName());
			//理货员工号
			loaderEntity.setLoaderCode(loader.getLoaderCode());
			//获取理货员所在的装卸车队
			PorterEntity porter  = porterService.queryPorterByEmpCode(loader.getLoaderCode());
			//调用综合接口查询理货员名称、理货员所属装卸车队
			if(porter != null){
				if(StringUtils.isNotBlank(porter.getParentOrgCode())){
					//装卸车队code
					loaderEntity.setLoadOrgCode(porter.getParentOrgCode());
					LoadAndUnloadSquadEntity team = loadAndUnloadSquadService.queryLoadAndUnloadSquadByCode(porter.getParentOrgCode());
					if(team != null){
						loaderEntity.setLoadOrgName(team.getName());//装卸车队name
					}else{
						//此处留空
					}
				}
			}else{
				//此处留空
			}
			//加入任务时间
			loaderEntity.setJoinTime(baseEntity.getUnloadStartTime());
			//离开任务时间，新增，此时为空
			//是否建立任务理货员
			loaderEntity.setBeCreator(FossConstants.NO);
			//类型
			loaderEntity.setTaskType(LoadConstants.LOADER_PARTICIPATION_TRANSFER_UNLOAD);
			//任务ID
			loaderEntity.setTaskId(baseEntity.getId());
			loadManList.add(loaderEntity);
		}
		//如果为新增卸车任务，则设置当前用户为建立任务理货员
		if(addnewOrModify == 0){
			LoaderParticipationEntity mainEntity = new LoaderParticipationEntity();
			CurrentInfo ci = FossUserContext.getCurrentInfo();
			//ID
			mainEntity.setId(UUIDUtils.getUUID());
			//理货员姓名
			mainEntity.setLoaderName(ci.getEmpName());
			//理货员工号
			mainEntity.setLoaderCode(ci.getEmpCode());
			//加入任务时间
			mainEntity.setJoinTime(baseEntity.getUnloadStartTime());
			//装卸车队编号
			mainEntity.setLoadOrgCode(ci.getCurrentDeptCode());
			//装卸车队名称
			mainEntity.setLoadOrgName(ci.getCurrentDeptName());
			//离开任务时间，新增，此时为空
			//是否建立任务理货员
			mainEntity.setBeCreator(FossConstants.YES);
			//类型
			mainEntity.setTaskType(LoadConstants.LOADER_PARTICIPATION_TRANSFER_UNLOAD);
			//任务ID
			mainEntity.setTaskId(baseEntity.getId());
			loadManList.add(mainEntity);
		}
		return loadManList;
	}

	/**
	 * 根据卸车任务ID获取任务基本信息
	 * @author 045923-foss-shiwei
	 * @date 2012-12-14 上午10:20:38
	 */
	@Override
	public UnloadTaskEntity queryUnloadTaskBaseInfoById(String unloadTaskId) {
		//返回查询结果
		return unloadTaskDao.queryUnloadTaskBaseInfoById(unloadTaskId);
	}

	/**
	 * 根据卸车任务ID获取其下单据列表
	 * @author 045923-foss-shiwei
	 * @date 2012-12-14 上午10:21:24
	 */
	@Override
	public List<UnloadBillDetailEntity> queryUnloadTaskBillDetailListById(String unloadTaskId) {
		//返回查询结果
		return unloadTaskDao.queryUnloadTaskBillDetailListById(unloadTaskId);
	}

	/**
	 * 根据卸车任务ID获取其下卸车员列表
	 * @author 045923-foss-shiwei
	 * @date 2012-12-14 上午10:22:01
	 */
	@Override
	public List<LoaderParticipationEntity> queryLoaderDetailListById(String unloadTaskId) {
		//返回查询结果
		return unloadTaskDao.queryLoaderDetailListById(unloadTaskId);
	}
	
	/**
	 * 根据卸车任务ID获取卸车任务的创建人
	 * @author 045923-foss-shiwei
	 * @date 2013-2-20 上午11:12:22
	 */
	@Override
	public List<LoaderParticipationEntity> queryTaskCreatorLoaderByTaskId(String unloadTaskId){
		//返回查询结果
		return unloadTaskDao.queryTaskCreatorLoaderByTaskId(unloadTaskId);
	}

	/**
	 * 批量传入单据编号和卸车任务ID，将单据从卸车任务中删除
	 * @author 045923-foss-shiwei
	 * @date 2012-12-14 下午3:05:03
	 */
	@Override
	public int deleteBillDetailListFromUnloadTask(DeleteFromUnloadTaskDto deleteDto,String unloadType) {
		List<String> noList = deleteDto.getNoList();
		//调用分配卸车任务服务，将删除的单据的分配状态改回为”未分配“
		List<UnloadBillDetailDto> dtoList = new ArrayList<UnloadBillDetailDto>();
		for(String no : noList){
			UnloadBillDetailDto dto = new UnloadBillDetailDto();
			dto.setBillNo(no);
			if(StringUtils.equals(unloadType, UnloadConstants.UNLOAD_TASK_TYPE_SHORT_DISTANCE)){
				dto.setBillType(UnloadConstants.BILL_TYPE_HANDOVER);
			}else if(StringUtils.equals(unloadType, UnloadConstants.UNLOAD_TASK_TYPE_LONG_DISTANCE)){
				dto.setBillType(UnloadConstants.BILL_TYPE_VEHICLEASSEMBLE);
			}else if(StringUtils.equals(unloadType, UnloadConstants.UNLOAD_TASK_TYPE_BUSINESS)){
				//272681 卸车类型是商务专递的单据类型
				dto.setBillType(UnloadConstants.BILL_TYPE_AIR_HANDOVERTYPE);
			}else{
				//此处可以为空
			}
			dtoList.add(dto);
		}
		if(StringUtils.equals(unloadType, UnloadConstants.UNLOAD_TASK_TYPE_BUSINESS)){
			//272681删除商务专递卸车任务时更新卸车状态和分配状态
			this.updateArriveBusinessAirBillState(dtoList,TaskTruckConstant.BILL_ASSIGN_STATE_UNASSIGN,UnloadConstants.UNLOAD_TASK_STATE_UNSTART);
		}else{
		//调用分配卸车任务服务
		this.updateArriveBillState(dtoList,TaskTruckConstant.BILL_ASSIGN_STATE_UNASSIGN);
		}
		if(null!=deleteDto.getNoList()&&deleteDto.getNoList().size()>0){
			//将单据从卸车任务中删除
			unloadTaskDao.deleteBillDetailListFromUnloadTask(deleteDto);
		}
		
		//返回处理成功
		return FossConstants.SUCCESS;
	}

	/**
	 * 批量传入卸车员code和卸车任务ID，将卸车员从卸车员参与情况表中删除
	 * @author 045923-foss-shiwei
	 * @date 2012-12-14 下午3:05:58
	 */
	@Override
	public int deleteLoaderListFromUnloadTask(DeleteFromUnloadTaskDto deleteDto) {
		//删除卸车员
		unloadTaskDao.deleteLoaderListFromUnloadTask(deleteDto);
		//返回处理成功
		return FossConstants.SUCCESS;
	}
	

	/**
	 * @description 根据卸车任务编号返回快递交接单据明细
	 * @param unloadWaybillDetailDto
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 * @update 2016年5月3日 上午9:12:43
	 */
	public List<UnloadBillDetailEntity> queryExpressUnloadWaybillDetail(ExpressQueryUnloadWaybillDetailDto unloadWaybillDetailDto,String unloadType) throws Exception{
		
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(FossUserContext.getCurrentDeptCode());
		//营业部
		if(StringUtils.equals(FossConstants.YES, org.getSalesDepartment())) {
			return new ArrayList<UnloadBillDetailEntity>();
	     }
		
		// 获取objectMapper
		ObjectMapper objectMapper = JSONUtils.obtainObjectMapper();
		//设置  部门编号
		unloadWaybillDetailDto.setOrgCode(FossUserContext.getCurrentDeptCode());
		
		String requestJsonStr=objectMapper.writeValueAsString(unloadWaybillDetailDto);
		LOGGER.info("根据卸车任务编号返回快递单据明细参数"+requestJsonStr);
		//调用同步数据接口
		FossToWKResponseEntity fossToWKResponseEntity=fossToWkService.queryUnloadWaybillDetailFromWk(requestJsonStr);
		if(fossToWKResponseEntity==null){
			LOGGER.error("Foss查询悟空快递单据明细失败!");
			throw new TfrBusinessException("Foss查询悟空快递单据明细失败!");
		}
		if("0".equals(fossToWKResponseEntity.getStatus())){
			LOGGER.error("Foss查询悟空快递单据明细失败，错误信息："+fossToWKResponseEntity.getExMsg());
			throw new TfrBusinessException("Foss查询悟空快递单据明细失败，错误信息："+fossToWKResponseEntity.getExMsg());
		}

		List<UnloadBillDetailEntity> unloadBillDetailEntityList=new ArrayList<UnloadBillDetailEntity>();
		   
		@SuppressWarnings("unchecked")
		List<Object> list=(List<Object>) fossToWKResponseEntity.getData();
	    UnloadBillDetailEntity expressUnloadBillDetailEntity=null;
	    JSONObject jsonObj=null;
		for(Object obj:list){
			expressUnloadBillDetailEntity=new UnloadBillDetailEntity();
			jsonObj=JSONObject.fromObject(obj);
			//设置  单据编号
			expressUnloadBillDetailEntity.setBillNo(jsonObj.getString("handoverBillNo"));
			//设置 零担快递标识
			expressUnloadBillDetailEntity.setExpressOrLingDan("express");
			if(StringUtils.isEmpty(jsonObj.getString("weight"))
					||"null".equals(jsonObj.getString("weight"))){
				expressUnloadBillDetailEntity.setWeight(new BigDecimal(0));
			}else{
				//设置  重量
				expressUnloadBillDetailEntity.setWeight(new BigDecimal(jsonObj.getString("weight")));
			}
			if(StringUtils.isEmpty(jsonObj.getString("goodsQty"))
					||"null".equals(jsonObj.getString("goodsQty"))){
				expressUnloadBillDetailEntity.setPieces(new BigDecimal(0));
			}else{
				//设置  件数
				expressUnloadBillDetailEntity.setPieces(new BigDecimal(jsonObj.getString("goodsQty")));
			}
			if(StringUtils.isEmpty(jsonObj.getString("volume"))
					||"null".equals(jsonObj.getString("volume"))){
				expressUnloadBillDetailEntity.setVolume(new BigDecimal(0));
			}else{
				//设置 体积
				expressUnloadBillDetailEntity.setVolume(new BigDecimal(jsonObj.getString("volume")));
			}
			if(StringUtils.isEmpty(jsonObj.getString("goodsQty"))
					||"null".equals(jsonObj.getString("goodsQty"))){
				expressUnloadBillDetailEntity.setWaybillTotal(new BigDecimal(0));
			}else{
				//设置 票数
				expressUnloadBillDetailEntity.setWaybillTotal(new BigDecimal(jsonObj.getString("goodsQty")));
			}
			
			//长途
			if(StringUtils.equals(unloadType,UnloadConstants.UNLOAD_TASK_TYPE_LONG_DISTANCE)){
			//设置 配载类型
			expressUnloadBillDetailEntity.setBillType(UnloadConstants.BILL_TYPE_VEHICLEASSEMBLE);
			}else{
			//设置 交接单类型
			expressUnloadBillDetailEntity.setBillType(UnloadConstants.BILL_TYPE_HANDOVER);
			}
			unloadBillDetailEntityList.add(expressUnloadBillDetailEntity);
		}
		
		  return unloadBillDetailEntityList;
	}

	/**
	 * 修改卸车任务
	 * @author 045923-foss-shiwei
	 * @date 2012-12-14 下午3:11:14
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskService#updateUnloadTask(com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadTaskModifyDto)
	 */
	@Override
	@Transactional
	public int updateUnloadTask(UnloadTaskModifyDto modifyDto) {
		//获取传入的卸车任务ID
		String unloadTaskId = modifyDto.getUnloadTaskId();
		//获取传入的月台号
		String platformNo = modifyDto.getPlatformNo();
		//获取传入的月台ID
		String platformId = modifyDto.getPlatformId();
		//获取传入的新增的单据list
		List<UnloadBillDetailEntity> addedBillList = modifyDto.getAddedBillList();
		//获取传入的删除的单据list
		List<UnloadBillDetailEntity> deletedBillList = modifyDto.getDeletedBillList();
		//获取传入的新增的卸车员
		List<LoaderParticipationEntity> addedLoaderList = modifyDto.getAddedLoaderList();
		//获取传入的删除的卸车员
		List<LoaderParticipationEntity> deletedLoaderList = modifyDto.getDeletedLoaderList();
		//根据ID，获取修改前的实体
		UnloadTaskEntity oldEntity = this.queryUnloadTaskBaseInfoById(unloadTaskId);
		
		//如果卸车任务不存在，则无法修改
		if(oldEntity == null){
			//抛业务异常，中断
			throw new TfrBusinessException("卸车任务不存在或已取消！");
		}
		
		//如果卸车任务已结束，则无法修改
		if(!StringUtils.equals(oldEntity.getTaskState(), UnloadConstants.UNLOAD_TASK_STATE_UNLOADING)){
			//抛业务异常，中断
			throw new TfrBusinessException("卸车任务已经结束或取消，无法修改！");
		}
		
		//构造实体，更新基本信息
		UnloadTaskEntity entity = new UnloadTaskEntity();
		//卸车任务ID
		entity.setId(unloadTaskId);
		//月台号
		entity.setPlatformNo(platformNo);
		//月台ID
		entity.setPlatformId(platformId);
		
		//如果月台号不一致，则放空原来的月台，占有新的月台
		if(StringUtils.isNotBlank(oldEntity.getPlatformNo())
				&& StringUtils.isNotBlank(entity.getPlatformNo())
				&& !StringUtils.equals(oldEntity.getPlatformNo(), entity.getPlatformNo())){
			
			//更新基本信息
			this.updateUnloadTaskBasicInfo(entity);
			//放空原先的月台
			this.platformDispatchService.updatePlatformStatusForEnd(entity.getUnloadTaskNo(), new Date());
			
			//占用新的月台
			//获取月台虚拟code
			PlatformEntity  platform = platformService.queryPlatformByCode(oldEntity.getUnloadOrgCode(), entity.getPlatformNo());
			String platformVirtualCode = null;
			if(platform == null){
				throw new TfrBusinessException("月台不是当前部门所有！");
			}else{
				platformVirtualCode = platform.getVirtualCode();
			}
			PlatformDistributeEntity distributeEntity = new PlatformDistributeEntity();
			//月台虚拟code
			distributeEntity.setPlatformNo(platformVirtualCode);
			//车牌号
			distributeEntity.setVehicleNo(oldEntity.getVehicleNo());
			//卸车任务编号
			distributeEntity.setLoadTaskNo(oldEntity.getUnloadTaskNo());
			//开始卸车时间
			distributeEntity.setUseStartTime(oldEntity.getUnloadStartTime());
			//结束卸车时间
			distributeEntity.setUseEndTime(oldEntity.getPlanCompleteTime());
			//卸车部门
			distributeEntity.setTransferCenterNo(oldEntity.getUnloadOrgCode());
			//占用类型：实际使用
			distributeEntity.setType(PlatformDispatchConstants.PLATFORMDISPATCH_TYPE_ACTUALUSE);
			//卸车占用
			distributeEntity.setScheduleSource(PlatformDispatchConstants.PLATFORMDISPATCH_SCHEDULESOURCE_UNLOAD);
			//调用月台服务，占用月台
			platformDispatchService.updatePlatformStatusForUsing(distributeEntity);
		}
		
		//定义零担单据list
		List<UnloadBillDetailEntity> newAddedBillList =new ArrayList<UnloadBillDetailEntity>();
		//插入传入的新增的单据list
		if(addedBillList != null && addedBillList.size() != 0){
			//此处选出待添加的零担的单据
			for(int i=0;i<addedBillList.size();i++){
				if(!UnloadCommonUtils.isExpressHandOver(addedBillList.get(i).getBillNo())){
					newAddedBillList.add(addedBillList.get(i));
				}
			}
			this.addUnloadTaskBillDetailList(outPutBillDetailEntityList(newAddedBillList, oldEntity),oldEntity.getUnloadType());
		}
		
		//定义零担单据list
		List<UnloadBillDetailEntity> newDeletedBillList =new ArrayList<UnloadBillDetailEntity>();
		//删除传入的删除的单据list
		if(deletedBillList != null && deletedBillList.size() != 0){
			
			//此处选出待删除的零担的单据
			for(int i=0;i<deletedBillList.size();i++){
				if(!UnloadCommonUtils.isExpressHandOver(deletedBillList.get(i).getBillNo())){
					newDeletedBillList.add(deletedBillList.get(i));
				}
			}
			DeleteFromUnloadTaskDto deleteBillDto = new DeleteFromUnloadTaskDto();
			//设置卸车任务ID
			deleteBillDto.setUnloadTaskId(unloadTaskId);
			//获取所有被删除的单据NO
			List<String> noList = new ArrayList<String>();
			for(UnloadBillDetailEntity billEntity : newDeletedBillList){
				noList.add(billEntity.getBillNo());
			}
			deleteBillDto.setNoList(noList);
			this.deleteBillDetailListFromUnloadTask(deleteBillDto,oldEntity.getUnloadType());
		}
		
		//插入传入的新增的卸车员list
		if(addedLoaderList != null && addedLoaderList.size() != 0){
			this.addLoaderParticipationList(this.outPutLoaderParticipationList(oldEntity, addedLoaderList,1));
		}
		//删除传入的删除的卸车员list
		if(deletedLoaderList != null && deletedLoaderList.size() != 0){
			DeleteFromUnloadTaskDto deleteBillDto = new DeleteFromUnloadTaskDto();
			deleteBillDto.setUnloadTaskId(unloadTaskId);
			//获取所有被删除的卸车员code
			List<String> noList = new ArrayList<String>();
			for(LoaderParticipationEntity loaderEntity : deletedLoaderList){
				noList.add(loaderEntity.getLoaderCode());
			}
			deleteBillDto.setNoList(noList);
			//删除卸车员
			this.deleteLoaderListFromUnloadTask(deleteBillDto);
		}
//		派送发短信回滚
//		addJobTodo(modifyDto.getUnloadTaskId(),FossUserContext.getCurrentInfo().getEmpCode(),FossUserContext.getCurrentDeptCode());
		
		boolean tfrSwitch4Ecs = configurationParamsService.queryTfrSwitch4Ecs();
		LOGGER.error("同步修改卸车任务到悟空系统开关" + tfrSwitch4Ecs);
		if(tfrSwitch4Ecs){
			
			OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(oldEntity.getUnloadOrgCode());
			//营业部
			if(StringUtils.equals(FossConstants.YES, org.getSalesDepartment())) {
				return FossConstants.SUCCESS;
			}
			try {
				updateExpressUnloadTask(modifyDto);
			} catch (Exception e) {
				LOGGER.error("同步修改卸车任务到悟空系统失败:"+e.getMessage());	
				throw new TfrBusinessException(e.getMessage());
			}
		}
		
		//返回处理成功
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 根据ID更新卸车任务状态
	 * @author 045923-foss-shiwei
	 * @date 2012-12-14 下午3:36:54
	 */
	@Override
	public int updateUnloadTaskState(String unloadTaskId, String targetState) {
		//更新卸车任务状态
		unloadTaskDao.updateUnloadTaskState(unloadTaskId, targetState);
		//返回处理成功
		return FossConstants.SUCCESS;
	}

	/**
	 * 根据ID更新卸车任务基本信息
	 * @author 045923-foss-shiwei
	 * @date 2012-12-14 下午3:37:32
	 */
	@Override
	public int updateUnloadTaskBasicInfo(UnloadTaskEntity entity) {
		//更新卸车任务基本信息
		unloadTaskDao.updateUnloadTaskBasicInfo(entity);
		//返回处理成功
		return FossConstants.SUCCESS;
	}
	
	/**
	 * @description FOSS同步修改卸车任务到悟空系统
	 * (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskService#updateExpressUnloadTask(com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadTaskModifyDto)
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 上午11:25:01
	 * @version V1.0
	 */
	@Override
	public void updateExpressUnloadTask(UnloadTaskModifyDto modifyDto)
			throws Exception {

		ExpressUpdateUnloadTaskDto expressUpdateUnloadTaskDto=new ExpressUpdateUnloadTaskDto();
		
		//设置 卸车任务编号
		expressUpdateUnloadTaskDto.setUnloadTaskNo(modifyDto.getUnloadTaskNo());
		//设置 月台号
		expressUpdateUnloadTaskDto.setPlatformNo(modifyDto.getPlatformNo());
		//设置 修改时间
		expressUpdateUnloadTaskDto.setUpdateTime(new Date());
		//设置 更新人编号
		expressUpdateUnloadTaskDto.setUpdateNo(FossUserContext.getCurrentInfo().getEmpCode());
		//设置 当前部门编号
		expressUpdateUnloadTaskDto.setUpdateOrgCode(FossUserContext.getCurrentDeptCode());
		
		
		//需添加的卸车任务明细集合
		List<ExpressUnloadTaskDetailDto> addTaskDetailList=new ArrayList<ExpressUnloadTaskDetailDto>();
		//卸车运单明细实体
		UnloadBillDetailEntity addUnloadBillDetailEntity=null;
		//快递-卸车任务明细Dto
		ExpressUnloadTaskDetailDto addUnloadTaskDetailDto=null;
		for(Iterator<UnloadBillDetailEntity> its=modifyDto.getAddedBillList().iterator();its.hasNext();){
			
			addUnloadBillDetailEntity=its.next();
			
			if(UnloadCommonUtils.isExpressHandOver(addUnloadBillDetailEntity.getBillNo())){
				
				addUnloadTaskDetailDto=new ExpressUnloadTaskDetailDto();
				
				//设置 卸车任务编号
				addUnloadTaskDetailDto.setUnloadTaskNo(modifyDto.getUnloadTaskNo());
				//设置 交接单编号
				addUnloadTaskDetailDto.setHandoverBillNo(addUnloadBillDetailEntity.getBillNo());
				//设置 件数
				addUnloadTaskDetailDto.setGoodsQty(addUnloadBillDetailEntity.getPieces().longValue());
				//设置 重量
				addUnloadTaskDetailDto.setWeight(addUnloadBillDetailEntity.getWeight());
				//设置 体积
				addUnloadTaskDetailDto.setVolume(addUnloadBillDetailEntity.getVolume());
				
				//设置 到达部门编号
				addTaskDetailList.add(addUnloadTaskDetailDto);
			}
			
		}
		
		//设置 新增卸车任务交接单信息
		expressUpdateUnloadTaskDto.setAddNewUnloadDetail(addTaskDetailList);
		
		
		//需删除的卸车任务明细集合
		List<ExpressUnloadTaskDetailDto> delTaskDetailList=new ArrayList<ExpressUnloadTaskDetailDto>();
		//卸车运单明细实体
		UnloadBillDetailEntity delUnloadBillDetailEntity=null;
		//快递-卸车任务明细Dto
		ExpressUnloadTaskDetailDto delUnloadTaskDetailDto=null;
		for(Iterator<UnloadBillDetailEntity> its=modifyDto.getDeletedBillList().iterator();its.hasNext();){
			
			delUnloadBillDetailEntity=its.next();
			if(UnloadCommonUtils.isExpressHandOver(delUnloadBillDetailEntity.getBillNo())){
			
			delUnloadTaskDetailDto=new ExpressUnloadTaskDetailDto();
			//设置 卸车任务编号
			delUnloadTaskDetailDto.setUnloadTaskNo(modifyDto.getUnloadTaskNo());
			//设置 交接单编号
			delUnloadTaskDetailDto.setHandoverBillNo(delUnloadBillDetailEntity.getBillNo());
			//设置 件数
			delUnloadTaskDetailDto.setGoodsQty(delUnloadBillDetailEntity.getPieces().longValue());
			//设置 重量
			delUnloadTaskDetailDto.setWeight(delUnloadBillDetailEntity.getWeight());
			//设置 体积
			delUnloadTaskDetailDto.setVolume(delUnloadBillDetailEntity.getVolume());
			
			delTaskDetailList.add(delUnloadTaskDetailDto);
		  }
		}
		
		//设置 删除卸车任务交接单信息
		expressUpdateUnloadTaskDto.setDeleteOldUnloadDetail(delTaskDetailList);
				
		
		//需新增的理货员集合
		List<ExpressLoaderParticipateDto> addLoaderParticipateList=new ArrayList<ExpressLoaderParticipateDto>();
		//装卸车人员参与情况实体(添加)
		LoaderParticipationEntity addLoaderParticipationEntity=null;
		//快递-理货员明细(添加)
		ExpressLoaderParticipateDto addLoaderParticipate=null;
		
		//遍历新增理货员列表
		for(Iterator<LoaderParticipationEntity> its=modifyDto.getAddedLoaderList().iterator();its.hasNext();){
			//创建快递-理货员明细实体
			addLoaderParticipate=new ExpressLoaderParticipateDto();
			addLoaderParticipationEntity=its.next();
			//设置 卸车任务编号
			addLoaderParticipate.setTaskNo(modifyDto.getUnloadTaskNo());
			//设置 理货员姓名
			addLoaderParticipate.setLoaderName(addLoaderParticipationEntity.getLoaderName());
			//设置 理货员工号
			addLoaderParticipate.setLoaderCode(addLoaderParticipationEntity.getLoaderCode());
		
			//设置 加入时间  
			addLoaderParticipate.setJoinTime(new Date());
			
			//设置 是否为建立任务理货员
			if(addLoaderParticipationEntity.getLoaderCode().equals(FossUserContext.getCurrentInfo().getEmpCode())){
				addLoaderParticipate.setIscreator("Y");
			}else{
				addLoaderParticipate.setIscreator("N");
			}
			
			addLoaderParticipateList.add(addLoaderParticipate);
		}
		
		//设置 新增理货员信息
		expressUpdateUnloadTaskDto.setAddNewLoader(addLoaderParticipateList);
		
		
		//需删除的已有理货员集合
		List<ExpressLoaderParticipateDto> deleteOldLoaderParticipateList=new ArrayList<ExpressLoaderParticipateDto>();
		//装卸车人员参与情况实体
		LoaderParticipationEntity deleteOldLoaderParticipationEntity=null;
		//快递-理货员明细实体
		ExpressLoaderParticipateDto deleteOldLoaderLoaderParticipate=null;
		
		//遍历待删除理货员列表
		for(Iterator<LoaderParticipationEntity> its=modifyDto.getDeletedLoaderList().iterator();its.hasNext();){
			//创建快递-理货员明细实体
			deleteOldLoaderLoaderParticipate=new ExpressLoaderParticipateDto();
			deleteOldLoaderParticipationEntity=its.next();
			//设置 卸车任务编号
			deleteOldLoaderLoaderParticipate.setTaskNo(modifyDto.getUnloadTaskNo());
			//设置 理货员姓名
			deleteOldLoaderLoaderParticipate.setLoaderName(deleteOldLoaderParticipationEntity.getLoaderName());
			//设置 理货员工号
			deleteOldLoaderLoaderParticipate.setLoaderCode(deleteOldLoaderParticipationEntity.getLoaderCode());
			
			//设置 加入时间
			deleteOldLoaderLoaderParticipate.setJoinTime(new Date());
			
			//设置 是否为建立任务理货员
			if(deleteOldLoaderParticipationEntity.getLoaderCode().equals(FossUserContext.getCurrentInfo().getEmpCode())){
				deleteOldLoaderLoaderParticipate.setIscreator("Y");
			}else{
				deleteOldLoaderLoaderParticipate.setIscreator("N");
			}
			
			deleteOldLoaderParticipateList.add(deleteOldLoaderLoaderParticipate);
		}
		
		//设置 删除理货员信息
		expressUpdateUnloadTaskDto.setDeleteOldLoader(deleteOldLoaderParticipateList);
		
		
		// 获取objectMapper
		ObjectMapper objectMapper = JSONUtils.obtainObjectMapper();
		// 设置时间转换格式
		SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT);
		// 设置到objectMapper
		objectMapper.setDateFormat(dateFormat);
		
		String requestJsonStr=objectMapper.writeValueAsString(expressUpdateUnloadTaskDto);
		LOGGER.info("修改卸车任务到悟空系统参数"+requestJsonStr);
		
		//调用同步数据接口
		FossToWKResponseEntity fossToWKResponseEntity=fossToWkService.syncupdateExpressUnloadTask(requestJsonStr);
		if(fossToWKResponseEntity==null){
			LOGGER.error("Foss同步修改卸车任务到悟空系统失败!");
			throw new TfrBusinessException("Foss同步修改卸车任务到悟空系统失败!");
		}
		if("1".equals(fossToWKResponseEntity.getStatus())){
			LOGGER.info("Foss同步修改卸车任务到悟空系统成功!");
		}else{
			LOGGER.error("Foss同步修改卸车任务到悟空系统失败，错误信息："+fossToWKResponseEntity.getExMsg());
			throw new TfrBusinessException("Foss同步修改卸车任务到悟空系统，错误信息："+fossToWKResponseEntity.getExMsg());
		}

	}

	/**
	 * 通过卸车任务ID，取消（物理删除）卸车任务，删除三表数据，t_opt_unload_task，t_opt_unload_bill_detail，t_opt_loader_participation
	 * 同时更新任务内所有单据的卸车分配状态为未分配
	 * @author 045923-foss-shiwei
	 * @date 2012-12-14 下午7:41:03
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskService#cancelUnloadTask(java.lang.String,java.lang.String)
	 */
	@Transactional
	@Override
	public int cancelUnloadTask(String unloadTaskId,String unloadTaskNo) {
		//获取基本信息
		UnloadTaskEntity baseEntity = this.queryUnloadTaskBaseInfoById(unloadTaskId);
		//卸车任务是否存在
		if(baseEntity == null){
			//抛业务异常，中断
			throw new TfrBusinessException("该卸车任务不存在或者已取消！");
		}
		//如果任务已结束，则无法取消
		if(StringUtils.equals(baseEntity.getTaskState(),UnloadConstants.ASSIGN_UNLOAD_TASK_STATE_FINISHED)){
			//抛业务异常，中断
			throw new TfrBusinessException("该卸车任务已经结束，无法取消！");
		}
		//获取任务下所有单据
		List<UnloadBillDetailEntity> billList = this.queryUnloadTaskBillDetailListById(unloadTaskId);
		//获取任务下所有卸车员信息
		List<LoaderParticipationEntity> loaderList = this.queryLoaderDetailListById(unloadTaskId);
		
		//构造deleteDto，删除单据和卸车员
		DeleteFromUnloadTaskDto billDto = new DeleteFromUnloadTaskDto();
		List<String> billNoList = new ArrayList<String>();
		DeleteFromUnloadTaskDto loaderDto = new DeleteFromUnloadTaskDto();
		List<String> loaderCodeList = new ArrayList<String>();
		//待删除的单据list
		for(UnloadBillDetailEntity bill : billList){
			billNoList.add(bill.getBillNo());
		}
		billDto.setNoList(billNoList);
		billDto.setUnloadTaskId(unloadTaskId);
		//待删除的卸车员list
		for(LoaderParticipationEntity loader : loaderList){
			loaderCodeList.add(loader.getLoaderCode());
		}
		loaderDto.setNoList(loaderCodeList);
		loaderDto.setUnloadTaskId(unloadTaskId);
		
		
		//若任务占用有月台，则需放空月台
		if(!StringUtils.isBlank(baseEntity.getPlatformNo())){
			//放空月台
			this.platformDispatchService.updatePlatformStatusForEnd(baseEntity.getUnloadTaskNo(), new Date());
		}
		//删除任务基本信息
		unloadTaskDao.deleteUnloadTaskBasicInfo(unloadTaskId);
		//删除单据，同时更新单据分配状态为未分配
		this.deleteBillDetailListFromUnloadTask(billDto,baseEntity.getUnloadType());
		//删除卸车员参与情况
		this.deleteLoaderListFromUnloadTask(loaderDto);
//		/**
//		 * 创建卸车任务发短信 限制的业务时间段08:00-17:30
//		 */
//		Date businessTime=judgeTimeRange(TransferConstants.BUSINESSTIME,new Date());
//		派送发短信回滚代码
//		deleteTfrJob(baseEntity.getId());
		
		boolean tfrSwitch4Ecs = configurationParamsService.queryTfrSwitch4Ecs();
		LOGGER.error("同步删除卸车任务到悟空系统开关" + tfrSwitch4Ecs);
		if (tfrSwitch4Ecs) {
			OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
					.queryOrgAdministrativeInfoByCode(baseEntity.getUnloadOrgCode());
			// 营业部
			if (StringUtils.equals(FossConstants.YES, org.getSalesDepartment())) {
				return FossConstants.SUCCESS;
			}

			ExpressCancelUnloadTaskDto expressCancelUnloadTaskDto = new ExpressCancelUnloadTaskDto();
			expressCancelUnloadTaskDto.setUnloadTaskNo(unloadTaskNo);
			expressCancelUnloadTaskDto.setUpdateTime(new Date());
			try {
				expressCancelUnloadTask(expressCancelUnloadTaskDto);
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), "");
				throw new TfrBusinessException(e.getMessage());
			}
		}
		//返回处理成功
		return FossConstants.SUCCESS;
	}

	/**
	 * @description Foss同步取消卸车任务到悟空系统
	 * @param expressCancelUnloadTaskDto
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午12:30:11
	 */
	public void expressCancelUnloadTask(ExpressCancelUnloadTaskDto expressCancelUnloadTaskDto) throws Exception{
		
		//设置  当前操作人编号
		expressCancelUnloadTaskDto.setUpdateNo(FossUserContext.getCurrentInfo().getEmpCode());
		//设置 当前部门编号
		expressCancelUnloadTaskDto.setUpdateOrgCode(FossUserContext.getCurrentDeptCode());
		
		// 获取objectMapper
		ObjectMapper objectMapper = JSONUtils.obtainObjectMapper();
		// 设置时间转换格式
		SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT);
		// 设置到objectMapper
		objectMapper.setDateFormat(dateFormat);
			
		
		String requestJsonStr=objectMapper.writeValueAsString(expressCancelUnloadTaskDto);
		LOGGER.info("Foss同步取消卸车任务到悟空系统参数"+requestJsonStr);
		
		//调用同步数据接口
		FossToWKResponseEntity fossToWKResponseEntity=fossToWkService.syncCancelUnloadTaskToWk(requestJsonStr);
		if(fossToWKResponseEntity==null){
			LOGGER.error("Foss同步取消卸车任务到悟空系统失败!");
			throw new TfrBusinessException("Foss同步取消卸车任务到悟空系统失败!");
		}
		if("1".equals(fossToWKResponseEntity.getStatus())){
			LOGGER.info("Foss同步取消卸车任务到悟空系统成功!");
		}else{
			LOGGER.error("Foss同步取消卸车任务到悟空系统失败，错误信息："+fossToWKResponseEntity.getExMsg());
			throw new TfrBusinessException("Foss同步取消卸车任务到悟空系统，错误信息："+fossToWKResponseEntity.getExMsg());
		}
	}
	
	/**
	 * 手工确认卸车任务界面，根据交接单号，获取运单号
	 * @author 045923-foss-shiwei
	 * @date 2012-12-15 下午4:56:55
	 */
	@Override
	public List<HandOverBillDetailEntity> queryWaybillListByHandOverBillNo(String handOverBillNo) {
		//返回查询结果
		return handOverBillService.queryHandOverBillDetailByNo(handOverBillNo);
	}
	
	/**
	 * @description 根据交接单编号返回快递笼号，包号，运单号
	 * @param paramMap
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 * @update 2016年5月3日 上午10:29:15
	 */
	@SuppressWarnings("unchecked")
	public List<HandOverBillDetailEntity> expressQueryWaybillListByHandOverBillNo(Map<String,String> paramMap) throws Exception{
		
		// 获取objectMapper
		ObjectMapper objectMapper = JSONUtils.obtainObjectMapper();
		//设置  当前部门编号
		paramMap.put("orgCode", FossUserContext.getCurrentDeptCode());
		
		String unloadType=paramMap.get("unloadType");
		if(null!=unloadType){
		 paramMap.remove("unloadType");	
		}
		
		String requestJsonStr=objectMapper.writeValueAsString(paramMap);
		
		LOGGER.info("根据交接单编号返回快递笼号，包号，运单号参数"+requestJsonStr);
		//调用同步数据接口
		FossToWKResponseEntity fossToWKResponseEntity=fossToWkService.expressQueryWaybillListByHandOverBillNo(requestJsonStr);
		
		if(fossToWKResponseEntity==null){
			LOGGER.error("Foss根据交接单编号查询悟空快递笼号，包号，运单号失败!");
			throw new TfrBusinessException("Foss根据交接单编号查询悟空快递笼号，包号，运单号失败!");
		}
		if("0".equals(fossToWKResponseEntity.getStatus())){
			LOGGER.error("Foss根据交接单编号查询悟空快递笼号，包号，运单号失败，错误信息："+fossToWKResponseEntity.getExMsg());
			throw new TfrBusinessException("Foss根据交接单编号查询悟空快递笼号，包号，运单号失败，错误信息："+fossToWKResponseEntity.getExMsg());
		}

		List<HandOverBillDetailEntity> handOverBillDetailEntity=new ArrayList<HandOverBillDetailEntity>();
		    
		List<Object> list=(List<Object>) fossToWKResponseEntity.getData();
		    
		HandOverBillDetailEntity expressHandOverBillDetailEntity=null;
	    JSONObject jsonObj=null;
		for(Object obj:list){
			expressHandOverBillDetailEntity=new HandOverBillDetailEntity();
			jsonObj=JSONObject.fromObject(obj);
			if(StringUtils.equals(unloadType,UnloadConstants.UNLOAD_TASK_TYPE_LONG_DISTANCE)){
				//设置  交接单编号
				expressHandOverBillDetailEntity.setHandOverBillNo(jsonObj.getString("cargoNo"));
				//设置  配载单编号
				expressHandOverBillDetailEntity.setVehicleAssembleNo(jsonObj.getString("handoverBillNo"));
	            //设置   货物类型
				expressHandOverBillDetailEntity.setCargoType(jsonObj.getString("cargoType"));
				//设置 件号
				expressHandOverBillDetailEntity.setCargoNo(jsonObj.getString("cargoNo"));
				
			}else{
				//设置 快递是笼号/包号/运单编号
				expressHandOverBillDetailEntity.setWaybillNo(jsonObj.getString("cargoNo"));
				//设置  交接单编号
				expressHandOverBillDetailEntity.setHandOverBillNo(jsonObj.getString("handoverBillNo"));
	            //设置   货物类型
				expressHandOverBillDetailEntity.setCargoType(jsonObj.getString("cargoType"));
				//设置 件号
				expressHandOverBillDetailEntity.setCargoNo(jsonObj.getString("cargoNo"));
			}
			handOverBillDetailEntity.add(expressHandOverBillDetailEntity);
		}
		
		  return handOverBillDetailEntity;
	}

	/**
	 * 手工确认卸车任务界面，根据交接单号、运单号获取流水号列表
	 * @author 045923-foss-shiwei
	 * @date 2012-12-17 上午10:43:21
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskService#querySerialNoListByHandOverBillNoAndWaybillNo(java.lang.String, java.lang.String)
	 */
	@Override
	public List<HandOverBillSerialNoDetailEntity> querySerialNoListByHandOverBillNoAndWaybillNo(
			String handOverBillNo, String waybillNo) {
		return handOverBillService.getHandOverBillSerialNoDetailsByWayBillNo(waybillNo, handOverBillNo);
	}

	/**
	 * 用于确认短途卸车界面，快速查询时根据运单号获取交接单号list
	 * @author 045923-foss-shiwei
	 * @date 2012-12-19 上午10:42:52
	 */
	@Override
	public List<String> queryHandOverBillListByWaybillNo(String unloadTaskId,
			String waybillNo) {
		return unloadTaskDao.queryHandOverBillListByWaybillNo(unloadTaskId, waybillNo);
	}

	/**
	 * 确认卸车任务时，保存数据方法
	 * @author 045923-foss-shiwei
	 * @date 2012-12-19 下午7:05:59
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskService#confirmUnloadTask(com.deppon.foss.module.transfer.unload.api.shared.dto.ConfirmUnloadTaskDto)
	 */
	@Override
	@Transactional
	public int confirmUnloadTask(ConfirmUnloadTaskDto confirmUnloadTaskDto) {
		/**
		 * 确认卸车任务时，做以下操作：
		 * 1、更新卸车任务信息，包括卸车结束时间、任务状态、是否卸车异常；
		 * 2、插入卸车运单明细，需要结合交接件数、多货、少货情况得出操作件数；
		 * 3、插入卸车流水号明细；
		 * 4、更新交接单状态为“已入库”；
		 * 5、将所有操作的流水号在本部门入库。
		 */
		//获取卸车任务id
		String unloadTaskId = confirmUnloadTaskDto.getUnloadTaskId();
		//构造卸车任务实体
		UnloadTaskEntity baseEntity = unloadTaskDao.queryUnloadTaskBaseInfoById(unloadTaskId);
		//只能确认“进行中”的卸车任务
		if(!StringUtils.equals(baseEntity.getTaskState(), UnloadConstants.UNLOAD_TASK_STATE_UNLOADING)){
			throw new TfrBusinessException("该卸车任务已经结束或取消，无法执行卸车结束操作！");
		}
		//校验是否解封签
		//loadService.validateVehicleNoCanBeUsed(baseEntity.getVehicleNo());
		List<UnloadBillDetailDto> unloadBills = pdaUnloadTaskDao.queryUnloadBillsByTaskId(unloadTaskId);
		List<String> billNos= new ArrayList<String>();
		for (UnloadBillDetailDto unloadBill:unloadBills){
			billNos.add(unloadBill.getBillNo());
		}
		List<SealEntity>  unCheckedSeal = pdaUnloadTaskDao.querySealByBillNo(billNos, 
				baseEntity.getVehicleNo());
		if(CollectionUtils.isNotEmpty(unCheckedSeal)){
			if(unCheckedSeal.get(0).getSealState().equals(SealConstant.SEAL_STATE_UNCHECK)){
				//如果该车辆存在尚未检查封签，则不允许建立卸车任务
				throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_SEAL_UNCHEK_MESSAGECODE);
			}
		}
		//卸车结束时间
		baseEntity.setUnloadEndTime(new Date());
		
		//获取前台传入的少货的配载单List
		List<ConfirmUnloadTaskBillsDto> lackVehicleAssembleBillList = confirmUnloadTaskDto.getLackVehicleAssembleBillList();
		
		//只有配载单的list
		List<ConfirmUnloadTaskBillsDto> newLackVehicleAssembleBillList=null;
		//如果为长途卸车
		if(StringUtils.equals(baseEntity.getUnloadType(), UnloadConstants.UNLOAD_TASK_TYPE_LONG_DISTANCE)){
		//只有配载单的list
		newLackVehicleAssembleBillList=new ArrayList<ConfirmUnloadTaskBillsDto>(); 
		 if(null!=lackVehicleAssembleBillList){
			//长途卸车时去除少货配载单中的快递交接单编号
				for(int i=0;i<lackVehicleAssembleBillList.size();i++){
					if(!UnloadCommonUtils.isExpressHandOver(lackVehicleAssembleBillList.get(i).getVehicleAssembleNo())){
						newLackVehicleAssembleBillList.add(lackVehicleAssembleBillList.get(i));
					}
				} 
		   }
		}else{
			newLackVehicleAssembleBillList=lackVehicleAssembleBillList;
			if(null==newLackVehicleAssembleBillList){
				newLackVehicleAssembleBillList=new ArrayList<ConfirmUnloadTaskBillsDto>(0);
			}
		}
		
		//获取前台传入的少货的交接单list 
		List<ConfirmUnloadTaskBillsDto> lackHandOverBillList = confirmUnloadTaskDto.getLackHandOverBillList();
		//定义只有零担交接单的list
		List<ConfirmUnloadTaskBillsDto> newLackHandOverBillList=new ArrayList<ConfirmUnloadTaskBillsDto>();
		//如果为长途卸车
		if(StringUtils.equals(baseEntity.getUnloadType(), UnloadConstants.UNLOAD_TASK_TYPE_LONG_DISTANCE)){
		
		//长途卸车时获取少货交接单中的零担货
		for(int i=0;i<lackHandOverBillList.size();i++){
			if(StringUtils.isEmpty(lackHandOverBillList.get(i).getCargoType())){
				newLackHandOverBillList.add(lackHandOverBillList.get(i));
			}
		}
	   }else{
		 //短途卸车时获取少货交接单中的零担货
			for(int i=0;i<lackHandOverBillList.size();i++){
				
				if(!UnloadCommonUtils.isExpressHandOver(lackHandOverBillList.get(i).getBillNo())){
					newLackHandOverBillList.add(lackHandOverBillList.get(i));
				}
				
			}
	   }
		
		//获取前台传入的少货的运单list
		List<ConfirmUnloadTaskBillsDto> lackWaybillList = confirmUnloadTaskDto.getLackWaybillList();
		
		//少货的零担运单list
		List<ConfirmUnloadTaskBillsDto> newLackWaybillList =null;
		//长途卸车
	    if(StringUtils.equals(baseEntity.getUnloadType(), UnloadConstants.UNLOAD_TASK_TYPE_LONG_DISTANCE)){
	    	newLackWaybillList=lackWaybillList;
	    	if(null==newLackWaybillList){
	    		newLackWaybillList=new ArrayList<ConfirmUnloadTaskBillsDto>(0);
	    	}
	    }else{
	    	 //获取前台传入的少货的零担运单list
			  newLackWaybillList = new ArrayList<ConfirmUnloadTaskBillsDto>();
			  for(ConfirmUnloadTaskBillsDto confirmUnloadTaskBillsDto:lackWaybillList){
				  if(StringUtils.isEmpty(confirmUnloadTaskBillsDto.getCargoType())){
					  newLackWaybillList.add(confirmUnloadTaskBillsDto);
				  }
			  }
	    }
		
		
		//获取前台传入的少货的流水号list
		List<ConfirmUnloadTaskBillsDto> lackSerialNoList = confirmUnloadTaskDto.getLackSerialNoList();
		//获取前台传入的多货的流水号明细
		List<ConfirmUnloadTaskBillsDto> moreSerialNoList = confirmUnloadTaskDto.getMoreSerialNoList();
		
		
		
		//卸车任务是否异常
		if((lackVehicleAssembleBillList == null || lackVehicleAssembleBillList.size() == 0)
				&& (lackHandOverBillList == null || lackHandOverBillList.size() == 0)
				&& (lackWaybillList == null || lackWaybillList.size() ==0)
				&& (lackSerialNoList == null || lackSerialNoList.size() == 0)
				&& (moreSerialNoList == null || moreSerialNoList.size() == 0)){
			baseEntity.setBeException(FossConstants.NO);
		}else{
			baseEntity.setBeException(FossConstants.YES);
		}
		
		List<ConfirmUnloadTaskBillsDto> newMoreSerialNoList = new ArrayList<ConfirmUnloadTaskBillsDto>();
		if(CollectionUtils.isNotEmpty(moreSerialNoList)){
			for(ConfirmUnloadTaskBillsDto confirmUnloadTaskBillsDto:moreSerialNoList){
				if(StringUtils.isEmpty(confirmUnloadTaskBillsDto.getCargoNo())
						&&StringUtils.isEmpty(confirmUnloadTaskBillsDto.getCargoType())){
					newMoreSerialNoList.add(confirmUnloadTaskBillsDto);
				}
			}
		}
		
		//定义待插入的卸车任务运单明细
		List<UnloadWaybillDetailEntity> waybillDetailList = new ArrayList<UnloadWaybillDetailEntity>();
		//定义待插入的卸车任务流水号明细
		List<UnloadSerialNoDetailEntity> serialNoDetailList = new ArrayList<UnloadSerialNoDetailEntity>();
		
		//获取卸车任务下所有运单
		List<UnloadWaybillDetailEntity> oldWaybillList = queryWaybillListByUnloadTaskId(baseEntity);
		//获取提交的所有少货运单
		Map<String,Map<String,ConfirmUnloadTaskBillsDto>> lackWaybillMap = this.outPutWaybillListForLackGoods(newLackVehicleAssembleBillList, newLackHandOverBillList, newLackWaybillList, lackSerialNoList, baseEntity);
		//获取提交的所有多货运单
		Map<String,Map<String,ConfirmUnloadTaskBillsDto>> moreWaybillMap = this.outPutWaybillDetailListForMoreGoods(newMoreSerialNoList);
		
		//更新卸车任务信息
		this.updateUnloadTaskBasicInfo(baseEntity);
		//如果月台号不为空，则放空月台
		if(StringUtils.isNotBlank(baseEntity.getPlatformNo())){
			this.platformDispatchService.updatePlatformStatusForEnd(baseEntity.getUnloadTaskNo(), baseEntity.getUnloadEndTime());
		}
		if(baseEntity.getUnloadType().equals(UnloadConstants.UNLOAD_TASK_TYPE_BUSINESS)){
		//272681更新商务专递卸车的卸车状态
		this.updateArriveBusinessAirBillState(unloadBills, TaskTruckConstant.BILL_ASSIGN_STATE_ASSIGNED, UnloadConstants.UNLOAD_TASK_STATE_FINISHED);
		}
		//更新卸车任务状态
		this.updateUnloadTaskState(unloadTaskId, UnloadConstants.ASSIGN_UNLOAD_TASK_STATE_FINISHED);
		//更新卸车员参与情况的离开任务时间
		this.updateLoaderLeaveTaskTime(unloadTaskId, baseEntity.getUnloadEndTime());
		
		//增加任务车辆Action
		TruckActionDetailEntity actionDetail = new TruckActionDetailEntity();
		actionDetail.setId(DepartureConstant.SEQ_TRUCK_ACTION_JOB);
		actionDetail.setBundType(DepartureConstant.JOB_TRUCK_UNLOAD_MANUAL);
		actionDetail.setCreateTime(new Date());
		actionDetail.setCreateDate(new Date());
		actionDetail.setStatus(DepartureConstant.JOB_NOT_START);
		actionDetail.setTruckTaskDetailId(baseEntity.getId());
		actionDetail.setOperatorCode(DepartureHandle.getCurrentUserCode());
		actionDetail.setOperatorName(DepartureHandle.getCurrentUserName());
		departureDao.addTruckActionDetail(actionDetail);
		/***卸车少货处理代办**/
		if(null !=lackWaybillMap && lackWaybillMap.size()>0){
			for (Map<String,ConfirmUnloadTaskBillsDto> serialnoMap:lackWaybillMap.values()){
				if(null !=serialnoMap && serialnoMap.size()>0){
					for(ConfirmUnloadTaskBillsDto serialnoDto:serialnoMap.values()){
						try {
							waybillRfcService.resetTodoWhenLost(serialnoDto.getWaybillNo(),
									serialnoDto.getSerialNo(),baseEntity.getUnloadOrgCode());
						} catch (Exception e) {
							LOGGER.error("代办重置异常"+serialnoDto.getWaybillNo());
						}
					}
				}
			}
		}
		/**保存卸车任务运单明细和流水号明细**/
		//如果是长途卸车
		if(StringUtils.equals(baseEntity.getUnloadType(), UnloadConstants.UNLOAD_TASK_TYPE_LONG_DISTANCE)){
			saveDataWhenConfirmLongDistanceUnloadTask(waybillDetailList,serialNoDetailList,oldWaybillList,lackWaybillMap,moreWaybillMap,baseEntity);
		}
		//如果是短途卸车
		if(StringUtils.equals(baseEntity.getUnloadType(), UnloadConstants.UNLOAD_TASK_TYPE_SHORT_DISTANCE)){
			saveDataWhenConfirmShortDistanceUnloadTask(waybillDetailList,serialNoDetailList,oldWaybillList,lackWaybillMap,moreWaybillMap,baseEntity);
		}
		//2015/8/25 272681如果是商务专递卸车
		if(StringUtils.equals(baseEntity.getUnloadType(), UnloadConstants.UNLOAD_TASK_TYPE_BUSINESS)
				||StringUtils.equals(baseEntity.getUnloadType(),UnloadConstants.UNLOAD_TASK_TYPE_PACKAGE)){
			saveDataWhenConfirmBusinessAirUnloadTask(waybillDetailList,serialNoDetailList,oldWaybillList,lackWaybillMap,moreWaybillMap,baseEntity);
		}
		if (LOGGER.isInfoEnabled()){
			LOGGER.info("UnloadTaskService调用计价dubbo接口成功");
		}
		//判断是否晚到
		//this.isArriveLate(waybillDetailList,baseEntity);
		//2016年12月29日15:48:38 zm  添加时间点限制   如果超出配置时间则不判断晚到
		try {
			ConfigurationParamsEntity configurationParams = configurationParamsService.queryConfigurationParamsByOrgCode(
					DictionaryConstants.SYSTEM_CONFIG_PARM__TFR , DictionaryConstants.IS_ARRIVE_LATE_PARAMS, 
					FossConstants.ROOT_ORG_CODE);
			Date bizDate  =null;
			if(configurationParams != null && StringUtils.isNotEmpty(configurationParams.getConfValue())){
				 LOGGER.error("判断是否晚到配置参数:"+	configurationParams.getConfValue());
				 bizDate  = DateUtils.strToDate(configurationParams.getConfValue());
			}
			//当前时间小于设定时间点则不调用晚到逻辑
			if(null!=bizDate && baseEntity.getUnloadEndTime().getTime()<bizDate.getTime()){
				//判断是否晚到
				this.isArriveLate(waybillDetailList,baseEntity);
			}
		} catch (Exception e) {
			LOGGER.error("判断是否晚到出错:"+e.getMessage());
		}
		
		
		// 判断是否合伙人部门 标记
		boolean isPTPfalage = false;
		// 查询卸车部门
		SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService
				.querySaleDepartmentInfoByCode(baseEntity.getUnloadOrgCode());
		if (saleDepartmentEntity != null) {
			isPTPfalage = StringUtils.equals(FossConstants.YES,
					saleDepartmentEntity.getIsLeagueSaleDept());
		}
		// 到达合伙人卸车 插入代办表 定时任务 推送PTP触发扣款
		if (isPTPfalage) {
			// 卸车任务id存入job 代办表
			tfrJobTodoService.addJobTodo(unloadTaskId,
					BusinessSceneConstants.BUSINESS_SCENE_TRUCK_UNLOAD,
					new String[] { BusinessGoalContants.BUSINESS_GOAL_TO_PTP },
					new Date(), DepartureHandle.getCurrentUserCode());
		}
		boolean tfrSwitch4Ecs = configurationParamsService.queryTfrSwitch4Ecs();
		LOGGER.error("同步确认卸车任务到悟空系统开关" + tfrSwitch4Ecs);
		if (tfrSwitch4Ecs) {
			OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
					.queryOrgAdministrativeInfoByCode(baseEntity.getUnloadOrgCode());
			// 非营业部卸车同步至悟空
			if (!StringUtils.equals(FossConstants.YES, org.getSalesDepartment())) {
				try {
					confirmExpressUnloadTask(confirmUnloadTaskDto);
				} catch (Exception e) {
					LOGGER.error("FOSS同步确认卸车任务到WK系统失败:" + e.getMessage());
					throw new TfrBusinessException(e.getMessage());
				}
			}
		}
		
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 根据卸车任务ID获取任务下所有运单，同时修改交接单状态为“已入库”
	 * @author 045923-foss-shiwei
	 * @date 2012-12-19 下午8:23:03
	 */
	private List<UnloadWaybillDetailEntity> queryWaybillListByUnloadTaskId(UnloadTaskEntity baseEntity){
		//根据卸车任务获取任务下所有单据编号
		List<UnloadBillDetailEntity> billList = unloadTaskDao.queryUnloadTaskBillDetailListById(baseEntity.getId());
		List<UnloadWaybillDetailEntity> waybillList = new ArrayList<UnloadWaybillDetailEntity>();
		//如果是短途卸车任务
		if(StringUtils.equals(baseEntity.getUnloadType(), UnloadConstants.UNLOAD_TASK_TYPE_SHORT_DISTANCE)){
			//循环遍历所有单据（交接单），同时调用接口，更改车辆任务等单据状态
			List<UnloadBillDetailDto> arriveBillDtoList = new ArrayList<UnloadBillDetailDto>();
			for(UnloadBillDetailEntity billEntity : billList){
				String handOverBillNo = billEntity.getBillNo();
				UnloadBillDetailDto dto = new UnloadBillDetailDto();
				dto.setBillNo(handOverBillNo);
				dto.setBillType(UnloadConstants.BILL_TYPE_HANDOVER);
				arriveBillDtoList.add(dto);
				//修改交接单状态为已入库
				LOGGER.error("短途卸车结束，修改交接单状态为已入库，交接单号：" + handOverBillNo);
				//不更新快递货
				if(!UnloadCommonUtils.isExpressHandOver(handOverBillNo)){
					handOverBillService.updateHandOverBillStateByNo(handOverBillNo,LoadConstants.HANDOVERBILL_STATE_ALREADY_INSTOCK);
				}
				
				if(UnloadCommonUtils.isExpressHandOver(handOverBillNo)){
					//更新快递交接单 283250
					handOverBillService.updateWKHandOverBillStateByNo(handOverBillNo, LoadConstants.WKHANDOVERBILL_STATE_ALREADY_INSTOCK);
				}
				
				List<UnloadWaybillDetailEntity> innerWaybillList = unloadTaskDao.queryUnloadTaskWaybillDetailByHandOverBillNo(handOverBillNo);
				waybillList.addAll(innerWaybillList);
			}
			//更改车辆任务单据状态
			this.updateArriveBillState(arriveBillDtoList, TaskTruckConstant.BILL_ASSIGN_STATE_UNLOADED);
			return waybillList;
			//如果是长途卸车任务
		}else if(StringUtils.equals(baseEntity.getUnloadType(), UnloadConstants.UNLOAD_TASK_TYPE_LONG_DISTANCE)){
			//更改车辆任务等单据状态
			List<UnloadBillDetailDto> arriveBillDtoList = new ArrayList<UnloadBillDetailDto>();
			for(UnloadBillDetailEntity billEntity : billList){
				String vehicleAssembleNo = billEntity.getBillNo();
				UnloadBillDetailDto dto = new UnloadBillDetailDto();
				dto.setBillNo(vehicleAssembleNo);
				dto.setBillType(UnloadConstants.BILL_TYPE_VEHICLEASSEMBLE);
				//获取该配载单下的交接单，并将交接单状态修改为已入库
				List<String> handOverBillNoList = vehicleAssembleBillService.queryHandOverBillNosByVehicleAssembleNo(vehicleAssembleNo);
				for(String no : handOverBillNoList){
					
					
					//不更新快递货
					if(!UnloadCommonUtils.isExpressHandOver(no)){
						LOGGER.error("长途卸车结束，修改交接单状态为已入库，交接单号：" + no);
						handOverBillService.updateHandOverBillStateByNo(no,LoadConstants.HANDOVERBILL_STATE_ALREADY_INSTOCK);
					}
					
					if(UnloadCommonUtils.isExpressHandOver(no)){
					//更新快递交接单 283250
					handOverBillService.updateWKHandOverBillStateByNo(no, LoadConstants.WKHANDOVERBILL_STATE_ALREADY_INSTOCK);
					}
				}
				arriveBillDtoList.add(dto);
				List<UnloadWaybillDetailEntity> innerWaybillList = unloadTaskDao.queryUnloadTaskWaybillDetailByVehicleAssembleNo(vehicleAssembleNo);
				waybillList.addAll(innerWaybillList);
			}
			this.updateArriveBillState(arriveBillDtoList, TaskTruckConstant.BILL_ASSIGN_STATE_UNLOADED);
			return waybillList;
		}else if(StringUtils.equals(baseEntity.getUnloadType(),UnloadConstants.UNLOAD_TASK_TYPE_BUSINESS)
				||StringUtils.equals(baseEntity.getUnloadType(),UnloadConstants.UNLOAD_TASK_TYPE_PACKAGE)){
			//2015/8/28 272681 如果是商务专递卸车任务
			for(UnloadBillDetailEntity billEntity : billList){
				String handOverBillNo = billEntity.getBillNo();
				UnloadBillDetailDto dto = new UnloadBillDetailDto();
				dto.setBillNo(handOverBillNo);
				//修改交接单状态为已入库
				LOGGER.error("商务专递卸车结束，修改交接单状态为已入库，交接单号：" + handOverBillNo);
				List<UnloadWaybillDetailEntity> innerWaybillList = unloadTaskDao.queryUnloadTaskWaybillDetailByAirHandOverBillNo(handOverBillNo);
				waybillList.addAll(innerWaybillList);
			}
			
			return waybillList;
		}
		return waybillList;
	}
	
	/**
	 * 为多货的运单输出卸车运单明细Map，key为运单号，value为map（key流水号，value流水号信息）
	 * @author 045923-foss-shiwei
	 * @date 2012-12-20 上午10:26:24
	 */
	private Map<String,Map<String,ConfirmUnloadTaskBillsDto>> outPutWaybillDetailListForMoreGoods(List<ConfirmUnloadTaskBillsDto> moreSerialNoList){
		Map<String,Map<String,ConfirmUnloadTaskBillsDto>> map = new HashMap<String,Map<String,ConfirmUnloadTaskBillsDto>>();
		//转换为map
		for(ConfirmUnloadTaskBillsDto dto : moreSerialNoList){
			String waybillNo = dto.getWaybillNo();
			if(map.get(waybillNo) == null){
				Map<String,ConfirmUnloadTaskBillsDto> innerMap = new HashMap<String,ConfirmUnloadTaskBillsDto>();
				innerMap.put(dto.getSerialNo(), dto);
				map.put(waybillNo, innerMap);
			}else{
				Map<String,ConfirmUnloadTaskBillsDto> innerMap = map.get(waybillNo);
				innerMap.put(dto.getSerialNo(), dto);
				map.put(waybillNo, innerMap);
			}
		}
		return map;
	}
	
	/**
	 * 为少货众多list输出少货map：key为 waybillNo + "@" + billNo，value为map(key,流水号，map，流水号信息
	 * @author 045923-foss-shiwei
	 * @date 2012-12-20 上午10:58:56
	 */
	private Map<String,Map<String,ConfirmUnloadTaskBillsDto>> outPutWaybillListForLackGoods(List<ConfirmUnloadTaskBillsDto> lackVehicleAssembleBillList,
																																						List<ConfirmUnloadTaskBillsDto> lackHandOverBillList,
																																						List<ConfirmUnloadTaskBillsDto> lackWaybillList,
																																						List<ConfirmUnloadTaskBillsDto> lackSerialNoList,
																																						UnloadTaskEntity baseEntity){
		//定义map，key为 waybillNo + "@" + billNo，value为map(key,流水号，map，流水号信息)
		Map<String,Map<String,ConfirmUnloadTaskBillsDto>> map = new HashMap<String,Map<String,ConfirmUnloadTaskBillsDto>>();
		//如果为长途卸车
		if(StringUtils.equals(baseEntity.getUnloadType(), UnloadConstants.UNLOAD_TASK_TYPE_LONG_DISTANCE)){
			//遍历少货配载单list
			for(ConfirmUnloadTaskBillsDto dto : lackVehicleAssembleBillList){
				String vehicleAssembleNo = dto.getVehicleAssembleNo();
				List<String> handOverBillNoList = vehicleAssembleBillService.queryHandOverBillNosByVehicleAssembleNo(vehicleAssembleNo);
				for(String handOverBillNo : handOverBillNoList){
					List<HandOverBillSerialNoDetailEntity> serialNoList = handOverBillService.getHandOverBillSerialNoDetailsByWayBillNo(null, handOverBillNo);
					for(HandOverBillSerialNoDetailEntity serialNo : serialNoList){
						String waybillNo = serialNo.getWaybillNo();
						String key = vehicleAssembleNo + TAG + waybillNo;
						//如果map中无此key，则直接添加
						if(map.get(key) == null){
							Map<String,ConfirmUnloadTaskBillsDto> innerMap = new HashMap<String,ConfirmUnloadTaskBillsDto>();
							ConfirmUnloadTaskBillsDto innerDto = new ConfirmUnloadTaskBillsDto();
							innerDto.setVehicleAssembleNo(vehicleAssembleNo);
							innerDto.setHandOverBillNo(handOverBillNo);
							innerDto.setWaybillNo(waybillNo);
							innerDto.setSerialNo(serialNo.getSerialNo());
							innerMap.put(serialNo.getSerialNo(), innerDto);
							map.put(key, innerMap);
						}else{
							Map<String,ConfirmUnloadTaskBillsDto> innerMap = map.get(key);
							ConfirmUnloadTaskBillsDto innerDto = new ConfirmUnloadTaskBillsDto();
							innerDto.setVehicleAssembleNo(vehicleAssembleNo);
							innerDto.setHandOverBillNo(handOverBillNo);
							innerDto.setWaybillNo(waybillNo);
							innerDto.setSerialNo(serialNo.getSerialNo());
							innerMap.put(serialNo.getSerialNo(), innerDto);
							map.put(key, innerMap);
						}
					}
				}
			}
			//遍历少货交接单
			for(ConfirmUnloadTaskBillsDto dto : lackHandOverBillList){
				String vehicleAssembleNo = dto.getVehicleAssembleNo();
				String handOverBillNo = dto.getHandOverBillNo();
				//获取交接单下所有流水号
				List<HandOverBillSerialNoDetailEntity> serialNoList = handOverBillService.getHandOverBillSerialNoDetailsByWayBillNo(null, handOverBillNo);
				//遍历流水号
				for(HandOverBillSerialNoDetailEntity serialNo : serialNoList){
					String waybillNo = serialNo.getWaybillNo();
					String key = vehicleAssembleNo + TAG + waybillNo;
					//如果map中无此key，则直接添加
					if(map.get(key) == null){
						Map<String,ConfirmUnloadTaskBillsDto> innerMap = new HashMap<String,ConfirmUnloadTaskBillsDto>();
						ConfirmUnloadTaskBillsDto innerDto = new ConfirmUnloadTaskBillsDto();
						innerDto.setVehicleAssembleNo(vehicleAssembleNo);
						innerDto.setHandOverBillNo(handOverBillNo);
						innerDto.setWaybillNo(waybillNo);
						innerDto.setSerialNo(serialNo.getSerialNo());
						innerMap.put(serialNo.getSerialNo(), innerDto);
						map.put(key, innerMap);
					}else{
						Map<String,ConfirmUnloadTaskBillsDto> innerMap = map.get(key);
						ConfirmUnloadTaskBillsDto innerDto = new ConfirmUnloadTaskBillsDto();
						innerDto.setVehicleAssembleNo(vehicleAssembleNo);
						innerDto.setHandOverBillNo(handOverBillNo);
						innerDto.setWaybillNo(waybillNo);
						innerDto.setSerialNo(serialNo.getSerialNo());
						innerMap.put(serialNo.getSerialNo(), innerDto);
						map.put(key, innerMap);
					}
				}
			}
			//遍历少货运单
			for(ConfirmUnloadTaskBillsDto dto : lackWaybillList){
				String vehicleAssembleNo = dto.getVehicleAssembleNo();
				String handOverBillNo = dto.getHandOverBillNo();
				String waybillNo = dto.getWaybillNo();
				//获取指定运单号、交接单号下的流水号list
				List<HandOverBillSerialNoDetailEntity> serialNoList = handOverBillService.getHandOverBillSerialNoDetailsByWayBillNo(waybillNo, handOverBillNo);
				//遍历流水号
				for(HandOverBillSerialNoDetailEntity serialNo : serialNoList){
					String key = vehicleAssembleNo + TAG + waybillNo;
					//如果map中无此key，则直接添加
					if(map.get(key) == null){
						Map<String,ConfirmUnloadTaskBillsDto> innerMap = new HashMap<String,ConfirmUnloadTaskBillsDto>();
						ConfirmUnloadTaskBillsDto innerDto = new ConfirmUnloadTaskBillsDto();
						innerDto.setVehicleAssembleNo(vehicleAssembleNo);
						innerDto.setHandOverBillNo(handOverBillNo);
						innerDto.setWaybillNo(waybillNo);
						innerDto.setSerialNo(serialNo.getSerialNo());
						innerMap.put(serialNo.getSerialNo(), innerDto);
						map.put(key, innerMap);
					}else{
						Map<String,ConfirmUnloadTaskBillsDto> innerMap = map.get(key);
						ConfirmUnloadTaskBillsDto innerDto = new ConfirmUnloadTaskBillsDto();
						innerDto.setVehicleAssembleNo(vehicleAssembleNo);
						innerDto.setHandOverBillNo(handOverBillNo);
						innerDto.setWaybillNo(waybillNo);
						innerDto.setSerialNo(serialNo.getSerialNo());
						innerMap.put(serialNo.getSerialNo(), innerDto);
						map.put(key, innerMap);
					}
				}
			}
			//遍历少货流水号
			for(ConfirmUnloadTaskBillsDto serialNo : lackSerialNoList){
				String vehicleAssembleNo = serialNo.getVehicleAssembleNo();
				String handOverBillNo = serialNo.getHandOverBillNo();
				String waybillNo = serialNo.getWaybillNo();
				String key = vehicleAssembleNo + TAG + waybillNo;
				if(map.get(key) == null){
					Map<String,ConfirmUnloadTaskBillsDto> innerMap = new HashMap<String,ConfirmUnloadTaskBillsDto>();
					ConfirmUnloadTaskBillsDto innerDto = new ConfirmUnloadTaskBillsDto();
					innerDto.setVehicleAssembleNo(vehicleAssembleNo);
					innerDto.setHandOverBillNo(handOverBillNo);
					innerDto.setWaybillNo(waybillNo);
					innerDto.setSerialNo(serialNo.getSerialNo());
					innerMap.put(serialNo.getSerialNo(), innerDto);
					map.put(key, innerMap);
				}else{
					Map<String,ConfirmUnloadTaskBillsDto> innerMap = map.get(key);
					ConfirmUnloadTaskBillsDto innerDto = new ConfirmUnloadTaskBillsDto();
					innerDto.setVehicleAssembleNo(vehicleAssembleNo);
					innerDto.setHandOverBillNo(handOverBillNo);
					innerDto.setWaybillNo(waybillNo);
					innerDto.setSerialNo(serialNo.getSerialNo());
					innerMap.put(serialNo.getSerialNo(), innerDto);
					map.put(key, innerMap);
				}
			}
			return map;
		}else if(StringUtils.equals(baseEntity.getUnloadType(), UnloadConstants.UNLOAD_TASK_TYPE_SHORT_DISTANCE)){
			//遍历少货交接单
			for(ConfirmUnloadTaskBillsDto dto : lackHandOverBillList){
				String handOverBillNo = dto.getHandOverBillNo();
				//获取交接单下所有流水号
				List<HandOverBillSerialNoDetailEntity> serialNoList = handOverBillService.getHandOverBillSerialNoDetailsByWayBillNo(null, handOverBillNo);
				//遍历流水号
				for(HandOverBillSerialNoDetailEntity serialNo : serialNoList){
					String waybillNo = serialNo.getWaybillNo();
					String key = handOverBillNo + TAG + waybillNo;
					//如果map中无此key，则直接添加
					if(map.get(key) == null){
						Map<String,ConfirmUnloadTaskBillsDto> innerMap = new HashMap<String,ConfirmUnloadTaskBillsDto>();
						ConfirmUnloadTaskBillsDto innerDto = new ConfirmUnloadTaskBillsDto();
						innerDto.setHandOverBillNo(handOverBillNo);
						innerDto.setWaybillNo(waybillNo);
						innerDto.setSerialNo(serialNo.getSerialNo());
						innerMap.put(serialNo.getSerialNo(), innerDto);
						map.put(key, innerMap);
					}else{
						Map<String,ConfirmUnloadTaskBillsDto> innerMap = map.get(key);
						ConfirmUnloadTaskBillsDto innerDto = new ConfirmUnloadTaskBillsDto();
						innerDto.setHandOverBillNo(handOverBillNo);
						innerDto.setWaybillNo(waybillNo);
						innerDto.setSerialNo(serialNo.getSerialNo());
						innerMap.put(serialNo.getSerialNo(), innerDto);
						map.put(key, innerMap);
					}
				}
			}
			//遍历少货运单
			for(ConfirmUnloadTaskBillsDto dto : lackWaybillList){
				String handOverBillNo = dto.getHandOverBillNo();
				String waybillNo = dto.getWaybillNo();
				//获取指定运单号、交接单号下的流水号list
				List<HandOverBillSerialNoDetailEntity> serialNoList = handOverBillService.getHandOverBillSerialNoDetailsByWayBillNo(waybillNo, handOverBillNo);
				//遍历流水号
				for(HandOverBillSerialNoDetailEntity serialNo : serialNoList){
					String key = handOverBillNo + TAG + waybillNo;
					//如果map中无此key，则直接添加
					if(map.get(key) == null){
						Map<String,ConfirmUnloadTaskBillsDto> innerMap = new HashMap<String,ConfirmUnloadTaskBillsDto>();
						ConfirmUnloadTaskBillsDto innerDto = new ConfirmUnloadTaskBillsDto();
						innerDto.setHandOverBillNo(handOverBillNo);
						innerDto.setWaybillNo(waybillNo);
						innerDto.setSerialNo(serialNo.getSerialNo());
						innerMap.put(serialNo.getSerialNo(), innerDto);
						map.put(key, innerMap);
					}else{
						Map<String,ConfirmUnloadTaskBillsDto> innerMap = map.get(key);
						ConfirmUnloadTaskBillsDto innerDto = new ConfirmUnloadTaskBillsDto();
						innerDto.setHandOverBillNo(handOverBillNo);
						innerDto.setWaybillNo(waybillNo);
						innerDto.setSerialNo(serialNo.getSerialNo());
						innerMap.put(serialNo.getSerialNo(), innerDto);
						map.put(key, innerMap);
					}
				}
			}
			//遍历少货流水号
			for(ConfirmUnloadTaskBillsDto serialNo : lackSerialNoList){
				String handOverBillNo = serialNo.getHandOverBillNo();
				String waybillNo = serialNo.getWaybillNo();
				String key = handOverBillNo + TAG + waybillNo;
				if(map.get(key) == null){
					Map<String,ConfirmUnloadTaskBillsDto> innerMap = new HashMap<String,ConfirmUnloadTaskBillsDto>();
					ConfirmUnloadTaskBillsDto innerDto = new ConfirmUnloadTaskBillsDto();
					innerDto.setHandOverBillNo(handOverBillNo);
					innerDto.setWaybillNo(waybillNo);
					innerDto.setSerialNo(serialNo.getSerialNo());
					innerMap.put(serialNo.getSerialNo(), innerDto);
					map.put(key, innerMap);
				}else{
					Map<String,ConfirmUnloadTaskBillsDto> innerMap = map.get(key);
					ConfirmUnloadTaskBillsDto innerDto = new ConfirmUnloadTaskBillsDto();
					innerDto.setHandOverBillNo(handOverBillNo);
					innerDto.setWaybillNo(waybillNo);
					innerDto.setSerialNo(serialNo.getSerialNo());
					innerMap.put(serialNo.getSerialNo(), innerDto);
					map.put(key, innerMap);
				}
			}
			return map;
			//2015/9/6 272681  如果商务专递卸车
		}else if(StringUtils.equals(baseEntity.getUnloadType(), UnloadConstants.UNLOAD_TASK_TYPE_BUSINESS)
				||StringUtils.equals(baseEntity.getUnloadType(), UnloadConstants.UNLOAD_TASK_TYPE_PACKAGE)){
			//遍历少货交接单
			for(ConfirmUnloadTaskBillsDto dto : lackHandOverBillList){
				String handOverBillNo = dto.getHandOverBillNo();
				//获取商务专递交接单下所有流水号
				List<HandOverBillSerialNoDetailEntity> serialNoList = handOverBillService.getBusAirHandOverBillSerialNoDetailsByWayBillNo(null, handOverBillNo);
				//遍历流水号
				for(HandOverBillSerialNoDetailEntity serialNo : serialNoList){
					String waybillNo = serialNo.getWaybillNo();
					String key = handOverBillNo + TAG + waybillNo;
					//如果map中无此key，则直接添加
					if(map.get(key) == null){
						Map<String,ConfirmUnloadTaskBillsDto> innerMap = new HashMap<String,ConfirmUnloadTaskBillsDto>();
						ConfirmUnloadTaskBillsDto innerDto = new ConfirmUnloadTaskBillsDto();
						innerDto.setHandOverBillNo(handOverBillNo);
						innerDto.setWaybillNo(waybillNo);
						innerDto.setSerialNo(serialNo.getSerialNo());
						innerMap.put(serialNo.getSerialNo(), innerDto);
						map.put(key, innerMap);
					}else{
						Map<String,ConfirmUnloadTaskBillsDto> innerMap = map.get(key);
						ConfirmUnloadTaskBillsDto innerDto = new ConfirmUnloadTaskBillsDto();
						innerDto.setHandOverBillNo(handOverBillNo);
						innerDto.setWaybillNo(waybillNo);
						innerDto.setSerialNo(serialNo.getSerialNo());
						innerMap.put(serialNo.getSerialNo(), innerDto);
						map.put(key, innerMap);
					}
				}
			}
			//遍历少货运单
			for(ConfirmUnloadTaskBillsDto dto : lackWaybillList){
				String handOverBillNo = dto.getHandOverBillNo();
				String waybillNo = dto.getWaybillNo();
				//获取指定运单号、交接单号下的流水号list
				List<HandOverBillSerialNoDetailEntity> serialNoList = handOverBillService.getBusAirHandOverBillSerialNoDetailsByWayBillNo(waybillNo, handOverBillNo);
				//遍历流水号
				for(HandOverBillSerialNoDetailEntity serialNo : serialNoList){
					String key = handOverBillNo + TAG + waybillNo;
					//如果map中无此key，则直接添加
					if(map.get(key) == null){
						Map<String,ConfirmUnloadTaskBillsDto> innerMap = new HashMap<String,ConfirmUnloadTaskBillsDto>();
						ConfirmUnloadTaskBillsDto innerDto = new ConfirmUnloadTaskBillsDto();
						innerDto.setHandOverBillNo(handOverBillNo);
						innerDto.setWaybillNo(waybillNo);
						innerDto.setSerialNo(serialNo.getSerialNo());
						innerMap.put(serialNo.getSerialNo(), innerDto);
						map.put(key, innerMap);
					}else{
						Map<String,ConfirmUnloadTaskBillsDto> innerMap = map.get(key);
						ConfirmUnloadTaskBillsDto innerDto = new ConfirmUnloadTaskBillsDto();
						innerDto.setHandOverBillNo(handOverBillNo);
						innerDto.setWaybillNo(waybillNo);
						innerDto.setSerialNo(serialNo.getSerialNo());
						innerMap.put(serialNo.getSerialNo(), innerDto);
						map.put(key, innerMap);
					}
				}
			}
			//遍历少货流水号
			for(ConfirmUnloadTaskBillsDto serialNo : lackSerialNoList){
				String handOverBillNo = serialNo.getHandOverBillNo();
				String waybillNo = serialNo.getWaybillNo();
				String key = handOverBillNo + TAG + waybillNo;
				if(map.get(key) == null){
					Map<String,ConfirmUnloadTaskBillsDto> innerMap = new HashMap<String,ConfirmUnloadTaskBillsDto>();
					ConfirmUnloadTaskBillsDto innerDto = new ConfirmUnloadTaskBillsDto();
					innerDto.setHandOverBillNo(handOverBillNo);
					innerDto.setWaybillNo(waybillNo);
					innerDto.setSerialNo(serialNo.getSerialNo());
					innerMap.put(serialNo.getSerialNo(), innerDto);
					map.put(key, innerMap);
				}else{
					Map<String,ConfirmUnloadTaskBillsDto> innerMap = map.get(key);
					ConfirmUnloadTaskBillsDto innerDto = new ConfirmUnloadTaskBillsDto();
					innerDto.setHandOverBillNo(handOverBillNo);
					innerDto.setWaybillNo(waybillNo);
					innerDto.setSerialNo(serialNo.getSerialNo());
					innerMap.put(serialNo.getSerialNo(), innerDto);
					map.put(key, innerMap);
				}
			}
			return map;
		}
		return map;
	}
	
	/**
	 * 私有方法，长途卸车时保存卸车数据调用，保存卸车运单明细和流水号明细，入库多货的流水号和未少货的流水号
	 * @author 045923-foss-shiwei
	 * @date 2012-12-20 下午3:21:57
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private int saveDataWhenConfirmLongDistanceUnloadTask(List<UnloadWaybillDetailEntity> waybillDetailList,
																									List<UnloadSerialNoDetailEntity> serialNoDetailList,
																									List<UnloadWaybillDetailEntity> oldWaybillList,
																									Map<String,Map<String,ConfirmUnloadTaskBillsDto>> lackWaybillMap,
																									Map<String,Map<String,ConfirmUnloadTaskBillsDto>> moreWaybillMap,
																									UnloadTaskEntity baseEntity){
		for(UnloadWaybillDetailEntity entity : oldWaybillList){
			//获取配载单号
			String vehicleAssembleNo = entity.getBillNo();
			//获取交接单号
			String handOverBillNo = entity.getHandOverBillNo();
			//获取运单号
			String waybillNo = entity.getWaybillNo();
			//构造少货map的id
			String lackMapId = vehicleAssembleNo + TAG + waybillNo;
			//获取所有该配载单、该运单下的少货件数
			int lackPieces = 0;
			if(lackWaybillMap.get(lackMapId) != null){
				lackPieces = lackWaybillMap.get(lackMapId).size();
			}
			//获取该运单的多货件数
			Map<String,ConfirmUnloadTaskBillsDto> moreSerialNoMap = moreWaybillMap.get(entity.getWaybillNo());
			int morePieces = 0;
			if(moreSerialNoMap != null){
				morePieces = moreSerialNoMap.size();
			}
			//计算该运单的总件数
			int optPieces = entity.getHandOverPieces() - lackPieces + morePieces;
			//操作件数
			entity.setOperationGoodsQty(Integer.valueOf(optPieces));
			//卸车重量
			BigDecimal weight = entity.getWeight().multiply(new BigDecimal(optPieces)).divide(new BigDecimal(entity.getHandOverPieces()),2,BigDecimal.ROUND_UP);
			entity.setWeight(weight);
			//卸车体积
			BigDecimal volumn = entity.getVolume().multiply(new BigDecimal(optPieces)).divide(new BigDecimal(entity.getHandOverPieces()),2,BigDecimal.ROUND_UP);
			entity.setVolume(volumn);
			//运单明细卸车任务ID
			entity.setUnloadTaskId(baseEntity.getId());
			//ID
			entity.setId(UUIDUtils.getUUID());
			//扫描件数：0
			entity.setScanGoodsQty(Integer.valueOf(0));
			//任务建立时间
			entity.setTaskBeginTime(baseEntity.getUnloadStartTime());
			//任务修改时间
			entity.setModifyDate(baseEntity.getUnloadStartTime());
			//任务建立部门
			entity.setUnloadOrgCode(baseEntity.getUnloadOrgCode());
			//包号
			if(handOverBillNo!=null){
				if(handOverBillNo.startsWith("B")){
					entity.setPackageNo(handOverBillNo);
				}
			}
			//将该运单置于waybillDetailList中
			waybillDetailList.add(entity);
			
			//获取该交接单、该运单下的流水号，如果没有少货，则为正常
			List<HandOverBillSerialNoDetailEntity> oldSerialNoList = handOverBillService.getHandOverBillSerialNoDetailsByWayBillNo(waybillNo, handOverBillNo);
			
			//遍历交接单中运单下的流水号
			for(HandOverBillSerialNoDetailEntity oldSerialNo : oldSerialNoList){
				//构造待插入流水号，置于serialNoDetailList中
				//构造待插入的流水号明细对象
				UnloadSerialNoDetailEntity tempSerialNo = new UnloadSerialNoDetailEntity();
				tempSerialNo.setId(UUIDUtils.getUUID());
				tempSerialNo.setUnloadWaybillDetailId(entity.getId());
				//扫描状态，空
				tempSerialNo.setScanStatus(null);
				//货物状态 如果在少货map中发现该流水号，则为少货，否则为正常
				if(lackWaybillMap.get(lackMapId) != null){
					Map<String,ConfirmUnloadTaskBillsDto> tempMap = lackWaybillMap.get(lackMapId);
					if(tempMap.get(oldSerialNo.getSerialNo()) != null){
						tempSerialNo.setGoodsStatus(UnloadConstants.UNLOAD_GOODS_STATE_LACK);// 少货
					}else{
						tempSerialNo.setGoodsStatus(UnloadConstants.UNLOAD_GOODS_STATE_NORMAL);// 正常
						//调用库存service，入库该件货物
						/*InOutStockEntity stockEntity = new InOutStockEntity();
						stockEntity.setWaybillNO(waybillNo);
						stockEntity.setSerialNO(oldSerialNo.getSerialNo());
						stockEntity.setOrgCode(currentInfo.getCurrentDeptCode());
						stockEntity.setOperatorName(currentInfo.getEmpName());
						stockEntity.setOperatorCode(currentInfo.getEmpCode());
						stockEntity.setInOutStockType(StockConstants.UNLOAD_GOODS_IN_STOCK_TYPE);
						stockService.inStockPC(stockEntity);*/
					}
				}else{
					tempSerialNo.setGoodsStatus(UnloadConstants.UNLOAD_GOODS_STATE_NORMAL);// 正常
					//调用库存service，入库该件货物
					/*InOutStockEntity stockEntity = new InOutStockEntity();
					stockEntity.setWaybillNO(waybillNo);
					stockEntity.setSerialNO(oldSerialNo.getSerialNo());
					stockEntity.setOrgCode(currentInfo.getCurrentDeptCode());
					stockEntity.setOperatorName(currentInfo.getEmpName());
					stockEntity.setOperatorCode(currentInfo.getEmpCode());
					stockEntity.setInOutStockType(StockConstants.UNLOAD_GOODS_IN_STOCK_TYPE);
					stockService.inStockPC(stockEntity);*/
				}
				//操作时间
				tempSerialNo.setOptTime(baseEntity.getUnloadEndTime());
				//创建时间
				tempSerialNo.setCreateDate(baseEntity.getUnloadStartTime());
				//流水号
				tempSerialNo.setSerialNo(oldSerialNo.getSerialNo());
				//任务建立时间
				tempSerialNo.setTaskCreateTime(baseEntity.getUnloadStartTime());
				//建立任务部门编码
				tempSerialNo.setCreateOrgCode(baseEntity.getUnloadOrgCode());
				//将构造好的流水号置于serialNoDetailList中
				serialNoDetailList.add(tempSerialNo);
			}
			
			//将该运单的多货流水号置于serialNoDetailList中，然后将该运单的流水号map从多货运单中删除
			if(moreSerialNoMap != null){
				Set entrySet = moreSerialNoMap.entrySet();
				Iterator it = entrySet.iterator();
				while(it.hasNext()){
					Map.Entry entry = (Map.Entry)it.next();
					ConfirmUnloadTaskBillsDto serialNo = (ConfirmUnloadTaskBillsDto)entry.getValue();
					//构造待插入的流水号明细对象
					UnloadSerialNoDetailEntity moreSerialNo = new UnloadSerialNoDetailEntity();
					moreSerialNo.setId(UUIDUtils.getUUID());
					moreSerialNo.setUnloadWaybillDetailId(entity.getId());
					//扫描状态，空
					moreSerialNo.setScanStatus(null);
					
					//货物状态 多货
					//调用走货路径接口，判断是夹带多货还是异地多货
					FeedbackDto feedbackDto = calculateTransportPathService.getNextOrgAndTime(serialNo.getWaybillNo(), 
							serialNo.getSerialNo(), baseEntity.getUnloadOrgCode());
					if(feedbackDto.getPathDetailEntity() == null){//异地夹带多货
						moreSerialNo.setGoodsStatus(UnloadConstants.UNLOAD_GOODS_STATE_MORE_ALLOPATRY_ENTRAINED);
					}else{//夹带多货
						moreSerialNo.setGoodsStatus(UnloadConstants.UNLOAD_GOODS_STATE_MORE_ENTRAINED);
					}
					//操作时间
					moreSerialNo.setOptTime(baseEntity.getUnloadEndTime());
					//创建时间
					moreSerialNo.setCreateDate(baseEntity.getUnloadStartTime());
					//流水号
					moreSerialNo.setSerialNo(serialNo.getSerialNo());
					//任务建立时间
					moreSerialNo.setTaskCreateTime(baseEntity.getUnloadStartTime());
					//建立任务部门编码
					moreSerialNo.setCreateOrgCode(baseEntity.getUnloadOrgCode());
					//将构造好的流水号置于serialNoDetailList中
					serialNoDetailList.add(moreSerialNo);
					//将该多货流水号入库
					/*InOutStockEntity stockEntity = new InOutStockEntity();
					stockEntity.setWaybillNO(waybillNo);
					stockEntity.setSerialNO(serialNo.getSerialNo());
					stockEntity.setOrgCode(currentInfo.getCurrentDeptCode());
					stockEntity.setOperatorName(currentInfo.getEmpName());
					stockEntity.setOperatorCode(currentInfo.getEmpCode());
					stockEntity.setInOutStockType(StockConstants.UNLOAD_GOODS_MORE_IN_STOCK_TYPE);
					stockService.inStockPC(stockEntity);*/
				}
				//将该运单多货流水号map从多货运单map中删除
				moreWaybillMap.remove(entity.getWaybillNo());
			}
		}
		//此时多货map中剩下的都是卸车任务中没有的运单，遍历map，构造卸车任务运单明细和流水号明细
		if(moreWaybillMap != null && moreWaybillMap.size() != 0){
			Set entrySet = moreWaybillMap.entrySet();
			Iterator it = entrySet.iterator();
			while(it.hasNext()){
				Map.Entry entry = (Map.Entry)it.next();
				Map<String,ConfirmUnloadTaskBillsDto> serialNoMap = (Map<String,ConfirmUnloadTaskBillsDto>)entry.getValue();
				//获取运单号
				String waybillNo = (String)entry.getKey();
				//调用接送货接口，获取运单信息
				WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(waybillNo);
				if(waybill == null){
					throw new TfrBusinessException("运单数据获取失败，运单号：" + waybillNo);
				}
				//构造待保存的卸车任务运单明细实体
				UnloadWaybillDetailEntity waybillEntity = new UnloadWaybillDetailEntity();
				//ID
				waybillEntity.setId(UUIDUtils.getUUID());
				//UNLOAD_TASK_ID
				waybillEntity.setUnloadTaskId(baseEntity.getId());
				//单据编号
				waybillEntity.setBillNo(null);
				//运单号
				waybillEntity.setWaybillNo(waybillNo);
				//已操作件数
				waybillEntity.setOperationGoodsQty(Integer.valueOf(serialNoMap.size()));
				//重量
				waybillEntity.setWeight((waybill.getGoodsWeightTotal().divide(new BigDecimal(waybill.getGoodsQtyTotal()),2,BigDecimal.ROUND_UP)).multiply(new BigDecimal(serialNoMap.size())));
				//体积
				waybillEntity.setVolume((waybill.getGoodsVolumeTotal().divide(new BigDecimal(waybill.getGoodsQtyTotal()),2,BigDecimal.ROUND_UP)).multiply(new BigDecimal(serialNoMap.size())));
				//扫描件数
				waybillEntity.setScanGoodsQty(Integer.valueOf(0));
				//出发部门
				//到达部门
				//货名
				waybillEntity.setGoodsName(waybill.getGoodsName());
				//包装
				waybillEntity.setPack(waybill.getGoodsPackage());
				//运输性质
				waybillEntity.setTransportType(productService4Dubbo.getProductByCache(waybill.getProductCode(), null).getName());
				//任务建立时间
				waybillEntity.setTaskBeginTime(baseEntity.getUnloadStartTime());
				//任务修改时间
				waybillEntity.setModifyDate(baseEntity.getUnloadStartTime());
				//建立任务部门编码
				waybillEntity.setUnloadOrgCode(baseEntity.getUnloadOrgCode());
				waybillDetailList.add(waybillEntity);
				
				//遍历多货的流水号，构造待保存的流水号实体
				Set entrySet1 = serialNoMap.entrySet();
				Iterator it1 = entrySet1.iterator();
				while(it1.hasNext()){
					Map.Entry entry1 = (Map.Entry)it1.next();
					//获取流水号
					String serialNo = entry1.getKey().toString();
					//构造流水号实体
					UnloadSerialNoDetailEntity tempSerialNo = new UnloadSerialNoDetailEntity();
					tempSerialNo.setId(UUIDUtils.getUUID());
					//任务运单明细ID
					tempSerialNo.setUnloadWaybillDetailId(waybillEntity.getId());
					//扫描状态，空
					tempSerialNo.setScanStatus(null);
					
					//货物状态 多货
					//调用走货路径接口，判断是夹带多货还是异地多货
					FeedbackDto feedbackDto = calculateTransportPathService.getNextOrgAndTime(waybillEntity.getWaybillNo(), 
							serialNo, baseEntity.getUnloadOrgCode());
					if(feedbackDto.getPathDetailEntity() == null){//异地夹带多货
						tempSerialNo.setGoodsStatus(UnloadConstants.UNLOAD_GOODS_STATE_MORE_ALLOPATRY_ENTRAINED);
					}else{//夹带多货
						tempSerialNo.setGoodsStatus(UnloadConstants.UNLOAD_GOODS_STATE_MORE_ENTRAINED);
					}
					
					//操作时间
					tempSerialNo.setOptTime(baseEntity.getUnloadEndTime());
					//创建时间
					tempSerialNo.setCreateDate(baseEntity.getUnloadStartTime());
					//流水号
					tempSerialNo.setSerialNo(serialNo);
					//任务建立时间
					tempSerialNo.setTaskCreateTime(baseEntity.getUnloadStartTime());
					//建立任务部门编码
					tempSerialNo.setCreateOrgCode(baseEntity.getUnloadOrgCode());
					//将构造好的流水号置于serialNoDetailList中
					serialNoDetailList.add(tempSerialNo);
					
					//流水号入库
					/*InOutStockEntity stockEntity = new InOutStockEntity();
					stockEntity.setWaybillNO(waybillNo);
					stockEntity.setSerialNO(tempSerialNo.getSerialNo());
					stockEntity.setOrgCode(currentInfo.getCurrentDeptCode());
					stockEntity.setOperatorName(currentInfo.getEmpName());
					stockEntity.setOperatorCode(currentInfo.getEmpCode());
					stockEntity.setInOutStockType(StockConstants.UNLOAD_GOODS_MORE_IN_STOCK_TYPE);
					stockService.inStockPC(stockEntity);*/
				}
			}
		}
		//保存卸车任务运单明细和流水号明细
		this.addUnloadTaskWaybillDetailList(waybillDetailList);
		this.addUnloadTaskSerialNoDetailList(serialNoDetailList);
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 短途卸车完成后，确认保存数据，入库多货的流水号和未少货的流水号
	 * @author 045923-foss-shiwei
	 * @date 2012-12-20 下午3:33:38
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private int saveDataWhenConfirmShortDistanceUnloadTask(List<UnloadWaybillDetailEntity> waybillDetailList,
			List<UnloadSerialNoDetailEntity> serialNoDetailList,
			List<UnloadWaybillDetailEntity> oldWaybillList,
			Map<String,Map<String,ConfirmUnloadTaskBillsDto>> lackWaybillMap,
			Map<String,Map<String,ConfirmUnloadTaskBillsDto>> moreWaybillMap,
			UnloadTaskEntity baseEntity){
		for(UnloadWaybillDetailEntity entity : oldWaybillList){
			//获取交接单号
			String handOverBillNo = entity.getHandOverBillNo();
			//获取运单号
			String waybillNo = entity.getWaybillNo();
			//运单明细卸车任务ID
			entity.setUnloadTaskId(baseEntity.getId());
			//构造少货map的id
			String lackMapId = handOverBillNo + TAG + waybillNo;
			//获取所有该交接单、该运单下的少货件数
			int lackPieces = 0;
			if(lackWaybillMap.get(lackMapId) != null){
				lackPieces = lackWaybillMap.get(lackMapId).size();
			}
			//获取该运单的多货件数
			Map<String,ConfirmUnloadTaskBillsDto> moreSerialNoMap = moreWaybillMap.get(entity.getWaybillNo());
			int morePieces = 0;
			if(moreSerialNoMap != null){
				morePieces = moreSerialNoMap.size();
			}
			//计算该运单的总件数
			int optPieces = entity.getHandOverPieces() - lackPieces + morePieces;
			//操作件数
			entity.setOperationGoodsQty(Integer.valueOf(optPieces));
			//卸车重量
			BigDecimal weight = entity.getWeight().multiply(new BigDecimal(optPieces)).divide(new BigDecimal(entity.getHandOverPieces()),2,BigDecimal.ROUND_UP);
			entity.setWeight(weight);
			//卸车体积
			BigDecimal volumn = entity.getVolume().multiply(new BigDecimal(optPieces)).divide(new BigDecimal(entity.getHandOverPieces()),2,BigDecimal.ROUND_UP);
			entity.setVolume(volumn);
			//ID
			entity.setId(UUIDUtils.getUUID());
			//扫描件数：0
			entity.setScanGoodsQty(Integer.valueOf(0));
			//任务建立时间
			entity.setTaskBeginTime(baseEntity.getUnloadStartTime());
			//任务修改时间
			entity.setModifyDate(baseEntity.getUnloadStartTime());
			//任务建立部门
			entity.setUnloadOrgCode(baseEntity.getUnloadOrgCode());
			//包号
			if(handOverBillNo!=null){
				if(handOverBillNo.startsWith("B")){
					entity.setPackageNo(handOverBillNo);
				}
			}
			//将该运单置于waybillDetailList中
			waybillDetailList.add(entity);
			
			//获取该交接单、该运单下的流水号，如果没有少货，则为正常
			List<HandOverBillSerialNoDetailEntity> oldSerialNoList = handOverBillService.getHandOverBillSerialNoDetailsByWayBillNo(waybillNo, handOverBillNo);
		
			//遍历交接单中运单下的流水号
			for(HandOverBillSerialNoDetailEntity oldSerialNo : oldSerialNoList){
				//构造待插入流水号，置于serialNoDetailList中
				//构造待插入的流水号明细对象
				UnloadSerialNoDetailEntity tempSerialNo = new UnloadSerialNoDetailEntity();
				tempSerialNo.setId(UUIDUtils.getUUID());
				tempSerialNo.setUnloadWaybillDetailId(entity.getId());
				//扫描状态，空
				tempSerialNo.setScanStatus(null);
				//货物状态 如果在少货map中发现该流水号，则为少货，否则为正常
				if(lackWaybillMap.get(lackMapId) != null){
					Map<String,ConfirmUnloadTaskBillsDto> tempMap = lackWaybillMap.get(lackMapId);
					if(tempMap.get(oldSerialNo.getSerialNo()) != null){
						tempSerialNo.setGoodsStatus(UnloadConstants.UNLOAD_GOODS_STATE_LACK);// 少货
					}else{
						tempSerialNo.setGoodsStatus(UnloadConstants.UNLOAD_GOODS_STATE_NORMAL);// 正常
						//调用库存service，入库该件货物
						/*InOutStockEntity stockEntity = new InOutStockEntity();
						stockEntity.setWaybillNO(waybillNo);
						stockEntity.setSerialNO(oldSerialNo.getSerialNo());
						stockEntity.setOrgCode(currentInfo.getCurrentDeptCode());
						stockEntity.setOperatorName(currentInfo.getEmpName());
						stockEntity.setOperatorCode(currentInfo.getEmpCode());
						stockEntity.setInOutStockType(StockConstants.UNLOAD_GOODS_IN_STOCK_TYPE);
						stockService.inStockPC(stockEntity);*/
					}
				}else{
					tempSerialNo.setGoodsStatus(UnloadConstants.UNLOAD_GOODS_STATE_NORMAL);//正常
					//调用库存service，入库该件货物
					/*InOutStockEntity stockEntity = new InOutStockEntity();
					stockEntity.setWaybillNO(waybillNo);
					stockEntity.setSerialNO(oldSerialNo.getSerialNo());
					stockEntity.setOrgCode(currentInfo.getCurrentDeptCode());
					stockEntity.setOperatorName(currentInfo.getEmpName());
					stockEntity.setOperatorCode(currentInfo.getEmpCode());
					stockEntity.setInOutStockType(StockConstants.UNLOAD_GOODS_IN_STOCK_TYPE);
					stockService.inStockPC(stockEntity);*/
				}
				//操作时间
				tempSerialNo.setOptTime(baseEntity.getUnloadEndTime());
				//创建时间
				tempSerialNo.setCreateDate(baseEntity.getUnloadStartTime());
				//流水号
				tempSerialNo.setSerialNo(oldSerialNo.getSerialNo());
				//任务建立时间
				tempSerialNo.setTaskCreateTime(baseEntity.getUnloadStartTime());
				//建立任务部门编码
				tempSerialNo.setCreateOrgCode(baseEntity.getUnloadOrgCode());
				//将构造好的流水号置于serialNoDetailList中
				serialNoDetailList.add(tempSerialNo);
			}
			
			//将该运单的多货流水号置于serialNoDetailList中，然后将该运单的流水号map从多货运单中删除
			if(moreSerialNoMap != null){
				Set entrySet = moreSerialNoMap.entrySet();
				Iterator it = entrySet.iterator();
				while(it.hasNext()){
					Map.Entry entry = (Map.Entry)it.next();
					ConfirmUnloadTaskBillsDto serialNo = (ConfirmUnloadTaskBillsDto)entry.getValue();
					//构造待插入的流水号明细对象
					UnloadSerialNoDetailEntity moreSerialNo = new UnloadSerialNoDetailEntity();
					moreSerialNo.setId(UUIDUtils.getUUID());
					moreSerialNo.setUnloadWaybillDetailId(entity.getId());
					//扫描状态，空
					moreSerialNo.setScanStatus(null);
					
					//货物状态 多货
					//调用走货路径接口，判断是夹带多货还是异地多货
					FeedbackDto feedbackDto = calculateTransportPathService.getNextOrgAndTime(serialNo.getWaybillNo(), 
							serialNo.getSerialNo(), baseEntity.getUnloadOrgCode());
					if(feedbackDto.getPathDetailEntity() == null){//异地夹带多货
						moreSerialNo.setGoodsStatus(UnloadConstants.UNLOAD_GOODS_STATE_MORE_ALLOPATRY_ENTRAINED);
					}else{//夹带多货
						moreSerialNo.setGoodsStatus(UnloadConstants.UNLOAD_GOODS_STATE_MORE_ENTRAINED);
					}
					
					//操作时间
					moreSerialNo.setOptTime(baseEntity.getUnloadEndTime());
					//创建时间
					moreSerialNo.setCreateDate(baseEntity.getUnloadStartTime());
					//流水号
					moreSerialNo.setSerialNo(serialNo.getSerialNo());
					//任务建立时间
					moreSerialNo.setTaskCreateTime(baseEntity.getUnloadStartTime());
					//建立任务部门编码
					moreSerialNo.setCreateOrgCode(baseEntity.getUnloadOrgCode());
					//将构造好的流水号置于serialNoDetailList中
					serialNoDetailList.add(moreSerialNo);
					//将该多货流水号入库
					/*InOutStockEntity stockEntity = new InOutStockEntity();
					stockEntity.setWaybillNO(waybillNo);
					stockEntity.setSerialNO(serialNo.getSerialNo());
					stockEntity.setOrgCode(currentInfo.getCurrentDeptCode());
					stockEntity.setOperatorName(currentInfo.getEmpName());
					stockEntity.setOperatorCode(currentInfo.getEmpCode());
					stockEntity.setInOutStockType(StockConstants.UNLOAD_GOODS_MORE_IN_STOCK_TYPE);
					stockService.inStockPC(stockEntity);*/
				}
				//将该运单多货流水号map从多货运单map中删除
				moreWaybillMap.remove(entity.getWaybillNo());
			}
		}
		
		//此时多货map中剩下的都是卸车任务中没有的运单，遍历map，构造卸车任务运单明细和流水号明细
		if(moreWaybillMap != null && moreWaybillMap.size() != 0){
			Set entrySet = moreWaybillMap.entrySet();
			Iterator it = entrySet.iterator();
			while(it.hasNext()){
				Map.Entry entry = (Map.Entry)it.next();
				Map<String,ConfirmUnloadTaskBillsDto> serialNoMap = (Map<String,ConfirmUnloadTaskBillsDto>)entry.getValue();
				//获取运单号
				String waybillNo = (String)entry.getKey();
				//调用接送货接口，获取运单信息
				WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(waybillNo);
				if(waybill == null){
					throw new TfrBusinessException("运单数据获取失败，运单号：" + waybillNo);
				}
				//构造待保存的卸车任务运单明细实体
				UnloadWaybillDetailEntity waybillEntity = new UnloadWaybillDetailEntity();
				//ID
				waybillEntity.setId(UUIDUtils.getUUID());
				//UNLOAD_TASK_ID
				waybillEntity.setUnloadTaskId(baseEntity.getId());
				//单据编号
				waybillEntity.setBillNo(null);
				//运单号
				waybillEntity.setWaybillNo(waybillNo);
				//已操作件数
				waybillEntity.setOperationGoodsQty(Integer.valueOf(serialNoMap.size()));
				//重量
				waybillEntity.setWeight((waybill.getGoodsWeightTotal().divide(new BigDecimal(waybill.getGoodsQtyTotal()),2,BigDecimal.ROUND_UP)).multiply(new BigDecimal(serialNoMap.size())));
				//体积
				waybillEntity.setVolume((waybill.getGoodsVolumeTotal().divide(new BigDecimal(waybill.getGoodsQtyTotal()),2,BigDecimal.ROUND_UP)).multiply(new BigDecimal(serialNoMap.size())));
				//扫描件数
				waybillEntity.setScanGoodsQty(Integer.valueOf(0));
				//出发部门
				//到达部门
				//货名
				waybillEntity.setGoodsName(waybill.getGoodsName());
				//包装
				waybillEntity.setPack(waybill.getGoodsPackage());
				//运输性质
				waybillEntity.setTransportType(productService4Dubbo.getProductByCache(waybill.getProductCode(), null).getName());
				//任务建立时间
				waybillEntity.setTaskBeginTime(baseEntity.getUnloadStartTime());
				//任务修改时间
				waybillEntity.setModifyDate(baseEntity.getUnloadStartTime());
				//建立任务部门编码
				waybillEntity.setUnloadOrgCode(baseEntity.getUnloadOrgCode());
				waybillDetailList.add(waybillEntity);
				
				//遍历多货的流水号，构造待保存的流水号实体
				Set entrySet1 = serialNoMap.entrySet();
				Iterator it1 = entrySet1.iterator();
				while(it1.hasNext()){
					Map.Entry entry1 = (Map.Entry)it1.next();
					//获取流水号
					String serialNo = entry1.getKey().toString();
					//构造流水号实体
					UnloadSerialNoDetailEntity tempSerialNo = new UnloadSerialNoDetailEntity();
					tempSerialNo.setId(UUIDUtils.getUUID());
					//任务运单明细ID，null
					tempSerialNo.setUnloadWaybillDetailId(waybillEntity.getId());
					//扫描状态，空
					tempSerialNo.setScanStatus(null);
					//货物状态 多货
					//调用走货路径接口，判断是夹带多货还是异地多货
					FeedbackDto feedbackDto = calculateTransportPathService.getNextOrgAndTime(waybillEntity.getWaybillNo(), 
							serialNo, baseEntity.getUnloadOrgCode());
					if(feedbackDto.getPathDetailEntity() == null){//异地夹带多货
						tempSerialNo.setGoodsStatus(UnloadConstants.UNLOAD_GOODS_STATE_MORE_ALLOPATRY_ENTRAINED);
					}else{//夹带多货
						tempSerialNo.setGoodsStatus(UnloadConstants.UNLOAD_GOODS_STATE_MORE_ENTRAINED);
					}
					//操作时间
					tempSerialNo.setOptTime(baseEntity.getUnloadEndTime());
					//创建时间
					tempSerialNo.setCreateDate(baseEntity.getUnloadStartTime());
					//流水号
					tempSerialNo.setSerialNo(serialNo);
					//任务建立时间
					tempSerialNo.setTaskCreateTime(baseEntity.getUnloadStartTime());
					//建立任务部门编码
					tempSerialNo.setCreateOrgCode(baseEntity.getUnloadOrgCode());
					//将构造好的流水号置于serialNoDetailList中
					serialNoDetailList.add(tempSerialNo);
					
					//入库流水号
					//调用库存service，入库该件货物
					/*InOutStockEntity stockEntity = new InOutStockEntity();
					stockEntity.setWaybillNO(waybillNo);
					stockEntity.setSerialNO(tempSerialNo.getSerialNo());
					stockEntity.setOrgCode(currentInfo.getCurrentDeptCode());
					stockEntity.setOperatorName(currentInfo.getEmpName());
					stockEntity.setOperatorCode(currentInfo.getEmpCode());
					stockEntity.setInOutStockType(StockConstants.UNLOAD_GOODS_MORE_IN_STOCK_TYPE);
					stockService.inStockPC(stockEntity);*/
				}
			}
		}
		//保存卸车任务运单明细和流水号明细
		this.addUnloadTaskWaybillDetailList(waybillDetailList);
		this.addUnloadTaskSerialNoDetailList(serialNoDetailList);
		return FossConstants.SUCCESS;
	}

	/**
	 * 根据卸车任务ID更新卸车人员参与情况的离开任务时间
	 * @author 045923-foss-shiwei
	 * @date 2012-12-20 下午8:03:29
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskService#updateLoaderLeaveTaskTime(java.lang.String, java.util.Date)
	 */
	@Override
	public int updateLoaderLeaveTaskTime(String unloadTaskId, Date leaveTime) {
		unloadTaskDao.updateLoaderLeaveTaskTime(unloadTaskId, leaveTime);
		return FossConstants.SUCCESS;
	}

	/**
	 * 调用配载单接口，获取配载单下的交接单列表
	 * @author 045923-foss-shiwei
	 * @date 2012-12-21 下午3:49:45
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskService#queryHandOverBillListByVehicleAssembleNo(java.lang.String)
	 */
	@Override
	public List<QueryHandOverBillDto> queryHandOverBillListByVehicleAssembleNo(
			String vehicleAssembleNo) {
		return vehicleAssembleBillService.queryHandOverBillListByVNo(vehicleAssembleNo);
	}

	/**
	 * 手工确认卸车任务界面，添加多货时校验运单号、流水号是否合法
	 * @author 045923-foss-shiwei
	 * @date 2012-12-23 上午11:20:19
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskService#validateWaybillNoAndSerialNo(java.lang.String, java.lang.String)
	 */
	@Override
	public int validateWaybillNoAndSerialNo(String waybillNo, String serialNo) {
		return unloadTaskDao.validateWaybillNoAndSerialNo(waybillNo, serialNo);
	}

	/**
	 * 确认卸车任务界面，添加多货运单、流水号时，校验运单号、流水号是否存在于本次卸车任务中
	 * @author 045923-foss-shiwei
	 * @date 2012-12-23 下午3:39:52
	 */
	@Override
	public int validateWaybillNoAndSerialNoIsInUnloadTask(String unloadTaskId,
			String unloadType, String waybillNo, String serialNo) {
		//构造查询条件对象
		ConfirmUnloadTaskBillsDto dto = new ConfirmUnloadTaskBillsDto();
		dto.setUnloadTaskId(unloadTaskId);
		dto.setWaybillNo(waybillNo);
		dto.setSerialNo(serialNo);
		//如果是长途卸车
		if(StringUtils.equals(unloadType, UnloadConstants.UNLOAD_TASK_TYPE_LONG_DISTANCE)){
			return unloadTaskDao.validateNosIsInLongUnloadTask(dto);
		}
		//如果是短途卸车
		if(StringUtils.equals(unloadType, UnloadConstants.UNLOAD_TASK_TYPE_SHORT_DISTANCE)){
			return unloadTaskDao.validateNosIsInShortUnloadTask(dto);
		}
		//2015/9/8  272681  如果是商务专递卸车
		if(StringUtils.equals(unloadType, UnloadConstants.UNLOAD_TASK_TYPE_BUSINESS)){
			return unloadTaskDao.validateNosIsInBusinessUnloadTask(dto);
		}
		return 0;
	}

	/**
	 * 确认卸车任务（长途），快速定位功能，根据运单号获取运单号所在的配载单、交接单
	 * @author 045923-foss-shiwei
	 * @date 2012-12-23 下午5:14:44
	 */
	@Override
	public List<ConfirmUnloadTaskBillsDto> queryBillNosListByWaybillNo(
			String unloadTaskId, String waybillNo) {
		return unloadTaskDao.queryBillNosListByWaybillNo(unloadTaskId, waybillNo);
	}

	/**
	 * 卸车差异模块调用，看某长途卸车中多货的流水号在上一环节是否扫描
	 * @author 045923-foss-shiwei
	 * @date 2012-12-27 下午2:16:28
	 */
	@Override
	public List<String> queryLongDistanceLoadTaskIsScaned(
			ConfirmUnloadTaskBillsDto dto) {
		return unloadTaskDao.queryLongDistanceLoadTaskIsScaned(dto);
	}

	/**
	 * 卸车差异模块调用，看某短途卸车中多货的流水号在上一环节是否扫描
	 * @author 045923-foss-shiwei
	 * @date 2012-12-27 下午2:16:28
	 */
	@Override
	public List<String> queryShortDistanceLoadTaskIsScaned(
			ConfirmUnloadTaskBillsDto dto) {
		return unloadTaskDao.queryShortDistanceLoadTaskIsScaned(dto);
	}
	
	/**
	 * 获取当前部门的上级部门code
	 * @author 045923-foss-shiwei
	 * @date 2013-4-2 下午4:48:53
	 */
	private String querySuperiorOrgCode(){
		String orgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
		OrgAdministrativeInfoEntity superEntity = loadService.querySuperiorOrgByOrgCode(orgCode);
		if(superEntity == null || StringUtils.isBlank(superEntity.getCode())){
			LOGGER.error("###################根据部门（code：" + orgCode + "）获取上级营业部、派送部、总调、外场、结果为空！");
			throw new TfrBusinessException("获取本部门的上级组织失败(包括营业部、派送部、总调、外场)！");
		}else{
			return superEntity.getCode();
		}
	}

	/**
	 * 获取上级组织实体
	 * @author 045923-foss-shiwei
	 * @date 2013-4-3 下午2:51:16
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskService#querySuperOrgByOrgCode(java.lang.String)
	 */
	@Override
	public OrgAdministrativeInfoEntity querySuperOrgByOrgCode(String orgCode) {
		if(StringUtils.isBlank(orgCode)){
			orgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
		}
		OrgAdministrativeInfoEntity org = loadService.querySuperiorOrgByOrgCode(orgCode);
		if(org == null){
			throw new TfrBusinessException("获取当前部门的上级组织失败(包括营业部、派送部、外场、总调)！");
		}
		return loadService.querySuperiorOrgByOrgCode(orgCode);
	}

	/** 
	 * @author dp-duyi
	 * @date 2013-5-10 下午2:57:51
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskService#confirmPDAUnloadTask(java.lang.String, java.util.Date)
	 */
	@Override
	public void confirmPDAUnloadTask(String taskNo, Date endTime) {
		UnloadTaskEntity unloadTask = pdaUnloadTaskDao.queryUnloadTaskByTaskNo(taskNo);
		if(unloadTask != null){
			try{
				//插入理货员
				LoaderParticipationEntity loader = new LoaderParticipationEntity();
				loader.setJoinTime(new Date());
				loader.setLeaveTime(new Date());
				loader.setBeCreator(FossConstants.NO);
				loader.setId(UUIDUtils.getUUID());
				loader.setTaskType("UNLOAD");
				UserEntity user = FossUserContext.getCurrentUser();
				loader.setLoaderCode(user.getEmployee().getEmpCode());
				loader.setLoaderName(user.getEmployee().getEmpName());
				OrgAdministrativeInfoEntity currentDept = FossUserContext.getCurrentDept();
				loader.setLoadOrgCode(currentDept.getCode());
				loader.setLoadOrgName(currentDept.getName());
				loader.setTaskId(taskNo);
				loader.setFlag("1");
				List<LoaderParticipationEntity> loaders = new ArrayList<LoaderParticipationEntity>();
				loaders.add(loader);
				pdaLoadDao.insertTransferLoaderParticipation(loaders);
			}catch(Exception e){
				LOGGER.error("手工确认PDA卸车任务失败"+taskNo+" "+FossUserContext.getCurrentUser().getUserName(), e);
			}
			
			//增加任务车辆Action
			TruckActionDetailEntity actionDetail = new TruckActionDetailEntity();
			//JOB主键sequence
			actionDetail.setId(DepartureConstant.SEQ_TRUCK_ACTION_JOB);
			// 卸车(PDA)
			actionDetail.setBundType(DepartureConstant.JOB_TRUCK_UNLOAD_PDA);
			actionDetail.setCreateTime(new Date());
			actionDetail.setCreateDate(new Date());
			//	未处理
			actionDetail.setStatus(DepartureConstant.JOB_NOT_START);
			actionDetail.setTruckTaskDetailId(unloadTask.getId());
			departureDao.addTruckActionDetail(actionDetail);
			
			pdaUnloadService.finishUnloadTaskAndSoOn(taskNo, endTime, null, FossUserContext.getCurrentUser().getEmployee().getEmpCode(), FossConstants.NO, null);
		}
			}

	/**
	 * 根据卸车差异报告id，少货运单号、流水号获取运单所属单据
	 * @author 045923-foss-shiwei
	 * @date 2013-6-25 上午11:06:50
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadTaskDao#queryLackGoodsBillNoByWaybillNoAndSerialNo(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<UnloadWaybillDetailDto> queryLackGoodsBillNoByWaybillNoAndSerialNo(String reportId, String waybillNo, String serialNo) {
		return unloadTaskDao.queryLackGoodsBillNoByWaybillNoAndSerialNo(reportId, waybillNo, serialNo);
	}
	
	/**
	 * 
	 * <p>提供接口给接送货查询卸车任务，配合运单中止需求</p> 
	 * @author alfred
	 * @date 2014-5-8 下午3:13:19
	 * @param waybillNo
	 * @return
	 * @see
	 */
	@Override
	public boolean queryUnloadTaskReport(String waybillNo) {
		//查询运单实体
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
		Map<String,String> condition = new HashMap<String,String>();
		condition.put("waybillNo", waybillEntity.getWaybillNo());
		condition.put("orgCode", waybillEntity.getReceiveOrgCode());
		List<String> taskNoList = unloadTaskDao.queryUnloadTaskReport(condition);
		if(taskNoList.size()>0 && taskNoList !=null){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * @author nly
	 * @date 2015年4月22日 上午10:02:06
	 * @function 根据运单号查询所有的卸车运单明细
	 * @param waybillNo
	 * @return
	 */
	@Override
	public List<UnloadWaybillDetailEntity> queryUnloadWaybillDetailByNo(String waybillNo){
		return unloadTaskDao.queryUnloadWaybillDetailByNo(waybillNo);
	}
	/**
	 * @author zyr
	 * @date 2015-7-11上午10:12:26
	 * @function 到达部门是驻地营业部，获取有效的运单信息，到达部门为运单的提货网点所属外场，判断是否晚到，晚到则调用接送货接口
	 * @param waybillDetailList
	 * @return
	 */
	private void isArriveLate(List<UnloadWaybillDetailEntity> waybillDetailList,UnloadTaskEntity baseEntity) {
		//2016年12月27日09:12:27 zm add  添加晚到预计到达时间推后24小时
		long time=ConstantsNumberSonar.SONAR_NUMBER_24*ConstantsNumberSonar.SONAR_NUMBER_60*ConstantsNumberSonar.SONAR_NUMBER_60*ConstantsNumberSonar.SONAR_NUMBER_1000;
		for(UnloadWaybillDetailEntity waybillDetail : waybillDetailList) {
			//获取运单提货网点
			WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillDetail.getWaybillNo());
			//通过OrgCode部门编号获取部门实体，判定此部门性质是否为非驻地营业部
			OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillEntity.getCustomerPickupOrgCode());
			if(null != org) {
				//营业部
				if(StringUtils.equals(FossConstants.YES, org.getSalesDepartment())) {
					SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySimpleSaleDepartmentByCode(waybillEntity.getCustomerPickupOrgCode());
					//驻地营业部
					if(StringUtils.equals(FossConstants.YES, saleDepartmentEntity.getStation())) {
						if(StringUtil.equals(baseEntity.getUnloadOrgCode(), saleDepartmentEntity.getTransferCenter())) {
							//查询运单的预计到达时间
							WaybillPlanArriveTimeDto dto = arrivalDao.queryPlanArriveTime(waybillDetail.getWaybillNo());
							if(null != dto) {
								if(null != dto.getPreArriveTime()) {
									//Date realityArriverTime = Calendar.getInstance().getTime();
									if(baseEntity.getUnloadEndTime().getTime() > (dto.getPreArriveTime().getTime()+time)) {
										//调接送货接口
										compensateSpreadService.calculateSpread(waybillDetail.getWaybillNo());
									}
								}else {
									LOGGER.error("运单号" + waybillDetail.getWaybillNo() + "未查询到预计到达时间!");
								}
							}
						}
					}
				}
			}
		}
	}
	/**
	 * 
	* @Title: judgeTimeRange 
	* @Description: 判断时间是否在0800-1730类
	* 1.在08:00之前返回08：00
	* 2.在17:30之后返回第二天08:00
	* @param  timeRangeStr（0800-1730时间区间）
	* @param  departTime 需要判断时间
	* @param @return    设定文件 
	* @return Date    返回类型 
	* @author 189284-ZhangXu
	* @Date 2015-8-14  下午3:36:43
	* @throws
	 */
	@Override
	public Date judgeTimeRange(String timeRangeStr,Date departTime){
		String[] timeRangeArray = timeRangeStr.split("-");
		//获取配置的开始时间、结束时间
		String sTimeStr = timeRangeArray[0],
				eTimeStr = timeRangeArray[1];
		//将开始、结束时间转化为数字
		int sTime = Integer.parseInt(sTimeStr),
				eTime = Integer.parseInt(eTimeStr);
		//截取放行时间的时、分，转成HHmm形式，24小时制
		DateFormat df = new SimpleDateFormat("HHmm");
		String nTimeStr = df.format(departTime);
		//讲当前时间的小时、分钟转化为数字
		int nTime = Integer.parseInt(nTimeStr);
		//定义日历时间
		Calendar cal = Calendar.getInstance();
		//此处比较大小，如果当前时间在配置时间范围内，则返回departTime
		if(nTime >= sTime && nTime < eTime){
			cal.setTime(departTime);
		}
		if(nTime <sTime){
			cal.setTime(departTime);
			cal.set(Calendar.YEAR, Calendar.MONTH, Calendar.DATE, ConstantsNumberSonar.SONAR_NUMBER_8,0, 0);
		}
		if(nTime >eTime){
			cal.setTime(departTime);
			cal.set(Calendar.YEAR, Calendar.MONTH, Calendar.DATE+1, ConstantsNumberSonar.SONAR_NUMBER_8,0, 0);
		}
		return cal.getTime();
	}
	/**
	 * 
	* @Title: addJobTodo 
	* @Description:添加  （创建卸车任务时 发短信 ）代办Job
	* @param  unloadTaskId(卸车任务id)
	* @param  orgCode 操作部门Code
	*  BusinessId 任务Id
	*  BusinessScene任务场景
	*  BusinessGoal 业务目标
	* @return void    返回类型 
	* @author 189284-ZhangXu
	* @Date 2015-8-25  下午3:39:11
	* @throws
	 */
	@Override
	public void addJobTodo(String unloadTaskId,String user,String orgCode){
			/**
			 * 创建卸车任务发短信 限制的业务时间段0800-1730
			 */
			Date businessTime=judgeTimeRange(TransferConstants.BUSINESSTIME,new Date());
			LOGGER.info("创建卸车任务发短信 时间："+businessTime);
			/**
			 * 查询此任务编号的代办任务是否已经存在，如果存在 不在新添加
			 */
			int tfrJobCount=tfrJobTodoService.selectJobTodoMigrationByBusinessId(unloadTaskId, 
					BusinessGoalContants.BUSINESS_GOAL_UNLOADTASK_SMS);
			
			/*
			 * 新增 代办事务
			 */		

			if(tfrJobCount<1){
				tfrJobTodoService.addJobTodo(unloadTaskId,
						BusinessSceneConstants.BUSINESS_SCENE_UNLOADTASK_SMS, 
						new String[]{BusinessGoalContants.BUSINESS_GOAL_UNLOADTASK_SMS}, 
						businessTime, 
						user);
			}
			LOGGER.info("创建卸车任务发短信 时间："+businessTime+"tfrJobCount是否有此代办job:"+tfrJobCount+"unloadTaskNo:"+unloadTaskId);
	}
	/**
	 * 
	* @Title: deleteTfrJob 
	* @Description:删除卸车任务的时候  更新 代办Job取消发送短信
	* 试点期间全国只针对这8个到货大区开通权限，其余大区按原先形式不变，
	* 杭州大区，北京丰台大区，广州白云大区，深圳西部大区，台州大区，保定大区，泉州大区，新乡大区
    * 下的营业部
	* @param @param unloadTaskNo    设定文件 
	* @return void    返回类型 
	* @author 189284-ZhangXu
	* @Date 2015-8-25  下午3:52:29
	* @throws
	 */
	@Override
	public void deleteTfrJob(String unloadTaskId){
		/**
		 * String businessId,String businessGoal, String businessScene
		 */
		List<TfrJobTodoEntity> tfrJobTodoEntitys=tfrJobTodoService.selectJobTodoByBusinessId( unloadTaskId, 
				BusinessGoalContants.BUSINESS_GOAL_UNLOADTASK_SMS,BusinessSceneConstants.BUSINESS_SCENE_UNLOADTASK_SMS);
		/*
		 * 新增 代办事务
		 */		
		if(CollectionUtils.isNotEmpty(tfrJobTodoEntitys)){
			if(StringUtils.isNotEmpty(tfrJobTodoEntitys.get(0).getId())){
				/**
				 * step 3：更新待处理信息
				 */
				tfrJobTodoService.updateJobTodoByID(tfrJobTodoEntitys.get(0).getId());
				LOGGER.info("删除的卸车任务号unloadTaskId:"+unloadTaskId+"id:"+tfrJobTodoEntitys.get(0).getId());
			}
		}else{
			LOGGER.info("任务号"+unloadTaskId+"的卸车任务短信已经发送");
		}
		
	}
	
	/**
	 * 根据商务专递交接单号获取交接单运单列表
	 * @author 272681 chenlei
	 * @date 2015/9/17
	 */
	@Override
	public List<HandOverBillDetailEntity> queryWaybillListByAirHandOverBillNo(String handOverBillNo) {
		//返回查询结果
		return handOverBillService.queryBusAirHandOverBillDetailByNo(handOverBillNo);
	}
	
	/**
	 * 手工确认卸车任务界面，根据商务专递的交接单号、运单号获取流水号列表
	 * @author 272681 
	 * @date 2015/9/2
	 */
	@Override
	public List<HandOverBillSerialNoDetailEntity> querySerialNoListByAirHandOverBillNoAndWaybillNo(
			String handOverBillNo, String waybillNo) {
		return handOverBillService.getBusAirHandOverBillSerialNoDetailsByWayBillNo(waybillNo, handOverBillNo);
	}
	
	/**
	 * 用于确认卸车界面，快速查询时根据商务专递运单号获取交接单号list
	 * @author 272681 
	 * @date 2015/9/13
	 */
	@Override
	public List<String> queryAirHandOverBillListByWaybillNo(String unloadTaskId,
			String waybillNo) {
		//272681快速查询时根据商务专递的运单号获取交接单list
		return unloadTaskDao.queryAirHandOverBillListByWaybillNo(unloadTaskId, waybillNo);
	}
	
	/**
	 * 根据商务专递交接单号获取待卸的交接单信息list
	 * @author 272681
	 * @date 2015/9/15
	 */
	@Override
	public List<PlanUnloadBillDto> queryArrivedBusinessAirBillInfoByNo(
			List<String> businessAirBillNoList) {
		List<PlanUnloadBillDto> list = new ArrayList<PlanUnloadBillDto>();
		//获取当前登录部门code
		String orgCode = this.querySuperiorOrgCode();
		//构造查询条件
		QueryArrivedBillInfoByNoDto nosDto = new QueryArrivedBillInfoByNoDto();
		//交接单编号list
		nosDto.setNosList(businessAirBillNoList);
		//部门code
		nosDto.setOrgCode(orgCode);
		List<PlanUnloadBillDto> list2 = unloadTaskDao.queryArrivedBusinessAirBillInfoByNo(nosDto);
		
		OrgAdministrativeInfoEntity loginOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(FossUserContext.getCurrentDeptCode());
		if("Y".equals(loginOrg.getSalesDepartment())){
			for(int i=0;i<list2.size();i++){
				if(UnloadCommonUtils.isExpressHandOver(list2.get(i).getBillNo())){
					list2.remove(i);
					--i;
				}
			}
		}
		
		if(CollectionUtils.isNotEmpty(list2)){
			list.addAll(list2);
		}
		//判定营业部是否有对应快递分部，若有查询出该分部的到达车辆信息
		ExpressBranchSalesDeptEntity  branchSalesDeptEntity = new ExpressBranchSalesDeptEntity();
		branchSalesDeptEntity.setSalesDeptCode(orgCode);
		ExpressBranchSalesDeptEntity branchSalesDept= expressBranchSalesDeptService.
				queryByExpressBranchSalesDeptCode(branchSalesDeptEntity);
		if(branchSalesDept !=null){
			nosDto.setOrgCode(branchSalesDept.getExpressBranchCode());
			List<PlanUnloadBillDto> list1 =unloadTaskDao.queryArrivedBusinessAirBillInfoByNo(nosDto);
			if(CollectionUtils.isNotEmpty(list1)){
				list.addAll(list1);
			}
		}
		//返回查询结果
		return list;
	}
	
	/**
	 * 根据商务专递卸车任务ID获取其下单据列表
	 * @author 272681
	 * @date 2015/9/16
	 */
	@Override
	public List<UnloadBillDetailEntity> queryAirUnloadTaskBillDetailListById(String unloadTaskId) {
		//返回查询结果
		return unloadTaskDao.queryAirUnloadTaskBillDetailListById(unloadTaskId);
	}
	
	/**
	 * 新增卸车、确认卸车时，更新商务专递到达单据状态
	 * @author 272681
	 * @date 2015/8/24 
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskService#updateArriveBusinessAirBillState(java.util.List, java.lang.String)
	 */
	@Transactional
	@Override
	public int updateArriveBusinessAirBillState(List<UnloadBillDetailDto> bills,String state,String unloadState){
		//商务专递272681 
		List<ArriveBillDto> arriveBusinessAirBills = new ArrayList<ArriveBillDto>();
		ArriveBillDto arriveBill;
		for(UnloadBillDetailDto unloadBill : bills){
			//到达单据
			arriveBill = new ArriveBillDto();
			arriveBill.setBillNo(unloadBill.getBillNo());
			arriveBill.setAssignState(state);
			arriveBill.setUnloadState(unloadState);
			if(UnloadConstants.BILL_TYPE_AIR_HANDOVERTYPE.equals(unloadBill.getBillType())){
				//272681 商务专递的单据类型
				arriveBusinessAirBills.add(arriveBill);
			}
		}
		//272681 商务专递 更新单据状态
		if(CollectionUtils.isNotEmpty(arriveBusinessAirBills)){
			assignUnloadTaskDao.updateArriveBusinessAirBillState(arriveBusinessAirBills);
		}
		
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 商务专递卸车完成后，确认保存数据，入库多货的流水号和未少货的流水号
	 * @author 272681
	 * @date 2015/8/28
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private int saveDataWhenConfirmBusinessAirUnloadTask(List<UnloadWaybillDetailEntity> waybillDetailList,
			List<UnloadSerialNoDetailEntity> serialNoDetailList,
			List<UnloadWaybillDetailEntity> oldWaybillList,
			Map<String,Map<String,ConfirmUnloadTaskBillsDto>> lackWaybillMap,
			Map<String,Map<String,ConfirmUnloadTaskBillsDto>> moreWaybillMap,
			UnloadTaskEntity baseEntity){
		for(UnloadWaybillDetailEntity entity : oldWaybillList){
			//获取交接单号
			String handOverBillNo = entity.getHandOverBillNo();
			//获取运单号
			String waybillNo = entity.getWaybillNo();
			//运单明细卸车任务ID
			entity.setUnloadTaskId(baseEntity.getId());
			//获取运输性质  272681
			String tran = entity.getTransportType();
			//将商专专递的运输性质改为商务专递DEAP 272681
			if(tran != null && entity.getTransportType().equals(UnloadConstants.UNLOAD_TASK_TYPE_TRANSPORT)){
			  entity.setTransportType(UnloadConstants.TRANSPORT_TYPE);
			}
			//构造少货map的id
			String lackMapId = handOverBillNo + TAG + waybillNo;
			//获取所有该交接单、该运单下的少货件数
			int lackPieces = 0;
			if(lackWaybillMap.get(lackMapId) != null){
				lackPieces = lackWaybillMap.get(lackMapId).size();
			}
			//获取该运单的多货件数
			Map<String,ConfirmUnloadTaskBillsDto> moreSerialNoMap = moreWaybillMap.get(entity.getWaybillNo());
			int morePieces = 0;
			if(moreSerialNoMap != null){
				morePieces = moreSerialNoMap.size();
			}
			//计算该运单的总件数
			int optPieces = entity.getHandOverPieces() - lackPieces + morePieces;
			//操作件数
			entity.setOperationGoodsQty(Integer.valueOf(optPieces));
			//卸车重量
			BigDecimal weight = entity.getWeight().multiply(new BigDecimal(optPieces)).divide(new BigDecimal(entity.getHandOverPieces()),2,BigDecimal.ROUND_UP);
			entity.setWeight(weight);
			//卸车体积
			BigDecimal volumn = entity.getVolume().multiply(new BigDecimal(optPieces)).divide(new BigDecimal(entity.getHandOverPieces()),2,BigDecimal.ROUND_UP);
			entity.setVolume(volumn);
			//ID
			entity.setId(UUIDUtils.getUUID());
			//扫描件数：0
			entity.setScanGoodsQty(Integer.valueOf(0));
			//任务建立时间
			entity.setTaskBeginTime(baseEntity.getUnloadStartTime());
			//任务修改时间
			entity.setModifyDate(baseEntity.getUnloadStartTime());
			//任务建立部门
			entity.setUnloadOrgCode(baseEntity.getUnloadOrgCode());
			//将该运单置于waybillDetailList中
			waybillDetailList.add(entity);
			
			//2015/9/1 272681 获取商务专递交接单、该运单下的流水号，如果没有少货，则为正常 
			List<HandOverBillSerialNoDetailEntity> oldSerialNoList = handOverBillService.getBusAirHandOverBillSerialNoDetailsByWayBillNo(waybillNo, handOverBillNo);

			//遍历交接单中运单下的流水号
			for(HandOverBillSerialNoDetailEntity oldSerialNo : oldSerialNoList){
				//构造待插入流水号，置于serialNoDetailList中
				//构造待插入的流水号明细对象
				UnloadSerialNoDetailEntity tempSerialNo = new UnloadSerialNoDetailEntity();
				tempSerialNo.setId(UUIDUtils.getUUID());
				tempSerialNo.setUnloadWaybillDetailId(entity.getId());
				//扫描状态，空
				tempSerialNo.setScanStatus(null);
				
				//2015/9/6 272681  货物状态 如果在少货map中发现该流水号，货物状态都是正常，但是在设备编号中说明少货
				if(lackWaybillMap.get(lackMapId) != null){
					Map<String,ConfirmUnloadTaskBillsDto> tempMap = lackWaybillMap.get(lackMapId);
					if(tempMap.get(oldSerialNo.getSerialNo()) != null){
						//2015/8/28 272681 商务专递不生成差异报告，货物状态都为正常
						tempSerialNo.setGoodsStatus(UnloadConstants.UNLOAD_GOODS_STATE_NORMAL);//正常
						//2015/9/6 272681  少货时将设备编号设置为少货
						tempSerialNo.setDeviceNo(UnloadConstants.UNLOAD_GOODS_STATE_LACK);//少货
						
					}else{
						tempSerialNo.setGoodsStatus(UnloadConstants.UNLOAD_GOODS_STATE_NORMAL);// 正常
						tempSerialNo.setDeviceNo(UnloadConstants.UNLOAD_GOODS_STATE_NORMAL);
					}
				}else{
					tempSerialNo.setGoodsStatus(UnloadConstants.UNLOAD_GOODS_STATE_NORMAL);//正常
					tempSerialNo.setDeviceNo(UnloadConstants.UNLOAD_GOODS_STATE_NORMAL);
				}
				//操作时间
				tempSerialNo.setOptTime(baseEntity.getUnloadEndTime());
				//创建时间
				tempSerialNo.setCreateDate(baseEntity.getUnloadStartTime());
				//流水号
				tempSerialNo.setSerialNo(oldSerialNo.getSerialNo());
				//任务建立时间
				tempSerialNo.setTaskCreateTime(baseEntity.getUnloadStartTime());
				//建立任务部门编码
				tempSerialNo.setCreateOrgCode(baseEntity.getUnloadOrgCode());
				//将构造好的流水号置于serialNoDetailList中
				serialNoDetailList.add(tempSerialNo);
			}
			
			//将该运单的多货流水号置于serialNoDetailList中，然后将该运单的流水号map从多货运单中删除
			if(moreSerialNoMap != null){
				Set entrySet = moreSerialNoMap.entrySet();
				Iterator it = entrySet.iterator();
				while(it.hasNext()){
					Map.Entry entry = (Map.Entry)it.next();
					ConfirmUnloadTaskBillsDto serialNo = (ConfirmUnloadTaskBillsDto)entry.getValue();
					//构造待插入的流水号明细对象
					UnloadSerialNoDetailEntity moreSerialNo = new UnloadSerialNoDetailEntity();
					moreSerialNo.setId(UUIDUtils.getUUID());
					moreSerialNo.setUnloadWaybillDetailId(entity.getId());
					//扫描状态，空
					moreSerialNo.setScanStatus(null);
					
					//货物状态 多货
					//调用走货路径接口，判断是夹带多货还是异地多货
					//if-else中内容相同-352203
					/*FeedbackDto feedbackDto = */calculateTransportPathService.getNextOrgAndTime(serialNo.getWaybillNo(), 
							serialNo.getSerialNo(), baseEntity.getUnloadOrgCode());
					/*if(feedbackDto.getPathDetailEntity() == null){//异地夹带多货
						moreSerialNo.setGoodsStatus(UnloadConstants.UNLOAD_GOODS_STATE_NORMAL);
						moreSerialNo.setDeviceNo(UnloadConstants.UNLOAD_GOODS_STATE_MORE);//多货
					}else{//夹带多货
						moreSerialNo.setGoodsStatus(UnloadConstants.UNLOAD_GOODS_STATE_NORMAL);
						moreSerialNo.setDeviceNo(UnloadConstants.UNLOAD_GOODS_STATE_MORE);//多货
					}*/
					moreSerialNo.setGoodsStatus(UnloadConstants.UNLOAD_GOODS_STATE_NORMAL);
					moreSerialNo.setDeviceNo(UnloadConstants.UNLOAD_GOODS_STATE_MORE);//多货
					
					//操作时间
					moreSerialNo.setOptTime(baseEntity.getUnloadEndTime());
					//创建时间
					moreSerialNo.setCreateDate(baseEntity.getUnloadStartTime());
					//流水号
					moreSerialNo.setSerialNo(serialNo.getSerialNo());
					//任务建立时间
					moreSerialNo.setTaskCreateTime(baseEntity.getUnloadStartTime());
					//建立任务部门编码
					moreSerialNo.setCreateOrgCode(baseEntity.getUnloadOrgCode());
					//将构造好的流水号置于serialNoDetailList中
					serialNoDetailList.add(moreSerialNo);
	
				}
				//将该运单多货流水号map从多货运单map中删除
				moreWaybillMap.remove(entity.getWaybillNo());
			}
		}
		
		//此时多货map中剩下的都是卸车任务中没有的运单，遍历map，构造卸车任务运单明细和流水号明细
		if(moreWaybillMap != null && moreWaybillMap.size() != 0){
			Set entrySet = moreWaybillMap.entrySet();
			Iterator it = entrySet.iterator();
			while(it.hasNext()){
				Map.Entry entry = (Map.Entry)it.next();
				Map<String,ConfirmUnloadTaskBillsDto> serialNoMap = (Map<String,ConfirmUnloadTaskBillsDto>)entry.getValue();
				//获取运单号
				String waybillNo = (String)entry.getKey();
				//调用接送货接口，获取运单信息
				WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(waybillNo);
				if(waybill == null){
					throw new TfrBusinessException("运单数据获取失败，运单号：" + waybillNo);
				}
				//构造待保存的卸车任务运单明细实体
				UnloadWaybillDetailEntity waybillEntity = new UnloadWaybillDetailEntity();
				//ID
				waybillEntity.setId(UUIDUtils.getUUID());
				//UNLOAD_TASK_ID
				waybillEntity.setUnloadTaskId(baseEntity.getId());
				//单据编号
				waybillEntity.setBillNo(null);
				//运单号
				waybillEntity.setWaybillNo(waybillNo);
				//已操作件数
				waybillEntity.setOperationGoodsQty(Integer.valueOf(serialNoMap.size()));
				//重量
				waybillEntity.setWeight((waybill.getGoodsWeightTotal().divide(new BigDecimal(waybill.getGoodsQtyTotal()),2,BigDecimal.ROUND_UP)).multiply(new BigDecimal(serialNoMap.size())));
				//体积
				waybillEntity.setVolume((waybill.getGoodsVolumeTotal().divide(new BigDecimal(waybill.getGoodsQtyTotal()),2,BigDecimal.ROUND_UP)).multiply(new BigDecimal(serialNoMap.size())));
				//扫描件数
				waybillEntity.setScanGoodsQty(Integer.valueOf(0));
				//出发部门
				//到达部门
				//货名
				waybillEntity.setGoodsName(waybill.getGoodsName());
				//包装
				waybillEntity.setPack(waybill.getGoodsPackage());
				//运输性质
				waybillEntity.setTransportType(productService4Dubbo.getProductByCache(waybill.getProductCode(), null).getName());
				//任务建立时间
				waybillEntity.setTaskBeginTime(baseEntity.getUnloadStartTime());
				//任务修改时间
				waybillEntity.setModifyDate(baseEntity.getUnloadStartTime());
				//建立任务部门编码
				waybillEntity.setUnloadOrgCode(baseEntity.getUnloadOrgCode());
				waybillDetailList.add(waybillEntity);
				
				//遍历多货的流水号，构造待保存的流水号实体
				Set entrySet1 = serialNoMap.entrySet();
				Iterator it1 = entrySet1.iterator();
				while(it1.hasNext()){
					Map.Entry entry1 = (Map.Entry)it1.next();
					//获取流水号
					String serialNo = entry1.getKey().toString();
					//构造流水号实体
					UnloadSerialNoDetailEntity tempSerialNo = new UnloadSerialNoDetailEntity();
					tempSerialNo.setId(UUIDUtils.getUUID());
					//任务运单明细ID，null
					tempSerialNo.setUnloadWaybillDetailId(waybillEntity.getId());
					//扫描状态，空
					tempSerialNo.setScanStatus(null);
					//货物状态 多货
					//调用走货路径接口，判断是夹带多货还是异地多货
					//if-else中内容相同-352203
					/*FeedbackDto feedbackDto = */calculateTransportPathService.getNextOrgAndTime(waybillEntity.getWaybillNo(), 
							serialNo, baseEntity.getUnloadOrgCode());
					/*if(feedbackDto.getPathDetailEntity() == null){//异地夹带多货
						tempSerialNo.setGoodsStatus(UnloadConstants.UNLOAD_GOODS_STATE_NORMAL);
						tempSerialNo.setDeviceNo(UnloadConstants.UNLOAD_GOODS_STATE_MORE);//多货
					}else{//夹带多货
						tempSerialNo.setGoodsStatus(UnloadConstants.UNLOAD_GOODS_STATE_NORMAL);
						tempSerialNo.setDeviceNo(UnloadConstants.UNLOAD_GOODS_STATE_MORE);//多货
					}*/
					tempSerialNo.setGoodsStatus(UnloadConstants.UNLOAD_GOODS_STATE_NORMAL);
					tempSerialNo.setDeviceNo(UnloadConstants.UNLOAD_GOODS_STATE_MORE);//多货
					//操作时间
					tempSerialNo.setOptTime(baseEntity.getUnloadEndTime());
					//创建时间
					tempSerialNo.setCreateDate(baseEntity.getUnloadStartTime());
					//流水号
					tempSerialNo.setSerialNo(serialNo);
					//任务建立时间
					tempSerialNo.setTaskCreateTime(baseEntity.getUnloadStartTime());
					//建立任务部门编码
					tempSerialNo.setCreateOrgCode(baseEntity.getUnloadOrgCode());
					//将构造好的流水号置于serialNoDetailList中
					serialNoDetailList.add(tempSerialNo);
					
				}
			}
		}
		//保存卸车任务运单明细和流水号明细
		this.addUnloadTaskWaybillDetailList(waybillDetailList);
		this.addUnloadTaskSerialNoDetailList(serialNoDetailList);
		return FossConstants.SUCCESS;
}
	
	/**
	 * @description 添加快递多货时，校验输入的运单号、流水号是否合法
	 * @param map
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @throws Exception 
	 * @update 2016年5月17日 下午5:18:01
	 */
	public FossToWKResponseDto validateExpressWaybillNoAndSerialNo(Map<String,String> map) throws Exception{
		
		
		String requestJsonStr=JSONObject.fromObject(map).toString();
		LOGGER.error("添加快递多货时，校验输入的运单号、流水号是否合法参数"+requestJsonStr);
		//调用同步数据接口
		FossToWKResponseEntity fossToWKResponseEntity=fossToWkService.validateExpressWaybillNoAndSerialNo(requestJsonStr);
		if(fossToWKResponseEntity==null){
			LOGGER.error("Foss查询悟空快递快递单据明细失败!");
			throw new TfrBusinessException("Foss查询悟空快递快递单据明细失败!");
		}

		FossToWKResponseDto fossToWKResponseDto=new FossToWKResponseDto(); 
		//设置 状态
		fossToWKResponseDto.setStatus(fossToWKResponseEntity.getStatus());
		//设置 错误信息
		fossToWKResponseDto.setExMsg(fossToWKResponseEntity.getExMsg());
		//设置 返回数据
		fossToWKResponseDto.setData(fossToWKResponseEntity.getData());
		
		return fossToWKResponseDto;
	}

	/**
	 * @description 根据卸车任务编号查询单据号列表
	 * @param unloadTaskId
	 * @return
	 * @throws Exception
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月17日 下午7:56:04
	 */
	@Override
	public List<String> queryBillNoListByUnloadTaskId(String unloadTaskId) {
	   List<String> list=null;
		if(StringUtils.isNotEmpty(unloadTaskId)){
	    	list=unloadTaskDao.queryBillNosByUnloadTaskId(unloadTaskId);
	    }
		return list;
	}

	/**
	 * @description 短途查询交接单号list
	 * @param paramMap
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年6月8日 上午8:28:23
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryExpressHandOverBillListByWaybillNo(Map<String, String> paramMap) {

		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(FossUserContext.getCurrentDeptCode());
		//营业部
		if(StringUtils.equals(FossConstants.YES, org.getSalesDepartment())) {
			return new ArrayList<String>();
	     }
		
		// 获取objectMapper
		ObjectMapper objectMapper = JSONUtils.obtainObjectMapper();
			
		String requestJsonStr=null;
		try {
			requestJsonStr = objectMapper.writeValueAsString(paramMap);
		}catch (IOException e) {
			e.printStackTrace();
			LOGGER.error("Foss通过运单号到悟空系统查询交接单号失败:"+e.getMessage());
		}
		LOGGER.error("Foss通过运单号到悟空系统查询交接单号参数"+requestJsonStr);
		//调用同步数据接口
		FossToWKResponseEntity fossToWKResponseEntity=fossToWkService.queryExpressHandOverBillListByWaybillNo(requestJsonStr);
		if(fossToWKResponseEntity==null){
			LOGGER.error("Foss通过运单号到悟空系统查询交接单号失败!");
			throw new TfrBusinessException("Foss通过运单号到悟空系统查询交接单号失败!");
		}
		List<String> handOverBillList=new ArrayList<String>();
		if("1".equals(fossToWKResponseEntity.getStatus())){
			LOGGER.error("Foss通过运单号到悟空系统查询交接单号成功!");
			List<Object> list=(List<Object>) fossToWKResponseEntity.getData();
			JSONObject jsonObj=null;
			for(Object obj:list){
				jsonObj=JSONObject.fromObject(obj);
				handOverBillList.add(jsonObj.getString("handoverNo"));
			}
			return handOverBillList;
		}else{
			LOGGER.error("Foss通过运单号到悟空系统查询交接单号失败，错误信息："+fossToWKResponseEntity.getExMsg());
			return handOverBillList;
		}
		
	}

	
	
	/**
	 * @description 长途查询交接单号list
	 * @param paramMap
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年6月8日 上午8:28:23
	 */
	@SuppressWarnings("unchecked")
	@Override
	public  List<ConfirmUnloadTaskBillsDto> queryExpressBillNosListByWaybillNo(Map<String, String> paramMap) {
	
		// 获取objectMapper
		ObjectMapper objectMapper = JSONUtils.obtainObjectMapper();
			
		String requestJsonStr=null;
		try {
			requestJsonStr = objectMapper.writeValueAsString(paramMap);
		}catch (IOException e) {
			e.printStackTrace();
			LOGGER.error("Foss通过运单号到悟空系统查询交接单号失败:"+e.getMessage());
		}
		LOGGER.error("Foss通过运单号到悟空系统查询交接单号参数"+requestJsonStr);
		//调用同步数据接口
		FossToWKResponseEntity fossToWKResponseEntity=fossToWkService.queryExpressHandOverBillListByWaybillNo(requestJsonStr);
		if(fossToWKResponseEntity==null){
			LOGGER.error("Foss通过运单号到悟空系统查询交接单号失败!");
			throw new TfrBusinessException("Foss通过运单号到悟空系统查询交接单号失败!");
		}
		List<ConfirmUnloadTaskBillsDto> handOverBillList=new ArrayList<ConfirmUnloadTaskBillsDto>();
		if("1".equals(fossToWKResponseEntity.getStatus())){
			LOGGER.error("Foss通过运单号到悟空系统查询交接单号成功!");
			List<Object> list=(List<Object>) fossToWKResponseEntity.getData();
			ConfirmUnloadTaskBillsDto confirmUnloadTaskBillsDto=null;
			JSONObject jsonObj=null;
			for(Object obj:list){
				confirmUnloadTaskBillsDto=new ConfirmUnloadTaskBillsDto();
				jsonObj=JSONObject.fromObject(obj);
				confirmUnloadTaskBillsDto.setVehicleAssembleNo(jsonObj.getString("handoverNo"));
				confirmUnloadTaskBillsDto.setHandOverBillNo(jsonObj.getString("cargoNo"));
				handOverBillList.add(confirmUnloadTaskBillsDto);
			}
			return handOverBillList;
		}else{
			LOGGER.error("Foss通过运单号到悟空系统查询交接单号失败，错误信息："+fossToWKResponseEntity.getExMsg());
			return handOverBillList;
		}
		
	}
	/**
	 * 新建卸车任务，若为营业部交接卸车，改交接单状态为已到达
	 * @author 360903
	 * @date 2016年12月13日 10:18:50
	 */
	private void updateHandOverBillStatus(UnloadTaskAddnewDto addnewDto) {
		if(addnewDto !=null && CollectionUtils.isNotEmpty(addnewDto.getBillList())){
			if(StringUtils.equals(addnewDto.getVehicleNo(),LoadConstants.VEHICLE_NO_SALE)){
				List<UnloadBillDetailEntity> list  = addnewDto.getBillList();
				for(UnloadBillDetailEntity ue:list){
					handOverBillService.updateHandOverBillStateByNo(ue.getBillNo(),LoadConstants.HANDOVERBILL_STATE_ALREADY_ARRIVE);
					departureDao.updateTaskByByHandOverBillSale(ue.getBillNo());
				}
			}
		}
	}
}