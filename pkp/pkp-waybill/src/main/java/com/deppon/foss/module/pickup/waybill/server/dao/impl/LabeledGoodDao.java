/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/dao/impl/LabeledGoodDao.java
 * 
 * FILE NAME        	: LabeledGoodDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.exceptions.TooManyResultsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.ILabeledGoodDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabeledGoodDto;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 货签数据持久层
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-sunrui,date:2012-10-30 下午3:18:06
 * </p>
 * 
 * @author foss-sunrui
 * @date 2012-10-30 下午3:18:06
 * @since
 * @version
 */
public class LabeledGoodDao extends iBatis3DaoImpl implements ILabeledGoodDao {

	private static final String NAMESPACE = "foss.pkp.LabeledGoodMapper.";
	protected final static Logger LOG = LoggerFactory.getLogger(LabeledGoodDao.class.getName());
	   
	/**
	 * 
	 * <p>
	 * 按需插入
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-30 下午3:31:11
	 * @param record
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.ILabeledGoodDao#insertSelective(com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity)
	 */
	@Override
	public int insertSelective(LabeledGoodEntity record) {
		record.setId(UUIDUtils.getUUID());
		return this.getSqlSession().insert(
				NAMESPACE + "insertLabeledGoodSelective", record);
	}

	/**
	 * 
	 * <p>
	 * 批量插入
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-11-6 下午4:54:09
	 * @param list
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.ILabeledGoodDao#insertBatch(java.util.List)
	 */
	@Override
	public int insertBatch(List<LabeledGoodEntity> list) {
		try {
			int j = 0;
			if (list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					list.get(i).setId(UUIDUtils.getUUID());
					j= j +this.getSqlSession().insert(
							NAMESPACE + "insertLabeledGood", list.get(i));
				}
				return j;
			} else {
				return 0;
			}
		} catch (Exception e) {
			LOG.error("insertBatch", e);
			throw new BusinessException(e.getMessage(),e);
			//这个负数有特殊作用  是告诉外界插入异常 需要他做其他处理的
		}
	}
	@Override
	public int insertBatchLabeledGoods(List<LabeledGoodEntity> list) {
		if(CollectionUtils.isNotEmpty(list)){
			if(list.size() > FossConstants.LIMIT_SIZE){
				int count = 0;
				List<List<LabeledGoodEntity>> splitList = CollectionUtils.splitListBySize(list, FossConstants.LIMIT_SIZE);
				for(List<LabeledGoodEntity> labeledGoodEntityList : splitList){
					count += this.getSqlSession().insert(
								NAMESPACE + "insertBatchLabeledGoods", labeledGoodEntityList);
				}
				return count;
			}else{
				return this.getSqlSession().insert(
						NAMESPACE + "insertBatchLabeledGoods", list);
			}
		}else{
			return 0;
		}
	}

	/**
	 * 
	 * <p>
	 * 按需删除
	 * 
	 * 不是真正的删除 是设置 active 为N
	 * 
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-30 下午3:31:11
	 * @param record
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.ILabeledGoodDao#deleteGoodEntity(com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity)
	 */
	public int deleteGoodEntity(LabeledGoodEntity record) {
		return this.getSqlSession().update(
				NAMESPACE + "deleteGoodEntity", record);
	}

	/**
	 * 
	 * <p>
	 * 按需插入
	 * 
	 * 不是真正的删除 是设置 active 为N
	 * 
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-30 下午3:31:11
	 * @param record
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.ILabeledGoodDao#deleteGoodEntityBatch(com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity)
	 */
	public int deleteGoodEntityBatch(List<LabeledGoodEntity> records) {
		int j = 0;
		for(int i =0 ;i <records.size(); i ++){
			LabeledGoodEntity record = records.get(i);
			j = j + this.getSqlSession().update(
					NAMESPACE + "deleteGoodEntity", record);
		}
		return j;
	}

	/**
	 * 
	 * <p>
	 * 通过主键查询
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-30 下午3:31:25
	 * @param id
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.ILabeledGoodDao#queryByPrimaryKey(java.lang.String)
	 */
	@Override
	public LabeledGoodEntity queryByPrimaryKey(String id) {
		return (LabeledGoodEntity) this.getSqlSession().selectOne(
				NAMESPACE + "selectLabeledGoodByPrimaryKey", id);
	}

	/**
	 * 
	 * <p>
	 * 通过运单号和流水号查询
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-30 下午3:31:41
	 * @param labeledGoodDto
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.ILabeledGoodDao#queryByWaybillNoAndSerialNo(java.util.Map)
	 */
	@Override
	public LabeledGoodEntity queryByWaybillNoAndSerialNo(
			LabeledGoodDto labeledGoodDto, boolean isActive) throws TooManyResultsException {
		Map<String,String> map = new HashMap<String,String>();
		map.put("waybillNo", labeledGoodDto.getWaybillNo());
		map.put("serialNo", labeledGoodDto.getSerialNo());
		if(isActive){
			map.put("active", FossConstants.ACTIVE);
		}else{
			map.put("active", FossConstants.INACTIVE);
		}
		return (LabeledGoodEntity) this.getSqlSession().selectOne(
				NAMESPACE + "selectLabeledGoodByTwoNo", map);
	}

	/**
	 * 
	 * <p>
	 * 通过运单号查询最大的序列号
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-30 下午4:56:29
	 * @param waybillNo
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.ILabeledGoodDao#queryLastSerialByWaybillNo(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public LabeledGoodEntity queryLastSerialByWaybillNo(String waybillNo) {
		List<LabeledGoodEntity> labeledGoodList = this.getSqlSession()
				.selectList(NAMESPACE + "selectlastSerialNoBywaybillNo",
						waybillNo);
		if (labeledGoodList.size() > 0) {
			return labeledGoodList.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 
	 * <p>
	 * 通过主键更新
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-30 下午3:32:10
	 * @param record
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.ILabeledGoodDao#updateByPrimaryKeySelective(com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity)
	 */
	@Override
	public int updateByPrimaryKeySelective(LabeledGoodEntity record) {
		return this.getSqlSession().update(
				NAMESPACE + "updateLabeledGoodByPrimaryKeySelective", record);
	}

	/**
	 * 
	 * <p>
	 * 通过运单号查询所有流水号
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date 2012-10-30 下午4:55:34
	 * @param waybillNo
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LabeledGoodEntity> queryAllSerialByWaybillNo(String waybillNo) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("waybillNo", waybillNo);
		map.put("active", FossConstants.YES);
		List<LabeledGoodEntity> labeledGoodList = this.getSqlSession()
				.selectList(NAMESPACE + "selectlastSerialNoBywaybillNo",
						map);
		return labeledGoodList;
	}

	/**
	 * 
	 * <p>
	 * 通过运单号查询所有激活流水号
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date 2012-10-30 下午4:55:34
	 * @param waybillNo
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	public List<LabeledGoodEntity> queryActiveSerialNoBywaybillNo(
			String waybillNo) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("waybillNo", waybillNo);
		map.put("active", FossConstants.ACTIVE);
		return this.getSqlSession()
				.selectList(NAMESPACE + "selectActiveSerialNoBywaybillNo",
						waybillNo);
	}

	/**
	 * 更改运单号
	 * @param waybillNo
	 * @param waybillNo2
	 */
	public void modifyWaybillNo(String oldWaybillNo, String newWaybillNo) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("oldWaybillNo", oldWaybillNo);
		map.put("newWaybillNo", newWaybillNo);
		this.getSqlSession()
				.selectList(NAMESPACE + "modifyWaybillNo",
						map);
	}

	/**
	 * 通过运单号和流水号查询(为中转提供) 
	 * @author WangQianJin
	 * @date 2013-6-20 下午3:50:41
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LabeledGoodEntity> queryLabeledGoodByWaybillNoAndSerialNo(String waybillNo, String serialNo){
		Map<String,String> map = new HashMap<String,String>();
		map.put("waybillNo", waybillNo);
		map.put("serialNo", serialNo);
		map.put("active", FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE + "queryLabeledGoodByWaybillNoAndSerialNo", map);				
	}

	/**
	 * @function 根据单号查询所有已经作废的流水号信息
	 * @author Foss-105888-Zhangxingwang
	 * @date 2013-11-6 10:05:16
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LabeledGoodEntity> queryLabelGoodStatusisNByWaybillNo(
			String waybillNo, String status) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("waybillNo", waybillNo);
		map.put("active", status);
		return this.getSqlSession().selectList(NAMESPACE+"selectlastSerialNoBywaybillNo", map);
	}
	
	/**
	 * 根据运单号删除流水号信息
	 * @remark 
	 * @author WangQianJin
	 * @date 2014-1-21 上午9:23:50
	 */
	@Override
	public int deleteLabeledGoodByWaybillNo(String waybillNo){
		Map<String, Object> map = new HashMap<String, Object>();		
		map.put("active", FossConstants.INACTIVE);
		map.put("waybillNo", waybillNo);
		map.put("conActive", FossConstants.ACTIVE);
		return this.getSqlSession().update(NAMESPACE + "deleteLabeledGoodByWaybillNo", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LabeledGoodEntity> queryLabeledGoodByWaybillNoWithSerial(
			String waybillNo, List<String> serialNoList, String active) {
		Map<String, Object> map = new HashMap<String, Object>();		
		map.put("serialNoList", serialNoList);
		map.put("waybillNo", waybillNo);
		map.put("active", active);
		return this.getSqlSession().selectList(NAMESPACE+"queryLabeledGoodByWaybillNoWithSerial", map);
	}

	@Override
	public void deleteLabByWaybillNos(List<String> waybillNos) {
		
		this.getSqlSession().delete(NAMESPACE+"deleteLabByWaybillNos", waybillNos);
	}

	@Override
	public void deleteLabByWaybillNo(String waybillNo) {
		this.getSqlSession().delete(NAMESPACE+"deleteLabByWaybillNo", waybillNo);
		
	}
	
}