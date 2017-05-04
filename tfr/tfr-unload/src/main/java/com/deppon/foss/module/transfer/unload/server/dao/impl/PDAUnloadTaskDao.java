/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 * 
 *  
 *  you may not use this file except in compliance with the License.
 * 
 *  
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 * 
 *  
 *  Unless required by applicable law or agreed to in writing, software
 * 
 *  
 *  distributed under the License is distributed on an "AS IS" BASIS,
 * 
 *  
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * 
 *  
 *  
 *  See the License for the specific language governing permissions and
 *  
 *  
 *  limitations under the License.
 * 
 *  
 * 
 *  
 *  Contributors:
 * 
 *  
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-unload
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/server/dao/impl/PDAUnloadTaskDao.java
 *  
 *  FILE NAME          :PDAUnloadTaskDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/**
 * 
 * 
 * PROJECT NAME: tfr-unload
 * 
 * 
 * PACKAGE NAME: com.deppon.foss.module.transfer.unload.server.dao.impl
 * 
 * 
 * FILE    NAME: PDAUnloadTaskDao.java
 * 
 * 
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 * 
 * 
 * 
 *相关业务用例
 * 
 * 	BUC_FOSS_5.30.10_040 建立卸车任务
 * 
 *	BUC_FOSS_5.30.10_050 逐件扫描货物
 *
 *	BUC_FOSS_5.30.10_060完成卸车任务
 *
 * 
 * 
 * 
 * 用例描述
 * 
 * 		理货员使用PDA建立卸车任务后，PDA调用FOSS接口下拉卸车清单，同步卸车记录，同步扫描记录至FOSS
 * 
 * 
 * 
 * 
 * 
 * 用例条件
 * 
 * 		前置条件	
 * 
 * 		1.	PDA建立卸车任务	
 * 
 * 		SUC-240建立卸车任务（PDA）
 * 
 *		SUC-241逐件扫描卸车货物（PDA）
 *
 *		SUC-287卸车补录重量体积（PDA）
 *
 *		SUC-276提交卸车任务（PDA）
 *
 * 
 * 
 * 
 * 
 * 		后置条件
 * 
 * 		1、	可以查询卸车进度
 * 
 *		2、	可以查询卸车任务
 *
 *		3、	可以查询卸车过程中产生的需重贴标签的货物
 *
 *		4、	生成卸车差异报告	SUC-96生成卸车差异报告
 *
 *		SUC-94查询卸车进度
 *
 *		SUC-95查询卸车任务
 *
 *		SUC-51查询重贴标签货物
 *
 *
 *
 *
 *
 *
 *
 *
 *操作步骤：
 *
 *
 *	建立卸车任务
 *
 *		1	PDA建立卸车任务：
 *
 *			营业部卸车时，理货员录入车牌号，FOSS根据车牌号查找该车辆中到达部门为本部门的交接单
 *
 *		2、外场卸车时，直接根据已分配的交接单/配载单/完成接货任务/拉回货建立卸车任务
 *
 *	补充步骤：
 *
 *			a、	检查是否可以建立卸车任务（参见业务规则SR-8）
 *
 *			b、	若可以建立卸车任务，生成卸车任务编号（参见业务规则SR-7），FOSS保存卸车任务、卸车理货员、卸车PDA，信息
 *
 *			c、	返回卸车货物清单给PDA（参见业务规则SR-1、SR-2）
 *
 *		3、每5分钟调用此接口更新【卸车货物清单】中是否有更改字段
 *
 *		4、理货员手动点击刷新时，PDA调用此接口更新【卸车货物清单】中是否有更改字段
 * 
 * 补充步骤：
 * 
 * 			a、返回卸车清单（参见业务规则SR-1、SR-2）
 * 		
 * 
 * 		5、调用月台接口
 * 				
 * 			修改月台状态为使用中，记录月台使用车牌号，
 * 			开始使用时间，结束使用时间（此时是预计结束时间）（参见业务规则SR-6）
 * 
 * 
 * 		6、其他PDA共同参与此卸车任务
 * 
 * 				1、	若加入卸车任务理货员在该任务理货员清单中
 * 
 *				2、	返回卸车清单（参见业务规则SR-1、SR-2）
 *
 * 扩展事件
 * 
 * 		1a	营业部建立卸车任务时，若车牌号中无到达部门为卸车部门的交接单，则FOSS返回PDA相应提示信息
 * 
 * 		1b	若FOSS检查PDA传过来的卸车任务信息，发现不能建立卸车任务，则FOSS返回PDA相应提示信息
 * 
 * 		4a	若加入卸车任务理货员不在该任务理货员清单中，则返回下拉任务失败
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 逐件扫描
 * 
 * 
 * 		1、PDA向FOSS提交一条卸车数据		FOSS判断货物在【卸车货物清单】中
 * 		
 * 		2	若货物在【卸车货物清单】中（参见业务规则SR-10）
 * 			则调用FOSS接口同步货物扫描状态为已扫描、货物状态为正常、卸车件数+1，至FOSS，
 * 			若卸车操作部门为中转部门、驻地部门，则货物入库
 * 
 * 
 * 
 * 
 * 
 * 
 * 	扩展事件
 * 
 * 		若货物不在【卸车货物清单】中	
 * 	
 * 		1、	则调用FOSS接口，判断货物状态（参见业务规则SR-3）
 * 
 *		2、	返回PDA货物状态，若PDA确认卸车则执行3、4步操作（参见业务规则SR-11）
 *
 *		3、	调用FOSS接口记录该卸车记录，货物扫描状态为已扫描、货物状态为上述货物状态，卸车件数+1，是否强卸为是，至FOSS
 *
 *		4、	若卸车操作部门为中转部门、驻地部门，则货物入库，上一库存部门出库此货物
 *
 * 
 * 
 * 
 * 
 * 
 * 
 * 卸车录入重量体积
 * 
 * 		对于集中卸车理货员使用PDA入货物重量、体积	
 *
 *		1、	调用FOSS接口同步货物重量、体积至FOSS
 *
 *		2、	FOSS检查该运单是否有重量和体积
 *
 *		3、	运单无重量和体积，FOSS将重量和体积保存到运单信息中
 *
 * 
 * 	扩展事件
 * 
 * 		运单已有重量或体积，或全部都有，FOSS返回给PDA，重量/体积/重量体积已经存在了，不执行该项的运单修改
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 修改理货员
 * 		
 * 		PDA中途添加/删除理货员（参见业务规则SR-9）
 * 			调用FOSS接口，将理货员加入/离开信息同步至FOSS
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 撤销卸车任务
 * 		
 * 		PDA撤销卸车任务（参见业务规则SR-5）	
 * 		
 * 		PDA调用FOSS接口，同步【卸车任务】中是否撤销为是至FOSS
 * 
 * 		调用月台接口		
 * 
 * 		修改月台状态为空闲中，记录月台历史记录，车牌号，开始使用时间，结束使用时间（任务提交时间）
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 提交卸车任务
 * 
 * 		1	PDA提交卸车任务	
 * 
 *		2	PDA确认提交卸车任务	
 *
 *			a、	PDA调用FOSS接口同步【卸车PDA】中离开任务时间、
 *			【卸车理货员】中离开任务时间至FOSS
 *
 * 			b、	同步【卸车任务】中卸车结束时间、卸车状态至FOSS；
 * 
 * 		3	调用月台接口		
 * 
 *			修改月台状态为空闲中，记录月台历史记录，车牌号，开始使用时间，结束使用时间（任务提交时间）
 *
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 业务规则
 * 
 * 
 * 
 *SR-1	
 *	【卸车货物清单】中货物为【卸车任务】中单据编号中货物清单
 * 
 * 
 *SR-2	
 *	【卸车货物清单】中是否强卸默认为否、扫描状态默认为未扫描、货物状态默认为少货、卸车件数默认为0
 *
 *
 *SR-3	
 *	若标签已作废，则返回PDA货物状态为“已作废”
 *
 *	若标签已签收，则返回PDA货物状态为“已签收”
 *
 *	若货物走货路径上有卸车操作部门节点，则货物状态标记为“多货-夹带”；
 *
 *	若货物走货路径上没有卸车操作部门节点，则货物状态标记为“多货-异地夹带”；
 *
 *
 *
 *SR-5	
 *	
 *只
 *有建立任务PDA可以执行撤销操作
 *
 *
 *SR-6	
 *	结束时间为建立卸车任务时间+max（卸车货物清单中总重量/卸车重量标准，卸车货物清单中总体积/卸车体积标准）
 *
 *
 *SR-7	
 *	编号规则为：两位任务类别号码+6位年月日+5位流水号码，流水号每日更新一次，
 *
 *	如0212110112345，其中，两位任务类别号码对应关系为：外场卸车:03，营业部卸车:06
 *
 *
 *SR-8	
 *	交接单
 *
 *	配载单/
 *
 *	完成接货任务/
 *
 *	拉回货任务只有在没有建立卸车任务或建立的卸车任务已作废的情况下可以建立卸车任务
 *
 *	理货员必须和卸车部门在同一区域
 *
 *	必须执行检查封签后才能建立卸车任务
 *
 *
 *SR-9	
 *
 *	理货员必须和卸车部门在同一区域
 *
 *
 *SR-10	
 *
 *	货物手输时重复上面两步操作，扫描状态为手输
 *
 *
 *SR-11	
 *
 *	若货物状态为“已作废”、“已签收”，PDA不能确认卸车
 *
 *
 *SR-12	
 *
 *	无流水号的卸车不入库，不上报OA
 *
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */ 
package com.deppon.foss.module.transfer.unload.server.dao.impl;
/**
 * 引入包
 */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
