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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stock/api/shared/exception/StockException.java
 *  
 *  FILE NAME          :StockException.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.stock.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;
/**
 * 库存操作异常
 * @author 097457-foss-wangqiang
 * @date 2012-12-25 上午11:47:56
 */
public class StockException extends BusinessException {

	/**
	 * 导出文件失败
	 */
	public static final String EXPORT_FILE_ERROR_CODE = "error.export.file";
	
	/**
	 * 需要修改走货路径
	 */
	public static final String NEED_CHANGE_TRANSPORT_PATH_ERROR_CODE = "error.need.change.transport.path";
	
	/**
	 * 操作失败
	 */
	public static final String OPERATION_FAILURE_ERROR_CODE = "error.operation.failure";
	/** PC页面 入库时 货物已存在库存中*/
	public static final String GOODS_EXIST_STOCK_ERROR_CODE = "error.goods.exist.stock";
	/** PC页面 出库时 货物不在库存中*/
	public static final String GOODS_NOT_EXIST_STOCK_ERROR_CODE = "error.goods.not.exist.stock";
	/**
	* PDA出库时 货物不在库存中
	*/
	public static final String GOODS_NOT_EXIST_STOCK_ERROR_CODE_CN = "货物不在库存中";
	/** 查询走货路径失败*/
	public static final String QUERY_TRANSPORT_PATH_FAILURE_ERROR_CODE = "error.query.transport.path.failure";
	/** 根据下一部门获取货区失败*/
	public static final String QUERY_GOODS_AREA_BY_NEXT_ORG_ERROR_CODE = "error.query.goodsArea.by.next.org.failure";
	
	/** 根据部门获取货区失败*/
	public static final String QUERY_GOODS_AREA_BY_ORGCODE_FAILURE_ERROR_CODE = "error.query.goodsArea.by.orgcode.failure";
	
	/** 修改走货路径失败*/
	public static final String CHANGE_TRANSPORT_PATH_FAILURE_ERROR_CODE = "error.change.transport.path.failure";
	
	/** 查询修改后的走货路径失败*/
	public static final String QUERY_CHANGED_TRANSPORT_PATH_FAILURE_ERROR_CODE = "error.query.changed.transport.path.failure";
	
	/** 本部门不在修改后的走货路径中*/
	public static final String ORG_NOT_EXIST_CHANGED_TRANSPORT_PATH_ERROR_CODE = "error.org.not.exist.changed.transport.path";
	
	/**
	 * 部门不存在
	 */
	public static final String ORG_NOT_EXIST_ERROR_CODE = "error.org.not.exist";
	
	/**
	 * 运单不存在
	 */
	public static final String WAYBILL_NOT_EXIST_ERROR_CODE = "error.waybill.not.exist";
	
	/**
	 * 用户不存在
	 */
	public static final String USER_NOT_EXIST_ERROR_CODE = "error.user.not.exist";
	/** 货件没有在本部门偏线外发*/
	public static final String NO_PARTIALLINE_ERROR_CODE = "error.no.partialline";
	/** 入库部门错误，部门类型为驻地营业部时，应入库到所属外场库存*/
	public static final String STATION_SALES_ORG_ERROR_CODE = "error.station.sales.org";
	/** 该票没有在本部门做过自提*/
	public static final String NO_ARRIVE_SHEET_ERROR_CODE = "error.no.arrive.sheet";
	/** 该票没有在本部门做过派送*/
	public static final String NO_ARRANGE_SEND_ERROR_CODE = "error.no.arrange.send";
	/** 该票没有在本部门做过空运*/
	public static final String NO_AIR_WAYBILL_ERROR_CODE = "error.no.air.waybill";
	/** 货物不在本部门库存中*/
	public static final String GOODS_NOT_EXIST_LOCAL_STOCK_ERROR_CODE = "error.goods.not.exist.local.stock";
	/** 货物不在本部门库存中*/
	public static final String GOODS_NOT_EXIST_LOCAL_STOCK_ERROR_CODE_CN = "货物不在本部门库存中";
	/** 本部门卸车差异报告中没有该货物*/
	public static final String NO_UNLODA_DIFF = "error.no.unload.diff";
	/** 本部门卸车少货差异报告和清仓少货差异报告中都没有该货物*/
	public static final String NO_DIFF = "error.no.diff";
	/** 查询处理少货特殊组织编号失败*/
	public static final String QUERY_LOSE_GOODS_SPECIAL_ORG_FAILURE_ERROR_CODE = "error.query.lose.goods.special.org.failure";
	/** 货物在特殊货区，不可出库*/
	public static final String GOODS_IN_SPECIAL_AREA_ERROR_CODE = "error.goods.in.special.area.can.not.outstock";
	/** 货物已预配*/
	public static final String GOODS_ALREADY_PRE_HANDOVER_ERROR_CODE = "error.goods.already.pre.handover";
	/**查询当前外场对应的驻地派送部门失败*/
	public static final String QUERY_STATION_ORG_FAILURE_ERROR_CODE = "error.query.station.org.failure";
	/**出库失败*/
	public static final String OUT_STOCK_FAILURE_ERROR_CODE = "error.out.stock.failure";
	/**非驻地派送部*/
	public static final String NOT_STATION_ORG_ERROR_CODE = "error.not.station.org";
	/**移除货件失败*/
	public static final String INVALID_GOODS_FAILURE_ERROR_CODE = "error.invalid.goods.failure";
	
	private static final long serialVersionUID = -8256366413119557366L;
	/**获取外场部门失败*/
	public static final String QUERY_USER_TRANSFER_CENTER_ERROR_CODE = "error.query.transfer.center.org";
	/**
	 * 构造方法
	 * @param code 
	 * @param msg 
	 */
	public StockException(String code, String msg) {
		super(code,msg);
	}
	
	public StockException(String code,Object... args) {
		super(code,args);
	}

}