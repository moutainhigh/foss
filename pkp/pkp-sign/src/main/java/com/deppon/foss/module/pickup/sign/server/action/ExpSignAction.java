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

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignArriveSheetDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.ArriveSheetVo;
import com.deppon.foss.module.pickup.sign.api.server.service.IExpSignService;
import com.deppon.foss.module.pickup.sign.api.shared.exception.SignException;
import com.deppon.foss.module.pickup.sign.api.shared.vo.SignDetailVo;
import com.deppon.foss.module.pickup.sign.api.shared.vo.SignVo;
import com.deppon.foss.module.pickup.sign.api.shared.vo.StockVo;

/**
 * 签收出库Action
 * @author foss-yuting
 * @date 2014-10-09 上午10:55:12
 * @since
 * @version
 */
public class ExpSignAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	/**
	 * 签收出库service
	 */
	private IExpSignService expSignService;

	/**
	 * 签收出库vo
	 */
	private SignVo signVo = new SignVo();
	/**
	 * 库存vo
	 */
	private StockVo stockVo = new StockVo();
	/**
	 * 签收明细vo
	 */
	private SignDetailVo signDetailVo = new SignDetailVo();

	/**
	 * 定义查询签收出库界面的库存到达联
	 */
	private ArriveSheetVo arriveSheetVo = new ArriveSheetVo();
	
	/**
	 * 营业部Service
	 */
	private ISaleDepartmentService saleDepartmentService;

	/**
	 * 
	 * 签收出库--查询到达联
	 * @author foss-yuting
	 * @date 2014-10-09 上午10:55:12
	 * @return
	 * @see
	 */
	@JSON
	public String queryArriveSheetInfo() {
		try {
			// 判断是否是合伙人部门，如果不是合伙人部门则直接返回
			/*SaleDepartmentEntity saleDept = saleDepartmentService
					.querySaleDepartmentInfoByCode(FossUserContext.getCurrentDeptCode());
			if (null != saleDept) {
				if (FossConstants.YES.equals(saleDept.getIsLeagueSaleDept())) {
					throw new SignException("当前部门不是直营部门!");
				}
			}*/
			
			// 当前登录人的部门编码
			signVo.getSignDto().setLastLoadOrgCode(FossUserContext.getCurrentDeptCode());
			arriveSheetVo=expSignService.queryArriveSheetByParams(signVo.getSignDto(),this.getStart(), this.getLimit());
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
	 * 
	 * 提交录入的签收信息
	 * @author foss-yuting
	 * @date 2014-10-09 上午10:55:12
	 * @return
	 * @see
	 */
	@JSON
	public String addSign() {
		try {
			// 获取当前用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 录入签收信息到签收明细里
			String resultMsg = expSignService.addSign(signDetailVo.getSignDetailList(),arriveSheetVo.getArriveSheet(), signVo.getLineSignDto(), currentInfo,signVo.getOrderNo());
			if (StringUtils.isNotBlank(resultMsg)) {
				return returnSuccess(resultMsg);
			}
		} catch (BusinessException e) {
			// 处理异常
			return returnError(e);
		}
		// 返回成功
		return returnSuccess(SignException.SIGN_SUCCESS);//签收出库成功
	}

	/**
	 * 查询运单的货物流水号
	 * @author foss-yuting
	 * @date 2014-10-09 上午10:55:12
	 * @return
	 * @see
	 */
	@JSON
	public String querySerialNo() {
		try {
			// 获取当前用户登录的部门编码
			signVo.getSignDto().setLastLoadOrgCode(FossUserContext.getCurrentDeptCode());
			// 根据运单编号
			stockVo.setStockDtoList(expSignService.queryStock(signVo.getSignDto()));
		} catch (SignException e) {
			// 返回错误信息
			return returnError(e);
		}
		// 返回成功
		return returnSuccess();
	}
	
	/**
	 * 根据运单号查询子母件信息
	 * @author 231438-chenjunying
	 * @date 2015-08-24 上午09:55:12
	 * @return
	 * @see
	 */
	@JSON
	public String queryCzmInfo(){
		try{
			// 获取当前用户登录的部门编码
			signVo.getSignDto().setLastLoadOrgCode(FossUserContext.getCurrentDeptCode());
			signVo.setCzmSignDto(expSignService.queryCzmInfo(signVo.getSignDto()));
		}catch(SignException e){
			// 返回错误信息
			return returnError(e);
		}
		return returnSuccess();
	}
	/**
	 * 查询子母件在库运单及流水号
	 * @author 231438-chenjunying
	 * @date 2015-08-20 上午09:55:12
	 * @return
	 * @see
	 */
	@JSON
	public String queryCzmInSerialNo(){
		try{
			// 获取当前用户登录的部门编码
			signVo.getSignDto().setLastLoadOrgCode(FossUserContext.getCurrentDeptCode());
			//根据在库运单号，查询流水号(运单号-流水号) 子母件都只有一件也就是一个运单号对应一个流水号
			stockVo.setStockDtoList(expSignService.queryCzmInStock(signVo.getSignDto()));
		}catch(SignException e){
			// 返回错误信息
			return returnError(e);
		}
		return returnSuccess();
	}
	/**
	 * 查询子母件签收限制配置参数的值
	 * @author 231438-chenjunying
	 * @date 2015-09-09 上午10:55:12
	 * @return
	 * @see
	 */
	@JSON
	public String queryCzmSignLimit(){
		try{
			//获取子母件签收配置限制值
			String value = expSignService.queryCzmSignLimit(signVo.getCzmSignDto().getCzmSignDataCode());
			if(null != value){
				signVo.getCzmSignDto().setCzmSignLimit(Integer.parseInt(value));
			}
		}catch(SignException e){
			// 返回错误信息
			return returnError(e);
		}
		return returnSuccess();
	}
	/**
	 * 子母件签收
	 * @author 231438-chenjunying
	 * @date 2015-09-010 上午09:45:12
	 * @return
	 * @see
	 */
	@JSON
	public String addCzmBatchSign(){
		try {
			// 获取当前用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			String resultMsg = expSignService.addBatchCzmSign(signDetailVo.getCzmSignList(),
					arriveSheetVo.getArriveSheet(),signVo.getLineSignDto(), currentInfo,signVo.getOrderNo());
			if(StringUtils.isNotEmpty(resultMsg)){
				return returnSuccess(resultMsg);
			}
		}catch(SignException e){
			// 返回错误信息
			return returnError(e);
		}
		return returnSuccess();
	}
	/**
	 * Sets the 签收出库service.
	 *
	 * @param signService the new 签收出库service
	 */
	public void setExpSignService(IExpSignService expSignService) {
		this.expSignService = expSignService;
	}

	/**
	 * Gets the 签收出库vo.
	 *
	 * @return the 签收出库vo
	 */
	public SignVo getSignVo() {
		return signVo;
	}
	
	/**
	 * Sets the 签收出库vo.
	 *
	 * @param signVo the new 签收出库vo
	 */
	public void setSignVo(SignVo signVo) {
		this.signVo = signVo;
	}
	
	/**
	 * Gets the 定义查询签收出库界面的库存到达联.
	 *
	 * @return the 定义查询签收出库界面的库存到达联
	 */
	public ArriveSheetVo getArriveSheetVo() {
		return arriveSheetVo;
	}
	
	/**
	 * Sets the 定义查询签收出库界面的库存到达联.
	 *
	 * @param arriveSheetVo the new 定义查询签收出库界面的库存到达联
	 */
	public void setArriveSheetVo(ArriveSheetVo arriveSheetVo) {
		this.arriveSheetVo = arriveSheetVo;
	}
	
	/**
	 * Gets the 库存vo.
	 *
	 * @return the 库存vo
	 */
	public StockVo getStockVo() {
		return stockVo;
	}
	
	/**
	 * Sets the 库存vo.
	 *
	 * @param stockVo the new 库存vo
	 */
	public void setStockVo(StockVo stockVo) {
		this.stockVo = stockVo;
	}
	
	/**
	 * Gets the 签收明细vo.
	 *
	 * @return the 签收明细vo
	 */
	public SignDetailVo getSignDetailVo() {
		return signDetailVo;
	}
	
	/**
	 * Sets the 签收明细vo.
	 *
	 * @param signDetailVo the new 签收明细vo
	 */
	public void setSignDetailVo(SignDetailVo signDetailVo) {
		this.signDetailVo = signDetailVo;
	}

	/**
	 * @param saleDepartmentService the saleDepartmentService to set
	 */
	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}
	
}