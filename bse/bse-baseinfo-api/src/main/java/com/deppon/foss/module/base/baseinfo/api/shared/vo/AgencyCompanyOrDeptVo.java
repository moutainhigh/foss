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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/AgencyCompanyOrDeptVo.java
 * 
 * FILE NAME        	: AgencyCompanyOrDeptVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
/**
 * (【空运，偏线】代理【公司，网点】VO)
 * @author 073586-FOSS-LIXUEXING
 * @date 2012-11-27 19:40:42
 * @since
 * @version
 */
public class AgencyCompanyOrDeptVo {
	// 空运代理公司、偏线代理公司 信息 集合
	private List<BusinessPartnerEntity> businessPartnerEntityList;
	// 空运代理公司、偏线代理公司 信息
	private BusinessPartnerEntity businessPartnerEntity;

	// 空运代理网点、偏线代理网点 信息 集合
	private List<OuterBranchEntity> outerBranchEntityList;
	// 空运代理网点、偏线代理网点 信息
	private OuterBranchEntity outerBranchEntity;
	
	// 偏线代理公司信息 实体中对应virtualCode字段，字段间以逗号分隔[支持批量删除]
	private String codeStr;
	// 返回前台的INT类型属性
	private int returnInt;
	
	// 增值服务
	private String valueAddedServices;
	// 承运业务
	private String carrierBusiness;
	/**
	 * 下面是get,set方法
	 * 
	 * @author 073586-FOSS-LIXUEXING
	 * @date 2012-11-27  19:40:42
	 */

	public void setBusinessPartnerEntityList(
			List<BusinessPartnerEntity> businessPartnerEntityList) {
		this.businessPartnerEntityList = businessPartnerEntityList;
	}

	public List<BusinessPartnerEntity> getBusinessPartnerEntityList() {
		return businessPartnerEntityList;
	}

	public void setBusinessPartnerEntity(
			BusinessPartnerEntity businessPartnerEntity) {
		this.businessPartnerEntity = businessPartnerEntity;
	}

	public BusinessPartnerEntity getBusinessPartnerEntity() {
		return businessPartnerEntity;
	}

	public String getCodeStr() {
		return codeStr;
	}

	public void setCodeStr(String codeStr) {
		this.codeStr = codeStr;
	}

	public int getReturnInt() {
		return returnInt;
	}

	public void setReturnInt(int returnInt) {
		this.returnInt = returnInt;
	}

	public List<OuterBranchEntity> getOuterBranchEntityList() {
		return outerBranchEntityList;
	}

	public void setOuterBranchEntityList(
			List<OuterBranchEntity> outerBranchEntityList) {
		this.outerBranchEntityList = outerBranchEntityList;
	}

	public OuterBranchEntity getOuterBranchEntity() {
		return outerBranchEntity;
	}

	public void setOuterBranchEntity(OuterBranchEntity outerBranchEntity) {
		this.outerBranchEntity = outerBranchEntity;
	}

	public String getValueAddedServices() {
		return valueAddedServices;
	}

	public void setValueAddedServices(String valueAddedServices) {
		this.valueAddedServices = valueAddedServices;
	}

	public String getCarrierBusiness() {
		return carrierBusiness;
	}

	public void setCarrierBusiness(String carrierBusiness) {
		this.carrierBusiness = carrierBusiness;
	}

}
