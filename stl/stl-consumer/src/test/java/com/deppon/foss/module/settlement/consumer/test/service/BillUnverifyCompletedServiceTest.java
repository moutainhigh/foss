package com.deppon.foss.module.settlement.consumer.test.service;
import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillUnverifyCompletedService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillUnverifyComletedResultDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillUnverifyCompletedQueryDto;
import com.deppon.foss.module.settlement.consumer.test.BaseTestCase;




/**
 * 未完全核销单据查询service测试类
 * @author foss-qiaolifeng
 * @date 2013-5-14 下午3:29:22
 */
public class BillUnverifyCompletedServiceTest extends BaseTestCase {

	private static final Logger log = LogManager
			.getLogger(BillUnverifyCompletedServiceTest.class);
	
	@Autowired
	private IBillUnverifyCompletedService billUnverifyCompletedService;
	
	
	/**
	 * 查询测试
	 *
	 * @author foss-qiaolifeng
	 * @date 2013-5-14 下午3:31:13
	 * @param
	 * @return 成功失败标记
	 * @exception SettlementException
	 * @see
	 */
	@Test
	@Rollback(true)
	public void queryBillUnverifyCompletedListTest() {
		
		log.info("测试开始.......");
		try{
			
			String customerCode = "20101014-05131992";
			BillUnverifyCompletedQueryDto dto = new BillUnverifyCompletedQueryDto();
			dto.setCustomerCode(customerCode);
			
			Long billNums = billUnverifyCompletedService.queryBillUnverifyCompletedTotals(dto);
			
			List<BillUnverifyComletedResultDto> rtnList = billUnverifyCompletedService.queryBillUnverifyCompletedList(dto);
			
			log.info("该客户存在的未完全核销单据条数："+rtnList.size());
			Assert.assertEquals(billNums.longValue(), rtnList.size());
		}catch(BusinessException ex){
			Assert.assertNotNull(ex);
		}
		log.info("测试结束.......");
	}
}
