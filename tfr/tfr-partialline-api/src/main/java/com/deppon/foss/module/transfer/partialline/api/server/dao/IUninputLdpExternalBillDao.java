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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/partialline/api/server/dao/ILdpUninputPartiallineDao.java
 * 
 *  FILE NAME     :ILdpUninputPartiallineDao.java
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
package com.deppon.foss.module.transfer.partialline.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpHandoverBillDetailDto;

/**
 * 未录入落地配外发单Dao
 * 
 * @author ibm-liuzhaowei
 * @date 2013-07-16 上午9:18:36
 */
public interface IUninputLdpExternalBillDao {

	/**
	 * 查询未录入落地配外发单列表（查询交接类型为落地配，且未录入外发单的交接单明细）
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17  上午11:06:41
	 */
	List<LdpHandoverBillDetailDto> queryUninputLdpExternalBill(LdpHandoverBillDetailDto detailDto, int limit, int start);

	/**
	 * 查询未录入落地配外发单列表（查询交接类型为落地配，且未录入外发单的交接单明细）总条数
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17  上午11:07:24
	 */
	Long queryUninputLdpExternalBillCount(LdpHandoverBillDetailDto detailDto);

	/**
	 * 按交接时间降序查询(1.最新生成的 2.未产生交接单)交接单明细
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17  下午2:58:52
	 */
	LdpHandoverBillDetailDto queryLastHandoverBillDetail(LdpHandoverBillDetailDto detailDto);
}