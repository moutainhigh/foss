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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/action/ReverseSignRfcAction.java
 * 
 * FILE NAME        	: ReverseSignRfcAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.server.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignChangeService;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RepaymentArrivesheetDto;
import com.deppon.foss.module.pickup.sign.api.shared.exception.SignException;
import com.deppon.foss.module.pickup.sign.api.shared.vo.ChangeSignResultVo;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 反签收ACTION
 * 
 * @author ibm-lizhiguo
 * @date 2012-11-21 上午10:04:35
 */
public class ReverseSignRfcAction extends AbstractAction {
	/**
	 */
	private static final long serialVersionUID = 1L;
	// Logger
	private static final Logger LOGGER = LoggerFactory.getLogger(ReverseSignRfcAction.class);
	//申请签收变更service
	private ISignChangeService signChangeService;
	//vo
	private ChangeSignResultVo vo;
	/**
	 * 运单状态服务
	 * 接口
	 */
	private IActualFreightService actualFreightService;
	
	/**
	 *  set对象
	 */
	public void setSignChangeService(ISignChangeService signChangeService) {
		this.signChangeService = signChangeService;
	}
	/**
	 *  get对象
	 */
	public ChangeSignResultVo getVo() {
		return vo;
	}
	/**
	 *  set对象
	 */
	public void setVo(ChangeSignResultVo vo) {
		this.vo = vo;
	}
	/**
	 * 营业部接口
	 */
	private ISaleDepartmentService saleDepartmentService;
	/**
	 * 数据字典
	 */
	private IConfigurationParamsService configurationParamsService;
	/**
	 * 运单service
	 */
	private IWaybillManagerService waybillManagerService;

	/**
	 * 
	 * 反签收，到达联和付款信息和运单信息
	 * 
	 * @author ibm-lizhiguo
	 * @date 2012-11-5 上午11:41:18
	 */
	@JSON
	public String queryReverseSignRfcDedicated() {
		LOGGER.info("queryReverseSignRfc() begin.......");
		try {
			//获得返回数据
			RepaymentArrivesheetDto dto = signChangeService
					.searchReverseSignDedicatedInfo(vo.getSignResultDto()
							.getSignRfcEntity().getWaybillNo());
			
			//没有数据，抛出异常
			if ((dto == null || dto.getWaybillEntity() == null)
					|| (CollectionUtils.isEmpty(dto.getRepaymentEntityList())
					&& CollectionUtils.isEmpty(dto.getArriveSheetEntityList()))) {
				//抛出异常，界面显示
				throw new SignException("数据没找到");
			}
			
			//合伙人反签收、整车与数据初始化4.10----start-------------239284----------------------------------------------------------------------------
			// 获取当前用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//获取运单信息
			WaybillEntity wayBillEntity = waybillManagerService.queryWaybillBasicByNo(vo.getSignResultDto().getSignRfcEntity().getWaybillNo());
			if (null != wayBillEntity) {
				//如果不是整车，判断当然运单是否是合伙人运单； 如果是整车，则走原有的反签收流程
				if (!FossConstants.YES.equals(wayBillEntity.getIsWholeVehicle())) {
					
							//到达部门
							SaleDepartmentEntity saleDept = saleDepartmentService.querySaleDepartmentInfoByCode(currentInfo.getCurrentDeptCode());
							if (null != saleDept) {
									//如果达到部门是合伙人部门
									validaDtoExtracted(dto, wayBillEntity,
											saleDept);
							} else {
								throw new SignException("合伙人签收到达部门信息不存在 ！");
							}
						
				}
			} else {
				throw new SignException(vo.getSignResultDto().getSignRfcEntity().getWaybillNo() + "数据没找到!");
			}
			//合伙人反签收、整车与数据初始化4.10----end-------------239284----------------------------------------------------------------------------
			
			//设置返回数据
			vo.getSignResultDto().setRepaymentArrivesheetDto(dto);
			//异常
		} catch (BusinessException e) {
			// 返回error
			return returnError(e);
		}
		LOGGER.info("queryReverseSignRfc() end.......");
		// 返回success
		return returnSuccess();
	}
	private void validaDtoExtracted(RepaymentArrivesheetDto dto,
			WaybillEntity wayBillEntity, SaleDepartmentEntity saleDept) {
		if (FossConstants.YES.equals(saleDept.getIsLeagueSaleDept())) {
			//配置参数-合伙人签收4.10上线前运单走以前逻辑;  读取配置参数日期开关;  如果开关日期不为空，则日期之前走原反签收流程；日期之后走合伙人签收流程
			String configString = configurationParamsService.queryConfValueByCode(ConfigurationParamsConstants.PKP_PTP_SIGN_INIT_410);
			if (StringUtils.isNotBlank(configString)) {
			    	try {
			    		SimpleDateFormat sdf = new SimpleDateFormat(SignConstants.PTP_INIT_DATE_410);
						long intTime = sdf.parse(configString.trim()).getTime();
						long destTime = wayBillEntity.getBillTime().getTime();
						//如果开单日期大于初始化日期，则走合伙人反签收流程
						if (destTime >= intTime) {
							//按照合伙人签收
							dto.setPartnerBillingLogo(FossConstants.YES);  //这里取值和实际承运表的字段意义不同
						}
					} catch (ParseException e) {
						throw new SignException("合伙人签收初始化日期开关解析错误！");
					}
			}
			
		}
	}

