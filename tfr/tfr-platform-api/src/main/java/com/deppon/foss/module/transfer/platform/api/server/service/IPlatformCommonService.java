package com.deppon.foss.module.transfer.platform.api.server.service;

import java.util.Map;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;

public interface IPlatformCommonService {
	/**
	 * @desc 查询部门所属经营本部
	 * @param orgCode
	 * @return
	 * @date 2015年4月3日 下午4:48:43
	 * @author Ouyang
	 */
	Map<String, String> findSupHq(String orgCode);

	/**
	 * @desc 查询部门所属外场
	 * @param orgCode
	 * @return
	 * @date 2015年4月7日 下午12:03:24
	 * @author Ouyang
	 */
	OrgAdministrativeInfoEntity querySupTfrCtr(String orgCode);

}
