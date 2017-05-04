package com.deppon.foss.module.settlement.writeoff.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.ReflectionUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.components.export.pdf.PDFExport;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountDService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountDEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IStatementMakeService;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IStatementModifyService;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IStatementWriteoffService;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.StatementOfAccountMakeQueryDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.StatementOfAccountMakeQueryResultDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.StatementToPaymentResultDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.vo.StatementOfAccountMakeQueryResultVo;
import com.deppon.foss.module.settlement.writeoff.api.shared.vo.StatementOfAccountMakeQueryVo;
import com.deppon.foss.util.DateUtils;

/**
 * 修改对账单
 * 
 * @author 045738-foss-maojianqiang
 * @date 2012-10-20 下午3:06:16
 */
public class StatementModifyAction extends AbstractAction {
	/**
	 * 声明日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(StatementModifyAction.class);
	/**
	 * 声明导出pdf名称
	 */
	private static final String PDFNAME = "对账单";
	/**
	 * 声明导出excel的名称
	 */
	private static final String EXCELXMLNAME = "对账单本期明细";
	private static final String EXCELNAME = "对账单";
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -7543884281922558713L;
	
	/** 
	 * excel名称 
	 **/
	private String excelName;

	/** 
	 * excel导出流
	 **/
	private InputStream inputStream;

	/**
	 * 制作对账单查询结果（对账单及对账单明细单据）
	 */
	private StatementOfAccountMakeQueryResultVo statementOfAccountMakeQueryResultVo = new StatementOfAccountMakeQueryResultVo();

	/**
	 * 前台查询参数
	 */
	private StatementOfAccountMakeQueryVo statementOfAccountMakeQueryVo = new StatementOfAccountMakeQueryVo();

	/**
	 * 注入statementModifyService
	 */
	private IStatementModifyService statementModifyService;

	/**
	 * 注入statementMakeService
	 */
	private IStatementMakeService statementMakeService;

	/**
	 * 注入statementOfAccountDService
	 */
	private IStatementOfAccountDService statementOfAccountDService;

	/**
	 * 注入核销service
	 */
	private IStatementWriteoffService statementWriteoffService;
	
	/**
	 * 根据产品CODE找对应的名称
	 */
	IProductService  productService;

	/**
	 * 导出pdf的输入流
	 */
	private InputStream pdfStream;

	/**
	 * 导出pdf名称
	 */
	private String fileName;

	/**
	 * 查询对账单
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-10-20 下午3:03:38
	 */
	@JSON
	public String queryStatement() {
		//获取对账单查询dto
		StatementOfAccountMakeQueryDto queryDto = this.statementOfAccountMakeQueryVo.getStatementOfAccountMakeQueryDto();
		try {
			// 给第结束日期加1天
			if (queryDto.getPeriodEndDate() != null) {
				// 将前台结束日期进行+1操作，设置成 第2天的00:00:00
				queryDto.setPeriodEndDate(DateUtils.convert(DateUtils.addDay(queryDto.getPeriodEndDate(), 1),DateUtils.DATE_FORMAT));
			}
			// 获取当前登录用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 将orgCode设置给dto
			queryDto.setOrgCode(currentInfo.getCurrentDeptCode());
			queryDto.setEmpCode(currentInfo.getEmpCode());
			// 调用service方法进行查询
			StatementOfAccountMakeQueryResultDto dto = this.statementModifyService.queryStatement(queryDto,this.getStart(),this.getLimit());
			// 如果没查询到，则new一个防止前台报错
			if (dto == null) {
				// 实例化dto
				dto = new StatementOfAccountMakeQueryResultDto();
			}
			// 将dto设置给vo，将vo返回
			this.statementOfAccountMakeQueryResultVo.setStatementOfAccountMakeQueryResultDto(dto);
			this.setTotalCount(dto.getTotalCount());
			//正常返回
			return returnSuccess();
		//异常处理	
		} catch (BusinessException e) {
			//记录日志
			logger.error(e.getMessage(), e);
			//异常返回
			return returnError(e);
		}
	}

	/**
	 * 根据对账单号查询对账单明细
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-10-30 下午2:12:36
	 */
	@JSON
	public String queryStatementDetailByNumber() {

		try {
			String statementNo = null;
			// 获取对账单号
			if(null != statementOfAccountMakeQueryResultVo){
				StatementOfAccountMakeQueryResultDto dto = statementOfAccountMakeQueryResultVo.getStatementOfAccountMakeQueryResultDto();
				if(null != dto){
					StatementOfAccountEntity soa = dto.getStatementOfAccount();
					if(null != soa){
						statementNo = soa.getStatementBillNo();
					}else{
						throw new SettlementException("获取statementNo对象为空");
					}
				}else{
					throw new SettlementException("获取StatementOfAccountEntity对象为空");
				}
			}else{
				throw new SettlementException("获取statementOfAccountMakeQueryResultVo对象为空");
			}
			// 如果对账单号为空，则抛出异常
			if (StringUtil.isBlank(statementNo)) {
				//提示对账单号不能为空
				throw new SettlementException("对账单号不能为空！");
			}

			// 调用service,查询对账单明细
			List<StatementOfAccountDEntity> statementOfAccountDPeriodList = this.statementOfAccountDService.queryByStatementBillNo(statementNo);
			// 如果明细为null
			if (CollectionUtils.isEmpty(statementOfAccountDPeriodList)) {
				//新建明细信息并传递到前台
				statementOfAccountDPeriodList = new ArrayList<StatementOfAccountDEntity>();
			}
			// 将dto设置给vo，将vo返回
			this.statementOfAccountMakeQueryResultVo.getStatementOfAccountMakeQueryResultDto().setStatementOfAccountDPeriodList(statementOfAccountDPeriodList);
			//正常返回
			return returnSuccess();
		//异常处理	
		} catch (BusinessException e) {
			//记录日志
			logger.error(e.getMessage(), e);
			//异常返回
			return returnError(e);
		}
	}

