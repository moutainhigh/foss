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
 *  PROJECT NAME  : tfr-pda-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/pda/api/server/service/IPDAStockService.java
 *  
 *  FILE NAME          :IPDAStockService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.pda.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.common.api.shared.dto.BaseDataDictDto;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockPositionNumberEntity;

public interface IPDAStockService extends IService{
	
	/**
	 * PDA入库代包装货区
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @param inOutStockEntity.orgCode 部门编号
	 * @param inOutStockEntity.operatorCode 操作人工号       
	 * @param inOutStockEntity.scanTime 扫描时间
	 * @param inOutStockEntity.pdaDeviceNO PDA设备号   
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-8 上午8:51:18
	 */
	int inStockPackageAreaPDA(InOutStockEntity inOutStockEntity);
	/**
	 * PDA出库代包装货区
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @param inOutStockEntity.orgCode 部门编号
	 * @param inOutStockEntity.operatorCode 操作人工号       
	 * @param inOutStockEntity.scanTime 扫描时间
	 * @param inOutStockEntity.pdaDeviceNO PDA设备号   
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-8 上午8:55:59
	 */
	int outStockPackageAreaPDA(InOutStockEntity inOutStockEntity);
	/**
	 * PDA入库贵重物品货区
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @param inOutStockEntity.orgCode 部门编号
	 * @param inOutStockEntity.operatorCode 操作人工号       
	 * @param inOutStockEntity.scanTime 扫描时间
	 * @param inOutStockEntity.pdaDeviceNO PDA设备号   
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-8 上午8:56:43
	 */
	int inStockValuableAreaPDA(InOutStockEntity inOutStockEntity);
	/**
	 * PDA出库贵重物品货区
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @param inOutStockEntity.orgCode 部门编号
	 * @param inOutStockEntity.operatorCode 操作人工号       
	 * @param inOutStockEntity.scanTime 扫描时间
	 * @param inOutStockEntity.pdaDeviceNO PDA设备号   
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-8 上午8:57:31
	 */
	int outStockValuableAreaPDA(InOutStockEntity inOutStockEntity);
	/**
	 * PDA入库异常货区
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @param inOutStockEntity.orgCode 部门编号
	 * @param inOutStockEntity.operatorCode 操作人工号       
	 * @param inOutStockEntity.scanTime 扫描时间
	 * @param inOutStockEntity.pdaDeviceNO PDA设备号   
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-8 上午8:57:56
	 */
	int inStockExceptionAreaPDA(InOutStockEntity inOutStockEntity);
	/**
	 * PDA出库异常货区
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @param inOutStockEntity.orgCode 部门编号
	 * @param inOutStockEntity.operatorCode 操作人工号       
	 * @param inOutStockEntity.scanTime 扫描时间
	 * @param inOutStockEntity.pdaDeviceNO PDA设备号   
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-8 上午8:58:35
	 */
	int outStockExceptionAreaPDA(InOutStockEntity inOutStockEntity);
	/**
	 * PDA单件入库
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @param inOutStockEntity.inOutStockType 入库类型  参见常量类 com.deppon.foss.module.transfer.stock.api.define.StockConstants
	 * @param inOutStockEntity.orgCode 部门编号
	 * @param inOutStockEntity.operatorCode 操作人工号       
	 * @param inOutStockEntity.scanTime 扫描时间
	 * @param inOutStockEntity.pdaDeviceNO PDA设备号   
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-8 上午9:00:02
	 */
	int singleInStockPDA(InOutStockEntity inOutStockEntity);
	
	/**
	* @description PDA 对已在库存的货物进行库位确认
	* @param inOutStockEntity.waybillNO 运单号
	* @param inOutStockEntity.serialNO 流水号 
	* @param inOutStockEntity.position 库位
	* @param inOutStockEntity.orgCode 部门编号
	* @param inOutStockEntity.operatorCode 操作人工号
	* @param inOutStockEntity.operatorName 操作人名称
	* @param inOutStockEntity.scanTime 扫描时间
	* @param inOutStockEntity.pdaDeviceNO PDA设备号   
	* @return
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-17 下午4:15:53
	*/
	int updateStockStockPosition(InOutStockEntity inOutStockEntity);
	
	/**
	* @param orgCode 当前部门的编号
	* @return
	* @description 对应外场的驻地派送部货区
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-17 下午5:19:11
	*/
	List<BaseDataDictDto> areaByOrgcodeList(String orgCode);
	
	/**
	* @param orgCode 当前部门的编号
	* @param goodsAreaCode 驻地派送部货区的code
	* @return
	* @description 
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-17 下午5:21:46
	*/
	List<BaseDataDictDto> queryPositionList(String orgCode,String goodsAreaCode);
	
	/**
	* @param waybillNo 运单号
	* @param orgCode 部门编号
	* @param stockPositionNumber  定位编号
	* @return 根据运单号、部门编号来查询库存表
	* @description 
	* @version 1.0
	* @author 200968-foss-zwd
	* @update 2014-12-18 下午5:21:46
	*/
	List<StockPositionNumberEntity> queryStockPositionNumber(String waybillNo,String orgCode);
	
	/**
	* @param waybillNo 运单号
	* @param serialNo  流水号
	* @param orgCode 部门编号
	* @param stockPositionNumber  定位编号
	* @return 根据运单号、流水号、部门编号来存储定位编号到库存表中
	* @description 
	* @version 1.0
	* @author 200968-foss-zwd
	* @update 2014-12-19 上午8:19:46
	*/
	void saveStockPositionNumber(List<StockPositionNumberEntity> stockPositionNumberEntityList);


}