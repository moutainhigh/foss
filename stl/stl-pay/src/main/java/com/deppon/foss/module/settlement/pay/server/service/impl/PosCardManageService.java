package com.deppon.foss.module.settlement.pay.server.service.impl;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.framework.server.components.export.excel.SheetData;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IFossConfigEntityService;
import com.deppon.foss.module.settlement.common.api.server.service.IPdaPosManageService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.FossConfigEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.PosCardManageDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.service.impl.HttpClientUtil;
import com.deppon.foss.module.settlement.pay.api.server.dao.IPdaPosManageDao;
import com.deppon.foss.module.settlement.pay.api.server.dao.IPosCardManageDao;
import com.deppon.foss.module.settlement.pay.api.server.service.IPosCardManageService;
import com.deppon.foss.module.settlement.pay.api.shared.domain.FinsTradeSerialNoReqDto;
import com.deppon.foss.module.settlement.pay.api.shared.domain.FinsTradeSerialNoRespDto;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;

/**
 * POS刷卡管理实现
 * 
 * @ClassName: PosCardManageService
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2016-1-12 下午5:33:14
 */
public class PosCardManageService implements IPosCardManageService {
	/**
	 * 获取日志
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PosCardManageService.class);

	/**
	 * 注入Dao
	 */
	private IPosCardManageDao posCardManageDao;

	/**
	 * 注入组织service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	/**
	 * 注入service
	 */
	private IPdaPosManageService pdaPosManageService;

	/**
	 * 注入Dao
	 */
	private IPdaPosManageDao pdaPosManageDao;
	
	/**
	 * 查询esb地址信息的接口
	 */
	@Autowired
	private IFossConfigEntityService fossConfigEntityService;
	

	/**
	 * 参数个数为四个
	 */
	private final int NUMBER_OF_FOUR = 4;
	/**
	 * 交易流水号的长度
	 */
	private final int NUMBER_OF_TWELVE = 12;
	/**
	 * 参数个数为3个
	 */
	private final int NUMBER_OF_THREE = 3;
	/**
	 * 查询银行卡报表
	 * 
	 * @author &269052 |zhouyuan008@deppon.com
	 * @date 2016-1-12 下午5:33:14
	 */
	@Override
	public PosCardManageDto queryPosCard(PosCardManageDto dto, int start,
			int limit) {
		LOGGER.info("**********查询银行卡报表开始**********");
		// 获取当前用户信息
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		// 设置参数
		setPosCardParam(dto);
		/*if (StringUtils.isBlank(dto.getLargeAreaCode())&&
				StringUtils.isBlank(dto.getOrgCode()) &&
				StringUtils.isBlank(dto.getLargeAreaCode())){
			throw new SettlementException("大区、小区、营业部至少选择一项！");
		}*/
		// 按大区、小区、部门查询权限
		setSearchArea(dto, currentInfo);
		// 查询总行数
		int count = posCardManageDao.getCountByDate(dto);
		if (count > 0) {
			List<PosCardEntity> posCardEntitys = posCardManageDao
					.queryPosCardEntitys(dto, start, limit);
			for (PosCardEntity posCardEntity : posCardEntitys) {
				BigDecimal frozenAmount = posCardEntity.getFrozenAmount() == null? new BigDecimal(0):posCardEntity.getFrozenAmount();
				//查询时初始化冻结金额
				posCardEntity.setFrozenAmount(frozenAmount);
			}
			dto.setPosCardEntitys(posCardEntitys);
			// 设置总行数
			dto.setCount(count);
		}
		LOGGER.info("**********查询银行卡报表结束**********");
		return dto;
	}

	/**
	 * 还款单按设置区域（大区、小区）、部门查询条件 大区下有多个部门、小区下多个部门或按指定部门查询
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2013-1-12 下午5:03:26
	 */
	private void setSearchArea(PosCardManageDto dto, CurrentInfo currentInfo) {
		// 声明目标部门集合
		List<String> targetList = new ArrayList<String>();
		// 如果部门存在，则获取当前部门与用户所管辖部门的交集
		if (StringUtils.isNotBlank(dto.getOrgCode())) {
			// 则需要查看小区是否选对 或者大区是否选对
			if (StringUtils.isNotBlank(dto.getSmallAreaCode())) {
				List<String> smallRegionDepts = new ArrayList<String>();
				// 调用综合方法查询
				List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService
						.queryOrgAdministrativeInfoEntityAllSubByCode(dto
								.getSmallAreaCode());
				// 循环部门，获取用户所管辖部门与查询到部门的交集
				for (OrgAdministrativeInfoEntity en : orgList) {
					smallRegionDepts.add(en.getCode());
				}
				// 如果所选部门与所选小区不符合，则查询不到
				if (!smallRegionDepts.contains(dto.getOrgCode())) {
					throw new SettlementException("所选小区与所选部门不相符，查询不到数据！");
				}
			} else if (StringUtils.isNotBlank(dto.getLargeAreaCode())) {
				List<String> largeRegionDepts = new ArrayList<String>();
				// 调用综合方法查询
				List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService
						.queryOrgAdministrativeInfoEntityAllSubByCode(dto
								.getLargeAreaCode());
				// 循环部门，获取用户所管辖部门与查询到部门的交集
				for (OrgAdministrativeInfoEntity en : orgList) {
					largeRegionDepts.add(en.getCode());
				}
				// 如果所选部门与所选大区不符合，则查询不到
				if (!largeRegionDepts.contains(dto.getOrgCode())) {
					throw new SettlementException("所选大区与所选部门不相符，查询不到数据！");
				}
			}
			targetList.add(dto.getOrgCode());
			// 如果部门不存在，小区存在，则获取小区地下所有部门与该用户所管辖部门交集
		} else if (StringUtils.isNotBlank(dto.getSmallAreaCode())) {
			// 调用综合方法查询
			List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService
					.queryOrgAdministrativeInfoEntityAllSubByCode(dto
							.getSmallAreaCode());
			// 循环部门，获取用户所管辖部门与查询到部门的交集
			for (OrgAdministrativeInfoEntity en : orgList) {
				targetList.add(en.getCode());
			}
			// 如果部门、小区都不存在，而大区存在， 则获取大区底下所有部门与该用户所管辖部门交集
		} else if (StringUtils.isNotBlank(dto.getLargeAreaCode())) {
			// 调用综合方法查询
			List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService
					.queryOrgAdministrativeInfoEntityAllSubByCode(dto
							.getLargeAreaCode());
			// 循环部门，获取用户所管辖部门与查询到部门的交集
			for (OrgAdministrativeInfoEntity en : orgList) {
				targetList.add(en.getCode());
			}
			// 如果都不存在，则获取默认用户所管辖部门集合
		}
		// 设置可查询部门结果集
		dto.setDeptCodes(targetList);
		// 包装当前登录人编码 为后数据权限传参数
		dto.setEmpCode(currentInfo.getEmpCode());
	}

