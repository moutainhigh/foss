/**   
* @Title: ExpressHandOverBillServiceTest.java 
* @Package com.deppon.foss.module.transfer.load.server 
* @Description: 包交接单service测试类
* @author shiwei shiwei@outlook.com
* @date 2013-9-3 下午5:04:34 
* @version V1.0   
*/
package com.deppon.foss.module.transfer.load.server;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.transfer.load.api.server.dao.IExpressHandOverBillDao;
import com.deppon.foss.module.transfer.load.api.server.service.IExpressHandOverBillService;

/** 
 * @ClassName: ExpressHandOverBillServiceTest 
 * @Description: 包交接单service测试类
 * @author shiwei shiwei@outlook.com
 * @date 2013-9-3 下午5:04:34 
 *  
 */
public class ExpressHandOverBillServiceTest {
	
	@Autowired
	private IExpressHandOverBillService expressHandOverBillService;
	
	private static final String packageNo = "B00000003";
	
	private static final String waybillNo = "4595689742";
	
	private static final String handOverBillNo = "00004560";
	
	/**
	 * 
	* @Title: queryWaybillListByPackageNo 
	* @Description: 根据包号获取运单列表
	* @author shiwei shiwei@outlook.com
	* @date 2013-9-3 下午5:07:37 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@Test
	public void queryWaybillListByPackageNo(){
		expressHandOverBillService.queryWaybillListByPackageNo(packageNo);
	}
	
	/**
	 * 
	* @Title: querySerialNoListByPackageNoAndWaybillNo 
	* @Description: 根据包号运单号获取流水号list
	* @author shiwei shiwei@outlook.com
	* @date 2013-9-3 下午5:08:31 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@Test
	public void querySerialNoListByPackageNoAndWaybillNo(){
		expressHandOverBillService.querySerialNoListByPackageNoAndWaybillNo(packageNo, waybillNo);
	}
	
	/**
	 * 
	* @Title: queryExpressPackageByPackageNo 
	* @Description: 根据包号获取包信息
	* @author shiwei shiwei@outlook.com
	* @date 2013-9-3 下午5:09:29 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@Test
	public void queryExpressPackageByPackageNo(){
		expressHandOverBillService.queryExpressPackageByPackageNo(packageNo);
	}
	
	/**
	 * 
	* @Title: loadExpressPackageInfo 
	* @Description: 根据包号获取包信息、运单信息
	* @author shiwei shiwei@outlook.com
	* @date 2013-9-3 下午5:10:30 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@Test
	public void loadExpressPackageInfo(){
		expressHandOverBillService.loadExpressPackageInfo(packageNo);
	}
	
	/**
	 * 
	* @Title: queryLDPHandOverBill 
	* @Description: 查询落地配交接单
	* @author shiwei shiwei@outlook.com
	* @date 2013-9-3 下午5:12:11 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@Test
	public void queryLDPHandOverBill(){
		expressHandOverBillService.queryLDPHandOverBill(handOverBillNo, null, null);
	}

}
