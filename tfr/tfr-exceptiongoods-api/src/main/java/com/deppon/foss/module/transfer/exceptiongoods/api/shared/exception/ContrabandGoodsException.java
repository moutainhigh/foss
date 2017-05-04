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
 *  PROJECT NAME  : tfr-exceptiongoods-api
 *  
 * package_name  : 
 *  
 *  FILE PATH          :/ContrabandGoodsException.java
 * 
 *  FILE NAME          :ContrabandGoodsException.java
 *  
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.exceptiongoods.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;
/**
 * 违禁品操作相关异常
 * @author 097457-foss-wangqiang
 * @date 2012-12-25 下午12:09:02
 */
public class ContrabandGoodsException extends BusinessException{
	
	private static final long serialVersionUID = 7762422030403407679L;
	/**
	 * 获取异常货区编号失败
	 */
	public static final String QUERY_EXCEPTION_GOODSAREA_ERROR_CODE = "error.query.exception.goodsArea";
	/**
	 * 异常货区库存中没有该票货
	 */
	public static final String NOT_EXIST_EXCEPTION_GOODSAREA_STOCK_ERROR_CODE = "error.not.exist.exception.goodsArea.stock";
	/**
	 * 查询用户所属外场部门失败
	 */
	public static final String QUERY_USER_TRANSFER_CENTER_ERROR_CODE = "error.query.user.transfer.center.failure";
	
	
	/**
	 * 构造方法
	 * @param code 
	 * @param msg 
	 */
	public ContrabandGoodsException(String code, String msg) {
		super(code,msg);
	}
}