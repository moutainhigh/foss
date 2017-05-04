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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/ui/addressfield/autocomplete/match/DataMatchHelper.java
 * 
 * FILE NAME        	: DataMatchHelper.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.common.client.ui.addressfield.autocomplete.match;

import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;


/**
 * 
 * 匹配助理类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:102246-foss-shaohongliang,date:2012-10-24 下午6:37:42, </p>
 * @author 102246-foss-shaohongliang
 * @date 2012-10-24 下午6:37:42
 * @since
 * @version
 */
public interface DataMatchHelper {

    /**
     * 判断指定的用于匹配文本是否被允许
     * @param text
     * @return
     */
     boolean isMatchTextAccept(String text);

    /**
     * 判断给定的值是否与文本值匹配
     * @param matchedText
     * @param data
     * @return
     */
     MatchedType isDataMatched(String matchText, AddressFieldDto data);
}