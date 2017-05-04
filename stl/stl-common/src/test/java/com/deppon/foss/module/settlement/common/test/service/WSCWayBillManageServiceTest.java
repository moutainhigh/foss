/**
 * @company : com.deppon
 * @poroject : foss结算
 * @copyright : copyright (c) 2016
 * 
 * @description: 单元测试-待刷卡运单管理接口
 * @author : panshiqi (309613)
 * @date : 2016年3月11日 下午3:51:48
 * @version : v1.0
 */
package com.deppon.foss.module.settlement.common.test.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.settlement.common.api.server.service.IWSCWayBillManageService;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCRecordEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCWayBillEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCWayBillParamEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCWayBillReturnEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.test.BaseTestCase;

public class WSCWayBillManageServiceTest extends BaseTestCase {

	/**
	 * 注入测试的service
	 */
	@Autowired
	private IWSCWayBillManageService wscWayBillManageService;

	/**
	 * 
	* @description: 单元测试-添加代刷卡运单数据
	* @title: testAddWSCWayBill
	* @author panshiqi 309613
	* @date 2016年3月11日 下午4:07:36
	 */
	@SuppressWarnings("unused")
	@Test
	public void testAddWSCWayBill() {
		// 模拟参数
		WSCWayBillParamEntity params = new WSCWayBillParamEntity();
		params.setWayBillNo("00000000x"); // 运单号
		//params.setWayBillSource("1"); // 数据来源{1-运单开单 2-运单更改}
		params.setWayBillSource("2");
		params.setSendCustomerCode("FHR00000000x"); // 发货人编号
		params.setSendCustomerName("发货人x"); // 发货人名称
		params.setCreateBillOrgCode("KDDP00000000x"); // 开单部门编号
		params.setCreateBillOrgName("开单部门x"); // 开单部门名称
		Date nowTime = new Date();
		params.setCreateBillTime(nowTime); // 开单时间
		params.setWaitSwipeAmount(100); // 待刷卡金额
		params.setCreateUserCode("CJR00000000x"); // 创建人编号
		params.setCreateUserName("创建人x"); // 创建人名称
		try {
			// 成功
			wscWayBillManageService.addWSCWayBill(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	* @description: 根据运单号获取(最新&有效)待刷卡运单数据
	* @title: testGetLastActiveDataByWayBillNo
	* @author panshiqi 309613
	* @date 2016年3月11日 下午4:08:40
	 */
	@SuppressWarnings("unused")
	@Test
	public void testGetLastActiveDataByWayBillNo() {
		// 模拟参数
		WSCWayBillParamEntity params = new WSCWayBillParamEntity();
		params.setWayBillNo("00000000x");
		try {
			WSCWayBillReturnEntity result = wscWayBillManageService.getLastActiveDataByWayBillNo(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	* @description: 根据运单号修改待刷卡运单数据为无效
	* @title: testInvalidAndInsertWSCWayBill
	* @author panshiqi 309613
	* @date 2016年3月11日 下午4:10:07
	 */
	@SuppressWarnings("unused")
	@Test
	public void testInvalidWSCWayBillByWayBillNo() {
		// 模拟参数
		WSCWayBillParamEntity params = new WSCWayBillParamEntity();
		params.setWayBillNo("00000000x");
		params.setModifyUserCode("panshiqi");
		params.setModifyUserName("潘士奇");
		try {
			wscWayBillManageService.invalidWSCWayBillByWayBillNo(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	* @description: 修改待刷卡运单金额
	* @title: testInvalidAndInsertWSCWayBill
	* @author panshiqi 309613
	* @date 2016年3月11日 下午4:12:16
	 */
	@SuppressWarnings("unused")
	@Test
	public void testInvalidAndInsertWSCWayBill() {
		// 模拟参数
		WSCWayBillParamEntity params = new WSCWayBillParamEntity();
		params.setWscItemId("2C3F1A8A37B61351E0536F0AA8C0C906");
		params.setWaitSwipeAmount(200);
		params.setModifyUserCode("panshiqi");
		params.setModifyUserName("潘士奇");
		try {
			wscWayBillManageService.invalidAndInsertWSCWayBill(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	* @description: 根据部门编号查询部门下待刷卡运单数据
	* @title: testGetWayBillListByOrgCode
	* @author panshiqi 309613
	* @date 2016年3月11日 下午4:13:04
	 */
	@SuppressWarnings("unused")
	@Test
	public void testGetWayBillListByOrgCode() {
		// 模拟参数
		WSCWayBillParamEntity params = new WSCWayBillParamEntity();
		params.setOrgCode("W011405050402"); // 部门编号
		try {
			// 仅部门编号查询
			List<WSCWayBillEntity> result = wscWayBillManageService.getWayBillListByOrgCode(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	* @description: 记录刷卡结果, 使用事务控制
	* @title: testRecordSwipeCardResult
	* @author panshiqi 309613
	* @date 2016年3月11日 下午4:23:45
	 */
	@SuppressWarnings("unused")
	@Test
	public void testRecordSwipeCardResult() {
		// 模拟参数
		WSCWayBillParamEntity params = new WSCWayBillParamEntity();
		params.setWscItemId("2C3F1A8A37B61351E0536F0AA8C0C907"); // 待刷卡数据编号
		params.setWayBillNo(""); // 运单编号
		params.setAlreadySwipeAmount(10); // 已刷卡金额
		params.setSwipeCardOrgCode("SCDP000000001"); // 刷卡部门编号
		params.setSwipeCardOrgName("刷卡部门1"); // 刷卡部门名称
		params.setSwipeCardTime(new Date()); // 刷卡时间
		params.setSwipeCardUserCode("SCR0000000001"); // 刷卡人编号
		params.setSwipeCardUserName("刷卡人1"); // 刷卡人名称
		params.setSerialNo("LSH000000001"); // 交易流水号
		try {
			int result = wscWayBillManageService.recordSwipeCardResult(params);
		} catch (SettlementException e) {
			String msg = e.getMessage();
			String errrorCode = e.getErrorCode();
			System.out.println(errrorCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	* @description: 更新T+0报表明细  && T+0报表(事务)
	* @title: testUpdateT0StatementAndDetail
	* @author panshiqi 309613
	* @date 2016年3月11日 下午4:24:58
	 */
	@Test
	public void testUpdateT0StatementAndDetail() {

		// 调用T+0接口,修改T+0报表及其明细数据
		// 封装明细实体
		PosCardDetailEntity detail = new PosCardDetailEntity();

		// 设置单据号
		detail.setInvoiceNo("00000000x");

		// 设置已占用金额：(T+0明细运单已使用流水号金额、T+0报表已使用流水号金额)
		detail.setOccupateAmount(new BigDecimal(100.0));

		// 修改T+0数据,这是一个事务
		try {
			//wscWayBillManageService.updateT0StatementAndDetail(detail);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	* @description: 记录刷卡结果(批量),包含事务
	* @title: testrRecordSwipeCardResultBatch
	* @author panshiqi 309613
	* @date 2016年3月11日 下午4:25:58
	 */
	@SuppressWarnings("unused")
	@Test
	public void testrRecordSwipeCardResultBatch() {
		// 声明结果对象
		WSCWayBillReturnEntity result = new WSCWayBillReturnEntity();

		// 测试参数对象
		List<WSCRecordEntity> paramsList = new ArrayList<WSCRecordEntity>();

		// 模拟参数
		WSCWayBillEntity params1 = new WSCWayBillEntity();
		params1.setWscItemId("2C3F1A8A37B61351E0536F0AA8C0C906"); // 待刷卡数据编号
		params1.setWayBillNo(""); // 运单编号
		params1.setAlreadySwipeAmount(10); // 已刷卡金额
		params1.setSwipeCardOrgCode("SCDP000000001"); // 刷卡部门编号
		params1.setSwipeCardOrgName("刷卡部门1"); // 刷卡部门名称
		params1.setSwipeCardTime(new Date()); // 刷卡时间
		params1.setSwipeCardUserCode("SCR0000000001"); // 刷卡人编号
		params1.setSwipeCardUserName("刷卡人1"); // 刷卡人名称
		params1.setSerialNo("LSH000000001"); // 交易流水号
		WSCRecordEntity record1 = new WSCRecordEntity();
		record1.getWscRecordList().add(params1);

		// 模拟参数
		WSCWayBillEntity params2 = new WSCWayBillEntity();
		params2.setWscItemId("2C3F1A8A37B61351E0536F0AA8C0C907"); // 待刷卡数据编号
		params2.setWayBillNo(""); // 运单编号
		params2.setAlreadySwipeAmount(10); // 已刷卡金额
		params2.setSwipeCardOrgCode("SCDP000000001"); // 刷卡部门编号
		params2.setSwipeCardOrgName("刷卡部门1"); // 刷卡部门名称
		params2.setSwipeCardTime(new Date()); // 刷卡时间
		params2.setSwipeCardUserCode("SCR0000000001"); // 刷卡人编号
		params2.setSwipeCardUserName("刷卡人1"); // 刷卡人名称
		params2.setSerialNo("LSH000000001"); // 交易流水号
		record1.getWscRecordList().add(params2);

		// 模拟参数
		WSCWayBillEntity params3 = new WSCWayBillEntity();
		params3.setWscItemId("2C3F1A8A37B61351E0536F0AA8C0C908"); // 待刷卡数据编号
		params3.setWayBillNo(""); // 运单编号
		params3.setAlreadySwipeAmount(10.000); // 已刷卡金额
		params3.setSwipeCardOrgCode("SCDP000000001"); // 刷卡部门编号
		params3.setSwipeCardOrgName("刷卡部门1"); // 刷卡部门名称
		params3.setSwipeCardTime(new Date()); // 刷卡时间
		params3.setSwipeCardUserCode("SCR0000000001"); // 刷卡人编号
		params3.setSwipeCardUserName("刷卡人1"); // 刷卡人名称
		params3.setSerialNo("LSH000000001"); // 交易流水号
		WSCRecordEntity record2 = new WSCRecordEntity();
		record2.getWscRecordList().add(params3);

		paramsList.add(record1);
		paramsList.add(record2);

		try {
			result = wscWayBillManageService.recordSwipeCardResultBatch(paramsList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	* @description: 已刷卡运单金额减少
	* @title: testSwipedBillAmountReduce
	* @author panshiqi 309613
	* @date 2016年3月14日 上午8:49:44
	 */
	@Test
	public void testSwipedBillAmountReduce() {

		// 调用T+0接口,修改T+0报表及其明细数据
		WSCWayBillParamEntity params = new WSCWayBillParamEntity();
		params.setWayBillNo("20160203");
		params.setSerialNo("111111115");
		params.setBillReduceAmount(5.0);

		try {
			wscWayBillManageService.swipedBillAmountReduce(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
