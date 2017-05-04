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
 * PROJECT NAME	: pkp-pickup
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pickup/server/action/CashDiffReportAction.java
 * 
 * FILE NAME        	: CashDiffReportAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pickup.server.action;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.pickup.pickup.api.server.service.ICashDiffReportService;
import com.deppon.foss.module.pickup.pickup.api.shared.exception.CashDiffReportException;
import com.deppon.foss.module.pickup.pickup.api.shared.vo.CashDiffReportVo;

/**
 * 接送货现金差异报表
 * 		-查看现金差异报表 Action
 * @author admin
 * @date 2012-11-21 下午1:58:34
 * 
 */
public class CashDiffReportAction extends AbstractAction {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 视图对象
	 */
	private CashDiffReportVo vo = new CashDiffReportVo();
	
	/**
	 * 现金差异报表service
	 */
	private ICashDiffReportService cashDiffReportService;
	
	/**
	 * 查询现金差异报表
	 * @date 2012-11-21 下午1:58:34
	 */
	@JSON
	public String queryCashDiffReport() {
		try{
			//查询签收单返单数量
			Long totalCount = cashDiffReportService.queryCashDiffReportTotal(vo.getCashDiffReportDto());
			if(totalCount != null && totalCount.intValue() > 0)
			{
				//查询签收单返单
				vo.setRtCashDiffReportDtoList( cashDiffReportService.queryCashDiffReport(
						vo.getCashDiffReportDto(),this.getStart(),
						this.getLimit()));
			}else{
				vo.setRtCashDiffReportDtoList(null);
			}
			//TOTAL
			this.setTotalCount(totalCount);
		} catch (CashDiffReportException e) {
			//GO ERROR
			return returnError(e);
		}
		//GO SUCCESS
		return returnSuccess();
	}

	/**
	 * Sets 
	 * 		the 现金差异报表service.
	 *
	 * @param cashDiffReportService 
	 * 		the new 现金差异报表service
	 */
	public void setCashDiffReportService(
			ICashDiffReportService cashDiffReportService) {
		this.cashDiffReportService = cashDiffReportService;
	}


	/**
	 * Gets the 视图对象.
	 *
	 * @return the 视图对象
	 */
	public CashDiffReportVo getVo() {
		return vo;
	}


	/**
	 * Sets the 视图对象.
	 *
	 * @param vo the new 视图对象
	 */
	public void setVo(CashDiffReportVo vo) {
		this.vo = vo;
	}
	
	
}