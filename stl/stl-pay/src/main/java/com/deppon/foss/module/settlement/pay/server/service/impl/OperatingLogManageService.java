package com.deppon.foss.module.settlement.pay.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.OperatingLogEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.dao.IOperatingLogQueryDao;
import com.deppon.foss.module.settlement.pay.api.server.service.IOperatingLogManageService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.OperatingLogQueryDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.OperatingLogResultDto;

/**
 * 操作日志service
 * 
 * @author foss-qiaolifeng
 * @date 2012-12-10 下午4:46:10
 */
public class OperatingLogManageService implements IOperatingLogManageService {

	/**
	 * Logger
	 */
	private static final Logger logger = LogManager
			.getLogger(OperatingLogManageService.class);

	/**
	 * 综合业务组织service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	/**
	 * 操作日志dao
	 */
	private IOperatingLogQueryDao operatingLogQueryDao;

	/**
	 * 根据日期查询日志
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-12-10 下午8:30:57
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IOperatingLogManageService#queryOperatingLogByDate(com.deppon.foss.module.settlement.pay.api.shared.dto.OperatingLogQueryDto)
	 */
	@Override
	public List<OperatingLogResultDto> queryOperatingLogByDate(
			OperatingLogQueryDto dto, CurrentInfo cInfo) {

		logger.debug("查询操作日志开始...");
		List<OperatingLogResultDto> rtnDtoList = new ArrayList<OperatingLogResultDto>();

		if (dto == null || dto.getStartOperateTime() == null
				|| dto.getEndOperateTime() == null) {
			throw new SettlementException("输入参数异常,查询日志失败!");
		}

		// 根据大区、小区获取下属的部门编码，并设置大区小区下属部门编码到查询条件中
		setBusinessRegionCodeList(dto, cInfo);

		// 查询数据
		List<OperatingLogEntity> operatingLogList = operatingLogQueryDao
				.queryOperatingLogByDate(dto);

		// OperatingLogEntity转换成OperatingLogResultDto
		if (CollectionUtils.isNotEmpty(operatingLogList)) {
			for (OperatingLogEntity entity : operatingLogList) {
				OperatingLogResultDto rtnDto = new OperatingLogResultDto();
				BeanUtils.copyProperties(entity, rtnDto);

				// 根据小区类别编码、组织编码获取小区信息
				List<String> smallRegin = new ArrayList<String>();
				smallRegin.add(BizTypeConstants.ORG_SMALL_REGION);// 小区类别编码
				OrgAdministrativeInfoEntity smallReginEntity = orgAdministrativeInfoComplexService
						.queryOrgAdministrativeInfoByCode(
								entity.getOperateOrgCode(), smallRegin);
				if (smallReginEntity != null) {

					// 设置小区编码、名称
					rtnDto.setBusinessSmallRegionCode(smallReginEntity
							.getCode());
					rtnDto.setBusinessSmallRegionName(smallReginEntity
							.getName());
				}

				// 根据大区类别编码、组织编码获取大区信息
				List<String> bigRegin = new ArrayList<String>();
				bigRegin.add(BizTypeConstants.ORG_BIG_REGION);//大曲
				OrgAdministrativeInfoEntity bigReginEntity = orgAdministrativeInfoComplexService
						.queryOrgAdministrativeInfoByCode(
								entity.getOperateOrgCode(), bigRegin);
				if (bigReginEntity != null) {

					// 设置大区编码、名称
					rtnDto.setBusinessBigRegionCode(bigReginEntity.getCode());
					rtnDto.setBusinessBigRegionName(bigReginEntity.getName());
				}

				// 根据小区类别编码、组织编码获取事业部信息
				List<String> division = new ArrayList<String>();
				division.add(BizTypeConstants.ORG_DIVISION);//事业部
				OrgAdministrativeInfoEntity divisionReginEntity = orgAdministrativeInfoComplexService
						.queryOrgAdministrativeInfoByCode(
								entity.getOperateOrgCode(), division);
				if (divisionReginEntity != null) {

					// 设置事业部编码、名称
					// 设置小区编码、名称
					rtnDto.setDivisionCode(divisionReginEntity.getCode());
					rtnDto.setDivisionName(divisionReginEntity.getName());
				}

				// 加入到返回值
				rtnDtoList.add(rtnDto);
			}
		}

		logger.debug("查询操作日志结束...");
		return rtnDtoList;
	}

