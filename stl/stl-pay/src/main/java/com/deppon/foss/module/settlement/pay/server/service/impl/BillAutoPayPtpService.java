package com.deppon.foss.module.settlement.pay.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.inteface.domain.payment.AutoPayRequest;
import com.deppon.esb.inteface.domain.payment.AutoPayResult;
import com.deppon.esb.inteface.domain.payment.AutoPaymentInfo;
import com.deppon.esb.pojo.transformer.jaxb.AutoPayRequestTrans;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPaymentDService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillWriteoffService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentDEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillWriteoffEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPmtAutoPayDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillAutoPayPtpService;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillPayablePtpService;
import com.deppon.foss.module.settlement.pay.api.shared.domain.BillPayInfoEntity;
import com.deppon.foss.module.settlement.pay.api.shared.domain.PtpAutoPayPFCREntity;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IPartnerStatementOfAwardService;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * FOSS到PTP自动付款service
 * @author 302346-Jiang Xun
 */
public class BillAutoPayPtpService implements IBillAutoPayPtpService{
	
	//应付单备注
	private static String PAYMENT_NOTES = "PTP自动付款";
	
	//ESB客户端接口服务编码
	private static String PTP_AUTO_PAY_SERVER_CODE = "ESB_FOSS2ESB_PARTNER_AUTOPAY";
	
	//ESB接口版本号
	private static String VERSION = "1.0";
	
	//ESB接口描述
	private static String DESC = "FOSS到PTP自动付款ESB接口";
	
	//付款成功备注
	//private static String SUCCESS_OPINION = "到付转预收成功";
	
	//付款失败备注
	private static String FAIL_OPINION = "";
	
	//被JOB调用时，程序的运行的时间（小时）
	private static int CALLED_BY_JOB_HOUR = 23;
	
	private static final Logger LOGGER = LogManager
			.getLogger(BillAutoPayPtpService.class);
	
	/**
	 * 结算通用服务service
	 */
	@Autowired
	private ISettlementCommonService settlementCommonService;
	
	/**
	 * 注入应付单服务接口
	 */
	@Autowired
	private IBillPayableService billPayableService;
	
	/**
	 * 注入合伙人应付单服务接口
	 */
	@Autowired
	private IBillPayablePtpService billPayablePtpService;
	/**
	 * 注入付款单明细服务接口
	 */
	@Autowired
	private IBillPaymentDService billPaymentDService;
	/**
	 * 应付单通用服务service
	 */
	@Autowired
	private IBillPaymentService billPaymentService;
	
	/**
	 * 核销服务service
	 */
	@Autowired
	private IBillWriteoffService billWriteoffService;
	
	/**
	 * 注入奖罚对账单service
	 */
	@Autowired
	private IPartnerStatementOfAwardService statementOfAwardService;
	
	/**
	 * 自动付款到PTP
	 * @author 302346 jiang xun
	 * @date 2016-05-20 下午5:46:00
	 * @return
	 */
	public void autoPaytoPtp(){
		LOGGER.info("自动付款到PTP开始！");
		//获取要传递的应付信息
		List<AutoPaymentInfo> paymentList = this.builPaymentList();
		
//		List<AutoPaymentInfo> paymentList = new ArrayList<AutoPaymentInfo>();
//		AutoPaymentInfo autoPaymentInfo1 = new AutoPaymentInfo();
//		autoPaymentInfo1.setPaymentNo("1234ABCD");
//		autoPaymentInfo1.setAmount(BigDecimal.valueOf(198));
//		autoPaymentInfo1.setPaymentOrgCode("DP100856");
//		autoPaymentInfo1.setCustomerCode("DP100856");
//		autoPaymentInfo1.setPaymentType("AA");
//		autoPaymentInfo1.setBusinessDate(new Date());
//		paymentList.add(autoPaymentInfo1);
//		
//		AutoPaymentInfo autoPaymentInfo2 = new AutoPaymentInfo();
//		autoPaymentInfo2.setPaymentNo("NO.2");
//		autoPaymentInfo2.setAmount(BigDecimal.valueOf(300));
//		autoPaymentInfo2.setPaymentOrgCode("DP100856");
//		autoPaymentInfo2.setCustomerCode("DP100856");
//		autoPaymentInfo2.setPaymentType("BB");
//		autoPaymentInfo2.setBusinessDate(new Date());
//		paymentList.add(autoPaymentInfo2);
		
		if(CollectionUtils.isEmpty(paymentList)){
			LOGGER.info("自动付款到PTP。没有满足条件的合伙人到付应付单，无需通知PTP！自动付款到PTP结束！");
			return;
		}
		//声明要传递的值
		AutoPayRequest request = new AutoPayRequest();
		request.getAutoPaymentInfo().addAll(paymentList);
		//设置ESB请求头
		AccessHeader header = buildHeader();
		// 发送付款信息到ESB
		try {
			LOGGER.info("开始自动付款到PTP：\n"
					+ new AutoPayRequestTrans()
							.fromMessage(request));
			ESBJMSAccessor.asynReqeust(header, request);
		} catch (ESBException e) {
			LOGGER.error("自动付款到PTP出错。调用ESB接口出错！" + e.getExceptionCode(), e);
			throw new SettlementException("自动付款到PTP出错。调用ESB接口出错！"
					+ e.getExceptionCode());
		}
		LOGGER.info("自动付款到PTP结束！");
	}
	/**
	 * 委托派费自动付款
	 * yanjunjie
	 **/
	public void autoPaytoPtpPDDF(){
		LOGGER.info("委托派费自动付款到PTP开始！");
		List<AutoPaymentInfo> paymentList = this.buildPDDFList();
		if(CollectionUtils.isEmpty(paymentList)){
			LOGGER.info("自动付款到PTP。没有满足条件的合伙人到付应付单，无需通知PTP！自动付款到PTP结束！");
			return;
		}
		//声明要传递的值
		AutoPayRequest request = new AutoPayRequest();
		request.getAutoPaymentInfo().addAll(paymentList);
		//设置ESB请求头
		AccessHeader header = buildHeader();
		// 发送付款信息到ESB
		try {
			LOGGER.info("开始自动付款到PTP：\n"
					+ new AutoPayRequestTrans()
							.fromMessage(request));
			ESBJMSAccessor.asynReqeust(header, request);
			LOGGER.info("开始自动付款到PTP：\n"
					+ new AutoPayRequestTrans()
							.fromMessage(request));
		} catch (ESBException e) {
			LOGGER.error("自动付款到PTP出错。调用ESB接口出错！" + e.getExceptionCode(), e);
			throw new SettlementException("自动付款到PTP出错。调用ESB接口出错！"
					+ e.getExceptionCode());
		}
		LOGGER.info("委托派费自动付款到PTP结束！");
	}
	
	/**
	 * 设置ESB请求头
	 * @author foss-Jiang Xun
	 * @date 2016-05-23 上午10:01:00
	 */
	private AccessHeader buildHeader() {
		AccessHeader header = new AccessHeader();
		//服务编码
		header.setEsbServiceCode(PTP_AUTO_PAY_SERVER_CODE);
		header.setVersion(VERSION);
		//接口描述
		header.setBusinessDesc1(DESC);
		header.setBusinessDesc2(DESC);
		header.setBusinessDesc3(DESC);
		//businessId
		header.setBusinessId(new Date().toString());
		return header;
	}

