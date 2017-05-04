package com.deppon.foss.module.transfer.common.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.common.api.shared.dto.SwiftNumberInfoDto;

public interface IQuerySwiftNumberInfoDao {
	
	/**
	* @description 查询流水号信息
	* @param wayBillNo
	* @return
	* @author 316759-foss-wangruipeng
	* @date 2016-09-02 上午09:22:24
	*/
	public List<SwiftNumberInfoDto> querySwiftNumberInfo(String wayBillNo);
	
}
