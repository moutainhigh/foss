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
 * PROJECT NAME	: stl-agency
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/agency/test/util/AgencyTestUtil.java
 * 
 * FILE NAME        	: AgencyTestUtil.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.agency.test.util;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class AgencyTestUtil {

	
	public static CurrentInfo getCurrentInfo() {
		UserEntity user = new UserEntity();
		EmployeeEntity employee = new EmployeeEntity();
		employee.setEmpCode("000123");
		employee.setEmpName("张三");
		user.setEmployee(employee);
		user.setUserName("zhangsan");

		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		dept.setCode("W011302020106");
		dept.setName("到达北京营业部");

		CurrentInfo currentInfo = new CurrentInfo(user, dept);
		return currentInfo;
	}
	
	/**
	 * 
	 * 获取运单号-测试使用
	 * 
	 * @author dp-wujiangtao
	 * @date 2012-10-20 下午2:37:06
	 * @return
	 * @see
	 */
	public static String getWaybillNO() {
		return "YD"+new Date().getTime() ;
	}
	
	/**
	 * 获取外发单号-测试使用
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-21 下午8:30:15
	 * @return
	 */
	public static String getExternalNO(){
		return "WFD"+new Date().getTime() ;
	}

	/**
	 * 获取配载车次号
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-1 下午2:29:15
	 * @return
	 */
	public static String getVehicleAssembleNO(){
		return "PZCC"+new Date().getTime() ;
	}
	
	
	/**
	 * 设置测试应收单数据
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-23 上午10:34:09
	 * @param waybillNo
	 *            运单号
	 * @param receivableNo
	 *            应收单号
	 * @param billType
	 *            应收单类型
	 * @param paymentType
	 *            付款类型
	 * @return
	 * @see
	 */
	public static BillReceivableEntity getBillReceivableEntity(
			String waybillNo, String receivableNo, String billType,
			String paymentType) {
		BillReceivableEntity entity = new BillReceivableEntity();
		String uuid = UUIDUtils.getUUID();
		entity.setId(uuid);// 主键
		entity.setWaybillId(waybillNo);// 运单Id
		entity.setWaybillNo(waybillNo);// 运单号
		entity.setReceivableNo(receivableNo);// 应收单号
		entity.setCreateType("1");// 系统自动生成
		entity.setSourceBillNo(waybillNo);
		entity.setSourceBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL);// 来源单据类型
		entity.setBillType(billType);// 单据子类型 -到付应收单
		entity.setPaymentType(paymentType);// 付款方式-到付
		if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE
				.equals(billType)
				|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE
						.equals(billType)
				|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_PARTIAL_LINE
						.equals(billType)
				|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY
						.equals(billType)
				||SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD.equals(billType)		
				) {
			entity.setReceivableOrgCode("W011302020106");// 应收部门编码
			entity.setReceivableOrgName("到达北京营业部");// 应收部门名称
			entity.setCustomerCode("");// 客户编码
			entity.setCustomerName("");// 客户名称
		} else {
			entity.setReceivableOrgCode("W011302020106");// 应收部门编码
			entity.setReceivableOrgName("出发上海徐泾营业部");// 应收部门名称
			entity.setCustomerCode("0005");// 客户编码
			entity.setCustomerName("张三");// 客户名称
		}

		entity.setGeneratingOrgCode("SR1");// 收入部门编码
		entity.setGeneratingOrgName("收入上海营业部");// 收入部门名称
		entity.setGeneratingComCode("SRCOM1");// 收入子公司编码
		entity.setGeneratingComName("收入公司上海德邦物流");// 收入子公司名称
		entity.setDunningOrgCode("CK1");// 催款部门编码
		entity.setDunningOrgName("催款上海青浦营业部");// 催款部门名称
		entity.setOrigOrgCode("W011302020106");// 出发部门编码
		entity.setOrigOrgName("出发上海徐泾营业部");// 出发部门名称
		entity.setDestOrgCode("W011302020106");// 到达部门编码
		entity.setDestOrgName("到达北京营业部");// 到达部门名称
		

		// 设置金额 部分
		entity.setAmount(new BigDecimal(1000));// 总金额
		entity.setUnverifyAmount(entity.getAmount());// 未核销金额
		entity.setVerifyAmount(new BigDecimal(0));// 已核销金额
		entity.setTransportFee(new BigDecimal(400));// 公布价运费
		entity.setPickupFee(new BigDecimal(100));// 接货费
		entity.setDeliveryGoodsFee(new BigDecimal(100));// 送货费
		entity.setPackagingFee(new BigDecimal(100));// 包装手续费
		entity.setCodFee(new BigDecimal(100));// 代收货款手续费
		entity.setInsuranceFee(new BigDecimal(100));// 保价费
		entity.setOtherFee(new BigDecimal(100));// 其他费用

		entity.setValueAddFee(new BigDecimal(600));// 增值费用
		entity.setPromotionsFee(new BigDecimal(0));// 优惠费用
		entity.setCurrencyCode("1");// 货币 1 人民币

		// 提货方式
		entity.setReceiveMethod("1");
		entity.setBusinessDate(new Date());// 设置业务日期
		entity.setAccountDate(new Date());// 记账日期
		entity.setProductCode("1");// 产品类型 1精准汽运
		entity.setActive("Y");// 是否有效 默认为Y
		entity.setIsRedBack("N");// 是否为红单 默认为否
		entity.setIsInit("N");// 是否初始化 否
		entity.setVersionNo(FossConstants.INIT_VERSION_NO);// 版本号 1
		
		return entity;
	}
}
