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
 *  PROJECT NAME  : tfr-arrival
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/arrival/server/service/impl/ArrivalService.java
 *  
 *  FILE NAME          :ArrivalService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.arrival.server.service.impl;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IDepartureStandardService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressBranchSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressLineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.ILoadAndUnloadEfficiencyVehicleComplexService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DepartureStandardEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressBranchSalesDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressLineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.complex.LoadAndUnloadEfficiencyVehicleDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DepartureStandardDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LeasedVehicleTypeException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LoadAndUnloadEfficiencyVehicleException;
import com.deppon.foss.module.base.common.api.server.service.IBusinessMonitorService;
import com.deppon.foss.module.base.common.api.shared.define.BusinessMonitorIndicator;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.waybill.api.server.service.ICompensateSpreadService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ITodoActionService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWeixinSendService;
import com.deppon.foss.module.pickup.waybill.shared.define.WeixinConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.TodoActionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WeixinSendDto;
import com.deppon.foss.module.settlement.common.api.server.service.ITruckArriveConfirmService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.dto.TruckArriveConfirmDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.trackings.api.server.service.IPushForWaybillToPTPService;
import com.deppon.foss.module.transfer.arrival.api.server.dao.IArrivalDao;
import com.deppon.foss.module.transfer.arrival.api.server.service.IArrivalService;
import com.deppon.foss.module.transfer.arrival.api.shared.define.ArrivalConstant;
import com.deppon.foss.module.transfer.arrival.api.shared.define.ErrorConstant;
import com.deppon.foss.module.transfer.arrival.api.shared.domain.ArrivalEntity;
import com.deppon.foss.module.transfer.arrival.api.shared.domain.CUBCChargingAssmebles;
import com.deppon.foss.module.transfer.arrival.api.shared.domain.FossTransterEntity;
import com.deppon.foss.module.transfer.arrival.api.shared.domain.QueryArrivalEntity;
import com.deppon.foss.module.transfer.arrival.api.shared.domain.TodoWhenArriveTruckEntity;
import com.deppon.foss.module.transfer.arrival.api.shared.domain.TruckArrivalEntity;
import com.deppon.foss.module.transfer.arrival.api.shared.dto.LeasedTruckDTO;
import com.deppon.foss.module.transfer.arrival.api.shared.dto.OperationDTO;
import com.deppon.foss.module.transfer.arrival.api.shared.dto.PlatformDTO;
import com.deppon.foss.module.transfer.arrival.api.shared.dto.QueryPlatformDTO;
import com.deppon.foss.module.transfer.arrival.api.shared.dto.TruckActionDetailDto;
import com.deppon.foss.module.transfer.arrival.api.shared.dto.VehicleInfoDTO;
import com.deppon.foss.module.transfer.arrival.api.shared.dto.WaybillPlanArriveTimeDto;
import com.deppon.foss.module.transfer.arrival.server.handle.ArrivalHandle;
import com.deppon.foss.module.transfer.common.api.cubcgray.model.VestResponse;
import com.deppon.foss.module.transfer.common.api.server.service.IFossToCubcService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrJobTodoService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrNotifyService;
import com.deppon.foss.module.transfer.common.api.server.service.IWkBillAddTfrNotifyService;
import com.deppon.foss.module.transfer.common.api.shared.define.BusinessGoalContants;
import com.deppon.foss.module.transfer.common.api.shared.define.BusinessSceneConstants;
import com.deppon.foss.module.transfer.common.api.shared.define.CUBCGrayContants;
import com.deppon.foss.module.transfer.common.api.shared.define.NotifyWkConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrNotifyEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcTruckConfirmArrivalResponse;
import com.deppon.foss.module.transfer.common.api.shared.dto.GrayParameterDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.WkHandOverBillEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.common.server.utils.CUBCGrayUtil;
import com.deppon.foss.module.transfer.departure.api.handle.DepartureHandle;
import com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao;
import com.deppon.foss.module.transfer.departure.api.server.dao.ISharedDao;
import com.deppon.foss.module.transfer.departure.api.server.dao.ITrackingDao;
import com.deppon.foss.module.transfer.departure.api.server.dao.IVehicleInfoDao;
import com.deppon.foss.module.transfer.departure.api.server.service.IDepartureService;
import com.deppon.foss.module.transfer.departure.api.shared.define.DepartureConstant;
import com.deppon.foss.module.transfer.departure.api.shared.domain.BusinessInfoEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.QueryDepartEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.TruckActionDetailEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.TruckDepartEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.TruckTaskDetailEntity;
import com.deppon.foss.module.transfer.departure.api.shared.dto.HandoverRefershDTO;
import com.deppon.foss.module.transfer.departure.api.shared.dto.VehicleDetailDTO;
import com.deppon.foss.module.transfer.departure.api.shared.dto.WayBillRefershDTO;
import com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService;
import com.deppon.foss.module.transfer.load.api.server.service.IOutsideVehicleChargeService;
import com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService;
import com.deppon.foss.module.transfer.load.api.server.service.IVehicleSealService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.define.SealConstant;
import com.deppon.foss.module.transfer.load.api.shared.define.TaskTruckConstant;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.SealEntity;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateOptimalPlatformService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IPlatformDispatchService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanDetailService;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.OptimalPlatformDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.CalculateOptimalPlatformException;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.exception.StockException;
import com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao;
import com.deppon.foss.module.transfer.unload.api.server.service.IPdaUnloadOptimizeService;
import com.deppon.foss.module.transfer.unload.api.shared.define.ChangeLabelGoodsConstants;
import com.deppon.foss.module.transfer.unload.api.shared.domain.ChangeLabelGoodsEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.VehicleSyncPdaEntity;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 查询到达数据分页 车辆出发时间获取规则： 车辆出发分属营业部及外场。 
 * 车辆出发时间获取方式：分为出发时间（GPS获取
 * ）、出发时间（PDA获取）、出发时间（PDA获取）； 
 * 营业部：为GPS获取、交接获取（发车确认）车辆出发时间两种；
 * 外场：为GPS获取、PDA获取、交接获取（手工）三种； 
 * 系统保留任一获取方式的车辆出发时间记录 ，但在到达界面中只显示唯一一个
 * ，显示默认优先级为GPS获取、PDA获取、交接获取。 点击确认分配按钮， 
 * 系统保留预分配月台记录。 对应月台的可用时间段减去已经“已预分配的”。
 * 月台分配记录为已分配。 未做月台预分配的记录为未分配。 
 * 车辆出发部门为本部门，才能做发车确认。 车辆到达部门为本部门，才能做到达确认。
 * 车辆出发或到达部门为本部门，才能执行证件包上交管理。 车辆到达部门为本部门，才能进行外请车尾款支付确认。 
 * 系统默认显示发车计划中到达部门为本部门的所有车辆
 * 。车辆中配载单到达部门为本部门的才会在界面显示尾款未结清金额。 只有长途外请车才可以结算。
 *  当前登录部门为外场运作部门时，
 * “查询车辆到达情况”界面，点击发车确认的车辆，产生一条待放行信息， 
 * 车辆状态记录为待放行，出发放行类型记录为“任务车辆”。 当车辆到达部门为营业部时
 * ，在车辆出发时，GPS将车辆预计到达时间推送给到达部门 。
 * 分配月台按钮， 可以在“查询车辆到达情况”结果列表里选择一条记录，
 * 将车牌带进来，不需要在预分配月台界面重新查询一次。.
 * 
 * @author foss-liubinbin(for IBM)
 * @date 2012-12-25 上午11:35:55
 * @see com.deppon.foss.module.transfer.arrival.api.server.dao.IArrivalDao#queryArrivalGrid(com.deppon.foss.module.transfer.arrival.api.shared.domain.QueryArrivalEntity,
 *      int, int)
 */
public class ArrivalService implements IArrivalService {
	/** ****日志******. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ArrivalService.class);
	
	/**
	 * 部门service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	/**
	 * 接送货微信service
	 */
	private IWeixinSendService weixinSendService;
	
	/**
	 * 运单service
	 */
	private IWaybillManagerService waybillManagerService;
	/**
	 * 
	 * stl
	 * **/
	private ITruckArriveConfirmService truckArriveConfirmService;
	
	/**
	 * 插入待办job表
	 */
	private ITfrJobTodoService tfrJobTodoService;
	
	/**
	 * 组织信息 Service
	 */
	
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * 营业部 Service 
	 */
	
	private ISaleDepartmentService saleDepartmentService;
	
	private CUBCGrayUtil cubcUtil;
	
	public void setCubcUtil(CUBCGrayUtil cubcUtil) {
		this.cubcUtil = cubcUtil;
	}

	/**
	 * 运单Dao
	 */
	private ITrackingDao trackingDao;

	public void setTrackingDao(ITrackingDao trackingDao) {
		this.trackingDao = trackingDao;
	}

	/**
	 * 发车计划service 200968 2016-01-11
	 */
	private ITruckDepartPlanDetailService truckDepartPlanDetailService;
	
	private IPdaUnloadOptimizeService pdaUnloadOptimizeService;
	
	public void setPdaUnloadOptimizeService(IPdaUnloadOptimizeService pdaUnloadOptimizeService) {
		this.pdaUnloadOptimizeService = pdaUnloadOptimizeService;
	}

	public void setTruckDepartPlanDetailService(
			ITruckDepartPlanDetailService truckDepartPlanDetailService) {
		this.truckDepartPlanDetailService = truckDepartPlanDetailService;
	}
	
	/**
	 * 同步给快递车辆任务 通知表
	 */
	private ITfrNotifyService tfrNotifyService;
	
	public void setTfrNotifyService(ITfrNotifyService tfrNotifyService) {
		this.tfrNotifyService = tfrNotifyService;
	}
	/**
	 * 晚到补差价接口
	 */
	
	
	private ICompensateSpreadService compensateSpreadService;
	
	public void setCompensateSpreadService(
			ICompensateSpreadService compensateSpreadService) {
		this.compensateSpreadService = compensateSpreadService;
	}

	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**  
	 * tfrJobTodoService.  
	 *  
	 * @param   tfrJobTodoService    the tfrJobTodoService to set  
	 * @since   JDK 1.6  
	 */
	public void setTfrJobTodoService(ITfrJobTodoService tfrJobTodoService) {
		this.tfrJobTodoService = tfrJobTodoService;
	}

	/**
	 * @param waybillManagerService the waybillManagerService to set
	 */
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	/**
	 * @param weixinSendService the weixinSendService to set
	 */
	public void setWeixinSendService(IWeixinSendService weixinSendService) {
		this.weixinSendService = weixinSendService;
	}

	public void setTruckArriveConfirmService(
			ITruckArriveConfirmService truckArriveConfirmService) {
		this.truckArriveConfirmService = truckArriveConfirmService;
	}

	// 屏蔽ECS系统接口服务类
	private IConfigurationParamsService configurationParamsService;
	
	/**
	 * 屏蔽ECS系统接口服务类
	 * @param configurationParamsService
	 */
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
	
	//sonar-352203
//	private IPushForWaybillToPTPService pushForWaybillToPTPService;
	/**
	 * @param pushForWaybillToPTPService the pushForWaybillToPTPService to set
	 */
/*	public void setPushForWaybillToPTPService(
			IPushForWaybillToPTPService pushForWaybillToPTPService) {
		this.pushForWaybillToPTPService = pushForWaybillToPTPService;
	}*/
	
	private IFossToCubcService fossToCubcService;

	/**
	 * 根据条件查询到达信息.
	 * 
	 * @param queryEntity
	 *            the query entity
	 * @param limit
	 *            the limit
	 * @param start
	 *            the start
	 * @return the list
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-14 下午4:05:34
	 */
	@Override
	public List<ArrivalEntity> queryArrivalGrid(QueryArrivalEntity queryEntity,
			int limit, int start) {
		List<ArrivalEntity> list = arrivalDao.queryArrivalGrid(queryEntity, limit, start);
		
		////335284 cubc gray
		List<String> chargingAssmebleNos = new ArrayList<String>();
		for (ArrivalEntity e : list) {
			if (e.getFee() == null && e.getChargingAssmebleNo() != null) {
				chargingAssmebleNos.add(e.getChargingAssmebleNo());
			}
		}
		if (chargingAssmebleNos.size() > 0) {
			LOGGER.info("走cubc查询未结清货款：" + chargingAssmebleNos);
			CUBCChargingAssmebles nos = new CUBCChargingAssmebles();
			nos.setChargingAssmebleNos(chargingAssmebleNos);
			String jsonString = JSONObject.toJSONString(nos);
			String result = null;
			try {
				result = fossToCubcService.queryArrivalUnverifyFeeFromCUBC(jsonString);
			} catch (Exception e) {
				LOGGER.error("CUBC晚于FOSS上线时间，先进行容错处理", e);
				result = "cubc";
			}
			if (!result.startsWith("cubc")) {
				try {
					CUBCChargingAssmebles parseObject = JSONObject.parseObject(result, CUBCChargingAssmebles.class);
					if (parseObject.isSuccess() && parseObject.getFossTransterEntities() != null) {
						List<FossTransterEntity> transterEntities = parseObject.getFossTransterEntities();
						for (FossTransterEntity cubcAmount : transterEntities) {
							for (ArrivalEntity fossObj : list) {
								if (fossObj.getFee() != null //已有金额 ，跳过
										&& fossObj.getChargingAssmebleNo() != null //计费配载单为空，跳过
										&& fossObj.getChargingAssmebleNo().equals(cubcAmount.getChargingAssmebleNo())) {
									fossObj.setFee(cubcAmount.getUnverifyAmount());
									break;
								}
							}
						}
					}
				} catch (JSONException ej) {//接口不通
					LOGGER.error("cubc - 接口异常queryArrivalGrid", ej);
					throw new TfrBusinessException("cubc - 接口异常", ej);
				}
			}
		}
		////end cubc gray
		
		
		/**
		 * @desc 需求又变更了，要求修改入场时间的逻辑，将入场时间取值于PDA获取月台的时间，需求变更你懂的！ 下面的代码需要注释掉了
		 * @author 105795
		 * @date 2015-07-08
		 * */
		//将任务车辆id拿出来
		/*List<String> truckTaskIdList=new ArrayList<String>();
		for(ArrivalEntity entity : list){
			if(entity.getTruckTaskId()!=null){
				truckTaskIdList.add(entity.getTruckTaskId());
			}
		}*/
		//根据任务车辆ID查询封签信息根据任务车辆去查询封签类型及入场时间
		
		/*if(truckTaskIdList.size()>0){
			List<ArrivalEntity> sealList=arrivalDao.querySealByTruckTaskId(truckTaskIdList);
			for(ArrivalEntity sealEntity:sealList){
				
				for(ArrivalEntity entity:list){
					
					if(sealEntity.getTruckTaskId()!=null&&entity.getTruckTaskId()!=null
							&&sealEntity.getTruckTaskId().equals(entity.getTruckTaskId())){
						entity.setArrCheckTime(sealEntity.getArrCheckTime()==null?null:sealEntity.getArrCheckTime());
						entity.setCheckType(sealEntity.getCheckType()==null?null:sealEntity.getCheckType());
					}
					
				}
			}
			
		}*/
		
		for (ArrivalEntity entity : list) {
			/*//如果封签校验类型为FOSS或者为空则入场时间重置为空
			if(entity.getCheckType()==null||entity.getCheckType().equals("FOSS")){
				entity.setArrCheckTime(null);
				
			}*/
			// 出发时间
			if(entity.getManualConfirmDepartTime()!=null){
				entity.setDepartTime2(entity.getManualConfirmDepartTime());
				entity.setDepartTime(DepartureHandle.getDateStr(entity
					.getManualConfirmDepartTime())+getDepartType(DepartureConstant.ACTUAL_DEPART_TYPE_MANUAL_CONFIRM));
			}else{
				entity.setDepartTime2(entity.getActualDepartTime());
				entity.setDepartTime(DepartureHandle.getDateStr(entity
						.getActualDepartTime())
						+ getDepartType(entity.getActualDepartType()));
			}
			
			if(DepartureConstant.ACTUAL_DEPART_TYPE_MANUAL.equals(entity.getActualDepartType())&&entity.getActualDepartTime()!=null){
				entity.setManualDepartTime(entity.getActualDepartTime());
			}else if(DepartureConstant.ACTUAL_DEPART_TYPE_MANUAL_CONFIRM.equals(entity.getActualDepartType())){
				entity.setManualConfirmDepartTime(entity.getActualDepartTime());
			}else if(DepartureConstant.DEPART_TYPE_PDA.equals(entity.getActualDepartType())){
				entity.setPdaDepartTime(entity.getActualDepartTime());
			}else if(DepartureConstant.DEPART_TYPE_GPS.equals(entity.getActualDepartType())){
				entity.setGpsDepartTime(entity.getActualDepartTime());
			}
			// 到达时间
			if(entity.getManualArriveTime()!=null){
				entity.setArrivalTime(DepartureHandle.getDateStr(entity
						.getManualArriveTime())
						+ getArrivalType(DepartureConstant.ACTUAL_DEPART_TYPE_MANUAL));
			}else{
				entity.setArrivalTime(DepartureHandle.getDateStr(entity
						.getActualArrivalTime())
						+ getArrivalType(entity.getActualArriveType()));
			}
			// 如果状态是未出发，并且当前时间大于预计出发时间加30分钟，显示高亮
			if (entity.getPlanDepartTime() != null) {
				if (ArrivalConstant.ARRIVAL_VEHICLE_UNDEPART.equals(entity
						.getDetailStatus())
						&& ArrivalHandle.adddate(new Date(), 0, -ConstantsNumberSonar.SONAR_NUMBER_30).after(
								entity.getPlanDepartTime())) {
					entity.setIsBright(FossConstants.YES);
				}
			}
			
			//2013-10-16 KDTE-1366 zyx
			//(GPSService.updateVehicleTrack() 一般来说会自动对车辆预计到达时间做更新的)
			//如果GPS没有对该车辆进行过跟踪(也就是tfr.t_opt_truck_task_tracking该表无车辆任务的相关信息)
			//那么 预计到达时间=实际出发时间+规定运行时间
			//反之则不对预计到达时间做任何操作
//			Long hasTruckTaskTracking = arrivalDao.hasTruckTaskTracking(entity.getId());
//			if(hasTruckTaskTracking <= 0) {
//				//GPS没有对该车辆做过跟踪, 则没有更新过预计到达时间
//				//此时预计到达时间=实际出发时间+规定运行时间
//				if(entity.getDepartTime2() != null) {
//					//先默认查运作到运作的数据, 如果没有时效则再查营业部到运作的
//					Long commonAging = lineService.calculateAging(entity.getLineVirtualCode(), entity.getOrigOrgCode(), entity.getDestOrgCode(), DictionaryValueConstants.PRODUCT_NORMAL);
//					if(commonAging == null || commonAging <= 0) {
//						List<DepartureStandardEntity> departureStandardList = departureStandardService.queryDepartureStandardListByLineVirtualCode(entity.getLineVirtualCode());
//						if(CollectionUtils.isEmpty(departureStandardList)) {
//							//如果营业部到运作也为空则时效默认为0
//							commonAging = 0L;
//						} else {
//							DepartureStandardEntity departureStandard = departureStandardList.get(0);
//							//准点发车时间 eg:0200(02:00) 1000(10:00)
//							String leaveTimeStr = departureStandard.getLeaveTime();
//							//准点到达时间 eg:0200(02:00) 1000(10:00)
//							String arriveTimeStr = departureStandard.getArriveTime();
//							if(StringUtils.isEmpty(leaveTimeStr) || StringUtils.isEmpty(arriveTimeStr)) {
//								//如果出发 到达时间有一个为空, 则都重置为00:00;
//								//否则计算出来的结果会有明显的问题.
//								leaveTimeStr = "0000";
//								arriveTimeStr = "0000";
//							}
//							SimpleDateFormat sdf = new SimpleDateFormat("hhmm");
//							try {
//								Date leaveTime = sdf.parse(leaveTimeStr);
//								Date arriveTime = sdf.parse(arriveTimeStr);
//								commonAging = arriveTime.getTime() - leaveTime.getTime();
//								//此时commonAging的结果为毫秒级的, 我们需要的是千分之一小时为单位的.
//								//所以commonAging = commonAging / 1000 / 60 / 60 * 1000;
//								commonAging = commonAging / 60 / 60;
//							} catch (ParseException e) {
//								throw new TfrBusinessException("获取线路时效时出错, 时效转换错误!");
//							}
//						}
//					}
//					BigDecimal runHours = new BigDecimal(commonAging).divide(new BigDecimal(1000),3,BigDecimal.ROUND_UP);
//					//再重新转换为分钟
//					BigDecimal runMinutes = runHours.multiply(new BigDecimal(60));
//					Calendar calendar = Calendar.getInstance();
//					calendar.setTime(entity.getDepartTime2());
//					calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + runMinutes.intValue());
//					entity.setPlanArrivalTime(calendar.getTime());
//				}
//			}
		//end
			
			// 如果状态是在途，并且当前时间大于预计到达时间加30分钟，显示高亮
			if (entity.getPlanArrivalTime() != null) {
				if (ArrivalConstant.ARRIVAL_VEHICLE_ONTHEWAY.equals(entity
						.getDetailStatus())
						&& ArrivalHandle.adddate(new Date(), 0, -ConstantsNumberSonar.SONAR_NUMBER_30).after(
								entity.getPlanArrivalTime())) {
					entity.setIsBright(FossConstants.YES);
				}
			}
			// 证件包情况
			if ("2".equals(queryEntity.getCertificaterBagStatus())) {
				// 证件包状态
				entity.setCertificteBagStatus(ArrivalConstant.ALLOCATIONED_BAG);
			} else {
				// 证件包状态
				entity.setCertificteBagStatus(ArrivalConstant.NOT_ALLOCATIONED_BAG);
			}
			// 月台情况
			if (StringUtil.isBlank(entity.getPlatformStatus())) {
				entity.setPlatformStatus(ArrivalConstant.NOT_ALLOCATIONED);
			} else {
				entity.setPlatformStatus(ArrivalConstant.ALLOCATIONED);
			}
		}
		return list;
	}

