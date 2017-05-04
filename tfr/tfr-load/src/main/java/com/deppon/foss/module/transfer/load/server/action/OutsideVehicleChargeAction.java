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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/server/action/OutsideVehicleChargeAction.java
 *  
 *  FILE NAME          :OutsideVehicleChargeAction.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.service.IOutsideVehicleChargeService;
import com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService;
import com.deppon.foss.module.transfer.load.api.shared.define.OutsideVehicleChargeConstants;
import com.deppon.foss.module.transfer.load.api.shared.domain.AdjustOutVehicleFeeEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.AdjustOutVehicleFeeLogEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.AdjustOutVehicleFeeDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.RewardOrPunishAgreementDto;
import com.deppon.foss.module.transfer.load.api.shared.vo.AdjustOutVehicleFeeVo;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 调整外请车费用Action
 * 
 * @author ibm-liming
 * @date 2012-11-20 下午6:44:20
 */
public class OutsideVehicleChargeAction extends AbstractAction {

	private static final long serialVersionUID = 7707257702463810224L;

	private IOutsideVehicleChargeService outsideVehicleChargeService;

	private AdjustOutVehicleFeeVo vo = new AdjustOutVehicleFeeVo();

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OutsideVehicleChargeAction.class);

	/** 导出Excel 文件流 */
	private InputStream excelStream;
	/** 导出Excel 文件名 */
	private String fileName;

	/**
	 * 配载单service
	 */
	private IVehicleAssembleBillService vehicleAssembleBillService;

	/**
	 * 查询外请车费用信息
	 * 
	 */
	@JSON
	public String queryOutsideVehicleChargeList() {		
		try {
			// modify by liangfuxiang 2013-3-18下午3:11:58 begin 去除魔鬼数据
			// LOGGER.info("查询数据");
			LOGGER.info(OutsideVehicleChargeConstants.QUERY_DATA);
			// modify by liangfuxiang 2013-3-18下午3:12:12 end;
			List<AdjustOutVehicleFeeEntity> adjustOutVehicleFeeList=outsideVehicleChargeService.queryOutsideVehicleChargeList(vo.getAdjustOutVehicleFeeDto(), limit, start);
			vo.setAdjustOutVehicleFeeList(adjustOutVehicleFeeList);
			Long	totalCount=outsideVehicleChargeService.queryCount(vo.getAdjustOutVehicleFeeDto());
			this.setTotalCount(totalCount);	
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 编码转换
	 * @author zyx
	 * @date 2013-11-28 下午4:35:14
	 * @param fileName
	 * @return
	 * @throws UnsupportedEncodingException
	 * @see
	 */
	public String encodeFileName(String fileName)
			throws UnsupportedEncodingException {
		String returnStr;
		String agent = (String) ServletActionContext.getRequest().getHeader(
				"USER-AGENT");
		if (agent != null && agent.indexOf("MSIE") == -1) {
			returnStr = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
		} else {
			returnStr = URLEncoder.encode(fileName, "UTF-8");
		}
		return returnStr;
	}

	/**
	 * 导出外请车费用信息
	 * @author zyx
	 * @date 2013-11-28 下午4:38:24
	 * @return
	 * @see
	 */
	public String exportOutsideVehicleCharge() {
		try {
			fileName = encodeFileName("外请车费用信息");
			excelStream = outsideVehicleChargeService.exportOutsideVehicleCharge(vo.getAdjustOutVehicleFeeDto());
		} catch (TfrBusinessException e) {
			return returnError(e);
		} catch (UnsupportedEncodingException e) {
			return returnError(new TfrBusinessException("转换字符串编码时出错!"));
		}
		return returnSuccess();
	}

	/**
	 * 保存新的外请车费用调整数据
	 * 
	 */
	@JSON
	public String addOutsideVehicleCharge() {
		try {
			AdjustOutVehicleFeeEntity adjustOutVehicleFee = vo.getAdjustOutVehicleFeeEntity();
//			String vehicleassembleNo = adjustOutVehicleFee.getVehicleassembleNo();
//			AdjustOutVehicleFeeDto adjustOutVehicleFeeDto = outsideVehicleChargeService.queryByVehicleassembleNo(vehicleassembleNo);
//			if(StringUtils.equals(adjustOutVehicleFeeDto.getBeTimeLiness(), FossConstants.YES)) {
//				throw new TfrBusinessException("该配载单有时效协议, 不能新增!");
//			}
			Boolean isAdjust = outsideVehicleChargeService.queryBillPayableIsWriteOff(vo.getAdjustOutVehicleFeeEntity().getVehicleassembleNo(), FossConstants.YES);
			if(isAdjust) {
				throw new TfrBusinessException("该配载单已经结算, 无法申请!");
			}
			outsideVehicleChargeService.addOutsideVehicleCharge(adjustOutVehicleFee);
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 保存新的外请车费用调整数据
	 * 
	 */
	@JSON
	public String deleteOutsideVehicleCharge() {
		try {
			outsideVehicleChargeService.deleteOutsideVehicleCharge(vo
					.getAdjustOutVehicleFeeDto().getId());
		} catch (BusinessException e) {
			return returnError(e);
		}

		return returnSuccess();
	}

	/**
	 * 更新外请车费用调整数据
	 * 
	 * 
	 */
	@JSON
	public String updateOutsideVehicleCharge() {
		try {
			outsideVehicleChargeService.updateOutsideVehicleCharge(vo.getAdjustOutVehicleFeeEntity());
		} catch (BusinessException e) {
			return returnError(e);
		}

		return returnSuccess();
	}

	@JSON
	public String queryById() {
		try {
			AdjustOutVehicleFeeDto dto = outsideVehicleChargeService
					.queryById(vo.getAdjustOutVehicleFeeDto().getId());
			if (StringUtils.equals(dto.getTimelinessClause(), FossConstants.YES)) {
				// 如果当前单据有时效协议, 则需要显示时效条款相关信息
				// 其中包含奖罚条款, 最大奖励金额, 最小奖励金额
				RewardOrPunishAgreementDto rewardOrPunishAgreementDto = vehicleAssembleBillService
						.queryRewardOrPunishAgreementDetail(dto.getVehicleassembleNo());
				vo.setRewardOrPunishAgreementDto(rewardOrPunishAgreementDto);
			}
			vo.setAdjustOutVehicleFeeDto(dto);
		} catch (BusinessException e) {
			return returnError(e);
		}

		return returnSuccess();
	}

	/**
	 * 审批意见界面 装车总金额
	 * @author 269701
	 * @date 2015-07-12
	 */
	@JSON
	public String queryApprovalById() {
		try {
			//计算装车总金额
			AdjustOutVehicleFeeDto dto = outsideVehicleChargeService
					.queryById(vo.getAdjustOutVehicleFeeDto().getId());
			vo.setAdjustOutVehicleFeeDto(dto);
		} catch (BusinessException e) {
			return returnError(e);
		}

		return returnSuccess();
	}

	/**
	 * 审核外请车费用调整申请
	 * 
	 * 
	 */
	@JSON
	public String auditOutsideVehicleCharge() {
		try {
			outsideVehicleChargeService.auditOutsideVehicleCharge(vo.getAdjustOutVehicleFeeEntity());
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 审批外请车费用调整申请（通过 or 不通过）
	 * 
	 * @author 269701-foss-lln
	 * 
	 * @date 2015-07-09 下午5:45:50
	 * 
	 */
	@JSON
	public String approvalOutsideVehicleCharge() {
		try {
			outsideVehicleChargeService.approvalOutsideVehicleCharge(vo
					.getAdjustOutVehicleFeeEntity());
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 调整外请车费用修改记录
	 * 
	 * @author 269701-foss-lln
	 * 
	 * @date 2015-07-10 下午5:45:50
	 * 
	 */
	public String queryUpdateLogs() {
		try {
			// 判空
			if (vo != null
					&& vo.getAdjustOutVehicleFeeLogDto() != null
					&& StringUtils.isNotBlank(vo.getAdjustOutVehicleFeeLogDto()
							.getAdjustOutVehicleFeeId())) {
				// 分页查询修改记录,默认按照时间降序排序
				List<AdjustOutVehicleFeeLogEntity> logList = outsideVehicleChargeService
						.queryOutsideVehicleUpdateLogs(
								vo.getAdjustOutVehicleFeeLogDto(), limit, start);
				// 判空
				if (CollectionUtils.isNotEmpty(logList)) {
					// 设置值
					vo.setLogList(logList);
				}
				// 查询总条数
				Long totoalCount = outsideVehicleChargeService
						.queryOutsideVehicleUpdateLogsTotalCount(vo
								.getAdjustOutVehicleFeeLogDto());
				// 判空
				if (totoalCount != null) {
					// 设置值
					this.setTotalCount(totoalCount);
				}
			}
			// 成功
			return returnSuccess();
			// 异常
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * 未审核记录条数 以及 审批中记录条数的统计
	 * 
	 * @author 269701-foss-lln
	 * 
	 * @date 2015-07-16
	 * 
	 */
	@JSON
	public String noAuditAndAuditInCount() {
		try {
			AdjustOutVehicleFeeEntity auditCount = outsideVehicleChargeService
					.noAuditAndAuditInCount(vo.getAdjustOutVehicleFeeDto());
			//未审核记录条数
			vo.setNoAuditCount(auditCount.getNoAuditCount());	
			//审批中记录条数
			vo.setAuditInCount(auditCount.getAuditInCount());
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 根据配载车次号找配载单信息
	 * 
	 * 283250快递拆分新加逻辑
	 * 根据配载车次号找配载单信息如果找不到
	 * 就根据该条件查询快递交接单信息
	 */
	@JSON
	public String queryByVehicleassembleNo() {
		try {
			AdjustOutVehicleFeeDto adjustOutVehicleFeeDto = outsideVehicleChargeService
					.queryByVehicleassembleNo(vo.getAdjustOutVehicleFeeDto().getVehicleassembleNo());
			vo.setAdjustOutVehicleFeeDto(adjustOutVehicleFeeDto);
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 外请车费用调整审核后,调整费用
	 * 
	 * @param vehicleassembleNo
	 * @param isAdjust
	 * @return
	 */
	@JSON
	public String adjustOutVehicleFee() {
		try {
			int num = outsideVehicleChargeService.adjustOutVehicleFee(vo.getAdjustOutVehicleFeeDto());
			vo.setIsNum(num);
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 外请车费用调整审批通过后,调整费用
	 * @author 269701--foss--lln
	 * @date 2015-07-28
	 * @return
	 */
	@JSON
	public String approvalAdjustOutVehicleFee() {
		try {
			int num = outsideVehicleChargeService.approvalAdjustOutVehicleFee(vo.getAdjustOutVehicleFeeDto());
			vo.setIsNum(num);
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 效验该车辆能否调整
	 * 
	 * @author dp-liming
	 * @date 2012-11-19 下午 16:40:34
	 */
	@JSON
	public String queryBillPayableIsWriteOff() {
		try {
			Boolean isAdjust = outsideVehicleChargeService.queryBillPayableIsWriteOff(vo.getAdjustOutVehicleFeeEntity()
							.getVehicleassembleNo(), FossConstants.YES);
			vo.setIsAdjust(isAdjust);
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 查询配置奖罚费用范围
	 * 
	 * @author dp-liming
	 * @date 2012-11-19 下午 16:40:34
	 */
	@JSON
	public String queryConfigurationParamsExactByEntity() {
		try {
			List<ConfigurationParamsEntity> list = outsideVehicleChargeService
					.queryConfigurationParamsExactByEntity(limit, start);
			vo.setConfigurationParamsList(list);
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}

	@JSON
	public String queryOutsideVehicleChargeByVehicleassembleNo() {
		try {
			List<AdjustOutVehicleFeeDto> list = outsideVehicleChargeService
					.queryOutsideVehicleChargeByVehicleassembleNo(vo
							.getAdjustOutVehicleFeeDto().getVehicleassembleNo());
			vo.setAdjustOutVehicleFeeDtoList(list);
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}

	public AdjustOutVehicleFeeVo getVo() {
		return vo;
	}

	public void setVo(AdjustOutVehicleFeeVo vo) {
		this.vo = vo;
	}

	public void setOutsideVehicleChargeService(
			IOutsideVehicleChargeService outsideVehicleChargeService) {
		this.outsideVehicleChargeService = outsideVehicleChargeService;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @param vehicleAssembleBillService the vehicleAssembleBillService to set
	 */
	public void setVehicleAssembleBillService(
			IVehicleAssembleBillService vehicleAssembleBillService) {
		this.vehicleAssembleBillService = vehicleAssembleBillService;
	}
}