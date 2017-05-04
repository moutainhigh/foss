package com.deppon.foss.module.settlement.consumer.server.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.framework.server.components.export.excel.SheetData;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillCashCollectionService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillCashCollectionEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillCashCollectionQueryService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCashCollectionQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.vo.BillCashCollectionQueryVo;
import com.deppon.foss.util.NumberUtils;

/**
 * 查询现金收款单Action.
 *
 * @author foss-zhangxiaohui
 * @date Nov 2, 2012 4:11:59 PM
 */
public class BillCashCollectionQueryAction extends AbstractAction {

	/** 实体序列号. */
	private static final long serialVersionUID = -1640841633590112543L;

	/** 声明日志对象. */
	private static final Logger LOGGER = LoggerFactory.getLogger(BillCashCollectionQueryAction.class);

	/** 现金收款单VO. */
	private BillCashCollectionQueryVo billCashCollectionQueryVO;

	/** 现金收款单查询Service. */
	private IBillCashCollectionQueryService billCashCollectionQueryService;

	/** 现金收款单Entity 查询Service. */
	private IBillCashCollectionService billCashCollectionService;
	
	/** 导出excel名称. */
	private static final String EXPORT_NAME = "现金收款单";
	
	
	
	/** 导出excel的输入流. */
	private ByteArrayInputStream inputStream;

	/** Excel文件名. */
	private String excelName;
	
	/**
	 * 根据产品CODE找对应的名称
	 */
	IProductService  productService;


	/**
	 * 通过单号查询现金收款单.
	 *
	 * @return the string
	 * @author foss-zhangxiaohui
	 * @date Nov 2, 2012 4:17:38 PM
	 */
	@JSON
	public String queryCashCollectionBillByWayBillNo() {
		try {
			
			// 判断页面传过来的运单号数组是否有值
			if (null == billCashCollectionQueryVO.getWayBillNosArray()|| billCashCollectionQueryVO.getWayBillNosArray().length == 0) {
				// 如果没有值则抛出异常
				throw new SettlementException("传入的运单单号不能为空");
			}
			
			//现金收款单查询dao			
			BillCashCollectionQueryDto billCashDto =new BillCashCollectionQueryDto();
		
			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();
			// 获取当前登录用户下属的所有部门Set
			Set<String> userOrgCodes = cInfo.getUser().getOrgids();
			// 将当前登录用户下属部门Set转化为List
			List<String> userOrgCodesList = new ArrayList<String>(userOrgCodes);
			
			//当前用户所拥有权限			
			if(userOrgCodesList.size()>0){
				billCashDto.setUserOrgCodesList(userOrgCodesList);
			}
			
			billCashDto.setWaybillNos(Arrays.asList(billCashCollectionQueryVO.getWayBillNosArray()));

			// 通过遍历数组生成的List查现金收款单信息
			List<BillCashCollectionEntity> billCashCollectionBillNoList = billCashCollectionQueryService.queryBillCashCollectionByWaybillNO(billCashDto,cInfo);

			
			//计算总金额和总条数，返回到界面
			if (CollectionUtils.isNotEmpty(billCashCollectionBillNoList)) {
				// 现金收款单数据赋值给页面上的List
				billCashCollectionQueryVO.setBillCashCollectionList(billCashCollectionBillNoList);
				// 设置查询出来的数据的数量
				billCashCollectionQueryVO.setTotalRecordsInDB(billCashCollectionBillNoList.size());
				// 声明合计总金额
				BigDecimal totalAmount = null;
				// 设置查询出来的总记录的总金额
				for (int i = 0; i < billCashCollectionBillNoList.size(); i++) {
					// 设置分页的总数据量
					totalAmount = NumberUtils.add(totalAmount,
							billCashCollectionBillNoList.get(i).getAmount());
				}
				// 赋值传回页面
				billCashCollectionQueryVO.setTotalAmount(totalAmount);
			} else {
				// 这里不做处理
			}
		}
		// 捕获异常
		catch (BusinessException e) {
			// 打印错误信息
			LOGGER.error("按运单号查询现金收款单" + e.getMessage(), e);
			// 返回错误信息
			return returnError("按运单号查询现金收款单" + e);
		}
		// 返回结果
		return returnSuccess();
	}

