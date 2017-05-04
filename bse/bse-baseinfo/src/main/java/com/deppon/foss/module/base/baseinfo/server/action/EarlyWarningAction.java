package com.deppon.foss.module.base.baseinfo.server.action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.ColumnConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEarlyWarningService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EarlyWarningEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.EarlyWarningVo;
import com.deppon.foss.util.copyUtil.XlsImpUtil;
import com.deppon.foss.base.util.define.NumberConstants;
/**
 * 
 * 提前预警ACTION类
 * @author 262036 - huangwei
 * @date 2015-8-19 下午7:21:43
 * @since
 * @version
 */
public class EarlyWarningAction extends AbstractAction{
	
    private static final Logger LOGGER = LoggerFactory.getLogger(EarlyWarningAction.class);

    private static final long serialVersionUID = 123278923423451123L;
	/**
	 * 提前预警SERVICE接口
	 */
	private IEarlyWarningService earlyWarningService;
	
	//上传文件每行的的总列数
	private static final int CELL_COUNT = 7;
	//上传数量5000
	private static final int MAX_NUMBER = 5000;
	/**
	 * 提前预警VO
	 */
	private EarlyWarningVo earlyWarningVo = new EarlyWarningVo();
	
	/**
	 * 
	 * 分页查询提前预警信息
	 * @author 262036 - huangwei
	 * @date 2015-8-19 下午7:22:41
	 * @return
	 * @see
	 */
	@JSON
	public String queryEarlyWarnings(){
		try{
			List<EarlyWarningEntity> entityList = earlyWarningService.queryEarlyWarningEntitysByCityCode(earlyWarningVo.getEntity(), limit, start);
			earlyWarningVo.setEntityList(entityList);
			// 查询总记录数
			Long totalCount = earlyWarningService.queryRecordCount(earlyWarningVo.getEntity());
		    // 设置totalCount
		    this.setTotalCount(totalCount);
			return returnSuccess();
		}catch(BusinessException e){
			LOGGER.info(e.getMessage(), e);
			return returnError(e);
		}
	}
	
	//导出模板和
	public String execlToTemplate() throws IOException{
		earlyWarningVo.setDownloadFileName(URLEncoder.encode(ColumnConstants.EXPORT_EARLY_WARNING, "UTF-8"));
		ExportSetting exportSetting = new ExportSetting();
		//设置表头名称
	    exportSetting.setSheetName(ColumnConstants.EXPORT_EARLY_WARNING);
	    ExportResource sheet = new ExportResource();
		sheet.setHeads(ComnConst.EARLY_WARNING);
		List<List<String>> results = new ArrayList<List<String>>();
		sheet.setRowList(results);
	    ExporterExecutor objExporterExecutor = new ExporterExecutor();
	    earlyWarningVo.setExcelStream(objExporterExecutor.exportSync(sheet,exportSetting));
		return returnSuccess();
	}
	
	//导入数据
	public String importEarlyWarning(){
		Workbook book = null;
		try {
			if(earlyWarningVo.getUploadFile()!=null&&StringUtils.isNotBlank(earlyWarningVo.getUploadFileFileName())){
				FileInputStream inputStream = new FileInputStream(earlyWarningVo.getUploadFile());
				book = XlsImpUtil.create(inputStream);
			}
			String message="";
			if(book!=null){
				message=readWorkbook(book);
			}
			if(message.equals("success")){
				return super.returnSuccess(MessageType.IMPORT_SUCCESS);
			}else{
				return returnError("数据参数错误!");
			}
				
			
		} catch (FileNotFoundException e) {
			return returnError("文件不存在异常:" + e.getMessage(), e);
		} catch (InvalidFormatException e) {
			return returnError("文件格式异常:" + e.getMessage(), e);
		} catch (Exception e) {
			return returnError("文件异常:" + e.getMessage(), e);
		}
	}

	private String readWorkbook(Workbook book) {
		//获取sheet的个数
		int sheetNum = book.getNumberOfSheets();

		for(int i=0;i<sheetNum;i++){
			if(book.getSheetName(i).equals("提前预警线路信息.xls")){
				//获取工作表
				Sheet sheet = book.getSheetAt(i);
				int rowNum = sheet.getPhysicalNumberOfRows();
				//目前上传数据量限定在5000条以内
				if(rowNum>MAX_NUMBER){
					return returnError("上传文件数据太多!!!");
				}
				//读取表头,进行校验
				Row row = sheet.getRow(0);
				if(row!=null){
					//313353 2016/09/12 sonar优化  start
					String result = this.sonarSplit(row);
					if(!result.isEmpty()){
						return result;
					}
					//313353 2016/09/12 sonar优化  end
				}else{
					return returnError("文件格式异常:请按照模板添加上传数据!!!");
				}
				List<EarlyWarningEntity> ses= new ArrayList<EarlyWarningEntity>();
				makeExcelToSes(sheet, ses);
				earlyWarningService.importEarlyWarnings(ses);
			}
		}
		return "success";
	}
	
