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
 *  PROJECT NAME  : tfr-package
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/packaging/server/dao/impl/PDAPackagingDao.java
 *  
 *  FILE NAME          :PDAPackagingDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-package
 * PACKAGE NAME: com.deppon.foss.module.transfer.packaging.server.dao.impl
 * FILE    NAME: PDAPackagingDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.packaging.server.dao.impl;

import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.packaging.api.server.dao.IPDAPackagingDao;
import com.deppon.foss.module.transfer.packaging.api.shared.define.PackagingConstants;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAPackagingInfoEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDASerialRelationEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.QueryPDAUnpackConEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.QueryPDAUnpackResEntity;
import com.deppon.foss.module.transfer.pda.api.shared.dto.SerialNoAreaDto;

/**
 * PDA包装录入DAO实现类
 * @author 046130-foss-xuduowei
 * @date 2012-11-3 上午9:35:12
 */
public class PDAPackagingDao extends iBatis3DaoImpl implements IPDAPackagingDao {

	/**
	 * 
	 * PDA端查询营业部代打包装货物
	 * @param queryPDAUnpackConEntity 查询条件
	 * @return 待包装货物i型哪些
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-3 上午9:27:13
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<QueryPDAUnpackResEntity> queryPDAUnpackResult(
			QueryPDAUnpackConEntity queryPDAUnpackConEntity) {
		
		return getSqlSession().selectList(
				PackagingConstants.PACKAGING_OUT_IBATIS_NAMESAPCE+"selectPDAUnpackArea",
				queryPDAUnpackConEntity);
	}
	/**
	 * 
	 * 根据查询运单号查询运单货件在当前部门的状态
	 * 
	 * @author 046130-foss-xuduowei
	 * @date 2013-1-9 下午4:59:07
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SerialNoAreaDto> querySerialArea(Map<String, Object> map) {
		
		return getSqlSession().selectList(
				PackagingConstants.PACKAGING_OUT_IBATIS_NAMESAPCE+"selectSerialArea", 
				map);
	}

	/**
	 * 
	 * PDA包装录入信息
	 * @param pdaPackagingInfoEntity 包装录入信息
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-3 上午9:28:39
	 */
	@Override
	public int addPackagingInfo(PDAPackagingInfoEntity pdaPackagingInfoEntity) {
		
		return 0;
		
	}
	
	/**
	 * 
	 * PDA保存新流水号
	 * @param pdaSerialRelationEntity 流水号关系实体
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-3 上午11:53:50
	 */
	@Override
	public int addSerialRealtion(PDASerialRelationEntity pdaSerialRelationEntity) {
		
		return 0;
		
	}
	
	/**
	 * 
	 * 根据运单号查询已包装信息
	 * @param waybillNo 运单号
	 * @return 符合条件的个数
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-3 下午1:45:10
	 */
	@Override
	public int queryPackByWaybillNo(String waybillNo) {
		
		return 0;
	}
	/**
	 * 
	 * 查询需要包装且未包装的流水号
	 * @author 046130-foss-xuduowei
	 * @date 2013-1-15 上午11:41:30
	 */
	@Override
	public int selectSerialUnpack(Map<String, Object> map) {
		
		return Integer.parseInt(
				getSqlSession().selectOne(
						PackagingConstants.PACKAGING_OUT_IBATIS_NAMESAPCE+"selectSerialUnpack",
						map).toString());
	}
	
	
	

}