	/**
	 * 通过来源单号查询现金收款单.
	 *
	 * @return the string
	 * @author foss-zhangxiaohui
	 * @date Jan 22, 2013 2:43:06 PM
	 */
	@JSON
	public String queryCashCollectionBillBySourceBillNo() {
		try {
			// 判断页面传过来的运单号数组是否有值
			if (null == billCashCollectionQueryVO.getSourceBillNosArray()|| billCashCollectionQueryVO.getSourceBillNosArray().length == 0) {
				// 如果没有值则抛出异常
				throw new SettlementException("传入的来源单号不能为空");
			}
			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();
			// 获取当前登录用户下属的所有部门Set
			Set<String> userOrgCodes = cInfo.getUser().getOrgids();
			// 将当前登录用户下属部门Set转化为List
			List<String> userOrgCodesList = new ArrayList<String>(userOrgCodes);
			// 声明运单号List,用来做参数
			List<String> sourceBillNoList = new ArrayList<String>();
			// 遍历运单单号数组
			for (int i = 0; i < billCashCollectionQueryVO.getSourceBillNosArray().length; i++) {
				// 把数组遍历出来添加到List里面
				sourceBillNoList.add(billCashCollectionQueryVO.getSourceBillNosArray()[i]);
			}
			// 通过遍历数组生成的List查现金收款单信息
			List<BillCashCollectionEntity> billCashCollectionBillNoList = billCashCollectionService.queryBySourceBillNOsAndOrgCodes(sourceBillNoList,null,userOrgCodesList,null,cInfo);

			// 对查询结果做非空验证
			if (CollectionUtils.isNotEmpty(billCashCollectionBillNoList)) {
				// 现金收款单数据赋值给页面上的List
				billCashCollectionQueryVO.setBillCashCollectionList(billCashCollectionBillNoList);
				// 设置查询出来的数据的数量
				billCashCollectionQueryVO.setTotalRecordsInDB(billCashCollectionBillNoList.size());
				// 声明合计总金额
				BigDecimal totalAmount = null;
				// 设置查询出来的总记录的总金额
				for (int i = 0; i < billCashCollectionBillNoList.size(); i++) {
					// 设置分页的总数据量
					totalAmount = NumberUtils.add(totalAmount,
							billCashCollectionBillNoList.get(i).getAmount());
				}
				// 赋值传回页面
				billCashCollectionQueryVO.setTotalAmount(totalAmount);
			} else {
				// 这里不做处理
			}
		}
		// 捕获异常
		catch (BusinessException e) {
			// 打印错误信息
			LOGGER.error("按来源单号查询现金收款单" + e.getMessage(), e);
			// 返回错误信息
			return returnError("按来源单号查询现金收款单" + e);
		}
		// 返回结果
		return returnSuccess();
	}
	
