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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/action/ContrabandSignAction.java
 * 
 * FILE NAME        	: ContrabandSignAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.server.action;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.sign.api.server.service.IContrabandSignService;
import com.deppon.foss.module.pickup.sign.api.shared.exception.SignException;
import com.deppon.foss.module.pickup.sign.api.shared.vo.ContrabandInfoVo;
import com.deppon.foss.module.pickup.waybill.api.shared.define.PickupWaybillConstants;

/**
 * 
 * 违禁品签收action
 * @author foss-meiying
 * @date 2013-1-25 下午2:00:35
 * @since
 * @version
 */
public class ContrabandSignAction extends AbstractAction {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 3391314884651991352L;
	/**
	 * 违禁品vo
	 */
	private ContrabandInfoVo vo = new ContrabandInfoVo() ;
	/**
	 * 违禁品service
	 */
	private IContrabandSignService contrabandSignService;
	/** 
	 * 业务互斥锁服务
	 */
	private IBusinessLockService businessLockService;
	/**
	 * 根据运单号查询违禁品信息
	 * @author foss-meiying
	 * @date 2013-1-25 下午2:16:38
	 * @return
	 * @see
	 */
	@JSON
	public String queryContrabandInfoByWaybillNo() {
		try {
			vo.setContrabandInfoDto(contrabandSignService.queryContrabandInfoByWaybillNo(vo.getWaybillNo(),FossUserContext.getCurrentDeptCode()));
		} catch (SignException e) {//异常处理
			return returnError(e);
		}
		return returnSuccess();
	}
	/**
	 * 提交违禁品信息
	 * @author foss-meiying
	 * @date 2013-1-25 下午2:16:38
	 * @return
	 * @see
	 */
	@JSON
	public String addContrabandInfo() {
		MutexElement mutexElement = new MutexElement(vo.getContrabandInfoDto().getWaybillNo(), "运单号", MutexElementType.RFC_SIGN);
		try {
			// 获取当前用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//互斥锁定
			boolean isLocked = businessLockService.lock(mutexElement, NumberConstants.NUMBER_4);
			//若未上锁
			if(!isLocked){
				//抛出派送单异常
				throw new SignException(SignException.WAYBILL_LOCKED);
			}
			
			//设置违禁品签收
			vo.getContrabandInfoDto().setExceptionStatus(PickupWaybillConstants.CONTRABAND_GOODS);
			
//			contrabandSignService.addContrabandInfo(currentInfo, vo.getContrabandInfoDto());
			contrabandSignService.addContrabandInfoForReturnOrder(currentInfo, vo.getContrabandInfoDto());
			//解锁
			businessLockService.unlock(mutexElement);
		} catch (SignException e) {//异常处理
			//解锁
			businessLockService.unlock(mutexElement);
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 提交异常丢货信息
	 * @author foss-meiying
	 * @date 2013-1-25 下午2:16:38
	 * @return
	 * @see
	 */
	@JSON
	public String addLostCargoInfo() {
		try {
			// 获取当前用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//设置丢货签收
			vo.getContrabandInfoDto().setExceptionStatus(PickupWaybillConstants.MISS_GOODS);
			contrabandSignService.addContrabandInfoForReturnOrder(currentInfo, vo.getContrabandInfoDto());
		} catch (SignException e) {//异常处理
			return returnError(e);
		}
		return returnSuccess();
	}
	/**
	 * 根据运单号或入库时间查询丢货信息 
	 * @author foss-meiying
	 * @date 2013-1-25 下午2:16:38
	 * @return
	 * @see
	 */
	@JSON
	public String queryLostCargoInfoByCondition() {
		try {
			//得到当前登录部门
			vo.getLostCargoInfoDto().setOrgCode(FossUserContext.getCurrentDeptCode());
			this.totalCount = contrabandSignService.queryLostCargoCountByCondition(vo.getLostCargoInfoDto());
			// 如果存在信息
			if (this.totalCount != null && this.totalCount > 0) {
				vo.setLostCargoInfoDtoList((contrabandSignService.queryLostCargoInfoByCondition(vo.getLostCargoInfoDto(),this.getStart(), this.getLimit())));
			}else {
				vo.setLostCargoInfoDtoList(null);
			}
		} catch (SignException e) {//异常处理
			return returnError(e);
		}
		return returnSuccess();
	}
	/**
	 * 根据运单号查询签收件信息 
	 * @author foss-meiying
	 * @date 2013-1-25 下午2:16:38
	 * @return
	 * @see
	 */
	@JSON
	public String queryOptStockSerinalNo() {
		try {
			vo.getContrabandInfoDto().setSignDetailList(
					contrabandSignService.queryOptStockSerinalNo(vo.getWaybillNo(), FossUserContext.getCurrentDeptCode()));
		} catch (SignException e) {//异常处理
			return returnError(e);
		}
		return returnSuccess();
	}
	/**
	 * Gets the 违禁品vo.
	 *
	 * @return the 违禁品vo
	 */
	public ContrabandInfoVo getVo() {
		return vo;
	}
	
	/**
	 * Sets the 违禁品vo.
	 *
	 * @param vo the new 违禁品vo
	 */
	public void setVo(ContrabandInfoVo vo) {
		this.vo = vo;
	}
	
	/**
	 * Sets the 违禁品service.
	 *
	 * @param contrabandSignService the new 违禁品service
	 */
	public void setContrabandSignService(IContrabandSignService contrabandSignService) {
		this.contrabandSignService = contrabandSignService;
	}
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}
	
	
	
}