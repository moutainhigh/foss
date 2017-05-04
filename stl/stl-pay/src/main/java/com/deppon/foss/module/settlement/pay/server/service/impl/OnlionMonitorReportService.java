package com.deppon.foss.module.settlement.pay.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.ws.Holder;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.deppon.esb.header.ESBHeader;
import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.framework.server.components.export.excel.SheetData;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.service.IOnlionMonitorReportService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.OnlionMonitorReportDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.OnlionMonitorReportListDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.OnlionMonitorReportResultDto;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.mydeppon.module.finance.shared.ws.CommonException;
import com.deppon.mydeppon.module.finance.shared.ws.IOnlinePayWebServcies;
import com.deppon.mydeppon.module.finance.shared.ws.OnlinePayInfo;
import com.deppon.mydeppon.module.finance.shared.ws.QueryOnlnePayListRequest;
import com.deppon.mydeppon.module.finance.shared.ws.QueryOnlnePayListResponse;

/**
 * 查询在线支付信息服务
 * 
 * @author 045738-foss-maojianqiang
 * @date 2012-12-27 上午8:32:42
 */
public class OnlionMonitorReportService implements IOnlionMonitorReportService {

	/**
	 * 日志
	 */
	private static final Logger LOGGER = LogManager
			.getLogger(OnlionMonitorReportService.class);
	/**
	 * 是否在途运输途中
	 */
	private static final String ISONWAY_NOT = "否";
	private static final String ISONWAY_YES = "是";
	/**
	 * 支付类型
	 */
	private static final String  PAYTYPE_RECEIVE = "应收单";
	private static final String  PAYTYPE_STATEMENT = "对账单";
	
	/**
	 * 支付状态
	 */
	private static final String  PAYSTATUS_SUCCESS = "支付成功";
	private static final String  PAYSTATUS_NO = "未支付";
	private static final String  PAYSTATUS_FAILURE = "支付失败";
	
	/**
	 * 核销状态
	 */
	private static final String VERIFYSTATUS_NO = "未核销";
	private static final String VERIFYSTATUS_FAILURE = "核销失败";
	private static final String VERIFYSTATUS_SUCCESS = "核销成功";

	/**
	 * 注入esb查询在线支付信息接口
	 */
	private IOnlinePayWebServcies onlinePayWebServcies;

	/**
	 * 注入部门service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/**
	 * 注入组织service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	/**
	 * 注入应收单service
	 */
	private IBillReceivableService billReceivableService;
	
	/**
	 * 根据产品CODE找对应的名称
	 */
	private IProductService  productService;
	
	/**
	 * 查询在线支付信息
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-27 上午8:32:42
	 * @param queryDto
	 * @return 查询结果
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IOnlionMonitorReportService#queryOnlionMointorList(com.deppon.foss.module.settlement.pay.api.shared.dto.OnlionMonitorReportDto)
	 */
	@Override
	public OnlionMonitorReportResultDto queryOnlionMointorList(
			OnlionMonitorReportDto queryDto, int start, int limit) {
		// 校验传入参数
		validateQueryParam(queryDto);
		// 对结果进行转化，转化为结算返回dto
		OnlionMonitorReportResultDto resuntDto = convertParamToQuery(queryDto,
				start, limit);
		return resuntDto;
	}