	/**
	 * sonar优化方法拆分
	 * @author 313353
	 */
	private String sonarSplit(Row row){
		String[] forCompare = { "出发城市编码", "出发城市名称", "到达城市编码", "到达城市名称", "操作票数",
				"兑现票数", "兑现率" };
		for(int i = 0; i <= NumberConstants.NUMBER_6; i++){
			Cell cell = row.getCell(i);
			if(cell==null||cell.getCellType()!=Cell.CELL_TYPE_STRING||
					!forCompare[i].equals(cell.getStringCellValue().trim())){
						return returnError("文件格式异常:请按照模板添加上传数据!!!");
					}
		}
		return StringUtils.EMPTY;
	}
	
	private void makeExcelToSes(Sheet sheet,List<EarlyWarningEntity> ses){
		//记录那些行的数据无法解析
		List<Integer> numList = new ArrayList<Integer>();
		if(sheet!=null&&ses!=null){
			// 获取物理行数
			int rowCount = sheet.getPhysicalNumberOfRows();
			// 根据行数循环
			Row row = null;
			// EXCEL行记录
			EarlyWarningEntity se = null;
			// 根据行数循环,标记外层循环
			for (int rowNum = 1; rowNum < rowCount; rowNum++) {
				// 获取每行数据
				se = new EarlyWarningEntity();
				// 取得一行的数据
				row = sheet.getRow(rowNum);
				Cell cell = null;
				String cellValue = null;
				// 如果有一行空白则不会再录数据(一行数据全为空)
				for (int colNum = 0; colNum < CELL_COUNT; colNum++) {
					cell = row.getCell(colNum);
					if(cell==null){
						numList.add(rowNum+1);
						break;
					}
					cellValue = cellValue(cell);
					//如果为空或者长度超过50说明该行数据无效,记录行数返回到页面
					if(cellValue==null||"".equals(cellValue)||cellValue.length()>NumberConstants.NUMBER_50){
						numList.add(rowNum+1);
						break;
					}
					if(colNum==0){
						se.setStartCityCode(cellValue);
					}else if(colNum==NumberConstants.NUMBER_1){
						se.setStartCityName(cellValue);
					}else if(colNum==NumberConstants.NUMBER_2){
						se.setEndCityCode(cellValue);
					}else if(colNum==NumberConstants.NUMBER_3){
						se.setEndCityName(cellValue);
					}else if(colNum==NumberConstants.NUMBER_4){
						try{
							se.setOperaTickets(Integer.valueOf(cellValue));
						}catch(Exception e){
							numList.add(rowNum+1);
							break;
						}
					}else if(colNum==NumberConstants.NUMBER_5){
						try{
							se.setPromiseTickets(Integer.valueOf(cellValue));
						}catch(Exception e){
							numList.add(rowNum+1);
							break;
						}
					}else{
						se.setPromiseRate(cellValue);
						ses.add(se);
					}
				}
			}
			earlyWarningVo.setNumList(numList);
		}
	}
	
	private String cellValue(Cell cell){
		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				return cell.getStringCellValue().trim();
			case Cell.CELL_TYPE_NUMERIC:
				//排除掉导入数字数据后面的.0
				String str = cell.getNumericCellValue()+"";
				String[] nums = str.split("\\.");
				if(nums.length==2){
					try {
						//Integer.parseInt(nums[0]);
						//如果最后一个数字是0,就返回第一个字符串
						if("0".equals(nums[1])){
							return nums[0];
						}
					} catch (NumberFormatException e) {
						return str;
					}
				}
				return str;
			default:
				return null;
		}
	}

	public EarlyWarningVo getEarlyWarningVo() {
		return earlyWarningVo;
	}

	public void setEarlyWarningVo(EarlyWarningVo earlyWarningVo) {
		this.earlyWarningVo = earlyWarningVo;
	}

	public void setEarlyWarningService(IEarlyWarningService earlyWarningService) {
		this.earlyWarningService = earlyWarningService;
	}
	

}
