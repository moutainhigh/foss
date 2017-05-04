/**
 * Copyright 2013 STL TEAM
 */
/*******************************************************************************
 * Copyright 2013 STL TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: stl-pay
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/pay/test/service/BillAutoPayPtpServiceTest.java
 * 
 * FILE NAME        	: BillAutoPayPtpServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2016  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.pay.test.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.CollectionUtils;

import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.inteface.domain.payment.AutoPayResult;
import com.deppon.esb.inteface.domain.payment.AutoPaymentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPaymentDService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillWriteoffService;
import com.deppon.foss.module.settlement.common.api.server.service.IFossConfigEntityService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentDEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillAutoPayPtpService;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillPayablePtpService;
import com.deppon.foss.module.settlement.pay.api.shared.domain.PtpAutoPayPFCREntity;
import com.deppon.foss.module.settlement.pay.test.BaseTestCase;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 测试自动付款到PTP服务
 * @author 302346-foss-Jiang Xun
 * @date 2016-05-23 上午11:16:00
 */
public class BillAutoPayPtpServiceTest extends BaseTestCase{

	/**
	 * 获取日志
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(BillAutoPayPtpServiceTest.class);
	
	//应付单备注
	private static String PAYMENT_NOTES = "PTP自动付款";

	//ESB客户端接口服务编码
	private static String ESB_FOSS2ESB_PARTNER_AUTOPAY = "ESB_FOSS2ESB_PARTNER_AUTOPAY";
	
	//ESB接口版本号
	private static String VERSION = "1.0";
	
	//ESB接口描述
	private static String DESC = "FOSS到PTP自动付款ESB接口";
	
	/**
	 * 自动付款服务service
	 */
	@Autowired
	private IBillAutoPayPtpService billAutoPayPtpService;
	
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
	 * 应付单通用服务service
	 */
	@Autowired
	private IBillPaymentService billPaymentService;
	@Autowired
	private IFossConfigEntityService fossConfigEntityService;
	
	/**
	 * 核销服务service
	 */
	@Autowired
	private IBillWriteoffService billWriteoffService;
	
	/**
	 * 注入付款单明细服务接口
	 */
	@Autowired
	private IBillPaymentDService billPaymentDService;
	
	/**
	 * 设置ESB请求头
	 * @author foss-Jiang Xun
	 * @date 2016-05-23 上午10:01:00
	 */
	private AccessHeader buildHeader() {
		AccessHeader header = new AccessHeader();
		//服务编码
		header.setEsbServiceCode(ESB_FOSS2ESB_PARTNER_AUTOPAY);
		header.setVersion(VERSION);
		//接口描述
		header.setBusinessDesc1(DESC);
		//businessId
		header.setBusinessId(new Date().toString());
		return header;
	}

	/**
	 * 查询合伙人到付应付单，生成付款单，更新付款单（不触发付款工作流）
	 * @param payableList	
	 * 应付单列表
	 * @return
	 * 付款单列表
	 **/ 
	@Rollback(true)
	@Test
	public void  builPaymentList() {//List<AutoPaymentInfo>
		LOGGER.info("封装付款单集合开始...");
		//应付单单据子类型
		String billType = SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_PAYABLE;
		//有效状态
		String active = FossConstants.ACTIVE;
		//开始时间(job启动时间)
		Date fromDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -2);
		fromDate = cal.getTime();
		//结束时间
		cal.add(Calendar.DATE, 1);
		Date toDate = cal.getTime();
				
		//查询满足条件的应付单
		List<PtpAutoPayPFCREntity> payableList = billPayablePtpService.queryPFCPBills(billType, active, fromDate, toDate);
		
		
		//应付单号集合
		List<String> payableNoList = new ArrayList<String>();
		//自动付款单集合
		List<AutoPaymentInfo> autoPaymentList = new ArrayList<AutoPaymentInfo>();
		//应付单数量
		int payableBillNumber = 0;
		
		//批量新增付款单
		for(PtpAutoPayPFCREntity payableEntity:payableList){
			
			//打印
			LOGGER.info("应付单号数量是:"+payableEntity.getPayableNos().split(",").length+
					"\n应付单号长度是:"+payableEntity.getPayableNos().length());
			
			//付款单实体
			BillPaymentEntity billPaymentEntity = new BillPaymentEntity();
			//付款实体
			AutoPaymentInfo autoPaymentInfo = new AutoPaymentInfo();
			billPaymentEntity.setId(UUIDUtils.getUUID());//主键id
			billPaymentEntity.setPaymentNo(settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.FK2));//付款单号
			billPaymentEntity.setActive(FossConstants.ACTIVE);//是否有效
			billPaymentEntity.setIsRedBack(FossConstants.NO);//是否红单
			billPaymentEntity.setPaymentOrgCode(payableEntity.getPayableOrgCode());//付款部门编码
			billPaymentEntity.setPaymentOrgName(payableEntity.getPayableOrgCode());//付款部门名称
			billPaymentEntity.setCustomerCode(payableEntity.getCustomerCode());//客户编码
			billPaymentEntity.setCustomerName(payableEntity.getCustomerName());//客户名称
			billPaymentEntity.setAccountDate(new Date());//记账日期
			billPaymentEntity.setBusinessDate(new Date());//业务日期
			billPaymentEntity.setRemitStatus(SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__TRANSFERRING);//汇款状态(汇款中)
			billPaymentEntity.setAmount(payableEntity.getAmount());//付款金额
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
			//查询是否有重复的付款单...
			
