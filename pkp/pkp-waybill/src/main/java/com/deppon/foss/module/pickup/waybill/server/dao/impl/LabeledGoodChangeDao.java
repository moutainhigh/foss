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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/dao/impl/LabeledGoodChangeDao.java
 * 
 * FILE NAME        	: LabeledGoodChangeDao.java
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

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.ILabeledGoodChangeDao;
import com.deppon.foss.module.pickup.waybill.shared.define.LabeledGoodChangeHistoryConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodChangeEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabeledGoodDto;
import com.deppon.foss.util.UUIDUtils;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class LabeledGoodChangeDao
	extends iBatis3DaoImpl implements ILabeledGoodChangeDao {

	/**
	 * 审批状态
	 */
	private static final String FLOWSTATUS = "flowStatus";
	/**
	 * 运单号
	 */
	private static final String WAYBILLNO = "waybillNo";
	/**
	 * 命名空间
	 */
	private static final String NAMESPACE = "foss.pkp.LabeledGoodChangeEntityMapper.";

	/**
     * 
     * <p>按需插入一条数据</p> 
     * @author foss-sunrui
     * @date 2012-10-30 下午3:12:29
     * @param record
     * @return
     * @see
     */
	public int insertSelective(LabeledGoodChangeEntity record) {
		if(record.getId()==null){
			record.setId(UUIDUtils.getUUID());
		}
		return this.getSqlSession().insert(
				NAMESPACE + "insertLabeledGoodChangeSelective", record);
	}

	/**
     * 
     * <p>批量插入</p> 
     * @author foss-sunrui
     * @date 2012-11-6 下午4:53:14
     * @param record
     * @return
     * @see
     */
	public int insertBatch(List<LabeledGoodChangeEntity> list) {
		try {
			if (list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					list.get(i).setId(UUIDUtils.getUUID());
				}
				return this.getSqlSession().insert(
						NAMESPACE + "insertLabeledGoodChangeBatch", list);
			} else {
				return 0;
			}
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 根据运单号查询最近一次审批通过的 货签打木架和更改数量信息冗余信息表，如果该运单一次都没有更改过
	 * 责返回是一个size = 0的 List
	 * @param waybillNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public 	List<LabeledGoodChangeEntity>  
		queryLastApprovedLabelGoodChangeHistory(String waybillNo){
		Map<String, String> map = new HashMap<String, String> ();
		map.put(WAYBILLNO, waybillNo);
		//审批同意
		map.put(FLOWSTATUS, LabeledGoodChangeHistoryConstants.FLOW_STATUS_APPROVED);
		//去掉以前删除的货签
		map.put("changeType",  LabeledGoodChangeHistoryConstants.CHANGE_TYPE_DELETE);
		return this.getSqlSession().selectList(NAMESPACE 
				+ "queryLastApprovedLabelGoodChange", map);
	}

	/**
	 * 根据运单号查询最近一次审中的 货签打木架和更改数量信息冗余信息表，
	 * 如果该运单一次都没有更改过
	 * 责返回是一个size = 0的 List
	 * @param waybillNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LabeledGoodChangeEntity> queryInProcessLabelGoodChangeHistory(
			String waybillNo) {
		Map<String, String> map = new HashMap<String, String> ();
		//运单号 
		map.put(WAYBILLNO, waybillNo);
		//审批中 
		map.put(FLOWSTATUS, LabeledGoodChangeHistoryConstants.FLOW_STATUS_IN_PROCESS);
		
		return this.getSqlSession().selectList(NAMESPACE 
				+ "queryInProcessLabelGoodChangeHistory", map);
	}
	
	/**
	 * 将运单更新为审批通过
	 * 
	 * @param waybillNo
	 */
	public int updateLabeledGoodChangeToApprove(String waybillNo){
		Map<String, String> map = new HashMap<String, String> ();
		//运单号 
		map.put(WAYBILLNO, waybillNo);
		//审批同意
		map.put(FLOWSTATUS, LabeledGoodChangeHistoryConstants.FLOW_STATUS_APPROVED);
		//审批中 
		map.put("flowStatusBefore", LabeledGoodChangeHistoryConstants.FLOW_STATUS_IN_PROCESS);
		return this.getSqlSession().update(
				NAMESPACE + "updateLabeledGoodChangeToApprove", map);
	}
	
	/**
	 * 删除历史冗余信息 审批不通过 
	 * 
	 * @param waybillNo
	 */
	public int deleteLabeledGoodChangeInProcessByWaybillNo(String waybillNo){
		Map<String, String> map = new HashMap<String, String> ();
		//运单号 
		map.put(WAYBILLNO, waybillNo);
		//审批中 
		map.put(FLOWSTATUS, LabeledGoodChangeHistoryConstants.FLOW_STATUS_IN_PROCESS);
		return this.getSqlSession().delete(
				NAMESPACE + "deleteLabeledGoodChangeInProcessByWaybillNo", map);
				
	}

	/**
	 * 调用中转增加 取消走货路径中其中一票的接口
	 * @param newLabelList
	 */
	public void updateLabeledGoodChangeToNeedInvokeTfr(
			List<LabeledGoodDto> newLabelList) {
		if(newLabelList!=null && newLabelList.size()>0){
			for(LabeledGoodDto dto : newLabelList){
				this.getSqlSession().update(NAMESPACE +  "updateLabeledGoodChangeToNeedInvokeTfr", dto.getLabledGoodChangeId());
			}
		}	
	}
	
	/**
	 * 查询最近一次审批通过的所有记录
	 * @param waybillNo
	 * @return
	 */@SuppressWarnings("unchecked")
	public 	List<LabeledGoodChangeEntity>  
		queryLastApprovedChange(String waybillNo){
		Map<String, String> map = new HashMap<String, String> ();
		map.put(WAYBILLNO, waybillNo);
		
		List<LabeledGoodChangeEntity>  list = this.getSqlSession().selectList(NAMESPACE 
				+ "queryLastApprovedChange", map);
		if(list!= null && list.size()>0){
			List <LabeledGoodChangeEntity>  resultList = new ArrayList<LabeledGoodChangeEntity> ();
			LabeledGoodChangeEntity lastApprovalEntity = list.get(0);
			String changeTimes  = lastApprovalEntity.getChangeTimes();
			if(changeTimes==null){
				changeTimes = "";
			}
			for(int i=0;i<list.size(); i++){
				LabeledGoodChangeEntity entity = list.get(i);
				if(changeTimes.equals(entity.getChangeTimes())){
					resultList.add(entity);
				}
			}
			return resultList;
		}else{
			return list;
		}
	}
	 /**
	  * 将状态更新为不用更新中转接口了
	  * @param addList
	  */
	 public void updateLabeledGoodChangeToNoNeedInVokeInterface(
			 List<LabeledGoodChangeEntity> addList) {
		 if(addList!=null && addList.size()>0){
			 for(LabeledGoodChangeEntity dto : addList){
				 this.getSqlSession().update(NAMESPACE +  "updateLabeledGoodChangeToNoNeedInVokeInterface", dto.getId());
			 }
		 }	
		 
	 }
	 
	 /**
	  * 根据id将状态更新为不用更新中转接口了 
	  * @param id
	  * @author 272311-sangwenhao
	  */
	 public void updateLabeledGoodChangeToNoNeedInVokeInterface(String id){
		 if(StringUtils.isNotBlank(id)){
			this.getSqlSession().update(NAMESPACE +  "updateLabeledGoodChangeToNoNeedInVokeInterface", id);
		 }	
	 }
		/**
		 * 更改运单号
		 * @param waybillNo
		 * @param waybillNo2
		 */
	public void modifyWaybillNo(String waybillNo, String waybillNo2) {
		Map<String, String> map = new HashMap<String, String> ();
		//运单号 
		map.put(WAYBILLNO, waybillNo);
		map.put("newWaybill", waybillNo2);
		this.getSqlSession().update(NAMESPACE 
				+ "modifyWaybillNo", map);
	}
}