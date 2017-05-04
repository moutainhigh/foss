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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/commonselector/CommonSaleDeptAndOuterBranchService.java
 * 
 * FILE NAME        	: CommonSaleDeptAndOuterBranchService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonSaleDeptAndOuterBranchDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonSaleDeptAndOuterBranchService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonSaleDeptAndOuterBranchDto;
import com.deppon.foss.module.base.baseinfo.server.service.impl.complex.OrgAdministrativeInfoComplexService;

/**
 * 公共查询选择器--营业部和偏线代理网点信息查询 Service
 * 
 * @author 078823-foss-panGuangJun
 * @date 2012-12-5 下午3:12:48
 */
public class CommonSaleDeptAndOuterBranchService implements
		ICommonSaleDeptAndOuterBranchService {

	/**
	 * 公共查询选择器--营业部和偏线代理网点信息查询 Dao
	 */
	private ICommonSaleDeptAndOuterBranchDao commonSaleDeptAndOuterBranchDao;
	private OrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	/**
	 * 查询营业部和偏线代理网点信息
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @param
	 * @date 2013-1-28 上午11:23:12
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonSaleDepartmentService#searchSaleDeptAndOuterBranchByCondition(com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonSaleDeptAndOuterBranchDto,
	 *      int, int)
	 */
	@Override
	public List<CommonSaleDeptAndOuterBranchDto> searchSaleDeptAndOuterBranchByCondition(
			CommonSaleDeptAndOuterBranchDto dto, int start, int limit) {
		if (StringUtils.isNotBlank(dto.getSaleDeptCode())) {
			List<OrgAdministrativeInfoEntity> orgs = orgAdministrativeInfoComplexService
					.queryOrgAdministrativeInfoEntitySalesByBig(dto
							.getSaleDeptCode());
			if (CollectionUtils.isNotEmpty(orgs)) {
				List<String> codes = new ArrayList<String>();
				for (OrgAdministrativeInfoEntity org : orgs) {
					codes.add(org.getCode());
				}
				dto.setCodes(codes);
			}
		}
		return commonSaleDeptAndOuterBranchDao
				.searchSaleDeptAndOuterBranchByCondition(dto, start, limit);
	}

	/**
	 * 查询总条数
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @param
	 * @date 2013-1-28 上午11:23:14
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonSaleDepartmentService#countReocrd(com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonSaleDeptAndOuterBranchDto)
	 */
	@Override
	public long countReocrd(CommonSaleDeptAndOuterBranchDto dto) {
		if (StringUtils.isNotBlank(dto.getSaleDeptCode())) {
			List<OrgAdministrativeInfoEntity> orgs = orgAdministrativeInfoComplexService
					.queryOrgAdministrativeInfoEntitySalesByBig(dto
							.getSaleDeptCode());
			if (CollectionUtils.isNotEmpty(orgs)) {
				List<String> codes = new ArrayList<String>();
				for (OrgAdministrativeInfoEntity org : orgs) {
					codes.add(org.getCode());
				}
				dto.setCodes(codes);
			}
		}
		return commonSaleDeptAndOuterBranchDao.countReocrd(dto);
	}

	public OrgAdministrativeInfoComplexService getOrgAdministrativeInfoComplexService() {
		return orgAdministrativeInfoComplexService;
	}

	public void setOrgAdministrativeInfoComplexService(
			OrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public ICommonSaleDeptAndOuterBranchDao getCommonSaleDeptAndOuterBranchDao() {
		return commonSaleDeptAndOuterBranchDao;
	}

	public void setCommonSaleDeptAndOuterBranchDao(
			ICommonSaleDeptAndOuterBranchDao commonSaleDeptAndOuterBranchDao) {
		this.commonSaleDeptAndOuterBranchDao = commonSaleDeptAndOuterBranchDao;
	}
}