/**
 * 引入包
 */
import java.util.List;
/**
 * 引入包
 */
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;






/**
 * 引入包
 */
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
/**
 * 引入包
 */
import com.deppon.foss.module.transfer.load.api.shared.define.SealConstant;
import com.deppon.foss.module.transfer.load.api.shared.domain.ExpressPackageDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
/**
 * 引入包
 */
import com.deppon.foss.module.transfer.load.api.shared.domain.SealEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAAssignUnloadBillEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.SCPDAAssignUnloadTaskEntity;
/**
 * 引入包
 */
import com.deppon.foss.module.transfer.unload.api.server.dao.IBatchSaveProcessDao;
import com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao;
/**
 * 引入包
 */
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
import com.deppon.foss.module.transfer.unload.api.shared.domain.AssignUnloadTaskEntity;
/**
 * 引入包
 */
import com.deppon.foss.module.transfer.unload.api.shared.domain.ChangeLabelGoodsEntity;
/**
 * 引入包
 */
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadBillDetailEntity;
/**
 * 引入包
 */
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadSerialNoDetailEntity;
/**
 * 引入包
 */
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadTaskEntity;
/**
 * 引入包
 */
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadWaybillDetailEntity;
/**
 * 引入包
 */
import com.deppon.foss.module.transfer.unload.api.shared.dto.ArriveBillDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.PDAUnloadAsyncBillMsgDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadBillDetailDto;
/**
 * 引入包
 */
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadGoodsSerialDetailDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadSerialDetaiDto;
/**
 * 引入包
 */
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadSerialNoDetailDto;
/**
 * pda卸车dao
 * @author dp-duyi
 * @date 2012-11-28 上午10:12:31
 */
@SuppressWarnings("unchecked")
public class PDAUnloadTaskDao extends iBatis3DaoImpl implements IPDAUnloadTaskDao{
	private static final String NAMESPACE = "tfr-unload.";
	private IBatchSaveProcessDao batchSaveProcessDao;
	public void setBatchSaveProcessDao(IBatchSaveProcessDao batchSaveProcessDao) {
		this.batchSaveProcessDao = batchSaveProcessDao;
	}
	/** 
	 * pda接口：查询已分配卸车任务:未开始、进行中
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-11-28 上午10:15:03
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#pdaQueryAssignedUnloadTask(java.util.Map)
	 */
	@Override
	public List<PDAAssignUnloadBillEntity> pdaQueryAssignedUnloadTask(
			Map<String, String> condition) {
		return this.getSqlSession().selectList(NAMESPACE+"pda_queryAssignUnloadTask",condition);
	}
	
	/** 
	 * pda接口：查询已分配卸车任务:未开始、进行中
	 * 
	 * 
	 * @author dp-hongwy
	 * @date 2015-05-08 下午14:50:26
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#pdaQueryAssignedUnloadTask(java.util.Map)
	 */
	@Override
	public List<SCPDAAssignUnloadTaskEntity> pdaQueryAssignedSCUnloadTask(
			Map<String, String> condition) {
		return this.getSqlSession().selectList(NAMESPACE+"pda_queryAssignSCUnloadTask",condition);
	}
	/**
	 * 
	 * @author dp-hongwy
	 * @date 2015-05-12下午14:43:20
	 * 
	 */
	public int updateUnloadScanState(String billNo){
		return this.getSqlSession().update(NAMESPACE+"pda_updateUnloadScanState",billNo);
	}
	
