package com.deppon.foss.module.transfer.management.server.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;








import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.framework.server.components.export.excel.SheetData;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.transfer.management.api.server.dao.IBillListCheckDao;
import com.deppon.foss.module.transfer.management.api.server.service.IBillListCheckService;
import com.deppon.foss.module.transfer.management.api.shared.define.BillListCheckConstants;
import com.deppon.foss.module.transfer.management.api.shared.dto.BillListCheckDto;
import com.deppon.foss.module.transfer.management.api.shared.dto.BillListCheckStaDto;
import com.deppon.foss.module.transfer.management.api.shared.vo.BillListCheckVo;
import com.deppon.foss.module.transfer.scheduling.api.define.ScheduleConstants;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;

/**
 * 电子对账单
 * 
 * @author songjie
 * 
 */
public class BillListCheckService implements IBillListCheckService {

	/**
	 * @fields LOGGER
	 * @author 14022-foss-songjie
	 * @update 2013年11月28日 下午3:43:13
	 * @version V1.0
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(BillListCheckService.class);

	/**
	 * 综合查询Service
	 * 
	 * @fields orgAdministrativeInfoComplexService
	 * @author 14022-foss-songjie
	 * @update 2013年11月28日 下午3:42:47
	 * @version V1.0
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	/**
	 * @fields billListCheckDao
	 * @author 14022-foss-songjie
	 * @update 2013年12月3日 上午10:32:34
	 * @version V1.0
	 */
	private IBillListCheckDao billListCheckDao;
	

	/**
	* @description 分页查询
	* @param map
	* @param start
	* @param limit
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年12月6日 上午10:13:50
	*/
	@SuppressWarnings("rawtypes")
	@Override
	public List<BillListCheckDto> queryBillListCheck(BillListCheckVo billListCheckVo, int start,
			int limit) {
		Map map = packParamComm(billListCheckVo);
		return billListCheckDao.queryBillListCheck(map, start, limit);
	}
	
	
	/**
	* @description 分页查询的总记录数
	* @param billListCheckVo
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年12月6日 上午10:18:12
	*/
	@SuppressWarnings("rawtypes")
	@Override
	public long queryBillListCheckCount(BillListCheckVo billListCheckVo) {
		Map map = packParamComm(billListCheckVo);
		return billListCheckDao.queryBillListCheckCount(map);
	}


	/**
	* @description  分页查询数据的统计
	* @param billListCheckVo
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年12月6日 上午10:18:29
	*/
	@SuppressWarnings("rawtypes")
	@Override
	public BillListCheckStaDto queryBillListCheckStatic(
			BillListCheckVo billListCheckVo) {
		Map map = packParamComm(billListCheckVo);
		return billListCheckDao.queryBillListCheckStatic(map);
	}

	

	/**
	* @description 修改对账单
	* @param vo
	* @throws BusinessException
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年12月6日 下午2:42:38
	*/
	@Override
	public void updateBillListCheckDto(BillListCheckDto dto)
			throws BusinessException {
		dto = handleEmpty(dto);
		dto.setOperateDate(new Date());
		// 计算优惠后金额 = 原金额-优惠金额 燃油费
		dto.setFuelFeeSellAfter(dto.getFuelFee().subtract(dto.getFuelFeeSell()));
		// 计算优惠后金额 = 原金额-优惠金额 路桥费
		dto.setRoadTollFeeSellAfter(dto.getRoadTollFee().subtract(dto.getRoadTollFeeSell()));
		try{
		billListCheckDao.updateBillListCheck(dto);
		}catch(BusinessException be){
			LOGGER.error("updateBillListCheckDto 更新失败");
			throw new BusinessException("更新失败", "更新失败");
		}
	}
	

