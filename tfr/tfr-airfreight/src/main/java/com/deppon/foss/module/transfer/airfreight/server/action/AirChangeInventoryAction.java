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
 *  FILE PATH          :/AirChangeInventoryAction.java
 * 
 *  FILE NAME          :AirChangeInventoryAction.java
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

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirChangeInventoryService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirDispatchUtilService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirTransPickupBillDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.vo.AirChangeInventoryException;
import com.deppon.foss.module.transfer.airfreight.api.shared.vo.AirTransPickupBillVo;

/**
 * 变更清单Action.
 * @author 099197-foss-zhoudejun
 * @date 2012-12-12 下午4:31:51
 */
public class AirChangeInventoryAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8050312479755237586L;
	
	/** 变更清单service. */
	private IAirChangeInventoryService airChangeInventoryService;
	
	/** 注入合大票vo. */
	private AirTransPickupBillVo airTransPickupBillVo = new AirTransPickupBillVo();
	
	/** 导出Excel 文件流. */
	private InputStream excelStream;  
	
	/** 导出Excel 文件名. */
	private String fileName; 
	
	/** 查找空运总调 service*/
	private IAirDispatchUtilService airDispatchUtilService;

	/**
	 * (空运)根据运单号查询合大票明细.
	 * @return the string
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-12 下午4:38:59
	 */
	@JSON
	public String queryChangeInventoryDetail() {
		String waybillNO = airTransPickupBillVo.getAirTransPickupBillDto()
				.getWaybillNo();
  		//根据当前部门取空运总调
		String deptCode = FossUserContext.getCurrentDeptCode();
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = airDispatchUtilService.queryAirDispatchDept(deptCode);
		
		AirTransPickupBillDto airTransPickupBillDto= airChangeInventoryService
				.queryChangeInventoryDetail(waybillNO,orgAdministrativeInfoEntity.getCode());
		airTransPickupBillVo.setAirChangeInventoryList(airTransPickupBillDto.getAirChangeInventoryList());
		airTransPickupBillVo.setAirChangeInventoryDetailList(airTransPickupBillDto.getAirChangeInventoryDetailList());
		return super.returnSuccess();
	}

	/**
	 * 变更清单,修改清单logger日志.
	 * @return the string
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-13 下午8:28:12
	 */
	@SuppressWarnings("rawtypes")
	@JSON
	public String updateAirWaybillDetailOrLogger() {
		try {
			List<AirPickupbillDetailEntity> airPickupbillDetailList = airTransPickupBillVo
					.getAirPickupbillDetailList();
			Map<String,String> parameterMap = airTransPickupBillVo.getParameter();
			Map<String,String> stlWayBillNoMap = airTransPickupBillVo.getStlWayBillNoMap();
			Map<String,String> delWayBillNoMap = airTransPickupBillVo.getDelWayBillNoMap();
			List<List> allList = airChangeInventoryService.updateAirWaybillDetailOrLogger(airPickupbillDetailList,
				parameterMap,stlWayBillNoMap,delWayBillNoMap);
			airTransPickupBillVo.setAllList(allList);
		} catch (AirChangeInventoryException e) {
			return super.returnError(e);
		}
		return super.returnSuccess();
	}
	
	/**
	 * 上传变更清单
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-31 上午11:51:23
	 */
	public String callHangeListEdi(){
		String callIsNotEdiFlag = airTransPickupBillVo.getCallIsNotEdiFlag();
		List<String> idsList = airTransPickupBillVo.getIds();
		String airWaybillNo = airTransPickupBillVo.getAirWaybillNo();
		try {
			fileName = URLEncoder.encode(airWaybillNo+"变更清单","UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new AirChangeInventoryException("编码转换异常");
		}
		try {
			excelStream = airChangeInventoryService.uploadChangeListCallEdi(idsList,callIsNotEdiFlag,airWaybillNo);
		} catch (AirChangeInventoryException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 设置 变更清单service.
	 * @param airChangeInventoryService the new 变更清单service
	 */
	public void setAirChangeInventoryService(
			IAirChangeInventoryService airChangeInventoryService) {
		this.airChangeInventoryService = airChangeInventoryService;
	}
	
	/**
	 * 获取 注入合大票vo.
	 * @return the 注入合大票vo
	 */
	public AirTransPickupBillVo getAirTransPickupBillVo() {
		return airTransPickupBillVo;
	}
	
	/**
	 * 设置 注入合大票vo.
	 * @param airTransPickupBillVo the new 注入合大票vo
	 */
	public void setAirTransPickupBillVo(
			AirTransPickupBillVo airTransPickupBillVo) {
		this.airTransPickupBillVo = airTransPickupBillVo;
	}

	/**
	 * 获取 导出Excel 文件流.
	 *
	 * @return the 导出Excel 文件流
	 */
	public InputStream getExcelStream() {
		return excelStream;
	}

	/**
	 * 设置 导出Excel 文件流.
	 *
	 * @param excelStream the new 导出Excel 文件流
	 */
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	/**
	 * 获取 导出Excel 文件名.
	 *
	 * @return the 导出Excel 文件名
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * 设置 导出Excel 文件名.
	 *
	 * @param fileName the new 导出Excel 文件名
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public void setAirDispatchUtilService(
			IAirDispatchUtilService airDispatchUtilService) {
		this.airDispatchUtilService = airDispatchUtilService;
	}
}