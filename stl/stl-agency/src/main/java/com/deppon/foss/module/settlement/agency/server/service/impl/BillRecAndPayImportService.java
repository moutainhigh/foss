package com.deppon.foss.module.settlement.agency.server.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAirAgencyCompanyService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAirlinesAgentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAirlinesService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAgentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.agency.api.server.service.IBillRecAndPayImportService;
import com.deppon.foss.module.settlement.agency.api.shared.dto.BillRecAndPayImportDto;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.transfer.airfreight.api.define.AirfreightConstants;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirWaybillDetailService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirWaybillService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 导入空运应收、应付信息
 * 
 * @author foss-pengzhen
 * @date 2012-11-13 下午2:30:55
 * @since
 * @version
 */
public class BillRecAndPayImportService implements IBillRecAndPayImportService {

	/**
	 * 应付单service
	 */
	private IBillPayableService billPayableService;
	
	/** 
	 * 查询航空正单 
	*/
	private IAirWaybillDetailService  pointsSingleJointTicketService; 
	
	/**
	 * 应收单service
	 */
	private IBillReceivableService billReceivableService;

	/**
	 * 注入中转查询正单号接口
	 */
	private IAirWaybillService airWaybillService;

	/**
	 * 注入接送货查询运单号接口
	 */
	private IWaybillManagerService waybillManagerService;

	/**
	 * 注入获取单号的公共接口
	 */
	private ISettlementCommonService settlementCommonService;

	/**
	 * 根据客户code获取信息
	 */
	private IAirAgencyCompanyService airAgencyCompanyService;
	
	/**
	 * 注入组织信息接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * 注入获取配置参数接口
	 */
	private IConfigurationParamsService configurationParamsService;
	
	/**
	 * 注入航空公司接口
	 */
	private IAirlinesService airlinesService;
	
	/**
	 * 注入航空公司代理人接口
	 */
	private IAirlinesAgentService airlinesAgentService;
	
	/**
	 * 定义所对应正单号、行的常量
	 */
	private static final int AIR_REC_PAYABLE_BILL_ZERO = 0;
	
	/**
	 * 定义所对应的运单号的常量
	 */
	private static final int AIR_REC_PAY_WAYBILLNO_ONE = 1;
	
	/**
	 * 定义所对应的客户的常量
	 */
	private static final int AIR_REC_PAY_CUSTOMERCODE_TWO = 2;
	
	/**
	 * 定义所对应的应收的常量
	 */
	private static final int AIR_REC_PAY_RECAMOUNT_THREE = 3;
	
	/**
	 * 定义所对应的应付的常量
	 */
	private static final int AIR_REC_PAY_PAYAMOUNT_FOUR = 4;
	
    private static final String AIR_REC_PAY_PAYAMOUNT = "0";     
    
    private static final String PRODUCT_PACKAGE_DESC =  "标准快递";
    
    private static final String PRODUCT_AIR_FREIGHT_DESC =  "精准空运";
   
