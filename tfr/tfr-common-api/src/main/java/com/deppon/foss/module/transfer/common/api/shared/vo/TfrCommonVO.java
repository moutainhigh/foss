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
 *  PROJECT NAME  : tfr-common-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/api/shared/vo/TfrCommonVO.java
 *  
 *  FILE NAME          :TfrCommonVO.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.common.api.shared.vo;

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.transfer.common.api.shared.dto.BaseDataDictDto;

public class TfrCommonVO {
	/**数据字典编码*/
	private String termsCode;
	/**返回数据字典下拉框内容*/
	private List<BaseDataDictDto> baseDataDictDtoList = new ArrayList<BaseDataDictDto>();
	/**运单号*/
	private String waybillNo;
	/**serialNo*/
	private String serialNo;
	/**返回数据是否正确*/
	private boolean resultSuccessful;
	/**返回数据个数*/
	private Integer resultCount;
	/**返回的信息*/
	private String resultMessage;

	public String getTermsCode() {
		return termsCode;
	}

	public void setTermsCode(String termsCode) {
		this.termsCode = termsCode;
	}

	public List<BaseDataDictDto> getBaseDataDictDtoList() {
		return baseDataDictDtoList;
	}

	public void setBaseDataDictDtoList(List<BaseDataDictDto> baseDataDictDtoList) {
		this.baseDataDictDtoList = baseDataDictDtoList;
	}

	public boolean getResultSuccessful() {
		return resultSuccessful;
	}

	public void setResultSuccessful(boolean resultSuccessful) {
		this.resultSuccessful = resultSuccessful;
	}

	public Integer getResultCount() {
		return resultCount;
	}

	public void setResultCount(Integer resultCount) {
		this.resultCount = resultCount;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
}