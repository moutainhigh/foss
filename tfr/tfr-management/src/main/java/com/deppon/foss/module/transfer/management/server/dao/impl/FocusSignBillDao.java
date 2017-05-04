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
 *  PROJECT NAME  : tfr-management
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/server/dao/impl/FocusSignBillDao.java
 *  
 *  FILE NAME          :FocusSignBillDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.management.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.management.api.server.dao.IFocusSignBillDao;
import com.deppon.foss.module.transfer.management.api.shared.domain.FocusSignBillEntity;
import com.deppon.foss.module.transfer.management.api.shared.domain.WaybillFeeDetailEntity;
import com.deppon.foss.module.transfer.management.api.shared.dto.FocusSignBillDto;
import com.deppon.foss.module.transfer.management.api.shared.dto.SignBillTotalDto;

public class FocusSignBillDao extends iBatis3DaoImpl implements	IFocusSignBillDao {
	/**
	 * 签单mybatis命名空间
	 */
	private static final String NAMESPACE = "foss.management.focusSignBill.";
	/**
	 * 签单明细mybatis命名空间
	 */
	private static final String NAMESPACE_DETAIL = "foss.management.waybillFeeDetail.";
	
	/**
	 * 分页获取集中接货签单信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-29 下午2:45:35
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IFocusSignBillDao#queryFocusSignBill(com.deppon.foss.module.transfer.management.api.shared.dto.FocusSignBillDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FocusSignBillDto> queryFocusSignBill(FocusSignBillDto dto, int start, int limit) {
		//设置分页
		RowBounds rowBounds = new RowBounds(start,limit);
		//返回结果
		return this.getSqlSession().selectList(NAMESPACE + "selectFocusSignBill", dto, rowBounds);
	}
	
	/**
	 * 获取集中接货签单信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-29 下午2:45:35
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IFocusSignBillDao#queryFocusSignBill(com.deppon.foss.module.transfer.management.api.shared.dto.FocusSignBillDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FocusSignBillDto> queryFocusSignBill(FocusSignBillDto dto) {
		//返回结果
		return this.getSqlSession().selectList(NAMESPACE + "selectFocusSignBill", dto);
	}
	
	/**
	 * 获取集中接货签单信息数量
	 * @author 038300-foss-pengzhen
	 * @date 2012-12-15 上午10:49:22
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IFocusSignBillDao#queryFocusSignBillCount(com.deppon.foss.module.transfer.management.api.shared.dto.FocusSignBillDto)
	 */
	@Override
	public Long queryFocusSignBillCount(FocusSignBillDto dto) {
		////返回结果
		return (Long)this.getSqlSession().selectOne(NAMESPACE + "selectFocusSignBillCount", dto);
	}
	/**
	 * 新增集中接货签单信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-12-15 下午6:59:05
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IFocusSignBillDao#addFocusSignBill(com.deppon.foss.module.transfer.management.api.shared.domain.FocusSignBillEntity)
	 */
	@Override
	public void addFocusSignBill(FocusSignBillEntity entity) {
		//新增集中接货签单信息
		this.getSqlSession().insert(NAMESPACE + "insertFocusSignBill", entity);
	}
	/**
	 * 新增集中接货签单明细信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-12-15 下午6:59:24
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IFocusSignBillDao#addWaybillFeeDetail(java.util.List)
	 */
	@Override
	public void addWaybillFeeDetail(List<WaybillFeeDetailEntity> addList) {
		//新增集中接货签单明细信息
		this.getSqlSession().insert(NAMESPACE_DETAIL + "insertWaybillFeeDetail", addList);
	}
	/**
	 * 更新集中接货签单信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-12-15 下午6:59:38
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IFocusSignBillDao#updateFocusSignBill(com.deppon.foss.module.transfer.management.api.shared.domain.FocusSignBillEntity)
	 */
	@Override
	public void updateFocusSignBill(FocusSignBillEntity entity) {
		//更新集中接货签单信息
		this.getSqlSession().update(NAMESPACE + "updateFocusSignBill", entity);
	}
	/**
	 * 更新集中接货签单明细信息 
	 * @author 038300-foss-pengzhen
	 * @date 2012-12-15 下午6:59:51
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IFocusSignBillDao#updateWaybillFeeDetail(java.util.List)
	 */
	@Override
	public void updateWaybillFeeDetail(List<WaybillFeeDetailEntity> updateList) {
		//更新集中接货签单明细信息 
		this.getSqlSession().delete(NAMESPACE_DETAIL + "updateWaybillFeeDetailList", updateList);
	}
	