	/**
	 * 封装excel文件
	 * @author 045738-foss-maojianqiang
	 * @date 2012-11-21 上午10:06:12
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IBillPayableQueryManageservice#exportBillPayable(com.deppon.foss.module.settlement.pay.api.shared.dto.BillPayableManageDto)
	 */
	@Override
	public HSSFWorkbook exportBillPayable(OnlionMonitorReportDto queryDto) {
	
		// 调用执行方法，获取结果集
		OnlionMonitorReportResultDto resultDto = convertParamToQuery(queryDto, 1,
				Integer.MAX_VALUE);
		// 判断要导出数据是否存在，若不存在，则抛出异常提示
		if (resultDto == null || resultDto.getList() == null
				|| resultDto.getList().size() == 0) {
			throw new SettlementException("没有要导出的数据!");
		}
		// 将要导出东西封装转化成Excel导出要用的List<List<String>> 格式
		List<List<String>> rowList = convertListFormEntity(resultDto.getList());

		// 获取导出数据
		SheetData data = new SheetData();
		String[] rowHeads = {
			"运单\\对账单号","支付编号","运输性质","事业部",	"大区","小区","部门",
			"客户编码","客户名称","登录用户名","应收总额","已支付金额",	
			"支付类型","支付状态","支付时间",	"核销状态","核销时间",	
			"付款方式","是否运输在途支付"
		};
		
		// 设置导出列头
		data.setRowHeads(rowHeads);
		data.setRowList(rowList);
		// 获取平台提供导出函数
		ExcelExport export = new ExcelExport();
		// 返回wookbook
		HSSFWorkbook wookbook = export.exportExcel(data, "sheet",
				SettlementConstants.EXPORT_EXCEL_MAX_COUNTS);
		return wookbook;
	}
	
	
	/**
	 * list的实体转化成list<list<String>
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-11-21 下午2:25:57
	 */
	private List<List<String>> convertListFormEntity(
			List<OnlionMonitorReportListDto> list) {
		
		// 声明sheetList
		List<List<String>> sheetList = new ArrayList<List<String>>();
		
		// 循环进行封装
		for (OnlionMonitorReportListDto entity : list) {
			// 声明一行的rowList
			List<String> rowList = new ArrayList<String>();
			rowList.add(entity.getNumber());
			rowList.add(entity.getPayNumber());
			if(StringUtils.isNotEmpty(entity.getProductCode())){
				ProductEntity product = productService.getProductByCache(entity.getProductCode(),new Date());
				if(null != product){
					rowList.add(product.getName());
				}else{
					rowList.add(null);
				}
			}else{
				rowList.add(null);
			}
			rowList.add(entity.getBusinessDivision());
			rowList.add(entity.getLargeArea());
			rowList.add(entity.getSmallArea());
			rowList.add(entity.getDeptName());
			rowList.add(entity.getCustNumber());
			rowList.add(entity.getCustName());
			rowList.add(entity.getUserName());
			rowList.add(entity.getTotalAmount()==null ? null:entity.getTotalAmount().toString());
			rowList.add(entity.getOnlinePayAmount()==null ? null:entity.getOnlinePayAmount().toString());
			//进行数据转化 --单据类型
			if(SettlementESBDictionaryConstants.BHO_ONLION_PAYTYPE_RECEIVE.equals(String.valueOf(entity.getPayType()))){
				//应收单
				rowList.add(PAYTYPE_RECEIVE);
			}else{
				//对账单
				rowList.add(PAYTYPE_STATEMENT);
			}
			
			//进行数据转化 --支付状态
			if(SettlementESBDictionaryConstants.BHO_ONLION_PAYSTATUS_SUCCESS.equals(String.valueOf(entity.getPayStatus()))){
				//支付成功
				rowList.add(PAYSTATUS_SUCCESS);
			}else if(SettlementESBDictionaryConstants.BHO_ONLION_PAYSTATUS_NO.equals(String.valueOf(entity.getPayStatus()))){
				//未支付
				rowList.add(PAYSTATUS_NO);
			}else{
				//支付失败
				rowList.add(PAYSTATUS_FAILURE);
			}
			//进行日期格式化
			if(entity.getOnlinePayTime()!=null){
				rowList.add(DateUtils.convert(entity.getOnlinePayTime(),DateUtils.DATE_TIME_FORMAT));
			}else{
				rowList.add(null);
			}
			
			//进行数据转化 --核销状态
			if(SettlementESBDictionaryConstants.BHO_ONLION_VERIFYSTATUS_SUCCESS.equals(String.valueOf(entity.getVerifyStatus()))){
				//核销成功
				rowList.add(VERIFYSTATUS_SUCCESS);
			}else if(SettlementESBDictionaryConstants.BHO_ONLION_VERIFYSTATUS_NO.equals(String.valueOf(entity.getVerifyStatus()))){
				//未核销
				rowList.add(VERIFYSTATUS_NO);
			}else{
				//核销失败
				rowList.add(VERIFYSTATUS_FAILURE);
			}
			//进行日期格式化
			if(entity.getVerifyTime()!=null){
				rowList.add(DateUtils.convert(entity.getVerifyTime(),DateUtils.DATE_TIME_FORMAT));
			}else{
				rowList.add(null);
			}
			//获取支付方式
			rowList.add(String.valueOf(entity.getPaymentType()));
			//获取是否在途运输途中 
			rowList.add(entity.getIsOnway());
			//封装sheet	
			sheetList.add(rowList);
		}
		
		return sheetList;
	}
	
