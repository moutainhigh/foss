/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.settlement.consumer.test.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillReceivableDto;
import com.deppon.foss.module.settlement.consumer.server.service.impl.BillReceivableMonthlyQueryService;
import com.deppon.foss.module.settlement.consumer.test.BaseTestCase;

/**
 * (描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:273272,date:2016-2-17 下午1:39:38, </p>
 * @author 273272 唐俊
 * @date 2016-2-17 下午1:39:38
 * @since
 * @version
 */
public class BillReceivableMonthlyQueryServiceTest extends BaseTestCase{
	@Resource
	private BillReceivableMonthlyQueryService billReceivableMonthlyQueryService;
	
	@Test
	@Rollback(true)
	public void testInsertBillReceivable() {
		List<BillReceivableEntity> list = new ArrayList<BillReceivableEntity>();
		BillReceivableDto billReceivableDto = new BillReceivableDto();
		//获取当前时间设置为查询的结束时间
		Date date = new Date();
		billReceivableDto.setEndDate(date);
		//将当前时间减一个月设置为查询的开始时间
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, -1);
		billReceivableDto.setStartDate(cal.getTime());
		list = billReceivableMonthlyQueryService.queryBillReceivableByData(billReceivableDto);
		billReceivableMonthlyQueryService.insertBillReceivable(list);
	}

}
