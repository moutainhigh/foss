package com.deppon.foss.prt.statementbill;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountDService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountDEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IStatementModifyService;
import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperDataSource;
import com.deppon.foss.print.util.ClassPathResourceUtil;

/**
 * 对账单打印（数据绑定）
 * 
 * @author 045738-foss-maojianqiang
 * @date 2012-11-3 下午12:51:55
 */
public class StatementbillPrtDataSource implements JasperDataSource {

	// 注入日志
	private static final Logger logger = LoggerFactory.getLogger(StatementbillPrtDataSource.class);
	// 时分秒类型
	DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// 日期类型
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");// 此处需要多出一个空格，不然打印日期最后一位出不来。
	// 界面传入带T，需要转化成date,然后再转成目标格式
	DateFormat stringFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	
	/**
	 * 设置form表单的值
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-11-3 下午12:53:19
	 * @see com.deppon.foss.print.jasper.JasperDataSource#createParameterDataSource(com.deppon.foss.print.jasper.JasperContext)
	 */
	public Map<String, Object> createParameterDataSource(JasperContext jasperContext) {
		
		logger.info("------------进入打印数据源开始-------------");
		// 获取图片
		ClassPathResourceUtil util = new ClassPathResourceUtil();
		//获取对账单服务接口
		IStatementModifyService statementModifyService = (IStatementModifyService)jasperContext.getApplicationContext().getBean("statementModifyService");

		//读取图片
		InputStream sumDetail = util.getInputStream("com/deppon/foss/prt/statementbill/images/sumDetail.png");
		// 拼接显示明细
		String periodDetail = "";
		//本次未还应收金额
		BigDecimal periodUnverifyRecAmount = BigDecimal.ZERO;
		//本次未还应付金额
		BigDecimal periodUnverifyPayAmount = BigDecimal.ZERO;
		//本次未还预收金额
		BigDecimal periodUnverifyPreAmount = BigDecimal.ZERO;
		//本次未还预付金额
		BigDecimal periodUnverifyAdvAmount = BigDecimal.ZERO;

		// 声明打印值map
		Map<String, Object> parameter = new HashMap<String, Object>();
		//声明日期列表
		List<String> dataList = new ArrayList<String>();
		dataList.add("periodBeginDate");//起始日期
		dataList.add("periodEndDate");//结束日期
		dataList.add("confirmTime");//确认时间
		dataList.add("createTime");//创建时间

		// 循环拿出界面传入的数据源，放置到要打印map中去
		for (String key : jasperContext.getParamkeys()) {
			// 控制图片显示和隐藏
			if (key.equals("periodAmount") && StringUtil.isNotBlank((String)jasperContext.get(key))) {
				//本期金额
				BigDecimal periodAmount = new BigDecimal((String) jasperContext.get(key));
				// 如果本期发生金额不等于0，则显示图片，否则隐藏
				if (periodAmount.compareTo(BigDecimal.ZERO) != 0) {
					parameter.put("subDetailImg", sumDetail);
				}
			}
			if(key.equals("companyCode") && StringUtil.isNotBlank((String)jasperContext.get(key))){
				// 按公司编号查询对应对账单章
				String chapterUrl = statementModifyService.queryStatementChapter((String)jasperContext.get(key));
				// ddw
				InputStream companyNameImg = util.getInputStream(chapterUrl);
				// 判断图片地址是否为空
				if(companyNameImg != null){
					parameter.put("companyNameImg", companyNameImg);
				}
			}
			// 获取本期剩余应收金额
			if (key.equals("periodUnverifyRecAmount") && StringUtil.isNotBlank((String)jasperContext.get(key))) {
				//获取界面值
				BigDecimal amount = new BigDecimal((String) jasperContext.get(key));
				//判断界面是否传递有该值
				if (amount.compareTo(BigDecimal.ZERO) != 0) {
					periodUnverifyRecAmount = amount;
				}
			}
			// 获取本期剩余应付金额
			if (key.equals("periodUnverifyPayAmount") && StringUtil.isNotBlank((String)jasperContext.get(key))) {
				//获取界面值
				BigDecimal amount = new BigDecimal((String) jasperContext.get(key));
				//判断界面是否传递有该值
				if (amount.compareTo(BigDecimal.ZERO) != 0) {
					periodUnverifyPayAmount = amount;
				}
			}
			// 获取本期剩余预收金额
			if (key.equals("periodUnverifyPreAmount") && StringUtil.isNotBlank((String)jasperContext.get(key))) {
				//获取界面值
				BigDecimal amount = new BigDecimal((String) jasperContext.get(key));
				//判断界面是否传递有该值
				if (amount.compareTo(BigDecimal.ZERO) != 0) {
					periodUnverifyPreAmount = amount;
				}
			}
			// 获取本期剩余预收金额
			if (key.equals("periodUnverifyAdvAmount") && StringUtil.isNotBlank((String)jasperContext.get(key))) {
				//获取界面值
				BigDecimal amount = new BigDecimal((String) jasperContext.get(key));
				//判断界面是否传递有该值
				if (amount.compareTo(BigDecimal.ZERO) != 0) {
					periodUnverifyAdvAmount = amount;
				}
			}

			// 设置转化后的值
			String convertValue = null;
			/**
			 * 下面需要对单据进行转化
			 */
			// 对账单类型
			if (key.equals("billType")&& StringUtil.isNotBlank((String)jasperContext.get(key))) {
				//获取界面传递对账单值
				String value = (String) jasperContext.get(key);
				//进行数据转化
				if(StringUtil.isNotBlank(value)){
					convertValue = DictUtil.rendererSubmitToDisplay(value,DictionaryConstants.STATEMENT_OF_ACCOUNT__BILL_TYPE);
				}
				//设置值
				parameter.put(key, convertValue);
				continue;
			}
			// 确认状态
			if (key.equals("confirmStatus") && jasperContext.get(key) != null) {
				//获取界面传递对账单值
				String value = (String) jasperContext.get(key);
				//进行数据转化 
				if (StringUtil.isNotBlank(value)) {
					convertValue = DictUtil.rendererSubmitToDisplay(value,DictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS);
				}
				//设置值
				parameter.put(key, convertValue);
				continue;
			}
			// 日期类转化
			if ((jasperContext.get(key) != null && !"".equals(jasperContext.get(key)))
					&& dataList.contains(key)) {
				try {
					// 先将传入日期转化为日期对象
					Date targetDate = stringFormat.parse((String) jasperContext.get(key));
					// 如果是起始和结束日期，则转化成日期格式
					if (key.equals("periodBeginDate")|| key.equals("periodEndDate")) {
						//格式化为YYYY-MM-DD
						convertValue = dateFormat.format(targetDate);
					} else {
						//格式化为YYYY-MM-DD hh:mm:ss
						convertValue = dateTimeFormat.format(targetDate);
					}
					parameter.put(key, convertValue);
					continue;
				} catch (ParseException e) {
					//记录日志
					logger.error(e.getMessage(),e);
					throw new SettlementException("日期格式转化错误！");
					
				}
			}
			// 将对应参数放入map中
			parameter.put(key, jasperContext.get(key));
		}
		//获取应收金额
		if (periodUnverifyRecAmount.compareTo(BigDecimal.ZERO) != 0) {
			periodDetail = periodDetail + "应收：" + periodUnverifyRecAmount.toString() + "  ";
		}
		//获取应付金额
		if (periodUnverifyPayAmount.compareTo(BigDecimal.ZERO) != 0) {
			periodDetail = periodDetail + "应付：" + periodUnverifyPayAmount.toString() + "  ";
		}
		//获取预收金额
		if (periodUnverifyPreAmount.compareTo(BigDecimal.ZERO) != 0) {
			periodDetail = periodDetail + "预收：" + periodUnverifyPreAmount.toString() + "  ";
		}
		//获取预付金额
		if (periodUnverifyAdvAmount.compareTo(BigDecimal.ZERO) != 0) {
			periodDetail = periodDetail + "预付：" + periodUnverifyAdvAmount.toString() + "  ";
		}

		if (!"".equals(periodDetail)) {
			parameter.put("periodDetail", periodDetail);
		}
		logger.info("------------进入打印数据源结束-------------");
		return parameter;
	}

