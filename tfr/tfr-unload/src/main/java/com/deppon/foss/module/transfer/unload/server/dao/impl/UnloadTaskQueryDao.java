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
 *  PROJECT NAME  : tfr-unload
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/server/dao/impl/UnloadTaskQueryDao.java
 *  
 *  FILE NAME          :UnloadTaskQueryDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.unload.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
import com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadTaskQueryDao;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadSerialNoDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadTaskDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadWaybillDetailDto;
import com.deppon.foss.util.CollectionUtils;

/**
 * 查询卸车任务Dao
 * @author ibm-zhangyixin
 * @date 2012-11-29 上午10:33:44
 */
public class UnloadTaskQueryDao extends iBatis3DaoImpl implements IUnloadTaskQueryDao {
	private static final String NAMESPACE = "foss.unload.unloadtaskquery.";

	/**
	 * 查询卸车任务(分页)
	 * @author ibm-zhangyixin
	 * @date 2012-12-6 上午9:50:39
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UnloadTaskDto> queryUnloadTask(UnloadTaskDto unloadTaskDto,
			int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryUnloadTask", unloadTaskDto, rowBounds);
	}

	/**
	 * 查询卸车任务总数
	 * @author ibm-zhangyixin
	 * @date 2012-12-6 上午9:50:54
	 */
	@Override
	public Long getTotCount(UnloadTaskDto unloadTaskDto) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "getTotCount", unloadTaskDto);
	}

	/**  零担
	 * 根据卸车任务编号获取单条卸车任务
	 * @author ibm-zhangyixin
	 * @date 2012-12-7 上午11:26:06
	 */
	@Override
	public UnloadTaskDto getUnloadTaskByUnloadTaskNo(String unloadTaskNo) {
		@SuppressWarnings("unchecked")
		List<UnloadTaskDto> unloadTaskDtos = this.getSqlSession().selectList(NAMESPACE + "getUnloadTaskByUnloadTaskNo", unloadTaskNo);
		return CollectionUtils.isEmpty(unloadTaskDtos) ? new UnloadTaskDto() : unloadTaskDtos.get(0);
	}
	

	/**
	 *  零担
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
	@SuppressWarnings("unchecked")
	@Override
	public List<UnloadWaybillDetailDto> queryUnloadTaskDetailByUnloadTaskNo(
			String unloadTaskNo) {
		return this.getSqlSession().selectList(NAMESPACE + "queryUnloadTaskDetailByUnloadTaskNo", unloadTaskNo);
	}
	
	/**
	 *  快递
	 * 根据卸车任务编号获取卸车明细
	 * @author ibm-zhangyixin
	 * @date 2012-12-7 上午11:26:33
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UnloadWaybillDetailDto> queryUnloadTaskDetailByUnloadTaskNoExpress(
			String unloadTaskNo) {
		return this.getSqlSession().selectList(NAMESPACE + "queryUnloadTaskDetailByUnloadTaskNoExpress", unloadTaskNo);
	}

	/**
	 * 根据unloadWaybillDetailId得到所有流水号
	 * @author ibm-zhangyixin
	 * @date 2012-12-17 下午6:43:44
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UnloadSerialNoDetailEntity> queryUnloadSerialNoByUnloadWaybillDetailId(
			String unloadWaybillDetailId) {
		//根据unloadWaybillDetailId得到所有流水号
		return this.getSqlSession().selectList(NAMESPACE + "queryUnloadSerialNoByUnloadWaybillDetailId", unloadWaybillDetailId);
	}

	/**
	 * 根据taskID得到所有理货员
	 * 如果是手工卸车，卸车创建人不需要显示。
	 * 如果是PDA卸车，需要显示。
	 * T_OPT_LOADER_PARTICIPATION这个表中有be_creator字段，Y为创建者，N为参与者
	 * @author ibm-zhangyixin
	 * @date 2012-12-17 下午6:43:44
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LoaderParticipationEntity> queryLoaderByTaskId(
			String unloadTaskId, String unloadWay) {
		Map<String, String> map = new HashMap<String, String>(2);
		map.put("unloadTaskId", unloadTaskId);
		//卸车方式
		map.put("unloadWay", unloadWay);
		return this.getSqlSession().selectList(NAMESPACE + "queryLoaderByTaskId", map);
	}

	/** 
	 * @Title: queryUnloadTask 
	 * @Description: 查询卸车任务(不分页)
	 * @param unloadTaskDto
	 * @return    
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadTaskQueryDao#queryUnloadTask(com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadTaskDto)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-5-29上午11:07:09
	 */ 
	@SuppressWarnings("unchecked")
	@Override
	public List<UnloadTaskDto> queryUnloadTask(UnloadTaskDto unloadTaskDto) {
		return this.getSqlSession().selectList(NAMESPACE + "queryUnloadTask", unloadTaskDto);
	}

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
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadTaskQueryDao#queryExportUnloadTaskDetailByUnloadTaskNo(java.lang.String)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-6-24下午7:43:58
	 */ 
	@SuppressWarnings("unchecked")
	@Override
	public List<UnloadWaybillDetailDto> queryExportUnloadTaskDetailByUnloadTaskNo(
			String taskNo) {
		return this.getSqlSession().selectList(NAMESPACE + "queryExportUnloadTaskDetailByUnloadTaskNo", taskNo);
	}
	
	/**
	 * 根据商务专递交接单号获取路线
	 * @author 272681 chenlei
	 * @date 2015/9/10
	 */
	public String queryBusinessLine(String unloadTaskNo){
		
		return (String) this.getSqlSession().selectOne(NAMESPACE + "queryBusinessLine",unloadTaskNo);
		
	}
}