/**
 * @company : com.deppon
 * @poroject : foss结算
 * @copyright : copyright (c) 2016
 * 
 * @description: 单元测试-待刷卡运单管理页面接口
 * @author : panshiqi (309613)
 * @date : 2016年3月14日 上午9:36:44
 * @version : v1.0
 */
package com.deppon.foss.module.settlement.pay.test.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.settlement.pay.api.server.service.IWscWayBillManageService;
import com.deppon.foss.module.settlement.pay.test.BaseTestCase;

public class WscWayBillManageServiceTest extends BaseTestCase {

	/**
	 *注入service 
	 */
	@Autowired
	private IWscWayBillManageService stlPayWscWayBillManageService;

	@Test
	public void testQueryWscWayBill() {

		// TODO: 根据查询条件获取待刷卡运单数据

	}

	@Test
	public void testExportWscWayBill() {

		// TODO: 导出Excel文件

	}

}