	@Override
	public void addBillListCheckDto(BillListCheckDto dto)
			throws BusinessException {
		dto = handleEmpty(dto);
		dto.setId(UUIDUtils.getUUID());
		dto.setOperateDate(new Date());
		dto.setQueueID(UUIDUtils.getUUID());
		// 插入对账单日志表
		billListCheckDao.insertBillListCheckLogOne(dto);
		
		// 账单日期
		// 所属事业部
		// 所属车队
		Map<String, Object> mainMap = new HashMap<String, Object>();
		mainMap.put("billDate", dto.getBillDate());
		mainMap.put("divisionOrgCode", dto.getDivisionOrgCode());
		mainMap.put("transDepartmentCode", dto.getTransDepartmentCode());
		BillListCheckDto dbDto = billListCheckDao.queryBillListCheck(mainMap);
		
		if(dbDto!=null){//已经存在了改记录,更新操作
			dto.setId(dbDto.getId());
			//燃油费
			dto.setFuelFee(dto.getFuelFee().add(dbDto.getFuelFee()));
			//燃油费优惠费
			dto.setFuelFeeSell(dto.getFuelFeeSell().add(dbDto.getFuelFeeSell()));
			//路桥费
			dto.setRoadTollFee(dto.getRoadTollFee().add(dbDto.getRoadTollFee()));
			//路桥优惠费
			dto.setRoadTollFeeSell(dto.getRoadTollFeeSell().add(dbDto.getRoadTollFeeSell()));
			
			// 计算优惠后金额 = 原金额-优惠金额 燃油费
			dto.setFuelFeeSellAfter(dto.getFuelFee().subtract(dto.getFuelFeeSell()));
			// 计算优惠后金额 = 原金额-优惠金额 路桥费
			dto.setRoadTollFeeSellAfter(dto.getRoadTollFee().subtract(dto.getRoadTollFeeSell()));
			billListCheckDao.updateBillListCheck(dto);
		}else{
			// 计算优惠后金额 = 原金额-优惠金额 燃油费
			dto.setFuelFeeSellAfter(dto.getFuelFee().subtract(dto.getFuelFeeSell()));
			// 计算优惠后金额 = 原金额-优惠金额 路桥费
			dto.setRoadTollFeeSellAfter(dto.getRoadTollFee().subtract(dto.getRoadTollFeeSell()));
			billListCheckDao.updateBillListCheck(dto);
			dto.setId(UUIDUtils.getUUID());
			billListCheckDao.insertBillListCheckOne(dto);
		}
		
	}


	/**
	 * @param uploadFile
	 * @param uploadFileFileName
	 * @return
	 */
	@Transactional
	public void importFileService(List<BillListCheckDto> excelDtoList,
			BillListCheckVo vo) {
		// 插入对账单日志表
		billListCheckDao.batchInsertBillListCheckLog(excelDtoList);
		// 获取最大账单日期 最小账单日期
		// 所属事业部
		// 所属车队
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("queueID", vo.getBillListCheckDto().getQueueID());
		// 对账单日志进行分组查询
		List<BillListCheckDto> listGroup = billListCheckDao
				.queryBillListCheckLogGroup(map);
		for (BillListCheckDto billListCheckDto : listGroup) {
			// 账单日期
			// 所属事业部
			// 所属车队
			Map<String, Object> mainMap = new HashMap<String, Object>();
			mainMap.put("billDate", billListCheckDto.getBillDate());
			mainMap.put("divisionOrgCode", billListCheckDto.getDivisionOrgCode());
			mainMap.put("transDepartmentCode", billListCheckDto.getTransDepartmentCode());
			BillListCheckDto dbDto = billListCheckDao.queryBillListCheck(mainMap);
			
			billListCheckDto.setOperateDate(new Date());
			billListCheckDto.setDivisionOrgName(vo.getBillListCheckDto().getDivisionOrgName());
			billListCheckDto.setTransDepartmentName(vo.getBillListCheckDto().getTransDepartmentName());
			billListCheckDto.setOperatorCode(vo.getBillListCheckDto().getOperatorCode());
			billListCheckDto.setOperatorName(vo.getBillListCheckDto().getOperatorName());
			
			if(dbDto!=null){//已经存在了改记录,更新操作
				billListCheckDto.setId(dbDto.getId());
				//燃油费
				billListCheckDto.setFuelFee(billListCheckDto.getFuelFee().add(dbDto.getFuelFee()));
				//燃油费优惠费
				billListCheckDto.setFuelFeeSell(billListCheckDto.getFuelFeeSell().add(dbDto.getFuelFeeSell()));
				//路桥费
				billListCheckDto.setRoadTollFee(billListCheckDto.getRoadTollFee().add(dbDto.getRoadTollFee()));
				//路桥优惠费
				billListCheckDto.setRoadTollFeeSell(billListCheckDto.getRoadTollFeeSell().add(dbDto.getRoadTollFeeSell()));
				
				// 计算优惠后金额 = 原金额-优惠金额 燃油费
				billListCheckDto.setFuelFeeSellAfter(billListCheckDto.getFuelFee().subtract(billListCheckDto.getFuelFeeSell()));
				// 计算优惠后金额 = 原金额-优惠金额 路桥费
				billListCheckDto.setRoadTollFeeSellAfter(billListCheckDto
						.getRoadTollFee().subtract(billListCheckDto.getRoadTollFeeSell()));
				billListCheckDao.updateBillListCheck(billListCheckDto);
			}else{
				// 计算优惠后金额 = 原金额-优惠金额 燃油费
				billListCheckDto.setFuelFeeSellAfter(billListCheckDto.getFuelFee().subtract(billListCheckDto.getFuelFeeSell()));
				// 计算优惠后金额 = 原金额-优惠金额 路桥费
				billListCheckDto.setRoadTollFeeSellAfter(billListCheckDto
						.getRoadTollFee().subtract(billListCheckDto.getRoadTollFeeSell()));
				billListCheckDto.setId(UUIDUtils.getUUID());
				billListCheckDao.insertBillListCheckOne(billListCheckDto);
			}
			
		}
	}