	/** 
	 * 根据任务编号查询卸车任务
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-12-17 下午4:04:16
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryUnloadTaskByTaskNo(java.lang.String)
	 */
	@Override
	public UnloadTaskEntity queryUnloadTaskByTaskNo(String taskNo) {
		return (UnloadTaskEntity) this.getSqlSession().selectOne(NAMESPACE+"queryUnloadTaskByTaskNo", taskNo);
	}
	/** 
	 * 根据单据编号查询单据
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-12-17 下午4:26:12
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryUnloadBillsByBillNo(java.util.List)
	 */
	@Override
	public List<UnloadBillDetailDto> queryUnloadBillsByBillNo(
			List<String> billNos) {
		return this.getSqlSession().selectList(NAMESPACE+"queryUnloadBillsByBillNo", billNos);
	}
	/** 
	 * 查询卸车交接单
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-12-17 下午6:47:26
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryHandOverUnloadDetail(java.util.List)
	 */
	@Override
	public List<UnloadGoodsSerialDetailDto> queryHandOverUnloadDetail(
			List<UnloadBillDetailDto> bills,String taskId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("taskId", taskId);
		param.put("bills", bills);
		return this.getSqlSession().selectList(NAMESPACE+"queryHandOverUnloadDetail", param);
	}
	/** 
	 * 查询卸车配载单
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-12-17 下午6:47:26
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryAssembleUnloadDetail(java.util.List)
	 */
	@Override
	public List<UnloadGoodsSerialDetailDto> queryAssembleUnloadDetail(List<UnloadBillDetailDto> bills,String taskId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("taskId", taskId);
		param.put("bills", bills);
		return this.getSqlSession().selectList(NAMESPACE+"queryAssembleUnloadDetail", param);
	}
	/** 
	 * 查询卸车接送货单据
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-12-17 下午6:47:26
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryPickUpUnloadDetail(java.util.List)
	 */
	@Override
	public List<UnloadGoodsSerialDetailDto> queryPickUpUnloadDetail(List<UnloadBillDetailDto> bills,String taskId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("taskId", taskId);
		param.put("bills", bills);
		return this.getSqlSession().selectList(NAMESPACE+"queryPickUpUnloadDetail", param);
	}
	/** 
	 * 查询电子面单单据
	 * 
	 * 
	 * @author dp-sjl
	 * @date 2016-6-12 14:05:47
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryELookGoodsDetail(java.util.List)
	 */
	@Override
	public List<UnloadGoodsSerialDetailDto> queryELookGoodsDetail(List<UnloadBillDetailDto> bills,String taskId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("taskId", taskId);
		param.put("bills", bills);
		return this.getSqlSession().selectList(NAMESPACE+"queryELookGoodsDetail", param);
	}
	/** 
	 * 根据任务ID查询单据
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-12-17 下午7:07:21
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryUnloadBillsByTaskNo(java.lang.String)
	 */
	@Override
	public List<UnloadBillDetailDto> queryUnloadBillsByTaskId(String taskId) {
		return this.getSqlSession().selectList(NAMESPACE+"queryUnloadBillsByTaskId",taskId);
	}
	/** 
	 * 查询封签
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-12-18 上午10:09:35
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#querySeal(com.deppon.foss.module.transfer.load.api.shared.domain.SealEntity)
	 */
	@Override
	public List<SealEntity> querySeal(SealEntity seal) {
		return this.getSqlSession().selectList(NAMESPACE+"querySeal", seal);
	}
	/** 
	 * 更新已分配卸车任务状态
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-12-18 上午11:25:02
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#uodateAssignUnloadTaskState(java.util.List)
	 */
	@Override
	public int updateAssignUnloadTaskState(List<AssignUnloadTaskEntity> unloadTasks) {
		return batchSaveProcessDao.batchSaveProcess(UnloadConstants.BATCH_SAVE_TYPE_UPDATE, NAMESPACE+"updateAssignUnloadTaskState", unloadTasks);
	}
	/** 
	 * 根据任务ID查询理货员
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-12-18 下午5:10:16
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryLoaderByTaskId(com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity)
	 */
	@Override
	public List<LoaderParticipationEntity> queryLoaderByTaskId(LoaderParticipationEntity loader) {
		return this.getSqlSession().selectList(NAMESPACE+"queryLoaderByTaskId", loader);
	}
	/** 
	 * 根据任务ID查询扫描记录条数
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-12-19 上午11:25:40
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryScanSerialNoQTYByTaskId(java.lang.String)
	 */
	@Override
	public int queryScanSerialNoQTYByTaskId(String taskId) {
		return (Integer) this.getSqlSession().selectOne(NAMESPACE+"queryScanSerialNoQTYByTaskId", taskId);
	}
	/** 
	 * 更新卸车任务
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-12-19 上午11:28:00
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#updateUnloadTask(com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadTaskEntity)
	 */
	@Override
	public int updateUnloadTask(UnloadTaskEntity unloadTask) {
		return this.getSqlSession().update(NAMESPACE+"updateUnloadTask",unloadTask);
	}
	/** 
	 * 查询任务车辆中未结束卸车的有效单据数
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-12-20 上午9:16:19
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryUnloadedValideBillQty(java.lang.String)
	 */
	@Override
	public int queryUnfinishUnloadedValideBillQty(String truckTaskDetailId) {
		return (Integer) this.getSqlSession().selectOne(NAMESPACE+"queryUnfinishUnloadedValideBillQty", truckTaskDetailId);
	}
	/** 
	 * 修改任务车辆明细状态
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-12-20 上午9:24:38
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#updateTruckTaskDetailState(com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskDetailEntity)
	 */
	@Override
	public int updateTruckTaskDetailState(TruckTaskDetailEntity truckTaskDetail) {
		return this.getSqlSession().update(NAMESPACE+"updateTruckTaskDetailState", truckTaskDetail);
	}
	/** 
	 * 查询未完成卸车的任务车辆明细记录条数
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-12-20 上午9:37:03
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryUnfinishUnloadedTruckTaskDetailQty(java.lang.String)
	 */
	@Override
	public int queryUnfinishUnloadedTruckTaskDetailQty(String truckTaskId) {
		return (Integer) this.getSqlSession().selectOne(NAMESPACE+"queryUnfinishUnloadedTruckTaskDetailQty", truckTaskId);
	}
	/** 
	 * 修改任务车辆状态
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-12-20 上午9:39:26
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#updateTruckTaskState(com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskEntity)
	 */
	@Override
	public int updateTruckTaskState(TruckTaskEntity truckTask) {
		return this.getSqlSession().update(NAMESPACE+"updateTruckTaskState", truckTask);
	}
	/** 
	 * 插入扫描流水号
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-12-20 下午3:25:14
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#insertUnloadSerialNoEntity(com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadSerialNoDetailEntity)
	 */
	@Override
	public int insertUnloadSerialNoEntity(
			UnloadSerialNoDetailEntity serialNoEntity) {
		return this.getSqlSession().insert(NAMESPACE+"insertUnloadSerialNoEntity", serialNoEntity);
	}
	/** 
	 * 插入扫描运单明细
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-12-20 下午3:25:14
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#insertUnloadWayBillEntity(com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadWaybillDetailEntity)
	 */
	@Override
	public int insertUnloadWayBillEntity(UnloadWaybillDetailEntity wayBIllEntity) {
		if(wayBIllEntity.getVolume() != null){
			wayBIllEntity.setVolume(wayBIllEntity.getVolume().setScale(2,BigDecimal.ROUND_HALF_UP));
		}
		if(wayBIllEntity.getWeight() != null){
			wayBIllEntity.setWeight(wayBIllEntity.getWeight().setScale(2,BigDecimal.ROUND_HALF_UP));
		}
		if(wayBIllEntity.getUnloadVolumeTotal() != null){
			wayBIllEntity.setUnloadVolumeTotal(wayBIllEntity.getUnloadVolumeTotal().setScale(2,BigDecimal.ROUND_HALF_UP));
		}
		if(wayBIllEntity.getUnloadWeightTotal() != null){
			wayBIllEntity.setUnloadWeightTotal(wayBIllEntity.getUnloadWeightTotal().setScale(2,BigDecimal.ROUND_HALF_UP));
		}
		//配合BI，增加修改时间
		if(wayBIllEntity.getModifyDate() ==null){
			wayBIllEntity.setModifyDate(new Date());
		}
		wayBIllEntity.setCreateDate(new Date());
		return this.getSqlSession().insert(NAMESPACE+"insertUnloadWayBillEntity", wayBIllEntity);
	}
	/** 
	 * 更新扫描运单明细
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-12-20 下午3:25:14
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#updateUnloadWayBillEntity(com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadWaybillDetailEntity)
	 */
	@Override
	public int updateUnloadWayBillEntity(UnloadWaybillDetailEntity wayBIllEntity) {
		if(wayBIllEntity.getVolume() != null){
			wayBIllEntity.setVolume(wayBIllEntity.getVolume().setScale(2,BigDecimal.ROUND_HALF_UP));
		}
		if(wayBIllEntity.getWeight() != null){
			wayBIllEntity.setWeight(wayBIllEntity.getWeight().setScale(2,BigDecimal.ROUND_HALF_UP));
		}
		if(wayBIllEntity.getUnloadVolumeTotal() != null){
			wayBIllEntity.setUnloadVolumeTotal(wayBIllEntity.getUnloadVolumeTotal().setScale(2,BigDecimal.ROUND_HALF_UP));
		}
		if(wayBIllEntity.getUnloadWeightTotal() != null){
			wayBIllEntity.setUnloadWeightTotal(wayBIllEntity.getUnloadWeightTotal().setScale(2,BigDecimal.ROUND_HALF_UP));
		}
		return this.getSqlSession().update(NAMESPACE+"updateUnloadWayBillEntity", wayBIllEntity);
	}
	/** 
	 * 更新扫描流水号
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-12-20 下午3:25:14
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#updateUnloadSerialNoEntity(com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadSerialNoDetailEntity)
	 */
	@Override
	public int updateUnloadSerialNoEntity(
			UnloadSerialNoDetailEntity serialNoEntity) {
		return this.getSqlSession().update(NAMESPACE+"updateUnloadSerialNoEntity", serialNoEntity);
	}
	/** 
	 * 查询卸车运单明细
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-12-20 下午3:58:23
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryUnloadWayBillDetail(com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadWaybillDetailEntity)
	 */
	@Override
	public UnloadWaybillDetailEntity queryUnloadWayBillDetail(
			UnloadWaybillDetailEntity wayBillEntity) {
		return (UnloadWaybillDetailEntity) this.getSqlSession().selectOne(NAMESPACE+"queryUnloadWayBillDetail", wayBillEntity);
	}
	/** 
	 * 查询卸车流水号明细
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-12-20 下午3:58:23
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryUnloadSerialNoEntity(com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadSerialNoDetailEntity)
	 */
	@Override
	public UnloadSerialNoDetailEntity queryUnloadSerialNoEntity(
			UnloadSerialNoDetailEntity serialNoEntity) {
		return (UnloadSerialNoDetailEntity) this.getSqlSession().selectOne(NAMESPACE+"queryUnloadSerialNoEntity",serialNoEntity);
	}
	/** 
	 * 查询卸车交接单中流水号:交接单号、运单号、流水号
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-12-21 上午9:02:07
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryHandOverUnloadSerialDetail(java.util.List)
	 */
	@Override
	public List<UnloadGoodsSerialDetailDto> queryHandOverUnloadSerialDetail(
			List<UnloadBillDetailDto> bills) {
		return this.getSqlSession().selectList(NAMESPACE+"queryHandOverUnloadSerialDetail", bills);
	}
	/** 
	 *查询卸车配载单中流水号：配载单号、运单号、流水号
	 *
	 *
	 * @author dp-duyi
	 * @date 2012-12-21 上午9:02:07
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryAssembleUnloadSerialDetail(java.util.List)
	 */
	@Override
	public List<UnloadGoodsSerialDetailDto> queryAssembleUnloadSerialDetail(
			List<UnloadBillDetailDto> bills) {
		return this.getSqlSession().selectList(NAMESPACE+"queryAssembleUnloadSerialDetail", bills);
	}
	/** 
	 * 查询卸车接送货单据：单据编号、运单号、流水号
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-12-21 上午9:02:07
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryPickUpUnloadSerialDetail(java.util.List)
	 */
	@Override
	public List<UnloadGoodsSerialDetailDto> queryPickUpUnloadSerialDetail(
			List<UnloadBillDetailDto> bills) {
		return this.getSqlSession().selectList(NAMESPACE+"queryPickUpUnloadSerialDetail", bills);
	}
	
	
	/**
	 * 查询商务专递 卸车单据：单据编号、运单号、流水号
	 * @author 263072
	 * @date 2015-10-24 15:11:35
	 */
	@Override
	public List<UnloadGoodsSerialDetailDto> queryAireHandOverUnloadSerialDetail(List<UnloadBillDetailDto> bills){
		return this.getSqlSession().selectList(NAMESPACE+"queryAirUnloadSerialDetail", bills);
	}
	
