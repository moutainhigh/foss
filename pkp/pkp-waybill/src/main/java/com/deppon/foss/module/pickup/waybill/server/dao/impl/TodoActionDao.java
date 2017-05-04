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
 * PROJECT NAME  : pkp-waybill
 * 
 * FILE PATH          : src/main/java/com/deppon/foss/module/pickup/waybill/server/dao/impl/TodoActionDao.java
 * 
 * FILE NAME          : TodoActionDao.java
 * 
 * AUTHOR      : FOSS接送货系统开发组
 * 
 * HOME PAGE    : http://www.deppon.com
 * 
 * COPYRIGHT    : Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.dao.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.waybill.api.server.dao.ITodoActionDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodTodoEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.TodoActionEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ExceptMsgActionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ExceptMsgConditionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabelGoodTodoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabeledGoodTodoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LatestHandOverDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PendingMsgActionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PendingMsgConditionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PickupToDoMsgDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.TodoActionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.TodoConditionDto;
import com.deppon.foss.util.define.FossConstants;

public class TodoActionDao extends iBatis3DaoImpl implements ITodoActionDao {
    
    private static final String namespace="com.deppon.foss.module.pickup.waybill.todoAction.";  

  @Override
  public void updateProcessingStatus(String todoActionId, int confirmStatus) {
    Map<String, Object> paraMap = new HashMap<String, Object>();
    paraMap.put("id", todoActionId);
    paraMap.put("confirmStatus", confirmStatus);
    getSqlSession().update(namespace + "updateProcessingStatus", paraMap);
  }
  
  @Override
  public void updateLabeledHandleStatus(String waybillRfcId, String status, String deptCode) {
    Map<String, Object> paraMap = new HashMap<String, Object>();
    paraMap.put("waybillRfcId", waybillRfcId);
    paraMap.put("deptCode", deptCode);
    paraMap.put("status", status);
    getSqlSession().update(namespace + "updateLabeledHandleStatus", paraMap);
  }
  
