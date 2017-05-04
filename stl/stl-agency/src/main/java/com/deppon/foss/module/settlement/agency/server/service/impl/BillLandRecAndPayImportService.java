package com.deppon.foss.module.settlement.agency.server.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.service.IAirAgencyCompanyService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILdpAgencyCompanyService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerExpressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.agency.api.server.service.IBillLandRecAndPayImportService;
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
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirWaybillService;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 导入快递代理应收、应付信息
 * 
 * @author foss-guxinhua
 * @date 2012-11-13 下午2:30:55
 * @since
 * @version
 */
public class BillLandRecAndPayImportService implements IBillLandRecAndPayImportService {

	/**
	 * 应付单service
	 */
	private IBillPayableService billPayableService;

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
	 * 快递代理公司接口
	 */
	private ILdpAgencyCompanyService ldpAgencyCompanyService;
	
	/**
	 * 定义所对应的运单号的常量
	 */
	private static final int LAND_REC_PAY_WAYBILLNO_ONE = 0;
	
	/**
	 * 定义所对应的客户的常量
	 */
	private static final int LAND_REC_PAY_CUSTOMERCODE_TWO = 1;
	
	/**
	 * 定义所对应的应收的常量
	 */
	private static final int LAND_REC_PAY_RECAMOUNT_THREE = 2;
	
	/**
	 * 定义所对应的应付的常量
	 */
	private static final int LAND_REC_PAY_PAYAMOUNT_FOUR = 3;
	
	/**
	 * 定义所对应的备注的常量 
	 * @author 218392 zhangyongxue 2015-07-29 15:27:39
	 */
	private static final int LAND_REC_PAY_NOTES_FIVE = 4;
	
	/**
	 * 定义所对应的其他应付类型的常量
	 * @author 218392 zhangyongxue 2015-07-30 10:48:26
	 */
	private static final int LAND_REC_PAY__OTHERPAYTYPE_SIX = 5;
	
