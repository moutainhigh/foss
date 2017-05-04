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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/service/impl/SignDetailService.java
 * 
 * FILE NAME        	: SignDetailService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.server.service.impl;
import java.util.Date;

import com.deppon.foss.module.pickup.sign.api.server.dao.ISerialSignResultDao;
import com.deppon.foss.module.pickup.sign.api.server.service.ISerialSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SerialSignResultEntity;
import com.deppon.foss.util.define.FossConstants;
/**
 * 
	外发流水签收
 * @author foss-sunyanfei
 * @date 2015-10-24 下午3:48:43
 * @since
 * @version
 */
public class SerialSignResultService implements ISerialSignResultService {

	/**
	 * 外发流水签收dao
	 */
	private ISerialSignResultDao serialSignResultDao;
	
	public void setSerialSignResultDao(ISerialSignResultDao serialSignResultDao) {
		this.serialSignResultDao = serialSignResultDao;
	}

	/**
     * 根据运单号、流水号查询外发流水签收
     * @author foss-sunyanfei
     * @date 2015-10-24 下午4:36:53
     * @param serialSignResultEntity
     * @return
     */
	@Override
	public SerialSignResultEntity queryByConditions(SerialSignResultEntity serialSignResultEntity) {
		return serialSignResultDao.queryByConditions(serialSignResultEntity);
	}
	
	/**
     * 单条添加外发流水签收信息
     * @author foss-sunyanfei
     * @date 2015-10-24 上午9:36:15
     * @param serialSignResultEntity
     * @return
     * @see
     */
	@Override
	public int addSerialSignResultInfo(SerialSignResultEntity serialSignResultEntity) {
		return serialSignResultDao.insertSerialSignResult(serialSignResultEntity);
	}

	/**
	 * 通过运单号作废当前运单的流水签收结果
	 * @author foss-sunyanfei
	 * @date 2015-10-31 下午15:44:12
	 * @param waybillNo
	 * @param modifyTime
	 * @return
	 */
	@Override
	public int invalidWaybillSignResult(String waybillNo,Date modifyTime) {
		//作废当前运单外发流水签收结果为无效
		SerialSignResultEntity serialSignResultEntity = new SerialSignResultEntity();
		//运单号
		serialSignResultEntity.setWaybillNo(waybillNo);
		// 时效时间为当前时间
		serialSignResultEntity.setModifyTime(modifyTime);
		//无效
		serialSignResultEntity.setActive(FossConstants.INACTIVE);
		return serialSignResultDao.updateBySerialSignResult(serialSignResultEntity);
	}

}