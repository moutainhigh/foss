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
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/dao/impl/WaybillRfcVarificationDao.java
 * 
 * FILE NAME        	: WaybillRfcVarificationDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAccountEntity;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRfcVarificationDao;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcActionHistoryEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcAgentEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcAgentPersonEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcChangeDetailEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcCondition;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcProofEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcChangeDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcPrintDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcQueryAgentConDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * <p>
 * 更改单审核受理dao实现<br />
 * </p>
 * 
 * @title WaybillRfcChangeDao.java
 * @package com.deppon.foss.module.pickup.waybill.server.dao.impl
 * @author suyujun
 * @version 0.1 2012-11-27
 */
public class WaybillRfcVarificationDao extends iBatis3DaoImpl implements
		IWaybillRfcVarificationDao {
	private static final String NAMESPACE = "com.deppon.foss.pkp.waybillRfcVarificationMapper.";
	private static final String NAMESPACE_ACTIONHISTORY = "foss.pkp.WaybillRfcActionHistoryEntityMapper.";
	private static final String QUERY_WAYBILLRFC_DTO = "queryWaybillRfcVarifyDto";
	private static final String QUERY_WAYBILLRFC_PROOF_LIST = "queryWayBillRfcProofByRfcId";
	private static final String INSERT_WAYBILLRFC_ACTIONHISTORY = "insert";
	private static final String QUERY_WAYBILLRFC_ChangeDetail_LIST = "queryWayBillRfcChangeDetailByRfcId";
	private static final String QUERY_WAYBILLRFC_CHKANDPROCESS_LIST = "queryWaybillRfcChkAndPro";
	/**
	 * 
	 * <p>
	 * 依据查询条件进行更改单信息查询<br />
	 * </p>
	 * 
	 * @author suyujun
	 * @version 0.1 2012-11-27
	 * @param condition
	 *            封装查询条件
	 * @return WaybillRfcChangeDto 更改单展示DTO
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillRfcChangeDto> queryWaybillRfcVarifyDto(
			WaybillRfcCondition condition) {
		List<WaybillRfcChangeDto> list = new ArrayList<WaybillRfcChangeDto>();
		//审核WaybillRfcCondition
		if(WaybillRfcConstants.WAYBILL_RFC_CHECK.equals(condition.getDealType())){
			list = this.getSqlSession().selectList(NAMESPACE + "queryWaybillRfcCheckDto", condition);
		}else{
		//处理
			list = this.getSqlSession().selectList(
					NAMESPACE + QUERY_WAYBILLRFC_DTO, condition);
		}
		return list;
	}

	/**
	 * 
	 * <p>
	 * 依据更改单ID查询凭证<br />
	 * </p>
	 * 
	 * @author suyujun
	 * @version 0.1 2012-11-28
	 * @param waybillRfcId
	 * @return List<WaybillRfcProofEntity>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillRfcProofEntity> queryWayBillRfcProofByRfcId(
			String waybillRfcId) {
		return this.getSqlSession().selectList(
				NAMESPACE + QUERY_WAYBILLRFC_PROOF_LIST, waybillRfcId);
	}

	/**
	 * 
	 * <p>
	 * 新增更改单操作记录<br />
	 * </p>
	 * @author suyujun
	 * @version 0.1 2012-11-28
	 * @param actionHistory
	 * void
	 */
	@Override
	public void addWaybillRfcActionHistory(
			WaybillRfcActionHistoryEntity actionHistory) {
		actionHistory.setId(UUIDUtils.getUUID());
		this.getSqlSession().insert(
				NAMESPACE_ACTIONHISTORY + INSERT_WAYBILLRFC_ACTIONHISTORY, actionHistory);
	}

	/**
	 * 
	 * <p>根据变更单号查询变更明细</p> 
	 * @author foss-gengzhe
	 * @date 2012-12-3 下午3:23:47
	 * @param waybillRfcId
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRfcVarificationDao#queryWayBillRfcChangeDetailByRfcId(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillRfcChangeDetailEntity> queryWayBillRfcChangeDetailByRfcId(
		String waybillRfcId) {
	    return this.getSqlSession().selectList(
			NAMESPACE + QUERY_WAYBILLRFC_ChangeDetail_LIST, waybillRfcId);
	}

	/**
	 * 审核和受理状态查询
	 * @author 043260-foss-suyujun
	 * @date 2012-12-7
	 * @param condition
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRfcVarificationDao#queryWaybillRfcChkAndPro(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcCondition)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillRfcChangeDto> queryWaybillRfcChkAndPro(
			WaybillRfcCondition condition) {
		return this.getSqlSession().selectList(NAMESPACE+ QUERY_WAYBILLRFC_CHKANDPROCESS_LIST, condition);
	}
	
	/**
	 * 查询打印信息
	 * @author 043260-foss-suyujun
	 * @date 2012-12-10
	 * @param waybillNo
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRfcVarificationDao#queryWaybillRfcPrintDto(java.lang.String)
	 */
	public WaybillRfcPrintDto queryWaybillRfcPrintDto(String waybillNo) {
		return (WaybillRfcPrintDto) this.getSqlSession().selectOne(NAMESPACE + "queryWaybillRfcPrintDto", waybillNo);
	}
	
	
	/**
	 * 根据更改单ID 查询更改单打印信息
	 * @author 097972-foss-dengtingting
	 * @date 2013-4-9 下午4:02:12
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRfcVarificationDao#queryWaybillRfcPrintDtoByRfcid(java.lang.String)
	 */
	@Override
	public WaybillRfcPrintDto queryWaybillRfcPrintDtoByRfcid(String rfcId) {
		return (WaybillRfcPrintDto) this.getSqlSession().selectOne(NAMESPACE + "queryWaybillRfcPrintDtoByRfcid", rfcId);
	}

	/**
	 * 新增更改代理主记录
	 * @author 043260-foss-suyujun
	 * @date 2012-12-24
	 * @param entity
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRfcVarificationDao#addWayBillRfcAgent(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcAgentEntity)
	 */
	@Override
	public WaybillRfcAgentEntity addWayBillRfcAgent(WaybillRfcAgentEntity entity) {
		this.getSqlSession().insert(NAMESPACE + "addWayBillRfcAgent", entity);
		return entity;
	}

	/**
	 * 新增更改代理人员信息
	 * @author 043260-foss-suyujun
	 * @date 2012-12-24
	 * @param agent
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRfcVarificationDao#addWayBillRfcAgentPerson(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcAgentPersonEntity)
	 */
	@Override
	public void addWayBillRfcAgentPerson(WaybillRfcAgentPersonEntity agent) {
		this.getSqlSession().insert(NAMESPACE + "WaybillRfcAgentPersonEntity",agent);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillRfcAgentEntity> queryWaybillRfcAgent(
			WaybillRfcQueryAgentConDto condition) {
		return this.getSqlSession().selectList(NAMESPACE + "queryWaybillRfcAgent", condition);
	}

	/** 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-26
	 * @param id
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRfcVarificationDao#queryWaybillRfcAgent(java.lang.String)
	 */
	@Override
	public WaybillRfcAgentEntity queryWaybillRfcAgentById(String id) {
		return (WaybillRfcAgentEntity) this.getSqlSession().selectOne(NAMESPACE + "queryWaybillRfcAgentById", id);
	}

	/**
	 * 删除代理人实体 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-26
	 * @param agentPerson
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRfcVarificationDao#deleteWaybillRfcAgentPerson(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcAgentPersonEntity)
	 */
	@Override
	public void deleteWaybillRfcAgentPerson(WaybillRfcAgentPersonEntity agentPerson) {
		this.getSqlSession().delete(NAMESPACE + "deleteWaybillRfcAgentPerson", agentPerson);
	}

	/** 
	 * 删除代理实体
	 * @author 043260-foss-suyujun
	 * @date 2012-12-26
	 * @param originalEntity
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRfcVarificationDao#deleteWaybillRfcAgent(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcAgentEntity)
	 */
	@Override
	public void deleteWaybillRfcAgent(WaybillRfcAgentEntity originalEntity) {
		this.getSqlSession().delete(NAMESPACE + "deleteWaybillRfcAgent", originalEntity);
	}

	/** 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-26
	 * @param agentPerson
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRfcVarificationDao#updateWaybillRfcAgentPerson(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcAgentPersonEntity)
	 */
	@Override
	public void updateWaybillRfcAgentPerson(
			WaybillRfcAgentPersonEntity agentPerson) {
		this.getSqlSession().update(NAMESPACE + "updateWaybillRfcAgentPerson", agentPerson);
	}

	/** 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-26
	 * @param originalEntity
	 * @@see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRfcVarificationDao#updateWaybillRfcAgent(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcAgentEntity)
	 */
	@Override
	public void updateWaybillRfcAgent(WaybillRfcAgentEntity originalEntity) {
		this.getSqlSession().update(NAMESPACE + "updateWaybillRfcAgent", originalEntity);
	}

	/** 
	 * 根据当前登录人查询权限列表
	 * @author 043260-foss-suyujun
	 * @date 2012-12-28
	 * @param conditionDto
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRfcVarificationDao#queryAuthorityListByAgentCode(com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcQueryAgentConDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillRfcAgentEntity> queryAuthorityListByAgentCode(
			WaybillRfcQueryAgentConDto conditionDto) {
		return this.getSqlSession().selectList(NAMESPACE + "queryAuthorityListByAgentCode", conditionDto);
	}

	/**
	 * 
	 * 查询判断代理是否重复授权 ，返回重复的员工姓名
	 * @author foss-gengzhe
	 * @date 2013-1-25 下午3:26:59
	 * @param waybillRfcAgentEntity
	 * @param currentEmpCodes
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRfcVarificationDao#queryWaybillRfcAgentByCondition(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcAgentEntity, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryWaybillRfcAgentByCondition(
			WaybillRfcAgentEntity waybillRfcAgentEntity, List<String> currentEmpCodes) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("currentEmpCodes", currentEmpCodes);
		map.put("status", waybillRfcAgentEntity.getStatus());
		map.put("validTime", waybillRfcAgentEntity.getValidTime());
		map.put("overDueTime", waybillRfcAgentEntity.getOverdueTime());
		map.put("active", waybillRfcAgentEntity.getActive());
		map.put("type", waybillRfcAgentEntity.getType());
		return this.getSqlSession().selectList(NAMESPACE + "queryWaybillRfcAgentByCondition", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public CusAccountEntity queryCusAccountByWaybillInfo(String accountName,
			String accountCode, String accountBank,String custCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accountName", accountName);
		map.put("accountCode", accountCode);
		map.put("accountBank", accountBank);
		map.put("custCode", custCode);
		map.put("active", FossConstants.ACTIVE);
		List<CusAccountEntity> cusAccountEntitys = (List<CusAccountEntity>)this.getSqlSession().selectList(NAMESPACE + "queryCusAccountByWaybillInfo", map);
		if(cusAccountEntitys.size()>0){
			return cusAccountEntitys.get(0);
		}else{
			return null;
		}
	}

	/**
	 * 
	 * <p>
	 * 依据更改单ID查询审核受理信息<br />
	 * </p>
	 * @author helong
	 * @version 0.1 2013-06-18
	 * @param waybillRfcId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillRfcActionHistoryEntity> queryWayBillRfcActionHistory(
			String waybillRfcId) {
		return this.getSqlSession().selectList(NAMESPACE_ACTIONHISTORY + "selectByRfcId", waybillRfcId);
	}
	
	@SuppressWarnings("unchecked")
	public List<WaybillRfcChangeDto> queryWaybillRfcCargoVarificationDto(
			WaybillRfcCondition waybillRfcCondition){
		return this.getSqlSession().selectList(NAMESPACE+"queryWaybillRfcCargoVarificationDto", waybillRfcCondition);
	}
	
	/**
	 * 获取客户最后一次开户银行信息
	 * @创建时间 2014-5-6 上午10:44:13   
	 * @创建人： WangQianJin
	 */
	@SuppressWarnings("unchecked")
	@Override
	public CusAccountEntity queryCusAccountByCreateTime(String accountName,
			String accountCode, String accountBank,String custCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accountName", accountName);
		map.put("accountCode", accountCode);
		map.put("accountBank", accountBank);
		map.put("custCode", custCode);
		map.put("active", FossConstants.ACTIVE);
		return (CusAccountEntity)this.getSqlSession().selectOne(NAMESPACE + "queryCusAccountByCreateTime", map);		
	}
	
	
	/**
	 * 根据运单ID更新客户编码
	 * @创建时间 2014-7-1 上午10:47:17   
	 * @创建人： WangQianJin
	 */
	@Override
	public void updateCustomerCodeByWaybillId(WaybillEntity waybillEntity) {
		this.getSqlSession().update(NAMESPACE + "updateCustomerCodeByWaybillId", waybillEntity);
	}
	
	//根据运单号查询改单状态
	@Override
	public WaybillRfcChangeDto queryWaybillRfcTypeById(String wayBillId){
		return (WaybillRfcChangeDto)this.getSqlSession().selectOne(NAMESPACE + "queryWaybillRfcTypeById", wayBillId);	
	}
}