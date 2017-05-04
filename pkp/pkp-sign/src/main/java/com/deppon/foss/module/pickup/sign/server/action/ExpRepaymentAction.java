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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/action/RepaymentAction.java
 * 
 * FILE NAME        	: RepaymentAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.server.action;

import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService;
import com.deppon.foss.module.pickup.sign.api.shared.domain.RepaymentEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.AirAgencyQueryDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RepaymentDto;
import com.deppon.foss.module.pickup.sign.api.shared.exception.RepaymentException;
import com.deppon.foss.module.pickup.sign.api.shared.vo.RePaymentVo;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 *
 * 结清货款Action
 * @author 043258-
 *		foss-zhaobin
 * @date 2012-11-20 
 * 		下午4:29:12
 * @since
 * @version
 */
public class ExpRepaymentAction extends AbstractAction 
{
	
	/** 
	 * 业务互斥锁服务
	 */
	private IBusinessLockService businessLockService;
	/** 
	 * 零
	 */
	private static final int ZERO = 0;	
	
	/**
 * 序列号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 付款vo
	 */
	private RePaymentVo vo;
	
	/**
	 * 付款服务
	 */
	private IRepaymentService repaymentService;

	/**
	 * Gets the vo.
	 *
	 * @return the vo
	 */
	public RePaymentVo getVo() {
		return vo;
	}
	
	/**
	 * Sets the vo.
	 *
	 * @param vo the new vo
	 */
	public void setVo(RePaymentVo vo) {
		this.vo = vo;
	}
	
	
	public BigDecimal getNoCancelAmount() {
		return noCancelAmount;
	}

	public void setNoCancelAmount(BigDecimal noCancelAmount) {
		this.noCancelAmount = noCancelAmount;
	}

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	/**
	 * Sets the repayment service.
	 *
	 * @param repaymentService the new repayment service
	 */
	public void setRepaymentService(IRepaymentService repaymentService) {
		this.repaymentService = repaymentService;
	}
	
	/**
	 * 未核销金额
	 */
	private BigDecimal noCancelAmount;
	
	/**
	 * 查询待处理列表.
	 *
	 * @return the string
	 * @author 043258-
	 * 		foss-zhaobin
	 * @date 2012-11-20
	 * 		 下午4:31:29
	 * @since
	 * @version
	 */
	@JSON 
	public String queryAirAgencyQueryDtoList(){
		try {
			//根据查询条件返回待处理列表 
			//增加是否快递字段作为查询的条件  
			AirAgencyQueryDto airAgencyQueryDto=vo.getRepaymentDto().getAirAgencyQueryDto();
			airAgencyQueryDto.setIsExpress(FossConstants.YES);
			
			List<AirAgencyQueryDto> list = repaymentService.queryAirAgencyQueryDtoList(airAgencyQueryDto);
			//付款dto
			RepaymentDto repaymentDto = new RepaymentDto();
			//设置dto
			repaymentDto.setAirAgencyQueryDtoList(list);
			//设置vo
			vo.setRepaymentDto(repaymentDto);
		} catch (BusinessException e) {
			//返回异常
			return returnError(e);
		}
		//成功
		return returnSuccess();
	}
	
	/**
	 * 根据运单号查询详细信息.
	 *
	 * @return the string
	 * @author 043258-
	 * 		foss-zhaobin
	 * @date 2012-11-26
	 * 		 下午2:53:03
	 * @since
	 * @version
	 */
	@JSON 
	public String queryPaymentByWaybillNo(){
		try {
			//根据运单号查询详细信息
			RepaymentDto repaymentDto = repaymentService.queryPaymentByWaybillNo(vo.getRepaymentDto().getAirAgencyQueryDto().getWaybillNo());
			//设置vo
			vo.setRepaymentDto(repaymentDto);
		} catch (BusinessException e) {
			//返回异常
			return returnError(e);
		}
		//成功
		return returnSuccess();
	}
	
