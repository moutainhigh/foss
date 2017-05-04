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
 *  PROJECT NAME  : tfr-pda-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/pda/api/server/service/IPdaStockcheckingService.java
 *  
 *  FILE NAME          :IPdaStockcheckingService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.pda.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PdaPackageStTastDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PdaStTaskDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.StTaskZoneDto;

/**
 * 清仓任务相关的PDA接口服务
 * @author foss-wuyingjie
 * @date 2012-12-25 上午10:48:07
 */
public interface IPdaStockcheckingService extends IService {
	
	/**
	 * 获取某货件当前所在地货区编号
	 * 
	 * @param waybillNo 运单号
	 * @param serialNo  流水号
	 * @param deptCode  部门编号
	 * @return 当前此货件所在地的货区编号
	 * 
	 * @author foss-wuyingjie
	 * @date 2012-12-13 上午9:46:15
	 */
	String queryGoodsAreaCode(String waybillNo, String serialNo, String deptCode);
	

	/**
	 * <pre>
	 * 建立清仓任务
	 * 1、当传入清仓任务编号为空时
	 *  1.2、如果本部门本库区存在一个清仓中的任务，则直接返回此任务对象，不建立新的任务
	 *  1.1、直接针对此PDA设备编号生成一个新的清仓任务
	 *  1.2、生成此PDA设备生成对应的PDA清仓任务分支
	 * 2、当传入清仓任务编号不为空时
	 *  2.1、通过此清仓任务编号，尝试查询已存在的清仓任务
	 *  2.2、存在的返回已存在的清仓任务对象
	 *  </pre>
	 * 
	 * @param deptCode      部门编号
	 * @param goodsAreaCode 货区编号
	 * @param operatorCode  操作人编号
	 * @param pdaNo         pda设备编号
	 * @param stTaskNo      清仓任务编号
	 * @return List<PdaStTaskDto> pda所需的清仓任务明细列表
	 * 
	 * @author foss-wuyingjie
	 * @date 2012-12-13 上午10:10:15
	 */
	PdaStTaskDto createPdaStTask(String deptCode, String goodsAreaCode, String operatorCode, String pdaNo, String stTaskNo, String isStation, Integer startQty,Integer endQty,String receiveMethod, String districtCode,String isExpressGoods);
	
	/**
	 * <pre>
	 * 刷新清仓任务
     *
	 * 通过清仓任务编号获取对应的清仓任务快照
	 * </pre>
	 *
	 * @param stTaskNo 清仓任务编号
	 * @return List<PdaStTaskDto> pda所需的清仓任务明细列表
	 *
	 * @author foss-wuyingjie
	 * @date 2012-12-13 上午10:19:57
	 */
	List<PdaStTaskDto> queryPdaStTaskDtoList(String stTaskNo);
	
	/**
	 * <pre>
	 * 刷新清仓任务包清仓
     *
	 * 通过清仓任务编号获取对应的清仓任务包清仓快照
	 * </pre>
	 *
	 * @param stTaskNo 清仓任务编号
	 * @return List<PdaPackageStTastDto> pda所需的清仓任务包清仓明细列表
	 *
	 * @author foss-zhuyunrong
	 * @date 2015-03-25 上午11:19:57
	 */
	List<PdaPackageStTastDto> queryPdaPackageStTastDtoList(String stTaskNo);
	
	/**
	 * <pre>
	 * 清仓扫描
	 * 1、往PDA扫描结果表中插入一条数据，包含：正常、多货、撤销
	 * 2、插入一条数据至pda扫描结果表中
	 * 3、按pda扫描时间排序，查询此件已扫描的历史记录
	 *  2.1、若存在，则需通过清仓任务编号、运单号、序列号覆盖此最后一个数据的信息
	 *   2.1.1、若为撤销情况，则删除对应的清仓任务结果列表中的数据
	 *   2.1.2、若不为撤销状态，则更新对应的清仓任务结果列表中的数据
	 *  2.2、若不存在，则插入一条数据至清仓任务结果列表中
	 * 3、任何异常情况，返回false,成功返回true
	 * </pre>
	 *
	 * @param stTaskNo			清仓任务编号
	 * @param waybillNo			运单号
	 * @param serialNo			流水号
	 * @param scanResultStatus	状态
	 * @param scanTime			扫描时间
	 * @param operatorCode		操作人编号
	 * @param pdaNo				pda设备号
	 * @param positionCode 		库位号
	 * @return true - 处理成功 | false - 处理失败
	 *
	 * @author foss-wuyingjie
	 * @date 2012-12-13 上午10:29:40
	 */
	boolean scanStTaskDetail(String stTaskNo, String waybillNo, String serialNo, String scanResultStatus, Date scanTime, String operatorCode, String pdaNo, String positionNo);
	
