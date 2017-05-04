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
 * PROJECT NAME	: pkp-creating
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/dao/impl/ActualFreightDao.java
 * 
 * FILE NAME        	: ActualFreightDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.deppon.foss.module.pickup.creating.client.dao.IActualFreightDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.util.UUIDUtils;
import com.google.inject.Inject;

/**
 * 
 * 运单状态数据持久层
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-10-30 下午5:46:39,content: </p>
 * @author foss-sunrui
 * @date 2012-10-30 下午5:46:39
 * @since
 * @version
 */
public class ActualFreightDao implements IActualFreightDao {
    
    private static final String NAMESPACE = "foss.pkp.ActualFreightEntityMapper.";
    
	private SqlSession sqlSession;

	@Inject
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

    @Override
    public int insertSelective(ActualFreightEntity record) {
	record.setId(UUIDUtils.getUUID());
	return this.sqlSession.insert(NAMESPACE + "insertSelective", record);
    }

    @Override
    public ActualFreightEntity queryByPrimaryKey(String id) {
	return (ActualFreightEntity) this.sqlSession.selectOne(NAMESPACE + "selectByPrimaryKey", id);
    }
    
    @Override
    public ActualFreightEntity queryByWaybillNo(String waybillNo) {
	return (ActualFreightEntity) this.sqlSession.selectOne(NAMESPACE + "selectByWaybillNo", waybillNo);
    }

    @Override
    public int updateByPrimaryKeySelective(ActualFreightEntity record) {
	return this.sqlSession.update(NAMESPACE + "updateByPrimaryKeySelective", record);
    }

	@Override
	public int updateSubGenerateGoodsQtyByWaybillNo(ActualFreightEntity entity) {
		return this.sqlSession.update(NAMESPACE + "updateSubGenerateGoodsQtyByWaybillNo",entity);
	}
	@Override
	public int updateAddGenerateGoodsQtyByWaybillNo(ActualFreightEntity entity) {
		return this.sqlSession.update(NAMESPACE + "updateAddGenerateGoodsQtyByWaybillNo",entity);
	}

	@Override
	public int updateGenerateGoodsQtyByWaybillNo(ActualFreightEntity entity) {
		return this.sqlSession.update(NAMESPACE + "updateGenerateGoodsQtyByWaybillNo",entity);
	}

	/**
	 * 新增运单附件信息
	 */
	@Override
	public int insertActualFreightEntity(ActualFreightEntity entity) {
		return this.sqlSession.insert(NAMESPACE + "insert", entity);
	}

	@Override
	public int updateArriveNotoutGoodsQtyByWaybillNo(String waybillNo,
			Integer signGoodsQty) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("signGoodsQty", signGoodsQty);
		map.put("waybillNo", waybillNo);
	   return this.sqlSession.update(NAMESPACE + "updateArriveNotoutGoodsQtyByWaybillNo",map);
	}
	/**
	 * 
     * 根据运单号
     * 更新ActualFreight中的“到达未出库件数”=“到达未出库件数”+“作废到达联的签收件数”，
	 *	“已生成到达联件数”=“已生成到达联件数”-“作废到达联的件数”
     * @author foss-meiying
     * @date 2012-12-5 下午3:17:47
     * @param actualFreightEntity.waybillNo 运单号
     * @param actualFreightEntity.arriveNotoutGoodsQty 作废到达联的签收件数
     * @param actualFreightEntity.generateGoodsQty 作废到达联的件数
     * 
     * @return
     * @see
     */
	@Override
	public int updateActualFreightByWaybillNo(
			ActualFreightEntity actualFreightEntity) {
		return this.sqlSession.update(NAMESPACE + "updateActualFreightByWaybillNo",actualFreightEntity);
	}

    /**
     * 
     * 根据单号更新结清状态
     * @author 043258-foss-zhaobin
     * @date 2012-12-20 下午7:34:20
     */
	@Override
	public void updateActualFreightByNo(ActualFreightEntity actualFreightEntity)
	{
		 this.sqlSession.update(NAMESPACE + "updateActualFreightByNo",actualFreightEntity);
	}

}