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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/dao/impl/ReturnBillProcessDao.java
 * 
 * FILE NAME        	: ReturnBillProcessDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IReturnBillProcessDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.ReturnBillProcessEntity;
import com.deppon.foss.util.UUIDUtils;

/**
 * 
 * 签收单返单
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-11-7 上午11:41:34,content:TODO </p>
 * @author foss-sunrui
 * @date 2012-11-7 上午11:41:34
 * @since
 * @version
 */
public class ReturnBillProcessDao extends iBatis3DaoImpl implements IReturnBillProcessDao {
    
    private static final String NAMESPACE = "foss.pkp.ReturnBillProcessEntityMapper.";

    @Override
    public int insertSelective(ReturnBillProcessEntity record) {
	// TODO Auto-generated method stub
	record.setId(UUIDUtils.getUUID());
	return this.getSqlSession().insert(NAMESPACE + "insertSelective", record);
    }

    @Override
    public ReturnBillProcessEntity queryByPrimaryKey(String id) {
	// TODO Auto-generated method stub
	return (ReturnBillProcessEntity) this.getSqlSession().selectOne(NAMESPACE + "selectByPrimaryKey", id);
    }

    @Override
    public int updateByPrimaryKeySelective(ReturnBillProcessEntity record) {
	// TODO Auto-generated method stub
	return this.getSqlSession().update(NAMESPACE + "updateByPrimaryKeySelective", record);
    }

	@Override
	public int deleteByWaybillNO(String waybillNO) {
		// TODO Auto-generated method stub
		 	return this.getSqlSession().delete(NAMESPACE + "deleteByWaybillNO", waybillNO);
	}
    
    

}