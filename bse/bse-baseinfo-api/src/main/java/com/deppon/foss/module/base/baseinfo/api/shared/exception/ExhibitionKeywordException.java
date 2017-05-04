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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/exception/ExhibitionKeywordException.java
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
 * 展馆关键字信息业务异常处理类
 * @author:189284
 * @time:2014-12-27 上午9:16:24
 * @since
 * @version
 */
public class ExhibitionKeywordException extends BusinessException {

	private static final long serialVersionUID = -505901443023397246L;
	

    /**
     * 展馆关键字信息业务异常ERROR_KEY
     */
	/**
	 * 保存成功！
	 */
    public static final String EXHIBITIONKEYWORD_ADD_SUCCESS = "foss.bse.baseinfo.ExhibitionKeywordException.addSuccess";
    /**
	 * 保存失败！
	 */
    public static final String EXHIBITIONKEYWORD_ADD_FAILURE = "foss.bse.baseinfo.ExhibitionKeywordException.addFailure";
    /**
	 * 删除成功！
	 */
    public static final String EXHIBITIONKEYWORD_DEL_SUCCESS = "foss.bse.baseinfo.ExhibitionKeywordException.delSuccess";
    /**
	 * 删除失败！
	 */
    public static final String EXHIBITIONKEYWORD_DEL_FAILURE = "foss.bse.baseinfo.ExhibitionKeywordException.delFailure";
    /**
	 * 跟新成功！
	 */
    public static final String EXHIBITIONKEYWORD_UPD_SUCCESS = "foss.bse.baseinfo.ExhibitionKeywordException.updSuccess";
    /**
	 * 跟新失败！
	 */
    public static final String EXHIBITIONKEYWORD_UPD_FAILURE = "foss.bse.baseinfo.ExhibitionKeywordException.updFailure";
    /**
     * 参数错误
     */
    public static final String EXHIBITIONKEYWORD_PARAMS_ERROR = "foss.bse.baseinfo.ExhibitionKeywordException.paramsError";
    /**
     * 展馆关键字和详细地址必须填写一个！
     */
    public static final String EXHIBITION_KEYWORD_ADDRESS = "foss.bse.baseinfo.ExhibitionKeywordException.KeywordAndAddress";
    /**
     * 货物类型 为空
     */
    public static final String EXHIBITIONKEYWORD_TYPE_ERROR = "foss.bse.baseinfo.ExhibitionKeywordException.typeError";
    /**
     * 该展馆信息已经存在了
     */
    public static final String EXHIBITIONKEYWORD_DUPLICATE= "foss.bse.baseinfo.ExhibitionKeywordException.duplicate";
	public ExhibitionKeywordException(String errCode) {
		super();
		super.errCode = errCode;
	}

	public ExhibitionKeywordException(String code, String msg) {

		super(code, msg);
	}

}
