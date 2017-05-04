/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-predeliver
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/service/impl/WayBillNoLocusService.java
 * 
 * FILE NAME        	: WayBillNoLocusService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IAirAgencyDeptDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILdpAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IUserService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverbillService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IWayBillNoLocusService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.WayBillNoLocusConstant;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WayBillLocusDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WayBillNoLocusDTO;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillDeliveryDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.WayBillNoLocusException;
import com.deppon.foss.module.pickup.predeliver.server.process.HandleTrajectoryResponse;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.sign.api.server.service.ISerialSignResultService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignChangeService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignDetailService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SerialSignResultEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IPdaAppInfoDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPdaScanService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillPendingService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.OmsOrderEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PdaAppInfoEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PdaScanEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillAcHisPdaEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPictureEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.PdaScanQueryDto;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.trackings.api.server.service.ISynTrackingServer;
import com.deppon.foss.module.trackings.api.shared.domain.DMPSynTrackingEntity;
import com.deppon.foss.module.transfer.airfreight.api.define.AirfreightConstants;
import com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirQueryFlightArriveDao;
import com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirWaybillDao;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirHandOverBillService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirQueryModifyPickupbillService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirQueryModifyTfrPickupBillService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirTrackFlightService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirUnshippedGoodsService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirHandOverBillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTransPickupbillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirUnshippedGoodsEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.OppLocusEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirHandOverBillDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirQueryFlightArriveDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirTrackFlightDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirTransPickupBillDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirUnshippedGoodsDto;
import com.deppon.foss.module.transfer.departure.api.handle.DepartureHandle;
import com.deppon.foss.module.transfer.departure.api.server.service.ITrackingService;
import com.deppon.foss.module.transfer.departure.api.shared.domain.LoadDetailEntity;
import com.deppon.foss.module.transfer.departure.api.shared.dto.HandoverBillDTO;
import com.deppon.foss.module.transfer.load.api.server.service.IExpressPackageService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.partialline.api.server.service.IAgentWaybillTrackService;
import com.deppon.foss.module.transfer.partialline.api.server.service.IExternalBillService;
import com.deppon.foss.module.transfer.partialline.api.server.service.ILdpExternalBillService;
import com.deppon.foss.module.transfer.partialline.api.server.service.ILdpExternalBillTrackService;
import com.deppon.foss.module.transfer.partialline.api.server.service.IPrintAgentWaybillService;
import com.deppon.foss.module.transfer.partialline.api.shared.define.PartiallineConstants;
import com.deppon.foss.module.transfer.partialline.api.shared.define.TrackingEventTypeEnum;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.InternationalTrackingEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.PrintAgentWaybillRecordEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.ExternalBillDto;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpExternalBillDto;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpExternalBillTrackDto;
import com.deppon.foss.module.transfer.pda.api.shared.define.TransferPDADictConstants;
import com.deppon.foss.module.transfer.pda.api.shared.domain.CreateDeliveryReceiptEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.WaybillInfoEntity;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.unload.api.server.service.ISortingScanService;
import com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskService;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.foss.module.pickup.waybill.api.server.service.IOmsOrderService;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressPickService;

/**
 * 
 * 查询运单执行轨迹（供接口调用）
 * 
 * @author foss-liubinbin(for IBM)
 * @date 2012-12-24 下午6:45:54
 */
public class WayBillNoLocusService implements IWayBillNoLocusService {
	private static final Logger LOGGER = LoggerFactory.getLogger(WayBillNoLocusService.class);
	
	/**
	 * Oms系统订单处理类
	 */
	private IOmsOrderService omsOrderService;

	public void setOmsOrderService(IOmsOrderService omsOrderService) {
		this.omsOrderService = omsOrderService;
	}

	private IPDAExpressPickService pdaExpressPickService;


	public void setPdaExpressPickService(
			IPDAExpressPickService pdaExpressPickService) {
		this.pdaExpressPickService = pdaExpressPickService;
	}
	
	/**
	 * 查询外请车service接口
	 */
	private ILeasedVehicleService LeasedVehicleService;
	
	public void setLeasedVehicleService(ILeasedVehicleService leasedVehicleService) {
		LeasedVehicleService = leasedVehicleService;
	}

	/**
	 * 组织信息 Service接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 通知客户Service
	 */
	private INotifyCustomerService notifyCustomerService;
	/**
	 * 运单状态服务接口
	 */
	private IActualFreightService actualFreightService;
	/**
	 * 到达联管理接口
	 */
	private IArriveSheetManngerService arriveSheetManngerService;
	/**
	 * 派送排单Service接口
	 */
	private IDeliverbillService deliverbillService;
	/**
	 * 合票清单
	 */
	private IAirQueryModifyPickupbillService airQueryModifyPickupbillService;
	/**
	 * 空运中转清单
	 */
	private IAirQueryModifyTfrPickupBillService airQueryModifyTfrPickupBillService;
	/**
	 * 正单交接单
	 */
	private IAirHandOverBillService airHandOverBillService;
	/**
	 * 查询签收变更结果
	 */
	private ISignChangeService signChangeService;

	/**
	 * 查询空运正单信息
	 */
	private IAirTrackFlightService airTrackFlightService;
	/**
	 * 外发单信息
	 */
	private IExternalBillService externalBillService;
	/**
	 * 快递代理外发信息
	 */
	private ILdpExternalBillService ldpExternalBillService;
	/**
	 * 空运拉货
	 */
	private IAirUnshippedGoodsService airUnshippedGoodsService;
	/**
	 * PDA暂存
	 */
	private IWaybillPendingService waybillPendingService;
	
	/**
	 * 快递代理信息服务接口
	 */
	private ILdpAgencyDeptService ldpAgencyDeptService; 
	/**
	 * 快递代理轨迹服务接口
	 */
	private ILdpExternalBillTrackService ldpExternalBillTrackService;
	
	/**
	 * 应付单服务.
	 */
	private IBillPayableService billPayableService;
	
	/**
	 * 运单快递服务
	 */
	private IWaybillExpressService waybillExpressService;
	
	/**
	 * 外发流水签收服务接口   foss-sunyanfei 2015-11-04
	 */
	private ISerialSignResultService serialSignResultService;
	
	public void setSerialSignResultService(
			ISerialSignResultService serialSignResultService) {
		this.serialSignResultService = serialSignResultService;
	}

	private  IAgentWaybillTrackService agentWaybillTrackService;
	
	public void setAgentWaybillTrackService(
			IAgentWaybillTrackService agentWaybillTrackService) {
		this.agentWaybillTrackService = agentWaybillTrackService;
	}

	/**
	 * 营业部外发轨迹
	 */
	private IPrintAgentWaybillService printAgentWaybillService;
	/**查询空运到达 200968 2015-08-14 */
	private IAirQueryFlightArriveDao airQueryFlightArriveDao;
	
	
	public void setAirQueryFlightArriveDao(
			IAirQueryFlightArriveDao airQueryFlightArriveDao) {
		this.airQueryFlightArriveDao = airQueryFlightArriveDao;
	}

	/**航空正单dao zwd 200968*/
	private IAirWaybillDao airWayBillDao;
	
	
	public void setAirWayBillDao(IAirWaybillDao airWayBillDao) {
		this.airWayBillDao = airWayBillDao;
	}

	/**
	 * 行政组织 zwd 2015-08-12
	 */
	private IAdministrativeRegionsService administrativeRegionsService;
	
