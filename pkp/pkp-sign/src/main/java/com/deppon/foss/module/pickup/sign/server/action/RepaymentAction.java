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
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.RepaymentEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.AccountBookRequestDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.AirAgencyQueryDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RepaymentDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.sign.api.shared.exception.RepaymentException;
import com.deppon.foss.module.pickup.sign.api.shared.exception.SignException;
import com.deppon.foss.module.pickup.sign.api.shared.vo.RePaymentVo;
import com.deppon.foss.module.pickup.sign.api.shared.vo.WaybillVo;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
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
public class RepaymentAction extends AbstractAction {
	//客户编码
	private String consigneeCode;
	
	public String getConsigneeCode() {
		return consigneeCode;
	}

	public void setConsigneeCode(String consigneeCode) {
		this.consigneeCode = consigneeCode;
	}
	private BigDecimal balanceAmount;
		
	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
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
	 * 运单信息vo WaybillVo
	 */
	private WaybillVo waybillVo;
	/**
	 * 付款服务
	 */
	private IRepaymentService repaymentService;
	
	/**
	 * 返回状态提醒
	 */
	private String stateMsg;

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
	
	
	public WaybillVo getWaybillVo() {
		return waybillVo;
	}

	public void setWaybillVo(WaybillVo waybillVo) {
		this.waybillVo = waybillVo;
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
	 * 营业部Service
	 */
	private ISaleDepartmentService saleDepartmentService;
	/**
	 * @param saleDepartmentService the saleDepartmentService to set
	 */
	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

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
			// 判断是否是合伙人部门，如果不是合伙人部门则直接返回
			/*SaleDepartmentEntity saleDept = saleDepartmentService.querySaleDepartmentInfoByCode(FossUserContext.getCurrentDeptCode());
			if (null != saleDept) {
				if (FossConstants.YES.equals(saleDept.getIsLeagueSaleDept())) {
					throw new SignException("当前部门不是直营部门!");
				}
			}*/
			
			if(vo != null && vo.getRepaymentDto() != null){
				List<AirAgencyQueryDto> list = repaymentService.queryAirAgencyQueryDtoList(vo.getRepaymentDto().getAirAgencyQueryDto());
				//付款dto
				RepaymentDto repaymentDto = new RepaymentDto();
				//设置dto
				repaymentDto.setAirAgencyQueryDtoList(list);
				//设置vo
				vo.setRepaymentDto(repaymentDto);
			}
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
			RepaymentDto repaymentDto = repaymentService.queryPaymentByWaybillNo(vo.getRepaymentDto().getAirAgencyQueryDto().getWaybillNo());
			boolean  isLack= repaymentService.isLack(vo.getRepaymentDto().getAirAgencyQueryDto().getWaybillNo());
			//过滤数据  如果运单有上报差错   派送方式非自提    整车、偏线、空运及快递货物免收保管费    add by  yangkang
			if(isLack||repaymentDto.getWaybillDto().getReceiveMethod().indexOf(SignConstants.RECEIVE_METHOD)==-1
					||WaybillConstants.MONTH_PAYMENT.equals(repaymentDto.getWaybillDto().getPaidMethod())
					||ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(repaymentDto.getWaybillDto().getProductCode())
					||ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(repaymentDto.getWaybillDto().getProductCode())
					||ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(repaymentDto.getWaybillDto().getProductCode())
					||WaybillConstants.directDetermineIsExpressByProductCode(repaymentDto.getWaybillDto().getProductCode())){
				
						repaymentDto.getWaybillDto().setStorageCharge(BigDecimal.ZERO);
						
			}else{
				//判断该运单是否已经有保管费小票记录产生    add   by  yangkang
				BigDecimal storageChargeSum = repaymentService.queryStorageChargeWithOtherRevenue(vo.getRepaymentDto().getAirAgencyQueryDto().getWaybillNo());
			    //如果该运单已经产生了小票记录，则应付的保管费为运单现有保管费与已付保管费的差值
				if(storageChargeSum.compareTo(BigDecimal.ZERO)>0){
					BigDecimal storageChargeTemp = repaymentDto.getWaybillDto().getStorageCharge().subtract(storageChargeSum);
					if(storageChargeTemp.compareTo(BigDecimal.ZERO)>0){
						repaymentDto.getWaybillDto().setStorageCharge(storageChargeTemp);
					}else{
						repaymentDto.getWaybillDto().setStorageCharge(BigDecimal.ZERO);
					}
				}	
			}
			
			if(repaymentDto.getWaybillDto().getStorageCharge()!=null){
				//对保管费取整数
				repaymentDto.getWaybillDto().setStorageCharge(repaymentDto.getWaybillDto().getStorageCharge().setScale(0,BigDecimal.ROUND_HALF_UP));	
			}
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
	 * 选中运单是否有签收单原件返回
	 * @return
	 */
	@JSON 
	public String queryWaybillByBack(){
		try {
			vo.setWaybillDto(repaymentService.queryWaybillByBack(vo.getRepaymentDto().getAirAgencyQueryDto().getWaybillNo()));
		} catch (BusinessException e) {
			return returnError(e);
		}
		
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
				this.repaymentService.addPaymentInfo(vo.getRepaymentEntity(),currentInfo,vo.getWaybillDto());
				
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
				this.repaymentService.validatePaymentInfo(vo.getRepaymentEntity(),currentInfo,vo.getWaybillDto());
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
   /**
    * 
    *
    * @Function: com.deppon.foss.module.pickup.sign.server.action.RepaymentAction.querystorageChargeUpdateInfo
    * @Description:在结清货款之前查询需要修改的运单保管费
    *
    * @return
    *
    * @version:v1.0
    * @author:130376-YANGKANG
    * @date:2014-5-28 下午4:07:40
    *
    * Modification History:
    * Date         Author      Version     Description
    * -----------------------------------------------------------------
    * 2014-5-28    130376-YANGKANG      v1.0.0         create
    */
	@JSON
	public String querystorageChargeUpdateInfo(){
		try {
			if (waybillVo != null) {
				WaybillDto dto=repaymentService.querystorageChargeUpdateInfo(waybillVo.getDto().getWaybillNo());
				if(dto!=null){
					if(dto.getStorageCharge()==null){
						dto.setStorageCharge(BigDecimal.ZERO);
					}	
				}
				waybillVo.setDto(dto);
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
	 *
	 * @Function: com.deppon.foss.module.pickup.sign.server.action.RepaymentAction.updatestorageCharge
	 * @Description:根据运单号更新运单的保管费
	 *
	 * @return
	 *
	 * @version:v1.0
	 * @author:130376-YANGKANG
	 * @date:2014-5-29 上午8:11:09
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2014-5-29    130376-YANGKANG      v1.0.0         create
	 */
	@JSON
	public String updatestorageCharge(){
		try {
			if(waybillVo != null){
				//判断该运单是否已经有保管费小票记录产生   如果有则不允许进行修改
				BigDecimal storageChargeSum = repaymentService.queryStorageChargeWithOtherRevenue(waybillVo.getDto().getWaybillNo());
				if(storageChargeSum.compareTo(BigDecimal.ZERO)>0){
					return returnError("该运单已经产生了保管费小票记录，作废小票之后，才可以修改!");
				}
				CurrentInfo userInfo = FossUserContext.getCurrentInfo();
				repaymentService.updatestorageCharge(waybillVo.getDto(),userInfo);
			}
		} catch (BusinessException e) {
			//返回异常
			return returnError(e);
		}
		//成功
		return returnSuccess();
	}	
	
	
	/**
	 * 提前找货
	 * @date 2014-11-27 下午3:58:10
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.server.action.RepaymentAction#updateInadvanceGoodsByRepayment()
	 */
	@JSON
	public String updateInadvanceGoodsByRepayment(){
		try {
			stateMsg=repaymentService.updateInadvanceGoodsByRepayment(waybillVo.getDto());
		} catch (SignException e) {
			return returnError(e);
		}
		return returnSuccess();
	}

	public String getStateMsg() {
		return stateMsg;
	}

	public void setStateMsg(String stateMsg) {
		this.stateMsg = stateMsg;
	}	
	
	/**
	 * 当付款方式为余额结清时,ajax查询余额,并将余额返回到页面
	 * @date 2016-11-18 下午3:58:10  add by 353654
	 * @return 
	 */
	@JSON
	public String queryBalanceAmount(){
		try {
			//如果vo不为空
			if (vo != null) 
			{
				CurrentInfo userInfo = FossUserContext.getCurrentInfo();//当前登录人
				AccountBookRequestDto requestDto = new AccountBookRequestDto();
				requestDto.setCustomerCode(vo.getRepaymentEntity().getConsigneeCode());
				requestDto.setDeptCode(userInfo.getCurrentDeptCode());
				balanceAmount = repaymentService.queryBalanceAmountByConsigneeCode(requestDto);
			}
		} catch (RepaymentException e) {
			return returnError(e);
		} catch(SignException e){		
			return returnError(e);
		}
		return returnSuccess();
	}
}