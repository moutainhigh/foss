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
 * PROJECT NAME	: pkp-waybill-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/dao/ILabeledGoodTodoDao.java
 * 
 * FILE NAME        	: ILabeledGoodTodoDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.waybill.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.pickup.waybill.api.shared.dto.ChangeNodeDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodTodoEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabeledGoodTodoWaybillDto;

/**
 * 
 * TO DO 待办
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-gengzhe,date:2012-12-7 上午9:08:06, </p>
 * @author foss-gengzhe
 * @date 2012-12-7 上午9:08:06
 * @since
 * @version
 */
public interface ILabeledGoodTodoDao {

	/**
	 * 
	 * <p>新增变更待办事项</p> 
	 * @author foss-gengzhe
	 * @date 2012-12-7 上午9:15:08
	 * @param labeledGoodTodoEntity
	 * @see
	 */
	void addLabeledGoodTodo(LabeledGoodTodoEntity labeledGoodTodoEntity);

	/**
	 * 查询当前部门待办
	 * @author 043260-foss-suyujun
	 * @date 2012-12-11
	 * @param args
	 * @return
	 * @return List<String>
	 * @see
	 */
	List<String> queryTodoWhenLoadTruck(Map<String, Object> args);

	/**
	 * 查询货物在指定部门的走货轨迹
	 * @author 043260-foss-suyujun
	 * @date 2012-12-12
	 * @param waybillNo
	 * @param serialNo
	 * @param lastOrgCode
	 * @return String
	 */
	List<LabeledGoodTodoEntity> queryActuatingNode(Map<String,Object> args);

	/**
	 * 更新执行节点
	 * @author 043260-foss-suyujun
	 * @date 2012-12-13
	 * @param entity
	 * @return void
	 * @see
	 */
	void updateLabeledGoodTodoEntityById(LabeledGoodTodoEntity entity);
	
	/**
	 * 
	 * <p>判断是否已存在重复的待办事项</p> 
	 * @author foss-gengzhe
	 * @date 2012-12-18 下午8:25:42
	 * @return
	 * @see
	 */
	List<LabeledGoodTodoEntity> queryByOrgAndRfcNo(Map<String,Object> args);

	
	
	/**
	 * 
	 * 查询变更节点
	 * 
	 * @date 2012-11-19 下午2:18:01
	 */
	List<LabeledGoodTodoEntity> queryTodoByWaybillRfcId(String waybillRfcId); 
	
	/**
	 * 
	 * 批量修改变更节点
	 * 
	 * @date 2012-11-19 下午2:18:01
	 */
	int updateBatchNodes(List<ChangeNodeDto> list);
	
	/**
	 * 查询所有入库时间为null的todo对象 
	 * 用于在定时任务中去中转补充时间
	 * @date 2012-11-19 下午2:18:01
	 */
	List<LabeledGoodTodoWaybillDto> selectByNullRemindTime();
	
	/**
	 * 超时的对象
	 * 生成催促代办
	 * @date 2012-11-19 下午2:18:01
	 */
	List<LabeledGoodTodoWaybillDto> selectByOverTime(LabeledGoodTodoEntity dto);
	
	/**
	 * 
	 * 判断货物是否交接已装车
	 * 
	 * @param args
	 * @return
	 * @author ibm-wangfei
	 * @date Apr 1, 2013 4:42:36 PM
	 */
	Long getTotalCountForStock(ChangeNodeDto dto);
	
	/**
	 * 
	 * 判断货物是否已出库
	 * 
	 * @param args
	 * @return
	 * @author ibm-wangfei
	 * @date Apr 1, 2013 4:42:36 PM
	 */
	Long queryOutStock(ChangeNodeDto dto);

	/**
	 * 
	 * 批量新增变更待办事项
	 * @author 102246-foss-shaohongliang
	 * @date 2013-4-12 上午9:33:38
	 */
	int batchAddLabeledGoodTodo(
			List<LabeledGoodTodoEntity> labeledGoodTodoEntitys);


	/**
	 * 
	 * 查看这票更改在待办中漂移后部门的原有状态
	 * @author 102246-foss-shaohongliang
	 * @date 2013-4-12 上午9:33:38
	 */
	String queryOrgCodeTodoStatus(String waybillRfcId, String orgCode);

	/**
	 * 
	 * 查询未打印的部门待办条数
	 * @author 102246-foss-shaohongliang
	 * @date 2013-4-12 上午9:33:38
	 */
	Long queryOrgCodeNoPrintTodoCount(String waybillRfcId, String oldOrgCode);

	/**
	 * 分页查询所有走货路径生成status='N'并且没有异常的代办
	 * @param labeledGoodTodoEntity
	 * @param start
	 * @param limited
	 * @return
	 */
	List<LabeledGoodTodoEntity> queryTodoByStatusAndExceptionMsg(
			LabeledGoodTodoEntity labeledGoodTodoEntity, int start, int limited);

	/**
	 * 删代办
	 * @param waybillNo
	 */
	void deleteTodoByWaybillNo(String waybillNo);

	/**
	 * @param id
	 */
	void deleteTodoByWaybillRfcId(String id);

	void updateLabeledGoodTodoEntityBatchById(Map<String, Object> batchMap);

	/**
	 * 根据更改单ID查询异常的待办数据
	 * @author 105888-foss-zhangxingwang
	 * @date 2013-9-27 20:45:03
	 * @param waybillRfcIdList
	 * @return
	 */
	List<LabeledGoodTodoEntity> queryLabelTodoExceptMsgStatus(
			List<String> waybillRfcIdList, String status);

	/**
	 * 根据流水号ID、流水号、待办状态查询所有未生成待办的数据，专为PDA开单不入库，需要进入包装库区件数减少的查询所有待办的生成状态而写
	 * @author Foss-105888-Zhangxingwang
	 * @date 2013-11-5 10:45:26
	 * @param labelGoodId
	 * @param serialNo
	 * @param status
	 */
	List<LabeledGoodTodoEntity> queryLabelGoodTodoStatusIsNoByLabelGoodId(
			String labelGoodId, String serialNo, String status);

	/**
	 * @function 根据已经作废的流水号ID删除对应的所有待办信息
	 * @author Foss-105888-Zhangxingwang
	 * @date 2013-11-6 10:05:16
	 */
	void insertSelective(LabeledGoodTodoEntity labeledGoodTodoEntity);

	/**
	 * @function 根据已经作废的流水号ID删除对应的所有待办信息
	 * @author Foss-105888-Zhangxingwang
	 * @date 2013-11-6 10:05:16
	 */
	void deleteTodoByLabelgoodIds(List<String> delabelGoodsList);

	int insertSelectiveBatch(List<LabeledGoodTodoEntity> labelList);

	/**
	 * 根据状态查询需要 重新入库的待办记录
	 * @Foss-105888-Zhangxingwangauthor 105888
	 * @date 2014-4-12 15:24:31
	 */
	List<LabeledGoodTodoEntity> queryNeedRestockTodoInfo(String status, int start, int limited);

	List<String> queryTodoActionWhenLost(String waybillNo,
			String serialNo, List<String> deptList);

	List<String> queryTodoWhenUnloadTruck(Map<String, Object> args);

	int resetTodoByIdList(List<String> todoIdList);

	List<LabeledGoodTodoEntity> queryNeedResetTodoBySerialNo(String waybillNo, String serialNo);
}