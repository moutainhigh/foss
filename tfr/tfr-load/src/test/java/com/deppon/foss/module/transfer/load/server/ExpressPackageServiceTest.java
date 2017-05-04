/**   
* @Title: ExpressPackageServiceTest.java 
* @Package com.deppon.foss.module.transfer.load.server 
* @Description: 包Service测试类
* @author shiwei shiwei@outlook.com
* @date 2013-9-3 下午4:54:48 
* @version V1.0   
*/
package com.deppon.foss.module.transfer.load.server;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.BeforeTransaction;

import com.deppon.foss.module.transfer.common.api.shared.define.ExpressConstants;
import com.deppon.foss.module.transfer.load.api.server.service.IExpressPackageService;
import com.deppon.foss.module.transfer.load.api.shared.dto.ExpressPackageQueryDto;

/** 
 * @ClassName: ExpressPackageServiceTest 
 * @Description: 包Service测试类
 * @author shiwei shiwei@outlook.com
 * @date 2013-9-3 下午4:54:48 
 *  
 */
public class ExpressPackageServiceTest {
	
	@Autowired
	private IExpressPackageService expressPackageService;
	
	private static final String packageNo = "B00000003";
	
	@BeforeTransaction
	public void beforeTransaction() {

	}
	
	/**
	 * 
	* @Title: queryExpressPackageList 
	* @Description: 查询包
	* @author shiwei shiwei@outlook.com
	* @date 2013-9-3 下午4:44:01 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@Test
	public void queryExpressPackageList(){
		ExpressPackageQueryDto entity = new ExpressPackageQueryDto();
		entity.setPackageNo(packageNo);
		expressPackageService.queryExpressPackageList(entity, 0, 1);
	}
	
	/**
	 * 
	* @Title: queryExpressPackageCount 
	* @Description: 查询包数目
	* @author shiwei shiwei@outlook.com
	* @date 2013-9-3 下午4:48:26 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@Test
	public void queryExpressPackageCount(){
		ExpressPackageQueryDto entity = new ExpressPackageQueryDto();
		entity.setPackageNo(packageNo);
		expressPackageService.queryExpressPackageCount(entity);
	}
	
	/**
	 * 
	* @Title: updateExpressPackageState 
	* @Description: 更新包状态
	* @author shiwei shiwei@outlook.com
	* @date 2013-9-3 下午4:50:17 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@Test
	public void updateExpressPackageState(){
		expressPackageService.updateExpressPackageState(packageNo, ExpressConstants.PACKAGE_STATUS_CREATED_HANDOVER_BILL);
	}
	
	/**
	 * 
	* @Title: queryExpressPackageByPackageNo 
	* @Description: 撤销包
	* @author shiwei shiwei@outlook.com
	* @date 2013-9-3 下午4:52:49 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@Test
	public void cancelExpressPackage(){
		expressPackageService.cancelExpressPackage(packageNo);
	}

}
