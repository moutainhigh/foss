package com.deppon.foss.module.settlement.consumer.server.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
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
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillReceivableQueryService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillReceivableDto;
import com.deppon.foss.module.settlement.consumer.api.shared.vo.BillReceivableVo;
import com.deppon.foss.util.NumberUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 查询应收单Action.
 *
 * @author foss-zhangxiaohui
 * @date Oct 17, 2012 11:04:56 AM
 */
public class BillReceivableQueryAction extends AbstractAction {

	/** 实体序列号. */
	private static final long serialVersionUID = -475408607567583013L;

	/** 声明日志对象. */
	private static final Logger LOGGER = LoggerFactory.getLogger(BillReceivableQueryAction.class);

	/** 导出excel名称. */
	private static final String EXPORT_NAME = "应收单";

	/** 应收单VO. */
	private BillReceivableVo billReceivableVO;

	/** 应收单查询Service. */
	private IBillReceivableQueryService billReceivableQueryService;

	/** 应收单Entity 查询Service. */
	private IBillReceivableService billReceivableService;

	/** 导出excel的输入流. */
	private ByteArrayInputStream inputStream;

	/** Excel文件名. */
	private String excelName;

	/**
	 * 根据产品CODE找对应的名称
	 */
	IProductService  productService;

	/**
	 * 查询应收单Action--通过单号查询应收单.
	 *
	 * @return the string
	 * @author foss-zhangxiaohui
	 * @date Oct 30, 2012 4:10:29 PM
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@JSON
	public String queryReceivableBillByWayBillNo() {
		try {
			// 判断页面传过来的运单号数组是否有值
			if (null == billReceivableVO.getWayBillNosArray()
					|| billReceivableVO.getWayBillNosArray().length == 0) {
				// 运单数组为空则抛出异常
				throw new SettlementException("查询单号不能为空");
			}
			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();
			// 获取当前登录用户下属的所有部门Set
			Set<String> userOrgCodes = cInfo.getUser().getOrgids();
			// 将当前登录用户下属部门Set转化为List
			List<String> userOrgCodesList = new ArrayList<String>(userOrgCodes);
			// 声明运单号的List
			List<String> wayBillNoList = new ArrayList<String>();
			// 声明应收单单号的List
			List<String> receivableBillNoList = new ArrayList<String>();
			// 以开头是否包含YS来判断是否是应收单号，从而放在不同的List里面去执行查询
			for (int i = 0; i < billReceivableVO.getWayBillNosArray().length; i++) {
				// 如果以YS开头则放到应收单单号的List
				if (billReceivableVO.getWayBillNosArray()[i].startsWith("YS")) {
					// 如果运单是以YS开头则添加到按照应收单号查询的List
					receivableBillNoList.add(billReceivableVO.getWayBillNosArray()[i]);
				}
				// 如果不是YS开头则放在按照运单单号查询的List
				else {
					wayBillNoList.add(billReceivableVO.getWayBillNosArray()[i]);
				}
			}
			// 存放由运单单号查询出来出来的数据
			List<BillReceivableEntity> billReceivableBillNoList = new ArrayList<BillReceivableEntity>();
			// 判断按照运单单号查询的List是否为空
			if (CollectionUtils.isNotEmpty(wayBillNoList)) {
				// 按运单单号List查询
				billReceivableBillNoList = billReceivableService.queryByWaybillNosAndOrgCodes(wayBillNoList, userOrgCodesList, null,cInfo);
			}
			// 存放由应收单单号查询出来的应收单实体对象集合
			List<BillReceivableEntity> list = new ArrayList<BillReceivableEntity>();
			// 如果应收单单号的List有值则执行应收单单号的查询
			if (CollectionUtils.isNotEmpty(receivableBillNoList)) {
				// 以应收单单号做参数查询
				list = billReceivableService.queryByReceivableNosAndOrgCodes(receivableBillNoList, "Y",cInfo);
			}
			// 声明一个总的List来存放按照运单号的查询结果和应收单号查询的结果
			List<BillReceivableEntity> listAll = new ArrayList<BillReceivableEntity>();
			
			// 只有有一个查询结果不为空才能做 Collection的并集
			if (CollectionUtils.isNotEmpty(billReceivableBillNoList)){
				//运单号查询的结果不为空则添加
				listAll.addAll(billReceivableBillNoList);
			}	
			// 只有有一个查询结果不为空才能做 Collection的并集
			if (CollectionUtils.isNotEmpty(list)){
				//运单号查询的结果不为空则添加
				listAll.addAll(list);
			}
			//声明一个HashSet去除重复的数据
			HashSet h = new HashSet(listAll);
			//清空所占的堆空间
			listAll.clear();
			//存储处理后的结果
			listAll.addAll(h);
			if(CollectionUtils.isNotEmpty(listAll)){
			// 应收单数据赋值给页面上的List
			billReceivableVO.setBillReceivableList(listAll);
			// 设置查询出来的数据的数量
			billReceivableVO.setTotalRecordsInDB(listAll.size());
			// 声明合计总金额
			BigDecimal totalAmount = null;
			// 声明 核销总金额/实收总金额
			BigDecimal totalVerifyAmount = null;
			// 声明未核销总金额/未收金额
			BigDecimal totalUnverifyAmount = null;
			// 设置返回数据中总金额，未核销金额和已核销金额
			for (int i = 0; i < listAll.size(); i++) {
				// 累加总金额
				totalAmount = NumberUtils.add(totalAmount,listAll.get(i).getAmount());
				// 累加核销总金额
				totalVerifyAmount = NumberUtils.add(totalVerifyAmount,
						listAll.get(i).getVerifyAmount());
				// 累加未核销总金额
				totalUnverifyAmount = NumberUtils.add(totalUnverifyAmount, listAll.get(i).getUnverifyAmount());
			}
			// 设置查询出来的总金额
			billReceivableVO.setTotalAmount(totalAmount);
			// 设置查询出来的核销总金额
			billReceivableVO.setTotalVerifyAmount(totalVerifyAmount);
			// 设置查询出来的未核销总金额
			billReceivableVO.setTotalUnverifyAmount(totalUnverifyAmount);
			}else{
				//这里不做处理
			}
		}
		// 捕获异常
		catch (BusinessException e) {
			// 打印错误的信息
			LOGGER.error("按运单号查询应收单" + e.getMessage(), e);
			// 返回错误信息
			return returnError("按运单号查询应收单" + e);
		}
		// 返回结果
		return returnSuccess();
	}

	/**
	 * 通过来源单号查询应收单.
	 *
	 * @return the string
	 * @author foss-zhangxiaohui
	 * @date Jan 22, 2013 3:57:17 PM
	 */
	@JSON
	public String queryReceivableBillBySourceBillNo() {
		try {
			// 判断页面传过来的来源单号数组是否有值
			if (null == billReceivableVO.getSourceBillNosArray()
					|| billReceivableVO.getSourceBillNosArray().length == 0) {
				// 运单数组为空则抛出异常
				throw new SettlementException("查询来源单号不能为空");
			}
			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();
			// 获取当前登录用户下属的所有部门Set
			Set<String> userOrgCodes = cInfo.getUser().getOrgids();
			// 将当前登录用户下属部门Set转化为List
			List<String> userOrgCodesList = new ArrayList<String>(userOrgCodes);
			// 声明来源单号的List
			List<String> sourceBillNoList = new ArrayList<String>();
			// 遍历数组
			for (int i = 0; i < billReceivableVO.getSourceBillNosArray().length; i++) {
				// 放在按照运单单号查询的List
				sourceBillNoList.add(billReceivableVO.getSourceBillNosArray()[i]);
			}
			// 存放由来源单号查询出来出来的数据
			List<BillReceivableEntity> list = new ArrayList<BillReceivableEntity>();
			//判断来源单号是否为空
			if(CollectionUtils.isNotEmpty(sourceBillNoList)){
				// 按运单单号List查询
				list = billReceivableService.queryBySourceBillNOsAndOrgCodes(sourceBillNoList, userOrgCodesList, null,null,cInfo);
			}

			// 只有有一个查询结果不为空才能做 Collection的并集
			if (CollectionUtils.isNotEmpty(list)){
			// 应收单数据赋值给页面上的List
			billReceivableVO.setBillReceivableList(list);
			// 设置查询出来的数据的数量
			billReceivableVO.setTotalRecordsInDB(list.size());
			// 声明合计总金额
			BigDecimal totalAmount = null;
			// 声明 核销总金额/实收总金额
			BigDecimal totalVerifyAmount = null;
			// 声明未核销总金额/未收金额
			BigDecimal totalUnverifyAmount = null;
			// 设置返回数据中总金额，未核销金额和已核销金额
			for (int i = 0; i < list.size(); i++) {
				// 累加总金额
				totalAmount = NumberUtils.add(totalAmount,list.get(i).getAmount());
				// 累加核销总金额
				totalVerifyAmount = NumberUtils.add(totalVerifyAmount,
						list.get(i).getVerifyAmount());
				// 累加未核销总金额
				totalUnverifyAmount = NumberUtils.add(totalUnverifyAmount, list.get(i).getUnverifyAmount());
			}
			// 设置查询出来的总金额
			billReceivableVO.setTotalAmount(totalAmount);
			// 设置查询出来的核销总金额
			billReceivableVO.setTotalVerifyAmount(totalVerifyAmount);
			// 设置查询出来的未核销总金额
			billReceivableVO.setTotalUnverifyAmount(totalUnverifyAmount);
			}else{
				//这里不做处理
			}
		}
		// 捕获异常
		catch (BusinessException e) {
			// 打印错误的信息
			LOGGER.error("按来源单号查询应收单" + e.getMessage(), e);
			// 返回错误信息
			return returnError("按来源单号查询应收单" + e);
		}
		// 返回结果
		return returnSuccess();
	}
	
