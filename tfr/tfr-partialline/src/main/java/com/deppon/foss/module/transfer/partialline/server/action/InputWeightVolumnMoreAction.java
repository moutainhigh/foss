package com.deppon.foss.module.transfer.partialline.server.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.partialline.api.server.service.IInputWeightVolumnMoreService;
import com.deppon.foss.module.transfer.partialline.api.shared.define.PartiallineConstants;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.InputWeightVolumnMoreDto;
import com.deppon.foss.module.transfer.partialline.api.shared.exception.ExternalBillException;
import com.deppon.foss.module.transfer.partialline.api.shared.util.PartialLineUtil;
import com.deppon.foss.module.transfer.partialline.api.shared.util.XlsImpUtil;
import com.deppon.foss.module.transfer.partialline.api.shared.vo.InputWeightVolumnMoreVo;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;

public class InputWeightVolumnMoreAction extends AbstractAction{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(InputWeightVolumnMoreAction.class);

	private InputWeightVolumnMoreVo vo;
	private IInputWeightVolumnMoreService inputWeightVolumnMoreService;
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	/**
	 * 导入文件
	 */
	private File uploadFile;
	/**
	 * 导入文件名
	 */
	private String uploadFileFileName;
	/**
	 * 文件大小
	 * @return
	 */
	
	private int fileSize;
	/**
	 *一票多件页面根据条件查询
	 *@date  2015-11-14下午2:21:40
	 *@author 257220
	 */
	public String queryInputWeightVolumnMoreList(){
		LOGGER.info("开始查询数据");
		try{			
			InputWeightVolumnMoreDto dto = new InputWeightVolumnMoreDto();
			if(vo == null){
				return  returnSuccess();
			}
			String waybillNos = vo.getWaybillNos();
			String handoverNos = vo.getHandOverBillNos();
			List<String> waybillNoList = null;
			List<String> handoverNoList = null;
			if(StringUtils.isNotEmpty(waybillNos)){
				waybillNoList = PartialLineUtil.getListFromStr(waybillNos);
			}
			if(StringUtils.isNotEmpty(handoverNos)){
				handoverNoList = PartialLineUtil.getListFromStr(handoverNos);
			}
			Date beginInStockTime = vo.getDto().getBeginInStockTime();
			Date endInStockTime = vo.getDto().getEndInStockTime();
			Date beginHandOverTime = vo.getDto().getBeginHandOverTime();
			Date endHandOverTime = vo.getDto().getEndHandOverTime();
			
			if(CollectionUtils.isNotEmpty(waybillNoList)) {
				dto.setWaybillNoList(waybillNoList);
			} else if(CollectionUtils.isNotEmpty(handoverNoList)) {
				dto.setHandOverBillList(handoverNoList);
			} else if(beginInStockTime != null && endInStockTime != null) {
				dto.setBeginInStockTime(beginInStockTime);
				dto.setEndInStockTime(endInStockTime);
			} else if(beginHandOverTime != null && endHandOverTime != null) {
				dto.setBeginHandOverTime(beginHandOverTime);
				dto.setEndHandOverTime(endHandOverTime);
			}
			String deptCode = "";
			CurrentInfo user = FossUserContext.getCurrentInfo();
			if(user != null) {
				String currentDeptCode = user.getCurrentDeptCode();
				OrgAdministrativeInfoEntity entity = this.getBigOrg(currentDeptCode);
				if(entity != null ) {
					deptCode = entity.getCode();
					dto.setDeptCode(deptCode);
				}
			}
			Long count = inputWeightVolumnMoreService.queryInputWeightVolumnMoreListCount(dto);
			if(count > 0){
				//查询
				List<InputWeightVolumnMoreDto> list = inputWeightVolumnMoreService.queryInputWeightVolumnMoreList(dto, start, limit);
				vo.setList(list);
				this.setTotalCount(count);
			}
			return super.returnSuccess();
		}catch(ExternalBillException e){
			return super.returnError(e);
		}
	}
	/**
	 * 保存信息
	 *@param 
	 *@return
	 *@date  2015-11-16上午9:49:05
	 *@author 257220
	 */
	public String saveMPSRecords(){
		try{
			if(vo == null || vo.getList() == null){
				return returnSuccess();
			}
			List<InputWeightVolumnMoreDto> list = vo.getList();
			inputWeightVolumnMoreService.save(list);
			return returnSuccess();
		}catch(BusinessException e){
			return returnError(e);
		}
	}
	
