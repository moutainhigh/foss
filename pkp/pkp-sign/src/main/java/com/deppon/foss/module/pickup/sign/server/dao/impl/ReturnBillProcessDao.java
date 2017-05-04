/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-sign
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/dao/impl/ReturnBillProcessDao.java
 * 
 * FILE NAME        	: ReturnBillProcessDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.server.dao.impl;


import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;

import com.deppon.foss.module.pickup.sign.api.server.dao.IReturnBillProcessDao;
import com.deppon.foss.module.pickup.sign.api.shared.domain.ReturnBillProcessEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RtSearchReturnBillProcessDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.SearchReturnBillProcessDto;

/**
 * 查询 - 签收单返单
 * @date 2012-11-20 下午6:16:18
 */
@SuppressWarnings("unchecked")
public class ReturnBillProcessDao extends iBatis3DaoImpl 
	implements IReturnBillProcessDao{

	
	// 修改会员功能模块命名空间
	private static final String RETURNBILLPROCESSNAMESPACE = 
			"com.deppon.foss.module.pickup.sign.api.shared.domain.ReturnBillProcessEntity.";
	
	// 查询签收单返单数据集合
	private static final String SEARCHRETURNBILLPROCESSRFC = "searchReturnBillProcessList";
	
	// 查询签收单返单数据集合总数
	private static final String RETURNBILLPROCESSCOUNT = "getTotalCount";
	
	// 根据ID查询
	private static final String SELECTBYPRIMARYKEY = "selectByPrimaryKey";
	
	//更新 签收单返单
	private static final String UPDATERETURNBILLPROCESS = "updateReturnBillProcess";
	
	//根据运单号查询签收单信息
	private static final String  QUERYSIGNEDBILLBYWAYBILLNO = "querySignedBillByWaybillNo";
	/**
	 * 查询签收单返单纪录
	 * @date 2012-11-21 下午3:57:48
	 * @param dto
	 * @param start
	 * @param limit
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IReturnBillProcessDao#searchReturnBillProcessList(com.deppon.foss.module.pickup.sign.api.shared.dto.SearchReturnBillProcessDto, int, int)
	 */
	public List<RtSearchReturnBillProcessDto> searchReturnBillProcessList(
			SearchReturnBillProcessDto dto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return  (List<RtSearchReturnBillProcessDto> ) getSqlSession().selectList(RETURNBILLPROCESSNAMESPACE + 
				SEARCHRETURNBILLPROCESSRFC, dto,rowBounds);
	}

	/**
	 * 查询签收单返单记录总数
	 * @date 2012-11-21 下午3:58:10
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IReturnBillProcessDao#getReturnBillProcessCount(com.deppon.foss.module.pickup.sign.api.shared.dto.SearchReturnBillProcessDto)
	 */
	public Long getReturnBillProcessCount(SearchReturnBillProcessDto dto) {
		return (Long)this.getSqlSession().selectOne(RETURNBILLPROCESSNAMESPACE 
				+ RETURNBILLPROCESSCOUNT,dto);
	}

	/**
	 * 根据id查询ReturnBillProcess
	 * @date 2012-11-22 下午1:58:45
	 */
	public RtSearchReturnBillProcessDto searchReturnBillProcessById(
			SearchReturnBillProcessDto dto) {
		return (RtSearchReturnBillProcessDto)this.getSqlSession().selectOne(
				RETURNBILLPROCESSNAMESPACE+SELECTBYPRIMARYKEY,dto.getIds());
		
	}

	/**
	 * 更新ReturnBillProcess
	 * @date 2012-11-22 下午1:58:45
	 */
	public void updateReturnBillProcess(
			ReturnBillProcessEntity returnBillProcessEntity) {
		this.getSqlSession().update(RETURNBILLPROCESSNAMESPACE+UPDATERETURNBILLPROCESS,
				returnBillProcessEntity);
		
	}

	/**
	 * 根据运单号查询签收单信息
	 * 运单号： 运单单号
	 * @date 2012-11-22 下午1:58:45
	 */
	public List<ReturnBillProcessEntity> querySignedBillByWaybillNo(
			String waybillNo) {
		return  (List<ReturnBillProcessEntity>) getSqlSession().selectList(RETURNBILLPROCESSNAMESPACE + 
				QUERYSIGNEDBILLBYWAYBILLNO, waybillNo);
	}
	
	/**
	 * 
	 * 导出Excel表格
	 * @author 038590-foss-wanghui
	 * @date 2013-6-27 下午6:03:12
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IReturnBillProcessDao#searchReturnBillProcessList(com.deppon.foss.module.pickup.sign.api.shared.dto.SearchReturnBillProcessDto)
	 */
	@Override
	public List<RtSearchReturnBillProcessDto> searchReturnBillProcessList(SearchReturnBillProcessDto dto) {
		return  (List<RtSearchReturnBillProcessDto> ) getSqlSession().selectList(RETURNBILLPROCESSNAMESPACE + 
				SEARCHRETURNBILLPROCESSRFC, dto);
	}

	/**
	 * 批量修改返单处理表
	 * @author 268377
	 * @date
	 */
	@Override
	public int updateBatchRetrunBillProcess(RtSearchReturnBillProcessDto rtSearchReturnBillProcessDto) {
		return getSqlSession().update(RETURNBILLPROCESSNAMESPACE+"updateBatchRetrunBillProcess",rtSearchReturnBillProcessDto);
	}

}