/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-airfreight
 *  
 *  package_name  : 
 *  
 *  FILE PATH          :/PrintAirWaybillTagDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.airfreight.api.server.dao.IPrintAirWaybillTagDao;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirWayBillDto;

/**
 * 打印正单标签相关
 * @author foss-liuxue(for IBM)
 * @date 2012-11-28 下午3:08:59
 */
@SuppressWarnings("unchecked")
public class PrintAirWaybillTagDao extends iBatis3DaoImpl implements IPrintAirWaybillTagDao {

	private static final String nameSpace = "foss.airfreight.";
	
	/**
	 * 根据正单号获取相应信息
	 * @author foss-liuxue(for IBM)
	 * @date 2012-11-26 下午8:15:26
	 */
	public List<AirWaybillEntity> queryAirWaybillInfo(AirWayBillDto airWayBillDto){
		//根据正单号获取相应信息
		return this.getSqlSession().selectList(nameSpace + "queryAirWayBillList",airWayBillDto);
	}
	
}