	public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}

	/*根据运单号查询运单信息 zwd 200968 2015-08-08*/
	private IWaybillDao waybillDao;
	
	//空运代理网点 zwd 2015-08-12
    private IAirAgencyDeptDao airAgencyDeptDao;
    
	
	public void setAirAgencyDeptDao(IAirAgencyDeptDao airAgencyDeptDao) {
		this.airAgencyDeptDao = airAgencyDeptDao;
	}

	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}

	public void setAirTrackFlightService(IAirTrackFlightService airTrackFlightService) {
		this.airTrackFlightService = airTrackFlightService;
	}

	public void setAirUnshippedGoodsService(IAirUnshippedGoodsService airUnshippedGoodsService) {
		this.airUnshippedGoodsService = airUnshippedGoodsService;
	}

	/************** 必须输入运单号的错误吗 **********/
	private static final String WAYBILL_NO_NOT_INPUT = "WAYBILL_NO_NOT_INPUT";

	/************** 查询不到相应的运单信息 **********/
	private static final String WAYBILL_NO_NOT_FIND = "WAYBILL_NO_NOT_FIND";
	private static final String WAYBILL_NO_IS_RETURN = "转货/返货中，新单号：";

	/**
	 * 用户信息
	 */
	private IUserService userService;

	/**
	 * 员工信息
	 */
	private IEmployeeService employeeService;

	/**
	 * productService
	 */
	private IProductService productService;

	/**
	 * 运单管理接口
	 */
	IWaybillManagerService waybillManagerService;
	/**
	 * 运单轨迹服务跟踪
	 */
	ITrackingService trackingService;

	/**
	 * 运单签收结果service
	 */
	IWaybillSignResultService waybillSignResultService;

	/**
	 * 卸车
	 */
	IUnloadTaskService unloadTaskService;
	
   
	
	/**
	 * 上分拣 zwd 200968
	 */
	private ISortingScanService sortingScanService ;
	/**
	 * 上分拣 zwd 200968
	 * @param sortingScanService
	 */
	public void setSortingScanService(ISortingScanService sortingScanService) {
		this.sortingScanService = sortingScanService;
	}
	/**
	 * 包信息管理 zwd  200968
	 */
	private IExpressPackageService expressPackageService;
	/**
	 * 包信息管理 zwd
	 * @author 200968
	 * @param expressPackageService
	 */
	public void setExpressPackageService(
			IExpressPackageService expressPackageService) {
		this.expressPackageService = expressPackageService;
	}

	/**
	 * 签收明细service
	 */
	 ISignDetailService signDetailService;
	 
	//注入零担扫描的dao
	private IPdaAppInfoDao pdaAppInfoDao;
	
	//扫描接口注入
	private IPdaScanService pdaScanService;

	public void setPdaAppInfoDao(IPdaAppInfoDao pdaAppInfoDao) {
		this.pdaAppInfoDao = pdaAppInfoDao;
	}

	/**
 /**
	   * 同步DMP大件家装到FOSS PC端综合页面
	  */
	private ISynTrackingServer synTrackingServer;
	public void setSynTrackingServer(ISynTrackingServer synTrackingServer) {
		this.synTrackingServer = synTrackingServer;
	}
	public void setPdaScanService(IPdaScanService pdaScanService) {
		this.pdaScanService = pdaScanService;
	}	/**
	 * 
	 * 运单轨迹接口（供官网调用）
	 * 
	 * @param waybillNo 运单号
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 上午9:59:07
	 */
	@Override
	public List<WayBillNoLocusDTO> getWayBillNoLocus(String waybillNo) throws WayBillNoLocusException {
		return getWayBillNoLocus(waybillNo, WayBillNoLocusConstant.INVOKING_SOURCE_WEBSITE);
	}

	/**
	 * 
	 * 运单轨迹接口（供官网、综合、PDA调用）
	 * 
	 * @param waybillNo 运单号
	 * @param invokingSource 调用来源
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 上午9:59:07
	 */
	@Override
	public List<WayBillNoLocusDTO> getWayBillNoLocus(String waybillNo, String invokingSource) throws WayBillNoLocusException {
		// 验证是否输入运单号
		if (StringUtil.isBlank(waybillNo)) {
			throw new WayBillNoLocusException(WAYBILL_NO_NOT_INPUT, "运单号必须输入");
		}
		WaybillEntity waybillEntity = queryWaybillEntityByNo(waybillNo);
		if (waybillEntity == null) {
			throw new WayBillNoLocusException(WAYBILL_NO_NOT_FIND, "查询不到相应的运单信息");
		}
		List<WayBillNoLocusDTO> dtoList = new ArrayList<WayBillNoLocusDTO>();
		// 开单
		dtoList.addAll(getWayBillNoLocusFromPKPCreate(waybillEntity));
		// 出发、到达
		dtoList.addAll(getWayBillNoLocusFromTFR(waybillEntity, null, false, invokingSource));
		if (!StringUtil.equals(invokingSource, WayBillNoLocusConstant.INVOKING_SOURCE_WEBSITE)) {
			dtoList.addAll(getWayBillNoLocusFromAir(waybillEntity, null));
		}
		// 获取运单提货通知信息列表
		dtoList.addAll(getWayBillNoLocusFromNotify(waybillEntity));
		// 派送
		dtoList.addAll(getWayBillNoLocusFromDelivery(waybillEntity, invokingSource));
		// 派送拉回、正常签收、异常签收、部分签收
		if(!StringUtil.equals(invokingSource, WayBillNoLocusConstant.INVOKING_SOURCE_FOSS)){
			WaybillSignResultEntity entity = new WaybillSignResultEntity();
			entity.setWaybillNo(waybillNo);
			entity.setActive(FossConstants.ACTIVE);
			WaybillSignResultEntity newEntity = waybillSignResultService.queryWaybillSignResultByWaybillNo(entity);
			if(newEntity!=null && StringUtil.equals(newEntity.getSignSituation(),ArriveSheetConstants.SITUATION_UNNORMAL_ABANDONGOODS)){
				//update by foss-231434-bieyexiong 非FOSS综合异常弃货，理赔后，异常弃货推送规轨迹
				// 获取理赔应付单 --根据运单号集合和单据类型集合查询应付单信息
				List<BillPayableEntity> billPayables = billPayableService.queryByWaybillNosAndByBillTypes(
								Arrays.asList(waybillNo),
								Arrays.asList(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM));
				//(需求DN201603140026只针对零担)非FOSS综合弃货签收并且是零担时，查询理赔应付单，存在时推送轨迹
				if(!waybillExpressService.onlineDetermineIsExpressByProductCode(waybillEntity.getProductCode(),null)
						&& CollectionUtils.isNotEmpty(billPayables)){
					dtoList.addAll(getWayBillNoLocusFromSignInfo(waybillEntity,null));
				}
			}else{
				//非FOSS综合非异常弃货推送轨迹
				dtoList.addAll(getWayBillNoLocusFromSignInfo(waybillEntity,null));
			}
		}else{
			//FOSS综合任何情况下推送签收信息
			dtoList.addAll(getWayBillNoLocusFromSignInfo(waybillEntity,null));
		}
		if (StringUtil.equals(invokingSource, WayBillNoLocusConstant.INVOKING_SOURCE_FOSS)) {
			// 综合查询调用
			// 外发单

			// 反签收信息
			dtoList.addAll(getWayBillNoLocusFromSignRfc(waybillEntity));
		}
		// 终止
		dtoList.addAll(getWayBillNoLocusFromdeliveryAborted(waybillEntity));

		for (WayBillNoLocusDTO wayBillNoLocusDTO : dtoList) {// 操作类型转成名称
			if (dtoList != null && dtoList.size() > 0 && wayBillNoLocusDTO.getOperateType() != null) {
				wayBillNoLocusDTO.setOperateTypeName(WayBillNoLocusConstant.map.get(wayBillNoLocusDTO.getOperateType()));
				wayBillNoLocusDTO.setCurrentStatus(WayBillNoLocusConstant.currentStatusMap.get(wayBillNoLocusDTO.getOperateType()));
			} else {
				LOGGER.info("没有符合条件的记录");
			}
		}
		return dtoList;
	}

	/**
	 * 
	 * PDA查询货物接口
	 * 
	 * @param waybillNo
	 * @return List<WayBillLocusDto>
	 * @author ibm-wangfei
	 * @date Jan 11, 2013 4:15:26 PM
	 */
	@Override
	public WayBillLocusDto getWayBillLocusForPda(String waybillNo) {
		// 验证是否输入运单号
		if (StringUtil.isBlank(waybillNo)) {
			throw new WayBillNoLocusException("运单号必须输入");
		}
		WaybillEntity waybillEntity = queryWaybillEntityByNo(waybillNo);
		if (waybillEntity == null) {
			throw new WayBillNoLocusException("查询不到相应的运单信息");
		}
		WayBillLocusDto wayBillLocusDto = new WayBillLocusDto();

		// 运单号
		wayBillLocusDto.setWaybillNo(waybillNo);
		// 货物名称
		wayBillLocusDto.setGoodsName(waybillEntity.getGoodsName());
		// 目的站
		wayBillLocusDto.setTargetOrgCode(waybillEntity.getTargetOrgCode());
		// 提货方式
		if (StringUtil.isNotBlank(waybillEntity.getReceiveMethod())) {
			// 汽运
			String receiveName = DictUtil.rendererSubmitToDisplay(waybillEntity.getReceiveMethod(), DictionaryConstants.PICKUP_GOODS);
			if (StringUtil.isNotBlank(receiveName)) {
				// 空运
				receiveName = DictUtil.rendererSubmitToDisplay(waybillEntity.getReceiveMethod(), DictionaryConstants.PICKUP_GOODS_AIR);
			}
			wayBillLocusDto.setReceiveMethod(receiveName);
		}
		// 运输性质
		ProductEntity producTypeEntity = productService.getProductByCache(waybillEntity.getProductCode(), new Date());
		wayBillLocusDto.setProductCode(producTypeEntity == null ? "" : producTypeEntity.getName());
		// 货物总件数
		wayBillLocusDto.setGoodsQtyTotal(waybillEntity.getGoodsQtyTotal());
		// 货物总重量
		wayBillLocusDto.setGoodsWeightTotal(waybillEntity.getGoodsWeightTotal());
		// 货物总体积
		wayBillLocusDto.setGoodsVolumeTotal(waybillEntity.getGoodsVolumeTotal());

		// 运单轨迹接口
		List<WayBillNoLocusDTO> wayBillNoLocusDTOList = getWayBillNoLocus(waybillNo);
		// 处理运单轨迹列表并返回瓶装好的DOM 参照邓夫伟的运行轨迹
		HandleTrajectoryResponse.handleTrajectoryResponse(wayBillNoLocusDTOList);
		for (WayBillNoLocusDTO dto : wayBillNoLocusDTOList) {
			LOGGER.info(dto.getOperateTime() + "_" + dto.getNotes());
		}
		// 货物追踪信息.
		wayBillLocusDto.setWayBillNoLocusDTOList(wayBillNoLocusDTOList);

		return wayBillLocusDto;
	}

	/**
	 * 按件查询运单轨迹
	 * 
	 * @param waybillNo 运单号
	 * @param serialNo 流水号
	 */
	@Override
	public List<WayBillNoLocusDTO> getWayBillNoLocusBySerialNo(String waybillNo, String serialNo) {
		CreateDeliveryReceiptEntity deliveryReceiptEntity = new CreateDeliveryReceiptEntity();
		// 验证是否输入运单号
		if (StringUtil.isBlank(waybillNo)) {
			throw new WayBillNoLocusException(WAYBILL_NO_NOT_INPUT, "运单号必须输入");
		}
		WaybillEntity waybillEntity = queryWaybillEntityByNo(waybillNo);
		if (serialNo != null) {
			// 通过运单号流水号查询时，没有该流水号，报错
			if (unloadTaskService.validateWaybillNoAndSerialNo(waybillNo, serialNo) <= 0) {
				return null;
				// throw new WayBillNoLocusException("查询不到相应的流水信息",
				// "查询不到相应的流水信息");
			}
		}
		if (waybillEntity == null) {
			throw new WayBillNoLocusException(WAYBILL_NO_NOT_INPUT, "运单号必须输入");
		}
		List<WayBillNoLocusDTO> dtoList = new ArrayList<WayBillNoLocusDTO>();
		//326027--判断运单是否为零担电子运单
		OmsOrderEntity  omsOrderEntity = omsOrderService.queryOmsOrderByWaybillNo(waybillNo);
		if(null != omsOrderEntity){
			//根据运单查询交接单信息
			List<CreateDeliveryReceiptEntity> list=pdaExpressPickService.queryLTLPackHandoverbill(waybillNo);
			//判断集合是否为空
			if(list.size()>0){
				deliveryReceiptEntity = list.get(0);
				if(deliveryReceiptEntity!=null)
				dtoList.addAll(getWayBillNoLocusFromLTLElectronic(deliveryReceiptEntity,waybillNo));
			}
		}else{ 
			// 开单
			dtoList.addAll(getWayBillNoLocusFromPKPCreate(waybillEntity));
		}
		// 出发、到达
		dtoList.addAll(getWayBillNoLocusFromTFR(waybillEntity, serialNo, false, WayBillNoLocusConstant.INVOKING_SOURCE_FOSS));
		dtoList.addAll(getWayBillNoLocusFromAir(waybillEntity, null));
		// 获取运单提货通知信息列表
		dtoList.addAll(getWayBillNoLocusFromNotify(waybillEntity));
		// 派送
		dtoList.addAll(getWayBillNoLocusFromDelivery(waybillEntity, WayBillNoLocusConstant.INVOKING_SOURCE_FOSS));
		// 派送拉回、正常签收、异常签收、部分签收
		dtoList.addAll(getWayBillNoLocusFromSignInfo(waybillEntity,serialNo));
		// 终止
		dtoList.addAll(getWayBillNoLocusFromdeliveryAborted(waybillEntity));

		for (WayBillNoLocusDTO wayBillNoLocusDTO : dtoList) {// 操作类型转成名称
			if (dtoList != null && dtoList.size() > 0 && wayBillNoLocusDTO.getOperateType() != null) {
				wayBillNoLocusDTO.setOperateTypeName(WayBillNoLocusConstant.map.get(wayBillNoLocusDTO.getOperateType()));
				wayBillNoLocusDTO.setCurrentStatus(WayBillNoLocusConstant.currentStatusMap.get(wayBillNoLocusDTO.getOperateType()));
			} else {
				LOGGER.info("没有符合条件的记录");
			}
		}
		return dtoList;
	}

	/**
	 * 按件查询运单轨迹(供综合调用)
	 * 
	 * @param waybillNo 运单号
	 * @param serialNo 流水号
	 */
	@SuppressWarnings("unused")
	@Override
	public List<WayBillNoLocusDTO> getWayBillNoLocusForBse(String waybillNo) {
		// 验证是否输入运单号
		if (StringUtil.isBlank(waybillNo)) {
			throw new WayBillNoLocusException(WAYBILL_NO_NOT_INPUT, "运单号必须输入");
		}
		WaybillEntity waybillEntity = queryWaybillEntityByNo(waybillNo);
		if (waybillEntity == null) {
			throw new WayBillNoLocusException(WAYBILL_NO_NOT_FIND, "查询不到相应的运单信息");
		}
		List<WayBillNoLocusDTO> dtoList = new ArrayList<WayBillNoLocusDTO>();
		CreateDeliveryReceiptEntity deliveryReceiptEntity = new CreateDeliveryReceiptEntity();
		WaybillPictureEntity waybillPictureEntity=new WaybillPictureEntity();
		waybillPictureEntity.setWaybillNo(waybillNo);
		System.out.println(waybillNo);
		waybillPictureEntity.setActive("Y");
		//查询图片开单信息
		waybillPictureEntity=waybillPendingService.queryWaybillPictureByEntity(waybillPictureEntity);
		//非图片开单轨迹
		if(null==waybillPictureEntity){
			//326027--判断运单是否为零担电子运单
			OmsOrderEntity  omsOrderEntity = omsOrderService.queryOmsOrderByWaybillNo(waybillNo);
			if(omsOrderEntity!=null){
				//根据运单查询交接单信息
				List<CreateDeliveryReceiptEntity> list=pdaExpressPickService.queryLTLPackHandoverbill(waybillNo);
				if(list.size()>0){
					deliveryReceiptEntity = list.get(0);
					if(deliveryReceiptEntity!=null)
					dtoList.addAll(getWayBillNoLocusFromLTLElectronic(deliveryReceiptEntity,waybillNo));
				}
			}else {
			// PDA暂存信息  
			dtoList.addAll(getWayBillNoLocusFromPDAPending(waybillNo));
			// 开单  
			dtoList.addAll(getWayBillNoLocusFromPKPCreate(waybillEntity, WayBillNoLocusConstant.INVOKING_SOURCE_FOSS));
			}
		}else{
			//图片开单轨迹
			try {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date lineTime=format.parse("2015-06-30 14:00:00");
				Date createTime=format.parse(format.format(waybillPictureEntity.getCreateTime()));
				if(createTime.after(lineTime)){
						//图片开单但是上传时间在上线之后，走新的图片开单轨迹
					    //图片开单轨迹
					    dtoList.addAll(getWayBillNoLocusFromPKPCreatePic2(waybillEntity, WayBillNoLocusConstant.INVOKING_SOURCE_FOSS,waybillPictureEntity));
						if(waybillPictureEntity.getClearEmp()!=null || waybillPictureEntity.getClearDept()!=null){
							//外场补录重量体积时轨迹
			               if(StringUtil.equals(waybillPictureEntity.getIsException(), "Y")){
			            		if(waybillEntity.getGoodsVolumeTotal() == null || waybillEntity.getGoodsVolumeTotal().compareTo(new BigDecimal(0)) <= 0
										||  waybillEntity.getGoodsWeightTotal() == null || waybillEntity.getGoodsWeightTotal().compareTo(new BigDecimal(0)) <= 0){
			            			  // 外场补录重量体积时产生异常时轨迹
						   			  dtoList.addAll(getWayBillNoLocusFromPKPMakeupWeightVolume1(waybillEntity, WayBillNoLocusConstant.INVOKING_SOURCE_FOSS,waybillPictureEntity));
					   				  if(waybillEntity.getPendingType().equals(WaybillConstants.WAYBILL_STATUS_PENDING_ACTIVE) || 
									      waybillEntity.getPendingType().equals(WaybillConstants.WAYBILL_STATUS_PC_ACTIVE)){
					   					  dtoList.addAll(getWayBillNoLocusFromPKPCreateWaybillPic(waybillEntity, WayBillNoLocusConstant.INVOKING_SOURCE_FOSS));
					   				 }
			            	   }else{
					   				 dtoList.addAll(getWayBillNoLocusFromPKPMakeupWeightVolume1(waybillEntity, WayBillNoLocusConstant.INVOKING_SOURCE_FOSS,waybillPictureEntity));
					   				 dtoList.addAll(getWayBillNoLocusFromPKPCreateWaybillPic(waybillEntity, WayBillNoLocusConstant.INVOKING_SOURCE_FOSS));
			            	   }
			               }else{
			            	  // 外场补录重量体积时未产生异常时轨迹
			 				 dtoList.addAll(getWayBillNoLocusFromPKPMakeupWeightVolume(waybillEntity, WayBillNoLocusConstant.INVOKING_SOURCE_FOSS,waybillPictureEntity));
			 				 dtoList.addAll(getWayBillNoLocusFromPKPCreateWaybill(waybillEntity, WayBillNoLocusConstant.INVOKING_SOURCE_FOSS,waybillPictureEntity));  
			               }
						}else{
							//营业部直接补录运单（含重量体积时）轨迹
							dtoList.addAll(getWayBillNoLocusFromPDAPendingPic(waybillNo,waybillPictureEntity));
							  if(waybillEntity.getPendingType().equals(WaybillConstants.WAYBILL_STATUS_PENDING_ACTIVE) || 
								waybillEntity.getPendingType().equals(WaybillConstants.WAYBILL_STATUS_PC_ACTIVE)){
							    dtoList.addAll(getWayBillNoLocusFromPKPCreatePic(waybillEntity, WayBillNoLocusConstant.INVOKING_SOURCE_FOSS));
							  }
							 }
				}else{
					  //图片开单但是上传时间在上线之前，走原来的非图片开单轨迹
					  dtoList.addAll(getWayBillNoLocusFromPDAPending(waybillNo));
					  dtoList.addAll(getWayBillNoLocusFromPKPCreate(waybillEntity, WayBillNoLocusConstant.INVOKING_SOURCE_FOSS));
				}
			} catch (Exception e) {
			e.printStackTrace();
		}
	}
		// 出发、到达
		dtoList.addAll(getWayBillNoLocusFromTFR(waybillEntity, null, true, WayBillNoLocusConstant.INVOKING_SOURCE_FOSS));
		dtoList.addAll(getWayBillNoLocusFromAir(waybillEntity, null));
		// 获取运单提货通知信息列表
		dtoList.addAll(getWayBillNoLocusFromNotify(waybillEntity));
		// 派送
		dtoList.addAll(getWayBillNoLocusFromDelivery(waybillEntity, WayBillNoLocusConstant.INVOKING_SOURCE_FOSS));
		// 派送拉回、正常签收、异常签收、部分签收
		dtoList.addAll(getWayBillNoLocusFromSignInfo(waybillEntity,null));
		// 终止
		dtoList.addAll(getWayBillNoLocusFromdeliveryAborted(waybillEntity));

		for (WayBillNoLocusDTO wayBillNoLocusDTO : dtoList) {// 操作类型转成名称
			if (dtoList != null && dtoList.size() > 0 && wayBillNoLocusDTO.getOperateType() != null) {
				wayBillNoLocusDTO.setOperateTypeName(WayBillNoLocusConstant.map.get(wayBillNoLocusDTO.getOperateType()));
				wayBillNoLocusDTO.setCurrentStatus(WayBillNoLocusConstant.currentStatusMap.get(wayBillNoLocusDTO.getOperateType()));
			} else {
				LOGGER.info("没有符合条件的记录");
			}
		}
		return dtoList;
	}
	/**
	 * 
	 * 新的综合查询要求，运单补录-图片开单
	 * @param waybillNo
	 * @return
	 * @author liutao  065340  2015-06-13
	 */
	private Collection<? extends WayBillNoLocusDTO> getWayBillNoLocusFromPKPCreatePic2(WaybillEntity waybillEntity, String invokingSourceFoss,WaybillPictureEntity waybillPictureEntity) {
		List<WayBillNoLocusDTO> dtos= new ArrayList<WayBillNoLocusDTO>();
		WayBillNoLocusDTO dto = new WayBillNoLocusDTO();
		// 运单号
		dto.setWaybillNo(waybillEntity.getWaybillNo());
		// 设置操作部门相关信息
		setOrgInfo(waybillEntity.getCreateOrgCode(), dto, WayBillNoLocusConstant.OPERATE_ORG);
		// 操作内容
		dto.setOperateContent(null);
		// 操作时间
		dto.setOperateTime(waybillPictureEntity.getEndBillTime());
		// 操作類型
		dto.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_CREATE_PDA_BSE_PIC);
		// 备注 运单储运事项
		dto.setNotes(waybillEntity.getTransportationRemark());
		// 操作人姓名
		UserEntity userEntity = userService.queryUserByEmpCode(waybillEntity.getCreateUserCode());
		if (userEntity != null) {
			dto.setOperateName(userEntity.getEmpName());
		}else
		{
			dto.setOperateName(waybillEntity.getCreateUserName());
		}
		// 操作件数
		dto.setOperateNumber(waybillEntity.getGoodsQtyTotal());
		// 单据编号
		dto.setBillNo(waybillEntity.getWaybillNo());
		// 车牌号
		dto.setVehicleNo(null);
		// 备注 如果是空运，添加航班信息
		if (StringUtil.equals(DictionaryValueConstants.BSE_LINE_TRANSTYPE_KONGYUN, waybillEntity.getTransportType())) {
			dto.setNotes(DictUtil.rendererSubmitToDisplay(waybillEntity.getFlightNumberType(), DictionaryConstants.AIR_FLIGHT_TYPE));
		} else {
			dto.setNotes(null);
		}
		// 流水号
		dto.setSerialNo(null);
		// 设置目的站信息
		setOrgInfo(waybillEntity.getLastLoadOrgCode(), dto, WayBillNoLocusConstant.DEST_ORG);
		// 离开后预计到达下一操作部门时间
		dto.setPlanArriveTime(null);
		// 派送人员姓名
		dto.setDeliveryName(null);
		// 派送人员电话
		dto.setDeliveryPhone(null);
		// 签收人姓名
		dto.setSignManName(null);
		LOGGER.info(ReflectionToStringBuilder.toString(dto));
		dtos.add(dto);
		return dtos;
	}
	/**
	 * 
	 * 新增“补录重量体积”操作类型，包扣：货物状态、操作部门、操作人、操作类型、操作时间、件数、单据编号
	 * 
	 * @param waybillEntity
	 * @param invokingSourceFoss
	 * @return
	 * @author 065340  liutao
	 * @date 2015-06-16
	 */
	private Collection<? extends WayBillNoLocusDTO> getWayBillNoLocusFromPKPMakeupWeightVolume1(
			WaybillEntity waybillEntity, String invokingSourceFoss,WaybillPictureEntity waybillPictureEntity) {
		List<WayBillNoLocusDTO> dtos = getWayBillNoLocusFromPKPCreatePic(waybillEntity,waybillPictureEntity);
		WayBillNoLocusDTO dto = dtos.get(0);
		// 操作类型
		if (StringUtil.equals(WayBillNoLocusConstant.OPERATE_TYPE_CREATE_PDA, dto.getOperateType()) ||
				WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(waybillEntity.getPendingType())) {
			//运单补录时间
			dto.setOperateTime(waybillPictureEntity.getMakeupTime());
			// 操作类型
			dto.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_MAKEUP_PDA_BSE);
			// 备注 运单储运事项
			dto.setNotes(waybillEntity.getTransportationRemark());
		} 
		else {
			// 提交运单
			dto.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_CREATE_BSE);
		}
		return dtos;
	}
	/**
	 * 
	 * 新增“生成运单”操作类型，包扣：货物状态、操作部门、操作人、操作类型、操作时间、件数、单据编号
	 * 
	 * @param waybillEntity
	 * @param invokingSourceFoss
	 * @return
	 * @author 065340  liutao
	 * @date 2015-06-16
	 */
	private Collection<? extends WayBillNoLocusDTO> getWayBillNoLocusFromPKPCreateWaybill(
			WaybillEntity waybillEntity, String invokingSourceFoss,WaybillPictureEntity waybillPictureEntity) {
		List<WayBillNoLocusDTO> dtos = getWayBillNoLocusFromPKPCreatePic(waybillEntity,waybillPictureEntity);
		WayBillNoLocusDTO dto = dtos.get(0);
		// 操作类型
		if (StringUtil.equals(WayBillNoLocusConstant.OPERATE_TYPE_CREATE_PDA, dto.getOperateType())) {
			if(!waybillEntity.getWaybillNo().isEmpty()){
				WaybillEntity waybillEntity1 = waybillManagerService.queryWaybillNo(waybillEntity.getWaybillNo());
				//运单补录时间
				dto.setOperateTime(waybillEntity1.getCreateTime());
			}
			// 操作类型
			dto.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_GENERATE_PDA_BSE);
			// 备注 运单储运事项
			dto.setNotes(waybillEntity.getTransportationRemark());
		} 
		else {
			// 提交运单
			dto.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_CREATE_BSE);
		}
		return dtos;
	}
	/**
	 * 
	 * 新的综合查询要求，分运单补录-图片开单
	 * @param waybillNo
	 * @return
	 * @author liutao  065340  2015-06-13
	 */
	private Collection<? extends WayBillNoLocusDTO> getWayBillNoLocusFromPKPCreatePic(WaybillEntity waybillEntity, String invokingSourceFoss) {
		List<WayBillNoLocusDTO> dtos = getWayBillNoLocusFromPKPCreate(waybillEntity);
		WayBillNoLocusDTO dto = dtos.get(0);
		// 操作类型
		if (StringUtil.equals(WayBillNoLocusConstant.OPERATE_TYPE_CREATE_PDA, dto.getOperateType())) {
			if(!waybillEntity.getWaybillNo().isEmpty()){
				WaybillEntity waybillEntity1 = waybillManagerService.queryWaybillNo(waybillEntity.getWaybillNo());
				//运单补录时间
				dto.setOperateTime(waybillEntity1.getCreateTime());
			}
			// 运单补录
			dto.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_GENERATE_PDA_BSE);
			// 备注 运单储运事项
			dto.setNotes(waybillEntity.getTransportationRemark());
		} else {
			// 提交运单
			dto.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_CREATE_BSE);
		}
		return dtos;
	}
	/**
	 * 
	 * 新的综合查询要求，生成运单-图片开单
	 * 
	 * @param waybillEntity
	 * @param invokingSourceFoss
	 * @return
	 * @author 065340  liutao
	 * @date 2015-06-16
	 */
	private Collection<? extends WayBillNoLocusDTO> getWayBillNoLocusFromPKPCreateWaybillPic(WaybillEntity waybillEntity, String invokingSourceFoss) {
		List<WayBillNoLocusDTO> dtos = getWayBillNoLocusFromPKPCreate(waybillEntity);
		// 操作人姓名
		UserEntity userEntity = userService.queryUserByEmpCode(waybillEntity.getModifyUserCode());
		if (userEntity != null) {
			dtos.get(0).setOperateName(userEntity.getEmpName());
		}else{
		  dtos.get(0).setOperateName(waybillEntity.getCreateUserName());
		}
		WayBillNoLocusDTO dto = dtos.get(0);
		// 操作类型
		if (StringUtil.equals(WayBillNoLocusConstant.OPERATE_TYPE_CREATE_PDA, dto.getOperateType())) {
			if(!waybillEntity.getWaybillNo().isEmpty()){
				WaybillEntity waybillEntity1 = waybillManagerService.queryWaybillNo(waybillEntity.getWaybillNo());
				//运单补录时间
				dto.setOperateTime(waybillEntity1.getCreateTime());
			}
			// 运单补录
			dto.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_GENERATE_PDA_BSE);
			// 备注 运单储运事项
			dto.setNotes(waybillEntity.getTransportationRemark());
		} else {
			// 提交运单
			dto.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_CREATE_BSE);
		}
		return dtos;
	}
	/**
	 * 
	 * 新增“补录重量体积”操作类型，包扣：货物状态、操作部门、操作人、操作类型、操作时间、件数、单据编号
	 * 
	 * @param waybillEntity
	 * @param invokingSourceFoss
	 * @return
	 * @author 065340  liutao
	 * @date 2015-06-16
	 */
	private Collection<? extends WayBillNoLocusDTO> getWayBillNoLocusFromPKPMakeupWeightVolume(
			WaybillEntity waybillEntity, String invokingSourceFoss,WaybillPictureEntity waybillPictureEntity) {
		List<WayBillNoLocusDTO> dtos = getWayBillNoLocusFromPKPCreatePic(waybillEntity,waybillPictureEntity);
		WayBillNoLocusDTO dto = dtos.get(0);
		// 操作类型
		if (StringUtil.equals(WayBillNoLocusConstant.OPERATE_TYPE_CREATE_PDA, dto.getOperateType())) {
			if(!waybillEntity.getWaybillNo().isEmpty()){
				WaybillEntity waybillEntity1 = waybillManagerService.queryWaybillNo(waybillEntity.getWaybillNo());
				//运单补录时间
				dto.setOperateTime(waybillEntity1.getCreateTime());
			}
			// 操作类型
			dto.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_MAKEUP_PDA_BSE);
			// 备注 运单储运事项
			dto.setNotes(waybillEntity.getTransportationRemark());
		} 
		else {
			// 提交运单
			dto.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_CREATE_BSE);
		}
		return dtos;
	}
	/**
	 * 
	 * PDA暂存信息-图片开单
	 * 
	 * @param waybillNo
	 * @author 065340  liutao
	 * @date 2015-06-16
	 */
	private List<WayBillNoLocusDTO> getWayBillNoLocusFromPDAPendingPic(String waybillNo,WaybillPictureEntity waybillPictureEntity) {
		List<WayBillNoLocusDTO> dtoList = new ArrayList<WayBillNoLocusDTO>();
		WaybillPendingEntity waybillPendingEntity = waybillPendingService.queryPendingByNo(waybillNo);
		if (waybillPendingEntity == null) {
			// 没有符合条件的值
			return dtoList;
		}
		WayBillNoLocusDTO dto = new WayBillNoLocusDTO();
		// 运单号
		dto.setWaybillNo(waybillPendingEntity.getWaybillNo());
		// 单据编号
		dto.setBillNo(waybillPendingEntity.getWaybillNo());

		// 设置操作部门相关信息
		setOrgInfo(waybillPictureEntity.getBillOrgCode(), dto, WayBillNoLocusConstant.OPERATE_ORG);
		// 操作类型
		dto.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_PDA_PENDING_PIC);
		// 操作内容
		dto.setOperateContent(null);
		// 操作时间
		dto.setOperateTime(waybillPictureEntity.getCreateTime());
		// 操作人姓名
		UserEntity userEntity = userService.queryUserByEmpCode(waybillPictureEntity.getCreateUserCode());
		if (userEntity != null) {
			dto.setOperateName(userEntity.getEmpName());
		}
		// 操作件数
		dto.setOperateNumber(waybillPendingEntity.getGoodsQtyTotal());
		// 单据编号
		dto.setBillNo(null);
		// 车牌号
		dto.setVehicleNo(waybillPendingEntity.getLicensePlateNum());
		// 备注
		dto.setNotes(null);
		// 流水号
		dto.setSerialNo(null);
		// 设置目的站信息
		// 离开后预计到达下一操作部门时间
		dto.setPlanArriveTime(null);
		// 派送人员姓名
		dto.setDeliveryName(null);
		// 派送人员电话
		dto.setDeliveryPhone(null);
		// 签收人姓名
		dto.setSignManName(null);
		LOGGER.info(ReflectionToStringBuilder.toString(dto));
		dtoList.add(dto);
		return dtoList;
	}
	/**
	 * 
	 * 根据运单号查询运单实体
	 * 
	 * @param waybillNo
	 * @return WaybillEntity
	 * @author ibm-wangfei
	 * @date Jan 8, 2013 5:32:08 PM
	 */
	private WaybillEntity queryWaybillEntityByNo(String waybillNo) {
		// 通过运单号获取运单信息
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
		//		LOGGER.info(ReflectionToStringBuilder.toString(waybillEntity));
		return waybillEntity;
	}

	/**
	 * 
	 * PDA暂存信息
	 * 
	 * @param waybillNo
	 * @return
	 * @author ibm-wangfei
	 * @date May 23, 2013 7:32:05 PM
	 */
	private List<WayBillNoLocusDTO> getWayBillNoLocusFromPDAPending(String waybillNo) {
		List<WayBillNoLocusDTO> dtoList = new ArrayList<WayBillNoLocusDTO>();
		WaybillPendingEntity waybillPendingEntity = waybillPendingService.queryPendingByNo(waybillNo);
		if (waybillPendingEntity == null) {
			// 没有符合条件的值
			return dtoList;
		}
		WayBillNoLocusDTO dto = new WayBillNoLocusDTO();
		// 运单号
		dto.setWaybillNo(waybillPendingEntity.getWaybillNo());
		// 单据编号
		dto.setBillNo(waybillPendingEntity.getWaybillNo());

		// 设置操作部门相关信息
		setOrgInfo(waybillPendingEntity.getCreateOrgCode(), dto, WayBillNoLocusConstant.OPERATE_ORG);

		// 操作类型
		dto.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_PDA_PENDING);
		// 操作内容
		dto.setOperateContent(null);
		// 操作时间
		dto.setOperateTime(waybillPendingEntity.getBillTime());
		// 操作人姓名
		UserEntity userEntity = userService.queryUserByEmpCode(waybillPendingEntity.getCreateUserCode());
		if (userEntity != null) {
			dto.setOperateName(userEntity.getEmpName());
		}
		// 操作件数
		dto.setOperateNumber(waybillPendingEntity.getGoodsQtyTotal());
		// 单据编号
		dto.setBillNo(null);
		// 车牌号
		dto.setVehicleNo(waybillPendingEntity.getLicensePlateNum());
		// 备注
		dto.setNotes(null);
		// 流水号
		dto.setSerialNo(null);
		// 设置目的站信息
		// 离开后预计到达下一操作部门时间
		dto.setPlanArriveTime(null);
		// 派送人员姓名
		dto.setDeliveryName(null);
		// 派送人员电话
		dto.setDeliveryPhone(null);
		// 签收人姓名
		dto.setSignManName(null);
		LOGGER.info(ReflectionToStringBuilder.toString(dto));
		dtoList.add(dto);
		return dtoList;
	}

	/**
	 * 
	 * 通过运单号查询运单的轨迹（数据来自接送货，开单）
	 * 
	 * @param waybillEntity
	 * @author ibm-wangfei
	 * @date 2013-1-8 上午9:58:19
	 */
	private List<WayBillNoLocusDTO> getWayBillNoLocusFromPKPCreate(WaybillEntity waybillEntity) {
		List<WayBillNoLocusDTO> dtoList = new ArrayList<WayBillNoLocusDTO>();
		WayBillNoLocusDTO dto = new WayBillNoLocusDTO();
		// 运单号
		dto.setWaybillNo(waybillEntity.getWaybillNo());
   
		 //设置预计到达时间 200968 2016-02-20
		dto.setPreArriveTime(waybillEntity.getPreArriveTime());
		
		// 设置操作部门相关信息
//		setOrgInfo(waybillEntity.getCreateOrgCode(), dto, WayBillNoLocusConstant.OPERATE_ORG);
		setOrgInfo(waybillEntity.getReceiveOrgCode(), dto, WayBillNoLocusConstant.OPERATE_ORG);

		// 操作类型
		if (WaybillConstants.WAYBILL_STATUS_PENDING_ACTIVE.equals(waybillEntity.getPendingType())) {
			dto.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_CREATE_PDA);
		} else {
			dto.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_CREATE);
		}
		// 操作内容
		dto.setOperateContent(null);
		// 操作时间
		dto.setOperateTime(waybillEntity.getBillTime());
		// 操作人姓名
		UserEntity userEntity = userService.queryUserByEmpCode(waybillEntity.getCreateUserCode());
		if (userEntity != null) {
			dto.setOperateName(userEntity.getEmpName());
		}else
		{
			dto.setOperateName(waybillEntity.getCreateUserName());
		}
		// 操作件数
		dto.setOperateNumber(waybillEntity.getGoodsQtyTotal());
		// 单据编号
		dto.setBillNo(waybillEntity.getWaybillNo());
		// 车牌号
		dto.setVehicleNo(null);
		// 备注 如果是空运，添加航班信息
		if (StringUtil.equals(DictionaryValueConstants.BSE_LINE_TRANSTYPE_KONGYUN, waybillEntity.getTransportType())) {
			dto.setNotes(DictUtil.rendererSubmitToDisplay(waybillEntity.getFlightNumberType(), DictionaryConstants.AIR_FLIGHT_TYPE));
		} else {
			dto.setNotes(null);
		}
		// 流水号
		dto.setSerialNo(null);
		// 设置目的站信息
		setOrgInfo(waybillEntity.getLastLoadOrgCode(), dto, WayBillNoLocusConstant.DEST_ORG);
		// 离开后预计到达下一操作部门时间
		dto.setPlanArriveTime(null);
		// 派送人员姓名
		dto.setDeliveryName(null);
		// 派送人员电话
		dto.setDeliveryPhone(null);
		// 签收人姓名
		dto.setSignManName(null);
		LOGGER.info(ReflectionToStringBuilder.toString(dto));
		dtoList.add(dto);
		return dtoList;
	}

	/**
	 * 
	 * 新的综合查询要求，分运单补录和提交运单
	 * 
	 * @param waybillEntity
	 * @param invokingSourceFoss
	 * @return
	 * @author ibm-wangfei
	 * @date May 23, 2013 6:52:25 PM
	 */
	private Collection<? extends WayBillNoLocusDTO> getWayBillNoLocusFromPKPCreate(WaybillEntity waybillEntity, String invokingSourceFoss) {
		List<WayBillNoLocusDTO> dtos = getWayBillNoLocusFromPKPCreate(waybillEntity);
		WayBillNoLocusDTO dto = dtos.get(0);
		// 操作类型
		if (StringUtil.equals(WayBillNoLocusConstant.OPERATE_TYPE_CREATE_PDA, dto.getOperateType())) {
			if(!waybillEntity.getWaybillNo().isEmpty()){
				WaybillEntity waybillEntity1 = waybillManagerService.queryWaybillNo(waybillEntity.getWaybillNo());
				//运单补录时间
				dto.setOperateTime(waybillEntity1.getCreateTime());
			}
			// 运单补录
			//dto.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_CREATE_BSE);
			dto.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_CREATE_PDA_BSE);
			ActualFreightEntity actualFreight = actualFreightService.queryByWaybillNo(waybillEntity.getWaybillNo());
			   //判断是否补录中心补录的单据，如果是则显示开单人写“供应商”开单部门写“供应商部门”
				 if(null!=actualFreight && null!=actualFreight.getIsExpressFocus() && "Y".equals(actualFreight.getIsExpressFocus())){
					 dto.setOperateName("供应商");
					 WaybillEntity waybillEntity2 = waybillManagerService.queryWaybillNo(waybillEntity.getWaybillNo());
					 if(null!=waybillEntity2 && StringUtils.isNotEmpty(waybillEntity2.getReceiveOrgName())){
					 dto.setOperateOrgName(waybillEntity2.getReceiveOrgName());
					 }else{
					 dto.setOperateOrgName("供应商部门");
					 }
				  }
			// 备注 运单储运事项
			dto.setNotes(waybillEntity.getTransportationRemark());
		} else {
			// 提交运单
			//dto.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_CREATE_PDA_BSE);
			dto.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_CREATE_BSE);
		}
		return dtos;
	}
	/**
	 * 
	 * @param waybillEntity
	 * @param invokingSourceFoss
	 * @return
	 * @author 065340  liutao
	 * @date 2015-06-16
	 */
	private List<WayBillNoLocusDTO> getWayBillNoLocusFromPKPCreatePic(WaybillEntity waybillEntity,WaybillPictureEntity waybillPictureEntity) {
		List<WayBillNoLocusDTO> dtoList = new ArrayList<WayBillNoLocusDTO>();
		WayBillNoLocusDTO dto = new WayBillNoLocusDTO();
		// 运单号
		dto.setWaybillNo(waybillEntity.getWaybillNo());
		// 设置操作部门相关信息
		if(waybillPictureEntity.getClearDept()!=null){
		     setOrgInfo(waybillPictureEntity.getClearDept(),dto,WayBillNoLocusConstant.OPERATE_ORG);
		}else{
			 setOrgInfo(waybillEntity.getCreateOrgCode(),dto,WayBillNoLocusConstant.OPERATE_ORG);
		}
		// 操作类型
		if (WaybillConstants.WAYBILL_STATUS_PENDING_ACTIVE.equals(waybillEntity.getPendingType())) {
			dto.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_CREATE_PDA);
		} else {
			dto.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_CREATE);
		}
		// 操作内容
		dto.setOperateContent(null);
		// 操作时间
		dto.setOperateTime(waybillEntity.getBillTime());
		// 操作人姓名
		 UserEntity userEntity =null;
		if(waybillPictureEntity.getClearEmp()!=null){
			  userEntity = userService.queryUserByEmpCode(waybillPictureEntity.getClearEmp());
		}else{
			  userEntity = userService.queryUserByEmpCode(waybillEntity.getCreateUserCode());
		}
		if (userEntity != null) {
			dto.setOperateName(userEntity.getEmpName());
		}else
		{
			dto.setOperateName(waybillEntity.getCreateUserName());
		}
		// 操作件数
		dto.setOperateNumber(waybillEntity.getGoodsQtyTotal());
		// 单据编号
		dto.setBillNo(waybillEntity.getWaybillNo());
		// 车牌号
		dto.setVehicleNo(null);
		// 备注 如果是空运，添加航班信息
		if (StringUtil.equals(DictionaryValueConstants.BSE_LINE_TRANSTYPE_KONGYUN, waybillEntity.getTransportType())) {
			dto.setNotes(DictUtil.rendererSubmitToDisplay(waybillEntity.getFlightNumberType(), DictionaryConstants.AIR_FLIGHT_TYPE));
		} else {
			dto.setNotes(null);
		}
		// 流水号
		dto.setSerialNo(null);
		// 设置目的站信息
		setOrgInfo(waybillEntity.getLastLoadOrgCode(), dto, WayBillNoLocusConstant.DEST_ORG);
		// 离开后预计到达下一操作部门时间
		dto.setPlanArriveTime(null);
		// 派送人员姓名
		dto.setDeliveryName(null);
		// 派送人员电话
		dto.setDeliveryPhone(null);
		// 签收人姓名
		dto.setSignManName(null);
		LOGGER.info(ReflectionToStringBuilder.toString(dto));
		dtoList.add(dto);
		return dtoList;
	}
	/**
	 * 查询补录后的PDA开单信息
	 * @author liyongfei
	 * @param waybillEntity
	 * @return
	 */
	@SuppressWarnings("unused")
	@Override
	public WayBillNoLocusDTO getWayBillHisPDA(String  waybillNo){
		if(waybillNo==null) return null;
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillNo(waybillNo);
		if(waybillEntity==null) return null;
		//根据运单编号查询历史操作表
		WayBillNoLocusDTO dto = null;
		List<String> actionItem = new ArrayList<String>();
//		actionItem.add("goodsQty");
//		actionItem.add("licensePlateNum");
		List<WaybillAcHisPdaEntity> entityList =  waybillManagerService.queryWaybillHisByNo(waybillNo, actionItem);
		if(entityList!=null && entityList.size()>0){
			dto = new WayBillNoLocusDTO();
			dto.setBillNo(waybillNo);
			dto.setOperateOrgCode(entityList.get(0).getOperatorOrgCode());
			dto.setOperateOrgName(entityList.get(0).getOperatorOrg());
			dto.setOperateName(entityList.get(0).getOperator());
			try {
				WaybillPictureEntity waybillPictureEntity=new WaybillPictureEntity();
				waybillPictureEntity.setWaybillNo(waybillNo);
				waybillPictureEntity.setActive("Y");
				//查询图片开单信息
				waybillPictureEntity=waybillPendingService.queryWaybillPictureByEntity(waybillPictureEntity);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date lineTime=format.parse("2015-06-30 14:00:00");
				if(null!=waybillPictureEntity){
					Date createTime=format.parse(format.format(waybillPictureEntity.getCreateTime()));
					if(createTime.after(lineTime)){
							   dto.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_PDA_PENDING_PIC);
							   dto.setOperateTime(waybillPictureEntity.getCreateTime());
					}else{
						 dto.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_PDA_PENDING);
						 dto.setOperateTime(waybillEntity.getBillTime()); 
					}
				}else{
					  dto.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_PDA_PENDING);
					  dto.setOperateTime(waybillEntity.getBillTime());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Map<String,Object> condition = new HashMap<String, Object>();
		for(WaybillAcHisPdaEntity entity : entityList){
			condition.put(entity.getActionItem(), entity.getAfterChange());
		}
		/**
		 * 设置操作类型和货物状态
		 */
		if(dto!=null){
			int operateNumber = 0;
			String licensePlateNum = "";
			if(condition.get("goodsQty")==null){
				if(waybillEntity.getGoodsQtyTotal()!=null){
					operateNumber = Integer.valueOf(waybillEntity.getGoodsQtyTotal());
				}
			}else{
				operateNumber = Integer.valueOf(condition.get("goodsQty").toString());
			}
			
			if(condition.get("licensePlateNum")==null){
				licensePlateNum = waybillEntity.getLicensePlateNum();
			}else{
				licensePlateNum =condition.get("licensePlateNum").toString();
			}
			dto.setOperateNumber(operateNumber);
			dto.setVehicleNo(licensePlateNum);
			dto.setOperateTypeName(WayBillNoLocusConstant.map.get(dto.getOperateType()));
			dto.setCurrentStatus(WayBillNoLocusConstant.currentStatusMap.get(dto.getOperateType()));
		}
		return dto;
	}
	
	/**
	 * 
	 * 查询零担电子运单的货物轨迹
	 * 
	 * @param waybillEntity
	 * @param invokingSourceFoss
	 * @author 326027
	 */
	@SuppressWarnings("unchecked")
	private List<WayBillNoLocusDTO> getWayBillNoLocusFromLTLElectronic(CreateDeliveryReceiptEntity deliveryReceiptEntity,String waybillNo) {
		Calendar cal = Calendar.getInstance();
		long startTime = cal.getTimeInMillis();
		List<WayBillNoLocusDTO> tfrlist = new ArrayList<WayBillNoLocusDTO>();
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		if (deliveryReceiptEntity!=null) {
				//根据交接单司机工号查询操作人姓名和操作部门
				String empCode=deliveryReceiptEntity.getDriverCode();
				EmployeeEntity employeeEntity = employeeService.querySimpleEmployeeByEmpCode(empCode);
				//调用综合通过车牌判断是否为外请车(工号，车牌号)
				String truckCode=deliveryReceiptEntity.getTruckCode();
				String driverCode=deliveryReceiptEntity.getDriverCode();
				//LeasedTruckEntity leasedTruckEntity = LeasedVehicleService.queryLeasedVehicleByVehicleNo(driverCode);
				//根据车牌号查询所服务车队
				LeasedTruckEntity entity = LeasedVehicleService.queryLeasedVehicleTeam(truckCode);
				//根据运单查询零担扫描表 
				PdaAppInfoEntity pdaAppInfoEntity = pdaAppInfoDao.queryPdaAppInfoByWaybillNO(waybillNo);
				//根据运单号查询pending表
				WaybillPendingEntity  waybillPendingEntity = waybillPendingService.queryWaybillByWaybillNo(waybillNo);
				//给零担新增已扫描轨迹
				tfrlist.add(getWayBillNoLocusFromPdaScan(pdaAppInfoEntity));
				//生成待补录
				tfrlist.add(getWayBillNoLocusFromPending(waybillPendingEntity));
				//接货中
				tfrlist.add(getWayBillNoLocusFromDeliver(deliveryReceiptEntity, employeeEntity,entity));
				//完成接货
				tfrlist.add(getWayBillNoLocusFromDeliverFinish(deliveryReceiptEntity, employeeEntity,entity));
		}
		return tfrlist;
	}

	/**
	 * 
	 * 通过运单号查询运单的轨迹（数据来自中转，包括出发，到达）
	 * 
	 * @param waybillEntity
	 * @param serialNo 流水号（若为空，就按照最慢的一件来取）
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 上午9:58:19
	 */
	@SuppressWarnings("unchecked")
	private List<WayBillNoLocusDTO> getWayBillNoLocusFromTFR(WaybillEntity waybillEntity, String serialNo, boolean isBsa, String invokingSource) {
		Calendar cal = Calendar.getInstance();
		long startTime = cal.getTimeInMillis();
		List<WayBillNoLocusDTO> tfrlist = new ArrayList<WayBillNoLocusDTO>();
		// 通过运单号取得所有的交接单号涉及的信息,又过来设置查询条件
		HandoverBillDTO handoverBill = new HandoverBillDTO();
		handoverBill.setWaybillNo(waybillEntity.getWaybillNo());
		// 取得开单时间的前七天，查询时间之前一点的，防止有交接时间在开单时间之前的
		handoverBill.setBilltime(DepartureHandle.getSevenDayTimeBefore(waybillEntity.getBillTime()));
		List<HandoverBillDTO> handoverBilllist = new ArrayList<HandoverBillDTO>();
		if (!isBsa) {
			handoverBilllist = trackingService.getHandoverBillByWayBillNo(handoverBill, serialNo);
		} else {
			handoverBilllist = trackingService.getAllHandoverBillByWayBillNo(handoverBill);
		}

		if (handoverBilllist == null) {
			return new ArrayList<WayBillNoLocusDTO>();
		}
		for (HandoverBillDTO handoverBillDTO : handoverBilllist) {
			if (LoadConstants.HANDOVERBILL_STATE_ALREADY_DEPART == handoverBillDTO.getHandoverbillStatus()) {// 状态是已交接、已出发的（出发）
				tfrlist.add(getWayBillNoLocusFromDepart(waybillEntity, handoverBillDTO));
			} else if (LoadConstants.HANDOVERBILL_STATE_ALREADY_ARRIVE == handoverBillDTO.getHandoverbillStatus() ||
					LoadConstants.HANDOVERBILL_STATE_ALREADY_INSTOCK == handoverBillDTO.getHandoverbillStatus()) {// 状态是已到达、已入库的（到达）
				tfrlist.add(getWayBillNoLocusFromDepart(waybillEntity, handoverBillDTO));
				tfrlist.add(getWayBillNoLocusFromArrive(waybillEntity, handoverBillDTO));
			}
		}

		//官网和FOSS均显示快递代理外发单录入和快递代理轨迹
		// 快递代理外发单录入
		//查询快递代理外发记录
		LdpExternalBillDto ldpExternalDto = new LdpExternalBillDto();
		ldpExternalDto.setWaybillNo(handoverBill.getWaybillNo());
		LdpExternalBillDto ldpExternalBillDto = ldpExternalBillService.queryExternalBillByWaybillNo(ldpExternalDto);
		if (ldpExternalBillDto != null) {
			// zwd 200968  2015-07-21  快递:快递代理外发录入
			tfrlist.add(getWayBillNoLocusFromLDPPartialLine(waybillEntity, ldpExternalBillDto));
		}			
		
		//快递代理轨迹
		LdpExternalBillTrackDto ldpExternalBillTrackDto = new LdpExternalBillTrackDto();
		ldpExternalBillTrackDto.setWaybillNo(handoverBill.getWaybillNo());
		List<LdpExternalBillTrackDto> ldpExternalBillTracks = ldpExternalBillTrackService.queryLdpExternalBillTrackList(ldpExternalBillTrackDto);
		
		//国际件货物轨迹 
		List<InternationalTrackingEntity> interTackingsEntitys = agentWaybillTrackService.
				queryInterTrackingEntity(handoverBill.getWaybillNo());
		if(null != interTackingsEntitys){
			for(InternationalTrackingEntity entity:interTackingsEntitys){
				tfrlist.add(getWayBillNoLocusFromInterTrackings(entity));
			}
		}
		
		//DMP执行JOB：如果JOB同一次执行了修改前和修改后的对象,则将时间靠后的添加到tfrlist（轨迹信息中）
		//零担大件家装货物轨迹 
		List<DMPSynTrackingEntity> dmpSynTrackingEntityList=synTrackingServer.queryDMPTrackingsByWayBillNo(handoverBill.getWaybillNo());
		if(dmpSynTrackingEntityList!=null){
			for (DMPSynTrackingEntity dmpSynTrackingEntity : dmpSynTrackingEntityList) {
				tfrlist.add(getNoLocusFromDMPTrackings(dmpSynTrackingEntity));
			}
		}
		
		//营业部外发轨迹
		List<PrintAgentWaybillRecordEntity> sdExternalBillTracks = printAgentWaybillService.queryRecordByWaybillNo(waybillEntity.getWaybillNo(),"SD");
		
		if (!StringUtil.equals(invokingSource, WayBillNoLocusConstant.INVOKING_SOURCE_WEBSITE)) {
			
			//二程接驳-理货员装车出发到达轨迹
			List<HandoverBillDTO> tallyerHandoverBillList = trackingService.getTallyerHandoverBilllist(handoverBill, serialNo);
			if(tallyerHandoverBillList !=null){
				for(HandoverBillDTO tallyerHandoverBill :tallyerHandoverBillList){
					if(LoadConstants.HANDOVERBILL_STATE_ALREADY_DEPART == tallyerHandoverBill.getHandoverbillStatus()){// 状态是已出发
						tfrlist.add(getWayBillNoLocusFromTallyerDepart(waybillEntity, tallyerHandoverBill));
					}else if(LoadConstants.HANDOVERBILL_STATE_ALREADY_ARRIVE == tallyerHandoverBill.getHandoverbillStatus() ||
							LoadConstants.HANDOVERBILL_STATE_ALREADY_INSTOCK == tallyerHandoverBill.getHandoverbillStatus()){//状态是已到达、已入库的（到达）
						tfrlist.add(getWayBillNoLocusFromTallyerDepart(waybillEntity, tallyerHandoverBill));
						tfrlist.add(getWayBillNoLocusFromTallyerArrive(waybillEntity, tallyerHandoverBill));
					}
				}
			}
			
			//二程接驳-司机装车出发到达轨迹
			List<HandoverBillDTO> driverHandoverBillList = trackingService.getDriverHandoverBilllist(handoverBill, serialNo);
			if(driverHandoverBillList !=null){
				for(HandoverBillDTO driverHandoverBill :driverHandoverBillList){
					if(driverHandoverBill.getHandoverbillStatus()==0){// 状态是已出发
						tfrlist.add(getWayBillNoLocusFromDriverDepart(waybillEntity, driverHandoverBill));
					}else if(driverHandoverBill.getHandoverbillStatus()==1){//状态是已到达
						tfrlist.add(getWayBillNoLocusFromDriverDepart(waybillEntity, driverHandoverBill));
						tfrlist.add(getWayBillNoLocusFromDriverArrive(waybillEntity, driverHandoverBill));
					}
				}
			}
			
			// 装车轨迹
			List<LoadDetailEntity> loadBilllist = trackingService.getLoadDetailTrackingByWayBillNo(handoverBill, serialNo);
			if (loadBilllist != null) {
				for (LoadDetailEntity loadDetailEntity : loadBilllist) {
					if (LoadConstants.LOAD_TASK_STATE_LOADING.equals(loadDetailEntity.getTaskState()))
						tfrlist.add(getWayBillNoLocusFromLoad(waybillEntity, loadDetailEntity));
					else if (LoadConstants.LOAD_TASK_STATE_FINISHEN.equals(loadDetailEntity.getTaskState()) || LoadConstants.LOAD_TASK_STATE_SUBMITED.equals(loadDetailEntity.getTaskState())) {
						// 装车扫描
						tfrlist.add(getWayBillNoLocusFromLoad(waybillEntity, loadDetailEntity));
						// 装车提交
						tfrlist.add(getWayBillNoLocusFromLoadSubmit(waybillEntity, loadDetailEntity));
					}
				}
			}
			// 交接轨迹
			List<HandoverBillDTO> handoverBills = trackingService.queryHandOverBillDetailByWaybillNo(waybillEntity.getWaybillNo(), serialNo);
			if (handoverBills != null) {
				for (HandoverBillDTO ha : handoverBills) {
					// 判断交接单类型是否是外发交接单
					if (LoadConstants.HANDOVER_TYPE_PARTIALLINE.equals(ha.getHandoverType())) {
						tfrlist.add(getWayBillNoLocusFromPartline(waybillEntity, ha));
					} else if(LoadConstants.HANDOVER_TYPE_LDP.equals(ha.getHandoverType())){//快递:快递代理交接
						tfrlist.add(getWayBillNoLocusFromLDPHandOver(waybillEntity, ha));
					}else {
						tfrlist.add(getWayBillNoLocusFromHandover(waybillEntity, ha));
					}
				}
			}
			// 登入包装货区
			handoverBill.setInStockType(StockConstants.PACKAGE_AREA_IN_STOCK_TYPE);
			List<HandoverBillDTO> packageAreaInBills = trackingService.getPackageAreaInByWayBillNo(handoverBill, serialNo, waybillEntity.getGoodsQtyTotal());
			if (packageAreaInBills != null) {
				for (HandoverBillDTO ha : packageAreaInBills) {
					tfrlist.add(getWayBillNoLocusFromPackageAreaIn(waybillEntity, ha, WayBillNoLocusConstant.OPERATE_TYPE_PACKAGE_AREA_IN));
				}
			}
			// 登出包装货区
			handoverBill.setOutStockType(StockConstants.PACKAGE_AREA_IN_STOCK_TYPE_NORMAL);
			List<HandoverBillDTO> packageAreaOutBills = trackingService.getPackageAreaOutByWayBillNo(handoverBill, serialNo, waybillEntity.getGoodsQtyTotal());
			if (packageAreaOutBills != null) {
				for (HandoverBillDTO ha : packageAreaOutBills) {
					tfrlist.add(getWayBillNoLocusFromPackageAreaIn(waybillEntity, ha, WayBillNoLocusConstant.OPERATE_TYPE_PACKAGE_AREA_OUT));
				}
			}
//			// 登入普通货区
//			handoverBill.setInStockType(StockConstants.NORMAL_AREA_FROM_SPECIAL_IN_STOCK_TYPE);
//			packageAreaInBills = trackingService.getPackageAreaInByWayBillNo(handoverBill, serialNo, waybillEntity.getGoodsQtyTotal());
//			if (packageAreaInBills != null) {
//				for (HandoverBillDTO ha : packageAreaInBills) {
//					tfrlist.add(getWayBillNoLocusFromPackageAreaIn(waybillEntity, ha, WayBillNoLocusConstant.OPERATE_TYPE_NORMAL_AREA_IN));
//				}
//			}
//			// 登出普通货区
//			handoverBill.setOutStockType(StockConstants.PACKAGE_AREA_IN_STOCK_TYPE);
//			packageAreaOutBills = trackingService.getPackageAreaOutByWayBillNo(handoverBill, serialNo, waybillEntity.getGoodsQtyTotal());
//			if (packageAreaOutBills != null) {
//				for (HandoverBillDTO ha : packageAreaOutBills) {
//					tfrlist.add(getWayBillNoLocusFromPackageAreaIn(waybillEntity, ha, WayBillNoLocusConstant.OPERATE_TYPE_NORMAL_AREA_OUT));
//				}
//			}
			// 登入包装货区(贵重)
			handoverBill.setInStockType(StockConstants.VALUABLE_AREA_IN_STOCK_TYPE);
			List<HandoverBillDTO> packageAreaInExpBills = trackingService.getPackageAreaInByWayBillNo(handoverBill, serialNo, waybillEntity.getGoodsQtyTotal());
			if (packageAreaInExpBills != null) {
				for (HandoverBillDTO ha : packageAreaInExpBills) {
					tfrlist.add(getWayBillNoLocusFromPackageAreaInEXP(waybillEntity, ha));
				}
			}
			// 登出包装货区(贵重)
			handoverBill.setOutStockType(StockConstants.VALUABLE_AREA_IN_STOCK_TYPE_NORMAL);
			List<HandoverBillDTO> packageAreaOutExpBills = trackingService.getPackageAreaOutByWayBillNo(handoverBill, serialNo, waybillEntity.getGoodsQtyTotal());
			if (packageAreaOutExpBills != null) {
				for (HandoverBillDTO ha : packageAreaOutExpBills) {
					tfrlist.add(getWayBillNoLocusFromPackageAreaOutEXP(waybillEntity, ha));
				}
			}

			// 外发单录入
			ExternalBillDto tempDto = new ExternalBillDto();
			tempDto.setWaybillNo(handoverBill.getWaybillNo());
			ExternalBillDto externalBillDto = externalBillService.queryExternalBillByWaybillNo(tempDto);
			if (externalBillDto != null) {
				tfrlist.add(getWayBillNoLocusFromPartialLine(waybillEntity, externalBillDto));
			}
			
			/*//快递代理轨迹（FOSS快递代理轨迹显示所有操作记录）
			if(CollectionUtils.isNotEmpty(ldpExternalBillTracks)){
				for(LdpExternalBillTrackDto ldpTrack : ldpExternalBillTracks){
					tfrlist.add(getWayBillNoLocusFromLDPTrask(waybillEntity, ldpTrack));
				}
			} */
			//2015-07-21 zwd 200968 Foss货物轨迹中，两条轨迹的“操作类型”和“操作时间”相同，则删除任一条轨迹，仅显示一条
			if(CollectionUtils.isNotEmpty(ldpExternalBillTracks)){
				List<LdpExternalBillTrackDto> ldpExternalTracks = new ArrayList<LdpExternalBillTrackDto>();
				//去除重复的操作类型与操作时间 ldpTrackSet
				Set<Date> ldpTrackSet = new HashSet<Date>();
				for(LdpExternalBillTrackDto ldpTrack : ldpExternalBillTracks){
					if(ldpTrack.getOperationTime() != null){
						ldpTrackSet.add(ldpTrack.getOperationTime());
					}else{
						ldpExternalTracks.add(ldpTrack);
					}
				}
				//去除重复的结果集 ldpExternalTracks
				for(Date ldpSet : ldpTrackSet){
					for(LdpExternalBillTrackDto ldpTrack : ldpExternalBillTracks){
						String sdate1 , sdate2;
						if(ldpTrack.getOperationTime() != null){
							
							sdate1=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(ldpTrack.getOperationTime());
							sdate2=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(ldpSet);  
						
							if(sdate1.equals(sdate2)){
								ldpExternalTracks.add(ldpTrack);
								break;
							}
						}
					}
				}
				//快递:快递代理外发轨迹
				for(LdpExternalBillTrackDto ldpTrack : ldpExternalTracks){
				   tfrlist.add(getWayBillNoLocusFromLDPTrask(waybillEntity, ldpTrack));
			    } 
		     }
			
			if(CollectionUtils.isNotEmpty(sdExternalBillTracks)) {
				//营业部外发轨迹
				PrintAgentWaybillRecordEntity recordEntity = sdExternalBillTracks.get(0);
				if(null != recordEntity) {
					tfrlist.add(getWayBillNoLocusFromSDTrack(waybillEntity, recordEntity));
				}
			}
			
			// 单票入库
			List<String> stockTypes = new ArrayList<String>();
			stockTypes.add(StockConstants.LOSE_GOODS_FOUND_IN_STOCK_TYPE);
			stockTypes.add(StockConstants.PARTIALLINE_RETURN_IN_STOCK_TYPE);
			stockTypes.add(StockConstants.AIR_FREIGHT_RETURN_SIGN_IN_STOCK_TYPE);
			stockTypes.add(StockConstants.SEND_RETURN_IN_STOCK_TYPE);
			stockTypes.add(StockConstants.PACKAGE_RETURN_IN_STOCK_TYPE);
			handoverBill.setInStockType(null);
			handoverBill.setInStockTypes(stockTypes);
			List<HandoverBillDTO> stockCheckingNormal = trackingService.getPackageAreaInByWayBillNo(handoverBill, serialNo, waybillEntity.getGoodsQtyTotal());
			if (stockCheckingNormal != null) {
				for (HandoverBillDTO ha : stockCheckingNormal) {
					tfrlist.add(getWayBillNoLocusFromStockCheckingNormal(waybillEntity, ha));
				}
			}
			// 清仓入库
			handoverBill.setInStockTypes(null);
			handoverBill.setInStockType(StockConstants.STOCKCHECKING_MORE_GOODS_IN_STOCK_TYPE);
			List<HandoverBillDTO> stockCheckingMore = trackingService.getPackageAreaInByWayBillNo(handoverBill, serialNo, waybillEntity.getGoodsQtyTotal());
			if (stockCheckingMore != null) {
				for (HandoverBillDTO ha : stockCheckingMore) {
					tfrlist.add(getWayBillNoLocusFromStockCheckingMore(waybillEntity, ha));
				}
			}
			// 查询其他入库中，数据库中存在的类型
			List<String> inStockStatusStr = trackingService.getInStockTypesWayBillNo(handoverBill, WayBillNoLocusConstant.inStockStatusStr);
			// 其他入库
			if (inStockStatusStr != null) {
				for (String inStockType : inStockStatusStr) {
					handoverBill.setInStockTypes(null);
					handoverBill.setInStockType(inStockType);
					List<HandoverBillDTO> othStockList = trackingService.getPackageAreaInByWayBillNo(handoverBill, serialNo, waybillEntity.getGoodsQtyTotal());
					if (othStockList != null) {
						for (HandoverBillDTO ha : othStockList) {
							WayBillNoLocusDTO wayBillNoLocusDTO = getWayBillNoLocusFromStockOther(waybillEntity, ha);
							if (StockConstants.TRANSPORT_PATH_CHANGE_IN_STOCK_TYPE.equals(inStockType)) {
								wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.TRANSPORT_PATH_CHANGE_IN_STOCK_TYPE);
							} else {
								//特殊处理
								if ("LOSE_GOODS".equals(inStockType)) {
									wayBillNoLocusDTO.setVehicleNo(ha.getBillNo());
									wayBillNoLocusDTO.setOperateOrgName("丢货");
								}
								//装车多货特殊处理
								if("LOAD_MORE".equals(inStockType))
								{
									wayBillNoLocusDTO.setVehicleNo(ha.getBillNo());
								}
								wayBillNoLocusDTO.setOperateType(inStockType);
							}
							tfrlist.add(wayBillNoLocusDTO);
						}
					}
				}
			}
			// 查询其他入库中，数据库中存在的类型
			List<String> outStockStatusStr = trackingService.getOutStockTypesWayBillNo(handoverBill, WayBillNoLocusConstant.outStockStatusStr);
			// 其他出库
			for (String outStockType : outStockStatusStr) {
				handoverBill.setOutStockType(outStockType);
				List<HandoverBillDTO> othStockList = trackingService.getPackageAreaOutByWayBillNo(handoverBill, serialNo, waybillEntity.getGoodsQtyTotal());
				if (othStockList != null) {
					for (HandoverBillDTO ha : othStockList) {
						WayBillNoLocusDTO wayBillNoLocusDTO = getWayBillNoLocusFromStockOther(waybillEntity, ha);
						if (StockConstants.TRANSPORT_PATH_CHANGE_OUT_STOCK_TYPE.equals(outStockType)) {
							wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.TRANSPORT_PATH_CHANGE_OUT_STOCK_TYPE);
						} else {
							wayBillNoLocusDTO.setOperateType(outStockType);
						}
						tfrlist.add(wayBillNoLocusDTO);
					}
				}
			}
			// 拉回入库
			handoverBill.setInStockTypes(null);
			handoverBill.setInStockType(StockConstants.AIR_UNSHIPPED_GOODS_IN_STOCK_TYPE);
			List<HandoverBillDTO> unshippedStockCheckingMore = trackingService.getPackageAreaInByWayBillNo(handoverBill, serialNo, waybillEntity.getGoodsQtyTotal());
			if (unshippedStockCheckingMore != null) {
				for (HandoverBillDTO ha : unshippedStockCheckingMore) {
					tfrlist.add(getWayBillNoLocusFromUnshippedStockCheckingMore(waybillEntity, ha));
				}
			}

			// 包装轨迹
			List<HandoverBillDTO> packages = trackingService.getPackageByWayBillNo(waybillEntity.getWaybillNo());
			if (packages != null) {
				for (HandoverBillDTO ha : packages) {
					tfrlist.add(getWayBillNoLocusFromPackage(waybillEntity, ha));
				}
			}
			//326027--4
			// 卸车轨迹
			List<HandoverBillDTO> unloadBilllist = trackingService.getUnloadBillByWayBillNo(handoverBill, serialNo);
			if (unloadBilllist != null) {
				for (HandoverBillDTO handoverBillDTO : unloadBilllist) {
					if (UnloadConstants.UNLOAD_TASK_TYPE_DELIVER.equals(handoverBillDTO.getUnloadType())) {
						tfrlist.add(getWayBillNoLocusFromUnloadDeliver(waybillEntity, handoverBillDTO));
					} else {
						tfrlist.add(getWayBillNoLocusFromUnload(waybillEntity, handoverBillDTO));
					}
				}
			}
			//叉车司机扫描
			List<HandoverBillDTO> trayScans = trackingService.getTrayScanByWayBillNo(handoverBill,serialNo);
			if(CollectionUtils.isNotEmpty(trayScans)){
				for(HandoverBillDTO temp : trayScans){
					tfrlist.add(getWayBillNoLocusFormTrayScanTask(waybillEntity,temp));
				}
			}
			//326027--3
			// 建立卸车任务 zwd 20150105 200968
			List<HandoverBillDTO> unloadTaskList = trackingService.queryUnloadTaskByWayBillNo(handoverBill, serialNo);
			if (CollectionUtils.isNotEmpty(unloadTaskList)) {
				for (HandoverBillDTO temp : unloadTaskList) {
					tfrlist.add(getWayBillNoLocusFormUnloadTask(temp));
				}
			}
			//点单任务管理 cl 20160115 272681
			List<HandoverBillDTO> orderTaskList = trackingService.queryOrderTaskByWayBillNo(handoverBill,serialNo);
			if (CollectionUtils.isNotEmpty(orderTaskList)) {
				for (HandoverBillDTO temp : orderTaskList) {
					tfrlist.add(getWayBillNoLocusFormOrderTask(temp));
				}
			}
			//上分拣 zwd 20141212 200968
			List<HandoverBillDTO>  sortingScanList = trackingService.querySortingScanByWayBillNo(handoverBill, serialNo);
			if(CollectionUtils.isNotEmpty(sortingScanList)){
				for(HandoverBillDTO temp : sortingScanList){
					tfrlist.add(getWayBillNoLocusFormSortingScan(temp));
				}
			}
			
			// 包信息管理 zwd 20141218 200968
			List<HandoverBillDTO>  expressPackageDetailEntityList =  trackingService.queryExpressPackageDetailByWayBillNo(handoverBill, serialNo);
			if(CollectionUtils.isNotEmpty(expressPackageDetailEntityList)){
				for(HandoverBillDTO temp : expressPackageDetailEntityList){
					tfrlist.add(getWayBillNoLocusFormExpressPackageDetail(temp));
				}
			}
			
			//空运通知客户 张卫东 2015-09-15 200968
			List<HandoverBillDTO>  airNotifyCustomersSmsInfoList =  trackingService.queryAirNotifyCustomersSmsInfoByWayBillNo(handoverBill, serialNo);
			if(CollectionUtils.isNotEmpty(airNotifyCustomersSmsInfoList)){
				HandoverBillDTO temp = new HandoverBillDTO();
				temp = airNotifyCustomersSmsInfoList.get(0);
				tfrlist.add(getWayBillNoLocusFormAirNotifyCustomersSmsInfo(temp));
			}
			
			// 空运到达轨迹  zwd 2015-08-07 200968
			List<HandoverBillDTO>  airQueryFlightArrivePickUpList =  trackingService.queryFlightArriveFromAirWaybillNoPickUp(handoverBill, serialNo);
			List<HandoverBillDTO>  airQueryFlightArriveGoodsList =  trackingService.queryFlightArriveFromAirWaybillNoGoods(handoverBill, serialNo);
			int count = 0 ;
			
			if(handoverBill.getWaybillNo()!= null){
				count = airWayBillDao.queryWaybillDetailsByWaybillNo(handoverBill.getWaybillNo());
			}
			
			//存放正单号,用来判断是否分批配载            空运到达类型:代理到机场提货
			if(CollectionUtils.isNotEmpty(airQueryFlightArrivePickUpList)){
				HandoverBillDTO tempBillDto = new HandoverBillDTO();
				tempBillDto  = airQueryFlightArrivePickUpList.get(0);
				AirQueryFlightArriveDto airQFADto = new AirQueryFlightArriveDto();
				//正单号
				airQFADto.setAirWaybillNo(tempBillDto.getHandoverNo());
				airQFADto.setWaybillNo(tempBillDto.getWaybillNo());
				airQFADto.setFlightArriveType(AirfreightConstants.AGENT_TO_AIRPORT_PICK_UP);  
	    		long num = airQueryFlightArriveDao.getSerialsCount(airQFADto);
	    		int countPickUpSerials = Integer.parseInt(String.valueOf(num));
	    		 //根据正单号+运单号+空运到达类型 修改已到达的流水个数
	    		tempBillDto.setGoodsQty(countPickUpSerials);
				if(count>1){
					//分批配载,到达件数不显示    设置一下标志位 
					tempBillDto.setManualDepartUserCode("YY");
					tfrlist.add(getWayBillNoLocusFormAirQueryFlightArrive(tempBillDto));
				}else{
					tempBillDto.setManualDepartUserCode("NN");
					tfrlist.add(getWayBillNoLocusFormAirQueryFlightArrive(tempBillDto));
				}
			}
			
			
			//存放正单号,用来判断是否分批配载              空运到达类型:货物到达代理处
			if(CollectionUtils.isNotEmpty(airQueryFlightArriveGoodsList)){
				HandoverBillDTO tempBillDto = new HandoverBillDTO();
				tempBillDto  = airQueryFlightArriveGoodsList.get(0);
				AirQueryFlightArriveDto airQFADto = new AirQueryFlightArriveDto();
				//正单号
				airQFADto.setAirWaybillNo(tempBillDto.getHandoverNo());
				airQFADto.setWaybillNo(tempBillDto.getWaybillNo());
				airQFADto.setFlightArriveType(AirfreightConstants.AGENT_TO_AIRPORT_PICK_UP);  
	    		long num = airQueryFlightArriveDao.getSerialsCount(airQFADto);
	    		int countGoodsSerials = Integer.parseInt(String.valueOf(num));
	    		 //根据正单号+运单号+空运到达类型 修改已到达的流水个数
	    		tempBillDto.setGoodsQty(countGoodsSerials);
				if(count>1){
					//分批配载,到达件数不显示    设置一下标志位 
					tempBillDto.setManualDepartUserCode("YY");
					tfrlist.add(getWayBillNoLocusFormAirQueryFlightArrive(tempBillDto));
				}else{
					tempBillDto.setManualDepartUserCode("NN");
					tfrlist.add(getWayBillNoLocusFormAirQueryFlightArrive(tempBillDto));
				}
			}
			/*****************OPP外发轨迹开始******************/
			//OPP外发轨迹：出发代理 出发 到达 到达代理出发 到达 以及 已返货
			//@author 269701--lln
			//@date 2016-05-16
			List<OppLocusEntity>  oppLocusList =  airQueryFlightArriveDao.queryOppLocusByWayBillNo(handoverBill.getWaybillNo(),serialNo);
			if(CollectionUtils.isNotEmpty(oppLocusList)){
				for(OppLocusEntity temp : oppLocusList){
					tfrlist.add(getWayBillNoLocusFormOPP(temp));
				}
			}
			/*****************OPP外发轨迹结束******************/
		} else {
			/*//官网只显示最早的一条轨迹信息
			if(CollectionUtils.isNotEmpty(ldpExternalBillTracks)){
				//Map<运单号，List<操作类型>>
				Map<String,List<String>> map = new HashMap<String,List<String>>();
				
				for(LdpExternalBillTrackDto ldpTrack : ldpExternalBillTracks){
					List<String> typeList = new ArrayList<String>();
					// zwd 200968 2015-07-21 containsKey(Object key) 如果 Map包含指定键的映射，则返回 true
					if(!map.containsKey(ldpTrack.getWaybillNo())) {
						//运单号和 操作类型：到达-1，离开-2，派件-3，派件失败-4
						//操作类型：到达-1，离开-2，派件-3，派件失败-4
						typeList.add(ldpTrack.getOperationtype());
						//zwd 200968 2015-07-21 put(Object key, Object value)	将指定值与指定键相关联
						map.put(ldpTrack.getWaybillNo(), typeList);
						tfrlist.add(getWayBillNoLocusFromLDPTrask(waybillEntity, ldpTrack));
					} else {
						//操作类型不存在时
						// zwd 200968 2015-07-21 get(Object key)	返回与指定键关联的值
						if(!map.get(ldpTrack.getWaybillNo()).contains(ldpTrack.getOperationtype())){
							//添加
							map.get(ldpTrack.getWaybillNo()).add(ldpTrack.getOperationtype());
							tfrlist.add(getWayBillNoLocusFromLDPTrask(waybillEntity, ldpTrack));
						}
					}
				}
			} */
			//放开FOSS对官网的外发轨迹条数限制	每一条不重复的外发轨迹均可给到官网进行轨迹展示。
			//2015-07-21 zwd 200968 Foss货物轨迹中，两条轨迹的“操作类型”和“操作时间”相同，则删除任一条轨迹，仅显示一条
			if(CollectionUtils.isNotEmpty(ldpExternalBillTracks)){
				//去除重复的操作类型与操作时间 ldpTrackSet
				Set<Date> ldpTrackSet = new HashSet<Date>();
				for(LdpExternalBillTrackDto ldpTrack : ldpExternalBillTracks){
					ldpTrackSet.add(ldpTrack.getOperationTime());
				}
				List<LdpExternalBillTrackDto> ldpExternalTracks = new ArrayList<LdpExternalBillTrackDto>();
				//去除重复的结果集 ldpExternalTracks
				for(Date ldpSet : ldpTrackSet){
					for(LdpExternalBillTrackDto ldpTrack : ldpExternalBillTracks){
						String sdate1 , sdate2;  
						sdate1=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(ldpTrack.getOperationTime());
						sdate2=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(ldpSet);  
						
						if(sdate1.equals(sdate2)){
							ldpExternalTracks.add(ldpTrack);
							break;
						}
					}
				}
				//快递:快递代理外发轨迹
				for(LdpExternalBillTrackDto ldpTrack : ldpExternalTracks){
				   tfrlist.add(getWayBillNoLocusFromLDPTrask(waybillEntity, ldpTrack));
			    } 
		     }
			
			
			if(CollectionUtils.isNotEmpty(sdExternalBillTracks)) {
				//营业部外发轨迹
				PrintAgentWaybillRecordEntity recordEntity = sdExternalBillTracks.get(0);
				if(null != recordEntity){
					tfrlist.add(getWayBillNoLocusFromSDTrack(waybillEntity, recordEntity));
				}
			}	
			
			//官网显示原单开返货单轨迹记录
			String[] inStockStatutAr = {StockConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO};
			List<String> inStockStatusStr = trackingService.getInStockTypesWayBillNo(handoverBill, inStockStatutAr);
			if (inStockStatusStr != null) {
				for (String inStockType : inStockStatusStr) {
					handoverBill.setInStockTypes(null);
					handoverBill.setInStockType(inStockType);
					List<HandoverBillDTO> othStockList = trackingService.getPackageAreaInByWayBillNo(handoverBill, serialNo, waybillEntity.getGoodsQtyTotal());
					if (othStockList != null) {
						for (HandoverBillDTO ha : othStockList) {
							WayBillNoLocusDTO wayBillNoLocusDTO = getWayBillNoLocusFromStockOther(waybillEntity, ha);
							wayBillNoLocusDTO.setOperateType(StockConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO);
							tfrlist.add(wayBillNoLocusDTO);
						}
					}
				}
			}
		}
		// 按照制单时间来排序
		Collections.sort(tfrlist, new WayBillNoLocusDTO());
		LOGGER.info("查询运单轨迹中转部分所用时间：" + (cal.getTimeInMillis() - startTime) + "毫秒");
		return tfrlist;
	}
	/**
	 * 建立卸车任务 zwd 20150105 200968
	 * @param handoverBillDTO
	 * @return
	 */
	private WayBillNoLocusDTO getWayBillNoLocusFormUnloadTask(HandoverBillDTO handoverBillDTO){
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();

		// 单据编号 --卸车任务编号
		wayBillNoLocusDTO.setBillNo(handoverBillDTO.getUnloadTaskNo());
		// 操作部门 --建立任务部门名称
		wayBillNoLocusDTO.setOperateOrgName(handoverBillDTO.getUnloadOrgName());
		// 操作时间 --操作时间 or任务建立时间
		wayBillNoLocusDTO.setOperateTime(handoverBillDTO.getTaskBeginTime());
		// 操作件数
		wayBillNoLocusDTO.setOperateNumber(handoverBillDTO.getGoodsQty());
		// 操作类型
		wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_UNLOAD_TASK); // 常量定义
		// 货物状态 (根据操作类型来获取货物状态)
		wayBillNoLocusDTO.setCurrentStatus(WayBillNoLocusConstant.currentStatusMap.get(wayBillNoLocusDTO.getOperateType()));
		// 操作人姓名
		wayBillNoLocusDTO.setOperateName(handoverBillDTO.getLoaderName());
		// 建立卸车任务车牌号
		wayBillNoLocusDTO.setVehicleNo(handoverBillDTO.getVehicleNo());
		if(StringUtils.equals(UnloadConstants.UNLOAD_TASK_TYPE_SCEXPRESS, handoverBillDTO.getUnloadType())){
			// 备注 --(默认空)
			wayBillNoLocusDTO.setNotes("二程接驳");
		}

		return wayBillNoLocusDTO;
	}
	
	/**
	 * 点单任务管理 cl 20160115 272681
	 * @param handoverBillDTO
	 * @return
	 */
	private WayBillNoLocusDTO getWayBillNoLocusFormOrderTask(HandoverBillDTO handoverBillDTO){
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();

		// 单据编号 --卸车任务编号
		wayBillNoLocusDTO.setBillNo(handoverBillDTO.getOrderTaskNo());
		// 操作部门 --建立任务部门名称
		wayBillNoLocusDTO.setOperateOrgName(handoverBillDTO.getCreateOrgName());
		// 操作时间 --操作时间 or任务建立时间
		if(StringUtils.equals(WayBillNoLocusConstant.ORDER_TASK_STATE_IN, handoverBillDTO.getOrderTaskState())){
			wayBillNoLocusDTO.setOperateTime(handoverBillDTO.getOrderStartTime());
		}else{
			wayBillNoLocusDTO.setOperateTime(handoverBillDTO.getOrderEndTime());
		}
		
		// 操作件数
		wayBillNoLocusDTO.setOperateNumber(handoverBillDTO.getOrderGoodsQty());
		// 操作类型
		if(StringUtils.equals(WayBillNoLocusConstant.ORDER_TASK_STATE_IN, handoverBillDTO.getOrderTaskState())){
			wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.ORDER_TASK_STATE_IN);
		}else{
			wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.ORDER_TASK_STATE_END);
		}
		// 货物状态 (根据操作类型来获取货物状态)
		wayBillNoLocusDTO.setCurrentStatus(WayBillNoLocusConstant.currentStatusMap.get(wayBillNoLocusDTO.getOperateType()));
		// 操作人姓名
		wayBillNoLocusDTO.setOperateName(handoverBillDTO.getOrderManName());
		// 建立卸车任务车牌号
		wayBillNoLocusDTO.setVehicleNo(handoverBillDTO.getVehicleNo());

		return wayBillNoLocusDTO;
	}
	
	/**
	 * 上分拣
	 * @author 200968-zwd
	 * @date  2014年12月12日 9:05:29
	 */
	private WayBillNoLocusDTO getWayBillNoLocusFormSortingScan(HandoverBillDTO handoverBillDTO){
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		// 单据编号 --运单号
		wayBillNoLocusDTO.setBillNo(handoverBillDTO.getWaybillNo());
		// 操作部门 --操作部门名称
		wayBillNoLocusDTO.setOperateOrgName(handoverBillDTO.getOperationOrgName());
		// 操作时间  --上分拣扫描时间
		wayBillNoLocusDTO.setOperateTime(handoverBillDTO.getCreateTime());
		// 操作件数
		wayBillNoLocusDTO.setOperateNumber(handoverBillDTO.getGoodsQty());
		// 操作类型
		wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_SORTING_SCAN); //常量定义
		// 货物状态 (根据操作类型来获取货物状态)
		wayBillNoLocusDTO.setCurrentStatus(WayBillNoLocusConstant.currentStatusMap.get(wayBillNoLocusDTO.getOperateType()));
		// 操作人姓名
		wayBillNoLocusDTO.setOperateName(handoverBillDTO.getOperationName());
		return wayBillNoLocusDTO;
	}
	
	/**
	 * 包信息管理
	 * @author 200968-zwd
	 * @date  2014年12月13日 13:45:29
	 */
	private WayBillNoLocusDTO getWayBillNoLocusFormExpressPackageDetail(HandoverBillDTO handoverBillDTO){
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		// 操作部门 --来自于收货部门
		wayBillNoLocusDTO.setOperateOrgName(handoverBillDTO.getDepartOrgName());
		// 操作人   --来自于包的创建人
		wayBillNoLocusDTO.setOperateName(handoverBillDTO.getCreateUserName());
		// 操作时间 --来自于包的创建时间
		wayBillNoLocusDTO.setOperateTime(handoverBillDTO.getCreateTime());
		//建包中 
		if(handoverBillDTO.getStatus().equals(TransferPDADictConstants.EXPRESS_PACKAGE_STATE_PROGRESS)){
			// 操作类型 
			wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_Express_PackagIng);
			// 货物状态 
			wayBillNoLocusDTO.setCurrentStatus(WayBillNoLocusConstant.currentStatusMap.get(wayBillNoLocusDTO.getOperateType()));
		}else{
			
			// 操作类型 
			wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_Express_Packaged);
			// 货物状态 
			wayBillNoLocusDTO.setCurrentStatus(WayBillNoLocusConstant.currentStatusMap.get(wayBillNoLocusDTO.getOperateType()));
			// 修改建包时间
			wayBillNoLocusDTO.setOperateTime(handoverBillDTO.getEndTime());
			
		}
		//根据运单号和流水号来查询包信息，一共有多少件已经建包
		// 操作件数
		wayBillNoLocusDTO.setOperateNumber(handoverBillDTO.getGoodsQty());
		// 单据编号  --取的是运单号
		wayBillNoLocusDTO.setBillNo(handoverBillDTO.getWaybillNo());
		// 车牌号 --(默认空)
		wayBillNoLocusDTO.setVehicleNo(null);
		// 备注 --(默认空)
		wayBillNoLocusDTO.setNotes(null);
		return wayBillNoLocusDTO;
	}
	

	/**
	 * 
	* @description OPP轨迹
	* @param handoverBillDTO
	* @return
	* @version 1.0
	* @author 269701-foss-lln
	* @update 2016年5月16日 下午3:04:13
	 */
	private WayBillNoLocusDTO getWayBillNoLocusFormOPP(OppLocusEntity entity){
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		// 操作部门 --来自于操作部门
		wayBillNoLocusDTO.setOperateOrgName(entity.getOperationOrgName());
		// 操作人   --来自于操作人
		wayBillNoLocusDTO.setOperateName(entity.getOperationName());
		// 操作时间 --来自于创建时间
		wayBillNoLocusDTO.setOperateTime(DateUtils.convert(entity.getOperTime(), DateUtils.DATE_TIME_FORMAT));
		// 货物状态 ：运输中：OPP_Status
		wayBillNoLocusDTO.setCurrentStatus(WayBillNoLocusConstant.currentStatusMap.get("OPP_STATUS"));
		// 备注 --出发和到达代理操作时候 备注为空；返货时候，显示返货备注数据
		wayBillNoLocusDTO.setNotes(null);
		// 车牌号 --(默认空)
		wayBillNoLocusDTO.setVehicleNo(null);
		
		 //操作类型：OPP_DDEPART出发代理已出发 OPP_DARRIVEL出发代理已到达 = OPP_AARRIVEL到达代理已到达 OPP_RETURN 已返货';
		if(StringUtils.equals("OPP_DDEPART", entity.getOperStatus())){
			wayBillNoLocusDTO.setOperateTypeName("出发代理已出发");
		}else if(StringUtils.equals("OPP_DARRIVEL", entity.getOperStatus())){
			wayBillNoLocusDTO.setOperateTypeName("出发代理已到达");
		}else if(StringUtils.equals("OPP_AARRIVEL", entity.getOperStatus())){
			wayBillNoLocusDTO.setOperateTypeName("到达代理已到达");
		}else{
			wayBillNoLocusDTO.setOperateTypeName("已返货");
			// 备注--返货时候，显示返货备注数据
			wayBillNoLocusDTO.setNotes(entity.getNotes());
		}
		// 操作件数
		wayBillNoLocusDTO.setOperateNumber(entity.getOperNum());
		// 单据编号  --取的是清单号
		wayBillNoLocusDTO.setBillNo(entity.getAirWaybillNo());

		return wayBillNoLocusDTO;
	}
	
	/**
	 * 空运通知客户信息
	 * @author zwd 200968
	 * @date 2015年9月15日 9:45:29
	 */
	private WayBillNoLocusDTO getWayBillNoLocusFormAirNotifyCustomersSmsInfo(HandoverBillDTO handoverBillDTO){
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		
		wayBillNoLocusDTO.setWaybillNo(handoverBillDTO.getWaybillNo());
        // 操作部门 
		wayBillNoLocusDTO.setOperateOrgName(handoverBillDTO.getOrgName());
		// 操作人  
		wayBillNoLocusDTO.setOperateName(handoverBillDTO.getOperationName());
		// 操作时间--修改时间
		wayBillNoLocusDTO.setOperateTime(handoverBillDTO.getEndTime());
		// 操作时间--第一次发送时间
		//wayBillNoLocusDTO.setOperateTime(handoverBillDTO.getCreateTime());
		
		// 操作类型 
		wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_AIR_NOTIFY_CUSTOMERS);
		// 货物状态 
		wayBillNoLocusDTO.setCurrentStatus(WayBillNoLocusConstant.currentStatusMap.get(wayBillNoLocusDTO.getOperateType()));
		
		//根据运单号和流水号来查询包信息，一共有多少件已经建包
		// 操作件数
		wayBillNoLocusDTO.setOperateNumber(handoverBillDTO.getGoodsQty());
		// 单据编号  --航班号
		wayBillNoLocusDTO.setBillNo(handoverBillDTO.getPackageNo());
		// 车牌号 --(默认空)
		wayBillNoLocusDTO.setVehicleNo(null);
		// 备注
		wayBillNoLocusDTO.setNotes(handoverBillDTO.getLoaderName());    
	        
		return wayBillNoLocusDTO;
	}
	
	/**
	 * 空运到达 zwd 2015-08-07 200968
	 * @param handoverBillDTO
	 * @param serialNo
	 * @return
	 */
	private WayBillNoLocusDTO getWayBillNoLocusFormAirQueryFlightArrive(HandoverBillDTO handoverBillDTO){
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		// 操作部门 --来自于到达录入部门
		wayBillNoLocusDTO.setOperateOrgName(handoverBillDTO.getUnloadOrgName());
		// 操作人   --来自于到达录入部门的操作人
		wayBillNoLocusDTO.setOperateName(handoverBillDTO.getLoaderName());
		// 操作时间 --来自于到达时间
		wayBillNoLocusDTO.setOperateTime(handoverBillDTO.getCreateTime());
		
		if(handoverBillDTO.getHandoverType().equals(AirfreightConstants.AGENT_TO_AIRPORT_PICK_UP)){
			// 操作类型 
			wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.AGENT_TO_AIRPORT_PICK_UP);
			// 货物状态 
			wayBillNoLocusDTO.setCurrentStatus(WayBillNoLocusConstant.currentStatusMap.get(wayBillNoLocusDTO.getOperateType()));
		}else if(handoverBillDTO.getHandoverType().equals(AirfreightConstants.GOODS_ARRIVE_AGENCY)){
			// 操作类型 
			wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.GOODS_ARRIVE_AGENCY);
			// 货物状态 
			wayBillNoLocusDTO.setCurrentStatus(WayBillNoLocusConstant.currentStatusMap.get(wayBillNoLocusDTO.getOperateType()));
		}
		// 操作件数 -- 到达件数 tempBillDto.setManualDepartUserCode("YY");
		if(handoverBillDTO.getManualDepartUserCode().equals("YY")){
			//不赋值
		}else{
			wayBillNoLocusDTO.setOperateNumber(handoverBillDTO.getGoodsQty());
		}
		// 单据编号  --取的是运单号
		wayBillNoLocusDTO.setBillNo(handoverBillDTO.getWaybillNo());
		
		// 车牌号 --航班号
		wayBillNoLocusDTO.setVehicleNo(handoverBillDTO.getVehicleNo());
		
		// 备注 --提货网点所在城市
		/**
		 * 运单有提货网点/到达部门，空运的提货网点都是空运代理公司
         * 空运的提货网点是空运代理网点
		 * 通过运单编号查询运单
		 * @param waybill
		 */
		WaybillEntity waybill = new WaybillEntity();
		waybill = waybillDao.queryWaybillByNo(handoverBillDTO.getWaybillNo());
		
		/**空运代理网点*/
		OuterBranchEntity entityCondition = new OuterBranchEntity();
		//设置状态：有效
		entityCondition.setActive(FossConstants.ACTIVE);
		//设置 网点类型：空运代理：KY 偏线代理：PX.
		entityCondition.setBranchtype(DictionaryValueConstants.OUTERBRANCH_TYPE_KY);
		
		if(waybill != null){
			if(waybill.getCustomerPickupOrgCode() != null){
				//空运的提货网点都是空运代理公司
				entityCondition.setAgentDeptCode(waybill.getCustomerPickupOrgCode());
			   }
			}
		 /**
	     * 空运代理网点. 根据传入对象查询符合条件所有空运代理网点信息 
	     */
		
		List<OuterBranchEntity> outBranchList =  airAgencyDeptDao.queryAirAgencyDepts(entityCondition, NumberConstants.NUMBER_5, 0);
		
		if(CollectionUtils.isNotEmpty(outBranchList)){
			/**空运代理网点*/
			OuterBranchEntity entity = new OuterBranchEntity();
			entity = outBranchList.get(0);
			if(entity.getAgentDeptCode() != null){
			String cityName = administrativeRegionsService.queryAdministrativeRegionsNameByCode(entity.getCityCode());
			//运单提货网点所在城市
			wayBillNoLocusDTO.setNotes(cityName);
			}
		}
		return wayBillNoLocusDTO;
	}
	
	
	/**
	 * 
	 * 通过运单号查询运单的轨迹（数据来自中转，包括空运）
	 * 
	 * @param waybillEntity
	 * @param serialNo 流水号（若为空，就按照最慢的一件来取）
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 上午9:58:19
	 */
	@SuppressWarnings("unchecked")
	private List<WayBillNoLocusDTO> getWayBillNoLocusFromAir(WaybillEntity waybillEntity, String serialNo) {
		Calendar cal = Calendar.getInstance();
		long startTime = cal.getTimeInMillis();
		List<WayBillNoLocusDTO> tfrlist = new ArrayList<WayBillNoLocusDTO>();
		if (waybillEntity == null)
			return null;
		if (WaybillConstants.AIR_FLIGHT.equals(waybillEntity.getProductCode())||
				WaybillConstants.DEAP.equals(waybillEntity.getProductCode())) {
			// 如果该运单的运输性质是精准空运,增加3个状态：制作航空正单/制作合票清单/制作中转提货清单(加上[商务专递])
			// 制作航空正单
			List<AirWaybillEntity> airWaybillEntityList = trackingService.queryAirWayBillListByWaybillNo(waybillEntity.getWaybillNo());
			for (AirWaybillEntity airWaybillEntity : airWaybillEntityList) {
				// 根据正单号查询合票清单明细
				AirTransPickupBillDto airTransPickupBillDto = new AirTransPickupBillDto();
				if (airWaybillEntity != null) {
					tfrlist.add(getWayBillNoLocusFromAirBill(waybillEntity, airWaybillEntity));
					airTransPickupBillDto.setAirWaybillNo(airWaybillEntity.getAirWaybillNo());
					// 如果正单的状态是待跟踪
					List<AirTrackFlightDto> airTrackinglist = airTrackFlightService.queryTrackInfoByAirWaybillNo(airWaybillEntity.getAirWaybillNo());
					if (airTrackinglist != null && airTrackinglist.size() == 1) {
						// 只有起飞
						tfrlist.add(getWayBillNoLocusFromAirTakeOff(waybillEntity, airTrackinglist.get(0), airWaybillEntity));
					} else if (airTrackinglist != null && airTrackinglist.size() == 2) {
						// 既有起飞也有到达
						tfrlist.add(getWayBillNoLocusFromAirTakeOff(waybillEntity, airTrackinglist.get(0), airWaybillEntity));
						tfrlist.add(getWayBillNoLocusFromAirArrive(waybillEntity, airTrackinglist.get(1), airWaybillEntity));
					}
				}
			}
			//查询正单交接单
			AirHandOverBillDto airHandOverBillDto = new AirHandOverBillDto();
			airHandOverBillDto.setWaybillNo(waybillEntity.getWaybillNo());
			List<AirHandOverBillEntity> airHandOverBillDetailList = airHandOverBillService.queryAirHandOverBill(airHandOverBillDto, Integer.MIN_VALUE, Integer.MAX_VALUE);
			if (airHandOverBillDetailList != null) {
				for (AirHandOverBillEntity airHandOverBillEntity : airHandOverBillDetailList) {
					tfrlist.add(getAirHandOverDetailFromAirBill(waybillEntity, airHandOverBillEntity));
				}
			}
			// 空运拉货
			AirUnshippedGoodsDto airUnshippedGoodsDto = new AirUnshippedGoodsDto();
			airUnshippedGoodsDto.setBillNo(waybillEntity.getWaybillNo());
			List<AirUnshippedGoodsEntity> airUnsiplist = airUnshippedGoodsService.queryUnshippedGoods(airUnshippedGoodsDto, Integer.MIN_VALUE, Integer.MAX_VALUE);
			if (airUnsiplist != null) {
				for (AirUnshippedGoodsEntity airUnshippedGoodsEntity : airUnsiplist) {
					tfrlist.add(getWayBillNoLocusFromAirUnshippedGoods(waybillEntity, airUnshippedGoodsEntity));
				}
			}

			// 根据正单号查询中转提货单
			List<AirPickupbillEntity> airPickupbilllist = airQueryModifyPickupbillService.queryAirPickupbillListForViewTrack(waybillEntity.getWaybillNo());
			if (airPickupbilllist != null && airPickupbilllist.size() > 0) {
				for (AirPickupbillEntity AirPickupbillEntity : airPickupbilllist) {
					tfrlist.add(getAirPickUpBillNoLocusFromAirBill(waybillEntity, AirPickupbillEntity));
				}
			}
			List<AirTransPickupbillEntity> airTransPickupbilllist = airQueryModifyTfrPickupBillService.queryTfrPickupBillListForViewTrack(waybillEntity.getWaybillNo());
			if (airTransPickupbilllist != null) {
				for (AirTransPickupbillEntity AirTransPickupbillEntity : airTransPickupbilllist) {
					tfrlist.add(getAirTransPickUpBillNoLocusFromAirBill(waybillEntity, AirTransPickupbillEntity));
				}
			}
		}
		// 按照制单时间来排序
		Collections.sort(tfrlist, new WayBillNoLocusDTO());
		LOGGER.info("查询运单轨迹中转（空运）部分所用时间：" + (cal.getTimeInMillis() - startTime) + "毫秒");
		return tfrlist;
	}
	
	/**
	 * 305082
	 * 设置电子运单已扫描轨迹
	 */
	private WayBillNoLocusDTO getWayBillNoLocusFromPdaScan(PdaAppInfoEntity pdaAppInfoEntity){
		//有扫描数据
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		if (pdaAppInfoEntity != null && pdaAppInfoEntity.getScan().equals(FossConstants.ACTIVE)) {
			// 设置单据编号
			wayBillNoLocusDTO.setBillNo(pdaAppInfoEntity.getWaybillNo());
			//操作部门
			OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(pdaAppInfoEntity.getOriginateOrgCode());
			if(org != null){
				wayBillNoLocusDTO.setOperateOrgName(org.getName());
			}
			EmployeeEntity employeeEntity = employeeService.querySimpleEmployeeByEmpCode(pdaAppInfoEntity.getDriverCode());
			//操作人
			wayBillNoLocusDTO.setOperateName(employeeEntity != null && employeeEntity.getEmpName() != null ? employeeEntity.getEmpName() : null);
			// 设置操作类型为已扫描
			wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.IS_PDASCAN_INACTIVE);
			// 设置操作时间为扫描时间
			wayBillNoLocusDTO.setOperateTime(pdaAppInfoEntity.getOverTaskTime());
			// 设置车牌号
			wayBillNoLocusDTO.setVehicleNo(pdaAppInfoEntity.getTruckCode());
			// 设置件数（总件数）
			wayBillNoLocusDTO.setOperateNumber(pdaAppInfoEntity.getGoodsQtyTotal());
			// 备注为空
			wayBillNoLocusDTO.setNotes(null);
		}
		return wayBillNoLocusDTO;
	}
	/**
	 * 326027
	 * 设置电子运单完成补录轨迹（完成待补录）
	 */
	private WayBillNoLocusDTO getWayBillNoLocusFromPending(WaybillPendingEntity waybillPendingEntity){
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		// 设置单据编号
		wayBillNoLocusDTO.setBillNo(waybillPendingEntity.getWaybillNo());
		//操作部门
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillPendingEntity.getReceiveOrgCode());
		if(org != null){
			wayBillNoLocusDTO.setOperateOrgName(org.getName());
		}
		//操作人
		wayBillNoLocusDTO.setOperateName("系统自动生成");
		// 设置操作类型为完成待补录
		wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.FINASH_ADDITIONAL_RECORDING);
		// 设置操作时间为交接单创建时间
		wayBillNoLocusDTO.setOperateTime(waybillPendingEntity.getCreateTime());
		// 设置车牌号
		wayBillNoLocusDTO.setVehicleNo(null);
		// 设置件数（总件数）
		wayBillNoLocusDTO.setOperateNumber(waybillPendingEntity.getGoodsQtyTotal());
		// 备注为空
		wayBillNoLocusDTO.setNotes(null);
		return wayBillNoLocusDTO;
	}
	
	/**
	 * 326027
	 * 设置电子运单接货的轨迹（接货中）
	 */
	private WayBillNoLocusDTO getWayBillNoLocusFromDeliver(
			CreateDeliveryReceiptEntity deliveryReceiptEntity,
			EmployeeEntity employeeEntity,LeasedTruckEntity entity) {
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		List<WaybillInfoEntity> list = deliveryReceiptEntity
				.getWaybillInfoEntity();
		String wayBillNo = list.get(0).getWblCode();
		// 设置单据编号
		wayBillNoLocusDTO.setBillNo(wayBillNo);
		if(entity!=null){
			//操作部门
			wayBillNoLocusDTO.setOperateOrgName(entity.getName());
			//操作人
			wayBillNoLocusDTO.setOperateName(deliveryReceiptEntity.getTruckCode());
		}else {
			//changing by 306486 离职工号导致的运单查询异常
			if (employeeEntity!=null) {
				// 设置操作部门
				OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
						.queryOrgAdministrativeInfoByCode(employeeEntity
								.getOrgCode());
				if (org != null) {
					wayBillNoLocusDTO.setOperateOrgName(org.getName());
				}
				// 操作人设置为app人员
				wayBillNoLocusDTO.setOperateName(employeeEntity.getEmpName());
			}
		}
		// 设置操作类型为创建接货任务
		wayBillNoLocusDTO
				.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_PICK_TASK);
		// 设置操作时间为交接单创建时间
		wayBillNoLocusDTO.setOperateTime(deliveryReceiptEntity
				.getCreationTime());
		// 设置车牌号
		wayBillNoLocusDTO.setVehicleNo(deliveryReceiptEntity.getTruckCode());
		// 设置件数（总件数）
		/*wayBillNoLocusDTO.setOperateNumber(deliveryReceiptEntity
				.getTotalPieces());*/
		wayBillNoLocusDTO.setOperateNumber(list.get(0).getPieces());
		// 备注为空
		wayBillNoLocusDTO.setNotes(null);
		return wayBillNoLocusDTO;

	}

	/**
	 * 326027
	 * 设置电子运单接货的轨迹（完成接货）
	 */
	private WayBillNoLocusDTO getWayBillNoLocusFromDeliverFinish(
			CreateDeliveryReceiptEntity deliveryReceiptEntity,
			EmployeeEntity employeeEntity,LeasedTruckEntity entity) {
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		List<WaybillInfoEntity> list = deliveryReceiptEntity
				.getWaybillInfoEntity();
		String wayBillNo = list.get(0).getWblCode();
		// 设置单据编号
		wayBillNoLocusDTO.setBillNo(wayBillNo);
		if(entity!=null){
			//操作部门
			wayBillNoLocusDTO.setOperateOrgName(entity.getName());
			//操作人
			wayBillNoLocusDTO.setOperateName(deliveryReceiptEntity.getTruckCode());
		}else{
			// 设置操作部门
			OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(employeeEntity.getOrgCode());
			if(org != null){
				wayBillNoLocusDTO.setOperateOrgName(org.getName());
			}
			// 操作人设置为登录app人员
			wayBillNoLocusDTO.setOperateName(employeeEntity.getEmpName());
		}
		// 设置操作类型为提交接货任务
		wayBillNoLocusDTO
				.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_PICK_SUBMIT);
		// 设置操作时间为修改时间
		wayBillNoLocusDTO.setOperateTime(deliveryReceiptEntity
				.getModifyTime());
		// 设置车牌号
		wayBillNoLocusDTO.setVehicleNo(deliveryReceiptEntity.getTruckCode());
		// 设置件数（件数）
		/*wayBillNoLocusDTO.setOperateNumber(deliveryReceiptEntity
				.getTotalPieces());*/
		wayBillNoLocusDTO.setOperateNumber(list.get(0).getPieces());
		// 备注为空
		wayBillNoLocusDTO.setNotes(null);
		return wayBillNoLocusDTO;
	}

	/**
	 * 
	 * 设置运单出发的轨迹
	 * 
	 * @returnT
	 * @author ibm-liubinbin
	 * @date Jan 8, 2013 2:02:40 PM
	 */
	private WayBillNoLocusDTO getWayBillNoLocusFromDepart(WaybillEntity waybillEntiy, HandoverBillDTO handoverBillDTO) {
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		wayBillNoLocusDTO.setBillNo(handoverBillDTO.getHandoverNo());
		// 设置操作部门、操作部门名称，操作部门所属城市code，操作部门所属城市名称
		setOrgInfo(handoverBillDTO.getOrigOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.OPERATE_ORG);
		// 设置操作类型为出发
		wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_DEPART);
		// 设置操作时间为出发时间,如果没有出发时间，直接拿到达时间减一秒
		if(handoverBillDTO.getActualDepartTime()==null)
		{
			if(handoverBillDTO.getActualArriveTime()!=null)
			{
				wayBillNoLocusDTO.setOperateTime(DepartureHandle.getOneMinuteTimeBefore(handoverBillDTO.getActualArriveTime()));
			}
		}else{
			wayBillNoLocusDTO.setOperateTime(handoverBillDTO.getActualDepartTime());
		}
		// 操作人设置为司机姓名
		if (handoverBillDTO.getGpsDepartTime() != null) {
			wayBillNoLocusDTO.setOperateName("GPS");
		} else if (handoverBillDTO.getManualDepartUserCode() != null) {
			wayBillNoLocusDTO.setOperateName(employeeService.queryEmployeeByEmpCode(handoverBillDTO.getManualDepartUserCode()) == null ? "" : employeeService.queryEmployeeByEmpCode(handoverBillDTO.getManualDepartUserCode()).getEmpName());
		} else if (handoverBillDTO.getPdaDepartUserCode() != null) {
			wayBillNoLocusDTO.setOperateName(employeeService.queryEmployeeByEmpCode(handoverBillDTO.getPdaDepartUserCode()) == null ? "" : employeeService.queryEmployeeByEmpCode(handoverBillDTO.getPdaDepartUserCode()).getEmpName());
		} else if(handoverBillDTO.getManualDepartUserName()!=null) {
			wayBillNoLocusDTO.setOperateName(handoverBillDTO.getManualDepartUserName());
		}else {
			wayBillNoLocusDTO.setOperateName(handoverBillDTO.getDriverName());
		}
		// 设置车牌号
		wayBillNoLocusDTO.setVehicleNo(handoverBillDTO.getVehicleNo());
		// 设置下一部门编码（通过车辆信息里的车辆到达部门得到）
		setOrgInfo(handoverBillDTO.getDestOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.NEXT_ORG);
		// 设置目的站部门（通过运单表中的最终卸车时间）
		setOrgInfo(waybillEntiy.getLastLoadOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.DEST_ORG);
		// 设置预计到达时间
		wayBillNoLocusDTO.setPlanArriveTime(handoverBillDTO.getPlanArriveTime());
		// 设置件数（开单件数）
		wayBillNoLocusDTO.setOperateNumber(handoverBillDTO.getGoodsQty());
		return wayBillNoLocusDTO;
	}

	/**
	 * 
	 * <p>设置二程接驳理货员出发轨迹</p> 
	 * @author alfred
	 * @date 2015-8-30 下午3:42:00
	 * @param waybillEntiy
	 * @param handoverBillDTO
	 * @return
	 * @see
	 */
	private WayBillNoLocusDTO getWayBillNoLocusFromTallyerDepart(WaybillEntity waybillEntiy, HandoverBillDTO handoverBillDTO) {
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		wayBillNoLocusDTO.setBillNo(handoverBillDTO.getHandoverNo());
		setOrgInfo(handoverBillDTO.getOrigOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.OPERATE_ORG);
		// 设置操作类型为出发
		wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_DEPART);
		wayBillNoLocusDTO.setOperateName(employeeService.queryEmployeeByEmpCode(handoverBillDTO.getManualDepartUserCode()) == null ? "" : employeeService.queryEmployeeByEmpCode(handoverBillDTO.getManualDepartUserCode()).getEmpName());
		// 设置车牌号
		wayBillNoLocusDTO.setVehicleNo(handoverBillDTO.getVehicleNo());
		// 设置件数（开单件数）
		wayBillNoLocusDTO.setOperateNumber(handoverBillDTO.getHandoverGoodsQty());
		//设置操作时间
		wayBillNoLocusDTO.setOperateTime(handoverBillDTO.getActualDepartTime());
		return wayBillNoLocusDTO;
	}
	
	
	/**
	 * 
	 * <p>设置二程接驳理货员到达轨迹</p> 
	 * @author alfred
	 * @date 2015-8-30 下午3:42:00
	 * @param waybillEntiy
	 * @param handoverBillDTO
	 * @return
	 * @see
	 */
	private WayBillNoLocusDTO getWayBillNoLocusFromTallyerArrive(WaybillEntity waybillEntiy, HandoverBillDTO handoverBillDTO) {
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		wayBillNoLocusDTO.setBillNo(handoverBillDTO.getHandoverNo());
		// 设置操作部门、操作部门名称，操作部门所属城市code，操作部门所属城市名称
		setOrgInfo(employeeService.queryEmployeeByEmpCode(handoverBillDTO.getManualArriveUserCode()) == null ? "" : employeeService.queryEmployeeByEmpCode(handoverBillDTO.getManualArriveUserCode()).getOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.OPERATE_ORG);
		// 设置操作类型为到达
		wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_ARRIVE);
		wayBillNoLocusDTO.setOperateName(employeeService.queryEmployeeByEmpCode(handoverBillDTO.getManualArriveUserCode()) == null ? "" : employeeService.queryEmployeeByEmpCode(handoverBillDTO.getManualArriveUserCode()).getEmpName());
		// 设置车牌号
		wayBillNoLocusDTO.setVehicleNo(handoverBillDTO.getVehicleNo());
		// 设置件数（开单件数）
		wayBillNoLocusDTO.setOperateNumber(handoverBillDTO.getHandoverGoodsQty());
		//设置操作时间
		wayBillNoLocusDTO.setOperateTime(handoverBillDTO.getActualArriveTime());
		return wayBillNoLocusDTO;
	}
	
	/**
	 * 
	 * <p>设置二程接驳司机出发轨迹</p> 
	 * @author alfred
	 * @date 2015-8-30 下午3:42:00
	 * @param waybillEntiy
	 * @param handoverBillDTO
	 * @return
	 * @see
	 */
	private WayBillNoLocusDTO getWayBillNoLocusFromDriverDepart(WaybillEntity waybillEntiy, HandoverBillDTO handoverBillDTO) {
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		wayBillNoLocusDTO.setBillNo(handoverBillDTO.getHandoverNo());
		setOrgInfo(handoverBillDTO.getOrigOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.OPERATE_ORG);
		// 设置操作类型为出发
		wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_DEPART);
		wayBillNoLocusDTO.setOperateName(employeeService.queryEmployeeByEmpCode(handoverBillDTO.getManualDepartUserCode()) == null ? "" : employeeService.queryEmployeeByEmpCode(handoverBillDTO.getManualDepartUserCode()).getEmpName());
		// 设置车牌号
		wayBillNoLocusDTO.setVehicleNo(handoverBillDTO.getVehicleNo());
		// 设置件数（开单件数）
		wayBillNoLocusDTO.setOperateNumber(handoverBillDTO.getHandoverGoodsQty());
		//设置操作时间
		wayBillNoLocusDTO.setOperateTime(handoverBillDTO.getActualDepartTime());
		return wayBillNoLocusDTO;
	} 
	
	/**
	 * 
	 * <p>设置二程接驳司机到达轨迹</p> 
	 * @author alfred
	 * @date 2015-8-30 下午3:42:00
	 * @param waybillEntiy
	 * @param handoverBillDTO
	 * @return
	 * @see
	 */
	private WayBillNoLocusDTO getWayBillNoLocusFromDriverArrive(WaybillEntity waybillEntiy, HandoverBillDTO handoverBillDTO) {
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		wayBillNoLocusDTO.setBillNo(handoverBillDTO.getHandoverNo());
		// 设置操作部门、操作部门名称，操作部门所属城市code，操作部门所属城市名称
		setOrgInfo(handoverBillDTO.getDestOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.OPERATE_ORG);
		// 设置操作类型为到达
		wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_ARRIVE);
		wayBillNoLocusDTO.setOperateName(employeeService.queryEmployeeByEmpCode(handoverBillDTO.getManualArriveUserCode()) == null ? "" : employeeService.queryEmployeeByEmpCode(handoverBillDTO.getManualArriveUserCode()).getEmpName());
		// 设置车牌号
		wayBillNoLocusDTO.setVehicleNo(handoverBillDTO.getVehicleNo());
		// 设置件数（开单件数）
		wayBillNoLocusDTO.setOperateNumber(handoverBillDTO.getHandoverGoodsQty());
		//设置操作时间
		wayBillNoLocusDTO.setOperateTime(handoverBillDTO.getActualArriveTime());
		return wayBillNoLocusDTO;
	}
	
	/**
	 * 
	 * <p>设置国际件轨迹</p> 
	 * @author alfred
	 * @date 2016-4-22 下午22:20:15
	 * @param waybillEntiy
	 * @see
	 */
	private WayBillNoLocusDTO getWayBillNoLocusFromInterTrackings(InternationalTrackingEntity entity){
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		// 设置操作类型为出发
		TrackingEventTypeEnum trackEnum = getTrackingEventTypeEnum(entity.getOperateType());
		wayBillNoLocusDTO.setOperateType(trackEnum == null ? "" : trackEnum.getFossTrackCode());
		//设置操作时间
		wayBillNoLocusDTO.setOperateTime(entity.getOperateTime());
		wayBillNoLocusDTO.setBillNo(entity.getWaybillNo());
		wayBillNoLocusDTO.setNotes(entity.getNote());
		return wayBillNoLocusDTO;
	}
	
	/**
	 * 设置零担大件家装轨迹
	 * @param entity
	 * @return
	 */
	private WayBillNoLocusDTO getNoLocusFromDMPTrackings(DMPSynTrackingEntity entity){
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		wayBillNoLocusDTO.setWaybillNo(entity.getWayBillNo());
		wayBillNoLocusDTO.setCurrentStatus(WayBillNoLocusConstant.currentStatusMap.get(entity.getCurrentStatus()));
		wayBillNoLocusDTO.setOperateOrgName(entity.getOperateOrgName());
		wayBillNoLocusDTO.setOperateName(entity.getOperateName());
		wayBillNoLocusDTO.setOperateTypeName(entity.getOperateTypeName());
		wayBillNoLocusDTO.setOperateTime(entity.getOperateTime());
		wayBillNoLocusDTO.setOperateNumber(entity.getOperateNumber());
		wayBillNoLocusDTO.setNotes(entity.getNotes());
		return wayBillNoLocusDTO;
	}
	
	/**获取国际件轨迹枚举*/
	private TrackingEventTypeEnum getTrackingEventTypeEnum(String status){
		return PartiallineConstants.enumMap.get(PartiallineConstants.TRACK_PREFIX + status);
	}
	
	/**
	 *@author alfred
	 *operateType
	   操作类型： 
	      清关完成交接至金浦仓库 -------10--清关到达  ---INTERNATION_CUSTOMS_ARRIVAL
	      货物运输至主要转运中心 -------11--转运场到达 --INTERNATION_TRANSFER_ARRIVAL
	      目的转运中心卸货中----------12--目的站到达 --INTERNATION_DEST_ARRIVAL
	      派送中/派送中(重货专用卡车)---13--派送    -----INTERNATION_DELIVER
	      派送完成/二次派送成功  -------14--签收 ------INTERNATION_SIGN
	      派送异常  ----------------20--派送    -----INTERNATION_EXCEPTION_DELIVER
	      已退回------------------21--已退回   ----INTERNATION_RETURN
	      派送地址更改--------------22--派送拉回  ---INTERNATION_ADDRESS_CHANGE
	      派送未完成 ---------------23--派送拉回  ---INTERNATION_UNDELIVER
	      地址错误  ----------------24--派送拉回  ---INTERNATION_ADDRESS_ERROR
	 */
	/*private String transOperateType(String type){
		String operateType = "";
		if(PartiallineConstants.OPERATE_TYPE_INTER_CUSTOMS_ARRIVAL.equals(type)){
			//关完成交接至金浦仓库 -------10--清关到达
			operateType = WayBillNoLocusConstant.OPERATE_TYPE_INTER_CUSTOMS_ARRIVAL;
		}else if(PartiallineConstants.OPERATE_TYPE_INTER_TRANSFER_ARRIVAL.equals(type)){
			// 货物运输至主要转运中心 -------11--转运场到达
			operateType = WayBillNoLocusConstant.OPERATE_TYPE_INTER_TRANSFER_ARRIVAL;
		}else if(PartiallineConstants.OPERATE_TYPE_INTER_DEST_ARRIVAL.equals(type)){
			// 目的转运中心卸货中----------12--目的站到达 
			operateType = WayBillNoLocusConstant.OPERATE_TYPE_INTER_DEST_ARRIVAL;
		}else if(PartiallineConstants.OPERATE_TYPE_INTER_DELIVER.equals(type)){
			// 派送中/派送中(重货专用卡车)---13--派送
			operateType = WayBillNoLocusConstant.OPERATE_TYPE_INTER_DELIVER;
		}else if(PartiallineConstants.OPERATE_TYPE_INTER_SIGN.equals(type)){
			//派送完成/二次派送成功  -------14--签收
			operateType = WayBillNoLocusConstant.OPERATE_TYPE_INTER_SIGN;
		}else if(PartiallineConstants.OPERATE_TYPE_INTER_EXCEPTION_DELIVER.equals(type)){
			//派送异常  ----------------20--派送
			operateType = WayBillNoLocusConstant.OPERATE_TYPE_INTER_EXCEPTION_DELIVER;
		}else if(PartiallineConstants.OPERATE_TYPE_INTER_RETURN.equals(type)){
			//已退回------------------21--已退回
			operateType = WayBillNoLocusConstant.OPERATE_TYPE_INTER_RETURN;
		}else if(PartiallineConstants.OPERATE_TYPE_INTER_ADDRESS_CHANGE.equals(type)){
			//派送地址更改--------------22--派送拉回
			operateType = WayBillNoLocusConstant.OPERATE_TYPE_INTER_ADDRESS_CHANGE;
		}else if(PartiallineConstants.OPERATE_TYPE_INTER_UNDELIVER.equals(type)){
			//派送未完成 ---------------23--派送拉回
			operateType = WayBillNoLocusConstant.OPERATE_TYPE_INTER_UNDELIVER;
		}else if(PartiallineConstants.OPERATE_TYPE_INTER_ADDRESS_ERROR.equals(type)){
			//地址错误  ----------------24--派送拉回
			operateType = WayBillNoLocusConstant.OPERATE_TYPE_INTER_ADDRESS_ERROR;
		}
		return operateType;
		
	}*/
	/**
	 * 
	 * 设置运单出发的轨迹
	 * 
	 * @returnT
	 * @author ibm-liubinbin
	 * @date Jan 8, 2013 2:02:40 PM
	 */
	private WayBillNoLocusDTO getWayBillNoLocusFromUnload(WaybillEntity waybillEntiy, HandoverBillDTO handoverBillDTO) {
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		wayBillNoLocusDTO.setBillNo(handoverBillDTO.getHandoverNo());
		// 设置操作部门、操作部门名称，操作部门所属城市code，操作部门所属城市名称
		setOrgInfo(handoverBillDTO.getUnloadOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.OPERATE_ORG);
		// 设置操作类型为卸车
		wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_UNLOAD);
		// 设置操作时间为卸车时间
		wayBillNoLocusDTO.setOperateTime(handoverBillDTO.getTaskBeginTime());
		// 操作人设置为卸车人
		wayBillNoLocusDTO.setOperateName(handoverBillDTO.getLoaderName());
		// 设置车牌号
		wayBillNoLocusDTO.setVehicleNo(handoverBillDTO.getVehicleNo());
		// 设置下一部门编码（通过车辆信息里的车辆到达部门得到）
		setOrgInfo(handoverBillDTO.getDestOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.NEXT_ORG);
		// 设置目的站部门（通过运单表中的最终卸车时间）
		setOrgInfo(waybillEntiy.getLastLoadOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.DEST_ORG);
		// 设置预计到达时间
		wayBillNoLocusDTO.setPlanArriveTime(handoverBillDTO.getPlanArriveTime());
		// 设置件数（开单件数）
		wayBillNoLocusDTO.setOperateNumber(handoverBillDTO.getGoodsQty());
		// 设置定位编号 zwd 200968
		wayBillNoLocusDTO.setStockPositionNumber(handoverBillDTO.getStockPositionNumber());
		if(StringUtils.equals(UnloadConstants.UNLOAD_TASK_TYPE_SCEXPRESS, handoverBillDTO.getUnloadType())){
			wayBillNoLocusDTO.setNotes("二程接驳");
		}
		return wayBillNoLocusDTO;
	}

	/**
	 * 
	 * 设置运单出发的轨迹
	 * 
	 * @returnT
	 * @author ibm-liubinbin
	 * @date Jan 8, 2013 2:02:40 PM
	 */
	private WayBillNoLocusDTO getWayBillNoLocusFromUnloadDeliver(WaybillEntity waybillEntiy, HandoverBillDTO handoverBillDTO) {
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		wayBillNoLocusDTO.setBillNo(handoverBillDTO.getHandoverNo());
		// 设置操作部门、操作部门名称，操作部门所属城市code，操作部门所属城市名称
		setOrgInfo(handoverBillDTO.getUnloadOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.OPERATE_ORG);
		// 设置操作类型为接货卸车
		wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_UNLOAD_DELIVER);
		// 设置操作时间为卸车时间
		wayBillNoLocusDTO.setOperateTime(handoverBillDTO.getTaskBeginTime());
		// 操作人设置为卸车人
		wayBillNoLocusDTO.setOperateName(handoverBillDTO.getLoaderName());
		// 设置车牌号
		wayBillNoLocusDTO.setVehicleNo(handoverBillDTO.getVehicleNo());
		// 设置下一部门编码（通过车辆信息里的车辆到达部门得到）
		setOrgInfo(handoverBillDTO.getDestOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.NEXT_ORG);
		// 设置目的站部门（通过运单表中的最终卸车时间）
		setOrgInfo(waybillEntiy.getLastLoadOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.DEST_ORG);
		// 设置预计到达时间
		wayBillNoLocusDTO.setPlanArriveTime(handoverBillDTO.getPlanArriveTime());
		// 设置件数（开单件数）
		wayBillNoLocusDTO.setOperateNumber(handoverBillDTO.getGoodsQty());
		return wayBillNoLocusDTO;
	}
	/**
	 * 设置叉车司机扫描货物轨迹
	 * @author 105869-heyongdong
	 * @date 2014年9月9日 17:25:29
	 * */
	private WayBillNoLocusDTO getWayBillNoLocusFormTrayScanTask(WaybillEntity waybillEntiy, HandoverBillDTO handoverBillDTO){
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		wayBillNoLocusDTO.setBillNo(handoverBillDTO.getTaskNo());
		wayBillNoLocusDTO.setOperateOrgName(handoverBillDTO.getForkliftOrgName());
		// 设置操作类型为叉车司机扫描 
		wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_TRAY_SCAN);
		// 设置操作时间为扫描时间
		wayBillNoLocusDTO.setOperateTime(handoverBillDTO.getTrayScanaTime());
		// 操作人设置为叉车司机
		wayBillNoLocusDTO.setOperateName(handoverBillDTO.getForkliftDriverName());
		// 设置车牌号为空
		wayBillNoLocusDTO.setVehicleNo(null);
		// 设置件数该托盘绑定的该运单总件数
		wayBillNoLocusDTO.setOperateNumber(handoverBillDTO.getScanGoodsQty());
		return wayBillNoLocusDTO;
	}
	/**
	 * 
	 * 装车扫描轨迹
	 * 
	 * @returnT
	 * @author ibm-liubinbin
	 * @date Jan 8, 2013 2:02:40 PM
	 */
	private WayBillNoLocusDTO getWayBillNoLocusFromLoad(WaybillEntity waybillEntiy, LoadDetailEntity loadDetailEntity) {
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		wayBillNoLocusDTO.setBillNo(loadDetailEntity.getWaybillNo());
		wayBillNoLocusDTO.setOperateOrgName(loadDetailEntity.getLoadOrgName());
		// 设置操作类型为卸车
		wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_LOAD_SCAN);
		// 设置操作时间为卸车时间
		wayBillNoLocusDTO.setOperateTime(loadDetailEntity.getLoadStartTime());
		// 操作人设置为卸车人
		wayBillNoLocusDTO.setOperateName(loadDetailEntity.getLoaderName());
		// 设置车牌号
		wayBillNoLocusDTO.setVehicleNo(loadDetailEntity.getVehicleNo());
		// 设置件数（开单件数）
		wayBillNoLocusDTO.setOperateNumber(loadDetailEntity.getLoadQty());
		if(StringUtils.equals(loadDetailEntity.getTaskType(),
				WayBillNoLocusConstant.TRAJICTORY_EXPRESS_SENDPIECE_LOAD)
				||StringUtils.equals(loadDetailEntity.getTaskType(),
						WayBillNoLocusConstant.TRAJICTORY_EXPRESS_CONNECTION_LOAD)
						||StringUtils.equals(loadDetailEntity.getTaskType(),
								WayBillNoLocusConstant.TRAJICTORY_EXPRESS_DRIVER_LOAD)){
			wayBillNoLocusDTO.setNotes("二程接驳");
		}
		return wayBillNoLocusDTO;
	}

	/**
	 * 
	 * 交接轨迹
	 * 
	 * @returnT
	 * @author ibm-liubinbin
	 * @date Jan 8, 2013 2:02:40 PM
	 */
	private WayBillNoLocusDTO getWayBillNoLocusFromHandover(WaybillEntity waybillEntiy, HandoverBillDTO ha) {
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		wayBillNoLocusDTO.setBillNo(ha.getHandoverNo());
		wayBillNoLocusDTO.setOperateOrgName(ha.getOrigOrgname());
		// 设置操作类型为交接
		wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_HANDOVER);
		// 设置操作时间为交接
		wayBillNoLocusDTO.setOperateTime(ha.getCreateTime());
		// 操作人设置为卸车人
		wayBillNoLocusDTO.setOperateName(ha.getCreateUserName());
		// 设置车牌号
		wayBillNoLocusDTO.setVehicleNo(ha.getVehicleNo());
		// 设置件数（开单件数）
		wayBillNoLocusDTO.setOperateNumber(ha.getHandoverGoodsQty());
		return wayBillNoLocusDTO;
	}

	/**
	 * 
	 * 外发交接
	 * 
	 * @returnT
	 * @author ibm-liubinbin
	 * @date Jan 8, 2013 2:02:40 PM
	 */
	private WayBillNoLocusDTO getWayBillNoLocusFromPartline(WaybillEntity waybillEntiy, HandoverBillDTO ha) {
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		wayBillNoLocusDTO.setBillNo(ha.getHandoverNo());
		wayBillNoLocusDTO.setOperateOrgName(ha.getOrigOrgname());
		// 设置操作类型为外发交接
		wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_PART_LINE);
		// 设置操作时间为交接
		wayBillNoLocusDTO.setOperateTime(ha.getCreateTime());
		// 操作人设置为卸车人
		wayBillNoLocusDTO.setOperateName(ha.getCreateUserName());
		// 设置车牌号
		wayBillNoLocusDTO.setVehicleNo(ha.getVehicleNo());
		// 设置件数（开单件数）
		wayBillNoLocusDTO.setOperateNumber(ha.getHandoverGoodsQty());
		return wayBillNoLocusDTO;
	}
	/**
	 * 
	 * 快递:快递代理交接
	 * 
	 * @returnT
	 * @author duyi
	 * @date Jan 8, 2013 2:02:40 PM
	 */
	private WayBillNoLocusDTO getWayBillNoLocusFromLDPHandOver(WaybillEntity waybillEntiy, HandoverBillDTO ha) {
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		wayBillNoLocusDTO.setBillNo(ha.getHandoverNo());
		wayBillNoLocusDTO.setOperateOrgName(ha.getOrigOrgname());
		// 设置操作类型为快递代理交接
		wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_LDP_HANDOVER);
		// 设置操作时间为交接
		wayBillNoLocusDTO.setOperateTime(ha.getCreateTime());
		// 操作人设置为卸车人
		wayBillNoLocusDTO.setOperateName(ha.getCreateUserName());
		// 设置车牌号
		wayBillNoLocusDTO.setVehicleNo(ha.getVehicleNo());
		// 设置件数（开单件数）
		wayBillNoLocusDTO.setOperateNumber(ha.getHandoverGoodsQty());
		return wayBillNoLocusDTO;
	}
	/**
	 * 
	 * 打包装扫描
	 * 
	 * @returnT
	 * @author ibm-liubinbin
	 * @date Jan 8, 2013 2:02:40 PM
	 */
	private WayBillNoLocusDTO getWayBillNoLocusFromPackage(WaybillEntity waybillEntiy, HandoverBillDTO ha) {
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		wayBillNoLocusDTO.setBillNo(ha.getWaybillNo());
		wayBillNoLocusDTO.setOperateOrgName(ha.getOrgName());
		// 设置操作类型为交接
		wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_PACKAGE);
		// 设置操作时间为交接
		wayBillNoLocusDTO.setOperateTime(ha.getCreateTime());
		// 操作人设置为卸车人
		wayBillNoLocusDTO.setOperateName(ha.getCreateUserName());
		// 设置件数（开单件数）
		wayBillNoLocusDTO.setOperateNumber(ha.getPackedNum());
		return wayBillNoLocusDTO;
	}

	/**
	 * 
	 * 登入包装货区
	 * 
	 * @returnT
	 * @author ibm-liubinbin
	 * @date Jan 8, 2013 2:02:40 PM
	 */
	private WayBillNoLocusDTO getWayBillNoLocusFromPackageAreaIn(WaybillEntity waybillEntiy, HandoverBillDTO ha, String operatorType) {
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		wayBillNoLocusDTO.setBillNo(waybillEntiy.getWaybillNo());
		// 设置操作部门、操作部门名称，操作部门所属城市code，操作部门所属城市名称
		setOrgInfo(ha.getOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.OPERATE_ORG);
		// 设置操作类型为交接
		wayBillNoLocusDTO.setOperateType(operatorType);
		// 设置操作时间为交接
		wayBillNoLocusDTO.setOperateTime(ha.getCreateTime());
		// 操作人设置为卸车人
		wayBillNoLocusDTO.setOperateName(ha.getCreateUserName());
		// 设置件数（开单件数）
		wayBillNoLocusDTO.setOperateNumber(ha.getPackedNum());
		return wayBillNoLocusDTO;
	}

	/**
	 * 
	 * 外发单录入
	 * 
	 * @returnT
	 * @author ibm-liubinbin
	 * @date Jan 8, 2013 2:02:40 PM
	 */
	private WayBillNoLocusDTO getWayBillNoLocusFromPartialLine(WaybillEntity waybillEntiy, ExternalBillDto ha) {
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		wayBillNoLocusDTO.setBillNo(ha.getExternalBillNo());
		wayBillNoLocusDTO.setOperateOrgName(ha.getExternalOrgName());
		// 设置操作类型为交接
		wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_PARTIAL_LINE);
		// 设置操作时间为交接
		wayBillNoLocusDTO.setOperateTime(ha.getRegisterTime());
		// 操作人设置为录入人
		wayBillNoLocusDTO.setOperateName(ha.getExternalUserName());
		wayBillNoLocusDTO.setNotes("外发代理:" + ha.getOrigOrgName() + "联系电话:" + ha.getContactPhone());
		return wayBillNoLocusDTO;
	}
	/**
	 * 
	 * 快递:快递代理外发录入
	 * 
	 * @returnT
	 * @author duyi
	 * @date Jan 8, 2013 2:02:40 PM
	 */
	private WayBillNoLocusDTO getWayBillNoLocusFromLDPPartialLine(WaybillEntity waybillEntiy, LdpExternalBillDto ha) {
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		wayBillNoLocusDTO.setBillNo(ha.getExternalBillNo());
		wayBillNoLocusDTO.setOperateOrgName(ha.getExternalOrgName());
		// 设置操作类型为交接
		wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_LDP_PARTIAL_LINE);
		// 设置操作时间为交接
		wayBillNoLocusDTO.setOperateTime(ha.getRegisterTime());
		// 操作人设置为卸车人
		wayBillNoLocusDTO.setOperateName(ha.getExternalUserName());
		//代理网点+代理网点电话
		wayBillNoLocusDTO.setNotes("外发代理:" + ha.getAgentOrgName() + "联系电话:" + ha.getAgentOrgPhone());
		return wayBillNoLocusDTO;
	}
	/**
	 * 
	 * 快递:快递代理外发轨迹
	 * 
	 * @returnT
	 * @author duyi
	 * @date Jan 8, 2013 2:02:40 PM
	 */
	private WayBillNoLocusDTO getWayBillNoLocusFromLDPTrask(WaybillEntity waybillEntiy, LdpExternalBillTrackDto ldpTrack) {
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		wayBillNoLocusDTO.setBillNo(waybillEntiy.getWaybillNo());
		//代理部门 快递代理轨迹操作部门不显示
//		wayBillNoLocusDTO.setOperateOrgName(ldpTrack.getAgentCompanyName());
		//设置操作类型为交接
		if(PartiallineConstants.OPERATE_TYPE_LDP_TRACK_ARRIVE.equals(ldpTrack.getOperationtype())){
			wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_LDP_TRACK_ARRIVE);//到达
		}else if(PartiallineConstants.OPERATE_TYPE_LDP_TRACK_LEAVE.equals(ldpTrack.getOperationtype())){
			wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_LDP_TRACK_LEAVE);//离开
		}else if(PartiallineConstants.OPERATE_TYPE_LDP_TRACK_DELIVER.equals(ldpTrack.getOperationtype())){
			wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_LDP_TRACK_DELIVER);//派件
		}else if(PartiallineConstants.OPERATE_TYPE_LDP_TRACK_DELIVER_FAIL.equals(ldpTrack.getOperationtype())){
			wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_LDP_TRACK_DELIVER_FAIL);//派件失败
		}else if(PartiallineConstants.OPERATE_TYPE_LDP_TRACK_RETURN.equals(ldpTrack.getOperationtype())){
			wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_LDP_TRACK_RETURN);//货物拉回德邦
		}else if(PartiallineConstants.OPERATE_TYPE_LDP_TRACK_DEFUSE_SIGN.equals(ldpTrack.getOperationtype())){
			wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_LDP_TRACK_DEFUSE_SIGN);//拒签
		}else if(PartiallineConstants.OPERATE_TYPE_LDP_TRACK_DELIVERY.equals(ldpTrack.getOperationtype())){
			wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_LDP_TRACK_DELIVERY);//已配达
		}
		//操作城市
		wayBillNoLocusDTO.setOperateCityName(ldpTrack.getOperationcity());
		// 操作时间
		wayBillNoLocusDTO.setOperateTime(ldpTrack.getOperationTime());
		// 操作人
		wayBillNoLocusDTO.setOperateName(ldpTrack.getOperationUserName());
		//ISSUE-4469 操作描述
		//类型为派件时，备注统一显示为“派送中”，其他类型显示代理信息备注
		if(PartiallineConstants.OPERATE_TYPE_LDP_TRACK_DELIVER.equals(ldpTrack.getOperationtype())) {
			wayBillNoLocusDTO.setNotes("派送中");
		}else if(PartiallineConstants.OPERATE_TYPE_LDP_TRACK_DELIVERY.equals(ldpTrack.getOperationtype())){
			wayBillNoLocusDTO.setNotes("客户已签收");
		}else {
			wayBillNoLocusDTO.setNotes(ldpTrack.getOperationDescribe());
		}
		return wayBillNoLocusDTO;
	}
	/**
	 * @author nly
	 * @date 2015年2月4日 下午2:13:53
	 * @function 快递：营业部外发轨迹
	 * @param waybillEntiy
	 * @param recordEntity
	 * @return
	 */
	private WayBillNoLocusDTO getWayBillNoLocusFromSDTrack(WaybillEntity waybillEntiy,PrintAgentWaybillRecordEntity recordEntity) {
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		//单据编号为运单号
		wayBillNoLocusDTO.setBillNo(waybillEntiy.getWaybillNo());
		// 设置操作部门、操作部门名称，操作部门所属城市code，操作部门所属城市名称
		setOrgInfo(recordEntity.getOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.OPERATE_ORG);
		// 设置操作类型为已转寄
		wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_SD_TRACK_TRANSFER);
		// 设置操作时间为绑定时间
		wayBillNoLocusDTO.setOperateTime(recordEntity.getOperatTime());
		// 操作人设置为绑定人
		wayBillNoLocusDTO.setOperateName(recordEntity.getOperatorName());
		// 设置件数为开单件数
		wayBillNoLocusDTO.setOperateNumber(waybillEntiy.getGoodsQtyTotal());
		//备注
		wayBillNoLocusDTO.setNotes("已转寄" + recordEntity.getAgentCompanyName() + ",单号"+recordEntity.getAgentWaybillNo());
		return wayBillNoLocusDTO;
	}
	/**
	 * 
	 * 登入包装货区（贵重）
	 * 
	 * @returnT
	 * @author ibm-liubinbin
	 * @date Jan 8, 2013 2:02:40 PM
	 */
	private WayBillNoLocusDTO getWayBillNoLocusFromPackageAreaInEXP(WaybillEntity waybillEntiy, HandoverBillDTO ha) {
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		wayBillNoLocusDTO.setBillNo(waybillEntiy.getWaybillNo());
		// 设置操作部门、操作部门名称，操作部门所属城市code，操作部门所属城市名称
		setOrgInfo(ha.getOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.OPERATE_ORG);
		// 设置操作类型为交接
		wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_PACKAGE_AREA_IN_EXP);
		// 设置操作时间为交接
		wayBillNoLocusDTO.setOperateTime(ha.getCreateTime());
		// 操作人设置为卸车人
		wayBillNoLocusDTO.setOperateName(ha.getCreateUserName());
		// 设置件数（开单件数）
		wayBillNoLocusDTO.setOperateNumber(ha.getPackedNum());
		return wayBillNoLocusDTO;
	}

	/**
	 * 
	 * 登出包装货区（贵重）
	 * 
	 * @returnT
	 * @author ibm-liubinbin
	 * @date Jan 8, 2013 2:02:40 PM
	 */
	private WayBillNoLocusDTO getWayBillNoLocusFromPackageAreaOutEXP(WaybillEntity waybillEntiy, HandoverBillDTO ha) {
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		wayBillNoLocusDTO.setBillNo(waybillEntiy.getWaybillNo());
		// 设置操作部门、操作部门名称，操作部门所属城市code，操作部门所属城市名称
		setOrgInfo(ha.getOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.OPERATE_ORG);
		// 设置操作类型为交接
		wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_PACKAGE_AREA_OUT_EXP);
		// 设置操作时间为交接
		wayBillNoLocusDTO.setOperateTime(ha.getCreateTime());
		// 操作人设置为卸车人
		wayBillNoLocusDTO.setOperateName(ha.getCreateUserName());
		// 设置件数（开单件数）
		wayBillNoLocusDTO.setOperateNumber(ha.getPackedNum());
		return wayBillNoLocusDTO;
	}

	/**
	 * 
	 * 空运拉货
	 * 
	 * @returnT
	 * @author ibm-liubinbin
	 * @date Jan 8, 2013 2:02:40 PM
	 */
	private WayBillNoLocusDTO getWayBillNoLocusFromAirUnshippedGoods(WaybillEntity waybillEntiy, AirUnshippedGoodsEntity ha) {
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		wayBillNoLocusDTO.setBillNo(ha.getBillNo());
		wayBillNoLocusDTO.setOperateOrgName(ha.getAirAssembleOrgName());
		// 设置操作类型为交接
		wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_AIR_UN_SHIPPED_GOODS);
		// 设置操作时间为交接
		wayBillNoLocusDTO.setOperateTime(ha.getCreateTime());
		// 操作人设置为卸车人
		wayBillNoLocusDTO.setOperateName(ha.getCreateUserName());
		// 设置件数（开单件数）
		wayBillNoLocusDTO.setOperateNumber(ha.getGoodsQtyTotal());
		return wayBillNoLocusDTO;
	}

	/**
	 * 
	 * 清仓入库
	 * 
	 * @returnT
	 * @author ibm-liubinbin
	 * @date Jan 8, 2013 2:02:40 PM
	 */
	private WayBillNoLocusDTO getWayBillNoLocusFromStockCheckingMore(WaybillEntity waybillEntiy, HandoverBillDTO ha) {
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		wayBillNoLocusDTO.setBillNo(waybillEntiy.getWaybillNo());
		// 设置操作部门、操作部门名称，操作部门所属城市code，操作部门所属城市名称
		setOrgInfo(ha.getOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.OPERATE_ORG);
		// 设置操作类型为交接
		wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_STOCKCHECKING_MORE);
		// 设置操作时间为交接
		wayBillNoLocusDTO.setOperateTime(ha.getCreateTime());
		// 操作人设置为卸车人
		wayBillNoLocusDTO.setOperateName(ha.getCreateUserName());
		// 设置件数（开单件数）
		wayBillNoLocusDTO.setOperateNumber(ha.getPackedNum());
		/**
		 * 设置定位编号 zwd 200968
		 */
		wayBillNoLocusDTO.setStockPositionNumber(ha.getStockPositionNumber());
		return wayBillNoLocusDTO;
	}

	/**
	 * 
	 * 其他的入库
	 * 
	 * @returnT
	 * @author ibm-liubinbin
	 * @date Jan 8, 2013 2:02:40 PM
	 */
	private WayBillNoLocusDTO getWayBillNoLocusFromStockOther(WaybillEntity waybillEntiy, HandoverBillDTO ha) {
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		wayBillNoLocusDTO.setBillNo(waybillEntiy.getWaybillNo());
		// 设置操作部门、操作部门名称，操作部门所属城市code，操作部门所属城市名称
		setOrgInfo(ha.getOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.OPERATE_ORG);
		// 设置操作时间为交接
		wayBillNoLocusDTO.setOperateTime(ha.getCreateTime());
		// 操作人设置为卸车人
		wayBillNoLocusDTO.setOperateName(ha.getCreateUserName());
		// 设置件数（开单件数）
		wayBillNoLocusDTO.setOperateNumber(ha.getPackedNum());
		/**
		 * 设置定位编号 zwd 200968
		 */
		wayBillNoLocusDTO.setStockPositionNumber(ha.getStockPositionNumber());
		return wayBillNoLocusDTO;
	}

	/**
	 * 
	 * 拉回入库
	 * 
	 * @returnT
	 * @author ibm-liubinbin
	 * @date Jan 8, 2013 2:02:40 PM
	 */
	private WayBillNoLocusDTO getWayBillNoLocusFromUnshippedStockCheckingMore(WaybillEntity waybillEntiy, HandoverBillDTO ha) {
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		wayBillNoLocusDTO.setBillNo(waybillEntiy.getWaybillNo());
		// 设置操作部门、操作部门名称，操作部门所属城市code，操作部门所属城市名称
		setOrgInfo(ha.getOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.OPERATE_ORG);
		// 设置操作类型为交接
		wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_STOCKCHECKING_MORE);
		// 设置操作时间为交接
		wayBillNoLocusDTO.setOperateTime(ha.getCreateTime());
		// 操作人设置为卸车人
		wayBillNoLocusDTO.setOperateName(ha.getCreateUserName());
		// 设置件数（开单件数）
		wayBillNoLocusDTO.setOperateNumber(ha.getPackedNum());
		/**
		 * 设置定位编号 zwd 200968
		 */
		wayBillNoLocusDTO.setStockPositionNumber(ha.getStockPositionNumber());
		return wayBillNoLocusDTO;
	}

	/**
	 * 
	 * 单票入库
	 * 
	 * @returnT
	 * @author ibm-liubinbin
	 * @date Jan 8, 2013 2:02:40 PM
	 */
	private WayBillNoLocusDTO getWayBillNoLocusFromStockCheckingNormal(WaybillEntity waybillEntiy, HandoverBillDTO ha) {
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		wayBillNoLocusDTO.setBillNo(waybillEntiy.getWaybillNo());
		// 设置操作部门、操作部门名称，操作部门所属城市code，操作部门所属城市名称
		setOrgInfo(ha.getOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.OPERATE_ORG);
		// 设置操作类型为交接
		wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_STOCKCHECKING_NORMAL);
		// 设置操作时间为交接
		wayBillNoLocusDTO.setOperateTime(ha.getCreateTime());
		// 操作人设置为卸车人
		wayBillNoLocusDTO.setOperateName(ha.getCreateUserName());
		// 设置件数（开单件数）
		wayBillNoLocusDTO.setOperateNumber(ha.getPackedNum());
		/**
		 * 设置定位编号 zwd 200968
		 */
		wayBillNoLocusDTO.setStockPositionNumber(ha.getStockPositionNumber());
		return wayBillNoLocusDTO;
	}

	/**
	 * 
	 * 装车提交轨迹
	 * 
	 * @returnT
	 * @author ibm-liubinbin
	 * @date Jan 8, 2013 2:02:40 PM
	 */
	private WayBillNoLocusDTO getWayBillNoLocusFromLoadSubmit(WaybillEntity waybillEntiy, LoadDetailEntity loadDetailEntity) {
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		wayBillNoLocusDTO.setBillNo(loadDetailEntity.getWaybillNo());
		wayBillNoLocusDTO.setOperateOrgName(loadDetailEntity.getLoadOrgName());
		// 设置操作类型为卸车
		wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_LOAD_SUBMIT);
		// 设置操作时间为卸车时间
		wayBillNoLocusDTO.setOperateTime(loadDetailEntity.getLoadEndTime());
		// 操作人设置为卸车人
		wayBillNoLocusDTO.setOperateName(loadDetailEntity.getLoaderName());
		// 设置车牌号
		wayBillNoLocusDTO.setVehicleNo(loadDetailEntity.getVehicleNo());
		// 设置件数（开单件数）
		wayBillNoLocusDTO.setOperateNumber(loadDetailEntity.getLoadQty());
		if(StringUtils.equals(loadDetailEntity.getTaskType(),
				WayBillNoLocusConstant.TRAJICTORY_EXPRESS_SENDPIECE_LOAD)
				||StringUtils.equals(loadDetailEntity.getTaskType(),
						WayBillNoLocusConstant.TRAJICTORY_EXPRESS_CONNECTION_LOAD)
						||StringUtils.equals(loadDetailEntity.getTaskType(),
								WayBillNoLocusConstant.TRAJICTORY_EXPRESS_DRIVER_LOAD)){
			wayBillNoLocusDTO.setNotes("二程接驳");
		}
		return wayBillNoLocusDTO;
	}

	/**
	 * 
	 * 设置空运正单的轨迹
	 * 
	 * @param waybillEntity waybillNo 运单号 orderNo 订单号 orderChannel 订单来源
	 *            orderPaidMethod 订单付款方式 deliveryCustomerId 发货客户ID
	 *            deliveryCustomerCode 发货客户编码 deliveryCustomerName 发货客户名称
	 *            deliveryCustomerMobilephone 发货客户手机 deliveryCustomerPhone
	 *            发货客户电话 deliveryCustomerContact 发货客户联系人
	 *            deliveryCustomerNationCode 发货国家 deliveryCustomerProvCode 发货省份
	 *            deliveryCustomerCityCode 发货市 deliveryCustomerDistCode 发货区
	 *            deliveryCustomerAddress 发货具体地址 receiveCustomerId 收货客户ID
	 *            receiveCustomerCode 收货客户编码 receiveCustomerName 收货客户名称
	 *            receiveCustomerMobilephone 收货客户手机 receiveCustomerPhone 收货客户电话
	 *            receiveCustomerContact 收货客户联系人 receiveCustomerNationCode 收货国家
	 *            receiveCustomerProvCode 收货省份 receiveCustomerCityCode 收货市
	 *            receiveCustomerDistCode 收货区 receiveCustomerAddress 收货具体地址
	 *            receiveOrgCode 收货部门(出发部门) productId 产品ID productCode 运输性质
	 *            receiveMethod 提货方式 customerPickupOrgCode 提货网点 loadMethod 配载类型
	 *            targetOrgCode 目的站 pickupToDoor 是否上门接货 driverCode 司机
	 *            pickupCentralized 是否集中接货 loadLineCode 配载线路 loadOrgCode 配载部门
	 *            lastLoadOrgCode 最终配载部门 preDepartureTime 预计出发时间
	 *            preCustomerPickupTime 预计派送/提货时间 carDirectDelivery 是否大车直送
	 *            goodsName 货物名称 goodsQtyTotal 货物总件数 goodsWeightTotal 货物总重量
	 *            goodsVolumeTotal 货物总体积 goodsSize 货物尺寸 goodsTypeCode 货物类型
	 *            preciousGoods 是否贵重物品 specialShapedGoods 是否异形物品 outerNotes 对外备注
	 *            innerNotes 对内备注
	 * @returnT
	 * @author ibm-liubinbin
	 * @date Jan 8, 2013 2:02:40 PM
	 */
	private WayBillNoLocusDTO getWayBillNoLocusFromAirBill(WaybillEntity waybillEntiy, AirWaybillEntity airWaybillEntity) {
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		wayBillNoLocusDTO.setBillNo(airWaybillEntity.getAirWaybillNo());
		// 设置操作部门、操作部门名称，操作部门所属城市code，操作部门所属城市名称
		setOrgInfo(airWaybillEntity.getCreateOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.OPERATE_ORG);
		// 设置操作类型为出发
		wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_AIR_HANDOVER_BILL);
		// 设置操作时间为起飞时间
		wayBillNoLocusDTO.setOperateTime(airWaybillEntity.getCreateTime());
		// 操作人设置为制单人名称
		wayBillNoLocusDTO.setOperateName(airWaybillEntity.getCreateUserName());
		// 设置车牌号(航空公司二字码)
		wayBillNoLocusDTO.setVehicleNo(airWaybillEntity.getFlightNo());
		// 设置下一部门编码（通过车辆信息里的车辆到达部门得到）
		setOrgInfo(airWaybillEntity.getDestOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.NEXT_ORG);
		// 设置目的站部门（通过运单表中的最终卸车时间）
		setOrgInfo(waybillEntiy.getLastLoadOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.DEST_ORG);
		// 设置预计到达时间(就是到达时间)
		wayBillNoLocusDTO.setPlanArriveTime(airWaybillEntity.getArriveTime());
		// 设置件数（开单件数）
		wayBillNoLocusDTO.setOperateNumber(airWaybillEntity.getGoodsQty());
		wayBillNoLocusDTO.setNotes("预计航班起飞时间:" + DepartureHandle.getDateStr(airWaybillEntity.getTakeOffTime()) + ";航班运价:" + airWaybillEntity.getFee() + "元每公斤");
		return wayBillNoLocusDTO;
	}

	/**
	 * 航空起飞
	 * 
	 * @param waybillEntiy
	 * @param airWaybillEntity
	 * @return
	 */
	private WayBillNoLocusDTO getWayBillNoLocusFromAirTakeOff(WaybillEntity waybillEntiy, AirTrackFlightDto airTrackFlightDto, AirWaybillEntity airWaybillEntity) {
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		wayBillNoLocusDTO.setBillNo(airTrackFlightDto.getAirWaybillNo());
		// 设置操作部门、操作部门名称，操作部门所属城市code，操作部门所属城市名称
		setOrgInfo(airTrackFlightDto.getCreateOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.OPERATE_ORG);
		// 设置操作类型为出发
		wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_AIR_TAKE_OFF);
		// 设置操作时间为起飞时间
		wayBillNoLocusDTO.setOperateTime(airTrackFlightDto.getActualTakeOffTime());
		// 操作人设置为制单人名称
		wayBillNoLocusDTO.setOperateName(airTrackFlightDto.getCreateUserName());
		// 设置车牌号(航空公司二字码)
		wayBillNoLocusDTO.setVehicleNo(airWaybillEntity.getFlightNo());
		// 设置下一部门编码（通过车辆信息里的车辆到达部门得到）
		setOrgInfo(airWaybillEntity.getDestOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.NEXT_ORG);
		// 设置目的站部门（通过运单表中的最终卸车时间）
		setOrgInfo(waybillEntiy.getLastLoadOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.DEST_ORG);
		// 设置预计到达时间(就是到达时间)
		wayBillNoLocusDTO.setPlanArriveTime(airWaybillEntity.getArriveTime());
		// 设置件数（开单件数）
		wayBillNoLocusDTO.setOperateNumber(airWaybillEntity.getGoodsQty());
		wayBillNoLocusDTO.setNotes(airTrackFlightDto.getMessage());
		return wayBillNoLocusDTO;
	}

	/**
	 * 航空到达
	 * 
	 * @param waybillEntiy
	 * @param airWaybillEntity
	 * @return
	 */
	private WayBillNoLocusDTO getWayBillNoLocusFromAirArrive(WaybillEntity waybillEntiy, AirTrackFlightDto airTrackFlightDto, AirWaybillEntity airWaybillEntity) {
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		wayBillNoLocusDTO.setBillNo(airWaybillEntity.getAirWaybillNo());
		// 设置操作部门、操作部门名称，操作部门所属城市code，操作部门所属城市名称
		setOrgInfo(airTrackFlightDto.getCreateOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.OPERATE_ORG);
		// 设置操作类型为航班到达
		wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_AIR_ARRIVE);
		// 设置操作时间为到达时间
		wayBillNoLocusDTO.setOperateTime(airTrackFlightDto.getActualArriveTime());
		// 操作人设置为制单人名称
		wayBillNoLocusDTO.setOperateName(airTrackFlightDto.getCreateUserName());
		// 设置车牌号(航空公司二字码)
		wayBillNoLocusDTO.setVehicleNo(airWaybillEntity.getFlightNo());
		// 设置下一部门编码（通过车辆信息里的车辆到达部门得到）
		setOrgInfo(airWaybillEntity.getDestOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.NEXT_ORG);
		// 设置目的站部门（通过运单表中的最终卸车时间）
		setOrgInfo(waybillEntiy.getLastLoadOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.DEST_ORG);
		// 设置预计到达时间(就是到达时间)
		wayBillNoLocusDTO.setPlanArriveTime(airWaybillEntity.getArriveTime());
		// 设置件数（开单件数）
		wayBillNoLocusDTO.setOperateNumber(airWaybillEntity.getGoodsQty());
		wayBillNoLocusDTO.setNotes(airTrackFlightDto.getMessage());
		return wayBillNoLocusDTO;
	}

	/**
	 * 
	 * 设置空运合大票清单的轨迹
	 * 
	 * @param wayBillNo
	 * @returnT
	 * @author ibm-liubinbin
	 * @date Jan 8, 2013 2:02:40 PM
	 */
	private WayBillNoLocusDTO getAirPickUpBillNoLocusFromAirBill(WaybillEntity waybillEntiy, AirPickupbillEntity airPickupbillEntity) {
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		wayBillNoLocusDTO.setBillNo(airPickupbillEntity.getAirWaybillNo());
		// 设置操作部门、操作部门名称，操作部门所属城市code，操作部门所属城市名称
		setOrgInfo(airPickupbillEntity.getOrigOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.OPERATE_ORG);
		// 设置操作类型为出发
		wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_AIR_PICKUPBILL);
		// 设置操作时间为修改时间
		wayBillNoLocusDTO.setOperateTime(airPickupbillEntity.getCreateTime());
		// 操作人设置为制单人名称
		wayBillNoLocusDTO.setOperateName(airPickupbillEntity.getCreateUserName());
		// 设置车牌号(航空公司二字码)
		wayBillNoLocusDTO.setVehicleNo(airPickupbillEntity.getFlightNo());
		// 设置下一部门编码（通过车辆信息里的车辆到达部门得到）
		setOrgInfo(airPickupbillEntity.getDestOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.NEXT_ORG);
		// 设置目的站部门（通过运单表中的最终卸车时间）
		setOrgInfo(waybillEntiy.getLastLoadOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.DEST_ORG);
		// 设置预计到达时间(就是到达时间)
		// wayBillNoLocusDTO.setPlanArriveTime(airPickupbillEntity.getArriveTime());
		// 设置件数（开单件数）
		wayBillNoLocusDTO.setOperateNumber(airPickupbillEntity.getGoodsQtyTotal());
		return wayBillNoLocusDTO;
	}

	/**
	 * 
	 * 设置空运中转提货清单的轨迹
	 * 
	 * @param waybillEntity waybillNo 运单号 orderNo 订单号 orderChannel 订单来源
	 *            orderPaidMethod 订单付款方式 deliveryCustomerId 发货客户ID
	 *            deliveryCustomerCode 发货客户编码 deliveryCustomerName 发货客户名称
	 *            deliveryCustomerMobilephone 发货客户手机 deliveryCustomerPhone
	 *            发货客户电话 deliveryCustomerContact 发货客户联系人
	 *            deliveryCustomerNationCode 发货国家 deliveryCustomerProvCode 发货省份
	 *            deliveryCustomerCityCode 发货市 deliveryCustomerDistCode 发货区
	 *            deliveryCustomerAddress 发货具体地址 receiveCustomerId 收货客户ID
	 *            receiveCustomerCode 收货客户编码 receiveCustomerName 收货客户名称
	 *            receiveCustomerMobilephone 收货客户手机 receiveCustomerPhone 收货客户电话
	 *            receiveCustomerContact 收货客户联系人 receiveCustomerNationCode 收货国家
	 *            receiveCustomerProvCode 收货省份 receiveCustomerCityCode 收货市
	 *            receiveCustomerDistCode 收货区 receiveCustomerAddress 收货具体地址
	 *            receiveOrgCode 收货部门(出发部门) productId 产品ID productCode 运输性质
	 *            receiveMethod 提货方式 customerPickupOrgCode 提货网点 loadMethod 配载类型
	 *            targetOrgCode 目的站 pickupToDoor 是否上门接货 driverCode 司机
	 *            pickupCentralized 是否集中接货 loadLineCode 配载线路 loadOrgCode 配载部门
	 *            lastLoadOrgCode 最终配载部门 preDepartureTime 预计出发时间
	 *            preCustomerPickupTime 预计派送/提货时间 carDirectDelivery 是否大车直送
	 *            goodsName 货物名称 goodsQtyTotal 货物总件数 goodsWeightTotal 货物总重量
	 *            goodsVolumeTotal 货物总体积 goodsSize 货物尺寸 goodsTypeCode 货物类型
	 *            preciousGoods 是否贵重物品 specialShapedGoods 是否异形物品 outerNotes 对外备注
	 *            innerNotes 对内备注
	 * @returnT
	 * @author ibm-liubinbin
	 * @date Jan 8, 2013 2:02:40 PM
	 */
	private WayBillNoLocusDTO getAirTransPickUpBillNoLocusFromAirBill(WaybillEntity waybillEntiy, AirTransPickupbillEntity airTransPickupbillEntity) {
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		wayBillNoLocusDTO.setBillNo(airTransPickupbillEntity.getAirWaybillNo());
		// 设置操作部门、操作部门名称，操作部门所属城市code，操作部门所属城市名称
		setOrgInfo(airTransPickupbillEntity.getCreateOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.OPERATE_ORG);
		// 设置操作类型为出发
		wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_AIR_TRANS_PICKUPBILL);
		// 设置操作时间为修改时间
		wayBillNoLocusDTO.setOperateTime(airTransPickupbillEntity.getCreateTime());
		// 操作人设置为制单人名称
		wayBillNoLocusDTO.setOperateName(airTransPickupbillEntity.getCreateUserName());
		// 设置车牌号(航空公司二字码)
		wayBillNoLocusDTO.setVehicleNo(airTransPickupbillEntity.getTransferFlightNo());
		// 设置下一部门编码（通过车辆信息里的车辆到达部门得到）
		setOrgInfo(airTransPickupbillEntity.getDestOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.NEXT_ORG);
		// 设置目的站部门（通过运单表中的最终卸车时间）
		setOrgInfo(waybillEntiy.getLastLoadOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.DEST_ORG);
		// 设置预计到达时间(就是到达时间)
		// 设置件数（开单件数）
		wayBillNoLocusDTO.setOperateNumber(airTransPickupbillEntity.getGoodsQtyTotal());
		return wayBillNoLocusDTO;
	}

	/**
	 * 正单交接单
	 * 
	 * @param waybillEntiy
	 * @param airTransPickupbillEntity
	 * @return
	 */
	private WayBillNoLocusDTO getAirHandOverDetailFromAirBill(WaybillEntity waybillEntiy, AirHandOverBillEntity airHandOverBillEntity) {
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		wayBillNoLocusDTO.setBillNo(airHandOverBillEntity.getAirHandoverNo());
		// 设置操作部门、操作部门名称，操作部门所属城市code，操作部门所属城市名称
		setOrgInfo(airHandOverBillEntity.getOrgName(), wayBillNoLocusDTO, WayBillNoLocusConstant.OPERATE_ORG);
		wayBillNoLocusDTO.setOperateOrgName(airHandOverBillEntity.getOrgName());
		// 设置操作类型为出发
		if (FossConstants.YES.equals(airHandOverBillEntity.getAirWaybillStockState())) {
			wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_AIR_HANDOVER_DETAIL_OUT);
		} else {
			wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_AIR_HANDOVER_DETAIL_IN);
		}
		// 设置操作时间为修改时间
		wayBillNoLocusDTO.setOperateTime(airHandOverBillEntity.getCreateTime());
		// 操作人设置为制单人名称
		wayBillNoLocusDTO.setOperateName(airHandOverBillEntity.getHandoverEmp());
		// 设置车牌号(航空公司二字码)
		wayBillNoLocusDTO.setVehicleNo(airHandOverBillEntity.getFlightNo());
		// 设置目的站部门（通过运单表中的最终卸车时间）
		setOrgInfo(waybillEntiy.getLastLoadOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.DEST_ORG);
		// 设置预计到达时间(就是到达时间)
		// 设置件数（开单件数）
		wayBillNoLocusDTO.setOperateNumber(airHandOverBillEntity.getGoodsQtyTotal());
		return wayBillNoLocusDTO;
	}

	/**
	 * 
	 * 设置运单到达的轨迹
	 * 
	 * @param waybillEntity waybillNo 运单号 orderNo 订单号 orderChannel 订单来源
	 *            orderPaidMethod 订单付款方式 deliveryCustomerId 发货客户ID
	 *            deliveryCustomerCode 发货客户编码 deliveryCustomerName 发货客户名称
	 *            deliveryCustomerMobilephone 发货客户手机 deliveryCustomerPhone
	 *            发货客户电话 deliveryCustomerContact 发货客户联系人
	 *            deliveryCustomerNationCode 发货国家 deliveryCustomerProvCode 发货省份
	 *            deliveryCustomerCityCode 发货市 deliveryCustomerDistCode 发货区
	 *            deliveryCustomerAddress 发货具体地址 receiveCustomerId 收货客户ID
	 *            receiveCustomerCode 收货客户编码 receiveCustomerName 收货客户名称
	 *            receiveCustomerMobilephone 收货客户手机 receiveCustomerPhone 收货客户电话
	 *            receiveCustomerContact 收货客户联系人 receiveCustomerNationCode 收货国家
	 *            receiveCustomerProvCode 收货省份 receiveCustomerCityCode 收货市
	 *            receiveCustomerDistCode 收货区 receiveCustomerAddress 收货具体地址
	 *            receiveOrgCode 收货部门(出发部门) productId 产品ID productCode 运输性质
	 *            receiveMethod 提货方式 customerPickupOrgCode 提货网点 loadMethod 配载类型
	 *            targetOrgCode 目的站 pickupToDoor 是否上门接货 driverCode 司机
	 *            pickupCentralized 是否集中接货 loadLineCode 配载线路 loadOrgCode 配载部门
	 *            lastLoadOrgCode 最终配载部门 preDepartureTime 预计出发时间
	 *            preCustomerPickupTime 预计派送/提货时间 carDirectDelivery 是否大车直送
	 *            goodsName 货物名称 goodsQtyTotal 货物总件数 goodsWeightTotal 货物总重量
	 *            goodsVolumeTotal 货物总体积 goodsSize 货物尺寸 goodsTypeCode 货物类型
	 *            preciousGoods 是否贵重物品 specialShapedGoods 是否异形物品 outerNotes 对外备注
	 *            innerNotes 对内备注
	 * @returnT
	 * @author ibm-liubinbin
	 * @date Jan 8, 2013 2:02:40 PM
	 */
	private WayBillNoLocusDTO getWayBillNoLocusFromArrive(WaybillEntity waybillEntiy, HandoverBillDTO handoverBillDTO) {
		WayBillNoLocusDTO wayBillNoLocusDTO = new WayBillNoLocusDTO();
		wayBillNoLocusDTO.setBillNo(handoverBillDTO.getHandoverNo());
		// 设置操作部门、操作部门名称，操作部门所属城市code，操作部门所属城市名称
		setOrgInfo(handoverBillDTO.getDestOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.OPERATE_ORG);
		// 设置操作类型为出发
		wayBillNoLocusDTO.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_ARRIVE);
		// 设置操作时间为出发时间
		wayBillNoLocusDTO.setOperateTime(handoverBillDTO.getActualArriveTime());
		// 操作人设置为司机姓名
		if (handoverBillDTO.getGpsArriveTime() != null) {
			wayBillNoLocusDTO.setOperateName("GPS");
		} else if (handoverBillDTO.getManualArriveUserCode() != null) {
			wayBillNoLocusDTO.setOperateName(employeeService.queryEmployeeByEmpCode(handoverBillDTO.getManualArriveUserCode()) == null ? "" : employeeService.queryEmployeeByEmpCode(handoverBillDTO.getManualArriveUserCode()).getEmpName());
		} else if (handoverBillDTO.getPdaArriveUserCode() != null) {
			wayBillNoLocusDTO.setOperateName(employeeService.queryEmployeeByEmpCode(handoverBillDTO.getPdaArriveUserCode()) == null ? "" : employeeService.queryEmployeeByEmpCode(handoverBillDTO.getPdaArriveUserCode()).getEmpName());
			setOrgInfo(handoverBillDTO.getPdaArriveOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.OPERATE_ORG);
		} else {
			wayBillNoLocusDTO.setOperateName(handoverBillDTO.getDriverName());
		}
		// 设置车牌号
		wayBillNoLocusDTO.setVehicleNo(handoverBillDTO.getVehicleNo());
		// 设置下一部门编码（通过车辆信息里的车辆到达部门得到）
		setOrgInfo(handoverBillDTO.getDestOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.NEXT_ORG);
		// 设置目的站部门（通过运单表中的最终卸车时间）
		setOrgInfo(waybillEntiy.getLastLoadOrgCode(), wayBillNoLocusDTO, WayBillNoLocusConstant.DEST_ORG);
		// 设置预计到达时间
		wayBillNoLocusDTO.setPlanArriveTime(handoverBillDTO.getPlanArriveTime());
		// 设置件数（开单件数）
		wayBillNoLocusDTO.setOperateNumber(handoverBillDTO.getGoodsQty());
		return wayBillNoLocusDTO;
	}

	/**
	 * 
	 * 获取运单提货通知信息列表
	 * 
	 * @param waybillEntity waybillNo 运单号 orderNo 订单号 orderChannel 订单来源
	 *            orderPaidMethod 订单付款方式 deliveryCustomerId 发货客户ID
	 *            deliveryCustomerCode 发货客户编码 deliveryCustomerName 发货客户名称
	 *            deliveryCustomerMobilephone 发货客户手机 deliveryCustomerPhone
	 *            发货客户电话 deliveryCustomerContact 发货客户联系人
	 *            deliveryCustomerNationCode 发货国家 deliveryCustomerProvCode 发货省份
	 *            deliveryCustomerCityCode 发货市 deliveryCustomerDistCode 发货区
	 *            deliveryCustomerAddress 发货具体地址 receiveCustomerId 收货客户ID
	 *            receiveCustomerCode 收货客户编码 receiveCustomerName 收货客户名称
	 *            receiveCustomerMobilephone 收货客户手机 receiveCustomerPhone 收货客户电话
	 *            receiveCustomerContact 收货客户联系人 receiveCustomerNationCode 收货国家
	 *            receiveCustomerProvCode 收货省份 receiveCustomerCityCode 收货市
	 *            receiveCustomerDistCode 收货区 receiveCustomerAddress 收货具体地址
	 *            receiveOrgCode 收货部门(出发部门) productId 产品ID productCode 运输性质
	 *            receiveMethod 提货方式 customerPickupOrgCode 提货网点 loadMethod 配载类型
	 *            targetOrgCode 目的站 pickupToDoor 是否上门接货 driverCode 司机
	 *            pickupCentralized 是否集中接货 loadLineCode 配载线路 loadOrgCode 配载部门
	 *            lastLoadOrgCode 最终配载部门 preDepartureTime 预计出发时间
	 *            preCustomerPickupTime 预计派送/提货时间 carDirectDelivery 是否大车直送
	 *            goodsName 货物名称 goodsQtyTotal 货物总件数 goodsWeightTotal 货物总重量
	 *            goodsVolumeTotal 货物总体积 goodsSize 货物尺寸 goodsTypeCode 货物类型
	 *            preciousGoods 是否贵重物品 specialShapedGoods 是否异形物品 outerNotes 对外备注
	 *            innerNotes 对内备注
	 * @return
	 * @author ibm-wangfei
	 * @date Jan 8, 2013 2:02:40 PM
	 */
	private List<WayBillNoLocusDTO> getWayBillNoLocusFromNotify(WaybillEntity waybillEntity) {
		List<WayBillNoLocusDTO> dtoList = new ArrayList<WayBillNoLocusDTO>();
		// 查询客户通知历史记录列表.
		List<NotificationEntity> notificationEntitys = notifyCustomerService.queryNotificationByWaybillNoAsc(waybillEntity.getWaybillNo());
		WayBillNoLocusDTO dto = null;
		if (CollectionUtils.isNotEmpty(notificationEntitys)) {
			for (NotificationEntity notify : notificationEntitys) {
				dto = new WayBillNoLocusDTO();
				// 运单号
				dto.setWaybillNo(waybillEntity.getWaybillNo());
				// 设置操作部门相关信息
				setOrgInfo(notify.getOperateOrgCode(), dto, WayBillNoLocusConstant.OPERATE_ORG);

				// 操作类型
				if (StringUtil.equals(FossConstants.YES, notify.getIsPreNotify())) {
					dto.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_NOTIFY_PRE);
				} else if (waybillEntity.getReceiveMethod() != null && waybillEntity.getReceiveMethod().startsWith(WaybillConstants.DELIVER_FREE)) {
					dto.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_NOTIFY_DELIVER);
				} else {
					dto.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_NOTIFY_PICKUP);
				}
				// 操作内容--短信/语音通知成功/失败
				dto.setOperateContent(DictUtil.rendererSubmitToDisplay(notify.getNoticeType(), DictionaryConstants.PKP_NOTIFY_CUSTOMER_TYPE) + "_"
						+ DictUtil.rendererSubmitToDisplay(notify.getNoticeResult(), DictionaryConstants.PKP_NOTIFY_CUSTOMER_STATUS));
				// 操作时间
				dto.setOperateTime(notify.getOperateTime());
				// 操作人姓名
				dto.setOperateName(notify.getOperator());
				// 操作件数
				dto.setOperateNumber(notify.getArriveGoodsQty());
				// 单据编号
				dto.setBillNo(null);
				// 车牌号
				dto.setVehicleNo(null);
				// 备注--通知对象
				dto.setNotes(notify.getConsignee());
				// 流水号
				dto.setSerialNo(null);
				// 离开后预计到达下一操作部门时间
				dto.setPlanArriveTime(null);
				// 派送人员姓名
				dto.setDeliveryName(null);
				// 派送人员电话
				dto.setDeliveryPhone(null);
				// 签收人姓名
				dto.setSignManName(null);
				LOGGER.info(ReflectionToStringBuilder.toString(dto));
				dtoList.add(dto);
			}
		} else {
			LOGGER.info("没有符合条件的记录");
		}
		return dtoList;
	}

	/**
	 * 
	 * 获取运单派送信息列表
	 * 
	 * @param waybillEntity waybillNo 运单号 orderNo 订单号 orderChannel 订单来源
	 *            orderPaidMethod 订单付款方式 deliveryCustomerId 发货客户ID
	 *            deliveryCustomerCode 发货客户编码 deliveryCustomerName 发货客户名称
	 *            deliveryCustomerMobilephone 发货客户手机 deliveryCustomerPhone
	 *            发货客户电话 deliveryCustomerContact 发货客户联系人
	 *            deliveryCustomerNationCode 发货国家 deliveryCustomerProvCode 发货省份
	 *            deliveryCustomerCityCode 发货市 deliveryCustomerDistCode 发货区
	 *            deliveryCustomerAddress 发货具体地址 receiveCustomerId 收货客户ID
	 *            receiveCustomerCode 收货客户编码 receiveCustomerName 收货客户名称
	 *            receiveCustomerMobilephone 收货客户手机 receiveCustomerPhone 收货客户电话
	 *            receiveCustomerContact 收货客户联系人 receiveCustomerNationCode 收货国家
	 *            receiveCustomerProvCode 收货省份 receiveCustomerCityCode 收货市
	 *            receiveCustomerDistCode 收货区 receiveCustomerAddress 收货具体地址
	 *            receiveOrgCode 收货部门(出发部门) productId 产品ID productCode 运输性质
	 *            receiveMethod 提货方式 customerPickupOrgCode 提货网点 loadMethod 配载类型
	 *            targetOrgCode 目的站 pickupToDoor 是否上门接货 driverCode 司机
	 *            pickupCentralized 是否集中接货 loadLineCode 配载线路 loadOrgCode 配载部门
	 *            lastLoadOrgCode 最终配载部门 preDepartureTime 预计出发时间
	 *            preCustomerPickupTime 预计派送/提货时间 carDirectDelivery 是否大车直送
	 *            goodsName 货物名称 goodsQtyTotal 货物总件数 goodsWeightTotal 货物总重量
	 *            goodsVolumeTotal 货物总体积 goodsSize 货物尺寸 goodsTypeCode 货物类型
	 *            preciousGoods 是否贵重物品 specialShapedGoods 是否异形物品 outerNotes 对外备注
	 *            innerNotes 对内备注
	 * @return List<WayBillNoLocusDTO>
	 * @author ibm-wangfei
	 * @date Jan 8, 2013 2:02:40 PM
	 */
	private List<WayBillNoLocusDTO> getWayBillNoLocusFromDelivery(WaybillEntity waybillEntity, String invokingSource) {
		List<WayBillNoLocusDTO> dtoList = new ArrayList<WayBillNoLocusDTO>();
		List<WaybillDeliveryDto> waybillDeliveryDtos = null;
//		if (StringUtil.equals(invokingSource, WayBillNoLocusConstant.INVOKING_SOURCE_FOSS)) {
//		waybillDeliveryDtos = deliverbillService.queryWaybillDeliveryListByWaybillNoForBse(waybillEntity.getWaybillNo());
//		} else {
			waybillDeliveryDtos = deliverbillService.queryWaybillDeliveryListByWaybillNo(waybillEntity.getWaybillNo());
//		}
		WayBillNoLocusDTO dto = null;
		if (CollectionUtils.isNotEmpty(waybillDeliveryDtos)) {
			for (WaybillDeliveryDto waybillDeliveryDto : waybillDeliveryDtos) {
				dto = new WayBillNoLocusDTO();
				// 运单号
				dto.setWaybillNo(waybillEntity.getWaybillNo());
				// 设置操作部门相关信息
				setOrgInfo(waybillDeliveryDto.getOperateOrgCode(), dto, WayBillNoLocusConstant.OPERATE_ORG);

				// 操作类型
//				if (StringUtil.equals(invokingSource, WayBillNoLocusConstant.INVOKING_SOURCE_FOSS)) {
//					dto.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_DELIVER_BILL_PREFIX + waybillDeliveryDto.getStatus());
//				} else {
					dto.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_DELIVERY);
//				}
				// 操作内容
				dto.setOperateContent(null);
				// 操作时间
				dto.setOperateTime(waybillDeliveryDto.getOperateTime());
				// 操作人姓名
				dto.setOperateName(waybillDeliveryDto.getOperatorName());
				// 操作件数
				dto.setOperateNumber(waybillDeliveryDto.getOperateGoodsQty());
				// 单据编号
				dto.setBillNo(waybillDeliveryDto.getDeliverbillNo());
				// 车牌号
				dto.setVehicleNo(waybillDeliveryDto.getVehicleNo());
				// 备注
				dto.setNotes(StringUtil.defaultIfNull(waybillDeliveryDto.getDriverName()) + " " + StringUtil.defaultIfNull(waybillDeliveryDto.getDriverPhone()));
				// 流水号
				dto.setSerialNo(null);
				// 离开后预计到达下一操作部门时间
				dto.setPlanArriveTime(null);
				// 派送人员姓名
				dto.setDeliveryName(waybillDeliveryDto.getDriverName());
				// 派送人员电话
				dto.setDeliveryPhone(waybillDeliveryDto.getDriverPhone());
				// 签收人姓名
				dto.setSignManName(null);
				LOGGER.info(ReflectionToStringBuilder.toString(dto));
				dtoList.add(dto);
			}
		} else {
			LOGGER.info("没有符合条件的记录");
		}
		return dtoList;
	}

	/**
	 * 
	 * 获取运单派送拉回、正常签收、异常签收、部分签收等信息列表
	 * 
	 * @param waybillEntity waybillNo 运单号 orderNo 订单号 orderChannel 订单来源
	 *            orderPaidMethod 订单付款方式 deliveryCustomerId 发货客户ID
	 *            deliveryCustomerCode 发货客户编码 deliveryCustomerName 发货客户名称
	 *            deliveryCustomerMobilephone 发货客户手机 deliveryCustomerPhone
	 *            发货客户电话 deliveryCustomerContact 发货客户联系人
	 *            deliveryCustomerNationCode 发货国家 deliveryCustomerProvCode 发货省份
	 *            deliveryCustomerCityCode 发货市 deliveryCustomerDistCode 发货区
	 *            deliveryCustomerAddress 发货具体地址 receiveCustomerId 收货客户ID
	 *            receiveCustomerCode 收货客户编码 receiveCustomerName 收货客户名称
	 *            receiveCustomerMobilephone 收货客户手机 receiveCustomerPhone 收货客户电话
	 *            receiveCustomerContact 收货客户联系人 receiveCustomerNationCode 收货国家
	 *            receiveCustomerProvCode 收货省份 receiveCustomerCityCode 收货市
	 *            receiveCustomerDistCode 收货区 receiveCustomerAddress 收货具体地址
	 *            receiveOrgCode 收货部门(出发部门) productId 产品ID productCode 运输性质
	 *            receiveMethod 提货方式 customerPickupOrgCode 提货网点 loadMethod 配载类型
	 *            targetOrgCode 目的站 pickupToDoor 是否上门接货 driverCode 司机
	 *            pickupCentralized 是否集中接货 loadLineCode 配载线路 loadOrgCode 配载部门
	 *            lastLoadOrgCode 最终配载部门 preDepartureTime 预计出发时间
	 *            preCustomerPickupTime 预计派送/提货时间 carDirectDelivery 是否大车直送
	 *            goodsName 货物名称 goodsQtyTotal 货物总件数 goodsWeightTotal 货物总重量
	 *            goodsVolumeTotal 货物总体积 goodsSize 货物尺寸 goodsTypeCode 货物类型
	 *            preciousGoods 是否贵重物品 specialShapedGoods 是否异形物品 outerNotes 对外备注
	 *            innerNotes 对内备注
	 * @return
	 * @author ibm-wangfei
	 * @date 2013-1-8 15:36:24
	 */
	private List<WayBillNoLocusDTO> getWayBillNoLocusFromSignInfo(WaybillEntity waybillEntity,String serialNo) {
		List<WayBillNoLocusDTO> dtoList = new ArrayList<WayBillNoLocusDTO>();
		WayBillNoLocusDTO dto = null;
		boolean isAbanoned = false;
		// 是偏线或空运或弃货
		WaybillSignResultEntity signEntity = new WaybillSignResultEntity();
		signEntity.setWaybillNo(waybillEntity.getWaybillNo());
		signEntity.setActive(FossConstants.ACTIVE);
		// 根据运单号查询运单签结果里的运单信息
		WaybillSignResultEntity newEntity = waybillSignResultService.queryWaybillSignResultByWaybillNo(signEntity);
		if (newEntity != null && 
				StringUtil.equals(newEntity.getSignSituation(),ArriveSheetConstants.SITUATION_UNNORMAL_ABANDONGOODS)) {
			// 签收结果为弃货
			isAbanoned = true;
		}
		
		//判断是否为经济快递中的快递代理签收
		boolean isExpressExternal = false;
		if(WaybillConstants.directDetermineIsExpressByProductCode(waybillEntity.getProductCode())){
			OuterBranchExpressEntity entity = ldpAgencyDeptService.queryLdpAgencyDeptByCode(waybillEntity.getCustomerPickupOrgCode(),FossConstants.ACTIVE);
			if(null != entity){
				isExpressExternal = true;
			}
		}
		// 判断运单是否属于偏线空运的运单或者已转弃货通过的运单，直接查询签收结果表
		if (isAbanoned == true || StringUtil.equals(waybillEntity.getProductCode(), ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT) || StringUtil.equals(waybillEntity.getProductCode(), ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE) ||isExpressExternal) {
			// update by foss-sunyanfei 2015-11-04
			if(serialNo != null){
				//通过运单号查询落地配外发表单中该运单号的记录
				List<LdpExternalBillDto> ldpExternalBillList = ldpExternalBillService.queryExternalBillListByWaybillNo(waybillEntity.getWaybillNo());
				//判断该运单是否是外发运单，并且是否是一票多件
				if(ldpExternalBillList != null && ldpExternalBillList.size() > 1){//是外发运单，并且是一票多件
					//运单号+流水号查询外发流水签收表
					SerialSignResultEntity serialSignResultEntity = new SerialSignResultEntity();
					serialSignResultEntity.setWaybillNo(waybillEntity.getWaybillNo());
					serialSignResultEntity.setSerialNo(serialNo);
					SerialSignResultEntity serial = serialSignResultService.queryByConditions(serialSignResultEntity);
					if(serial !=null){
						dtoList = this.wayBillNoLocusDtoList(waybillEntity, serialNo,serial);
					}else{//外发流水未签收
						return dtoList;
					}
				}else{//是外发运单，但不是一票多件
					dtoList = this.wayBillNoLocusDtoList(waybillEntity, serialNo,null);
				}
			}else{
				dtoList = this.wayBillNoLocusDtoList(waybillEntity, serialNo,null);
			}
			// update by foss-sunyanfei 2015-11-04
		} else {
			// 根据运单号查询签收的到达联集合
			List<ArriveSheetEntity> arriveSheetEntitys = arriveSheetManngerService.queryArriveSheetListByWaybillNoWithSign(waybillEntity.getWaybillNo());
			if (CollectionUtils.isNotEmpty(arriveSheetEntitys)) {
				// 按照签收时间进行排序
				Collections.sort(arriveSheetEntitys, new Comparator<ArriveSheetEntity>() {
					public int compare(ArriveSheetEntity a1, ArriveSheetEntity a2) {
						if (a1.getSignTime() == null) {
							return -1;
						}
						if (a2.getSignTime() == null) {
							return 1;
						}
						return a1.getSignTime().compareTo(a2.getSignTime());
					}
				});
				
				for (int i = 0; i < arriveSheetEntitys.size(); i++) {
					ArriveSheetEntity arriveSheetEntity = arriveSheetEntitys.get(i);
					if(StringUtils.isNotBlank(serialNo)){
						//流水号集合  ---查询专线签收的流水号集合
						SignDetailEntity signDetail = new SignDetailEntity();
						signDetail.setArrivesheetNo(arriveSheetEntity.getArrivesheetNo());
						signDetail.setSerialNo(serialNo);
						if(CollectionUtils.isEmpty(signDetailService.querySignDetailByParams(signDetail))){
							continue;
						}
					}
					dto = new WayBillNoLocusDTO();
					// 运单号
					dto.setWaybillNo(waybillEntity.getWaybillNo());
					// 设置操作部门相关信息
					setOrgInfo(arriveSheetEntity.getOperateOrgCode(), dto, WayBillNoLocusConstant.OPERATE_ORG);
					// 判断签收类型
					if (StringUtil.equals(ArriveSheetConstants.SITUATION_NORMAL_SIGN, arriveSheetEntity.getSituation())) {
						/** 正常签收 */
						// 操作类型
						dto.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_NORMAL_SIGN);
						// 操作内容--签收状态下的签收人 \ 派送拉回的原因
						dto.setOperateContent(StringUtil.replace(arriveSheetEntity.getDeliverymanName(), NotifyCustomerConstants.DEFAULT_GOODS_AREA_CODE, ""));
					} else if (StringUtil.equals(ArriveSheetConstants.SITUATION_GOODS_BACK, arriveSheetEntity.getSituation())) {
						/** 货物拉回 */
						// 操作类型
						dto.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_DELIVERY_RETURN);
						// 操作内容--签收状态下的签收人 \ 派送拉回的原因
						dto.setOperateContent(DictUtil.rendererSubmitToDisplay(arriveSheetEntity.getSignNote(), DictionaryConstants.PKP_PULLBACK_REASON));//拉回原因备注
					} else if (StringUtil.equals(ArriveSheetConstants.SITUATION_PARTIAL_SIGN, arriveSheetEntity.getSituation())) {
						/** 部分签收 */
						// 操作类型
						dto.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_PART_SIGN);
						// 操作内容--签收状态下的签收人 \ 派送拉回的原因
						dto.setOperateContent(null);
					} 
					else {
						/** 异常签收 */
						// 操作类型
						dto.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_EXCEPTION_SIGN);
						// 操作内容--签收状态下的签收人 \ 派送拉回的原因
						dto.setOperateContent(null);
					}

					// 操作时间
					dto.setOperateTime(arriveSheetEntity.getOperateTime());
					if (i == 0) {
						// 设置第一次签收时间
						dto.setFirstSignTime(arriveSheetEntity.getOperateTime());
					}
					// 操作人姓名
					dto.setOperateName(arriveSheetEntity.getOperator());
					if(StringUtil.equals(ArriveSheetConstants.SITUATION_GOODS_BACK, arriveSheetEntity.getSituation())){
						// 操作件数--取到达联件数
						dto.setOperateNumber(arriveSheetEntity.getArriveSheetGoodsQty());
					}else{
						// 操作件数---取签收件数
						dto.setOperateNumber(arriveSheetEntity.getSignGoodsQty());
					}
					// 单据编号
					dto.setBillNo(null);
					// 车牌号
					dto.setVehicleNo(null);
					if(WaybillConstants.directDetermineIsExpressByProductCode(waybillEntity.getProductCode())
							&& StringUtil.equals(ArriveSheetConstants.SITUATION_UNNORMAL_ELSE, arriveSheetEntity.getSituation())&&
							StringUtil.isNotBlank(arriveSheetEntity.getSignNote()) &&
							arriveSheetEntity.getSignNote().startsWith(WAYBILL_NO_IS_RETURN)){
						// 备注
						dto.setNotes(arriveSheetEntity.getSignNote());
					}else if(StringUtil.equals(ArriveSheetConstants.SITUATION_GOODS_BACK,// 判断到达联是否为派送拉回
                            arriveSheetEntity.getSituation())){
						// add by 353654 DN201608260005
                        dto.setNotes(DictUtil.rendererSubmitToDisplay(arriveSheetEntity.getSignNote(),
                                DictionaryConstants.PKP_PULLBACK_REASON));
					}else{
						// 备注
						dto.setNotes(null);
					}
					// 流水号
					dto.setSerialNo(null);
					// 离开后预计到达下一操作部门时间
					dto.setPlanArriveTime(null);
					// 派送人员姓名
					dto.setDeliveryName(null);
					// 派送人员电话
					dto.setDeliveryPhone(null);
					if(WaybillConstants.directDetermineIsExpressByProductCode(waybillEntity.getProductCode())){
						//快递  DN201511090005  添加“签收人类型”字段 243921
						if(StringUtils.isNotEmpty(arriveSheetEntity.getDeliverymanType())){
							dto.setSignManName(DictUtil.rendererSubmitToDisplay(arriveSheetEntity.getDeliverymanType(), DictionaryConstants.PKP_SIGN_PERSON_TYPE));
						}else{
							dto.setSignManName("代理");
						}
					}else{ //零担 签收人姓名  
						dto.setSignManName(StringUtil.replace(arriveSheetEntity.getDeliverymanName(), NotifyCustomerConstants.DEFAULT_GOODS_AREA_CODE, ""));
					}
							
					LOGGER.info(ReflectionToStringBuilder.toString(dto));
					dtoList.add(dto);
				}
			} else {
				LOGGER.info("没有符合条件的记录");
			}
		}

		return dtoList;
	}

	/**
	 * 
	 * 获取运单反签收等信息列表
	 * 
	 * @param waybillEntity waybillNo 运单号 orderNo 订单号 orderChannel 订单来源
	 *            orderPaidMethod 订单付款方式 deliveryCustomerId 发货客户ID
	 *            deliveryCustomerCode 发货客户编码 deliveryCustomerName 发货客户名称
	 *            deliveryCustomerMobilephone 发货客户手机 deliveryCustomerPhone
	 *            发货客户电话 deliveryCustomerContact 发货客户联系人
	 *            deliveryCustomerNationCode 发货国家 deliveryCustomerProvCode 发货省份
	 *            deliveryCustomerCityCode 发货市 deliveryCustomerDistCode 发货区
	 *            deliveryCustomerAddress 发货具体地址 receiveCustomerId 收货客户ID
	 *            receiveCustomerCode 收货客户编码 receiveCustomerName 收货客户名称
	 *            receiveCustomerMobilephone 收货客户手机 receiveCustomerPhone 收货客户电话
	 *            receiveCustomerContact 收货客户联系人 receiveCustomerNationCode 收货国家
	 *            receiveCustomerProvCode 收货省份 receiveCustomerCityCode 收货市
	 *            receiveCustomerDistCode 收货区 receiveCustomerAddress 收货具体地址
	 *            receiveOrgCode 收货部门(出发部门) productId 产品ID productCode 运输性质
	 *            receiveMethod 提货方式 customerPickupOrgCode 提货网点 loadMethod 配载类型
	 *            targetOrgCode 目的站 pickupToDoor 是否上门接货 driverCode 司机
	 *            pickupCentralized 是否集中接货 loadLineCode 配载线路 loadOrgCode 配载部门
	 *            lastLoadOrgCode 最终配载部门 preDepartureTime 预计出发时间
	 *            preCustomerPickupTime 预计派送/提货时间 carDirectDelivery 是否大车直送
	 *            goodsName 货物名称 goodsQtyTotal 货物总件数 goodsWeightTotal 货物总重量
	 *            goodsVolumeTotal 货物总体积 goodsSize 货物尺寸 goodsTypeCode 货物类型
	 *            preciousGoods 是否贵重物品 specialShapedGoods 是否异形物品 outerNotes 对外备注
	 *            innerNotes 对内备注
	 * @return
	 * @author ibm-wangfei
	 * @date 2013-1-8 15:36:24
	 */
	private List<WayBillNoLocusDTO> getWayBillNoLocusFromSignRfc(WaybillEntity waybillEntity) {
		// 反签收货物轨迹查询
		List<WayBillNoLocusDTO> dtoList = signChangeService.queryReverseArriveSheetByWaybillNo(waybillEntity.getWaybillNo());
		// 根据运单号查询签收的到达联集合
		if (CollectionUtils.isNotEmpty(dtoList)) {
			for (WayBillNoLocusDTO dto : dtoList) {
				// 设置操作部门相关信息
				setOrgInfo(dto.getOperateOrgCode(), dto, WayBillNoLocusConstant.OPERATE_ORG);
				// 操作类型
				dto.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_SIGN_RFC);
				LOGGER.info(ReflectionToStringBuilder.toString(dto));
			}
		}
		return dtoList;
	}

	/**
	 * 
	 * 获取运单终止列表
	 * 
	 * @param waybillEntity waybillNo 运单号 orderNo 订单号 orderChannel 订单来源
	 *            orderPaidMethod 订单付款方式 deliveryCustomerId 发货客户ID
	 *            deliveryCustomerCode 发货客户编码 deliveryCustomerName 发货客户名称
	 *            deliveryCustomerMobilephone 发货客户手机 deliveryCustomerPhone
	 *            发货客户电话 deliveryCustomerContact 发货客户联系人
	 *            deliveryCustomerNationCode 发货国家 deliveryCustomerProvCode 发货省份
	 *            deliveryCustomerCityCode 发货市 deliveryCustomerDistCode 发货区
	 *            deliveryCustomerAddress 发货具体地址 receiveCustomerId 收货客户ID
	 *            receiveCustomerCode 收货客户编码 receiveCustomerName 收货客户名称
	 *            receiveCustomerMobilephone 收货客户手机 receiveCustomerPhone 收货客户电话
	 *            receiveCustomerContact 收货客户联系人 receiveCustomerNationCode 收货国家
	 *            receiveCustomerProvCode 收货省份 receiveCustomerCityCode 收货市
	 *            receiveCustomerDistCode 收货区 receiveCustomerAddress 收货具体地址
	 *            receiveOrgCode 收货部门(出发部门) productId 产品ID productCode 运输性质
	 *            receiveMethod 提货方式 customerPickupOrgCode 提货网点 loadMethod 配载类型
	 *            targetOrgCode 目的站 pickupToDoor 是否上门接货 driverCode 司机
	 *            pickupCentralized 是否集中接货 loadLineCode 配载线路 loadOrgCode 配载部门
	 *            lastLoadOrgCode 最终配载部门 preDepartureTime 预计出发时间
	 *            preCustomerPickupTime 预计派送/提货时间 carDirectDelivery 是否大车直送
	 *            goodsName 货物名称 goodsQtyTotal 货物总件数 goodsWeightTotal 货物总重量
	 *            goodsVolumeTotal 货物总体积 goodsSize 货物尺寸 goodsTypeCode 货物类型
	 *            preciousGoods 是否贵重物品 specialShapedGoods 是否异形物品 outerNotes 对外备注
	 *            innerNotes 对内备注
	 * @return List<WayBillNoLocusDTO>
	 * @author ibm-wangfei
	 * @date 2013-1-8 15:36:24
	 */
	private List<WayBillNoLocusDTO> getWayBillNoLocusFromdeliveryAborted(WaybillEntity waybillEntity) {
		List<WayBillNoLocusDTO> dtoList = new ArrayList<WayBillNoLocusDTO>();
		ActualFreightEntity actualFreightEntity = actualFreightService.queryByWaybillNo(waybillEntity.getWaybillNo());
		if (actualFreightEntity != null) {
			// 判断运单的生效状态
			if (StringUtil.equals(WaybillConstants.ABORTED, actualFreightEntity.getStatus())) {
				// 如果等于终止
				WayBillNoLocusDTO dto = new WayBillNoLocusDTO();
				// 运单号
				dto.setWaybillNo(waybillEntity.getWaybillNo());
				// 设置操作部门相关信息
				setOrgInfo(waybillEntity.getCreateOrgCode(), dto, WayBillNoLocusConstant.OPERATE_ORG);
				// 操作类型
				dto.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_ABORTED);
				// 操作内容
				dto.setOperateContent(null);
				// 操作时间
				dto.setOperateTime(waybillEntity.getCreateTime());
				// 操作人姓名
				dto.setOperateName(waybillEntity.getCreateUser());
				// 操作件数
				dto.setOperateNumber(waybillEntity.getGoodsQtyTotal());
				// 单据编号
				dto.setBillNo(null);
				// 车牌号
				dto.setVehicleNo(null);
				// 备注
				dto.setNotes(null);
				// 流水号
				dto.setSerialNo(null);
				// 设置目的站信息
				setOrgInfo(waybillEntity.getLastLoadOrgCode(), dto, WayBillNoLocusConstant.DEST_ORG);
				// 离开后预计到达下一操作部门时间
				dto.setPlanArriveTime(null);
				// 派送人员姓名
				dto.setDeliveryName(null);
				// 派送人员电话
				dto.setDeliveryPhone(null);
				// 签收人姓名
				dto.setSignManName(null);
				LOGGER.info(ReflectionToStringBuilder.toString(dto));
				dtoList.add(dto);
			} else {
				LOGGER.info("没有符合条件的记录");
			}
		} else {
			LOGGER.info("没有符合条件的记录");
		}
		return dtoList;
	}

	/**
	 * 
	 * 设置组织相关信息
	 * 
	 * @param orgCode
	 * @param dto
	 * @param operOrgType
	 * @author ibm-wangfei
	 * @date Jan 8, 2013 2:38:19 PM
	 */
	private void setOrgInfo(String orgCode, WayBillNoLocusDTO dto, int operOrgType) {
		if (StringUtil.isBlank(orgCode)) {
			return;
		}
		// 根据部门编码，获取组织信息
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(orgCode);

		switch (operOrgType) {
		case WayBillNoLocusConstant.OPERATE_ORG:
			// 操作部门编码
			dto.setOperateOrgCode(orgCode);
			// 操作部门标杆编码
			OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCode(orgCode);

			if (orgEntity != null) {
				LOGGER.info("标杆编码：{}", orgEntity.getUnifiedCode());
				dto.setUnifiedCode(orgEntity.getUnifiedCode());
			}

			if (orgAdministrativeInfoEntity != null) {
				// 操作部门名称
				dto.setOperateOrgName(orgAdministrativeInfoEntity.getName());
				// 操作部门所在城市编码
				dto.setOperateCityCode(orgAdministrativeInfoEntity.getCityCode());
				// 操作部门所在城市名称
				dto.setOperateCityName(orgAdministrativeInfoEntity.getCityName());
			}
			return;
		case WayBillNoLocusConstant.NEXT_ORG:
			if (orgAdministrativeInfoEntity != null) {
				// 下一站部门编码
				dto.setNextOrgCode(orgCode);
				// 下一站部门名称
				dto.setNextOrgName(orgAdministrativeInfoEntity.getName());
				// 下一站所在城市编码
				dto.setNextCityCode(orgAdministrativeInfoEntity.getCityCode());
				// 下一站所在城市名称
				dto.setNextCityName(orgAdministrativeInfoEntity.getCityName());
			}
			return;
		case WayBillNoLocusConstant.DEST_ORG:
			if (orgAdministrativeInfoEntity != null) {
				// 目的站部门编码
				dto.setDestinationStationOrgCode(orgCode);
				// 目的站部门名称
				dto.setDestinationStationOrgName(orgAdministrativeInfoEntity.getName());
				// 目的站部门所在城市编码
				dto.setDestinationStationCityCode(orgAdministrativeInfoEntity.getCityCode());
				// 目的站部门所在城市名称
				dto.setDestinationStationCityName(orgAdministrativeInfoEntity.getCityName());
			}
			return;
		default:
			return;
		}
	}
	
	/**
	 * 
	 * <p>运单轨迹接口（供CC调用）</p> 
	 * @author alfred
	 * @date 2014-7-31 下午3:40:43
	 * @param waybillNo
	 * @return 
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IWayBillNoLocusService#getWayBillNoLocusForCC(java.lang.String)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public WayBillNoLocusDTO getWayBillNoLocusForCC(String waybillNo) {
		WayBillNoLocusDTO dto = new WayBillNoLocusDTO();
		String status = "";
		WaybillEntity waybillEntity = queryWaybillEntityByNo(waybillNo);
		if (waybillEntity == null) {
			return null;
		}
		status = waybillManagerService.getWaybillStatus(waybillNo);
		//作废或终止
		if(status.equals(WayBillNoLocusConstant.TRAJICTORY_CC_OBSOLETE)||
				status.equals(WayBillNoLocusConstant.TRAJICTORY_CC_ABORTED)){
			dto.setWaybillNo(waybillNo);
			dto.setDestinationStationOrgName(waybillEntity.getTargetOrgCode());
			dto.setCurrentStatus(WayBillNoLocusConstant.TRAJICTORY_CC_OBSOLETE);
			return dto;
		}
		
		/**
		 * 判定签收状态
		 */
		Map statusMap =  trackingService.queryWaybillArrStatus(waybillNo);
		status =  (String) statusMap.get("waybillStatus");
		if(StringUtils.isNotEmpty(status)){
			//到达到达部门
			if(status.equals(WayBillNoLocusConstant.TRAJICTORY_CC_STATION_IN)){
				dto.setWaybillNo(waybillNo);
				dto.setDestinationStationOrgName(waybillEntity.getTargetOrgCode());
				dto.setCurrentStatus(WayBillNoLocusConstant.TRAJICTORY_CC_STATION_IN);
				dto.setOperateTime((Date) statusMap.get("arriveTime"));
				return dto;
				//派送中
			}else if(status.equals(WayBillNoLocusConstant.TRAJICTORY_CC_DELIVERING)){
				dto.setWaybillNo(waybillNo);
				dto.setDestinationStationOrgName(waybillEntity.getTargetOrgCode());
				dto.setCurrentStatus(WayBillNoLocusConstant.TRAJICTORY_CC_DELIVERING);
				dto.setDeliveryName((String) statusMap.get("driverName"));
				dto.setDeliveryPhone((String) statusMap.get("driverTel"));
				return dto;
				//正常签收
			}else if(status.equals(WayBillNoLocusConstant.TRAJICTORY_CC_NORMAL_SIGN)){
				dto.setWaybillNo(waybillNo);
				dto.setDestinationStationOrgName(waybillEntity.getTargetOrgCode());
				dto.setCurrentStatus(WayBillNoLocusConstant.TRAJICTORY_CC_NORMAL_SIGN);
				dto.setSignManName( (String) statusMap.get("delivermanName"));
				return dto;
				//异常签收
			}else if(status.equals(WayBillNoLocusConstant.TRAJICTORY_CC_UNNORMAL_SIGN)){
				dto.setWaybillNo(waybillNo);
				dto.setDestinationStationOrgName(waybillEntity.getTargetOrgCode());
				dto.setCurrentStatus(WayBillNoLocusConstant.TRAJICTORY_CC_UNNORMAL_SIGN);
				dto.setSignManName( (String) statusMap.get("delivermanName"));
				return dto;
			}
		}
		/**
		 * 判定是否外发
		 */
		Map outerMap =  trackingService.queryWaybillIsOuter(waybillNo);
		status =  (String) outerMap.get("isOuter");
		if(StringUtils.isNotEmpty(status)){
			//外发
			if(status.equals(WayBillNoLocusConstant.TRAJICTORY_CC_IS_OUTER)){
				dto.setWaybillNo(waybillNo);
				dto.setDestinationStationOrgName(waybillEntity.getTargetOrgCode());
				dto.setCurrentStatus(WayBillNoLocusConstant.TRAJICTORY_CC_IS_OUTER);
				return dto;
			}
		}
		
		/**
		 * 判定中转运输状态
		 */
		Map tfrMap =  trackingService.queryPathdetail(waybillNo, waybillEntity.getReceiveOrgCode(),
				waybillEntity.getCustomerPickupOrgCode());
		status =  (String) tfrMap.get("action");
		if(StringUtil.isEmpty(status)){
			//已开单
			if(waybillEntity.getPickupCentralized().equals("Y")){
				dto.setWaybillNo(waybillNo);
				dto.setDestinationStationOrgName(waybillEntity.getTargetOrgCode());
				dto.setCurrentStatus(WayBillNoLocusConstant.TRAJICTORY_CC_EFFECTIVE);
				return dto;
			}
			//已开单
		}else if(status.equals(WayBillNoLocusConstant.TRAJICTORY_CC_EFFECTIVE)){
			dto.setWaybillNo(waybillNo);
			dto.setDestinationStationOrgName(waybillEntity.getTargetOrgCode());
			dto.setCurrentStatus(WayBillNoLocusConstant.TRAJICTORY_CC_EFFECTIVE);
			return dto;
			//不在库存
		}else if (status.equals("STATION_OUT")||status.equals("TFR_IN")||status.equals("TFR_OUT")||
				status.equals("STATION_IN")){
			Map cityname = trackingService.queryCitynameForCC((String) tfrMap.get("nextOrgCode"));
			Map orgtype =  trackingService.queryOrgtypeForCC((String) tfrMap.get("nextOrgCode"));
			String orgName = (String)cityname.get("cityName") + (String)orgtype.get("typeName");
			
			dto.setWaybillNo(waybillNo);
			dto.setDestinationStationOrgName(waybillEntity.getTargetOrgCode());
			dto.setCurrentStatus(WayBillNoLocusConstant.TRAJICTORY_CC_TFR_OUTSTOCK);
			dto.setNextCityName((String)cityname.get("cityName"));
			dto.setNextOrgName(orgName);
			return dto;
			//在库存
		}else if(status.equals("TFR_STOCK")||status.equals("STATION_STOCK")){
			//到达时间
			Date arriveTime =  (Date) tfrMap.get("arriveTime");
			Map cityname = trackingService.queryCitynameForCC((String) tfrMap.get("nowOrgCode"));
			Map orgtype =  trackingService.queryOrgtypeForCC((String) tfrMap.get("nowOrgCode"));
			String orgName = (String)cityname.get("cityName") + (String)orgtype.get("typeName");
			
			dto.setWaybillNo(waybillNo);
			dto.setDestinationStationOrgName(waybillEntity.getTargetOrgCode());
			dto.setCurrentStatus(WayBillNoLocusConstant.TRAJICTORY_CC_TFR_INSTOCK);
			dto.setOperateCityName((String)cityname.get("cityName"));
			dto.setOperateOrgName(orgName);
			dto.setOperateTime(arriveTime);
			return dto;
		}
		return dto;
	}

	/**
	 * 判断运单是否属于偏线空运的运单或者已转弃货通过的运单，直接查询签收结果表
	 * @author foss-sunyanfei
	 * @date 2015-11-04 下午16:32:18
	 * @param waybillEntity
	 * @param serialNo
	 * @return
	 */
	private List<WayBillNoLocusDTO> wayBillNoLocusDtoList(WaybillEntity waybillEntity,String serialNo,SerialSignResultEntity serialEntity){
		List<WayBillNoLocusDTO> dtoList= new ArrayList<WayBillNoLocusDTO>();
		WayBillNoLocusDTO dto = new WayBillNoLocusDTO();
		
		// 是偏线或空运
		WaybillSignResultEntity entity = new WaybillSignResultEntity();
		entity.setWaybillNo(waybillEntity.getWaybillNo());
		entity.setActive(FossConstants.ACTIVE);
		// 根据运单号查询运单签结果里的运单信息
		WaybillSignResultEntity newEntity = waybillSignResultService.queryWaybillSignResultByWaybillNo(entity);
		if (newEntity == null) {
			LOGGER.info("没有符合条件的记录");
			return dtoList;
		}
		dto = new WayBillNoLocusDTO();
		// 运单号
		dto.setWaybillNo(waybillEntity.getWaybillNo());
		if(newEntity.getCreateOrgCode()== null || newEntity.getCreateOrgCode().equals("")){
			// 设置操作部门相关信息
			setOrgInfo(waybillEntity.getLastLoadOrgCode(), dto, WayBillNoLocusConstant.OPERATE_ORG);
		}else {
			// 设置操作部门相关信息
			setOrgInfo(newEntity.getCreateOrgCode(), dto, WayBillNoLocusConstant.OPERATE_ORG);
		}
		if(serialEntity != null){
			// 一票多件流水签收 判断签收类型
			newEntity.setSignSituation(serialEntity.getSignSituation());
		}
		// 判断签收类型
		if (StringUtil.equals(ArriveSheetConstants.SITUATION_NORMAL_SIGN, newEntity.getSignSituation())) {
			/** 正常签收 */
			// 操作类型
			dto.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_NORMAL_SIGN);
			// 操作内容--签收状态下的签收人 \ 派送拉回的原因
			dto.setOperateContent(StringUtil.replace(newEntity.getDeliverymanName(), NotifyCustomerConstants.DEFAULT_GOODS_AREA_CODE, ""));
		} else if (StringUtil.equals(ArriveSheetConstants.SITUATION_PARTIAL_SIGN, newEntity.getSignSituation())) {
			/** 部分签收 */
			// 操作类型
			dto.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_PART_SIGN);
			// 操作内容--签收状态下的签收人 \ 派送拉回的原因
			dto.setOperateContent(null);
		}
		else {
			/** 异常签收 */
			// 操作类型
			dto.setOperateType(WayBillNoLocusConstant.OPERATE_TYPE_EXCEPTION_SIGN);
			// 操作内容--签收状态下的签收人 \ 派送拉回的原因
			dto.setOperateContent(null);
		}

		dto.setFirstSignTime(newEntity.getCreateTime());
		// 操作人姓名
		dto.setOperateName(null);
		//判断签收件数是否为空
		if(serialEntity != null){
			// 操作件数
			dto.setOperateNumber(serialEntity.getSignGoodsQty());
			dto.setOperateTime(serialEntity.getCreateTime());
		}else{
			// 操作件数
			dto.setOperateNumber(newEntity.getSignGoodsQty());
			// 操作时间  update By269044  菜鸟轨迹考核-签收时间优化需求：将显示的签收时间优化为Foss收到PDA同步数据的时间
			dto.setOperateTime(newEntity.getCreateTime());
		}
		// 单据编号
		dto.setBillNo(null);
		// 车牌号
		dto.setVehicleNo(null);
		// 备注--签收人姓名
		String deliverymanName = StringUtil.replace(newEntity.getDeliverymanName(), NotifyCustomerConstants.DEFAULT_GOODS_AREA_CODE, "");
		String signNote = newEntity.getSignNote();
		StringBuffer notes = new StringBuffer();
		if (StringUtil.isNotBlank(deliverymanName)) {
			notes.append("签收人:");
			notes.append(deliverymanName); 
		}
		if (StringUtil.isNotBlank(signNote)) {
			notes.append(" 签收备注");
			notes.append(signNote);
		}
		dto.setNotes(notes.toString());
		
