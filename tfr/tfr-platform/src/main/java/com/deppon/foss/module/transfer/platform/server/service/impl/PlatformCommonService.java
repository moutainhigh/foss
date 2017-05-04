package com.deppon.foss.module.transfer.platform.server.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.transfer.platform.api.server.dao.IPlatformCommonDao;
import com.deppon.foss.module.transfer.platform.api.server.service.IPlatformCommonService;

public class PlatformCommonService implements IPlatformCommonService {

	private IPlatformCommonDao platformCommonDao;

	public void setPlatformCommonDao(IPlatformCommonDao platformCommonDao) {
		this.platformCommonDao = platformCommonDao;
	}

	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
	 * @desc 查询部门所属经营本部
	 * @param orgCode
	 * @return map.get("HQ_CODE")经营本部编码,map.get("HQ_NAME")经营本部名称
	 * @date 2015年4月3日 下午4:48:43
	 * @author Ouyang
	 */
	@Override
	public Map<String, String> findSupHq(String orgCode) {
		Map<String, String> result = platformCommonDao.findSupHq(orgCode);
		if (result == null) {
			result = new HashMap<String, String>();
		}
		return result;
	}

	/**
	 * @desc 查询部门所属外场
	 * @param orgCode
	 * @return
	 * @date 2015年4月7日 下午12:02:56
	 * @author Ouyang
	 */
	@Override
	public OrgAdministrativeInfoEntity querySupTfrCtr(String orgCode) {
		if (StringUtils.isEmpty(orgCode)) {
			return new OrgAdministrativeInfoEntity();
		}

		List<String> bizTypesList = new ArrayList<String>();
		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);

		// 调用综合接口，查询部门所属外场
		OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoComplexService
				.queryOrgAdministrativeInfoIncludeSelfByCode(orgCode,
						bizTypesList);

		return orgEntity;
	}
}
