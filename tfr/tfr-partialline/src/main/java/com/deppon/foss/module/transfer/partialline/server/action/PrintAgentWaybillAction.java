package com.deppon.foss.module.transfer.partialline.server.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

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

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.common.api.shared.dto.AgentWaybillNoRequestDto;
import com.deppon.foss.module.transfer.common.api.shared.vo.AgentWaybillExceptionDto;
import com.deppon.foss.module.transfer.partialline.api.server.service.IAgentWaybillTrackService;
import com.deppon.foss.module.transfer.partialline.api.server.service.IPrintAgentWaybillService;
import com.deppon.foss.module.transfer.partialline.api.shared.define.PartiallineConstants;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.PrintAgentWaybillEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.PrintAgentWaybillRecordEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.PrintAgentWaybillDto;
import com.deppon.foss.module.transfer.partialline.api.shared.util.XlsImpUtil;
import com.deppon.foss.module.transfer.partialline.api.shared.vo.PrintAgentWaybillVo;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;

public class PrintAgentWaybillAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(PrintAgentWaybillAction.class);
	
	private IPrintAgentWaybillService printAgentWaybillService;
	private IAgentWaybillTrackService agentWaybillTrackService;
	private PrintAgentWaybillVo vo;
	
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
	
	/**
	 * @author chigo
	 * @date 2015年2月5日上午10:33:56
	 * @function 作废绑定营业部快递单号和运单号
	 * @param 
	 * @return
	 */
	public String unBundleSdExternalBill() {
		try{
			LOGGER.info("作废绑定营业部的运单信息");
			PrintAgentWaybillDto dto = new PrintAgentWaybillDto();
			String waybillNos = vo.getWaybillNos();
			String agentWaybillNos = vo.getAgentWaybillNos();
			
			dto.setWaybillNoList(this.getListFromStrs(waybillNos));
			dto.setAgentWaybillNoList(this.getListFromStrs(agentWaybillNos));
		
			printAgentWaybillService.unBundleSdExternalBill(dto);
			
			return SUCCESS;
		}catch (BusinessException e) {
			return returnError(e);
		}
		
	}
	/**
	 * @author chigo
	 * @date 2015年2月4日下午8:36:55
	 * @function 绑定营业部快递单号和运单号
	 * @param 
	 * @return
	 */
	public String bundleSdExternalBill() {
		try{
			LOGGER.info("绑定营业部的运单信息");
			PrintAgentWaybillDto dto = new PrintAgentWaybillDto();
			String waybillNos = vo.getWaybillNos();
			String agentWaybillNos = vo.getAgentWaybillNos();
			String agentCompanys = vo.getAgentCompanyCodes();
			String frights = vo.getFrightFees();
			
			dto.setWaybillNoList(this.getListFromStrs(waybillNos));
			dto.setAgentWaybillNoList(this.getListFromStrs(agentWaybillNos));
			dto.setAgentCompanyList(this.getListFromStrs(agentCompanys));
			dto.setFrightFeeList(this.getListFromStrs(frights));
			printAgentWaybillService.bundleSdExternalBill(dto);
			
			return SUCCESS;
		}catch (BusinessException e) {
			return returnError(e);
		}
		
	}
	/**
	 * @author chigo
	 * @date 2015年2月4日下午8:43:55
	 * @function 把string转为list
	 * @param s
	 * @return
	 */
	private List<String> getListFromStrs(String s) {
		List<String> list = new ArrayList<String>();
		if(StringUtils.isNotEmpty(s)) {
			String[] strs = s.split(",");
			for(String str:strs) {
				if(StringUtils.isNotEmpty(str)) {
					list.add(str);
				}
			}
		}
		return list;
	}
	/**
	 * @author chigo
	 * @date 2015年2月3日 上午11:59:56
	 * @function 查询营业部的运单信息
	 * @param 
	 * @return
	 */
	public String queryBundleWaybills() {
		try{
			LOGGER.info("查询营业部的运单信息");
			PrintAgentWaybillDto dto = new PrintAgentWaybillDto();
			String waybillNo = vo.getDto().getWaybillNo();
			String agentWaybillNo = vo.getDto().getAgentWaybillNo();
			Date beginOperatTime = vo.getDto().getBeginOperatTime();
			Date endOperatTime = vo.getDto().getEndOperatTime();
//			String bundleState = vo.getDto().getBundleState();
			if(waybillNo !=null&& !(waybillNo.equals(""))){
				dto.setWaybillNo(waybillNo);
			}
			else{
					if(agentWaybillNo !=null&& !(agentWaybillNo.equals(""))){
						dto.setAgentWaybillNo(agentWaybillNo);
					}
					if(beginOperatTime !=null && endOperatTime != null){
						dto.setBeginOperatTime(beginOperatTime);
						dto.setEndOperatTime(endOperatTime);
					}
//					if(bundleState !=null && !(bundleState.equals(""))){
//						dto.setBundleState(bundleState);
//					}
			
				}
			
			Long totalCount = printAgentWaybillService.queryBundleWaybillsCount(dto);
			
			if(totalCount > 0) {
				List<PrintAgentWaybillRecordEntity> list = printAgentWaybillService.queryBundleWaybills(dto,this.getStart(),this.getLimit());
				vo.setListRecordEntities(list);
			}
			this.setTotalCount(totalCount);
			return returnSuccess();
		}catch(BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * @author nly
	 * @date 2014年9月22日 上午10:56:31
	 * @function 查询运单信息 优先级：运单号>交接单号>入库时间>交接时间
	 * @return
	 */
	public String queryWaybills() {
//		List<PrintAgentWaybillRecordEntity> agentWaybillRecordEntities = new ArrayList<PrintAgentWaybillRecordEntity>();
//		PrintAgentWaybillRecordEntity agentWaybillRecordEntity = new PrintAgentWaybillRecordEntity();
//		agentWaybillRecordEntity.setWaybillNo("5884545446");
//		agentWaybillRecordEntities = this.printAgentWaybillService.queryWaybillsRecord(agentWaybillRecordEntity,this.getStart(),this.getLimit());
		try{
			LOGGER.info("查询打印代理面单的运单信息");
			PrintAgentWaybillDto dto = new PrintAgentWaybillDto();
			List<String> waybillNoList = this.getListFromStr(vo.getWaybillNos());
			List<String> handoverNoList = this.getListFromStr(vo.getHandOverBillNos());
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
			
			Long totalCount = printAgentWaybillService.queryWaybillCount(dto);
			
			if(totalCount > 0) {
				List<PrintAgentWaybillEntity> list = printAgentWaybillService.queryWaybills(dto,this.getStart(),this.getLimit());
				vo.setList(list);
			}
			this.setTotalCount(totalCount);
			return returnSuccess();
		}catch(BusinessException e) {
			e.printStackTrace();
			return returnError(e);
		}
	}
	/**
	 * @author nly
	 * @date 2015年4月28日 下午12:39:48
	 * @function
	 * @return
	 */
	public String addLdpBindRecords(){
		try{
			LOGGER.error("******保存快递代理绑定信息********");
			List<PrintAgentWaybillRecordEntity> recordList = vo.getListRecordEntities(); 
			CurrentInfo user = FossUserContext.getCurrentInfo();
			for(PrintAgentWaybillRecordEntity entity : recordList) {
				printAgentWaybillService.checkAgentWaybillRecord(entity);
				printAgentWaybillService.checkWaybillRecord(entity);
				printAgentWaybillService.addLdpBindRecord(entity);
				printAgentWaybillService.addTrackSyn(entity, user);
			}
			return returnSuccess();
		}catch(BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * @author nly
	 * @date 2015年4月28日 下午3:23:29
	 * @function 作废快递代理绑定
	 * @return
	 */
	public String invalidLdpBindRecords(){
		try{
			LOGGER.info("作废快递代理绑定信息");
			List<PrintAgentWaybillRecordEntity> recordList = vo.getListRecordEntities(); 
			for(PrintAgentWaybillRecordEntity entity : recordList) {
				printAgentWaybillService.invalidLdpBindRecord(entity);
			}
			return returnSuccess();
		}catch(BusinessException e) {
			return returnError(e);
		}
	}
	/**
	 * @author nly
	 * @date 2015年4月29日 上午11:04:19
	 * @function 是否存在有效的快递代理外发单
	 * @return
	 */
	public String hasLdpExternalBill(){
		try{
			LOGGER.info("判断是否有有效的快递代理外发单");
			String waybillNo = printAgentWaybillService.queryLdpExternalBill(vo.getWaybillNoList());
			vo.setWaybillNos(waybillNo);
			return returnSuccess();
		}catch(BusinessException e) {
			return returnError(e);
		}
	}
	/**
	 * @author nly
	 * @date 2014年9月9日 上午11:30:02
	 * @function 把string转为list
	 * @param s
	 * @return
	 */
	private List<String> getListFromStr(String s) {
		List<String> list = new ArrayList<String>();
		if(StringUtils.isNotEmpty(s)) {
			String[] handOverBills = s.split("\\n");
			for(String billNo:handOverBills) {
				if(StringUtils.isNotEmpty(billNo)) {
					list.add(billNo);
				}
			}
		}
		return list;
	}
	
	/**
	 *导入批量打印面单数据信息(导入excel表格数据)
	 * @Date 2015-04-29
	 * @author gongjp
	 * @param 无
	 * @return String
	 */
	public String importPrintAgentWaybillInfo(){
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
						List<PrintAgentWaybillRecordEntity> excelDtos = new ArrayList<PrintAgentWaybillRecordEntity>();
						//将Excel表格每行数据读入列表
						makeExcelDtos(excelDtos, sheet,fileSize);
						// 判空
						if (CollectionUtils.isNotEmpty(excelDtos)) {
							// 执行批量插入
							int totalCount = printAgentWaybillService.batchImportPrintAgentWaybillList(excelDtos);
							vo=new PrintAgentWaybillVo();
							// 传回成功导入条数
							vo.setImportTotalCount(totalCount);
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
					return returnError("数据异常:请确认导入数据格式是否正确！", e);

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
	private  void makeExcelDtos(List<PrintAgentWaybillRecordEntity> excelDtos, Sheet sheet,int fileSize) {
		// 判空
		//sonar-352203
		if (sheet == null || excelDtos == null) {
			return;
		}
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
			int colCnt = PartiallineConstants.EXCEL_COLUMN_MAX_SIZE;
			// EXCEL行记录
			PrintAgentWaybillRecordEntity excelDto = null;
			//用户信息
			String userCode = "";
			String userName = "";
			String orgCode= "";
			String orgName= "";
			if(FossUserContext.getCurrentInfo() != null) {
				userCode = FossUserContext.getCurrentInfo().getEmpCode();
				userName = FossUserContext.getCurrentInfo().getEmpName();
				orgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
				orgName = FossUserContext.getCurrentInfo().getCurrentDeptName();
			}
			List<String> list = new ArrayList<String>();
			// 根据行数循环
			for (int rowNum = 1; rowNum < rowCount; rowNum++) {
				// 获取每行数据
				excelDto = new PrintAgentWaybillRecordEntity();
				excelDto.setOperatorCode(userCode);
				excelDto.setOperatorName(userName);
				excelDto.setOrgCode(orgCode);
				excelDto.setOrgName(orgName);
				// 取得一行的数据
				row = sheet.getRow(rowNum);
				//判断单元格值是否满足要求
				//TO-DO
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
				//导入excel第四列已经去掉，所以去除校验
			/*	if (row == null || row.getCell(PartiallineConstants.EXCEL_COLUMN_FOUR) == null
		                        || StringUtils.isBlank(obtainStringVal(row.getCell(PartiallineConstants.EXCEL_COLUMN_FOUR)))){
					list.add("第"+rowNum+"行第"+(PartiallineConstants.EXCEL_COLUMN_FOUR+1)+"列数据为空");
                 }*/if(list!=null&&list.size()>0){
                	 String error = changeArrayToString(list);
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
				//modified by 257220:代理公司从后台根据提货网点获取
                //excelDto.setAgentCompanyCode(this.getCmpCode(excelDto.getAgentCompanyName()));
                //校验
                printAgentWaybillService.checkWaybillIsOuter(excelDto);//运单号是否外发
				printAgentWaybillService.checkAgentWaybillRecord(excelDto);
				printAgentWaybillService.checkWaybillRecord(excelDto);
				printAgentWaybillService.checkStockAndHandOverbill(excelDto);
				excelDtos.add(excelDto);
			}
//		}
	}
	
	/**
	 * 将集合转换成字符串
	 * @param list
	 * @author gongjp
	 * @date 2015-05-06
	 * @param list
	 * @return String
	 */
	public  String changeArrayToString(List<String> list) {
		StringBuilder sb=new StringBuilder();
		if(list!=null&&list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				String error = list.get(i);
				sb.append(error+",");
				if(i%ConstantsNumberSonar.SONAR_NUMBER_4==0){
					sb.append("\r\n");
				}
			}
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}
	
	/**
	 * @author gongjp
	 * @param agentCompanyName
	 * @return String
	 */
	private String getCmpCode(String agentCompanyName) {
		String cmpCode = "";
		if(PartiallineConstants.agentWaybillMap.get("STO").equals(agentCompanyName)){
			cmpCode = "STO";
		} else if(PartiallineConstants.agentWaybillMap.get("YTO").equals(agentCompanyName)) {
			cmpCode = "YTO";
		} else if(PartiallineConstants.agentWaybillMap.get("ZTO").equals(agentCompanyName)) {
			cmpCode = "ZTO";
		} else if(PartiallineConstants.agentWaybillMap.get("YUNDA").equals(agentCompanyName)) {
			cmpCode = "YUNDA";
		} else if(PartiallineConstants.agentWaybillMap.get("TTK").equals(agentCompanyName)) {
			cmpCode = "TTK";
		} else if(PartiallineConstants.agentWaybillMap.get("UC").equals(agentCompanyName)) {
			cmpCode = "UC";
		} else if(PartiallineConstants.agentWaybillMap.get("SF").equals(agentCompanyName)) {
			cmpCode = "SF";
		} else if(PartiallineConstants.agentWaybillMap.get("BEST").equals(agentCompanyName)) {
			cmpCode = "BEST";
		} else if(PartiallineConstants.agentWaybillMap.get("EMS").equals(agentCompanyName)) {
			cmpCode = "EMS";
		} else if(PartiallineConstants.agentWaybillMap.get("ZJS").equals(agentCompanyName)) {
			cmpCode = "ZJS";
		} else if(PartiallineConstants.agentWaybillMap.get("QF").equals(agentCompanyName)) {
			cmpCode = "QF";
		} else if(PartiallineConstants.agentWaybillMap.get("GTO").equals(agentCompanyName)) {
			cmpCode = "GTO";
		} 
		//modified by 257220 新增代理公司
		else if(PartiallineConstants.agentWaybillMap.get("UNITOP").equals(agentCompanyName)){//全一快递	
			cmpCode = "UNITOP";
		}else if(PartiallineConstants.agentWaybillMap.get("ANYTIME").equals(agentCompanyName)){	//全日通	
			cmpCode = "ANYTIME";
		}else if(PartiallineConstants.agentWaybillMap.get("QC").equals(agentCompanyName)){	//全晨快递	
			cmpCode = "QC";
		}else if(PartiallineConstants.agentWaybillMap.get("SUER").equals(agentCompanyName)){	//速尔快递	
			cmpCode = "SUER";
		}else if(PartiallineConstants.agentWaybillMap.get("HMJ").equals(agentCompanyName)){	//黄马甲快递	
			cmpCode = "HMJ";
		}else if(PartiallineConstants.agentWaybillMap.get("RUFENGDA").equals(agentCompanyName)){	//如风达	
			cmpCode = "RUFENGDA";
		}else if(PartiallineConstants.agentWaybillMap.get("YUTONG").equals(agentCompanyName)){	//运通快递	
			cmpCode = "YUTONG";
		}
		return cmpCode;
	}

	/**
	 * 
	 * 单元格取值
	 * @author gongjp
	 * @date 2015-04-29
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
	private void obtainCellValue(Row row, int colNum, PrintAgentWaybillRecordEntity excelDto) throws ParseException {
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
					case 0:// 导入代理单号
						excelDto.setAgentWaybillNo(cellVal);
						return;
					case 1:// 运单号
						boolean flag = checkWaybillNOIsNumeric(cellVal);
						if(!flag){
							throw new BusinessException("您导入的运单号为"+cellVal+",运单号必须为10位数字","您导入的运单号为"+cellVal+",运单号必须为10位数字");
						}
						excelDto.setWaybillNo(cellVal);
						return;
					case 2:// 流水号
						cellVal=changeSerialNoFormat(cellVal);
						excelDto.setSerialNo(cellVal);
						return;
//modified by 257220:代理公司直接从后台根据提货网点获取						
//					case 3:// 代理公司名称
//						excelDto.setAgentCompanyName(cellVal);
//						return;
					default:
						return;
					}					
				}

			}
		}
	}
	
	/**@author gongjp
	 * @Date 2015-04-30
	 * 转换流水号格式默认为四位(解决excel导入后以1.0的形式转变成0001的格式)
	 * @param cellVal
	 * @return String
	 */
	public  String changeSerialNoFormat(String cellVal) {
		if(cellVal.length()>0){
			int index=cellVal.indexOf(".0");
			if(index != -1){
			    String finalResult=cellVal.substring(0, index);
			    switch (finalResult.length()) {
				case 1:
				case 2:
					cellVal="000"+finalResult;
					break;
				case ConstantsNumberSonar.SONAR_NUMBER_3:
					cellVal="0"+finalResult;
					break;
				case ConstantsNumberSonar.SONAR_NUMBER_4:
					cellVal=finalResult;
					break;				
				default:
					break;
				}
			}
		}
		return cellVal;
	}

	/**
	 * 检测导入的运单号是否为十位数字
	 * @author gongjp
	 * @Date 2015-05-05
	 * @param String
	 * @return boolean
	 */

	public boolean checkWaybillNOIsNumeric(String waybillNo) {
		Pattern pattern = Pattern.compile("[0-9]{10}");
		return pattern.matcher(waybillNo).matches();
	}
	/**
	 * 人工订阅代理单号
	 * @return
	 * @author 257220
	 * @date 2015-7-28上午10:09:35
	 */
	public String trackOrder(){
		try{
			LOGGER.info("人工轨迹订阅开始");
			List<PrintAgentWaybillRecordEntity> recordList = vo.getListRecordEntities(); 
			if(recordList == null || recordList.size() == 0){
				return returnSuccess("请选择数据！");
			}
			for(PrintAgentWaybillRecordEntity entity : recordList) {
				//数据检查
				printAgentWaybillService.checkAgentWaybillCanDoOrder(entity);
			}
			List<AgentWaybillNoRequestDto> agentWaybillNoRequestDtoList = printAgentWaybillService.buildAgentWaybillNoRequestDto(recordList);
			//推送订阅 返回未订阅成功的运单
			List<AgentWaybillExceptionDto> exceptionList = agentWaybillTrackService.pushAgentWaybillNos(agentWaybillNoRequestDtoList);
			if(exceptionList == null || exceptionList.size() == 0){
				return returnSuccess();
			}else{
				String failAgentWaybillNos = "";//未成功的运单号
				for (AgentWaybillExceptionDto agentWaybillExceptionDto : exceptionList) {
					failAgentWaybillNos = failAgentWaybillNos + agentWaybillExceptionDto.getWaybillNo() + ",";
				}
				if(failAgentWaybillNos.length() > 0){
					failAgentWaybillNos = failAgentWaybillNos.substring(0,failAgentWaybillNos.length()-1);
				}
				return returnError("代理单号："+failAgentWaybillNos+"订阅失败");
			}
			
		}catch(BusinessException e) {
			e.printStackTrace();
			return returnError(e);
		}
	}
	/**
	 * 停止轨迹订阅
	 * @return
	 * @author 257220
	 * @date 2015-8-5下午2:20:38
	 */
	public String stopTrackOrder(){
		try{
			LOGGER.info("停止轨迹订阅开始...");
			List<PrintAgentWaybillRecordEntity> recordList = vo.getListRecordEntities();
			//循环更新轨迹订阅状态为停止订阅
			for (PrintAgentWaybillRecordEntity printAgentWaybillRecordEntity : recordList) {
				printAgentWaybillService.stopTrackOrder(printAgentWaybillRecordEntity);
			}
			return returnSuccess();
		}catch(BusinessException e){
			e.printStackTrace();
			return returnError(e);
		}
	}
	public void setPrintAgentWaybillService(
			IPrintAgentWaybillService printAgentWaybillService) {
		this.printAgentWaybillService = printAgentWaybillService;
	}

	public PrintAgentWaybillVo getVo() {
		return vo;
	}

	public void setVo(PrintAgentWaybillVo vo) {
		this.vo = vo;
	}

	public void setAgentWaybillTrackService(
			IAgentWaybillTrackService agentWaybillTrackService) {
		this.agentWaybillTrackService = agentWaybillTrackService;
	}
	
}