	/**
	 * 查询合伙人到付应付单，生成付款单，更新付款单（不触发付款工作流）
	 * @return
	 * 付款单列表
	 **/ 
	public List<AutoPaymentInfo> builPaymentList() {
		LOGGER.info("封装付款单集合开始...");
		//应付单单据子类型
		String billType = SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_PAYABLE;
		//有效状态
		String active = FossConstants.ACTIVE;
		//开始时间(job启动时间)
		Date fromDate = new Date();
		Calendar cal = Calendar.getInstance();
		//当天的午夜
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 0, 0, 0);
		if(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)==CALLED_BY_JOB_HOUR){
			cal.add(Calendar.DATE, 1);
		}else{ 
			//否则设置的时间不做处理 
		}
		cal.add(Calendar.DATE, -2);
		fromDate = cal.getTime();
		//结束时间
		cal.add(Calendar.DATE, 1);
		Date toDate = cal.getTime();
				
		//查询满足条件的应付单
		List<PtpAutoPayPFCREntity> payableList = billPayablePtpService.queryPFCPBills(billType, active, fromDate, toDate);
		
		//自动付款单集合
		List<AutoPaymentInfo> autoPaymentList = new ArrayList<AutoPaymentInfo>();
		
		//应付单数量
		int payableBillNumber = 0;
		
		//批量新增付款单
		for(PtpAutoPayPFCREntity payableEntity:payableList){
			//付款单实体
			BillPaymentEntity billPaymentEntity = new BillPaymentEntity();
			//付款实体
			AutoPaymentInfo autoPaymentInfo = new AutoPaymentInfo();
			billPaymentEntity.setId(UUIDUtils.getUUID());//主键id
			billPaymentEntity.setPaymentNo(settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.FK2));//付款单号
			billPaymentEntity.setActive(FossConstants.ACTIVE);//是否有效
			billPaymentEntity.setIsRedBack(FossConstants.NO);//是否红单
			billPaymentEntity.setPaymentOrgCode(payableEntity.getPayableOrgCode());//付款部门编码
			billPaymentEntity.setPaymentOrgName(payableEntity.getPayableOrgName());//付款部门名称
			billPaymentEntity.setCustomerCode(payableEntity.getCustomerCode());//客户编码
			billPaymentEntity.setCustomerName(payableEntity.getCustomerName());//客户名称
			billPaymentEntity.setAccountDate(new Date());//记账日期
			billPaymentEntity.setBusinessDate(new Date());//业务日期
			billPaymentEntity.setRemitStatus(SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__TRANSFERRING);//汇款状态(汇款中)
			billPaymentEntity.setAmount(payableEntity.getAmount().divide(BigDecimal.valueOf(100)));//付款金额
			billPaymentEntity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);//生成方式
			billPaymentEntity.setCreateTime(new Date());//创建时间
			billPaymentEntity.setCreateUserCode("SYSTEM");//创建人编码
			billPaymentEntity.setCreateUserName("SYSTEM");//创建人名称
			billPaymentEntity.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT__US);//付款方式
			billPaymentEntity.setIsInit(FossConstants.NO);//是否是初始化数据（否）
			billPaymentEntity.setAuditStatus(SettlementDictionaryConstants.BILL_PAYMENT__AUDIT_STATUS__NOT_AUDIT);//审核状态(未审核)
