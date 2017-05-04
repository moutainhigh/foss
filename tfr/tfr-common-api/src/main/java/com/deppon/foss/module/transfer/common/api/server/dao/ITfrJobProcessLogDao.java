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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/api/server/dao/ITfrJobProcessLogDao.java
 *  
 *  FILE NAME          :ITfrJobProcessLogDao.java
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
 *  
 *  
 *  
 ******************************************************************************/
package com.deppon.foss.module.transfer.common.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.TfrJobProcessLogDto;

public interface ITfrJobProcessLogDao {

	/**
	 * 新增一条job执行日志
	 * @author foss-wuyingjie
	 * @date 2012-11-29 下午8:06:47
	 * 
	 */
	void addJobProcessLog(TfrJobProcessLogEntity jobProcessLogEntity);
	
	/**
	 * 根据特定的参数查询TfrJobProcessLogDto集合
	 * @author foss-wuyingjie
	 * @date 2012-11-29 下午8:06:47
	 * 
	 */
	List<TfrJobProcessLogEntity> selectJobProcessLogList(TfrJobProcessLogDto jobProcessLogdDto,int start,int limit);
	
	/**
	 * 根据特定的参数查询count
	 * @author foss-yuyongxiang
	 * @date 2013年5月10日 12:00:52
	 * 
	 */
	Long selectJobProcessLogCount(TfrJobProcessLogDto jobProcessLogdDto);
	
	/**
	 * 根据ID查出一个对应的实体
	 * @author foss-wuyingjie
	 * @date 2012-11-29 下午8:06:47
	 * 
	 */
	TfrJobProcessLogEntity selectByPrimaryKey(String id);
	
	
	
	
}