	/**
	 * 查询期初对账单，添加到对账单明细中
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-10-20 下午5:36:19
	 */
	@JSON
	public String queryStatementForAdd() {
		// 获取前台传入的dto
		StatementOfAccountMakeQueryDto queryDto = this.statementOfAccountMakeQueryVo.getStatementOfAccountMakeQueryDto();
		try {
			// 获取当前登录用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 给第结束日期加1天
			if (queryDto.getPeriodEndDate() != null) {
				// 将前台结束日期进行+1操作，设置成 第2天的00:00:00
				queryDto.setPeriodEndDate(DateUtils.convert(DateUtils.addDay(queryDto.getPeriodEndDate(), 1),DateUtils.DATE_FORMAT));
			}
			
			// 调用serviec，进行查询
			StatementOfAccountMakeQueryResultDto dto = this.statementMakeService.queryForAddDetailBill(queryDto, currentInfo);
			// 如果有结果则将其放入到返回的vo中
			if (dto == null) {
				// 实例化dto
				dto = new StatementOfAccountMakeQueryResultDto();
			}
			// 将dto设置给vo
			this.statementOfAccountMakeQueryResultVo.setStatementOfAccountMakeQueryResultDto(dto);
			//正常返回
			return returnSuccess();
		//异常处理	
		} catch (BusinessException e) {
			//记录日志
			logger.error(e.getMessage(), e);
			//异常返回
			return returnError(e);
		}
	}

	/**
	 * 确认对账单
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-10-19 下午6:08:18
	 */
	@JSON
	public String confirmStatement() {
		// 获取dto
		StatementOfAccountMakeQueryResultDto queryDto = this.statementOfAccountMakeQueryResultVo.getStatementOfAccountMakeQueryResultDto();
		// 获取当前操作时要进行确认还是反确认动作
		String confirmStatus = this.statementOfAccountMakeQueryResultVo.getConfirmStatus();
		String applyInvoice = this.statementOfAccountMakeQueryResultVo.getApplyInvoice();
		try {
			// 获取当前登录用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 判断进行的确认还是反确认操作
			if (StringUtil.isBlank(confirmStatus)) {
				//提示确认类型不能为空
				throw new SettlementException("确认类型不能为空!");
			}
			
			// 如果为确认操作，则调用确认接口
			if (confirmStatus.equals(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__CONFIRM)) {
				for(int i = 0; i < queryDto.getStatementOfAccountList().size(); i++){
					queryDto.getStatementOfAccountList().get(i).setApplyInvoice(applyInvoice);
					if(applyInvoice.equals(SettlementDictionaryConstants.SETTLEMENT_INVOICE_APPLY_INVOICE_YES)){
						queryDto.getStatementOfAccountList().get(i).setInvoiceStatus(SettlementDictionaryConstants.SETTLEMENT_INVOICE_STATUS_NO_APPLY);
					}else{
						queryDto.getStatementOfAccountList().get(i).setInvoiceStatus(null);
					}
				}
				// 调用接口进行确认操作
				statementModifyService.confirmStatement(queryDto, currentInfo);
			} else {
				for(int i = 0; i < queryDto.getStatementOfAccountList().size(); i++){
					queryDto.getStatementOfAccountList().get(i).setApplyInvoice(null);
					queryDto.getStatementOfAccountList().get(i).setInvoiceStatus(null);
				}
				// 调用接口进行反确认操作
				statementModifyService.unConfirmStatement(queryDto, currentInfo);
			}
			//正常返回
			return returnSuccess();
		//异常处理	
		} catch (BusinessException e) {
			//记录日志
			logger.error(e.getMessage(), e);
			//异常返回
			return returnError(e);
		}
	}

	public String applyStatement(){
		// 获取dto
		StatementOfAccountMakeQueryResultDto queryDto = this.statementOfAccountMakeQueryResultVo.getStatementOfAccountMakeQueryResultDto();
		// 获取当前操作时要进行确认还是反确认动作
		String invoiceStatus = this.statementOfAccountMakeQueryResultVo.getInvoiceStatus();
		try {
			// 获取当前登录用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 判断进行的确认还是反确认操作
			if (StringUtil.isBlank(invoiceStatus)) {
				//提示确认类型不能为空
				throw new SettlementException("发票状态不能为空!");
			}
			// 如果为确认操作，则调用确认接口
			if (invoiceStatus.equals(SettlementDictionaryConstants.SETTLEMENT_INVOICE_STATUS_APPLIED)) {
				// 调用接口进行确认操作
				statementModifyService.appliedStatement(queryDto, currentInfo);
			} else {
				// 调用接口进行反确认操作
				statementModifyService.notApplyStatement(queryDto, currentInfo);
			}
			//正常返回
			return returnSuccess();
		//异常处理	
		} catch (BusinessException e) {
			//记录日志
			logger.error(e.getMessage(), e);
			//异常返回
			return returnError(e);
		}
	}
	
	/**
	 * 添加对账单明细
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-10-20 下午3:05:14
	 */
	@JSON
	public String addStatementDetail() {
		//获取对账单查询制作dto
		StatementOfAccountMakeQueryResultDto queryDto = this.statementOfAccountMakeQueryResultVo.getStatementOfAccountMakeQueryResultDto();
		try {
			// 获取当前登录用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 调用service进行
			StatementOfAccountMakeQueryResultDto dto = this.statementModifyService.addStatementDetail(queryDto, currentInfo);
			// 如果有结果则将其放入到返回的vo中
			if (dto == null) {
				//如果没有结果则将新建dto
				dto = new StatementOfAccountMakeQueryResultDto();
			}
			// 将dto设置给vo,将vo返回
			this.statementOfAccountMakeQueryResultVo.setStatementOfAccountMakeQueryResultDto(dto);
			//正常返回
			return returnSuccess();
		//异常处理	
		} catch (BusinessException e) {
			//记录日志
			logger.error(e.getMessage(), e);
			//异常返回
			return returnError(e);
		}
	}

