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
 *  PROJECT NAME  : tfr-common
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/server/utils/FTPUtil.java
 *  
 *  FILE NAME          :FTPUtil.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-common
 * PACKAGE NAME: com.deppon.foss.module.transfer.common.server.utils
 * FILE    NAME: UnifiedCodeConvertUtils.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.common.server.utils;

import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;

/**
 * @author 046130-foss-xuduowei
 *
 */
public class UnifiedCodeConvertUtils {
	/**
	 * 组织接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * 
	 * 将部门编码转为标杆编码
	 * 
	 * @author 046130-foss-xuduowei
	 * @date 2013-5-3下午1:55:31
	 */
	public String convertDeptCodeToUnifiedCode(String deptCode) {
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(deptCode);
		if(org == null){
			throw new TfrBusinessException("将部门编码转为标杆编码失败，部门信息为null");
		}else{
			return org.getUnifiedCode();
		}
		
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	
	
}