	/**
	 * 校验传入参数
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-27 上午8:35:58
	 * @param queryDto
	 */
	private void validateQueryParam(OnlionMonitorReportDto queryDto) {
		// 校验查询参数
		if (queryDto == null) {
			throw new SettlementException("查询参数不能为空！");
		}
		// 校验必填项
		if (StringUtils.isBlank(queryDto.getSearchType())) {
			throw new SettlementException("查询类型参数不能为空！");
		}
		// 如果查询类型为支付日期或核销日期，则其实日期和结束日期不能为空
		if (StringUtils.equals(SettlementESBDictionaryConstants.BHO_ONLION_QUERYTYPE_PAYDATE, queryDto.getSearchType())
				|| StringUtils.equals(SettlementESBDictionaryConstants.BHO_ONLION_QUERYTYPE_VERIFYDATE, queryDto.getSearchType())) {
			// 如果查询起始和结束日期为空，则抛出异常
			if (queryDto.getStartDate() == null
					|| queryDto.getEndDate() == null) {
				throw new SettlementException("开始日期和结束日期都不能为空！");
			}
		}
		// 如果查询类型为按运单号查询，则运单号集合不能为空
		else if (StringUtils.equals(SettlementESBDictionaryConstants.BHO_ONLION_QUERYTYPE_WAYBILLNOS, queryDto.getSearchType())) {
			if (queryDto.getBillNumber() == null
					|| queryDto.getBillNumber().length == 0) {
				throw new SettlementException("查询运单号集合不能为空！");
			}
		}
		// 如果查询类型为按运单号查询，则运单号集合不能为空
		else if (StringUtils.equals(SettlementESBDictionaryConstants.BHO_ONLION_QUERYTYPE_STATEMENTNOS, queryDto.getSearchType())) {
			if (queryDto.getAccoutNumber() == null
					|| queryDto.getAccoutNumber().length == 0) {
				throw new SettlementException("查询对账单单号集合不能为空！");
			}
		} else {
			throw new SettlementException("查询类型不正确！");
		}
	}

	/**
	 * 进行数据转化
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-27 下午3:42:52
	 * @param queryDto
	 */
	private OnlionMonitorReportResultDto convertParamToQuery(
			OnlionMonitorReportDto queryDto, int start, int limit) {
		// 声明ESB表头
		ESBHeader header = new ESBHeader();
		
		header.setEsbServiceCode(SettlementESBDictionaryConstants.ESB_FOSS2ESB_QUERY_PAYINFO);
		//与业务相关的字段
		header.setBusinessId(UUIDUtils.getUUID());
		header.setExchangePattern(SettlementESBDictionaryConstants.ESB_HEADER__EXCHANGE_PATTERN);
		header.setVersion(SettlementESBDictionaryConstants.ESB_HEADER__VERSION);
		//消息格式
		header.setMessageFormat(SettlementESBDictionaryConstants.ESB_HEADER__MESSAGE_FORMAT);
		header.setRequestId(UUIDUtils.getUUID());
	    //请求系统
		header.setSourceSystem(SettlementESBDictionaryConstants.ESB_HEADER__SOURCE_SYSTEM);
		
		Holder<ESBHeader> esbHeader = new Holder<ESBHeader>(header);

		// 进行数据转化
		QueryOnlnePayListRequest request = new QueryOnlnePayListRequest();
		request.setBilltype(queryDto.getBilltype());
		request.setDeptName(queryDto.getDeptCode());
		request.setEndDate(queryDto.getEndDate());
		request.setPage(start);
		request.setPageSize(limit);
		request.setPayState(queryDto.getPayState());
		request.setRefundState(queryDto.getRefundState());
		request.setSearchType(queryDto.getSearchType());
		request.setStartDate(queryDto.getStartDate());
		//运单号
		if(StringUtils.equals(SettlementESBDictionaryConstants.BHO_ONLION_QUERYTYPE_WAYBILLNOS,queryDto.getSearchType())){
			request.getBillNumber().addAll(Arrays.asList(queryDto.getBillNumber()));
			//对账单号
		}else if(StringUtils.equals(SettlementESBDictionaryConstants.BHO_ONLION_QUERYTYPE_STATEMENTNOS,queryDto.getSearchType())){
			request.getAccoutNumber().addAll(Arrays.asList(queryDto.getAccoutNumber()));
		}


		try {
			// 调用官网接口，获取查询时结果
			QueryOnlnePayListResponse response = onlinePayWebServcies
					.queryOnlnePayList(esbHeader, request);
			// 声明查询结果集dto
			OnlionMonitorReportResultDto dto = new OnlionMonitorReportResultDto();
			convertResultToDto(response, dto);
			return dto;
		} catch (CommonException e) {
			LOGGER.error("调用官网查询在线支付监控数据出错" + e.getMessage(), e);
			throw new SettlementException(e.getMessage());
		}
	}

