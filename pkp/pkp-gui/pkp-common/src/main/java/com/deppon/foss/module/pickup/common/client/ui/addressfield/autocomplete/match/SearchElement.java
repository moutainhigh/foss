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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/ui/addressfield/autocomplete/match/SearchElement.java
 * 
 * FILE NAME        	: SearchElement.java
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
 * 含拼音的搜索字段
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:102246-foss-shaohongliang,date:2012-10-24 下午6:36:32, </p>
 * @author 102246-foss-shaohongliang
 * @date 2012-10-24 下午6:36:32
 * @since
 * @version
 */
public class SearchElement {
	
	/**
	 * 地址栏DTO对象
	 */
	private AddressFieldDto value;

	/**
	 * 匹配类型
	 */
	private MatchedType matchedType;
	
	/**
	 * 匹配文本
	 */
	private String matchText;

	/**
	 * 
	 * 构造实例
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午8:13:08
	 */
	public SearchElement(String matchText, AddressFieldDto value, MatchedType matchedType) {
		this.value = value;
		this.matchedType = matchedType;
		this.matchText = matchText;
	}

	/**
	 * 
	 * 构造实例
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午8:13:08
	 */
	public SearchElement(String matchText, AddressFieldDto value) {
		this.value = value;
		this.matchText = matchText;
	}
	
	
	@Override
	public String toString() {
		if(value == null){
			return super.toString();
		}else{
			//根据不同的匹配类型高亮显示匹配文本
			if(matchedType==MatchedType.FullMatch){
				return "<html>" + value.toString() + " (<span style='color:red'>" + value.getPinyin().substring(0, matchText.length()) + "</span>" + value.getPinyin().substring(matchText.length()) + ")</html>";
			}else if(matchedType==MatchedType.ShortMatch){
				return "<html>" + value.toString() + " (<span style='color:red'>" + value.getShortPinyin().substring(0, matchText.length()) + "</span>" + value.getShortPinyin().substring(matchText.length()) + ")</html>";
			}else{
				return value.toString();
			}
		}
	}

	
	/**
	 * @return the value
	 */
	public AddressFieldDto getValue() {
		return value;
	}


}