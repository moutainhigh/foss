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
 * PROJECT NAME	: stl-web
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/web/server/test/waybill/SettlementWaybillTest.java
 * 
 * FILE NAME        	: SettlementWaybillTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.web.server.test.waybill;

import junit.framework.TestSuite;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.dto.LineSignDto;
import com.deppon.foss.module.settlement.consumer.api.server.service.ILineSignService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IWaybillPickupService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillPickupInfoDto;
import com.deppon.foss.module.settlement.web.test.BaseTestCase;
import com.deppon.foss.module.settlement.web.test.util.TestUtil;
import com.deppon.foss.util.define.FossConstants;
import com.opensymphony.xwork2.interceptor.annotations.Before;

/**
 * 结算开单-还款-签收
 * 
 * @author 099995-foss-wujiangtao
 * @date 2013-1-29 下午6:29:04
 * @since
 * @version
 */
public class SettlementWaybillTest extends BaseTestCase{
	
	/**
	 *  运单dto  
	 *  只有现金收款单
	 */
	private WaybillPickupInfoDto waybill;
	
	
	/**
	 *  运单dto  
	 *  只有始发应收单
	 */
	public static WaybillPickupInfoDto origWaybill;
	
	/**
	 * 运单dto
	 * 只有到付运费应收单
	 */
	public static WaybillPickupInfoDto destWaybill;
	
	/**
	 * 签收dto
	 */
	public static LineSignDto  singDto;
	
	/**
	 * 私有开单Service
	 */
	@Autowired
	private IWaybillPickupService  waybillPickupService;
	
	/**
	 * 私有签收Service
	 */
	@Autowired
	private ILineSignService lineSignService;
	
	/**
	 * 运单Service
	 */
	@Autowired
	private IWaybillManagerService waybillManagerService;
	
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 始发部门编码
	 */
	private String origOrgCode;
	
	/**
	 * 始发部门名称
	 */
	private String origOrgName;
	
	/**
	 * 始发操作者编码
	 */
	private String origUserCode;
	
	/**
	 * 始发操作者名称
	 */
	private String origUserName;
	
	/**
	 * 到达部门编码
	 */
	private String destOrgCode;
	
	/**
	 * 到达部门名称
	 */
	private String destOrgName;
	
	/**
	 * 到达操作者编码
	 */
	private String destUserCode;
	
	/**
	 * 到达操作者名称
	 */
	private String destUserName;
	
	@Before
	public void setUp(){
		System.out.println("开单");
	}
	
	/**
	 * 开单只添加现金收款单，直接进行签收操作
	 *  
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-29 下午7:05:25
	 */
	@Test
	@Rollback(true)
	public void testKaiDanAndQianShou(){
		
		//开单现金
		initWaybill(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);//插入运单信息
		junit.textui.TestRunner.run(suite());
		
		//开单月结
		initWaybill(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT);//插入运单信息
		junit.textui.TestRunner.run(suite());
		
		//开单临欠
		initWaybill(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT);//插入运单信息
		junit.textui.TestRunner.run(suite());
		
		//开单网上支付  --不能进行签收，始发应收单必须全部付款
		initWaybill(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE);//插入运单信息
		junit.textui.TestRunner.run(suite());	
	}
	
	
	
	/**
	 * 新增插入运单信息
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-30 下午2:24:25
	 */
	private void initWaybill(String paymentType){
		
		//给Service对象进行赋值
		String waybillNo=TestUtil.getWaybillNO();
		this.setWaybillNo(waybillNo);
		
		this.setOrigOrgCode("W011303070303");
		this.setOrigOrgName("广州花都区花山营业部");
		this.setOrigUserCode("044983");
		this.setOrigUserName("王军");
		this.setDestOrgCode("W011306040304");
		this.setDestOrgName("武汉派送部");
		this.setDestUserCode("28410");
		this.setDestUserName("刘志非");

		WaybillPickupInfoDto waybill=null;
		
		//付款方式-现金
		if(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH.equals(paymentType)){
			waybill=TestUtil.getWaybillPickupInfoDtoForCash(this.getWaybillNo(),paymentType,
					this.getOrigOrgCode(),this.getDestOrgCode());
		}
		//月结
		else if(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT.equals(paymentType)
				){
			waybill=TestUtil.getWaybillPickupInfoDtoForCt(this.getWaybillNo(),paymentType,
					this.getOrigOrgCode(),this.getDestOrgCode());
		}
		//临欠
		else if(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT.equals(paymentType)
				){
			waybill=TestUtil.getWaybillPickupInfoDtoForDt(this.getWaybillNo(),paymentType,
					this.getOrigOrgCode(),this.getDestOrgCode());
		}
		//网上支付
		else if(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.equals(paymentType)
				){
			waybill=TestUtil.getWaybillPickupInfoDtoForOnLine(this.getWaybillNo(),paymentType,
					this.getOrigOrgCode(),this.getDestOrgCode());
		}
		
		//付款方式-到付
		else if(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT.equals(paymentType)){
			waybill=TestUtil.getWaybillPickupInfoDtoForFc(this.getWaybillNo(),paymentType,
					this.getOrigOrgCode(),this.getDestOrgCode());
		}
		String sql=TestUtil.getInsertWaybillSql(this.getWaybillNo(), waybill);
		this.simpleJdbcTemplate.getJdbcOperations().execute(sql);
		
		//保存运单成功后，查询运单信息
		WaybillEntity waybillEntity=waybillManagerService.queryWaybillBasicByNo(this.getWaybillNo());
		BeanUtils.copyProperties(waybillEntity, waybill);
		this.setWaybill(waybill);
		logger.info("----------------------------------------------"+waybill.getWaybillNo());
	}
	
