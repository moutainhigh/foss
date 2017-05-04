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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/dao/impl/HisDeliveryCusDao.java
 * 
 * FILE NAME        	: HisDeliveryCusDao.java
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

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IHisDeliveryCusDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.HisDeliveryCusEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.HisCustomerDto;
import com.deppon.foss.util.UUIDUtils;

/**
 * 
 * 历史发货客户信息数据持久层
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-sunrui,date:2012-10-31 上午11:37:13,content:TODO
 * </p>
 * 
 * @author foss-sunrui
 * @date 2012-10-31 上午11:37:13
 * @since
 * @version
 */
public class HisDeliveryCusDao extends iBatis3DaoImpl implements IHisDeliveryCusDao {

	private static final String NAMESPACE = "foss.pkp.HisDeliveryCusEntityMapper.";

	@Override
	public int insertSelective(HisDeliveryCusEntity record) {
		record.setId(UUIDUtils.getUUID());
		record.setCreateTime(new Date());
		return this.getSqlSession().insert(NAMESPACE + "insertSelective", record);
	}

	@Override
	public HisDeliveryCusEntity selectByPrimaryKey(String id) {
		return (HisDeliveryCusEntity) this.getSqlSession().selectOne(NAMESPACE + "selectByPrimaryKey", id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HisDeliveryCusEntity> queryByCondition(HisCustomerDto hisCustomer) {
		return this.getSqlSession().selectList(NAMESPACE + "selectByCondition", hisCustomer);
	}

	@Override
	public int updateByPrimaryKeySelective(HisDeliveryCusEntity record) {
		return this.getSqlSession().insert(NAMESPACE + "updateByPrimaryKeySelective", record);
	}
	
	/**
	 * 
	 * <p>根据电话找历史接货客户</p> 
	 * @author foss-gengzhe
	 * @date 2012-12-13 上午10:18:24
	 * @param phone
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IHisDeliveryCusDao#selectByPhone(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HisDeliveryCusEntity> selectByPhone(String phone) {
		return (List<HisDeliveryCusEntity>) this.getSqlSession().selectList(NAMESPACE + "selectByPhone", phone);
	}
	
	/**
	 * 根据多个电话号码查询历史发货客户信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 下午2:19:19
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HisDeliveryCusEntity> queryDeliveryCustByPhoneList(List<String> phoneList,Date time){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("phones",phoneList);
		map.put("createTime", time);
		return this.getSqlSession().selectList(NAMESPACE + "selectByPhoneList", map);
	}

	/**
	 * 
	 * <p>根据手机找历史接货客户</p> 
	 * @author foss-gengzhe
	 * @date 2012-12-13 上午10:18:32
	 * @param mobilephone
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IHisDeliveryCusDao#selectByMobilePhone(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HisDeliveryCusEntity> selectByMobilephone(String mobilephone) {
		return (List<HisDeliveryCusEntity>) this.getSqlSession().selectList(NAMESPACE + "selectByMobilephone", mobilephone);
	}
	
	/**
	 * 根据多个手机号码查询历史发货客户信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 下午2:19:19
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HisDeliveryCusEntity> queryDeliveryCustByMobileList(List<String> mobileList,Date time){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("mobiles",mobileList);
		map.put("createTime", time);
		return this.getSqlSession().selectList(NAMESPACE + "selectByMobileList", map);
	}
	
	/**
	 * 
	 * <p>新增收货客户</p> 
	 * @author foss-gengzhe
	 * @date 2012-12-13 上午10:34:07
	 * @param record
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IHisDeliveryCusDao#insert(com.deppon.foss.module.pickup.waybill.shared.domain.HisDeliveryCusEntity)
	 */
	@Override
	public int insert(HisDeliveryCusEntity record) {
		record.setId(UUIDUtils.getUUID());
		record.setCreateTime(new Date());
		return this.getSqlSession().insert(NAMESPACE + "insert", record);
	}

}