	/**
	 * 设置grid的值
	 * @author 045738-foss-maojianqiang
	 * @throws Exception 
	 * @date 2012-11-3 下午12:53:40
	 * @see com.deppon.foss.print.jasper.JasperDataSource#createDetailDataSource(com.deppon.foss.print.jasper.JasperContext)
	 */
	public List<Map<String, Object>> createDetailDataSource(JasperContext jasperContext) throws Exception {
		//获取前台传入列表
		final String[] columns = (String[]) jasperContext.get("arrayColumns");
		//将数组转化为list
		final List<String> columnsList = Arrays.asList(columns);
		//获取前台传入的对账单号
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		//获取对账单号
		String statementBillNo = (String) jasperContext.get("statementBillNo");
		//获取对账单服务接口
		IStatementOfAccountDService statementOfAccountDService = (IStatementOfAccountDService)jasperContext.getApplicationContext().getBean("statementOfAccountDService");
		//根据对账单号查询明细
		List<StatementOfAccountDEntity> targetList = statementOfAccountDService.queryByStatementBillNo(statementBillNo);
		//获取产品类型
		IProductService productService = (IProductService)jasperContext.getApplicationContext().getBean("productService");
		
		//获取全部有效的第三级别产品类型
		List<ProductEntity> productList = productService.queryLevel3ProductInfo();
		// 生成存储产品类型的map
		Map<String, String> productMap = new HashMap<String, String>();
		// 如果产品类型不为空，循环加入到map中
		if (!CollectionUtils.isEmpty(productList)) {
			for (ProductEntity entity : productList) {
				productMap.put(entity.getCode(), entity.getName());
			}
		}
		
		//声明日期列表
		List<String> dataList = new ArrayList<String>();
		dataList.add("businessDate");//业务日期
		dataList.add("accountDate");//记账日期
		dataList.add("signDate");//签收日期
		dataList.add("disableTime");//失效日期
		dataList.add("createTime");//创建时间
		
		//判断列表是否为空，如果不为空，循环获取打印数据
		if(CollectionUtils.isNotEmpty(targetList)){
			//循环对账单明细类表
			for(final StatementOfAccountDEntity entity:targetList){
				//声明明细map
				final Map<String, Object> map = new HashMap<String, Object>();
				//列标
				int index=0;
				//根据前台传入列来获取对应数据
				for(String columnName:columnsList){
					//根据传入实体属性获取实体field
					Field field = ReflectionUtils.findField(StatementOfAccountDEntity.class, columnName);
					//非空判断
					if(field != null){
						//列标+1，因为打印模板从columns1开始
						index+=1;
						//设置field可以获取value
						ReflectionUtils.makeAccessible(field);
						//获取对应属性的value
						Object fieldValue = ReflectionUtils.getField(field, entity);
						//如果为日期，需要转化
						if(fieldValue!=null && dataList.contains(columnName)){
							//转化日期为y-m-d形式
							String dateToString = dateFormat.format(fieldValue);
							//设置转化后值给fieldValue
							fieldValue = dateToString;
						}
						//来源单号
						if(columnName.equals("sourceBillNo")){
							if(null != entity.getSourceBillNo() && !"".equals(entity.getSourceBillNo())){
								fieldValue = entity.getSourceBillNo().substring(0, SettlementReportNumber.EIGHT) + " " + entity.getSourceBillNo().substring(SettlementReportNumber.EIGHT);
							}
						}
						//运单号
						if(columnName.equals("waybillNo")){
							if(null != entity.getWaybillNo() && !"".equals(entity.getWaybillNo())){
								fieldValue = entity.getWaybillNo().substring(0, SettlementReportNumber.EIGHT) + " " + entity.getWaybillNo().substring(SettlementReportNumber.EIGHT);
							}
						}
						//收货人编码
						if(columnName.equals("receiveCustomerCode")){
							if(null != entity.getReceiveCustomerCode() && !"".equals(entity.getReceiveCustomerCode())){
								fieldValue = entity.getReceiveCustomerCode().substring(0, SettlementReportNumber.EIGHT) + " " + entity.getReceiveCustomerCode().substring(SettlementReportNumber.EIGHT);
							}
						}
						//对账单编号
						if(columnName.equals("statementBillNo")){
							if(null != entity.getStatementBillNo() && !"".equals(entity.getStatementBillNo())){
								fieldValue = entity.getStatementBillNo().substring(0, SettlementReportNumber.EIGHT) + " " + entity.getStatementBillNo().substring(SettlementReportNumber.EIGHT);
							}
						}
						//始发网点编码
						if(columnName.equals("origOrgCode")){
							if(null != entity.getOrigOrgCode() && !"".equals(entity.getOrigOrgCode())){
								fieldValue = entity.getOrigOrgCode().substring(0, SettlementReportNumber.EIGHT) + " " + entity.getOrigOrgCode().substring(SettlementReportNumber.EIGHT);
							}
						}
						//部门编码
						if(columnName.equals("orgCode")){
							if(null != entity.getOrgCode() && !"".equals(entity.getOrgCode())){
								fieldValue = entity.getOrgCode().substring(0, SettlementReportNumber.EIGHT) + " " + entity.getOrgCode().substring(SettlementReportNumber.EIGHT);
							}
						}
						//到达部门编码
						if(columnName.equals("destOrgCode")){
							if(null != entity.getDestOrgCode() && !"".equals(entity.getDestOrgCode())){
								fieldValue = entity.getDestOrgCode().substring(0, SettlementReportNumber.EIGHT) + " " + entity.getDestOrgCode().substring(SettlementReportNumber.EIGHT);
							}
						}
						//发货客户编码
						if(columnName.equals("deliveryCustomerCode")){
							if(null != entity.getDeliveryCustomerCode() && !"".equals(entity.getDeliveryCustomerCode())){
								fieldValue = entity.getDeliveryCustomerCode().substring(0, SettlementReportNumber.EIGHT) + " " + entity.getDeliveryCustomerCode().substring(SettlementReportNumber.EIGHT);
							}
						}
						//原始来源单据编号
						if(columnName.equals("origSourceBillNo")){
							if(null != entity.getOrigSourceBillNo() && !"".equals(entity.getOrigSourceBillNo())){
								fieldValue = entity.getOrigSourceBillNo().substring(0, SettlementReportNumber.EIGHT) + " " + entity.getOrigSourceBillNo().substring(SettlementReportNumber.EIGHT);
							}
						}
						//如果为数据字典类型，需要转化
						if(fieldValue!=null &&  (columnName.equals("billParentType")
								||columnName.equals("billType")||columnName.equals("paymentType")
								||columnName.equals("productCode")||columnName.equals("receiveMethod")
								||columnName.equals("auditStatus"))){
							//将单据父类型继续转化
							if(columnName.equals("billParentType")){
								fieldValue = DictUtil.rendererSubmitToDisplay(fieldValue.toString(),DictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE);
							//付款方式转化
							}else if(columnName.equals("paymentType")){
								fieldValue = DictUtil.rendererSubmitToDisplay(fieldValue.toString(),DictionaryConstants.SETTLEMENT__PAYMENT_TYPE);
							//单据子类型转化，需要根据不同单据父类型调用不同数据字典
							}else if(columnName.equals("billType")){
								//应收单
								if(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__RECEIVABLE.equals(entity.getBillParentType())){
									fieldValue = DictUtil.rendererSubmitToDisplay(fieldValue.toString(),DictionaryConstants.BILL_RECEIVABLE__BILL_TYPE);	
								//应付单	
								}else if(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__PAYABLE.equals(entity.getBillParentType())){
									fieldValue = DictUtil.rendererSubmitToDisplay(fieldValue.toString(),DictionaryConstants.BILL_PAYABLE__BILL_TYPE);
								//预收单	
								}else if(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__DEPOSIT_RECEIVED.equals(entity.getBillParentType())){
									fieldValue = DictUtil.rendererSubmitToDisplay(fieldValue.toString(),DictionaryConstants.BILL_DEPOSIT_RECEIVED__BILL_TYPE);
								//预付单	
								}else if(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__ADVANCED_PAYMENT.equals(entity.getBillParentType())){
									fieldValue = DictUtil.rendererSubmitToDisplay(fieldValue.toString(),DictionaryConstants.BILL_ADVANCED_PAYMENT__BILL_TYPE);
								}
							}else if(columnName.equals("productCode")){
								fieldValue =productMap.get(fieldValue).toString();
							}//提货方式
							else if(columnName.equals("receiveMethod")){
								String receiveMethod = fieldValue.toString();
								//先去汽运提货方式词条中拿
								fieldValue = DictUtil.rendererSubmitToDisplay(receiveMethod, DictionaryConstants.PICKUP_GOODS);
								//如果汽运提货方式没拿到，则去空运词条中拿
								if(receiveMethod==fieldValue){
									fieldValue = DictUtil.rendererSubmitToDisplay(receiveMethod, DictionaryConstants.PICKUP_GOODS_AIR);
								}
							}else if(columnName.equals("auditStatus")){
								fieldValue = DictUtil.rendererSubmitToDisplay(fieldValue.toString(),DictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS);
							}
						}
						//封装列字段
						map.put("columnName"+index,fieldValue);
					}
				}
				list.add(map);
			} 
		}else{
			Map<String, Object> map = new HashMap<String, Object>();
			list.add(map);
		}
		return list;
	}
}
