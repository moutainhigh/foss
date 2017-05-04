/**
 * 
 */
package com.deppon.foss.module.settlement.consumer.test.service;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillReceivablePtpService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillNewReceivablePtpDto;
import com.deppon.foss.module.settlement.consumer.test.BaseTestCase;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 测试代收货款应收单生成与红冲
 * @author231438
 * @date 2016-01-13 下午2:59:08
 */
public class BillReceivableNewPtpServiceHCodTest extends BaseTestCase{
	@Autowired
	private IBillReceivablePtpService billReceivableNewPtpService;
	
	/**
	 * 
	 * 测试新增代收货款应收单
	 * @author231438
	 * @date 2016-01-13 下午2:59:08
	 */
	//@Test
	//@Rollback(false)
	public void testAddHCODReceivable() {
		logger.info("测试添加合伙人代收货款应收单开始：");
		Date nowTime = new Date();
		BillReceivableEntity entity = new BillReceivableEntity();
		entity.setId(UUIDUtils.getUUID());
		entity.setWaybillNo("5024479520");
		entity.setWaybillId("1df5b636-21e9-4a94-a6ce-806eda30ooxx");
		entity.setDeliveryCustomerCode("S20140318-16237159");
		entity.setDeliveryCustomerName("刘小波");
		entity.setReceiveCustomerCode("S20140806-38295901");
		entity.setReceiveMethod("PICKUP");
		entity.setProductCode("FLF");
		entity.setGoodsQtyTotal(new BigDecimal(1));
		entity.setGoodsVolumeTotal(new BigDecimal(0.8));
		entity.setGoodsName("铁件");
		entity.setCustomerPickupOrgCode("W0000005036");
		entity.setTargetOrgCode("W0000005036");
		entity.setReceivableOrgCode("W0000000000");
		entity.setReceivableOrgName("虚构合伙人对接快递点部");
		entity.setAmount(new BigDecimal(250));
		entity.setUnverifyAmount(entity.getAmount());
		entity.setVerifyAmount(BigDecimal.ZERO); // 已核销金额
		entity.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT); // 付款方式
		entity.setInvoiceMark(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_TWO);//代收货款应收单发票标记
        //代收货款的统一结算为否
        entity.setUnifiedSettlement(FossConstants.NO);
        entity.setCurrencyCode("RMB");
        entity.setBusinessDate(nowTime);
        entity.setAccountDate(nowTime);
		
        CurrentInfo currentInfo = this.getCurrentInfo("075874", "W0000004631");
		BillNewReceivablePtpDto ptpDto= new BillNewReceivablePtpDto();
//		ptpDto.setReceiveOrgCode("W0600307063507");
//		ptpDto.setReceiveOrgName("北京房山区城关镇营业部");
//		ptpDto.setCodAmount(new BigDecimal(250));
//		ptpDto.setBillTime(nowTime);
//		ptpDto.setReceiveCustomerContact("马杀鸡");
//		ptpDto.setLastLoadOrgCode("W0000005036");
//		ptpDto.setLastLoadOrgName("北京房山区良乡双网分部");
//		ptpDto.setGoodsWeightTotal(new BigDecimal(30));
//		
//		ptpDto.setCurrentInfo(currentInfo);
//		ptpDto.setBillReceivableEntity(entity);
		
//		Assert.assertNotNull(billReceivableNewPtpService.addHCodBillReceivable(ptpDto));
		logger.info("生成代收货款应收单完成！");
	}
	
	private CurrentInfo getCurrentInfo(String operatorCode,String deptCode){
		UserEntity user = new UserEntity();
		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		EmployeeEntity emp = new EmployeeEntity();
		emp.setEmpName("蔡健雅");
		emp.setEmpCode(operatorCode);//员工编码
		user.setEmployee(emp);//用户信息
		
		dept.setName("北京石景山区衙门口点部");
		//部门编码
		dept.setCode(deptCode);
		return new CurrentInfo(user, dept);//返回对象
	}
//	@Test
//	@Rollback(false)
	/**
	 * 代收货款应收单红冲
	 */
//	public void testWriteBackHCodReceivable(){
//		logger.info("红冲合伙人代收货款应收单开始....");
//		BillReceivableEntity entity = new BillReceivableEntity();
//		entity.setWaybillNo("5024479520");
//		CurrentInfo currentInfo = this.getCurrentInfo("075874", "W0000004631");
//		BillNewReceivablePtpDto ptpDto= new BillNewReceivablePtpDto();
//		ptpDto.setBillReceivableEntity(entity);
//		ptpDto.setCurrentInfo(currentInfo);
//		Assert.assertNotNull(billReceivableNewPtpService.writeBackHCodReceivable(ptpDto));
//		logger.info("红冲合伙人代收货款应收单结束....");
//	}
}
