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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/partialline/server/dao/impl/LdpAgencySystemReportQueryDao.java
 * 
 *  FILE NAME     :LdpAgencySystemReportQueryDao.java
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

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.partialline.api.server.dao.ILdpAgencySystemReportQueryDao;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpAgencySystemReportQueryDto;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpAgencySystemReportResultDto;

/**
 * 偏线全盘报表Dao接口实现
 * 
 * @author ibm-liuzhaowei
 * @date 2013-07-30 下午4:56:16
 */
public class LdpAgencySystemReportQueryDao extends iBatis3DaoImpl implements ILdpAgencySystemReportQueryDao {

	/**
	 * 命名空间路径
	 */
	public static final String NAMESPACES = "foss.partialline.LdpAgencySystemReportMapper.";

	
	/** 
	 * 根据Dto查询落地配全盘报表
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-30 下午4:56:16
	 * @return  List<LdpAgencySystemReportResultDto>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LdpAgencySystemReportResultDto> queryLdpAgencySystemReportByDto(int offset, int start,
			LdpAgencySystemReportQueryDto dto) {
		//设置分页大小及起始页
		RowBounds rb = new RowBounds(offset, start);	
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACES + "queryLdpAgencySystemReportByDto", dto, rb);
	}

	/** 
	 * 根据Dto查询总记录条数
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-30 下午4:56:16
	 * @return LdpAgencySystemReportResultDto
	 */
	@Override
	public Long queryTotalRecordsInDBByDto(LdpAgencySystemReportQueryDto dto) {
		return (Long) this.getSqlSession().selectOne(NAMESPACES + "queryTotalRecordsInDBByDto", dto);
	}
}
