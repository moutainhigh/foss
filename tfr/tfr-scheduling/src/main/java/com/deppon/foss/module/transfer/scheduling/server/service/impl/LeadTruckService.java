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
 *  
 *  PROJECT NAME  : tfr-scheduling
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/service/impl/LeadTruckService.java
 * 
 *  FILE NAME     :LeadTruckService.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-scheduling
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.server.service.impl
 * FILE    NAME: PlatformDispatchService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.scheduling.server.service.impl;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.ILeadTruckDao;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ILeadTruckService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.LeadTruckEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.QueryLeadTruckEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.stock.api.shared.exception.StockException;
import com.deppon.foss.util.UUIDUtils;

/**
 * 月台Service实现
 * @author 104306-foss-liubinbin
 * @date 2012-10-31 下午3:43:00
 */
public class LeadTruckService implements ILeadTruckService {
	
	private static final Logger LOGGER = LogManager.getLogger(LeadTruckService.class);

	@Override
	public List<LeadTruckEntity> queryLeadTruckGrid(
			QueryLeadTruckEntity queryEntity, int limit, int start)
	{
		return leadTruckDao.queryLeadTruckGrid(queryEntity, limit, start);
	}

	/**
	 * 
	 * 增加一条外请车询价信息
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-6 上午10:21:50
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ILeadTruckService#addLeadTruck(com.deppon.foss.module.transfer.scheduling.api.shared.domain.LeadTruckEntity)
	 */
	@Override
	public void addLeadTruck(LeadTruckEntity entity)
	{
		//修改时间
		entity.setUpdateTime(new Date());
		if(entity.getId()==null||"".equals(entity.getId()))
		{//如果主键不存在，需要新增
			entity.setId(UUIDUtils.getUUID());
			//录入时间
			entity.setCreateTime(new Date());
			//录入人
			entity.setCreateUserCode(FossUserContext.getCurrentUser().getEmployee().getEmpCode());
			//录入人姓名
			entity.setCreateUserName(FossUserContext.getCurrentUser().getEmployee().getEmpName());
			leadTruckDao.addLeadTruck(entity);
		}else
		{//修改
			leadTruckDao.updateLeadTruck(entity);
		}
		
	}
	
	/** 
	 * @Title: encodeFileName 
	 * @Description: 将文件名转成UTF-8编码以防止乱码
	 * @param string
	 * @return    
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskQueryService#encodeFileName(java.lang.String)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-4-24下午4:14:37
	 */ 
	/**
	 * 生成导出文件名称
	 * @author huyue
	 * @date 2013-1-7 下午2:51:23
	 */
	public String encodeFileName(String fileName) throws TfrBusinessException {
		try {
			String returnStr;
			String agent = (String) ServletActionContext.getRequest().getHeader("USER-AGENT");
			if (agent != null && agent.indexOf("MSIE") == -1) {
				returnStr = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
			} else {
				returnStr = URLEncoder.encode(fileName, "UTF-8");
			}
			return returnStr;
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("转换文件名编码失败", e);
			throw new TfrBusinessException(StockException.EXPORT_FILE_ERROR_CODE, "");
		}
	}
	
	/** 
	 * @Title: exportLoadWayBillByTaskNo 
	 * @Description: 导出车辆出发到达明细
	 * @param taskNo
	 * @return    
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskQueryService#exportLoadWayBillByTaskNo(java.lang.String)
	 * @author: ibm-liubinbin
	 * @throws 
	 * Date:2013-4-24下午4:14:37
	 */ 
	@Override
	public InputStream exportTruckDepartOrArriveByTaskNo(String ids) {
		InputStream excelStream = null;
		QueryLeadTruckEntity queryEntity  =new QueryLeadTruckEntity();
		ids = ids.replaceAll(" ", "");
		queryEntity.setIds(Arrays.asList(ids.split(",")));
		List<LeadTruckEntity>  list = queryLeadTruckGrid(queryEntity,Integer.MAX_VALUE,Integer.MIN_VALUE);
		List<List<String>> rowList = new ArrayList<List<String>>();
		for(LeadTruckEntity entity : list){
			//每行的列List
			List<String> columnList = new ArrayList<String>();
			//询价日期
			columnList.add(getDateStr(entity.getInquiryTimeDate()));
			
			//出发外场
			columnList.add(entity.getOrigOrgName());
			
			//到达外场
			columnList.add(entity.getDestOrgName());
			
			//车长
			columnList.add(entity.getVehicleLengthName());
			
			//货箱结构
			if("FLAT".equals(entity.getContainerStructure()))
			{
				columnList.add("平板");
			}else if("HIGH_HURDLES".equals(entity.getContainerStructure())){
				columnList.add("高栏 ");
			}else if("CABINET".equals(entity.getContainerStructure())){
				columnList.add("柜式");
			}else
			{
				columnList.add("");
			}
			
			//运价
			BigDecimal fee = entity.getFee() == null ? BigDecimal.ZERO : entity.getFee();
			columnList.add(fee + "");
			
			//载重
			BigDecimal deadLoad = entity.getDeadLoad() == null ? BigDecimal.ZERO : entity.getDeadLoad();
			columnList.add(deadLoad + "");
			
			//净空
			BigDecimal selfVolumn = entity.getSelfVolumn() == null ? BigDecimal.ZERO : entity.getSelfVolumn();
			columnList.add(selfVolumn + "");
			
			//录入人
			columnList.add(entity.getCreateUserName());
			
			//录入时间
			columnList.add(getDateStr(entity.getCreateTime()));
			
			//修改时间
			columnList.add(getDateStr(entity.getUpdateTime()));
			
			rowList.add(columnList);
		}
		String[] rowHeads = { "询价日期", "出发外场", "到达外场", "车长", "货箱结构", "运价",
				"载重", "净空", "录入人", "录入时间", "修改时间"};// 定义表头

		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSheetName("外请车询价明细");
		exportSetting.setSize(ConstantsNumberSonar.SONAR_NUMBER_5000);
		ExportResource exportResource  = new ExportResource();
		exportResource.setHeads(rowHeads);
		exportResource.setRowList(rowList);
		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		excelStream = objExporterExecutor.exportSync(exportResource, exportSetting);
        return excelStream;
	}
	/** *****时间格式1********. */
	public static final String DATE_FORMAT_1 = "yyyy-MM-dd HH:mm:ss";
	/**
	 * date类型的转成String类型的.
	 *
	 * @param date the date
	 * @return the date str
	 * @author IBM-liubinbin
	 * @date 2012-11-8 下午3:04:20
	 */
	public static String getDateStr(Date date)
	{
		if (null != date)
		{
			SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_1);
			return format.format(date);
		}
		return "";
	}
	
	/*************外请车询价***************/
	private ILeadTruckDao leadTruckDao;

	public void setLeadTruckDao(ILeadTruckDao leadTruckDao)
	{
		this.leadTruckDao = leadTruckDao;
	}

	@Override
	public long getCount(QueryLeadTruckEntity queryEntity)
	{
		return leadTruckDao.getCount(queryEntity);
	}
	
}