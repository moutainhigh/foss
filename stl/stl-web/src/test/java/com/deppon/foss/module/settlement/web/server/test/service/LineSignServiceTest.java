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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/web/server/test/service/LineSignServiceTest.java
 * 
 * FILE NAME        	: LineSignServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.web.server.test.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IBillCashCollectionService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillRepaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillWriteoffService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillCashCollectionEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffOperationDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.LineSignDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.PaymentSettlementDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.dao.ICODEntityDao;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillPayCODService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ILineSignService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IPaymentSettlementService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;
import com.deppon.foss.module.settlement.web.test.BaseTestCase;
import com.deppon.foss.module.settlement.web.test.util.StlWebTestUtil;

import com.deppon.foss.util.define.FossConstants;
import com.opensymphony.xwork2.interceptor.annotations.Before;

public class LineSignServiceTest extends BaseTestCase {

	/**
	 * 根据传入的单据类型获取应收单号
	 */
	@Autowired
	private ISettlementCommonService settlementCommonService;

	@Autowired
	private IBillReceivableService billReceivableService;

	@Autowired
	private IBillPayableService billPayableService;

	@Autowired
	private IPaymentSettlementService paymentSettlementService;

	@Autowired
	private IBillRepaymentService billRepaymentService;

	@Autowired
	private ICODEntityDao codEntityDao;

	@Autowired
	private ILineSignService lineSignService;

	@Autowired
	private IBillCashCollectionService billCashCollectionService;

	@Autowired
	private IBillWriteoffService billWriteoffService;

	@Autowired
	private IBillPayCODService billPayCODService;

	@Autowired
	private ICodCommonService codCommonService;

	private String waybillNo;

	public LineSignServiceTest() {
		waybillNo = this.getWaybillNO();

		logger.info(waybillNo);
	}

	@Before
	public void setUp() {
		waybillNo = this.getPaymentNO();

		logger.info(waybillNo);
	}

	@After
	public void tearDown() {

	}

	/**
	 * 专线确认签收---走正常流程通过 签收成功后--走正常反签收流程
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-31 下午5:33:47
	 * @see
	 */
	@Test
	@Rollback(true)
	public void testLineConfirmTaking() {
		 waybillNo="200010000";
		//waybillNo = this.getWaybillNO();
		Date date = new Date();// 日期

////		// // 第一种情况全部还款，应收金额为1000 ---- 按照正常流程走得通
		this.initSignDataAndConfirmToPayment(waybillNo, date, new BigDecimal(
				"1000"), new BigDecimal("1000"));

//		// 公共使用的签收方法
		this.pubConfirmTaking(waybillNo);
		
		//判断签收记录 2012.12.11日需求变更之后改动
		//反实收货款成功后
//		this.testReversConfirmPayment(waybillNo, pocNo, new BigDecimal(
//				"1000"), new BigDecimal("1000"));
		
		// 签收服务成功后，调用反签收方法
		//this.testReverseConfirmTaking(waybillNo, date);
	}

