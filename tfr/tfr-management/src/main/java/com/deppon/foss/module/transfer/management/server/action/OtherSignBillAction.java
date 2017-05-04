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
 *  PROJECT NAME  : tfr-management
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/server/action/OtherSignBillAction.java
 *  
 *  FILE NAME          :OtherSignBillAction.java
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
import com.deppon.foss.module.transfer.management.api.server.service.IOtherSignBillService;
import com.deppon.foss.module.transfer.management.api.shared.domain.OtherSignBillEntity;
import com.deppon.foss.module.transfer.management.api.shared.dto.OtherSignBillDto;
import com.deppon.foss.module.transfer.management.api.shared.vo.OtherSignBillVo;

/**
 * 其他签单Action
 * @author 097457-foss-liming
 * @date 2012-12-12 上午10:10:00
 */
public class OtherSignBillAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5928440781343693243L;
	/** 其他签单vo*/
	private OtherSignBillVo vo=new OtherSignBillVo();
	/** 其他签单服务*/
	private IOtherSignBillService otherSignBillService;
	
	/** 导出Excel 文件流*/
	private static final Logger logger=  LogManager.getLogger(OtherSignBillAction.class);
	
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
	 * 查询其他签单
	 * @author 097457-foss-liming
	 * @date 2012-12-12 上午10:12:00
	 */
	public String  queryOtherSignBill(){
		try {
			//分页查询其他签单信息
			List<OtherSignBillEntity> otherSignBillList=otherSignBillService.queryOtherSignBill(vo.getOtherSignBillDto(), start, limit);
			//放入其他签单信息
			vo.setOtherSignBillList(otherSignBillList);
			//放入总条数
			this.setTotalCount(otherSignBillService.queryCount(vo.getOtherSignBillDto()));
		} catch (BusinessException e) {
			return returnError(e);
		}		
		return this.returnSuccess();
	}
	/**
	 * 新增其他签单
	 * @author 097457-foss-liming
	 * @date 2012-12-12 上午10:12:00
	 */
	public String  addOtherSignBill(){
		try {
			//新增其他签单
			otherSignBillService.addOtherSignBill(vo.getOtherSignBillEntity());
		}catch (BusinessException e) {
			return returnError(e);
		}		
		return this.returnSuccess();
	}
	/**
	 * 修改其他签单
	 * @author 097457-foss-liming
	 * @date 2012-12-12 上午11:05:02
	 */
	public String  updateOtherSignBill(){
		try {
			//修改其他签单
			otherSignBillService.updateOtherSignBill(vo.getOtherSignBillEntity());
		} catch (BusinessException e) {
			return returnError(e);
		}		
		return this.returnSuccess();
	}
	
	/**
	 * 删除其他签单
	 * @author 097457-foss-liming
	 * @date 2012-12-12 上午11:15:30
	 */
	public String  deleteOtherSignBill(){
		try {
			//删除其他签单
			otherSignBillService.deleteOtherSignBill(vo.getId());
		} catch (BusinessException e) {
			return returnError(e);
		}		
		return this.returnSuccess();
	}
	/**
	 * 统计其他签单费用
	 * @author 097457-foss-liming
	 * @date 2012-12-12 上午11:15:30
	 */
	public String  queryOtherSignBillByFee(){
		try {
			// 统计其他签单费用
			OtherSignBillDto otherSignBillDto=otherSignBillService.queryOtherSignBillByFee(vo.getOtherSignBillDto());
			//放入费用信息
			vo.setOtherSignBillDto(otherSignBillDto);
		} catch (BusinessException e) {
			return returnError(e);
		}		
		return this.returnSuccess();
	}
	
	
	/**
	 *   导出excel
	 * 
	 * @author dp-liming
	 * @date 2012-12-25 下午14:32:47
	 */	
	public String queryExportOtherSignBill(){
		try {
			//导出excel名字
			fileName=URLEncoder.encode("导出其他签单","UTF-8");
			//导出excel信息
			excelStream=otherSignBillService.queryExportOtherSignBill(vo.getOtherSignBillDto());		
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
	 * @date 2012-12-25 下午14:43:08
	 */	
	public String queryOtherSignBillByVehicleNo(){
		try {
			// 根据车牌号找车型
			OtherSignBillEntity 	otherSignBillEntity=otherSignBillService.queryOtherSignBillByVehicleNo(vo.getOtherSignBillDto().getVehicleNo());
			// 放入车型
			vo.setOtherSignBillEntity(otherSignBillEntity);
		} catch (BusinessException e) {
			logger.error(e);
			return returnError(e);
		}
		return returnSuccess();
	}
	
	
	/**
	 * 获取 其他签单vo.
	 *
	 * @return the 其他签单vo
	 */
	public OtherSignBillVo getVo() {
		return vo;
	}

	/**
	 * 设置 其他签单vo.
	 *
	 * @param vo the new 其他签单vo
	 */
	public void setVo(OtherSignBillVo vo) {
		this.vo = vo;
	}

	/**
	 * 设置 其他签单服务.
	 *
	 * @param otherSignBillService the new 其他签单服务
	 */
	public void setOtherSignBillService(IOtherSignBillService otherSignBillService) {
		this.otherSignBillService = otherSignBillService;
	}
}