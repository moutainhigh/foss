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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/dict/api/util/DictUtil.java
 * 
 * FILE NAME        	: DictUtil.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.dict.api.util;

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;

/**
 * 
 * 
*******************************************
* <b style="font-family:微软雅黑"><small>Description:业务字典工具类</small></b>   </br>
* <b style="font-family:微软雅黑"><small>HISTORY</small></b></br>
* <b style="font-family:微软雅黑"><small> ID      DATE    PERSON     REASON</small></b><br>
********************************************
* <div style="font-family:微软雅黑,font-size:70%"> 
* 1 2012-11-21 钟庭杰    新增
* </div>  
********************************************
 */
public final class DictUtil {
	
	private IDataDictionaryValueService dataDictionaryValueService;

    private static DictUtil util;

	public void setDataDictionaryValueService(
			IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}

	public void init(){
		util = this;
		util.dataDictionaryValueService = this.dataDictionaryValueService;
    }
	
	private static IDataDictionaryValueService getDataDictionaryValueService(){
		return util.dataDictionaryValueService;
	}

	/**
	 * 通过词条代码获得业务字典数据
	 * @param termsCode 词条代码
	 * @returns 业务字典数据
	 */
	public static DataDictionaryEntity getDataByTermsCode(String termsCode){
		IDataDictionaryValueService dataDictionaryValueService = DictUtil.getDataDictionaryValueService();
		if(dataDictionaryValueService!=null&&termsCode!=null){
			List<DataDictionaryValueEntity> list =  dataDictionaryValueService.queryDataDictionaryValueByTermsCode(termsCode);
			DataDictionaryEntity dataDictionaryEntity = new DataDictionaryEntity();
			dataDictionaryEntity.setTermsCode(termsCode);
			dataDictionaryEntity.setDataDictionaryValueEntityList(list);
			return dataDictionaryEntity;
		}
		return null; 			
	}
	/**
	 * 通过多个词条代码获得业务字典数据数组
	 * @param termsCodes 词条代码数组
	 * @returns 业务字典数据数组
	 */
	public static List<DataDictionaryEntity> getDataByTermsCodes(List<String> termsCodes){
		IDataDictionaryValueService dataDictionaryValueService = DictUtil.getDataDictionaryValueService();
		if(termsCodes==null){
			return null;
		}
		List<DataDictionaryEntity> dataDictionaryEntitys = new ArrayList<DataDictionaryEntity>();
		for(String termsCode:termsCodes){
			List<DataDictionaryValueEntity> list =  dataDictionaryValueService.queryDataDictionaryValueByTermsCode(termsCode);
			DataDictionaryEntity dataDictionaryEntity = new DataDictionaryEntity();
			dataDictionaryEntity.setTermsCode(termsCode);
			dataDictionaryEntity.setDataDictionaryValueEntityList(list);
			dataDictionaryEntitys.add(dataDictionaryEntity);
		}
		return dataDictionaryEntitys;
	}
	/**
	 *将业务字典的valueName（数据字典显示值）转换成描述valueCode（提交值）
	 * 使用方法rendererDictionary(valueName,’abc’);
	 * @param valueName  所要转换的值
	 * @param termsCode 词条代码
	 */
	public static String rendererDisplayToSubmit(String valueName, String termsCode) {
		DataDictionaryEntity data = DictUtil.getDataByTermsCode(termsCode);
		if (valueName!=null && data!=null) {
			for (DataDictionaryValueEntity value : data.getDataDictionaryValueEntityList()) {
				if (valueName.equals(value.getValueName())) {
				     return value.getValueCode();
				}
			}
		}
		return valueName;
	}
	/**
	 *将业务字典的valueCode（提交值）转换成描述valueName（数据字典显示值）
	 * 使用方法rendererDictionary(valueCode,’abc’);
	 * @param valueCode  所要转换的值
	 * @param termsCode 词条代码
	 */
	public static String rendererSubmitToDisplay(String valueCode, String termsCode) {
		DataDictionaryEntity data = DictUtil.getDataByTermsCode(termsCode);
		if (valueCode!=null && data!=null) {
			for (DataDictionaryValueEntity value : data.getDataDictionaryValueEntityList()) {
				if (valueCode.equals(value.getValueCode())) {
				     return value.getValueName();
				}
			}
		}
		return valueCode;
	}
}