	/**
	 * 将返回结果集转化为前台要用dto
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-27 下午4:52:04
	 * @param response
	 * @param dto
	 */
	private void convertResultToDto(QueryOnlnePayListResponse response,
			OnlionMonitorReportResultDto resultDto) {
		// 获取返回查询列表数据
		List<OnlinePayInfo> onlinePayInfoList = response.getOnlinePayInfoList();
		//总金额
		BigDecimal totalAmount = BigDecimal.ZERO;
		//在线支付总额
		BigDecimal onlionPayTotalAmount = BigDecimal.ZERO;
		//应收单号集合
		List<String> arNumbers = new ArrayList<String>();
		
		// 非空判断
		if (CollectionUtils.isNotEmpty(onlinePayInfoList)) {
			// 声明list
			List<OnlionMonitorReportListDto> list = new ArrayList<OnlionMonitorReportListDto>();
			//声明部门信息
			Map<String,Object> deptMap = new HashMap<String,Object>();
			//小区
			Map<String,Object> smallAreaMap = new HashMap<String,Object>();
			//大区
			Map<String,Object> largeMap = new HashMap<String,Object>();
			//事业部
			Map<String,Object> divisionMap = new HashMap<String,Object>();
			//声明部门编码
			String deptCode = null;
			//声明小区编码
			//String smallAreaCode = null;
			//声明大区编码
			String largeAreaCode = null;
			// 循环进行转化
			for (OnlinePayInfo entity : onlinePayInfoList) {
				OnlionMonitorReportListDto dto = new OnlionMonitorReportListDto();
				//如果map中没有该部门，则需要去根据标杆编码查询
				if(deptMap.get(entity.getDeptId())!=null){
					dto.setDeptName((String) deptMap.get(entity.getDeptId()));// 部门名称
				}else{
					//调用综合接口，根据标杆查询该部门
					OrgAdministrativeInfoEntity deptEntity= orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCode(entity.getDeptId());
					//非空判断
					if(deptEntity!=null){
						//设置dto为部门名称
						dto.setDeptName(deptEntity.getName());
						//将部门名称放到部门map中
						deptMap.put(entity.getDeptId(), deptEntity.getName());
						//设置部门编码
						deptCode = deptEntity.getCode();
					}
				}
				//如果map中没有该小区，则需要去根据标杆编码查询
				if(smallAreaMap.get(entity.getDeptId())!=null){
					dto.setSmallArea((String) smallAreaMap.get(entity.getDeptId()));// 大区
				}else{
					List<String> bizTypes = new ArrayList<String>();
					//声明
					bizTypes.add(BizTypeConstants.ORG_SMALL_REGION);
					//获取营业区
					OrgAdministrativeInfoEntity smallOrg = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(deptCode, bizTypes);
					//非空判断
					if(smallOrg!=null){
						dto.setSmallArea(smallOrg.getName());
						smallAreaMap.put(entity.getDeptId(),smallOrg.getName());
						//设置小区编码
						//smallAreaCode = smallOrg.getCode();
					}
				}
				//如果map中没有该大区，则需要去根据小区查询
				if(largeMap.get(entity.getDeptId())!=null){
					dto.setLargeArea((String) largeMap.get(entity.getDeptId()));// 大区
				}else{
					//获取大区
					List<String> bizTypes = new ArrayList<String>();
					bizTypes.add(BizTypeConstants.ORG_BIG_REGION);
					//获取营业区
					OrgAdministrativeInfoEntity bigOrg = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(deptCode, bizTypes);
					//大区非空判断
					if(bigOrg!=null){
						dto.setLargeArea(bigOrg.getName());
						largeMap.put(entity.getDeptId(), bigOrg.getName());
						//设置大区编码
						largeAreaCode = bigOrg.getCode();
					}
				}
				//如果事业部map中没有，则需要去根据大区
				if(divisionMap.get(entity.getDeptId())!=null){
					dto.setBusinessDivision((String) divisionMap.get(entity.getDeptId()));
				}else{
					//获取事业部
					List<String> bizTypes = new ArrayList<String>();
					bizTypes.add(BizTypeConstants.ORG_DIVISION);
					//获取营业区
					OrgAdministrativeInfoEntity division = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(largeAreaCode, bizTypes);
					//大区非空判断
					if(division!=null){
						dto.setBusinessDivision(division.getName());
						divisionMap.put(entity.getDeptId(), division.getName());
					}
				}
				dto.setNumber(entity.getPayNumber());// 单号
				dto.setPayNumber(entity.getTradenumber());// 支付编号
				dto.setCustName(entity.getCustId());// 客户名称
				dto.setCustNumber(entity.getCustNumber());// 客户编码
				dto.setUserName(entity.getUserName());// 用户名
				dto.setTotalAmount(entity.getTotalAmount());// 应收总额
				//计算应收总额
				totalAmount = totalAmount.add(entity.getTotalAmount());
				dto.setOnlinePayAmount(entity.getAlreadypayAmount());// 在线支付总额
				//计算在线支付总额
				onlionPayTotalAmount = onlionPayTotalAmount.add(entity.getAlreadypayAmount());
				dto.setPayType(entity.getBillType());// 支付类型
				dto.setPayStatus(entity.getFonlinePayStatus());// 支付状态
				dto.setOnlinePayTime(entity.getCreateTime());// 支付时间
				dto.setVerifyStatus(entity.getErpPayStatus());// 核销状态
				dto.setVerifyTime(entity.getErpRepayTime());// 核销时间
				dto.setPaymentType(entity.getPaytype());// 付款方式
				//获取是否在途运输途中  1、应收单判断，对账单不判断
				if(SettlementESBDictionaryConstants.BHO_ONLION_PAYTYPE_RECEIVE.equals(String.valueOf(entity.getBillType()))){
					//调用应收单接口进行判断
					arNumbers.add(dto.getNumber());
					dto.setIsOnway(ISONWAY_NOT);// 是否在途中运输途中，在后面进行比较
				}
				list.add(dto);
			}
			//如果应收单集合不是空，则进行是否在途运输途中判断
			if(CollectionUtils.isNotEmpty(arNumbers)){
				// 应收单类型集合(始发应收、专线到付)
				List<String> receivableTypeList = new ArrayList<String>();
				//添加应收单类型
				receivableTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE);
				//添加应收单类型
				receivableTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE);
				//调用应收单服务，获取应收单集合
				List<BillReceivableEntity> receiveList = billReceivableService.queryByWaybillNosOrSourceBillNosAndBillTypes(arNumbers, null, null, receivableTypeList, FossConstants.ACTIVE, FossConstants.INACTIVE);
				/**
				 * 判断是否在途运输支付,并把应收单上的运输性质传给resultDto
				 */
				validateIsOnWay(receiveList,list);
			}
			// 设置返回列表
			resultDto.setList(list);
		}
		// 总计
		resultDto.setTotalCount(response.getTotal());
		//应收总额
		resultDto.setTotalAmount(totalAmount);
		//在线支付总额
		resultDto.setOnlionPayTotalAmount(onlionPayTotalAmount);
	}
	
	/**
	 * 校验是否在途运输途中,并把应收单上的运输性质传给resultDto
	 * @author 045738-foss-maojianqiang
	 * @date 2013-1-12 上午9:38:18
	 */
	private void validateIsOnWay(List<BillReceivableEntity> receiveList,List<OnlionMonitorReportListDto> dtoList){
		//循环获取在途运输数据
		for(OnlionMonitorReportListDto dto:dtoList){
			//循环应收单
			for(BillReceivableEntity en:receiveList){
				//同一个单号进行计较处理
				if(dto.getNumber().equals(en.getWaybillNo())){
					//应收单确认收入日期存在，且在线支付时间小于确认收入日期，则表示在途运输支付，反之为否
					if(en.getConrevenDate()== null || dto.getOnlinePayTime().before(en.getConrevenDate())){
						//设置为在途支付
						dto.setIsOnway(ISONWAY_YES);
					}
					dto.setProductCode(en.getProductCode()); // 把应收单上的运输性质传给resultDto
					break;
				}
			}
		}
	}
	
	/**
	 * @param onlinePayWebServcies
	 */
	public void setOnlinePayWebServcies(
			IOnlinePayWebServcies onlinePayWebServcies) {
		this.onlinePayWebServcies = onlinePayWebServcies;
	}

	/**
	 * @param orgAdministrativeInfoService
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * @param orgAdministrativeInfoComplexService
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
	 * @param billReceivableService
	 */
	public void setBillReceivableService(IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}

	/**
	 * @param productService
	 */
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

}
