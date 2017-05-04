package com.deppon.foss.module.settlement.consumer.test.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.consumer.api.server.service.ILandBillPaidCODService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.LandBillPaidCODGridDto;
import com.deppon.foss.module.settlement.consumer.api.shared.vo.LandBillPaidCODConditionVo;
import com.deppon.foss.module.settlement.consumer.test.util.ConsumerTestUtil;
//import com.deppon.foss.util.UUIDUtils;
//import com.deppon.foss.util.define.FossConstants;

public class LandBillPaidCODServiceTest {

	@Autowired
	private ILandBillPaidCODService landBillPaidCODService;

	@Autowired
	private ICodCommonService codCommonService;

	@Autowired
	private IBillPayableService billPayableService;

	@Autowired
	private IBillReceivableService billReceivableService;

	/**
	 * 
	 * 按运单进行查询
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-12 下午4:46:50
	 */
	@Test
	@Ignore
	public void testQueryByWaybill() {
//		String codId = UUIDUtils.getUUID();

		// 插入数据
//		this.addBillForTest(codId);

		List<String> waybillNos = new ArrayList<String>();
		waybillNos.add("Waybill001");

		List<LandBillPaidCODGridDto> queryResult = this.landBillPaidCODService
				.queryByWaybillNos(ConsumerTestUtil.getCurrentInfo(),
						waybillNos);
		Assert.assertNotNull(queryResult);
		Assert.assertEquals(1, queryResult.size());

	}

	/**
	 * 按签收日期进行查询
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-13 下午4:38:57
	 */
	@Test
	@Rollback(true)
	@Ignore
	public void testQueryBySignDate() {
//		String codId = UUIDUtils.getUUID();

		// 插入数据
//		this.addBillForTest(codId);

		Calendar currentDate = Calendar.getInstance();
		currentDate.set(1980, 10, 1, 14, 1);

		Calendar endDate = Calendar.getInstance();
		endDate.set(1980, 10, 3, 14, 1);

		LandBillPaidCODConditionVo conditionVo = new LandBillPaidCODConditionVo();
		// 签收日期
		conditionVo.setInceptSignDate(currentDate.getTime());
		conditionVo.setEndSignDate(endDate.getTime());
		// 代收货款类别
		conditionVo.setCodType("codType");
		// 应付组织
		conditionVo.setOrgCode(ConsumerTestUtil.getCurrentInfo().getCurrentDeptCode());

		long rows = this.landBillPaidCODService.queryTotalRowsBySignDate(
				ConsumerTestUtil.getCurrentInfo(), conditionVo).getTotalCount();
		Assert.assertEquals(1, rows);

		List<LandBillPaidCODGridDto> result = this.landBillPaidCODService
				.queryBySignDate(ConsumerTestUtil.getCurrentInfo(),
						conditionVo, 0, 1);
		Assert.assertEquals(1, result.size());

	}

	/**
	 * 按审核日期查询
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-13 下午4:38:57
	 */
	@Test
	@Rollback(true)
	@Ignore
	public void testQueryByAuditDate() {

//		String codId = UUIDUtils.getUUID();

		// 插入数据
//		this.addBillForTest(codId);

		// 获得有效的Id
		CODEntity codEntity = this.codCommonService
				.queryByWaybill("Waybill001");

		List<String> codIds = new ArrayList<String>();
		codIds.add(codEntity.getId());

		// 执行审核
		this.landBillPaidCODService.makeEffectiveBillCOD(codIds,
				ConsumerTestUtil.getCurrentInfo());

		Calendar currentDate = Calendar.getInstance();

		LandBillPaidCODConditionVo conditionVo = new LandBillPaidCODConditionVo();
		// 审核日期
		conditionVo.setInceptAuditDate(DateUtils.addDays(currentDate.getTime(),
				-1));
		conditionVo
				.setEndAuditDate(DateUtils.addDays(currentDate.getTime(), 1));
		// 代收货款类别
		conditionVo.setCodType("ALL");
		// 应付组织
		conditionVo.setOrgCode("destOrgCode");

		long rows = this.landBillPaidCODService.queryTotalRowsByAuditDate(
				ConsumerTestUtil.getCurrentInfo(), conditionVo).getTotalCount();
		Assert.assertEquals(true, rows >= 1);

		List<LandBillPaidCODGridDto> result = this.landBillPaidCODService
				.queryByAuditDate(ConsumerTestUtil.getCurrentInfo(),
						conditionVo, 0, 1);
		Assert.assertEquals(1, result.size());

	}

