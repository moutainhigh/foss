/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-common-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/server/dao/IToDoMsgDao.java
 * 
 * FILE NAME        	: IToDoMsgDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.api.server.dao;

import java.util.List;
import java.util.Set;

import com.deppon.foss.module.base.common.api.shared.domain.ToDoMsgEntity;
import com.deppon.foss.module.base.common.api.shared.dto.ToDoMsgDto;
import com.deppon.foss.module.base.common.api.shared.dto.TodoDto;

/**
 * 待办事项Dao
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2012-12-14 上午8:19:45
 */
public interface IToDoMsgDao {
	/**
	 * 根据单号和类型 结束待办消息
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author 268984 
	 * @date 2016-11-16 上午11:29:36
	 * @param waybillNO
	 * @param businessType
	 * @return
	 * @see
	 */
	 int updateToDoMsgByCondition(String waybillNO,String businessType);
	/**
	 * 批量保存待办事项
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-15 下午1:33:03
	 */
	int batchSaveToDoMsg(List<ToDoMsgEntity> entityList);
	
	/**
	 * <p>保存待办事项</p> 
	 * @author 094463-foss-xieyantao
	 * @date 2013-4-8 上午8:46:04
	 * @param entity
	 * @return
	 * @see
	 */
	int saveToDoMsg(ToDoMsgEntity entity);

	/**
	 * 批量删除待办事项
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-15 下午5:25:42
	 */
	int deleteToDoMsg(List<ToDoMsgEntity> entityList);
	
	/**
	 * <p>删除待办事项</p> 
	 * @author 094463-foss-xieyantao
	 * @date 2013-4-7 下午5:52:43
	 * @param entity
	 * @return
	 * @see
	 */
	int deleteToDoMsgOne(ToDoMsgEntity entity);

	/**
	 * 根据待办实体参数查询待办信息
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-17 上午10:56:22
	 */
	List<ToDoMsgDto> selectToDoMsgDetailByEntity(ToDoMsgDto entity);

	/**
	 * 根据待办实体参数查询待办信息 带分页
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-17 上午10:56:22
	 */
	List<ToDoMsgDto> selectToDoMsgDetailByEntity(ToDoMsgDto entity,
			int start, int limit);

	/**
	 * 根据待办实体参数查询待办信息总条数
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-17 上午11:05:43
	 */
	long countSelectToDoMsgDetailByEntity(ToDoMsgDto entity);
	/**
	 * 根据接收方编码 ，待办业类型，待办流水号  结束待办
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-17 下午2:16:57
	 */
	int updateToDoMsgByCondition(String receiveOrgCode,String businessType, String serialNumber);
	/**
	 * 根据待办接收组织编码，接收方角色，待办类型，状态查询待办信息
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-17 上午10:56:24
	 */
	List<ToDoMsgDto> selectToDoMsgDetailByCondition(String receiveOrgCode,Set<String> receiveRoleCodes, String businessType, String status);
	
	/**
	 * 统计当前用户未处理的待办事项
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-17 上午10:56:22
	 */
	List<ToDoMsgDto> queryToDoMsgTotal(String receiveOrgCode,Set<String> receiveRoleCodes,String urlType);
	
	/**
	 * 根据待办类型查询的待办事项列表
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2012-12-29 下午2:43:59
	 * @return 
	 */
	List<ToDoMsgDto> queryToDoMsgByBisType(String businessType,String receiveOrgCode,Set<String> receiveRoleCodes,String urlType, int start, int limit);
		 
	/**
	 * 根据待办类型查询待办事项列表 总条数
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2012-12-29 下午2:43:46
	 * @return 
	 */
	long countQueryToDoMsgByBisType(String businessType,String receiveOrgCode,Set<String> receiveRoleCodes,String urlType);

	/**
	 * 根据businesstype分类统计代办个数
	 * @author shixiawoei
	 * @param receiveOrgCode
	 * @param status
	 * @return
	 */
	List<ToDoMsgDto> countToDoMsgDetailGroupByBusinessType(
			String receiveOrgCode, String status);

	/**
	 *  查询一条代办
	 *  @author shixiawoei
	 * @param receiveOrgCode
	 * @param receiveRoleCodes
	 * @param businessType
	 * @param status
	 * @return
	 */
	List<ToDoMsgDto> selectToDoMsgDetailByConditionOne(String receiveOrgCode,
			Set<String> receiveRoleCodes, String businessType, String status);

	Integer queryTOdoMsgTotalFromWaybill(TodoDto dto);
	/**
	 * 根据接收方编码 ，待办业类型，待办流水号  结束待办(给接送货)
	 * @author 130346-foss-lifanghong
	 * @date 2013-07-16 下午5:42:55 
	 */
	int updateMsgByCondition(String receiveOrgCode, String businessType,
			String serialNumber);
	/**
	 * 查询库存超过90天，自动弃货未处理总数（根据当前传入的部门）
	 * @param dto
	 * @return
	 */
	 Integer queryAutoAbandGoodsMsgTotal(TodoDto dto);
	 
	 /**
	 * 统计CC催单信息
	 * @param currentInfo
	 * @author 132599-foss-shenweihua
	 * @return
	 */
	 Integer queryCallCenterWaybillMsgTotal(TodoDto dto);
}