	/**
	 * 根据流水号查询银行卡报表明细
	 * 
	 * @author &269052 |zhouyuan008@deppon.com
	 * @date 2016-1-12 下午5:33:14
	 */
	@Override
	public PosCardManageDto queryPosCardDetail(PosCardManageDto dto) {
		LOGGER.info("**********根据流水号查询银行卡报表明细开始********");
		// 查询
		List<PosCardDetailEntity> posCardDetailEntity = posCardManageDao
				.queryPosCardDetail(dto);
		// 设置值
		dto.setPosCardDetailEntitys(posCardDetailEntity);
		LOGGER.info("**********根据流水号查询银行卡报表明细结束********");
		return dto;
	}

	/**
	 * 银行卡报表明细导出
	 * 
	 * @author &269052 |zhouyuan008@deppon.com
	 * @date 2016-1-12 下午5:33:14
	 */
	@Override
	public HSSFWorkbook exportPosCard(PosCardManageDto dto, int start, int limit) {
		// 获取用户当前登录信息
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		// 记录日志
		LOGGER.info("POS刷卡管理导出操作开始");
		/**
		 * 如果导出列头名称不存在，则抛出异常提示
		 */
		if (dto.getArrayColumnNames() == null
				|| dto.getArrayColumnNames().length == 0) {
			throw new SettlementException("导出Excel的列头名称不存在，请至少存在一列!");
		}
		/**
		 * 如果导出列头名称不存在，则抛出异常提示
		 */
		if (dto.getArrayColumns() == null || dto.getArrayColumns().length == 0) {
			throw new SettlementException("导出Excel的列头不存在，请至少存在一列!");
		}
		// 调用执行方法，获取结果集
		PosCardManageDto resultDto = queryBankReportDto(dto, 0,
				Integer.MAX_VALUE, currentInfo);
		// 判断要导出数据是否存在，若不存在，则抛出异常提示
		if (resultDto == null || resultDto.getPosCardEntitys() == null
				|| resultDto.getPosCardEntitys().size() == 0) {
			throw new SettlementException("没有要导出的数据!");
		}
		// 将要导出东西封装转化成Excel导出要用的List<List<String>> 格式
		List<List<String>> rowList = convertListFormEntity(
				resultDto.getPosCardEntitys(), dto.getArrayColumns());

		// 获取导出数据
		SheetData data = new SheetData();
		// 设置导出列头
		data.setRowHeads(dto.getArrayColumnNames());
		data.setRowList(rowList);
		// 获取平台提供导出函数
		ExcelExport export = new ExcelExport();
		// 返回wookbook
		HSSFWorkbook wookbook = export.exportExcel(data,
				SettlementConstants.EXCEL_SHEET_NAME,
				SettlementConstants.EXPORT_EXCEL_MAX_COUNTS);
		LOGGER.info("银行卡报表明细导出操作结束");
		return wookbook;
	}

	/**
	 * list的实体转化成list<list<String>
	 * 
	 * @author &269052 |zhouyuan008@deppon.com
	 * @date 2016-1-12 下午5:33:14
	 */
	private List<List<String>> convertListFormEntity(List<PosCardEntity> list,
			String[] header) {
		// 声明sheetList
		List<List<String>> sheetList = new ArrayList<List<String>>();

		// 循环进行封装
		for (PosCardEntity entity : list) {
			// 声明一行的rowList
			List<String> rowList = new ArrayList<String>();
			for (String columnName : header) {
				// 通过名称获取field
				Field field = ReflectionUtils.findField(PosCardEntity.class,
						columnName);
				if (field != null) {
					// 通过传入字段获取值
					ReflectionUtils.makeAccessible(field);
					Object fieldValue = ReflectionUtils.getField(field, entity);
					// 如果为日期，需要进行格式化
					if (Date.class.equals(field.getType())
							&& fieldValue != null) {
						// 日期转化
						fieldValue = DateUtils.convert((Date) fieldValue,
								DateUtils.DATE_TIME_FORMAT);
					}
					// 如果为数字,需要转换
					if (BigDecimal.class.equals(field.getType())
							&& fieldValue != null) {
						// 日期转化
						fieldValue = NumberUtils.createBigDecimal(fieldValue
								.toString());
					}
					// 将字段封装到list中
					if (fieldValue != null) {
						// 设置属性值
						rowList.add(fieldValue.toString());
					} else {
						rowList.add(null);
					}
				}
			}
			sheetList.add(rowList);
		}
		return sheetList;
	}

