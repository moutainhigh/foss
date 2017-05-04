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
 * PROJECT NAME	: pkp-sign
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/service/impl/WaybillQueryForBseService.java
 * 
 * FILE NAME        	: WaybillQueryForBseService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Collections;

import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPictureEntity;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IFreightRouteService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILdpAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedDriverService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOwnDriverService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonExpressVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverAssociationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressVehicleDto;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverHandoverbillDao;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IHandoverbillReturnDao;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverbillService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverHandoverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.VisibleHandBillReturnEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveSheetDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerConditionDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerDto;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.sign.api.server.service.IDeliverHandlerService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignDetailService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillQueryForBseService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.define.WaybillQueryForBseConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.DeliverbillDetailDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.DeliverySituationDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.StockDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WayBillOtherForBseDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WayBillStatusForBseDto;
import com.deppon.foss.module.pickup.sign.api.shared.exception.WaybillQueryForBseException;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILabeledGoodService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillPendingService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillQueryService;
import com.deppon.foss.module.pickup.waybill.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.SimpleWaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.define.ExpWaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPendingDto;
import com.deppon.foss.module.transfer.load.api.server.dao.IDeliverLoadGapReportDao;
import com.deppon.foss.module.transfer.load.api.server.service.IDeliverLoadTaskService;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.foss.module.transfer.load.api.shared.domain.QueryDiffReportByWayBillEntity;

/**
 * 
 * 提供给综合查询的接口实现
 * 
 * @author ibm-wangfei
 * @date Dec 24, 2012 5:35:00 PM
 */
public class WaybillQueryForBseService implements IWaybillQueryForBseService {
	private static final Logger LOGGER = LoggerFactory.getLogger(WaybillQueryForBseService.class);
	private static final String AIR_RECEIVE_METHOD = "空运偏线签收";// 空运偏线签收
	private static final String LWS_RECEIVE_METHOD = "快递代理签收";// 快递代理签收
	private static final String RECEIVE_METHOD_DELIVER = "派送";// 派送
	private static final String RECEIVE_METHOD_PICKUP = "自提";// 自提

	// 通知客户Service
	private INotifyCustomerService notifyCustomerService;
	// 运单待处理保存 Service
	private IWaybillPendingService waybillPendingService;
	// 走货路径服务类
	private IFreightRouteService freightRouteService;
	// 用于运单和更改单查询
	private IWaybillQueryService waybillQueryService;
	// 运单管理接口
	private IWaybillManagerService waybillManagerService;
	/**
	 * 运单签收结果service
	 */
	IWaybillSignResultService waybillSignResultService;
	/**
	 * 快递代理网点运价方案明细Service接口.
	 */
	private ILdpAgencyDeptService ldpAgencyDeptService;
	/**
	 * 组织信息 Service接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 到达联service
	 */
	private IArriveSheetManngerService arriveSheetManngerService;
	/**
	 * 派送排单Service接口
	 */
	private IDeliverbillService deliverbillService;
	/**
	 * “公司司机”的数据库对应数据访问Service接口
	 */
	private IOwnDriverService ownDriverService;

	/**
	 * “外请车司机”的数据库对应数据访问
	 */
	private ILeasedDriverService leasedDriverService;
	/**
	 * 货签服务接口
	 */
	private ILabeledGoodService labeledGoodService;
	/**
	 * 签收明细service
	 */
	private ISignDetailService signDetailService;
	/**
	 * 派送处理接口
	 */
	private IDeliverHandlerService deliverHandlerService;
	/**
	 * IDeliverLoadTaskService
	 */
	private IDeliverLoadTaskService deliverLoadTaskService;
	private ICommonExpressVehicleService commonExpressVehicleService;
	/**
	 * 派送交单DAO
	 */
	private IDeliverHandoverbillDao deliverHandoverbillDao;
	/**
	 * 待排运单退回DAO
	 */
	private IHandoverbillReturnDao handoverbillReturnDao;

	/**
	 * 区域服务类
	 */
	private IAdministrativeRegionsService administrativeRegionsService;

	/**
	 * 派送装车差异DAO
	 */
	private IDeliverLoadGapReportDao deliverLoadGapReportDao;
	/**
	 * 
	 * 根据运单号查询运单其他信息
	 * 
	 * @param waybillNo
	 * @return WayBillOtherForBseDto
	 * @author ibm-wangfei
	 * @date Dec 24, 2012 5:35:38 PM
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IWaybillQueryForBseService#queryOtherInfoByWaybillNo(java.lang.String)
	 */
	@Override
	public WayBillOtherForBseDto queryOtherInfoByWaybillNo(String waybillNo) throws WaybillQueryForBseException {
		if (StringUtil.isBlank(waybillNo)) {
			throw new WaybillQueryForBseException("运单号必须输入。");
		}
		if (LOGGER.isDebugEnabled()) {
			// 输出waybillNo
			LOGGER.debug(waybillNo);
		}
		NotifyCustomerConditionDto conditionDto = new NotifyCustomerConditionDto();
		conditionDto.setWaybillNo(waybillNo);
		// 查询运单信息
		NotifyCustomerDto notifyCustomerDto = notifyCustomerService.queryNotificationByWaybillNo(conditionDto);
		if (notifyCustomerDto == null) {
			throw new WaybillQueryForBseException("无法检索到运单。");
		}
		WayBillOtherForBseDto wayBillOtherForBseDto = new WayBillOtherForBseDto();
		// 开单时间
		wayBillOtherForBseDto.setBillTime(notifyCustomerDto.getBillTime());
		// 预计到达时间
		wayBillOtherForBseDto.setPreCustomerPickupTime(notifyCustomerDto.getPreCustomerPickupTime());
		// 开单组织
		wayBillOtherForBseDto.setCreateOrgCode(notifyCustomerDto.getCreateOrgCode());
		// 设置出发部门
		/**
		 * 由于零担电子运单计划走货路径需要设置开单部门作为始发部门，所以做特殊处理
		 * （323098王鹏涛）
		 */
		ActualFreightEntity actualFreightEntity  = waybillManagerService.queryActualFreightByNo(waybillNo);
		if(null!=actualFreightEntity && WaybillConstants.LTLEWAYBILL.equals(actualFreightEntity.getWaybillType())){
			WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
			if(waybillEntity!=null)
				notifyCustomerDto.setStartOrgCode(waybillEntity.getCreateOrgCode());
		}else{
			notifyCustomerDto.setStartOrgCode(getStartOrgCode(waybillNo));
		}
		try {
			// 查询走货线路
			// 若为整车，则不设置走货线路
			if (!ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(notifyCustomerDto.getProductCode())) {
				// 若为小件，则走货路径的起始部门应该为制单部门，而非收入部门（即收货部门）
				if (ExpWaybillConstants.directDetermineIsExpressByProductCode(notifyCustomerDto.getProductCode())) {
					// 集中开单出发部门不一定是收货部门，因此现在定义一个专用字段startOrgCode
					wayBillOtherForBseDto.setLoadLineCode(freightRouteService.queryRouteStringBySourceTarget(
							notifyCustomerDto.getCreateOrgCode(), notifyCustomerDto.getCustomerPickupOrgCode(),
							notifyCustomerDto.getProductCode()));
				} else {
					// 集中开单出发部门不一定是收货部门，因此现在定义一个专用字段startOrgCode
					// 集中开单计划走货路径、预配线路”根据开单‘收货部门（出发部门）’----‘到达部门’线路显示货物走货路径
					wayBillOtherForBseDto.setLoadLineCode(freightRouteService.queryRouteStringBySourceTarget(
							notifyCustomerDto.getStartOrgCode(), notifyCustomerDto.getCustomerPickupOrgCode(),
							notifyCustomerDto.getProductCode()));
				}
			}
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
		}
		// 到达部门 -- 提货网点
		wayBillOtherForBseDto.setCustomerPickupOrgCode(notifyCustomerDto.getCustomerPickupOrgCode());
		if (null != notifyCustomerDto.getStorageCharge()
				&& notifyCustomerDto.getStorageCharge().compareTo(BigDecimal.ZERO) != 0) {
			// 过滤数据 派送方式非自提 整车、偏线、空运及快递货物免收保管费
			if (notifyCustomerDto.getReceiveMethod().indexOf(SignConstants.RECEIVE_METHOD) == -1
					|| WaybillConstants.MONTH_PAYMENT.equals(notifyCustomerDto.getPaidMethod())
					|| ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(notifyCustomerDto.getProductCode())
					|| ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(notifyCustomerDto.getProductCode())
					|| ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(notifyCustomerDto.getProductCode())
					|| ExpWaybillConstants.directDetermineIsExpressByProductCode(notifyCustomerDto.getProductCode())) {
				wayBillOtherForBseDto.setStorageCharge(BigDecimal.ZERO);

			} else if (notifyCustomerDto.getStorageCharge().compareTo(new BigDecimal(SettlementReportNumber.FIVE)) < 0) {
				wayBillOtherForBseDto.setStorageCharge(new BigDecimal(SettlementReportNumber.FIVE));
			} else {
				wayBillOtherForBseDto.setStorageCharge(notifyCustomerDto.getStorageCharge().setScale(0,
						BigDecimal.ROUND_HALF_UP));
			}
		} else {
			wayBillOtherForBseDto.setStorageCharge(notifyCustomerDto.getStorageCharge());
		}
		// 运单状态
		wayBillOtherForBseDto.setWaybillStatus(notifyCustomerDto.getWaybillStatus());
		// 预付费保密
		wayBillOtherForBseDto.setSecretPrepaid(notifyCustomerDto.getSecretPrepaid());

		return wayBillOtherForBseDto;
	}

