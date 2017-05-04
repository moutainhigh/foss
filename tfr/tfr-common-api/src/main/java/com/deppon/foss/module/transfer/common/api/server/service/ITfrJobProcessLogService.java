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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/api/server/service/ITfrJobProcessLogService.java
 *  
 *  FILE NAME          :ITfrJobProcessLogService.java
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
 ******************************************************************************/
package com.deppon.foss.module.transfer.common.api.server.service;

import java.util.List;

import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.common.api.shared.vo.TfrJobProcessLogVo;

/**
 * <pre>
 * JOB日志查询
 * 
 * </pre>
 * @author foss-yuyongxiang
 * @date 2013年5月2日 10:05:28
 */
public interface ITfrJobProcessLogService {

	/**
	 * <pre>
	 *	通过传入的参数查询Job日志表
     * </pre>
	 * @param TfrJobProcessLogVo -> 查询参数例: 最大查询条数,起始条数;
	 * @return list -> 日志表集合
	 * @author foss-yuyongxiang
	 * @date 2013年5月2日 10:20:03
	 */
	public List<TfrJobProcessLogEntity> getJobProcessLogsList(TfrJobProcessLogVo vo,int start,int limit);
	
	/**
	 * <pre>
	 *	通过传入的参数查出的数据条数
	 * </pre>
	 * @param TfrJobProcessLogVo -> 查询参数例
	 * @return count -> 数据只和
	 * @author foss-yuyongxiang
	 * @date 2013年5月2日 10:20:03
	 */
	public Long getJobProcessLogscount(TfrJobProcessLogVo vo);
	/**
	 *<pre>
	 *	根据传入的ID查出对应的实体
     * </pre>
	 * @param vo
	 * @return TfrJobProcessLogEntity
	 * @author foss-yuyongxiang
	 * @date 2013年5月6日 14:21:41
	 */
	public TfrJobProcessLogEntity getJobProcessLogByID(TfrJobProcessLogVo vo);
	
}
