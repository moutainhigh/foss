/**
 * Project Name:tfr-platform
 * File Name:TransferCenterLayoutService.java
 * Package Name:com.deppon.foss.module.transfer.platform.server.service.impl
 * Date:2014年12月30日上午9:09:52
 * Copyright (c) 2014, shiwei@outlook.com All Rights Reserved.
 *
*/

package com.deppon.foss.module.transfer.platform.server.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.platform.api.server.dao.ITransferCenterLayoutDao;
import com.deppon.foss.module.transfer.platform.api.server.service.ITransferCenterLayoutService;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TransferCenterUnitDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * ClassName:TransferCenterLayoutService <br/>
 * Reason:	 场内布局图service实现类. <br/>
 * Date:     2014年12月30日 上午9:09:52 <br/>
 * @author   045923
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class TransferCenterLayoutService implements
		ITransferCenterLayoutService {
	
	private ITransferCenterLayoutDao transferCenterLayoutDao;
		
	public void setTransferCenterLayoutDao(
			ITransferCenterLayoutDao transferCenterLayoutDao) {
		this.transferCenterLayoutDao = transferCenterLayoutDao;
	}

	/**
	 * 用于判断上级部门是否为外场
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	@Override
	public List<TransferCenterUnitDto> queryGoodsArea(String orgCode) {
		return transferCenterLayoutDao.queryGoodsArea(orgCode);
	}

	@Override
	public List<TransferCenterUnitDto> queryPlatform(String orgCode) {
		return transferCenterLayoutDao.queryPlatform(orgCode);
	}

	@Override
	public List<TransferCenterUnitDto> queryTransferArea(String orgCode) {
		return transferCenterLayoutDao.queryTransferArea(orgCode);
	}

	@Override
	public String beTransferCenter() {
		String orgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
		//设置查询参数
		List<String> bizTypesList = new ArrayList<String>();
		//外场类型
		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		//查询上级部门
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.
				queryOrgAdministrativeInfoIncludeSelfByCode(orgCode, bizTypesList);
		if(orgAdministrativeInfoEntity != null){
			//返回部门编码
			return orgAdministrativeInfoEntity.getCode();
		}
		return FossConstants.NO;
	}

}