  @Override
  public void updateLabeledHandleStatusAndTime(String waybillRfcId, String status, String deptCode) {
    Map<String, Object> paraMap = new HashMap<String, Object>();
    paraMap.put("waybillRfcId", waybillRfcId);
    paraMap.put("deptCode", deptCode);
    paraMap.put("status", status);
    paraMap.put("operateTime", new Date());
    getSqlSession().update(namespace + "updateLabeledHandleStatusAndTime", paraMap);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<TodoActionDto> queryTodoActionsByCondition(
      TodoConditionDto todoConditionDto, int start, int limit) {
    RowBounds rowBounds = new RowBounds(start, limit);
    return this.getSqlSession().selectList(
        namespace + "queryTodoActionsByCondition", todoConditionDto,
        rowBounds);
  }
  
  /**
   * @功能 特地为导出写的一个Dao方法
   * @author 105888-foss-zhangxingwang
   * @date 
   */
  @SuppressWarnings("unchecked")
@Override
  public List<TodoActionDto> queryTodoActionsByConditionAll(
			TodoConditionDto todoConditionDto){
	  return getSqlSession().selectList(namespace+"queryTodoActionsByCondition", todoConditionDto);
  }

  @Override
  public LabeledGoodTodoDto queryTodoAction(String waybillRfcId, String deptCode) {
    Map<String, Object> paraMap = new HashMap<String, Object>();
    paraMap.put("waybillRfcId", waybillRfcId);
    paraMap.put("deptCode", deptCode);
    return (LabeledGoodTodoDto) getSqlSession().selectOne(namespace + "queryTodoAction", paraMap);
  }

  
  @Override
  public LabeledGoodTodoDto queryTodoAction(String waybillRfcId, String deptCode, 
      String handleOrgCode, String handleOrgCode2) {
    Map<String, Object> paraMap = new HashMap<String, Object>();
    paraMap.put("waybillRfcId", waybillRfcId);
    paraMap.put("deptCode", deptCode);
    paraMap.put("currentDept", handleOrgCode);
    paraMap.put("status", FossConstants.YES);
    paraMap.put("lastLoadOrgCode", handleOrgCode2);
    
    return (LabeledGoodTodoDto) getSqlSession().selectOne(namespace + "queryTodoAction", paraMap);
  }
  
  
  @Override
  public void updateLabeledPringStatus(String labeledGoodTodoId,String printed) {
    
    Map<String, Object> paraMap = new HashMap<String, Object>();
    
    
    paraMap.put("id", labeledGoodTodoId);
    paraMap.put("isPrinted", FossConstants.YES);
      
    LabeledGoodTodoEntity todoEntity = this.selectById(labeledGoodTodoId);
  
    if(todoEntity!=null && todoEntity.getPrintTime()==null ){
	    paraMap.put("printTime", new Date());
	    paraMap.put("operator", FossUserContext.getCurrentInfo().getEmpName());
	    paraMap.put("operatorCode", FossUserContext.getCurrentInfo().getEmpCode());
	    paraMap.put("operatorTime", new Date());
	    getSqlSession().update(namespace + "updateLabeledPringStatus", paraMap);
    }else{
    	getSqlSession().update(namespace + "updateLabeledPringStatus", paraMap);
    }
    
  }
  
  @Override
  public void updateLabeledPringStatusBatch(List<LabeledGoodTodoEntity> labeledGoodTodoEntitys) {
  	// TODO labeledGoodList
	  Map<String, Object> paraMap = new HashMap<String, Object>();	    
	  List<String> labeledGoodList = new ArrayList<String>();
	  for(LabeledGoodTodoEntity todoEntity : labeledGoodTodoEntitys){
		  labeledGoodList.add(todoEntity.getId());
	  }
	    paraMap.put("labeledGoodList", labeledGoodList);
	    paraMap.put("isPrinted", FossConstants.YES);
	    paraMap.put("printTime", new Date());
        paraMap.put("operator", FossUserContext.getCurrentInfo().getEmpName());
        paraMap.put("operatorCode", FossUserContext.getCurrentInfo().getEmpCode());
        paraMap.put("operatorTime", new Date());
        this.getSqlSession().update(namespace+"updateLabeledPringStatusBatch", paraMap);
  }

  @Override
  public String queryTodoActionIdIfAllPrinted(String labeledGoodTodoId) {
    Map<String, Object> paraMap = new HashMap<String, Object>();
    paraMap.put("labeledGoodTodoId", labeledGoodTodoId);
    paraMap.put("isPrinted", FossConstants.YES);
    return (String) getSqlSession().selectOne(namespace + "queryTodoActionIdIfAllPrinted", labeledGoodTodoId);
  }

  @Override
  public Long queryGoodsInfoCount(TodoConditionDto todoConditionDto) {
    return (Long) getSqlSession().selectOne(namespace + "queryGoodsInfoCount", todoConditionDto);
  }
  
  @Override
  public Long queryTodoActionNoPrintCount(TodoConditionDto todoConditionDto) {
    return (Long) getSqlSession().selectOne(namespace + "queryTodoActionNoPrintCount", todoConditionDto);
  }

  /**
   * 
   * <p>新增变更待办事项</p> 
   * @author foss-gengzhe
   * @date 2012-12-3 下午7:55:33
   * @param todoActionEntity 
   * @see com.deppon.foss.module.pickup.waybill.api.server.dao.ITodoActionDao#addToDoAction(com.deppon.foss.module.pickup.waybill.shared.domain.TodoActionEntity)
   */
  @Override
  public void addToDoAction(TodoActionEntity todoActionEntity) {
      getSqlSession().insert(namespace + "addToDoAction", todoActionEntity);
  }
  
  @Override
  public LabeledGoodTodoEntity selectById(String id){
    return (LabeledGoodTodoEntity) getSqlSession().selectOne(namespace + "selectById", id);
  }

  /**
   * 更新待办
   */
  @Override
  public void updateLabeledStatusByWaybillNum(String waybillNo,
      List<String> serials) {
    
  Map< String, Object> updateMap=new HashMap<String, Object>();
  updateMap.put("waybillNum", waybillNo);
  updateMap.put("serials", serials);
  getSqlSession().update(namespace + "updateLabeledStatusByWaybillNum", updateMap);
  }
  
  /**
   * @功能 根据条件查询所有异常数据
   * @author 105888-foss-zhangxingwang
   * @return 
   * @date 2013-7-29 17:09:28
   */
  @Override
  public Long queryExceptMsgInfoCount(
      ExceptMsgConditionDto exceptMsgConditionDto) {
    return (Long) getSqlSession().selectOne(namespace+"queryLabelGoodTodoExceptMsgCount", exceptMsgConditionDto);
  }
  
  /**
   * 查询所有异常数据的详细信息
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<ExceptMsgActionDto> queryTodoActionsByCondition(
      ExceptMsgConditionDto exceptMsgConditionDto, int start, int limit) {
    RowBounds rowBounds = new RowBounds(start, limit);
    return getSqlSession().selectList(namespace+"queryLabelGoodTodoExceptMsg", exceptMsgConditionDto,rowBounds);
  }
  

  /**
   * 查询未生成代办详细信息
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<PendingMsgActionDto> queryPendTodoActionDto(
      PendingMsgConditionDto pendingMsgConditionDto, int start, int limit) {
    RowBounds rowBounds = new RowBounds(start, limit);
    return getSqlSession().selectList(namespace+"queryPendTodoException", pendingMsgConditionDto,rowBounds);
  }
  
  /**
   * 查询代办未生成数量
   */
  @Override
  public Long queryPendTodoActionDtoCount(
      PendingMsgConditionDto pendingMsgConditionDto) {
    return (Long) getSqlSession().selectOne(namespace+"queryPendTodoExceptionCount", pendingMsgConditionDto);
  }

  @SuppressWarnings("unchecked")
@Override
  public List<LabelGoodTodoDto> queryLabelGoodTodo(String waybillRfcId){
	  Map< String, Object> updateMap=new HashMap<String, Object>();
	  updateMap.put("waybillRfcId", waybillRfcId);
	  return getSqlSession().selectList(namespace+"queryLabelGoodTodo", updateMap);
  }
  
  public void updateLabelTodoAndNewPathDetail(String waybillRfcId){
	  Map< String, Object> updateMap=new HashMap<String, Object>();
	  updateMap.put("waybillRfcId", waybillRfcId);
	  getSqlSession().update(namespace + "updateLabelGoodsTodo", updateMap);
  }

  public void updatePendTodoFailReason(List<String> pendTodoIdList){
	  Map< String, Object> updateMap=new HashMap<String, Object>();
	  updateMap.put("pendTodoIdList", pendTodoIdList);
	  getSqlSession().update(namespace + "updatePendTodoLabelGood", updateMap);
  }

@Override
public Long queryGoodsInfoWithHandleOrgCount(TodoConditionDto todoConditionDto) {
	return (Long) this.getSqlSession().selectOne(namespace+"queryGoodsInfoWithHandleOrgCount", todoConditionDto);
}

@SuppressWarnings("unchecked")
@Override
public List<TodoActionDto> queryTodoActionsByConditionWithHandleOrg(
		TodoConditionDto todoConditionDto, int start, int limit) {
	RowBounds rowBounds = new RowBounds(start, limit);
	return this.getSqlSession().selectList(namespace+"queryTodoActionsByConditionWithHandleOrg", todoConditionDto, rowBounds);
}

@Override
public void updateExceptMsgBatchStatus(List<String> waybillRfcIdList) {
	Map<String, Object> paraMap = new HashMap<String, Object>();
	    paraMap.put("waybillRfcIdList", waybillRfcIdList);
	    paraMap.put("printTime", "");
      paraMap.put("status", FossConstants.NO);
      paraMap.put("exceptMsg", FossConstants.NO);
      paraMap.put("isPrint", FossConstants.NO);
      paraMap.put("needRestock", FossConstants.NO);
      paraMap.put("handleOrgCode", "");
      paraMap.put("handleOrgName", "");
      this.getSqlSession().update(namespace+"updateExceptMsgBatchStatus", paraMap);
}

@SuppressWarnings("unchecked")
@Override
public List<LatestHandOverDto> queryLatestHandoverDto(TodoConditionDto todoConditionDto){
	return this.getSqlSession().selectList(namespace+"queryLatestHandoverDto", todoConditionDto);
}

@Override
public void updateAllNotPrintLabeledBatch(List<String> labelGoodsIdList,
		String yes, String no) {
	Map<String, Object> paraMap = new HashMap<String, Object>();
	paraMap.put("labelGoodsIdList", labelGoodsIdList);
	paraMap.put("status", FossConstants.YES);
	paraMap.put("isPrint", FossConstants.NO);
	paraMap.put("printTime", new Date());
	paraMap.put("operatorTime", new Date());
	paraMap.put("operator", "system");
	paraMap.put("operatorCode", "000000");
	paraMap.put("isPrinted", FossConstants.YES);
	this.getSqlSession().update(namespace+"updateAllNotPrintLabeledBatch", paraMap);
}

/**
 * 根据交接单对应里面的流水号进行待办的更新，至当前的操作部门
 * @author Foss-105888-Zhangxingwang
 * @date 2014-6-25 09:18:03
 */
@Override
public void updateTodoByHandoverBillNo(Map<Object, Object> maps) {
	this.getSqlSession().update(namespace+"updateTodoByHandoverBillNo", maps);
}

/**
 * 查询交接单对应流水号记录表中的数据，主要是考虑到一个交接单里面的流水个数大于1000个，给oracle进行数据的更新带来不便
 * @author Foss-105888-Zhangxingwang
 * @date 2014-6-25 09:19:23
 */
@SuppressWarnings("unchecked")
@Override
public List<String> selectMaxCountHandoverSerialNo(
		List<String> handOverbillNoList) {
	Map<String, Object> paraMap = new HashMap<String, Object>();
	paraMap.put("handoverBillNoList", handOverbillNoList);
	return this.getSqlSession().selectList(namespace+"selectMaxCountHandoverSerialNo", paraMap);
}

/**
 * DMANA-9463 整车运单未签收提示功能
 * @author 218438-foss-zhulei
 */
@SuppressWarnings("unchecked")
@Override
public List<PickupToDoMsgDto> queryWaybillNotSignDataList(PickupToDoMsgDto toDoMsgDto,List<Integer> days) {
	Map<String, Object> maps = new HashMap<String, Object>();
	maps.put("orgCode",toDoMsgDto.getReceiveOrgCode());
	maps.put("productCode", toDoMsgDto.getProductCode());
	maps.put("days",days);
	return this.getSqlSession().selectList(namespace+"queryWaybillInfoForWvhNotSigned", maps);
}
}