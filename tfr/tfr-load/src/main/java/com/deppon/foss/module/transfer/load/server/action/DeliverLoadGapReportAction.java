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
 *  PROJECT NAME  : tfr-load
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/server/action/DeliverLoadGapReportAction.java
 *  
 *  FILE NAME          :DeliverLoadGapReportAction.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-load
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.server.action
 * FILE    NAME: DeliverLoadGapReportAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.server.action;

import java.io.InputStream;
import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.service.IDeliverLoadGapReportService;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportSerialEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportWayBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.vo.DeliverLoadGapReportVo;


/**
 * 派送装车差异报告
 * @author dp-duyi
 * @date 2012-10-26 下午3:00:36
 */
public class DeliverLoadGapReportAction extends AbstractAction {

	private static final long serialVersionUID = -8235972483861353384L;
	private DeliverLoadGapReportVo vo;
	private IDeliverLoadGapReportService deliverLoadGapReportService;
	private InputStream deliverLoadGapReportDetailExcelStream;
	private String excelFileName;
	public InputStream getDeliverLoadGapReportDetailExcelStream() {
		return deliverLoadGapReportDetailExcelStream;
	}
	public void setDeliverLoadGapReportDetailExcelStream(
			InputStream deliverLoadGapReportDetailExcelStream) {
		this.deliverLoadGapReportDetailExcelStream = deliverLoadGapReportDetailExcelStream;
	}
	
	public String getExcelFileName() {
		return excelFileName;
	}
	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}
	public String queryDeliverLoadGapReport(){
		try{
			List<DeliverLoadGapReportEntity> reports = deliverLoadGapReportService.queryDeliverLoadGapReport(vo.getReport(), vo.getQueryTimeBegin(), vo.getQueryTimeEnd(), this.getLimit(), this.getStart());
			vo.setReports(reports);
			this.setTotalCount(deliverLoadGapReportService.queryDeliverLoadGapReportCount(vo.getReport(), vo.getQueryTimeBegin(), vo.getQueryTimeEnd()));
		}catch(TfrBusinessException e){
			return super.returnError(e);
		}	
		return returnSuccess();
	}
	public String queryDeliverLoadGapReportWayBills(){
		try{
			List<DeliverLoadGapReportWayBillEntity> reportWayBills = deliverLoadGapReportService.queryDeliverLoadGapReportWayBillsById(vo.getReport());
			vo.setReportWayBills(reportWayBills);
		}catch(TfrBusinessException e){
			return super.returnError(e);
		}	
		return returnSuccess();
	}
	/**
	 * 导出差异报告明细
	 * @author 045923-foss-shiwei
	 * @date 2013-5-29 下午3:02:26
	 */
	@SuppressWarnings("rawtypes")
	public String exportDeliverLoadGapDetailExcel(){
		//调用service，获取文件名、输入流
		List list = null;
		try{
			//调用service
			list = deliverLoadGapReportService.getDeliverLoadGapDetailExcelInputStream(vo.getReport().getTaskNo());
		}catch(BusinessException e){
			//返回业务异常信息
			return returnError(e);
		}
		//文件名
		excelFileName = (String)list.get(0);
		//文件流
		deliverLoadGapReportDetailExcelStream = (InputStream) list.get(1);
		return returnSuccess();
	}
	public String queryDeliverLoadGapReportSerials(){
		try{
			List<DeliverLoadGapReportSerialEntity> reportSerials = deliverLoadGapReportService.queryDeliverLoadGapReportSerials(vo.getReportWayBill());
			vo.setReportSerials(reportSerials);
		}catch(TfrBusinessException e){
			return super.returnError(e);
		}	
		return returnSuccess();
	}
	public DeliverLoadGapReportVo getVo() {
		return vo;
	}
	public void setVo(DeliverLoadGapReportVo vo) {
		this.vo = vo;
	}
	public void setDeliverLoadGapReportService(
			IDeliverLoadGapReportService deliverLoadGapReportService) {
		this.deliverLoadGapReportService = deliverLoadGapReportService;
	}
	
}