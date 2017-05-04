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
 *  PROJECT NAME  : tfr-common-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/api/server/dao/ITfrJobProcessDao.java
 *  
 *  FILE NAME          :ITfrJobProcessDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 ******************************************************************************/
package com.deppon.foss.module.transfer.common.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.TfrJobProcessDto;

public interface ITfrJobProcessDao {

	/**
	 * 需要通过业务编号、线程号、线程数获取job执行记录
	 * 
	 * @author foss-wuyingjie
	 * 
	 * @date 2012-11-29 下午4:05:45
	 */
	TfrJobProcessEntity queryJobProcessByIndex(String bizCode, int threadNo, int threadCount);
	
	/**
	 * 需要通过业务编号、线程号、线程数获取job执行记录
	 * 
	 * @author foss-wuyingjie
	 * 
	 * @date 2012-11-29 下午4:05:45
	 */
	TfrJobProcessEntity queryJobProcessByIndexNoLock(String bizCode, int threadNo, int threadCount);

	/**
	 * 新增一条job执行记录
	 * @author foss-wuyingjie
	 * @date 2012-11-29 下午5:00:25
	 */
	void addJobProcessEntity(TfrJobProcessEntity jobProcess);

	/**
	 * 更新一条job执行记录
	 * @author foss-wuyingjie
	 * @date 2012-11-29 下午7:03:54
	 */
	void updateJobProcessEntity(TfrJobProcessEntity jobProcess);

	/**
	 * 按ID更新一条job执行记录，更新非空字段
	 *
	 * @param jobProcess
	 *
	 * @author foss-wuyingjie
	 * @date 2013-1-23 上午10:34:10
	 */
	void updateByPrimaryKeySelective(TfrJobProcessEntity jobProcess);

	/**
	 * 需要通过 ID(主键) 获取job执行记录
	 * @author foss-yuyongxiang
	 * @date 2013年5月13日 08:54:56
	 */
	TfrJobProcessEntity selectByPrimaryKey(String id);
	
	/**
	 * 需要通过 查询参数(时间...等) 获取job执行记录列表
	 * @author foss-yuyongxiang
	 * @date 2013年5月13日 08:54:56
	 */
	List<TfrJobProcessEntity> selectJobProcessList(TfrJobProcessDto jobProcess,int start,int limit);
	
	/**
	 * 需要通过 查询参数(时间...等) 获取job执行记录列表
	 * @author foss-yuyongxiang
	 * @date 2013年5月13日 08:54:56
	 */
	Long selectJobProcessCount(TfrJobProcessDto jobProcess);

}