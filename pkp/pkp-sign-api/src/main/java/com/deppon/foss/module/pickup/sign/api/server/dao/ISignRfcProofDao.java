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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/server/dao/ISignRfcProofDao.java
 * 
 * FILE NAME        	: ISignRfcProofDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.sign.api.shared.domain.SignRfcProofEntity;

/**
 * 
 * <p>变更签收附件操作<br />
 * </p>
 * @title ISignRfcProofDao.java
 * @package com.deppon.foss.module.pickup.sign.api.server.dao 
 * @author ibm-lizhiguo
 * @version 0.1 2013-1-5
 */
public interface ISignRfcProofDao {

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
	int insertSelective(SignRfcProofEntity entity);

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
	SignRfcProofEntity selectById(String id);

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
	List<SignRfcProofEntity> selectBySignRfcId(String id);

}