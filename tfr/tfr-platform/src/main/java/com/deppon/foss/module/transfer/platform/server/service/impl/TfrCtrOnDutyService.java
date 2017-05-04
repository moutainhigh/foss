package com.deppon.foss.module.transfer.platform.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.platform.api.server.dao.ITfrCtrOnDutyDao;
import com.deppon.foss.module.transfer.platform.api.server.service.IPlatformCommonService;
import com.deppon.foss.module.transfer.platform.api.server.service.ITfrCtrOnDutyService;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrCtrOnDutyEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TfrCtrOnDutyQcDto;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class TfrCtrOnDutyService implements ITfrCtrOnDutyService {

	private ITfrCtrOnDutyDao tfrCtrOnDutyDao;

	private IPlatformCommonService platformCommonService;

	public void setTfrCtrOnDutyDao(ITfrCtrOnDutyDao tfrCtrOnDutyDao) {
		this.tfrCtrOnDutyDao = tfrCtrOnDutyDao;
	}

	public void setPlatformCommonService(
			IPlatformCommonService platformCommonService) {
		this.platformCommonService = platformCommonService;
	}

	/**
	 * @desc 添加基本信息
	 * @param tfrCtrOnDufy
	 * @param currentTime
	 * @param special
	 * @date 2015年8月12日下午7:18:17
	 */
	private void attachBaseInfo(TfrCtrOnDutyEntity tfrCtrOnDufy,
			Date currentTime, String special) {

		if (tfrCtrOnDufy == null) {
			throw new TfrBusinessException("请将外场出勤人员信息填写完整。");
		}

		Date beginTime = tfrCtrOnDufy.getBeginTime();
		Date onDutyDate = DateUtils.getStartDatetime(beginTime);
		tfrCtrOnDufy.setOnDutyDate(onDutyDate);

		tfrCtrOnDufy.setCreateTime(currentTime);
		tfrCtrOnDufy.setModifyTime(currentTime);
		tfrCtrOnDufy.setSpecial(special);

		CurrentInfo currentUser = FossUserContext.getCurrentInfo();
		String empCode = currentUser.getEmpCode();
		String empName = currentUser.getEmpName();

		tfrCtrOnDufy.setCreateUserCode(empCode);
		tfrCtrOnDufy.setCreateUserName(empName);
		tfrCtrOnDufy.setModifyUserCode(empCode);
		tfrCtrOnDufy.setModifyUserName(empName);

		tfrCtrOnDufy.setId(UUIDUtils.getUUID());
	}

	private void handleTime(TfrCtrOnDutyEntity tfrCtrOnDufy) {
		// 开始时间处理
		Date beginDatePart = DateUtils.getStartDatetime(tfrCtrOnDufy
				.getBeginDatePart());
		Date beginTimePart = tfrCtrOnDufy.getBeginTimePart();

		String beginDateStr = DateUtils.convert(beginDatePart,
				DateUtils.DATE_FORMAT);
		String beginTimeStr = DateUtils.convert(beginTimePart,
				DateUtils.TIME_FORMAT);

		// 结束时间处理
		Date endDatePart = DateUtils.getStartDatetime(tfrCtrOnDufy
				.getEndDatePart());
		Date endTimePart = tfrCtrOnDufy.getEndTimePart();

		String endDateStr = DateUtils.convert(endDatePart,
				DateUtils.DATE_FORMAT);
		String endTimeStr = DateUtils.convert(endTimePart,
				DateUtils.TIME_FORMAT);

		Date beginTime = DateUtils.convert(beginDateStr + " " + beginTimeStr,
				DateUtils.DATE_TIME_FORMAT);
		Date endTime = DateUtils.convert(endDateStr + " " + endTimeStr,
				DateUtils.DATE_TIME_FORMAT);

		if (beginTime == null || endTime == null) {
			throw new TfrBusinessException("工号" + tfrCtrOnDufy.getEmpCode()
					+ "员工缺少上班开始时间或上班结束时间");
		}

		if (beginTime.compareTo(endTime) >= 0) {
			throw new TfrBusinessException("工号" + tfrCtrOnDufy.getEmpCode()
					+ "员工的上班开始时间必须小于上班结束时间");
		}

		tfrCtrOnDufy.setBeginTime(beginTime);
		tfrCtrOnDufy.setEndTime(endTime);
	}

	/**
	 * @desc 新增
	 * @param tfrCtrOnDutys
	 * @date 2015年8月12日下午7:18:34
	 */
	@Transactional
	@Override
	public void insertTfrCtrOnDuty(List<TfrCtrOnDutyEntity> tfrCtrOnDutys) {
		Date currentTime = new Date();
		String special = FossConstants.NO;

		for (TfrCtrOnDutyEntity index : tfrCtrOnDutys) {
			handleTime(index);

			attachBaseInfo(index, currentTime, special);

			try {
				tfrCtrOnDutyDao.insertTfrCtrOnDuty(index);
			} catch (DuplicateKeyException e) {

				String empCode = index.getEmpCode();
				Date onDutyDate = index.getOnDutyDate();

				String dateStr = DateUtils.convert(onDutyDate,
						DateUtils.DATE_FORMAT);

				throw new TfrBusinessException("已经存在工号" + empCode + "在"
						+ dateStr + "的出勤记录，请将次记录修改或删除后添加！");
			}
		}
	}

	/**
	 * @desc 新增特殊人员
	 * @param tfrCtrOnDufy
	 * @date 2015年8月12日下午7:18:39
	 */
	@Override
	public void insertSpecial(TfrCtrOnDutyEntity tfrCtrOnDufy) {

		String tfrCtrCode = tfrCtrOnDufy.getTfrCtrCode();
		String orgCode = tfrCtrOnDufy.getOrgCode();
		if (!checkDeptMatched(tfrCtrCode, orgCode)) {
			throw new TfrBusinessException("外场和部门不匹配！");
		}

		Date beginTime = tfrCtrOnDufy.getBeginTime();
		Date endTime = tfrCtrOnDufy.getEndTime();

		if (beginTime == null || endTime == null) {
			throw new TfrBusinessException("员工缺少上班开始时间或上班结束时间");
		}

		if (beginTime.compareTo(endTime) >= 0) {
			throw new TfrBusinessException("员工的上班开始时间必须小于上班结束时间");
		}

		Date currentTime = new Date();
		String special = FossConstants.YES;
		attachBaseInfo(tfrCtrOnDufy, currentTime, special);
		tfrCtrOnDutyDao.insertTfrCtrOnDuty(tfrCtrOnDufy);
	}

	/**
	 * @desc 修改
	 * @param tfrCtrOnDufy
	 * @date 2015年8月12日下午7:18:46
	 */
	@Override
	public void updateTfrCtrOnDuty(TfrCtrOnDutyEntity tfrCtrOnDufy) {

		Date beginTime = tfrCtrOnDufy.getBeginTime();
		Date endTime = tfrCtrOnDufy.getEndTime();

		if (beginTime == null || endTime == null) {
			throw new TfrBusinessException("员工缺少上班开始时间或上班结束时间");
		}

		if (beginTime.compareTo(endTime) >= 0) {
			throw new TfrBusinessException("员工的上班开始时间必须小于上班结束时间");
		}

		Date onDutyDate = DateUtils.getStartDatetime(beginTime);
		tfrCtrOnDufy.setOnDutyDate(onDutyDate);

		CurrentInfo currentUser = FossUserContext.getCurrentInfo();
		String empCode = currentUser.getEmpCode();
		String empName = currentUser.getEmpName();
		tfrCtrOnDufy.setModifyUserCode(empCode);
		tfrCtrOnDufy.setModifyUserName(empName);

		int result = tfrCtrOnDutyDao.updateTfrCtrOnDuty(tfrCtrOnDufy);
		if (result == 0) {
			throw new TfrBusinessException("该记录已被修改或删除，请重新查询后，再进行修改！");
		}
	}

	/**
	 * @desc 删除
	 * @param id
	 * @date 2015年8月12日下午7:18:50
	 */
	@Override
	public void deleteTfrCtrOnDuty(String id) {
		tfrCtrOnDutyDao.deleteTfrCtrOnDuty(id);
	}

	/**
	 * @desc 查询
	 * @param parameter
	 * @param start
	 * @param limit
	 * @return
	 * @date 2015年8月12日下午7:19:19
	 */
	@Override
	public List<TfrCtrOnDutyEntity> findTfrCtrOnDutys(
			TfrCtrOnDutyQcDto parameter, int start, int limit) {
		return tfrCtrOnDutyDao.findTfrCtrOnDutys(parameter, start, limit);
	}

	/**
	 * @desc 查询总数量
	 * @param parameter
	 * @return
	 * @date 2015年8月13日下午12:13:32
	 */
	@Override
	public Long cntTfrCtrOnDutys(TfrCtrOnDutyQcDto parameter) {
		return tfrCtrOnDutyDao.cntTfrCtrOnDutys(parameter);
	}

	/**
	 * @desc 新增时查询前检查部门和外场是否匹配
	 * @param tfrCtrCode
	 * @param orgCode
	 * @date 2015年8月12日下午7:19:25
	 */
	private boolean checkDeptMatched(String tfrCtrCode, String orgCode) {
		OrgAdministrativeInfoEntity supOrg = platformCommonService
				.querySupTfrCtr(orgCode);
		if (supOrg == null || tfrCtrCode == null
				|| !tfrCtrCode.equals(supOrg.getCode())) {
			return false;
		}

		return true;
	}

	/**
	 * @desc 新增时查询
	 * @param parameter
	 * @return
	 * @date 2015年8月12日下午7:19:55
	 */
	@Override
	public List<TfrCtrOnDutyEntity> findInfos4Add(TfrCtrOnDutyQcDto parameter) {
		String tfrCtrCode = parameter.getTfrCtrCode();
		String orgCode = parameter.getOrgCode();

		if (!checkDeptMatched(tfrCtrCode, orgCode)) {
			return new ArrayList<TfrCtrOnDutyEntity>();
		}

		return tfrCtrOnDutyDao.findInfos4Add(parameter);
	}

}
