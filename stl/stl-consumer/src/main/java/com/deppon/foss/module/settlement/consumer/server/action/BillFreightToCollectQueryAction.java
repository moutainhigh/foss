package com.deppon.foss.module.settlement.consumer.server.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.framework.server.components.export.excel.SheetData;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillFreightToCollectQueryService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillFreightToCollectQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillFreightToCollectResultDto;
import com.deppon.foss.module.settlement.consumer.api.shared.vo.BillFreightToCollectVo;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;

/**
 * 到付清查Action.
 *
 * @author foss-zhangxiaohui
 * @date Oct 29, 2012 4:25:58 PM
 */

public class BillFreightToCollectQueryAction extends AbstractAction {

	/** 实体序列号. */
	private static final long serialVersionUID = 7509248698817484467L;

	/** 声明日志对象. */
	private static final Logger LOGGER = LoggerFactory.getLogger(BillFreightToCollectQueryAction.class);

	/** 到付清查VO. */
	private BillFreightToCollectVo billFreightToCollectVO;

	/** 到付清查Service. */
	private IBillFreightToCollectQueryService billFreightToCollectQueryService;
	
	/**
	 * 库存状态Service
	 */
    //private IStockService stockService;

	/** execl文件流. */
	private ByteArrayInputStream inputStream;

	/** execl文件名. */
	private String execlName;

	/** 异常返回标示. */
	private String resultMark;

	/** 汇款导出execl文件名. */
	private static final String COD_EXPORT_EXECL_NAME = "到付款清查明细表";
	
	/**
	 * 根据产品CODE找对应的名称
	 */
	private IProductService  productService;
	
	/**
	 * 通过日期到付清查.
	 *
	 * @return the string
	 * @author foss-zhangxiaohui
	 * @date Oct 30, 2012 4:03:29 PM
	 */
	@JSON
	public String queryFreightToCollectBillByDate() {
		try {
			
			//校验输入的参数
			this.validateDate();
			
			
			// 声明Dto对象存放查询出来的合计记录总条数 总金额 核销总金额 和 未核销总金额、声明一个变量去存放页面所选择的日期类型
			BillFreightToCollectQueryDto dto = null;
			// 声明返回页面显示的对象实体List
			List<BillFreightToCollectResultDto> billReceivableBillNoList = null;
			
			
			// 获取当前登录用户信息、 获取参数并赋值给Dto、 给结束日期加一天、
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();
			BillFreightToCollectQueryDto billFreightToCollectDto = billFreightToCollectVO.getDto();
			Date endDate = DateUtils.addDays(billFreightToCollectDto.getEndDate(), 1);
			billFreightToCollectDto.setEndDate(endDate);
			String dateType = billFreightToCollectVO.getDto().getSelectDateType();//查询日期类型
			
			
			// 日期类型为业务日期和记账日期时进行查询操作，否则其它为日期类型为异常的常量则抛出异常
			if (SettlementConstants.TAB_DATE_TYPE_FOR_BUSINESS.equals(dateType)) {//赋值查询结果给临时声明的Dto对象
				dto = billFreightToCollectQueryService.queryTotalAmountByBusinessDate(billFreightToCollectDto,cInfo);
			}else if (SettlementConstants.TAB_DATE_TYPE_FOR_ACCOUNT.equals(dateType)) {
				dto = billFreightToCollectQueryService.queryTotalAmountByAccountDate(billFreightToCollectDto,cInfo);// 赋值查询结果给临时声明的Dto对象
			}else {
				throw new SettlementException("按日期查询操作，传入的查询日期类型错误！");
			}
			
			// 查询数据库中记录总条数是否大于0，如果大于0则继续查询明细处理
			if (null != dto && dto.getTotalRecordsInDB() > 0) {
			setTotalCount(Long.valueOf(dto.getTotalRecordsInDB()));//应收单分页数据条数
			billFreightToCollectVO.setTotalRecordsInDB(dto.getTotalRecordsInDB());//总记录返回的总条数(总记录是指页面所显示的记录条数)
			
			
			// 总记录总金额(总记录是指页面所显示的记录条数)、 总记录的核销总金额(总记录是指页面所显示的记录条数)、 总记录的未核销总金额(总记录是指页面所显示的记录条数)
			billFreightToCollectVO.setTotalAmount(dto.getTotalAmount());
			billFreightToCollectVO.setTotalVerifyAmount(dto.getTotalVerifyAmount());
			billFreightToCollectVO.setTotalUnverifyAmount(dto.getTotalUnverifyAmount());
			
			
			// 日期类型为业务日期执行操作、日期类型为记账日期时执行操作、 如果日期类型为异常的常量则抛出异常
			if (SettlementConstants.TAB_DATE_TYPE_FOR_BUSINESS.equals(dateType)) {
				billReceivableBillNoList = billFreightToCollectQueryService.queryBillByBusinessDate(getStart(), getLimit(),billFreightToCollectDto,cInfo);
			}else if (SettlementConstants.TAB_DATE_TYPE_FOR_ACCOUNT.equals(dateType)) {
				billReceivableBillNoList = billFreightToCollectQueryService.queryBillByAccountDate(getStart(), getLimit(),billFreightToCollectDto,cInfo);
			}else {
				throw new SettlementException("传入的日期类型异常");
			}
			
			
			// 返回查询结果用于 设置查询结果返回页面显示的
			if (CollectionUtils.isNotEmpty(billReceivableBillNoList)) {
				billFreightToCollectVO.setBillFreightToCollectList(billReceivableBillNoList);
			} else {
				// 这里不做处理
				}
			}
		}
		
		
		// 捕获异常
		catch (BusinessException e) {
			// 打印错误信息
			LOGGER.error("到付清查查询" + e.getMessage(), e);
			// 返回错误信息
			return returnError("到付清查查询" + e);
		}
		
		
		// 返回结果
		return returnSuccess();
	}

