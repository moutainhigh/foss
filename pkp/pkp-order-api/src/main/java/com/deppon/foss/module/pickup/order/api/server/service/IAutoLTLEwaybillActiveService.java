package com.deppon.foss.module.pickup.order.api.server.service;

import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillProcessEntity;

/**
 * 零担电子面单激活线程Service
 * 
 * @author 325220-foss-liuhui
 * @date 2016-05-06
 *
 */
public interface IAutoLTLEwaybillActiveService {

	/**
	 * process
	 * 
	 * @param waybillProcessEntityList 运单激活处理记录List
	 */
	void process(List<WaybillProcessEntity> waybillProcessEntityList);
}
