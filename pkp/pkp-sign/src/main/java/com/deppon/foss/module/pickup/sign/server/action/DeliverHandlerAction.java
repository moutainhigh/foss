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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/action/DeliverHandlerAction.java
 * 
 * FILE NAME        	: DeliverHandlerAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.server.action;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.ArriveSheetVo;
import com.deppon.foss.module.pickup.sign.api.server.service.IDeliverHandlerService;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.dto.DeliverbillDetailDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.FinancialDto;
import com.deppon.foss.module.pickup.sign.api.shared.exception.DeliverHandlerException;
import com.deppon.foss.module.pickup.sign.api.shared.exception.SignException;
import com.deppon.foss.module.pickup.sign.api.shared.vo.DeliverbillDetailVo;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.util.define.FossConstants;

import org.apache.commons.lang3.StringUtils;

/**
 * 派送处理Action
 * 
 * @author foss-meiying
 * @date 2012-10-29 上午10:55:12
 * @since
 * @version
 */
public class DeliverHandlerAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	/**
	 * 派送处理Service
	 */
	private IDeliverHandlerService deliverHandlerService;

	/**
	 * 运单服务
	 * */
	private IWaybillManagerService waybillManagerService;
	/**
	 * 到达联vo
	 */
	private ArriveSheetVo arriveSheetVo = new ArriveSheetVo();
	/**
	 * 派送明细 Vo
	 */
	private DeliverbillDetailVo deliverbillDetailVo;
	
	/**
	 * 营业部Service
	 */
	private ISaleDepartmentService saleDepartmentService;
	
	/**
	 * 派送处理---查询运单号
	 * 
	 * @author foss-meiying
	 * @date 2012-10-30 下午1:42:20
	 * @return
	 * @see
	 */
	@JSON
	public String queryWaybillNoByParams() {
		try {
			// 判断是否是合伙人部门，如果不是合伙人部门则直接返回
			SaleDepartmentEntity saleDept = saleDepartmentService
					.querySaleDepartmentInfoByCode(FossUserContext.getCurrentDeptCode());
			if (null != saleDept) {
				if (FossConstants.YES.equals(saleDept.getIsLeagueSaleDept())) {
					throw new SignException("当前部门不是直营部门!");
				}
			}
			
			// 查询待处理模块下---运单信息
			deliverbillDetailVo.setDeliverbillDetailDtos(deliverHandlerService.queryDeliverbillWaybillNo(deliverbillDetailVo.getDeliverbillDetailDto()));
		} catch (BusinessException e) {//异常处理
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 根据运单号，到达联编号查询财务和签收信息
	 * 
	 * @author foss-meiying
	 * @date 2012-10-30 下午2:12:24
	 * @return
	 * @see
	 */
	@JSON
	public String queryFinanceSign() {
		try {
			String waybillNo = deliverbillDetailVo.getDeliverbillDetailDto().getWaybillNo();
			//判断运单号是否为空
			if(StringUtils.isBlank(waybillNo)) {
				//运单号为空
				throw new DeliverHandlerException(DeliverHandlerException.WAYBILLNO_IS_NULL);
			}
			//根据运单号查询财务信息
			FinancialDto financialDto=deliverHandlerService.queryFinanceSign(waybillNo);
			//根据运单号查询运单信息
			WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
			if(financialDto != null && waybillEntity != null) {
				//将得到的返单类型插入FinancialDto中
				financialDto.setReturnbillType(waybillEntity.getReturnBillType());
			}
			// 查询财务信息
			deliverbillDetailVo.setFinancialDto(financialDto);
			// 查询到达联信息
			arriveSheetVo.setArriveSheet(deliverHandlerService.queryArriveSheetByParams(deliverbillDetailVo.getDeliverbillDetailDto().getArrivesheetNo()));
			//如果该到达联不是pda签收,且到达联状态 为生成或派送中 查询流水号集合
			if(null != arriveSheetVo.getArriveSheet()&& StringUtil.equals(arriveSheetVo.getArriveSheet().getIsPdaSign(), FossConstants.NO)
						&& (ArriveSheetConstants.STATUS_GENERATE.equals(arriveSheetVo.getArriveSheet().getStatus()) ||ArriveSheetConstants.STATUS_DELIVER.equals(arriveSheetVo.getArriveSheet().getStatus()))){
					//查询流水号集合
					deliverbillDetailVo.setSignDetailEntitys(deliverHandlerService.queryStock(waybillNo, deliverbillDetailVo.getDeliverbillDetailDto().getDeliverbillNo()));
					arriveSheetVo.getArriveSheet().setSignTime(new Date());
			}
		} catch (DeliverHandlerException e) {//异常处理
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 修改派送处理签收信息 --无pda签收确认
	 * 
	 * @author foss-meiying
	 * @date 2012-11-9 下午4:12:04
	 * @return
	 * @see
	 */
	@JSON
	public String addNoPdaSignSign() {
		try {
			// 获取当前用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		    String mess = deliverHandlerService.addNoPdaSign(arriveSheetVo.getArriveSheet(), deliverbillDetailVo.getFinancialDto(),
		    		currentInfo,deliverbillDetailVo.getSignDetailEntitys());
		    //返回成功
		    return returnSuccess(mess);
		} catch (BusinessException e) { //异常处理
			return returnError(e);
		}
		
	}
	/**
	 * 修改派送处理签收信息 --有pda签收确认  录入签收人
	 * 
	 * @author foss-meiying
	 * @date 2012-11-9 下午4:12:04
	 * @return
	 * @see
	 */
	@JSON
	public String addPdaSign() {
		try {
			arriveSheetVo.getArriveSheet().setOperator(FossUserContext.getCurrentUser() == null ? "" : FossUserContext.getCurrentUser().getEmployee().getEmpName());
			arriveSheetVo.getArriveSheet().setOperatorCode(FossUserContext.getCurrentUser() == null ? "" : FossUserContext.getCurrentUser().getEmployee().getEmpCode());
			deliverHandlerService.addPdaSignInfo(arriveSheetVo.getArriveSheet());
		  //异常处理
		} catch (BusinessException e) {
			return returnError(e);
		}
		//返回成功
		return returnSuccess();
	}
	/**
	 * 查询该派送单是否有到达联没有进行签收确认
	 * 
	 * @author foss-meiying
	 * @date 2012-11-12 下午5:02:51
	 * @return
	 * @see
	 */
	@JSON
	public String queryArrivesheetIsSign() {
		try {
			// 获取当前用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			deliverbillDetailVo.getDeliverbillDetailDto().setLastLoadOrgCode(currentInfo.getCurrentDeptCode());//得到当前登录的部门
			List<DeliverbillDetailDto> lists = deliverHandlerService.queryArrivesheetIsSign(deliverbillDetailVo.getDeliverbillDetailDto());
			if (lists != null && SignConstants.ZERO < lists.size()) {
				StringBuffer waybillNos = new StringBuffer();
				for (DeliverbillDetailDto deliverbillDetailDto : lists) {
					//运单号
					waybillNos.append(deliverbillDetailDto.getWaybillNo());
					//常量","
					waybillNos.append(SignConstants.SPLIT_CHAR);
				}
				throw new DeliverHandlerException(DeliverHandlerException.WAYBILLNO_NOT_SIGN,new Object[]{waybillNos.substring(0, waybillNos.length()-1)});
			} else {
				//录入接回货物信息
				int result = deliverHandlerService.addPullbackGoods(deliverbillDetailVo.getDeliverbillDetailDto(),currentInfo);
				//如果返回为1
				if (SignConstants.ONE == result) {
					return returnSuccess(DeliverHandlerException.NO_PULLBACK_GOODS_DELIVER_CONFIRM_SUCCESS);
				}else if(SignConstants.THREE == result){
					throw new DeliverHandlerException(DeliverHandlerException.DELIVER_IS_SIGNINFO_CONFIRMED);
				}
				else{
					//送货成功
					return returnSuccess(DeliverHandlerException.DELIVER_CONFIRM_SUCCESS);
				}
			}
		} catch (DeliverHandlerException e) {
			//异常处理
			return returnError(e);
		}
	}
	/**
	 * get方法
	 */
	public DeliverbillDetailVo getDeliverbillDetailVo() {
		return deliverbillDetailVo;
	}
	/**
	 * set方法
	 */
	public void setDeliverbillDetailVo(DeliverbillDetailVo deliverbillDetailVo) {
		this.deliverbillDetailVo = deliverbillDetailVo;
	}
	/**
	 * set方法
	 */
	public void setDeliverHandlerService(IDeliverHandlerService deliverHandlerService) {
		this.deliverHandlerService = deliverHandlerService;
	}
	/**
	 * get方法
	 */
	public ArriveSheetVo getArriveSheetVo() {
		return arriveSheetVo;
	}
	/**
	 * set方法
	 */
	public void setArriveSheetVo(ArriveSheetVo arriveSheetVo) {
		this.arriveSheetVo = arriveSheetVo;
	}
	/**
	 * set方法
	 */
	public void setWaybillManagerService(IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}
}
