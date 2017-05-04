package com.deppon.foss.module.transfer.platform.server.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.HeaderRows;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOutfieldService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.platform.api.server.dao.ITfrCtrPersonnelBudgetDao;
import com.deppon.foss.module.transfer.platform.api.server.service.ITfrCtrPersonnelBudgetService;
import com.deppon.foss.module.transfer.platform.api.shared.define.PlatformConstants;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrCtrPersonnelActualEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrCtrPersonnelBudgetEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TfrCtrPersonnelBudgetDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TfrCtrPersonnelBudgetQcDto;
import com.deppon.foss.module.transfer.platform.api.shared.utils.PlatformUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class TfrCtrPersonnelBudgetService implements
		ITfrCtrPersonnelBudgetService {

	private ITfrCtrPersonnelBudgetDao tfrCtrPersonnelBudgetDao;

	private IOutfieldService outfieldService;

	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setTfrCtrPersonnelBudgetDao(
			ITfrCtrPersonnelBudgetDao tfrCtrPersonnelBudgetDao) {
		this.tfrCtrPersonnelBudgetDao = tfrCtrPersonnelBudgetDao;
	}

	public void setOutfieldService(IOutfieldService outfieldService) {
		this.outfieldService = outfieldService;
	}

	/**
	 * 查询给定部门code所属外场
	 * @param code
	 * @return
	 * @date 2014-3-26
	 * @author Ouyang
	 */
	@Override
	public Map<String, String> queryParentTfrCtrCode(String code) {
		Map<String, String> result = null;

		// 调用综合接口判断当前部门是否外场或外场子部门
		List<String> bizTypesList = new ArrayList<String>();

		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);

		OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoComplexService
				.queryOrgAdministrativeInfoIncludeSelfByCode(code, bizTypesList);
		if (orgEntity != null) {
			result = new HashMap<String, String>();
			result.put("code", orgEntity.getCode());
			result.put("name", orgEntity.getName());
		}

		return result;
	}

	/**
	 * 外场预算人员查询
	 * @param tfrCtrPersonnelBudgetQcDto
	 * @return
	 * @date 2014-3-31
	 * @author Ouyang
	 */
	@Override
	public List<TfrCtrPersonnelBudgetDto> selectTfrCtrPersonnelBudgets(
			TfrCtrPersonnelBudgetQcDto tfrCtrPersonnelBudgetQcDto) {

		if (tfrCtrPersonnelBudgetQcDto == null) {
			return null;
		}

		Date effectiveDate = tfrCtrPersonnelBudgetQcDto.getEffectiveDate();
		if (effectiveDate == null) {
			return null;
		}

		Date firstDayOfMonth = PlatformUtils.getFirstDayOfMonth(effectiveDate);
		tfrCtrPersonnelBudgetQcDto.setFirstDayOfMonth(firstDayOfMonth);

		List<TfrCtrPersonnelBudgetDto> budgetDtos = tfrCtrPersonnelBudgetDao
				.selectTfrCtrPersonnelBudgets(tfrCtrPersonnelBudgetQcDto);

		return budgetDtos;
	}

	/**
	 * 根据外场code,有效截至日期查记录
	 * @param transferCenterCode
	 * @param endEffectiveDate
	 * @return
	 * @date 2014-3-31
	 * @author Ouyang
	 */
	private TfrCtrPersonnelBudgetEntity selectBudgetRelated(
			String transferCenterCode, Date endEffectiveDate) {
		TfrCtrPersonnelBudgetEntity entity = new TfrCtrPersonnelBudgetEntity();
		entity.setTransferCenterCode(transferCenterCode);
		entity.setEndEffectiveDate(endEffectiveDate);
		return tfrCtrPersonnelBudgetDao.selectBudgetRelated(entity);
	}

	/**
	 * 修改有效截至日期
	 * @param beginEffectiveDate 新增/修改时待新增/修改记录的有效开始日期
	 * @param budgetRelated 待修改相连记录
	 * @date 2014-3-31
	 * @author Ouyang
	 */
	private void updateBudgetRelated(Date beginEffectiveDate,
			TfrCtrPersonnelBudgetEntity budgetRelated) {

		// 将有效截至日期修改为待新增、修改有效开始日期的前1天
		budgetRelated.setEndEffectiveDate(PlatformUtils
				.getLastMomentOfDay(PlatformUtils.addDate(beginEffectiveDate,
						Calendar.DAY_OF_MONTH, -1)));

		// 获取当前用户
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		String userCode = currentInfo.getEmpCode();
		String userName = currentInfo.getEmpName();

		// 设置修改人；entity4Update中的修改时间用做update的where子句的查询条件，所以此处不重新赋值；
		// 当前时间在update的set子句中set modify_time = sysdate
		budgetRelated.setModifyUserCode(userCode);
		budgetRelated.setModifyUserName(userName);

		// 有效有日期为新增记录有效开始日期的前一天
		int result = tfrCtrPersonnelBudgetDao
				.updateBudgetEndEffectiveDate(budgetRelated);
		if (result == 0) {
			throw new TfrBusinessException("相连外场人员预算信息已被修改或删除，请重试保存操作。");
		}
	}

	/**
	 * 新增外场预算人员
	 * @param entity
	 * @date 2014-3-26
	 * @author Ouyang
	 */
	@Transactional
	@Override
	public void insertBudget(TfrCtrPersonnelBudgetEntity entity) {

		if (entity == null) {
			throw new TfrBusinessException("请将外场人员预算信息填写完整。");
		}
		
		if(entity.getTotalQty() == null || entity.getTotalQty() == 0){
			throw new TfrBusinessException("外场人员预算信息总数为0，请正确填写。");
		}
		
		// 生效开始日期
		Date beginEffectiveDate = entity.getBeginEffectiveDate();

		if (beginEffectiveDate == null) {
			throw new TfrBusinessException("请填写外场人员预算生效日期。");
		}

		// 获取生效日期所在月份的最后1天的23:59:59
		Date endEffectiveDate = PlatformUtils.getLastMomentOfDay(PlatformUtils
				.getLastDayOfMonth(beginEffectiveDate));

		// 设置生效结束日期
		entity.setEndEffectiveDate(endEffectiveDate);

		// 获取当前用户
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		String userCode = currentInfo.getEmpCode();
		String userName = currentInfo.getEmpName();

		Date nowDate = new Date();

		// 设置创建人，创建时间，修改人，修改时间
		entity.setCreateUserCode(userCode);
		entity.setCreateUserName(userName);
		entity.setCreateTime(nowDate);
		entity.setModifyUserCode(userCode);
		entity.setModifyUserName(userName);
		entity.setModifyTime(nowDate);

		// 设置id
		entity.setId(UUIDUtils.getUUID());

		String nullFiled = PlatformUtils.getNullField(entity);
		if (nullFiled != null) {
			throw new TfrBusinessException("请将外场人员预算信息填写完整。 " + nullFiled);
		}

		// 判断数据库中是否存在有效结束日期为endEffectiveDate的外场预算人员信息
		TfrCtrPersonnelBudgetEntity budgetRelated = selectBudgetRelated(
				entity.getTransferCenterCode(), endEffectiveDate);
		if (budgetRelated != null) {

			// 新增记录的有效开始日期，需在相连有效日期之间
			Date beginEffectiveDateDB = budgetRelated.getBeginEffectiveDate();
			Date endEffectiveDate4DB = budgetRelated.getEndEffectiveDate();

			int compareToBegin = beginEffectiveDate
					.compareTo(beginEffectiveDateDB);
			int compareToEnd = beginEffectiveDate
					.compareTo(endEffectiveDate4DB);

			if (compareToBegin <= 0 || compareToEnd >= 0) {
				throw new TfrBusinessException("外场"
						+ budgetRelated.getTransferCenterName()
						+ "已存在生效日期为"
						+ PlatformUtils.formatDate2String(beginEffectiveDateDB,
								"yyyy-MM-dd")
						+ "至"
						+ PlatformUtils.formatDate2String(endEffectiveDate4DB,
								"yyyy-MM-dd") + "外场人员预算信息。");
			}

			// 新增前，将原有效截至日期为endEffectiveDate的记录的有效截至日期修改为待新增记录有效开始日期的前一天
			updateBudgetRelated(beginEffectiveDate, budgetRelated);
		}

		// 再新增外场预算人员
		tfrCtrPersonnelBudgetDao.insertBudget(entity);
	}

	/**
	 * 修改外场预算人员
	 * @param entity
	 * @date 2014-3-26
	 * @author Ouyang
	 */
	@Transactional
	@Override
	public void updateBudget(TfrCtrPersonnelBudgetEntity entity) {

		if (entity == null) {
			throw new TfrBusinessException("请选择待修改的外场人员预算信息。");
		}
		
		if(entity.getTotalQty() == null || entity.getTotalQty() == 0){
			throw new TfrBusinessException("外场人员预算信息总数为0，请正确填写。");
		}

		// 获取当前用户
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		String userCode = currentInfo.getEmpCode();
		String userName = currentInfo.getEmpName();

		// 设置修改人；entity中的修改时间用做update的where子句的查询条件，所以此处不重新赋值；
		// 当前时间在update的set子句中set modify_time = sysdate
		entity.setModifyUserCode(userCode);
		entity.setModifyUserName(userName);

		// 界面上传过的来有效开始日期
		Date beginEffectiveDateUI = entity.getBeginEffectiveDate();
		if (beginEffectiveDateUI != null) {
			String id = entity.getId();
			if (StringUtils.isEmpty(id)) {
				throw new TfrBusinessException("请选择待修改的外场人员预算信息。");
			}

			// 根据id查询待修改数据存放在数据库中的原始记录
			TfrCtrPersonnelBudgetEntity dbEntity = tfrCtrPersonnelBudgetDao
					.selectBudgetById(id);

			if (dbEntity == null) {
				throw new TfrBusinessException("待修改的外场人员预算信息不存在，id=" + id);
			}

			// 数据库中存放的待修改记录的有效开始日期
			Date beginEffectiveDateDB = dbEntity.getBeginEffectiveDate();
			// 比较界面传入有效开始日期和数据库中存放的有效开始日期
			int compareToBegin = beginEffectiveDateUI
					.compareTo(beginEffectiveDateDB);

			// 如果不相等，则需修改相连记录的有效截至日期为界面传入有效开始日期的前一天
			// 比如说预算人员有两条记录有效日期为A:1~20号，B:21~30号,现将B的有效开始日期修改为18号*，则需要
			// 1、查询出有效日期截至日期为21-1=20号*的记录A；2、将记录A的有效截至日期修改为18-1=17号
			if (compareToBegin != 0) {

				// 数据库中存放的待修改记录的有效截至日期
				Date endEffectiveDateDB = dbEntity.getEndEffectiveDate();
				// 比较界面传入有效开始日期和数据库中存放的有效开始日期
				int compareToEnd = beginEffectiveDateUI
						.compareTo(endEffectiveDateDB);

				// 如果界面传入的有效开始日期大于数据库存放的有效开始日期，
				// 则界面传入的有效开始日期需在数据库存放的有效开始、截至日期之间;
				if (compareToBegin == 1 && compareToEnd >= 0) {
					throw new TfrBusinessException("该记录的有效日期为"
							+ PlatformUtils.formatDate2String(
									beginEffectiveDateDB, "yyyy-MM-dd")
							+ "至"
							+ PlatformUtils.formatDate2String(
									endEffectiveDateDB, "yyyy-MM-dd")
							+ ",请调整有效日期，使其小于有效截至日期。");
				}

				// 查询相连记录
				TfrCtrPersonnelBudgetEntity budgetRelated = selectBudgetRelated(
						dbEntity.getTransferCenterCode(),
						PlatformUtils.getLastMomentOfDay(PlatformUtils
								.addDate(beginEffectiveDateDB,
										Calendar.DAY_OF_MONTH, -1)));

				if (budgetRelated != null) {
					// 如果界面传入的有效开始日期小于数据库存放的有效开始日期，
					// 则界面传入的有效开始日期需大于相连的有效开始日期
					Date beginEffectiveDateRelated = budgetRelated
							.getBeginEffectiveDate();
					if (compareToBegin == -1 && beginEffectiveDateUI.compareTo(beginEffectiveDateRelated) <= 0) {

							String beginDateStr = PlatformUtils
									.formatDate2String(
											beginEffectiveDateRelated,
											"yyyy-MM-dd");

							throw new TfrBusinessException(
									"存在有效日期为"
											+ beginDateStr
											+ "至"
											+ PlatformUtils.formatDate2String(
													budgetRelated
															.getEndEffectiveDate(),
													"yyyy-MM-dd")
											+ "的记录，请调整有效日期，使其大于" + beginDateStr);
					}

					// 修改记录相连的记录
					updateBudgetRelated(beginEffectiveDateUI, budgetRelated);
				}
			}
		}

		int result = tfrCtrPersonnelBudgetDao.updateBudget(entity);
		if (result == 0) {
			throw new TfrBusinessException("待修改的外场人员预算信息已被修改或删除，请重新查询后，再进行修改");
		}
	}

	/**
	 * 根据外场code,有效截至日期查记录
	 * @param transferCenterCode
	 * @param endEffectiveDate
	 * @return
	 * @date 2014-3-31
	 * @author Ouyang
	 */
	private TfrCtrPersonnelActualEntity selectActualRelated(
			String transferCenterCode, Date endEffectiveDate) {

		TfrCtrPersonnelActualEntity entity = new TfrCtrPersonnelActualEntity();
		entity.setTransferCenterCode(transferCenterCode);
		entity.setEndEffectiveDate(endEffectiveDate);
		return tfrCtrPersonnelBudgetDao.selectActualRelated(entity);
	}

	/**
	 * 修改相连的有效记录
	 * @param actualRelated
	 * @date 2014-3-31
	 * @author Ouyang
	 */
	private void updateActualRelated(Date beginEffectiveDate,
			TfrCtrPersonnelActualEntity actualRelated) {

		// 将有效截至日期修改为待新增、修改有效开始日期的前1天
		actualRelated.setEndEffectiveDate(PlatformUtils
				.getLastMomentOfDay(PlatformUtils.addDate(beginEffectiveDate,
						Calendar.DAY_OF_MONTH, -1)));

		// 获取当前用户
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		String userCode = currentInfo.getEmpCode();
		String userName = currentInfo.getEmpName();

		// 设置修改人；entity4Update中的修改时间用做update的where子句的查询条件，所以此处不重新赋值；
		// 当前时间在update的set子句中set modify_time = sysdate
		actualRelated.setModifyUserCode(userCode);
		actualRelated.setModifyUserName(userName);

		// 有效有日期为新增记录有效开始日期的前一天
		int result = tfrCtrPersonnelBudgetDao
				.updateActualEndEffectiveDate(actualRelated);
		if (result == 0) {
			throw new TfrBusinessException("相连外场实际人员信息已被修改或删除，请重试保存操作。");
		}
	}

	/**
	 * 新增外场实际人员
	 * @param entity
	 * @date 2014-3-26
	 * @author Ouyang
	 */
	@Transactional
	@Override
	public void insertActual(TfrCtrPersonnelActualEntity entity) {

		if (entity == null) {
			throw new TfrBusinessException("请将外场实际人员信息填写完整。");
		}
		
		if(entity.getTotalQty() == null || entity.getTotalQty() == 0){
			throw new TfrBusinessException("外场实际人员信息总数为0，请正确填写。");
		}

		Date nowDate = new Date();

		// 生效开始日期
		Date beginEffectiveDate = entity.getBeginEffectiveDate();

		// 设置生效开始日期
		entity.setBeginEffectiveDate(beginEffectiveDate);

		// 获取生效日期为2999-12-31 23:59:59
		Date endEffectiveDate = PlatformUtils.parseString2Date(
				"2999-12-31 23:59:59", "yyyy-MM-dd HH:mm:ss");

		// 设置生效结束日期
		entity.setEndEffectiveDate(endEffectiveDate);

		// 获取当前用户
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		String userCode = currentInfo.getEmpCode();
		String userName = currentInfo.getEmpName();

		// 设置创建人，创建时间，修改人，修改时间
		entity.setCreateUserCode(userCode);
		entity.setCreateUserName(userName);
		entity.setCreateTime(nowDate);
		entity.setModifyUserCode(userCode);
		entity.setModifyUserName(userName);
		entity.setModifyTime(nowDate);

		// 设置id
		entity.setId(UUIDUtils.getUUID());

		String nullFiled = PlatformUtils.getNullField(entity);
		if (nullFiled != null) {
			throw new TfrBusinessException("请将外场实际人员信息填写完整。 " + nullFiled);
		}

		// 判断数据库中是否存在有效开始日期在beginEffectiveDate之后有效截至日期为2999-12-31
		// 23:59:59的外场实际人员信息
		TfrCtrPersonnelActualEntity actualRelated = selectActualRelated(
				entity.getTransferCenterCode(), endEffectiveDate);

		if (actualRelated != null) {

			// 新增记录的有效开始日期，需在相连有效日期之间
			Date beginEffectiveDateDB = actualRelated.getBeginEffectiveDate();
			Date endEffectiveDate4DB = actualRelated.getEndEffectiveDate();

			int compareToBegin = beginEffectiveDate
					.compareTo(beginEffectiveDateDB);
			int compareToEnd = beginEffectiveDate
					.compareTo(endEffectiveDate4DB);

			if (compareToBegin <= 0 || compareToEnd >= 0) {
				throw new TfrBusinessException("外场"
						+ actualRelated.getTransferCenterName()
						+ "已存在生效日期为"
						+ PlatformUtils.formatDate2String(beginEffectiveDateDB,
								"yyyy-MM-dd")
						+ "至"
						+ PlatformUtils.formatDate2String(endEffectiveDate4DB,
								"yyyy-MM-dd") + "外场实际人员信息。");
			}

			// 新增前，将原有效截至日期为当月底的记录的有效截至日期修改为待新增记录有效开始日期的前一天
			updateActualRelated(beginEffectiveDate, actualRelated);
		}

		// 再新增外场实际人员
		tfrCtrPersonnelBudgetDao.insertActual(entity);

	}

	/**
	 * 修改外场实际人员
	 * @param entity
	 * @date 2014-3-26
	 * @author Ouyang
	 */
	@Override
	public void updateActual(TfrCtrPersonnelActualEntity entity) {

		if (entity == null) {
			throw new TfrBusinessException("请选择待修改的外场实际人员信息。");
		}
		Date beginDate = entity.getBeginEffectiveDate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(beginDate != null){
			String beginDayStr = sdf.format(beginDate);
			String nowDayStr = sdf.format(new Date());
			if(!StringUtils.equals(beginDayStr, nowDayStr)){
				throw new TfrBusinessException("只能修改当天的实际人员信息！");
			}
		}
		
		if(entity.getTotalQty() == null || entity.getTotalQty() == 0){
			throw new TfrBusinessException("外场实际人员信息总数为0，请正确填写。");
		}

		// 获取当前用户
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		String userCode = currentInfo.getEmpCode();
		String userName = currentInfo.getEmpName();

		// 设置修改人；entity中的修改时间用做update的where子句的查询条件，所以此处不重新赋值；
		// 当前时间在update的set子句中set modify_time = sysdate
		entity.setModifyUserCode(userCode);
		entity.setModifyUserName(userName);

		int result = tfrCtrPersonnelBudgetDao.updateActualPostNum(entity);
		if (result == 0) {
			throw new TfrBusinessException("待修改的外场实际人员信息已被修改或删除，请重新查询后，再进行修改");
		}
	}

	/**
	 * 查询最后一条预算人员
	 * @param entity
	 * @return
	 * @date 2014-3-31
	 * @author Ouyang
	 */
	@Override
	public TfrCtrPersonnelBudgetEntity selectBudgetLast(
			TfrCtrPersonnelBudgetEntity entity) {

		TfrCtrPersonnelBudgetEntity result = null;

		if (entity == null
				|| StringUtils.isEmpty(entity.getTransferCenterCode())) {
			return result;
		}

		Date date = new Date();
		Date endEffectiveDate = null;

		// 只查有效截至日期为当月最后一天，或上月最后一天的预算人员
		for (int i = 0; i <= 1; i++) {
			date = PlatformUtils.addDate(date, Calendar.MONTH, -i);
			endEffectiveDate = PlatformUtils.getLastMomentOfDay(PlatformUtils
					.getLastDayOfMonth(date));
			entity.setEndEffectiveDate(endEffectiveDate);

			result = tfrCtrPersonnelBudgetDao.selectBudgetLast(entity);
			if (result != null) {
				return result;
			}
		}

		return result;
	}

	/**
	 * 查询最后一条实际人员
	 * @param entity
	 * @return
	 * @date 2014-3-31
	 * @author Ouyang
	 */
	@Override
	public TfrCtrPersonnelActualEntity selectActualLast(
			TfrCtrPersonnelActualEntity entity) {

		TfrCtrPersonnelActualEntity result = null;

		if (entity == null
				|| StringUtils.isEmpty(entity.getTransferCenterCode())) {
			return result;
		}

		Date endEffectiveDate = PlatformUtils.parseString2Date(
				"2999-12-31 23:59:59", "yyyy-MM-dd HH:mm:ss");
		entity.setEndEffectiveDate(endEffectiveDate);

		TfrCtrPersonnelActualEntity actualLast = tfrCtrPersonnelBudgetDao
				.selectActualLast(entity);
		if (actualLast != null) {
			actualLast.setDimission(0);
		}
		return actualLast;
	}

	/**
	 * 批量导入
	 * @param uploadFileFileName
	 * @param uploadFile
	 * @date 2014-4-3
	 * @author Ouyang
	 */
	@Transactional
	@Override
	public void importTfrCtrPersonnelBudgets(String uploadFileName,
			File uploadFile) {

		if (StringUtils.isEmpty(uploadFileName) || uploadFile == null) {
			throw new TfrBusinessException("请选择待导入的文件！");
		}

		String suffix = uploadFileName.substring(
				uploadFileName.lastIndexOf(".")).toLowerCase();
		if (!PlatformConstants.EXCEL_FILE_EXT_03.equals(suffix)
				&& !PlatformConstants.EXCEL_FILE_EXT_07.equals(suffix)) {
			throw new TfrBusinessException("请正确选择.xls或.xlsx格式的excel文件！");
		}

		mangerImportExcel(uploadFile);
	}

	/**
	 * sonar优化 wwb 311396 抽取出 处理文件方法
	 * @param uploadFile
	 */
	private void mangerImportExcel(File uploadFile) {
		InputStream inputStream = null;
		Workbook workbook = null;

		try {
			inputStream = new FileInputStream(uploadFile);
			workbook = PlatformUtils.createWorkbook(inputStream);
		} catch (FileNotFoundException e) {
			throw new TfrBusinessException("请正确选择带导入的excel文件！");
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (workbook == null) {
			throw new TfrBusinessException("请正确选择.xls或.xlsx格式的解密excel文件！");
		}

		// 读取第1个sheet的内容
		Sheet sheet = workbook.getSheetAt(0);
		// 获取sheet行数
		int rownum = sheet.getPhysicalNumberOfRows();
		// 模版中第一为模版说明、第二行为表头
		if (rownum <= 2) {
			throw new TfrBusinessException("请在待导入 的excel中填写相关数据！");
		}

		// 用于表示第i行
		Row row = null;
		// 用于表示第i行中的列数
		int cellnum = 0;

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat numberFormat = new DecimalFormat("0");

		for (int i = 2; i < rownum; i++) {
			row = sheet.getRow(i);
			if (row == null) {
				throw new TfrBusinessException("第" + (i + 1)
						+ "行数据为空，请将该行删除后重试！");
			}
			cellnum = row.getLastCellNum();
			// Excel模版中有20列数据
			if (cellnum < PlatformConstants.SONAR_NUMBER_20) {
				throw new TfrBusinessException("第" + (i + 1)
						+ "行数据与模版不匹配，请按模版导入数据！");
			}

			// 用于表示第i行j列
			Cell cell = null;
			// 用于表示第i行j列的值
			String cellValue = null;
			// 将各行数据转换成对象
			TfrCtrPersonnelBudgetEntity entity = new TfrCtrPersonnelBudgetEntity();
			// 取excel中前20列数据
			for (int j = 0; j < PlatformConstants.SONAR_NUMBER_20; j++) {
				cell = row.getCell(j);
				// 将excel中的数据转换成String
				cellValue = PlatformUtils.parseExcelCell(cell, dateFormat,
						numberFormat);

				// 设置属性
				setTfrCtrAbsenteeInfoPropsByCell(entity, j, cellValue, i);
			}

			Integer totalQty = entity.getDirector()
					+ entity.getDirectorStatistician()
					+ entity.getSeniorManager() + entity.getManager()
					+ entity.getSubManager() + entity.getDispatch()
					+ entity.getForkliftSecurityGuard()
					+ entity.getGoodsAreaGuard() + entity.getPlatformGuard()
					+ entity.getCivilService() + entity.getPartialMember()
					+ entity.getTallyClerk()
					+ entity.getElectricForkliftDriver()
					+ entity.getMachineForkliftDriver() + entity.getCarpenter()
					+ entity.getRepairer() + entity.getSorter()
					+ entity.getOther();

			entity.setTotalQty(totalQty);

			insertBudget(entity);
		}
	}

	private void setTfrCtrAbsenteeInfoPropsByCell(
			TfrCtrPersonnelBudgetEntity entity, int cellnum, String cellValue,
			int rownum) {

		if (StringUtils.isBlank(cellValue)) {
			throw new TfrBusinessException("请正确填写第" + (rownum + 1) + "行，第"
					+ (cellnum + 1) + "列的值！");
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		OutfieldEntity outfieldEntity = new OutfieldEntity();
		outfieldEntity.setActive(FossConstants.YES);

		List<OutfieldEntity> results = null;
		OutfieldEntity result = null;
		switch (cellnum) {
		// 外场名称
		case 0:
			outfieldEntity.setName(cellValue);
			results = outfieldService.queryOutfieldExactByEntity(
					outfieldEntity, 0, 1);
			if (CollectionUtils.isEmpty(results)) {
				throw new TfrBusinessException("不存在名称为" + cellValue + "的外场信息！");
			}

			result = results.get(0);

			entity.setTransferCenterCode(result.getOrgCode());
			entity.setTransferCenterName(result.getName());

			break;
		// 生效日期
		case 1:
			Date beginEffectiveDate = null;
			try {
				beginEffectiveDate = dateFormat.parse(cellValue);
			} catch (ParseException e) {
				throw new TfrBusinessException("请正确填写第" + (rownum + 1) + "行，第"
						+ (cellnum + 1) + "列的值！");
			}
			entity.setBeginEffectiveDate(PlatformUtils
					.getFirstMomentOfDay(beginEffectiveDate));
			break;
		case 2:
			Integer director = Integer.valueOf(cellValue);
			entity.setDirector(director);
			break;
		case PlatformConstants.SONAR_NUMBER_3:
			Integer directorStatistician = Integer.valueOf(cellValue);
			entity.setDirectorStatistician(directorStatistician);
			break;
		case PlatformConstants.SONAR_NUMBER_4:
			Integer seniorManager = Integer.valueOf(cellValue);
			entity.setSeniorManager(seniorManager);
			break;
		case PlatformConstants.SONAR_NUMBER_5:
			Integer manager = Integer.valueOf(cellValue);
			entity.setManager(manager);
			break;
		case PlatformConstants.SONAR_NUMBER_6:
			Integer subManager = Integer.valueOf(cellValue);
			entity.setSubManager(subManager);
			break;
		case PlatformConstants.SONAR_NUMBER_7:
			Integer dispatch = Integer.valueOf(cellValue);
			entity.setDispatch(dispatch);
			break;
		case PlatformConstants.SONAR_NUMBER_8:
			Integer forkliftSecurityGuard = Integer.valueOf(cellValue);
			entity.setForkliftSecurityGuard(forkliftSecurityGuard);
			break;
		case PlatformConstants.SONAR_NUMBER_9:
			Integer goodsAreaGuard = Integer.valueOf(cellValue);
			entity.setGoodsAreaGuard(goodsAreaGuard);
			break;
		case PlatformConstants.SONAR_NUMBER_10:
			Integer platformGuard = Integer.valueOf(cellValue);
			entity.setPlatformGuard(platformGuard);
			break;
		case PlatformConstants.SONAR_NUMBER_11:
			Integer civilService = Integer.valueOf(cellValue);
			entity.setCivilService(civilService);
			break;
		case PlatformConstants.SONAR_NUMBER_12:
			Integer partialMember = Integer.valueOf(cellValue);
			entity.setPartialMember(partialMember);
			break;
		case PlatformConstants.SONAR_NUMBER_13:
			Integer tallyClerk = Integer.valueOf(cellValue);
			entity.setTallyClerk(tallyClerk);
			break;
		case PlatformConstants.SONAR_NUMBER_14:
			Integer electricForkliftDriver = Integer.valueOf(cellValue);
			entity.setElectricForkliftDriver(electricForkliftDriver);
			break;
		case PlatformConstants.SONAR_NUMBER_15:
			Integer machineForkliftDriver = Integer.valueOf(cellValue);
			entity.setMachineForkliftDriver(machineForkliftDriver);
			break;
		case PlatformConstants.SONAR_NUMBER_16:
			Integer carpenter = Integer.valueOf(cellValue);
			entity.setCarpenter(carpenter);
			break;
		case PlatformConstants.SONAR_NUMBER_17:
			Integer repairer = Integer.valueOf(cellValue);
			entity.setRepairer(repairer);
			break;
		case PlatformConstants.SONAR_NUMBER_18:
			Integer sorter = Integer.valueOf(cellValue);
			entity.setSorter(sorter);
			break;
		case PlatformConstants.SONAR_NUMBER_19:
			Integer other = Integer.valueOf(cellValue);
			entity.setOther(other);
			break;
		default:
			break;
		}
	}

	private String parseInteger2Str(Integer source) {
		return source == null ? null : source.toString();
	}

	/**
	 * 导出excel
	 * @param qcDto
	 * @return
	 * @date 2014-4-3
	 * @author Ouyang
	 */
	@Override
	public ExportResource exportTfrCtrPersonnelBudgets(
			TfrCtrPersonnelBudgetQcDto qcDto) {

		List<TfrCtrPersonnelBudgetDto> budgets = selectTfrCtrPersonnelBudgets(qcDto);
		List<List<String>> rowList = new ArrayList<List<String>>();
		List<String> result = null;

		for (TfrCtrPersonnelBudgetDto item : budgets) {
			result = new ArrayList<String>();

			result.add(String.format("%1$tF",
					item.getBeginEffectiveDateBudget()));
			result.add(String.format("%1$tF", item.getEndEffectiveDateBudget()));

			result.add(item.getTransferCenterCode());
			result.add(item.getTransferCenterName());

			result.add(parseInteger2Str(item.getDirectorBudget()));
			result.add(parseInteger2Str(item.getDirectorActual()));
			result.add(parseInteger2Str(item.getDirectorRemainder()));

			result.add(parseInteger2Str(item.getDirectorStatisticianBudget()));
			result.add(parseInteger2Str(item.getDirectorStatisticianActual()));
			result.add(parseInteger2Str(item.getDirectorStatisticianRemainder()));

			result.add(parseInteger2Str(item.getSeniorManagerBudget()));
			result.add(parseInteger2Str(item.getSeniorManagerActual()));
			result.add(parseInteger2Str(item.getSeniorManagerRemainder()));

			result.add(parseInteger2Str(item.getManagerBudget()));
			result.add(parseInteger2Str(item.getManagerActual()));
			result.add(parseInteger2Str(item.getManagerRemainder()));

			result.add(parseInteger2Str(item.getSubManagerBudget()));
			result.add(parseInteger2Str(item.getSubManagerActual()));
			result.add(parseInteger2Str(item.getSubManagerRemainder()));

			result.add(parseInteger2Str(item.getDispatchBudget()));
			result.add(parseInteger2Str(item.getDispatchActual()));
			result.add(parseInteger2Str(item.getDispatchRemainder()));

			result.add(parseInteger2Str(item.getForkliftSecurityGuardBudget()));
			result.add(parseInteger2Str(item.getForkliftSecurityGuardActual()));
			result.add(parseInteger2Str(item
					.getForkliftSecurityGuardRemainder()));

			result.add(parseInteger2Str(item.getGoodsAreaGuardBudget()));
			result.add(parseInteger2Str(item.getGoodsAreaGuardActual()));
			result.add(parseInteger2Str(item.getGoodsAreaGuardRemainder()));

			result.add(parseInteger2Str(item.getPlatformGuardBudget()));
			result.add(parseInteger2Str(item.getPlatformGuardActual()));
			result.add(parseInteger2Str(item.getPlatformGuardRemainder()));

			result.add(parseInteger2Str(item.getCivilServiceBudget()));
			result.add(parseInteger2Str(item.getCivilServiceActual()));
			result.add(parseInteger2Str(item.getCivilServiceRemainder()));

			result.add(parseInteger2Str(item.getPartialMemberBudget()));
			result.add(parseInteger2Str(item.getPartialMemberActual()));
			result.add(parseInteger2Str(item.getPartialMemberRemainder()));

			result.add(parseInteger2Str(item.getTallyClerkBudget()));
			result.add(parseInteger2Str(item.getTallyClerkActual()));
			result.add(parseInteger2Str(item.getTallyClerkRemainder()));

			result.add(parseInteger2Str(item.getElectricForkliftDriverBudget()));
			result.add(parseInteger2Str(item.getElectricForkliftDriverActual()));
			result.add(parseInteger2Str(item
					.getElectricForkliftDriverRemainder()));

			result.add(parseInteger2Str(item.getMachineForkliftDriverBudget()));
			result.add(parseInteger2Str(item.getMachineForkliftDriverActual()));
			result.add(parseInteger2Str(item
					.getMachineForkliftDriverRemainder()));

			result.add(parseInteger2Str(item.getCarpenterBudget()));
			result.add(parseInteger2Str(item.getCarpenterActual()));
			result.add(parseInteger2Str(item.getCarpenterRemainder()));

			result.add(parseInteger2Str(item.getRepairerBudget()));
			result.add(parseInteger2Str(item.getRepairerActual()));
			result.add(parseInteger2Str(item.getRepairerRemainder()));

			result.add(parseInteger2Str(item.getSorterBudget()));
			result.add(parseInteger2Str(item.getSorterActual()));
			result.add(parseInteger2Str(item.getSorterRemainder()));

			result.add(parseInteger2Str(item.getOtherBudget()));
			result.add(parseInteger2Str(item.getOtherActual()));
			result.add(parseInteger2Str(item.getOtherRemainder()));

			result.add(parseInteger2Str(item.getTotalQtyBudget()));
			result.add(parseInteger2Str(item.getTotalQtyActual()));
			result.add(parseInteger2Str(item.getTotalQtyRemainder()));

			result.add(parseInteger2Str(item.getDimission()));
			result.add(parseInteger2Str(item.getDimissionAccumulated()));

			rowList.add(result);
		}

		HeaderRows[] headerRows = { new HeaderRows(0, 1, 0, 0, "生效日期"),
				new HeaderRows(0, 1, 1, 1, "截至日期"),

				new HeaderRows(0, 1, 2, 2, "外场编码"),
				new HeaderRows(0, 1, PlatformConstants.SONAR_NUMBER_3, PlatformConstants.SONAR_NUMBER_3, "外场名称"),

				new HeaderRows(0, 0, PlatformConstants.SONAR_NUMBER_4, PlatformConstants.SONAR_NUMBER_6, "总监"),
				new HeaderRows(0, 0, PlatformConstants.SONAR_NUMBER_7, PlatformConstants.SONAR_NUMBER_9, "总监统计员"),
				new HeaderRows(0, 0, PlatformConstants.SONAR_NUMBER_10, PlatformConstants.SONAR_NUMBER_12, "高级经理"),
				new HeaderRows(0, 0, PlatformConstants.SONAR_NUMBER_13, PlatformConstants.SONAR_NUMBER_15, "经理"),
				new HeaderRows(0, 0, PlatformConstants.SONAR_NUMBER_16, PlatformConstants.SONAR_NUMBER_18, "副经理"),
				new HeaderRows(0, 0, PlatformConstants.SONAR_NUMBER_19, PlatformConstants.SONAR_NUMBER_21, "外场调度"),
				new HeaderRows(0, 0, PlatformConstants.SONAR_NUMBER_22, PlatformConstants.SONAR_NUMBER_24, "叉车安全员"),
				new HeaderRows(0, 0, PlatformConstants.SONAR_NUMBER_25, PlatformConstants.SONAR_NUMBER_27, "货区管理员"),
				new HeaderRows(0, 0, PlatformConstants.SONAR_NUMBER_28, PlatformConstants.SONAR_NUMBER_30, "货台管理员"),
				new HeaderRows(0, 0, PlatformConstants.SONAR_NUMBER_31, PlatformConstants.SONAR_NUMBER_33, "文职"),
				new HeaderRows(0, 0, PlatformConstants.SONAR_NUMBER_34, PlatformConstants.SONAR_NUMBER_36, "偏线外发员"),
				new HeaderRows(0, 0, PlatformConstants.SONAR_NUMBER_37, PlatformConstants.SONAR_NUMBER_39, "理货员(含组长)"),
				new HeaderRows(0, 0, PlatformConstants.SONAR_NUMBER_40, PlatformConstants.SONAR_NUMBER_42, "电叉司机(含组长)"),
				new HeaderRows(0, 0, PlatformConstants.SONAR_NUMBER_43, PlatformConstants.SONAR_NUMBER_45, "机叉司机"),
				new HeaderRows(0, 0, PlatformConstants.SONAR_NUMBER_46, PlatformConstants.SONAR_NUMBER_48, "木工"),
				new HeaderRows(0, 0, PlatformConstants.SONAR_NUMBER_49, PlatformConstants.SONAR_NUMBER_51, "修理工"),
				new HeaderRows(0, 0, PlatformConstants.SONAR_NUMBER_52, PlatformConstants.SONAR_NUMBER_54, "分拣员"),
				new HeaderRows(0, 0, PlatformConstants.SONAR_NUMBER_55, PlatformConstants.SONAR_NUMBER_57, "其他"),
				new HeaderRows(0, 0, PlatformConstants.SONAR_NUMBER_58, PlatformConstants.SONAR_NUMBER_60, "合计"),
				new HeaderRows(0, 1, PlatformConstants.SONAR_NUMBER_61, PlatformConstants.SONAR_NUMBER_61, "离职数"),
				new HeaderRows(0, 1, PlatformConstants.SONAR_NUMBER_62, PlatformConstants.SONAR_NUMBER_62, "月累计离职数") };

		ExportResource sheet = new ExportResource();
		sheet.setHeaderHeight(2);

		sheet.setHeaderList(Arrays.asList(headerRows));
		String[] source = { "预算", "实际", "差额" };
		String[] heads = new String[PlatformConstants.SONAR_NUMBER_4];
		for (int i = 0; i <= PlatformConstants.SONAR_NUMBER_18; i++) {
			heads = (String[]) ArrayUtils.addAll(heads, source);
		}
		heads = (String[]) ArrayUtils.addAll(heads, new String[2]);
		sheet.setHeads(heads);
		sheet.setRowList(rowList);

		return sheet;
	}
}