	/** 
	 * 查询快递集中卸货单据：单据编号、运单号、流水号 
	 * 
	 * 
	 * @author zwd 200968 
	 * @date 2015-3-17 上午11:22:07
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryEWayUnloadSerialDetail(java.util.List)
	 */
	@Override
	public List<UnloadGoodsSerialDetailDto> queryEWayUnloadSerialDetail(
			List<UnloadBillDetailDto> bills) {
		return this.getSqlSession().selectList(NAMESPACE+"queryEWayUnloadSerialDetail", bills);
	}
		
	/** 
	 * 查询卸车扫描记录
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-12-21 上午9:33:37
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryUnloadScanSerialDetail(java.lang.String)
	 */
	@Override
	public List<UnloadSerialNoDetailDto> queryUnloadScanSerialDetailByTaskId(
			String taskId) {
		return this.getSqlSession().selectList(NAMESPACE+"queryUnloadScanSerialDetailByTaskId", taskId);
	}
	/** 
	 * 插入更换标签
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-12-22 上午11:20:48
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#insertChangeLabelGoodsEntity(com.deppon.foss.module.transfer.unload.api.shared.domain.ChangeLabelGoodsEntity)
	 */
	@Override
	public int insertChangeLabelGoodsEntity(
			ChangeLabelGoodsEntity changeLabelGoods) {
		return this.getSqlSession().insert(NAMESPACE+"insertChangeLabelGoodsEntity", changeLabelGoods);
	}
	/** 
	 * 删除更换标签
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-12-22 上午11:20:48
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#deleteChangeLabelGoodsEntity(com.deppon.foss.module.transfer.unload.api.shared.domain.ChangeLabelGoodsEntity)
	 */
	@Override
	public int deleteChangeLabelGoodsEntity(
			ChangeLabelGoodsEntity changeLabelGoods) {
		return this.getSqlSession().delete(NAMESPACE+"deleteChangeLabelGoodsEntity", changeLabelGoods);
	}
	/** 
	 * 插入明细
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2013-1-7 下午4:51:56
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#insertUnloadBillDetails(java.util.List)
	 */
	@Override
	public int insertUnloadBillDetails(
			List<UnloadBillDetailEntity> unloadBillEntitys) {
		return batchSaveProcessDao.batchSaveProcess(UnloadConstants.BATCH_SAVE_TYPE_INSERT, NAMESPACE+"insertUnloadBillDetails", unloadBillEntitys);
	}
	/** 
	 * 
	 * @author dp-duyi
	 * @date 2013-4-20 下午5:18:05
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#updateAssignUnloadTaskStateByState(com.deppon.foss.module.transfer.unload.api.shared.domain.AssignUnloadTaskEntity, java.lang.String)
	 */
	@Override
	public int updateAssignUnloadTaskStateByState(
			AssignUnloadTaskEntity unloadTask, String beforeState) {
		Map<String,Object> condition  = new HashMap<String,Object>();
		condition.put("unloadTask", unloadTask);
		condition.put("beforeState", beforeState);
		return this.getSqlSession().update(NAMESPACE+"updateAssignUnloadTaskStateByState", condition);
	}
	
