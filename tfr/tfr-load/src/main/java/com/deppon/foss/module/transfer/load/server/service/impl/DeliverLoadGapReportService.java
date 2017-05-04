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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/server/service/impl/DeliverLoadGapReportService.java
 *  
 *  FILE NAME          :DeliverLoadGapReportService.java
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
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.server.service.impl
 * FILE    NAME: DeliverLoadGapReportService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.server.service.impl;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.dao.IDeliverLoadGapReportDao;
import com.deppon.foss.module.transfer.load.api.server.service.IDeliverLoadGapReportService;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportSerialEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportWayBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.DiffReportReturnNumEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.QueryDiffReportByWayBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.DeliverLoadGapReportWayBillDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * @author dp-duyi
 * @date 2012-10-25 下午3:34:05
 */
public class DeliverLoadGapReportService implements IDeliverLoadGapReportService{
	private IDeliverLoadGapReportDao deliverLoadGapReportDao;
	public void setDeliverLoadGapReportDao(
			IDeliverLoadGapReportDao deliverLoadGapReportDao) {
		this.deliverLoadGapReportDao = deliverLoadGapReportDao;
	}
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	private ISaleDepartmentService saleDepartmentService;
	
	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	/**
	 * Sets the org administrative info service.
	 *
	 * @param orgAdministrativeInfoService the new org administrative info service
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/** 
	 * 生成装车差异报告
	 * @author 042795-foss-duyi
	 * @date 2012-10-25 下午3:34:34
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IDeliverLoadGapReportService#createDeliverLoadGapReport()
	 */
	@Override
	public void createDeliverLoadGapReport() {
		deliverLoadGapReportDao.createDeliverLoadGapReort();
	}

	/** 
	 * 查询差异报告
	 * @author dp-duyi
	 * @date 2012-10-26 下午3:22:25
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IDeliverLoadGapReportService#queryDeliverLoadGapReport(com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportEntity, java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public List<DeliverLoadGapReportEntity> queryDeliverLoadGapReport(
			DeliverLoadGapReportEntity report, String queryTimeBegin,
			String queryTimeEnd, int limit, int start) {
		Map<String,Object> condition = this.makeQueryCondition(report, queryTimeBegin, queryTimeEnd);
		List<DeliverLoadGapReportEntity> reports = deliverLoadGapReportDao.queryDeliverLoadGapReport(condition, limit, start);
		if(CollectionUtils.isEmpty(reports)){
			return null;
			//throw new TfrBusinessException(DeliverLoadGapReportExceptionCode.DATA_NOTFIND_MESSAGECODE);
		}
		for(DeliverLoadGapReportEntity deliverLoadGapReportEntity : reports) {
				Integer  sumWaybill = deliverLoadGapReportDao.querySumWaybillReturn(deliverLoadGapReportEntity.getTaskNo());
				if(null != sumWaybill) {
					deliverLoadGapReportEntity.setPlanLoadGoodsQty(deliverLoadGapReportEntity.getPlanLoadGoodsQty() + sumWaybill);
					deliverLoadGapReportEntity.setLackGoodsQty(deliverLoadGapReportEntity.getLackGoodsQty() + sumWaybill);
				}
		}
		return reports;
	}

	/** 
	 * 查询差异报告条数
	 * @author dp-duyi
	 * @date 2012-10-29 上午8:27:51
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IDeliverLoadGapReportService#queryDeliverLoadGapReportCount(com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportEntity, java.lang.String, java.lang.String)
	 */
	@Override
	public Long queryDeliverLoadGapReportCount(
			DeliverLoadGapReportEntity report, String queryTimeBegin,
			String queryTimeEnd) {
		Map<String,Object> condition = this.makeQueryCondition(report, queryTimeBegin, queryTimeEnd);
		return deliverLoadGapReportDao.queryDeliverLoadGapReportCount(condition);
	}
	public Map<String,Object> makeQueryCondition(DeliverLoadGapReportEntity report, String queryTimeBegin,
			String queryTimeEnd){
		Map<String,Object> condition = new HashMap<String,Object>();
		if("ALL".equals(report.getState())){
			report.setState("");
		}
		report.setBeValid(FossConstants.ACTIVE);
		String orgCode = null;
		String currentOrgCode = FossUserContext.getCurrentDeptCode();
		OrgAdministrativeInfoEntity loginOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(currentOrgCode);
		if(loginOrg != null){
			if(FossConstants.YES.equals(loginOrg.getSalesDepartment())){
				orgCode = loginOrg.getCode();
				SaleDepartmentEntity saleDetp = saleDepartmentService.querySaleDepartmentByCode(loginOrg.getCode());
				if(saleDetp != null && FossConstants.YES.equals(saleDetp.getStation())){
					orgCode = saleDetp.getTransferCenter();
				}
			}else{
				List<String> bizTypes = new ArrayList<String>();
				bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
				OrgAdministrativeInfoEntity org = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(currentOrgCode,bizTypes);
				if(org != null){
					orgCode = org.getCode();	
				}else{
					//查询车队，通过车队查外场
					bizTypes.add(BizTypeConstants.ORG_TRANS_DEPARTMENT);
					org = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(currentOrgCode,bizTypes);
					if(org != null){
						List<String> orgCodes = deliverLoadGapReportDao.queryTransferCenterByMotorcade(org.getCode());
						if(!CollectionUtils.isEmpty(orgCodes)){
							report.setOrgCodes(orgCodes);
						}
					}
				}
			}	
		}
		report.setOrigOrgCode(orgCode);
		condition.put("report", report);
		condition.put("queryTimeBegin", queryTimeBegin);
		condition.put("queryTimeEnd", queryTimeEnd);
		return condition;
	}

