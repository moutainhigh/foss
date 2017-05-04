package com.deppon.foss.module.transfer.platform.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.platform.api.server.dao.ITfrCtrPersonnelBudgetDao;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrCtrPersonnelActualEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrCtrPersonnelBudgetEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TfrCtrPersonnelBudgetDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TfrCtrPersonnelBudgetQcDto;

public class TfrCtrPersonnelBudgetDao extends iBatis3DaoImpl implements
		ITfrCtrPersonnelBudgetDao {

	private static final String NAMESPACE = "com.deppon.foss.module.transfer.platform.api.server.dao.ITfrCtrPersonnelBudgetDao.";

	@SuppressWarnings("unchecked")
	@Override
	public List<TfrCtrPersonnelBudgetDto> selectTfrCtrPersonnelBudgets(
			TfrCtrPersonnelBudgetQcDto tfrCtrPersonnelBudgetQcDto) {
		
		return super.getSqlSession().selectList(
				NAMESPACE + "selectTfrCtrPersonnelBudgets",
				tfrCtrPersonnelBudgetQcDto);
	}

	@Override
	public void insertBudget(TfrCtrPersonnelBudgetEntity entity) {
		super.getSqlSession().insert(NAMESPACE + "insertBudget", entity);
	}

	@Override
	public void insertActual(TfrCtrPersonnelActualEntity entity) {
		super.getSqlSession().insert(NAMESPACE + "insertActual", entity);
	}

	@Override
	public int updateBudgetEndEffectiveDate(TfrCtrPersonnelBudgetEntity entity) {
		return super.getSqlSession().update(
				NAMESPACE + "updateBudgetEndEffectiveDate", entity);
	}

	@Override
	public int updateActualEndEffectiveDate(TfrCtrPersonnelActualEntity entity) {
		return super.getSqlSession().update(
				NAMESPACE + "updateActualEndEffectiveDate", entity);
	}

	@Override
	public int updateBudget(TfrCtrPersonnelBudgetEntity entity) {
		return super.getSqlSession().update(NAMESPACE + "updateBudget",
				entity);
	}

	@Override
	public int updateActualPostNum(TfrCtrPersonnelActualEntity entity) {
		return super.getSqlSession().update(NAMESPACE + "updateActualPostNum",
				entity);
	}

	@Override
	public TfrCtrPersonnelBudgetEntity selectBudgetById(String id) {
		return (TfrCtrPersonnelBudgetEntity) super.getSqlSession().selectOne(
				NAMESPACE + "selectBudgetById", id);
	}

	@Override
	public TfrCtrPersonnelBudgetEntity selectBudgetRelated(
			TfrCtrPersonnelBudgetEntity entity) {
		return (TfrCtrPersonnelBudgetEntity) super.getSqlSession().selectOne(
				NAMESPACE + "selectBudgetRelated", entity);
	}

	@Override
	public TfrCtrPersonnelActualEntity selectActualRelated(
			TfrCtrPersonnelActualEntity entity) {
		return (TfrCtrPersonnelActualEntity) super.getSqlSession().selectOne(
				NAMESPACE + "selectActualRelated", entity);
	}

	@Override
	public TfrCtrPersonnelBudgetEntity selectBudgetLast(
			TfrCtrPersonnelBudgetEntity entity) {
		return (TfrCtrPersonnelBudgetEntity) super.getSqlSession().selectOne(
				NAMESPACE + "selectBudgetLast", entity);
	}

	@Override
	public TfrCtrPersonnelActualEntity selectActualLast(
			TfrCtrPersonnelActualEntity entity) {
		return (TfrCtrPersonnelActualEntity) super.getSqlSession().selectOne(
				NAMESPACE + "selectActualLast", entity);
	}
}
