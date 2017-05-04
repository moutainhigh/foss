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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/server/dao/impl/PDALoadDao.java
 *  
 *  FILE NAME          :PDALoadDao.java
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
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * PROJECT NAME: tfr-load
 * 
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.server.dao.impl
 * FILE    NAME: PDATransferLoadDao.java
 * 
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 * 
 * 功能：
 * 
 * 
 * 一、建立装车任务
 * 
 * 
 * 1、PDA建立装车任务：
 * 
 * 
 * 		FOSS检验是否可以建立装车任务（参见业务规则SR-10,SR-12）
 * 
 * 2、若可以建立装车任务，生成装车任务编号，（参见业务规则SR-1、SR-2、SR-3）
 * 
 * 3、	若可以建立装车任务，调用FOSS接口同步装车任务、装车理货员、装车PDA，将信息同步至FOSS(参见业务规则SR-11)
 * 	
 * 1、	每5分钟(由PDA定义和发起)调用此接口更新装车清单	返回装车清单（参见业务规则SR-1、SR-2、SR-3）
 * 
 * 3、理货员手动点击刷新时，PDA调用此接口更新装车清单		
 * 
 *	调用月台接口		修改月台状态为使用中，记录月台使用车牌号，开始使用时间，结束使用时间（参见业务规则SR-7）
 *  其他PDA共同参与此装车任务	1、	若加入理货员在该装车任务理货员中
 *  
 * 2、	返回装车清单（参见业务规则SR-1、SR-2、SR-3）
 * 
 * 
 * 3、	调用FOSS接口，将理货员加入/离开信息同步至FOSS
 * 
 * 
 * 二、逐件扫描
 * 
 * PDA向FOSS提供一条装车数据	
 * 
 *  若货物在【装车货物清单】中，则调用FOSS接口同步货物扫描状态为已扫描、货物状态为正常、装车件数+1，至FOSS
 *  
 *	 返回装车记录ID给PDA
 *
 *
 *
 * 三、查询非装车清单货物
 * 
 * 若货物不在【装车货物清单】中则调用FOSS接口
 * 
 * 1、判断货物状态、是否强装、装车件数（参见业务规则SR-4）
 * 
 * 
 * 2、记录该装车记录，货物扫描状态为已扫描、货物状态为上述货物状态，至FOSS
 * 
 * 3、返回装车记录ID给PDA
 * 
 * 四、修改理货员
 * 
 * PDA添加/删除理货员	: 调用FOSS接口，将理货员加入/离开信息同步至FOSS
 * 
 * 五、撤销装车任务
 * 
 * 
 * PDA撤销装车任务（参见业务规则SR-6）	
 * 
 * 	:PDA调用FOSS接口，同步【装车任务】中任务状态为是至FOSS
 * 
 * 调用月台接口		
 * 
 * 	1、	修改月台状态为空闲中，记录月台历史记录，车牌号，开始使用时间，结束使用时间（任务提交时间）
 *
 * 
 * 六、提交装车任务
 * 
 * a、PDA确认提交装车任务:
 * 
 * 		1、	PDA调用FOSS接口同步【装车PDA】中离开任务时间、【装车理货员】中离开任务时间至FOSS
 * 
 *		2、	同步【装车任务】中装车结束时间、任务状态至FOSS；（参见业务规则SR-5）
 *
 *		3、	【装车货物清单】中非强装货物出库；
 *
 *		4、	记录“多货-夹带”货物货物轨迹，上一库存部门出库，并通知上一库存部门出库
 *
 *		5、	“强装-异地夹带”货物本部门入库，上一库存部门出库，并通知上一库存部门出库，重新生成走货路径
 *
 *  b、调用月台接口
 *  
 *  	修改月台状态为空闲中，记录月台历史记录，车牌号，开始使用时间，结束使用时间（任务提交时间）
 *  
 *  
 * 
 * 
 * 业务规则：
 * 
 * SR-1	装车清单中货物为库存为本部门，走货路径节点的下一环节为【装车任务】中到达部门，若需本部门打木架，则本部门已代打木架出库；
 *	若有发往本部门更改单，则更改单已受理的货物；
 *	贵重物品的库区状态发生变化，也更新给PDA
 *
 *
 * SR-2	若货物的运输性质为精准卡航、精准城运，则该货物为必走货；
 * 
 * 若货物的运输性质为精准汽运（长途）、精准汽运（短途）、精准空运、汽运偏线，
 * 
 * 且本部门库存时间超过48小时，则该货物为必走货
 * 
 * 
 * 
 * 
 * SR-3	是否强装默认为否、扫描状态默认为未扫描、货物状态默认为未装车、装车件数默认为0
 * 
 * 
 * 
 * SR-4	若标签已经作废，则直接返回PDA标签已作废，不执行下面操作
 * 
 *		若标签已经签收，则直接返回PDA标签已签收，不执行下面操作
 *
 *		若货物在本部门库存，但装车任务到达部门不为货物走货路径下一结点，则货物状态标记为“强装”，是否强装“是”；
 *		
 *		若货物不在本部门库存：
 *
 *			1）	若本部门为货物走货路径结点，且货物走货路径下一结点为【装车任务】中到达部门，则货物状态标记为“多货-夹带”，装车件数+1；
 *			
 *			2）	若本部门为货物走货路径结点，且货物走货路径下一结点不为【装车任务】中到达部门，则货物状态标记为“强装-夹带”，是否强装“是”；
 *
 *			3）	若本部门不为货物走货路径结点，则重新计算货物走货路径，若新走货路径下一节点为【装车任务】中到达部门，则货物标记为“多货-异地夹带”，装车件数+1；
 *
 *			4）	若本部门不为货物走货路径结点，则重新计算货物走货路径，若新走货路径下一节点不为【装车任务】中到达部门，则货物标记为“强装-异地夹带”，是否强装“是”；
 *
 *
 *
 * SR-6	只有建立任务PDA可以执行撤销操作
 * 
 * 
 * 
 * SR-7	若该车牌号在长短途发车计划中则结束时间为发车计划中规定发车时间，否则为空
 * 
 * 
 * 
 * SR-8	建立装车任务后任务状态为进行中，撤销装车任务后任务状态为已取消，提交装车任务后任务状态为已完成
 * 
 * 
 * 
 * SR-9	编号规则为：两位任务类别号码+6位年月日+5位流水号码，流水号每日更新一次，如0112110112345，其中，两位任务类别号码对应关系为：外场装车:01，营业部装车:05
 * 
 * 
 * 
 * SR-10	车辆必须存在车辆基础资料中，否则返回车辆不存在
 *	目的站必须在目的站基础资料中，否则返回目的站不存在
 *	若装车部门与目的站之间为长途（参见线路基础资料），则目的站与线路必须有相应发车计划（该线路、该车牌有班次未发车），否则返回无此发车计划
 *	若装车部门与目的站之间为短途（参见线路基础资料），且车辆为班车（车辆所属部门属性为班车车队且该车辆未借出或车辆所属部门属性为接送货车队且借调到班车车队），则目的站与线路必须有相应发车计划（该线路、该车牌有班次未发车），否则返回无此发车计划（派送装车、偏线装车、营业部装车不做此校验）
 *
 *
 *
 * SR-11	若货物有更改未受理，则货物状态为“强装-有更改” ，是否强装“是”；
 * 
 *		若货物需带打包装，但未打包装，则货物状态为“强装-未打包装” ，是否强装“是”；
 *
 *		若货物在木架区没有出库，则货物状态为“强装-代打木架未出库” ，是否强装“是”；
 *
 *		若货物在贵重物品区没有出库，则货物状态为“强装-贵重物品未出库” ，是否强装“是”；
 *
 *   	若货物的重量或体积为零，则不允许装车，则货物状态为“强装” ，是否强装“是”；
 *   
 *   
 *   
 * SR-12	PDA装车，先找发车计划，如果没有发车计划，则始发部门到达部门间必须要有线路，如果没有，则不能新增交接单，或者建立PDA装车任务
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */ 
package com.deppon.foss.module.transfer.load.server.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.common.api.shared.dto.OaReportClearMore;
import com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportWayBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadDestOrgEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadSerialNoEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadWaybillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.PDATaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoadGoodsDetailSerialDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoadTaskSerialNoDto;
import com.deppon.foss.module.transfer.pda.api.shared.domain.CreateDeliveryReceiptEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAAssignLoadTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.QueryAssignedLoadTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.WaybillInfoEntity;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDAGoodsSerialNoDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDAWaybillReturnDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PackagePathLoaderDto;
import com.deppon.foss.module.transfer.unload.api.server.dao.IBatchSaveProcessDao;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;