	/**
	 * 删除对账单明细
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-10-19 下午6:16:32
	 */
	@JSON
	public String deleteStatementEntry() {
		// 获取前台传入dto
		StatementOfAccountMakeQueryResultDto queryDto = this.statementOfAccountMakeQueryResultVo.getStatementOfAccountMakeQueryResultDto();
		try {
			// 获取当前登录用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 调用service进行
			StatementOfAccountMakeQueryResultDto dto = this.statementModifyService.deleteStatementDetail(queryDto, currentInfo);
			// 如果有结果则将其放入到返回的vo中
			if (dto == null) {
				//新建对账单制作dto
				dto = new StatementOfAccountMakeQueryResultDto();
			}
			// 将dto设置给vo,将vo返回
			this.statementOfAccountMakeQueryResultVo.setStatementOfAccountMakeQueryResultDto(dto);
			//正常返回
			return returnSuccess();
		//异常处理	
		} catch (BusinessException e) {
			//记录日志
			logger.error(e.getMessage(), e);
			//异常返回
			return returnError(e);
		}
	}

	/**
	 * 核销/批量核销
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-10-20 下午3:06:21
	 */
	@JSON
	public String batchWriteoffStatement() {
		//获取对账单制作dto
		StatementOfAccountMakeQueryResultDto queryDto = this.statementOfAccountMakeQueryResultVo.getStatementOfAccountMakeQueryResultDto();
		try {
			// 获取当前登录用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 调用service进行
			StatementOfAccountMakeQueryResultDto dto = statementWriteoffService.writeoffStatement(queryDto, currentInfo);
			// 如果没查询到则实例化该对象
			if (dto == null) {
				//新建对账单制作dto
				dto = new StatementOfAccountMakeQueryResultDto();
			}
			// 将dto设置给vo,将vo返回
			this.statementOfAccountMakeQueryResultVo.setStatementOfAccountMakeQueryResultDto(dto);
			//正常返回
			return returnSuccess();
		//异常处理	
		} catch (BusinessException e) {
			//记录日志
			logger.error(e.getMessage(), e);
			//异常返回
			return returnError(e);
		}
	}

	/**
	 * 
	 * 还款/批量还款
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-11 下午7:39:08
	 */
	@JSON
	public String repayment() {
		try {
			// 获取传入参数
			StatementOfAccountMakeQueryResultDto queryDto = this.statementOfAccountMakeQueryResultVo.getStatementOfAccountMakeQueryResultDto();
			// 判断传入数据是否为空，如果没传入则抛出异常
			if (queryDto == null|| CollectionUtils.isEmpty(queryDto.getStatementOfAccountList())) {
				//提示还款时输入参数为空
				throw new SettlementException("还款时输入参数为空!");
			} 
			// 获取当前登录用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();

			// 调用批量还款
			StatementToPaymentResultDto rtnDto = this.statementModifyService.repaymentBatch(queryDto, currentInfo);

			// 设置返回参数
			this.statementOfAccountMakeQueryResultVo.setStatementToPaymentResultDto(rtnDto);

			//正常返回
			return returnSuccess();
		//异常处理	
		} catch (BusinessException e) {
			//记录日志
			logger.error(e.getMessage(), e);
			//异常返回
			return returnError(e);
		}
	}