	/**
	 * 根据签单号删除集中接货签单信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-12-15 下午7:00:03
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IFocusSignBillDao#deleteFocusSignBill(java.lang.String)
	 */
	@Override
	public void deleteFocusSignBill(String signBillNumber) {
		//根据签单号删除集中接货签单信息
		this.getSqlSession().delete(NAMESPACE + "deleteFocusSignBill", signBillNumber);
	}
	/**
	 * 删除集中接货指定签单号对应的所有明细信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-12-15 下午7:00:46
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IFocusSignBillDao#deleteWaybillFeeDetail(java.lang.String)
	 */
	@Override
	public void deleteWaybillFeeDetail(String signBillNumber) {
		//删除集中接货指定签单号对应的所有明细信息
		this.getSqlSession().delete(NAMESPACE_DETAIL + "deleteWaybillFeeDetail", signBillNumber);
	}
	
	/**
	 * 根据待删列表删除集中接货签单明细信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-12-15 下午7:00:55
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IFocusSignBillDao#deleteWaybillFeeDetail(java.util.List)
	 */
	@Override
	public void deleteWaybillFeeDetail(List<String> deleteIdList) {
		//根据待删列表删除集中接货签单明细信息
		this.getSqlSession().delete(NAMESPACE_DETAIL + "deleteWaybillFeeDetailList", deleteIdList);
	}
	
	/**
	 * 根据签单号查询集中接货签单信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-12-15 下午7:01:13
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IFocusSignBillDao#queryFocusSignBillByNo(java.lang.String)
	 */
	@Override
	public FocusSignBillEntity queryFocusSignBillByNo(String signBillNumber) {
		//根据签单号查询集中接货签单信息
		return (FocusSignBillEntity)this.getSqlSession().selectOne(NAMESPACE + "selectFocusSignBillByNo", signBillNumber);
	}
	/**
	 * 根据签单号查询集中接货签单费用明细信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-12-15 下午7:01:34
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IFocusSignBillDao#queryWaybillFeeDetailByNo(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillFeeDetailEntity> queryWaybillFeeDetailByNo(
			String signBillNumber) {
		//根据签单号查询集中接货签单费用明细信息
		return this.getSqlSession().selectList(NAMESPACE_DETAIL + "selectWaybillFeeDetailByNo", signBillNumber);
	}
	/**
	 * 查询集中接货签单合计信息 
	 * @author 038300-foss-pengzhen
	 * @date 2012-12-26 下午3:01:11
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IFocusSignBillDao#queryFocusSignBillTotal(com.deppon.foss.module.transfer.management.api.shared.dto.FocusSignBillDto)
	 */
	@Override
	public SignBillTotalDto queryFocusSignBillTotal(FocusSignBillDto dto) {
		//查询集中接货签单合计信息 
		return (SignBillTotalDto) this.getSqlSession().selectOne(NAMESPACE + "selectFocusSignBillTotal", dto);
	}

	/** 
	 * @Title: getOrgNameByCode 
	 * @Description: 根据部门code查name
	 * @param useTruckOrgCode
	 * @return    
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IFocusSignBillDao#getOrgNameByCode(java.lang.String)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-3-25上午10:26:19
	 */ 
	@Override
	public String getOrgNameByCode(String useTruckOrgCode) {
		//根据部门编码获取部门名称
		return (String) this.getSqlSession().selectOne(NAMESPACE + "getOrgNameByCode", useTruckOrgCode);
	}

	/**
	 * 编辑时通过签单ID查询签单信息 
	 * @author Administrator
	 * @date 2013-10-18 上午11:10:48
	 * @param id
	 * @return 
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IFocusSignBillDao#queryFocusSignBillById(java.lang.String)
	 */
	@Override
	public FocusSignBillEntity queryFocusSignBillById(String id) {
		//编辑时通过签单ID查询签单信息 
		return (FocusSignBillEntity)this.getSqlSession().selectOne(NAMESPACE + "queryFocusSignBillById", id);
	}

}