	/**
	 * <pre>
	 * 包清仓扫描
	 * 1、往PDA扫描结果表中插入n条数据，包含：正常、多货、撤销
	 * 2、插入n条数据至pda扫描结果表中
	 * 3、按pda扫描时间排序，查询此件已扫描的历史记录
	 *  2.1、若存在，则需通过清仓任务编号、运单号、序列号覆盖此最后一个数据的信息
	 *   2.1.1、若为撤销情况，则删除对应的清仓任务结果列表中的数据
	 *   2.1.2、若不为撤销状态，则更新对应的清仓任务结果列表中的数据
	 *  2.2、若不存在，则插入一条数据至清仓任务结果列表中
	 * 3、任何异常情况，返回false,成功返回true
	 * </pre>
	 *
	 * @param stTaskNo			清仓任务编号
	 * @param packageNo			包号
	 * @param scanResultStatus	状态
	 * @param scanTime			扫描时间
	 * @param operatorCode		操作人编号
	 * @param pdaNo				pda设备号
	 * @param positionCode 		库位号
	 * @return true - 处理成功 | false - 处理失败
	 *
	 * @author foss-zhuyunrong
	 * @date 2015-03-25 上午11:23:57
	 */
	boolean scanStTaskPackageDetail(String stTaskNo, String packageNo, String scanResultStatus, Date scanTime, String operatorCode, String pdaNo, String positionNo);
	
	/**
	 * <pre>
	 * 提交清仓任务
	 * 1、判断PDA清仓任务表中是否各pda设备都已完成任务
	 *  1.1、若完成清仓任务，更新清仓任务状态至TransferConstants.STOCK_CHECKING_DONE，并返回true
	 *  1.2、若存在未完成的pda清仓任务，则返回false
	 * </pre>
	 *
	 * @param stTaskNo	  清仓任务编号
	 * @param pdaNo      pda设备编号
	 * @param scanTime   任务完成时间
	 * @return true - 处理成功 | false - 处理失败
	 *
	 * @author foss-wuyingjie
	 * @date 2012-12-13 下午1:43:37
	 */
	boolean submitPdaStTask(String stTaskNo, String pdaNo, Date scanTime);
	
	/**
	 * <pre>
	 * 强制提交任务，不判断PDA清仓任务表中是否各pda设备都已完成任务
	 * 1、更新清仓任务状态及完成时间TransferConstants.STOCK_CHECKING_DONE
	 * 2、若此时有N个pda分支，则向PDA清仓任务表中插入n条新的数据，各数据状态均为TransferPDADictConstants.ST_TASK_STATUS_BRANCH_FINISH完成状态
	 * </pre>
	 *
	 * @param stTaskNo
	 * @param pdaNo
	 * @param scanTime
	 * @return
	 *
	 * @author foss-wuyingjie
	 * @date 2012-12-21 上午9:44:06
	 */
	boolean enforceSubmitPdaStTask(String stTaskNo, String pdaNo, Date scanTime);
	
	/**
	 * <pre>
	 * 撤销清仓任务
	 * 1、判断清仓任务下的所有的清仓任务明细信息是否都已撤销
	 *  1.1、若存在未撤销的明细数据，则返回false
	 *  1.2、若都已撤销完毕，则更新此清仓任务状态至TransferConstants.STOCK_CHECKING_CANCEL，操作成功后返回true
	 * </pre>
	 * 
	 * @param stTaskNo		清仓任务编号
	 * @param pdaNo			pda设备编号
	 * @param scanTime	         任务撤销时间
	 * @param operatorCode  操作员工编号
	 * @return true - 处理成功 | false - 处理失败
	 *
	 * @author foss-wuyingjie
	 * @date 2012-12-13 下午1:53:03
	 */
	boolean cancelPdaStTask(String stTaskNo, String pdaNo, Date scanTime, String operatorCode);
	
	/**
	 * <pre>
	 * 完成清仓任务
	 * 1、判断此pda设备对应的清仓任务是否已完成(已提交) ????
	 * 1、某一pda设备对应的分支清仓任已完成，需在PDA清仓任务表中标志已完成此pda的任务
	 * </pre>
	 *
	 * @param stTaskNo		清仓任务编号
	 * @param scanTime      任务完成时间
	 * @param pdaNo         pda设备号
	 * @return true - 处理成功 | false - 处理失败
	 *
	 * @author foss-wuyingjie
	 * @date 2012-12-13 下午1:55:08
	 */
	boolean finishPdaStTask(String stTaskNo, Date scanTime, String pdaNo);

	/**
	 * @author nly
	 * @date 2014年11月13日 上午11:00:18
	 * @function 根据外场code获取对应的分区信息
	 * @param deptCode
	 * @return
	 */
	List<StTaskZoneDto> queryZoneInfo(String deptCode);
	
	/**
	 * @author 268084
	 * @date 
	 * @function 根据外场code和库区编号查找库区实体(类型)
	 * @param deptCode
	 * @return
	 */
	String queryGoodsType(String transferCenterCode,String goodsAreaCode);
	
	/**
	 * @author 268084
	 * @date 
	 * @function 根据部门编号获取货区信息
	 * @param deptCode
	 * @return
	 */
	String queryDeptInfo(String deptCode);
}