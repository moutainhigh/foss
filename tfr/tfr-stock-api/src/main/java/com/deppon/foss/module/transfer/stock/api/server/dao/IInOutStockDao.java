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
 *  PROJECT NAME  : tfr-stock-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stock/api/server/dao/IInOutStockDao.java
 *  
 *  FILE NAME          :IInOutStockDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.stock.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockDetailDto;

/**
 * 本接口定义了对出入库动作信息的增加操作
 * @author 097457-foss-wangqiang
 * @date 2012-10-16 下午1:39:59
 */
public interface IInOutStockDao {
	/**
	 * 增加入库动作信息
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-12 下午4:05:56
	 */
	int addInStock(InOutStockEntity inOutStockEntity);
	/**
	 * 增加出库动作信息
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-12 下午4:06:01
	 */
	int addOutStock(InOutStockEntity inOutStockEntity);
	
	/**
	 * 查询运单出库记录
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-3 上午9:13:17
	 */
	List<String> queryOutStock(String waybillNO, Date createBillTime);
	
	/**
	 * 查询入库动作记录
	 * @author 097457-foss-wangqiang
	 * @date 2013-1-17 下午2:37:34
	 */
	List<InOutStockEntity> queryInStockInfo(String waybillNo, String serialNo, String orgCode, Date createBillTime);
	
	/**
	* @param waybillNo
	* @param serialNo
	* @param orgCode
	* @param createBillTime 
	* @return
	* @description 查询小于入库时间的入库动作记录
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-27 下午1:54:05
	*/
	List<InOutStockEntity> queryInStockInfoSmall(String waybillNo, String serialNo, String orgCode, Date createBillTime);
	
	/**
	 * 查询出库动作记录
	 * @param waybillNo
	 * @param serialNo
	 * @param orgCode
	 * @param createBillTime
	 * @return
	 * 
	 * @author foss-wuyingjie
	 */
	List<InOutStockEntity> queryOutStockInfo(String waybillNo, String serialNo,	String orgCode, Date createBillTime);
	/**
	 * 更改入库记录运单号
	 * @param oldWaybillNo 原运单号
	 * @param newWaybillNo 新运单号
	 * @param createBillTime 开单时间
	 * @param inOutStockType 出入库类型
	 * @author 097457-foss-wangqiang
	 * @date 2013-3-18 下午4:39:33
	 */
	void updateWaybillNoInStock(String oldWaybillNo, String newWaybillNo, Date createBillTime, String inOutStockType);
	/**
	 * 更改出库记录运单号
	 * @param oldWaybillNo 原运单号
	 * @param newWaybillNo 新运单号
	 * @param createBillTime 开单时间
	 * @param inOutStockType 出入库类型
	 * @author 097457-foss-wangqiang
	 * @date 2013-3-18 下午4:39:33
	 */
	void updateWaybillNoOutStock(String oldWaybillNo, String newWaybillNo, Date createBillTime, String inOutStockType);
	/**
	 * 查询货件出库信息
	 * @author 097457-foss-wangqiang
	 * @date 2013-4-8 上午10:48:46
	 */
	List<WaybillStockDetailDto>  queryGoodsOutStock(String waybillNo, String serialNo, Date createBillTime);
	/**
	 * @author niuly
	 * @date 2014-5-24上午8:41:54
	 * @function 根据运单号查询所有的入库部门编码
	 * @param waybillNo
	 * @return
	 */
	List<String> queryInDeptCodeByWaybillNo(String waybillNo);
	
}