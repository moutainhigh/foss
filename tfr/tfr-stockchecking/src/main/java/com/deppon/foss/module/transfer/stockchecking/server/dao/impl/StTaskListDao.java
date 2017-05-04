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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/server/dao/impl/StTaskListDao.java
 *  
 *  FILE NAME          :StTaskListDao.java
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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.exceptiongoods.api.define.ExceptionGoodsConstants;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PdaPackageStTastDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PdaStTaskDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskListDao;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StTaskEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StTaskListEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StTaskWaybillNoListDto;
import com.deppon.foss.util.UUIDUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 清仓任务快照相关业务处理
 * @author foss-wuyingjie
 * @date 2012-12-25 上午10:46:22
 */
public class StTaskListDao extends iBatis3DaoImpl implements IStTaskListDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(StTaskListDao.class);
	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:46:28
	 * @function 新增任务明细列表
	 * @param taskListEntity
	 * @return
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskListDao#addStTaskListEntity(com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StTaskListEntity)
	 */
	@Override
	public void addStTaskListEntity(StTaskListEntity taskListEntity) {
		taskListEntity.setId(UUIDUtils.getUUID());
		
		this.getSqlSession().insert("foss.tfr.StTaskListDao.addStTaskListEntity", taskListEntity);
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:46:32
	 * @funciton 批量新增任务明细列表
	 * @param stTaskListEntityList
	 * @return 
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskListDao#addStTaskListEntityBatch(java.util.List)
	 */
	@Override
	public void addStTaskListEntityBatch(List<StTaskListEntity> stTaskListEntityList) {
		Connection connection = this.getSqlSession().getConnection();
		StringBuffer sql =  new StringBuffer();
		sql.append("insert into TFR.T_OPT_ST_TASK_LIST (ID, WAYBILL_NO, SERIAL_NO, ST_TASK_ID) ")
           .append("values (?, ?, ?, ?)");

		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(sql.toString());

			for (StTaskListEntity entity : stTaskListEntityList) {
				int i = 0;
				
				ps.setString(++i, entity.getId());
				ps.setString(++i, entity.getWaybillNo());
				ps.setString(++i, entity.getSerialNo());
				ps.setString(++i, entity.getStTaskId());
				
				ps.addBatch();
			}

			ps.executeBatch();

		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					//sonar-352203
					LOGGER.info("StTaskListDao.addStTaskListEntityBatch 报错:" + StringUtils.substring(e.toString(), 0, ConstantsNumberSonar.SONAR_NUMBER_100));
				}
			}
		}
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:46:40
	 * @function 删除任务
	 * @param stTaskId
	 * @return
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskListDao#deleteByStTaskId(java.lang.String)
	 */
	@Override
	public void deleteByStTaskId(String stTaskId) {
		this.getSqlSession().delete("foss.tfr.StTaskListDao.deleteByStTaskId", stTaskId);
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:46:46
	 * @function 查询任务中的运单
	 * @param stTaskId
	 * @return List<StTaskWaybillNoListDto>
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskListDao#queryStTaskWaybillNoListByStTaskId(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StTaskWaybillNoListDto> queryStTaskWaybillNoListByStTaskId(String stTaskId) {
		return this.getSqlSession().selectList("foss.tfr.StTaskListDao.queryStTaskWaybillNoListByStTaskId", stTaskId);
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:46:50
	 * @function 查询任务明细列表
	 * @param stTaskId
	 * @return List<StTaskListEntity>
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskListDao#queryStTaskListByStTaskId(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StTaskListEntity> queryStTaskListByStTaskId(String stTaskId) {

		return this.getSqlSession().selectList("foss.tfr.StTaskListDao.queryStTaskListByStTaskId", stTaskId);
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:46:56
	 * @function 查询PDA少货明细列表
	 * @param stTaskId
	 * @return List<StTaskListEntity>
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskListDao#queryPdaLackList(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StTaskListEntity> queryPdaLackList(String stTaskId) {

		List<StTaskListEntity> list = this.getSqlSession().selectList("foss.tfr.StTaskListDao.queryPdaLackList", stTaskId);
		
		if(CollectionUtils.isEmpty(list)){
			return new ArrayList<StTaskListEntity>();
		}else{
			return list;
		}
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:47:00
	 * @function 查询pda任务列表
	 * @param stTaskNo
	 * @return List<PdaStTaskDto>
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskListDao#queryPdaStTaskDtoList(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PdaStTaskDto> queryPdaStTaskDtoList(String stTaskNo) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("stTaskNo", stTaskNo);
		params.put("contraband", ExceptionGoodsConstants.CONTRABAND_PROCESS_RESULT);
		
		return this.getSqlSession().selectList("foss.tfr.StTaskListDao.queryPdaStTaskDtoList", params);
	}
	
	/** 
	 * @author foss-zhuyunrong
	 * @date 2015-03-27 上午10:35:00
	 * @function 查询pda任务包清仓列表
	 * @param stTaskNo
	 * @return List<PdaPackageStTastDto>
	 * @see 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PdaPackageStTastDto> queryPdaPackageStTastDtoList(String stTaskNo) {
		return this.getSqlSession().selectList("foss.tfr.StTaskListDao.queryPdaPackageStTastDtoList", stTaskNo);
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:47:00
	 * @function 查询任务中运单的总件数
	 * @param stTaskId
	 * @param waybillNo
	 * @param serialNo
	 * @return long
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskListDao#queryPdaStTaskDtoList(java.lang.String)
	 */
	@Override
	public Long queryEntityCountByGoodsInfo(String stTaskId, String waybillNo, String serialNo) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("stTaskId", stTaskId);
		params.put("waybillNo", waybillNo);
		params.put("serialNo", serialNo);
		
		return (Long) this.getSqlSession().selectOne("foss.tfr.StTaskListDao.queryEntityCountByGoodsInfo", params);
	}
	/**
	 * 新增一个清仓任务列表快照
	 * @param stTaskId
	 * @param orgCode
	 * @param goodsAreaCode
	 * @author 097457-foss-wangqiang
	 * @date 2013-4-28 下午3:04:10
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskListDao#addStTaskListFromStock(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void addStTaskListFromStock(StTaskEntity task, int beforeTime) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("stTaskId", task.getId());
		paramsMap.put("orgCode", task.getDeptcode());
		paramsMap.put("goodsAreaCode", task.getGoodsareacode());
		paramsMap.put("receiveMethodList", task.getReceiveMethodList());
		paramsMap.put("districtCodeList", task.getDistrictCodeList());
		paramsMap.put("startQty", task.getStartQty());
		paramsMap.put("endQty", task.getEndQty());
		
		paramsMap.put("beforeTime", beforeTime);
		this.getSqlSession().insert("foss.tfr.StTaskListDao.addStTaskListFromStock", paramsMap);
	}
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
	 * @return
	 */
	@Override
	public void addStTaskListFromStockByQty(String stTaskId, String orgCode, String goodsAreaCode,Integer startQty, Integer endQty,int beforeTime) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("stTaskId", stTaskId);
		paramsMap.put("orgCode", orgCode);
		paramsMap.put("goodsAreaCode", goodsAreaCode);
		paramsMap.put("startQty", startQty);
		paramsMap.put("endQty", endQty);
		paramsMap.put("beforeTime", beforeTime);
		this.getSqlSession().insert("foss.tfr.StTaskListDao.addStTaskListFromStockByQty", paramsMap);
	}

	/**查询清仓快照列表(快递)
	 * @author 268084
	 * @param stTaskNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PdaStTaskDto> queryPdaStTaskDtoListExpress(String stTaskNo) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("stTaskNo", stTaskNo);
		params.put("contraband", ExceptionGoodsConstants.CONTRABAND_PROCESS_RESULT);
		
		return this.getSqlSession().selectList("foss.tfr.StTaskListDao.queryPdaStTaskDtoListExpress", params);
	}

	/**查询清仓快照列表(零担)
	 * @author 268084
	 * @param stTaskNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PdaStTaskDto> queryPdaStTaskDtoListNotExpress(String stTaskNo) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("stTaskNo", stTaskNo);
		params.put("contraband", ExceptionGoodsConstants.CONTRABAND_PROCESS_RESULT);
		
		return this.getSqlSession().selectList("foss.tfr.StTaskListDao.queryPdaStTaskDtoListNotExpress", params);
	}

	/**
	 * 新增一个清仓任务列表快照(快递员清仓,只清快递的)
	 * @param stTaskId
	 * @param orgCode
	 * @param goodsAreaCode
	 * @author 268084
	 * @date 
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskListDao#addStTaskListFromStock(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void addStTaskListFromStockForExpress(StTaskEntity task,
			int beforeTime) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("stTaskId", task.getId());
		paramsMap.put("orgCode", task.getDeptcode());
		paramsMap.put("goodsAreaCode", task.getGoodsareacode());
		paramsMap.put("receiveMethodList", task.getReceiveMethodList());
		paramsMap.put("districtCodeList", task.getDistrictCodeList());
		paramsMap.put("startQty", task.getStartQty());
		paramsMap.put("endQty", task.getEndQty());
		
		paramsMap.put("beforeTime", beforeTime);
		this.getSqlSession().insert("foss.tfr.StTaskListDao.addStTaskListFromStockForExpress", paramsMap);
	}
	/**
	 * 新增一个清仓任务列表快照(快递员清仓,只清零担)
	 * @param stTaskId
	 * @param orgCode
	 * @param goodsAreaCode
	 * @author 268084
	 * @date 
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskListDao#addStTaskListFromStock(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void addStTaskListFromStockForNoExpress(StTaskEntity task,
			int beforeTime) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("stTaskId", task.getId());
		paramsMap.put("orgCode", task.getDeptcode());
		paramsMap.put("goodsAreaCode", task.getGoodsareacode());
		paramsMap.put("receiveMethodList", task.getReceiveMethodList());
		paramsMap.put("districtCodeList", task.getDistrictCodeList());
		paramsMap.put("startQty", task.getStartQty());
		paramsMap.put("endQty", task.getEndQty());
		paramsMap.put("beforeTime", beforeTime);
		this.getSqlSession().insert("foss.tfr.StTaskListDao.addStTaskListFromStockForNoExpress", paramsMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PdaStTaskDto> queryPdaStSnap(String stTaskNo) {
		return super.getSqlSession().selectList("foss.tfr.StTaskListDao.queryPdaStSnap", stTaskNo);
	}

	@Override
	public Long queryContraband(String waybillNo) {
		return (Long) super.getSqlSession().selectOne("foss.tfr.StTaskListDao.queryContraband", waybillNo);
	}

	@Override
	public PdaStTaskDto queryWaybillInfo(String waybillNo) {
		return (PdaStTaskDto) super.getSqlSession().selectOne("foss.tfr.StTaskListDao.queryWaybillInfo", waybillNo);
	}

	@Override
	public Long queryNeedPack(String waybillNo) {
		return (Long) super.getSqlSession().selectOne("foss.tfr.StTaskListDao.queryNeedPack", waybillNo);
	}

	@Override
	public String queryOrgStationNum(String code) {
		return (String) super.getSqlSession().selectOne("foss.tfr.StTaskListDao.queryOrgStationNum", code);
	}

	@Override
	public String queryOuterBranchStationNum(String code) {
		return (String) super.getSqlSession().selectOne("foss.tfr.StTaskListDao.queryOuterBranchStationNum", code);
	}
}