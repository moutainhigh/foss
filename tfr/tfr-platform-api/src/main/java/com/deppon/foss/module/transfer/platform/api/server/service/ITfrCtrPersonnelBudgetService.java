package com.deppon.foss.module.transfer.platform.api.server.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrCtrPersonnelActualEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrCtrPersonnelBudgetEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TfrCtrPersonnelBudgetDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TfrCtrPersonnelBudgetQcDto;

public interface ITfrCtrPersonnelBudgetService {

	/**
	 * 返回传入部门code所属外场
	 * @return
	 * @date 2014-3-26
	 * @author Ouyang
	 */
	Map<String, String> queryParentTfrCtrCode(String currentDeptCode);

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
	 * 新增外场预算人员
	 * @param entity
	 * @date 2014-3-26
	 * @author Ouyang
	 */
	void insertBudget(TfrCtrPersonnelBudgetEntity entity);

	/**
	 * 修改外场预算人员
	 * @param entity
	 * @date 2014-3-26
	 * @author Ouyang
	 */
	void updateBudget(TfrCtrPersonnelBudgetEntity entity);

	/**
	 * 新增外场实际人员
	 * @param entity
	 * @date 2014-3-26
	 * @author Ouyang
	 */
	void insertActual(TfrCtrPersonnelActualEntity entity);

	/**
	 * 修改外场实际人员
	 * @param entity
	 * @date 2014-3-26
	 * @author Ouyang
	 */
	void updateActual(TfrCtrPersonnelActualEntity entity);

	/**
	 * 查询最后一条预算人员
	 * @param entity
	 * @return
	 * @date 2014-3-31
	 * @author Ouyang
	 */
	TfrCtrPersonnelBudgetEntity selectBudgetLast(
			TfrCtrPersonnelBudgetEntity entity);

	/**
	 * 查询最后一条实际人员
	 * @param entity
	 * @return
	 * @date 2014-3-31
	 * @author Ouyang
	 */
	TfrCtrPersonnelActualEntity selectActualLast(
			TfrCtrPersonnelActualEntity entity);

	/**
	 * 批量导入
	 * @param uploadFileFileName
	 * @param uploadFile
	 * @date 2014-4-3
	 * @author Ouyang
	 */
	void importTfrCtrPersonnelBudgets(String uploadFileName, File uploadFile);

	/**
	 * 导出excel
	 * @param qcDto
	 * @return
	 * @date 2014-4-3
	 * @author Ouyang
	 */
	ExportResource exportTfrCtrPersonnelBudgets(TfrCtrPersonnelBudgetQcDto qcDto);

}
