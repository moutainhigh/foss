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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/server/action/TransferSignBillAction.java
 *  
 *  FILE NAME          :TransferSignBillAction.java
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
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.transfer.management.api.server.service.ITransferSignBillService;
import com.deppon.foss.module.transfer.management.api.shared.domain.TransferSignBillEntity;
import com.deppon.foss.module.transfer.management.api.shared.dto.TransferSignBillDto;
import com.deppon.foss.module.transfer.management.api.shared.vo.TransferSignBillVo;
/**
 * 转货车签单Action
 * @author foss-liming
 * @date 2012-11-30 上午9:51:06
 */
public class TransferSignBillAction extends AbstractAction {
	
	
	/**
	 * 日志
	 */
	private static final Logger logger = LogManager.getLogger(TransferSignBillAction.class);
	
	
	/**
	 * uid
	 */
	private static final long serialVersionUID = -5244907935723621054L;
	
	/**
	 * 转货车VO
	 */ 
	private TransferSignBillVo vo=new TransferSignBillVo();
	
	/**
	 * 转货车服务
	 */	
	private ITransferSignBillService transferSignBillService;

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
	 * 分页查询转货车签单信息
	 * @author foss-liming
	 * @date 2012-11-30 上午10:03:34
	 */
	@JSON
	public String queryTransferSignBill(){
		
		try {
			//分页获取转货车签单信息
			List<TransferSignBillEntity> transferSignBillList=	transferSignBillService.queryTransferSignBill(vo.getTransferSignBillDto(), start, limit);
			//放入转货车签单信息
			vo.setTransferSignBillList(transferSignBillList);
			//放入总条数
			this.setTotalCount(transferSignBillService.queryCount(vo.getTransferSignBillDto()));			
		} catch (BusinessException e) {
			logger.error(e);
			return returnError(e);
		}
		return this.returnSuccess();
	}
	
	/**
	 * 新增转货车签单信息
	 * @author foss-liming
	 * @date 2012-11-30 上午10:03:34
	 */
	@JSON
	public String addTransferSignBill(){
		try {
			// 新增转货车签单信息
			transferSignBillService.addTransferSignBill(vo.getTransferSignBillEntity());
		}catch (BusinessException e) {
			return returnError(e);
		}
		return this.returnSuccess();
	}
	
	/**
	 * 修改转货车签单信息
	 * @author foss-liming
	 * @date 2012-11-30 上午10:03:34
	 */
	@JSON
	public String updateTransferSignBill(){
		try {
			//修改转货车签单信息
			transferSignBillService.updateTransferSignBill(vo.getTransferSignBillEntity());
		}catch (BusinessException e) {
			logger.info(e);
			return returnError(e);
		}
		return this.returnSuccess();
	}
	
	/**
	 * 删除转货车签单信息
	 * @author foss-liming
	 * @date 2012-11-30 上午10:03:34
	 */
	public String deleteTransferSignBill(){
		try {
			//删除转货车签单信息
			transferSignBillService.deleteTransferSignBill(vo.getId());
		} catch (BusinessException e) {
			logger.info(e);
			return returnError(e);
		}
		return this.returnSuccess();
	}
	/**
	 * 删除转货车签单信息
	 * @author foss-liming
	 * @date 2012-11-30 上午10:03:34
	 */
	public String queryTransferSignBillByFee(){
		try {
			//删除转货车签单信息
			TransferSignBillDto transferSignBillDto= transferSignBillService.queryTransferSignBillByFee(vo.getTransferSignBillDto());
			vo.setTransferSignBillDto(transferSignBillDto);
		}catch (BusinessException e) {
			logger.info(e);
			return returnError(e);
		}
		return this.returnSuccess();
	}
	
	/**
	 * 导出excel
	 * @author foss-liming
	 * @date 2012-12-25  下午 14:03:34
	 */
	public String queryExportTransferSignBill(){
		try {
			// 导出excel名字
			fileName=URLEncoder.encode("导出转货车签单","UTF-8");
			// 导出转货车签单信息excel
			excelStream= transferSignBillService.queryExportTransferSignBill(vo.getTransferSignBillDto());			
		}catch (BusinessException e) {
			logger.info(e);
			return returnError(e);
		}catch (UnsupportedEncodingException e) {
			logger.info(e);
			return returnError(e.getMessage());
		}
		return this.returnSuccess();
	}
	
	/**
	 * 根据车牌号找车型
	 * @author foss-liming
	 * @date 2012-12-25  下午 14:03:34
	 */
	public String  queryTransferSignBillByVehicleNo(){
		
		try {
			// 根据车牌号找车型
			TransferSignBillEntity transferSignBillEntity=transferSignBillService.queryTransferSignBillByVehicleNo(vo.getTransferSignBillDto().getVehicleNo());
			//放值
			vo.setTransferSignBillEntity(transferSignBillEntity);		
		}catch (BusinessException e) {
			logger.info(e);
			return returnError(e);
		}
		return this.returnSuccess();
	}
	
	/**
	 * 获取 转货车VO.
	 *
	 * @return the 转货车VO
	 */
	public TransferSignBillVo getVo() {
		return vo;
	}

	/**
	 * 设置 转货车VO.
	 *
	 * @param vo the new 转货车VO
	 */
	public void setVo(TransferSignBillVo vo) {
		this.vo = vo;
	}
	
	/**
	 * 设置 转货车服务.
	 *
	 * @param transferSignBillService the new 转货车服务
	 */
	public void setTransferSignBillService(ITransferSignBillService transferSignBillService) {
		this.transferSignBillService = transferSignBillService;
	}

}