	/**
	 * @description 将Excel表格每行数据读入列表
	 * @param excelDtos
	 * @param sheet
	 * @version 1.0
	 * @author 14022-foss-songjie
	 * @update 2013年11月26日 上午8:45:04
	 */
	private Map<String, Date> makeExcelDtos(List<BillListCheckDto> excelDtos,
			Sheet sheet,BillListCheckVo vo)  throws BusinessException{
		Map<String, Date> backMap = null;
		Date minDate = null;
		Date maxDate = null;
		// 判空
		if (sheet != null && excelDtos != null) {
			// 获取物理行数
			int rowCount = sheet.getPhysicalNumberOfRows();
			// 根据行数循环
			Row row = null;
			// 最大列数
			int colCnt = ScheduleConstants.EXCEL_COLUMN_INIT_SIZE;
			// EXCEL行记录
			BillListCheckDto excelDto = null;
			// 根据行数循环
			for (int rowNum = 1; rowNum < rowCount; rowNum++) {
				// 获取每行数据
				excelDto = new BillListCheckDto();
				excelDto.setId(UUIDUtils.getUUID());
				excelDto.setOperateDate(new Date());
				excelDto.setOperatorCode(vo.getBillListCheckDto().getOperatorCode());
				excelDto.setOperatorName(vo.getBillListCheckDto().getOperatorName());
				excelDto.setDivisionOrgCode(vo.getBillListCheckDto().getDivisionOrgCode());
				excelDto.setDivisionOrgName(vo.getBillListCheckDto().getDivisionOrgName());
				excelDto.setTransDepartmentCode(vo.getBillListCheckDto().getTransDepartmentCode());
				excelDto.setTransDepartmentName(vo.getBillListCheckDto().getTransDepartmentName());
				excelDto.setQueueID(vo.getBillListCheckDto().getQueueID());
				// 取得一行的数据
				row = sheet.getRow(rowNum);
				// 如果本行第一列为空，则不继续取值
				// 判空
				if (row == null
						|| row.getCell(ScheduleConstants.EXCEL_COLUMN_FIRST) == null
						|| StringUtils
								.isBlank(obtainStringVal(row
										.getCell(ScheduleConstants.EXCEL_COLUMN_FIRST)))) {
					continue;
				} else {
					// 如果列数小于1
					// 判空
					if (colCnt < ScheduleConstants.EXCEL_COLUMN_SECOND) {
						colCnt = ConstantsNumberSonar.SONAR_NUMBER_5;
					}
					// 循环取列值
					for (int colNum = 0; colNum < colCnt; colNum++) {
						// 由于读取EXCEL效率低下，故先取所有的值，再到内存中校验，提升性能（前提是数据量不大）
						Map<String, Date> tempMap = obtainCellValue(row,
								colNum, excelDto, minDate, maxDate);
						if (tempMap != null) {
							minDate = tempMap.get("minDate");
							maxDate = tempMap.get("maxDate");
						}

					}
				}
				excelDto = handleEmpty(excelDto);
				excelDtos.add(excelDto);
			}
			backMap = new HashMap<String, Date>();
			// 最大和最小的日期
			backMap.put("minDate", minDate);
			backMap.put("maxDate", maxDate);
		}
		return backMap;
	}

