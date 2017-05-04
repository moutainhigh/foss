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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.platform.api.server.dao.ITfrCtrAbsenteeInfoDao;
import com.deppon.foss.module.transfer.platform.api.server.service.ITfrCtrAbsenteeInfoService;
import com.deppon.foss.module.transfer.platform.api.shared.define.PlatformConstants;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrCtrAbsenteeInfoEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TfrCtrAbsenteeInfoQcDto;
import com.deppon.foss.module.transfer.platform.api.shared.utils.PlatformUtils;
import com.deppon.foss.util.UUIDUtils;

/**
 * @author Ouyang
 * 
 */
public class TfrCtrAbsenteeInfoService implements ITfrCtrAbsenteeInfoService {

	private ITfrCtrAbsenteeInfoDao tfrCtrAbsenteeInfoDao;

	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;


	public void setTfrCtrAbsenteeInfoDao(
			ITfrCtrAbsenteeInfoDao tfrCtrAbsenteeInfoDao) {
		this.tfrCtrAbsenteeInfoDao = tfrCtrAbsenteeInfoDao;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
	 * 校验异常时间正确性
	 * @param entity
	 * @date 2014-3-9
	 * @author Ouyang
	 */
	private void validatAbsentDate(TfrCtrAbsenteeInfoEntity entity) {
		if (entity.getAbsentEndDate().before(entity.getAbsentBeginDate())) {
			throw new TfrBusinessException("请正确选择异常时间范围！");
		}
	}

	/**
	 * 校验数据唯一性
	 * @param entity
	 * @date 2014-3-9
	 * @author Ouyang
	 */
	private void validateUnique(TfrCtrAbsenteeInfoEntity entity) {

		TfrCtrAbsenteeInfoEntity absenteeInfo = tfrCtrAbsenteeInfoDao
				.select1TfrCtrAbsenteeInfo(entity);

		if (absenteeInfo != null) {
			throw new TfrBusinessException("员工"
					+ absenteeInfo.getAbsenteeCode() + "在异常时间"
					+ String.format("%1$tF", absenteeInfo.getAbsentBeginDate())
					+ "至"
					+ String.format("%1$tF", absenteeInfo.getAbsentEndDate())
					+ "已存在异常记录");
		}
	}

	/**
	 * 新增/修改前校验
	 * @param entity
	 * @date 2014-3-9
	 * @author Ouyang
	 */
	private void validateBeforeSubmit(TfrCtrAbsenteeInfoEntity entity) {
		validatAbsentDate(entity);
		validateUnique(entity);
	}

	/**
	 * 校验员工是否属于本外场
	 * @param entity
	 * @date 2014-3-10
	 * @author Ouyang
	 */
	private void validateUserTfrCtr(TfrCtrAbsenteeInfoEntity entity) {
		// 员工部门
		String absenteeOrgCode = entity.getAbsenteeOrgCode();
		// 获取员工所在部门所属外场
		Map<String, String> tfrCtr = queryParentTfrCtrCode(absenteeOrgCode);
		if (tfrCtr == null) {
			throw new TfrBusinessException("员工" + entity.getAbsenteeCode()
					+ "不属于本外场！");
		}

		// 外场code
		String transferCenterCode = tfrCtr.get("code");

		if (!StringUtils.equals(transferCenterCode,
				entity.getTransferCenterCode())) {
			throw new TfrBusinessException("员工" + entity.getAbsenteeCode()
					+ "不属于本外场！");
		}
	}

	/**
	 * 新增外场异常人员信息
	 * @param entity
	 * @date 2014-2-26
	 * @author Ouyang
	 */
	@Override
	public void insertTfrCtrAbsenteeInfo(TfrCtrAbsenteeInfoEntity entity) {

		if (entity == null) {
			throw new TfrBusinessException("请将外场人员异常信息填写完整。");
		}

		// 设置创建人，创建时间，修改人，修改时间
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		String userCode = currentInfo.getEmpCode();
		String userName = currentInfo.getEmpName();
		Date nowDate = new Date();

		entity.setCreateUser(userCode);
		entity.setCreateUserName(userName);
		entity.setCreateDate(nowDate);
		entity.setModifyUser(userCode);
		entity.setModifyUserName(userName);
		entity.setModifyDate(nowDate);

		entity.setId(UUIDUtils.getUUID());

		String nullFiled = PlatformUtils.getNullField(entity);
		if (nullFiled != null) {
			throw new TfrBusinessException("请将外场人员异常信息填写完整。 " + nullFiled);
		}

		// 将异常开始时间设置为传入日期的00:00:00
		entity.setAbsentBeginDate(PlatformUtils.getFirstMomentOfDay(entity
				.getAbsentBeginDate()));

		// 将异常结束时间设置为传入日期的23:59:59
		entity.setAbsentEndDate(PlatformUtils.getLastMomentOfDay(entity
				.getAbsentEndDate()));
		// 校验员工是否属于本外场
		validateUserTfrCtr(entity);
		// 保存前校验
		validateBeforeSubmit(entity);

		tfrCtrAbsenteeInfoDao.insertTfrCtrAbsenteeInfo(entity);
	}

	/**
	 * 新增外场异常人员信息
	 * @param entities
	 * @date 2014-2-26
	 * @author Ouyang
	 */
	@Override
	@Transactional
	public void insertTfrCtrAbsenteeInfos(
			List<TfrCtrAbsenteeInfoEntity> entities) {

		for (TfrCtrAbsenteeInfoEntity entity : entities) {
			insertTfrCtrAbsenteeInfo(entity);
		}
	}

	/**
	 * 批量删除外场人员信息
	 * @param ids
	 * @date 2014-2-26
	 * @author Ouyang
	 */
	@Override
	public void deleteTfrCtrAbsenteeInfos(List<String> ids) {
		if (!CollectionUtils.isEmpty(ids)) {
			tfrCtrAbsenteeInfoDao.deleteTfrCtrAbsenteeInfos(ids);
		}
	}

	/**
	 * 修改外场异常人员信息
	 * @param entity
	 * @date 2014-2-26
	 * @author Ouyang
	 */
	@Override
	public void updateTfrCtrAbsenteeInfo(TfrCtrAbsenteeInfoEntity entity) {

		if (entity == null) {
			throw new TfrBusinessException("请选择待修改的记录。");
		}

		// 设置修改人、修改时间
		CurrentInfo user = FossUserContext.getCurrentInfo();
		String userCode = user.getEmpCode();
		String userName = user.getEmpName();
		Date nowDate = new Date();

		entity.setModifyUser(userCode);
		entity.setModifyUserName(userName);
		entity.setModifyDate(nowDate);

		// 将异常开始时间设置为传入日期的00:00:00
		entity.setAbsentBeginDate(PlatformUtils.getFirstMomentOfDay(entity
				.getAbsentBeginDate()));

		// 将异常结束时间设置为传入日期的23:59:59
		entity.setAbsentEndDate(PlatformUtils.getLastMomentOfDay(entity
				.getAbsentEndDate()));

		validateBeforeSubmit(entity);

		if (tfrCtrAbsenteeInfoDao.updateTfrCtrAbsenteeInfo(entity) == 0) {
			throw new TfrBusinessException("待修改的记录不存在，id = " + entity.getId());
		}
	}

	/**
	 * 验证查询条件
	 * @return
	 * @date 2014-2-26
	 * @author Ouyang
	 */
	private void validateQueryCondition(TfrCtrAbsenteeInfoQcDto qcDto) {
		if (qcDto == null || qcDto.getTransferCenterCode() == null) {
			throw new TfrBusinessException("请正确选择查询条件！");
		}

		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(qcDto.getCreateBeginDate());
		calendar.add(Calendar.DAY_OF_YEAR,
				PlatformConstants.QUERY_DATE_SPAN_1MONTH);
		if (qcDto.getCreateEndDate().after(calendar.getTime())) {
			throw new TfrBusinessException("请正确选择查询条件！");
		}
	}

	/**
	 * 查询外场异常人员信息
	 * @param qcDto
	 * @return
	 * @date 2014-2-26
	 * @author Ouyang
	 */
	@Override
	public List<TfrCtrAbsenteeInfoEntity> queryTfrCtrAbsenteeInfos(
			TfrCtrAbsenteeInfoQcDto qcDto) {

		// 验证查询条件
		validateQueryCondition(qcDto);

		return tfrCtrAbsenteeInfoDao.queryTfrCtrAbsenteeInfos(qcDto);
	}

	/**
	 * 分页查询外场异常人员信息
	 * @param qcDto
	 * @param start
	 * @param limit
	 * @return
	 * @date 2014-2-26
	 * @author Ouyang
	 */
	@Override
	public List<TfrCtrAbsenteeInfoEntity> queryPagingTfrCtrAbsenteeInfos(
			TfrCtrAbsenteeInfoQcDto qcDto, int start, int limit) {

		validateQueryCondition(qcDto);

		return tfrCtrAbsenteeInfoDao.queryPagingTfrCtrAbsenteeInfos(qcDto,start, limit);
	}

	/**
	 * 查询外场异常人员信息总数
	 * @param qcDto
	 * @return
	 * @date 2014-2-26
	 * @author Ouyang
	 */
	@Override
	public Long queryTfrCtrAbsenteeInfoCount(TfrCtrAbsenteeInfoQcDto qcDto) {
		return tfrCtrAbsenteeInfoDao.queryTfrCtrAbsenteeInfoCount(qcDto);
	}

	/**
	 * 根据id查询外场异常人员信息
	 * @param id
	 * @return
	 * @date 2014-2-26
	 * @author Ouyang
	 */
	@Override
	public TfrCtrAbsenteeInfoEntity queryTfrCtrAbsenteeInfoById(String id) {
		return tfrCtrAbsenteeInfoDao.queryTfrCtrAbsenteeInfoById(id);
	}

	/**
	 * 返回当前部门所属外场
	 * @return
	 * @date 2014-2-26
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
	 * 导出excel
	 * @param ids 选择中行记录的id集合
	 * @param qcDto 非选中条件下，导出的查询条件
	 * @return
	 * @date 2014-2-26
	 * @author Ouyang
	 */
	@Override
	public ExportResource exportTfrCtrAbsenteeInfos(List<String> ids,
			TfrCtrAbsenteeInfoQcDto qcDto) {

		List<TfrCtrAbsenteeInfoEntity> tfrCtrAbsenteeInfoEntities = null;

		// 判断是否选中记录导出
		if (CollectionUtils.isEmpty(ids)) {
			try {
				// 验证查询条件
				validateQueryCondition(qcDto);
			} catch (TfrBusinessException e) {
				ExportResource sheet = new ExportResource();
				List<List<String>> rowList = new ArrayList<List<String>>();
				sheet.setRowList(rowList);
				return sheet;
			}

			// 非选中条件下，查询外场异常人员信息
			tfrCtrAbsenteeInfoEntities = queryTfrCtrAbsenteeInfos(qcDto);

		} else {
			tfrCtrAbsenteeInfoEntities = new ArrayList<TfrCtrAbsenteeInfoEntity>();

			// 选中条件下， 根据选中记录id查询外场异常人员信息
			for (String id : ids) {
				TfrCtrAbsenteeInfoEntity tfrCtrAbsenteeInfoEntity = tfrCtrAbsenteeInfoDao
						.queryTfrCtrAbsenteeInfoById(id);
				if (tfrCtrAbsenteeInfoEntity != null) {
					tfrCtrAbsenteeInfoEntities.add(tfrCtrAbsenteeInfoEntity);
				}
			}
		}

		List<List<String>> rowList = new ArrayList<List<String>>();
		List<String> result = null;

		String[] codes = PlatformConstants.ABSENTEESTATUS_CODES;
		String[] values = PlatformConstants.ABSENTEESTATUS_VALUES;

		for (TfrCtrAbsenteeInfoEntity entity : tfrCtrAbsenteeInfoEntities) {
			result = new ArrayList<String>();

			// 外场
			result.add(entity.getTransferCenterName());
			// 部门
			result.add(entity.getAbsenteeOrgName());
			// 姓名
			result.add(entity.getAbsenteeName());
			// 工号
			result.add(entity.getAbsenteeCode());
			// 岗位
			result.add(entity.getAbsenteePostName());
			// 入职日期
			result.add(String.format("%1$tF", entity.getAbsenteeEntryDate()));
			// 异常开始时间
			result.add(String.format("%1$tF", entity.getAbsentBeginDate()));
			// 异常结束时间
			result.add(String.format("%1$tF", entity.getAbsentEndDate()));
			// 异常状态
			for (int i = 0; i < codes.length; i++) {
				if (StringUtils.equals(entity.getAbsenteeStatus(), codes[i])) {
					result.add(values[i]);
					break;
				}
				if (i == codes.length - 1) {
					result.add(codes[i]);
				}
			}
			// 创建人
			result.add(entity.getCreateUserName());
			// 录入时间
			result.add(String.format("%1$tF %2$tT", entity.getCreateDate(),
					entity.getCreateDate()));
			// 修改人
			result.add(entity.getModifyUserName());
			// 修改时间
			result.add(String.format("%1$tF %2$tT", entity.getModifyDate(),
					entity.getModifyDate()));

			rowList.add(result);
		}
		ExportResource sheet = new ExportResource();
		sheet.setHeads(PlatformConstants.TFRCTRABSENTEEINFO_EXCEL_TITLE);
		sheet.setRowList(rowList);
		return sheet;
	}

	/**
	 * 批量导入
	 * @param uploadFileName
	 * @param uploadFile
	 * @date 2014-2-26
	 * @author Ouyang
	 */
	@Override
	@Transactional
	public void importTfrCtrAbsenteeInfos(String uploadFileName, File uploadFile) {
		// 获取当前部门所属外场
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		Map<String, String> tfrCtr = queryParentTfrCtrCode(currentDeptCode);
		if (tfrCtr == null) {
			throw new TfrBusinessException("请在外场或外场子部门进行外场异常人员信息录入！");
		}

		// 获取当前部门所属外场code
		String transferCenterCode = tfrCtr.get("code");

		// 获取当前用户，用于设置创建（修改）人（时间）
		CurrentInfo user = FossUserContext.getCurrentInfo();
		String userCode = user.getEmpCode();
		String userName = user.getEmpName();
		Date nowDate = new Date();

		if (StringUtils.isEmpty(uploadFileName) || uploadFile == null) {
			throw new TfrBusinessException("请选择待导入的文件！");
		}

		String suffix = uploadFileName.substring(
				uploadFileName.lastIndexOf(".")).toLowerCase();
		if (!PlatformConstants.EXCEL_FILE_EXT_03.equals(suffix)
				&& !PlatformConstants.EXCEL_FILE_EXT_07.equals(suffix)) {
			throw new TfrBusinessException("请正确选择.xls或.xlsx格式的excel文件！");
		}

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
		// 模版中第一为模版说明、第二行为表头;分别为工号、异常状态、异常开始时间、异常结束时间
		if (rownum <= PlatformConstants.EXCEL_TEMPLATE_HEADER_NUM) {
			throw new TfrBusinessException("请在待导入 的excel中填写相关数据！");
		}

		// 用于表示第i行
		Row row = null;
		// 用于表示第i行中的列数
		int cellnum = 0;

		List<TfrCtrAbsenteeInfoEntity> entities = new ArrayList<TfrCtrAbsenteeInfoEntity>();

		for (int i = PlatformConstants.EXCEL_TEMPLATE_HEADER_NUM; i < rownum; i++) {
			row = sheet.getRow(i);
			if (row == null) {
				throw new TfrBusinessException("第" + (i + 1)
						+ "行数据为空，请将该行删除后重试！");
			}
			cellnum = row.getLastCellNum();
			if (cellnum < PlatformConstants.EXCEL_TEMPLATE_CLOUMN_NUM) {
				throw new TfrBusinessException("第" + (i + 1)
						+ "行数据与模版不匹配，请按模版导入数据！");
			}

			// 用于表示第i行j列
			Cell cell = null;
			// 用于表示第i行j列的值
			String cellValue = null;
			// 将各行数据转换成对象
			TfrCtrAbsenteeInfoEntity entity = new TfrCtrAbsenteeInfoEntity();

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			DecimalFormat numberFormat = new DecimalFormat("0");

			for (int j = 0; j < PlatformConstants.EXCEL_TEMPLATE_CLOUMN_NUM; j++) {
				cell = row.getCell(j);
				// 将excel中的数据转换成String
				cellValue = PlatformUtils.parseExcelCell(cell, dateFormat,
						numberFormat);

				// 设置工号、姓名、部门、岗位、入职时间、异常状态、异常时间
				setTfrCtrAbsenteeInfoPropsByCell(entity, j, cellValue, i);
			}

			// 设置id、修改人、修改时间
			entity.setId(UUIDUtils.getUUID());
			entity.setCreateUser(userCode);
			entity.setCreateUserName(userName);
			entity.setCreateDate(nowDate);
			entity.setModifyUser(userCode);
			entity.setModifyUserName(userName);
			entity.setModifyDate(nowDate);

			// 校验员工是否属于当前外场
			if (!StringUtils.equals(transferCenterCode,
					entity.getTransferCenterCode())) {
				throw new TfrBusinessException("第" + (i + 1)
						+ "行员工不属于当前外场，请选择本外场员工进行录入！");
			}

			// 判断excel中的同一工号异常时间段是否有交叉
			validateOverlap(entities, entity);

			entities.add(entity);
		}

		// 校验excel中的各工号异常时间端是否与数据库中的数据有交叉
		validatUnique4DB(entities);

		// 保存数据
		for (TfrCtrAbsenteeInfoEntity entity : entities) {
			tfrCtrAbsenteeInfoDao.insertTfrCtrAbsenteeInfo(entity);
		}
	}

	/**
	 * 判断excel中的同一工号异常时间段是否有交叉
	 * @param existsEntities 已经存在在list中的对象
	 * @param tbaEntity 带添加的对象
	 * @date 2014-3-10
	 * @author Ouyang
	 */
	private void validateOverlap(List<TfrCtrAbsenteeInfoEntity> existsEntities,
			TfrCtrAbsenteeInfoEntity tbaEntity) {

		// 待添加异常人员code
		String tbaAbsenteeCode = tbaEntity.getAbsenteeCode();
		// 待添加异常人员异常开始时间
		Date tbaAbsentBeginDate = tbaEntity.getAbsentBeginDate();
		// 待添加异常人员异常结束时间
		Date tbaAbsentEndDate = tbaEntity.getAbsentEndDate();

		// 已经存在的异常人员code
		String existsAbsenteeCode = null;
		// 已存在异常人员异常开始时间
		Date existsAbsentBeginDate = null;
		// 已存在添加异常人员异常结束时间
		Date existsAbsentEndDate = null;

		for (TfrCtrAbsenteeInfoEntity entity : existsEntities) {
			existsAbsenteeCode = entity.getAbsenteeCode();
			if (StringUtils.equals(tbaAbsenteeCode, existsAbsenteeCode)) {

				existsAbsentBeginDate = entity.getAbsentBeginDate();
				existsAbsentEndDate = entity.getAbsentEndDate();

				if (overlap(tbaAbsentBeginDate, tbaAbsentEndDate,
						existsAbsentBeginDate, existsAbsentEndDate)) {
					throw new TfrBusinessException("存在工号" + tbaAbsenteeCode
							+ "异常时间段交叉的数据！");
				}
			}
		}

	}

	/**
	 * 判断两时间段是否交叉
	 * @param beginDate1
	 * @param endDate1
	 * @param beginDate2
	 * @param endDate2
	 * @return
	 * @date 2014-3-9
	 * @author Ouyang
	 */
	private boolean overlap(Date beginDate1, Date endDate1, Date beginDate2,
			Date endDate2) {
		long beginTime1 = beginDate1.getTime();
		long endTime1 = endDate1.getTime();
		long beginTime2 = beginDate2.getTime();
		long endTime2 = endDate2.getTime();

		if ((beginTime1 >= beginTime2 && beginTime1 <= endTime2)
				|| (endTime1 >= beginTime2 && endTime1 <= endTime2)) {
			return true;
		}

		if ((beginTime2 >= beginTime1 && beginTime2 <= endTime1)
				|| (endTime2 >= beginTime1 && endTime2 <= endTime1)) {
			return true;
		}

		return false;
	}

	/**
	 * 校验excel中的各工号异常时间端是否与数据库中的数据有交叉
	 * @param entities
	 * @date 2014-3-9
	 * @author Ouyang
	 */
	private void validatUnique4DB(List<TfrCtrAbsenteeInfoEntity> entities) {
		for (TfrCtrAbsenteeInfoEntity entity : entities) {
			validateUnique(entity);
		}
	}

	/**
	 * 根据excel中各cell的值填充外场异常信息属性
	 * @param entity
	 * @param cellnum
	 * @param cellValue
	 * @param rownum
	 * @date 2014-3-9
	 * @author Ouyang
	 */
	private void setTfrCtrAbsenteeInfoPropsByCell(
			TfrCtrAbsenteeInfoEntity entity, int cellnum, String cellValue,
			int rownum) {

		if (StringUtils.isBlank(cellValue)) {
			throw new TfrBusinessException("请正确填写第" + (rownum + 1) + "行，第"
					+ (cellnum + 1) + "列的值！");
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		switch (cellnum) {
		case 0:// 工号
				// 根据工号查询员工信息，得到姓名、部门、岗位、入职时间、所属外场
			EmployeeEntity employeeEntity = tfrCtrAbsenteeInfoDao
					.queryEmployeeByEmpCode(cellValue);

			if (employeeEntity == null) {
				throw new TfrBusinessException("不存在工号为" + cellValue + "的员工信息！");
			}

			String absenteeName = employeeEntity.getEmpName();
			String absenteePostCode = employeeEntity.getTitle();
			String absenteePostName = employeeEntity.getTitleName();
			/*OrgAdministrativeInfoEntity absenteeOrg = employeeEntity
					.getDepartment();*/
			String absenteeOrgCode = employeeEntity.getOrgCode();
			String absenteeOrgName = employeeEntity.getOrgName();

			entity.setAbsenteeCode(cellValue);
			entity.setAbsenteeName(absenteeName);
			entity.setAbsenteePostCode(absenteePostCode);
			entity.setAbsenteePostName(absenteePostName);
			entity.setAbsenteeOrgCode(absenteeOrgCode);
			entity.setAbsenteeOrgName(absenteeOrgName);
			entity.setAbsenteeEntryDate(employeeEntity.getEntryDate());

			// 获取员工所在部门所属外场
			Map<String, String> parentTfrCtr = queryParentTfrCtrCode(absenteeOrgCode);
			if (parentTfrCtr == null) {
				throw new TfrBusinessException("工号为" + cellValue + "的员工不属于某外场！");
			}
			// 外场code
			String transferCenterCode = parentTfrCtr.get("code");
			// 外场name
			String transferCenterName = parentTfrCtr.get("name");

			entity.setTransferCenterCode(transferCenterCode);
			entity.setTransferCenterName(transferCenterName);
			
			
			break;
		case 1:// 异常状态
			for (int i = 0; i < PlatformConstants.ABSENTEESTATUS_CODES.length; i++) {
				if (PlatformConstants.ABSENTEESTATUS_VALUES[i]
						.equals(cellValue)) {
					entity.setAbsenteeStatus(PlatformConstants.ABSENTEESTATUS_CODES[i]);
					break;
				}
				if (i == PlatformConstants.ABSENTEESTATUS_CODES.length - 1) {
					throw new TfrBusinessException("请正确填写第" + (rownum + 1) + "行，第"
							+ (cellnum + 1) + "列的值！");
				}
			}
			break;
		case 2:// 异常开始时间
			Date absentBeginDate = null;
			try {
				absentBeginDate = dateFormat.parse(cellValue);
			} catch (ParseException e) {
				throw new TfrBusinessException("请正确填写第" + (rownum + 1) + "行，第"
						+ (cellnum + 1) + "列的值！");
			}
			entity.setAbsentBeginDate(PlatformUtils
					.getFirstMomentOfDay(absentBeginDate));
			break;
		case PlatformConstants.SONAR_NUMBER_3:// 异常结束时间
			Date absentEndDate = null;
			try {
				absentEndDate = dateFormat.parse(cellValue);
			} catch (ParseException e) {
				throw new TfrBusinessException("请正确填写第" + (rownum + 1) + "行，第"
						+ (cellnum + 1) + "列的值！");
			}
			entity.setAbsentEndDate(PlatformUtils
					.getLastMomentOfDay(absentEndDate));
			break;
		default:
			break;
		}
	}
	/**
	 * 分页查询外场人员异常信息 不分页
	 * @param qcDto
	 * @return 外场人员异常信息集合
	 * @date 2015-01-22
	 * @author wqh
	 */
	public List<TfrCtrAbsenteeInfoEntity> queryPagingTfrCtrAbsenteeInfos(TfrCtrAbsenteeInfoQcDto qcDto)
	{
		
		validateQueryCondition(qcDto);
		return tfrCtrAbsenteeInfoDao.queryPagingTfrCtrAbsenteeInfos(qcDto);
		

	}

}