	/**
	 * 通过日期导出到付清查.
	 *
	 * @return the string
	 * @author foss-zhangxiaohui
	 * @date Oct 30, 2012 4:03:29 PM
	 */
	public String queryFreightToCollectBillExportExecl() {
		ByteArrayOutputStream baos = null;
		try {
			//校验输入的参数
			this.validateDate();
			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();
			// 获取参数并赋值给Dto
			BillFreightToCollectQueryDto billFreightToCollectDto = billFreightToCollectVO.getDto();
			// 给结束日期加一天
			Date endDate = DateUtils.addDays(billFreightToCollectDto.getEndDate(), 1);
			billFreightToCollectDto.setEndDate(endDate);
			// 声明一个变量去存放页面所选择的日期类型
			String dateType = billFreightToCollectVO.getDto().getSelectDateType();			
			// 声明返回页面显示的对象实体List
			List<BillFreightToCollectResultDto> billReceivableBillNoList = null;
			
	
			// 日期类型为业务日期执行操作
			if (SettlementConstants.TAB_DATE_TYPE_FOR_BUSINESS.equals(dateType)) {
				// 赋值查询结果
				billReceivableBillNoList = billFreightToCollectQueryService.queryBillByBusinessDate(0,SettlementConstants.EXPORT_EXCEL_MAX_COUNTS,billFreightToCollectDto,cInfo);
			}
			// 日期类型为记账日期时执行操作
			else if (SettlementConstants.TAB_DATE_TYPE_FOR_ACCOUNT.equals(dateType)) {
				// 赋值查询结果
				billReceivableBillNoList = billFreightToCollectQueryService.queryBillByAccountDate(0,SettlementConstants.EXPORT_EXCEL_MAX_COUNTS,billFreightToCollectDto,cInfo);
			}
			// 如果日期类型为异常的常量则抛出异常
			else {
				throw new SettlementException("传入的日期类型异常");
			}
			// 查询出来的结果不为空的话传回页面
			if (CollectionUtils.isNotEmpty(billReceivableBillNoList)) {
				this.setExeclName(new String((COD_EXPORT_EXECL_NAME)
						.getBytes(SettlementConstants.UNICODE_GBK),
						SettlementConstants.UNICODE_ISO));
				//声明一个Excel导出对象
				ExcelExport export = new ExcelExport();
				//声明一个工作簿对象
				HSSFWorkbook work = export.exportExcel(getSheetDataByBillReceivableEntityList(billReceivableBillNoList),
								COD_EXPORT_EXECL_NAME,
								SettlementConstants.EXPORT_MAX_COUNT);
				//给字节数组流缓冲区申请内存空间
				baos = new ByteArrayOutputStream();
				//把内容写入输出流
				work.write(baos);
				//赋值给输入流
				inputStream = new ByteArrayInputStream(baos.toByteArray());
			} else {
				//抛出错误信息
				throw new SettlementException("查询的结果为空");
			}	

			//返回结果
			return returnSuccess();
		} 
		//捕获业务异常
		catch (BusinessException e) {
			//抛出异常
			return markLogReturnError(e, "到付清查报表导出BusinessException" + e.getErrorCode());
		} 
		//捕获IO异常
		catch (IOException e) {
			//抛出异常
			return markLogReturnError(e, "到付清查报表导出IOException" + e.getMessage());
		}
		//捕获非IO 非Business异常
		catch (Exception e) {
			//抛出异常信息
			return markLogReturnError(e, "到付清查报表导出" + e.getMessage());
		} finally {
			try {
				// 判断输出流是否为空
				if (baos != null) {
					// 不为空则关掉资源
					baos.close();
				}
			}
			// 捕获异常
			catch (Exception e) {
				// 返回错误信息
				return markLogReturnError(e, "到付清查查询导出" + e.getMessage());
			}
		}
	}
	