    private static final String LAND_REC_PAY_PAYAMOUNT = "0";
	/**
	 * 导入Excel信息
	 * @author foss-guxinhua
	 * @date 2012-11-13 下午2:31:52
	 * @param file
	 * @param fileName
	 * @throws Exception
	 * @see com.deppon.foss.module.settlement..api.server.service.
	 *      IBillRecAndPayImportService#importRecAndPaylist(java.io.File,
	 *      java.lang.String)
	 */
	@Transactional
	public BillRecAndPayImportDto importRecAndPaylist(File file, String fileName) {
		// 声明一个应收应付的对象
		BillRecAndPayImportDto billRecAndPayImportDto = null;
		// 声明流对象
		InputStream is = null;
		Workbook xwb = null;
		// 参数为空
		if (!file.exists()) {
			// 导入文件异常
			throw new SettlementException("导入文件异常！");
		}
		// 参数为空
		if (StringUtils.isEmpty(fileName)) {
			// 导入文件异常
			throw new SettlementException("导入文件异常！");
		}
		try {
			// 实例文件流
			is = new FileInputStream(file);
			// 处理字符
			String filetype = fileName.substring(fileName.lastIndexOf(".") + 1);
			if (filetype.equalsIgnoreCase("xls")) {
				// 支持excel2003
				xwb = new HSSFWorkbook(is);
			} else if (filetype.equalsIgnoreCase("xlsx")) {
				// 支持excel2007、10版
				xwb = new XSSFWorkbook(is);
			} else {
				throw new SettlementException("文件类型不支持，请使用excel03、07、10版！");
			}

			// 声明实例一个数据集合
			List<List<String>> dataList = new ArrayList<List<String>>();
			// 声明一个list集合
			List<String> rowList = null;
			Sheet xSheet = xwb.getSheetAt(0);
			Row firstRow = xSheet.getRow(0);
			// 循环行Row
			// 从第2行开始
			for (int rowNum = 1; rowNum <= xSheet.getLastRowNum(); rowNum++) {
				validaSheet(dataList, xSheet, firstRow, rowNum); 
			}
			// 1,校验excel信息是否为空
			if (CollectionUtils.isEmpty(dataList)) {
				// 导入数据为空，不能进行导入操作
				throw new SettlementException("导入数据为空，不能进行导入操作！");
			}
			
			if (dataList.size() > SettlementConstants.MAX_LIST_SIZE){
				// 导入数据为空，不能进行导入操作
				throw new SettlementException("导入数据超过1000条，不能进行导入操作！");
			}
			
			// 声明实例list对象
			List<BillRecAndPayImportDto> billRecAndPayDtos = new ArrayList<BillRecAndPayImportDto>();
			//声明合计
			int tatal = 0;
			// 声明实例总条数
			List<String> waybillNos = new ArrayList<String>();
			// 声明实例应收总金额
			List<String> totalRecAmounts = new ArrayList<String>();
			// 声明实例应付金额
			List<String> totalPayAmounts = new ArrayList<String>();
			//到配置参数表BSE.T_BAS_SYS_CONFIG查询配置项最大值 为 99999999，@author 218392 张永雪添加注释信息 2015-07-29
			String amountBig = configurationParamsService.queryConfValueByCode(ConfigurationParamsConstants.STL_LDP_ADD_MAX_AMOUNT);//99999999
			for (List<String> listRow : dataList) {
				BillRecAndPayImportDto billRecAndPayDto = new BillRecAndPayImportDto();

				// 获取excel中的数据
				String waybillNo = listRow.get(LAND_REC_PAY_WAYBILLNO_ONE);
				if (StringUtils.isNotEmpty(waybillNo)) {
					// 添加到运单号集合
					waybillNos.add(waybillNo);
				}
				// 获取excel中的数据添加到代理集合
				String customerCode = listRow.get(LAND_REC_PAY_CUSTOMERCODE_TWO);
				billRecAndPayDto.setCustomerCode(customerCode);
				// 获取excel中的数据添加到应收单集合
				String totalRecAmountStr = listRow.get(LAND_REC_PAY_RECAMOUNT_THREE);
				totalRecAmounts.add(totalRecAmountStr);
				// 获取excel中的数据添加到应付单集合
				String totalPayAmountStr = listRow.get(LAND_REC_PAY_PAYAMOUNT_FOUR);
				totalPayAmounts.add(totalPayAmountStr);
				
				/**
				 * @author 218392 zhangyongxue 2015-07-29 15:50:50
				 * 判断备注是否为空
				 */
				//获取excel中的数据添加到应付单集合
				String notes = listRow.get(LAND_REC_PAY_NOTES_FIVE);
				if(StringUtils.isNotEmpty(notes)){
					billRecAndPayDto.setNotes(notes);
				}else{
					throw new SettlementException("备注为空，不能进行导入操作!");
				}
				
				/**
				 * @author 218392 zhangyongxue 2015-07-30 10:52:30
				 * 判断其他应付类型是否为空
				 */
				//获取excel中的数据添加到应付单集合
				String otherPayType = listRow.get(LAND_REC_PAY__OTHERPAYTYPE_SIX);
				if(StringUtils.isNotEmpty(otherPayType)){
					billRecAndPayDto.setOtherPayType(otherPayType);
				}else{
					throw new SettlementException("其他应付类型为空，不能进行导入操作!");
				}

				billRecAndPayDtos.add(billRecAndPayDto);
				
				// 校验运单号不能为空
				if (StringUtils.isEmpty(waybillNo)) {
					throw new SettlementException("运单号不能为空，不能进行导入操作");
				}

				// 校验代理编码是否为空
				if (StringUtils.isEmpty(customerCode)) {
					if (StringUtils.isNotEmpty(waybillNo)) {
						throw new SettlementException("运单号" + waybillNo + "代理编码不能为空，请填写代理编码内容！");
					}
				}

				// 4,校验应收金额和应付金额是否为空且是否合法
				// 校验应收金额和应付金额不能同时为空，请重新填写！
				if (StringUtils.isEmpty(totalPayAmountStr)
						&& StringUtils.isEmpty(totalRecAmountStr)) {
					if (StringUtils.isNotEmpty(waybillNo)) {
						throw new SettlementException("运单号" + waybillNo + "应收金额和应付金额不能同时为空，请重新填写金额内容！");
					}
				}

				// 如果应付金额为空，默认设置为零
				if (StringUtils.isEmpty(totalPayAmountStr)) {
					totalPayAmountStr = LAND_REC_PAY_PAYAMOUNT;
				}
				// 如果应收金额为空，默认设置为零
				if (StringUtils.isEmpty(totalRecAmountStr)) {
					totalRecAmountStr = LAND_REC_PAY_PAYAMOUNT;
				}

				// 校验应收金额和应付金额不能同时为0，请重新填写！
				if (StringUtils.equals(totalPayAmountStr, LAND_REC_PAY_PAYAMOUNT)
					&& StringUtils.equals(totalRecAmountStr,LAND_REC_PAY_PAYAMOUNT)) {
					// 应收金额和应付金额中必须有一个大于零，请重新填写金额内容
					if (StringUtils.isNotEmpty(waybillNo)) {
						throw new SettlementException("运单号" + waybillNo + "应收金额和应付金额中必须有一个大于零，请重新填写金额内容！");
					}
				}

				// 校验应收、应付金额,应收、应付金额必须大于0且小于 快递代理其他应收应付最大金额 （STL_LDP_ADD_MAX_AMOUNT） 配置项，请重新填写！
				if (Double.parseDouble(totalRecAmountStr) >= Double.parseDouble(amountBig) 
						|| Double.parseDouble(totalRecAmountStr) < 0
						|| Double.parseDouble(totalPayAmountStr) >= Double.parseDouble(amountBig) 
					    || Double.parseDouble(totalPayAmountStr) < 0) {
					// 校验应收、应付金额,应收、应付金额必须大于0且小于5000，请重新填写！
					if (StringUtils.isNotEmpty(waybillNo)) {
						throw new SettlementException("运单号" + waybillNo + "应收、应付金额必须大于0且小于" + amountBig + "，请重新填写金额内容！");
					}
				}
				tatal++;
			}

			// 判断导入进来的运单号，是否合法性，如有不合法，抛异常提示给用户。
			// 声明一个运单集合
			List<WaybillEntity> waybillEntities = null;
			if (CollectionUtils.isNotEmpty(waybillNos)) {
				// 批量查询运单号信息
				waybillEntities = waybillManagerService.queryWaybillBasicByNoList(waybillNos);
				// 校验运单号批量
				if (CollectionUtils.isEmpty(waybillEntities)) {
					throw new SettlementException("没有查询到对应的运单号，不能进行导入操作！");
				}
				// 校验运单号批量
				if (waybillNos.size() != waybillEntities.size()) {
					for (String waybillNo : waybillNos) {
						validaList(waybillEntities, waybillNo);
					}
				}
			}
			
			// 实例化billRecAndPayImportDtos
			billRecAndPayImportDto = validaImport(dataList, tatal,
					waybillEntities);
		} catch (FileNotFoundException e) {
			// 没找到对应文件异常
			throw new SettlementException("没找到对应文件！");
		} catch (IOException e) {
			// 导入文件有误异常
			throw new SettlementException("导入文件有误！"+e.getMessage());
		} catch (NumberFormatException e) {
			// 导入金额格式有误异常
			throw new SettlementException("导入金额格式有误！");
		} finally {
			try {
				if (is != null) {
					// 关闭流
					is.close();
				}
			} catch (IOException e) {
				// 关闭流
				throw new SettlementException("关闭流错误！");
			}
		}
		return billRecAndPayImportDto;
	}

