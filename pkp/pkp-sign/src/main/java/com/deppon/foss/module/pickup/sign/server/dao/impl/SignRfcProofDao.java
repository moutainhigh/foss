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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/dao/impl/SignRfcProofDao.java
 * 
 * FILE NAME        	: SignRfcProofDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.sign.api.server.dao.ISignRfcProofDao;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignRfcProofEntity;
import com.deppon.foss.util.UUIDUtils;


/**
 * 
 * <p>变更签收附件操作Dao<br />
 * </p>
 * @title SignRfcProofDao.java
 * @package com.deppon.foss.module.pickup.sign.server.dao.impl 
 * @author ibm-lizhiguo
 * @version 0.1 2013-1-5
 */
public class SignRfcProofDao extends iBatis3DaoImpl implements ISignRfcProofDao{
	//变更签收附件NAMESPACE
	private static final String NAMESPACE = "com.deppon.foss.module.pickup.sign.api.shared.domain.SignRfcProofEntity.";
	//附件保存
	private static final String INSERTSELECTIVE = "insertSelective";
	//查看附件
	private static final String SELECTBYID = "selectById";
	//获得该变更签收的所以附件
	private static final String SELECTBYSIGNRFCID = "selectBySignRfcId";
	
	/**
	 * 
	 * <p>保存附件<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-1-5
	 * @param entity
	 * @return
	 * int
	 */
	@Override
	public int insertSelective(SignRfcProofEntity entity) {
		entity.setId(UUIDUtils.getUUID());
		return getSqlSession().insert(
				NAMESPACE + INSERTSELECTIVE, entity);
	}
	/**
	 * 
	 * <p>查看附件<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-1-5
	 * @param id
	 * @return
	 * SignRfcProofEntity
	 */
	@Override
	public SignRfcProofEntity selectById(String id){
		return (SignRfcProofEntity)getSqlSession().selectOne(NAMESPACE+SELECTBYID, id);
	}
	/**
	 * 
	 * <p>获得变更签收对应的所以附件<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-1-5
	 * @param id
	 * @return
	 * List<SignRfcProofEntity>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SignRfcProofEntity> selectBySignRfcId(String id){
		return getSqlSession().selectList(NAMESPACE+SELECTBYSIGNRFCID, id);
	}
}