	/** 
	 * 查询运单明细
	 * 接送货接口，根据派送单号查询实际少货的运单（查询装车任务运单表），包括差异报告中的和没在差异报告中的
	 * @author dp-duyi
	 * @date 2012-10-29 下午1:57:36
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IDeliverLoadGapReportService#queryDeliverLoadGapReportWayBills(com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportEntity)
	 */
	@Override
	public List<DeliverLoadGapReportWayBillEntity> queryDeliverLoadGapReportWayBills(
			DeliverLoadGapReportEntity report) {
		return deliverLoadGapReportDao.queryDeliverLoadGapReportWayBills(report);
	}

	/** 
	 * 查询流水号明细
	 * @author dp-duyi
	 * @date 2012-10-29 下午1:57:36
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IDeliverLoadGapReportService#queryDeliverLoadGapReportSerials(com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportWayBillEntity)
	 */
	@Override
	public List<DeliverLoadGapReportSerialEntity> queryDeliverLoadGapReportSerials(
			DeliverLoadGapReportWayBillEntity reportWayBill) {
		return deliverLoadGapReportDao.queryDeliverLoadGapReportSerials(reportWayBill);
	}

	/** 
	 * 查询运单明细，界面显示，查询明细
	 * @author dp-duyi
	 * @date 2013-6-24 上午9:45:11
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IDeliverLoadGapReportService#queryDeliverLoadGapReportWayBillsById(com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportEntity)
	 */
	@Override
	public List<DeliverLoadGapReportWayBillEntity> queryDeliverLoadGapReportWayBillsById(
			DeliverLoadGapReportEntity report) {
		return deliverLoadGapReportDao.queryDeliverLoadGapReportWayBillsById(report);
	}