	/**
	 * 通过来源单号查询现金收款单.
	 *
	 * @return the string
	 * @author foss-zhangxiaohui
	 * @date Jan 22, 2013 2:43:06 PM
	 */
	@JSON
	public String queryCashCollectionBillByBatchNo(){
		try {
			// 判断页面传过来的运单号数组是否有值
			if (null == billCashCollectionQueryVO.getBatchNos()|| billCashCollectionQueryVO.getBatchNos().size() == 0) {
				// 如果没有值则抛出异常
				throw new SettlementException("传入的银联交易流水号不能为空");
			}
			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();

			// 通过遍历数组生成的List查现金收款单信息
			List<BillCashCollectionEntity> billCashCollectionBillNoList = billCashCollectionService.queryByBatchNos(billCashCollectionQueryVO.getBatchNos(),null, cInfo);
			// 对查询结果做非空验证
			if (CollectionUtils.isNotEmpty(billCashCollectionBillNoList)) {
				// 现金收款单数据赋值给页面上的List
				billCashCollectionQueryVO.setBillCashCollectionList(billCashCollectionBillNoList);
				// 设置查询出来的数据的数量
				billCashCollectionQueryVO.setTotalRecordsInDB(billCashCollectionBillNoList.size());
				// 声明合计总金额
				BigDecimal totalAmount = null;
				// 设置查询出来的总记录的总金额
				for (int i = 0; i < billCashCollectionBillNoList.size(); i++) {
					// 设置分页的总数据量
					totalAmount = NumberUtils.add(totalAmount,
							billCashCollectionBillNoList.get(i).getAmount());
				}
				// 赋值传回页面
				billCashCollectionQueryVO.setTotalAmount(totalAmount);
			} else {
				// 这里不做处理
			}
		}
		// 捕获异常
		catch (BusinessException e) {
			// 打印错误信息
			LOGGER.error("按银联交易流水号查询现金收款单" + e.getMessage(), e);
			// 返回错误信息
			return returnError("按银联交易流水号查询现金收款单" + e);
		}
		// 返回结果
		return returnSuccess();
	}
	
	
	/**
	 * 通过Dto查询应收单.
	 *
	 * @return the string
	 * @author foss-zhangxiaohui
	 * @date Oct 30, 2012 4:11:29 PM
	 */
	@JSON
	public String queryCashCollectionBillByDto() {
		try {		
			//校验输入日期
			this.validateDate();	
			// 声明Dto做对象传参
			BillCashCollectionQueryDto billCashCollectionQueryDto = billCashCollectionQueryVO.getDto();
			// 给结束日期加一天
			Date endDate = DateUtils.addDays(billCashCollectionQueryDto.getEndDate(), 1);
			billCashCollectionQueryDto.setEndDate(endDate);
			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();
			// 声明一个Dto来存放合计记录条数 总金额
			BillCashCollectionQueryDto dto = null;
			// 日期类型为业务日期执行操作
			if (SettlementConstants.TAB_DATE_TYPE_FOR_BUSINESS.equals(billCashCollectionQueryDto.getSelectDateType())) {
				// 查询结果赋值给临时的Dto对象
				dto = billCashCollectionQueryService.queryTotalAmountByBusinessDate(billCashCollectionQueryDto,cInfo);
			}
			// 日期类型为记账日期时执行操作
			else if (SettlementConstants.TAB_DATE_TYPE_FOR_ACCOUNT
					.equals(billCashCollectionQueryDto.getSelectDateType())) {
				// 查询结果赋值给临时的Dto对象
				dto = billCashCollectionQueryService
						.queryTotalAmountByAccountDate(billCashCollectionQueryDto,cInfo);
			}
			// 如果日期类型为异常的常量则抛出异常
			else {
				throw new SettlementException("传入的日期类型异常");
			}
			// 查询数据库中记录总条数，总金额---判断List是否为空，size是否大于0
			if (null != dto && dto.getTotalRecordsInDB() > 0) {
				// 如果不为空，设置数据总条数
				setTotalCount(Long.valueOf(dto.getTotalRecordsInDB()));
				// 查询数据库中记录总条数
				billCashCollectionQueryVO.setTotalRecordsInDB(dto.getTotalRecordsInDB());
				// 设置查询出来的记录总金额
				billCashCollectionQueryVO.setTotalAmount(dto.getTotalAmount());
				List<BillCashCollectionEntity> billCashCollectionList = null;
				// 日期类型为业务日期执行操作
				if (SettlementConstants.TAB_DATE_TYPE_FOR_BUSINESS
						.equals(billCashCollectionQueryDto.getSelectDateType())) {
					// 查询结果赋值
					billCashCollectionList = billCashCollectionQueryService
							.queryBillCashCollectionByBusinessDate(getStart(),
									getLimit(), billCashCollectionQueryDto,cInfo);
				}
				// 日期类型为记账日期时执行操作
				else if (SettlementConstants.TAB_DATE_TYPE_FOR_ACCOUNT
						.equals(billCashCollectionQueryDto.getSelectDateType())) {

					// 查询结果赋值
					billCashCollectionList = billCashCollectionQueryService
							.queryBillCashCollectionByAccountDate(getStart(),
									getLimit(), billCashCollectionQueryDto,cInfo);
				}
				// 如果日期类型为异常的常量则抛出异常
				else {
					throw new SettlementException("传入的日期类型异常");
				}

				// 如果查出来的结果集不为空则赋值给页面上的List
				if (CollectionUtils.isNotEmpty(billCashCollectionList)) {
					billCashCollectionQueryVO.setBillCashCollectionList(billCashCollectionList);
				}
			}
		}
		// 捕获异常
		catch (BusinessException e) {
			// 打印错误信息
			LOGGER.error("按日期查询现金收款单" + e.getMessage(), e);
			// 返回错误信息
			return returnError("按日期查询现金收款单" + e);
		}
		// 返回结果
		return returnSuccess();
	}

