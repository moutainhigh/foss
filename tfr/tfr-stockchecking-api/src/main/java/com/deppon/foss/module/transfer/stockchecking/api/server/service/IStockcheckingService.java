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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/api/server/service/IStockcheckingService.java
 *  
 *  FILE NAME          :IStockcheckingService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.stockchecking.api.server.service;

import java.io.InputStream;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.BaseDataDictDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.ScanDetailDto;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StOperatorEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.dto.GoodsStockDto;
import com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StTaskDto;
import com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StTaskWaybillNoListDto;
import com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StWaybillInfoDto;
import com.deppon.foss.module.transfer.stockchecking.api.shared.vo.StockcheckingVO;

public interface IStockcheckingService extends IService {

	/**
	 * 查询清仓任务列表
	 * 
	 * @author foss-wuyingjie
	 * @date 2012-10-18 上午11:50:56
	 * @param stTaskDto
	 * @param limit
	 * @param start
	 * @return List<StTaskDto>
	 */
	List<StTaskDto> queryStTaskDtoList(StTaskDto stTaskDto, int limit, int start);

	/**
	 * 查询清仓任务列表对应的总数
	 * @author foss-wuyingjie
	 * @date 2012-10-18 上午11:51:16
	 * @param stTaskDto
	 * @return long
	 */
	Long queryStTaskDtoListCount(StTaskDto stTaskDto);

	/**
	 * 查询库区信息列表
	 * @author foss-wuyingjie
	 * @date 2012-10-24 上午9:08:55
	 * @param deptNo 部门编号
	 * @return List<GoodsStockDto>
	 */
	List<GoodsStockDto> queryGoodsStockDtoList(GoodsStockDto goodsStockDto);

	/**
	 * <pre>
	 * 创建清仓任务
	 * 1、每个库区创建一个对应的清仓任务，比如页面勾选了2个库区，则创建2个清仓任务
	 * 2、通过传入的库区列表实时获取各库区下未清仓的件，各清仓任务生成对应的库存快照供日后比对
	 * 3、生成清仓任务后状态为 TransferConstants.STOCK_CHECKING_DOING
	 * </pre>
	 * @author foss-wuyingjie
	 * @param selectedGoodsAreasList 已选的库区列表，只保存编号
	 * @param stOperatorEntity 已选的清仓人列表
	 * @param districtName 
	 * @param startQty 起始件数
	 * @param endQty 结束件数
	 * @date 2012-10-24 下午5:05:22
	 */
	void addStTask(List<String> selectedGoodsAreasList, List<StOperatorEntity> stOperatorEntity,String receiveMethod,String districtCode, String districtName, Integer startQty, Integer endQty,String stType);

	/**
	 * 获取长短途货区数据字典下拉框，需调用综合接口
	 * @author foss-wuyingjie
	 * @date 2012-10-26 上午11:35:35
	 * @param
	 * @return List<BaseDataDictDto>
	 */
	List<BaseDataDictDto> queryGoodsAreaUsage();

	/**
	 * 通过stTaskId清仓任务获取清仓人
	 * @author foss-wuyingjie
	 * @date 2012-11-1 上午10:14:03
	 * @param stTaskId 
	 * @return List<StOperatorEntity>
	 */
	List<StOperatorEntity> queryOperatorsByStTaskId(String stTaskId);

	/**
	 * 更新清仓人信息
	 * @param stTaskId 清仓任务ID
	 * @param selectedOperatorList 已选取的清仓人列表
	 * @author foss-wuyingjie
	 * @date 2012-11-1 上午11:48:33
	 */
	void updateStTask(String stTaskId, List<StOperatorEntity> selectedOperatorList);

	/**
	 * 取消清仓任务，状态置为TransferConstants.STOCK_CHECKING_CANCEL,同时删除库存快照(清仓任务列表)
	 * @author foss-wuyingjie
	 * @date 2012-11-2 上午8:52:36
	 * @param stTaskId
	 */
	void cancelStTask(String stTaskId);

	/**
	 * 由清仓任务ID获取清仓任务信息
	 * @author foss-wuyingjie
	 * @date 2012-11-7 上午11:05:07
	 * @param stTaskId
	 * @return StTaskDto
	 */
	StTaskDto queryStTaskById(String stTaskId);

	/**
	 * 由清仓任务ID获取库存快照的运单号列表
	 * @author foss-wuyingjie
	 * @date 2012-11-7 下午1:50:05
	 * @param stTaskId
	 * @return List<StTaskWaybillNoListDto>
	 */
	List<StTaskWaybillNoListDto> queryStTaskWaybillNoListByStTaskId(String stTaskId);

