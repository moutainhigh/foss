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
 *  FILE PATH          :/NoLabelGoodsException.java
 * 
 *  FILE NAME          :NoLabelGoodsException.java
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
 * 无标签多货异常
 * @author 097457-foss-wangqiang
 * @date 2012-12-25 下午12:09:22
 */
public class NoLabelGoodsException extends BusinessException{
	
	private static final long serialVersionUID = 3612802002783394701L;
	/**
	 * 上传文件失败
	 */
	public static final String UPLOAD_FILE_ERROR_CODE = "error.upload.file";
	
	/**
	 * 查询打印信息失败
	 */
	public static final String QUERY_PRINT_LABEL_INFO_ERROR_CODE = "error.query.print.label.info";
	/**
	 * 获取外场部门失败
	 */
	public static final String QUERY_TRANSFER_CENTER_ORG_ERROR_CODE = "error.query.transfer.center.org";
	/**
	 * 入库失败
	 */
	public static final String IN_STOCK_EXCEPTION_ERROR_CODE = "error.in.stock";
	/**
	 * 上报OA失败
	 */
	public static final String REPORT_OA_ERROR_CODE = "error.report.oa";
	
	/**
	 * 查询用户所属外场部门失败
	 */
	public static final String QUERY_USER_TRANSFER_CENTER_ERROR_CODE = "error.query.user.transfer.center.failure";
	
	/**
	 * 查询所属大区或事业部失败
	 */
	public static final String QUERY_BIG_REGION_ERROR_CODE = "error.query.big.region.failure";
	
	
	/**
	 * 构造方法
	 * @param code 
	 * @param msg 
	 */
	public NoLabelGoodsException(String code, String msg) {
		super(code,msg);
	}

}