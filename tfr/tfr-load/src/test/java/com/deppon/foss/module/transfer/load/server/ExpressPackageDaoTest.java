/**   
* @Title: ExpressPackageDao.java 
* @Package com.deppon.foss.module.transfer.load.server 
* @Description: 包dao测试类
* @author shiwei shiwei@outlook.com
* @date 2013-9-3 下午4:34:58 
* @version V1.0   
*/
package com.deppon.foss.module.transfer.load.server;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.BeforeTransaction;

import com.deppon.foss.module.transfer.common.api.shared.define.ExpressConstants;
import com.deppon.foss.module.transfer.load.api.server.dao.IExpressPackageDao;
import com.deppon.foss.module.transfer.load.api.shared.dto.ExpressPackageQueryDto;

/** 
 * @ClassName: ExpressPackageDao 
 * @Description: 包dao测试类
 * @author shiwei shiwei@outlook.com
 * @date 2013-9-3 下午4:34:58 
 *  
 */
public class ExpressPackageDaoTest {
	
	@Autowired
	private IExpressPackageDao expressPackageDao;
	
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
		expressPackageDao.queryExpressPackageList(entity, 0, 1);
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
		expressPackageDao.queryExpressPackageCount(entity);
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
		expressPackageDao.updateExpressPackageState(packageNo, ExpressConstants.PACKAGE_STATUS_CREATED_HANDOVER_BILL);
	}
	
	/**
	 * 
	* @Title: queryExpressPackageByPackageNo 
	* @Description: 根据包号获取包信息
	* @author shiwei shiwei@outlook.com
	* @date 2013-9-3 下午4:52:49 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@Test
	public void queryExpressPackageByPackageNo(){
		expressPackageDao.queryExpressPackageByPackageNo(packageNo);
	}


}