//			billPaymentEntity.setSourceBillNo(payableEntity.getPayableNos());//来源单据号
			billPaymentEntity.setSourceBillNo(SettlementConstants.DEFAULT_BILL_NO);//来源单据号（'N/A'）
			billPaymentEntity.setSourceBillType(SettlementDictionaryConstants.BILL_PAYMENT__SOURCE_BILL_TYPE__ADVANCED_PAYMENT);//来源单据类型
			billPaymentEntity.setVersionNo(FossConstants.INIT_VERSION_NO);//版本号
			billPaymentEntity.setNotes(PAYMENT_NOTES);//备注
			billPaymentEntity.setAutoSendCount(new BigDecimal(1));//推送次数
			billPaymentEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);//币种（RMB）
			try {
				//新增付款单
				billPaymentService.addBilAutoPayPayment(billPaymentEntity);
				
				//应付单号集合
				List<String> payableNoList = new ArrayList<String>(Arrays.asList(payableEntity.getPayableNos().split(",")));
				// 新增付款单明细
				this.autoPayPtpAddBillPaymentD(payableNoList,billPaymentEntity);

				payableBillNumber += payableNoList.size();
				//应付单号超过1000时,分批更新应付单
				int leftNum = payableNoList.size();
				int span = 1000;//每次的更新数量
				List<String> subList;//更新的应付单号
				while(leftNum>0){
					LOGGER.info("分批更新应付单...");
					span = (leftNum<span)?leftNum:span;
					subList = payableNoList.subList(0, span);
					//批量更新应付单
					payableEntity.setPayableNoList(subList);//要更新的应付单号集合
					payableEntity.setPaymentNo(billPaymentEntity.getPaymentNo());//付款单号
					payableEntity.setBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_PAYABLE);//应付单类型
					payableEntity.setPaymentNotes(PAYMENT_NOTES);//备注
					billPayablePtpService.updateBatchPFCPBills(payableEntity);
					subList.clear();
					leftNum = payableNoList.size();
				}
				
				//自动付款信息
				autoPaymentInfo.setPaymentNo(billPaymentEntity.getPaymentNo());//付款单号
				autoPaymentInfo.setPaymentOrgCode(payableEntity.getPayableUnifiedCode());//付款部门标杆编码
				autoPaymentInfo.setPaymentType(billPaymentEntity.getPaymentType());//付款类型
				autoPaymentInfo.setCustomerCode(payableEntity.getCustomerUnifiedCode());//合伙人客户标杆编码
				autoPaymentInfo.setAmount(payableEntity.getAmount().divide(BigDecimal.valueOf(100)));//付款金额
				autoPaymentInfo.setBusinessDate(billPaymentEntity.getBusinessDate());//业务日期
				autoPaymentInfo.setRechargeType("1");//充值类型
				StringBuffer sb=new StringBuffer();
				//获取付款单明细 
				List<BillPaymentDEntity>  BillPaymentlist =billPaymentDService.queryPaymentDEntityListByPaymentNo(billPaymentEntity.getPaymentNo());
				if(CollectionUtils.isNotEmpty(BillPaymentlist)){
					//付款单明细 获取运单号
					for(int i =0;i< BillPaymentlist.size();i++){
						if((i+1)==BillPaymentlist.size()){
							sb.append(BillPaymentlist.get(i).getWaybillNo());
						}else{
							sb.append(BillPaymentlist.get(i).getWaybillNo()+",");
						}
					}
					autoPaymentInfo.setWayBills(sb.toString());//运单号
				}
				
				autoPaymentList.add(autoPaymentInfo);
			}catch(BusinessException e){
				LOGGER.error("自动付款到PTP异常。新增付款单、修改应付单出错！" + e.getErrorCode(),e);
				continue;
			}catch(Exception e){
				LOGGER.error("自动付款到PTP出错。新增付款单、修改应付单出错！" + e.getMessage(),e);
				continue;
			}
		}
		//打印应付单和付款单信息
		LOGGER.info("自动付款，记录应付单信息。满足条件的总应付单数量是:"+payableBillNumber);
		if(CollectionUtils.isEmpty(autoPaymentList)){
			LOGGER.info("自动付款，记录付款单信息。总付款单数量是:0");
		}else{
			LOGGER.info("自动付款，记录付款单信息。总付款单数量是:"+autoPaymentList.size());
			for(int i=0;i<autoPaymentList.size();i++){
				LOGGER.info("自动付款，记录付款单信息。\n"+"付款单号是:"+autoPaymentList.get(i).getPaymentNo()
						+"\n客户编码是:"+autoPaymentList.get(i).getCustomerCode()
						+"\n付款部门编码是:"+autoPaymentList.get(i).getPaymentOrgCode()
						
						+"\n付款类型是:"+autoPaymentList.get(i).getPaymentType()
						+"\n付款金额是:"+autoPaymentList.get(i).getAmount());
			}
		}
		LOGGER.info("封装付款单集合结束...");
		return autoPaymentList;
	}
	/**
	 * 委托派费自动付款
	 * 
	 */
	public List<AutoPaymentInfo> buildPDDFList(){

		LOGGER.info("封装付款单集合开始...");
		//应付单单据子类型
		String billType = SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELEGATION_DELIVERY_FEE;
		//有效状态
		String active = FossConstants.ACTIVE;
		//开始时间(job启动时间)
		Date fromDate = new Date();
		Calendar cal = Calendar.getInstance();
		//当天的早上
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 0, 0, 0);
		cal.add(Calendar.DATE, -4);
		fromDate = cal.getTime();
		//结束时间
		cal.add(Calendar.DATE, 1);
		Date toDate = cal.getTime();
				
		//查询符合条件的委托派费应付单
		List<PtpAutoPayPFCREntity> payableList = billPayablePtpService.queryPDDFBills(billType, active, fromDate, toDate);
		
		//自动付款单集合
		List<AutoPaymentInfo> autoPaymentList = new ArrayList<AutoPaymentInfo>();
		
		//应付单数量
		int payableBillNumber = 0;
		
		//批量新增付款单
		for(PtpAutoPayPFCREntity payableEntity:payableList){
			//付款单实体
			BillPaymentEntity billPaymentEntity = new BillPaymentEntity();
			//付款实体
			AutoPaymentInfo autoPaymentInfo = new AutoPaymentInfo();
			billPaymentEntity.setId(UUIDUtils.getUUID());//主键id
			billPaymentEntity.setPaymentNo(settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.FK2));//付款单号
			billPaymentEntity.setActive(FossConstants.ACTIVE);//是否有效
			billPaymentEntity.setIsRedBack(FossConstants.NO);//是否红单
			billPaymentEntity.setPaymentOrgCode(payableEntity.getPayableOrgCode());//付款部门编码
			billPaymentEntity.setPaymentOrgName(payableEntity.getPayableOrgName());//付款部门名称
			billPaymentEntity.setCustomerCode(payableEntity.getCustomerCode());//客户编码
			billPaymentEntity.setCustomerName(payableEntity.getCustomerName());//客户名称
			billPaymentEntity.setAccountDate(new Date());//记账日期
			billPaymentEntity.setBusinessDate(new Date());//业务日期
			billPaymentEntity.setRemitStatus(SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__TRANSFERRING);//汇款状态(汇款中)
			billPaymentEntity.setAmount(payableEntity.getAmount().divide(BigDecimal.valueOf(100)));//付款金额
			billPaymentEntity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);//生成方式
			billPaymentEntity.setCreateTime(new Date());//创建时间
			billPaymentEntity.setCreateUserCode("SYSTEM");//创建人编码
			billPaymentEntity.setCreateUserName("SYSTEM");//创建人名称
			billPaymentEntity.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__COMMISSION_FEE);//付款方式
			billPaymentEntity.setIsInit(FossConstants.NO);//是否是初始化数据（否）
			billPaymentEntity.setAuditStatus(SettlementDictionaryConstants.BILL_PAYMENT__AUDIT_STATUS__NOT_AUDIT);//审核状态(未审核)
