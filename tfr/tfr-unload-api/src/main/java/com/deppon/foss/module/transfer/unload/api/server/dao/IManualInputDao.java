package com.deppon.foss.module.transfer.unload.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.unload.api.shared.domain.ManualInputForkVoteEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.ManualInputGoodsQtyEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ManualInputQcDto;

public interface IManualInputDao {

	List<ManualInputForkVoteEntity> queryForkVoteEntities(ManualInputQcDto dto);

	void insertForkVoteEntity(ManualInputForkVoteEntity entity);

	void updateForkVoteEntity(ManualInputForkVoteEntity entity);
	
	void updateForkVoteById(ManualInputForkVoteEntity entity);

	String queryForkVoteId(ManualInputQcDto dto);

	List<ManualInputGoodsQtyEntity> queryGoodsQtyEntities(ManualInputQcDto dto);

	void insertGoodsQtyEntity(ManualInputGoodsQtyEntity entity);

	void updateGoodsQtyEntity(ManualInputGoodsQtyEntity entity);
	
	void updateGoodsQtyById(ManualInputGoodsQtyEntity entity);

	String queryGoodsQtyId(ManualInputQcDto dto);
}
