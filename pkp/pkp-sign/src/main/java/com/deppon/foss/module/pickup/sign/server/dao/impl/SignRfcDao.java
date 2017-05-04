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
 * PROJECT NAME	: pkp-sign
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/dao/impl/SignRfcDao.java
 * 
 * FILE NAME        	: SignRfcDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.sign.api.server.dao.ISignRfcDao;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignRfcEntity;
import com.deppon.foss.util.UUIDUtils;

/**
 * 签收变更Dao
 * 
 * @author ibm-lizhiguo
 * @date 2012-10-29 下午6:16:18
 */
public class SignRfcDao extends iBatis3DaoImpl implements ISignRfcDao {

	/*** 20121116 ***/
	// 签收付款的NAMESPACE
	private static final String SIGNRFCREPAYMENTNAMESPACE = "com.deppon.foss.module.pickup.sign.api.shared.domain.SignRfcEntity.";
	// 获得变更签收LIST
	private static final String SIGNRFCLIST = "querySignRfcList";
	// 获得变更签收条数
	private static final String TOTALCOUNT = "getTotalCount";
	// 保存变更签收数据
	private static final String INSERTSELECTIVE = "insertSelective";
	//申请数据
	private static final String SELECTBYID = "selectById";
	//更新申请数据
	private static final String UPDATEBYID = "updateByPrimaryKeySelective";
	//根据运单号查询
	private static final String SELECTBYWAYBILL = "querySignRfcListByWaybill";
	//根据条件查询
	private static final String SELECTBYCONDITION = "querySignRfcListByCondition";
	//判断有无申请中或者已同意的反签收运单
	private static final String GETWAYBILLAPPROVALCOUNTLING = "getWaybillApprovalCountling";
	/**
	 * 
	 * 获得变更申请LIST
	 * @author ibm-lizhiguo
	 * @date 2012-11-20 下午6:05:08
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SignRfcEntity> querySignRfcList(SignRfcEntity entity,int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(SIGNRFCREPAYMENTNAMESPACE + SIGNRFCLIST,
				entity, rowBounds);
	}
	/**
	 * 
	 * <p>更具运单号查询<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-20
	 * @param entity
	 * @return
	 * List<SignRfcEntity>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SignRfcEntity> querySignRfcListByWaybill(SignRfcEntity entity) {
		return getSqlSession().selectList(SIGNRFCREPAYMENTNAMESPACE + SELECTBYWAYBILL,entity);
	}

	/**
	 * 
	 * 获得申请条数
	 * @author ibm-lizhiguo
	 * @date 2012-11-20 下午6:05:18
	 */
	@Override
	public Long getTotalCount(SignRfcEntity entity) {
		return (Long) this.getSqlSession().selectOne(
				SIGNRFCREPAYMENTNAMESPACE + TOTALCOUNT, entity);
	}
	
	/**
	 * 
	 * 根据运单号查询所有到达更改未审批的记录
	 * @param entity
	 * @return
	 * @author ibm-wangfei
	 * @date Jun 29, 2013 10:03:32 AM
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.ISignRfcDao#getWaybillNotApprovalCount(com.deppon.foss.module.pickup.sign.api.shared.domain.SignRfcEntity)
	 */
	@Override
	public Long getWaybillNotApprovalCount(SignRfcEntity entity) {
		return (Long) this.getSqlSession().selectOne(
				SIGNRFCREPAYMENTNAMESPACE + "getWaybillNotApprovalCount", entity);
	}

	/**
	 * 
	 * 增加申请数据
	 * @author ibm-lizhiguo
	 * @date 2012-11-20 下午6:05:23
	 */
	@Override
	public int insertSelective(SignRfcEntity entity) {
		entity.setId(UUIDUtils.getUUID());
		return getSqlSession().insert(
				SIGNRFCREPAYMENTNAMESPACE + INSERTSELECTIVE, entity);
	}
	
	/**
	 * 
	 * <p>获得申请数据详细<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2012-12-5
	 * @param id
	 * @return
	 * SignRfcEntity
	 */
	@Override
	public SignRfcEntity selectById(String id){
		return (SignRfcEntity)getSqlSession().selectOne(SIGNRFCREPAYMENTNAMESPACE+SELECTBYID, id);
	}
	
	/**
	 * 
	 * <p>修改申请数据<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2012-12-12
	 * @param entity
	 * @return
	 * int
	 */
	@Override
	public int updateSelective(SignRfcEntity entity) {
		return getSqlSession().update(SIGNRFCREPAYMENTNAMESPACE + UPDATEBYID, entity);
	}
	/**
	 * 
	 * <p>获得签收结果LIST<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2012-12-22
	 * @param entity
	 * @return
	 * List<SignRfcEntity>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SignRfcEntity> querySignRfcList(SignRfcEntity entity) {
		return getSqlSession().selectList(SIGNRFCREPAYMENTNAMESPACE + SIGNRFCLIST,entity);
	}
	/**
	 * 查询运单的反签收记录和变更详情type
	 * @author 231438
	 * @param entity
	 * @return
	 * List<SignRfcEntity>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SignRfcEntity> queryReserveSignListByCondition(SignRfcEntity entity) {
		return getSqlSession().selectList(SIGNRFCREPAYMENTNAMESPACE + SELECTBYCONDITION,entity);
	}
	/**
	 * @param entity
	 * @return 查询审批中的签收变更单的运单数量
	 * @date 2017年3月7日 20:31:34
	 * @author 378375
	 */
	public Integer getWaybillApprovalCountling(SignRfcEntity entity){
		return (Integer)getSqlSession().selectOne(SIGNRFCREPAYMENTNAMESPACE + GETWAYBILLAPPROVALCOUNTLING,entity);
	}
}