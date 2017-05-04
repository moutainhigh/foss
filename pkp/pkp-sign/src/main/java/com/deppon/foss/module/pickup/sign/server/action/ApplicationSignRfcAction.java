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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/action/ApplicationSignRfcAction.java
 * 
 * FILE NAME        	: ApplicationSignRfcAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.server.action;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignChangeService;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RepaymentArrivesheetDto;
import com.deppon.foss.module.pickup.sign.api.shared.exception.SignException;
import com.deppon.foss.module.pickup.sign.api.shared.vo.ChangeSignResultVo;
import com.deppon.foss.util.define.FossConstants;


/**
 * 申请签收变更
 * @author ibm-lizhiguo
 * @date 2012-11-5 上午11:39:44
 */
public class ApplicationSignRfcAction extends AbstractAction {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	// Logger
	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationSignRfcAction.class);
	//申请签收变更service
	private ISignChangeService signChangeService;
	//vo
	private ChangeSignResultVo vo;

	/**
	 * 
	 * <p>注入对象<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2012-12-19
	 * @param signChangeService
	 * void
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
	 * 
	 * 专线，到达联和付款信息
	 * @author ibm-lizhiguo
	 * @date 2012-11-5 上午11:41:18
	 */
	@JSON
	public String querySignResultDedicated() {
		LOGGER.info("querySignResultDedicated() begin....");
		try {
			//获得专线数据
			RepaymentArrivesheetDto dto = signChangeService.searchRepaymentArrivesheet(vo.getSignResultDto().getSignRfcEntity().getWaybillNo());
			//设置专线数据
			vo.getSignResultDto().setRepaymentArrivesheetDto(dto);
			//是否有到达联和付款信息
			if(CollectionUtils.isEmpty(dto.getArriveSheetEntityList()) 
					&& CollectionUtils.isEmpty(dto.getRepaymentEntityList())){
				//判断是否有付款审批中的数据
				if(FossConstants.YES.equals(dto.getIsAuditingRepaymentFlg())
						|| FossConstants.YES.equals(dto.getIsAuditingArrivesheetFlg())){
					//前台显示消息
					throw new SignException("数据审批中");
				}
			}else{
				LOGGER.info("有数据");
			}
			//异常
		} catch (BusinessException e) {
			// 返回error
			return returnError(e);
		}
		LOGGER.info("querySignResultDedicated() end....");
		// 返回success
		return returnSuccess();
		
	}
	/**
	 * 
	 * 空运偏线，签收结果信息
	 * @author ibm-lizhiguo
	 * @date 2012-11-5 上午11:41:18
	 */
	@JSON
	public String querySignResultAirPartialLine() {
		LOGGER.info("querySignResultAirPartialLine() begin....");
		try {
			//获得查询的结果
			WaybillSignResultEntity entity = signChangeService.searchWaybillSignResult(vo.getSignResultDto().getSignRfcEntity().getWaybillNo());
			//设置返回的结果
			vo.setWaybillSignResultEntity(entity);
			//判断数据是否有
			if(entity == null || StringUtils.isEmpty(entity.getId())){
				//显示消息
//				throw new BusinessException("数据没找到");
				return returnError("空运/偏线/快递代理理数据没有找到");
				//看是否有在审批中的数据
			}else if(FossConstants.YES.equals(entity.getIsRfcing())){
				//显示消息
				//throw new SignException("数据审批中");
				return returnError("数据审批中");
			}
			//异常
		} catch (BusinessException e) {
			// 返回error
			return returnError("查询失败：" + e.getErrorCode());
		}
		LOGGER.info("querySignResultAirPartialLine() end....");
		// 返回success
		return returnSuccess();
	}
	/**
	 * 
	 * 专线变更数据保存
	 * @author ibm-lizhiguo
	 * @date 2012-11-7 下午4:22:54
	 */
	@JSON
	public String saveChangeDedicated() {
		LOGGER.info("saveChangeDedicated() begin....");
		try {
			//获取当前用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//保存专线变更
			signChangeService.saveChangeDedicated(vo.getSignResultDto(),currentInfo);
			//异常
		} catch (BusinessException e) {
			// 返回error
			return returnError(e);
		}
		LOGGER.info("saveChangeDedicated() end....");
		// 返回success
		return returnSuccess();
	}
	/**
	 * 
	 * 空运和偏线变更数据保存
	 * @author ibm-lizhiguo
	 * @date 2012-11-7 下午4:22:54
	 */
	@JSON
	public String saveChangeAirliftPartialLine() {
		LOGGER.info("saveChangeAirliftPartialLine() begin....");
		try {
			//获取当前用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//保存空运偏线的变更
			signChangeService.saveChangeAirliftPartialLine(vo.getSignResultDto(),currentInfo);
			//异常
		} catch (BusinessException e) {
			// 返回error
			return returnError(e);
		}
		LOGGER.info("saveChangeAirliftPartialLine() begin....");
		// 返回success
		return returnSuccess();
	}
	
}