	/**
	 * 导入Excle文件
	 * 
	 * @param uploadFile
	 * @param uploadFileName
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public boolean importFileComm(File uploadFile, String uploadFileFileName,
			BillListCheckVo vo)  throws BusinessException {
		boolean backFlag = false;
		// 文件
		Workbook book = null;
		// 输入流
		FileInputStream inputStream = null;
		// 抓取文件类型
		// 文件名不为空
		// 判空 uploadFileName
		if (StringUtils.isNotBlank(uploadFileFileName)) {
			// 判空
			if (uploadFileFileName
					.endsWith(ScheduleConstants.EXCEL_FILE_TAIL_XLS_DOWN)
					|| uploadFileFileName
							.endsWith(ScheduleConstants.EXCEL_FILE_TAIL_XLS_UP)
					|| uploadFileFileName
							.endsWith(ScheduleConstants.EXCEL_FILE_TAIL_XLSX_DOWN)
					|| uploadFileFileName
							.endsWith(ScheduleConstants.EXCEL_FILE_TAIL_XLSX_UP)) {
				// 验证通过
			} else {
				throw new BusinessException("只支持.xls或.xlsx格式的文件,请选择正确的文件导入", "");
				
			}
		}
		// 判空
		if (uploadFile != null) {
			try {
				inputStream = new FileInputStream(uploadFile);
				book = WorkbookFactory.create(inputStream);
				//book = XlsImpUtil.create(inputStream);
			} catch (Exception ex) {
				ex.printStackTrace();
				LOGGER.error("非2003格式文件，转2007格式(ps:非加密文件)");
				throw new BusinessException("非2003格式文件，转2007格式(ps:非加密文件)", "非2003格式文件，转2007格式(ps:非加密文件)");
			} finally {
				// 关闭流文件
				// 判空
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						LOGGER.error(ScheduleConstants.SCHEDULE_EXCEPTION, e);
						throw new BusinessException("文件关闭失败", "");
					}
				}
			}
		} else {
			throw new BusinessException("请选择导入文件", "请选择导入文件");
		}
		List<BillListCheckDto> excelDtoList = null;

		// 解析导入的数据文件
		// 判空
		if (book != null) {
			Sheet sheet = null;
			try {
			// 默认获取获取工作表1
			sheet = book
					.getSheetAt(ScheduleConstants.EXCEL_DEFAULT_SHEET_ONE);
			} catch (Exception e) {
				// 日志
				LOGGER.error("数据异常", e);
				// 异常信息
				throw new BusinessException("数据异常", "");
			} finally {
				// 回收文件数据
				// 判空
				if (book != null) {
					book = null;
				}
			}
			// 读取Excel的所用数据,考虑到数据量不大，暂时不适用集中缓存
			excelDtoList = new ArrayList<BillListCheckDto>();
			// 判断记录总数
			if (sheet != null) {
				// 获取物理行数
				int rowCountMax = sheet.getPhysicalNumberOfRows();
				if (rowCountMax > ConstantsNumberSonar.SONAR_NUMBER_5001) {// 记录超出5千行 返回提示 用户进行分批
					throw new BusinessException("只支持5000行记录的Excel文件导入", "");
				}
			}else{
				throw new BusinessException("Excel工作页1 读取失败");
			}
			
				// 将Excel表格每行数据读入列表
				String queueID = UUIDUtils.getUUID();
				vo.getBillListCheckDto().setQueueID(queueID);
				try{
				Map<String, Date> tempMap = makeExcelDtos(excelDtoList, sheet,vo);
				
				// 最大日期和最小日期
				if (tempMap != null) {
					vo.setBeginDate(tempMap.get("minDate"));
					vo.setEndDate(tempMap.get("maxDate"));
				}
				}catch(BusinessException e){
					throw new BusinessException(e.getMessage());
				}
		
		}
		try{
			importFileService(excelDtoList, vo);
		}catch(Exception e){
			// 日志
			LOGGER.error("数据导入失败", e);
			// 异常信息
			throw new BusinessException("数据导入失败",e);
		}
		backFlag = true;

		return backFlag;
	}

	/**
	 * @description 获取单元格值
	 * @param row
	 * @param colNum
	 * @param excelDto
	 * @version 1.0
	 * @author 14022-foss-songjie
	 * @update 2013年11月26日 上午8:41:36
	 */
	private Map<String, Date> obtainCellValue(Row row, int colNum,
			BillListCheckDto excelDto, Date minDate, Date maxDate)  throws BusinessException {
		Map<String, Date> backMap = null;
		// 判空
		if (row != null) {
			Cell cell = row.getCell(colNum);
			// 判空
			if (cell != null) {
				// 取单元格值
				String cellVal = obtainStringVal(cell);
				// 值不为空，则辨别字段值类型
				// 判空
				if (StringUtils.isNotBlank(cellVal)) {
					// 根据列号设置列值
					switch (colNum) {
					case ScheduleConstants.COL_0:// 账单 日期
						try {
							Date billDate = com.deppon.foss.util.DateUtils
									.convert(
											cellVal,
											com.deppon.foss.util.DateUtils.DATE_FORMAT);
							if(billDate==null){
								throw new BusinessException("日期格式错误");
							}
							excelDto.setBillDate(billDate);
							if (minDate == null) {
								minDate = billDate;
							} else {
								// 早于billDate的日期
								if (billDate.before(minDate)) {
									minDate = billDate;
								}
							}

							if (maxDate == null) {
								maxDate = billDate;
							} else {
								// 晚于billDate的日期
								if (billDate.after(maxDate)) {
									maxDate = billDate;
								}
							}
							backMap = new HashMap<String, Date>();
							backMap.put("minDate", minDate);
							backMap.put("maxDate", maxDate);
						} catch (Exception e) {
							LOGGER.error("账单 日期错误", e);
							throw new BusinessException("日期处理错误");
						}
						return backMap;
					case ScheduleConstants.COL_1:// 燃油费
						excelDto.setFuelFee(convertToBigDecimal(cellVal));
						return null;
					case ScheduleConstants.COL_2:// 燃油优惠金额
						excelDto.setFuelFeeSell(convertToBigDecimal(cellVal));
						return null;
					case ScheduleConstants.COL_3:// 路桥费
						excelDto.setRoadTollFee(convertToBigDecimal(cellVal));
						return null;
					case ScheduleConstants.COL_4:// 路桥优惠金额
						excelDto.setRoadTollFeeSell(convertToBigDecimal(cellVal));
						return null;
					default:
						return null;
					}
				}

			}
		}
		return backMap;
	}