	/**
	 * 根据状态更新已分配卸车任务状态 商务专递 2015-10-12 13:59:54
	 * @param unloadTask
	 * @param beforeState
	 * @author 263072 
	 * @return
	 */
	@Override
	public int updateAssignUnloadTaskStateByStateForDEAP(
			AssignUnloadTaskEntity unloadTask, String beforeState) {
		Map<String,Object> condition  = new HashMap<String,Object>();
		condition.put("unloadTask", unloadTask);
		condition.put("beforeState", beforeState);
		return this.getSqlSession().update(NAMESPACE+"updateAssignUnloadTaskStateByStateForDEAP", condition);
	}
	
	/** 
	 * //查询卸车多货货物
	 * @author dp-duyi
	 * @date 2013-4-22 下午4:32:13
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryUnloadMoreGoods(java.lang.String)
	 */
	@Override
	public List<UnloadGoodsSerialDetailDto> queryUnloadMoreGoods(String taskNo) {
		return this.getSqlSession().selectList(NAMESPACE+"queryUnloadMoreGoods", taskNo);
	}
	/** 
	 * 查询车辆中到达部门非本部门的单据
	 * @author dp-duyi
	 * @date 2013-4-24 上午10:03:31
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryUnOrgBillInVehicle(java.util.List, java.util.List)
	 */
	@Override
	public List<ArriveBillDto> queryUnArriveBillInVehicle(List<String> orgCodes,
			List<String> unloadBills) {
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("orgCodes", orgCodes);
		condition.put("unloadBills", unloadBills);
		return this.getSqlSession().selectList(NAMESPACE+"queryUnArriveBillInVehicle",condition);
	}
	/** 
	 * 查询货物是否在交接单中
	 * @author dp-duyi
	 * @date 2013-4-24 上午10:03:31
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryGoodsBeInHandOverBill(java.util.List, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean queryGoodsBeInHandOverBill(List<String> billNos,
			String wayBillNo, String serialNo) {
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("billNos", billNos);
		condition.put("wayBillNo", wayBillNo);
		condition.put("serialNo", serialNo);
		long qty = (Long) this.getSqlSession().selectOne(NAMESPACE+"queryGoodsBeInHandOverBill", condition);
		if(qty > 0){
			return true;
		}else{
			return false;
		}
	}
	/** 
	 * 查询货物是否在配载单中
	 * @author dp-duyi
	 * @date 2013-4-24 上午10:03:31
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryGoodsBeInAssembleBill(java.util.List, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean queryGoodsBeInAssembleBill(List<String> billNos,
			String wayBillNo, String serialNo) {
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("billNos", billNos);
		condition.put("wayBillNo", wayBillNo);
		condition.put("serialNo", serialNo);
		long qty = (Long) this.getSqlSession().selectOne(NAMESPACE+"queryGoodsBeInAssembleBill", condition);
		if(qty > 0){
			return true;
		}else{
			return false;
		}
	}
	/** 
	 * 查询卸车运单
	 * @author dp-duyi
	 * @date 2013-4-24 上午11:43:04
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryUnloadGoodsDetail(java.lang.String, java.lang.String)
	 */
	@Override
	public UnloadGoodsSerialDetailDto queryUnloadGoodsDetail(String wayBillNo,
			String serialNo,String orgCode) {
		Map<String,String> condition = new HashMap<String,String>();
		condition.put("wayBillNo", wayBillNo);
		condition.put("serialNo", serialNo);
		condition.put("orgCode", orgCode);
		List<UnloadGoodsSerialDetailDto> coll = this.getSqlSession().selectList(NAMESPACE+"queryUnloadGoodsDetail", condition);
		if(CollectionUtils.isNotEmpty(coll)){
			return coll.get(0);
		}else{
			return null;
		}
	}
	/** 
	 * 查询货物是否在接送货单据中，如果存在，则返回接送送件数
	 * @author dp-duyi
	 * @date 2013-4-24 下午6:50:36
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryGoodsBeInPickUpBill(java.util.List, java.lang.String, java.lang.String)
	 */
	@Override
	public UnloadBillDetailDto queryGoodsBeInPickUpBill(String taksNo,
			String wayBillNo, String serialNo) {
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("taksNo", taksNo);
		condition.put("wayBillNo", wayBillNo);
		condition.put("serialNo", serialNo);
		List<UnloadBillDetailDto> coll = this.getSqlSession().selectList(NAMESPACE+"queryGoodsBeInPickUpBill", condition);
		if(CollectionUtils.isNotEmpty(coll)){
			return coll.get(0);
		}else{
			return null;
		}
	}
	/** 
	 * TODO（方法详细描述说明、方法参数的具体涵义）
	 * @author dp-duyi
	 * @date 2013-4-28 上午9:12:32
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryPickUpBillByGoods(java.util.List, java.lang.String, java.lang.String)
	 */
	@Override
	public UnloadBillDetailDto queryPickUpBillByGoods(List<String> billNos,
			String wayBillNo, String serialNo) {
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("billNos", billNos);
		condition.put("wayBillNo", wayBillNo);
		condition.put("serialNo", serialNo);
		List<UnloadBillDetailDto> coll = this.getSqlSession().selectList(NAMESPACE+"queryPickUpBillByGoods", condition);
		if(CollectionUtils.isNotEmpty(coll)){
			return coll.get(0);
		}else{
			return null;
		}
	}
	/** 
	 * TODO（方法详细描述说明、方法参数的具体涵义）
	 * @author dp-duyi
	 * @date 2013-4-28 上午9:12:32
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryHandOverBillByGoods(java.util.List, java.lang.String, java.lang.String)
	 */
	@Override
	public UnloadBillDetailDto queryHandOverBillByGoods(
			List<String> billNos, String wayBillNo, String serialNo) {
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("billNos", billNos);
		condition.put("wayBillNo", wayBillNo);
		condition.put("serialNo", serialNo);
		List<UnloadBillDetailDto> coll = this.getSqlSession().selectList(NAMESPACE+"queryHandOverBillByGoods", condition);
		if(CollectionUtils.isNotEmpty(coll)){
			return coll.get(0);
		}else{
			return null;
		}
	}
	/** 
	 * TODO（方法详细描述说明、方法参数的具体涵义）
	 * @author dp-duyi
	 * @date 2013-4-28 上午9:12:32
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryAssembleBillByGoods(java.util.List, java.lang.String, java.lang.String)
	 */
	@Override
	public UnloadBillDetailDto queryAssembleBillByGoods(
			List<String> billNos, String wayBillNo, String serialNo) {
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("billNos", billNos);
		condition.put("wayBillNo", wayBillNo);
		condition.put("serialNo", serialNo);
		List<UnloadBillDetailDto> coll = this.getSqlSession().selectList(NAMESPACE+"queryAssembleBillByGoods", condition);
		if(CollectionUtils.isNotEmpty(coll)){
			return coll.get(0);
		}else{
			return null;
		}
	}
	/** 
	 * @Title: getLoaderParticipationByTaskId 
	 * @Description: 根据卸车任务ID获取卸车任务创建人 
	 * @param taskId
	 * @return    
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#getLoaderParticipationByTaskId(java.lang.String)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-7-22下午5:11:06
	 */ 
	@Override
	public LoaderParticipationEntity getLoaderParticipationByTaskId(
			String taskId) {
		return (LoaderParticipationEntity) this.getSqlSession().selectOne(NAMESPACE+"getLoaderParticipationByTaskId", taskId);
	}
	/**
	 * 重写更新卸车运单明细
	 */
	@Override
	public int newUpdateUnloadWayBillEntity(
			UnloadWaybillDetailEntity wayBillEntity,String serialNo,Date scanTime,String goodsState) {
		if(wayBillEntity.getVolume() != null){
			wayBillEntity.setVolume(wayBillEntity.getVolume().setScale(2,BigDecimal.ROUND_HALF_UP));
		}
		if(wayBillEntity.getWeight() != null){
			wayBillEntity.setWeight(wayBillEntity.getWeight().setScale(2,BigDecimal.ROUND_HALF_UP));
		}
		if(wayBillEntity.getUnloadVolumeTotal() != null){
			wayBillEntity.setUnloadVolumeTotal(wayBillEntity.getUnloadVolumeTotal().setScale(2,BigDecimal.ROUND_HALF_UP));
		}
		if(wayBillEntity.getUnloadWeightTotal() != null){
			wayBillEntity.setUnloadWeightTotal(wayBillEntity.getUnloadWeightTotal().setScale(2,BigDecimal.ROUND_HALF_UP));
		}
		wayBillEntity.setModifyDate(new Date());
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("wayBillEntity", wayBillEntity);
		condition.put("serialNo", serialNo);
		condition.put("scanTime", scanTime);
		condition.put("goodsState", goodsState);
		return this.getSqlSession().update(NAMESPACE+"newUpdateUnloadWayBillEntity", condition);
	}
	@Override
	public int newUpdateUnloadSerialNoEntity(
			UnloadSerialNoDetailEntity serialNoEntity) {
		return this.getSqlSession().update(NAMESPACE+"newUpdateUnloadSerialNoEntity", serialNoEntity);
	}
	/**
	 * 
	 * <p>通过单据编号查询封签信息</p> 
	 * @author alfred
	 * @date 2014-9-12 下午7:09:18
	 * @param billNos
	 * @param vehicleNo
	 * @return 
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#querySealByBillNo(java.util.List, java.lang.String)
	 */
	@Override
	public List<SealEntity> querySealByBillNo(List<String> billNos,
			String vehicleNo) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("vehicleNo", vehicleNo);
		param.put("billNos", billNos);
		param.put("sealType", SealConstant.SEAL_TYPE_INVALID);
		return this.getSqlSession().selectList(NAMESPACE+"querySealByBillNo", param);
	}
	
	/**
	 * 
	 * <p>查询包运单明细，去除重复运单号记录</p> 
	 * @author alfred
	 * @date 2014-10-29 上午10:59:40
	 * @param packageNo
	 * @return 
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryScanPackageDetails(java.lang.String)
	 */
	@Override
	public List<ExpressPackageDetailEntity> queryScanPackageDetails(
			String packageNo) {
		return this.getSqlSession().selectList(NAMESPACE+"queryScanPackageDetails", packageNo);
	
	}
	
	/**
	 * 
	 * <p>查询包中某个运单的流水号</p> 
	 * @author alfred
	 * @date 2014-10-29 下午2:36:41
	 * @param packageNo
	 * @param waybillNo
	 * @return 
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryPackageSerialNo(java.lang.String, java.lang.String)
	 */
	@Override
	public List<String> queryPackageSerialNo(String packageNo, String waybillNo) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("packageNo", packageNo);
		param.put("waybillNo", waybillNo);
		return this.getSqlSession().selectList(NAMESPACE+"queryPackageSerialNo", param);
	}
	
	/**
	 * 
	 * <p>批量插入流水号</p> 
	 * @author alfred
	 * @date 2014-10-29 下午3:25:39
	 * @param serialNoEntitys
	 * @return 
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#insertUnloadSerialNos(java.util.List)
	 */
	@Override
	public int insertUnloadSerialNos(
			List<UnloadSerialNoDetailEntity> serialNoEntitys) {
		return batchSaveProcessDao.batchSaveProcess(UnloadConstants.BATCH_SAVE_TYPE_INSERT, NAMESPACE+"insertUnloadSerialNos", serialNoEntitys);

	}
	
	/**
	 * 
	 * <p>查询卸车明细中是否对这包号进行扫描</p> 
	 * @author alfred
	 * @date 2014-10-29 下午5:05:19
	 * @param taskid
	 * @param packageNo
	 * @return 
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryIsPackageScan(java.lang.String, java.lang.String)
	 */
	@Override
	public List<UnloadWaybillDetailEntity> queryIsPackageScan(String taskid, String packageNo) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("taskid", taskid);
		param.put("packageNo", packageNo);
		return this.getSqlSession().selectList(NAMESPACE+"queryIsPackageScan", param);
	}
	
	/**
	 * 
	 * <p>更新包扫描运单明细</p> 
	 * @author alfred
	 * @date 2014-10-29 下午5:31:28
	 * @param wayBillEntity
	 * @return 
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#updatePackageWayBillEntity(com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadWaybillDetailEntity)
	 */
	@Override
	public int updatePackageWayBillEntity(
			UnloadWaybillDetailEntity wayBillEntity) {
		return this.getSqlSession().update(NAMESPACE+"updatePackageWayBillEntity", wayBillEntity);
	}
	
	/**
	 * 
	 * <p>查询包中某个运单明细</p> 
	 * @author alfred
	 * @date 2014-10-30 上午10:21:08
	 * @param waybillNo
	 * @param packageNo
	 * @return 
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryPackageDetailsByWaybill(java.lang.String, java.lang.String)
	 */
	@Override
	public ExpressPackageDetailEntity queryPackageDetailsByWaybill(String waybillNo,
			String packageNo) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("waybillNo", waybillNo);
		param.put("packageNo", packageNo);
		List<ExpressPackageDetailEntity> coll =  this.getSqlSession().selectList(NAMESPACE+"queryPackageDetailsByWaybill", param);
		if(CollectionUtils.isNotEmpty(coll)){
			return coll.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 
	 * <p>批量更新包中某个运单的流水号</p> 
	 * @author alfred
	 * @date 2014-10-30 下午2:38:44
	 * @param serialNoEntity
	 * @return 
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#updateUnloadPackageSerialNo(com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadSerialNoDetailEntity)
	 */
	@Override
	public int updateUnloadPackageSerialNo(
			UnloadSerialNoDetailEntity serialNoEntity) {
		return this.getSqlSession().update(NAMESPACE+"updateUnloadPackageSerialNo", serialNoEntity);
	}
	
	/**
	 * 
	 * <p>查询包信息</p> 
	 * @author alfred
	 * @date 2014-11-4 上午10:09:50
	 * @param bills
	 * @return 
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryPackageUnloadDetail(java.util.List)
	 */
	@Override
	public List<UnloadGoodsSerialDetailDto> queryPackageUnloadDetail(
			List<UnloadBillDetailDto> bills) {
		return this.getSqlSession().selectList(NAMESPACE+"queryPackageUnloadDetail", bills);
	}
	
	/**
	 * 
	 * <p>查询强卸包货物</p> 
	 * @author alfred
	 * @date 2014-11-16 上午11:10:06
	 * @param taskNo
	 * @return 
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryUnloadMorePackageGoods(java.lang.String)
	 */
	@Override
	public List<UnloadGoodsSerialDetailDto> queryUnloadMorePackageGoods(
			String taskNo) {
		return this.getSqlSession().selectList(NAMESPACE+"queryUnloadMorePackageGoods", taskNo);
	}
	
	/**
	 * 
	 * <p>根据配载单号查询包交接单</p> 
	 * @author alfred
	 * @date 2014-11-16 下午8:28:17
	 * @param vehicleassembleNo
	 * @return 
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryHandoverByNO(java.lang.String)
	 */
	@Override
	public List<UnloadBillDetailDto> queryHandoverByNO(String vehicleassembleNo) {
		return this.getSqlSession().selectList(NAMESPACE+"queryHandoverByNO",vehicleassembleNo);
	}
	
	/**
	 * 
	 * <p>查询交接单中卸车包信息</p> 
	 * @author alfred
	 * @date 2014-11-18 下午7:46:35
	 * @param bills
	 * @return 
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryPackageHandUnloadDetail(java.util.List)
	 */
	@Override
	public List<UnloadGoodsSerialDetailDto> queryPackageHandUnloadDetail(
			List<UnloadBillDetailDto> bills) {
		return this.getSqlSession().selectList(NAMESPACE+"queryPackageHandUnloadDetail", bills);
	}
	
	/**
	 * 
	 * <p>查询配载单中卸车包信息</p> 
	 * @author alfred
	 * @date 2014-11-18 下午7:46:40
	 * @param bills
	 * @return 
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryPackageAssembleDetail(java.util.List)
	 */
	@Override
	public List<UnloadGoodsSerialDetailDto> queryPackageAssembleDetail(
			List<UnloadBillDetailDto> bills) {
		return this.getSqlSession().selectList(NAMESPACE+"queryPackageAssembleDetail", bills);
	}

	/**
	 * 
	 * <p>查询卸车航空正单单据</p> 
	 * @author alfred
	 * @date 2014-10-23 上午11:16:13
	 * @param bills
	 * @param taskId
	 * @return 
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryAirUnloadDetail(java.util.List, java.lang.String)
	 */
	@Override
	public List<UnloadGoodsSerialDetailDto> queryAirUnloadDetail(
			List<UnloadBillDetailDto> bills, String taskId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("taskId", taskId);
		param.put("bills", bills);
		return this.getSqlSession().selectList(NAMESPACE+"queryAirUnloadDetail", param);
	}
	
	/**
	 * 查询卸车航空正单交接单单据
	 * @Author 263072
	 * @Date 2015-9-11 15:12:15
	 * @param bills
	 * @param taskId
	 * @return
	 */
	@Override
	public List<UnloadGoodsSerialDetailDto> queryAirHandoverUnloadDetail(
			List<UnloadBillDetailDto> bills, String taskId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("taskId", taskId);
		param.put("bills", bills);
		return this.getSqlSession().selectList(NAMESPACE+"queryAirHandoverUnloadDetail", param);
	}
	/**
	 * 
	 * <p>根据货物查询航空正单单据</p> 
	 * @author alfred
	 * @date 2014-10-23 下午2:51:04
	 * @param billNos
	 * @param wayBillNo
	 * @param serialNo
	 * @return 
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryAirBillByGoods(java.util.List, java.lang.String, java.lang.String)
	 */
	@Override
	public UnloadBillDetailDto queryAirBillByGoods(List<String> billNos,
			String wayBillNo, String serialNo) {
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("billNos", billNos);
		condition.put("wayBillNo", wayBillNo);
		condition.put("serialNo", serialNo);
		List<UnloadBillDetailDto> coll = this.getSqlSession().selectList(NAMESPACE+"queryAirBillByGoods", condition);
		if(CollectionUtils.isNotEmpty(coll)){
			return coll.get(0);
		}else{
			return null;
		}
	}
	/**
	 * 
	 * <p>查询快递集中卸货单据</p> 
	 * @author zwd 200968
	 * @date 2015-2-5 上午11:16:13 queryAirUnloadDetail
	 * @param bills
	 * @param taskId
	 * @return 
	 */
	@Override
	public List<UnloadGoodsSerialDetailDto> queryEwaybillUnloadDetail(
			List<UnloadBillDetailDto> bills, String taskId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("taskId", taskId);
		param.put("bills", bills);
		return this.getSqlSession().selectList(NAMESPACE+"queryEwaybillUnloadDetail", param);
	}
	/** 
	 * 查询快递集中卸货 
	 * @author zwd 200968
	 * @date 2015-2-5 下午3:12:32
	 */
	public UnloadBillDetailDto queryEwayBillByGoods(List<String> billNos,
			String wayBillNo, String serialNo) {
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("billNos", billNos);
		condition.put("wayBillNo", wayBillNo);
		condition.put("serialNo", serialNo);
		List<UnloadBillDetailDto> coll = this.getSqlSession().selectList(NAMESPACE+"queryEwayBillByGoods", condition);
		if(CollectionUtils.isNotEmpty(coll)){
			return coll.get(0);
		}else{
			return null;
		}
	}
	/**
	 * 插入卸车扫描相关信息到异步表中 
	 * @author 105869
	 * @date 2015年3月5日 16:16:07
	 * @param unloadAsyncMsgs
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#insertUnloadAsyncMsg(java.util.List)
	 */
	@Override
	public int insertUnloadAsyncMsg(List<PDAUnloadAsyncBillMsgDto> unloadAsyncMsgs){
		if(CollectionUtils.isEmpty(unloadAsyncMsgs)){
			return 0;
		}
		return batchSaveProcessDao.batchSaveProcess(UnloadConstants.BATCH_SAVE_TYPE_INSERT,NAMESPACE+"insertUnloadAsyncMsg", unloadAsyncMsgs);
//		int totalnum=0;
//		if(CollectionUtils.isNotEmpty(unloadAsyncMsgs)){
//			for(PDAUnloadAsyncBillMsgDto dto:unloadAsyncMsgs){
//				int num=this.getSqlSession().insert(NAMESPACE+"insertUnloadAsyncMsg", dto);
//				totalnum=num+totalnum;
//			}
//		}
//		return totalnum;
	}
	
	/**
	 * 删除卸车运单入库信息临时表
	 * @author 105869
	 * @date 2015年3月12日 09:12:23
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#deleteUnloadAsyncMsg(java.util.List)
	 */
	@Override
	public int deleteUnloadAsyncMsg(List<String> ids) {
		int i=0;
		for(String id:ids){
			 this.getSqlSession().delete(NAMESPACE+"deleteUnloadAsyncMsg", id);
			 i++;
		}
		return i;
	}
	/**
	 * 加业务锁--设置jobId
	 * @author 105869
	 * @date 2015年3月21日 14:56:13
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#upateUnloadMsgForJob(java.lang.String)
	 */
	@Override
	public int upateUnloadMsgForJob(String jobId,int dataLimit ) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("jobId", jobId);
		map.put("dataLimit", dataLimit);
		return this.getSqlSession().update(NAMESPACE+"upateUnloadMsgForJob", map);
	}
	/**
	 * 查询需要入库的运单信息通过jobId
	 * @author 105869
	 * @date 2015年3月21日 15:49:05 
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryInStockMsg(java.lang.String)
	 */
	@Override
	public List<PDAUnloadAsyncBillMsgDto> queryInStockMsg(String jobId) {
		
		return this.getSqlSession().selectList(NAMESPACE+"queryInStockMsg", jobId);
	}
	/**
	 * 更新jobid 还原初始状态N/A
	 * 
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#updateUnloadAsyncBillJobId(java.util.List)
	 */
	@Override
	public int updateUnloadAsyncBillJobId( List<PDAUnloadAsyncBillMsgDto> inStockMsgs) {
		for(PDAUnloadAsyncBillMsgDto parameter:inStockMsgs){
			
			parameter.setJobId(UnloadConstants.UNLOAD_INSTOCK_MSG_JOBID);
			this.getSqlSession().update(NAMESPACE+"updateUnloadAsyncBillJobId", parameter);
			
		}
		return 0;
	}
	
	/**
	 * 重置数据 站位符
	 * @author 105869
	 * @date 2015年3月25日 15:53:35
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#reSetUnloadInstockMsg()
	 */
	@Override
	public int reSetUnloadInstockMsg() {
		Map<String,String> paramter =new HashMap<String,String>();
		paramter.put("jobId", UnloadConstants.UNLOAD_INSTOCK_MSG_JOBID);
		return this.getSqlSession().update(NAMESPACE+"reSetUnloadInstockMsg", paramter);
	}
	
	/**
	 * 二程接驳卸车 创建任务后 状态修改
	 *  @author 218427
	 *  
	 * */
	public int updateAssignSCUnloadTaskStateByState(List<String> billNoList,
			int connectionBillState, String billAssignState){
		Map<String,Object> condition  = new HashMap<String,Object>();
		condition.put("billNoList", billNoList);
		condition.put("connectionBillState", connectionBillState);
		condition.put("billAssignState", billAssignState);

		return this.getSqlSession().update(NAMESPACE+"pda_updateAssignSCUnloadTaskState",condition);
	}
	/**询卸车单据
	 *  @author 218427
	 *  
	 * */
	@Override
	public List<UnloadBillDetailDto> querySCUnloadBillsByBillNo(
			List<String> billNos) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(NAMESPACE+"querySCUnloadBillsByBillNo", billNos);
	}
	/**
	 * 插入卸车时间
	 *  @author 218427
	 *  
	 * */
