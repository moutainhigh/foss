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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/api/server/dao/IStResultListDao.java
 *  
 *  FILE NAME          :IStResultListDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.stockchecking.api.server.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.common.api.shared.dto.ScanDetailDto;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StResultListEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StTaskEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StTaskListEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.PcakageWayBillDto;

/**
 * 处理清仓结果相关的crud操作
 * @author foss-wuyingjie
 * @date 2012-11-13 下午5:18:44
 */
public interface IStResultListDao {

	/**
	 * 批量新增清仓处理结果
	 * @author foss-wuyingjie
	 * @date 2012-11-13 下午5:27:35
	 * @param stResultListEntityList
	 * @return
	 */
	void addStResultListBatch(List<StResultListEntity> stResultListEntityList);

	/**
	 * 通过清仓任务ID以及运单号查询扫描明细
	 * @author foss-wuyingjie
	 * @date 2012-11-14 下午7:59:25
	 * @param stTaskId
	 * @param waybillNo
	 * @return List<ScanDetailDto>
	 */
	List<ScanDetailDto> queryScanDetailInStTask(String stTaskId, String waybillNo);
	/**
	 * 通过清仓任务ID及清仓结果状态，获取差异结果
	 * @author foss-wuyingjie
	 * @date 2012-11-30 下午2:38:35
	 * @param stTaskId
	 * @param goodsStatus
	 * @return List<StTaskListEntity>
	 */
	List<StTaskListEntity> queryGapList(String stTaskId, String goodsStatus);

	/**
	 * 通过清仓任务ID、运单号、序列号获取历史扫描条数
	 *
	 * @param stTaskId
	 * @param waybillNo
	 * @param serialNo
	 * @return
	 *
	 * @author foss-wuyingjie
	 * @date 2012-12-18 上午10:52:46
	 */
	long queryStResultListCount(String stTaskId, String waybillNo, String serialNo);

	/**
	 * 删除某清仓任务扫描结果数据
	 *
	 * @param stResultListEntity
	 *
	 * @author foss-wuyingjie
	 * @date 2012-12-18 上午11:45:47
	 */
	void deleteStResultListEntity(StResultListEntity stResultListEntity);

	/**
	 * 按照非空字段，更新清仓任务扫描结果记录信息
	 *
	 * @param stResultListEntity
	 *
	 * @author foss-wuyingjie
	 * @date 2012-12-18 上午11:45:02
	 */
	void updateByPrimaryKeySelective(StResultListEntity stResultListEntity);

	/**
	 * 插入一条数据至清仓任务扫描结果记录表中
	 *
	 * @param stResultListEntity
	 *
	 * @author foss-wuyingjie
	 * @date 2012-12-18 上午11:50:34
	 */
	void addStResultListEntity(StResultListEntity stResultListEntity);

	/**
	 * 通过清仓任务ID、运单号、流水号，获取此扫描明细结果ID
	 *
	 * @param id        清仓任务ID
	 * @param waybillNo 运单号
	 * @param serialNo  流水号
	 * @return
	 *
	 * @author foss-wuyingjie
	 * @date 2013-1-11 下午4:42:03
	 */
	StResultListEntity queryStResultEntity(String id, String waybillNo,	String serialNo);

	/**
	 * 通过清仓任务编号和运单号，查询此运单号下面的已扫描件数
	 * @param stTaskNo
	 * @param waybillNo
	 * @return: 
	 *
	 * @author foss-wuyingjie
	 * @param scanDoneStatus 
	 * @date 2013-2-25 下午2:06:02
	 */
	int queryScanDoneCountByWaybillNo(String stTaskNo, String waybillNo, String scanDoneStatus);

	/**
	 * 通过清仓任务ID、运单号获取此运单已扫描(或已处理)的件数
	 * @param stTaskId
	 * @param waybillNo
	 * @return: 
	 *
	 * @author foss-wuyingjie
	 * @date 2013-3-26 下午3:33:53
	 */
	Long queryFinishedScanCountInTask(String stTaskId, String waybillNo);
	/**
	 * 查询多货入库件数
	 * @param map
	 * @return
	 */
	int queryInStockNumForLack(Map<String,Object> map);
	/**
	 * 查询少货出库件数
	 * @param map
	 * @return
	 */
	int queryOutStockNumForSurplus(Map<String,Object> map);
	/**
	 * 根据任务id获取扫描数据
	 * @param stTaskId
	 * @return
	 */
	List<StResultListEntity> queryResultListByTaskId(String stTaskId);
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
	List<StTaskEntity> querySecScanDetail(String waybillNo, String serialNo, String deptcode, Date handleTime, int oaReportHourRule);
	/**
	 * 下拉包的运单明细
	 * @author zhuyunrong
	 * @date 2015-03-31 09：17
	 * @param Strng packageNo
	 * @return List<PcakageWayBillDto> (运单和流水号)
	 * **/
	List<PcakageWayBillDto> queryPackageDetail(String packageNo);
	
	/**
	 * 查询运单是否确认交货（德邦家装）
	 * @author zyr
	 * @date 2015-10-09
	 * @param String waybillNo
	 * @return String (交货状态)
	 * **/
	String querySalesDeptDelivery(String waybillNo);
	
}