	/**
	 * 导出对账单
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-10-19 下午6:10:12
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String exportStatement() {
		try {
			// 获取mapper,进行前台转换
			ObjectMapper mapper = new ObjectMapper();
			// 将前台出入json字符串转化为对象
			StatementOfAccountEntity entity = mapper.readValue((String) statementOfAccountMakeQueryResultVo.getStatementofAccountStr(),StatementOfAccountEntity.class);
			// 获取类头名称
			String[] header = statementOfAccountMakeQueryResultVo.getArrayColumns();
			// 获取列头中文名
			String[] headerName = statementOfAccountMakeQueryResultVo.getArrayColumnNames();
			// 获取导出类型
			String type = statementOfAccountMakeQueryResultVo.getExportType();
			// 放界面对账单信息
			Map<String, Object> map = new HashMap<String, Object>();
			// 获取当前路径
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			// 设置单据类型
			map.put("billType", DictUtil.rendererSubmitToDisplay(entity.getBillType(), DictionaryConstants.STATEMENT_OF_ACCOUNT__BILL_TYPE));
			// 对创建时间进行转化
			if (entity.getCreateTime() != null) {
				// 对日期进行格式化
				map.put("createTime", DateUtils.convert(entity.getCreateTime()));
			}
			// 收货人
			map.put("customerCode", entity.getCustomerCode());
			// 确认状态进行转化
			map.put("confirmStatus", DictUtil.rendererSubmitToDisplay(entity.getConfirmStatus(),DictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS));
			// 判断日期是否为空
			if (entity.getPeriodBeginDate() != null) {
				// 对日期进行格式化
				map.put("periodBeginDate",DateUtils.convert(entity.getPeriodBeginDate()));
			}
			// 设置公司名称
			map.put("companyName", entity.getCompanyName());
			// 设置客户名称
			map.put("customerName", entity.getCustomerName());
			// 判断确认时间
			if (entity.getConfirmTime() != null) {
				// 对日期进行格式化
				map.put("confirmTime",DateUtils.convert(entity.getConfirmTime()));
			}
			// 判断结束日期
			if (entity.getPeriodEndDate() != null) {
				// 对日期进行格式化
				map.put("periodEndDate",DateUtils.convert(entity.getPeriodEndDate()));
			}
			// 判断对账单号是否为null
			if (StringUtil.isBlank(entity.getStatementBillNo())) {
				//提示对账单号不能为空
				throw new Exception("对账单号不能为空");
			}
			// 设置部门名称
			map.put("createOrgName", entity.getCreateOrgName());
			// 设置对账单号
			map.put("statementBillNo", entity.getStatementBillNo());
			// 设置部门标杆编码
			map.put("unifiedCode", entity.getUnifiedCode());
			// 设置银行账号
			map.put("companyAccountBankNo", entity.getCompanyAccountBankNo());
			// 设置开户行
			map.put("accountUserName", entity.getAccountUserName());
			// 设置开户行支行名称
			map.put("bankBranchName", entity.getBankBranchName());
			// 设置本期剩余未还金额
			map.put("unpaidAmount", entity.getUnpaidAmount());
			// 设置结账次数
			map.put("settleNum", entity.getSettleNum());
			// 设置本期金额
			map.put("periodAmount", entity.getPeriodAmount());
			// 设置本期应收金额
			map.put("periodRecAmount", entity.getPeriodRecAmount());
			// 设置本期应付金额
			map.put("periodPayAmount", entity.getPeriodPayAmount());
			// 设置本期预付金额
			map.put("periodAdvAmount", entity.getPeriodAdvAmount());
			// 设置本期预收额
			map.put("periodPreAmount", entity.getPeriodPreAmount());
			// 判断本期金额是否为0
			if (entity.getPeriodAmount() != null&& entity.getPeriodAmount().compareTo(BigDecimal.ZERO) != 0) {
				// 设置本期明细
				String periodDetail = "";
				// 获取文件读取类
				PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
				// 声明图片位置
				Resource resource = resolver.getResource("com/deppon/foss/module/settlement/writeoff/server/META-INF/images/sumDetail.png");
				// 按公司编号查询对应对账单章
				String chapterUrl = statementModifyService.queryStatementChapter(entity.getCompanyCode());
				// ddw
				Resource companyNameImg = resolver.getResource(chapterUrl);
				// 将图片放到map中
				if (resource != null) {
					map.put("subDetailImg", resource.getInputStream());
				}
				// 将图片放到map中
				if (companyNameImg != null) {
					map.put("companyNameImg", companyNameImg.getInputStream());
				}
				// 获取应收
				if (entity.getPeriodUnverifyRecAmount().compareTo(BigDecimal.ZERO) != 0) {
					periodDetail = periodDetail + "应收："+ entity.getPeriodUnverifyRecAmount().toString()+ "  ";
				}
				// 获取应付金额
				if (entity.getPeriodUnverifyPayAmount().compareTo(BigDecimal.ZERO) != 0) {
					periodDetail = periodDetail + "应付："+ entity.getPeriodUnverifyPayAmount().toString()+ "  ";
				}
				// 获取预收金额
				if (entity.getPeriodUnverifyPreAmount().compareTo(BigDecimal.ZERO) != 0) {
					periodDetail = periodDetail + "预收："+ entity.getPeriodUnverifyPreAmount().toString()+ "  ";
				}
				// 获取预付金额
				if (entity.getPeriodUnverifyAdvAmount().compareTo(BigDecimal.ZERO) != 0) {
					periodDetail = periodDetail + "预付："+ entity.getPeriodUnverifyAdvAmount().toString()+ "  ";
				}
				// 本期明细
				if (StringUtils.isNotBlank(periodDetail)) {
					// 设置本期明细
					map.put("periodDetail", periodDetail);
				}
			}
			// 声明列标
			int flag = 0;
			// 拼接列头
			for (int i = 0; i < headerName.length; i++) {
				flag += 1;
				// 设置列标
				map.put("columnName" + flag, headerName[i]);
			}
			// 如果是从对账单导出，明细需要从后台查询，反之从界面获取
			if (StringUtil.isNotBlank(type) && type.equals("byStatement")) {
				// 根据对账单号，查询对账单明细
				List<StatementOfAccountDEntity> targetList = this.statementOfAccountDService.queryByStatementBillNo(entity.getStatementBillNo());
				// 判断对账单明细是否为空
				if (CollectionUtils.isNotEmpty(targetList)) {
					// 循环对账单明细
					for (final StatementOfAccountDEntity dEntity : targetList) {
						// 声明打印模板对应表格的一行数据
						final Map<String, Object> listMap = new HashMap<String, Object>();
						// 列标
						int index = 0;
						// 根据前台传入列来获取对应数据
						for (String columnName : header) {
							// 根据传入对账单明细的属性名称，根据反射，获取其属性对应的值
							Field field = ReflectionUtils.findField(StatementOfAccountDEntity.class,columnName);
							// 判断值是否为空
							if (field != null) {
								index += 1;
								// 设置field允许获取值
								ReflectionUtils.makeAccessible(field);
								// 根据属性名获取当前循环的实体的对应值
								Object fieldValue = ReflectionUtils.getField(field, dEntity);
								// 如果为日期，需要转化
								fieldValue = validaOfAccount(dEntity,
										columnName, fieldValue);
								// 将当前字段放置到行map中
								listMap.put("columnName" + index, fieldValue);
							}
						}
						// 将行map放置到表map中
						list.add(listMap);
					}
				} else {
					// 声明行listMap，没有数据
					Map<String, Object> listMap = new HashMap<String, Object>();
					//加入到list
					list.add(listMap);
				}
			} else {
				// 对账单导出，明细从界面获取
				List<LinkedHashMap> targetList = mapper.readValue((String) statementOfAccountMakeQueryResultVo.getStatementofAccountDetailStr(), List.class);
				// 循环获取界面传入数据，进行转化
				for (LinkedHashMap l : targetList) {
					Map<String, Object> listMap = new HashMap<String, Object>();
					// 声明列标
					int index = 0;
					// 循环列
					validaListMap(header, l, listMap, index);
					// 将行map放置到表map中
					list.add(listMap);
				}
			}
			// 声明文件名称
			fileName = new String((PDFNAME).getBytes(SettlementConstants.UNICODE_GBK),SettlementConstants.UNICODE_ISO);
			// 进行流导出操作
			pdfStream = PDFExport.exportPDF("settlement/writeoff","statementbill", map, list);
			//正常返回
			return returnSuccess();
		//异常处理	
		} catch (Exception e) {
			//记录日志
			logger.error(e.getMessage(), e);
			//异常返回
			return ERROR;
		}
	}

	private Object validaOfAccount(final StatementOfAccountDEntity dEntity,
			String columnName, Object fieldValue) {
		if (fieldValue != null) {
			// 声明日期列表
			List<String> dataList = new ArrayList<String>();
			// 声明数据字典字段列表
			List<String> dictList = new ArrayList<String>();
			dataList.add("businessDate");// 业务日期
			dataList.add("accountDate");// 记账日期
			dataList.add("signDate");// 签收日期
			dataList.add("disableTime");// 失效日期
			dataList.add("createTime");// 创建时间
			dictList.add("billParentType");// 单据父类型
			dictList.add("billType");// 单据子类型
			dictList.add("paymentType");// 付款方式
			dictList.add("productCode");// 产品类型
			dictList.add("receiveMethod");//提货方式
			dictList.add("auditStatus");//审核状态

			// 如果当前字段为日期，则进行格式化操作
			if (dataList.contains(columnName)) {
				// 转化日期为y-m-d形式
				String dateToString = DateUtils.convert((Date) fieldValue,"yyyy-MM-dd");
				// 重置fieldValue值
				fieldValue = dateToString;
			}
			// 如果为数据字典类型，需要转化
			if (dictList.contains(columnName)) {
				// 进行数据转化
				fieldValue = exprotPDFConvert(fieldValue.toString(),columnName,dEntity.getBillParentType());
			}
		}
		return fieldValue;
	}

	private void validaListMap(String[] header, LinkedHashMap l,
			Map<String, Object> listMap, int index) {
		for (String columnName : header) {
			index += 1;
			// 获取对应单元格的值
			Object value = l.get(columnName);
			// 如果为日期，需要转化
			if (value != null) {
				// 声明日期列表
				List<String> dataList = new ArrayList<String>();
				// //声明数据字典字段列表
				List<String> dictList = new ArrayList<String>();
				dictList.add("billParentType");// 单据父类型
				dictList.add("billType");// 单据子类型
				dictList.add("paymentType");// 单据父类型
				dictList.add("productCode");// 单据父类型
				dictList.add("receiveMethod");//提货方式
				dictList.add("auditStatus");//审核状态
				dataList.add("businessDate");// 业务日期
				dataList.add("accountDate");// 记账日期
				dataList.add("signDate");// 签收日期
				dataList.add("disableTime");// 失效日期
				dataList.add("createTime");// 创建时间
				// 如果为日期，则要进行转化
				if (dataList.contains(columnName)) {
					// 将long转为日期
					Date date = new Date((Long) value);
					// 转化日期为y-m-d形式
					String dateToString = DateUtils.convert(date,"yyyy-MM-dd");
					// 重置value的值
					value = dateToString;
				}

				// 获取单据父类型
				String parentType = (String) l.get("billParentType");
				// 如果为数据字典类型，需要转化
				if (dictList.contains(columnName)) {
					// 进行数据转化
					value = exprotPDFConvert(value.toString(),columnName, parentType);
				}
			}
			// 将当前字段放置到行map中
			listMap.put("columnName" + index, value);
		}
	}

	/**
	 * 导出pdf数据转化
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-17 上午10:54:09
	 */
	private String exprotPDFConvert(String fieldValue, String columnName,String parentType) {
		// 单据父类型进行转化
		if (columnName.equals("billParentType")) {
			fieldValue = DictUtil.rendererSubmitToDisplay(fieldValue.toString(),DictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE);
			// 付款方式进行转化
		} else if (columnName.equals("paymentType")) {
			//数据字典转换
			fieldValue = DictUtil.rendererSubmitToDisplay(fieldValue.toString(), DictionaryConstants.SETTLEMENT__PAYMENT_TYPE);
			// 单据子类型进行转化
		} else if (columnName.equals("billType")&& StringUtils.isNotBlank(parentType)) {
			// 应收单
			if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__RECEIVABLE.equals(parentType)) {
				//数据字典转换
				fieldValue = DictUtil.rendererSubmitToDisplay(fieldValue.toString(), DictionaryConstants.BILL_RECEIVABLE__BILL_TYPE);
				// 应付单
			} else if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__PAYABLE.equals(parentType)) {
				//数据字典转换
				fieldValue = DictUtil.rendererSubmitToDisplay(fieldValue.toString(), DictionaryConstants.BILL_PAYABLE__BILL_TYPE);
				// 预收单
			} else if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__DEPOSIT_RECEIVED.equals(parentType)) {
				//数据字典转换
				fieldValue = DictUtil.rendererSubmitToDisplay(fieldValue.toString(),DictionaryConstants.BILL_DEPOSIT_RECEIVED__BILL_TYPE);
				// 预付单
			} else if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__ADVANCED_PAYMENT.equals(parentType)) {
				//数据字典转换
				fieldValue = DictUtil.rendererSubmitToDisplay(fieldValue.toString(),DictionaryConstants.BILL_ADVANCED_PAYMENT__BILL_TYPE);
			}
		//产品类型
		}else if(columnName.equals("productCode")){
			//通过产品code 获取产品名称,从缓存里面取
			ProductEntity productEntity=null;
			if(StringUtils.isNotEmpty(fieldValue.toString())){
				productEntity=productService.getProductByCache(fieldValue.toString(),new Date());
			}
			if(null!=productEntity){
				//产品名称				
				fieldValue = productEntity.getName();
			}
		//提货方式
		}else if(columnName.equals("receiveMethod")){
			String receiveMethod = fieldValue.toString();
			//先去汽运提货方式词条中拿
			fieldValue = DictUtil.rendererSubmitToDisplay(receiveMethod, DictionaryConstants.PICKUP_GOODS);
			//如果汽运提货方式没拿到，则去空运词条中拿
			if(fieldValue==null ||receiveMethod==fieldValue){
				fieldValue = DictUtil.rendererSubmitToDisplay(receiveMethod, DictionaryConstants.PICKUP_GOODS_AIR);
			}
		//审核状态 
		}else if(columnName.equals("auditStatus")){
			fieldValue = DictUtil.rendererSubmitToDisplay(fieldValue.toString(),DictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS);
		}
		//返回值
		return fieldValue;
	}
	
	/**
	 * 
	 * 导出对账单明细excel
	 * @author 045738-foss-maojianqiang
	 * @date 2013-6-17 下午7:17:04
	 * @return
	 */
	public String exportStatementDetail(){
		// 查询参数不能为空
		if (statementOfAccountMakeQueryResultVo == null) {
			return returnError("导出参数不能为空!");
		}
		// 获取类头名称
		String[] header = statementOfAccountMakeQueryResultVo.getArrayColumns();
		// 获取列头中文名
		String[] headerName = statementOfAccountMakeQueryResultVo.getArrayColumnNames();
		//获取对账单号 
		String statementNo = statementOfAccountMakeQueryResultVo.getStatementNo();
		try {

			// 设置导出excel名称
			try {
				this.setExcelName(new String((EXCELXMLNAME).getBytes(SettlementConstants.UNICODE_GBK),SettlementConstants.UNICODE_ISO));
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(), e);
				return returnError("导出Excel失败");
			}

			// 调用service,查询对账单明细
			List<StatementOfAccountDEntity> statementOfAccountDPeriodList = this.statementOfAccountDService.queryByStatementBillNo(statementNo);

			// 导出格式数据
			ExportResource exportResource = packExportResource(statementOfAccountDPeriodList,header,headerName);

			// 创建导出表头对象
			ExportSetting exportSetting = new ExportSetting();
			// 设置名称
			exportSetting.setSheetName(SettlementConstants.EXCEL_SHEET_NAME);
			
			// 创建导出工具类
			ExporterExecutor executor = new ExporterExecutor();

			// 输出到导出流中
			inputStream = executor.exportSync(exportResource, exportSetting);

		} catch (BusinessException e) {
			// 记录日志并返回失败
			logger.error(e.getMessage(),e);
			return returnError("导出对账单明细异常:" + e.getMessage());

		}

		return returnSuccess();
	}
	
	/**
	 * 封装对账单明细excel
	 * @author 045738-foss-maojianqiang
	 * @date 2013-6-18 上午8:48:42
	 * @param statementOfAccountDPeriodList
	 * @param header
	 * @param headerName
	 * @return
	 */
	private ExportResource packExportResource(List<StatementOfAccountDEntity> dList,String[] header,String[] headerName){
		//声明字典集合
		List<String> termCodes = new ArrayList<String>();
		termCodes.add(DictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE);//单据父类型
		termCodes.add(DictionaryConstants.SETTLEMENT__PAYMENT_TYPE);//支付方式
		termCodes.add(DictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS);//支付方式
		termCodes.add(DictionaryConstants.PICKUP_GOODS);//提货方式
		termCodes.add(DictionaryConstants.PICKUP_GOODS_AIR);//提货方式
		termCodes.add(DictionaryConstants.BILL_RECEIVABLE__BILL_TYPE);//应收单
		termCodes.add(DictionaryConstants.BILL_PAYABLE__BILL_TYPE	);//应付单
		termCodes.add(DictionaryConstants.BILL_DEPOSIT_RECEIVED__BILL_TYPE);//预收单
		termCodes.add(DictionaryConstants.BILL_ADVANCED_PAYMENT__BILL_TYPE);//预付单
		//获取对账单要用到的所有数据字典 
		Map<String,Map<String,Object>> termMaps = SettlementUtil.getDataDictionaryByTermsCodes(termCodes);
		
		//获取全部有效的第三级别产品类型
		List<ProductEntity> productList = productService
				.queryLevel3ProductInfo();
		// 生成存储产品类型的map
		Map<String, String> productMap = new HashMap<String, String>();
		// 如果产品类型不为空，循环加入到map中
		if (!CollectionUtils.isEmpty(productList)) {
			for (ProductEntity entity : productList) {
				productMap.put(entity.getCode(), entity.getName());
			}
		}
		// 导出excel数据
		ExportResource data = new ExportResource();
		// 声明sheetList
		List<List<String>> sheetList = new ArrayList<List<String>>();
		// 循环进行封装
		for (StatementOfAccountDEntity entity : dList) {
			// 声明一行的rowList
			List<String> rowList = new ArrayList<String>();
			for (String columnName : header) {
				// 通过名称获取field
				Field field = ReflectionUtils.findField(
						StatementOfAccountDEntity.class, columnName);
				if (field != null) {
					// 通过传入字段获取值
					ReflectionUtils.makeAccessible(field);
					Object fieldValue = ReflectionUtils.getField(field, entity);
					// 如果为日期，需要进行格式化
					if (Date.class.equals(field.getType())&& fieldValue != null) {
						//日期转化
						fieldValue = DateUtils.convert((Date) fieldValue,DateUtils.DATE_TIME_FORMAT);
					}
					// 将字段封装到list中
					if (fieldValue != null) {
						// 单据父类型进行转化
						if (columnName.equals("billParentType")) {
							fieldValue =SettlementUtil.getDataDictionaryByTermsName(termMaps,DictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE, fieldValue.toString());
							// 付款方式进行转化
						} else if (columnName.equals("paymentType")) {
							//数据字典转换
							fieldValue = SettlementUtil.getDataDictionaryByTermsName(termMaps,DictionaryConstants.SETTLEMENT__PAYMENT_TYPE, fieldValue.toString());
							// 单据子类型进行转化
						} else if (columnName.equals("billType")&& StringUtils.isNotBlank(entity.getBillParentType())) {
							//声明单据父类型
							String parentType = entity.getBillParentType();
							// 应收单
							fieldValue = validaMap(termMaps, fieldValue,
									parentType);
						//产品类型
						}else if(columnName.equals("productCode")){
							//如果数据产品类型编码不为空
							if(StringUtils.isNotEmpty(fieldValue.toString())){
								//将产品类型转换编码为名称
								fieldValue=productMap.get(fieldValue.toString());
							}
						//提货方式
						}else if(columnName.equals("receiveMethod")){
							String receiveMethod = fieldValue.toString();
							//先去汽运提货方式词条中拿
							fieldValue = SettlementUtil.getDataDictionaryByTermsName(termMaps,DictionaryConstants.PICKUP_GOODS, receiveMethod);
							//如果汽运提货方式没拿到，则去空运词条中拿
							if(fieldValue==null ||receiveMethod==fieldValue){
								fieldValue = SettlementUtil.getDataDictionaryByTermsName(termMaps,DictionaryConstants.PICKUP_GOODS_AIR,receiveMethod);
							}
						//审核状态 
						}else if(columnName.equals("auditStatus")){
							fieldValue = SettlementUtil.getDataDictionaryByTermsName(termMaps,DictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS, fieldValue.toString());
						}
						
						//设置属性值
						if(fieldValue!=null){
							rowList.add(fieldValue.toString());
						}else{
							rowList.add(null);
						}
					} else {
						rowList.add(null);
					}
				}
			}
			sheetList.add(rowList);
		}
		//封装数据
		data.setRowList(sheetList);
		data.setHeads(headerName);
		return data;
	}

	private Object validaMap(Map<String, Map<String, Object>> termMaps,
			Object fieldValue, String parentType) {
		if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__RECEIVABLE.equals(parentType)) {
			//数据字典转换
			fieldValue = SettlementUtil.getDataDictionaryByTermsName(termMaps,DictionaryConstants.BILL_RECEIVABLE__BILL_TYPE, fieldValue.toString());
			// 应付单
		} else if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__PAYABLE.equals(parentType)) {
			//数据字典转换
			fieldValue = SettlementUtil.getDataDictionaryByTermsName(termMaps,DictionaryConstants.BILL_PAYABLE__BILL_TYPE, fieldValue.toString());
			// 预收单
		} else if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__DEPOSIT_RECEIVED.equals(parentType)) {
			//数据字典转换
			fieldValue = SettlementUtil.getDataDictionaryByTermsName(termMaps,DictionaryConstants.BILL_DEPOSIT_RECEIVED__BILL_TYPE, fieldValue.toString());
			// 预付单
		} else if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__ADVANCED_PAYMENT.equals(parentType)) {
			//数据字典转换
			fieldValue = SettlementUtil.getDataDictionaryByTermsName(termMaps,DictionaryConstants.BILL_ADVANCED_PAYMENT__BILL_TYPE, fieldValue.toString());
		}
		return fieldValue;
	}
	
	public String exportStatementXLS(){
		// 查询参数不能为空
		if (statementOfAccountMakeQueryResultVo == null) {
			return returnError("导出参数不能为空!");
		}
		// 获取类头名称
		String[] header = statementOfAccountMakeQueryResultVo.getArrayColumns();
		// 获取列头中文名
		String[] headerName = statementOfAccountMakeQueryResultVo.getArrayColumnNames();
		try {

			// 设置导出excel名称
			try {
				this.setExcelName(new String((EXCELNAME).getBytes(SettlementConstants.UNICODE_GBK),SettlementConstants.UNICODE_ISO));
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(), e);
				return returnError("导出Excel失败");
			}
			StatementOfAccountMakeQueryDto queryDto = this.statementOfAccountMakeQueryVo.getStatementOfAccountMakeQueryDto();
			// 给第结束日期加1天
			if (queryDto.getPeriodEndDate() != null) {
				// 将前台结束日期进行+1操作，设置成 第2天的00:00:00
				queryDto.setPeriodEndDate(DateUtils.convert(DateUtils.addDay(queryDto.getPeriodEndDate(), 1),DateUtils.DATE_FORMAT));
			}
			// 获取当前登录用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 将orgCode设置给dto
			queryDto.setOrgCode(currentInfo.getCurrentDeptCode());
			queryDto.setEmpCode(currentInfo.getEmpCode());
			StatementOfAccountMakeQueryResultDto dto = this.statementModifyService.queryStatementXLS(queryDto);
			// 调用service,查询对账单明细
			List<StatementOfAccountEntity> statementOfAccountPeriodList = dto.getStatementOfAccountList();

			// 导出格式数据
			ExportResource exportResource = packExportResourceXLS(statementOfAccountPeriodList,header,headerName);

			// 创建导出表头对象
			ExportSetting exportSetting = new ExportSetting();
			// 设置名称
			exportSetting.setSheetName(SettlementConstants.EXCEL_SHEET_NAME);
			
			// 创建导出工具类
			ExporterExecutor executor = new ExporterExecutor();

			// 输出到导出流中
			inputStream = executor.exportSync(exportResource, exportSetting);

		} catch (BusinessException e) {
			// 记录日志并返回失败
			logger.error(e.getMessage(),e);
			return returnError("导出对账单异常:" + e.getMessage());

		}

		return returnSuccess();
	}
	
	private ExportResource packExportResourceXLS(List<StatementOfAccountEntity> dList,String[] header,String[] headerName){
		//声明字典集合
		List<String> termCodes = new ArrayList<String>();
		termCodes.add(DictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS);//对账单确认状态
		termCodes.add(DictionaryConstants.STATEMENT_OF_ACCOUNT__BILL_TYPE);//对账单类型
		//获取对账单要用到的所有数据字典 
		Map<String,Map<String,Object>> termMaps = SettlementUtil.getDataDictionaryByTermsCodes(termCodes);
		// 导出excel数据
		ExportResource data = new ExportResource();
		// 声明sheetList
		List<List<String>> sheetList = new ArrayList<List<String>>();
		// 循环进行封装
		for (StatementOfAccountEntity entity : dList) {
			// 声明一行的rowList
			List<String> rowList = new ArrayList<String>();
			for (String columnName : header) {
				// 通过名称获取field
				Field field = ReflectionUtils.findField(StatementOfAccountEntity.class, columnName);
				if (field != null) {
					// 通过传入字段获取值
					ReflectionUtils.makeAccessible(field);
					if(columnName.equals("paidAmount")){
						if(entity.getPaidAmount() == null){
							entity.setPaidAmount(BigDecimal.valueOf(0));
						}
					}
					Object fieldValue = ReflectionUtils.getField(field, entity);
					// 如果为日期，需要进行格式化
					if (Date.class.equals(field.getType())&& fieldValue != null) {
						//日期转化
						fieldValue = DateUtils.convert((Date) fieldValue,DateUtils.DATE_TIME_FORMAT);
					}
					// 将字段封装到list中
					if (fieldValue != null) {
						if (columnName.equals("billType") && StringUtils.isNotBlank(entity.getBillType())) {
							//数据字典转换
							fieldValue = SettlementUtil.getDataDictionaryByTermsName(termMaps,DictionaryConstants.STATEMENT_OF_ACCOUNT__BILL_TYPE, fieldValue.toString());
						//产品类型
						}else if(columnName.equals("confirmStatus") && StringUtils.isNotBlank(entity.getConfirmStatus())){
							//数据字典转换
							fieldValue = SettlementUtil.getDataDictionaryByTermsName(termMaps,DictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS, fieldValue.toString());
						//提货方式
						}else if(columnName.equals("paidAmount")){
							if(entity.getPeriodAmount().compareTo(BigDecimal.valueOf(0)) > 0){
								fieldValue = entity.getPeriodAmount().subtract(entity.getUnpaidAmount());
							}else{
								fieldValue = 0;
							}
						}else if(columnName.equals("applyInvoice") && StringUtils.isNotBlank(entity.getApplyInvoice())){
							fieldValue = validaAccount(entity);
						}else if(columnName.equals("invoiceStatus") && StringUtils.isNotBlank(entity.getInvoiceStatus())){
							fieldValue = validaStatementOf(entity);
						}
						//设置属性值
						if(fieldValue!=null){
							rowList.add(fieldValue.toString());
						}else{
							rowList.add(null);
						}
					} else {
						rowList.add(null);
					}
				}
			}
			sheetList.add(rowList);
		}
		//封装数据
		data.setRowList(sheetList);
		data.setHeads(headerName);
		return data;
	}

	private Object validaAccount(StatementOfAccountEntity entity) {
		Object fieldValue;
		if(SettlementDictionaryConstants.SETTLEMENT_INVOICE_APPLY_INVOICE_YES.equals(entity.getApplyInvoice())){
			fieldValue = "是";
		}else if(SettlementDictionaryConstants.SETTLEMENT_INVOICE_APPLY_INVOICE_NO.equals(entity.getApplyInvoice())){
			fieldValue = "否";
		}else{
			fieldValue = entity.getApplyInvoice();
		}
		return fieldValue;
	}

	private Object validaStatementOf(StatementOfAccountEntity entity) {
		Object fieldValue;
		if(SettlementDictionaryConstants.SETTLEMENT_INVOICE_STATUS_APPLIED.equals(entity.getInvoiceStatus())){
			fieldValue = "已申请";
		}else if(SettlementDictionaryConstants.SETTLEMENT_INVOICE_STATUS_NO_APPLY.equals(entity.getInvoiceStatus())){
			fieldValue = "未申请";
		}else{
			fieldValue = entity.getInvoiceStatus();
		}
		return fieldValue;
	}
	
	/**
	 * @return statementOfAccountMakeQueryResultVo
	 */
	public StatementOfAccountMakeQueryResultVo getStatementOfAccountMakeQueryResultVo() {
		return statementOfAccountMakeQueryResultVo;
	}

	/**
	 * @param statementOfAccountMakeQueryResultVo
	 */
	public void setStatementOfAccountMakeQueryResultVo(
			StatementOfAccountMakeQueryResultVo statementOfAccountMakeQueryResultVo) {
		this.statementOfAccountMakeQueryResultVo = statementOfAccountMakeQueryResultVo;
	}

	/**
	 * @return statementOfAccountMakeQueryVo
	 */
	public StatementOfAccountMakeQueryVo getStatementOfAccountMakeQueryVo() {
		return statementOfAccountMakeQueryVo;
	}

	/**
	 * @param statementOfAccountMakeQueryVo
	 */
	public void setStatementOfAccountMakeQueryVo(
			StatementOfAccountMakeQueryVo statementOfAccountMakeQueryVo) {
		this.statementOfAccountMakeQueryVo = statementOfAccountMakeQueryVo;
	}

	/**
	 * @param statementModifyService
	 */
	public void setStatementModifyService(
			IStatementModifyService statementModifyService) {
		this.statementModifyService = statementModifyService;
	}

	/**
	 * @param statementMakeService
	 */
	public void setStatementMakeService(
			IStatementMakeService statementMakeService) {
		this.statementMakeService = statementMakeService;
	}

	/**
	 * @param statementOfAccountDService
	 */
	public void setStatementOfAccountDService(
			IStatementOfAccountDService statementOfAccountDService) {
		this.statementOfAccountDService = statementOfAccountDService;
	}

	/**
	 * @param statementWriteoffService
	 */
	public void setStatementWriteoffService(
			IStatementWriteoffService statementWriteoffService) {
		this.statementWriteoffService = statementWriteoffService;
	}

	/**
	 * @return pdfStream
	 */
	public InputStream getPdfStream() {
		return pdfStream;
	}

	/**
	 * @param pdfStream
	 */
	public void setPdfStream(InputStream pdfStream) {
		this.pdfStream = pdfStream;
	}

	/**
	 * @return fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
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

	/**
	 * @GET
	 * @return excelName
	 */
	public String getExcelName() {
		/*
		 *@get
		 *@ return excelName
		 */
		return excelName;
	}

	/**
	 * @SET
	 * @param excelName
	 */
	public void setExcelName(String excelName) {
		/*
		 *@set
		 *@this.excelName = excelName
		 */
		this.excelName = excelName;
	}

	/**
	 * @GET
	 * @return inputStream
	 */
	public InputStream getInputStream() {
		/*
		 *@get
		 *@ return inputStream
		 */
		return inputStream;
	}

	/**
	 * @SET
	 * @param inputStream
	 */
	public void setInputStream(InputStream inputStream) {
		/*
		 *@set
		 *@this.inputStream = inputStream
		 */
		this.inputStream = inputStream;
	}

}