	/**
	 * 
	 * 反签收专线数据保存
	 * @author ibm-lizhiguo
	 * @date 2012-11-7 下午4:22:54
	 */
	@JSON
	public String saveReverseSignRfcDedicated() {
		LOGGER.info("saveReverseSignRfc() begin.......");
		try {
			// 获取当前用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//如果运单号为空(当前台点击取消时，会清空运单号，所以在这里再加限制)，不能反签收
			if(StringUtils.isBlank(vo.getSignResultDto().getSignRfcEntity().getWaybillNo())){
				LOGGER.info("传入运单号为空！");
				throw new SignException(SignException.WAYBILLNO_IS_NOT_NULL);
			}
			
			
			
			//保存反签收专线数据
			signChangeService.saveReverseSignDedicatedInfo(vo.getSignResultDto(),
					currentInfo);
			//异常
		} catch (BusinessException e) {
			// 返回error
			return returnError(e);
		}
		LOGGER.info("saveReverseSignRfc() end.......");
		// 返回success
		return returnSuccess();
	}

	/**
	 * <p>空运偏线反签收查询信息<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-1-21
	 * @return
	 * String
	 */
	@JSON
	public String queryReverseSignRfcAirPartial(){
		LOGGER.info("queryReverseSignRfcAirPartial() begin.......");
		try {
			//获得空运偏线反签收查询信息
			RepaymentArrivesheetDto dto = signChangeService
					.searchReverseSignAirPartial(vo.getSignResultDto()
							.getSignRfcEntity().getWaybillNo());
			//获得数据放入到返回数据中
			vo.getSignResultDto().setRepaymentArrivesheetDto(dto);
			//没有数据，抛出异常
			if ((dto == null || dto.getWaybillEntity() == null)
					|| dto.getWaybillEntity() ==null || dto.getWaybillSignResultEntity() == null) {
				//界面上显示抛出异常信息
				throw new SignException("数据没找到");
			} else if(FossConstants.YES.equals(dto.getWaybillSignResultEntity().getIsRfcing())){
				//界面上显示抛出异常信息
				throw new SignException("数据审批中");
			}
			
			//异常
		} catch (BusinessException e) {
			// 返回error
			return returnError(e);
		}
		LOGGER.info("queryReverseSignRfcAirPartial() end.......");
		// 返回success
		return returnSuccess();
	}
	/**
	 * 
	 * <p>保存空运偏线信息<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-1-21
	 * @return
	 * String
	 */
	@JSON
	public String saveReverseSignRfcAirPartial(){
		LOGGER.info("saveReverseSignRfcAirPartial() begin.......");
		try {
			// 获取当前用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//保存空运反签收信息
			signChangeService.saveReverseSignAirPartialInfo(vo.getSignResultDto(),
					currentInfo);
			//异常
		} catch (BusinessException e) {
			// 返回error
			return returnError(e);
		}
		LOGGER.info("saveReverseSignRfcAirPartial() end.......");
		// 返回success
		return returnSuccess();
	}
	public void setActualFreightService(IActualFreightService actualFreightService) {
		this.actualFreightService = actualFreightService;
	}
	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	
}