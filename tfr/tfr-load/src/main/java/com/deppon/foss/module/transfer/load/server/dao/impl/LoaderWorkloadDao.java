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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/server/dao/impl/LoaderWorkloadDao.java
 *  
 *  FILE NAME          :LoaderWorkloadDao.java
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

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.load.api.server.dao.ILoaderWorkloadDao;
import com.deppon.foss.module.transfer.load.api.shared.dto.ErrorVolumeDeductionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoaderWorkloadDetailDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoaderWorkloadDto;

/**
 * 查询装卸车工作量dao
 * @author ibm-zhangyixin
 * @date 2012-11-28 下午4:24:44
 */
public class LoaderWorkloadDao extends iBatis3DaoImpl implements ILoaderWorkloadDao {
	private static final String NAMESPACE = "foss.load.loaderworkload.";

	/**
	 * 个人统计tab 查询方法
	 * @author ibm-zhangyixin
	 * @date 2012-12-17 上午10:51:04
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LoaderWorkloadDto> queryPersonCount(
			LoaderWorkloadDto loaderWorkloadDto, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryPersonCount", loaderWorkloadDto, rowBounds);
	}
	
	/**
	 * 个人统计tab(快递) 查询方法
	 * @author ibm-zhangyixin
	 * @date 2012-12-17 上午10:51:04
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LoaderWorkloadDto> queryPersonCountExpress(
			LoaderWorkloadDto loaderWorkloadDto, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryPersonCountExpress", loaderWorkloadDto, rowBounds);
	}

	/**
	 * 个人统计tab 获取查询的总记录数
	 * @author ibm-zhangyixin
	 * @date 2012-12-17 上午10:54:28
	 */
	@Override
	public Long getTotCountPersonCount(LoaderWorkloadDto loaderWorkloadDto) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "getTotCountPersonCount", loaderWorkloadDto);
	}
	
	
	@Override
	public Long getTotCountPersonCountExpress(
			LoaderWorkloadDto loaderWorkloadDto) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "getTotCountPersonCountExpress", loaderWorkloadDto);
	}

	/**
	 * 队统计tab 查询方法
	 * @author ibm-zhangyixin
	 * @date 2012-12-18 下午4:41:17
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LoaderWorkloadDto> queryTeamCount(
			LoaderWorkloadDto loaderWorkloadDto, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryTeamCount", loaderWorkloadDto, rowBounds);
	}
	
	/**
	 * 队统计tab 查询方法
	 * @author zhangpeng
	 * @date 2015-12-10下午4:41:17
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LoaderWorkloadDto> queryTeamCountExpress(
			LoaderWorkloadDto loaderWorkloadDto, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryTeamCountExpress", loaderWorkloadDto, rowBounds);
	}

	/**
	 * 队统计tab 获取查询的总记录数
	 * @author ibm-zhangyixin
	 * @date 2012-12-18 下午4:41:30
	 */
	@Override
	public Long getTotCountTeamCount(LoaderWorkloadDto loaderWorkloadDto) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "getTotCountTeamCount", loaderWorkloadDto);
	}

	/**
	 * 个人统计tab 查询方法_查询出所有的(无分页)
	 * @author ibm-zhangyixin
	 * @date 2012-12-27 下午2:20:11
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LoaderWorkloadDto> queryPersonCount(
			LoaderWorkloadDto loaderWorkloadDto) {
		return this.getSqlSession().selectList(NAMESPACE + "queryPersonCount", loaderWorkloadDto);
	}

	/**
	 * 队统计tab 查询方法_查询出所有(无分页)
	 * @author ibm-zhangyixin
	 * @date 2012-12-27 下午2:40:52
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LoaderWorkloadDto> queryTeamCount(
			LoaderWorkloadDto loaderWorkloadDto) {
		return this.getSqlSession().selectList(NAMESPACE + "queryTeamCount", loaderWorkloadDto);
	}

	/**
	 * 个人统计查询合计
	 * @author ibm-zhangyixin
	 * @date 2012-12-27 下午3:40:40
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ILoaderWorkloadDao#queryPersonCountSummary(com.deppon.foss.module.transfer.load.api.shared.dto.LoaderWorkloadDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LoaderWorkloadDto> queryPersonCountSummary(
			LoaderWorkloadDto loaderWorkloadDto) {
		return this.getSqlSession().selectList(NAMESPACE + "queryPersonCountSummary", loaderWorkloadDto);
	}
	
	/**
	 * 个人统计查询合计
	 * @author ibm-zhangyixin
	 * @date 2012-12-27 下午3:40:40
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ILoaderWorkloadDao#queryPersonCountSummary(com.deppon.foss.module.transfer.load.api.shared.dto.LoaderWorkloadDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LoaderWorkloadDto> queryPersonCountSummaryExpress(
			LoaderWorkloadDto loaderWorkloadDto) {
		return this.getSqlSession().selectList(NAMESPACE + "queryPersonCountSummaryExpress", loaderWorkloadDto);
	}

	/**
	 * 队统计查询合计
	 * @author ibm-zhangyixin
	 * @date 2012-12-27 下午4:16:28
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ILoaderWorkloadDao#queryTeamCountSummary(com.deppon.foss.module.transfer.load.api.shared.dto.LoaderWorkloadDto)
	 */
	@Override
	public LoaderWorkloadDto queryTeamCountSummary(
			LoaderWorkloadDto loaderWorkloadDto) {
		return (LoaderWorkloadDto) this.getSqlSession().selectOne(NAMESPACE + "queryTeamCountSummary", loaderWorkloadDto);
	}

	@Override
	public LoaderWorkloadDto queryTeamCountSummaryExpress(
			LoaderWorkloadDto loaderWorkloadDto) {
		return (LoaderWorkloadDto) this.getSqlSession().selectOne(NAMESPACE + "queryTeamCountSummaryExpress", loaderWorkloadDto);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<LoaderWorkloadDetailDto> queryReCreateWorkLoadLoadTask(
			String waybillNo) {
		
		return this.getSqlSession().selectList(NAMESPACE+"queryReCreateWorkLoadLoadTask",waybillNo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoaderWorkloadDetailDto> queryReCreateWorkLoadUnLoadTask(
			String waybillNo) {
		return this.getSqlSession().selectList(NAMESPACE+"queryReCreateWorkLoadUnLoadTask", waybillNo);
	}

	@Override
	public int deleteOldWorkLoadLoadTask(String taskNo) {
		return this.getSqlSession().delete(NAMESPACE+"deleteOldWorkLoadLoadTask", taskNo);

	}




	@Override
	public int addErrorVolumnDeduction(
			ErrorVolumeDeductionDto errorVolumeDeductionDto) {
		return this.getSqlSession().insert(NAMESPACE+"addErrorVolumnDeduction", errorVolumeDeductionDto);
	}
	/**
	 * 差错货量扣除tab查询
	 * @author 257220
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<ErrorVolumeDeductionDto> queryErrorVolumeDeductionList(
			ErrorVolumeDeductionDto errorVolumeDeductionDto, int limit,
			int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryErrorVolumeDeductionList", errorVolumeDeductionDto, rowBounds);
	}

	

	

}