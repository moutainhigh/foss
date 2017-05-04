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
 *  PROJECT NAME  : tfr-stock
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stock/server/dao/impl/StockDao.java
 *  
 *  FILE NAME          :StockDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.stock.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.stock.api.server.dao.IQmsYchDao;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InnerPickupCurrDeptEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.IsLoseGroupEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.QmsYchEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.WaybillArridept;




@SuppressWarnings("unchecked")
public class QmsYchDao extends iBatis3DaoImpl implements IQmsYchDao{
	
	private static final String NAMESPACE = "qms.ych.";
	
	/**
	* @description FOSS系统库存中开单=90天的运单信息
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IQmsYchDao#queryBillTimeBigNinetyDay()
	* @author 218381-foss-lijie
	* @update 2015年6月1日 上午9:48:45
	* @version V1.0
	*/
	@Override
	public List<QmsYchEntity> queryBillTimeBigNinetyDay(Date beginDate,Date endDate) {
		Map param = new HashMap();
		param.put("beginDate", beginDate);
		param.put("endDate", endDate);
		return this.getSqlSession().selectList(NAMESPACE + "queryBillTimeBigNinetyDay",param);
	}

	
	/**
	* @description 查询是否在零担丢货小组或者快递丢货小组
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IQmsYchDao#isLoseGroup(java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年6月1日 上午11:24:45
	* @version V1.0
	*/
	@Override
	public IsLoseGroupEntity isLoseGroup(String waybillNo) { 
		
		return (IsLoseGroupEntity)this.getSqlSession().selectOne(NAMESPACE + "isLoseGroup",waybillNo);
	}


	


	
	/**
	* @description 内部带货同步所处部门
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IQmsYchDao#innerPickupCurrDept(java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年6月1日 下午3:49:45
	* @version V1.0
	*/
	@Override
	public List<InnerPickupCurrDeptEntity> innerPickupCurrDept() {
		return this.getSqlSession().selectList(NAMESPACE + "innerPickupCurrDept");
	}
	/**
	* @description 根据部门code判断部门是否是驻地营业部
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IQmsYchDao#isStation(java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年7月17日 下午2:22:00
	* @version V1.0
	*/
	@Override
	public String isStation(String orgCode) {
		return (String)this.getSqlSession().selectOne(NAMESPACE+"isStation",orgCode);
	}


	
	/**
	* @description 根据驻地营业部code查询对应外场code
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IQmsYchDao#selectTransferByOrgCode(java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年7月17日 下午2:22:20
	* @version V1.0
	*/
	@Override
	public String selectTransferByOrgCode(String orgCode) {
		return (String)this.getSqlSession().selectOne(NAMESPACE+"selectTransferByOrgCode",orgCode);
	}


	
	/**
	* @description 异常货同步未处理的单号到达部门
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IQmsYchDao#queryWaybillArridept(java.util.List)
	* @author 218381-foss-lijie
	* @update 2015年11月9日 上午9:46:25
	* @version V1.0
	*/
	@Override
	public List<WaybillArridept> queryWaybillArridept(List<String> list) {
		return this.getSqlSession().selectList(NAMESPACE + "queryWaybillArridept",list);
	}
}