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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/action/SignAction.java
 * 
 * FILE NAME        	: SignAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.server.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignArriveSheetDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.ArriveSheetVo;
import com.deppon.foss.module.pickup.sign.api.server.service.IDeliverHandlerService;
import com.deppon.foss.module.pickup.sign.api.server.service.IPtpSignService;
import com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignService;
import com.deppon.foss.module.pickup.sign.api.shared.dto.FinancialDto;
import com.deppon.foss.module.pickup.sign.api.shared.exception.DeliverHandlerException;
import com.deppon.foss.module.pickup.sign.api.shared.exception.SignException;
import com.deppon.foss.module.pickup.sign.api.shared.vo.DeliverbillDetailVo;
import com.deppon.foss.module.pickup.sign.api.shared.vo.PtpSignVo;
import com.deppon.foss.module.pickup.sign.api.shared.vo.SignDetailVo;
import com.deppon.foss.module.pickup.sign.api.shared.vo.SignVo;
import com.deppon.foss.util.define.FossConstants;

/**
 * 合伙人签收出库Action
 * @author foss-239284
 * @since
 * @version
 */
public class PtpSignAction extends AbstractAction {
	
	private static final Logger LOGGER = LogManager
			.getLogger(PtpSignAction.class);
	
	/**
	 * 签收出库vo
	 */
	private SignVo signVo = new SignVo();
	
	/**
	 * 定义查询签收出库界面的库存到达联
	 */
	private ArriveSheetVo arriveSheetVo = new ArriveSheetVo();
	