//			billPaymentEntity.setSourceBillNo(payableEntity.getPayableNos());//来源单据号
			billPaymentEntity.setSourceBillNo(SettlementConstants.DEFAULT_BILL_NO);//来源单据号（'N/A'）
			billPaymentEntity.setSourceBillType(SettlementDictionaryConstants.BILL_PAYMENT__SOURCE_BILL_TYPE__ADVANCED_PAYMENT);//来源单据类型
			billPaymentEntity.setVersionNo(FossConstants.INIT_VERSION_NO);//版本号
			billPaymentEntity.setNotes(PAYMENT_NOTES);//备注
			billPaymentEntity.setAutoSendCount(new BigDecimal(1));//推送次数
			billPaymentEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);//币种（RMB）
			try {
				//新增付款单
				billPaymentService.addBilAutoPayPayment(billPaymentEntity);
				
				//应付单号集合
				List<String> payableNoList = new ArrayList<String>(Arrays.asList(payableEntity.getPayableNos().split(",")));
				// 新增付款单明细
				this.autoPayPtpAddBillPaymentD(payableNoList,billPaymentEntity);

				payableBillNumber += payableNoList.size();
				//应付单号超过1000时,分批更新应付单
				int leftNum = payableNoList.size();
				int span = 1000;//每次的更新数量
				List<String> subList;//更新的应付单号
				while(leftNum>0){
					LOGGER.info("分批更新应付单...");
					span = (leftNum<span)?leftNum:span;
					subList = payableNoList.subList(0, span);
					//批量更新应付单
					payableEntity.setPayableNoList(subList);//要更新的应付单号集合
					payableEntity.setPaymentNo(billPaymentEntity.getPaymentNo());//付款单号
					payableEntity.setBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELEGATION_DELIVERY_FEE);//应付单类型
					payableEntity.setPaymentNotes(PAYMENT_NOTES);//备注
					billPayablePtpService.updateBatchPFCPBills(payableEntity);
					subList.clear();
					leftNum = payableNoList.size();
				}

				//自动付款信息
				autoPaymentInfo.setPaymentNo(billPaymentEntity.getPaymentNo());//付款单号
				autoPaymentInfo.setPaymentOrgCode(payableEntity.getPayableUnifiedCode());//付款部门标杆编码
				autoPaymentInfo.setPaymentType(billPaymentEntity.getPaymentType());//付款类型
				autoPaymentInfo.setCustomerCode(payableEntity.getCustomerUnifiedCode());//合伙人客户标杆编码
				autoPaymentInfo.setAmount(payableEntity.getAmount().divide(BigDecimal.valueOf(100)));//付款金额
				autoPaymentInfo.setBusinessDate(billPaymentEntity.getBusinessDate());//业务日期
				autoPaymentInfo.setRechargeType("2");//充值类型
				
				StringBuffer sb=new StringBuffer();
				//获取付款单明细 
				List<BillPaymentDEntity>  BillPaymentlist =billPaymentDService.queryPaymentDEntityListByPaymentNo(billPaymentEntity.getPaymentNo());
				if(CollectionUtils.isNotEmpty(BillPaymentlist)){
					//付款单明细 获取运单号
					for(int i =0;i< BillPaymentlist.size();i++){
						if((i+1)==BillPaymentlist.size()){
							sb.append(BillPaymentlist.get(i).getWaybillNo());
						}else{
							sb.append(BillPaymentlist.get(i).getWaybillNo()+",");
						}
					}
					autoPaymentInfo.setWayBills(sb.toString());//运单号
				}
				
				autoPaymentList.add(autoPaymentInfo);
			}catch(BusinessException e){
				LOGGER.error("自动付款到PTP异常。新增付款单、修改应付单出错！" + e.getErrorCode(),e);
				continue;
			}catch(Exception e){
				LOGGER.error("自动付款到PTP出错。新增付款单、修改应付单出错！" + e.getMessage(),e);
				continue;
			}
		}
		//打印应付单和付款单信息
		LOGGER.info("自动付款，记录应付单信息。满足条件的总应付单数量是:"+payableBillNumber);
		if(CollectionUtils.isEmpty(autoPaymentList)){
			LOGGER.info("自动付款，记录付款单信息。总付款单数量是:0");
		}else{
			LOGGER.info("自动付款，记录付款单信息。总付款单数量是:"+autoPaymentList.size());
			for(int i=0;i<autoPaymentList.size();i++){
				LOGGER.info("自动付款，记录付款单信息。\n"+"付款单号是:"+autoPaymentList.get(i).getPaymentNo()
						+"\n客户编码是:"+autoPaymentList.get(i).getCustomerCode()
						+"\n付款部门编码是:"+autoPaymentList.get(i).getPaymentOrgCode()
						+"\n付款类型是:"+autoPaymentList.get(i).getPaymentType()
						+"\n付款金额是:"+autoPaymentList.get(i).getAmount());
			}
		}
		LOGGER.info("封装付款单集合结束...");
		return autoPaymentList;
	}
	
	/**
	 * 自动付款到PTP,处理结果回调
	 * @author 302346 jiang xun
	 * @date 2016-06-01 下午3:24:00
	 * @param resultList
	 * 付款信息列表
	 * @return
	 */
	public void dealAutoPayResult(List<AutoPayResult> resultList){
		LOGGER.info("处理自动付款到PTP结果开始！"); 
		if(CollectionUtils.isEmpty(resultList)){
			LOGGER.error("处理自动付款到PTP结果结束，PTP响应结果为空！");
			throw new SettlementException("处理自动付款到PTP结果结束，PTP响应结果为空！");
		}
		//处理成功款单号集合
		List<String> successPaymentNos = new ArrayList<String>();
		//处理失败款单号集合
		List<String> failPaymentNos = new ArrayList<String>();
		
		//成功的付款单集合
		BillPmtAutoPayDto successPaymentDto = new BillPmtAutoPayDto();
		//成功的付款单集合
		BillPmtAutoPayDto failPaymentDto = new BillPmtAutoPayDto();
	
		
		//付款成功处理意见
		String successOption = "到付转预收成功";
		
		for(int i=0;i<resultList.size();i++){
			//付款单号
			String paymentNo = resultList.get(i).getSourceBillNO();
			if("0".equals(resultList.get(i).getErrcode())){
				successPaymentNos.add(paymentNo);
			}else{
				failPaymentNos.add(paymentNo);
			}
		}
		LOGGER.info("PTP处理成功单号数量：" + successPaymentNos.size() + ",单号:" + successPaymentNos);
		LOGGER.info("PTP处理失败单号数量：" + failPaymentNos.size() + ",单号:" + failPaymentNos);
		Date now = new Date();
		successPaymentDto.setRemitStatus(SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__TRANSFERRED);//付款成功——汇款状态（已汇款）
		successPaymentDto.setModifyUserCode("SYSTEM");//付款成功——修改人编码
		successPaymentDto.setModifyUserName("SYSTEM");//付款成功——修改人名称
		successPaymentDto.setModifyTime(now);//付款成功——修改时间
		successPaymentDto.setDisableOpinion(successOption);//付款成功——处理意见
		successPaymentDto.setActive(FossConstants.ACTIVE);
		successPaymentDto.setPaymentNoList(successPaymentNos);
		
		failPaymentDto.setRemitStatus(SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__NOT_TRANSFER);//付款失败——汇款状态（未汇款）
		failPaymentDto.setModifyUserCode("SYSTEM");//付款失败——修改人编码
		failPaymentDto.setModifyUserName("SYSTEM");//付款失败——修改人名称
		failPaymentDto.setModifyTime(now);//付款失败——修改时间
		failPaymentDto.setDisableOpinion(FAIL_OPINION);//付款失败——处理意见
		failPaymentDto.setActive(FossConstants.ACTIVE);
		failPaymentDto.setPaymentNoList(failPaymentNos);
		if(CollectionUtils.isNotEmpty(successPaymentNos)){
			//根据付款单号集合查询付款信息
			List<BillPayInfoEntity> payinfoList = billPayablePtpService.queryPayInfoByPayableNos(successPaymentNos);
			if(CollectionUtils.isNotEmpty(payinfoList)){
				if(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__COMMISSION_FEE.equals(payinfoList.get(0).getPaymentType())){
					successOption = "委托派费转预收成功";
				}else if(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__REWARD_RETURN.equals(payinfoList.get(0).getPaymentType())
						|| SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__EXPRESS_ERROR_RETURN.equals(payinfoList.get(0).getPaymentType())){
					successOption = "奖励自动返成功";
				}else{
					successOption = "到付转预收成功";
				}
			}
			for(BillPayInfoEntity payinfo:payinfoList){
				// 应付单的付款金额大于未核销金额时，不能核销
				if (payinfo.getPaymentAmount().compareTo(
						payinfo.getUnverifyAmount()) > 0) {
					LOGGER.error("付款单号："+payinfo.getPaymentNo()+",应付单号:"+payinfo.getPayableNo()+"中的付款金额大于未核销金额，无法核销");
					continue;
				}
				// 应付单未核销金额或者付款金额等于零时，不能用于核销
				if (payinfo.getUnverifyAmount().compareTo(BigDecimal.ZERO) == 0
						|| payinfo.getPaymentAmount().compareTo(BigDecimal.ZERO) == 0) {
					LOGGER.error("付款单号："+payinfo.getPaymentNo()+",应付单号:"+payinfo.getPayableNo()+"应付单中的未核销金额或者付款金额为0，无法核销");
					continue;
				}
				//修改对账单的应付单集合
				List<BillPayableEntity> billPayableEntitys = new ArrayList<BillPayableEntity>();
				
				//组装应付单、核销单
				Object[] obj = builBillEntity(payinfo);
				BillWriteoffEntity billWriteoffEntity = (BillWriteoffEntity)obj[0];
				//组装应付单
				BillPayableEntity billPayableEntity = (BillPayableEntity)obj[1];
				//新增核销单
				billWriteoffService.addWriteoff(billWriteoffEntity);

				// 核销应付单（付款冲应付）
				UserEntity user = new UserEntity();
				EmployeeEntity emp = new EmployeeEntity();
				emp.setEmpCode("SYSTEM");
				emp.setEmpName("SYSTEM");
				user.setEmployee(emp);
				CurrentInfo currentInfo = new CurrentInfo(user,null);
				payinfo.setUnverifyAmount(payinfo.getUnverifyAmount().subtract(
						billWriteoffEntity.getAmount()));
				payinfo.setVerifyAmount(payinfo.getVerifyAmount().add(
						billWriteoffEntity.getAmount()));
				try{
					billPayableService.writeoffBillPayable(billPayableEntity,
							billWriteoffEntity.getAmount(), currentInfo);
//					//添加成功的应付单到集合
//					BillPayableEntity payableEntity = new BillPayableEntity();
					billPayableEntity.setPayableNo(payinfo.getPayableNo());
					billPayableEntitys.add(billPayableEntity);
				}catch(SettlementException e){
					LOGGER.error("付款单号：" + payinfo.getPaymentNo() + ",应付单号:"
									+ payinfo.getPayableNo() + "核销应付单失败！"
									+ e.getErrorCode(), e);
				}
				//当返回的付款单付款方式是奖励应付自动返或者快递差错应付自动返时
				//修改应付单对应对账单信息和对账单明细
				if(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__REWARD_RETURN.equals(
						payinfo.getPaymentType())
						|| SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__EXPRESS_ERROR_RETURN.equals(
								payinfo.getPaymentType())){
					try{
						if(billPayableEntitys.size()>0){
							LOGGER.info("奖励自动返修改对账单及明细开始...");
							Map<String,Object> payableNos = new HashMap<String, Object>();
							payableNos.put("billPayableList", billPayableEntitys);
							//更新应付单对应的对账的本期已还金额和本期未还金额
							statementOfAwardService.updateAmountInStatementByPayableNo(billPayableEntitys.get(0));
							//更新应付单对应的对账单明细
							statementOfAwardService.updateAmountByPayableNo(payableNos);
							LOGGER.info("奖励自动返修改对账单及明细结束...");
						}
					}catch (SettlementException e) {
						LOGGER.error("付款单号：" + payinfo.getPaymentNo() + ",应付单号:"
								+ payinfo.getPayableNo() + "修改对账单失败！"
								+ e.getErrorCode(), e);
					}
				}
				
			}
			//付款成功，更新付款单（汇款状态为已汇款）
			if(CollectionUtils.isNotEmpty(successPaymentDto.getPaymentNoList())){
				//付款单号超过1000时,分批更新付款单
				int leftNum = successPaymentDto.getPaymentNoList().size();
				int span = 1000;//每次的更新数量
				List<String> subList;//更新的付款单号
				while(leftNum>0){
					LOGGER.info("分批更新付款单...");
					span = (leftNum<span)?leftNum:span;
					subList = successPaymentDto.getPaymentNoList().subList(0, span);
					//批量更新应付单
					successPaymentDto.setRemitStatus(SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__TRANSFERRED);//付款成功——汇款状态（已汇款）
					successPaymentDto.setModifyUserCode("SYSTEM");//付款成功——修改人编码
					successPaymentDto.setModifyUserName("SYSTEM");//付款成功——修改人名称
					successPaymentDto.setModifyTime(now);//付款成功——修改时间
					successPaymentDto.setDisableOpinion(successOption);//付款成功——处理意见
					successPaymentDto.setActive(FossConstants.ACTIVE);
					successPaymentDto.setPaymentNoList(subList);
					billPaymentService.updateRemitStatusByPmtNos(successPaymentDto);
					subList.clear();
					leftNum = successPaymentDto.getPaymentNoList().size();
				}
			}
		}else{
			LOGGER.info("处理自动付款到PTP结果。无成功结果！");
		}
		
		//付款失败，更新付款单（汇款状态为未汇款）
		if(CollectionUtils.isNotEmpty(failPaymentDto.getPaymentNoList())){
			//付款单号超过1000时,分批更新付款单
			int leftNum = failPaymentDto.getPaymentNoList().size();
			int span = 1000;//每次的更新数量
			List<String> subList;//更新的付款单号
			while(leftNum>0){
				LOGGER.info("分批更新付款单...");
				span = (leftNum<span)?leftNum:span;
				subList = failPaymentDto.getPaymentNoList().subList(0, span);
				failPaymentDto.setRemitStatus(SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__NOT_TRANSFER);//付款失败——汇款状态（未汇款）
				failPaymentDto.setModifyUserCode("SYSTEM");//付款失败——修改人编码
				failPaymentDto.setModifyUserName("SYSTEM");//付款失败——修改人名称
				failPaymentDto.setModifyTime(now);//付款失败——修改时间
				failPaymentDto.setDisableOpinion(FAIL_OPINION);//付款失败——处理意见
				failPaymentDto.setActive(FossConstants.ACTIVE);
				//failPaymentDto.setPaymentNoList(failPaymentNos);
				failPaymentDto.setPaymentNoList(subList);
				billPaymentService.updateRemitStatusByPmtNos(failPaymentDto);
				subList.clear();
				leftNum = failPaymentDto.getPaymentNoList().size();
			}
		}
		LOGGER.info("处理自动付款到PTP结果结束！");
	}
	
	/**
	 * 组装核销单实体
	 * @param payinfo
	 * 应付信息实体
	 * @return
	 * 核销单实体
	 **/ 
	public Object[] builBillEntity(BillPayInfoEntity payinfo) {
		Object[] obj = new Object[2]; 
		Date now = new Date();
		// 生成核销单记录
		BillWriteoffEntity billWriteoffEntity = new BillWriteoffEntity();
		// 修改应付单记录
		BillPayableEntity billPayableEntity = new BillPayableEntity();
		
		//核销id
		billWriteoffEntity.setId(UUIDUtils.getUUID());
		//核销批次号
		billWriteoffEntity.setWriteoffBatchNo(settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.HX_BN));
		//核销单号
		billWriteoffEntity.setWriteoffBillNo(settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.HX));
		//核销人编码
		billWriteoffEntity.setCreateUserCode("SYSTEM");
		//核销人名称
		billWriteoffEntity.setCreateUserName("SYSTEM");
		//生效状态
		billWriteoffEntity.setActive(FossConstants.ACTIVE);
		//是否红单
		billWriteoffEntity.setIsRedBack(FossConstants.NO);
		//核销时间
		billWriteoffEntity.setWriteoffTime(now);
		//记账时间
		billWriteoffEntity.setAccountDate(now);
		//初始版本号
		billWriteoffEntity.setVersionNo(FossConstants.INIT_VERSION_NO);
		//单据生成方式
		billWriteoffEntity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);
		
		// 开始来源单号为付款单号
		billWriteoffEntity.setBeginNo(payinfo.getPaymentNo());
		//设置开始和结束id
		billWriteoffEntity.setBeginId(payinfo.getpId());
		billWriteoffEntity.setEndId(payinfo.getId());
		// 开始来源记账日期
		billWriteoffEntity.setBeginAccountDate(payinfo.getpAccountDate());
		// 目的来源单号为应付单号
		billWriteoffEntity.setEndNo(payinfo.getPayableNo());
		// 目的来源记账日期
		billWriteoffEntity.setEndAccountDate(payinfo.getAccountDate());
		// 核销部门编码为应付单应付部门编码
		billWriteoffEntity.setOrgCode(payinfo.getPayableOrgCode());
		// 核销部门名称为应付单应付部门名称
		billWriteoffEntity.setOrgName(payinfo.getPayableOrgName());
		// 核销类型应付款冲应付
		billWriteoffEntity
				.setWriteoffType(SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__PAYMENT_PAYABLE);//付款冲应付
		// 客户信息取应付单客户信息
		billWriteoffEntity.setCustomerCode(payinfo.getCustomerCode());
		billWriteoffEntity.setCustomerName(payinfo.getCustomerName());
		// 目的运单号为应付单运单号
		billWriteoffEntity.setEndWaybillNo(payinfo.getWaybillNo());
		// 核销单金额为应付单的付款金额
		billWriteoffEntity.setAmount(payinfo.getPaymentAmount());
		// 网点信息取付款单上的付款部门
		billWriteoffEntity.setOrgCode(payinfo.getPaymentOrgCode());
		billWriteoffEntity.setOrgName(payinfo.getPaymentOrgName());
		
		billPayableEntity.setId(payinfo.getId());//应付单id
		billPayableEntity.setAccountDate(payinfo.getAccountDate());//应付单记账日期
		billPayableEntity.setVersionNo(payinfo.getVersionNo());//应付单版本号
		billPayableEntity.setPaymentAmount(payinfo.getPaymentAmount());//应付单付款金额
		billPayableEntity.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE);//应付单审核状态(已审核)
		billPayableEntity.setModifyUserCode("SYSTEM");//应付单修改人编码
		billPayableEntity.setModifyUserName("SYSTEM");//应付单修改人名称
		
		obj[0] = billWriteoffEntity;
		obj[1] = billPayableEntity;
		return obj;
	}
	
	/**
	 * 合伙人到付自动付款 新增付款单明细
	 * @param payableNoList 
	 * 应付单号列表
	 * @author 231438
	 */
	public void autoPayPtpAddBillPaymentD(List<String> payableNoList,BillPaymentEntity billPaymentEntity){
		LOGGER.info("新增付款单明细开始：");
		//付款单明细
		BillPaymentDEntity entity = new BillPaymentDEntity();
		LOGGER.info("----合伙人到付自动付款 新增付款单明细:根据应付单号集合查询应付单记录----");
		//根据应付单号集合单查询出多条应付单记录
		List<BillPayableEntity> payableEntityList =billPayableService.queryByPayableNOs(payableNoList, FossConstants.ACTIVE);
		for(BillPayableEntity payableEntity:payableEntityList){
			entity.setId(UUIDUtils.getUUID());
			//付款单号
			entity.setPaymentNo(billPaymentEntity.getPaymentNo());
			//来源单号--应付单的应付单号
			entity.setSourceBillNo(payableEntity.getPayableNo());
			//运单号--应付单的运单号
			entity.setWaybillNo(payableEntity.getWaybillNo());
			//来源单据类型
			entity.setSourceBillType(SettlementDictionaryConstants.BILL_PAYMENT_D_SOURCE_BILL_TYPE_PAYABLE);
			//付款单的记账日期
			entity.setPaymentAccountDate(billPaymentEntity.getAccountDate());
			//来源单据的记账日期
			entity.setSourceAccountDate(payableEntity.getAccountDate());
			//创建时间
			entity.setCreateTime(new Date());
			entity.setPayAmount(payableEntity.getUnverifyAmount());
			//调用付款单明细Service方法进行保存
			billPaymentDService.addBillPaymentD(entity);
		}
	}
	
	/**
	 * 自动付款重推，发送请求到PTP
	 * @author 231438
	 * @param paymentList
	 * @return
	 */
	private void requestPtp(List<AutoPaymentInfo> paymentList){
		//声明要传递的值
		AutoPayRequest request = new AutoPayRequest();
		request.getAutoPaymentInfo().addAll(paymentList);
		//设置ESB请求头
		AccessHeader header = buildHeader();
		// 发送付款信息到ESB
		try {
			LOGGER.info("开始自动付款到PTP：\n"
					+ new AutoPayRequestTrans()
							.fromMessage(request));
			ESBJMSAccessor.asynReqeust(header, request);
		} catch (ESBException e) {
			LOGGER.error("自动付款到PTP出错。调用ESB接口出错！" + e.getExceptionCode(), e);
			throw new SettlementException("自动付款到PTP出错。调用ESB接口出错！" + e.getExceptionCode());
		}
		LOGGER.info("自动付款到PTP结束！");
	}
	
	/**
	 * 合伙人到付运费自动付款重推方法
	 *@author 231438
	 *@date 2016-06-06 上午8:15:05
	 *@return
	 */
	@Override
	public void autoPaytoPtpPushAgin(){
		LOGGER.info("-------自动付款重推开始！------");
		//获取要传递的付款信息
		List<AutoPaymentInfo> paymentList = this.getPushAgaginList();
		if(CollectionUtils.isEmpty(paymentList)){
			LOGGER.info("自动付款重推到PTP，没有满足条件的合伙人到付付款，无需通知PTP！自动付款到PTP结束！");
			return;
		}
		
		// 更新付款单:汇款状态：汇款中、modify_time和自动付款推送次数 
		LOGGER.info("批量更新付款单:更新汇款状态、更新时间和推送次数！");
		this.updatePaymentList(paymentList);
		
		//调用接口发送请求
		this.requestPtp(paymentList);
		LOGGER.info("合伙人到付运费自动付款重推结束");
	}

	private List<AutoPaymentInfo> getPushAgaginList() {
		//付款单实体--查询用
		BillPaymentEntity payEntity = new BillPaymentEntity();
		//付款单类型：到付转预收--FCUS
		List<String> PaymentType = new ArrayList<String>();
		PaymentType.add(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT__US);
		PaymentType.add(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__COMMISSION_FEE);
		payEntity.setPaymentTypes(PaymentType);
		//汇款状态：未汇款 NT
		payEntity.setRemitStatus(SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__NOT_TRANSFER);
		//符合条件的付款单集合
		List<BillPaymentEntity> payableEntityList = billPaymentService.queryBillPaymentByPmtType(payEntity);
		if(CollectionUtils.isEmpty(payableEntityList)){
			LOGGER.info("合伙人到付运费自动付款：根据条件查询付款单信息为空！");
			return null;
		}
		//付款单集合
		List<AutoPaymentInfo> autoPaymentList = new ArrayList<AutoPaymentInfo>();
		for(BillPaymentEntity payable:payableEntityList){
			//付款实体
			AutoPaymentInfo autoPaymentInfo = new AutoPaymentInfo();
			
			//自动付款信息
	   		autoPaymentInfo.setPaymentNo(payable.getPaymentNo());
			//付款部门--取标杆编码
			autoPaymentInfo.setPaymentOrgCode(payable.getPaymentOrgUnifiedCode());
			//付款类型--到付转预收
			autoPaymentInfo.setPaymentType(payable.getPaymentType());
			autoPaymentInfo.setCustomerCode(payable.getCustomerCode());
			autoPaymentInfo.setAmount(payable.getAmount().divide(BigDecimal.valueOf(100)));
			autoPaymentInfo.setBusinessDate(payable.getCreateTime());//取付款单生成时间
			
			//充值类型
			if(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT__US.equals(payable.getPaymentType())){
				autoPaymentInfo.setRechargeType("1");
			}else{
				autoPaymentInfo.setRechargeType("2");
			}
			
			StringBuffer sb=new StringBuffer();
			//获取付款单明细 
			List<BillPaymentDEntity>  BillPaymentlist =billPaymentDService.queryPaymentDEntityListByPaymentNo(payable.getPaymentNo());
			if(CollectionUtils.isNotEmpty(BillPaymentlist)){
				//付款单明细 获取运单号
				for(int i =0;i< BillPaymentlist.size();i++){
					if((i+1)==BillPaymentlist.size()){
						sb.append(BillPaymentlist.get(i).getWaybillNo());
					}else{
						sb.append(BillPaymentlist.get(i).getWaybillNo()+",");
					}
				}
				autoPaymentInfo.setWayBills(sb.toString());//运单号
			}
			
			autoPaymentList.add(autoPaymentInfo);
		}
		//打印付款单信息
		LOGGER.info("自动付款重推，记录付款单信息。满足条件的总付款单数量是:"+autoPaymentList.size());
		if(CollectionUtils.isEmpty(autoPaymentList)){
			LOGGER.info("自动付款重推，记录付款单信息。总付款单数量是:0");
		}else{
			LOGGER.info("自动付款，记录付款单信息。总付款单数量是:"+autoPaymentList.size());
			for(int i=0;i<autoPaymentList.size();i++){
				LOGGER.info("自动付款重推，记录付款单信息。\n"+"付款单号是:"+autoPaymentList.get(i).getPaymentNo()
						+"\n客户编码是:"+autoPaymentList.get(i).getCustomerCode()
						+"\n付款部门编码是:"+autoPaymentList.get(i).getPaymentOrgCode()
						+"\n付款类型是:"+autoPaymentList.get(i).getPaymentType()
						+"\n付款金额是:"+autoPaymentList.get(i).getAmount());
			}
		}
		LOGGER.info("打印重推付款单集合结束...");
		return autoPaymentList;
	}
	/**
	 * 批量更新付款单
	 * @author 231438
	 * @param paymentList
	 * @return
	 */
	private void updatePaymentList(List<AutoPaymentInfo> paymentList){
		List<String> updateList = new ArrayList<String>();
		//将付款单号参数循环取出
		for(AutoPaymentInfo info:paymentList){
			updateList.add(info.getPaymentNo());
		}
		BillPmtAutoPayDto dto = new BillPmtAutoPayDto();

		//付款单号超过1000时,分批更新付款单
		int leftNum = updateList.size();
		int span = 1000;//每次的更新数量
		while(leftNum>0){
			span = (leftNum<span)?leftNum:span;
			List<String> subList = new ArrayList<String>(updateList.subList(0, span));//更新的付款号
			//批量更新付款单
			//付款单号集合
			dto.setPaymentNoList(subList);
			//汇款状态--汇款中
			dto.setRemitStatus(SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__TRANSFERRING);
			dto.setActive(FossConstants.ACTIVE);
			//单据类型到付转预收--FCUS
			//dto.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT__US);
			dto.setModifyTime(new Date());
			billPaymentService.updateFcusPaymentByPaymentNos(dto);
			//subList.clear();
			//删除已经更新过的付款单
			updateList.removeAll(subList);
			leftNum = updateList.size();
		}
	}
	
	/**
	 * 奖励自动返
	 * @author 355019
	 */
	@Override
	public void autoPaytoPtpReward() {
		LOGGER.info("奖励自动返付款到PTP开始！");
		List<AutoPaymentInfo> paymentList = this.buildRewAutoList();
		if(CollectionUtils.isEmpty(paymentList)){
			LOGGER.info("奖励自动返付款到PTP：没有满足条件的合伙人到付应付单，无需通知PTP！自动付款到PTP结束！");
			return;
		}
		//声明要传递的值
		AutoPayRequest request = new AutoPayRequest();
		request.getAutoPaymentInfo().addAll(paymentList);
		//设置ESB请求头
		AccessHeader header = buildHeader();
		// 发送付款信息到ESB
		try {
			LOGGER.info("开始奖励自动返付款到PTP：\n"
					+ new AutoPayRequestTrans()
							.fromMessage(request));
			ESBJMSAccessor.asynReqeust(header, request);
			LOGGER.info("开始奖励自动返付款到PTP：\n"
					+ new AutoPayRequestTrans()
							.fromMessage(request));
		} catch (ESBException e) {
			LOGGER.error("奖励自动返付款到PTP出错。调用ESB接口出错！" + e.getExceptionCode(), e);
			throw new SettlementException("奖励自动返付款到PTP出错。调用ESB接口出错！"
					+ e.getExceptionCode());
		}
		LOGGER.info("奖励自动返付款到PTP结束！");
	}
	
	/**
	 * @description 奖励自动返
	 * @author 355019
	 */
	public List<AutoPaymentInfo> buildRewAutoList(){

		LOGGER.info("封装付款单集合开始...");
		
		//应付单单据子类型
		//合伙人奖励应付
		String rew_billType = SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__BONUS;
		//合伙人快递差错应付
		String err_billType = SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__LTL_ERROR;
		
		//有效状态
		String active = FossConstants.ACTIVE;
		//开始时间(job启动时间)
		Date fromDate = new Date();
		Calendar cal = Calendar.getInstance();
		//当天的早上
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 0, 0, 0);
		cal.add(Calendar.DATE, -3);
		fromDate = cal.getTime();
		//结束时间
		cal.add(Calendar.DATE, 3);
		Date toDate = cal.getTime();
				
		//查询符合条件的合伙人奖励应付单汇总
		List<PtpAutoPayPFCREntity> rew_payableList = billPayablePtpService.queryPBPLEBills(rew_billType, active, fromDate, toDate);
		
		//查询符合条件的合伙人快递差错应付单汇总
		List<PtpAutoPayPFCREntity> err_payableList = billPayablePtpService.queryPBPLEBills(err_billType, active, fromDate, toDate);
		
		//自动付款单集合
		List<AutoPaymentInfo> autoPaymentList = new ArrayList<AutoPaymentInfo>();
		
		//绑定应付单类型与对应应付单集合
		Map<String,List<PtpAutoPayPFCREntity>> payMap = new HashMap<String, List<PtpAutoPayPFCREntity>>();
		payMap.put(rew_billType, rew_payableList);
		payMap.put(err_billType, err_payableList);
		
		//应付单数量
		int payableBillNumber = 0;
		//遍历不同应付单集合生成付款单
		for(Entry<String, List<PtpAutoPayPFCREntity>> map : payMap.entrySet()){
			String billType = map.getKey();
			//单据子类型为空就执行下一个循环
			if(StringUtils.isEmpty(billType))
				continue;
			//批量新增付款单
			for(PtpAutoPayPFCREntity payableEntity:map.getValue()){
				//付款单实体
				BillPaymentEntity billPaymentEntity = new BillPaymentEntity();
				//付款实体
				AutoPaymentInfo autoPaymentInfo = new AutoPaymentInfo();
				billPaymentEntity.setId(UUIDUtils.getUUID());//主键id
				billPaymentEntity.setPaymentNo(settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.FK2));//付款单号
				billPaymentEntity.setActive(FossConstants.ACTIVE);//是否有效
				billPaymentEntity.setIsRedBack(FossConstants.NO);//是否红单
				billPaymentEntity.setPaymentOrgCode(payableEntity.getPayableOrgCode());//付款部门编码
				billPaymentEntity.setPaymentOrgName(payableEntity.getPayableOrgName());//付款部门名称
				billPaymentEntity.setCustomerCode(payableEntity.getCustomerCode());//客户编码
				billPaymentEntity.setCustomerName(payableEntity.getCustomerName());//客户名称
				billPaymentEntity.setAccountDate(new Date());//记账日期
				billPaymentEntity.setBusinessDate(new Date());//业务日期
				billPaymentEntity.setRemitStatus(SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__TRANSFERRING);//汇款状态(汇款中)
				billPaymentEntity.setAmount(payableEntity.getAmount().divide(BigDecimal.valueOf(100)));//付款金额
				billPaymentEntity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);//生成方式
				billPaymentEntity.setCreateTime(new Date());//创建时间
				billPaymentEntity.setCreateUserCode("SYSTEM");//创建人编码
				billPaymentEntity.setCreateUserName("SYSTEM");//创建人名称
				if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__BONUS.equals(billType))
					billPaymentEntity.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__REWARD_RETURN);//付款方式
				else if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__LTL_ERROR.equals(billType))
					billPaymentEntity.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__EXPRESS_ERROR_RETURN);//付款方式				
				billPaymentEntity.setIsInit(FossConstants.NO);//是否是初始化数据（否）
				billPaymentEntity.setAuditStatus(SettlementDictionaryConstants.BILL_PAYMENT__AUDIT_STATUS__NOT_AUDIT);//审核状态(未审核)
	//			billPaymentEntity.setSourceBillNo(payableEntity.getPayableNos());//来源单据号
				billPaymentEntity.setSourceBillNo(SettlementConstants.DEFAULT_BILL_NO);//来源单据号（'N/A'）
				billPaymentEntity.setSourceBillType(SettlementDictionaryConstants.BILL_PAYMENT__SOURCE_BILL_TYPE__ADVANCED_PAYMENT);//来源单据类型
				billPaymentEntity.setVersionNo(FossConstants.INIT_VERSION_NO);//版本号
				billPaymentEntity.setNotes(PAYMENT_NOTES);//备注
				billPaymentEntity.setAutoSendCount(new BigDecimal(1));//推送次数
				billPaymentEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);//币种（RMB）
				try {
					//新增付款单
					billPaymentService.addBilAutoPayPayment(billPaymentEntity);
					
					//应付单号集合
					List<String> payableNoList = new ArrayList<String>(Arrays.asList(payableEntity.getPayableNos().split(",")));
					// 新增付款单明细
					this.autoPayPtpAddBillPaymentD(payableNoList,billPaymentEntity);
	
					payableBillNumber += payableNoList.size();
					//应付单号超过1000时,分批更新应付单
					int leftNum = payableNoList.size();
					int span = 1000;//每次的更新数量
					List<String> subList;//更新的应付单号
					while(leftNum>0){
						LOGGER.info("分批更新应付单...");
						span = (leftNum<span)?leftNum:span;
						subList = payableNoList.subList(0, span);
						//批量更新应付单
						payableEntity.setPayableNoList(subList);//要更新的应付单号集合
						payableEntity.setPaymentNo(billPaymentEntity.getPaymentNo());//付款单号
						payableEntity.setBillType(billType);//应付单类型
						payableEntity.setPaymentNotes(PAYMENT_NOTES);//备注
						billPayablePtpService.updateBatchPFCPBills(payableEntity);
						subList.clear();
						leftNum = payableNoList.size();
					}
	
					//自动付款信息
					autoPaymentInfo.setPaymentNo(billPaymentEntity.getPaymentNo());//付款单号
					autoPaymentInfo.setPaymentOrgCode(payableEntity.getPayableUnifiedCode());//付款部门标杆编码
					autoPaymentInfo.setPaymentType(billPaymentEntity.getPaymentType());//付款类型
					autoPaymentInfo.setCustomerCode(payableEntity.getCustomerUnifiedCode());//合伙人客户标杆编码
					autoPaymentInfo.setAmount(payableEntity.getAmount().divide(BigDecimal.valueOf(100)));//付款金额
					autoPaymentInfo.setBusinessDate(billPaymentEntity.getBusinessDate());//业务日期
					if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__BONUS.equals(billType))
						autoPaymentInfo.setRechargeType("3");//充值类型,奖励
					else if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__LTL_ERROR.equals(billType))
						autoPaymentInfo.setRechargeType("4");//充值类型,差错
					
					StringBuffer sb=new StringBuffer();
					//获取付款单明细 
					List<BillPaymentDEntity>  BillPaymentlist =billPaymentDService.queryPaymentDEntityListByPaymentNo(billPaymentEntity.getPaymentNo());
					if(CollectionUtils.isNotEmpty(BillPaymentlist)){
						//付款单明细 获取运单号
						for(int i =0;i< BillPaymentlist.size();i++){
							if((i+1)==BillPaymentlist.size()){
								sb.append(BillPaymentlist.get(i).getWaybillNo());
							}else{
								sb.append(BillPaymentlist.get(i).getWaybillNo()+",");
							}
						}
						autoPaymentInfo.setWayBills(sb.toString());//运单号
					}
					
					autoPaymentList.add(autoPaymentInfo);
				}catch(BusinessException e){
					LOGGER.error("自动付款到PTP异常。新增付款单、修改应付单出错！" + e.getErrorCode(),e);
					continue;
				}catch(Exception e){
					LOGGER.error("自动付款到PTP出错。新增付款单、修改应付单出错！" + e.getMessage(),e);
					continue;
				}
			}
		}
		//打印应付单和付款单信息
		LOGGER.info("自动付款，记录应付单信息。满足条件的总应付单数量是:"+payableBillNumber);
		if(CollectionUtils.isEmpty(autoPaymentList)){
			LOGGER.info("自动付款，记录付款单信息。总付款单数量是:0");
		}else{
			LOGGER.info("自动付款，记录付款单信息。总付款单数量是:"+autoPaymentList.size());
			for(int i=0;i<autoPaymentList.size();i++){
				LOGGER.info("自动付款，记录付款单信息。\n"+"付款单号是:"+autoPaymentList.get(i).getPaymentNo()
						+"\n客户编码是:"+autoPaymentList.get(i).getCustomerCode()
						+"\n付款部门编码是:"+autoPaymentList.get(i).getPaymentOrgCode()
						+"\n付款类型是:"+autoPaymentList.get(i).getPaymentType()
						+"\n付款金额是:"+autoPaymentList.get(i).getAmount());
			}
		}
		LOGGER.info("封装付款单集合结束...");
		return autoPaymentList;
	}
}
