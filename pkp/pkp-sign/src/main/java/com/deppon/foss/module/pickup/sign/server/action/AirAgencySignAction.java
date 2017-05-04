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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/action/AirAgencySignAction.java
 * 
 * FILE NAME        	: AirAgencySignAction.java
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

import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.sign.api.server.service.IAirAgencySignService;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.sign.api.shared.exception.AirAgencySignException;
import com.deppon.foss.module.pickup.sign.api.shared.vo.AirAgencySignVo;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


/**
 * 
 * 签收偏线空运货物action
 * @author foss-meiying
 * @date 2012-10-16 下午2:24:19
 * @since
 * @version
 */
public class AirAgencySignAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager
			.getLogger(AirAgencySignAction.class);
	/**
	 * 签收偏线空运货物service
	 */
	private IAirAgencySignService airAgencySignService;
	/**
	 * 业务互斥锁服务
	 */
	private IBusinessLockService businessLockService;
	/**
	 * 零
	 */
	private static final int ZERO = 0;
	/**
	 * 偏线空运vo
	 */
	private AirAgencySignVo airAgencySignVo=new AirAgencySignVo();
	/**
	 * 
	 * 保存签收信息
	 * @author foss-meiying
	 * @date 2012-10-16 下午3:02:17
	 * @return
	 * @see
	 */
	@JSON
	public String addWaybillSignResult(){
		LOGGER.info("空运/偏线签收。签收开始....");
		MutexElement mutexElement = new MutexElement(airAgencySignVo.getLineSignDto().getWaybillNo(), "空运/偏线异常签收", MutexElementType.RFC_SIGN);
		try {
			//互斥锁定
			boolean isLocked = businessLockService.lock(mutexElement, ZERO);
			//如果没有上锁
			if(!isLocked){
				//返回异常
				return returnError("运单"+airAgencySignVo.getLineSignDto().getWaybillNo() + ", 正在进行操作，请稍后再试!");
			}
			// 获取当前用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			String resultMsg = airAgencySignService.addWaybillSignResult(airAgencySignVo.getWaybillSignResultEntity(),currentInfo,airAgencySignVo.getLineSignDto(),airAgencySignVo.getOldArriveNotoutGoodsQty());
			if (StringUtils.isNotBlank(resultMsg)) {
				return returnSuccess(resultMsg);
			}
		} catch (BusinessException e) {
			LOGGER.error("空运/偏线签收异常", e);
			//解锁
			businessLockService.unlock(mutexElement);
			//异常处理
			return returnError(e);
		}finally {
			businessLockService.unlock(mutexElement);
		}
		LOGGER.info("空运/偏线签收。签收结束....");
		return returnSuccess(AirAgencySignException.SIGN_SUCCESS);//返回成功
	}
	/**
	 * 
	 * 根据条件查询待处理运单号
	 * @author foss-meiying
	 * @date 2012-10-16 下午3:04:03
	 * @return
	 * @see
	 */
	public String queryAirAgencySignByParams(){
		try {
			airAgencySignVo.getAirAgencyQueryDto().setLastLoadOrgCode(FossUserContext.getCurrentDeptCode());//当前登录部门
			airAgencySignVo.setAgencyQueryDtos(airAgencySignService.queryAirInfobyParams(airAgencySignVo.getAirAgencyQueryDto()));
		} catch (AirAgencySignException e) {
			//异常处理
			return returnError(e);
		}
		return returnSuccess();//返回成功
	}
	/**
	 * 
	 *根据单号查询运单基本信息
	 * @author foss-meiying
	 * @date 2012-10-16 下午3:02:45
	 * @return
	 * @see
	 */
	@JSON
	public String queryByWaybillNo(){
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			WaybillDto waybillDto=new WaybillDto();
			//查询运单基本信息
			waybillDto=airAgencySignService.queryByWaybillNoIsSign(airAgencySignVo.getAirAgencyQueryDto().getWaybillNo(),null);
			waybillDto.setServiceTime(sdf.format(new Date()));
			airAgencySignVo.setWaybillDto(waybillDto);
		} catch (AirAgencySignException e) {
			//异常处理
			return returnError(e);
		}
		return returnSuccess();
	}
	/**
	 * 根据运单号查询偏线外发单信息
	 * @author foss-meiying
	 * @date 2012-12-12 下午9:17:35
	 * @return
	 * @see
	 */
	@JSON
	public String queryExternalBillByWaybillNo(){
		try {
			airAgencySignVo.setExternalBillInfoDto(airAgencySignService.queryExternalBillByWaybillNo(airAgencySignVo.getAirAgencyQueryDto().getWaybillNo()));
		} catch (AirAgencySignException e) {
			//异常处理
			//返回错误信息
			return returnError(e);
		}
		//返回成功
		return returnSuccess();
	}
	/**
	 * set方法
	 */
	public void setAirAgencySignService(IAirAgencySignService airAgencySignService) {
		this.airAgencySignService = airAgencySignService;
	}
	/**
	 * get方法
	 */
	public AirAgencySignVo getAirAgencySignVo() {
		return airAgencySignVo;
	}
	/**
	 * set方法
	 */
	public void setAirAgencySignVo(AirAgencySignVo airAgencySignVo) {
		this.airAgencySignVo = airAgencySignVo;
	}

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}
}