	/**
	 * 导入Excel信息
	 * 
	 * @author foss-pengzhen
	 * @date 2012-11-13 下午2:31:52
	 * @param file
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public BillRecAndPayImportDto importRecAndPaylist(File file,
			String fileName) {
		//声明一个应收应付的list对象
		List<BillRecAndPayImportDto> billRecAndPayImportDtos = null;
		//声明一个应收应付的对象
		BillRecAndPayImportDto billRecAndPayImportDto = null;
		//声明流对象
		InputStream is = null;
		Workbook xwb = null;
		//参数为空
		if(!file.exists()){
			//导入文件异常
			throw new SettlementException("导入文件异常！");
		}
		//参数为空
		if(StringUtils.isEmpty(fileName)){
			//导入文件异常
			throw new SettlementException("导入文件异常！");
		}
		try {
			//实例文件流
			is = new FileInputStream(file);
			//处理字符
			String filetype = fileName.substring(fileName.lastIndexOf('.') + 1);
			if (filetype.equalsIgnoreCase("xls")) {
				// 支持excel2003
				xwb = new HSSFWorkbook(is); 
			} else if (filetype.equalsIgnoreCase("xlsx")) {
				// 支持excel2007、10版
				xwb = new XSSFWorkbook(is); 
			} else {
				throw new SettlementException("文件类型不支持，请使用excel03、07、10版！");
			}

			//声明实例一个数据集合
			List<List<String>> dataList = new ArrayList<List<String>>();
			//声明一个list集合
			List<String> rowList = null;
			Sheet xSheet = xwb.getSheetAt(AIR_REC_PAYABLE_BILL_ZERO);
			Row firstRow = xSheet.getRow(AIR_REC_PAYABLE_BILL_ZERO);
			// 循环行Row
			// 从第2行开始
			for (int rowNum = 1; rowNum <= xSheet.getLastRowNum(); rowNum++) {
				Row xRow = xSheet.getRow(rowNum);
				if (null == xRow) {
					throw new SettlementException("文件内容标题有误，请修正重试");
				}
				rowList = new ArrayList<String>();
				// 循环列Cell，添加到LIst集合中
				for (int cellNum = 0; cellNum < firstRow.getLastCellNum(); cellNum++) {
					Cell xCell = xRow.getCell(cellNum);
					//如果参数为空
					if (null == xCell) {
						//则添加一个null
						rowList.add(null);
					} else {
						int cellType = xCell.getCellType();
						if (Cell.CELL_TYPE_BLANK == cellType) {
							//如果为空，则添加一个null
							rowList.add(null);
						} else if (Cell.CELL_TYPE_BOOLEAN == cellType) {
							//判断为boolean类型
							rowList.add(String.valueOf(xCell.getBooleanCellValue()));
						} else if (Cell.CELL_TYPE_NUMERIC == cellType) {
							//判断为numeric类型
							rowList.add(String.valueOf(xCell.getNumericCellValue()));
						} else if (Cell.CELL_TYPE_STRING == cellType) {
							//判断为string类型
							rowList.add(xCell.getStringCellValue());
						} else {
							throw new SettlementException("未知的列类型.");
						}
					}
				}
				if((rowList.get(AIR_REC_PAYABLE_BILL_ZERO)!=null 
						//正单号和运单号只存在一个就加入
						|| rowList.get(AIR_REC_PAY_WAYBILLNO_ONE)!=null)
								//代理信息一定存在
								&& rowList.get(AIR_REC_PAY_CUSTOMERCODE_TWO)!=null
										//金额存在其中一个就可以加入
										&& (rowList.get(AIR_REC_PAY_RECAMOUNT_THREE)!=null
												|| rowList.get(AIR_REC_PAY_PAYAMOUNT_FOUR)!=null)){
					dataList.add(rowList);
				}
			}
			// 1,校验excel信息是否为空
			if (CollectionUtils.isEmpty(dataList)) {
				//导入数据为空，不能进行导入操作
				throw new SettlementException("导入数据为空，不能进行导入操作！");
			}
			//声明实例list对象
			List<BillRecAndPayImportDto> billRecAndPayDtos = new ArrayList<BillRecAndPayImportDto>();
			int tatal = 0;
			// 声明实例总条数
			List<String> waybillNos = new ArrayList<String>();
			// 声明实例应收总金额
			List<String> totalRecAmounts = new ArrayList<String>();
			// 声明实例应付金额
			List<String> totalPayAmounts = new ArrayList<String>();
			String amountBig = configurationParamsService.queryConfValueByCode(ConfigurationParamsConstants.STL_AIR_ADD_MAX_AMOUNT);
			for(List<String> listRow : dataList){
				BillRecAndPayImportDto billRecAndPayDto = new BillRecAndPayImportDto();
				// 获取excel中的数据添加到正单号集合
				String sourceBillNo = listRow.get(AIR_REC_PAYABLE_BILL_ZERO);
				billRecAndPayDto.setSourceBillNo(sourceBillNo);
				// 获取excel中的数据
				String waybillNo = listRow.get(AIR_REC_PAY_WAYBILLNO_ONE);
				if(StringUtils.isNotEmpty(waybillNo)){
					//添加到运单号集合
					waybillNos.add(waybillNo);
				}
				// 获取excel中的数据添加到代理集合
				String customerCode = listRow.get(AIR_REC_PAY_CUSTOMERCODE_TWO);
				billRecAndPayDto.setCustomerCode(customerCode);
				// 获取excel中的数据添加到应收单集合
				String totalRecAmountStr = listRow.get(AIR_REC_PAY_RECAMOUNT_THREE);
				totalRecAmounts.add(totalRecAmountStr);
				// 获取excel中的数据添加到应付单集合
				String totalPayAmountStr = listRow.get(AIR_REC_PAY_PAYAMOUNT_FOUR);
				totalPayAmounts.add(totalPayAmountStr);
				
				billRecAndPayDtos.add(billRecAndPayDto);
				
				//校验正单号和运单号不能同时为空
				if (StringUtils.isEmpty(sourceBillNo)
						&& StringUtils.isEmpty(waybillNo)) {
					throw new SettlementException("正单号和运单号不能同时为空，不能进行导入操作");
				}
				
				//校验正单号和运单号不能同时存在
				if (StringUtils.isNotEmpty(sourceBillNo)
						&& StringUtils.isNotEmpty(waybillNo)) {
					throw new SettlementException("正单号" + sourceBillNo + "和运单号"
							+ waybillNo + "不能同时存在，不能进行导入操作");
				}
				
				//校验代理编码是否为空
				if (StringUtils.isEmpty(customerCode)) {
					if(StringUtils.isNotEmpty(sourceBillNo)){
						throw new SettlementException("正单号" + sourceBillNo+ "代理编码不能为空，请填写代理编码内容！");
					}
					else if(StringUtils.isNotEmpty(waybillNo)){
						throw new SettlementException("和运单号" + waybillNo+ "代理编码不能为空，请填写代理编码内容！");
					}
				}
				
				// 4,校验应收金额和应付金额是否为空且是否合法
				// 校验应收金额和应付金额不能同时为空，请重新填写！
				if (StringUtils.isEmpty(totalPayAmountStr)
						&& StringUtils.isEmpty(totalRecAmountStr)) {
					if(StringUtils.isNotEmpty(sourceBillNo)){
						throw new SettlementException("正单号" + sourceBillNo
								+ "应收金额和应付金额不能同时为空，请重新填写金额内容！");
					}
					else if(StringUtils.isNotEmpty(waybillNo)){
						throw new SettlementException("和运单号" + waybillNo
								+ "应收金额和应付金额不能同时为空，请重新填写金额内容！");
					}
				}
				
				//如果应付金额为空，默认设置为零的操作
				if(StringUtils.isEmpty(totalPayAmountStr)){
					totalPayAmountStr = AIR_REC_PAY_PAYAMOUNT;
				}
				//如果应收金额为空，默认设置为零的操作
				if(StringUtils.isEmpty(totalRecAmountStr)){
					totalRecAmountStr = AIR_REC_PAY_PAYAMOUNT;
				}
				
				// 校验应收金额和应付金额不能同时为0，请重新填写！
				if(StringUtils.equals(totalPayAmountStr, AIR_REC_PAY_PAYAMOUNT) 
						&& StringUtils.equals(totalRecAmountStr, AIR_REC_PAY_PAYAMOUNT)){
					//应收金额和应付金额中必须有一个大于零，请重新填写金额内容
					if(StringUtils.isNotEmpty(sourceBillNo)){
						throw new SettlementException("正单号" + sourceBillNo
								+ "应收金额和应付金额中必须有一个大于零，请重新填写金额内容！");
					}
					//应收金额和应付金额中必须有一个大于零，请重新填写金额内容
					else if(StringUtils.isNotEmpty(waybillNo)){
						throw new SettlementException("和运单号" + waybillNo
								+ "应收金额和应付金额中必须有一个大于零，请重新填写金额内容！");
					}
				}
				
				//校验应收、应付金额,应收、应付金额必须大于0且小于5000，请重新填写！
				if ((Double.parseDouble(totalRecAmountStr) >= Double.parseDouble(amountBig)
						|| Double.parseDouble(totalRecAmountStr) <= AIR_REC_PAYABLE_BILL_ZERO)
						&& (Double.parseDouble(totalPayAmountStr) >= Double.parseDouble(amountBig)
						|| Double.parseDouble(totalPayAmountStr) <= AIR_REC_PAYABLE_BILL_ZERO)) {
					//校验应收、应付金额,应收、应付金额必须大于0且小于5000，请重新填写！
					if(StringUtils.isNotEmpty(sourceBillNo)){
						throw new SettlementException("正单号" + sourceBillNo
								+ "应收、应付金额必须大于0且小于" + amountBig + "，请重新填写金额内容！");
					}//校验应收、应付金额,应收、应付金额必须大于0且小于5000，请重新填写！
					else if(StringUtils.isNotEmpty(waybillNo)){
						throw new SettlementException("和运单号" + waybillNo
								+ "应收、应付金额必须大于0且小于" + amountBig + "，请重新填写金额内容！");
					}
				}
				tatal++;
			}
			
			//声明实例list对象
			List<BillRecAndPayImportDto> billRecAndPayImportlist = new ArrayList<BillRecAndPayImportDto>();
			if(CollectionUtils.isNotEmpty(billRecAndPayDtos)){
				//循环有正单号的信息
    			for(BillRecAndPayImportDto recAndPayImportDto : billRecAndPayDtos){
    				if(StringUtils.isNotEmpty(recAndPayImportDto.getSourceBillNo())){
    					//把正单号添加到新的集合，校验带有正单号的数据
    					billRecAndPayImportlist.add(recAndPayImportDto);
    				}
    			}
			}
			if(CollectionUtils.isNotEmpty(billRecAndPayImportlist)){
				// 根据正单号查询航空公司应付单或空运代理应付单信息，校验该正单号是否被合大票清单使用
				// 根据查询出的应付单信息校验正单号和代理名称是否匹配
				//声明实例list对象
    			@SuppressWarnings("unchecked")
    			List<BillRecAndPayImportDto> recAndPayImportDtos = airWaybillService
    					.batchCheckAirWaybillNoisExist(billRecAndPayImportlist);
    			//循环校验数据
    			for(BillRecAndPayImportDto dto : recAndPayImportDtos){
    				if(StringUtils.equals(FossConstants.NO, dto.getIsExist())){
    					//校验带有正单号的数据
    					throw new SettlementException("正单号" + dto.getSourceBillNo() + "或代理信息" 
    							+ dto.getCustomerCode() + "不合法或未被使用，不能进行导入操作！");
    				}
    			}
			}
			//声明一个运单集合
			List<WaybillEntity> waybillEntities = null;
			if (CollectionUtils.isNotEmpty(waybillNos)) {
				//批量查询运单号信息
				waybillEntities = waybillManagerService.queryWaybillBasicByNoList(waybillNos);
				// 校验运单号批量
				if (CollectionUtils.isEmpty(waybillEntities)) {
					throw new SettlementException("没有查询到对应的运单号，不能进行导入操作！");
				}
				// 校验运单号批量
				if(waybillNos.size() != waybillEntities.size()){
    				for(String waybillNo :waybillNos){
    					boolean flag = false;
    					//循环的去比对所存在的运单号
    					for(WaybillEntity waybillEntity : waybillEntities ){
    						if(StringUtils.equals(waybillNo, waybillEntity.getWaybillNo())){
    							flag = true;
    						}
    					}
    					//校验是否合法
    					if(!flag){
    						throw new SettlementException("运单号"+ waybillNo +"不合法，不能进行导入操作！");
    					}
    				}
				}
			}
			//实例化billRecAndPayImportDtos
			billRecAndPayImportDtos = new ArrayList<BillRecAndPayImportDto>();
			//实例化billRecAndPayImportDto
			billRecAndPayImportDto = new BillRecAndPayImportDto();
			//声明一个recAndPayImportDto对象
			BillRecAndPayImportDto recAndPayImportDto = null;
			//声明应收总金额
			BigDecimal totalRecAmount = BigDecimal.ZERO;
			//声明应付总金额
			BigDecimal totalPayAmount = BigDecimal.ZERO;
			//声明应收金额
			BigDecimal recAmount = BigDecimal.ZERO;
			//声明应付金额
			BigDecimal payAmount = BigDecimal.ZERO;
			//循环添加到dto中,并返回到界面
			for(List<String> listRow : dataList){
				recAndPayImportDto = new BillRecAndPayImportDto();
				// 获取excel中的数据添加到正单号集合
				String sourceBillNo = listRow.get(AIR_REC_PAYABLE_BILL_ZERO);
				recAndPayImportDto.setSourceBillNo(sourceBillNo);
				// 获取excel中的数据添加到运单号集合
				String waybillNo = listRow.get(AIR_REC_PAY_WAYBILLNO_ONE);
				//设置运单号
				recAndPayImportDto.setWaybillNo(waybillNo);
				if(StringUtils.isNotEmpty(waybillNo)&&CollectionUtils.isNotEmpty(waybillEntities)){
    				for(WaybillEntity waybillEntity : waybillEntities){
    					if(StringUtils.equals(waybillNo, waybillEntity.getWaybillNo())){
    						//设置部门code
    						recAndPayImportDto.setOrigOrgCode(waybillEntity.getReceiveOrgCode());
	    					//根据编码查询收货部门的name
	    					OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = 
	    							orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillEntity.getReceiveOrgCode());
	    					if(orgAdministrativeInfoEntity != null){
	    						//设置部门name
								recAndPayImportDto.setOrigOrgName(orgAdministrativeInfoEntity.getName());
	    					}
	    					break;
    					}
    				}
				}
				//设置正单号
				if(StringUtils.isNotEmpty(recAndPayImportDto.getSourceBillNo())){
					AirWaybillEntity airWaybillEntity
					= pointsSingleJointTicketService.queryWidthPrintAirWaybill(recAndPayImportDto.getSourceBillNo());
				recAndPayImportDto.setSourceBillNo(airWaybillEntity.getAirWaybillNo());			
				if(AirfreightConstants.PACKAGE_AIR.equals(airWaybillEntity.getTransportType())){
					airWaybillEntity.setProductName(PRODUCT_PACKAGE_DESC);
					airWaybillEntity.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE);
				}
				if(AirfreightConstants.PRECISION_AIR.equals(airWaybillEntity.getTransportType())){
					airWaybillEntity.setProductName(PRODUCT_AIR_FREIGHT_DESC);
					airWaybillEntity.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT);
				}
				recAndPayImportDto.setProductCode(airWaybillEntity.getProductCode());
				recAndPayImportDto.setProductName(airWaybillEntity.getProductName());
				}
				// 获取excel中的数据添加到代理集合
				String customerCode = listRow.get(AIR_REC_PAY_CUSTOMERCODE_TWO);
				recAndPayImportDto.setCustomerCode(customerCode);
				
				/**
				 * 根据航空公司编码查询公司是否存在
				 */
				AirlinesEntity airlinesEntity =  airlinesService.queryAirlineByCode(customerCode);
				