	private void validaSheet(List<List<String>> dataList, Sheet xSheet,
			Row firstRow, int rowNum) {
		List<String> rowList;
		Row xRow = xSheet.getRow(rowNum);
		if (null == xRow) {
			throw new SettlementException("文件内容标题有误，请修正重试");
		}
		rowList = new ArrayList<String>();
		// 循环列Cell，添加到LIst集合中
		for (int cellNum = 0; cellNum < firstRow.getLastCellNum(); cellNum++) {
			Cell xCell = xRow.getCell(cellNum);
			// 如果参数为空
			if (null == xCell) {
				// 则添加一个null
				rowList.add(null);
			} else {
				int cellType = xCell.getCellType();
				if (Cell.CELL_TYPE_BLANK == cellType) {
					// 如果为空，则添加一个null
					rowList.add(null);
				} else if (Cell.CELL_TYPE_BOOLEAN == cellType) {
					// 判断为boolean类型
					rowList.add(String.valueOf(xCell
							.getBooleanCellValue()));
				} else if (Cell.CELL_TYPE_NUMERIC == cellType) {
					// 判断为numeric类型
					rowList.add(String.valueOf(xCell
							.getNumericCellValue()));
				} else if (Cell.CELL_TYPE_STRING == cellType) {
					// 判断为string类型
					rowList.add(xCell.getStringCellValue());
				} else {
					throw new SettlementException("未知的列类型.");
				}
			}
		}
		if ( // 运单号不能为空
			rowList.get(LAND_REC_PAY_WAYBILLNO_ONE) != null
			// 代理信息一定存在
			&& rowList.get(LAND_REC_PAY_CUSTOMERCODE_TWO) != null
				// 金额存在其中一个就可以加入
			&& (rowList.get(LAND_REC_PAY_RECAMOUNT_THREE) != null 
				|| rowList.get(LAND_REC_PAY_PAYAMOUNT_FOUR) != null)) {

			dataList.add(rowList);
		}
	}

	private void validaList(List<WaybillEntity> waybillEntities,
			String waybillNo) {
		boolean flag = false;
		// 循环的去比对所存在的运单号
		for (WaybillEntity waybillEntity : waybillEntities) {
			if (StringUtils.equals(waybillNo,waybillEntity.getWaybillNo())) {
				flag = true;
				break;
			}
		}
		// 校验是否合法
		if (!flag) {
			throw new SettlementException("运单号" + waybillNo + "不合法，不能进行导入操作！");
		}
	}

