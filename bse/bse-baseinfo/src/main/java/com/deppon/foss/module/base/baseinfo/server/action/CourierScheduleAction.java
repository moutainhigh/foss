/** 
 * 
 */
package com.deppon.foss.module.base.baseinfo.server.action;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICourierScheduleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CourierScheduleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CourierScheduleReportEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GridHeaderEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CourierScheduleExcelDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.CourierScheduleException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CourierScheduleVo;
import com.deppon.foss.module.base.baseinfo.server.util.XlsImpUtil;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;

/**
 *<p>Title: CourierScheduleAction</p>
 * <p>Description:快递员排班的action </p>
 * <p>Company: Deppon</p>
 * @author    130566-ZengJunfan
 * @date       2014-4-19
 */
public class CourierScheduleAction extends AbstractAction {
	
	private static final long serialVersionUID = 8262419551338845263L;
	/**
	 * 日志
	 */
	private static final Logger LOGGER =LoggerFactory.getLogger(CourierScheduleAction.class);
	/**
	 * 快递员排班Service
	 */
	private ICourierScheduleService courierScheduleService;
	/**
	 * 员工表
	 */
	private IEmployeeService employeeService;
	//交互实体
	private CourierScheduleVo vo;
	/**
	 * 导入文件
	 */
	private String uploadFile;
	/**
	 * 导入文件名
	 */
	private String uploadFileFileName;
	/**
	 * 组织
	 */
	//private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	  /**
     * 业务锁 Service
     */
    private IBusinessLockService businessLockService;
	
	
	/**
	 * 查询当前用户点部数据权限
	 *<p>Title: queryCourierUserDeptData</p>
	 *<p></p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-9上午10:15:39
	 * @return
	 */
	@JSON
	public String queryCourierUserDeptData(){
		try {
			vo =new CourierScheduleVo();
			List<String> orgListCode =FossUserContext.getCurrentUserManagerDeptCodes();
			//创建一个快递点数据权限集合
			List<String>  userOrgDatas=this.getUserOrgData(orgListCode);
			vo.setUserOrgDatas(userOrgDatas);
			return returnSuccess();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage(),e);
			return returnError(e.getMessage());
		}
	}
	/**
	 *<p>Title: getUserOrgData</p>
	 *<p>获取用户的快递点部数据权限</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-9上午11:09:27
	 * @return
	 */
	private List<String> getUserOrgData(List<String> orgListCode) {
		
		List<String> userOrgDatas=new ArrayList<String>();
		
		if(CollectionUtils.isNotEmpty(orgListCode)){
			for (String orgCode : orgListCode) {
				userOrgDatas.add(orgCode);
			}
		}
		return userOrgDatas;
	}
	/**
	 * 
	 *<p>Title: queryCourierScheduleList</p>
	 *<p>分页查询页面</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-19下午6:07:15
	 * @return
	 */
	@JSON
	public String queryCourierScheduleList(){
		try {
			if(vo == null){
				return returnSuccess();
			}
			CourierScheduleEntity entity =vo.getCourierScheduleEntity();
			//设置当前登录人
			CurrentInfo currentInfo =FossUserContext.getCurrentInfo();
			entity.setEmpCode(currentInfo.getEmpCode());
			//查询
			List<CourierScheduleEntity> resultList =courierScheduleService.queryCourierScheduleList(entity, start, limit);
			vo.setCourierScheduleList(resultList);
			this.setTotalCount(courierScheduleService.queryCount(entity));
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(),e);
			return returnError(e.getMessage());
		}
	}
	/**
	 * 
	 *<p>Title: queryCourierScheduleListByOther</p>
	 *<p>根据条件查询列表</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-24下午6:28:38
	 * @return
	 */
	@JSON
	public String queryCourierScheduleListByOther(){
		try {
			CourierScheduleEntity entity =vo.getCourierScheduleEntity();
			//查询
			List<CourierScheduleEntity> resultList =courierScheduleService.queryCourierScheduleListByOther(entity);
			vo.setCourierScheduleList(resultList);
			return returnSuccess();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage(),e);
			return returnError(e.getMessage());
		}
	}
	/**
	 *<p>Title: addCourierSchedule</p>
	 *<p>添加快递员排班</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-25上午8:30:33
	 * @return
	 */
	@JSON
	public String addCourierSchedule(){
		try {
			CourierScheduleEntity entity =vo.getCourierScheduleEntity();
			MutexElement mutex = new MutexElement(String.valueOf(entity.getExpressSmallZoneCode()), "COURIERSCHEDULE_EXPRESS_SMALLZONE",MutexElementType.COURIERSCHEDULE_EXPRESS_SMALLZONE);
			LOGGER.info("开始加锁：" + mutex.getBusinessNo());
			boolean result = businessLockService.lock(mutex,
					NumberConstants.ZERO);
			if (result) {
				LOGGER.info("成功加锁：" + mutex.getBusinessNo());
			} else {
				LOGGER.info("失败加锁：" + mutex.getBusinessNo());
			}
			CurrentInfo currentInfo =FossUserContext.getCurrentInfo();
			entity.setCreateUser(currentInfo.getEmpCode());
			//新增
			courierScheduleService.addCourierSchedule(entity);
			
			LOGGER.info("开始解锁：" + mutex.getBusinessNo());
			// 解业务锁
			businessLockService.unlock(mutex);
			LOGGER.info("完成解锁：" + mutex.getBusinessNo());
			
			return returnSuccess(MessageType.SAVE_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(),e);
			return returnError(e.getMessage());
		}
	}
	/**
	 * 
	 *<p>Title: updateCourierSchedule</p>
	 *<p>修改快递员排班</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-25上午9:30:08
	 * @return
	 */
	@JSON
	public String updateCourierSchedule(){
		try {
			CourierScheduleEntity entity =vo.getCourierScheduleEntity();
			CurrentInfo currentInfo =FossUserContext.getCurrentInfo();
			entity.setModifyUser(currentInfo.getEmpCode());
			//修改
			courierScheduleService.updateCourierSchedule(entity);
			return returnSuccess(MessageType.UPDATE_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(),e);
			return returnError(e.getMessage());
		}
	}
	/**
	 * 
	 *<p>Title: deleteCourierScheduleMore</p>
	 *<p>作废快递员排班</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-28上午8:32:41
	 * @return
	 */
	@JSON
	public String deleteCourierScheduleMore(){
		try {
			List<String> ids =vo.getIds();
			CurrentInfo currentInfo =FossUserContext.getCurrentInfo();
			courierScheduleService.deleteCourierScheduleMore(ids,currentInfo.getEmpCode());
			return returnSuccess(MessageType.DELETE_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(),e);
			return returnError(e.getMessage());
		}
	}
	
	
	/**
	 *<p>Title: queryGridHeader</p>
	 *<p>生成表头</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-23下午3:23:49
	 * @return
	 */
	@JSON
	public String queryGridHeader(){
		try {
			List<GridHeaderEntity> gridHeaderFields =new ArrayList<GridHeaderEntity>();
			Calendar cdr = Calendar.getInstance();
			if(null != vo &&StringUtils.isNotBlank(vo.getYmd())){
				cdr.setTime(DateUtils.convert(vo.getYmd(),DateUtils.DATE_FORMAT));
			}else{
				return returnError("vo为空");
			}
			// 获取本月最后一天的数值
			int actualMaximum = cdr.getActualMaximum(Calendar.DATE);
			GridHeaderEntity gridHeaderEntity =null;
			for (int day = 1; day <= actualMaximum; day++){
				// 设置当天日期
				cdr.set(cdr.get(Calendar.YEAR), cdr.get(Calendar.MONTH), day);
				// 获取日期，星期，等数据
				gridHeaderEntity = new GridHeaderEntity();
				gridHeaderEntity.setDataIndex("date" + cdr.get(Calendar.DAY_OF_MONTH));
				gridHeaderEntity.setRendererData("date" + cdr.get(Calendar.DAY_OF_MONTH));
				gridHeaderEntity.setDataType("String");
				// 星期
				gridHeaderEntity.setDayText(convertDayOfWeek(cdr.get(Calendar.DAY_OF_WEEK)));
				// 日期
				gridHeaderEntity.setHeaderDate(cdr.get(Calendar.DAY_OF_MONTH));
				gridHeaderFields.add(gridHeaderEntity);
			}
			vo.setGridHeaderFields(gridHeaderFields);
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(),e);
			return returnError(e);
		}
	}
	/**
	 *<p>Title: convertDayOfWeek</p>
	 *<p>工具方法，星期转换</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-23下午3:34:56
	 * @param dayNum
	 * @return
	 */
	private String convertDayOfWeek(int dayNum) {
		String day = "";
		switch (dayNum) {
		case Calendar.SUNDAY:
			day = "天";
			break;
		case Calendar.MONDAY:
			day = "一";
			break;
		case Calendar.TUESDAY:
			day = "二";
			break;
		case Calendar.WEDNESDAY:
			day = "三";
			break;
		case Calendar.THURSDAY:
			day = "四";
			break;
		case Calendar.FRIDAY:
			day = "五";
			break;
		case Calendar.SATURDAY:
			day = "六";
			break;
		default:
			day = "";
			break;
		}
		return day;
	}
	/**
	 *<p>Title: importCourierSchedule</p>
	 *<p>导入快递员排班表</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-25上午10:36:40
	 * @return
	 */
	public String importCourierSchedule(){
		// 文件
		Workbook book = null;
		// 输入流
		FileInputStream inputStream = null;
		// 抓取文件类型
		int importTotal = 0;
		try {
			if(StringUtils.isNotBlank(uploadFileFileName)){
				//验证导入的文件类型
				/*if(uploadFileFileName.endsWith(".XLS")||uploadFileFileName.endsWith(".xls")
						||uploadFileFileName.endsWith(".xlsx")||uploadFileFileName.endsWith(".XLSX")){
					//nothing to do
					;
				}else{
					throw new CourierScheduleException("系统只支持.xls或.xlsx的格式文件，请选择正确文件进行导入");
				}*/
				
				if(!uploadFileFileName.endsWith(".XLS")&&!uploadFileFileName.endsWith(".xls")
						&&!uploadFileFileName.endsWith(".xlsx")&&!uploadFileFileName.endsWith(".XLSX")){
					throw new CourierScheduleException("系统只支持.xls或.xlsx的格式文件，请选择正确文件进行导入");
				}
			}
			//验证文件非空
			if(uploadFile !=null){
				try {
					//转换流
					inputStream =new FileInputStream(uploadFile);
					//解析excel流
					book =XlsImpUtil.create(inputStream);
				} catch (Exception e) {
					LOGGER.error("非2003格式文件，转2007格式");
					return returnError("数据异常:" + e.getMessage(), e);
				}
			}else{
				throw new CourierScheduleException("请选择文件进行导入");
			}
			
			if(book ==null){
				throw new  CourierScheduleException("book 为空");
			}
			// 默认获取获取工作表1
			Sheet sheet = book.getSheetAt(NumberConstants.ZERO);
			//
			List<CourierScheduleExcelDto> excelDtos =new ArrayList<CourierScheduleExcelDto>();
			//将Excel表格每行数据读入列表
			excelDtos=makeExcelDtos(sheet);
			//传入的快递点部
			String expressPartCode =vo.getCourierScheduleEntity().getExpressPartCode();
			//校验传入的快点点部和excel中的快递点部是否一致
			compareExcelPartCodeToExpressPartCode(expressPartCode,excelDtos);
				//收集所有快递员，校验快递员是否属于该快递点部
			validCourierBelongTheOrg(excelDtos,expressPartCode);
			//获取登陆人信息
			CurrentInfo currentInfo =FossUserContext.getCurrentInfo();
			//批量插入导入信息
			importTotal =courierScheduleService.addCourierScheduleMore(excelDtos,currentInfo.getEmpCode());
			vo.setImportToTal(importTotal);
			return returnSuccess(MessageType.SAVE_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(),e);
			return returnError(e.getMessage(),e);
		}finally {
			// 回收文件数据
			// 判空
			if (book != null) {
				book = null;
			}
			// 关闭流文件
			// 判空
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					LOGGER.error("关闭失败",e);
					return returnError("文件关闭失败");
				}
			}
		} 
	}
	/**
	 *<p>Title: validCourierBelongTheOrg</p>
	 *<p>工具方法：校验excel中的快递员是否是属于该快递点部</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-25下午3:35:15
	 * @param excelDtos
	 * @param expressPartCode
	 */
	private void validCourierBelongTheOrg(
			List<CourierScheduleExcelDto> excelDtos, String expressPartCode) {
		if(CollectionUtils.isEmpty(excelDtos)||StringUtils.isBlank(expressPartCode)){
			throw new CourierScheduleException("传入数据为空");
		}
		//循环
		for (CourierScheduleExcelDto courierScheduleExcelDto : excelDtos) {
			//获取dto中的排班实体
			CourierScheduleEntity entity =courierScheduleExcelDto.getScheduleEntity();
			//非空校验
			if(null !=entity&&StringUtils.isNotBlank(entity.getCourierCode())){
				//查询员工信息【优化，不要拼接组织的员工信息，减少服务器的压力】
				EmployeeEntity employeeEntity =employeeService.querySimpleEmployeeByEmpCode(entity.getCourierCode());
				//若存在该员工，
				if(null !=employeeEntity&&StringUtils.isNotBlank(employeeEntity.getOrgCode())){
					//校验是否导入文件中与选择的点部不一致的数据
					if(!expressPartCode.equals(employeeEntity.getOrgCode())){
						throw new CourierScheduleException("排班导入文件中存在不属于"+expressPartCode+"部门的快递员的数据");
					}
					//若该员工不存在
				}else if(null ==employeeEntity){
					throw new CourierScheduleException("排班导入文件中存在不是公司的快递员的数据");
				}
				
			}
		}			
		
	}
	/**
	 *<p>Title: compareExcelPartCodeToExpressPartCode</p>
	 *<p>工具方法：校验传入的快递点部和excel中的快递点部是否一致，</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-25下午3:25:06
	 * @param expressPartCode
	 * @param excelDtos
	 */
	private void compareExcelPartCodeToExpressPartCode(String expressPartCode,
			List<CourierScheduleExcelDto> excelDtos) {
		if(CollectionUtils.isEmpty(excelDtos)||StringUtils.isBlank(expressPartCode)){
			throw new CourierScheduleException("传入数据为空");
		}
		//循环
		for (CourierScheduleExcelDto courierScheduleExcelDto : excelDtos) {
			//获取dto中的排班实体
			CourierScheduleEntity entity =courierScheduleExcelDto.getScheduleEntity();
			//非空校验
			if(null !=entity && StringUtils.isNotBlank(entity.getExpressPartCode())){
				//校验是否导入文件中与选择的点部不一致的数据
				if(!expressPartCode.equals(entity.getExpressPartCode())){
					throw new CourierScheduleException("排班导入文件中存在不是"+expressPartCode+"部门的数据");
				}
			}
		}
	}
	/**
	 *<p>Title: makeExcelDtos</p>
	 *<p>工具方法：将excel表格每行数据读入列表</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-25下午1:50:21
	 * @param sheet
	 * @return
	 */
	private List<CourierScheduleExcelDto> makeExcelDtos(Sheet sheet) {
		List<CourierScheduleExcelDto> excelDtos =new ArrayList<CourierScheduleExcelDto>();
		if(null !=sheet){
			// 获取物理行数
			int rowCount = sheet.getPhysicalNumberOfRows();
			// 根据行数循环
			Row row = null;
			//最大列数
			int colCnt = 0;
			//行记录
			CourierScheduleExcelDto excelDto =null;
			
			for (int rowNum = 1; rowNum < rowCount; rowNum++) {
				// 获取每行数据
				excelDto = new CourierScheduleExcelDto();
				// 设置行号
				excelDto.setRowNum(rowNum);
				// 取得一行的数据
				row = sheet.getRow(rowNum);
				// 如果本行第一列为空，则不继续取值
				if(row==null ||row.getCell(0) == null
						||StringUtils.isBlank(obtainStringVal(row.getCell(0)))){
					continue;
				}else{
					//若列数小于1
					if(colCnt<1){
						//设置最大列数，一共12列
						colCnt=NumberConstants.NUMBER_12;
					}
					CourierScheduleEntity entity =new CourierScheduleEntity();
					//获取值
					for (int colNum = 0; colNum < colCnt; colNum++) {
						obtainCellValue(row, colNum, entity);
					}
					//设置当前行的排班信息
					excelDto.setScheduleEntity(entity);
				}
				excelDtos.add(excelDto);
			}
			return excelDtos;
		}
		return null;
	}
	/**
	 *<p>Title: obtainCellValue</p>
	 *<p>根据方法：获取单元格的值</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-25下午2:32:43
	 * @param row 行值
	 * @param i 列数
	 * @param excelDto
	 */
	private void obtainCellValue(Row row, int colNum,
			CourierScheduleEntity entity) {
		if (null != row && null != row.getCell(colNum)) {
			// 获取单元格
			Cell cell = row.getCell(colNum);
			String cellValue = obtainStringVal(cell);
			// 查询是否有.0的数据
			int idx = cellValue.indexOf(".0");
			if (!sonarSplitOne(colNum, entity, cellValue, idx)) {
				if (!sonarSplitTwo(colNum, entity, cellValue, idx)) {
					sonarSplitThree(colNum, entity, cellValue, idx);
				}
			}
		}
	}
	
	/**
	 * sonar优化方法拆分
	 * @author 313353
	 * 
	 */
	private boolean sonarSplitOne(int colNum, CourierScheduleEntity entity, 
			String cellValue, int idx){
		switch (colNum) {
		case NumberConstants.NUMERAL_ZORE://点部编码
			entity.setExpressPartCode(cellValue); 
			return true;
		case NumberConstants.NUMBER_2: //快递员编码
			if(idx>0){
				entity.setCourierCode(StringUtils.substring(cellValue, 0, idx));
			}else{
				entity.setCourierCode(cellValue);
			}
			return true;
		case NumberConstants.NUMBER_3://快递员名称
			entity.setCourierName(cellValue);
			return true;
		case NumberConstants.NUMBER_4://排班日期
			entity.setSchedulingDate(DateUtils.convert(cellValue,DateUtils.DATE_FORMAT));
			return true;
		case NumberConstants.NUMBER_5://工作类别
			entity.setPlanType(cellValue);
			return true;
		default: 
			return false;
		}
	}
	
	/**
	 * sonar优化方法拆分
	 * @author 313353
	 * 
	 */
	private boolean sonarSplitTwo(int colNum, CourierScheduleEntity entity, 
			String cellValue, int idx){
		switch (colNum) {
		case NumberConstants.NUMBER_7://收派小区编码
			if("无".equals(cellValue)){
				entity.setExpressSmallZoneCode("");
			}else{
				entity.setExpressSmallZoneCode(cellValue.trim());
			}
			return true;
		case NumberConstants.NUMBER_8://收派小区名称
			if("无".equals(cellValue)){
				entity.setExpressSmallZoneName("");
			}else{
				entity.setExpressSmallZoneName(cellValue.trim());
			}
			return true;
		case NumberConstants.NUMBER_9://起始楼层
			if("".equals(cellValue)|| "无".equals(cellValue)){
				entity.setStartFloor(0);
				//后面若是以.0 结尾的 截取.0
			}else if(cellValue.endsWith(".0")){
				entity.setStartFloor(Integer.parseInt(StringUtils.substring(cellValue, 0, idx)));
			}else{
				entity.setStartFloor(Integer.parseInt(cellValue));
			}
			return true;
		case NumberConstants.NUMBER_10://结束楼层
			if("".equals(cellValue)|| "无".equals(cellValue)){
				entity.setEndFloor(0);
			}else if(cellValue.endsWith(".0")){
				entity.setEndFloor(Integer.parseInt(StringUtils.substring(cellValue, 0, idx)));
			}else{
				entity.setEndFloor(Integer.parseInt(cellValue));
			}
			return true;
		default: return false;
		}
	}
		
		/**
		 * sonar优化方法拆分
		 * @author 313353
		 * 
		 */
		private void sonarSplitThree(int colNum, CourierScheduleEntity entity, 
				String cellValue, int idx){
			switch (colNum) {
			case NumberConstants.NUMBER_11://快递员属性
				if("定区".equals(cellValue)){
					entity.setCourierNature(DictionaryValueConstants.COURIER_NATURE_FIXED);
				}else if("机动".equals(cellValue)){
					entity.setCourierNature(DictionaryValueConstants.COURIER_NATURE_MOTORIZED);
				}else{
					entity.setCourierNature("");
				}
				return;
			case NumberConstants.NUMBER_1: //点部名称(这边不给他赛值)
			case NumberConstants.NUMBER_6://工作类别名称(不传给entity)
			default:
				return;
			}
	}
	
	/**
	 *<p>Title: obtainStringVal</p>
	 *<p>根据方法： 单元格取值转换</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-25下午2:17:57
	 * @param cell
	 * @return
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
				cellVal = com.deppon.foss.util.DateUtils.convert(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()),
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
			if ("NaN".equals(cellVal)) {// 如果获取的数据值为非法值,则转换为获取字符串
				cellVal = cell.getRichStringCellValue().toString();
			}
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:// 布尔
			cellVal = " " + cell.getBooleanCellValue();
			break;
		// 此行表示该单元格值为空
		case HSSFCell.CELL_TYPE_BLANK: // 空值
			//cellVal = "";
			//break;
		case HSSFCell.CELL_TYPE_ERROR: // 故障
			cellVal = "";
			break;
		default:
			cellVal = cell.getRichStringCellValue().toString();
		}
		return cellVal.trim();
	}
	/**
	 * 
	 *<p>Title: queryCourierScheduleReportList</p>
	 *<p>报表式查看</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-28下午7:02:41
	 * @return
	 */
	@JSON
	public String queryCourierScheduleReportList(){
		try {
			CourierScheduleEntity entity =vo.getCourierScheduleEntity();
			List<CourierScheduleReportEntity> reportEntiyList =courierScheduleService.queryCourierScheduleReportList(entity,this.start,this.limit);
			vo.setReportEntityList(reportEntiyList);
			long count =courierScheduleService.queryReportListCount(entity);
			this.setTotalCount(count);
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(),e);
			return returnError(e);
		}
	}
	/**
	 * @return the uploadFile
	 */
	public String getUploadFile() {
		return uploadFile;
	}
	/**
	 * @param uploadFile the uploadFile to set
	 */
	public void setUploadFile(String uploadFile) {
		this.uploadFile = uploadFile;
	}
	/**
	 * @return the uploadFileFileName
	 */
	public String getUploadFileFileName() {
		return uploadFileFileName;
	}
	/**
	 * @param uploadFileFileName the uploadFileFileName to set
	 */
	public void setUploadFileFileName(String uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}
	/**
	 * @return the vo
	 */
	public CourierScheduleVo getVo() {
		return vo;
	}
	/**
	 * @param vo the vo to set
	 */
	public void setVo(CourierScheduleVo vo) {
		this.vo = vo;
	}
	/**
	 * @param courierScheduleService the courierScheduleService to set
	 */
	public void setCourierScheduleService(
			ICourierScheduleService courierScheduleService) {
		this.courierScheduleService = courierScheduleService;
	}
	/**
	 * @param employeeService the employeeService to set
	 */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	/**
	 * @param orgAdministrativeInfoService the orgAdministrativeInfoService to set
	 */
	/*public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	*/
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}
}
