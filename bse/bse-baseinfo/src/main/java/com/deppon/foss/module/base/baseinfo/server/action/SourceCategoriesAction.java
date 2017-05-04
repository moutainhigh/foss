package com.deppon.foss.module.base.baseinfo.server.action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.deppon.foss.base.util.define.ColumnConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISourceCategoriesService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SourceCategoriesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.SourceCategoriesVo;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.util.copyUtil.XlsImpUtil;

/**
 * 货源品类基础资料ACTION
 * @author 198771
 *
 */
public class SourceCategoriesAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 14446461649431499L;
	
	private ISourceCategoriesService sourceCategoriesService;
	
	private IDataDictionaryService dataDictionaryService;
	
	//上传文件每行的的总列数
	private static final int CELL_COUNT = 2;
	
	private SourceCategoriesVo sourceCategoriesVo = new SourceCategoriesVo();

	public SourceCategoriesVo getSourceCategoriesVo() {
		return sourceCategoriesVo;
	}

	public void setSourceCategoriesVo(SourceCategoriesVo sourceCategoriesVo) {
		this.sourceCategoriesVo = sourceCategoriesVo;
	}
	
	public void setDataDictionaryService(
			IDataDictionaryService dataDictionaryService) {
		this.dataDictionaryService = dataDictionaryService;
	}

	public void setSourceCategoriesService(
			ISourceCategoriesService sourceCategoriesService) {
		this.sourceCategoriesService = sourceCategoriesService;
	}
	
	//点击新增
	public String addSourceCategoriesEntity(){
		sourceCategoriesVo = sourceCategoriesService.addSourceCategoriesEntity(sourceCategoriesVo);
		return returnSuccess();
	}

	//点击查询
	public String querySourceCategoriesEntitys(){
		sourceCategoriesVo = sourceCategoriesService.querySourceCategoriesEntitys(
				sourceCategoriesVo.getCondition(),limit,start);
		totalCount = sourceCategoriesService.getTotalRecord(sourceCategoriesVo.getCondition());
		return returnSuccess();
	}
	
	//点击修改
	public String updateSourceCategoriesEntity(){
		sourceCategoriesVo = sourceCategoriesService.updateSourceCategoriesEntity(
				sourceCategoriesVo.getSourceCategoriesEntity());
		return returnSuccess();
	}
	
	//点击删除
	public String deleteSourceCategoriesEntitys(){
		sourceCategoriesService.deleteSourceCategoriesEntitys(
				sourceCategoriesVo.getSourceCategoriesIds());
		return returnSuccess();
	}
	
	//导出模板和数据
	public String execlToTemplate() throws IOException{
		sourceCategoriesVo.setDownloadFileName(URLEncoder.encode(ColumnConstants.EXPORT_SOURCE_CATEGORY, "UTF-8"));
		ExportSetting exportSetting = new ExportSetting();
		//设置表头名称
	    exportSetting.setSheetName(ColumnConstants.EXPORT_SOURCE_CATEGORY);
	    ExportResource resource = sourceCategoriesService.queryExportResource(sourceCategoriesVo);
	    ExporterExecutor objExporterExecutor = new ExporterExecutor();
		sourceCategoriesVo.setExcelStream(objExporterExecutor.exportSync(resource,exportSetting));
		return returnSuccess();
	}
	
	//导入数据
	public String importSourceCategory(){
		Workbook book = null;
		try {
			if(sourceCategoriesVo.getUploadFile()!=null&&StringUtils.isNotBlank(sourceCategoriesVo.getUploadFileFileName())){
				FileInputStream inputStream = new FileInputStream(sourceCategoriesVo.getUploadFile());
				book = XlsImpUtil.create(inputStream);
			}
			//313353 sonar
			String result = this.sonarSplitTwo(book);
			if(!result.isEmpty()){
				return result;
			}
			return super.returnSuccess(MessageType.IMPORT_SUCCESS);
		} catch (FileNotFoundException e) {
			return returnError("文件不存在异常:" + e.getMessage(), e);
		} catch (InvalidFormatException e) {
			return returnError("文件格式异常:" + e.getMessage(), e);
		} catch (Exception e) {
			return returnError("文件异常:" + e.getMessage(), e);
		}
	}
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 * @date 2016/09/12
	 */
	private String sonarSplitOne(Row row) {
		Cell cell = row.getCell(0);
		//如果表头第一个单元格为空，或者类型不正确，或者内容不正确，就说明格式异常
		if(cell==null||cell.getCellType()!=Cell.CELL_TYPE_STRING||
			!"行业货源品类".equals(cell.getStringCellValue().trim())){
			return returnError("文件格式异常:请按照模板添加上传数据!!!");
		}
		cell = row.getCell(1);
		//如果表头第二个单元格为空，或者类型不正确，或者内容不正确，就说明格式异常
		if(cell==null||cell.getCellType()!=Cell.CELL_TYPE_STRING||
		!"行业货源品名".equals(cell.getStringCellValue().trim())){
			return returnError("文件格式异常:请按照模板添加上传数据!!!");
		}
		return StringUtils.EMPTY;
	}
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 * @date 2016/09/12
	 */
	private String sonarSplitTwo(Workbook book) {
		if(book!=null){
			//默认获取工作表1
			Sheet sheet = book.getSheetAt(0);
			int rowNum = sheet.getPhysicalNumberOfRows();
			//目前上传数据量限定在2000条以内
			if(rowNum > NumberConstants.NUMBER_2000){
				return returnError("上传文件数据太多!!!");
			}
			//读取表头,进行校验
			Row row = sheet.getRow(0);
			if(row!=null){
				//313353 sonar 
				String result = this.sonarSplitOne(row);
				if(!result.isEmpty()){
					return result;
				}
			}else{
				return returnError("文件格式异常:请按照模板添加上传数据!!!");
			}
			List<SourceCategoriesEntity> ses= new ArrayList<SourceCategoriesEntity>();
			makeExcelToSes(sheet, ses);
			sourceCategoriesService.importSourceCategoriesEntitys(ses);
		}
		return StringUtils.EMPTY;
	}
	
	
	private void makeExcelToSes(Sheet sheet,List<SourceCategoriesEntity> ses){
		//根据词代码查询所有值代码和值名称
		List<DataDictionaryValueEntity> dataDictionaryValueEntityList = 
				dataDictionaryService.queryDataDictionaryByTermsCode(DictionaryConstants.BSE_SOURCE_CATEGORY)
				.getDataDictionaryValueEntityList();
		//获取所有的valueCode,valueName
		Map<String,String> values = new HashMap<String,String>();
		for(DataDictionaryValueEntity entity:dataDictionaryValueEntityList){
			values.put(entity.getValueName(), entity.getValueCode());
		}
		
		UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
		String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
		//记录那些行的数据无法解析
		List<Integer> numList = new ArrayList<Integer>();
		if(sheet!=null&&ses!=null){
			// 获取物理行数
			int rowCount = sheet.getPhysicalNumberOfRows();
			// 根据行数循环
			Row row = null;
			// EXCEL行记录
			SourceCategoriesEntity se = null;
			// 根据行数循环,标记外层循环
			for (int rowNum = 1; rowNum < rowCount; rowNum++) { 
				// 获取每行数据
				se = new SourceCategoriesEntity();
				se.setCreateUser(userCode);
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
					if(cellValue == null || "".equals(cellValue) || cellValue.length() > NumberConstants.NUMBER_50){
						numList.add(rowNum+1);
						break;
					}
					if(colNum==0){
						//将品类转换为code,如果没找到,说明品类无效,那么这一行数据无效
						if(values.get(cellValue)==null){
							numList.add(rowNum+1);
							break;
						}
						se.setCategory(values.get(cellValue));
					}else{
						//如果读到了品名,说明一行数据读取完成,就将数据对象增加到集合里
						se.setName(cellValue);
						ses.add(se);
					}
				}
				
			}
			sourceCategoriesVo.setNumList(numList);
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
}