	/**
	 * 派送明细 Vo
	 */
	private DeliverbillDetailVo deliverbillDetailVo = new DeliverbillDetailVo();
	/**
	 * 合伙人签收VO
	 */
	private PtpSignVo ptpSignVo = new PtpSignVo();
	/**
	 * 签收明细vo
	 */
	private SignDetailVo signDetailVo = new SignDetailVo();
	/** 
	 * 零
	 */
	private static final int ZERO = 0;	
	
	
	/**
	 * 签收出库service
	 */
	private ISignService signService;
	/**
	 * 派送处理Service
	 */
	private IDeliverHandlerService deliverHandlerService;
	/**
	 * 合伙人签收service
	 */
	private IPtpSignService	ptpSignService;
	/**
	 * 结清货款Service
	 */
	private IRepaymentService repaymentService;
	/** 
	 * 业务互斥锁服务
	 */
	private IBusinessLockService businessLockService;
	/**
	 *  营业部 Service
	 */
	private ISaleDepartmentService saleDepartmentService;
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * 签收出库--查询到达联
	 * @author foss-239284
	 * @return
	 * @see
	 */
	@JSON
	public String queryArriveSheetInfoPtp() {
		try {
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//判断是否是合伙人部门，如果不是合伙人部门则直接返回
			SaleDepartmentEntity saleDept = saleDepartmentService.querySaleDepartmentInfoByCode(currentInfo.getCurrentDeptCode());
			if (null != saleDept) {
				if (!FossConstants.YES.equals(saleDept.getIsLeagueSaleDept())) {
					throw new SignException("当前部门不是合伙人对接部门!");
				}
			}
			//OrgAdministrativeInfoEntity  a =	FossUserContext.getCurrentDept();
			// 当前登录人的部门编码
			signVo.getSignDto().setLastLoadOrgCode(FossUserContext.getCurrentDeptCode());
			arriveSheetVo=signService.queryPtpArriveSheetByParams(signVo.getSignDto(),this.getStart(), this.getLimit());
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List<SignArriveSheetDto> signArriveSheetDtoList=arriveSheetVo.getSignArriveSheetDtoList();
			if(signArriveSheetDtoList!=null){
				for(int i=0;i<signArriveSheetDtoList.size();i++){
					SignArriveSheetDto tmp=signArriveSheetDtoList.get(i);
					tmp.setServiceTime(sdf.format(new Date()));//将服务器现在时间传到页面显示
				}
			}
			this.totalCount=arriveSheetVo.getTotalCount();
		} catch (BusinessException e) {
			// 返回错误信息
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 合伙人零担签收-查询财务信息
	 * 2016-01-12
	 * 239284
	 * @return
	 */
	@JSON
	public String queryFinanceForPtp() {
		try {
			String waybillNo = deliverbillDetailVo.getDeliverbillDetailDto().getWaybillNo();
			//判断运单号是否为空
			if(StringUtils.isBlank(waybillNo)) {
				//运单号为空
				throw new DeliverHandlerException(DeliverHandlerException.WAYBILLNO_IS_NULL);
			}
			//根据运单号查询财务信息
			FinancialDto financialDto=deliverHandlerService.queryFinanceSign(waybillNo);
			
			// 查询财务信息
			deliverbillDetailVo.setFinancialDto(financialDto);
			
		} catch (BusinessException e) {
			// 返回错误信息
			return returnError(e);
		}
		return returnSuccess();
	}
	
	
	/**
	 * 
	 * 提交录入的签收信息
	 * @author foss-239284
	 * @date 2012-10-17 上午10:43:00
	 * @return
	 */
	@JSON
	public String addSignForPtp() {
		LOGGER.info("合伙人零担签收。签收开始....");
		MutexElement mutexElement = new MutexElement(this.getPtpSignVo().getWaybillNo(), "合伙人异常签收", MutexElementType.PTP_SIGN_EXCEPTION_WAYBILL_NO);
		try {
			
			// 获取当前用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			PtpSignVo ptpVo = this.getPtpSignVo();
			
			//互斥锁定
			boolean isLocked = businessLockService.lock(mutexElement, ZERO);
			//如果没有上锁
			if(!isLocked){
				//返回异常
				return returnError("运单"+ptpVo.getWaybillNo() + ", 正在进行操作，请稍后再试!");
			}
			
			if (StringUtils.isEmpty(ptpVo.getWaybillNo())) {
				LOGGER.error("合伙人零担签收。运单为空!");
				// 抛出业务异常
				throw new SignException("运单不能为空!");
			}
			LOGGER.info("合伙人零担签收。运单号是:"+ptpVo.getWaybillNo());
			
			//判断是否结清: true 表已经结清
			boolean repaymentFlag = repaymentService.isPayment(ptpVo.getWaybillNo());
			//代收货款校验与扣款
			if (!repaymentFlag) {
				ptpSignService.validatePayCOD(ptpVo, currentInfo);
			}
			
			//签收并核销应收单
			String resultMsg  = this.ptpSignService.wrieOffAndSign(ptpVo, signDetailVo.getSignDetailList(),arriveSheetVo.getArriveSheet(), signVo.getLineSignDto(), currentInfo, signVo.getOrderNo());
			
			if (StringUtils.isNotBlank(resultMsg)) {
				return returnSuccess(resultMsg);
			}
		} catch (BusinessException e) {
			LOGGER.error("合伙人零担签收异常", e);
			//解锁
			businessLockService.unlock(mutexElement);
			// 处理异常
			return returnError(e);
		} finally {
			businessLockService.unlock(mutexElement);
		}
		LOGGER.info("合伙人零担签收。签收结束....");
		// 返回成功
		return returnSuccess(SignException.SIGN_SUCCESS);//签收出库成功
	}
	
	
	
	
	
	
	public SignVo getSignVo() {
		return signVo;
	}

	public void setSignVo(SignVo signVo) {
		this.signVo = signVo;
	}

	public ArriveSheetVo getArriveSheetVo() {
		return arriveSheetVo;
	}

	public void setArriveSheetVo(ArriveSheetVo arriveSheetVo) {
		this.arriveSheetVo = arriveSheetVo;
	}

	public void setSignService(ISignService signService) {
		this.signService = signService;
	}

	public DeliverbillDetailVo getDeliverbillDetailVo() {
		return deliverbillDetailVo;
	}

	public void setDeliverbillDetailVo(DeliverbillDetailVo deliverbillDetailVo) {
		this.deliverbillDetailVo = deliverbillDetailVo;
	}

	public void setDeliverHandlerService(
			IDeliverHandlerService deliverHandlerService) {
		this.deliverHandlerService = deliverHandlerService;
	}
	
	public void setPtpSignService(IPtpSignService ptpSignService) {
		this.ptpSignService = ptpSignService;
	}

	public PtpSignVo getPtpSignVo() {
		return ptpSignVo;
	}

	public void setPtpSignVo(PtpSignVo ptpSignVo) {
		this.ptpSignVo = ptpSignVo;
	}

	public void setRepaymentService(IRepaymentService repaymentService) {
		this.repaymentService = repaymentService;
	}

	public SignDetailVo getSignDetailVo() {
		return signDetailVo;
	}

	public void setSignDetailVo(SignDetailVo signDetailVo) {
		this.signDetailVo = signDetailVo;
	}

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}
	
}