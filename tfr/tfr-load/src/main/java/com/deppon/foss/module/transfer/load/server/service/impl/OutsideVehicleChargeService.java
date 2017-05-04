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
 *  PROJECT NAME  : tfr-load
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/server/service/impl/OutsideVehicleChargeService.java
 *  
 *  FILE NAME          :OutsideVehicleChargeService.java
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
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.agency.api.server.service.ITruckStowageService;
import com.deppon.foss.module.settlement.agency.api.shared.dto.StlVehicleAssembleBillDto;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.transfer.common.api.cubcgray.model.VestResponse;
import com.deppon.foss.module.transfer.common.api.server.service.IFossToCubc;
import com.deppon.foss.module.transfer.common.api.shared.define.CUBCGrayContants;
import com.deppon.foss.module.transfer.common.api.server.service.IFossToCubcService;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcValidationSourceBillNoResponse;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcVehicleAssembleBillRequest;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcVehicleAssembleBillResponse;
import com.deppon.foss.module.transfer.common.api.shared.dto.GrayParameterDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.common.server.utils.CUBCGrayUtil;
import com.deppon.foss.module.transfer.load.api.server.dao.IOutsideVehicleChargeDao;
import com.deppon.foss.module.transfer.load.api.server.service.ILoadService;
import com.deppon.foss.module.transfer.load.api.server.service.IOutsideVehicleChargeService;
import com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService;
import com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.define.OutsideVehicleChargeConstants;
import com.deppon.foss.module.transfer.load.api.shared.define.TaskTruckConstant;
import com.deppon.foss.module.transfer.load.api.shared.domain.AdjustOutVehicleFeeEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.AdjustOutVehicleFeeLogEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.VehicleAssembleBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.AdjustOutVehicleFeeDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.AdjustOutVehicleFeeLogDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.TruckTaskBillInfoDto;
import com.deppon.foss.module.transfer.load.api.shared.vo.OutVehicleAssembleBillAndFeeVo;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.NumberUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 */
public class OutsideVehicleChargeService implements IOutsideVehicleChargeService {

	/**
	 * 
	 */
	public static final String isAdjust = "Y";
	
	/**
	 * 外请车dao
	 */
	private IOutsideVehicleChargeDao outsideVehicleChargeDao;
	
	/**
	 * 配载单service
	 */
	private IVehicleAssembleBillService vehicleAssembleBillService;
	
	/**
	 *  调用结算Service 审核调整费用
	 */
	private IBillPayableService billPayableService;
	/**
	 * 调用结算Service 是否可以调整费用
	 */
	private ITruckStowageService truckStowageService;
	/**
	 *  调用综合Service 配置参数
	 */
	private IConfigurationParamsService configurationParamsService;
	/**
	 * 获取当前登录部门所属外场service
	 */
	private ILoadService loadService;
	
	/**获取车辆任务明细service**/
	private ITruckTaskService truckTaskService;
	
	
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


	public void setFossToCubc(IFossToCubc fossToCubc) {
		this.fossToCubc = fossToCubc;
	}

	/**设置获取车辆任务明细service**/
	public void setTruckTaskService(ITruckTaskService truckTaskService) {
		this.truckTaskService = truckTaskService;
	}

	/**
	 * 设置 获取当前登录部门所属外场service.
	 *
	 * @param loadService the new 获取当前登录部门所属外场service
	 */
	public void setLoadService(ILoadService loadService) {
		this.loadService = loadService;
	}

	/**
	 * 设置 配载单service.
	 *
	 * @param vehicleAssembleBillService the new 配载单service
	 */
	public void setVehicleAssembleBillService(
			IVehicleAssembleBillService vehicleAssembleBillService) {
		this.vehicleAssembleBillService = vehicleAssembleBillService;
	}

	
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LogManager.getLogger(OutsideVehicleChargeService.class);

	/**
	 * 空字符串
	 */
	private static final String BLANK_STRING = "";

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IOutsideVehicleChargeService#queryOutsideVehicleChargeList(com.deppon.foss.module.transfer.load.api.shared.dto.AdjustOutVehicleFeeDto, int, int)
	 */
	@Override
	public List<AdjustOutVehicleFeeEntity> queryOutsideVehicleChargeList(AdjustOutVehicleFeeDto assignLoadTaskDto, int limit, int start) {
		List<AdjustOutVehicleFeeEntity> adjustOutVehicleFeeList = outsideVehicleChargeDao.queryOutsideVehicleChargeList(assignLoadTaskDto, limit, start);
		return adjustOutVehicleFeeList;
	}

	/**
	 * 未审核记录条数以及审批中记录条数的统计
	 * 
	 * @author 269701---2015-07-16
	 * @param assignLoadTaskDto
	 */
	@Override
	public AdjustOutVehicleFeeEntity noAuditAndAuditInCount(
			AdjustOutVehicleFeeDto assignLoadTaskDto) {
		// 未审核的条数
		int noAuditCount = 0;
		// 审批中的条数
		int auditInCount = 0;
		AdjustOutVehicleFeeEntity auditCount = new AdjustOutVehicleFeeEntity();
		//获取查询结果
		List<AdjustOutVehicleFeeEntity> adjustOutVehicleFeeList = outsideVehicleChargeDao
				.noAuditAndAuditInCount(assignLoadTaskDto);
		// 循环查询结果，统计未审核以及审批中的条数
		for (AdjustOutVehicleFeeEntity aof : adjustOutVehicleFeeList) {
			if (StringUtils.equals(OutsideVehicleChargeConstants.NOAUDIT,
					aof.getAuditState())) {
				// 未审核的条数的统计
				noAuditCount++;
			} else if (StringUtils.equals(
					OutsideVehicleChargeConstants.AUDITIN, aof.getAuditState())) {
				// 审批中的条数的统计
				auditInCount++;
			}
		}
		// 未审核的条数
		auditCount.setNoAuditCount(noAuditCount);
		// 审批中的条数
		auditCount.setAuditInCount(auditInCount);
		return auditCount;
	}

	/**
	 * 导出外请车费用信息
	 * 
	 * @author zyx
	 * @date 2013-11-28 下午4:41:33
	 * @param adjustOutVehicleFeeDto
	 * @return
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IOutsideVehicleChargeService#exportOutsideVehicleCharge(com.deppon.foss.module.transfer.load.api.shared.dto.AdjustOutVehicleFeeDto)
	 */
	@Override
	public InputStream exportOutsideVehicleCharge(
			AdjustOutVehicleFeeDto adjustOutVehicleFeeDto) {
		InputStream excelStream = null;

		List<AdjustOutVehicleFeeEntity> adjustOutVehicleFees = queryOutsideVehicleChargeList(
				adjustOutVehicleFeeDto, Integer.MAX_VALUE, Integer.MIN_VALUE);
		// 行List
		List<List<String>> rowList = new ArrayList<List<String>>();
		for (AdjustOutVehicleFeeEntity adjustOutVehicleFee : adjustOutVehicleFees) {
			// 每行的列List
			List<String> columnList = new ArrayList<String>();

			// 部门经理审核状态--改为审核状态
			String auditState = adjustOutVehicleFee.getAuditState();
			if (StringUtils.equals(auditState,
					OutsideVehicleChargeConstants.NOAUDIT)) {
				auditState = "未审核";
			} else if (StringUtils.equals(auditState,
					OutsideVehicleChargeConstants.AUDITNOTPASS)) {
				auditState = "未通过";
			} else if (StringUtils.equals(auditState,
					OutsideVehicleChargeConstants.AUDITPASS)) {
				auditState = "已通过";
			} else if (StringUtils.equals(auditState,
					OutsideVehicleChargeConstants.AUDITIN)) {
				// 269701--lln----2015-07-08
				// 审核状态中增加“审批中”状态
				auditState = "审批中";
			}
			columnList.add(auditState);

			// 配载车次号
			columnList.add(adjustOutVehicleFee.getVehicleassembleNo());
			// 269701--lln----2015-07-08
			// 配载类型
			if (StringUtils.equals(adjustOutVehicleFee.getAssembleType(),
					OutsideVehicleChargeConstants.CAR_LOAD)) {
				columnList.add("整车");
			} else if (StringUtils.equals(
					adjustOutVehicleFee.getAssembleType(),
					OutsideVehicleChargeConstants.OWNER_LINE)) {
				columnList.add("专线");
			} else {
				columnList.add(" ");
			}
			// 车牌号
			columnList.add(adjustOutVehicleFee.getVehicleNo());

			// 主驾驶姓名
			columnList.add(adjustOutVehicleFee.getDriverName());

			// 司机电话   269701--lln----2015-07-08
			columnList.add(adjustOutVehicleFee.getDriverMobilePhone());

			// 走货日期
			Date handoverTime = adjustOutVehicleFee.getHandoverTime();
			String handoverTimeStr = "";
			if (handoverTime != null) {
				handoverTimeStr = DateUtils.convert(handoverTime);
			}
			columnList.add(handoverTimeStr);

			// 出发部门
			columnList.add(adjustOutVehicleFee.getOrigOrgName());

			// 到达部门
			columnList.add(adjustOutVehicleFee.getDestOrgName());

			//总运费
			BigDecimal feeTotal = adjustOutVehicleFee.getFeeTotal() == null ? BigDecimal.ZERO : adjustOutVehicleFee.getFeeTotal();
			DecimalFormat df = new DecimalFormat("0.00");
			columnList.add(df.format(feeTotal));

			//预付运费总额
			BigDecimal prepaidFeeTotal = adjustOutVehicleFee.getPrepaidFeeTotal() == null ? BigDecimal.ZERO : adjustOutVehicleFee.getPrepaidFeeTotal();
			columnList.add(df.format(prepaidFeeTotal));

			//到付运费总额
			BigDecimal arriveFeeTotal = adjustOutVehicleFee.getArriveFeeTotal() == null ? BigDecimal.ZERO : adjustOutVehicleFee.getArriveFeeTotal();
			columnList.add(df.format(arriveFeeTotal));
			
			//初始金额
			BigDecimal initAmt = adjustOutVehicleFee.getInitAmt() == null ? BigDecimal.ZERO : adjustOutVehicleFee.getInitAmt();
			columnList.add(df.format(initAmt));

			//调整费用
			BigDecimal adjustFee = adjustOutVehicleFee.getAdjustFee() == null ? BigDecimal.ZERO : adjustOutVehicleFee.getAdjustFee();
			String adjustFeeStr = "";
			if(StringUtils.equals(adjustOutVehicleFee.getAwardType(), OutsideVehicleChargeConstants.REWARD)) {
				//奖励
				adjustFeeStr = "+" + df.format(adjustFee);
			} else if(StringUtils.equals(adjustOutVehicleFee.getAwardType(), OutsideVehicleChargeConstants.FINE)) {
				//罚款
				adjustFeeStr = "-" + df.format(adjustFee);
			} else if(StringUtils.equals(adjustOutVehicleFee.getAwardType(), OutsideVehicleChargeConstants.ADJUST_TYPE_FEE_AUGMENT)) {
				//费用增加
				adjustFeeStr = "+" + df.format(adjustFee);
			} else if(StringUtils.equals(adjustOutVehicleFee.getAwardType(), OutsideVehicleChargeConstants.ADJUST_TYPE_FEE_REDUCE)) {
				//费用减少
				adjustFeeStr = "-" + df.format(adjustFee);
			}
			columnList.add(adjustFeeStr);

			//是否时效条款
			String timelinessClause = adjustOutVehicleFee.getTimelinessClause();
			if(StringUtils.equals(timelinessClause, FossConstants.YES)) {
				timelinessClause = "是";
			} else {
				timelinessClause = "否";
			}
			columnList.add(timelinessClause);

			//变化时长
			columnList.add(adjustOutVehicleFee.getTimelinessDurationStr());
			//调整原因 269701
			columnList.add(adjustReasonStr(adjustOutVehicleFee.getTimelinessClause(),adjustOutVehicleFee.getCause(),adjustOutVehicleFee.getAdjustReason()));

			// 其他原因
			columnList.add(adjustOutVehicleFee.getOtherReason());

			// 审核人名称
			columnList.add(adjustOutVehicleFee.getAuditorName());

			rowList.add(columnList);
		}
		//表头追加 "部门经理审核状态"改为 "审核状态", "配载车次号", "配载类型"，"司机电话"
		String[] rowHeads = { "审核状态", "配载车次号", "配载类型", "车牌号", "主驾驶姓名", "司机电话",
				"走货日期", "出发部门", "到达部门", "总运费", "预付运费总额", "到付运费总额", "初始金额",
				"调整费用", "时效条款", "变化时长", "调整原因", "其他原因", "审核人" };// 定义表头

		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSheetName("外请车费用信息");
		exportSetting.setSize(LoadConstants.SONAR_NUMBER_5000);
		ExportResource exportResource = new ExportResource();
		exportResource.setHeads(rowHeads);
		exportResource.setRowList(rowList);
		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		excelStream = objExporterExecutor.exportSync(exportResource,exportSetting);
		return excelStream;
	}