	/** 
	 * 导出差异报告明细
	 * @author dp-duyi
	 * @date 2013-7-12 下午12:41:18
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IDeliverLoadGapReportService#getDeliverLoadGapDetailExcelInputStream(java.lang.String)
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getDeliverLoadGapDetailExcelInputStream(String taskNo) {
		// TODO Auto-generated method stub
		//获取交接单下所有运单
				List<DeliverLoadGapReportWayBillDto> gapReportDetails = deliverLoadGapReportDao.queryExportDeliverLoadGapDetail(taskNo);
				List<List<String>> rowList = new ArrayList<List<String>>();
				if(CollectionUtils.isEmpty(gapReportDetails)){
					List<String> columnList = new ArrayList<String>();
					rowList.add(columnList);
				}
				for(DeliverLoadGapReportWayBillDto gapReportDetail : gapReportDetails){
					List<String> columnList = new ArrayList<String>();
					//运单号
					columnList.add(gapReportDetail.getWayBillNo());
					//差异类型
					columnList.add(gapReportDetail.getGapType());
					//预计装车件数
					columnList.add(String.valueOf(gapReportDetail.getPlanLoadQty()));
					//实际装车件数
					columnList.add(String.valueOf(gapReportDetail.getActualLoadQty()));
					//差异件数
					columnList.add(String.valueOf(gapReportDetail.getLackGoodsQty()));
					//运输性质
					columnList.add(gapReportDetail.getTransportType());
					//未装车备注
					columnList.add(gapReportDetail.getNotes());
					//目的站
					columnList.add(gapReportDetail.getDestination());
					rowList.add(columnList);
				}
				//定义表头
				String[] rowHeads = {"运单号",
												"差异类型",
												"预计装车件数",
												"实际装车件数",
												"差异件数",
												"运输性质",
												"未装车备注",
												"目的站"
												};
				//导出资源类
				ExportResource exportResource = new ExportResource();
				//设置导出文件的表头
			    exportResource.setHeads(rowHeads);
			    //设置导出数据
			    exportResource.setRowList(rowList);
			    //导出设置
			    ExportSetting exportSetting = new ExportSetting();
			    //设置sheetname
			    exportSetting.setSheetName("运单列表");
			    //设置sheet行数
			    if(CollectionUtils.isEmpty(gapReportDetails)){
			    	exportSetting.setSize(1);
			    }else{
			    	exportSetting.setSize(gapReportDetails.size() + 1);
			    }
			    ExporterExecutor objExporterExecutor = new ExporterExecutor();
			    //获取输入流
			    InputStream excelStream = objExporterExecutor.exportSync(exportResource, exportSetting);

		        //文件名
		        String fileName = null;
		    	try {
					String str = "派送装车差异报告明细" + "-装车任务编号：" + taskNo;
					String agent = (String) ServletActionContext.getRequest().getHeader(
							"USER-AGENT");
					if (agent != null && agent.indexOf("MSIE") == -1) {
						fileName = new String(str.getBytes("UTF-8"), "iso-8859-1");
					} else {
						fileName = URLEncoder.encode(str, "UTF-8");
					}
					//fileName = URLEncoder.encode("派送装车差异报告明细" + "-装车任务编号：" + taskNo, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					//抛出业务异常
					throw new TfrBusinessException(e.getMessage());
				} //设置fileName
		        List list = new ArrayList();
		        list.add(fileName);
		        list.add(excelStream);
		        //返回action
		        return list;
	}
	/**
	 * @author 269701--lln
	 * @date 2015-11-11上午10:39:43
	 * @function 根据运单查派送装车差异报告，显示出其中运单差异报告类型为“少货”、“退回”和预计装车件数的记录
	 * @param waybillNo 运单号 
	 * @return 预计装车件数、 差异报告类型
	 */
	@Override
	public List<QueryDiffReportByWayBillEntity> diffReportByWayBill(String waybillNo) {
		
		List<QueryDiffReportByWayBillEntity> diffReportReturnLackGoodsList=null;
		//运单号为空
		if(StringUtil.isEmpty(waybillNo)){
			//抛出业务异常
			throw new TfrBusinessException("运单号不能为空");
		}else{
			diffReportReturnLackGoodsList= deliverLoadGapReportDao.queryDiffReportByWayBill(waybillNo);
			return diffReportReturnLackGoodsList;
		}
			
	}
	
	
	/**
	 * @author 283250--liuyi
	 * @date 2016-02-25上午10:39:43
	 * @function 根据运单号以及交单开始时间，查询装车“少货”、“退回”次数
	 * @param waybillNo 运单号  surrenderTime 交单开始时间
	 * @return DiffReportReturnNumEntity “少货”、“退回”次数
	 */
	@Override
	public DiffReportReturnNumEntity queryDiffReportReturnNum(String waybillNo,Date surrenderTime) {
		//运单号为空
		if(StringUtil.isEmpty(waybillNo)||surrenderTime==null){
			//抛出业务异常
			throw new TfrBusinessException("运单号,交单时间不能为空");
		}else{
			Map<String,Object> queryEntity = new HashMap<String,Object>();
			//DiffReportReturnNumEntity queryEntity=new DiffReportReturnNumEntity();
			queryEntity.put("waybillNo", waybillNo);
			queryEntity.put("surrenderTime", surrenderTime);
			DiffReportReturnNumEntity resultEntity=deliverLoadGapReportDao.queryDiffReportReturnNum(queryEntity);
			if(resultEntity==null){
				resultEntity=new DiffReportReturnNumEntity();
				resultEntity.setReturnNum(0);
				resultEntity.setLoseNum(0);
				resultEntity.setWaybillNo(waybillNo);
				resultEntity.setSurrenderTime(surrenderTime);
			}
			return resultEntity;
		}
			
	}
}