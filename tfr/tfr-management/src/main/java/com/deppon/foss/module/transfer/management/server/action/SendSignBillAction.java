/**
 *  initial comments.
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
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
 *  PROJECT NAME  : tfr-management
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/server/action/SendSignBillAction.java
 *  
 *  FILE NAME          :SendSignBillAction.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.management.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.transfer.management.api.server.service.ISendSignBillService;
import com.deppon.foss.module.transfer.management.api.shared.domain.SendSignBillEntity;
import com.deppon.foss.module.transfer.management.api.shared.dto.SendSignBillDto;
import com.deppon.foss.module.transfer.management.api.shared.vo.SendSignBillVo;

/**
 *   派送签单Action
 * 
 * @author dp-liming
 * @date 2012-11-29 上午10:32:47
 */
public class SendSignBillAction extends AbstractAction {
	/**uid*/
	private static final long serialVersionUID = 45663500286063128L;
	/**日志*/
	private static final Logger logger=  LogManager.getLogger(SendSignBillAction.class);
	/**派送签单VO*/
	private SendSignBillVo vo=new SendSignBillVo();
	/**派送签单服务*/
	private ISendSignBillService sendSignBillService;

	/** 导出Excel 文件流*/
	private InputStream excelStream;  
	/** 导出Excel 文件名*/
	private String fileName;  
	
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
	
	/**
	 *   查询派送签单
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:32:47
	 */
	public String querySendSignBill(){
		try {
			SendSignBillDto sendSignBillDto = vo.getSendSignBillDto();
			String beginDate = sendSignBillDto.getBeginSignBillDate();
			String endDate = sendSignBillDto.getEndSignBillDate();
			beginDate += " 00:00:00";
			endDate += " 23:59:59";
			sendSignBillDto.setBeginSignBillDate(beginDate);
			sendSignBillDto.setEndSignBillDate(endDate);
			//查询派送签单
			List<SendSignBillEntity> sendSignBillList = sendSignBillService.querySendSignBill(vo.getSendSignBillDto(), start, limit);
			//放入派送签单信息
			vo.setSendSignBillList(sendSignBillList);
			//放入总条数
			Long totalCount = sendSignBillService.queryCount(vo.getSendSignBillDto());
			this.setTotalCount(totalCount);
			
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	/**
	 *   新增派送签单
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:32:47
	 */
	public String addSendSignBill(){
		try {
			//新增派送签单
			sendSignBillService.addSendSignBill(vo.getSendSignBillEntity());
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 *   修改派送签单
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:32:47
	 */
	public String updateSendSignBill(){
		try {
			//修改派送签单
			sendSignBillService.updateSendSignBill(vo.getSendSignBillEntity());
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 *   删除派送签单
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:32:47
	 */
	public String deleteSendSignBill(){
		try {
			//删除派送签单
			sendSignBillService.deleteSendSignBill(vo.getSendSignBillEntity().getId());
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 *   查询派送签单费用
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:32:47
	 */
	public String querySendSignBillByFee(){
		try {
			SendSignBillDto params = vo.getSendSignBillDto();
			String beginDate = params.getBeginSignBillDate();
			String endDate = params.getEndSignBillDate();
			beginDate += " 00:00:00";
			endDate += " 23:59:59";
			params.setBeginSignBillDate(beginDate);
			params.setEndSignBillDate(endDate);
			
			//查询派送签单费用
			SendSignBillDto sendSignBillDto = sendSignBillService.querySendSignBillByFee(params);
			//放入费用信息
			vo.setSendSignBillDto(sendSignBillDto);
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 *   导出excel
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:32:47
	 */	
	public String queryExportSendSignBill(){
		try {
			//派送签单导出名字
			fileName=URLEncoder.encode("导出派送签单","UTF-8");
			//导出派送签单信息
			SendSignBillDto sendSignBillDto = vo.getSendSignBillDto();
			String beginDate = sendSignBillDto.getBeginSignBillDate();
			String endDate = sendSignBillDto.getEndSignBillDate();
			beginDate += " 00:00:00";
			endDate += " 23:59:59";
			sendSignBillDto.setBeginSignBillDate(beginDate);
			sendSignBillDto.setEndSignBillDate(endDate);
			excelStream=sendSignBillService.queryExportSendSignBill(vo.getSendSignBillDto());		
		} catch (BusinessException e) {
			logger.error(e);
			return returnError(e);
		}catch (UnsupportedEncodingException e) {
			logger.info(e);
			return returnError(e.getMessage());
		}
		return returnSuccess();
	}
	
	/**
	 *   根据车牌号找车型
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:32:47
	 */	
	public String querySendSignBillByVehicleNo(){
		try {
			//根据车牌号找车型
			SendSignBillEntity sendSignBillEntity=sendSignBillService.querySendSignBillByVehicleNo(vo.getSendSignBillDto().getVehicleNo());	
			//放入信息
			vo.setSendSignBillEntity(sendSignBillEntity);
		} catch (BusinessException e) {
			logger.error(e);
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 *   根据派送单号找派送签单信息
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:32:47
	 */	
	public String querySendSignBillBySignBillNo(){
		try {		
			//  根据派送单号找派送签单信息
			SendSignBillEntity sendSignBillEntity=sendSignBillService.queryDeliveryInfoByDeliverbillNo(vo.getSendSignBillDto().getSignBillNo());	
			//放入信息
			vo.setSendSignBillEntity(sendSignBillEntity);
		} catch (BusinessException e) {
			logger.error(e);
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 获取 派送签单VO.
	 *
	 * @return the 派送签单VO
	 */
	public SendSignBillVo getVo() {
		return vo;
	}

	/**
	 * 设置 派送签单VO.
	 *
	 * @param vo the new 派送签单VO
	 */
	public void setVo(SendSignBillVo vo) {
		this.vo = vo;
	}

	/**
	 * 设置 派送签单服务.
	 *
	 * @param sendSignBillService the new 派送签单服务
	 */
	public void setSendSignBillService(ISendSignBillService sendSignBillService) {
		this.sendSignBillService = sendSignBillService;
	}
	
	
}