package com.deppon.foss.module.transfer.unload.api.server.service;

import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.unload.api.shared.domain.ManualInputForkVoteEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.ManualInputGoodsQtyEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ManualInputQcDto;

public interface IManualInputService extends IService {

	Map<String, String> queryParentTfrCtrCode(String code);
	
	List<ManualInputForkVoteEntity> queryForkVoteEntities(ManualInputQcDto dto);

	void insertForkVoteEntity(ManualInputForkVoteEntity entity);

	List<ManualInputGoodsQtyEntity> queryGoodsQtyEntities(ManualInputQcDto dto);

	void insertGoodsQtyEntity(ManualInputGoodsQtyEntity entity);
}