	/**
	 * 增加付款信息.
	 *
	 * @return the string
	 * @author 043258-
	 * 		foss-zhaobin
	 * @date 2012-11-26 
	 * 		下午2:53:50
	 * @since
	 * @version
	 */
	@JSON 
	public String addPaymentInfo(){
		
		MutexElement mutexElement = new MutexElement(vo.getRepaymentEntity().getWaybillNo(), "结清货款", MutexElementType.REPAYMENT_NO);
		//互斥锁定
		boolean isLocked = businessLockService.lock(mutexElement, ZERO);
		//如果没有上锁
		if(!isLocked){
			//返回异常
			return returnError(RepaymentException.PAYMENT_OPERATING);
		}
		
		try {
			//如果vo不为空
			if (vo != null) 
			{
				// 获取当前用户
				CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
				
				//新增付款信息
				this.repaymentService.addPaymentInfo(vo.getRepaymentEntity(),currentInfo,null);
			}
		} catch (BusinessException e) {
			//解锁
			businessLockService.unlock(mutexElement);
			//返回异常
			return returnError(e);
		} finally{
			//解锁
			businessLockService.unlock(mutexElement);
		}
		//成功
		return returnSuccess();
	}
	
	/**
	 * 校验操作人密码.
	 *
	 * @return the string
	 * @author 043258-
	 * 		foss-zhaobin
	 * @date 2012-11-29
	 * 		 下午5:09:53
	 * @since
	 * @version
	 */
	@JSON 
	public String validatePaymentInfo(){
		try {
			if (vo != null) 
			{
				// 获取当前用户
				CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
				//校验操作密码
				this.repaymentService.validatePaymentInfo(vo.getRepaymentEntity(),currentInfo,null);
			}
		} catch (BusinessException e) {
			//返回异常
			return returnError(e);
		}
		//成功
		return returnSuccess();
	}
	
	/**
	 * 退款操作.
	 *
	 * @return the string
	 * @author 043258-
	 * 		foss-zhaobin
	 * @date 2012-11-27
	 * 		 下午2:23:42
	 * @since
	 * @version
	 */
	@JSON 
	public String refundPaymentInfo(){
		try {
			//如果vo不为空
			if (vo != null) 
			{
				//退款
				boolean flag = this.repaymentService.refundPaymentInfo(vo.getRepaymentEntity().getWaybillNo());
				if(flag == false)
				{
					//抛出异常 参数错误
					throw new RepaymentException(RepaymentException.REFUND_ERROR);
				}
			}
		} catch (BusinessException e) {
			//返回异常
			return returnError(e);
		}
		//成功
		return returnSuccess();
	}
	
	/**
	 * 快速结清操作.
	 *
	 * @return the string
	 * @author 043258-
	 * 		foss-zhaobin
	 * @date 2013-2-26
	 * 		 下午2:23:42
	 * @since
	 * @version
	 */
	@JSON 
	public String quickPayment(){
		try {
			//如果vo不为空
			if (vo != null) 
			{
				//快速结清
				RepaymentEntity repaymentEntity = new RepaymentEntity();
				//传入单号
				repaymentEntity.setWaybillNo(vo.getRepaymentEntity().getWaybillNo());
				this.repaymentService.paymentOperate(repaymentEntity);
			}
		} catch (BusinessException e) {
			//返回异常
			return returnError(e);
		}
		//成功
		return returnSuccess();
	}
	
	/**
	 * 
	 * 根据款项认领编号获取未核销金额
	 * @author 043258-
	 *				foss-zhaobin
	 * @date 2013-4-11 
	 *				下午4:35:23
	 * @return
	 * @see
	 */
	@JSON 
	public String queryTransfer(){
		try {
			//如果vo不为空
			if (vo != null) 
			{
				noCancelAmount = repaymentService.queryTransfer(vo.getRepaymentEntity().getClaimNo(),vo.getRepaymentEntity().getPaymentType(),
						vo.getRepaymentEntity().getWaybillNo());
			}
		} catch (BusinessException e) {
			//返回异常
			return returnError(e);
		}
		//成功
		return returnSuccess();
	}

}