	/**
	 * @description 单元格取值
	 * @param cell
	 * @return
	 * @version 1.0
	 * @author 14022-foss-songjie
	 * @update 2013年11月26日 上午8:44:10
	 */
	private String obtainStringVal(Cell cell) {
		// 列值
		String cellVal = "";
		// 单元格类型
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_NUMERIC: // 数值型
			// 判空
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				// 如果是date类型则 ，获取该cell的date值
				cellVal = com.deppon.foss.util.DateUtils.convert(
						HSSFDateUtil.getJavaDate(cell.getNumericCellValue()),
						com.deppon.foss.util.DateUtils.DATE_FORMAT);
			} else {// 纯数字
				cellVal = String.valueOf(cell.getNumericCellValue());
			}
			break;
		// 此行表示单元格的内容为string类型
		case HSSFCell.CELL_TYPE_STRING: // 字符串型
			cellVal = cell.getRichStringCellValue().toString();
			break;
		case HSSFCell.CELL_TYPE_FORMULA:// 公式型
			try {
				// 读公式计算值
				cellVal = String.valueOf(cell.getNumericCellValue());
			} catch (Exception e) {
				cellVal = String.valueOf(cell.getStringCellValue());
			}
			// 判空
			if (ScheduleConstants.NAN.equals(cellVal)) {// 如果获取的数据值为非法值,则转换为获取字符串
				cellVal = cell.getRichStringCellValue().toString();
			}
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:// 布尔
			cellVal = " " + cell.getBooleanCellValue();
			break;
		// 此行表示该单元格值为空
		case HSSFCell.CELL_TYPE_BLANK: // 空值
		case HSSFCell.CELL_TYPE_ERROR: // 故障
			cellVal = "";
			break;
		default:
			cellVal = cell.getRichStringCellValue().toString();
		}
		return cellVal.trim();
	}

	/**
	 * @description 将字符串转化为long
	 * @param longStr
	 * @return
	 * @version 1.0
	 * @author 14022-foss-songjie
	 * @update 2013年11月26日 上午8:44:27
	 */
	private BigDecimal convertToBigDecimal(String longStr)  throws BusinessException {
		try {
			if (StringUtils.isNotBlank(longStr)) {
				longStr = longStr.trim();
				BigDecimal number = new BigDecimal(longStr);
				return number;
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new BusinessException("输入非法数字");
		}
	}
	
	
	/**
	* @description 判断BigDecimal类型的字段是否为空
	* @param dto
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年12月6日 下午5:45:17
	*/
	private BillListCheckDto handleEmpty(BillListCheckDto dto){
		if(dto!=null){
			if(dto.getFuelFee()==null) {
				dto.setFuelFee(new BigDecimal(0));
			}
			if(dto.getFuelFeeSell()==null) {
				dto.setFuelFeeSell(new BigDecimal(0));
			}
			if(dto.getFuelFeeSellAfter()==null) {
				dto.setFuelFeeSellAfter(new BigDecimal(0));
			}
			if(dto.getRoadTollFee()==null) {
				dto.setRoadTollFee(new BigDecimal(0));
			}
			if(dto.getRoadTollFeeSell()==null) {
				dto.setRoadTollFeeSell(new BigDecimal(0));
			}
			if(dto.getRoadTollFeeSellAfter()==null) {
				dto.setRoadTollFeeSellAfter(new BigDecimal(0));
			}
			return dto;
		}else{
			return null;	
		}
	}
	
	
	/**
	 * @description 封装当前部门的属性
	 * @param orgAdministrativeInfoEntity
	 * @param billListCheckVo
	 * @return
	 * @version 1.0
	 * @author 14022-foss-songjie
	 * @update 2013年11月28日 下午3:38:24
	 */
	@Override
	public BillListCheckVo queryCurrentInfo(
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity,
			BillListCheckVo billListCheckVo) {
		// 车队的名称和code
		/*
		 * oaif.getCode(); oaif.getName();
		 */
		// 对应的事业部 名称和code
		billListCheckVo
				.setCurrentOrgCode(orgAdministrativeInfoEntity.getCode());
		billListCheckVo
				.setCurrentOrgName(orgAdministrativeInfoEntity.getName());

		List<String> bizTypesList = new ArrayList<String>();
		bizTypesList.add(BizTypeConstants.ORG_DIVISION);
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity2 = orgAdministrativeInfoComplexService
				.queryOrgAdministrativeInfoIncludeSelfByCode(
						orgAdministrativeInfoEntity.getCode(), bizTypesList);

		if (orgAdministrativeInfoEntity2 != null) {
			billListCheckVo.setCurrentCareerCode(orgAdministrativeInfoEntity2
					.getCode());
			billListCheckVo.setCurrentCareerName(orgAdministrativeInfoEntity2
					.getName());
		}

		// 判断是否为车队
//		if (orgAdministrativeInfoEntity.checkDispatchTeam()
//				|| orgAdministrativeInfoEntity.checkTransDepartment()
//				|| orgAdministrativeInfoEntity.checkTransTeam()) {
//			billListCheckVo.setIsTransTeam(BillListCheckConstants.YES);
		if (orgAdministrativeInfoEntity.checkTransDepartment()) {
			billListCheckVo.setIsTransTeam(BillListCheckConstants.YES);
		} else {
			billListCheckVo.setIsTransTeam(BillListCheckConstants.NO);
		}
		return billListCheckVo;
	}

	/**
	 * @description 导出Excel
	 * @param billListCheckVo
	 * @return
	 * @version 1.0
	 * @author 14022-foss-songjie
	 * @update 2013年11月29日 下午3:37:28
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public InputStream exportExcelStream(BillListCheckVo billListCheckVo) {
		InputStream excelStream = null;
		try {
			List<BillListCheckDto> billListCheckDtoList;
			// 查询对账单表 BillListCheckDto
			Map map = packParamComm(billListCheckVo);
			billListCheckDtoList = billListCheckDao.exportBillListCheck(map);

			// 行List
			List<List<String>> rowList = new ArrayList<List<String>>();
			if (billListCheckDtoList != null) {
				for (BillListCheckDto dto : billListCheckDtoList) {
					// 每行的列List
					List<String> columnList = new ArrayList<String>();
					columnList.add(DateUtils.convert(dto.getBillDate(),
							DateUtils.DATE_FORMAT));
					columnList.add(dto.getDivisionOrgName() + "");
					columnList.add(dto.getTransDepartmentName() + "");
					columnList.add(dto.getFuelFee() + "");
					columnList.add(dto.getFuelFeeSell() + "");
					columnList.add(dto.getRoadTollFee() + "");
					columnList.add(dto.getRoadTollFeeSell() + "");
					rowList.add(columnList);
				}
			}
			//汇总:
			BillListCheckStaDto staDTo = billListCheckDao.queryBillListCheckStatic(map);
			if(staDTo!=null){
				//汇总列
				List<String> columnStaList = new ArrayList<String>();
				columnStaList.add("");
				columnStaList.add("");
				columnStaList.add("合计：");
				columnStaList.add(staDTo.getFuelFeeTotal()+"");
				columnStaList.add(staDTo.getFuelFeeSellTotal()+"");
				columnStaList.add(staDTo.getRoadTollFeeTotal()+"");
				columnStaList.add(staDTo.getRoadTollFeeSellTotal()+"");
				rowList.add(columnStaList);
			}
			
			
			SheetData sheetData = new SheetData();
			sheetData.setRowHeads(BillListCheckConstants.ROW_HEADS);
			sheetData.setRowList(rowList);

			ExcelExport excelExportUtil = new ExcelExport();
			excelStream = excelExportUtil.inputToClient(excelExportUtil
					.exportExcel(sheetData, BillListCheckConstants.SHEET_NAME,
							BillListCheckConstants.SHEET_SIZE));
		} catch (BusinessException e) {
			LOGGER.error("导出对账单异常", e);
			throw new BusinessException("导出对账单异常", e);
		}
		return excelStream;
	}
	
	
	/**
	 * 下载模板
	 * @param uploadFile
	 * @param uploadFileName
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public InputStream downFileModel() throws BusinessException {
		InputStream excelStream = null;
		try {
			// 行List
			List<List<String>> rowList = new ArrayList<List<String>>();
			
			//举例
			List<String> columnList = new ArrayList<String>();
			columnList.add("2013/12/17");
			columnList.add("1000000");
			columnList.add("0");
			columnList.add("5000");
			columnList.add("0");
			rowList.add(columnList);
			
			SheetData sheetData = new SheetData();
			sheetData.setRowHeads(BillListCheckConstants.ROW_HEADS_MODEL);
			sheetData.setRowList(rowList);
			
			ExcelExport excelExportUtil = new ExcelExport();
			excelStream = excelExportUtil.inputToClient(excelExportUtil
					.exportExcel(sheetData, BillListCheckConstants.SHEET_NAME,
							BillListCheckConstants.SHEET_SIZE));
		} catch (BusinessException e) {
			LOGGER.error("电子对账单模板下载异常", e);
			throw new BusinessException("电子对账单模板下载异常", e);
		}
		return excelStream;
	}


	/**
	 * @description 参数封装1
	 * @param beginDate
	 * @param endDate
	 * @param divisionOrgCode
	 * @param transDepartmentCode
	 * @return
	 * @version 1.0
	 * @author 14022-foss-songjie
	 * @update 2013年12月4日 上午9:47:30
	 */
	private Map<String, Object> packParamComm(BillListCheckVo billListCheckVo) {
		// 获取最大账单日期 最小账单日期
		// 所属事业部
		// 所属车队
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("beginDate", billListCheckVo.getBeginDate());
		map.put("endDate", billListCheckVo.getEndDate());
		map.put("divisionOrgCode", billListCheckVo.getDivisionOrgCode());
		map.put("transDepartmentCode", billListCheckVo.getTransDepartmentCode());
		return map;
	}

	public IOrgAdministrativeInfoComplexService getOrgAdministrativeInfoComplexService() {
		return orgAdministrativeInfoComplexService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public IBillListCheckDao getBillListCheckDao() {
		return billListCheckDao;
	}

	public void setBillListCheckDao(IBillListCheckDao billListCheckDao) {
		this.billListCheckDao = billListCheckDao;
	}

}
