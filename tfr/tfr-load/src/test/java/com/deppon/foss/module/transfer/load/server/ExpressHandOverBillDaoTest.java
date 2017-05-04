/**   
* @Title: ExpressHandOverBillDaoTest.java 
* @Package com.deppon.foss.module.transfer.load.server 
* @Description: 包交接单dao测试类
* @author shiwei shiwei@outlook.com
* @date 2013-9-3 下午5:00:12 
* @version V1.0   
*/
package com.deppon.foss.module.transfer.load.server;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.BeforeTransaction;

import com.deppon.foss.module.transfer.load.api.server.dao.IExpressHandOverBillDao;

/** 
 * @ClassName: ExpressHandOverBillDaoTest 
 * @Description: 包交接单dao测试类
 * @author shiwei shiwei@outlook.com
 * @date 2013-9-3 下午5:00:12 
 *  
 */
public class ExpressHandOverBillDaoTest {
	
	@Autowired
	private IExpressHandOverBillDao expressHandOverBillDao;
	
	private static final String packageNo = "B00000003";
	
	private static final String waybillNo = "4595689742";
	
	@BeforeTransaction
	public void beforeTransaction() {

	}
	
	/**
	 * 
	* @Title: queryWaybillListByPackageNo 
	* @Description: 根据包号获取运单明细
	* @author shiwei shiwei@outlook.com
	* @date 2013-9-3 下午5:02:46 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@Test
	public void queryWaybillListByPackageNo(){
		expressHandOverBillDao.queryWaybillListByPackageNo(packageNo);
	}

	/**
	 * 
	* @Title: querySerialNoListByPackageNoAndWaybillNo 
	* @Description: 根据包号运单号获取流水号列表
	* @author shiwei shiwei@outlook.com
	* @date 2013-9-3 下午5:03:29 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@Test
	public void querySerialNoListByPackageNoAndWaybillNo(){
		expressHandOverBillDao.querySerialNoListByPackageNoAndWaybillNo(packageNo, waybillNo);
	}
}
