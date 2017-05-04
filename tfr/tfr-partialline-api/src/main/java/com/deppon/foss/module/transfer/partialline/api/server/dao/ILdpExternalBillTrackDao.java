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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/partialline/api/server/dao/ILdpExternalBillTrackDao.java
 * 
 *  FILE NAME     :ILdpExternalBillTrackDao.java
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

import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpExternalBillTrackDto;

/**
 * 落地配外发单轨迹Dao接口
 * @author ibm-liuzhaowei
 * @date 2013-07-29 上午9:18:36
 */
public interface ILdpExternalBillTrackDao {

	/**
	 * 插入新的外发单轨迹纪录
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-29  上午9:24:53
	 */
	int addLdpExternalBillTrack(LdpExternalBillTrackDto record);

	List<LdpExternalBillTrackDto> queryLdpExternalBillTrackList(LdpExternalBillTrackDto ldpExternalBillTrackDto);

	List<LdpExternalBillTrackDto> queryLdpBillTrackList(LdpExternalBillTrackDto ldpDto);

	void deleteLDPTrack(String waybillNo);

}