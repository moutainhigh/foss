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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/dao/impl/ActualFreightDao.java
 * 
 * FILE NAME        	: ActualFreightDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IActualFreightDao;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.StockMoveDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ActualFreightDto;
import com.deppon.foss.util.UUIDUtils;

/**
 * 
 * 运单状态数据持久层
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-10-30 下午5:46:39</p>
 * @author foss-sunrui
 * @date 2012-10-30 下午5:46:39
 * @since
 * @version
 */
public class ActualFreightDao extends iBatis3DaoImpl implements IActualFreightDao {
    
    private static final String NAMESPACE = "foss.pkp.ActualFreightEntityMapper.";

    /**
     * 新增ActualFreightEntity实体
     * @author 043260-foss-suyujun
     * @date 2012-12-24
     * @param record
     * @return
     * @@see com.deppon.foss.module.pickup.waybill.api.server.dao.IActualFreightDao#insertSelective(com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity)
     */
    @Override
    public int insertSelective(ActualFreightEntity record) {
		record.setId(UUIDUtils.getUUID());
    	if( record.getCreateTime() != null){
    		record.setModifyTime(new Date());
    	}else{
    		record.setCreateTime(new Date());
    		record.setModifyTime(record.getCreateTime());
    	}
    	return this.getSqlSession().insert(NAMESPACE + "insertSelective", record);
    }

    /**
     * 根据id查询ActualFreightEntity
     * @author 043260-foss-suyujun
     * @date 2012-12-24
     * @param id
     * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IActualFreightDao#queryByPrimaryKey(java.lang.String)
     */
    @Override
    public ActualFreightEntity queryByPrimaryKey(String id) {
    	return (ActualFreightEntity) this.getSqlSession().selectOne(NAMESPACE + "selectByPrimaryKey", id);
    }

    /**
     * 根据运单号查询ActualFreightEntity
     * @author 043260-foss-suyujun
     * @date 2012-12-24
     * @param waybillNo
     * @return ActualFreightEntity
     * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IActualFreightDao#queryByWaybillNo(java.lang.String)
     */
    @Override
    public ActualFreightEntity queryByWaybillNo(String waybillNo) {
    	return (ActualFreightEntity) this.getSqlSession().selectOne(NAMESPACE + "selectByWaybillNo", waybillNo);
    }

    /**
     * 根据主键更新ActualFreightEntity
     * @author 043260-foss-suyujun
     * @date 2012-12-24
     * @param record
     * @return int
     * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IActualFreightDao#updateByPrimaryKeySelective(com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity)
     */
    @Override
    public int updateByPrimaryKeySelective(ActualFreightEntity record) {
    	record.setModifyTime(new Date());
    	return this.getSqlSession().update(NAMESPACE + "updateByPrimaryKeySelective", record);
    }
    /**
     * 增加一个更新的方法，用于更新 统一结算信息
     */
    @Override
    public int updateActualById(ActualFreightEntity record){
    	record.setModifyTime(new Date());
    	return this.getSqlSession().update(NAMESPACE + "updateActualById", record);
    }
    /**
     * 根据主键更新ActualFreightEntity
     * @author 043260-foss-suyujun
     * @date 2012-12-24
     * @param record
     * @return int
     * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IActualFreightDao#updateByPrimaryKeySelective(com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity)
     */
    @Override
    public int updateByWaybillNo(ActualFreightEntity record) {
    	String waybillNo = record.getWaybillNo();
    	return updateByWaybillNo(record,waybillNo);
    }
    
    /**
     * 根据主键更新ActualFreightEntity
     * @author 043260-foss-suyujun
     * @date 2012-12-24
     * @param record
     * @return int
     * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IActualFreightDao#updateByPrimaryKeySelective(com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity)
     */
    @Override
    public int updateByWaybillNo(ActualFreightEntity record,String oldWaybillNo){
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("entity", record);
		map.put("oldWaybillNo", oldWaybillNo);
		map.put("modifyTime", new Date());
		return this.getSqlSession().update(NAMESPACE + "updateByWaybillNo", map);
    }

