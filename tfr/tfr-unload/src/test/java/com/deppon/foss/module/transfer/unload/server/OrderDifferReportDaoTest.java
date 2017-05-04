/**   
* @Title: OrderDifferReportDaoTest.java 
* @Package com.deppon.foss.module.transfer.load.server 
* @Description: 点单差异报告 dao测试类
* @author  189284
* @date 2016-1-15 下午3:20:01 
* @version V1.0   
*/
package com.deppon.foss.module.transfer.unload.server;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.transfer.unload.api.server.service.IOrderDifferReportService;

@ContextConfiguration(locations = { "classpath*:com/deppon/foss/module/transfer/load/server/spring-test.xml" })
@TransactionConfiguration
@Transactional
public class OrderDifferReportDaoTest {
	@Autowired
	private IOrderDifferReportService orderDifferReportService;
	/**
	 * 
	 * <p>差异报告查询</p> 
	 * @author 189284 
	 * @date 2016-1-15 下午4:14:26
	 * @see
	 */
	@Test
	public void  queryOrderReportDetailByNo(){
		orderDifferReportService.queryOrderReportDetailByNo("1");
	}
}