/**
 * PDA中转装车dao
 * 
 * @author dp-duyi
 * 
 * @date 2012-11-19 上午10:36:46
 */
@SuppressWarnings("unchecked")
public class PDALoadDao extends iBatis3DaoImpl implements IPDALoadDao{
	private static final String NAMESPACE = "tfr-load."; 
	private IBatchSaveProcessDao batchSaveProcessDao;
	public void setBatchSaveProcessDao(IBatchSaveProcessDao batchSaveProcessDao) {
		this.batchSaveProcessDao = batchSaveProcessDao;
	}
	/** 
	 * 插入装车任务
	 * @param
	 * @author dp-duyi
	 * @date 2012-11-19 上午10:51:54
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#insertTransferLoadTask(com.deppon.foss.module.transfer.load.api.shared.domain.LoadTaskEntity)
	 */
	@Override
	public int insertTransferLoadTask(LoadTaskEntity loadTask) {
		return this.getSqlSession().insert(NAMESPACE+"insertLoadTask", loadTask);
	}
	/**
	 * 插入装车到达部门
	 * 
	 * 
	 * @param loadDestOrg
	 * @author dp-duyi
	 * @date 2012-11-19 上午10:51:54
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#insertTransferLoadDestOrg(java.util.List)
	 */
	@Override
	public int insertTransferLoadDestOrg(List<LoadDestOrgEntity> loadDestOrg) {
		return batchSaveProcessDao.batchSaveProcess(UnloadConstants.BATCH_SAVE_TYPE_INSERT, NAMESPACE+"insertLoadDestOrg", loadDestOrg);
	}
	/** 
	 * 插入装车理货员
	 * 
	 * @param loaders
	 * @author dp-duyi
	 * @date 2012-11-19 上午10:51:54
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#insertTransferLoaderParticipation(java.util.List)
	 */
	@Override
	public int insertTransferLoaderParticipation(
			List<LoaderParticipationEntity> loaders) {
		return batchSaveProcessDao.batchSaveProcess(UnloadConstants.BATCH_SAVE_TYPE_INSERT, NAMESPACE+"insertLoadLoader", loaders);
	}
	/** 
	 * 提交装车任务
	 * 
	 * 
	 * @param loadTask
	 * @author dp-duyi
	 * @date 2012-11-20 上午8:55:04
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#updateLoadTask(com.deppon.foss.module.transfer.load.api.shared.domain.LoadTaskEntity)
	 */
	@Override
	public int updateLoadTask(LoadTaskEntity loadTask) {
		return this.getSqlSession().update(NAMESPACE+"updateLoadTask", loadTask);
	}
	/** 
	 * 提交装车任务时更新理货员
	 * 
	 * 
	 * @param loadTask
	 * @author dp-duyi
	 * @date 2012-11-20 上午8:55:04
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#updateLoaderParticipationByLoadTask(com.deppon.foss.module.transfer.load.api.shared.domain.LoadTaskEntity)
	 */
	@Override
	public int updateLoaderParticipationByLoadTask(Map<String,Object> loadTask) {
		return this.getSqlSession().update(NAMESPACE+"updateLoaderParticipationByLoadTask", loadTask);
	}
	/** 
	 * 根据理货员更新理货员
	 * 
	 * 
	 * @param loaders
	 * @author dp-duyi
	 * @date 2012-11-20 上午8:55:04
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#updateLoaderParticipationByLoader(com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity)
	 */
	@Override
	public int updateLoaderParticipationByLoader(
			List<LoaderParticipationEntity> loaders) {
		return batchSaveProcessDao.batchSaveProcess(UnloadConstants.BATCH_SAVE_TYPE_UPDATE, NAMESPACE+"updateLoaderParticipationByLoader", loaders);
	}
	/** 
	 * 根据装车任务编号查询装车任务
	 * 
	 * 
	 * @param taskNo
	 * @author dp-duyi
	 * @date 2012-11-20 上午9:15:55
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#queryLoadTaskByTaskNo(java.lang.String)
	 */
	@Override
	public LoadTaskEntity queryLoadTaskByTaskNo(String taskNo) {
		return (LoadTaskEntity) this.getSqlSession().selectOne(NAMESPACE+"queryLoadTaskByTaskNo",taskNo);
	}
	
	/**
	 * 根据装车任务表的taskNo查找装车运单明细表中已装车件数小于运单表的货物总件数的运单号信息 
	 * 
	 * @param taskNo
	 * @author dp-zwd
	 */public List<String> queryWayBillNo(String taskNo){
		
		return this.getSqlSession().selectList(NAMESPACE+"queryWayBill",taskNo);
	}
	
