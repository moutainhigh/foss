package com.deppon.foss.module.transfer.platform.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.transfer.platform.api.server.dao.ITfrCtrStaffDao;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrCtrStaffNoDutyEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TfrCtrStaffDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TfrCtrStaffQcDto;

public class TfrCtrStaffDao extends iBatis3DaoImpl implements ITfrCtrStaffDao {

	private static final String NAMESPACE = "com.deppon.foss.module.transfer.platform.api.server.dao.ITfrCtrStaffDao.";

	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeEntity> getEmployeesByPosts(List<String> posts) {

		return super.getSqlSession().selectList(
				NAMESPACE + "getEmployeesByPosts", posts);
	}

	@Override
	public Long selectOneWorkLoad(TfrCtrStaffNoDutyEntity loader) {
		return (Long) super.getSqlSession().selectOne(
				NAMESPACE + "selectOneWorkLoad", loader);
	}

	@Override
	public Long selectOneTrayScan(TfrCtrStaffNoDutyEntity forkliftDriver) {
		return (Long) super.getSqlSession().selectOne(
				NAMESPACE + "selectOneTrayScan", forkliftDriver);
	}

	@Override
	public Long selectOneStaff3DayNoDuty(TfrCtrStaffNoDutyEntity entity) {
		return (Long) super.getSqlSession().selectOne(
				NAMESPACE + "selectOneStaff3DayNoDuty", entity);
	}

	@Override
	public Long selectCntStaffNoDutyInPre2Day(TfrCtrStaffNoDutyEntity entity) {
		return (Long) super.getSqlSession().selectOne(
				NAMESPACE + "selectCntStaffNoDutyInPre2Day", entity);
	}

	@Override
	public Integer queryTfrCtrActual(String transferCenterCode,
			Date firstMomOfQueryMonth) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("transferCenterCode", transferCenterCode);
		map.put("firstMomOfQueryMonth", firstMomOfQueryMonth);

		return (Integer) super.getSqlSession().selectOne(
				NAMESPACE + "queryTfrCtrActual", map);
	}

	@Override
	public void insertStaffNoDuty(TfrCtrStaffNoDutyEntity entity) {
		super.getSqlSession().insert(NAMESPACE + "insertStaffNoDuty", entity);
	}

	@Override
	public void insertStaff3DayNoDuty(TfrCtrStaffNoDutyEntity entity) {
		super.getSqlSession().insert(NAMESPACE + "insertStaff3DayNoDuty",
				entity);
	}

	@Override
	public void insertStaffOnDuty(TfrCtrStaffNoDutyEntity entity) {
		super.getSqlSession().insert(NAMESPACE + "insertStaffOnDuty", entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TfrCtrStaffDto> queryTfrCtrStaffDtos(TfrCtrStaffQcDto dto) {
		return super.getSqlSession().selectList(
				NAMESPACE + "queryTfrCtrStaffDtos", dto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TfrCtrStaffNoDutyEntity> queryTfrCtrStaff3DayNoDuty(
			String transferCenterCode, String statisticMonth) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("transferCenterCode", transferCenterCode);
		map.put("statisticMonth", statisticMonth);
		return super.getSqlSession().selectList(
				NAMESPACE + "queryTfrCtrStaff3DayNoDuty", map);
	}

}