//	@Override
//	public int updateSCArrivalTime(List<String> billNos){
//		return this.getSqlSession().insert(NAMESPACE+"updateSCArrivalTime",billNos);
//	}
	/**
	 * 
	 * <p>查询二程接驳卸货单据</p> 
	 * @author hwy 218427
	 * @date 2015-5-15  下午11:16:13 queryAirUnloadDetail
	 * @param bills
	 * @param taskId
	 * @return 
	 */
	@Override
	public List<UnloadGoodsSerialDetailDto> querySCUnloadDetail(
			List<UnloadBillDetailDto> bills, String taskId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("taskId", taskId);
		param.put("bills", bills);
		return this.getSqlSession().selectList(NAMESPACE+"querySCbillUnloadDetail", param);
	}

	public int updateState(Map<String, Object> condition){
		return this.getSqlSession().update(NAMESPACE+"updateSCbillUnloadState", condition);
	}
 
	/** 
	 * 根据任务编号查询二程接驳卸车任务
	 * 
	 * 
	 * @author dp-hwy
	 * @date 2015-05-18 上午10:24:16
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryUnloadTaskByTaskNo(java.lang.String)
	 */
	@Override
	public UnloadTaskEntity querySCUnloadTaskByTaskNo(String taskNo) {
		return (UnloadTaskEntity) this.getSqlSession().selectOne(NAMESPACE+"querySCUnloadTaskByTaskNo", taskNo);
	}
	
	
	/** 
	 * 根据任务编号查询二程接驳卸车任务
	 * @author dp-hwy
	 * @date 2015-05-27 上午09:31:16
	 */
    @Override
    public List<UnloadGoodsSerialDetailDto> querySCUnloadSerialDetail(
    		List<UnloadBillDetailDto> bills) {
    	return this.getSqlSession().selectList(NAMESPACE+"querySCUnloadSerialDetail", bills);
    }
	
    
    @Override
	public String querySCUnloadOrgCode(String waybillNo) {
		
    	List<String> resultList=new ArrayList<String>();
    	String orgCode=null;
    	resultList=this.getSqlSession().selectList(NAMESPACE+"querySCUnloadOrgCode",waybillNo);
		if(CollectionUtils.isNotEmpty(resultList)){
			orgCode= resultList.get(0);
		}
    	return orgCode;
	}

    /**通过运单号查询所有卸车流水信息(流水、时间)*/
	public List<UnloadSerialDetaiDto> queryUnloadSerialDetailByWaybillNo(String waybillNo){
		return this.getSqlSession().selectList(NAMESPACE+"queryUnloadSerialDetailByWaybillNo", waybillNo);
	}

}