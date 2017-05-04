/**   
* @Title: ComplementServiceTest.java 
* @Package com.deppon.foss.module.transfer.load.server 
* @Description: 补码service测试类
* @author shiwei shiwei@outlook.com
* @date 2013-9-3 下午4:24:04 
* @version V1.0   
*/
package com.deppon.foss.module.transfer.load.server;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.BeforeTransaction;

import com.deppon.foss.module.transfer.load.api.server.service.IComplementService;
import com.deppon.foss.module.transfer.load.api.shared.dto.ComplementQueryDto;
import com.deppon.foss.util.define.FossConstants;

/** 
 * @ClassName: ComplementServiceTest 
 * @Description: 补码service测试类
 * @author shiwei shiwei@outlook.com
 * @date 2013-9-3 下午4:24:04 
 *  
 */
public class ComplementServiceTest {
	
	@Autowired
	private IComplementService complementService;
	
	private static final String waybillNo = "5648756924";
	
	private static final String orgName = "圆通速递华东公司";
	
	private static final String orgCode = "LDP00008";
	
	@BeforeTransaction
	public void beforeTransaction() {

	}
	
	/**
	* @Title: queryComplementCount 
	* @Description: 查询待补码运单的总数
	* @author shiwei shiwei@outlook.com
	* @date 2013-9-3 下午4:16:20 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@Test
	public void queryComplementCount(){
		ComplementQueryDto queryDto = new ComplementQueryDto();
		queryDto.setWaybillNo(waybillNo);
		complementService.queryComplementCount(queryDto);
	}
	
	/**
	* @Title: queryComplementList 
	* @Description: 查询待补码运单
	* @author shiwei shiwei@outlook.com
	* @date 2013-9-3 下午4:17:23 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@Test
	public void queryComplementList(){
		ComplementQueryDto queryDto = new ComplementQueryDto();
		queryDto.setWaybillNo(waybillNo);
		complementService.queryComplementList(queryDto, 0, 1);
	}
	
	/**
	 * 
	* @Title: complementPkpOrg 
	* @Description: 补码
	* @author shiwei shiwei@outlook.com
	* @date 2013-9-3 下午4:27:37 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@Test
	public void complementPkpOrg(){
		//complementService.complementPkpOrg(waybillNo, orgCode, orgName,FossConstants.NO);
	}
	
	/**
	 * 
	* @Title: queryTransferCenterCode 
	* @Description: 获取上级组织code
	* @author shiwei shiwei@outlook.com
	* @date 2013-9-3 下午4:34:17 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@Test
	public void queryTransferCenterCode(){
		complementService.queryTransferCenterCode();
	}

}
