package com.deppon.foss.module.transfer.common.server.service.impl;

import java.util.List;

import com.deppon.foss.module.transfer.common.api.server.dao.IQuerySwiftNumberInfoDao;
import com.deppon.foss.module.transfer.common.api.server.service.IQuerySwiftNumberInfoService;
import com.deppon.foss.module.transfer.common.api.shared.dto.SwiftNumberInfoDto;

public class QuerySwiftNumberInfoService implements IQuerySwiftNumberInfoService {

	private IQuerySwiftNumberInfoDao querySwiftNumberInfoDao;

	public IQuerySwiftNumberInfoDao getQuerySwiftNumberInfoDao() {
		return querySwiftNumberInfoDao;
	}

	public void setQuerySwiftNumberInfoDao(
			IQuerySwiftNumberInfoDao querySwiftNumberInfoDao) {
		this.querySwiftNumberInfoDao = querySwiftNumberInfoDao;
	}

	/**
	 * 查询流水号信息
	 * 
	 * @author 316759
	 * @param wayBillNo
	 * @return
	 */
	@Override
	public List<SwiftNumberInfoDto> querySwiftNumberInfo(String wayBillNo) {
		return querySwiftNumberInfoDao.querySwiftNumberInfo(wayBillNo);
	}

}
