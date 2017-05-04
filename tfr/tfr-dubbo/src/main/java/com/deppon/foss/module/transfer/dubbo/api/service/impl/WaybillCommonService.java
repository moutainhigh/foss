package com.deppon.foss.module.transfer.dubbo.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.transfer.dubbo.api.service.IWaybillCommonService;

public class WaybillCommonService implements IWaybillCommonService {
	private static final String SPLIT_CHAR_DASH = "-";
	private IAdministrativeRegionsService administrativeRegionsService;
	
	@Override
	public String getCompleteAddress(String provCode, String cityCode, String distCode, String address) {
		StringBuffer sb = new StringBuffer();
		AdministrativeRegionsEntity entity;
		// 根据编码查询 -- 省
		if (StringUtil.isNotBlank(provCode)) {
			entity = getAdministrativeRegionsService().queryAdministrativeRegionsByCode(provCode);
			if (entity != null && StringUtil.isNotBlank(entity.getName())) {
				sb.append(entity.getName());
				sb.append(SPLIT_CHAR_DASH);
			}
		}
		// 根据编码查询 -- 市
		if (StringUtil.isNotBlank(cityCode)) {
			entity = getAdministrativeRegionsService().queryAdministrativeRegionsByCode(cityCode);
			if (entity != null && StringUtil.isNotBlank(entity.getName())) {
				sb.append(entity.getName());
				sb.append(SPLIT_CHAR_DASH);
			}
		}
		// 根据编码查询 -- 区
		if (StringUtil.isNotBlank(distCode)) {
			entity = getAdministrativeRegionsService().queryAdministrativeRegionsByCode(distCode);
			if (entity != null && StringUtil.isNotBlank(entity.getName())) {
				sb.append(entity.getName());
				sb.append(SPLIT_CHAR_DASH);
			}
		}

		if (StringUtil.isNotBlank(sb.toString()) && sb.toString().length() > 0) {
			return StringUtil.isNotBlank(address) ? sb.append(address).toString() : sb.substring(0, sb.length() - 1);
		} else {
			return StringUtil.isNotBlank(address) ? address : "";
		}
	}

	public IAdministrativeRegionsService getAdministrativeRegionsService() {
		return administrativeRegionsService;
	}

	@Autowired
	public void setAdministrativeRegionsService(IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}
}
