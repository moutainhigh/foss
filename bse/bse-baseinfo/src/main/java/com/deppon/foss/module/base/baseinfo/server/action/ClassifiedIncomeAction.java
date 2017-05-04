package com.deppon.foss.module.base.baseinfo.server.action;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.NumberFormat;
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
import com.deppon.foss.base.util.define.ColumnConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IClassifiedIncomeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ClassifiedIncomeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ClassifiedIncomeQueryDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.ClassifiedIncomeVo;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.util.copyUtil.XlsImpUtil;
/**
 * 重分类基础信息
 * @author 307196
 */
public class ClassifiedIncomeAction extends AbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4147101546828964662L;
	/**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassifiedIncomeAction.class);
    private IClassifiedIncomeService classifiedIncomeService;
    /**
     * 导出Excel 文件名.
     */
    private String downloadFileName;
    /**
     * 导出Excel 文件流
     */
    private InputStream inputStream;
    
    //上传文件每行的的总列数
  	private static final int CELL_COUNT = 6;
  	//上传数量5000
  	private static final int MAX_NUMBER = 5000;
	
	public String getDownloadFileName() {
		return downloadFileName;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void setClassifiedIncomeService(
			IClassifiedIncomeService classifiedIncomeService) {
		this.classifiedIncomeService = classifiedIncomeService;
	}

	private ClassifiedIncomeVo classifiedIncomeVo = new ClassifiedIncomeVo();


	public ClassifiedIncomeVo getClassifiedIncomeVo() {
		return classifiedIncomeVo;
	}

	public void setClassifiedIncomeVo(ClassifiedIncomeVo classifiedIncomeVo) {
		this.classifiedIncomeVo = classifiedIncomeVo;
	}
	/**
	 * 业务锁SERVICE
	 */
	private IBusinessLockService businessLockService;
	
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	/**
	 * 查询重分类基础信息
	 * @return
	 */
	@JSON
	public String queryClassifiedIncome(){
		
		try{
			
			//验证参数
			if(classifiedIncomeVo==null
					||classifiedIncomeVo.getClassifiedIncomeQueryDto()==null){
				throw new BusinessException("查询重分类基础信息失败,参数异常");
			}
			
			//先查询总条数
			long total = classifiedIncomeService.queryTotalByCondition(classifiedIncomeVo.getClassifiedIncomeQueryDto());
			//如果总数大于0
			if(total>0){
				//查询重分类基础信息
				List<ClassifiedIncomeEntity> returnDto = classifiedIncomeService.
						queryClassifiedIncomeByCondition(classifiedIncomeVo.getClassifiedIncomeQueryDto(), this.getStart(), this.getLimit());
				//设置返回值
				classifiedIncomeVo.setClassifiedIncomeEntityList(returnDto);
				this.setTotalCount(total);
			}
			
		}catch(BusinessException e){
			LOGGER.error(e.getMessage(), e);
			return returnError(e.getMessage(), e);
		}
		return returnSuccess();
	}
	
	/**
	 * 新增重分类基础信息
	 * @return
	 */
	@JSON
	public String addClassifiedIncome(){
		
		try{
			
			//验证参数
			if(classifiedIncomeVo==null
					||classifiedIncomeVo.getClassifiedIncomeEntity()==null){
				throw new BusinessException("新增重分类基础信息失败,参数异常");
			}
			
			// 获取当前登录用户信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 业务锁
			MutexElement mutex = new MutexElement(classifiedIncomeVo
					.getClassifiedIncomeEntity().getOwendSubsidiaryCode()
					+ classifiedIncomeVo.getClassifiedIncomeEntity()
							.getProductTypeCode(), "OWNED_SUBSIDIARY_CODE",
					MutexElementType.OWNED_SUBSIDIARY_CODE);
			LOGGER.info("开始加锁：" + mutex.getBusinessNo());
			boolean result = businessLockService.lock(mutex,
					NumberConstants.ZERO);
			if (result) {
				LOGGER.info("成功加锁：" + mutex.getBusinessNo());
				//新增
				classifiedIncomeService.addClassifiedIncome(classifiedIncomeVo.getClassifiedIncomeEntity(), currentInfo);
				LOGGER.info("开始解锁：" + mutex.getBusinessNo());
				// 解业务锁
				businessLockService.unlock(mutex);
				LOGGER.info("完成解锁：" + mutex.getBusinessNo());
				return returnSuccess();
			} else {
				return returnError("其他用户正在新增，请稍后重新提交保存！");
			}
			
		}catch(BusinessException e){
			LOGGER.error(e.getMessage(), e);
			return returnError(e.getMessage(), e);
		}
	}
	
	/**
	 * 修改重分类基础信息
	 * @return
	 */
	@JSON
	public String updateClassifiedIncome(){
		
		try{
			
			//验证参数
			if(classifiedIncomeVo==null
					||classifiedIncomeVo.getClassifiedIncomeQueryDto()==null){
				throw new BusinessException("修改重分类基础信息失败,参数异常");
			}
			
			// 获取当前登录用户信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//修改
			classifiedIncomeService.updateClassifiedIncome(classifiedIncomeVo.getClassifiedIncomeQueryDto(), currentInfo);
		}catch(BusinessException e){
			LOGGER.error(e.getMessage(), e);
			return returnError(e.getMessage(), e);
		}
		return returnSuccess();
	}
	
	/**
	 * 作废重分类基础信息
	 * @return
	 */
	@JSON
	public String deleteClassifiedIncome(){
		
		try{
			
			//验证参数
			if(classifiedIncomeVo==null
					||classifiedIncomeVo.getClassifiedIncomeQueryDto()==null){
				throw new BusinessException("删除重分类基础信息失败,参数异常");
			}
			
			// 获取当前登录用户信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//作废
			classifiedIncomeService.deleteClassifiedIncome(classifiedIncomeVo.getClassifiedIncomeQueryDto(), currentInfo);
			
		}catch(BusinessException e){
			LOGGER.error(e.getMessage(), e);
			return returnError(e.getMessage(), e);
		}
		return returnSuccess();
	}
	
	/**
	 * 导出基础信息
	 * @return
	 */
	public String exportClassifiedIncomeList() {
    	try {
	    // 导出文件名称
	    downloadFileName = URLEncoder.encode(ColumnConstants.CLASSIFIED_INCOME_NAME, "UTF-8");
	    // 获取查询参数
	    ClassifiedIncomeQueryDto entity = classifiedIncomeVo.getClassifiedIncomeQueryDto();
	    // 获取导出数据对象
	    ExportResource exportResource = classifiedIncomeService.exportClassifiedIncomeList(entity);

	    ExportSetting exportSetting = new ExportSetting();
	    // 设置名称
	    exportSetting.setSheetName(ColumnConstants.CLASSIFIED_INCOME_NAME);

	    ExporterExecutor objExporterExecutor = new ExporterExecutor();
	    // 导出成文件
	    inputStream = objExporterExecutor.exportSync(exportResource,
		    exportSetting);	

	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	} catch (UnsupportedEncodingException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError("UnsupportedEncodingException", e);
	}
    }
	/**
	 * 导入基础信息
	 */
	
		public String importClassifiedIncome(){
			Workbook book = null;
			try {
				if(classifiedIncomeVo.getUploadFile()!=null&&StringUtils.isNotBlank(classifiedIncomeVo.getUploadFileFileName())){
					FileInputStream inputStream = new FileInputStream(classifiedIncomeVo.getUploadFile());
					book = XlsImpUtil.create(inputStream);
				}
				String fileName = classifiedIncomeVo.getUploadFileFileName();
				
				if(!fileName.substring(0, NumberConstants.NUMBER_7).equals("重分类基础信息")){
					throw new BusinessException("上传文件名不正确。");
				}
				if(book==null){
					throw new BusinessException("book 为空");
				}
				//获取工作表
				Sheet sheet = book.getSheetAt(0);
				int rowNum = sheet.getPhysicalNumberOfRows();
				//目前上传数据量限定在5000条以内
				if(rowNum>MAX_NUMBER){
					return returnError("上传文件数据太多!!!");
				}
				List<ClassifiedIncomeEntity> ses= new ArrayList<ClassifiedIncomeEntity>();
				makeExcelToSes(sheet, ses);
				classifiedIncomeService.importClassifiedIncomeList(ses);
				return returnSuccess(MessageType.IMPORT_SUCCESS);
		
			} catch (FileNotFoundException e) {
				return returnError("文件不存在异常:" + e.getMessage(), e);
			} catch (InvalidFormatException e) {
				return returnError("文件格式异常:" + e.getMessage(), e);
			}catch (BusinessException e) {
				return returnError(e.getMessage(), e);
			}  catch (Exception e) {
				return returnError("文件异常:请检查是否解锁或" + e.getMessage(), e);
			}
		}
		
		private void makeExcelToSes(Sheet sheet,List<ClassifiedIncomeEntity> ses){
			//记录那些行的数据无法解析
			List<Integer> numList = new ArrayList<Integer>();
			if(sheet!=null&&ses!=null){
				// 获取物理行数
				int rowCount = sheet.getPhysicalNumberOfRows();
				// 根据行数循环
				Row row = null;
				// EXCEL行记录
				ClassifiedIncomeEntity se = null;
				// 根据行数循环,标记外层循环
				for (int rowNum = 1; rowNum < rowCount; rowNum++) {
					// 获取每行数据
					se = new ClassifiedIncomeEntity();
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
						if(cell.getCellType() == Cell.CELL_TYPE_STRING){
							cellValue = cell.getStringCellValue().trim();
						}
						if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
							NumberFormat nt = NumberFormat.getPercentInstance();
							 nt.setMinimumFractionDigits(2);
							 cellValue = nt.format(cell.getNumericCellValue());
						}
						
						//如果为空或者长度超过50说明该行数据无效,记录行数返回到页面
						if(cellValue==null||"".equals(cellValue)||cellValue.length()>NumberConstants.NUMBER_50){
							numList.add(rowNum+1);
							break;
						}
						switch(colNum){
						case 0:
							se.setProductTypeName(cellValue);
							break;
						case 1:
							se.setProductTypeCode(cellValue);
							break;
						case 2:
							se.setOwendSubsidiaryName(cellValue);
							break;
						case NumberConstants.NUMBER_3:
							se.setOwendSubsidiaryCode(cellValue);
							break;
						case NumberConstants.NUMBER_4:
							se.setSixPercent(cellValue);
							break;
						case NumberConstants.NUMBER_5:
							se.setElevenPercent(cellValue);
							ses.add(se);
							break;
						default:
							break;
						}
						
					}
				}
				classifiedIncomeVo.setNumList(numList);
			}
		}
}