//					dto.setNotes(StringUtil.replace(newEntity.getDeliverymanName(), NotifyCustomerConstants.DEFAULT_GOODS_AREA_CODE, ""));
		// 流水号
		dto.setSerialNo(null);
		// 离开后预计到达下一操作部门时间
		dto.setPlanArriveTime(null);
		// 派送人员姓名
		dto.setDeliveryName(null);
		// 派送人员电话
		dto.setDeliveryPhone(null);
		// 签收人姓名
		dto.setSignManName(StringUtil.replace(newEntity.getDeliverymanName(), NotifyCustomerConstants.DEFAULT_GOODS_AREA_CODE, ""));
		LOGGER.info(ReflectionToStringBuilder.toString(dto));
		dtoList.add(dto);
					
		return dtoList;
	}
	
	/**
	 * 设置 运单管理接口.
	 * 
	 * @param waybillManagerService the new 运单管理接口
	 */
	public void setWaybillManagerService(IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	/**
	 * 设置 组织信息 Service接口.
	 * 
	 * @param orgAdministrativeInfoService the new 组织信息 Service接口
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * 设置 通知客户Service.
	 * 
	 * @param notifyCustomerService the new 通知客户Service
	 */
	public void setNotifyCustomerService(INotifyCustomerService notifyCustomerService) {
		this.notifyCustomerService = notifyCustomerService;
	}

	/**
	 * 设置 运单轨迹服务跟踪.
	 * 
	 * @param trackingService the new 运单轨迹服务跟踪
	 */
	public void setTrackingService(ITrackingService trackingService) {
		this.trackingService = trackingService;
	}

	/**
	 * 设置 运单状态服务接口.
	 * 
	 * @param actualFreightService the new 运单状态服务接口
	 */
	public void setActualFreightService(IActualFreightService actualFreightService) {
		this.actualFreightService = actualFreightService;
	}

	/**
	 * 设置 到达联管理接口.
	 * 
	 * @param arriveSheetManngerService the new 到达联管理接口
	 */
	public void setArriveSheetManngerService(IArriveSheetManngerService arriveSheetManngerService) {
		this.arriveSheetManngerService = arriveSheetManngerService;
	}

	/**
	 * 设置 派送排单Service接口.
	 * 
	 * @param deliverbillService the new 派送排单Service接口
	 */
	public void setDeliverbillService(IDeliverbillService deliverbillService) {
		this.deliverbillService = deliverbillService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	public void setWaybillSignResultService(IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}

	public void setSignChangeService(ISignChangeService signChangeService) {
		this.signChangeService = signChangeService;
	}

	public void setUnloadTaskService(IUnloadTaskService unloadTaskService) {
		this.unloadTaskService = unloadTaskService;
	}

	public void setExternalBillService(IExternalBillService externalBillService) {
		this.externalBillService = externalBillService;
	}

	public void setWaybillPendingService(IWaybillPendingService waybillPendingService) {
		this.waybillPendingService = waybillPendingService;
	}
	
	public void setLdpAgencyDeptService(ILdpAgencyDeptService ldpAgencyDeptService) {
		this.ldpAgencyDeptService = ldpAgencyDeptService;
	}

	public void setAirQueryModifyPickupbillService(IAirQueryModifyPickupbillService airQueryModifyPickupbillService) {
		this.airQueryModifyPickupbillService = airQueryModifyPickupbillService;
	}

	public void setAirQueryModifyTfrPickupBillService(IAirQueryModifyTfrPickupBillService airQueryModifyTfrPickupBillService) {
		this.airQueryModifyTfrPickupBillService = airQueryModifyTfrPickupBillService;
	}

	public void setAirHandOverBillService(IAirHandOverBillService airHandOverBillService) {
		this.airHandOverBillService = airHandOverBillService;
	}

	public void setLdpExternalBillService(
			ILdpExternalBillService ldpExternalBillService) {
		this.ldpExternalBillService = ldpExternalBillService;
	}

	public void setLdpExternalBillTrackService(ILdpExternalBillTrackService ldpExternalBillTrackService) {
		this.ldpExternalBillTrackService = ldpExternalBillTrackService;
	}

	public void setSignDetailService(ISignDetailService signDetailService) {
		this.signDetailService = signDetailService;
	}

	public void setPrintAgentWaybillService(
			IPrintAgentWaybillService printAgentWaybillService) {
		this.printAgentWaybillService = printAgentWaybillService;
	}

	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}

	public void setWaybillExpressService(
			IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}
	
}