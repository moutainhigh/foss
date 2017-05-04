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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/server/service/impl/VehicleAssembleBillService.java
 *  
 *  FILE NAME          :VehicleAssembleBillService.java
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

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressLineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOutfieldService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOwnVehicleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressLineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.MapDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.base.common.api.server.service.IBusinessMonitorService;
import com.deppon.foss.module.base.common.api.shared.define.BusinessMonitorIndicator;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignDetailService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.agency.api.server.service.ITruckStowageService;
import com.deppon.foss.module.settlement.agency.api.shared.dto.StlVehicleAssembleBillDto;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableConditionDto;
import com.deppon.foss.module.transfer.common.api.cubcgray.model.VestBatchResult;
import com.deppon.foss.module.transfer.common.api.cubcgray.model.VestResponse;
import com.deppon.foss.module.transfer.common.api.server.service.IFossToCubc;
import com.deppon.foss.module.transfer.common.api.server.service.IFossToCubcService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.CUBCGrayContants;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrSerialNumberRuleEnum;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferDictionaryConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.TotalNumberEntityEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcBillPayableConditionRequest;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcBillPayableConditionResponse;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcValidationSourceBillNoResponse;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcVehicleAssembleBillRequest;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcVehicleAssembleBillResponse;
import com.deppon.foss.module.transfer.common.api.shared.dto.GrayParameterDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.WayBillAssembleDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.common.server.utils.CUBCGrayUtil;
import com.deppon.foss.module.transfer.load.api.server.dao.IVehicleAssembleBillDao;
import com.deppon.foss.module.transfer.load.api.server.service.IAsynTruckStowageToCubc;
import com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService;
import com.deppon.foss.module.transfer.load.api.server.service.ILoadService;
import com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService;
import com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService;
import com.deppon.foss.module.transfer.load.api.server.service.IVehicleSealService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.define.TaskTruckConstant;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.MakeUpWaybillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.MotorstowagebillrecordEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.RewardOrPunishAgreementEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.VehicleAssembleBillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.VehicleAssembleBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.VehicleAssembleBillOptLogEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.DeleteHandOverBillFromVehicleAssembleBillDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.GetInfoWhenVehicleNoLostFocusDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.PrintCarriageContractDataDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.PrintVehicleAssembleBillDetailDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.PrintVehicleAssembleBillHeaderDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryVehicleAssembleBillConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryVehicleAssembleBillDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.RewardOrPunishAgreementDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.SerialNoLoadExceptionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.UpdateHandOverBillStateDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.UpdateVehicleAssembleBillDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.UpdateVehicleAssembleBillStateDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.UpdateVehicleNoAndDriverInfoFromVehicleAssembleBillDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.VehicleSealInfoDto;
import com.deppon.foss.module.transfer.load.api.shared.vo.VehicleAssembleBillVo;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IInviteVehicleService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IPassInviteApplyService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanDetailService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PassInviteApplyEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.OrgEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * @className: VehicleAssembleBillService
 * @author: ShiWei shiwei@outlook.com
 * @description: 配载单模块service类
 * @date: 2012-11-8 下午2:52:37
 * 
 */
public class VehicleAssembleBillService implements IVehicleAssembleBillService {

	/**
	 * 记录日志
	 */
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

	/**
	 * 本模块dao
	 * 
	 * 
	 */
	private IVehicleAssembleBillDao vehicleAssembleBillDao;

	/**
	 * 交接单模块Service
	 */
	private IHandOverBillService handOverBillService;

	/** 签收明细service */
	private ISignDetailService signDetailService;

	/**
	 * tfr common，生成配载车次号
	 */
	private ITfrCommonService tfrCommonService;

	/**
	 * 综合车辆service
	 */
	private IVehicleService vehicleService;

	/**
	 * 综合公司车service，用于查询货柜信息
	 */
	private IOwnVehicleService ownVehicleService;

	/**
	 * 综合外场接口，用于判断到达部门是否支持自动分拣
	 */
	private IOutfieldService outfieldService;

	/**
	 * 任务车辆service，用于修改、作废配载单时调用接口更新任务车辆明细信息
	 */
	private ITruckTaskService truckTaskService;

	/**
	 * 结算service，传入配载车次号，获取是否已做出发付款确认
	 */
	private IBillPayableService billPayableService;

	/**
	 * 结算service，作废配载单时红冲
	 */
	private ITruckStowageService truckStowageService;

	/**
	 * 发车计划service
	 */
	private ITruckDepartPlanDetailService truckDepartPlanDetailService;

	/**
	 * 外请车约车接口，用于获取外请车费用
	 */
	private IPassInviteApplyService passInviteApplyService;

	/**
	 * 外请车约车接口，用于更改约车使用状态
	 */
	private IInviteVehicleService inviteVehicleService;

	/**
	 * 线路service，用于获取两部门之间的公里数，当为自有车时，计算总运费
	 */
	private ILineService lineService;

	private IExpressLineService expresslineService;

	/**
	 * 组织接口，用于判断到达部门是外场还是营业部
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/**
	 * 应用监控服务
	 */
	private IBusinessMonitorService businessMonitorService;

	/**
	 * 用户获取上级部门
	 */
	private ILoadService loadService;

	private static final String ZERO = "0";

	/**
	 * 快递配载车次号前缀
	 */
	private static final String PACKAGE_ASSEMBLE_PREFIX = "KD-";

	/**
	 * 数据字典
	 * */
	private IConfigurationParamsService configurationParamsService;

	/**
	 * 运单服务
	 */
	private IWaybillManagerService waybillManagerService;

	/**
	 * 封签service
	 */
	private IVehicleSealService vehicleSealService;

	/**
	 * CUBC新增
	 */
	private IAsynTruckStowageToCubc asynTruckStowageToCubc;

	/**
	 * 调用cubc同步接口
	 */
	private IFossToCubc fossToCubc;
	
	/**
	 * 调用cubc常用同步接口
	 */
	private IFossToCubcService fossToCubcService;
	

	private CUBCGrayUtil cubcUtil;

	public void setCubcUtil(CUBCGrayUtil cubcUtil) {
		this.cubcUtil = cubcUtil;
	}

	public void setFossToCubcService(IFossToCubcService fossToCubcService) {
		this.fossToCubcService = fossToCubcService;
	}

	public void setAsynTruckStowageToCubc(
			IAsynTruckStowageToCubc asynTruckStowageToCubc) {
		this.asynTruckStowageToCubc = asynTruckStowageToCubc;
	}

	public void setFossToCubc(
			IFossToCubc fossToCubc) {
		this.fossToCubc = fossToCubc;
	}

	public void setVehicleSealService(IVehicleSealService vehicleSealService) {
		this.vehicleSealService = vehicleSealService;
	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	public void setLoadService(ILoadService loadService) {
		this.loadService = loadService;
	}

	public void setBusinessMonitorService(
			IBusinessMonitorService businessMonitorService) {
		this.businessMonitorService = businessMonitorService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setLineService(ILineService lineService) {
		this.lineService = lineService;
	}

	public void setExpresslineService(IExpressLineService expresslineService) {
		this.expresslineService = expresslineService;
	}

	public void setInviteVehicleService(
			IInviteVehicleService inviteVehicleService) {
		this.inviteVehicleService = inviteVehicleService;
	}

	public void setPassInviteApplyService(
			IPassInviteApplyService passInviteApplyService) {
		this.passInviteApplyService = passInviteApplyService;
	}

	public void setTruckDepartPlanDetailService(
			ITruckDepartPlanDetailService truckDepartPlanDetailService) {
		this.truckDepartPlanDetailService = truckDepartPlanDetailService;
	}

	public void setTruckStowageService(ITruckStowageService truckStowageService) {
		this.truckStowageService = truckStowageService;
	}

	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}

	public void setTruckTaskService(ITruckTaskService truckTaskService) {
		this.truckTaskService = truckTaskService;
	}

	public void setOutfieldService(IOutfieldService outfieldService) {
		this.outfieldService = outfieldService;
	}

	public void setOwnVehicleService(IOwnVehicleService ownVehicleService) {
		this.ownVehicleService = ownVehicleService;
	}

	public void setVehicleService(IVehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}

	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}

	public void setHandOverBillService(IHandOverBillService handOverBillService) {
		this.handOverBillService = handOverBillService;
	}

	public void setVehicleAssembleBillDao(
			IVehicleAssembleBillDao vehicleAssembleBillDao) {
		this.vehicleAssembleBillDao = vehicleAssembleBillDao;
	}

	/**
	 * 校验配载单明细是否全为包交接单或普通交接单
	 * 
	 * @param billDetailEntityList
	 * @return 是否包交接单
	 * @date 2013-10-29 下午10:16:33
	 * @author Ouyang
	 */
	private String getVehicleAssembleBillBePackage(
			List<VehicleAssembleBillDetailEntity> billDetailEntityList) {

		if (CollectionUtils.isEmpty(billDetailEntityList)) {
			throw new TfrBusinessException("请添加交接单！");
		}
		// 是否商务专递 交接单 默认false 否
		boolean isKDY = false;
		Set<String> set = new HashSet<String>();
		for (VehicleAssembleBillDetailEntity item : billDetailEntityList) {
			String handOverBillNo = item.getHandOverBillNo();
			HandOverBillEntity handOverBillEntity = handOverBillService
					.queryHandOverBillByNo(handOverBillNo);
			if (handOverBillEntity == null) {
				throw new TfrBusinessException("交接单" + handOverBillNo
						+ "已作废或不存在！");
			}
			if(StringUtils.isNotEmpty(handOverBillNo)&& (
					StringUtils.equals(handOverBillNo.substring(0, 2), "ky")||
					StringUtils.equals(handOverBillNo.substring(0, LoadConstants.SONAR_NUMBER_3), "KYB"))){
				isKDY=true;
				break;  
			}
			// xiaobc update start..........2014-08-26
			// 是否是包交接单
			if (StringUtils.equals(handOverBillEntity.getBePackage(),
					FossConstants.YES)) {
				set.add(handOverBillEntity.getBePackage());
			} else {
				// 根据交接单号查询交接单下第一个运单的运输性质
				String productType = this
						.queryProductTypeByHandoverNo(handOverBillNo);
				// 如果运输性质是快递(快递交接单)
				if (StringUtils.equals(productType, "PACKAGE")
						|| StringUtils.equals(productType, "RCP")
						|| StringUtils.equals(productType, "EPEP")) {
					set.add(FossConstants.YES);
				} else {// 零担交接单
					set.add(FossConstants.NO);
				}
			}
			// xiaobc update end..........

			if (set.size() > 1) {
				throw new TfrBusinessException("所选交接单必须全为包交接单和快递交接单或全为零担交接单!");
			}
		}

		if (isKDY) {
			// 表达为 商务专递单
			return "KDY";
		} else {
			return set.contains(FossConstants.YES) ? FossConstants.YES
					: FossConstants.NO;
		}
	}

	/**
	 * 传入配载单基本信息，交接单list，保存配载单信息
	 * 
	 * @author 045923-foss-shiwei
	 * @date 2012-11-8 下午2:55:09
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService#saveVehicleAssembleBill(com.deppon.foss.module.transfer.load.api.shared.domain.VehicleAssembleBillEntity,
	 *      java.util.List)
	 */
	@Override
	@Transactional
	public String saveVehicleAssembleBill(VehicleAssembleBillEntity baseEntity,
			List<VehicleAssembleBillDetailEntity> billDetailEntityList,
			RewardOrPunishAgreementDto rewardOrPunishDto) {
		baseEntity.setId(UUIDUtils.getUUID());
		// 获取当前时间
		Date nowDate = new Date();
		// 设置发车日期
		baseEntity.setLeaveTime(this.changeHoursForDate(baseEntity
				.getLeaveTime()));
		// 获取当前用户、部门等
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		// 创建时间
		baseEntity.setCreateDate(nowDate);
		// 创建人
		baseEntity.setCreateUserCode(currentInfo.getEmpCode());
		// 创建人name
		baseEntity.setCreateUserName(currentInfo.getEmpName());
		// 修改日期
		baseEntity.setModifyDate(baseEntity.getCreateDate());
		// 修改人code
		baseEntity.setModifyUserCode(baseEntity.getCreateUserCode());
		// 修改人name
		baseEntity.setModifyUserName(baseEntity.getCreateUserName());
		// 是否生成任务车辆明细
		baseEntity.setBeCreateTaskTruck(FossConstants.NO);
		// 币种
		baseEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		/*
		 * 出发部门 当前部门
		 */
		LOGGER.error("保存配载单时：获取本部门的上级组织：" + currentInfo.getCurrentDeptCode()
				+ "****查询开始");
		OrgAdministrativeInfoEntity orgEntity = loadService
				.querySuperiorOrgByOrgCode(currentInfo.getCurrentDeptCode());
		if (orgEntity == null) {
			throw new TfrBusinessException("获取本部门的上级组织失败(包括营业部、派送部、外场、总调)！");
		}
		baseEntity.setOrigOrgCode(orgEntity.getCode());
		// 出发部门code
		baseEntity.setOrigOrgName(orgEntity.getName());

		if (StringUtils.isNotEmpty(baseEntity.getVehicleNo())) {
			// 校验车牌号是否可用
			validateVehicleNoCanBeUsed(baseEntity.getVehicleNo(),
					baseEntity.getOrigOrgCode());
		}

		// 如果不是整车配载单，则校验发车计划
		if (StringUtils.equals(baseEntity.getAssembleType(),
				LoadConstants.VEHICLE_ASSEMBLE_TYPE_OWNER_LINE)) {
			// 线路信息，从发车计划中获取

			// 200968 20151223
			TruckDepartPlanDetailDto planDetail = null;
			// 200968 根据车牌号，调用综合接口，获取车辆信息
			VehicleAssociationDto vehicleAssociationDto = queryVehicleInfoByVehicleNo(baseEntity
					.getVehicleNo());
			if (vehicleAssociationDto != null) {
				// 200968 判断车牌号是否为挂车vehicletype_trailer
				if (StringUtils.equals(vehicleAssociationDto.getVehicleType(),
						"vehicletype_trailer")
						&& !vehicleAssociationDto.getVehicleMotorcadeName()
								.equals("外请车")) {
					planDetail = truckDepartPlanDetailService
							.queryDepartPlanDetailByTrailerVehicleNo(
									baseEntity.getOrigOrgCode(),
									baseEntity.getDestOrgCode(),
									baseEntity.getVehicleNo(),
									baseEntity.getLeaveTime());
				} else {
					planDetail = truckDepartPlanDetailService
							.queryLatestTruckDepartPlanDetail(
									baseEntity.getOrigOrgCode(),
									baseEntity.getDestOrgCode(),
									baseEntity.getVehicleNo(),
									baseEntity.getLeaveTime());
				}
				if (planDetail != null) {
					// 发车计划明细id
					baseEntity.setTruckDepartPlanDetailId(planDetail.getId());
					// 线路名称
					baseEntity.setLine(planDetail.getLineName());
				} else {
					throw new TfrBusinessException("未读取到发车计划信息，请确认该车辆是否已出发！");
				}
			}
		}

		// 2013-07-09 11:00 签收后不能整车配载zyx
		if (StringUtils.equals(baseEntity.getAssembleType(),
				LoadConstants.VEHICLE_ASSEMBLE_TYPE_CAR_LOAD)) {
			String billNo = "";
			for (VehicleAssembleBillDetailEntity vehicleAssembleBillDetail : billDetailEntityList) {
				String handOverBillNo = vehicleAssembleBillDetail
						.getHandOverBillNo();
				List<HandOverBillDetailEntity> handOverBillDetails = handOverBillService
						.queryHandOverBillDetailByNo(handOverBillNo);
				for (HandOverBillDetailEntity handOverBillDetail : handOverBillDetails) {
					String waybillNo = handOverBillDetail.getWaybillNo();
					billNo = waybillNo;
					List<SerialNoLoadExceptionDto> serialNoLoadExceptions = handOverBillService
							.queryWaybillDetailByNos(handOverBillNo, waybillNo);
					for (SerialNoLoadExceptionDto serialNoLoadException : serialNoLoadExceptions) {
						String serialNo = serialNoLoadException.getSerialNo();
						// 断货物是否已签收的接口，参数（运单号、流水号）
						String checkSerialNoIsSign = signDetailService
								.querySerialNoIsSign(waybillNo, serialNo);
						// 已签收出库
						if (StringUtils.equals(FossConstants.YES,
								checkSerialNoIsSign)) {
							throw new TfrBusinessException("运单:" + waybillNo
									+ "已签收, 不能整车配载!");
						}
					}
				}
			}
			/*
			 * 校验当前配载的交接单的车牌号是否包含在该运单开单时导入的约车编号中匹配的车牌号中，若不包含则不允许提交，
			 * 并给出提示："该车不是在运单开单时导入的约车编码中所关联的车辆中，请重新选择。"；若包含，通过此次校验，沿原规则处理。
			 */
			WaybillEntity waybill = waybillManagerService
					.queryWaybillBasicByNo(billNo);
			if (waybill != null) {
				// 获取运单 中的约车号
				String inviteNo = waybill.getOrderVehicleNum();
				// 如果约车编号不为空
				if (StringUtils.isNotBlank(inviteNo)) {
					// 调用约车服务
					List<String> vehicleNos = inviteVehicleService
							.queryInviteVehicleInfo(inviteNo);
					boolean isvehicle = false;
					for (String vehicleNo : vehicleNos) {
						// 不一致则抛业务异常，中断操作
						if (StringUtils.equals(vehicleNo,
								baseEntity.getVehicleNo())) {
							isvehicle = true;
						}
					}
					if (!isvehicle) {
						// 业务异常信息
						throw new TfrBusinessException(
								"该车不是在运单开单时导入的约车编码中所关联的车辆中，请重新选择！");
					}
				}
			}
			/*
			 * 校验系统中是否存在未作废的配载单中是否同时包含有该运单单号和车牌号，如果同时包含，则不允许提交，并给出提示：
			 * "该运单已配载在该车中，请重新选择运单或者车辆！"；否则，则允许提交
			 */
			List<QueryVehicleAssembleBillDto> vehicleAssembills = vehicleAssembleBillDao
					.queryVehicleAssemblyBillByWaybillNo(billNo);
			if (CollectionUtils.isNotEmpty(vehicleAssembills)) {
				for (QueryVehicleAssembleBillDto dto : vehicleAssembills) {
					if (StringUtils.equals(dto.getVehicleNo(),
							baseEntity.getVehicleNo())) {
						throw new TfrBusinessException(
								"该运单已配载在该车中，请重新选择运单或者车辆！");
					}
				}
			}

		}
		// 是否整车
		String isCarLoad = StringUtils.equals(baseEntity.getAssembleType(),
				LoadConstants.VEHICLE_ASSEMBLE_TYPE_CAR_LOAD) ? FossConstants.YES
				: FossConstants.NO;
		// 是否快递配载单 商务专递 返回KDY
		String bePackage = this
				.getVehicleAssembleBillBePackage(billDetailEntityList);// 校验配载单明细是否全为包交接单或普通交接单，并返回是否包交接单
		// 重新生成配载车次号
		String vehicleAssembleNo = generateVehicleAssembleNo(
				baseEntity.getDestOrgCode(), isCarLoad,
				baseEntity.getLeaveTime(), bePackage);
		// 设置配载车次号
		baseEntity.setVehicleAssembleNo(vehicleAssembleNo);
		LOGGER.error("----保存配载单时,计算财务数据------开始");
		// 如果是 如果是零担配载单 而选择了于零担合车则 保存不成功
		if (StringUtils.equals(bePackage, FossConstants.NO)
				&& StringUtils.equals(baseEntity.getBeInLTLCar(),
						FossConstants.YES)) {
			throw new TfrBusinessException("非快递配载单不允许勾选是否与零担合车！");
		}

		// 如果在途装卸、非最终到达，则总运费按0算
		if (StringUtils.equals(FossConstants.YES, baseEntity.getBeMidWayLoad())
				&& StringUtils.equals(FossConstants.NO,
						baseEntity.getBeFinallyArrive())
				&& baseEntity.getFeeTotal().compareTo(BigDecimal.ZERO) == 1) {
			throw new TfrBusinessException("在途装卸且非最终到达时，不计算总运费，请重新输入车牌号！");
		}
		// 如果计算总运费，且车辆为外请车，则总运费=首款+尾款
		if (StringUtils.equals(baseEntity.getVehicleOwnerShip(),
				ComnConst.ASSETS_OWNERSHIP_TYPE_LEASED)
				&& ((StringUtils.equals(baseEntity.getBeMidWayLoad(),
						FossConstants.NO) && StringUtils.equals(
						baseEntity.getBeInLTLCar(), FossConstants.NO)) || (StringUtils
						.equals(baseEntity.getBeMidWayLoad(), FossConstants.YES) && StringUtils
						.equals(baseEntity.getBeFinallyArrive(),
								FossConstants.YES)))
				&& (baseEntity.getFeeTotal().compareTo(
						baseEntity.getPrePaidFeeTotal().add(
								baseEntity.getArriveFeeTotal())) != 0)
				&& !StringUtils.equals(baseEntity.getPaymentType(), "CT")) {
			throw new TfrBusinessException(
					"“总运费”必须等于“预付运费总额”与“到付运费总额”之和，请重新输入运费！");
		}
		if (StringUtils.equals(baseEntity.getVehicleOwnerShip(),
				ComnConst.ASSETS_OWNERSHIP_TYPE_LEASED)
				&& StringUtils.equals(isCarLoad, FossConstants.YES)
				&& (StringUtils.equals(baseEntity.getBeMidWayLoad(),
						FossConstants.NO) || (StringUtils.equals(
						baseEntity.getBeMidWayLoad(), FossConstants.YES) && StringUtils
						.equals(baseEntity.getBeFinallyArrive(),
								FossConstants.YES)))
				&& baseEntity.getFeeTotal().compareTo(BigDecimal.ZERO) == 0) {
			throw new TfrBusinessException("车次“总运费”不能为0，请重新输入车牌号读取运费！");
		}

		LOGGER.error("----保存配载单时,计算财务数据------结束");
		//duh-276198-20150906-判断专线首款是否小于等于总车费的1/3
		
		if(StringUtils.equals(baseEntity.getVehicleOwnerShip(), ComnConst.ASSETS_OWNERSHIP_TYPE_LEASED)
				&& ( (StringUtils.equals(baseEntity.getBeMidWayLoad(), FossConstants.NO) 
						&& StringUtils.equals(baseEntity.getBeInLTLCar(),FossConstants.NO) )
						|| (StringUtils.equals(baseEntity.getBeMidWayLoad(), FossConstants.YES)
								&& StringUtils.equals(baseEntity.getBeFinallyArrive(), FossConstants.YES)))
				&&baseEntity.getAssembleType().equals("OWNER_LINE")
				&& !StringUtils.equals(baseEntity.getPaymentType(), "CT")){
			//计算专线预付车费最大值，最大值为总车费的1/3
			double preMaxfee = Math.floor(baseEntity.getFeeTotal().doubleValue()*1/LoadConstants.SONAR_NUMBER_3);
			BigDecimal bigPreMaxfee = new BigDecimal(preMaxfee);
			if (baseEntity.getPrePaidFeeTotal().compareTo(bigPreMaxfee) == 1) {
				throw new TfrBusinessException("专线首款不能大于总车费的1/3，请重新输入预付运费！");
			}
			// throw new
			// TfrBusinessException("专线首款不能大于总车费的1/3，请重新输入预付运费！"+bigPreMaxfee+"!!!"+preMaxfee);

		}
		
		//duh-276198-20150906-判断整车首款是否小于等于总车费的2/3
		
		if(StringUtils.equals(baseEntity.getVehicleOwnerShip(), ComnConst.ASSETS_OWNERSHIP_TYPE_LEASED)
				&& ( (StringUtils.equals(baseEntity.getBeMidWayLoad(), FossConstants.NO) 
						&& StringUtils.equals(baseEntity.getBeInLTLCar(),FossConstants.NO) )
						|| (StringUtils.equals(baseEntity.getBeMidWayLoad(), FossConstants.YES)
								&& StringUtils.equals(baseEntity.getBeFinallyArrive(), FossConstants.YES)))
				&&baseEntity.getAssembleType().equals("CAR_LOAD")
				&& !StringUtils.equals(baseEntity.getPaymentType(), "CT")){
			//计算整车预付车费最大值，最大值为总车费的2/3
			double preMaxfee = Math.floor(baseEntity.getFeeTotal().doubleValue()*2/LoadConstants.SONAR_NUMBER_3);
			BigDecimal bigPreMaxfee = new BigDecimal(preMaxfee);
			if (baseEntity.getPrePaidFeeTotal().compareTo(bigPreMaxfee) == 1) {
				throw new TfrBusinessException("专线首款不能大于总车费的2/3，请重新输入预付运费！");
			}
		}

		LOGGER.error("----保存配载单时,获取车牌或部门信息------开始");
		// BUG-57945
		if (StringUtils.equals(baseEntity.getVehicleOwnerShip(),
				ComnConst.ASSETS_OWNERSHIP_TYPE_LEASED)
				&& StringUtils.equals(baseEntity.getBeMidWayLoad(),
						FossConstants.YES)
				&& StringUtils.equals(baseEntity.getBeFinallyArrive(),
						FossConstants.NO)
				&& baseEntity.getFeeTotal() != null
				&& baseEntity.getFeeTotal().compareTo(BigDecimal.ZERO) != 0) {
			throw new TfrBusinessException("车次在途卸且非最终到达没有运费，请重新输入车牌号！");
		}
		if (StringUtils.equals(baseEntity.getBeMidWayOnlyLoad(),
				FossConstants.YES)) {
			if (StringUtils.isEmpty(baseEntity.getOnTheWayDestOrgCode())) {
				throw new TfrBusinessException("选择了中途只装不卸，必须填写中途到达部门！");
			}
			if (StringUtils.equals(baseEntity.getOnTheWayDestOrgCode(),
					baseEntity.getOrigOrgCode())
					|| StringUtils.equals(baseEntity.getOnTheWayDestOrgCode(),
							baseEntity.getDestOrgCode())) {
				throw new TfrBusinessException("中途到达部门不能选择当前部门或最终到达部门！");
			}
			OrgAdministrativeInfoEntity onTheWayDestOrgEntity = loadService
					.querySuperiorOrgByOrgCode(baseEntity
							.getOnTheWayDestOrgCode());
			if (onTheWayDestOrgEntity == null) {
				throw new TfrBusinessException(
						"获取中途到达部门的上级组织失败(包括营业部、派送部、外场、总调)！");
			}
			baseEntity.setOnTheWayDestOrgCode(onTheWayDestOrgEntity.getCode());
			baseEntity.setOnTheWayDestOrgName(onTheWayDestOrgEntity.getName());
		} else {
			baseEntity.setOnTheWayDestOrgCode(null);
			baseEntity.setOnTheWayDestOrgName(null);
		}

		if (StringUtils.equals(baseEntity.getVehicleOwnerShip(),
				ComnConst.ASSETS_OWNERSHIP_TYPE_LEASED)) {
			if (baseEntity.getDriverOfWeight() == null
					|| baseEntity.getDriverOfVolumn() == null) {
				throw new TfrBusinessException("外请车请填写司机自带货量！");
			} else {
				baseEntity.setDriverOfWeight(baseEntity.getDriverOfWeight());
				baseEntity.setDriverOfVolumn(baseEntity.getDriverOfVolumn());
				baseEntity.setApplicationRatedClearance(baseEntity
						.getApplicationRatedClearance());
				baseEntity.setApplicationRatedLoad(baseEntity
						.getApplicationRatedLoad());
				baseEntity.setBeCarSmallUsed(baseEntity.getBeCarSmallUsed());
			}
		} else {
			baseEntity.setDriverOfWeight(null);
			baseEntity.setDriverOfVolumn(null);
			baseEntity.setApplicationRatedClearance(null);
			baseEntity.setApplicationRatedLoad(null);
			baseEntity.setBeCarSmallUsed(null);
		}
		LOGGER.error("----保存配载单时,时效数据考核------开始");
		/** 构造时效条款 */
		if (StringUtils.equals(baseEntity.getBeTimeLiness(), FossConstants.YES)) {
			checkRewardInfo(
					rewardOrPunishDto.getRewardOrPunishAgreementEntity(),
					baseEntity);
			addRewardProt(rewardOrPunishDto, baseEntity);
		}
		LOGGER.error("----保存配载单时,时效数据考核------结束");
		List<String> trailerHandOverbills = new ArrayList<String>();
		// 构造交接单List，更新交接单状态为“已配载”
		List<UpdateHandOverBillStateDto> handOverBillList = new ArrayList<UpdateHandOverBillStateDto>();
		// 为明细列表中的实体增加属性
		for (int i = 0; i < billDetailEntityList.size(); i++) {
			// 获取明细中一条交接单
			VehicleAssembleBillDetailEntity detailEntity = billDetailEntityList
					.get(i);
			// 生成ID
			detailEntity.setId(UUIDUtils.getUUID());
			// 配载单ID
			detailEntity.setVehicleAssembleBillId(baseEntity.getId());
			// 创建时间 --配合BI
			detailEntity.setCreateDate(baseEntity.getCreateDate());
			// 如果交接单中的车牌号与配载单中的车牌号不一致，则需要调用车辆任务接口
			if (!StringUtils.equals(baseEntity.getVehicleNo(),
					detailEntity.getVehicleNo())) {
				trailerHandOverbills.add(detailEntity.getHandOverBillNo());
			}
			// 构造对象，修改交接单状态
			UpdateHandOverBillStateDto dto = new UpdateHandOverBillStateDto();
			// 交接单号
			dto.setHandOverBillNo(detailEntity.getHandOverBillNo());
			/*
			 * 目标状态：已配载
			 */
			dto.setTargetState(LoadConstants.HANDOVERBILL_STATE_ALREADY_ASSEMBLE);// 已配载
			handOverBillList.add(dto);
		}
		// 总运费、预付总运费、到付总运费
		baseEntity.setFeeTotal(baseEntity.getFeeTotal());
		// 预付运费总额
		baseEntity.setPrePaidFeeTotal(baseEntity.getPrePaidFeeTotal());
		// 到付运费总额
		baseEntity.setArriveFeeTotal(baseEntity.getArriveFeeTotal());
		LOGGER.error("----保存配载单时,开始写入数据库------");
		// 保存配载单信息
		vehicleAssembleBillDao.saveVehicleAssembleBill(baseEntity,
				billDetailEntityList);
		// 更新交接单状态
		handOverBillService.updateHandOverBillState(handOverBillList);

		LOGGER.error("----保存配载单时,调用结算接口------开始");
		/*
		 * 如果为外请车，则调用结算接口 (未在途装卸、或者在途装卸且最终到达时调用)，更改约车使用状态
		 */
		if (baseEntity.getVehicleOwnerShip().equals(
				ComnConst.ASSETS_OWNERSHIP_TYPE_LEASED)) {
			// 整车发往香港派送部不生成应付单
			if (!StringUtils.equals(FossConstants.YES, isCarLoad)
					|| !StringUtils.equals(baseEntity.getDestOrgCode(),
							"W01330403")) {
				// 调用结算接口
				callStlInterfaceWhenAddNew(baseEntity);
			}
		}

		LOGGER.error("配载车次号：" + vehicleAssembleNo + "生成任务车辆开始！");
		// 提前用挂牌号装车的交接单需要修改车辆任务
		if (CollectionUtils.isNotEmpty(trailerHandOverbills)
				&& trailerHandOverbills.size() > 0) {
			truckTaskService.modifyTruckTaskByTrailerHandOverBill(
					baseEntity.getVehicleNo(), trailerHandOverbills);
		}
		// 如果在途只装不卸则需要生成中途到达部门的车辆任务
		if (StringUtils.equals(baseEntity.getBeMidWayOnlyLoad(),
				FossConstants.YES)) {
			truckTaskService.createTruckTaskForMidLoad(vehicleAssembleNo,
					baseEntity.getOnTheWayDestOrgCode(),
					baseEntity.getOnTheWayDestOrgName());
		}
		truckTaskService.createTruckTaskByAssembleBill(vehicleAssembleNo);
		LOGGER.error("配载车次号：" + vehicleAssembleNo + "生成任务车辆结束！");
		// 放在最后校验是防止提前装车 校验同一个车辆任务下只有一个运费的车次存在否在保存不成功
		if (baseEntity.getFeeTotal() != null
				&& (BigDecimal.ZERO.compareTo(baseEntity.getFeeTotal()) < 0)
				&& StringUtils.equals(
						LoadConstants.VEHICLE_ASSEMBLE_TYPE_CAR_LOAD,
						baseEntity.getAssembleType())) {
			validInJoinCar(vehicleAssembleNo);
		}
		/* 应用监控服务 */
		// 配载单数量计数
		BusinessMonitorIndicator vehicleAssembleBillCounterIndicator = BusinessMonitorIndicator.ASSEMBLE_BILL_COUNT;
		businessMonitorService.counter(vehicleAssembleBillCounterIndicator,
				currentInfo);
		// 返回前台配载车次号
		return vehicleAssembleNo;
	}

