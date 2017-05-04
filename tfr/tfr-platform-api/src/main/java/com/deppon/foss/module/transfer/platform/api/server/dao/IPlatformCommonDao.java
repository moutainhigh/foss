package com.deppon.foss.module.transfer.platform.api.server.dao;

import java.util.Map;

public interface IPlatformCommonDao {

	/**
	 * @desc 查询部门所属经营本部
	 * @param orgCode
	 * @return
	 * @date 2015年4月3日 下午4:48:43
	 * @author Ouyang
	 */
	Map<String,String> findSupHq(String orgCode);
}
