package com.deppon.foss.module.settlement.consumer.server.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.framework.server.components.export.excel.SheetData;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.consumer.api.server.service.IOtherRevenueService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.OtherRevenueEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CustomerInfoDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OtherRevenueDto;
import com.deppon.foss.module.settlement.consumer.api.shared.vo.OtherRevenueVo;
import com.deppon.foss.util.DateUtils;

/**
 * 小票操作Action.
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2012-11-16 上午11:30:25
 */
public class OtherRevenueAction extends AbstractAction {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LogManager
			.getLogger(OtherRevenueAction.class);

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6630290848725121792L;

	/** The vo. */
	private OtherRevenueVo vo;

	/** 小票单据Service. */
	private IOtherRevenueService otherRevenueService;

	/** 导出输出流. */
	private ByteArrayInputStream inputStream;

	/** excel名称. */
	private String execlName;

	/** 小票号是否连续. */
	private boolean consecutive;
	 /**
	 * 根据产品CODE找对应的名称
	 */
	private IProductService  productService;

	

	/**
	 * 互斥锁接口
	 */
	private IBusinessLockService businessLockService;

	/**
	 * @param businessLockService
	 */
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	/**
	 * 初始化新增界面.
	 * 
	 * @return the string
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-16 下午1:46:49
	 */
	@JSON
	public String initAddPage() {
		LOGGER.info("into initAddPage");
		return returnSuccess();
	}

	/**
	 * 初始化小票查询界面.
	 * 
	 * @return the string
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-16 下午1:46:49
	 */
	@JSON
	public String initQueryRevenuePage() {
		LOGGER.info("into initQueryRevenuePage");
		return returnSuccess();
	}

	/**
	 * Consequtive.
	 * 
	 * @return the string
	 */
	@JSON
	public String consequtive() {
		try {

			// 获取当前用户信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();

			// 获取用户输入小票单号
			OtherRevenueDto otherRevenueDto = vo.getDto();
			String otherRevenueNo = otherRevenueDto.getOtherRevenueNo();

			// 小票单号为空
			if (StringUtils.isBlank(otherRevenueDto.getOtherRevenueNo())) {
				return returnError("小票单号为空");
			}

			// 小票单号不为8位
			else if (otherRevenueDto.getOtherRevenueNo().length() != SettlementConstants.OTHERREVENUE_RULE_MIN_LENGTH) {
				return returnError("小票单号为8位数字");
			}

			// 判断是否连续
			else {
				consecutive = otherRevenueService.isConsecutiveNum(currentInfo,
						otherRevenueNo);
			}

			return returnSuccess();
		} catch (BusinessException e) {

			// 异常处理
			LOGGER.error(e.getMessage(), e);
			return returnError(e);
		}

	}