	/**
	 * 
	 * 审核空运代收货款
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-16 上午10:18:40
	 */
	@Test
	@Ignore
	public void testAuditAirCod() {
		// 添加测试数据

//		String codId = UUIDUtils.getUUID();
		// 插入数据
//		this.addBillForTest(codId);
		// 获得有效的Id
		CODEntity codEntity = this.codCommonService
				.queryByWaybill("Waybill001");

		// 封装List
		List<String> ids = new ArrayList<String>();
		ids.add(codEntity.getId());

		// 进行验证
		this.landBillPaidCODService.makeEffectiveBillCOD(ids,
				ConsumerTestUtil.getCurrentInfo());
	}

	/**
	 * 
	 * 反审核空运代收货款
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-16 上午10:18:40
	 */
	@Test
	@Ignore
	public void testAntiAuditAirCod() {
		// 添加测试数据

//		String codId = UUIDUtils.getUUID();
		// 插入数据
//		this.addBillForTest(codId);
		// 获得有效的Id
		CODEntity codEntity = this.codCommonService
				.queryByWaybill("Waybill001");

		// 封装List
		List<String> ids = new ArrayList<String>();
		ids.add(codEntity.getId());

		// 错误的反审核
		try {
			this.landBillPaidCODService.reverseEffectiveBillCOD(ids,
					ConsumerTestUtil.getCurrentInfo());
		} catch (Exception ex) {
			Assert.assertNotNull(ex);
		}

		// 正确的审核
		// 进行审核验证
		this.landBillPaidCODService.makeEffectiveBillCOD(ids,
				ConsumerTestUtil.getCurrentInfo());

		// 进行反审核
		this.landBillPaidCODService.reverseEffectiveBillCOD(ids,
				ConsumerTestUtil.getCurrentInfo());
	}

	/**
	 * 
	 * 准备测试数据
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-12 下午4:50:42
	 */
	private void addBillForTest(String codID) {
		// 防止程序报错，故意设置一个较小的日期
		Calendar currentDate = Calendar.getInstance();
		currentDate.set(1980, 10, 2, 14, 1);

		// 代收货款记录
		CODEntity codEntity = new CODEntity();
//		codEntity.setId(UUIDUtils.getUUID());
		codEntity.setWaybillNo("Waybill001");
		codEntity.setWaybillId("Waybiid001");
		codEntity.setCodType("codType");

		codEntity.setBusinessDate(new Date());
		codEntity.setIsInit("N");

		// 代收货款金额
		codEntity.setCodAmount(new BigDecimal("10000"));

		// 收款人
		codEntity.setPayeeName("payeeName");
//		codEntity.setPayeeCode("payeeCode");

		// 应付组织编码
		codEntity.setPayableOrgCode("payableOrgCode");

		codEntity.setBankHQName("ICBC");
		codEntity.setBatchNumber("2012110501");

		// 代收货款类型
		codEntity.setCodType("codType");
		// 代收货款状态
		codEntity.setStatus("Y");
		codEntity
				.setAirStatus(SettlementDictionaryConstants.COD__AIR_STATUS__NOT_AUDIT);
//		codEntity.setActive(FossConstants.ACTIVE);
//		codEntity.setVersionNo(FossConstants.INIT_VERSION_NO);

		// 退款路径
		codEntity.setRefundPath("test");

		// 退款成功时间
		codEntity.setRefundSuccessTime(currentDate.getTime());
		// 代收货款空运组织审核时间
		codEntity.setAirOrgAuditTime(currentDate.getTime());

		// 设置付款申请时间
		codEntity.setCodExportTime(currentDate.getTime());
		codEntity.setTusyorgRfdApptime(currentDate.getTime());
		// 业务日期
		codEntity.setBusinessDate(currentDate.getTime());

		this.codCommonService.createCOD(codEntity,
				ConsumerTestUtil.getCurrentInfo());

		// 添加应付单
		this.addBillPayable(currentDate, ConsumerTestUtil.getCurrentInfo());

		// 添加应收单
		this.addBillReceivable(currentDate, ConsumerTestUtil.getCurrentInfo());

	}

