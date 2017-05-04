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
 *  PROJECT NAME  : tfr-stock-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stock/api/shared/typehandler/InStockArrayTypeHandler.java
 *  
 *  FILE NAME          :InStockArrayTypeHandler.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.stock.api.shared.typehandler;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.jdbc.support.nativejdbc.CommonsDbcpNativeJdbcExtractor;

import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;

public class InStockArrayTypeHandler implements TypeHandler{
	private static final String IN_STOCK_OBJECT = "TFR.IN_STOCK_OBJECT";
	private static final String IN_STOCK_OBJECT_TABLE = "TFR.IN_STOCK_TABLE";
	
	@Override
	public void setParameter(PreparedStatement ps, int i, Object parameter,
			JdbcType jdbcType) throws SQLException {
		
		if(parameter == null){
			ps.setNull(i, Types.ARRAY);
		}else{
			CommonsDbcpNativeJdbcExtractor extractor = new CommonsDbcpNativeJdbcExtractor();
			Connection conn = extractor.getNativeConnectionFromStatement(ps);
			StructDescriptor structDescriptor  = new StructDescriptor(IN_STOCK_OBJECT, conn);
			@SuppressWarnings("unchecked")
			List<InOutStockEntity> inOutStockList = (List<InOutStockEntity>)parameter;
			
			STRUCT[] structs = new STRUCT[inOutStockList.size()]; 
			Object[] result = new Object[0];
			
			for(int j = 0; j < inOutStockList.size(); j++) {  
				result = new Object[StockConstants.SONAR_NUMBER_12];
				result[0] = UUIDUtils.getUUID();
				result[1] = inOutStockList.get(j).getWaybillNO();
				result[2] = inOutStockList.get(j).getSerialNO();
				result[StockConstants.SONAR_NUMBER_3] = inOutStockList.get(j).getOperatorCode();
				result[StockConstants.SONAR_NUMBER_4] = inOutStockList.get(j).getOperatorName();
				if(StringUtils.isBlank(inOutStockList.get(j).getGoodsAreaCode())){
					result[StockConstants.SONAR_NUMBER_5] = "N/A";
				}else{
					result[StockConstants.SONAR_NUMBER_5] = inOutStockList.get(j).getGoodsAreaCode();
				}
				result[StockConstants.SONAR_NUMBER_6] = inOutStockList.get(j).getDeviceType();
				result[StockConstants.SONAR_NUMBER_7] = inOutStockList.get(j).getOrgCode();
				result[StockConstants.SONAR_NUMBER_8] = inOutStockList.get(j).getNextOrgCode();
				result[StockConstants.SONAR_NUMBER_9] = DateUtils.convert(inOutStockList.get(j).getPlanStartTime(), "yyyy-MM-dd HH:mm:ss");
				result[StockConstants.SONAR_NUMBER_10] = inOutStockList.get(j).getInOutStockType();
				result[StockConstants.SONAR_NUMBER_11] = UUIDUtils.getUUID();
				structs[j] = new STRUCT(structDescriptor, conn, result);  
			}
							
		    ArrayDescriptor desc = ArrayDescriptor.createDescriptor(IN_STOCK_OBJECT_TABLE, conn);
		    ARRAY list = new ARRAY(desc, conn, structs);
		    ps.setArray(i, list);
		}
	}

	@Override
	public Object getResult(ResultSet rs, String columnName)
			throws SQLException {
		return null;
	}

	@Override
	public Object getResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		return null;
	}

}