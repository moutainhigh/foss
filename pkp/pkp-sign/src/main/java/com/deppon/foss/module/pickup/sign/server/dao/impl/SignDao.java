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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/dao/impl/SignDao.java
 * 
 * FILE NAME        	: SignDao.java
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
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignArriveSheetDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignDto;
import com.deppon.foss.module.pickup.sign.api.server.dao.ISignDao;
import com.deppon.foss.module.pickup.sign.api.shared.dto.StockDto;
/**
 * 签收出库Dao
 * @author foss-meiying
 * @date 2012-11-28 上午11:49:44
 * @since
 * @version
 */
public class SignDao extends iBatis3DaoImpl implements ISignDao {
	/**
	 * 命名空间
	 */
	private static final String NAMESPACE = "pkp.sign.";
	/**
	 * 查询签收出库流水号  通过运单编号,运单的最终配载部门=当前登录部门
	 * @author foss-meiying
	 * @date 2012-12-18 下午7:32:27
	 * @param dto (运单号,当前登录人的部门编码)
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.ISignDao#queryStock(com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StockDto> queryStock(SignDto dto) {
		return this.getSqlSession().selectList(NAMESPACE+"queryStockByWaybillNo",dto);
	}
	/**
	 * 根据运单号查询到达联信息（整车）
	 * @param dto
	 * @return
	 */
	public List<SignArriveSheetDto> queryArrivesheetListByWaybillWVH(SignDto dto){
		return this.getSqlSession().selectList(NAMESPACE+"queryArrivesheetListByWaybillWVH",dto);
	}
	 /**
	  * 根据运单号集合查询在库子母件信息
	  * @author 231438-chenjunying
	  * @param dto
	  * @return
	  */
	@SuppressWarnings("unchecked")
	@Override
	public List<StockDto> queryCzmInStock(SignDto dto) {
		return this.getSqlSession().selectList(NAMESPACE+"queryCzmInStockByWaybillNo",dto);
	}

}