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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.deppon.foss.module.pickup.sign.api.server.service.IExpDeliverHandlerService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.DeliverbillDetailDto;
import com.deppon.foss.module.pickup.sign.api.shared.exception.DeliverHandlerException;
import com.deppon.foss.module.pickup.sign.api.shared.exception.SignException;
import com.deppon.foss.module.pickup.sign.api.shared.vo.DeliverbillDetailVo;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRelateDetailEntityService;
import com.deppon.foss.module.pickup.waybill.shared.dto.TwoInOneWaybillDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 派送处理Action
 * 
 * @author foss-yuting
 * @date 2014-10-09 上午10:55:12
 * @since
 * @version
 */
public class ExpDeliverHandlerAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	/**
	 * 派送处理Service
	 */
	private IExpDeliverHandlerService expDeliverHandlerService;
	/**
	 * 到达联vo
	 */
	private ArriveSheetVo arriveSheetVo = new ArriveSheetVo();
	/**
	 * 派送明细 Vo
	 */
	private DeliverbillDetailVo deliverbillDetailVo;

	/**
	 * 子母件Service
	 */
	private IWaybillRelateDetailEntityService waybillRelateDetailEntityService;

	/**
	 * 运单签收Service
	 */
	private IWaybillSignResultService waybillSignResultService;

	/**
	 * 营业部Service
	 */
	private ISaleDepartmentService saleDepartmentService;

	/**
	 * 派送处理---查询运单号
	 * 
	 * @author foss-yuting
	 * @date 2014-10-09 上午10:55:12
	 * @return
	 * @see
	 */
	@JSON
	public String queryWaybillNoByParams() {
		try {
			// 判断是否是合伙人部门，如果不是合伙人部门则直接返回
			/*SaleDepartmentEntity saleDept = saleDepartmentService
					.querySaleDepartmentInfoByCode(FossUserContext.getCurrentDeptCode());
			if (null != saleDept) {
				if (FossConstants.YES.equals(saleDept.getIsLeagueSaleDept())) {
					throw new SignException("当前部门不是直营部门!");
				}
			}*/
						
			// 查询待处理模块下---运单信息
			deliverbillDetailVo
					.setDeliverbillDetailDtos(expDeliverHandlerService
							.queryDeliverbillWaybillNo(deliverbillDetailVo
									.getDeliverbillDetailDto()));
		} catch (BusinessException e) {// 异常处理
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 根据运单号，到达联编号查询财务和签收信息
	 * 
	 * @author foss-yuting
	 * @date 2014-10-09 上午10:55:12
	 * @return
	 * @see
	 */
	@JSON
	public String queryFinanceSign() {
		try {
			String waybillNo = deliverbillDetailVo.getDeliverbillDetailDto().getWaybillNo();
			// 子母件 268377 yanling
			Map<String, Object> queryCZMparams = new HashMap<String, Object>();
			queryCZMparams.put("waybillNo", waybillNo);
			queryCZMparams.put("active", FossConstants.YES);
			TwoInOneWaybillDto twoInOneWaybillDto = waybillSignResultService.queryWaybillRelateByWaybillOrOrderNo(queryCZMparams);
			// 判断是否子母件
			if (null != twoInOneWaybillDto && (FossConstants.YES).equals(twoInOneWaybillDto.getIsTwoInOne())) {
				//获取母单单号
				String parentWaybillNo = twoInOneWaybillDto.getMainWaybillNo();
				// 查询子母件财务信息
				deliverbillDetailVo.setFinancialDto(expDeliverHandlerService.queryFinanceSign(parentWaybillNo));
			} else {
				// 查询财务信息
				deliverbillDetailVo.setFinancialDto(expDeliverHandlerService.queryFinanceSign(waybillNo));
			}
			// 查询到达联信息
			arriveSheetVo.setArriveSheet(expDeliverHandlerService.queryArriveSheetByParams(deliverbillDetailVo.getDeliverbillDetailDto().getArrivesheetNo()));
			// 如果该到达联不是pda签收,且到达联状态 为生成或派送中 查询流水号集合
			if (null != arriveSheetVo.getArriveSheet() && StringUtil.equals(arriveSheetVo.getArriveSheet().getIsPdaSign(), FossConstants.NO)
					&& (ArriveSheetConstants.STATUS_GENERATE
							.equals(arriveSheetVo.getArriveSheet().getStatus()) || ArriveSheetConstants.STATUS_DELIVER
							.equals(arriveSheetVo.getArriveSheet().getStatus()))) {
				//子母件查询流水号集合 268377
				if (null != twoInOneWaybillDto && (FossConstants.YES).equals(twoInOneWaybillDto.getIsTwoInOne())) {// 268377
					deliverbillDetailVo.setSignDetailEntitys(expDeliverHandlerService.queryCZMStock(twoInOneWaybillDto.getWaybillNoList(), deliverbillDetailVo.getDeliverbillDetailDto().getDeliverbillNo()));
					deliverbillDetailVo.setTwoInOneWaybillDto(twoInOneWaybillDto);
				} else {
					// 查询流水号集合
					deliverbillDetailVo.setSignDetailEntitys(expDeliverHandlerService.queryStock(waybillNo, deliverbillDetailVo.getDeliverbillDetailDto().getDeliverbillNo()));
				}

				arriveSheetVo.getArriveSheet().setSignTime(new Date());
			}
		} catch (DeliverHandlerException e) {// 异常处理
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 修改派送处理签收信息 --无pda签收确认
	 * 
	 * @author foss-yuting
	 * @date 2014-10-09 上午10:55:12
	 * @return
	 * @see
	 */
	@JSON
	public String addNoPdaSignSign() {
		try {
			// 获取当前用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			Map<String, Object> queryCZMparams = new HashMap<String, Object>();
			queryCZMparams.put("waybillNo",arriveSheetVo.getArriveSheet().getWaybillNo());
			queryCZMparams.put("active", FossConstants.YES);
			//查询子母件信息
			TwoInOneWaybillDto twoInOneWaybillDto = waybillSignResultService.queryWaybillRelateByWaybillOrOrderNo(queryCZMparams);
			//获取签收明细结合 268377 
			List<SignDetailEntity> signDetailEntitys = deliverbillDetailVo.getSignDetailEntitys();
			//判断是否是子母件 268377
			if (null != twoInOneWaybillDto && (FossConstants.YES).equals(twoInOneWaybillDto.getIsTwoInOne())) {
				String mess=expDeliverHandlerService.addNoPdaCZMSign(arriveSheetVo.getArriveSheet(),deliverbillDetailVo.getFinancialDto(), currentInfo,signDetailEntitys,twoInOneWaybillDto);
				return returnSuccess(mess);
			} else {
				String mess = expDeliverHandlerService.addNoPdaSign(
						arriveSheetVo.getArriveSheet(),
						deliverbillDetailVo.getFinancialDto(), currentInfo,
						deliverbillDetailVo.getSignDetailEntitys());
				// 返回成功
				return returnSuccess(mess);
			}
		} catch (BusinessException e) { // 异常处理
			return returnError(e);
		}

	}
	/**
	 * 修改派送处理签收信息 --有pda签收确认  录入签收人
	 * 
	 * @author foss-yuting
	 * @date 2014-10-09 上午10:55:12
	 * @return
	 * @see
	 */
	@JSON
	public String addPdaSign() {
		try {
			arriveSheetVo.getArriveSheet().setOperator(FossUserContext.getCurrentUser() == null ? "" : FossUserContext.getCurrentUser().getEmployee().getEmpName());
			arriveSheetVo.getArriveSheet().setOperatorCode(FossUserContext.getCurrentUser() == null ? "" : FossUserContext.getCurrentUser().getEmployee().getEmpCode());

		//查询子母件参数 268377
			Map<String, Object> queryCZMparams = new HashMap<String, Object>();
			queryCZMparams.put("waybillNo", arriveSheetVo.getArriveSheet().getWaybillNo());
			queryCZMparams.put("active", FossConstants.YES);
			TwoInOneWaybillDto twoInOneWaybillDto = waybillSignResultService.queryWaybillRelateByWaybillOrOrderNo(queryCZMparams);
			//判断是否子母件 
			if(null != twoInOneWaybillDto && (FossConstants.YES).equals(twoInOneWaybillDto.getIsTwoInOne())){
					expDeliverHandlerService.addCZMPdaSignInfo(arriveSheetVo.getArriveSheet(),twoInOneWaybillDto);
			}else{
				//测试
//			int i=1/0;
				expDeliverHandlerService.addPdaSignInfo(arriveSheetVo.getArriveSheet());
			}

		  //异常处理
		} catch (DeliverHandlerException e) {
			return returnError(e);
		}
		//返回成功
		return returnSuccess();
	}
	/**
	 * 查询该派送单是否有到达联没有进行签收确认
	 * 
	 * @author foss-yuting
	 * @date 2014-10-09 上午10:55:12
	 * @return
	 * @see
	 */
	@JSON
	public String queryArrivesheetIsSign() {
		try {
			// 获取当前用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			deliverbillDetailVo.getDeliverbillDetailDto().setLastLoadOrgCode(currentInfo.getCurrentDeptCode());//得到当前登录的部门
			List<DeliverbillDetailDto> lists = expDeliverHandlerService.queryArrivesheetIsSign(deliverbillDetailVo.getDeliverbillDetailDto());
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
				int result = expDeliverHandlerService.addPullbackGoods(deliverbillDetailVo.getDeliverbillDetailDto(),currentInfo);
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
	public void setExpDeliverHandlerService(
			IExpDeliverHandlerService expDeliverHandlerService) {
		this.expDeliverHandlerService = expDeliverHandlerService;
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

	public IWaybillRelateDetailEntityService getWaybillRelateDetailEntityService() {
		return waybillRelateDetailEntityService;
	}

	public void setWaybillRelateDetailEntityService(
			IWaybillRelateDetailEntityService waybillRelateDetailEntityService) {
		this.waybillRelateDetailEntityService = waybillRelateDetailEntityService;
	}


	public void setWaybillSignResultService(IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}

	/**
	 * @param saleDepartmentService the saleDepartmentService to set
	 */
	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}
	
}