	/**
	 * (二程接驳出发到达)查询本部门出发的发车任务.
	 * @param the query entity
	 * @param limit
	 * @param start
	 * @return the list
	 * @author gongjp
	 * @date 2015-08-26 下午19:08:51
	 */
	@Override
	public List<ArrivalEntity> querySecondCarDepartureGrid(QueryArrivalEntity queryEntity,int limit, int start) {
		List<ArrivalEntity> list = arrivalDao.querySecondCarDepartureGrid(queryEntity,
				limit, start);
		return list;
	}
	
	
	/**
	 * @function (二程接驳发车确认)根据id查询所有发车任务信息
	 * @return the string
	 * @author gongjp
	 * @date 2015-09-01 下午18:24:23
	 */
	@Override
	public List<ArrivalEntity> checkSecondCarDepartConfirm(OperationDTO dto){
		List<ArrivalEntity> list = arrivalDao.checkSecondCarDepartConfirm(dto);
		return list;
	}

	/**
	 * @function (二程接驳到达确认)根据id查询所有发车任务信息
	 * @return the string
	 * @author gongjp
	 * @date 2015-09-01 下午18:30:34
	 */
	@Override
	public List<ArrivalEntity> checkSecondCarArrivalConfirm(OperationDTO dto){
		List<ArrivalEntity> list = arrivalDao.checkSecondCarArrivalConfirm(dto);
		return list;
	}
	
	/**
	 * @function (二程接驳到达本部门查询)查询本部门到达的车辆信息.
	 * @param the query entity
	 * @param limit
	 * @param start
	 * @return the list
	 * @author gongjp
	 * @date 2015-08-28 下午09:41:51
	 */
	@Override
	public List<ArrivalEntity> querySecondCarArrivalGrid(QueryArrivalEntity queryEntity,int limit, int start){
		List<ArrivalEntity> list = arrivalDao.querySecondCarArrivalGrid(queryEntity,
				limit, start);
		return list;
	}
	
	/**
	 * 查询车辆取消出发记录
	 * @author 163580
	 * @date 2014-1-20 上午10:26:42
	 * @param truckTaskDetailId
	 * @return
	 * @see
	 */
	@Override
	public List<TruckActionDetailDto> queryCancelDepartureGrid(
			String truckTaskDetailId) {
		if(StringUtils.isEmpty(truckTaskDetailId)) {
			throw new TfrBusinessException("查询车辆取消出发记录时出错, 错误的参数!");
		}
		//别问我为啥不跟查询取消到达记录的方法封装在一起
		return arrivalDao.queryCancelDepartureGrid(truckTaskDetailId);
	}

	/**
	 * 查询车辆取消到达记录
	 * @author 163580
	 * @date 2014-1-20 上午10:27:20
	 * @param truckTaskDetailId
	 * @return
	 * @see
	 */
	@Override
	public List<TruckActionDetailDto> queryCancelArrivalGrid(
			String truckTaskDetailId) {
		if(StringUtils.isEmpty(truckTaskDetailId)) {
			throw new TfrBusinessException("查询车辆取消到达记录时出错, 错误的参数!");
		}
		return arrivalDao.queryCancelArrivalGrid(truckTaskDetailId);
	}

	/**
	 * 查询外请车.
	 * 
	 * @param queryEntity
	 *            the query entity
	 * @return the leased truck dto
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-14 下午4:05:34
	 */
	@Override
	public LeasedTruckDTO queryLeasedTruck(QueryArrivalEntity queryEntity) {
		LeasedTruckDTO dto = arrivalDao.queryLeasedTruck(queryEntity);
		
		////335284 cubc gray
		if (dto != null && dto.getTotalFee().compareTo(new BigDecimal(0)) == 0) {
			LOGGER.info("走cubc查询外请车费用:" + dto.getTotalTrucks());
			String no = arrivalDao.queryLeasedTruckAssmebleNo(queryEntity);
			if (no == null) {
				LOGGER.info("走cubc查询外请车费用:未查询到配载单");
				return dto;
			}
			
			CUBCChargingAssmebles assmeble = new CUBCChargingAssmebles();
			List<String> list = new ArrayList<String>();
			list.add(no);
			assmeble.setChargingAssmebleNos(list);
			String jsonString = JSONObject.toJSONString(assmeble);
			String result = null;
			try {
				result = fossToCubcService.queryLeasedTruckTotalFee(jsonString);
			} catch (Exception e) {
				LOGGER.error("CUBC晚于FOSS上线时间，先进行容错处理", e);
				result = "cubc";
			}
			
			if (!result.startsWith("cubc")) {
				try {
					CUBCChargingAssmebles parseObject = JSONObject.parseObject(result, CUBCChargingAssmebles.class);
					if (parseObject.isSuccess() && parseObject.getFossTransterEntities() != null) {
						List<FossTransterEntity> transterEntities = parseObject.getFossTransterEntities();
						if (transterEntities != null && transterEntities.size() > 0) {
							dto.setTotalFee(transterEntities.get(0).getAmountTotal());
							LOGGER.info("设置cubc返回的费用:" + dto.getTotalFee());
						}
					}
				} catch (JSONException e) {
					LOGGER.error("cubc - 接口异常queryLeasedTruck", e);
					throw new TfrBusinessException("cubc - 接口异常", e);
				}
			}
		}
		////end cubc
		
		return dto;
	}

	/**
	 * 判断车辆放行的类型:PDA；手工放行；电子围栏放行.
	 * 
	 * @param actualDepartType
	 *            the actual depart type
	 * @return the depart type
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-14 下午4:05:34
	 */
	private String getDepartType(String actualDepartType) {
		if (DepartureConstant.ACTUAL_DEPART_TYPE_GPS.equals(actualDepartType)) {
			// 是否是GPS
			return ArrivalConstant.DEPART_TYPE_NAME_GPS;
		}
		if (DepartureConstant.ACTUAL_DEPART_TYPE_PDA.equals(actualDepartType)) {
			// 是否是PDA
			return ArrivalConstant.DEPART_TYPE_NAME_PDA;
		}
		if (DepartureConstant.ACTUAL_DEPART_TYPE_MANUAL
				.equals(actualDepartType)) {
			// 是否是手工
			return ArrivalConstant.DEPART_TYPE_NAME_MANUAL;
		}
		if (DepartureConstant.ACTUAL_DEPART_TYPE_MANUAL_CONFIRM
				.equals(actualDepartType)) {
			// 是否是手工确认
			return ArrivalConstant.DEPART_TYPE_NAME_DEPART_CONFIRM;
		}
		return "";
	}

	/**
	 * 判断车辆放行的类型:PDA；手工放行；电子围栏放行.
	 * 
	 * @param actualArriveType
	 *            the actual arrive type
	 * @return the arrival type
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-14 下午4:05:34
	 */
	private String getArrivalType(String actualArriveType) {
		if (DepartureConstant.ACTUAL_DEPART_TYPE_GPS.equals(actualArriveType)) {
			// 是否是GPS
			return ArrivalConstant.DEPART_TYPE_NAME_GPS;
		}
		if (DepartureConstant.ACTUAL_DEPART_TYPE_PDA.equals(actualArriveType)) {
			// 是否是PDA
			return ArrivalConstant.DEPART_TYPE_NAME_PDA;
		}
		if (DepartureConstant.ACTUAL_DEPART_TYPE_MANUAL
				.equals(actualArriveType)) {
			// 是否是手工
			return ArrivalConstant.ARRIVE_TYPE_NAME_MANUAL;
		}
		return "";
	}

	
	/*
	 * 335284 cubc gray
	 * 下面的sql关联了结算金额
	 */
	/**
	 * 取得信息条数.
	 * 
	 * @param queryEntity
	 *            the query entity
	 * @return the count
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-10 下午6:20:55
	 * @see com.deppon.foss.module.transfer.arrival.api.server.service.IArrivalService#getCount(com.deppon.foss.module.transfer.arrival.api.shared.domain.QueryArrivalEntity)
	 */
	@Override
	public Long getCount(QueryArrivalEntity queryEntity) {
		return arrivalDao.getCount(queryEntity);
	}

	/**
	 * 取得信息条数.
	 * @param queryEntity
	 * @return the count
	 * @author gongjp
	 * @date 2015-08-26 下午 20:47:34
	 */
	@Override
	public Long getSecondCarDepartureCount(QueryArrivalEntity queryEntity) {
		return arrivalDao.getSecondCarDepartureCount(queryEntity);
	}
	
	/**
	 * 取得到达本部门信息条数.
	 * @param queryEntity
	 * @return the count
	 * @author gongjp
	 * @date 2015-08-28 上午 11:11:34
	 */
	@Override
	public Long getSecondCarArrivalCount(QueryArrivalEntity queryEntity) {
		return arrivalDao.getSecondCarArrivalCount(queryEntity);
	}
	
