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
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/commonselector/CommonPublicBankAccountService.java
 * 
 * FILE NAME        	: CommonPublicBankAccountService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonPublicBankAccountDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonPublicBankAccountService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PublicBankAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PublicBankAccountDto;

 
/**
 * 公共查询选择器--组织对公账号Service
 * @author 101911-foss-zhouChunlai
 * @date 2013-1-11 上午8:49:42
 */
public class CommonPublicBankAccountService implements ICommonPublicBankAccountService {
	
	/**
	 * 组织信息 Service接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * 公共选择器---组织公共账户Dao
	 */
	private ICommonPublicBankAccountDao commonPublicBankAccountDao;

	/**
     * 根据dto模糊查询 对公银行账号
     * @author 087584-foss-lijun
     * @date 2012-12-10 下午8:4:19
     */
    public List<PublicBankAccountEntity> queryPublicBankAccountByDto(PublicBankAccountDto dto,int start, int limit){
    	if(StringUtils.isNotBlank(dto.getDeptCode())){
    		OrgAdministrativeInfoEntity orgInfo=this.getOrgInfo(dto.getDeptCode());
    		if(null != orgInfo){
    			dto.setDeptCd(orgInfo.getUnifiedCode());
    		}
    	}
    	return commonPublicBankAccountDao.queryPublicBankAccountByDto(dto, start, limit);
    }
	
    /**
     * 返回的记录总数,用于分页 
     * @author 087584-foss-lijun
     * @date 2012-12-10 下午8:4:19
     */
    public long countQueryPublicBankAccountByDto(PublicBankAccountDto dto){
    	if(StringUtils.isNotBlank(dto.getDeptCode())){
    		OrgAdministrativeInfoEntity orgInfo=this.getOrgInfo(dto.getDeptCode());
    		if(null != orgInfo){
    			dto.setDeptCd(orgInfo.getUnifiedCode());
    		}
    	}
    	return commonPublicBankAccountDao.queryRecordCount(dto);
    } 
	
    /**
     * 根据部门编码获取部门标杆编码
     * @author 101911-foss-zhouChunlai
     * @param 
     * @date 2013-1-11 上午11:18:36
     * @return 
     */
    private OrgAdministrativeInfoEntity getOrgInfo(String deptCode){
		return orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(deptCode);
    }
    
	public void setCommonPublicBankAccountDao(ICommonPublicBankAccountDao commonPublicBankAccountDao) {
		this.commonPublicBankAccountDao = commonPublicBankAccountDao;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	

}