	private BillRecAndPayImportDto validaImport(List<List<String>> dataList,
			int tatal, List<WaybillEntity> waybillEntities) {
		List<BillRecAndPayImportDto> billRecAndPayImportDtos;
		BillRecAndPayImportDto billRecAndPayImportDto;
		billRecAndPayImportDtos = new ArrayList<BillRecAndPayImportDto>();
		// 实例化billRecAndPayImportDto
		billRecAndPayImportDto = new BillRecAndPayImportDto();
		// 声明一个recAndPayImportDto对象
		BillRecAndPayImportDto recAndPayImportDto = null;
		// 声明应收总金额
		BigDecimal totalRecAmount = BigDecimal.ZERO;
		// 声明应付总金额
		BigDecimal totalPayAmount = BigDecimal.ZERO;
		// 声明应收金额
		BigDecimal recAmount = BigDecimal.ZERO;
		// 声明应付金额
		BigDecimal payAmount = BigDecimal.ZERO;
		// 循环添加到dto中,并返回到界面
		for (List<String> listRow : dataList) {
			recAndPayImportDto = validaWay(waybillEntities, listRow);
 
			// 获取excel中的数据添加到应收单集合
			String totalRecAmountStr = listRow.get(LAND_REC_PAY_RECAMOUNT_THREE);
			// 判断应收金额是否为空
			if (StringUtils.isNotEmpty(totalRecAmountStr)) {
				// 实例应收金额
				recAmount = new BigDecimal(totalRecAmountStr);
				// 保留2位小数
				recAmount = recAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
				// 设置应收金额
				recAndPayImportDto.setRecAmount(recAmount);
				// 计算应收金额
				totalRecAmount = totalRecAmount.add(recAmount);
			}
			// 获取excel中的数据添加到应付单集合
			String totalPayAmountStr = listRow.get(LAND_REC_PAY_PAYAMOUNT_FOUR);
			// 判断应付金额是否为空
			if (StringUtils.isNotEmpty(totalPayAmountStr)) {
				// 实例应付金额
				payAmount = new BigDecimal(totalPayAmountStr);
				// 保留两位小数
				payAmount = payAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
				// 设置应付金额
				recAndPayImportDto.setPayAmount(payAmount);
				// 计算应付金额
				totalPayAmount = totalPayAmount.add(payAmount);
			}
			
			/**
			 * @author 218392 zhangyongxue 2015-07-29 15:58:50
			 * 判断备注是否为空
			 */
			//获取excel中的数据添加到应付单集合
			String notes = listRow.get(LAND_REC_PAY_NOTES_FIVE);
			if(StringUtils.isNotEmpty(notes)){
				recAndPayImportDto.setNotes(notes);
			}else{
				throw new SettlementException("备注为空，不能进行导入操作!");
			}
			
			/**
			 * @author 218392 zhangyongxue 2015-07-30 10:52:30
			 * 判断其他应付类型是否为空
			 */
			//获取excel中的数据添加到应付单集合
			String otherPayType = listRow.get(LAND_REC_PAY__OTHERPAYTYPE_SIX);
			if(StringUtils.isNotEmpty(otherPayType)){
				recAndPayImportDto.setOtherPayType(otherPayType);
			}else{
				throw new SettlementException("其他应付类型为空，不能进行导入操作!");
			}
			
			// 设置对象到对象集合中
			billRecAndPayImportDtos.add(recAndPayImportDto);
		}
		// 设置总条数
		billRecAndPayImportDto.setTotal(tatal);
		// 设置应收总金额
		billRecAndPayImportDto.setTotalRecAmount(totalRecAmount);
		// 设置应付总金额
		billRecAndPayImportDto.setTotalPayAmount(totalPayAmount);
		// 获取所有的参数设置到对象中的list的集合中
		billRecAndPayImportDto.setBillRecAndPayImportDtoList(billRecAndPayImportDtos);
		return billRecAndPayImportDto;
	}

	private BillRecAndPayImportDto validaWay(
			List<WaybillEntity> waybillEntities, List<String> listRow) {
		BillRecAndPayImportDto recAndPayImportDto;
		recAndPayImportDto = new BillRecAndPayImportDto();

		// 获取excel中的数据添加到运单号集合
		String waybillNo = listRow.get(LAND_REC_PAY_WAYBILLNO_ONE);
		// 设置运单号
		recAndPayImportDto.setWaybillNo(waybillNo);
		recAndPayImportDto.setSourceBillNo(waybillNo);
		if (StringUtils.isNotEmpty(waybillNo)
				&& CollectionUtils.isNotEmpty(waybillEntities)) {
			for (WaybillEntity waybillEntity : waybillEntities) {
				validaRecAndPay(recAndPayImportDto, waybillNo,
						waybillEntity);
				
			}
		}
		// 获取excel中的数据添加到代理集合
		String customerCode = listRow.get(LAND_REC_PAY_CUSTOMERCODE_TWO);
		recAndPayImportDto.setCustomerCode(customerCode);

		/**
		 * 根据快递代理编码 查询 快递代理
		 */
		BusinessPartnerExpressEntity businessPartnerExpressEntity = ldpAgencyCompanyService.queryEntityByCode(customerCode,FossConstants.ACTIVE);
		// 该代理不存在！不能进行导入操作！
		if (null == businessPartnerExpressEntity) {
			throw new SettlementException("该" + customerCode + "对接的代理不存在，不能进行导入操作！");
		}else{
			recAndPayImportDto.setCustomerName(businessPartnerExpressEntity.getAgentCompanyName());
		}
		return recAndPayImportDto;
	}

