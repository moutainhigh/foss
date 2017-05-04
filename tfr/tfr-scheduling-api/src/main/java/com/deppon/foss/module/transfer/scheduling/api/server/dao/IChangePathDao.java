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
 *  
 *  PROJECT NAME  : tfr-scheduling-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/server/dao/IChangePathDao.java
 * 
 *  FILE NAME     :IChangePathDao.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
package com.deppon.foss.module.transfer.scheduling.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.scheduling.api.shared.domain.ChangePathEntity;

/**
 * 路径改变dao
 * @author huyue
 * @date 2012-10-11 下午9:16:55
 */
public interface IChangePathDao{

	/**
	 * 新增路径改变信息 自动筛选有值字段
	 * @author huyue
	 * @date 2012-10-18 下午6:50:41
	 */
	int addChangePathSelect(ChangePathEntity changePathEntity);
	
	/**
	 * 根据部门,原货区编号,时间查询相应调整策略 order by 生效时间倒序 routeNo 正序  如果有两次修改,则取最近一次修改
	 * @author huyue
	 * @date 2012-11-7 下午3:29:56
	 */
	List<ChangePathEntity> queryByTime(Map<String, Object> map);
	
	/**
	 * 新增路径改变信息
	 * @author huyue
	 * @date 2012-10-18 下午6:50:51
	 */
	int addChangePath(ChangePathEntity changePathEntity);

	/**
	 * 批量新增路径改变信息
	 * @author huyue
	 * @date 2012-10-18 下午6:51:01
	 */
	int addListChangePath(List<ChangePathEntity> changePathList);
	
	/**
	 * 根据PKID更新路径改变信息 自动筛选有值字段
	 * @author huyue
	 * @date 2012-10-18 下午6:51:10
	 */
	int updateChangePathSelect(ChangePathEntity changePathEntity);
	
	/**
	 * 根据PKID更新路径改变信息
	 * @author huyue
	 * @date 2012-10-18 下午6:51:19
	 */
	int updateChangePath(ChangePathEntity changePathEntity);
	
	/**
	 * 批量更新路径改变信息
	 * @author huyue
	 * @date 2012-10-18 下午6:51:28
	 */
	int updateListChangePath(List<ChangePathEntity> changePathList);
	
	/**
	 * 根据部门和原货区查询非合车路径改变信息
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-8 上午10:13:55
	 */
	ChangePathEntity queryModifyChangePath(String origOrgCode, String origGoodsAreaCode);
	/**
	 *  根据部门和原货区查询合车路径改变信息
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-9 下午3:10:56
	 */
	List<ChangePathEntity> queryTogetherTruckChangePath(String origOrgCode, String origGoodsAreaCode);
}