	/**
	 * 新增配载单时如果是外请车，调用结算接口
	 * 
	 * @author 045923-foss-shiwei
	 * @date 2012-12-3 下午9:11:24
	 */
	private void callStlInterfaceWhenAddNew(VehicleAssembleBillEntity baseEntity) {
		// 当配载单“在途装卸”为否，或者“在途装卸”为是、且“最终到达”为是时调用
		if ((StringUtils.equals(FossConstants.NO, baseEntity.getBeMidWayLoad()) && StringUtils
				.equals(FossConstants.NO, baseEntity.getBeInLTLCar()))
				|| (StringUtils.equals(FossConstants.YES,
						baseEntity.getBeMidWayLoad()) && StringUtils.equals(
						FossConstants.YES, baseEntity.getBeFinallyArrive()))) {
			if (baseEntity.getFeeTotal().compareTo(BigDecimal.ZERO) != 0) {
				// 更改约车信息为使用状态
				inviteVehicleService.updateUseStatusForUsing(
						baseEntity.getInviteNo(), baseEntity.getVehicleNo());
				// 根据约车标号查询信息本门名称和编码 310248
				OrgEntity orgEntity = passInviteApplyService
						.queryOrgDtoByApplyByInviteNo(baseEntity.getInviteNo());
				// 结算dto
				StlVehicleAssembleBillDto dto = new StlVehicleAssembleBillDto();
				// 调用CUBC dto
				CubcVehicleAssembleBillRequest request = new CubcVehicleAssembleBillRequest();

				// 车次号
				dto.setVehicleAssembleNo(baseEntity.getVehicleAssembleNo());
				request.setVehicleAssembleNo(baseEntity.getVehicleAssembleNo());
				// 判断车辆是否为合同车，dto车辆所有权类型
				VehicleAssociationDto vehicleInfo = vehicleService
						.queryVehicleAssociationDtoByVehicleNo(baseEntity
								.getVehicleNo());
				if (vehicleInfo != null) {
					if (StringUtils.equals(FossConstants.YES,
							vehicleInfo.getBargainVehicle())) {
						// 外请合同车
						dto.setVehicleOwnerShip(LoadConstants.VEHICLE_OWNERSHIP_BARGAIN_VEHICLE);
						request.setVehicleOwnerShip(LoadConstants.VEHICLE_OWNERSHIP_BARGAIN_VEHICLE);
					} else {
						// 普通外请车
						dto.setVehicleOwnerShip(LoadConstants.VEHICLE_OWNERSHIP_LEASED_VEHICLE);
						request.setVehicleOwnerShip(LoadConstants.VEHICLE_OWNERSHIP_LEASED_VEHICLE);
					}
				} else {
					// 普通外请车
					dto.setVehicleOwnerShip(LoadConstants.VEHICLE_OWNERSHIP_LEASED_VEHICLE);
					request.setVehicleOwnerShip(LoadConstants.VEHICLE_OWNERSHIP_LEASED_VEHICLE);
				}
				// 车牌号
				dto.setVehicleNo(baseEntity.getVehicleNo());
				// 预付运费总额
				dto.setPrePaidFeeTotal(baseEntity.getPrePaidFeeTotal());
				// 付款方式
				dto.setPaymentType(baseEntity.getPaymentType());
				// 出发部门code，name
				dto.setOrigOrgName(baseEntity.getOrigOrgName());
				dto.setOrigOrgCode(baseEntity.getOrigOrgCode());
				// 发车日期
				dto.setLeaveTime(baseEntity.getLeaveTime());
				// 总运费
				dto.setFeeTotal(baseEntity.getFeeTotal());
				// 配载单中的司机手机号
				dto.setDriverPhone(baseEntity.getDriverMobilePhone());
				// 车牌号
				request.setVehicleNo(baseEntity.getVehicleNo());
				// 预付运费总额
				request.setPrePaidFeeTotal(baseEntity.getPrePaidFeeTotal());
				// 付款方式
				request.setPaymentType(baseEntity.getPaymentType());
				// 出发部门code，name
				request.setOrigOrgName(baseEntity.getOrigOrgName());
				request.setOrigOrgCode(baseEntity.getOrigOrgCode());
				// 发车日期
				request.setLeaveTime(baseEntity.getLeaveTime());
				// 总运费
				request.setFeeTotal(baseEntity.getFeeTotal());
				// 配载单中的司机手机号
				request.setDriverPhone(baseEntity.getDriverMobilePhone());

				if (null != orgEntity) {
					// 信息部名称 310248
					dto.setOrgName(orgEntity.getApplyPath());
					// 信息部编码310248
					dto.setOrgCode(orgEntity.getMinistryinformationCode());
					// 信息部名称
					request.setOrgName(orgEntity.getApplyPath());
					// 信息部编码
					request.setOrgCode(orgEntity.getMinistryinformationCode());
				}
				// 主驾驶姓名
				dto.setDriverName(baseEntity.getDriverName());
				// 司机code
				dto.setDriverCode(baseEntity.getDriverCode());
				// 到达部门name
				dto.setDestOrgName(baseEntity.getDestOrgName());
				// 到达部门code
				dto.setDestOrgCode(baseEntity.getDestOrgCode());
				// 币种
				dto.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
				// 是否押回单
				dto.setBeReturnReceipt(baseEntity.getBeReturnReceipt());
				// 到付运费总额
				dto.setArriveFeeTotal(baseEntity.getArriveFeeTotal());
				// 配载类型
				dto.setAssembleType(baseEntity.getAssembleType());
				// 主驾驶姓名
				request.setDriverName(baseEntity.getDriverName());
				// 司机code
				request.setDriverCode(baseEntity.getDriverCode());
				// 到达部门name
				request.setDestOrgName(baseEntity.getDestOrgName());
				// 到达部门code
				request.setDestOrgCode(baseEntity.getDestOrgCode());
				// 币种
				request.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
				// 是否押回单
				request.setBeReturnReceipt(baseEntity.getBeReturnReceipt());
				// 到付运费总额
				request.setArriveFeeTotal(baseEntity.getArriveFeeTotal());
				// 配载类型
				request.setAssembleType(baseEntity.getAssembleType());
				// 如果配载类型为整车，则得到整车运单号
				if (StringUtils.equals(
						LoadConstants.VEHICLE_ASSEMBLE_TYPE_CAR_LOAD,
						baseEntity.getAssembleType())) {
					// 获取交接单号
					String handOverBillNo = this
							.queryHandOverBillListByVNo(
									baseEntity.getVehicleAssembleNo()).get(0)
							.getHandOverBillNo();
					// 获取运单号
					String waybillNo = handOverBillService
							.queryHandOverBillDetailByNo(handOverBillNo).get(0)
							.getWaybillNo();
					dto.setWaybillNo(waybillNo);
					request.setWaybillNo(waybillNo);

					/***
					 * 调用结算接口获取应付单，单号（车次号）通过车次号查询配载单中的车牌号；
					 * 用车牌号与当前车牌号做对比，如果相等则抛出异常，提示。 目的：防止二次配载
					 * **/

					BillPayableConditionDto conditionDto = new BillPayableConditionDto();

					CubcBillPayableConditionRequest cubcBillPayableConditionRequest = new CubcBillPayableConditionRequest();
					// 传入查询条件运单号
					conditionDto.setWaybillNo(waybillNo);

					cubcBillPayableConditionRequest.setWaybillNo(waybillNo);
					// 设置查询条件 单据类型 整车首款和整车尾款
					String billType[] = new String[2];
					billType[0] = SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_FIRST;
					billType[1] = SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST;
					conditionDto.setBillTypes(billType);

					cubcBillPayableConditionRequest.setBillTypes(billType);
					
					//灰度方案
					GrayParameterDto parDto = new GrayParameterDto();
					parDto.setSourceBillType(CUBCGrayUtil.SBType.PZ.getName());
					parDto.setSourceBillNos(new String[] {baseEntity.getVehicleAssembleNo()});
					
					//调用灰度工具类方法
					VestResponse vestResponse = cubcUtil.getUcbcGrayData(parDto, new Throwable());
					if (CUBCGrayContants.SYSTEM_CODE_FOSS.equals(vestResponse.getVestBatchResult().get(0).getVestSystemCode())) {
						// 调用结算接口查询应付单信息
						List<BillPayableEntity> billPayableEntity = billPayableService
								.queryBillPayableByCondition(conditionDto);

						// 如果查询到应付单信息，则校验应付单中的车次号对应的车牌号是否与当前相等，相等则抛出异常
						if (CollectionUtils.isNotEmpty(billPayableEntity)
								&& billPayableEntity.size() > 0) {
							for (BillPayableEntity temp : billPayableEntity) {
								// 车次号
								String vechicleAssemNo = temp.getSourceBillNo();
								// 查询配载信息，通过车次号
								List<VehicleAssembleBillEntity> list = vehicleAssembleBillDao
										.queryVehicleAssembleBillByNo(vechicleAssemNo);
								if (list != null && list.size() > 0) {
									String vehicleNo = list.get(0).getVehicleNo();
									if (StringUtils.equals(
											baseEntity.getVehicleNo(), vehicleNo)) {
										throw new TfrBusinessException("该运单已用了车:"
												+ vehicleNo + "做了整车配载，不能再次用该车做配载！");
									}
								} else {
									// 有应付单，没有配载信息
								}

							}

						}
					} else {
						// 调用cubc接口查询应付单信息
						CubcBillPayableConditionResponse conditionResponse = null;
						try {
							LOGGER.error("FOSS调用cubc接口查询应付单信息,waybillNo:"
									+ waybillNo);
							conditionResponse = fossToCubc
									.queryBillPayableByCondition(cubcBillPayableConditionRequest);
							LOGGER.error("FOSS调用cubc接口查询应付单信息,返回车次号是"
									+ conditionResponse.getSourceBillNo());
						} catch (Exception e) {
							throw new TfrBusinessException("CUBC异常:" + e);
						}
						// 如果查询到应付单信息，则校验应付单中的车次号对应的车牌号是否与当前相等，相等则抛出异常
						if (null != conditionResponse) {
							if (CollectionUtils.isNotEmpty(conditionResponse.getSourceBillNo())
									&& conditionResponse.getSourceBillNo().size() > 0) {
								for (String temp : conditionResponse.getSourceBillNo()) {
									// 车次号
									String VechicleAssemNo = temp;
									// 查询配载信息，通过车次号
									List<VehicleAssembleBillEntity> list = vehicleAssembleBillDao
											.queryVehicleAssembleBillByNo(VechicleAssemNo);
									if (list != null && list.size() > 0) {
										String vehicleNo = list.get(0).getVehicleNo();
										if (StringUtils.equals(baseEntity.getVehicleNo(), vehicleNo)) {
											throw new TfrBusinessException(
													"该运单已用了车:" + vehicleNo + "做了整车配载，不能再次用该车做配载！");
										}
									} else {
										// 有应付单，没有配载信息
									}

								}
							}
						}
					}
				}
				CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
				// 员工工号
				request.setEmpCode(FossUserContext.getCurrentInfo()
						.getEmpCode());
				// 员工姓名
				request.setEmpName(FossUserContext.getCurrentInfo()
						.getEmpName());
				// 当前登录部门编码
				request.setCurrentDeptCode(FossUserContext.getCurrentInfo()
						.getCurrentDeptCode());
				// 当前登录部门名称
				request.setCurrentDeptName(FossUserContext.getCurrentInfo()
						.getCurrentDeptName());

				////cubcgray 335284-310248
				GrayParameterDto param = new GrayParameterDto();
				param.setSourceBillNos(new String[]{request.getDriverCode()});
				param.setSourceBillType(CUBCGrayUtil.SBType.WQC.getName());
				VestResponse response = cubcUtil.getCubcGrayDataByCustomer(param, new Throwable());
				boolean flag = true;//stl, not cubc
				for (VestBatchResult r : response.getVestBatchResult()) {
					if (CUBCGrayContants.SYSTEM_CODE_CUBC.equals(r.getVestSystemCode())) {
						flag = false;
						break;
					}
				}
				LOGGER.info("新增配载单CUBC分流标识：" + flag);
				if (flag) {
					// 调用结算接口
					this.truckStowageService.addTruckStowage(dto, currentInfo);
				} else {
					// 调用CUBC接口
					asynTruckStowageToCubc.pushAddTruckStowage(request);
				}
			} else {
				// 更改约车信息为使用状态
				inviteVehicleService.updateUseStatusForUsing(
						baseEntity.getInviteNo(), baseEntity.getVehicleNo());
			}

		}
	}

	/**
	 * @action 生成奖罚协议
	 * @author foss-heyongdong
	 * @date 2013年11月12日 08:37:19
	 * 
	 * */
	private void addRewardProt(RewardOrPunishAgreementDto rewardOrPunishDto,
			VehicleAssembleBillEntity baseEntity) {
		// 如果选择了奖罚协议且填写了内容
		List<RewardOrPunishAgreementEntity> addRewardOrPunishAgreement = new ArrayList<RewardOrPunishAgreementEntity>();
		// ;
		if (rewardOrPunishDto != null) {
			List<RewardOrPunishAgreementEntity> rewardOrPunishEntity = rewardOrPunishDto
					.getRewardOrPunishAgreementEntity();
			if (rewardOrPunishEntity == null
					|| rewardOrPunishEntity.size() <= 0) {
				throw new TfrBusinessException("请填写时效协议！");
			}
			for (int i = 0; i < rewardOrPunishEntity.size(); i++) {
				RewardOrPunishAgreementEntity dto = rewardOrPunishEntity.get(i);
				// 设置id
				dto.setId(UUIDUtils.getUUID());
				// 设置配载车次号
				dto.setVehicleAssemBillNo(baseEntity.getVehicleAssembleNo());
				// 设置每个类型的最高金额上限
				if (StringUtils.equals(dto.getRewardOrPunishType(),
						LoadConstants.REWARDPROT_TYPE_FINE)) {
					dto.setRewardOrPunishMaxMoney(rewardOrPunishDto
							.getFineMaxMoney());
				} else if (StringUtils.equals(dto.getRewardOrPunishType(),
						LoadConstants.REWARDPROT_TYPE_REWARD)) {
					dto.setRewardOrPunishMaxMoney(rewardOrPunishDto
							.getRewardMaxMoney());
				}
				// 创建人
				dto.setCreateUserName(baseEntity.getCreateUserName());
				// 创建人工号
				dto.setCreatorUserCode(baseEntity.getCreateUserCode());
				// 创建时间
				dto.setCreateTime(new Date());
				// 修改人
				dto.setModifyUserName(baseEntity.getModifyUserName());
				// 修改人工号
				dto.setModifyUserCode(baseEntity.getModifyUserCode());
				// 修改时间
				dto.setModifyTime(new Date());
				addRewardOrPunishAgreement.add(dto);
			}
		} else {
			throw new TfrBusinessException("请填写时效协议！");
		}
		vehicleAssembleBillDao
				.saveVehicleRewardProt(addRewardOrPunishAgreement);
	}

	/**
	 * 调用交接单模块service，获取本部门待配载的交接单
	 * 
	 * @author 045923-foss-shiwei
	 * @date 2012-11-9 下午4:41:07
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService#queryUnAssembledHandOverBillList(com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillConditionDto)
	 */
	@Override
	public List<QueryHandOverBillDto> queryUnAssembledHandOverBillList(
			QueryHandOverBillConditionDto queryHandOverBillConditionDto) {
		if (null == queryHandOverBillConditionDto) {
			return null;
		}
		List<QueryHandOverBillDto> handOverBillList = new ArrayList<QueryHandOverBillDto>();
		// 设置查询条件
		queryHandOverBillConditionDto.setCurrentDept(this
				.querySuperiorOrgCode());
		// 如果挂牌号为空,则调用发车计划中的挂牌号
		String trailerVehicleNo = queryHandOverBillConditionDto
				.getTrailerVehicleNo();
		String truckPlanDetailVehicleNo = queryHandOverBillConditionDto
				.getTruckPlanDetailVehicleNo();
		if (StringUtils.isEmpty(trailerVehicleNo)
				&& StringUtils.isNotEmpty(truckPlanDetailVehicleNo)) {
			// 重新设置挂牌号
			queryHandOverBillConditionDto
					.setTrailerVehicleNo(truckPlanDetailVehicleNo);
		}
		handOverBillList = handOverBillService
				.queryHandOverBillListForVehicleAssembleBill(queryHandOverBillConditionDto);
		// xiaobc update start.....2014-08-27
		// 将快递交接单归为包交接单一类
		if (handOverBillList.size() > 0) {
			for (QueryHandOverBillDto handOverBill : handOverBillList) {
				// 如果是非包交接单
				if (!StringUtils.equals(handOverBill.getBePackage(),
						FossConstants.YES)) {
					String productType = this
							.queryProductTypeByHandoverNo(handOverBill
									.getHandOverBillNo());// 查询交接单中第一条运单的运输性质
					// 如果是快递交接单
					if (StringUtils.equals(productType, "PACKAGE")
							|| StringUtils.equals(productType, "RCP")
							|| StringUtils.equals(productType, "EPEP")) {
						handOverBill.setBePackage(FossConstants.YES);// 将快递交接单与包交接单归于一类
					}
				}
			}
		}
		// xiaobc update end.....
		// 返回查询结果
		return handOverBillList;
	}

	/**
	 * 调用交接单模块service，获取本部门待配载的交接单
	 * 
	 * @author gongjp
	 * @date 2015-08-19 下午15:15:07
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService#queryUnAssembledHandOverBillList(com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillConditionDto)
	 */
	@Override
	public List<QueryHandOverBillDto> queryUnAssembledHandOverBillList1(
			QueryHandOverBillConditionDto queryHandOverBillConditionDto) {
		List<QueryHandOverBillDto> handOverBillList = new ArrayList<QueryHandOverBillDto>();
		// 设置查询条件
		queryHandOverBillConditionDto.setCurrentDept(this
				.querySuperiorOrgCode());
		// 如果挂牌号为空,则调用发车计划中的挂牌号
		String trailerVehicleNo = queryHandOverBillConditionDto
				.getTrailerVehicleNo();
		String truckPlanDetailVehicleNo = queryHandOverBillConditionDto
				.getTruckPlanDetailVehicleNo();
		if (StringUtils.isEmpty(trailerVehicleNo)
				&& StringUtils.isNotEmpty(truckPlanDetailVehicleNo)) {
			// 重新设置挂牌号
			queryHandOverBillConditionDto
					.setTrailerVehicleNo(truckPlanDetailVehicleNo);
		}
		handOverBillList = handOverBillService
				.queryHandOverBillListForVehicleAssembleBill(queryHandOverBillConditionDto);
		// xiaobc update start.....2014-08-27
		// 将快递交接单归为包交接单一类
		if (handOverBillList.size() > 0) {
			for (QueryHandOverBillDto handOverBill : handOverBillList) {
				// 如果是非包交接单
				if (!StringUtils.equals(handOverBill.getBePackage(),
						FossConstants.YES)) {
					String productType = this
							.queryProductTypeByHandoverNo(handOverBill
									.getHandOverBillNo());// 查询交接单中第一条运单的运输性质
					// 如果是快递交接单
					if (StringUtils.equals(productType, "PACKAGE")
							|| StringUtils.equals(productType, "RCP")
							|| StringUtils.equals(productType, "EPEP")) {
						handOverBill.setBePackage(FossConstants.YES);// 将快递交接单与包交接单归于一类
					} else {
						handOverBill.setBePackage(FossConstants.NO);
					}
				}
			}
		}
		// xiaobc update end.....
		// 返回查询结果
		return handOverBillList;
	}

	/**
	 * 查询配载单
	 * 
	 * @author 045923-foss-shiwei
	 * @date 2012-11-13 下午5:12:24
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService#queryVehicleAssembleBillList(com.deppon.foss.module.transfer.load.api.shared.dto.QueryVehicleAssembleBillConditionDto)
	 */
	@Override
	public List<QueryVehicleAssembleBillDto> queryVehicleAssembleBillList(
			QueryVehicleAssembleBillConditionDto conditionDto, int limit,
			int start) {
		String orgCode = this.querySuperiorOrgCode();
		conditionDto.setCurrentDeptCode(orgCode);
		// 返回查询结果
		return vehicleAssembleBillDao.queryVehicleAssembleBillList(
				conditionDto, limit, start);
	}

	/**
	 * 获取查询到的配载单的总条数
	 * 
	 * @author 045923-foss-shiwei
	 * @date 2012-11-14 下午4:49:07
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService#getVehicleAssembleBillCount(com.deppon.foss.module.transfer.load.api.shared.dto.QueryVehicleAssembleBillConditionDto)
	 */
	@Override
	public Long getVehicleAssembleBillCount(
			QueryVehicleAssembleBillConditionDto conditionDto) {
		String orgCode = this.querySuperiorOrgCode();
		conditionDto.setCurrentDeptCode(orgCode);
		// 返回查询结果
		return vehicleAssembleBillDao.getVehicleAssembleBillCount(conditionDto);
	}

	/**
	 * 根据配载车次号获取配载单基本信息
	 * 
	 * @author 045923-foss-shiwei
	 * @date 2012-11-15 下午4:36:38
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService#queryVehicleAssembleBillByNo(java.lang.String)
	 */
	@Override
	public List<VehicleAssembleBillEntity> queryVehicleAssembleBillByNo(
			String vehicleAssembleNo) {
		// 非空校验
		if (StringUtils.isEmpty(vehicleAssembleNo)) {
			throw new TfrBusinessException("配载车次号不能为空！");
		}
		// 根据配载车次号获取配载单信息
		List<VehicleAssembleBillEntity> list = vehicleAssembleBillDao
				.queryVehicleAssembleBillByNo(vehicleAssembleNo);
		/** 注释掉异常信息，获取配置单信息空的时候将会继续查询快递交接单信息 */
		// 若不存在，则抛异常，中断操作
		/*
		 * if(null == list || list.size() == 0){ throw new
		 * TfrBusinessException("配载单【" + vehicleAssembleNo + "】不存在或者已经被作废！"); }
		 */
		return list;
	}

	/**
	 * @description 根据快递交接单号 获取交接单信息 (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService#queryVehicleAssembleBillByNoFromWk(java.lang.String)
	 * @author 283250-foss-liuyi
	 * @update 2016年5月24日 下午5:16:07
	 * @version V1.0
	 */
	@Override
	public List<VehicleAssembleBillEntity> queryVehicleAssembleBillByNoFromWk(
			String vehicleAssembleNo) { // 非空校验
		if (StringUtils.isEmpty(vehicleAssembleNo)) {
			throw new TfrBusinessException("快递交接单号不能为空！");
		}
		// 根据配载车次号获取配载单信息
		List<VehicleAssembleBillEntity> list = vehicleAssembleBillDao
				.queryVehicleAssembleBillByNoFromWk(vehicleAssembleNo);
		return list;
	}

