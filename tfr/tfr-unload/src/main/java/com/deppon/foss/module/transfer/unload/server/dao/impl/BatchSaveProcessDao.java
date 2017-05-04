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
 *  PROJECT NAME  : tfr-stockchecking-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/api/server/dao/IStTaskDao.java
 *  
 *  FILE NAME          :IStTaskDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-unload
 * PACKAGE NAME: com.deppon.foss.module.transfer.unload.server.dao.impl
 * FILE    NAME: BatchSaveUtil.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.unload.server.dao.impl;

import java.util.List;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.unload.api.server.dao.IBatchSaveProcessDao;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
import com.deppon.foss.module.transfer.unload.api.shared.dto.AssignUnloadTaskDto;

/**
 * 控制批量新增、修改条数
 * @author dp-duyi
 * @date 2013-3-25 上午9:33:01
 */
public class BatchSaveProcessDao extends iBatis3DaoImpl  implements IBatchSaveProcessDao{

	/** 
	 * 控制批量新增、修改条数
	 * @author dp-duyi
	 * @date 2013-3-25 上午9:43:24
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IBatchSaveProcessDao#batchSaveProcess(java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public int batchSaveProcess(String saveType, String saveId,
			List<?> data) {
		int start = 0;
		int end;
		if(start+UnloadConstants.BATCH_COUNT<data.size()){
			end = start+UnloadConstants.BATCH_COUNT;
		}else{
			end = data.size();
		}
		while(start < data.size()){
			if(UnloadConstants.BATCH_SAVE_TYPE_INSERT.equals(saveType)){
				this.getSqlSession().insert(saveId, data.subList(start, end));
			}else{
				this.getSqlSession().update(saveId, data.subList(start, end));
			}
			start = end;
			if(start+UnloadConstants.BATCH_COUNT<data.size()){
				end = start+UnloadConstants.BATCH_COUNT;
			}else{
				end = data.size();
			}
		}
		return data.size();
	}

	/**
	* @description 批量更新快递交接单状态
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.unload.api.server.dao.IBatchSaveProcessDao#batchUpdateProcess(java.lang.String, java.lang.String, java.util.List)
	* @author 328768-foss-gaojianfu
	* @update 2016年6月15日 下午7:08:42
	* @version V1.0
	 */
	@Override
	public int batchUpdateProcess(String batchUpdateType, String updateId, List<AssignUnloadTaskDto> tasks) {
		int start = 0;
		int end;
		if(start+UnloadConstants.BATCH_COUNT<tasks.size()){
			end = start+UnloadConstants.BATCH_COUNT;
		}else{
			end = tasks.size();
		}
		while(start < tasks.size()){
			this.getSqlSession().update(updateId, tasks.subList(start, end));
			start = end;
			if(start+UnloadConstants.BATCH_COUNT<tasks.size()){
				end = start+UnloadConstants.BATCH_COUNT;
			}else{
				end = tasks.size();
			}
		}
		return tasks.size();
	}

}
