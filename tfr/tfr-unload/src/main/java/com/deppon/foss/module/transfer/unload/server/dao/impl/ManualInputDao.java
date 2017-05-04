package com.deppon.foss.module.transfer.unload.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.unload.api.server.dao.IManualInputDao;
import com.deppon.foss.module.transfer.unload.api.shared.domain.ManualInputForkVoteEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.ManualInputGoodsQtyEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ManualInputQcDto;

public class ManualInputDao extends iBatis3DaoImpl implements IManualInputDao {

	private static final String NAMESPACE = "com.deppon.foss.module.transfer.unload.api.server.dao.IManualInputDao.";

	@SuppressWarnings("unchecked")
	@Override
	public List<ManualInputForkVoteEntity> queryForkVoteEntities(
			ManualInputQcDto dto) {
		return super.getSqlSession().selectList(
				NAMESPACE + "queryForkVoteEntities", dto);
	}

	@Override
	public void insertForkVoteEntity(ManualInputForkVoteEntity entity) {
		super.getSqlSession()
				.insert(NAMESPACE + "insertForkVoteEntity", entity);
	}

	@Override
	public void updateForkVoteEntity(ManualInputForkVoteEntity entity) {
		super.getSqlSession()
				.update(NAMESPACE + "updateForkVoteEntity", entity);
	}

	@Override
	public void updateForkVoteById(ManualInputForkVoteEntity entity) {
		super.getSqlSession().update(NAMESPACE + "updateForkVoteById", entity);
	}

	@Override
	public String queryForkVoteId(ManualInputQcDto dto) {
		return (String) super.getSqlSession().selectOne(
				NAMESPACE + "queryForkVoteId", dto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ManualInputGoodsQtyEntity> queryGoodsQtyEntities(
			ManualInputQcDto dto) {
		return super.getSqlSession().selectList(
				NAMESPACE + "queryGoodsQtyEntities", dto);
	}

	@Override
	public void insertGoodsQtyEntity(ManualInputGoodsQtyEntity entity) {
		super.getSqlSession()
				.insert(NAMESPACE + "insertGoodsQtyEntity", entity);
	}

	@Override
	public void updateGoodsQtyEntity(ManualInputGoodsQtyEntity entity) {
		super.getSqlSession()
				.update(NAMESPACE + "updateGoodsQtyEntity", entity);
	}

	@Override
	public void updateGoodsQtyById(ManualInputGoodsQtyEntity entity) {
		super.getSqlSession().update(NAMESPACE + "updateGoodsQtyById", entity);
	}

	@Override
	public String queryGoodsQtyId(ManualInputQcDto dto) {
		return (String) super.getSqlSession().selectOne(
				NAMESPACE + "queryGoodsQtyId", dto);
	}

}