	/**
	 * 校验输入日期.
	 *
	 * @author foss-zhangxiaohui
	 * @date Dec 25, 2012 1:03:29 PM
	 */
	private void validateDate(){
		// 查询的参数非空校验
		if (null == billCashCollectionQueryVO
				|| null == billCashCollectionQueryVO.getDto()) {

			// 查询参数Dto为空则抛出异常
			throw new SettlementException("查询条件不能为空");
		}
		// 开始日期非空校验
		if (null == billCashCollectionQueryVO.getDto().getStartDate()) {
			// 开始日期为空则抛出异常
			throw new SettlementException("开始日期不能为空");
		}
		// 结束日期非空校验
		if (null == billCashCollectionQueryVO.getDto().getEndDate()) {
			// 结束日期为空则抛出异常
			throw new SettlementException("结束日期不能为空");
		}
		// 判断开始日期是否小于结束日期
		if (billCashCollectionQueryVO.getDto().getStartDate() != null
				&& billCashCollectionQueryVO.getDto().getEndDate() != null) {
			Date startDate = DateUtils.truncate(billCashCollectionQueryVO
					.getDto().getStartDate(), Calendar.SECOND);
			Date endDate = DateUtils.truncate(billCashCollectionQueryVO
					.getDto().getEndDate(), Calendar.SECOND);
			if (startDate.after(endDate)) {
				throw new SettlementException("开始日期大于结束日期！");
			}
		}
	}