    /**
     * 更新数量
     * @author 043260-foss-suyujun
     * @date 2012-12-24
     * @param entity
     * @return
     * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IActualFreightDao#updateSubGenerateGoodsQtyByWaybillNo(com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity)
     */
    @Override
    public int updateSubGenerateGoodsQtyByWaybillNo(ActualFreightEntity entity) {
    	entity.setModifyTime(new Date());
    	return this.getSqlSession().update(NAMESPACE + "updateSubGenerateGoodsQtyByWaybillNo",entity);
    }
    /**
     * 更新ActualFreightEntity
     * @author 043260-foss-suyujun
     * @date 2012-12-24
     * @param entity
     * @return
     * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IActualFreightDao#updateAddGenerateGoodsQtyByWaybillNo(com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity)
     */
    @Override
    public int updateAddGenerateGoodsQtyByWaybillNo(ActualFreightEntity entity) {
    	entity.setModifyTime(new Date());
    	return this.getSqlSession().update(NAMESPACE + "updateAddGenerateGoodsQtyByWaybillNo",entity);
    }

    /**
     * 更新ActualFreightEntity
     * @author 043260-foss-suyujun
     * @date 2012-12-24
     * @param entity
     * @return
     * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IActualFreightDao#updateGenerateGoodsQtyByWaybillNo(com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity)
     */
    @Override
    public int updateGenerateGoodsQtyByWaybillNo(ActualFreightEntity entity) {
    	entity.setModifyTime(new Date());
    	return this.getSqlSession().update(NAMESPACE + "updateGenerateGoodsQtyByWaybillNo",entity);
    }

	/**
	 * 新增运单附件信息
	 * @author 043260-foss-suyujun
	 * @date 2012-12-24
	 * @param entity
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IActualFreightDao#insertActualFreightEntity(com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity)
	 */
	 
	@Override
	public int insertActualFreightEntity(ActualFreightEntity entity) {
		if(entity != null){
			if(entity.getCreateTime() != null){
				entity.setModifyTime(new Date());
			}else{
				entity.setCreateTime(new Date());
				entity.setModifyTime(entity.getCreateTime());
			}
		}
		return this.getSqlSession().insert(NAMESPACE + "insertSelective", entity);
	}
	/**
     * 根据运单号更新该运单到达未出库件数 或者付款方式
     * @author foss-meiying
     * @date 2012-12-5 下午3:17:47
     * @param ActualFreightDto
     * @return
     * @see
     */
	@Override
	public int updateArriveNotoutGoodsQtyByWaybillNo(ActualFreightDto dto) {
		dto.setModifyTime(new Date());
	   return this.getSqlSession().update(NAMESPACE + "updateArriveNotoutGoodsQtyByWaybillNo",dto);
	}
	/**
     * 根据运单号更新运单运单已生成的到达联,到达未出库件数,排单件数
     * @author foss-meiying
     * @date 2012-12-5 下午3:17:47
     * @param actualFreightEntity
     * 
     * @return
     * @see
     */
	@Override
	public int updateActualFreightPartByWaybillNo(
			ActualFreightEntity actualFreightEntity) {
		actualFreightEntity.setModifyTime(new Date());
		return this.getSqlSession().update(NAMESPACE + "updateActualFreightPartByWaybillNo",actualFreightEntity);
	}

    /**
     * 
     * 根据单号更新结清状态
     * @author 043258-foss-zhaobin
     * @date 2012-12-20 下午7:34:20
     */
	@Override
	public void updateActualFreightSettleStatusByNo(ActualFreightEntity actualFreightEntity)
	{
		actualFreightEntity.setModifyTime(new Date());
		 this.getSqlSession().update(NAMESPACE + "updateActualFreightSettleStatusByNo",actualFreightEntity);
	}