	/**
	 * 查询应收单Action--通过Dto查询应收单.
	 *
	 * @return the string
	 * @author foss-zhangxiaohui
	 * @date Oct 30, 2012 4:11:29 PM
	 */
	@JSON
	public String queryReceivableBillByDate() {
		try {

			this.validateDate();// 校验日期
			BillReceivableDto dto = null;// 声明临时的Dto对象存放查询出来的记录总条数和总金额

			CurrentInfo cInfo = FossUserContext.getCurrentInfo();// 获取当前登录用户信息
			BillReceivableDto billReceivableDto = billReceivableVO.getDto();// 获得页面传过来的参数对象Dto
			String dateType = billReceivableDto.getSelectDateType();// 声明一个变量去存放页面所选择的日期类型
			List<BillReceivableEntity> billReceivableBillNoList = null;// 声明存放查询结果的List(返回页面显示的)

			Date endDate = DateUtils.addDays(billReceivableDto.getEndDate(), 1);
			billReceivableDto.setEndDate(endDate);// 给当前日期增加一天
			
			//催款部门和收入部门			
			if(SettlementConstants.GENERATING_ORG_CODE.equals(billReceivableDto.getIsDept())){
				billReceivableDto.setGeneratingOrgCode(SettlementConstants.GENERATING_ORG_CODE);
			}else{
				billReceivableDto.setDunningDeptCode(SettlementConstants.DUNNING_ORG_CODE);
			}
			
			billReceivableDto.setIsPartner(FossConstants.NO);
			if (SettlementConstants.TAB_DATE_TYPE_FOR_BUSINESS.equals(dateType)) {// 日期类型为业务日期执行操作
				dto = billReceivableQueryService.queryTotalAmountByBusinessDate(billReceivableDto,cInfo);// 赋值给Dto对象
			} else if (SettlementConstants.TAB_DATE_TYPE_FOR_ACCOUNT.equals(dateType)) {// 日期类型为记账日期时执行操作
				dto = billReceivableQueryService.queryTotalAmountByAccountDate(billReceivableDto, cInfo);// 赋值给Dto对象
			} else {
				throw new SettlementException("传入的日期类型异常");// 如果日期类型为异常的常量则抛出异常
			}

			// 查询数据库中记录总条数是否大于0，如果大于0则抛异常
			if (null != dto && dto.getTotalRecordsInDB() > 0) {
				setTotalCount(Long.valueOf(dto.getTotalRecordsInDB()));// 应收单分页数据条数
				billReceivableVO.setTotalRecordsInDB(dto.getTotalRecordsInDB());// 取出List里面的总记录条数并赋值给Vo传回页面
				billReceivableVO.setTotalAmount(dto.getTotalAmount());// 取出List里面的总金额并赋值给Vo传回页面
				billReceivableVO.setTotalVerifyAmount(dto
						.getTotalVerifyAmount());// 取出List里面的核销总金额并赋值给Vo传回页面
				billReceivableVO.setTotalUnverifyAmount(dto
						.getTotalUnverifyAmount());// 取出List里面的未核销总金额并赋值给Vo传回页面

				// 日期类型为业务日期执行操作
				if (SettlementConstants.TAB_DATE_TYPE_FOR_BUSINESS
						.equals(dateType)) {
					// 赋值给声明的对象以返回页面显示
					billReceivableBillNoList = billReceivableQueryService
							.queryBillReceivableByBusinessDate(getStart(),
									getLimit(), billReceivableDto, cInfo);
				} else if (SettlementConstants.TAB_DATE_TYPE_FOR_ACCOUNT
						.equals(dateType)) {// 日期类型为记账日期时执行操作
					// 赋值给声明的对象以返回页面显示
					billReceivableBillNoList = billReceivableQueryService
							.queryBillReceivableByAccountDate(getStart(),
									getLimit(), billReceivableDto, cInfo);
				} else {
					throw new SettlementException("传入的日期类型异常");// 如果日期类型为异常的常量则抛出异常
				}
				if (CollectionUtils.isNotEmpty(billReceivableBillNoList)) {// 应收单数据不为空则赋值传回页面
					billReceivableVO
							.setBillReceivableList(billReceivableBillNoList);// 查询结果不为空则传回页面
				} else {// 这里不做处理
				}
			}
		} catch (BusinessException e) {// 捕获异常
			LOGGER.error("按日期查询应收单" + e.getMessage(), e);// 输出log信息
			return returnError("按日期查询应收单" + e);// 返回错误信息
		}
		// 返回结果
		return returnSuccess();
	}

