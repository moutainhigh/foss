/**
 *  initial comments.
 */
/*
 * PROJECT NAME: pkp-predeliver
 * PACKAGE NAME: com.deppon.foss.module
 * FILE    NAME: QueryGoodsDaoTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.predeliver.server.dao;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.deppon.foss.module.pickup.predeliver.api.server.dao.INotifyCustomerDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.InvoiceInfomationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerConditionDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.NotifyCustomerVo;
import com.deppon.foss.module.pickup.predeliver.server.dao.impl.NotifyCustomerDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.util.UUIDUtils;

/**
 * 
 * 通知客户DAO测试类
 * 
 * @author ibm-wangfei
 * @date Oct 24, 2012 5:06:00 PM
 */
public class NotifyCustomerDaoTest {

	private INotifyCustomerDao notifyCustomerDao = null;
	NotificationEntity notificationEntity;
	private static ApplicationContext ctx = null;

	String[] xmls = new String[] {
			"com/deppon/foss/module/pickup/predeliver/test/META-INF/spring.xml",
			"com/deppon/foss/module/pickup/predeliver/server/META-INF/spring.xml"};
	
	@Before
	public void init() throws Exception {
		try {
			if (ctx == null || notifyCustomerDao == null) {
				ctx = new ClassPathXmlApplicationContext(xmls);
				notifyCustomerDao = ctx.getBean(NotifyCustomerDao.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * 测试新增运单客户通知记录
	 * @author ibm-wangfei
	 * @date Oct 24, 2012 5:29:55 PM
	 */
	@Test
	public void addNotificationEntity() {
		notificationEntity = new NotificationEntity();
		notificationEntity.setWaybillNo("2012109");
		notificationEntity.setCustomerQulification("合同客户");
		
		notificationEntity.setPaymentType("现金");
		notificationEntity.setDeliverDate("2012-07-12");
		notificationEntity.setDeliverRequire("周日送货");
		notificationEntity.setIsNeedInvoice("0");
		notificationEntity.setIsSentRequired("0");
		notificationEntity.setIsStorageCharge("0");
		notificationEntity.setNoticeType("电话");
		notificationEntity.setNoticeContent("周日送货");
		notificationEntity.setNoticeResult("0");
		notificationEntity.setConsignee("王先生");
		notificationEntity.setMobile("13999999999");
		notificationEntity.setDeliverType("自提");
		notificationEntity.setExceptionNotes("abc");
		
		notificationEntity.setId(UUIDUtils.getUUID());
		notificationEntity.setOperator("1");// 操作人 OPERATOR
		notificationEntity.setOperatorNo("1"); // 操作人编码 OPERATOR_NO
		notificationEntity.setOperateOrgName("1"); // 操作部门 OPERATE_ORG_NAME
		notificationEntity.setOperateOrgCode("1"); // 操作部门编码 OPERATE_ORG_CODE
		notificationEntity.setOperateTime(new Date()); // 操作时间 OPERATE_TIME
		int result = notifyCustomerDao.addNotificationEntity(notificationEntity);
		Assert.assertTrue(result > 0);
	}
	
	/**
	 * 
	 * 新增运单通知发票信息
	 * @author ibm-wangfei
	 * @date Oct 24, 2012 5:30:30 PM
	 */
	@Test
	public void addInvoiceInfomationEntity() {
		InvoiceInfomationEntity entity = new InvoiceInfomationEntity();
		entity.setId(UUIDUtils.getUUID()); // ID ID
		entity.setAccount("账号");
		entity.setAddress("地址");
		entity.setBank("银行");
		entity.setCompanyName("公司名称");
		entity.setTel("130999999999");
		entity.setWaybillNo("2012109");
		entity.setTaxNo("税号");
		entity.setCreateDate(new Date());
		entity.setOperateTime(new Date()); // 操作时间 OPERATE_TIME
		// TODO
		entity.setOperator("1");// 操作人 OPERATOR
		entity.setOperatorCode("1"); // 操作人编码 OPERATOR_NO
		entity.setOperateOrgName("1"); // 操作部门 OPERATE_ORG_NAME
		entity.setOperateOrgCode("1"); // 操作部门编码

		int result = notifyCustomerDao.addInvoiceInfomationEntity(entity);
		System.out.println(entity.getId());
		Assert.assertTrue(result > 0);
	}
	
	/**
	 * 
	 * 查询运单通知历史记录
	 * @author ibm-wangfei
	 * @date Oct 24, 2012 5:30:46 PM
	 */
	@Test
	public void queryNotificationsByParam() {
		String waybillNo = "2012109";
		NotificationEntity notifycation = new NotificationEntity();
		notifycation.setWaybillNo(waybillNo);
		notifycation.setOrder("DESC");
		List<NotificationEntity> list = notifyCustomerDao.queryNotificationsByParam(notifycation);
		Assert.assertTrue(list.size() > 0);
	}
	
	/**
	 * 查询运单通知列表测试
	 * @author ibm-wangfei
	 * @date Oct 24, 2012 5:32:43 PM
	 */
	@Test
	public void queryNotifyCustomerList() {
		NotifyCustomerVo vo = new NotifyCustomerVo();
//		vo.setWaybillNo("2012123");
//		vo.setHandoverNo("123");
//		vo.setVehicleAssembleNo("123213");
//		vo.setStorageDay("5");
//		vo.setInStockTimeFrom("2012-12-10");
//		vo.setEstimateDepartTimeFrom("2012-12-10");
		vo.setConditionDto(new NotifyCustomerConditionDto());
		vo.getConditionDto().setSelectType("1");
		// 登录人员所在部门
		vo.getConditionDto().setLastLoadOrgCode("上海青浦");
		// 默认查询中派送方式--派送
		vo.getConditionDto().setReceiveMethodTmp("XXX");
		// 默认查询中通知状态--成功
		vo.getConditionDto().setNotificationTypeTmp("");
		// 默认日期
		vo.getConditionDto().setDeliverDateTmp(new Date());
		// 默认查询中付款方式--非临时欠款等收款放货
		vo.getConditionDto().setPaidMethod(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT);
		// 默认查询中变更状态 -- 已受理
//		vo.getConditionDto().setWbrStatus("");
		vo.setDtoList(notifyCustomerDao.queryNotifyCustomerListForHandoverNo(vo.getConditionDto(), 1, 1));
		Assert.assertTrue(vo.getDtoList().size() > 0);
	}
	
	@Test
	public void queryNotifyCustomer() {
		NotifyCustomerConditionDto conditionDto = new NotifyCustomerConditionDto();
		conditionDto.setWaybillNo("2012123");
		NotifyCustomerDto dto = notifyCustomerDao.queryNotifyCustomer(conditionDto);
		Assert.assertTrue(dto.getWaybillNo().equals("2012123"));
	}
	
	@Test
	public void updateByWaybillNo() {
		ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
		actualFreightEntity.setWaybillNo("2012117");
		actualFreightEntity.setNotificationTime(new Date());
		actualFreightEntity.setNotificationResult("VOICE_NOTICING");
		int i  = notifyCustomerDao.updateActualFreightEntityByWaybillNo(actualFreightEntity);
		Assert.assertTrue(i > 0);
	}
	/**
	 * 
	 * 根據运单号列表，查询运单相关信息
	 * @author ibm-wangfei
	 * @date Nov 2, 2012 10:09:22 AM
	 */
	@Test
	public void queryWaybillsByNos() {
		String waybillNos = "2012123,2012117,";
		List<NotifyCustomerDto> dtos = notifyCustomerDao.queryWaybillsByNos(waybillNos.split(","));
		Assert.assertTrue(dtos.size() > 0);
	}
	
	/**
	 * 
	 * 根据车次号进行运单查询
	 * @author ibm-wangfei
	 * @date Nov 7, 2012 11:19:47 AM
	 */
	@Test
	public void  queryNotifyCustomerListForVehicleAssembleNo() {
		NotifyCustomerConditionDto conditionDto = new NotifyCustomerConditionDto();
		conditionDto.setVehicleAssembleNo("2012123");
		List<NotifyCustomerDto> dtos = notifyCustomerDao.queryNotifyCustomerListForVehicleAssembleNo(conditionDto, 1, 1);
		Assert.assertTrue(dtos.size() > 0);
	}
	
	/**
	 * 
	 * 初步查询出需要统计仓储费的运单信息
	 * @author ibm-wangfei
	 * @date Nov 12, 2012 2:55:16 PM
	 */
	@Test
	public void  queryWaybillsByCFF() {
		String productCodes = "C30006,C30007,";
		List<NotifyCustomerDto> dtos = notifyCustomerDao.queryWaybillsByCFF(productCodes.split(","));
		Assert.assertTrue(dtos.size() > 0);
	}
	
	
	
}