	/**
	 * 根据配载车次号获取其下交接单列表
	 * 
	 * @author 045923-foss-shiwei
	 * @date 2012-11-15 下午6:32:15
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService#queryHandOverBillListByVNo(java.lang.String)
	 */
	@Override
	public List<QueryHandOverBillDto> queryHandOverBillListByVNo(
			String vehicleAssembleNo) {
		// 获取交接单明细
		List<QueryHandOverBillDto> list = vehicleAssembleBillDao
				.queryHandOverBillListByVNo(vehicleAssembleNo);
		// 返回查询结果
		return list;
	}

	/**
	 * 获取打印运输合同需要的数据
	 * 
	 * @author ibm-zhangyixin
	 * @date 2012-11-16 上午10:21:37
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService#queryPrintCarriageContractData(java.lang.String)
	 */
	@Override
	public PrintCarriageContractDataDto queryPrintCarriageContractData(
			String vehicleAssembleNo) {
		// 返回查询结果
		return vehicleAssembleBillDao
				.queryPrintCarriageContractData(vehicleAssembleNo);
	}

	/**
	 * 根据配载车次号获取修改日志
	 * 
	 * @author 045923-foss-shiwei
	 * @date 2012-11-16 上午10:52:47
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService#queryVehicleAssembleBillOptLogByNo(java.lang.String)
	 */
	@Override
	public List<VehicleAssembleBillOptLogEntity> queryVehicleAssembleBillOptLogByNo(
			String vehicleAssembleNo, int limit, int start) {
		// 返回查询结果
		return vehicleAssembleBillDao.queryVehicleAssembleBillOptLogByNo(
				vehicleAssembleNo, limit, start);
	}

	/**
	 * 根据配载车次号获取修改日志的条数
	 * 
	 * @author 045923-foss-shiwei
	 * @date 2012-11-16 下午2:26:38
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService#queryVehicleAssembleBillOptLogCountByNo(java.lang.String)
	 */
	@Override
	public Long queryVehicleAssembleBillOptLogCountByNo(String vehicleAssembleNo) {
		// 返回查询结果
		return vehicleAssembleBillDao
				.queryVehicleAssembleBillOptLogCountByNo(vehicleAssembleNo);
	}

	/**
	 * 根据配置车次号找出当前配载单下所有的交接单号, 打印时用到
	 * 
	 * @author ibm-zhangyixin
	 * @date 2012-11-20 下午1:47:10
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService#queryHandOverBillNosByVehicleAssembleNo(java.lang.String)
	 */
	@Override
	public List<String> queryHandOverBillNosByVehicleAssembleNo(
			String vehicleAssembleNo) {
		// 根据配置车次号找出当前配载单下所有的交接单号, 打印时用到
		List<QueryHandOverBillDto> queryHandOverBillDtos = queryHandOverBillListByVNo(vehicleAssembleNo);
		List<String> handOverBillNos = new ArrayList<String>(
				queryHandOverBillDtos.size());
		// 循环添加
		for (QueryHandOverBillDto queryHandOverBillDto : queryHandOverBillDtos) {
			// 循环添加
			handOverBillNos.add(queryHandOverBillDto.getHandOverBillNo());
		}
		// 返回结果
		return handOverBillNos;
	}

	/**
	 * 私有方法，比较两个BigDecimal类型的数值是否相等，包括null
	 * 
	 * @author 045923-foss-shiwei
	 * @date 2013-1-11 下午5:13:22
	 */
	private boolean compareBigDecimal(BigDecimal a, BigDecimal b) {
		// 若都为null，则视为相等
		if (a == null && b == null) {
			return true;
			// 若只有一个等于null，则为不相等
		} else if (a == null || b == null) {
			return false;
		} else {
			// 其他情况则进行比较
			if (a.compareTo(b) == 0) {
				return true;
			}
			return false;
		}
	}

	/**
	 * 校验车牌号是否可用（判断的是封签）
	 * */
	private void validateVehicleNoCanBeUsed(String vehicleNo, String orgCode) {
		VehicleSealInfoDto seal = vehicleSealService
				.queryVehicleSealInfo(vehicleNo);
		if (seal != null
				&& !StringUtils.equals(seal.getSealdOrgCode(), orgCode)) {

			StringBuilder sealDestOrg = new StringBuilder();
			if (CollectionUtils.isNotEmpty(seal.getTruckTaskDetails())) {
				int i = 0;
				for (TruckTaskDetailEntity truckTaskDetail : seal
						.getTruckTaskDetails()) {
					i += 1;
					sealDestOrg.append(truckTaskDetail.getDestOrgName());
					if (i == seal.getTruckTaskDetails().size()) {
						sealDestOrg.append("。");
					} else {
						sealDestOrg.append("、");
					}
				}
			}

			throw new TfrBusinessException("车辆封签尚未校验，可校验该车辆封签的部门为："
					+ sealDestOrg.toString());

		}

		if (seal != null && StringUtils.equals(seal.getSealdOrgCode(), orgCode)) {

			boolean isdepart = false;
			StringBuilder sealDestOrg = new StringBuilder();
			if (CollectionUtils.isNotEmpty(seal.getTruckTaskDetails())) {
				int i = 0;
				for (TruckTaskDetailEntity truckTaskDetail : seal
						.getTruckTaskDetails()) {
					i += 1;
					sealDestOrg.append(truckTaskDetail.getDestOrgName());
					if (i == seal.getTruckTaskDetails().size()) {
						sealDestOrg.append("。");
					} else {
						sealDestOrg.append("、");
					}

					// 是否存在本部门未出发的
					if (StringUtils
							.isNotEmpty(truckTaskDetail.getOrigOrgCode())
							&& StringUtils.equals(
									truckTaskDetail.getOrigOrgCode(), orgCode)
							&& !StringUtils
									.equals(truckTaskDetail.getState(),
											TaskTruckConstant.TASK_TRUCK_STATE_UNDEPART)) {
						isdepart = true;
					}
				}

				if (isdepart) {
					throw new TfrBusinessException("车辆封签尚未校验，可校验该车辆封签的部门为："
							+ sealDestOrg.toString());
				}
			}

		}
	}

	/**
	 * 修改配载单
	 * 
	 * @author 045923-foss-shiwei
	 * @param updateVehicleAssembleBillDto前台传入的dto对象
	 * @return boolean处理成功或者失败
	 * @date 2012-11-20 上午8:42:11
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService#updateVehicleAssembleBill(com.deppon.foss.module.transfer.load.api.shared.dto.UpdateVehicleAssembleBillDto)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional
	public boolean updateVehicleAssembleBill(
			UpdateVehicleAssembleBillDto updateVehicleAssembleBillDto,
			RewardOrPunishAgreementDto rewardDto) {
		/**
		 * 只能修改符合以下条件的配载单 1、“配载部门”为本部门； 2、状态为“未发车”或者“已出发； 3、未做出发付款确认
		 */
		/**
		 * 修改配载单后，做以下操作： 1、update基本信息； 2、insert新增的交接单； 3、将新增的交接单状态修改为“已配载”；
		 * 4、delete删除的交接单； 5、将删除的交接单状态修改为“已交接”； 6、如果配载单已生成了任务车辆明细：
		 * a）调用任务车辆接口，传入新增的交接单号； b）调用任务车辆接口，传入删除的交接单号； 7、如果修改了车牌号：
		 * a）如果修改前车牌号是外请车，修改后车牌号是公司车：调用结算接口，红冲应付单； 调用外请车约车接口，传入修改前的车牌号；
		 * b）如果修改前车牌号是公司车，修改后车牌号是外请车：调用结算接口，生成应付单；
		 * c）如果修改前车牌号是外请车，修改后车牌号是外请车：先调用结算红冲接口，传入修改前的车牌号，
		 * 再调用结算生成应付单接口，传入修改后的车牌号； 调用外请车约车接口，传入修改前的车牌号； d）调用交接单接口，更新其下所有交接单的车牌号；
		 * e）如果配载单已经生成了任务车辆明细，则需调用任务车辆接口 8、如果没有修改车牌号，修改了配载单财务信息，需调用结算接口；
		 * 9、生成配载单修改日志； 10、修改奖罚协议内容，会删除原有的奖罚协议在新增修改后的信息。并记录日志
		 */
		// 获取更新后的基本信息
		VehicleAssembleBillEntity baseEntity = updateVehicleAssembleBillDto
				.getBaseEntity();
		String vehicleAssembleNo = baseEntity.getVehicleAssembleNo();

		// 2013-07-09 11:00 签收后不能整车配载zyx
		List<VehicleAssembleBillDetailEntity> handoverBillDetailList = updateVehicleAssembleBillDto
				.getHandoverBillList();
		// 如果为整车配载
		if (StringUtils.equals(baseEntity.getAssembleType(),
				LoadConstants.VEHICLE_ASSEMBLE_TYPE_CAR_LOAD)) {
			for (VehicleAssembleBillDetailEntity vehicleAssembleBillDetail : handoverBillDetailList) {
				String handOverBillNo = vehicleAssembleBillDetail
						.getHandOverBillNo();
				List<HandOverBillDetailEntity> handOverBillDetails = handOverBillService
						.queryHandOverBillDetailByNo(handOverBillNo);
				for (HandOverBillDetailEntity handOverBillDetail : handOverBillDetails) {
					String waybillNo = handOverBillDetail.getWaybillNo();
					List<SerialNoLoadExceptionDto> serialNoLoadExceptions = handOverBillService
							.queryWaybillDetailByNos(handOverBillNo, waybillNo);
					for (SerialNoLoadExceptionDto serialNoLoadException : serialNoLoadExceptions) {
						String serialNo = serialNoLoadException.getSerialNo();
						// 断货物是否已签收的接口，参数（运单号、流水号）
						String checkSerialNoIsSign = signDetailService
								.querySerialNoIsSign(waybillNo, serialNo);
						// 已签收出库
						if (StringUtils.equals(FossConstants.YES,
								checkSerialNoIsSign)) {
							throw new TfrBusinessException("运单:" + waybillNo
									+ "已签收, 不能整车配载!");
						}
					}
				}
			}
		}

		if (StringUtils.equals(baseEntity.getVehicleOwnerShip(),
				ComnConst.ASSETS_OWNERSHIP_TYPE_LEASED)) {
			String holdingState = handOverBillService
					.queryHoldingState(vehicleAssembleNo);
			if (holdingState.equals("Y")) {
				throw new TfrBusinessException("配载单生成的临时租车已付款、已预提或在预提中，不允许修改！");
			}
		}
		boolean isExpress = vehicleAssembleNo.startsWith("KD");
		if (!isExpress
				&& StringUtils.equals(baseEntity.getBeInLTLCar(),
						FossConstants.YES)) {
			throw new TfrBusinessException("非快递配载单不允许勾选是否与零担合车！");
		}
		// 获取修改前的配载单实体
		VehicleAssembleBillEntity oldEntity = this
				.queryVehicleAssembleBillByNo(baseEntity.getVehicleAssembleNo())
				.get(0);
		// 车牌号是否被修改，如果修改，则修改前未删除的交接单的车牌号，司机信息
		boolean isVehicleNoModified = StringUtils.equals(
				oldEntity.getVehicleNo(), baseEntity.getVehicleNo()) ? false
				: true;

		/*
		 * 结算信息是否已做出发付款确认
		 */
		// 付款方式
		boolean isStlInfoModified = !StringUtils.equals(
				baseEntity.getPaymentType(), oldEntity.getPaymentType())
				// 预付运费总额
				|| !compareBigDecimal(baseEntity.getPrePaidFeeTotal(),
						oldEntity.getPrePaidFeeTotal())
				// 到付运费总额
				|| !compareBigDecimal(baseEntity.getArriveFeeTotal(),
						oldEntity.getArriveFeeTotal())
				// 是否押回单
				|| !StringUtils.equals(baseEntity.getBeReturnReceipt(),
						oldEntity.getBeReturnReceipt()) ? true : false;
		
		// 封装灰度实体，类型是配载单
		GrayParameterDto parDto = new GrayParameterDto();
		parDto.setSourceBillType(CUBCGrayUtil.SBType.PZ.getName());
		parDto.setSourceBillNos(new String[] { vehicleAssembleNo });
		VestResponse vestResponse = cubcUtil.getUcbcGrayData(parDto, new Throwable());

		boolean isDepartureWriteOff = false;
		if (CUBCGrayContants.SYSTEM_CODE_FOSS.equals(vestResponse.getVestBatchResult().get(0).getVestSystemCode())) {
			// 配载单是否已做出发付款确认
			isDepartureWriteOff = billPayableService.queryBillPayableIsWriteOff(vehicleAssembleNo, null);
		} else {
				Map<String, String> paramMap = new HashMap<String, String>();
				// 配载单号
				paramMap.put("sourceBillNo", vehicleAssembleNo);
				paramMap.put("isAdjust", null);

				CubcValidationSourceBillNoResponse cubcValidationVerificationResponse = fossToCubcService
						.queryBillPayableIsWriteOff(paramMap);
				if (null != cubcValidationVerificationResponse) {
					if (!cubcValidationVerificationResponse.isSuccess()) {
						throw new TfrBusinessException("根据来源单号调用cubc查询应付单是否已经核销失败:cubc"
								+ cubcValidationVerificationResponse.getExceptionMsg() + "！");
					} else if (cubcValidationVerificationResponse.isSuccess()
							&& cubcValidationVerificationResponse.isAudited()) {
						isDepartureWriteOff = true;
					}
				} else {
					throw new TfrBusinessException("根据来源单号调用cubc查询应付单是否已经核销失败！");
				}
		}
		// 获取修改前配载单下的交接单list
		List<QueryHandOverBillDto> oldBillList = null;
		Map<String, QueryHandOverBillDto> oldBillMap = null;
		// 挂牌号
		String trailerVehicleNo = null;

		// 是否修改车牌号
		if (isVehicleNoModified) {
			// 如果配载单已出发，则无法修改车牌号
			if (oldEntity.getState() != LoadConstants.VEHICLEASSEMBLEBILL_STATE_NOT_DEPART) {
				// 业务异常，中断
				throw new TfrBusinessException("该配载单已经发车，无法修改车牌号！");
			}
			// 如果已出发付款确认，无法修改车牌号
			if (isDepartureWriteOff) {
				// 业务异常，中断
				throw new TfrBusinessException("该配载单已做出发付款确认，无法修改车牌号！");
			}
			// 获取修改前配载单中的车牌号
			oldBillList = this.queryHandOverBillListByVNo(vehicleAssembleNo);
			oldBillMap = new HashMap<String, QueryHandOverBillDto>();
			for (QueryHandOverBillDto dto : oldBillList) {
				oldBillMap.put(dto.getHandOverBillNo(), dto);
			}

			/* 如果不是整车配载单，则更新发车计划明细和线路名称 */
			// 线路信息，从发车计划中获取
			if (StringUtils.equals(baseEntity.getAssembleType(),
					LoadConstants.VEHICLE_ASSEMBLE_TYPE_OWNER_LINE)) {
				// 去掉发车日期的时分秒
				Date leaveTime = changeHoursForDate(baseEntity.getLeaveTime());

				// 200968 20161223
				TruckDepartPlanDetailDto planDetail = null;
				VehicleAssociationDto vehicleAssociationDto = queryVehicleInfoByVehicleNo(baseEntity
						.getVehicleNo());
				if (vehicleAssociationDto != null) {
					if (StringUtils.equals(
							vehicleAssociationDto.getVehicleType(),
							"vehicletype_trailer")
							&& !vehicleAssociationDto.getVehicleMotorcadeName()
									.equals("外请车")) {
						planDetail = truckDepartPlanDetailService
								.queryDepartPlanDetailByTrailerVehicleNo(
										baseEntity.getOrigOrgCode(),
										baseEntity.getDestOrgCode(),
										baseEntity.getVehicleNo(),
										baseEntity.getLeaveTime());
					} else {
						planDetail = truckDepartPlanDetailService
								.queryLatestTruckDepartPlanDetail(
										oldEntity.getOrigOrgCode(),
										oldEntity.getDestOrgCode(),
										baseEntity.getVehicleNo(), leaveTime);
					}

					if (planDetail != null) {
						// 发车计划明细id
						baseEntity.setTruckDepartPlanDetailId(planDetail
								.getId());
						// 线路名称
						baseEntity.setLine(planDetail.getLineName());
						// 挂牌号
						trailerVehicleNo = planDetail.getTrailerVehicleNo();

					} else {
						throw new TfrBusinessException(
								"未读取到发车计划信息，请确认该车辆是否已出发！");
					}
				}
			}
			// 如果计算总运费，且车辆为外请车，则总运费=首款+尾款
			if (StringUtils.equals(baseEntity.getVehicleOwnerShip(),
					ComnConst.ASSETS_OWNERSHIP_TYPE_LEASED)
					&& (StringUtils.equals(baseEntity.getBeMidWayLoad(),
							FossConstants.NO) || (StringUtils.equals(
							baseEntity.getBeMidWayLoad(), FossConstants.YES) && StringUtils
							.equals(baseEntity.getBeFinallyArrive(),
									FossConstants.YES)))
					&& (baseEntity.getFeeTotal().compareTo(
							baseEntity.getPrePaidFeeTotal().add(
									baseEntity.getArriveFeeTotal())) != 0)
					&& !StringUtils.equals(baseEntity.getPaymentType(), "CT")) {
				throw new TfrBusinessException(
						"“总运费”必须等于“预付运费总额”与“到付运费总额”之和，请重新输入运费！");
			}
		} else {
			// 发车计划id
			String truckDepartPlanDetailId = oldEntity
					.getTruckDepartPlanDetailId();
			if (StringUtils.isNotEmpty(truckDepartPlanDetailId)) {
				// 通过id查询发车计划
				TruckDepartPlanDetailDto planDetail = truckDepartPlanDetailService
						.queryTruckDepartPlanDetailById(
								truckDepartPlanDetailId, FossConstants.YES);
				if (planDetail != null) {
					// 挂牌号
					trailerVehicleNo = planDetail.getTrailerVehicleNo();
				}
			}
		}

		if (StringUtils.equals(baseEntity.getVehicleOwnerShip(),
				ComnConst.ASSETS_OWNERSHIP_TYPE_LEASED)
				&& StringUtils.equals(baseEntity.getAssembleType(),
						LoadConstants.VEHICLE_ASSEMBLE_TYPE_CAR_LOAD)
				&& (StringUtils.equals(baseEntity.getBeMidWayLoad(),
						FossConstants.NO) || (StringUtils.equals(
						baseEntity.getBeMidWayLoad(), FossConstants.YES) && StringUtils
						.equals(baseEntity.getBeFinallyArrive(),
								FossConstants.YES)))
				&& baseEntity.getFeeTotal().compareTo(BigDecimal.ZERO) == 0) {
			throw new TfrBusinessException("车次“总运费”不能为0，请重新输入车牌号读取运费！");
		}
		if (StringUtils.equals(baseEntity.getVehicleOwnerShip(),
				ComnConst.ASSETS_OWNERSHIP_TYPE_LEASED)
				&& (StringUtils.equals(baseEntity.getBeMidWayLoad(),
						FossConstants.NO) || (StringUtils.equals(
						baseEntity.getBeMidWayLoad(), FossConstants.YES) && StringUtils
						.equals(baseEntity.getBeFinallyArrive(),
								FossConstants.YES)))
				&& baseEntity.getFeeTotal() == null) {
			throw new TfrBusinessException("车次“总运费”不能为空，请重新输入车牌号读取运费！");
		}
		//duh-276198-20150906-判断专线首款是否小于等于总车费的1/3
		
		if(StringUtils.equals(baseEntity.getVehicleOwnerShip(), ComnConst.ASSETS_OWNERSHIP_TYPE_LEASED)
				&& ( (StringUtils.equals(baseEntity.getBeMidWayLoad(), FossConstants.NO) 
						&& StringUtils.equals(baseEntity.getBeInLTLCar(),FossConstants.NO) )
						|| (StringUtils.equals(baseEntity.getBeMidWayLoad(), FossConstants.YES)
								&& StringUtils.equals(baseEntity.getBeFinallyArrive(), FossConstants.YES)))
				&&baseEntity.getAssembleType().equals("OWNER_LINE")
				&& !StringUtils.equals(baseEntity.getPaymentType(), "CT")){
			//计算专线预付车费最大值，最大值为总车费的1/3
			double preMaxfee = Math.floor(baseEntity.getFeeTotal().doubleValue()*1/LoadConstants.SONAR_NUMBER_3);
			BigDecimal bigPreMaxfee = new BigDecimal(preMaxfee);
			if (baseEntity.getPrePaidFeeTotal().compareTo(bigPreMaxfee) == 1) {
				throw new TfrBusinessException("专线首款不能大于总车费的1/3，请重新输入预付运费！");
			}
			// throw new
			// TfrBusinessException("专线首款不能大于总车费的1/3，请重新输入预付运费！"+bigPreMaxfee+"!!!"+preMaxfee);

		}
		
		//duh-276198-20150906-判断整车首款是否小于等于总车费的2/3
		
		if(StringUtils.equals(baseEntity.getVehicleOwnerShip(), ComnConst.ASSETS_OWNERSHIP_TYPE_LEASED)
				&& ( (StringUtils.equals(baseEntity.getBeMidWayLoad(), FossConstants.NO) 
						&& StringUtils.equals(baseEntity.getBeInLTLCar(),FossConstants.NO) )
						|| (StringUtils.equals(baseEntity.getBeMidWayLoad(), FossConstants.YES)
								&& StringUtils.equals(baseEntity.getBeFinallyArrive(), FossConstants.YES)))
				&&baseEntity.getAssembleType().equals("CAR_LOAD")
				&& !StringUtils.equals(baseEntity.getPaymentType(), "CT")){
			//计算整车预付车费最大值，最大值为总车费的2/3
			double preMaxfee = Math.floor(baseEntity.getFeeTotal().doubleValue()*2/LoadConstants.SONAR_NUMBER_3);
			BigDecimal bigPreMaxfee = new BigDecimal(preMaxfee);
			if (baseEntity.getPrePaidFeeTotal().compareTo(bigPreMaxfee) == 1) {
				throw new TfrBusinessException("专线首款不能大于总车费的2/3，请重新输入预付运费！");
			}
		}
		// BUG-57945
		if (StringUtils.equals(baseEntity.getVehicleOwnerShip(),
				ComnConst.ASSETS_OWNERSHIP_TYPE_LEASED)
				&& StringUtils.equals(baseEntity.getBeMidWayLoad(),
						FossConstants.YES)
				&& StringUtils.equals(baseEntity.getBeFinallyArrive(),
						FossConstants.NO)
				&& baseEntity.getFeeTotal() != null
				&& baseEntity.getFeeTotal().compareTo(BigDecimal.ZERO) != 0) {
			throw new TfrBusinessException("车次在途卸且非最终到达没有运费，请重新输入车牌号！");
		}
		// 如果已做出发付款确认或者已发车，则无法修改结算信息
		if (isStlInfoModified) {
			// 如果配载单已出发，则无法修改车牌号
			if (oldEntity.getState() != LoadConstants.VEHICLEASSEMBLEBILL_STATE_NOT_DEPART) {
				throw new TfrBusinessException("该配载单已经发车，无法修改结算信息！");
			}
			// 如果已出发付款确认，无法修改车牌号
			if (isDepartureWriteOff) {
				throw new TfrBusinessException("该配载单已做出发付款确认，无法修改结算信息！");
			}
		}
		if (StringUtils.equals(baseEntity.getVehicleOwnerShip(),
				ComnConst.ASSETS_OWNERSHIP_TYPE_LEASED)) {
			if (baseEntity.getDriverOfWeight() == null
					|| baseEntity.getDriverOfVolumn() == null) {
				throw new TfrBusinessException("外请车请填写司机自带货量！");
			} else {
				baseEntity.setDriverOfWeight(baseEntity.getDriverOfWeight());
				baseEntity.setDriverOfVolumn(baseEntity.getDriverOfVolumn());
				baseEntity.setApplicationRatedClearance(baseEntity
						.getApplicationRatedClearance());
				baseEntity.setApplicationRatedLoad(baseEntity
						.getApplicationRatedLoad());
				baseEntity.setBeCarSmallUsed(baseEntity.getBeCarSmallUsed());
			}
		} else {
			baseEntity.setDriverOfWeight(null);
			baseEntity.setDriverOfVolumn(null);
			baseEntity.setApplicationRatedClearance(null);
			baseEntity.setApplicationRatedLoad(null);
			baseEntity.setBeCarSmallUsed(null);
		}
		// 出发部门code、name
		baseEntity.setOrigOrgCode(oldEntity.getOrigOrgCode());
		baseEntity.setOrigOrgName(oldEntity.getOrigOrgName());
		if (isVehicleNoModified
				&& StringUtils.isNotEmpty(baseEntity.getVehicleNo())) {
			// 校验车牌号是否可用 即校验封签
			validateVehicleNoCanBeUsed(baseEntity.getVehicleNo(),
					baseEntity.getOrigOrgCode());
		}
		// 获取当前用户、时间等
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		// 最后修改时间
		baseEntity.setModifyDate(new Date());
		// 最后修改人姓名、工号
		baseEntity.setModifyUserCode(currentInfo.getEmpCode());
		baseEntity.setModifyUserName(currentInfo.getEmpName());
		// 设置创建人工号，姓名，时间，id
		baseEntity.setCreateUserCode(oldEntity.getCreateUserCode());
		baseEntity.setCreateUserName(oldEntity.getCreateUserName());
		// 设置在途装卸部门
		if (StringUtils.equals(baseEntity.getBeMidWayOnlyLoad(),
				FossConstants.NO)) {
			baseEntity.setOnTheWayDestOrgCode(null);
			baseEntity.setOnTheWayDestOrgName(null);
		} else {
			if (StringUtils.isEmpty(baseEntity.getOnTheWayDestOrgCode())) {
				throw new TfrBusinessException("选择了中途只装不卸，必须填写中途到达部门！");
			}
			if (StringUtils.equals(baseEntity.getOnTheWayDestOrgCode(),
					baseEntity.getOrigOrgCode())
					|| StringUtils.equals(baseEntity.getOnTheWayDestOrgCode(),
							baseEntity.getDestOrgCode())) {
				throw new TfrBusinessException("中途到达部门不能选择当前部门或最终到达部门！");
			}
			OrgAdministrativeInfoEntity onTheWayDestOrgEntity = loadService
					.querySuperiorOrgByOrgCode(baseEntity
							.getOnTheWayDestOrgCode());
			baseEntity.setOnTheWayDestOrgName(onTheWayDestOrgEntity.getName());
		}
		// 获取新增的交接单列表
		List<VehicleAssembleBillDetailEntity> addedHandOverBillList = null;
		if (updateVehicleAssembleBillDto.getAddedHandOverBillList() != null
				&& updateVehicleAssembleBillDto.getAddedHandOverBillList()
						.size() != 0) {
			addedHandOverBillList = updateVehicleAssembleBillDto
					.getAddedHandOverBillList();
		}

		// 获取删除的交接单
		DeleteHandOverBillFromVehicleAssembleBillDto deletedHandOverBillDto = null;
		List<VehicleAssembleBillDetailEntity> deletedHandOverBillList = updateVehicleAssembleBillDto
				.getDeletedHandOverBillList();
		if (deletedHandOverBillList != null
				&& deletedHandOverBillList.size() != 0) {
			deletedHandOverBillDto = new DeleteHandOverBillFromVehicleAssembleBillDto();
			// 为dto设置配载单ID
			deletedHandOverBillDto.setVehicleAssembleBillId(baseEntity.getId());
			// 收集交接单号
			List<String> handOverBillNoList = new ArrayList<String>();
			for (int i = 0; i < deletedHandOverBillList.size(); i++) {
				String no = deletedHandOverBillList.get(i).getHandOverBillNo();
				handOverBillNoList.add(no);
				// 获取修改前的本次未删除的交接单
				if (isVehicleNoModified) {
					oldBillMap.remove(no);
				}
			}
			// 为dto设置交接单号List
			deletedHandOverBillDto.setHandOverBillList(handOverBillNoList);
		}