				AirlinesAgentEntity  airlinesAgentEntity =  null;
				BusinessPartnerEntity businessPartnerEntity = null;
				if(null == airlinesEntity){
					businessPartnerEntity = airAgencyCompanyService.queryEntityByCode(customerCode);
					if(null == businessPartnerEntity){
        				/**
        			     * <p>根据条件有选择的检索出符合条件的“航空公司代理人”唯一记录（条件做自动判断，只选择实体中非空值）</p> 
        			     */
        				AirlinesAgentEntity airlinesAgent = new AirlinesAgentEntity();
        				airlinesAgent.setAgentCode(customerCode);
        			    airlinesAgentEntity = airlinesAgentService.queryAirlinesAgentBySelective(airlinesAgent);
					}
				}
				//该代理不存在！不能进行导入操作！
				if(null == airlinesEntity && null == airlinesAgentEntity && null == businessPartnerEntity ){
					throw new SettlementException("该" + customerCode + "对接的代理不存在，不能进行导入操作！");
				}
				
				if(null != airlinesEntity){
					recAndPayImportDto.setCustomerName(airlinesEntity.getName());
				}
				
				if(null != airlinesAgentEntity){
					recAndPayImportDto.setCustomerName(airlinesAgentEntity.getAgentName());
				}
				
