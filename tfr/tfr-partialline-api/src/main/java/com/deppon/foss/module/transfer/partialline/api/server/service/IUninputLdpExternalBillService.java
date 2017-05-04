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
 *  PROJECT NAME  : tfr-partialline-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 落地配外发管理
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/partialline/api/server/service/ILdpUninputPartiallineService.java
 * 
 *  FILE NAME     :ILdpUninputPartiallineService.java
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
package com.deppon.foss.module.transfer.partialline.api.server.service;

import java.util.List;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpExternalBillDto;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpHandoverBillDetailDto;

public interface IUninputLdpExternalBillService {
	
	/**
	 * 生成落地配外发单
	 * 
	 * @author ibm-liuzhaowei
	 * @param waybillNoList 要生成落地配外发单的运单集合，批量生成
	 * @param currentInfo 用户信息
	 * @return 生成落地配外发失败的运单集合，批量生成外发单时某个运单失败不影响其他运单的生成
	 * @date 2013-07-17 上午11:46:12
	 * 
	 * @update-author foss-lln-269701
	 * @update-date 2015-10-22 下午15:42:58
	 * @update-return 一票多件时，生成落地配外发失败的运单和流水号集合，批量生成外发单时某个运单流水号失败不影响其他运单流水号的生成
	 */	
	//String addLdpExternalBill(List<String> waybillNoList, CurrentInfo currentInfo);
	 String addLdpExternalBill(List<LdpHandoverBillDetailDto> wayBillSerialNos, CurrentInfo currentInfo);
	/**
	 * 查询未录入落地配外发单列表（查询交接单明细）
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-16 上午9:18:36
	 */
	List<LdpHandoverBillDetailDto> queryUninputLdpExternalBill(LdpHandoverBillDetailDto detailDto, int limit, int start);

	/**
	 * 查询未录入落地配外发单列表（查询交接单明细）总条数
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17  上午11:00:11
	 */
	Long queryUninputLdpExternalBillCount(LdpHandoverBillDetailDto detailDto);

	/**
	 * 查询运单号是否存在未录入的交接单
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17  上午11:29:29
	 */
	void queryHandedUninputWaybill(LdpExternalBillDto dto);
	
	public List<String> queryTransCenterChildrenCodes(String currentDeptCode);
}