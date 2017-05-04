package com.deppon.foss.module.transfer.packaging.server.action;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.management.api.shared.define.FuelConsonptionConstants;
import com.deppon.foss.module.transfer.management.api.shared.util.XlsImpUtil;
import com.deppon.foss.module.transfer.packaging.api.server.service.IPackageAssistPriceService;
import com.deppon.foss.module.transfer.packaging.api.server.service.IPackageMainPriceService;
import com.deppon.foss.module.transfer.packaging.api.shared.define.PackagingConstants;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.PackageAssistPriceEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.PackageMainPriceEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.dto.QueryAssistPackedDto;
import com.deppon.foss.module.transfer.packaging.api.shared.vo.QueryPackedPriceVo;
import com.deppon.foss.util.CollectionUtils;
/**
 * @desc 辅助包装action
 * @author foss-105795-wqh
 * @date   2014-05-14
 * **/
public class PackageAssistPriceAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3502773435387164899L;
	
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LogManager.getLogger(PackageAssistPriceAction.class);

	private QueryPackedPriceVo queryPackedPriceVo=new QueryPackedPriceVo();
	
	//辅助包装金额service
	private IPackageAssistPriceService packageAssistPriceService;
	//主要包装金额信息
	private IPackageMainPriceService packageMainPriceService;
	
	/**
	 * 获取指定部门接口
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	//获取开单信息
	private IWaybillManagerService waybillManagerService;

	
	//导出文件名
    private String excelFileName; 
	/**
	 * 导出流
	 */
    private transient InputStream packedAssistPirceExcelStream;
    
    
    /**
	 * 导入文件
	 */
	private File uploadFile;
    
    /**
	 * 导入文件名
	 */
	private String uploadFileFileName;
	@JSON
	public String queryPackedAssistPriceById(){
		try{
			PackageAssistPriceEntity packageAssis = packageAssistPriceService.queryPackedAssistPriceById(queryPackedPriceVo.getId());
			if(packageAssis != null){
				queryPackedPriceVo.setPackageAssistPriceEntity(packageAssis);
				return this.returnSuccess();
			}else{
				return this.returnError("该运单辅助包装信息不存在");
			}
		}catch(TfrBusinessException e){
			return this.returnError(e);
		}
	}
	/** 
	 * @desc 审核辅助包装金额信息
	 * @return string
	 * @author 042795-foss-duyi
	 * @date 2014-06-25 上午9:56:16
	 */
    @JSON
    public String auditPackedAssistPrice(){
    	try{
    		packageAssistPriceService.auditPackedAssistPrice(queryPackedPriceVo.getIds());
    	}catch(TfrBusinessException e){
    		return this.returnError(e);
    	}
    	return this.returnSuccess();
    }
    /** 
	 * @desc 反审核辅助包装金额信息
	 * @return string
	 * @author 042795-foss-duyi
	 * @date 2014-06-25 上午9:56:16
	 */
    @JSON
    public String deauditPackedAssistPrice(){
    	try{
    		packageAssistPriceService.deauditPackedAssistPrice(queryPackedPriceVo.getIds());
    	}catch(TfrBusinessException e){
    		return this.returnError(e);
    	}
    	return this.returnSuccess();
    }
    
	/**
	 * @desc 添加辅助包装金额信息
	 * @return string
	 * @author 105795
	 * @date   2014-05-20
	 * 
	 * **/
	@JSON
	public String addPackageAssistPrice()
	{
		try {
			PackageAssistPriceEntity packageAssistPriceEntity=queryPackedPriceVo.getPackageAssistPriceEntity();
			
			packageAssistPriceService.addPackageAssistPrice(packageAssistPriceEntity);
			
			
			return this.returnSuccess();
		} catch (TfrBusinessException e) {
			return this.returnError(e);
		}
		
		
	}
	
	/**
	 * @desc 添加辅助包装金额信息
	 * @return string
	 * @author 105795
	 * @date   2014-05-20
	 * 
	 * **/
	@JSON
	public String updatePackageAssistPrice()
	{
		try {
			
			PackageAssistPriceEntity packageAssistPriceEntity=queryPackedPriceVo.getPackageAssistPriceEntity();
			Date modifyTime = packageAssistPriceService.updatePackageAssistPrice(packageAssistPriceEntity);
			packageAssistPriceEntity.setModifyTime(modifyTime);
			return this.returnSuccess();
		} catch (TfrBusinessException e) {
			return this.returnError(e);
		}
	}
	
	/**
	 * @desc 查询辅助包装金额信息，分页
	 * @return string
	 * @author 105795
	 * @date   2014-05-14
	 * 
	 * **/
	@JSON
	public String queryAssistPirceList(){
		//接收前台传来的值
		QueryAssistPackedDto queryCondition=queryPackedPriceVo.getQueryAssistPackedDto();
		//判断运单号是否为空，如果不为空，则其他条件为空
		
	    	
	    	
		String waybillNo=queryCondition.getWaybillNo();
		if(StringUtil.isNotEmpty(waybillNo))
		{
			queryCondition.setPackDeptCode(null);
			queryCondition.setWaybillCreateDept(null);
			queryCondition.setPackageSupplierCode(null);
			queryCondition.setPackedBeginDate(null);
			queryCondition.setPackedEndDate(null);
			queryCondition.setAuditStatus(null);
			
		}
		 
		try {
			
			List<PackageAssistPriceEntity> packageAssistPriceEntityList=
					packageAssistPriceService.queryAssistPirceList(queryCondition, this.getLimit(), this.getStart());
			//查询总条数
			
			long totalCount=packageAssistPriceService.queryAssistPirceList(queryCondition).size();
			//设置总条数
			this.setTotalCount(totalCount);

			queryPackedPriceVo.setPackageAssistPriceEntityList(packageAssistPriceEntityList);
		} catch (TfrBusinessException e) {
			return this.returnError(e);
		}
		
		
		return this.returnSuccess();
	}
	
	
	/**
	 * @desc 根据运单号、包装供应商，当前外场查询 主要包装中是否存在该条记录
	 * @return string
	 * @author 105795
	 * @date   2014-05-16
	 * 
	 * **/
	@JSON
	public String queryPackageMainPriceByWaybillNoAndpackageSupplierCode()
	{
		//接收前台传来的值
		QueryAssistPackedDto queryCondition=queryPackedPriceVo.getQueryAssistPackedDto();
		//判断运单号是否为空，如果不为空，则其他条件为空
		// 当前操作部门
		List<String> bizTypes = new ArrayList<String>();
		// 设置外场类型
		bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		OrgAdministrativeInfoEntity activeDeptInfo = FossUserContext
				.getCurrentDept();
		//组织对象
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoComplexService
				.queryOrgAdministrativeInfoByCode(activeDeptInfo.getCode(),
						bizTypes);
		if (org == null) {
			return this.returnError("当前登录人无操作部门");
		} else {
			// 无操作
		}
		
		String waybillNo=queryCondition.getWaybillNo();
		if(StringUtil.isEmpty(waybillNo))
		{
			return this.returnError("请输入运单号");
		}
		//判断运单号是否存在
		WaybillEntity waybillEntity=waybillManagerService.queryWaybillBasicByNo(waybillNo); 
		if(waybillEntity==null)
		{
			return this.returnError("isNotExist");
			
		}
		try {
			List<PackageMainPriceEntity> packageMainPriceEntityList=
					packageMainPriceService.queryMainPackagePriceByWaybillNoAndSupplierCode(waybillNo, org.getCode(), queryCondition.getPackageSupplierCode());
		
			if(packageMainPriceEntityList==null ||packageMainPriceEntityList.size()<1)
			{
				
				return this.returnSuccess();
			}else{
				
				throw new TfrBusinessException("运单："+waybillNo+"PDA端已经扫描，不能录入打木架、打木箱体积");

			}
			
		} catch (TfrBusinessException e) {
			return this.returnError(e);
		}
		
	}
	
	
	/**
	 * @desc 根据id删除辅助包装信息
	 * @return string
	 * @author 105795
	 * @date   2014-05-22
	 * 
	 * **/
	@JSON
	public String deletePackedAssistPriceById()
	{
		try {
			
			
		    	
			PackageAssistPriceEntity packageAssistPriceEntity=queryPackedPriceVo.getPackageAssistPriceEntity();
			packageAssistPriceService.deletePackedAssistPriceById(packageAssistPriceEntity.getId());
			
			
			return returnSuccess();
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
		
	}
	
	
	/**
	 * @desc 导出辅助包装金额信息
	 * @return string
	 * @author foss中转-105795-wqh
	 * @date   2014-05-27
	 * 
	 * **/
	@SuppressWarnings("rawtypes")
	public String exportPackedAssistPriceExcel()
	{
		//接收前台传来的值
		QueryAssistPackedDto queryCondition=queryPackedPriceVo.getQueryAssistPackedDto();
		//判断运单号是否为空，如果不为空，则其他条件为空
		
	    	
	    	
		String waybillNo=queryCondition.getWaybillNo();
		if(StringUtil.isNotEmpty(waybillNo))
		{
			queryCondition.setPackDeptCode(null);
			queryCondition.setWaybillCreateDept(null);
			queryCondition.setPackageSupplierCode(null);
			queryCondition.setPackedBeginDate(null);
			queryCondition.setPackedEndDate(null);
			queryCondition.setAuditStatus(null);
			
		}
		List list = null;
		try {
			list=packageAssistPriceService.exportPackedAssistPriceExcel(queryCondition);
			excelFileName =(String) list.get(0);
			packedAssistPirceExcelStream = (InputStream)list.get(1);
			return this.returnSuccess();
		} catch (TfrBusinessException e) {
			
			return this.returnError(e);
		}finally{
			
			if(packedAssistPirceExcelStream!=null)
			{
				try {
					packedAssistPirceExcelStream.close();
				} catch (IOException e) {
					throw new TfrBusinessException("文件关闭失败", "");
				}	
			}
			
		}
		
	}
	
	/**
	 * @desc 下载导入辅助包装金额模版
	 * @return string
	 * @author foss中转-105795-wqh
	 * @date   2014-05-27
	 * 
	 * **/
	@SuppressWarnings("rawtypes")
	public String downloadMasterplateToAssistExcel()
	{
		List list = null;
		try {
			list=packageAssistPriceService.downloadMasterplateToAssistExcel();
			excelFileName =(String) list.get(0);
			packedAssistPirceExcelStream = (InputStream)list.get(1);
			return this.returnSuccess();
		} catch (TfrBusinessException e) {
			if(packedAssistPirceExcelStream!=null)
			{
				
					try {
						packedAssistPirceExcelStream.close();
					} catch (IOException e1) {
						throw new TfrBusinessException("文件关闭失败", "");
					}
				
			}
			return this.returnError(e);
		}
		
	}
	
	/**
	 * @desc 导入辅助包装金额
	 * @return string
	 * @author foss中转-105795-wqh
	 * @date   2014-06-04
	 * 
	 * **/
	public String importPackedAssistPriceInfo()
	{
		
		// 文件
		Workbook book = null;
		// 输入流
		FileInputStream inputStream = null;
		// 抓取文件类型
		try {
			// 文件名不为空
			// 判空
			if (StringUtils.isNotBlank(uploadFileFileName)) {
				// 判空
				if (uploadFileFileName.endsWith(FuelConsonptionConstants.EXCEL_FILE_TAIL_XLS_DOWN)
						|| uploadFileFileName.endsWith(FuelConsonptionConstants.EXCEL_FILE_TAIL_XLS_UP)
						|| uploadFileFileName.endsWith(FuelConsonptionConstants.EXCEL_FILE_TAIL_XLSX_DOWN)
						|| uploadFileFileName.endsWith(FuelConsonptionConstants.EXCEL_FILE_TAIL_XLSX_UP)) {
					// 验证通过
				} else {
					throw new BusinessException("只支持.xls或.xlsx格式的文件,请选择正确的文件导入", "只支持.xls或.xlsx格式的文件,请选择正确的文件导入");
				}
			}
			// 判空
			if (uploadFile != null) {
				inputStream = new FileInputStream(uploadFile);
				
				try {
					book = XlsImpUtil.create(inputStream);
				} catch (Exception ex) {
					LOGGER.error("非2003格式文件，转2007格式，或文件加密");
					throw new BusinessException("非2003格式文件，转2007格式，或文件加密", "非2003格式文件，转2007格式，或文件加密");
				}
			} else {
				throw new BusinessException("请选择导入文件", "请选择导入文件");
			}
			// 解析导入的数据文件
			// 判空
			if (book != null) {
				// 默认获取获取工作表1
				Sheet sheet = book.getSheetAt(FuelConsonptionConstants.EXCEL_DEFAULT_SHEET_ONE);

				// 读取Excel的所用数据,考虑到数据量不大，暂时不适用集中缓存
				List<PackageAssistPriceEntity> packageAssistPriceList=new ArrayList<PackageAssistPriceEntity>();
				// 将Excel表格每行数据读入列表
				readExcel(packageAssistPriceList,sheet);
				//判断导入条数是否大于1000
				if(packageAssistPriceList.size()>PackagingConstants.PACKAGE_ASSIST_IMPORT_COUNT)
				{
					LOGGER.error("导入不能大于："+PackagingConstants.PACKAGE_ASSIST_IMPORT_COUNT+" 条");
					throw new TfrBusinessException("导入不能大于："+PackagingConstants.PACKAGE_ASSIST_IMPORT_COUNT+" 条");
				}
				
				// 判空
				if (CollectionUtils.isNotEmpty(packageAssistPriceList)) {
					//检验导入的数据中是否存在同一单且同一包装供应商多条记录
					checkImportRepeat(packageAssistPriceList);
					
					// 执行批量插入
					int totalCount = packageAssistPriceService.importPackedAssistPriceInfo(packageAssistPriceList);
					// 传回成功导入条数
					queryPackedPriceVo.setImportTotalCount(totalCount);
				} else {
					throw new BusinessException("导入数据为空", "导入数据为空");
				}
			}

			// 成功
			return super.returnSuccess();
			// 异常
		} catch (TfrBusinessException e) {
			// 日志
			LOGGER.error(e);
			// 异常信息
			return super.returnError(e);
			// 异常
		} catch (FileNotFoundException e) {
			// 日志
			LOGGER.error("数据文件不存在", e);
			// 异常信息
			return returnError("数据文件不存在");
			// 异常信息
		} catch (Exception e) {
			// 日志
			LOGGER.error(e);
			// 异常信息
			return returnError("数据异常:请确认导入数据格式是否正确！"+e.getMessage());

		} finally {
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
					LOGGER.error(e);
					return returnError("文件关闭失败");
				}
			}
		}
		
	}
	
	
	
	/***
	 * 读取excel的内容
	 * 
	 * */
	private void readExcel(List<PackageAssistPriceEntity> packageAssistPriceList ,Sheet sheet ){
		if (sheet != null && packageAssistPriceList != null) {
		// 获取物理行数
		int rowCount = sheet.getPhysicalNumberOfRows();
		// 根据行数循环
		Row row = null;
		//列
		Cell cell=null;
	    
	    PackageAssistPriceEntity packageAssistPriceEntity=null;
	    
	    //第一行是表头，从第二行开始读取
	    for(int rowNum=1;rowNum<rowCount;rowNum++)
	    {
	    	packageAssistPriceEntity=new PackageAssistPriceEntity();
	    	//获取本行
	    	row=sheet.getRow(rowNum);
	    	//如果本行为空则结束
	    	if(row==null)
	    	{
	    		continue;
	    	}
	    	
	    	//如果导入行，有一行值为空则导入失败，并提示用户
	    	
	    	//运单号
	    	cell=row.getCell(PackagingConstants.PACKAGE_ASSIST_IMPORT_WAYBILLNO);
	    	if(cell==null)
	    	{
	    		throw new TfrBusinessException("运单号不能为空");
	    	}
	    	//设置运单号为sting
	    	cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	    	
	    	String waybillNo=obtainStringVal(cell);
	    	/*//处理运单字符串形如  45678963.0
	    	int endIndex=waybillNo.indexOf(".");
	    	//截取子字符串
	    	waybillNo=waybillNo.substring(0,endIndex);*/
	    	
	    	packageAssistPriceEntity.setWaybillNo(waybillNo);
	    	
	    	//包装供应商编码
	    	cell=row.getCell(PackagingConstants.PACKAGE_ASSIST_IMPORT_SUPPLIERCODE);
	    	if(cell==null)
	    	{
	    		throw new TfrBusinessException("运单:"+waybillNo+" 对应的包装供应商编码不能为空");
	    	}
	    	//如果录入包装供应商全部是数字，这里需要设置为字符串
	    	cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	    	String packageSupplierCode=obtainStringVal(cell);
	    	packageAssistPriceEntity.setPackageSupplierCode(packageSupplierCode);
	    	
	    	//实际打木架体积
	    	cell=row.getCell(PackagingConstants.PACKAGE_ASSIST_IMPORT_FRAME_VOLUME);
	    	if(cell==null)
	    	{
	    		throw new TfrBusinessException("运单:"+waybillNo+" 对应的实际打木架体积不能为空,如不存在实际打木架体积，请用0表示");
	    	}
	    	String actualFrameVolume=obtainStringVal(cell);
	    	packageAssistPriceEntity.setActualFrameVolume(checkNumber("实际打木架体积",actualFrameVolume));
	    	
	    	//实际打木箱体积
	    	cell=row.getCell(PackagingConstants.PACKAGE_ASSIST_IMPORT_BOX_VOLUME);
	    	if(cell==null)
	    	{
	    		throw new TfrBusinessException("运单:"+waybillNo+" 对应的实际打木箱体积不能为空,如不存在实际打木箱体积，请用0表示");
	    	}
	    	String actualWoodenVolume=obtainStringVal(cell);
	    	packageAssistPriceEntity.setActualWoodenVolume(checkNumber("实际打木箱体积",actualWoodenVolume));
	    	
	    	
	    	//实际打木托个数
	    	cell=row.getCell(PackagingConstants.PACKAGE_ASSIST_IMPORT_MASK_NUM);
	    	if(cell==null)
	    	{
	    		throw new TfrBusinessException("运单:"+waybillNo+" 对应的实际打木托个数不能为空,如不存在实际打木托个数，请用0表示");
	    	}
	    	String actualMaskNumber=obtainStringVal(cell);
	    	packageAssistPriceEntity.setActualMaskNumber(checkNumber("实际打木托个数",actualMaskNumber));
	    	packageAssistPriceEntity.setPackageMaskNumber(checkNumber("实际打木托个数",actualMaskNumber));
	    	
	    	//木条长度
	    	cell=row.getCell(PackagingConstants.PACKAGE_ASSIST_IMPORT_WOODBAR_NUM);
	    	if(cell==null)
	    	{
	    		throw new TfrBusinessException("运单:"+waybillNo+" 对应的木条长度不能为空,如不存在木条长度，请用0表示");
	    	}
	    	String woodenBarLong=obtainStringVal(cell);
	    	packageAssistPriceEntity.setWoodenBarLong(checkNumber("木条长度",woodenBarLong));
	    	
	    	
	    	//气泡膜体积
	    	cell=row.getCell(PackagingConstants.PACKAGE_ASSIST_IMPORT_BUBBVELAMEN_VOLUME);
	    	if(cell==null)
	    	{
	    		throw new TfrBusinessException("运单:"+waybillNo+" 对应的气泡膜体积不能为空,如不存在气泡膜体积，请用0表示");
	    	}
	    	String bubbVelamenVolume=obtainStringVal(cell);
	    	packageAssistPriceEntity.setBubbVelamenVolume(checkNumber("气泡膜体积",bubbVelamenVolume));
	    	
	    	//缠绕膜体积
	    	cell=row.getCell(PackagingConstants.PACKAGE_ASSIST_IMPORT_BINDVELAMEN_NUM);
	    	if(cell==null)
	    	{
	    		throw new TfrBusinessException("运单:"+waybillNo+" 对应的缠绕膜体积不能为空,如不存在缠绕膜体积，请用0表示");
	    	}
	    	String bindVelamenVolume=obtainStringVal(cell);
	    	packageAssistPriceEntity.setBindVelamenVolume(checkNumber("缠绕膜体积",bindVelamenVolume));
	    	
	    	//包带根数
	    	cell=row.getCell(PackagingConstants.PACKAGE_ASSIST_IMPORT_BAGBEL_NUM);
	    	if(cell==null)
	    	{
	    		throw new TfrBusinessException("运单:"+waybillNo+" 对应的包带根数不能为空,如不存在包带根数，请用0表示");
	    	}
	    	String bagBeltNum=obtainStringVal(cell);
	    	packageAssistPriceEntity.setBagBeltNum(checkNumber("包带根数",bagBeltNum).intValue());
	    	
	    	
	     //将本行记录放入list
	    	packageAssistPriceList.add(packageAssistPriceEntity);
	    	
	    }
	      
		
		}
		
	}
	
	
	
	/**
	 * 
	 * 单元格取值
	 * 
	 * @author 105795-wqh-foss中转开发组
	 * 
	 * @date 2014-06-05 上午9:22:47
	 * 
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
			if (FuelConsonptionConstants.NAN.equals(cellVal)) {// 如果获取的数据值为非法值,则转换为获取字符串
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
	 * 检验是否为数据类型
	 * 
	 * **/
	private BigDecimal checkNumber(String cellName,String value)
	{
		try {
			
			Double.parseDouble(value);
			
		} catch (NumberFormatException e) {
			LOGGER.error("录入值只能是数字");
			throw new TfrBusinessException(cellName+" 录入值只能是数字");
		}
		
		return new BigDecimal(value);
	}
	
	/**
	 * 
	 * 判断导入数据是否存在重复
	 *   判断标准：存在同一单且同一包装供应商
	 * 
	 * **/
	private void checkImportRepeat(List<PackageAssistPriceEntity> packageAssistPriceList)
	{
		for(int i=0;i<packageAssistPriceList.size()-1;i++)
		{
			String waybillNo1=packageAssistPriceList.get(i).getWaybillNo();
			String supplierCode1=packageAssistPriceList.get(i).getPackageSupplierCode();
			
			for(int j=i+1;j<packageAssistPriceList.size();j++)
			{
				
				String waybillNo2=packageAssistPriceList.get(j).getWaybillNo();
				String supplierCode2=packageAssistPriceList.get(j).getPackageSupplierCode();


				if(waybillNo1.equals(waybillNo2)&&supplierCode1.equals(supplierCode2))
				{
					
					LOGGER.error("导入信息中，存在多条："+waybillNo1+" 包装供应商编码："+supplierCode1+" 的记录");
					throw new TfrBusinessException("导入信息中，存在多条："+waybillNo1+"; 包装供应商编码："+supplierCode1+" 的记录");
					
				}
				
				
			}
			
		}
		
		
		
	}
	
	
	
	//get and set
	public QueryPackedPriceVo getQueryPackedPriceVo() {
		return queryPackedPriceVo;
	}

	public void setQueryPackedPriceVo(QueryPackedPriceVo queryPackedPriceVo) {
		this.queryPackedPriceVo = queryPackedPriceVo;
	}





	public void setPackageAssistPriceService(
			IPackageAssistPriceService packageAssistPriceService) {
		this.packageAssistPriceService = packageAssistPriceService;
	}
	public void setPackageMainPriceService(
			IPackageMainPriceService packageMainPriceService) {
		this.packageMainPriceService = packageMainPriceService;
	}


	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}


	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public String getExcelFileName() {
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}

	public InputStream getPackedAssistPirceExcelStream() {
		return packedAssistPirceExcelStream;
	}

	public void setPackedAssistPirceExcelStream(
			InputStream packedAssistPirceExcelStream) {
		this.packedAssistPirceExcelStream = packedAssistPirceExcelStream;
	}

	public File getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}

	public String getUploadFileFileName() {
		return uploadFileFileName;
	}

	public void setUploadFileFileName(String uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}

	
	
	
}
