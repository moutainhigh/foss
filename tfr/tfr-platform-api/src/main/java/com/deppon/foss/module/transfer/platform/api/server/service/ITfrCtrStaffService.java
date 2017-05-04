package com.deppon.foss.module.transfer.platform.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrCtrStaffNoDutyEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TfrCtrStaffDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TfrCtrStaffQcDto;

public interface ITfrCtrStaffService {

	/**
	 * 查询部门所属外场
	 * @param code
	 * @return
	 * @date 2014-4-28
	 * @author Ouyang
	 */
	OrgAdministrativeInfoEntity getTfrCtrBySubCode(String code);
	
	/**
	 * 保存装卸车员(叉车司机)的出勤情况
	 * @date 2014-4-22
	 * @author Ouyang
	 */
	void insertTfrCtrStaff();

	/**
	 * 查询外场人员情况
	 * @param dto
	 * @return
	 * @date 2014-4-25
	 * @author Ouyang
	 */
	List<TfrCtrStaffDto> queryTfrCtrStaffDtos(TfrCtrStaffQcDto dto);

	/**
	 * 查询某外场某月月累计连续3天未出勤人员
	 * @param transferCenterCode
	 * @param queryDate
	 * @return
	 * @date 2014-4-28
	 * @author Ouyang
	 */
	List<TfrCtrStaffNoDutyEntity> queryTfrCtrStaff3DayNoDuty(
			String transferCenterCode, Date queryDate);
	
	/**
	 * 导出外场月累计连续3日未出勤名单
	 * @param transferCenterCode
	 * @param queryDate
	 * @return
	 * @date 2014-4-30
	 * @author Ouyang
	 */
	ExportResource exportTfrCtrStaff3DayNoDuty(String transferCenterCode, Date queryDate);
	
	/**
	 * 导出外场人员情况
	 * @param dto
	 * @return
	 * @date 2014-4-30
	 * @author Ouyang
	 */
	ExportResource exportTfrCtrStaffDtos(TfrCtrStaffQcDto dto);
}
