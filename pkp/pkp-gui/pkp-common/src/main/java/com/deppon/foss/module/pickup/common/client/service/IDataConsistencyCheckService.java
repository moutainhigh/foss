package com.deppon.foss.module.pickup.common.client.service;

import java.util.List;

import com.deppon.foss.module.pickup.common.client.vo.DataConsistencyCheckVo;
import com.deppon.foss.module.pickup.waybill.shared.vo.DataConsistencyVo;

public interface IDataConsistencyCheckService {
	/**
	 * 本地下载实体查询
	 */
	public List<DataConsistencyCheckVo> querylocalTableDate();
	/**
	 * 服务器表查询
	 */
	public List<DataConsistencyVo> countServiceTableDate(List<DataConsistencyVo> selectedData,String userCode);
	/**
	 * 本地实体条目数查询
	 */
	public List<DataConsistencyCheckVo> countLocalTableCounts(List<DataConsistencyCheckVo> dataConsistencyCheck);
}
