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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/dao/impl/LabeledGoodTodoDao.java
 * 
 * FILE NAME        	: LabeledGoodTodoDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.ILabeledGoodTodoDao;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.ChangeNodeDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodTodoEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabeledGoodTodoWaybillDto;
import com.deppon.foss.module.transfer.load.api.shared.define.TaskTruckConstant;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * TODO待办
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-gengzhe,date:2012-12-7 上午9:16:47 </p>
 * @author foss-gengzhe
 * @date 2012-12-7 上午9:16:47
 * @since
 * @version
 */
public class LabeledGoodTodoDao extends iBatis3DaoImpl implements ILabeledGoodTodoDao{

	private static final String namespace="foss.pkp.LabeledGoodTodoEntityMapper.";
	
	/**
	 * 
	 * <p>TODO新增待办</p> 
	 * @author foss-gengzhe
	 * @date 2012-12-7 上午9:20:46
	 * @param labeledGoodTodoEntity 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.ILabeledGoodTodoDao#addLabeledGoodTodo(com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodTodoEntity)
	 */
	@Override
	public void addLabeledGoodTodo(LabeledGoodTodoEntity labeledGoodTodoEntity) {
		labeledGoodTodoEntity.setId(UUIDUtils.getUUID());
		List list2 = getSqlSession().selectList(namespace + "selectBySerialNoAndRfcId",  labeledGoodTodoEntity );
		if(list2!=null && list2.size()>0){
			return;
		}
		getSqlSession().insert(namespace + "addLabeledGoodTodo", labeledGoodTodoEntity);
	}

	/**
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-11
	 * @param args
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.ILabeledGoodTodoDao#queryTodoWhenLoadTruck(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryTodoWhenLoadTruck(Map<String, Object> args) {
		return this.getSqlSession().selectList(namespace + "queryTodoWhenLoadTruck", args);
	}

	/**
	 * 查询货物在指定部门的走货轨迹
	 * @author 043260-foss-suyujun
	 * @date 2012-12-12
	 * @param waybillNo
	 * @param serialNo
	 * @param lastOrgCode
	 * @return String
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.ILabeledGoodTodoDao#queryActuatingNode(java.lang.String, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LabeledGoodTodoEntity> queryActuatingNode(Map<String,Object> args) {
		return this.getSqlSession().selectList(namespace + "queryActuatingNode", args);
	}

	/**
	 * 更新执行节点
	 * @author 043260-foss-suyujun
	 * @date 2012-12-13
	 * @param entity
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.ILabeledGoodTodoDao#updateLabeledGoodTodoEntity(com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodTodoEntity)
	 */
	@Override
	public void updateLabeledGoodTodoEntityById(LabeledGoodTodoEntity entity) {
		this.getSqlSession().update(namespace + "updateByPrimaryKeySelective", entity);
	}

	/**
	 * 
	 * <p>判断是否已存在重复的待办事项</p> 
	 * @author foss-gengzhe
	 * @date 2012-12-18 下午8:34:14
	 * @param args
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.ILabeledGoodTodoDao#queryByOrgAndRfcNo(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LabeledGoodTodoEntity> queryByOrgAndRfcNo(Map<String,Object> args) {
		return this.getSqlSession().selectList(namespace + "queryByOrgAndRfcNo", args);
	}
	
	/**
	 * 
	 * 查询变更节点
	 * 
	 * @date 2012-11-19 下午2:18:01
	 */
	@SuppressWarnings("unchecked")
	public List<LabeledGoodTodoEntity> queryTodoByWaybillRfcId(String waybillRfcId){
		return this.getSqlSession().selectList(namespace + "queryTodoByWaybillRfcId", waybillRfcId);
	}
	
	/**
	 * 
	 * 批量修改变更节点
	 * 
	 * @date 2012-11-19 下午2:18:01
	 */
	public int updateBatchNodes(List<ChangeNodeDto> list){
		int num =  this.getSqlSession().insert(namespace + "updateBatchNodes", list);
		return num;
	}

	/**
	 * 查询所有入库时间为null的todo对象 
	 * 用于在定时任务中去中转补充时间
	 * @date 2012-11-19 下午2:18:01
	 */
	@SuppressWarnings("unchecked")
	public List<LabeledGoodTodoWaybillDto> selectByNullRemindTime() {
		return this.getSqlSession().selectList(namespace + "selectByNullRemindTime");
	}
	
	
	
