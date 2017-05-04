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
 * PROJECT NAME	: pkp-sign-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/server/dao/ISignRfcDao.java
 * 
 * FILE NAME        	: ISignRfcDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.sign.api.shared.domain.SignRfcEntity;




/**
 * 变更签收单
 * @author ibm-lizhiguo
 * @date 2012-10-18 上午9:46:23
 */
public interface ISignRfcDao {

	/**
	 * 
	 * 获得变更申请LIST
	 * @author ibm-lizhiguo
	 * @date 2012-11-20 下午6:05:08
	 */
	List<SignRfcEntity> querySignRfcList(SignRfcEntity entity,int start, int limit);

	/**
	 * 
	 * 获得申请条数
	 * @author ibm-lizhiguo
	 * @date 2012-11-20 下午6:05:18
	 */
	Long getTotalCount(SignRfcEntity entity);

	/**
	 * 
	 * 增加申请数据
	 * @author ibm-lizhiguo
	 * @date 2012-11-20 下午6:05:23
	 */
	int insertSelective(SignRfcEntity entity);

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
	SignRfcEntity selectById(String id);
	
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
	int updateSelective(SignRfcEntity entity);
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
	List<SignRfcEntity> querySignRfcList(SignRfcEntity entity);

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
	List<SignRfcEntity> querySignRfcListByWaybill(SignRfcEntity entity);

	Long getWaybillNotApprovalCount(SignRfcEntity entity);
	/**
	 * 查询运单的反签收记录和变更详情type
	 * @author 231438
	 * @param entity
	 * @return
	 * List<SignRfcEntity>
	 */
	List<SignRfcEntity> queryReserveSignListByCondition(SignRfcEntity entity);
    /**
     * @param condition
     * @return 查询审批中的签收变更单的运单数量
     * @date 2017年3月7日 20:34:04
     * @author 378375
     */
	Integer getWaybillApprovalCountling(SignRfcEntity condition);
	
}