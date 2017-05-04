package com.deppon.foss.module.transfer.common.api.server.service;

import java.util.List;

import com.deppon.foss.module.transfer.common.api.shared.dto.SwiftNumberInfoDto;

public interface IQuerySwiftNumberInfoService {

	/**
	 * 查询流水号信息
	 * 
	 * @param wayBillNo
	 * @return
	 */
	public List<SwiftNumberInfoDto> querySwiftNumberInfo(String wayBillNo);
	
}