	/**
	 * 导出现金收款单.
	 *
	 * @return the string
	 * @author foss-zhangxiaohui
	 * @date Dec 28, 2012 3:16:58 PM
	 */
	public String exportBillCashCollection() {
		// 1)查询
		try {
			// 存放由查询出来出来的数据
			List<BillCashCollectionEntity> billCashCollectionList = new ArrayList<BillCashCollectionEntity>();
			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();
			// 按运单号查询
			if (SettlementConstants.TAB_QUERY_BY_WAYBILL_NO
					.equals(billCashCollectionQueryVO.getDto().getQueryTab())) {
				
				billCashCollectionList = validaBill(cInfo);
			}
			// 按日期查询
			else if (SettlementConstants.TAB_QUERY_BY_DATE.equals(billCashCollectionQueryVO.getDto().getQueryTab())) {
				billCashCollectionList = validaEntity(billCashCollectionList,
						cInfo);
			}else if(SettlementConstants.TAB_QUERY_BY_LANDSTOWAGE_NO.equals(billCashCollectionQueryVO.getDto().getQueryTab())){
				// 判断页面传过来的运单号数组是否有值
				if (null == billCashCollectionQueryVO.getBatchNos()|| billCashCollectionQueryVO.getBatchNos().size() == 0) {
					// 如果没有值则抛出异常
					throw new SettlementException("传入的银联交易流水号不能为空");
				}
				
				//现金收款单查询dao			
				//BillCashCollectionQueryDto billCashDto =new BillCashCollectionQueryDto();
				
				// 通过遍历数组生成的List查现金收款单信息
				billCashCollectionList = billCashCollectionService.queryByBatchNos(billCashCollectionQueryVO.getBatchNos(), null, cInfo);
			}else{
				billCashCollectionList = validaBillCashCollection(
						billCashCollectionList, cInfo);				
				
			}
			// 如果查出来的结果集不为空则赋值给页面上的List
			if (CollectionUtils.isNotEmpty(billCashCollectionList)) {
				billCashCollectionQueryVO.setBillCashCollectionList(billCashCollectionList);
			}
		}
		//捕获异常
		 catch (BusinessException e) {
			 //打印错误信息
			LOGGER.error("现金收款单导出" + e.getMessage(), e);
			//返回错误信息
			return returnError("现金收款单导出" + e.getMessage());
		}
		// 2)导出
		ByteArrayOutputStream os = null;
		try {
			os = validaException();
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
			return returnError("导出Excel失败");
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					new ByteArrayOutputStream();
					return returnError("导出Excel失败");
				}
			}
		}
		//返回结果
		return returnSuccess();
	}

	private ByteArrayOutputStream validaException()
			throws UnsupportedEncodingException, IOException {
		ByteArrayOutputStream os;
		// 导出Excel
		ExcelExport export = new ExcelExport();
		// 将要导出东西封装转化成Excel导出要用的List<List<String>> 格式
		List<List<String>> rowList = convertListFormEntity(billCashCollectionQueryVO.getBillCashCollectionList(),billCashCollectionQueryVO.getDto().getArrayColumns());
		// 获取导出数据
		SheetData data = new SheetData();
		// 设置导出列头
		data.setRowHeads(billCashCollectionQueryVO.getDto().getArrayColumnNames());
		data.setRowList(rowList);
		// 设置每次最多导出10万条数据
		HSSFWorkbook work = export.exportExcel(data,
				SettlementConstants.EXCEL_SHEET_NAME,
				SettlementConstants.EXPORT_EXCEL_MAX_COUNTS);
		//设置导出的Excel文件的名字
		excelName = new String(
				(EXPORT_NAME).getBytes(SettlementConstants.UNICODE_GBK),
				SettlementConstants.UNICODE_ISO);
		os = new ByteArrayOutputStream();
		work.write(os);
		inputStream = new ByteArrayInputStream(os.toByteArray());
		return os;
	}

	private List<BillCashCollectionEntity> validaBillCashCollection(
			List<BillCashCollectionEntity> billCashCollectionList,
			CurrentInfo cInfo) {
		// 判断页面传过来的来源单号数组是否有值
		if (null == billCashCollectionQueryVO.getSourceBillNosArray()|| billCashCollectionQueryVO.getSourceBillNosArray().length == 0) {
			// 如果没有值则抛出异常
			throw new SettlementException("传入的来源单号不能为空");
		}
		// 获取当前登录用户下属的所有部门Set
		Set<String> userOrgCodes = cInfo.getUser().getOrgids();
		// 将当前登录用户下属部门Set转化为List
		List<String> userOrgCodesList = new ArrayList<String>(userOrgCodes);
		// 声明运单号的List
		List<String> sourceBillNoList = new ArrayList<String>();
		// 遍历运单号数组，添加单号到运单List做查询
		for (int i = 0; i < billCashCollectionQueryVO.getSourceBillNosArray().length; i++) {
			// 如果不是YS开头则放在按照运单单号查询的List
			sourceBillNoList.add(billCashCollectionQueryVO.getSourceBillNosArray()[i]);
		}
		// 判断按照运单单号查询的List是否为空
		if (CollectionUtils.isNotEmpty(sourceBillNoList)) {
			// 通过遍历数组生成的List查现金收款单信息
				billCashCollectionList = billCashCollectionService.queryBySourceBillNOsAndOrgCodes(sourceBillNoList,null,userOrgCodesList,null,cInfo);
			}
		return billCashCollectionList;
	}

	private List<BillCashCollectionEntity> validaEntity(
			List<BillCashCollectionEntity> billCashCollectionList,
			CurrentInfo cInfo) {
		//校验输入日期
		this.validateDate();	
		Date endDate = DateUtils.addDays(billCashCollectionQueryVO.getDto().getEndDate(), 1);
		billCashCollectionQueryVO.getDto().setEndDate(endDate);

		// 日期类型为业务日期执行操作
		if (SettlementConstants.TAB_DATE_TYPE_FOR_BUSINESS
				.equals(billCashCollectionQueryVO.getDto().getSelectDateType())) {

			// 赋值给声明的对象以返回页面显示
			billCashCollectionList = billCashCollectionQueryService.queryBillCashCollectionByBusinessDate(
							0,SettlementConstants.EXPORT_EXCEL_MAX_COUNTS,billCashCollectionQueryVO.getDto(),cInfo);
		}
		// 日期类型为记账日期时执行操作
		else if (SettlementConstants.TAB_DATE_TYPE_FOR_ACCOUNT
				.equals(billCashCollectionQueryVO.getDto().getSelectDateType())) {
			// 赋值给声明的对象以返回页面显示
			billCashCollectionList = billCashCollectionQueryService.queryBillCashCollectionByAccountDate(
					0,SettlementConstants.EXPORT_EXCEL_MAX_COUNTS,billCashCollectionQueryVO.getDto(),cInfo);
		}
//按银联交易流水号查询
		return billCashCollectionList;
	}

	private List<BillCashCollectionEntity> validaBill(CurrentInfo cInfo) {
		List<BillCashCollectionEntity> billCashCollectionList;
		// 判断页面传过来的运单号数组是否有值
		if (null == billCashCollectionQueryVO.getWayBillNosArray()|| billCashCollectionQueryVO.getWayBillNosArray().length == 0) {
			// 如果没有值则抛出异常
			throw new SettlementException("传入的运单单号不能为空");
		}
		
		//现金收款单查询dao			
		BillCashCollectionQueryDto billCashDto =new BillCashCollectionQueryDto();
		// 获取当前登录用户下属的所有部门Set
		Set<String> userOrgCodes = cInfo.getUser().getOrgids();
		// 将当前登录用户下属部门Set转化为List
		List<String> userOrgCodesList = new ArrayList<String>(userOrgCodes);
		
		//当前用户所拥有权限			
		if(userOrgCodesList.size()>0){
			billCashDto.setUserOrgCodesList(userOrgCodesList);
		}
		
		billCashDto.setWaybillNos(Arrays.asList(billCashCollectionQueryVO.getWayBillNosArray()));
		// 通过遍历数组生成的List查现金收款单信息
		billCashCollectionList = billCashCollectionQueryService.queryBillCashCollectionByWaybillNO(billCashDto,cInfo);
		return billCashCollectionList;
	}
	
	/**
	 * list的实体转化成list<list<String>
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-11-21 下午2:25:57
	 */
	private List<List<String>> convertListFormEntity(
			List<BillCashCollectionEntity> list, String[] header) {
		// 声明sheetList
		List<List<String>> sheetList = new ArrayList<List<String>>();
		
		//声明字典集合
		List<String> termCodes = new ArrayList<String>();
		termCodes.add(DictionaryConstants.BILL_RECEIVABLE__COLLECTION_TYPE);
		termCodes.add(DictionaryConstants.SETTLEMENT__PAYMENT_TYPE);
		termCodes.add(DictionaryConstants.BILL_CASH_COLLECTION__STATUS);
		//后台获取数据字典对应的数据
		Map<String,Map<String,Object>> map = SettlementUtil.getDataDictionaryByTermsCodes(termCodes);
		
		// 循环进行封装
		for (BillCashCollectionEntity entity : list) {
			validaList(header, sheetList, map, entity);
		}
		return sheetList;
	}

	private void validaList(String[] header, List<List<String>> sheetList,
			Map<String, Map<String, Object>> map,
			BillCashCollectionEntity entity) {
		// 声明一行的rowList
		List<String> rowList = new ArrayList<String>();
		for (String columnName : header) {
			// 通过名称获取field
			Field field = ReflectionUtils.findField(
					BillCashCollectionEntity.class, columnName);
			if (field != null) {
				validaCollection(map, entity, rowList, columnName, field);
			}
		}
		sheetList.add(rowList);
	}

	private void validaCollection(Map<String, Map<String, Object>> map,
			BillCashCollectionEntity entity, List<String> rowList,
			String columnName, Field field) {
		// 通过传入字段获取值
		ReflectionUtils.makeAccessible(field);
		Object fieldValue = ReflectionUtils.getField(field, entity);
		// 如果为日期，需要进行格式化
		if (Date.class.equals(field.getType())&& fieldValue != null) {
			//日期转化
			fieldValue = com.deppon.foss.util.DateUtils.convert((Date) fieldValue,"yyyy-MM-dd HH:mm:ss");
		}
		// 将字段封装到list中
		if (fieldValue != null) {
			validaBillCash(map, entity, rowList, columnName,
					fieldValue);
			
		} else {
			rowList.add(null);
		}
	}

	private void validaBillCash(Map<String, Map<String, Object>> map,
			BillCashCollectionEntity entity, List<String> rowList,
			String columnName, Object fieldValue) {
		// 如果为单据子类型，则需要转化
		if (columnName.equals("collectionType")) {
			//来源单据类别转化
			fieldValue = SettlementUtil.getDataDictionaryByTermsName(map
					,DictionaryConstants.BILL_RECEIVABLE__COLLECTION_TYPE,fieldValue.toString());
			//如果为单据状态，则需要转化
		}else if (columnName.equals("status")) {
			//数据字典转化
			fieldValue = SettlementUtil.getDataDictionaryByTermsName(map,
					DictionaryConstants.BILL_CASH_COLLECTION__STATUS, fieldValue.toString());
		}//发票标记
		else if(columnName.equals("invoiceMark")){
			if(fieldValue.toString().equals(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_ONE)){
				fieldValue="01-运输专票11%";
			}else if(fieldValue.toString().equals(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_TWO)){
				fieldValue="02-非运输专票";
			}else{
				fieldValue="";
			}
		}
		else if (columnName.equals("billType")) {
			//数据字典转化
//							fieldValue = SettlementUtil.getDataDictionaryByTermsName(map,
//									DictionaryConstants.BILL_CASH_COLLECTION__BILL_TYPE,
//									fieldValue.toString());
			
			fieldValue=EXPORT_NAME;
		}else if(columnName.equals("sourceBillType")){
			fieldValue = validaValue(fieldValue);
		}
		else if (columnName.equals("paymentType")) {
			//数据字典转化
			fieldValue = SettlementUtil.getDataDictionaryByTermsName(map,
					DictionaryConstants.SETTLEMENT__PAYMENT_TYPE, fieldValue.toString());
		}else if(StringUtils.isNotEmpty(entity.getBillType()) && columnName.equals(entity.getBillType())){
			fieldValue = EXPORT_NAME;
		}else if(columnName.equals("productCode")){
			fieldValue = productService.getProductByCache(fieldValue.toString(),new Date()).getName();
		}
		if(fieldValue!=null){
			//设置属性值
			rowList.add(fieldValue.toString());
		}else {
			rowList.add(null);
		}
	}

	private Object validaValue(Object fieldValue) {
		//数据字典转化
		if(fieldValue.equals("W")){
			fieldValue="运单";
		}else if(fieldValue.equals("R")){
			fieldValue="小票";
		}
		return fieldValue;
	}
	
	/**
	 * Gets the bill cash collection query vo.
	 *
	 * @return billCashCollectionQueryVO
	 */
	public BillCashCollectionQueryVo getBillCashCollectionQueryVO() {
		return billCashCollectionQueryVO;
	}

	/**
	 * Sets the bill cash collection query vo.
	 *
	 * @param billCashCollectionQueryVO the new bill cash collection query vo
	 */
	public void setBillCashCollectionQueryVO(
			BillCashCollectionQueryVo billCashCollectionQueryVO) {
		this.billCashCollectionQueryVO = billCashCollectionQueryVO;
	}
	
	/**
	 * Sets the bill cash collection query service.
	 *
	 * @param billCashCollectionQueryService the new bill cash collection query service
	 */
	public void setBillCashCollectionQueryService(
			IBillCashCollectionQueryService billCashCollectionQueryService) {
		this.billCashCollectionQueryService = billCashCollectionQueryService;
	}

	/**
	 * Sets the bill cash collection service.
	 *
	 * @param billCashCollectionService the new bill cash collection service
	 */
	public void setBillCashCollectionService(
			IBillCashCollectionService billCashCollectionService) {
		this.billCashCollectionService = billCashCollectionService;
	}

	/**
	 * Gets the input stream.
	 *
	 * @return inputStream
	 */
	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}

	/**
	 * Sets the input stream.
	 *
	 * @param inputStream the new input stream
	 */
	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * Gets the excel name.
	 *
	 * @return excelName
	 */
	public String getExcelName() {
		return excelName;
	}

	/**
	 * Sets the excel name.
	 *
	 * @param excelName the new excel name
	 */
	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}

	/**
	 * @GET
	 * @return productService
	 */
	public IProductService getProductService() {
		/*
		 *@get
		 *@ return productService
		 */
		return productService;
	}

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
