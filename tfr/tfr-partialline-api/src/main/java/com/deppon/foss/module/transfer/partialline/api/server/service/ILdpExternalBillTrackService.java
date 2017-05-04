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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/partialline/api/server/service/ILdpExternalBillTrackService.java
 * 
 *  FILE NAME     :ILdpExternalBillTrackService.java
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

import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpExternalBillTrackDto;

/**
 * 落地配外发单轨迹service接口
 * @author ibm-liuzhaowei
 * @date 2013-07-29 上午9:18:36
 */
public interface ILdpExternalBillTrackService {

	/**
	 * 新增落地配外发单轨迹记录
	 * 
	 * @author ibm-liuzhaowei
	 * @param record 轨迹记录
	 * @date 2013-07-29 上午11:46:12
	 */
	int addLdpExternalBillTrack(LdpExternalBillTrackDto record);

	
	/**
	 * 
	 * 提供给综合查询查询落地配外发的轨迹
	 * 
	 * @param ldpExternalBillTrackDto
	 * @return
	 */
	List<LdpExternalBillTrackDto> queryLdpExternalBillTrackList(LdpExternalBillTrackDto ldpExternalBillTrackDto);

	/**
	 * @author nly
	 * @date 2015年4月13日 下午2:02:30
	 * @function 查询某一条轨迹
	 * @param ldpDto
	 * @return
	 */
	List<LdpExternalBillTrackDto> queryLdpBillTrackList(LdpExternalBillTrackDto ldpDto);

	/**
	 * @author nly
	 * @date 2015年4月15日 下午2:01:15
	 * @function 删除落地配轨迹
	 * @param waybillNo
	 */
	void deleteLDPTrack(String waybillNo);

}