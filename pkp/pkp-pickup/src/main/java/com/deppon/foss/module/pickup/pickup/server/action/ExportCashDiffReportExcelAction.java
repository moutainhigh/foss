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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pickup/server/action/ExportCashDiffReportExcelAction.java
 * 
 * FILE NAME        	: ExportCashDiffReportExcelAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pickup.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.pickup.pickup.api.server.service.ICashDiffReportService;
import com.deppon.foss.module.pickup.pickup.api.shared.vo.CashDiffReportVo;

/**
 * 
 * 接送货现金差异报表导出Action
 * @author admin
 * @date 2012-11-7 下午2:12:29
 */
public class ExportCashDiffReportExcelAction extends AbstractAction{
	
	/**
	 * logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ExportCashDiffReportExcelAction.class);
	
	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 这个输入流对应上面struts.xml中配置的那个excelStream，两者必须一致
	 */
	private InputStream excelStream;  
	/**
	 * 这个名称就是用来传给上面struts.xml中的${fileName}的
	 */
	private String fileName;
	/**
	 * 接送货现金差异报表-Vo
	 */
	private CashDiffReportVo vo = new CashDiffReportVo();
	/**
	 * 接送货现金差异报表-Service
	 */
	private ICashDiffReportService cashDiffReportService;
	
	/**
	 * Gets the 这个输入流对应上面struts.
	 *
	 * @return the 这个输入流对应上面struts
	 */
	public InputStream getExcelStream() {
		return excelStream;
	}
	
	/**
	 * Sets the 这个输入流对应上面struts.
	 *
	 * @param excelStream the new 这个输入流对应上面struts
	 */
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}
	
	/**
	 * Gets the 这个名称就是用来传给上面struts.
	 *
	 * @return the 这个名称就是用来传给上面struts
	 */
	public String getFileName() {
		return fileName;
	}
	
	/**
	 * Sets the 这个名称就是用来传给上面struts.
	 *
	 * @param fileName the new 这个名称就是用来传给上面struts
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}  
	
	/**
	 * Gets the 接送货现金差异报表-Vo.
	 *
	 * @return the 接送货现金差异报表-Vo
	 */
	public CashDiffReportVo getVo() {
		return vo;
	}
	
	/**
	 * Sets the 接送货现金差异报表-Vo.
	 *
	 * @param vo the new 接送货现金差异报表-Vo
	 */
	public void setVo(CashDiffReportVo vo) {
		this.vo = vo;
	}
	
	/**
	 * Sets the 接送货现金差异报表-Service.
	 *
	 * @param cashDiffReportService the new 接送货现金差异报表-Service
	 */
	public void setCashDiffReportService(
			ICashDiffReportService cashDiffReportService) {
		this.cashDiffReportService = cashDiffReportService;
	}
	/**
	 * 
	 * 导出接送货现金差异报表数据
	 * @date 2012-11-6 下午4:29:21
	 */
	public String exportExcel(){
		try {
			fileName = URLEncoder.encode("接送货现金差异报表", "UTF-8"); //设置fileName
		} catch (UnsupportedEncodingException se) {
			LOGGER.error("error", se);
		}
		excelStream = cashDiffReportService.queryCashDiffReportInfo(vo.getCashDiffReportDto());
		return returnSuccess();
	}
	

}