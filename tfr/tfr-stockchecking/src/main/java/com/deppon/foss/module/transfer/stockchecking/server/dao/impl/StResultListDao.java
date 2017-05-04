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
 *  PROJECT NAME  : tfr-stockchecking
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/server/dao/impl/StResultListDao.java
 *  
 *  FILE NAME          :StResultListDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.stockchecking.server.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.dto.ScanDetailDto;
import com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStResultListDao;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StResultListEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StTaskEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StTaskListEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.PcakageWayBillDto;
import com.deppon.foss.util.UUIDUtils;

/**
 * 处理清仓结果相关的crud操作
 * @author foss-wuyingjie
 * @date 2012-12-25 上午10:43:44
 */
public class StResultListDao extends iBatis3DaoImpl implements IStResultListDao {

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:43:48
	 * @function 批量新增扫描结果
	 * @param stResultListEntityList
	 * @return void
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStResultListDao#addStResultListBatch(java.util.List)
	 */
	@Override
	public void addStResultListBatch(List<StResultListEntity> stResultListEntityList) {
		if(CollectionUtils.isNotEmpty(stResultListEntityList)){
			this.getSqlSession().insert("foss.tfr.StResultListDao.addStResultListBatch", stResultListEntityList);
		}
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:43:53
	 * @funciton 根据任务id和运单号查询扫描明细
	 * @param stTaskId
	 * @param waybillNo
	 * @return List<ScanDetailDto>
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStResultListDao#queryScanDetailInStTask(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ScanDetailDto> queryScanDetailInStTask(String stTaskId,	String waybillNo) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("stTaskId", stTaskId);
		params.put("waybillNo", waybillNo);
		
		return this.getSqlSession().selectList("foss.tfr.StResultListDao.queryScanDetailInStTask", params);
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:43:59
	 * @function 根据任务id和货物状态查询任务列表
	 * @param stTaskId
	 * @param goodsStatus
	 * @return List<StTaskListEntity>
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStResultListDao#queryGapList(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StTaskListEntity> queryGapList(String stTaskId, String goodsStatus) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("stTaskId", stTaskId);
		params.put("goodsStatus", goodsStatus);
		
		List<StTaskListEntity> list = this.getSqlSession().selectList("foss.tfr.StResultListDao.queryGapList", params);
		
		if(CollectionUtils.isEmpty(list)){
			return new ArrayList<StTaskListEntity>();
		}else{
			return list;
		}
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:44:07
	 * @function 查询扫描列表总数
	 * @param stTaskId
	 * @param waybillNo
	 * @param serialNo
	 * @return long
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStResultListDao#queryStResultListCount(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public long queryStResultListCount(String stTaskId, String waybillNo, String serialNo) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("stTaskId", stTaskId);
		params.put("waybillNo", waybillNo);
		params.put("serialNo", serialNo);
		
		return (Long) this.getSqlSession().selectOne("foss.tfr.StResultListDao.queryStResultListCount", params);
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:44:11
	 * @function 删除扫描明细列表
	 * @param stResultListEntity
	 * @return
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStResultListDao#deleteStResultListEntity(com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StResultListEntity)
	 */
	@Override
	public void deleteStResultListEntity(StResultListEntity stResultListEntity) {
		String id = stResultListEntity.getId();
		this.getSqlSession().delete("foss.tfr.StResultListDao.deleteByPrimaryKey", id);
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:44:15
	 * @function 更新扫描明细
	 * @param stResultListEntity
	 * @return
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStResultListDao#updateByPrimaryKeySelective(com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StResultListEntity)
	 */
	@Override
	public void updateByPrimaryKeySelective(StResultListEntity stResultListEntity) {
		this.getSqlSession().delete("foss.tfr.StResultListDao.updateByPrimaryKeySelective", stResultListEntity);
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:44:19
	 * @function 新增扫描明细
	 * @param stResultListEntity
	 * @return
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStResultListDao#addStResultListEntity(com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StResultListEntity)
	 */
	@Override
	public void addStResultListEntity(StResultListEntity stResultListEntity) {
		stResultListEntity.setId(UUIDUtils.getUUID());
		this.getSqlSession().insert("foss.tfr.StResultListDao.addStResultListEntity", stResultListEntity);
	}

	/** 
	 * 通过清仓任务ID、运单号、流水号，获取此扫描明细结果ID
	 * @author foss-wuyingjie
	 * @date 2013-1-11 下午4:42:44
	 * @param id
	 * @param waybillNo
	 * @param serialNo
	 * @return StResultListEntity
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStResultListDao#queryStResultEntity(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public StResultListEntity queryStResultEntity(String id, String waybillNo, String serialNo) {
		StResultListEntity stResultListEntity = new StResultListEntity();
		stResultListEntity.setStTaskId(id);
		stResultListEntity.setWaybillNo(waybillNo);
		stResultListEntity.setSerialNo(serialNo);
		
		List<StResultListEntity> resultList = this.getSqlSession().selectList("foss.tfr.StResultListDao.queryStResultEntity", stResultListEntity);
		
		if(CollectionUtils.isNotEmpty(resultList)){
			return resultList.get(0);
		}else{
			return stResultListEntity;
		}
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2013-3-26 下午3:34:50
	 * @function 查询已扫描的总数
	 * @param stTaskNo
	 * @param waybillNo
	 * @param scanDoneStatus
	 * @return int
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStResultListDao#queryScanDoneCountByWaybillNo(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public int queryScanDoneCountByWaybillNo(String stTaskNo, String waybillNo, String scanDoneStatus) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("stTaskNo", stTaskNo);
		params.put("waybillNo", waybillNo);
		params.put("scanDoneStatus", scanDoneStatus);
		
		return (Integer) this.getSqlSession().selectOne("foss.tfr.StResultListDao.queryScanDoneCountByWaybillNo", params);
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2013-3-26 下午3:34:45
	 * @function 查询任务中某单号已扫描件数
	 * @param stTaskId
	 * @param waybillNo
	 * @return long
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStResultListDao#queryFinishedScanCountInTask(java.lang.String, java.lang.String)
	 */
	@Override
	public Long queryFinishedScanCountInTask(String stTaskId, String waybillNo) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("stTaskId", stTaskId);
		String[] scanStatusArray = {TransferConstants.SCAN_DONE, TransferConstants.SCAN_MANUAL};
		paramsMap.put("scanStatusArray", scanStatusArray);
		paramsMap.put("waybillNo", waybillNo);
		
		return (Long) this.getSqlSession().selectOne("foss.tfr.StResultListDao.queryFinishedScanCountInTask", paramsMap);
	}

	/**
	 * @author niuly
	 * @date 2013-6-20 17:29:43
	 * @function 查询多货是否有入库记录
	 * @param map
	 * @return int
	 */
	@Override
	public int queryInStockNumForLack(Map<String, Object> map) {
		return (Integer) this.getSqlSession().selectOne("foss.tfr.StResultListDao.queryInStockNumForLack", map);
	}
	/**
	 * @author niuly
	 * @date 2013-6-20 17:29:43
	 * @function 查询少货是否有出库记录
	 * @param map
	 * @return int
	 */
	@Override
	public int queryOutStockNumForSurplus(Map<String, Object> map) {
		return (Integer) this.getSqlSession().selectOne("foss.tfr.StResultListDao.queryOutStockNumForSurplus", map);
	}
	/**
	 * @author niuly
	 * @date 2013-7-16 19:32:56
	 * @function 根据任务id查询扫描明细
	 * @param stTaskId
	 * @return List<StResultListEntity>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StResultListEntity> queryResultListByTaskId(String stTaskId) {
		return this.getSqlSession().selectList("foss.tfr.StResultListDao.selectStResultListByTaskId", stTaskId);
	}

	/**
	 * @author niuly
	 * @date 2013-08-15 15:04:23
	 * @function 查询某件少货在差异报告生成时间至上报OA前是否被正常扫描
	 * @param waybillNo
	 * @param serialNo
	 * @param deptcode
	 * @param handleTime
	 * @param oaReportHourRule
	 * @return List<StTaskEntity>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StTaskEntity> querySecScanDetail(String waybillNo,String serialNo, String deptCode, Date handleTime,int oaReportHourRule) {
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("waybillNo", waybillNo);
		params.put("serialNo", serialNo);
		params.put("deptCode", deptCode);
		params.put("handleTime", handleTime);
		params.put("oaReportHourRule", oaReportHourRule);
		
		return this.getSqlSession().selectList("foss.tfr.StResultListDao.querySecScanDetail", params);
	}
	/**
	 * 下拉包的运单明细
	 * @author zhuyunrong
	 * @date 2015-03-31 09：17
	 * @param Strng packageNo
	 * @return List<PcakageWayBillDto> (运单和流水号)
	 * **/
	@Override
	public List<PcakageWayBillDto> queryPackageDetail(String packageNo){
		return this.getSqlSession().selectList("foss.tfr.StResultListDao.queryPackageDetail", packageNo);
	}
	
	/**
	 * 根据运单号查询
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String querySalesDeptDelivery(String waybillNo) {
		List<String> wayStaus = this.getSqlSession().selectList("foss.tfr.StResultListDao.querySalesDeptDelivery", waybillNo);
		if(CollectionUtils.isNotEmpty(wayStaus)){
			return wayStaus.get(0);
		}else{
			return null;
		}
	}
}