	/**
	 * 查询应收单Action--校验日期.
	 *
	 * @author foss-zhangxiaohui
	 * @date Dec 25, 2012 1:11:29 PM
	 */
	private void validateDate() {
		// 查询的参数不能为空
		if (null == billReceivableVO || null == billReceivableVO.getDto()) {
			// 如果查询参数的Dto为空则抛出异常
			throw new SettlementException("查询参数不能为空");
		}
		// 开始日期非空校验
		if (billReceivableVO.getDto().getStartDate() == null) {
			// 开始日期为空则抛出异常
			throw new SettlementException("开始日期不能为空");
		}
		// 结束日期非空校验
		if (null == billReceivableVO.getDto().getEndDate()) {
			// 结束日期为空则抛出异常
			throw new SettlementException("结束日期不能为空");
		}
		// 判断开始日期是否小于结束日期
		if (billReceivableVO.getDto().getStartDate() != null
				&& billReceivableVO.getDto().getEndDate() != null) {
			Date startDate = DateUtils.truncate(billReceivableVO.getDto()
					.getStartDate(), Calendar.SECOND);
			Date endDate = DateUtils.truncate(billReceivableVO.getDto()
					.getEndDate(), Calendar.SECOND);
			// 判断开始日期是否在结束日期之后
			if (startDate.after(endDate)) {
				// 如果开始日期在结束日期之后则抛出异常
				throw new SettlementException("开始日期大于结束日期！");
			}
		}
	}

