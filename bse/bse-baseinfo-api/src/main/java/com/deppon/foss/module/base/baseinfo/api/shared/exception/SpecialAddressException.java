/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/exception/SpecialAddressException.java
 * 
 * FILE NAME        	: SpecialAddressException.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;
/**
 * 
 * 特殊地址业务异常处理类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:何波,date:2013-2-20 下午2:22:38</p>
 * @author 何波
 * @date 2013-2-20 下午2:22:38
 * @since
 * @version
 */
public class SpecialAddressException extends BusinessException {

	private static final long serialVersionUID = -505901443023397246L;
	

    /**
     * "特殊地址"的业务异常ERROR_KEY
     */
    public static final String SPECIALADDRESS_ADD_SUCCESS = "foss.bse.baseinfo.specialAddressException.addSuccess";
    
    public static final String SPECIALADDRESS_ADD_FAILURE = "foss.bse.baseinfo.specialAddressException.addFailure";
    
    public static final String SPECIALADDRESS_DEL_SUCCESS = "foss.bse.baseinfo.specialAddressException.delSuccess";
    
    public static final String SPECIALADDRESS_DEL_FAILURE = "foss.bse.baseinfo.specialAddressException.delFailure";
    
    public static final String SPECIALADDRESS_UPD_SUCCESS = "foss.bse.baseinfo.specialAddressException.updSuccess";
    
    public static final String SPECIALADDRESS_UPD_FAILURE = "foss.bse.baseinfo.specialAddressException.updFailure";

	public SpecialAddressException(String errCode) {
		super();
		super.errCode = errCode;
	}

	public SpecialAddressException(String code, String msg) {

		super(code, msg);
	}

}
