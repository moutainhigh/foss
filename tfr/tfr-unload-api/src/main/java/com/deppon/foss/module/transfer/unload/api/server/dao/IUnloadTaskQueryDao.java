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
 *  PROJECT NAME  : tfr-unload-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/server/dao/IUnloadTaskQueryDao.java
 *  
 *  FILE NAME          :IUnloadTaskQueryDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.unload.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadSerialNoDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadTaskDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadWaybillDetailDto;

/**
 * 卸车任务查询Dao
 * @author ibm-zhangyixin
 * @date 2012-11-29 上午10:31:57
 */
public interface IUnloadTaskQueryDao {

	/**
	 * 查询卸车任务(分页)
	 * @author ibm-zhangyixin
	 * @date 2012-12-6 上午9:50:39
	 */
	List<UnloadTaskDto> queryUnloadTask(UnloadTaskDto unloadTaskDto, int limit,
			int start);

	/**
	 * 查询卸车任务总数
	 * @author ibm-zhangyixin
	 * @date 2012-12-6 上午9:50:54
	 */
	Long getTotCount(UnloadTaskDto unloadTaskDto);

	/**
	 * 根据卸车任务编号获取单条卸车任务
	 * @author ibm-zhangyixin
	 * @date 2012-12-7 上午11:26:06
	 */
	UnloadTaskDto getUnloadTaskByUnloadTaskNo(String unloadTaskNo);

	/**
	 * 根据卸车任务编号获取卸车明细
	 * 	交接件数:</br>
			如果单据类型为交接单, 取该单据下 交接单明细表中sum(已配件数)</br>
			如果为配载单, 根据配载单找到该配载单下所有交接单, 再根据交接单找交接明细下sum(已配件数)</br>
			如果为接货任务, 转货交接单, 派送拉回货 则取交接表(pkp.t_srv_stay_handover) 下所有的件数</br>
		件数扫描率:</br>
			已扫描件数/交接件数</br>
		交接重量</br>
			如果单据类型为交接单, 取该单据下交接单明细表中sum(已配重量)</br>
			如果为配载单, 根据该配载单好找到该配置单下所有交接单, 再根据交接单找交接单明细下sum(已配重量)</br>
			如果为派送拉回货, 交接单开单重量*交接件数(交接件数获取方式同上)/开单件数</br>
			如果为转货交接单, 取该单据下交接单明细表中sum(已配重量)</br>
			如果为接回货, 取交接单中开单重量</br>
		交接体积</br>
			如果单据类型为交接单, 取该单据下交接单明细表中sum(已配体积)</br>
			如果为配载单, 根据该配载单好找到该配置单下所有交接单, 再根据交接单找交接单明细下sum(已配体积)</br>
			如果为派送拉回货, 交接单开单体积*交接件数(交接件数获取方式同上)/开单件数</br>
			如果为转货交接单, 取该单据下交接单明细表中sum(已配体积)</br>
			如果为接回货, 取交接单中开单体积</br>
	 * @author ibm-zhangyixin
	 * @date 2012-12-7 上午11:26:33
	 */
	List<UnloadWaybillDetailDto> queryUnloadTaskDetailByUnloadTaskNo(String unloadTaskNo);

	/**
	 * 根据unloadWaybillDetailId得到所有流水号
	 * @author ibm-zhangyixin
	 * @date 2012-12-17 下午6:43:44
	 */
	List<UnloadSerialNoDetailEntity> queryUnloadSerialNoByUnloadWaybillDetailId(
			String unloadWaybillDetailId);

	/**
	 * 根据taskID得到所有理货员
	 * 如果是手工卸车，卸车创建人不需要显示。
	 * 如果是PDA卸车，需要显示。
	 * T_OPT_LOADER_PARTICIPATION这个表中有be_creator字段，Y为创建者，N为参与者
	 * @author ibm-zhangyixin
	 * @date 2012-12-17 下午6:43:44
	 */
	List<LoaderParticipationEntity> queryLoaderByTaskId(String unloadTaskId,
			String unloadWay);

	/** 
	 * @Title: queryUnloadTask 
	 * @Description: 查询卸车任务(不分页)
	 * @param unloadTaskDto
	 * @return    
	 * @return List<UnloadTaskDto>    返回类型 
	 * queryUnloadTask
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-5-29上午11:06:49
	 */ 
	List<UnloadTaskDto> queryUnloadTask(UnloadTaskDto unloadTaskDto);

	/** 
	 * @Title: queryExportUnloadTaskDetailByUnloadTaskNo 
	 * @Description: 根据卸车任务编号获取卸车明细
	 * 	交接件数:</br>
			如果单据类型为交接单, 取该单据下 交接单明细表中sum(已配件数)</br>
			如果为配载单, 根据配载单找到该配载单下所有交接单, 再根据交接单找交接明细下sum(已配件数)</br>
			如果为接货任务, 转货交接单, 派送拉回货 则取交接表(pkp.t_srv_stay_handover) 下所有的件数</br>
		件数扫描率:</br>
			已扫描件数/交接件数</br>
		交接重量</br>
			如果单据类型为交接单, 取该单据下交接单明细表中sum(已配重量)</br>
			如果为配载单, 根据该配载单好找到该配置单下所有交接单, 再根据交接单找交接单明细下sum(已配重量)</br>
			如果为派送拉回货, 交接单开单重量*交接件数(交接件数获取方式同上)/开单件数</br>
			如果为转货交接单, 取该单据下交接单明细表中sum(已配重量)</br>
			如果为接回货, 取交接单中开单重量</br>
		交接体积</br>
			如果单据类型为交接单, 取该单据下交接单明细表中sum(已配体积)</br>
			如果为配载单, 根据该配载单好找到该配置单下所有交接单, 再根据交接单找交接单明细下sum(已配体积)</br>
			如果为派送拉回货, 交接单开单体积*交接件数(交接件数获取方式同上)/开单件数</br>
			如果为转货交接单, 取该单据下交接单明细表中sum(已配体积)</br>
			如果为接回货, 取交接单中开单体积</br>
	 * @param taskNo
	 * @return    
	 * @return List<UnloadWaybillDetailDto>    返回类型 
	 * queryExportUnloadTaskDetailByUnloadTaskNo
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-6-24下午7:43:34
	 */ 
	List<UnloadWaybillDetailDto> queryExportUnloadTaskDetailByUnloadTaskNo(
			String taskNo);

	/**
	 * 根据商务专递交接单号获取路线
	 * @author 272681 chenlei
	 * @date 2015/9/10
	 */
	String queryBusinessLine(String unloadTaskNo);

	List<UnloadWaybillDetailDto> queryUnloadTaskDetailByUnloadTaskNoExpress(
			String unloadTaskNo);
	
}