	/**
	 * 反签收测试
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-3 下午5:47:28
	 * @see
	 */
	public void testReverseConfirmTaking(String waybillNo, Date date) {
		
		// 专线反签收
		LineSignDto signDto = new LineSignDto();
		signDto.setWaybillNo(waybillNo);
		signDto.setSignDate(new Date());
		signDto.setSignOrgCode("W011302020106");
		signDto.setSignOrgName("上海营业部");
		signDto.setIsWholeVehicle(FossConstants.NO);
		signDto.setSignType(SettlementConstants.LINE_SIGN);

		// 专线反签收
		try {
			lineSignService.reverseConfirmTaking(signDto,
					StlWebTestUtil.getCurrentInfo());

			// 调用反签收方法成功后校验对应的应收单的确认收入日期是否为空
			BillReceivableConditionDto billReceivableConDto = new BillReceivableConditionDto(
					waybillNo);
			billReceivableConDto
					.setSourceBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL);
			billReceivableConDto
					.setBillTypes(new String[] {
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE,
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE });
			List<BillReceivableEntity> list = this.billReceivableService
					.queryBillReceivableByCondition(billReceivableConDto);
			if (CollectionUtils.isNotEmpty(list)) {
				for (BillReceivableEntity entity : list) {
					Assert.assertTrue(entity.getConrevenDate() == null);
				}
			}
		} catch (SettlementException e) {
			logger.error(e.getMessage(),e);
		}
	}

	/**
	 * 反签收测试
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-3 下午5:47:28
	 * @see
	 */
	public void testReverseConfirmTaking(String waybillNo, Date date,
			String orgCode, String signType) {

		// 专线反签收
		LineSignDto signDto = new LineSignDto();
		signDto.setWaybillNo(waybillNo);
		signDto.setSignDate(new Date());
		signDto.setSignOrgCode(orgCode);
		signDto.setSignOrgName("上海营业部");
		signDto.setIsWholeVehicle(FossConstants.YES);
		signDto.setSignType(signType);

		// 专线反签收
		try {
			lineSignService.reverseConfirmTaking(signDto,
					StlWebTestUtil.getCurrentInfo());
		} catch (SettlementException e) {
			logger.error(e.getMessage(),e);
		}
	}

	/**
	 * (不能签收)运单号 为空
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-6 下午6:59:58
	 * @see
	 */
	@Test
	@Rollback(true)
	public void testLineConfirmTakingOne() {

		Date date = new Date();// 日期
		waybillNo = this.getWaybillNO();// 运单号

		// 初始化数据和实收货款操作
		initSignDataAndConfirmToPayment(waybillNo, date,
				new BigDecimal("1000"), new BigDecimal("1000"));

		// 公共使用的签收方法
		this.pubConfirmTaking(null);
	}

	/**
	 * (不能签收)签收部门编码为空
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-6 下午6:59:58
	 * @see
	 */
	@Test
	@Rollback(true)
	public void testLineConfirmTakingByOrgCodeIsEmpty() {
		Date date = new Date();// 日期
		waybillNo = this.getWaybillNO();// 运单号

		// 初始化数据和实收货款操作
		initSignDataAndConfirmToPayment(waybillNo, date,
				new BigDecimal("1000"), new BigDecimal("1000"));

		// 公共使用的签收方法
		this.pubConfirmTaking(waybillNo, "", SettlementConstants.LINE_SIGN);
	}

	/**
	 * 签收部门编码和到达部门编码不一致情况
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-7 上午8:49:33
	 * @see
	 */
	@Test
	@Rollback(true)
	public void testLineConfirmTakingByOrgCodeIsNotSame() {
		Date date = new Date();// 日期
		waybillNo = this.getWaybillNO();// 运单号

		// 初始化数据和实收货款操作
		initSignDataAndConfirmToPayment(waybillNo, date,
				new BigDecimal("1000"), new BigDecimal("1000"));

		// 公共使用的签收方法 单据为 “W011302020106”
		this.pubConfirmTaking(waybillNo, "W0113020201061",
				SettlementConstants.LINE_SIGN);
	}

	/**
	 * (不能签收)签收类型为空（专线/偏线/空运签收）
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-6 下午6:59:58
	 * @see
	 */
	@Test
	@Rollback(true)
	public void testLineConfirmTakingBySignTypeEmpty() {
		Date date = new Date();// 日期
		waybillNo = this.getWaybillNO();// 运单号

		// 初始化数据和实收货款操作
		initSignDataAndConfirmToPayment(waybillNo, date,
				new BigDecimal("1000"), new BigDecimal("1000"));

		// 公共使用的签收方法
		this.pubConfirmTaking(waybillNo, "W011302020106", null);
	}

	/**
	 * (不能签收)签收类型不合法（专线/偏线/空运签收）
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-6 下午6:59:58
	 * @see
	 */
	@Test
	@Rollback(true)
	public void testLineConfirmTakingBySignType() {
		Date date = new Date();// 日期
		waybillNo = this.getWaybillNO();// 运单号

		// 初始化数据和实收货款操作
		initSignDataAndConfirmToPayment(waybillNo, date,
				new BigDecimal("1000"), new BigDecimal("1000"));

		// 公共使用的签收方法
		this.pubConfirmTaking(waybillNo, "W011302020106", "001");
	}

	/**
	 * (不能签收)还款金额都小于应收金额，存在未核销金额，不能进行后续的签收操作
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-6 下午6:59:58
	 * @see
	 */
	@Test
	@Rollback(true)
	public void testLineConfirmTakingTwo() {

		Date date = new Date();// 日期
		waybillNo = this.getWaybillNO();// 运单号

		// 初始化数据和实收货款操作
		initSignDataAndConfirmToPayment(waybillNo, date, new BigDecimal("800"),
				new BigDecimal("800"));

		// 公共使用的签收方法
		this.pubConfirmTaking(waybillNo);
	}

	/**
	 * (不能签收)到付实收货款金额等于应收单金额，实收代收货款金额为0
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-6 下午7:13:02
	 * @see
	 */
	@Test
	@Rollback(true)
	public void testLineConfirmTakingThree() {
		Date date = new Date();// 日期
		waybillNo = this.getWaybillNO();// 运单号

		// 初始化数据和实收货款操作
		initSignDataAndConfirmToPayment(waybillNo, date,
				new BigDecimal("1000"), new BigDecimal("0"));

		// 公共使用的签收方法
		this.pubConfirmTaking(waybillNo);
	}

	/**
	 * (不能签收)第四种： 到付实收货款金额等于0，代收货款金额为应收代收货款金额 不能进行后续的签收操作
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-6 下午7:13:02
	 * @see
	 */
	@Test
	@Rollback(true)
	public void testLineConfirmTakingFour() {
		Date date = new Date();// 日期
		waybillNo = this.getWaybillNO();// 运单号

		// 初始化数据和实收货款操作
		initSignDataAndConfirmToPayment(waybillNo, date, new BigDecimal("0"),
				new BigDecimal("1000"));

		// 公共使用的签收方法
		this.pubConfirmTaking(waybillNo);
	}

	/**
	 * （到付转临欠/月结后可签收）实收货款为“0” 才能进行下一步的到付转临欠/月结,才能进行后续的签收操作
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-6 下午7:24:17
	 * @see
	 */
	@Test
	@Rollback(true)
	public void testLineConfirmTakingFive() {

		Date date = new Date();// 日期
		waybillNo = this.getWaybillNO();// 运单号

		// 初始化数据和实收货款操作
		initSignDataAndConfirmToPayment(waybillNo, date, new BigDecimal("0"),
				new BigDecimal("1000"));

		// 未收取到付运费，进行到付转临欠/月结操作
		this.confirmToBillReceivable(waybillNo);

		// 转临欠/月结后，调用公共使用的签收方法
		this.pubConfirmTaking(waybillNo);
	}

	/**
	 * （不能签收）实收货款金额和实收代收货款大于实际还款金额
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-6 下午7:34:05
	 * @see
	 */
	@Test
	@Rollback(true)
	public void testLineConfirmTakingSix() {
		Date date = new Date();// 日期
		waybillNo = this.getWaybillNO();// 运单号

		LineSignDto signDto = new LineSignDto();
		signDto.setWaybillNo(waybillNo);
		signDto.setSignDate(new Date());
		signDto.setSignOrgCode("W011302020106");
		signDto.setSignOrgName("上海营业部");
		signDto.setIsWholeVehicle(FossConstants.YES);
		signDto.setSignType(SettlementConstants.LINE_SIGN);// 专线签收
		try {
			// 初始化数据和实收货款操作
			initSignDataAndConfirmToPayment(waybillNo, date, new BigDecimal(
					"1000.5"), new BigDecimal("1000.5"));

			// 调用签收方法
			lineSignService.confirmTaking(signDto,
					StlWebTestUtil.getCurrentInfo());
		} catch (SettlementException e) {
			logger.error(e.getErrorCode());
		}
	}

	/**
	 * 签收之后，不能重复调用反签收接口
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-6 下午8:14:59
	 * @see
	 */
	@Test
	@Rollback(true)
	public void testLineConfirmTakingSeven() {
		Date date = new Date();// 日期

		// 第一种情况全部还款，应收金额为1000 ---- 按照正常流程走得通
		// 因为测试把部分方法提取为公共方法，目的减少代码量
		this.initSignDataAndConfirmToPayment(waybillNo, date, new BigDecimal(
				"1000"), new BigDecimal("1000"));

		// 公共使用的签收方法
		this.pubConfirmTaking(waybillNo);

		// 重复调用签收方法
		this.pubConfirmTaking(waybillNo);

	}

	/************************************************ 测试不能反签收操作 ************************************************/

	/**
	 * (不能反签收)运单号 为空
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-6 下午6:59:58
	 * @see
	 */
	@Test
	@Rollback(true)
	public void testNotLineReverseConfirmTakingOne() {

		Date date = new Date();// 日期
		waybillNo = this.getWaybillNO();// 运单号

		// 初始化数据和实收货款操作
		initSignDataAndConfirmToPayment(waybillNo, date,
				new BigDecimal("1000"), new BigDecimal("1000"));

		// 公共使用的签收方法
		this.pubConfirmTaking(waybillNo);

		// 签收服务成功后，调用反签收方法
		this.testReverseConfirmTaking(null, date);
	}

	/**
	 * (不能反签收)签收部门编码为空
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-6 下午6:59:58
	 * @see
	 */
	@Test
	@Rollback(true)
	public void testNotLineReverseConfirmTakingByOrgCodeIsEmpty() {
		Date date = new Date();// 日期
		waybillNo = this.getWaybillNO();// 运单号

		// 初始化数据和实收货款操作
		initSignDataAndConfirmToPayment(waybillNo, date,
				new BigDecimal("1000"), new BigDecimal("1000"));

		// 公共使用的签收方法
		this.pubConfirmTaking(waybillNo);
		// 签收服务成功后，调用反签收方法
		this.testReverseConfirmTaking(waybillNo, date, "",
				SettlementConstants.LINE_SIGN);
	}

	/**
	 * 反签收部门编码不一致情况
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-7 上午8:47:41
	 * @see
	 */
	@Test
	@Rollback(true)
	public void testNotLineReverseConfirmTakingByOrgCodeIsNotSame() {
		Date date = new Date();// 日期
		waybillNo = this.getWaybillNO();// 运单号

		// 初始化数据和实收货款操作
		initSignDataAndConfirmToPayment(waybillNo, date,
				new BigDecimal("1000"), new BigDecimal("1000"));

		// 公共使用的签收方法
		this.pubConfirmTaking(waybillNo);
		// 输入的反签收部门编码不一致 单据中的到达部门为 W011302020106
		this.testReverseConfirmTaking(waybillNo, date, "W0113020201061",
				SettlementConstants.LINE_SIGN);
	}

	/**
	 * (不能反签收)反签收类型为空（专线/偏线/空运签收）
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-6 下午6:59:58
	 * @see
	 */
	@Test
	@Rollback(true)
	public void testNotLineReverseConfirmTakingTypeEmpty() {
		Date date = new Date();// 日期
		waybillNo = this.getWaybillNO();// 运单号

		// 初始化数据和实收货款操作
		initSignDataAndConfirmToPayment(waybillNo, date,
				new BigDecimal("1000"), new BigDecimal("1000"));

		// 公共使用的签收方法
		this.pubConfirmTaking(waybillNo, "W011302020106", null);
	}

	/**
	 * (不能反签收)反签收类型不合法（专线/偏线/空运签收）
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-6 下午6:59:58
	 * @see
	 */
	@Test
	@Rollback(true)
	public void testNotLineReverseConfirmTakingBySignType() {
		Date date = new Date();// 日期
		waybillNo = this.getWaybillNO();// 运单号

		// 初始化数据和实收货款操作
		initSignDataAndConfirmToPayment(waybillNo, date,
				new BigDecimal("1000"), new BigDecimal("1000"));

		// 公共使用的签收方法
		this.pubConfirmTaking(waybillNo, "W011302020106", "001");
	}

	/**
	 * 专线确认签收---走正常流程通过 签收成功后--加入不能反签收的一些判断内容 有手工核销过的单据，应付单的核销金额大于0不能进行反签收操作
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-31 下午5:33:47
	 * @see
	 */
	@Test
	@Rollback(true)
	public void testNotLineReverseConfirmTaking() {
		Date date = new Date();// 日期
		waybillNo = this.getWaybillNO();// 运单号

		// 因为测试把部分方法提取为公共方法，目的减少代码量
		this.initSignDataAndConfirmToPayment(waybillNo, date, new BigDecimal(
				"0"), new BigDecimal("1000"));

		// 进行应收冲应付核销操作 手工核销（应付单的已核销金额大于0，不允许进行反签收操作）
		this.writeoffReceibableAndPayable(waybillNo,
				StlWebTestUtil.getCurrentInfo());

		// 公共使用的签收方法
		this.pubConfirmTaking(waybillNo);

		// 签收服务成功后，调用反签收方法
		this.testReverseConfirmTaking(waybillNo, date);

	}

	/**
	 * 专线确认签收---走正常流程通过 签收成功后--加入不能反签收的一些判断内容 第二种情况：
	 * 代收货款状态如果为：资金部冻结、退款中、退款成功，不能进行反签收操作
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-31 下午5:33:47
	 * @see
	 */
	@Test
	@Rollback(true)
	@Ignore
	public void testNotLineReverseConfirmTakingTwo() {
		/**
		 * 因代收货款直接去掉了，更新状态的方法，故废掉此测试方法。 2012-11-09
		 */

		Date date = new Date();// 日期
		waybillNo = this.getWaybillNO();// 运单号

		// 因为测试把部分方法提取为公共方法，目的减少代码量
		this.initSignDataAndConfirmToPayment(waybillNo, date, new BigDecimal(
				"1000"), new BigDecimal("1000"));

		// 公共使用的签收方法
		this.pubConfirmTaking(waybillNo);

		// 第一步：查询代收货款状态如果为：资金部冻结、退款中、退款成功，不能进行反签收操作
		this.testUpdateCodStatus(waybillNo);

		// 签收服务成功后，调用反签收方法
		this.testReverseConfirmTaking(waybillNo, date);

	}

	/**
	 * 存在理赔应付单，不能进行反签收操作
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-6 下午8:01:59
	 * @see
	 */
	@Test
	@Rollback(true)
	public void testNotLineReverseConfirmTakingThree() {
		Date date = new Date();// 日期
		waybillNo = this.getWaybillNO();// 运单号

		// 因为测试把部分方法提取为公共方法，目的减少代码量
		this.initSignDataAndConfirmToPayment(waybillNo, date, new BigDecimal(
				"1000"), new BigDecimal("1000"));

		// 公共使用的签收方法
		this.pubConfirmTaking(waybillNo);

		// 插入理赔应付单
		this.addClaimBillPayable(waybillNo,
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM,
				date);

		// 签收服务成功后，调用反签收方法
		this.testReverseConfirmTaking(waybillNo, date);

	}

	/**
	 * 存在服务补救应付单，不能进行反签收操作
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-6 下午8:01:59
	 * @see
	 */
	@Test
	@Rollback(true)
	public void testNotLineReverseConfirmTakingFour() {
		Date date = new Date();// 日期
		waybillNo = this.getWaybillNO();// 运单号

		// 因为测试把部分方法提取为公共方法，目的减少代码量
		this.initSignDataAndConfirmToPayment(waybillNo, date, new BigDecimal(
				"1000"), new BigDecimal("1000"));

		// 公共使用的签收方法
		this.pubConfirmTaking(waybillNo);

		// 插入服务补救应付单
		this.addClaimBillPayable(
				waybillNo,
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__COMPENSATION,
				date);

		// 签收服务成功后，调用反签收方法
		this.testReverseConfirmTaking(waybillNo, date);

	}

	/**
	 * 修改应付单的冻结状态为已冻结,不能进行反签收操作
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-6 下午8:07:34
	 * @see
	 */
	@Test
	@Rollback(true)
	public void testNotLineReverseConfirmTakingFive() {
		Date date = new Date();// 日期
		waybillNo = this.getWaybillNO();// 运单号

		// 因为测试把部分方法提取为公共方法，目的减少代码量
		this.initSignDataAndConfirmToPayment(waybillNo, date, new BigDecimal(
				"1000"), new BigDecimal("1000"));

		// 公共使用的签收方法
		this.pubConfirmTaking(waybillNo);

		// 修改应付单的冻结状态为已冻结----不能进行反签收操作
		List<BillPayableEntity> lists = this.testFrozenBillPayable(waybillNo);// 冻结应付单

		// 测试通过验证过滤后，可设置传入的参数为空，不在调用这个方法
		// List<BillPayableEntity> lists = this.testFrozenBillPayable(null);//
		// 冻结应付单

		// 取消冻结应付单之后-可进行反签收操作
		// if (CollectionUtils.isNotEmpty(lists)) {
		// this.testCancelFrozenBillPayable(lists);
		// }

		// 不调用这个方法,lists设置为空即可
		lists = null;
		this.testCancelFrozenBillPayable(lists);

		// 调用反签收方法
		this.testReverseConfirmTaking(waybillNo, date);

	}

	/**
	 * 
	 * 修改应付单的支付状态为：已支付或付款中不能进行反签收操作
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-6 下午8:09:39
	 * @see
	 */
	@Test
	@Rollback(true)
	public void testNotLineReverseConfirmTakingSix() {
		Date date = new Date();// 日期
		waybillNo = this.getWaybillNO();// 运单号

		// 因为测试把部分方法提取为公共方法，目的减少代码量
		this.initSignDataAndConfirmToPayment(waybillNo, date, new BigDecimal(
				"1000"), new BigDecimal("1000"));

		// 公共使用的签收方法
		this.pubConfirmTaking(waybillNo);

		// 应付单的支付状态为：已支付或付款中不能进行反签收操作
		this.testPayForBillPayable(waybillNo);

		// 测试通过，不在调用这个方法
		// this.testPayForBillPayable(null);

		// 更改支付状态为：未支付--可以进行反签收操作
		// this.testCancelPayForBillPayable(waybillNo);

		// 不在调用这个方法 waybillNo参数设置为空即可
		this.testCancelPayForBillPayable(null);

		// 调用反签收方法
		this.testReverseConfirmTaking(waybillNo, date);
	}

	/**
	 * 锁定应收单（不能进行反签收操作）
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-7 上午10:14:16
	 * @see
	 */
	@Test
	@Rollback(true)
	public void testNotLineReverseConfirmTakingSeven() {
		Date date = new Date();// 日期
		waybillNo = this.getWaybillNO();// 运单号

		// 因为测试把部分方法提取为公共方法，目的减少代码量
		this.initSignDataAndConfirmToPayment(waybillNo, date, new BigDecimal(
				"1000"), new BigDecimal("1000"));

		// 公共使用的签收方法
		this.pubConfirmTaking(waybillNo);

		// 锁定运单对应的应收单
		this.lockBillReceivable(waybillNo);

		// 锁定应收单后，可解锁应收单 传入waybillNo为空即不调用这个方法
		// unlockBillReceivable(waybillNo);
		unlockBillReceivable(null);

		// 调用反签收方法
		this.testReverseConfirmTaking(waybillNo, date);
	}

	/**
	 * 
	 * 已经反签收过了，再次调用反签收接口
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-6 下午8:12:45
	 * @see
	 */
	@Test
	@Rollback(true)
	public void testNotLineReverseConfirmTakingEight() {
		Date date = new Date();// 日期
		waybillNo = this.getWaybillNO();// 运单号

		// 因为测试把部分方法提取为公共方法，目的减少代码量
		this.initSignDataAndConfirmToPayment(waybillNo, date, new BigDecimal(
				"1000"), new BigDecimal("1000"));

		// 公共使用的签收方法
		this.pubConfirmTaking(waybillNo);

		// 调用反签收方法
		this.testReverseConfirmTaking(waybillNo, date);

		// 重复调用反签收方法
		this.testReverseConfirmTaking(waybillNo, date);
	}

	/********************************************************** 下面是帮助方法 *************************************************************/

	// 公用调用签收方法
	private void pubConfirmTaking(String waybillNo) {
		LineSignDto signDto = new LineSignDto();
		signDto.setWaybillNo(waybillNo);
		signDto.setSignDate(new Date());
		signDto.setSignOrgCode("W011305080202");
		signDto.setSignOrgName("北京石景山营业部");
		signDto.setIsWholeVehicle(FossConstants.YES);
		signDto.setSignType(SettlementConstants.LINE_SIGN);// 专线签收
		try {
			// 调用签收方法
			lineSignService.confirmTaking(signDto,
					StlWebTestUtil.getCurrentInfo());

			// 调用签收方法成功后校验对应的应收单的确认收入日期是否为空
			BillReceivableConditionDto billReceivableConDto = new BillReceivableConditionDto(
					waybillNo);
			billReceivableConDto
					.setSourceBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL);
			billReceivableConDto
					.setBillTypes(new String[] {
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE,
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE });
			List<BillReceivableEntity> list = this.billReceivableService
					.queryBillReceivableByCondition(billReceivableConDto);
			if (CollectionUtils.isNotEmpty(list)) {
            for (BillReceivableEntity entity : list) {
					Assert.assertTrue(entity.getConrevenDate() != null);
				}
			}
		} catch (SettlementException e) {
			logger.error(e.getMessage(),e);
		}
	}

	// 公用调用签收方法-2
	private void pubConfirmTaking(String waybillNo, String destOrgCode,
			String signType) {
		LineSignDto signDto = new LineSignDto();
		signDto.setWaybillNo(waybillNo);
		signDto.setSignDate(new Date());
		signDto.setSignOrgCode(destOrgCode);
		signDto.setSignOrgName("上海营业部");
		signDto.setIsWholeVehicle(FossConstants.YES);
		signDto.setSignType(signType);// 专线签收
		try {
			// 调用签收方法
			lineSignService.confirmTaking(signDto,
					StlWebTestUtil.getCurrentInfo());
		} catch (SettlementException e) {
			logger.error(e.getErrorCode(),e);
		}
	}

	/**
	 * 初始化财务数据和实收货款操作
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-6 下午7:18:42
	 * @param waybillNo
	 * @param date
	 * @return
	 * @see
	 */
	private String initSignDataAndConfirmToPayment(String waybillNo, Date date,
			BigDecimal toPayFee, BigDecimal codFee) {
		// 初始化财务单据数据
		initBills(waybillNo, date);

		/****************************************** 签收前，先进行实收货款操作 ****************************************************/
		// 实收货款
		PaymentSettlementDto dto = new PaymentSettlementDto();
		dto.setWaybillNo(waybillNo);
		dto.setBusinessDate(new Date());
		dto.setDestOrgCode("W011302020106");
		dto.setDestOrgName("上海营业部");
		dto.setCustomerCode("1");
		dto.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
		dto.setCustomerCode("0002333122");
		dto.setCustomerName("张三");

		// 设置动态获取还款批次号
		String pcNo = getPaymentNO();
		dto.setSourceBillNo(pcNo);

		dto.setToPayFee(toPayFee);
		dto.setCodFee(codFee);// 实收代收货款金额1000
		try {
			// 调用实收货款方法
			paymentSettlementService.confirmToPayment(dto,
					StlWebTestUtil.getCurrentInfo());

			// 查询是否存在还款单记录
			List<String> sourceBillNos = new ArrayList<String>();
			sourceBillNos.add(dto.getSourceBillNo());
			List<BillRepaymentEntity> list = billRepaymentService
					.queryBySourceBillNOs(sourceBillNos, FossConstants.ACTIVE);

			// 存在还款单记录
			Assert.assertTrue(CollectionUtils.isNotEmpty(list));
		} catch (SettlementException e) {
			logger.error(e.getErrorCode(),e);
		}
		return pcNo;

	}
	
	
	/**
	 * 反实收货款
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-31 上午11:00:23
	 * @see toPayFee:实收货款费用；codFee实收代收货款费用 ，根据查询出来的应收单进行比较
	 */
	public void testReversConfirmPayment(String waybillNo, String sourceBillNo,
			BigDecimal toPayFee, BigDecimal codFee) {
		PaymentSettlementDto dto = new PaymentSettlementDto();
		dto.setWaybillNo(waybillNo);
		dto.setBusinessDate(new Date());
		dto.setDestOrgCode("W011302020106");
		dto.setDestOrgName("上海营业部");
		dto.setCustomerCode("1");
		dto.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
		dto.setCustomerCode("0002333122");
		
		dto.setCustomerName("张三");
		dto.setSourceBillNo(sourceBillNo);
		try {
			paymentSettlementService.reversConfirmPayment(dto,
					StlWebTestUtil.getCurrentInfo());

			// 查询是否存在有效的还款单记录
			List<String> sourceBillNos = new ArrayList<String>();
			sourceBillNos.add(sourceBillNo);
			List<BillRepaymentEntity> list = billRepaymentService
					.queryBySourceBillNOs(sourceBillNos, FossConstants.ACTIVE);

			// 存在提示错误信息
			Assert.assertEquals(
					CollectionUtils.isNotEmpty(list) ? false : true, true);

		} catch (BusinessException e) {
			logger.error(e.getErrorCode(),e);
		}
	}

	/**
	 * 锁定应收单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-7 上午11:07:14
	 * @param waybillNo
	 * @see
	 */
	private void lockBillReceivable(String waybillNo) {
		if (StringUtils.isNotEmpty(waybillNo)) {
			BillReceivableConditionDto dto = new BillReceivableConditionDto(
					waybillNo);
			List<BillReceivableEntity> list = this.billReceivableService
					.queryBillReceivableByCondition(dto);
			if (CollectionUtils.isNotEmpty(list)) {
				List<String> ids = new ArrayList<String>();
				for (BillReceivableEntity entity : list) {
					ids.add(entity.getId());
				}
				BillReceivableDto receDto = new BillReceivableDto();
				receDto.setBillReceivables(list);
				Date date = DateUtils.addMinutes(new Date(), 30);
				receDto.setUnlockDateTime(date);
				receDto.setLockCustomerCode("TODOC");
				receDto.setLockCustomerName("TODON");
				try {
					this.billReceivableService.lockBillReceivable(receDto,
							StlWebTestUtil.getCurrentInfo());

					// 加锁之后，判断应收单的锁定时间是否为空

					List<BillReceivableEntity> lists = this.billReceivableService
							.queryBillReceivableByCondition(dto);
					for (BillReceivableEntity entity : lists) {
						Assert.assertTrue(entity.getUnlockDateTime() != null);
					}
				} catch (SettlementException e) {
					logger.error(e.getErrorCode(),e);
				}
			}
		}
	}

	/**
	 * 解锁应收单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-7 上午11:08:21
	 * @param waybillNo
	 * @see
	 */
	private void unlockBillReceivable(String waybillNo) {
		if (StringUtils.isNotEmpty(waybillNo)) {
			BillReceivableConditionDto dto = new BillReceivableConditionDto(
					waybillNo);
			List<BillReceivableEntity> list = this.billReceivableService
					.queryBillReceivableByCondition(dto);
			if (CollectionUtils.isNotEmpty(list)) {
				List<String> ids = new ArrayList<String>();
				for (BillReceivableEntity entity : list) {
					ids.add(entity.getId());
				}
				BillReceivableDto receDto = new BillReceivableDto();
				receDto.setBillReceivables(list);
				receDto.setUnlockDateTime(null);
				receDto.setLockCustomerCode("");
				receDto.setLockCustomerName("");
				try {
					this.billReceivableService.unlockBillReceivable(receDto,
							StlWebTestUtil.getCurrentInfo());
					List<BillReceivableEntity> lists = this.billReceivableService
							.queryBillReceivableByCondition(dto);
					for (BillReceivableEntity entity : lists) {
						Assert.assertTrue(entity.getUnlockDateTime() == null);
					}
				} catch (SettlementException e) {
					logger.error(e.getErrorCode(),e);
				}
			}
		}
	}

	/**
	 * 插入理赔应付单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-6 下午8:01:32
	 * @param waybillNo
	 * @param date
	 * @see
	 */
	private void addClaimBillPayable(String waybillNo, String billType,
			Date date) {
		// 第二步：新增一条理赔应付单；设置该运单存在理赔应付单，不能进行反签收操作

		BillPayableEntity ciaimbillPayable = StlWebTestUtil
				.getBillPayableEntity(waybillNo, this.getPayableNO(billType),
						billType, date);
		try {
			this.billPayableService.addBillPayable(ciaimbillPayable,
					StlWebTestUtil.getCurrentInfo());
		} catch (SettlementException e) {
			logger.error(e.getErrorCode(),e);
		}
	}

	/**
	 * 修改代收货款状态为资金部冻结、退款中、退款成功
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-6 下午6:08:28
	 * @param waybillNo
	 * @see
	 */
	private void testUpdateCodStatus(String waybillNo) {
		if (StringUtils.isNotEmpty(waybillNo)) {
			List<String> codWaybillNos = new ArrayList<String>();
			codWaybillNos.add(waybillNo);
			CODEntity cod = this.codCommonService.queryByWaybill(waybillNo);
			if (cod != null && StringUtils.isNotEmpty(cod.getId())) {
				// 修改代收货款状态为资金部冻结----不能进行反签收操作
				// billPayCODService.updateBillCODState(cod.getId(),
				// SettlementDictionaryConstants.COD__STATUS__FUND_FREEZE,
				// "zbw",
				// "046644");

				// //设置代收货款状态为：退款中----不能进行反签收操作
				// billPayCODService.updateBillCODState(cod.getId(),
				// SettlementDictionaryConstants.COD__STATUS__RETURNING, "zbw",
				// "046644");

				// 设置代收货款状态为：已退款----不能进行反签收操作
				// billPayCODService.updateBillCODState(cod.getId(),
				// SettlementDictionaryConstants.COD__STATUS__RETURNED,
				// "test", "test2");

				// 设置代收货款状态为：退款失败 ---可进行反签收操作
				// billPayCODService.updateBillCODState(cod.getId(),
				// SettlementDictionaryConstants.COD__STATUS__RETURN_FAILURE,
				// "zbw", "046644");
			}
		}

	}

	/**
	 * 冻结应付单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-6 上午10:16:39
	 * @see
	 */
	private List<BillPayableEntity> testFrozenBillPayable(String waybillNo) {
		if (StringUtils.isNotEmpty(waybillNo)) {
			BillPayableConditionDto dto = new BillPayableConditionDto(waybillNo);
			dto.setBillTypes(new String[] {
					SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD,
					SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE,
					SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST });
			List<BillPayableEntity> list = this.billPayableService
					.queryBillPayableByCondition(dto);
			List<BillPayableEntity> lists = new ArrayList<BillPayableEntity>();
			if (CollectionUtils.isNotEmpty(list)) {
				for (BillPayableEntity entity : list) {
					if (entity != null
							&& StringUtils.isNotEmpty(entity.getId())) {
						entity.setFrozenStatus(SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__FROZEN);
						entity.setFrozenTime(new Date());
						entity.setFrozenUserCode("TODOID");
						entity.setFrozenUserName("TODONAME");
						try{
							this.billPayableService.frozenBillPayable(entity,
									StlWebTestUtil.getCurrentInfo());
						}catch(SettlementException e){
						    logger.error(e.getMessage(),e);
						}
						lists.add(entity);
					}
				}
			}
			return lists;
		}
		return null;
	}

	/**
	 * 取消冻结应付单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-6 上午10:17:25
	 * @param waybillNo
	 * @see
	 */
	private void testCancelFrozenBillPayable(List<BillPayableEntity> list) {
		if (CollectionUtils.isNotEmpty(list)) {
			for (BillPayableEntity entity : list) {
				if (SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__FROZEN
						.equals(entity.getFrozenStatus())) {// 已冻结
					entity.setFrozenStatus(SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__NOT_FROZEN);
					entity.setFrozenTime(null);
					entity.setFrozenUserCode(null);
					entity.setFrozenUserName(null);
					try{
						this.billPayableService.cancelFrozenBillPayable(entity,
								StlWebTestUtil.getCurrentInfo());
					}catch(SettlementException e){
						logger.error(e.getMessage(),e);
					}
				}
			}
		}
	}

	/**
	 * 批量修改应付单的支付状态 和付款单号
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-6 下午3:25:17
	 * @param waybillNo
	 * @see
	 */
	private void testPayForBillPayable(String waybillNo) {
		if (StringUtils.isNotEmpty(waybillNo)) {
			BillPayableConditionDto dto = new BillPayableConditionDto(waybillNo);
			dto.setBillTypes(new String[] {
					SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD,
					SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE,
					SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST });
			List<BillPayableEntity> list = this.billPayableService
					.queryBillPayableByCondition(dto);
			if (CollectionUtils.isNotEmpty(list)) {
				for (BillPayableEntity payableEntity : list) {
					payableEntity.setPaymentAmount(new BigDecimal("1000"));
					payableEntity.setPaymentNotes("测试");
					BillPayableDto payableDto = new BillPayableDto();
					org.springframework.beans.BeanUtils.copyProperties(payableEntity, payableDto);
					payableDto.setPaymentNo("FK123456");
					try{
						this.billPayableService.payForBillPayable(payableDto,
								StlWebTestUtil.getCurrentInfo());
					}catch(SettlementException e){
						logger.error(e.getErrorCode(),e);
					}
				}
			}
		}
	}

	/**
	 * 批量取消应付单的支付状态和付款单号
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-6 下午3:28:53
	 * @param waybillNo
	 * @see
	 */
	private void testCancelPayForBillPayable(String waybillNo) {
		if (StringUtils.isNotEmpty(waybillNo)) {
			BillPayableConditionDto dto = new BillPayableConditionDto(waybillNo);
			dto.setBillTypes(new String[] {
					SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD,
					SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE,
					SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST });
			List<BillPayableEntity> list = this.billPayableService
					.queryBillPayableByCondition(dto);
			if (CollectionUtils.isNotEmpty(list)) {
				for (BillPayableEntity payableEntity : list) {
					payableEntity.setPaymentAmount(new BigDecimal("1000"));
					payableEntity.setPaymentNotes("测试");
					BillPayableDto payableDto = new BillPayableDto();
					org.springframework.beans.BeanUtils.copyProperties(payableEntity, payableDto);
					payableDto.setPaymentNo("");
					payableDto.setPaymentAmount(null);
					payableEntity.setPaymentNotes("");
					try{
						this.billPayableService.cancelPayForBillPayable(payableDto,
								StlWebTestUtil.getCurrentInfo());
					}catch(SettlementException e){
						logger.error(e.getMessage(),e);
					}
				}
			}
		}
	}

	/**
	 * 反签收测试---制定到某一个运单号上面
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-3 下午5:47:28
	 * @see
	 */
	@Test
	@Rollback(true)
	@Ignore
	public void testReverseConfirmTakings() {
		// 签收接口
		LineSignDto signDto = new LineSignDto();
		signDto.setWaybillNo("1351942632531");
		signDto.setSignDate(new Date());
		signDto.setSignOrgCode("W011302020106");
		signDto.setSignOrgName("上海营业部");
		signDto.setIsWholeVehicle(FossConstants.YES);
		signDto.setSignType(SettlementConstants.LINE_SIGN);
		try {
			// 专线反签收
			lineSignService.reverseConfirmTaking(signDto,
					StlWebTestUtil.getCurrentInfo());
		} catch (SettlementException e) {
			logger.error(e.getMessage(),e);
		}
	}

	/**
	 * 调用应收冲应付-----为反签收接口校验做准备 反签收时，不能存在手工核销的应收单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-5 下午4:28:55
	 * @param waybillNo
	 * @see
	 */
	private void writeoffReceibableAndPayable(String waybillNo,
			CurrentInfo currentInfo) {
		// 运单号不为空时，不调用此方法运单号设置为空
		if (StringUtils.isNotEmpty(waybillNo)) {
			BillWriteoffOperationDto writeOffDto = new BillWriteoffOperationDto();

			// 查询代收货款应收单-运单
			BillReceivableConditionDto receConDto = new BillReceivableConditionDto(
					waybillNo);
			receConDto
					.setBillTypes(new String[] { SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE });// 到达运费应收单

			List<BillReceivableEntity> receivables = this.billReceivableService
					.queryBillReceivableByCondition(receConDto);
			Assert.assertTrue(CollectionUtils.isNotEmpty(receivables) ? receivables
					.size() == 1 : false);
			writeOffDto.setBillReceivableEntitys(receivables);

			// 查询代收货款应付单-运单
			BillPayableConditionDto payableConDto = new BillPayableConditionDto(
					waybillNo);
			payableConDto
					.setBillTypes(new String[] { SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD // 代收货款应付单
					});
			List<BillPayableEntity> payables = this.billPayableService
					.queryBillPayableByCondition(payableConDto);
			Assert.assertTrue(CollectionUtils.isNotEmpty(payables) ? payables
					.size() == 1 : false);
			writeOffDto.setBillPayableEntitys(payables);

			// 核销批次号
			writeOffDto.setWriteoffBatchNo(this.settlementCommonService
					.getSettlementNoRule(SettlementNoRuleEnum.HX_BN));

			writeOffDto
					.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);
			try{
				this.billWriteoffService.writeoffReceibableAndPayable(writeOffDto,
						currentInfo);
			}catch(SettlementException e){
				logger.error(e.getMessage(),e);
			}
			
		}
	}

	/**
	 * 到付转临欠/月结
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-6 上午10:13:42
	 * @param waybillNo
	 * @see
	 */
	private void confirmToBillReceivable(String waybillNo) {
		// 到付应收单转临欠/月结之后，到达运费应收单不用判断是否存在未核销金额----接上面的第五种情况
		PaymentSettlementDto payDto = new PaymentSettlementDto();
		// 业务日期
		payDto.setBusinessDate(new Date());
		// 运单号
		payDto.setWaybillNo(waybillNo);
		// 到达部门
		payDto.setDestOrgCode("W011302020106");
		payDto.setDestOrgName("上海营业部");
		payDto.setCustomerCode("1");
		payDto.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT);// 付款方式为临时欠款
		try {
			// 到付转临欠/月结
			this.paymentSettlementService.confirmToBillReceivable(payDto,
					StlWebTestUtil.getCurrentInfo());
		} catch (SettlementException e) {
			logger.error(e.getMessage(),e);
		}
	}

	/**
	 * 插入各种财务单据
	 */
	/******************************************** 初始化开单财务单据数据 *********************************************************/

	private void initBills(String waybillNo, Date date) {
		logger.info("运单号为：" + waybillNo + "*********************************");

		// 添加现金收款单
		BillCashCollectionEntity cashEntity = StlWebTestUtil
				.getBillCashCollectionEntity(waybillNo,
						this.getCashCollectionNO(), date);
		billCashCollectionService.addBillCashCollection(cashEntity,
				StlWebTestUtil.getCurrentInfo());

		// 始发运费运费应收单
		String origReceivableBillType = SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE;
		BillReceivableEntity origReceivable = StlWebTestUtil
				.getBillReceivableEntity(
						waybillNo,
						this.getReceivableNO(origReceivableBillType),
						origReceivableBillType,
						SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT);
		try {
			this.billReceivableService.addBillReceivable(origReceivable,
					StlWebTestUtil.getCurrentInfo());
		} catch (SettlementException e) {
			logger.error(e.getMessage(),e);
		}

		// 添加到付应收单
		String destReceivableBillType = SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE;
		BillReceivableEntity destReceivable = StlWebTestUtil
				.getBillReceivableEntity(
						waybillNo,
						this.getReceivableNO(destReceivableBillType),
						destReceivableBillType,
						SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT);
		try {
			this.billReceivableService.addBillReceivable(destReceivable,
					StlWebTestUtil.getCurrentInfo());
		} catch (SettlementException e) {
			logger.error(e.getMessage(),e);
		}

		// 添加代收货款应收单
		String codReceivableBillType = SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE;
		BillReceivableEntity codReceivable = StlWebTestUtil
				.getBillReceivableEntity(
						waybillNo,
						this.getReceivableNO(codReceivableBillType),
						codReceivableBillType,
						SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT);
		try {
			this.billReceivableService.addBillReceivable(codReceivable,
					StlWebTestUtil.getCurrentInfo());
		} catch (SettlementException e) {
			logger.error(e.getMessage(),e);
		}

		// 添加代收货款应付单
		String codPayableBillType = SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD;
		BillPayableEntity codPayable = StlWebTestUtil.getBillPayableEntity(
				waybillNo, this.getPayableNO(codPayableBillType),
				codPayableBillType, date);
		try {
			this.billPayableService.addBillPayable(codPayable,
					StlWebTestUtil.getCurrentInfo());
		} catch (SettlementException e) {
			logger.error(e.getMessage(),e);
		}

		// 添加装卸费应付单
		String servicePayableBillType = SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE;
		BillPayableEntity servicePayable = StlWebTestUtil.getBillPayableEntity(
				waybillNo, this.getPayableNO(servicePayableBillType),
				servicePayableBillType, date);
		try {
			this.billPayableService.addBillPayable(servicePayable,
					StlWebTestUtil.getCurrentInfo());
		} catch (SettlementException e) {
			logger.error(e.getMessage(),e);
		}

		// 添加整车尾款应付单
		String truckPayableBillType = SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST;
		BillPayableEntity truckPayable = StlWebTestUtil.getBillPayableEntity(
				waybillNo, this.getPayableNO(truckPayableBillType),
				truckPayableBillType, date);
		try {
			this.billPayableService.addBillPayable(truckPayable,
					StlWebTestUtil.getCurrentInfo());
		} catch (SettlementException e) {
			logger.error(e.getMessage(),e);
		}

		// 添加代收货款记录
		CODEntity codEntity = StlWebTestUtil.buildRandomCODEntity(waybillNo);
		try {
			codEntityDao.addCod(codEntity);
		} catch (SettlementException e) {
			logger.error(e.getMessage(),e);
		}
	}

	/************************************************************* 获取各种单据编号方法 *************************************/
	/**
	 * 根据传入的单据类型获取应收单号
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-23 上午10:40:06
	 * @return
	 * @see
	 */
	private String getReceivableNO(String billType) {
		if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE
				.equals(billType)) {
			return settlementCommonService
					.getSettlementNoRule(SettlementNoRuleEnum.YS2);
		} else if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE
				.equals(billType)) {
			return settlementCommonService
					.getSettlementNoRule(SettlementNoRuleEnum.YS6);
		} else if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE
				.equals(billType)) {
			return settlementCommonService
					.getSettlementNoRule(SettlementNoRuleEnum.YS1);
		}
		return "";
	}

	/**
	 * 获取应付单号
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-3 上午11:08:38
	 * @param billType
	 * @return
	 * @see
	 */
	private String getPayableNO(String billType) {

		if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD // 代收货款应付单
				.equals(billType)) {
			return settlementCommonService
					.getSettlementNoRule(SettlementNoRuleEnum.YF1);
		} else if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE // 装卸费应付单
				.equals(billType)) {
			return settlementCommonService
					.getSettlementNoRule(SettlementNoRuleEnum.YF2);
		} else if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST // 整车尾款应付单
				.equals(billType)) {
			return settlementCommonService
					.getSettlementNoRule(SettlementNoRuleEnum.YF62);
		} else if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM
				.equals(billType)) {// 理赔应付
			return settlementCommonService
					.getSettlementNoRule(SettlementNoRuleEnum.YF3);
		} else if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__REFUND
				.equals(billType)) {// 退运费应付
			return settlementCommonService
					.getSettlementNoRule(SettlementNoRuleEnum.YF4);
		} else if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__COMPENSATION
				.equals(billType)) {// 服务补救应付
			return settlementCommonService
					.getSettlementNoRule(SettlementNoRuleEnum.YF5);
		}
		return "";
	}

	/**
	 * 模拟获取实收单号方法
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-31 下午4:11:55
	 * @return
	 * @see
	 */
	private String getPaymentNO() {
		return new Date().getTime() + "";
	}

	/**
	 * 获取运单号
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-31 下午5:33:01
	 * @return
	 * @see
	 */
	private String getWaybillNO() {
		return new Date().getTime() + "";
	}

	/**
	 * 获取现金收款单号码
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-5 上午11:48:54
	 * @return
	 * @see
	 */
	private String getCashCollectionNO() {
		String num = settlementCommonService
				.getSettlementNoRule(SettlementNoRuleEnum.XS1);
		return num;
	}

	public IBillPayCODService getBillPayCODService() {
		return billPayCODService;
	}

}