		// 生成日志
		List<VehicleAssembleBillOptLogEntity> optLogList = new ArrayList<VehicleAssembleBillOptLogEntity>();
		// 获取基本信息修改日志
		if (StringUtils.isNotBlank(updateVehicleAssembleBillDto
				.getBaseInfoOptLog())) {
			VehicleAssembleBillOptLogEntity baseInfoLog = createLogForBaseInfo(
					baseEntity,
					updateVehicleAssembleBillDto.getBaseInfoOptLog());
			optLogList.add(baseInfoLog);
		}
		// 获取删除交接单修改日志
		if (deletedHandOverBillList != null
				&& deletedHandOverBillList.size() != 0) {
			VehicleAssembleBillOptLogEntity deletedBillLog = createLogForHandOverBill(
					oldEntity, baseEntity, deletedHandOverBillList, 1);// 删除交接单日志传入1
			optLogList.add(deletedBillLog);
		}
		// 获取添加交接单修改日志
		if (addedHandOverBillList != null && addedHandOverBillList.size() != 0) {
			VehicleAssembleBillOptLogEntity addedBillLog = createLogForHandOverBill(
					oldEntity, baseEntity, addedHandOverBillList, 0);// 添加交接单日志传入0
			optLogList.add(addedBillLog);
		}
		/** 奖罚协议 */
		if (StringUtils.equals(baseEntity.getBeTimeLiness(), FossConstants.YES)) {
			// 校验奖罚协议，是否能够新增奖励类型或者是否在不奖时间段内
			checkRewardInfo(rewardDto.getRewardOrPunishAgreementEntity(),
					baseEntity);
			// 创建奖罚协议修改日志
			VehicleAssembleBillOptLogEntity addedRewardLog = createLogForRewardOrPunish(
					oldEntity, baseEntity,
					rewardDto.getRewardOrPunishAgreementEntity());
			// 通过协议中的修改内容判断是否修改了内容
			if (StringUtils.isNotBlank(addedRewardLog.getOptContent())) {
				// 添加日志
				optLogList.add(addedRewardLog);
				// 如果奖罚从 存在到修改后存在 则删除原来的奖罚协议,否在直接新增
				if (StringUtils.equals(oldEntity.getBeTimeLiness(),
						FossConstants.YES)) {
					// 删除原有奖罚协议
					vehicleAssembleBillDao
							.deletReWardOrPunishAgreement(oldEntity
									.getVehicleAssembleNo());
				}
				// 新增奖罚协议
				addRewardProt(rewardDto, baseEntity);
			}
		} else if (StringUtils.equals(baseEntity.getBeTimeLiness(),
				FossConstants.NO)
				&& StringUtils.equals(oldEntity.getBeTimeLiness(),
						FossConstants.YES)) {
			// 删除奖罚协议
			vehicleAssembleBillDao.deletReWardOrPunishAgreement(oldEntity
					.getVehicleAssembleNo());
		}
		// 调用结算接口
		this.callStlInterfaceWhenModify(oldEntity, baseEntity);
		// 总运费、预付总运费、到付总运费
		baseEntity.setFeeTotal(baseEntity.getFeeTotal());
		// 预付运费总额
		baseEntity.setPrePaidFeeTotal(baseEntity.getPrePaidFeeTotal());
		// 到付运费总额
		baseEntity.setArriveFeeTotal(baseEntity.getArriveFeeTotal());
		// 调用dao
		this.vehicleAssembleBillDao.updateVehicleAssembleBill(baseEntity,
				addedHandOverBillList, deletedHandOverBillDto, optLogList);

		/** 如果是专线 、公司车、提前装车（即有挂牌号交接单） 则调用修改挂牌号交接单车辆任务方法 **/
		// 条件是 专线 公司车 添加了交接单
		if (StringUtils.equals(baseEntity.getAssembleType(),
				LoadConstants.VEHICLE_ASSEMBLE_TYPE_OWNER_LINE)
				&& StringUtils.equals(baseEntity.getVehicleOwnerShip(),
						ComnConst.ASSETS_OWNERSHIP_TYPE_COMPANY)
				&& CollectionUtils.isNotEmpty(addedHandOverBillList)
				&& addedHandOverBillList.size() > 0) {
			// 获取新增的交接单
			List<String> trailerHandOverbills = new ArrayList<String>();
			for (VehicleAssembleBillDetailEntity temp : addedHandOverBillList) {
				// 判断交接单的车牌号 以区分挂牌号交接单
				if (!StringUtils.equals(baseEntity.getVehicleNo(),
						temp.getVehicleNo())) {
					// 如果跟发车计划中的挂牌号相等则添加到 挂牌号list中否在 提示 配载单中交接单车牌号于配载单车牌号不一致
					// TODO
					if (StringUtils.isNotEmpty(trailerVehicleNo)
							&& StringUtils.equals(temp.getVehicleNo(),
									trailerVehicleNo)) {
						trailerHandOverbills.add(temp.getHandOverBillNo());
					} else {
						throw new TfrBusinessException("交接单车牌号与配载单车牌号不一致！");
					}
				}
			}
			// 如果挂牌号交接单不为空则调用修改挂牌号车辆任务接口
			if (CollectionUtils.isNotEmpty(trailerHandOverbills)
					&& trailerHandOverbills.size() > 0) {
				truckTaskService.modifyTruckTaskByTrailerHandOverBill(
						baseEntity.getVehicleNo(), trailerHandOverbills);
			}
		}

		// 200968 2016-01-12
		if (isVehicleNoModified
				&& StringUtils.equals(baseEntity.getAssembleType(),
						LoadConstants.VEHICLE_ASSEMBLE_TYPE_OWNER_LINE)
				&& StringUtils.equals(baseEntity.getVehicleOwnerShip(),
						ComnConst.ASSETS_OWNERSHIP_TYPE_COMPANY)) {
			List<String> trailerHandOverbills = new ArrayList<String>();
			// 获取配载单下的所有交接单
			if (CollectionUtils.isNotEmpty(oldBillList)) {
				for (QueryHandOverBillDto queryHandOverBillDto : oldBillList) {
					// 判断交接单的车牌号 以区分挂牌号交接单
					if (!StringUtils.equals(baseEntity.getVehicleNo(),
							queryHandOverBillDto.getVehicleNo())) {
						// 如果跟发车计划中的挂牌号相等则添加到 挂牌号list中否在 提示 配载单中交接单车牌号于配载单车牌号不一致
						// TODO
						if (StringUtils.isNotEmpty(trailerVehicleNo)
								&& StringUtils.equals(
										queryHandOverBillDto.getVehicleNo(),
										trailerVehicleNo)) {
							trailerHandOverbills.add(queryHandOverBillDto
									.getHandOverBillNo());
						} else {
							throw new TfrBusinessException("交接单车牌号与配载单车牌号不一致！");
						}
					}
				}
				// 如果挂牌号交接单不为空则调用修改挂牌号车辆任务接口
				if (CollectionUtils.isNotEmpty(trailerHandOverbills)
						&& trailerHandOverbills.size() > 0) {
					truckTaskService.modifyTruckTaskByTrailerHandOverBill(
							baseEntity.getVehicleNo(), trailerHandOverbills);
				}
			}
		}