	/**
	 * 根据运单号获取出发部门
	 * 
	 * @author WangQianJin
	 * @date 2013-7-30 上午11:03:20
	 */
	private String getStartOrgCode(String waybillNo) {
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
		if (waybillEntity != null) {
			/**
			 * 判断是否是集中开单
			 */
			if (FossConstants.YES.equals(waybillEntity.getPickupCentralized())) {
				// 集中开单查开单组所属中转场的默认出发部门
				// 判断是否是 异地开单
				String createOrgCode = waybillEntity.getCreateOrgCode();
				if (StringUtil.isNotEmpty(waybillNo)) {
					WaybillPictureEntity entity = new WaybillPictureEntity();
					entity.setActive(PricingConstants.CENTRALIZE_PICKUP_YES);
					entity.setWaybillNo(waybillNo);
					entity = waybillPendingService.queryWaybillPictureByEntity(entity);
					// 异地开单
					if (entity != null && FossConstants.NO.equals(entity.getLocal())) {
						// 根据本属开单组去查询
						LOGGER.info(entity.getLocal());
						createOrgCode = entity.getLocalBillGroupCode();
					}
				}
				// 集中开单查开单组所属中转场的默认出发部门
				SaleDepartmentEntity saleDepartment = waybillManagerService
						.queryPickupCentralizedDeptCode(createOrgCode);
				if (saleDepartment != null && saleDepartment.getCode() != null) {
					return saleDepartment.getCode();
				} else {
					return null;
				}
			} else {
				// 非集中开单收货部门为出发部门
				return waybillEntity.getReceiveOrgCode();
			}
		} else {
			return null;
		}
	}

	/**
	 * 
	 * 根据运单号查询运单状态，返回一个对象的集合
	 * 
	 * @param waybillNo
	 * @return
	 * @author ibm-wangfei
	 * @date Dec 24, 2012 5:35:52 PM
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IWaybillQueryForBseService#queryWaybillStatusByWaybillNoForPkp(java.lang.String)
	 */
	@Override
	public List<WayBillStatusForBseDto> queryWaybillStatusByWaybillNoForPkp(String waybillNo)
			throws WaybillQueryForBseException {
		if (StringUtil.isBlank(waybillNo)) {
			throw new WaybillQueryForBseException("运单号必须输入。");
		}
		if (LOGGER.isDebugEnabled()) {
			// 输出waybillNo
			LOGGER.debug(waybillNo);
		}
		List<WayBillStatusForBseDto> wayBillStatusForBseDtos = new ArrayList<WayBillStatusForBseDto>();
		WayBillStatusForBseDto wayBillStatusForBseDto;

		// 查询运单
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);

		if (waybillEntity == null) {
			/************************************************* 查询运单号对应的PDA状态 ***********************/
			// 根据运单号查询待处理运单基本信息
			WaybillPendingDto waybillPendingDto = new WaybillPendingDto();
			// 运单号/订单号
			waybillPendingDto.setMixNo(waybillNo);
			WaybillPendingEntity waybillPending = new WaybillPendingEntity();
			// 运单类型为PDA待处理
			waybillPending.setPendingType(WaybillConstants.WAYBILL_STATUS_PDA_PENDING);
			// 默认查询全部 运输性质的待处理运单
			waybillPending.setProductCode("all");
			waybillPendingDto.setWaybillPending(waybillPending);
			// 查询运单待处理表
			List<WaybillPendingEntity> waybillPendingEntitys = waybillPendingService.queryPending(waybillPendingDto);
			if (CollectionUtils.isNotEmpty(waybillPendingEntitys)) {
				waybillPending = waybillPendingEntitys.get(0);
				// 运单状态
				if (StringUtil.equals(FossConstants.NO, waybillPending.getActive())) {
					wayBillStatusForBseDto = new WayBillStatusForBseDto();
					// 设置PDA状态
					wayBillStatusForBseDto.setGoodsStatus(WaybillQueryForBseConstants.PDA_WAYBILL_CREATE);
					// 流水号集合 ---查开单货件流水号集合
					List<LabeledGoodEntity> labeledGoods = labeledGoodService.queryAllSerialByWaybillNo(waybillNo);
					if (CollectionUtils.isNotEmpty(labeledGoods)) {
						List<String> serialnos = new ArrayList<String>();
						for (LabeledGoodEntity labeledGoodEntity : labeledGoods) {
							serialnos.add(labeledGoodEntity.getSerialNo());
						}
						wayBillStatusForBseDto.setSerialNos(serialnos);
					}
					// 设置开单件数
					wayBillStatusForBseDto.setGoodsQty(waybillPending.getGoodsQtyTotal());
					// 当前位置
					OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService
							.queryOrgAdministrativeInfoByCodeClean(waybillPending.getCreateOrgCode());
					if (orgAdministrativeInfoEntity != null) {
						wayBillStatusForBseDto.setCurrentLocation(orgAdministrativeInfoEntity.getName());
					}
					// 开单时间
					wayBillStatusForBseDto.setOperatorDate(waybillPending.getBillTime());
					wayBillStatusForBseDtos.add(wayBillStatusForBseDto);
				}
			}
			return wayBillStatusForBseDtos;
		}