	/**
	 * 校验输入日期.
	 * 
	 * @author foss-zhangxiaohui
	 * @date Oct 30, 2012 4:03:29 PM
	 */
	private void validateDate() {
		// 查询单号的参数不能为空
		if (null == billFreightToCollectVO
				|| null == billFreightToCollectVO.getDto()) {
			// Dto对象为空则抛出异常
			throw new SettlementException("查询条件不能为空");
		}

		if(null == billFreightToCollectVO.getDto().getStartDate()){
			// 结束日期为空则抛出异常
			throw new SettlementException("开始日期不能为空");
		}
		
		// 结束日期非空校验
		if (null == billFreightToCollectVO.getDto().getEndDate()) {
			// 结束日期为空则抛出异常
			throw new SettlementException("结束日期不能为空");
		}
		
//		 判断开始日期是否小于结束日期
		if (billFreightToCollectVO.getDto().getStartDate() != null
				&& billFreightToCollectVO.getDto().getEndDate() != null) {
			Date startDate = DateUtils.truncate(billFreightToCollectVO
					.getDto().getStartDate(), Calendar.SECOND);
			Date endDate = DateUtils.truncate(billFreightToCollectVO
					.getDto().getEndDate(), Calendar.SECOND);
			// 如果开始日期在结束日期之后
			if (startDate.after(endDate)) {
				// 开始日期小于结束日期则抛出异常
				throw new SettlementException("开始日期大于结束日期！");
			}
		}
		
//		// 开始日期非空则按大区或者小区查询迄今为止所有数据
//		if (null == billFreightToCollectVO.getDto().getStartDate()) {
//			if (null == billFreightToCollectVO.getDto().getBigArea()&& null == billFreightToCollectVO.getDto().getSmallArea()) {
//				throw new SettlementException("查询截止目前为止所有数据，大区小区不能同时为空！");
//			}
//		} else {
//			
//			if(null == billFreightToCollectVO.getDto().getStartDate()){
//				// 结束日期为空则抛出异常
//				throw new SettlementException("开始日期不能为空");
//			}
//			// 判断开始日期是否小于结束日期
//			if (billFreightToCollectVO.getDto().getStartDate() != null
//					&& billFreightToCollectVO.getDto().getEndDate() != null) {
//				Date startDate = DateUtils.truncate(billFreightToCollectVO
//						.getDto().getStartDate(), Calendar.SECOND);
//				Date endDate = DateUtils.truncate(billFreightToCollectVO
//						.getDto().getEndDate(), Calendar.SECOND);
//				// 如果开始日期在结束日期之后
//				if (startDate.after(endDate)) {
//					// 开始日期小于结束日期则抛出异常
//					throw new SettlementException("开始日期大于结束日期！");
//				}
//			}
//		}
	}

	/**
	 * 根据BillReceivableEntityList转化为SheetData.
	 *
	 * @param billReceivableEntityList the bill receivable entity list
	 * @return the sheet data by cod dto list
	 * @author ibm-guxinhua
	 * @date 2012-11-9 下午4:12:32
	 */
	private SheetData getSheetDataByBillReceivableEntityList(List<BillFreightToCollectResultDto> billReceivableEntityList) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SheetData sheetData = new SheetData();
		sheetData.setRowHeads(new String[] {"业务日期","单据编号","始发部门","到达部门","运单号","单据类别",
				"库存状态","货物总件数","运输性质","应收","实收","未收"});
		List<List<String>> rowList = new ArrayList<List<String>>();
		List<String> cellList = null;

		List<String> types=new ArrayList<String>();
		types.add(DictionaryConstants.BILL_RECEIVABLE__BILL_TYPE);// 代收货款类型
		
		//后台获取数据字典对应的数据
		Map<String,Map<String,Object>> map = SettlementUtil.getDataDictionaryByTermsCodes(types);
		