	/**
	 * 导出应收单.
	 *
	 * @return the string
	 * @author ibm-zhuwei
	 * @date 2012-12-27 下午2:27:55
	 */
	@SuppressWarnings("unchecked")
	public String exportBillReceivable() {
		String[] arrayColumnNames = billReceivableVO.getDto().getArrayColumnNames();
		String[] arrayColumns = billReceivableVO.getDto().getArrayColumns();
		/**
		 * 如果导出列头名称不存在，则抛出异常提示
		 */
		if (arrayColumnNames == null|| arrayColumnNames.length == 0) {
			throw new SettlementException("导出Excel的列头名称不存在，请至少存在一列!");
		}
		/**
		 * 如果导出列头名称不存在，则抛出异常提示
		 */
		if (arrayColumns == null || arrayColumns.length == 0) {
			throw new SettlementException("导出Excel的列头不存在，请至少存在一列!");
		}
		
		// 1)查询
		try {
			// 按运单查询
			if (SettlementConstants.TAB_QUERY_BY_WAYBILL_NO
					.equals(billReceivableVO.getDto().getQueryTab())) {
				//对查询参数做非空验证
				if (null == billReceivableVO.getWayBillNosArray()
						|| billReceivableVO.getWayBillNosArray().length == 0) {
					// 运单数组为空则抛出异常
					throw new SettlementException("查询单号不能为空");
				}
				// 声明运单号的List
				List<String> wayBillNoList = new ArrayList<String>();
				// 声明应收单单号的List
				List<String> receivableBillNoList = new ArrayList<String>();

				// 以开头是否包含YS来判断是否是应收单号，从而放在不同的List里面去执行查询
				for (int i = 0; i < billReceivableVO.getWayBillNosArray().length; i++) {
					// 如果以YS开头则放到应收单单号的List
					if (billReceivableVO.getWayBillNosArray()[i].startsWith("YS")) {
						// 如果运单是以YS开头则添加到按照应收单号查询的List
						receivableBillNoList.add(billReceivableVO.getWayBillNosArray()[i]);
					}
					// 如果不是YS开头则放在按照运单单号查询的List
					else {
						wayBillNoList.add(billReceivableVO.getWayBillNosArray()[i]);
					}
				}
				// 获取当前登录用户信息
				CurrentInfo cInfo = FossUserContext.getCurrentInfo();
				// 存放由运单单号查询出来出来的数据
				List<BillReceivableEntity> billReceivableBillNoList = null;
				// 判断按照运单单号查询的List是否为空
				if (CollectionUtils.isNotEmpty(wayBillNoList)) {
					// 按运单单号List查询
					billReceivableBillNoList = billReceivableService.queryByWaybillNosAndOrgCodes(wayBillNoList, null, null,cInfo);
				}
				// 存放由应收单单号查询出来的应收单实体对象集合
				List<BillReceivableEntity> list = null;
				// 如果应收单单号的List有值则执行应收单单号的查询
				if (CollectionUtils.isNotEmpty(receivableBillNoList)) {
					// 以应收单单号做参数查询
					list = billReceivableService.queryByReceivableNOs(
							receivableBillNoList, "");
				}
				// 声明一个总的List来存放按照运单号的查询结果和应收单号查询的结果
				List<BillReceivableEntity> listAll = new ArrayList<BillReceivableEntity>();
				// 只有有一个查询结果不为空才能做 Collection的并集
				if (CollectionUtils.isNotEmpty(billReceivableBillNoList)){
					//运单号查询的结果不为空则添加
					listAll.addAll(billReceivableBillNoList);
				}
				// 只有有一个查询结果不为空才能做 Collection的并集
				if (CollectionUtils.isNotEmpty(list)){
					//运单号查询的结果不为空则添加
					listAll.addAll(list);
				}
				//声明一个HashSet去除重复的数据
				@SuppressWarnings({"rawtypes" })
				HashSet h = new HashSet(listAll);
				//清空所占的堆空间
				listAll.clear();
				//存储处理后的结果
				listAll.addAll(h);
				if(CollectionUtils.isNotEmpty(listAll)){
				// 应收单数据赋值给页面上的List
				billReceivableVO.setBillReceivableList(listAll);
				}
			}
			// 按日期查询
			else if (SettlementConstants.TAB_QUERY_BY_DATE.equals(billReceivableVO.getDto().getQueryTab())) {
				
				Date endDate = DateUtils.addDays(billReceivableVO.getDto().getEndDate(), 1);
				billReceivableVO.getDto().setEndDate(endDate);
	
				List<BillReceivableEntity> billReceivableBillNoList = null;// 声明存放查询结果的List(返回页面显示的)
				CurrentInfo cInfo = FossUserContext.getCurrentInfo();// 获取当前登录用户信息
				
				//催款部门和收入部门			
				if(SettlementConstants.GENERATING_ORG_CODE.equals(billReceivableVO.getDto().getIsDept())){
					billReceivableVO.getDto().setGeneratingOrgCode(SettlementConstants.GENERATING_ORG_CODE);
				}else{
					billReceivableVO.getDto().setDunningDeptCode(SettlementConstants.DUNNING_ORG_CODE);
				}
				
				billReceivableVO.getDto().setIsPartner(FossConstants.NO);
				if (SettlementConstants.TAB_DATE_TYPE_FOR_BUSINESS
						.equals(billReceivableVO.getDto().getSelectDateType())) {// 日期类型为业务日期执行操作
					
					billReceivableBillNoList = billReceivableQueryService// 赋值给声明的对象以返回页面显示
							.queryBillReceivableByBusinessDate(0,SettlementConstants.EXPORT_EXCEL_MAX_COUNTS,
									billReceivableVO.getDto(),cInfo);
				}else if (SettlementConstants.TAB_DATE_TYPE_FOR_ACCOUNT// 日期类型为记账日期时执行操作
						.equals(billReceivableVO.getDto().getSelectDateType())) {

					// 赋值给声明的对象以返回页面显示
					billReceivableBillNoList = billReceivableQueryService
							.queryBillReceivableByAccountDate(0,SettlementConstants.EXPORT_EXCEL_MAX_COUNTS,
									billReceivableVO.getDto(),cInfo);
				}
				// 如果查出来的结果集不为空则赋值给页面上的List
				if (CollectionUtils.isNotEmpty(billReceivableBillNoList)) {
					// 查询结果不为空则传回页面
					billReceivableVO.setBillReceivableList(billReceivableBillNoList);
				}
			}else{

				// 判断页面传过来的来源单号数组是否有值
				if (null == billReceivableVO.getSourceBillNosArray()
						|| billReceivableVO.getSourceBillNosArray().length == 0) {
					// 运单数组为空则抛出异常
					throw new SettlementException("查询来源单号不能为空");
				}
				// 获取当前登录用户信息
				CurrentInfo cInfo = FossUserContext.getCurrentInfo();
				// 获取当前登录用户下属的所有部门Set
				Set<String> userOrgCodes = cInfo.getUser().getOrgids();
				// 将当前登录用户下属部门Set转化为List
				List<String> userOrgCodesList = new ArrayList<String>(userOrgCodes);
				// 声明来源单号的List
				List<String> sourceBillNoList = new ArrayList<String>();
				// 遍历数组
				for (int i = 0; i < billReceivableVO.getSourceBillNosArray().length; i++) {
					// 放在按照运单单号查询的List
					sourceBillNoList.add(billReceivableVO.getSourceBillNosArray()[i]);
				}
				// 存放由来源单号查询出来出来的数据
				List<BillReceivableEntity> list = new ArrayList<BillReceivableEntity>();
				//判断来源单号是否为空
				if(CollectionUtils.isNotEmpty(sourceBillNoList)){
					// 按运单单号List查询
					list = billReceivableService.queryBySourceBillNOsAndOrgCodes(sourceBillNoList, userOrgCodesList, null,null,cInfo);
				}
				// 只有有一个查询结果不为空才能做 Collection的并集
				if (CollectionUtils.isNotEmpty(list)){
					// 应收单数据赋值给页面上的List
					billReceivableVO.setBillReceivableList(list);
				}
			}
		} 
		//捕获异常
		catch (BusinessException e) {
			//打印错误信息
			LOGGER.error("应收单导出" + e.getMessage(), e);
			//返回错误信息
			return returnError("应收单导出" + e.getMessage());
		}
		// 2)导出
		ByteArrayOutputStream os = null;
		try {
			// 导出Excel
			ExcelExport export = new ExcelExport();
			// 设置每次最多导出10万条数据
			HSSFWorkbook work = export.exportExcel(fillSheetData(this
					.getBillReceivableVO().getBillReceivableList(),arrayColumns,arrayColumnNames),
					SettlementConstants.EXCEL_SHEET_NAME,
					SettlementConstants.EXPORT_EXCEL_MAX_COUNTS);

			excelName = new String(
					(EXPORT_NAME).getBytes(SettlementConstants.UNICODE_GBK),
					SettlementConstants.UNICODE_ISO);
			//给输出流申请内存空间
			os = new ByteArrayOutputStream();
			//把输出流写入工作簿对象
			work.write(os);
			//赋值给输入流
			inputStream = new ByteArrayInputStream(os.toByteArray());
		} 
		//捕获IO异常
		catch (IOException e) {
			//打印错误信息
			LOGGER.error(e.getMessage(), e);
			//返回错误信息
			return returnError("导出Excel失败");
		} finally {
			//如果输出流不为空则关掉该资源
			if (os != null) {
				try {
					//关掉输出流
					os.close();
				} 
				//捕获关掉输出流时可能的IO异常
				catch (IOException e) {
					//返回错误信息
					return returnError("导出Excel失败");
				}
			}
		}
		//返回正确结果
		return returnSuccess();
	}

