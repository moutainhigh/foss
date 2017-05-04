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
 *  
 *  
 *  
 *  
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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/server/action/RegularTruckSignBillAction.java
 *  
 *  FILE NAME          :RegularTruckSignBillAction.java
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
import com.deppon.foss.module.transfer.management.api.server.service.IRegularTruckSignBillService;
import com.deppon.foss.module.transfer.management.api.shared.domain.RegularTruckSignBillEntity;
import com.deppon.foss.module.transfer.management.api.shared.dto.RegularTruckSignBillDto;
import com.deppon.foss.module.transfer.management.api.shared.vo.RegularTruckSignBillVo;


/**
 * 专线对发签单Action
 * @author 097457-foss-liming
 * @date 2012-12-19 下午15:10:10
 */
public class RegularTruckSignBillAction  extends AbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7259453463550902884L;
	
	/** 专线对发签单VO*/
	private RegularTruckSignBillVo vo=new RegularTruckSignBillVo();
	
	/** 专线对发签单服务*/
	private IRegularTruckSignBillService regularTruckSignBillService;
	
	/** 日志*/
	private static final Logger logger=  LogManager.getLogger(RegularTruckSignBillAction.class);
	
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
	 * 分页查询专线对发签单
	 * @author 097457-foss-liming
	 * @date 2012-12-19 下午15:15:10
	 */
	public String queryRegularTruckSignBill(){
		try {
			//获取专线对发签单信息
			List<RegularTruckSignBillEntity> regularTruckSignBillList = regularTruckSignBillService.queryRegularTruckSignBill(vo.getRegularTruckSignBillDto(), start, limit);
			//放入专线对发签单信息
			vo.setRegularTruckSignBillList(regularTruckSignBillList);
			//得到专线对发签单信息总条数
			Long totalCount=	regularTruckSignBillService.queryCount(vo.getRegularTruckSignBillDto());
			//放入专线对发签单信息总条数
			this.setTotalCount(totalCount);
			
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 新增专线对发签单
	 * @author 097457-foss-liming
	 * @date 2012-12-19 下午15:20:23
	 */
	public String addRegularTruckSignBill(){
		try {
			//新增专线对发签单
			regularTruckSignBillService.addRegularTruckSignBill(vo.getRegularTruckSignBillEntity());
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	/**
	 * 修改专线对发签单
	 * @author 097457-foss-liming
	 * @date 2012-12-19 下午15:22:02
	 */
	public String updateRegularTruckSignBill(){
		try {
			//修改专线对发签单
			regularTruckSignBillService.updateRegularTruckSignBill(vo.getRegularTruckSignBillEntity());
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	/**
	 * 删除专线对发签单
	 * @author 097457-foss-liming
	 * @date 2012-12-19 下午15:34:09
	 */
	public String deleteRegularTruckSignBill(){
		try {
			//删除专线对发签单
			regularTruckSignBillService.deleteRegularTruckSignBill(vo.getRegularTruckSignBillEntity().getId());
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	/**
	 * 查询专线对发签单费用
	 * @author 097457-foss-liming
	 * @date 2012-12-19 下午15:15:10
	 */
	public String queryRegularTruckSignBillByFee(){
		try {
		// 查询专线对发签单费用
		RegularTruckSignBillDto 	regularTruckSignBillDto=regularTruckSignBillService.queryRegularTruckSignBillByFee(vo.getRegularTruckSignBillDto());
		//放入费用
		vo.setRegularTruckSignBillDto(regularTruckSignBillDto);
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 计算专线对发签单费用
	 * @author 097457-foss-liming
	 * @date 2012-12-19 下午15:15:10
	 */
	public String  calculateRegularTruckSignBillFee(){
		try {
			//计算专线对发签单费用
			RegularTruckSignBillEntity 	regularTruckSignBillEntity=regularTruckSignBillService.calculateRegularTruckSignBillFee(vo.getRegularTruckSignBillDto());
			//放入专线对发签单费用
			vo.setRegularTruckSignBillEntity(regularTruckSignBillEntity);
			} catch (BusinessException e) {
				return returnError(e);
			}
			return returnSuccess();
	}
	
	/**
	 *   导出excel
	 * 
	 * @author dp-liming
	 * @date 2012-12-25 下午14:32:47
	 */	
	public String queryExportRegularTruckSignBill(){
		try {	
			// 导出excel名字
			fileName=URLEncoder.encode("导出专线对发签单","UTF-8");
			// 导出excel信息
			excelStream=regularTruckSignBillService.queryExportRegularTruckSignBill(vo.getRegularTruckSignBillDto());		
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
	public String queryRegularTruckSignBillByVehicleNo(){
		try {
			//根据车牌号找车型
			RegularTruckSignBillEntity 	regularTruckSignBillEntity=regularTruckSignBillService.queryRegularTruckSignBillByVehicleNo(vo.getRegularTruckSignBillDto().getVehicleNo());
			//放入车型信息
			vo.setRegularTruckSignBillEntity(regularTruckSignBillEntity);
		} catch (BusinessException e) {
			logger.error(e);
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 获取 专线对发签单VO.
	 *
	 * @return the 专线对发签单VO
	 */
	public RegularTruckSignBillVo getVo() {
		return vo;
	}
	
	/**
	 * 设置 专线对发签单VO.
	 *
	 * @param vo the new 专线对发签单VO
	 */
	public void setVo(RegularTruckSignBillVo vo) {
		this.vo = vo;
	}
	
	/**
	 * 设置 专线对发签单服务.
	 *
	 * @param regularTruckSignBillService the new 专线对发签单服务
	 */
	public void setRegularTruckSignBillService(
			IRegularTruckSignBillService regularTruckSignBillService) {
		this.regularTruckSignBillService = regularTruckSignBillService;
	}
	
	
}