			try {
				//新增付款单
				billPaymentService.addBilAutoPayPayment(billPaymentEntity);
				
				//应付单号集合
				payableNoList = Arrays.asList(payableEntity.getPayableNos().split(","));
				payableBillNumber += payableNoList.size();
				//应付单号超过1000时,分批更新应付单
				int leftNum = payableNoList.size();
				int span = 1000;//每次的更新数量
				List<String> subList;//更新的应付单号
				while(leftNum>0){
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
				
				// 新增付款单明细
				this.autoPayPtpAddBillPaymentD(payableNoList,billPaymentEntity);
				
				//自动付款信息
				autoPaymentInfo.setPaymentNo(billPaymentEntity.getPaymentNo());//付款单号
				autoPaymentInfo.setPaymentOrgCode(payableEntity.getPayableUnifiedCode());//付款部门标杆编码
				autoPaymentInfo.setPaymentType(billPaymentEntity.getPaymentType());//付款类型
				autoPaymentInfo.setCustomerCode(payableEntity.getCustomerUnifiedCode());//合伙人客户标杆编码
				autoPaymentInfo.setAmount(billPaymentEntity.getAmount());//付款金额
				autoPaymentInfo.setBusinessDate(billPaymentEntity.getBusinessDate());//业务日期
				autoPaymentList.add(autoPaymentInfo);
			}catch(Exception e){
				LOGGER.error("自动付款到PTP出错。新增付款单、修改应付单出错！"+e.getMessage());
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
//		return autoPaymentList;
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
	 * 测试自动付款结果功能
	 * @author 302346-foss-Jiang Xun
	 * @date 2016-05-23 上午11:19:00
	 */
	@Rollback(false)
	@Test
	@Ignore
	public void testDealAutoPayResult(){
		//'FK1000008302','FK1000006659','FK1000008658','FK200023354'
		LOGGER.info("***处理自动付款到PTP结果开始！");
		List<AutoPayResult> resultList = new ArrayList<AutoPayResult>();
		AutoPayResult autoPayResult1 = new AutoPayResult();
		AutoPayResult autoPayResult2 = new AutoPayResult();
		AutoPayResult autoPayResult3 = new AutoPayResult();
		AutoPayResult autoPayResult4 = new AutoPayResult();
		autoPayResult1.setSourceBillNO("FK1000008302");
		autoPayResult1.setErrcode("0");
		autoPayResult2.setSourceBillNO("FK1000006659");
		autoPayResult2.setErrcode("0");
		autoPayResult3.setSourceBillNO("FK1000008658");
		autoPayResult3.setErrcode("1");
		autoPayResult3.setErrmsg("参数为空");
		autoPayResult4.setSourceBillNO("FK200023354");
		autoPayResult4.setErrcode("1");
		autoPayResult4.setErrmsg("格式不正确");
		
		resultList.add(autoPayResult1);
		resultList.add(autoPayResult2);
		resultList.add(autoPayResult3);
		resultList.add(autoPayResult4);
		
		billAutoPayPtpService.dealAutoPayResult(resultList);
		LOGGER.info("***处理自动付款到PTP结果结束！");
	}
	

	public ISettlementCommonService getSettlementCommonService() {
		return settlementCommonService;
	}

	public void setSettlementCommonService(
			ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

	public IBillPayableService getBillPayableService() {
		return billPayableService;
	}

	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}

	public IBillPayablePtpService getBillPayablePtpService() {
		return billPayablePtpService;
	}

	public void setBillPayablePtpService(
			IBillPayablePtpService billPayablePtpService) {
		this.billPayablePtpService = billPayablePtpService;
	}

	public IBillPaymentService getBillPaymentService() {
		return billPaymentService;
	}

	public void setBillPaymentService(IBillPaymentService billPaymentService) {
		this.billPaymentService = billPaymentService;
	}

	public IFossConfigEntityService getFossConfigEntityService() {
		return fossConfigEntityService;
	}

	public void setFossConfigEntityService(
			IFossConfigEntityService fossConfigEntityService) {
		this.fossConfigEntityService = fossConfigEntityService;
	}

	public IBillWriteoffService getBillWriteoffService() {
		return billWriteoffService;
	}

	public void setBillWriteoffService(IBillWriteoffService billWriteoffService) {
		this.billWriteoffService = billWriteoffService;
	}

	public IBillAutoPayPtpService getBillAutoPayPtpService() {
		return billAutoPayPtpService;
	}

	public void setBillAutoPayPtpService(
			IBillAutoPayPtpService billAutoPayPtpService) {
		this.billAutoPayPtpService = billAutoPayPtpService;
	}
	
	
}