	/**
	 * 应收单填充Excel.
	 *
	 * @param list the list
	 * @return the sheet data
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-12 上午8:29:56
	 */
	private SheetData fillSheetData(List<BillReceivableEntity> list,String[] arrayColumns,String[] arrayColumnNames) {
		//声明Excel的Sheet对象来存放每个Sheet的数据
		SheetData sheetData = new SheetData();
		sheetData.setRowHeads(arrayColumnNames);
		//判断填充的对象是否为空
		if (CollectionUtils.isEmpty(list)) {
			//返回SheetData对象
			return sheetData;
		}
		List<List<String>> rowList = new ArrayList<List<String>>();
		
		//声明字典集合
		List<String> termCodes = new ArrayList<String>();
		termCodes.add(DictionaryConstants.BILL_RECEIVABLE__BILL_TYPE);
		termCodes.add(DictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE);
		termCodes.add(DictionaryConstants.SETTLEMENT__PAYMENT_TYPE);
		termCodes.add(DictionaryConstants.FOSS_BOOLEAN);
		termCodes.add(DictionaryConstants.SETTLEMENT__CREATE_TYPE);
		termCodes.add(DictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS);
		termCodes.add(DictionaryConstants.BILL_RECEIVABLE__COLLECTION_TYPE);
		//FOSS_BOOLEAN
		termCodes.add(DictionaryConstants.FOSS_BOOLEAN);
			
		//termCodes.add(DictionaryConstants.ASSEMBLE_TRANSPROPERTY);
		//后台获取数据字典对应的数据
		Map<String,Map<String,Object>> map = SettlementUtil.getDataDictionaryByTermsCodes(termCodes);
		
		for (BillReceivableEntity entity : list) {
			List<String> cellList = new ArrayList<String>();
			for (String columnName : arrayColumns) {
				// 通过名称获取field
				Field field = ReflectionUtils.findField(
						BillReceivableEntity.class, columnName);
				if (field != null) {
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
						//数据字典转化
						if(columnName.equals("billType")){
							fieldValue = SettlementUtil.getDataDictionaryByTermsName(map, 
									DictionaryConstants.BILL_RECEIVABLE__BILL_TYPE, fieldValue.toString());
						}else if(columnName.equals("sourceBillType")){
							fieldValue = SettlementUtil.getDataDictionaryByTermsName(map, 
									DictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE, fieldValue.toString());
						}else if(columnName.equals("paymentType")){
							fieldValue = SettlementUtil.getDataDictionaryByTermsName(map, 
									DictionaryConstants.SETTLEMENT__PAYMENT_TYPE, fieldValue.toString());
						}else if(columnName.equals("active")){
							fieldValue = SettlementUtil.getDataDictionaryByTermsName(map, 
									DictionaryConstants.FOSS_BOOLEAN, fieldValue.toString());
						}else if(columnName.equals("isRedBack")){
							fieldValue = SettlementUtil.getDataDictionaryByTermsName(map, 
									DictionaryConstants.FOSS_BOOLEAN, fieldValue.toString());
						}else if(columnName.equals("isInit")){
							fieldValue = SettlementUtil.getDataDictionaryByTermsName(map, 
									DictionaryConstants.FOSS_BOOLEAN, fieldValue.toString());
						}else if(columnName.equals("createType")){
							fieldValue = SettlementUtil.getDataDictionaryByTermsName(map, 
									DictionaryConstants.SETTLEMENT__CREATE_TYPE, fieldValue.toString());
						}else if (columnName.equals("approveStatus")){			
							fieldValue = SettlementUtil.getDataDictionaryByTermsName(map, 
							DictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS, fieldValue.toString());
						
						}else if(columnName.equals("stamp")){
							fieldValue = SettlementUtil.getDataDictionaryByTermsName(map, 
									DictionaryConstants.FOSS_BOOLEAN, fieldValue.toString());
						}else if (columnName.equals("collectionType")){
							fieldValue = SettlementUtil.getDataDictionaryByTermsName(map, 
						    DictionaryConstants.BILL_RECEIVABLE__COLLECTION_TYPE, fieldValue.toString());								
						//发票标记
						}else if(columnName.equals("invoiceMark")){
							if(fieldValue.toString().equals(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_ONE))
							{
								fieldValue="01-运输专票11%";
							}else if(fieldValue.toString().equals(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_TWO)){
								fieldValue="02-非运输专票";
							}
						}else if(columnName.equals("productCode")){	
							fieldValue = productService.getProductByCache(fieldValue.toString(),new Date()).getName();
						}else if(columnName.equals("isDiscount")){
							if(FossConstants.YES.equals(fieldValue.toString()))	{
								fieldValue="是";
							}else{
								fieldValue="否";
							}
						}else if(columnName.equals("withholdStatus")){
							if(SettlementDictionaryConstants.STL__WITHHOLD_STATUS__UNWITHHOLDED.equals(fieldValue.toString())){
								fieldValue="未扣款";
							}else if(SettlementDictionaryConstants.STL__WITHHOLD_STATUS__WITHHOLD_SUCCESS.equals(fieldValue.toString())){
								fieldValue="扣款成功";
							}else if(SettlementDictionaryConstants.STL__WITHHOLD_STATUS__WITHHOLD_FAILED.equals(fieldValue.toString())){
								fieldValue="扣款失败";
							}else{
								fieldValue="";
							}
						}
						//设置属性值
						if(fieldValue!=null){
							cellList.add(fieldValue.toString());
						}else{
							cellList.add(null);
						}						
					} else {
						if(columnName.equals("isDiscount")){
							fieldValue="否";
							cellList.add(fieldValue.toString());
						}else{
							cellList.add(null);
						}
					}
				}
				
			}
			rowList.add(cellList);
		}
		sheetData.setRowList(rowList);
		//返回Sheet数据
		return sheetData;
	}
	
	/**
	 * 标记应收单
	 * @author foss-pengzhen
	 * @date 2013-5-21 下午5:47:51
	 * @return
	 * @see
	 */
	@JSON
	public String stampReceivable(){
		try {
			//获取界面参数
			String receivableNosArray[] = billReceivableVO.getReceivableNosArray();
			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();
			//设置标记状态：Y;
			String stamp = FossConstants.YES;
			//调用接口
			billReceivableQueryService.stampReceivable(receivableNosArray, stamp,cInfo);
			
			return returnSuccess();
		} catch (BusinessException e) {
			// 输出log信息
			LOGGER.error("标记应收单" + e.getMessage(), e);
			// 返回错误信息
			return returnError(e);
		}
	}
	
	/**
	 * 反标记应收单
	 * @author foss-pengzhen
	 * @date 2013-5-21 下午5:47:51
	 * @return
	 * @see
	 */
	@JSON
	public String reverseStampReceivable(){
		try {
			//获取界面参数
			String receivableNosArray[] = billReceivableVO.getReceivableNosArray();
			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();
			//设置标记状态：N;
			String stamp = FossConstants.NO;
			//调用接口
			billReceivableQueryService.stampReceivable(receivableNosArray, stamp,cInfo);
			return returnSuccess();
		} catch (BusinessException e) {
			// 输出log信息
			LOGGER.error("反标记应收单" + e.getMessage(), e);
			// 返回错误信息
			return returnError(e);
		}
	}
	
	/**
	 * Gets the bill receivable vo.
	 *
	 * @return billReceivableVO
	 */
	public BillReceivableVo getBillReceivableVO() {
		return billReceivableVO;
	}

	/**
	 * Sets the bill receivable vo.
	 *
	 * @param billReceivableVO the new bill receivable vo
	 */
	public void setBillReceivableVO(BillReceivableVo billReceivableVO) {
		this.billReceivableVO = billReceivableVO;
	}

	/**
	 * Sets the bill receivable query service.
	 *
	 * @param billReceivableQueryService the new bill receivable query service
	 */
	public void setBillReceivableQueryService(
			IBillReceivableQueryService billReceivableQueryService) {
		this.billReceivableQueryService = billReceivableQueryService;
	}

	/**
	 * Sets the bill receivable service.
	 *
	 * @param billReceivableService the new bill receivable service
	 */
	public void setBillReceivableService(
			IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
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
