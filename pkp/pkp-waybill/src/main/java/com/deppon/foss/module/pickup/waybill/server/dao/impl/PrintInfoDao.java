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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/dao/impl/PrintInfoDao.java
 * 
 * FILE NAME        	: PrintInfoDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IPrintInfoDao;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.PrintInfoEntity;
import com.deppon.foss.util.UUIDUtils;

/**
 * 
 * 运单打印信息数据持久层
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-12-5 下午4:56:25,content:TODO </p>
 * @author foss-sunrui
 * @date 2012-12-5 下午4:56:25
 * @since
 * @version
 */
public class PrintInfoDao extends iBatis3DaoImpl implements IPrintInfoDao {
	
	private static final String NAMESPACE = "foss.pkp.PrintInfoEntityMapper.";

	@Override
	public int insertSelective(PrintInfoEntity record) {
		// TODO Auto-generated method stub
		record.setId(UUIDUtils.getUUID());
		return this.getSqlSession().insert(NAMESPACE + "insertSelective", record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PrintInfoEntity> queryByWaybillId(String waybillId,String waybillNo) {
		// TODO Auto-generated method stub
		Map<String,String> map = new HashMap<String,String>();
		map.put("waybillId", waybillId);
		map.put("waybillNo", waybillNo);
		map.put("printType", WaybillConstants.PRINT_INFO_WAYBILL);
		return this.getSqlSession().selectList(NAMESPACE + "selectByWaybill", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PrintInfoEntity> queryByWaybillRFCId(String waybillRFCId,String waybillNo) {
		// TODO Auto-generated method stub
		Map<String,String> map = new HashMap<String,String>();
		map.put("waybillId", waybillRFCId);
		map.put("waybillNo", waybillNo);
		map.put("printType", WaybillConstants.PRINT_INFO_WAYBILLRFC);
		return this.getSqlSession().selectList(NAMESPACE + "selectByWaybill", map);
	}

	@Override
	public int updateByPrimaryKeySelective(PrintInfoEntity record) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update(NAMESPACE + "updateByPrimaryKeySelective", record);
	}

	@Override
	public int queryPrintTimesByWaybillId(String waybillId,String waybillNo) {
		// TODO Auto-generated method stub
		Map<String,String> map = new HashMap<String,String>();
		map.put("waybillId", waybillId);
		map.put("waybillNo", waybillNo);
//		map.put("printType", WaybillConstants.PRINT_INFO_WAYBILL);
		Integer retVal =  (Integer) this.getSqlSession().selectOne(NAMESPACE + "countPrintTimesByWaybill", map);
		if(retVal!=null&&retVal!=0){
			return retVal.intValue();
		}else{
			return 0;
		}
	}

	@Override
	public int queryPrintTimesByWaybillRFCId(String waybillRFCId,String waybillNo) {
		// TODO Auto-generated method stub
		Map<String,String> map = new HashMap<String,String>();
		map.put("waybillId", waybillRFCId);
		map.put("waybillNo", waybillNo);
		map.put("printType", WaybillConstants.PRINT_INFO_WAYBILLRFC);
		Integer retVal =  (Integer) this.getSqlSession().selectOne(NAMESPACE + "countPrintTimesByWaybill", map);
		if(retVal!=null&&retVal!=0){
			return retVal.intValue();
		}else{
			return 0;
		}
	}
	/**
	 * 更新运单打印次数
	 * 
	 * @author foss-zhuxue
	 * @date 2016-10-13 
	 * @param waybillNo
	 * @return
	 * @see
	 */
	@Override
	public int queryPrintTimesByWaybill(String waybillNo) {
		Integer retVal =  (Integer) this.getSqlSession().selectOne(NAMESPACE + "countPrintPrintJudgeWaybill", waybillNo);
		if(retVal!=null&&retVal!=0){
			return retVal;
		}else{
			return 0;
		}
	}
	/**
	 * 更新运单打印表中打印状态
	 * zhuxue
	 */
	@Override
	public int updatePrintJudgeSelective(PrintInfoEntity record) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update(NAMESPACE + "updatePrintSelective", record);
	}

}