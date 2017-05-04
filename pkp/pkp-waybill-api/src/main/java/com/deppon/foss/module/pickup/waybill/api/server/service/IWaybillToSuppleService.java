package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.io.InputStream;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillSupplementLogEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillToSuppleCondtionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillToSuppleResultDto;

public interface IWaybillToSuppleService extends IService {
	/**
	 * 添加待补录日志表实体数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-8-11 15:02:03
	 * @param waybillSupplementLogEntity
	 */
	void addWaybillToSuppleRecord(WaybillSupplementLogEntity waybillSupplementLogEntity);

	List<WaybillToSuppleResultDto> queryWaybillToSuppleAction(WaybillToSuppleCondtionDto waybillToSuppleCondtionDto, int start, int limit);

	Long queryWaybillToSuppleActionCount(WaybillToSuppleCondtionDto waybillToSuppleCondtionDto);

	InputStream exportWaybillSuppleAction(WaybillToSuppleCondtionDto waybillToSuppleCondtionDto);

}
