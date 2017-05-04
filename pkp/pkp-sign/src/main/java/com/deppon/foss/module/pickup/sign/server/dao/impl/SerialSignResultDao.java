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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/dao/impl/SerialSignResultDao.java
 * 
 * FILE NAME        	: SerialSignResultDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.server.dao.impl;

import java.util.Date;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.sign.api.server.dao.ISerialSignResultDao;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SerialSignResultEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 外发流水签收Dao实现
 * 
 * @author foss-sunyanfei
 * @date 2015-10-19 上午11:50:01
 * @since
 * @version
 */
public class SerialSignResultDao extends iBatis3DaoImpl implements ISerialSignResultDao {
	/**
	 * 命名空间
	 */
	private static final String NAMESPACE = "com.deppon.foss.module.pickup.sign.api.shared.domain.SerialSignResultEntity.";
	
	/**
  	 * 新增外发流水签收信息
  	 * @author foss-sunyanfei
  	 * @date 2015-10-19 下午09:59:09
  	 * @param serialSignResultEntity
  	 */
	@Override
	public int insertSerialSignResult(SerialSignResultEntity serialSignResultEntity) {
		//id
		serialSignResultEntity.setId(UUIDUtils.getUUID());
		//是否有效
		serialSignResultEntity.setActive(FossConstants.YES);
		//签收时间
		serialSignResultEntity.setSignTime(new Date());
		//创建时间
		serialSignResultEntity.setCreateTime(new Date());
		//修改时间
		serialSignResultEntity.setModifyTime(new Date());
		return this.getSqlSession().insert(NAMESPACE+"insert", serialSignResultEntity);
	}

	/**
     * 根据运单号、流水号查询外发流水签收信息
     * @author foss-sunyanfei
     * @date 2015-10-24 下午16:36:21
     * @param serialSignResultEntity
     * @return
     */
	@Override
	public SerialSignResultEntity queryByConditions(SerialSignResultEntity serialSignResultEntity) {
		//是否有效
		serialSignResultEntity.setActive(FossConstants.YES);
		return (SerialSignResultEntity) this.getSqlSession().selectOne(NAMESPACE + "selectByConditions", serialSignResultEntity);
	}

	/**
     * 根据运单号作废该运单号的外发流水签收结果记录
     * @param waybillNo
     * @param modifyTime
     * @return
     */
	@Override
	public int updateBySerialSignResult(SerialSignResultEntity serialSignResultEntity){
		return this.getSqlSession().update(NAMESPACE+"updateByWaybillNo", serialSignResultEntity);
	}
	
}