		/*
		 * 构造修改UpdateVehicleNoAndDriverInfoFromVehicleAssembleBillDto对象，
		 * 预备修改交接单车牌及司机信息
		 */
		if (oldBillMap != null && oldBillMap.size() != 0) {
			UpdateVehicleNoAndDriverInfoFromVehicleAssembleBillDto updateHandOverBillDto = new UpdateVehicleNoAndDriverInfoFromVehicleAssembleBillDto();
			List<String> handOverBillNoList = new ArrayList<String>();
			Set entrySet = oldBillMap.entrySet();
			Iterator iterator = entrySet.iterator();
			// 遍历map，
			while (iterator.hasNext()) {
				Map.Entry<String, QueryHandOverBillDto> entry = (Map.Entry<String, QueryHandOverBillDto>) iterator
						.next();// key
				String billNo = entry.getKey();
				handOverBillNoList.add(billNo);
			}
			updateHandOverBillDto.setVehicleAssembleNo(vehicleAssembleNo);
			updateHandOverBillDto.setHandOverBillNoList(handOverBillNoList);
			updateHandOverBillDto.setVehicleNo(baseEntity.getVehicleNo());
			updateHandOverBillDto.setDriverCode(baseEntity.getDriverCode());
			updateHandOverBillDto.setDriverName(baseEntity.getDriverName());
			updateHandOverBillDto.setDriverTel(baseEntity
					.getDriverMobilePhone());
			// 调用交接单service，更新交接单车牌号及司机信息
			handOverBillService
					.updateVehicleNoAndDriverInfoFromVehicleAssembleBill(updateHandOverBillDto);
		}
		// 调用任务车辆接口
		if (isVehicleNoModified) {
			// 调用任务车辆接口
			truckTaskService.assembleBillUpdateVehicleNo(vehicleAssembleNo);
			truckTaskService.createTruckTaskByAssembleBill(vehicleAssembleNo);
		}
		/**
		 * 在途只装不卸的配载单修改了车牌号或者中途达到部门 需要删除原有的 出发部门 到 中途装车部门的虚拟车辆任务 （如 A-C
		 * 需要在中途B装车到C 那么A-B需要一个没有单据的车辆任务），然后新增虚拟车辆任务
		 */
		String oldisOnlyLoad = oldEntity.getBeMidWayOnlyLoad();
		String nowisOnlyLoad = baseEntity.getBeMidWayOnlyLoad();
		// 如果修改前不是在途只装不卸 ， 修改后是在途只装不卸
		if (!StringUtils.equals(oldisOnlyLoad, FossConstants.YES)
				&& StringUtils.equals(nowisOnlyLoad, FossConstants.YES)) {
			// 新增虚拟车辆任务即没有单据的车连任务
			truckTaskService.createTruckTaskForMidLoad(vehicleAssembleNo,
					baseEntity.getOnTheWayDestOrgCode(),
					baseEntity.getOnTheWayDestOrgName());
		}
		// 如果修改前是在途只装不卸 ，修改后不是在途装车不卸
		if (StringUtils.equals(oldisOnlyLoad, FossConstants.YES)
				&& !StringUtils.equals(nowisOnlyLoad, FossConstants.YES)) {
			// 删除车辆任务
			truckTaskService.deleteTruckTaskForMidLoad(
					oldEntity.getOrigOrgCode(),
					oldEntity.getOnTheWayDestOrgCode(),
					oldEntity.getVehicleNo());
		}
		// 如果修改前是在途只装不卸 ，修改后还是在途只装不卸
		if (StringUtils.equals(oldisOnlyLoad, FossConstants.YES)
				&& StringUtils.equals(nowisOnlyLoad, FossConstants.YES)) {
			if (isVehicleNoModified
					|| !StringUtils.equals(oldEntity.getOnTheWayDestOrgCode(),
							baseEntity.getOnTheWayDestOrgCode())) {
				// 删除原有的到达中途部门的虚拟车辆任务
				truckTaskService.deleteTruckTaskForMidLoad(
						oldEntity.getOrigOrgCode(),
						oldEntity.getOnTheWayDestOrgCode(),
						oldEntity.getVehicleNo());
				// 新增车辆任务
				truckTaskService.createTruckTaskForMidLoad(vehicleAssembleNo,
						baseEntity.getOnTheWayDestOrgCode(),
						baseEntity.getOnTheWayDestOrgName());
			}
		}
		// 校验同一个车连任务下只能有一个运费的配载单
		if (baseEntity.getFeeTotal() != null
				&& baseEntity.getFeeTotal().compareTo(BigDecimal.ZERO) > 0) {
			validInJoinCar(baseEntity.getVehicleAssembleNo());
		}
		return true;
	}

	/**
	 * 私有方法，修改配载单结算信息时调用结算接口
	 * 
	 * @author 045923-foss-shiwei
	 * @date 2012-12-4 上午9:51:26
	 */
	private void callStlInterfaceWhenModify(
			VehicleAssembleBillEntity oldEntity,
			VehicleAssembleBillEntity newEntity) {
		/**
		 * 修改配载单时，分以下几种情况 1、修改前为公司车，修改后为外请车，传入新的配载单结算信息调用结算add方法
		 * 2、修改前为外请车、修改后为公司车，传入旧的配载单结算信息调用结算disable方法
		 * 3、修改前为外请车、修改后为外请车，传入旧的结算信息和旧车牌，调用结算disable方法， 传入新的结算信息和新车牌，调用结算add方法
		 * 4、修改前后车牌无变化，均为同一外请车，
		 * 则当“预付运费总额”、“到付运费总额”、“付款方式”、“是否押回单”任一字段发生变更，调用结算modify方法
		 */
		// 如果车牌号没变化，有两种情况，同一辆公司车，同一辆外请车
		if (StringUtils.equals(oldEntity.getVehicleNo(),
				newEntity.getVehicleNo())) {
			if (StringUtils.equals(oldEntity.getVehicleOwnerShip(),
					ComnConst.ASSETS_OWNERSHIP_TYPE_COMPANY)) {
				return;
			} else if (StringUtils.equals(oldEntity.getVehicleOwnerShip(),
					ComnConst.ASSETS_OWNERSHIP_TYPE_LEASED)) {
				/*
				 * 结算相关信息发生改变，则调用结算接口
				 */
				// 预付运费总额
				if (oldEntity.getPrePaidFeeTotal().compareTo(
						newEntity.getPrePaidFeeTotal()) != 0
						// 到付运费总额
						|| oldEntity.getArriveFeeTotal().compareTo(
								newEntity.getArriveFeeTotal()) != 0
						// 总运费
						|| oldEntity.getFeeTotal().compareTo(
								newEntity.getFeeTotal()) != 0
						// 付款方式
						|| !StringUtils.equals(oldEntity.getPaymentType(),
								newEntity.getPaymentType())
						// 是否押回单
						|| !StringUtils.equals(oldEntity.getBeReturnReceipt(),
								newEntity.getBeReturnReceipt())
						// bug 修改配载单的司机没有调用结算的接口
						|| !StringUtils.equals(oldEntity.getDriverCode(),
								newEntity.getDriverCode())) {
					/*
					 * 修改前和修改后，符合以下条件，则调用结算modify方法
					 * 1、修改前，在途装卸、最终到达；修改后，在途装卸、最终到达； 2、修改前，非在途装卸；修改后，非在途装卸；
					 * 3、修改前，非在途装卸；修改后，在途装卸、最终到达； 4、修改前，在途装卸、最终到达；修改后，非在途装卸；
					 */
					// 修改前，在途装卸、最终到达；修改后，在途装卸、最终到达；
					if ((StringUtils.equals(FossConstants.YES,
							oldEntity.getBeMidWayLoad())
							&& StringUtils.equals(FossConstants.YES,
									oldEntity.getBeFinallyArrive())
							&& StringUtils.equals(FossConstants.YES,
									newEntity.getBeMidWayLoad()) && StringUtils
								.equals(FossConstants.YES,
										newEntity.getBeFinallyArrive()))
							// 修改前，非在途装卸；修改后，非在途装卸；
							|| (StringUtils.equals(FossConstants.NO,
									newEntity.getBeMidWayLoad()) && StringUtils
									.equals(FossConstants.NO,
											oldEntity.getBeMidWayLoad()))
							// 修改前，非在途装卸；修改后，在途装卸、最终到达；
							|| (StringUtils.equals(FossConstants.NO,
									oldEntity.getBeMidWayLoad())
									&& StringUtils.equals(FossConstants.YES,
											newEntity.getBeMidWayLoad()) && StringUtils
										.equals(FossConstants.YES,
												newEntity.getBeFinallyArrive()))
							// 修改前，在途装卸、最终到达；修改后，非在途装卸；
							|| (StringUtils.equals(FossConstants.YES,
									newEntity.getBeMidWayLoad())
									&& StringUtils.equals(FossConstants.YES,
											newEntity.getBeFinallyArrive()) && StringUtils
										.equals(FossConstants.NO,
												oldEntity.getBeMidWayLoad()))) {
						// 修改前和修改后运费都不为0
						if (oldEntity.getFeeTotal().compareTo(BigDecimal.ZERO) == 1
								&& newEntity.getFeeTotal().compareTo(
										BigDecimal.ZERO) == 1) {

							// 构造对象
							StlVehicleAssembleBillDto dto = new StlVehicleAssembleBillDto();

							CubcVehicleAssembleBillRequest request = new CubcVehicleAssembleBillRequest();
							// 配载车次号
							dto.setVehicleAssembleNo(oldEntity
									.getVehicleAssembleNo());
							request.setVehicleAssembleNo(oldEntity
									.getVehicleAssembleNo());
							// 如果是整车，则传入运单号
							if (StringUtils
									.equals(newEntity.getAssembleType(),
											LoadConstants.VEHICLE_ASSEMBLE_TYPE_CAR_LOAD)) {
								// 获取交接单号
								String handOverBillNo = this
										.queryHandOverBillListByVNo(
												oldEntity
														.getVehicleAssembleNo())
										.get(0).getHandOverBillNo();
								// 获取运单号
								String waybillNo = handOverBillService
										.queryHandOverBillDetailByNo(
												handOverBillNo).get(0)
										.getWaybillNo();
								dto.setWaybillNo(waybillNo);
								request.setWaybillNo(waybillNo);
							}
							// 配载类型
							dto.setAssembleType(newEntity.getAssembleType());
							// 出发部门code\name
							dto.setOrigOrgCode(oldEntity.getOrigOrgCode());
							// 出发部门name
							dto.setOrigOrgName(oldEntity.getOrigOrgName());
							// 到达部门code\name
							dto.setDestOrgCode(oldEntity.getDestOrgCode());
							// 到达部门name
							dto.setDestOrgName(oldEntity.getDestOrgName());
							// 车牌号
							dto.setVehicleNo(newEntity.getVehicleNo());
							// 配载类型
							request.setAssembleType(newEntity.getAssembleType());
							// 出发部门code\name
							request.setOrigOrgCode(oldEntity.getOrigOrgCode());
							// 出发部门name
							request.setOrigOrgName(oldEntity.getOrigOrgName());
							// 到达部门code\name
							request.setDestOrgCode(oldEntity.getDestOrgCode());
							// 到达部门name
							request.setDestOrgName(oldEntity.getDestOrgName());
							// 车牌号
							request.setVehicleNo(newEntity.getVehicleNo());
							/*
							 * 判断车辆是否为合同车，dto车辆所有权类型
							 */
							VehicleAssociationDto vehicleInfo = vehicleService
									.queryVehicleAssociationDtoByVehicleNo(newEntity
											.getVehicleNo());
							if (StringUtils.equals(FossConstants.YES,
									vehicleInfo.getBargainVehicle())) {
								// 合同车
								dto.setVehicleOwnerShip(LoadConstants.VEHICLE_OWNERSHIP_BARGAIN_VEHICLE);
								request.setVehicleOwnerShip(LoadConstants.VEHICLE_OWNERSHIP_BARGAIN_VEHICLE);
							} else {
								// 普通外请车
								dto.setVehicleOwnerShip(LoadConstants.VEHICLE_OWNERSHIP_LEASED_VEHICLE);
								request.setVehicleOwnerShip(LoadConstants.VEHICLE_OWNERSHIP_LEASED_VEHICLE);
							}
							// 司机name、code
							dto.setDriverCode(newEntity.getDriverCode());
							// 司机name
							dto.setDriverName(newEntity.getDriverName());
							// 付款方式
							dto.setPaymentType(newEntity.getPaymentType());
							// 总运费
							dto.setFeeTotal(newEntity.getFeeTotal());
							// 预付运费总额
							dto.setPrePaidFeeTotal(newEntity
									.getPrePaidFeeTotal());
							// 到付运费总额
							dto.setArriveFeeTotal(newEntity.getArriveFeeTotal());
							// 是否押回单
							dto.setBeReturnReceipt(newEntity
									.getBeReturnReceipt());
							// 币种
							dto.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
							// 车辆出发日期
							dto.setLeaveTime(newEntity.getLeaveTime());
							// 司机name、code
							request.setDriverCode(newEntity.getDriverCode());
							// 司机name
							request.setDriverName(newEntity.getDriverName());
							// 付款方式
							request.setPaymentType(newEntity.getPaymentType());
							// 总运费
							request.setFeeTotal(newEntity.getFeeTotal());
							// 预付运费总额
							request.setPrePaidFeeTotal(newEntity
									.getPrePaidFeeTotal());
							// 到付运费总额
							request.setArriveFeeTotal(newEntity
									.getArriveFeeTotal());
							// 是否押回单
							request.setBeReturnReceipt(newEntity
									.getBeReturnReceipt());
							// 币种
							request.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
							// 车辆出发日期
							request.setLeaveTime(newEntity.getLeaveTime());
							// 员工工号
							request.setEmpCode(FossUserContext.getCurrentInfo()
									.getEmpCode());
							// 员工姓名
							request.setEmpName(FossUserContext.getCurrentInfo()
									.getEmpName());
							// 当前登录部门编码
							request.setCurrentDeptCode(FossUserContext
									.getCurrentInfo().getCurrentDeptCode());
							// 当前登录部门名称
							request.setCurrentDeptName(FossUserContext
									.getCurrentInfo().getCurrentDeptName());

							//灰度DTO封装
							GrayParameterDto parDto = new GrayParameterDto();
							parDto.setSourceBillType(CUBCGrayUtil.SBType.PZ.getName());
							parDto.setSourceBillNos(new String[] {dto.getVehicleAssembleNo()});
							//调用灰度工具类方法
							VestResponse vestResponse = cubcUtil.getUcbcGrayData(parDto, new Throwable());
							//返回结果是FOSS走结算， 是CUBC走结算中心
							if (CUBCGrayContants.SYSTEM_CODE_FOSS.equals(vestResponse.getVestBatchResult().get(0).getVestSystemCode())) {
								// 调用结算接口
								this.truckStowageService.modifyTruckStowage(dto, FossUserContext.getCurrentInfo());
							} else {
								LOGGER.error("FOSS推送更新配载单,配载车次号" + request.getVehicleAssembleNo() + "给CUBC开始...");
								// 调用cubc接口
								CubcVehicleAssembleBillResponse response = fossToCubc.pushmodifyTruckStowage(request);
								LOGGER.error("FOSS推送更新配载单,配载车次号" + request.getVehicleAssembleNo() + "给CUBC结束;返回值是 :"
										+ response.getReason());
								if ("0".equals(response.getResult())) {
									throw new TfrBusinessException("cubc返回异常：" + response.getReason());
								}
							}
							// 修改前总运费为0，修改后总运费不为0，则调用结算的addnew
						} else if (oldEntity.getFeeTotal().compareTo(BigDecimal.ZERO) == 0
								&& newEntity.getFeeTotal().compareTo(BigDecimal.ZERO) != 0) {
							this.callStlInterfaceWhenAddNew(newEntity);
							// 修改前总运费不为0，修改后总运费为0，则调用结算的cancel
						} else if (oldEntity.getFeeTotal().compareTo(BigDecimal.ZERO) != 0
								&& newEntity.getFeeTotal().compareTo(BigDecimal.ZERO) == 0) {
							// 修改前车牌号为外请车，修改后车牌号为外请车
							this.callStlInterfaceWhenCancel(oldEntity);
						}
					} else {
						/*
						 * 若不符合if中的判断条件， 说明必然需要对修改前的配载单进行红冲，对修改后的配载单进行生成结算信息
						 * 故直接调用cancell和adnew，到底是否要调用结算接口，要根据在途装卸和最终到达情况在各个方法内判断
						 */
						this.callStlInterfaceWhenCancel(oldEntity);
						this.callStlInterfaceWhenAddNew(newEntity);
					}
					// 修改前有总运费 修改后没有运费
				} else {
					// 由于 外请车总运费可以为0 所以存在修改了 在途装卸类型 但是 车辆没有释放 需要根据条件修改车辆
					// 修改前是 ： ( 不与零担和车 非在途装卸 且 非最终到达 || 不与零担和车 在途装卸 且 最终到达 )
					// -->( 与零担和车 、非在途装卸、非最终到达||不与零担和车 、 在途装卸、非最终到达)
					// 车辆状态有已使用变为未使用
					if (((StringUtils.equals(FossConstants.NO,
							oldEntity.getBeInLTLCar()) && StringUtils.equals(
							FossConstants.NO, oldEntity.getBeMidWayLoad())) || (StringUtils
							.equals(FossConstants.NO, oldEntity.getBeInLTLCar())
							&& StringUtils.equals(FossConstants.YES,
									oldEntity.getBeMidWayLoad()) && StringUtils
								.equals(FossConstants.YES,
										oldEntity.getBeFinallyArrive())))
							// -->
							&& ((StringUtils.equals(FossConstants.YES,
									newEntity.getBeInLTLCar()) && StringUtils
									.equals(FossConstants.NO,
											newEntity.getBeMidWayLoad())) || (StringUtils
									.equals(FossConstants.NO,
											newEntity.getBeInLTLCar())
									&& StringUtils.equals(FossConstants.YES,
											newEntity.getBeMidWayLoad()) && StringUtils
										.equals(FossConstants.NO,
												newEntity.getBeFinallyArrive())))) {
						// 更改约车信息为未使用状态
						inviteVehicleService.updateUseStatusForUnused(
								oldEntity.getInviteNo(),
								oldEntity.getVehicleNo());
					}
					// 修改前是 ：( 与零担和车 、非在途装卸、非最终到达 || 不与零担和车 、 在途装卸、非最终到达)--->(
					// 不与零担和车 非在途装卸 且 非最终到达 || 不与零担和车 在途装卸 且 最终到达 )
					// 车辆状态由未使用改为已使用
					if (((StringUtils.equals(FossConstants.YES,
							oldEntity.getBeInLTLCar()) && StringUtils.equals(
							FossConstants.NO, oldEntity.getBeMidWayLoad())) || (StringUtils
							.equals(FossConstants.NO, oldEntity.getBeInLTLCar())
							&& StringUtils.equals(FossConstants.YES,
									oldEntity.getBeMidWayLoad()) && StringUtils
								.equals(FossConstants.NO,
										oldEntity.getBeFinallyArrive())))
							// -->
							&& ((StringUtils.equals(FossConstants.NO,
									newEntity.getBeInLTLCar()) && StringUtils
									.equals(FossConstants.NO,
											newEntity.getBeMidWayLoad())) || (StringUtils
									.equals(FossConstants.NO,
											newEntity.getBeInLTLCar())
									&& StringUtils.equals(FossConstants.YES,
											newEntity.getBeMidWayLoad()) && StringUtils
										.equals(FossConstants.YES,
												newEntity.getBeFinallyArrive())))) {
						// 更改约车信息为使用状态
						inviteVehicleService.updateUseStatusForUsing(
								oldEntity.getInviteNo(),
								oldEntity.getVehicleNo());
					}
				}
			}
		} else {// 如果车牌号不相同
			if (StringUtils.equals(oldEntity.getVehicleOwnerShip(),
					ComnConst.ASSETS_OWNERSHIP_TYPE_COMPANY)
					&& StringUtils.equals(newEntity.getVehicleOwnerShip(),
							ComnConst.ASSETS_OWNERSHIP_TYPE_COMPANY)) {
				// 如果修改前后均为公司车，则不处理
				return;
			} else if (StringUtils.equals(oldEntity.getVehicleOwnerShip(),
					ComnConst.ASSETS_OWNERSHIP_TYPE_COMPANY)
					&& StringUtils.equals(newEntity.getVehicleOwnerShip(),
							ComnConst.ASSETS_OWNERSHIP_TYPE_LEASED)) {
				// 修改前车牌号为公司车，修改后为外请车，则调用结算addnew方法
				this.callStlInterfaceWhenAddNew(newEntity);
			} else if (StringUtils.equals(oldEntity.getVehicleOwnerShip(),
					ComnConst.ASSETS_OWNERSHIP_TYPE_LEASED)
					&& StringUtils.equals(newEntity.getVehicleOwnerShip(),
							ComnConst.ASSETS_OWNERSHIP_TYPE_COMPANY)) {
				// 修改前车牌号为外请车，修改后为公司车
				this.callStlInterfaceWhenCancel(oldEntity);
			} else if (StringUtils.equals(oldEntity.getVehicleOwnerShip(),
					ComnConst.ASSETS_OWNERSHIP_TYPE_LEASED)
					&& StringUtils.equals(newEntity.getVehicleOwnerShip(),
							ComnConst.ASSETS_OWNERSHIP_TYPE_LEASED)) {
				// 修改前车牌号为外请车，修改后车牌号为外请车
				this.callStlInterfaceWhenCancel(oldEntity);
				this.callStlInterfaceWhenAddNew(newEntity);
			}
		}
	}

	/**
	 * 私有方法，为修改配载单基本信息生成一条日志
	 * 
	 * @author 045923-foss-shiwei
	 * @date 2012-11-20 下午12:02:05
	 */
	private VehicleAssembleBillOptLogEntity createLogForBaseInfo(
			VehicleAssembleBillEntity baseEntity, String optInfo) {
		// 构造日志实体
		VehicleAssembleBillOptLogEntity log = new VehicleAssembleBillOptLogEntity();
		// 补充基本属性
		addFieldValueForLog(log, baseEntity);
		// 修改类别
		log.setOptType("修改基本信息");
		// 修改内容
		log.setOptContent(optInfo);
		return log;
	}

	/**
	 * 私有方法，为新增的、删除的交接单生成一条日志； 同时更新新增的、删除的交接单状态为已配载、已交接
	 * 
	 * @author 045923-foss-shiwei
	 * @date 2012-11-20 上午11:38:11
	 */
	private VehicleAssembleBillOptLogEntity createLogForHandOverBill(
			VehicleAssembleBillEntity oldEntity,
			VehicleAssembleBillEntity baseEntity,
			List<VehicleAssembleBillDetailEntity> handOverBillList, int type) {
		// 操作日志对象
		VehicleAssembleBillOptLogEntity log = new VehicleAssembleBillOptLogEntity();
		// 交接单更新dtoList
		List<UpdateHandOverBillStateDto> dtoList = new ArrayList<UpdateHandOverBillStateDto>();
		// 补充基本属性
		addFieldValueForLog(log, baseEntity);
		// 操作类别 type 0，新增；1，删除
		if (type == 0) {
			log.setOptType("添加交接单");
		}
		if (type == 1) {
			log.setOptType("删除交接单");
		}

		// 获取交接单的个数
		int listLength = handOverBillList.size();
		// 拼接修改内容
		StringBuffer sb = new StringBuffer();
		if (type == 0) {
			sb.append("添加");
		}
		if (type == 1) {
			sb.append("删除");
		}
		sb.append(listLength);
		sb.append("个交接单，交接单号为：");
		for (int i = 0; i < listLength; i++) {
			sb.append(handOverBillList.get(i).getHandOverBillNo());
			// 主键
			handOverBillList.get(i).setId(UUIDUtils.getUUID());
			// 配载单id
			handOverBillList.get(i)
					.setVehicleAssembleBillId(baseEntity.getId());
			if (i + 1 == listLength) {
				sb.append("；");
			} else {
				sb.append("、");
			}
			// 构造对象，修改交接单状态
			UpdateHandOverBillStateDto updateDto = new UpdateHandOverBillStateDto();
			updateDto.setHandOverBillNo(handOverBillList.get(i)
					.getHandOverBillNo());
			if (type == 1) {
				// 删除交接单时回滚至已交接
				updateDto.setBeJump(FossConstants.YES);
				updateDto
						.setTargetState(LoadConstants.HANDOVERBILL_STATE_FORMAL_HANDOVER);
				// 如果配载单已生成任务车辆，则调用任务车辆接口
				if (StringUtils.equals(oldEntity.getBeCreateTaskTruck(),
						FossConstants.YES)) {
					truckTaskService.updateBillLevelToValid(updateDto
							.getHandOverBillNo());
				}
			}
			if (type == 0) {
				// 添加交接单修改为已配载(如果配载单已出发，则直接修改交接单状态为已出发)
				if (oldEntity.getState() == LoadConstants.VEHICLEASSEMBLEBILL_STATE_ALREADY_DEPART) {
					updateDto
							.setTargetState(LoadConstants.HANDOVERBILL_STATE_ALREADY_DEPART);
					updateDto.setBeJump(FossConstants.YES);// 中间跳过“已配载”状态，故设置该状态为Y
				} else {
					updateDto
							.setTargetState(LoadConstants.HANDOVERBILL_STATE_ALREADY_ASSEMBLE);
				}

				// 如果配载单已生成任务车辆，则调用任务车辆接口
				if (StringUtils.equals(oldEntity.getBeCreateTaskTruck(),
						FossConstants.YES)) {
					truckTaskService.updateBillLevelToUnValid(updateDto
							.getHandOverBillNo());
				}
			}
			dtoList.add(updateDto);
		}
		// 更新交接单状态
		handOverBillService.updateHandOverBillState(dtoList);
		// 修改内容
		log.setOptContent(sb.toString());
		return log;
	}

	private VehicleAssembleBillOptLogEntity createLogForRewardOrPunish(
			VehicleAssembleBillEntity oldEntity,
			VehicleAssembleBillEntity baseEntity,
			List<RewardOrPunishAgreementEntity> rewardOrPunishAgreementList) {

		// 操作日志对象
		VehicleAssembleBillOptLogEntity log = new VehicleAssembleBillOptLogEntity();
		// 补充基本属性
		addFieldValueForLog(log, baseEntity);
		// 操作类型
		log.setOptType("修改时效条款");
		// 拼接修改内容
		StringBuffer sb = new StringBuffer();
		// 获取原有的奖罚信息
		List<RewardOrPunishAgreementEntity> rewardOrPunishDatil = vehicleAssembleBillDao
				.queryRewardOrPunishDetail(oldEntity.getVehicleAssembleNo());
		// 判断一下新的协议是否有内容--TODO
		// 记录是否存时间区间在标志
		boolean isInit = false;
		// 循环判断是否修改了奖罚内容（只能统计到修改和新增的）
		for (int i = 0; i < rewardOrPunishAgreementList.size(); i++) {
			// 获取修改后的奖罚内容
			RewardOrPunishAgreementEntity newAgreement = rewardOrPunishAgreementList
					.get(i);
			// 用于存放匹配到的奖罚实体
			RewardOrPunishAgreementEntity temp = null;
			// 先判断时间段
			for (int j = 0; j < rewardOrPunishDatil.size(); j++) {
				RewardOrPunishAgreementEntity oldAgreement = rewardOrPunishDatil
						.get(j);
				// 匹配当前奖罚类型对应的时间段是否存在
				if (StringUtils.equals(newAgreement.getRewardOrPunishType(),
						oldAgreement.getRewardOrPunishType())
						&& StringUtils.equals(newAgreement.getTimeLimit(),
								oldAgreement.getTimeLimit())) {

					isInit = true;
					temp = oldAgreement;
				}
			}

			// 如果匹配到当前奖罚类型对应的时间段，则比较是否修改奖罚金额；相等这表示没有做修改，否则做日志记录
			if (isInit) {
				// BigDecimal 能用equals？
				if (!newAgreement.getAgreementMoney().equals(
						temp.getAgreementMoney())) {

					sb.append("修改“"
							+ convertRewardOrPunishType(newAgreement
									.getRewardOrPunishType()) + "类型”“"
							+ convertTimeType(newAgreement.getTimeLimit())
							+ "”的金额由“" + temp.getAgreementMoney() + "”改为“"
							+ newAgreement.getAgreementMoney() + "”;");
				}
				isInit = false;
			} else {
				// 如果没有匹配到当前时间段，记录日志内容
				sb.append("新增“"
						+ convertRewardOrPunishType(newAgreement
								.getRewardOrPunishType()) + "类型”“"
						+ convertTimeType(newAgreement.getTimeLimit())
						+ "”的金额为“" + newAgreement.getAgreementMoney() + "”;");
			}

		}
		// 循环判断是否删除的奖罚内容
		boolean isdelet = false;

		for (int i = 0; i < rewardOrPunishDatil.size(); i++) {
			// 获取修改后的奖罚内容
			RewardOrPunishAgreementEntity oldAgreement = rewardOrPunishDatil
					.get(i);

			for (int j = 0; j < rewardOrPunishAgreementList.size(); j++) {
				RewardOrPunishAgreementEntity newAgreement = rewardOrPunishAgreementList
						.get(j);
				// 匹配当前奖罚类型对应的时间段是否存在
				if (StringUtils.equals(oldAgreement.getRewardOrPunishType(),
						newAgreement.getRewardOrPunishType())
						&& StringUtils.equals(oldAgreement.getTimeLimit(),
								newAgreement.getTimeLimit())) {

					isdelet = true;

				}
			}

			if (!isdelet) {
				// 没有匹配上说明该跳协议被删除了，记录日志
				sb.append("删除“"
						+ convertRewardOrPunishType(oldAgreement
								.getRewardOrPunishType()) + "类型”“"
						+ convertTimeType(oldAgreement.getTimeLimit())
						+ "”金额为“" + oldAgreement.getAgreementMoney() + "”的数据;");
			} else {
				isdelet = false;
			}
		}
		// 修改内容
		log.setOptContent(sb.toString());

		return log;
	}

	/**
	 * 私有方法，调用
	 * */
	private void checkRewardInfo(
			List<RewardOrPunishAgreementEntity> rewardOrPunishAgreementEntity,
			VehicleAssembleBillEntity baseEntity) {
		// 把校验放到后台： 1、公司车不能有时效协议；2、在途卸的不能有时效协议 3、整车的没有时效协议
		if ((StringUtils.equals(baseEntity.getVehicleOwnerShip(),
				ComnConst.ASSETS_OWNERSHIP_TYPE_COMPANY)
				|| StringUtils.equals(baseEntity.getAssembleType(),
						LoadConstants.VEHICLE_ASSEMBLE_TYPE_CAR_LOAD) || (StringUtils
				.equals(baseEntity.getBeMidWayLoad(), FossConstants.YES) && StringUtils
				.equals(baseEntity.getBeFinallyArrive(), FossConstants.NO)))
				&& StringUtils.equals(baseEntity.getBeTimeLiness(),
						FossConstants.YES)) {
			throw new TfrBusinessException("只有专线外请车且非在途的配载才能签署时效条款!");
		}

		if (rewardOrPunishAgreementEntity.size() <= 0) {
			throw new TfrBusinessException("请输入时效条款内容！");
		}
		// 是否存在奖励协议
		boolean isReward = false;
		for (RewardOrPunishAgreementEntity temp : rewardOrPunishAgreementEntity) {
			if (StringUtils.equals(temp.getRewardOrPunishType(),
					LoadConstants.REWARDPROT_TYPE_REWARD)) {
				isReward = true;
				break;
			}
		}
		// 如果存在奖励协议则需要：1、查看奖励类型是否可用 2、查看车辆到达时间是否在每个外场配置的时间区间内
		if (isReward) {
			// 查看奖励类型是否可用
			if (beRewardCheck()) {

				ConfigurationParamsEntity paramEntity = configurationParamsService.queryConfigurationParamsByOrgCode(
								DictionaryConstants.SYSTEM_CONFIG_PARM__TFR,
								ConfigurationParamsConstants.TFR_PARM_REWARDORPUNISH_TIME_INTERVAL,
								baseEntity.getDestOrgCode());
				if (paramEntity == null) {
					throw new TfrBusinessException("获取是否奖励外场对应的时间参数配置失败！");
				}
				// 时间配置格式：22:30+4
				String timeInterval = paramEntity.getConfValue();
				String[] timetemp = timeInterval.split("\\+");
				String[] temp = timetemp[0].split(":");
				// 配置参中配置的时间小时：22
				int hour = Integer.valueOf(temp[0]);
				// 配置参数中配置的时间分钟：30
				int minutedown = Integer.valueOf(temp[1]);
				// 配置参数中配置的时长：4
				long minuteup = Long.valueOf(timetemp[1]);
				// 配置参数中配置的办单时长
				ConfigurationParamsEntity configurationParams = configurationParamsService
						.queryConfigurationParamsOneByCode(
								ConfigurationParamsConstants.TFR_PARM_ADDVECHICLEAESSBILL_TIME);
				if (configurationParams == null) {
					throw new TfrBusinessException("请配置配载单办单时间！");
				}
				int min = Integer.valueOf(configurationParams.getConfValue());
				// 运行时长
				String runHour = baseEntity.getRunHours();
				String[] runHours = runHour.split("\\.");
				long a = 0, b = 0;
				a = Long.valueOf(runHours[0]);
				int m = 10;
				if (runHours.length > 1) {
					for (int i = 1; i < runHours[1].length(); i++) {
						m = m * m;
					}
					b = Long.valueOf(runHours[1]);

				}
				
				//当前时间
				Date nowDate = new Date();
					// 计算预计到达世间
					Date preArrDate = new Date(nowDate.getTime()
							+ LoadConstants.SONAR_NUMBER_1001 * LoadConstants.SONAR_NUMBER_60
									* (min + a * LoadConstants.SONAR_NUMBER_60)
							+ (LoadConstants.SONAR_NUMBER_1001 * LoadConstants.SONAR_NUMBER_60
									* LoadConstants.SONAR_NUMBER_60 * b) / m);

					// 时间格式转换，目的去掉 时，分，秒
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				ParsePosition pos = new ParsePosition(0);
					// 去掉 时，分，秒后的时间
					Date formatDate = format.parse(format.format(preArrDate), pos);

					// 计算不奖励的起始时间段:当前时间的日期+参数配置中的起始时间
					Date timeDown = new Date(formatDate.getTime() + LoadConstants.SONAR_NUMBER_1001
							* LoadConstants.SONAR_NUMBER_60 * (hour * LoadConstants.SONAR_NUMBER_60 + minutedown));
					// 计算不奖励的结束时间段：当前时间日期+参数配置中的结束时间
					Date timeUp = new Date(timeDown.getTime() + LoadConstants.SONAR_NUMBER_1001
							* LoadConstants.SONAR_NUMBER_60 * LoadConstants.SONAR_NUMBER_60 * minuteup);

					if (preArrDate.after(timeDown) && preArrDate.before(timeUp)) {
					throw new TfrBusinessException("该配载单预计到达时间不再奖励时间范围内，不能有奖励的协议！");
				}

					// 是否可奖线路
					RewardOrPunishAgreementDto rewardDto = this.checkBeAgingLine(baseEntity.getDestOrgCode());
					if (rewardDto.getBeAgingLine()) {
					throw new TfrBusinessException("该路线不支持奖励包含奖励类型的协议！");
				}
			} else {
				throw new TfrBusinessException("现不支持奖励类型的协议！");
			}
		}
		}


	/**
	 * 用于时间段表现方式转换
	 * 
	 * @author foss-heyongdong
	 * @date 2013年12月20日 17:26:40
	 * @return String
	 * */
	private String convertTimeType(String str) {
		String timeType = "";
		if (StringUtils.equals(str, "0")) {
			timeType = "0小时以上";
		} else if (StringUtils.equals(str, "0-1")) {
			timeType = "0-1小时";
		} else if (StringUtils.equals(str, "1")) {
			timeType = "1小时以上";
		} else if (StringUtils.equals(str, "1-2")) {
			timeType = "1-2小时";
		} else if (StringUtils.equals(str, "2")) {
			timeType = "2小时以上";
		} else {
			timeType = "";
		}
		return timeType;
	}

	private String convertRewardOrPunishType(String str) {
		String rewardType = "";
		if (StringUtils.equals(str, LoadConstants.REWARDPROT_TYPE_REWARD)) {
			rewardType = "奖励";
		} else if (StringUtils.equals(str, LoadConstants.REWARDPROT_TYPE_FINE)) {
			rewardType = "罚款";
		} 
		return rewardType;
	}

	/**
	 * 私有方法，为日志补充属性
	 * 
	 * @author 045923-foss-shiwei
	 * @date 2012-11-20 上午11:58:06
	 */
	private void addFieldValueForLog(VehicleAssembleBillOptLogEntity log,
			VehicleAssembleBillEntity baseEntity) {
		// 日志id
		log.setId(UUIDUtils.getUUID());
		// 日志制单时间
		log.setBillingTime(baseEntity.getCreateDate());
		// 日志配载单id
		log.setVehicleAssembleBillId(baseEntity.getId());
		// 日志配载单编号
		log.setVehicleAssembleNo(baseEntity.getVehicleAssembleNo());
		// 获取当前用户、时间等
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		// 修改人code
		log.setOperatorCode(currentInfo.getEmpCode());
		// 修改人name
		log.setOperatorName(currentInfo.getEmpName());
		// 操作时间
		log.setOptTime(baseEntity.getModifyDate());
	}

	/**
	 * 查询出打印配置单中的一部分数据
	 * 
	 * @author ibm-zhangyixin
	 * @date 2012-11-20 下午2:07:50
	 */
	@Override
	public PrintVehicleAssembleBillHeaderDto queryVehicleAssembleBillPrtDataSource(
			String vehicleAssembleNo) {
		// 返回查询结果
		return this.vehicleAssembleBillDao
				.queryVehicleAssembleBillPrtDataSource(vehicleAssembleNo);
	}

	/**
	 * 查询出打印配置单中的一部分数据_根据配载单号查询出装车人
	 * 
	 * @author ibm-zhangyixin
	 * @date 2012-11-20 下午2:07:50
	 */
	@Override
	public String queryLoaderNameByVehicleassemblebillId(
			String vehicleassemblebillId) {
		// 返回查询结果
		return this.vehicleAssembleBillDao
				.queryLoaderNameByVehicleassemblebillId(vehicleassemblebillId);
	}

	/**
	 * 查询出打印配置单中的一部分数据_查询出该配置单下所有交接单的汇总
	 * 
	 * @author ibm-zhangyixin
	 * @date 2012-11-20 下午2:07:50
	 */
	@Override
	public PrintVehicleAssembleBillHeaderDto querySummaryHandOverBillByVehicleassemblebill(
			String vehicleassemblebillId) {
		// 返回查询结果
		return this.vehicleAssembleBillDao
				.querySummaryHandOverBillByVehicleassemblebill(vehicleassemblebillId);
	}

	/**
	 * 查询出打印配置单中的一部分数据_查询出该配置单下运单明细数据
	 * 
	 * @author ibm-zhangyixin
	 * @date 2012-11-20 下午2:07:50
	 */
	@Override
	public List<PrintVehicleAssembleBillDetailDto> queryVehicleAssembleBillDetailPrtDataSource(
			String vehicleassemblebillId) {
		// 返回查询结果
		return this.vehicleAssembleBillDao
				.queryVehicleAssembleBillDetailPrtDataSource(vehicleassemblebillId);
	}

	/**
	 * 插入打印次数
	 * 
	 * @author ibm-zhangyixin
	 * @date 2012-11-20 下午2:29:15
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService#insertMotorstowagebillrecord(com.deppon.foss.module.transfer.load.api.shared.domain.MotorstowagebillrecordEntity)
	 */
	@Override
	public int insertMotorstowagebillrecord(
			MotorstowagebillrecordEntity motorstowagebillrecordEntity) {
		// 记录打印次数
		return this.vehicleAssembleBillDao
				.insertMotorstowagebillrecord(motorstowagebillrecordEntity);
	}

	/**
	 * 根据交接单号, 运单号, 查询出交接明细
	 * 
	 * @author ibm-zhangyixin
	 * @date 2012-11-20 下午2:35:35
	 */
	@Override
	public String getHandOverBillSerialNoDetailsByWayBillNo(String wayBillNo,
			String handOverBillNo) {
		// 返回查询结果
		return this.vehicleAssembleBillDao
				.getHandOverBillSerialNoDetailsByWayBillNo(wayBillNo,
						handOverBillNo);
	}

	/**
	 * 根据配载车次号和封签类型获取封签号list
	 * 
	 * @author 045923-foss-shiwei
	 * @date 2013-3-16 下午2:02:02
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService#querySealNosByVehicleAssembleNo(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String querySealNosByVehicleAssembleNo(String vehicleAssembleNo,
			String sealType) {
		// 返回查询结果
		return this.vehicleAssembleBillDao.querySealNosByVehicleAssembleNo(
				vehicleAssembleNo, sealType);
	}

	/**
	 * 根据车牌号，调用综合接口，获取车辆信息
	 * 
	 * @author 045923-foss-shiwei
	 * @param vehicleNo
	 *            车牌号
	 * @date 2012-11-22 下午2:29:29
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService#queryVehicleAssociationDtoByVehicleNo(java.lang.String)
	 */
	@Override
	public VehicleAssociationDto queryVehicleInfoByVehicleNo(String vehicleNo) {
		// 返回查询结果
		return vehicleService.queryVehicleAssociationDtoByVehicleNo(vehicleNo);
	}

	/**
	 * 根据货柜编号，调用综合接口，获取货柜尺寸载重净空信息
	 * 
	 * @author 045923-foss-shiwei
	 * @date 2012-11-23 上午10:22:18
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService#queryContainerInfoByContainerNo(java.lang.String)
	 */
	@Override
	public OwnTruckEntity queryContainerInfoByContainerNo(String containerNo) {
		// 构造查询条件
		OwnTruckEntity entity = new OwnTruckEntity();
		// 设置货柜号
		entity.setContainerCode(containerNo);
		// 返回查询结果
		return ownVehicleService.queryOwnVehicleBySelective(entity,
				DictionaryValueConstants.VEHICLE_TYPE_TRAILER);
	}

	/**
	 * 根据公司车车牌号，获取车辆类型
	 * 
	 * @author 045923-foss-shiwei
	 * @date 2012-11-23 上午10:21:27
	 */
	@Override
	public String queryOwnerVehicleTypeByVehicleNo(String vehicleNo) {
		// 构造车牌号的字符串数组
		String[] str = { vehicleNo };
		// 调用综合接口
		List<VehicleAssociationDto> vehicleList = ownVehicleService
				.queryOwnVehicleAssociationDtosByVehicleNos(str);
		if (CollectionUtils.isNotEmpty(vehicleList)) {
			VehicleAssociationDto vehicleDto = vehicleList.get(0);
			// 返回车辆类型
			return vehicleDto.getVehicleType();
		}
		return null;
	}

	/**
	 * 调用综合接口，根据到达部门编码，获取该部门是否支持自动分拣
	 * 
	 * @author 045923-foss-shiwei
	 * @date 2012-11-23 下午3:58:16
	 */
	@Override
	public OutfieldEntity queryOutfieldIsHasSortingMachine(String outfieldCode) {
		// 调用综合接口，获取外场实体
		OutfieldEntity entity = outfieldService
				.queryOutfieldByOrgCode(outfieldCode);
		// 返回实体
		return entity;
	}

	/**
	 * 为前台生成配载车次号
	 * 
	 * @author 045923-foss-shiwei
	 * @date 2012-11-23 下午4:35:24
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService#showVehicleAssembleNo(java.lang.String,
	 *      java.util.Date)
	 */
	@Override
	public String showVehicleAssembleNo(String destDeptCode, String isCarLoad,
			Date leaveTime) {
		// 获取当前部门编码
		String departDeptCode = this.querySuperiorOrgCode();
		// 获取当前外场
		OutfieldEntity currentOutfield = outfieldService
				.queryOutfieldByOrgCode(departDeptCode);
		if (currentOutfield == null) {
			throw new TfrBusinessException("当前部门不是外场！");
		}
		// 根据code获取到达部门
		OutfieldEntity destOutfield = null;
		OrgAdministrativeInfoEntity destOrgEntity = this.orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCode(destDeptCode);
		if (destOrgEntity == null) {
			throw new TfrBusinessException("到达部门不存在！");
		}
		// 如果不是整车配载单，则到达部门必须为外场
		if (!StringUtils.equals(FossConstants.YES, isCarLoad)
				&& !StringUtils.equals(destOrgEntity.getTransferCenter(),
						FossConstants.YES)) {
			throw new TfrBusinessException("“专线”配载时，到达部门必须为外场！");
		}
		// 如果到达部门为外场
		if (StringUtils.equals(destOrgEntity.getTransferCenter(),
				FossConstants.YES)) {
			destOutfield = outfieldService.queryOutfieldByOrgCode(destDeptCode);
			if (destOutfield == null) {
				throw new TfrBusinessException("到达部门组织标记为外场，但不存在该外场信息，请核对基础资料！");
			}
			// 若到达部门为营业部，则获取该营业部的到达配载外场
		} else if (StringUtils.equals(destOrgEntity.getSalesDepartment(),
				FossConstants.YES)) {
			// 获取营业部的到达配载外场
			MapDto destDto = this.lineService
					.queryDefaultArriveTransferOrgCode(destDeptCode);
			if (destDto == null || StringUtils.isBlank(destDto.getCode())) {
				MapDto destDto2 = this.expresslineService
						.queryDefaultArriveTransferOrgCode(destDeptCode);
				if (destDto2 == null || StringUtils.isBlank(destDto2.getCode())) {
					throw new TfrBusinessException("该到达营业部的到达配载外场不存在！");
				}
				String destOutfieldOrgCode = destDto2.getCode();
				destOutfield = outfieldService
						.queryOutfieldByOrgCode(destOutfieldOrgCode);
				if (destOutfield == null) {
					throw new TfrBusinessException("该到达营业部的到达配载外场不存在！");
				}
			} else {
				String destOutfieldOrgCode = destDto.getCode();
				destOutfield = outfieldService
						.queryOutfieldByOrgCode(destOutfieldOrgCode);
				if (destOutfield == null) {
					throw new TfrBusinessException("该到达营业部的到达配载外场不存在！");
				}
			}
		} else {
			throw new TfrBusinessException("到达部门必须为营业部或者外场！");
		}
		// 截取出发日期年月日
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String leaveDate = dateFormat.format(leaveTime).toString();
		if (destDeptCode == null || destDeptCode.equals("")
				|| isCarLoad == null || isCarLoad.equals("")
				|| leaveTime == null) {
			return null;
		}
		return tfrCommonService
				.showSerialNumber(TfrSerialNumberRuleEnum.PZCC, departDeptCode,
						destOutfield.getOrgCode(), leaveDate, isCarLoad);
	}

	/**
	 * 私有方法，生成配载车次号
	 * 
	 * @author 045923-foss-shiwei
	 * @date 2012-12-3 下午5:12:56
	 */
	private String generateVehicleAssembleNo(String destDeptCode,
			String isCarLoad, Date leaveTime, String bePackage) {
		// 获取当前部门编码
		String departDeptCode = this.querySuperiorOrgCode();
		// 获取当前外场
		OutfieldEntity currentOutfield = outfieldService
				.queryOutfieldByOrgCode(departDeptCode);
		if (currentOutfield == null) {
			throw new TfrBusinessException("当前部门不是外场！");
		}
		// 根据code获取到达部门
		OutfieldEntity destOutfield = null;
		OrgAdministrativeInfoEntity destOrgEntity = this.orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCode(destDeptCode);
		if (destOrgEntity == null) {
			throw new TfrBusinessException("到达部门不存在！");
		}
		// 如果不是整车配载单，则到达部门必须为外场
		if (!StringUtils.equals(FossConstants.YES, isCarLoad)
				&& !StringUtils.equals(destOrgEntity.getTransferCenter(),
						FossConstants.YES)) {
			throw new TfrBusinessException("“专线”配载时，到达部门必须为外场！");
		}
		// 如果到达部门为外场
		if (StringUtils.equals(destOrgEntity.getTransferCenter(),
				FossConstants.YES)) {
			destOutfield = outfieldService.queryOutfieldByOrgCode(destDeptCode);
			if (destOutfield == null) {
				throw new TfrBusinessException("到达部门组织标记为外场，但不存在该外场信息，请核对基础信息！");
			}
			// 若到达部门为营业部，则获取该营业部的到达配载外场
		} else if (StringUtils.equals(destOrgEntity.getSalesDepartment(),
				FossConstants.YES)) {
			// 获取营业部的到达配载外场
			MapDto destDto = this.lineService
					.queryDefaultArriveTransferOrgCode(destDeptCode);
			if (destDto == null || StringUtils.isBlank(destDto.getCode())) {
				MapDto destDto2 = this.expresslineService
						.queryDefaultArriveTransferOrgCode(destDeptCode);
				if (destDto2 == null || StringUtils.isBlank(destDto2.getCode())) {
					throw new TfrBusinessException("该到达营业部的到达配载外场不存在！");
				}
				String destOutfieldOrgCode = destDto2.getCode();
				destOutfield = outfieldService
						.queryOutfieldByOrgCode(destOutfieldOrgCode);
				if (destOutfield == null) {
					throw new TfrBusinessException("该到达营业部的到达配载外场不存在！");
				}
			} else {
				String destOutfieldOrgCode = destDto.getCode();
				destOutfield = outfieldService
						.queryOutfieldByOrgCode(destOutfieldOrgCode);
				if (destOutfield == null) {
					throw new TfrBusinessException("该到达营业部的到达配载外场不存在！");
				}
			}
		} else {
			throw new TfrBusinessException("到达部门必须为营业部或者外场！");
		}
		// 截取出发日期年月日
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		// 发车日期字符串，yyyyMMdd
		String leaveDate = dateFormat.format(leaveTime).toString();
		return this.generateVehicleAssembleNo(departDeptCode,
				destOutfield.getOrgCode(), leaveDate, isCarLoad, bePackage);
	}

	/**
	 * 批量更新配载单状态
	 * 
	 * @author 045923-foss-shiwei
	 * @date 2012-11-26 下午8:45:28
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService#updateVehicleAssembleBillState(java.util.List)
	 */
	@Override
	public boolean updateVehicleAssembleBillState(
			List<UpdateVehicleAssembleBillStateDto> updateVehicleAssembleBillStateDtoList) {
		// 调用dao
		vehicleAssembleBillDao
				.updateVehicleAssembleBillState(updateVehicleAssembleBillStateDtoList);
		// 返回处理成功
		return true;
	}

	/**
	 * 根据配载车次号更新配载单状态
	 * 
	 * @author 045923-foss-shiwei
	 * @date 2012-11-26 下午8:45:45
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService#updateVehicleAssembleBillStateByVNo(java.lang.String,
	 *      int)
	 */
	@Override
	public boolean updateVehicleAssembleBillStateByVNo(
			String vehicleAssembleNo, int targetState) {
		// 构造对象
		UpdateVehicleAssembleBillStateDto dto = new UpdateVehicleAssembleBillStateDto();
		// 配载车次号
		dto.setVehicleAssembleNo(vehicleAssembleNo);
		// 目标状态
		dto.setTargetState(targetState);
		// 构造list
		List<UpdateVehicleAssembleBillStateDto> updateVehicleAssembleBillStateDtoList = new ArrayList<UpdateVehicleAssembleBillStateDto>();
		updateVehicleAssembleBillStateDtoList.add(dto);
		// 调用dao
		vehicleAssembleBillDao
				.updateVehicleAssembleBillState(updateVehicleAssembleBillStateDtoList);

		return true;
	}

	/**
	 * 根据配载车次号作废配载单
	 * 
	 * @author 045923-foss-shiwei
	 * @date 2012-11-30 下午6:25:23
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService#cancelVehicleAssembleBill(java.lang.String)
	 */
	@Override
	@Transactional
	public boolean cancelVehicleAssembleBill(String vehicleAssembleNo) {
		/**
		 * 只能作废符合以下条件的配载单： 1、状态为“未发车”； 2、“出发部门”为本部门； 3、未做出发付款确认；
		 */
		/**
		 * 作废配载单后做以下操作： 1、将配载单下的交接单状态修改为“已交接”； 2、如果车牌号为外请车：
		 * a）则需要调用外请车约车接口，放空使用的约车信息； b）调用结算红冲接口； 3、如果配载单已生成任务车辆明细信息，则需调用任务车辆接口；
		 */
		// 根据配载车次号获取配载单实体
		List<VehicleAssembleBillEntity> billEntityList = this
				.queryVehicleAssembleBillByNo(vehicleAssembleNo);
		if (billEntityList.size() == 0) {
			throw new TfrBusinessException("配载单不存在或配载单已作废！");
		}
		VehicleAssembleBillEntity billEntity = billEntityList.get(0);
		// 如果出发部门不是本部门
		String deptCode = this.querySuperiorOrgCode();
		if (!StringUtils.equals(deptCode, billEntity.getOrigOrgCode())) {
			throw new TfrBusinessException("配载单出发部门不是本部门，无权作废！");
		}
		// 如果状态不是“未发车”
		if (billEntity.getState() != LoadConstants.VEHICLEASSEMBLEBILL_STATE_NOT_DEPART) {
			throw new TfrBusinessException("该配载单已经发车，无法作废！");
		}
		
		//封装灰度实体，类型是配载单
		GrayParameterDto parDto = new GrayParameterDto();
		parDto.setSourceBillType(CUBCGrayUtil.SBType.PZ.getName());
		parDto.setSourceBillNos(new String[] {vehicleAssembleNo});
		
		VestResponse vestResponse =cubcUtil.getUcbcGrayData(parDto, new Throwable());
		
		
		if (CUBCGrayContants.SYSTEM_CODE_FOSS.equals(vestResponse.getVestBatchResult().get(0).getVestSystemCode())) {
			// 如果已做出发付款确认(调用结算接口)
			if (billPayableService.queryBillPayableIsWriteOff(vehicleAssembleNo, null)) {
				throw new TfrBusinessException("该配载单已做出发付款确认，无法作废！");
			}
		} else {
				Map<String, String> paramMap = new HashMap<String, String>();
				// 配载单号
				paramMap.put("sourceBillNo", vehicleAssembleNo);
				paramMap.put("isAdjust", null);
				CubcValidationSourceBillNoResponse cubcValidationVerificationResponse = fossToCubcService
						.queryBillPayableIsWriteOff(paramMap);
				if (null != cubcValidationVerificationResponse) {
					if (!cubcValidationVerificationResponse.isSuccess()) {
						throw new TfrBusinessException("根据来源单号调用cubc查询应付单是否已经核销失败:cubc"
								+ cubcValidationVerificationResponse.getExceptionMsg() + "！");
					} else if (cubcValidationVerificationResponse.isSuccess()
							&& cubcValidationVerificationResponse.isAudited()) {
						if(StringUtils.isNotEmpty(cubcValidationVerificationResponse.getReason())){
							throw new TfrBusinessException("cubc:"+cubcValidationVerificationResponse.getReason());
						}else{
							throw new TfrBusinessException("该配载单已做出发付款确认，无法作废！");
						}
					}
				} else {
					throw new TfrBusinessException("根据来源单号调用cubc查询应付单是否已经核销失败！");
				}
		}

		if (ComnConst.ASSETS_OWNERSHIP_TYPE_LEASED.equals(billEntity
				.getVehicleOwnerShip())) {
			String holdingState = handOverBillService
					.queryHoldingState(vehicleAssembleNo);
			if (holdingState.equals("Y")) {
				throw new TfrBusinessException("配载单生成的临时租车已付款、已预提或在预提中，不允许修改！");
			}
		}
		// 外请车的车辆作废配载单时，如果此车牌号存在一条已报到的约车编号，不让作废此车牌号的配载单；系统校验提醒：‘此车牌号XX，约车编号AA，约车部门为XX，请先释放此车牌号，才能作废配载单’
		if (StringUtils.equals(billEntity.getVehicleOwnerShip(),
				ComnConst.ASSETS_OWNERSHIP_TYPE_LEASED)) {
			PassInviteApplyEntity inviteVehicleEntity = passInviteApplyService
					.queryPassInvitePrice(billEntity.getVehicleNo(), null);
			if (inviteVehicleEntity != null
					&& !StringUtils.equals(inviteVehicleEntity.getInviteNo(),
							billEntity.getInviteNo())) {
				throw new TfrBusinessException("此车牌号"
						+ billEntity.getVehicleNo() + "，约车编号"
						+ inviteVehicleEntity.getInviteNo() + "，约车部门为"
						+ inviteVehicleEntity.getApplyOrgCode()
						+ "，请先释放此车牌号，才能作废配载单");
			}
		}

		/*
		 * 将配载单内的交接单的状态修改为“已交接”
		 */
		// 获取配载单下的交接单
		List<QueryHandOverBillDto> handOverBillList = vehicleAssembleBillDao
				.queryHandOverBillListByVNo(vehicleAssembleNo);
		// 定义list，批量更新交接单状态
		List<UpdateHandOverBillStateDto> updateDtoList = new ArrayList<UpdateHandOverBillStateDto>();
		for (QueryHandOverBillDto dto : handOverBillList) {
			// 构造对象
			UpdateHandOverBillStateDto updateDto = new UpdateHandOverBillStateDto();
			// 交接单号
			updateDto.setHandOverBillNo(dto.getHandOverBillNo());
			// 目标状态：已交接
			updateDto
					.setTargetState(LoadConstants.HANDOVERBILL_STATE_FORMAL_HANDOVER);
			updateDto.setBeJump(FossConstants.YES);
			updateDtoList.add(updateDto);
		}
		// 调用交接单服务，更新交接单状态
		handOverBillService.updateHandOverBillState(updateDtoList);
		// 如果作废时车牌号为外请车，则调用结算接口，红冲，调用外请车约车接口，将状态改回未使用
		if (ComnConst.ASSETS_OWNERSHIP_TYPE_LEASED.equals(billEntity
				.getVehicleOwnerShip())) {
			this.callStlInterfaceWhenCancel(billEntity);
		}
		// 更新配载单状态
		updateVehicleAssembleBillStateByVNo(vehicleAssembleNo,
				LoadConstants.VEHICLEASSEMBLEBILL_STATE_ALREADY_CANCEL);

		// 记录作废日志
		VehicleAssembleBillOptLogEntity log = new VehicleAssembleBillOptLogEntity();
		this.addFieldValueForLog(log, billEntity);
		log.setOptContent("作废配载单");
		log.setOptType("作废配载单");
		log.setOptTime(new Date());
		List<VehicleAssembleBillOptLogEntity> logList = new ArrayList<VehicleAssembleBillOptLogEntity>();
		logList.add(log);
		vehicleAssembleBillDao.addOptLogList(logList);

		// 如果已生成任务车辆明细，则调用任务车辆接口
		if (FossConstants.YES.equals(billEntity.getBeCreateTaskTruck())) {
			truckTaskService.deleteTruckTaskbyAssembelBill(vehicleAssembleNo);
		}
		// 在途只装不卸的配载单需要删除 出发部门 到 中途装车部门的虚拟车辆任务 （如 A-C 需要在中途B装车到C
		// 那么A-B需要一个没有单据的车辆任务）
		if (StringUtils.equals(billEntity.getBeMidWayOnlyLoad(),
				FossConstants.YES)
				&& StringUtils.isNotBlank(billEntity.getOnTheWayDestOrgCode())) {
			// 删除中途只装不卸生成的车辆任务
			truckTaskService.deleteTruckTaskForMidLoad(
					billEntity.getOrigOrgCode(),
					billEntity.getOnTheWayDestOrgCode(),
					billEntity.getVehicleNo());
		}

		return true;
	}

	/**
	 * 私有方法，作废配载单时，如果车牌号为外请车，调用结算接口
	 * 
	 * @author 045923-foss-shiwei
	 * @date 2012-12-4 上午10:48:03
	 */
	private void callStlInterfaceWhenCancel(VehicleAssembleBillEntity baseEntity) {
		// 只有当配载单没有发生在途装卸或者发生在途装卸且该配载单最终到达的情况下才调用结算接口
		if ((StringUtils.equals(FossConstants.NO, baseEntity.getBeMidWayLoad()) && StringUtils
				.equals(FossConstants.NO, baseEntity.getBeInLTLCar()))
				|| (StringUtils.equals(FossConstants.YES,
						baseEntity.getBeMidWayLoad()) && StringUtils.equals(
						FossConstants.YES, baseEntity.getBeFinallyArrive()))) {
			if (baseEntity.getFeeTotal().compareTo(BigDecimal.ZERO) != 0) {
				// 更改约车信息为未使用状态
				inviteVehicleService.updateUseStatusForUnused(
						baseEntity.getInviteNo(), baseEntity.getVehicleNo());
				StlVehicleAssembleBillDto dto = new StlVehicleAssembleBillDto();

				CubcVehicleAssembleBillRequest request = new CubcVehicleAssembleBillRequest();
				// 车次号
				dto.setVehicleAssembleNo(baseEntity.getVehicleAssembleNo());
				request.setVehicleAssembleNo(baseEntity.getVehicleAssembleNo());
				// 判断车辆是否为合同车，dto车辆所有权类型
				VehicleAssociationDto vehicleInfo = vehicleService
						.queryVehicleAssociationDtoByVehicleNo(baseEntity
								.getVehicleNo());
				if (StringUtils.equals(FossConstants.YES,
						vehicleInfo.getBargainVehicle())) {
					dto.setVehicleOwnerShip(LoadConstants.VEHICLE_OWNERSHIP_BARGAIN_VEHICLE);
					request.setVehicleOwnerShip(LoadConstants.VEHICLE_OWNERSHIP_BARGAIN_VEHICLE);
				} else {
					dto.setVehicleOwnerShip(LoadConstants.VEHICLE_OWNERSHIP_LEASED_VEHICLE);
					request.setVehicleOwnerShip(LoadConstants.VEHICLE_OWNERSHIP_LEASED_VEHICLE);
				}
				// 车牌号
				dto.setVehicleNo(baseEntity.getVehicleNo());
				// 出发部门code
				dto.setOrigOrgCode(baseEntity.getOrigOrgCode());
				// 到达部门code
				dto.setDestOrgCode(baseEntity.getDestOrgCode());
				// 配载类型
				dto.setAssembleType(baseEntity.getAssembleType());
				// 车牌号
				request.setVehicleNo(baseEntity.getVehicleNo());
				// 出发部门code
				request.setOrigOrgCode(baseEntity.getOrigOrgCode());
				// 到达部门code
				request.setDestOrgCode(baseEntity.getDestOrgCode());
				// 配载类型
				request.setAssembleType(baseEntity.getAssembleType());
				// 员工工号
				request.setEmpCode(FossUserContext.getCurrentInfo()
						.getEmpCode());
				// 员工姓名
				request.setEmpName(FossUserContext.getCurrentInfo()
						.getEmpName());
				// 当前登录部门编码
				request.setCurrentDeptCode(FossUserContext.getCurrentInfo()
						.getCurrentDeptCode());
				// 当前登录部门名称
				request.setCurrentDeptName(FossUserContext.getCurrentInfo()
						.getCurrentDeptName());
				// 如果配载类型为整车，则得到整车运单号
				if (StringUtils.equals(
						LoadConstants.VEHICLE_ASSEMBLE_TYPE_CAR_LOAD,
						baseEntity.getAssembleType())) {
					// 获取交接单号
					String handOverBillNo = this
							.queryHandOverBillListByVNo(
									baseEntity.getVehicleAssembleNo()).get(0)
							.getHandOverBillNo();
					// 获取运单号
					String waybillNo = handOverBillService
							.queryHandOverBillDetailByNo(handOverBillNo).get(0)
							.getWaybillNo();
					dto.setWaybillNo(waybillNo);
					request.setWaybillNo(waybillNo);
				}
				//灰度DTO封装
				GrayParameterDto parDto = new GrayParameterDto();
				parDto.setSourceBillType(CUBCGrayUtil.SBType.PZ.getName());
				parDto.setSourceBillNos(new String[] {dto.getVehicleAssembleNo()});
				//调用灰度工具类方法
				VestResponse vestResponse = cubcUtil.getUcbcGrayData(parDto, new Throwable());
				if (CUBCGrayContants.SYSTEM_CODE_FOSS.equals(vestResponse.getVestBatchResult().get(0).getVestSystemCode())) {
					// 调用结算接口
					this.truckStowageService.disableTruckStowage(dto,
							FossUserContext.getCurrentInfo());
				} else {
					LOGGER.error("FOSS推送作废配载单,配载车次号" + request.getVehicleAssembleNo() + "给CUBC开始...");
					// 调用cubc接口
					CubcVehicleAssembleBillResponse response = fossToCubc.pushdisableTruckStowage(request);
					LOGGER.error(
							"FOSS推送作废配载单,配载车次号" + request.getVehicleAssembleNo() + "给CUBC结束;返回值是 :" + response);
					if (response == null) {
						throw new TfrBusinessException("ESB接口异常");
					}
					if ("0".equals(response.getResult())) {
						throw new TfrBusinessException("调用CUBC接口异常: " + response.getReason());
					}
				}
			} else {
				// 更改约车信息为未使用状态
				inviteVehicleService.updateUseStatusForUnused(
						baseEntity.getInviteNo(), baseEntity.getVehicleNo());
			}

		}
	}

	/**
	 * 调用外请车约车模块接口，传入外请车车牌号，获取外请车总费用
	 * 
	 * @author 045923-foss-shiwei
	 * @date 2012-12-1 下午2:37:05
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService#getTotalFeeFromInviteVehicleModule(java.lang.String)
	 */
	@Override
	public Object getTotalFeeFromInviteVehicleModule(String vehicleNo,
			String assembleType) {
		// 获取用车部门
		String orgCode = StringUtils.equals(LoadConstants.VEHICLE_ASSEMBLE_TYPE_CAR_LOAD, assembleType) ? null : this.querySuperiorOrgCode();
		PassInviteApplyEntity inviteVehicleEntity = passInviteApplyService.queryPassInvitePrice(vehicleNo,orgCode);
		if(inviteVehicleEntity == null || 
				inviteVehicleEntity.getInviteCost() == null ||
					(StringUtils.equals(LoadConstants.VEHICLE_ASSEMBLE_TYPE_CAR_LOAD, assembleType)
							&& inviteVehicleEntity.getInviteCost().compareTo(BigDecimal.ZERO) == 0)
				){
			throw new TfrBusinessException("车辆【" + vehicleNo + "】未约车或者未报到，暂无法使用！");
		}/*else if(StringUtils.equals(LoadConstants.VEHICLE_ASSEMBLE_TYPE_CAR_LOAD, assembleType)
				&& inviteVehicleEntity.getInviteCost().compareTo(BigDecimal.ZERO) == 0){
			throw new TfrBusinessException("车辆【" + vehicleNo + "】未约车或者未报到，暂无法使用！");
		}*/
		return inviteVehicleEntity;
	}

	/**
	 * 传入到达部门code，返回外场实体，如果不是外场，则返回null
	 * 
	 * @author 045923-foss-shiwei
	 * @date 2012-12-5 下午9:18:50
	 */
	@Override
	public OutfieldEntity queryOutfieldByOrgCode(String arriveDeptCode) {
		// 返回外场信息
		return outfieldService.queryOutfieldByOrgCode(arriveDeptCode);
	}

	/**
	 * 传入配载车次号，调用结算接口，看是否已做出发付款确认
	 * 
	 * @author 045923-foss-shiwei
	 * @param vehicleAssembleNo
	 *            配载车次号
	 * @date 2012-12-6 下午5:08:38
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService#isDepartPaymentConfirm(java.lang.String)
	 */
	@Override
	public boolean isDepartPaymentConfirm(String vehicleAssembleNo) {
		// 调用结算接口
		return billPayableService.queryBillPayableIsWriteOff(vehicleAssembleNo,
				null);
	}

	/**
	 * 提供给综合的接口，根据配载车次号，获取车次下的所有运单单号集合
	 * 
	 * @author 045923-foss-shiwei
	 * @date 2012-12-23 上午9:43:09
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService#queryWaybillNoListByVehicleAssembleNo(java.lang.String)
	 */
	@Override
	public List<String> queryWaybillNoListByVehicleAssembleNo(
			String vehicleAssembleNo) {
		if (StringUtils.isEmpty(vehicleAssembleNo)) {
			throw new TfrBusinessException("配载车次号不能为空！");
		}
		// 返回查询结果
		return vehicleAssembleBillDao
				.queryWaybillNoListByVehicleAssembleNo(vehicleAssembleNo);
	}

	/**
	 * 校验到达部门是否在该日发车计划中
	 * 
	 * @author 045923-foss-shiwei
	 * @date 2013-1-7 上午9:52:19
	 */
	@Override
	public int validateArriveDeptFromTruckPlan(String destOrgCode,
			Date leaveTime) {
		// 获取出发部门code
		String departOrgCode = this.querySuperiorOrgCode();
		Long count = truckDepartPlanDetailService.queryPlanDetailCount(
				departOrgCode, destOrgCode, leaveTime);
		// 如果返回的记录条数为0，则表明到达部门不在该日出发部门的发车计划中
		if (count.compareTo(0L) == 0) {
			throw new TfrBusinessException("到达部门不在本部门该日的发车计划中，请重新选择到达部门和发车日期！");
		}
		return FossConstants.SUCCESS;
	}

	/**
	 * 获取发车计划信息
	 * 
	 * @author 045923-foss-shiwei
	 * @date 2013-1-7 上午10:23:04
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService#validateVehicleNoFromTruckPlan(java.lang.String,
	 *      java.util.Date, java.lang.String)
	 */
	@Override
	public GetInfoWhenVehicleNoLostFocusDto validateVehicleNoFromTruckPlan(
			String destOrgCode, Date leaveTime, String vehicleNo,
			String transProperty) {
		// 获取出发部门code
		String departOrgCode = this.querySuperiorOrgCode();
		// 获取发车计划明细信息

		// 200968
		TruckDepartPlanDetailDto planDetail = null;
		VehicleAssociationDto vehicleAssociationDto = queryVehicleInfoByVehicleNo(vehicleNo);
		if (vehicleAssociationDto != null) {

			if (StringUtils.equals(vehicleAssociationDto.getVehicleType(),
					"vehicletype_trailer")
					&& !vehicleAssociationDto.getVehicleMotorcadeName().equals(
							"外请车")) {
				planDetail = truckDepartPlanDetailService
						.queryDepartPlanDetailByTrailerVehicleNo(departOrgCode,
								destOrgCode, vehicleNo, leaveTime);
			} else {
				// 获取发车计划明细信息
				planDetail = truckDepartPlanDetailService
						.queryLatestTruckDepartPlanDetail(departOrgCode,
								destOrgCode, vehicleNo, leaveTime);
			}
			if (planDetail == null) {
				throw new TfrBusinessException("车辆【" + vehicleNo
						+ "】不在该线路、该日期的发车计划中！");
			}
		}
		//sonar 218427 
		if(planDetail.getLineNo().isEmpty()){
			throw new TfrBusinessException("线路号为空");
		}
		//获取线路虚拟编码
		String virtualCode = planDetail.getLineNo();
		// 调用综合接口，获取线路实体信息
		LineEntity lineEntity = lineService.queryLineByVirtualCode(virtualCode);
		ExpressLineEntity explineEntity = expresslineService
				.queryLineByVirtualCode(virtualCode);

		GetInfoWhenVehicleNoLostFocusDto dtoInfo = new GetInfoWhenVehicleNoLostFocusDto();

		// 如果运输性质不为空，则根据不同的运输性质获取不同的运行时数
		/*
		 * if(StringUtils.isNotBlank(transProperty)){
		 * if(StringUtils.equals(LoadConstants
		 * .VEHICLE_ASSEMBLE_TRANSPROPERTY_LONG_DISTANCE, transProperty)){
		 * if(lineEntity.getCommonAging() != null){ //运行时数读取普货时效
		 * dtoInfo.setRunHours(new
		 * BigDecimal(lineEntity.getCommonAging()).divide(new
		 * BigDecimal(1000),3,BigDecimal.ROUND_UP)); } }else
		 * if(StringUtils.equals
		 * (LoadConstants.VEHICLE_ASSEMBLE_TRANSPROPERTY_TRUCK_LIKE_PLAN,
		 * transProperty)){ if(lineEntity.getFastAging() != null){ //运行时数读取卡货时效
		 * dtoInfo.setRunHours(new
		 * BigDecimal(lineEntity.getFastAging()).divide(new
		 * BigDecimal(1000),3,BigDecimal.ROUND_UP)); } } }
		 */
		if (StringUtils.isNotBlank(transProperty)) {
			if (StringUtils.equals(
					LoadConstants.VEHICLE_ASSEMBLE_TRANSPROPERTY_LONG_DISTANCE,
					transProperty)) {
				if (lineEntity.getCommonAging() != null) {
					// 运行时数读取普货时效
					dtoInfo.setRunHours(new BigDecimal(lineEntity.getCommonAging()).divide(new BigDecimal(LoadConstants.VOLUME_SIZE),LoadConstants.SONAR_NUMBER_3,BigDecimal.ROUND_UP));
				} else if (explineEntity.getCommonAging() != null) {
					// 运行时数读取普货时效
					dtoInfo.setRunHours(new BigDecimal(explineEntity.getCommonAging()).divide(new BigDecimal(LoadConstants.VOLUME_SIZE),LoadConstants.SONAR_NUMBER_3,BigDecimal.ROUND_UP));
				}
			} else if (StringUtils
					.equals(LoadConstants.VEHICLE_ASSEMBLE_TRANSPROPERTY_TRUCK_LIKE_PLAN,
							transProperty)
					|| StringUtils
							.equals(LoadConstants.VEHICLE_ASSEMBLE_TRANSPROPERTY_TRUCK_LIKE_PCP,
									transProperty)) {
				if (lineEntity.getFastAging() != null) {
					// 运行时数读取卡货时效
					dtoInfo.setRunHours(new BigDecimal(lineEntity.getFastAging()).divide(new BigDecimal(LoadConstants.VOLUME_SIZE),LoadConstants.SONAR_NUMBER_3,BigDecimal.ROUND_UP));
				} else if (explineEntity.getFastAging() != null) {
					// 运行时数读取卡货时效
					dtoInfo.setRunHours(new BigDecimal(explineEntity.getFastAging()).divide(new BigDecimal(LoadConstants.VOLUME_SIZE),LoadConstants.SONAR_NUMBER_3,BigDecimal.ROUND_UP));

				}
			}
		}

		// 司机code
		dtoInfo.setDriverCode(planDetail.getDriverCode1());
		// 司机姓名
		dtoInfo.setDriverName(planDetail.getDriverName1());
		// 司机电话
		dtoInfo.setDriverTel(planDetail.getDriverPhone1());
		// 卡车班次
		dtoInfo.setFrequencyNo(planDetail.getFrequencyNo());
		// 货柜编号
		dtoInfo.setContainerNo(planDetail.getContainerNo());
		// 挂牌号
		dtoInfo.setTrailerVehicleNo(planDetail.getTrailerVehicleNo());
		// 线路虚拟编码
		dtoInfo.setLineNo(virtualCode);
		return dtoInfo;
	}

	/**
	 * 调用结算接口，查询配载单是否已做出发付款确认
	 * 
	 * @author 045923-foss-shiwei
	 * @date 2013-1-9 上午10:28:16
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService#queryBillPayableIsWriteOff(java.lang.String)
	 */
	@Override
	public boolean queryBillPayableIsWriteOff(String vehicleAssembleNo) {
		// 调用结算接口

		return billPayableService.queryBillPayableIsWriteOff(vehicleAssembleNo,
				null);
	}

	/**
	 * 提供给综合查询的接口： 根据运单号查询该运单号的所有汽运配载记录，包括每次的出发时间和预计到达时间
	 * 
	 * @author 045923-foss-shiwei
	 * @date 2013-1-10 上午11:26:06
	 */
	@Override
	public List<WayBillAssembleDto> queryVehicleAssembleRecordsByWaybillNo(
			String waybillNo) {
		if (StringUtils.isBlank(waybillNo)) {
			throw new TfrBusinessException("查询运单配载记录时，运单号不能为空！");
		}
		return vehicleAssembleBillDao
				.queryVehicleAssembleRecordsByWaybillNo(waybillNo);
	}

	/**
	 * 获取两部门之间的公里数，乘以车辆每公里费用，计算运费
	 * 
	 * @author 045923-foss-shiwei
	 * @date 2013-1-22 下午2:31:15
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService#calculateFeeTotalForOwnTruck(java.lang.String,
	 *      java.lang.String, java.lang.String, java.math.BigDecimal)
	 */
	@Override
	public BigDecimal calculateFeeTotalForOwnTruck(String destOrgCode,
			String lineVirtualCode, BigDecimal eachKilometersFees) {
		if (StringUtils.isBlank(lineVirtualCode)) {
			// 业务异常，中断
			throw new TfrBusinessException("从发车计划获取线路虚拟编码失败，请确认发车计划线路信息是否正确！");
		}
		if (eachKilometersFees == null
				|| eachKilometersFees.compareTo(BigDecimal.ZERO) == 0) {
			// 业务异常，中断
			throw new TfrBusinessException("车辆每公里费用获取失败，请检查车辆基础资料是否正确！");
		}
		String departOrgCode = this.querySuperiorOrgCode();
		// 获取两部门公里数
		Long distance = lineService.queryDistanceBySourceTarget(
				lineVirtualCode, departOrgCode, destOrgCode);
		Long distance2 = expresslineService.queryDistanceBySourceTarget(
				lineVirtualCode, departOrgCode, destOrgCode);

		if (distance == null || distance.compareTo(0L) == 0) {
			if (distance2 == null || distance2.compareTo(0L) == 0) {
				// 业务异常，中断
				throw new TfrBusinessException(
						"获取出发部门、到达部门之间的公里数失败，请确认线路基础资料是否正确！");
			}
		}
		// 部门公里数*车辆每公里费用=总运费
		if (distance != null && distance.compareTo(0L) != 0) {
			return eachKilometersFees.multiply(new BigDecimal(distance));
		}
		// 部门公里数*车辆每公里费用=总运费
		if (distance2 != null && distance2.compareTo(0L) != 0) {
			return eachKilometersFees.multiply(new BigDecimal(distance2));
		}
		return null;
	}

	/**
	 * 用于验证选择的配载单在该车牌下是否还有其他的没选择
	 * 
	 * @author ibm-zhangyixin
	 * @date 2012-10-31 下午4:00:01
	 */
	@Override
	public Long checkPrintVehicleAssembleBill(
			VehicleAssembleBillVo vehicleAssembleBillVo) {
		// 返回查询结果
		return vehicleAssembleBillDao
				.checkPrintVehicleAssembleBill(vehicleAssembleBillVo);
	}

	/**
	 * @Title: checkPrintTruckTask
	 * @Description: 校验选择了多少任务车辆
	 * @param vehicleAssembleBillVo
	 * @return
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService#checkPrintTruckTask(com.deppon.foss.module.transfer.load.api.shared.vo.VehicleAssembleBillVo)
	 * @author: ibm-zhangyixin
	 * @throws Date
	 *             :2013-6-1下午11:52:20
	 */
	@Override
	public Long checkPrintTruckTask(VehicleAssembleBillVo vehicleAssembleBillVo) {
		// 返回查询结果
		return vehicleAssembleBillDao
				.checkPrintTruckTask(vehicleAssembleBillVo);
	}

	/**
	 * 根据配置车次号找出当前配载单下所有的交接单号, 打印时用到
	 * 
	 * @author ibm-zhangyixin
	 * @date 2012-11-20 下午1:47:10
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService#queryHandOverBillNosByVehicleAssembleNo(java.lang.String)
	 */
	@Override
	public List<String> queryHandOverBillNosByVehicleAssembleNo(
			List<String> vehicleAssembleNos) {
		// 交接单号
		List<String> handOverBillNos = new ArrayList<String>();
		for (String vehicleAssembleNo : vehicleAssembleNos) {
			// 根据配置车次号找出当前配载单下所有的交接单号, 打印时用到
			List<QueryHandOverBillDto> queryHandOverBillDtos = queryHandOverBillListByVNo(vehicleAssembleNo);
			// 循环添加
			for (QueryHandOverBillDto queryHandOverBillDto : queryHandOverBillDtos) {
				// 根据配置车次号找出当前配载单下所有的交接单号, 打印时用到
				handOverBillNos.add(queryHandOverBillDto.getHandOverBillNo());
			}
		}
		// 返回结果
		return handOverBillNos;
	}

	/**
	 * 根据运输类型的不同来获取运行时数
	 * 
	 * @author 045923-foss-shiwei
	 * @date 2013-2-25 下午3:31:43
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService#queryRunHoursByTransProperty(java.lang.String,
	 *      java.util.Date, java.lang.String, java.lang.String)
	 */
	@Override
	public BigDecimal queryRunHoursByTransProperty(String destOrgCode,
			Date leaveTime, String vehicleNo, String transProperty) {
		// 获取出发部门code
		String departOrgCode = this.querySuperiorOrgCode();
		// 获取发车计划明细

		TruckDepartPlanDetailDto planDetail = null;// 200968
		VehicleAssociationDto vehicleAssociationDto = queryVehicleInfoByVehicleNo(vehicleNo);
		if (vehicleAssociationDto != null) {
			if (StringUtils.equals(vehicleAssociationDto.getVehicleType(),
					"vehicletype_trailer")) {
				planDetail = truckDepartPlanDetailService
						.queryDepartPlanDetailByTrailerVehicleNo(departOrgCode,
								destOrgCode, vehicleNo, leaveTime);
			} else {
				planDetail = truckDepartPlanDetailService
						.queryLatestTruckDepartPlanDetail(departOrgCode,
								destOrgCode, vehicleNo, leaveTime);
			}
			if (planDetail == null) {
				// 业务异常、中断操作
				throw new TfrBusinessException("车辆【" + vehicleNo
						+ "】不在该线路、该日期的发车计划中！");
			}
		}
		//sonar 218427 
		if(planDetail.getLineNo().isEmpty()){
			throw new TfrBusinessException("线路号为空");
		}
		//线路虚拟编码
		String virtualCode = planDetail.getLineNo();
		// 线路实体
		LineEntity lineEntity = lineService.queryLineByVirtualCode(virtualCode);
		ExpressLineEntity explineEntity = expresslineService
				.queryLineByVirtualCode(virtualCode);

		// 根据不同的运输性质获取不同的运行时数
		if (StringUtils.equals(
				LoadConstants.VEHICLE_ASSEMBLE_TRANSPROPERTY_LONG_DISTANCE,
				transProperty)) {
			if (lineEntity.getCommonAging() != null) {
				// 普货时效为运行时数
				return new BigDecimal(lineEntity.getCommonAging()).divide(new BigDecimal(LoadConstants.VOLUME_SIZE),LoadConstants.SONAR_NUMBER_3,BigDecimal.ROUND_UP);
			} else if (explineEntity.getCommonAging() != null) {
				// 普货时效为运行时数
				return new BigDecimal(explineEntity.getCommonAging()).divide(new BigDecimal(LoadConstants.VOLUME_SIZE),LoadConstants.SONAR_NUMBER_3,BigDecimal.ROUND_UP);
			}
		} else if (StringUtils.equals(
				LoadConstants.VEHICLE_ASSEMBLE_TRANSPROPERTY_TRUCK_LIKE_PLAN,
				transProperty)
				|| StringUtils
						.equals(LoadConstants.VEHICLE_ASSEMBLE_TRANSPROPERTY_TRUCK_LIKE_PCP,
								transProperty)) {
			if (lineEntity.getFastAging() != null) {
				// 卡货时效为运行时数
				return new BigDecimal(lineEntity.getFastAging()).divide(new BigDecimal(LoadConstants.VOLUME_SIZE),LoadConstants.SONAR_NUMBER_3,BigDecimal.ROUND_UP);
			} else if (explineEntity.getFastAging() != null) {
				// 卡货时效为运行时数
				return new BigDecimal(explineEntity.getFastAging()).divide(new BigDecimal(LoadConstants.VOLUME_SIZE),LoadConstants.SONAR_NUMBER_3,BigDecimal.ROUND_UP);
			}
		}
		return null;
	}

	/**
	 * 修改时根据运输类型的不同来获取运行时数
	 * 
	 * @author 105869-foss-heyongdong
	 * @date 2013-7-29 下午3:31:43
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService#queryModifyRunHoursByTransProperty(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public BigDecimal queryModifyRunHoursByTransProperty(String vehicleNo,
			String vehicleAssembleNo, String transProperty) {
		// 获取发车计划明细Id
		List<VehicleAssembleBillEntity> vehicleAssembleBill = queryVehicleAssembleBillByNo(vehicleAssembleNo);
		String truckDepartPlanDetailId = vehicleAssembleBill.get(0)
				.getTruckDepartPlanDetailId();
		if (StringUtils.isBlank(truckDepartPlanDetailId)) {
			// 业务异常、中断操作
			throw new TfrBusinessException("车辆【" + vehicleNo
					+ "】不在该线路、该日期的发车计划中！");
		}
		// 状态
		String status = "Y";
		// 获取发车计划明细
		TruckDepartPlanDetailDto planDetail = truckDepartPlanDetailService
				.queryTruckDepartPlanDetailById(truckDepartPlanDetailId, status);
		if (planDetail == null) {
			// 业务异常、中断操作
			throw new TfrBusinessException("车辆【" + vehicleNo
					+ "】不在该线路、该日期的发车计划中！");
		}
		// 线路虚拟编码
		String virtualCode = planDetail.getLineNo();
		// 线路实体
		LineEntity lineEntity = lineService.queryLineByVirtualCode(virtualCode);
		ExpressLineEntity explineEntity = expresslineService
				.queryLineByVirtualCode(virtualCode);

		// 根据不同的运输性质获取不同的运行时数
		if (StringUtils.equals(
				LoadConstants.VEHICLE_ASSEMBLE_TRANSPROPERTY_LONG_DISTANCE,
				transProperty)) {
			if (lineEntity.getCommonAging() != null) {
				// 普货时效为运行时数
				return new BigDecimal(lineEntity.getCommonAging()).divide(new BigDecimal(LoadConstants.VOLUME_SIZE),LoadConstants.SONAR_NUMBER_3,BigDecimal.ROUND_UP);
			} else if (explineEntity.getCommonAging() != null) {
				// 普货时效为运行时数
				return new BigDecimal(explineEntity.getCommonAging()).divide(new BigDecimal(LoadConstants.VOLUME_SIZE),LoadConstants.SONAR_NUMBER_3,BigDecimal.ROUND_UP);
			}
		} else if (StringUtils.equals(
				LoadConstants.VEHICLE_ASSEMBLE_TRANSPROPERTY_TRUCK_LIKE_PLAN,
				transProperty)
				|| StringUtils
						.equals(LoadConstants.VEHICLE_ASSEMBLE_TRANSPROPERTY_TRUCK_LIKE_PCP,
								transProperty)) {
			if (lineEntity.getFastAging() != null) {
				// 卡货时效为运行时数
				return new BigDecimal(lineEntity.getFastAging()).divide(new BigDecimal(LoadConstants.VOLUME_SIZE),LoadConstants.SONAR_NUMBER_3,BigDecimal.ROUND_UP);
			} else if (explineEntity.getFastAging() != null) {
				// 卡货时效为运行时数
				return new BigDecimal(explineEntity.getFastAging()).divide(new BigDecimal(LoadConstants.VOLUME_SIZE),LoadConstants.SONAR_NUMBER_3,BigDecimal.ROUND_UP);
			}
		}
		return null;
	}

	/**
	 * 根据配载车次号导出运单
	 * 
	 * @author 045923-foss-shiwei
	 * @date 2013-3-15 下午3:39:41
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService#getWayBillExcelInputStream(java.lang.String)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getWayBillExcelInputStream(String vehicleAssembleNo) {
		VehicleAssembleBillEntity baseEntity = this
				.queryVehicleAssembleBillByNo(vehicleAssembleNo).get(0);
		if (baseEntity == null) {
			throw new TfrBusinessException("配载单不存在或者已经被作废！");
		}
		// 获取配载单下的交接单
		List<QueryHandOverBillDto> handOverBillNos = this
				.queryHandOverBillListByVNo(vehicleAssembleNo);
		// 定义总重量、总体积、总票数、总件数
		BigDecimal totalWeight = BigDecimal.ZERO, totalVolume = BigDecimal.ZERO;
		// 总票数，总件数
		Integer totalWaybill = 0, totalPieces = 0;
		List<List<String>> rowList = new ArrayList<List<String>>();
		// 遍历交接单、获取运单
		for (QueryHandOverBillDto no : handOverBillNos) {
			List<HandOverBillDetailEntity> wayBillList = handOverBillService
					.queryHandOverBillDetailByNo(no.getHandOverBillNo());
			List<List<String>> innerList = new ArrayList<List<String>>();
			for (HandOverBillDetailEntity waybill : wayBillList) {
				List<String> columnList = new ArrayList<String>();
				// 运单号
				columnList.add(waybill.getWaybillNo());
				// 运输性质
				columnList.add(waybill.getTransProperty());
				// 已配件数
				columnList.add(waybill.getPieces().toString());
				// 已配重量
				columnList.add(waybill.getWeight().toString());
				// 实际重量
				columnList.add(waybill.getWeightAc().toString());
				// 已配体积
				columnList.add(waybill.getCubage().toString());
				// 实际体积
				columnList.add(waybill.getCubageAc().toString());
				// 备注
				columnList.add(waybill.getNote());
				// 货物名称
				columnList.add(waybill.getGoodsName());
				// 包装
				columnList.add(waybill.getPacking());
				// 提货网点
				columnList.add(waybill.getArriveDept());
				// 收货部门
				columnList.add(waybill.getReceiveOrgName());
				// 运单备注
				columnList.add(waybill.getWaybillNote());
				// 是否贵重物品
				if (StringUtils.equals(waybill.getIsPreciousGoods(),
						FossConstants.YES)) {
					columnList.add("是");
				} else {
					columnList.add("否");
				}
				innerList.add(columnList);
			}
			// 累加重量
			totalWeight = totalWeight.add(no.getWeightTotal());
			// 体积
			totalVolume = totalVolume.add(no.getVolumeTotal());
			// 票数
			totalWaybill += no.getWaybillQtyTotal();
			// 件数
			totalPieces += no.getGoodsQtyTotal();
			rowList.addAll(innerList);
		}
		List<List<String>> outList = new ArrayList<List<String>>();
		List<String> staInfo = new ArrayList<String>();
		staInfo.add("总体积：" + totalVolume + "方");
		staInfo.add("总重量：" + totalWeight + "千克");
		staInfo.add("总件数：" + totalPieces + "件");
		staInfo.add("总票数：" + totalWaybill + "票");
		outList.add(staInfo);
		rowList.addAll(outList);
		// 定义表头
		String[] rowHeads = { "运单号", "运输性质", "已配件数", "已配重量", "实际重量", "已配体积",
				"实际体积", "备注", "货物名称", "包装", "提货网点", "收货部门", "运单备注", "是否贵重物品" };
		// 导出资源类
		ExportResource exportResource = new ExportResource();
		// 设置导出文件的表头
		exportResource.setHeads(rowHeads);
		// 设置导出数据
		exportResource.setRowList(rowList);
		// 导出设置
		ExportSetting exportSetting = new ExportSetting();
		// 设置sheetname
		exportSetting.setSheetName("运单列表");
		// 设置sheet行数
		exportSetting.setSize(rowList.size() + 1);
		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		// 获取输入流
		InputStream excelStream = objExporterExecutor.exportSync(
				exportResource, exportSetting);

		// 文件名
		String fileName = "车次清单列表" + "-配载车次号：" + vehicleAssembleNo;
		try {

			String agent = (String) ServletActionContext.getRequest()
					.getHeader("USER-AGENT");

			if (agent != null && agent.indexOf("MSIE") == -1) {
				fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
			} else {
				fileName = URLEncoder.encode(fileName, "UTF-8");
			}

		} catch (UnsupportedEncodingException e) {
			throw new TfrBusinessException(e.getMessage());
		} // 设置fileName
		List list = new ArrayList();
		list.add(fileName);
		list.add(excelStream);
		return list;

	}

	/**
	 * 导出配载单
	 * 
	 * @author 045923-foss-shiwei
	 * @date 2013-5-29 下午5:32:41
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService#getVehicleAssembleBillExcelInputStream(com.deppon.foss.module.transfer.load.api.shared.dto.QueryVehicleAssembleBillConditionDto)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getVehicleAssembleBillExcelInputStream(
			QueryVehicleAssembleBillConditionDto conditionDto) {
		// 查询交接单
		String orgCode = this.querySuperiorOrgCode();
		conditionDto.setCurrentDeptCode(orgCode);
		List<QueryVehicleAssembleBillDto> billList = vehicleAssembleBillDao
				.queryVehicleAssembleBillListNoPaging(conditionDto);
		List<List<String>> rowList = new ArrayList<List<String>>();
		// 如果查询结果为空，则导出空文件
		if (CollectionUtils.isEmpty(billList)) {
			List<String> columnList = new ArrayList<String>();
			rowList.add(columnList);
		}
		for (QueryVehicleAssembleBillDto bill : billList) {
			List<String> columnList = new ArrayList<String>();
			// 配载车次号
			columnList.add(bill.getVehicleAssembleNo());
			// 不导出已作废的配载单
			if (bill.getState() == LoadConstants.VEHICLEASSEMBLEBILL_STATE_ALREADY_CANCEL) {
				continue;
			} else {
				String state = this.convertStateNumber2Value(bill.getState());
				// 配载单状态
				columnList.add(state);
			}

			// 总运费
			columnList.add(bill.getFeeTotal().toString());
			// 配载类型
			if (StringUtils.equals(bill.getAssembleType(),
					LoadConstants.VEHICLE_ASSEMBLE_TYPE_OWNER_LINE)) {
				columnList.add("专线");
			} else {
				columnList.add("整车");
			}
			// 班次
			columnList.add(bill.getFrequencyNo());
			// 配载部门
			columnList.add(bill.getOrigOrgName());
			// 发车时间
			columnList.add(DateUtils.convert(bill.getDepartTime(),
					DateUtils.DATE_TIME_FORMAT));
			// 到达部门
			columnList.add(bill.getDestOrgName());
			// 到达时间
			columnList.add(DateUtils.convert(bill.getArriveTime(),
					DateUtils.DATE_TIME_FORMAT));
			// 车牌号
			columnList.add(bill.getVehicleNo());
			// 车辆所有权类别
			if (StringUtils.equals(bill.getVehicleOwnerShip(), "Company")) {
				columnList.add("公司车");
			} else {
				columnList.add("外请车");
			}

			// 装载率
			columnList.add(bill.getLoadingRate());
			// 配载日期
			columnList.add(DateUtils.convert(bill.getCreateDate(),
					DateUtils.DATE_TIME_FORMAT));
			// 预计到达时间
			columnList.add(DateUtils.convert(bill.getPlanArriveTime(),
					DateUtils.DATE_TIME_FORMAT));
			// 制单人
			columnList.add(bill.getCreateUserName());
			// 主驾驶姓名
			columnList.add(bill.getDriverName());
			// 司机电话
			columnList.add(bill.getDriverMobilePhone());
			// 修改人
			columnList.add(bill.getModifyUserName());
			// 修改时间
			columnList.add(DateUtils.convert(bill.getModifyDate(),
					DateUtils.DATE_TIME_FORMAT));
			// 净空
			columnList.add(bill.getRatedClearance().toString());
			// 载重
			columnList.add(bill.getRatedLoad().toString());
			// 司机自带货量（m³）
			columnList.add(bill.getDriverOfVolumn() == null ? null : bill
					.getDriverOfVolumn().toString());
			// 司机自带货量（kg）
			columnList.add(bill.getDriverOfWeight() == null ? null : bill
					.getDriverOfWeight().toString());
			// 适用净空
			columnList.add(bill.getApplicationRatedClearance() == null ? null
					: bill.getApplicationRatedClearance().toString());
			// 适用载重
			columnList.add(bill.getApplicationRatedLoad() == null ? null : bill
					.getApplicationRatedLoad().toString());

			// 总票数
			if (bill.getWaybillQtyTotal() == null) {
				columnList.add(ZERO);
			} else {
				columnList.add(bill.getWaybillQtyTotal().toString());
			}
			// 总件数
			if (bill.getGoodsQtyTotal() == null) {
				columnList.add(ZERO);
			} else {
				columnList.add(bill.getGoodsQtyTotal().toString());
			}
			// 总重量
			if (bill.getWeightTotal() == null) {
				columnList.add(ZERO);
			} else {
				columnList.add(bill.getWeightTotal().toString());
			}
			// 总体积
			if (bill.getVolumeTotal() == null) {
				columnList.add(ZERO);
			} else {
				columnList.add(bill.getVolumeTotal().toString());
			}
			// 空运票数
			if (bill.getWaybillQtyAF() == null) {
				columnList.add(ZERO);
			} else {
				columnList.add(bill.getWaybillQtyAF().toString());
			}
			// 空运重量
			if (bill.getGoodsWeightAF() == null) {
				columnList.add(ZERO);
			} else {
				columnList.add(bill.getGoodsWeightAF().toString());
			}
			// 空运体积
			if (bill.getGoodsVolumeAF() == null) {
				columnList.add(ZERO);
			} else {
				columnList.add(bill.getGoodsVolumeAF().toString());
			}
			// 卡航票数
			if (bill.getWaybillQtyFLF() == null) {
				columnList.add(ZERO);
			} else {
				columnList.add(bill.getWaybillQtyFLF().toString());
			}
			// 卡航重量
			if (bill.getGoodsWeightFLF() == null) {
				columnList.add(ZERO);
			} else {
				columnList.add(bill.getGoodsWeightFLF().toString());
			}
			// 卡航体积
			if (bill.getGoodsVolumeFLF() == null) {
				columnList.add(ZERO);
			} else {
				columnList.add(bill.getGoodsVolumeFLF().toString());
			}
			// 城运票数
			if (bill.getWaybillQtyFSF() == null) {
				columnList.add(ZERO);
			} else {
				columnList.add(bill.getWaybillQtyFSF().toString());
			}
			// 城运重量
			if (bill.getGoodsWeightFSF() == null) {
				columnList.add(ZERO);
			} else {
				columnList.add(bill.getGoodsWeightFSF().toString());
			}
			// 城运体积
			if (bill.getGoodsVolumeFSF() == null) {
				columnList.add(ZERO);
			} else {
				columnList.add(bill.getGoodsVolumeFSF().toString());
			}
			// 信息部名称
			columnList.add(bill.getApplyPath());
			// 信息部编码 310248
			columnList.add(bill.getMinistryinformationCode());
			// 车主姓名
			columnList.add(bill.getTruckOwnerName());
			// 是否大车小用
			if (bill.getBeCarSmallUsed() == null) {
				columnList.add(null);
			} else if (StringUtils.equals(bill.getBeCarSmallUsed(),
					FossConstants.NO)) {
				columnList.add("否");
			} else {
				columnList.add("是");
			}
			// 备注
			columnList.add(bill.getNote());
			rowList.add(columnList);
		}
		// 定义表头
		String[] rowHeads = { "配载车次号", "状态", "总运费", "配载类型", "车次", "配载部门",
				"发车时间", "到达部门", "到达时间", "车牌号", "车辆所有权类别", "装载率", "配载日期",
				"预计到达时间", "制单人", "主驾驶姓名", "司机电话", "修改人", "修改时间", "净空", "载重",
				"司机自带货量（m³）", "司机自带货量（kg）", "适用净空", "适用载重", "总票数", "总件数",
				"总重量", "总体积", "空运票数", "空运重量", "空运体积", "卡航票数", "卡航重量", "卡航体积",
				"城运票数", "城运重量", "城运体积", "信息部名称", "信息部编码", "车主姓名", "是否大车小用",
				"备注" };
		// 导出资源类
		ExportResource exportResource = new ExportResource();
		// 设置导出文件的表头
		exportResource.setHeads(rowHeads);
		// 设置导出数据
		exportResource.setRowList(rowList);
		// 导出设置
		ExportSetting exportSetting = new ExportSetting();
		// 设置sheetname
		exportSetting.setSheetName("配载单列表");
		// 设置sheet行数
		exportSetting.setSize(billList.size() + 1);
		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		// 获取输入流
		InputStream excelStream = objExporterExecutor.exportSync(
				exportResource, exportSetting);

		// 文件名
		String fileName = "配载单";
		try {
			String agent = (String) ServletActionContext.getRequest()
					.getHeader("USER-AGENT");

			if (agent != null && agent.indexOf("MSIE") == -1) {
				fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
			} else {
				fileName = URLEncoder.encode(fileName, "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			// 抛出业务异常
			throw new TfrBusinessException(e.getMessage());
		} // 设置fileName
		List list = new ArrayList();
		list.add(fileName);
		list.add(excelStream);
		// 返回action
		return list;
	}

	/**
	 * 私有方法，转换状态编码和值
	 * 
	 * @author 045923-foss-shiwei
	 * @date 2013-5-29 下午5:41:10
	 */
	private String convertStateNumber2Value(int state) {
		switch (state) {
		case LoadConstants.VEHICLEASSEMBLEBILL_STATE_NOT_DEPART:
			return "未发车";
		case LoadConstants.VEHICLEASSEMBLEBILL_STATE_ALREADY_DEPART:
			return "已出发";
		case LoadConstants.VEHICLEASSEMBLEBILL_STATE_ALREADY_ARRIVE:
			return "已到达";
		default:
			return "已作废";
		}
	}

	/**
	 * 获取当前部门的上级部门code
	 * 
	 * @author 045923-foss-shiwei
	 * @date 2013-4-2 下午4:48:53
	 */
	@Override
	public String querySuperiorOrgCode() {
		String orgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
		OrgAdministrativeInfoEntity superEntity = loadService
				.querySuperOrgByOrgCode(orgCode);
		if (superEntity == null || StringUtils.isBlank(superEntity.getCode())) {
			LOGGER.error("###################根据部门（code：" + orgCode
					+ "）获取上级营业部、派送部、总调、外场、结果为空！");
			throw new TfrBusinessException("获取本部门的上级组织失败(包括营业部、派送部、总调、外场)！");
		} else {
			return superEntity.getCode();
		}
	}

	/**
	 * @Title: checkCarriageContractPrinted
	 * @Description: 判断运输合同是否重复打印
	 * @param vehicleAssembleNo
	 * @return
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService#checkCarriageContractPrinted(java.lang.String)
	 * @author: ibm-zhangyixin
	 * @throws Date
	 *             :2013-4-19下午3:26:48
	 */
	@Override
	public int checkCarriageContractPrinted(String vehicleAssembleNo) {
		MotorstowagebillrecordEntity motorstowagebillrecord = vehicleAssembleBillDao
				.getMotorstowagebillrecordByVehicleAssembleNo(vehicleAssembleNo);
		return motorstowagebillrecord == null ? 0 : 1;
	}

	/**
	 * 修改date类型的时分秒
	 * 
	 * @author 045923-foss-shiwei
	 * @date 2013-6-2 下午4:37:51
	 */
	private Date changeHoursForDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		return cal.getTime();
	}

	/**
	 * 私有方法，生成配载车次号
	 * 
	 * @author 045923-foss-shiwei
	 * @date 2013-7-2 上午8:57:56
	 */
	private String generateVehicleAssembleNo(String origOrgCode,
			String destOrgCode, String leaveTime, String beCarLoad,
			String bePackage) {
		String newCode = "";
		long newSeq = 0;
		// 获取到达组织code的list
		List<String> destOrgCodeList = queryDestOrgCodeListForVehicleAssembleNo(destOrgCode);
		// 按照传入参数，查询对应配载车次记录的最后单号
		String oldSn = vehicleAssembleBillDao.queryLatestVehicleAssembleNo(
				origOrgCode, destOrgCodeList, leaveTime);
		// 若未返回记录，则单号为01
		if (StringUtils.isBlank(oldSn)) {
			newSeq = 1;
		} else {
			// 若返回了1条记录，则在已有单号基础上+1
			String interceptedNo = StringUtils.right(oldSn, 2);
			try {
				newSeq = Long.valueOf(interceptedNo).longValue() + 1;
			} catch (NumberFormatException e) {
				throw new TfrBusinessException(
						TfrBusinessException.ILLEGAL_ARGUMENT_EXCEPTION,
						e.getMessage(), e);
			}
		}

		String newSn = String.format("%0" + 2 + "d", newSeq);

		OutfieldEntity startOrg = outfieldService
				.queryOutfieldByOrgCode(origOrgCode);
		OutfieldEntity endOrg = outfieldService
				.queryOutfieldByOrgCode(destOrgCode);
		if (null == startOrg || null == endOrg) {
			throw new TfrBusinessException("配载单的“到达部门”或者“出发部门”不是外场！");
		}
//		如果是整车类型，则需要加入整车参数前缀
		if(FossConstants.YES.equals(beCarLoad)){
			newCode = startOrg.getSimpleCode() + endOrg.getSimpleCode() + StringUtils.right(leaveTime, LoadConstants.SONAR_NUMBER_6) + TransferDictionaryConstants.TOTAL_TRUCK_PREFIX + newSn;
		}else{
			newCode = startOrg.getSimpleCode() + endOrg.getSimpleCode() + StringUtils.right(leaveTime, LoadConstants.SONAR_NUMBER_6) + newSn;
		}
		if (StringUtils.equals("KDY", bePackage)) {
			newCode = "KDY-" + newCode;
		} else {
			if (StringUtils.equals(FossConstants.YES, bePackage)) {
				newCode = PACKAGE_ASSEMBLE_PREFIX + newCode;
			}
		}

		return newCode;
	}

	/**
	 * 私有方法，获取到达外场下所有的营业部code，如果非整车，则直接返回到达外场code
	 * 
	 * @author 045923-foss-shiwei
	 * @date 2013-5-17 上午10:20:59
	 */
	private List<String> queryDestOrgCodeListForVehicleAssembleNo(
			String destOutfieldCode) {
		List<String> list = new ArrayList<String>();
		list.add(destOutfieldCode);

		LineEntity con = new LineEntity();
		con.setActive(FossConstants.ACTIVE);
		// 修改配载单车次号生成重复问题 过滤掉不是默认外场
		con.setIsDefault(FossConstants.YES);
		con.setLineSort(DictionaryValueConstants.BSE_LINE_SORT_TARGET);
		con.setOrginalOrganizationCode(destOutfieldCode);
		List<LineEntity> lineList = lineService
				.querySimpleLineListByCondition(con);
		for (LineEntity entity : lineList) {
			list.add(entity.getDestinationOrganizationCode());
		}
		return list;
	}

	/**
	 * @param signDetailService
	 *            the signDetailService to set Date:2013-7-10下午3:15:23
	 */
	public void setSignDetailService(ISignDetailService signDetailService) {
		this.signDetailService = signDetailService;
	}

	/**
	 * 通过运单查询配载单
	 * 
	 * @author 105869-foss-heyongdong
	 * @date 2013-9-6 15:58:30
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService#queryVehicleAssemblyBillByWaybillNo(java.lang.String)
	 * */
	@Override
	public boolean queryVehicleAssemblyBillByWaybillNo(String waybillNo) {
		// 根据运单查询配载单
		List<QueryVehicleAssembleBillDto> vehicleAssembills = vehicleAssembleBillDao
				.queryVehicleAssemblyBillByWaybillNo(waybillNo);
		// 标示
		int counts = 0;

		if (CollectionUtils.isNotEmpty(vehicleAssembills)
				&& vehicleAssembills.size() > 0) {
			// 如果分批配载则出发站目的站必须一致
			if (vehicleAssembills.size() > 1) {
				// 出发站
				String orgCode = vehicleAssembills.get(0).getOrigOrgCode();
				// 目的站
				String destCode = vehicleAssembills.get(0).getDestOrgCode();
				for (QueryVehicleAssembleBillDto vehicleAssembill : vehicleAssembills) {
					// 出发站
					String orgCode1 = vehicleAssembill.getOrigOrgCode();
					// 目的站
					String destCode1 = vehicleAssembill.getDestOrgCode();
					if (StringUtils.equals(orgCode, orgCode1)
							&& StringUtils.equals(destCode, destCode1)) {
						counts += 1;
					} else {
						return false;
					}
					if (vehicleAssembills.size() == counts) {
						return true;
					}
				}
			} else {
				return true;
			}
		}

		return false;
	}

	/**
	 * 通过运单查询配载单
	 * 
	 * @author 105869-foss-heyongdong
	 * @date 2013-9-6 15:58:30
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService#queryVehicleAssembleBillInfoByBillNo(java.lang.String)
	 * */
	@Override
	public QueryVehicleAssembleBillDto queryVehicleAssembleBillInfoByBillNo(
			String vehicleAssembillNo) {
		QueryVehicleAssembleBillDto queryVehicleAssembleBill = null;
		List<QueryVehicleAssembleBillDto> vehicleAssembills = vehicleAssembleBillDao
				.queryVehicleAssembleBillInfoByBillNo(vehicleAssembillNo);
		if (vehicleAssembills != null && vehicleAssembills.size() > 0) {
			queryVehicleAssembleBill = vehicleAssembills.get(0);
		}
		return queryVehicleAssembleBill;
	}

	/**
	 * 校验当前线路是否奖励线路
	 * 
	 * @author 105869-foss-heyongdong
	 * @date 2013-9-6 15:58:30
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService#checkBeAgingLine(java.lang.String)
	 * */
	@Override
	public RewardOrPunishAgreementDto checkBeAgingLine(String destDeptCode) {
		RewardOrPunishAgreementDto rewardPubishDto = new RewardOrPunishAgreementDto();
		// 获取当前用户、部门等
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		/*
		 * 出发部门/当前部门
		 */
		OrgAdministrativeInfoEntity orgEntity = loadService
				.querySuperiorOrgByOrgCode(currentInfo.getCurrentDeptCode());
		if (orgEntity == null) {
			throw new TfrBusinessException("获取本部门的上级组织失败(包括营业部、派送部、外场、总调)！");
		}
		// 根据出发部门到达部门调用综合接口，判断是否时效线路
		LineEntity lineEntity = lineService.queryLineEntityByCondition(
				orgEntity.getCode(), destDeptCode);

		if (lineEntity == null) {
			throw new TfrBusinessException("获取可奖路线基础资料失败！");
		}
		String isNorewardPunish = lineEntity.getIsNorewardPunish();
		if (StringUtils.isNotBlank(isNorewardPunish)
				&& StringUtils.equals(isNorewardPunish, FossConstants.YES)) {
			// throw new TfrBusinessException("该路线不是可奖罚路线，不能签署时效条款");
			// 只可罚不可奖
			rewardPubishDto.setBeAgingLine(true);
		} else {
			// 可奖罚
			rewardPubishDto.setBeAgingLine(false);
		}

		return rewardPubishDto;
	}

	/**
	 * 查询奖罚信息
	 * 
	 * @author foss-heyongdong
	 * @date 2013年12月19日 09:34:22
	 * @return RewardOrPunishAgreementDto
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService#queryRewardOrPunishAgreement(java.lang.String)
	 */
	@Override
	public RewardOrPunishAgreementDto queryRewardOrPunishAgreement(
			String vehicleAssembleNo) {
		// 查询奖罚最该金额
		RewardOrPunishAgreementDto rewardOrPunishDto = new RewardOrPunishAgreementDto();
		// 最高奖励上限
		BigDecimal rewardMaxMoney = new BigDecimal(0);
		// 最高罚款上限
		BigDecimal fineMaxMoney = new BigDecimal(0);
		// 查询奖罚明细
		List<RewardOrPunishAgreementEntity> rewardOrPunishDatil = vehicleAssembleBillDao
				.queryRewardOrPunishDetail(vehicleAssembleNo);
		for (int i = 0; i < rewardOrPunishDatil.size(); i++) {
			RewardOrPunishAgreementEntity temp = rewardOrPunishDatil.get(i);
			String[] timeW = temp.getTimeLimit().split("-");
			if (timeW.length == 1) {
				rewardOrPunishDatil.get(i).setTimeDown(
						Integer.valueOf(timeW[0]));
			} else if (timeW.length == 2) {
				rewardOrPunishDatil.get(i).setTimeDown(
						Integer.valueOf(timeW[0]));
				rewardOrPunishDatil.get(i).setTimeUp(Integer.valueOf(timeW[1]));
			}

			// 奖罚类型
			String rewardType = temp.getRewardOrPunishType();
			if (StringUtils.endsWith(rewardType,
					LoadConstants.REWARDPROT_TYPE_REWARD)) {
				// 设置最高奖励金额
				rewardMaxMoney = temp.getRewardOrPunishMaxMoney();
			} else if (StringUtils.endsWith(rewardType,
					LoadConstants.REWARDPROT_TYPE_FINE)) {
				// 设置最高罚款金额
				fineMaxMoney = temp.getRewardOrPunishMaxMoney();
			}
		}
		rewardOrPunishDto.setRewardOrPunishAgreementEntity(rewardOrPunishDatil);
		rewardOrPunishDto.setFineMaxMoney(fineMaxMoney);
		rewardOrPunishDto.setRewardMaxMoney(rewardMaxMoney);
		return rewardOrPunishDto;
	}

	/**
	 * 调用综合接口，判断奖励类型是否可用
	 * 
	 * @author foss-heyongdong
	 * @date 2013年12月25日 08:34:59
	 * */
	private boolean beRewardCheck() {
		ConfigurationParamsEntity configurationParams = configurationParamsService
				.queryConfigurationParamsOneByCode(ConfigurationParamsConstants.TFR_OUTSIDEVEHICLECHARGE_BE_REWARD);
		if (configurationParams == null) {
			throw new TfrBusinessException("外请车是否奖罚尚未配置, 请联系相关部门尽快配置!");
		}
		// 为Y则为有奖励
		return StringUtils.equals(configurationParams.getConfValue(),
				FossConstants.YES);
	}

	/**
	 * 给外请车费用调整界面的
	 * 
	 * @author foss-heyongdong
	 * @date 2013年12月24日 14:51:41
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService#queryRewardOrPunishAgreementDetail(java.lang.String)
	 * **/
	@Override
	public RewardOrPunishAgreementDto queryRewardOrPunishAgreementDetail(
			String vehicleAssembleNo) {
		// 查询奖罚最该金额
		RewardOrPunishAgreementDto rewardOrPunishDto = new RewardOrPunishAgreementDto();
		// 查询奖罚明细
		List<RewardOrPunishAgreementEntity> rewardOrPunishDatil = vehicleAssembleBillDao
				.queryRewardOrPunishDetail(vehicleAssembleNo);
		List<RewardOrPunishAgreementEntity> afrewardOrPunishDatil = new ArrayList<RewardOrPunishAgreementEntity>();
		// 最高奖励上限
		BigDecimal rewardMaxMoney = new BigDecimal(0);
		// 最高罚款上限
		BigDecimal fineMaxMoney = new BigDecimal(0);

		for (int i = 0; i < rewardOrPunishDatil.size(); i++) {

			RewardOrPunishAgreementEntity temp = rewardOrPunishDatil.get(i);
			// 奖罚类型
			String rewardType = temp.getRewardOrPunishType();
			if (StringUtils.endsWith(rewardType,
					LoadConstants.REWARDPROT_TYPE_REWARD)) {
				// 设置最高奖励金额
				rewardMaxMoney = temp.getRewardOrPunishMaxMoney();
			} else if (StringUtils.endsWith(rewardType,
					LoadConstants.REWARDPROT_TYPE_FINE)) {
				// 设置最高罚款金额
				fineMaxMoney = temp.getRewardOrPunishMaxMoney();
			}
			// 转化奖罚类型
			temp.setRewardOrPunishType(convertRewardOrPunishType(rewardType));
			// 转换时间段
			temp.setTimeLimit(convertTimeType(temp.getTimeLimit()));

			afrewardOrPunishDatil.add(temp);
		}
		rewardOrPunishDto
				.setRewardOrPunishAgreementEntity(afrewardOrPunishDatil);
		rewardOrPunishDto.setRewardMaxMoney(rewardMaxMoney);
		rewardOrPunishDto.setFineMaxMoney(fineMaxMoney);
		return rewardOrPunishDto;
	}

	/**
	 * 传入运行时差和配载单号
	 * 
	 * @author foss-heyongdong
	 * @date 2013年12月24日 14:51:41
	 * @see com.deppon.foss.module.transfer.load.api.server.service.
	 *      IVehicleAssembleBillService
	 *      #qureyRewardOrPunishMoney(java.lang.String,java.lang.double)
	 * **/
	@Override
	public BigDecimal qureyRewardOrPunishMoney(String vehicleAssembleNo,double runHours){
		if(Double.valueOf(0).equals(runHours)){
			return new BigDecimal(0);
		}
		// 查询奖罚明细
		List<RewardOrPunishAgreementEntity> rewardOrPunishDatil = vehicleAssembleBillDao
				.queryRewardOrPunishDetail(vehicleAssembleNo);
		try {
			// 拆分时间段，填充时间上下限
			for (int i = 0; i < rewardOrPunishDatil.size(); i++) {
				RewardOrPunishAgreementEntity temp = rewardOrPunishDatil.get(i);
				String[] timeW = temp.getTimeLimit().split("-");
				if (timeW.length == 1) {
					rewardOrPunishDatil.get(i).setTimeDown(
							Integer.valueOf(timeW[0]));
				} else if (timeW.length == 2) {
					rewardOrPunishDatil.get(i).setTimeDown(
							Integer.valueOf(timeW[0]));
					rewardOrPunishDatil.get(i).setTimeUp(
							Integer.valueOf(timeW[1]));
				}
			}
			// 通过传递过来的时间差（实际到达时间-实际出发时间-预计时长） 比较协议内时间的上下限，返回协议金额
			for (int i = 0; i < rewardOrPunishDatil.size(); i++) {
				RewardOrPunishAgreementEntity temp = rewardOrPunishDatil.get(i);
				// 时间差大于0表示，延迟
				if (runHours > 0) {
					if (StringUtils.equals(temp.getRewardOrPunishType(),
							LoadConstants.REWARDPROT_TYPE_FINE)) {
						if ((temp.getTimeUp() != 0)
								&& (runHours > temp.getTimeDown())
								&& (runHours <= temp.getTimeUp())) {
							return temp.getAgreementMoney();
						} else if ((temp.getTimeUp() == 0)
								&& (runHours > temp.getTimeDown())) {
							return temp.getAgreementMoney();
						}
					}
				} else {
					// 小与0则，奖励金额
					if (StringUtils.equals(temp.getRewardOrPunishType(),
							LoadConstants.REWARDPROT_TYPE_REWARD)) {
						if ((temp.getTimeUp() != 0)
								&& (-runHours > temp.getTimeDown())
								&& (-runHours <= temp.getTimeUp())) {
							return temp.getAgreementMoney();
						} else if ((temp.getTimeUp() == 0)
								&& (-runHours > temp.getTimeDown())) {
							return temp.getAgreementMoney();
						}
					}
				}
			}
		} catch (Exception e) {
			// 只许成功不许失败
			return new BigDecimal(0);
		}
		return new BigDecimal(0);
	}

	/**
	 * 运单补录更新配载单装载率
	 * 
	 * @author heyongdong
	 * @date 2014年2月20日 11:37:35
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService#updateVehAssemForMakeUpWaybill(com.deppon.foss.module.transfer.load.api.shared.domain.MakeUpWaybillEntity)
	 */
	@Override
	public boolean updateVehAssemForMakeUpWaybill(
			MakeUpWaybillEntity makeUpWaybillEntity) {
		LOGGER.error("==============运单补录-更新配载单-start===============");
		String waybillNo = makeUpWaybillEntity.getWaybillNo();
		if (StringUtils.isEmpty(waybillNo)) {
			LOGGER.error("运单号为空！更新失败");
			return true;
		}
		List<QueryVehicleAssembleBillDto> vehicleAssembills = vehicleAssembleBillDao
				.queryVehicleAssemblyBillByWaybillNo(waybillNo);
		if (CollectionUtils.isEmpty(vehicleAssembills)
				|| vehicleAssembills.size() == 0) {
			LOGGER.error("运单没有配载，不需要更新");
			return true;
		}
		for (QueryVehicleAssembleBillDto entity : vehicleAssembills) {
			String vehicleAssembleNo = entity.getVehicleAssembleNo();
			List<QueryVehicleAssembleBillDto> vehicleAssemDtos = vehicleAssembleBillDao
					.queryVehicleAssembleBillInfoByBillNo(vehicleAssembleNo);
			if (CollectionUtils.isEmpty(vehicleAssemDtos)
					|| vehicleAssemDtos.size() <= 0) {
				continue;
			}
			QueryVehicleAssembleBillDto vehicleAssemDto = vehicleAssemDtos
					.get(0);
			// 装载率
			String oldloadingrate = vehicleAssemDto.getLoadingRate();
			// 考核体积
			BigDecimal examineVolume = vehicleAssemDto.getExamineVolume();
			// 所有交接单总体积
			BigDecimal totalvolumneAc = vehicleAssemDto.getVolumeTotal();
			if (examineVolume == null || examineVolume.equals(BigDecimal.ZERO)
					|| totalvolumneAc == null
					|| totalvolumneAc.equals(BigDecimal.ZERO)) {
				LOGGER.error("考核体积为0或者总体积为0，不需要更新");
				continue;
			}
			String loadingRate = totalvolumneAc.divide(examineVolume, 2,
					BigDecimal.ROUND_HALF_UP).toString();
			// 更新配载单装载率
			vehicleAssembleBillDao.updateVehAssemForMakeUpWaybill(loadingRate,
					vehicleAssembleNo);
			// 插入日志
			VehicleAssembleBillOptLogEntity logEntity = insertLog(
					vehicleAssemDto, oldloadingrate, loadingRate);
			List<VehicleAssembleBillOptLogEntity> logEntitys = new ArrayList<VehicleAssembleBillOptLogEntity>();
			logEntitys.add(logEntity);
			vehicleAssembleBillDao.updateVehicleAssembleBill(null, null, null,
					logEntitys);
		}
		LOGGER.error("==============运单补录-更新配载单-end===============");
		return true;
	}

	/**
	 * 私有方法插入配载单修改装载率日志
	 * 
	 * @param baseEntity
	 * @param oldRate
	 * @param newRate
	 * @return
	 */
	private VehicleAssembleBillOptLogEntity insertLog(
			QueryVehicleAssembleBillDto baseEntity, String oldRate,
			String newRate) {
		VehicleAssembleBillOptLogEntity log = new VehicleAssembleBillOptLogEntity();
		// 日志id
		log.setId(UUIDUtils.getUUID());
		// 日志制单时间
		log.setBillingTime(baseEntity.getCreateDate());
		// 日志配载单id
		log.setVehicleAssembleBillId(baseEntity.getId());
		// 日志配载单编号
		log.setVehicleAssembleNo(baseEntity.getVehicleAssembleNo());
		// 修改人code
		log.setOperatorCode("系统");
		// 修改人name
		log.setOperatorName("系统");
		// 操作时间
		log.setOptTime(new Date());
		// 修改类别
		log.setOptType("修改基本信息");
		log.setOptContent("运单补录修改装载率由：" + oldRate + "修改为：" + newRate);
		return log;
	}

	/**
	 * 根据交接单号 查询交接单中第一条运单的运输性质
	 * 
	 * @Author: 200978 xiaobingcheng 2014-8-26
	 * @param handoverNo
	 * @return
	 */
	public String queryProductTypeByHandoverNo(String handoverNo) {
		String productCode = vehicleAssembleBillDao
				.queryProductTypeByHandoverNo(handoverNo);
		return productCode;
	}

	/**
	 * 校验到达部门是当前部门的在途车辆任务 存在则抛出异常
	 * 
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService#validateOntheWayTruckTask(java.lang.String)
	 */
	@Override
	public void validateOntheWayTruckTask(String vehicleNo) {
		// 获取当前部门code
		String orgCode = this.querySuperiorOrgCode();
		// 查询车辆任务明细
		TruckTaskDetailEntity qeuryParam = new TruckTaskDetailEntity();
		qeuryParam.setVehicleNo(vehicleNo);
		qeuryParam.setDestOrgCode(orgCode);
		qeuryParam.setState(TaskTruckConstant.TASK_TRUCK_STATE_ONTHEWAY);
		qeuryParam.setBusinessType("LONG_DISTANCE");
		List<String> truckTaskBill = truckTaskService
				.queryTruckTaskDetailAndBill(qeuryParam);
		if (CollectionUtils.isNotEmpty(truckTaskBill)
				&& truckTaskBill.size() > 0) {
			// 配载单号
			StringBuffer vehicleAessBill = new StringBuffer();
			for (String billNo : truckTaskBill) {
				vehicleAessBill.append(billNo).append("/");
			}
			String vehicleBillStr = vehicleAessBill.toString();
			// 去掉最后一个“/”
			if (StringUtils.isNotEmpty(vehicleBillStr)
					&& vehicleBillStr.lastIndexOf("/") == vehicleBillStr
							.length() - 1) {
				vehicleBillStr = vehicleBillStr.substring(0,
						vehicleBillStr.length() - 1);
			}
			throw new TfrBusinessException("车牌号" + vehicleNo + "，配载单"
					+ vehicleBillStr + "，还未到达本部门必须做到达后，本部门才能使用此车牌号！");
		}
	}

	/**
	 * 查询配载单在租车标记中状态
	 * 
	 * @author 105869
	 * @date 2014年9月21日 10:49:04
	 * @param String
	 * 
	 * */
	@Override
	public String queryHoldingState(String vehicleAssembleNo) {
		return handOverBillService.queryHoldingState(vehicleAssembleNo);
	}

	/**
	 * 通过车牌号查询 未出发且 在途只装不卸的配载单
	 * 
	 * @author 105869
	 * @2014年11月26日 14:05:54
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService#queryMidleUnloadVehicleAssemByVehicleNo(java.lang.String)
	 */
	@Override
	public String queryMidleUnloadVehicleAssemByVehicleNo(String vehicleNo) {

		return vehicleAssembleBillDao
				.queryMidleUnloadVehicleAssemByVehicleNo(vehicleNo);
	}

	/**
	 * 校验同一个车是否存在 两个有运费的配载单，存在不能保存
	 * 
	 * @author 105869
	 * @date 2015年1月27日 17:08:16
	 * @param String
	 *            billNo,
	 * @param String
	 *            isExpress,
	 * @param String
	 *            newOrModify
	 * */
	public void validInJoinCar(String billNo) {
		List<TruckTaskDetailEntity> taskDetails = truckTaskService
				.queryTruckTaskDetail(billNo);
		if (CollectionUtils.isNotEmpty(taskDetails) && taskDetails.size() > 0) {
			// 车辆任务明细id
			String truckTaskDetailId = taskDetails.get(0).getId();
			List<String> vehicleAessNos = vehicleAssembleBillDao
					.queryTruckBillByDetailId(truckTaskDetailId, billNo);
			if (CollectionUtils.isNotEmpty(vehicleAessNos)
					&& vehicleAessNos.size() > 0) {
				throw new TfrBusinessException("同一车牌号，快递与零担均显示运费，无法保存！");
			}
		}
	}

	@Override
	public List<String> queryTruckBillByDetailId(String truckTaskDetailId,
			String billNo) {
		return vehicleAssembleBillDao.queryTruckBillByDetailId(
				truckTaskDetailId, billNo);
	}

	/**
	 * 当年1月1号到当日长途外请车和合同车累计发车趟数 当月1号到当日累计长途外请车和合同车累计发车趟数
	 * 
	 * @author 105869
	 * @date 2015年1月5日 16:19:29
	 * @return TotalNumberEntityEntity
	 */
	@Override
	public TotalNumberEntityEntity cumulativeDepartureTimes() {
		// 当前时间
		Date nowDate = new Date();
		String ymd=DateUtils.convert(nowDate, DateUtils.DATE_FORMAT);
		String year =ymd.substring(0,LoadConstants.SONAR_NUMBER_4);
		String month= ymd.substring(0,LoadConstants.SONAR_NUMBER_7);
		year=year+"-01-01 00:00:00";
		month=month+"-01 00:00:00";
		//当年1月1号
		Date ystartDate =DateUtils.convert(year, DateUtils.DATE_TIME_FORMAT);
		//当月1号
		Date mstartDate =DateUtils.convert(month, DateUtils.DATE_TIME_FORMAT);
		//查询当月发车趟数
		long monthCount=vehicleAssembleBillDao.leasedVehicleDepartureTimes(mstartDate,nowDate);
		//查询当年发车趟数
		long yearCount=vehicleAssembleBillDao.leasedVehicleDepartureTimes(ystartDate,nowDate);
		TotalNumberEntityEntity entity= new TotalNumberEntityEntity();
		entity.setCurrentmonth(monthCount);
		entity.setCurrentyear(yearCount);
		return entity;
	}

	/**
	 * 给结算接口
	 * <p>
	 * 根据运单号 查询配载单数
	 * </p>
	 * 
	 * @author 189284
	 * @date 2015-12-17 上午11:41:36
	 * @param waybillNo
	 * @return
	 * @see
	 */
	@Override
	public String queryIsVehicleassembleByNo(String waybillNo) {
		if (StringUtils.isEmpty(waybillNo)) {
			throw new TfrBusinessException("运单号为空！");
		}
		Long count = vehicleAssembleBillDao
				.queryIsVehicleassembleByNo(waybillNo);
		return count > 0 ? "Y" : "N";
	}

	/**
	 ** 通过车辆任务id和单据号查询该车辆任务下 所有有运费单据号
	 * 
	 * @author 332209
	 * @date 2015年1月29日 11:30:51
	 * @param truckTaskDetailId
	 *            ,billNo
	 * */
	@Override
	public List<String> queryTruckBillByDetailIdAndBillNo(
			String truckTaskDetailId, String billNo) {
		return vehicleAssembleBillDao.queryTruckBillByDetailIdAndBillNo(
				truckTaskDetailId, billNo);
	}

	/**
	 * 根据交接单号获取交接单基本信息
	 * 
	 * @author 045923-foss-shiwei
	 * @date 2012-11-15 下午4:36:38
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService#queryVehicleAssembleBillByNo(java.lang.String)
	 */
	@Override
	public List<VehicleAssembleBillEntity> queryWkHandoverBillByNo(
			String vehicleAssembleNo) {
		// 非空校验
		if (StringUtils.isEmpty(vehicleAssembleNo)) {
			throw new TfrBusinessException("交接单号不能为空！");
		}
		// 根据交接单号获取交接单基本信息
		List<VehicleAssembleBillEntity> list = vehicleAssembleBillDao
				.queryWkHandoverBillByNo(vehicleAssembleNo);
		/** 注释掉异常信息，获取配置单信息空的时候将会继续查询快递交接单信息 */
		// 若不存在，则抛异常，中断操作
		/*
		 * if(null == list || list.size() == 0){ throw new
		 * TfrBusinessException("配载单【" + vehicleAssembleNo + "】不存在或者已经被作废！"); }
		 */
		return list;
	}
}