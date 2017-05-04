package com.deppon.foss.module.transfer.platform.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrCtrStaffNoDutyEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TfrCtrStaffDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TfrCtrStaffQcDto;

public interface ITfrCtrStaffDao {

	/**
	 * 根据岗位code，获取员工
	 * @param posts
	 * @return
	 * @date 2014-4-23
	 * @author Ouyang
	 */
	List<EmployeeEntity> getEmployeesByPosts(List<String> posts);

	/**
	 * 查询装卸车员是否有货量
	 * @param loader
	 * @return
	 * @date 2014-4-22
	 * @author Ouyang
	 */
	Long selectOneWorkLoad(TfrCtrStaffNoDutyEntity loader);

	/**
	 * 查询叉车司机是否有叉车票数
	 * @param forkliftDriver
	 * @return
	 * @date 2014-4-22
	 * @author Ouyang
	 */
	Long selectOneTrayScan(TfrCtrStaffNoDutyEntity forkliftDriver);

	/**
	 * 判断未出勤员工在统计日期当月是否存在连续3天未出勤的记录
	 * @param entity
	 * @return
	 * @date 2014-4-24
	 * @author Ouyang
	 */
	Long selectOneStaff3DayNoDuty(TfrCtrStaffNoDutyEntity entity);

	/**
	 * 查询TfrCtrStaffNoDutyEntity.statisticDate前两天，该人员工号的未出勤记录数；
	 * 如果每天都未出勤，则应该每天一条，若前两天的记录数为2，则表示改员工连续3天未出勤
	 * @param entity 当天未出勤的员工
	 * @return
	 * @date 2014-4-23
	 * @author Ouyang
	 */
	Long selectCntStaffNoDutyInPre2Day(TfrCtrStaffNoDutyEntity entity);

	/**
	 * 查询某外场某天的总实际人数
	 * @param transferCenterCode
	 * @param firstMomOfQueryMonth
	 * @return
	 * @date 2014-4-28
	 * @author Ouyang
	 */
	Integer queryTfrCtrActual(String transferCenterCode,
			Date firstMomOfQueryMonth);
	
	/**
	 * 保存未出勤的装卸车员(叉车司机)
	 * @param entity
	 * @date 2014-4-22
	 * @author Ouyang
	 */
	void insertStaffNoDuty(TfrCtrStaffNoDutyEntity entity);

	/**
	 * 保存连续3天未出勤的装卸车员(叉车司机)
	 * @param entity
	 * @date 2014-4-23
	 * @author Ouyang
	 */
	void insertStaff3DayNoDuty(TfrCtrStaffNoDutyEntity entity);

	/**
	 * 保存出勤的装卸车员(叉车司机)
	 * @param entity
	 * @date 2014-4-25
	 * @author Ouyang
	 */
	void insertStaffOnDuty(TfrCtrStaffNoDutyEntity entity);
	
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
	 * @param statisticMonth
	 * @return
	 * @date 2014-4-28
	 * @author Ouyang
	 */
	List<TfrCtrStaffNoDutyEntity> queryTfrCtrStaff3DayNoDuty(
			String transferCenterCode, String statisticMonth);

}
