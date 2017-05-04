package com.deppon.foss.module.transfer.platform.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrCtrPersonnelActualEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrCtrPersonnelBudgetEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TfrCtrPersonnelBudgetDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TfrCtrPersonnelBudgetQcDto;

public interface ITfrCtrPersonnelBudgetDao {

	/**
	 * 
	 * @param tfrCtrPersonnelBudgetQcDto
	 * @return
	 * @date 2014-3-31
	 * @author Ouyang
	 */
	List<TfrCtrPersonnelBudgetDto> selectTfrCtrPersonnelBudgets(
			TfrCtrPersonnelBudgetQcDto tfrCtrPersonnelBudgetQcDto);

	/**
	 * 新增预算
	 * @param entity
	 * @date 2014-3-31
	 * @author Ouyang
	 */
	void insertBudget(TfrCtrPersonnelBudgetEntity entity);

	/**
	 * 新增实际
	 * @param entity
	 * @date 2014-3-31
	 * @author Ouyang
	 */
	void insertActual(TfrCtrPersonnelActualEntity entity);

	/**
	 * 修改预算有效截至日期
	 * @param entity
	 * @return
	 * @date 2014-3-31
	 * @author Ouyang
	 */
	int updateBudgetEndEffectiveDate(TfrCtrPersonnelBudgetEntity entity);

	/**
	 * 修改实际有效截至日期
	 * @param entity
	 * @return
	 * @date 2014-3-31
	 * @author Ouyang
	 */
	int updateActualEndEffectiveDate(TfrCtrPersonnelActualEntity entity);

	/**
	 * 修改预算人员
	 * @param entity
	 * @return
	 * @date 2014-3-31
	 * @author Ouyang
	 */
	int updateBudget(TfrCtrPersonnelBudgetEntity entity);

	/**
	 * 修改实际各岗位人员数量
	 * @param entity
	 * @return
	 * @date 2014-3-31
	 * @author Ouyang
	 */
	int updateActualPostNum(TfrCtrPersonnelActualEntity entity);

	/**
	 * 根据id查询预算人员
	 * @param id
	 * @return
	 * @date 2014-3-31
	 * @author Ouyang
	 */
	TfrCtrPersonnelBudgetEntity selectBudgetById(String id);

	/**
	 * 根据外场编码，有效截至日期查询预算人员
	 * @param entity
	 * @return
	 * @date 2014-3-31
	 * @author Ouyang
	 */
	TfrCtrPersonnelBudgetEntity selectBudgetRelated(
			TfrCtrPersonnelBudgetEntity entity);

	/**
	 * 根据外场编码，有效截至日期查询实际人员
	 * @param entity
	 * @return
	 * @date 2014-3-31
	 * @author Ouyang
	 */
	TfrCtrPersonnelActualEntity selectActualRelated(
			TfrCtrPersonnelActualEntity entity);

	TfrCtrPersonnelBudgetEntity selectBudgetLast(
			TfrCtrPersonnelBudgetEntity entity);

	TfrCtrPersonnelActualEntity selectActualLast(
			TfrCtrPersonnelActualEntity entity);
}
