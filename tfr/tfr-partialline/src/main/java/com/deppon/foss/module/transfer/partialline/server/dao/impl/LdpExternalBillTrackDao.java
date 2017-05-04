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
 *  PROJECT NAME  : tfr-partialline
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 落地配外发管理
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/partialline/server/dao/impl/LdpExternalBillTrackDao.java
 * 
 *  FILE NAME     :LdpExternalBillTrackDao.java
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
package com.deppon.foss.module.transfer.partialline.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.partialline.api.server.dao.ILdpExternalBillTrackDao;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpExternalBillTrackDto;
import com.deppon.foss.util.UUIDUtils;

/**
 * 落地配Dao
 * @author ibm-liuzhaowei
 * @date 2013-07-29 上午9:18:36
 */
public class LdpExternalBillTrackDao extends iBatis3DaoImpl implements ILdpExternalBillTrackDao {

	/**
	 * 前缀
	 */
	private static String prefix = "foss.partialline.ldpExternalBillTrackMapper.";

	/**
	 * 插入落地配外发单轨迹记录
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-29 上午11:52:20
	 */
	@Override
	public int addLdpExternalBillTrack(LdpExternalBillTrackDto record) {
		record.setId(UUIDUtils.getUUID());
		// 插入
		this.getSqlSession().insert(prefix + "addLdpExternalBillTrack", record);
		
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LdpExternalBillTrackDto> queryLdpExternalBillTrackList(LdpExternalBillTrackDto ldpExternalBillTrackDto) {
		
		return this.getSqlSession().selectList(prefix + "queryLdpExternalBillTrackList", ldpExternalBillTrackDto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LdpExternalBillTrackDto> queryLdpBillTrackList(LdpExternalBillTrackDto ldpDto) {
		return this.getSqlSession().selectList(prefix + "queryLdpBillTrackList", ldpDto);
	}

	@Override
	public void deleteLDPTrack(String waybillNo) {
		this.getSqlSession().delete(prefix + "deleteLDPTrack", waybillNo);
	}
	
}