	/**
	 * 查询月台数据.
	 * 
	 * @param dtoP
	 *            the dto p
	 * @return the list
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-16 下午2:18:22
	 * @see com.deppon.foss.module.transfer.arrival.api.server.service.IArrivalService#queryPlatformGrid(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public List<PlatformDTO> queryPlatformGrid(QueryPlatformDTO dtoP) {
		// 根据车牌号查询所有的交接单
		List<String> strList = arrivalDao.queryBillByTaskId(dtoP
				.getTruckTaskDetailId());
		List<OptimalPlatformDto> platlist = new ArrayList<OptimalPlatformDto>();
		if (null != strList && strList.size() < 1) {
			// 如果为空，直接给出车牌号
			strList.add(dtoP.getVehicleNo());
		}
		try {
			if (ArrivalConstant.VEHICLE_SHOW_EMPTY_PLATFORM.equals(dtoP
					.getOnlyNullPlatform())) {
				// 只显示空月台
				platlist = calculateOptimalPlatformService
						.calcUnusedOptimalPlatform(dtoP.getTruckTaskDetailId());
			} else {
				// 显示所有的月台
				platlist = calculateOptimalPlatformService
						.calcOptimalPlatform(dtoP.getTruckTaskDetailId());
			}
		} catch (CalculateOptimalPlatformException e) {
			// 月台报错
			throw new TfrBusinessException(
					getArgumentMessage(e.getErrorArguments()), "");
		}
		List<OptimalPlatformDto> platforms = new ArrayList<OptimalPlatformDto>();
		if (platlist != null) {
			if (platlist.size() >= ConstantsNumberSonar.SONAR_NUMBER_5) {// 只取前五条
				for (int i = 0; i < ConstantsNumberSonar.SONAR_NUMBER_5; i++) {
					platforms.add(platlist.get(i));
				}
			} else {
				for (OptimalPlatformDto opt : platlist) {
					platforms.add(opt);
				}
			}
		}
		/* 应用监控服务 */
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		// 计算最优月台计数
		BusinessMonitorIndicator counterIndicator = BusinessMonitorIndicator.OPTIMAL_PLATFORM_CALC_COUNT;
		businessMonitorService.counter(counterIndicator, currentInfo);
		List<PlatformDTO> list = new ArrayList<PlatformDTO>();
		PlatformDTO dto = new PlatformDTO();
		for (OptimalPlatformDto optimalPlatformDto : platforms) {
			dto = new PlatformDTO();
			if (optimalPlatformDto.getPlatformEntity() != null) {
				// 月台号
				dto.setPlatformNo(optimalPlatformDto.getPlatformEntity()
						.getPlatformCode());
				// 月台虚拟号
				dto.setVirtualCode(optimalPlatformDto.getPlatformEntity()
						.getVirtualCode());
				// 是否有升降平台
				dto.setHasLift(optimalPlatformDto.getPlatformEntity()
						.getHasLift());
				// 状态
				dto.setPlatformStatus(optimalPlatformDto.getPlatformEntity()
						.getActive());
				// 车长
				dto.setVehicleType(optimalPlatformDto.getPlatformEntity()
						.getVehicleName());
				// 可用时间
				dto.setEffectiveTime(optimalPlatformDto.getUsableTime());
				// 状态
				dto.setPlatformStatus(optimalPlatformDto.getIsUsable());
			}
			list.add(dto);
		}
		return list;
	}

	/**
	 * 转换错误吗为中文.
	 * 
	 * @param argumentMessage
	 *            the argument message
	 * @return the argument message
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-27 下午1:54:54
	 */
	private String getArgumentMessage(Object[] argumentMessage) {
		if (argumentMessage == null) {
			// 返回空
			return StringUtil.EMPTY_STRING;
		}
		StringBuilder stringBuilder = new StringBuilder();
		for (Object object : argumentMessage) {
			stringBuilder.append(object.toString());
		}
		return stringBuilder.toString();
	}

	/**
	 * 初始化分派月台的界面数据.
	 * 
	 * @param queryEntity
	 *            the query entity
	 * @return the vehicle info dto
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-19 下午1:48:16
	 * @see com.deppon.foss.module.transfer.arrival.api.server.service.IArrivalService#queryVehicleForPlatform(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public VehicleInfoDTO queryVehicleForPlatform(QueryArrivalEntity queryEntity) {
		VehicleInfoDTO dto = new VehicleInfoDTO();
		dto.setTruckTaskDetailId(queryEntity.getId());
		int frequencyNo = 0;
		if (!StringUtil.isBlank(queryEntity.getFrequencyNo())) {
			try {
				// 班次号
				frequencyNo = Integer.parseInt(queryEntity.getFrequencyNo());
			} catch (NumberFormatException e) {
				// 班次转换类型出错，数据库中班次的数值不对
				throw new TfrBusinessException("班次转换类型出错，数据库中班次的数值不对", "");
			}
		}
		// 查询线路信息
		DepartureStandardDto dDto = null;
		dDto = lineService.queryDepartureStandardByLineSequence(
						queryEntity.getLineVirtualCode(), frequencyNo);
		/**
		 * 2014-09-10  200978
		 * 分离快递和零担的线路服务接口
		 */
		//如果零担的线路信息为空，则查询快递线路
		if(dDto == null){
			dDto = expresslineService.queryDepartureStandardByLineSequence(queryEntity.getLineVirtualCode(), frequencyNo);
		}
		
		// 查询车辆信息
		VehicleAssociationDto vDto = vehicleService
				.queryVehicleAssociationDtoByVehicleNo(queryEntity
						.getVehicleNo());
		if (vDto != null) {
			VehicleDetailDTO vdto = new VehicleDetailDTO();
			vdto.setTaskDetailId(queryEntity.getId());
			BusinessInfoEntity busi = vehicleInfoDao.queryBusiness(vdto);
			// 载重
			dto.setWeight(busi.getWeight()
					+ DepartureConstant.VEHICLE_UNIT_OF_KG + busi.getVolume()
					+ DepartureConstant.VEHICLE_UNIT_OF_VOLUME);
			// 车长名称
			dto.setVehicleLengthName(vDto.getVehicleLengthValue());
		}
		if (dDto != null) {
			// 运输性质
			dto.setProductType(dDto.getProductType());
		}
		// 线路名称
		dto.setLineName(queryEntity.getLineName());
		// 装卸车标准
		LoadAndUnloadEfficiencyVehicleDto loadAndUnloadEfficiencyVehicleDto = null;
		try {
			// 装卸车标准
			loadAndUnloadEfficiencyVehicleDto = loadAndUnloadEfficiencyVehicleComplexService
					.gainLoadAndUnloadEfficiencyVehicleUp(
							queryEntity.getVehicleNo(),
							queryEntity.getDestOrgCode());
		} catch (LoadAndUnloadEfficiencyVehicleException e) {
			// 调用装卸车任务接口报错
			LOGGER.error("调用装卸车任务接口报错" + e.getMessage(), e);
		}
		if (loadAndUnloadEfficiencyVehicleDto != null) {
			// 卸车时长
			dto.setUnloadTime(loadAndUnloadEfficiencyVehicleDto.getUnloadHour()
					+ "小时"
					+ loadAndUnloadEfficiencyVehicleDto.getUnloadMinute()
					+ "分钟");
			try {
				// 卸车时长
				dto.setPlatformUserEndTime(ArrivalHandle.adddate(queryEntity
						.getPlanArrivalTime(), Integer
						.parseInt(loadAndUnloadEfficiencyVehicleDto
								.getUnloadHour()), Integer
						.parseInt(loadAndUnloadEfficiencyVehicleDto
								.getUnloadMinute())));
			} catch (NumberFormatException e) {
				// 卸车时长不为数值
				throw new TfrBusinessException("卸车时长不为数值", "");
			}
		} else {
			dto.setPlatformUserEndTime(queryEntity.getPlanArrivalTime());
		}
		// 月台使用完毕时间--开始时间和结束时间
		dto.setPlatformUserStartTime(queryEntity.getPlanArrivalTime());
		if (null == queryEntity.getPlanArrivalTime()) {
			// 如果预计到达时间为空，怎默认的分配时间都设置为空，让操作员自己选择
			dto.setPlatformUserEndTime(null);
			dto.setPlatformUserStartTime(null);
		}
		// 设置预计到达时间
		dto.setPlanArrivalTime(queryEntity.getPlanArrivalTime());
		return dto;
	}

	/**
	 * 确认分配选中的月台.
	 * 
	 * @param vehicleInfoDTO
	 *            the vehicle info dto
	 * @return the vehicle info dto
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-30 下午6:32:58
	 */
	@Override
	public VehicleInfoDTO confirmPlatform(VehicleInfoDTO vehicleInfoDTO) {
		// 调用接口,返回月台号，进行月台分配
		try {
			platformDispatchService.addPlatformDistributeForArrival(
					vehicleInfoDTO.getVirtualCode(),
					vehicleInfoDTO.getPlatformUserStartTime(),
					vehicleInfoDTO.getPlatformUserEndTime(),
					vehicleInfoDTO.getVehicleNo());
			/* 应用监控服务 */
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 最优月台使用次数
			BusinessMonitorIndicator counterIndicator = BusinessMonitorIndicator.OPTIMAL_PLATFORM_USE_COUNT;
			businessMonitorService.counter(counterIndicator, currentInfo);
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			throw new TfrBusinessException(
					getArgumentMessage(e.getErrorArguments()), "");
		}
		// 更新任务车辆明细的到达外键值
		TruckTaskDetailEntity truckTaskDetailEntity = new TruckTaskDetailEntity();
		// 任务明细ID
		truckTaskDetailEntity.setId(vehicleInfoDTO.getTruckTaskDetailId());
		// 月太虚拟编号
		truckTaskDetailEntity.setPlatformDistributeId(vehicleInfoDTO
				.getPlatformNo());
		// 更新任务车辆明细
		departureDao.updateTaskByManual(truckTaskDetailEntity);
		return null;
	}

	/**
	 * 放行确认.
	 * 
	 * @param operationDTO
	 *            the operation dto
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-25 下午4:02:57
	 * @see com.deppon.foss.module.transfer.arrival.api.server.service.IArrivalService#departConfirm(com.deppon.foss.module.transfer.arrival.api.shared.dto.OperationDTO)
	 */
	@Override
	@Transactional
	public void departConfirm(OperationDTO operationDTO) {
		// 2013-09-25 BUG-55946 前台校验不是最新数据 取后台数据
		TruckTaskDetailEntity truckTaskDetail = new TruckTaskDetailEntity();
		truckTaskDetail.setId(operationDTO.getId());
		//只根据车辆任务明细id查找车辆明细
		List<TruckTaskDetailEntity> taskDetailDetaiList  =  departureDao.queryTruckTaskDetail(truckTaskDetail);
		String actualdepartType = taskDetailDetaiList.get(0).getActualDepartType();
		//已经手工出发的车辆不能再次手工出发 
		if(DepartureConstant.ACTUAL_DEPART_TYPE_MANUAL.equals(actualdepartType)||
				DepartureConstant.ACTUAL_DEPART_TYPE_MANUAL_CONFIRM.equals(actualdepartType)){
			LOGGER.error("已经手工出发的车辆不能再次手工出发");
			throw new TfrBusinessException("已经手工出发的车辆不能再次手工出发");
		}
		String status = taskDetailDetaiList.get(0).getStatus();
		if (!(ArrivalConstant.ARRIVAL_VEHICLE_UNDEPART.equals(status) || 
				ArrivalConstant.ARRIVAL_VEHICLE_ONTHEWAY.equals(status))) {
			// 只能对待出发的车辆进行发车确认
			LOGGER.error("车辆已经出发, 不能再次出发, 请刷新后操作!");
			throw new TfrBusinessException("车辆已经出发, 不能再次出发, 请刷新后操作!", "");
		}
		// 通过车辆任务明细ID查到该车辆任务下同一出发部门的所有的车辆任务明细
		// 需要更新所有的车辆任务明细
		Date departTime = new Date();
		OperationDTO dto = new OperationDTO();
		// 任务车辆ID
		dto.setTruckTaskId(operationDTO.getTruckTaskId());
		// 出发部门
		dto.setOrigOrgCode(operationDTO.getOrigOrgCode());
		// 状态
		dto.setVehicleStatus(ArrivalConstant.ARRIVAL_VEHICLE_UNDEPART);
		//如果车辆已经出发，直接更新时间
		// 任务车辆明细置为发车
		TruckTaskDetailEntity truckTaskDetailEntity = new TruckTaskDetailEntity();
		truckTaskDetailEntity.setId(operationDTO.getId());
		truckTaskDetailEntity.setManualDepartTime(departTime);
		if(!(DepartureConstant.ACTUAL_DEPART_TYPE_MANUAL.equals(operationDTO.getActualDepartType())
				||DepartureConstant.ACTUAL_DEPART_TYPE_PDA.equals(operationDTO.getActualDepartType())
				||DepartureConstant.ACTUAL_DEPART_TYPE_GPS.equals(operationDTO.getActualDepartType())
				||DepartureConstant.ACTUAL_DEPART_TYPE_MANUAL_CONFIRM.equals(operationDTO.getActualDepartType())))
		{
			truckTaskDetailEntity.setActualDepartTime(departTime);
			truckTaskDetailEntity.setActualDepartType(DepartureConstant.ACTUAL_DEPART_TYPE_MANUAL_CONFIRM);
		}
		truckTaskDetailEntity.setManualDepartUserCode(DepartureHandle
				.getCurrentUserCode());
		truckTaskDetailEntity.setManualDepartUserName(DepartureHandle
				.getCurrentUserName());
		truckTaskDetailEntity.setManualDepartOrgCode(DepartureHandle
				.getCurrentOrgCode());
		truckTaskDetailEntity.setManualDepartOrgName(FossUserContext.getCurrentDeptName());
		//出发确认前校验是否一个车辆任务含有多个有运费的车次
		List<String> vehicleAessNOs=vehicleAssembleBillService.queryTruckBillByDetailIdAndBillNo(operationDTO.getId(), null);
		if(CollectionUtils.isNotEmpty(vehicleAessNOs)&&vehicleAessNOs.size()>1){
			throw  new TfrBusinessException("同一车牌号，快递与零担均显示运费，无法出发！"); 
		}
		// 更新任务车辆明细
		departureDao.updateTaskByManual(truckTaskDetailEntity);
		List<OperationDTO> taskDetailList = arrivalDao
				.queryTruckDetailByTaskId(dto);
		/* 应用监控服务 */
		// 车辆放行时计数
		VehicleAssociationDto vehicleDto = null;
		try {
			// 车牌号查询车辆信息
			vehicleDto = vehicleService
					.queryVehicleAssociationDtoByVehicleNo(operationDTO
							.getVehicleNo());
		} catch (LeasedVehicleTypeException e) {
			LOGGER.error(e.getMessage());
			throw new BusinessException(e.getMessage(), "");
		}
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		if (vehicleDto == null) {
			vehicleDto = new VehicleAssociationDto();
		}
		if (DepartureConstant.TRUCK_TYPE_LEASED.equals(vehicleDto
				.getVehicleOwnershipType())) {
			// 外请车
			if (!FossConstants.YES.equals(operationDTO.getBeCarLoad())) {
				// 需要是不含整车的
				if (DepartureConstant.DEPART_ITEM_TYPE_LONG.equals(operationDTO
						.getBusinessType())) {
					// 长途发车台次
					BusinessMonitorIndicator counterIndicator = BusinessMonitorIndicator.LEASED_LONG_DEPART_COUNT;
					businessMonitorService.counter(counterIndicator,
							currentInfo);
				} else if (DepartureConstant.DEPART_ITEM_TYPE_SHORT
						.equals(operationDTO.getBusinessType())) {
					// 短途发车台次
					BusinessMonitorIndicator counterIndicator = BusinessMonitorIndicator.LEASED_SHORT_DEPART_COUNT;
					businessMonitorService.counter(counterIndicator,
							currentInfo);
				}
			}
			if (DepartureConstant.DEPART_ITEM_TYPE_PKP.equals(operationDTO
					.getBusinessType())) {
				// 集中接送货车发车台次
				BusinessMonitorIndicator counterIndicator = BusinessMonitorIndicator.LEASED_SCHEDULE_DEPART_COUNT;
				businessMonitorService.counter(counterIndicator, currentInfo);
			}
		}
		if (DepartureConstant.DEPART_ITEM_TYPE_LONG.equals(operationDTO
				.getBusinessType())) {
			// 长途发车台次
			BusinessMonitorIndicator counterIndicator = BusinessMonitorIndicator.TRUCK_LONG_DEPART_COUNT;
			businessMonitorService.counter(counterIndicator, currentInfo);
		} else if (DepartureConstant.DEPART_ITEM_TYPE_SHORT.equals(operationDTO
				.getBusinessType())) {
			// 短途发车台次
			BusinessMonitorIndicator counterIndicator = BusinessMonitorIndicator.TRUCK_SHORT_DEPART_COUNT;
			businessMonitorService.counter(counterIndicator, currentInfo);
		} else if (DepartureConstant.DEPART_ITEM_TYPE_PKP.equals(operationDTO
				.getBusinessType())) {
			// 集中接送货车发车台次
			BusinessMonitorIndicator counterIndicator = BusinessMonitorIndicator.TRUCK_SCHEDULE_DEPART_COUNT;
			businessMonitorService.counter(counterIndicator, currentInfo);
		}
		if (taskDetailList != null) {
			for (OperationDTO o : taskDetailList) {
				
				// 根据任务车辆明细判断是否是集配交接单，集配交接单不能放行
				departureService.taskIsDistanceHandover(o.getTruckTaskId(),o.getOrigOrgCode(),null);
				if (!(DepartureConstant.DEPART_ITEM_TYPE_PKP
						.equals(operationDTO.getBusinessType()) || FossConstants.YES
						.equals(operationDTO.getBeCarLoad()))) {
				  
					TruckDepartPlanDetailDto planDetail = null;
					//获取车辆信息
					VehicleAssociationDto vehicleInfo = vehicleAssembleBillService.queryVehicleInfoByVehicleNo(o.getVehicleNo());
					if(vehicleInfo != null){
						//200968  判断车牌号是否为挂车vehicletype_trailer
						if(StringUtils.equals(vehicleInfo.getVehicleType(), "vehicletype_trailer")&& !vehicleInfo.getVehicleMotorcadeName().equals("外请车")) {
							//根据 出发部门、到达部门、日期、挂牌号查询当天的班次
							planDetail = truckDepartPlanDetailService.queryDepartPlanDetailByTrailerVehicleNo(o.getOrigOrgCode(), o.getDestOrgCode(), o.getVehicleNo(), null);
						} else {
							planDetail = truckDepartPlanDetailService.queryLatestTruckDepartPlanDetail(o.getOrigOrgCode(), o.getDestOrgCode(), o.getVehicleNo(), null);
						}
					}

					//2016-01-11  长途+公司车
					if(DepartureConstant.DEPART_ITEM_TYPE_LONG.equals(operationDTO.getBusinessType())
							&& DepartureConstant.TRUCK_TYPE_OWN.equals(vehicleDto
									.getVehicleOwnershipType())){
						//发车计划不为空
					  if(planDetail!=null){
						//1、 200968 2016-01-10 长途+公司车+挂车  车牌校验: 校验配载单的车牌号是否与发车计划的车牌号一致。
						if(!o.getVehicleNo().equals(planDetail.getVehicleNo())){
							LOGGER.error("请确保配载单的车牌号与发车计划的车牌号保持一致!");
							throw new TfrBusinessException("请确保配载单车牌号与发车计划车牌号保持一致!");
						  }
					   }
					}
					
					SealEntity sealEntity = new SealEntity();
					sealEntity.setTruckTaskDetailId(o.getTruckTaskId());
					sealEntity.setSealType(SealConstant.SEAL_TYPE_BIND);
					List<SealEntity> seallist = vehicleSealService
							.querySealByTruckTaskId(sealEntity);
					if (seallist == null || seallist.size() <= 0) {
						LOGGER.error("未找到该车辆的封签信息，不能放行");
						throw new TfrBusinessException("未找到该车辆的封签信息，不能放行");
					}
				}
				//通过车牌号跟任务车辆ID找到放行ID
				QueryDepartEntity queryEntity = new QueryDepartEntity();
				queryEntity.setTruckTaskId(o.getTruckTaskId());
				queryEntity.setVehicleNo(o.getVehicleNo());
				List<String> statuses = new ArrayList<String>();
				statuses.add(DepartureConstant.DEPART_STATUS_WAIT);
				statuses.add(DepartureConstant.DEPART_STATUS_Fail);
				queryEntity.setStatuses(statuses);
				List<TruckDepartEntity> truckList = departureService.queryDepart(
						queryEntity);
				TruckDepartEntity manualEntity = updateTruckDepart(truckList);
				
				List<String> vehicleAssemNOs=vehicleAssembleBillService.queryTruckBillByDetailId(operationDTO.getId(), null);
				if(CollectionUtils.isNotEmpty(vehicleAssemNOs)&&vehicleAssemNOs.size()>1){
					throw  new TfrBusinessException("同一车牌号，快递与零担均显示运费，无法出发！"); 
				}
				// 出发
				departConfirm(o.getId(), operationDTO.getTruckTaskId(),manualEntity.getId(), departTime);
				
				
				if (DepartureConstant.DEPART_ITEM_TYPE_LONG.equals(operationDTO.getBusinessType())
						|| DepartureConstant.DEPART_ITEM_TYPE_SHORT.equals(operationDTO.getBusinessType())) {
					VehicleSyncPdaEntity info = new VehicleSyncPdaEntity();
					info.setId(UUIDUtils.getUUID());
					info.setTruckTaskDetailId(o.getId());
					info.setType(operationDTO.getBusinessType()+"DEPART");
					info.setFailed(FossConstants.NO);
					Date currentTime = new Date();
					info.setCreateTime(currentTime);
					info.setModifyTime(currentTime);
					pdaUnloadOptimizeService.addVehicleSyncPda(info);
				}
				
				/**
				 * 调用tps接口同步tps发车信息
				 */
//				try {
//					LOGGER.error("调用tps接口--同步发车开始!车辆任务id："+o.getId());
//					this.departureService.synchDepartArriveInfoToTps(o.getId(), departTime, "start");
//					LOGGER.error("调用tps接口--同步发车结束!车辆任务id："+o.getId());
//				} catch (Exception e) {
//					LOGGER.error("调用tps接口--同步发车失败,错误信息："+e.toString());
//					e.printStackTrace();
//				}
			}
		}
	}
	/**
	 * 二程接驳出发发车确认.
	 * @return the string
	 * @author gongjp
	 * @date 2015-08-27 上午15:11:34
	 */
	@Override
	@Transactional
	public void secondCarDepartConfirm(OperationDTO operationDTO) {
		arrivalDao.updateSecondCarDepartConfirm(operationDTO);
	}
	
	/**
	 * 二程接驳到达确认.
	 * @return the string
	 * @author gongjp
	 * @date 2015-08-28 上午15:02:35
	 */
	@Override
	@Transactional
	public void secondCarArrivalConfirm(OperationDTO operationDTO) {
		arrivalDao.updateSecondCarArrivalConfirm(operationDTO);
	}
	/**
	 * 通过任务车辆ID更新车辆放行状态
	 * @param truckTaskId
	 * @param vehicleNo
	 */
	private TruckDepartEntity updateTruckDepart(List<TruckDepartEntity> truckList){
		TruckDepartEntity manualEntity = new TruckDepartEntity();
		if(truckList!=null&&truckList.size()>0){
			// 更新该车辆的放行时间
			// 放行状态置为已出发
			manualEntity.setId(truckList.get(0).getId());
			manualEntity
			.setStatus(DepartureConstant.DEPART_STATUS_DEPARTURE);
			manualEntity.setManualDepartUserCode(DepartureHandle
					.getCurrentUserCode());
			manualEntity.setManualDepartOrgCode(DepartureHandle
					.getCurrentOrgCode());
	//		manualEntity.setManualDepartTime(DepartureHandle
	//				.getCurrentDate());
			manualEntity.setUpdateUserCode(DepartureHandle
					.getCurrentUserCode());
			manualEntity.setUpdateOrgCode(DepartureHandle
					.getCurrentOrgCode());
			manualEntity.setUpdateUserName(DepartureHandle
					.getCurrentUserName());
			manualEntity
			.setUpdateTime(DepartureHandle.getCurrentDate());
			departureDao.saveManual(manualEntity);
		}
		return manualEntity;
	}

	/**
	 * 放行确认.
	 * 
	 * @param truckTaskDetailId
	 *            the truck task detail id
	 * @param truckTaskId
	 *            the truck task id
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-25 上午11:38:50
	 * @see com.deppon.foss.module.transfer.arrival.api.server.service.IArrivalService#departConfirm(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	@Transactional
	public void departConfirm(String truckTaskDetailId, String truckTaskId,String truckDepartId, Date departTime) {
		// 任务车辆明细置为发车
		TruckTaskDetailEntity truckTaskDetailEntity = new TruckTaskDetailEntity();
		truckTaskDetailEntity.setId(truckTaskDetailId);
		truckTaskDetailEntity.setTruckDepartId(truckDepartId);
		// 状态置为在途
		truckTaskDetailEntity
				.setStatus(ArrivalConstant.ARRIVAL_VEHICLE_ONTHEWAY);
		truckTaskDetailEntity.setActualDepartTime(departTime);
		truckTaskDetailEntity.setManualDepartTime(departTime);
		
		TruckTaskDetailEntity truckTaskDetailParms = new TruckTaskDetailEntity();
		truckTaskDetailParms.setId(truckTaskDetailId);
		List<TruckTaskDetailEntity> truckTaskDetails = departureDao.queryTruckTaskDetail(truckTaskDetailParms);
		if(truckTaskDetails.size() > 0) {
			LOGGER.error("开始更新预计到达时间");
			truckTaskDetailEntity.setPlanArriveTime(updatePlanArriveTimeWhenDepart(truckTaskDetails.get(0), departTime));
			departureDao.updateTaskByManual(truckTaskDetailEntity);
			LOGGER.error("结束更新预计到达时间");
		}
		// 出发类型置为人工出发确认
		truckTaskDetailEntity
				.setActualDepartType(DepartureConstant.ACTUAL_DEPART_TYPE_MANUAL_CONFIRM);
		// 更新任务车辆明细
		departureDao.updateTaskByManual(truckTaskDetailEntity);
		// 更新任务车辆状态
		truckTaskDetailEntity = new TruckTaskDetailEntity();
		truckTaskDetailEntity.setId(truckTaskId);
		truckTaskDetailEntity
				.setStatus(ArrivalConstant.ARRIVAL_VEHICLE_ONTHEWAY);
		departureDao.updateTruckTask(truckTaskDetailEntity);
		// job表增加一条数据
		TruckActionDetailEntity actionDetail = new TruckActionDetailEntity();
		// ID
		actionDetail.setId(DepartureConstant.SEQ_TRUCK_ACTION_JOB);
		// 类型
		actionDetail.setBundType(DepartureConstant.JOB_TRUCK_DEPART);
		// 任务车辆明细ID
		actionDetail.setTruckTaskDetailId(truckTaskDetailId);
		// 状态
		actionDetail.setStatus(DepartureConstant.JOB_NOT_START);
		// 创建时间
		actionDetail.setCreateTime(DepartureHandle.getCurrentDate());
		actionDetail.setOperatorCode(DepartureHandle.getCurrentUserCode());
		actionDetail.setOperatorName(DepartureHandle.getCurrentUserName());
		departureDao.addTruckActionDetail(actionDetail);
		tfrJobTodoService.addJobTodo(truckTaskDetailId, 
				BusinessSceneConstants.BUSINESS_SCENE_TRUCK_DEPARTURE,
				new String[]{BusinessGoalContants.BUSINESS_GOAL_TPS,
				BusinessGoalContants.BUSINESS_GOAL_EXPRESS100,
				BusinessGoalContants.BUSINESS_GOAL_TO_TAOBAOJZ,
				BusinessGoalContants.BUSINESS_GOAL_TO_TAOBAO,
				BusinessGoalContants.BUSINESS_GOAL_TO_JIAWAYUN}, 
				departTime,
				actionDetail.getOperatorCode());
		// 调用ECS系统接口开关
		if (configurationParamsService.queryTfrSwitch4Ecs()) {
			/** 新增同步给快递系统 车辆任务信息 通知任务 */
			Log.error("插入临时表，通过JOB推送给ECS系统");
			// 根据车辆任务明细ID查询出悟空交接单
			List<WkHandOverBillEntity> wkHandoverbillList = wkBillAddTfrNotifyService
					.queryWkHandOverBillByTruckTaskDetailId(truckTaskDetailId);
			// 已交接单为单位插入到临时表
			for (WkHandOverBillEntity entity : wkHandoverbillList) {
				String paramJson = entity.getHandoverBillNo() + "," + entity.getOperationOrgCode();
				String currentUserInfo = DepartureHandle.getCurrentUserCode() + ","
						+ DepartureHandle.getCurrentUserName() + "," + DepartureHandle.getCurrentOrgCode();
				tfrNotifyService.addTfrNotifyEntity(new TfrNotifyEntity(UUIDUtils.getUUID(),
						NotifyWkConstants.NOTIFY_TYPE_TRUCK_DEPARTURE, truckTaskDetailId,
						BusinessSceneConstants.WK_HANDORVERBILL_HAVE_DEPART, currentUserInfo, paramJson));

			}
		}
	}

	/**
	 * 到达确认.
	 * 
	 * @param operationDTO
	 *            the operation dto
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-25 下午4:02:57
	 * @see com.deppon.foss.module.transfer.arrival.api.server.service.IArrivalService#departConfirm(com.deppon.foss.module.transfer.arrival.api.shared.dto.OperationDTO)
	 */ 
	@Override
	@Transactional
	public List<String> arrivalConfirm(OperationDTO operationDTO) {
		// 2013-09-25 BUG-55946 前台校验不是最新数据 取后台数据
		//TruckTaskDetailEntity truckTaskDetail = new TruckTaskDetailEntity();
		//truckTaskDetail.setId(operationDTO.getId());
		//只根据车辆任务明细id查找车辆明细
		//List<TruckTaskDetailEntity> taskDetailDetaiList  =  departureDao.queryTruckTaskDetail(truckTaskDetail);
		List<TruckTaskDetailEntity> taskDetailDetaiList  =  departureDao.queryTruckTaskDetailByIDList(operationDTO.getId());
		String actualArriveType = taskDetailDetaiList.get(0).getActualArriveType();
		//已经手工出发的车辆不能再次手工出发 
		if(DepartureConstant.ACTUAL_DEPART_TYPE_MANUAL.equals(actualArriveType)||
				DepartureConstant.ACTUAL_DEPART_TYPE_MANUAL_CONFIRM.equals(actualArriveType)) {
			LOGGER.error("已经手工到达的车辆不能再次手工到达");
			throw new TfrBusinessException("已经手工到达的车辆不能再次手工到达");
		}
		String status = taskDetailDetaiList.get(0).getStatus();
		if (ArrivalConstant.ARRIVAL_VEHICLE_CANCLED.equals(status)) {
			LOGGER.error("任务已被作废, 请刷新后操作!");
			throw new TfrBusinessException("车辆任务已被作废, 请刷新后操作!", "");
		}

		if (ArrivalConstant.ARRIVAL_VEHICLE_ARRIVED.equals(status)) {
			LOGGER.error("车辆已经到达, 不能再次到达, 请刷新后操作!");
			throw new TfrBusinessException("车辆已经到达, 不能再次到达, 请刷新后操作!", "");
		}
		//用来判断是否有代办事项
		boolean hasAgency = false;
		//用于需要到达的所有的任务车辆明细ID
		List<String> detailIds = new ArrayList<String>();
		//设置手工到达时间
		TruckTaskDetailEntity truckTaskDetailEntity = new TruckTaskDetailEntity();
		truckTaskDetailEntity.setId(operationDTO.getId());
		truckTaskDetailEntity.setManualArriveTime(new Date());
		// 更新任务车辆明细
		departureDao.updateTaskByManual(truckTaskDetailEntity);
		// 通过车辆任务明细ID查到该车辆任务下同一出发部门的所有的车辆任务明细
		// 需要更新所有的车辆任务明细
		OperationDTO dto = new OperationDTO();
		dto.setTruckTaskId(operationDTO.getTruckTaskId());
		
		//如果查到有放行的申请，直接放行
		//通过车牌号跟任务车辆ID找到放行ID
		QueryDepartEntity queryEntity = new QueryDepartEntity();
		queryEntity.setTruckTaskId(operationDTO.getTruckTaskId());
		queryEntity.setVehicleNo(operationDTO.getVehicleNo());
		List<String> statusesVehicle = new ArrayList<String>();
		statusesVehicle.add(DepartureConstant.DEPART_STATUS_WAIT);
		statusesVehicle.add(DepartureConstant.DEPART_STATUS_Fail);
		queryEntity.setStatuses(statusesVehicle);
		List<TruckDepartEntity> truckList = departureService.queryDepart(
				queryEntity);
		updateTruckDepart(truckList);
		// 到达部门
		dto.setDestOrgCode(operationDTO.getDestOrgCode());
		List<String> statuses = new ArrayList<String>();
		statuses.add(ArrivalConstant.ARRIVAL_VEHICLE_UNDEPART);
		statuses.add(ArrivalConstant.ARRIVAL_VEHICLE_ONTHEWAY);
//		// 状态
//		dto.setVehicleStatus(ArrivalConstant.ARRIVAL_VEHICLE_ONTHEWAY);
		dto.setStatuses(statuses);
		// 只能对在途的车辆进行到达
		List<OperationDTO> taskDetailList = arrivalDao
				.queryTruckDetailByTaskId(dto);
		
		if (taskDetailList != null) {
			for (OperationDTO o : taskDetailList) {
				// 根据任务车辆明细判断是否是集配交接单，集配交接单不能到达
				departureService.taskIsDistanceHandover(o.getTruckTaskId(),null,o.getDestOrgCode());
				// 到达确认
				arrivalConfirm(o.getId(), operationDTO.getTruckTaskId(),
						operationDTO.getVehicleNo(), o.getPlanArrivalTime(),
						o.getTruckArriveId(), o.getBeCarLoad(),o.getActualDepartType());
				
				if (DepartureConstant.DEPART_ITEM_TYPE_LONG.equals(operationDTO.getBusinessType())
						|| DepartureConstant.DEPART_ITEM_TYPE_SHORT.equals(operationDTO.getBusinessType())) {
					VehicleSyncPdaEntity info = new VehicleSyncPdaEntity();
					info.setId(UUIDUtils.getUUID());
					info.setTruckTaskDetailId(o.getId());
					info.setType(operationDTO.getBusinessType()+"ARRIVE");
					info.setFailed(FossConstants.NO);
					Date currentTime = new Date();
					info.setCreateTime(currentTime);
					info.setModifyTime(currentTime);
					pdaUnloadOptimizeService.addVehicleSyncPda(info);
				}
				
				//生成外请车时效费用 MANA-227
				outsideVehicleChargeService.addOutsideVehicleChargeForArrival(o.getId());
				tfrJobTodoService.addJobTodo(o.getId(),
						BusinessSceneConstants.BUSINESS_SCENE_TRUCK_ARRIVAL, 
						new String[]{BusinessGoalContants.BUSINESS_GOAL_EXPRESS100,
						BusinessGoalContants.BUSINESS_GOAL_TPS,
						BusinessGoalContants.BUSINESS_GOAL_TO_TAOBAOJZ,
						BusinessGoalContants.BUSINESS_GOAL_TO_TAOBAO,
						BusinessGoalContants.BUSINESS_GOAL_TO_JIAWAYUN
						}, 
						new Date(),
						DepartureHandle.getCurrentUserCode());
				//增加调用ECS系统接口开关
				if (configurationParamsService.queryTfrSwitch4Ecs()) {
					Log.error("调用插入临时表，通过JOB推送数据给悟空");
					//根据车辆任务明细ID查询出悟空交接单
					List<WkHandOverBillEntity> wkHandoverbillList = wkBillAddTfrNotifyService.queryWkHandOverBillByTruckTaskDetailId(o.getId());
					//已交接单为单位插入到临时表
					for (WkHandOverBillEntity entity : wkHandoverbillList) {
						String paramJson = entity.getHandoverBillNo()+"," + entity.getOperationOrgCode();
						String currentUserInfo = DepartureHandle.getCurrentUserCode() + "," + DepartureHandle.getCurrentUserName() + "," + DepartureHandle.getCurrentOrgCode();
						/**新增同步给快递系统 车辆任务信息 通知任务*/
						tfrNotifyService.addTfrNotifyEntity(new TfrNotifyEntity(
								UUIDUtils.getUUID(),NotifyWkConstants.NOTIFY_TYPE_TRUCK_ARRIVAL,
								o.getId(),BusinessSceneConstants.WK_HANDORVERBILL_HAVE_ARRIVE,currentUserInfo,paramJson));
					}
				}
				if(isPTPOrg(operationDTO.getDestOrgCode())){
					tfrJobTodoService.addJobTodo(o.getId(), 
							BusinessSceneConstants.BUSINESS_SCENE_TRUCK_ARRIVAL, 
							new String[]{
							BusinessGoalContants.BUSINESS_GOAL_TO_PTP
							}, 
							new Date(),
							DepartureHandle.getCurrentUserCode());
				}
				
				/**
				 * 调用tps接口同步tps到达信息
				 */
//				try {
//					LOGGER.error("调用tps接口--同步到达开始!车辆任务id："+o.getId());
//					this.departureService.synchDepartArriveInfoToTps(o.getId(), new Date(), "arrive");
//					LOGGER.error("调用tps接口--同步到达结束!车辆任务id："+o.getId());
//				} catch (Exception e) {
//					LOGGER.error("调用tps接口--同步到达失败,错误信息："+e.toString());
//					e.printStackTrace();
//				}
				
				//查看是否有代办事项
				if(arrivalDao.queryTodoWhenArriveTruck(o.getId()).size()>0){
					hasAgency = true;
				}
				detailIds.add(o.getId());
			}
			
			//MANA-348车辆到达时校验封签
			vehicleSealService.updateSealForArrival(operationDTO.getVehicleNo());
		}
		
		if(detailIds != null && detailIds.size() > 0) {
			//调用接送货接口，推送到达消息，用于官网微信
			this.doWeixinInfo(detailIds,operationDTO.getDestOrgCode());
			
			//判断是否晚到
			//this.isArriveLate(detailIds, operationDTO.getDestOrgCode());
			//2016年12月27日10:05:09 zm  添加时间点限制   如果超出配置时间则不判断晚到
			try {
				ConfigurationParamsEntity configurationParams = configurationParamsService.queryConfigurationParamsByOrgCode(
						DictionaryConstants.SYSTEM_CONFIG_PARM__TFR , DictionaryConstants.IS_ARRIVE_LATE_PARAMS, 
						FossConstants.ROOT_ORG_CODE);
				Date bizDate  =null;
				if(configurationParams != null && StringUtils.isNotEmpty(configurationParams.getConfValue())){
					 LOGGER.error("判断是否晚到配置参数:"+	configurationParams.getConfValue());
					 bizDate  = DateUtils.strToDate(configurationParams.getConfValue());
				}
				Date realityArriverTime = Calendar.getInstance().getTime();
				//当前时间小于设定时间点则不调用晚到逻辑
				if(null!=bizDate && realityArriverTime.getTime()<bizDate.getTime()){
					//判断是否晚到
					this.isArriveLate(detailIds, operationDTO.getDestOrgCode());
				}
			} catch (Exception e) {
				LOGGER.error("判断是否晚到出错:"+e.getMessage());
			}
		}
		
		// TODO 332219 推送数据到PTP
		// 获取当前部门的code
		/*String superOrgCode = DepartureHandle.getCurrentOrgCode();
		// 查询出发部门获取是否是营业部、一级网点、二级网点
		SaleDepartmentEntity departDept = saleDepartmentService.querySaleDepartmentByCode(superOrgCode);
		// 当前部门不能为空
		if(departDept != null){
			// 当前部门是合伙人部门才推送数据
			 if(StringUtils.equals(departDept.getArrive() , FossConstants.YES)){
				 try {
						// 先通过车辆任务明细查到所有的运单号和流水号
						WayBillRefershDTO wayBillRefershDTO = new WayBillRefershDTO();
						wayBillRefershDTO.setTruckTaskDetailId(operationDTO.getId());
						List<WayBillRefershDTO> waylist = sharedDao.queryDataForWaybillRefresh(wayBillRefershDTO); 
						// 查询到达ID
						String arrivedId = arrivalDao.queryTruckArriveId(operationDTO.getId());
						if (StringUtils.isNotEmpty(arrivedId)) {
							// 根据到达id查询操作人等信息
							TruckArrivalEntity truckArrivalEntity = arrivalDao.queryTruckArrivedInfo(arrivedId);
							if (null != truckArrivalEntity) {
								for (WayBillRefershDTO list : waylist) {
									List<WaybillTrackingsDto> waybillList=new ArrayList<WaybillTrackingsDto>();
									WaybillTrackingsDto waybillTrackingsDto=new WaybillTrackingsDto();
									waybillTrackingsDto.setWaybillNo(list.getWaybillNo());
									waybillTrackingsDto.setNextDeptCode(truckArrivalEntity.getCreateOrgCode());
									// 实际到达时间
									Date arrivalTime = truckTaskDetailEntity.getManualArriveTime();
									LOGGER.info("入库时间------------"+arrivalTime);
									if(arrivalTime!=null){
										waybillTrackingsDto.setInStockTime(arrivalTime);
									}
									waybillList.add(waybillTrackingsDto);
									pushForWaybillToPTPService.pushWaybillToPTP(waybillList, truckArrivalEntity.getCreateUserCode(), truckArrivalEntity.getCreateUserName());
								}
							}
						}
					} catch (Exception e) {
						LOGGER.info("推送同步运单信息到合伙人扣款  异常 ");
						e.printStackTrace();
					}
			 }
		 }*/
		if(hasAgency){
			return detailIds;
		}else{
			return null;
		}
	}
	/**
	 * 
	 * <p>判断是否合伙人部门</p> 
	 * @author 189284 
	 * @date 2016-2-19 上午10:14:09
	 * @param destOrgCode
	 * @return
	 * @see
	 */
	@Override
	public Boolean isPTPOrg(String destOrgCode){
		SaleDepartmentEntity saleDepartmentEntity=saleDepartmentService.querySaleDepartmentInfoByCode(destOrgCode);
		if(saleDepartmentEntity==null){
			return false;
		}else{
		 return StringUtils.equals(FossConstants.YES, saleDepartmentEntity.getIsLeagueSaleDept());
		}
	}
	/**
	 * @author zyr
	 * @date 2015-7-11上午10:12:26
	 * @function 到达部门是非驻地营业部，获取有效的运单信息，到达部门为运单的提货网点，判断是否晚到，晚到则调用接送货接口
	 * @param taskDetailDetaiList
	 * @return
	 */
	private void isArriveLate(List<String> detailIds,String destOrgCode) {
		//通过OrgCode部门编号获取部门实体，判定此部门性质是否为非驻地营业部
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(destOrgCode);
		//营业部
		if(StringUtils.equals(FossConstants.YES, org.getSalesDepartment())) {
			SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySimpleSaleDepartmentByCode(destOrgCode);
			//非驻地营业部
			if(StringUtils.equals(FossConstants.NO, saleDepartmentEntity.getStation())) {
				//查询该车辆任务到达的运单
				List<HandOverBillDetailEntity> waybillDetailList = handOverBillService.queryHandOverBillDetailByIds(detailIds);
				if(waybillDetailList == null || waybillDetailList.size() == 0) {
					return;
				}
				//2016年12月27日09:12:27 zm add  添加晚到预计到达时间推后24小时
				long time=ConstantsNumberSonar.SONAR_NUMBER_24*ConstantsNumberSonar.SONAR_NUMBER_60*ConstantsNumberSonar.SONAR_NUMBER_60*ConstantsNumberSonar.SONAR_NUMBER_1000;
				//过滤到达部门为运单提货网点的运单信息
				for(HandOverBillDetailEntity entity:waybillDetailList){
					//获取运单提货网点
					WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(entity.getWaybillNo());
					if(waybillEntity != null && destOrgCode.equals(waybillEntity.getCustomerPickupOrgCode())) {
						//查询运单的预计到达时间
						WaybillPlanArriveTimeDto dto = arrivalDao.queryPlanArriveTime(entity.getWaybillNo());
						if(null != dto) {
							if(null != dto.getPreArriveTime()) {
								Date realityArriverTime = Calendar.getInstance().getTime();
								if(realityArriverTime.getTime() > (dto.getPreArriveTime().getTime()+time)) {
									compensateSpreadService.calculateSpread(entity.getWaybillNo());
								}
							}else {
								LOGGER.error("运单号" + entity.getWaybillNo() + "未查询到预计到达时间!");
							}
						}
					}
				}
			}
		}
	}
	/**
	 * @author niuly
	 * @date 2014-3-6上午9:00:26
	 * @function 获取有效的运单信息，到达部门为运单的提货网点
	 * @param taskDetailDetaiList
	 * @return
	 */
	private void doWeixinInfo(List<String> detailIds,String destOrgCode) {
		List<WeixinSendDto> dtoList = new ArrayList<WeixinSendDto>();
		//查询该车辆任务到达的运单
		List<HandOverBillDetailEntity> waybillDetailList = handOverBillService.queryHandOverBillDetailByIds(detailIds);
		if(waybillDetailList == null || waybillDetailList.size() == 0) {
			return;
		}
		
		//过滤到达部门为运单提货网点的运单信息
		List<HandOverBillDetailEntity> tempList = new ArrayList<HandOverBillDetailEntity>();
		for(HandOverBillDetailEntity entity:waybillDetailList){
			//获取运单总件数和提货网点
			WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(entity.getWaybillNo());
			
			if(waybillEntity != null && destOrgCode.equals(waybillEntity.getCustomerPickupOrgCode())) {
				//是否整车
				String isWholeVehicle = waybillEntity.getIsWholeVehicle();
				//是否经过到达部门
				String isPass = waybillEntity.getIsPassOwnDepartment();
				//整车且不经过到达部门不推送消息
				if("Y".equals(isWholeVehicle) && !"Y".equals(isPass)) {
					continue;
				}
				/**
				 * start
				 * 如果开单时间和到达时间大于20天则微信不推送信息
				 * @author zenghaibin 205109
				 * @date 修改于2014/8/8 10:02
				 ***/
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//定义时间格式
				long from=0L;//开单时间
				long to=0L;//当前时间（到达时间）
				try {
					
					from = df.parse(df.format(waybillEntity.getBillTime().getTime())).getTime();//获取开单时间
					to = df.parse(df.format(new Date())).getTime();
					} catch (ParseException e) {
						throw new TfrBusinessException("日期转换异常！");
					}
				long intervalDay=(to-from)/ConstantsNumberSonar.SONAR_NUMBER_3600 * ConstantsNumberSonar.SONAR_NUMBER_1000 *ConstantsNumberSonar.SONAR_NUMBER_24;//间隔时间
				if(intervalDay>ConstantsNumberSonar.SONAR_NUMBER_20){//如果大于20天则不进行推送，不加入tempList
					continue;
				}
				/**end**/
				//开单总件数
				entity.setWaybillPieces(BigDecimal.valueOf(waybillEntity.getGoodsQtyTotal()));
				tempList.add(entity);
			}
		}
		
		if(tempList == null || tempList.size() == 0) {
			return;
		}
		
		//封装要处理的dto
		for(HandOverBillDetailEntity entity:tempList){
			//微信dto
			WeixinSendDto dto = new WeixinSendDto();
			//运单号
			dto.setWaybillNo(entity.getWaybillNo());
			//状态发生时间
			dto.setCreateTime(new Date());
			//当前件数
			dto.setCurrentPieces(entity.getPieces().intValue());
			//到达类别  
			//到达：当前处理件数 >= 运单总件数  分批到达：当前处理件数< 运单总件数 （因运单总件数会变，而未同步至交接单中，与需求人员确认与运单总件数对比）
			if(entity.getPieces().compareTo(entity.getWaybillPieces()) < 0) {
				dto.setStateType(WeixinConstants.WEIXIN_PART_DESTARRIVED_TYPE);
			}else {
				dto.setStateType(WeixinConstants.WEIXIN_DESTARRIVED_TYPE);
			}
			int arrivedCount = 0;
			if(WeixinConstants.WEIXIN_PART_DESTARRIVED_TYPE.equals(dto.getStateType())) {
				//已处理件数：分批到达时，为至当前为止所有交接到达的件数总和
				arrivedCount = handOverBillService.queryWaybillCountByNoAndDept(entity.getWaybillNo(),destOrgCode);
			} else {
				//已处理件数：到达时，为运单总件数
				arrivedCount = entity.getWaybillPieces().intValue();
			}
			dto.setProcessedPieces(arrivedCount);
			//到达网点编码
			dto.setCustomerPickUpOrgCode(destOrgCode);
			
			dtoList.add(dto);
		}
		//调用接送货接口
		if(dtoList!= null && dtoList.size() > 0) {
			for(WeixinSendDto weixinDto:dtoList) {
				//给接送货推送消息，用于官网微信功能
				weixinSendService.sysnWeixinInfoForWebSiteUnDirectly(weixinDto, weixinDto.getStateType());
			}
		}
	}
	
	/**
	 * 到达确认.
	 * 
	 * @param truckTaskDetailId
	 *            the truck task detail id
	 * @param truckTaskId
	 *            the truck task id
	 * @param vehicleNo
	 *            the vehicle no
	 * @param planArrivalTime
	 *            the plan arrival time
	 * @param truckArriveId
	 *            the truck arrive id
	 * @param beCarLoad
	 *            the be car load
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-22 下午4:55:58
	 * @see com.deppon.foss.module.transfer.arrival.api.server.service.IArrivalService#arrivalConfirm(java.lang.String,
	 *      java.lang.String, java.lang.String, java.util.Date)
	 */
	@Transactional
	@Override
	public void arrivalConfirm(String truckTaskDetailId, String truckTaskId,
			String vehicleNo, Date planArrivalTime, String truckArriveId,
			String beCarLoad,String actualDepartType) {
		// 判断是否有到达的信息
		String arriveId = truckArriveId;
		if (StringUtil.isBlank(truckArriveId)) {
			// 新增一条到达信息
			arriveId = addTruckArrival(vehicleNo, planArrivalTime);
		} else {
			// 更新到达的纸质放行时间
			TruckArrivalEntity truckArrivalEntity = new TruckArrivalEntity();
			truckArrivalEntity.setId(truckArriveId);
			truckArrivalEntity.setManualArriveUserCode(FossUserContext
					.getCurrentUser().getEmployee().getEmpCode());
			truckArrivalEntity.setManualArriveOrgCode(FossUserContext
					.getCurrentDeptCode());
			truckArrivalEntity.setManualArriveTime(new Date());
			arrivalDao.updateTruckArrival(truckArrivalEntity);
		}
		// 只有第一次通过手工到达的记录需要更改下面的状态
		if (StringUtil.isBlank(truckArriveId)) {
			// 任务车辆明细置为发车
			TruckTaskDetailEntity truckTaskDetailEntity = new TruckTaskDetailEntity();
			truckTaskDetailEntity.setId(truckTaskDetailId);
			// 状态置为到达
			truckTaskDetailEntity
					.setStatus(ArrivalConstant.ARRIVAL_VEHICLE_ARRIVED);
			truckTaskDetailEntity.setActualArriveTime(new Date());
			truckTaskDetailEntity.setTruckArriveId(arriveId);
			// 到达类型置为人工到达
			truckTaskDetailEntity
					.setActualArriveType(DepartureConstant.ACTUAL_DEPART_TYPE_MANUAL);
			// 更新任务车辆明细
			departureDao.updateTaskByManual(truckTaskDetailEntity);
			// 更新任务车辆的状态，先查看该条信息是不是最后一条未到达的任务
			ArrivalEntity arrivalEntity = new ArrivalEntity();
			arrivalEntity.setId(truckTaskDetailId);
			arrivalEntity.setTruckTaskId(truckTaskId);
			arrivalEntity
					.setDetailStatus(ArrivalConstant.ARRIVAL_VEHICLE_ARRIVED);
			if (arrivalDao.isLastTask(arrivalEntity) <= 0) {
				// 已经是最后一条了，需要更新任务车辆状态
				truckTaskDetailEntity = new TruckTaskDetailEntity();
				truckTaskDetailEntity.setId(truckTaskId);
				truckTaskDetailEntity
						.setStatus(ArrivalConstant.ARRIVAL_VEHICLE_ARRIVED);
				departureDao.updateTruckTask(truckTaskDetailEntity);
			} else {
				//begin by zyx
				//RTFR2013112601 MANA-227
				//当前车辆任务中的配载单是否为最终到达
				if(beFinallyArrive(truckTaskDetailId)) {
					//查询当前主任务下未到达的子任务
					List<TruckTaskDetailEntity> truckTaskDetails = queryUnArrivalTaskByTaskId(truckTaskId);
					//判断是否所有任务都做了到达(不为0则有任务未做到达)
					//MANA-2170 集配交接单未配载不记录已到达
					int flag = 0;
					for(TruckTaskDetailEntity truckTaskDetail : truckTaskDetails) {
						
						QueryDepartEntity queryEntity = new QueryDepartEntity();
						// 单据级别为有效
						queryEntity.setBillLevel(TaskTruckConstant.BILL_LEVEL_VALID);
						// 交接单类型为集配
						queryEntity.setHandoverType(LoadConstants.HANDOVER_TYPE_LONG_DISTANCE);
						queryEntity.setId(truckTaskDetail.getTruckTaskId());
						queryEntity.setOrigOrgCode(truckTaskDetail.getOrigOrgCode());
						queryEntity.setDestOrgCode(truckTaskDetail.getDestOrgCode());
						List<String> strlist = departureDao.isDistanceHandover(queryEntity);
						if (strlist.size() > 0) {
							flag = 1;
							//集配交接单未配载不能到达
							continue;
						}
						
						truckTaskDetail.setTruckArriveId(arriveId);
						truckTaskDetail.setStatus(ArrivalConstant.ARRIVAL_VEHICLE_ARRIVED);
						truckTaskDetail.setActualArriveTime(new Date());
						truckTaskDetail.setActualArriveType(DepartureConstant.ACTUAL_DEPART_TYPE_FOSS);
						departureDao.updateTaskByManual(truckTaskDetail);
						
						// job表增加一条数据
						TruckActionDetailEntity actionDetail = new TruckActionDetailEntity();
						actionDetail.setId(DepartureConstant.SEQ_TRUCK_ACTION_JOB);
						actionDetail.setBundType(DepartureConstant.JOB_TRUCK_ARRIVAL);
						actionDetail.setTruckTaskDetailId(truckTaskDetail.getId());
						actionDetail.setStatus(DepartureConstant.JOB_NOT_START);
						actionDetail.setCreateTime(DepartureHandle.getCurrentDate());
						actionDetail.setOperatorCode(DepartureHandle.getCurrentUserCode());
						actionDetail.setOperatorName(DepartureHandle.getCurrentUserName());
						departureDao.addTruckActionDetail(actionDetail);
						
					}
					
					//如果所有明细都到达过则更新主任务为已结束
					if(flag == 0) {
						//更新车辆主任务, 此次任务结束
						TruckTaskDetailEntity truckTaskDetailTemp = new TruckTaskDetailEntity();
						truckTaskDetailTemp.setId(truckTaskId);
						truckTaskDetailTemp.setStatus(ArrivalConstant.ARRIVAL_VEHICLE_ARRIVED);
						
						departureDao.updateTruckTask(truckTaskDetailTemp);
					} else {
						//MANA-2170 集配交接单未配载不记录已到达
						//非最终到达，更新状态为中途到达
						truckTaskDetailEntity = new TruckTaskDetailEntity();
						truckTaskDetailEntity.setId(truckTaskId);
						truckTaskDetailEntity
						.setStatus(DepartureConstant.ARRIVAL_VEHICLE_HALFWAY_ARRIVE);
						departureDao.updateTruckTask(truckTaskDetailEntity);
					}
					//end
				} else {
					//非最终到达，更新状态为中途到达
					truckTaskDetailEntity = new TruckTaskDetailEntity();
					truckTaskDetailEntity.setId(truckTaskId);
					truckTaskDetailEntity
					.setStatus(DepartureConstant.ARRIVAL_VEHICLE_HALFWAY_ARRIVE);
					departureDao.updateTruckTask(truckTaskDetailEntity);
				}
				
			}
//			// 判断是否是整车，是的话需要直接入库
//			if (FossConstants.YES.equals(beCarLoad)) {
//				// 整车直接入库，不需要增加job的数据
//				instockBeCarLoad(truckTaskDetailId);
//			} else {
				// job表增加一条数据
				TruckActionDetailEntity actionDetail = new TruckActionDetailEntity();
				actionDetail.setId(DepartureConstant.SEQ_TRUCK_ACTION_JOB);
				actionDetail.setBundType(DepartureConstant.JOB_TRUCK_ARRIVAL);
				actionDetail.setTruckTaskDetailId(truckTaskDetailId);
				actionDetail.setStatus(DepartureConstant.JOB_NOT_START);
				actionDetail.setCreateTime(DepartureHandle.getCurrentDate());
				actionDetail.setOperatorCode(DepartureHandle.getCurrentUserCode());
				actionDetail.setOperatorName(DepartureHandle.getCurrentUserName());
				departureDao.addTruckActionDetail(actionDetail);
//			}
		}
		/**
		 * @desc :调结算接口，记录日志
		 * @author :foss-105795-wqh
		 * @date ：2014-06-03
		 * 
		 * **/
		//获取任务车辆中的配载单
		List<String> vehicleBillNoList= sharedDao.getVehicleAssembByDetail(truckTaskDetailId);
		if(CollectionUtils.isEmpty(vehicleBillNoList)){
			vehicleBillNoList = sharedDao.queryEcsLongBill(truckTaskDetailId);
		}
		
	    if(vehicleBillNoList!=null&&vehicleBillNoList.size()>0){
	    	for(int i=0;i<vehicleBillNoList.size();i++){
	    		String vehicleBillNo=vehicleBillNoList.get(i);
	    		if(StringUtil.isBlank(vehicleBillNo)){
	    			continue;
	    		}
	    		
	    		TruckArriveConfirmDto truckArriveConfirmDto=new TruckArriveConfirmDto();
	    		truckArriveConfirmDto.setHandleNo(vehicleBillNo);
	    		truckArriveConfirmDto.setConfirmTime(new Date());
	    		truckArriveConfirmDto.setConfirmType(SettlementDictionaryConstants.TRUCK_ARRIVE_CONFIRM);
	    		
	    		
				// 封装灰度实体，类型是配载单
				GrayParameterDto parDto = new GrayParameterDto();
				parDto.setSourceBillType(CUBCGrayUtil.SBType.PZ.getName());
				parDto.setSourceBillNos(new String[] { vehicleBillNo });
				VestResponse vestResponse = cubcUtil.getUcbcGrayData(parDto, new Throwable());

				if (CUBCGrayContants.SYSTEM_CODE_FOSS
						.equals(vestResponse.getVestBatchResult().get(0).getVestSystemCode())) {
					// 调结算接口
					try {

						truckArriveConfirmService.truckConfirm(truckArriveConfirmDto);
					} catch (SettlementException e) {
						LOGGER.error("调结算接口异常：" + e.getErrorCode());
						throw new TfrBusinessException("调结算接口异常：" + e.getErrorCode());

					}
				} else {
					String json = com.alibaba.fastjson.JSONObject.toJSONString(truckArriveConfirmDto);
					net.sf.json.JSONObject paramJson = net.sf.json.JSONObject.fromObject(json);
					paramJson.put("empCode", FossUserContext.getCurrentInfo().getEmpCode());
					paramJson.put("empName", FossUserContext.getCurrentInfo().getEmpName());
					paramJson.put("currentDeptName", FossUserContext.getCurrentInfo().getCurrentDeptName());
					paramJson.put("currentDeptCode", FossUserContext.getCurrentInfo().getCurrentDeptCode());
					CubcTruckConfirmArrivalResponse cubcTruckConfirmArrivalResponse = fossToCubcService.truckConfirm(paramJson);
					if (null != cubcTruckConfirmArrivalResponse) {
						if (0 == cubcTruckConfirmArrivalResponse.getResult()) {
							throw new TfrBusinessException("调CUBC接口异常：" + cubcTruckConfirmArrivalResponse.getReason());
						}
					} else {
						throw new TfrBusinessException("调CUBC接口异常");
					}
				}
	    		
	    	}
	    	
	    }
	}
	
	/**
	 * 根据车辆主任务ID查询未到达的车辆任务
	 * @author 163580
	 * @date 2013-12-10 下午2:57:42
	 * @param truckTaskId
	 * @return
	 * @see
	 */
	private List<TruckTaskDetailEntity> queryUnArrivalTaskByTaskId(String truckTaskId) {
		return departureDao.queryUnArrivalTaskByTaskId(truckTaskId);
	}
	
	/**
	 * 当前任务车辆任务下配载单是否最终到达
	 * true最终到达
	 * @author 163580
	 * @date 2013-12-10 上午10:57:36
	 * @param truckTaskDetailId
	 * @return 
	 * @see
	 */
	@Override
	public boolean beFinallyArrive(String truckTaskDetailId) {
		return arrivalDao.beFinallyArrive(truckTaskDetailId) == 0 ? false : true;
	}

	/**
	 * 
	 * 到达时判断是否整车，如果是整车，直接入库
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-3-28 下午2:15:29
	 */
	private void instockBeCarLoad(String truckTaskDetailId) {
		// 先通过车辆任务明细查到所有的运单号和流水号
		WayBillRefershDTO wayBillRefershDTO = new WayBillRefershDTO();
		wayBillRefershDTO.setTruckTaskDetailId(truckTaskDetailId);
		List<WayBillRefershDTO> waylist = sharedDao
				.queryDataForWaybillRefresh(wayBillRefershDTO); 
		// 查询到达ID
		String arrivedId = arrivalDao.queryTruckArriveId(truckTaskDetailId);
		if (StringUtils.isNotEmpty(arrivedId)) {
			// 根据到达id查询操作人等信息
			TruckArrivalEntity truckArrivalEntity = arrivalDao.queryTruckArrivedInfo(arrivedId);
			if (null != truckArrivalEntity) {
				for (WayBillRefershDTO dto : waylist) {
					InOutStockEntity inOutStockEntity = new InOutStockEntity();
					// 运单号
					inOutStockEntity.setWaybillNO(dto.getWaybillNo());
					// 流水号
					inOutStockEntity.setSerialNO(dto.getSerialNo());
					// 操作部门
					inOutStockEntity.setOrgCode(truckArrivalEntity.getCreateOrgCode());
					// 操作员工代码
					inOutStockEntity.setOperatorCode(truckArrivalEntity.getCreateUserCode());
					// 操作员工名称
					inOutStockEntity.setOperatorName(truckArrivalEntity.getCreateUserName());
					// 操作类型，整车入库
					if(FossConstants.YES.equals(dto.getBeCarLoad())){
					inOutStockEntity
							.setInOutStockType(StockConstants.WHOLE_VEHICLE_ARRIVAL_IN_STOCK_TYPE);
					}else{
						inOutStockEntity
						.setInOutStockType(StockConstants.UNLOAD_GOODS_IN_STOCK_TYPE);
					}
					stockService.inStockPC(inOutStockEntity);
					// 更改交接单状态，更改走火路径的状态TODO
					// 获取交接单号
					List<HandoverRefershDTO> handoverList = sharedDao
							.getHandoverByDetail(truckTaskDetailId);
					if (handoverList != null && handoverList.size() > 0) {
						for (HandoverRefershDTO handoverRefershDTO : handoverList) {
							// 更改交接单好为已入库
							handOverBillService.updateHandOverBillStateByNo(
									handoverRefershDTO.getHandoverNo(),
									LoadConstants.HANDOVERBILL_STATE_ALREADY_INSTOCK);
						}
					}
					// 获取配载单号
					List<String> vehicleAssembList = sharedDao
							.getVehicleAssembByDetail(truckTaskDetailId);
					if (vehicleAssembList != null && vehicleAssembList.size() > 0) {
						for (String vehicleAssembleBillNo : vehicleAssembList) {
							// 更改配载单号为已到达
							vehicleAssembleBillService
									.updateVehicleAssembleBillStateByVNo(
											vehicleAssembleBillNo,
											LoadConstants.VEHICLEASSEMBLEBILL_STATE_ALREADY_ARRIVE);
						}
					}
				}
			}
		}
	}

	/**
	 * 增加一条到达信息.
	 * 
	 * @param vehicleNo
	 *            the vehicle no
	 * @param planArrivalTime
	 *            the plan arrival time
	 * @return the string
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-20 下午4:48:13
	 */
	@Override
	public String addTruckArrival(String vehicleNo, Date planArrivalTime) {
		TruckArrivalEntity truckArrivalEntity = new TruckArrivalEntity();
		// 判断已到还是晚到
		if (ArrivalHandle.isOnTime(planArrivalTime)) {
			// 准时到达
			truckArrivalEntity.setStatus(ArrivalConstant.DEPART_ARRIVALED);
		} else {
			// 晚点到达
			truckArrivalEntity.setStatus(ArrivalConstant.DEPART_LATE_ARRIVALED);
		}
		truckArrivalEntity.setId(UUIDUtils.getUUID());
		// 车牌号
		truckArrivalEntity.setVehicleNo(vehicleNo);
		// 放行员编码
		truckArrivalEntity.setManualArriveUserCode(FossUserContext
				.getCurrentUser().getEmployee().getEmpCode());
		// 放行部门
		truckArrivalEntity.setManualArriveOrgCode(FossUserContext
				.getCurrentDeptCode());
		// 放行时间
		truckArrivalEntity.setManualArriveTime(new Date());
		// 创建时间
		truckArrivalEntity.setCreateUserCode(FossUserContext.getCurrentUser()
				.getEmployee().getEmpCode());
		// 创建部门
		truckArrivalEntity.setCreateOrgCode(FossUserContext
				.getCurrentDeptCode());
		// 创建人姓名
		truckArrivalEntity.setCreateUserName(FossUserContext.getCurrentUser()
				.getEmployee().getEmpName());
		// 创建时间
		truckArrivalEntity.setCreateTime(new Date());
		arrivalDao.insertTruckArrival(truckArrivalEntity);
		return truckArrivalEntity.getId();
	}
	
	private OrgAdministrativeInfoEntity querySuperiorOrgByOrgCode(String orgCode) {
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
			throw new TfrBusinessException("获取上级部门失败, 无上级部门");
		}
	}

	/**
	 * Cancle depart.
	 * 
	 * @param operationDTO
	 *            the operation dto
	 */
	@Override
	@Transactional
	public void cancleDepart(OperationDTO operationDTO) {
		// 通过车辆任务明细ID查到该车辆任务下同一出发部门的所有的车辆任务明细
		// 需要更新所有的车辆任务明细
		OperationDTO dto = new OperationDTO();
		dto.setTruckTaskId(operationDTO.getTruckTaskId());
		dto.setOrigOrgCode(operationDTO.getOrigOrgCode());
		// dto.setVehicleStatus(ArrivalConstant.ARRIVAL_VEHICLE_ONTHEWAY);
		List<OperationDTO> taskDetailList = arrivalDao
				.queryTruckDetailByTaskId(dto);
		
		if (taskDetailList != null) {
			for (OperationDTO o : taskDetailList) {
				//MANA-404 控制非出发部门不能取消车辆发车
				//2014年1月17日 10:13:29 zyx
				OrgAdministrativeInfoEntity org = querySuperiorOrgByOrgCode(DepartureHandle.getCurrentOrgCode());
				if(org == null) {
					throw new TfrBusinessException("查询组织所属上级部门失败!!", "");
				}
				if(!StringUtils.equals(o.getOrigOrgCode(), org.getCode())) {
					throw new TfrBusinessException("只有车辆出发部门才能取消发车!", "");
				}
				//end
				
				// 查看是否已经开始卸车
				if (ArrivalConstant.ARRIVAL_VEHICLE_ARRIVED.equals(o
						.getVehicleStatus())) {
					LOGGER.error("已经到达的任务车辆不能取消");
					String truckTaskDetailId = o.getId();
					if(StringUtils.isNotEmpty(truckTaskDetailId)) {
						QueryArrivalEntity queryEntity = new QueryArrivalEntity();
						List<String> ids = new ArrayList<String>();
						ids.add(truckTaskDetailId);
						queryEntity.setIds(ids);
						List<ArrivalEntity> arrivals = arrivalDao.queryBillNos(queryEntity);
						if(CollectionUtils.isNotEmpty(arrivals)) {
							ArrivalEntity arrival = arrivals.get(0);
							String billNos = arrival.getBillNos();
							throw new TfrBusinessException("单据:[" + billNos + "]已经到达不能取消", "");
						}
					}
				}
				if (DepartureConstant.ACTUAL_DEPART_TYPE_GPS.equals(o
						.getActualDepartType())
						|| DepartureConstant.ACTUAL_DEPART_TYPE_PDA.equals(o
								.getActualDepartType())) {
					LOGGER.error("PDA放行或者GPS放行的车辆不能取消,车辆任务明细ID" + o.getId());
					throw new TfrBusinessException("PDA放行或者GPS放行的车辆不能取消", "");
				}
				if (ArrivalConstant.ARRIVAL_VEHICLE_ONTHEWAY.equals(o
						.getVehicleStatus())) {
					if (isUnloading(o.getId())) {
						LOGGER.error("已经开始卸车的任务不能取消");
						throw new TfrBusinessException("已经开始卸车的任务不能取消", "");
					}
					cancleDepart(o.getId(), operationDTO.getTruckTaskId());
				}
			}
		}
	}

	/**
	 * 取消放行，只能对人工确认放行的记录取消放行.
	 * 
	 * @param truckTaskDetailId
	 *            the truck task detail id
	 * @param truckTaskId
	 *            the truck task id
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-21 上午10:19:06
	 * @see com.deppon.foss.module.transfer.arrival.api.server.service.IArrivalService#cancleDepart(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	@Transactional
	public void cancleDepart(String truckTaskDetailId, String truckTaskId) {
		// 将封签置为待出发
		QueryDepartEntity queryDepartEntity = new QueryDepartEntity();
		// 设置取消状态
		queryDepartEntity.setStatus(DepartureConstant.DEPART_STATUS_WAIT);
		queryDepartEntity.setTruckTaskId(truckTaskId);
		departureDao.cancleDepartByTaskId(queryDepartEntity);
		// 任务车辆明细置为发车
		TruckTaskDetailEntity truckTaskDetailEntity = new TruckTaskDetailEntity();
		truckTaskDetailEntity.setId(truckTaskDetailId);
		// 状态置为待放行
		truckTaskDetailEntity
				.setStatus(ArrivalConstant.ARRIVAL_VEHICLE_UNDEPART);
		truckTaskDetailEntity.setActualDepartTime(null);
		// 出发类型置为人工出发确认
		truckTaskDetailEntity.setActualDepartType(null);
		truckTaskDetailEntity.setPlatformDistributeId(null);
		// 更新任务车辆明细
		departureDao.updateTaskDetailCancleDepart(truckTaskDetailEntity);
		// 更新任务车辆的状态，先查看该条信息是不是最后一条未放行的任务
		ArrivalEntity arrivalEntity = new ArrivalEntity();
		arrivalEntity.setId(truckTaskDetailId);
		arrivalEntity.setTruckTaskId(truckTaskId);
		// 查看是不是任务车辆下面的所有的明细的状态都是待出发，最后一条需要置为待出发
		arrivalEntity.setDetailStatus(ArrivalConstant.ARRIVAL_VEHICLE_ONTHEWAY);
		if (arrivalDao.isLastTask(arrivalEntity) <= 0) {
			// 已经是最后一条了，需要更新任务车辆状态
			// 更新任务车辆状态
			truckTaskDetailEntity = new TruckTaskDetailEntity();
			truckTaskDetailEntity.setId(truckTaskId);
			truckTaskDetailEntity
					.setStatus(ArrivalConstant.ARRIVAL_VEHICLE_UNDEPART);
			departureDao.updateTruckTask(truckTaskDetailEntity);
		} else {
			// 中途到达退回去的，更新为在途
			truckTaskDetailEntity = new TruckTaskDetailEntity();
			truckTaskDetailEntity.setId(truckTaskId);
			truckTaskDetailEntity
					.setStatus(ArrivalConstant.ARRIVAL_VEHICLE_ONTHEWAY);
			departureDao.updateTruckTask(truckTaskDetailEntity);
		}
		// job表增加一条数据
		TruckActionDetailEntity actionDetail = new TruckActionDetailEntity();
		actionDetail.setId(DepartureConstant.SEQ_TRUCK_ACTION_JOB);
		actionDetail.setBundType(DepartureConstant.JOB_TRUCK_DEPART_CANCLE);
		actionDetail.setTruckTaskDetailId(truckTaskDetailId);
		actionDetail.setStatus(DepartureConstant.JOB_NOT_START);
		actionDetail.setCreateTime(DepartureHandle.getCurrentDate());
		actionDetail.setOperatorCode(DepartureHandle.getCurrentUserCode());
		actionDetail.setOperatorName(DepartureHandle.getCurrentUserName());
		departureDao.addTruckActionDetail(actionDetail);
		tfrJobTodoService.addJobTodo(truckTaskDetailId, 
				BusinessSceneConstants.BUSINESS_SCENE_CANCEL_TRUCK_DEPARTURE, 
				new String[]{BusinessGoalContants.BUSINESS_GOAL_EXPRESS100}, 
				actionDetail.getCreateTime(),
				actionDetail.getOperatorCode());
		//调用ECS系统接口开关
		if (configurationParamsService.queryTfrSwitch4Ecs()) {
			/** 新增同步给快递系统 车辆任务信息 通知任务 */
			Log.error("调用插入临时表，用于推送数据给悟空");
			// 根据车辆任务明细ID查询出悟空交接单
			List<WkHandOverBillEntity> wkHandoverbillList = wkBillAddTfrNotifyService
					.queryWkHandOverBillByTruckTaskDetailId(truckTaskDetailId);
			// 已交接单为单位插入到临时表
			for (WkHandOverBillEntity entity : wkHandoverbillList) {
				String paramJson = entity.getHandoverBillNo() + "," + entity.getOperationOrgCode();
				String currentUserInfo = DepartureHandle.getCurrentUserCode() + ","
						+ DepartureHandle.getCurrentUserName() + "," + DepartureHandle.getCurrentOrgCode();
				tfrNotifyService.addTfrNotifyEntity(new TfrNotifyEntity(UUIDUtils.getUUID(),
						NotifyWkConstants.NOTIFY_TYPE_CANCEL_TRUCK_DEPARTURE, truckTaskDetailId,
						BusinessSceneConstants.WK_HANDORVERBILL_CANCEL_HAVE_DEPART, currentUserInfo, paramJson));
			}
		}

	}

	/**
	 * Cancle arrive. 取消单条任务车辆明细
	 * 
	 * @param operationDTO
	 *            the operation dto
	 */
	@Override
	@Transactional
	public void cancleArrive(OperationDTO operationDTO) {
		// 通过车辆任务明细ID查到该车辆任务下同一出发部门的所有的车辆任务明细
		// 需要更新所有的车辆任务明细
		OperationDTO dto = new OperationDTO();
		dto.setTruckTaskId(operationDTO.getTruckTaskId());
		dto.setDestOrgCode(operationDTO.getDestOrgCode());
		List<OperationDTO> taskDetailList = arrivalDao.queryTruckDetailByTaskId(dto);
		if (taskDetailList != null) {
			for (OperationDTO o : taskDetailList) {
				//MANA-404 控制非出发部门不能取消车辆发车
				//2014年1月17日 10:13:29 zyx
				OrgAdministrativeInfoEntity org = querySuperiorOrgByOrgCode(DepartureHandle.getCurrentOrgCode());
				if(org == null) {
					throw new TfrBusinessException("查询组织所属上级部门失败!!", "");
				}
				if(!StringUtils.equals(o.getDestOrgCode(), org.getCode())) {
					//判定营业部是否有对应快递分部，若有查询出该分部的到达车辆信息
					ExpressBranchSalesDeptEntity  branchSalesDeptEntity = new ExpressBranchSalesDeptEntity();
					branchSalesDeptEntity.setSalesDeptCode(org.getCode());
					ExpressBranchSalesDeptEntity branchSalesDept= expressBranchSalesDeptService.
							queryByExpressBranchSalesDeptCode(branchSalesDeptEntity);
					if(branchSalesDept !=null){
						if(!StringUtils.equals(o.getDestOrgCode(), branchSalesDept.getExpressBranchCode())){
							throw new TfrBusinessException("只有车辆到达部门才能取消到达!", "");
						} 
					}else{
						throw new TfrBusinessException("只有车辆到达部门才能取消到达!", "");
					}
				}
				//end
				
				// 查看是否已经开始卸车
				if (isUnloading(o.getId())) {
					LOGGER.error("已经开始卸车的任务不能取消");
					throw new TfrBusinessException("已经开始卸车的任务不能取消", "");
				}
				if (DepartureConstant.ACTUAL_DEPART_TYPE_GPS.equals(o
						.getActualArriveType())
						|| DepartureConstant.ACTUAL_DEPART_TYPE_PDA.equals(o
								.getActualArriveType())) {
					LOGGER.error("PDA放行或者GPS到达的车辆不能取消,车辆任务明细ID" + o.getId());
					throw new TfrBusinessException("PDA到达或者GPS到达的车辆不能取消", "");
				}
				cancleArrive(o.getId(), operationDTO.getTruckTaskId(),
						o.getTruckArriveId(), o.getBeCarLoad());
			}
		}
	}

	/**
	 * 取消到达.
	 * 
	 * @param truckTaskDetailId
	 *            the truck task detail id
	 * @param truckTaskId
	 *            the truck task id
	 * @param truckArriveId
	 *            the truck arrive id
	 * @param beCarLoad
	 *            the be car load
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-5 上午11:15:59
	 * @see com.deppon.foss.module.transfer.arrival.api.server.service.IArrivalService#cancleArrive(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public void cancleArrive(String truckTaskDetailId, String truckTaskId,
			String truckArriveId, String beCarLoad) {
		// 任务车辆明细置为发车
		TruckTaskDetailEntity truckTaskDetailEntity = new TruckTaskDetailEntity();
		truckTaskDetailEntity.setId(truckTaskDetailId);
		// 状态置为待放行
		truckTaskDetailEntity
				.setStatus(ArrivalConstant.ARRIVAL_VEHICLE_ONTHEWAY);
		truckTaskDetailEntity.setActualArriveTime(null);
		// 出发类型置为人工出发确认
		truckTaskDetailEntity.setActualArriveType(null);
		// 更新任务车辆明细
		truckTaskDetailEntity.setTruckArriveId(null);
		truckTaskDetailEntity.setPlatformDistributeId(null);
		truckTaskDetailEntity.setManualArriveTime(null);
		departureDao.updateTaskDetailCancleArrive(truckTaskDetailEntity);
		// 更新任务车辆状态
		truckTaskDetailEntity = new TruckTaskDetailEntity();
		truckTaskDetailEntity.setId(truckTaskId);
		truckTaskDetailEntity
				.setStatus(ArrivalConstant.ARRIVAL_VEHICLE_ONTHEWAY);
		departureDao.updateTruckTask(truckTaskDetailEntity);
		// 车辆到达记录置为取消状态
		if (null != truckArriveId) {
			TruckArrivalEntity truckArrivalEntity = new TruckArrivalEntity();
			truckArrivalEntity.setId(truckArriveId);
			truckArrivalEntity.setStatus(ArrivalConstant.DEPART_LATE_CANCLED);
			arrivalDao.updateTruckArrival(truckArrivalEntity);
		}
		// 判断是否是整车，是的话需要直接出库
		if (FossConstants.YES.equals(beCarLoad)) {
			// 先通过车辆任务明细查到所有的运单号和流水号
			WayBillRefershDTO wayBillRefershDTO = new WayBillRefershDTO();
			wayBillRefershDTO.setTruckTaskDetailId(truckTaskDetailId);
			List<WayBillRefershDTO> waylist = sharedDao
					.queryDataForWaybillRefresh(wayBillRefershDTO);
			for (WayBillRefershDTO dto : waylist) {
				InOutStockEntity inOutStockEntity = new InOutStockEntity();
				// 运单号
				inOutStockEntity.setWaybillNO(dto.getWaybillNo());
				// 流水号
				inOutStockEntity.setSerialNO(dto.getSerialNo());
				// 操作部门
				inOutStockEntity.setOrgCode(FossUserContext
						.getCurrentDeptCode());
				// 操作员工代码
				inOutStockEntity.setOperatorCode(FossUserContext
						.getCurrentUser().getEmployee().getEmpCode());
				// 操作员工名称
				inOutStockEntity.setOperatorName(FossUserContext
						.getCurrentUser().getEmployee().getEmpName());
				// 操作类型，整车入库
				if(FossConstants.YES.equals(dto.getBeCarLoad())){
					inOutStockEntity
							.setInOutStockType(StockConstants.WHOLE_VEHICLE_OUT_STOCK_TYPE);
				}else{
					inOutStockEntity
					.setInOutStockType(StockConstants.LOAD_GOODS_OUT_STOCK_TYPE);
				}
				stockService.outStockPC(inOutStockEntity);
			}
		} 
//		else {
		//MANA-4044、RULE-A3	整车到达货物入库，取消整车到达后，撤销货物入库状态；同时交接单、配载单状态记录为已出发。
		// job表增加一条数据
		TruckActionDetailEntity actionDetail = new TruckActionDetailEntity();
		actionDetail.setId(DepartureConstant.SEQ_TRUCK_ACTION_JOB);
		actionDetail
				.setBundType(DepartureConstant.JOB_TRUCK_ARRIVAL_CANCLE);
		actionDetail.setTruckTaskDetailId(truckTaskDetailId);
		actionDetail.setStatus(DepartureConstant.JOB_NOT_START);
		actionDetail.setCreateTime(DepartureHandle.getCurrentDate());
		actionDetail.setOperatorCode(DepartureHandle.getCurrentUserCode());
		actionDetail.setOperatorName(DepartureHandle.getCurrentUserName());
		departureDao.addTruckActionDetail(actionDetail);
		tfrJobTodoService.addJobTodo(truckTaskDetailId, 
				BusinessSceneConstants.BUSINESS_SCENE_CANCEL_TRUCK_ARRIVAL, 
				new String[]{BusinessGoalContants.BUSINESS_GOAL_EXPRESS100}, 
				actionDetail.getCreateTime(),
				actionDetail.getOperatorCode());
		//调用ECS系统接口开关
		if (configurationParamsService.queryTfrSwitch4Ecs()) {
			/** 新增同步给快递系统 车辆任务信息 通知任务 */
			Log.error("调用插入临时表，用于推送数据给悟空");
			// 根据车辆任务明细ID查询出悟空交接单
			List<WkHandOverBillEntity> wkHandoverbillList = wkBillAddTfrNotifyService
					.queryWkHandOverBillByTruckTaskDetailId(truckTaskDetailId);
			// 已交接单为单位插入到临时表
			for (WkHandOverBillEntity entity : wkHandoverbillList) {
				String paramJson = entity.getHandoverBillNo() + "," + entity.getOperationOrgCode();
				String currentUserInfo = DepartureHandle.getCurrentUserCode() + ","
						+ DepartureHandle.getCurrentUserName() + "," + DepartureHandle.getCurrentOrgCode();
				tfrNotifyService.addTfrNotifyEntity(new TfrNotifyEntity(UUIDUtils.getUUID(),
						NotifyWkConstants.NOTIFY_TYPE_CANCEL_TRUCK_ARRIVAL, truckTaskDetailId,
						BusinessSceneConstants.WK_HANDORVERBILL_CANCEL_HAVE_ARRIVE, currentUserInfo, paramJson));
			}
		}