	/**
	 * 查询POS报表明细DTO
	 * 
	 * @Title: queryBankReportDto
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	private PosCardManageDto queryBankReportDto(PosCardManageDto dto,
			int start, int limit, CurrentInfo currentInfo) {
		LOGGER.info("查询POS报表明细明细开始，开始日期和结束日期：" + dto.getPeriodBeginDate()
				+ " ," + dto.getPeriodEndDate());
		// 实例化dto
		PosCardManageDto resultDto = new PosCardManageDto();

		// 设置参数
		setPosCardParam(dto);

		List<PosCardEntity> resultEntitys = new ArrayList<PosCardEntity>();

		List<PosCardEntity> posCardEntitys = null;

		// 封装流水号集合
		List<String> list = new ArrayList<String>();
		// 获取流水号集合
		String[] serialNos = dto.getSerialNos();
		List<String> s = Arrays.asList(serialNos);
		LOGGER.info("流水号：" + dto.getSerialNos()[0]);
		// 判断流水号是否存在,存在则选中导出
		if (s.get(0).isEmpty()) {
			// 判断是按日期还是单号
			String[] invoices = dto.getInvoices();
			// 判断是否是资金部导出
			String isExport = dto.getIsExport();

			// 判断是否按单号查询
			if ("TD".equals(dto.getQueryTabType()) ) {
				setSearchArea(dto, currentInfo);
				if ("true".equals(isExport)) {
					// 按资金部导出,
					//dto.setBelongMoudleCode(null);
					//dto.setTradeSerialNo(null);
					dto.setUnUsedAmount(null);
					dto.setEmpCode(currentInfo.getEmpCode());
					
					//dto.setDeptCodes(null);
				} /*else {
					setSearchArea(dto, currentInfo);
				}*/
				// 单号为空,则按日期查询
				// 按条件查询导出
				posCardEntitys = posCardManageDao.queryPosCardEntitys(dto,
						start, limit);
			} else {
				// 单号不为空,用单号去查询交易流水号
				posCardEntitys = posCardManageDao.queryPosCardByNumbers(Arrays
						.asList(invoices),currentInfo.getEmpCode());
			}
			// 设置交易流水号
			for (PosCardEntity entity : posCardEntitys) {
				list.add(entity.getTradeSerialNo().trim());
			}
		} else {
			LOGGER.info("" + serialNos);
			// 将数组转换为集合
			list = Arrays.asList(serialNos);
		}
		// 设置流水号数据
		if (list != null && list.size() > 0) {
			// 查询导出数据集合
			resultEntitys = posCardManageDao.queryExportData(list);
		} else {
			throw new SettlementException("当前查询条件下没有查询到数据！");
		}
		/**
		 * 2、根据流水号查询明细
		 */
		// 设置导出结果
		resultDto.setPosCardEntitys(resultEntitys);
		return resultDto;
	}

	/**
	 * 设置参数
	 * 
	 * @Title: setPosCardParam
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	private void setPosCardParam(PosCardManageDto dto) {
		String moudle = dto.getBelongMoudleCode();
		if ("ST".equals(moudle)) {
			moudle = SettlementDictionaryConstants.NCI_STATEMENT;
		} else if ("SE".equals(moudle)) {
			moudle = SettlementDictionaryConstants.NCI_SETTLE;
		} else if ("DE".equals(moudle)) {
			moudle = SettlementDictionaryConstants.NCI_DEPOSIT;
		} else if ("WA".equals(moudle)) {
			moudle = SettlementDictionaryConstants.NCI_WAYBILL;
		} else if ("KD".equals(moudle)) {
			moudle = SettlementDictionaryConstants.NCI_KD;
		} else if ("ALL".equals(moudle)) {
			moudle = null;
		}
		dto.setBelongMoudleCode(moudle);
		// 未使用金额
		String amountString = dto.getUnUsedAmount();
		if ("1".equals(amountString)) {
			amountString = "true";
		} else if ("0".equals(amountString)) {
			amountString = "false";
		} else if ("ALL".equals(amountString)) {
			amountString = null;
		}
		dto.setUnUsedAmount(amountString);
	}

	/**
	 * 按单据号查询流水号明细信息
	 * 
	 * @Title: queryPosCardByNumber
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	public PosCardManageDto queryPosCardByNumber(PosCardManageDto dto) {
		// 获取单号集合
		String[] list = dto.getInvoices();
		//获取当前登录用户
		CurrentInfo info = FossUserContext.getCurrentInfo();
		// 根据单据号去查询
		List<PosCardEntity> entitys = posCardManageDao
				.queryPosCardByNumbers(Arrays.asList(list),info.getEmpCode());
		for (PosCardEntity posCardEntity : entitys) {
			BigDecimal frozenAmount = posCardEntity.getFrozenAmount() == null? new BigDecimal(0):posCardEntity.getFrozenAmount();
			//查询时初始化冻结金额
			posCardEntity.setFrozenAmount(frozenAmount);
		}
		// 设置返回值
		dto.setPosCardEntitys(entitys);
		// 设置总行数
		dto.setCount(list.length);
		return dto;
	}
	
	/**
	 * 导入异常数据
	 * 
	 * @Title: inportExceptionData
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-5-28 下午12:39:50
	 */
	@Override
	public String importExceptionData(String param) {
		LOGGER.info("导入异常数据开始");
		// 获取当前登录用户
		CurrentInfo info = FossUserContext.getCurrentInfo();
		/**
		 * 解析字符串
		 */
		String[] mess = param.split(",");
		
		if (mess.length != NUMBER_OF_FOUR) {
			throw new SettlementException("参数个数为四个！");
		}

		String cardDeptCode = mess[0].trim();
		/**
		 * 交易流水号
		 */
		String tradeSerialNo = mess[1].trim();
		if (tradeSerialNo.length() != NUMBER_OF_TWELVE) {
			throw new SettlementException("交易流水号长度不为12位！");
		}
		if(!StringUtils.isNumeric(tradeSerialNo)){
			throw new SettlementException("交易流水号必须全是数字！");
		}
		BigDecimal amount = BigDecimal.ZERO;
		
		try{
			amount = BigDecimal.valueOf(Double.parseDouble(mess[2].trim()));
		}catch(Exception e){
			throw new SettlementException("金额输入有误，请检查输入！");
		}
		if (BigDecimal.ZERO.compareTo(amount) > 0) {
			throw new SettlementException("流水号金额必须大于0！");
		}
		Date cardTime = null;
		 
		try {	
			String dateStr = "yyyy/MM/dd HH:mm";
			//2016/5/22 10:36
			String date = mess[NUMBER_OF_THREE].trim();
			SimpleDateFormat sdf = new SimpleDateFormat(dateStr);
			//时间校验
			sdf.setLenient(false);
			cardTime = sdf.parse(date);
		} catch (ParseException e) {
			throw new SettlementException("时间错误，请检查！"); 
		}
		
		/**
		 * 封装参数
		 */
		PosCardEntity entity = new PosCardEntity();
		entity.setTradeSerialNo(tradeSerialNo);
		entity.setSerialAmount(amount);
		entity.setCardTime(cardTime);
		entity.setCardDeptCode(cardDeptCode);
		entity.setBelongModule(SettlementDictionaryConstants.NCI_SETTLE);
		entity.setCreateUser(info.getEmpName());
		entity.setCreateUserCode(info.getEmpName());
		/**
		 * 
		 */
		List<PosCardEntity> lists = new ArrayList<PosCardEntity>();
		PosCardManageDto dto = new PosCardManageDto();
		dto.setTradeSerialNo(entity.getTradeSerialNo());
		lists = pdaPosManageDao.queryPosCardData(dto);
		if (lists.size() == 0) {
			pdaPosManageService.insertPosCardMessage(entity, "");
		} else {
			throw new SettlementException("流水号: " + entity.getTradeSerialNo()
					+ "已存在，请重新输入！");
		}
		LOGGER.info("导入异常数据结束");
		return tradeSerialNo;
	}

	/**
	 * 修改异常金额
	 * 
	 * @Title: updateExceptionMoney
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-5-28 下午12:40:37
	 */
	@Override
	public String updateExceptionMoney(String param) {
		LOGGER.info("修改异常金额开始");
		// 获取当前登录用户
		CurrentInfo info = FossUserContext.getCurrentInfo();
		/**
		 * 解析字符串
		 */
		String[] mess = param.split(",");
		if (mess.length != NUMBER_OF_THREE) {
			throw new SettlementException("参数个数为3个！");
		}

		/**
		 * 交易流水号
		 */
		String tradeSerialNo = mess[0].trim();
		if (tradeSerialNo.length() != NUMBER_OF_TWELVE) {
			throw new SettlementException("交易流水号长度不为12位！");
		}
		
		if(!StringUtils.isNumeric(tradeSerialNo)){
			throw new SettlementException("交易流水号必须全是数字！");
		}
		
		String invoiceNo = mess[1].trim();
		BigDecimal amount = BigDecimal.ZERO;
		try{
			amount = BigDecimal.valueOf(Double.parseDouble(mess[2].trim()));
		}catch(Exception e){
			throw new SettlementException("金额输入有误，请检查输入！");
		}
		if (BigDecimal.ZERO.compareTo(amount) > 0) {
			throw new SettlementException("释放必须大于0！");
		}
		
		PosCardDetailEntity entity = new PosCardDetailEntity();
		
		entity.setInvoiceNo(invoiceNo);
		entity.setTradeSerialNo(tradeSerialNo);
		entity.setAmount(amount);
		entity.setModifyUser(info.getEmpName());
		entity.setModifyUserCode(info.getEmpCode());
		pdaPosManageService.updateExceptionData(entity);
		
		LOGGER.info("修改异常金额结束");
		return tradeSerialNo;
	}
	
	/**
	 * POS机刷卡异常管理--删除导入的异常数据,并同步财务自助(事务)
	 *
	 * @Title: deleteExceptionData
	 * @author： 357637 |yuanhuijun001@deppon.com
	 */
	@Override
	@Transactional
	public String deleteExceptionDataTX(String param) {

		LOGGER.info("********pos机刷卡异常管理删除导入异常数据开始********");
		
		//去两头空白
		String tradeSerialNo = param.trim();
		//校验
		if(StringUtils.isBlank(tradeSerialNo)){
			throw new SettlementException("输入的交易流水号不能为空!");
		}
		if(tradeSerialNo.length() != SettlementConstants.TRADESERIALNO_LENGTH || !StringUtils.isNumeric(tradeSerialNo)){
			throw new SettlementException("仅可输入1个长度为12位纯数字交易流水号!");
		}
		List<String> tradeSerialNos = new ArrayList<String>();
		tradeSerialNos.add(tradeSerialNo);
		//查询pos机刷卡管理表中是否有对应的交易流水记录
		List<PosCardEntity> posCardEntites = posCardManageDao.queryPosCardBySerialNos(tradeSerialNos);
		if(CollectionUtils.isEmpty(posCardEntites)){
			throw new SettlementException("输入的交易流水号不存在!");
		}
		//一个流水号只能有一条交易流水记录
		int min_size = 1 ;
		if(posCardEntites.size() != min_size){
			throw new SettlementException("该流交易流水号对应多条POS刷卡数据,不能删除操作!");
		}
		int index = 0;
		PosCardEntity posCardEntity = posCardEntites.get(index);
		//查询出的POS刷卡数据必须是通过[导入异常数据]页面导入的
		String belongModule = posCardEntity.getBelongModule();
		if(!SettlementDictionaryConstants.NCI_SETTLE.equals(belongModule)){
			throw new SettlementException("该数据系统生成,不可删除!");
		}
		//该交易流水号的未使用金额必须等于流水号金额
		if(!(posCardEntity.getUnUsedAmount().subtract(posCardEntity.getSerialAmount())).equals(new BigDecimal(0))){
			throw new SettlementException("该交易流水号已被使用,请先释放金额!");
		}
		pdaPosManageService.deleteExceptionData(posCardEntity);

		// 需要通知财务自助,将POS差异报表中同样的交易流水号数据给删除掉
		pushRemittanceMessToFins(posCardEntity);
		
		LOGGER.info("********pos机刷卡异常管理删除导入异常数据结束********");
		return posCardEntity.getTradeSerialNo();
	}
	

	/**
	 * POS机刷卡异常管理--删除导入的异常数据时要将删除流水号同步到财务自助
	 *
	 * @Title: pushRemittanceMessToFins
	 * @author： 357637 |yuanhuijun001@deppon.com
	 */
	@SuppressWarnings("unchecked")
	private void pushRemittanceMessToFins(PosCardEntity posCardEntity){
		
		LOGGER.info("********POS机刷卡异常管理对接FINS财务自助开始********");
		//用于本地测试使用的url路径
//		String url = "http://10.224.65.138:8080/financManager/webservice/deletePosDataService/deletePosData";
		String url = "";
		//查询对接财务自助删除交易流水号ESB借口的url
		FossConfigEntity configEntity = fossConfigEntityService.queryFossConfigEntityByServerCode(SettlementESBDictionaryConstants.ESB_FOSS2ESB_DELETE_POS_DATA);
		if (null != configEntity && !StringUtil.isEmpty(configEntity.getEsbAddr())) {
			url = configEntity.getEsbAddr();
		} else {
			throw new SettlementException("读取esb地址有误!");
		}
		//请求参数
		FinsTradeSerialNoReqDto req = new FinsTradeSerialNoReqDto();
		//设置请求参数
		List<FinsTradeSerialNoReqDto> reqList = new ArrayList<FinsTradeSerialNoReqDto>();
		req.setTradeSerialNo(posCardEntity.getTradeSerialNo());
		reqList.add(req);
		//将请求信息转换为json字符串
		JSONArray requestParamJson = JSONArray.fromObject(reqList); 
		String requestParamJsonStr = requestParamJson.toString();
		RequestEntity requestEntity = null;
		try {
			requestEntity = new StringRequestEntity(requestParamJsonStr,
					"application/json", "UTF-8");
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(e.getMessage());
			throw new SettlementException("数据错误:封装RequestEntity错误！");
		}
		/**
		 * 本地测试使用,模拟HttpClient发起请求得到响应数据
		 */
//		List<FinsTradeSerialNoRespDto> respList = new ArrayList<FinsTradeSerialNoRespDto>();
//		FinsTradeSerialNoRespDto response = new FinsTradeSerialNoRespDto();
//		response.setTradeSerialNo(posCardEntity.getTradeSerialNo());
//		response.setState("0");
//		String responseBody = JSONArray.fromObject(response).toString();
//		LOGGER.info(responseBody);
		
		String responseBody = "";
		try {
			//HttpClient同步发起请求
			responseBody = HttpClientUtil.postRequest(url, requestEntity);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new SettlementException("同步财务自助删除交易流水号请求异常!!");
		}
		JSONArray jsonArray = null;
		try {
			jsonArray = JSONArray.fromObject(responseBody);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new SettlementException("同步财务自助删除交易流水号服务返回数据异常!!");
		}
		//用于存放财务资助返回删除失败交易流水号的集合
		List<String> list = new ArrayList<String>();
		if (null != jsonArray) {
			//相应参数
			List<FinsTradeSerialNoRespDto> respList = (List<FinsTradeSerialNoRespDto>)JSONArray.toCollection(jsonArray, FinsTradeSerialNoRespDto.class);
			for (FinsTradeSerialNoRespDto responseDto : respList) {
				if(null == responseDto){
					throw new SettlementException("同步财务自助删除交易流水号服务返回数据为空!!");
				}
				LOGGER.info("调用FINS服务端接口后响应的结果...");
				LOGGER.info("是否成功：" + responseDto.getState());
				//判断返回状态
				if(!SettlementConstants.DEL_SERIAL_STATUS_FROM_FINS_BLANK.equals(responseDto.getState()) && !SettlementConstants.DEL_SERIAL_STATUS_FROM_FINS_SUCCESS.equals(responseDto.getState())){
					LOGGER.error("财务自助删除交易流水号失败:"+responseDto.getTradeSerialNo()+"返回响应结果状态:"+responseDto.getState());
					list.add(responseDto.getTradeSerialNo());
				}
				LOGGER.info("********POS机刷卡异常管理对接FINS财务自助结束!!********");
				if(CollectionUtils.isNotEmpty(list)){
					throw new SettlementException("财务自助删除交易流水号"+list.toString()+"失败！");
				}
			}
		}	
	}
	
	/**
	 * POS机刷卡管理--按流水号查询流水信息
	 *
	 * @Title: queryPosCardBySerialNos
	 * @author： 357637 |yuanhuijun001@deppon.com
	 * @date 2016-11-7 下午12:40:37
	 */
	public PosCardManageDto queryPosCardBySerialNos(
			PosCardManageDto posCardManageDto) {
		// 获取交易流水号集合
		String[] serialNosArr = posCardManageDto.getSerialNos();
		// 获取当前登录用户
		CurrentInfo info = FossUserContext.getCurrentInfo();
		// 根据单据号去查询
		List<PosCardEntity> entitys = posCardManageDao
						.queryPosCardBySerialNosAndEmpCode(Arrays.asList(serialNosArr),info.getEmpCode());
		for (PosCardEntity posCardEntity : entitys) {
			BigDecimal frozenAmount = posCardEntity.getFrozenAmount() == null? new BigDecimal(0):posCardEntity.getFrozenAmount();
			//查询时初始化冻结金额
			posCardEntity.setFrozenAmount(frozenAmount);
		}
		// 设置返回值
		posCardManageDto.setPosCardEntitys(entitys);
		// 设置总行数
		posCardManageDto.setCount(entitys.size());
		return posCardManageDto;
	}
	
	/**
	 * POS机刷卡管理--冻结交易流水金额
	 *
	 * @Title: frozenPostCard
	 * @author： 357637 |yuanhuijun001@deppon.com
	 * @date 2016-12-13  下午12:40:37
	 */
	public void frozenPostCard(List<PosCardEntity> posCardEntitys) {
		// 获取当前登录用户
		CurrentInfo info = FossUserContext.getCurrentInfo();
		if(null == info ){
			throw new SettlementException("冻结操作,操作员信息不能为空!!");
		}
		//校验请求参数,校验不通过抛异常
		List<String> tradeSerialNos = checkFrozenRequestParams(posCardEntitys);
		//根据交易流水号查询POS刷卡管理数据如果查询出的数据与请求参数不一致抛异常
		List<PosCardEntity> posCardEntities = posCardManageDao.queryPosCardBySerialNos(tradeSerialNos);
		//校验查询出的数据与前台传进的POS刷卡管理数据的冻结状态是否与数据库中的状态一致,不一致抛异常
		List<PosCardEntity> checkQueryResult = checkFrozenQueryResult(tradeSerialNos,posCardEntitys,posCardEntities);
		//批量更新POS刷卡管理数据的冻结状态,判断更新前与更新后的总条数是否一致,不一致抛异常
		pdaPosManageService.updatePosCardEntitys(checkQueryResult);
		
	}
	
	/**
	 * OS机刷卡管理--校验查询出的需要冻结的数据
	 *
	 * @Title: checkFrozenQueryResult
	 * @author： 357637 |yuanhuijun001@deppon.com
	 * @date 2016-12-13  下午12:40:37
	 */
	private List<PosCardEntity> checkFrozenQueryResult(List<String> tradeSerialNos, List<PosCardEntity> posCardEntitys,
			List<PosCardEntity> posCardEntities) {
		List<PosCardEntity> updatePosCardEntitys = new ArrayList<PosCardEntity>();
		if(CollectionUtils.isEmpty(posCardEntities)){
			throw new SettlementException("查询出的POS机刷卡记录为空,不能进行冻结/取消冻结操作!!"); 
		}
		if(posCardEntities.size() != tradeSerialNos.size()){
			throw new SettlementException("查询出的POS机刷卡记录与进行冻结/取消冻结操作的数据条数不符!!"); 
		}
		for (PosCardEntity posCardEntity : posCardEntities) {
			System.out.println(posCardEntity.toString());
			//POS刷卡记录数据的冻结状态为全额冻结的不允许进行冻结操作
			if(posCardEntity.getFrozenStatus() == 1){
				LOGGER.error("查询出的POS机刷卡交易流水号:"+posCardEntity.getTradeSerialNo()+"冻结状态为:"+posCardEntity.getFrozenStatus()+"全额冻结,不能再进行冻结操作!!");
				throw new SettlementException("查询出的POS机刷卡交易流水号:"+posCardEntity.getTradeSerialNo()+"冻结状态为全额冻结,不能再进行冻结操作!!");
			}
			//POS刷卡记录里的未使用金额等于0是不允许进行冻结/取消冻结操作
			int resoult = posCardEntity.getUnUsedAmount().compareTo(new BigDecimal(0));
			if(resoult == 0){
				LOGGER.error("查询出的POS机刷卡交易流水号:"+posCardEntity.getTradeSerialNo()+"未使用金额为0,不能进行冻结/取消冻结操作!!");
				throw new SettlementException("查询出的POS机刷卡交易流水号:"+posCardEntity.getTradeSerialNo()+"未使用金额为0,不能进行冻结/取消冻结操作!!");
			}
			//POS刷卡记录里的冻结金额等于未使用金额,不允许冻结操作
			BigDecimal frozenAmount = posCardEntity.getFrozenAmount() ==null? new BigDecimal(0):posCardEntity.getFrozenAmount();
			int resoult1 = frozenAmount.compareTo(posCardEntity.getUnUsedAmount());
			if(resoult1 == 0 ){
				LOGGER.error("查询出的POS机刷卡交易流水号:"+posCardEntity.getTradeSerialNo()+"未使用金额已经全部冻结,不能进行冻结操作!!");
				throw new SettlementException("查询出的POS机刷卡交易流水号:"+posCardEntity.getTradeSerialNo()+"未使用金额已经冻结,不能进行冻结操作!!");
			}
			PosCardEntity updatePosCardEntity = new PosCardEntity();
			updatePosCardEntity.setTradeSerialNo(posCardEntity.getTradeSerialNo());
			updatePosCardEntity.setFrozenAmount(posCardEntity.getUnUsedAmount());
			updatePosCardEntity.setVersion(posCardEntity.getVersion());
			updatePosCardEntity.setFrozenTime(new Date());
			updatePosCardEntity.setId(posCardEntity.getId());
			updatePosCardEntity.setModifyDate(new Date());
			int compareTo = posCardEntity.getUnUsedAmount().compareTo(posCardEntity.getSerialAmount());
			if(compareTo == 0){
				//如果未使用金额等于POS机刷卡交易流水金额,冻结状态设置为全部冻结
				updatePosCardEntity.setFrozenStatus(SettlementConstants.POS_CARD_FROZEN_STATUS_1);
			} else if (compareTo < 0 ) {
				//如果未使用金额小于POS机刷卡交易流水基恩,冻结状态设置为部分冻结
				updatePosCardEntity.setFrozenStatus(SettlementConstants.POS_CARD_FROZEN_STATUS_2);
			} else {
				LOGGER.error("查询出的POS机刷卡交易流水号:"+posCardEntity.getTradeSerialNo()+"的未使用金额大于POS机刷卡管理流水金额,不能进行冻结/取消冻结操作!!");
				throw new SettlementException("查询出的POS机刷卡交易流水号:"+posCardEntity.getTradeSerialNo()+"的未使用金额大于POS机刷卡管理流水金额,不能进行冻结/取消冻结操作!!");
			}
			updatePosCardEntitys.add(updatePosCardEntity);
		}
		return updatePosCardEntitys;
	}

	/**
	 * 校验冻结交易流水金额参数
	 *
	 * @Title: checkFrozenRequestParams
	 * @author： 357637 |yuanhuijun001@deppon.com
	 * @date 2016-12-13  下午12:40:37
	 */
	private List<String> checkFrozenRequestParams(List<PosCardEntity> posCardEntitys) {
		List<String> tradeSerialNos = new ArrayList<String>();
		if(CollectionUtils.isEmpty(posCardEntitys)){
			throw new SettlementException("冻结操作,请求参数不能为空!!");
		}
		for (PosCardEntity posCardEntity : posCardEntitys) {
			if(null == posCardEntity.getTradeSerialNo()){
				throw new SettlementException("冻结操作,交易流水号不能为空!!");
			}
			tradeSerialNos.add(posCardEntity.getTradeSerialNo());
		}
		LOGGER.info("请求冻结的交易流水:"+tradeSerialNos.toString());
		return tradeSerialNos;
	}

	
	/**
	 * POS机刷卡管理--取消冻结交易流水金额
	 *
	 * @Title: thawPostCard
	 * @author： 357637 |yuanhuijun001@deppon.com
	 * @date 2016-12-13  下午12:40:37
	 */
	public void thawPostCard(List<PosCardEntity> posCardEntitys) {
		// 获取当前登录用户
		CurrentInfo info = FossUserContext.getCurrentInfo();
		if(null == info ){
			throw new SettlementException("取消冻结操作,操作员信息不能为空!!");
		}
		//校验请求参数,校验不通过抛异常
		List<String> tradeSerialNos = checkThawRequestParams(posCardEntitys);
		//根据交易流水号查询POS刷卡管理数据如果查询出的数据与请求参数不一致抛异常
		List<PosCardEntity> posCardEntities = posCardManageDao.queryPosCardBySerialNos(tradeSerialNos);
		//校验查询出的数据与前台传进的POS刷卡管理数据的冻结状态是否与数据库中的状态一致,不一致抛异常
		List<PosCardEntity> checkQueryResult = checkThawQueryResult(tradeSerialNos,posCardEntitys,posCardEntities);
		//批量更新POS刷卡管理数据的冻结状态,判断更新前与更新后的总条数是否一致,不一致抛异常
		pdaPosManageService.updatePosCardEntitys(checkQueryResult);
		
	}
	
	/**
	 * POS机刷卡管理--校验查询出的需要取消冻结的数据
	 *
	 * @Title: checkThawQueryResult
	 * @author： 357637 |yuanhuijun001@deppon.com
	 * @date 2016-12-13  下午12:40:37
	 */
	private List<PosCardEntity> checkThawQueryResult(
			List<String> tradeSerialNos, List<PosCardEntity> posCardEntitys,
			List<PosCardEntity> posCardEntities) {
		List<PosCardEntity> updatePosCardEntitys = new ArrayList<PosCardEntity>();
		if(CollectionUtils.isEmpty(posCardEntities)){
			throw new SettlementException("查询出的POS机刷卡记录为空,不能进行冻结/取消冻结操作!!"); 
		}
		if(posCardEntities.size() != tradeSerialNos.size()){
			throw new SettlementException("查询出的POS机刷卡记录与进行冻结/取消冻结操作的数据条数不符!!"); 
		}
		for (PosCardEntity posCardEntity : posCardEntities) {
			
			//POS刷卡管理数据的冻结为未冻结的不允许执行取消冻结操作
			short frozenStatus = posCardEntity.getFrozenStatus();
			if(frozenStatus == 0){
				LOGGER.error("查询出的POS机刷卡交易流水号:"+posCardEntity.getTradeSerialNo()+"冻结状态为未冻结,不能进行取消冻结操作!!");
				throw new SettlementException("查询出的POS机刷卡交易流水号:"+posCardEntity.getTradeSerialNo()+"冻结状态为未冻结,不能进行取消冻结操作!!");
			}
			
			//POS刷卡记录里的未使用金额等于0是不允许进行冻结/取消冻结操作
			int resoult = posCardEntity.getUnUsedAmount().compareTo(new BigDecimal(0));
			if(resoult == 0){
				LOGGER.error("查询出的POS机刷卡交易流水号:"+posCardEntity.getTradeSerialNo()+"未使用金额为0,不能进行冻结/取消冻结操作!!");
				throw new SettlementException("查询出的POS机刷卡交易流水号:"+posCardEntity.getTradeSerialNo()+"未使用金额为0,不能进行冻结/取消冻结操作!!");
			}
			//POS刷卡记录里的冻结金额等于0,不允许取消冻结操作
			BigDecimal frozenAmount = posCardEntity.getFrozenAmount() ==null? new BigDecimal(0):posCardEntity.getFrozenAmount();
			int resoult1 = frozenAmount.compareTo(new BigDecimal(0));
			if(resoult1 == 0 ){
				LOGGER.error("查询出的POS机刷卡交易流水号:"+posCardEntity.getTradeSerialNo()+"冻结金额等于0,不能进行取消冻结操作!!");
				throw new SettlementException("查询出的POS机刷卡交易流水号:"+posCardEntity.getTradeSerialNo()+"冻结金额等于0,不能进行取消冻结操作!!");
			}
			//封装更新数据
			PosCardEntity updatePosCardEntity = new PosCardEntity();
			updatePosCardEntity.setTradeSerialNo(posCardEntity.getTradeSerialNo());
			updatePosCardEntity.setFrozenAmount(new BigDecimal(0));
			updatePosCardEntity.setVersion(posCardEntity.getVersion());
			updatePosCardEntity.setFrozenTime(null);
			updatePosCardEntity.setId(posCardEntity.getId());
			updatePosCardEntity.setModifyDate(new Date());
			updatePosCardEntity.setFrozenStatus(SettlementConstants.POS_CARD_FROZEN_STATUS_0);
			updatePosCardEntitys.add(updatePosCardEntity);
		}
		return updatePosCardEntitys;
	}

	/**
	 * POS机刷卡管理--校验请求参数,校验不通过抛异常
	 *
	 * @Title: checkThawRequestParams
	 * @author： 357637 |yuanhuijun001@deppon.com
	 * @date 2016-12-13  下午12:40:37
	 */
	private List<String> checkThawRequestParams(List<PosCardEntity> posCardEntitys) {
		List<String> tradeSerialNos = new ArrayList<String>();
		if(CollectionUtils.isEmpty(posCardEntitys)){
			throw new SettlementException("取消冻结操作,请求参数不能为空!!");
		}
		for (PosCardEntity posCardEntity : posCardEntitys) {
			if(null == posCardEntity.getTradeSerialNo()){
				throw new SettlementException("取消冻结操作,交易流水号不能为空!!");
			}
			tradeSerialNos.add(posCardEntity.getTradeSerialNo());
		}
		LOGGER.info("请求取消冻结的交易流水:"+tradeSerialNos.toString());
		return tradeSerialNos;
	}

	/**
	 * *************************************************************************************************
	 */
	
	
	public void setPosCardManageDao(IPosCardManageDao posCardManageDao) {
		this.posCardManageDao = posCardManageDao;
	}

	public IPosCardManageDao getPosCardManageDao() {
		return posCardManageDao;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setPdaPosManageService(IPdaPosManageService pdaPosManageService) {
		this.pdaPosManageService = pdaPosManageService;
	}

	public void setPdaPosManageDao(IPdaPosManageDao pdaPosManageDao) {
		this.pdaPosManageDao = pdaPosManageDao;
	}

	

	

	
	
}
