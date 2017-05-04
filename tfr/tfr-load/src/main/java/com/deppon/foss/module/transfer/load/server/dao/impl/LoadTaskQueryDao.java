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
 *  PROJECT NAME  : tfr-load
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/server/dao/impl/LoadTaskQueryDao.java
 *  
 *  FILE NAME          :LoadTaskQueryDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.server.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.load.api.server.dao.ILoadTaskQueryDao;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportWayBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadSerialNoEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoadTaskDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoadWayBillDetailDto;

/**
 * 查询装车任务Dao
 * @author ibm-zhangyixin
 * @date 2012-11-29 上午10:28:08
 */
public class LoadTaskQueryDao extends iBatis3DaoImpl implements ILoadTaskQueryDao {
	private static final String NAMESPACE = "foss.load.loadtask.";
	
	/**
	 * 根据输入的参数查询出装车任务(分页)
	 * @author ibm-zhangyixin
	 * @date 2012-11-29 上午10:28:24
	 */
	@SuppressWarnings("unchecked")
	public List<LoadTaskDto> queryLoadTask(LoadTaskDto loadTaskDto, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryLoadTaskDto", loadTaskDto, rowBounds);
	}

	/**
	 * 查询出总记录数
	 * @author ibm-zhangyixin
	 * @date 2012-11-29 上午10:28:43
	 */
	public Long getTotCount(LoadTaskDto loadTaskDto) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "getTotCount", loadTaskDto);
	}

	/**
	 * 根据装车任务ID 查询出所有理货员
	 * @author ibm-zhangyixin
	 * @date 2012-11-29 上午10:28:54
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LoaderParticipationEntity> queryLoaderByTaskId(String taskId) {
		return this.getSqlSession().selectList(NAMESPACE + "queryLoaderByTaskId", taskId);
	}

	/**
	 * 根据装车任务编号查询出所有的装车运单明细
	 * @author ibm-zhangyixin
	 * @date 2012-11-29 上午10:29:14
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LoadWayBillDetailDto> queryLoadWayBillByTaskNo(String taskNo) {
		return this.getSqlSession().selectList(NAMESPACE + "queryLoadWayBillByTaskNo", taskNo);
	}

	/**
	 * 根据任务No得到装车任务
	 * @author ibm-zhangyixin
	 * @date 2012-11-29 上午10:29:35
	 */
	@SuppressWarnings("unchecked")
	@Override
	public LoadTaskDto getLoadTaskByTaskNo(String taskNo) {
		List<LoadTaskDto> loadTaskDtos = this.getSqlSession().selectList(NAMESPACE + "getLoadTaskByTaskNo", taskNo);
		if(loadTaskDtos.size() > 0) {
			return loadTaskDtos.get(0);
		}
		return null;
	}

	/**
	 * 根据loadWaybillDetailId得到所有流水号
	 * @author ibm-zhangyixin
	 * @date 2012-11-29 上午10:29:57
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LoadSerialNoEntity> queryloadSerialNoByLoadWaybillDetailId(
			String loadWaybillDetailId) {
		return this.getSqlSession().selectList(NAMESPACE + "queryloadSerialNoByLoadWaybillDetailId", loadWaybillDetailId);
	}

	/**
	 * 根据配送装车No获取配送装车Id
	 * @author ibm-zhangyixin
	 * @date 2012-12-3 下午5:18:11
	 */
	@Override
	public String queryLoadGaprepIdByGaprepNo(String gaprepNo) {
		return (String) this.getSqlSession().selectOne(NAMESPACE + "queryLoadGaprepIdByGaprepNo", gaprepNo);
	}

	/**
	 * 根据派送装车差异报告ID查询出派送装车数据
	 * @author ibm-zhangyixin
	 * @date 2012-12-4 上午11:11:06
	 */
	@Override
	public DeliverLoadGapReportEntity queryLoadGaprepReport(String loadGaprepId) {
		return (DeliverLoadGapReportEntity) this.getSqlSession().selectOne(NAMESPACE + "queryLoadGaprepReport", loadGaprepId);
	}

	/**
	 * 根据派送装车差异报告ID查询出运单数据
	 * @author ibm-zhangyixin
	 * @date 2012-12-4 上午11:11:32
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DeliverLoadGapReportWayBillEntity> queryDeliverLoadGapReportWayBills(
			String loadGaprepId) {
		return this.getSqlSession().selectList(NAMESPACE + "queryDeliverLoadGapReportWayBills", loadGaprepId);
	}

	/** 
	 * @Title: queryLoadTask 
	 * @Description: 根据输入的参数查询出装车任务(不分页)
	 * @param loadTaskDto
	 * @return    
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ILoadTaskQueryDao#queryLoadTask(com.deppon.foss.module.transfer.load.api.shared.dto.LoadTaskDto)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-5-29上午10:01:56
	 */ 
	@SuppressWarnings("unchecked")
	@Override
	public List<LoadTaskDto> queryLoadTask(LoadTaskDto loadTaskDto) {
		return this.getSqlSession().selectList(NAMESPACE + "queryLoadTaskDto", loadTaskDto);
	}

	/** 
	 * @Title: queryExportLoadWayBillByTaskNo 
	 * @Description: 根据装车任务编号查询出所有的装车运单明细
	 * @param taskNo
	 * @return    
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ILoadTaskQueryDao#queryExportLoadWayBillByTaskNo(java.lang.String)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-6-24下午7:52:24
	 */ 
	@SuppressWarnings("unchecked")
	@Override
	public List<LoadWayBillDetailDto> queryExportLoadWayBillByTaskNo(
			String taskNo) {
		return this.getSqlSession().selectList(NAMESPACE + "queryExportLoadWayBillByTaskNo", taskNo);
	}

	/**
	 * 
	 * 提供方法给清仓，查询给定时间前的装车扫描状态
	 * @author alfred
	 * @date 2013-9-6 上午9:29:54
	 * @param loadSerialNo
	 * @return 
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ILoadTaskQueryDao#queryLoadScanState(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LoadSerialNoEntity> queryLoadScanState(
			Map<String, Object> loadSerialNo) {
		return this.getSqlSession().selectList(NAMESPACE + "queryLoadScanState", loadSerialNo);
	}
	/**
	 * @author nly
	 * @date 2015年5月16日 下午5:53:24
	 * @function 查询落地配装车信息
	 * @param condition
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LoadSerialNoEntity> queryLdpLoadScanInfo(Map<String, String> condition) {
		return this.getSqlSession().selectList(NAMESPACE + "queryLdpLoadScanInfo", condition);
	}
	
}