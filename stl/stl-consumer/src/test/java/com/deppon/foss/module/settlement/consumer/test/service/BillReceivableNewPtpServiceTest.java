/**
 * Copyright 2016 STL TEAM
 */
/*******************************************************************************
 * Copyright 2016 STL TEAM
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
 * PROJECT NAME	: stl-consumer-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/consumer/server/service/impl/BillReceivableNewPtpService.java
 * 
 * FILE NAME        	: BillReceivableNewPtpService.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2016  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.test.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableDService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableDEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillNewReceivablePtpDto;
import com.deppon.foss.module.settlement.consumer.test.BaseTestCase;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 对接合伙人生成应付单service接口实现
 * @author foss-Jiang Xun
 * @date 2016-01-07 上午11:27:13
 */
public class BillReceivableNewPtpServiceTest extends BaseTestCase {
	
	private static final Logger LOGGER = LogManager
			.getLogger(BillReceivableNewPtpServiceTest.class);
	
	/**
	 * 应收单通用服务service
	 */
	@Autowired
	private IBillReceivableService billReceivableService;
	/**
	 * 结算通用服务service
	 */
	@Autowired
	private ISettlementCommonService settlementCommonService;
	/**
	 * 快递点部营业部映射关系Service
	 */
//	@Autowired
//	private IExpressPartSalesDeptService expressPartSalesDeptService;
	/**
	 * 应收单明细Service
	 */
	@Autowired
	private IBillReceivableDService billReceivableDService;
	/**
	 * 应收单服务service set注入
	 * @param billReceivableService
	 */
	/**
	 * 应收单据子类型控制方法
	 * @author foss-Jiang Xun
	 * @date 2016-01-07 上午11:23:13
	 */
	@Test
	@Rollback(false)
	public void generatReceivableBillController() {
		LOGGER.info("测试生成合伙人应收单开始.................");
		
		//dto
		BillNewReceivablePtpDto dto = new BillNewReceivablePtpDto();
//		dto.setBillReceivableEntity(new BillReceivableEntity());
//		dto.getBillReceivableEntity().setId(UUIDUtils.getUUID());//id
//		dto.getBillReceivableEntity().setReceivableNo("kkk5");//应收单号
//		dto.getBillReceivableEntity().setCreateType("kkk5");;//生成方式
//		
//		dto.getBillReceivableEntity().setWaybillNo("kkk5");//运单号
//		dto.getBillReceivableEntity().setWaybillId("kkk5");//运单id
//		dto.getBillReceivableEntity().setSourceBillType("taobao");//来源单据类型
//		dto.getBillReceivableEntity().setSourceBillNo("jjj");//来源单据号
//		
//		dto.getBillReceivableEntity().setBillType("POR");//单据子类型
//		dto.getBillReceivableEntity().setReceivableOrgCode("001");//应收部门编码
//		dto.getBillReceivableEntity().setReceivableOrgName("dept1");//应收部门名称
//		dto.getBillReceivableEntity().setGeneratingOrgCode(dto.getBillReceivableEntity().getGeneratingOrgCode());//收入部门编码
//		dto.getBillReceivableEntity().setGeneratingOrgName(dto.getBillReceivableEntity().getGeneratingOrgName());//收入部门名称
//		dto.getBillReceivableEntity().setGeneratingComCode(dto.getBillReceivableEntity().getGeneratingComCode());//收入子公司编码
//		dto.getBillReceivableEntity().setGeneratingComName(dto.getBillReceivableEntity().getGeneratingComName());//收入子公司名称
//		dto.getBillReceivableEntity().setDunningOrgCode(dto.getBillReceivableEntity().getDunningOrgCode());//催款部门编码
//		dto.getBillReceivableEntity().setDunningOrgName(dto.getBillReceivableEntity().getDunningOrgName());//催款部门名称
//		dto.getBillReceivableEntity().setOrigOrgCode(dto.getBillReceivableEntity().getOrigOrgCode());//出发部门编码
//		dto.getBillReceivableEntity().setOrigOrgName(dto.getBillReceivableEntity().getOrigOrgName());//出发部门名称
//		dto.getBillReceivableEntity().setDestOrgCode(dto.getBillReceivableEntity().getDestOrgCode());//到达部门编码
//		dto.getBillReceivableEntity().setDestOrgName(dto.getBillReceivableEntity().getDestOrgName());//到达部门名称
//		dto.getBillReceivableEntity().setCustomerCode(dto.getBillReceivableEntity().getCustomerCode());//客户编码/应收代理编码
//		dto.getBillReceivableEntity().setCustomerName(dto.getBillReceivableEntity().getCustomerName());//客户名称/应收代理名称
//		dto.getBillReceivableEntity().setDeliveryCustomerCode(dto.getBillReceivableEntity().getDeliveryCustomerCode());//发货客户编码
//		dto.getBillReceivableEntity().setDeliveryCustomerName(dto.getBillReceivableEntity().getDeliveryCustomerName());//发货客户名称
//		dto.getBillReceivableEntity().setReceiveCustomerCode(dto.getBillReceivableEntity().getReceiveCustomerCode());//收货客户编码
//		dto.getBillReceivableEntity().setCurrencyCode("RMB");//币种
//		dto.getBillReceivableEntity().setGeneratingComName(dto.getBillReceivableEntity().getGeneratingComName());//收入子公司名称
//		dto.getBillReceivableEntity().setGeneratingComName(dto.getBillReceivableEntity().getGeneratingComName());//收入子公司名称
//		dto.getBillReceivableEntity().setBusinessDate(new Date());//业务日期
//		dto.getBillReceivableEntity().setAccountDate(new Date());//记账日期
////		dto.getBillReceivableEntity().setConrevenDate(dto.getBillReceivableEntity().getConrevenDate());//确认收入日期
//		dto.getBillReceivableEntity().setPaymentType("现付");//付款方式
//		dto.getBillReceivableEntity().setProductCode(dto.getBillReceivableEntity().getProductCode());//运输性质
//		dto.getBillReceivableEntity().setProductName(dto.getBillReceivableEntity().getProductName());//运输名称
//		dto.getBillReceivableEntity().setProductId(dto.getBillReceivableEntity().getProductId());//产品ID
////		dto.getBillReceivableEntity().setTransportFee(dto.getBillReceivableEntity().getTransportFee());//公布价运费
//		dto.getBillReceivableEntity().setPickupFee(dto.getBillReceivableEntity().getPickupFee());//接货费
//		dto.getBillReceivableEntity().setDeliveryGoodsFee(dto.getBillReceivableEntity().getDeliveryGoodsFee());//送货费
//		dto.getBillReceivableEntity().setCodFee(dto.getBillReceivableEntity().getCodFee());//代收货款手续费
//		dto.getBillReceivableEntity().setInsuranceFee(dto.getBillReceivableEntity().getInsuranceFee());//保价费
//		dto.getBillReceivableEntity().setOtherFee(dto.getBillReceivableEntity().getOtherFee());//其他费用
//		dto.getBillReceivableEntity().setValueAddFee(dto.getBillReceivableEntity().getValueAddFee());//增值费用
//		dto.getBillReceivableEntity().setPromotionsFee(dto.getBillReceivableEntity().getPromotionsFee());//优惠费用
//		dto.getBillReceivableEntity().setGoodsName(dto.getBillReceivableEntity().getGoodsName());//货物名称
//		dto.getBillReceivableEntity().setGoodsVolumeTotal(dto.getBillReceivableEntity().getGoodsVolumeTotal());//货物总体积
//		dto.getBillReceivableEntity().setBillWeight(dto.getBillReceivableEntity().getBillWeight());//计费重量
//		dto.getBillReceivableEntity().setReceiveMethod(dto.getBillReceivableEntity().getReceiveMethod());//提货方式
//		dto.getBillReceivableEntity().setCustomerPickupOrgCode(dto.getBillReceivableEntity().getCustomerPickupOrgCode());//提货网点
//		dto.getBillReceivableEntity().setGoodsQtyTotal(dto.getBillReceivableEntity().getGoodsQtyTotal());//货物总件数
//		dto.getBillReceivableEntity().setTargetOrgCode(dto.getBillReceivableEntity().getTargetOrgCode());//目的站
//		dto.getBillReceivableEntity().setVersionNo(FossConstants.INIT_VERSION_NO);//版本号
//		dto.getBillReceivableEntity().setActive(FossConstants.ACTIVE);//是否有效
//		dto.getBillReceivableEntity().setIsRedBack("N");//是否红单
//		dto.getBillReceivableEntity().setIsInit(FossConstants.NO);//是否初始化
//		dto.getBillReceivableEntity().setCollectionType(dto.getBillReceivableEntity().getCollectionType());//收款类别
//		dto.getBillReceivableEntity().setCollectionName(dto.getBillReceivableEntity().getCollectionName());//收款名称
//		dto.getBillReceivableEntity().setNotes(dto.getBillReceivableEntity().getNotes());//备注
//		dto.getBillReceivableEntity().setUnitPrice(dto.getBillReceivableEntity().getUnitPrice());//运费计费费率
//		dto.getBillReceivableEntity().setInsuranceAmount(dto.getBillReceivableEntity().getInsuranceAmount());//保险声明价值
//		dto.getBillReceivableEntity().setDeliveryCustomerContact(dto.getBillReceivableEntity().getDeliveryCustomerContact());//发货联系人
//		dto.getBillReceivableEntity().setExpressOrigOrgCode(dto.getBillReceivableEntity().getExpressOrigOrgCode());//出发部门快递点部编码
//		dto.getBillReceivableEntity().setExpressOrigOrgName(dto.getBillReceivableEntity().getExpressOrigOrgName());//出发部门快递点部名称
////		dto.getBillReceivableEntity().setInvoiceMark(dto.getBillReceivableEntity().getInvoiceMark());//发票标记
//		dto.getBillReceivableEntity().setUnifiedSettlement(dto.getBillReceivableEntity().getUnifiedSettlement());//是否统一结算
//		dto.getBillReceivableEntity().setContractOrgCode(dto.getBillReceivableEntity().getContractOrgCode());//合同部门编码
//		dto.getBillReceivableEntity().setContractOrgName(dto.getBillReceivableEntity().getContractOrgName());//合同部门名称
//		dto.getBillReceivableEntity().setIsDiscount(dto.getBillReceivableEntity().getIsDiscount());//是否折扣
////		dto.getBillReceivableEntity().setWithholdStatus(dto.getBillReceivableEntity().getWithholdStatus());//扣款状态
//		dto.getBillReceivableEntity().setAmount(BigDecimal.valueOf(150));//订单总金额
//		
//		dto.getBillReceivableEntity().setVerifyAmount(BigDecimal.valueOf(100));//已核销金额	
//		dto.getBillReceivableEntity().setUnverifyAmount(BigDecimal.valueOf(50));//未核销金额	
		
		//设置应收明细
		List<BillReceivableDEntity> listD = new ArrayList<BillReceivableDEntity>();
		BillReceivableDEntity dEntity1 = new BillReceivableDEntity();
		BillReceivableDEntity dEntity2 = new BillReceivableDEntity();
		dEntity1.setId(UUIDUtils.getUUID());
		dEntity1.setSourceBillNo("hhh2");
		dEntity1.setReceivableNo("hhh2");
		dEntity1.setAmount(BigDecimal.valueOf(50));
		dEntity1.setActive("Y");
		dEntity2.setId(UUIDUtils.getUUID());
		dEntity2.setSourceBillNo("ggg2");
		dEntity2.setReceivableNo("ggg2");
		dEntity2.setAmount(BigDecimal.valueOf(100));
		dEntity2.setActive("Y");
		listD.add(dEntity1);
		listD.add(dEntity2);
		
//		dto.setBillReceivableDList(listD);
		
		
		//模拟用户信息
		CurrentInfo currentInfo = SettlementUtil.getFKCurrentInfo();
		
		//单据子类型
//		String billType = dto.getBillReceivableEntity().getBillType();
		//数据判空等验证...
		//验证是否生成应收单（各项金额之和为0，不生成应收单）
//		if(BigDecimal.ZERO.equals(dto.getBillReceivableEntity().getAmount())){
//			throw new SettlementException("应收单总金额之和为0，不生成应收单");
//		}
		
//		//获取应付单实体Entity
//		BillReceivableEntity entity = dto.getBillReceivableEntity();
		//给应收单实体各自业务转换...
//		//生成应收单
//		billReceivableService.addBillReceivable(entity, currentInfo);
		
		
//		//分发（应收单号生成）
//		if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__ORIGINAL_RECEIVABLE.equals(billType)){//始发提成应收
//			dto.getBillReceivableEntity().setReceivableNo(settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YS21));//应收单号
//			generatOriginalReceivableBill(dto,currentInfo);
//		}else if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_RECEIVABLE.equals(billType)){//委派费应收
//			dto.getBillReceivableEntity().setReceivableNo(settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YS22));
////			generatDeliveryReceivableBill(dto,null);
//		}else if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__DELIVERY_RECEIVABLE.equals(billType)){//始发提成应收和委托派送应收
//			dto.getBillReceivableEntity().setReceivableNo(settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YS21));
//			generatOriginalReceivableBill(dto,null);
////			generatDeliveryReceivableBill(dto,null);
//		}else if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE.equals(billType)){//合伙人到付
//			dto.getBillReceivableEntity().setReceivableNo(settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YS23));
//		}else if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__ACCOUNT_RECEIVABLE_COD.equals(billType)){//合伙人代收货款应收
//			dto.getBillReceivableEntity().setReceivableNo(settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YS25));
//		}
		LOGGER.info("测试生成合伙人应收单结束......................");
//		Assert.assertEquals(, );
		Assert.assertNotNull("nnnnnnnn");
	}
	
	
	
	/**
	 * 生成始发提成应收单
	 * @author foss-Jiang Xun
	 * @date 2016-01-07 下午01:08:05
	 * 注：要根据部门编码，查询部门的汉字名称
	 */
	public Object generatOriginalReceivableBill(BillNewReceivablePtpDto dto,CurrentInfo currentInfo) {
		
		
//		//获取应付单实体Entity
//		BillReceivableEntity entity = dto.getBillReceivableEntity();
//		//给应收单实体各自业务转换...
//		//生成应收单
//		billReceivableService.addBillReceivable(entity, currentInfo);
//		
//		//获取应收单明细列表
//		List<BillReceivableDEntity> listD = dto.getBillReceivableDList();
//		
//		//生成应收单明细
//		billReceivableDService.addList(listD);
		
		
		return null;
	}
}
