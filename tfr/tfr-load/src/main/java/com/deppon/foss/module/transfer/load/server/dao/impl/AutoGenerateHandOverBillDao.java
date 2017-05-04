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
 *  PROJECT NAME  : tfr-load
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/server/dao/impl/AutoGenerateHandOverBillDao.java
 *  
 *  FILE NAME          :AutoGenerateHandOverBillDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-load
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.server.dao.impl
 * FILE    NAME: AutoGenerateHandOverBillDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.dao.IAutoGenerateHandOverBillDao;

/**
 * 自动生成交接单
 * @author dp-duyi
 * @date 2012-11-5 上午9:12:32
 */
public class AutoGenerateHandOverBillDao extends iBatis3DaoImpl implements IAutoGenerateHandOverBillDao{
	private static final String NAMESPACE = "tfr-load.";

	/** 
	 * 自动生成交接单
	 * @author dp-duyi
	 * @date 2012-11-5 上午9:23:51
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IAutoGenerateHandOverBillDao#createHandOverBill()
	 */
	@Override
	public void createHandOverBill(Date bizJobStartTime,Date bizJobEndTime,int threadCount, int threadNo) {

	this.generateHandOverBill(bizJobStartTime, bizJobEndTime, threadCount, threadNo, null);

	}

	/** 
	 * TODO（方法详细描述说明、方法参数的具体涵义）
	 * @author dp-duyi
	 * @date 2013-6-21 下午2:09:13
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IAutoGenerateHandOverBillDao#createHandOverBillByTaskNo(java.lang.String)
	 */
	@Override
	@SuppressWarnings("rawtypes")
	//276198-duhao-20151212-增加交接单号参数
	public String createHandOverBillByTaskNo(String taskNo) {
		Map paramsMap = this.generateHandOverBill(null, null, 1, 0, taskNo);
		if(paramsMap != null){
			if(StringUtils.isNotBlank((String)paramsMap.get("exceptionInfo"))){
				throw new TfrBusinessException((String)paramsMap.get("exceptionInfo"));
			}
			return(String)paramsMap.get("handOverBillNo");
		}else{
			return null;
		}
	}

	/** 
	 * 增加零担电子面单交接单号参数
	 * @author songjl
	 * @date 2016-5-24 15:00:02
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IAutoGenerateHandOverBillDao#createLTLHandOverBillByTaskNo(java.lang.String)
	 */
	@Override
	public String createLTLHandOverBillByTaskNo(String taskNo) {
		if(!StringUtils.isBlank(taskNo)){
			return "D"+taskNo.substring(1, taskNo.length());
		}else{
			throw new TfrBusinessException("生成交接单编号出错");
		}
	}

	/** 
	 * TODO（方法详细描述说明、方法参数的具体涵义）
	 * @author dp-duyi
	 * @date 2013-6-21 下午2:09:13
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IAutoGenerateHandOverBillDao#generateHandOverBill(java.util.Date, java.util.Date, int, int, java.lang.String)
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	//276198-duhao-20151212-增加交接单号参数
	public Map generateHandOverBill(Date bizJobStartTime, Date bizJobEndTime,
			int threadCount, int threadNo, String taskNo) {
		Map condition  = new HashMap();
		condition.put("bizJobStartTime", bizJobStartTime);
		condition.put("bizJobEndTime", bizJobEndTime);
		condition.put("threadCount", threadCount);
		condition.put("threadNo", threadNo);
		condition.put("taskNo", taskNo);
		this.getSqlSession().selectOne(NAMESPACE+"selectByProcCreateHandOverBill",condition);
		return condition;
	}

	/**
	 * 
	 * <p>生成快递接货交接单</p> 
	 * @author alfred
	 * @date 2015-2-5 下午3:17:09
	 * @param taskNo
	 * @param handOverNo
	 * @return 
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IAutoGenerateHandOverBillDao#autoCreatePackHandoverbill(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map autoCreatePackHandoverbill(String taskNo, String handOverNo) {
		Map condition  = new HashMap();
		condition.put("taskNo", taskNo);
		condition.put("handOverNo", handOverNo);
		this.getSqlSession().selectOne(NAMESPACE+"autoCreatePackHandoverbill",condition);
		return condition;
	}

	/**
	 * 
	 * <p>完成接货任务，生成交接单</p> 
	 * @author alfred
	 * @date 2015-3-22 下午4:50:45
	 * @param taskID
	 * @param orgCode
	 * @param vehicle
	 * @return 
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IAutoGenerateHandOverBillDao#autoCreatePKPHandoverbill(java.lang.String, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map autoCreatePKPHandoverbill(String taskID, String orgCode,
			String vehicleNo,String handoverNo,String operatorCode) {
		Map condition  = new HashMap();
		condition.put("taskID", taskID);
		condition.put("orgCode", orgCode);
		condition.put("vehicleNo", vehicleNo);
		condition.put("handoverNo", handoverNo);
		condition.put("operatorCode", operatorCode);
		this.getSqlSession().selectOne(NAMESPACE+"autoCreatePKPHandoverbill",condition);
		return condition;
	}

	/**
	 * 
	 * <p>生成二程接驳交接单</p> 
	 * @author alfred
	 * @date 2015-5-11 上午9:07:32
	 * @param taskNo
	 * @param handOverNo
	 * @return 
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IAutoGenerateHandOverBillDao#autoCreateConnectionHandover(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map autoCreateConnectionHandover(String taskNo, String handOverNo) {
		Map condition  = new HashMap();
		condition.put("taskNo", taskNo);
		condition.put("handOverNo", handOverNo);
		this.getSqlSession().selectOne(NAMESPACE+"autoCreateConnectionHandover",condition);
		return condition;
	}

}