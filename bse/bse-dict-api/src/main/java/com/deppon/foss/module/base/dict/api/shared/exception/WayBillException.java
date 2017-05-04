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
 * PROJECT NAME	: bse-dict-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/dict/api/shared/exception/WayBillException.java
 * 
 * FILE NAME        	: WayBillException.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.dict.api.shared.exception;
/**
 * Exception开发规范
 * 1.必须继承com.deppon.foss.framework.exception.BusinessException
 * 2.类名必须以Exception结尾
 * 3.必须生成serialVersionUID
 * 4.类中的ERROR_CODE必须声明为static final禁止使用enum枚举
 *   命名规则：模块名_具体功能描述_ERROR_CODE全部使用大写字母
 *   例如：LINEINFO_ID_NULL_ERROR_CODE
 *   类中ERROR_CODE的值必须使用国际化中的Key
 */
