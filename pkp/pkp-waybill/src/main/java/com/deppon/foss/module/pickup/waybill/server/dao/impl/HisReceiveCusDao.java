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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/dao/impl/HisReceiveCusDao.java
 * 
 * FILE NAME        	: HisReceiveCusDao.java
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
import com.deppon.foss.module.pickup.waybill.api.server.dao.IHisReceiveCusDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.HisReceiveCusEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.HisCustomerDto;
import com.deppon.foss.util.UUIDUtils;

/**
 * 
 * 历史收货客户信息数据持久层
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-sunrui,date:2012-10-31 上午11:35:39,content:TODO
 * </p>
 * 
 * @author foss-sunrui
 * @date 2012-10-31 上午11:35:39
 * @since
 * @version
 */
public class HisReceiveCusDao extends iBatis3DaoImpl implements IHisReceiveCusDao {

	private static final String NAMESPACE = "foss.pkp.HisReceiveCusEntityMapper.";

	@Override
	public int insertSelective(HisReceiveCusEntity record) {
		record.setId(UUIDUtils.getUUID());
		record.setCreateTime(new Date());
		return this.getSqlSession().insert(NAMESPACE + "insertSelective", record);
	}

	@Override
	public HisReceiveCusEntity queryByPrimaryKey(String id) {
		return (HisReceiveCusEntity) this.getSqlSession().selectOne(NAMESPACE + "selectByPrimaryKey", id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HisReceiveCusEntity> queryByCondition(HisCustomerDto hisCustomer) {
		return this.getSqlSession().selectList(NAMESPACE + "selectByCondition", hisCustomer);
	}

	@Override
	public int updateByPrimaryKeySelective(HisReceiveCusEntity record) {
		return this.getSqlSession().insert(NAMESPACE + "updateByPrimaryKeySelective", record);
	}

	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author foss-gengzhe
	 * @date 2012-12-13 上午10:39:54
	 * @param phone
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IHisReceiveCusDao#selectByPhone(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HisReceiveCusEntity> selectByPhone(String phone) {
		return (List<HisReceiveCusEntity>) this.getSqlSession().selectList(NAMESPACE + "selectByPhone", phone);
	}

	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author foss-gengzhe
	 * @date 2012-12-13 上午10:39:58
	 * @param mobilephone
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IHisReceiveCusDao#selectByMobilephone(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HisReceiveCusEntity> selectByMobilephone(String mobilephone) {
		return (List<HisReceiveCusEntity>) this.getSqlSession().selectList(NAMESPACE + "selectByMobilephone", mobilephone);
	}

	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author foss-gengzhe
	 * @date 2012-12-13 上午10:40:05
	 * @param record
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IHisReceiveCusDao#insert(com.deppon.foss.module.pickup.waybill.shared.domain.HisReceiveCusEntity)
	 */
	@Override
	public int insert(HisReceiveCusEntity record) {
		record.setId(UUIDUtils.getUUID());
		record.setCreateTime(new Date());
		return this.getSqlSession().insert(NAMESPACE + "insert", record);
	}
	
	/**
	 * 根据多个手机号码查询最近三个月的历史收货客户信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 下午2:19:19
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HisReceiveCusEntity> queryReceiveCustByMobileList(List<String> mobileList,Date time){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("mobiles",mobileList);
		map.put("createTime", time);
		return this.getSqlSession().selectList(NAMESPACE + "selectByMobileList", map);
	}

	
	/**
	 * 根据多个电话号码查询最近三个月的历史收货客户信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 下午2:19:19
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HisReceiveCusEntity> queryReceiveCustByPhoneList(List<String> phoneList,Date time){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("phones",phoneList);
		map.put("createTime", time);
		return this.getSqlSession().selectList(NAMESPACE + "selectByPhoneList", map);
	}
}