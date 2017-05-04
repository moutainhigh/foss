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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/partialline/api/server/dao/ILdpExternalBillDao.java
 * 
 *  FILE NAME     :ILdpExternalBillDao.java
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

import com.deppon.foss.module.transfer.partialline.api.shared.domain.DpjzSignInDetialBillEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.DpjzSignInDetailDto;

/**
 * 家装送装明细及签收确认信息
 * @author foss-lln--269701
 * @date 2015-12-05 上午9:18:36
 */
public interface IDpjzSignInMsgDao {

	/**
	 * 插入新的纪录
	 * 
	 * @author foss-lln-269701
	 * @date 2015-12-05  上午9:04:53
	 */
	int insert(DpjzSignInDetialBillEntity record);

	/**
	 * 校验DOP推送数据是否存在
	 * @author foss-lln-269701
	 * @date 2015-12-05  上午9:04:53
	 */
	List<DpjzSignInDetailDto> queryWayBillNoIsExist(DpjzSignInDetialBillEntity record);
	/**
	 * 
	 * 更新接受到数据状态
	 * @author foss-lln-269701
	 * @date 2015-12-05  上午9:04:53
	 */
	int updateDpjzSignInStatus(String id,String active);
	/**
	 * 查询家装送装明细及签收确认信息
	 * 
	 * @author foss-lln-269701
	 * @date 2015-12-05  上午9:03:45
	 */
	List<DpjzSignInDetailDto> querydpjzSignInDetailBills(DpjzSignInDetailDto detailDto, int limit, int start);

	/**
	 * 获取总条数
	 * 
	 * @author foss-lln-269701
	 * @date 2015-12-05  下午2:31:28
	 */
	Long querydpjzSignInDetailBillsCount(DpjzSignInDetailDto dto);

	/**
	 * 核对 德邦家装送装信息以及签收确认
	 * 修改状态--同意or不同意
	 * @author foss-lln--269701
	 * @date 2015-12-05  上午11:00:11
	 */
	int dpjzSignInDetialCheck(DpjzSignInDetialBillEntity entity);
	/**
	 * 通过job自动更新核对状态和意见
	 * @author foss-lln--269701
	 * @date 2015-12-15  上午11:00:11
	 */
	public int updateDpjzSignInMsgWaybill(DpjzSignInDetailDto entity);
	/**
	 * 收到DOP推送的信息后，给收货部门发送在线通知;
	 * 根据运单号查询运单表 获取收货部门信息
	 * @author foss-lln--269701
	 * @date 2016-02-24  上午11:00:11
	 */
	public List<DpjzSignInDetailDto> queryDpjzReceiveMsgWaybill(String waybillNo);
}