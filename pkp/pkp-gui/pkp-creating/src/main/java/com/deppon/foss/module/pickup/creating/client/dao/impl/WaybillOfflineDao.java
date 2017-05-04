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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/dao/impl/WaybillOfflineDao.java
 * 
 * FILE NAME        	: WaybillOfflineDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.deppon.foss.module.pickup.creating.client.dao.IWaybillOfflineDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillOfflineEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillOfflineDto;
import com.google.inject.Inject;

/**
 * 
 * 离线运单数据持久层访问类
 * @author 105089-foss-yangtong
 * @date 2012-10-16 下午5:28:47
 * @since
 * @version
 */
public class WaybillOfflineDao implements IWaybillOfflineDao {
	//name space sornar needed
	private static final String NAMESPACE = "pkp.waybillOfflineMapper.";
			
	private SqlSession sqlSession;

	@Inject
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	/**
	 * <p>
	 * 通过主键获取本地运单
	 * </p>
	 * 
	 * @author Bobby
	 * @date 2012-10-16 上午11:09:15
	 * @param id
	 * @return
	 * @see com.deppon.foss.module.pickup.creating.client.dao.IWaybillDao#selectByPrimaryKey(java.lang.String)
	 */
	@Override
	public WaybillOfflineEntity queryByPrimaryKey(String id) {
		return sqlSession.selectOne(NAMESPACE
				+ "selectWaybillLocalByPrimaryKey", id);
	}

	/**
	 * <p>
	 * 通过运单号查本地运单
	 * </p>
	 * 
	 * @author Bobby
	 * @date 2012-10-16 上午11:27:19
	 * @param waybillNo
	 * @return
	 * @see com.deppon.foss.module.pickup.creating.client.dao.IWaybillDao#selectByWaybillNo(java.lang.String)
	 */
	@Override
	public WaybillOfflineEntity queryByWaybillNo(String waybillNo) {
		return sqlSession.selectOne(NAMESPACE + "selectByWaybillLocalNo",
				waybillNo);
	}

	/**
	 * <p>
	 * 获取最新本地运单
	 * </p>
	 * 
	 * @author Bobby
	 * @date 2012-10-16 上午11:09:09
	 * @return
	 * @see com.deppon.foss.module.pickup.creating.client.dao.IWaybillDao#selectLastWaybill()
	 */
	@Override
	public WaybillOfflineEntity queryLastWaybill() {
		return sqlSession.selectOne(NAMESPACE + "selectLastWaybillLocal");
	}

    /**
     * 更新离线运单打印次数
     * 
     * @author foss-jiangfei
     * @date 2012-11-15 下午11:48:25
     * @param waybillNo
     * @return
     * @see
     */
    public int updateWaybillPrintTimes(String waybillNo) {
    Map<String, Object> map = new HashMap<String, Object>();
	map.put("printTimes", +1);
	map.put("waybillNo", waybillNo);
	return sqlSession.update(NAMESPACE + "updateWaybillPrintTimeOffLine",
			map);
    }
	
	/**
	 * 
	 * 获取离线运单
	 * @date 2012-10-16 下午5:29:27
	 * @return
	 * @see
	 */
	@Override
	public List<WaybillOfflineEntity> queryWaybillOffline(
			WaybillOfflineDto waybillOfflineDto) {
		/*Map<String, Object> parms = new HashMap<String, Object>();
		//制单时间
		parms.put("billStartTime", waybillOfflineDto.getBillStartTime());
		parms.put("billEndTime", waybillOfflineDto.getBillEndTime());
		//FOSS提交时间
		parms.put("createStartTime", waybillOfflineDto.getCreateStartTime());
		parms.put("createEndTime", waybillOfflineDto.getCreateEndTime());
		//运单、订单号
		parms.put("mixNo",waybillOfflineDto.getWaybillOfflineEntity().getWaybillNo());
		//收货部门
		parms.put("receiveOrgCode",waybillOfflineDto.getWaybillOfflineEntity().getReceiveOrgCode());
		//运输性质
		parms.put("productCode",waybillOfflineDto.getWaybillOfflineEntity().getProductCode());
		//运单状态
		parms.put("active",waybillOfflineDto.getWaybillOfflineEntity().getActive());
		//开单人
		parms.put("createUserCode",waybillOfflineDto.getWaybillOfflineEntity().getCreateUserCode());
		//制单部门
		parms.put("createOrgCode",waybillOfflineDto.getWaybillOfflineEntity().getCreateOrgCode());*/
		return sqlSession.selectList( NAMESPACE + "getWaybillOfflines", waybillOfflineDto);
	}
	
	/**
	 * 
	 * <p>
	 * 通过运单号/订单号判断运单是否存在OFFLINE
	 * </p>
	 * @author foss-yangtong
	 * @date 2012-10-30 下午7:44:25
	 * @return
	 * @see 
	 */
	@Override
	public WaybillOfflineEntity queryOfflineByNo(String mixNo) {
		return sqlSession.selectOne(NAMESPACE + "queryOffine", mixNo);
	}

	@Override
	public Integer countOfflineActiveWayBill() {
		return sqlSession.selectOne(NAMESPACE + "countOffineActiveWaybill");
	}
}