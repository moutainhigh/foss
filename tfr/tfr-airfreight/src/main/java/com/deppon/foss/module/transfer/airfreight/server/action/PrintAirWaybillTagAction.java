/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-airfreight
 *  
 * package_name  : 
 *  
 *  FILE PATH          :/PrintAirWaybillTagAction.java
 * 
 *  FILE NAME          :PrintAirWaybillTagAction.java
 *  
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.server.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IPrintAirWaybillTagService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.vo.PrintAirWaybillTagVO;

/**
 * 打印正单标签相关
 * @author foss-liuxue(for IBM)
 * @date 2012-11-28 下午3:08:59
 */
public class PrintAirWaybillTagAction extends AbstractAction {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 3482976517200825317L;
	
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PrintAirWaybillTagAction.class);
	
	/**
	 * 打印标签service
	 */
	private IPrintAirWaybillTagService printAirWaybillTagService;
	
	/**
	 * 打印标签vo
	 */
	private PrintAirWaybillTagVO printAirWaybillTagVO = new PrintAirWaybillTagVO();

	/**
	 * 获取 打印标签vo.
	 *
	 * @return the 打印标签vo
	 */
	public PrintAirWaybillTagVO getPrintAirWaybillTagVO() {
		return printAirWaybillTagVO;
	}

	/**
	 * 设置 打印标签vo.
	 *
	 * @param printAirWaybillTagVO the new 打印标签vo
	 */
	public void setPrintAirWaybillTagVO(PrintAirWaybillTagVO printAirWaybillTagVO) {
		this.printAirWaybillTagVO = printAirWaybillTagVO;
	}

	/**
	 * 设置 打印标签service.
	 *
	 * @param printAirWaybillTagService the new 打印标签service
	 */
	public void setPrintAirWaybillTagService(
			IPrintAirWaybillTagService printAirWaybillTagService) {
		this.printAirWaybillTagService = printAirWaybillTagService;
	}

	/**
	 * 获取航空公司信息
	 * @author foss-liuxue(for IBM)
	 * @date 2012-11-28 下午7:23:21
	 */
	@JSON
	public String queryAllAirlines(){
		try{
			//获取航空公司信息
			List<AirlinesEntity> airlinesEntityList = printAirWaybillTagService.queryAllAirlines();
			//存入vo
			printAirWaybillTagVO.setAirlinesEntityList(airlinesEntityList);
			return returnSuccess();
		}catch(BusinessException e){
			//打印异常日志
			LOGGER.error(e.getMessage(), e);
			return returnError(e);
		}
	}
	
	/**
	 * 根据正单号获取相应信息
	 * @author foss-liuxue(for IBM)
	 * @date 2012-11-26 下午8:15:26
	 */
	@JSON
	public String queryWaybillInfo(){
		try{
			//根据正单号获取相应信息
			AirWaybillEntity airWaybillEntity = printAirWaybillTagService.queryAirWaybillInfo(printAirWaybillTagVO.getAirWayBillDto());
			//存入vo
			printAirWaybillTagVO.setAirWaybillEntity(airWaybillEntity);
			return returnSuccess();
		}catch(BusinessException e){
			//打印异常日志
			LOGGER.error(e.getMessage(), e);
			return returnError(e);
		}
	}
	
}