                if(null != businessPartnerEntity){
                	recAndPayImportDto.setCustomerName(businessPartnerEntity.getAgentCompanyName());
                }
				
				// 获取excel中的数据添加到应收单集合
				String totalRecAmountStr = listRow.get(AIR_REC_PAY_RECAMOUNT_THREE);
				//判断应收金额是否为空
				if(StringUtils.isNotEmpty(totalRecAmountStr)){
					//实例应收金额
    				recAmount = new BigDecimal(totalRecAmountStr);
    				//保留2位小数
    				recAmount = recAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
    				//设置应收金额
    				recAndPayImportDto.setRecAmount(recAmount);
    				//计算应收金额
    				totalRecAmount = totalRecAmount.add(recAmount);
				}
				// 获取excel中的数据添加到应付单集合
				String totalPayAmountStr = listRow.get(AIR_REC_PAY_PAYAMOUNT_FOUR);
				//判断应付金额是否为空
				if(StringUtils.isNotEmpty(totalPayAmountStr)){
					//实例应付金额
    				payAmount = new BigDecimal(totalPayAmountStr);
    				//保留两位小数
    				payAmount = payAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
    				//设置应付金额
    				recAndPayImportDto.setPayAmount(payAmount);
    				//计算应付金额
    				totalPayAmount = totalPayAmount.add(payAmount);
				}
				//设置对象到对象集合中
				billRecAndPayImportDtos.add(recAndPayImportDto);
			}
			//设置总条数
			billRecAndPayImportDto.setTotal(tatal);
			//设置应收总金额
			billRecAndPayImportDto.setTotalRecAmount(totalRecAmount);
			//设置应付总金额
			billRecAndPayImportDto.setTotalPayAmount(totalPayAmount);
			//获取所有的参数设置到对象中的list的集合中
			billRecAndPayImportDto.setBillRecAndPayImportDtoList(billRecAndPayImportDtos);
		} catch (FileNotFoundException e) {
			//没找到对应文件异常
			throw new SettlementException("没找到对应文件！");
		} catch (IOException e) {
			//导入文件有误异常
			throw new SettlementException("导入文件有误！");
		}catch(NumberFormatException e) {
			//导入金额格式有误异常
			throw new SettlementException("导入金额格式有误！");
		}finally {
			try {
				if (is != null) {
					//关闭流
					is.close();
				}
			} catch (IOException e) {
				//关闭流
				throw new SettlementException("关闭流错误！");
			}
		}
		return billRecAndPayImportDto;
	}

	/**
	 * 保存应收、应付信息
	 * 
	 * @author foss-pengzhen
	 * @date 2012-11-13 下午3:06:33
	 * @return
	 * @see
	 */
	@Transactional
	public void saveAirImportPayAndRec(
			BillRecAndPayImportDto billRecAndPayImportDto,
			CurrentInfo currentInfo) {
		//判断参数是否为空
		if (billRecAndPayImportDto == null) {
			throw new SettlementException("内部错误，应收、应付代理成本对象为空!");
		}
		//判断参数是否为空
		if (CollectionUtils.isEmpty(billRecAndPayImportDto.getBillRecAndPayImportDtoList())) {
			throw new SettlementException("内部错误，应收、应付代理成本对象为空!");
		}
		//声明实例billReceivableEntity对象
		BillReceivableEntity billReceivableEntity = new BillReceivableEntity();
		for (BillRecAndPayImportDto recAndPayImportDto : billRecAndPayImportDto
					.getBillRecAndPayImportDtoList()){
			if (recAndPayImportDto.getRecAmount() != null
					&& recAndPayImportDto.getRecAmount().compareTo(
							BigDecimal.ZERO) > 0) {
				// 设置id
				billReceivableEntity.setId(UUIDUtils.getUUID());
				// 设置空运其他应收管理的应收单号,应收单单号系统自动生成，生成规则“YS90+7位流水号”
				billReceivableEntity
						.setReceivableNo(settlementCommonService
								.getSettlementNoRule(SettlementNoRuleEnum.YS90));

				// 设置空运其他应收管理的系统生成方式
				billReceivableEntity
						.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);
				//如果单号为空，则写入默认单号
				if(StringUtils.isEmpty(recAndPayImportDto.getWaybillNo())){
					billReceivableEntity
					.setWaybillNo(SettlementConstants.DEFAULT_BILL_NO);
				}else{
    				billReceivableEntity.setWaybillNo(recAndPayImportDto.getWaybillNo());
				}

				if(StringUtils.isNotEmpty(recAndPayImportDto.getSourceBillNo())){
					// 如果空运其他应收管理的航空正单号不为空,业务单号为界面输入正单号
					billReceivableEntity.setSourceBillNo(recAndPayImportDto.getSourceBillNo());
				}else{
					// 如果空运其他应收管理的航空正单号为空,业务单号为界面输入运单号
					billReceivableEntity.setSourceBillNo(recAndPayImportDto.getWaybillNo());
				}

				//出发部门（收货部门）code
				if(StringUtils.isNotEmpty(recAndPayImportDto.getOrigOrgCode())){
					billReceivableEntity.setOrigOrgCode(recAndPayImportDto.getOrigOrgCode());
				}else{
					billReceivableEntity.setOrigOrgCode(currentInfo.getCurrentDeptCode());
				}
				//出发部门（收货部门）name
				if(StringUtils.isNotEmpty(recAndPayImportDto.getOrigOrgName())){
					billReceivableEntity.setOrigOrgName(recAndPayImportDto.getOrigOrgName());
				}else{
					billReceivableEntity.setOrigOrgName(currentInfo.getCurrentDeptName());
				}
				//设置备注
				if(StringUtils.isNotEmpty(recAndPayImportDto.getNotes())){
					billReceivableEntity.setNotes(recAndPayImportDto.getNotes());
				}
				
				billReceivableEntity.setDestOrgCode(currentInfo.getCurrentDeptCode());
				billReceivableEntity.setDestOrgName(currentInfo.getCurrentDeptName());
				
				// 设置空运其他应收管理的来源单据类型 手工输入
				billReceivableEntity
						.setSourceBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__MANUAL);

				// 设置空运其他应收管理的单据子类型
				billReceivableEntity
						.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_RECEIVABLE);

				// 设置空运其他应收管理的代理、航空公司名称、代理信息为界面输入信息
				billReceivableEntity.setCustomerCode(recAndPayImportDto
						.getCustomerCode());
				// 设置空运其他应收管理的代理、航空公司code、代理信息为界面输入信息
				billReceivableEntity.setCustomerName(recAndPayImportDto.getCustomerName());
				// 设置空运其他应收管理的收货部门code
				billReceivableEntity.setGeneratingOrgCode(currentInfo.getCurrentDeptCode());
				// 设置空运其他应收管理的收货部门name
				billReceivableEntity.setGeneratingOrgName(currentInfo.getCurrentDeptName());
				
				//催款部门编码
				billReceivableEntity.setDunningOrgCode(currentInfo.getCurrentDeptCode());
				//催款部门名称
				billReceivableEntity.setDunningOrgName(currentInfo.getCurrentDeptName());
				
				//设置应收部门编码
				billReceivableEntity.setReceivableOrgCode(currentInfo.getCurrentDeptCode());
				//设置应收部门名称
		    	billReceivableEntity.setReceivableOrgName(currentInfo.getCurrentDeptName());
		    	
		    	// 获取当前选择的应收部门组织的的实体信息
				OrgAdministrativeInfoEntity generatOrgEntity = orgAdministrativeInfoService
								.queryOrgAdministrativeInfoByCode(currentInfo.getCurrentDeptCode());
				if(generatOrgEntity != null){
					//设置收货部门code
					billReceivableEntity.setGeneratingComCode(generatOrgEntity.getSubsidiaryCode());// 当前登录用户操作公司
					//设置收货部门名称
					billReceivableEntity.setGeneratingComName(generatOrgEntity.getSubsidiaryName());
				}
				
				// 金额和未核销金额为其它应收金额
				// 设置空运其他应收管理的金额
				billReceivableEntity.setUnverifyAmount(recAndPayImportDto
						.getRecAmount());
				billReceivableEntity.setAmount(recAndPayImportDto
						.getRecAmount());

				// 设置空运其他应收管理的未核销金额,已核销金额默认为0
				billReceivableEntity.setVerifyAmount(BigDecimal.ZERO);

				// 设置空运其他应收管理的币种
				billReceivableEntity
						.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
				
				//BUG-17091
				// 支付方式 月结
				billReceivableEntity
						.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT);
		

				// 业务日期、记账日期为系统当前时间；创建时间默认为系统当前时间
				Date now = new Date();
				// 设置空运其他应收管理的业务时间
				billReceivableEntity.setBusinessDate(now);

				// 设置空运其他应收管理的记账时间
				billReceivableEntity.setAccountDate(now);

				// 设置空运应收部门code
				billReceivableEntity.setReceivableOrgCode(currentInfo.getCurrentDeptCode());

				// 新增的空运其他应收单单据默认为有效的单据
				billReceivableEntity.setActive(FossConstants.YES);
				// 新增的空运其他应收单单据默认为非红单单据
				billReceivableEntity.setIsRedBack(FossConstants.NO);
				// 新增的空运其他应收单单据默认为有效的单据
				billReceivableEntity.setIsInit(FossConstants.NO);
				// 设置审核状态为已审核 --update time 2013-07-16
				billReceivableEntity
						.setApproveStatus(SettlementDictionaryConstants.NOTE_APPLICATION__APPROVE_STATUS__AUDIT_AGREE);
				// 版本号
				billReceivableEntity
						.setVersionNo(FossConstants.INIT_VERSION_NO);
				// 默认单号
				billReceivableEntity.setStatementBillNo(SettlementConstants.DEFAULT_BILL_NO);


				if(StringUtil.isEmpty(recAndPayImportDto.getProductCode())
						||PricingConstants.ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(recAndPayImportDto.getProductCode())){
					billReceivableEntity.setProductName(PRODUCT_AIR_FREIGHT_DESC);
					billReceivableEntity.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT);
				}
				else{
					billReceivableEntity.setProductName(PRODUCT_PACKAGE_DESC);
					billReceivableEntity.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE);
					
				}
				//billReceivableEntity.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT);
				
				/*
				 * 税务改造需求
				 * 
				 * 空运其他应收单发票标记为 02-非运输专票
				 * 
				 * 杨书硕 2013-11-5 上午9:58:56
				 */
				
				billReceivableEntity.setInvoiceMark(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_TWO);
				
				billReceivableService.addBillReceivable(billReceivableEntity, currentInfo);	
				
			}
		}
		//声明实例billPayableEntity对象
		BillPayableEntity billPayableEntity = new BillPayableEntity();
		for (BillRecAndPayImportDto recAndPayImportDto : billRecAndPayImportDto
				.getBillRecAndPayImportDtoList()){
			if (recAndPayImportDto.getPayAmount() != null
					&& recAndPayImportDto.getPayAmount().compareTo(BigDecimal.ZERO) > 0) {
				// 设置id
				billPayableEntity.setId(UUIDUtils.getUUID());
				//设置运单号，如为空，设置默认单号
				if(StringUtils.isEmpty(recAndPayImportDto.getWaybillNo())){
					billPayableEntity.setWaybillNo(SettlementConstants.DEFAULT_BILL_NO);
				}else{
    				billPayableEntity.setWaybillNo(recAndPayImportDto.getWaybillNo());
				}

				
				//出发部门（收货部门）code
				if(StringUtils.isNotEmpty(recAndPayImportDto.getOrigOrgCode())){
					billPayableEntity.setOrigOrgCode(recAndPayImportDto.getOrigOrgCode());
				}else{
					billPayableEntity.setOrigOrgCode(currentInfo.getCurrentDeptCode());
				}
				//出发部门（收货部门）name
				if(StringUtils.isNotEmpty(recAndPayImportDto.getOrigOrgName())){
					billPayableEntity.setOrigOrgName(recAndPayImportDto.getOrigOrgName());
				}else{
					billPayableEntity.setOrigOrgName(currentInfo.getCurrentDeptName());
				}
				
				billPayableEntity.setDestOrgCode(currentInfo.getCurrentDeptCode());
				billPayableEntity.setDestOrgName(currentInfo.getCurrentDeptName());
				
				// 设置空运其他应付管理的系统生成方式
				billPayableEntity
						.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);

				if(StringUtils.isNotEmpty(recAndPayImportDto.getSourceBillNo())){
					// 如果空运其他应收管理的航空正单号不为空,业务单号为界面输入正单号
					billPayableEntity.setSourceBillNo(recAndPayImportDto.getSourceBillNo());
				}else{
					// 如果空运其他应收管理的航空正单号为空,业务单号为界面输入运单号
					billPayableEntity.setSourceBillNo(recAndPayImportDto.getWaybillNo());
				}

				// 设置空运其他应付管理的来源单据类型 手工输入
				billPayableEntity.setSourceBillType(SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__AIR_WAYBILL);

				// 设置空运其他应付管理的单据子类型
				billPayableEntity.setBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR_OTHER);

				// 设置审核状态为已审核 --update time 2013-07-16
				billPayableEntity.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE);

				// 设置空运其他应付管理的代理、航空公司code、代理信息为界面输入信息
				billPayableEntity.setCustomerCode(recAndPayImportDto.getCustomerCode());
				// 设置空运其他应付管理的代理、航空公司名称、代理信息为界面输入信息
				billPayableEntity.setCustomerName(recAndPayImportDto.getCustomerName());
				// 设置空运其他应付管理的应付子公司code
				billPayableEntity.setPayableOrgCode(currentInfo.getCurrentDeptCode());
				// 设置空运其他应付管理的应付子公司name
				billPayableEntity.setPayableOrgName(currentInfo.getCurrentDeptName());

				// 获取当前选择的应付部门组织的的实体信息
				OrgAdministrativeInfoEntity payOrgEntity = orgAdministrativeInfoService
								.queryOrgAdministrativeInfoByCode(currentInfo.getCurrentDeptCode());
				if(payOrgEntity != null){
					// 设置空运其他应付管理的应付部门code
					billPayableEntity.setPayableComCode(payOrgEntity.getSubsidiaryCode());
					// 设置空运其他应付管理的应付部门name
					billPayableEntity.setPayableComName(payOrgEntity.getSubsidiaryName());
				}
				
				// 生效状态不能为空
				billPayableEntity.setEffectiveStatus(SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__YES);

				// 支付状态不能为空
				billPayableEntity.setPayStatus(SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__NO);

				// 冻结状态不能为空
				billPayableEntity.setFrozenStatus(SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__NOT_FROZEN);

				// 设置空运其他应付管理的金额
				billPayableEntity.setAmount(recAndPayImportDto.getPayAmount());

				// 设置空运其他应付管理的未核销金额
				billPayableEntity.setUnverifyAmount(recAndPayImportDto.getPayAmount());

				// 设置空运其他应付管理的核销金额
				billPayableEntity.setVerifyAmount(BigDecimal.ZERO);

				// 设置空运其他应付管理的币种
				billPayableEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);

				// 业务日期、记账日期为系统当前时间；创建时间默认为系统当前时间
				Date now = new Date();
				// 设置空运其他应付管理的业务时间
				billPayableEntity.setBusinessDate(now);

				// 设置空运其他应付管理的记账时间
				billPayableEntity.setAccountDate(now);

				// 设置空运其他应付管理的确认付入日期,暂未空

				// 新增的空运其他应付单单据默认为有效的单据
				billPayableEntity.setActive(FossConstants.ACTIVE);
				// 新增的空运其他应付单单据默认为非红单单据
				billPayableEntity.setIsRedBack(FossConstants.NO);
				// 新增的空运其他应付单单据默认为非初始化单据
				billPayableEntity.setIsInit(FossConstants.NO);
				// 版本号
				billPayableEntity.setVersionNo(FossConstants.INIT_VERSION_NO);
				// 默认单号
				billPayableEntity.setStatementBillNo(SettlementConstants.DEFAULT_BILL_NO);

				if(StringUtil.isEmpty(recAndPayImportDto.getProductCode())
						||PricingConstants.ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(recAndPayImportDto.getProductCode())){
					billPayableEntity.setProductName(PRODUCT_AIR_FREIGHT_DESC);
					billPayableEntity.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT);
				}
				else{
					billPayableEntity.setProductName(PRODUCT_PACKAGE_DESC);
					billPayableEntity.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE);
				}
				//设置备注
				billPayableEntity.setNotes(recAndPayImportDto.getNotes());
			
				// 设置空运其他应付管理的应付单号,应付单单号系统自动生成，生成规则“YF90+7位流水号”
				billPayableEntity.setPayableNo(settlementCommonService
						.getSettlementNoRule(SettlementNoRuleEnum.YF90));
				/*
				 * 税务改造需求
				 * 
				 * 空运其他应付单发票标记为 02-非运输专票
				 * 
				 * 杨书硕 2013-11-5 上午9:58:56
				 */
				
				billPayableEntity.setInvoiceMark(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_TWO);

				billPayableService.addBillPayable(billPayableEntity,currentInfo);
			}
		}
	}

	
	/**
	 * @return  the billPayableService
	 */
	public IBillPayableService getBillPayableService() {
		return billPayableService;
	}

	
	/**
	 * @param billPayableService the billPayableService to set
	 */
	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}

	
	/**
	 * @return  the billReceivableService
	 */
	public IBillReceivableService getBillReceivableService() {
		return billReceivableService;
	}

	
	/**
	 * @param billReceivableService the billReceivableService to set
	 */
	public void setBillReceivableService(
			IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}

	
	/**
	 * @return  the airWaybillService
	 */
	public IAirWaybillService getAirWaybillService() {
		return airWaybillService;
	}

	
	/**
	 * @param airWaybillService the airWaybillService to set
	 */
	public void setAirWaybillService(IAirWaybillService airWaybillService) {
		this.airWaybillService = airWaybillService;
	}

	
	/**
	 * @return  the waybillManagerService
	 */
	public IWaybillManagerService getWaybillManagerService() {
		return waybillManagerService;
	}

	
	/**
	 * @param waybillManagerService the waybillManagerService to set
	 */
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	
	/**
	 * @return  the settlementCommonService
	 */
	public ISettlementCommonService getSettlementCommonService() {
		return settlementCommonService;
	}

	
	/**
	 * @param settlementCommonService the settlementCommonService to set
	 */
	public void setSettlementCommonService(
			ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

	
	/**
	 * @return  the airAgencyCompanyService
	 */
	public IAirAgencyCompanyService getAirAgencyCompanyService() {
		return airAgencyCompanyService;
	}

	
	/**
	 * @param airAgencyCompanyService the airAgencyCompanyService to set
	 */
	public void setAirAgencyCompanyService(
			IAirAgencyCompanyService airAgencyCompanyService) {
		this.airAgencyCompanyService = airAgencyCompanyService;
	}

	
	/**
	 * @return  the orgAdministrativeInfoService
	 */
	public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
		return orgAdministrativeInfoService;
	}

	
	/**
	 * @param orgAdministrativeInfoService the orgAdministrativeInfoService to set
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	
	/**
	 * @return  the configurationParamsService
	 */
	public IConfigurationParamsService getConfigurationParamsService() {
		return configurationParamsService;
	}

	
	/**
	 * @param configurationParamsService the configurationParamsService to set
	 */
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	
	/**
	 * @param airlinesService the airlinesService to set
	 */
	public void setAirlinesService(IAirlinesService airlinesService) {
		this.airlinesService = airlinesService;
	}

	
	/**
	 * @param airlinesAgentService the airlinesAgentService to set
	 */
	public void setAirlinesAgentService(IAirlinesAgentService airlinesAgentService) {
		this.airlinesAgentService = airlinesAgentService;
	}

	public void setPointsSingleJointTicketService(
			IAirWaybillDetailService pointsSingleJointTicketService) {
		this.pointsSingleJointTicketService = pointsSingleJointTicketService;
	}
}