	/**
	 * 新增操作.
	 * 
	 * @return the string
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-16 下午1:46:49
	 */
	@JSON
	public String addOtherRevenue() {
		Boolean isflag =false;
		MutexElement mutexElement=null;
		try {
			// 获取当前用户信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			if (null == currentInfo
					|| StringUtils.isBlank(currentInfo.getEmpCode())) {
				// 用户信息不正确,请刷新页面再进行操作!
				return returnError("用户信息不正确,请刷新页面再进行操作!");
			}
			OtherRevenueDto otherRevenueDto = vo.getDto();
			if (StringUtils.isBlank(otherRevenueDto.getOtherRevenueNo())
					|| otherRevenueDto.getOtherRevenueNo().length() != SettlementConstants.OTHERREVENUE_RULE_MIN_LENGTH) {
				// 请输入有效的小票单号!
				return returnError("请输入有效的小票单号!");
			} else if (null == otherRevenueDto.getAmount()) {
				// 请输入有效金额!
				return returnError("请输入有效金额!");
			} else if ( otherRevenueDto.getCustomerName().length()>SettlementReportNumber.TWO_HUNDRED) {
					// 
				throw new SettlementException("客户名称超过最大长度");
			}else if (otherRevenueDto.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
				// 请输入大于零的有效金额!
				return returnError("请输入大于零的有效金额!");
			} else if (StringUtils.isBlank(otherRevenueDto.getPaymentType())) {
				// 请选择收款方式!
				return returnError("请选择收款方式!");
			} else if (StringUtils.isBlank(otherRevenueDto
					.getIncomeCategories())) {
				// 请选择收入类别!
				return returnError("请选择收入类别!");
			} else if (StringUtils.isBlank(otherRevenueDto.getCustomerType())) {
				// 请选择客户类型!
				return returnError("请选择客户类型!");
			} else if (StringUtils.isNotBlank(otherRevenueDto.getWaybillNo())) {
				if (otherRevenueDto.getWaybillNo().length() < SettlementConstants.WAYBILL_RULE_MIN_LENGTH
						|| otherRevenueDto.getWaybillNo().length() > SettlementConstants.WAYBILL_RULE_MAX_LENGTH14) {
					// 请输入8到14位运单号!
					return returnError("请输入8到14位运单号!");
				}
			}
			
			// 业务互锁运单号
			mutexElement = new MutexElement(otherRevenueDto.getOtherRevenueNo(), "新增小票操作",	MutexElementType.OTHER_REVENUE_NO);
			// 锁定
			 isflag = businessLockService.lock(mutexElement,SettlementConstants.BUSINESS_LOCK_BATCH);
			
			if (!isflag) {
				new SettlementException("新增小票失败(锁定失败)");
			}

            String id = otherRevenueService.addOtherRevenue(otherRevenueDto, currentInfo);
			vo.getDto().setId(id);
			
			return returnSuccess("保存成功!");
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			return returnError(e);
		}finally{
			if(isflag){
				businessLockService.unlock(mutexElement);
			}
		}
	}

	/**
	 * 根据客户编码获取客户信息.
	 * 
	 * @return the string
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-21 下午7:00:24
	 */
	@JSON
	public String queryCustomerInfo() {
		try {
			if (null != vo && null != vo.getCustDto()
					&& StringUtils.isNotBlank(vo.getCustDto().getCode())
					&& StringUtils.isNotBlank(vo.getCustDto().getType())) {
				// 根据客户编码获取客户信息
				// 封装到vo上

				// 查询客户代理信息
				CustomerInfoDto dto = otherRevenueService.queryCustomerInfo(vo
						.getCustDto());

				// 设置到VO上
				vo.setCustDto(dto);

			}
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			return returnError(e);
		}
	}
	/**
	 * @author 310970
	 * @Date 2016  02  28
	 * @return the string
	 * 针对于  财务经理作废  还借支 （RB-还借支） 类型的小票
	 */
	@JSON
	public String canxelOtherRevenueOfRB(){
		try {
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			if (null == currentInfo
					|| StringUtils.isBlank(currentInfo.getEmpCode())) {
				// 用户信息不正确,请刷新页面再进行操作!
				return returnError("用户信息不正确,请刷新页面再进行操作!");
			}
			OtherRevenueDto otherRevenueDto = vo.getDto();
			if (null == otherRevenueDto) {
				// 作废失败!
				return returnError("作废失败!");
			}
			if(SettlementConstants.CANCEL_OTHERREVENUE_CLASS.equals(otherRevenueDto.getInvoiceCategory())&&!SettlementConstants.CANCEL_OTHERREVENUE_DEPTCODE.equals(currentInfo.getCurrentDeptCode())){
				return returnError("结清货款时产生的保管费小票不允许作废!请联系包装与代收产品管理组进行作废操作！");
			}
			if (StringUtils.isEmpty(otherRevenueDto.getActive())) {
				// 作废失败!
				return returnError("作废失败!");
			}
			if (StringUtils.isEmpty(otherRevenueDto.getOtherRevenueNo())) {
				// 作废失败!
				return returnError("作废失败!");
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("active:" + otherRevenueDto.getActive());
				LOGGER.debug("otherRevenueNo:"
						+ otherRevenueDto.getOtherRevenueNo());
				LOGGER.debug("id:" + otherRevenueDto.getId());
			}
			//作废还借支小票
			otherRevenueService.cancelOtherRevenueOfRB(otherRevenueDto, currentInfo);
			
			return returnSuccess("作废成功");
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			return returnError(e);
		}
	}
	/**
	 * 作废小票.
	 * 
	 * @return the string
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-20 上午10:16:36
	 */
	@JSON
	public String cancelOtherRevenue() {
		try {
			// 获取当前用户信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			if (null == currentInfo
					|| StringUtils.isBlank(currentInfo.getEmpCode())) {
				// 用户信息不正确,请刷新页面再进行操作!
				return returnError("用户信息不正确,请刷新页面再进行操作!");
			}
			OtherRevenueDto otherRevenueDto = vo.getDto();
			if (null == otherRevenueDto) {
				// 作废失败!
				return returnError("作废失败!");
			}
			//结清货款时产生的保管费小票记录不允许作废，只有包装与代收产品管理组具有作废权限																					
			if(SettlementConstants.CANCEL_OTHERREVENUE_CLASS.equals(otherRevenueDto.getInvoiceCategory())&&!SettlementConstants.CANCEL_OTHERREVENUE_DEPTCODE.equals(currentInfo.getCurrentDeptCode())){
				return returnError("结清货款时产生的保管费小票不允许作废!请联系包装与代收产品管理组进行作废操作！");
			}
			if (StringUtils.isEmpty(otherRevenueDto.getActive())) {
				// 作废失败!
				return returnError("作废失败!");
			}
			if (StringUtils.isEmpty(otherRevenueDto.getOtherRevenueNo())) {
				// 作废失败!
				return returnError("作废失败!");
			}
			// Check whether
			// this category is enabled for
			// the DEBUG Level.
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("active:" + otherRevenueDto.getActive());
				LOGGER.debug("otherRevenueNo:"
						+ otherRevenueDto.getOtherRevenueNo());
				LOGGER.debug("id:" + otherRevenueDto.getId());
			}
			// 作废小票
			otherRevenueService
					.cancelOtherRevenue(otherRevenueDto, currentInfo);
			return returnSuccess("作废成功");
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			return returnError(e);
		}

	}

	/**
	 * 查询小票记录.
	 * 
	 * @return the string
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-20 上午10:17:02
	 */
	@JSON
	public String queryOtherRevenue() {
		try {
			if (null == vo) {
				return returnError("查询参数无效");
			}
			OtherRevenueDto otherRevenueDto = vo.getDto();

			// 加入数据权限控制
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			otherRevenueDto.setCreateUserCode(currentInfo.getEmpCode());

			// 按日期
			if (StringUtils.equals(otherRevenueDto.getQueryPageTab(),
					SettlementConstants.TAB_QUERY_BY_DATE)) {
				// 开始时间
				Date startTime = otherRevenueDto.getCreateStartTime();
				// 结束时间
				Date endTime = otherRevenueDto.getCreateEndTime();

				if (null == startTime) {
					return returnError("请选择开始时间!");//@218392 zhangyongxue 2015-10-12
				}
				if (null == endTime) {
					return returnError("请选择结束时间!");//@218392 zhangyongxue 2015-10-12
				}
				// 返回两个时间的相差天数
				long diffDays = DateUtils.getTimeDiff(startTime, endTime);
				// 查询日期不能大于
				// 31天
				if (diffDays > SettlementConstants.DATE_LIMIT_DAYS_MONTH) {
					return returnError("查询日期不能大于"
							+ SettlementConstants.DATE_LIMIT_DAYS_MONTH + "天!");
				}
				// 录入结束时间必须大于录入开始时间!
				if (diffDays < 0) {
					return returnError("结束时间必须大于开始时间!");//@218392 zhangyongxue 2015-10-12
				}
				// Check whether this category is
				// enabled for the DEBUG Level.
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("startTime:" + startTime);
					LOGGER.debug("endTime:" + endTime);
				}
				// 返回该日期加1秒
				otherRevenueDto.setCreateEndTime(DateUtils
						.getEndTimeAdd(endTime));
				
				//@author 218392 zhangyongxue 2015-10-12
				if(StringUtils.equals("1", otherRevenueDto.getTimeType())){
					otherRevenueDto.setTimeType("");
				}else{
					otherRevenueDto.setTimeType("void");
				}
				
				// 按小票单号
			} else if (StringUtils.equals(otherRevenueDto.getQueryPageTab(),
					SettlementConstants.TAB_QUERY_BY_OTHERREVENUE_NO)) {
				if (CollectionUtils.isEmpty(otherRevenueDto
						.getOtherRevenueNos())) {
					// 请输入小票单号
					return returnError("请输入小票单号!");
				}
				
			} else {
				return returnError("查询选项卡页有误");
			}

			// 查询小票记录总条数
			OtherRevenueDto dto = otherRevenueService
					.countQueryOtherRevenue(otherRevenueDto);
			if (dto != null && dto.getTotalCount() > 0) {
				// 查询小票记录总条数
				// 分页
				List<OtherRevenueEntity> noteQueryDtoList = otherRevenueService
						.queryOtherRevenue(otherRevenueDto, this.getStart(),
								this.getLimit());
				// 设置
				vo.setList(noteQueryDtoList);

				// 设置总金额
				vo.setTotalAmount(dto.getTotalAmount());

				// 设置
				this.setTotalCount(dto.getTotalCount());
			}
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			return returnError(e);
		}
	}

	/**
	 * 导出小票数据.
	 * 
	 * @return the string
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-27 下午8:43:38
	 */
	public String exprotOtherRevenue() {
		try {
			// vo对象不为空和 查询类型不能为空
			if (vo == null) {
				return returnError("查询参数不能为空！");
			}
			OtherRevenueDto otherRevenueDto = vo.getDto();

			// 按照日期查询
			if (StringUtils.equals(otherRevenueDto.getQueryPageTab(),
					SettlementConstants.TAB_QUERY_BY_DATE)) {

				Date startTime = otherRevenueDto.getCreateStartTime();
				Date endTime = otherRevenueDto.getCreateEndTime();
				// 请选择开始时间!
				if (null == startTime) {
					return returnError("请选择开始时间!");//@218392 zhangyongxue 2015-10-12
				}
				// 请选择结束时间!
				if (null == endTime) {
					return returnError("请选择结束时间!");//@218392 zhangyongxue 2015-10-12
				}
				// 返回两个时间的相差天数
				long diffDays = DateUtils.getTimeDiff(startTime, endTime);
				// 查询日期不能大于31天
				if (diffDays > SettlementConstants.DATE_LIMIT_DAYS_MONTH) {
					return returnError("查询日期不能大于"
							+ SettlementConstants.DATE_LIMIT_DAYS_MONTH + "天!");
				}
				// 录入结束时间必须大于录入开始时间
				if (diffDays < 0) {
					return returnError("结束时间必须大于开始时间!");
				}
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("startTime:" + startTime);
					LOGGER.debug("endTime:" + endTime);
				}
				// 设置结束时间
				otherRevenueDto.setCreateEndTime(DateUtils
						.getEndTimeAdd(endTime));
				
				//设置时间类型
				//@author 218392 zhangyongxue 2015-10-12
				if(StringUtils.equals("1", otherRevenueDto.getTimeType())){
					otherRevenueDto.setTimeType("");
				}else{
					otherRevenueDto.setTimeType("void");
				}

				// 按照小票单号查询
			} else if (StringUtils.equals(otherRevenueDto.getQueryPageTab(),
					SettlementConstants.TAB_QUERY_BY_OTHERREVENUE_NO)) {

				if (CollectionUtils.isEmpty(otherRevenueDto
						.getOtherRevenueNos())) {
					// 小票单号判断
					return returnError("请输入小票单号!");
				}
			} else {
				return returnError("查询选项卡页有误");
			}
			// 查询小票记录
			// 设置开始结束条数
			List<OtherRevenueEntity> noteQueryDtoList = otherRevenueService
					.queryOtherRevenue(otherRevenueDto,
							SettlementConstants.EXPORT_EXCEL_START_NUMBER,
							SettlementConstants.EXPORT_EXCEL_MAX_COUNTS);
			try {
				// 设置Excel名称
				// 设置编码
				this.setExeclName(URLEncoder.encode("小票单据导出结果",
						SettlementConstants.UNICODE_UTF));

			} catch (UnsupportedEncodingException e) {
				// 导出Excel失败
				LOGGER.error(e.toString(), e);
				return returnError("导出Excel失败");
			}
			ExcelExport export = new ExcelExport();
			// 设置每次最多导出
			// 10万条数据
			HSSFWorkbook work = export.exportExcel(
					fillSheetData(noteQueryDtoList),
					SettlementConstants.EXCEL_SHEET_NAME,
					SettlementConstants.EXPORT_EXCEL_MAX_COUNTS);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				work.write(baos);
				inputStream = new ByteArrayInputStream(baos.toByteArray());
			} catch (IOException e) {
				// 导出Excel失败
				LOGGER.error(e.toString(), e);
				return returnError("导出Excel失败");
			} finally {
				if (baos != null) {
					try {
						baos.close();
					} catch (IOException e) {
						// 导出Excel失败
						LOGGER.error(e.toString(), e);
						return returnError("导出Excel失败");
					}
				}
			}// 生成Excel
		} catch (BusinessException e) {
			LOGGER.error(e.toString(), e);
			return returnError("导出参数不能为空！");
		}
		return returnSuccess();
	}

	/**
	 * 填充小票单据Excel.
	 * 
	 * @param noteQueryDtoList
	 *            the note query dto list
	 * @return the sheet data
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-27 下午8:52:32
	 */
	private SheetData fillSheetData(List<OtherRevenueEntity> noteQueryDtoList) {
		SheetData sheetData = new SheetData();
		//@218392 zhangyongxue 2015-10-12 小票优化需求：导出的表头增加了 是否作废  作废时间 两列
		sheetData.setRowHeads(new String[] { "部门名称", "所属子公司","运输性质", "小票单号", "金额",
				"客户编码", "客户名称", "收款方式", "收入类别", "运单单号", "录入人员", "录入时间","是否作废","作废时间","银联交易流水号","备注"});
		//声明字典集合
		List<String> termCodes = new ArrayList<String>();
		termCodes.add(DictionaryConstants.OTHER_REVENUE__PAYMENT_TYPE);
		termCodes.add(DictionaryConstants.BILL_RECEIVABLE__COLLECTION_TYPE);
		//后台获取数据字典对应的数据
		Map<String,Map<String,Object>> map = SettlementUtil.getDataDictionaryByTermsCodes(termCodes);
				
		if (CollectionUtils.isNotEmpty(noteQueryDtoList)) {
			List<List<String>> rowList = new ArrayList<List<String>>();
			for (OtherRevenueEntity entity : noteQueryDtoList) {
				List<String> cellList = new ArrayList<String>();
				cellList.add(entity.getGeneratingOrgName());// 部门名称
				cellList.add(entity.getGeneratingComName());// 所属子公司
				if(StringUtils.isNotBlank(entity.getProductCode())){
					cellList.add(productService.getProductByCache(entity.getProductCode(),new Date()).getName());// 运输性质
				}
				cellList.add(entity.getOtherRevenueNo());// 小票单号
				cellList.add(entity.getAmount() == null ? "" : String
						.valueOf(entity.getAmount()));// 金额
				cellList.add(entity.getCustomerCode());// 客户编码
				cellList.add(entity.getCustomerName());// 客户名称
				cellList.add(SettlementUtil.getDataDictionaryByTermsName(map,
						DictionaryConstants.OTHER_REVENUE__PAYMENT_TYPE,
						entity.getPaymentType()));// 收款方式
				cellList.add(SettlementUtil.getDataDictionaryByTermsName(map,
						DictionaryConstants.BILL_RECEIVABLE__COLLECTION_TYPE,
						entity.getIncomeCategories()));// 收入类别
				cellList.add(entity.getWaybillNo());// 运单单号
				cellList.add(entity.getCreateUserName());// 录入人员
				cellList.add(DateUtils.convert(entity.getCreateTime(),
						DateUtils.DATE_TIME_FORMAT));// 录入时间
				//是否作废 @218392 zhangyongxue 2015-10-12
				if(StringUtils.equals("Y", entity.getIsDisable())){
					cellList.add("是");
				}else{
					cellList.add("否");
				}
		
				//作废时间 @218392 zhangyongxue 2015-10-12
				cellList.add(DateUtils.convert(entity.getDisableTime(), DateUtils.DATE_TIME_FORMAT));	
                cellList.add(entity.getPosBatchNo());// 银联交易流水号
				cellList.add(entity.getNotes());//备注

				rowList.add(cellList);
			}
			sheetData.setRowList(rowList);
		}
		return sheetData;
	}
	//ddw
	public String queryWaybill(){
		if(StringUtils.isNotEmpty(vo.getDto().getWaybillNo().trim())){
			WaybillDto waybill = otherRevenueService.queryWaybillByNo(vo.getDto().getWaybillNo().trim());
			if(null != waybill && null != waybill.getWaybillEntity()){
				if(StringUtils.isNotEmpty(waybill.getWaybillEntity().getProductCode())){
					vo.getDto().setProductCode(waybill.getWaybillEntity().getProductCode());
					vo.getDto().setInvoiceMark(waybill.getActualFreightEntity().getInvoice());
				}
			}
		}
		return returnSuccess();
	}
	
	/**
	 * Gets the vo.
	 * 
	 * @return vo
	 */
	public OtherRevenueVo getVo() {
		return vo;
	}

	/**
	 * Sets the vo.
	 * 
	 * @param vo
	 *            the new vo
	 */
	public void setVo(OtherRevenueVo vo) {
		this.vo = vo;
	}

	/**
	 * Sets the other revenue service.
	 * 
	 * @param otherRevenueService
	 *            the new other revenue service
	 */
	public void setOtherRevenueService(IOtherRevenueService otherRevenueService) {
		this.otherRevenueService = otherRevenueService;
	}

	/**
	 * Gets the execl name.
	 * 
	 * @return the execl name
	 */
	public String getExeclName() {
		return execlName;
	}

	/**
	 * Gets the input stream.
	 * 
	 * @return the input stream
	 */
	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}

	/**
	 * Sets the execl name.
	 * 
	 * @param execlName
	 *            the new execl name
	 */
	public void setExeclName(String execlName) {
		this.execlName = execlName;
	}

	/**
	 * Sets the input stream.
	 * 
	 * @param inputStream
	 *            the new input stream
	 */
	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * Checks if is consecutive.
	 * 
	 * @return true, if is consecutive
	 */
	public boolean isConsecutive() {
		return consecutive;
	}
	
	/**
	 * @GET
	 * @return productService
	 */
	/*public IProductService getProductService() {
		
		 *@get
		 *@ return productService
		 
		return productService;
	}*/

	/**
	 * @SET
	 * @param productService
	 */
	public void setProductService(IProductService productService) {
		/*
		 *@set
		 *@this.productService = productService
		 */
		this.productService = productService;
	}

}
