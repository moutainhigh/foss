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
 *  PROJECT NAME  : tfr-management-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/server/service/IFocusSignBillService.java
 *  
 *  FILE NAME          :IFocusSignBillService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.management.api.server.service;

import java.io.InputStream;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.transfer.management.api.shared.domain.FocusSignBillEntity;
import com.deppon.foss.module.transfer.management.api.shared.domain.WaybillFeeDetailEntity;
import com.deppon.foss.module.transfer.management.api.shared.dto.FocusSignBillDto;
import com.deppon.foss.module.transfer.management.api.shared.dto.SignBillTotalDto;
/**
 * 集中接货签单service接口
 * @author 038300-foss-pengzhen
 * @date 2012-11-30 上午11:30:30
 */
public interface IFocusSignBillService extends IService {
	/**
	 * 分页获取集中接货签单信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-29 下午1:54:14
	 * @param dto 查询条件封装dto
	 */
	List<FocusSignBillDto> queryFocusSignBill(FocusSignBillDto dto, int start, int limit);
	/**
	 * 获取集中接货签单信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-29 下午1:54:14
	 */
	InputStream  queryExportFocusSignBill(FocusSignBillDto dto);
	/**
	 * 获取集中接货签单信息数量
	 * @author 038300-foss-pengzhen
	 * @date 2012-12-15 上午10:46:19
	 */
	Long queryFocusSignBillCount(FocusSignBillDto dto);
	/**
	 * 获取运单信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-30 上午10:10:32
	 * @param waybillNo  运单号
	 */
	WaybillEntity queryWayBill(String waybillNo);
	/**
	 * 保存集中接货签单信息及明细信息
	 * @author 038300-foss-pengzhen
	 * @param entity 集中接货签单信息
	 * @param addList 增加的明细列表
	 * @param updateList 更新的明细列表
	 * @param deleteIdList 删除的明细列表
	 * @param userInfo 当前用户信息
	 * @param operType 操作类型：新增or修改
	 * @date 2012-12-4 上午8:39:47
	 */
	void saveFocusSignBill(FocusSignBillEntity entity, 
			List<WaybillFeeDetailEntity> addList, 
			List<WaybillFeeDetailEntity> updateList, 
			List<String> deleteIdList,
			CurrentInfo userInfo,
			boolean operType);
	/**
	 * 根据签单号删除集中接货签单信息
	 * @param 签单号
	 * @author 038300-foss-pengzhen
	 * @date 2012-12-15 下午5:15:40
	 */
	void deleteFocusSignBill(String signBillNumber);
	/**
	 * 根据签单号查询集中接货签单信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-12-15 下午6:52:29
	 */
	FocusSignBillEntity queryFocusSignBillByNo(String signBillNumber);
	/**
	 * 根据签单号查询集中接货签单费用明细信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-12-15 下午6:54:31
	 */
	List<WaybillFeeDetailEntity> queryWaybillFeeDetailByNo(String signBillNumber);
	/**
	 * 查询集中接货签单合计信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-12-26 下午2:59:27
	 */
	SignBillTotalDto queryFocusSignBillTotal(FocusSignBillDto dto);
	/** 
	 * @Title: getOrgNameByCode 
	 * @Description: 根据部门code查name
	 * @param useTruckOrgCode
	 * @return    
	 * @return String    返回类型 
	 * getOrgNameByCode
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-3-25上午10:25:00
	 */ 
	String getOrgNameByCode(String useTruckOrgCode);
	
	/**
	 * 编辑时通过签单ID查询签单信息 
	 * @author Administrator
	 * @date 2013-10-18 上午11:02:43
	 * @param id
	 * @return
	 * @see
	 */
	FocusSignBillEntity queryFocusSignBillById(String id);
}