	/**
	 * 根据DB得到的调整原因key，通过数据字典转换为相应的value 269701---lln---2015-07-21
	 * 因多处使用，提为方法
	 * @param timelinessClause(是否时效条款),cause(产生时效),adjustReason(调整原因)
	 * @return adjustReasonStr
	 */
	private String adjustReasonStr(String timelinessClause, String cause,String adjustReason) {
		// 调整原因
		String reasonStr = "";
		if (StringUtils.equals(timelinessClause, FossConstants.YES)) {
			reasonStr = cause;
			if (StringUtils.equals(reasonStr,OutsideVehicleChargeConstants.CAUSE_AGINGINADVANCE)) {
				// 调整原因-时效提前原因
				reasonStr = DictUtil.rendererSubmitToDisplay( adjustReason, DictionaryConstants.CAUSE_AGINGINADVANCE_REASON);
			} else {
				// 调整原因-时效延误原因
				reasonStr = DictUtil.rendererSubmitToDisplay(adjustReason, DictionaryConstants.CAUSE_AGINGDELAY_REASON);
			}
		} else {
			if (StringUtils.equals(adjustReason,OutsideVehicleChargeConstants.AGINGDELAY)) {
				reasonStr = "时效延误";
			} else if (StringUtils.equals(adjustReason,OutsideVehicleChargeConstants.OTHER)) {
				reasonStr = "其他";
			} else if (StringUtils.equals(adjustReason,OutsideVehicleChargeConstants.AGINGINADVANCE)) {
				reasonStr = "时效提前";
			}
			// 费用增加原因
			if (StringUtils.isEmpty(reasonStr)|| StringUtils.equals(reasonStr,adjustReason)) {
				reasonStr = DictUtil.rendererSubmitToDisplay(adjustReason,DictionaryConstants.FEE_AUGMENT_REASON);
			}
			// 费用减少原因
			if (StringUtils.isEmpty(reasonStr)|| StringUtils.equals(reasonStr,adjustReason)) {
				reasonStr = DictUtil.rendererSubmitToDisplay(adjustReason,DictionaryConstants.FEE_REDUCE_REASON);
			}
			// 时效提前原因
			if (StringUtils.isEmpty(reasonStr)|| StringUtils.equals(reasonStr,adjustReason)) {
				reasonStr = DictUtil.rendererSubmitToDisplay(adjustReason,DictionaryConstants.CAUSE_AGINGINADVANCE_REASON);
			}
			// 时效延误原因
			if (StringUtils.isEmpty(reasonStr)|| StringUtils.equals(reasonStr,adjustReason)) {
				reasonStr = DictUtil.rendererSubmitToDisplay(adjustReason,DictionaryConstants.CAUSE_AGINGDELAY_REASON);
			}
		}

		return reasonStr;

	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IOutsideVehicleChargeService#queryCount(com.deppon.foss.module.transfer.load.api.shared.dto.AdjustOutVehicleFeeDto)
	 */
	@Override
	public Long queryCount(AdjustOutVehicleFeeDto adjustOutVehicleFeeDto) {
		Long total = outsideVehicleChargeDao.queryCount(adjustOutVehicleFeeDto);
		return total;
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IOutsideVehicleChargeService#addOutsideVehicleCharge(com.deppon.foss.module.transfer.load.api.shared.domain.AdjustOutVehicleFeeEntity)
	 */
	@Override
	@Transactional
	public int addOutsideVehicleCharge(AdjustOutVehicleFeeEntity adjustOutVehicleFeeEntity) {
		adjustOutVehicleFeeEntity.setId(UUIDUtils.getUUID());
		//269701--lln--2015-09-09 begin
		//配载车次号为整车配载车次号，则调整费用文本框不受配置参数的限制，该文本框中只可输入正整数；
		if(!StringUtils.equals(adjustOutVehicleFeeEntity.getAssembleType(),OutsideVehicleChargeConstants.CAR_LOAD)){
		//269701--lln--2015-09-09 end
		//2013年12月28日 10:41:43 modify by zyx begin
		//此处逻辑完全脱胎于前台js修改费用处
		if(!StringUtils.equals(adjustOutVehicleFeeEntity.getTimelinessClause(), FossConstants.YES)) {
			//非时效费用修改时, 需要判断该配载单的奖励活惩罚的总金额不能大于配置的总金额
			List<AdjustOutVehicleFeeDto> adjustOutVehicleFeeDtos = queryOutsideVehicleChargeByVehicleassembleNo(adjustOutVehicleFeeEntity.getVehicleassembleNo());
			if(adjustOutVehicleFeeDtos.size() >0 ) {
				BigDecimal awardFee = BigDecimal.ZERO;
				BigDecimal fineFee = BigDecimal.ZERO;
				for(AdjustOutVehicleFeeDto dto : adjustOutVehicleFeeDtos) {
					if(!StringUtils.equals(adjustOutVehicleFeeEntity.getId(), dto.getId())){//ID 相同则为同一个对象, 不计入统计
						if(StringUtils.equals(dto.getAwardType(), OutsideVehicleChargeConstants.ADJUST_TYPE_FEE_AUGMENT) &&
								!StringUtils.equals(dto.getTimelinessClause(), FossConstants.YES)){
							//该配载单奖励总金额
							awardFee = awardFee.add(dto.getAdjustFee());
							continue;
						}
						if(StringUtils.equals(dto.getAwardType(), OutsideVehicleChargeConstants.ADJUST_TYPE_FEE_REDUCE) &&
								!StringUtils.equals(dto.getTimelinessClause(), FossConstants.YES)){
							//该配载单罚款总金额
							fineFee = fineFee.add(dto.getAdjustFee());
							continue;
						}
					}
				}
				awardFee = awardFee.multiply(BigDecimal.valueOf(LoadConstants.INSURANCE_VALUE_MULTIPLY));
				fineFee = fineFee.multiply(BigDecimal.valueOf(LoadConstants.INSURANCE_VALUE_MULTIPLY));
				double feeTot = 0.0;
				if(StringUtils.equals(adjustOutVehicleFeeEntity.getAwardType(), OutsideVehicleChargeConstants.ADJUST_TYPE_FEE_AUGMENT)) {
					//费用增加时, 调整总费用 = 本次调整费用 + 该单据所有增加的费用 - 费用减少的费用(该逻辑为以前的逻辑并未修改) zyx
					feeTot = adjustOutVehicleFeeEntity.getAdjustFee().doubleValue() + awardFee.doubleValue() - fineFee.doubleValue();
				} else {
					//费用减少时, 调整总费用 = 该单据所有增加的费用 - 费用减少的费用 - 本次调整费用(该逻辑为以前的逻辑并未修改) zyx
					feeTot = awardFee.doubleValue() - fineFee.doubleValue() - adjustOutVehicleFeeEntity.getAdjustFee().doubleValue();
				}
				List<ConfigurationParamsEntity> configurationParamss = queryConfigurationParamsExactByEntity(Integer.MAX_VALUE, Integer.MAX_VALUE);
				double awardMaxFee = 0.00;
				double fineMaxFee = 0.00;
				//获取配置参数中配置的奖励, 罚款最大值
				for(ConfigurationParamsEntity configurationParams : configurationParamss){	
					if(StringUtils.equals(configurationParams.getCode(), OutsideVehicleChargeConstants.DEFAULT_AWARD_MAXFEE)){
						awardMaxFee = NumberUtils.toDouble(configurationParams.getConfValue());
					}
					if(StringUtils.equals(configurationParams.getCode(), OutsideVehicleChargeConstants.DEFAULT_FINE_MAXFEE)){
						fineMaxFee = NumberUtils.toDouble(configurationParams.getConfValue());
					}
				}
				feeTot = feeTot / LoadConstants.SONAR_NUMBER_100;
				if(feeTot >= -fineMaxFee && feeTot <= awardMaxFee){
					//总费用效验通过
				}else{
					throw new TfrBusinessException("当前奖罚范围超出最大值, 请修改后再保存!");
				}
			}
		}
		}
		//end

		// 269701--lln--2015-07-13 begin
		//外请车费用修改日志表 新增一条记录
		addOutsideVehicleUpdateLog(adjustOutVehicleFeeEntity,
				OutsideVehicleChargeConstants.ADDLOG);
		// 269701--lln--2015-07-13 end

		return outsideVehicleChargeDao
				.addOutsideVehicleCharge(adjustOutVehicleFeeEntity);
	}

	/**
	 * 查询外请车费用日志
	 * 
	 * @author 269701-foss-lln
	 * @date 2015-07-14 上午9:05:58
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IOutsideVehicleChargeService#queryOutsideVehicleUpdateLogs(com.deppon.foss.module.transfer.load.api.shared.domain.AdjustOutVehicleFeeLogDto)
	 */
	@Override
	public List<AdjustOutVehicleFeeLogEntity> queryOutsideVehicleUpdateLogs(
			AdjustOutVehicleFeeLogDto adjustOutVehicleFeeLogDto, int limit,
			int start) {
		return outsideVehicleChargeDao.queryOutsideVehicleUpdateLogs(
				adjustOutVehicleFeeLogDto, limit, start);
	}

	/**
	 * 查询外请车费用修改日志总条数
	 * 
	 * @author 269701-foss-lln
	 * @date 2015-07-14 上午9:05:58
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IOutsideVehicleChargeService#queryOutsideVehicleUpdateLogsTotalCount(com.deppon.foss.module.transfer.load.api.shared.domain.AdjustOutVehicleFeeLogDto)
	 */
	@Override
	public Long queryOutsideVehicleUpdateLogsTotalCount(
			AdjustOutVehicleFeeLogDto adjustOutVehicleFeeLogDto) {
		Long total = outsideVehicleChargeDao
				.queryOutsideVehicleUpdateLogsTotalCount(adjustOutVehicleFeeLogDto);
		return total;
	}

	/**
	 * 
	 * * 新增时效费用接口
	 * 外请车到达确认之后，自动生成一条待审核数据（不可删除），
	 * 其中如果配载单录入了时效条款，则自动填写“是“，如果没有，则填写“否“；
	 * 只有时效条款外请车车辆“到达确认”以后，系统自动计算出本次运行的结果，
	 * 不足一分钟按一分钟计，最终计算以小时为单位，保存两位小数。
	 * 变化时长=实际运行时长-预计运行时长；
	 * 2、实际运行时长里，没有出发时间的配载单，出发时间按配载时间计算；
	 * <p>提供给到达</p>  
	 * @author 163580
	 * @date 2013-12-5 上午9:49:31
	 * @param vehicleAssembleNo  配载单号
	 * @param timelinessDuration 变化时长(小于0为提前, 反之为延后, 单位为小时)
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IOutsideVehicleChargeService#addOutsideVehicleChargeForArrival(com.deppon.foss.module.transfer.load.api.shared.domain.AdjustOutVehicleFeeEntity)
	 */
	@Override
	@Transactional
	public void addOutsideVehicleChargeForArrival(String truckTaskDetailId) {
		try{
			LOGGER.info("到达生成时效费用信息begin");
			if(StringUtils.isEmpty(truckTaskDetailId)) {
				throw new TfrBusinessException("生成时效费用时出错,任务ID不能为空!");
//				生成时效费用时出错, truckTaskDetailId不能为空!
//				return;
			}
			
			List<TruckTaskBillInfoDto> truckTaskBillInfos = null;
			//增加悟空交接单取得开关
			if (configurationParamsService.queryTfrSwitch4Ecs()) {
				/**配载单 快递交接单标示位  是否为快递交接单*/
				//boolean isWkBill=false;
				truckTaskBillInfos =new ArrayList<TruckTaskBillInfoDto>();
				/**配载单*/
				List<TruckTaskBillInfoDto> truckTaskBillInfosPzd = outsideVehicleChargeDao.queryTruckTaskBillInfoByTruckTaskDetailId(truckTaskDetailId);
				/**快递交接单*/
				List<TruckTaskBillInfoDto> truckTaskBillInfosWk = outsideVehicleChargeDao.queryTruckTaskBillInfoByTruckTaskDetailIdFromWkBill(truckTaskDetailId);
				
				if(!CollectionUtils.isEmpty(truckTaskBillInfosPzd)) {
					truckTaskBillInfos.addAll(truckTaskBillInfosPzd);
				}
				if(!CollectionUtils.isEmpty(truckTaskBillInfosWk)) {
					truckTaskBillInfos.addAll(truckTaskBillInfosWk);
				}
			} else {
				truckTaskBillInfos = outsideVehicleChargeDao.queryTruckTaskBillInfoByTruckTaskDetailId(truckTaskDetailId);
			}

			
			if(CollectionUtils.isEmpty(truckTaskBillInfos)) {
				//生成时效费用时出错, 无单据信息;
				return;
			}

			for(TruckTaskBillInfoDto billinfoDto : truckTaskBillInfos) {
				String vehicleAssembleNo = billinfoDto.getBillNo();
				if(StringUtils.isEmpty(vehicleAssembleNo)) {
					//单据号为空, 不能继续生成
					continue;
				}
				
				List<AdjustOutVehicleFeeDto> adjustOutVehicleFeeDtos = queryOutsideVehicleChargeCauseByVehicleassembleNo(vehicleAssembleNo);
				if(!CollectionUtils.isEmpty(adjustOutVehicleFeeDtos)) {
					//如果该配载单已经生成过费用则不需要再次生成(因为可能存在, 车辆已经到达生成后, 又将车辆到达给取消了),
					//不管是否审核
					continue;
				}
				
				AdjustOutVehicleFeeDto dto = outsideVehicleChargeDao.queryByVehicleassembleNo(vehicleAssembleNo);
				if(dto == null) {
					//增加获取悟空交接单开关
					if (configurationParamsService.queryTfrSwitch4Ecs()) {
						/**如果配载单null 就继续查快递交接单信息
						 * 查询快递长度外请车交接单
						 * */
						dto= outsideVehicleChargeDao.queryByVehicleassembleNoFromWk(vehicleAssembleNo);
						if(dto==null){
							//配载单数据有问题不能生成费用
							/**快递交接单数据有问题不能生成费用*/
							continue;
						}	
					} else {
						//配载单数据有问题不能生成费用
						continue;
					}
				}
				
				//这里只用判断配载单是否有时效协议即可
				if(!StringUtils.equals(dto.getBeTimeLiness(), FossConstants.YES)) {
					//无时效协议
					continue;
				}
				
				// 判断是否有权限申请,调用结算返回false代表未审核，否则不能操作
				if (queryBillPayableIsWriteOff(vehicleAssembleNo, FossConstants.YES)) {
					// 无权限，则告知无法申请。
					LOGGER.error(OutsideVehicleChargeConstants.OUTSIDEVEHICLECHARGE_BILLPAYABLE_FALSE_LOG);
					continue;
				}
				
				//出发时间
				Date actualDepartTime = billinfoDto.getActualDepartTime();
				if(actualDepartTime == null) {
					//因为系统允许未出发直接到达, 所以可能存在出发时间为空的数据;
					//此时需取配载单的创建时间
					actualDepartTime = dto.getHandoverTime();
				}
				
				//到达时间
				Date actualArriveTime = billinfoDto.getActualArriveTime();
				
				String runHoursStr = dto.getRunHours();
				//如果当前配载单为最终到达
				//则取第一个到达部门的出发到达时间
				if(StringUtils.equals(dto.getBefinallyarrive(), FossConstants.YES)) {
					//根据主任务ID获取子任务(根据到达时间排序, 取第一个到达部门的出发时间)
					TruckTaskDetailEntity truckTaskDetail = outsideVehicleChargeDao.getFirstTruckTaskDetailById(billinfoDto.getTruckTaskId());
					if(truckTaskDetail == null) {
						continue;
					}
					List<TruckTaskBillInfoDto> truckTaskBillInfoDtos = outsideVehicleChargeDao.queryTruckTaskBillInfoByTruckTaskDetailId(truckTaskDetail.getId());
					TruckTaskBillInfoDto truckTaskBillInfoDto = truckTaskBillInfoDtos.get(0);
					AdjustOutVehicleFeeDto adjustOutVehicleFeeDto = outsideVehicleChargeDao.queryByVehicleassembleNo(truckTaskBillInfoDto.getBillNo());
					
					//增加调用悟空数据开关
					if (configurationParamsService.queryTfrSwitch4Ecs()) {
						if(adjustOutVehicleFeeDto==null){
							//如果配置单查询的结果为null 就查询快递的交接单（长途外请车）
							adjustOutVehicleFeeDto=outsideVehicleChargeDao.queryByVehicleassembleNoFromWk(truckTaskBillInfoDto.getBillNo());
						}
					}

					//如果当前配载单为最终到达
					//则取第一个到达部门下面的单据时效
					runHoursStr = adjustOutVehicleFeeDto.getRunHours();
					
					actualDepartTime = truckTaskDetail.getActualDepartTime();
					if(actualDepartTime == null) {
						//因为系统允许未出发直接到达, 所以可能存在出发时间为空的数据;
						//此时需取配载单的创建时间
						actualDepartTime = adjustOutVehicleFeeDto.getHandoverTime();
					}
					actualArriveTime = truckTaskDetail.getActualArriveTime();
					if(actualArriveTime == null) {
						//理论上来说这里的到达时间永远不可能为空, 因为最终到达后会更新其他子任务为已到达
						actualArriveTime = new Date();
					}
				}
				
				if(actualArriveTime == null) {
					actualArriveTime = new Date();
				}
				
				//到达时间 - 出发时间 = 实际运行时长(分钟)
				long runingTime = DateUtils.getMinuteDiff(actualDepartTime, actualArriveTime);
				Double runingTimes = Double.valueOf(runingTime);
				//转换为小时(保留3位小数)
				BigDecimal timelinessDuration = new BigDecimal(runingTimes / 60.0).setScale(LoadConstants.SONAR_NUMBER_3, BigDecimal.ROUND_HALF_UP);
				
				BigDecimal runHours = BigDecimal.ZERO;
				if(NumberUtils.isNumber(runHoursStr)) {
					runHours = BigDecimal.valueOf(NumberUtils.toDouble(runHoursStr));
				}
				//变化时长; 实际运行时长 - 线路时效 = 变化时长
				timelinessDuration = timelinessDuration.subtract(runHours);
				
				AdjustOutVehicleFeeEntity adjustOutVehicleFee = new AdjustOutVehicleFeeEntity();
				adjustOutVehicleFee.setVehicleassembleNo(vehicleAssembleNo);
				adjustOutVehicleFee.setVehicleNo(dto.getVehicleNo());
				adjustOutVehicleFee.setDriverCode(dto.getDriverCode());
				adjustOutVehicleFee.setDriverName(dto.getDriverName());
				adjustOutVehicleFee.setHandoverTime(dto.getHandoverTime());
				adjustOutVehicleFee.setOrigOrgCode(dto.getOrigOrgCode());
				adjustOutVehicleFee.setOrigOrgName(dto.getOrigOrgName());
				adjustOutVehicleFee.setDestOrgCode(dto.getDestOrgCode());
				adjustOutVehicleFee.setDestOrgName(dto.getDestOrgName());
				//总运费, 数据库中保存的是已分为单位,  查询出来时默认除以了100, 此处再将之还原为分
				BigDecimal feeTotal = dto.getFeeTotal();
				if(feeTotal == null) {
					feeTotal = BigDecimal.ZERO;
				}
				adjustOutVehicleFee.setFeeTotal(feeTotal.multiply(BigDecimal.valueOf(LoadConstants.INSURANCE_VALUE_MULTIPLY)));
				
				//预付运费, 数据库中保存的是已分为单位,  查询出来时默认除以了100, 此处再将之还原为分
				BigDecimal prepaidFeeTotal = dto.getPrepaidFeeTotal();
				if(prepaidFeeTotal == null) {
					prepaidFeeTotal = BigDecimal.ZERO;
				}
				adjustOutVehicleFee.setPrepaidFeeTotal(prepaidFeeTotal.multiply(BigDecimal.valueOf(LoadConstants.INSURANCE_VALUE_MULTIPLY)));

				//到付运费, 数据库中保存的是已分为单位,  查询出来时默认除以了100, 此处再将之还原为分
				BigDecimal arriveFeeTotal = dto.getArriveFeeTotal();
				if(arriveFeeTotal == null) {
					arriveFeeTotal = BigDecimal.ZERO;
				}
				adjustOutVehicleFee.setArriveFeeTotal(arriveFeeTotal.multiply(BigDecimal.valueOf(LoadConstants.INSURANCE_VALUE_MULTIPLY)));
				
				if(timelinessDuration.doubleValue() < 0) {
					//变化时长(小于0为提前, 大于0为延后, 单位为小时)
					//产生原因-时效提前
					adjustOutVehicleFee.setCause(OutsideVehicleChargeConstants.CAUSE_AGINGINADVANCE);
					//提前则为奖励
					adjustOutVehicleFee.setAwardType(OutsideVehicleChargeConstants.REWARD);
					//调整原因为时效提前
					adjustOutVehicleFee.setAdjustReason(OutsideVehicleChargeConstants.CAUSE_AGINGINADVANCE_REASON_AGINGINADVANCE);
				} else {
					//变化时长(小于0为提前, 大于0为延后, 单位为小时)
					//产生原因-时效延误
					adjustOutVehicleFee.setCause(OutsideVehicleChargeConstants.CAUSE_AGINGDELAY);
					//延后则为罚款
					adjustOutVehicleFee.setAwardType(OutsideVehicleChargeConstants.FINE);
					//调整原因为时效延误-无
					adjustOutVehicleFee.setAdjustReason("无");
				}
				//取单据时效协议表中的费用, 配载单提供接口返回 
				BigDecimal adjustFee = vehicleAssembleBillService.qureyRewardOrPunishMoney(vehicleAssembleNo, timelinessDuration.doubleValue());
				if(adjustFee == null) {
					adjustFee = BigDecimal.ZERO;
				}
				adjustOutVehicleFee.setAdjustFee(adjustFee.multiply(BigDecimal.valueOf(LoadConstants.INSURANCE_VALUE_MULTIPLY)));
				BigDecimal initAmt = adjustFee.multiply(BigDecimal.valueOf(LoadConstants.INSURANCE_VALUE_MULTIPLY));
				//initAmt初始金额 只做展示用
				if(StringUtils.equals(adjustOutVehicleFee.getAwardType(), OutsideVehicleChargeConstants.REWARD)){
					//如果为奖励
					//do nothing
					//initAmt = 0 + initAmt;
				}
				if(StringUtils.equals(adjustOutVehicleFee.getAwardType(), OutsideVehicleChargeConstants.FINE)){
					//如果为罚款, 则界面上显示为负数
					initAmt = BigDecimal.ZERO.subtract(initAmt);
				}
				adjustOutVehicleFee.setInitAmt(initAmt);
				if(adjustFee.compareTo(BigDecimal.ZERO) == 0) {
					adjustOutVehicleFee.setAuditState(OutsideVehicleChargeConstants.AUDITPASS);
				} else {
					adjustOutVehicleFee.setAuditState(OutsideVehicleChargeConstants.NOAUDIT);
				}
				adjustOutVehicleFee.setTimelinessClause(FossConstants.YES);
				adjustOutVehicleFee.setTimelinessDuration(timelinessDuration);
				addOutsideVehicleCharge(adjustOutVehicleFee);
				LOGGER.info("单据生成时效费用成功:"+vehicleAssembleNo);
			}
			LOGGER.info("到达生成时效费用信息end:"+truckTaskDetailId);
		} catch (Exception ex) {
			//车辆到达后生成外请车协议费用
			//如果生成时出问题, 不应该影响车辆正常到达
			LOGGER.error(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IOutsideVehicleChargeService#updateOutsideVehicleCharge(com.deppon.foss.module.transfer.load.api.shared.domain.AdjustOutVehicleFeeEntity)
	 */
	@Override
	@Transactional
	public int updateOutsideVehicleCharge(AdjustOutVehicleFeeEntity adjustOutVehicleFeeEntity) {
		// modify by liangfuxiang 013-03-13 出发部门修改 begin
		/*
		 * if(StringUtils.equals(entity.getCode(), adjustOutVehicleFeeEntity.getDestOrgCode())){ LOGGER.info("到达部门与当前部门是同一部门"); }else if(StringUtils.equals(entity.getCode(),
		 * adjustOutVehicleFeeEntity.getOrigOrgCode())){ LOGGER.info("出发部门与当前部门是同一部门"); }else{ throw new TfrBusinessException("当前用户不可以调整费用，请重新登录", ""); }
		 */
		
		if(StringUtils.equals(adjustOutVehicleFeeEntity.getAdjustReason(), "无") || StringUtils.isEmpty(adjustOutVehicleFeeEntity.getAdjustReason())) {
			throw new TfrBusinessException("时效延误的调整原因不能为无，请选择其他项!");
		}

		if (validateOrgCode(adjustOutVehicleFeeEntity.getOrigOrgCode())) {
			// modify by liangfuxiang 2013-3-18下午2:16:20 begin 去除魔鬼数据
			// LOGGER.info("出发部门与当前部门是同一部门");
			LOGGER.info(OutsideVehicleChargeConstants.CURRENT_DEPT_SAME_AS_ORGINAL_DEPT);
			// modify by liangfuxiang 2013-3-18下午2:16:49 end;
		}
		else {
			// modify by liangfuxiang 2013-3-18下午2:17:44 begin 国际化
			// throw new TfrBusinessException("当前用户不可以调整费用，请重新登录", "");
			throw new TfrBusinessException(OutsideVehicleChargeConstants.CURRENT_USER_NO_ADJUST_RIGHT, BLANK_STRING);
			// modify by liangfuxiang 2013-3-18下午2:18:06 end;
		}
		// modify by liangfuxiang 013-03-13 出发部门修改 end
		
		AdjustOutVehicleFeeDto adjustOutVehicleFeeDto = queryById(adjustOutVehicleFeeEntity.getId());
		//2013年12月27日 08:55:07 modify by zyx
		if(StringUtils.equals(FossConstants.YES, adjustOutVehicleFeeDto.getTimelinessClause()) 
				&& StringUtils.equals(OutsideVehicleChargeConstants.REWARD, adjustOutVehicleFeeEntity.getAwardType())
				&& adjustOutVehicleFeeEntity.getAdjustFee().doubleValue() > 0) {
			//如果为有时效条款的费用, 并且当前费用为奖励, 则需要判断当前系统是否有配置奖励
			if(!beReward()) {
				//如果当前系统没有奖励, 但是当前费用却包含奖励信息, 则不能保存
				throw new TfrBusinessException("当前系统未配置奖励, 请修改后再保存!");
			}
		}
		//2013年12月27日 08:55:07 modify by zyx end
		//269701--lln--2015-09-09 begin
		//配载车次号为整车配载车次号，则调整费用文本框不受配置参数的限制，该文本框中只可输入正整数；
		if(!StringUtils.equals(adjustOutVehicleFeeEntity.getAssembleType(),OutsideVehicleChargeConstants.CAR_LOAD)){
		//269701--lln--2015-09-09 end
		//2013年12月28日 10:41:43 modify by zyx begin
		//此处逻辑完全脱胎于前台js修改费用处
		if(!StringUtils.equals(adjustOutVehicleFeeDto.getTimelinessClause(), FossConstants.YES)) {
			//非时效费用修改时, 需要判断该配载单的奖励活惩罚的总金额不能大于配置的总金额
			List<AdjustOutVehicleFeeDto> adjustOutVehicleFeeDtos = queryOutsideVehicleChargeByVehicleassembleNo(adjustOutVehicleFeeEntity.getVehicleassembleNo());
			if(adjustOutVehicleFeeDtos.size() >0 ) {
				BigDecimal awardFee = BigDecimal.ZERO;
				BigDecimal fineFee = BigDecimal.ZERO;
				for(AdjustOutVehicleFeeDto dto : adjustOutVehicleFeeDtos) {
					if(!StringUtils.equals(adjustOutVehicleFeeEntity.getId(), dto.getId())){//ID 相同则为同一个对象, 不计入统计
						if(StringUtils.equals(dto.getAwardType(), OutsideVehicleChargeConstants.ADJUST_TYPE_FEE_AUGMENT) &&
								!StringUtils.equals(dto.getTimelinessClause(), FossConstants.YES)){
							//该配载单奖励总金额
							awardFee = awardFee.add(dto.getAdjustFee());
							continue;
						}
						if(StringUtils.equals(dto.getAwardType(), OutsideVehicleChargeConstants.ADJUST_TYPE_FEE_REDUCE) &&
								!StringUtils.equals(dto.getTimelinessClause(), FossConstants.YES)){
							//该配载单罚款总金额
							fineFee = fineFee.add(dto.getAdjustFee());
							continue;
						}
					}
				}
				awardFee = awardFee.multiply(BigDecimal.valueOf(LoadConstants.INSURANCE_VALUE_MULTIPLY));
				fineFee = fineFee.multiply(BigDecimal.valueOf(LoadConstants.INSURANCE_VALUE_MULTIPLY));
				double feeTot = 0.0;
				if(StringUtils.equals(adjustOutVehicleFeeEntity.getAwardType(), OutsideVehicleChargeConstants.ADJUST_TYPE_FEE_AUGMENT)) {
					//费用增加时, 调整总费用 = 本次调整费用 + 该单据所有增加的费用 - 费用减少的费用(该逻辑为以前的逻辑并未修改) zyx
					feeTot = adjustOutVehicleFeeEntity.getAdjustFee().doubleValue() + awardFee.doubleValue() - fineFee.doubleValue();
				} else {
					//费用减少时, 调整总费用 = 该单据所有增加的费用 - 费用减少的费用 - 本次调整费用(该逻辑为以前的逻辑并未修改) zyx
					feeTot = awardFee.doubleValue() - fineFee.doubleValue() - adjustOutVehicleFeeEntity.getAdjustFee().doubleValue();
				}
				List<ConfigurationParamsEntity> configurationParamss = queryConfigurationParamsExactByEntity(Integer.MAX_VALUE, Integer.MAX_VALUE);
				double awardMaxFee = 0.00;
				double fineMaxFee = 0.00;
				//获取配置参数中配置的奖励, 罚款最大值
				for(ConfigurationParamsEntity configurationParams : configurationParamss){	
					if(StringUtils.equals(configurationParams.getCode(), OutsideVehicleChargeConstants.DEFAULT_AWARD_MAXFEE)){
						awardMaxFee = NumberUtils.toDouble(configurationParams.getConfValue());
					}
					if(StringUtils.equals(configurationParams.getCode(), OutsideVehicleChargeConstants.DEFAULT_FINE_MAXFEE)){
						fineMaxFee = NumberUtils.toDouble(configurationParams.getConfValue());
					}
				}
				feeTot = feeTot / LoadConstants.SONAR_NUMBER_100;
				if(feeTot >= -fineMaxFee && feeTot <= awardMaxFee){
					//总费用效验通过
				}else{
					throw new TfrBusinessException("当前奖罚范围超出最大值, 请修改后再保存!");
				}
			}
		}
		}
		//end

		// 269701--lln--2015-07-13 begin
		//外请车费用次改日志表 新增一条修改记录
		addOutsideVehicleUpdateLog(adjustOutVehicleFeeEntity,OutsideVehicleChargeConstants.UPDATELOG);
		// 269701--lln--2015-07-13 end

		return outsideVehicleChargeDao.updateOutsideVehicleCharge(adjustOutVehicleFeeEntity);
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IOutsideVehicleChargeService#auditOutsideVehicleCharge(com.deppon.foss.module.transfer.load.api.shared.domain.AdjustOutVehicleFeeEntity)
	 */
	@Override
	@Transactional
	public int auditOutsideVehicleCharge(AdjustOutVehicleFeeEntity adjustOutVehicleFeeEntity) {
		UserEntity user = FossUserContext.getCurrentUser();
		EmployeeEntity emp = user.getEmployee();
		AdjustOutVehicleFeeDto dto = outsideVehicleChargeDao.queryById(adjustOutVehicleFeeEntity.getId());
		if(dto.getAuditState().equals(OutsideVehicleChargeConstants.AUDITPASS)){
			throw new TfrBusinessException("审核已通过的不可以再审核,请重新查询,核实状态");
		}
		if (validateOrgCode(dto.getOrigOrgCode())) {// 判断该条审核记录的审核人是不是当前部门
			// modify by liangfuxiang 2013-3-18下午2:16:20 begin 国际化
			// LOGGER.info("出发部门与当前部门是同一部门");
			LOGGER.info(OutsideVehicleChargeConstants.CURRENT_DEPT_SAME_AS_ORGINAL_DEPT);
			// modify by liangfuxiang 2013-3-18下午2:16:49 end;
		}
		else {
			// modify by liangfuxiang 2013-3-18下午2:02:05 begin 国际化
			// throw new TfrBusinessException("当前用户不可以审核，请重新登录", "");
			throw new TfrBusinessException(OutsideVehicleChargeConstants.CURRENT_USER_NO_AUDIT_RIGHT, BLANK_STRING);
			// modify by liangfuxiang 2013-3-18下午2:02:51 end;
		}
		if (emp != null) {
			adjustOutVehicleFeeEntity.setAuditorCode(emp.getEmpCode());
			adjustOutVehicleFeeEntity.setAuditorName(emp.getEmpName());
		}
		// return outsideVehicleChargeDao.updateOutsideVehicleCharge(adjustOutVehicleFeeEntity);
		// lln(269701)-2015-07-08下午 3:40 begin
		// 如果是整车配载单，点击通过按钮后FOSS中该条记录的审核状态变为“审批中”；
		//审核不通过 显示未通过   审核通过时 显示 审批中
		if(StringUtils.equals(adjustOutVehicleFeeEntity.getAuditState(), OutsideVehicleChargeConstants.AUDITPASS)){
			if (dto.getAssembleType().equals(OutsideVehicleChargeConstants.CAR_LOAD)) {
				adjustOutVehicleFeeEntity.setAuditState(OutsideVehicleChargeConstants.AUDITIN);
			}
		}
		// lln(269701)-2015-07-08下午3:40 end

		// 269701--lln--2015-07-13 begin
		// 将审核 不通过 or 通过 的外请车调整费用数据，记录到修改日志表中
		addOutsideVehicleUpdateLog(adjustOutVehicleFeeEntity,OutsideVehicleChargeConstants.AUDITLOG);
		// 269701--lln--2015-07-13 end
		// 只更新审核状态, 和审核意见
		return outsideVehicleChargeDao.checkOutsideVehicleCharge(adjustOutVehicleFeeEntity);
	}

	/**
	 * 审批界面操作
	 * 
	 * lln(269701)-2015-07-08下午 4:40 
	 * (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.load.api.server.service.
	 * IOutsideVehicleChargeService
	 * #approvalOutsideVehicleCharge(com.deppon.foss.
	 * module.transfer.load.api.shared.domain.AdjustOutVehicleFeeEntity)
	 */
	@Override
	@Transactional
	public int approvalOutsideVehicleCharge(AdjustOutVehicleFeeEntity adjustOutVehicleFeeEntity) {
		UserEntity user = FossUserContext.getCurrentUser();
		EmployeeEntity emp = user.getEmployee();
//		AdjustOutVehicleFeeDto dto = outsideVehicleChargeDao.queryById(adjustOutVehicleFeeEntity.getId());
//		if (validateOrgCode(dto.getOrigOrgCode())) {// 判断该条审批记录的审批人是不是当前部门
//			// LOGGER.info("出发部门与当前部门是同一部门");
//			LOGGER.info(OutsideVehicleChargeConstants.CURRENT_DEPT_SAME_AS_ORGINAL_DEPT);
//		} else {
//			// throw new TfrBusinessException("当前用户不可以审批，请重新登录", "");
//			throw new TfrBusinessException(
//				OutsideVehicleChargeConstants.CURRENT_USER_NO_APPROVAL_RIGHT,
//					BLANK_STRING);
//		}
		if (emp != null) {
			adjustOutVehicleFeeEntity.setAuditorCode(emp.getEmpCode());
			adjustOutVehicleFeeEntity.setAuditorName(emp.getEmpName());
		}
		// return
		// outsideVehicleChargeDao.updateOutsideVehicleCharge(adjustOutVehicleFeeEntity);
		// 269701--lln--2015-07-13 begin
		// 将审批不通过 or 通过 的外请车调整费用数据，记录到修改日志表中
		addOutsideVehicleUpdateLog(adjustOutVehicleFeeEntity,OutsideVehicleChargeConstants.APPROVALLOG);
		// 269701--lln--2015-07-13 end
		// 只更新审核状态(DB), 和审核意见(DB)
		return outsideVehicleChargeDao.checkOutsideVehicleCharge(adjustOutVehicleFeeEntity);
	}

	/**
	 * 外请车调整费用数据，记录到修改日志表中
	 *  
	 * 269701--lln--2015--07--15
	 * 
	 * @return void
	 */
	private void addOutsideVehicleUpdateLog(AdjustOutVehicleFeeEntity adjustOutVehicleFeeEntity, String flag) {
		// 获取当前用户信息
		UserEntity user = FossUserContext.getCurrentUser();
		EmployeeEntity emp = user.getEmployee();
		AdjustOutVehicleFeeLogEntity adjustOutVehicleFeeLogEntity = new AdjustOutVehicleFeeLogEntity();
		// 外请车费用修改日志ID
		adjustOutVehicleFeeLogEntity.setId(UUIDUtils.getUUID());
		// 外请车费用ID
		adjustOutVehicleFeeLogEntity.setAdjustOutVehicleFeeId(adjustOutVehicleFeeEntity.getId());
		// 操作人CODE
		adjustOutVehicleFeeLogEntity.setCreateUserCode(emp.getEmpCode());
		// 操作人---操作人为当前记录的操作人；
		adjustOutVehicleFeeLogEntity.setCreateUserName(emp.getEmpName());
		// 操作时间---操作时间为当前记录的操作时间；

		// 当前页面的数据
		//调整原因
		String adjustReason = adjustReasonStr(adjustOutVehicleFeeEntity.getTimelinessClause(),adjustOutVehicleFeeEntity.getCause()
				    ,adjustOutVehicleFeeEntity.getAdjustReason());
		//获取修改之后的增减类型/奖罚类型，即当前页面的
		String awardType = adjustOutVehicleFeeEntity.getAwardType();
		//配载车次号
		String vehicleassembleNo=adjustOutVehicleFeeEntity.getVehicleassembleNo();
		//获取修改之后的调整费用，即当前页面的调整费用
		BigDecimal adjustFee=adjustOutVehicleFeeEntity.getAdjustFee().divide(new BigDecimal(LoadConstants.INSURANCE_VALUE_MULTIPLY));
		//产生原因
		String cause=adjustOutVehicleFeeEntity.getCause();
		// 修改内容
		StringBuffer strLog = new StringBuffer();
		
		if (StringUtils.equals(flag, OutsideVehicleChargeConstants.ADDLOG)) {
			// 将新增外请车费用调整数据插入到 外请车费用修改日志表中
			// 操作内容---新增数据：配载单号+调整原因+增减类型+调整金额
			strLog.append("新增费用调整: 配载车次号:"+ vehicleassembleNo+";");
			if(StringUtils.equals(awardType, OutsideVehicleChargeConstants.ADJUST_TYPE_FEE_AUGMENT)){
				strLog.append("增减类型:费用增加;");
			}else if(StringUtils.equals(awardType,OutsideVehicleChargeConstants.ADJUST_TYPE_FEE_REDUCE)){
				strLog.append("增减类型:费用减少;");
			}
			strLog.append("调整原因:" + adjustReason+";");
			strLog.append("调整费用: "+ adjustFee+";");
			
			adjustOutVehicleFeeLogEntity.setModifyContent(strLog.toString());
			// 操作类别-----当某条记录新增之后，操作类别记录为“新增”；
			adjustOutVehicleFeeLogEntity.setLogType(OutsideVehicleChargeConstants.ADDLOG);
			
		} else if (StringUtils.equals(flag,OutsideVehicleChargeConstants.UPDATELOG)) {
			// 将修改的外请车调整费用数据，记录到修改日志表中
			//获取修改之前的数据
			AdjustOutVehicleFeeDto adjustOutVehicleFeeDto=queryById(adjustOutVehicleFeeEntity.getId());
			//获取修改之前的增减类型/奖罚类型
			String awardTypeOrg=adjustOutVehicleFeeDto.getAwardType();
			String awardTypeOrgValue="";
			if(StringUtils.equals(awardTypeOrg,OutsideVehicleChargeConstants.ADJUST_TYPE_FEE_AUGMENT)){
				awardTypeOrgValue="费用增加";
			}else if(StringUtils.equals(awardTypeOrg,OutsideVehicleChargeConstants.ADJUST_TYPE_FEE_REDUCE)){
				awardTypeOrgValue="费用减少";
			}else if(StringUtils.equals(awardTypeOrg,OutsideVehicleChargeConstants.REWARD)){
				awardTypeOrgValue="奖励";
			}else if(StringUtils.equals(awardTypeOrg,OutsideVehicleChargeConstants.FINE)){
				awardTypeOrgValue="罚款";
			}
			//获取修改之前的调整原因
			String adjustReasonOrg=adjustReasonStr(adjustOutVehicleFeeDto.getTimelinessClause(),adjustOutVehicleFeeDto.getCause(),adjustOutVehicleFeeDto.getAdjustReason());
			//获取修改之前的调整费用
			BigDecimal adjustFeeOrg=adjustOutVehicleFeeDto.getAdjustFee();
			//修改之前的产生原因
			String causeOrg=adjustOutVehicleFeeDto.getCause();

			strLog.append("修改调整费用:配载车次号"+ vehicleassembleNo+";");
			//根据是否有时效条款进行区分两个form
			 
			//该情况是没有时效条款
			if (!StringUtils.equals(adjustOutVehicleFeeDto.getTimelinessClause(), FossConstants.YES)) {
				// 该form有增减类型，没有产生原因标签
				// 操作内容---修改数据：配载单号+调整原因+增减类型+调整金额
				if (!StringUtils.equals(awardTypeOrg, awardType) && !StringUtils.equals(adjustReasonOrg, adjustReason) && adjustFeeOrg.compareTo(adjustFee) != 0) {
					// 增减类型，调整原因，调整费用均发生变化
					if (StringUtils.equals(awardType,OutsideVehicleChargeConstants.ADJUST_TYPE_FEE_AUGMENT)) {
						strLog.append("增减类型: 由"+ awardTypeOrgValue+"修改为 费用增加;");
					} else if (StringUtils.equals(awardType,OutsideVehicleChargeConstants.ADJUST_TYPE_FEE_REDUCE)) {
						strLog.append("增减类型: 由"+ awardTypeOrgValue+"修改为 费用减少;");
					}
					strLog.append("调整原因:由"+ adjustReasonOrg+"修改为 "+adjustReason+";");
					strLog.append("调整费用:由"+ adjustFeeOrg+"修改为 "+adjustFee+";");
					
				} else if (!StringUtils.equals(awardTypeOrg,awardType)&& !StringUtils.equals(adjustReasonOrg,adjustReason)
										&& adjustFeeOrg.compareTo(adjustFee) == 0) {
					// 增减类型，调整原因均发生变化，调整费用没有变化
					if (StringUtils.equals(awardType,OutsideVehicleChargeConstants.ADJUST_TYPE_FEE_AUGMENT)) {
						strLog.append("增减类型: 由"+ awardTypeOrgValue+"修改为 费用增加;");
					} else if (StringUtils.equals(awardType,OutsideVehicleChargeConstants.ADJUST_TYPE_FEE_REDUCE)) {
						strLog.append("增减类型: 由"+ awardTypeOrgValue+"修改为 费用减少;");
					}
					strLog.append("调整原因:由"+ adjustReasonOrg+"修改为 "+adjustReason+";");
					
				} else if(!StringUtils.equals(awardTypeOrg,awardType)&& StringUtils.equals(adjustReasonOrg,adjustReason)
						&& adjustFeeOrg.compareTo(adjustFee) != 0){
					// 增减类型，调整费用均发生变化，调整原因没有变化
					if (StringUtils.equals(awardType,OutsideVehicleChargeConstants.ADJUST_TYPE_FEE_AUGMENT)) {
						strLog.append("增减类型: 由"+ awardTypeOrgValue+"修改为 费用增加; ");
					} else if (StringUtils.equals(awardType,OutsideVehicleChargeConstants.ADJUST_TYPE_FEE_REDUCE)) {
						strLog.append("增减类型: 由"+ awardTypeOrgValue+"修改为 费用减少; ");
					}
					strLog.append("调整原因:由"+ adjustReasonOrg+"修改为 "+adjustReason+";");
					
				}else if(StringUtils.equals(awardTypeOrg,awardType)&& !StringUtils.equals(adjustReasonOrg,adjustReason)
						&& adjustFeeOrg.compareTo(adjustFee) != 0){
					// 调整原因，调整费用均发生变化，增减类型没有变化
					strLog.append("调整原因:由"+ adjustReasonOrg+"修改为 "+adjustReason+";");
					strLog.append("调整费用:由"+ adjustFeeOrg+"修改为 "+adjustFee+";");
					
				}else if (!StringUtils.equals(awardTypeOrg,awardType) && StringUtils.equals(adjustReasonOrg,adjustReason)
								&& adjustFeeOrg.compareTo(adjustFee) == 0) {
					// 只有增减类型发生变化，调整原因以及调整费用没有发生变化
					if (StringUtils.equals(awardType,OutsideVehicleChargeConstants.ADJUST_TYPE_FEE_AUGMENT)) {
						strLog.append("增减类型: 由"+ awardTypeOrgValue+"修改为 费用增加;");
					} else if (StringUtils.equals(awardType,OutsideVehicleChargeConstants.ADJUST_TYPE_FEE_REDUCE)) {
						strLog.append("增减类型: 由"+ awardTypeOrgValue+"修改为 费用减少;");
					}
					
				} else if (StringUtils.equals(awardTypeOrg,awardType) && !StringUtils.equals(adjustReasonOrg,adjustReason)
									&& adjustFeeOrg.compareTo(adjustFee) == 0) {
					// 只有调整原因发生变化，增减类型以及调整费用没有变化
					strLog.append("调整原因:由"+ adjustReasonOrg+"修改为 "+adjustReason+";");
					
				} else if (StringUtils.equals(awardTypeOrg,awardType)&& StringUtils.equals(adjustReasonOrg,adjustReason)
								&& adjustFeeOrg.compareTo(adjustFee) != 0) {
					// 只有调整费用发生变化,增减类型以及调整原因没有变化
					strLog.append("调整费用:由"+ adjustFeeOrg+"修改为 "+adjustFee+";");
				}

			} else{
				// 该form有产生原因，没有增减类型标签
				if(!StringUtils.equals(cause,causeOrg) && !StringUtils.equals(adjustReasonOrg,adjustReason) && adjustFeeOrg.compareTo(adjustFee)!=0){
					//产生原因，奖罚类型，调整原因以及调整费用发生变化
					if(StringUtils.equals(cause,OutsideVehicleChargeConstants.CAUSE_AGINGINADVANCE)){
						strLog.append("产生原因:由 时效延误修改为 时效提前 ; 奖罚类型:由" +awardTypeOrgValue +"奖励;");
					}else{
						strLog.append("产生原因:由 时效提前修改为 时效延误 ; 奖罚类型:由" +awardTypeOrgValue +"罚款;");
					}
					strLog.append("调整原因:由" + adjustReasonOrg+"修改为 "+adjustReason+";");
					strLog.append("调整费用:由"+ adjustFeeOrg+"修改为 "+adjustFee+";");
				} else if(!StringUtils.equals(cause,causeOrg) && !StringUtils.equals(adjustReasonOrg,adjustReason) && adjustFeeOrg.compareTo(adjustFee)==0){
					//产生原因，奖罚类型，以及调整原因发生变化，调整费用没有变化
					if(StringUtils.equals(cause,OutsideVehicleChargeConstants.CAUSE_AGINGINADVANCE)){
						strLog.append("产生原因:由 时效延误修改为 时效提前 ; 奖罚类型:由" +awardTypeOrgValue +"奖励;");
					}else{
						strLog.append("产生原因:由 时效提前修改为 时效延误 ; 奖罚类型:由" +awardTypeOrgValue +"罚款;");
					}
					strLog.append("调整原因:由" + adjustReasonOrg+"修改为 "+adjustReason+";");
				}else if(!StringUtils.equals(cause,causeOrg) && StringUtils.equals(adjustReasonOrg,adjustReason) && adjustFeeOrg.compareTo(adjustFee)==0){
					//产生原因，奖罚类型发生变化，调整原因以及调整费用没有变化
					if(StringUtils.equals(cause,OutsideVehicleChargeConstants.CAUSE_AGINGINADVANCE)){
						strLog.append("产生原因:由 时效延误修改为 时效提前 ; 奖罚类型:由" +awardTypeOrgValue +"奖励;");
					}else{
						strLog.append("产生原因:由 时效提前修改为 时效延误 ; 奖罚类型:由" +awardTypeOrgValue +"罚款;");
					}
					
				}else if(!StringUtils.equals(cause,causeOrg) && StringUtils.equals(adjustReasonOrg,adjustReason) && adjustFeeOrg.compareTo(adjustFee)==0){
					//产生原因，奖罚类型发生变化，调整费用发生变化，调整原因没有变化
					if(StringUtils.equals(cause,OutsideVehicleChargeConstants.CAUSE_AGINGINADVANCE)){
						strLog.append("产生原因:由 时效延误修改为 时效提前 ; 奖罚类型:由" +awardTypeOrgValue +"奖励;");
					}else{
						strLog.append("产生原因:由 时效提前修改为 时效延误 ; 奖罚类型:由" +awardTypeOrgValue +"罚款;");
					}
					strLog.append("调整费用:由"+ adjustFeeOrg+"修改为 "+adjustFee+";");
				}else if(!StringUtils.equals(cause,causeOrg) && StringUtils.equals(adjustReasonOrg,adjustReason) && adjustFeeOrg.compareTo(adjustFee)==0){
					//调整费用，调整原因发生变化，产生原因，奖罚类型没有变化
					strLog.append("调整原因:由" + adjustReasonOrg+"修改为 "+adjustReason+";");
					strLog.append("调整费用:由"+ adjustFeeOrg+"修改为 "+adjustFee+";");
					
				}else if(StringUtils.equals(cause,causeOrg) && !StringUtils.equals(adjustReasonOrg,adjustReason) && adjustFeeOrg.compareTo(adjustFee)==0){
					//只有 调整原因发生变化，调整费用以及产生原因，奖罚类型没有变化
					strLog.append("调整原因:由" + adjustReasonOrg+"修改为 "+adjustReason+";");
				}else if(StringUtils.equals(cause,causeOrg) && StringUtils.equals(adjustReasonOrg,adjustReason) && adjustFeeOrg.compareTo(adjustFee)!=0){
					//只有调整费用发生变化，调整原因以及产生原因，奖罚类型没有变化
					strLog.append("调整费用:由"+ adjustFeeOrg+"修改为 "+adjustFee+";");
				}
			}
			adjustOutVehicleFeeLogEntity.setModifyContent(strLog.toString());
			// 操作类别-----当某条记录新增之后，操作类别记录为“新增”；
			adjustOutVehicleFeeLogEntity.setLogType(OutsideVehicleChargeConstants.UPDATELOG);
		} else if (StringUtils.equals(flag,
				OutsideVehicleChargeConstants.APPROVALLOG)) {
			// 将审批不通过的外请车调整费用数据，记录到修改日志表中
			// 操作内容---当审批 通过/不通过时，操作内容显示为审批 通过/不通过意见；
			adjustOutVehicleFeeLogEntity.setModifyContent(adjustOutVehicleFeeEntity.getApprovalOpinion());
			// 根据审批状态判断 审批通过(AUDITTPASS) or 不通过(AUDITNOTPASS)
			if (StringUtils.equals(adjustOutVehicleFeeEntity.getAuditState(),
					OutsideVehicleChargeConstants.AUDITNOTPASS)) {
				// 操作类别---当某条记录审批不通过之后，操作类别记录为“退回”
				adjustOutVehicleFeeLogEntity.setLogType(OutsideVehicleChargeConstants.BACKLOG);
			} else {
				// 操作类别---当某条记录审批通过之后，操作类别记录为“审批”
				adjustOutVehicleFeeLogEntity
						.setLogType(OutsideVehicleChargeConstants.APPROVALLOG);
			}
		} else if (StringUtils.equals(flag,OutsideVehicleChargeConstants.AUDITLOG)) {
			// 操作内容---当审核 通过/不通过时，操作内容显示为审核 通过/不通过意见；
			adjustOutVehicleFeeLogEntity.setModifyContent(adjustOutVehicleFeeEntity.getAuditOpinion());
			// 根据审核状态判断 审批通过(AUDITTPASS) or 不通过(AUDITNOTPASS)
			if (StringUtils.equals(adjustOutVehicleFeeEntity.getAuditState(),OutsideVehicleChargeConstants.AUDITNOTPASS)) {
				// 操作类别---当某条记录审核不通过之后，操作类别记录为“退回”
				adjustOutVehicleFeeLogEntity.setLogType(OutsideVehicleChargeConstants.BACKLOG);
			} else {
				// 操作类别---当某条记录审核通过之后，操作类别记录为“审核”
				adjustOutVehicleFeeLogEntity.setLogType(OutsideVehicleChargeConstants.AUDITLOG);
			}
		}
		//记录到DB
		outsideVehicleChargeDao.addOutsideVehicleUpdateLog(adjustOutVehicleFeeLogEntity);

	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IOutsideVehicleChargeService#getOutsideVehicleChargeById(java.lang.String)
	 */
	@Override
	public AdjustOutVehicleFeeEntity getOutsideVehicleChargeById(String id) {
		AdjustOutVehicleFeeEntity adjustOutVehicleFeeEntity = outsideVehicleChargeDao.getOutsideVehicleChargeById(id);
		return adjustOutVehicleFeeEntity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.load.api.server.service.
	 * IOutsideVehicleChargeService#deleteOutsideVehicleCharge(java.lang.String)
	 */
	@Override
	@Transactional
	public int deleteOutsideVehicleCharge(String id) {
		AdjustOutVehicleFeeDto adjustOutVehicleFeeDto = outsideVehicleChargeDao.queryById(id);
		// modify by liangfuxiang 013-03-13 出发部门删除 begin

		/*
		 * if(StringUtils.equals(entity.getCode(), dto.getDestOrgCode())){
		 * LOGGER.info("到达部门与当前部门是同一部门"); } else
		 * if(StringUtils.equals(entity.getCode(), dto.getOrigOrgCode())) {
		 * throw new TfrBusinessException("当前用户不可以删除，请重新登录", ""); } else{ throw
		 * new TfrBusinessException("当前用户不可以删除，请重新登录", ""); }
		 */

		if (validateOrgCode(adjustOutVehicleFeeDto.getOrigOrgCode())) {
			// modify by liangfuxiang 2013-3-18下午2:22:01 begin 国际化
			// LOGGER.info("到达部门与当前部门是同一部门");
			LOGGER.info(OutsideVehicleChargeConstants.CURRENT_DEPT_SAME_AS_DEST_DEPT);
			// modify by liangfuxiang 2013-3-18下午2:22:11 end;
		} else {
			// modify by liangfuxiang 2013-3-18下午2:24:32 begin 国际化
			// throw new TfrBusinessException("当前用户不可以删除，请重新登录", "");
			throw new TfrBusinessException(
					OutsideVehicleChargeConstants.CURRENT_USER_NO_DELETE_RIGHT,
					BLANK_STRING);
			// modify by liangfuxiang 2013-3-18下午2:24:57 end;
		}
		//269701--lln--2015-09-09 begin
		//配载车次号为整车配载车次号，则调整费用文本框不受配置参数的限制，该文本框中只可输入正整数；
		if(!StringUtils.equals(adjustOutVehicleFeeDto.getAssembleType(),OutsideVehicleChargeConstants.CAR_LOAD)){
		//269701--lln--2015-09-09 end
		// 2013年12月28日 10:41:43 modify by zyx begin
		// 此处逻辑完全脱胎于前台js修改费用处
		if (!StringUtils.equals(adjustOutVehicleFeeDto.getTimelinessClause(),FossConstants.YES)) {
			// 非时效费用修改时, 需要判断该配载单的奖励活惩罚的总金额不能大于配置的总金额
			List<AdjustOutVehicleFeeDto> adjustOutVehicleFeeDtos = queryOutsideVehicleChargeByVehicleassembleNo(adjustOutVehicleFeeDto
					.getVehicleassembleNo());
			if (adjustOutVehicleFeeDtos.size() > 0) {
				BigDecimal awardFee = BigDecimal.ZERO;
				BigDecimal fineFee = BigDecimal.ZERO;
				for (AdjustOutVehicleFeeDto dto : adjustOutVehicleFeeDtos) {
					if (!StringUtils.equals(adjustOutVehicleFeeDto.getId(),dto.getId())) {// ID 相同则为同一个对象, 不计入统计
						if (StringUtils.equals(dto.getAwardType(),OutsideVehicleChargeConstants.ADJUST_TYPE_FEE_AUGMENT)
								&& !StringUtils.equals(dto.getTimelinessClause(),FossConstants.YES)) {
							// 该配载单奖励总金额
							awardFee = awardFee.add(dto.getAdjustFee());
							continue;
						}
						if (StringUtils.equals(dto.getAwardType(),OutsideVehicleChargeConstants.ADJUST_TYPE_FEE_REDUCE)
								&& !StringUtils.equals(dto.getTimelinessClause(),FossConstants.YES)) {
							// 该配载单罚款总金额
							fineFee = fineFee.add(dto.getAdjustFee());
							continue;
						}
					}
				}
				double feeTot = 0.0;
				// 费用删除时, 调整总费用 = 该单据所有增加的费用 - 费用减少的费用
				// 调整总费用不能大于配置的费用
				feeTot = awardFee.doubleValue() - fineFee.doubleValue();
				List<ConfigurationParamsEntity> configurationParamss = queryConfigurationParamsExactByEntity(
						Integer.MAX_VALUE, Integer.MAX_VALUE);
				double awardMaxFee = 0.00;
				double fineMaxFee = 0.00;
				// 获取配置参数中配置的奖励, 罚款最大值
				for (ConfigurationParamsEntity configurationParams : configurationParamss) {
					if (StringUtils.equals(configurationParams.getCode(),
							OutsideVehicleChargeConstants.DEFAULT_AWARD_MAXFEE)) {
						awardMaxFee = NumberUtils.toDouble(configurationParams.getConfValue());
					}
					if (StringUtils.equals(configurationParams.getCode(),OutsideVehicleChargeConstants.DEFAULT_FINE_MAXFEE)) {
						fineMaxFee = NumberUtils.toDouble(configurationParams.getConfValue());
					}
				}
				if (feeTot >= -fineMaxFee && feeTot <= awardMaxFee) {
					// 总费用效验通过
				} else {
					throw new TfrBusinessException("不能删除, 删除后将超过当前奖罚范围最大值!");
				}
			}
		}
		}
		// end
		//将删除操作记录到操作log表中
		//269701--foss--2015-07-25
		addOutsideVehicleUpdateLogDeleteLog(adjustOutVehicleFeeDto);
		
		// modify by liangfuxiang 013-03-13 出发部门删除 end
		outsideVehicleChargeDao.deleteOutsideVehicleCharge(id);
		return 0;
	}

	/**
	 * 删除操作时，将该操作记录到log
	 * @author 269701--foss--lln
	 * @date 2015-07-25
	 * 
	 */
	private void addOutsideVehicleUpdateLogDeleteLog(AdjustOutVehicleFeeDto adjustOutVehicleFeeDto){
		// 获取当前用户信息
		UserEntity user = FossUserContext.getCurrentUser();
		EmployeeEntity emp = user.getEmployee();
		AdjustOutVehicleFeeLogEntity adjustOutVehicleFeeLogEntity = new AdjustOutVehicleFeeLogEntity();
		// 外请车费用修改日志ID
		adjustOutVehicleFeeLogEntity.setId(UUIDUtils.getUUID());
		// 外请车费用ID
		adjustOutVehicleFeeLogEntity.setAdjustOutVehicleFeeId(adjustOutVehicleFeeDto.getId());
		// 操作人CODE
		adjustOutVehicleFeeLogEntity.setCreateUserCode(emp.getEmpCode());
		// 操作人---操作人为当前记录的操作人；
		adjustOutVehicleFeeLogEntity.setCreateUserName(emp.getEmpName());
		// 操作时间---操作时间为当前记录的操作时间；
		// 修改内容
		StringBuffer strLog = new StringBuffer();
		strLog.append("删除调整费用:配载车次号"+ adjustOutVehicleFeeDto.getVehicleassembleNo()+";");
		strLog.append("车牌号："+ adjustOutVehicleFeeDto.getVehicleNo()+";");
		strLog.append("出发部门名称："+ adjustOutVehicleFeeDto.getDestOrgName()+";");
		strLog.append("到达部门名称："+ adjustOutVehicleFeeDto.getDestOrgName()+";");
		strLog.append("该条数据ID:"+ adjustOutVehicleFeeDto.getId()+";");
		strLog.append("调整费用:"+ adjustOutVehicleFeeDto.getAdjustFee()+";");
		
		adjustOutVehicleFeeLogEntity.setModifyContent(strLog.toString());
		
		adjustOutVehicleFeeLogEntity.setLogType(OutsideVehicleChargeConstants.DELETELOG);
		//记录到DB
		outsideVehicleChargeDao.addOutsideVehicleUpdateLog(adjustOutVehicleFeeLogEntity);
	}
	
	
	/* (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IOutsideVehicleChargeService#queryByVehicleassembleNo(java.lang.String)
	 */
	@Override
	public AdjustOutVehicleFeeDto queryByVehicleassembleNo(String vehicleassembleNo) {
		//校验是否为空
		if(StringUtils.isBlank(vehicleassembleNo)){
			// throw new TfrBusinessException("配载车次号输入有误，请重新输入配载车次号", "");
			throw new TfrBusinessException(OutsideVehicleChargeConstants.WRONG_WAYBILLNO, BLANK_STRING);
		}
		//获取配载单基本信息
		List<VehicleAssembleBillEntity> assembleBillEntities = vehicleAssembleBillService.queryVehicleAssembleBillByNo(vehicleassembleNo);
		// 增加ECS系统开关
		if (configurationParamsService.queryTfrSwitch4Ecs()) {
			/**如果获取配载单基本信息为空，就查询快递交接单信息283250*/
			if(assembleBillEntities.size() < 1){
				assembleBillEntities=vehicleAssembleBillService.queryVehicleAssembleBillByNoFromWk(vehicleassembleNo);
			}
		}
		
		if(assembleBillEntities.size() < 1){
			// throw new TfrBusinessException("配载车次号输入有误，请重新输入配载车次号", "");
			throw new TfrBusinessException(OutsideVehicleChargeConstants.WRONG_WAYBILLNO, BLANK_STRING);
		}else{
			int state = assembleBillEntities.get(0).getState();
			//如果状态为未发车，则抛出异常
			if(state == LoadConstants.VEHICLEASSEMBLEBILL_STATE_NOT_DEPART){
				//必须在车次发车之后才能调整外请车费用
				throw new TfrBusinessException(OutsideVehicleChargeConstants.MUST_AFTER_LEAVE);
			}
		}
		AdjustOutVehicleFeeDto dto = outsideVehicleChargeDao.queryByVehicleassembleNo(vehicleassembleNo);
		
		//增加悟空开关
		if (configurationParamsService.queryTfrSwitch4Ecs()) {
			if (dto == null) {
				/**如果返回信息null就查询快递交接单返回相应的信息*/
				dto=outsideVehicleChargeDao.queryByVehicleassembleNoFromWk(vehicleassembleNo);
			}
		}
		
		if (dto == null) {
			// modify by liangfuxiang 2013-3-18下午2:29:16 begin 国际化
			// throw new TfrBusinessException("配载车次号输入有误，请重新输入配载车次号", "");
			throw new TfrBusinessException(OutsideVehicleChargeConstants.WRONG_WAYBILLNO, BLANK_STRING);
			// modify by liangfuxiang 2013-3-18下午2:45:28 end;
		}
		else {
			// modify by liangfuxiang 013-03-14 出发部门删除 begin
			// if(StringUtils.equals(entity.getCode(), dto.getDestOrgCode())){
			if (validateOrgCode(dto.getOrigOrgCode())) {
				// modify by liangfuxiang 013-03-14 出发部门删除 end
				// modify by liangfuxiang 2013-3-18下午2:22:01 begin 国际化
				// LOGGER.info("到达部门与当前部门是同一部门");
				LOGGER.info(OutsideVehicleChargeConstants.CURRENT_DEPT_SAME_AS_DEST_DEPT);
				// modify by liangfuxiang 2013-3-18下午2:22:11 end;
			}
			else {
				// modify by liangfuxiang 2013-3-18下午2:36:57 begin 国际化
				// throw new TfrBusinessException("当前用户不可以申请费用，请重新登录", "");
				throw new TfrBusinessException(OutsideVehicleChargeConstants.CURRENT_USER_NO_APPLY_RIGHT, BLANK_STRING);
				// modify by liangfuxiang 2013-3-18下午2:37:08 end;
			}
			if (StringUtils.equalsIgnoreCase(ComnConst.ASSETS_OWNERSHIP_TYPE_LEASED, dto.getVehicleOwnerShip())) {
				// modify by liangfuxiang 2013-3-18下午2:50:32 begin 国际化
				// LOGGER.info("是外请车！");
				LOGGER.info(OutsideVehicleChargeConstants.BE_OUTSIDEVEHICLE);
				// modify by liangfuxiang 2013-3-18下午2:50:43 end;
			}
			else {
				// modify by liangfuxiang 2013-3-18下午2:41:34 begin 国际化
				// throw new TfrBusinessException("公司车不可以调整费用", "");
				throw new TfrBusinessException(OutsideVehicleChargeConstants.COMPANY_VEHICLE_CANNOT_ADJUST, BLANK_STRING);
				// modify by liangfuxiang 2013-3-18下午2:41:56 end;
			}
			
			//外请车是在途装卸，不可以调整外请车费用
			if(dto.getBeMidwayload().endsWith(FossConstants.YES)&&dto.getBefinallyarrive().equals(FossConstants.NO)){
				throw new TfrBusinessException(OutsideVehicleChargeConstants.ON_WAY_UNLOAD);
			}
		}
		return dto;
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IOutsideVehicleChargeService#queryById(java.lang.String)
	 */
	@Override
	public AdjustOutVehicleFeeDto queryById(String id) {
		return outsideVehicleChargeDao.queryById(id);
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IOutsideVehicleChargeService#adjustOutVehicleFee(com.deppon.foss.module.transfer.load.api.shared.dto.AdjustOutVehicleFeeDto)
	 */
	@Override
	public int adjustOutVehicleFee(AdjustOutVehicleFeeDto dto) {

		AdjustOutVehicleFeeDto vehicleFeeDto = outsideVehicleChargeDao.queryById(dto.getId());
		if (validateOrgCode(vehicleFeeDto.getOrigOrgCode())) {
			// modify by liangfuxiang 2013-3-18下午2:12:42 begin 国际化
			// LOGGER.info("出发部门与当前部门是同一部门");
			LOGGER.info(OutsideVehicleChargeConstants.CURRENT_DEPT_SAME_AS_ORGINAL_DEPT);
			// modify by liangfuxiang 2013-3-18下午2:12:54 end;
		} else {
			// modify by liangfuxiang 2013-3-18下午2:02:05 begin 国际化
			// throw new TfrBusinessException("当前用户不可以审核，请重新登录", "");
			throw new TfrBusinessException(
					OutsideVehicleChargeConstants.CURRENT_USER_NO_AUDIT_RIGHT,
					BLANK_STRING);
			// modify by liangfuxiang 2013-3-18下午2:02:51 end;
		}

		// add by liangfuxiang 2013-03-14 begin ISSUE-1930
		// 外请车费用调整申请的时候，调用结算接口，判断是否可以申请
		String vehicleAssembleNo = dto.getVehicleassembleNo();

		// 配载单号非空性校验
		if (StringUtils.isEmpty(StringUtils.trim(vehicleAssembleNo))) {
			// 配载单号为空,抛出异常
			LOGGER.error(OutsideVehicleChargeConstants.OUTSIDEVEHICLECHARGE_WILLWAYNO_EMPTY_LOG);
			throw new TfrBusinessException(OutsideVehicleChargeConstants.OUTSIDEVEHICLECHARGE_WILLWAYNO_EMPTY,
					BLANK_STRING);
		}
		// 判断是否有权限申请,调用结算返回false代表未审核，否则不能操作
		if (queryBillPayableIsWriteOff(vehicleAssembleNo, FossConstants.YES)) {
			// 无权限，则告知无法申请。
			LOGGER.info(OutsideVehicleChargeConstants.OUTSIDEVEHICLECHARGE_BILLPAYABLE_FALSE_LOG);
			throw new TfrBusinessException(OutsideVehicleChargeConstants.OUTSIDEVEHICLECHARGE_BILLPAYABLE_FALSE,
					BLANK_STRING);
		}

		// add by liangfuxiang 2013-03-14 end

		//269701---foss---lln---2015-07-27 begin
		//外请车费用调整申请的时候，调用结算接口
		//审核界面
		List<AdjustOutVehicleFeeDto> list = outsideVehicleChargeDao.queryOutsideVehicleChargeBy(dto.getVehicleassembleNo());
		//增加快递开关
		if (configurationParamsService.queryTfrSwitch4Ecs()) {
			/**查询快递部分  长途外请车部分*/
			if(list==null||list.size()<=0){
				list=outsideVehicleChargeDao.queryOutsideVehicleChargeByFromWk(dto.getVehicleassembleNo());
			}
		}
		String waybillNo = null;
		//整车时候，审核只改变审核状态--审批中
		//由1421行的 AdjustOutVehicleFeeDto adjustOutVehicleFeeDto = list.get(0);可知， 上述查询获取数据只有一条
		if(StringUtils.equals(LoadConstants.VEHICLE_ASSEMBLE_TYPE_CAR_LOAD,list.get(0).getAssembleType())){
			//只改变审核状态
			return 1;
		}else{
			//专线时候，调用结算接口
			if (list.size() > 0) {
//				for (AdjustOutVehicleFeeDto feeDto : list) {
//					if (StringUtils.equals(LoadConstants.VEHICLE_ASSEMBLE_TYPE_CAR_LOAD,feeDto.getAssembleType())) {// 判断外请车是不是整车
//							waybillNo = feeDto.getWaybillNo();
//						}
//				}
					AdjustOutVehicleFeeDto adjustOutVehicleFeeDto = list.get(0);
					StlVehicleAssembleBillDto stlVehicleAssembleBillDto = new StlVehicleAssembleBillDto();
					stlVehicleAssembleBillDto.setAdjustFee(dto.getAdjustFee());
					stlVehicleAssembleBillDto.setArriveFeeTotal(adjustOutVehicleFeeDto.getArriveFeeTotal());
					stlVehicleAssembleBillDto.setAssembleType(adjustOutVehicleFeeDto.getAssembleType());
					stlVehicleAssembleBillDto.setAuditState(dto.getAuditState());
					//cubc参数
					CubcVehicleAssembleBillRequest request = new CubcVehicleAssembleBillRequest();
					request.setAdjustFee(dto.getAdjustFee());
					request.setArriveFeeTotal(adjustOutVehicleFeeDto.getArriveFeeTotal());
					request.setAssembleType(adjustOutVehicleFeeDto.getAssembleType());
					request.setAuditState(dto.getAuditState());
					// 费用增加 = 奖励, 费用减少 = 罚款
					String awardType = dto.getAwardType();
					if (StringUtils.equals(awardType,OutsideVehicleChargeConstants.ADJUST_TYPE_FEE_AUGMENT)) {
						// 费用增加 则传过去的是奖励
						awardType = OutsideVehicleChargeConstants.REWARD;
					} else if (StringUtils.equals(awardType,OutsideVehicleChargeConstants.ADJUST_TYPE_FEE_REDUCE)) {
						// 费用减少 则传过去的是罚款
						awardType = OutsideVehicleChargeConstants.FINE;
					}
					// 判定该配载单是否已经做到达 alfred 2014-05-29
					List<TruckTaskDetailEntity> truckTaskDetailList = truckTaskService.queryTruckTaskDetail(dto.getVehicleassembleNo());
					if (truckTaskDetailList.size() > 0) {
						TruckTaskDetailEntity truckTaskDetailEntity = truckTaskDetailList.get(0);
						if (truckTaskDetailEntity.getState().equals(TaskTruckConstant.TASK_TRUCK_STATE_ARRIVED)
								|| truckTaskDetailEntity.getState().equals(TaskTruckConstant.TASK_TRUCK_STATE_UNLOADED)) {
							stlVehicleAssembleBillDto.setIsArrive("Y");
							stlVehicleAssembleBillDto.setArriveTime(truckTaskDetailEntity.getActualArriveTime());
							request.setIsArrive("Y");
							request.setArriveTime(truckTaskDetailEntity.getActualArriveTime());
						} else {
							stlVehicleAssembleBillDto.setIsArrive("N");
							request.setIsArrive("N");
						}

					}
					stlVehicleAssembleBillDto.setAwardType(awardType);
					stlVehicleAssembleBillDto.setBeReturnReceipt(adjustOutVehicleFeeDto.getBeReturnReceipt());
					stlVehicleAssembleBillDto.setCurrencyCode(adjustOutVehicleFeeDto.getCurrencyCode());
					stlVehicleAssembleBillDto.setDestOrgCode(adjustOutVehicleFeeDto.getDestOrgCode());
					stlVehicleAssembleBillDto.setDestOrgName(adjustOutVehicleFeeDto.getDestOrgName());
					stlVehicleAssembleBillDto.setDriverName(adjustOutVehicleFeeDto.getDriverName());
					stlVehicleAssembleBillDto.setDriverCode(adjustOutVehicleFeeDto.getDriverCode());
					stlVehicleAssembleBillDto.setDestOrgCode(adjustOutVehicleFeeDto.getDestOrgCode());
					stlVehicleAssembleBillDto.setFeeTotal(adjustOutVehicleFeeDto.getFeeTotal());
					stlVehicleAssembleBillDto.setLeaveTime(adjustOutVehicleFeeDto.getLeaveTime());
					stlVehicleAssembleBillDto.setOrigOrgCode(adjustOutVehicleFeeDto.getOrigOrgCode());
					stlVehicleAssembleBillDto.setOrigOrgName(adjustOutVehicleFeeDto.getOrigOrgName());
					stlVehicleAssembleBillDto.setPaymentType(adjustOutVehicleFeeDto.getPaymentType());
					stlVehicleAssembleBillDto.setPrePaidFeeTotal(adjustOutVehicleFeeDto.getPrepaidFeeTotal());
					stlVehicleAssembleBillDto.setVehicleAssembleNo(adjustOutVehicleFeeDto.getVehicleassembleNo());
					stlVehicleAssembleBillDto.setVehicleNo(adjustOutVehicleFeeDto.getVehicleNo());
					stlVehicleAssembleBillDto.setVehicleOwnerShip(adjustOutVehicleFeeDto.getVehicleOwnerShip());
					stlVehicleAssembleBillDto.setWaybillNo(waybillNo);
					CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
					
					request.setAwardType(awardType);
					request.setBeReturnReceipt(adjustOutVehicleFeeDto.getBeReturnReceipt());
					request.setCurrencyCode(adjustOutVehicleFeeDto.getCurrencyCode());
					request.setDestOrgCode(adjustOutVehicleFeeDto.getDestOrgCode());
					request.setDestOrgName(adjustOutVehicleFeeDto.getDestOrgName());
					request.setDriverName(adjustOutVehicleFeeDto.getDriverName());
					request.setDriverCode(adjustOutVehicleFeeDto.getDriverCode());
					request.setDestOrgCode(adjustOutVehicleFeeDto.getDestOrgCode());
					request.setFeeTotal(adjustOutVehicleFeeDto.getFeeTotal());
					request.setLeaveTime(adjustOutVehicleFeeDto.getLeaveTime());
					request.setOrigOrgCode(adjustOutVehicleFeeDto.getOrigOrgCode());
					request.setOrigOrgName(adjustOutVehicleFeeDto.getOrigOrgName());
					request.setPaymentType(adjustOutVehicleFeeDto.getPaymentType());
					request.setPrePaidFeeTotal(adjustOutVehicleFeeDto.getPrepaidFeeTotal());
					request.setVehicleAssembleNo(adjustOutVehicleFeeDto.getVehicleassembleNo());
					request.setVehicleNo(adjustOutVehicleFeeDto.getVehicleNo());
					request.setVehicleOwnerShip(adjustOutVehicleFeeDto.getVehicleOwnerShip());
					request.setWaybillNo(waybillNo);
					request.setEmpCode(FossUserContext.getCurrentInfo().getEmpCode());
					request.setEmpName(FossUserContext.getCurrentInfo().getEmpName());
					request.setCurrentDeptCode(FossUserContext.getCurrentInfo().getCurrentDeptCode());
					request.setCurrentDeptName(FossUserContext.getCurrentInfo().getCurrentDeptName());
					if (stlVehicleAssembleBillDto.getAdjustFee() == null
							|| Double.valueOf(0).equals(stlVehicleAssembleBillDto.getAdjustFee().doubleValue())) {
						return 1;
					}
					//灰度DTO封装
					GrayParameterDto parDto = new GrayParameterDto();
					parDto.setSourceBillType(CUBCGrayUtil.SBType.PZ.getName());
					parDto.setSourceBillNos(new String[] {stlVehicleAssembleBillDto.getVehicleAssembleNo()});
					//调用灰度工具类方法
					VestResponse vestResponse = cubcUtil.getUcbcGrayData(parDto, new Throwable());
					if (CUBCGrayContants.SYSTEM_CODE_FOSS.equals(vestResponse.getVestBatchResult().get(0).getVestSystemCode())) {
						truckStowageService.adjustOutVehicleFee(stlVehicleAssembleBillDto,currentInfo);
					} else {
						if (request.getAdjustFee() == null || request.getAdjustFee().doubleValue() == 0) {
							return 1;
						}
						LOGGER.error("FOSS推送费用调整审批通过调用CUBC接口,配载车次号" + request.getVehicleAssembleNo() + "给CUBC开始...");
						CubcVehicleAssembleBillResponse response = null;
						try {
							// 调用cubc接口
							response = fossToCubc.pushadjustOutVehicleFee(request);
							if (null == response) {
								throw new TfrBusinessException("FOSS推送费用调整审批通过调用CUBC接口时ESB发生异常!");
							}
							if (StringUtils.equals("0", response.getResult())) {
								throw new TfrBusinessException("FOSS推送费用调整审批通过调用CUBC接口操作时失败,失败消息:" + response.getReason());
							}
	
						} catch (Exception e) {
							throw new TfrBusinessException("FOSS推送费用调整审批通过调用CUBC接口操作时失败"+e);
						}
						LOGGER.error("FOSS推送费用调整审批通过调用CUBC接口,配载车次号" + request.getVehicleAssembleNo() + "给CUBC结束;返回值是 :"+response);
					}
				
					return 1;
				} else {
					return 0;
				}
			}
		}

	/**
	 * 调整外请车费用，审批通过之后，调用结算接口
	 * @author 269701--foss--lln
	 * @date 2015--07--27
	 * (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IOutsideVehicleChargeService#approvalAdjustOutVehicleFee(com.deppon.foss.module.transfer.load.api.shared.dto.AdjustOutVehicleFeeDto)
	 */
	@Override
	public int approvalAdjustOutVehicleFee(AdjustOutVehicleFeeDto dto) {

//		AdjustOutVehicleFeeDto vehicleFeeDto = outsideVehicleChargeDao.queryById(dto.getId());
//		if (validateOrgCode(vehicleFeeDto.getOrigOrgCode())) {
//			// LOGGER.info("出发部门与当前部门是同一部门");
//			LOGGER.info(OutsideVehicleChargeConstants.CURRENT_DEPT_SAME_AS_ORGINAL_DEPT);
//		} else {
//			// throw new TfrBusinessException("当前用户不可以审核，请重新登录", "");
//			throw new TfrBusinessException(
//					OutsideVehicleChargeConstants.CURRENT_USER_NO_AUDIT_RIGHT,
//					BLANK_STRING);
//		}

		// 外请车费用调整申请的时候，调用结算接口，判断是否可以申请
		String vehicleAssembleNo = dto.getVehicleassembleNo();

		// 配载单号非空性校验
		if (StringUtils.isEmpty(StringUtils.trim(vehicleAssembleNo))) {
			// 配载单号为空,抛出异常
			LOGGER.error(OutsideVehicleChargeConstants.OUTSIDEVEHICLECHARGE_WILLWAYNO_EMPTY_LOG);
			throw new TfrBusinessException(OutsideVehicleChargeConstants.OUTSIDEVEHICLECHARGE_WILLWAYNO_EMPTY,
					BLANK_STRING);
		}
		// 判断是否有权限申请,调用结算返回false代表未审核，否则不能操作
		if (queryBillPayableIsWriteOff(vehicleAssembleNo, FossConstants.YES)) {
			// 无权限，则告知无法申请。
			LOGGER.info(OutsideVehicleChargeConstants.OUTSIDEVEHICLECHARGE_BILLPAYABLE_FALSE_LOG);
			throw new TfrBusinessException(OutsideVehicleChargeConstants.OUTSIDEVEHICLECHARGE_BILLPAYABLE_FALSE,
					BLANK_STRING);
		}

		//审批界面
		//外请车费用调整申请的时候，调用结算接口
		List<AdjustOutVehicleFeeDto> list = outsideVehicleChargeDao.queryOutsideVehicleChargeBy(dto.getVehicleassembleNo());
		if (list.size() > 0) {
			AdjustOutVehicleFeeDto adjustOutVehicleFeeDto = list.get(0);
			StlVehicleAssembleBillDto stlVehicleAssembleBillDto = new StlVehicleAssembleBillDto();
			CubcVehicleAssembleBillRequest  request = new CubcVehicleAssembleBillRequest();
			stlVehicleAssembleBillDto.setAdjustFee(dto.getAdjustFee());
			stlVehicleAssembleBillDto.setArriveFeeTotal(adjustOutVehicleFeeDto.getArriveFeeTotal());
			stlVehicleAssembleBillDto.setAssembleType(adjustOutVehicleFeeDto.getAssembleType());
			stlVehicleAssembleBillDto.setAuditState(dto.getAuditState());
			request.setAdjustFee(dto.getAdjustFee());
			request.setArriveFeeTotal(adjustOutVehicleFeeDto.getArriveFeeTotal());
			request.setAssembleType(adjustOutVehicleFeeDto.getAssembleType());
			request.setAuditState(dto.getAuditState());
			// 费用增加 = 奖励, 费用减少 = 罚款
			String awardType = dto.getAwardType();
			if (StringUtils.equals(awardType,OutsideVehicleChargeConstants.ADJUST_TYPE_FEE_AUGMENT)) {
				// 费用增加 则传过去的是奖励
				awardType = OutsideVehicleChargeConstants.REWARD;
			} else if (StringUtils.equals(awardType,OutsideVehicleChargeConstants.ADJUST_TYPE_FEE_REDUCE)) {
				// 费用减少 则传过去的是罚款
				awardType = OutsideVehicleChargeConstants.FINE;
			}
			// 判定该配载单是否已经做到达 alfred 2014-05-29
			List<TruckTaskDetailEntity> truckTaskDetailList = truckTaskService.queryTruckTaskDetail(dto.getVehicleassembleNo());
			if (truckTaskDetailList.size() > 0) {
				TruckTaskDetailEntity truckTaskDetailEntity = truckTaskDetailList.get(0);
				if (truckTaskDetailEntity.getState().equals(TaskTruckConstant.TASK_TRUCK_STATE_ARRIVED)
						|| truckTaskDetailEntity.getState().equals(TaskTruckConstant.TASK_TRUCK_STATE_UNLOADED)) {
					stlVehicleAssembleBillDto.setIsArrive("Y");
					stlVehicleAssembleBillDto.setArriveTime(truckTaskDetailEntity.getActualArriveTime());
					request.setIsArrive("Y");
					request.setArriveTime(truckTaskDetailEntity.getActualArriveTime());
				} else {
					stlVehicleAssembleBillDto.setIsArrive("N");
					request.setIsArrive("N");
				}

			}
			stlVehicleAssembleBillDto.setAwardType(awardType);
			stlVehicleAssembleBillDto.setBeReturnReceipt(adjustOutVehicleFeeDto.getBeReturnReceipt());
			stlVehicleAssembleBillDto.setCurrencyCode(adjustOutVehicleFeeDto.getCurrencyCode());
			stlVehicleAssembleBillDto.setDestOrgCode(adjustOutVehicleFeeDto.getDestOrgCode());
			stlVehicleAssembleBillDto.setDestOrgName(adjustOutVehicleFeeDto.getDestOrgName());
			stlVehicleAssembleBillDto.setDriverName(adjustOutVehicleFeeDto.getDriverName());
			stlVehicleAssembleBillDto.setDriverCode(adjustOutVehicleFeeDto.getDriverCode());
			stlVehicleAssembleBillDto.setDestOrgCode(adjustOutVehicleFeeDto.getDestOrgCode());
			stlVehicleAssembleBillDto.setFeeTotal(adjustOutVehicleFeeDto.getFeeTotal());
			stlVehicleAssembleBillDto.setLeaveTime(adjustOutVehicleFeeDto.getLeaveTime());
			stlVehicleAssembleBillDto.setOrigOrgCode(adjustOutVehicleFeeDto.getOrigOrgCode());
			stlVehicleAssembleBillDto.setOrigOrgName(adjustOutVehicleFeeDto.getOrigOrgName());
			stlVehicleAssembleBillDto.setPaymentType(adjustOutVehicleFeeDto.getPaymentType());
			stlVehicleAssembleBillDto.setPrePaidFeeTotal(adjustOutVehicleFeeDto.getPrepaidFeeTotal());
			stlVehicleAssembleBillDto.setVehicleAssembleNo(adjustOutVehicleFeeDto.getVehicleassembleNo());
			stlVehicleAssembleBillDto.setVehicleNo(adjustOutVehicleFeeDto.getVehicleNo());
			stlVehicleAssembleBillDto.setVehicleOwnerShip(adjustOutVehicleFeeDto.getVehicleOwnerShip());
			stlVehicleAssembleBillDto.setWaybillNo(adjustOutVehicleFeeDto.getWaybillNo());
			
			request.setAwardType(awardType);
			request.setBeReturnReceipt(adjustOutVehicleFeeDto.getBeReturnReceipt());
			request.setCurrencyCode(adjustOutVehicleFeeDto.getCurrencyCode());
			request.setDestOrgCode(adjustOutVehicleFeeDto.getDestOrgCode());
			request.setDestOrgName(adjustOutVehicleFeeDto.getDestOrgName());
			request.setDriverName(adjustOutVehicleFeeDto.getDriverName());
			request.setDriverCode(adjustOutVehicleFeeDto.getDriverCode());
			request.setDestOrgCode(adjustOutVehicleFeeDto.getDestOrgCode());
			request.setFeeTotal(adjustOutVehicleFeeDto.getFeeTotal());
			request.setLeaveTime(adjustOutVehicleFeeDto.getLeaveTime());
			request.setOrigOrgCode(adjustOutVehicleFeeDto.getOrigOrgCode());
			request.setOrigOrgName(adjustOutVehicleFeeDto.getOrigOrgName());
			request.setPaymentType(adjustOutVehicleFeeDto.getPaymentType());
			request.setPrePaidFeeTotal(adjustOutVehicleFeeDto.getPrepaidFeeTotal());
			request.setVehicleAssembleNo(adjustOutVehicleFeeDto.getVehicleassembleNo());
			request.setVehicleNo(adjustOutVehicleFeeDto.getVehicleNo());
			request.setVehicleOwnerShip(adjustOutVehicleFeeDto.getVehicleOwnerShip());
			request.setWaybillNo(adjustOutVehicleFeeDto.getWaybillNo());
			request.setEmpCode(FossUserContext.getCurrentInfo().getEmpCode());
			request.setEmpName(FossUserContext.getCurrentInfo().getEmpName());
			request.setCurrentDeptCode(FossUserContext.getCurrentInfo().getCurrentDeptCode());
			request.setCurrentDeptName(FossUserContext.getCurrentInfo().getCurrentDeptName());
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			if (stlVehicleAssembleBillDto.getAdjustFee() == null
					|| Double.valueOf(0).equals(stlVehicleAssembleBillDto.getAdjustFee().doubleValue())) {
				return 1;
			}
			if (request.getAdjustFee() == null
					|| request.getAdjustFee().doubleValue() == 0) {
				return 1;
			}
			//灰度DTO封装
			GrayParameterDto parDto = new GrayParameterDto();
			parDto.setSourceBillType(CUBCGrayUtil.SBType.PZ.getName());
			parDto.setSourceBillNos(new String[] {stlVehicleAssembleBillDto.getVehicleAssembleNo()});
			//调用灰度工具类方法
			VestResponse vestResponse = cubcUtil.getUcbcGrayData(parDto, new Throwable());
			if (CUBCGrayContants.SYSTEM_CODE_FOSS.equals(vestResponse.getVestBatchResult().get(0).getVestSystemCode())) {
				truckStowageService.adjustOutVehicleFee(stlVehicleAssembleBillDto,currentInfo);
			} else {
				LOGGER.error("FOSS推送费用调整审批通过调用CUBC接口,配载车次号" + request.getVehicleAssembleNo() + "给CUBC开始...");
				 CubcVehicleAssembleBillResponse response = null; 
				 try { // 调用cubc接口
				 response = fossToCubc.pushadjustOutVehicleFee(request);
					 if(null == response){
						 throw new TfrBusinessException("FOSS推送费用调整审批通过调用CUBC接口时ESB发生异常!");	
					 }
					 if(StringUtils.equals("0",response.getResult())){
						 throw new TfrBusinessException("FOSS推送费用调整审批通过调用CUBC接口操作时失败,失败消息:"+response.getReason());	
					 }
				} catch (Exception e) {
					throw new TfrBusinessException("FOSS推送费用调整审批通过调用CUBC接口操作时失败"+e);
				}
				LOGGER.error("FOSS推送费用调整审批通过调用CUBC接口,配载车次号" + request.getVehicleAssembleNo() + "给CUBC结束;返回值是 :"+response);
			}
			return 1;
		} else {
			return 0;
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IOutsideVehicleChargeService#queryBillPayableIsWriteOff(java.lang.String, java.lang.String)
	 */
	@Override
	public Boolean queryBillPayableIsWriteOff(String vehicleassembleNo, String isAdjust) {
		
		// 封装灰度实体，类型是配载单
		GrayParameterDto parDto = new GrayParameterDto();
		parDto.setSourceBillType(CUBCGrayUtil.SBType.PZ.getName());
		parDto.setSourceBillNos(new String[] { vehicleassembleNo });

		VestResponse vestResponse = cubcUtil.getUcbcGrayData(parDto, new Throwable());
		boolean state = false;
		if (!CUBCGrayContants.SYSTEM_CODE_FOSS.equals(vestResponse.getVestBatchResult().get(0).getVestSystemCode())) {

				Map<String, String> paramMap = new HashMap<String, String>();
				// 配载单号
				paramMap.put("sourceBillNo", vehicleassembleNo);
				paramMap.put("isAdjust", FossConstants.YES);

				CubcValidationSourceBillNoResponse cubcValidationVerificationResponse = fossToCubcService
						.queryBillPayableIsWriteOff(paramMap);
				if (null != cubcValidationVerificationResponse) {
					if (!cubcValidationVerificationResponse.isSuccess()) {
						throw new TfrBusinessException("根据来源单号调用cubc查询应付单是否已经核销失败:cubc"
								+ cubcValidationVerificationResponse.getExceptionMsg() + "！");
					} else if (cubcValidationVerificationResponse.isSuccess()
							&& cubcValidationVerificationResponse.isAudited()) {
						state = true;
					}
				} else {
					throw new TfrBusinessException("根据来源单号调用cubc查询应付单是否已经核销失败！");
				}
		} else {
			return billPayableService.queryBillPayableIsWriteOff(vehicleassembleNo, isAdjust);
		}
		return state;
	}

	/**
	 * 
	 *
	 * @param outsideVehicleChargeDao 
	 */
	public void setOutsideVehicleChargeDao(IOutsideVehicleChargeDao outsideVehicleChargeDao) {
		this.outsideVehicleChargeDao = outsideVehicleChargeDao;
	}

	/**
	 * 设置 调用结算Service 审核调整费用.
	 *
	 * @param billPayableService the new 调用结算Service 审核调整费用
	 */
	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}

	/**
	 * 设置 调用结算Service 是否可以调整费用.
	 *
	 * @param truckStowageService the new 调用结算Service 是否可以调整费用
	 */
	public void setTruckStowageService(ITruckStowageService truckStowageService) {
		this.truckStowageService = truckStowageService;
	}

	/**
	 * 设置 调用综合Service 配置参数.
	 *
	 * @param configurationParamsService the new 调用综合Service 配置参数
	 */
	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IOutsideVehicleChargeService#queryConfigurationParamsExactByEntity(int, int)
	 */
	@Override
	public List<ConfigurationParamsEntity> queryConfigurationParamsExactByEntity(int limit, int start) {
		String str[] = new String[LoadConstants.SONAR_NUMBER_4];
		str[0] = OutsideVehicleChargeConstants.DEFAULT_AWARD_MAXFEE;
		str[1] = OutsideVehicleChargeConstants.DEFAULT_AWARD_MINFEE;
		str[2] = OutsideVehicleChargeConstants.DEFAULT_FINE_MAXFEE;
		str[LoadConstants.SONAR_NUMBER_3] = OutsideVehicleChargeConstants.DEFAULT_FINE_MINFEE;
		return configurationParamsService.queryConfigurationParamsBatchByCode(str);

	}

	/**
	 * 当前系统是否有配置奖励 true为有奖励
	 * @author 163580
	 * @date 2013-12-18 下午2:24:13
	 * @return 
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IOutsideVehicleChargeService#beReward()
	 */
	@Override
	public Boolean beReward() {
//		ConfigurationParamsEntity configurationParamsEntity = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR , ConfigurationParamsConstants.TFR_PARM__TFR_SEAL_EXC_LATEST_TIME, FossConstants.ROOT_ORG_CODE);
//		ConfigurationParamsEntity configurationParams = configurationParamsService.queryConfigurationParamsByVirtualCode(ConfigurationParamsConstants.TFR_OUTSIDEVEHICLECHARGE_BE_REWARD);
		List<ConfigurationParamsEntity> configurationParamss = configurationParamsService.queryConfigurationParamsBatchByCode(new String[]{ConfigurationParamsConstants.TFR_OUTSIDEVEHICLECHARGE_BE_REWARD});
		if(CollectionUtils.isEmpty(configurationParamss)) {
			throw new TfrBusinessException("配置参数错误, 外请车是否奖励尚未配置, 请联系相关部门尽快配置!");
		}
		ConfigurationParamsEntity configurationParams = configurationParamss.get(0);
		//为Y则为有奖励
		return StringUtils.equals(configurationParams.getConfValue(), FossConstants.YES);
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IOutsideVehicleChargeService#queryOutsideVehicleChargeByVehicleassembleNo(java.lang.String)
	 */
	public List<AdjustOutVehicleFeeDto> queryOutsideVehicleChargeByVehicleassembleNo(String vehicleassembleNo) {
		return outsideVehicleChargeDao.queryOutsideVehicleChargeByVehicleassembleNo(vehicleassembleNo);
	}

	/**
	 * 根据配载单号查询有时效协议的数据
	 * @author 163580
	 * @date 2014-1-3 上午11:16:42
	 * @param vehicleassembleNo
	 * @return
	 * @see
	 */
	public List<AdjustOutVehicleFeeDto> queryOutsideVehicleChargeCauseByVehicleassembleNo(String vehicleassembleNo) {
		return outsideVehicleChargeDao.queryOutsideVehicleChargeCauseByVehicleassembleNo(vehicleassembleNo);
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IOutsideVehicleChargeService#isBillPayable(java.lang.String)
	 */
	@Override
	public Boolean isBillPayable(String waybillNo) throws TfrBusinessException {
		// 非空判断：输入参数waybillNo---必填项
		if (StringUtils.isEmpty(StringUtils.trim(waybillNo))) {
			// waybillNo为空，则抛出异常
			LOGGER.error(OutsideVehicleChargeConstants.OUTSIDEVEHICLECHARGE_WILLWAYNO_EMPTY_LOG);
			throw new TfrBusinessException(OutsideVehicleChargeConstants.OUTSIDEVEHICLECHARGE_WILLWAYNO_EMPTY, BLANK_STRING);
		}
		//根据配载车次号获取配载单信息
		List<VehicleAssembleBillEntity> assembleBillEntities = vehicleAssembleBillService.queryVehicleAssembleBillByNo(waybillNo);
		
		if (CollectionUtils.isEmpty(assembleBillEntities)) {
			assembleBillEntities = vehicleAssembleBillService.queryWkHandoverBillByNo(waybillNo);
		}
		
		if (CollectionUtils.isEmpty(assembleBillEntities)) {
			assembleBillEntities = new ArrayList<VehicleAssembleBillEntity>();
		} else {
			//快递一定不是整车，不需要设置这个AssembleType参数
//			if ("Y".equals(assembleBillEntities.get(0).getAssembleType())){
//				assembleBillEntities.get(0).setAssembleType(LoadConstants.VEHICLE_ASSEMBLE_TYPE_CAR_LOAD);
//			}
		}
		if(assembleBillEntities.size() < 1){
			// throw new TfrBusinessException("配载车次号输入有误，请重新输入配载车次号", "");
			throw new TfrBusinessException(OutsideVehicleChargeConstants.WRONG_WAYBILLNO, BLANK_STRING);
		}
		// 返回是否可结算
		// 判断是否费用调整
		Long adjustAcount = outsideVehicleChargeDao.getAdjustOutVehicleFeeRecordCount(waybillNo);
		Long count = null;
		String msg = "";
		//如果是整车则不用卸车
		if(StringUtils.equals(assembleBillEntities.get(0).getAssembleType(), LoadConstants.VEHICLE_ASSEMBLE_TYPE_CAR_LOAD)){
			count = (long)outsideVehicleChargeDao.queryIsArriveCount(waybillNo);
			msg = "到达";
		}else{
			// 根据单号查询可结算的记录数量(卸车)
			count = (long) outsideVehicleChargeDao.getBillPayableCountByWaybillNo(waybillNo);
			msg = "卸车";
		}
		if(adjustAcount < 1  && count >0){
			LOGGER.info("可正常结算");
		}else{
			if(adjustAcount > 0 && count < 1){
				throw new TfrBusinessException("当前单号未"+msg+"并且存在调整外请车费用未审核，请联系部门"+assembleBillEntities.get(0).getDestOrgName()+"完成"+msg+"，以及部门"+assembleBillEntities.get(0).getOrigOrgName()+"完成调整外请车费用的审核", BLANK_STRING);
			}
			if (adjustAcount > 0) {
				throw new TfrBusinessException("当前单号存在调整外请车费用未审核，请联系部门"+assembleBillEntities.get(0).getOrigOrgName(), "");
			}
			if(count < 1){
				throw new TfrBusinessException("当前单号未"+msg+"，请联系部门"+assembleBillEntities.get(0).getDestOrgName(), "");
			}
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IOutsideVehicleChargeService#queryOutVehicleAssembleBillAndFeeVoList(java.util.List)
	 */
	@Override
	public List<OutVehicleAssembleBillAndFeeVo> queryOutVehicleAssembleBillAndFeeVoList(List<String> vehicleAssembleNoList) throws TfrBusinessException {
		
		// 为空，抛出异常
		if (CollectionUtils.isEmpty(vehicleAssembleNoList)) {
			LOGGER.warn("OutsideVehicleChargeService[queryOutVehicleAssembleBillAndFeeVoList()]:" + OutsideVehicleChargeConstants.VEHICLEASSEMBLENOLIST_NULL);
			throw new TfrBusinessException(OutsideVehicleChargeConstants.VEHICLEASSEMBLENOLIST_NULL);
		}
		else {

			List<OutVehicleAssembleBillAndFeeVo> outVehicleAssembleBillAndFeeVoList = new ArrayList<OutVehicleAssembleBillAndFeeVo>();

			if (vehicleAssembleNoList.size() > LoadConstants.SONAR_NUMBER_500) {
				// 列表拆分为多个列表，分别查询 以500为一个基点
				List<String> subVehicleAssembleNoList = null;
				List<OutVehicleAssembleBillAndFeeVo> subOutVehicleAssembleBillAndFeeVoList = null;
				int count = vehicleAssembleNoList.size() / LoadConstants.SONAR_NUMBER_500;
				int mod = vehicleAssembleNoList.size() % LoadConstants.SONAR_NUMBER_500;
				for (int i = 0; i < count; i++) {
					subVehicleAssembleNoList = new ArrayList<String>(vehicleAssembleNoList.subList(i * LoadConstants.SONAR_NUMBER_500, (i + 1) * LoadConstants.SONAR_NUMBER_500));
					subOutVehicleAssembleBillAndFeeVoList = outsideVehicleChargeDao.queryOutVehicleAssembleBillAndFeeVoList(subVehicleAssembleNoList);
					// 合并结果子集至总结果集
					outVehicleAssembleBillAndFeeVoList = combineSubConfigOrgRelationEntityList(outVehicleAssembleBillAndFeeVoList, subOutVehicleAssembleBillAndFeeVoList);
				}
				// 若存在余数
				if (mod > 0) {
					if(subVehicleAssembleNoList == null){
						subVehicleAssembleNoList = new ArrayList<String>();
					}
					subVehicleAssembleNoList = new ArrayList<String>(subVehicleAssembleNoList.subList(count * LoadConstants.SONAR_NUMBER_500, count * LoadConstants.SONAR_NUMBER_500 + mod));
					// 查询
					subOutVehicleAssembleBillAndFeeVoList = outsideVehicleChargeDao.queryOutVehicleAssembleBillAndFeeVoList(subVehicleAssembleNoList);
					// 合并结果子集至总结果集
					outVehicleAssembleBillAndFeeVoList = combineSubConfigOrgRelationEntityList(outVehicleAssembleBillAndFeeVoList, subOutVehicleAssembleBillAndFeeVoList);
				}
			}
			else {
				outVehicleAssembleBillAndFeeVoList = outsideVehicleChargeDao.queryOutVehicleAssembleBillAndFeeVoList(vehicleAssembleNoList);
			}
			// 获取信息列表
			// List<OutVehicleAssembleBillAndFeeVo> outVehicleAssembleBillAndFeeVoList= outsideVehicleChargeDao.queryOutVehicleAssembleBillAndFeeVoList(vehicleAssembleNoList);
			return outVehicleAssembleBillAndFeeVoList;
		}
		
	}

	/**
	 * 校验当前部门所属外场是否和配载单的出发部门一致
	 * @author foss-liuxue(for IBM)
	 * @date 2013-6-5 下午3:39:17
	 */
	public boolean validateOrgCode(String code){
		//调用接口查询当前部门的外场
		OrgAdministrativeInfoEntity administrativeInfoEntity = loadService.querySuperiorOrgByOrgCode(FossUserContext.getCurrentDeptCode());
		//如果不为空，则查询到了
		if(null != administrativeInfoEntity && StringUtils.equals(FossConstants.YES, administrativeInfoEntity.getTransferCenter())){
			//比较code，如果一致则返回true
			if(StringUtils.equals(code, administrativeInfoEntity.getCode())){
				return true;
			}
			LOGGER.info("校验当前部门所属外场是否和配载单的出发部门一致失败"+code+"_"+administrativeInfoEntity.getCode());
		}
		//验证错误则返回false
		return false;
	}

	/** 
	* @Title: combineSubConfigOrgRelationEntityList 
	* @Description: 合并子集到总集中
	* @param outVehicleAssembleBillAndFeeVoList
	* @param subOutVehicleAssembleBillAndFeeVoList
	* @return  设定文件 
	* @return List<OutVehicleAssembleBillAndFeeVo>    返回类型 
	* @see combineSubConfigOrgRelationEntityList
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-5-31 下午5:34:42   
	* @throws 
	*/ 
	private List<OutVehicleAssembleBillAndFeeVo> combineSubConfigOrgRelationEntityList(List<OutVehicleAssembleBillAndFeeVo> outVehicleAssembleBillAndFeeVoList,
			List<OutVehicleAssembleBillAndFeeVo> subOutVehicleAssembleBillAndFeeVoList) {

		// 若子集非空，直接返回
		if (!CollectionUtils.isEmpty(subOutVehicleAssembleBillAndFeeVoList)) {
			// 遍历子集，增加至总总集
			for (OutVehicleAssembleBillAndFeeVo outVehicleAssembleBillAndFeeVo : subOutVehicleAssembleBillAndFeeVoList) {
				outVehicleAssembleBillAndFeeVoList.add(outVehicleAssembleBillAndFeeVo);
			}
		}

		return outVehicleAssembleBillAndFeeVoList;
	}
}