	/**
     * 根据单号更新到达信息
     * 
     * @author 038590-foss-wanghui
     * @date 2013-3-8 上午11:25:42
     */
	@Override
	public int updateArriveByWaybillNo(ActualFreightEntity actualFreightEntity) {
		actualFreightEntity.setModifyTime(new Date());
		return this.getSqlSession().update(NAMESPACE + "updateArriveByWaybillNo", actualFreightEntity);
	}
	/**
	 * 根据运单号修改ActualFreight部分信息 （并发控制）
	 * @author foss-meiying
	 * @date 2013-4-19 下午5:23:13
	 * @param dto
	 * @return
	 * @see
	 */
	public int updatePartGoodsQtyControlByWaybillNo(ActualFreightDto dto){
		dto.setModifyTime(new Date());
		return this.getSqlSession().update(NAMESPACE + "updatePartGoodsQtyControlByWaybillNo", dto);
	}
	
	/**
	 * 删除
	 * 
	 * @param waybillNo
	 * @return
	 * 
	 *  版本		 用户			时间				内容
	 *  0001	zxy				20130916		新增：BUG-54827  用于6月1号前的运单号失效，删除此数据
	 */
	@Override
	public int deleteActualFreightByWaybillNo(String waybillNo) {
		return this.getSqlSession().delete(NAMESPACE + "deleteActualFreightByWaybillNo", waybillNo);
	}

	/**
	 * 查询是否电子运单
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-8-18 20:38:04
	 * @param eWaybillNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ActualFreightEntity queryActualFreightByWaybillType(String eWaybillNo, String waybillType) {
		Map<String, Object> map = new HashMap<String, Object>();		
		map.put("waybillType", waybillType);
		map.put("waybillNo", eWaybillNo);
		List<ActualFreightEntity> list = this.getSqlSession().selectList(NAMESPACE + "queryActualFreightByWaybillType", map);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	/**
	 * 批量激活实际城运信息--电子运单
	 * @author liyongfei--DMANA-2035
	 * @param waybillNoList
	 * @return
	 */
	@Override
	public int setActualFreightActive(List<String> waybillNoList) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();		
		map.put("waybillNoList", waybillNoList);
		return (Integer)this.getSqlSession().update(NAMESPACE+"setActualFreightActive", map);
	}

	/**
	 * 更新城运表状态
	 * @author liyongfei
	 * @param waybillNo
	 * @return
	 */
	@Override
	public int setActualFreightStatus(String waybillNo,String status){
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		if(waybillNo==null || status==null || "".equals(waybillNo)){
			return 0;
		}
		map.put("waybillNo", waybillNo);
		map.put("status", status);
		map.put("modifyTime", new Date());
		return (Integer)this.getSqlSession().update(NAMESPACE+"setActualFreightStatus", map);
	}
	/**
	 * 货物迁移
	 */
	public void stockMove(StockMoveDto sto){
		this.getSqlSession().selectOne(NAMESPACE + "stockMoveProcedure", sto);
	}

	@Override
	public int updateByWaybillNoSelective(ActualFreightEntity actualFreightEntity) {
		//sangwenhao-272311
		actualFreightEntity.setModifyTime(new Date()) ;
		return this.getSqlSession().update(NAMESPACE+"updateByWaybillNoSelective", actualFreightEntity);
	}
	

	/**
	 * 根据运单号更新部分派送交单里通知的信息
	 * @author 159231 meiying
	 * 2015-5-4  下午6:53:32
	 * @param actualFreightEntity
	 * @return
	 */
	@Override
	public int updatePartByWaybillNo(ActualFreightEntity actualFreightEntity) {
		actualFreightEntity.setModifyTime(new Date());
		return this.getSqlSession().update(NAMESPACE + "updatePartByWaybillNo", actualFreightEntity);
	}
	/**
	 * 修改预计送货日期
	 * @author 159231 meiying
	 * 2015-5-4  下午6:53:32
	 * @param actualFreightEntity
	 * @return
	 */
	@Override
	public int updateDeliverDateByWaybillNos(ActualFreightDto dto) {
		dto.setModifyTime(new Date());
		return this.getSqlSession().update(NAMESPACE + "updateDeliverDateByWaybillNos", dto);
	}
	
}