package com.deppon.foss.module.transfer.unload.server;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;


import com.deppon.foss.module.transfer.unload.api.server.dao.IOrderTaskDao;
import com.deppon.foss.module.transfer.unload.api.shared.domain.OrderSerialNoDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.OrderTaskEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.OtHandOverBillDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.OrderTaskDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.QuerySerialInfoBywaybillNoDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.QuerySerialListByTskNumDto;
import com.deppon.foss.util.UUIDUtils;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:com/deppon/foss/module/transfer/load/server/spring-test.xml" })
@TransactionConfiguration
@Transactional
public class OrderTaskTest {

	@Autowired
	private IOrderTaskDao orderTaskDao;
	
	private static final Date date = new Date();
	
	private static final String orderTaskNo = "H00000381";
	
	
	public void testQueryOrderTaskBaseInfoByNo() {
		OrderTaskEntity bean = orderTaskDao.queryOrderTaskBaseInfoByNo(orderTaskNo);
		System.out.println(bean.toString()); 
		//System.out.println(new Date());
	}
	
	public void testQueryOrderTaskSerialNo() {
		List<QuerySerialListByTskNumDto> list = orderTaskDao.queryOrderTaskSerialNo(orderTaskNo);
		String str = list.get(0).getSerialNo();
		System.out.println(str);
	}
	

	public void testInsertMoreSerialNo(){
		OrderTaskEntity entity = new OrderTaskEntity();
		entity.setId("1");
		entity.setOrderTaskNo(orderTaskNo);
		entity.setArriveCode("W12134343");
		entity.setArriveName("到达部门");
		entity.setVehicleNo("Q1234");
		entity.setOrderTaskState("END");
		entity.setOrderCode("1234");
		entity.setOrderName("点单员");
		entity.setTotWaybillQty((long)2);
		entity.setTotGoodsQty((long)2);
		entity.setTotWeight(BigDecimal.TEN);
		entity.setTotVolume(BigDecimal.TEN);
		entity.setCreateOrgCode("W4376913874");
		entity.setOrderStartTime(date);
		entity.setOrderEndTime(date);
		entity.setModifyTime(date);
		orderTaskDao.updateOrderTaskBasicInfo(entity);
	}
	
	public void testQueryOrderTaskBillDetailListByNo() {
		List<OtHandOverBillDetailEntity> list = orderTaskDao.queryOrderTaskBillDetailListByNo(orderTaskNo);
		System.out.println(list.size());
	}
	
	public void testQueryOrderTaskSerialNoListByBillNo() {
		List<OrderSerialNoDetailEntity> bean = orderTaskDao.queryOrderTaskSerialNoListByBillNo("13d24dcd-fb97-40bd-8ba0-c957a1d144a7");
		System.out.println(bean.size());
	}
	
	public void testQueryValidateWaybillNoAndSerialNo(
			String waybillNo) {
		List<QuerySerialInfoBywaybillNoDto> list = orderTaskDao.queryValidateWaybillNoAndSerialNo("314343543");
		System.out.println(list.size());
	}
	@Test
	public void testGetTotCount() {
		OrderTaskDto orderTaskDto = new OrderTaskDto();
		orderTaskDto.setOrderTaskNo(orderTaskNo);
		System.out.println(orderTaskDao.getTotCount(orderTaskDto));
	}
	
	public void testQueryOrderTask() {
		OrderTaskDto orderTaskDto = new OrderTaskDto();
		orderTaskDto.setOrderTaskNo(orderTaskNo);
	    orderTaskDao.queryOrderTask(orderTaskDto, 0, 1);
	}
	
	public void testaddOrderTaskBillDetailList(){
		List<OtHandOverBillDetailEntity> newBillList = new ArrayList<OtHandOverBillDetailEntity>();
		OtHandOverBillDetailEntity bill = new OtHandOverBillDetailEntity();
	
			bill.setId(UUIDUtils.getUUID());
			bill.setOrderGoodsQty(BigDecimal.ZERO);
			bill.setOrderTaskNo("0123456789");
			bill.setCreateDate(new Date());
			bill.setModifyDate(new Date());
			newBillList.add(bill);
		orderTaskDao.addOrderTaskBillDetailList(newBillList);
	}
	
	public void testPrint(){
		System.out.println("ttt");
	}
}
