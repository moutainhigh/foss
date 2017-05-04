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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/commonselector/CommonTransferCenterService.java
 * 
 * FILE NAME        	: CommonTransferCenterService.java
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

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonTransferCenterDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IUserDeptDataService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonTransferCenterService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.TransferCenterEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.TransferCenterDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 公共选择器--查询外场Dao
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2013-1-12 上午10:17:25
 */
public class CommonTransferCenterService implements ICommonTransferCenterService {

	/**
	 * 外场Service
	 */
	private ICommonTransferCenterDao commonTransferCenterDao;
	
	/**
	 * 数据权限Service
	 */
	private IUserDeptDataService userDeptDataService;
	
	
	/**
	 * 
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	
	/**
	 * 根据条件查询外场信息
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @param
	 * @date 2013-1-30 下午4:17:51
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonTransferCenterDao#queryTransferCenterByCondition(com.deppon.foss.module.base.baseinfo.api.shared.dto.TransferCenterDto,
	 *      int, int)
	 */
	@Override
	public List<TransferCenterEntity> queryTransferCenterByCondition(
			TransferCenterDto dto, int limit, int start) {
		if(StringUtils.equals(dto.getFlag(), FossConstants.ACTIVE)&&
				!StringUtils.isEmpty(dto.getCurrentOrgCode())){
			List<String> bizTypes = new ArrayList<String> ();
			bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
			OrgAdministrativeInfoEntity outfield = orgAdministrativeInfoComplexService.
					queryOrgAdministrativeInfoIncludeSelfByCode(dto.getCurrentOrgCode(), bizTypes);
			if(null!=outfield){
				List<String> orgCodeList  =new ArrayList<String>();
				orgCodeList.add(outfield.getCode());
				dto.setOrgCodes(orgCodeList);
			}
			return commonTransferCenterDao.queryTransferCenterByCondition(dto,limit, start);
		}else{
			return commonTransferCenterDao.queryTransferCenterByCondition(setFlagCondition(dto),
					limit, start);
		}
	}

	/**
	 * 根据条件查询外场信息总条数
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @param
	 * @date 2013-1-30 下午4:18:08
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonTransferCenterDao#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.dto.TransferCenterDto)
	 */
	@Override
	public Long queryRecordCount(TransferCenterDto dto) {
		if(StringUtils.equals(dto.getFlag(), FossConstants.ACTIVE)&&
				!StringUtils.isEmpty(dto.getCurrentOrgCode())){
			List<String> bizTypes = new ArrayList<String> ();
			bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
			OrgAdministrativeInfoEntity outfield = orgAdministrativeInfoComplexService.
					queryOrgAdministrativeInfoIncludeSelfByCode(dto.getCurrentOrgCode(), bizTypes);
			if(null!=outfield){
				List<String> orgCodeList  =new ArrayList<String>();
				orgCodeList.add(outfield.getCode());
				dto.setOrgCodes(orgCodeList);
			}
			return commonTransferCenterDao.queryRecordCount(dto);
 		}else{
			return commonTransferCenterDao.queryRecordCount(setFlagCondition(dto));
		}
		
	}

	/**
	 * 标识是否有判断条件
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2013-1-30 下午7:04:10
	 * @return 
	 */
	private TransferCenterDto setFlagCondition(TransferCenterDto dto) {
		TransferCenterDto condition = dto;
		if (StringUtils.isNotBlank(condition.getVehicleAssemble())
				|| StringUtils.isNotBlank(condition.getOutAssemble())
				|| StringUtils.isNotBlank(condition.getPackingWood())
				|| StringUtils.isNotBlank(condition.getTransfer())
				|| StringUtils.isNotBlank(condition.getSortingMachine())) {
			condition.setIsFlag(FossConstants.YES);
		} else {
			condition.setIsFlag(null);
		}
		condition.setOrgCodes(allowQueryOrgCodes(dto));
		return condition;
	}
	
	/**
	 * 根据当前部门和编码获取用户的数据权限
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2013-1-31 上午10:00:56
	 * @return 
	 */
	private List<String> allowQueryOrgCodes(TransferCenterDto dto){
		if(StringUtils.isNotBlank(dto.getUserCode()) && StringUtils.isNotBlank(dto.getCurrentOrgCode())){
			return userDeptDataService.queryUserDeptDataInTransferCenter(dto.getUserCode(), dto.getCurrentOrgCode());
		}
		return null;
	}
	
	public ICommonTransferCenterDao getCommonTransferCenterDao() {
		return commonTransferCenterDao;
	}
	
	public void setCommonTransferCenterDao(
			ICommonTransferCenterDao commonTransferCenterDao) {
		this.commonTransferCenterDao = commonTransferCenterDao;
	}

	public void setUserDeptDataService(IUserDeptDataService userDeptDataService) {
		this.userDeptDataService = userDeptDataService;
	}
	
 
 
}
