package com.deppon.foss.module.transfer.partialline.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.partialline.api.shared.dto.DpjzSignInDetailDto;

public interface IAutoCheckDpjzSignInDao {
	//查询需处理的运单信息
	List<DpjzSignInDetailDto> queryDpjzSignInMsgWaybill(Map<String,Object> map);
}