//		}
		/**
		 * @desc :调结算接口，记录日志
		 * @author :foss-105795-wqh
		 * @date ：2014-06-03
		 * 
		 * **/
		//获取任务车辆中的配载单
		List<String> vehicleBillNoList= sharedDao.getVehicleAssembByDetail(truckTaskDetailId);
	    if(vehicleBillNoList!=null&&vehicleBillNoList.size()>0){
	    	for(int i=0;i<vehicleBillNoList.size();i++){
	    		String vehicleBillNo=vehicleBillNoList.get(i);
	    		if(StringUtil.isBlank(vehicleBillNo)){
	    			continue;
	    		}
	    		
	    		TruckArriveConfirmDto truckArriveConfirmDto=new TruckArriveConfirmDto();
	    		truckArriveConfirmDto.setHandleNo(vehicleBillNo);
	    		truckArriveConfirmDto.setConfirmTime(new Date());
	    		truckArriveConfirmDto.setConfirmType(SettlementDictionaryConstants.TRUCK_ARRIVE_UNCONFIRM);
	    		
				// 封装灰度实体，类型是配载单
				GrayParameterDto parDto = new GrayParameterDto();
				parDto.setSourceBillType(CUBCGrayUtil.SBType.PZ.getName());
				parDto.setSourceBillNos(new String[] { vehicleBillNo });
				VestResponse vestResponse = cubcUtil.getUcbcGrayData(parDto, new Throwable());

				if (CUBCGrayContants.SYSTEM_CODE_FOSS
						.equals(vestResponse.getVestBatchResult().get(0).getVestSystemCode())) {
					// 调结算接口
					try {

						truckArriveConfirmService.truckConfirm(truckArriveConfirmDto);
					} catch (SettlementException e) {
						LOGGER.error("调结算接口异常：" + e.getErrorCode());
						throw new TfrBusinessException("调结算接口异常：" + e.getErrorCode());

					}
				} else {
					String json = com.alibaba.fastjson.JSONObject.toJSONString(truckArriveConfirmDto);
					net.sf.json.JSONObject paramJson = net.sf.json.JSONObject.fromObject(json);
					paramJson.put("empCode", FossUserContext.getCurrentInfo().getEmpCode());
					paramJson.put("empName", FossUserContext.getCurrentInfo().getEmpName());
					paramJson.put("currentDeptName", FossUserContext.getCurrentInfo().getCurrentDeptName());
					paramJson.put("currentDeptCode", FossUserContext.getCurrentInfo().getCurrentDeptCode());
					// 调CUBC接口
					CubcTruckConfirmArrivalResponse cubcTruckConfirmArrivalResponse = fossToCubcService
							.truckConfirm(paramJson);
					if (null != cubcTruckConfirmArrivalResponse) {
						if (0 == cubcTruckConfirmArrivalResponse.getResult()) {
							throw new TfrBusinessException("调CUBC接口异常：" + cubcTruckConfirmArrivalResponse.getReason());
						}
					} else {
						throw new TfrBusinessException("调CUBC接口异常");
					}
				}
			}
	    	
	    }
	}

	/**
	 * 判断目标部门是不是当前部门.
	 * 
	 * @param orgCode
	 *            the org code
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-5 下午4:45:43
	 * @see com.deppon.foss.module.transfer.arrival.api.server.service.IArrivalService#validOrgCode(java.lang.String)
	 */
	@Override
	public void validOrgCode(String orgCode) {
		if (StringUtil.isBlank(orgCode)) {
			throw new TfrBusinessException(ErrorConstant.DEPART_ORG_NOT_NULL,
					"");
		}
		if (!orgCode.equals(DepartureHandle.getCurrentOrgCode())) {
			throw new TfrBusinessException(ErrorConstant.THIS_ORG_HAS_NOT_OWN,
					"");
		}
	}

	/**
	 * 校验是否做过封签.
	 * 
	 * @param orgCode
	 *            the org code
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-5 下午4:45:43
	 * @see com.deppon.foss.module.transfer.arrival.api.server.service.IArrivalService#validOrgCode(java.lang.String)
	 */
	@Override
	public Long validSail(String truckTaskDetailId) {
		if (arrivalDao.validSail(truckTaskDetailId) <= 0) {
			return null;
		} else {
			return (long) 0;
		}
	}
	/** 
	 * @Title: encodeFileName 
	 * @Description: 将文件名转成UTF-8编码以防止乱码
	 * @param string
	 * @return    
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskQueryService#encodeFileName(java.lang.String)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-4-24下午4:14:37
	 */ 
	@Override
	public String encodeFileName(String fileName) {
		try {
			return URLEncoder.encode(fileName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new StockException("将文件名转成UTF-8编码时出错","");
		}
	}
	/** 
	 * @Description: 处理标签
	 * @param string
	 * @return    
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskQueryService#encodeFileName(java.lang.String)
	 * @author: ibm-liubinbin
	 * @throws 
	 * Date:2013-4-24下午4:14:37
	 */ 
	@Override
	public void handleLabeledGoods(List<String> detailIds) {
		for(String str:detailIds){
			List<TodoWhenArriveTruckEntity> list = arrivalDao.queryTodoWhenArriveTruck(str);
			for(TodoWhenArriveTruckEntity entity:list){
				ChangeLabelGoodsEntity changeLabelGoods = new ChangeLabelGoodsEntity();
				changeLabelGoods.setId(UUIDUtils.getUUID());
				//设置 单据编号
				if(entity.getVehicleassembleNo()!=null){
					changeLabelGoods.setBillNo(entity.getVehicleassembleNo());
				}else{
					changeLabelGoods.setBillNo(entity.getHandoverNo());
				}
				//设置 运单号
				changeLabelGoods.setWaybillNo(entity.getWaybillNo());
				//设置 流水号
				changeLabelGoods.setSerialNo(entity.getSerialNo());
				//设置 更换原因
				changeLabelGoods.setChangeReason(ChangeLabelGoodsConstants.CHANGE_REASON_BY_MODIFY);
				//设置 发现环节
				changeLabelGoods.setDiscoverTache(ChangeLabelGoodsConstants.FIND_SCENE_UNLOAD);
				//设置 发现时间
				changeLabelGoods.setDiscoverTime(entity.getDiscoverTime());
				//设置 处理状态
				changeLabelGoods.setHandleStatus(ChangeLabelGoodsConstants.HANDLESTATUS_UNTREATED);
				//设置 发现部门编号
				changeLabelGoods.setOrgCode(entity.getDestOrgCode());
				//设置 发现部门名称
				changeLabelGoods.setOrgName(entity.getDestOrgName());
				//插入更换标签
				pdaUnloadTaskDao.insertChangeLabelGoodsEntity(changeLabelGoods);
			}
		}
	}
	/** 
	 * @Title: exportLoadWayBillByTaskNo 
	 * @Description: 导出车辆出发到达明细
	 * @param taskNo
	 * @return    
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskQueryService#exportLoadWayBillByTaskNo(java.lang.String)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-4-24下午4:14:37
	 */ 
	@Override
	public InputStream exportTruckDepartOrArriveByTaskNo(String ids) {
		InputStream excelStream = null;
		QueryArrivalEntity queryEntity  =new QueryArrivalEntity();
		ids = ids.replaceAll(" ", "");
		queryEntity.setIds(Arrays.asList(ids.split(",")));
		List<ArrivalEntity>  list = queryArrivalGrid(queryEntity,Integer.MAX_VALUE,Integer.MIN_VALUE);
		List<ArrivalEntity>  billNos =  arrivalDao.queryBillNos(queryEntity);
		List<List<String>> rowList = new ArrayList<List<String>>();
		for(ArrivalEntity arrivalEntity : list){
			//每行的列List
			List<String> columnList = new ArrayList<String>();
			//车牌号
			columnList.add(arrivalEntity.getVehicleNo());
			//用来判断是否存在交接单、配载单
			boolean flag = false;
			//交接单/配载单
			for(ArrivalEntity entity : billNos){
				if(arrivalEntity.getId().equals(entity.getId())){
					//交接单、配载单
					flag = true;
					columnList.add(entity.getBillNos());
				}
			}
			if(!flag){
				columnList.add("");
			}
			
			//线路
			columnList.add(arrivalEntity.getLineNo());
			
			//出发部门
			columnList.add(arrivalEntity.getOrigOrgName());
			
			//到达部门
			columnList.add(arrivalEntity.getDestOrgName());
			
			//出发时间
			columnList.add(ArrivalHandle.getDateStr(arrivalEntity.getActualDepartTime()));
			
			//到达时间
			columnList.add(ArrivalHandle.getDateStr(arrivalEntity.getActualArrivalTime()));
			
			//出发确认时间
			columnList.add(ArrivalHandle.getDateStr(arrivalEntity.getManualConfirmDepartTime()));
			
			//到达确认时间
			columnList.add(ArrivalHandle.getDateStr(arrivalEntity.getManualArriveTime()));
			
			//手工放行时间
			columnList.add(ArrivalHandle.getDateStr(arrivalEntity.getManualDepartTime()));
			
			//保安（PDA）时间
			columnList.add(ArrivalHandle.getDateStr(arrivalEntity.getPdaDepartTime()));
			
			//预计到达时间
			columnList.add(ArrivalHandle.getDateStr(arrivalEntity.getPlanArrivalTime()));
			
			//入场时间
			columnList.add(arrivalEntity.getArrCheckTime()==null?null:ArrivalHandle.getDateStr(arrivalEntity.getArrCheckTime()));

			//月台号
			columnList.add(arrivalEntity.getPlatformCode()==null?"":arrivalEntity.getPlatformCode());
			
			//到达情况
			if(ArrivalConstant.DEPART_ARRIVALED.equals(arrivalEntity.getArrivalStatus())){
				columnList.add("已到");
			}/*else if(ArrivalConstant.DEPART_NOT_ARRIVALED.equals(arrivalEntity.getArrivalStatus())){
				columnList.add("未到");//和else合并-sonar-352203
			}*/else if(ArrivalConstant.DEPART_LATE_ARRIVALED.equals(arrivalEntity.getArrivalStatus())){
				columnList.add("晚到");
			}else{
				columnList.add("未到");
			}
			
			//证件包情况
			columnList.add(arrivalEntity.getCertificteBagStatus());
			
			//月台分配
			columnList.add(arrivalEntity.getPlatformStatus());
			
			//未结清金额
			BigDecimal fee = arrivalEntity.getFee() == null ? BigDecimal.ZERO : arrivalEntity.getFee();
			columnList.add(fee + "");
			
			//装车人
			columnList.add(arrivalEntity.getLoader());
			
			//车辆归属类型
			if("Company".equals(arrivalEntity.getVehicleOwnerType())){
				columnList.add("公司车");
			}else{
				columnList.add("外请车");
			}
			
			//是否整车
			if(FossConstants.YES.equals(arrivalEntity.getBeCarLoad())){
				columnList.add("是");
			}else{
				columnList.add("否");
			}
			
			rowList.add(columnList);
		}
		String[] rowHeads = { "车牌号", "交接单/配载单", "线路", "出发部门", "到达部门", "出发时间",
				"到达时间", "出发确认时间", "到达确认时间", "手工放行时间", "保安（PDA）时间", "预计到达时间","入场时间","月台号",
				"到达情况", "证件包情况", "月台分配", "未结清金额", "装车人", "车辆归属类型", "是否整车" };// 定义表头

		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSheetName("车辆出发到达明细");
		exportSetting.setSize(ConstantsNumberSonar.SONAR_NUMBER_5000);
		ExportResource exportResource  = new ExportResource();
		exportResource.setHeads(rowHeads);
		exportResource.setRowList(rowList);
		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		excelStream = objExporterExecutor.exportSync(exportResource, exportSetting);
        return excelStream;
	}
	/**
	 * ********************************* **********.
	 */
	/******************* 到达底层接口 ***************************/
	private IArrivalDao arrivalDao;
	/**
	 * ***************** 出发底层接口 **************************.
	 */
	private IDepartureDao departureDao;
	/**
	 * ****************** 线路服务接口 *************************.
	 */
	private ILineService lineService;
	/**
	 * ********************快递线路服务接口*******************************
	 */
	private IExpressLineService expresslineService;
	
	/**发车标准service**/
	private IDepartureStandardService departureStandardService;
	
	/**
	 * ****************** 车辆服务接口 *************************.
	 */
	IDepartureService departureService;
	private IVehicleService vehicleService;
	/**
	 * ******************* 走货路径服务接口 ************************.
	 */
	private ICalculateOptimalPlatformService calculateOptimalPlatformService;
	/**
	 * ******************* 月台服务接口 ************************.
	 */
	private IPlatformDispatchService platformDispatchService;
	/**
	 * ******************* 装卸车标准 ************************.
	 */
	/** ****车辆信息******. */
	private IVehicleInfoDao vehicleInfoDao;
	private IPDAUnloadTaskDao pdaUnloadTaskDao;

	IVehicleSealService vehicleSealService;
	/**
	 * The load and unload efficiency vehicle complex service.
	 */
	ILoadAndUnloadEfficiencyVehicleComplexService loadAndUnloadEfficiencyVehicleComplexService;
	/**
	 * ************************ 更新配载单 *************************.
	 */
	private IVehicleAssembleBillService vehicleAssembleBillService; // 更新配载单
	/**
	 * ******************* 库存服务接口 ************************.
	 */
	private IStockService stockService;
	/** The shared dao. */
	private ISharedDao sharedDao;
	
	/**处理待办service**/
	private ITodoActionService todoActionService;
	
	/**
	 * @param todoActionService the todoActionService to set
	 */
	public void setTodoActionService(ITodoActionService todoActionService) {
		this.todoActionService = todoActionService;
	}
	/**
	 * 外请车时效费用service
	 */
	private IOutsideVehicleChargeService outsideVehicleChargeService;
	/**
	 * 设置 ******************************* ************.
	 * 
	 * @param arrivalDao
	 *            the new *************** ******** ********************
	 */
	public void setArrivalDao(IArrivalDao arrivalDao) {
		this.arrivalDao = arrivalDao;
	}

	/**
	 * 设置 *****************出发底层接口******** ******************.
	 * 
	 * @param departureDao
	 *            the new *************** **出发底层接口 *************** ***********
	 */
	public void setDepartureDao(IDepartureDao departureDao) {
		this.departureDao = departureDao;
	}

	/**
	 * ************************ 更新交接单 *************************.
	 */
	private IHandOverBillService handOverBillService; // 更新交接单
	/** The business monitor service. */
	private IBusinessMonitorService businessMonitorService;

	/**
	 * 设置 *******************走货路径服务接口**** ********************.
	 * 
	 * @param calculateOptimalPlatformService
	 *            the new *************** ****走货路径服务接口 *********** *************
	 */
	public void setCalculateOptimalPlatformService(
			ICalculateOptimalPlatformService calculateOptimalPlatformService) {
		this.calculateOptimalPlatformService = calculateOptimalPlatformService;
	}

	/**
	 * 设置 *******************月台服务接口****** ******************.
	 * 
	 * @param platformDispatchService
	 *            the new *************** ****月台服务接口 ************* ***********
	 */
	public void setPlatformDispatchService(
			IPlatformDispatchService platformDispatchService) {
		this.platformDispatchService = platformDispatchService;
	}

	/**
	 * 设置 ******************线路服务接口******* ******************.
	 * 
	 * @param lineService
	 *            the new *************** ***线路服务接口 ************** ***********
	 */
	public void setLineService(ILineService lineService) {
		this.lineService = lineService;
	}

	/**
	 * 设置 ******************快递线路服务接口******* ******************.
	 * 
	 * @param expresslineService
	 *            the new *************** ***快递线路服务接口 ************** ***********
	 */
	public void setExpresslineService(IExpressLineService expresslineService) {
		this.expresslineService = expresslineService;
	}

	/**
	 * 设置 ******************车辆服务接口******* ******************.
	 * 
	 * @param vehicleService
	 *            the new *************** ***车辆服务接口 ************** ***********
	 */
	public void setVehicleService(IVehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}

	/**
	 * 设置 *******************库存服务接口****** ******************.
	 * 
	 * @param stockService
	 *            the new *************** ****库存服务接口 ************* ***********
	 */
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}

	/**
	 * Sets the shared dao.
	 * 
	 * @param sharedDao
	 *            the new shared dao
	 */
	public void setSharedDao(ISharedDao sharedDao) {
		this.sharedDao = sharedDao;
	}

	/**
	 * 设置 ******************* 装卸车标准************************.
	 * 
	 * @param loadAndUnloadEfficiencyVehicleComplexService
	 *            the new ******************* 装卸车标准 ****************** ******
	 */
	public void setLoadAndUnloadEfficiencyVehicleComplexService(
			ILoadAndUnloadEfficiencyVehicleComplexService loadAndUnloadEfficiencyVehicleComplexService) {
		this.loadAndUnloadEfficiencyVehicleComplexService = loadAndUnloadEfficiencyVehicleComplexService;
	}

	/**
	 * 取消到达时先判断是否已卸车.
	 * 
	 * @param truckTaskDetailId
	 *            the truck task detail id
	 * @return true, if is unloading
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-22 下午2:18:08
	 * @see com.deppon.foss.module.transfer.arrival.api.server.service.IArrivalService#isUnloading(java.lang.String)
	 */
	@Override
	public boolean isUnloading(String truckTaskDetailId) {
		List<String> list = arrivalDao.isUnloading(truckTaskDetailId);
		if (list == null || list.size() <= 0) {
			return false;
		}
		return true;
	}

	/**
	 * Sets the hand over bill service.
	 * 
	 * @param handOverBillService
	 *            the new hand over bill service
	 */
	public void setHandOverBillService(IHandOverBillService handOverBillService) {
		this.handOverBillService = handOverBillService;
	}

	/**
	 * Sets the vehicle assemble bill service.
	 * 
	 * @param vehicleAssembleBillService
	 *            the new vehicle assemble bill service
	 */
	public void setVehicleAssembleBillService(
			IVehicleAssembleBillService vehicleAssembleBillService) {
		this.vehicleAssembleBillService = vehicleAssembleBillService;
	}

	/**
	 * Sets the business monitor service.
	 * 
	 * @param businessMonitorService
	 *            the new business monitor service
	 */
	public void setBusinessMonitorService(
			IBusinessMonitorService businessMonitorService) {
		this.businessMonitorService = businessMonitorService;
	}

	public void setVehicleInfoDao(IVehicleInfoDao vehicleInfoDao) {
		this.vehicleInfoDao = vehicleInfoDao;
	}

	public void setDepartureService(IDepartureService departureService) {
		this.departureService = departureService;
	}

	public void setVehicleSealService(IVehicleSealService vehicleSealService) {
		this.vehicleSealService = vehicleSealService;
	}

	public void setPdaUnloadTaskDao(IPDAUnloadTaskDao pdaUnloadTaskDao) {
		this.pdaUnloadTaskDao = pdaUnloadTaskDao;
	}

	public void setDepartureStandardService(
			IDepartureStandardService departureStandardService) {
		this.departureStandardService = departureStandardService;
	}

	/**
	 * @param outsideVehicleChargeService the outsideVehicleChargeService to set
	 */
	public void setOutsideVehicleChargeService(
			IOutsideVehicleChargeService outsideVehicleChargeService) {
		this.outsideVehicleChargeService = outsideVehicleChargeService;
	}

	/**
	 * @param orgAdministrativeInfoComplexService the orgAdministrativeInfoComplexService to set
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	
	private IExpressBranchSalesDeptService expressBranchSalesDeptService;
	
	public final void setExpressBranchSalesDeptService(
			IExpressBranchSalesDeptService expressBranchSalesDeptService) {
		this.expressBranchSalesDeptService = expressBranchSalesDeptService;
	}

	/**
	 * 提供给接送货的接口用来校验运单是否配载是否到达
	 * arrival为真时则校验是否到达
	 * @author 163580
	 * @date 2014-2-18 上午11:22:12
	 * @param waybillNo
	 * @param arrival
	 * @return 
	 * @see com.deppon.foss.module.transfer.arrival.api.server.service.IArrivalService#validateWaybillNo(java.lang.String, boolean)
	 */
	@Override
	public String validateWaybillNo(String waybillNo, boolean arrival) {
		if(StringUtils.isEmpty(waybillNo)) {
			throw new TfrBusinessException("运单号不能为空!","");
		}
		//校验运单是否配载
		Long count = arrivalDao.validateWaybillNoAssemble(waybillNo);
		if(count <= 0) {
			//该运单不存在配载
			return "该整车运单没做配载，不能结清货款，请联系配载部门或者出发部门解决。";
		}
		if(arrival) {
			//校验运单是否存在未到达的数据
			count = arrivalDao.validateWaybillNoArrival(waybillNo);
			if(count > 0) {
				//存在未到达
				return "该整车未录入到达，不能结清货款。";
			}
		}
		return null;
	}

	/**
	 * @author 163580
	 * @date 2014-6-27 下午7:17:29
	 * @param id 
	 * @see com.deppon.foss.module.transfer.arrival.api.server.service.IArrivalService#handleTodo(java.lang.String)
	 */
	@Override
	public String handleTodo(String id) {
		TruckTaskDetailEntity truckTaskDetailEntity = new TruckTaskDetailEntity();
		truckTaskDetailEntity.setId(id);
		List<TruckTaskDetailEntity> trucktaskTaskDetails = departureDao.queryTruckTaskDetail(truckTaskDetailEntity);
		if(CollectionUtils.isEmpty(trucktaskTaskDetails)) {
			throw new TfrBusinessException("参数错误， 请刷新页面后再试!");
		}
		TruckTaskDetailEntity truckTaskDetail = trucktaskTaskDetails.get(0);
		if(!StringUtils.equals(truckTaskDetail.getStatus(), ArrivalConstant.ARRIVAL_VEHICLE_ARRIVED)) {
			throw new TfrBusinessException("只有已到达的才能处理!");
		}
		List<String> handoverBillNoList = arrivalDao.queryBillByTaskDetailId(id);
		if(CollectionUtils.isEmpty(handoverBillNoList)) {
			throw new TfrBusinessException("当前任务下没有有效的单据!");
		}
		TodoActionDto todoActionDto = new TodoActionDto();
		todoActionDto.setHandoverBillNoList(handoverBillNoList);
		ResultDto resultDto = todoActionService.updateTodoByHandoverBillNo(todoActionDto);
		String msg = "处理完毕!";
		if(resultDto != null) {
			msg = resultDto.getMsg();
		}
		LOGGER.error(msg);
		return handoverBillNoList.get(0);
	}

	/**
	 * 根据出发、到达部门获取线路
	 * @param sourceCode
	 * @param targetCode
	 * @return
	 * @date 2014-4-25
	 * @author Ouyang
	 */
	private LineEntity getLine(String sourceCode, String targetCode) {

		LOGGER.error("sourceCode = " + sourceCode + "targetCode = " + targetCode);
		if (StringUtils.isEmpty(sourceCode) || StringUtils.isEmpty(targetCode)) {
			return null;
		}

		LineEntity condition = new LineEntity();
		condition.setOrginalOrganizationCode(sourceCode);
		condition.setDestinationOrganizationCode(targetCode);
		condition.setValid(FossConstants.YES);
		condition.setActive(FossConstants.ACTIVE);

		// 根据两点查询线路
		List<LineEntity> lineList = lineService
				.querySimpleLineListByCondition(condition);

		if (CollectionUtils.isEmpty(lineList)) {
			return null;
		}

		// 若只有一条，则返回
		if (lineList.size() == 1) {
			return lineList.get(0);
		}

		// 始发线路，取汽运线路；运作到运作，取专线线路，到达线路两部门间只有一条
		String lineType = null;
		String transType = null;
		for (int i = 0, size = lineList.size(); i < size; i++) {
			LineEntity line = lineList.get(i);
			transType = line.getTransType();
			lineType = line.getLineType();
			if (DictionaryValueConstants.BSE_LINE_TRANSTYPE_QIYUN
					.equals(transType)
					|| DictionaryValueConstants.BSE_LINE_TYPE_ZHUANXIAN
							.equals(lineType)) {
				return line;
			}
			
			//数据正常的情况，上面的部分一定会取到正确的线路
			if(i == size -1){
				return line;
			}
		}

		return null;
	}
	
	/**
	 * 2014-09-10  200978
	 * 分离快递和零担的线路服务接口
	 * 根据出发、到达部门获取快递线路
	 * @Author: 200978  xiaobingcheng
	 * 2014-9-10
	 * @param sourceCode
	 * @param targetCode
	 * @return
	 */
	private ExpressLineEntity getExpressLine(String sourceCode, String targetCode) {

		LOGGER.error("sourceCode = " + sourceCode + "targetCode = " + targetCode);
		if (StringUtils.isEmpty(sourceCode) || StringUtils.isEmpty(targetCode)) {
			return null;
		}

		ExpressLineEntity condition = new ExpressLineEntity();
		condition.setOrginalOrganizationCode(sourceCode);
		condition.setDestinationOrganizationCode(targetCode);
		condition.setValid(FossConstants.YES);
		condition.setActive(FossConstants.ACTIVE);

		// 根据两点查询线路
		List<ExpressLineEntity> expressLineList = expresslineService
				.querySimpleLineListByCondition(condition);

		if (CollectionUtils.isEmpty(expressLineList)) {
			return null;
		}

		// 若只有一条，则返回
		if (expressLineList.size() == 1) {
			return expressLineList.get(0);
		}

		// 始发线路，取汽运线路；运作到运作，取专线线路，到达线路两部门间只有一条
		String lineType = null;
		String transType = null;
		for (int i = 0, size = expressLineList.size(); i < size; i++) {
			ExpressLineEntity line = expressLineList.get(i);
			transType = line.getTransType();
			lineType = line.getLineType();
			if (DictionaryValueConstants.BSE_LINE_TRANSTYPE_QIYUN
					.equals(transType)
					|| DictionaryValueConstants.BSE_LINE_TYPE_ZHUANXIAN
							.equals(lineType)) {
				return line;
			}
			
			//数据正常的情况，上面的部分一定会取到正确的线路
			if(i == size -1){
				return line;
			}
		}

		return null;
	}
	
	/**
	 * 车辆出发时，根据出发时间和车辆任务明细对应线路的时效，获取任务明细的预计到达时间
	 * @param entity
	 * @return
	 * @date 2014-4-25
	 * @author Ouyang
	 */
	private Date updatePlanArriveTimeWhenDepart(TruckTaskDetailEntity entity, Date departTime) {

		// 出发时间
		Date actualDepartTime = departTime;
		actualDepartTime = actualDepartTime == null ? new Date() : actualDepartTime;
		
		Long aging = 0L;

		// 车辆任务明细的出发、到达部门
		String sourceCode = entity.getOrigOrgCode();
		String targetCode = entity.getDestOrgCode();

		LOGGER.error("开始获取线路信息");
		// 根据出发、到达部门获取线路
		LineEntity line = getLine(sourceCode, targetCode);
		ExpressLineEntity expressLine = null;
		/**
		 * 2014-09-10 update 200978
		 * 分离零担和快递的线路服务接口
		 */
		LOGGER.error("获取线路信息结束");
		if (line == null) {
			LOGGER.error("开始获取快递线路信息");
			//如果零担线路为null，则查询快递线路
			expressLine = getExpressLine(sourceCode, targetCode);
			LOGGER.error("获取快递线路信息结束");
			if(expressLine == null){
				LOGGER.error("线路为空, 返回出发时间!");
				return actualDepartTime;
			}
		}
		// 线路类型；始发线路、运作到运作、到达线路
		String lineSort =null;
		if(line!=null){
			lineSort = line.getLineSort();
		}else{
			lineSort = expressLine.getLineSort();
		}

		if (DictionaryValueConstants.BSE_LINE_SORT_TRANSFER.equals(lineSort)) {
			String vehicleOwnerType = entity.getVehicleOwnerType();
			// 运作到运作线路，公司车取卡车时效,外请车取普车时效
			if(line!=null){
				aging = DepartureConstant.TRUCK_TYPE_OWN.equals(vehicleOwnerType) ? line
						.getFastAging() : line.getCommonAging();
			}else{
				aging = DepartureConstant.TRUCK_TYPE_OWN.equals(vehicleOwnerType) ? expressLine
						.getFastAging() : expressLine.getCommonAging();
			}
		} else {
			// 线路虚拟编码
			String lineVirtualCode = null;
			if(line != null){
				lineVirtualCode = line.getVirtualCode();
			}else{
				lineVirtualCode = expressLine.getVirtualCode();
			}
			if (StringUtils.isNotEmpty(lineVirtualCode)) {
				// 根据线路虚拟编码获取对应发车标准
				LOGGER.error("开始获取发车标准");
				List<DepartureStandardEntity> departStandards = departureStandardService
						.queryDepartureStandardListByLineVirtualCode(lineVirtualCode);
				LOGGER.error("结束获取发车标准");
				if (CollectionUtils.isNotEmpty(departStandards)) {
					// 取第一班发车标准，根据到达出发时间，到达时间计算时效(千分之小时)
					DepartureStandardEntity departStandard = departStandards
							.get(0);

					DateFormat dateFormat = new SimpleDateFormat("HHmm");

					// 出发时间
					Date leaveTime = null;
					// 到达时间
					Date arriveTime = null;
					try {
						leaveTime = dateFormat.parse(departStandard
								.getLeaveTime());
						arriveTime = dateFormat.parse(departStandard
								.getArriveTime());
					} catch (ParseException e) {
						LOGGER.error("departStandard.getLeaveTime() = " + departStandard.getLeaveTime());
						LOGGER.error("departStandard.getArriveTime() = " + departStandard.getArriveTime());

						LOGGER.error(e.getMessage());
						return actualDepartTime;
					}

					// 天数
					Long arriveDay = departStandard.getArriveDay() == null ? 0L
							: departStandard.getArriveDay();
					// 时效(千分之小时)
					aging = (new BigDecimal(arriveTime.getTime()
							- leaveTime.getTime()).divide(new BigDecimal(
									ConstantsNumberSonar.SONAR_NUMBER_60 * ConstantsNumberSonar.SONAR_NUMBER_60), 0, BigDecimal.ROUND_HALF_UP)).longValue()
							+ arriveDay * ConstantsNumberSonar.SONAR_NUMBER_24 * ConstantsNumberSonar.SONAR_NUMBER_1000;
				}
			}
		}

		LOGGER.error("开始计算预计到达时间");
		//预计到达时间为出发时间+时效
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(actualDepartTime);
		calendar.add(Calendar.SECOND,
				new Long(aging * ConstantsNumberSonar.SONAR_NUMBER_60 * ConstantsNumberSonar.SONAR_NUMBER_60 / ConstantsNumberSonar.SONAR_NUMBER_1000).intValue());
		LOGGER.error("计算预计到达时间结束");
		
		Date planArriveTime = calendar.getTime();
		
		// 设置预计到达时间
		return planArriveTime;
	}

	/**
	 * <p>执行Job整车入库</p> 
	 * @author 316759-wangruipeng
	 * @date 2016-07-14 下午05:03:09
	 * @param truckTaskDetailId
	 * @return
	 * @see
	 */
	@Override
	public void pushForWholeVehicle(String truckTaskDetailId) {
		LOGGER.info("整车入库开始");
		// 判断传入参数是否为空
		if (StringUtils.isBlank(truckTaskDetailId)) {
			throw new TfrBusinessException("车辆任务明细ID为空！");
		}
		try {
			instockBeCarLoad(truckTaskDetailId);
		} catch (Exception e) {
			// 整车入库报错
			throw new TfrBusinessException(e.getMessage());
		}
		
	}

	public IFossToCubcService getFossToCubcService() {
		return fossToCubcService;
	}

	public void setFossToCubcService(IFossToCubcService fossToCubcService) {
		this.fossToCubcService = fossToCubcService;
	}

	/**
	 * 
	 *提供接口给结算，根据运单和最终配载部门查询车辆到达时间
	 * @author alfred
	 * @date 2013-9-10 上午9:33:49
	 * @param waybillNO
	 * @param destOrgCode
	 * @return 
	 * @see com.deppon.foss.module.transfer.arrival.api.server.service.IArrivalService#queryArrivalByWaybillAndDestOrg(java.lang.String, java.lang.String)
	 */
	/*@Override
	public List<Date> queryArrivalByWaybillAndDestOrg(String waybillNO,
			String destOrgCode) {
		// TODO Auto-generated method stub
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("waybillNO", waybillNO);
		condition.put("destOrgCode", destOrgCode);
		return arrivalDao.queryArrivalByWaybillAndDestOrg(condition);
	}*/
	
	
}