		for (BillFreightToCollectResultDto entity : billReceivableEntityList) {
			cellList = new ArrayList<String>();
			cellList.add(null == entity.getBusinessDate()? "" : sdf.format(entity.getBusinessDate()));
			cellList.add(entity.getReceivableNo());
			cellList.add(entity.getOrigOrgName());
			cellList.add(entity.getDestOrgName());
			cellList.add(entity.getWaybillNo());
			cellList.add(entity.getBillType() == null ? "" : 
				SettlementUtil.getDataDictionaryByTermsName(map,
						DictionaryConstants.BILL_RECEIVABLE__BILL_TYPE,entity.getBillType()));
			//调用中转接口，查询货物的库存状态
			if(StringUtils.isNotEmpty(entity.getStockStatus())){
				cellList.add(changeStockStatusToName(entity.getStockStatus()));			
			}
			cellList.add(String.valueOf(entity.getGoodsQtyTotal()));
			cellList.add(productService.getProductByCache(entity.getProductCode(),new Date()).getName());	
			cellList.add(entity.getAmount().toString());
			cellList.add(entity.getVerifyAmount().toString());
			cellList.add(entity.getUnverifyAmount().toString());
			rowList.add(cellList);
		}
		sheetData.setRowList(rowList);
		return sheetData;
	}
	
	/**
	 * 带标记的returnError.
	 *
	 * @param e the e
	 * @param returnError the return error
	 * @return the string
	 * @author ibm-guxinhua
	 * @date 2012-11-30 上午11:34:13
	 */
	private String markLogReturnError(Exception e, String returnError) {
		//设置显示结果类型为Error
		setResultMark(ERROR);
		//打印错误信息
		LOGGER.error("到付清查导出" + e.getMessage(), e);
		//返回错误信息
		return returnError(returnError);
	}
	
	/**
	 * 库存状态转化
	 * 
	 * @author foss-zhangxiaohui
	 * @date Mar 28, 2013 8:40:42 PM
	 */
	private String changeStockStatusToName(String stockStatus) {
		//判断是否是库存少货
		if(stockStatus.equals("LOSE_STOCK")){
			//返回库存少货
			return "库存少货";
		}
		//判断是否是正常签收
		else if(stockStatus.equals("NORMAL_SIGN_STOCK")){
			//返回正常签收
			return "正常签收";
		}
		//判断是否是异常签收
		else if(stockStatus.equals("UNNORMAL_SIGN")){
			//返回异常签收
			return "异常签收";
		}
		//判断是否未入库
		else if(stockStatus.equals("NO_STOCK")){
			//返回未入库
			return "未入库";
		}
		//判断是否是库存中
		else if(stockStatus.equals("IN_STOCK")){
			//返回库存中
			return "库存中";
		}
		//判断是否是库存异常
		else if(stockStatus.equals("EXCEPTION_STOCK")){
			//返回库存异常
			return "库存异常";
		}else{
			//不属于以上情况则直接返回原值
			return stockStatus;
		}
	}
	
	/**
	 * Gets the bill freight to collect vo.
	 *
	 * @return billFreightToCollectVO
	 */
	public BillFreightToCollectVo getBillFreightToCollectVO() {
		return billFreightToCollectVO;
	}

	/**
	 * Sets the bill freight to collect vo.
	 *
	 * @param billFreightToCollectVO the new bill freight to collect vo
	 */
	public void setBillFreightToCollectVO(
			BillFreightToCollectVo billFreightToCollectVO) {
		this.billFreightToCollectVO = billFreightToCollectVO;
	}

	/**
	 * Sets the bill freight to collect query service.
	 *
	 * @param billFreightToCollectQueryService the new bill freight to collect query service
	 */
	public void setBillFreightToCollectQueryService(
			IBillFreightToCollectQueryService billFreightToCollectQueryService) {
		this.billFreightToCollectQueryService = billFreightToCollectQueryService;
	}

	/**
	 * @param stockService the stockService to set
	 */
	/*public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}*/
	
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
	 * Gets the execl name.
	 *
	 * @return execlName
	 */
	public String getExeclName() {
		return execlName;
	}

	/**
	 * Sets the execl name.
	 *
	 * @param execlName the new execl name
	 */
	public void setExeclName(String execlName) {
		this.execlName = execlName;
	}

	/**
	 * Gets the result mark.
	 *
	 * @return resultMark
	 */
	public String getResultMark() {
		return resultMark;
	}

	/**
	 * Sets the result mark.
	 *
	 * @param resultMark the new result mark
	 */
	public void setResultMark(String resultMark) {
		this.resultMark = resultMark;
	}

	/**
	 * @param productService
	 */
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	
	
}