	/**
	 * 测试添加应收单
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-7 下午5:22:05
	 */
	private void addBillReceivable(Calendar currentDate, CurrentInfo currentInfo) {
		// 同时添加应付单
		BillReceivableEntity billReceivable = new BillReceivableEntity();
//		billReceivable.setId(UUIDUtils.getUUID());
		billReceivable.setWaybillNo("Waybill001");
		billReceivable.setWaybillId("waybillId");

//		billReceivable.setActive(FossConstants.ACTIVE);
		billReceivable.setCreateType("AUTO_TEST");
		billReceivable.setBillType("COD_TYPE");

		billReceivable.setSourceBillType("waybill");
		billReceivable.setSourceBillNo("waybill");
		// 代收货款金额
		billReceivable.setAmount(new BigDecimal("100000"));
		// 已经核销金额
		billReceivable.setVerifyAmount(BigDecimal.ZERO);
		// 未核销金额
		billReceivable.setUnverifyAmount(new BigDecimal("100000"));
		// 应付组织编码
		billReceivable.setReceivableOrgCode("receivableOrgCode");
		// 应收单单号
		billReceivable.setReceivableNo("Waybill001");

		billReceivable.setCurrencyCode("RMB");
		billReceivable.setAccountDate(new Date());
		billReceivable.setBusinessDate(new Date());
		billReceivable.setIsRedBack("N");
		billReceivable.setIsInit("N");
//		billReceivable.setVersionNo(FossConstants.INIT_VERSION_NO);
		// 到达组织
		billReceivable.setDestOrgCode(currentInfo.getCurrentDeptCode());
		
		// 应收单的单据子类型-空运代理代收货款应收单
		billReceivable
				.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD);
		// 付款方式
		billReceivable.setPaymentType("paymentType");
		// 产品类型
		billReceivable.setProductCode("0001");

		this.billReceivableService.addBillReceivable(billReceivable,
				ConsumerTestUtil.getCurrentInfo());
	}

	/**
	 * 测试添加应付单
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-7 下午5:22:05
	 */
	private void addBillPayable(Calendar currentDate, CurrentInfo currentInfo) {
		// 同时添加应付单
		BillPayableEntity billPayable = new BillPayableEntity();
//		billPayable.setId(UUIDUtils.getUUID());
		billPayable.setWaybillNo("Waybill001");
		billPayable.setWaybillId("waybillId");

		billPayable.setPayableType("codType");
		billPayable.setPayableNo("001");
//		billPayable.setActive(FossConstants.ACTIVE);
		billPayable.setCreateType("AUTO_TEST");
		billPayable.setBillType("COD_TYPE");

		billPayable.setPayableType("codType");
		billPayable.setSourceBillType("waybill");
		billPayable.setSourceBillNo("waybill");
		// 代收货款金额
		billPayable.setAmount(new BigDecimal("100000"));
		// 已经核销金额
		billPayable.setVerifyAmount(BigDecimal.ZERO);
		// 未核销金额
		billPayable.setUnverifyAmount(new BigDecimal("100000"));
		// 应付组织编码
		billPayable.setPayableOrgCode("payableOrgCode");

		// 到达组织
		billPayable.setDestOrgCode(currentInfo.getCurrentDeptCode());
		// 生效状态
		billPayable
				.setEffectiveStatus(SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__NO);
		// 支付状态
		billPayable.setPayStatus("N");

		// 冻结状态
		billPayable.setFrozenStatus("N");
		// 审核状态
		billPayable.setApproveStatus(SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__AUDIT_AGREE);

		billPayable.setCurrencyCode("RMB");
		billPayable.setAccountDate(new Date());
		billPayable.setBusinessDate(new Date());
		billPayable.setIsRedBack("N");
		billPayable.setIsInit("N");
		billPayable.setCustomerContactName("payeeName");
//		billPayable.setVersionNo(FossConstants.INIT_VERSION_NO);

		// //应付单的单据子类型 = 应付代收货款
		billPayable
				.setBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);

		this.billPayableService.addBillPayable(billPayable,
				ConsumerTestUtil.getCurrentInfo());
	}

	public void setLandBillPaidCODService(
			ILandBillPaidCODService landBillPaidCODService) {
		this.landBillPaidCODService = landBillPaidCODService;
	}

	public void setCodCommonService(ICodCommonService codCommonService) {
		this.codCommonService = codCommonService;
	}

	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}

	public void setBillReceivableService(
			IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}

}