	/**
	 * 导入excel
	 * @return
	 * @author 257220
	 * @date 2015-9-9上午11:01:11
	 */
	public String importMPSRecords(){
		Workbook book = null;
		// 输入流
		FileInputStream inputStream = null;
		// 抓取文件类型
		try {
			// 文件名不为空
			// 判空
			if (StringUtils.isNotBlank(uploadFileFileName)) {
				// 判空
				if (uploadFileFileName.endsWith(PartiallineConstants.EXCEL_FILE_TAIL_XLS_DOWN)
						|| uploadFileFileName.endsWith(PartiallineConstants.EXCEL_FILE_TAIL_XLS_UP)
						|| uploadFileFileName.endsWith(PartiallineConstants.EXCEL_FILE_TAIL_XLSX_DOWN)
						|| uploadFileFileName.endsWith(PartiallineConstants.EXCEL_FILE_TAIL_XLSX_UP)) {
					// 验证通过
				} else {
					throw new BusinessException("只支持.xls或.xlsx格式的文件,请选择正确的文件导入", "只支持.xls或.xlsx格式的文件,请选择正确的文件导入");
				}
			}
			// 判空
			if (uploadFile != null) {
				inputStream = new FileInputStream(uploadFile);
				fileSize=inputStream.available();
				try {
					book = XlsImpUtil.create(inputStream);
				} catch (Exception ex) {
					LOGGER.error("非2003格式文件，转2007格式");
					throw new BusinessException("非2003格式文件，转2007格式，或文件加密", "非2003格式文件，转2007格式，或文件加密");
				}
			} else {
				throw new BusinessException("请选择导入文件", "请选择导入文件");
			}
			// 解析导入的数据文件
			// 判空
			if (book != null) {
				// 默认获取获取工作表1
				Sheet sheet = book.getSheetAt(PartiallineConstants.EXCEL_DEFAULT_SHEET_ONE);
				// 读取Excel的所用数据,考虑到数据量不大，暂时不适用集中缓存
				List<InputWeightVolumnMoreDto> excelDtos = new ArrayList<InputWeightVolumnMoreDto>();
				//将Excel表格每行数据读入列表
				makeExcelDtos(excelDtos, sheet,fileSize);
				// 判空
				if (CollectionUtils.isNotEmpty(excelDtos)) {
					// 执行批量插入
					inputWeightVolumnMoreService.batchSaveDataFromExcel(excelDtos);
					/*//返回导入信息
					if(parentList != null){
						InputWeightVolumnDto dto = new InputWeightVolumnDto();
						dto.setWaybillNoList(parentList);
						vo = new InputWeightVolumnMoreVo();
						this.setLimit(1000); 
						vo.setList(getInputWeightVolumnBaseInfo(dto));
					}*/
				} else {
					throw new BusinessException("导入数据为空", "导入数据为空");
				}	
			}

			// 成功
			return super.returnSuccess();
			// 异常
		} catch (BusinessException e) {
			// 日志
			//LOGGER1.error(e+"");;
			// 异常信息
			return super.returnError(e);
			// 异常
		} catch (FileNotFoundException e) {
			// 日志
			//LOGGER1.error("数据文件不存在", e);
			// 异常信息
			return returnError("数据文件不存在");
			// 异常信息
		} catch (Exception e) {
			// 日志
			//LOGGER1.error("数据异常", e);
			// 异常信息
			e.printStackTrace();
			return returnError("数据异常:请确认导入数据格式是否正确！", e.getMessage());

		} finally {
			// 回收文件数据
			// 判空
			if (book != null) {
				book=null;
			}
			// 关闭流文件
			// 判空
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					//LOGGER1.error(e+"");
					return returnError("文件关闭失败");
				}
			}
		}
	}
	/**
	 * @author gongjp
	 * @date 2015-04-29
	 * @function 读取excel内容至列表
	 * @param excelDtos
	 * @param sheet
	 * @return 
	 */
	private  void makeExcelDtos(List<InputWeightVolumnMoreDto> excelDtos, Sheet sheet,int fileSize) {
		// 判空
		if (sheet != null && excelDtos != null) {
			// 获取物理行数
			int rowCount = sheet.getPhysicalNumberOfRows();
			//如果文件大小超过10M给出提示信息
			if(fileSize>ConstantsNumberSonar.SONAR_NUMBER_10*ConstantsNumberSonar.SONAR_NUMBER_1024*ConstantsNumberSonar.SONAR_NUMBER_1024){
				throw new BusinessException("您导入的文件大小为"+fileSize+"字节,上传的EXCEL数据文件大小不能超过10M","您导入的文件大小为"+fileSize+"字节", "上传的EXCEL数据文件大小不能超过10M");
			//每次导入Excel不能超过1000条
			}
			if(rowCount>ConstantsNumberSonar.SONAR_NUMBER_1001){
				throw new BusinessException("您导入的文件条数为"+(rowCount-1)+"条,每次导入Excel不能超过1000条","您导入的文件条数为"+(rowCount-1)+"条,每次导入Excel不能超过1000条");
			}
			// 根据行数循环
			Row row = null;
			// 最大列数
			int colCnt = PartiallineConstants.EXCEL_COLUMN_WVM_MAX_SIZE;
			// EXCEL行记录
			InputWeightVolumnMoreDto excelDto = null;
			List<String> list = new ArrayList<String>();
			// 根据行数循环
			for (int rowNum = 1; rowNum < rowCount; rowNum++) {
				excelDto = new InputWeightVolumnMoreDto();
				// 取得一行的数据
				row = sheet.getRow(rowNum);
				//判断单元格值是否满足要求
				// 如果本行第一列或第二列或第三列或第四列有任意一列值为空，则不取本行的数据，继续往下取值
				if (row == null || row.getCell(PartiallineConstants.EXCEL_COLUMN_FIRST) == null
								|| StringUtils.isBlank(obtainStringVal(row.getCell(PartiallineConstants.EXCEL_COLUMN_FIRST)))){
					list.add("第"+rowNum+"行第"+(PartiallineConstants.EXCEL_COLUMN_FIRST+1)+"列数据为空");
				}
				if (row == null || row.getCell(PartiallineConstants.EXCEL_COLUMN_SECOND) == null
						        || StringUtils.isBlank(obtainStringVal(row.getCell(PartiallineConstants.EXCEL_COLUMN_SECOND)))){
					list.add("第"+rowNum+"行第"+(PartiallineConstants.EXCEL_COLUMN_SECOND+1)+"列数据为空");
		        }
				if (row == null || row.getCell(PartiallineConstants.EXCEL_COLUMN_THREE) == null
				                || StringUtils.isBlank(obtainStringVal(row.getCell(PartiallineConstants.EXCEL_COLUMN_THREE)))){
					list.add("第"+rowNum+"行第"+(PartiallineConstants.EXCEL_COLUMN_THREE+1)+"列数据为空");
                 }
				if (row == null || row.getCell(PartiallineConstants.EXCEL_COLUMN_FOUR) == null
		                        || StringUtils.isBlank(obtainStringVal(row.getCell(PartiallineConstants.EXCEL_COLUMN_FOUR)))){
					list.add("第"+rowNum+"行第"+(PartiallineConstants.EXCEL_COLUMN_FOUR+1)+"列数据为空");
                }
				if(list!=null&&list.size()>0){
                	 String error = PartialLineUtil.changeArrayToString(list);
                	 if(rowNum+1>=rowCount){
                		 throw new BusinessException(error,error);
                	 }else{
                		 continue;
                	 }
                	
                 }				
				else {
					// 循环取列值
					for (int colNum = 0; colNum < colCnt; colNum++) {
						// 由于读取EXCEL效率低下，故先取所有的值，再到内存中校验，提升性能（前提是数据量不大）
						try {
							obtainCellValue(row, colNum, excelDto);
						} catch (ParseException e) {
							Log.error("获取单元格数据失败");
							e.printStackTrace();
						}
					}
				}
				excelDtos.add(excelDto);
			}
		}
	}
	
	/**
	 * 单元格取值
	 * @param cell
	 * @return
	 * @author 257220
	 * @date 2015-9-9上午11:10:03
	 */
	public String obtainStringVal(Cell cell) {
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
			if (PartiallineConstants.NAN.equals(cellVal)) {// 如果获取的数据值为非法值,则转换为获取字符串
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
	 * @author gongjp
	 * @date 2015-04-29
	 * @function  获取单元格的值
	 * @param row
	 * @param colNum
	 * @param excelDto
	 * @param createUser
	 * @throws ParseException
	 */
	private void obtainCellValue(Row row, int colNum, InputWeightVolumnMoreDto excelDto) throws ParseException {
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
					case 0:// 运单号
						excelDto.setWaybillNo(cellVal);
						return;
					case 1:// 流水号
						excelDto.setSerialNo(cellVal);
						return;
					case 2:// 重量
						BigDecimal weight = new BigDecimal(cellVal);
						weight = weight.setScale(2, BigDecimal.ROUND_HALF_UP);
						excelDto.setWeight(weight);
						return;
					case ConstantsNumberSonar.SONAR_NUMBER_3:// 体积
						BigDecimal volume = new BigDecimal(cellVal);
						volume = volume.setScale(2, BigDecimal.ROUND_HALF_UP);
						excelDto.setVolumn(volume);
						return;
					default:
						return;
					}					
				}

			}
		}
	}
	/**
	 * @author nly
	 * @date 2014年9月22日 下午5:18:27
	 * @function 获取上级外场
	 * @param currentOrg
	 * @return
	 */
	private OrgAdministrativeInfoEntity getBigOrg(String currentOrgCode){
			//设置查询参数
			List<String> bizTypesList = new ArrayList<String>();
			//外场类型
			bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
			OrgAdministrativeInfoEntity bigOrg = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(currentOrgCode, bizTypesList);
			if(bigOrg == null){
				throw new TfrBusinessException("查询当前部门所属外场失败","查询当前部门所属外场失败");
			}
			return bigOrg;
	}
	public void setVo(InputWeightVolumnMoreVo vo) {
		this.vo = vo;
	}
	public InputWeightVolumnMoreVo getVo() {
		return vo;
	}
	public void setInputWeightVolumnMoreService(
			IInputWeightVolumnMoreService inputWeightVolumnMoreService) {
		this.inputWeightVolumnMoreService = inputWeightVolumnMoreService;
	}
	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}
	public void setUploadFileFileName(String uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
}