		wayBillStatusForBseDto = new WayBillStatusForBseDto();
		if (StringUtil.equals(WaybillConstants.WAYBILL_STATUS_PENDING_ACTIVE, waybillEntity.getPendingType())
				|| StringUtil.equals(WaybillConstants.WAYBILL_STATUS_PC_ACTIVE, waybillEntity.getPendingType())
				|| StringUtil.equals(WaybillConstants.WAYBILL_STATUS_RFC_ACTIVE, waybillEntity.getPendingType())) {
			// 运单等于PDA补录、暂存已开单、更改生效
			// 设置PDA状态
			wayBillStatusForBseDto.setGoodsStatus(WaybillQueryForBseConstants.WAYBILL_CREATE);
			wayBillStatusForBseDto = getWayBillStatusForBseDto(wayBillStatusForBseDto, waybillNo);
			// 流水号集合 ---查开单货件流水号集合
			List<LabeledGoodEntity> labeledGoods = labeledGoodService.queryAllSerialByWaybillNo(waybillNo);
			if (CollectionUtils.isNotEmpty(labeledGoods)) {
				List<String> serialnos = new ArrayList<String>();
				for (LabeledGoodEntity labeledGoodEntity : labeledGoods) {
					serialnos.add(labeledGoodEntity.getSerialNo());
				}
				wayBillStatusForBseDto.setSerialNos(serialnos);
			}
			// 设置开单件数
			wayBillStatusForBseDto.setGoodsQty(waybillEntity.getGoodsQtyTotal());
			// 当前位置
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService
					.queryOrgAdministrativeInfoByCodeClean(waybillEntity.getCreateOrgCode());
			if (orgAdministrativeInfoEntity != null) {
				wayBillStatusForBseDto.setCurrentLocation(orgAdministrativeInfoEntity.getName());
			}
			// 开单时间
			wayBillStatusForBseDto.setOperatorDate(waybillEntity.getBillTime());
			wayBillStatusForBseDtos.add(wayBillStatusForBseDto);
		}
		/************************************************* 查询运单号对应的“派送中”状态 **************************************************/
		DeliverbillDetailDto detaildto = new DeliverbillDetailDto();
		detaildto.setWaybillNo(waybillNo); // 运单号
		detaildto.setActive(FossConstants.YES); // 有效
		detaildto.setDestroyed(FossConstants.NO); // 未作废
		detaildto.setArriveSheetStatus((ArriveSheetConstants.STATUS_DELIVER));// 派送中
		List<DeliverbillDetailDto> deliverbillList = deliverHandlerService.queryPartDeliverBillByWaybillNo(detaildto);

		if (CollectionUtils.isNotEmpty(deliverbillList)) {
			wayBillStatusForBseDtos.remove(wayBillStatusForBseDto);// 删除上一次的记录
			for (DeliverbillDetailDto deliverbillDetailDto : deliverbillList) {
				WayBillStatusForBseDto wayBillStatusForBseDto1 = new WayBillStatusForBseDto();
				// 调用中转查询流水号集合
				List<String> serialNos = deliverLoadTaskService.queryLastLoadSerialNos(
						deliverbillDetailDto.getDeliverbillNo(), waybillNo);
				if (CollectionUtils.isNotEmpty(serialNos) && serialNos.size() > 0) { // 如果有流水号集合信息
					wayBillStatusForBseDto1.setSerialNos(serialNos);// 得到流水号
				}
				wayBillStatusForBseDto1.setCurrentLocation(deliverbillDetailDto.getCreateOrgName());// 当前派送单创建部门名称
				wayBillStatusForBseDto1.setGoodsQty(deliverbillDetailDto.getArriveSheetGoodsQty());// 到达联的到达联件数
				wayBillStatusForBseDto1.setGoodsStatus(WaybillQueryForBseConstants.STATUS_DELIVER);// 状态是送货中
				wayBillStatusForBseDtos.add(wayBillStatusForBseDto1);
			}

		}
		/************************************************* 查询运单号对应的“正常签收”、“异常签收”状态 ********************************************/
		WaybillSignResultEntity entity = new WaybillSignResultEntity();
		entity.setWaybillNo(waybillEntity.getWaybillNo());
		entity.setActive(FossConstants.ACTIVE);
		// 根据运单号查询运单签结果里的运单信息
		WaybillSignResultEntity newEntity = waybillSignResultService.queryWaybillSignResultByWaybillNo(entity);
		if (newEntity == null) {
			LOGGER.info("没有符合条件的记录");
			return wayBillStatusForBseDtos;
		}
		wayBillStatusForBseDto = new WayBillStatusForBseDto();
		// 设置签收状态
		if (StringUtil.equals(ArriveSheetConstants.SITUATION_NORMAL_SIGN, newEntity.getSignSituation())) {
			wayBillStatusForBseDto.setGoodsStatus(WaybillQueryForBseConstants.SITUATION_NORMAL_SIGN);
			wayBillStatusForBseDto = getWayBillStatusForBseDto(wayBillStatusForBseDto, waybillNo);
		} else if (StringUtil.equals(ArriveSheetConstants.SITUATION_PARTIAL_SIGN, newEntity.getSignSituation())) {
			wayBillStatusForBseDto.setGoodsStatus(WaybillQueryForBseConstants.SITUATION_PARTIAL_SIGN);
			wayBillStatusForBseDto = getWayBillStatusForBseDto(wayBillStatusForBseDto, waybillNo);
		} else {
			wayBillStatusForBseDto.setGoodsStatus(WaybillQueryForBseConstants.SITUATION_UNNORMAL_SIGN);
			wayBillStatusForBseDto = getWayBillStatusForBseDto(wayBillStatusForBseDto, waybillNo);
		}
		// 流水号集合 ---查询专线签收的流水号集合
		StockDto dto = new StockDto();
		dto.setWaybillNo(waybillNo); // 运单号
		dto.setActive(FossConstants.YES); // 有效
		dto.setDestroyed(FossConstants.NO); // 未作废
		dto.setStatus(ArriveSheetConstants.STATUS_SIGN);// 签收
		wayBillStatusForBseDto.setSerialNos(signDetailService.querySerialNoByWaybillNo(dto));
		// 设置开单件数
		wayBillStatusForBseDto.setGoodsQty(newEntity.getSignGoodsQty());
		// 签收时间
		wayBillStatusForBseDto.setOperatorDate(newEntity.getSignTime());
		List<WayBillStatusForBseDto> returnDtos = new ArrayList<WayBillStatusForBseDto>();