	/**
	 * 超时的对象
	 * 生成催促代办
	 * @date 2012-11-19 下午2:18:01
	 */
	@SuppressWarnings("unchecked")
	public List<LabeledGoodTodoWaybillDto>  selectByOverTime(LabeledGoodTodoEntity dto) {
		return this.getSqlSession().selectList(namespace + "selectByOverTime", dto);
	}

	/**
	 * 
	 * 判断货物是否交接装车
	 * 
	 * @param args
	 * @return
	 * @author ibm-wangfei
	 * @date Apr 1, 2013 4:42:36 PM
	 */
	@Override
	public Long getTotalCountForStock(ChangeNodeDto dto) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("waybillNo", dto.getWaybillNo());
		map.put("serialNo", dto.getSerialNo());
		map.put("origOrgCode", dto.getExeNode());
		map.put("taskState", TaskTruckConstant.TASK_TRUCK_STATE_CANCLED);
		map.put("beLoaded", FossConstants.YES);
		return (Long) this.getSqlSession().selectOne(namespace + "getTotalCountForStock", map);
	}
	
	/**
	 * 
	 * 判断货物是否出库
	 * 
	 * @param args
	 * @return
	 * @author ibm-wangfei
	 * @date Apr 1, 2013 4:42:36 PM
	 */
	@Override
	public Long queryOutStock(ChangeNodeDto dto) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("wayBillNo", dto.getWaybillNo());
		map.put("serialNo", dto.getSerialNo());
		map.put("orgCode", dto.getExeNode());
		map.put("isValid", FossConstants.YES);
		return (Long) this.getSqlSession().selectOne(namespace + "queryOutStock", map);
	}


	/**
	 * 
	 * 批量新增变更待办事项
	 * @author 102246-foss-shaohongliang
	 * @date 2013-4-12 上午9:33:38
	 */
	@Override
	public int batchAddLabeledGoodTodo(
		List<LabeledGoodTodoEntity> list) {
		int j = 0;
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setId(UUIDUtils.getUUID());
				
				List list2 = getSqlSession().selectList(namespace + "selectBySerialNoAndRfcId",  list.get(i));
				if(list2!=null && list2.size()>0){
					continue;
				}
				j = j+ getSqlSession().insert(namespace + "addLabeledGoodTodo", list.get(i));
			}
			return j;
		} else {
			return 0;
		}
	}
	

	/**
	 * 
	 * 查看这票更改在待办中漂移后部门的原有状态
	 * @author 102246-foss-shaohongliang
	 * @date 2013-4-12 上午9:33:38
	 */
	@Override
	public String queryOrgCodeTodoStatus(String waybillRfcId, String orgCode){
		Map<String, String> map = new HashMap<String, String>();
		map.put("waybillRfcId", waybillRfcId);
		map.put("orgCode", orgCode);
		return (String) this.getSqlSession().selectOne(namespace + "queryOrgCodeTodoStatus", map);
	}
	

	/**
	 * 
	 * 查询未打印的部门待办条数
	 * @author 102246-foss-shaohongliang
	 * @date 2013-4-12 上午9:33:38
	 */
	@Override
	public Long queryOrgCodeNoPrintTodoCount(String waybillRfcId, String orgCode){
		Map<String, String> map = new HashMap<String, String>();
		map.put("waybillRfcId", waybillRfcId);
		map.put("orgCode", orgCode);
		return (Long) this.getSqlSession().selectOne(namespace + "queryOrgCodeNoPrintTodoCount", map);
	}
	
	/**
	 * 分页查询所有走货路径生成status='N'并且没有异常的代办
	 * @param labeledGoodTodoEntity
	 * @param start
	 * @param limited
	 * @return
	 */
	
	/**
	 * 
	 * 查询未打印的部门待办条数
	 * @author 102246-foss-shaohongliang
	 * @date 2013-4-12 上午9:33:38
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<LabeledGoodTodoEntity> queryTodoByStatusAndExceptionMsg(
			LabeledGoodTodoEntity labeledGoodTodoEntity, int start, int limited){
		RowBounds bounds = new RowBounds(start, limited);
		return this.getSqlSession().selectList(namespace + 
				"queryTodoByStatusAndExceptionMsg", labeledGoodTodoEntity, bounds);
	}
	
	/**
	 * 删代办
	 */
	public void deleteTodoByWaybillNo(String waybillNo){
		this.getSqlSession().delete(namespace + 
				"deleteTodoByWaybillNo", waybillNo);
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.ILabeledGoodTodoDao#deleteTodoByWaybillRfcId(java.lang.String)
	 */
	@Override
	public void deleteTodoByWaybillRfcId(String id) {
		this.getSqlSession().delete(namespace + 
				"deleteTodoByWaybillRfcId", id);
	}

	@Override
	public void updateLabeledGoodTodoEntityBatchById(
			Map<String, Object> batchMap) {
		this.getSqlSession().update(namespace+"updateLabeledGoodTodoEntityBatchById", batchMap);
	}

	/**
	 * 根据更改单ID查询异常的待办数据
	 * @author 105888-foss-zhangxingwang
	 * @date 2013-9-27 20:45:03
	 * @param waybillRfcId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LabeledGoodTodoEntity> queryLabelTodoExceptMsgStatus(
			List<String> waybillRfcIdList, String status) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("waybillRfcIdList", waybillRfcIdList);
		map.put("status", status);
		return this.getSqlSession().selectList(namespace+"queryLabelTodoExceptMsgStatus", map);
	}
	
	/**
	 * 根据流水号ID、流水号、待办状态查询所有未生成待办的数据，专为PDA开单不入库，需要进入包装库区件数减少的查询所有待办的生成状态而写
	 * @author Foss-105888-Zhangxingwang
	 * @date 2013-11-5 10:45:26
	 * @param labelGoodId
	 * @param serialNo
	 * @param status
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LabeledGoodTodoEntity> queryLabelGoodTodoStatusIsNoByLabelGoodId(String labelGoodId, String serialNo, String status){
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("labelGoodId", labelGoodId);
		map.put("serialNo", serialNo);
		map.put("status", status);
		return this.getSqlSession().selectList(namespace+"queryLabelGoodTodoStatusIsNoByLabelGoodId", map);
	}
	@Override
	public void insertSelective(LabeledGoodTodoEntity labeledGoodTodoEntity){
		labeledGoodTodoEntity.setId(UUIDUtils.getUUID());
		List list2 = getSqlSession().selectList(namespace + "selectBySerialNoAndRfcId",  labeledGoodTodoEntity );
		if(list2!=null && list2.size()>0){
			return;
		}
		getSqlSession().insert(namespace + "insertSelective", labeledGoodTodoEntity);
	}
	
	/**
	 * @function 根据已经作废的流水号ID删除对应的所有待办信息
	 * @author Foss-105888-Zhangxingwang
	 * @date 2013-11-6 10:05:16
	 */
	@Override
	public void deleteTodoByLabelgoodIds(List<String> delabelGoodsList){
		Map<Object,Object> map = new HashMap<Object,Object>();
		map.put("delabelGoodsList", delabelGoodsList);
		this.getSqlSession().delete(namespace+"deleteTodoByLabelgoodIds", map);
	}
	
	/**
	 * 支持批量插入数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-4-10 09:42:17
	 * @param labelList
	 * @return
	 */
	@Override
	public int insertSelectiveBatch(List<LabeledGoodTodoEntity> labelList){
		return this.getSqlSession().insert(namespace+"insertSelectiveBatch", labelList);
	}
	
	/**
	 * 根据状态查询需要 重新入库的待办记录
	 * @Foss-105888-Zhangxingwangauthor 105888
	 * @date 2014-4-12 15:24:31
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LabeledGoodTodoEntity> queryNeedRestockTodoInfo(String status, int start, int limited){
		RowBounds bounds = new RowBounds(start, limited);
		Map<Object,Object> map = new HashMap<Object,Object>();
		map.put("status", status);
		return this.getSqlSession().selectList(namespace+"queryNeedRestockTodoInfo", map, bounds);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryTodoActionWhenLost(String waybillNo, String serialNo, List<String> deptList) {
		Map<Object,Object> map = new HashMap<Object,Object>();
		map.put("waybillNo", waybillNo);
		map.put("serialNo", serialNo);
		map.put("deptList", deptList);
		return this.getSqlSession().selectList(namespace+"queryTodoActionWhenLost", map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryTodoWhenUnloadTruck(Map<String, Object> args) {
		return this.getSqlSession().selectList(namespace+"queryTodoWhenUnloadTruck", args);
	}
	
	@Override
	public int resetTodoByIdList(List<String> todoIdList){
		Map<Object,Object> map = new HashMap<Object,Object>();
		map.put("todoIdList", todoIdList);
		return this.getSqlSession().update(namespace+"resetTodoByIdList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<LabeledGoodTodoEntity> queryNeedResetTodoBySerialNo(String waybillNo, String serialNo){
		Map<Object,Object> map = new HashMap<Object,Object>();
		map.put("waybillNo", waybillNo);
		map.put("serialNo", serialNo);
		return this.getSqlSession().selectList(namespace+"queryNeedResetTodoBySerialNo", map);
	}
}