	/** 
	 * 根据装车流水号id查询装车流水号及装车运单明细
	 * 
	 * 
	 * @param condition
	 * @author dp-duyi
	 * @date 2012-11-20 下午3:29:05
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#queryLoadTaskSerialNoDtoBySerialId(java.util.Map)
	 */
	@Override
	public LoadTaskSerialNoDto queryLoadTaskSerialNoDtoByCondition(		Map<String, String> condition) {
		return (LoadTaskSerialNoDto) this.getSqlSession().selectOne(NAMESPACE+"queryLoadTaskSerialNoDtoByCondition", condition);
	}
	/** 
	 * 更新装车运单明细中装车件数已操作件数
	 * 
	 * 
	 * @param loadWaybillDetailEntity
	 * @author dp-duyi
	 * @date 2012-11-20 下午3:39:56
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#updateLoadTaskWayBillDetail(com.deppon.foss.module.transfer.load.api.shared.domain.LoadWaybillDetailEntity)
	 */
	@Override
	public int updateLoadTaskWayBillDetail(LoadWaybillDetailEntity loadWaybillDetailEntity) {
		return this.getSqlSession().update(NAMESPACE+"updateLoadTaskWayBillDetail", loadWaybillDetailEntity);
	}
	/** 
	 * 删除装车运单明细
	 * 
	 * 
	 * @param id
	 * @author dp-duyi
	 * @date 2012-11-20 下午3:39:56
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#deleteLoadWaybillDetailEntity(java.lang.String)
	 */
	@Override
	public int deleteLoadWaybillDetailEntity(String id) {
		return this.getSqlSession().delete(NAMESPACE+"deleteLoadTaskWayBillDetail",id);
	}
	/** 
	 * 删除装车流水号明细
	 * 
	 * 
	 * @param id
	 * @author dp-duyi
	 * @date 2012-11-20 下午3:39:56
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#deleteLoadSerialNoEntity(java.lang.String)
	 */
	@Override
	public int deleteLoadSerialNoEntity(String id) {
		return this.getSqlSession().delete(NAMESPACE+"deleteLoadTaskSerialNo",id);
	}
	/** 
	 * 插入装车PDA
	 * 
	 * 
	 * @param pdaEntity
	 * @author dp-duyi
	 * @date 2012-12-5 下午2:00:09
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#insertPDATask(com.deppon.foss.module.transfer.load.api.shared.domain.PDATaskEntity)
	 */
	@Override
	public int insertPDATask(PDATaskEntity pdaEntity) {
		return this.getSqlSession().insert(NAMESPACE+"insertPDATask",pdaEntity);
	}
	/** 
	 *根据任务编号查询pda，
	 *
	 *
	 * @param condition
	 * condition：任务编号，是否建立任务PDA,任务类型
	 * @author dp-duyi
	 * @date 2012-12-5 下午4:01:37
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#queryPDATaskByTaskNo(java.util.Map)
	 */
	@Override
	public List<PDATaskEntity> queryPDATaskByTaskNo(
			Map<String, String> condition) {
		return this.getSqlSession().selectList(NAMESPACE+"queryPDATask", condition);
	}
	/** 
	 * 更新pdaTask:leaveTime,TaskNo,leaveTime
	 * 
	 * 
	 * @param parameter
	 * @author dp-duyi
	 * @date 2012-12-5 下午6:50:16
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#updatePDATaskEntity(java.util.Map)
	 */
	@Override
	public int updatePDATaskEntity(Map<String, Object> parameter) {
		return this.getSqlSession().update(NAMESPACE+"updatePDATaskEntity", parameter);
	}
	/** 
	 * 按照任务编号查询未提交任务PDA数
	 * 
	 * 
	 * @param taskNo
	 * @author dp-duyi
	 * @date 2012-12-6 下午1:55:52
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#queryUnSubmitPDAQty(java.lang.String)
	 */
	@Override
	public int queryUnSubmitPDAQty(String taskNo) {
		return (Integer)this.getSqlSession().selectOne(NAMESPACE+"queryUnSubmitPDAQty",taskNo);
	}
	/** 
	 * 根据任务编号查询扫描记录条数
	 * 
	 * 
	 * @param taskId
	 * @author dp-duyi
	 * @date 2012-12-6 下午4:40:30
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#queryScanSerialNoQTY(java.lang.String)
	 */
	@Override
	public int queryScanSerialNoQTYByTaskId(String taskId) {
		return (Integer)this.getSqlSession().selectOne(NAMESPACE+"queryScanSerialNoQTYByTaskId",taskId);
	}
	/** 
	 * 更新装车流水号信息
	 * 
	 * 
	 * @param loadSerialNoEntity
	 * @author dp-duyi
	 * @date 2012-12-7 下午2:18:15
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#updateLoadTaskSerialNo(com.deppon.foss.module.transfer.load.api.shared.domain.LoadSerialNoEntity)
	 */
	@Override
	public int updateLoadTaskSerialNo(LoadSerialNoEntity loadSerialNoEntity) {
		return this.getSqlSession().update(NAMESPACE+"updateLoadTaskSerialNo",loadSerialNoEntity);
	}	
	/** 
	 * 根据装车任务id，运单号查询装车运单明细
	 * 
	 * 
	 * @param loadTaskWayBillEntity
	 * @author dp-duyi
	 * @date 2012-12-7 下午4:16:39
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#queryLoadWaybillDetailEntityByWayBillNo(com.deppon.foss.module.transfer.load.api.shared.domain.LoadWaybillDetailEntity)
	 */
	@Override
	public LoadWaybillDetailEntity queryLoadWaybillDetailEntityByWayBillNo(LoadWaybillDetailEntity loadTaskWayBillEntity) {
		return (LoadWaybillDetailEntity) this.getSqlSession().selectOne(NAMESPACE+"queryLoadWaybillDetailEntityByWayBillNo",loadTaskWayBillEntity);
	}
	/** 
	 * 根据装车任务id，包号查询装车运单明细
	 * 
	 * 
	 * @param loadTaskWayBillEntity
	 * @author dp-zwd
	 * @date 2015-4-17 下午2:16:39
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#queryLoadWaybillDetailEntityByWayBillNo(com.deppon.foss.module.transfer.load.api.shared.domain.LoadWaybillDetailEntity)
	 */
	@Override
	public List<LoadWaybillDetailEntity> queryLoadWaybillDetailEntityByPackageNo(LoadWaybillDetailEntity loadTaskWayBillEntity) {
		return this.getSqlSession().selectList(NAMESPACE+"queryLoadWaybillDetailEntityByPackageNo",loadTaskWayBillEntity);
	}
	/**
	 * 
	 * <p>更新包扫描运单明细</p> 
	 * @date 2015-4-17下午3:3:28
	 * @param wayBillEntity
	 * @return 
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#updatePackageWayBillEntity(com.deppon.foss.module.transfer.load.api.shared.domain.LoadWaybillDetailEntity)
	 */
	@Override
	public int updatePackageWayBillEntity(
			LoadWaybillDetailEntity wayBillEntity) {
		return this.getSqlSession().update(NAMESPACE+"updatePackageWayBillEntity", wayBillEntity);
	}
	/** 
	 * 插入装车流水号明细记录
	 * 
	 * 
	 * @param loadWaybillDetailEntity
	 * @author dp-duyi
	 * @date 2012-12-7 下午4:33:04
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#insertLoadWayBillDetailEntity(com.deppon.foss.module.transfer.load.api.shared.domain.LoadWaybillDetailEntity)
	 */
	@Override
	public int insertLoadWayBillDetailEntity(LoadWaybillDetailEntity loadWaybillDetailEntity) {
		if(null == loadWaybillDetailEntity.getModifyDate()){
			//配合BI，新增修改时间
			loadWaybillDetailEntity.setModifyDate(new Date());
		}
		return this.getSqlSession().insert(NAMESPACE+"insertLoadWayBillDetailEntity", loadWaybillDetailEntity);
	}
	/** 
	 * 插入装车运单明细记录
	 * 
	 * 
	 * @param loadSerialNoEntity
	 * @author dp-duyi
	 * @date 2012-12-7 下午4:33:04
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#insertLoadSerialNoEntity(com.deppon.foss.module.transfer.load.api.shared.domain.LoadSerialNoEntity)
	 */
	@Override
	public int insertLoadSerialNoEntity(LoadSerialNoEntity loadSerialNoEntity) {
		return this.getSqlSession().insert(NAMESPACE+"insertLoadSerialNoEntity", loadSerialNoEntity);
	}
	/** 
	 * 根据流水号、装车运单明细id查询装车流水号id
	 * 
	 * 
	 * @param loadSerialNoEntity
	 * @author dp-duyi
	 * @date 2012-12-7 下午4:33:04
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#queryLoadSerialNoEntityBySerialNo(com.deppon.foss.module.transfer.load.api.shared.domain.LoadSerialNoEntity)
	 */
	@Override
	public LoadSerialNoEntity queryLoadSerialNoEntityBySerialNo(LoadSerialNoEntity loadSerialNoEntity) {
		return (LoadSerialNoEntity) this.getSqlSession().selectOne(NAMESPACE+"queryLoadSerialNoEntityBySerialNo", loadSerialNoEntity);
	}
	/** 
	 * 查询少货运单
	 * 
	 * 
	 * @param condition
	 * @author dp-duyi
	 * @date 2012-12-11 下午2:43:00
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#queryDeliverLoadLackWayBill(java.util.Map)
	 */
	@Override
	public List<DeliverLoadGapReportWayBillEntity> queryDeliverLoadLackWayBill(Map<String, String> condition) {
		return this.getSqlSession().selectList(NAMESPACE+"queryDeliverLoadLackWayBill", condition);
	}
	/** 
	 * 查询少货流水号
	 * 
	 * 
	 * @param condition
	 * @author dp-duyi
	 * @date 2012-12-11 下午2:43:00
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#queryDeliverLoadLackSerial(java.util.Map)
	 */
	@Override
	public List<LoadSerialNoEntity> queryDeliverLoadLackSerial(
			Map<String, Object> condition) {
		return this.getSqlSession().selectList(NAMESPACE+"queryDeliverLoadLackSerial", condition);
	}
	/** 
	 * 查询多货：未预配、多货
	 * 
	 * 
	 * @param condition
	 * @author dp-duyi
	 * @date 2012-12-12 下午3:11:11
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#queryMoreGoods(java.lang.String)
	 */
	@Override
	public List<LoadTaskSerialNoDto> queryGoodsByGoodsStates(Map<String,Object> condition) {
		return this.getSqlSession().selectList(NAMESPACE+"queryGoodsByGoodsStates", condition);
	}
	/** 
	 * 根据装车任务id查询装车到达部门
	 * 
	 * 
	 * @param loadTaskId
	 * @author dp-duyi
	 * @date 2012-12-12 下午3:11:11
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#queryLoadDestOrgCodesById(java.lang.String)
	 */
	@Override
	public List<String> queryLoadDestOrgCodesById(String loadTaskId) {
		return this.getSqlSession().selectList(NAMESPACE+"queryLoadDestOrgCodesById", loadTaskId);
	}
	/** 
	 * 根据运单号，流水号查询货件数
	 * 
	 * 
	 * @param condition
	 * @author dp-duyi
	 * @date 2012-12-12 下午6:37:59
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#queryLabeledGoodsBySerialNo(java.util.Map)
	 */
	@Override
	public int queryValidLabeledCount(Map<String, String> condition) {
		return (Integer) this.getSqlSession().selectOne(NAMESPACE+"queryValidLabeledCount", condition);
	}
	/** 
	 * 查询货物签收状态
	 * 
	 * 
	 * @param condition
	 * @author dp-duyi
	 * @date 2012-12-13 上午8:56:27
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#queryWayBillSignedState(java.lang.String)
	 */
	@Override
	public int queryWayBillSignedState(Map<String,String> condition) {
		return  (Integer)this.getSqlSession().selectOne(NAMESPACE+"queryWayBillSignedState",condition);
	}
	/** 
	 * 查询装车出库货物
	 * 
	 * 
	 * @param condition
	 * @author dp-duyi
	 * @date 2012-12-13 上午9:23:06
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#queryOutStockSerialNo(java.util.Map)
	 */
	@Override
	public List<LoadTaskSerialNoDto> queryOutStockGoods(Map<String, String> condition) {
		return this.getSqlSession().selectList(NAMESPACE+"queryOutStockGoods", condition);
	}
	/** 
	 * 查询需上报差错运单
	 * 
	 * 
	 * @param taskId
	 * @author dp-duyi
	 * @date 2012-12-13 下午6:36:22
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#queryLoadOaReportMoreGoods(java.lang.String)
	 */
	@Override
	public List<OaReportClearMore> queryLoadOaReportMoreGoods(String taskId,Date bizBeginDate) {
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("taskId", taskId);
		condition.put("bizBeginDate", bizBeginDate);
		return this.getSqlSession().selectList(NAMESPACE+"queryLoadOaReportMoreGoods", condition);
	}
	/** 
	 * 刷新正常货区装车清单
	 * 
	 * 
	 * @param taskNo
	 * @author dp-duyi
	 * @date 2012-12-14 上午10:44:29
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#refrushNormalTransferLoadTaskDetail(java.lang.String)
	 */
	@Override
	public List<LoadGoodsDetailSerialDto> refrushNormalTransferLoadTaskDetail(String taskNo,List<String> destOrgCodes
			,String goodsType,String isExpress) {
		Map<String,Object> condition = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(goodsType)){
			condition.put("goodsType", goodsType);
		}else{
			condition.put("goodsType", null);
		}
		if(StringUtils.isNotBlank(taskNo)){
			condition.put("taskNo", taskNo);
		}else{
			condition.put("taskNo", null);
		}
		if(CollectionUtils.isNotEmpty(destOrgCodes)){
			condition.put("destOrgCodes", destOrgCodes);
		}else{
			condition.put("destOrgCodes", null);
		}
		if(StringUtils.isNotBlank(isExpress)){
			condition.put("isExpress", isExpress);
		}else{
			condition.put("isExpress", null);
		}
		return this.getSqlSession().selectList(NAMESPACE+"refrushNormalTransferLoadTaskDetail", condition);
	}
	
	/** 
	 * 刷新虚拟库存装车清单
	 * 
	 * 
	 * @param taskNo
	 * @author dp-332219
	 * @date 2016-12-8
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#refrushNormalTransferLoadTaskDetail(java.lang.String)
	 */
	@Override
	public List<LoadGoodsDetailSerialDto> refrushSaleNormalTransferLoadTaskDetail(String taskNo,List<String> destOrgCodes
			,String goodsType,String isExpress) {
		Map<String,Object> condition = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(goodsType)){
			condition.put("goodsType", goodsType);
		}else{
			condition.put("goodsType", null);
		}
		if(StringUtils.isNotBlank(taskNo)){
			condition.put("taskNo", taskNo);
		}else{
			condition.put("taskNo", null);
		}
		if(CollectionUtils.isNotEmpty(destOrgCodes)){
			condition.put("destOrgCodes", destOrgCodes);
		}else{
			condition.put("destOrgCodes", null);
		}
		if(StringUtils.isNotBlank(isExpress)){
			condition.put("isExpress", isExpress);
		}else{
			condition.put("isExpress", null);
		}
		return this.getSqlSession().selectList(NAMESPACE+"refrushSaleNormalTransferLoadTaskDetail", condition);
	}
	
	/**
	 * 刷新 到达未卸车 装车清单
	 * map => key : origOrgCode,handoverbillStateList,arriveDeptList,start,limit
	 * @author yuyongxiang
	 * @date 2013年8月2日 10:51:54
	 */
	@Override
	public List<LoadGoodsDetailSerialDto> refrushNormalTransferLoadTaskDetailUnloadingNotReach(Map<String, Object> map) {
		String statement = NAMESPACE + "refrushNormalTransferLoadTaskDetailUnloadingNotReach";
		RowBounds rowBounds = new RowBounds(Integer.valueOf(map.get("start").toString()), Integer.valueOf(map.get("limit").toString()));
		return getSqlSession().selectList(statement, map,rowBounds);
	}
	
	/** 
	 * 刷新合车货区装车清单
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-12-14 上午10:44:29
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#refrushTogetherTransferLoadTaskDetail(java.lang.String)
	 */
	@Override
	public List<LoadGoodsDetailSerialDto> refrushTogetherTransferLoadTaskDetail(
			String taskNo,List<String> destOrgCodes,String goodsType,String isExpress) {
		Map<String,Object> condition = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(taskNo)){
			condition.put("taskNo", taskNo);
		}else{
			condition.put("taskNo", null);
		}
		if(StringUtils.isNotBlank(goodsType)){
			condition.put("goodsType", goodsType);
		}else{
			condition.put("goodsType", null);
		}
		if(CollectionUtils.isNotEmpty(destOrgCodes)){
			condition.put("destOrgCodes", destOrgCodes);
		}else{
			condition.put("destOrgCodes", null);
		}
		if(StringUtils.isNotBlank(isExpress)){
			condition.put("isExpress", isExpress);
		}else{
			condition.put("isExpress", null);
		}
		return this.getSqlSession().selectList(NAMESPACE+"refrushTogetherTransferLoadTaskDetail", condition);
	}
	/** 
	 * 查询合车到达部门
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-12-14 下午12:24:12
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#queryTogetherDestOrgCodes(java.util.Map)
	 */
	@Override
	public List<String> queryTogetherDestOrgCodes(Map<String, String> condition) {
		return this.getSqlSession().selectList(NAMESPACE+"queryTogetherDestOrgCodes", condition);
	}
	/** 
	 * PDA查询已分配装车任务:进行中、未开始的任务
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-11-6 下午12:36:39
	 * @see com.deppon.foss.module.transfer.pda.api.server.dao.IPDADeliverLoadDao#queryAssignedLoadTask(com.deppon.foss.module.transfer.pda.api.shared.domain.QueryAssignedLoadTaskEntity)
	 */
	@Override
	public List<PDAAssignLoadTaskEntity> queryAssignedLoadTask(
			QueryAssignedLoadTaskEntity condition) {
		return this.getSqlSession().selectList(NAMESPACE+"pda_queryAssignedLoadTask", condition);
	}
	/** 
	 * 刷新派送装车运单清单
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-12-15 下午3:29:56
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#refrushDeliverLoadWayBIllDetail(java.lang.String)
	 */
	@Override
	public List<LoadGoodsDetailSerialDto> refrushNormalDeliverLoadTaskDetail(
			String taskNo) {
		return this.getSqlSession().selectList(NAMESPACE+"refrushNormalDeliverLoadTaskDetail", taskNo);
	}
	/** 
	 * 查询理货员
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2013-1-12 下午3:38:12
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#queryLoaderParticipation(java.lang.String, java.lang.String)
	 */
	@Override
	public List<LoaderParticipationEntity> queryLoaderParticipation(
			String taskId, String loaderCode,String flag) {
		Map<String,String> condition = new HashMap<String,String>();
		//任务id
		condition.put("taskId", taskId);
		//装车员工号
		condition.put("loaderCode", loaderCode);
		//标志
		condition.put("flag", flag);
		return this.getSqlSession().selectList(NAMESPACE+"queryLoaderParticipation", condition);
	}
	/** 
	 * 更新装车任务运单明细+增加
	 * 
	 * 
	 * @author dp-duyi
	 * 
	 * @date 2013-1-31 下午8:46:47
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#updateLoadTaskWayBillDetailAdd(java.lang.String)
	 */
	@Override
	public int updateLoadTaskWayBillDetailAdd(LoadWaybillDetailEntity loadWaybillDetailEntity) {
		return this.getSqlSession().update(NAMESPACE+"updateLoadTaskWayBillDetailAdd", loadWaybillDetailEntity);
	}

	/** 
	 * 开单非库存流水号
	 * @author dp-duyi
	 * @date 2013-3-4 下午7:30:49
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#queryLabeledGoodsNotInStock(java.lang.String, java.lang.String)
	 */
	@Override
	public List<PDAGoodsSerialNoDto> queryLabeledGoodsNotInStock(String wayBillNo,
			String orgCode) {
		Map<String,String> condition = new HashMap<String,String>();
		condition.put("wayBillNo", wayBillNo);
		condition.put("orgCode", orgCode);
		return this.getSqlSession().selectList(NAMESPACE+"queryLabeledGoodsNotInStock", condition);
	}

	/** 
	 * 查询库存流水号
	 * @author dp-duyi
	 * @date 2013-3-4 下午7:42:56
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#queryStockSerialNos(java.lang.String, java.lang.String)
	 */
	@Override
	public List<PDAGoodsSerialNoDto> queryStockSerialNos(String wayBillNo,
			String orgCode,String goodsAreaCode,int needLoadQty) {
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("wayBillNo", wayBillNo);
		condition.put("orgCode", orgCode);
		condition.put("goodsAreaCode", goodsAreaCode);
		condition.put("needLoadQty", needLoadQty);
		return this.getSqlSession().selectList(NAMESPACE+"queryStockSerialNos", condition);
	}

	/** 
	 * 根据运单号查询库存中为包装流水号
	 * @author dp-duyi
	 * @date 2013-3-5 上午8:45:13
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#queryUnpackStockSerialNos(java.lang.String, java.lang.String)
	 */
	@Override
	public List<PDAGoodsSerialNoDto> queryUnpackStockSerialNos(
			String wayBillNo, String orgCode) {
		Map<String,String> condition = new HashMap<String,String>();
		condition.put("wayBillNo", wayBillNo);
		condition.put("orgCode", orgCode);
		return this.getSqlSession().selectList(NAMESPACE+"queryUnpackStockSerialNos", condition);
	}

	/** 
	 * 查询是否未包装
	 * @author dp-duyi
	 * @date 2013-3-6 上午8:57:25
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#queryBeNeedPack(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean queryBeUnpack(String wayBillNo, String serialNo,
			String orgCode) {
		Map<String,String> condition = new HashMap<String,String>();
		condition.put("wayBillNo", wayBillNo);
		condition.put("orgCode", orgCode);
		condition.put("serialNo", serialNo);
		List<String> result = this.getSqlSession().selectList(NAMESPACE+"queryBeNeedPack", condition);
		if(CollectionUtils.isNotEmpty(result)){
			return true;
		}else{
			return false;
		}
	}

	/** 
	 * 查询预配件数是否大于装车件数
	 * @author dp-duyi
	 * @date 2013-3-6 下午4:35:52
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#stockQtyIsBiggerThanLoadQty(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public int stockQtyIsBiggerThanLoadQty(String taskId,
			String deliverBillNo, String wayBillNo) {
		Map<String,String> condition = new HashMap<String,String>();
		condition.put("taskId", taskId);
		condition.put("deliverBillNo", deliverBillNo);
		condition.put("wayBillNo", wayBillNo);
		List<Integer> result = this.getSqlSession().selectList(NAMESPACE+"stockQtyIsBiggerThanLoadQty", condition);
		if(CollectionUtils.isEmpty(result)){
			return 0;
		}else{
			if(result.get(0) <= 0){
				return 0;
			}else{
				return result.get(0);
			}
		}
	}
	/** 
	 * 刷新多货装车清单
	 * @author dp-duyi
	 * @date 2013-4-10 下午6:41:04
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#refrushMoreGoodsLoadTaskDetail(java.lang.String)
	 */
	@Override
	public List<LoadGoodsDetailSerialDto> refrushMoreGoodsLoadTaskDetail(
			String taskNo,String isExpress) {
		Map<String,Object> condition = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(taskNo)){
			condition.put("taskNo", taskNo);
		}else{
			condition.put("taskNo", null);
		}
		if(StringUtils.isNotBlank(isExpress)){
			condition.put("isExpress", isExpress);
		}else{
			condition.put("isExpress", null);
		}
		return this.getSqlSession().selectList(NAMESPACE+"refrushMoreGoodsLoadTaskDetail", condition);
	}
	/** 
	 * 查询未完成中转装车任务
	 * @author dp-duyi
	 * @date 2013-4-11 上午9:31:22
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#queryUnfinishedLoadTask(com.deppon.foss.module.transfer.pda.api.shared.domain.QueryAssignedLoadTaskEntity)
	 */
	@Override
	public List<PDAAssignLoadTaskEntity> queryUnfinishedLoadTask(
			QueryAssignedLoadTaskEntity condition) {
		return this.getSqlSession().selectList(NAMESPACE+"pda_queryUnfinishedLoadTask", condition);
	}
	/** 
	 * 查询已扫描未装车流水号
	 * @author dp-duyi
	 * @date 2013-4-12 上午10:25:42
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#queryScanedUnloadSerialNos(java.lang.String, java.util.List)
	 */
	@Override
	public List<LoadSerialNoEntity> queryScanedUnloadSerialNos(
			String loadWayBillDetailId) {
		Map<String,Object> condition = new HashMap<String,Object>();
		//任务id
		condition.put("loadWayBillDetailId", loadWayBillDetailId);
		//货物状态
		//condition.put("goodsStates", goodsStates);
		//返回条件
		return this.getSqlSession().selectList(NAMESPACE+"queryScanedUnloadSerialNos", condition);
	}
	/** 
	 * 查询库存少货流水号
	 * @author dp-duyi
	 * @date 2013-4-12 上午10:25:42
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#queryStockUnloadSerialNos(java.lang.String, java.lang.String, int)
	 */
	@Override
	public List<LoadSerialNoEntity> queryStockUnloadSerialNos(
			String wayBillNo, String origOrgCode, String loadWayBillDetailId, int rownum) {
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("wayBillNo", wayBillNo);
		condition.put("origOrgCode", origOrgCode);
		condition.put("loadWayBillDetailId", loadWayBillDetailId);
		condition.put("rowNum", rownum);
		return this.getSqlSession().selectList(NAMESPACE+"queryStockUnloadSerialNos", condition);
	}
	public List<Integer> queryDeliverStockQty(String taskNo,String wayBillNo){
		Map<String,String> condition = new HashMap<String,String>();
		condition.put("taskNo", taskNo);
		condition.put("wayBillNo", wayBillNo);
		return this.getSqlSession().selectList(NAMESPACE+"queryDeliverStockQty", condition);
	}
	/** 
	 * 
	 * @author dp-duyi
	 * @date 2013-4-12 下午1:48:04
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#queryDeliverBillCreatOrgCodeByDeliverBillNo(java.lang.String)
	 */
	@Override
	public List<String> queryDeliverBillOrgCodeByDeliverBillNo(
			String deliverBillNo) {
		return this.getSqlSession().selectList(NAMESPACE+"queryDeliverBillOrgCodeByDeliverBillNo", deliverBillNo);
	}
	/** 根据任务ID查询到达部门
	 * @author dp-duyi
	 * @date 2013-4-19 上午10:29:52
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#queryLoadDestOrgsByCode(java.lang.String)
	 */
	@Override
	public List<LoadDestOrgEntity> queryLoadDestOrgs(String loadTaskId) {
		return this.getSqlSession().selectList(NAMESPACE+"queryLoadDestOrgs", loadTaskId);
	}
	/** 
	 * 根据部门查询运单库存件数
	 * @author dp-duyi
	 * @date 2013-6-19 下午2:15:57
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#queryWayBillStockQty(java.lang.String, java.lang.String)
	 */
	@Override
	public int queryWayBillStockQty(String wayBillNo, String orgCode) {
		Map<String,String> condition = new HashMap<String,String>();
		condition.put("wayBillNo", wayBillNo);
		condition.put("orgCode", orgCode);
		return (Integer) this.getSqlSession().selectOne(NAMESPACE+"queryWayBillStockQty", condition);
	}
	/** 
	 * 查询派送排单件数
	 * @author dp-duyi
	 * @date 2013-6-19 下午3:17:13
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#queryDeliverArrangeQty(java.lang.String, java.lang.String)
	 */
	@Override
	public int queryDeliverArrangeQty(String deliverBillNo, String wayBillNo) {
		Map<String,String> condition = new HashMap<String,String>();
		condition.put("wayBillNo", wayBillNo);
		condition.put("deliverBillNo", deliverBillNo);
		return (Integer) this.getSqlSession().selectOne(NAMESPACE+"queryDeliverArrangeQty", condition);
	}
	/** 
	 * 根据装车任务号查询交接单
	 * @author dp-duyi
	 * 于2014-11-22 15:27 alfred 修改，修复散货快递交接单没有生成车辆任务
	 * @date 2013-6-21 下午9:18:16
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#queryHandOverBillNoByTaskNo(java.lang.String)
	 */
	@Override
	public List<String> queryHandOverBillNoByTaskNo(String loadTaskNo) {
		return	this.getSqlSession().selectList(NAMESPACE+"queryHandOverBillNoByTaskNo", loadTaskNo);
	}
	/** 
	 * select for update 装车任务，避免重复提交
	 * @author dp-duyi
	 * @date 2013-6-22 上午11:20:09
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#queryLoadTaskByTaskNoForUpdate(java.lang.String)
	 */
	@Override
	public LoadTaskEntity queryLoadTaskByTaskNoForUpdate(String taskNo) {
		return (LoadTaskEntity) this.getSqlSession().selectOne(NAMESPACE+"queryLoadTaskByTaskNoForUpdate",taskNo);
	}
	/** 
	 * 查询已装车货物
	 * @author dp-duyi
	 * @date 2013-7-11 下午1:03:19
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#queryLoadedGoods(java.util.Map)
	 */
	@Override
	public List<LoadTaskSerialNoDto> queryLoadedGoods(
			Map<String, String> condition) {
		return this.getSqlSession().selectList(NAMESPACE+"queryLoadedGoods",condition);
	}
	/**
	 * 
	 * 更新待补录的装卸任务明细重量体积
	 * @author alfred
	 * @date 2014-2-19 下午2:34:21
	 * @param condition
	 * @return 
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#updateMakeUpPDAloadAndUnload(java.util.Map)
	 */
	@Override
	public int updateMakeUpPDAloadAndUnload(Map<String, Object> condition) {
		return this.getSqlSession().update(NAMESPACE+"updateMakeUpPDAloadAndUnload", condition);
	}
	/**
	 * 
	 * 查询理货员
	 * @author alfred
	 * @date 2014-3-13 下午5:33:31
	 * @param taskId
	 * @return 
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#queryLoaderByTaskID(java.lang.String)
	 */
	@Override
	public LoaderParticipationEntity queryLoaderByTaskID(String taskId) {
		return (LoaderParticipationEntity)this.getSqlSession().selectOne(NAMESPACE+"queryLoaderByTaskID", taskId);
	
	}
	/**
	 * 
	 * <p>刷新普通包装车清单</p> 
	 * @author alfred
	 * @date 2014-10-26 上午10:33:03
	 * @param taskNo
	 * @param destOrgCodes
	 * @param goodsType
	 * @return 
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#refrushPackageTransferLoadTaskDetail(java.lang.String, java.util.List, java.lang.String)
	 */
	@Override
	public List<LoadGoodsDetailSerialDto> refrushPackageTransferLoadTaskDetail(
			String taskNo, List<String> destOrgCodes) {
		Map<String,Object> condition = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(taskNo)){
			condition.put("taskNo", taskNo);
		}else{
			condition.put("taskNo", null);
		}
		if(CollectionUtils.isNotEmpty(destOrgCodes)){
			condition.put("destOrgCodes", destOrgCodes);
		}else{
			condition.put("destOrgCodes", null);
		}
		return this.getSqlSession().selectList(NAMESPACE+"refrushPackageTransferLoadTaskDetail", condition);
	}
	/**
	 * 
	 * <p>根据扫描时间更新包装车运单明细</p> 
	 * @author alfred
	 * @date 2014-10-26 下午3:31:25
	 * @param loadWayBill
	 * @param scanTime
	 * @return 
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#updateLoadWayBillQTYByScanTime(com.deppon.foss.module.transfer.load.api.shared.domain.LoadWaybillDetailEntity, java.util.Date)
	 */
	@Override
	public int updateLoadWayBillQTYByScanTime(
			LoadWaybillDetailEntity loadWayBill, Date scanTime) {
		if(loadWayBill == null){
			return 0;
		}
		//配合BI项目，增加修改时间字段
		loadWayBill.setModifyDate(scanTime);
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("loadWayBill", loadWayBill);
		condition.put("scanTime", scanTime);
		return this.getSqlSession().update(NAMESPACE+"updatePackageWayBillQTYByScanTime", condition);
	
	}
	/**
	 * 
	 * <p>根据任务id查询包号</p> 
	 * @author alfred
	 * @date 2014-10-26 下午4:56:06
	 * @param loadTaskId
	 * @return 
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#queryLoadPackageNoById(java.lang.String)
	 */
	@Override
	public List<String> queryLoadPackageNoById(String loadTaskId) {
		return this.getSqlSession().selectList(NAMESPACE+"queryLoadPackageNoById", loadTaskId);
	}
	
	/**
	 * 
	 * <p>刷新直达包装车清单</p> 
	 * @author alfred
	 * @date 2014-10-27 下午8:17:05
	 * @param taskNo
	 * @param destOrgCodes
	 * @return 
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#refrushDirectPackageTransferLoadDetail(java.lang.String, java.util.List)
	 */
	@Override
	public List<LoadGoodsDetailSerialDto> refrushDirectPackageTransferLoadDetail(
			String taskNo, List<String> destOrgCodes) {
		Map<String,Object> condition = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(taskNo)){
			condition.put("taskNo", taskNo);
		}else{
			condition.put("taskNo", null);
		}
		if(CollectionUtils.isNotEmpty(destOrgCodes)){
			condition.put("destOrgCodes", destOrgCodes);
		}else{
			condition.put("destOrgCodes", null);
		}
		return this.getSqlSession().selectList(NAMESPACE+"refrushDirectPackageTransferLoadDetail", condition);
	}
	
	/**
	 * 
	 * <p>生成包交接单</p> 
	 * @author alfred
	 * @date 2014-11-1 上午9:16:04
	 * @param taskNo
	 * @param packagaNo
	 * @param length
	 * @param isDirectPackage
	 * @return 
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#autoCreatePackHandoverbill(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Map autoCreatePackHandoverbill(String taskNo, String packageNo,
			int length, String isDirectPackage) {
		Map paramsMap = new HashMap();
		paramsMap.put("taskNo", taskNo);
		paramsMap.put("packageNo", packageNo);
		paramsMap.put("length", length);
		paramsMap.put("isDirectPackage",isDirectPackage);
		this.getSqlSession().selectOne(NAMESPACE + "createPackageHandoverBill", paramsMap);
		return paramsMap;
	}
	
	/**
	 * 
	 * <p>刷新多货装车包清单</p> 
	 * @author alfred
	 * @date 2014-11-15 下午1:41:45
	 * @param taskNo
	 * @return 
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#refrushMorePackageLoadDetail(java.lang.String)
	 */
	@Override
	public List<LoadGoodsDetailSerialDto> refrushMorePackageLoadDetail(
			String taskNo) {
		return this.getSqlSession().selectList(NAMESPACE+"refrushMorePackageLoadDetail", taskNo);
	}
	

	/** 
	 * 插入派送装车运单退回
	 * @param
	 * @author dp-zyr
	 * @date 2015-05-6 上午10:51:54
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#insertLoadWaybillReturn(com.deppon.foss.module.transfer.pda.api.shared.dto.PDAWaybillReturnDto)
	 */
	@Override
	public int insertLoadWaybillReturn(PDAWaybillReturnDto pdaWaybillReturnDto) {
		return this.getSqlSession().insert(NAMESPACE+"insertLoadWaybillReturn", pdaWaybillReturnDto);
	}
	
	/** 
	 * 批量更新装车流水号信息
	 * @param loadSerialNoEntity
	 * @author zwd 200968
	 * @date 2015-5-22 下午3:18:15
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#updateLoadTaskSerialNo(com.deppon.foss.module.transfer.load.api.shared.domain.LoadSerialNoEntity)
	 */
	@Override
	public int updateLoadTaskSerialNoNew(LoadSerialNoEntity loadSerialNoEntity) {
		return this.getSqlSession().update(NAMESPACE+"updateLoadTaskSerialNoNew",loadSerialNoEntity);
	}
	
	/**
	 * 
	 * <p>司机装车多个交接单合成一个交接单</p> 
	 * @author alfred
	 * @date 2015-4-20 下午3:34:06
	 * @param condition 
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#addPickHandoverBill(java.util.Map)
	 */
	@Override
	public void addPickHandoverBill(Map<String, Object> condition) {
		this.getSqlSession().insert(NAMESPACE + "addPickHandoverBill",condition);
	}
	
	/**
	 * 
	 * <p>查询交接单是否存在</p> 
	 * @author alfred
	 * @date 2015-5-29 下午5:17:16
	 * @param taskNos
	 * @return 
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#beExistPickHandover(java.util.List)
	 */
	@Override
	public int beExistPickHandover(List<String> taskNos) {
		return (Integer) this.getSqlSession().selectOne(NAMESPACE+"beExistPickHandover", taskNos);
	}
	
	/**
	 * 
	 * <p>根据接驳点编码查询接驳点名称</p> 
	 * @author alfred
	 * @date 2015-9-7 下午7:43:52
	 * @param pointCode
	 * @return 
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#queryAccessPointName(java.lang.String)
	 */
	@Override
	public String queryAccessPointName(String pointCode) {
		return (String) this.getSqlSession().selectOne(NAMESPACE+"queryAccessPointName",pointCode);
	}

	
	/**
	 * FOSS根据PDA传过来的运单号和当前所在转运场，查找出该运单走货路径中的下一转运场，并把结果返回给PDA。
	 * @author zwd 200968 2015-07-21
	 * @param waybillNo
	 * @param orgCode
	 * @return 
	 */
	public PackagePathLoaderDto  unlockPackagePathDetail(String waybillNo, String orgCode) {
		Map<String,String> condition = new HashMap<String,String>();
		condition.put("waybillNo", waybillNo);
		condition.put("orgCode", orgCode);
		List<PackagePathLoaderDto> packagePathLoaderlist = new ArrayList<PackagePathLoaderDto>();
		packagePathLoaderlist =  this.getSqlSession().selectList(NAMESPACE+"unlockPackagePathDetail", condition);
		
		if(CollectionUtils.isNotEmpty(packagePathLoaderlist) && packagePathLoaderlist.size() >= 1){
			Set<PackagePathLoaderDto> packagePathLoaderSet = new HashSet<PackagePathLoaderDto>();//去除重复结果集
			for (PackagePathLoaderDto packagePathLoaderDto : packagePathLoaderlist) {
				packagePathLoaderSet.add(packagePathLoaderDto);
			}
			// 去重后的结果集不为空并且下一部门只有一个,则返回
			if(CollectionUtils.isNotEmpty(packagePathLoaderSet) && packagePathLoaderSet.size() == 1 ){
				return packagePathLoaderlist.get(0);
			}
		}
		
		return null;
	}
	/**
	 * 判断是否是装车任务
	 */
	@Override
	public int judgeIfIsDeliverLoad(String waybillNo) {
		
		return (Integer) this.getSqlSession().selectOne(NAMESPACE+"judgeIfIsDeliverLoad", waybillNo);
	}
	/**
	 * 判断运单是不是在库存中
	 */
	@Override
	public List<String> queryWaybillInStock(String waybillNo) {
		
		return this.getSqlSession().selectList(NAMESPACE+"queryWaybillInStock", waybillNo);
	}
	/**
	 * 查询运单在库存中的数量
	 */
	@Override
	public Integer waybillNoQtyInStock(String waybillNo) {
		
		return (Integer) this.getSqlSession().selectOne(NAMESPACE+"waybillNoQtyInStock", waybillNo);
	}
	/**
	 * 查询已装车的运单数量
	 */
	@Override
	public Integer loadWaybillQty(String waybillNo) {
		
		return (Integer) this.getSqlSession().selectOne(NAMESPACE+"loadWaybillQty", waybillNo);
	}
	/**
	 * 查询某运单的最后入库时间
	 * 
	 */
	@Override
	public Date lastInstockTime(String waybillNo) {
		
		return (Date) this.getSqlSession().selectOne(NAMESPACE+"queryLastInstockTime", waybillNo);
	}
	/**
	 * 查询某运单的装车时间
	 * 
	 */
	@Override
	public Date loadTime(String waybillNo) {
		
		return (Date) this.getSqlSession().selectOne(NAMESPACE+"queryLoadTime", waybillNo);
	}
	//通过任务号查询出运单  与 子母件单号比对  hongwy-218427
	public LoadTaskEntity queryWayBillNos(String taskNo){
		return (LoadTaskEntity) this.getSqlSession().selectOne(NAMESPACE+"queryWayBillNos",taskNo);
	}
	
	//通过生成的单号与数据库对比 是否重复  hongwy-218427
	@Override
	public String queryTaskNos(String taskNo) {
		
		return (String) this.getSqlSession().selectOne(NAMESPACE+"queryTaskNos",taskNo);
	}
	
	
	/**
	* @description 根据装车任务编号查询所有运单号信息
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#queryWayBillNoList(java.lang.String)
	* @author 218381-foss-lijie
	* @update 2016年1月14日 上午9:59:30
	* @version V1.0
	*/
	@Override
	public List<String> queryWayBillNoList(String taskNo) {
		return this.getSqlSession().selectList(NAMESPACE+"queryWayBillNoList",taskNo);
	}
	
	@Override
	public int addLTLHandoverBill(CreateDeliveryReceiptEntity deliveryReceiptEntity) {
		
		/**查询营业部所属的始发外场*/
		String oriOrgCode = deliveryReceiptEntity.getConsigneeDeptCode();
		String desOrgCode = (String)this.getSqlSession().selectOne(NAMESPACE + "selectBaseLineDestOrgCode",oriOrgCode);
		logger.info("查询营业部所属的始发外场:oriOrgCode="+oriOrgCode+";desOrgCode="+desOrgCode);
		deliveryReceiptEntity.setConsigneeDeptCode(desOrgCode);
		
		return this.getSqlSession().insert(NAMESPACE + "addLTLHandoverBill",deliveryReceiptEntity);
	}
	

	/**根据运单号查询零担电子运单交接单信息*/
	@Override
	public  List<CreateDeliveryReceiptEntity> queryLTLPackHandoverbill(String waybillNo){
		return (List<CreateDeliveryReceiptEntity>)this.getSqlSession().selectList(NAMESPACE + "queryLTLPackHandoverbill",waybillNo);
	}

	@Override
	public int addLTLHandoverBillDetail(WaybillInfoEntity waybill) {
		// TODO Auto-generated method stub
		return this.getSqlSession().insert(NAMESPACE + "addLTLHandoverBillDetail",waybill);
	}
	@Override
	public List<WaybillInfoEntity> queryLTLPackHandoverbillDetail(
			String waybillNo) {
		
		return (List<WaybillInfoEntity>) this.getSqlSession().selectList(NAMESPACE + "queryLTLPackHandoverbillDetail",waybillNo);
	}

	@Override
	public int addLTLHandoverSerialNo(WaybillInfoEntity waybill) {
		return this.getSqlSession().insert(NAMESPACE + "addLTLHandoverSerialNo",waybill);
	}
	
	/**
	 * 刷新正常库区零担装车清单(剔除优先货、代办的清单)
	 */
	@Override
	public List<LoadGoodsDetailSerialDto> refreshSimpleLDDetail(String taskNo,
			List<String> destOrgCodes, String goodsType) {
		Map<String,Object> condition = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(goodsType)){
			condition.put("goodsType", goodsType);
		}else{
			condition.put("goodsType", null);
		}
		if(StringUtils.isNotBlank(taskNo)){
			condition.put("taskNo", taskNo);
		}else{
			condition.put("taskNo", null);
		}
		if(CollectionUtils.isNotEmpty(destOrgCodes)){
			condition.put("destOrgCodes", destOrgCodes);
		}else{
			condition.put("destOrgCodes", null);
		}
		return this.getSqlSession().selectList(NAMESPACE+"refreshSimpleLDDetail", condition);
	}
	
	/**
	 * 刷新虚拟库存零担装车清单(剔除优先货、代办的清单)
	 */
	@Override
	public List<LoadGoodsDetailSerialDto> refreshSaleSimpleLDDetail(String taskNo,
			List<String> destOrgCodes, String goodsType) {
		Map<String,Object> condition = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(goodsType)){
			condition.put("goodsType", goodsType);
		}else{
			condition.put("goodsType", null);
		}
		if(StringUtils.isNotBlank(taskNo)){
			condition.put("taskNo", taskNo);
		}else{
			condition.put("taskNo", null);
		}
		if(CollectionUtils.isNotEmpty(destOrgCodes)){
			condition.put("destOrgCodes", destOrgCodes);
		}else{
			condition.put("destOrgCodes", null);
		}
		return this.getSqlSession().selectList(NAMESPACE+"refreshSaleSimpleLDDetail", condition);
	}
	
	/**
	 * 刷新正常库区快递散货装车清单(剔除优先货、代办的清单)
	 */
	@Override
	public List<LoadGoodsDetailSerialDto> refreshSimpleEXPDetail(String taskNo,
			List<String> destOrgCodes, String goodsType) {
		Map<String,Object> condition = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(goodsType)){
			condition.put("goodsType", goodsType);
		}else{
			condition.put("goodsType", null);
		}
		if(StringUtils.isNotBlank(taskNo)){
			condition.put("taskNo", taskNo);
		}else{
			condition.put("taskNo", null);
		}
		if(CollectionUtils.isNotEmpty(destOrgCodes)){
			condition.put("destOrgCodes", destOrgCodes);
		}else{
			condition.put("destOrgCodes", null);
		}
		return this.getSqlSession().selectList(NAMESPACE+"refreshSimpleEXPDetail", condition);
	}
	
	/**
	 * 刷新合车库区装车清单(剔除优先货、代办的清单)
	 */
	@Override
	public List<LoadGoodsDetailSerialDto> refreshTogetherSimpleDetail(
			String taskNo, List<String> destOrgCodes, String goodsType) {
		Map<String,Object> condition = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(taskNo)){
			condition.put("taskNo", taskNo);
		}else{
			condition.put("taskNo", null);
		}
		if(StringUtils.isNotBlank(goodsType)){
			condition.put("goodsType", goodsType);
		}else{
			condition.put("goodsType", null);
		}
		if(CollectionUtils.isNotEmpty(destOrgCodes)){
			condition.put("destOrgCodes", destOrgCodes);
		}else{
			condition.put("destOrgCodes", null);
		}
		return this.getSqlSession().selectList(NAMESPACE+"refreshTogetherSimpleDetail", condition);
	}
	
	/** 
	 * 查询装车出库货物
	 * 
	 * 
	 * @param condition
	 * @author dp-332219
	 * @date 2016-12-26
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao#querySaleOutStockSerialNo(java.util.Map)
	 */
	@Override
	public List<LoadTaskSerialNoDto> querySaleOutStockGoods(Map<String, String> condition) {
		return this.getSqlSession().selectList(NAMESPACE+"querySaleOutStockGoods", condition);
	}
}