	/**
	 * 根据日期分页查询日志
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-12-10 下午4:46:19
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IOperatingLogManageService#queryOperatingLogByDate(com.deppon.foss.module.settlement.pay.api.shared.dto.OperatingLogQueryDto)
	 */
	@Override
	public List<OperatingLogResultDto> queryOperatingLogByDateAndPage(
			OperatingLogQueryDto dto, int start, int limit, CurrentInfo cInfo) {

		logger.debug("分页查询操作日志开始...");
		List<OperatingLogResultDto> rtnDtoList = new ArrayList<OperatingLogResultDto>();

		if (dto == null || dto.getStartOperateTime() == null
				|| dto.getEndOperateTime() == null) {
			throw new SettlementException("输入参数异常,查询日志失败!");
		}

		// 根据大区、小区获取下属的部门编码，并设置大区小区下属部门编码到查询条件中
		setBusinessRegionCodeList(dto, cInfo);

		// 查询数据
		List<OperatingLogEntity> operatingLogList = operatingLogQueryDao
				.queryOperatingLogByDateAndPage(dto, start, limit);

		// OperatingLogEntity转换成OperatingLogResultDto
		if (CollectionUtils.isNotEmpty(operatingLogList)) {
			for (OperatingLogEntity entity : operatingLogList) {
				OperatingLogResultDto rtnDto = new OperatingLogResultDto();
				BeanUtils.copyProperties(entity, rtnDto);

				// 根据小区类别编码、组织编码获取小区信息
				List<String> smallRegin = new ArrayList<String>();
				smallRegin.add(BizTypeConstants.ORG_SMALL_REGION);// 小区类别编码
				OrgAdministrativeInfoEntity smallReginEntity = orgAdministrativeInfoComplexService
						.queryOrgAdministrativeInfoByCode(
								entity.getOperateOrgCode(), smallRegin);
				if (smallReginEntity != null) {

					// 设置小区编码、名称
					rtnDto.setBusinessSmallRegionCode(smallReginEntity
							.getCode());
					rtnDto.setBusinessSmallRegionName(smallReginEntity
							.getName());
				}

				// 根据小区类别编码、组织编码获取大区信息
				List<String> bigRegin = new ArrayList<String>();
				bigRegin.add(BizTypeConstants.ORG_BIG_REGION);//大区
				OrgAdministrativeInfoEntity bigReginEntity = orgAdministrativeInfoComplexService
						.queryOrgAdministrativeInfoByCode(
								entity.getOperateOrgCode(), bigRegin);
				if (bigReginEntity != null) {

					// 设置大区编码、名称
					rtnDto.setBusinessBigRegionCode(bigReginEntity.getCode());
					rtnDto.setBusinessBigRegionName(bigReginEntity.getName());
				}

				// 根据区类别编码、组织编码获取事业部信息
				List<String> division = new ArrayList<String>();
				division.add(BizTypeConstants.ORG_DIVISION);//事业部
				OrgAdministrativeInfoEntity divisionReginEntity = orgAdministrativeInfoComplexService
						.queryOrgAdministrativeInfoByCode(
								entity.getOperateOrgCode(), division);
				if (divisionReginEntity != null) {

					// 设置事业部编码、名称
					// 设置小区编码、名称
					rtnDto.setDivisionCode(divisionReginEntity.getCode());
					rtnDto.setDivisionName(divisionReginEntity.getName());
				}

				// 加入到返回值
				rtnDtoList.add(rtnDto);
			}
		}

		logger.debug("分页查询操作日志结束...");
		return rtnDtoList;
	}

	/**
	 * 根据日期查询日志总条数
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-12-10 下午4:46:33
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IOperatingLogManageService#queryTotalRowsbyDate(com.deppon.foss.module.settlement.pay.api.shared.dto.OperatingLogQueryDto)
	 */
	@Override
	public Long queryTotalRowsbyDate(OperatingLogQueryDto dto, CurrentInfo cInfo) {

		// 根据大区、小区获取下属的部门编码，并设置大区小区下属部门编码到查询条件中
		setBusinessRegionCodeList(dto, cInfo);

		// 查询总条数并返回
		return operatingLogQueryDao.queryTotalRowsbyDate(dto);
	}

	/**
	 * 根据大区、小区获取下属的部门编码，并设置大区小区下属部门编码到查询条件中
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-12-10 下午6:58:08
	 */
	private void setBusinessRegionCodeList(OperatingLogQueryDto dto,
			CurrentInfo cInfo) {

		// 初始化待设置当前登录用户下属部门List
		List<String> targetList = new ArrayList<String>();

		// 当前登录用户下属部门List
		List<String> userOrgCodesList = new ArrayList<String>();

		// 如果界面输入的部门存在，获取当前部门与当前登录用户下属部门的交集
		if (StringUtils.isNotBlank(dto.getOperateOrgCode())) {
			// 当前登录用户所属部门条件设置为空
			targetList.add(dto.getOperateOrgCode());

			// 如果输入的部门不存在，小区存在
		} else if (StringUtils.isNotBlank(dto.getBusinessSmallRegionCode())) {
			// 根据小区编码，获取该小区下属营业部部门信息
			List<OrgAdministrativeInfoEntity> list = orgAdministrativeInfoComplexService
					.queryOrgAdministrativeInfoEntityAllSubByCode(dto
							.getBusinessSmallRegionCode());
			// 如果营业部门信息不为空，获取其全部部门编码
			if (CollectionUtils.isNotEmpty(list)) {
				for (OrgAdministrativeInfoEntity entity : list) {
					targetList.add(entity.getCode());
				}
			}
			// 如果输入的部门、小区不存在，大区存在
		} else if (StringUtils.isNotBlank(dto.getBusinessBigRegionCode())) {

			// 根据大区编码，获取该大区下属营业部部门信息
			List<OrgAdministrativeInfoEntity> list = orgAdministrativeInfoComplexService
					.queryOrgAdministrativeInfoEntityAllSubByCode(dto
							.getBusinessBigRegionCode());
			// 如果营业部门信息不为空，获取其全部部门编码
			if (CollectionUtils.isNotEmpty(list)) {
				for (OrgAdministrativeInfoEntity entity : list) {
					targetList.add(entity.getCode());
				}
			}
			// 如果输入的部门、小区、大区均为不存在，则按当前登录用户的下属部门为条件查询
		} else {
			targetList.addAll(userOrgCodesList);
		}

		// 设置查询条件
		dto.setBusinessRegionCodeList(targetList);
		
		//设置当前登录用户员工编码
		dto.setEmpCode(cInfo.getEmpCode());
	}

	/**
	 * @param orgAdministrativeInfoComplexService
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
	 * @param operatingLogQueryDao
	 */
	public void setOperatingLogQueryDao(
			IOperatingLogQueryDao operatingLogQueryDao) {
		this.operatingLogQueryDao = operatingLogQueryDao;
	}

}