		returnDtos.add(wayBillStatusForBseDto);
		return returnDtos;

	}

	/**
	 * foss-panguoyang
	 * 
	 * @param wayBillStatusForBseDto
	 * @param waybillNo
	 * @return
	 */
	public WayBillStatusForBseDto getWayBillStatusForBseDto(WayBillStatusForBseDto wayBillStatusForBseDto,
			String waybillNo) {
		// 运单状态
		String status = waybillManagerService.getWaybillStatus(waybillNo);
		if (status != null) {
			if (WaybillConstants.OBSOLETE.equals(status)) {
				wayBillStatusForBseDto.setGoodsStatus(WaybillQueryForBseConstants.WAYBILL_OBSOLETE);
			}
			if (WaybillConstants.ABORTED.equals(status)) {
				wayBillStatusForBseDto.setGoodsStatus(WaybillQueryForBseConstants.WAYBILL_ABORTED);
			}
		}
		return wayBillStatusForBseDto;
	}

	/**
	 * 自定义查询
	 * 
	 * @param where
	 *            where語句，count 总数
	 * @author 038590-foss-wanghui
	 * @date 2013-3-1 上午9:12:32
	 */
	@Override
	public List<SimpleWaybillDto> queryWayBillListByUserDefinedQuery(String where, int count) {
		return waybillQueryService.queryWayBillListByUserDefinedQuery(where, count);
	}

	public void setWaybillQueryService(IWaybillQueryService waybillQueryService) {
		this.waybillQueryService = waybillQueryService;
	}

	/**
	 * 综合查询增加派送情况查询
	 * 
	 * @author foss-meiying
	 * @date 2013-7-2 上午9:12:32
	 */
	public DeliverySituationDto queryDeliverySituationByWaybill(String waybillNo) {
		DeliverySituationDto deliverySituationDto = new DeliverySituationDto();
		WaybillEntity entity = waybillManagerService.queryPartWaybillByNo(waybillNo);
		if (entity == null) {
			return null;
		} else {
			// 通知情况
			deliverySituationDto.setNotificationList(notifyCustomerService.queryNotificationByWaybillNo(waybillNo));
			DeliverbillDto deliverbillDto = new DeliverbillDto();

			// DMANA-3694 预派送单状态字段根据派送单状态实时更新
			// // 派送单状态为 已确认 已下拉 已签收确认
			// List<String> deliverStatusList = new ArrayList<String>();
			// //已确认
			// deliverStatusList.add(DeliverbillConstants.STATUS_CONFIRMED);
			// // 派送单状态为"已签收确认"
			// deliverStatusList.add(DeliverbillConstants.STATUS_SIGNINFO_CONFIRMED);
			// //已下拉
			// deliverStatusList.add(DeliverbillConstants.STATUS_PDA_DOWNLOADED);
			// deliverbillDto.setDeliverbillStatus(deliverStatusList);//派送单状态集合
			deliverbillDto.setWaybillNo(waybillNo);// 运单号

			// 派送情况
			List<DeliverbillDto> deliverbilldtoList = deliverbillService.queryPartDeliverbillbyWaybill(deliverbillDto);
			List<DeliverbillDto> commonList = new ArrayList<DeliverbillDto>();

			// 获取中转派送差异报告List
			List<QueryDiffReportByWayBillEntity> diffReportList = deliverLoadGapReportDao
					.queryDiffReportByWayBill(waybillNo);
			// 获取结算接口派送单号集合
			List<String> list1 = new ArrayList<String>();
			if (CollectionUtils.isNotEmpty(deliverbilldtoList)) {
				for (DeliverbillDto diffDto : deliverbilldtoList) {
					list1.add(diffDto.getDeliverbillNo());
				}
			}

			// 获取中转接口派送单号集合
			List<String> list2 = new ArrayList<String>();
			if (CollectionUtils.isNotEmpty(diffReportList)) {
				for (QueryDiffReportByWayBillEntity list : diffReportList) {
					list2.add(list.getDeliverBillNo());
				}
				//list2中转和结算差异
				list2.removeAll(list1);
			}
			// 传入运单号+派送单号差异集合list2
			if (CollectionUtils.isNotEmpty(list2)) {
				deliverbillDto.setDeliverList(list2);
				deliverbilldtoList = deliverbillService.queryPartDeliverbillbyWaybill(deliverbillDto);
			}

			List<QueryDiffReportByWayBillEntity> newDiff = new ArrayList<QueryDiffReportByWayBillEntity>();
			// 获取中转差异（不存在结算派送单明细的派送单）派送报告List
			if (CollectionUtils.isNotEmpty(list2)) {
				for (int m = 0; m < list2.size(); m++) {
					for (QueryDiffReportByWayBillEntity diff : diffReportList) {
						if (list2.get(m).equals(diff.getDeliverBillNo())) {
							newDiff.add(diff);
						}
					}
				}
			}

			// 遍历结算接口少货数据
			List<String> list3 = new ArrayList<String>();
			if (CollectionUtils.isNotEmpty(list1) && CollectionUtils.isNotEmpty(diffReportList)) {
				for (int i = 0; i < list1.size(); i++) {
					for (QueryDiffReportByWayBillEntity waybillEntity : diffReportList) {
						if (list1.get(i).equals(waybillEntity.getDeliverBillNo()) && waybillEntity.getNotes() != "") {
							list3.add(list1.get(i));
							newDiff.add(waybillEntity);
						}
					}
				}
			}

			DeliverbillDto commonDto = new DeliverbillDto();
			commonDto.setDeliverList(list3);
			commonList = deliverbillService.queryPartDeliverbillbyWaybill(commonDto);
			if(CollectionUtils.isNotEmpty(commonList)){ 
				for (DeliverbillDto comDto : commonList) {
					comDto.setStatus(DictUtil.rendererSubmitToDisplay(comDto.getStatus(),
							DictionaryConstants.PKP_DELIVERBILL_STATUS));
				}
			}
			if(CollectionUtils.isNotEmpty(list3)&&CollectionUtils.isNotEmpty(deliverbilldtoList)&&CollectionUtils.isNotEmpty(commonList)){
				for(int k=0;k<list3.size();k++){
					for(DeliverbillDto dto:deliverbilldtoList){
						for (DeliverbillDto comDto : commonList) {
							if(list3.get(k).equals(dto.getDeliverbillNo())&&list3.get(k).equals(comDto.getDeliverbillNo())){
								comDto.setArrangeGoodsQty(dto.getArrangeGoodsQty());
							}
						}
					}
				}
			}

			// --end--
			if (CollectionUtils.isNotEmpty(deliverbilldtoList)) {
				for (DeliverbillDto deliverbillDto2 : deliverbilldtoList) {
					deliverbillDto2.setStatus(DictUtil.rendererSubmitToDisplay(deliverbillDto2.getStatus(),
							DictionaryConstants.PKP_DELIVERBILL_STATUS));
					if (StringUtils.equals(deliverbillDto2.getIsExpress(), FossConstants.YES)
							&& (StringUtils.isNotEmpty(deliverbillDto2.getDriverCode()) || StringUtils
									.isNotEmpty(deliverbillDto2.getVehicleNo()))) {
						// 如果是小件派送单,并且司机工号或者司机车牌号不为空
						ExpressVehicleDto expressVeh = new ExpressVehicleDto();
						// 司机工号
						expressVeh.setEmpCode(deliverbillDto2.getDriverCode());
						// 车牌号
						expressVeh.setVehicleNo(deliverbillDto2.getVehicleNo());
						List<ExpressVehicleDto> expressVehicleDtos = commonExpressVehicleService
								.queryExpressVehicleNoListBySelectiveCondition(expressVeh, NumberConstants.NUMBER_10,
										NumberConstants.ZERO);
						if (CollectionUtils.isNotEmpty(expressVehicleDtos)) {
							// 司机电话
							deliverbillDto2.setDriverTel(expressVehicleDtos.get(0).getMobilePhone());
						}
					} else {
						// 若司机工号不为空
						if (StringUtil.isNotEmpty(deliverbillDto2.getDriverCode())) {
							// 内部司机根据工号查询相关信息
							DriverAssociationDto driverAssociationDto = ownDriverService
									.queryOwnDriverAssociationDtoByDriverCode(deliverbillDto2.getDriverCode());
							// 用来交互“司机（公司、外请）”的数据实体相关联信息的DTO不为空
							validaDriverAssociation(deliverbillDto2,
									driverAssociationDto);
							// 如果车牌号不为空
						} else if (StringUtil.isNotEmpty(deliverbillDto2.getVehicleNo())) {
							// 外请司机根据车牌号进行查询
							List<DriverAssociationDto> driverAssociationDtos = leasedDriverService
									.queryLesasedDriverAssociationDtoByTruckVehicleNo(deliverbillDto2.getVehicleNo());

							if (CollectionUtils.isNotEmpty(driverAssociationDtos)) {
								// 司机电话
								deliverbillDto2.setDriverTel(driverAssociationDtos.get(0).getDriverPhone());
							}
						}

						// 给结算中转共有数据赋值
						if (CollectionUtils.isNotEmpty(list3) && CollectionUtils.isNotEmpty(deliverbilldtoList)
								&& CollectionUtils.isNotEmpty(newDiff)) {
							for (int i = 0; i < list3.size(); i++) {
								for (DeliverbillDto fDto : deliverbilldtoList) {
									if (list3.get(i).equals(fDto.getDeliverbillNo())
											&& newDiff.get(i).getDeliverBillNo().equals(list3.get(i))) {
										fDto.setDriverName(null);
										fDto.setOperateTime(null);
										fDto.setDriverTel(null);
										if(StringUtil.isEmpty(newDiff.get(i).getNotes())){
											fDto.setGapType(newDiff.get(i).getGapType()+"/--");
										}else{
										fDto.setGapType(newDiff.get(i).getGapType()+"/"+newDiff.get(i).getNotes());
										}
										fDto.setArrangeGoodsQty(newDiff.get(i).getPlanLoadQty().intValue());
									}
								}
							}
						}

						// 给差异的派送单重新赋值
						if (CollectionUtils.isNotEmpty(newDiff) && CollectionUtils.isNotEmpty(deliverbilldtoList)) {
							validaDeliverbill(deliverbilldtoList, newDiff);
						}
					}
					// 所有派送情况的集合
					List<DeliverbillDto> allList = new ArrayList<DeliverbillDto>();
					allList.addAll(deliverbilldtoList);
					allList.addAll(commonList);
					Collections.sort(allList, new Comparator<DeliverbillDto>() {
						public int compare(DeliverbillDto o1, DeliverbillDto o2) {
							if (o1.getOperateTime() == null || o2.getOperateTime() == null) {
								return -1;
							} else {
								return o1.getOperateTime().compareTo(o2.getOperateTime());
							}
						}
					});
					deliverySituationDto.setDeliverbilldtoList(allList);
				}
			}

			// 判断是否为经济快递中的快递代理签收
			boolean isExpressExternal = false;
			if (WaybillConstants.directDetermineIsExpressByProductCode(entity.getProductCode())) {
				OuterBranchExpressEntity outerBranchExpressEntity = ldpAgencyDeptService.queryLdpAgencyDeptByCode(
						entity.getCustomerPickupOrgCode(), FossConstants.ACTIVE);
				if (null != outerBranchExpressEntity) {
					isExpressExternal = true;
				}
			}
			List<WaybillSignResultEntity> signSituationList = new ArrayList<WaybillSignResultEntity>();
			if (isExpressExternal) {// 签收类型为快递代理签收
				WaybillSignResultEntity waybillSign = new WaybillSignResultEntity();
				waybillSign.setWaybillNo(waybillNo);
				signSituationList = waybillSignResultService.queryWaybillSignResultLit(waybillSign);// 根据运单号查询返回签收情况
				if (CollectionUtils.isNotEmpty(signSituationList)) {// 如果查询签收情况不为空
					for (WaybillSignResultEntity waybillSignResultEntity : signSituationList) {
						waybillSignResultEntity.setReceiveMethod(LWS_RECEIVE_METHOD);
						waybillSignResultEntity.setIsPdaSign(FossConstants.NO);// 非PDA签收
						waybillSignResultEntity.setSignStatus(null);// 到达联签收状态
						waybillSignResultEntity.setSignSituation(DictUtil.rendererSubmitToDisplay(
								waybillSignResultEntity.getSignSituation(), DictionaryConstants.PKP_SIGN_SITUATION));
					}
					deliverySituationDto.setSignSituationList(signSituationList);
					return deliverySituationDto;
				}
			}
			// 签收情况
			if (ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(entity.getProductCode())
					|| ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(entity.getProductCode())) {// 如何运输性质是偏线或空运查运单签收结果数据
				WaybillSignResultEntity waybillSign = new WaybillSignResultEntity();
				waybillSign.setWaybillNo(waybillNo);
				signSituationList = waybillSignResultService.queryWaybillSignResultLit(waybillSign);// 根据运单号查询返回签收情况
				if (CollectionUtils.isNotEmpty(signSituationList)) {// 如果查询签收情况不为空
					for (WaybillSignResultEntity waybillSignResultEntity : signSituationList) {
						waybillSignResultEntity.setReceiveMethod(AIR_RECEIVE_METHOD);
						waybillSignResultEntity.setIsPdaSign(FossConstants.NO);// 非PDA签收
						waybillSignResultEntity.setSignStatus(null);// 到达联签收状态
						waybillSignResultEntity.setSignSituation(DictUtil.rendererSubmitToDisplay(
								waybillSignResultEntity.getSignSituation(), DictionaryConstants.PKP_SIGN_SITUATION));
					}
				}
			} else {// 运输性质不为偏线空运
				ArriveSheetDto dto = new ArriveSheetDto();
				dto.setWaybillNo(waybillNo);// 运单号
				List<String> arriveSheetStatus = new ArrayList<String>();
				arriveSheetStatus.add(ArriveSheetConstants.STATUS_SIGN);// 签收
				arriveSheetStatus.add(ArriveSheetConstants.STATUS_REFUSE);// 拒收
				dto.setArriveSheetStatus(arriveSheetStatus);
				List<ArriveSheetEntity> arriveSheetList = arriveSheetManngerService.queryArriveSheetByLifeCycle(dto);
				if (CollectionUtils.isNotEmpty(arriveSheetList)) {// 如果查询签收情况不为空
					for (ArriveSheetEntity arriveSheetEntity : arriveSheetList) {
						WaybillSignResultEntity waybillSign = new WaybillSignResultEntity();
						// 签收情况包括签收时间、签收人、签收件数、到达联签收状态、签收情况、签收备注这几个字段，数据应该来源于签收表里面。
						waybillSign.setSignTime(arriveSheetEntity.getSignTime());// 签收时间
						if (SignConstants.DEFAULT_VALUE.equals(arriveSheetEntity.getDeliverymanName())) {
							waybillSign.setDeliverymanName(null);
						} else {
							waybillSign.setDeliverymanName(arriveSheetEntity.getDeliverymanName());// 签收人
						}
						if (ArriveSheetConstants.SIGN_RECEIVE_METHOD_DELIVER.equals(arriveSheetEntity
								.getReceiveMethod())) {// 签收提货方式--派送
							waybillSign.setReceiveMethod(RECEIVE_METHOD_DELIVER);
						} else if (ArriveSheetConstants.SIGN_RECEIVE_METHOD_PICKUP.equals(arriveSheetEntity
								.getReceiveMethod())) {// 签收提货方式--自提
							waybillSign.setReceiveMethod(RECEIVE_METHOD_PICKUP);
						}
						waybillSign.setSignGoodsQty(arriveSheetEntity.getSignGoodsQty());// 签收件数
						if (ArriveSheetConstants.SITUATION_GOODS_BACK.equals(arriveSheetEntity.getSituation())) {// 如果签收情况是货物拉回
							waybillSign.setSignNote(DictUtil.rendererSubmitToDisplay(arriveSheetEntity.getSignNote(),
									DictionaryConstants.PKP_PULLBACK_REASON));// 拉回原因备注
						} else {
							waybillSign.setSignNote(arriveSheetEntity.getSignNote());// 签收备注
						}
						waybillSign.setIsPdaSign(arriveSheetEntity.getIsPdaSign());// 得到是否PDA签收
						waybillSign.setDeliverymanType(DictUtil.rendererSubmitToDisplay(
								arriveSheetEntity.getDeliverymanType(), DictionaryConstants.PKP_SIGN_PERSON_TYPE));// 签收人类型
						waybillSign.setActive(arriveSheetEntity.getActive());// 是否有效
						if (StringUtils.isNotBlank(arriveSheetEntity.getSignStatus())) {
							waybillSign.setSignStatus(DictUtil.rendererSubmitToDisplay(
									arriveSheetEntity.getSignStatus(), DictionaryConstants.PKP_SIGN_STATUS));// 到达联签收状态
						}
						waybillSign.setProductCode(entity.getProductCode());// 运输性质
						waybillSign.setSignSituation(DictUtil.rendererSubmitToDisplay(arriveSheetEntity.getSituation(),
								DictionaryConstants.PKP_SIGN_SITUATION));
						// update
						// arriveSheetEntity.setSituation(ArriveSheetConstants.SITUATION_UNNORMAL_GOODSHORT);

						// 获得运单的签收类型 件数和流水号
						joinSignDetailInfo(arriveSheetEntity, waybillSign);

						/*
						 * if(ArriveSheetConstants.SITUATION_UNNORMAL_GOODSHORT.
						 * equals(arriveSheetEntity.getSituation())){ //流水号集合
						 * ---查询专线签收的流水号集合 SignDetailEntity signDetail = new
						 * SignDetailEntity();
						 * signDetail.setArrivesheetNo(arriveSheetEntity
						 * .getArrivesheetNo());
						 * signDetail.setGoodShorts(ArriveSheetConstants.Y);
						 * List<SignDetailEntity>
						 * signDetailList=signDetailService
						 * .querySignDetailByParams(signDetail); StringBuffer
						 * sBuffer=new StringBuffer();
						 * if(CollectionUtils.isNotEmpty(signDetailList)){
						 * sBuffer
						 * .append(signDetailList.size()+ArriveSheetConstants
						 * .BACKSLASH); for (SignDetailEntity signDetailEntity :
						 * signDetailList) { String serialNo =
						 * signDetailEntity.getSerialNo();
						 * sBuffer.append(serialNo+ArriveSheetConstants.COMMA);
						 * } if(StringUtils.isNotEmpty(sBuffer.toString())){
						 * String goodShortCount=sBuffer.toString().substring(0,
						 * sBuffer.toString().length()-1);
						 * waybillSign.setGoodShortAndSerialNo(goodShortCount);
						 * } }else{
						 * waybillSign.setGoodShortAndSerialNo(String.valueOf
						 * (0)); } }else{
						 * waybillSign.setGoodShortAndSerialNo(String
						 * .valueOf(0)); }
						 */

						signSituationList.add(waybillSign);
						// update
					}
				}
			}
			deliverySituationDto.setSignSituationList(signSituationList);

			// add by foss-sunyanfei 2015-8-14
			LOGGER.info("综合信息查询--交单情况 开始" + waybillNo);
			// 交单情况
			if (StringUtils.isNotEmpty(waybillNo)) {
				DeliverHandoverbillEntity deliverEntity = new DeliverHandoverbillEntity();
				List<DeliverHandoverbillEntity> deliverbillList = new ArrayList<DeliverHandoverbillEntity>();
				deliverEntity.setWaybillNo(waybillNo);// 运单号
				// 根据运单号查询交单信息列表
				List<DeliverHandoverbillEntity> deliverHandoverbillList = deliverHandoverbillDao
						.queryListByWaybillNo(deliverEntity);
				if (deliverHandoverbillList != null && deliverHandoverbillList.size() > 0) {
					for (int i = 0; i < deliverHandoverbillList.size(); i++) {
						DeliverHandoverbillEntity deliverHandoverbillEntity = new DeliverHandoverbillEntity();
						deliverHandoverbillEntity.setBillTime(deliverHandoverbillList.get(i).getBillTime()); // 交单时间
						deliverHandoverbillEntity.setPreDeliverDate(deliverHandoverbillList.get(i).getPreDeliverDate()); // 预计送货日期
						deliverHandoverbillEntity.setBillQty(deliverHandoverbillList.get(i).getBillQty()); // 交单件数
						deliverHandoverbillEntity.setBillOperateCode(deliverHandoverbillList.get(i)
								.getBillOperateCode()); // 交单人
						deliverHandoverbillEntity.setBillOperateName(deliverHandoverbillList.get(i)
								.getBillOperateName()); // 交单人名称
						deliverHandoverbillEntity.setBillOperateOrgCode(deliverHandoverbillList.get(i)
								.getBillOperateOrgCode()); // 交单部门
						deliverHandoverbillEntity.setBillOperateOrgName(deliverHandoverbillList.get(i)
								.getBillOperateOrgName()); // 交单部门名称
						deliverHandoverbillEntity.setDeliverRequire(deliverHandoverbillList.get(i).getDeliverRequire()); // 送货要求
						String provinceName = "";// 收货省份名称
						String cityName = "";// 收货市名称
						String distcodeName = "";// 收货区名称
						String address = "";// 收货地址
						String addressNote = "";// 收货地址备注
						// 省份
						if (StringUtils.isNotEmpty(deliverHandoverbillList.get(i).getReceiveCustomerProvCode())) {
							AdministrativeRegionsEntity provEntity = administrativeRegionsService
									.queryAdministrativeRegionsByCode(deliverHandoverbillList.get(i)
											.getReceiveCustomerProvCode());
							if (provEntity != null) {
								provinceName = StringUtil.defaultIfNull(provEntity.getName());
							}
						}
						// 城市
						cityName = validaDeliver(deliverHandoverbillList, i,
								cityName);
						// 地区
						if (StringUtils.isNotEmpty(deliverHandoverbillList.get(i).getReceiveCustomerDistCode())) {
							AdministrativeRegionsEntity distEntity = administrativeRegionsService
									.queryAdministrativeRegionsByCode(deliverHandoverbillList.get(i)
											.getReceiveCustomerDistCode());
							if (distEntity != null) {
								distcodeName = StringUtil.defaultIfNull(distEntity.getName());
							}
						}
						// 收货地址
						if (StringUtils.isNotEmpty(deliverHandoverbillList.get(i).getReceiveCustomerAddress())) {
							address = StringUtil.defaultIfNull(deliverHandoverbillList.get(i)
									.getReceiveCustomerAddress());
						}
						// 收货地址备注
						if (StringUtils.isNotEmpty(deliverHandoverbillList.get(i).getReceiveCustomerAddressNote())) {
							addressNote = StringUtil.defaultIfNull(deliverHandoverbillList.get(i)
									.getReceiveCustomerAddressNote());
						}
						// 拼接实际收货地址
						String addressStr = provinceName + cityName + distcodeName + address + addressNote;
						LOGGER.info("拼接实际收货地址   ---  " + addressStr);
						deliverHandoverbillEntity.setReceiveCustomerAddress(addressStr); // 实际收货地址
						// 如果当前交单记录是是有效的，则【退/撤单时间】置为空
						if (deliverHandoverbillList.get(i).getActive().equals(FossConstants.YES)) {
							deliverHandoverbillEntity.setModifyTime(null); // 退/撤单时间
						} else {
							deliverHandoverbillEntity.setModifyTime(deliverHandoverbillList.get(i).getModifyTime()); // 退/撤单时间
						}
						deliverHandoverbillEntity.setActive(deliverHandoverbillList.get(i).getActive()); // 是否有效交单
						LOGGER.info("综合信息查询--交单情况 --单个实体"
								+ ReflectionToStringBuilder.toString(deliverHandoverbillEntity));

						deliverbillList.add(deliverHandoverbillEntity);
					}
					for (int i = 0; i < deliverbillList.size(); i++) {
						LOGGER.info("综合信息查询--交单情况--单个实体 ---"
								+ ReflectionToStringBuilder.toString(deliverbillList.get(i)));
					}
					deliverySituationDto.setDeliverHandoverbillList(deliverbillList);
				}
			}
			LOGGER.info("综合信息查询--交单情况 结束" + waybillNo);
			LOGGER.info("综合信息查询--退单情况 开始" + waybillNo);
			// 退单情况
			if (StringUtils.isNotEmpty(waybillNo)) {
				VisibleHandBillReturnEntity visibleReturnEntity = new VisibleHandBillReturnEntity();
				List<VisibleHandBillReturnEntity> visibleReturnList = new ArrayList<VisibleHandBillReturnEntity>();
				String returnName = ""; // 退单原因中文名称
				visibleReturnEntity.setWaybillNo(waybillNo);// 运单号
				// 根据运单号查询退单信息列表
				List<VisibleHandBillReturnEntity> visibleHandBillReturnList = handoverbillReturnDao
						.queryWaitWaybillReturnListByWaybillNo(visibleReturnEntity);
				if (visibleHandBillReturnList != null && visibleHandBillReturnList.size() > 0) {
					for (int i = 0; i < visibleHandBillReturnList.size(); i++) {
						VisibleHandBillReturnEntity visibleHandBillReturnEntity = new VisibleHandBillReturnEntity();
						visibleHandBillReturnEntity.setCreateDate(visibleHandBillReturnList.get(i).getCreateDate()); // 退单时间
						// 将退单原因转为中文名称
						returnName = DictUtil.rendererSubmitToDisplay(visibleHandBillReturnList.get(i)
								.getReturnReason(), DictionaryConstants.PKP_VISIBLE_WAYBILL_RETURN);
						visibleHandBillReturnEntity.setReturnReason(returnName); // 退单原因
						visibleHandBillReturnEntity.setReturnReasonNotes(visibleHandBillReturnList.get(i)
								.getReturnReasonNotes()); // 退单原因备注
						visibleHandBillReturnEntity.setCreaterCode(visibleHandBillReturnList.get(i).getCreaterCode()); // 退单人
						visibleHandBillReturnEntity.setCreaterName(visibleHandBillReturnList.get(i).getCreaterName()); // 退单人名称
						visibleHandBillReturnEntity.setCreateOrgCode(visibleHandBillReturnList.get(i)
								.getCreateOrgCode()); // 退单部门
						visibleHandBillReturnEntity.setCreateOrgName(visibleHandBillReturnList.get(i)
								.getCreateOrgName()); // 退单部门名称
						LOGGER.info("综合信息查询--退单情况 结束" + ReflectionToStringBuilder.toString(visibleHandBillReturnEntity));
						visibleReturnList.add(visibleHandBillReturnEntity);
					}
					for (int i = 0; i < visibleReturnList.size(); i++) {
						LOGGER.info("综合信息查询--退单情况--单个实体 ---"
								+ ReflectionToStringBuilder.toString(visibleReturnList.get(i)));
					}
					deliverySituationDto.setVisibleHandBillReturnList(visibleReturnList);
				}
			}
			LOGGER.info("综合信息查询--退单情况 结束");
			// add by foss-sunyanfei 2015-8-14
			return deliverySituationDto;
		}

	}

	private void validaDriverAssociation(DeliverbillDto deliverbillDto2,
			DriverAssociationDto driverAssociationDto) {
		if (driverAssociationDto != null) {
			// 司机电话
			deliverbillDto2.setDriverTel(driverAssociationDto.getDriverPhone());
		} else {
			// 外请司机根据车牌号进行查询
			List<DriverAssociationDto> driverAssociationDtos = leasedDriverService
					.queryLesasedDriverAssociationDtoByTruckVehicleNo(deliverbillDto2
							.getVehicleNo());

			if (CollectionUtils.isNotEmpty(driverAssociationDtos)) {
				// 司机电话
				deliverbillDto2.setDriverTel(driverAssociationDtos.get(0).getDriverPhone());
			}
		}
	}

	private void validaDeliverbill(List<DeliverbillDto> deliverbilldtoList,
			List<QueryDiffReportByWayBillEntity> newDiff) {
		for (int i = 0; i < newDiff.size(); i++) {
			for (DeliverbillDto fDto : deliverbilldtoList) {
				if (newDiff.get(i).getDeliverBillNo().equals(fDto.getDeliverbillNo())) {
					fDto.setDriverName(null);
					fDto.setOperateTime(null);
					fDto.setDriverTel(null);
					if(StringUtil.isEmpty(newDiff.get(i).getNotes())){
						fDto.setGapType(newDiff.get(i).getGapType()+"/--");
					}else{
					fDto.setGapType(newDiff.get(i).getGapType()+"/"+newDiff.get(i).getNotes());
					}
					fDto.setArrangeGoodsQty(newDiff.get(i).getPlanLoadQty().intValue());
				}
			}
		}
	}

	private String validaDeliver(
			List<DeliverHandoverbillEntity> deliverHandoverbillList, int i,
			String cityName) {
		if (StringUtils.isNotEmpty(deliverHandoverbillList.get(i).getReceiveCustomerCityCode())) {
			AdministrativeRegionsEntity cityEntity = administrativeRegionsService
					.queryAdministrativeRegionsByCode(deliverHandoverbillList.get(i)
							.getReceiveCustomerCityCode());
			if (cityEntity != null) {
				cityName = StringUtil.defaultIfNull(cityEntity.getName());
			}
		}
		return cityName;
	}

	/**
	 * 获得运单异常类型 异常签收的件数/流水号 拼接为字符串 格式：异常类型 件数/流水号
	 * 
	 * @author foss-chenjunying
	 * @param arriveSheetEntity
	 *            到达联实体：用来判断运单的异常签收类型，及查询其汇签表记录 取值拼接字符串
	 * @param waybillSign
	 *            封装拼接后的字符串
	 * */
	private void joinSignDetailInfo(ArriveSheetEntity arriveSheetEntity, WaybillSignResultEntity waybillSign) {
		/**
		 * 异常-破损统计 同票多类异常使用
		 */
		int unnormalBreakCount = 0;
		/**
		 * 异常-污染统计 同票多类异常使用
		 */
		int unnormalPollutionCount = 0;
		/**
		 * 异常-潮湿统计 同票多类异常使用
		 */
		int unnormalDampCount = 0;
		/**
		 * 异常-内物短少统计 同票多类异常使用
		 */
		int unnormalGoodshortCount = 0;
		/**
		 * 异常-其他统计 同票多类异常使用
		 */
		int unnormalElseCount = 0;
		/**
		 * 存 异常-破损流水号 同票多类异常使用
		 */
		StringBuffer unnormalBreakSerialNos = new StringBuffer();
		/**
		 * 存 异常-污染流水号 同票多类异常使用
		 */
		StringBuffer unnormalPollutionSerialNos = new StringBuffer();
		/**
		 * 存 异常-潮湿流水号 同票多类异常使用
		 */
		StringBuffer unnormalDampSerialNos = new StringBuffer();
		/**
		 * 存 异常-内物短少流水号 同票多类异常使用
		 */
		StringBuffer unnormalGoodshortSerialNos = new StringBuffer();
		/**
		 * 存 异常-其他流水号 同票多类异常使用
		 */
		StringBuffer unnormalElseSerialNos = new StringBuffer();
		if (ArriveSheetConstants.SITUATION_UNNORMAL_SAMEVOTEODD.equals(arriveSheetEntity.getSituation())) {
			SignDetailEntity signDetail = new SignDetailEntity();
			signDetail.setArrivesheetNo(arriveSheetEntity.getArrivesheetNo());
			// 查询出符合条件的汇签表信息
			List<SignDetailEntity> signDetailList = signDetailService.querySignDetailByParams(signDetail);
			StringBuffer sBuffer = new StringBuffer();
			if (CollectionUtils.isNotEmpty(signDetailList)) {
				for (SignDetailEntity signDetailEntity : signDetailList) {
					if (StringUtils.isBlank(signDetailEntity.getSituation())) {
						continue;
					}
					// 异常-破损
					if (ArriveSheetConstants.SITUATION_UNNORMAL_BREAK.equals(signDetailEntity.getSituation())) {
						unnormalBreakCount += 1;
						// 拼接流水号
						unnormalBreakSerialNos.append(signDetailEntity.getSerialNo() + "、");
						continue;
					}
					// 异常-污染
					if (ArriveSheetConstants.SITUATION_UNNORMAL_POLLUTION.equals(signDetailEntity.getSituation())) {
						unnormalPollutionCount += 1;
						unnormalPollutionSerialNos.append(signDetailEntity.getSerialNo() + "、");
						continue;
					}
					// 异常-潮湿
					if (ArriveSheetConstants.SITUATION_UNNORMAL_DAMP.equals(signDetailEntity.getSituation())) {
						unnormalDampCount += 1;
						unnormalDampSerialNos.append(signDetailEntity.getSerialNo() + "、");
						continue;
					}
					// 异常-那内物短少
					if (ArriveSheetConstants.SITUATION_UNNORMAL_GOODSHORT.equals(signDetailEntity.getSituation())) {
						unnormalGoodshortCount += 1;
						unnormalGoodshortSerialNos.append(signDetailEntity.getSerialNo() + "、");
						continue;
					}
					// 异常-其他
					if (ArriveSheetConstants.SITUATION_UNNORMAL_ELSE.equals(signDetailEntity.getSituation())) {
						unnormalElseCount += 1;
						unnormalElseSerialNos.append(signDetailEntity.getSerialNo() + "、");
					}
				}
				// 异常-破损
				if (unnormalBreakCount > 0) {
					// 拼接字符串，格式为：异常类型 件数/流水号、流水号；异常类型 件数/流水号、流水号
					sBuffer.append(DictUtil.rendererSubmitToDisplay(ArriveSheetConstants.SITUATION_UNNORMAL_BREAK,
							DictionaryConstants.PKP_SIGN_SITUATION)
							+ ArriveSheetConstants.SPAN1
							+ unnormalBreakCount
							+ ArriveSheetConstants.BACKSLASH
							+ unnormalBreakSerialNos.toString().substring(0, unnormalBreakSerialNos.length() - 1) + ";");
				}
				// 异常-污染
				if (unnormalPollutionCount > 0) {
					sBuffer.append(DictUtil.rendererSubmitToDisplay(ArriveSheetConstants.SITUATION_UNNORMAL_POLLUTION,
							DictionaryConstants.PKP_SIGN_SITUATION)
							+ ArriveSheetConstants.SPAN1
							+ unnormalPollutionCount
							+ ArriveSheetConstants.BACKSLASH
							+ unnormalPollutionSerialNos.toString().substring(0,
									unnormalPollutionSerialNos.length() - 1) + ";");
				}
				// 异常-潮湿
				if (unnormalDampCount > 0) {
					sBuffer.append(DictUtil.rendererSubmitToDisplay(ArriveSheetConstants.SITUATION_UNNORMAL_DAMP,
							DictionaryConstants.PKP_SIGN_SITUATION)
							+ ArriveSheetConstants.SPAN1
							+ unnormalDampCount
							+ ArriveSheetConstants.BACKSLASH
							+ unnormalDampSerialNos.toString().substring(0, unnormalDampSerialNos.length() - 1) + ";");
				}
				// 异常-内物短少
				if (unnormalGoodshortCount > 0) {
					sBuffer.append(DictUtil.rendererSubmitToDisplay(ArriveSheetConstants.SITUATION_UNNORMAL_GOODSHORT,
							DictionaryConstants.PKP_SIGN_SITUATION)
							+ ArriveSheetConstants.SPAN1
							+ unnormalGoodshortCount
							+ ArriveSheetConstants.BACKSLASH
							+ unnormalGoodshortSerialNos.toString().substring(0,
									unnormalGoodshortSerialNos.length() - 1) + ";");
				}
				// 异常-其他
				if (unnormalElseCount > 0) {
					sBuffer.append(DictUtil.rendererSubmitToDisplay(ArriveSheetConstants.SITUATION_UNNORMAL_ELSE,
							DictionaryConstants.PKP_SIGN_SITUATION)
							+ ArriveSheetConstants.SPAN1
							+ unnormalElseCount
							+ ArriveSheetConstants.BACKSLASH
							+ unnormalElseSerialNos.toString().substring(0, unnormalElseSerialNos.length() - 1) + ";");
				}
				if (StringUtils.isNotEmpty(sBuffer.toString())) {
					String goodShortAndSerialNo = sBuffer.toString().substring(0, sBuffer.toString().length() - 1);
					waybillSign.setGoodShortAndSerialNo(goodShortAndSerialNo);
				}
			}
		}
		// 异常-破损
		else if (ArriveSheetConstants.SITUATION_UNNORMAL_BREAK.equals(arriveSheetEntity.getSituation())
				|| ArriveSheetConstants.SITUATION_UNNORMAL_POLLUTION.equals(arriveSheetEntity.getSituation())
				|| ArriveSheetConstants.SITUATION_UNNORMAL_DAMP.equals(arriveSheetEntity.getSituation())
				|| ArriveSheetConstants.SITUATION_UNNORMAL_GOODSHORT.equals(arriveSheetEntity.getSituation())
				|| ArriveSheetConstants.SITUATION_UNNORMAL_ELSE.equals(arriveSheetEntity.getSituation())) {
			SignDetailEntity signDetail = new SignDetailEntity();
			signDetail.setArrivesheetNo(arriveSheetEntity.getArrivesheetNo());
			signDetail.setSituation(arriveSheetEntity.getSituation());
			// 查询出符合条件的汇签表信息
			List<SignDetailEntity> signDetailList = signDetailService.querySignDetailByParams(signDetail);
			StringBuffer sBuffer = new StringBuffer(); // 用于拼接汇签表中要使用的信息
			if (CollectionUtils.isNotEmpty(signDetailList)) {
				// 拼接字符串，DictUtilrendererSubmitToDisplay将传入的数据字典code转换为相应的值显示
				// 如：SITUATION_UNNORMAL_BREAK="异常-破损"
				sBuffer.append(DictUtil.rendererSubmitToDisplay(arriveSheetEntity.getSituation(),
						DictionaryConstants.PKP_SIGN_SITUATION)
						+ ArriveSheetConstants.SPAN1
						+ signDetailList.size()
						+ ArriveSheetConstants.BACKSLASH);
				for (SignDetailEntity signDetailEntity : signDetailList) {
					// 拼接异常流水号，以顿号分隔
					String serialNo = signDetailEntity.getSerialNo();
					sBuffer.append(serialNo + "、");
				}
				// 字符串不为空，有相对应的异常签收详情，去掉最后一个顿号
				if (StringUtils.isNotEmpty(sBuffer.toString())) {
					String goodShortCount = sBuffer.toString().substring(0, sBuffer.toString().length() - 1);
					waybillSign.setGoodShortAndSerialNo(goodShortCount);
				}
			} else {
				waybillSign.setGoodShortAndSerialNo(String.valueOf(0));
			}
		} else {
			waybillSign.setGoodShortAndSerialNo(String.valueOf(0));
		}
	}

	/**
	 * 
	 * 通知客户
	 * 
	 * @param notifyCustomerService
	 * @author ibm-wangfei
	 * @date Dec 24, 2012 5:55:17 PM
	 */
	public void setNotifyCustomerService(INotifyCustomerService notifyCustomerService) {
		this.notifyCustomerService = notifyCustomerService;
	}

	public void setFreightRouteService(IFreightRouteService freightRouteService) {
		this.freightRouteService = freightRouteService;
	}

	public void setWaybillPendingService(IWaybillPendingService waybillPendingService) {
		this.waybillPendingService = waybillPendingService;
	}

	public void setWaybillManagerService(IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public void setWaybillSignResultService(IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}

	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setArriveSheetManngerService(IArriveSheetManngerService arriveSheetManngerService) {
		this.arriveSheetManngerService = arriveSheetManngerService;
	}

	public void setDeliverbillService(IDeliverbillService deliverbillService) {
		this.deliverbillService = deliverbillService;
	}

	public void setOwnDriverService(IOwnDriverService ownDriverService) {
		this.ownDriverService = ownDriverService;
	}

	public void setLeasedDriverService(ILeasedDriverService leasedDriverService) {
		this.leasedDriverService = leasedDriverService;
	}

	public void setLabeledGoodService(ILabeledGoodService labeledGoodService) {
		this.labeledGoodService = labeledGoodService;
	}

	public void setSignDetailService(ISignDetailService signDetailService) {
		this.signDetailService = signDetailService;
	}

	public void setDeliverHandlerService(IDeliverHandlerService deliverHandlerService) {
		this.deliverHandlerService = deliverHandlerService;
	}

	public void setDeliverLoadTaskService(IDeliverLoadTaskService deliverLoadTaskService) {
		this.deliverLoadTaskService = deliverLoadTaskService;
	}

	public void setCommonExpressVehicleService(ICommonExpressVehicleService commonExpressVehicleService) {
		this.commonExpressVehicleService = commonExpressVehicleService;
	}

	public void setLdpAgencyDeptService(ILdpAgencyDeptService ldpAgencyDeptService) {
		this.ldpAgencyDeptService = ldpAgencyDeptService;
	}

	public void setDeliverHandoverbillDao(IDeliverHandoverbillDao deliverHandoverbillDao) {
		this.deliverHandoverbillDao = deliverHandoverbillDao;
	}

	public void setHandoverbillReturnDao(IHandoverbillReturnDao handoverbillReturnDao) {
		this.handoverbillReturnDao = handoverbillReturnDao;
	}

	public void setAdministrativeRegionsService(IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}

	public void setDeliverLoadGapReportDao(IDeliverLoadGapReportDao deliverLoadGapReportDao) {
		this.deliverLoadGapReportDao = deliverLoadGapReportDao;
	}
	
}