	private void validaRecAndPay(BillRecAndPayImportDto recAndPayImportDto,
			String waybillNo, WaybillEntity waybillEntity) {
		if (StringUtils.equals(waybillNo,waybillEntity.getWaybillNo())) {
			// 设置部门code
			recAndPayImportDto.setOrigOrgCode(waybillEntity.getCreateOrgCode());
			
			// 根据编码查询收货部门的name
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService
					.queryOrgAdministrativeInfoByCode(waybillEntity.getCreateOrgCode());
			if (orgAdministrativeInfoEntity != null) {
				// 设置部门name
				recAndPayImportDto.setOrigOrgName(orgAdministrativeInfoEntity.getName());
			}
		}
	}

	/**
	 * 保存应收、应付信息
	 * 
	 * @author foss-guxinhua
	 * @date 2012-11-13 下午3:06:33
	 * @return
	 * @see
	 */
	@Transactional
	public void saveLandImportPayAndRec(
			BillRecAndPayImportDto billRecAndPayImportDto,
			CurrentInfo currentInfo) {
		// 判断参数是否为空
		if (billRecAndPayImportDto == null || CollectionUtils.isEmpty(billRecAndPayImportDto.getBillRecAndPayImportDtoList())) {
			throw new SettlementException("内部错误，应收、应付代理成本对象为空!");
		}
		
		// 获取当前选择的应收部门组织的的实体信息
		OrgAdministrativeInfoEntity generatOrgEntity = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCode(currentInfo.getCurrentDeptCode());
		
		if(generatOrgEntity == null){
			throw new SettlementException("内部异常，当前部门编码："+currentInfo.getCurrentDeptCode()+"对应部门为空，不能保存快递代理其他应收、应付信息!");
		}
		
		//判断，当前登陆用户不属于外场部门，
		if(!StringUtils.equals(generatOrgEntity.getTransferCenter(), FossConstants.YES)){
			throw new SettlementException("当前部门不是顶级外场，不能保存快递代理其他应收、应付信息!");
		}
		
		/*
		 * 3.60特惠件 快递代理新增加的一种三级产品
		 * 
		 * 10562 2014年8月6日 上午10:04:05
		 */
		//获取运单号集合
		validaRecAndPayImportDto(billRecAndPayImportDto, currentInfo,
				generatOrgEntity);
	}

	private void validaRecAndPayImportDto(
			BillRecAndPayImportDto billRecAndPayImportDto,
			CurrentInfo currentInfo,
			OrgAdministrativeInfoEntity generatOrgEntity) {
		List<String> waybillNoList = new ArrayList<String>();
		for (BillRecAndPayImportDto recAndPayImportDto : billRecAndPayImportDto.getBillRecAndPayImportDtoList()) {
			waybillNoList.add(recAndPayImportDto.getWaybillNo());
		}
		//查询运单
		 List<WaybillEntity> waybillEntityList = waybillManagerService.queryWaybillBasicByNoList(waybillNoList);
		 //处理成一个Map
		 Map<String,String> productCodeMap = new HashMap<String,String>();
		 for(WaybillEntity e: waybillEntityList){
			 if(!productCodeMap.containsKey(e.getWaybillNo())){
				 productCodeMap.put(e.getWaybillNo(), e.getProductCode());
			 }
		 }
		
		// 声明实例billReceivableEntity对象
		getBillReceivable(billRecAndPayImportDto, currentInfo,
				generatOrgEntity, productCodeMap);
		// 声明实例billPayableEntity对象
		BillPayableEntity billPayableEntity = new BillPayableEntity();
		for (BillRecAndPayImportDto recAndPayImportDto : billRecAndPayImportDto.getBillRecAndPayImportDtoList()) {
			if (recAndPayImportDto.getPayAmount() != null
					&& recAndPayImportDto.getPayAmount().compareTo(BigDecimal.ZERO) > 0) {
				// 设置id
				billPayableEntity.setId(UUIDUtils.getUUID());
				// 设置运单号，如为空，设置默认单号
				if (StringUtils.isEmpty(recAndPayImportDto.getWaybillNo())) {
					billPayableEntity.setWaybillNo(SettlementConstants.DEFAULT_BILL_NO);
				} else {
					billPayableEntity.setWaybillNo(recAndPayImportDto.getWaybillNo());
					billPayableEntity.setSourceBillNo(recAndPayImportDto.getWaybillNo());
				}

				// 出发部门（收货部门）code
				if (StringUtils.isNotEmpty(recAndPayImportDto.getOrigOrgCode())) {
					billPayableEntity.setOrigOrgCode(recAndPayImportDto.getOrigOrgCode());
				} else {
					billPayableEntity.setOrigOrgCode(currentInfo.getCurrentDeptCode());
				}
				// 出发部门（收货部门）name
				if (StringUtils.isNotEmpty(recAndPayImportDto.getOrigOrgName())) {
					billPayableEntity.setOrigOrgName(recAndPayImportDto.getOrigOrgName());
				} else {
					billPayableEntity.setOrigOrgName(currentInfo.getCurrentDeptName());
				}

				valiidaOrgAdministrative(currentInfo, generatOrgEntity,
						productCodeMap, billPayableEntity, recAndPayImportDto);
				
				billPayableService.addBillPayable(billPayableEntity,currentInfo);
			}
		}
	}

