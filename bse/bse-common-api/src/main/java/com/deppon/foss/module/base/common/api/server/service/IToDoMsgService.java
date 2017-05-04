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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/server/service/IToDoMsgService.java
 * 
 * FILE NAME        	: IToDoMsgService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.api.server.service;

import java.util.List;
import java.util.Set;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.common.api.shared.domain.ToDoMsgEntity;
import com.deppon.foss.module.base.common.api.shared.domain.ToDoMsgResult;
import com.deppon.foss.module.base.common.api.shared.dto.ToDoMsgDto;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;

/**
 * 待办事项Service 目前支持两种方式进行创建待办： 1. 增量创建
 * 业务在可能产生待办的触发点，调用接口createIncrementToDoMsg进行创建一条或者多条待办；
 * 在业务结束的触发点，调用接口finishToDoMsg结束某条待办；
 * 
 * 此种方式适合与触发点较少的情况；
 * 
 * 2. 全量创建 业务应用需要针对特定的待办类型，定时轮询全网所有网点的所有待办，指定角色后调用此接口createFullToDoMsg 进行创建待办；
 * 此种方式不需要待办结束的动作，因为每一次都会全量删除后再进行插入。
 * 
 * 创建待办必须指定： 1. 待办部门 2. 待办角色 3. 待办类型（更改单待办等） 4. 业务单号类型（运单、订单等）
 * 
 * 另外，业务需要提供对于具体待办明细对应的URL，以供界面跳转；
 * 比如：http://[host]:[port]/stl/queryWaybill.action?waybillno={?}
 * 
 * 需要注意的是：待办是发给目标部门下的对应员工，不包括目标部门下面的子部门的对应员工；
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2012-12-12 下午7:08:02
 */
public interface IToDoMsgService extends IService {

	/**
	 * 单个或批量创建待办(增量) 同种待办类型不同业务单号的待办进行新建待办
	 * 
	 * @param entityList
	 *            待办事项集合
	 * @return 保存待办的反馈结果
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-12 下午5:42:55
	 */
	List<ToDoMsgResult> createIncrementToDoMsg(List<ToDoMsgEntity> entityList);

	/**
	 * 单个结束待办 对同待办类型同业务单号的待办进行结束处理操作
	 * 
	 * @param receiveOrgCode
	 *            接收网点编码
	 * @param businessType
	 *            待力业务类型
	 * @param serialNumber
	 *            待办业务流水号
	 * @return 保存待办的反馈结果
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-12 下午5:42:55
	 */
	ToDoMsgResult finishToDoMsg(String receiveOrgCode, String businessType,
			String serialNumber);

	/**
	 * 创建待办(全量) 插入前先删除同类型的待办再新建
	 * 
	 * @param entity
	 *            待办事项集合
	 * @return 保存待办的反馈结果
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-12 下午5:42:55
	 */
	List<ToDoMsgResult> createFullToDoMsg(List<ToDoMsgEntity> entity);

	/**
	 * 根据查询条件查询待办明细
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-12 下午6:53:11
	 */
	List<ToDoMsgDto> queryToDoMsgDetail(ToDoMsgDto dto);

	/**
	 * 根据接收网点编码,接收方角色,待办类型,和状态 查询待办明细
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-12 下午6:53:11
	 */
	List<ToDoMsgDto> queryToDoMsgDetail(String receiveOrgCode,
			Set<String> receiveRoleCodes, String businessType, String status);

	/**
	 * 根据businesstype分类统计代办个数
	 * @param receiveOrgCode
	 * @param status
	 * @return
	 */
	List<ToDoMsgDto> countToDoMsgDetail(String receiveOrgCode,String status);
	
	/**
	 * 根据待办类型查询待办信息
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @param
	 * @date 2012-12-29 下午2:51:22
	 * @return
	 */
	List<ToDoMsgDto> queryToDoMsgByBisType(String businessType,CurrentInfo currentInfo,String urlType, int start, int limit);

	/**
	 * 根据待办类型查询待办信息总条数
	 * 
	 * @author shixiawoei
	 * @param
	 * @date 2012-12-29 下午2:47:35
	 * @return
	 */
	long countQueryToDoMsgByBisType(String businessType, CurrentInfo currentInfo,String urlType);

	/**
	 * 统计当前用户未处理的待办事项
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-17 上午10:56:22
	 */
	List<ToDoMsgDto> queryToDoMsgTotal(CurrentInfo currentInfo,String urlType);

	/**
	 * 根据待办实体参数查询待办信息 带分页
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-17 上午10:56:22
	 */
	List<ToDoMsgDto> selectToDoMsgDetailByEntity(ToDoMsgDto dto,
			CurrentInfo currentInfo, int start, int limit);

	/**
	 * 根据待办实体参数查询待办信息总条数
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-17 上午11:05:43
	 */
	long countSelectToDoMsgDetailByEntity(ToDoMsgDto dto,
			CurrentInfo currentInfo);

	/**
	 * 根据接收网点编码,接收方角色,待办类型,和状态 查询待办明细 查询一条代办
	 * @author shixiawoei
	 * @param receiveOrgCode
	 * @param object
	 * @param businessType
	 * @param todomsgStatusTypeProcessing
	 * @return
	 */
	List<ToDoMsgDto> queryToDoMsgDetailOne(String receiveOrgCode,
			Set<String> receiveRoleCodes, String businessType,
			String todomsgStatusTypeProcessing);
	/**
	 * 单个结束待办给接送货
	 * 对同待办类型同业务单号的待办进行结束处理操作
	 * @param receiveOrgCode 接收网点编码
	 * @param businessType 待力业务类型
	 * @param serialNumber 待办业务流水号
	 * @return 保存待办的反馈结果
	 * @author 130346-foss-lifanghong
	 * @date 2013-07-16 下午5:42:55 
	 */
	ToDoMsgResult finishToDoMsgs(String receiveOrgCode, String businessType,
			String serialNumber);
	/**
	 * 统计库存超过90天自动转弃货未处理的提醒
	 * @param currentInfo
	 * @param urlType
	 * @return
	 */
	 List<ToDoMsgDto> queryAbandGoodsTypeAutoTotal(CurrentInfo currentInfo,String urlType);
	 
	 /**
	 * 统计CC催单信息
	 * @param currentInfo
	 * @author 132599-foss-shenweihua
	 * @return
	 */
	 List<ToDoMsgDto> queryCallCenterWaybillMsgTotal(CurrentInfo currentInfo);
}
