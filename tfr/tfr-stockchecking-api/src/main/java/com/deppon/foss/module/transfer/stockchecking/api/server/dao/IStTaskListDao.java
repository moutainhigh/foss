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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/api/server/dao/IStTaskListDao.java
 *  
 *  FILE NAME          :IStTaskListDao.java
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

import java.util.List;

import com.deppon.foss.module.transfer.pda.api.shared.dto.PdaPackageStTastDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PdaStTaskDto;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StTaskEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StTaskListEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StTaskWaybillNoListDto;

/**
 * 清仓任务快照相关业务处理
 * @author foss-wuyingjie
 * @date 2012-12-14 下午2:36:45
 */
public interface IStTaskListDao {

	/**
	 * 新增一个清仓任务列表实体
	 * @author foss-wuyingjie
	 * @date 2012-10-26 下午2:53:10
	 * @param taskListEntity
	 * @return
	 */
	void addStTaskListEntity(StTaskListEntity taskListEntity);

	/**
	 * 批量新增一个清仓任务列表快照
	 * @author foss-wuyingjie
	 * @date 2012-11-1 下午2:16:21
	 * @param stTaskListEntityList
	 * @return
	 */
	void addStTaskListEntityBatch(List<StTaskListEntity> stTaskListEntityList);

	/**
	 * 根据清仓任务ID删除清仓任务列表记录
	 * @author foss-wuyingjie
	 * @date 2012-11-2 上午9:11:18
	 * @param stTaskId
	 * @return
	 */
	void deleteByStTaskId(String stTaskId);

	/**
	 * 根据清仓任务ID清仓货物清单的运单号列表
	 * @author foss-wuyingjie
	 * @date 2012-11-7 下午1:52:55
	 * @param stTaskId
	 * @return List<StTaskWaybillNoListDto>
	 */
	List<StTaskWaybillNoListDto> queryStTaskWaybillNoListByStTaskId(String stTaskId);

	/**
	 * 根据清仓任务ID获取清仓货物清单列表
	 * @author foss-wuyingjie
	 * @date 2012-11-8 上午9:23:36
	 * @param stTaskId
	 * @return List<StTaskListEntity>
	 */
	List<StTaskListEntity> queryStTaskListByStTaskId(String stTaskId);

	/**
	 * 通过PDA清仓任务ID，获取少货的清仓差异
	 * @author foss-wuyingjie
	 * @date 2012-11-30 下午2:40:28
	 * @param stTaskId
	 * @return List<StTaskListEntity>
	 */
	List<StTaskListEntity> queryPdaLackList(String stTaskId);

	/**
	 * 通过清仓任务编号，获取pda设备所需的清仓快照信息列表
	 *
	 * @param stTaskNo	清仓任务编号
	 * @return 清仓快照信息列表
	 *
	 * @author foss-wuyingjie
	 * @date 2012-12-14 下午2:37:08
	 */
	List<PdaStTaskDto> queryPdaStTaskDtoList(String stTaskNo);
	
	/**
	 * 通过清仓任务编号，获取pda设备所需的包清仓快照信息列表
	 *
	 * @param stTaskNo	清仓任务编号
	 * @return 包清仓快照信息列表
	 *
	 * @author foss-zhuyunrong
	 * @date 2015-03-27 上午10:35:00
	 */
	List<PdaPackageStTastDto> queryPdaPackageStTastDtoList(String stTaskNo);

	/**
	 * 根据清仓任务ID、运单号、流水号，查看快照中的数据数
	 * @param stTaskId
	 * @param waybillNo
	 * @param serialNo
	 * @return: 
	 *
	 * @author foss-wuyingjie
	 * @date 2013-3-18 下午12:26:02
	 */
	Long queryEntityCountByGoodsInfo(String stTaskId, String waybillNo, String serialNo);
	
	/**
	 * 新增一个清仓任务列表快照
	 * @param stTaskId
	 * @param orgCode
	 * @param goodsAreaCode
	 * @author 097457-foss-wangqiang
	 * @date 2013-4-28 下午3:02:37
	 */
	void addStTaskListFromStock(StTaskEntity task,int beforeTime);
	/**
	 * @author niuly
	 * @date 2013-6-17 18:27:08
	 * @function 按件建立清仓任务时新增任务明细
	 * @param stTaskId
	 * @param orgCode
	 * @param goodsAreaCode
	 * @param endQty 
	 * @param startQty 
	 * @param beforeTime
	 */
	void addStTaskListFromStockByQty(String stTaskId, String orgCode, String goodsAreaCode, Integer startQty, Integer endQty,int beforeTime);
	
	
	
	/**查询清仓快照列表(快递)
	 * @author 268084
	 * @param stTaskNo
	 * @return
	 */
	List<PdaStTaskDto> queryPdaStTaskDtoListExpress(String stTaskNo);
	
	/**查询清仓快照列表(零担)
	 * @author 268084
	 * @param stTaskNo
	 * @return
	 */
	List<PdaStTaskDto> queryPdaStTaskDtoListNotExpress(String stTaskNo);
	
	/**
	 * 新增一个清仓任务列表快照(快递)
	 * @param stTaskId
	 * @param orgCode
	 * @param goodsAreaCode
	 * @author 268084
	 * @date 
	 */
	void addStTaskListFromStockForExpress(StTaskEntity task,int beforeTime);
	/**
	 * 新增一个清仓任务列表快照(零担)
	 * @param stTaskId
	 * @param orgCode
	 * @param goodsAreaCode
	 * @author 268084
	 * @date 
	 */
	void addStTaskListFromStockForNoExpress(StTaskEntity task, int beforeTime);
	
	
	List<PdaStTaskDto> queryPdaStSnap(String stTaskNo);
	
	PdaStTaskDto queryWaybillInfo(String waybillNo);
	
	Long queryContraband(String waybillNo);
	
	Long queryNeedPack(String waybillNo);

	String queryOrgStationNum(String code);

	String queryOuterBranchStationNum(String code);

}