	/**
	 * 开单服务测试类
	 * 
	 * 签收服务测试类
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-31 上午10:38:03
	 * @return
	 */
	public  junit.framework.Test suite() {
	     TestSuite suite = new TestSuite();
	     
	     //添加：新增运单测试类
	     StlWaybillPickupServiceTest pickTest=  new StlWaybillPickupServiceTest("testAddWaybill");
	     
	     //设置StlWaybillPickupServiceTest测试类需要的数据
	     pickTest.setWaybillNo(this.getWaybillNo());
	     pickTest.setWaybill(this.getWaybill());
	     pickTest.setOrigOrgCode(this.getOrigOrgCode());
	     pickTest.setOrigOrgName(this.getOrigOrgName());
	     pickTest.setOrigUserCode(this.getOrigUserCode());
	     pickTest.setOrigUserName(this.getOrigUserName());
	     
	     //设置Service，设置参数
	     pickTest.setWaybillPickupService(waybillPickupService);
	     pickTest.setSimpleJdbcTemplate(simpleJdbcTemplate);
	     pickTest.setWaybillManagerService(waybillManagerService);
	     
	     suite.addTest(pickTest);//suite添加测试类
	     
	     //添加----签收测试类
	     StlLineSignServiceTest lineSignTest= new StlLineSignServiceTest("testConfirmTaking");
	     
	 	//获取专线签收dto信息
	     LineSignDto signDto=TestUtil.getLineSignDto(singDto, 
					this.getWaybillNo(), this.getDestOrgCode(),
					this.getDestOrgName(),
					SettlementConstants.LINE_SIGN, FossConstants.NO, FossConstants.NO);
	     lineSignTest.setLineSignService(lineSignService);
	     lineSignTest.setSingDto(signDto);
	     
	     //签收到达部门编码和开单到达部门编码保持一致
	     lineSignTest.setDestOrgCode(this.getDestOrgCode());
	     lineSignTest.setDestOrgName(this.getDestOrgName());
	     lineSignTest.setDestUserCode(this.getDestUserCode());
	     lineSignTest.setDestUserName(this.getDestUserName());
	     suite.addTest(lineSignTest);//suite添加测试类
	     
	     return suite;
	}
	
	/**
	 * @return  the waybill
	 */
	public  WaybillPickupInfoDto getWaybill() {
		return waybill;
	}

	
	/**
	 * @param waybill the waybill to set
	 */
	public  void setWaybill(WaybillPickupInfoDto waybill) {
		this.waybill = waybill;
	}

	
	/**
	 * @return  the origWaybill
	 */
	public static WaybillPickupInfoDto getOrigWaybill() {
		return origWaybill;
	}

	
	/**
	 * @param origWaybill the origWaybill to set
	 */
	public static void setOrigWaybill(WaybillPickupInfoDto origWaybill) {
		SettlementWaybillTest.origWaybill = origWaybill;
	}

	
	/**
	 * @return  the destWaybill
	 */
	public static WaybillPickupInfoDto getDestWaybill() {
		return destWaybill;
	}

	
	/**
	 * @param destWaybill the destWaybill to set
	 */
	public static void setDestWaybill(WaybillPickupInfoDto destWaybill) {
		SettlementWaybillTest.destWaybill = destWaybill;
	}


	
	/**
	 * @return  the singDto
	 */
	public static LineSignDto getSingDto() {
		return singDto;
	}


	
	/**
	 * @param singDto the singDto to set
	 */
	public static void setSingDto(LineSignDto singDto) {
		SettlementWaybillTest.singDto = singDto;
	}

	
	/**
	 * @return  the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	
	/**
	 * @param waybillNo the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	
	/**
	 * @return  the origOrgCode
	 */
	public String getOrigOrgCode() {
		return origOrgCode;
	}

	
	/**
	 * @param origOrgCode the origOrgCode to set
	 */
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	
	/**
	 * @return  the origOrgName
	 */
	public String getOrigOrgName() {
		return origOrgName;
	}

	
	/**
	 * @param origOrgName the origOrgName to set
	 */
	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}

	
	/**
	 * @return  the destOrgCode
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}

	
	/**
	 * @param destOrgCode the destOrgCode to set
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	
	/**
	 * @return  the destOrgName
	 */
	public String getDestOrgName() {
		return destOrgName;
	}

	
	/**
	 * @param destOrgName the destOrgName to set
	 */
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}

	
	/**
	 * @return  the origUserCode
	 */
	public String getOrigUserCode() {
		return origUserCode;
	}

	
	/**
	 * @param origUserCode the origUserCode to set
	 */
	public void setOrigUserCode(String origUserCode) {
		this.origUserCode = origUserCode;
	}

	
	/**
	 * @return  the origUserName
	 */
	public String getOrigUserName() {
		return origUserName;
	}

	
	/**
	 * @param origUserName the origUserName to set
	 */
	public void setOrigUserName(String origUserName) {
		this.origUserName = origUserName;
	}

	
	/**
	 * @return  the destUserCode
	 */
	public String getDestUserCode() {
		return destUserCode;
	}

	
	/**
	 * @param destUserCode the destUserCode to set
	 */
	public void setDestUserCode(String destUserCode) {
		this.destUserCode = destUserCode;
	}

	
	/**
	 * @return  the destUserName
	 */
	public String getDestUserName() {
		return destUserName;
	}

	
	/**
	 * @param destUserName the destUserName to set
	 */
	public void setDestUserName(String destUserName) {
		this.destUserName = destUserName;
	}
	
}