	private void valiidaOrgAdministrative(CurrentInfo currentInfo,
			OrgAdministrativeInfoEntity generatOrgEntity,
			Map<String, String> productCodeMap,
			BillPayableEntity billPayableEntity,
			BillRecAndPayImportDto recAndPayImportDto) {
		billPayableEntity.setDestOrgCode(currentInfo.getCurrentDeptCode());
		billPayableEntity.setDestOrgName(currentInfo.getCurrentDeptName());

		// 设置快递代理其他应付管理的系统生成方式
		billPayableEntity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);

		// 设置快递代理其他应付管理的来源单据类型 手工输入
		billPayableEntity.setSourceBillType(SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__WAYBILL);

		// 设置快递代理其他应付管理的单据子类型
		billPayableEntity.setBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__LAND_STOWAGE_OTHER);

		// 设置审核状态为未审核
		billPayableEntity.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__NOT_AUDIT);

		// 设置快递代理其他应付管理的代理为界面输入信息
		billPayableEntity.setCustomerCode(recAndPayImportDto.getCustomerCode());
		// 设置快递代理其他应付管理的代理为界面输入信息
		billPayableEntity.setCustomerName(recAndPayImportDto.getCustomerName());
		// 设置快递代理其他应付管理的应付子公司code
		billPayableEntity.setPayableOrgCode(currentInfo.getCurrentDeptCode());
		// 设置快递代理其他应付管理的应付子公司name
		billPayableEntity.setPayableOrgName(currentInfo.getCurrentDeptName());

		if (generatOrgEntity != null) {
			// 设置快递代理其他应付管理的应付部门code
			billPayableEntity.setPayableComCode(generatOrgEntity.getSubsidiaryCode());
			// 设置快递代理其他应付管理的应付部门name
			billPayableEntity.setPayableComName(generatOrgEntity.getSubsidiaryName());
		}

		// 生效状态不能为空
		billPayableEntity.setEffectiveStatus(SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__YES);

		// 支付状态不能为空
		billPayableEntity.setPayStatus(SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__NO);

		// 冻结状态不能为空
		billPayableEntity.setFrozenStatus(SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__NOT_FROZEN);

		// 设置快递代理其他应付管理的金额
		billPayableEntity.setAmount(recAndPayImportDto.getPayAmount());

		// 设置快递代理其他应付管理的未核销金额
		billPayableEntity.setUnverifyAmount(recAndPayImportDto.getPayAmount());

		// 设置快递代理其他应付管理的核销金额
		billPayableEntity.setVerifyAmount(BigDecimal.ZERO);

		// 设置快递代理其他应付管理的币种
		billPayableEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);

		// 业务日期、记账日期为系统当前时间；创建时间默认为系统当前时间
		validaCurrentInfo(currentInfo, productCodeMap,
				billPayableEntity, recAndPayImportDto);
	}

	private void validaCurrentInfo(CurrentInfo currentInfo,
			Map<String, String> productCodeMap,
			BillPayableEntity billPayableEntity,
			BillRecAndPayImportDto recAndPayImportDto) {
		Date now = new Date();
		// 设置快递代理其他应付管理的业务时间
		billPayableEntity.setBusinessDate(now);

		// 设置快递代理其他应付管理的记账时间
		billPayableEntity.setAccountDate(now);

		// 设置快递代理其他应付管理的确认付入日期,暂未空
		
		// 变更
		billPayableEntity.setSignDate(now);

		// 新增的快递代理其他应付单单据默认为有效的单据
		billPayableEntity.setActive(FossConstants.ACTIVE);
		// 新增的快递代理其他应付单单据默认为非红单单据
		billPayableEntity.setIsRedBack(FossConstants.NO);
		// 新增的快递代理其他应付单单据默认为非初始化单据
		billPayableEntity.setIsInit(FossConstants.NO);
		// 版本号
		billPayableEntity.setVersionNo(FossConstants.INIT_VERSION_NO);
		// 默认单号
		billPayableEntity.setStatementBillNo(SettlementConstants.DEFAULT_BILL_NO);

		/*
		 * 3.60特惠件 快递代理新增加的一种三级产品
		 * 
		 * 10562 2014年8月6日 上午10:23:40
		 */
		billPayableEntity.setProductCode(productCodeMap.get(recAndPayImportDto.getWaybillNo()));
		// 设置备注
		billPayableEntity.setNotes(recAndPayImportDto.getNotes());
		
		/**
		 * 设置 其他应付类型 ，这里要进行转换讲Grid里的汉字转换成对应的code然后保存到后台
		 * @author 218392  zhangyongxue 2015-07-30 15:00:30
		 */
		if(StringUtils.equals("拖车费", recAndPayImportDto.getOtherPayType())){
			billPayableEntity.setPayableType("TF");
		}else if(StringUtils.equals("转寄费", recAndPayImportDto.getOtherPayType())){
			billPayableEntity.setPayableType("TSF");
		}else if(StringUtils.equals("返货费", recAndPayImportDto.getOtherPayType())){
			billPayableEntity.setPayableType("RGF");
		}else if(StringUtils.equals("重量偏差费", recAndPayImportDto.getOtherPayType())){
			billPayableEntity.setPayableType("WDF");
		}else if(StringUtils.equals("进仓费", recAndPayImportDto.getOtherPayType())){
			billPayableEntity.setPayableType("IHF");
		}else if(StringUtils.equals("超重派送费", recAndPayImportDto.getOtherPayType())){
			billPayableEntity.setPayableType("ODF");
		}else if(StringUtils.equals("其他", recAndPayImportDto.getOtherPayType())){
			billPayableEntity.setPayableType("OF");
		}

		// 设置快递代理其他应付管理的应付单号,应付单单号系统自动生成，生成规则“YF93+7位流水号”
		billPayableEntity.setPayableNo(settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YF93));
		//设置出发和到达部门快递代理点部为当前  --根据与王芳、张仲秋商议，快递代理这边都获取当前登录部门
		billPayableEntity.setExpressOrigOrgCode(currentInfo.getCurrentDeptCode());
		billPayableEntity.setExpressOrigOrgName(currentInfo.getCurrentDeptName());
		billPayableEntity.setExpressDestOrgCode(currentInfo.getCurrentDeptCode());
		billPayableEntity.setExpressDestOrgName(currentInfo.getCurrentDeptName());
		
		/*
		 * 税务改造需求
		 * 
		 * 快递代理其他应付发票标记为 02-非运输专票
		 * 
		 * 杨书硕 2013-11-5 上午9:58:56
		 */
		
		billPayableEntity.setInvoiceMark(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_TWO);
	}

	private void getBillReceivable(
			BillRecAndPayImportDto billRecAndPayImportDto,
			CurrentInfo currentInfo,
			OrgAdministrativeInfoEntity generatOrgEntity,
			Map<String, String> productCodeMap) {
		BillReceivableEntity billReceivableEntity = new BillReceivableEntity();
		for (BillRecAndPayImportDto recAndPayImportDto : billRecAndPayImportDto.getBillRecAndPayImportDtoList()) {
			if (recAndPayImportDto.getRecAmount() != null
					&& recAndPayImportDto.getRecAmount().compareTo(BigDecimal.ZERO) > 0) {
				// 设置id
				billReceivableEntity.setId(UUIDUtils.getUUID());
				// 设置快递代理其他应收管理的应收单号,应收单单号系统自动生成，生成规则“YS93+7位流水号”
				billReceivableEntity.setReceivableNo(settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YS93));

				// 设置快递代理其他应收管理的系统生成方式
				billReceivableEntity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);
				// 如果单号为空，则写入默认单号
				if (StringUtils.isEmpty(recAndPayImportDto.getWaybillNo())) {
					billReceivableEntity.setWaybillNo(SettlementConstants.DEFAULT_BILL_NO);
				} else {
					billReceivableEntity.setWaybillNo(recAndPayImportDto.getWaybillNo());
					billReceivableEntity.setSourceBillNo(recAndPayImportDto.getWaybillNo());
				}

				// 出发部门（收货部门）code
				if (StringUtils.isNotEmpty(recAndPayImportDto.getOrigOrgCode())) {
					billReceivableEntity.setOrigOrgCode(recAndPayImportDto.getOrigOrgCode());
				} else {
					billReceivableEntity.setOrigOrgCode(currentInfo.getCurrentDeptCode());
				}
				// 出发部门（收货部门）name
				if (StringUtils.isNotEmpty(recAndPayImportDto.getOrigOrgName())) {
					billReceivableEntity.setOrigOrgName(recAndPayImportDto.getOrigOrgName());
				} else {
					billReceivableEntity.setOrigOrgName(currentInfo.getCurrentDeptName());
				}
				billReceivableEntity.setDestOrgCode(currentInfo.getCurrentDeptCode());
				billReceivableEntity.setDestOrgName(currentInfo.getCurrentDeptName());

				// 设置快递代理其他应收管理的来源单据类型 手工输入
				billReceivableEntity.setSourceBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__MANUAL);

				// 设置快递代理其他应收管理的单据子类型
				billReceivableEntity.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_RECEIVABLE);

				// 设置快递代理其他应收管理的代理为界面输入信息
				billReceivableEntity.setCustomerCode(recAndPayImportDto.getCustomerCode());
				// 设置快递代理其他应收管理的代理为界面输入信息
				billReceivableEntity.setCustomerName(recAndPayImportDto.getCustomerName());
				// 设置快递代理其他应收管理的收货部门code
				billReceivableEntity.setGeneratingOrgCode(currentInfo.getCurrentDeptCode());
				// 设置快递代理其他应收管理的收货部门name
				billReceivableEntity.setGeneratingOrgName(currentInfo.getCurrentDeptName());

				// 催款部门编码
				billReceivableEntity.setDunningOrgCode(currentInfo.getCurrentDeptCode());
				// 催款部门名称
				billReceivableEntity.setDunningOrgName(currentInfo.getCurrentDeptName());

				// 设置应收部门编码
				billReceivableEntity.setReceivableOrgCode(currentInfo.getCurrentDeptCode());
				// 设置应收部门名称
				billReceivableEntity.setReceivableOrgName(currentInfo.getCurrentDeptName());

				if (generatOrgEntity != null) {
					// 设置收货部门code
					billReceivableEntity.setGeneratingComCode(generatOrgEntity.getSubsidiaryCode());// 当前登录用户操作公司
					// 设置收货部门名称
					billReceivableEntity.setGeneratingComName(generatOrgEntity.getSubsidiaryName());
				}

				// 金额和未核销金额为其它应收金额
				// 设置快递代理其他应收管理的金额
				billReceivableEntity.setUnverifyAmount(recAndPayImportDto.getRecAmount());
				billReceivableEntity.setAmount(recAndPayImportDto.getRecAmount());

				// 设置快递代理其他应收管理的未核销金额,已核销金额默认为0
				billReceivableEntity.setVerifyAmount(BigDecimal.ZERO);

				// 设置快递代理其他应收管理的币种
				billReceivableEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);

				// BUG-17091
				// 支付方式 月结
				billReceivableEntity.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT);

				// 业务日期、记账日期为系统当前时间；创建时间默认为系统当前时间
				Date now = new Date();
				// 设置快递代理其他应收管理的业务时间
				billReceivableEntity.setBusinessDate(now);

				// 设置快递代理其他应收管理的记账时间
				billReceivableEntity.setAccountDate(now);
				
				// 变更
		    	billReceivableEntity.setConrevenDate(now);

				// 设置快递代理应收部门code
				billReceivableEntity.setReceivableOrgCode(currentInfo.getCurrentDeptCode());

				// 新增的快递代理其他应收单单据默认为有效的单据
				billReceivableEntity.setActive(FossConstants.YES);
				// 新增的快递代理其他应收单单据默认为非红单单据
				billReceivableEntity.setIsRedBack(FossConstants.NO);
				// 新增的快递代理其他应收单单据默认为有效的单据
				billReceivableEntity.setIsInit(FossConstants.NO);
				// 未审核
				billReceivableEntity.setApproveStatus(SettlementDictionaryConstants.NOTE_APPLICATION__APPROVE_STATUS__NOT_AUDIT);
				// 版本号
				billReceivableEntity.setVersionNo(FossConstants.INIT_VERSION_NO);
				// 默认单号
				billReceivableEntity.setStatementBillNo(SettlementConstants.DEFAULT_BILL_NO);

				/*
				 * 3.60特惠件 快递代理新增加的一种三级产品
				 * 
				 * 10562 2014年8月6日 上午10:23:14
				 */
				billReceivableEntity.setProductCode(productCodeMap.get(recAndPayImportDto.getWaybillNo()));
				
				billReceivableEntity.setNotes(recAndPayImportDto.getNotes());
				//设置出发和到达部门快递代理点部为当前
				billReceivableEntity.setExpressOrigOrgCode(currentInfo.getCurrentDeptCode());
				billReceivableEntity.setExpressOrigOrgName(currentInfo.getCurrentDeptName());
				billReceivableEntity.setExpressDestOrgCode(currentInfo.getCurrentDeptCode());
				billReceivableEntity.setExpressDestOrgName(currentInfo.getCurrentDeptName());
				/*
				 * 税务改造需求
				 * 
				 * 快递代理其他应收单发票标记为 02-非运输专票
				 * 
				 * 杨书硕 2013-11-5 上午9:58:56
				 */
				
				billReceivableEntity.setInvoiceMark(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_TWO);

				billReceivableService.addBillReceivable(billReceivableEntity,currentInfo);
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
	 * @return ldpAgencyCompanyService
	 */
	public ILdpAgencyCompanyService getLdpAgencyCompanyService() {
		return ldpAgencyCompanyService;
	}

	/**
	 * @param ldpAgencyCompanyService
	 */
	public void setLdpAgencyCompanyService(
			ILdpAgencyCompanyService ldpAgencyCompanyService) {
		this.ldpAgencyCompanyService = ldpAgencyCompanyService;
	}

}