	/**
	 * 通过清仓任务ID获取清仓人结果的运单统计信息
	 * @author foss-wuyingjie
	 * @date 2012-11-14 下午3:56:43
	 * @param stTaskId
	 * @return List<StWaybillInfoDto>
	 */
	List<StWaybillInfoDto> queryStWaybillInfoDtoByStTaskId(String stTaskId);

	/**
	 * 通过清仓任务ID以及运单号查询扫描明细
	 * @author foss-wuyingjie
	 * @date 2012-11-14 下午7:58:07
	 * @param stTaskId
	 * @param waybillNo
	 * @return List<ScanDetailDto>
	 */
	List<ScanDetailDto> queryScanDetailInStTask(String stTaskId, String waybillNo);

	/**
	 * 确认清仓任务结束，需要更新清仓任务状态，并生成新的清仓任务结果
	 * @author foss-wuyingjie
	 * @param stTaskId 清仓任务ID
	 * @param selectedOperatorList 页面所选的清仓人列表
	 * @param scanResultOkList 页面中确认打勾的库存清单
	 * @param scanResultHaveNotList 页面中确认少货的库存清单
	 * @param scanResultSurplusList 页面中确认多货的库存清单
	 * @date 2012-11-15 上午9:11:22
	 */
	void confirmStTask(String stTaskId, 
					   List<StOperatorEntity> selectedOperatorList,
					   List<StTaskWaybillNoListDto> scanResultOkList,
					   List<StTaskWaybillNoListDto> scanResultHaveNotList,
					   List<StTaskWaybillNoListDto> scanResultSurplusList);

	/**
	 * 创建导出文件名
	 * @author foss-wuyingjie
	 * @date 2012-11-15 下午3:41:46
	 * @param fileName
	 * @return String
	 */
	String createFileName(String fileName);

	/**
	 * 通过清仓任务ID获取清仓任务明细信息
	 * @author foss-wuyingjie
	 * @date 2012-11-15 下午3:47:06
	 * @param stTaskId
	 * @return InputStream
	 */
	InputStream exportTaskById(String stTaskId);
	
	/**
	 * 通过部门编号以及库区编号，验证是否存在正在清仓的任务
	 * @param deptCode
	 * @param goodsAreaCode
	 * @return: 若存在正在执行的清仓任务，返回任务列表
	 *
	 * @author foss-wuyingjie
	 * @date 2013-3-20 上午11:05:31
	 */
	List<StTaskDto> queryTaskInProcess(String deptCode, String goodsAreaCode);
	
	/**
	 * 获取当前部门的上级外场、空运总调大部门
	 * @author 097457-foss-wangqiang
	 * @date 2013-5-18 下午2:36:18
	 * @param currentOrg
	 * @return OrgAdministrativeInfoEntity
	 */
	OrgAdministrativeInfoEntity getBigOrg(OrgAdministrativeInfoEntity currentOrg);
	
	/**
	 * 查询外场的货区统计信息
	 * @author 205109-foss-zenghaibin
	 * @date 2014-9-19 下午2:36:18
	 * @param currentOrg
	 * @return StockcheckingVO
	 */
	StockcheckingVO statistics();
	
	

	/**
	 * @author niuly
	 * @function 判断货区是否为驻地派送货区
	 * @param code
	 * @param goodsArea
	 * @return boolean
	 */
	boolean isBasStation(String code, String goodsArea);

	/**
	 * @author niuly
	 * @date 2014-6-16下午4:31:50
	 * @function 自动取消清仓任务
	 */
	void cancelStTask();

	/**
	 * @author nly
	 * @date 2014年10月30日 上午9:05:36
	 * @function 是否试点外场
	 * @return
	 */
	boolean isTestTrans();

	/**
	 * @author nly
	 * @date 2014年11月11日 下午4:35:29
	 * @function 查询所属外场
	 * @return
	 */
	String queryTransferCode();
	/**
	 * Author:268084
	 * 根据部门编码查询外场
	 * @param orgCode
	 * @return
	 */
	OrgAdministrativeInfoEntity querySupTfrCtr(String orgCode);

	/**
	 * @author nly
	 * @date 2014年11月7日 上午10:41:28
	 * @function 校验是否存在交叉的清仓任务
	 * @param goodsStockDto
	 */
//	void checkStockChecking(GoodsStockDto goodsStockDto);
}